/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Msg;

/**
 * @author ALBURHANY
 *
 */
public class MUNSCheckerConfirmation extends X_UNS_CheckerConfirmation implements DocAction {
	
	private MUNSCheckerConfirmLine m_CheckerConfirmLine = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7619974476053441200L;

	/**
	 * @param ctx
	 * @param UNS_CheckerConfirmation_ID
	 * @param trxName
	 */
	public MUNSCheckerConfirmation(Properties ctx,
			int UNS_CheckerConfirmation_ID, String trxName) {
		super(ctx, UNS_CheckerConfirmation_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCheckerConfirmation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUNSCheckerConfirmatio[");
		sb.append(get_ID()).append("-").append(getDocumentNo())
			.append(",Status=").append(getDocStatus()).append(",Action=").append(getDocAction())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 * 	Get Document Info
	 *	@return document info
	 */
	public String getDocumentInfo()
	{
		return Msg.getElement(getCtx(), "UNS_CheckerConfirmation_ID") + " " + getDocumentNo();
	}	//	getDocumentInfo
	
	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	public File createPDF ()
	{
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{		
		
		if(is_ValueChanged(COLUMNNAME_UNS_Checker_ID))
		{
			String sql = "DELETE FROM UNS_CheckerConfirm_Line WHERE UNS_CheckerConfirmation_ID=?";
			DB.executeUpdate(sql, get_ID(), get_TrxName());
		}
		return true;
	}	
	
	protected boolean afterSave (boolean newRecord, boolean success)
	{		
//		if(is_ValueChanged(COLUMNNAME_UNS_Checker_ID))
//		{
//			if(getUNS_Checker_ID() > 0)
//			{
//				MUNSChecker c = new MUNSChecker(getCtx(), getUNS_Checker_ID(), get_TrxName());
//				for(MUNSCheckerLine cl : c.getLine())
//				{
//					m_CheckerConfirmLine = new MUNSCheckerConfirmLine(getCtx(), 0, get_TrxName());
//					m_CheckerConfirmLine.setAD_Org_ID(getAD_Org_ID());
//					m_CheckerConfirmLine.setUNS_CheckerConfirmation_ID(get_ID());
//					m_CheckerConfirmLine.setUNS_CheckerLine_ID(cl.get_ID());
//					m_CheckerConfirmLine.setDescription(cl.getDescription());
//					m_CheckerConfirmLine.saveEx();
//				}
//			}
//		}
		
		if(newRecord || is_ValueChanged(COLUMNNAME_UNS_Checker_ID))
		{
			MUNSChecker c = new MUNSChecker(getCtx(), getUNS_Checker_ID(), get_TrxName());
			
			for(MUNSCheckerLine cl : c.getLine())
			{
				MUNSCheckerConfirmLine ccl = new MUNSCheckerConfirmLine(cl, get_ID());
				if(!ccl.save())
					throw new AdempiereUserError("Failed when trying create job dutty confirmation detail");
			}
		}
		return super.afterSave(newRecord, success);
	}
	protected boolean beforeDelete()
	{
		
		return true;
	}

	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (processAction, getDocAction());
	}	//	process
	
	/**	Process Message 			*/
	private String			m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean 		m_justPrepared = false;

	/**
	 * 	Unlock Document.
	 * 	@return true if success 
	 */
	public boolean unlockIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("unlockIt - " + toString());
		setProcessed(false);
		return true;
	}	//	unlockIt
	
	/**
	 * 	Invalidate Document
	 * 	@return true if success 
	 */
	public boolean invalidateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("invalidateIt - " + toString());
		return true;
	}	//	invalidateIt
	
	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid) 
	 */
	public String prepareIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}	//	prepareIt
	
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	
	public String completeIt()
	{
//		if (DOCACTION_Prepare.equals(getDocAction()))
//		{
//			setProcessed(false);
//			return DocAction.STATUS_InProgress;
//		}
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		if(null != createCheckerResult())
			return DOCACTION_Invalidate;
		
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		//
		setProcessed(true);
		setDocAction(ACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt

	/**
	 * 	Void Document.
	 * 	Same as Close.
	 * 	@return true if success 
	 */
	public boolean voidIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("voidIt - " + toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		if (!closeIt())
			return false;
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	voidIt
	
	/**
	 * 	Close Document.
	 * 	Cancel not delivered Quantities
	 * 	@return true if success 
	 */
	public boolean closeIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("closeIt - " + toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	closeIt
	
	/**
	 * 	Reverse Correction
	 * 	@return true if success 
	 */
	public boolean reverseCorrectIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reverseCorrectIt - " + toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	reverseCorrectionIt
	
	/**
	 * 	Reverse Accrual - none
	 * 	@return true if success 
	 */
	public boolean reverseAccrualIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reverseAccrualIt - " + toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;				
		
		return false;
	}	//	reverseAccrualIt
	
	/** 
	 * 	Re-activate
	 * 	@return true if success 
	 */
	public boolean reActivateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reActivateIt - " + toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
				
		setProcessed(false);
		setDocAction(DOCACTION_Prepare);
		setDocStatus(DOCSTATUS_Drafted);
		saveEx();
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	reActivateIt

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
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
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean approveIt() {
		
		return true;
	}

	@Override
	public boolean rejectIt() {
		
		return true;
	}
	
	public MUNSCheckerConfirmLine[] getLine(int UNS_CheckerConfirmation_ID)
	{
		List<MUNSCheckerConfirmLine> m_CheckerConfirmLine = new Query(getCtx(), MUNSCheckerConfirmLine.Table_Name,
				COLUMNNAME_UNS_CheckerConfirmation_ID + "=?", get_TrxName()).setParameters(UNS_CheckerConfirmation_ID)
				.list();
		
		return m_CheckerConfirmLine.toArray(new MUNSCheckerConfirmLine[m_CheckerConfirmLine.size()]);
	}
	
	public String createCheckerResult()
	{
		m_processMsg = null;
		MUNSCheckerResult cr = new MUNSCheckerResult(getCtx(), 0, get_TrxName());
		
		cr.setAD_Org_ID(getAD_Org_ID());
		cr.setUNS_Checker_ID(get_ID());
		cr.setCheckerType(getCheckerType());
		cr.setAD_OrgTo_ID(getAD_OrgFrom_ID());
		cr.setC_BPartner_ID(getC_BPartner_ID());
		cr.setSalesRep_ID(getSalesRep_ID());
		cr.setDateDoc(getDateDoc());
		if(!cr.save())
			m_processMsg = "Error ocurred when trying create Checker Result";
		
		return m_processMsg;
	}
	
	public static MUNSCheckerConfirmation getOfChecker(Properties ctx, int UNS_Checker_ID, String trxName)
	{
		MUNSCheckerConfirmation cc = null;
		
		cc = new Query(ctx, Table_Name, COLUMNNAME_UNS_Checker_ID + "=? AND " + COLUMNNAME_DocStatus
						+ " IN (?, ?, ?)", trxName)
						.setParameters(UNS_Checker_ID, DOCSTATUS_InProgress, DOCSTATUS_Completed, DOCSTATUS_Closed)
						.first();
		
		if(cc.get_ID() > 0)
			cc = null;
		
		return cc;
	}
	
	public MUNSCheckerConfirmation(MUNSChecker checker)
	{
		this(checker.getCtx(), 0, checker.get_TrxName());
		
		setAD_Org_ID(checker.getAD_OrgTo_ID());
		setUNS_Checker_ID(checker.get_ID());
		setAD_OrgFrom_ID(checker.getAD_Org_ID());
		setC_BPartner_ID(checker.getC_BPartner_ID());
		setDateDoc(checker.getDateDoc());
		setCheckerType(checker.getCheckerType());
		setDescription(checker.getDescription());
		setSalesRep_ID(checker.getSalesRep_ID());
	}
	
	public static void main (String [] args)
	{
		int x = 5;
		for(Integer i = 1; i <= x;)
		{
			String print = null;
			print = i.toString() + " ";
			if(i >= 2)
			{
				for(int itwo = 1; itwo < i; itwo++)
				{
					print = print + i.toString() + " ";
				}
			}
			System.out.println(print);
			i++;
		}
		
		for(int i = 0; i <= x; i++)
		{
			if(1 == i)
			{
				System.out.println(i);
				continue;
			}
		}
	}
}