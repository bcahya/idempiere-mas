/**
 * 
 */
package com.uns.model.override;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import org.compiere.model.MNonBusinessDay;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.I_UNS_Contract_Recommendation;
import com.uns.model.MUNSContractRecommendation;
import com.uns.model.MUNSLeavePermissionTrx;
import com.uns.model.MUNSPayRollLine;
import com.uns.model.MUNSProductionPayConfig;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSResourceWorkerLine;
import com.uns.model.MUNSSlotType;
import com.uns.model.MUNSWorkerPresence;
import com.uns.model.factory.UNSHRMModelFactory;
import com.uns.model.factory.UNSIntegrationModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSHalfPeriodPresence extends com.uns.model.MUNSHalfPeriodPresence {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public MUNSHalfPeriodPresence(Properties ctx, int id, String trxName) {
		super(ctx, id, trxName);
	}
	public MUNSHalfPeriodPresence(Properties ctx, ResultSet rs, String trxName){
		super(ctx, rs, trxName);
	}
	
	/**
	 * WorkerHalfPeriodPresence is unique for each of department's work-center,
	 * instead use get(ctx, UNS_Employee_ID, date, AD_Org_ID, workCenter_ID, trxName).
	 * 
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @param date
	 * @param AD_Org_ID
	 * @param trxName
	 * @return
	 * @deprecated 
	 */
	public static MUNSHalfPeriodPresence get(
			Properties ctx, int UNS_Employee_ID, Timestamp date
			, int AD_Org_ID, String trxName)
	{
		return Query.get(
				ctx, UNSIntegrationModelFactory.EXTENSION_ID, MUNSHalfPeriodPresence.Table_Name
				,MUNSHalfPeriodPresence.COLUMNNAME_UNS_Employee_ID + " = "  + UNS_Employee_ID 
				+ " AND '" +  date + "' BETWEEN " + MUNSHalfPeriodPresence.COLUMNNAME_StartDate 
				+ " AND " + MUNSHalfPeriodPresence.COLUMNNAME_EndDate 
				+ " AND " + MUNSHalfPeriodPresence.COLUMNNAME_AD_Org_ID + " = " + AD_Org_ID, trxName)
				.firstOnly();
	}

	/**
	 * 
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @param date
	 * @param AD_Org_ID
	 * @param workCenter_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSHalfPeriodPresence get(
			Properties ctx, int UNS_Employee_ID, Timestamp date, 
			int AD_Org_ID, int workCenter_ID, String trxName)
	{
		String whereClause = "UNS_Employee_ID = ? AND ? BETWEEN StartDate AND EndDate " +
				"AND AD_Org_ID=? AND UNS_Resource_ID=?";
		
		return Query.get(ctx, UNSHRMModelFactory.EXTENSION_ID, MUNSHalfPeriodPresence.Table_Name,
						 whereClause, trxName)
				.setParameters(UNS_Employee_ID, date, AD_Org_ID, workCenter_ID)
				.firstOnly();
	}

	/**
	 * 
	 * @param date
	 * @param UNS_Resource_ID
	 * @return
	 */
	@Override
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
		wp.setPermissionType(AbsenceType);
		wp.setPresenceStatus(presenceStatus);
		wp.setProductionQty(BigDecimal.ZERO);
		wp.setReceivableAmt(BigDecimal.ZERO);
		wp.saveEx();
		
		return true;
	}

	/**
	 * 
	 * @param date
	 * @param UNS_Resource_ID
	 * @return
	 */
	public void createAbsence(
			Timestamp date, MUNSLeavePermissionTrx leavePerm, MUNSResourceWorkerLine rscWorkerLine)
	{
		String AbsenceType = leavePerm.getLeaveType();
		/*
		if(leavePerm.getLeaveType().equals(MUNSLeavePermissionTrx.LEAVETYPE_LeaveCuti))
		{
			AbsenceType = MUNSWorkerPresence.PRESENCESTATUS_Izin;
		}
		else if(leavePerm.getLeaveType().equals(MUNSLeavePermissionTrx.LEAVETYPE_MaternityHamilPlusMelahirkan))
		{
			AbsenceType = MUNSWorkerPresence.PRESENCESTATUS_Izin;
		}
		else if(leavePerm.getLeaveType().equals(MUNSLeavePermissionTrx.LEAVETYPE_Permission))
		{
			if(leavePerm.getPermissionType().equals(MUNSLeavePermissionTrx.PERMISSIONTYPE_Dinas)
			   || leavePerm.getPermissionType().equals(MUNSLeavePermissionTrx.PERMISSIONTYPE_NonDinas))
			{
				AbsenceType = MUNSWorkerPresence.PRESENCESTATUS_Izin;
			}
			else if(leavePerm.getPermissionType().endsWith(MUNSLeavePermissionTrx.PERMISSIONTYPE_SickLeave))
			{
				AbsenceType = MUNSWorkerPresence.PRESENCESTATUS_IstirahatSakit;
			}
			else if(leavePerm.getPermissionType().equals(MUNSLeavePermissionTrx.PERMISSIONTYPE_WorkAccident))
			{
				AbsenceType = MUNSWorkerPresence.PRESENCESTATUS_SakitKecelakaanKerja;
			}
			else 
				throw new AdempiereUserError("UNKNOWN PERMISSION TYPE " 
									+ leavePerm.getPermissionType());
		}
		else
			throw new AdempiereUserError("UNKNOWN LEAVE TYPE " + leavePerm.getLeaveType());
		*/
		BigDecimal receivableAmt = Env.ZERO;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		MUNSResource rsc = (MUNSResource) rscWorkerLine.getUNS_Resource();
		MUNSSlotType slotType = rsc.getSlotType();
		
		boolean isWorkDay = slotType.IsWorkDay(cal.get(Calendar.DAY_OF_WEEK));
		boolean isNonBusinesDay = MNonBusinessDay.isNonBusinessDay(getCtx(), date, getAD_Org_ID(), get_TrxName());		
		
		MUNSProductionPayConfig payConfig = MUNSProductionPayConfig
				.get(getCtx(), rscWorkerLine.getAD_Org_ID(), date, get_TrxName());
		MUNSPayRollLine payrollLine = payConfig.getDailyPayrollLineOf(
				rscWorkerLine.getUNS_Job_Role_ID(), rscWorkerLine.getPayrollTerm());
		
		I_UNS_Contract_Recommendation contract = getUNS_Employee()
				.getUNS_Contract_Recommendation();
		
		if(!contract.getNextContractType().equals(MUNSContractRecommendation.NEXTCONTRACTTYPE_Borongan)
				&& !contract.getNextContractType().equals(MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganCV)
				&& !contract.getNextContractType().equals(MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarian)
				&& !contract.getNextContractType().equals(MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarianCV)
				&& (AbsenceType.equals(MUNSLeavePermissionTrx.LEAVETYPE_SuratKeteranganIstirahat)
						|| AbsenceType.equals(MUNSLeavePermissionTrx.LEAVETYPE_SuratKeteranganIstirahatKecelakaanKerja)))
		
			
//		if (!rscWorkerLine.getEmploymentStatus().equals(MUNSResourceWorkerLine.EMPLOYMENTSTATUS_Borongan)
//			&& (AbsenceType.equals(MUNSLeavePermissionTrx.LEAVETYPE_SuratKeteranganIstirahat)
//				|| AbsenceType.equals(MUNSLeavePermissionTrx.LEAVETYPE_SuratKeteranganIstirahatKecelakaanKerja)))
		{
			if ((payConfig.isPayableSKK() 
				&& AbsenceType.equals(MUNSLeavePermissionTrx.LEAVETYPE_SuratKeteranganIstirahatKecelakaanKerja))
					|| payConfig.isPayableSK()) 
			{
				// Hanya set receivable amount utk yg SK, SKK, dan tanggal ini merupakan 
				// hari kerja/bukan hari libur.
				if (!isNonBusinesDay && isWorkDay)
				{
					receivableAmt = (payrollLine != null)? payrollLine.getPayFullDay() 
							: payConfig.getPayFullDay(isSupervised());
				}
			}
		}
		
		MUNSWorkerPresence wp = getWorkerPresence(date, 0);
		if(null == wp)
		{
			wp = new MUNSWorkerPresence(getCtx(), 0, get_TrxName());
			wp.setAD_Org_ID(getAD_Org_ID());
			wp.setUNS_HalfPeriod_Presence_ID(get_ID());
			wp.setWorkDate(date);
			
			if (isNonBusinesDay)
				wp.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburNasional);
			else if (!isWorkDay)
				wp.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburMingguan);
			else
				wp.setDayType(MUNSWorkerPresence.DAYTYPE_HariKerjaBiasa);
			
			wp.setPayrollTerm(rscWorkerLine.getPayrollTerm());
			wp.setUNS_Job_Role_ID(rscWorkerLine.getUNS_Job_Role_ID());
			wp.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_Izin);
			wp.setPermissionType(AbsenceType);
			wp.setReceivableAmt(receivableAmt);
			wp.setTotalReceivableAmt(receivableAmt);
			wp.setProductionQty(BigDecimal.ZERO);
		}
		else
		{ 
			if (wp.getPresenceStatus().equals(MUNSWorkerPresence.PRESENCESTATUS_HalfDay))
			{
				//TODO subject utk dicek ulang berdasarkan proposal, apakah yg sdh masuk 1/2 hari dianggap full atau
				//tetap setengah hari dg tipe permission sesuai dg pada leave permission.
				//wp.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_FullDay);
			}
			else if (wp.getPresenceStatus().equals(MUNSWorkerPresence.PRESENCESTATUS_Mangkir))
			{
				wp.setPresenceStatus(AbsenceType);
				wp.setReceivableAmt(receivableAmt);
				wp.setTotalReceivableAmt(receivableAmt);
				wp.setProductionQty(BigDecimal.ZERO);
			}
		}
		wp.setPermissionType(leavePerm.getLeaveType());
		wp.saveEx();
		
		//if Leave Permission is VOID
		if (MUNSLeavePermissionTrx.ACTION_Void.equals(leavePerm.getDocAction())
				&& MUNSWorkerPresence.PRESENCESTATUS_Izin.equals(wp.getPresenceStatus())){
			if (!isNonBusinesDay && isWorkDay)
			{
				receivableAmt = (payrollLine != null)? payrollLine.getPayFullDay() 
						: payConfig.getPayFullDay(isSupervised());
				
				wp.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_FullDay);
				wp.setPermissionType(null);
				wp.setReceivableAmt(receivableAmt);
				wp.setTotalReceivableAmt(receivableAmt);
				wp.setProductionQty(new BigDecimal(7));
				wp.saveEx();
			}
		}
	}
}
