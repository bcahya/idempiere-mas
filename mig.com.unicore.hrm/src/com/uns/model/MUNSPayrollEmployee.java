/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;
import com.uns.model.process.PayrollEmployeeGenerator;

/**
 * @author menjangan
 *
 */
public class MUNSPayrollEmployee extends X_UNS_Payroll_Employee implements DocAction, DocOptions {

	public static final String POTONGAN_GAJI_POKOK = "P.GP BASE ON PRESENCE";
	public static final String PREFIX_POTONGAN = "POTONGAN_";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7666909915012518549L;
	
	public static final int SCALE_PRECISION = 0;
	private MUNSMonthlyPayrollEmployee m_parent = null;
	private MUNSPayrollCostBenefit[] m_CostBenefits = null;
	
	public MUNSPayrollCostBenefit[] getCostBenefits (boolean requery)
	{
		if (m_CostBenefits != null && !requery)
		{
			set_TrxName(m_CostBenefits, get_TrxName());
			return m_CostBenefits;
		}
		String wc = "UNS_Payroll_Employee_ID = ?";
		List<MUNSPayrollCostBenefit> list = Query.get(
				getCtx(), UNSHRMModelFactory.EXTENSION_ID, 
				MUNSPayrollCostBenefit.Table_Name, wc, get_TrxName()).
				setParameters(getUNS_Payroll_Employee_ID()).list();
		m_CostBenefits = new MUNSPayrollCostBenefit[list.size()];
		list.toArray(m_CostBenefits);
		return m_CostBenefits;
	}

