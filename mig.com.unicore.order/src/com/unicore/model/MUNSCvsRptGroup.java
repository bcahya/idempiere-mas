/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.model.MUNSBPCanvasser;
import com.uns.base.model.Query;
import com.uns.util.MessageBox;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author Burhani Adam
 *
 */
public class MUNSCvsRptGroup extends X_UNS_Cvs_RptGroup implements DocAction, DocOptions {

	private String m_processMsg = null;
	private boolean isAutoComplete = false;
	private boolean isAutoCalculate = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4608544194842282083L;

	/**
	 * @param ctx
	 * @param UNS_Cvs_RptGroup_ID
	 * @param trxName
	 */
	public MUNSCvsRptGroup(Properties ctx, int UNS_Cvs_RptGroup_ID,
			String trxName) {
		super(ctx, UNS_Cvs_RptGroup_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCvsRptGroup(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean beforeDelete()
	{
		for(MUNSCvsRpt rpt : getReports())
		{
			rpt.deleteEx(false, get_TrxName());
		}
		
		return true;
	}
	
	@Override
	public boolean processIt(String action) throws Exception 
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	@Override
	public boolean unlockIt() 
	{
		log.info(toString());
		return true;
	}

	@Override
	public boolean invalidateIt() 
	{
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}
	
	private boolean m_justPrepared = false;
	@Override
	public String prepareIt() 
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}
		m_justPrepared = true;

		if (!DOCACTION_Complete.equals(getDocAction()))
		{
			setDocAction(DOCACTION_Complete);
		}
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() 
	{
		log.info(toString());
		if (!isApproved())
		{
			setIsApproved(true);
		}
		return true;
	}

	@Override
	public boolean rejectIt() 
	{
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() 
	{
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}
		
		log.info(toString());

		if (!m_justPrepared) 
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}
		
		String msg = "Do you want to calculate discount?";
		int answer = MessageBox.showMsg(this, getCtx(), msg, 
				"Calculate Discount ?", MessageBox.YESNO, 
				MessageBox.ICONQUESTION);
		if (answer == MessageBox.RETURN_YES)
		{
			isAutoCalculate = true;
		}
		
		String message = "Automaticaly complete Invoice Document ? ";
		int retVal = MessageBox.showMsg(this, getCtx(), message, 
				"Auto Complete Confirmation"
				, MessageBox.YESNO, MessageBox.ICONWARNING);
		
		if(retVal == MessageBox.RETURN_YES)
		{
			isAutoComplete = true;
		}
		
		for(MUNSCvsRpt rpt : getReports())
		{
			try
			{
				rpt.setCalcDisc(isAutoCalculate);
				rpt.setAutoComplete(isAutoComplete);
				rpt.setConsolidate(true);
				rpt.processIt(DocAction.ACTION_Complete);				
				rpt.saveEx();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		if(isAutoComplete)
		{
			MUNSBPCanvasser cvsInfo = MUNSBPCanvasser.getOf(getC_BPartner_ID(),
					get_TrxName());
			cvsInfo.setLastReport(getDateDoc());
			cvsInfo.saveEx();
			
			if(cvsInfo.getQtyOnHand().signum() != 0 
					&& cvsInfo.isMustReturnRemainingItems())
			{
				m_processMsg = "Please return remaining items into warehouse"
						+ " before completing this document.";
				return DocAction.STATUS_Invalid;
			}
		}
		
		analyzeCompleteLines();
		if(!isFullyCompleteLines)
		{
			m_processMsg = "Please complete all lines, for complete this document";
			return DocAction.STATUS_InProgress;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		if (DOCSTATUS_Closed.equals(getDocStatus())
				|| DOCSTATUS_Reversed.equals(getDocStatus())
				|| DOCSTATUS_Voided.equals(getDocStatus())) {
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}

		for(MUNSCvsRpt rpt : getReports())
		{
			try
			{
				rpt.setConsolidate(true);
				rpt.processIt(DocAction.ACTION_Void);
				rpt.saveEx();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		setDocStatus(DOCSTATUS_Voided); 
		saveEx();

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public boolean closeIt() {
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		return false;
	}

	@Override
	public boolean reActivateIt()
	{
		for(MUNSCvsRpt rpt : getReports())
		{
			try
			{
				rpt.setConsolidate(true);
				rpt.processIt(DocAction.ACTION_ReActivate);
				rpt.saveEx();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		setProcessed(false);
		setDocStatus(DOCSTATUS_Drafted);
		setDocAction(DOCACTION_Complete);
		return true;
	}

	@Override
	public String getSummary() {
		return (new StringBuilder("Canvas Report Group ").append(getDocumentNo()))
				.toString();
	}

	@Override
	public String getDocumentInfo() {
		return getSummary();
	}

	@Override
	public File createPDF() {
		return null;
	}

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return Env.ZERO;
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		if(docStatus.equals(DOCSTATUS_Completed))
		{
			options[index++] = DocAction.ACTION_ReActivate;
		}
		return index;
	}
	
	protected MUNSCvsRpt[] getReports()
	{
		List<MUNSCvsRpt> reports =
				Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSCvsRpt.Table_Name,
						COLUMNNAME_UNS_Cvs_RptGroup_ID + "=?", get_TrxName()).setParameters(get_ID())
						.list(); 
		return reports.toArray(new MUNSCvsRpt[reports.size()]);
	}
	
	private boolean isFullyCompleteLines = true;
	private void analyzeCompleteLines()
	{
		String sql = "SELECT COUNT(*) FROM UNS_Cvs_Rpt WHERE UNS_Cvs_RptGroup_ID=? AND DocStatus NOT IN ('CO', 'CL')";
		int count = DB.getSQLValue(get_TrxName(), sql, get_ID());
		if(count > 0)
		{
			isFullyCompleteLines = false;
		}
		
	}
}