/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Period;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;

import com.uns.util.MessageBox;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSMonthlyPayrollEmployee extends X_UNS_MonthlyPayroll_Employee
		implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSPayrollEmployee[] m_lines;
	private String m_processMsg;
	private boolean m_justPrepared = false;
	
	
	/**
	 * @param ctx
	 * @param UNS_MonthlyPayroll_Employee_ID
	 * @param trxName
	 */
	public MUNSMonthlyPayrollEmployee(Properties ctx,
			int UNS_MonthlyPayroll_Employee_ID, String trxName) {
		super(ctx, UNS_MonthlyPayroll_Employee_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMonthlyPayrollEmployee(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		log.info(toString());
		setProcessed(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
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
		
		MUNSPayrollEmployee[] pays = getLines(true);
		
		String completeErrorMsg = "";
		
		for(MUNSPayrollEmployee pay : pays)
		{
			if(pay.getDocStatus().equals(MUNSPayrollEmployee.DOCSTATUS_Completed)
					|| pay.getDocStatus().equals(MUNSPayrollEmployee.DOCSTATUS_Closed)
					|| pay.getDocStatus().equals(MUNSPayrollEmployee.DOCSTATUS_Voided))
				continue;
			try 
			{
				if(!pay.processIt(MUNSPayrollEmployee.DOCACTION_Complete))
				{
					completeErrorMsg += "can't complete document line payroll employee document no : " 
									+ pay.getDocumentNo();
					completeErrorMsg += "\n";
				}
			} catch (Exception ex) {
				completeErrorMsg += ex.getMessage();
				completeErrorMsg += "\n";
			}
		}
		
		if(!completeErrorMsg.equals(""))
		{
			m_processMsg = completeErrorMsg;
			return DocAction.STATUS_Invalid;
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
		// TODO Auto-generated method stub
		m_processMsg = "Logic void belum dibuat!!!";
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		m_processMsg ="Logic close belum di buat!!!";
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return m_processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return Env.getContextAsInt(getCtx(), "C_Currency_ID");
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public MUNSPayrollEmployee[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
			return m_lines;
		
		List<MUNSPayrollEmployee> list = Query.get(
				getCtx(), UNSHRMModelFactory.EXTENSION_ID
				, MUNSPayrollEmployee.Table_Name
				, MUNSPayrollEmployee.COLUMNNAME_UNS_MonthlyPayroll_Employee_ID 
				+ " =?", get_TrxName()).setParameters(get_ID())
				.list();
		
		m_lines = new MUNSPayrollEmployee[list.size()];
		m_lines = list.toArray(m_lines);
		
		return m_lines;
	}
	
	public String generatePayroll()
	{
		String msg = "";
		if(MUNSMonthlyPayrollEmployee.GENERATELIST_GenerateByContract.equals(getGenerateList()))
		{
			msg = generateByContract();
		}
		else if (MUNSMonthlyPayrollEmployee.GENERATELIST_GenerateByPresence.equals(getGenerateList()))
		{
			msg = generateByPresence();
		}
		else
		{
		
			log.log(Level.SEVERE, "Unknown type : " + getGenerateList());
			String m = Msg.getMsg(getCtx(), "NotRunningMsg");
			String title = Msg.getMsg(getCtx(), "NotRunningTitle");
			MessageBox.showMsg(this, getCtx(), m  + getGenerateList(), title
					, MessageBox.OK, MessageBox.ICONINFORMATION);
		}
		
		return msg;
	}
	
	private String generateByContract()
	{
		String msg = "";
		MUNSPayrollConfiguration config = MUNSPayrollConfiguration.get(
				getCtx(), MPeriod.get(getCtx(), getC_Period_ID()), get_TrxName());
		MUNSContractRecommendation[] contractList = 
				MUNSContractRecommendation.getUsedGeneralPayroll(
						getCtx(), get_TrxName(), getAD_Org_ID(), 
						config.getStartDate());
		
		if(null == contractList)
		{
			return "-No active contract found :(. No Payroll generated";
		}
		
		MUNSPayrollEmployee[] existings = getLines(true);
		
		int newRecord = 0;
		int prevRecord = existings.length;
		int totalError = 0;
		
		for(MUNSContractRecommendation contract : contractList)
		{
			boolean found = false;
			
			String payrollTerm = MUNSPayrollTermConfig.getPayrollTermOf(
					getAD_Org_ID()
					, contract.getNewSectionOfDept_ID()
					, contract.getNextContractType()
					, Env.getContextAsDate(getCtx(), "Date")
					, get_TrxName());
			if(null == payrollTerm)
				payrollTerm = contract.getNextPayrollTerm();
			
			if (!PERIODTYPE_1Month.equals(getPeriodType()))
			{
				return "Period type is not impletemnted";
			}
			
			if(getPeriodType().equals(MUNSMonthlyPayrollEmployee.PERIODTYPE_1Month)
					&& (!payrollTerm.equals(MUNSEmployee.PAYROLLTERM_Monthly)
							&& !payrollTerm.equals(MUNSEmployee.PAYROLLTERM_HarianBulanan)))
				continue;

			if(!getPeriodType().equals(MUNSMonthlyPayrollEmployee.PERIODTYPE_1Month)
					&& (payrollTerm.equals(MUNSEmployee.PAYROLLTERM_Monthly)
							|| payrollTerm.equals(MUNSEmployee.PAYROLLTERM_HarianBulanan)))
				continue;
			
			for(MUNSPayrollEmployee existing : existings)
			{
				if(existing.getUNS_Employee_ID() != contract.getUNS_Employee_ID())
					continue;
				
				found = true;
				break;
			}
			
			if(found)
				continue;
			
			MUNSPayrollEmployee payroll = new MUNSPayrollEmployee(this,++newRecord);
			payroll.createFrom(contract);
			payroll.setC_DocType_ID(getC_DocType_ID());
			payroll.setIsGenerate(true);
			
			try{
				payroll.saveEx();
			} catch (Exception ex) {
				msg += "- Failed to create payroll for employee : " 
							+ payroll.getUNS_Employee().getName() 
								+ ". Caused by : " + CLogger.retrieveErrorString("Unknown :(\n");
				
				String newTrxName = Trx.createTrxName();
				this.set_TrxName(newTrxName);
				totalError++;
				continue;
			}
			
			msg += payroll.calculatePayroll();
		}
		
		String okMsg = prevRecord > 0 ? "Total Previus record Generated : " + prevRecord + "\n": "";
		okMsg += newRecord > 0 ? "Total New Record Generated : " + newRecord + "\n" : "";
		okMsg += totalError > 0 ? "Total Payroll Not Created : " + totalError + "\n" : "";
		if(!"".equals(okMsg))
			okMsg += "=======================================================================\n";
		return okMsg + msg;
	}
	
	
	/**
	 * Generate Payroll from monthly presence
	 * @return
	 */
	private String generateByPresence()
	{
 		String msg = "";
		
		MUNSMonthlyPresenceSummary[] monthlyPresences = MUNSMonthlyPresenceSummary.getOf(
				getCtx(), getAD_Org_ID(), getC_Period_ID(), getPeriodType(), get_TrxName());
		if(null == monthlyPresences)
		{
			I_C_Period period = getC_Period();
			return "- Not found PRESENCE SUMMARY for period : " + period.getName();
		}
		
		MUNSPayrollEmployee[] payEmployeeList = getLines(true);
		
		int newRecord = 0;
		int prevRecord = payEmployeeList.length;
		int totalError = 0;
		
		for(MUNSMonthlyPresenceSummary monthlyPresence : monthlyPresences)
		{
			boolean found = false;
			for(MUNSPayrollEmployee payEmployee : payEmployeeList)
			{
				if(payEmployee.getUNS_Employee_ID() != monthlyPresence.getUNS_Employee_ID())
					continue;
				found = true;
				break;
			}
			
			if(found)
				continue;
			
			MUNSPayrollEmployee payroll = new MUNSPayrollEmployee(this,+newRecord);
			payroll.createFrom(monthlyPresence);
			payroll.setC_DocType_ID(getC_DocType_ID());
			payroll.setIsGenerate(true);
			
			try{
				payroll.saveEx();
			} catch (Exception ex) {
				msg += "- Failed to create payroll for employee : " 
							+ monthlyPresence.getUNS_Employee().getName() 
								+ ". Caused by : " + CLogger.retrieveErrorString("Unknown :(\n");
				
				String newTrxName = Trx.createTrxName();
				this.set_TrxName(newTrxName);
				totalError++;
				continue;
			}
			
			msg += payroll.calculatePayroll();
		}
		
		String okMsg = prevRecord > 0 ? "Total Previus record Generated : " + prevRecord + "\n": "";
		okMsg += newRecord > 0 ? "Total New Record Generated : " + newRecord + "\n" : "";
		okMsg += totalError > 0 ? "Total Payroll Not Created : " + totalError + "\n" : "";
		if(!"".equals(okMsg))
			okMsg += "=======================================================================\n";
		return okMsg + msg;
	}
	
	public boolean beforeDelete()
	{
		MUNSPayrollEmployee[] lines = getLines(true);
		for(MUNSPayrollEmployee line : lines)
		{
			if(!line.delete(true))
				throw new AdempiereException("Failed to delete lines");
		}
		return super.beforeDelete();
	}
	
	
	public void calculatePaySummary()
	{
		StringBuilder sb = new StringBuilder("UPDATE ")
		.append(Table_Name)
		.append(" SET ");
		
		ArrayList<Object> params = new ArrayList<Object>();
		
		boolean anyUpdate = false;
		
		int count = get_ColumnCount();
		for(int i=0; i<count; i++)
		{
			Object val = get_Value(i);
			String columnname = get_ColumnName(i);
			
			if(val instanceof BigDecimal == false)
				continue;
			if(columnname.equals(COLUMNNAME_ProcessedOn))
				continue;
			
			if(anyUpdate)
				sb.append(", ");
			
			if(!anyUpdate)
				anyUpdate = true;
			
			sb.append(columnname)
			.append(" =");
			
			//define param
			StringBuilder sbp = new StringBuilder("(SELECT COALESCE(SUM(")
			.append(columnname)
			.append("),0) FROM ")
			.append(MUNSPayrollEmployee.Table_Name)
			.append(" WHERE ")
			.append(MUNSPayrollEmployee.COLUMNNAME_UNS_MonthlyPayroll_Employee_ID)
			.append("=")
			.append(get_ID())
			.append(")");
			
			sb.append(sbp.toString());
		}
		
		if(!anyUpdate)
			return;
		
		sb.append(" WHERE ")
		.append(COLUMNNAME_UNS_MonthlyPayroll_Employee_ID)
		.append("=? ");
		params.add(get_ID());
		
		Object[] oo = new Object[params.size()];
		oo = params.toArray(oo);
		
		String sql = sb.toString();
		
		int retVal = DB.executeUpdate(sql, oo, false, get_TrxName());
		if(retVal < 0)
			throw new AdempiereException("Failed To update header!!!");
		
	}
	
	public boolean isGenerated()
	{
		return "Y".equals(getGeneratePay());
	}

}
