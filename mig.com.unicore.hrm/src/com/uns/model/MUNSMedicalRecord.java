/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_C_Period;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author Inohtaf, modified and re-engineered by AzHaidar
 *
 */
public class MUNSMedicalRecord extends X_UNS_MedicalRecord implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String m_processMsg = null;
	
	private boolean m_justPrepared = false;
	
	private MUNSEmployeeAllowanceRecord m_medicalAllowace = null;
	
	private MUNSMedicalDiseaseRecord[] m_lines = null;
	
	/**
	 * @param ctx
	 * @param UNS_MedicalRecord_ID
	 * @param trxName
	 */
	public MUNSMedicalRecord(Properties ctx, int UNS_MedicalRecord_ID,
			String trxName) {
		super(ctx, UNS_MedicalRecord_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMedicalRecord(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSEmployeeAllowanceRecord getMedicalAllowanceRecord()
	{
		if (m_medicalAllowace == null)
			m_medicalAllowace = MUNSEmployeeAllowanceRecord.getCreateMedical(
					getCtx(), (MUNSEmployee) getUNS_Employee(), getmedical_date(), get_TrxName());;
					
		return m_medicalAllowace;
	}
	
	/**
	 * 
	 * @return
	 */
	public static MUNSMedicalRecord[] getMedicalOfInvoiceLine(Properties ctx, int C_InvoiceLine_ID, String trxName)
	{
		MUNSMedicalRecord[] retMedRefs = null;
		
		String whereClause = "C_InvoiceLine_ID = " + C_InvoiceLine_ID;
		
		List<MUNSMedicalRecord> medRecList = Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, MUNSMedicalRecord.Table_Name, whereClause, trxName)
				.list();
		
		retMedRefs = new MUNSMedicalRecord[medRecList.size()];
		medRecList.toArray(retMedRefs);
		
		return retMedRefs;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSMedicalDiseaseRecord[] getLines()
	{
		return getLines(false);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSMedicalDiseaseRecord[] getLines(boolean requery)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(get_TrxName());
			return m_lines;
		}
		
		List<MUNSMedicalDiseaseRecord> diseaseList = Query.get(
				getCtx(), UNSHRMModelFactory.EXTENSION_ID, MUNSMedicalDiseaseRecord.Table_Name, 
				"UNS_MedicalRecord_ID=" + get_ID(), get_TrxName())
				.list();
		
		m_lines = new MUNSMedicalDiseaseRecord[diseaseList.size()];
		
		diseaseList.toArray(m_lines);
		
		return m_lines;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) throws AdempiereUserError 
	{
		// Created by Recruitment MCU
		if (isHRDRequitment()){
			
		}
		// Created by Hospital Invoice.
		else if (getC_InvoiceLine_ID() > 0)
		{
			if (getMedicalCosts().signum() == 0)
				throw new FillMandatoryException(COLUMNNAME_MedicalCosts);
			
		}
		else // non hospital referral. 
		{
			if (getUNS_Employee_ID() > 0)
			{
				String dependent = getwho_is_sick();
				
				String depValue = null;
				
				I_UNS_Employee employee = getUNS_Employee();
				
				if (null == dependent || dependent.equals(MUNSMedicalRecord.WHO_IS_SICK_Employee))
					depValue = employee.getName();
				else if (dependent.equals(MUNSMedicalRecord.WHO_IS_SICK_EmployeeInsured1))
					depValue = employee.getEmployeeInsured1();
				else if (dependent.equals(MUNSMedicalRecord.WHO_IS_SICK_EmployeeInsured2))
					depValue = employee.getEmployeeInsured2();
				else if (dependent.equals(MUNSMedicalRecord.WHO_IS_SICK_EmployeeInsured3))
					depValue = employee.getEmployeeInsured3();
				else if (dependent.equals(MUNSMedicalRecord.WHO_IS_SICK_EmployeeInsured4))
					depValue = employee.getEmployeeInsured4();
				
				if(depValue == null || depValue.isEmpty()) {
					throw new AdempiereUserError("The employee do not have " + dependent);
				}
				else
					setinsured_name(depValue);
			}
			
			Timestamp start = getsl_recommend_startdate();
			Timestamp end = getsl_recommend_enddate();
			
			if((start != null && end == null) || (start == null && end != null))
				throw new AdempiereUserError("You should fill both SL Recommend Start Date and " +
						"SL Recommend End Date");
			else if(start != null && end != null){
				if(start.compareTo(end) > 0)
					throw new AdempiereUserError("Start Date Should be Less or Equals to End Date");
			}
			
			if (getUNS_Employee_ID() > 0 && getAllowancePayment().signum() > 0)
			{
				MUNSEmployeeAllowanceRecord medAllowanceRec = getMedicalAllowanceRecord();
				
				if (medAllowanceRec == null && getAllowancePayment().signum() > 0)
					m_processMsg = "Employee doesn't have medical allowance.";
				else if (medAllowanceRec.getRemainingAmt().compareTo(getAllowancePayment()) < 0)
					m_processMsg = "Employee's remaining medical allowance amount less than allowance payment amount.";
				
				if (m_processMsg != null)
					throw new AdempiereException (m_processMsg);
			}
			
			BigDecimal totalPayment = getAllowancePayment().add(getCashPayment()).add(getPayrollPayment());
			
			if(totalPayment.compareTo(getMedicalCosts()) != 0)
			{
				throw new AdempiereUserError("Total payment must be equal to total medical costs.");
			}
		}
		return true;
	}
	
	
	/**
	 * 
	 * @param UNS_Employee_ID
	 * @param C_Period_ID
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getTotalBiayaBerobat1Period(
			int UNS_Employee_ID, MUNSPayrollConfiguration payrollConfig, I_C_Period period, String trxName)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(period.getStartDate());
		cal.set(Calendar.DATE, payrollConfig.getPayrollDateEnd());
		
		Timestamp payrollDateEnd = new Timestamp (cal.getTimeInMillis());
		
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, payrollConfig.getPayrollDateStart());

		Timestamp payrollDateStart = new Timestamp (cal.getTimeInMillis());
		
		BigDecimal totalBiayaBerobat = DB.getSQLValueBD(
				trxName, "SELECT SUM(" + COLUMNNAME_PayrollPayment 
				+ ") FROM " + Table_Name 
				+ " WHERE " + COLUMNNAME_UNS_Employee_ID + " = ?"
				+ " AND " + COLUMNNAME_medical_date + " BETWEEN ? AND ?"
				+ " AND " + COLUMNNAME_hospital_referal + " = ?", 
				UNS_Employee_ID, payrollDateStart, payrollDateEnd, "N");
//				, UNS_Employee_ID, C_Period_ID, MUNSMedicalRecord.MEDICALPAYMETHOD_PiecePayroll);
		
		if (null == totalBiayaBerobat)
			return BigDecimal.ZERO;
		
		return totalBiayaBerobat;
	}
	

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

	@Override
	public boolean processIt(String action) throws Exception {
		// 
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		// 
		log.info(toString());
		setProcessed(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		// 
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() 
	{
		// 
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
	
		if (checkExistingRecord())
			throw new AdempiereUserError("Employee has had Medical Record at SL Recommend Date.");
		
		MUNSMedicalDiseaseRecord[] diseaseRecs = getLines();
		if (!ishospital_referal() && (diseaseRecs == null || diseaseRecs.length == 0))
		{
			m_processMsg = "Cannot process medical record with empty disease record(s).";
			setProcessed(false);
			return DOCSTATUS_Drafted;
		}
		
		if (null != getAllowancePayment() && getAllowancePayment().signum() > 0)
		{
			MUNSEmployeeAllowanceRecord medAllowanceRec = getMedicalAllowanceRecord();
			
			if (medAllowanceRec == null)
				m_processMsg = "Employee doesn't have medical allowance.";
			else if (medAllowanceRec.getRemainingAmt().compareTo(getAllowancePayment()) < 0)
				m_processMsg = "Employee's remaining medical allowance amount less than allowance payment amount.";
			
			if (m_processMsg != null) {
				setProcessed(false);
				return DOCSTATUS_Invalid;
			}
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		setProcessed(true);
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// 
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		// 
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() 
	{
		// 
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());
		
//		Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status) || DocAction.STATUS_Drafted.equals(status))
				return status;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if (getUNS_Employee_ID() > 0 && getAllowancePayment().signum() > 0)
		{
			MUNSEmployeeAllowanceRecord medAllowanceRec = getMedicalAllowanceRecord();
			
			medAllowanceRec.setMedicalAllowanceUsed(
					medAllowanceRec.getMedicalAllowanceUsed().add(getAllowancePayment()));
			
			if (!medAllowanceRec.save()) {
				m_processMsg = "Failed when updating medical allowance ";
				return DocAction.STATUS_Invalid;
			}
		}
		
		setProcessed(true);	
		//m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		// 
		return false;
	}

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

	@Override
	public boolean reverseCorrectIt() {
		// 
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// 
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// 
		return false;
	}

	@Override
	public String getSummary() {
		// 
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// 
		return null;
	}

	@Override
	public File createPDF() {
		// 
		return null;
	}

	@Override
	public String getProcessMsg() {
		// 
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// 
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// 
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// 
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean checkExistingRecord(){
		String sql = "SELECT COUNT(*) FROM UNS_MedicalRecord mr where mr.UNS_MedicalRecord_ID" +
				" IN (SELECT UNS_MedicalRecord_ID FROM UNS_MedicalRecord medical WHERE '" +
				getsl_recommend_startdate() + "' BETWEEN mr.SL_Recommend_StartDate AND " +
				"mr.SL_Recommend_EndDate) AND mr.UNS_Employee_ID="+getUNS_Employee_ID();
		int count = DB.getSQLValue(get_TrxName(), sql);
		
		if (count==1)
			return false;
		
		return true;
	}

}