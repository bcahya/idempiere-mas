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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Resource_InOut
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Resource_InOut extends PO implements I_UNS_Resource_InOut, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150315L;

    /** Standard Constructor */
    public X_UNS_Resource_InOut (Properties ctx, int UNS_Resource_InOut_ID, String trxName)
    {
      super (ctx, UNS_Resource_InOut_ID, trxName);
      /** if (UNS_Resource_InOut_ID == 0)
        {
			setInOutType (null);
			setIsPrimary (false);
// N
			setIsSingleAttribute (false);
// N
			setLine (0);
// @SQL=SELECT (COUNT(Line)*10)+10 AS DefaultValue FROM UNS_Resource_InOut WHERE UNS_Resource_ID=@UNS_Resource_ID@ AND UNS_Resource_transition_ID=0
			setM_Product_ID (0);
			setMaxCaps (Env.ZERO);
			setMaxCapsMT (Env.ZERO);
// 0
			setOptimumCaps (Env.ZERO);
			setOptimumCapsMT (Env.ZERO);
// 0
			setOutputType (null);
// SGL
			setQAMonitoring (true);
// Y
			setToBeForecast (false);
// N
			setUNS_Resource_InOut_ID (0);
			setUOMCaps_ID (0);
// @SQL=SELECT C_UOM_ID AS DefaultValue FROM M_Product WHERE M_Product_ID=@M_Product_ID@
			setUOMTime_ID (0);
// 101
        } */
    }

    /** Load Constructor */
    public X_UNS_Resource_InOut (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Resource_InOut[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Actual Max Caps.
		@param ActualMaxCaps Actual Max Caps	  */
	public void setActualMaxCaps (BigDecimal ActualMaxCaps)
	{
		set_Value (COLUMNNAME_ActualMaxCaps, ActualMaxCaps);
	}

	/** Get Actual Max Caps.
		@return Actual Max Caps	  */
	public BigDecimal getActualMaxCaps () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualMaxCaps);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Max Capacities (MT).
		@param ActualMaxCapsMT Actual Max Capacities (MT)	  */
	public void setActualMaxCapsMT (BigDecimal ActualMaxCapsMT)
	{
		set_Value (COLUMNNAME_ActualMaxCapsMT, ActualMaxCapsMT);
	}

	/** Get Actual Max Capacities (MT).
		@return Actual Max Capacities (MT)	  */
	public BigDecimal getActualMaxCapsMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualMaxCapsMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Daily Avg Production Hours.
		@param DailyAvgProductionHours Daily Avg Production Hours	  */
	public void setDailyAvgProductionHours (BigDecimal DailyAvgProductionHours)
	{
		set_Value (COLUMNNAME_DailyAvgProductionHours, DailyAvgProductionHours);
	}

	/** Get Daily Avg Production Hours.
		@return Daily Avg Production Hours	  */
	public BigDecimal getDailyAvgProductionHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DailyAvgProductionHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sunday Work Hours.
		@param Day1ProductionHours Sunday Work Hours	  */
	public void setDay1ProductionHours (BigDecimal Day1ProductionHours)
	{
		set_Value (COLUMNNAME_Day1ProductionHours, Day1ProductionHours);
	}

	/** Get Sunday Work Hours.
		@return Sunday Work Hours	  */
	public BigDecimal getDay1ProductionHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Day1ProductionHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Monday Production Hours.
		@param Day2ProductionHours Monday Production Hours	  */
	public void setDay2ProductionHours (BigDecimal Day2ProductionHours)
	{
		set_Value (COLUMNNAME_Day2ProductionHours, Day2ProductionHours);
	}

	/** Get Monday Production Hours.
		@return Monday Production Hours	  */
	public BigDecimal getDay2ProductionHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Day2ProductionHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tuesday Production Hours.
		@param Day3ProductionHours Tuesday Production Hours	  */
	public void setDay3ProductionHours (BigDecimal Day3ProductionHours)
	{
		set_Value (COLUMNNAME_Day3ProductionHours, Day3ProductionHours);
	}

	/** Get Tuesday Production Hours.
		@return Tuesday Production Hours	  */
	public BigDecimal getDay3ProductionHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Day3ProductionHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Wednesday Production Hours.
		@param Day4ProductionHours Wednesday Production Hours	  */
	public void setDay4ProductionHours (BigDecimal Day4ProductionHours)
	{
		set_Value (COLUMNNAME_Day4ProductionHours, Day4ProductionHours);
	}

	/** Get Wednesday Production Hours.
		@return Wednesday Production Hours	  */
	public BigDecimal getDay4ProductionHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Day4ProductionHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Thursday Production Hours.
		@param Day5ProductionHours Thursday Production Hours	  */
	public void setDay5ProductionHours (BigDecimal Day5ProductionHours)
	{
		set_Value (COLUMNNAME_Day5ProductionHours, Day5ProductionHours);
	}

	/** Get Thursday Production Hours.
		@return Thursday Production Hours	  */
	public BigDecimal getDay5ProductionHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Day5ProductionHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Friday Production Hours.
		@param Day6ProductionHours Friday Production Hours	  */
	public void setDay6ProductionHours (BigDecimal Day6ProductionHours)
	{
		set_Value (COLUMNNAME_Day6ProductionHours, Day6ProductionHours);
	}

	/** Get Friday Production Hours.
		@return Friday Production Hours	  */
	public BigDecimal getDay6ProductionHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Day6ProductionHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Saturday Production Hours.
		@param Day7ProductionHours Saturday Production Hours	  */
	public void setDay7ProductionHours (BigDecimal Day7ProductionHours)
	{
		set_Value (COLUMNNAME_Day7ProductionHours, Day7ProductionHours);
	}

	/** Get Saturday Production Hours.
		@return Saturday Production Hours	  */
	public BigDecimal getDay7ProductionHours () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Day7ProductionHours);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Expected Production Cost.
		@param ExpectedProductionCost Expected Production Cost	  */
	public void setExpectedProductionCost (BigDecimal ExpectedProductionCost)
	{
		set_Value (COLUMNNAME_ExpectedProductionCost, ExpectedProductionCost);
	}

	/** Get Expected Production Cost.
		@return Expected Production Cost	  */
	public BigDecimal getExpectedProductionCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExpectedProductionCost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Input = I */
	public static final String INOUTTYPE_Input = "I";
	/** Output = O */
	public static final String INOUTTYPE_Output = "O";
	/** Set InOut Type.
		@param InOutType InOut Type	  */
	public void setInOutType (String InOutType)
	{

		set_Value (COLUMNNAME_InOutType, InOutType);
	}

	/** Get InOut Type.
		@return InOut Type	  */
	public String getInOutType () 
	{
		return (String)get_Value(COLUMNNAME_InOutType);
	}

	/** Set Primary.
		@param IsPrimary 
		Indicates if this is the primary budget
	  */
	public void setIsPrimary (boolean IsPrimary)
	{
		set_Value (COLUMNNAME_IsPrimary, Boolean.valueOf(IsPrimary));
	}

	/** Get Primary.
		@return Indicates if this is the primary budget
	  */
	public boolean isPrimary () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrimary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Single Attribute.
		@param IsSingleAttribute 
		To indicate that this product's stock is single ASI (One Product's Stock One ASI)
	  */
	public void setIsSingleAttribute (boolean IsSingleAttribute)
	{
		set_Value (COLUMNNAME_IsSingleAttribute, Boolean.valueOf(IsSingleAttribute));
	}

	/** Get Is Single Attribute.
		@return To indicate that this product's stock is single ASI (One Product's Stock One ASI)
	  */
	public boolean isSingleAttribute () 
	{
		Object oo = get_Value(COLUMNNAME_IsSingleAttribute);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
    {
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Max. Capaciy.
		@param MaxCaps Max. Capaciy	  */
	public void setMaxCaps (BigDecimal MaxCaps)
	{
		set_Value (COLUMNNAME_MaxCaps, MaxCaps);
	}

	/** Get Max. Capaciy.
		@return Max. Capaciy	  */
	public BigDecimal getMaxCaps () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxCaps);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Max Caps (MT).
		@param MaxCapsMT Max Caps (MT)	  */
	public void setMaxCapsMT (BigDecimal MaxCapsMT)
	{
		set_Value (COLUMNNAME_MaxCapsMT, MaxCapsMT);
	}

	/** Get Max Caps (MT).
		@return Max Caps (MT)	  */
	public BigDecimal getMaxCapsMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxCapsMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Optimum Caps.
		@param OptimumCaps Optimum Caps	  */
	public void setOptimumCaps (BigDecimal OptimumCaps)
	{
		set_Value (COLUMNNAME_OptimumCaps, OptimumCaps);
	}

	/** Get Optimum Caps.
		@return Optimum Caps	  */
	public BigDecimal getOptimumCaps () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OptimumCaps);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Optimum Caps MT.
		@param OptimumCapsMT Optimum Caps MT	  */
	public void setOptimumCapsMT (BigDecimal OptimumCapsMT)
	{
		set_Value (COLUMNNAME_OptimumCapsMT, OptimumCapsMT);
	}

	/** Get Optimum Caps MT.
		@return Optimum Caps MT	  */
	public BigDecimal getOptimumCapsMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OptimumCapsMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Output Link Name.
		@param OutputLinkName 
		The value of product that is set as it's Output Link.
	  */
	public void setOutputLinkName (String OutputLinkName)
	{
		set_Value (COLUMNNAME_OutputLinkName, OutputLinkName);
	}

	/** Get Output Link Name.
		@return The value of product that is set as it's Output Link.
	  */
	public String getOutputLinkName () 
	{
		return (String)get_Value(COLUMNNAME_OutputLinkName);
	}

	/** Multi = MLT */
	public static final String OUTPUTTYPE_Multi = "MLT";
	/** Multi Optional = MOP */
	public static final String OUTPUTTYPE_MultiOptional = "MOP";
	/** Optional = OPT */
	public static final String OUTPUTTYPE_Optional = "OPT";
	/** Single = SGL */
	public static final String OUTPUTTYPE_Single = "SGL";
	/** Set Output Type.
		@param OutputType Output Type	  */
	public void setOutputType (String OutputType)
	{

		set_Value (COLUMNNAME_OutputType, OutputType);
	}

	/** Get Output Type.
		@return Output Type	  */
	public String getOutputType () 
	{
		return (String)get_Value(COLUMNNAME_OutputType);
	}

	/** Set QA Monitoring.
		@param QAMonitoring 
		This product must be monitoring by QA before next process.
	  */
	public void setQAMonitoring (boolean QAMonitoring)
	{
		set_Value (COLUMNNAME_QAMonitoring, Boolean.valueOf(QAMonitoring));
	}

	/** Get QA Monitoring.
		@return This product must be monitoring by QA before next process.
	  */
	public boolean isQAMonitoring () 
	{
		Object oo = get_Value(COLUMNNAME_QAMonitoring);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Resource Name.
		@param ResourceName 
		The name of this linked resource id.
	  */
	public void setResourceName (String ResourceName)
	{
		set_Value (COLUMNNAME_ResourceName, ResourceName);
	}

	/** Get Resource Name.
		@return The name of this linked resource id.
	  */
	public String getResourceName () 
	{
		return (String)get_Value(COLUMNNAME_ResourceName);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getResourceName());
    }

	/** Set ToBeForecast.
		@param ToBeForecast ToBeForecast	  */
	public void setToBeForecast (boolean ToBeForecast)
	{
		set_Value (COLUMNNAME_ToBeForecast, Boolean.valueOf(ToBeForecast));
	}

	/** Get ToBeForecast.
		@return ToBeForecast	  */
	public boolean isToBeForecast () 
	{
		Object oo = get_Value(COLUMNNAME_ToBeForecast);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public com.uns.model.I_UNS_Resource_InOut getUNS_OutputLink() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource_InOut)MTable.get(getCtx(), com.uns.model.I_UNS_Resource_InOut.Table_Name)
			.getPO(getUNS_OutputLink_ID(), get_TrxName());	}

	/** Set Output Link.
		@param UNS_OutputLink_ID 
		The resource output this output linked to.
	  */
	public void setUNS_OutputLink_ID (int UNS_OutputLink_ID)
	{
		if (UNS_OutputLink_ID < 1) 
			set_Value (COLUMNNAME_UNS_OutputLink_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_OutputLink_ID, Integer.valueOf(UNS_OutputLink_ID));
	}

	/** Get Output Link.
		@return The resource output this output linked to.
	  */
	public int getUNS_OutputLink_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_OutputLink_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource)MTable.get(getCtx(), com.uns.model.I_UNS_Resource.Table_Name)
			.getPO(getUNS_Resource_ID(), get_TrxName());	}

	/** Set Manufacture Resource.
		@param UNS_Resource_ID Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID)
	{
		if (UNS_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
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

	/** Set Manufacture Resource InOut.
		@param UNS_Resource_InOut_ID Manufacture Resource InOut	  */
	public void setUNS_Resource_InOut_ID (int UNS_Resource_InOut_ID)
	{
		if (UNS_Resource_InOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_InOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_InOut_ID, Integer.valueOf(UNS_Resource_InOut_ID));
	}

	/** Get Manufacture Resource InOut.
		@return Manufacture Resource InOut	  */
	public int getUNS_Resource_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Resource_InOut_UU.
		@param UNS_Resource_InOut_UU UNS_Resource_InOut_UU	  */
	public void setUNS_Resource_InOut_UU (String UNS_Resource_InOut_UU)
	{
		set_Value (COLUMNNAME_UNS_Resource_InOut_UU, UNS_Resource_InOut_UU);
	}

	/** Get UNS_Resource_InOut_UU.
		@return UNS_Resource_InOut_UU	  */
	public String getUNS_Resource_InOut_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Resource_InOut_UU);
	}

	/** Set Resource Transition.
		@param UNS_Resource_Transition_ID Resource Transition	  */
	public void setUNS_Resource_Transition_ID (int UNS_Resource_Transition_ID)
	{
		if (UNS_Resource_Transition_ID < 1) 
			set_Value (COLUMNNAME_UNS_Resource_Transition_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Resource_Transition_ID, Integer.valueOf(UNS_Resource_Transition_ID));
	}

	/** Get Resource Transition.
		@return Resource Transition	  */
	public int getUNS_Resource_Transition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_Transition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getUOMCaps() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMCaps_ID(), get_TrxName());	}

	/** Set UOM Capaciy.
		@param UOMCaps_ID UOM Capaciy	  */
	public void setUOMCaps_ID (int UOMCaps_ID)
	{
		if (UOMCaps_ID < 1) 
			set_Value (COLUMNNAME_UOMCaps_ID, null);
		else 
			set_Value (COLUMNNAME_UOMCaps_ID, Integer.valueOf(UOMCaps_ID));
	}

	/** Get UOM Capaciy.
		@return UOM Capaciy	  */
	public int getUOMCaps_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMCaps_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getUOMTime() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMTime_ID(), get_TrxName());	}

	/** Set UOM Time.
		@param UOMTime_ID UOM Time	  */
	public void setUOMTime_ID (int UOMTime_ID)
	{
		if (UOMTime_ID < 1) 
			set_Value (COLUMNNAME_UOMTime_ID, null);
		else 
			set_Value (COLUMNNAME_UOMTime_ID, Integer.valueOf(UOMTime_ID));
	}

	/** Get UOM Time.
		@return UOM Time	  */
	public int getUOMTime_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMTime_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}