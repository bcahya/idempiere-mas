/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;

import com.uns.model.MUNSContractEvaluation;
import com.uns.model.MUNSContractRecommendation;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSPayrollLevelConfig;
import com.uns.model.MUNSPayrollTermConfig;

/**
 * @author menjangan
 *
 */
public class CreateNewContract extends SvrProcess {

	private MUNSContractEvaluation m_ContractEvaluation = null;
	private MUNSContractRecommendation m_PrevContract = null;
	private MUNSEmployee m_Employee = null;
	private Properties m_ctx;
	private String m_trxName;

	/**
	 * 
	 */
	public CreateNewContract() {
	}
	
	public CreateNewContract(Properties ctx, String trxName) {
		super();
		
		m_ctx = ctx;
		m_trxName = trxName;
	}
	
	@Override
	public Properties getCtx() {
		if (m_ctx!=null)
			return m_ctx;
		
		return super.getCtx();
	}

	@Override
	public String get_TrxName() {
		if (m_trxName!=null)
			return m_trxName;
		
		return super.get_TrxName();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		if (getRecord_ID() <=0)
			throw new IllegalArgumentException("NO RECORD ID");
		m_ContractEvaluation = new MUNSContractEvaluation(getCtx(), getRecord_ID(), get_TrxName());
		
		if (null == m_ContractEvaluation)
			throw new AdempiereException("NOT FOUND CONTRACT EVALUATION WITH ID " + getRecord_ID());
		
		m_Employee = MUNSEmployee.get(getCtx(), m_ContractEvaluation.getUNS_Employee_ID());
		
//		Create Contract manual without check Total Grade
//		if ((m_Employee.getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_SubContract)
//				&& m_ContractEvaluation.getTotalGrade().compareTo(new BigDecimal(60))<0)
//				
//			|| (m_Employee.getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_Company)
//					&& m_ContractEvaluation.getTotalGrade().compareTo(new BigDecimal(2.6))<0))
//			
//			throw new AdempiereException("Can't create new contract, Total Grade too Low.");
	
	}
	
