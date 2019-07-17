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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_SalesTargetLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_SalesTargetLine extends PO implements I_UNS_SalesTargetLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141212L;

    /** Standard Constructor */
    public X_UNS_SalesTargetLine (Properties ctx, int UNS_SalesTargetLine_ID, String trxName)
    {
      super (ctx, UNS_SalesTargetLine_ID, trxName);
      /** if (UNS_SalesTargetLine_ID == 0)
        {
			setIsUseSameSchemaForEveryPeriod (true);
// Y
			setTargetPeriodic (null);
			setUNS_SalesTarget_ID (0);
			setUNS_SalesTargetLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_SalesTargetLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_SalesTargetLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner.
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Deduction.
		@param Deduction Deduction	  */
	public void setDeduction (BigDecimal Deduction)
	{
		set_Value (COLUMNNAME_Deduction, Deduction);
	}

	/** Get Deduction.
		@return Deduction	  */
	public BigDecimal getDeduction () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Deduction);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Generate List.
		@param GenerateList 
		Generate List
	  */
	public void setGenerateList (String GenerateList)
	{
		set_Value (COLUMNNAME_GenerateList, GenerateList);
	}

	/** Get Generate List.
		@return Generate List
	  */
	public String getGenerateList () 
	{
		return (String)get_Value(COLUMNNAME_GenerateList);
	}

	/** Set Incentive.
		@param Incentive Incentive	  */
	public void setIncentive (BigDecimal Incentive)
	{
		set_Value (COLUMNNAME_Incentive, Incentive);
	}

	/** Get Incentive.
		@return Incentive	  */
	public BigDecimal getIncentive () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Incentive);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Use Same Schema For Every Period.
		@param IsUseSameSchemaForEveryPeriod Use Same Schema For Every Period	  */
	public void setIsUseSameSchemaForEveryPeriod (boolean IsUseSameSchemaForEveryPeriod)
	{
		set_Value (COLUMNNAME_IsUseSameSchemaForEveryPeriod, Boolean.valueOf(IsUseSameSchemaForEveryPeriod));
	}

	/** Get Use Same Schema For Every Period.
		@return Use Same Schema For Every Period	  */
	public boolean isUseSameSchemaForEveryPeriod () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseSameSchemaForEveryPeriod);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Level Test1 = L1 */
	public static final String SALESLEVEL_LevelTest1 = "L1";
	/** Set Sales Level.
		@param SalesLevel Sales Level	  */
	public void setSalesLevel (String SalesLevel)
	{

		set_Value (COLUMNNAME_SalesLevel, SalesLevel);
	}

	/** Get Sales Level.
		@return Sales Level	  */
	public String getSalesLevel () 
	{
		return (String)get_Value(COLUMNNAME_SalesLevel);
	}

	/** Sales Executive = SEX */
	public static final String SALESTYPE_SalesExecutive = "SEX";
	/** Set Sales Type.
		@param SalesType 
		Not Defined
	  */
	public void setSalesType (String SalesType)
	{

		set_Value (COLUMNNAME_SalesType, SalesType);
	}

	/** Get Sales Type.
		@return Not Defined
	  */
	public String getSalesType () 
	{
		return (String)get_Value(COLUMNNAME_SalesType);
	}

	/** Daily = 01 */
	public static final String TARGETPERIODIC_Daily = "01";
	/** Monthly = 02 */
	public static final String TARGETPERIODIC_Monthly = "02";
	/** Quarterly = 03 */
	public static final String TARGETPERIODIC_Quarterly = "03";
	/** Half Year = 04 */
	public static final String TARGETPERIODIC_HalfYear = "04";
	/** Yearly = 05 */
	public static final String TARGETPERIODIC_Yearly = "05";
	/** Set Target Periodic.
		@param TargetPeriodic 
		Discont target periodic for target break type periodic
	  */
	public void setTargetPeriodic (String TargetPeriodic)
	{

		set_Value (COLUMNNAME_TargetPeriodic, TargetPeriodic);
	}

	/** Get Target Periodic.
		@return Discont target periodic for target break type periodic
	  */
	public String getTargetPeriodic () 
	{
		return (String)get_Value(COLUMNNAME_TargetPeriodic);
	}

	/** Set Top Target.
		@param TOPTarget 
		Term of Payment target.
	  */
	public void setTOPTarget (BigDecimal TOPTarget)
	{
		set_Value (COLUMNNAME_TOPTarget, TOPTarget);
	}

	/** Get Top Target.
		@return Term of Payment target.
	  */
	public BigDecimal getTOPTarget () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TOPTarget);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sales Target.
		@param UNS_SalesTarget_ID Sales Target	  */
	public void setUNS_SalesTarget_ID (int UNS_SalesTarget_ID)
	{
		if (UNS_SalesTarget_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesTarget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesTarget_ID, Integer.valueOf(UNS_SalesTarget_ID));
	}

	/** Get Sales Target.
		@return Sales Target	  */
	public int getUNS_SalesTarget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SalesTarget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sales Target Line.
		@param UNS_SalesTargetLine_ID Sales Target Line	  */
	public void setUNS_SalesTargetLine_ID (int UNS_SalesTargetLine_ID)
	{
		if (UNS_SalesTargetLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesTargetLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_SalesTargetLine_ID, Integer.valueOf(UNS_SalesTargetLine_ID));
	}

	/** Get Sales Target Line.
		@return Sales Target Line	  */
	public int getUNS_SalesTargetLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SalesTargetLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_SalesTargetLine_UU.
		@param UNS_SalesTargetLine_UU UNS_SalesTargetLine_UU	  */
	public void setUNS_SalesTargetLine_UU (String UNS_SalesTargetLine_UU)
	{
		set_Value (COLUMNNAME_UNS_SalesTargetLine_UU, UNS_SalesTargetLine_UU);
	}

	/** Get UNS_SalesTargetLine_UU.
		@return UNS_SalesTargetLine_UU	  */
	public String getUNS_SalesTargetLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_SalesTargetLine_UU);
	}

	/** Set Value Target.
		@param ValueTarget Value Target	  */
	public void setValueTarget (BigDecimal ValueTarget)
	{
		set_Value (COLUMNNAME_ValueTarget, ValueTarget);
	}

	/** Get Value Target.
		@return Value Target	  */
	public BigDecimal getValueTarget () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueTarget);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}