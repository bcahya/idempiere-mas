package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
public class MUNSPayrollCostBenefit extends X_UNS_Payroll_CostBenefit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1815639362495765898L;

	public MUNSPayrollCostBenefit(Properties ctx,
			int UNS_Payroll_CostBenefit_ID, String trxName) {
		super(ctx, UNS_Payroll_CostBenefit_ID, trxName);
	}

	public MUNSPayrollCostBenefit(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public BigDecimal getTrxAmount () {
		BigDecimal amt = getAmount();
		if (!isBenefit())
			amt = amt.negate();
		return amt;
	}
	
//	public static MUNSPayrollCostBenefit get (int pEmployee_ID, int Component_ID, String trxName) {
//		MUNSPayrollCostBenefit costBenefit = Query.get(
//				Env.getCtx(), UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
//				"UNS_Payroll_Employee_ID = ? AND UNS_Payroll_Component_Conf_ID = ?", 
//				trxName).setParameters(pEmployee_ID, Component_ID).firstOnly();
//		return costBenefit;
//	}
	
	public MUNSPayrollCostBenefit (MUNSPayrollEmployee payroll, MUNSPayrollComponentConf conf) {
		this (payroll.getCtx(), 0, payroll.get_TrxName());
		setClientOrg(payroll);
		if (conf != null) {
			setIsBenefit(conf.isBenefit());
			setName(conf.getName());
			setUNS_Payroll_Component_Conf_ID(conf.get_ID());
			setCostBenefit_Acct(conf.getCostBenefit_Acct());
			setAmount(conf.getAmount());
			setSeqNo(conf.getSeqNo());
			setIsPPHComp(conf.isPPHComp());
		}
		setUNS_Payroll_Employee_ID(payroll.get_ID());
	}
	
	@Override
	protected boolean beforeSave (boolean newRecord) 
	{
		return super.beforeSave(newRecord);
	}
	
	protected boolean afterSave (boolean newRecord, boolean success)
	{		
		return super.afterSave(newRecord, success);
	}
	
	private MUNSPayrollEmployee m_payrollEmployee = null;
	
	public MUNSPayrollEmployee getPayrollEmployee ()
	{
		if (m_payrollEmployee == null)
			m_payrollEmployee = new MUNSPayrollEmployee(getCtx(), getUNS_Payroll_Employee_ID(), get_TrxName());
		return m_payrollEmployee;
	}
}
