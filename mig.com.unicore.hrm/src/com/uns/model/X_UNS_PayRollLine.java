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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_PayRollLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PayRollLine extends PO implements I_UNS_PayRollLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150311L;

    /** Standard Constructor */
    public X_UNS_PayRollLine (Properties ctx, int UNS_PayRollLine_ID, String trxName)
    {
      super (ctx, UNS_PayRollLine_ID, trxName);
      /** if (UNS_PayRollLine_ID == 0)
        {
			setCriteriaType (null);
			setIsGradualCalculation (false);
// N
			setIsSwitchTodaily (false);
// N
			setIsTargetBased (false);
// N
			setName (null);
			setNoWorkDayIncentive (Env.ZERO);
// 0
			setPayOverTime (Env.ZERO);
// @SupervisedPayOTPerHour@
			setPayrollTerm (null);
// 01
			setUNS_Job_Role_ID (0);
			setUNS_PayCriteria_ID (0);
			setUNS_PayRollLine_ID (0);
			setUNS_ProductionPayConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PayRollLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_PayRollLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Accomplishment = ACP */
	public static final String CRITERIATYPE_Accomplishment = "ACP";
	/** Daily = DAY */
	public static final String CRITERIATYPE_Daily = "DAY";
	/** Product = PRD */
	public static final String CRITERIATYPE_Product = "PRD";
	/** Premi = PRM */
	public static final String CRITERIATYPE_Premi = "PRM";
	/** Set Criteria Type.
		@param CriteriaType Criteria Type	  */
	public void setCriteriaType (String CriteriaType)
	{

		set_Value (COLUMNNAME_CriteriaType, CriteriaType);
	}

	/** Get Criteria Type.
		@return Criteria Type	  */
	public String getCriteriaType () 
	{
		return (String)get_Value(COLUMNNAME_CriteriaType);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Contractor Incentive.
		@param InsentifPemborong 
		The incentive for contractor based on worker's production result (for Borongan) or worker's presence (for Non-Borongan)
	  */
	public void setInsentifPemborong (BigDecimal InsentifPemborong)
	{
		set_Value (COLUMNNAME_InsentifPemborong, InsentifPemborong);
	}

	/** Get Contractor Incentive.
		@return The incentive for contractor based on worker's production result (for Borongan) or worker's presence (for Non-Borongan)
	  */
	public BigDecimal getInsentifPemborong () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InsentifPemborong);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Is Gradual Calculation.
		@param IsGradualCalculation 
		Indication to calculate the payment gradually based on the target achivement (step-by-step).
	  */
	public void setIsGradualCalculation (boolean IsGradualCalculation)
	{
		set_Value (COLUMNNAME_IsGradualCalculation, Boolean.valueOf(IsGradualCalculation));
	}

	/** Get Is Gradual Calculation.
		@return Indication to calculate the payment gradually based on the target achivement (step-by-step).
	  */
	public boolean isGradualCalculation () 
	{
		Object oo = get_Value(COLUMNNAME_IsGradualCalculation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Switched To Daily.
		@param IsSwitchTodaily 
		If out of premi constraints, worker's receivable is switched to daily pay
	  */
	public void setIsSwitchTodaily (boolean IsSwitchTodaily)
	{
		set_Value (COLUMNNAME_IsSwitchTodaily, Boolean.valueOf(IsSwitchTodaily));
	}

	/** Get Switched To Daily.
		@return If out of premi constraints, worker's receivable is switched to daily pay
	  */
	public boolean isSwitchTodaily () 
	{
		Object oo = get_Value(COLUMNNAME_IsSwitchTodaily);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Target Based.
		@param IsTargetBased Target Based	  */
	public void setIsTargetBased (boolean IsTargetBased)
	{
		set_Value (COLUMNNAME_IsTargetBased, Boolean.valueOf(IsTargetBased));
	}

	/** Get Target Based.
		@return Target Based	  */
	public boolean isTargetBased () 
	{
		Object oo = get_Value(COLUMNNAME_IsTargetBased);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Total Result Target.
		@param IsTotalResultTarget 
		To indicate that the target defined is based on the product total result of the production.
	  */
	public void setIsTotalResultTarget (boolean IsTotalResultTarget)
	{
		set_Value (COLUMNNAME_IsTotalResultTarget, Boolean.valueOf(IsTotalResultTarget));
	}

	/** Get Is Total Result Target.
		@return To indicate that the target defined is based on the product total result of the production.
	  */
	public boolean isTotalResultTarget () 
	{
		Object oo = get_Value(COLUMNNAME_IsTotalResultTarget);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
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

	/** Set Pay.
		@param Pay Pay	  */
	public void setPay (BigDecimal Pay)
	{
		set_Value (COLUMNNAME_Pay, Pay);
	}

	/** Get Pay.
		@return Pay	  */
	public BigDecimal getPay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Pay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Full Day.
		@param PayFullDay 
		Normal daily work-day payment for full day presence
	  */
	public void setPayFullDay (BigDecimal PayFullDay)
	{
		set_Value (COLUMNNAME_PayFullDay, PayFullDay);
	}

	/** Get Pay Full Day.
		@return Normal daily work-day payment for full day presence
	  */
	public BigDecimal getPayFullDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayFullDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Half Day.
		@param PayHalfDay 
		Normal daily work-day payment for half day presence
	  */
	public void setPayHalfDay (BigDecimal PayHalfDay)
	{
		set_Value (COLUMNNAME_PayHalfDay, PayHalfDay);
	}

	/** Get Pay Half Day.
		@return Normal daily work-day payment for half day presence
	  */
	public BigDecimal getPayHalfDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayHalfDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Holiday Full Day.
		@param PayHolidayFullDay 
		Holiday payment for full day presence
	  */
	public void setPayHolidayFullDay (BigDecimal PayHolidayFullDay)
	{
		set_Value (COLUMNNAME_PayHolidayFullDay, PayHolidayFullDay);
	}

	/** Get Pay Holiday Full Day.
		@return Holiday payment for full day presence
	  */
	public BigDecimal getPayHolidayFullDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayHolidayFullDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Holiday Half Day.
		@param PayHolidayHalfDay 
		Holiday payment for half day presence
	  */
	public void setPayHolidayHalfDay (BigDecimal PayHolidayHalfDay)
	{
		set_Value (COLUMNNAME_PayHolidayHalfDay, PayHolidayHalfDay);
	}

	/** Get Pay Holiday Half Day.
		@return Holiday payment for half day presence
	  */
	public BigDecimal getPayHolidayHalfDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayHolidayHalfDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay  Holiday OT PerHour.
		@param PayHolidayOTPerHour 
		Holiday payment per over time hours presence
	  */
	public void setPayHolidayOTPerHour (BigDecimal PayHolidayOTPerHour)
	{
		set_Value (COLUMNNAME_PayHolidayOTPerHour, PayHolidayOTPerHour);
	}

	/** Get Pay  Holiday OT PerHour.
		@return Holiday payment per over time hours presence
	  */
	public BigDecimal getPayHolidayOTPerHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayHolidayOTPerHour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay N.Holiday Full Day.
		@param PayNHolidayFullDay 
		National holiday payment for full day presence
	  */
	public void setPayNHolidayFullDay (BigDecimal PayNHolidayFullDay)
	{
		set_Value (COLUMNNAME_PayNHolidayFullDay, PayNHolidayFullDay);
	}

	/** Get Pay N.Holiday Full Day.
		@return National holiday payment for full day presence
	  */
	public BigDecimal getPayNHolidayFullDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayNHolidayFullDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay N.Holiday Half Day.
		@param PayNHolidayHalfDay 
		National Holiday payment for half day presence
	  */
	public void setPayNHolidayHalfDay (BigDecimal PayNHolidayHalfDay)
	{
		set_Value (COLUMNNAME_PayNHolidayHalfDay, PayNHolidayHalfDay);
	}

	/** Get Pay N.Holiday Half Day.
		@return National Holiday payment for half day presence
	  */
	public BigDecimal getPayNHolidayHalfDay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayNHolidayHalfDay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay  N.Holiday OT PerHour.
		@param PayNHolidayOTPerHour 
		National payment per overtime hours presence
	  */
	public void setPayNHolidayOTPerHour (BigDecimal PayNHolidayOTPerHour)
	{
		set_Value (COLUMNNAME_PayNHolidayOTPerHour, PayNHolidayOTPerHour);
	}

	/** Get Pay  N.Holiday OT PerHour.
		@return National payment per overtime hours presence
	  */
	public BigDecimal getPayNHolidayOTPerHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayNHolidayOTPerHour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay National Holiday.
		@param PayNationalHoliday 
		Payment amount on national holiday work-result
	  */
	public void setPayNationalHoliday (BigDecimal PayNationalHoliday)
	{
		set_Value (COLUMNNAME_PayNationalHoliday, PayNationalHoliday);
	}

	/** Get Pay National Holiday.
		@return Payment amount on national holiday work-result
	  */
	public BigDecimal getPayNationalHoliday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayNationalHoliday);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Over Time.
		@param PayOverTime 
		Overtime Payment amount on normal day
	  */
	public void setPayOverTime (BigDecimal PayOverTime)
	{
		set_Value (COLUMNNAME_PayOverTime, PayOverTime);
	}

	/** Get Pay Over Time.
		@return Overtime Payment amount on normal day
	  */
	public BigDecimal getPayOverTime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayOverTime);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Pay Holiday.
		@param PaySunday 
		Payment amount on holiday work-result
	  */
	public void setPaySunday (BigDecimal PaySunday)
	{
		set_Value (COLUMNNAME_PaySunday, PaySunday);
	}

	/** Get Pay Holiday.
		@return Payment amount on holiday work-result
	  */
	public BigDecimal getPaySunday () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PaySunday);
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

	/** Set Premi Achivement Recognized.
		@param PremiAchivementRecognized Premi Achivement Recognized	  */
	public void setPremiAchivementRecognized (BigDecimal PremiAchivementRecognized)
	{
		set_Value (COLUMNNAME_PremiAchivementRecognized, PremiAchivementRecognized);
	}

	/** Get Premi Achivement Recognized.
		@return Premi Achivement Recognized	  */
	public BigDecimal getPremiAchivementRecognized () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PremiAchivementRecognized);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Absence Based = ABB */
	public static final String PREMICALCULATEBASED_AbsenceBased = "ABB";
	/** Product Based = PRB */
	public static final String PREMICALCULATEBASED_ProductBased = "PRB";
	/** Value Based = VLB */
	public static final String PREMICALCULATEBASED_ValueBased = "VLB";
	/** Set Premi Calculate Based.
		@param PremiCalculateBased Premi Calculate Based	  */
	public void setPremiCalculateBased (String PremiCalculateBased)
	{

		set_Value (COLUMNNAME_PremiCalculateBased, PremiCalculateBased);
	}

	/** Get Premi Calculate Based.
		@return Premi Calculate Based	  */
	public String getPremiCalculateBased () 
	{
		return (String)get_Value(COLUMNNAME_PremiCalculateBased);
	}

	/** Set Std. Number of Labor.
		@param StdLaborNumber 
		The standard expected number of labor involved during production.
	  */
	public void setStdLaborNumber (int StdLaborNumber)
	{
		set_Value (COLUMNNAME_StdLaborNumber, Integer.valueOf(StdLaborNumber));
	}

	/** Get Std. Number of Labor.
		@return The standard expected number of labor involved during production.
	  */
	public int getStdLaborNumber () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_StdLaborNumber);
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

	public com.uns.model.I_UNS_Job_Role getUNS_Job_Role() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Job_Role)MTable.get(getCtx(), com.uns.model.I_UNS_Job_Role.Table_Name)
			.getPO(getUNS_Job_Role_ID(), get_TrxName());	}
	
	public com.uns.model.I_UNS_PayCriteria getUNS_PayCriteria() throws RuntimeException
    {
		return (com.uns.model.I_UNS_PayCriteria)MTable.get(getCtx(), com.uns.model.I_UNS_PayCriteria.Table_Name)
			.getPO(getUNS_PayCriteria_ID(), get_TrxName());	}

	/** Set Pay Criteria.
		@param UNS_PayCriteria_ID Pay Criteria	  */
	public void setUNS_PayCriteria_ID (int UNS_PayCriteria_ID)
	{
		if (UNS_PayCriteria_ID < 1) 
			set_Value (COLUMNNAME_UNS_PayCriteria_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_PayCriteria_ID, Integer.valueOf(UNS_PayCriteria_ID));
	}

	/** Get Pay Criteria.
		@return Pay Criteria	  */
	public int getUNS_PayCriteria_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PayCriteria_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pay Roll Line.
		@param UNS_PayRollLine_ID Pay Roll Line	  */
	public void setUNS_PayRollLine_ID (int UNS_PayRollLine_ID)
	{
		if (UNS_PayRollLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PayRollLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PayRollLine_ID, Integer.valueOf(UNS_PayRollLine_ID));
	}

	/** Get Pay Roll Line.
		@return Pay Roll Line	  */
	public int getUNS_PayRollLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PayRollLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PayRollLine_UU.
		@param UNS_PayRollLine_UU UNS_PayRollLine_UU	  */
	public void setUNS_PayRollLine_UU (String UNS_PayRollLine_UU)
	{
		set_Value (COLUMNNAME_UNS_PayRollLine_UU, UNS_PayRollLine_UU);
	}

	/** Get UNS_PayRollLine_UU.
		@return UNS_PayRollLine_UU	  */
	public String getUNS_PayRollLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PayRollLine_UU);
	}

	public com.uns.model.I_UNS_ProductionPayConfig getUNS_ProductionPayConfig() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ProductionPayConfig)MTable.get(getCtx(), com.uns.model.I_UNS_ProductionPayConfig.Table_Name)
			.getPO(getUNS_ProductionPayConfig_ID(), get_TrxName());	}

	/** Set Production Pay Config.
		@param UNS_ProductionPayConfig_ID Production Pay Config	  */
	public void setUNS_ProductionPayConfig_ID (int UNS_ProductionPayConfig_ID)
	{
		if (UNS_ProductionPayConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductionPayConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductionPayConfig_ID, Integer.valueOf(UNS_ProductionPayConfig_ID));
	}

	/** Get Production Pay Config.
		@return Production Pay Config	  */
	public int getUNS_ProductionPayConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ProductionPayConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}