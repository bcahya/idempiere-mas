/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import org.compiere.model.MNonBusinessDay;
import org.compiere.model.MPeriod;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;

import com.uns.model.I_UNS_Contract_Recommendation;
import com.uns.model.MUNSContractRecommendation;
import com.uns.model.MUNSDailyPresence;
import com.uns.model.MUNSMonthlyPresenceSummary;
import com.uns.model.MUNSPayrollConfiguration;

public class GenerateDailyRecord extends SvrProcess 
{
	private int m_Record_ID = 0;
	private Properties m_Ctx = null;
	private String m_trxName = null;
	private MUNSMonthlyPresenceSummary m_MonthlySummary = null;
	
	public GenerateDailyRecord()
	{
		super();
	}	

	public GenerateDailyRecord(Properties ctx, int UNS_MonthlyPresence_ID, String trxName)
	{
		this.m_Ctx = ctx;
		this.m_Record_ID = UNS_MonthlyPresence_ID;
		this.m_MonthlySummary = new MUNSMonthlyPresenceSummary(ctx, UNS_MonthlyPresence_ID, trxName);
		this.m_trxName = trxName;
	}
	
	public GenerateDailyRecord(Properties ctx, MUNSMonthlyPresenceSummary monthlySummary, String trxName)
	{
		this.m_Ctx = ctx;
		this.m_Record_ID = monthlySummary.get_ID();
		this.m_MonthlySummary = monthlySummary;
		this.m_trxName = trxName;
	}
	
	@Override
	protected void prepare() {	
		m_Record_ID = getRecord_ID();
		m_Ctx = getCtx();
		m_trxName = get_TrxName();
	}
	
	@Override
	protected String doIt() throws Exception 
	{	
		String msg = generateDailyPresence();
		if(null != msg)
			throw new AdempiereUserError(msg);
		 return msg;
	}

