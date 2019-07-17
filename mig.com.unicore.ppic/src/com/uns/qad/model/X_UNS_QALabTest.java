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
package com.uns.qad.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_QALabTest
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QALabTest extends PO implements I_UNS_QALabTest, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130712L;

    /** Standard Constructor */
    public X_UNS_QALabTest (Properties ctx, int UNS_QALabTest_ID, String trxName)
    {
      super (ctx, UNS_QALabTest_ID, trxName);
      /** if (UNS_QALabTest_ID == 0)
        {
			setDecimalPoint (null);
			setMaxValue (Env.ZERO);
// 0
			setMinValue (Env.ZERO);
// 0
			setTestType (null);
			setThresHold_Type (null);
// RG
			setUNS_QALabTest_ID (0);
			setUNS_QALabTest_Type_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QALabTest (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_QALabTest[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accredited by National Accreditation Committee.
		@param AccByNAC Accredited by National Accreditation Committee	  */
	public void setAccByNAC (boolean AccByNAC)
	{
		set_Value (COLUMNNAME_AccByNAC, Boolean.valueOf(AccByNAC));
	}

	/** Get Accredited by National Accreditation Committee.
		@return Accredited by National Accreditation Committee	  */
	public boolean isAccByNAC () 
	{
		Object oo = get_Value(COLUMNNAME_AccByNAC);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** DecimalPoint AD_Reference_ID=1000145 */
	public static final int DECIMALPOINT_AD_Reference_ID=1000145;
	/** No Decimal Place = 0 */
	public static final String DECIMALPOINT_NoDecimalPlace = "0";
	/** One Decimal Places = 1 */
	public static final String DECIMALPOINT_OneDecimalPlaces = "1";
	/** Two Decimal Places = 2 */
	public static final String DECIMALPOINT_TwoDecimalPlaces = "2";
	/** Three Decimal Places = 3 */
	public static final String DECIMALPOINT_ThreeDecimalPlaces = "3";
	/** Four Decimal Places = 4 */
	public static final String DECIMALPOINT_FourDecimalPlaces = "4";
	/** Five Decimal Places = 5 */
	public static final String DECIMALPOINT_FiveDecimalPlaces = "5";
	/** Six Decimal Places = 6 */
	public static final String DECIMALPOINT_SixDecimalPlaces = "6";
	/** Set Decimal Point.
		@param DecimalPoint 
		Decimal Point in the data file - if any
	  */
	public void setDecimalPoint (String DecimalPoint)
	{

		set_Value (COLUMNNAME_DecimalPoint, DecimalPoint);
	}

	/** Get Decimal Point.
		@return Decimal Point in the data file - if any
	  */
	public String getDecimalPoint () 
	{
		return (String)get_Value(COLUMNNAME_DecimalPoint);
	}

	/** Set Equipment.
		@param Equipment Equipment	  */
	public void setEquipment (String Equipment)
	{
		set_Value (COLUMNNAME_Equipment, Equipment);
	}

	/** Get Equipment.
		@return Equipment	  */
	public String getEquipment () 
	{
		return (String)get_Value(COLUMNNAME_Equipment);
	}

	/** Set International References.
		@param InternationalReferences International References	  */
	public void setInternationalReferences (String InternationalReferences)
	{
		set_Value (COLUMNNAME_InternationalReferences, InternationalReferences);
	}

	/** Get International References.
		@return International References	  */
	public String getInternationalReferences () 
	{
		return (String)get_Value(COLUMNNAME_InternationalReferences);
	}

	/** Set Max Value.
		@param MaxValue Max Value	  */
	public void setMaxValue (BigDecimal MaxValue)
	{
		set_Value (COLUMNNAME_MaxValue, MaxValue);
	}

	/** Get Max Value.
		@return Max Value	  */
	public BigDecimal getMaxValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MaxValue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min Value.
		@param MinValue Min Value	  */
	public void setMinValue (BigDecimal MinValue)
	{
		set_Value (COLUMNNAME_MinValue, MinValue);
	}

	/** Get Min Value.
		@return Min Value	  */
	public BigDecimal getMinValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinValue);
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

	/** Set Negative Result.
		@param NegativeResult Negative Result	  */
	public void setNegativeResult (String NegativeResult)
	{
		set_Value (COLUMNNAME_NegativeResult, NegativeResult);
	}

	/** Get Negative Result.
		@return Negative Result	  */
	public String getNegativeResult () 
	{
		return (String)get_Value(COLUMNNAME_NegativeResult);
	}

	/** Set Parameter Value.
		@param ParameterValue Parameter Value	  */
	public void setParameterValue (String ParameterValue)
	{
		set_Value (COLUMNNAME_ParameterValue, ParameterValue);
	}

	/** Get Parameter Value.
		@return Parameter Value	  */
	public String getParameterValue () 
	{
		return (String)get_Value(COLUMNNAME_ParameterValue);
	}

	/** Set Passing By Value.
		@param PassByValue Passing By Value	  */
	public void setPassByValue (boolean PassByValue)
	{
		set_Value (COLUMNNAME_PassByValue, Boolean.valueOf(PassByValue));
	}

	/** Get Passing By Value.
		@return Passing By Value	  */
	public boolean isPassByValue () 
	{
		Object oo = get_Value(COLUMNNAME_PassByValue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Positive Result.
		@param PositiveResult Positive Result	  */
	public void setPositiveResult (String PositiveResult)
	{
		set_Value (COLUMNNAME_PositiveResult, PositiveResult);
	}

	/** Get Positive Result.
		@return Positive Result	  */
	public String getPositiveResult () 
	{
		return (String)get_Value(COLUMNNAME_PositiveResult);
	}

	public org.compiere.model.I_M_Product getProductService() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getProductService_ID(), get_TrxName());	}

	/** Set Product Service.
		@param ProductService_ID Product Service	  */
	public void setProductService_ID (int ProductService_ID)
	{
		if (ProductService_ID < 1) 
			set_Value (COLUMNNAME_ProductService_ID, null);
		else 
			set_Value (COLUMNNAME_ProductService_ID, Integer.valueOf(ProductService_ID));
	}

	/** Get Product Service.
		@return Product Service	  */
	public int getProductService_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ProductService_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Test Hour.
		@param TestHour Test Hour	  */
	public void setTestHour (BigDecimal TestHour)
	{
		set_Value (COLUMNNAME_TestHour, TestHour);
	}

	/** Get Test Hour.
		@return Test Hour	  */
	public BigDecimal getTestHour () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TestHour);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** TestType AD_Reference_ID=1000144 */
	public static final int TESTTYPE_AD_Reference_ID=1000144;
	/** Chemical = C */
	public static final String TESTTYPE_Chemical = "C";
	/** Microbiological = M */
	public static final String TESTTYPE_Microbiological = "M";
	/** Requested Product = R */
	public static final String TESTTYPE_RequestedProduct = "R";
	/** Set Test Type.
		@param TestType Test Type	  */
	public void setTestType (String TestType)
	{

		set_Value (COLUMNNAME_TestType, TestType);
	}

	/** Get Test Type.
		@return Test Type	  */
	public String getTestType () 
	{
		return (String)get_Value(COLUMNNAME_TestType);
	}

	/** ThresHold_Type AD_Reference_ID=1000124 */
	public static final int THRESHOLD_TYPE_AD_Reference_ID=1000124;
	/** Min = MN */
	public static final String THRESHOLD_TYPE_Min = "MN";
	/** Max = MX */
	public static final String THRESHOLD_TYPE_Max = "MX";
	/** Range = RG */
	public static final String THRESHOLD_TYPE_Range = "RG";
	/** Set Threshold Type.
		@param ThresHold_Type Threshold Type	  */
	public void setThresHold_Type (String ThresHold_Type)
	{

		set_Value (COLUMNNAME_ThresHold_Type, ThresHold_Type);
	}

	/** Get Threshold Type.
		@return Threshold Type	  */
	public String getThresHold_Type () 
	{
		return (String)get_Value(COLUMNNAME_ThresHold_Type);
	}

	/** Set Laboratory Test.
		@param UNS_QALabTest_ID Laboratory Test	  */
	public void setUNS_QALabTest_ID (int UNS_QALabTest_ID)
	{
		if (UNS_QALabTest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QALabTest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QALabTest_ID, Integer.valueOf(UNS_QALabTest_ID));
	}

	/** Get Laboratory Test.
		@return Laboratory Test	  */
	public int getUNS_QALabTest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QALabTest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UNS_QALabTest_Type getUNS_QALabTest_Type() throws RuntimeException
    {
		return (I_UNS_QALabTest_Type)MTable.get(getCtx(), I_UNS_QALabTest_Type.Table_Name)
			.getPO(getUNS_QALabTest_Type_ID(), get_TrxName());	}

	/** Set Laboratory Test Type.
		@param UNS_QALabTest_Type_ID Laboratory Test Type	  */
	public void setUNS_QALabTest_Type_ID (int UNS_QALabTest_Type_ID)
	{
		if (UNS_QALabTest_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QALabTest_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QALabTest_Type_ID, Integer.valueOf(UNS_QALabTest_Type_ID));
	}

	/** Get Laboratory Test Type.
		@return Laboratory Test Type	  */
	public int getUNS_QALabTest_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QALabTest_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QALabTest_UU.
		@param UNS_QALabTest_UU UNS_QALabTest_UU	  */
	public void setUNS_QALabTest_UU (String UNS_QALabTest_UU)
	{
		set_Value (COLUMNNAME_UNS_QALabTest_UU, UNS_QALabTest_UU);
	}

	/** Get UNS_QALabTest_UU.
		@return UNS_QALabTest_UU	  */
	public String getUNS_QALabTest_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QALabTest_UU);
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

	/** Set Old Code.
		@param Value2 
		Old Code laboratory test.
	  */
	public void setValue2 (String Value2)
	{
		set_Value (COLUMNNAME_Value2, Value2);
	}

	/** Get Old Code.
		@return Old Code laboratory test.
	  */
	public String getValue2 () 
	{
		return (String)get_Value(COLUMNNAME_Value2);
	}
}