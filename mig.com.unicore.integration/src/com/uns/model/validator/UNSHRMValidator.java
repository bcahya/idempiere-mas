/**
 * 
 */
package com.uns.model.validator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;

import org.compiere.model.MClient;
import org.compiere.model.MNonBusinessDay;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.uns.util.MessageBox;

import com.uns.model.MUNSAttConfiguration;
import com.uns.model.MUNSCheckInOut;
import com.uns.model.MUNSDailyPresence;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSMonthlyPresenceSummary;
import com.uns.model.MUNSOTConfirmation;
import com.uns.model.MUNSOTRequest;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSSlotType;
import com.uns.model.X_UNS_CheckInOut;
import com.uns.model.utilities.HRMUtils;

/**
 * @author Menjangan
 *
 */
public class UNSHRMValidator implements ModelValidator {

	private int m_AD_Client_ID = 0;
	CLogger log = CLogger.getCLogger(this.getClass());
	/**
	 * 
	 */
	public UNSHRMValidator() 
	{
		super ();
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#initialize(org.compiere.model.
	 * ModelValidationEngine, org.compiere.model.MClient)
	 */
	@Override
	public void initialize(ModelValidationEngine engine, MClient client) 
	{
		if(client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
			log.log(Level.INFO, client.toString());
		}
		else
		{
			log.log(Level.INFO, "Initializing global validator -"
					+ this.toString());
		}
		
		engine.addDocValidate(MUNSOTRequest.Table_Name, this);
		engine.addModelChange(MUNSOTRequest.Table_Name, this);
		engine.addDocValidate(MUNSOTConfirmation.Table_Name, this);
		engine.addModelChange(MUNSOTConfirmation.Table_Name, this);
		engine.addModelChange(MUNSDailyPresence.Table_Name, this);
		engine.addModelChange(MUNSCheckInOut.Table_Name, this);
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#getAD_Client_ID()
	 */
	@Override
	public int getAD_Client_ID() 
	{
		return m_AD_Client_ID;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#login(int, int, int)
	 */
	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) 
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#modelChange(
	 * org.compiere.model.PO, int)
	 */
	@Override
	public String modelChange(PO po, int type) throws Exception 
	{
		String error = null;
		switch (po.get_TableName()) 
		{
			case MUNSOTRequest.Table_Name:
				if (type == TYPE_BEFORE_CHANGE || type == TYPE_AFTER_NEW)
				{
					error = doValidateOTRequestDate((MUNSOTRequest) po);
				}
				break;
			case MUNSOTConfirmation.Table_Name :
				if (type == TYPE_BEFORE_CHANGE || type == TYPE_AFTER_NEW)
				{
					error = doValidateOTConfirmationDate(
							(MUNSOTConfirmation) po);
				}
				break;
			case MUNSDailyPresence.Table_Name :
				if (type == TYPE_BEFORE_CHANGE || type == TYPE_BEFORE_NEW)
				{
					error = doBeforeSaveDailyPresence(po);
				}
				break;
			case MUNSMonthlyPresenceSummary.Table_Name :
				if (type == TYPE_BEFORE_NEW)
				{
					error = initMonthlyPresenceValues(po);
				}
				break;
			case MUNSEmployee.Table_Name :
				if (type == TYPE_AFTER_CHANGE || type == TYPE_AFTER_NEW)
				{
					error = validateDuplicateEmployee(po);
				}
			case MUNSCheckInOut.Table_Name :
				if(type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE)
				{
					error = createDaily((MUNSCheckInOut) po, type);
				}
			default:
				break;
		}
		
		return error;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#docValidate
	 * (org.compiere.model.PO, int)
	 */
	@Override
	public String docValidate(PO po, int timing) 
	{
		String error = null;
		switch (po.get_TableName()) 
		{
			case MUNSOTRequest.Table_Name:
				if (timing == TIMING_BEFORE_PREPARE)
				{
					error = doValidateOTRequestDate((MUNSOTRequest) po);
				}
				break;
			case MUNSOTConfirmation.Table_Name :
				if (timing == TIMING_BEFORE_PREPARE)
			{
				error = doValidateOTConfirmationDate(
						(MUNSOTConfirmation) po);
			}
				if(timing == TIMING_BEFORE_COMPLETE)
			{
				error = upDailyPresence((MUNSOTConfirmation) po);
			}
				if(timing == TIMING_AFTER_VOID)
			{
				error = upDailyPresenceOnVoid((MUNSOTConfirmation) po);
			}
				break;
			default:
				break;
		}
		
		return error;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	private String doValidateOTRequestDate (MUNSOTRequest request)
	{
		if (request.getStartTime().after(request.getEndTime()))
		{
			return "End Time should be greater than of the Start Time";
		}
		if(request.getUNS_Employee_ID() > 0)
		{
			HRMUtils util = new HRMUtils((MUNSEmployee) request.getUNS_Employee(), request.getStartTime());
			String msg = util.init(true);
			if(!Util.isEmpty(msg, false))
				return msg;
			
			if(util.isWeeklyHoliday() || util.isNationalHoliday())
				return null;
			
			if((request.getStartTime().before(util.getTimeOutRules())
					&& request.getStartTime().after(util.getTimeInRules()))
					|| (request.getEndTime().after(util.getTimeInRules())
					&& request.getEndTime().before(util.getTimeOutRules())))
			{
				return "This process can not be continued because the work hours of employees are on the job."
						+ "\nMinimum Time In : " + util.getTimeInRules().toString()
						+ "\nMaximum Time Out : " + util.getTimeOutRules().toString();
			}
		}
		
//		MUNSEmployee employee = new MUNSEmployee(
//				request.getCtx(), request.getUNS_Employee_ID(), 
//				request.get_TrxName());
		
//		String shiftType = employee.getShift();
//		Calendar curCal = Calendar.getInstance();
//		curCal.setTimeInMillis(request.getStartTime().getTime());
		
//		if (MUNSEmployee.SHIFT_Shift.equals(shiftType))
//		{
//			MUNSSlotType slotType = MUNSSlotType.getByEmployee(request.getCtx(), 
//									request.getUNS_Employee_ID(), request.get_TrxName());
//			if (slotType == null)
//			{
//				return "Employee shift is not configured.";
//			}
//			
//			boolean isHoliday = slotType.isDaySlot() && !slotType.IsWorkDay(
//					new Integer(request.getDay()));
//			if (!isHoliday)
//			{
//				isHoliday = MNonBusinessDay.isNonBusinessDay(
//						request.getCtx(), request.getStartTime(), 
//						request.get_TrxName());
//			}
//			
//			if (slotType.isTimeSlot() && !isHoliday)
//			{
//				int add = MUNSAddWorkHours.getAddWorkHours(
//						request.get_TrxName(), request.getUNS_Employee_ID(), request.getStartTime());
//				int earlier = MUNSAddWorkHours.getEarlierAddWorkHours(
//						request.get_TrxName(), request.getUNS_Employee_ID(), request.getEndTime());
//				
//				Calendar cal = Calendar.getInstance();
//				cal.setTimeInMillis(request.getStartTime().getTime());
//				cal.add(Calendar.HOUR_OF_DAY, -earlier);
//				Timestamp minTimeIn = new Timestamp(cal.getTimeInMillis());
//				
//				cal.setTimeInMillis(request.getEndTime().getTime());
//				cal.set(Calendar.SECOND, 0);
//				cal.add(Calendar.HOUR_OF_DAY, add);
//				Timestamp maxTimeOut = new Timestamp(cal.getTimeInMillis());
//				
//				if(minTimeIn.before(request.getStartTime()) || maxTimeOut.after(request.getEndTime()))
//				{
//					return "This process can not be continued because the work hours of employees are on the job."
//							+ "\nMinimum Time In : " + minTimeIn.toString()
//							+ "\nMaximum Time Out : " + maxTimeOut.toString();
//				}
				
//				Calendar slotEndCal = Calendar.getInstance();
//				slotEndCal.setTimeInMillis(slotType.getTimeSlotEnd().getTime());
//				slotEndCal.set(Calendar.YEAR, curCal.get(Calendar.YEAR));
//				slotEndCal.set(Calendar.MONTH, curCal.get(Calendar.MONTH));
//				slotEndCal.set(Calendar.DATE, curCal.get(Calendar.DATE));
//				Timestamp curSlotEnd = new Timestamp(
//						slotEndCal.getTimeInMillis());
//				if (request.getStartTime().before(curSlotEnd)
//						&& !isHoliday)
//				{
//					return " Invalid Start Time. Start Time should be greater "
//							+ " than of the Time Slot End on Slot Type.";
//				}
//			}
//		}
		
		return null;
	}
	
	/**
	 * 
	 * @param confirm
	 * @return
	 */
	private String doValidateOTConfirmationDate (MUNSOTConfirmation confirm)
	{
		if (confirm.getStartTime().after(confirm.getEndTime()))
		{
			return "End Time should be greater than of the Start Time";
		}
		
//		MUNSEmployee employee = new MUNSEmployee(
//				confirm.getCtx(), confirm.getUNS_Employee_ID(), 
//				confirm.get_TrxName());
		
//		String shiftType = employee.getShift();
//		Calendar curCal = Calendar.getInstance();
//		curCal.setTimeInMillis(confirm.getStartTime().getTime());
		
//		if (MUNSEmployee.SHIFT_Shift.equals(shiftType))
//		{
//			int UNS_SlotType_ID = MUNSEmpStation.getEmployeeSlotType_ID(
//					confirm.get_TrxName(), employee.get_ID());
//			if (UNS_SlotType_ID <= 0)
//			{
//				return "Employee shift is not configured.";
//			}
//			
//			MUNSSlotType slotType = new MUNSSlotType(
//					confirm.getCtx(), UNS_SlotType_ID, confirm.get_TrxName());
//			
//			String day = DB.getSQLValueString(confirm.get_TrxName(), 
//					"SELECT Day FROM UNS_OTRequest WHERE UNS_OTRequest_ID = ?", 
//					confirm.getUNS_OTRequest_ID());
//			
//			boolean isHoliday = slotType.isDaySlot() && !slotType.IsWorkDay(
//					new Integer(day));
//			
//			if (!isHoliday)
//			{
//				isHoliday = MNonBusinessDay.isNonBusinessDay(
//						confirm.getCtx(), confirm.getStartTime(), confirm.getAD_Org_ID(), 
//						confirm.get_TrxName());
//			}
//			
//			if (slotType.isTimeSlot())
//			{
//				Calendar slotEndCal = Calendar.getInstance();
//				slotEndCal.setTimeInMillis(slotType.getTimeSlotEnd().getTime());
//				slotEndCal.set(Calendar.YEAR, curCal.get(Calendar.YEAR));
//				slotEndCal.set(Calendar.MONTH, curCal.get(Calendar.MONTH));
//				slotEndCal.set(Calendar.DATE, curCal.get(Calendar.DATE));
//				Timestamp curSlotEnd = new Timestamp(
//						slotEndCal.getTimeInMillis());
//				if (confirm.getStartTime().before(curSlotEnd)
//						&& !isHoliday)
//				{
//					return " Invalid Start Time. Start Time should be greater "
//							+ " than of the Time Slot End on Slot Type.";
//				}
//			}
//		}
		
		return null;
	}
	
	/** 
	 * @param po
	 * @return
	 */
	private String initMonthlyPresenceValues (PO po)
	{
		MUNSMonthlyPresenceSummary monthly = (MUNSMonthlyPresenceSummary) po;
		if (monthly.getC_Period_ID() == 0 || monthly.getStartDate() == null
				|| monthly.getEndDate() == null 
				|| monthly.is_ValueChanged(
						MUNSMonthlyPresenceSummary.COLUMNNAME_C_Period_ID))
		{
			HRMUtils util = new HRMUtils(monthly.getUNS_Employee(), 
					monthly.getC_Period_ID());
			String msg = util.init();
			if (!Util.isEmpty(msg, true))
			{
				return msg;
			}
			
			monthly.setC_Period_ID(util.getC_Period().get_ID());
			monthly.setStartDate(util.getStartDate());
			monthly.setEndDate(util.getEndDate());	
		}
		
		return null;
	}
	
	/**
	 * Validate MUNSDailyPresence on BeforeSave Event.
	 * @param po
	 * @return
	 */
	private String doBeforeSaveDailyPresence (PO po)
	{
		//TODO Lembur normatif dan non normatif
		MUNSDailyPresence day = (MUNSDailyPresence) po;
		MUNSMonthlyPresenceSummary monthly = day.getParent();
		HRMUtils util = new HRMUtils(day.getParent().getUNS_Employee(), 
				day.getPresenceDate());
		String msg = util.init(true);
		if (null != msg)
			return msg;
//		if(!util.isAutoSynchronize())
//			return null;
//		if(!Util.isEmpty(day.getPresenceStatus(), true) && day.getPresenceStatus().equals(MUNSDailyPresence.PRESENCESTATUS_Mangkir))
//			return null;
		
		
		if (day.getDayType() == null || null == day.getTimeInRules() 
				|| null == day.getTimeOutRules() || util.isOverTime())
		{
			day.setDay(util.getDay());
			if(day.getTimeInRules() == null)
				day.setTimeInRules(util.getTimeInRules());
			if(day.getTimeOutRules() == null)
				day.setTimeOutRules(util.getTimeOutRules());
			String dayType = MUNSDailyPresence.DAYTYPE_HariKerjaBiasa;
			String presenceStatus = day.getPresenceStatus();
			boolean isNationalHoliday = util.getPresenceDate() == null ?
					MNonBusinessDay.isNonBusinessDay(po.getCtx(), day.getPresenceDate(), day.getAD_Org_ID(), po.get_TrxName())
					: util.isNationalHoliday();
			if (isNationalHoliday)
			{
				dayType = MUNSDailyPresence.DAYTYPE_HariLiburNasional;
				presenceStatus = MUNSDailyPresence.PRESENCESTATUS_Libur;
			}
			else if (util.isWeeklyHoliday())
			{
				dayType = MUNSDailyPresence.DAYTYPE_HariLiburMingguan;
			}
			if (util.isOverTime() && (util.isWeeklyHoliday() 
					|| util.isNationalHoliday()))
			{
				presenceStatus = MUNSDailyPresence.PRESENCESTATUS_Lembur;
				day.setTimeInRules(util.getStartOverTime());
				day.setTimeOutRules(util.getEndOverTime());
			}
//			if(presenceStatus == null)
//				presenceStatus = MUNSDailyPresence.PRESENCESTATUS_FullDay;
			day.setPresenceStatus(presenceStatus);
			day.setDayType(dayType);
		}
		
		if (day.getPresenceStatus() == null)
		{
			day.setPresenceStatus(MUNSDailyPresence.PRESENCESTATUS_FullDay);
		}
		if (day.getDay().equals(monthly.getNoWorkDay()))
		{
			day.setDayType(MUNSDailyPresence.DAYTYPE_HariLiburMingguan);
		}
		
		if(day.getDayType().equals(MUNSDailyPresence.DAYTYPE_HariKerjaBiasa)
				&& day.getFSTimeIn() != null && day.getFSTimeOut() != null)
		{
			day.setPresenceStatus(MUNSDailyPresence.PRESENCESTATUS_FullDay);
		}
		
		if(day.getDayType().equals(MUNSDailyPresence.DAYTYPE_HariKerjaBiasa)
				&& day.getFSTimeIn() == null && day.getFSTimeOut() == null)
		{
			day.setPresenceStatus(MUNSDailyPresence.PRESENCESTATUS_Mangkir);
		}
		
		if(day.getPermissionType() != null && day.getFSTimeIn() == null
				&& day.getFSTimeOut() == null)
		{
			day.setPresenceStatus(MUNSDailyPresence.PRESENCESTATUS_Izin);
		}
		
		if (day.getDayType().equals(MUNSDailyPresence.DAYTYPE_HariKerjaBiasa) 
				&& day.getPresenceStatus().equals(
						MUNSDailyPresence.PRESENCESTATUS_Lembur))
		{
			return "Cannot set Presence Status \"Lembur\" for Normal Work Day."; 
		}
					
		String presenceStatus = day.getPresenceStatus();
		if (!presenceStatus.equals(MUNSDailyPresence.PRESENCESTATUS_FullDay) 
				&& !presenceStatus.equals(
						MUNSDailyPresence.PRESENCESTATUS_Lembur))
		{
			day.setOvertime(Env.ZERO);
		}
			
		if (MUNSDailyPresence.PRESENCESTATUS_FullDay.equals(presenceStatus))
			day.setPermissionType(null);
				
//		MUNSPayrollConfiguration payConfiguration =
//				MUNSPayrollConfiguration.get(
//						po.getCtx(), day.getPresenceDate(), 
//						po.get_TrxName());
		MUNSPayrollConfiguration payConfiguration = util.getPayConfig();
		
		if (presenceStatus.equals(MUNSDailyPresence.PRESENCESTATUS_Libur) 
		   || presenceStatus.equals(MUNSDailyPresence.PRESENCESTATUS_Mangkir)
		   || presenceStatus.equals(MUNSDailyPresence.PRESENCESTATUS_Lembur)
		   || presenceStatus.equals(MUNSDailyPresence.PRESENCESTATUS_Izin)) 
		{
			day.setWorkHours(BigDecimal.ZERO);
		}
		
		if(day.getTimeInRules() == null)
			day.setTimeInRules(util.getTimeInRules());
		if(day.getTimeOutRules() == null)
			day.setTimeOutRules(util.getTimeOutRules());
		
		if (day.getFSTimeIn() != null && day.getTimeInRules() != null)
		{
			long belatedDuration = day.getFSTimeIn().getTime() 
					- day.getTimeInRules().getTime();
			belatedDuration = belatedDuration / 1000/ 60;
			if(belatedDuration < 0)
				belatedDuration = 0;
			day.setBelatedDuration((int)belatedDuration);
			if (belatedDuration > 0)
			{
				if (!MUNSDailyPresence.PRESENCESTATUS_HalfDay.
						equals(presenceStatus)
						&& !MUNSDailyPresence.PRESENCESTATUS_Izin.
						equals(presenceStatus)
						&& !MUNSDailyPresence.PRESENCESTATUS_Libur.
						equals(presenceStatus)
						&& !MUNSDailyPresence.PRESENCESTATUS_Mangkir.
						equals(presenceStatus)
						&& !MUNSDailyPresence.PRESENCESTATUS_Lembur.
						equals(presenceStatus)
						&& belatedDuration > 0)
				{
					day.setPresenceStatus(MUNSDailyPresence.
							PRESENCESTATUS_Belated);	
				}
			}
		}
		
		if (day.getFSTimeIn() != null && day.getFSTimeOut() != null)
		{ 
			Timestamp in = null;
			Timestamp out = null;

			if(day.getFSTimeIn().compareTo(day.getTimeInRules()) == 1)
				in = day.getFSTimeIn();
			else
				in = day.getTimeInRules();
			if(day.getFSTimeOut().compareTo(day.getTimeOutRules()) == 1)
				out = day.getTimeOutRules();
			else
				out = day.getFSTimeOut();
			long workHours = out.getTime() - in.getTime();
			double hours = (double) workHours /1000/60/60;
			hours = hours - util.getDefaultBreakTime();
			
			double maxOTHours = 0; 
			double addWorkHours = 0;
			MUNSAttConfiguration att = util.getAttConfig();
			long maxInOT = day.getFSTimeIn().getTime() + (att.getMaxLateFSIn() * 1000 * 60);
			long minInOT = day.getFSTimeIn().getTime() - (att.getMaxEarlierFSIn() * 1000 * 60);
			long maxOutOT = day.getFSTimeOut().getTime() + (att.getMaxLateFSOut() * 1000 * 60);
			long minOutOT = day.getFSTimeOut().getTime() - (att.getMaxEarLierFSOut() * 1000 * 60);
			MUNSSlotType st = util.getSlotType();
			if(util.isOverTime())
			{
				long startOT = util.getStartOverTime().getTime();
				long endOT = util.getEndOverTime().getTime();
				
				double maxOutOTHours = (double) maxOutOT /1000/60/60;
				double minOutOTHours = (double) minOutOT /1000/60/60;
				double maxInOTHours = (double) maxInOT /1000/60/60;
				double minInOTHours = (double) minInOT /1000/60/60;
				double endOTHours = (double) endOT /1000/60/60;
				double startOTHours = (double) startOT /1000/60/60;
				
				if(endOTHours < maxOutOTHours && endOTHours > minOutOTHours)
				{
					double diff = 0;
					if(util.getEndOverTime().compareTo(day.getFSTimeOut()) == 1)
					{
						diff = util.getEndOverTime().getTime() - day.getFSTimeOut().getTime();
						diff = diff /1000/60/60;
					}
						maxOTHours = util.getMaxOTHours() - diff;
				}
				else if(startOTHours < maxInOTHours && startOTHours > minInOTHours)
				{
					double diff = 0;
					if(util.getStartOverTime().compareTo(day.getFSTimeIn()) == -1)
					{
						diff = util.getEndOverTime().getTime() - day.getFSTimeOut().getTime();
						diff = diff /1000/60/60;
					}
						maxOTHours = util.getMaxOTHours() - diff;
				}
			}
			
			if(st.isAutoAddOvertime())
				addWorkHours = hours - util.getMaxWorkHours() - util.getBreakTimeOnOT();
			else
				addWorkHours = maxOTHours - util.getBreakTimeOnOT();
			
			if(addWorkHours > 0)
				day.setOvertime(BigDecimal.valueOf(addWorkHours));
			else
				day.setOvertime(Env.ZERO);
			
			if (day.getPresenceStatus().equals(MUNSDailyPresence.PRESENCESTATUS_Lembur))
				day.setOvertime(new BigDecimal(hours).setScale(2, 
						RoundingMode.HALF_DOWN));
			else
				day.setWorkHours(new BigDecimal(hours).setScale(2, 
						RoundingMode.HALF_DOWN));
//			day.setAddWorkHours((int) hours - (int) util.getNormalWorkHours() - day.getOvertime().intValue());
		}
		
		if (!MUNSDailyPresence.DAYTYPE_HariKerjaBiasa.equals(day.getDayType()) 
				&& MUNSDailyPresence.PRESENCESTATUS_Lembur.equals(presenceStatus)
				&& (day.getOvertime().signum() > 0))
			{
				BigDecimal overTime = day.getOvertime();
				if (overTime.doubleValue() > util.getMaxOTHours())
					overTime = BigDecimal.valueOf(util.getMaxOTHours());
				BigDecimal maxLD1 = BigDecimal.valueOf(
						payConfiguration.getMaxLD1());
				BigDecimal maxLD2 = BigDecimal.valueOf
						(payConfiguration.getMaxLD2());
				
				if (overTime.compareTo(maxLD1) <= 0)
				{
					day.setLD1(overTime);
				}
				else
				{
					day.setLD1(maxLD1);
					overTime = overTime.subtract(maxLD1);
					if (overTime.compareTo(maxLD2) <= 0)
					{
						day.setLD2(overTime);
						day.setLD3(Env.ZERO);
					}
					else
					{
						day.setLD2(maxLD2);
						overTime = overTime.subtract(maxLD2);
						day.setLD3(overTime);
					}
				}
			}
		
		return null;
	}
	
	public String validateDuplicateEmployee(PO po)
	{
		if(po.get_ValueAsString("NextContractType").equals("CN1")
				|| po.get_ValueAsInt("NextContractNumber") == 1
					|| po.get_ValueAsInt("PrevDept_ID") <= 0)
		{
			MUNSEmployee emp = MUNSEmployee.getBaseNameBirthDay(po.getCtx(), po.get_ValueAsString("Name"),
					(Timestamp) po.get_Value("DateOfBirth"), po.get_TrxName());
			if(null != emp)
			{
				int retVal = MessageBox.showMsg(po.getCtx(), po.getProcessInfo()
						, "Notification duplicated employee information. \n" +
						  "Name and date of birth has been used at nip " + emp.getContractNumber() +
						  " /n Do you want to continue this process?"
						, "Duplicate Confirmation"
						, MessageBox.YESNO
						, MessageBox.ICONQUESTION);
				if(retVal == MessageBox.RETURN_YES)
				{
					return null;
				}
				else
				{
					return "transaction has cancelled";
				}
			}
		}
		return "";
	}
	
	private String upDailyPresence(MUNSOTConfirmation confirm)
	{
		MUNSEmployee[] emps = MUNSOTRequest.getEmployees(confirm.getCtx(), 
									confirm.getUNS_OTRequest_ID(), confirm.get_TrxName());
		
		int tolerance = MSysConfig.getIntValue(MSysConfig.TOLERANCE_OF_DIFFERENCE_TIME, 30);
		
		for(int i = 0; i < emps.length; i++)
		{
			HRMUtils util = new HRMUtils(emps[i], 
											confirm.getDateDoOT());
			String msg = util.init(true);
			if(!Util.isEmpty(msg, true))
				return msg;
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(confirm.getStartTime());
			cal.add(Calendar.MINUTE, -tolerance);
			String whereClause = "(CheckTime BETWEEN ? AND ?) AND"
					+ " AttendanceName = ?";
			ArrayList<Object> params = new ArrayList<>();
			params.add(new Timestamp (cal.getTimeInMillis()));
			cal.add(Calendar.MINUTE, tolerance);
			cal.add(Calendar.MINUTE, tolerance);
			params.add(new Timestamp (cal.getTimeInMillis()));
			params.add(emps[i].getAttendanceName());
			
			MUNSCheckInOut[] ioLM =
					MUNSCheckInOut.gets(confirm.getCtx(), confirm.get_TrxName(), 
							whereClause, params, MUNSCheckInOut.COLUMNNAME_CheckTime);
			Timestamp in = null;
			if(ioLM.length > 0)
			{
				ioLM[0].setCheckType(X_UNS_CheckInOut.CHECKTYPE_LemburMasuk);
				if(ioLM[0].getCheckTime().after(confirm.getStartTime()))
						in = ioLM[0].getCheckTime();
				ioLM[0].setPresenceDate(in);
				ioLM[0].saveEx();
			}
			if(in == null)
				in = confirm.getStartTime();
			
			params = new ArrayList<>();
			cal.setTime(confirm.getEndTime());
			cal.add(Calendar.MINUTE, -tolerance);
			params.add(new Timestamp (cal.getTimeInMillis()));
			cal.add(Calendar.MINUTE, tolerance);
			cal.add(Calendar.MINUTE, tolerance);
			params.add(new Timestamp (cal.getTimeInMillis()));
			params.add(emps[i].getAttendanceName());
			
			MUNSCheckInOut[] ioLK =
					MUNSCheckInOut.gets(confirm.getCtx(), confirm.get_TrxName(), 
							whereClause, params, MUNSCheckInOut.COLUMNNAME_CheckTime + " DESC");
			
			Timestamp out = null;
			if(ioLK.length > 0)
			{
				ioLK[0].setCheckType(X_UNS_CheckInOut.CHECKTYPE_LemburKeluar);
				if(ioLK[0].getCheckTime().before(confirm.getEndTime()))
					out = ioLK[0].getCheckTime();
				ioLK[0].setPresenceDate(out);
				ioLK[0].saveEx();
			}
			if(out == null)
				out = confirm.getEndTime();
			
			if((ioLK.length > 0 || ioLM.length > 0))
			{
				MUNSDailyPresence day = MUNSDailyPresence.getCreate(confirm.getDateDoOT(), 
												emps[i]);
				if(day != null)
				{
					long start = in.getTime();
					long end = out.getTime();
					double range = (double) end - (double) start;
					range = range / 1000 / 60 / 60;
					day.setIsNeedAdjustRule(true);
					day.setOvertime(day.getOvertime().add(BigDecimal.valueOf(range)));
					day.saveEx();
				}
				if(ioLK.length > 0)
				{
					ioLK[0].setUNS_DailyPresence_ID(day.get_ID());
					ioLK[0].setUNS_MonthlyPresenceVal_ID(-1);
					ioLK[0].setPresenceDate(in);
					ioLK[0].saveEx();
				}
				if(ioLM.length > 0)
				{
					ioLM[0].setUNS_DailyPresence_ID(day.get_ID());
					ioLM[0].setUNS_MonthlyPresenceVal_ID(-1);
					ioLM[0].setPresenceDate(out);
					ioLM[0].saveEx();
				}
			}
		}
		
		return null;
	}
	
	private String createDaily(MUNSCheckInOut io, int type)
	{
		if(type != TYPE_AFTER_NEW && !io.is_ValueChanged("AttendanceName")
				&& !io.is_ValueChanged("CheckTime"))		
			return null;
		
		MUNSEmployee emp = MUNSEmployee.getByAttName(io.get_TrxName(), io.getAttendanceName());
		if (emp == null) {
			io.setIsLinkedToEmployee(false);
			if (!io.save())
				return "could not update check in out";
			return null;
		}
		//check employee is having contract or not
		String sql = "SELECT 1 FROM UNS_Contract_Recommendation WHERE UNS_Employee_ID = ? AND TRUNC (?::TIMESTAMP) "
				+ " BETWEEN DateContractStart AND DateContractEnd";
		int checkNum = DB.getSQLValue(io.get_TrxName(), sql, emp.getUNS_Employee_ID(), io.getCheckTime());
		if (checkNum != 1) {
			io.setIsLinkedToEmployee(false);
			if (!io.save())
				return "could not update check in out";
			return null;
		}
		
		HRMUtils util = new HRMUtils(emp, io.getCheckTime());
		String msg = util.init(true);
		
		if(!Util.isEmpty(msg, true))
		{
			io.saveEx();
			return null;
		}
		
//		if(util.getPresenceDate() != null)
//		{
			MUNSDailyPresence day = MUNSDailyPresence.getByDate(io.getCtx(), 
					util.getPresenceDate() != null ? util.getPresenceDate()
					: io.getCheckTime(), emp.get_ID(), io.get_TrxName());
			if(day == null)
			{
				day = new MUNSDailyPresence(util.getPresenceDate() != null ?  util.getPresenceDate() 
						: io.getCheckTime(), emp);
				day.saveEx();
			}
			io.setUNS_DailyPresence_ID(day.get_ID());
			io.setIsLinkedToEmployee(true);
			if (!io.save())
				return "Could not update check in out";
//		}
		
		return null;
	}
	
	private String upDailyPresenceOnVoid(MUNSOTConfirmation confirm)
	{
		MUNSEmployee[] emps = MUNSOTRequest.getEmployees(confirm.getCtx(), 
				confirm.getUNS_OTRequest_ID(), confirm.get_TrxName());

		for(int i = 0; i < emps.length; i++)
		{
			MUNSDailyPresence day = MUNSDailyPresence.getByDate(confirm.getCtx(), 
					confirm.getDateDoOT(), emps[i].get_ID(), confirm.get_TrxName());
			//reset daily
			if(day != null)
			{
				day.setTimeInRules(null);
				day.setTimeOutRules(null);
				day.setMinTimeInRule(null);
				day.setMaxTimeOutRule(null);
				day.setOvertime(Env.ZERO);
				day.setBelatedDuration(0);
				if(!day.save())
					return "Could not update daily presence.";
			}
		}
		
		return null;
	}
}