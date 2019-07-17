/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPeriod;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import com.uns.model.I_UNS_Employee;
import com.uns.model.MUNSContractRecommendation;
import com.uns.model.MUNSDailyPresence;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSEmployeeLoan;
import com.uns.model.MUNSMedicalRecord;
import com.uns.model.MUNSMonthlyPayrollEmployee;
import com.uns.model.MUNSMonthlyPresenceSummary;
import com.uns.model.MUNSPayrollBaseEmployee;
import com.uns.model.MUNSPayrollComponentConf;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSPayrollCostBenefit;
import com.uns.model.MUNSPayrollEmployee;
import com.uns.model.MUNSPayrollLevelConfig;
import com.uns.model.MUNSPayrollTermConfig;
import com.uns.model.MUNSPeriodicCostBenefit;
import com.uns.model.MUNSUtilitiesUses;


/**
 * @author menjangan @UntaSoft, Fix by ITD-Andy 27/08/2013
 *
 */
public class PayrollEmployeeGenerator extends SvrProcess 
{
	private MUNSPayrollEmployee m_PayrollEmployee = null;
	private MUNSContractRecommendation m_contract = null;
	private Properties m_ctx;
	private String m_trxName;
	
	/**
	 * 
	 */
	public PayrollEmployeeGenerator() {
		super();
	}
	
