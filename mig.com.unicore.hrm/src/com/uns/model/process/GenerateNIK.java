/**
 * 
 */
package com.uns.model.process;

import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSContractRecommendation;
import com.uns.model.MUNSEmployee;
import com.uns.model.NIKGenerator;

/**
 * @author menjangan
 *
 */
public class GenerateNIK extends SvrProcess {

	private MUNSContractRecommendation m_ContractRecomendation = null;
	private String m_reff = "";
	/**
	 * 
	 */
	public GenerateNIK() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++){
			String name = para[i].getParameterName();
			log.fine("prepare - " + para[i]);
			if ("value".equalsIgnoreCase(name))
				m_reff = (String) para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		m_ContractRecomendation = new MUNSContractRecommendation(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {		
		MUNSEmployee employee = MUNSEmployee.get(getCtx(), m_ContractRecomendation.getUNS_Employee_ID());
		String payrollTerm = m_ContractRecomendation.getNextPayrollTerm();
		String employeType = employee.getEmploymentType();
		if(m_ContractRecomendation.isGenerateNIK())
			return null;
		
		if (m_reff!=null && m_reff.length()>0 && !m_reff.equalsIgnoreCase("000000")){
			m_ContractRecomendation.setNewNIK(m_reff);
			employee.setValue(m_reff);
		}
		else if (employeType.equals(MUNSEmployee.EMPLOYMENTTYPE_Company))
		{			
			if (m_ContractRecomendation.getNextContractType().equals(
					MUNSContractRecommendation.NEXTCONTRACTTYPE_Permanen)
					|| payrollTerm.equals(MUNSEmployee.PAYROLLTERM_Monthly))
			{
				NIKGenerator nikGenerator = new NIKGenerator(NIKGenerator.TYPE_PERMANEN, get_TrxName());
				m_ContractRecomendation.setNewNIK(nikGenerator.getNewNIK());
				employee.setValue(nikGenerator.getNewNIK());
				//employee.setEmploymentStatus(MUNSEmployee.EMPLOYMENTSTATUS_Bulanan);
			}
			
			else if (payrollTerm.equals(MUNSEmployee.PAYROLLTERM_2Weekly)
					|| payrollTerm.equals(MUNSEmployee.PAYROLLTERM_Weekly))
			{
				NIKGenerator nikGenerator = new NIKGenerator(NIKGenerator.TYPE_HARIAN, get_TrxName());
				m_ContractRecomendation.setNewNIK(nikGenerator.getNewNIK());
				employee.setValue(nikGenerator.getNewNIK());
			}
		}
		else if(employee.getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_SubContract))
		{
			NIKGenerator nikGenerator = new NIKGenerator(NIKGenerator.TYPE_PEMBORONG, get_TrxName());
			m_ContractRecomendation.setNewNIK(nikGenerator.getNewNIK());
			employee.setValue(nikGenerator.getNewNIK());
		}
		
		m_ContractRecomendation.setGenerateNIK("Y");
		m_ContractRecomendation.saveEx();
		
		employee.setPayrollTerm(payrollTerm);
		employee.saveEx();
		return null;
	}

}
