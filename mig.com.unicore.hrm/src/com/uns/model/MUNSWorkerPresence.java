/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Properties;

import org.compiere.model.MNonBusinessDay;
import org.compiere.model.MOrg;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSWorkerPresence extends X_UNS_Worker_Presence {

	/**
	 * 
	 */
	private static final long serialVersionUID = 914996641669728899L;
	private boolean isWorkDay = true;
	
	private MUNSHalfPeriodPresence m_parent = null;
	
	/**
	 * @param ctx
	 * @param UNS_Worker_Presence_ID
	 * @param trxName
	 */
	public MUNSWorkerPresence(Properties ctx, int UNS_Worker_Presence_ID, String trxName) {
		super(ctx, UNS_Worker_Presence_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSWorkerPresence(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * Get the worker presence for the production's worker.
	 * 
	 * @param ctx
	 * @param UNS_Production_Worker_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSWorkerPresence getOfProductionWorker(
			Properties ctx, int UNS_Production_Worker_ID, String trxName) 
	{
		MUNSWorkerPresence retPresence = 
				Query.get(ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
						  "UNS_Production_Worker_ID=?", trxName)
					 .setParameters(UNS_Production_Worker_ID)
					 .firstOnly();
		
		return retPresence;
	}
	
	public void setIsWorkDay(boolean workDay)
	{
		isWorkDay = workDay;
	}
	
	public void setDayType()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(getWorkDate().getTime());
		
		if (null == getWorkDate())
			throw new AdempiereUserError(
					"Can't define day type because work date is null. Please define work Day first");
		
		boolean  IsNonBusinessDay = MNonBusinessDay.isNonBusinessDay(
				getCtx(), getWorkDate(), getAD_Org_ID(), get_TrxName());
		if(IsNonBusinessDay)
			setDayType(DAYTYPE_HariLiburMingguan);
		else if (calendar.get(Calendar.DAY_OF_WEEK) == 1)
			setDayType(DAYTYPE_HariLiburNasional);
		else
			setDayType(DAYTYPE_HariKerjaBiasa);
	}
	
	public void setPresenceStatus()
	{
		if (DAYTYPE_HariLiburMingguan.equals(getDayType()) || DAYTYPE_HariLiburNasional.equals(getDayType()))
			setPresenceStatus(PRESENCESTATUS_Libur);
		else
			setPresenceStatus(PRESENCESTATUS_FullDay);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean sucess)
	{
		if(!updateHeader())
			return false;
		return true;
	}
	
	private boolean updateHeader()
	{
		MUNSHalfPeriodPresence parent = getParent();
		if(PRESENCESTATUS_Izin.equals(getPresenceStatus()))
		{
			MUNSLeavePermissionTrx leavePerm = MUNSLeavePermissionTrx.get(
					getCtx(), getParent().getUNS_Employee_ID(), getWorkDate(), get_TrxName());
			if(null == leavePerm)
				parent.setTotalNonPayableAbsence(parent.getTotalNonPayableAbsence().add(Env.ONE));
			else if(leavePerm.isPayableLeavePermission())
				parent.setTotalPayableAbsence(parent.getTotalPayableAbsence().add(Env.ONE));
		}
		parent.setTotalPresence(getTotalPresence());
		parent.setTotalSK(getTotalSakit());
		parent.setTotalSKK(getTotalSakitKecelakaanKerja());
		parent.setTotalMangkir(new BigDecimal(getTotalMangkir()));	
		parent.setTotalReceivableAmt(getTotalReceivableAmt());
		parent.setTotalOvertime(getTotalOverTime());
		parent.save();
		return true;
	}
	
	public BigDecimal getTotalPresence()
	{
		BigDecimal totalPresence = Env.ZERO;
		for (MUNSWorkerPresence wp : getParent().getLines(true))
		{
			if (PRESENCESTATUS_FullDay.equals(wp.getPresenceStatus())
					|| PRESENCESTATUS_HalfDay.equals(wp.getPresenceStatus())
					|| PRESENCESTATUS_Lembur.equals(getPresenceStatus()))
				totalPresence = totalPresence.add(Env.ONE);
		}
		return totalPresence;
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getTotalSakit()
	{
		BigDecimal totalSakit = Env.ZERO;
		for (MUNSWorkerPresence wp : getParent().getLines(true))
		{
			if (PERMISSIONTYPE_SuratKeteranganIstirahat.equals(wp.getPermissionType())) {
				if (PRESENCESTATUS_HalfDay.equals(wp.getPresenceStatus()))
					totalSakit = totalSakit.add(BigDecimal.valueOf(0.5));
				else
					totalSakit = totalSakit.add(Env.ONE);
			}
		}
		return totalSakit;
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getTotalSakitKecelakaanKerja()
	{
		BigDecimal total = Env.ZERO;
		for (MUNSWorkerPresence wp : getParent().getLines(true))
		{
			if (PERMISSIONTYPE_SuratKeteranganIstirahatKecelakaanKerja.equals(wp.getPermissionType())) { 
				if (PRESENCESTATUS_HalfDay.equals(wp.getPresenceStatus()))
					total = total.add(BigDecimal.valueOf(0.5));
				else
					total = total.add(Env.ONE);
					
			}
		}
		return total;
	}

	public int getTotalMangkir()
	{
		int totalMangkir = 0;
		for (MUNSWorkerPresence wp : getParent().getLines(true))
		{
			if (PRESENCESTATUS_Mangkir.equals(wp.getPresenceStatus()))
				totalMangkir++;
		}
		return totalMangkir;
	}
	
	public BigDecimal getTotalReceivableAmt()
	{
		BigDecimal totalReceivableAmt = BigDecimal.ZERO;
		for (MUNSWorkerPresence wp : getParent().getLines(true))
		{
			totalReceivableAmt = totalReceivableAmt.add(wp.getReceivableAmt());
		}
		return totalReceivableAmt;
	}
	
	public BigDecimal getTotalOverTime()
	{
		double total = 0.0;
		for(MUNSWorkerPresence wp : getParent().getLines(true))
		{
			total += wp.getOvertime().doubleValue();
		}
		return new BigDecimal(total);
	}
	
	public MUNSHalfPeriodPresence getParent()
	{
		if (m_parent == null)
			m_parent = (MUNSHalfPeriodPresence)getUNS_HalfPeriod_Presence();
		
		return m_parent;
	}
	
	public boolean isProductionOnSunday()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(getWorkDate().getTime());
		if (calendar.get(Calendar.DAY_OF_WEEK) == 1)
			return true;
		return false;
	}
	
	public boolean isNonBusinessDay()
	{
		return MNonBusinessDay.isNonBusinessDay(getCtx(), getWorkDate(), getAD_Org_ID(), get_TrxName());
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getWorkDate().getTime());	
		
		boolean isWorker = getParent().isSupervised();
		
		I_UNS_Employee employee = getUNS_HalfPeriod_Presence().getUNS_Employee();
		MUNSHalfPeriodPresence parent = (MUNSHalfPeriodPresence)getUNS_HalfPeriod_Presence();
	
		MUNSProductionPayConfig payConfig = MUNSProductionPayConfig.get(
				getCtx(), getAD_Org_ID(), getWorkDate(), get_TrxName());
		

		if (null == payConfig
			&& getUNS_BongkarMuat_ID() <= 0 && getUNS_LoadingCost_ID() <= 0
			&& getUNS_Production_Worker_ID() <= 0 
			&& getOT_Loading_ID() <= 0 && getOT_ProductionWOrker_ID() <=0
			&& getOT_Unloading_ID() <= 0)
			throw new AdempiereUserError("Cannot find Production Pay Config for : Dept.:" + 
				MOrg.get(getCtx(), getAD_Org_ID()).getName() + " - Employment Status:"
					+ employee.getPayrollTerm());
		
		MUNSLeavePermissionTrx leavePerm = MUNSLeavePermissionTrx.get(
				getCtx(), getParent().getUNS_Employee_ID(), getWorkDate(), get_TrxName());
		
		String prevPresenceStatus = (String)get_ValueOld(COLUMNNAME_PresenceStatus);
		
		if(null != prevPresenceStatus)
		{
			if(prevPresenceStatus.equals(PRESENCESTATUS_Izin))
			{
				if(null != leavePerm)
				{
					if(leavePerm.isPayableLeavePermission())
						parent.setTotalPayableAbsence(parent.getTotalPayableAbsence().subtract(Env.ONE));
					else
						parent.setTotalPayableAbsence(parent.getTotalPayableAbsence().subtract(Env.ONE));
				}
				else
				{
					parent.setTotalNonPayableAbsence(parent.getTotalNonPayableAbsence().subtract(Env.ONE));
				}
			}
		}
		
		if(getPresenceStatus().equals(PRESENCESTATUS_Libur))
			setReceivableAmt(BigDecimal.ZERO);
		else if(getPresenceStatus().equals(PRESENCESTATUS_Mangkir))
			setReceivableAmt(BigDecimal.ZERO);
		else if(getPresenceStatus().equals(PRESENCESTATUS_Izin) && null != leavePerm)
		{
			if(leavePerm.isPayableLeavePermission())
				setReceivableAmt(payConfig.getPayFullDay(isWorker));
			else
				setReceivableAmt(BigDecimal.ZERO);
		}
		else if(getPresenceStatus().equals(PRESENCESTATUS_Izin) && null == leavePerm)
		{
			setReceivableAmt(BigDecimal.ZERO);
		}
		else if (getPermissionType() != null)
		{
			if(getPermissionType().equals(PERMISSIONTYPE_SuratKeteranganIstirahat) && isWorker)
			{
				if(payConfig.isPayableSK() && isWorker)
					setReceivableAmt(payConfig.getPayFullDay(isWorker));
				else
					
					setReceivableAmt(BigDecimal.ZERO);
			}
			else if(getPermissionType().equals(PERMISSIONTYPE_SuratKeteranganIstirahatKecelakaanKerja) && isWorker)
			{
				if(payConfig.isPayableSKK() && isWorker)
					setReceivableAmt(payConfig.getPayFullDay(isWorker));
				else
					setReceivableAmt(BigDecimal.ZERO);
			}
		}
		else if(PRESENCESTATUS_FullDay.equals(getPresenceStatus())
				|| PRESENCESTATUS_HalfDay.equals(getPresenceStatus())
				|| PRESENCESTATUS_Lembur.equals(getPresenceStatus()))
		{
			if (getUNS_BongkarMuat_ID() <= 0 && getUNS_LoadingCost_ID() <= 0
					&& getUNS_Production_Worker_ID() <= 0 
					&& getOT_Loading_ID() <= 0 && getOT_ProductionWOrker_ID() <=0
					&& getOT_Unloading_ID() <= 0)
			{
				double payFullDay = payConfig.getPayFullDay(isWorker).doubleValue();
				double payHalfDay = payConfig.getPayHalfDay(isWorker).doubleValue();
				BigDecimal payFullDayHoliday = payConfig.getPayFullHoliday(isWorker);
				BigDecimal payHalfDayHoliday = payConfig.getPayHalfHoliday(isWorker);
				BigDecimal payNationalHolFull = payConfig.getPayFullNationalHoliday(isWorker);
				BigDecimal payNationalHolHalf = payConfig.getPayHalfNationalHoliday(isWorker);
				
				if (isNonBusinessDay())
				{
					if (getPresenceStatus().equals(PRESENCESTATUS_FullDay))
					{
						setReceivableAmt(payNationalHolFull);
						if(getOvertime().compareTo(BigDecimal.ZERO) > 0)
						{
							BigDecimal amtOverTime = getOvertime()
									.multiply(payConfig.getOverTimeNationalHoliday(isWorker));
							setReceivableAmt(getReceivableAmt().add(amtOverTime));
						}
					}
					else if (getPresenceStatus().equals(PRESENCESTATUS_HalfDay))
					{
						setReceivableAmt(payNationalHolHalf);
					}
				}
				else if(!isWorkDay)
				{
					if (getPresenceStatus().equals(PRESENCESTATUS_FullDay))
					{
						setReceivableAmt(payFullDayHoliday);
						if(getOvertime().compareTo(BigDecimal.ZERO) > 0)
						{
							BigDecimal amtOverTime = getOvertime()
									.multiply(payConfig.getOverTimeHoliday(isWorker));
							setReceivableAmt(getReceivableAmt().add(amtOverTime));
						}
					}
					else if (getPresenceStatus().equals(PRESENCESTATUS_HalfDay))
					{
						setReceivableAmt(payHalfDayHoliday);
					}
				}
				else if (getDayType().equals(DAYTYPE_HariKerjaBiasa))
				{
					if (getPresenceStatus().equals(PRESENCESTATUS_FullDay))
					{
						setReceivableAmt(new BigDecimal(payFullDay));
						BigDecimal amtOverTime = getOvertime()
								.multiply(payConfig.getPayOverTimePerHours(isWorker));
						setReceivableAmt(getReceivableAmt().add(amtOverTime));
					}
					else if (getPresenceStatus().equals(PRESENCESTATUS_HalfDay))
					{
						setReceivableAmt(new BigDecimal(payHalfDay));
					}
				}
			}
		}
		else
			setReceivableAmt(BigDecimal.ZERO);
	
		parent.save();
		return super.beforeSave(newRecord);
	}
	
	/**
	 * 
	 */
	protected boolean afterDelete(boolean success)
	{
		return updateHeader();
	}
	
	public boolean isWorkDay()
	{
		return getDayType().equals(DAYTYPE_HariKerjaBiasa);
	}
}
