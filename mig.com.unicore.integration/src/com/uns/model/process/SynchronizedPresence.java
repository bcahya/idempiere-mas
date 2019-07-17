/**
 * 
 */
package com.uns.model.process;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Util;

import com.uns.model.MUNSCheckInOut;
import com.uns.model.MUNSDailyPresence;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSMonthlyPresenceSummary;
import com.uns.model.MUNSMonthlyPresenceVal;
import com.uns.model.utilities.HRMUtils;

/**
 * @author Menjangan
 *
 */
public class SynchronizedPresence extends SvrProcess 
{
	
	private Properties m_ctx = null;
	private String m_trxName = null;
	private Hashtable<String, Object> m_cache = null;
	private final String EMPLOYEE = "EMPLOYEE";
	private boolean isManual = false;
	private MUNSMonthlyPresenceSummary m_Summary = null;
	private Timestamp m_startDate = null;
	private Timestamp m_endDate = null;
	
	public SynchronizedPresence() 
	{
		super ();
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		m_cache = new Hashtable<String, Object>();
		ProcessInfoParameter[] params = getParameter();
		for(ProcessInfoParameter param : params)
		{
			if(param.getParameterName() == null)
				;
			else if(param.getParameterName().equals("isManual"))
				isManual = param.getParameterAsBoolean();
			else if(param.getParameterName().equals("StartDate"))
				m_startDate = param.getParameterAsTimestamp();
			else if(param.getParameterName().equals("EndDate"))
				m_endDate = param.getParameterAsTimestamp();
		}
		
		if(isManual)
		{
			if(getRecord_ID() <= 0)
				throw new AdempiereException("Not found summary");
			else
				m_Summary = new MUNSMonthlyPresenceSummary(getCtx(), getRecord_ID(), get_TrxName());
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		String wc = "UNS_DailyPresence_ID IS NULL AND (UNS_MonthlyPresenceVal_ID IS NULL "
				+ " OR EXISTS (SELECT UNS_MonthlyPresenceVal_ID FROM UNS_MonthlyPresenceVal "
				+ " WHERE DocStatus IN ('CO','CL') AND UNS_MonthlyPresenceVal_ID = "
				+ " UNS_CheckInOut.UNS_MonthlyPresenceVal_ID)) AND IsActive = 'Y' AND EXISTS ("
				+ " SELECT UNS_Employee_ID FROM UNS_Employee WHERE AttendanceName "
				+ " = UNS_CheckInOut.AttendanceName AND EXISTS (SELECT UNS_Contract_Recommendation_ID "
				+ " FROM UNS_Contract_Recommendation WHERE UNS_Employee_ID = UNS_Employee.UNS_Employee_ID "
				+ " AND TRUNC(CAST (UNS_CheckInOut.CheckTime  AS DATE)) BETWEEN DateContractStart AND "
				+ " DateContractEnd)) AND IsActive = 'Y' AND IsPostpone = 'N'";
		
		if(isManual)
		{
			wc += " AND AttendanceName " + m_Summary.getUNS_Employee().getAttendanceName()
					+ " AND (TRUNC(CAST (UNS_CheckInOut.CheckTime AS DATE)) BETWEEN " + m_Summary.getStartDate()
					+ "' AND " + m_Summary.getEndDate() + "')";
		}
		
		if(m_startDate != null)
			wc += " AND TRUNC(CAST (UNS_CheckInOut.CheckTime AS DATE)) >= '" + m_startDate + "'";
		
		if(m_endDate != null)
			wc += " AND TRUNC(CAST (UNS_CheckInOut.CheckTime AS DATE)) <= '" + m_endDate + "'";
		
		MUNSCheckInOut[] inOuts = MUNSCheckInOut.gets(
				getCtx(), get_TrxName(), wc, 
				null, MUNSCheckInOut.COLUMNNAME_CheckTime + " DESC");
		
		log.info(inOuts.length + " total check In Out will be processed ");
		int errorCounter = inOuts.length;
		int successCounter = 0;
		StringBuilder errorBuilder = new StringBuilder();
		for (int i=0; i<inOuts.length; i++)
		{
			String msg = processCheckInOut(inOuts[i]);
			if (null != msg)
			{
				errorBuilder.append("\n").append(msg);
				log.log(Level.WARNING, msg);
				continue;
			}
			
			successCounter++;
			errorCounter--;
		}
		
		String errorMsg = errorBuilder.toString();
		errorMsg = successCounter + " Success & " + errorCounter + " failed. "
				+ errorMsg ;
		
		return errorMsg;
	}
	
	private String processCheckInOut (MUNSCheckInOut inOut)
	{
		MUNSEmployee employee = getEmployee(inOut.getAttendanceName());
		if (null == employee)
			return "Could not find employee with attendance name "
					+ inOut.getAttendanceName();		
		return doValidateCheck(inOut, employee);
	}
	
	/**
	 * 
	 * @param inOut
	 * @param employee
	 * @return
	 */
	private String doValidateCheck (MUNSCheckInOut inOut, MUNSEmployee employee)
	{
		HRMUtils util = new HRMUtils(employee, inOut.getCheckTime());
		String msg  = util.init(true);
		if (!Util.isEmpty(msg, true))
			return msg;
		if(!isManual && !util.isAutoSynchronize())
		{
			inOut.setisPostpone(true);
			inOut.saveEx();
			return null;
		}
		Timestamp presenceDate = util.getPresenceDate();
		if (null == presenceDate)
		{
			MUNSMonthlyPresenceVal validation = inOut.getPresenceValidation();
			if (null == validation)
			{
				validation = MUNSMonthlyPresenceVal.createValidation(
						inOut, "CheckTime", util.getStartDate(), 
						util.getEndDate(), employee.getC_BPartner_ID(), 
						util.getC_Period().get_ID());
			}
			if (null == validation)
				return CLogger.retrieveErrorString("Could not create presence validation");
			if (!DocAction.STATUS_Completed.equals(validation.getDocStatus())
					&& !DocAction.STATUS_Closed.equals(validation.getDocStatus()))
			{
				return "Ignored [" + inOut.toString() +"]. Please complete"
						+ " Presence Validation first " 
						+ validation.getDocumentNo();
			}
			presenceDate = (Timestamp) inOut.get_Value("PresenceDate");
		}
		
		return linkedToDaily(presenceDate, employee, util, inOut);
	}
	
	@Override
	public Properties getCtx ()
	{
		if (null == m_ctx)
		{
			m_ctx = super.getCtx();
		}
		
		return m_ctx;
	}
	
	@Override
	public String get_TrxName ()
	{
		if (null == m_trxName)
		{
			m_trxName = super.get_TrxName();
		}
		
		return m_trxName;
	}
	
	@SuppressWarnings("unchecked")
	private MUNSEmployee getEmployee (String attName)
	{
		MUNSEmployee employee = null;
		List<ValueMapping> list = (List<ValueMapping>) m_cache.get(EMPLOYEE);
		
		if (null == list)
		{
			list = new ArrayList<ValueMapping>();
			m_cache.put(EMPLOYEE, list);
		}
		
		for (int i=0; i<list.size(); i++)
		{
			if (list.get(i).getKey().equals(attName))
			{
				employee = (MUNSEmployee) list.get(i).getValue();
				break;
			}
		}
		
		if (null != employee)
		{
			return employee;
		}
		
		employee = MUNSEmployee.getByAttName(
				get_TrxName(), attName);
		
		if (null != employee)
		{
			ValueMapping mapping = new ValueMapping(attName, employee);
			list.add(mapping);
		}	
		
		return employee;
	}
	
	/**
	 * 
	 * @param presenceDate
	 * @param employee
	 * @param util
	 * @param inOut
	 * @return
	 */
	private String linkedToDaily (Timestamp presenceDate, MUNSEmployee employee, 
			HRMUtils util, MUNSCheckInOut inOut)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(presenceDate.getTime());
		String presenceStatus = MUNSDailyPresence.PRESENCESTATUS_FullDay;
		String dayType = MUNSDailyPresence.DAYTYPE_HariKerjaBiasa;
		if (util.isNationalHoliday())
		{
			dayType = MUNSDailyPresence.DAYTYPE_HariLiburNasional;
		}
		else if (util.isWeeklyHoliday())
		{
			dayType = MUNSDailyPresence.DAYTYPE_HariLiburMingguan;
		}
		if (util.isOverTime() && (util.isWeeklyHoliday() 
				|| util.isNationalHoliday()))
		{
			presenceStatus = MUNSDailyPresence.PRESENCESTATUS_Lembur;
		}
		
		try
		{
			MUNSDailyPresence presence = MUNSDailyPresence.getByDate(
					getCtx(), presenceDate, employee.get_ID(), get_TrxName());
			if (null == presence)
			{
				presence = new MUNSDailyPresence(presenceDate, employee);
			}
			
			String sql = "SELECT DocStatus FROM " + MUNSMonthlyPresenceSummary
					.Table_Name	+ " WHERE " + MUNSMonthlyPresenceSummary
					.COLUMNNAME_UNS_MonthlyPresenceSummary_ID + " = ? ";
			
			String monthlyDocStatus = DB.getSQLValueString(
					get_TrxName(), sql, 
					presence.getUNS_MonthlyPresenceSummary_ID());
			
			if (MUNSMonthlyPresenceSummary.DOCSTATUS_Completed.
					equals(monthlyDocStatus))
			{
				String errorMsg = "The monthly presence of day record [" 
							+ presence.toString() + "] is completed.";
				log.log(Level.WARNING, errorMsg);
				
				return errorMsg;
			}
			
			Timestamp timeIn = util.getTimeInRules();
			Timestamp maxFSIn = util.getMaxLateTimeInRules();
			Timestamp timeOut = util.getTimeOutRules();
			Timestamp minFSOut = util.getMaxEarlierTimeOutRules();
			long maxStart = maxFSIn.getTime();
			long minEnd = minFSOut.getTime();
			long check = util.getDate().getTime();
			
			if (maxStart >= check)
			{
				Timestamp lastIn = presence.getFSTimeIn();
				if (null == lastIn)
				{
					lastIn = util.getDate();
				}
				else if (timeIn.after(lastIn))
				{
					lastIn = util.getDate();
				}
				
				presence.setFSTimeIn(lastIn);
			}
			else if (minEnd <= check)
			{
				Timestamp lastOut = presence.getFSTimeOut();
				if (null == lastOut)
				{
					lastOut = util.getDate();
				}
				else if (util.getDate().after(lastOut))
				{
					lastOut = util.getDate();
				}
				
				presence.setFSTimeOut(lastOut);
			}
			
			presence.setTimeInRules(timeIn);
			presence.setTimeOutRules(timeOut);
			presence.setPresenceStatus(presenceStatus);
			presence.setDayType(dayType);
			presence.setDay(util.getDay());
			if (inOut.getAD_Org_ID() != presence.getAD_Org_ID())
			{
				inOut.setAD_Org_ID(presence.getAD_Org_ID());
			}
//			presence.setAddWorkHours((int) util.getAddWorkHours() + (int) util.getMaxOTHours());
		
			presence.saveEx();
			inOut.setUNS_DailyPresence_ID(presence.get_ID());
			inOut.saveEx();
		}
		catch (Exception ex)
		{
			return ex.getMessage();
		}
		
		return null;
	}
	
//	private String checkOverTime(MUNSCheckInOut io)
//	{
//		int tolerance = MSysConfig.getIntValue(MSysConfig.TOLERANCE_OF_DIFFERENCE_TIME, 30);
//		Timestamp time = io.getCheckTime();
//		String whereClause = "DocStatus IN ('CO', 'CL') AND UNS_Employee_ID = (SELECT"
//				+ " UNS_Employee_ID FROM UNS_Employee WHERE AttendanceName = ?) AND StartTime BETWEEN "
//				+ time.toString() + " - INTERVAL '"+ tolerance + " MINUTES' "
//				+ "AND " + time.toString() + " + INTERVAL '"+ tolerance + " MINUTES' ";
//		ArrayList<Object> params = new ArrayList<>();
//		params.add(io.getAttendanceName());
//		MUNSOTConfirmation[] confirm = MUNSOTConfirmation.gets(getCtx(), whereClause, params, get_TrxName());
//		
//		if(confirm.length > 0)
//		{
//			io.setPresenceDate(time);
//			io.setCheckType(X_UNS_CheckInOut.CHECKTYPE_LemburMasuk);
//			io.saveEx();
//		}
//		else
//		{
//			whereClause = "DocStatus IN ('CO', 'CL') AND UNS_Employee_ID = (SELECT"
//					+ " UNS_Employee_ID FROM UNS_Employee WHERE AttendanceName = ?) AND EndTime BETWEEN "
//					+ time.toString() + " - INTERVAL '"+ tolerance + " MINUTES' "
//					+ "AND " + time.toString() + " + INTERVAL '"+ tolerance + " MINUTES' ";
//			confirm = MUNSOTConfirmation.gets(getCtx(), whereClause, params, get_TrxName());
//			if(confirm.length > 0)
//			{
//				io.setPresenceDate(time);
//				io.setCheckType(X_UNS_CheckInOut.CHECKTYPE_LemburKeluar);
//				io.saveEx();
//			}
//			else
//			{
//				return "Not found over time for this check";
//			}
//		}
//		
//		return null;
//	}
}

class ValueMapping
{
	private Object m_key = null;
	private Object m_value = null;
	
	public ValueMapping (Object key, Object value)
	{
		m_key = key;
		m_value = value;
	}
	
	public Object getKey ()
	{
		return m_key;
	}
	
	public Object getValue ()
	{
		return m_value;
	}
}