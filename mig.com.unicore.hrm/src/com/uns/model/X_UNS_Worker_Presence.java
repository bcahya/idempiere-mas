/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_Worker_Presence
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Worker_Presence extends PO implements I_UNS_Worker_Presence, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150312L;

    /** Standard Constructor */
    public X_UNS_Worker_Presence (Properties ctx, int UNS_Worker_Presence_ID, String trxName)
    {
      super (ctx, UNS_Worker_Presence_ID, trxName);
      /** if (UNS_Worker_Presence_ID == 0)
        {
			setDayType (null);
			setNoWorkDayIncentive (Env.ZERO);
// 0
			setOvertime (Env.ZERO);
// 0
			setOvertimeReceivable (Env.ZERO);
// 0
			setPayrollTerm (null);
			setPresenceStatus (null);
			setReceivableAmt (Env.ZERO);
			setSubsidyReserved (false);
// N
			setTotalReceivableAmt (Env.ZERO);
// 0
			setUNS_HalfPeriod_Presence_ID (0);
			setUNS_Worker_Presence_ID (0);
			setWorkDate (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_UNS_Worker_Presence (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UNS_Worker_Presence[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Hari Kerja Biasa = HB */
	public static final String DAYTYPE_HariKerjaBiasa = "HB";
	/** Hari Libur Mingguan = HL */
	public static final String DAYTYPE_HariLiburMingguan = "HL";
	/** Hari Libur Nasional = HN */
	public static final String DAYTYPE_HariLiburNasional = "HN";
	/** Set Day Type.
		@param DayType Day Type	  */
	public void setDayType (String DayType)
	{

		set_Value (COLUMNNAME_DayType, DayType);
	}

	/** Get Day Type.
		@return Day Type	  */
	public String getDayType () 
	{
		return (String)get_Value(COLUMNNAME_DayType);
	}

	/** Set No-Work Day Incentive.
		@param NoWorkDayIncentive 
		The incentive amount if working on no-work days (weekly days-off or national holiday)
	  */
	public void setNoWorkDayIncentive (BigDecimal NoWorkDayIncentive)
	{
		set_Value (COLUMNNAME_NoWorkDayIncentive, NoWorkDayIncentive);
	}

	/** Get No-Work Day Incentive.
		@return The incentive amount if working on no-work days (weekly days-off or national holiday)
	  */
	public BigDecimal getNoWorkDayIncentive () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NoWorkDayIncentive);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set OT Loading.
		@param OT_Loading_ID 
		Over Time Loading
	  */
	public void setOT_Loading_ID (int OT_Loading_ID)
	{
		if (OT_Loading_ID < 1) 
			set_Value (COLUMNNAME_OT_Loading_ID, null);
		else 
			set_Value (COLUMNNAME_OT_Loading_ID, Integer.valueOf(OT_Loading_ID));
	}

	/** Get OT Loading.
		@return Over Time Loading
	  */
	public int getOT_Loading_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OT_Loading_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set OT Production Worker.
		@param OT_ProductionWOrker_ID 
		Over Time Production Worker
	  */
	public void setOT_ProductionWOrker_ID (int OT_ProductionWOrker_ID)
	{
		if (OT_ProductionWOrker_ID < 1) 
			set_Value (COLUMNNAME_OT_ProductionWOrker_ID, null);
		else 
			set_Value (COLUMNNAME_OT_ProductionWOrker_ID, Integer.valueOf(OT_ProductionWOrker_ID));
	}

	/** Get OT Production Worker.
		@return Over Time Production Worker
	  */
	public int getOT_ProductionWOrker_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OT_ProductionWOrker_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set OT Unloading.
		@param OT_Unloading_ID 
		Over Time Unloading
	  */
	public void setOT_Unloading_ID (int OT_Unloading_ID)
	{
		if (OT_Unloading_ID < 1) 
			set_Value (COLUMNNAME_OT_Unloading_ID, null);
		else 
			set_Value (COLUMNNAME_OT_Unloading_ID, Integer.valueOf(OT_Unloading_ID));
	}

	/** Get OT Unloading.
		@return Over Time Unloading
	  */
	public int getOT_Unloading_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OT_Unloading_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Overtime.
		@param Overtime Overtime	  */
	public void setOvertime (BigDecimal Overtime)
	{
		set_Value (COLUMNNAME_Overtime, Overtime);
	}

	/** Get Overtime.
		@return Overtime	  */
	public BigDecimal getOvertime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Overtime);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Overtime Receivable.
		@param OvertimeReceivable 
		Worker's receivable based on overtime hours multiplied by value defined on Production Payroll Configuration
	  */
	public void setOvertimeReceivable (BigDecimal OvertimeReceivable)
	{
		set_Value (COLUMNNAME_OvertimeReceivable, OvertimeReceivable);
	}

	/** Get Overtime Receivable.
		@return Worker's receivable based on overtime hours multiplied by value defined on Production Payroll Configuration
	  */
	public BigDecimal getOvertimeReceivable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OvertimeReceivable);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Monthly = 01 */
	public static final String PAYROLLTERM_Monthly = "01";
	/** Weekly = 02 */
	public static final String PAYROLLTERM_Weekly = "02";
	/** 2 Weekly = 03 */
	public static final String PAYROLLTERM_2Weekly = "03";
	/** Harian Bulanan = 04 */
	public static final String PAYROLLTERM_HarianBulanan = "04";
	/** Set Payroll Term.
		@param PayrollTerm Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm)
	{

		set_Value (COLUMNNAME_PayrollTerm, PayrollTerm);
	}

	/** Get Payroll Term.
		@return Payroll Term	  */
	public String getPayrollTerm () 
	{
		return (String)get_Value(COLUMNNAME_PayrollTerm);
	}

	/** Leave / Cuti = LCTI */
	public static final String PERMISSIONTYPE_LeaveCuti = "LCTI";
	/** Permission (Dispensation) / Izin Dibayar = PMDB */
	public static final String PERMISSIONTYPE_PermissionDispensationIzinDibayar = "PMDB";
	/** Permission (Dinas) = PMDN */
	public static final String PERMISSIONTYPE_PermissionDinas = "PMDN";
	/** Pay Permission / Izin Potong Gaji = PPAY */
	public static final String PERMISSIONTYPE_PayPermissionIzinPotongGaji = "PPAY";
	/** Maternity / Hamil+Melahirkan = MLHR */
	public static final String PERMISSIONTYPE_MaternityHamilPlusMelahirkan = "MLHR";
	/** Surat Keterangan Istirahat = SKI */
	public static final String PERMISSIONTYPE_SuratKeteranganIstirahat = "SKI";
	/** Surat Keterangan Istirahat Kecelakaan Kerja = SKIKK */
	public static final String PERMISSIONTYPE_SuratKeteranganIstirahatKecelakaanKerja = "SKIKK";
	/** Other = OTHR */
	public static final String PERMISSIONTYPE_Other = "OTHR";
	/** Set PermissionType.
		@param PermissionType PermissionType	  */
	public void setPermissionType (String PermissionType)
	{

		set_ValueNoCheck (COLUMNNAME_PermissionType, PermissionType);
	}

	/** Get PermissionType.
		@return PermissionType	  */
	public String getPermissionType () 
	{
		return (String)get_Value(COLUMNNAME_PermissionType);
	}

	/** Full Day = FLD */
	public static final String PRESENCESTATUS_FullDay = "FLD";
	/** Half Day = HLD */
	public static final String PRESENCESTATUS_HalfDay = "HLD";
	/** Izin = IZN */
	public static final String PRESENCESTATUS_Izin = "IZN";
	/** Libur = LBR */
	public static final String PRESENCESTATUS_Libur = "LBR";
	/** Lembur = LMR */
	public static final String PRESENCESTATUS_Lembur = "LMR";
	/** Mangkir = MKR */
	public static final String PRESENCESTATUS_Mangkir = "MKR";
	/** Reversed = RVD */
	public static final String PRESENCESTATUS_Reversed = "RVD";
	/** Reversal = RVL */
	public static final String PRESENCESTATUS_Reversal = "RVL";
	/** Set Presence Status.
		@param PresenceStatus Presence Status	  */
	public void setPresenceStatus (String PresenceStatus)
	{

		set_Value (COLUMNNAME_PresenceStatus, PresenceStatus);
	}

	/** Get Presence Status.
		@return Presence Status	  */
	public String getPresenceStatus () 
	{
		return (String)get_Value(COLUMNNAME_PresenceStatus);
	}

	/** Set Production Quantity.
		@param ProductionQty 
		Quantity of products to produce
	  */
	public void setProductionQty (BigDecimal ProductionQty)
	{
		set_Value (COLUMNNAME_ProductionQty, ProductionQty);
	}

	/** Get Production Quantity.
		@return Quantity of products to produce
	  */
	public BigDecimal getProductionQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProductionQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Basic Receivable.
		@param ReceivableAmt Basic Receivable	  */
	public void setReceivableAmt (BigDecimal ReceivableAmt)
	{
		set_Value (COLUMNNAME_ReceivableAmt, ReceivableAmt);
	}

	/** Get Basic Receivable.
		@return Basic Receivable	  */
	public BigDecimal getReceivableAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ReceivableAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Remarks.
		@param Remarks Remarks	  */
	public void setRemarks (String Remarks)
	{
		set_Value (COLUMNNAME_Remarks, Remarks);
	}

	/** Get Remarks.
		@return Remarks	  */
	public String getRemarks () 
	{
		return (String)get_Value(COLUMNNAME_Remarks);
	}

	/** Set Subsidy Amt.
		@param SubsidyAmt 
		The amount of subsidy to be reserved for this worker
	  */
	public void setSubsidyAmt (BigDecimal SubsidyAmt)
	{
		set_Value (COLUMNNAME_SubsidyAmt, SubsidyAmt);
	}

	/** Get Subsidy Amt.
		@return The amount of subsidy to be reserved for this worker
	  */
	public BigDecimal getSubsidyAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SubsidyAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reserved a Subsidy.
		@param SubsidyReserved 
		Worker is reserved for a subsidy based on company's rules
	  */
	public void setSubsidyReserved (boolean SubsidyReserved)
	{
		set_Value (COLUMNNAME_SubsidyReserved, Boolean.valueOf(SubsidyReserved));
	}

	/** Get Reserved a Subsidy.
		@return Worker is reserved for a subsidy based on company's rules
	  */
	public boolean isSubsidyReserved () 
	{
		Object oo = get_Value(COLUMNNAME_SubsidyReserved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Total Receivable Amt.
		@param TotalReceivableAmt Total Receivable Amt	  */
	public void setTotalReceivableAmt (BigDecimal TotalReceivableAmt)
	{
		set_Value (COLUMNNAME_TotalReceivableAmt, TotalReceivableAmt);
	}

	/** Get Total Receivable Amt.
		@return Total Receivable Amt	  */
	public BigDecimal getTotalReceivableAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalReceivableAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bongkar Muat.
		@param UNS_BongkarMuat_ID Bongkar Muat	  */
	public void setUNS_BongkarMuat_ID (int UNS_BongkarMuat_ID)
	{
		if (UNS_BongkarMuat_ID < 1) 
			set_Value (COLUMNNAME_UNS_BongkarMuat_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_BongkarMuat_ID, Integer.valueOf(UNS_BongkarMuat_ID));
	}

	/** Get Bongkar Muat.
		@return Bongkar Muat	  */
	public int getUNS_BongkarMuat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BongkarMuat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_HalfPeriod_Presence getUNS_HalfPeriod_Presence() throws RuntimeException
    {
		return (com.uns.model.I_UNS_HalfPeriod_Presence)MTable.get(getCtx(), com.uns.model.I_UNS_HalfPeriod_Presence.Table_Name)
			.getPO(getUNS_HalfPeriod_Presence_ID(), get_TrxName());	}

	/** Set Half Period Presence.
		@param UNS_HalfPeriod_Presence_ID Half Period Presence	  */
	public void setUNS_HalfPeriod_Presence_ID (int UNS_HalfPeriod_Presence_ID)
	{
		if (UNS_HalfPeriod_Presence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_HalfPeriod_Presence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_HalfPeriod_Presence_ID, Integer.valueOf(UNS_HalfPeriod_Presence_ID));
	}

	/** Get Half Period Presence.
		@return Half Period Presence	  */
	public int getUNS_HalfPeriod_Presence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_HalfPeriod_Presence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Job Role.
		@param UNS_Job_Role_ID Job Role	  */
	public void setUNS_Job_Role_ID (int UNS_Job_Role_ID)
	{
		if (UNS_Job_Role_ID < 1) 
			set_Value (COLUMNNAME_UNS_Job_Role_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Job_Role_ID, Integer.valueOf(UNS_Job_Role_ID));
	}

	/** Get Job Role.
		@return Job Role	  */
	public int getUNS_Job_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Job_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Loading Cost.
		@param UNS_LoadingCost_ID Loading Cost	  */
	public void setUNS_LoadingCost_ID (int UNS_LoadingCost_ID)
	{
		if (UNS_LoadingCost_ID < 1) 
			set_Value (COLUMNNAME_UNS_LoadingCost_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_LoadingCost_ID, Integer.valueOf(UNS_LoadingCost_ID));
	}

	/** Get Loading Cost.
		@return Loading Cost	  */
	public int getUNS_LoadingCost_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LoadingCost_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production Worker.
		@param UNS_Production_Worker_ID Production Worker	  */
	public void setUNS_Production_Worker_ID (int UNS_Production_Worker_ID)
	{
		if (UNS_Production_Worker_ID < 1) 
			set_Value (COLUMNNAME_UNS_Production_Worker_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Production_Worker_ID, Integer.valueOf(UNS_Production_Worker_ID));
	}

	/** Get Production Worker.
		@return Production Worker	  */
	public int getUNS_Production_Worker_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_Worker_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manufacture Resource.
		@param UNS_Resource_ID Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID)
	{
		if (UNS_Resource_ID < 1) 
			set_Value (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
	}

	/** Get Manufacture Resource.
		@return Manufacture Resource	  */
	public int getUNS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Worker Presence.
		@param UNS_Worker_Presence_ID Worker Presence	  */
	public void setUNS_Worker_Presence_ID (int UNS_Worker_Presence_ID)
	{
		if (UNS_Worker_Presence_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Worker_Presence_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Worker_Presence_ID, Integer.valueOf(UNS_Worker_Presence_ID));
	}

	/** Get Worker Presence.
		@return Worker Presence	  */
	public int getUNS_Worker_Presence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Worker_Presence_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Worker_Presence_UU.
		@param UNS_Worker_Presence_UU UNS_Worker_Presence_UU	  */
	public void setUNS_Worker_Presence_UU (String UNS_Worker_Presence_UU)
	{
		set_Value (COLUMNNAME_UNS_Worker_Presence_UU, UNS_Worker_Presence_UU);
	}

	/** Get UNS_Worker_Presence_UU.
		@return UNS_Worker_Presence_UU	  */
	public String getUNS_Worker_Presence_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Worker_Presence_UU);
	}

	/** Set Work Date.
		@param WorkDate Work Date	  */
	public void setWorkDate (Timestamp WorkDate)
	{
		set_Value (COLUMNNAME_WorkDate, WorkDate);
	}

	/** Get Work Date.
		@return Work Date	  */
	public Timestamp getWorkDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_WorkDate);
	}
}