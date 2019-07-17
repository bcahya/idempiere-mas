/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author eko
 *
 */
public class MUNSPayrollLevelConfig extends X_UNS_PayrollLevel_Config {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_PayrollLevel_Config_ID
	 * @param trxName
	 */
	public MUNSPayrollLevelConfig(Properties ctx,
			int UNS_PayrollLevel_Config_ID, String trxName) {
		super(ctx, UNS_PayrollLevel_Config_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPayrollLevelConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param employee
	 * @param trxName
	 * @return
	 */
	public static MUNSPayrollLevelConfig get(Properties ctx, I_UNS_Employee employee, String payrollTerm, String trxName)
	{
		String whereClause =   "PayrollLevel =? AND PayrollTerm = ? AND UNS_PayrollConfiguration_ID = " +
				"	(SELECT pc.UNS_PayrollConfiguration_ID FROM UNS_PayrollConfiguration pc " +
				"	 WHERE Now() BETWEEN pc.ValidFrom AND pc.ValidTo AND pc.IsActive='Y')";

		return Query.get(ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, whereClause, trxName)
				.setParameters(employee.getPayrollLevel(), payrollTerm)
				.first();
	}
	
	/**
	 * 
	 * @param ctx
	 * @param employee
	 * @param trxName
	 * @return
	 *
	public static BigDecimal getMedicalAllowanceOf (Properties ctx, I_UNS_Employee employee, String trxName)
	{
		String sql =   "SELECT COALESCE (MedicalAllowance, 0) FROM UNS_AllowanceConfig " +
				" WHERE C_JobCategory_ID=? AND UNS_PayrollConfiguration_ID = " +
				"	(SELECT pc.UNS_PayrollConfiguration_ID FROM UNS_PayrollConfiguration pc " +
				"	 WHERE Now() BETWEEN pc.ValidFrom AND pc.ValidTo AND pc.IsActive='Y')";
		BigDecimal medicalAllowance = 
				DB.getSQLValueBD(trxName, sql, employee.getC_Job().getC_JobCategory_ID());
		if (medicalAllowance == null)
			medicalAllowance = Env.ZERO;
		return medicalAllowance;
	}
	*/
	
	/**
	 * 
	 * @param ctx
	 * @param employee
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getMedicalAllowanceOf (Properties ctx, I_UNS_Employee employee, String trxName)
	{
		BigDecimal medicalAllowance = BigDecimal.ZERO;
		
		String sql =   "SELECT UNS_AllowanceConfig_ID FROM UNS_AllowanceConfig " +
				" WHERE C_JobCategory_ID=? AND ContractType=? AND UNS_PayrollConfiguration_ID = " +
				"	(SELECT pc.UNS_PayrollConfiguration_ID FROM UNS_PayrollConfiguration pc " +
				"	 WHERE Now() BETWEEN pc.ValidFrom AND pc.ValidTo AND pc.IsActive='Y')";
		
		int record_ID = DB.getSQLValue(trxName, sql, employee.getC_Job().getC_JobCategory_ID()
				, employee.getUNS_Contract_Recommendation().getNextContractType());
		if(record_ID <= 0)
			return medicalAllowance;
		
		X_UNS_AllowanceConfig allowanceConfig = new X_UNS_AllowanceConfig(ctx, record_ID, trxName);
		if(allowanceConfig.isAllowanceBaseOnSallary())
		{
			MUNSPayrollBaseEmployee payrollBase = MUNSPayrollBaseEmployee.get(
					ctx, employee.getUNS_Contract_Recommendation_ID(), trxName);
			medicalAllowance = payrollBase.getGPokok();
			if(allowanceConfig.getAllowanceMultiplier().signum() > 1)
				medicalAllowance = medicalAllowance.multiply(
						allowanceConfig.getAllowanceMultiplier());
		}
		else
		{
			medicalAllowance = allowanceConfig.getMedicalAllowance();
		}
		
		if (medicalAllowance == null)
			medicalAllowance = Env.ZERO;
		return medicalAllowance;
	}

}
