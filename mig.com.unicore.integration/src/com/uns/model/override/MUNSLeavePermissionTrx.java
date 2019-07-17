/**
 * 
 */
package com.uns.model.override;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.compiere.process.DocAction;

import com.uns.model.I_UNS_Employee;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSResourceWorkerLine;


/**
 * @author menjangan
 *
 */
public class MUNSLeavePermissionTrx extends com.uns.model.MUNSLeavePermissionTrx {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_processMsg = null;
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_LeavePermissionTrx_ID
	 * @param trxName
	 */
	public MUNSLeavePermissionTrx(Properties ctx, int UNS_LeavePermissionTrx_ID, String trxName) {
		super(ctx, UNS_LeavePermissionTrx_ID, trxName);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLeavePermissionTrx(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	@Override
	public String completeIt()
	{
		super.completeIt();
		if(MUNSEmployee.EMPLOYMENTTYPE_SubContract.equals(getUNS_Employee().getEmploymentType()))
			m_processMsg = updateWorkerPresence();
		
		if(null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		return DocAction.STATUS_Completed;
	}
	
	/**
	 * 
	 * @return
	 */
	protected String updateWorkerPresence()
	{
		MUNSResourceWorkerLine[] workerInResources = 
				MUNSResourceWorkerLine.getNonAdditionalWorkerOf(
						getCtx(), 
						getUNS_Employee_ID(), 
						get_TrxName());
		
		if(null == workerInResources || workerInResources.length == 0)
			return null;
		
		Calendar leaveCalStart = Calendar.getInstance();
		leaveCalStart.setTimeInMillis(getLeaveDateStart().getTime());
		leaveCalStart.set(Calendar.AM, Calendar.AM);
		leaveCalStart.set(Calendar.HOUR, 0);
		leaveCalStart.set(Calendar.MINUTE, 1);
		
		Calendar leaveCalEnd = Calendar.getInstance();
		leaveCalEnd.setTimeInMillis(getLeaveDateEnd().getTime());
		leaveCalEnd.set(Calendar.PM, Calendar.PM);
		leaveCalEnd.set(Calendar.HOUR, 11);
		leaveCalEnd.set(Calendar.MINUTE, 58);
		
		long leaveStartMillis = leaveCalStart.getTimeInMillis();
		
		long leaveEndMillis = leaveCalEnd.getTimeInMillis();
		
		for(MUNSResourceWorkerLine rscWorkerLine : workerInResources)
		{	
			MUNSResource wc = rscWorkerLine.getWorkCenter();
			
			MUNSHalfPeriodPresence workerPresence = null;
			
			while(leaveStartMillis <= leaveEndMillis)
			{
				Timestamp leaveTS = new Timestamp(leaveCalStart.getTimeInMillis());
				if (workerPresence == null 
					|| (!workerPresence.isInRange(new Timestamp(leaveStartMillis))))
				{
					workerPresence = MUNSHalfPeriodPresence.get(
							getCtx(), getUNS_Employee_ID(), leaveTS, 
							rscWorkerLine.getAD_Org_ID(), wc.get_ID(), get_TrxName());
					
					if(null == workerPresence)
					{
						I_UNS_Employee employee = getUNS_Employee();
						//TODO upayakan untuk secara otomatis di buat jk null, tapi ada kemungkinan memang tdk ada
						//krn worker sudah tidak lagi menjadi non-additional worker.
						return "Cannot find Worker Period Presence for worker " + employee.getName() 
								+ "(" + employee.getValue() + ") of date " + leaveTS;
					}
				}
				
				workerPresence.createAbsence(leaveTS, this, rscWorkerLine);
				
				leaveCalStart.add(Calendar.DATE, 1);
				leaveStartMillis = leaveCalStart.getTimeInMillis();
			}
		}
		return null;
	}
}
