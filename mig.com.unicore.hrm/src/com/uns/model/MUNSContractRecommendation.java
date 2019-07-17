/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
//import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSContractRecommendation extends X_UNS_Contract_Recommendation implements DocAction, DocOptions {

	
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSPayrollComponentConf[] m_components = null;
	
	public MUNSPayrollComponentConf[] getComponents (boolean requery)
	{
		if (m_components != null)
		{
			set_TrxName(m_components, get_TrxName());
			return m_components;
		}
		String wc = Table_Name + "_ID = ?";
		List<MUNSPayrollComponentConf> comps = Query.get(getCtx(), UNSHRMModelFactory.EXTENSION_ID,
				MUNSPayrollComponentConf.Table_Name, wc, get_TrxName()).setParameters(get_ID()).list();
		m_components = new MUNSPayrollComponentConf[comps.size()];
		comps.toArray(m_components);
		
		return m_components;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2950121656417505222L;

	/**
	 * @param ctx
	 * @param UNS_Contract_Recommendation_ID
	 * @param trxName
	 */
	public MUNSContractRecommendation(Properties ctx,
			int UNS_Contract_Recommendation_ID, String trxName) {
		super(ctx, UNS_Contract_Recommendation_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSContractRecommendation(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSContractRecommendation(MUNSEmployee employee)
	{
		super(employee.getCtx(), 0, employee.get_TrxName());
		setAD_Org_ID(employee.getAD_Org_ID());
		setUNS_Employee_ID(employee.getUNS_Employee_ID());
	}
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_Contract_Evaluation_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSContractRecommendation get(
			Properties ctx, int UNS_Contract_Evaluation_ID, String trxName)
	{
		return Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name
				, COLUMNNAME_UNS_Contract_Evaluation_ID + " = " + UNS_Contract_Evaluation_ID
				+ " AND " + COLUMNNAME_IsActive + " = 'Y' ", 
				trxName)
				.firstOnly();
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
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
			
		I_UNS_Employee employee = getUNS_Employee();
		if(employee.isBlacklist())
		{
			m_processMsg = "Can't create Contract for blacklisted emloyeee";
			return DocAction.STATUS_Drafted;
		}
		if (null == getNewPayrollLevel() || getNewPayrollLevel().equalsIgnoreCase(NEWPAYROLLLEVEL_NotDefined))
		{
			m_processMsg = "Employee must have payroll level, please define payroll level";
			return DocAction.STATUS_Drafted;
		}

		if (EMPLOYMENTTYPE_Company.equals(getEmploymentType()) && getNew_G_Pokok().equals(BigDecimal.ZERO))
		{
			m_processMsg = "Employee basic payroll: please define Basic Salary of the contract.";
			return DocAction.STATUS_Drafted;
		}

		if(getDateContractEnd().before(getDateContractStart()))
		{
			m_processMsg = "Date contract end must be greater than date contract start";
			return DocAction.STATUS_Drafted;
		}
		
		MUNSPayrollConfiguration payConfig = MUNSPayrollConfiguration.get(
				getCtx(), getDateContractStart(), get_TrxName());
		if(null == payConfig)
			throw new AdempiereUserError("Not found payroll configuration");
		
		MUNSPayrollLevelConfig payLevelConfig = payConfig.getPayrollLevel(getNewPayrollLevel()
				, getNextPayrollTerm());
		if (null == payLevelConfig)
			throw new AdempiereUserError("Not found Payroll Level Configuration with level " + getNewPayrollLevel());

			
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
		

		MUNSEmployee employee = new MUNSEmployee(getCtx(), getUNS_Employee_ID(), get_TrxName());
		if (MUNSEmployee.EMPLOYMENTTYPE_Company.equals(employee.getEmploymentType())
				|| isUseGeneralPayroll())
		{
			MUNSPayrollBaseEmployee prevPayrollBase = MUNSPayrollBaseEmployee.getPrev(
					getCtx(), getUNS_Employee_ID(), get_TrxName());
			if(null != prevPayrollBase)
			{
				prevPayrollBase.setIsActive(false);
				prevPayrollBase.save();				
			}
			m_processMsg = generatePayrollBaseEmployee();
			if(m_processMsg != null)
				return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = employee.updateEmployeeData(this);
		if (null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		try
		{
			employee.saveEx();
		}
		catch (Exception ex)
		{
			m_processMsg = ex.getMessage();
			return DocAction.STATUS_Invalid;
		}
		
		
		// Try to create allowance record for the company's employee.
		if (employee.getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_Company))
			MUNSEmployeeAllowanceRecord.getCreate(
					getCtx(), (MUNSEmployee) employee, getDateContractStart(), get_TrxName());
		
		setProcessed(true);	
		setIsApproved(true);
		setDocAction(DOCACTION_Close);
		saveEx();
		
		if (getUNS_Contract_Evaluation_ID() != 0){
			MUNSContractEvaluation contractEvalutaion = (MUNSContractEvaluation) getUNS_Contract_Evaluation();
			try {
				if(!contractEvalutaion.processIt(DOCACTION_Complete)) 
				{
					m_processMsg = "Error while auto completing Contract Evaluation of " 
							+ contractEvalutaion.getDocumentNo() + ". Error: " 
							+ CLogger.retrieveErrorString("No detail error.");
				}
			} catch(Exception e){
				throw new AdempiereUserError(e.getMessage());
			}
		}
		
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		if (DOCSTATUS_Closed.equals(getDocStatus())
			|| DOCSTATUS_Reversed.equals(getDocStatus())
			|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}
		
		if (getUNS_Contract_Evaluation_ID() > 0)
		{
			MUNSContractRecommendation prevContract = 
					MUNSContractRecommendation.get(getCtx(), 
							getUNS_Contract_Evaluation_ID(), get_TrxName());
			
			if (null == prevContract)
			{
				m_processMsg = "could not find prev contract of " + toString();
				return false;
			}
			
			String sql = " UPDATE " + MUNSPayrollBaseEmployee.Table_Name + " SET "
					+ MUNSPayrollBaseEmployee.COLUMNNAME_IsActive + " = ? WHERE "
					+ MUNSPayrollBaseEmployee.COLUMNNAME_UNS_Contract_Recommendation_ID
					+ " = ? ";
			if (DB.executeUpdate(sql, new Object[]{"Y", prevContract.get_ID()},
					false, get_TrxName())== -1)
			{
				m_processMsg = "can't update payroll base.";
				return false;
			}
			else if (DB.executeUpdate(sql, new Object[]{"N", get_ID()},
					false, get_TrxName())== -1)
			{
				m_processMsg = "can't update payroll base.";
				return false;
			}
			
			MUNSEmployee employe = (MUNSEmployee) getUNS_Employee();
			m_processMsg = employe.updateEmployeeData(prevContract);
			if (null != m_processMsg)
			{
				return false;
			}
			
			try
			{
				employe.save();
			}
			catch (Exception ex)
			{
				m_processMsg = ex.getMessage();
				return false;
			}
		}
		else
		{
			MUNSEmployee employe = (MUNSEmployee) getUNS_Employee();
			m_processMsg = employe.updateEmployeeData(this);
			String sql = " UPDATE " + MUNSPayrollBaseEmployee.Table_Name + " SET "
					+ MUNSPayrollBaseEmployee.COLUMNNAME_IsActive + " = ? WHERE "
					+ MUNSPayrollBaseEmployee.COLUMNNAME_UNS_Contract_Recommendation_ID
					+ " = ? ";
			if (DB.executeUpdate(sql, new Object[]{"N", get_ID()},
					false, get_TrxName())== -1)
			{
				m_processMsg = "can't update payroll base.";
				return false;
			}
			
			if (null != m_processMsg)
			{
				return false;
			}
			
			try
			{
				employe.save();
			}
			catch (Exception ex)
			{
				m_processMsg = ex.getMessage();
				return false;
			}
		}

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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	
	protected String generatePayrollBaseEmployee()
	{
		String msg = null;
		MUNSPayrollBaseEmployee pbEmployee = new MUNSPayrollBaseEmployee(this);
		if (!pbEmployee.save())
			msg = " Failed to create new Payroll Base Employee";
		return msg;
	}
	@Override
	protected boolean beforeDelete(){
		if (getUNS_Contract_Evaluation_ID()>0)
			return false;
		if (isProcessed())
			return false;
		
		return true;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		//IsImportData
		if (newRecord && getNewNIK()== null){
			setNewNIK(getUNS_Employee().getValue());
			
			String payrollTerm = MUNSPayrollTermConfig.getPayrollTermOf(
					getAD_Org_ID()
					, getNewSectionOfDept_ID()
					, getNextContractType()
					, Env.getContextAsDate(getCtx(), "Date")
					, get_TrxName());
			if(null == payrollTerm)
				payrollTerm = getUNS_Employee().getPayrollTerm();
			
			setNextPayrollTerm(payrollTerm);
			setNewPayrollLevel(getUNS_Employee().getPayrollLevel());
			setNewGender(getUNS_Employee().getGender());
			setNewShift(getUNS_Employee().getShift());
			setNewAgent_ID(getUNS_Employee().getVendor_ID());
		}
				
		if(newRecord)
		{
			if(getUNS_Employee().getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_SubContract))
				setNextContractNumber(getPrevContractNumber()+1);
		}
		
		//Direct Additional ITD-Andy 29/07/13
		if (getDateContractEnd().before(getDateContractStart()))
			throw new AdempiereUserError("Date contact end must bigger than date contact start");
		
		if(getUNS_Employee().isBlacklist())
			throw new AdempiereUserError("Can't create contract for blacklisted employee or worker");
		if(null == getNextContractType())
			throw new AdempiereException("Field Mandatory Contract");

		if(getEmploymentType().equals(EMPLOYMENTTYPE_Company)
				&& getNextContractType().equals(NEXTCONTRACTTYPE_SquenceContract))
		{
			throw new AdempiereUserError("Contract sequence are only for workers");
		}
		else if(getEmploymentType().equals(EMPLOYMENTTYPE_SubContract)
				&& !getNextContractType().equals(NEXTCONTRACTTYPE_SquenceContract))
		{
			throw new AdempiereUserError("Please choose sequence contract for Worker");
		}
		
		return super.beforeSave(newRecord);
	}
	
	public MUNSContractRecommendation getPrev ()
	{
		if (getUNS_Contract_Evaluation_ID() == 0)
			return null;
		
		MUNSContractEvaluation evaluation = new MUNSContractEvaluation(
				getCtx(), getUNS_Contract_Evaluation_ID(), get_TrxName());
		if (evaluation.getUNS_Contract_Recommendation_ID() == 0)
			return null;
		
		return new MUNSContractRecommendation(getCtx(), evaluation.getUNS_Contract_Recommendation_ID(), 
				get_TrxName());
	}
	
	/**
	 * @deprecated
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSContractRecommendation getPrev(
			Properties ctx, int UNS_Employee_ID, String trxName)
	{
		MUNSContractRecommendation prev = null;
		String sql = "SELECT * FROM " + Table_Name
				+ " WHERE " + COLUMNNAME_DateContractEnd + " = " +
						"(SELECT MAX(" + COLUMNNAME_DateContractEnd + ") FROM " + Table_Name + " "
								+ " WHERE " + COLUMNNAME_UNS_Employee_ID
								+ " = " + UNS_Employee_ID + " AND " + COLUMNNAME_IsActive + " ='Y')" 
								+ " AND " + COLUMNNAME_IsActive + " = 'Y' AND " 
								+ COLUMNNAME_UNS_Employee_ID
								+ " = " + UNS_Employee_ID;
		
		PreparedStatement stm = null;
		ResultSet rs = null;
		try
		{
			stm = DB.prepareStatement(sql,trxName);
			rs = stm.executeQuery();
			if (rs.next())
				prev = new MUNSContractRecommendation(ctx, rs, trxName);
		}catch (Exception e)
		{
			e.printStackTrace();
		}finally
		{
			DB.close(rs, stm);
		}
		
		return prev;
	}
	
	public boolean isGenerateNIK()
	{
		return getGenerateNIK().equalsIgnoreCase("Y");
	}

	/**
	 * 
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @param trxName
	 * @return
	 */
	public static List<MUNSContractRecommendation> getContractsOf(
			Properties ctx, int UNS_Employee_ID, String trxName)
	{
		return Query.get(
				ctx, UNSHRMModelFactory.getExtensionID(), Table_Name, 
				COLUMNNAME_UNS_Employee_ID + " = " + UNS_Employee_ID, trxName)
				.list();
	}
	
	/**
	 * Get employee contract which is valid on the date of dateToSearch.
	 * 
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @param date
	 * @param trxName
	 * @return
	 */
	public static MUNSContractRecommendation getOf(
			Properties ctx, int UNS_Employee_ID, Timestamp dateToSearch, String trxName)
	{
		return Query.get(
				ctx, UNSHRMModelFactory.getExtensionID(), Table_Name, 
				"UNS_Employee_ID = ? AND ? BETWEEN DateContractStart AND DateContractEnd", trxName)
				.setParameters(UNS_Employee_ID, dateToSearch)
				.first();
	}
	
	public void setPotonganToZero(){
		setNew_P_Label(BigDecimal.ZERO);
		setNew_P_Lain2(BigDecimal.ZERO);
		setNew_P_Mangkir(BigDecimal.ZERO);
		setNew_P_SPTP(BigDecimal.ZERO);
	}
	
	public void generateNIK()
	{
		
	}	
	
	private MUNSEmployee m_employee = null;
	
	@Override
	public MUNSEmployee getUNS_Employee ()
	{
		if (null != m_employee)
		{
			return m_employee;
		}
		
		m_employee = (MUNSEmployee)super.getUNS_Employee();
		return m_employee;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param trxName
	 * @param AD_Org_ID
	 * @param date
	 * @return
	 */
	public static MUNSContractRecommendation[] getUsedGeneralPayroll (
			Properties ctx, String trxName, int AD_Org_ID, Timestamp date)
	{
		StringBuilder wcBuilder = new StringBuilder(COLUMNNAME_AD_Org_ID)
		.append(" = ? AND ? BETWEEN " ).append(COLUMNNAME_DateContractStart)
		.append(" AND ").append(COLUMNNAME_DateContractEnd).append(" AND ")
		.append(COLUMNNAME_IsUseGeneralPayroll).append(" = ? AND ")
		.append(COLUMNNAME_DocStatus).append(" IN (?,?) AND ")
		.append(COLUMNNAME_IsActive).append(" = ? ");
		
		List<Object> parameters = new ArrayList<>();
		parameters.add(AD_Org_ID);
		parameters.add(date);
		parameters.add("Y");
		parameters.add("CO");
		parameters.add("CL");
		parameters.add("Y");
		
		String whereClause = wcBuilder.toString();
		return gets(ctx, trxName, whereClause, parameters, null);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param trxName
	 * @param whereClause
	 * @param parameters
	 * @param orderBy
	 * @return
	 */
	public static MUNSContractRecommendation[] gets (Properties ctx, 
			String trxName, String whereClause, List<Object> parameters, 
			String orderBy)
	{
		Query q = Query.get(ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
				whereClause, trxName);
		if (parameters != null)
		{
			q.setParameters(parameters);
		}
		if (orderBy != null)
		{
			q.setOrderBy(orderBy);
		}
		
		List<MUNSContractRecommendation> list = q.list();
		MUNSContractRecommendation[] contracts = 
				new MUNSContractRecommendation[list.size()];
		list.toArray(contracts);
		
		return contracts;
	}
}
