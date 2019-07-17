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
import java.util.Calendar;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;

import java.util.List;

import com.uns.util.MessageBox;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author eko
 *
 */
public class MUNSMonthlyPresenceSummary extends X_UNS_MonthlyPresenceSummary
		implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSDailyPresence[] m_lines = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSYearlyPresenceSummary m_yearlySummary = null;
	private MUNSEmployee m_employee = null;
	private MUNSCanteenActivity[] m_cantenActivities = null;
	
	
	/*
		public boolean isGenerate()
		{
			return getGenerateDailyPresence().equals("Y");
		}
	*/
	
		/**
		 * @param ctx
		 * @param rs
		 * @param trxName
		 */
		public MUNSMonthlyPresenceSummary(Properties ctx, ResultSet rs,
				String trxName) {
			super(ctx, rs, trxName);
			// 
		}

	/**
	 * @param ctx
	 * @param UNS_MonthlyPresenceSummary_ID
	 * @param trxName
	 */
	public MUNSMonthlyPresenceSummary(Properties ctx,
			int UNS_MonthlyPresenceSummary_ID, String trxName) {
		super(ctx, UNS_MonthlyPresenceSummary_ID, trxName);
		// 
	}
	
	/**
	 * 
	 * @param yearly
	 * @param C_Period_ID
	 */
	public MUNSMonthlyPresenceSummary(MUNSYearlyPresenceSummary yearly, int C_Period_ID,
			Timestamp startDate, Timestamp endDate)
	{
		super(yearly.getCtx(), 0, yearly.get_TrxName());
		setUNS_YearlyPresenceSummary_ID(yearly.getUNS_YearlyPresenceSummary_ID());
		setAD_Org_ID(yearly.getAD_Org_ID());
		setUNS_Employee_ID(yearly.getUNS_Employee_ID());
		setC_BPartner_ID(yearly.getC_BPartner_ID());
		setC_Job_ID(yearly.getC_Job_ID());
		setPayrollTerm(yearly.getPayrollTerm());
		setC_Period_ID(C_Period_ID);
		if(startDate != null)
			setStartDate(startDate);
		if(endDate != null)
			setEndDate(endDate);
		
		I_UNS_Employee employee = yearly.getUNS_Employee();
		
		setNoWorkDay(employee.getNoWorkDay());
		setShift(employee.getShift());
		
		saveEx();
	}
	
	public MUNSMonthlyPresenceSummary (
			MUNSYearlyPresenceSummary yearly, Timestamp date)
	{
		this(yearly.getCtx(), 0, yearly.get_TrxName());
		setUNS_YearlyPresenceSummary_ID(yearly.getUNS_YearlyPresenceSummary_ID());
		setAD_Org_ID(yearly.getAD_Org_ID());
		setUNS_Employee_ID(yearly.getUNS_Employee_ID());
		setC_BPartner_ID(yearly.getC_BPartner_ID());
		setC_Job_ID(yearly.getC_Job_ID());
		setPayrollTerm(yearly.getPayrollTerm());
		
		I_UNS_Employee employee = yearly.getUNS_Employee();
		
		setNoWorkDay(employee.getNoWorkDay());
		setShift(employee.getShift());
		
		MUNSPayrollConfiguration config = MUNSPayrollConfiguration.get(
				getCtx(), date, get_TrxName());
		
		int start = config.getPayrollDateStart();
		int end = config.getPayrollDateEnd();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		
		int maxDay = calendar.getMaximum(Calendar.DAY_OF_MONTH);
		int median = maxDay / 2;
		int curDate = calendar.get(Calendar.DATE);
		int period = calendar.get(Calendar.MONTH);
		
		if (start > median)
		{
			if (curDate >= start)
			{
				calendar.add(Calendar.MONTH, 1);
				period = calendar.get(Calendar.MONTH);
			}
			else
			{
				calendar.add(Calendar.MONTH, -1);
			}
		}
		else 
		{
			if (curDate < start)
			{
				calendar.add(Calendar.MONTH, -1);
				period = calendar.get(Calendar.MONTH);
			}
		}
		
		period++;
		String sql = "SELECT C_Period_ID FROM C_Period WHERE PeriodNo = ? "
				+ " AND C_Year_ID = ? ";
		int C_Period_ID = DB.getSQLValue(
				get_TrxName(), sql, period, yearly.getC_Year_ID());
		setC_Period_ID(C_Period_ID);
		
		calendar.set(Calendar.DATE, start);
		Timestamp startDate = new Timestamp(calendar.getTimeInMillis());
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, end);
		Timestamp endDate = new Timestamp(calendar.getTimeInMillis());
		setStartDate(TimeUtil.trunc(startDate, TimeUtil.TRUNC_DAY));
		setEndDate(TimeUtil.trunc(endDate, TimeUtil.TRUNC_DAY));
	}

	/*
		public boolean isGenerate()
		{
			return getGenerateDailyPresence().equals("Y");
		}
	*/
	
	public MUNSYearlyPresenceSummary getParent() 
	{
		if (m_yearlySummary != null)
			return m_yearlySummary;
		m_yearlySummary = (MUNSYearlyPresenceSummary) getUNS_YearlyPresenceSummary();
		return m_yearlySummary;
	}
	
	/**
	 * 
	 */
	public MUNSEmployee getUNS_Employee()
	{
		if (m_employee == null)
			m_employee = (MUNSEmployee) super.getUNS_Employee();
		
		return m_employee;
	}
	
	/**
	 * Get leave Permision Of This Month
	 * @return
	 */
	public MUNSLeavePermissionTrx[] getLeavePermissionOf()
	{
		MUNSLeavePermissionTrx[] leavePerms = null;
		List<MUNSLeavePermissionTrx> list = new ArrayList<MUNSLeavePermissionTrx>();
		MUNSYearlyPresenceSummary yearlyPresenceSummary = getParent();
		for (MUNSLeavePermissionTrx leavePerm : yearlyPresenceSummary.getLeavePermissionsOf(getC_Period_ID()))
		{
			list.add(leavePerm);
		}
		leavePerms = new MUNSLeavePermissionTrx[list.size()];
		return list.toArray(leavePerms);
	}
	
	/**
	 * 
	 * @param whereClause
	 * @return
	 */
	public MUNSDailyPresence[] getLines(String whereClause)
	{
		String whereClauseFinal = "UNS_MonthlyPresenceSummary_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		
		List<MUNSDailyPresence> list = Query.get(getCtx(), 
								UNSHRMModelFactory.EXTENSION_ID, MUNSDailyPresence.Table_Name, 
								whereClauseFinal, get_TrxName())
								.setParameters(new Object[]{getUNS_MonthlyPresenceSummary_ID()})
								.setOrderBy(MUNSDailyPresence.COLUMNNAME_UNS_DailyPresence_ID)
								.list();
		  
		return list.toArray(new MUNSDailyPresence[list.size()]);
	}
	
	/**
	 * Get list from Daily Presence for this monthly summary.
	 * 
	 * @param requery
	 * @return
	*/	 
	protected MUNSDailyPresence[] getLines(boolean requery)
	{
		if(m_lines == null || requery)
			m_lines = getLines(null);
		return m_lines;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSDailyPresence[] getLines()
	{		
		return getLines(false);
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocOptions#customizeValidActions(
	 * java.lang.String, java.lang.Object, java.lang.String, java.lang.String, int, java.lang.String[]
	 * , java.lang.String[], int)
	 */
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		// 
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
    			|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
    		options[index++] = DocumentEngine.ACTION_Prepare;
    	}
    	
    	if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
    		options[index++] = DocumentEngine.ACTION_ReActivate;
    		options[index++] = DocumentEngine.ACTION_Reverse_Correct;
    		options[index++] = DocumentEngine.ACTION_Void;
    	}   	
    		
    	return index;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		// 
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		// 
		log.info(toString());
		setProcessed(false);
		return true;
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		// 
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		// 
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		MUNSDailyPresence[] lines = getLines();
		if (lines == null || lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		
		//check periodic cost/benefit
		m_processMsg = checkPeriodicCostBenefit();
		if(m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
//		pindah ke periodic cost / benefit
		
//		MUNSCanteenActivity[] cas = getCantenActivities(false);
//		for (int i=0; i<cas.length; i++) {
//			cas[i].setProcessed(true);
//			if (!cas[i].save()) {
//				m_processMsg=CLogger.retrieveErrorString("Failed when try to update Canteen Activity");
//				return DOCSTATUS_Invalid;
//			}
//		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
			m_justPrepared = true;
			
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		// 
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		// 
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() 
	{
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
		
		StringBuilder msgBuilder = new StringBuilder();
		String msg = null;
		MUNSDailyPresence[] days = getLines(true);
		for (MUNSDailyPresence day : days)
		{
			MUNSMonthlyPresenceVal validation = day.getValidation();
			if (validation != null)
			{
				String docstatus = validation.getDocStatus();
				if (null != validation && !DOCSTATUS_Completed.equals(docstatus)
						&& !DOCSTATUS_Closed.equals(docstatus))
				{
					msg = msgBuilder.toString();
					if (!msg.contains(validation.getDocumentNo()))
					{
						if (msg.length() > 0)
						{
							msgBuilder.append(",");
						}
						msgBuilder.append(validation.getDocumentNo());
					}
				}
			}
			else if (((day.getFSTimeIn() == null || day.getFSTimeOut() == null)
					&& !MUNSDailyPresence.PRESENCESTATUS_Izin.
						equals(day.getPresenceStatus())
					&& !MUNSDailyPresence.PRESENCESTATUS_Libur.
						equals(day.getPresenceStatus())
					&& !MUNSDailyPresence.PRESENCESTATUS_Mangkir.
						equals(day.getPresenceStatus())
					&& !MUNSDailyPresence.DAYTYPE_HariLiburNasional.
						equals(day.getDayType())) ||
						(MUNSOTRequest.getValidof(
								get_TrxName(), day.getPresenceDate(), day.getUNS_MonthlyPresenceSummary()
								.getUNS_Employee_ID()) != null
								&& day.getBelatedDuration() > 0))
			{
				MUNSEmployee emp = MUNSEmployee.get(getCtx(), day.getUNS_MonthlyPresenceSummary().getUNS_Employee_ID());
				boolean isWorkDay = true;
				String sql = "SELECT hc.UNS_SlotType_ID FROM UNS_WorkHoursConfig hc"
						+ " INNER JOIN UNS_WorkHoursConfig_Line cl ON cl.UNS_WorkHoursConfig_ID"
						+ " = hc.UNS_WorkHoursConfig_ID"
						+ " WHERE (? BETWEEN hc.ValidFrom AND hc.ValidTo)"
						+ " AND hc.DocStatus IN ('CO', 'CL') AND"
						+ " (CASE WHEN cl.UNS_Resource_ID > 0 THEN cl.UNS_Resource_ID ="
						+ " (SELECT UNS_Resource_ID FROM UNS_Resource_WorkerLine WHERE Labor_ID = ?)"
						+ " ELSE cl.UNS_Employee_ID = ? END)";
				int slotType = DB.getSQLValue(get_TrxName(), sql, 
									new Object[]{day.getPresenceDate(), emp.get_ID(), emp.get_ID()});
				if(slotType <= 0)
				{
					sql = "SELECT r.UNS_SlotType_ID FROM UNS_Resource r"
							+ " INNER JOIN UNS_Resource_WorkerLine wl ON wl.UNS_Resource_ID = r.UNS_Resource_ID"
							+ " WHERE wl.Labor_ID = ?";
					slotType = DB.getSQLValue(get_TrxName(), sql, emp.get_ID());
				}
				if(slotType <= 0)
				{
					m_processMsg = "Employee not have slot type.";
					return DocAction.STATUS_Invalid;
				}
				sql = "SELECT COUNT(*) FROM UNS_SlotType st WHERE st.UNS_SlotType_ID = ?"
						+ " AND CASE WHEN IsDaySlot = 'Y' THEN (CASE WHEN "
						+ " ? = '1' THEN st.OnSunday = 'Y' WHEN"
						+ " ? = '2' THEN st.OnMonday = 'Y' WHEN"
						+ " ? = '3' THEN st.OnTuesday = 'Y' WHEN"
						+ " ? = '4' THEN st.OnWednesday = 'Y' WHEN"
						+ " ? = '5' THEN st.OnThursday = 'Y' WHEN"
						+ " ? = '6' THEN st.OnFriday = 'Y' WHEN"
						+ " ? = '7' THEN st.OnSaturday = 'Y' ELSE"
						+ " 1=1 END) ELSE 1=1 END";
				isWorkDay = DB.getSQLValue(get_TrxName(), sql, 
								new Object[]{slotType, day.getDay(), day.getDay(), day.getDay(), 
										 day.getDay(), day.getDay(), day.getDay(), day.getDay()}) 
										 	> 0 ? true : false;
			   	if(isWorkDay)
		    	{
			    	validation = MUNSMonthlyPresenceVal.createValidation(
								day, "PresenceDate", getStartDate(), 
								getEndDate(), getC_BPartner_ID(), 
								getC_Period_ID());
			    	msg = msgBuilder.toString();
					if (!msg.contains(validation.getDocumentNo()))
					{
						if (msg.length() > 0)
						{
							msgBuilder.append(",");
						}
		
						msgBuilder.append(validation.getDocumentNo());
					}
		    	}
			}
		}	
		msg = msgBuilder.toString();
		if (!Util.isEmpty(msg, true))
		{
			m_processMsg = "Please complete Monthly Presence Validation "
					.concat(msg).concat(" before do next process");
			MessageBox.showMsg(this, getCtx(), m_processMsg, "Monthly Presence Validation", 
					MessageBox.OK, MessageBox.ICONINFORMATION);
			return DocAction.STATUS_InProgress;
		}
	
		//ganti skema. Periodic Cost Benefit ambil canteen activity
//		m_processMsg = generatePeriodicCosts();
//		if (m_processMsg != null)
//			return DOCSTATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);	
		//m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		// 
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		// 
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// 
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		// 
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		//check & update Payroll
		String sql = "SELECT UNS_Payroll_Employee_ID FROM UNS_Payroll_Employee WHERE C_Period_ID = ?"
				+ " AND UNS_Employee_ID = ? AND DocStatus NOT IN ('VO','RE')";
		int payroll_id = DB.getSQLValue(get_TrxName(), sql, getC_Period_ID(), getUNS_Employee_ID());
		if(payroll_id > 0)
		{
			MUNSPayrollEmployee	payroll = new MUNSPayrollEmployee(getCtx(), payroll_id, get_TrxName());
			if(payroll.getDocStatus().equals(MUNSPayrollEmployee.DOCSTATUS_Completed)
					|| payroll.getDocStatus().equals(MUNSPayrollEmployee.DOCSTATUS_Closed))
			{
				m_processMsg = "Payroll has been complete, disallowed re-Active.";
				return false;
			}
			else if (payroll.getDocStatus().equals(MUNSPayrollEmployee.DOCSTATUS_InProgress))
			{
				m_processMsg = "Payroll is in progress, disallowed re-Active.";
				return false;
			}
			else
			{
				payroll.setIsGenerate(false);
				payroll.saveEx();
			}
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentNo()
	 */
	@Override
	public String getDocumentNo() {
		// 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		// 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		// 
		return m_processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// 
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// 
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// 
		return null;
	}	
	
	/**
	 * 
	 * @return
	 */
	public boolean deleteLines()
	{
		for (MUNSDailyPresence dailyPressence : getLines(null))
		{
			if (!dailyPressence.delete(true, get_TrxName()))
				return false;
		}
		return true;
	}
	
	/**
	 * Get it, or create it if not exist.
	 * 
	 * @param ctx
	 * @param employee
	 * @param C_Period_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSMonthlyPresenceSummary getCreate(
			Properties ctx, MUNSEmployee employee, int C_Period_ID, 
			Timestamp startDate, Timestamp endDate, String trxName)
	{
		MUNSMonthlyPresenceSummary monthlySummary = 
				get(ctx, employee.get_ID(), C_Period_ID, employee.getAD_Org_ID(), trxName);
		
		if (monthlySummary != null)
			return monthlySummary;
		
		// Create it.
//		String fiscalYear = MPeriod.get(ctx, C_Period_ID).getC_Year().getFiscalYear();
		String sql = "SELECT UNS_YearlyPresenceSummary_ID FROM UNS_YearlyPresenceSummary"
				+ " WHERE UNS_Employee_ID = ? AND C_Year_ID = ?";
		int parentID = DB.getSQLValue(trxName, sql, employee.get_ID(), MPeriod.get(ctx, C_Period_ID).getC_Year_ID());
		MUNSYearlyPresenceSummary yearlySummary = null;
		if(parentID > 0) 
			yearlySummary = new MUNSYearlyPresenceSummary(ctx, parentID, trxName);
		else
			yearlySummary = new MUNSYearlyPresenceSummary(ctx, employee, 
					MPeriod.get(ctx, C_Period_ID).getC_Year_ID(), trxName);
		
		monthlySummary = new MUNSMonthlyPresenceSummary(yearlySummary, C_Period_ID,
				startDate, endDate);
		
		return monthlySummary;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param C_Period_ID
	 * @param AD_Org_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSMonthlyPresenceSummary get(
			Properties ctx, int UNS_Employee_ID, int C_Period_ID, int AD_Org_ID, String trxName)
	{
		return Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name
				, COLUMNNAME_UNS_Employee_ID + " = " + UNS_Employee_ID + " AND "
				+ COLUMNNAME_C_Period_ID + " = " + C_Period_ID + " AND " 
				+ COLUMNNAME_AD_Org_ID + " = " + AD_Org_ID + " AND " 
						+ COLUMNNAME_IsActive + " = 'Y' "
						, trxName)
						.firstOnly();
	}
	
	/**
	 * 
	 * @param ctx
	 * @param date
	 * @param AD_Org_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSMonthlyPresenceSummary get(
			Properties ctx, int UNS_Employee_ID, Timestamp date, int AD_Org_ID, String trxName)
	{
		return Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name
				, COLUMNNAME_UNS_Employee_ID + " = " + UNS_Employee_ID + " AND ('"
				+ date + "' BETWEEN " + COLUMNNAME_StartDate + " AND " + COLUMNNAME_EndDate 
				+ ") AND " + COLUMNNAME_AD_Org_ID + " = " + AD_Org_ID + " AND " 
						+ COLUMNNAME_IsActive + " = 'Y' "
						, trxName)
						.firstOnly();
	}
	
	/**
	 * Update all header (yearly summary) except Leave Claim record.
	 * @return
	 */
	public boolean updateHeader()
	{
		MUNSYearlyPresenceSummary yearlyPresence = 
				(MUNSYearlyPresenceSummary) getUNS_YearlyPresenceSummary();
		MUNSMonthlyPresenceSummary[] monthlyList = yearlyPresence.getLines();

		int totalFullDay = 0;
		int totalHalfDay = 0;
		int totalWorkDay = 0;
		int totalAbsence = 0;
		float totalSK = 0;
		float totalSKK = 0;
		float totalLD1 = 0;
		float totalLD2 = 0;
		float totalLD3 = 0;
		float totalOverTime = 0;
		float totalOverTime1stHour = 0;
		float totalOverTimeNextHour = 0;
		
		for (MUNSMonthlyPresenceSummary monthly : monthlyList)
		{
			totalFullDay += monthly.getTotalFullDayPresence();
			totalHalfDay += monthly.getTotalHalfDayPresence();
			totalWorkDay += monthly.getTotalWorkDay();
			totalSK += monthly.getTotalSK().floatValue();
			totalSKK += monthly.getTotalSKK().floatValue();
			totalLD1 += monthly.getTotalLD1().floatValue();
			totalLD2 += monthly.getTotalLD2().floatValue();
			totalLD3 += monthly.getTotalLD3().floatValue();
			totalOverTime += monthly.getTotalOvertime().floatValue();
			totalAbsence += monthly.getTotalAbsence();
			totalOverTime1stHour += monthly.getTotalOvertime1stHour().floatValue();
			totalOverTimeNextHour += monthly.getTotalOvertimeNextHour().floatValue();
		}
		
		yearlyPresence.setTotalLD3(BigDecimal.valueOf(totalLD3));
		yearlyPresence.setTotalLD2(BigDecimal.valueOf(totalLD2));
		yearlyPresence.setTotalLD1(BigDecimal.valueOf(totalLD1));
		yearlyPresence.setTotalSKK(BigDecimal.valueOf(totalSKK));
		yearlyPresence.setTotalSK(BigDecimal.valueOf(totalSK));
		yearlyPresence.setTotalFullDayPresence(totalFullDay);
		yearlyPresence.setTotalHalfDayPresence(totalHalfDay);
		yearlyPresence.setTotalWorkDay(totalWorkDay);
		yearlyPresence.setTotalWorkDay(totalFullDay + totalHalfDay);
		yearlyPresence.setTotalAbsence(totalAbsence);
		yearlyPresence.setTotalOvertime(BigDecimal.valueOf(totalOverTime));
		yearlyPresence.setTotalOvertime1stHour(BigDecimal.valueOf(totalOverTime1stHour));
		yearlyPresence.setTotalOvertimeNextHour(BigDecimal.valueOf(totalOverTimeNextHour));
		
		yearlyPresence.saveEx();
		
		return true;
	}
	
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success){
		if(!updateHeader())
			throw new AdempiereUserError("Failed to update header");
		return super.afterSave(newRecord, success);
	}

	/**
	 * 
	 */
	public boolean updateData() 
	{
		return updateSummary(this);
	}

	public Timestamp getLastDate() {
		List<MUNSDailyPresence> dailyPresences =  Query.get(getCtx(), UNSHRMModelFactory.EXTENSION_ID, MUNSDailyPresence.Table_Name
				, COLUMNNAME_UNS_MonthlyPresenceSummary_ID + " = " + get_ID() + " AND " 
				+ COLUMNNAME_IsActive + " = 'Y' ", get_TrxName()).list();
		MUNSDailyPresence daily = dailyPresences.get(dailyPresences.size()-1);
		return daily.getPresenceDate();
	}

	/**
	 * Update given Monthly summary by tracing it's daily presence data.
	 * NOTE: Only call this method if you already very much confident that it's daily records are very well set.
	 * 
	 * @param monthly
	 * @return
	 */
	public static boolean updateSummary(MUNSMonthlyPresenceSummary monthly)
	{
		MUNSDailyPresence[] dailyList = monthly.getLines();
		
		int totalFullDayPresence = 0;
		int totalAbsence = 0;
		int totalPayableAbsence = 0;
		int totalNonPayableAbsence = 0;
		int totalHalfDayPresence = 0;
		int totalPayableHalfAbsence = 0;
		int totalNonPayableHalfAbsence = 0;
		float totalLD1 = 0;
		float totalLD2 = 0;
		float totalLD3 = 0;
		float totalSK = 0;
		float totalSKK = 0;
		float totalOvertime = 0;
		float totalOvertime1stHour = 0;
		float totalOvertimeNextHour = 0;
		int totalWorkDay = 0;
		float totalWorkHours = 0;
		
		for (MUNSDailyPresence daily : dailyList)
		{
			if (MUNSDailyPresence.PRESENCESTATUS_Libur.equals(daily.getPresenceStatus()))
				continue;
			
			if (daily.getPermissionType() != null && !daily.getPermissionType().isEmpty())
			{
				if(MUNSDailyPresence.PRESENCESTATUS_HalfDay.equals(daily.getPresenceStatus())) 
				{
					totalWorkDay++;
					totalWorkHours += daily.getWorkHours().floatValue();
					
					totalHalfDayPresence++;
					
					if (MUNSDailyPresence.PERMISSIONTYPE_Other.equals(daily.getPermissionType())
						|| MUNSDailyPresence.PERMISSIONTYPE_PayPermissionIzinPotongGaji.equals(daily.getPermissionType()))
						totalNonPayableHalfAbsence++;
					else { 
						totalPayableHalfAbsence++;
						
						if (MUNSDailyPresence.PERMISSIONTYPE_SuratKeteranganIstirahat.equals(daily.getPermissionType()))
							totalSK += 0.5;
						else if (MUNSDailyPresence.PERMISSIONTYPE_SuratKeteranganIstirahatKecelakaanKerja
								.equals(daily.getPermissionType()))
							totalSKK += 0.5;
					}
				}
				else if (MUNSDailyPresence.DAYTYPE_HariKerjaBiasa.equals(daily.getDayType())
						 && MUNSDailyPresence.PRESENCESTATUS_Izin.equals(daily.getPresenceStatus()))
						// && !PRESENCESTATUS_Mangkir.equals(getPresenceStatus())
						// && !PRESENCESTATUS_FullDay.equals(getPresenceStatus())
						// && !PRESENCESTATUS_Lembur.equals(getPresenceStatus())) 
				{
					totalWorkDay++;
					totalWorkHours += daily.getWorkHours().floatValue();
					
					totalAbsence++;
					
					if (MUNSDailyPresence.PERMISSIONTYPE_Other.equals(daily.getPermissionType())
						|| MUNSDailyPresence.PERMISSIONTYPE_PayPermissionIzinPotongGaji.equals(
								daily.getPermissionType()))
						totalNonPayableAbsence++;
					else {
						totalPayableAbsence++;
						if (MUNSDailyPresence.PERMISSIONTYPE_SuratKeteranganIstirahat.equals(
								daily.getPermissionType()))
							totalSK++;
						else if (MUNSDailyPresence.PERMISSIONTYPE_SuratKeteranganIstirahatKecelakaanKerja.equals(
								daily.getPermissionType()))
							totalSK++;
					}
				}
				continue;
			}
			//di bawah ini sudah pasti tanpa permission alias permission == null.
			else if (MUNSDailyPresence.PRESENCESTATUS_Mangkir.equals(daily.getPresenceStatus())) 
			{
				totalAbsence++;
				totalNonPayableAbsence++;
				continue;
			}
			else if (MUNSDailyPresence.PRESENCESTATUS_HalfDay.equals(daily.getPresenceStatus()))
			{
				totalHalfDayPresence++;
				totalNonPayableHalfAbsence++;

				totalWorkDay++;
				totalWorkHours += daily.getWorkHours().floatValue();
				continue;
			}
			
			// di bawah ini sudah hanya utk lembur dan full day.
			
			if (!MUNSDailyPresence.DAYTYPE_HariKerjaBiasa.equals(daily.getDayType())
				&& MUNSDailyPresence.PRESENCESTATUS_Lembur.equals(daily.getPresenceStatus()))
			{
				totalLD1 += daily.getLD1().floatValue();
				totalLD2 += daily.getLD2().floatValue();
				totalLD3 += daily.getLD3().floatValue();
				continue;
			}
			
			if (MUNSDailyPresence.DAYTYPE_HariKerjaBiasa.equals(daily.getDayType())
				&& (MUNSDailyPresence.PRESENCESTATUS_FullDay.equals(daily.getPresenceStatus())
						|| MUNSDailyPresence.PRESENCESTATUS_Belated.equals(daily.getPresenceStatus())))
			{
				totalWorkDay++;
				totalWorkHours += daily.getWorkHours().floatValue();
				
				totalFullDayPresence++;
				totalOvertime += daily.getOvertime().doubleValue();
				
				if (daily.getOvertime().compareTo(Env.ONE) <= 0) {
					totalOvertime1stHour += daily.getOvertime().floatValue();
				}
				else {
					totalOvertime1stHour++;
					totalOvertimeNextHour += daily.getOvertime().subtract(Env.ONE).floatValue();
				}
			}
		}
		monthly.setTotalWorkDay(totalWorkDay);
		monthly.setTotalWorkHours(BigDecimal.valueOf(totalWorkHours));
		
		monthly.setTotalFullDayPresence(totalFullDayPresence);
		
		monthly.setTotalAbsence(totalAbsence);
		monthly.setTotalPayableAbsence(totalPayableAbsence);
		monthly.setTotalNonPayableAbsence(totalNonPayableAbsence);
		
		monthly.setTotalHalfDayPresence(totalHalfDayPresence);
		monthly.setTotalPayableHalfDay(totalPayableHalfAbsence);
		monthly.setTotalNonPayableHalfDay(totalNonPayableHalfAbsence);
		
		monthly.setTotalSK(BigDecimal.valueOf(totalSK));
		monthly.setTotalSKK(BigDecimal.valueOf(totalSKK));
		
		monthly.setTotalLD1(BigDecimal.valueOf(totalLD1));
		monthly.setTotalLD2(BigDecimal.valueOf(totalLD2));
		monthly.setTotalLD3(BigDecimal.valueOf(totalLD3));

		monthly.setTotalOvertime(BigDecimal.valueOf(totalOvertime));
		monthly.setTotalOvertime1stHour(BigDecimal.valueOf(totalOvertime1stHour));
		monthly.setTotalOvertimeNextHour(BigDecimal.valueOf(totalOvertimeNextHour));
		
		monthly.saveEx();
		
		return true;
	}
	
	/**
	 * 
	 * @param payrollConfig
	 * @return
	 */
	public float getProportionPotonganLembur(MUNSPayrollConfiguration payrollConfig)
	{
		int totalNonPayableAbsence = 0;
		float proportionPotongan = 0;
		float multiplier = 1;
		float totalOTBasisHour = 39;
		
		if (MUNSEmployee.SHIFT_NonShift.equals(getUNS_Employee().getShift()))
		{
			multiplier = payrollConfig.getNonShiftOTDeductionMultiplier().floatValue();
			totalOTBasisHour = payrollConfig.getNonShiftOTAllowanceHours().floatValue();
			
			if (payrollConfig.isNonShiftAllWorkDayIsOT()) {
				totalNonPayableAbsence = getTotalNonPayableAbsence();
			}
			else {
				MUNSDailyPresence[] dailyList = getLines();
				
				for (MUNSDailyPresence daily : dailyList)
				{
					if (daily.getDay().equals(payrollConfig.getNonShiftOTDay())
						&& MUNSDailyPresence.DAYTYPE_HariKerjaBiasa.equals(daily.getDayType())) 
					{
						if (MUNSDailyPresence.PRESENCESTATUS_Mangkir.equals(daily.getPresenceStatus()))
							totalNonPayableAbsence++;
						else if (daily.getPermissionType() != null && !daily.getPermissionType().isEmpty()
								 && MUNSDailyPresence.PRESENCESTATUS_Izin.equals(daily.getPresenceStatus())
								 && (MUNSDailyPresence.PERMISSIONTYPE_Other.equals(daily.getPermissionType())
									 || MUNSDailyPresence.PERMISSIONTYPE_PayPermissionIzinPotongGaji.equals(
											daily.getPermissionType())))
							totalNonPayableAbsence++;
					}
				}
			}
		}
		else {
			multiplier = payrollConfig.getShiftOTDeductionMultiplier().floatValue();
			totalOTBasisHour = payrollConfig.getNonShiftOTAllowanceHours().floatValue();

			if (payrollConfig.isShiftAllWorkDayIsOT()) {
				totalNonPayableAbsence = getTotalNonPayableAbsence();
			}
			else {
				MUNSDailyPresence[] dailyList = getLines();
				
				for (MUNSDailyPresence daily : dailyList)
				{
					if (daily.getDay().equals(payrollConfig.getNonShiftOTDay())
						&& MUNSDailyPresence.DAYTYPE_HariKerjaBiasa.equals(daily.getDayType())) 
					{
						if (MUNSDailyPresence.PRESENCESTATUS_Mangkir.equals(daily.getPresenceStatus()))
							totalNonPayableAbsence++;
						else if (daily.getPermissionType() != null && !daily.getPermissionType().isEmpty()
								 && MUNSDailyPresence.PRESENCESTATUS_Izin.equals(daily.getPresenceStatus())
								 && (MUNSDailyPresence.PERMISSIONTYPE_Other.equals(daily.getPermissionType())
									 || MUNSDailyPresence.PERMISSIONTYPE_PayPermissionIzinPotongGaji.equals(
											daily.getPermissionType())))
							totalNonPayableAbsence++;
					}
				}
			}
		}
		proportionPotongan = (totalNonPayableAbsence * multiplier) / totalOTBasisHour;
		
		return proportionPotongan;
	}

	/**
	 * 
	 * @return
	 */
	public float getProportionPotonganMangkir()
	{
		float proportionPotongan = 0;
		
		proportionPotongan = getTotalNonPayableAbsence();
		proportionPotongan += getTotalNonPayableHalfDay() * 0.5;
		
		return proportionPotongan;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param AD_Org_ID
	 * @param C_Period_ID
	 * @param payrollPeriodType
	 * @param trxName
	 * @return
	 */
	public static MUNSMonthlyPresenceSummary[] getOf(
			Properties ctx, int AD_Org_ID, int C_Period_ID
			, String payrollPeriodType, String trxName)
	{
		List<MUNSMonthlyPresenceSummary> list = 
				new ArrayList<MUNSMonthlyPresenceSummary>();
		
		ArrayList<Object> params = new ArrayList<Object>();
		
		StringBuilder sb = new StringBuilder("SELECT * FROM ");
		sb.append(Table_Name)
		.append(" WHERE ")
		.append(COLUMNNAME_AD_Org_ID)
		.append(" =? ")
		.append(" AND ")
		.append(COLUMNNAME_C_Period_ID)
		.append(" =? AND ");
		
		params.add(AD_Org_ID);
		params.add(C_Period_ID);
		
		if(MUNSMonthlyPayrollEmployee.PERIODTYPE_1Month.equals(payrollPeriodType))
		{
			sb.append(COLUMNNAME_UNS_Employee_ID)
			.append(" IN (SELECT ")
			.append(MUNSEmployee.COLUMNNAME_UNS_Employee_ID)
			.append(" FROM ")
			.append(MUNSEmployee.Table_Name)
			.append(" WHERE ")
			.append(MUNSEmployee.COLUMNNAME_PayrollTerm)
			.append(" IN (?,?) AND ")
			.append(MUNSEmployee.COLUMNNAME_EmploymentType)
			.append(" IN (?))");
			
			params.add(MUNSEmployee.PAYROLLTERM_Monthly);
			params.add(MUNSEmployee.PAYROLLTERM_HarianBulanan);
			params.add(MUNSEmployee.EMPLOYMENTTYPE_Company);
		}
		else if(MUNSMonthlyPayrollEmployee.PERIODTYPE_1st2Weeks.equals(payrollPeriodType) 
				|| MUNSMonthlyPayrollEmployee.PERIODTYPE_2nd2Weeks.equals(payrollPeriodType))
		{
			sb.append(COLUMNNAME_UNS_Employee_ID)
			.append(" IN (SELECT ")
			.append(MUNSEmployee.COLUMNNAME_UNS_Employee_ID)
			.append(" FROM ")
			.append(MUNSEmployee.Table_Name)
			.append(" WHERE ")
			.append(MUNSEmployee.COLUMNNAME_PayrollTerm)
			.append(" IN (?) AND ")
			.append(MUNSEmployee.COLUMNNAME_EmploymentType)
			.append(" IN (?))");
			
			params.add(MUNSEmployee.PAYROLLTERM_2Weekly);
			params.add(MUNSEmployee.EMPLOYMENTTYPE_Company);
		}
		else if(MUNSMonthlyPayrollEmployee.PERIODTYPE_1stWeek.equals(payrollPeriodType)
				|| MUNSMonthlyPayrollEmployee.PERIODTYPE_2ndWeek.equals(payrollPeriodType)
				|| MUNSMonthlyPayrollEmployee.PERIODTYPE_3rdWeek.equals(payrollPeriodType)
				||MUNSMonthlyPayrollEmployee.PERIODTYPE_4thWeek.equals(payrollPeriodType))
		{
			sb.append(COLUMNNAME_UNS_Employee_ID)
			.append(" IN (SELECT ")
			.append(MUNSEmployee.COLUMNNAME_UNS_Employee_ID)
			.append(" FROM ")
			.append(MUNSEmployee.Table_Name)
			.append(" WHERE ")
			.append(MUNSEmployee.COLUMNNAME_PayrollTerm)
			.append(" IN (?) AND ")
			.append(MUNSEmployee.COLUMNNAME_EmploymentType)
			.append(" IN (?))");
			
			params.add(MUNSEmployee.PAYROLLTERM_Weekly);
			params.add(MUNSEmployee.EMPLOYMENTTYPE_Company);
		}
		else
		{
			throw new AdempiereException("Unhandled Payroll Term " + payrollPeriodType);
		}
		
		String sql = sb.toString();
		
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		try
		{
			stm = DB.prepareStatement(sql, trxName);
			int count = 1;
			for(Object oo : params)
			{
				stm.setObject(count++, oo);
			}
			rs = stm.executeQuery();
			while(rs.next())
			{
				MUNSMonthlyPresenceSummary record = new MUNSMonthlyPresenceSummary(
						ctx, rs, trxName);
				list.add(record);
			}
			
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		} finally
		{
			DB.close(rs, stm);
		}
		
		if(list == null || list.size() ==0)
			return null;

		MUNSMonthlyPresenceSummary[] arrayOfData = 
				new MUNSMonthlyPresenceSummary[list.size()];
		arrayOfData = list.toArray(arrayOfData);
		
		return arrayOfData;
	}
	
	@Override 
	public boolean beforeSave(boolean newRecord)
	{
		if(newRecord || is_ValueChanged(COLUMNNAME_C_Period_ID) || is_ValueChanged(COLUMNNAME_UNS_Employee_ID))
		{
			String sql = "SELECT COUNT(mps.*) FROM UNS_MonthlyPresenceSummary mps"
					+ " WHERE mps.UNS_MonthlyPresenceSummary_ID <> ?"
					+ " AND mps.UNS_Employee_ID = ? AND mps.C_Period_ID = ?";
			int count = DB.getSQLValue(get_TrxName(), sql, new Object[]{get_ID(), 
				getUNS_Employee_ID(), getC_Period_ID()});
			if(count > 0)
			{
				log.saveError("Error", "Duplicate period on one employee");
				return false;
			}
		}
		
		String parentPayrollTerm = getParent().getPayrollTerm();
		if(null == parentPayrollTerm 
				|| !parentPayrollTerm.equals(getPayrollTerm()))
		{
			getParent().setPayrollTerm(getPayrollTerm());
			getParent().save();
		}
		
		if (!isProcessed())
		{
			setStartEndDate();
		}
		
		return super.beforeSave(newRecord);
	}
	
	
	/**
	 * 
	 * @param week
	 * @return
	 */
	public MUNSDailyPresence[] getDailyPresencesInWeek(int week)
	{
		if(week < 1 || week > 4)
			return null;
		
		List<MUNSDailyPresence> list = new ArrayList<MUNSDailyPresence>();
		
		MUNSDailyPresence[] days1Month = getLines();
		Calendar cal = Calendar.getInstance();
		
		for(MUNSDailyPresence theDay : days1Month)
		{
			cal.setTimeInMillis(theDay.getPresenceDate().getTime());
			if(week ==1)
			{
				if(cal.get(Calendar.WEEK_OF_MONTH) == 1)
					list.add(theDay);
			}
			else if(week ==2)
			{
				if(cal.get(Calendar.WEEK_OF_MONTH) == 2)
					list.add(theDay);
			}
			else if(week ==3)
			{
				if(cal.get(Calendar.WEEK_OF_MONTH) == 3)
					list.add(theDay);
			}
			else if(week ==4)
			{
				if(cal.get(Calendar.WEEK_OF_MONTH) == 4)
					list.add(theDay);
			}
		}
		
		if(list.size() == 0)
			return null;
		
		MUNSDailyPresence[] days = new MUNSDailyPresence[list.size()];
		list.toArray(days);
		
		return days;
	}
	
	
	/**
	 * 
	 * @param weekSequence
	 * @return
	 */
	public MUNSDailyPresence[] getDailyPresencesIn2Week(int weekSequence)
	{
		if(weekSequence < 1 || weekSequence > 2)
			return null;
		
		List<MUNSDailyPresence> list = new ArrayList<MUNSDailyPresence>();
		
		MUNSDailyPresence[] days1Month = getLines();
		Calendar cal = Calendar.getInstance();
		
		for(MUNSDailyPresence theDay : days1Month)
		{
			cal.setTimeInMillis(theDay.getPresenceDate().getTime());
			if(weekSequence ==1)
			{
				if(cal.get(Calendar.WEEK_OF_MONTH) ==1
						|| cal.get(Calendar.WEEK_OF_MONTH) == 2)
				{
					list.add(theDay);
				}
			}
			else if(weekSequence ==2)
			{
				if(cal.get(Calendar.WEEK_OF_MONTH) == 3
						|| cal.get(Calendar.WEEK_OF_MONTH) == 4)
				{
					list.add(theDay);
				}
			}
		}
		
		if(list.size() == 0)
			return null;
		
		MUNSDailyPresence[] days = new MUNSDailyPresence[list.size()];
		list.toArray(days);
		
		return days;
	}
	
	private void setStartEndDate ()
	{
		MPeriod period = (MPeriod) getC_Period();
		Calendar cal = TimeUtil.getCalendar(period.getStartDate());
		
		MUNSPayrollConfiguration config =
				MUNSPayrollConfiguration.get(
						getCtx(), period, get_TrxName());
		
		if (null == config)
		{
			throw new AdempiereException(
					"Can't find payroll configuration.");
		}
		
		int startConfig = config.getPayrollDateStart();
		int endConfig = config.getPayrollDateEnd();
		
		cal.set(Calendar.DATE, startConfig);
		
		int daysOfMonth = cal.getActualMaximum(Calendar.DATE);
		int median = daysOfMonth / 2;
		
		if (startConfig > median)
		{
			cal.add(Calendar.MONTH, -1);
		}
		
		setStartDate(new Timestamp(cal.getTimeInMillis()));

		MUNSContractRecommendation contract =
				MUNSContractRecommendation.getOf(
						getCtx(), getUNS_Employee_ID(), getStartDate(), get_TrxName());
		if (null == contract)
			contract =
			MUNSContractRecommendation.getOf(
					getCtx(), getUNS_Employee_ID(), getEndDate(), get_TrxName());
		if (null == contract)
		{
			throw new AdempiereException("Employee hasn't have contract."
					+ getParent().getUNS_Employee().toString());
		}
		
		Timestamp contractStart = contract.getDateContractStart();
		
		if (getStartDate().before(contractStart))
		{
			Timestamp yesterdayContract = TimeUtil.addDays(contractStart, -1);
			MUNSContractRecommendation prevContract = contract.getPrev();
			
			if (null == prevContract || !yesterdayContract.equals(
					prevContract.getDateContractEnd()))
			{
				setStartDate(contractStart);
			}
		}
		
		if (startConfig > median)
		{
			cal.add(Calendar.MONTH, 1);
		}
		int maxDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(maxDayOfMonth < endConfig)
			endConfig = maxDayOfMonth;
		cal.set(Calendar.DATE, endConfig);
		
		setEndDate(new Timestamp(cal.getTimeInMillis()));
		
		if (getEndDate().after(contract.getDateContractEnd()))
		{
			MUNSContractRecommendation next = MUNSContractRecommendation.getOf(
					getCtx(), getParent().getUNS_Employee_ID(), getEndDate(), get_TrxName());
			if (null == next || getEndDate().after(next.getDateContractEnd()))
				setEndDate(contract.getDateContractEnd());
		}
	}
	
	/**
	 * 
	 * @param ctx
	 * @param whereClause
	 * @param params
	 * @param orderBy
	 * @param trxName
	 * @return
	 */
	public static MUNSMonthlyPresenceSummary[] get (
			Properties ctx, String whereClause, List<Object> params, 
			String orderBy, String trxName)
	{
		Query query = Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
				whereClause, trxName);
		if (params != null && params.size() > 0)
		{
			query.setParameters(params);
		}
		if (!Util.isEmpty(orderBy, true))
		{
			query.setOrderBy(orderBy);
		}
		
		List<MUNSMonthlyPresenceSummary> list = query.list();
		MUNSMonthlyPresenceSummary[] monthly = 
				new MUNSMonthlyPresenceSummary[list.size()];
		list.toArray(monthly);
		
		return monthly;
	}
	
	public MUNSDailyPresence getDaily (Timestamp date)
	{
		date = TimeUtil.trunc(date, TimeUtil.TRUNC_DAY);
		getLines();
		for (int i=0; i<m_lines.length; i++)
		{
			if (m_lines[i].getPresenceDate().equals(date))
			{
				return m_lines[i];
			}
		}
		
		return null;
	}
	
	@Override 
	public Timestamp getStartDate ()
	{
		if (super.getStartDate() != null)
			return super.getStartDate();
		
		setStartEndDate();
		return super.getStartDate();
	}
	
	@Override
	public Timestamp getEndDate ()
	{
		if (super.getEndDate() != null)
			return super.getEndDate();
		
		setStartEndDate();
		return super.getEndDate();
	}
	
	/**
	 * 
	 * @param defaultBreakfast
	 * @param defaultLunch
	 * @param defaultDinner
	 * @return null or error message.
	 */
	public String generateEmployeeCanteenActivity (
			boolean defaultBreakfast, boolean defaultLunch, 
			boolean defaultDinner) {
		Timestamp start = getStartDate();
		Timestamp end = TimeUtil.addDays(getEndDate(), 1);
		while (start.before(end)) {
			
			MUNSCanteenActivity activity = MUNSCanteenActivity.get(get_ID(), start, get_TrxName());
			if (activity == null) {
				activity = new MUNSCanteenActivity(this);
				activity.setIsBreakfast(defaultBreakfast);
				activity.setIsLunch(defaultLunch);
				activity.setIsDinner(defaultDinner);
				activity.setDateTrx(start);
				if (!activity.save()) 
					return CLogger.retrieveErrorString(
							"Failed when try to save canteen activity data");
			}
			start = TimeUtil.addDays(start, 1);
		}
		return null;
	}
	
	public MUNSCanteenActivity[] getCantenActivities (boolean requery) {
		if (m_cantenActivities != null && ! requery) {
			set_TrxName(m_cantenActivities, get_TrxName());
			return m_cantenActivities;
		}
		List<MUNSCanteenActivity> list = Query.get(
				getCtx(), UNSHRMModelFactory.EXTENSION_ID, 
				MUNSCanteenActivity.Table_Name, Table_Name +"_ID = ?", get_TrxName()).
				setParameters(get_ID()).list();
		m_cantenActivities = new MUNSCanteenActivity[list.size()];
		list.toArray(m_cantenActivities);
		return m_cantenActivities;
	}
	
	public String generatePeriodicCosts () {
		//TODO for handle weekly absence
		MUNSCanteenActivity[] activities = getCantenActivities(false);
		BigDecimal amount = Env.ZERO;
		for (int i=0; i<activities.length; i++) {
			MUNSCanteenPrice price = MUNSCanteenPrice.getByDate(getAD_Org_ID(), activities[i].getDateTrx(), get_TrxName());
			if (activities[i].isBreakfast())
				amount = amount.add(price.getBreakfast());
			if (activities[i].isLunch())
				amount = amount.add(price.getLunch());
			if (activities[i].isDinner())
				amount = amount.add(price.getDinner());
		}
		if (amount.signum() == 0)
			return null;
		
		MUNSPeriodicCostBenefit costBen = MUNSPeriodicCostBenefit.getOnPeriod(
				getAD_Org_ID(), getParent().getC_Year_ID(), getC_Period_ID(), get_TrxName());
		if (costBen == null) {
			costBen = new MUNSPeriodicCostBenefit(getCtx(), 0, get_TrxName());
			costBen.setAD_Org_ID(getAD_Org_ID());
			costBen.setC_Year_ID(getParent().getC_Year_ID());
			costBen.setC_Period_ID(getC_Period_ID());
			costBen.setCostBenefitType(MUNSPeriodicCostBenefit.COSTBENEFITTYPE_Canteen);
			costBen.setIsBenefit(false);
			costBen.setWeekNo(null);
			costBen.setDateFrom(getStartDate());
			costBen.setDateTo(getEndDate());
			if (!costBen.save())
				return CLogger.retrieveErrorString("Could not save Periodic Cost Benefit");
		}
		
		MUNSPeriodicCostBenefitLine line = new MUNSPeriodicCostBenefitLine(costBen);
		line.setUNS_Employee_ID(getUNS_Employee_ID());
		line.setAmount(amount);
		line.setRemainingAmount(Env.ZERO);
		line.setPaidAmt(Env.ZERO);
		if (!line.save()) {
			return CLogger.retrieveErrorString("Could not save Periodic Cost Benefit Line");
		}
		return null;
	}
	
	public String checkPeriodicCostBenefit() {
		
		String errorMsg = null;
		String sql = "SELECT per.DocStatus FROM uns_periodiccostbenefit per"
				+ " INNER JOIN uns_periodiccostbenefitline perl ON perl.uns_periodiccostbenefit_id = per.uns_periodiccostbenefit_id"
				+ " WHERE per.c_period_id = ? AND per.c_year_id = ? AND perl.uns_employee_id = ? AND per.docstatus NOT IN ('RE','VO')"
				+ " AND ((per.datefrom BETWEEN ? AND ? OR per.dateto BETWEEN ? AND ?)"
				+ " OR (? BETWEEN per.datefrom AND per.dateto OR ? BETWEEN per.datefrom AND per.dateto))";
		
		ResultSet rs = null;
		PreparedStatement sta = null;
		
		try {
			
			sta = DB.prepareStatement(sql, get_TrxName());
			sta.setInt(1, getC_Period_ID());
			sta.setInt(2, getParent().getC_Year_ID());
			sta.setInt(3, getUNS_Employee_ID());
			sta.setTimestamp(4, getStartDate());
			sta.setTimestamp(5, getEndDate());
			sta.setTimestamp(6, getStartDate());
			sta.setTimestamp(7, getEndDate());
			sta.setTimestamp(8, getStartDate());
			sta.setTimestamp(9, getEndDate());
			rs = sta.executeQuery();
			
			while(rs.next())
			{
				String DocStatus = rs.getString(1);
				if(!DocStatus.equals(DOCSTATUS_Completed) && !DocStatus.equals(DOCSTATUS_Closed))
				{
					errorMsg = "You must complete Periodic Cost / Benefit first";
					break;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		finally {
			DB.close(rs, sta);
		}
		
		return errorMsg;
	}
	
	
}
