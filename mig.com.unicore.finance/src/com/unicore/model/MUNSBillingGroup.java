/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInOut;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.unicore.model.factory.UNSFinanceModelFactory;
import com.uns.base.model.MInvoice;
import com.uns.base.model.Query;
import com.uns.model.MUNSBilling;
import com.uns.model.MUNSBillingLine;

/**
 * @author setyaka
 * 
 */
public class MUNSBillingGroup extends X_UNS_BillingGroup implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3238381958568417738L;

	/** Process Message */
	private String m_processMsg = null;

	public MUNSBillingGroup(Properties ctx, int UNS_BillingGroup_ID, String trxName) {
		super(ctx, UNS_BillingGroup_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSBillingGroup(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSBillingGroup(Properties ctx, String trxName) {
		this(ctx, 0, trxName);
		setDocAction(DocAction.ACTION_Prepare);
		setDocStatus(DocAction.STATUS_Drafted);
		setGenerateBillingLine("N");
		setGrandTotal(Env.ZERO);
		setProcessed(false);
	}

	public static MUNSBillingGroup getBilling(Properties ctx, int UNS_Billing_ID, String trxName) {
		return new MUNSBillingGroup(ctx, UNS_Billing_ID, trxName);
	}

	/*
	 * Generate Billing
	 */
	public int createBilling() {

		int noinv = 0;
		log.info("");
		try
		{
			// Delete previous records generated
			String sqldel = "DELETE FROM UNS_Billing " + " WHERE UNS_BillingGroup_ID = ?";
			PreparedStatement pstmtdel = DB.prepareStatement(sqldel, get_TrxName());
			pstmtdel.setInt(1, getUNS_BillingGroup_ID());
			int nodel = pstmtdel.executeUpdate();
			log.config("UNS_Billing_Line deleted=" + nodel);
			pstmtdel.close();

			// Search invoice applicable for Billing
			String sql = "SELECT AD_Client_ID, AD_Org_ID, C_Invoice_ID, DateInvoiced, C_PaymentTerm_ID, " // 1..5
					+ "DueDate, invoiceOpen(C_Invoice_ID,C_InvoicePaySchedule_ID), C_InvoicePaySchedule_ID " // 6,7
					+ "FROM C_Invoice_V WHERE AD_Client_ID = ? AND AD_Org_ID = ? "
					+ "AND IsActive = 'Y' AND IsSoTrx = ? AND DocStatus IN ('CO', 'CL') AND IsPaid = 'N' "
					+ "AND C_BPartner_ID = ? AND DueDate BETWEEN ? AND ?";

			PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getAD_Client_ID());
			pstmt.setInt(2, getAD_Org_ID());
			ResultSet rs = pstmt.executeQuery();
			int line = 0;
			while (rs.next())
			{
				line = line + 10;
				MUNSBillingLine bline = new MUNSBillingLine(getCtx(), 0, get_TrxName());
				bline.setAD_Org_ID(rs.getInt(2));
				bline.setLine(line);
				bline.setC_Invoice_ID(rs.getInt(3));
				bline.setDateInvoiced(rs.getTimestamp(4));
				bline.setC_PaymentTerm_ID(rs.getInt(5));
				bline.setDueDate(rs.getTimestamp(6));
				bline.setNetAmtToInvoice(rs.getBigDecimal(7));
				bline.setC_InvoicePaySchedule_ID(rs.getInt(8));
				bline.save();
				noinv++;
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e)
		{
			log.log(Level.SEVERE, "", e);
			return -1;
		}

		return noinv;
	}

	@Override
	protected boolean beforeDelete() {

		try
		{
			// Delete previous records generated
			String sqldel = "DELETE FROM UNS_Billing " + " WHERE UNS_BillingGroup_ID = ?";
			PreparedStatement pstmtdel = DB.prepareStatement(sqldel, get_TrxName());
			pstmtdel.setInt(1, getUNS_BillingGroup_ID());
			int nodel = pstmtdel.executeUpdate();
			log.config("UNS_Billing deleted=" + nodel);
			pstmtdel.close();
		} catch (SQLException e)
		{
			log.log(Level.SEVERE, "", e);
			return false;
		}

		return true;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		// updateHeaderAmount();
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// updateHeaderAmount();
		return true;
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(!newRecord)
		{
			String sql = "SELECT COUNT(bl.*) FROM UNS_BillingLine bl WHERE"
					+ " EXISTS (SELECT 1 FROM UNS_Billing b WHERE"
					+ " b.UNS_Billing_ID = bl.UNS_Billing_ID AND b.UNS_BillingGroup_ID = ?)";
			int count = DB.getSQLValue(get_TrxName(), sql, get_ID());
			setTotalInvoice(count);
		}

		return true;
	}

	public boolean approveIt() {
		log.info(toString());
		// setIsApproved(true);
		return true;
	}

	@Override
	public boolean closeIt() {
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public String completeIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());

		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}


		if (!MUNSBillingGroupResult.createBGResult(this))
			throw new AdempiereException("Error when create billing return");
		
		m_processMsg = ModelValidationEngine.get()
				.fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		setProcessed(true);
				
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	/**
	 * Set Processed. Propagate to Lines
	 * 
	 * @param processed
	 *            processed
	 */
	public void setProcessed(boolean processed) {
		super.setProcessed(processed);
		if (get_ID() == 0)
			return;
		String set = "SET Processed='" + (processed ? "Y" : "N") + "' WHERE UNS_BillingGroup_ID="
				+ getUNS_BillingGroup_ID();
		int noLine = DB.executeUpdateEx("UPDATE UNS_Billing " + set, get_TrxName());
		log.fine("setProcessed - " + processed + " - Lines=" + noLine);
	} // setProcessed

	@Override
	public File createPDF() {
		/*
		 * try { File temp = File.createTempFile(get_TableName()+get_ID()+"_",
		 * ".pdf"); return createPDF (temp); } catch (Exception e) {
		 * log.severe("Could not create PDF - " + e.getMessage()); }
		 */
		return null;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return getGrandTotal();
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return getCreatedBy();
	}

	@Override
	public String getDocumentInfo() {

		return "Grouping Billing " + getDocumentNo();
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return m_processMsg;
	}

	@Override
	public String getSummary() {
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		// : Total Lines = 123.00 (#1)
		sb.append(": ").append(Msg.translate(getCtx(), "GrandTotal")).append("=")
				.append(this.getGrandTotal());
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}

	@Override
	public boolean invalidateIt() {
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		log.info(toString());
		m_processMsg = ModelValidationEngine.get()
				.fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		// Should Have Line
		MUNSBilling[] bLines = getLines(false);
		if (bLines.length <= 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		else
		{
			for(MUNSBilling bill : bLines)
			{
				MUNSBillingLine[] lines = bill.getLines(true);
				for(MUNSBillingLine line : lines)
				{
					String sql = "SELECT InvoiceOpen(C_Invoice_ID, 0) FROM UNS_BillingLine"
							+ " WHERE UNS_BillingLine_ID = ?";
					BigDecimal open = DB.getSQLValueBD(get_TrxName(), sql, line.get_ID());
					if(open == null)
						open = Env.ZERO;
					
					if(open.compareTo(Env.ZERO) == 0)
						line.deleteEx(true);
					
					if(line.getNetAmtToInvoice().compareTo(open) != 0)
					{
						line.setNetAmtToInvoice(open);
						line.saveEx();
					}	
				}
			}
		}
		
		boolean isNeedCheckVAT = MSysConfig.getBooleanValue(
				MSysConfig.CHECK_VAT_ON_BILLING, false);
		if(isNeedCheckVAT)
		{
			checkVAT();
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	MUNSBilling[] lines = null;

	public MUNSBilling[] getLines(boolean query) {
		if (lines == null || query)
			lines = getLines(null);

		return lines;
	}

	public MUNSBilling[] getLines(String whereClause) {
		String whereClauseFinal = "UNS_BillingGroup_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;

		List<MUNSBilling> list = new Query(getCtx(), MUNSBilling.Table_Name, whereClauseFinal, get_TrxName())
				.setParameters(new Object[] { getUNS_BillingGroup_ID() }).setOrderBy(
						MUNSBilling.COLUMNNAME_UNS_Billing_ID).list();

		return list.toArray(new MUNSBilling[list.size()]);
	}

	@Override
	public boolean processIt(String processAction) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	}

	@Override
	public boolean reActivateIt() {

		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		MUNSBillingGroupResult gr = MUNSBillingGroupResult.getByBillingGroup(getCtx(), get_ID(), get_TrxName());
		
		if(gr.isProcessed() && gr.getDocStatus().equals("CO"))
			throw new AdempiereUserError("Cannot reactive, document Billing Result has processed");
		else
		{
			String delGL = "DELETE FROM UNS_BillingLine_Giro WHERE UNS_BillingLine_Result_ID IN"
					+ " (SELECT UNS_BillingLine_Result_ID FROM UNS_BillingLine_Result WHERE UNS_Billing_Result_ID IN"
					+ " (SELECT UNS_Billing_Result_ID FROM UNS_Billing_Result WHERE UNS_BillingGroup_Result_ID = " + gr.get_ID()
					+ "))";
			DB.executeUpdate(delGL, get_TrxName());
			
			String delBLR = "DELETE FROM UNS_BillingLine_Result WHERE UNS_Billing_Result_ID IN"
					+ " (SELECT UNS_Billing_Result_ID FROM UNS_Billing_Result WHERE UNS_BillingGroup_Result_ID = " + gr.get_ID()
					+ ")";
			DB.executeUpdate(delBLR, get_TrxName());
			
			String delBR = "DELETE FROM UNS_Billing_Result WHERE UNS_BillingGroup_Result_ID = " + gr.get_ID();
			DB.executeUpdate(delBR, get_TrxName());
			
			String delGBR = "DELETE FROM UNS_BillingGroup_Result WHERE UNS_BillingGroup_Result_ID = " + gr.get_ID();
			DB.executeUpdate(delGBR, get_TrxName());
			
			for(MUNSBilling bil : getLines(true))
			{
				for(MUNSBillingLine bLine : bil.getLines(true))
				{
					bLine.setProcessed(false);
					bLine.save();
				}
			}
		}
		
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		setProcessed(false);
		setDocAction(DOCACTION_Complete);

		return true;
	}

	@Override
	public boolean rejectIt() {
		log.info(toString());
		// setIsApproved(false);
		return true;
	}

	@Override
	public boolean reverseAccrualIt() {
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return voidIt();
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessed(false);
		return true;
	}

	@Override
	public boolean voidIt() {
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		// Set up Description as voided
		MUNSBilling[] blines = getLines(false);
		if (blines.length > 0)
		{
			for (MUNSBilling bline : blines)
			{
				bline.setDescription((bline.getDescription() == null ? "" : bline.getDescription())
						+ " Voided (Old Amount : " + bline.getGrandTotal() + ")");
				bline.save(get_TrxName());
			}
		}
		addDescription(Msg.getMsg(getCtx(), "Voided"));

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		return true;
	}

	/**
	 * Add to Description
	 * 
	 * @param description
	 *            text
	 */
	public void addDescription(String description) {
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription

	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Customize Valid Actions
	 * 
	 * @param docStatus
	 * @param processing
	 * @param orderType
	 * @param isSOTrx
	 * @param AD_Table_ID
	 * @param docAction
	 * @param options
	 * @return Number of valid options
	 */
	public int customizeValidActions(String docStatus, Object processing, String orderType, String isSOTrx,
			int AD_Table_ID, String[] docAction, String[] options, int index) {

		// If status = Drafted, add "Prepare" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
				|| docStatus.equals(DocumentEngine.STATUS_Invalid))
		{
			options[index++] = DocumentEngine.ACTION_Prepare;
			options[index++] = DocumentEngine.ACTION_ReActivate;
		}

		// If status = Completed, add "Reactivte" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Completed))
		{
			options[index++] = DocumentEngine.ACTION_ReActivate;
			options[index++] = DocumentEngine.ACTION_Void;
		}
		
		if (docStatus.equals(DocumentEngine.STATUS_InProgress))
		{
			options[index++] = DocumentEngine.ACTION_ReActivate;
		}

		return index;
	}

	public int recalcBilling(int SalesRep_ID, boolean DeleteOld, 
			String InvCollection, boolean isExcludeBilledInvoice, 
			Timestamp dateFrom, Timestamp dateTo) {
		int count = 0;
		try
		{
			if (DeleteOld)
			{
				String sqldel = "DElETE FROM UNS_BillingLine WHERE UNS_Billing_ID IN "
						+ " (SELECT UNS_Billing_ID FROM UNS_Billing WHERE UNS_BillingGroup_ID = ?)";

				PreparedStatement pstmtdel = DB.prepareStatement(sqldel, get_TrxName());
				pstmtdel.setInt(1, get_ID());
				int nodel = pstmtdel.executeUpdate();
				log.config("UNS_Billing deleted=" + nodel);
				
				sqldel = "DELETE FROM UNS_Billing " + " WHERE UNS_BillingGroup_ID = ?";
				pstmtdel = DB.prepareStatement(sqldel, get_TrxName());
				pstmtdel.setInt(1, get_ID());
				nodel = pstmtdel.executeUpdate();
				log.config("UNS_Billing deleted=" + nodel);
				
				pstmtdel.close();
			}
			
			Hashtable<Integer, MUNSBilling> mapBilling = initialBilling();
			MInvoice[] invs = null;
			if (InvCollection.equals("O"))
				invs = getDueOpenInvoices(isExcludeBilledInvoice, dateFrom, dateTo);
			else 
				invs = getOpenInvoices(isExcludeBilledInvoice, dateFrom, dateTo);
			
			for (MInvoice inv : invs)
			{
				MUNSBilling billing = mapBilling.get(inv.getC_BPartner_ID());
				if(null == billing)
				{
					billing = new MUNSBilling(this, inv.getC_BPartner_ID(), inv.getC_BPartner_Location_ID());
					billing.saveEx();
					mapBilling.put(inv.getC_BPartner_ID(), billing);
				}
				billing.createLines(inv, 0, 0);
				count++;
			}
		} catch (Exception e)
		{
			throw new AdempiereException(e.toString());
		}

		return count;
	}

	/**
	 * 
	 * @param excludeBilledInvoice
	 * @param dateFrom
	 * @return
	 */
	private MInvoice[] getOpenInvoices(boolean excludeBilledInvoice,
			Timestamp dateFrom, Timestamp dateTo) 
	{
		StringBuilder sb = new StringBuilder();
		String and = " AND ";
		boolean needAnd = false;
		if(getUNS_Rayon_ID() > 0)
		{
			sb.append("C_BPartner_Location_ID IN (SELECT C_BPartner_Location_ID FROM ")
			.append("C_BPartner_Location WHERE UNS_Rayon_ID = ")
			.append(getUNS_Rayon_ID()).append(")");
			needAnd = true;
		}
		if(getSalesRep_ID() > 0)
		{
			sb.append(needAnd ? and : "").append(" SalesRep_ID = ")
			.append(getSalesRep_ID());
			needAnd =  true;
		}
		
		if(dateFrom != null && dateTo != null)
		{
			sb.append(needAnd ? and : "").append(" DateInvoiced BETWEEN '")
			.append(dateFrom).append("' AND '").append(dateTo).append("'");
			needAnd = true;
		}
		else if(dateFrom != null && dateTo == null)
		{
			sb.append(needAnd ? and : "").append(
					" DateInvoiced BETWEEN '").append(dateFrom)
					.append("' AND '").append(getDateDoc()).append("'");
			needAnd = true;
		}
		else
		{
			sb.append(needAnd ? and : "").append(" DateInvoiced <= '")
			.append(dateTo != null ? dateTo : getDateDoc()).append("'");
			needAnd = true;
		}
		
		sb.append(needAnd ? and : ""). 
		append(" isPaid = 'N' AND InvoiceOpen(C_Invoice_ID, 0) > 0")
		.append(" AND (DocStatus= 'CO' OR DocStatus = 'CL') AND IsSOTrx = 'Y'");
		
		if(excludeBilledInvoice)
		{
			sb.append(" AND C_Invoice_ID NOT IN (SELECT C_Invoice_ID FROM UNS_BillingLine WHERE IsActive = 'Y'")
			.append(" AND UNS_Billing_ID IN (SELECT UNS_Billing_ID FROM UNS_Billing WHERE UNS_BillingGroup_ID IN(")
			.append("SELECT UNS_BillingGroup_ID FROM UNS_BillingGroup WHERE DocStatus IN ('CO','CL'))))");
		}
		
		List<MInvoice> invList = Query.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID,
				MInvoice.Table_Name, sb.toString(), get_TrxName()).list();

		MInvoice[] retval = new MInvoice[invList.size()];
		invList.toArray(retval);

		return retval;
	}
	
	/**
	 * 
	 * @return
	 */
	private MInvoice[] getDueOpenInvoices(boolean excludeBilledInvoice,
			Timestamp dateFrom, Timestamp dateTo) {
		StringBuilder sb = new StringBuilder();
		String and = " AND ";
		boolean needAnd = false;
		if(getUNS_Rayon_ID() > 0)
		{
			sb.append("C_BPartner_Location_ID IN ").append(
					"(SELECT C_BPartner_Location_ID FROM C_BPartner_Location")
					.append(" WHERE UNS_Rayon_ID = ").append(getUNS_Rayon_ID())
					.append(")");
			needAnd = true;
		}
		if(getSalesRep_ID() > 0)
		{
			sb.append(needAnd ? and : "").append(" SalesRep_ID = ")
					.append(getSalesRep_ID());
			needAnd = true;
		}
		
		if(dateFrom != null && dateTo != null)
		{
			sb.append(needAnd ? and : "").append(
					" DateInvoiced BETWEEN '").append(dateFrom)
					.append("' AND '").append(dateTo).append("'");
			needAnd = true;
		}
		else if(dateFrom != null && dateTo == null)
		{
			sb.append(needAnd ? and : "").append(
					" DateInvoiced BETWEEN '").append(dateFrom)
					.append("' AND '").append(getDateDoc()).append("'");
			needAnd = true;
		}
		else
		{
			sb.append(needAnd ? and : "").append(" DateInvoiced <= '")
			.append(dateTo != null ? dateTo : getDateDoc()).append("'");
			needAnd = true;
		}
		
		sb.append(needAnd ? and : "")
		.append(" isPaid = 'N' AND InvoiceOpen(C_Invoice_ID, 0) > 0")
		.append(" AND (DocStatus= 'CO' OR DocStatus = 'CL') AND NOW() >= DateInvoiced ")
		.append(" + (INTERVAL '1 days' * (SELECT NetDays FROM C_PaymentTerm WHERE ")
		.append(" C_PaymentTerm_ID = C_Invoice.C_PaymentTerm_ID)) AND IsSOTrx = 'Y'");
		
		if(excludeBilledInvoice)
		{
			sb.append(" AND C_Invoice_ID NOT IN (SELECT C_Invoice_ID FROM UNS_BillingLine WHERE IsActive = 'Y'")
			.append(" AND UNS_Billing_ID IN (SELECT UNS_Billing_ID FROM UNS_Billing WHERE UNS_BillingGroup_ID IN(")
			.append("SELECT UNS_BillingGroup_ID FROM UNS_BillingGroup WHERE DocStatus IN ('CO','CL'))))");
		}

		List<MInvoice> invList = Query.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID,
				MInvoice.Table_Name, sb.toString(), get_TrxName()).list();
		
		MInvoice[] retval = new MInvoice[invList.size()];
		invList.toArray(retval);

		return retval;
	}
	
	/**
	 * Get lines billing as Hashtable.
	 * @return
	 */
	public Hashtable<Integer, MUNSBilling> initialBilling()
	{
		Hashtable<Integer, MUNSBilling> retVal = new Hashtable<>();
		MUNSBilling[] lines = getLines(true);
		for(MUNSBilling bill : lines)
		{
			if(retVal.get(bill.getC_BPartner_ID()) != null)
				continue;
			
			retVal.put(bill.getC_BPartner_ID(), bill);
		}
		
		return retVal;
	}
	
	public String checkVAT ()
	{
		m_processMsg = null;
		
		for (MUNSBilling billing : getLines(null))
		{
			for (MUNSBillingLine bLine : billing.getLines(null))
			{
				String sql = "SELECT M_InOut_ID FROM M_InOut WHERE C_Invoice_ID = " + bLine.getC_Invoice_ID();
				
				MInOut io = new MInOut(getCtx(), DB.getSQLValue(get_TrxName(), sql.toString()), get_TrxName());
				
				if (io.getPOReference() == null)
					return null;
				else
				{
					if(bLine.getReferenceNo() != null)
						return null;
					else
					{
						m_processMsg = "Please Define Reference No for VAT Invoice";
						throw new AdempiereUserError(m_processMsg);
					}
				}
			}
		}
		return m_processMsg;
	}
}
