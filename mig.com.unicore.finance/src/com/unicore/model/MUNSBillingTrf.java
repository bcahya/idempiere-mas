/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MSalesRegion;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.util.MessageBox;

import com.uns.model.IUNSApprovalInfo;

/**
 * @author Burhani Adam
 *
 */
public class MUNSBillingTrf extends X_UNS_BillingTrf implements DocAction, DocOptions, IUNSApprovalInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 197744370843998234L;

	/**
	 * @param ctx
	 * @param UNS_BillingTrf_ID
	 * @param trxName
	 */
	public MUNSBillingTrf(Properties ctx, int UNS_BillingTrf_ID, String trxName) {
		super(ctx, UNS_BillingTrf_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBillingTrf(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean beforeSave(boolean newRecord)
	{
		if(getAmountTrf().compareTo(getAmount()) == 1)
		{
			log.saveError("Error", "Over Amount Transferred");
			return false;
		}
		if(newRecord || is_ValueChanged(COLUMNNAME_DateFrom) || is_ValueChanged(COLUMNNAME_DateTo))
		{
			MSalesRegion sr = MSalesRegion.getOfOrg(getCtx(), getAD_Org_ID(), get_TrxName());
			String sql = "SELECT COALESCE(SUM(p.PayAmt),0) FROM C_Payment p"
					+ " INNER JOIN C_BankStatementLine bsl ON bsl.C_Payment_ID = p.C_Payment_ID"
					+ " INNER JOIN C_BankStatement bs ON bs.C_BankStatement_ID = bsl.C_BankStatement_ID"
					+ " WHERE EXISTS (SELECT 1 FROM AD_Org org WHERE org.AD_Org_ID = p.AD_Org_ID AND org.C_SalesRegion_ID =?)"
					+ " AND p.IsReceipt = 'Y' AND p.DocStatus IN ('CO', 'CL') AND p.DateTrx BETWEEN ? AND ?"
					+ " AND p.TenderType = 'X' AND EXISTS (SELECT 1 FROM C_BankAccount ba WHERE ba.C_BankAccount_ID ="
					+ " p.C_BankAccount_ID AND ba.BankAccountType = 'B') AND bs.DocStatus NOT IN ('VO', 'RE')";
			BigDecimal totalAmt = DB.getSQLValueBD(get_TrxName(), sql, new Object[]{sr.get_ID(), getDateFrom(), getDateTo()});
			setAmount(totalAmt);
		}
		
		setDifferenceAmt(getAmount().subtract(getAmountTrf()));
		
		return true;
	}
	
	public boolean afterSave(boolean newRecord, boolean success)
	{
		
		return true;
	}
	
	public boolean beforeDelete()
	{
		for(MUNSBillingTrfValidation tfv : getValidation())
		{
			if(!tfv.delete(false))
			{
				log.saveError("Error", CLogger.retrieveErrorString("Error delete lines"));
				return false;
			}
			
		}
		return true;
	}
	
	/**************************************************************************
	 * Process document
	 * 
	 * @param processAction
	 *            document action
	 * @return true if performed
	 */
	public boolean processIt(String processAction) {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	} // process

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	/**
	 * Unlock Document.
	 * 
	 * @return true if success
	 */
	public boolean unlockIt() {
		if (log.isLoggable(Level.INFO))
			log.info("unlockIt - " + toString());
		setProcessed(false);
		return true;
	} // unlockIt

	/**
	 * Invalidate Document
	 * 
	 * @return true if success
	 */
	public boolean invalidateIt() {
		if (log.isLoggable(Level.INFO))
			log.info("invalidateIt - " + toString());
		return true;
	} // invalidateIt

	/**
	 * Prepare Document
	 * 
	 * @return new status (In Progress or Invalid)
	 */
	public String prepareIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (getValidation().length == 0)
			throw new AdempiereUserError(
					"Please define validation for complete this document");
		
		boolean forceZeroDiff = MSysConfig.getBooleanValue(MSysConfig.ZERO_DIFFERENCE_ON_TRF_ALLOCATION, false);
		if(forceZeroDiff && getDifferenceAmt().compareTo(Env.ZERO) != 0)
		{
			setProcessed(false);
			m_processMsg = "Difference amount must be ZERO (0).";
			return DocAction.STATUS_Invalid;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		setProcessed(true);
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	} // prepareIt

	/**
	 * Complete Document
	 * 
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */

	public String completeIt() {
		if (DOCACTION_Prepare.equals(getDocAction())) {
			setProcessed(false);
			return DocAction.STATUS_InProgress;
		}
		// Re-Check
		if (!m_justPrepared) {
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (log.isLoggable(Level.INFO))
			log.info(toString());
		
		for(MUNSBillingTrfValidation tfv : getValidation())
		{
			if(!tfv.processIt(DocAction.ACTION_Complete) || !tfv.save())
			{
				m_processMsg = "Failed complete validations. " + tfv.getProcessMsg();
				return DocAction.STATUS_Invalid;
			}
		}

		// User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null) {
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		//
		setProcessed(true);
		setDocAction(ACTION_Close);
		return DocAction.STATUS_Completed;
	} // completeIt

	/**
	 * Void Document. Same as Close.
	 * 
	 * @return true if success
	 */
	public boolean voidIt() {
		if (log.isLoggable(Level.INFO))
			log.info("voidIt - " + toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		if (!closeIt())
			return false;
		
		for(MUNSBillingTrfValidation tfv : getValidation())
		{
			if(!tfv.processIt(DocAction.ACTION_Void) || !tfv.save())
			{
				m_processMsg = "Failed when trying void validations. " + tfv.getProcessMsg();
				return false;
			}
		}

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		setDocStatus(DOCSTATUS_Voided);
		return true;
	} // voidIt

	/**
	 * Close Document. Cancel not delivered Quantities
	 * 
	 * @return true if success
	 */
	public boolean closeIt() {
		if (log.isLoggable(Level.INFO))
			log.info("closeIt - " + toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		return true;
	} // closeIt

	/**
	 * Reverse Correction
	 * 
	 * @return true if success
	 */
	public boolean reverseCorrectIt() {
		if (log.isLoggable(Level.INFO))
			log.info("reverseCorrectIt - " + toString());
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

		return false;
	} // reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 * 
	 * @return true if success
	 */
	public boolean reverseAccrualIt() {
		if (log.isLoggable(Level.INFO))
			log.info("reverseAccrualIt - " + toString());
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
	} // reverseAccrualIt

	/**
	 * Re-activate
	 * 
	 * @return true if success
	 */
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO))
			log.info("reActivateIt - " + toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		int retval = MessageBox.showMsg(this, getCtx(), "Do you want to void all transfer transaction?", 
				"Reactivate confirmation.", MessageBox.YESNO, MessageBox.ICONQUESTION);
		if(MessageBox.RETURN_OK == retval || retval == MessageBox.RETURN_YES)
		{	
			for(MUNSBillingTrfValidation tfv : getValidation())
			{
				if(!tfv.processIt(DocAction.ACTION_ReActivate) || !tfv.save())
				{
					m_processMsg = "failed when trying reactivate validations. " + tfv.getProcessMsg();
					return false;
				}
			}
		}
		
		setProcessed(false);
		setDocAction(DOCACTION_Prepare);
		setDocStatus(DOCSTATUS_Drafted);
		saveEx();
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		return true;
	} // reActivateIt

	@Override
	public String getSummary()
	{
		DecimalFormat df = new DecimalFormat("#,###.00");
		StringBuilder sb = new StringBuilder();
		sb.append(getDateAcct());
		sb.append(": ").append("#: ");
		sb.append(" Total : ").append(df.format(getAmount()));
		sb.append(" Transfer : ").append(df.format(getAmountTrf()));
		sb.append(" Difference : ").append(df.format(getDifferenceAmt()));
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" #Description : ").append(getDescription());
		sb.append(" (#").append(getValidation().length).append(")");

		return sb.toString();
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return super.getC_Currency_ID();
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return getAmountTrf();
	}

	@Override
	public boolean approveIt() {
		if (log.isLoggable(Level.INFO)) log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		if (log.isLoggable(Level.INFO)) log.info("rejectIt - " + toString());
		setIsApproved(false);
		setProcessed(false);
		return true;
	}

	public MUNSBillingTrfValidation[] getValidation() {
		List<MUNSBillingTrfValidation> trfVal = new Query(getCtx(),
				MUNSBillingTrfValidation.Table_Name,
				COLUMNNAME_UNS_BillingTrf_ID + "=?", get_TrxName())
				.setParameters(get_ID()).list();

		return trfVal.toArray(new MUNSBillingTrfValidation[trfVal.size()]);
	}
	
	@Override
	public List<Object[]> getApprovalInfoColumnClassAccessable()
	{	
		List<Object[]> list = new ArrayList<>();
		list.add(new Object[]{String.class, true});
		list.add(new Object[]{String.class, true});
		list.add(new Object[]{String.class, true});
		list.add(new Object[]{BigDecimal.class, true});
		list.add(new Object[]{String.class, true});
		list.add(new Object[]{String.class, true});
		
		return list;
	}

	@Override
	public String[] getDetailTableHeader()
	{
		String def[] = new String[]{"Organisasi", "From", "To",
				"Amount", "Description", "Record"};	
		
		return def;
	}

	@Override
	public List<Object[]> getDetailTableContent()
	{	
		List<Object[]> list = new ArrayList<>();
		
		String sql = "SELECT Master.* FROM (SELECT org.Value AS Org, (baFrom.Value || '-' || baFrom.Name) AS From,"
				+ " (baTo.Value || '-' || baTo.Name) AS To, btv.AmountTrf AS Amount,"
				+ " COALESCE(btv.Description, '-') AS Description, btv.UNS_BillingTrfValidation_ID AS RecordID"
				+ " FROM UNS_BillingTrfValidation btv"
				+ " INNER JOIN C_BankAccount baFrom ON baFrom.C_BankAccount_ID = btv.AccountFrom_ID"
				+ " INNER JOIN C_BankAccount baTo ON baTo.C_BankAccount_ID = btv.AccountTo_ID"
				+ " INNER JOIN AD_Org org ON org.AD_Org_ID = btv.AD_Org_ID"
				+ " WHERE btv.UNS_BillingTrf_ID = ?"
				+ " UNION ALL"
				+ " SELECT us.Name, UPPER(bg.Name) AS From, (ba.Value || '-' || ba.Name) AS To, bg.GrandTotal AS Amount,"
				+ " COALESCE(bg.Description, '-'), 0 AS RecordID FROM UNS_CustomerBG_Action bga"
				+ " INNER JOIN UNS_CustomerBG bg ON bg.UNS_CustomerBG_ID = bga.UNS_CustomerBG_ID"
				+ " INNER JOIN C_BankAccount ba ON ba.C_BankAccount_ID = bga.C_BankAccount_ID"
				+ " LEFT JOIN AD_User us ON us.AD_User_ID = bg.SalesRep_ID"
				+ " INNER JOIN AD_Org org ON org.AD_Org_ID = bg.AD_Org_ID"
				+ " WHERE bga.DateDoc BETWEEN ? AND ? AND org.C_SalesRegion_ID ="
				+ " (SELECT orga.C_SalesRegion_ID FROM AD_Org orga WHERE orga.AD_Org_ID = ?)"
				+ " ) AS Master";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			st.setInt(1, get_ID());
			st.setTimestamp(2, getDateFrom());
			st.setTimestamp(3, getDateTo());
			st.setInt(4, getAD_Org_ID());
			rs = st.executeQuery();
			
			while (rs.next())
			{
				int count = 0;
				Object[] rowData = new Object[6];
				rowData[count] = rs.getObject("Org");
				rowData[++count] = rs.getObject("From");
				rowData[++count] = rs.getObject("To");
				rowData[++count] = rs.getObject("Amount");
				rowData[++count] = rs.getObject("Description");
				rowData[++count] = rs.getObject("RecordID");
				list.add(rowData);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, st);
		}
		
		return list;
	}
	
	@Override
	public boolean isShowAttachmentDetail() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
				String orderType, String isSOTrx, int AD_Table_ID,
					String[] docAction, String[] options, int index)
	{
		if(docStatus.equals(DocAction.STATUS_Completed))
		{
			options[index++] = DocAction.ACTION_Void;
			options[index++] = DocAction.ACTION_ReActivate;
		}	
		return index;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTableIDDetail() {
		// TODO Auto-generated method stub
		return MUNSBillingTrfValidation.Table_ID;
	}
}
