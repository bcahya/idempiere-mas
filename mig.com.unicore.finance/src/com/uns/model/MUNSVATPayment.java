/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MPayment;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSFinanceModelFactory;

/**
 * @author Burhani Adam
 *
 */
public class MUNSVATPayment extends X_UNS_VATPayment implements DocAction, DocOptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = 422647828581810901L;

	/**
	 * @param ctx
	 * @param UNS_VATPayment_ID
	 * @param trxName
	 */
	public MUNSVATPayment(Properties ctx, int UNS_VATPayment_ID, String trxName) {
		super(ctx, UNS_VATPayment_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSVATPayment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSVAT[] getVATs(String whereClause, String orderClause) 
	{
		StringBuilder whereClauseFinal = new StringBuilder(MUNSVAT.COLUMNNAME_UNS_VATPayment_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(" AND ").append(whereClause);

		if (orderClause == null || orderClause.length() == 0)
			orderClause = MUNSVAT.COLUMNNAME_UNS_VAT_ID;
		
		List<MUNSVAT> list =
				Query.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID, MUNSVAT.Table_Name,
						whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID()).setOrderBy(orderClause)
						.list();

		return list.toArray(new MUNSVAT[list.size()]);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		m_processMsg = validatePrevRecord();
		if(!Util.isEmpty(m_processMsg, true) && !isProcessed())
		{
			log.saveError("Error", "Please complete or void other record (" + m_processMsg + ")");
			return false;
		}
		
		//getprevBallance
		if(getPrev_VATPayment_ID() <= 0)
		{
			String sql = "SELECT UNS_VATPayment_ID FROM UNS_VATPayment WHERE"
					+ " DocStatus IN ('CO','CL') AND DateTrx <= ? AND"
					+ " UNS_VATPayment_ID <> ? AND AD_Org_ID = ? ORDER BY DateTrx DESC, Created DESC";
			int prevID = DB.getSQLValue(get_TrxName(), sql, getDateTrx(), getUNS_VATPayment_ID(), getAD_Org_ID());
			if(prevID > 0)
				setPrev_VATPayment_ID(prevID);
		}
		
		if(getPrev_VATPayment_ID() > 0)
		{
			String sql = "SELECT Balance FROM UNS_VATPayment WHERE"
				+ " UNS_VATPayment_ID = ?";
			BigDecimal prevBallance = DB.getSQLValueBD(get_TrxName(), sql, getPrev_VATPayment_ID());
			setPrevBalanceAmt(prevBallance);
		}
		
		if(!is_ValueChanged(COLUMNNAME_PayAmt) && !isProcessed())
		{
			setPayAmt(getPrevBalanceAmt().add(getCurrentDiff()));
		}
		
		BigDecimal totalCurBlnc = getPrevBalanceAmt().add(getCurrentDiff());
		setTotalCurBallance(totalCurBlnc);
		setBalance(totalCurBlnc.subtract(getPayAmt()));
		
		return super.beforeSave(newRecord);
	}
	
	protected boolean beforeDelete()
	{
		String sql = "UPDATE UNS_VAT SET UNS_VATPayment_ID = NULL"
				+ " WHERE UNS_VATPayment_ID = ?";
		DB.executeUpdate(sql, get_ID(), false, get_TrxName());
		
		return true;
	}
	
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUNSVATPayment[");
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
		return Msg.getElement(getCtx(), "UNS_VATPayment_ID") + " " + getDocumentNo();
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
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}	//	prepareIt
	
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	
	public String completeIt()
	{
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
//		MPayment pay = null;
//		
//		if(getPayAmt().signum() > 0)
//		{
//			if(getC_Payment_ID() > 0)
//				pay = new MPayment(getCtx(), getC_Payment_ID(), get_TrxName());
//			else
//			{
//				pay = new MPayment(getCtx(), 0, get_TrxName());
//				pay.setAD_Org_ID(getAD_Org_ID());
//				pay.setC_DocType_ID(false);
//				pay.setC_BankAccount_ID(getC_BankAccount_ID());
//				pay.setC_BPartner_ID(getC_BPartner_ID());
//				pay.setDateTrx(getDateTrx());
//				pay.setDateAcct(getDateTrx());
//				pay.setDescription("**Auto Generated from VAT Payment #" + getDocumentNo() + "#**");
//				pay.setC_Charge_ID(getC_Charge_ID());
//				pay.setTenderType(getTenderType());
//				if(getTenderType().equals(X_C_Payment.TENDERTYPE_Check))
//					pay.setCheckNo(getCheckNo());
//				pay.setPayAmt(getPayAmt());
//				if(!pay.save())
//				{
//					m_processMsg = pay.getProcessMsg();
//					return DocAction.STATUS_Invalid;
//				}
//				
//			}
//			
//			if(!pay.isComplete())
//			{
//				String msg = "Payment has created (" + pay.getDocumentNo() + ").\n"
//						+ " Do you want to complete payment?";
//				String title = Msg.getMsg(getCtx(), "Confirm action");
//				int retVal = MessageBox.showMsg(this,
//						getCtx()
//						, msg
//						, title
//						, MessageBox.YESNO
//						, MessageBox.ICONQUESTION);
//				if(retVal == MessageBox.RETURN_YES || retVal == MessageBox.RETURN_OK)
//				{
//					if(!pay.processIt(DOCACTION_Complete) || !pay.save())
//					{
//						m_processMsg = pay.getProcessMsg();
//						return DocAction.STATUS_Invalid;
//					}
//				}	
//			}
//		}
		
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

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
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		if(getC_Payment_ID() > 0)
		{
			MPayment pay = new MPayment(getCtx(), getC_Payment_ID(), get_TrxName());
			if(!pay.processIt(DOCACTION_Void) && !pay.save())
			{
				m_processMsg = pay.getProcessMsg();
				return false;
			}
		}
		setDocStatus(DOCSTATUS_Voided);
		setProcessed(true);
		setDocAction(DocAction.ACTION_None);
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
		
		setDocStatus(DOCSTATUS_Closed);
		setDocAction(DocAction.ACTION_None);
		setProcessed(true);
		
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

		return voidIt();
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
		
		return voidIt();
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
		
		if(getC_Payment_ID() > 0)
		{
			MPayment pay = new MPayment(getCtx(), getC_Payment_ID(), get_TrxName());
			if(!pay.processIt(DOCACTION_Void) || !pay.save())
			{
				m_processMsg = pay.getProcessMsg();
				return false;
			}
		}
		setC_Payment_ID(-1);
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
		return getDocumentInfo();
	}

	@Override
	public String getProcessMsg() {
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
		return super.getPayAmt();
	}

	@Override
	public boolean approveIt() {
		setIsApproved(true);
		setProcessed(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		setIsApproved(false);
		setProcessed(false);
		return true;
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index)
	{
		if (docStatus.equals(DocAction.STATUS_Completed))
		{
			options[index++] = DocAction.ACTION_ReActivate;
			options[index++] = DocAction.ACTION_Void;
		}
		return index;
	}
	
	private String validatePrevRecord()
	{
		String sql = "SELECT ARRAY_TO_STRING(ARRAY_AGG(vp.DocumentNo), ';')"
				+ " FROM UNS_VATPayment vp WHERE vp.DocStatus NOT IN ('CO', 'CL', 'VO', 'RE')"
				+ " AND vp.AD_Org_ID = ? AND vp.UNS_VATPayment_ID <> ?";
		String list = DB.getSQLValueString(get_TrxName(), sql, getAD_Org_ID(), get_ID());
		
		return list;
	}
}	