	/**
	 * @param ctx
	 * @param UNS_Payroll_Employee_ID
	 * @param trxName
	 */
	public MUNSPayrollEmployee(Properties ctx, int UNS_Payroll_Employee_ID,
			String trxName) {
		super(ctx, UNS_Payroll_Employee_ID, trxName);
		// 
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPayrollEmployee(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// 
	}
	
	public MUNSPayrollEmployee(MUNSMonthlyPayrollEmployee parent,int i)
	{
		super(parent.getCtx(), 0, parent.get_TrxName());
		setAD_Org_ID(parent.getAD_Org_ID());
		setUNS_MonthlyPayroll_Employee_ID(parent.get_ID());
		setC_Period_ID(parent.getC_Period_ID());
		setDocStatus(parent.getDocStatus());
		setDocAction(parent.getDocAction());
		setGPokok(BigDecimal.ZERO);
		setTakeHomePay(BigDecimal.ZERO);
		setDocumentNo(parent.getDocumentNo()+ "-" + i);
		m_parent = parent;
	}
	
	/**
	 * 
	 *
	public void rescaleAllAmounts() {
		int precision = MUOM.getPrecision(getCtx(), getC_UOM_ID());
		GeneralCustomization.setScaleOf(this, NUMBERTYPE_COLUMNS, precision, BigDecimal.ROUND_HALF_UP, false);
	}
	*/
	
	@Override
	public boolean beforeSave(boolean newRecord){
		if (getDocStatus().equals(DOCSTATUS_Drafted)){
			if (getP_PinjamanKaryawan().compareTo(MUNSEmployeeLoan.getLoanToCompany(
					getCtx(), getUNS_Employee_ID(), get_TrxName()))>0)
				throw new AdempiereUserError("Payment of employee loans more than remaining loans, " +
						"Decrease company payment loans");
		
//			if (getP_Koperasi().compareTo(MUNSEmployeeLoan.getLoanToKoperasi(
//					getCtx(), getUNS_Employee_ID(), get_TrxName()))>0)
//				throw new AdempiereUserError("Payment of employee loans more than remaining loans, " +
//						"Decrease koperasi payment loans");
		}
		
//		for(MDocType cDocType : MDocType.getOfDocBaseType(getCtx(), "PRE")){
//			if (!cDocType.isSOTrx())
//				setC_DocType_ID(cDocType.getC_DocType_ID());
//		}
		
		setTakeHomePay(
				getPayableBruto().subtract(getPayrollDeduction())
				.setScale(SCALE_PRECISION, RoundingMode.HALF_UP));
		
		setStartDate(getParent().getStartDate());
		setEndDate(getParent().getEndDate());
		
		return super.beforeSave(newRecord);
	}
	
	/**
	 * set all jamsostek
	 * @param payConfig
	 */
	public void setJamSosTek(MUNSPayrollConfiguration payConfig, MUNSPayrollLevelConfig level, MUNSPayrollBaseEmployee payrollBase)
	{
		double amountJPKPaidByCom = 0.0;
		double amountJPKPaidByEmp = 0.0;
		double amountJKKPaidByCom = 0.0;
		double amountJKKPaidByEmp = 0.0;
		double amountJKPaidByCom = 0.0;
		double amountJKPaidByEmp = 0.0;
		double amountJHTPaidByCom = 0.0;
		double amountJHTPaidByEmp = 0.0;
		double amountJPPaidByComp = 0.0;
		double amountJPPaidByEmp = 0.0;
		double amtJPK = 0.0;
		double amtJK = 0.0;
		double amtJHT = 0.0;
		double amtJKK = 0.0;
		double amtJP = 0.0;
	
		double jpkKawinPaidByComp = payConfig.getJPKKawinPaidByCompany().doubleValue()/100;
		double jpkKawinPaidByEmp = payConfig.getJPKKawinPaidByEmployee().doubleValue()/100;
		double jpkLajangPaidByComp = payConfig.getJPKLajangPaidByCompany().doubleValue()/100;
		double jpkLajangPaidByEmp = payConfig.getJPKLajangPaidByEmployee().doubleValue()/100;
		double jkPaidByComp = payConfig.getJKPaidByCompany().doubleValue()/100;
		double jkPaidByEmp = payConfig.getJKPaidByEmployee().doubleValue()/100;
		double jkkPaidByComp = payConfig.getJKKPaidByCompany().doubleValue()/100;
		double jkkPaidByEmp = payConfig.getJKKPaidByEmployee().doubleValue()/100;
		double jhtPaidByComp = payConfig.getJHTPaidByCompany().doubleValue()/100;
		double jhtPaidByEmp = payConfig.getJHTPaidByEmployee().doubleValue()/100;
		double jpPaidByComp = payConfig.getJPPaidByCompany().doubleValue()/100;
		double jpPaidByEmp =payConfig.getJPPaidByEmployee().doubleValue()/100;
		
		double gajiBruto = getPayableBruto().doubleValue();
		double potongan = getPayrollDeduction().doubleValue();
		
		if (payrollBase.isJPKApplyed())
		{
			if(payConfig.getJPKBasicCalculation().equals(
					MUNSPayrollConfiguration.JPKBASICCALCULATION_BaseOnBasicSalary))
			{
				amtJPK = getGPokok().doubleValue();
				if(amtJPK > payConfig.getMaxGajiToCalcJPK().doubleValue())
					amtJPK = payConfig.getMaxGajiToCalcJPK().doubleValue();
			}
			else if (payConfig.getJPKBasicCalculation().equals(
					MUNSPayrollConfiguration.JPKBASICCALCULATION_BaseOnNettoSalary))
			{
				amtJPK = gajiBruto-potongan;
			}
			else if (payConfig.getJPKBasicCalculation().equals(
					MUNSPayrollConfiguration.JPKBASICCALCULATION_BaseOnBruttoSalary))
			{
				amtJPK = gajiBruto;
			}
			else if (MUNSPayrollConfiguration.JPKBASICCALCULATION_BaseOnUMK.equals(
					payConfig.getJPKBasicCalculation()))
			{
				amtJPK = level.getUMK().doubleValue();
				if (amtJPK == 0)
					amtJPK = payConfig.getUMK().doubleValue();
			}
			else if (MUNSPayrollConfiguration.JPKBASICCALCULATION_BaseOnUMP.equals(
					payConfig.getJPKBasicCalculation()))
			{
				amtJPK = level.getUMP().doubleValue();
				if (amtJPK == 0)
					amtJPK = payConfig.getUMP().doubleValue();
			}
		}
		
		if (payrollBase.isJKApplyed())
		{
			if(payConfig.getJKBasicCalculation().equals(
					MUNSPayrollConfiguration.JKBASICCALCULATION_BaseOnBasicSalary))
			{
				amtJK = getGPokok().doubleValue();
			}
			else if (payConfig.getJKBasicCalculation().equals(
					MUNSPayrollConfiguration.JKBASICCALCULATION_BaseOnNettoSalary))
			{
				amtJK = gajiBruto-potongan;
			}
			else if (payConfig.getJKBasicCalculation().equals(
					MUNSPayrollConfiguration.JKBASICCALCULATION_BaseOnBruttoSalary))
			{
				amtJK = gajiBruto;
			}
			else if (MUNSPayrollConfiguration.JPKBASICCALCULATION_BaseOnUMK.equals(
					payConfig.getJKBasicCalculation()))
			{
				amtJK = level.getUMK().doubleValue();
				if (amtJK == 0)
					amtJK = payConfig.getUMK().doubleValue();
			}
			else if (MUNSPayrollConfiguration.JKBASICCALCULATION_BaseOnUMP.equals(
					payConfig.getJKBasicCalculation()))
			{
				amtJK = level.getUMP().doubleValue();
				if (amtJK == 0)
					amtJK = payConfig.getUMP().doubleValue();
			}
		}
		
		if (payrollBase.isJKKApplyed())
		{
			if(payConfig.getJKKBasicCalculation().equals(
					MUNSPayrollConfiguration.JKKBASICCALCULATION_BaseOnBasicSalary))
			{
				amtJKK = getGPokok().doubleValue();
			}
			else if (payConfig.getJKKBasicCalculation().equals(
					MUNSPayrollConfiguration.JKKBASICCALCULATION_BaseOnNettoSalary))
			{
				amtJKK = gajiBruto-potongan;
			}
			else if (payConfig.getJKKBasicCalculation().equals(
					MUNSPayrollConfiguration.JKKBASICCALCULATION_BaseOnBruttoSalary))
			{
				amtJKK = gajiBruto;
			}
			else if (MUNSPayrollConfiguration.JKKBASICCALCULATION_BaseOnUMK.equals(
					payConfig.getJKKBasicCalculation()))
			{
				amtJKK = level.getUMK().doubleValue();;
				if (amtJKK == 0)
					amtJKK = payConfig.getUMK().doubleValue();
			}
			else if (MUNSPayrollConfiguration.JKKBASICCALCULATION_BaseOnUMP.equals(
					payConfig.getJKKBasicCalculation()))
			{
				amtJKK = level.getUMP().doubleValue();;
				if (amtJKK == 0)
					amtJKK = payConfig.getUMP().doubleValue();
			}
		}
		
		if (payConfig.isJHTApplyed())
		{
			if(payConfig.getJHTBasicCalculation().equals(
					MUNSPayrollConfiguration.JHTBASICCALCULATION_BaseOnBasicSalary))
			{
				amtJHT = getGPokok().doubleValue();
			}
			else if (payConfig.getJHTBasicCalculation().equals(
					MUNSPayrollConfiguration.JHTBASICCALCULATION_BaseOnNettoSalary))
			{
				amtJHT = gajiBruto-potongan;
			}
			else if (payConfig.getJHTBasicCalculation().equals(
					MUNSPayrollConfiguration.JHTBASICCALCULATION_BaseOnBruttoSalary))
			{
				amtJHT = gajiBruto;
			}
			else if (MUNSPayrollConfiguration.JHTBASICCALCULATION_BaseOnUMK.equals(
					payConfig.getJHTBasicCalculation()))
			{
				amtJHT = level.getUMK().doubleValue();
				if (amtJHT == 0)
					amtJHT = payConfig.getUMK().doubleValue();
			}
			else if (MUNSPayrollConfiguration.JHTBASICCALCULATION_BaseOnUMP.equals(
					payConfig.getJHTBasicCalculation()))
			{
				amtJHT = level.getUMP().doubleValue();
				if (amtJHT == 0)
					amtJHT = payConfig.getUMP().doubleValue();
			}
		}
		
		if (payrollBase.isJPApplied())
		{
			if(payConfig.getJPBasicCalculation().equals(
					MUNSPayrollConfiguration.JHTBASICCALCULATION_BaseOnBasicSalary))
			{
				amtJP = getGPokok().doubleValue();
			}
			else if (payConfig.getJPBasicCalculation().equals(
					MUNSPayrollConfiguration.JHTBASICCALCULATION_BaseOnNettoSalary))
			{
				amtJP = gajiBruto-potongan;
			}
			else if (payConfig.getJPBasicCalculation().equals(
					MUNSPayrollConfiguration.JHTBASICCALCULATION_BaseOnBruttoSalary))
			{
				amtJP = gajiBruto;
			}
			else if (MUNSPayrollConfiguration.JHTBASICCALCULATION_BaseOnUMK.equals(
					payConfig.getJPBasicCalculation()))
			{
				amtJP = level.getUMK().doubleValue();
				if (amtJP == 0)
					amtJP = payConfig.getUMK().doubleValue();
			}
			else if (MUNSPayrollConfiguration.JHTBASICCALCULATION_BaseOnUMP.equals(
					payConfig.getJPBasicCalculation()))
			{
				amtJP = level.getUMP().doubleValue();
				if (amtJP == 0)
					amtJP = payConfig.getUMP().doubleValue();
			}
		}
		
		
		
		I_UNS_Employee employee = getUNS_Employee();
		
		if(employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_Kawin0Tanggungan)
				||employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_Kawin1Tanggungan)
				||employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_Kawin2Tanggungan)
				||employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_Kawin3Tanggungan))
		{
			amountJPKPaidByCom = amtJPK * jpkKawinPaidByComp;
			amountJPKPaidByEmp = amtJPK * jpkKawinPaidByEmp;
		}
		else if(employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_TidakKawin0Tanggungan)
				||employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_TidakKawin1Tanggungan)
				||employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_TidakKawin2Tanggungan))
		{
			amountJPKPaidByCom = amtJPK * jpkLajangPaidByComp;
			amountJPKPaidByEmp = amtJPK * jpkLajangPaidByEmp;
		}
		
		amountJKPaidByCom = amtJK * jkPaidByComp;
		amountJKPaidByEmp = amtJK * jkPaidByEmp;
		amountJHTPaidByCom = amtJHT * jhtPaidByComp;
		amountJHTPaidByEmp = amtJHT * jhtPaidByEmp;
		amountJKKPaidByCom = amtJKK * jkkPaidByComp;
		amountJKKPaidByEmp = amtJKK * jkkPaidByEmp;
		amountJPPaidByComp = amtJP * jpPaidByComp;
		amountJPPaidByEmp = amtJP * jpPaidByEmp;
		
		
		if(payConfig.isJHTApplyed())
		{
			setA_JHT(new BigDecimal(amountJHTPaidByCom).setScale(SCALE_PRECISION, RoundingMode.HALF_UP));
			setP_JHT(new BigDecimal(amountJHTPaidByEmp).setScale(SCALE_PRECISION, RoundingMode.HALF_UP));
		}
		if (payConfig.isJKApplyed())
		{
			setA_JK(new BigDecimal(amountJKPaidByCom).setScale(SCALE_PRECISION, RoundingMode.HALF_UP));
			setP_JK(new BigDecimal(amountJKPaidByEmp).setScale(SCALE_PRECISION, RoundingMode.HALF_UP));
		}
		if (payConfig.isJKKApplyed())
		{
			setA_JKK(new BigDecimal(amountJKKPaidByCom).setScale(SCALE_PRECISION, RoundingMode.HALF_UP));
			setP_JKK(new BigDecimal(amountJKKPaidByEmp).setScale(SCALE_PRECISION, RoundingMode.HALF_UP));
		}
		if (payConfig.isJPKApplyed())
		{
			setA_JPK(new BigDecimal(amountJPKPaidByCom).setScale(SCALE_PRECISION, RoundingMode.HALF_UP));
			setP_JPK(new BigDecimal(amountJPKPaidByEmp).setScale(SCALE_PRECISION, RoundingMode.HALF_UP));
		}
		
		if (payConfig.isJPApplied())
		{
			setA_JP(new BigDecimal(amountJPPaidByComp).setScale(SCALE_PRECISION, RoundingMode.HALF_UP));
			setP_JP(new BigDecimal(amountJPPaidByEmp).setScale(SCALE_PRECISION, RoundingMode.HALF_UP));
		}
	}
	
	/**
	 * 
	 * @return
	 */
