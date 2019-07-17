/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;

import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author Burhani Adam
 *
 */
public class MUNSWorkHoursConfig extends X_UNS_WorkHoursConfig implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3176697109650974491L;

	/**
	 * @param ctx
	 * @param UNS_WorkHoursConfig_ID
	 * @param trxName
	 */
	public MUNSWorkHoursConfig(Properties ctx, int UNS_WorkHoursConfig_ID,
			String trxName) {
		super(ctx, UNS_WorkHoursConfig_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSWorkHoursConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param ctx
	 * @param UNS_Resource_ID
	 * @param trxName
	 * @param Record_ID use this parameter if you want to check duplicate, else -1 OR 0
	 * @return {@link MUNSWorkHoursConfig}
	 */
	public static MUNSWorkHoursConfig getByDate(Properties ctx, Timestamp date, int Record_ID, String trxName)
	{
		MUNSWorkHoursConfig config = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" AND ").append("?").append(" BETWEEN ")
			.append(COLUMNNAME_ValidFrom).append(" AND ")
				.append(COLUMNNAME_ValidTo).append(" AND")
					.append(COLUMNNAME_DocStatus).append(" IN ('CO', 'CL'");
		if(Record_ID > 0)
			sb.append(" AND ").append(COLUMNNAME_UNS_WorkHoursConfig_ID)
				.append("<>?");
		
		config = new Query(ctx, Table_Name, sb.toString(), trxName)
					.setParameters(date).first();
		
		return config;
	}
	
	public static MUNSWorkHoursConfig getByEmployee(Properties ctx, int UNS_Resource_ID, 
			int UNS_Employee_ID, Timestamp date, String trxName)
	{
		MUNSWorkHoursConfig config = null;
		
		date = TimeUtil.trunc(date, TimeUtil.TRUNC_DAY);
		String wc = "? BETWEEN UNS_WorkHoursConfig.ValidFrom AND UNS_WorkHoursConfig.ValidTo"
				+ " AND EXISTS (SELECT 1 FROM UNS_WorkHoursConfig_Line wl WHERE"
				+ " UNS_WorkHoursConfig.UNS_WorkHoursConfig_ID = wl.UNS_WorkHoursConfig_ID"
				+ " AND wl.UNS_Employee_ID = ? AND wl.IsActive = 'Y')"
				+ " AND UNS_WorkHoursConfig.DocStatus IN ('CO', 'CL')";
		
		if(UNS_Employee_ID > 0)
			config = new Query(ctx, Table_Name, wc, trxName)
					.setParameters(date, UNS_Employee_ID).first();
		if(config == null && UNS_Resource_ID > 0)
		{
			wc = wc.replace("UNS_Employee_ID", "UNS_Resource_ID");
			config = new Query(ctx, Table_Name, wc, trxName)
							.setParameters(date, UNS_Resource_ID).first();
		}
		
		return config;
	}
	
	public boolean beforeSave(boolean newRecord)
	{
//		if(newRecord
//				|| is_ValueChanged(COLUMNNAME_ValidFrom)
//					|| is_ValueChanged(COLUMNNAME_ValidTo))
//		{
//			MUNSWorkHoursConfig otherConfig = MUNSWorkHoursConfig.getByDate(
//												getCtx(), getValidFrom(), get_ID(), get_TrxName());
//			if(otherConfig != null)
//			{
//				log.saveError("Error", "Duplicate configuration with other configuration number " 
//											+ otherConfig.getDocumentNo());
//				return false;
//			}
//		}
		
		if(newRecord
				|| is_ValueChanged(COLUMNNAME_ValidFrom)
					|| is_ValueChanged(COLUMNNAME_ValidTo))
		{
			if(getValidFrom().after(getValidTo()))
			{
				log.saveError("Error", "Valid To should be greater than of the Valid From");
				return false;
			}
		}
		
		return true;
	}
	
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUNSPointSchema[");
		sb.append(get_ID()).append("-").append(getDocumentNo())
			.append(",Status=").append(getDocStatus()).append(",Action=").append(getDocAction())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 * 	Get Document Info
	 *	@return document info
	 */
	public String getDocumentInfo()
	{
		return Msg.getElement(getCtx(), "UNS_PointSchema_ID") + " " + getDocumentNo();
	}	//	getDocumentInfo
	
	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	public File createPDF ()
	{
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
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF
		
	protected boolean beforeDelete()
	{
		String sql = "DELETE FROM UNS_WorkHoursConfig_Line WHERE UNS_WorkHoursConfig_ID = ?";
		DB.executeUpdate(sql, get_ID(), get_TrxName());
		return true;
	}

	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (processAction, getDocAction());
	}	//	process
	
	/**	Process Message 			*/
	private String			m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean 		m_justPrepared = false;

	/**
	 * 	Unlock Document.
	 * 	@return true if success 
	 */
	public boolean unlockIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("unlockIt - " + toString());
		return true;
	}	//	unlockIt
	
	/**
	 * 	Invalidate Document
	 * 	@return true if success 
	 */
	public boolean invalidateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("invalidateIt - " + toString());
		return true;
	}	//	invalidateIt
	
	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid) 
	 */
	public String prepareIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		MUNSWorkHoursConfigLine[] lines = getLine();
		if(lines.length == 0)
		{
			m_processMsg = "Please define resource or employee for process this document";
			return DocAction.STATUS_Invalid;
		}
		else
		{
			//re-check duplicate every lines;
			for(MUNSWorkHoursConfigLine line : lines)
			{
				if(line.isDuplicateRecord())
				{
					m_processMsg = "Duplicate record in one adjusment";
					return DocAction.STATUS_Invalid;
				}
				if(line.isDuplicateWithOtherConfiguration())
				{
					m_processMsg = "Duplicate record with other adjusment";
					return DocAction.STATUS_Invalid;
				}
			}
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}	//	prepareIt
	
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		if(!org.compiere.util.Util.isEmpty(updateDaily(), true))
		{
			m_processMsg = "failed when trying update daily";
			return DocAction.STATUS_Invalid;
		}
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		//
		setProcessed(true);
		setDocAction(ACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt

	/**
	 * 	Void Document.
	 * 	Same as Close.
	 * 	@return true if success 
	 */
	public boolean voidIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("voidIt - " + toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		if (!closeIt())
			return false;
		
		if(!org.compiere.util.Util.isEmpty(updateDaily(), true))
		{
			m_processMsg = "failed when trying update daily";
			return false;
		}
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	voidIt
	
	/**
	 * 	Close Document.
	 * 	Cancel not delivered Qunatities
	 * 	@return true if success 
	 */
	public boolean closeIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("closeIt - " + toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	closeIt
	
	/**
	 * 	Reverse Correction
	 * 	@return true if success 
	 */
	public boolean reverseCorrectIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reverseCorrectIt - " + toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	reverseCorrectionIt
	
	/**
	 * 	Reverse Accrual - none
	 * 	@return true if success 
	 */
	public boolean reverseAccrualIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reverseAccrualIt - " + toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;				
		
		return false;
	}	//	reverseAccrualIt
	
	/** 
	 * 	Re-activate
	 * 	@return true if success 
	 */
	public boolean reActivateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reActivateIt - " + toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

	//	setProcessed(false);
		if (! reverseCorrectIt())
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		return true;
	}	//	reActivateIt

	@Override
	public String getSummary() {
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

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public MUNSWorkHoursConfigLine[] getLine()
	{
		java.util.List<MUNSWorkHoursConfigLine> line =
				com.uns.base.model.Query.get(getCtx(), UNSHRMModelFactory.EXTENSION_ID, 
						MUNSWorkHoursConfigLine.Table_Name, COLUMNNAME_UNS_WorkHoursConfig_ID + "=?", 
							get_TrxName()).setParameters(get_ID()).list();
		
		return line.toArray(new MUNSWorkHoursConfigLine[line.size()]);
	}
	
	@Override
	public int customizeValidActions(String docStatus, Object processing, String orderType, String isSOTrx,
			int AD_Table_ID, String[] docAction, String[] options, int index)
	{
		if (docStatus.equals(DocAction.STATUS_Completed))
			options[index++] = DocAction.ACTION_Void;
		
		return index;
	}
	
	private String updateDaily()
	{
		String sql = "UPDATE UNS_DailyPresence d SET IsNeedAdjustRule = 'Y' WHERE"
				+ " EXISTS (SELECT 1 FROM UNS_MonthlyPresenceSummary m WHERE"
				+ " m.UNS_MonthlyPresenceSummary_ID = d.UNS_MonthlyPresenceSummary_ID"
				+ " AND (m.UNS_Employee_ID IN (SELECT COALESCE(wcl.UNS_Employee_ID,0)"
				+ " FROM UNS_WorkHoursConfig_Line wcl WHERE wcl.UNS_WorkHoursConfig_ID=?)"
				+ " OR m.UNS_Employee_ID IN (SELECT rwl.Labor_ID FROM UNS_Resource_WorkerLine rwl"
				+ " WHERE EXISTS (SELECT 1 FROM UNS_WorkHoursConfig_Line wcl WHERE"
				+ " rwl.UNS_Resource_ID = wcl.UNS_Resource_ID AND wcl.UNS_WorkHoursConfig_ID=?))))"
				+ " AND d.PresenceDate BETWEEN ? AND ?";
		if(DB.executeUpdate(sql, new Object[]{get_ID(), get_ID(), 
				getValidFrom(), getValidTo()}, false, get_TrxName()) < 0)
			return "error";
		return "";
	}
}