/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

import com.uns.util.ErrorMsg;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan, <br>modified ITD-Andy
 * @reviewed, @modified : AzHaidar
 *
 */
public class MUNSSP extends X_UNS_SP implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1118049611600859232L;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;

	/**
	 * @param ctx
	 * @param UNS_SP_ID
	 * @param trxName
	 */
	public MUNSSP(Properties ctx, int UNS_SP_ID, String trxName) {
		super(ctx, UNS_SP_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSP(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		// TODO Auto-generated method stub
		
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
    			|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
    		options[index++] = DocumentEngine.ACTION_Prepare;
    	}
    	
    	// If status = Completed, add "Reactivte" in the list
    	if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
    		options[index++] = DocumentEngine.ACTION_Reverse_Correct;
    		options[index++] = DocumentEngine.ACTION_Void;
    	}   	
    		
    	return index;
	}

	@Override
	public boolean processIt(String action) throws Exception {
		// TODO Auto-generated method stub
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
			m_justPrepared = true;
		
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());
		
//		Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if(getSP().equals(SP_SuratPeringatan3))
		{
			m_processMsg = cutContract();
			if(null != m_processMsg)
				return DocAction.STATUS_Invalid;
		}
		
		setProcessed(true);	
		approveIt();
		//m_processMsg = info.toString();
	
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return super.getDocumentNo();
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
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return getAD_Org_ID();
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
	
	private MUNSSP getLatestSPOfEmployee()
	{
		String whereClause = COLUMNNAME_ValidTo + " = (" 
					+ " SELECT MAX("	+ COLUMNNAME_ValidTo + ")" 
					+ " FROM " + Table_Name 
					+ " WHERE " + COLUMNNAME_UNS_Employee_ID + " = " + getUNS_Employee_ID() 
					+ " AND " + COLUMNNAME_UNS_SP_ID + " <> " + getUNS_SP_ID() 
				+ " )"
				+ " AND " + COLUMNNAME_UNS_Employee_ID + " = " + getUNS_Employee_ID()
				+ " AND " + COLUMNNAME_DocStatus + "='" + DOCSTATUS_Completed + "'";

		MUNSSP latestSP = Query.get(getCtx(), UNSHRMModelFactory.getExtensionID(), 
				Table_Name, whereClause, get_TrxName()).first();
		
		return latestSP;
	}
	
	private Integer getSPNumber(String sp)
	{
		return new Integer(sp.substring(2, 3));
	}
	
	private boolean isValidSP()
	{
		MUNSSP latestSP = getLatestSPOfEmployee();
		if(null == latestSP){
			if(isForceToViolatesUnAcceptable() || getSP().equals(SP_SuratPeringatan1))
				return true;
			
			ErrorMsg.setErrorMsg(getCtx(), "SaveError", "Invalid SP!");
			return false;
		}
			
		int currentSPNumber = getSPNumber(getSP());
		int latestSPNumber = getSPNumber(latestSP.getSP());
		
		if(getValidFrom().compareTo(latestSP.getValidTo())<=0)
		{
			if(currentSPNumber<=latestSPNumber || currentSPNumber!=latestSPNumber+1)
			{
				ErrorMsg.setErrorMsg(getCtx(), "SaveError", "Last SP is " + latestSP.getSP()
						+ ", you must to raise the level of sp");
				return false;
			}
		}
		else if (currentSPNumber > 1)
		{
			ErrorMsg.setErrorMsg(getCtx(), "SaveError", "Invalid SP!");
			return false;
		}
		
		return true;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(!isValidSP())
			return false;
		return super.beforeSave(newRecord);
	}
	
	private String cutContract()
	{
		I_UNS_Employee employee = getUNS_Employee();
		MUNSContractEvaluation newEvaluation = new MUNSContractEvaluation(getCtx(), 0, get_TrxName());
		newEvaluation.setAD_Org_ID(employee.getAD_Org_ID());
		newEvaluation.setDescription("::Auto Generate From SP::");
		newEvaluation.setGrade(MUNSContractEvaluation.GRADE_E);
		newEvaluation.setLastContractDate(employee.getUNS_Contract_Recommendation().getDateContractStart());
		newEvaluation.setTotalGrade(BigDecimal.ZERO);
		newEvaluation.setUNS_Employee_ID(employee.getUNS_Employee_ID());
		newEvaluation.setUNS_Contract_Recommendation_ID(employee.getUNS_Contract_Recommendation_ID());
		newEvaluation.setRecommendation(MUNSContractEvaluation.RECOMMENDATION_ContractTermination);
		newEvaluation.setRemarks("::Contact terminated, SP ["+this.getDocumentNo()+"]::");
		newEvaluation.setDocStatus(DOCSTATUS_Drafted);
		newEvaluation.setDocAction(DOCACTION_Prepare);
		if(!newEvaluation.save())
		{
			return "Failed to create new contract evaluation";
		}
		
		try {
			newEvaluation.processIt(DOCACTION_Prepare);
		} catch (Exception ex) {
			throw new AdempiereException(ex.getMessage());
		}
		
		return null;
	}
}
