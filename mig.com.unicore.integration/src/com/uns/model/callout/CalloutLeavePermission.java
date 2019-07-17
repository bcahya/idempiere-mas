/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;


import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_JobCategory;
import org.compiere.model.MNonBusinessDay;
import org.compiere.model.X_C_NonBusinessDay;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;

import com.uns.model.I_UNS_LeavePermissionTrx;
import com.uns.model.MUNSDispensationConfig;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSEmployeeAllowanceRecord;
import com.uns.model.MUNSLeavePermissionTrx;
import com.uns.model.MUNSLeaveReservedConfig;
import com.uns.model.MUNSYearlyPresenceSummary;
import com.uns.model.X_UNS_LeavePermissionTrx;
import com.uns.model.X_UNS_YearlyPresenceSummary;


/**
 * @author eko
 *
 */
public class CalloutLeavePermission implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutLeavePermission() {
	}
	

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) 
	{	
		String retValue = null;
		
		
		if (mField.getColumnName().equals(X_UNS_LeavePermissionTrx.COLUMNNAME_LeaveDateEnd)
			|| mField.getColumnName().equals(X_UNS_LeavePermissionTrx.COLUMNNAME_LeaveDateStart)
			|| mField.getColumnName().equals(X_UNS_LeavePermissionTrx.COLUMNNAME_LeavePeriodType))
		{
			if(null == value)
				return "";
				
			String leaveType = mTab.get_ValueAsString(MUNSLeavePermissionTrx.COLUMNNAME_LeaveType);
			if (null == leaveType)
				return "";
			if (leaveType.equals(MUNSLeavePermissionTrx.LEAVETYPE_MaternityHamilPlusMelahirkan)
					&& mField.getColumnName().equals(X_UNS_LeavePermissionTrx.COLUMNNAME_LeaveDateStart))
				retValue = this.maternityEndDate(ctx, WindowNo, mTab, mField, value);
			if (retValue == null)
				retValue = updateRequestedLeave(ctx, WindowNo, mTab, mField, value);
		}
		else if(mField.getColumnName().equals(X_UNS_LeavePermissionTrx.COLUMNNAME_UNS_Employee_ID))
		{
			retValue = this.YearlyPresenceSummaryID(ctx, WindowNo, mTab, mField, value);
		}
		else if(mField.getColumnName().equals(X_UNS_LeavePermissionTrx.COLUMNNAME_LeaveType))
		{
			retValue = this.lastLeaveUsed(ctx, WindowNo, mTab, mField, value);
		}
		else if (mField.getColumnName().equals(X_UNS_LeavePermissionTrx.COLUMNNAME_C_Year_ID))	
			retValue = setYear(ctx, WindowNo, mTab, mField, value);
	
		return retValue;
	}
	
	
	//update LeaveRequest
	public String updateRequestedLeave(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) 
	{		
		I_UNS_LeavePermissionTrx leaveTrx = 
				GridTabWrapper.create(mTab, I_UNS_LeavePermissionTrx.class);
		
		Timestamp dateStart = leaveTrx.getLeaveDateStart();
		Timestamp dateEnd = leaveTrx.getLeaveDateEnd();
				
		if (dateStart == null || dateEnd== null)
			return null;
		
		if (leaveTrx.getLeavePeriodType() == null)
			return null;
		String leavePeriodType = leaveTrx.getLeavePeriodType();
		
		BigDecimal leaveRequested = 
				MUNSLeavePermissionTrx.calculateLeaveRequested(
						ctx, 
						(MUNSEmployee) leaveTrx.getUNS_Employee(), 
						dateStart, 
						dateEnd,
						leaveTrx.getLeaveType(),
						leavePeriodType,
						MUNSDispensationConfig.isKeguguran(leaveTrx.getUNS_DispensationConfig()),
						Trx.createTrxName());
		
		if (leaveRequested.signum() < 1)
			return "Please select Date Start less than or equal to Date End.";
		
		mTab.setValue(I_UNS_LeavePermissionTrx.COLUMNNAME_LeaveRequested, leaveRequested);

		MUNSEmployee employee = (MUNSEmployee) leaveTrx.getUNS_Employee();
		
		Timestamp backToWorkTS = null;
		
		if (leavePeriodType.equals(MUNSLeavePermissionTrx.LEAVEPERIODTYPE_EndDateIsHalfDay)
			|| leavePeriodType.equals(MUNSLeavePermissionTrx.LEAVEPERIODTYPE_StartAndEndDateIsHalfDay))
		{
			boolean prevDayIsNonBusinessDay = true;
			Calendar theEndCal = Calendar.getInstance();
			theEndCal.setTime(leaveTrx.getLeaveDateEnd());
			
			while (prevDayIsNonBusinessDay)
			{
				backToWorkTS = new Timestamp(theEndCal.getTimeInMillis());
				if (employee.isHoliday(theEndCal.get(Calendar.DAY_OF_WEEK)) 
					|| MNonBusinessDay.isNonBusinessDay(ctx, backToWorkTS, leaveTrx.getAD_Org_ID(), null))
				{
					theEndCal.add(Calendar.DATE, -1);
				}
				else {
					prevDayIsNonBusinessDay = false;
				}
			}
		}
		else {
			boolean nextDayIsNonBusinessDay = true;
			Calendar theEndCal = Calendar.getInstance();
			theEndCal.setTime(leaveTrx.getLeaveDateEnd());
			theEndCal.add(Calendar.DATE, 1);
			
			while (nextDayIsNonBusinessDay)
			{
				backToWorkTS = new Timestamp(theEndCal.getTimeInMillis());
				if (employee.isHoliday(theEndCal.get(Calendar.DAY_OF_WEEK)) 
					|| MNonBusinessDay.isNonBusinessDay(ctx, backToWorkTS, employee.getAD_Org_ID(), null))
				{
					theEndCal.add(Calendar.DATE, 1);
				}
				else {
					nextDayIsNonBusinessDay = false;
				}
			}
		}
		mTab.setValue(MUNSLeavePermissionTrx.COLUMNNAME_BackFromLeaveDate, backToWorkTS);
		
		return "";
	}
	
	public String updateRequestedLeave_Old(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value) 
	{
		
		String sql = "SELECT * FROM " + X_C_NonBusinessDay.Table_Name 
				+ " WHERE " + X_C_NonBusinessDay.COLUMNNAME_Date1
				+ " =?";
		I_UNS_LeavePermissionTrx leaveTrx = 
				GridTabWrapper.create(mTab, I_UNS_LeavePermissionTrx.class);
		
		Timestamp dateStart = leaveTrx.getLeaveDateStart();
		Timestamp dateEnd = leaveTrx.getLeaveDateEnd();
				
		if (dateStart == null || dateEnd== null)
			return null;
		
		Calendar calendar = Calendar.getInstance();
		long dateEndLong = dateEnd.getTime();
		long dateStartLong = dateStart.getTime();
		if (dateStartLong > dateEndLong){
			leaveTrx.setLeaveDateEnd(null);
			return "LeaveDateEnd must be greater than LeaveDateStart!";
		}
		int nonBusinesDay = 0;
		calendar.setTimeInMillis(dateStartLong);
		int start = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTimeInMillis(dateEndLong);
		int end = calendar.get(Calendar.DAY_OF_YEAR);
		
		for (int i= start; i<=end; i++)
		{
			calendar.set(Calendar.DAY_OF_YEAR, i);
			Timestamp thisDate = new Timestamp(calendar.getTimeInMillis());
			int count = DB.getSQLValue(null, sql, thisDate);
			if (count > 0 || calendar.get(Calendar.DAY_OF_WEEK)==1)
				nonBusinesDay +=1;
		}
		
		long lvreq = dateEndLong-dateStartLong;
		
		calendar.setTimeInMillis(lvreq);
		int dayReq = calendar.get(Calendar.DAY_OF_YEAR)-nonBusinesDay;
		mTab.setValue(I_UNS_LeavePermissionTrx.COLUMNNAME_LeaveRequested,dayReq);
		
		return "";
	}
	
	public String setYear (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value){
		Integer C_Year_ID = (Integer) value;
		Integer AD_Org_ID = (Integer) mTab.getValue("AD_Org_ID");
		Integer UNS_Employ_ID = (Integer) mTab.getValue(MUNSYearlyPresenceSummary.COLUMNNAME_UNS_Employee_ID);
		if (null == C_Year_ID)
			return "";
		if (null == UNS_Employ_ID)
			return "";
		if (null == UNS_Employ_ID || UNS_Employ_ID.intValue() <=0) {
			return "";
		}
		
		int UNS_YearlyPresenceSummary_ID = DB.getSQLValue(
				null, "SELECT "+X_UNS_YearlyPresenceSummary.COLUMNNAME_UNS_YearlyPresenceSummary_ID
				+ " FROM " + X_UNS_YearlyPresenceSummary.Table_Name 
				+ " WHERE " + X_UNS_YearlyPresenceSummary.COLUMNNAME_UNS_Employee_ID + "=?"
				+ " AND " + X_UNS_YearlyPresenceSummary.COLUMNNAME_AD_Org_ID + "=?" 
				+ " AND " + X_UNS_YearlyPresenceSummary.COLUMNNAME_C_Year_ID + "=?"
				, UNS_Employ_ID ,AD_Org_ID, C_Year_ID);
	
		mTab.setValue(X_UNS_LeavePermissionTrx.COLUMNNAME_UNS_YearlyPresenceSummary_ID, UNS_YearlyPresenceSummary_ID);
		return "";
	}
	
	public String YearlyPresenceSummaryID (Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value){
		Integer UNS_Employ_ID = (Integer) value;
		Integer AD_Org_ID = (Integer) mTab.getValue("AD_Org_ID");
		Integer C_Year_ID = (Integer) mTab.getValue(MUNSYearlyPresenceSummary.COLUMNNAME_C_Year_ID);
		if (null == C_Year_ID)
			return "";
		if (null == UNS_Employ_ID)
			return "";
		if (null == UNS_Employ_ID || UNS_Employ_ID.intValue() <=0) {
			return "";
		}
		
		int UNS_YearlyPresenceSummary_ID = DB.getSQLValue(
				null, "SELECT "+X_UNS_YearlyPresenceSummary.COLUMNNAME_UNS_YearlyPresenceSummary_ID
				+ " FROM " + X_UNS_YearlyPresenceSummary.Table_Name 
				+ " WHERE " + X_UNS_YearlyPresenceSummary.COLUMNNAME_UNS_Employee_ID + "=?"
				+ " AND " + X_UNS_YearlyPresenceSummary.COLUMNNAME_AD_Org_ID + "=?" 
				+ " AND " + X_UNS_YearlyPresenceSummary.COLUMNNAME_C_Year_ID + "=?"
				, UNS_Employ_ID ,AD_Org_ID, C_Year_ID);
	
		mTab.setValue(X_UNS_LeavePermissionTrx.COLUMNNAME_UNS_YearlyPresenceSummary_ID, UNS_YearlyPresenceSummary_ID);
		return "";
	}
	
	
	public String lastLeaveUsed (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (null == value || value.equals(""))
			return null;
		
		String msg = null;
		
		I_UNS_LeavePermissionTrx leavPermission = GridTabWrapper.create(mTab, I_UNS_LeavePermissionTrx.class);
		
		if (!leavPermission.getLeaveType().equals(X_UNS_LeavePermissionTrx.LEAVETYPE_LeaveCuti))
			return null;
		
		//if (null == leavPermission.getUNS_YearlyPresenceSummary())
		//	return "Not found presence summary";
		
		//I_UNS_YearlyPresenceSummary yearlyPresenceSummary = leavPermission.getUNS_YearlyPresenceSummary();
		Timestamp currDate = leavPermission.getLeaveDateStart();
		if (currDate == null) {
			if (leavPermission.getC_Period_ID() > 0)
				currDate = leavPermission.getC_Period().getStartDate();
			else 
				currDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		}
		String trxName = Trx.createTrxName();
		
		MUNSEmployeeAllowanceRecord allowance = 
				MUNSEmployeeAllowanceRecord.getCreateLeaveReserved(
						ctx, (MUNSEmployee) leavPermission.getUNS_Employee(), currDate, trxName);
		if (allowance != null)
		{
			Trx trx = Trx.get(trxName, false);
			trx.commit();
			leavPermission.setTotalLeaveClaimReserved(allowance.getLeaveClaimReserved());
			leavPermission.setLastLeaveUsed(allowance.getLeaveReservedUsed());
		}
		else {
			msg = "Cannot create leave type [LEAVE/CUTI] for the employee.";
			leavPermission.setLeaveType(X_UNS_LeavePermissionTrx.LEAVETYPE_Other);
		}
		return msg;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String maternityEndDate (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		I_UNS_LeavePermissionTrx leaveTrx = 
				GridTabWrapper.create(mTab, I_UNS_LeavePermissionTrx.class);
		
		MUNSEmployee EmployeeID = new MUNSEmployee(ctx,leaveTrx.getUNS_Employee_ID(),null);
		
		I_C_JobCategory jobCategory = EmployeeID.getC_Job().getC_JobCategory();
		
		MUNSLeaveReservedConfig leaveReservedConf = MUNSLeaveReservedConfig.get
							(ctx, jobCategory.getC_JobCategory_ID(), EmployeeID.getNationality(), null);
		
		Timestamp startDate = null;
		Timestamp endDate = null;
		int maternityClaimReserverd = 0;
		
		startDate = leaveTrx.getLeaveDateStart();
		maternityClaimReserverd = leaveReservedConf.getMaternityClaimReserved();
		if (maternityClaimReserverd == 0)
			return "Maternity Claim Reserverd is not configured";
		if (startDate == null)
			return null;
		
		endDate = TimeUtil.addDays(startDate, maternityClaimReserverd -1);
		
		leaveTrx.setLeaveDateEnd(endDate);
		return null;
	}
}