//	public BigDecimal getPotongan()
//	{
//		return getP_Koperasi().add(getP_Label()).add(getP_ListrikAir()).add(getP_Mangkir())
//				.add(getP_Obat()).add(getP_Other()).add(getP_PinjamanKaryawan()).add(getPMakan());
//	}
	
	public BigDecimal getPayableBruto ()
	{
		BigDecimal bruto = getGPokok();
		BigDecimal addAmt = getA_LemburJamPertama().add(getA_LemburJamBerikutnya()).add(getA_L1()).
				add(getA_L2()).add(getA_L3()).add(getTotalOtherAllowances());
		
		bruto = bruto.add(addAmt);
		return bruto;
	}
	
	public BigDecimal getPayrollDeduction ()
	{
		BigDecimal deduction =  getP_JHT().add(getP_JK()).add(getP_JKK()).add(getP_JPK()).add(getP_JP()).add(getTotalOtherDeductions());
		if (!isPPH21PaidByCompany())
			deduction = deduction.add(getPPH21());
		return deduction;
	}
	
	public BigDecimal getPPHBrutoAmt ()
	{
		BigDecimal baseAmt = getGPokok();
		BigDecimal addAmt = getA_JP().add(getA_JK()).add(getA_JKK()).add(getA_JPK()).
				add(getA_LemburJamPertama()).add(getA_LemburJamBerikutnya()).add(getA_L1()).
				add(getA_L2()).add(getA_L3());
		
		MUNSPayrollCostBenefit[] pphComps = getCostBenefits(true);
		for (int i=0; i<pphComps.length; i++) 
		{
			if (!pphComps[i].isPPHComp() || !pphComps[i].isBenefit())
				continue;
			addAmt = addAmt.add(pphComps[i].getAmount());
		}
		
		baseAmt = baseAmt.add(addAmt);
		return baseAmt;
	}
	
	public BigDecimal getPPHDeducAmt ()
	{
		BigDecimal pphDeducAmt = getP_JP().add(getP_JHT()).add(getP_JK()).add(getP_JKK()).
				add(getP_Mangkir());
		
		MUNSPayrollCostBenefit[] pphComps = getCostBenefits(true);
		for (int i=0; i<pphComps.length; i++) 
		{
			if (!pphComps[i].isPPHComp() || pphComps[i].isBenefit())
				continue;
			pphDeducAmt = pphDeducAmt.add(pphComps[i].getAmount());
		}
		
		return pphDeducAmt;
	}

	/**
	 * LD1 + LD2 + LD3 + Lembur (Jam 1 + Jam Berikutnya) + A_Other + A_Premi + A_Rapel + GP + T.Jabatan +
	 * T.Kesejahteraan + T.Khusus + T.Lembur.
	 * @return
	 */
