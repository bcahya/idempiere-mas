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
import org.compiere.model.MDocType;
import org.compiere.model.MOrg;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.uns.util.ErrorMsg;
import com.uns.util.UNSTimeUtil;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSHalfPeriodPresence extends X_UNS_HalfPeriod_Presence implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3072793214498259054L;
	private MUNSWorkerPresence[] m_Lines = null;

	/**
	 * @param ctx
	 * @param UNS_HalfPeriod_Presence_ID
	 * @param trxName
	 */
	public MUNSHalfPeriodPresence(Properties ctx,
			int UNS_HalfPeriod_Presence_ID, String trxName) {
		super(ctx, UNS_HalfPeriod_Presence_ID, trxName);
		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSHalfPeriodPresence(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}

	/**
	 * 
	 * @param ctx
	 * @param AD_Org_ID
	 * @param Labor_ID
	 * @param UNS_Job_Role_ID
	 * @param isSupervised
	 * @param cutOffWeekDay
	 * @param date
	 * @param workCenter_ID
	 * @param payrollTerm
	 * @param trxName
	 * @return
	 */
	public static MUNSHalfPeriodPresence getCreate(
			Properties ctx, int AD_Org_ID, int Labor_ID, int UNS_Job_Role_ID, boolean isSupervised,
			int cutOffWeekDay, Timestamp date, int workCenter_ID, String payrollTerm, String trxName)
	{
		MUNSHalfPeriodPresence retPresence =  
				get(ctx, Labor_ID, date, AD_Org_ID, workCenter_ID, trxName);
		
		if (retPresence != null)
			return retPresence;
		
		MUNSEmployee worker = new MUNSEmployee(ctx, Labor_ID, trxName);
		MPeriod period = MPeriod.get(ctx, date, AD_Org_ID, trxName);
		
		retPresence = new MUNSHalfPeriodPresence(ctx, 0, trxName);
		retPresence.setAD_Org_ID(AD_Org_ID);
		retPresence.setC_DocType_ID(1000089);
		retPresence.setC_Period_ID(period.getC_Period_ID());
		retPresence.setUNS_Employee_ID(Labor_ID);
		retPresence.setIsSupervised(isSupervised);
		retPresence.setC_BPartner_ID(worker.getC_BPartner_ID());
		retPresence.setUNS_Resource_ID(workCenter_ID);
		retPresence.setUNS_Job_Role_ID(UNS_Job_Role_ID);
		retPresence.setVendor_ID(worker.getVendor_ID());
		
		if (payrollTerm.equals(MUNSWorkerPresence.PAYROLLTERM_HarianBulanan))
		{
			retPresence.setHalfPeriodNo(MUNSHalfPeriodPresence.HALFPERIODNO_FullMonth);
			retPresence.setStartDate(period.getStartDate());
			retPresence.setEndDate(TimeUtil.addDays(period.getStartDate(), 14));
		}
		else if (payrollTerm.equals(MUNSWorkerPresence.PAYROLLTERM_2Weekly))
		{
			Timestamp halfPeriodDate = TimeUtil.addDays(period.getStartDate(), 14);
			if (halfPeriodDate.after(date))
			{
				retPresence.setHalfPeriodNo(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod2);
				retPresence.setStartDate(TimeUtil.addDays(period.getStartDate(), 15));
				retPresence.setEndDate(period.getEndDate());
			}
			else {
				retPresence.setHalfPeriodNo(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod1);
				retPresence.setStartDate(period.getStartDate());
				retPresence.setEndDate(TimeUtil.addDays(period.getStartDate(), 14));
			}
		}
		else { // It is weekly payroll term.
			Timestamp currCutOffWeekDay = UNSTimeUtil.getNextCutOffWeekDay(date, cutOffWeekDay);
			
			Timestamp startDate = UNSTimeUtil.addDays(currCutOffWeekDay, -6);
			int period_ID = MPeriod.getC_Period_ID(ctx, currCutOffWeekDay, AD_Org_ID);
			
			retPresence.setC_Period_ID(period_ID);
			retPresence.setStartDate(startDate);
			retPresence.setEndDate(currCutOffWeekDay);

			Calendar cal = Calendar.getInstance();
			cal.setTime(currCutOffWeekDay);
			
			int weekNo = cal.get(Calendar.WEEK_OF_MONTH);
			
			switch (weekNo) {
			case 1 : retPresence.setHalfPeriodNo(MUNSHalfPeriodPresence.HALFPERIODNO_Week1);
				break;
			case 2 : retPresence.setHalfPeriodNo(MUNSHalfPeriodPresence.HALFPERIODNO_Week2);
				break;
			case 3 : retPresence.setHalfPeriodNo(MUNSHalfPeriodPresence.HALFPERIODNO_Week3);
				break;
			case 4 : retPresence.setHalfPeriodNo(MUNSHalfPeriodPresence.HALFPERIODNO_Week4);
				break;
			case 5 : retPresence.setHalfPeriodNo(MUNSHalfPeriodPresence.HALFPERIODNO_Week5);
				break;
			}
		}
		/*
		
		if (halfPeriodType.equals(MUNSProductionPayConfig.TARGETPERIOD_1Month) 
			|| halfPeriodType.equals(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod1))
		{
			retPresence.setStartDate(period.getStartDate());
			if (halfPeriodType.equals(MUNSHalfPeriodPresence.HALFPERIODNO_FullMonth))
				retPresence.setEndDate(period.getEndDate());
			else
			retPresence.setEndDate(TimeUtil.addDays(period.getStartDate(), 14));
		}
		else if (halfPeriodType.equals(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod2)) {
			retPresence.setStartDate(TimeUtil.addDays(period.getStartDate(), 14));
			retPresence.setEndDate(period.getEndDate());
		}
		*/
		retPresence.saveEx();
		
		return retPresence;
	} // getCreate	
	
	/**
	 * 
	 * @param requery
	 * @return MUNSWorkerPresence[]
	 */
	public MUNSWorkerPresence[] getLines(boolean requery)
	{
		if (null != m_Lines && !requery)
		{
			set_TrxName(m_Lines, get_TrxName());
			return m_Lines;
		}
		
		String whereClause = MUNSWorkerPresence.COLUMNNAME_UNS_HalfPeriod_Presence_ID + " =?";
		List<MUNSWorkerPresence> list = Query.get(
				getCtx(), UNSHRMModelFactory.EXTENSION_ID, MUNSWorkerPresence.Table_Name
				, whereClause, get_TrxName())
				.setParameters(getUNS_HalfPeriod_Presence_ID())
				.list();
		
		m_Lines = new MUNSWorkerPresence[list.size()];
		return list.toArray(m_Lines);
	}
	
	
	private boolean deleteLines()
	{
		for (MUNSWorkerPresence workerPresence : getLines(false))
		{
			if (!workerPresence.delete(true))
			{
				ErrorMsg.setErrorMsg(getCtx(), "DeleteError", "Failed To Delete Lines " 
												+ workerPresence.getUNS_Worker_Presence_ID());
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	protected boolean beforeDelete()
	{
		if (!deleteLines())
			return false;
		return true;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (is_ValueChanged(COLUMNNAME_IsActive))
		{
			for (MUNSWorkerPresence workerPresence : getLines(false))
			{
				workerPresence.setIsActive(isActive());
				workerPresence.save();
			}
		}
		
		for(MDocType cDocType : MDocType.getOfDocBaseType(getCtx(), "PRE")){
			if (cDocType.isSOTrx())
				setC_DocType_ID(cDocType.getC_DocType_ID());
		}
	
		return true;
	}
	
	public String generateWorkerPresence()
	{
		String msg = null;
		if (!deleteLines())
			return "Failed to delete lines";
		MOrg org = MOrg.get(getCtx(), getAD_Org_ID());
		MUNSPayrollConfiguration payrollConfig = MUNSPayrollConfiguration.get(
								getCtx(), getMPeriod(), get_TrxName());
		if (null == payrollConfig)
			throw new AdempiereUserError("" +
					"please define payroll configuration for perion " + getMPeriod().getName() 
					+ " and departement " + org.getName());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(getMPeriod().getStartDate().getTime());
		int totalDayOfMont = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int median = totalDayOfMont / 2;
		int startPeriod = payrollConfig.getPayrollDateStart();
		int endPeriod = payrollConfig.getPayrollDateEnd();
		if (startPeriod > median)
			calendar.add(Calendar.MONTH, -1);
		
		calendar.set(Calendar.DATE, startPeriod);
		Calendar calendarHalfPeriod = Calendar.getInstance();
		if (getHalfPeriodNo().equals(HALFPERIODNO_HalfPeriod1))
		{
			calendar.set(Calendar.DATE, startPeriod);
			calendarHalfPeriod = (Calendar)calendar.clone();
			calendarHalfPeriod.add(Calendar.DAY_OF_YEAR, median);
		}
		if (getHalfPeriodNo().equals(HALFPERIODNO_HalfPeriod2))
		{
			calendar.add(Calendar.DAY_OF_YEAR, median+1);
			calendarHalfPeriod = (Calendar)calendar.clone();
			calendarHalfPeriod.set(Calendar.DATE, endPeriod);
		}
		
		while (calendar.get(Calendar.DAY_OF_YEAR) != calendarHalfPeriod.get(Calendar.DAY_OF_YEAR)+1)
		{
			MUNSWorkerPresence wp = new MUNSWorkerPresence(getCtx(), 0, get_TrxName());
			wp.setUNS_HalfPeriod_Presence_ID(getUNS_HalfPeriod_Presence_ID());
			wp.setWorkDate(new Timestamp(calendar.getTimeInMillis()));
			wp.setDayType();
			wp.setAD_Org_ID(getAD_Org_ID());
			wp.setPresenceStatus();
			wp.setReceivableAmt(BigDecimal.ZERO);
			if (!wp.save())
			{
				msg = "Can't create worker presence";
				throw new AdempiereUserError(msg);
			}
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		return msg;
	}
	
	public MPeriod getMPeriod()
	{
		return (MPeriod)getC_Period();
	}
	
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @param date
	 * @param AD_Org_ID
	 * @param workCenter_ID the work center.
	 * @param trxName
	 * @return MUNSHalfPeriodPresence
	 */
	public static MUNSHalfPeriodPresence get(
			Properties ctx, int UNS_Employee_ID, Timestamp date, 
			int AD_Org_ID, int workCenter_ID, String trxName)
	{
		String whereClause = "UNS_Employee_ID = ? AND '" + 
				new SimpleDateFormat("yyyy-MM-dd").format(date) + "' BETWEEN StartDate AND EndDate " +
				"AND AD_Org_ID=? AND UNS_Resource_ID=?";
		
		return Query.get(ctx, UNSHRMModelFactory.EXTENSION_ID, MUNSHalfPeriodPresence.Table_Name,
						 whereClause, trxName)
				.setParameters(UNS_Employee_ID, AD_Org_ID, workCenter_ID)
				.first();
	}
	
	/**
	 * 
	 * @param date
	 * @param UNS_SlotType_ID
	 * @return
	 */
	public MUNSWorkerPresence getWorkerPresence(Timestamp date, int UNS_Resource_ID)
	{
		return Query.get(
				getCtx(), UNSHRMModelFactory.EXTENSION_ID, MUNSWorkerPresence.Table_Name, 
				"UNS_HalfPeriod_Presence_ID=? AND WorkDate=?", get_TrxName())
				.setParameters(getUNS_HalfPeriod_Presence_ID(), date)
				.firstOnly();
	}


	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		
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

	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	
	@Override
	public boolean processIt(String action) throws Exception {
		
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		
		log.info(toString());
		setProcessed(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
	
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
			m_justPrepared = true;
			
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		
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
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		setProcessed(true);	
		//m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		
		return false;
	}

	@Override
	public boolean closeIt() {
		
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

	@Override
	public boolean reverseCorrectIt() {
		
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		
		return false;
	}

	@Override
	public boolean reActivateIt() {
		
		return false;
	}

	@Override
	public String getSummary() {
		
		return null;
	}

	@Override
	public String getDocumentNo() {
		
		return null;
	}

	@Override
	public String getDocumentInfo() {
		
		return null;
	}

	@Override
	public File createPDF() {
		
		return null;
	}

	@Override
	public String getProcessMsg() {
		
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		
		return null;
	}
	
	/**
	 * 
	 */
	public void setP_Obat(MUNSPayrollConfiguration payConfig)
	{
		BigDecimal totalBiayaBerobat1Period = MUNSMedicalRecord.getTotalBiayaBerobat1Period(
				getUNS_Employee_ID(), payConfig, getC_Period(), get_TrxName());
		if (null != totalBiayaBerobat1Period)
			setP_Obat(totalBiayaBerobat1Period);
	}
	
	/**
	 * 
	 */
	public void initLoanInstallment()
	{
		List<MUNSEmployeeLoan> listOfEmployeeLoan = MUNSEmployeeLoan.gets(
				getCtx(), getUNS_Employee_ID(), get_TrxName());
		for (MUNSEmployeeLoan employeeLoan : listOfEmployeeLoan)
		{
			if (employeeLoan.getLoanAmtLeft().compareTo(BigDecimal.ZERO) <= 0)
				continue;
			
			if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Company))
				setP_PinjamanKaryawan(employeeLoan.getInstallment());
			else if (employeeLoan.getLoanType().equals(MUNSEmployeeLoan.LOANTYPE_Koperasi))
				setP_Koperasi(employeeLoan.getInstallment());
			
			X_UNS_Loan_Installment loanInstallment = 
					new X_UNS_Loan_Installment(getCtx(), 0, get_TrxName());
			loanInstallment.setAD_Org_ID(employeeLoan.getAD_Org_ID());
			loanInstallment.setUNS_Employee_Loan_ID(employeeLoan.get_ID());
			loanInstallment.setPaidAmt(employeeLoan.getInstallment());
			loanInstallment.setPaidDate(Env.getContextAsDate(getCtx(), "#Date"));
			loanInstallment.save();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String createCriteriaResults()
	{
		MUNSProductionPayConfig payConfig =
				MUNSProductionPayConfig.get(
						getCtx(), getAD_Org_ID()
						, getStartDate()
						, get_TrxName());
		if (null == payConfig)
			throw new AdempiereUserError("Not found payroll configuration");
		int count = 0;
		for(MUNSPayRollLine criteriaLine : payConfig.getPayrollLines(false))
		{
			if(criteriaLine.getUNS_Job_Role_ID() != getUNS_Job_Role_ID())
				continue;
			
			if(criteriaLine.getCriteriaType().equals(
					MUNSPayRollLine.CRITERIATYPE_Product))
				continue;
			
			MUNSCriteriaResult newCriteriaResult = new MUNSCriteriaResult(getCtx(), 0, get_TrxName());
			newCriteriaResult.setAD_Org_ID(getAD_Org_ID());
			newCriteriaResult.setName(criteriaLine.getName());
			newCriteriaResult.setUNS_HalfPeriod_Presence_ID(get_ID());
			newCriteriaResult.setUNS_PayRollLine_ID(criteriaLine.get_ID());
			newCriteriaResult.setDescription("::Auto Generate::");
			newCriteriaResult.setResult(BigDecimal.ZERO);
			count++;
			if(!newCriteriaResult.save())
				throw new AdempiereException("Failed to create new Criteria Result");
		}
		if(count > 0)
			setCreateCriteriaResult("Y");
		save();
		return count + " Criteria result has created";
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		return super.afterSave(newRecord, success);
	}
	
	private MUNSCriteriaResult[] m_Criteriaresult = null;
	public MUNSCriteriaResult[] getCriteriaResults(boolean requery)
	{
		if(null != m_Criteriaresult && !requery)
		{
			set_TrxName(m_Criteriaresult, get_TrxName());
			return m_Criteriaresult;
		}
		
		String whereClause = COLUMNNAME_UNS_HalfPeriod_Presence_ID + " =?";
		List<MUNSCriteriaResult> list = Query.get(
				getCtx(), 
				UNSHRMModelFactory.getExtensionID(), 
				MUNSCriteriaResult.Table_Name, 
				whereClause, 
				get_TrxName())
				.setParameters(getUNS_HalfPeriod_Presence_ID())
				.list();
		
		m_Criteriaresult = new MUNSCriteriaResult[list.size()];
		return list.toArray(m_Criteriaresult);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isGeneratePay()
	{
		return getUpdateHalfPeriodPresence().equalsIgnoreCase("Y");
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isCreateCriteriraResult()
	{
		return getCreateCriteriaResult().equals("Y");
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean deleteCriteriaResult()
	{
		for(MUNSCriteriaResult criteriaResult : getCriteriaResults(false))
		{
			if(!criteriaResult.delete(true))
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param payConfig
	 * @return
	 */
	public boolean setPayToDaily(MUNSProductionPayConfig payConfig)
	{
		setIsSwitchTodaily(true);
		double dailyPay = payConfig.getPayFullDay(isSupervised()).doubleValue();
		double totalPaid = getTotalPresence().doubleValue() * dailyPay;
		totalPaid += getTotalPayableAbsence().doubleValue() * dailyPay;
		setPaidDaily(new BigDecimal(totalPaid));
		return true;
	}
	
	/**
	 * 
	 * @param presence
	 * @return
	 */
	public BigDecimal getPresence(String presence)
	{
		BigDecimal totalPresence = Env.ZERO;
		if(presence.equals(MUNSPayRollPremiTarget.ABSENTYPE_Mangkir))
			totalPresence = getTotalMangkir();
		else if(presence.equals(MUNSPayRollPremiTarget.ABSENTYPE_NonPayableDispensation))
			totalPresence = getTotalNonPayableAbsence().add(getTotalMangkir());
		else if(presence.equals(MUNSPayRollPremiTarget.ABSENTYPE_PayableDispensation))
			totalPresence = getTotalPayableAbsence()
			.add(getTotalSKK()).add(getTotalNonPayableAbsence()).add(getTotalMangkir());
		else if(presence.equals(MUNSPayRollPremiTarget.ABSENTYPE_Sakit))
			totalPresence = getTotalSK();
		else if(presence.equals(MUNSPayRollPremiTarget.ABSENTYPE_AllAbsenceType))
			totalPresence = getTotalSK().add(getTotalSKK().add(getTotalPayableAbsence()
					.add(getTotalNonPayableAbsence().add(getTotalMangkir()))));
		return totalPresence;
	}
	
	/**
	 * 
	 * @param date
	 * @param UNS_Resource_ID
	 * @return
	 */
	public boolean createAbsence(Timestamp date, int UNS_Resource_ID)
	{
		MUNSLeavePermissionTrx leavePerm =
				MUNSLeavePermissionTrx.get(
						getCtx(), 
						getUNS_Employee_ID(), 
						date, 
						get_TrxName());
		String AbsenceType = null;
		String presenceStatus = null;
		if(null == leavePerm)
			presenceStatus = MUNSWorkerPresence.PRESENCESTATUS_Mangkir;
		else
		{
			AbsenceType = leavePerm.getLeaveType();
			presenceStatus = MUNSWorkerPresence.PRESENCESTATUS_Izin;
		}
		
		MUNSWorkerPresence wp = getWorkerPresence(date, UNS_Resource_ID);
		if(null == wp)
		{
			wp = new MUNSWorkerPresence(getCtx(), 0, get_TrxName());
			wp.setAD_Org_ID(getAD_Org_ID());
			wp.setUNS_HalfPeriod_Presence_ID(get_ID());
			wp.setWorkDate(date);
			wp.setDayType();
		}
		wp.setUNS_Resource_ID(UNS_Resource_ID);
		wp.setPresenceStatus(presenceStatus);
		wp.setPermissionType(AbsenceType);
		wp.setProductionQty(BigDecimal.ZERO);
		wp.setReceivableAmt(BigDecimal.ZERO);
		if(!wp.save())
			return false;
		
		return true;
	}
	
	public double getGajiBruto()
	{
		double gajiBruto = getTotalReceivableAmt().doubleValue()
				+ getPremiKerajinanReceivable().doubleValue()
				+ getPremiTargetReceivable().doubleValue();
		return gajiBruto;
	}
	
	public double getPotongan()
	{
		double potongan = getP_Koperasi().doubleValue()
				+ getP_Label().doubleValue()
				+ getP_ListrikAir().doubleValue()
				+ getP_Obat().doubleValue()
				+ getP_PinjamanKaryawan().doubleValue();
		return potongan;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public boolean isInRange(Timestamp date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getStartDate().getTime());
		cal.set(Calendar.AM, Calendar.AM);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 1);
		
		long startMillis = cal.getTimeInMillis();
		
		cal.setTimeInMillis(getEndDate().getTime());
		cal.set(Calendar.PM, Calendar.PM);
		cal.set(Calendar.HOUR, 11);
		cal.set(Calendar.MINUTE, 58);
		
		long endMillis = cal.getTimeInMillis();
		
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.AM, Calendar.AM);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 12);
		
		long millisToCompare = cal.getTimeInMillis();
		
		return (startMillis <= millisToCompare && millisToCompare <= endMillis);
	}
}
