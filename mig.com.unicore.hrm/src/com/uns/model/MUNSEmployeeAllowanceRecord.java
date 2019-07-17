/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Year;
import org.compiere.model.MPeriod;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author Haryadi
 *
 */
public class MUNSEmployeeAllowanceRecord extends X_UNS_EmployeeAllowanceRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3641561496083493614L;

	/**
	 * @param ctx
	 * @param UNS_MedicalAllowanceRecord_ID
	 * @param trxName
	 */
	public MUNSEmployeeAllowanceRecord(Properties ctx,
			int UNS_MedicalAllowanceRecord_ID, String trxName) {
		super(ctx, UNS_MedicalAllowanceRecord_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSEmployeeAllowanceRecord(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * Get medical allowance record of employee which is valid for date.
	 * 
	 * @param ctx
	 * @param Employee_ID
	 * @param date
	 * @param trxName
	 * @return
	 */
	public static MUNSEmployeeAllowanceRecord get(Properties ctx, int Employee_ID, Timestamp date, String trxName)
	{
		MUNSEmployeeAllowanceRecord record = null;
		
		String whereClause = "UNS_Employee_ID=? AND ? BETWEEN PeriodDateStart AND PeriodDateEnd";
		
		record = Query.get(ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, whereClause, trxName)
				.setParameters(Employee_ID, date)
				.first();
		
		return record;
	}
	
	/**
	 * Get medical allowance record of employee which is valid for date. If not exist, then create it based on
	 * employee's contract.
	 * 
	 * @param ctx
	 * @param employee
	 * @param date
	 * @param trxName
	 * @return
	 */
	public static MUNSEmployeeAllowanceRecord getCreateMedical(
			Properties ctx, MUNSEmployee employee, Timestamp date, String trxName)
	{
		BigDecimal employeeAllowance = MUNSPayrollLevelConfig.getMedicalAllowanceOf(ctx, employee, trxName);
		
		if (employeeAllowance == null)
			return null;
		
		MUNSEmployeeAllowanceRecord medAllowanceRec = getCreate(ctx, employee, date, employeeAllowance, -1, trxName);
		
		return medAllowanceRec;
	}
	
	/**
	 * Get Leave Reserved allowance record of employee which is valid for date. If not exist, then create it based on
	 * employee's contract.
	 * 
	 * @param ctx
	 * @param employee
	 * @param date
	 * @param trxName
	 * @return
	 */
	public static MUNSEmployeeAllowanceRecord getCreateLeaveReserved(
			Properties ctx, MUNSEmployee employee, Timestamp date, String trxName)
	{
		MUNSLeaveReservedConfig lrc = 
				MUNSLeaveReservedConfig.get(ctx,
											employee.getC_Job().getC_JobCategory_ID(), 
											employee.getNationality(), 
											trxName);
		if (lrc == null)
			return null;
		int leaveAllowance = lrc.getLeaveClaimReserved();
		
		MUNSEmployeeAllowanceRecord medAllowanceRec = 
				getCreate(ctx, employee, date, new BigDecimal(-1), leaveAllowance, trxName);
		
		return medAllowanceRec;
	}
	
	public static MUNSEmployeeAllowanceRecord getCreate(
			Properties ctx, MUNSEmployee employee, Timestamp date, String trxName)
	{
		MUNSLeaveReservedConfig lrc = 
				MUNSLeaveReservedConfig.get(ctx,
											employee.getC_Job().getC_JobCategory_ID(), 
											employee.getNationality(), 
											trxName);
		if (lrc == null)
			return null;
		
		BigDecimal employeeAllowance = MUNSPayrollLevelConfig.getMedicalAllowanceOf(ctx, employee, trxName);
		
		if (employeeAllowance == null)
			return null;
		
		int leaveAllowance = lrc.getLeaveClaimReserved();
		
		MUNSEmployeeAllowanceRecord medAllowanceRec = 
				getCreate(ctx, employee, date, employeeAllowance, leaveAllowance, trxName);
		
		return medAllowanceRec;
	}
	
	/**
	 * Get medical allowance record of employee which is valid for date. If not exist, then create it based on
	 * employee's contract.
	 * 
	 * @param ctx
	 * @param employee
	 * @param date
	 * @param yearlyAllowanceAmt
	 * @param trxName
	 * @return
	 */
	public static MUNSEmployeeAllowanceRecord getCreate(
			Properties ctx, MUNSEmployee employee, Timestamp date, 
			BigDecimal yearlyAllowanceAmt, int yearlyLeaveReserved, String trxName)
	{
		MUNSEmployeeAllowanceRecord record = get(ctx, employee.get_ID(), date, trxName);

		MUNSContractRecommendation contract = null;
		
		if (record == null)
		{
			// try to find the contract based on the date.
			contract = MUNSContractRecommendation.getOf(ctx, employee.get_ID(), date, trxName);
			
			if (contract == null)
				return null;
		}
		else
			return record;
		
		if (yearlyAllowanceAmt.signum() < 0)
			yearlyAllowanceAmt = MUNSPayrollLevelConfig.getMedicalAllowanceOf(ctx, employee, trxName);
		
		if (yearlyLeaveReserved == -1)
		{
			MUNSLeaveReservedConfig lrc = 
					MUNSLeaveReservedConfig.get(ctx,
												employee.getC_Job().getC_JobCategory_ID(), 
												employee.getNationality(), 
												trxName);
			if (lrc != null)
				yearlyLeaveReserved = lrc.getLeaveClaimReserved();
		}
		
		if (yearlyAllowanceAmt.signum() <=0  && yearlyLeaveReserved <= 0)
			return null;
		
		Timestamp tglStart = contract.getDateContractStart();
		
		String payrollTerm = MUNSPayrollTermConfig.getPayrollTermOf(
				employee.getAD_Org_ID()
				, contract.getNewSectionOfDept_ID()
				, contract.getNextContractType()
				, Env.getContextAsDate(ctx, "Date")
				, trxName);
		if(null == payrollTerm)
			payrollTerm = employee.getPayrollTerm();
		
		if (MUNSEmployee.PAYROLLTERM_Monthly.equals(payrollTerm))
		{
			Calendar cal = Calendar.getInstance();
			cal.set(2014, 0, 1);
			
			if (tglStart.before(cal.getTime())) 
			{ // ini untuk yang sudah karyawan tetap sebelum tanggal 1/1/2014.
				cal.setTime(date);
				cal.set(Calendar.DATE, 1);
				cal.set(Calendar.MONTH, 0);
				tglStart = new Timestamp(cal.getTimeInMillis());
			}
			else { // ini untuk yang sudah karyawan tetap setelah tanggal 1/1/2014.
				cal.setTime(contract.getDateContractStart());
				
				int contractMonth = cal.get(Calendar.MONTH);
				int contractDate = cal.get(Calendar.DATE);
				
				Calendar theDateCal = Calendar.getInstance();
				theDateCal.setTime(date);
				int theDateMonth = theDateCal.get(Calendar.MONTH);
				int theDateDate = theDateCal.get(Calendar.DATE);
				
				cal.set(Calendar.YEAR, theDateCal.get(Calendar.YEAR));
				
				if (theDateMonth <= contractMonth && theDateDate < contractDate)
				{
					cal.add(Calendar.YEAR, -1);
				}
				tglStart = new Timestamp(cal.getTimeInMillis());
			}
		}
		else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(contract.getDateContractStart());
			
			Calendar theDateCal = Calendar.getInstance();
			theDateCal.setTime(date);
			int theDateYear = theDateCal.get(Calendar.YEAR);
			
			cal.set(Calendar.YEAR, theDateYear);
			
			if (cal.before(theDateCal))
				/*
				 * Cth: 1. tgl contract start = 1 Feb 2014, dan date = 1 Jun 2014, 
				 *         mk stlh 'cal' di pastikan mjd th 2014 set tglStart = 1 Feb 2014.
				 */
				tglStart = new Timestamp (cal.getTimeInMillis());
			else {
				/*
				 * Contoh: 1. tgl contract start = 1 Jun 2013, dan date = 1 mei 2014, 
				 *            maka kurangi tahun 'cal' mundur 1 th.
				 * 2. tgl contract start = 1 Des 2013, dan date = 1 Feb 2014, 
				 *    mk stlh 'cal' di jadikan tahun 1 Des 2014, kurangi lagi 1 th mjd 1 Des 2013.
				 */
				cal.add(Calendar.YEAR, -1);
				tglStart = new Timestamp (cal.getTimeInMillis());
			}
		}
		
		// Pertama test dg 1 hari sebelum tglStart di tahun berikutnya.
		// misalkan tglStart 1 Feb 2014, maka test tglEnd dg 31 Jan 2015. Jika ternyata DateContractEnd sebelum
		// tglEnd, maka gunakan DateContractEnd sebagai periode akhir allowance.
		Calendar calEndAllowancePeriod = Calendar.getInstance();
		calEndAllowancePeriod.setTime(tglStart);
		calEndAllowancePeriod.add(Calendar.DATE, -1);
		calEndAllowancePeriod.add(Calendar.YEAR, 1);
		
		Timestamp tglEnd = new Timestamp(calEndAllowancePeriod.getTimeInMillis());
		
		float totalDaysInPeriod = TimeUtil.getDaysBetween(tglStart, tglEnd) + 1;
		
		if (tglEnd.after(contract.getDateContractEnd()))
			tglEnd = contract.getDateContractEnd();
		
		float periodDurationInDays = TimeUtil.getDaysBetween(tglStart, tglEnd) + 1;
		
		float currentContractProportion = periodDurationInDays/totalDaysInPeriod;
		
		BigDecimal currentAllowanceAmt = Env.ZERO;
		if (yearlyAllowanceAmt != null && yearlyAllowanceAmt.signum() > 0)
		{
			currentAllowanceAmt = 
					yearlyAllowanceAmt.multiply(BigDecimal.valueOf(currentContractProportion))
					.setScale(0, BigDecimal.ROUND_HALF_DOWN);
		
			//if (currentAllowanceAmt.signum() == 0)
			//	return record;
		}
		
		float currentLeaveReserved = 0;
		if (yearlyLeaveReserved > 0) 
		{
			currentLeaveReserved = yearlyLeaveReserved * currentContractProportion;
			/*
			MUNSEmployeeAllowanceRecord prevRecord = 
					Query.get(ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, "UNS_Employee_ID=?", trxName)
					.setParameters(employee.get_ID())
					.setOrderBy(COLUMNNAME_PeriodDateEnd + " DESC")
					.first();
			if (prevRecord != null)
			{
				BigDecimal lastLeaveRemaining = 
						prevRecord.getLeaveClaimReserved().subtract(prevRecord.getLeaveReservedUsed());
				
				currentLeaveReserved = currentLeaveReserved + lastLeaveRemaining.floatValue();
			}
			*/
		}
			
		record = new MUNSEmployeeAllowanceRecord(ctx, 0, trxName);
		
		//TODO handle period closed (not found period)
       /*
		I_C_Year year = MPeriod.get(ctx, tglStart, employee.getAD_Org_ID()).getC_Year();
        */
		MPeriod period = MPeriod.get(
				ctx, tglStart, employee.getAD_Org_ID(), trxName);
		if (period == null)
		{
			return null;
		}
		
        I_C_Year year = period.getC_Year();
		record.setC_Year_ID(year.getC_Year_ID());
		record.setUNS_Employee_ID(employee.get_ID());
		record.setUNS_Contract_Recommendation_ID(contract.getUNS_Contract_Recommendation_ID());
		record.setAD_Org_ID(employee.getAD_Org_ID());
		record.setName("Medical allowance of year " + year.getFiscalYear());
		record.setRemarks("Allowance initialized based on contract " + contract.getDocumentNo() + " at " + 
				 		  TimeUtil.getDay(Calendar.getInstance().getTimeInMillis()));
		
		record.setPeriodDateStart(tglStart);
		record.setPeriodDateEnd(tglEnd);
		
		record.setMedicalAllowance(currentAllowanceAmt);
		record.setMedicalAllowanceUsed(Env.ZERO);
		record.setRemainingAmt(currentAllowanceAmt);
		record.setLeaveClaimReserved(BigDecimal.valueOf(currentLeaveReserved).setScale(1, RoundingMode.HALF_UP));
		record.setLeaveReservedUsed(Env.ZERO);

		record.saveEx();
		
		return record;
	}
	
	public static MUNSEmployeeAllowanceRecord getCreate_old(
			Properties ctx, MUNSEmployee employee, Timestamp date, 
			BigDecimal yearlyAllowanceAmt, String trxName)
	{
		MUNSEmployeeAllowanceRecord record = get(ctx, employee.get_ID(), date, trxName);

		MUNSContractRecommendation contract = null;
		
		if (record == null)
		{
			// try to find the contract based on the date.
			contract = MUNSContractRecommendation.getOf(ctx, employee.get_ID(), date, trxName);
			
			if (contract == null)
				return null;
		}
		else if (record != null &&
			record.getUNS_Contract_Recommendation_ID() == employee.getUNS_Contract_Recommendation_ID()) {
			return record;
		}
		
		if (employee.getUNS_Contract_Recommendation_ID() == 0)
			throw new AdempiereException("Cannot find contract of employee " + employee.getName());
		
		@SuppressWarnings("deprecation")
	    MPeriod period = MPeriod.get(ctx, date, employee.getAD_Org_ID());
		
		int fiscalYear = Integer.valueOf(period.getC_Year().getFiscalYear());
		
		if (null == contract)
			contract = (MUNSContractRecommendation) employee.getUNS_Contract_Recommendation();
		
		Calendar periodCal = Calendar.getInstance();
		periodCal.set(fiscalYear, 0, 1);
		
		Timestamp tglStart = new Timestamp(periodCal.getTimeInMillis());
		
		if (tglStart.before(contract.getDateContractStart())) 
			tglStart = contract.getDateContractStart();
		
		periodCal.set(fiscalYear, 11, 31);
		
		Timestamp tglEnd = new Timestamp(periodCal.getTimeInMillis());
		
		if (tglEnd.after(contract.getDateContractEnd()))
			tglEnd = contract.getDateContractEnd();
		
		float periodDurationInDays = TimeUtil.getDaysBetween(tglStart, tglEnd) + 1;
		float totalFiscalDays = periodCal.getActualMaximum(Calendar.DAY_OF_YEAR);
		
		float currentContractProportion = periodDurationInDays/totalFiscalDays;
		
		BigDecimal currentAllowanceAmt = 
				yearlyAllowanceAmt.multiply(BigDecimal.valueOf(currentContractProportion))
				.setScale(0, BigDecimal.ROUND_HALF_DOWN);
		
		if (currentAllowanceAmt.signum() == 0)
			return record;
		
		if (record == null) 
		{
			record = new MUNSEmployeeAllowanceRecord(ctx, 0, trxName);
			
			record.setC_Year_ID(period.getC_Year_ID());
			record.setUNS_Employee_ID(employee.get_ID());
			record.setUNS_Contract_Recommendation_ID(contract.getUNS_Contract_Recommendation_ID());
			record.setAD_Org_ID(employee.getAD_Org_ID());
			record.setName("Medical allowance of year " + fiscalYear);
			record.setRemarks("- Init allowance based on contract " + contract.getDocumentNo() + " at " + 
					 		  TimeUtil.getDay(Calendar.getInstance().getTimeInMillis()));
			
			record.setPeriodDateStart(tglStart);
			record.setPeriodDateEnd(tglEnd);
			
			record.setMedicalAllowance(currentAllowanceAmt);
			record.setMedicalAllowanceUsed(Env.ZERO);
			record.setRemainingAmt(currentAllowanceAmt);
		}		
		else {
			record.setPeriodDateEnd(tglEnd);
			record.setUNS_Contract_Recommendation_ID(contract.getUNS_Contract_Recommendation_ID());
			record.setRemarks(record.getRemarks() + " \n" + 
							 "- Update allowance based on contract " + contract.getDocumentNo() + " at " + 
							 TimeUtil.getDay(Calendar.getInstance().getTimeInMillis()));
			record.setMedicalAllowance(record.getMedicalAllowance().add(currentAllowanceAmt));
		}
		record.saveEx();
		
		return record;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		setRemainingAmt(getMedicalAllowance().subtract(getMedicalAllowanceUsed()));
		
		if (getRemainingAmt().signum() < 0)
			throw new AdempiereException("Cannot set remaining amount to negative value.");
		
		return true;
	}
	
}
