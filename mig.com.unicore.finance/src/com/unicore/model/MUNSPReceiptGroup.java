/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPayment;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.wf.MWorkflow;

import com.uns.util.MessageBox;

import com.unicore.base.model.MPaymentAllocate;
import com.unicore.model.factory.UNSFinanceModelFactory;
import com.uns.base.model.Query;
import com.uns.model.GeneralCustomization;

/**
 * @author root
 *
 */
public class MUNSPReceiptGroup extends X_UNS_PReceipt_Group implements
		I_UNS_PReceipt_Group, DocAction, DocOptions{
	
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSPaymentReceipt[] m_lines = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1922223454978374185L;

	/**
	 * @param ctx
	 * @param UNS_PReceipt_Group_ID
	 * @param trxName
	 */
	public MUNSPReceiptGroup(Properties ctx, int UNS_PReceipt_Group_ID,
			String trxName) {
		super(ctx, UNS_PReceipt_Group_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPReceiptGroup(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSPaymentReceipt[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSPaymentReceipt> list = Query.get(
				getCtx(), UNSFinanceModelFactory.EXTENSION_ID
				, MUNSPaymentReceipt.Table_Name, Table_Name + "_ID = ?"
				, get_TrxName()).setParameters(get_ID()).list();
		
		m_lines = new MUNSPaymentReceipt[list.size()];
		list.toArray(m_lines);
		return m_lines;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		if (log.isLoggable(Level.INFO))
			log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		Object oo = get_Value("DateReceived");
		if (null == oo)
		{
			throw new AdempiereUserError("Please define date received.");
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		createTransferPay();
		
		try
		{
			MUNSPaymentReceipt[] myLines = getLines(false);
			for(MUNSPaymentReceipt line : myLines)
			{
				if(!line.getDocStatus().equals(DOCSTATUS_Drafted)
						&& !line.getDocStatus().equals(DOCSTATUS_InProgress)
						&& !line.getDocStatus().equals(DOCSTATUS_Invalid))
				{
					continue;
				}
				
				ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(line, DocAction.ACTION_Complete);
				if(pi.isError())
				{
					throw new AdempiereUserError("Can't complete payment recept.." + pi.getSummary());
				}
			}
		}
		catch (Exception e)
		{
			setProcessed(false);
			throw new AdempiereException("Failed on complete lines.. " + e.getMessage());
		}
		setProcessed(true);
		m_justPrepared = true;
		saveEx();

		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		if (log.isLoggable(Level.INFO))
			log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		if (log.isLoggable(Level.INFO))
			log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {
		if (DOCACTION_Prepare.equals(getDocAction()))
		{
			setProcessed(false);
			return DocAction.STATUS_InProgress;
		}

		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
				
		String idBG = "SELECT UNS_BillingGroup_ID FROM UNS_PReceipt_Group WHERE UNS_PReceipt_Group_ID =?";
		MUNSBillingGroup bg = new MUNSBillingGroup(getCtx(), DB.getSQLValue(get_TrxName(), idBG, get_ID()), get_TrxName());
		if(bg.getDocStatus().equals(DOCSTATUS_Completed))
			statusGroupingBilling(bg, true);
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (log.isLoggable(Level.INFO))
			log.info(toString());

		// Implicit Approval
		if (!isApproved())
			approveIt();
		
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		addDescription(Msg.getMsg(getCtx(), "Voided"));

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}
	
	public void addDescription(String description) {
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before Close

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reverseCorrect
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return voidIt();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reverseAccrual
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;		
		
		for(MUNSPaymentReceipt pr : getLines(true))
		{
			if(pr.getDocStatus().equals(DOCSTATUS_Completed))
			{
				if(!pr.processIt(DOCACTION_Re_Activate))
				{
					m_processMsg = pr.getProcessMsg();
					return false;
				}
				pr.save();
			}
		}
		
		String sql = "SELECT 1 FROM C_Payment WHERE DocStatus IN ('IP', 'CO', 'CL') AND UNS_PR_Allocation_ID IN"
				+ " (SELECT UNS_PR_Allocation_ID FROM UNS_PR_Allocation WHERE UNS_BillingGroup_Result_ID IN"
				+ " (SELECT UNS_BillingGroup_Result_ID FROM UNS_PaymentReceipt WHERE UNS_PReceipt_Group_ID=?))";
		int count = DB.getSQLValue(get_TrxName(), sql, get_ID());
		if(count > 0)
		{
			m_processMsg = "Cannot reactivate this document, one or more than payment has processed";
			return false;
		}
		else	
		{
			String upLineResult = "UPDATE UNS_BillingLine_Result blr SET C_PaymentAllocate_ID = NULL"
					+ ", IsHasReceipt = 'N' WHERE EXISTS (SELECT 1 FROM UNS_Billing_Result br"
					+ " WHERE br.UNS_Billing_Result_ID = blr.UNS_Billing_Result_ID AND"
					+ " br.UNS_BillingGroup_Result_ID = ?)";
			DB.executeUpdate(upLineResult, getUNS_BillingGroup_Result_ID(), get_TrxName());
			String delPayAllocate = "DELETE FROM C_PaymentAllocate WHERE C_Payment_ID IN"
					+ " (SELECT C_Payment_ID FROM C_Payment WHERE DocStatus <> 'RE' AND UNS_PR_Allocation_ID ="
					+ " (SELECT UNS_PR_Allocation_ID FROM UNS_PR_Allocation WHERE UNS_BillingGroup_Result_ID = ?))";
			String delPayment = "DELETE FROM C_Payment WHERE DocStatus <> 'RE' AND UNS_PR_Allocation_ID ="
					+ " (SELECT UNS_PR_Allocation_ID FROM UNS_PR_Allocation WHERE UNS_BillingGroup_Result_ID =?)";
			String delBillPayAllocate = "DELETE FROM UNS_PR_Allocation WHERE UNS_BillingGroup_Result_ID =?";
			int countPayAllocate = DB.executeUpdate(delPayAllocate, getUNS_BillingGroup_Result_ID(), get_TrxName());
			int countPay = DB.executeUpdate(delPayment, getUNS_BillingGroup_Result_ID(), get_TrxName());
			int countBillPayAllocate = DB.executeUpdate(delBillPayAllocate, getUNS_BillingGroup_Result_ID(), get_TrxName());
			if(countPay < 0 || countPayAllocate < 0 ||countBillPayAllocate < 0)
			{
				m_processMsg = "Failed when trying delete payment";
				return false;
			}
			
			String upPayment = "UPDATE C_Payment SET UNS_PR_Allocation_ID = null"
					+ " WHERE UNS_PR_Allocation_ID = (SELECT UNS_PR_Allocation_ID FROM UNS_PR_Allocation"
					+ " WHERE UNS_BillingGroup_Result_ID = ?)";
			int updatePay = DB.executeUpdate(upPayment, getUNS_BillingGroup_Result_ID(), get_TrxName());
			if(updatePay < 0)
			{
				m_processMsg = "Failed when trying update payment (reverse)";
				return false;
			}
			
			String idBG = "SELECT UNS_BillingGroup_ID FROM UNS_BillingGroup_Result WHERE UNS_BillingGroup_Result_ID = ?";
			MUNSBillingGroup bg = new MUNSBillingGroup(getCtx(), DB.getSQLValue(get_TrxName(), idBG,
					getUNS_BillingGroup_Result_ID()), get_TrxName());
			if(bg.getDocStatus().equals(DOCSTATUS_Closed))
			statusGroupingBilling(bg, false);
		}
		
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		setProcessed(false);
		setDocStatus(DOCSTATUS_Drafted);
		setDocAction(DOCACTION_Complete);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		// : Grand Total = 123.00 (#1)
		sb.append(": ").append(Msg.translate(getCtx(), "Grand Total")).append("=").append(getGrandTotal());

		if (m_lines != null)
			sb.append(" (#").append(m_lines.length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return Env.ZERO;
	}
	
	public String toString()
	{
		StringBuffer sb =
				new StringBuffer(getClass().getName()).append(get_ID()).append("-").append(getDocumentNo())
						.append(", Grand Total=").append(getGrandTotal());

		return sb.toString();
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		
		if (docStatus.equals(DocumentEngine.STATUS_Completed))
		{
			options[index++] = DocumentEngine.ACTION_ReActivate;
		}
		return index;
	}
	
	public String statusGroupingBilling(MUNSBillingGroup bg, boolean isComplete)
	{
		m_processMsg = null;
		String msg = isComplete ? Msg.getMsg(getCtx(), "Do you want to close grouping billing ?")
				: Msg.getMsg(getCtx(), "Do you want to open grouping billing ?");
		String title = Msg.getMsg(getCtx(), "Confirm status grouping billing");
		int retVal = MessageBox.showMsg(this,
				getCtx()
				, msg
				, title
				, MessageBox.YESNO
				, MessageBox.ICONQUESTION);
		if(retVal == MessageBox.RETURN_NO || retVal == MessageBox.RETURN_NONE)
		{
			m_processMsg = null;
		}
		
		if(retVal == MessageBox.RETURN_OK || retVal == MessageBox.RETURN_YES)
		{			
			try {
				if(isComplete)
					bg.processIt(DOCACTION_Close);
				else
				{
					bg.setDocStatus(DOCSTATUS_Completed);
					bg.setDocAction(DOCACTION_Complete);
				}
				bg.save();
			} catch (Exception e) {
				m_processMsg = "Error when trying closed grouping billing";
				e.printStackTrace();
			}
		}
		return m_processMsg;
	}
	
	private void createTransferPay()
	{
		MUNSBillingGroupResult result = new MUNSBillingGroupResult(getCtx(), getUNS_BillingGroup_Result_ID(), get_TrxName());
		String whereClause = " UNS_PR_Allocation_ID = ? AND C_BPartner_ID = ? AND TenderType = ?"
				+ " AND DateTrx = ? AND Processed = 'N' AND C_BankAccount_ID = ?";
		
		String sql = "SELECT b.C_BPartner_ID, blr.C_Invoice_ID, blr.AmountTrf, blr.ReceiptDate, blr.C_BankAccount_ID,"
				+ " blr.AD_Org_ID, blr.WriteOffAmt FROM UNS_BillingLine_Result blr"
				+ " INNER JOIN UNS_Billing_Result br ON br.UNS_Billing_Result_ID = blr.UNS_Billing_Result_ID"
				+ " INNER JOIN UNS_Billing b ON b.UNS_Billing_ID = br.UNS_Billing_ID"
				+ " WHERE blr.PaymentStatus = 'PT' AND br.UNS_BillingGroup_Result_ID = ? ORDER BY b.C_BPartner_ID";
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		try {
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, result.get_ID());
			rs = stmt.executeQuery();
			while (rs.next())
			{
				int allocation_ID =
						GeneralCustomization.get_ID(get_TrxName(), MUNSPRAllocation.Table_Name,
								MUNSPRAllocation.COLUMNNAME_UNS_BillingGroup_Result_ID, "=", getUNS_BillingGroup_Result_ID());
				
				MUNSPRAllocation allocation =
						new MUNSPRAllocation(getCtx(), allocation_ID == -1 ? 0 : allocation_ID, get_TrxName());
				
				allocation.addAmount(Env.ZERO, Env.ZERO, rs.getBigDecimal(3));
				allocation.setUNS_BillingGroup_Result_ID(getUNS_BillingGroup_Result_ID());
				allocation.setAD_Org_ID(result.getAD_Org_ID());
				if (!allocation.save())
					throw new AdempiereException("Error while tray create Billing Payment Allocation");
				
				MPayment pay = new Query(getCtx(), MPayment.Table_Name, whereClause, get_TrxName())
									.setParameters(allocation.get_ID(), rs.getInt(1), MPayment.TENDERTYPE_DirectDeposit
											, rs.getTimestamp(4), rs.getInt(5)).first();
				
				if(pay == null)
				{
					pay = new MPayment(getCtx(), 0, get_TrxName());
					pay.setUNS_PR_Allocation_ID(allocation.get_ID());
					pay.setAD_Org_ID(rs.getInt(6));
					pay.setC_BankAccount_ID(rs.getInt(5));
					pay.setIsReceipt(true);
					pay.setC_DocType_ID(true);
					pay.setC_BPartner_ID(rs.getInt(1));
					pay.setTenderType(MPayment.TENDERTYPE_DirectDeposit);
					pay.setDateTrx(rs.getTimestamp(4));
					pay.setDateAcct(rs.getTimestamp(4));
					pay.setSalesRep_ID(getSalesRep_ID());
					pay.saveEx();
				}
				
				MPaymentAllocate pa = new MPaymentAllocate(getCtx(), 0, get_TrxName());
				BigDecimal openInvoice = DB.getSQLValueBD(get_TrxName(), "SELECT InvoiceOpen(?,0)", rs.getInt(2));
				pa.setC_Payment_ID(pay.get_ID());
				pa.setAD_Org_ID(rs.getInt(6));
				pa.setC_Invoice_ID(rs.getInt(2));
				pa.setAmount(rs.getBigDecimal(3));
				pa.setPayToOverUnderAmount(rs.getBigDecimal(3));
				pa.setWriteOffAmt(rs.getBigDecimal(7));
				pa.setOverUnderAmt(openInvoice.subtract(rs.getBigDecimal(3)));
				pa.setInvoiceAmt(openInvoice);
				pa.saveEx();
			}
		} catch (SQLException e) {
			throw new AdempiereException(e.getMessage());
		}		
	}
	
	public static MUNSPReceiptGroup getOfResult(Properties ctx, int BillGroupResult_ID, String trxName)
	{
		MUNSPReceiptGroup group = Query.get(ctx, UNSFinanceModelFactory.EXTENSION_ID,
				Table_Name, COLUMNNAME_UNS_BillingGroup_Result_ID + "=?", trxName).
				setParameters(BillGroupResult_ID).firstOnly();
		
		return group;
	}
	
	public boolean isComplete()
	{
		return getDocStatus().equals(DOCSTATUS_Completed)
				|| getDocStatus().equals(DOCSTATUS_Closed);
	}
}	
