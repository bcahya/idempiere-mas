package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.IUNSApprovalInfo;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

public class MUNSOTConfirmation extends X_UNS_OTConfirmation implements
		DocAction, DocOptions, IUNSApprovalInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5918918763355430460L;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	
	public MUNSOTConfirmation(Properties ctx, int UNS_OTConfirmation_ID,
			String trxName) 
	{
		super(ctx, UNS_OTConfirmation_ID, trxName);
	}

	public MUNSOTConfirmation (MUNSOTRequest request)
	{
		this (request.getCtx(), 0, request.get_TrxName());
		setClientOrg(request);
		setUNS_OTRequest_ID(request.get_ID());
	}
	public MUNSOTConfirmation(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}

	@Override
	public List<Object[]> getApprovalInfoColumnClassAccessable() 
	{
		//TODO define approval column info
		List<Object[]> retVal = new ArrayList<Object[]>();
		return retVal;
	}

	@Override
	public String[] getDetailTableHeader() 
	{
		//TODO define table headers for approval info
		String[] headers = new String[] {
			
		};
		
		return headers;
	}

	@Override
	public List<Object[]> getDetailTableContent() 
	{
		List<Object[]> contents = new ArrayList<Object[]>();
		//TODO define approval info content
		return contents;
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) 
	{
		if (docStatus.equals(DocAction.STATUS_Drafted))
		{
			options[index++] = DocAction.ACTION_Prepare;
		}
		
		if (docStatus.equals(DocAction.STATUS_Completed))
		{
			options[index++] = DocAction.ACTION_Void;
		}
		return index;
	}

	@Override
	public boolean processIt(String action) throws Exception 
	{
		doLog(Level.INFO, "Processing Document");
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	@Override
	public boolean unlockIt() 
	{
		doLog(Level.INFO, "Unlock Document");
		return true;
	}

	@Override
	public boolean invalidateIt() 
	{
		doLog(Level.INFO, "Invalidate Document");
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() 
	{
		doLog(Level.INFO, "Preparing Document");	
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (null != m_processMsg)
		{
			return DOCSTATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_PREPARE);
		if (null != m_processMsg)
		{
			return DOCSTATUS_Invalid;
		}
		
		setDocAction(DOCACTION_Complete);
		setProcessed(true);
		return DOCSTATUS_InProgress;
	}

	@Override
	public boolean approveIt() 
	{
		doLog(Level.INFO, "Approving Document");
		setProcessed(true);
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() 
	{
		doLog(Level.INFO, "Rejecting Document");
		setProcessed(false);
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() 
	{
		doLog(Level.INFO, "Completing Document");
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DOCSTATUS_InProgress.equals(status))
				return status;
		}
		
		try
		{
			m_processMsg = processRequest(DOCACTION_Complete);
			if (null != m_processMsg)
			{
				return DOCSTATUS_Invalid;
			}
		}
		catch (Exception ex)
		{
			m_processMsg = ex.getMessage();
			return DOCSTATUS_Invalid;
		}
		
//		upDailyPreserence(getUNS_Employee().getAttendanceName());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_COMPLETE);
		if (null != m_processMsg)
		{
			return DOCSTATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_COMPLETE);
		if (null != m_processMsg)
		{
			return DOCSTATUS_Invalid;
		}
		
		if (!isApproved())
		{
			approveIt();
		}
		
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DOCSTATUS_Completed;
	}

	@Override
	public boolean voidIt() 
	{
		doLog(Level.INFO, "Void Document");
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_VOID);
		if (null != m_processMsg)
		{
			return false;
		}
		
		try
		{
			m_processMsg = processRequest(DOCACTION_Void);
			if (null != m_processMsg)
			{
				return false;
			}
		}
		catch (Exception ex)
		{
			m_processMsg = ex.getMessage();
			return false;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_VOID);
		if (null != m_processMsg)
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean closeIt() {
		doLog(Level.INFO, "Closing Document");
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_CLOSE);
		if (null != m_processMsg)
		{
			return false;
		}
		
		setProcessed(true);
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_CLOSE);
		if (null != m_processMsg)
		{
			return false;
		}
		
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public boolean reverseCorrectIt() 
	{
		return voidIt();
	}

	@Override
	public boolean reverseAccrualIt() 
	{
		return voidIt();
	}

	@Override
	public boolean reActivateIt() 
	{
		doLog(Level.WARNING, "Disallowed Action, ReActivate-It");
		return false;
	}

	@Override
	public String getSummary() 
	{
		StringBuilder sb = new StringBuilder("Over Time Confirmation no ")
		.append(getDocumentNo());
		return sb.toString();
	}

	@Override
	public String getDocumentInfo() 
	{
		return getSummary();
	}

	@Override
	public File createPDF() 
	{
		doLog(Level.WARNING, "Not implemented method. createPDF()");
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
		return Env.ZERO;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static MUNSOTConfirmation getCreate(MUNSOTRequest request)
	{
		MUNSOTConfirmation otconfirmation = 
				Query.get(request.getCtx(), UNSHRMModelFactory.EXTENSION_ID ,
						MUNSOTConfirmation.Table_Name,
						"UNS_OTRequest_ID=?", request.get_TrxName())
						.setParameters(request.get_ID())
						.first();
						
		if (otconfirmation !=null)
		{
			return otconfirmation ;
		}
		
		otconfirmation = new MUNSOTConfirmation (request);
		otconfirmation.setDateDoc(request.getRequestDate());
		otconfirmation.setClientOrg(request);
		otconfirmation.setStartTime(request.getStartTime());
		otconfirmation.setEndTime(request.getEndTime());
		otconfirmation.setConfirmedHours(request.getRequestedHours());
		otconfirmation.saveEx();
		
		String sql = "UPDATE UNS_OTLine SET UNS_OTConfirmation_ID = ? WHERE UNS_OTRequest_ID = ?";
		DB.executeUpdate(sql, new Object[]{otconfirmation.get_ID(), 
				request.get_ID()}, false, request.get_TrxName());
				
		return otconfirmation;
	}
	
	private void doLog (Level level, String value)
	{
		if (!log.isLoggable(level))
		{
			return;
		}
		
		log.log(level, value);
	}
	
	private String processRequest (String action) throws Exception
	{
		//TODO setForced to request before run action.
		MUNSOTRequest request = new MUNSOTRequest(
				getCtx(), getUNS_OTRequest_ID(), get_TrxName());
		
		if(!request.checkMonthlyEmployee())
			return request.getProcessMsg();
		
		if (request.getDocStatus().equals(action)) {
			// do nothing.
			return null;
		}
		
		if (DOCACTION_Complete.equals(action))
		{
			request.setConfirmedHours(getConfirmedHours());
			request.saveEx();
		}
		
		request.setForce(true);
		boolean ok = request.processIt(action);
		
		if (!ok)
		{
			return request.getProcessMsg();
		}
		
		request.saveEx();
		
		return null;
	}
	
	public boolean isComplete ()
	{
		boolean complete = getDocStatus().equals(DOCSTATUS_Completed)
				|| getDocStatus().equals(DOCSTATUS_Voided)
				|| getDocStatus().equals(DOCSTATUS_Closed)
				|| getDocStatus().equals(DOCSTATUS_Reversed);
		
		return complete;
	}
	
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord || is_ValueChanged(COLUMNNAME_StartTime)
				|| is_ValueChanged(COLUMNNAME_EndTime) || is_ValueChanged(COLUMNNAME_BreakTime))
		{
			long start = getStartTime().getTime();
			long end = getEndTime().getTime();
			double range = (double) end - (double) start;
			range = range / 1000 / 60 / 60;
			BigDecimal value = new BigDecimal(range);
			value = value.subtract(getBreakTime());
			setConfirmedHours(value);
		}
		
		return super.beforeSave(newRecord);
	}
	
	public void upDailyPreserence(String attName)
	{
//		java.sql.Timestamp in = getStartTime();
//		java.sql.Timestamp out = getEndTime();
//		int tolerance = MSysConfig.getIntValue(MSysConfig.TOLERANCE_OF_DIFFERENCE_TIME, 15);
//		String whereClause = "AttendanceName = ? AND PresenceDate = ?"
//				+ " AND CheckTime BETWEEN '" + in.toString() + "'::timestamp - INTERVAL '"+ tolerance + " MINUTES' "
//						+ "AND '" + in.toString() + "'::timestamp"
//								+ " + INTERVAL '"+ tolerance + " MINUTES' ";
//		ArrayList<Object> params = new ArrayList<>();
//		params.add(attName);
//		params.add(getDateDoOT());
//		MUNSCheckInOut[] ioLM =
//				MUNSCheckInOut.gets(getCtx(), get_TrxName(), 
//						whereClause, params, MUNSCheckInOut.COLUMNNAME_CheckTime);
//		
//		if(ioLM.length > 0)
//		{
//			ioLM[0].setCheckType(X_UNS_CheckInOut.CHECKTYPE_LemburMasuk);
//			if(ioLM[0].getCheckTime().after(getStartTime()))
//				in = ioLM[0].getCheckTime();
//			ioLM[0].setPresenceDate(in);
//			ioLM[0].saveEx();
//		}
//		
//		whereClause = whereClause.replace(getStartTime().toString(), out.toString());
//		MUNSCheckInOut[] ioLK =
//				MUNSCheckInOut.gets(getCtx(), get_TrxName(), 
//						whereClause, params, MUNSCheckInOut.COLUMNNAME_CheckTime + " DESC");
//
//		if(ioLK.length > 0)
//		{
//			ioLK[0].setCheckType(X_UNS_CheckInOut.CHECKTYPE_LemburKeluar);
//			if(ioLK[0].getCheckTime().before(getEndTime()))
//				out = ioLK[0].getCheckTime();
//			ioLK[0].setPresenceDate(out);
//			ioLK[0].saveEx();
//		}
//		
//		if((ioLK.length > 0 || ioLM.length > 0))
//		{
//			MUNSDailyPresence day = MUNSDailyPresence.getCreate(getDateDoOT(), (MUNSEmployee) getUNS_Employee());
//			if(day != null)
//			{
//				long start = in.getTime();
//				long end = out.getTime();
//				double range = (double) end - (double) start;
//				range = range / 1000 / 60 / 60;
//				day.setOvertime(day.getOvertime().add(BigDecimal.valueOf(range)));
//				day.saveEx();
//			}
//			if(ioLK.length > 0)
//			{
//				ioLK[0].setUNS_DailyPresence_ID(day.get_ID());
//				ioLM[0].setUNS_MonthlyPresenceVal_ID(-1);
//				ioLK[0].saveEx();
//			}
//			if(ioLM.length > 0)
//			{
//				ioLM[0].setUNS_DailyPresence_ID(day.get_ID());
//				ioLK[0].setUNS_MonthlyPresenceVal_ID(-1);
//				ioLM[0].saveEx();
//			}
//		}
	}
	
	public static MUNSOTConfirmation[] 
			gets(Properties ctx, String whereClause, List<Object> params, String trxName)
	{
		Query query = new Query(ctx, Table_Name, whereClause, trxName);
		if (params != null && params.size() > 0)
		{
			query.setParameters(params);
		}
		
		List<MUNSOTConfirmation> list = query.list();
		MUNSOTConfirmation[] confirms = new MUNSOTConfirmation[list.size()];
		list.toArray(confirms);
		
		return confirms;
	}
	
	public static void main(String[] trs)
	{
		String a = "Adam Burhani";
		a = a.replace("Burhani", "Burhany");
		System.out.println(a);
	}

	@Override
	public int getTableIDDetail() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isShowAttachmentDetail() {
		// TODO Auto-generated method stub
		return false;
	}
}