	public String generateDailyPresence()
	{
		String msg = null;
		if (m_Record_ID <= 0)
			return "No monthly presence ID";
		
		if (m_MonthlySummary == null)
			m_MonthlySummary = new MUNSMonthlyPresenceSummary(m_Ctx, m_Record_ID, m_trxName);
		
		MPeriod period = MPeriod.get(m_Ctx, m_MonthlySummary.getC_Period_ID());
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(period.getStartDate().getTime());
		int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		double medianDayOfMonth = (double) maxDayOfMonth / 2.0;
		MUNSPayrollConfiguration pc = MUNSPayrollConfiguration.get(m_Ctx, period.getStartDate(), m_trxName);
		
		int startDate = pc.getPayrollDateStart();
		int endDate = pc.getPayrollDateEnd();
		Calendar calendarStartOfPeriod = (Calendar) calendar.clone();
		calendarStartOfPeriod.set(Calendar.DATE, startDate);
		if (startDate > medianDayOfMonth)
		{
			calendarStartOfPeriod.add(Calendar.MONTH, -1);
			maxDayOfMonth = calendarStartOfPeriod.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		
		Calendar calendarEndOfPeriod = (Calendar) calendarStartOfPeriod.clone();
		calendarEndOfPeriod.add(Calendar.MONTH, 1);
		calendarEndOfPeriod.set(Calendar.DATE, endDate);
	
		m_MonthlySummary.setIsGenerate(true);
		int days = 0;
		
		I_UNS_Contract_Recommendation contract = m_MonthlySummary.getUNS_Employee()
				.getUNS_Contract_Recommendation();
		
		Timestamp dateContractStart = contract.getDateContractStart();
		Timestamp dateContarctEnd = contract.getDateContractEnd();
		
//		m_MonthlySummary.setTotalFullDayPresence(0);
//		m_MonthlySummary.setTotalHalfDayPresence(0);
//		m_MonthlySummary.setTotalSK(Env.ZERO);
//		m_MonthlySummary.setTotalSKK(Env.ZERO);
//		m_MonthlySummary.setTotalOvertime(Env.ZERO);
//		m_MonthlySummary.setTotalWorkDay(0);
//		m_MonthlySummary.setTotalAbsence(0);
//		m_MonthlySummary.setTotalPayableAbsence(0);
//		m_MonthlySummary.setTotalLD1(Env.ZERO);
//		m_MonthlySummary.setTotalLD2(Env.ZERO);
//		m_MonthlySummary.setTotalLD3(Env.ZERO);
//		m_MonthlySummary.saveEx();
		int i=1;
		while (i<= maxDayOfMonth)
		{
			Timestamp current = new Timestamp(calendarStartOfPeriod.getTimeInMillis());
			if(current.before(dateContractStart))
			{
				calendarStartOfPeriod.add(Calendar.DATE, 1);
				MUNSContractRecommendation prevContract = ((MUNSContractRecommendation)contract).getPrev ();
				
				if(null == prevContract 
						||prevContract.get_ID() == contract.getUNS_Contract_Recommendation_ID()
						|| !current.before(prevContract.getDateContractEnd())
						|| !current.equals(prevContract.getDateContractEnd()))
				{
					i++;
					calendarStartOfPeriod.add(Calendar.DATE, 1);
					continue;
				}
			}
			
			if(current.after(dateContarctEnd))
				break;
				
			MUNSDailyPresence newDaily = m_MonthlySummary.getDaily(current);
			if (null == newDaily)
			{
				newDaily = new MUNSDailyPresence(m_MonthlySummary);
			}
			else
			{
				i++;
				calendarStartOfPeriod.add(Calendar.DATE, 1);
				continue;
			}
			
			newDaily.setAD_Org_ID(m_MonthlySummary.getAD_Org_ID());
			newDaily.setUNS_MonthlyPresenceSummary_ID(m_MonthlySummary.getUNS_MonthlyPresenceSummary_ID());
			newDaily.setPresenceDate(new Timestamp(calendarStartOfPeriod.getTimeInMillis()));
			newDaily.setDay(newDaily.getDay(calendarStartOfPeriod.get(Calendar.DAY_OF_WEEK)));
			
			if(isNonBusinessDay(new Timestamp(calendarStartOfPeriod.getTimeInMillis()), m_MonthlySummary.getAD_Org_ID()))
			{
				newDaily.setDayType(MUNSDailyPresence.DAYTYPE_HariLiburNasional);	
				newDaily.setPresenceStatus(MUNSDailyPresence.PRESENCESTATUS_Libur);
			} 
			else if (newDaily.getDay().equals(m_MonthlySummary.getNoWorkDay())){
				newDaily.setDayType(MUNSDailyPresence.DAYTYPE_HariLiburMingguan);	
				newDaily.setPresenceStatus(MUNSDailyPresence.PRESENCESTATUS_Libur);
			}
			else 
			{
				newDaily.setDayType(MUNSDailyPresence.DAYTYPE_HariKerjaBiasa);
				newDaily.setPresenceStatus(MUNSDailyPresence.PRESENCESTATUS_FullDay);
			}

			/* no need as daily presence should have had created if leave permission created.
			MUNSLeavePermissionTrx leavePerm = MUNSLeavePermissionTrx.get(
					m_Ctx, monthly.getUNS_Employee_ID()
					, new Timestamp(calendarStartOfPeriod.getTimeInMillis()), m_trxName);
					
			if (null != leavePerm ) {
				newDaily.setDayPresence(leavePerm);
			}
			*/
			
			newDaily.saveEx();
			calendarStartOfPeriod.add(Calendar.DATE, 1);
			i++;
			days++;
		} 
		
		msg = "Daily Presence records are created."; 
		
		m_MonthlySummary.updateData();
		addLog(0, null, new BigDecimal (days), msg, m_MonthlySummary.get_Table_ID(), m_MonthlySummary.get_ID());
		return null;
	}

	private boolean isNonBusinessDay(Timestamp thisDate, int Org_ID) {
		return MNonBusinessDay.isNonBusinessDay(m_Ctx, thisDate, Org_ID, m_trxName);
	}
}
