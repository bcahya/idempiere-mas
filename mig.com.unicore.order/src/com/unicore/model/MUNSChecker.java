/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Msg;

import com.uns.model.MUNSChargeRS;
import com.uns.util.MessageBox;

/**
 * @author ALBURHANY
 *
 */
public class MUNSChecker extends X_UNS_Checker implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3689837488416845914L;
	
	private MUNSChargeRS rs = null;
	/**
	 * @param ctx
	 * @param UNS_Checker_ID
	 * @param trxName
	 */
	public MUNSChecker(Properties ctx, int UNS_Checker_ID, String trxName) {
		super(ctx, UNS_Checker_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSChecker(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUNSChecker[");
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
		return Msg.getElement(getCtx(), "UNS_Checker_ID") + " " + getDocumentNo();
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
		if(!newRecord && is_ValueChanged(COLUMNNAME_DateDoc))
			if(getDateDoc().compareTo(getCreated()) == -1)
				throw new AdempiereException("Date Document not later than Date Created Document");
		
		if(is_ValueChanged(COLUMNNAME_CheckerType) && getCheckerType().equals("MC"))
			setAD_OrgTo_ID(-1);
		if(is_ValueChanged(COLUMNNAME_CheckerType) && getCheckerType().equals("BC"))
			setUNS_BillingGroup_ID(-1);
		
		if(newRecord || is_ValueChanged(COLUMNNAME_isDefinedCost))
		{
			if(isDefinedCost())
			{
				if(getUNS_Charge_RS_ID() > 0)
				{
					rs = new MUNSChargeRS(getCtx(), getUNS_Charge_RS_ID(), get_TrxName());
					if(getC_BPartner_ID() > 0 && getC_BPartner_ID() != rs.getC_BPartner_ID())
						throw new AdempiereUserError("No match business partner between this document and charge request");
					else
						setC_BPartner_ID(rs.getC_BPartner_ID());
					return true;
				}
				else
				{
					rs = new MUNSChargeRS(getCtx(), 0, get_TrxName());
					rs.setAD_Org_ID(getAD_Org_ID());
					rs.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_ChargeRequest));
					rs.setName(getName());
					rs.setC_BPartner_ID(getC_BPartner_ID());
					rs.setDateTrx(getDateDoc());
					rs.setDescription(getDescription());
					if(!rs.save())
						throw new AdempiereException("Failed when try creating document Charge Request");
					setUNS_Charge_RS_ID(rs.get_ID());
				}
			}
			
			if(getCheckerType().equals(X_UNS_Checker.CHECKERTYPE_BranchChecker) && getAD_OrgTo_ID() <= 0)
				throw new AdempiereException("Must define Inter-Organization for type Branch Checker");
			
			if(!isDefinedCost() && getUNS_Charge_RS_ID() > 0)
			{
				confirmDelete();
			}
		}
		return true;
	}	
	
	protected boolean afterSave (boolean newRecord, boolean success)
	{					
		return super.afterSave(newRecord, success);
	}
	protected boolean beforeDelete()
	{
		if(isDefinedCost())
			confirmDelete();
			
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
		
		rs = new MUNSChargeRS(getCtx(), getUNS_Charge_RS_ID(), get_TrxName());
		
		if(getCheckerType().equals(X_UNS_Checker.CHECKERTYPE_BranchChecker) && getLine().length == 0)
		{
			m_processMsg = Msg.getMsg("", "Must define line for type branch checker");
			return DocAction.STATUS_Invalid;
		}
		if(isDefinedCost())
		{
			if(rs.getLines(true).length == 0)
			{
				m_processMsg = Msg.getMsg("", "No Lines");
				return DocAction.STATUS_Invalid;
			}
			else
			{
				try {
					if(!rs.processIt(DOCACTION_Complete) && !rs.save())
						return DocAction.STATUS_Invalid;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
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
		
		if(getLine().length == 0 && getCheckerType().equals("BC"))
			throw new AdempiereException("Please define line for complete this document");
		
		if(getCheckerType().equals("BC"))
			createCheckerConfirm();
		
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
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
		
		if(isDefinedCost())
		{
			rs = new MUNSChargeRS(getCtx(), getUNS_Charge_RS_ID(), get_TrxName());
			if(rs.getDocStatus().equals(DOCSTATUS_InProgress)
					|| rs.getDocStatus().equals(DOCSTATUS_Completed)
					|| rs.getDocStatus().equals(DOCSTATUS_Closed))
			{
				m_processMsg = "Charge request has been proccess, please void first charge request document.";
				return false;
			}
		}
		
//		MUNSCheckerConfirmation cc = MUNSCheckerConfirmation.get
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
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		setIsApproved(false);
		return true;
	}

	public int customizeValidActions(String docStatus, Object processing,
				String orderType, String isSOTrx, int AD_Table_ID,
					String[] docAction, String[] options, int index)
	{
		if (docStatus.equals(DocAction.STATUS_Completed))
		{
			options[index++] = DocAction.ACTION_Void;
		}	
		return index;
	}
	public MUNSCheckerLine[] getLine()
	{
		List<MUNSCheckerLine> cl = new Query(getCtx(), MUNSCheckerLine.Table_Name, COLUMNNAME_UNS_Checker_ID + "=?",
				get_TrxName()).setParameters(get_ID()).list();
		
		return cl.toArray(new MUNSCheckerLine[cl.size()]);
	}
	
//	public boolean createCheckerResult()
//	{
//		MUNSCheckerResult cr = new MUNSCheckerResult(getCtx(), 0, get_TrxName());
//		
//		String sql = "SELECT UNS_BillingGroup_Result_ID FROM UNS_BillingGroup_Result"
//				+ " WHERE UNS_BillingGroup_ID = " + getUNS_BillingGroup_ID();
//		
//		cr.setAD_Org_ID(getAD_Org_ID());
//		cr.setUNS_Checker_ID(get_ID());
//		cr.setCheckerType(getCheckerType());
//		if(getCheckerType().equals("MC"))
//			cr.setUNS_BillingGroup_Result_ID(DB.getSQLValue(get_TrxName(), sql));
//		else if(getCheckerType().equals("BC"))
//			cr.setAD_OrgTo_ID(getAD_OrgTo_ID());
//		cr.setSalesRep_ID(getSalesRep_ID());
//		cr.setDateDoc(getDateDoc());
//		if(!cr.save())
//			throw new AdempiereException("Error ocurred when trying create Checker Result");
//		
//		return true;
//	}
	
	public boolean createCheckerConfirm()
	{
//		MUNSCheckerConfirmation cc = new MUNSCheckerConfirmation(getCtx(), 0, get_TrxName());
//		cc.setAD_Org_ID(getAD_OrgTo_ID());
//		cc.setUNS_Checker_ID(get_ID());
//		cc.setAD_OrgFrom_ID(getAD_Org_ID());
//		cc.setC_BPartner_ID(getC_BPartner_ID());
//		cc.setDateDoc(getDateDoc());
//		cc.setCheckerType(getCheckerType());
//		cc.setDescription(getDescription());
//		cc.setSalesRep_ID(getSalesRep_ID());
//		if(!cc.save())
//			throw new AdempiereException("Error ocurred when trying create Checker Confirmation");
//		
//		for(MUNSCheckerLine cl : getLine())
//		{
//			MUNSCheckerConfirmLine confirm = new MUNSCheckerConfirmLine(getCtx(), 0, get_TrxName());
//			
//			confirm.setUNS_CheckerConfirmation_ID(cc.get_ID());
//			confirm.setUNS_CheckerLine_ID(cl.get_ID());
//			confirm.setDescription(cl.getDescription());
//			if(!confirm.save())
//				throw new AdempiereException("Error ocurred when trying create Checker Confirmation Line");
//		}
		MUNSCheckerConfirmation cc = new MUNSCheckerConfirmation(this);
		cc.save();
		return true;
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
	
	public boolean confirmDelete()
	{
		String msg = Msg.getMsg(getCtx(), "Do you want to delete Charge Request also ?");
		String title = Msg.getMsg(getCtx(), "Confirm action");
		int retVal = MessageBox.showMsg(this,
				getCtx()
				, msg
				, title
				, MessageBox.YESNO
				, MessageBox.ICONQUESTION);
		if(retVal == MessageBox.RETURN_NO || retVal == MessageBox.RETURN_NONE)
			return true;
		
		if(retVal == MessageBox.RETURN_OK || retVal == MessageBox.RETURN_YES)
		{				
			deleteRS();
		}
		
		return true;
	}
}