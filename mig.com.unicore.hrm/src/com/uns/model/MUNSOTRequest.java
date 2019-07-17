/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author Toshiba
 * @see UNSHRMValidator {Validate date of OT Request On beforeSave & Prepare}
 */
public class MUNSOTRequest extends X_UNS_OTRequest implements DocAction,
		DocOptions 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 511195174092390564L;
	String m_processMsg = null;
	private boolean m_justPrepared = false;
	private boolean m_force = false;
	
	/**
	 * @param ctx
	 * @param UNS_OTRequest_ID
	 * @param trxName
	 */
	public MUNSOTRequest(Properties ctx, int UNS_OTRequest_ID, String trxName) 
	{
		super(ctx, UNS_OTRequest_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSOTRequest(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocOptions#customizeValidActions(
	 * java.lang.String, java.lang.Object, java.lang.String, java.lang.String,
	 * int, java.lang.String[], java.lang.String[], int)
	 */
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) 
	{
		if (docStatus.equals(DocAction.STATUS_Drafted))
		{
			options[index++] = DocAction.ACTION_Prepare;
		}
		
		if (docStatus.equals(DocAction.STATUS_Completed))
		{
			options[index++] = DocAction.ACTION_Void;
		}
		
		return index;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception 
	{
		doLog(Level.INFO, "Processing Document.");
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() 
	{
		doLog(Level.INFO, "Unlock Document");
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() 
	{
		doLog(Level.INFO, "Invalidate Document");
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() 
	{
		doLog(Level.INFO, "Prepare Document");
		
		//run UNSHRMValidator
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return DOCSTATUS_Invalid;
		}
		
//		m_processMsg = checkAddWorkHours();
//		if(m_processMsg != null)
//		{
//			return DOCSTATUS_Invalid;
//		}
		
		if(!checkMonthlyEmployee())
			return DOCSTATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return DOCSTATUS_Invalid;
		}
		
		setProcessed(true);
		setDocAction(DOCACTION_Complete);
		return DOCSTATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() 
	{
		doLog(Level.INFO, "Approve Document");
		setProcessed(true);
		setIsApproved(true);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() 
	{
		doLog(Level.INFO, "Reject Document");
		setIsApproved(false);
		setProcessed(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() 
	{
		doLog(Level.INFO, "Complete Document");
		
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
			return status;
		}
		
		if(getUNS_Employee_ID() > 0 && getLine().length > 0)
		{
			m_processMsg = "Please remove (resource or employee) selection or remove employee in header.";
			return DOCSTATUS_Invalid;
		}
		else if(getUNS_Employee_ID() <= 0 && getLine().length <= 0)
		{
			m_processMsg = "Please define employee in header or use selection (resource or employee)"
					+ " for definition over time";
			return DOCSTATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return DOCSTATUS_Invalid;
		}
		
		MUNSOTConfirmation confirmation = MUNSOTConfirmation.getCreate(this);
		if (null == confirmation)
		{
			m_processMsg = "Failed when trying to create confirmation.";
			return DOCSTATUS_Invalid;
		}
		
		if (!confirmation.isComplete() && !isForce())
		{
			m_processMsg = "Please complete Over Time Confirmation " 
					+ confirmation.getDocumentNo() + " before do next process.";
			return DOCSTATUS_InProgress;
		}
		
		if(!upAdjustRuleDaily())
		{
			m_processMsg = "Failed when trying update need adjust rule on daily presence";
			return DOCSTATUS_Invalid;
		}

		if (!isApproved())
		{
			approveIt();
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
		{
			return DOCSTATUS_Invalid;
		}
		
		setDocAction(DOCACTION_Close);
		return DOCSTATUS_Completed;
	}
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() 
	{
		doLog(Level.INFO, "Void Document");
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
		{
			return false;
		}
		
		MUNSOTConfirmation confirm = MUNSOTConfirmation.getCreate(this);
		if (confirm != null 
				&& !confirm.getDocStatus().equals(DOCSTATUS_Drafted)
				&& !confirm.getDocStatus().equals(DOCSTATUS_Invalid)
				&& !isForce())
		{
			m_processMsg = "Please void/reverse Confirmation first.";
		}
		else if (confirm != null) {
			confirm.setDocStatus(DOCSTATUS_Voided);
			confirm.setProcessed(true);
			confirm.saveEx();
		}
		
		if(!upAdjustRuleDaily())
		{
			m_processMsg = "Failed when trying update need adjust rule on daily presence";
			return false;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
		{
			return false;
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() 
	{
		doLog(Level.INFO, "Close Document");
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}
		
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}
		
		setDocAction(DOCACTION_None);
		setDocStatus(DOCSTATUS_Closed);
		setProcessed(true);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() 
	{
		doLog(Level.INFO, "Reverse Correct-It");
		return voidIt();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() 
	{
		doLog(Level.INFO, "Reverse AccrualIt");
		return voidIt();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() 
	{
		m_processMsg = "Disallowed action Reactivate-It";
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() 
	{
		StringBuilder sb = new StringBuilder("Over Time Request No ")
		.append(getDocumentNo());
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() 
	{
		return getSummary();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() 
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() 
	{
		return m_processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() 
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() 
	{
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() 
	{
		return null;
	}
	
	@Override
	public boolean beforeSave (boolean newRecord)
	{
		if (newRecord || is_ValueChanged(COLUMNNAME_StartTime)
				|| is_ValueChanged(COLUMNNAME_EndTime) || is_ValueChanged(COLUMNNAME_BreakTime))
		{
			long start = getStartTime().getTime();
			long end = getEndTime().getTime();
			double range = (double) end - (double) start;
			range = range / 1000 / 60 / 60;
			BigDecimal value = new BigDecimal(range);
			value = value.subtract(getBreakTime());
			setRequestedHours(value);
		}
		
		String sql = "SELECT hc.UNS_SlotType_ID FROM UNS_WorkHoursConfig hc"
				+ " INNER JOIN UNS_WorkHoursConfig_Line cl ON cl.UNS_WorkHoursConfig_ID"
				+ " = hc.UNS_WorkHoursConfig_ID"
				+ " WHERE (? BETWEEN hc.ValidFrom AND hc.ValidTo)"
				+ " AND hc.DocStatus IN ('CO', 'CL') AND"
				+ " (CASE WHEN cl.UNS_Resource_ID > 0 THEN cl.UNS_Resource_ID ="
				+ " (SELECT UNS_Resource_ID FROM UNS_Resource_WorkerLine WHERE Labor_ID = ?)"
				+ " ELSE cl.UNS_Employee_ID = ? END)";
		int slotType = DB.getSQLValue(get_TrxName(), sql, 
							new Object[]{getDateDoOT(), getUNS_Employee_ID(), getUNS_Employee_ID()});
		if(slotType <= 0)
		{
			sql = "SELECT r.UNS_SlotType_ID FROM UNS_Resource r"
					+ " INNER JOIN UNS_Resource_WorkerLine wl ON wl.UNS_Resource_ID = r.UNS_Resource_ID"
					+ " WHERE wl.Labor_ID = ?";
			slotType = DB.getSQLValue(get_TrxName(), sql, getUNS_Employee_ID());
		}
		
		if(slotType > 0)
			setUNS_SlotType_ID(slotType);
		
		return super.beforeSave(newRecord);
	}
	
	protected boolean beforeDelete()
	{
		String sql = "DELETE FROM UNS_OTLine WHERE UNS_OTRequest_ID = ?";
		return DB.executeUpdate(sql, get_ID(), get_TrxName()) >= 0;
	}
	
	private void doLog (Level level, String msg)
	{
		if (!log.isLoggable(level))
		{
			return;
		}
		
		log.log(level, msg);
	}
	
	public void setForce(boolean force)
	{
		m_force = force;
	}
	
	public boolean isForce ()
	{
		return m_force;
	}
	
	public static MUNSOTRequest get (String trxName, String whereClause, 
			List<Object> params)
	{
		Query q = Query.get(Env.getCtx(), UNSHRMModelFactory.EXTENSION_ID, 
				Table_Name, whereClause, trxName);
		if (null != params)
		{
			q.setParameters(params);
		}
		
		MUNSOTRequest req = q.first();
		
		return req;
	}
	
	public static MUNSOTRequest getValidof (
			String trxName, Timestamp date, int UNS_Employee_ID)
	{
		String wc = "UNS_OTRequest.DocStatus IN (?,?) AND UNS_OTRequest.DateDoOT = TRUNC (CAST "
				+ " (? AS DATE)) AND (UNS_OTRequest.UNS_Employee_ID = ?"
				+ " OR EXISTS (SELECT 1 FROM UNS_OTLine l WHERE"
				+ " l.UNS_OTRequest_ID = UNS_OTRequest.UNS_OTRequest_ID AND (l.UNS_Employee_ID = ?"
				+ " OR l.UNS_Resource_ID = (SELECT wl.UNS_Resource_ID FROM UNS_Resource_WorkerLine wl"
				+ " WHERE wl.Labor_ID = ?))))";
		List<Object> params = new ArrayList<Object>();
		params.add("CO");
		params.add("CL");
		params.add(date);
		params.add(UNS_Employee_ID);
		params.add(UNS_Employee_ID);
		params.add(UNS_Employee_ID);
		return get(trxName, wc, params);
	}
	
	public MUNSOTLine[] getLine()
	{
		List<MUNSOTLine> lines = new Query(getCtx(), MUNSOTLine.Table_Name, 
				COLUMNNAME_UNS_OTRequest_ID + "=?", get_TrxName()).setOnlyActiveRecords(true)
					.setParameters(get_ID()).list();
		
		return lines.toArray(new MUNSOTLine[lines.size()]);
	}
	
	public static MUNSEmployee[] getEmployees(Properties ctx, int OTRequest_ID, String trxName)
	{
		List<MUNSEmployee> employees = new ArrayList<>();
		
		String sql = "SELECT emp.* FROM UNS_Employee emp"
				+ " WHERE emp.UNS_Employee_ID = (SELECT rq.UNS_Employee_ID FROM "
				+ " UNS_OTRequest rq WHERE rq.UNS_OTRequest_ID = ?) OR "
				+ " EXISTS (SELECT 1 FROM UNS_OTLine l WHERE"
				+ " l.UNS_OTRequest_ID = ? AND (l.UNS_Employee_ID = emp.UNS_Employee_ID"
				+ " OR EXISTS (SELECT 1 FROM UNS_Resource_WorkerLine wl"
				+ " WHERE wl.Labor_ID = emp.UNS_Employee_ID AND wl.UNS_Resource_ID = l.UNS_Resource_ID)))";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			stmt = DB.prepareStatement(sql, trxName);
			stmt.setInt(1, OTRequest_ID);
			stmt.setInt(2, OTRequest_ID);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				employees.add(new MUNSEmployee(ctx, rs, trxName));
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		return employees.toArray(new MUNSEmployee[employees.size()]);
	}
	
	public boolean checkMonthlyEmployee()
	{
		MUNSEmployee[] emps = getEmployees(getCtx(), get_ID(), get_TrxName());
		
		for(int i=0;i<emps.length;i++)
		{
			MUNSMonthlyPresenceSummary monthly = MUNSMonthlyPresenceSummary.get(
					getCtx(), emps[i].get_ID(), getDateDoOT(), getAD_Org_ID(), get_TrxName());
			if(monthly != null && (monthly.getDocStatus().equals("CO")
					|| monthly.getDocStatus().equals("CL")))
			{
				m_processMsg = "Monthly for employee " + emps[i].getName() 
						+ " has completed, cannot running this action.";
				return false;
			}
		}
		
		return true;
	}
	
	public boolean upAdjustRuleDaily()
	{
		String sql = "UPDATE UNS_DailyPresence dp SET IsNeedAdjustRule = 'Y' WHERE"
				+ " dp.PresenceDate = (SELECT rq.DateDoOT FROM"
				+ " UNS_OTRequest rq WHERE rq.UNS_OTRequest_ID = ?)"
				+ " AND EXISTS (SELECT 1 FROM UNS_MonthlyPresenceSummary"
				+ " mps WHERE mps.UNS_MonthlyPresenceSummary_ID ="
				+ " dp.UNS_MonthlyPresenceSummary_ID AND (mps.UNS_Employee_ID"
				+ " = (SELECT COALESCE(rq.UNS_Employee_ID,0) FROM"
				+ " UNS_OTRequest rq WHERE rq.UNS_OTRequest_ID = ?)"
				+ " OR EXISTS (SELECT 1 FROM UNS_OTLine l WHERE"
				+ " l.UNS_OTRequest_ID = ? AND (l.UNS_Employee_ID"
				+ " = mps.UNS_Employee_ID OR EXISTS (SELECT 1 FROM"
				+ " UNS_Resource_WorkerLine wl WHERE wl.Labor_ID ="
				+ " mps.UNS_Employee_ID AND wl.UNS_Resource_ID = l.UNS_Resource_ID)))))";
		
		return DB.executeUpdate(sql, new Object[]{get_ID(), get_ID(), get_ID()}, false, get_TrxName()) >= 0;
	}
	
//	private String checkAddWorkHours()
//	{
//		String msg = null;
//		int add = MUNSAddWorkHours.getAddWorkHours(get_TrxName(), getUNS_Employee_ID(), getStartTime());
//		int earlier = MUNSAddWorkHours.getEarlierAddWorkHours(get_TrxName(), getUNS_Employee_ID(), getEndTime());
//		int defaultHourIn = MSysConfig.getIntValue(
//				MSysConfig.DEFAULT_ATT_HOUR_IN, 8, getUNS_Employee().getAD_Client_ID(), 
//				getUNS_Employee().getAD_Org_ID());
//		int defaultHourOut = MSysConfig.getIntValue(
//				MSysConfig.DEFAULT_ATT_HOUR_OUT, 17, 
//				getUNS_Employee().getAD_Client_ID(), getUNS_Employee().getAD_Org_ID());
//		int defaultMinuteIn = MSysConfig.getIntValue(
//				MSysConfig.DEFAULT_ATT_MINUTE_IN, 0,
//				getUNS_Employee().getAD_Client_ID(), getUNS_Employee().getAD_Org_ID());
//		int defaultMinuteOut = MSysConfig.getIntValue(
//				MSysConfig.DEFAULT_ATT_MINUTE_OUT, 0,
//				getUNS_Employee().getAD_Client_ID(), getUNS_Employee().getAD_Org_ID());
//		Timestamp minTimeIn = null;
//		Timestamp maxTimeOut = null;
//		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(getStartTime());
//		cal.set(Calendar.HOUR_OF_DAY, defaultHourIn);
//		cal.set(Calendar.MINUTE, defaultMinuteIn);
//		cal.set(Calendar.SECOND, 0);
//		cal.add(Calendar.HOUR_OF_DAY, -earlier);
//		minTimeIn = new Timestamp(cal.getTimeInMillis());
//		
//		cal.setTime(getEndTime());
//		cal.set(Calendar.HOUR_OF_DAY, defaultHourOut);
//		cal.set(Calendar.MINUTE, defaultMinuteOut);
//		cal.set(Calendar.SECOND, 0);
//		cal.add(Calendar.HOUR_OF_DAY, add);
//		maxTimeOut = new Timestamp(cal.getTimeInMillis());
//		
//		if(minTimeIn.before(getStartTime()) || maxTimeOut.after(getEndTime()))
//		{
//			msg = "This process can not be continued because the work hours of employees are on the job."
//					+ "\nMinimum Time In : " + minTimeIn.toString()
//					+ "\nMaximum Time Out : " + maxTimeOut.toString();
//		}
//		
//		return msg;
//	}
}