	public String processIt(MUNSContractEvaluation evaluation){
		m_ContractEvaluation = evaluation;
		
		if (null == m_ContractEvaluation)
			throw new AdempiereException("NOT FOUND CONTRACT EVALUATION WITH ID " + getRecord_ID());
		
		m_Employee = MUNSEmployee.get(getCtx(), m_ContractEvaluation.getUNS_Employee_ID());
		
		if ((m_Employee.getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_SubContract)
				&& m_ContractEvaluation.getTotalGrade().compareTo(new BigDecimal(60))<0)
				
			|| (m_Employee.getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_Company)
					&& m_ContractEvaluation.getTotalGrade().compareTo(new BigDecimal(2.6))<0))
			
			throw new AdempiereException("Can't create new contract, Total Grade too Low.");
		
		try {
			return doIt();
		} catch (Exception e) {
			throw new AdempiereException(e.toString());
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected String doIt() throws Exception {
		if(m_ContractEvaluation.isCreatedContract())
			throw new AdempiereUserError("Contract has been created, can't create again");
		
		if (m_ContractEvaluation.getRecommendation()
				.equals(MUNSContractEvaluation.RECOMMENDATION_ContractTermination))
			return null;
		
		m_PrevContract = MUNSContractRecommendation
				.getPrev(getCtx(), m_ContractEvaluation.getUNS_Employee_ID(), get_TrxName());

		if(null == m_PrevContract)
			throw new AdempiereUserError("Not found previous contract for employee " + m_Employee.getName());
		
		return createContractRecommendation();
	}
	
	private String createContractRecommendation()
	{		
		MUNSPayrollConfiguration payConfig = MUNSPayrollConfiguration
				.get(getCtx(), Env.getContextAsDate(getCtx(), "#Date"), get_TrxName());
		
		String payrollTerm = MUNSPayrollTermConfig.getPayrollTermOf(
				m_ContractEvaluation.getAD_Org_ID()
				, m_PrevContract.getNewSectionOfDept_ID()
				, m_PrevContract.getNextContractType()
				, Env.getContextAsDate(m_ctx, "Date")
				, m_trxName);
		
		if(null == payrollTerm)
			payrollTerm = m_PrevContract.getNextPayrollTerm();
		
		MUNSPayrollLevelConfig payrollLevelConfig = payConfig.getPayrollLevel(
				m_PrevContract.getNewPayrollLevel(), payrollTerm);
		if (null == payrollLevelConfig 
				&& m_Employee.getEmploymentType().equals(
						MUNSEmployee.EMPLOYMENTTYPE_Company))
			throw new AdempiereUserError(
					"NO PAYROLL LEVEL CONFIG FOR LEVEL " + m_PrevContract.getNewPayrollLevel());
		
		MUNSContractRecommendation contractRecommendation = new MUNSContractRecommendation(getCtx(), 0, get_TrxName());
		
		contractRecommendation.setPrevPayrollLevel(m_PrevContract.getNewPayrollLevel());
		contractRecommendation.setNewPayrollLevel(m_PrevContract.getNewPayrollLevel());
		if(m_ContractEvaluation.getRecommendation().equals(MUNSContractEvaluation.RECOMMENDATION_MoveToAnotherDept))
			contractRecommendation.setIsMoveTo(true);
		
		contractRecommendation.setEmploymentType(m_Employee.getEmploymentType());
		contractRecommendation.setAD_Org_ID(m_ContractEvaluation.getAD_Org_ID());
		contractRecommendation.setUNS_Contract_Evaluation_ID(m_ContractEvaluation.get_ID());
		contractRecommendation.setUNS_Employee_ID(m_ContractEvaluation.getUNS_Employee_ID());
		contractRecommendation.setNewDept_ID(m_PrevContract.getNewDept_ID());
		contractRecommendation.setDateContractStart(Env.getContextAsDate(getCtx(), "#Date"));
		contractRecommendation.setNextPayrollTerm(m_PrevContract.getNextPayrollTerm());
		contractRecommendation.setNewSectionOfDept_ID(m_PrevContract.getNewSectionOfDept_ID());
		contractRecommendation.setNewNIK(m_PrevContract.getNewNIK());
		contractRecommendation.setNewJob_ID(m_PrevContract.getNewJob_ID());
		contractRecommendation.setNewGender(m_PrevContract.getNewGender());
		contractRecommendation.setNewAgent_ID(m_PrevContract.getNewAgent_ID());
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(contractRecommendation.getDateContractStart().getTime());
		if (m_ContractEvaluation.getRecommendation().equals(MUNSContractEvaluation.RECOMMENDATION_SequenceContract))
			cal.add(Calendar.MONTH, 6);
		else
			cal.add(Calendar.MONTH, 12);
		contractRecommendation.setDateContractEnd(new Timestamp(cal.getTimeInMillis()));
		
		if(m_PrevContract.getNextContractType().equals(MUNSContractRecommendation.NEXTCONTRACTTYPE_Contract1)
				&& m_ContractEvaluation.getRecommendation().equals(MUNSContractEvaluation.RECOMMENDATION_Re_Contract))
			contractRecommendation.setNextContractType(MUNSContractRecommendation.NEXTCONTRACTTYPE_Recontract1);
		
		else if(m_ContractEvaluation.getRecommendation().equals(MUNSContractEvaluation.RECOMMENDATION_Contract2))
			contractRecommendation.setNextContractType(MUNSContractRecommendation.NEXTCONTRACTTYPE_Contract2);
		
		else if(m_ContractEvaluation.getRecommendation().equals(MUNSContractEvaluation.RECOMMENDATION_Re_Contract)
				&& m_PrevContract.getNextContractType().equals(MUNSContractRecommendation.NEXTCONTRACTTYPE_Contract2))
			contractRecommendation.setNextContractType(MUNSContractRecommendation.NEXTCONTRACTTYPE_Recontract2);
		
		else if(m_ContractEvaluation.getRecommendation().equals(MUNSContractEvaluation.RECOMMENDATION_Re_Contract)
				&& m_PrevContract.getNextContractType().equals(MUNSContractRecommendation.NEXTCONTRACTTYPE_Recontract1))
			contractRecommendation.setNextContractType(MUNSContractRecommendation.NEXTCONTRACTTYPE_Recontract1);
		
		else if(m_ContractEvaluation.getRecommendation().equals(MUNSContractEvaluation.RECOMMENDATION_Re_Contract)
				&& m_PrevContract.getNextContractType().equals(MUNSContractRecommendation.NEXTCONTRACTTYPE_Recontract2))
			contractRecommendation.setNextContractType(MUNSContractRecommendation.NEXTCONTRACTTYPE_Recontract2);
		
		else if(m_ContractEvaluation.getRecommendation().equals(MUNSContractEvaluation.RECOMMENDATION_DemotedToPosition))
			contractRecommendation.setNextContractType(m_PrevContract.getNextContractType());
		
		else if(m_ContractEvaluation.getRecommendation().equals(MUNSContractEvaluation.RECOMMENDATION_MoveToAnotherDept))
			contractRecommendation.setNextContractType(m_PrevContract.getNextContractType());
		
		else if(m_ContractEvaluation.getRecommendation().equals(MUNSContractEvaluation.RECOMMENDATION_PromotedToPosition))
			contractRecommendation.setNextContractType(m_PrevContract.getNextContractType());
		
		else if(m_ContractEvaluation.getRecommendation().equals(MUNSContractEvaluation.RECOMMENDATION_ToPermanenStatus))
			contractRecommendation.setNextContractType(MUNSContractRecommendation.NEXTCONTRACTTYPE_Permanen);
		
		else if(m_ContractEvaluation.getRecommendation().equals(MUNSContractEvaluation.RECOMMENDATION_SequenceContract))
			contractRecommendation.setNextContractType(MUNSContractRecommendation.NEXTCONTRACTTYPE_SquenceContract);
		
		else
			throw new AdempiereUserError("Invalid Contract Recommendation.");
	
		//set Preveous Contract
		contractRecommendation.setPrev_A_L1(m_PrevContract.getNew_A_L1());
		contractRecommendation.setPrev_A_L2(m_PrevContract.getNew_A_L2());
		contractRecommendation.setPrev_A_L3(m_PrevContract.getNew_A_L3());
		contractRecommendation.setPrev_A_Lain2(m_PrevContract.getNew_A_Lain2());
		contractRecommendation.setPrev_A_Premi(m_PrevContract.getNew_A_Premi());
		contractRecommendation.setPrev_G_Pokok(m_PrevContract.getNew_G_Pokok());
		contractRecommendation.setPrev_P_Label(m_PrevContract.getNew_P_Label());
		contractRecommendation.setPrev_P_Mangkir(m_PrevContract.getNew_P_Mangkir());
		contractRecommendation.setPrev_P_SPTP(m_PrevContract.getNew_P_SPTP());
		contractRecommendation.setPrev_T_Jabatan(m_PrevContract.getNew_T_Jabatan());
		contractRecommendation.setPrev_T_Kesejahteraan(m_PrevContract.getNew_T_Kesejahteraan());
		contractRecommendation.setPrev_T_Lembur(m_PrevContract.getNew_T_Lembur());
		
		contractRecommendation.setPrevDept_ID(m_PrevContract.getPrevDept_ID());
		contractRecommendation.setPrevNIK(m_Employee.getValue());
		contractRecommendation.setPrevSectionOfDept_ID(m_Employee.getC_BPartner_ID());
		contractRecommendation.setPrevShift(m_PrevContract.getNewShift());
		contractRecommendation.setPrevJob_ID(m_PrevContract.getNewJob_ID());
		contractRecommendation.setPrevPayrollTerm(m_PrevContract.getNextPayrollTerm());
		contractRecommendation.setPrevContractType(m_PrevContract.getNextContractType());
		contractRecommendation.setPrevLeburJamPertama(m_PrevContract.getNewLeburJamPertama());
		contractRecommendation.setPrevLeburJamBerikutnya(m_PrevContract.getNewLeburJamBerikutnya());
		contractRecommendation.setPrevGender(m_PrevContract.getNewGender());
		contractRecommendation.setPrevAgent_ID(m_PrevContract.getNewAgent_ID());
		
		if (m_Employee.getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_SubContract))
				contractRecommendation.setPrevContractNumber(m_PrevContract.getNextContractNumber());
		
		m_PrevContract.setIsActive(false);
		m_PrevContract.saveEx();
		
		if(!contractRecommendation.save())
			throw new IllegalArgumentException("Failed to generate Contract Recommendation");
		m_ContractEvaluation.setCreateNewSalary("Y");
		m_ContractEvaluation.saveEx();
		return "Contract Recommendation Has Created " + contractRecommendation.getDocumentNo();
	}

}
