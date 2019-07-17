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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_EmployeeAllowanceRecord
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_EmployeeAllowanceRecord extends PO implements I_UNS_EmployeeAllowanceRecord, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140126L;

    /** Standard Constructor */
    public X_UNS_EmployeeAllowanceRecord (Properties ctx, int UNS_EmployeeAllowanceRecord_ID, String trxName)
    {
      super (ctx, UNS_EmployeeAllowanceRecord_ID, trxName);
      /** if (UNS_EmployeeAllowanceRecord_ID == 0)
        {
			setC_Year_ID (0);
			setLeaveClaimReserved (Env.ZERO);
			setLeaveReservedUsed (Env.ZERO);
			setMedicalAllowance (Env.ZERO);
			setMedicalAllowanceUsed (Env.ZERO);
			setPeriodDateEnd (new Timestamp( System.currentTimeMillis() ));
			setPeriodDateStart (new Timestamp( System.currentTimeMillis() ));
			setRemainingAmt (Env.ZERO);
			setUNS_Contract_Recommendation_ID (0);
			setUNS_EmployeeAllowanceRecord_ID (0);
			setUNS_Employee_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_EmployeeAllowanceRecord (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_EmployeeAllowanceRecord[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException
    {
		return (org.compiere.model.I_C_Year)MTable.get(getCtx(), org.compiere.model.I_C_Year.Table_Name)
			.getPO(getC_Year_ID(), get_TrxName());	}

	/** Set Year.
		@param C_Year_ID 
		Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID)
	{
		if (C_Year_ID < 1) 
			set_Value (COLUMNNAME_C_Year_ID, null);
		else 
			set_Value (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
	}

	/** Get Year.
		@return Calendar Year
	  */
	public int getC_Year_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Year_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Leave Claim Reserved.
		@param LeaveClaimReserved Leave Claim Reserved	  */
	public void setLeaveClaimReserved (BigDecimal LeaveClaimReserved)
	{
		set_Value (COLUMNNAME_LeaveClaimReserved, LeaveClaimReserved);
	}

	/** Get Leave Claim Reserved.
		@return Leave Claim Reserved	  */
	public BigDecimal getLeaveClaimReserved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LeaveClaimReserved);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Leave Reserved Used.
		@param LeaveReservedUsed 
		The amount of Leave claim reserved used by employee
	  */
	public void setLeaveReservedUsed (BigDecimal LeaveReservedUsed)
	{
		set_Value (COLUMNNAME_LeaveReservedUsed, LeaveReservedUsed);
	}

	/** Get Leave Reserved Used.
		@return The amount of Leave claim reserved used by employee
	  */
	public BigDecimal getLeaveReservedUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LeaveReservedUsed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Medical Allowance.
		@param MedicalAllowance 
		The yearly employee's medical allowance amount
	  */
	public void setMedicalAllowance (BigDecimal MedicalAllowance)
	{
		set_Value (COLUMNNAME_MedicalAllowance, MedicalAllowance);
	}

	/** Get Medical Allowance.
		@return The yearly employee's medical allowance amount
	  */
	public BigDecimal getMedicalAllowance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MedicalAllowance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Medical Allowance Used.
		@param MedicalAllowanceUsed Medical Allowance Used	  */
	public void setMedicalAllowanceUsed (BigDecimal MedicalAllowanceUsed)
	{
		set_Value (COLUMNNAME_MedicalAllowanceUsed, MedicalAllowanceUsed);
	}

	/** Get Medical Allowance Used.
		@return Medical Allowance Used	  */
	public BigDecimal getMedicalAllowanceUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MedicalAllowanceUsed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Period Date End.
		@param PeriodDateEnd Period Date End	  */
	public void setPeriodDateEnd (Timestamp PeriodDateEnd)
	{
		set_Value (COLUMNNAME_PeriodDateEnd, PeriodDateEnd);
	}

	/** Get Period Date End.
		@return Period Date End	  */
	public Timestamp getPeriodDateEnd () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PeriodDateEnd);
	}

	/** Set Period Date Start.
		@param PeriodDateStart Period Date Start	  */
	public void setPeriodDateStart (Timestamp PeriodDateStart)
	{
		set_Value (COLUMNNAME_PeriodDateStart, PeriodDateStart);
	}

	/** Get Period Date Start.
		@return Period Date Start	  */
	public Timestamp getPeriodDateStart () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PeriodDateStart);
	}

	/** Set Remaining Amt.
		@param RemainingAmt 
		Remaining Amount
	  */
	public void setRemainingAmt (BigDecimal RemainingAmt)
	{
		set_Value (COLUMNNAME_RemainingAmt, RemainingAmt);
	}

	/** Get Remaining Amt.
		@return Remaining Amount
	  */
	public BigDecimal getRemainingAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RemainingAmt);
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

	public com.uns.model.I_UNS_Contract_Recommendation getUNS_Contract_Recommendation() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Contract_Recommendation)MTable.get(getCtx(), com.uns.model.I_UNS_Contract_Recommendation.Table_Name)
			.getPO(getUNS_Contract_Recommendation_ID(), get_TrxName());	}

	/** Set Contract.
		@param UNS_Contract_Recommendation_ID Contract	  */
	public void setUNS_Contract_Recommendation_ID (int UNS_Contract_Recommendation_ID)
	{
		if (UNS_Contract_Recommendation_ID < 1) 
			set_Value (COLUMNNAME_UNS_Contract_Recommendation_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Contract_Recommendation_ID, Integer.valueOf(UNS_Contract_Recommendation_ID));
	}

	/** Get Contract.
		@return Contract	  */
	public int getUNS_Contract_Recommendation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Contract_Recommendation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Employee Allowance Record.
		@param UNS_EmployeeAllowanceRecord_ID Employee Allowance Record	  */
	public void setUNS_EmployeeAllowanceRecord_ID (int UNS_EmployeeAllowanceRecord_ID)
	{
		if (UNS_EmployeeAllowanceRecord_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_EmployeeAllowanceRecord_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_EmployeeAllowanceRecord_ID, Integer.valueOf(UNS_EmployeeAllowanceRecord_ID));
	}

	/** Get Employee Allowance Record.
		@return Employee Allowance Record	  */
	public int getUNS_EmployeeAllowanceRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_EmployeeAllowanceRecord_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getUNS_Employee_ID(), get_TrxName());	}

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_Value (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getUNS_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uns_employeeallowancerecord_uu.
		@param uns_employeeallowancerecord_uu uns_employeeallowancerecord_uu	  */
	public void setuns_employeeallowancerecord_uu (String uns_employeeallowancerecord_uu)
	{
		set_Value (COLUMNNAME_uns_employeeallowancerecord_uu, uns_employeeallowancerecord_uu);
	}

	/** Get uns_employeeallowancerecord_uu.
		@return uns_employeeallowancerecord_uu	  */
	public String getuns_employeeallowancerecord_uu () 
	{
		return (String)get_Value(COLUMNNAME_uns_employeeallowancerecord_uu);
	}
}