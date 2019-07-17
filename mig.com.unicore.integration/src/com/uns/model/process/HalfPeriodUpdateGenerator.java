/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.I_C_DocType;
import com.uns.model.I_UNS_Employee;
import com.uns.model.MUNSCriteriaResult;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSEmployeeLoan;
import com.uns.model.MUNSHalfPeriodPresence;
import com.uns.model.MUNSLoanInstallment;
import com.uns.model.MUNSPayRollLine;
import com.uns.model.MUNSPayRollPremiTarget;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSProductionPayConfig;
import com.uns.model.MUNSProductionWorker;
import com.uns.model.MUNSProductionWorkerResult;
import com.uns.model.MUNSUtilitiesUses;
import com.uns.model.MUNSWorkerPresence;

/**
 * @author menjangan
 *
 */
public class HalfPeriodUpdateGenerator extends SvrProcess {

	/**
	 * 
	 */
	public HalfPeriodUpdateGenerator() {
	}

	private MUNSHalfPeriodPresence m_HalfPeriodPresence = null;
	private MUNSEmployee m_Employee = null;
	private MUNSProductionPayConfig m_PayConfig = null;
	private String m_ProcessMsg = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		m_HalfPeriodPresence = new MUNSHalfPeriodPresence(getCtx(), getRecord_ID(), get_TrxName());
		if (null == m_HalfPeriodPresence)
			throw new IllegalArgumentException("No Record ID");
		
		m_Employee = new MUNSEmployee(getCtx(), m_HalfPeriodPresence.getUNS_Employee_ID(), get_TrxName());
		
		if (null == m_Employee)
			throw new IllegalArgumentException(
					"Not found Employee date with ID " + m_HalfPeriodPresence.getUNS_Employee_ID());
		
		m_PayConfig = MUNSProductionPayConfig.get(
				getCtx(), m_HalfPeriodPresence.getAD_Org_ID()
				, m_HalfPeriodPresence.getEndDate(), get_TrxName());
		
