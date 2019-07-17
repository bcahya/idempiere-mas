/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import com.uns.util.ErrorMsg;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author Menjangan
 * Monthly Presence Validation.
 */
public class MUNSMonthlyPresenceVal extends X_UNS_MonthlyPresenceVal 
		implements DocAction, DocOptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8220041179345878433L;
	private MUNSDailyPresence[] m_days = null;
	private MUNSCheckInOut[] m_checks = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;

	/**
	 * @param ctx
	 * @param UNS_MonthlyPresenceVal_ID
	 * @param trxName
	 */
	public MUNSMonthlyPresenceVal(Properties ctx,
			int UNS_MonthlyPresenceVal_ID, String trxName)
	{
		super(ctx, UNS_MonthlyPresenceVal_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMonthlyPresenceVal(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}
	
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if ((newRecord || is_ValueChanged(COLUMNNAME_C_BPartner_ID) 
				|| is_ValueChanged(COLUMNNAME_C_Period_ID) 
				|| is_ValueChanged(COLUMNNAME_AD_Org_ID)) && isDuplicate())
		{
			ErrorMsg.setErrorMsg(getCtx(), 
					"Duplicate Monthly Validation Record", 
					"Duplicate Monthly Validation Record");
			return false;
		}
		
		return super.beforeSave(newRecord);
	}
	
	public MUNSDailyPresence[] getDaysValidation ()
	{
		return getDaysValidation(false);
	}
	
	public MUNSDailyPresence[] getDaysValidation (boolean requery)
	{
		if (null != m_days && !requery)
		{
			set_TrxName(m_days, get_TrxName());
			return m_days;
		}
		
		List<MUNSDailyPresence> list = Query.get(
				getCtx(), UNSHRMModelFactory.EXTENSION_ID, 
				MUNSDailyPresence.Table_Name, Table_Name + "_ID = ?", 
				get_TrxName()).setParameters(get_ID()).
				setOrderBy(MUNSDailyPresence.COLUMNNAME_PresenceDate).
				list();
		
		m_days = new MUNSDailyPresence[list.size()];
		list.toArray(m_days);
		
		return m_days;
	}
	
	public boolean isDuplicate ()
	{
		StringBuilder sb = new StringBuilder("SELECT COUNT (*) FROM ")
		.append(Table_Name).append(" WHERE ").append(COLUMNNAME_DocStatus)
		.append(" NOT IN (?,?,?,?) AND ").append(Table_Name).append("_ID <> ? ")
		.append(" AND ").append(COLUMNNAME_IsActive).append(" = ? ")
		.append(" AND ").append(COLUMNNAME_C_BPartner_ID).append(" = ? AND ")
		.append(COLUMNNAME_C_Period_ID).append(" = ? AND ")
		.append(COLUMNNAME_AD_Org_ID).append(" =?");
		
		List<Object> params = new ArrayList<Object>();
		params.add(DOCSTATUS_Completed);
		params.add(DOCSTATUS_Closed);
		params.add(DOCSTATUS_Reversed);
		params.add(DOCSTATUS_Voided);
		params.add(get_ID());
		params.add("Y");
		params.add(getC_BPartner_ID());
		params.add(getC_Period_ID());
		params.add(getAD_Org_ID());
		String sql = sb.toString();
		int record = DB.getSQLValue(get_TrxName(), sql, params);
		return record > 0;
	}
	
	public MUNSCheckInOut[] getChecksValidation ()
	{
		return getChecksValidation(false);
	}
	
	public MUNSCheckInOut[] getChecksValidation (boolean requery)
	{
		if (null != m_checks && !requery)
		{
			set_TrxName(m_checks, get_TrxName());
			return m_checks;
		}
		
		List<MUNSCheckInOut> list = Query.get(
				getCtx(), UNSHRMModelFactory.EXTENSION_ID, 
				MUNSCheckInOut.Table_Name, Table_Name + "_ID = ?", 
				get_TrxName()).setParameters(get_ID()).
				setOrderBy(MUNSCheckInOut.COLUMNNAME_CheckTime).
				list();
		
		m_checks = new MUNSCheckInOut[list.size()];
		list.toArray(m_checks);
		
		return m_checks;
	}
	
	
	public static MUNSMonthlyPresenceVal getDraftValidation (
			Properties ctx, String trxName, Timestamp date, int C_BPartner_ID, int AD_Org_ID)
	{
		date = TimeUtil.trunc(date, TimeUtil.TRUNC_DAY);
		StringBuilder sb = new StringBuilder(COLUMNNAME_C_BPartner_ID)
		.append(" = ? AND ").append("  ? BETWEEN ")
		.append(COLUMNNAME_StartDate).append(" AND ").append(COLUMNNAME_EndDate)
		.append(" AND " ).append(COLUMNNAME_DocStatus).append(" = ?  AND AD_Org_ID = ?");
		
		MUNSMonthlyPresenceVal month = Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
				sb.toString(), trxName).setParameters(
						C_BPartner_ID, date, DOCSTATUS_Drafted, AD_Org_ID).first();
		
		return month;
	}
	
	
	public static MUNSMonthlyPresenceVal getDraftValidation (
			Properties ctx, String trxName, int C_BPartner_ID, int C_Period_ID)
	{
		StringBuilder sb = new StringBuilder(COLUMNNAME_C_BPartner_ID)
		.append(" = ? AND ").append(COLUMNNAME_C_Period_ID).append(" = ? AND ")
		.append(COLUMNNAME_DocStatus).append(" = ? ");
		
		MUNSMonthlyPresenceVal month = Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
				sb.toString(), trxName).setParameters(
						C_BPartner_ID, C_Period_ID, DOCSTATUS_Drafted).first();
		
		return month;
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) 
	{
		if (docStatus.equals(DOCSTATUS_InProgress) || 
				docStatus.equals(DOCSTATUS_Completed))
		{
			options[index++] = DOCACTION_Void;
		}
		
		return index;
	}
	
	private void doAddLog (Level level, String msg)
	{
		if (log.isLoggable(level)) log.log(level, msg);
	}

	@Override
	public boolean processIt(String action) throws Exception 
	{
		doAddLog(Level.INFO, "Processing Document " + getDocumentInfo());
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	@Override
	public boolean unlockIt() 
	{
		doAddLog(Level.INFO, "Unlock it");
		setProcessed(false);
		return true;
	}

	@Override
	public boolean invalidateIt() 
	{
		doAddLog(Level.INFO, "Invalidate It");
		return false;
	}

	@Override
	public String prepareIt() 
	{
		doAddLog(Level.INFO, "Prepare Document");
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_PREPARE);
		
		if (null != m_processMsg)
		{
			return DOCSTATUS_Invalid;
		}
		
		getDaysValidation();
		getChecksValidation();
		
		if (m_days.length == 0 && m_checks.length == 0)
		{
			m_processMsg = "@No Lines@";
			return DOCSTATUS_Invalid;
		}
		
		for (MUNSCheckInOut check : m_checks)
		{
			if (check.isActive() && check.get_Value("PresenceDate") == null)
			{
				m_processMsg = "Please define Presence Date on "
						+ " Check Validation " + check.toString();
				return DOCSTATUS_Invalid;
			}
		}
		
		for (MUNSDailyPresence daily : m_days)
		{
			if ((null == daily.getFSTimeIn()
					|| null == daily.getFSTimeOut()) 
					&& !MUNSDailyPresence.PRESENCESTATUS_Izin.equals(daily.getPresenceStatus()) 
					&& !MUNSDailyPresence.PRESENCESTATUS_Mangkir.equals(daily.getPresenceStatus()) 
					&& !MUNSDailyPresence.PRESENCESTATUS_Libur.equals(daily.getPresenceStatus()))
			{
				m_processMsg = "Please define FS Time In and FS Time Out on "
						+ " Daily Validation first.";
				return DOCSTATUS_Invalid;
			}
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_PREPARE);
		if (null != m_processMsg)
		{
			return DOCSTATUS_Invalid;
		}
		
		m_justPrepared = true;
		setProcessed(true);
		setDocStatus(DOCSTATUS_InProgress);
		
		return DOCSTATUS_InProgress;
	}

	@Override
	public boolean approveIt() 
	{
		doAddLog(Level.INFO, "Approve it");
		setIsApproved(true);
		setProcessed(true);
		
		return true;
	}

	@Override
	public boolean rejectIt() 
	{
		doAddLog(Level.INFO, "Reject it");
		setProcessed(false);
		setIsApproved(false);

		return true;
	}

	@Override
	public String completeIt() 
	{
		doAddLog(Level.INFO, "Complete It");
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (status != DOCSTATUS_InProgress)
			{
				return DOCSTATUS_Invalid;
			}
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (null != m_processMsg)
		{
			return DOCSTATUS_Invalid;
		}
		
		for (int i=0; i<m_checks.length; i++)
		{
			MUNSCheckInOut check = m_checks[i];
			if (check.getUNS_DailyPresence_ID() <= 0)
			{
				MUNSDailyPresence daily = check.getCreateDaily();
				if (null == daily)
				{
					m_processMsg = "Failed to create Daily Presence";
					return DOCSTATUS_Invalid;
				}
			}
		}
		
		if (!isApproved())
		{
			approveIt();
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (null != m_processMsg)
		{
			return DOCSTATUS_Invalid;
		}
		
		setDocStatus(DOCSTATUS_Completed);
		return DOCSTATUS_Completed;
	}

	@Override
	public boolean voidIt() 
	{
		doAddLog(Level.INFO, "Void it");
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_VOID);
		if (null != m_processMsg)
		{
			return false;
		}
		
		getDaysValidation();
		getChecksValidation();
		
		for (int i=0; i<m_days.length; i++)
		{
			MUNSDailyPresence presence = m_days[i];
			MUNSMonthlyPresenceSummary monthly = (MUNSMonthlyPresenceSummary) 
					presence.getUNS_MonthlyPresenceSummary();
			if (monthly.getDocStatus().equals(DOCSTATUS_Completed)
					||	monthly.getDocStatus().equals(DOCSTATUS_Closed))
			{
				m_processMsg = "The Monthly Document of Daily Presence "
						+ presence.toString() + " has been processed";
				return false;
			}
			
			presence.set_ValueOfColumn(Table_Name + "_ID", null);
			presence.saveEx();
		}
		
		for (int i=0; i<m_checks.length; i++)
		{
			m_checks[i].set_ValueOfColumn(Table_Name + "_ID", null);
			m_checks[i].saveEx();
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_VOID);
		if (null != m_processMsg)
		{
			return false;
		}
		
		setDocStatus(DOCSTATUS_Voided);
		return true;
	}

	@Override
	public boolean closeIt() 
	{
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (null != m_processMsg)
		{
			return false;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_CLOSE);
		if (null != m_processMsg)
		{
			return false;
		}
		
		setDocStatus(DOCSTATUS_Closed);
		setDocAction(DOCACTION_None);
		
		return true;
	}

	@Override
	public boolean reverseCorrectIt() 
	{
		m_processMsg = "Invalid action Reverse.";
		return false;
	}

	@Override
	public boolean reverseAccrualIt() 
	{
		m_processMsg = "Invalid action Reverse.";
		return false;
	}

	@Override
	public boolean reActivateIt() 
	{
		return false;
	}

	@Override
	public String getSummary() 
	{
		return getDocumentInfo();
	}

	@Override
	public String getDocumentInfo() 
	{
		StringBuilder sb = new StringBuilder("[").append(get_ID())
				.append(", ").append(getDocumentNo()).append("]")
				.append(" - ").append(getStartDate()).append(" To ")
				.append(getEndDate()).append(" - Total Days Validation ")
				.append(getDaysValidation().length).append(" - ")
				.append("Total Check Validation ").
				append(getChecksValidation().length);
		String docInfo = sb.toString();
		
		return docInfo;
	}

	@Override
	public File createPDF() 
	{
		return null;
	}

	@Override
	public String getProcessMsg() 
	{
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() 
	{
		return 0;
	}

	@Override
	public int getC_Currency_ID() 
	{
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() 
	{
		return null;
	} 
	
	/**
	 * 
	 * @param po
	 * @param columnDate
	 * @param startDate
	 * @param endDate
	 * @param C_BPartner_ID
	 * @param C_Period_ID
	 * @return
	 */
	public static MUNSMonthlyPresenceVal createValidation (
			PO po, String columnDate, Timestamp startDate, Timestamp endDate, 
			int C_BPartner_ID, int C_Period_ID)
	{
		MUNSMonthlyPresenceVal validation =
				MUNSMonthlyPresenceVal.getDraftValidation(
						po.getCtx(), po.get_TrxName(), 
						(Timestamp) po.get_Value(columnDate), 
						C_BPartner_ID, po.getAD_Org_ID());
		
		startDate = TimeUtil.trunc(startDate, TimeUtil.TRUNC_DAY);
		endDate = TimeUtil.trunc(endDate, TimeUtil.TRUNC_DAY);
		
		if (null != validation)
		{
			po.set_ValueOfColumn(COLUMNNAME_UNS_MonthlyPresenceVal_ID, 
					validation.get_ID());
			po.saveEx();
			if(startDate.before(validation.getStartDate()))
				validation.setStartDate(startDate);
			if(endDate.after(validation.getEndDate()))
				validation.setEndDate(endDate);
			return validation;
		}
		
		validation = new MUNSMonthlyPresenceVal(
				po.getCtx(), 0, po.get_TrxName());
		validation.setAD_Client_ID(po.getAD_Client_ID());
		validation.setAD_Org_ID(po.getAD_Org_ID());
		validation.setC_BPartner_ID(C_BPartner_ID);
		try 
		{
			validation.setC_Period_ID(C_Period_ID);
			validation.setStartDate(startDate);
			validation.setEndDate(endDate);
			validation.setDateDoc(new Timestamp(System.currentTimeMillis()));
			validation.saveEx();
			
			po.set_ValueOfColumn(COLUMNNAME_UNS_MonthlyPresenceVal_ID, 
					validation.get_ID());
			po.saveEx();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}

		return validation;
	}
}
