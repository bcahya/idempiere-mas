/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Msg;

import com.uns.model.MUNSChargeRS;

/**
 * @author ALBURHANY
 *
 */
public class MUNSCheckerResult extends X_UNS_Checker_Result implements DocAction {

	private MUNSChargeRS rs = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3039706319227141657L;

	/**
	 * @param ctx
	 * @param UNS_Checker_Result_ID
	 * @param trxName
	 */
	public MUNSCheckerResult(Properties ctx, int UNS_Checker_Result_ID,
			String trxName) {
		super(ctx, UNS_Checker_Result_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCheckerResult(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUNSCheckerResult[");
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
		return Msg.getElement(getCtx(), "UNS_Checker_Result_ID") + " " + getDocumentNo();
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
		MUNSChecker c = new MUNSChecker(getCtx(), getUNS_Checker_ID(), get_TrxName());
		
		if(is_ValueChanged(COLUMNNAME_isDefinedCost) || newRecord)
		{
			if(!isDefinedCost() && c.isDefinedCost())
				throw new AdempiereException("Not define cost disallowed, reference has defined cost");
		}
		if(getUNS_Charge_RS_ID() <= 0  && isDefinedCost())
			createSettlement();
		
		if(c.getLine().length == 0)
			return true;
		else
		{
			for(MUNSCheckerLine cLine : c.getLine())
			{
				createLine(cLine);
			}
		}
		
		if(isDefinedCost())
		{
			rs = new MUNSChargeRS(getCtx(), 0, get_TrxName());
			if(c.isDefinedCost())
				rs.setReference_ID(c.getUNS_Charge_RS_ID());
			rs.setC_BPartner_ID(c.getC_BPartner_ID());
			rs.setDateTrx(getDateDoc());
			rs.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_Settlement));
			if(!rs.save())
				throw new AdempiereException("Failed when try creating Charge Settlement");
		}
		
		if(!isDefinedCost() && getUNS_Charge_RS_ID() > 0)
		{
			deleteRS();
		}
		return true;
	}	
	
	protected boolean afterSave (boolean newRecord, boolean success)
	{				
		return super.afterSave(newRecord, success);
	}
	protected boolean beforeDelete()
	{
		deleteRS();
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
		
		if(isDefinedCost())
		{
			rs = new MUNSChargeRS(getCtx(), getUNS_Charge_RS_ID(), null);
			if(!rs.isComplete())
			{
				rs.prepareIt();
				rs.saveEx();
			}
		}
		
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
	
	public static MUNSCheckerResult getByChecker(Properties ctx, int UNS_Chekcer_ID, String trxName)
	{
		MUNSCheckerResult cr = new Query(ctx, MUNSCheckerResult.Table_Name, COLUMNNAME_UNS_Checker_ID + "=?",
				trxName).setParameters(UNS_Chekcer_ID).first();
		
		return cr;
	}
	
	public String createLine (MUNSCheckerLine cLine)
	{
		MUNSCheckerLineResult lr = new MUNSCheckerLineResult(cLine.getCtx(), 0, cLine.get_TrxName());
		
		lr.setUNS_Checker_Result_ID(get_ID());
		lr.setUNS_CheckerLine_ID(cLine.get_ID());
		lr.setDescription(cLine.getDescription());
		lr.saveEx();
		
		return "Line has created.";
	}
	
	public String createSettlement()
	{
		rs = new MUNSChargeRS(getCtx(), 0, get_TrxName());
		rs.setAD_Org_ID(getAD_Org_ID());
		rs.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_Settlement));
		rs.setName(getName());
		rs.setC_BPartner_ID(getC_BPartner_ID());
		rs.setDateTrx(getDateDoc());
		rs.setDescription(getDescription());
		if(!rs.save())
			return "Failed when try creating document Settlement";
		setUNS_Charge_RS_ID(rs.get_ID());
		
		return "Document settlement (" + rs.getDocumentNo() + ") has created";
	}
	
	public boolean deleteRS()
	{
		if(getUNS_Charge_RS_ID() > 0)
		{
			rs = new MUNSChargeRS(getCtx(), getUNS_Charge_RS_ID(), get_TrxName());
			setUNS_Charge_RS_ID(-1);
			if(!rs.delete(false, get_TrxName()))
				throw new AdempiereException("Charge Request has processed, cannot modify this record");
		}
		return true;
	}
	
	
}