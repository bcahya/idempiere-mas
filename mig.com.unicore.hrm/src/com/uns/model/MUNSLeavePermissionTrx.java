 /**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_JobCategory;
import org.compiere.model.MNonBusinessDay;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;

import com.uns.util.MessageBox;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

import com.uns.model.MUNSJobCareTaker;
import com.uns.util.ErrorMsg;

/**
 * @author eko
 *
 */
public class MUNSLeavePermissionTrx extends X_UNS_LeavePermissionTrx implements
		DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSLeavePermissionTrx[] m_lines = null;
	private MUNSEmployee m_employee = null;
	private BigDecimal m_dLeaveRequested = Env.ZERO;
	

	/**
	 * @param ctx
	 * @param UNS_LeavePermissionTrx_ID
	 * @param trxName
	 */
	public MUNSLeavePermissionTrx(Properties ctx,
			int UNS_LeavePermissionTrx_ID, String trxName) {
		super(ctx, UNS_LeavePermissionTrx_ID, trxName);
		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLeavePermissionTrx(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}
	
	public MUNSLeavePermissionTrx[] getLines(String whereClause)
	{
		String whereClauseFinal = "";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		
		List<MUNSLeavePermissionTrx> list = Query.get(
				getCtx(), UNSHRMModelFactory.getExtensionID(), 
				MUNSLeavePermissionTrx.Table_Name, 
				whereClauseFinal, get_TrxName())
				.setOrderBy(MUNSLeavePermissionTrx.
						COLUMNNAME_UNS_LeavePermissionTrx_ID)
				.list();
		return list.toArray(new MUNSLeavePermissionTrx[list.size()]);
	}
	
	public MUNSLeavePermissionTrx[] getLines(){
		
		if(m_lines == null)
			m_lines = getLines(null);
		
		return m_lines;
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
	 * 
	 * @return
	 */
	public MUNSEmployee getEmployee()
	{
		return getUNS_Employee();
	}
	
	@Deprecated
	public MUNSLeavePermissionTrx[] get(
			int UNS_YearlyPresenceSummary_ID, 
			int C_Period_ID, int UNS_Employee_ID, int AD_Org_ID)
	{
		String whereClause = COLUMNNAME_UNS_YearlyPresenceSummary_ID + "=" 
				+ UNS_YearlyPresenceSummary_ID 
				+ " AND " + COLUMNNAME_C_Period_ID + "=" + C_Period_ID
				+ " AND " + COLUMNNAME_UNS_Employee_ID + " = " 
				+ " AND " + COLUMNNAME_AD_Org_ID + "=" + AD_Org_ID;
		MUNSLeavePermissionTrx[] leavePermTrx = getLines(whereClause);
		return leavePermTrx;
	}
	
	/**
	 * 
	 * @param UNS_YearlyPresenceSummary_ID
	 * @param C_Period_ID
	 * @param UNS_Employee_ID
	 * @param EmployeeType
	 * @param AD_Org_ID
	 * @return
	 */
	public MUNSLeavePermissionTrx[] get(
				int UNS_YearlyPresenceSummary_ID, int C_Period_ID,
				int UNS_Employee_ID, String EmployeeType, int AD_Org_ID)
	{
		String whereClause = COLUMNNAME_UNS_YearlyPresenceSummary_ID + "=" 
				+ UNS_YearlyPresenceSummary_ID 
				+ " AND " + COLUMNNAME_C_Period_ID + "=" + C_Period_ID
				+ " AND " + COLUMNNAME_UNS_Employee_ID + " = " + UNS_Employee_ID
				+ " AND " + COLUMNNAME_EmploymentType + " = " + EmployeeType
				+ " AND " + COLUMNNAME_AD_Org_ID + "=" + AD_Org_ID;
		MUNSLeavePermissionTrx[] leavePermTrx = getLines(whereClause);
		return leavePermTrx;
	}
	
	
	/**
	 * Get leave permission of half period
	 * @param UNS_YearlyPresenceSummary_ID
	 * @param C_Period_ID
	 * @param dateStart
	 * @param dateEnd
	 * @param UNS_Employee_ID
	 * @param EmployeeType
	 * @param AD_Org_ID
	 * @return MUNSLeavePermissionTrx[] 
	 */
	public MUNSLeavePermissionTrx[] getInHalfPeriod(
			int C_Period_ID, Timestamp dateStart, Timestamp dateEnd,
			int UNS_Employee_ID, String EmployeeType, int AD_Org_ID)
	{
		String whereClause = 
				COLUMNNAME_LeaveDateStart + " BETWEEN '" + dateStart
				+ "' AND '" + dateEnd + "' OR " +  COLUMNNAME_LeaveDateEnd 
				+ " BETWEEN '" + dateStart + "' AND '" + dateEnd
				+ "' AND " + COLUMNNAME_C_Period_ID + "=" + C_Period_ID
				+ " AND " + COLUMNNAME_UNS_Employee_ID + " = " + UNS_Employee_ID
				+ " AND " + COLUMNNAME_EmploymentType + " = " + EmployeeType
				+ " AND " + COLUMNNAME_AD_Org_ID + "=" + AD_Org_ID;
		MUNSLeavePermissionTrx[] leavePermTrx = getLines(whereClause);
		return leavePermTrx;
	}

	
	public BigDecimal getTotalLeaveUsedOfYear()
	{
		BigDecimal totalLeaveUsed = Env.ZERO;
		MUNSLeavePermissionTrx[] linesLeaveRequest = getLines
			(" "+ COLUMNNAME_LeaveType +"='LC'"+ "AND "+" "
			+ COLUMNNAME_AD_Org_ID + "= "+getAD_Org_ID() + " AND "
			+ COLUMNNAME_UNS_Employee_ID + "= "+getUNS_Employee_ID()
			+ " AND " + COLUMNNAME_EmploymentType + " = '" + getEmploymentType()
			+ "' AND "+ COLUMNNAME_C_Year_ID + "= "+getC_Year_ID() + " AND "
			+ COLUMNNAME_DocStatus + " IN ('" + DOCSTATUS_Closed + "', '" 
			+ DOCSTATUS_Completed + "')");
		
		for (int i=0; i<linesLeaveRequest.length; i++)
		{
			totalLeaveUsed = totalLeaveUsed.add(
					linesLeaveRequest[i].getLeaveRequested());
		}
		return totalLeaveUsed;
	}
		
	@Override
	protected boolean beforeSave(boolean newRecord)
	{	
		if(!LEAVETYPE_PermissionDispensationIzinDibayar.equals(getLeaveType()))
			setUNS_DispensationConfig_ID(0);
		
		
		String notAllowMsg = isRequestedLeaveAllowed();
		
		if (notAllowMsg != null && !notAllowMsg.isEmpty())
		{
			ErrorMsg.setErrorMsg(
					getCtx(), "Disallowed Leave Requested", notAllowMsg);
			return false;
		}
		
		if (!newRecord)
		{
			if (LEAVETYPE_SuratKeteranganIstirahat.equals(getLeaveType()) 
				|| LEAVETYPE_SuratKeteranganIstirahatKecelakaanKerja.equals(
						getLeaveType()))
			{
				if (getLeaveDateStart().before((Timestamp) get_ValueOld(
						COLUMNNAME_LeaveDateStart)))
				{
					ErrorMsg.setErrorMsg(getCtx(), "Invalid Date", 
							"Leave date start can't before clinic "
							+ "recommendation date.");
					return false;
				}
				if (getLeaveDateEnd().after((Timestamp) get_ValueOld(
						COLUMNNAME_LeaveDateEnd)))
				{

					ErrorMsg.setErrorMsg(getCtx(), "Invalid Date", 
							"Leave date end can't after clinic "
							+ "recommendation date.");
					return false;
				}
			}
		}
		MUNSYearlyPresenceSummary yearlyPresence = 
				MUNSYearlyPresenceSummary.getCreate(
						getCtx(), getEmployee(), getC_Year().getFiscalYear(), 
						get_TrxName());
		setUNS_YearlyPresenceSummary_ID(yearlyPresence.get_ID());
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public String isRequestedLeaveAllowed()
	{
		String retMsg = null;
		
		if (getLeaveDateStart() == null || getLeaveDateEnd() == null)
			retMsg = "Cannot process leave permission without selected "
					+ "date start and date end.";
		else if (getLeaveDateStart().after(getLeaveDateEnd()))
			retMsg = "Cannot process leave permission with date "
					+ "start greater than date end."; 
		else if(LEAVETYPE_PermissionDispensationIzinDibayar.equals(
				getLeaveType()))
		{
			if (getUNS_DispensationConfig_ID() > 0)
			{
				I_UNS_DispensationConfig dispensation = 
						getUNS_DispensationConfig();
				int MaximalHariUntukIzin = dispensation.getMaxDispensation();
				if (getLeaveRequested().doubleValue() > MaximalHariUntukIzin)
					retMsg = "Maximum Dispensation of " 
							+ dispensation.getName() + " is only for " 
							+ MaximalHariUntukIzin + " days.";
			}
			else 
			{
				return "Cannot process Leave Request of \"Permission "
						+ "Dispensation\" without any dispensation selected.";
			}
		}
		else if(LEAVETYPE_LeaveCuti.equals(getLeaveType()))
		{
			MUNSEmployeeAllowanceRecord allowance = 
					MUNSEmployeeAllowanceRecord.getCreateLeaveReserved(
							getCtx(), getEmployee(), getLeaveDateStart(), 
							get_TrxName());
			if (allowance == null)
				retMsg = "Employee doesn't have Leave "
						+ "Claim Reserved allowance.";
		}
		else if(LEAVETYPE_MaternityHamilPlusMelahirkan.equals(getLeaveType()))
		{
			retMsg = isMaternityLeaveAllowed();
		}
		return retMsg;
	}
	
	/**
	 * 
	 * @return return a not allowed message if Leave-Cuti is not allowed, null otherwise.
	 */
	public String isLeaveCutiAllowed()
	{
		if (!getLeaveType().equals(LEAVETYPE_LeaveCuti))
			return null;
			
		String retMsg = null; 
		
		MUNSEmployee employee = getEmployee();
		
		BigDecimal totalLeaveUsed = Env.ZERO;
		BigDecimal remainingReserved = Env.ZERO;

		int allowedLeaveReq = 0;

		Calendar startDateCal = Calendar.getInstance();
		startDateCal.setTime(getLeaveDateStart());
		int startYear = startDateCal.get(Calendar.YEAR);

		Calendar endDateCal = Calendar.getInstance();
		startDateCal.setTime(getLeaveDateEnd());
		int endYear = endDateCal.get(Calendar.YEAR);
		
		/*
		MUNSYearlyPresenceSummary leaveReservedCurrYear = 
				MUNSYearlyPresenceSummary.getCreate(
						getCtx(), employee, String.valueOf(startYear), get_TrxName());
		*/
		//allowedLeaveReq = leaveReservedCurrYear.getTotalLeaveClaimReserved();
		//totalLeaveUsed = leaveReservedCurrYear.getTotalLeaveUsed(); //getTotalLeaveUsedOfYear();
		remainingReserved = BigDecimal.valueOf(allowedLeaveReq).subtract(totalLeaveUsed);
		
		BigDecimal theLeaveRequestedOfYear = Env.ZERO;
		
		if (startYear == endYear) {
			theLeaveRequestedOfYear = getLeaveRequested();
		}
		else 
		{
			startDateCal.set(startYear, 11, 31);
			Timestamp endOfStartYear = new Timestamp(startDateCal.getTimeInMillis());
			
			theLeaveRequestedOfYear = 
					calculateLeaveRequested(getCtx(), 
											employee, 
											getLeaveDateStart(), 
											endOfStartYear,
											getLeaveType(),
											LEAVEPERIODTYPE_FullDay,
											false,
											get_TrxName());
			if (getLeavePeriodType().equals(LEAVEPERIODTYPE_StartDateIsHalfDay)
				|| getLeavePeriodType().equals(LEAVEPERIODTYPE_StartAndEndDateIsHalfDay))
				theLeaveRequestedOfYear.subtract(new BigDecimal(0.5));
			
		}
		
		if (remainingReserved.compareTo(theLeaveRequestedOfYear) < 0) {
			return "Cannot process leave request for " + theLeaveRequestedOfYear 
					+ " days with leave claim reserved remaining only " + remainingReserved + " days in "
					+ " the fiscal year of " + startYear;
		}
		
		if (startYear != endYear)
		{ // berarti permintaan cuti ada di akhir tahun dan dilanjutkan tahun fiskal berikutnya.
			/*
			MUNSYearlyPresenceSummary yearlyPresence = 
					MUNSYearlyPresenceSummary.getCreate(
							getCtx(), employee, String.valueOf(endYear), get_TrxName());
			*/
			//allowedLeaveReq = yearlyPresence.getTotalLeaveClaimReserved();
			//totalLeaveUsed = yearlyPresence.getTotalLeaveUsed(); //getTotalLeaveUsedOfYear();
			remainingReserved = BigDecimal.valueOf(allowedLeaveReq).subtract(totalLeaveUsed);

			endDateCal.set(endYear, 0, 1);
			Timestamp startOfEndYear = new Timestamp(endDateCal.getTimeInMillis());
			
			theLeaveRequestedOfYear = 
					calculateLeaveRequested(getCtx(), 
											employee, 
											startOfEndYear,
											getLeaveDateEnd(),
											getLeaveType(),
											LEAVEPERIODTYPE_FullDay, 
											false,
											get_TrxName());
			if (getLeavePeriodType().equals(LEAVEPERIODTYPE_EndDateIsHalfDay)
				|| getLeavePeriodType().equals(LEAVEPERIODTYPE_StartAndEndDateIsHalfDay))
				theLeaveRequestedOfYear.subtract(new BigDecimal(0.5));
			
			if (remainingReserved.compareTo(theLeaveRequestedOfYear) < 0) {
				retMsg = "Cannot process leave request for " + theLeaveRequestedOfYear 
						+ " days with leave claim reserved remaining only " + remainingReserved + " days in "
						+ " the fiscal year of " + endYear;
			}
			
		}
		return retMsg;
	}
	
	/**
	 * 
	 * @return return a not allowed message if maternity-leave is not allowed, null otherwise.
	 */
	public String isMaternityLeaveAllowed()
	{
		String retMsg = null; 
		if (getLeaveType().equals(LEAVETYPE_MaternityHamilPlusMelahirkan))
		{
			MUNSEmployee employee = getEmployee();
			
			if (employee.getGender().equals(MUNSEmployee.GENDER_Male))
				return "Male doesn't have permission for Maternity!";
			
			I_C_JobCategory jobCategory = employee.getC_Job().getC_JobCategory();
			
			MUNSLeaveReservedConfig leaveReservedConf = MUNSLeaveReservedConfig.get(
					getCtx(), jobCategory.getC_JobCategory_ID(), employee.getNationality(), get_TrxName());

			
			String sql = "SELECT count(*) FROM UNS_LeavePermissionTrx WHERE UNS_Employee_ID=? AND LeaveType=? AND" +
					" DocStatus IN (?, ?)";
			int prevNumberRequestedMaternity = 
					DB.getSQLValueEx(get_TrxName(), sql, 
								   employee.get_ID(), LEAVETYPE_MaternityHamilPlusMelahirkan,
								   DOCSTATUS_Completed, DOCSTATUS_Closed);
			if (prevNumberRequestedMaternity < 0)
				prevNumberRequestedMaternity = 0;

			if (prevNumberRequestedMaternity+employee.getNumberOfChild() >= leaveReservedConf.getMaxMaternity())
				return "Employee has reached maximum maternity (i.e. = " + 
						prevNumberRequestedMaternity + ")";
			
			int totalMaternityRequested = TimeUtil.getDaysBetween(getLeaveDateStart(), getLeaveDateEnd());
			totalMaternityRequested += 1; // since getDaysBetween for date of 26-28 is 2.
			
			if (leaveReservedConf.getMaternityClaimReserved() < totalMaternityRequested) {
				retMsg = "Maternity leave request cannot greater than maximum maternity reserved of " + 
						leaveReservedConf.getMaternityClaimReserved() + " days.";
			}
		}
		return retMsg;
	}
	
	protected int getTotalLeaveClaimReservedReal(I_UNS_Employee employee) 
	{
		String sql = "SELECT yps.TotalLeaveClaimReserved   " +
				"FROM UNS_YearlyPresenceSummary yps  WHERE yps.UNS_YearlyPresenceSummary_ID= "
				+ getUNS_YearlyPresenceSummary_ID();
		int leaveClaim = DB.getSQLValue(get_TrxName(), sql);
		if(leaveClaim == 0)
		{
			MUNSLeaveReservedConfig leaveConf = 
					MUNSLeaveReservedConfig.get(
							getCtx(),employee.getC_Job().getC_JobCategory_ID() 
							, employee.getNationality(), get_TrxName());
			if(null != leaveConf)
				leaveClaim = leaveConf.getLeaveClaimReserved();
			else
				leaveClaim = 0;
		}
		return leaveClaim;
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocOptions#customizeValidActions(java.lang.String, java.lang.Object, 
	 * java.lang.String, java.lang.String, int, java.lang.String[], java.lang.String[], int)
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
    	
    	// If status = Completed, add "Reactivte" in the list
    	if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
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
	/**COMMENT BY ITD-Andy 05/08/13 
	//Presence Summary has been created before leave
	private void generateMonthlyPresenceSummary(MUNSYearlyPresenceSummary yearly)
	{
		MUNSYearlyPresenceSummary newYearly = new MUNSYearlyPresenceSummary(this);
		newYearly.setAD_Org_ID(getAD_Org_ID());
		newYearly.setUNS_Employee_ID(getUNS_Employee_ID());
		newYearly.setC_Job_ID(EmployeeID.getC_Job_ID());
		newYearly.setEmploymentStatus(EmployeeID.getEmploymentStatus());
		newYearly.setC_BPartner_ID(EmployeeID.getC_BPartner_ID());
		newYearly.setC_Year_ID(getC_Year_ID());
		newYearly.setTotalSK(getTotalSKYear());
		newYearly.setTotalSKK(getTotalSKKYear());
		if (getLeaveType().equals(LEAVETYPE_LeaveCuti))
		{
			newYearly.setTotalLeaveUsed(getTotalLeaveUsedYear());
		}
		
		MUNSLeaveReservedConfig leaveReservedConf = MUNSLeaveReservedConfig.get(
				getCtx(), EmployeeID.getC_Job().getC_JobCategory_ID(), 
				EmployeeID.getNationality(), get_TrxName());
		newYearly.setTotalLeaveClaimReserved(leaveReservedConf.getLeaveClaimReserved());
		
		if (newYearly.save())
		{
			setUNS_YearlyPresenceSummary_ID(newYearly.get_ID());
			MUNSMonthlyPresenceSummary newMonthlyPresenceSummary =
					new MUNSMonthlyPresenceSummary(newYearly);
			newMonthlyPresenceSummary.setC_Period_ID(getC_Period_ID());
			newMonthlyPresenceSummary.setUNS_Employee_ID(getUNS_Employee_ID());
			newMonthlyPresenceSummary.setC_Job_ID(EmployeeID.getC_Job_ID());
			newMonthlyPresenceSummary.setEmploymentStatus(EmployeeID.getEmploymentStatus());
			newMonthlyPresenceSummary.setC_BPartner_ID(EmployeeID.getC_BPartner_ID());
//			newMonthlyPresenceSummary.setHourPerDay(EmployeeID.getHourPerDay());
			newMonthlyPresenceSummary.setNoWorkDay(EmployeeID.getNoWorkDay());
			newMonthlyPresenceSummary.setTotalSK(getTotalSKMonth());
			newMonthlyPresenceSummary.setTotalSKK( getTotalSKKMonth());
			if (!newMonthlyPresenceSummary.save())
			{
				m_processMsg = "Failed to create Monthly Presence Summary";
			}
			GenerateDailyRecord createDailyPresence = new GenerateDailyRecord(
					getCtx(), newMonthlyPresenceSummary.get_ID(), get_TrxName());
			String msg = createDailyPresence.generateDailyPresence();
			if (null != msg)
				throw new AdempiereUserError(msg);
		}		
	}
	*/
	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() 
	{
		// 
		log.info(toString());
		
		//if (getJobCareTaker_ID()==0)
		//	throw new FillMandatoryException(COLUMNNAME_JobCareTaker_ID);
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
	
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
			
		// Cek Leave Permission Request.
		String notAllowMsg = isRequestedLeaveAllowed();
		
		if (notAllowMsg != null && !notAllowMsg.isEmpty()) {
			m_processMsg = notAllowMsg;
			return DOCSTATUS_Invalid;
		}
		
		if (LEAVETYPE_LeaveCuti.equals(getLeaveType()))
		{
			MUNSEmployeeAllowanceRecord allowance = 
					MUNSEmployeeAllowanceRecord.getCreateLeaveReserved(
							getCtx(), getEmployee(), getLeaveDateStart(), get_TrxName());
			
			BigDecimal leaveReservedRemaining = 
					allowance.getLeaveClaimReserved().subtract(allowance.getLeaveReservedUsed());
			if (leaveReservedRemaining.compareTo(getLeaveRequested()) < 0)
			{
				String msg = Msg.getMsg(getCtx(), "ProcessedRequestMsg");
				String title = Msg.getMsg(getCtx(), "ProcessedRequestTitle");
				int answered = MessageBox.showMsg(this,
						getCtx(), msg, title, MessageBox.YESNO, MessageBox.ICONWARNING);
				if (answered == MessageBox.RETURN_NO)
				{
					m_processMsg = "Aborted.";
					return DOCSTATUS_Drafted;
				}
			}
		}
		/*
		MUNSDailyPresence dailyPresence = MUNSDailyPresence.getByDate(
				getCtx(), getLeaveDateStart(), getUNS_Employee_ID(),
				 get_TrxName());
		
		MUNSMonthlyPresenceSummary monthDate = dailyPresence.getParent();
		
		MUNSMonthlyPresenceSummary payrollMonth = 
		MUNSMonthlyPresenceSummary.get(getCtx(), 
				getUNS_Employee_ID(), getC_Period_ID(), getAD_Org_ID(), 
				get_TrxName());
		
		if (monthDate.get_ID() != payrollMonth.get_ID())
		{
			m_processMsg = " Leave Date Start not in payroll periode";
			log.log(Level.SEVERE, m_processMsg);
			return DocAction.STATUS_Invalid;
		}
		*/
		MUNSLeavePermissionTrx leaveStart = get(
				getCtx(), getUNS_Employee_ID(), getLeaveDateStart(),
				get_TrxName());
		MUNSLeavePermissionTrx leaveEnd = get(
				getCtx(), getUNS_Employee_ID(), getLeaveDateEnd(),
				get_TrxName());
		
		if (leaveStart != null || leaveEnd != null){
			m_processMsg = "Employee has had leave permission recorded at " +
					new SimpleDateFormat("yyyy-MM-dd").format(
							getLeaveDateStart()) + " - " +
					new SimpleDateFormat("yyyy-MM-dd").format(
							getLeaveDateEnd());
			return DocAction.STATUS_Invalid;	
		}		
		
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		
		m_justPrepared = true;
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {
		// 
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_COMPLETE);
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
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		MUNSEmployee employee = getEmployee();
		employee.setLastLeaveStart(getLeaveDateStart());
		employee.setLastLeaveEnd(getLeaveDateEnd());
		employee.saveEx();
		MUNSDailyPresence.updateDailyPresence(this);
		
		if (LEAVETYPE_LeaveCuti.equals(getLeaveType()))
		{
			MUNSEmployeeAllowanceRecord allowance = 
					MUNSEmployeeAllowanceRecord.getCreateLeaveReserved(
							getCtx(), getEmployee(), getLeaveDateStart(),
							get_TrxName());

			if (allowance == null)
			{
				m_processMsg = "Cannot find Leave Claim Reserved configuration "
						+ "for the employee's position.";
				return DOCSTATUS_Invalid;
			}
			
			BigDecimal theLeaveRequested = 
					calculateLeaveRequested(getCtx(), 
											employee, 
											getLeaveDateStart(), 
											getLeaveDateEnd(), 
											getLeaveType(),
											LEAVEPERIODTYPE_FullDay,
											false,
											get_TrxName());
			if (getLeavePeriodType().equals(LEAVEPERIODTYPE_EndDateIsHalfDay)
				|| getLeavePeriodType().equals(
						LEAVEPERIODTYPE_StartDateIsHalfDay))
				theLeaveRequested.subtract(new BigDecimal(0.5));
			else if (getLeavePeriodType().equals(
					LEAVEPERIODTYPE_StartAndEndDateIsHalfDay))
				theLeaveRequested.subtract(Env.ONE);
			
			allowance.setLeaveReservedUsed(allowance.getLeaveReservedUsed().
					add(theLeaveRequested));
			allowance.saveEx();
			/*
			Calendar startDateCal = Calendar.getInstance();
			startDateCal.setTime(getLeaveDateStart());
			int startYear = startDateCal.get(Calendar.YEAR);

			Calendar endDateCal = Calendar.getInstance();
			startDateCal.setTime(getLeaveDateEnd());
			int endYear = endDateCal.get(Calendar.YEAR);
			
			BigDecimal theLeaveRequestedOfYear = Env.ZERO;
			
			if (startYear == endYear) {
				theLeaveRequestedOfYear = getLeaveRequested();
			}
			else 
			{
				startDateCal.set(startYear, 11, 31);
				Timestamp endOfStartYear = new Timestamp(startDateCal.getTimeInMillis());
				
				theLeaveRequestedOfYear = 
						calculateLeaveRequested(getCtx(), 
												employee, 
												getLeaveDateStart(), 
												endOfStartYear, 
												getLeaveType(),
												LEAVEPERIODTYPE_FullDay,
												false,
												get_TrxName());
				if (getLeavePeriodType().equals(LEAVEPERIODTYPE_StartDateIsHalfDay)
					|| getLeavePeriodType().equals(LEAVEPERIODTYPE_StartAndEndDateIsHalfDay))
					theLeaveRequestedOfYear.subtract(new BigDecimal(0.5));
				
			}
			
			MUNSYearlyPresenceSummary currYearPresence = 
					(MUNSYearlyPresenceSummary) getUNS_YearlyPresenceSummary();

			//currYearPresence.setTotalLeaveUsed(currYearPresence.getTotalLeaveUsed().add(theLeaveRequestedOfYear));
			currYearPresence.saveEx();
			
			if (startYear != endYear)
			{ // berarti permintaan cuti ada di akhir tahun dan dilanjutkan tahun fiskal berikutnya.
				MUNSYearlyPresenceSummary nextYearPresence = 
						MUNSYearlyPresenceSummary.getCreate(
								getCtx(), employee, String.valueOf(endYear), get_TrxName());
				
				BigDecimal totalLeaveUsed = nextYearPresence.getTotalLeaveUsed(); //getTotalLeaveUsedOfYear();

				endDateCal.set(endYear, 0, 1);
				Timestamp startOfEndYear = new Timestamp(endDateCal.getTimeInMillis());
				
				theLeaveRequestedOfYear = 
						calculateLeaveRequested(getCtx(), 
												employee, 
												startOfEndYear,
												getLeaveDateEnd(),
												getLeaveType(),
												LEAVEPERIODTYPE_FullDay,
												false,
												get_TrxName());
				if (getLeavePeriodType().equals(LEAVEPERIODTYPE_EndDateIsHalfDay)
					|| getLeavePeriodType().equals(LEAVEPERIODTYPE_StartAndEndDateIsHalfDay))
					theLeaveRequestedOfYear.subtract(new BigDecimal(0.5));
				
				nextYearPresence.setTotalLeaveUsed(totalLeaveUsed.add(theLeaveRequestedOfYear));
				nextYearPresence.saveEx();
			}
			*/
		}
		
		if (getJobCareTaker_ID() > 0)
		{
			try
			{
				doProcessJobCareTaker(DOCACTION_Complete);
			}
			catch (Exception e)
			{
				m_processMsg = e.getMessage();
				return DocAction.STATUS_Invalid;
			}
		}
		
		setProcessed(true);	
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		BigDecimal countLCuti = Env.ZERO;
		
		MUNSEmployee employee = getEmployee();
		
		if(!MUNSEmployee.EMPLOYMENTTYPE_SubContract.equals(getUNS_Employee().getEmploymentType()))
		{
			employee.setLastLeaveStart(getLeaveDateStart());
			employee.setLastLeaveEnd(getLeaveDateEnd());
			employee.saveEx();
			
			countLCuti = MUNSDailyPresence.updateDailyPresence(this);
		}
		
		if (LEAVETYPE_LeaveCuti.equals(getLeaveType()))
		{
			MUNSEmployeeAllowanceRecord allowance = 
					MUNSEmployeeAllowanceRecord.getCreateLeaveReserved(
							getCtx(), getEmployee(), getLeaveDateStart(), get_TrxName());

			if (allowance == null)
				throw new AdempiereException("Cannot find Leave Claim Reserved configuration for the employee's position.");
			
			allowance.setLeaveReservedUsed(allowance.getLeaveReservedUsed().subtract(countLCuti));
			allowance.saveEx();
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		setDocAction(DOCACTION_None);
		setDocStatus(STATUS_Voided);
		return true;
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
	
	public static MUNSLeavePermissionTrx get(Properties ctx
			, int UNS_Employee_ID, Timestamp date, String trxName)
	{
		//Modified by ITD-Andy 13/08/2013
		String sDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return Query.get(
				ctx, UNSHRMModelFactory.getExtensionID()
				, Table_Name 
				, COLUMNNAME_UNS_Employee_ID + " = " + UNS_Employee_ID 
				+ " AND '" + sDate + "' BETWEEN " + COLUMNNAME_LeaveDateStart 
				+ " AND " + COLUMNNAME_LeaveDateEnd
				+ " AND " + COLUMNNAME_DocStatus + " IN ('CO', 'CL')"
				+ " AND " + COLUMNNAME_IsActive + " = 'Y'", 
				trxName)
				.firstOnly();
	}
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @param C_Period_ID
	 * @param trxName
	 * @return
	 */
	public static List<MUNSLeavePermissionTrx> gets(
			Properties ctx, int UNS_Employee_ID, int C_Period_ID, String trxName)
	{
		return Query.get(
				ctx, UNSHRMModelFactory.getExtensionID()
				, Table_Name
				, COLUMNNAME_UNS_Employee_ID + " = " + UNS_Employee_ID 
				+ " AND " + COLUMNNAME_C_Period_ID + " = " + C_Period_ID 
				+ " AND " + COLUMNNAME_IsActive + " = 'Y'"
				, trxName).list();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isPayableLeavePermission()
	{
		if(LEAVETYPE_Other.equals(getLeaveType()) || LEAVETYPE_PayPermissionIzinPotongGaji.equals(getLeaveType()))
			return false;
		else if(LEAVETYPE_PermissionDispensationIzinDibayar.equals(getLeaveType()))
		{
			int dispensation_ID = getUNS_DispensationConfig_ID();
			if(dispensation_ID > 0)	
			{
				MUNSDispensationConfig dispensation = (MUNSDispensationConfig) getUNS_DispensationConfig();
				I_UNS_Contract_Recommendation contract = getEmployee().getUNS_Contract_Recommendation();
				if(dispensation.isPayableFor(contract.getNextContractType()))
					return true;
				else
					return false;
			} 
			else {
				return false;
			}
		}
		return true;
	}

	
	/**
	 * Recalculate number of days of leave to be requested based on start date, end date and leave period type
	 * of the leave permission.
	 *  
	 * @return
	 */
	public BigDecimal getLeaveRequested(boolean recalculate) 
	{
		if (DOCSTATUS_Closed.equals(getDocStatus()) || DOCSTATUS_Completed.equals(getDocStatus()))
			return super.getLeaveRequested();
		
		if ((!recalculate && m_dLeaveRequested.signum() > 0))
			return m_dLeaveRequested;

		m_dLeaveRequested = 
				calculateLeaveRequested(getCtx(), 
									   getEmployee(), 
									   getLeaveDateStart(), 
									   getLeaveDateEnd(),
									   getLeaveType(),
									   getLeavePeriodType(),
									   MUNSDispensationConfig.isKeguguran(getUNS_DispensationConfig()),
									   get_TrxName());
		return m_dLeaveRequested;
	}

	/**
	 * 
	 */
	public BigDecimal getLeaveRequested() 
	{
		return getLeaveRequested(false);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param employee
	 * @param dateStart
	 * @param dateEnd
	 * @param leavePeriodType
	 * @param trxName
	 * @return
	 */
	public static BigDecimal calculateLeaveRequested(Properties ctx, 
													 MUNSEmployee employee, 
													 Timestamp dateStart, 
													 Timestamp dateEnd,
													 String leaveType,
													 String leavePeriodType,
													 boolean isKeguguran,
													 String trxName) 
	{
		if (dateEnd == null)
			dateEnd = dateStart;
		
		if (LEAVETYPE_MaternityHamilPlusMelahirkan.equals(leaveType)
			|| (LEAVETYPE_PermissionDispensationIzinDibayar.equals(leaveType)
				&& isKeguguran))
		{
			int requestedLeave = TimeUtil.getDaysBetween(dateStart, dateEnd);
			return BigDecimal.valueOf(requestedLeave + 1);
		}
		
		Calendar leaveCalStart = Calendar.getInstance();
		leaveCalStart.setTime(dateStart);
//		leaveCalStart.set(Calendar.AM, Calendar.AM);
//		leaveCalStart.set(Calendar.HOUR, 0);
//		leaveCalStart.set(Calendar.MINUTE, 1);
		
		Calendar leaveCalEnd = Calendar.getInstance();
		leaveCalEnd.setTime(dateEnd);
		leaveCalEnd.set(Calendar.PM, Calendar.PM);
		leaveCalEnd.set(Calendar.HOUR, 11);
		leaveCalEnd.set(Calendar.MINUTE, 58);
		
		BigDecimal leaveRequested = Env.ZERO;//new BigDecimal (TimeUtil.getDaysBetween(dateStart, dateEnd));
		dateEnd = TimeUtil.addDays(dateEnd, 1);
		
		while(dateStart.before(dateEnd))
		{
			int leaveDay = leaveCalStart.get(Calendar.DAY_OF_WEEK);
			
			if (employee.isHoliday(leaveDay) || MNonBusinessDay.isNonBusinessDay(ctx, dateStart, employee.get_ID(), trxName)) 
			{
				leaveCalStart.add(Calendar.DATE, 1);
				dateStart = TimeUtil.addDays(dateStart, 1);
				continue;
			}
			
			//Timestamp leaveTS = new Timestamp(leaveCalStart.getTimeInMillis());
			//if (MNonBusinessDay.isNonBusinessDay(ctx, dateStart, trxName))
			//	continue;
			
			leaveRequested = leaveRequested.add(Env.ONE);
			leaveCalStart.add(Calendar.DATE, 1);
			dateStart = TimeUtil.addDays(dateStart, 1);
		}
		
		if (leaveRequested.signum() < 1)
			return leaveRequested;
		
		if (leavePeriodType == null)
		{
			; // do nothing.
		}
		else if (MUNSLeavePermissionTrx.LEAVEPERIODTYPE_FullDay.equals(leavePeriodType))
			;
		else if (MUNSLeavePermissionTrx.LEAVEPERIODTYPE_StartDateIsHalfDay.equals(leavePeriodType)
				|| MUNSLeavePermissionTrx.LEAVEPERIODTYPE_EndDateIsHalfDay.equals(leavePeriodType))
			leaveRequested = leaveRequested.subtract(new BigDecimal (0.5));
		else if (MUNSLeavePermissionTrx.LEAVEPERIODTYPE_StartAndEndDateIsHalfDay.equals(leavePeriodType))
			leaveRequested = leaveRequested.subtract(Env.ONE);
		
		return leaveRequested;
	}
	
	public void doProcessJobCareTaker (String action) throws Exception
	{
		MUNSJobCareTaker careTaker = MUNSJobCareTaker.get(
				get_TrxName(), getUNS_Employee_ID(), getJobCareTaker_ID(), 
				getLeaveDateStart(), getLeaveDateEnd());
		
		if (null == careTaker && !DOCACTION_Void.equals(action))
		{
			careTaker = new MUNSJobCareTaker(getCtx(), 0, get_TrxName());
			careTaker.setAD_Org_ID(getAD_Org_ID());
			careTaker.setDateFrom(getLeaveDateStart());
			careTaker.setDateTo(getLeaveDateEnd());
			careTaker.setDescription(getRemarks());
			careTaker.setJobCareTaker_ID(getJobCareTaker_ID());
			careTaker.setUNS_Employee_ID(getUNS_Employee_ID());
			careTaker.setProcessInfo(getProcessInfo());
			careTaker.setGridTab(getGridTab());
			careTaker.saveEx();
		}
		
		if (!careTaker.isComplete())
		{
			boolean ok = careTaker.processIt(action);
			if (!ok)
			{
				throw new AdempiereException("Could not complete Job Care " 
						+ " Taker Document. " + careTaker.getProcessMsg());
			}
		}
	}
}