//	public BigDecimal getGajiBruto()
//	{
//		return getA_L1().add(getA_L2()).add(getA_L3())
//				.add(getA_LemburJamPertama()).add(getA_LemburJamBerikutnya())
//				.add(getA_Other()).add(getA_Premi()).add(getA_Rapel()).add(getGPokok())
//				.add(getG_T_Jabatan()).add(getG_T_Kesejahteraan()).add(getG_T_Khusus())
//				.add(getG_T_Lembur());
//	}
	
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		// 
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
	private boolean m_justPrepared = false;
	@Override
	public boolean processIt(String action) throws Exception {
		// 
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		// 
		log.info(toString());
		setProcessed(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		// 
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		// 
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if(!isGenerate())
		{
			m_processMsg = "This document has not generated. Please generate payroll first.";
			return DocAction.STATUS_Invalid;
		}
		
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
		// 
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		// 
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		// 
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
		getCostBenefits(false);
		for (int i=0; i<m_CostBenefits.length; i++)
		{
			if (MUNSPayrollComponentConf.COSTBENEFITTYPE_Canteen.equals(m_CostBenefits[i].getUNS_Payroll_Component_Conf().getCostBenefitType())) {
				MUNSPeriodicCostBenefitLine.pay(getUNS_Employee_ID(), m_CostBenefits[i].getAmount(), 
						MUNSPeriodicCostBenefit.COSTBENEFITTYPE_Canteen, get_TrxName());
			} else if (MUNSPayrollComponentConf.COSTBENEFITTYPE_Cooperative.equals(m_CostBenefits[i].getUNS_Payroll_Component_Conf().getCostBenefitType())) {
				MUNSPeriodicCostBenefitLine.pay(getUNS_Employee_ID(), m_CostBenefits[i].getAmount(), 
						MUNSPeriodicCostBenefit.COSTBENEFITTYPE_Cooperative, get_TrxName());
			}
		}
		
		createInstallment();
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
				
		setProcessed(true);	
		
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		// 
		return false;
	}

	@Override
	public boolean closeIt() {
		// 
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
		// 
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// 
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// 
		return false;
	}

	@Override
	public String getSummary() {
		// 
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// 
		return null;
	}

	@Override
	public File createPDF() {
		// 
		return null;
	}

	@Override
	public String getProcessMsg() {
		// 
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// 
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// 
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// 
		return null;
	}
	
	public boolean hasGeneratePay()
	{
		return getGeneratePay().equals("Y");
	}
	
	/**
	 * 
	 * @param monthlyPresence
	 */
	public void createFrom(PO po)
	{
		I_UNS_Employee employee = null;
		
		if(po instanceof MUNSContractRecommendation)
		{
			X_UNS_Contract_Recommendation contract = (X_UNS_Contract_Recommendation) po;
			employee = contract.getUNS_Employee();
		}
		else if(po instanceof MUNSMonthlyPresenceSummary)
		{
			X_UNS_MonthlyPresenceSummary presence = (X_UNS_MonthlyPresenceSummary) po;
			employee = presence.getUNS_Employee();
		}
		
		else if(po instanceof MUNSHalfPeriodPresence)
		{
			X_UNS_HalfPeriod_Presence presence = (X_UNS_HalfPeriod_Presence) po;
			employee = presence.getUNS_Employee();
		}
		else
		{
			log.log(Level.SEVERE, "Unhandled instance");
			return;
		}
		
		String payrollTerm = MUNSPayrollTermConfig.getPayrollTermOf(
				getAD_Org_ID()
				, employee.getC_BPartner_ID()
				,employee.getUNS_Contract_Recommendation().getNextContractType()
				, Env.getContextAsDate(getCtx(), "Date")
				, get_TrxName());
		if(null == payrollTerm)
			payrollTerm = employee.getPayrollTerm();
		
		setC_Job_ID(employee.getC_Job_ID());
		setPayrollTerm(payrollTerm);
		setUNS_Employee_ID(employee.getUNS_Employee_ID());
		setShift(employee.getShift());
		
		MUNSPayrollBaseEmployee pb = MUNSPayrollBaseEmployee.get(
				getCtx(), employee.getUNS_Contract_Recommendation_ID(), get_TrxName());
		
		setUNS_Contract_Recommendation_ID(employee.getUNS_Contract_Recommendation_ID());
		setUNS_PayrollBase_Employee_ID(pb.get_ID());
	}
	
	/**
	 * 
	 */
	public String calculatePayroll()
	{
		PayrollEmployeeGenerator payGenerator = new PayrollEmployeeGenerator();
		payGenerator.setPayrollEmploye(this);
		return payGenerator.calculatePay();
	}
	
	public boolean afterSave(boolean newRecord, boolean success)
	{
		if(success)
			updateHeader();
		
		return super.afterSave(newRecord, success);
	}
	
	private void updateHeader()
	{
		getParent().calculatePaySummary();
	}
	
	@Override
	public MUNSMonthlyPayrollEmployee getParent()
	{
		if (null == m_parent)
		{
			m_parent = new MUNSMonthlyPayrollEmployee(getCtx(), 
					getUNS_MonthlyPayroll_Employee_ID(), get_TrxName());
		}
		
		return m_parent;
	}
	
	private void createInstallment ()
	{
		double leftPaidToCompany = 0.0;
		double leftPaidToKoperasi = 0.0;
		getCostBenefits(false);
		for (int i=0; i<m_CostBenefits.length; i++)
		{
			if (!"PKR".equals(m_CostBenefits[i].getUNS_Payroll_Component_Conf().getCostBenefitType()))
				continue;
			leftPaidToCompany = m_CostBenefits[i].getAmount().doubleValue();
		}
		
		if (leftPaidToCompany == 0 && leftPaidToKoperasi == 0)
		{
			return;
		}
		
		List<MUNSEmployeeLoan> listOfEmployeeLoan = MUNSEmployeeLoan.gets(
				getCtx(), getUNS_Employee_ID(), get_TrxName());
		
		for (MUNSEmployeeLoan employeeLoan : listOfEmployeeLoan)
		{
			double loanAmtLeft = employeeLoan.getLoanAmtLeft().doubleValue();
			double paidAmt = 0;
			if(MUNSEmployeeLoan.LOANTYPE_Company.equals(employeeLoan.getLoanType()))
			{
				paidAmt = leftPaidToCompany;
				if(leftPaidToCompany > loanAmtLeft) {
					leftPaidToCompany = leftPaidToCompany - loanAmtLeft;
					paidAmt = loanAmtLeft;
				} else 
					leftPaidToCompany = 0;
			}
			else if(MUNSEmployeeLoan.LOANTYPE_Koperasi.equals(employeeLoan.getLoanType()))
			{
				paidAmt = leftPaidToKoperasi;
				if(leftPaidToKoperasi > loanAmtLeft) {
					leftPaidToKoperasi = leftPaidToKoperasi - loanAmtLeft;
					paidAmt = loanAmtLeft;
				} else 
					leftPaidToKoperasi = 0;
			}
			else
				throw new AdempiereUserError("UNKNOWN LOAN TYPE");
			
			if(paidAmt <= 0)
				continue;

			MUNSMonthlyPresenceSummary Monthly = MUNSMonthlyPresenceSummary.get(getCtx(), getUNS_Employee_ID(), 
					getC_Period_ID(), getAD_Org_ID(), get_TrxName());
			
			MUNSLoanInstallment loanInstallment = new MUNSLoanInstallment(getCtx(), 0, get_TrxName());
			
			loanInstallment.setAD_Org_ID(employeeLoan.getAD_Org_ID());
			loanInstallment.setUNS_Employee_Loan_ID(employeeLoan.get_ID());
			loanInstallment.setPaidAmt(new BigDecimal(paidAmt));
			loanInstallment.setPaidDate(Monthly.getLastDate());
			loanInstallment.setC_DocType_ID(MDocType.getDocType(I_C_DocType.DOCBASETYPE_LoanInstallment));
			loanInstallment.setIsCashPayment(false);
			loanInstallment.saveEx();
			
			try {
				loanInstallment.processIt(MUNSLoanInstallment.DOCACTION_Complete);
			} 
			catch(Exception ex) {
				throw new AdempiereException(ex.getMessage());
			}
		}

	}
}
