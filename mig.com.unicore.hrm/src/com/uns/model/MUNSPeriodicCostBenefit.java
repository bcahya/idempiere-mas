/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSPeriodicCostBenefit extends X_UNS_PeriodicCostBenefit implements DocAction,
								DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4538773437816876829L;
	
	public String m_processMsg = null;
	public boolean m_justPrepared = false; 

	/**
	 * @param ctx
	 * @param UNS_PeriodicCostBenefit_ID
	 * @param trxName
	 */
	public MUNSPeriodicCostBenefit(Properties ctx,
			int UNS_PeriodicCostBenefit_ID, String trxName) {
		super(ctx, UNS_PeriodicCostBenefit_ID, trxName);

	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPeriodicCostBenefit(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public static MUNSPeriodicCostBenefit getOnWeek (int AD_Org_ID, int C_Year_ID, String weekNo, String trxName) {
		String wc = "AD_Org_ID = ? AND C_Year_ID = ? AND WeekNo = ?";
		return Query.get(Env.getCtx(), UNSHRMModelFactory.EXTENSION_ID, Table_Name, wc, 
				trxName).setParameters(AD_Org_ID, C_Year_ID, weekNo).firstOnly();
	}

	public static MUNSPeriodicCostBenefit getOnPeriod (int AD_Org_ID, int C_Year_ID, int C_Period_ID, String trxName) {
		String wc = "AD_Org_ID = ? AND C_Year_ID = ? AND C_Period_ID = ?";
		return Query.get(Env.getCtx(), UNSHRMModelFactory.EXTENSION_ID, Table_Name, wc, 
				trxName).setParameters(AD_Org_ID, C_Year_ID, C_Period_ID).firstOnly();
	}
	
	private MUNSPeriodicCostBenefitLine[] m_lines = null;
	
	public MUNSPeriodicCostBenefitLine[] getLines (boolean requery) {
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		List<MUNSPeriodicCostBenefitLine> list = Query.get(
				getCtx(), 
				UNSHRMModelFactory.EXTENSION_ID, 
				MUNSPeriodicCostBenefitLine.Table_Name, 
				Table_Name + "_ID =?", get_TrxName()).
				setParameters(get_ID()).list();
		m_lines = new MUNSPeriodicCostBenefitLine[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	public MUNSPeriodicCostBenefitLine getLine (int employeeID) {
		getLines(false);
		for (int i=0; i<m_lines.length; i++) {
			if (m_lines[i].getUNS_Employee_ID() == employeeID)
				return m_lines[i];
		}
		return null;
	}
	
	public static BigDecimal getMyCostOrBenefit (int UNS_Employee_ID, String type, String trxName) {
		String __sql = "SELECT COALESCE (pcl.RemainingAmount, 0) FROM UNS_PeriodicCostBenefitLine pcl "
				+ " INNER JOIN UNS_PeriodicCostBenefit pc ON pc.UNS_PeriodicCostBenefit_ID = pcl.UNS_PeriodicCostBenefit_ID "
				+ " AND pc.DocStatus IN ('CO','CL') AND pc.CostBenefitType = ?  WHERE pcl.UNS_Employee_ID =? "
				+ " AND pcl.IsActive = ? AND pcl.Processed = ? ORDER BY pc.DateTo Desc";
//		String sql = "SELECT COALESCE (pcl.RemainingAmount, 0) FROM UNS_PeriodicCostBenefitLine pcl"
//				+ " WHERE pcl.UNS_Employee_ID = ? AND EXISTS (SELECT IsActive FROM UNS_PeriodicCostBenefit WHERE UNS_PeriodicCostBenefit_ID = "
//				+ " UNS_PeriodicCostBenefitLine.UNS_PeriodicCostBenefit_ID AND CostBenefitType = ? AND "
//				+ " DocStatus IN ('CO','CL')) AND Processed = ?";
		BigDecimal amount = DB.getSQLValueBD(trxName, __sql, type, UNS_Employee_ID, "Y", "Y");
		if (amount == null)
			amount = Env.ZERO;
		return amount;
	}
	
	public String processRecord () {
		setProcessed(true);
		String sql = "UPDATE UNS_PeriodicCostBenefitLine SET Processed = 'Y' "
				+ " WHERE UNS_PeriodicCostBenefit_ID = ?";
		int ok = DB.executeUpdate(sql, get_ID(), false, get_TrxName());
		if (ok == -1) {
			return CLogger.retrieveErrorString("Failed when try to update Periodic Cost Benefit Line");
		}
		return null;
	}
	
	@Override
	protected boolean beforeSave (boolean newRecord) {
//		if (getC_Period_ID() > 0) {
//			MPeriod period = MPeriod.get(getCtx(), getC_Period_ID());
//			MUNSPayrollConfiguration config = MUNSPayrollConfiguration.get(getCtx(), period, get_TrxName());
//			setDateFrom(config.getStartDate());
//			setDateTo(config.getEndDate());
//		}
		
		if(!newRecord && is_ValueChanged(COLUMNNAME_CostBenefitType))
		{
			getLines(false);
			if(m_lines.length > 0)
			{
				log.saveError("Save Error", "Cannot change Cost / Benefit Type, there was exists line");
				return false;
			}
				
		}
		
		return super.beforeSave(newRecord);
	}

	@Override
	public boolean processIt(String action) throws Exception {
		
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		if (log.isLoggable(Level.INFO)) log.info("unlockIt - " + toString());
		return true;
	}

	@Override
	public boolean invalidateIt() {
		if (log.isLoggable(Level.INFO)) log.info("invalidateIt - " + toString());
		return true;
	}

	@Override
	public String prepareIt() {
		
		if(log.isLoggable(Level.INFO))
			log.info(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if(m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		// update canteen activity
		getLines(false);
		for(int i=0; i<m_lines.length; i++)
		{	
			MUNSCanteenActivity[] activities = getActivities(
					m_lines[i].getUNS_Employee_ID(), getDateFrom(), getDateTo(), getAD_Org_ID(), false);
			for(int j=0; j<activities.length; j++)
			{
				activities[j].setProcessed(true);
				if(!activities[j].save())
				{
					m_processMsg = "Failed when try to update Canteen Activity";
					return DocAction.STATUS_Invalid;
				}
			}
			m_lines[i].setProcessed(true);
			m_lines[i].saveEx();
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if(m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		//check and add employee based on last benefit/cost
		checkAddEmployee();
		
		setProcessed(true);
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		setIsApproved(false);
		setProcessed(false);
		return true;
	}

	@Override
	public String completeIt() {
		
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if(m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		//TODO
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if(m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);
		setDocAction(ACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		
		if (log.isLoggable(Level.INFO))
			log.config(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if(m_processMsg != null)
			return false;
		
		// TODO
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if(m_processMsg != null)
			return false;
		
		return true;
	}

	@Override
	public boolean closeIt() {
		
		if (log.isLoggable(Level.INFO))
			log.config(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if(m_processMsg != null)
			return false;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if(m_processMsg != null)
			return false;
		
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		
		m_processMsg = "Disallowed Reverse Correct";
		
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		
		m_processMsg = "Disallowed Reverse Accrual";
		
		return false;
	}

	@Override
	public boolean reActivateIt() {
		
		if(log.isLoggable(Level.INFO))
			log.config(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if(m_processMsg != null)
			return false;
		
		// update canteen activity
		getLines(false);
		String errMsg = null;
		for(int i=0; i<m_lines.length; i++)
		{
			//check monthly presence
			String sql = "SELECT 1 FROM UNS_MonthlyPresenceSummary WHERE UNS_Employee_ID = ? AND C_Period_ID = ?"
					+ " AND C_Year_ID = ? AND Processed = Y";
			boolean exists = DB.getSQLValue(get_TrxName(), sql, m_lines[i].getUNS_Employee_ID(), getC_Period_ID()
					, getC_Year_ID()) > 0;
			if(exists)
			{
				String sqql = "SELECT CONCAT(Value,'_',Name) FROM UNS_Employee WHERE UNS_Employee_ID = ?";
				String name = DB.getSQLValueString(get_TrxName(), sqql, m_lines[i].getUNS_Employee_ID());
				errMsg = errMsg.concat(name+"; ");
			}
			
			MUNSCanteenActivity[] activities = getActivities(
					m_lines[i].getUNS_Employee_ID(), getDateFrom(), getDateTo(), getAD_Org_ID(), false);
			for(int j=0; j<activities.length; j++)
			{
				activities[j].setProcessed(true);
				if(!activities[j].save())
				{
					m_processMsg = "Failed when try to update Canteen Activity";
					return false;
				}
			}
		}
		
		if(errMsg != null)
		{
			m_processMsg = "MonthlyPresence has been processed, Cannot Re-Active. Employe : "+errMsg;
			return false;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if(m_processMsg != null)
			return false;
		
		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
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
		StringBuilder info = new StringBuilder();
		
		info.append(getCreated());
		
		return info.toString();
	}

	@Override
	public File createPDF() {
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}
	
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF

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
	
	public MUNSCanteenActivity[] getActivities(int employeeID, Timestamp dateFrom, Timestamp dateTo, int AD_Org_ID, boolean processed) {
		
		List<MUNSCanteenActivity> list = Query.get(getCtx(), 
				UNSHRMModelFactory.EXTENSION_ID, MUNSCanteenActivity.Table_Name, "UNS_Employee_ID = ?"
						+ " AND DateTrx BETWEEN ? AND ? AND AD_Org_ID = ? AND Processed = ?"
				, get_TrxName())
				.setParameters(employeeID, dateFrom, dateTo, AD_Org_ID, processed).list();
		MUNSCanteenActivity[] canteenActivities = new MUNSCanteenActivity[list.size()];
		list.toArray(canteenActivities);
		return canteenActivities;
	}
	
	protected void checkAddEmployee() {
		
		if(m_lines == null)
			getLines(false);
		
		StringBuilder sql = new StringBuilder("SELECT UNS_PeriodicCostBenefit_ID FROM UNS_PeriodicCostBenefit WHERE"
				+ " DocStatus IN ('CO','CL') AND isBenefit = ? AND CostBenefitType = ? ");
		if(getC_Period_ID() > 0)
			sql.append("AND C_Period_ID > 0 ");
		else
			sql.append("AND WeekNo != null ");
		
		sql.append("AND DateTo < ? ORDER BY DateTo DESC");
		
		int prevCostBent = DB.getSQLValue(get_TrxName(), sql.toString(), isBenefit(), getCostBenefitType()
				, getDateTo());
		if(prevCostBent > 0)
		{
			MUNSPeriodicCostBenefitLine[] prevLine = new MUNSPeriodicCostBenefit(
					getCtx(), prevCostBent, get_TrxName()).getLines(false);
			for(int i=0; i<prevLine.length; i++)
			{
				boolean exists = false;
				for(int j=0; j<m_lines.length; j++)
				{
					if(m_lines[j].getUNS_Employee_ID() == prevLine[i].getUNS_Employee_ID())
					{
						exists = true;
						break;
					}
				}
				
				if(exists)
				{
					MUNSPeriodicCostBenefitLine line = new MUNSPeriodicCostBenefitLine(this);
					line.setUNS_Employee_ID(prevLine[i].getUNS_Employee_ID());
					if(!line.save())
						throw new AdempiereException("Cannot save Periodic Cost Benefit Line");
					
				}
			}
		}
	}
	
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		
		if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
    		options[index++] = DocumentEngine.ACTION_ReActivate;
    		options[index++] = DocumentEngine.ACTION_Void;
    	}   
		
		return index;
	}
}
