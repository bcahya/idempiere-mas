/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import com.unicore.ui.ISortTabRecord;
import com.uns.util.MessageBox;

/**
 * @author Burhani Adam
 *
 */
public class MUNSVAT extends X_UNS_VAT implements DocAction, ISortTabRecord, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1149701744108559971L;

	/**
	 * @param ctx
	 * @param UNS_VAT_ID
	 * @param trxName
	 */
	public MUNSVAT(Properties ctx, int UNS_VAT_ID, String trxName) {
		super(ctx, UNS_VAT_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSVAT(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean beforeDelete()
	{
		upHeader();
		for(MUNSVATLine line : MUNSVATLine.getByParent(getCtx(), get_ID(), get_TrxName()))
		{
			line.delete(false);
		}
		
		return true;
	}
	
	public boolean beforeSave(boolean newRecord)
	{
		if(!newRecord && (is_ValueChanged(COLUMNNAME_IsSOTrx) || is_ValueChanged(COLUMNNAME_isCreditMemo)))
		{
			MUNSVATLine[] lines = MUNSVATLine.getByParent(getCtx(), get_ID(), get_TrxName());
			if(lines.length > 0)
			{
				int retVal = MessageBox.showMsg(this, getCtx(),
						"Changes to Sales Transaction OR Credit Memo"
						+ " will delete existing record. Continue?", "Delete Confirmation.",
						MessageBox.YESNO, MessageBox.ICONQUESTION);
				if(retVal == MessageBox.RETURN_YES || MessageBox.OK == retVal)
				{
					for(int i = 0; i < lines.length; i++)
						lines[i].delete(false, get_TrxName());
				}
				else
					return false;
			}
		}
		return true;
	}
	
	public String beforeSaveTabRecord (int parentRecord_ID)
	{
		if(getUNS_VATPayment_ID() <= 0)
		{
			upHeader();
		}
		return null;
	}
	
	private boolean upHeader()
	{
		if(get_ValueOldAsInt(COLUMNNAME_UNS_VATPayment_ID) > 0 || getUNS_VATPayment_ID() > 0)
		{
			MUNSVATPayment vp = new MUNSVATPayment(getCtx(),
				getUNS_VATPayment_ID() <= 0 ? get_ValueOldAsInt(COLUMNNAME_UNS_VATPayment_ID) : getUNS_VATPayment_ID(),
						get_TrxName());
			
			String add = "SELECT COALESCE(SUM(CASE WHEN l.isReplacement = 'Y' THEN l.DifferenceTaxAmt"
					+ " ELSE l.RevisionTaxAmt END),0) FROM UNS_VATLine l WHERE EXISTS"
					+ " (SELECT 1 FROM UNS_VAT v WHERE v.UNS_VAT_ID = l.UNS_VAT_ID"
					+ " AND v.UNS_VATPayment_ID = ? AND ((v.IsSOTrx = 'Y' AND v.IsCreditMemo = 'N')"
					+ " OR (v.IsSOTrx = 'N' AND v.IsCreditMemo = 'Y')))";
			BigDecimal addAmt = DB.getSQLValueBD(get_TrxName(), add, vp.get_ID());
			String sub = "SELECT COALESCE(SUM(CASE WHEN l.isReplacement = 'Y' THEN l.DifferenceTaxAmt"
					+ " ELSE l.RevisionTaxAmt END),0) FROM UNS_VATLine l WHERE EXISTS"
					+ " (SELECT 1 FROM UNS_VAT v WHERE v.UNS_VAT_ID = l.UNS_VAT_ID"
					+ " AND v.UNS_VATPayment_ID = ? AND ((v.IsSOTrx = 'N' AND v.IsCreditMemo = 'N')"
					+ " OR (v.IsSOTrx = 'Y' AND v.IsCreditMemo = 'Y')))";
			BigDecimal subAmt = DB.getSQLValueBD(get_TrxName(), sub, vp.get_ID());
			
			addAmt = addAmt.subtract(subAmt);
			vp.setCurrentDiff(addAmt);
			return vp.save();
		}
		
		return true;
	}
	
	public boolean afterSave(boolean newRecord, boolean success)
	{
		return upHeader();
	}	
	
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUNSVAT[");
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
		return Msg.getElement(getCtx(), "UNS_VAT_ID") + " " + getDocumentNo();
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
		
		String sql = "DELETE FROM Fact_Acct WHERE AD_Table_ID = ? AND Record_ID = ?";
		if(DB.executeUpdate(sql, new Object[]{Table_ID, get_ID()}, false, get_TrxName()) < 0)
		{
			m_processMsg = "failed when trying clear accounting.";
			return false;
		}
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
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
		
		String sql = "DELETE FROM Fact_Acct WHERE AD_Table_ID = ? AND Record_ID = ?";
		if(DB.executeUpdate(sql, new Object[]{Table_ID, get_ID()}, false, get_TrxName()) < 0)
		{
			m_processMsg = "failed when trying clear accounting.";
			return false;
		}
		setPosted(false);
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
		return getRevisionTaxAmt();
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
	public String beforeRemoveSelection() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isIncomeTax()
	{
		return (isSOTrx() && isCreditMemo()) || (!isSOTrx() && !isCreditMemo());
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index)
	{
		if(docStatus.equals(DocAction.STATUS_Completed))
		{
			options[index++] = DocAction.ACTION_ReActivate;
		}
		return index;
	}
}