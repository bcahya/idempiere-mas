/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author eko
 *
 */
public class MUNSYearlyPresenceSummary extends X_UNS_YearlyPresenceSummary 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MUNSMonthlyPresenceSummary[] m_lines = null;

	/**
	 * @param ctx
	 * @param UNS_YearlyPresenceSummary_ID
	 * @param trxName
	 */
	public MUNSYearlyPresenceSummary(Properties ctx,
			int UNS_YearlyPresenceSummary_ID, String trxName) {
		super(ctx, UNS_YearlyPresenceSummary_ID, trxName);
	}
	
	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSYearlyPresenceSummary(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @param leavePermission
	 */
	public MUNSYearlyPresenceSummary(MUNSLeavePermissionTrx leavePermission)
	{
		//super(leavePermission.getCtx(), 0 , leavePermission.get_TrxName());
		this(leavePermission.getCtx(), 
			 leavePermission.getEmployee(), 
			 leavePermission.getC_Year_ID(), 
			 leavePermission.get_TrxName());
	}

	/**
	 * 
	 * @param employee
	 * @param year the Fiscal Year.
	 */
	public MUNSYearlyPresenceSummary(Properties ctx, MUNSEmployee employee, int C_Year_ID, String trxName)
	{
		super(ctx, 0 , trxName);
		setClientOrg(employee);
		setC_Year_ID(C_Year_ID);
		setUNS_Employee_ID(employee.get_ID());
		setC_BPartner_ID(employee.getC_BPartner_ID());
		setC_Job_ID(employee.getC_Job_ID());
		saveEx();
	}

	/**
	 * Get employee's yearly presence summary for given fiscal year, and create it if not exist.
	 * 
	 * @param ctx
	 * @param employee
	 * @param fiscalYear
	 * @param trxName
	 * @return
	 */
	public static MUNSYearlyPresenceSummary getCreate(
			Properties ctx, MUNSEmployee employee, String fiscalYear, String trxName)
	{
		MUNSYearlyPresenceSummary yearlyPresence = get(ctx, employee.get_ID(), fiscalYear, trxName);
		
		if (null == yearlyPresence) 
		{
			int yearID = DB.getSQLValue(trxName,  "SELECT C_Year_ID FROM C_Year WHERE FiscalYear=?", fiscalYear);
			
			if (yearID > 0)
				yearlyPresence = new MUNSYearlyPresenceSummary(ctx, employee, yearID, trxName);
			else
				throw new AdempiereException("Please create Period for year of " + fiscalYear);
		}
		return yearlyPresence;
	}
	
	/**
	 * Get the employee's yearly presence summary for the given year.
	 * 
	 * @param ctx
	 * @param employeeID
	 * @param year
	 * @param trxName
	 * @return
	 */
	public static MUNSYearlyPresenceSummary get(Properties ctx, int employeeID, String fiscalYear, String trxName)
	{
		String whereClause = "UNS_Employee_ID=? AND C_Year_ID=(SELECT y.C_Year_ID FROM C_Year y WHERE " +
				"	y.FiscalYear=?)";
		return Query.get(ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, whereClause, trxName)
				.setParameters(employeeID, fiscalYear)
				.firstOnly();
	}
	
	/**
	 * 
	 * @param whereClause
	 * @return
	 */
	public MUNSLeavePermissionTrx[] getLeavePermisions(String whereClause)
	{
		String whereClauseFinal = "";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		
		List<MUNSLeavePermissionTrx> list = Query.get(getCtx(), UNSHRMModelFactory.getExtensionID(), 
											MUNSLeavePermissionTrx.Table_Name, whereClauseFinal, get_TrxName())
											.setOrderBy(MUNSLeavePermissionTrx.COLUMNNAME_UNS_LeavePermissionTrx_ID)
											.list();
		return list.toArray(new MUNSLeavePermissionTrx[list.size()]);
	}
	

	/**
	 * 
	 * @param C_Period_ID
	 * @return
	 */
	public MUNSLeavePermissionTrx[] getLeavePermissionsOf(int C_Period_ID)
	{
		String whereClause = MUNSLeavePermissionTrx.COLUMNNAME_UNS_YearlyPresenceSummary_ID + "=" 
								+ getUNS_YearlyPresenceSummary_ID()
				+ " AND " + MUNSLeavePermissionTrx.COLUMNNAME_C_Period_ID + "=" + C_Period_ID
				+ " AND " + MUNSLeavePermissionTrx.COLUMNNAME_UNS_Employee_ID + " = " +getUNS_Employee_ID()
				+ " AND " + MUNSLeavePermissionTrx.COLUMNNAME_AD_Org_ID + "=" + getAD_Org_ID();
		MUNSLeavePermissionTrx[] leavePermTrx = getLeavePermisions(whereClause);
		return leavePermTrx;
	}
	
	/**
	 * 
	 * @param whereClause
	 * @return
	 */
	public MUNSMonthlyPresenceSummary[] getLines(String whereClause)
	{
		String whereClauseFinal = "UNS_YearlyPresenceSummary_ID=? AND IsActive='Y'";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		
		List<MUNSMonthlyPresenceSummary> list = Query.get(getCtx(), 
								UNSHRMModelFactory.EXTENSION_ID, MUNSMonthlyPresenceSummary.Table_Name, 
								whereClauseFinal, get_TrxName())
								.setParameters(new Object[]{getUNS_YearlyPresenceSummary_ID()})
								.setOrderBy(MUNSMonthlyPresenceSummary.COLUMNNAME_UNS_MonthlyPresenceSummary_ID)
								.list();
		  
		return list.toArray(new MUNSMonthlyPresenceSummary[list.size()]);		
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSMonthlyPresenceSummary[] getLines(boolean requery)
	{
		if(m_lines == null || requery)
			m_lines = getLines(null);
		
		return m_lines;
	}

	/**
	 * 
	 * @return
	 */
	public MUNSMonthlyPresenceSummary[] getLines()
	{
		return getLines(false);
	}

	
	/**
	 * 
	 * @param leavePerm
	 * @return 
	 *
	public boolean UpdateBy(MUNSLeavePermissionTrx leavePerm)
	{
		//setTotalLeaveUsed(leavePerm.getTotalLeaveUsedOfYear());
		//setTotalSKK(leavePerm.getTotalSKKYear());
		//setTotalSK(leavePerm.getTotalSKYear());
		if ( !save())
			return false;
		MUNSMonthlyPresenceSummary[] monthlyPresences = getLines(false);
		int count = 0;
		if (null != monthlyPresences){
			for (MUNSMonthlyPresenceSummary monthlyPresence : monthlyPresences)	{
				if (monthlyPresence.getC_Period_ID() != leavePerm.getC_Period_ID())
					continue;
				
				GenerateDailyRecord createDailyPresence = new GenerateDailyRecord(
						getCtx(), monthlyPresence.get_ID(), get_TrxName());
				String msg = createDailyPresence.generateDailyPresence();
				if (null != msg)
					throw new AdempiereUserError(msg);
				monthlyPresence.updateData();
				count++;
				break;
			}
		}		
		if (null == monthlyPresences || count == 0)
			return false;
		return true;
	}
	*/
	
	@Override
	protected boolean beforeDelete() {
		
		for (MUNSMonthlyPresenceSummary mp : getLines()){
			
			mp.deleteLines();
			mp.deleteEx(true, get_TrxName());
		}
		
		return super.beforeDelete();
	}

	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		if (newRecord)
		{
			/*
			MUNSLeaveReservedConfig lrc = MUNSLeaveReservedConfig.get(
					getCtx(), 
					getC_Job().getC_JobCategory_ID(), 
					getUNS_Employee().getNationality(), 
					get_TrxName());
			
			if (lrc == null)
				throw new AdempiereUserError("Please create Leave Reserved Configuration for " 
											 + getC_Job().getC_JobCategory().getName());
			
			MUNSEmployee employee = (MUNSEmployee) getUNS_Employee();
			
			I_UNS_Contract_Recommendation employeeContract = employee.getUNS_Contract_Recommendation();
			
			Calendar contractCal = Calendar.getInstance();
			
			int currFiscalYear = Integer.valueOf(getC_Year().getFiscalYear());
			
			Calendar presenceYearCal = Calendar.getInstance();
			presenceYearCal.set(currFiscalYear, 0, 1);
			
			int currYearTotalDays = presenceYearCal.getActualMaximum(Calendar.DAY_OF_YEAR);

			float currYearLeaveProportion = 1;
			
			if (contractCal.after(presenceYearCal))
			{
				contractCal.setTime(employeeContract.getDateContractStart());
				
				int contractStartDOY = contractCal.get(Calendar.DAY_OF_YEAR);
				
				//reset it to the end of current fiscal year.
				presenceYearCal.set(currFiscalYear, 11, 31);
				//reset it to the end of contract date.
				contractCal.setTime(employeeContract.getDateContractEnd());
				
				if (contractCal.before(presenceYearCal)) // jk end of contract < end of current fiscal year. 
					currYearTotalDays = contractCal.getActualMaximum(Calendar.DAY_OF_YEAR);
				
				currYearLeaveProportion = (currYearTotalDays-contractStartDOY) / currYearTotalDays;
			}
			else if (!MUNSEmployee.EMPLOYMENTSTATUS_Bulanan.equals(getEmploymentStatus()))
			{
				contractCal.setTime(employeeContract.getDateContractEnd());
				
				int yearEnd = contractCal.get(Calendar.YEAR);
				
				if (yearEnd == currFiscalYear)
				{
					int remainingContract = contractCal.get(Calendar.DAY_OF_YEAR);
					
					currYearLeaveProportion = remainingContract / currYearTotalDays;
				}
			}
			setTotalLeaveClaimReserved(Math.round(lrc.getLeaveClaimReserved() * currYearLeaveProportion));
			 
			 */
		}
		
		I_UNS_Employee employe = getUNS_Employee();		
		
		String payterm = MUNSPayrollTermConfig.getPayrollTermOf(
				getAD_Org_ID()
				, employe.getC_BPartner_ID()
				, employe.getUNS_Contract_Recommendation()
				.getNextContractType()
				, Env.getContextAsDate(getCtx(), "Date")
				, get_TrxName());
		
		if(null == payterm)
			payterm = employe.getPayrollTerm();
			
		setPayrollTerm(payterm);

		return super.beforeSave(newRecord);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param AD_Org_ID
	 * @param C_Year_ID
	 * @param UNS_Employee_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSYearlyPresenceSummary getByLeave(Properties ctx, 
			int AD_Org_ID, int C_Year_ID, int UNS_Employee_ID, String trxName)
	{
		String whereClause = "AD_Org_ID=? AND C_Year_ID=? AND UNS_Employee_ID=?";
		return Query.get(ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, whereClause, trxName)
				.setParameters(AD_Org_ID, C_Year_ID, UNS_Employee_ID)
				.firstOnly();
		/*
		String sql = "SELECT UNS_YearlyPresenceSummary_ID FROM UNS_YearlyPresenceSummary " +
				"WHERE AD_Org_ID=? AND C_Year_ID=? AND UNS_Employee_ID=?";
		int yearPresence_ID = DB.getSQLValue(trxName, sql, AD_Org_ID, C_Year_ID, UNS_Employee_ID);
		
		if (yearPresence_ID==-1)
			return null;
		
		return new MUNSYearlyPresenceSummary(ctx, yearPresence_ID, trxName);
		*/
	}
	
	public MUNSMonthlyPresenceSummary getMonth (Timestamp date)
	{
		getLines(true);
		for (int i=0; i<m_lines.length; i++)
		{
			if ((m_lines[i].getStartDate().before(date)
					&& m_lines[i].getEndDate().after(date))
					|| m_lines[i].getStartDate().equals(date)
					|| m_lines[i].getEndDate().equals(date))
			{
				return m_lines[i];
			}
		}
		
		return null;
	}
}
