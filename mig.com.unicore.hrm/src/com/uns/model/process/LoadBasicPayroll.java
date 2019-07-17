/**
 * 
 */
package com.uns.model.process;

import java.util.Properties;

import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.AdempiereUserError;

import com.uns.model.MUNSContractRecommendation;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSPayrollLevelConfig;

/**
 * @author menjangan
 *
 */
public class LoadBasicPayroll extends SvrProcess {

	MUNSContractRecommendation m_ContractRecommend = null;
	
	private Properties m_ctx = null;
	private String m_trxName = null;
	

	/**
	 * 
	 */
	public LoadBasicPayroll() {
	}
	
	public LoadBasicPayroll(Properties ctx, int UNS_ContractRecommendation_ID, String trxName)
	{
		this.m_ctx = ctx;
		this.m_trxName = trxName;
		this.m_ContractRecommend = new MUNSContractRecommendation(
				m_ctx, UNS_ContractRecommendation_ID, m_trxName);
	}
	
	public LoadBasicPayroll(MUNSContractRecommendation contract)
	{
		this.m_ctx = contract.getCtx();
		this.m_trxName = contract.get_TrxName();
		this.m_ContractRecommend = contract;
	}
	
	public String load()
	{
		try {
			doIt();
		}catch (Exception ex)
		{
			return ex.getMessage();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		this.m_trxName = get_TrxName();
		this.m_ctx = getCtx();
		
		m_ContractRecommend = new MUNSContractRecommendation(m_ctx, getRecord_ID(), m_trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// worker is not generate payroll
		if (MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganCV.equals(
				m_ContractRecommend.getNextContractType()))
			return null;
		
		if (m_ContractRecommend.getNewPayrollLevel().equals(m_ContractRecommend.getPrevPayrollLevel())){
			
			m_ContractRecommend.setNew_G_Pokok(m_ContractRecommend.getPrev_G_Pokok());
			m_ContractRecommend.setNew_T_Kesejahteraan(m_ContractRecommend.getPrev_T_Kesejahteraan());
			m_ContractRecommend.setNew_T_Jabatan(m_ContractRecommend.getPrev_T_Jabatan());
			m_ContractRecommend.setNew_T_Lembur(m_ContractRecommend.getPrev_T_Lembur());
			m_ContractRecommend.setNew_A_L1(m_ContractRecommend.getPrev_A_L1());
			m_ContractRecommend.setNew_A_L2(m_ContractRecommend.getPrev_A_L2());
			m_ContractRecommend.setNew_A_L3(m_ContractRecommend.getPrev_A_L3());
			m_ContractRecommend.setNew_A_Premi(m_ContractRecommend.getPrev_A_Premi());
			m_ContractRecommend.setNew_A_Lain2(m_ContractRecommend.getPrev_A_Lain2());
			m_ContractRecommend.setNew_P_Mangkir(m_ContractRecommend.getPrev_P_Mangkir());
			m_ContractRecommend.setNew_P_SPTP(m_ContractRecommend.getPrev_P_SPTP());
			m_ContractRecommend.setNew_P_Lain2(m_ContractRecommend.getPrev_P_Lain2());
			m_ContractRecommend.setNewLeburJamPertama(m_ContractRecommend.getPrevLeburJamPertama());
			m_ContractRecommend.setNewLeburJamBerikutnya(m_ContractRecommend.getPrevLeburJamBerikutnya());
			
		} else {
			MUNSPayrollConfiguration payConfig = MUNSPayrollConfiguration.get(
				m_ctx, m_ContractRecommend.getDateContractEnd(), m_trxName);
			if (null == payConfig)
				throw new AdempiereUserError("Not Found Payroll Configuration");
		
			MUNSPayrollLevelConfig payrollLevelConfig = payConfig.getPayrollLevel(
				m_ContractRecommend.getNewPayrollLevel(), m_ContractRecommend.getNextPayrollTerm());
			if (null == payrollLevelConfig)
				throw new AdempiereUserError(
					"Not Found Payroll Level Config for level " + m_ContractRecommend.getNewPayrollLevel());
		
			m_ContractRecommend.setNew_G_Pokok(payrollLevelConfig.getMin_G_Pokok());
			m_ContractRecommend.setNew_T_Kesejahteraan(payrollLevelConfig.getMin_G_T_Kesejahteraan());
			m_ContractRecommend.setNew_T_Jabatan(payrollLevelConfig.getMin_G_T_Jabatan());
			m_ContractRecommend.setNew_T_Lembur(payrollLevelConfig.getMin_G_T_Lembur());
			m_ContractRecommend.setNew_A_L1(payrollLevelConfig.getMin_A_L1());
			m_ContractRecommend.setNew_A_L2(payrollLevelConfig.getMin_A_L2());
			m_ContractRecommend.setNew_A_L3(payrollLevelConfig.getMin_A_L3());
			m_ContractRecommend.setNew_A_Premi(payrollLevelConfig.getMin_A_Premi());
			m_ContractRecommend.setNew_A_Lain2(payrollLevelConfig.getMin_A_Lain2());
			m_ContractRecommend.setNew_P_Mangkir(payrollLevelConfig.getP_Mangkir());
			m_ContractRecommend.setNew_P_SPTP(payrollLevelConfig.getP_SPTP());
			m_ContractRecommend.setNew_P_Lain2(payrollLevelConfig.getP_Lain2());
			m_ContractRecommend.setNewLeburJamPertama(payrollLevelConfig.getMin_A_LemburJamPertama());
			m_ContractRecommend.setNewLeburJamBerikutnya(payrollLevelConfig.getMin_A_Lembur());
		}
		m_ContractRecommend.setPotonganToZero();
		if (!m_ContractRecommend.save(m_trxName))
			throw new AdempiereSystemError("Can't Load Basec Payroll");
		return null;
	}

}
