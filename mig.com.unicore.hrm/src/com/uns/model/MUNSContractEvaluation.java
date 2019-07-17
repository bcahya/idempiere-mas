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
import org.compiere.process.ProcessInfo;
import org.compiere.util.AdempiereUserError;
import org.compiere.wf.MWorkflow;

import com.uns.model.process.CreateNewContract;

/**
 * @author eko
 *
 */
public class MUNSContractEvaluation extends X_UNS_Contract_Evaluation implements DocAction, DocOptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_Contract_Evaluation_ID
	 * @param trxName
	 */
	public MUNSContractEvaluation(Properties ctx,
			int UNS_Contract_Evaluation_ID, String trxName) {
		super(ctx, UNS_Contract_Evaluation_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSContractEvaluation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Set inactive for all preveouse contract recomendation.
	 * @return
	 */
	public boolean inActivePrevContracRecommendation()
	{
		/*
		List<MUNSContractRecommendation> listOfContractRecommendation = MUNSContractRecommendation.get(
												getCtx(), getUNS_Contract_Evaluation_ID(), get_TrxName());
		for (MUNSContractRecommendation cr : listOfContractRecommendation)
		{
			cr.setIsActive(false);
			if (!cr.save())
				return false;
		}
		*/
		return true;
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

	private String m_processMsg = null;
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
		setProcessed(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	private boolean m_justPrepared = false;
	
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

		if (!isCreatedContract() && !RECOMMENDATION_ContractTermination.equals(getRecommendation())) {
			try {
				CreateNewContract create = new CreateNewContract(getCtx(), get_TrxName());
				create.processIt(this);
			} catch (Exception e) {
				
				throw new AdempiereUserError("Error while trying create new contract.\n"+ e.getMessage());
			}
		}
		
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
		//if contract termination inActiveEmployee.
		if (null == getRecommendation())
		{
			m_processMsg = "Null value of recommendation";
			return DocAction.STATUS_Invalid;
		}
		
		if (getRecommendation().equals(RECOMMENDATION_ContractTermination))
		{
			MUNSEmployee employee = (MUNSEmployee)getUNS_Employee();
			MUNSContractRecommendation contractRecommend = 
					new MUNSContractRecommendation(getCtx(), getUNS_Contract_Recommendation_ID(), get_TrxName());
			contractRecommend.setIsActive(false);
			contractRecommend.save();
			MUNSPayrollBaseEmployee pbEmployee = 
					MUNSPayrollBaseEmployee.getPrev(getCtx(), getUNS_Employee_ID(), get_TrxName());
			if(null != pbEmployee)
			{
				pbEmployee.setIsActive(false);
				pbEmployee.save();
			}
			employee.setIsTerminate(true);
			if(!employee.save())
				throw new AdempiereException("Failed to update employee");
		} else if(isCreatedContract()) {
			MUNSContractRecommendation contractRecommend = 
					MUNSContractRecommendation.get(getCtx(), getUNS_Contract_Evaluation_ID(), get_TrxName());
			if(contractRecommend==null)
				return DocAction.STATUS_InProgress;
			else if (!contractRecommend.isApproved()){
				m_processMsg = "Please Complete Contract Recommendation [" 
												+ contractRecommend.getDocumentNo() + "]";
				setProcessed(true);
				setDocAction(DOCACTION_Complete);
				return DocAction.STATUS_InProgress;
			}
		} else
			throw new AdempiereUserError("Create new contract before complete."); 
	

		setProcessed(true);	
		//m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		log.info(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		if (getRecommendation().equals(RECOMMENDATION_ContractTermination))
		{
			MUNSEmployee employee = (MUNSEmployee)getUNS_Employee();
			MUNSContractRecommendation contractRecommend = 
					new MUNSContractRecommendation(getCtx(), getUNS_Contract_Recommendation_ID(), get_TrxName());
			contractRecommend.setIsActive(true);
			contractRecommend.save();

			MUNSPayrollBaseEmployee pbEmployee = MUNSPayrollBaseEmployee.get(getCtx(), contractRecommend.get_ID(), get_TrxName());
			if(null != pbEmployee)
			{
				pbEmployee.setIsActive(true);
				pbEmployee.saveEx();
			}
			employee.setIsTerminate(false);
			if(!employee.save())
				throw new AdempiereException("Failed to update employee");
		}
		else if(isCreatedContract()) {
			MUNSContractRecommendation contractRecommend = 
					MUNSContractRecommendation.get(getCtx(), getUNS_Contract_Evaluation_ID(), get_TrxName());
			if(contractRecommend != null)
			{
				try
				{
					ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(contractRecommend, DocAction.ACTION_Void);
					if(pi.isError())
					{
						m_processMsg = pi.getSummary();
						return false;
					}
				}
				catch (Exception e)
				{
					m_processMsg = e.getLocalizedMessage();
					return false;
				}
			}
			
		} else
			throw new AdempiereUserError("Create new contract before complete."); 
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public boolean closeIt() {
		// TODO Auto-generated method stub
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
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
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

	public boolean isCreatedContract()
	{
		return "Y".equals(getCreateNewSalary());
	}
}
