/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereSystemError;

import com.uns.util.ErrorMsg;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan, fixed/revised by AzHarr
 *
 */
public class MUNSProductionPayConfig extends X_UNS_ProductionPayConfig implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9040306490168759962L;
	private MUNSPayRollLine[] m_PayrollLines = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private Hashtable<String, MUNSPayRollLine> m_PayrollLineMap = new Hashtable<String, MUNSPayRollLine>();

	/**
	 * @param ctx
	 * @param UNS_ProductionPayConfig_ID
	 * @param trxName
	 */
	public MUNSProductionPayConfig(Properties ctx,
			int UNS_ProductionPayConfig_ID, String trxName) {
		super(ctx, UNS_ProductionPayConfig_ID, trxName);
		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSProductionPayConfig(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
		
	}
	
	/*
	private boolean hasCreatedValidDate()
	{
		String query = "SELECT * FROM " + Table_Name + " WHERE ('" + getValidFrom() 
				+ "' BETWEEN " + COLUMNNAME_ValidFrom + " AND " + COLUMNNAME_ValidTo 
				+ " OR '" + getValidTo() + "' BETWEEN " + COLUMNNAME_ValidFrom 
				+ " AND " + COLUMNNAME_ValidTo
				+ ") AND " + COLUMNNAME_AD_Org_ID + "=" + getAD_Org_ID()
				+ " AND " + COLUMNNAME_EmploymentStatus + " = '" + getEmploymentStatus()
				+ "' AND " + COLUMNNAME_IsActive + "= 'Y' AND " 
				+ COLUMNNAME_UNS_ProductionPayConfig_ID + " <> " + getUNS_ProductionPayConfig_ID();
		int count  = DB.getSQLValue(get_TrxName(), query);
		if (count == -1)
			return false;
		
			ErrorMsg.setErrorMsg(getCtx(), "SaveError", " Valid date with range " + getValidFrom() 
					+ " to " + getValidTo() + " has already exists");
		return true;
	}
	*/
	
	/**
	 * 
	 * @return true if valid from < date valid to
	 */
	private boolean isDateValid()
	{
		long startDateAsLong = getValidFrom().getTime();
		long endDateAsLong = getValidTo().getTime();
		if (endDateAsLong < startDateAsLong)
			ErrorMsg.setErrorMsg(getCtx(), "SaveError","Valid to harus tanggal sesudah valid from");
		return true;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		//if (hasCreatedValidDate())
		//	return false;
		if (!isDateValid())
			return false;
		if (is_ValueChanged(COLUMNNAME_IsActive))
			setIsActiveForAllLines();
		return super.beforeSave(newRecord);
	}
	
	/**
	 * 
	 * @param requery
	 * @return MUNSPayRollLine[]
	 */
	public MUNSPayRollLine[] getPayrollLines(boolean requery)
	{
		if (null != m_PayrollLines && !requery)
		{
			set_TrxName(m_PayrollLines, get_TrxName());
			return m_PayrollLines;
		}
		String whereClause = MUNSPayRollLine.COLUMNNAME_UNS_ProductionPayConfig_ID + "=?";
		List<MUNSPayRollLine> list = Query.get(
				getCtx(), UNSHRMModelFactory.EXTENSION_ID, X_UNS_PayRollLine.Table_Name
				, whereClause, get_TrxName())
				.setParameters(getUNS_ProductionPayConfig_ID())
				.list();
		m_PayrollLines = new MUNSPayRollLine[list.size()];
		return list.toArray(m_PayrollLines);
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

	@Override
	public boolean processIt(String action) throws Exception {
		
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		
		log.info("unlockIt - " + toString());
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
		
		MUNSPayRollLine[] payrollLine = getPayrollLines(false);
		if (null == payrollLine)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		
		// kalo mau cek line2 nya lagi tinggal tambahin aja
		
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
		setProcessed(false);
		return true;
	}

	@Override
	public String completeIt() {
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());
		
		//Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		setProcessed(true);	
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		setDescription("Voided On " + getUpdated());
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		setProcessed(true);
		setDocAction(DOCACTION_None);
		
		return true;
	}

	@Override
	public boolean closeIt() {
		
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;		
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		return voidIt();
	}

	@Override
	public boolean reverseAccrualIt() {
		
		return false;
	}

	@Override
	public boolean reActivateIt() {
		
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;	
		
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;			
		
		setProcessed(false);
		setDocAction(DOCACTION_Prepare);
		
		return true;
	}

	@Override
	public String getSummary() {
		
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(":");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
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
		
		return getUpdatedBy();
	}

	@Override
	public int getC_Currency_ID() {
		
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		
		return null;
	}
	
	@Override
	protected boolean beforeDelete()
	{
		try{
		deleteLines();
		} catch (AdempiereSystemError e) {
			log.log(Level.SEVERE, e.toString());
			throw new IllegalArgumentException(e.toString());
		}
		return true;
	}
	
	private void deleteLines() throws AdempiereSystemError
	{
		for(MUNSPayRollLine payrollLine : getPayrollLines(false))
		{
			if (!payrollLine.delete(true))
				throw new AdempiereSystemError("failed to delete Payroll line " 
							+ payrollLine.getUNS_PayRollLine_ID() + payrollLine.getM_Product().getName());
		}
	}
	
	private void setIsActiveForAllLines()
	{
		for(MUNSPayRollLine payrollLine : getPayrollLines(false))
		{
			payrollLine.setIsActive(isActive());
			payrollLine.save();
		}
	}
	
	/**
	 * @deprecated
	 * @param M_Product_ID
	 * @param jobRole_ID
	 * @return
	 */
	public MUNSPayRollLine getPayrollLineOf(int M_Product_ID, int jobRole_ID)
	{
		for (MUNSPayRollLine payrollLine : getPayrollLines(false))
		{
			if (payrollLine.getM_Product_ID() == M_Product_ID 
					&& payrollLine.getUNS_Job_Role_ID() == jobRole_ID)
				return payrollLine;
		}
		
		return null;
	}
	

	/**
	 * Return an active and first default or first created or null if not found.
	 * 
	 * @param ctx
	 * @param AD_Org_ID
	 * @param payrollTerm
	 * @param date
	 * @param trxName
	 * @return
	 */
	public static MUNSProductionPayConfig get(
			Properties ctx, int AD_Org_ID, Timestamp date, String trxName)
	{
		String whereClause = "AD_Org_ID=? AND ? BETWEEN ValidFrom AND ValidTo";

		List<MUNSProductionPayConfig> payConfigList = Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, whereClause, trxName)
				.setParameters(AD_Org_ID, date)
				.setOnlyActiveRecords(true)
				.setOrderBy(COLUMNNAME_Created)
				.list();
		
		MUNSProductionPayConfig retPayConfig = null;
		
		for (MUNSProductionPayConfig payConfig : payConfigList)
		{
			if (retPayConfig == null)
				retPayConfig = payConfig;
			
			if (payConfig.isDefault()) 
			{
				retPayConfig = payConfig;
				break;
			}
		}
		return retPayConfig;
	}
	
	/**
	 * 
	 * @param UNS_JobRole_ID
	 * @param M_Product_ID
	 * @param C_UOM_ID
	 * @param payCriteria
	 * @param premiBasedCalc
	 * @return
	 */
	public MUNSPayRollLine getCriteriaLine(
			int UNS_JobRole_ID, int M_Product_ID, int C_UOM_ID, String payCriteria, String premiBasedCalc)
	{
		for(MUNSPayRollLine payLine : getPayrollLines(false))
		{
			if(UNS_JobRole_ID > 0 && payLine.getUNS_Job_Role_ID() != UNS_JobRole_ID)
				continue;
			if(M_Product_ID > 0 && payLine.getM_Product_ID() != M_Product_ID)
				continue;
			if(C_UOM_ID > 0 && payLine.getC_UOM_ID() != C_UOM_ID)
				continue;
			if(null != payCriteria && !payCriteria.equals(payLine.getCriteriaType()))
				continue;
			if(null != premiBasedCalc && !premiBasedCalc.equals(payLine.getPremiCalculateBased()))
				continue;
			
			return payLine;
		}
		return null;
	}
	
	/**
	 * 
	 * @param isWorker
	 * @return
	 */
	public BigDecimal getPayFullDay(boolean isWorker)
	{
		BigDecimal pay = BigDecimal.ZERO;
		if(isWorker)
			pay = getSupervisedPayFullDay();
		else
			pay = getSupervisorPayFullDay();
		return pay;		
	}
	
	/**
	 * 
	 * @param isWorker
	 * @return
	 */
	public BigDecimal getPayHalfDay(boolean isWorker)
	{
		BigDecimal pay = BigDecimal.ZERO;
		if(isWorker)
			pay = getSupervisedPayHalfDay();
		else
			pay = getSupervisorPayHalfDay();
		return pay;	
	}
	
	/**
	 * 
	 * @param isWorker
	 * @return
	 */
	public BigDecimal getPayFullHoliday(boolean isWorker)
	{
		BigDecimal pay = BigDecimal.ZERO;
		if(isWorker)
			pay = getSupervisedPayFullHoliday();
		else
			pay = getSupervisorPayFullHoliday();
		return pay;	
	}
	
	/**
	 * 
	 * @param isWorker
	 * @return
	 */
	public BigDecimal getPayHalfHoliday(boolean isWorker)
	{
		BigDecimal pay = BigDecimal.ZERO;
		if(isWorker)
			pay = getSupervisedPayHalfHoliday();
		else
			pay = getSupervisorPayHalfHoliday();
		return pay;	
	}
	
	/**
	 * 
	 * @param isWorker
	 * @return
	 */
	public BigDecimal getPayFullNationalHoliday(boolean isWorker)
	{
		BigDecimal pay = BigDecimal.ZERO;
		if(isWorker)
			pay = getSupervisedPayFullNHoliday();
		else
			pay = getSupervisorPayFullNHoliday();
		return pay;	
	}
	
	/**
	 * 
	 * @param isWorker
	 * @return
	 */
	public BigDecimal getPayHalfNationalHoliday(boolean isWorker)
	{
		BigDecimal pay = BigDecimal.ZERO;
		if(isWorker)
			pay = getSupervisedPayHalfNHoliday();
		else
			pay = getSupervisorPayHalfNHoliday();
		return pay;	
	}
	
	/**
	 * 
	 * @param isWorker
	 * @return
	 */
	public BigDecimal getPayOverTimePerHours(boolean isWorker)
	{
		BigDecimal pay = BigDecimal.ZERO;
		if(isWorker)
			pay = getSupervisedPayOTPerHour();
		else
			pay = getSupervisorPayOTPerhours();
		return pay;	
	}
	
	/**
	 * 
	 * @param isWorker
	 * @return
	 */
	public BigDecimal getOverTimeHoliday(boolean isWorker)
	{
		BigDecimal pay = BigDecimal.ZERO;
		if(isWorker)
			pay = getSupervisedPayHolidayOTPerHour();
		else
			pay = getSupervisorPayHolidayOTPerHours();
		return pay;	
	}
	
	/**
	 * 
	 * @param isWorker
	 * @return
	 */
	public BigDecimal getOverTimeNationalHoliday(boolean isWorker)
	{
		BigDecimal pay = BigDecimal.ZERO;
		if(isWorker)
			pay = getSupervisedPayNHolidayOTPerHour();
		else
			pay = getSupervisorPayNHolidayOTPerHour();
		return pay;	
	}
	
	
	/**
	 * 
	 * @param jobRoleId
	 * @param employmentStatus
	 * @return
	 */
	public MUNSPayRollLine getDailyPayrollLineOf(
			int jobRoleId, String payrollTerm)
	{
		String key = jobRoleId + "_" + payrollTerm + "_" + MUNSPayRollLine.CRITERIATYPE_Daily;
		
		MUNSPayRollLine payrollLine = m_PayrollLineMap.get(key);
		
		if (payrollLine != null)
			return payrollLine;
		
		String whereClause = 
				"UNS_ProductionPayConfig_ID=? AND UNS_Job_Role_ID=? AND payrollterm=? AND CriteriaType=?";
		
		payrollLine = Query
				.get(getCtx(), UNSHRMModelFactory.EXTENSION_ID, 
					 MUNSPayRollLine.Table_Name, whereClause, get_TrxName())
				.setParameters(get_ID(), jobRoleId, payrollTerm, MUNSPayRollLine.CRITERIATYPE_Daily)
				.firstOnly();
		
		if (null != payrollLine)
			m_PayrollLineMap.put(key, payrollLine);
		
		return payrollLine;
	}
}