	public void setPayrollEmploye(MUNSPayrollEmployee pe)
	{
		this.m_PayrollEmployee = pe;
		this.m_ctx = pe.getCtx();
		this.m_trxName = pe.get_TrxName();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		if (getRecord_ID() <=0)
			throw new IllegalArgumentException(" NO RECORD ID !");
		
		m_ctx = super.getCtx();
		m_trxName = super.get_TrxName();
		m_PayrollEmployee = new MUNSPayrollEmployee(m_ctx, getRecord_ID(), m_trxName);
		//if (null == m_PayrollEmployee)
		//	throw new IllegalArgumentException(" Cannot find Payroll Employe record for ID " + getRecord_ID());
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String calculatePay()
	{
		try {
			return doIt();	
		} catch (Exception e) {
			String documentNo = m_PayrollEmployee.getDocumentNo();
			
			String newTrxName = Trx.createTrxName();
			m_PayrollEmployee.set_TrxName(newTrxName);
			
			return "- Failed to calculate payroll Document : " 
					+ documentNo + ". Caused by : " + e.getMessage() + " \n";
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		
		MUNSPayrollConfiguration payConfig = MUNSPayrollConfiguration.get(
				m_ctx, (MPeriod)m_PayrollEmployee.getC_Period(), m_PayrollEmployee.get_TrxName());
	
		MUNSMonthlyPresenceSummary monthlyPresence = 
				MUNSMonthlyPresenceSummary.get(
						m_ctx, m_PayrollEmployee.getUNS_Employee_ID(), m_PayrollEmployee.getC_Period_ID(), 
						m_PayrollEmployee.getAD_Org_ID(), m_PayrollEmployee.get_TrxName());
		
		if (null == monthlyPresence)
			throw new IllegalArgumentException("" +
					"Not found monthly presence for " + m_PayrollEmployee.getUNS_Employee().getName());
		
		if(!monthlyPresence.getDocStatus().equals(MUNSMonthlyPresenceSummary.DOCSTATUS_Completed)
				&& !monthlyPresence.getDocStatus().equals(MUNSMonthlyPresenceSummary.DOCSTATUS_Closed))
			throw new AdempiereUserError("Please complete presence summary before create Payroll");
		
		I_UNS_Employee employee = m_PayrollEmployee.getUNS_Employee();
		m_contract = (MUNSContractRecommendation) employee.getUNS_Contract_Recommendation();
		String payrollTerm = employee.getPayrollTerm();
		//
		String defaultPayrollTerm = MUNSPayrollTermConfig.getPayrollTermOf(
				monthlyPresence.getAD_Org_ID()
				, employee.getC_BPartner_ID()
				, m_contract.getNextContractType()
				, Env.getContextAsDate(m_ctx, "Date"), m_trxName);
		
		if(defaultPayrollTerm != null && defaultPayrollTerm.equals(payrollTerm))
			payrollTerm = defaultPayrollTerm;
		if(!m_PayrollEmployee.getPayrollTerm().equals(payrollTerm))
		{
			m_PayrollEmployee.setPayrollTerm(payrollTerm);
			m_PayrollEmployee.save();
		}
		
		MUNSPayrollBaseEmployee pbEmployee = 
				(MUNSPayrollBaseEmployee) m_PayrollEmployee.getUNS_PayrollBase_Employee();
		
		MUNSPayrollLevelConfig payrollLevel = 
				payConfig.getPayrollLevel(pbEmployee.getPayrollLevel(), payrollTerm);
		
		if (null == payrollLevel)
			throw new AdempiereUserError("Cannot find payroll level");
	
		if(MUNSMonthlyPayrollEmployee.PERIODTYPE_1Month.equals(m_PayrollEmployee.getParent().getPeriodType()))
			generatePayroll(payConfig, monthlyPresence, pbEmployee, payrollLevel);
		else
			generateDaily(payConfig, monthlyPresence, pbEmployee, payrollLevel);
		return "";
	}
	
	
	/**
	 * 
	 * @param payConfig
	 * @param monthPresence
	 * @param payrollBase
	 * @param payrollLevel
	 */
	private void generateDaily(MUNSPayrollConfiguration payConfig, 
			MUNSMonthlyPresenceSummary monthPresence, 
			MUNSPayrollBaseEmployee payrollBase, 
			MUNSPayrollLevelConfig payrollLevel)
	{
		MUNSDailyPresence[] days = null;
		MUNSMonthlyPayrollEmployee monthPay = m_PayrollEmployee.getParent();
		
		if(MUNSMonthlyPayrollEmployee.PERIODTYPE_1stWeek.equals(monthPay.getPeriodType()))
		{
			days = monthPresence.getDailyPresencesInWeek(1);
		}
		else if(MUNSMonthlyPayrollEmployee.PERIODTYPE_2ndWeek.equals(monthPay.getPeriodType()))
		{
			days = monthPresence.getDailyPresencesInWeek(2);
		}
		else if(MUNSMonthlyPayrollEmployee.PERIODTYPE_3rdWeek.equals(monthPay.getPeriodType()))
		{
			days = monthPresence.getDailyPresencesInWeek(3);
		}
		else if(MUNSMonthlyPayrollEmployee.PERIODTYPE_4thWeek.equals(monthPay.getPeriodType()))
		{
			days = monthPresence.getDailyPresencesInWeek(4);
		}
		else if(MUNSMonthlyPayrollEmployee.PERIODTYPE_1st2Weeks.equals(monthPay.getPeriodType()))
		{
			days = monthPresence.getDailyPresencesIn2Week(1);
		}
		else if(MUNSMonthlyPayrollEmployee.PERIODTYPE_2nd2Weeks.equals(monthPay.getPeriodType()))
		{
			days = monthPresence.getDailyPresencesIn2Week(1);
		}
		else
		{
			log.log(Level.SEVERE, "Unhandled period type : " + monthPay.getPeriodType());
			throw new AdempiereException(
					"Unhandled period type : " + monthPay.getPeriodType());
		}
		
		float totalLD1 = 0;
		float totalLD2 = 0;
		float totalLD3 = 0;
		float totalOT1StHours = 0;
		float totalOTNexHours = 0;
		float totalNonPayableFullDayAbsence = 0;
		float totalNonPayableHalfDayAbsence = 0;
		
		for(MUNSDailyPresence day : days)
		{
			if(MUNSDailyPresence.PRESENCESTATUS_Libur.equals(day.getPresenceStatus()))
				continue;
			
			if(null != day.getPermissionType() && !day.getPermissionType().isEmpty())
			{
				if(MUNSDailyPresence.PRESENCESTATUS_HalfDay.equals(day.getPresenceStatus()))
				{
					if (MUNSDailyPresence.PERMISSIONTYPE_Other.equals(day.getPermissionType())
							|| MUNSDailyPresence.PERMISSIONTYPE_PayPermissionIzinPotongGaji.equals(day.getPermissionType()))
						totalNonPayableHalfDayAbsence++;
				}
				else if (MUNSDailyPresence.DAYTYPE_HariKerjaBiasa.equals(day.getDayType())
						 && MUNSDailyPresence.PRESENCESTATUS_Izin.equals(day.getPresenceStatus()))
				{	
					if (MUNSDailyPresence.PERMISSIONTYPE_Other.equals(day.getPermissionType())
						|| MUNSDailyPresence.PERMISSIONTYPE_PayPermissionIzinPotongGaji.equals(
								day.getPermissionType()))
						totalNonPayableFullDayAbsence++;
				}
				
				continue;
			}
			else if(MUNSDailyPresence.PRESENCESTATUS_Mangkir.equals(day.getPresenceStatus()))
			{
				totalNonPayableFullDayAbsence++;
				continue;
			}
			else if(MUNSDailyPresence.PRESENCESTATUS_HalfDay.equals(day.getPresenceStatus()))
			{
				totalNonPayableHalfDayAbsence++;
				continue;
			}
			if (!MUNSDailyPresence.DAYTYPE_HariKerjaBiasa.equals(day.getDayType())
					&& MUNSDailyPresence.PRESENCESTATUS_Lembur.equals(day.getPresenceStatus()))
			{
				totalLD1 += day.getLD1().floatValue();
				totalLD2 += day.getLD2().floatValue();
				totalLD3 += day.getLD3().floatValue();
				continue;
			}
			
			if (MUNSDailyPresence.DAYTYPE_HariKerjaBiasa.equals(day.getDayType())
					&& MUNSDailyPresence.PRESENCESTATUS_FullDay.equals(day.getPresenceStatus()))
			{
				if (day.getOvertime().compareTo(Env.ONE) <= 0) {
					totalOT1StHours += day.getOvertime().floatValue();
				}
				else {
					totalOT1StHours++;
					totalOTNexHours += day.getOvertime().subtract(Env.ONE).floatValue();
				}
			}
		}

		BigDecimal payLD1 = payrollBase.getA_L1().multiply(BigDecimal.valueOf(totalLD1));
		BigDecimal payLD2 = payrollBase.getA_L2().multiply(BigDecimal.valueOf(totalLD2));
		BigDecimal payLD3 = payrollBase.getA_L3().multiply(BigDecimal.valueOf(totalLD3));
		BigDecimal payLemburJamPertama = Env.ZERO;
		BigDecimal payLemburJamBerikutnya = Env.ZERO;

		m_PayrollEmployee.setTotalOtherAllowances(Env.ZERO);
		m_PayrollEmployee.setTotalOtherDeductions(Env.ZERO);
		payLemburJamPertama = 
				payrollBase.getA_LemburJamPertama().multiply(BigDecimal.valueOf(totalOT1StHours));
		
		payLemburJamBerikutnya = 
				payrollBase.getA_LemburJamBerikutnya().multiply(BigDecimal.valueOf(totalOTNexHours));
			
		double tunjanganJabatan = payrollBase.getG_T_Jabatan().doubleValue();
		double tunjanganKesejahteraan = payrollBase.getG_T_Kesejahteraan().doubleValue();
		double tunjanganKhusus = payrollBase.getG_T_Khusus().doubleValue();
		double tunjanganLembur = payrollBase.getG_T_Lembur().doubleValue();
		double gajiPokok = payrollBase.getGPokok().doubleValue();
		
		float proportionPotonganMangkir = (float) (totalNonPayableFullDayAbsence + (totalNonPayableHalfDayAbsence * 0.5))
											/ payConfig.getTotalWorkDayBasis().floatValue();
		
		double potonganMankir = 0;
		
		if (payrollLevel.isUMKBase())
			gajiPokok = payrollLevel.getUMK().doubleValue();
		
		if (payrollLevel.isGPBaseOnPresence())
			potonganMankir += gajiPokok * proportionPotonganMangkir;
		
		if (payrollLevel.isTJabatanBaseOnPresence())
			potonganMankir += tunjanganJabatan * proportionPotonganMangkir;
		
		if (payrollLevel.isTKesejahteraanBaseOnPresence())
			potonganMankir += tunjanganKesejahteraan * proportionPotonganMangkir;

		if (payrollLevel.isTKhususBaseOnPresence())
			potonganMankir += tunjanganKhusus * proportionPotonganMangkir;
			
		if (payrollLevel.isTLemburBaseOnPresence()) {
			float multiplier = 1;
			float totalOTBasisHour = 0;
			float proportionPotonganLembur = 0;
			
			if(MUNSEmployee.SHIFT_NonShift.equals(monthPresence.getUNS_Employee().getShift()))
			{
				totalOTBasisHour = payConfig.getNonShiftOTAllowanceHours().signum() > 0 ? payConfig.getNonShiftOTAllowanceHours().floatValue() : 39;
				multiplier = payConfig.getNonShiftOTDeductionMultiplier().signum() > 0 ? payConfig.getNonShiftOTDeductionMultiplier().floatValue() : 1;
			}
			else
			{
				totalOTBasisHour = payConfig.getShiftOTAllowanceHours().signum() > 0 ? payConfig.getShiftOTAllowanceHours().floatValue() : 39;
				multiplier = payConfig.getShiftOTDeductionMultiplier().signum() > 0 ? payConfig.getShiftOTDeductionMultiplier().floatValue() : 1;
			}
			
			if(payConfig.isNonShiftAllWorkDayIsOT() || payConfig.isNonShiftAllWorkDayIsOT())
			{
				proportionPotonganLembur = (float) ((totalNonPayableFullDayAbsence 
						+ (totalNonPayableHalfDayAbsence * 0.5)) * multiplier) / totalOTBasisHour;
			}
			else
			{
				proportionPotonganLembur = getProportionPotonganLembur(payConfig, days, multiplier, totalOTBasisHour);
			}
			
			double potonganLembur = tunjanganLembur * proportionPotonganLembur;
			tunjanganLembur -= potonganLembur;
		}
		int scalse = MUNSPayrollEmployee.SCALE_PRECISION;
		m_PayrollEmployee.setA_LemburJamPertama(payLemburJamPertama.setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setA_LemburJamBerikutnya(payLemburJamBerikutnya.setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setA_L1(payLD1.setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setA_L2(payLD2.setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setA_L3(payLD3.setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setA_Other(payrollBase.getA_Other().setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setA_Rapel(payrollBase.getA_Rapel().setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setG_T_Jabatan(new BigDecimal(tunjanganJabatan).setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setG_T_Kesejahteraan(new BigDecimal(tunjanganKesejahteraan).setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setG_T_Khusus(new BigDecimal(tunjanganKhusus).setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setG_T_Lembur(new BigDecimal(tunjanganLembur).setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setGPokok(new BigDecimal(gajiPokok).setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setP_ListrikAir(getChargeUtilitiesUses().setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setP_Mangkir(new BigDecimal(potonganMankir).setScale(scalse, RoundingMode.HALF_UP));
		
		BigDecimal totalBiayaBerobat1Period = MUNSMedicalRecord.getTotalBiayaBerobat1Period(
				m_PayrollEmployee.getUNS_Employee_ID(), payConfig, m_PayrollEmployee.getC_Period(), m_trxName);
		m_PayrollEmployee.setP_Obat(totalBiayaBerobat1Period.setScale(scalse, RoundingMode.HALF_UP));
		
		m_PayrollEmployee.setP_Other(payrollBase.getP_Other().setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setP_SPTP(payrollBase.getP_SPTP().setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setJamSosTek(payConfig, payrollLevel, payrollBase);

		MUNSPayrollCostBenefit[] costBens = m_PayrollEmployee.getCostBenefits(false);
		BigDecimal takeHomePay = m_PayrollEmployee.getPayableBruto().subtract(m_PayrollEmployee.getPayrollDeduction());
		MUNSPayrollComponentConf[] conf = MUNSPayrollComponentConf.get(m_contract);
		BigDecimal tCosts = Env.ZERO;
		BigDecimal tBenefit = Env.ZERO;
		
		//loop additional
		for (int i=0; i<conf.length; i++)
		{
			if (!conf[i].isBenefit())
				continue;
			BigDecimal amount = conf[i].getAmount();
			MUNSPayrollCostBenefit costBen = null;
			for (int j=0; j<costBens.length; j++) {
				if (costBens[j].getUNS_Payroll_Component_Conf_ID() == conf[i].get_ID() && conf[i].isBenefit()) {
					costBen = costBens[j];
					break;
				}
			}
			if (costBen == null)
				costBen = new MUNSPayrollCostBenefit(m_PayrollEmployee, conf[i]);
			if (conf[i].isBaseonPresence()) {
				amount = new BigDecimal(amount.doubleValue() * proportionPotonganMangkir).setScale(scalse, RoundingMode.HALF_UP);
				MUNSPayrollCostBenefit deduction = null;
				for (int j=0; j<costBens.length; j++) {
					if (costBens[j].getUNS_Payroll_Component_Conf_ID() == conf[i].get_ID() && !conf[i].isBenefit()) {
						deduction = costBens[j];
						break;
					}
				}
				if (deduction == null) {
					deduction = new MUNSPayrollCostBenefit(m_PayrollEmployee, conf[i]);
					deduction.setName((MUNSPayrollEmployee.PREFIX_POTONGAN+ conf[i].getName()));
				}
				BigDecimal deductionAmt = conf[i].getAmount().subtract(amount);
				deduction.setAmount(deductionAmt);
				if (!deduction.save())
					throw new AdempiereException(CLogger.retrieveErrorString("Failed when try to create cost or benefit"));
			}
			costBen.setAmount(conf[i].getAmount());
			if (!costBen.save())
				throw new AdempiereException(CLogger.retrieveErrorString("Failed when try to create cost or benefit"));
			takeHomePay = takeHomePay.add(costBen.getAmount());
			tBenefit = tBenefit.add(amount);
		}
		if (takeHomePay.signum() == 1)
		{
			//loop costs
			for (int i=0; takeHomePay.signum() == 1 && i<conf.length; i++) 
			{
				if (conf[i].isBenefit())
					continue;
				MUNSPayrollCostBenefit costBen = null;
				for (int j=0; j<costBens.length; j++) {
					if (costBens[j].getUNS_Payroll_Component_Conf_ID() == conf[i].get_ID() && conf[i].isBenefit()) {
						costBen = costBens[j];
						break;
					}
				}
				if (costBen == null)
					costBen = new MUNSPayrollCostBenefit(m_PayrollEmployee, conf[i]);
				BigDecimal amount = conf[i].getAmount();
				if (MUNSPayrollComponentConf.COSTBENEFITTYPE_Cooperative.equals(
						conf[i].getCostBenefitType()))
				{
					amount = MUNSPeriodicCostBenefit.getMyCostOrBenefit(
							m_PayrollEmployee.getUNS_Employee_ID(), MUNSPeriodicCostBenefit.COSTBENEFITTYPE_Cooperative, get_TrxName());
				}
				else if (MUNSPayrollComponentConf.COSTBENEFITTYPE_Canteen.equals(
						conf[i].getCostBenefitType()))
				{

					amount = MUNSPeriodicCostBenefit.getMyCostOrBenefit(
							m_PayrollEmployee.getUNS_Employee_ID(), MUNSPeriodicCostBenefit.COSTBENEFITTYPE_Canteen, get_TrxName());
				}
				else if ("PKR".equals(conf[i].getCostBenefitType()))
				{
					double totalInstallmentCompany = 0.0;
//					double totalInstallmentKoprasi = 0.0;
					
					List<MUNSEmployeeLoan> listOfEmployeeLoan = MUNSEmployeeLoan.gets(
							m_ctx, m_PayrollEmployee.getUNS_Employee_ID(), m_trxName);
					
					for (MUNSEmployeeLoan employeeLoan : listOfEmployeeLoan)
					{
						if (employeeLoan.getLoanAmtLeft().compareTo(BigDecimal.ZERO) <= 0)
							continue;
						if (employeeLoan.getLoanAmtLeft().compareTo(employeeLoan.getInstallment()) >= 0)
						{
//							if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Koperasi))
//								totalInstallmentKoprasi += employeeLoan.getInstallment().doubleValue();
//							else //if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Company))
								totalInstallmentCompany += employeeLoan.getInstallment().doubleValue();
						} 
						else 
						{
//							if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Koperasi))
//								totalInstallmentKoprasi += employeeLoan.getLoanAmtLeft().doubleValue();
//							else //if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Company))
								totalInstallmentCompany += employeeLoan.getLoanAmtLeft().doubleValue();
						}
					}
					amount = new BigDecimal(totalInstallmentCompany).setScale(scalse, RoundingMode.HALF_UP);
				}
				
				if (takeHomePay.compareTo(amount) == -1)
					amount = takeHomePay;
				
				costBen.setAmount(amount);
				
				if (!costBen.save())
					throw new AdempiereException(CLogger.retrieveErrorString("Failed when try to create cost or benefit"));
				takeHomePay = takeHomePay.subtract(costBen.getAmount());
				tCosts = tCosts.add(amount);
			}
			
			m_PayrollEmployee.setTotalOtherAllowances(tBenefit);
			m_PayrollEmployee.setTotalOtherDeductions(tCosts);
		}
			
//		m_PayrollEmployee.setPPH21(payConfig.getPPH(m_PayrollEmployee).setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setGeneratePay("Y");
		try{
			m_PayrollEmployee.saveEx();
		}catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}
			//throw new IllegalArgumentException("Failed to generate Payroll Employee ");
	}
	
	/**
	 * 
	 * @param payConfig
	 * @param monthPresence
	 * @param payrollBase
	 * @param payrollLevel
	 */
	private void generatePayroll(MUNSPayrollConfiguration payConfig, 
								MUNSMonthlyPresenceSummary monthPresence, 
								MUNSPayrollBaseEmployee payrollBase, 
								MUNSPayrollLevelConfig payrollLevel)
	{
		BigDecimal payLD1 = payrollBase.getA_L1().multiply(monthPresence.getTotalLD1());
		BigDecimal payLD2 = payrollBase.getA_L2().multiply(monthPresence.getTotalLD2());
		BigDecimal payLD3 = payrollBase.getA_L3().multiply(monthPresence.getTotalLD3());
		BigDecimal payLemburJamPertama = Env.ZERO;
		BigDecimal payLemburJamBerikutnya = Env.ZERO;
		m_PayrollEmployee.setTotalOtherAllowances(Env.ZERO);
		m_PayrollEmployee.setTotalOtherDeductions(Env.ZERO);
		MUNSMonthlyPayrollEmployee monthPay = m_PayrollEmployee.getParent();
		
		if(monthPresence.getTotalOvertime().signum() > 0 && !m_PayrollEmployee.isAllIn() 
				&& monthPay.getPeriodType().equals(MUNSMonthlyPayrollEmployee.PERIODTYPE_1Month))
		{
			payLemburJamPertama = 
					payrollBase.getA_LemburJamPertama().multiply(monthPresence.getTotalOvertime1stHour());
			
			payLemburJamBerikutnya = 
					payrollBase.getA_LemburJamBerikutnya().multiply(monthPresence.getTotalOvertimeNextHour());
		}
		
		double tunjanganJabatan = payrollBase.getG_T_Jabatan().doubleValue();
		double tunjanganKesejahteraan = payrollBase.getG_T_Kesejahteraan().doubleValue();
		double tunjanganKhusus = payrollBase.getG_T_Khusus().doubleValue();
		double tunjanganLembur = payrollBase.getG_T_Lembur().doubleValue();
		double gajiPokok = payrollBase.getGPokok().doubleValue();
		
		//I_UNS_Contract_Recommendation contract = payrollBase.getUNS_Contract_Recommendation();
		
		/*
		if (contract.getNextEmploymentStatus().equals(MUNSContractRecommendation.NEXTEMPLOYMENTSTATUS_Harian))
		{
			payFullDay *= (monthPresence.getTotalFullDayPresence() + monthPresence.getTotalSK().doubleValue() 
					+ monthPresence.getTotalSKK().doubleValue());
			
			payHalfDay *= monthPresence.getTotalHalfDayPresence();
			
			gajiPokok = payFullDay + payHalfDay;
		}
		*/
		
		float proportionPotonganMangkir = 
				monthPresence.getProportionPotonganMangkir() / payConfig.getTotalWorkDayBasis().floatValue();
//		double potonganMankir = 0;
		MUNSPayrollCostBenefit[] costBens = m_PayrollEmployee.getCostBenefits(false);
		
		if (payrollLevel.isUMKBase())
			gajiPokok = payrollLevel.getUMK().doubleValue();
		
		if (payrollLevel.isGPBaseOnPresence()) {
			BigDecimal potonganMankir = new BigDecimal(gajiPokok * proportionPotonganMangkir);
			MUNSPayrollCostBenefit costBen = null;
			for (int i=0; i<costBens.length; i++) {
				if (MUNSPayrollEmployee.POTONGAN_GAJI_POKOK.equals(costBens[i].getName())
						&& ! costBens[i].isBenefit()) {
					costBen = costBens[i];
					break;
				}
			}
			if (costBen == null) {
				costBen = new MUNSPayrollCostBenefit(m_PayrollEmployee, null);
				costBen.setIsBenefit(false);
				costBen.setName(MUNSPayrollEmployee.POTONGAN_GAJI_POKOK);
				costBen.setUNS_Payroll_Component_Conf_ID(-1);
				costBen.setCostBenefit_Acct(payConfig.getBiayaGajiBulananAcct_ID());
				costBen.setAmount(potonganMankir);
			}
		}
//		if (payrollLevel.isTJabatanBaseOnPresence())
//			potonganMankir += tunjanganJabatan * proportionPotonganMangkir;
//		
//		if (payrollLevel.isTKesejahteraanBaseOnPresence())
//			potonganMankir += tunjanganKesejahteraan * proportionPotonganMangkir;
//
//		if (payrollLevel.isTKhususBaseOnPresence())
//			potonganMankir += tunjanganKhusus * proportionPotonganMangkir;
//			
//		if (payrollLevel.isTLemburBaseOnPresence()) {
//			float proportionPotonganLembur = monthPresence.getProportionPotonganLembur(payConfig);
//			double potonganLembur = tunjanganLembur * proportionPotonganLembur;
//			tunjanganLembur -= potonganLembur;
//		}
		int scalse = MUNSPayrollEmployee.SCALE_PRECISION;
		m_PayrollEmployee.setA_LemburJamPertama(payLemburJamPertama.setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setA_LemburJamBerikutnya(payLemburJamBerikutnya.setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setA_L1(payLD1.setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setA_L2(payLD2.setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setA_L3(payLD3.setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setA_Other(payrollBase.getA_Other().setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setA_Rapel(payrollBase.getA_Rapel().setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setG_T_Jabatan(new BigDecimal(tunjanganJabatan).setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setG_T_Kesejahteraan(new BigDecimal(tunjanganKesejahteraan).setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setG_T_Khusus(new BigDecimal(tunjanganKhusus).setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setG_T_Lembur(new BigDecimal(tunjanganLembur).setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setGPokok(new BigDecimal(gajiPokok).setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setP_ListrikAir(getChargeUtilitiesUses().setScale(scalse, RoundingMode.HALF_UP));
//		m_PayrollEmployee.setP_Mangkir(new BigDecimal(potonganMankir).setScale(scalse, RoundingMode.HALF_UP));
		
		BigDecimal totalBiayaBerobat1Period = MUNSMedicalRecord.getTotalBiayaBerobat1Period(
				m_PayrollEmployee.getUNS_Employee_ID(), payConfig, m_PayrollEmployee.getC_Period(), m_trxName);
		m_PayrollEmployee.setP_Obat(totalBiayaBerobat1Period.setScale(scalse, RoundingMode.HALF_UP));
		
		m_PayrollEmployee.setP_Other(payrollBase.getP_Other().setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setP_SPTP(payrollBase.getP_SPTP().setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setJamSosTek(payConfig, payrollLevel, payrollBase);
		
		BigDecimal takeHomePay = m_PayrollEmployee.getPayableBruto().subtract(m_PayrollEmployee.getPayrollDeduction());
		MUNSPayrollComponentConf[] conf = MUNSPayrollComponentConf.get(m_contract);
		BigDecimal tCosts = Env.ZERO;
		BigDecimal tBenefit = Env.ZERO;
		//loop additional
		for (int i=0; i<conf.length; i++)
		{
			if (!conf[i].isBenefit())
				continue;
			BigDecimal amount = conf[i].getAmount();
			MUNSPayrollCostBenefit costBen = null;
			for (int j=0; j<costBens.length; j++) {
				if (costBens[j].getUNS_Payroll_Component_Conf_ID() == conf[i].get_ID() && conf[i].isBenefit()) {
					costBen = costBens[j];
					break;
				}
			}
			if (costBen == null)
				costBen = new MUNSPayrollCostBenefit(m_PayrollEmployee, conf[i]);
			if (MUNSPeriodicCostBenefit.COSTBENEFITTYPE_Bonuses.equals(conf[i].getCostBenefitType())) {
				amount = MUNSPeriodicCostBenefit.getMyCostOrBenefit(
						m_PayrollEmployee.getUNS_Employee_ID(), MUNSPeriodicCostBenefit.COSTBENEFITTYPE_Bonuses, get_TrxName());
			}
			if (conf[i].isBaseonPresence()) {
				amount = new BigDecimal(amount.doubleValue() * proportionPotonganMangkir).setScale(scalse, RoundingMode.HALF_UP);
				MUNSPayrollCostBenefit deduction = null;
				for (int j=0; j<costBens.length; j++) {
					if (costBens[j].getUNS_Payroll_Component_Conf_ID() == conf[i].get_ID() && !conf[i].isBenefit()) {
						deduction = costBens[j];
						break;
					}
				}
				if (deduction == null) {
					deduction = new MUNSPayrollCostBenefit(m_PayrollEmployee, conf[i]);
					deduction.setName((MUNSPayrollEmployee.PREFIX_POTONGAN+ conf[i].getName()));
				}
				BigDecimal deductionAmt = conf[i].getAmount().subtract(amount);
				deduction.setAmount(deductionAmt);
				if (!deduction.save())
					throw new AdempiereException(CLogger.retrieveErrorString("Failed when try to create cost or benefit"));
			}
			costBen.setAmount(amount);
			if (!costBen.save())
				throw new AdempiereException(CLogger.retrieveErrorString("Failed when try to create cost or benefit"));
			takeHomePay = takeHomePay.add(costBen.getAmount());
			tBenefit = tBenefit.add(amount);
		}
		boolean isPPhCalculated = false;
		if (takeHomePay.signum() == 1)
		{
			for (int i=0; takeHomePay.signum() == 1 && i<conf.length; i++)
			{
				if (!conf[i].isPPHComp())
					continue;
				if (conf[i].isBenefit())
					continue;
				MUNSPayrollCostBenefit costBen = null;
				for (int j=0; j<costBens.length; j++) {
					if (costBens[j].getUNS_Payroll_Component_Conf_ID() == conf[i].get_ID() && conf[i].isBenefit()) {
						costBen = costBens[j];
						break;
					}
				}
				if (costBen == null)
					costBen = new MUNSPayrollCostBenefit(m_PayrollEmployee, conf[i]);
				BigDecimal amount = conf[i].getAmount();
				if (MUNSPayrollComponentConf.COSTBENEFITTYPE_Cooperative.equals(
						conf[i].getCostBenefitType()))
				{
					amount = MUNSPeriodicCostBenefit.getMyCostOrBenefit(
							m_PayrollEmployee.getUNS_Employee_ID(), MUNSPeriodicCostBenefit.COSTBENEFITTYPE_Cooperative, get_TrxName());
				}
				else if (MUNSPayrollComponentConf.COSTBENEFITTYPE_Canteen.equals(
						conf[i].getCostBenefitType()))
				{

					amount = MUNSPeriodicCostBenefit.getMyCostOrBenefit(
							m_PayrollEmployee.getUNS_Employee_ID(), MUNSPeriodicCostBenefit.COSTBENEFITTYPE_Canteen, get_TrxName());
				}
				else if ("PKR".equals(conf[i].getCostBenefitType()))
				{
					double totalInstallmentCompany = 0.0;
//					double totalInstallmentKoprasi = 0.0;
					
					List<MUNSEmployeeLoan> listOfEmployeeLoan = MUNSEmployeeLoan.gets(
							m_ctx, m_PayrollEmployee.getUNS_Employee_ID(), m_trxName);
					
					for (MUNSEmployeeLoan employeeLoan : listOfEmployeeLoan)
					{
						if (employeeLoan.getLoanAmtLeft().compareTo(BigDecimal.ZERO) <= 0)
							continue;
						if (employeeLoan.getLoanAmtLeft().compareTo(employeeLoan.getInstallment()) >= 0)
						{
//							if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Koperasi))
//								totalInstallmentKoprasi += employeeLoan.getInstallment().doubleValue();
//							else //if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Company))
								totalInstallmentCompany += employeeLoan.getInstallment().doubleValue();
						} 
						else 
						{
//							if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Koperasi))
//								totalInstallmentKoprasi += employeeLoan.getLoanAmtLeft().doubleValue();
//							else //if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Company))
								totalInstallmentCompany += employeeLoan.getLoanAmtLeft().doubleValue();
						}
					}
					amount = new BigDecimal(totalInstallmentCompany).setScale(scalse, RoundingMode.HALF_UP);
				}
				
				if (takeHomePay.compareTo(amount) == -1)
					amount = takeHomePay;
				
				costBen.setAmount(amount);
				
				if (!costBen.save())
					throw new AdempiereException(CLogger.retrieveErrorString("Failed when try to create cost or benefit"));
				takeHomePay = takeHomePay.subtract(costBen.getAmount());
				tCosts = tCosts.add(amount);
			}
			m_PayrollEmployee.setPPH21(payConfig.getPPH(m_PayrollEmployee).setScale(scalse, RoundingMode.HALF_UP));
			isPPhCalculated = true;
			//loop costs
			for (int i=0; takeHomePay.signum() == 1 && i<conf.length; i++) 
			{
				if (conf[i].isPPHComp())
					continue;
				if (conf[i].isBenefit())
					continue;
				MUNSPayrollCostBenefit costBen = null;
				for (int j=0; j<costBens.length; j++) {
					if (costBens[j].getUNS_Payroll_Component_Conf_ID() == conf[i].get_ID()) {
						costBen = costBens[j];
						break;
					}
				}
				if (costBen == null)
					costBen = new MUNSPayrollCostBenefit(m_PayrollEmployee, conf[i]);
				BigDecimal amount = conf[i].getAmount();
				if (MUNSPayrollComponentConf.COSTBENEFITTYPE_Cooperative.equals(
						conf[i].getCostBenefitType()))
				{
					amount = MUNSPeriodicCostBenefit.getMyCostOrBenefit(
							m_PayrollEmployee.getUNS_Employee_ID(), MUNSPeriodicCostBenefit.COSTBENEFITTYPE_Cooperative, get_TrxName());
				}
				else if (MUNSPayrollComponentConf.COSTBENEFITTYPE_Canteen.equals(
						conf[i].getCostBenefitType()))
				{

					amount = MUNSPeriodicCostBenefit.getMyCostOrBenefit(
							m_PayrollEmployee.getUNS_Employee_ID(), MUNSPeriodicCostBenefit.COSTBENEFITTYPE_Canteen, get_TrxName());
				}
				else if (MUNSPayrollComponentConf.COSTBENEFITTYPE_PinjamanKaryawan.equals(
						conf[i].getCostBenefitType()))
				{
					double totalInstallmentCompany = 0.0;
//					double totalInstallmentKoprasi = 0.0;
					
					List<MUNSEmployeeLoan> listOfEmployeeLoan = MUNSEmployeeLoan.gets(
							m_ctx, m_PayrollEmployee.getUNS_Employee_ID(), m_trxName);
					
					for (MUNSEmployeeLoan employeeLoan : listOfEmployeeLoan)
					{
						if (employeeLoan.getLoanAmtLeft().compareTo(BigDecimal.ZERO) <= 0)
							continue;
						if (employeeLoan.getLoanAmtLeft().compareTo(employeeLoan.getInstallment()) >= 0)
						{
//							if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Koperasi))
//								totalInstallmentKoprasi += employeeLoan.getInstallment().doubleValue();
//							else //if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Company))
								totalInstallmentCompany += employeeLoan.getInstallment().doubleValue();
						} 
						else 
						{
//							if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Koperasi))
//								totalInstallmentKoprasi += employeeLoan.getLoanAmtLeft().doubleValue();
//							else //if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Company))
								totalInstallmentCompany += employeeLoan.getLoanAmtLeft().doubleValue();
						}
					}
					amount = new BigDecimal(totalInstallmentCompany).setScale(scalse, RoundingMode.HALF_UP);
				}
				
				if (takeHomePay.compareTo(amount) == -1)
					amount = takeHomePay;
				
				costBen.setAmount(amount);
				
				if (!costBen.save())
					throw new AdempiereException(CLogger.retrieveErrorString("Failed when try to create cost or benefit"));
				takeHomePay = takeHomePay.subtract(costBen.getAmount());
				tCosts = tCosts.add(amount);
			}
			
			m_PayrollEmployee.setTotalOtherAllowances(tBenefit);
			m_PayrollEmployee.setTotalOtherDeductions(tCosts);
		}
		if (!isPPhCalculated)
			m_PayrollEmployee.setPPH21(payConfig.getPPH(m_PayrollEmployee).setScale(scalse, RoundingMode.HALF_UP));
		m_PayrollEmployee.setGeneratePay("Y");
		try{
			m_PayrollEmployee.saveEx();
		}catch (Exception ex){
			throw new AdempiereException(ex.getMessage());
		}
			//throw new IllegalArgumentException("Failed to generate Payroll Employee ");
	}
	
	/**
	 * 
	 */
//	protected void setLoanInstallment()
//	{
//		double totalInstallmentCompany = 0.0;
//		double totalInstallmentKoprasi = 0.0;
//		
//		List<MUNSEmployeeLoan> listOfEmployeeLoan = MUNSEmployeeLoan.gets(
//				m_ctx, m_PayrollEmployee.getUNS_Employee_ID(), m_trxName);
//		
//		for (MUNSEmployeeLoan employeeLoan : listOfEmployeeLoan)
//		{
//			if (employeeLoan.getLoanAmtLeft().compareTo(BigDecimal.ZERO) <= 0)
//				continue;
//			if (employeeLoan.getLoanAmtLeft().compareTo(employeeLoan.getInstallment()) >= 0)
//			{
//				if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Koperasi))
//					totalInstallmentKoprasi += employeeLoan.getInstallment().doubleValue();
//				else //if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Company))
//					totalInstallmentCompany += employeeLoan.getInstallment().doubleValue();
//			} 
//			else 
//			{
//				if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Koperasi))
//					totalInstallmentKoprasi += employeeLoan.getLoanAmtLeft().doubleValue();
//				else //if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Company))
//					totalInstallmentCompany += employeeLoan.getLoanAmtLeft().doubleValue();
//			}
//		}
//		int scalse = MUNSPayrollEmployee.SCALE_PRECISION;
//		m_PayrollEmployee.setP_PinjamanKaryawan(
//				new BigDecimal(totalInstallmentCompany).setScale(scalse, RoundingMode.HALF_UP));
//		m_PayrollEmployee.setP_Koperasi(
//				new BigDecimal(totalInstallmentKoprasi).setScale(scalse, RoundingMode.HALF_UP));
//	}
	
	/**
	 * 
	 * @return
	 */
	protected BigDecimal getChargeUtilitiesUses()
	{
		BigDecimal charge = DB.getSQLValueBD(
				m_trxName
				, "SELECT SUM(" + MUNSUtilitiesUses.COLUMNNAME_Cost 
				+ ") FROM " + MUNSUtilitiesUses.Table_Name 
				+ " WHERE " + MUNSUtilitiesUses.COLUMNNAME_C_Period_ID + " = ? AND " 
				+ MUNSUtilitiesUses.COLUMNNAME_UNS_Employee_ID + " =?"
				, m_PayrollEmployee.getC_Period_ID()
				, m_PayrollEmployee.getUNS_Employee_ID());
		if(null == charge)
			charge =BigDecimal.ZERO;
		
		//BigDecimal minWTCharge = payConfig.getBiayaAir().multiply(payConfig.getMinWaterUsage());
		//BigDecimal minELCCharge = payConfig.getBiayaListrik().multiply(payConfig.getMinElectricityUsage());
		
		
		return charge;
	}
	
	/**
	 * 
	 * @param payrollConfig
	 * @param days
	 * @param multiplier
	 * @param totalOTBasisHour
	 * @return
	 */
	public float getProportionPotonganLembur(MUNSPayrollConfiguration payrollConfig, 
			MUNSDailyPresence[] days, float multiplier, float totalOTBasisHour)
	{
		int totalNonPayableAbsence = 0;
		float proportionPotongan = 0;
		
			for (MUNSDailyPresence daily : days)
			{
				if (daily.getDay().equals(payrollConfig.getNonShiftOTDay())
					&& MUNSDailyPresence.DAYTYPE_HariKerjaBiasa.equals(daily.getDayType())) 
				{
					if (MUNSDailyPresence.PRESENCESTATUS_Mangkir.equals(daily.getPresenceStatus()))
						totalNonPayableAbsence++;
					else if (daily.getPermissionType() != null && !daily.getPermissionType().isEmpty()
							 && MUNSDailyPresence.PRESENCESTATUS_Izin.equals(daily.getPresenceStatus())
							 && (MUNSDailyPresence.PERMISSIONTYPE_Other.equals(daily.getPermissionType())
								 || MUNSDailyPresence.PERMISSIONTYPE_PayPermissionIzinPotongGaji.equals(
										daily.getPermissionType())))
						totalNonPayableAbsence++;
				}
			}
		proportionPotongan = (totalNonPayableAbsence * multiplier) / totalOTBasisHour;
		
		return proportionPotongan;
	}
}