		if (null == m_PayConfig)
		throw new IllegalArgumentException(
				"Not found production pay config");
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		updateReceivable();
		return m_ProcessMsg;
	}
	
	
	/**
	 * 
	 */
	private void updateReceivable()
	{
		boolean isSwitchToDailyIfOutOfPremi = false;
		Hashtable<Integer, MUNSPayRollPremiTarget> mapOfPremiTarget = 
				new Hashtable<Integer, MUNSPayRollPremiTarget>();
		double totalInsentifKerajinan = 0.0;
		double insentifKembali = 0.0;
		double totalInsentifTarget = 0.0;
		
		Hashtable<Integer, WorkerResult> mapOfWorkerResult = getWorkerResult();
		
		
		
		for(MUNSCriteriaResult criteriaResult : m_HalfPeriodPresence.getCriteriaResults(false))
		{
			MUNSPayRollLine criteriaLine = (MUNSPayRollLine)criteriaResult.getUNS_PayRollLine();
			WorkerResult workerResultTmp = mapOfWorkerResult.get(criteriaLine.getM_Product_ID());
			if(null != workerResultTmp)
				criteriaResult.setResult(
						new BigDecimal(workerResultTmp.getQtyProduction()));
			
			if(!isSwitchToDailyIfOutOfPremi)
				isSwitchToDailyIfOutOfPremi = criteriaLine.isSwitchTodaily();
			
			if(MUNSPayRollLine.PREMICALCULATEBASED_AbsenceBased.equals(
					criteriaLine.getPremiCalculateBased()))
				continue;
			if(criteriaLine.isTargetBased())
			{
				double percentResultToCalc = criteriaLine.getPremiAchivementRecognized().doubleValue();
				if(percentResultToCalc <=0)
					percentResultToCalc = 100;
				percentResultToCalc /= 100;
				double resultToCalc = criteriaResult.getResult().doubleValue() * percentResultToCalc;
				MUNSPayRollPremiTarget criteriaTarget = 
						criteriaLine.getCriteriaTarget(new BigDecimal(resultToCalc));
				if(null == criteriaTarget)
					continue;
				
				criteriaResult.setResultAmt(criteriaTarget.getPay());
				mapOfPremiTarget.put(criteriaTarget.get_ID(), criteriaTarget);
				totalInsentifTarget += criteriaTarget.getPay().doubleValue();
			}
			else
			{
				criteriaResult.setResultAmt(criteriaLine.getPay());
				totalInsentifTarget += criteriaLine.getPay().doubleValue();
			}
			criteriaResult.save();
		}
		
		String[] absenceTypes = {MUNSPayRollPremiTarget.ABSENTYPE_Mangkir,
				MUNSPayRollPremiTarget.ABSENTYPE_NonPayableDispensation, 
				MUNSPayRollPremiTarget.ABSENTYPE_PayableDispensation, 
				MUNSPayRollPremiTarget.ABSENTYPE_Sakit};
		
		for(MUNSCriteriaResult criteriaResult : m_HalfPeriodPresence.getCriteriaResults(false))
		{
			MUNSPayRollLine criteriaLine =
					(MUNSPayRollLine)criteriaResult.getUNS_PayRollLine();
			if(!MUNSPayRollLine.CRITERIATYPE_Premi.equals(criteriaLine.getCriteriaType())
					&& !MUNSPayRollLine.PREMICALCULATEBASED_AbsenceBased.equals(
							criteriaLine.getPremiCalculateBased()))
				continue;
			
			double insentif = criteriaLine.getPay().doubleValue();
			double potonganInsentif = 0.0;

			for(String absenceType : absenceTypes)
			{
				BigDecimal absence = m_HalfPeriodPresence.getPresence(absenceType);
				MUNSPayRollPremiTarget criteriaTarget =
						criteriaLine.getCriteriaTarget(absence, absenceType);
				if(null != criteriaTarget)
				{
					potonganInsentif += criteriaTarget.getPay().doubleValue();
					if(criteriaTarget.getPayableToTarget_ID() > 0)
					{
						MUNSPayRollPremiTarget pp = mapOfPremiTarget.get(
								criteriaTarget.getPayableToTarget_ID());
						if(null != pp)
						{
							if(insentifKembali < criteriaTarget.getPayableAmt().doubleValue())
								insentifKembali = criteriaTarget.getPayableAmt().doubleValue();
						}
					}
				}
			}
			totalInsentifKerajinan += insentif;
			totalInsentifKerajinan -= potonganInsentif;
			criteriaResult.setResultAmt(new BigDecimal(totalInsentifKerajinan));
			criteriaResult.save();
		}
		
		if(insentifKembali > totalInsentifKerajinan)
			totalInsentifKerajinan = insentifKembali;
		
		if(totalInsentifKerajinan <= 0
				&& isSwitchToDailyIfOutOfPremi)
		{
			m_HalfPeriodPresence.setPayToDaily(m_PayConfig);
		}
		
		m_HalfPeriodPresence.setPremiKerajinanReceivable(new BigDecimal(totalInsentifKerajinan));
		m_HalfPeriodPresence.setPremiTargetReceivable(new BigDecimal(totalInsentifTarget));
		if (m_HalfPeriodPresence.getHalfPeriodNo().equals(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod2)
				|| m_HalfPeriodPresence.getHalfPeriodNo().equals(MUNSHalfPeriodPresence.HALFPERIODNO_FullMonth))
		{
			I_UNS_Employee worker = m_HalfPeriodPresence.getUNS_Employee();
			MUNSPayrollConfiguration payConfig =
					MUNSPayrollConfiguration.get(getCtx(), m_HalfPeriodPresence.getStartDate(), get_TrxName());
			m_HalfPeriodPresence.setA_JHT(payConfig.getJHTBorongan());
			m_HalfPeriodPresence.setA_JK(payConfig.getJKBorongan());
			m_HalfPeriodPresence.setA_JKK(payConfig.getJKKBorongan());
			m_HalfPeriodPresence.setA_JPK(payConfig.getJPKBorongan());
			m_HalfPeriodPresence.setP_Obat(payConfig);
			initLoanInstallment();
			m_HalfPeriodPresence.setP_ListrikAir(getChargeUtilitiesUses());
			if(payConfig.isCalculatePPH21Buruh()
					|| worker.getVendor_ID() <= 0)
				m_HalfPeriodPresence.setPPH21(payConfig.getPPH21Worker(m_HalfPeriodPresence));
		}
		m_HalfPeriodPresence.setUpdateHalfPeriodPresence("Y");
		m_HalfPeriodPresence.save();
	}
	
	/**
	 * 
	 * @return
	 */
	protected Hashtable<Integer, WorkerResult> getWorkerResult()
	{
		Hashtable<Integer, WorkerResult> mapOfWorkerResult =
				new Hashtable<Integer, WorkerResult>();

		MUNSWorkerPresence[] workerPresences = m_HalfPeriodPresence.getLines(false);
		for (MUNSWorkerPresence workerPresence : workerPresences)
		{
			
			if(workerPresence.getUNS_Production_Worker_ID() <= 0)
				continue;
			
			MUNSProductionWorker productionWorker = new MUNSProductionWorker(
							getCtx(), workerPresence.getUNS_Production_Worker_ID(), get_TrxName());
			
			/*
			MUNSResourceWorkerLine resourceWorker = 
					MUNSResourceWorkerLine.getWorkerOfOrg(
							getCtx(),
							m_HalfPeriodPresence.getAD_Org_ID(), 
							productionWorker.getReplacementLabor_ID(), 
							get_TrxName());
			if(null == resourceWorker)
				continue;
			*/
			for (MUNSProductionWorkerResult workerResult : productionWorker.getResults())
			{
				WorkerResult workerResultTmp = mapOfWorkerResult.get(workerResult.getM_Product_ID());
				if(null == workerResultTmp)
				{
					workerResultTmp = new WorkerResult(
							workerResult.getM_Product_ID(), workerPresence.getUNS_Job_Role_ID());
					workerResultTmp.setC_UOM_ID(workerResult.getM_Product().getC_UOM_ID());
					mapOfWorkerResult.put(workerResult.getM_Product_ID(), workerResultTmp);
					
				}
				workerResultTmp.setQtyProduction(
						workerResultTmp.getQtyProduction() + workerResult.getProductionQty().doubleValue());
			}
		}
		return mapOfWorkerResult;
	}
	
	/**
	 * 
	 */
	protected void initLoanInstallment()
	{
		double totalInstallmentCompany = 0.0;
		double totalInstallmentKoprasi = 0.0;
		List<MUNSEmployeeLoan> listOfEmployeeLoan = MUNSEmployeeLoan.gets(
				getCtx(), m_HalfPeriodPresence.getUNS_Employee_ID(), get_TrxName());
		for (MUNSEmployeeLoan employeeLoan : listOfEmployeeLoan)
		{
			if (employeeLoan.getLoanAmtLeft().compareTo(BigDecimal.ZERO) <= 0)
				continue;
			
			if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Company))
				totalInstallmentCompany += employeeLoan.getInstallment().doubleValue();
			else if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Koperasi))
				totalInstallmentKoprasi += employeeLoan.getInstallment().doubleValue();
			
			if(m_HalfPeriodPresence.isGeneratePay())
				continue;
			
			MUNSLoanInstallment loanInstallment = 
					new MUNSLoanInstallment(getCtx(), 0, get_TrxName());
			loanInstallment.setAD_Org_ID(employeeLoan.getAD_Org_ID());
			loanInstallment.setUNS_Employee_Loan_ID(employeeLoan.get_ID());
			loanInstallment.setPaidAmt(employeeLoan.getInstallment());
			loanInstallment.setPaidDate(Env.getContextAsDate(getCtx(), "#Date"));
			loanInstallment.setC_DocType_ID(MDocType.getDocType(I_C_DocType.DOCBASETYPE_LoanInstallment));
			loanInstallment.setIsCashPayment(false);
			loanInstallment.save();
			try {
				loanInstallment.processIt(MUNSLoanInstallment.DOCACTION_Complete);
			} catch(Exception ex) {
				throw new AdempiereException(ex.getMessage());
			}
			
		}
		m_HalfPeriodPresence.setP_PinjamanKaryawan(new BigDecimal(totalInstallmentCompany));
		m_HalfPeriodPresence.setP_Koperasi(new BigDecimal(totalInstallmentKoprasi));
	}
	
	/**
	 * 
	 * @return
	 */
	protected BigDecimal getChargeUtilitiesUses()
	{
		BigDecimal charge = DB.getSQLValueBD(
				get_TrxName()
				, "SELECT SUM(" + MUNSUtilitiesUses.COLUMNNAME_Cost 
				+ ") FROM " + MUNSUtilitiesUses.Table_Name 
				+ " WHERE " + MUNSUtilitiesUses.COLUMNNAME_C_Period_ID + " = ? AND " 
				+ MUNSUtilitiesUses.COLUMNNAME_UNS_Employee_ID + " =?"
				, m_HalfPeriodPresence.getC_Period_ID()
				, m_HalfPeriodPresence.getUNS_Employee_ID());
		if(null == charge)
			charge =BigDecimal.ZERO;
		
		return charge;
	}

}

class WorkerResult{
	double m_QtyProduction = 0.0;
	int m_Product_ID = 0;
	int m_JobRole_ID = 0;
	int m_UOM_ID = 0;
	
	WorkerResult(int product_ID, int job_ID){
		this.m_JobRole_ID = job_ID;
		this.m_Product_ID = product_ID;
	}
	
	double getQtyProduction(){
		return m_QtyProduction;
	}
	
	int getM_Product_ID(){
		return m_Product_ID;
	}
	
	int getUNS_JobRole_ID(){
		return m_JobRole_ID;
	}
	
	void setQtyProduction(double productionQty){
		this.m_QtyProduction = productionQty;
	}
	void setC_UOM_ID(int C_UOM_ID)
	{
		this.m_UOM_ID = C_UOM_ID;
	}
	int getC_UOM_ID()
	{
		return this.m_UOM_ID;
	}
}
