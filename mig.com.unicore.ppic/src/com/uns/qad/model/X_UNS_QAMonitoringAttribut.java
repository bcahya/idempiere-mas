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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_QAMonitoringAttribut
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAMonitoringAttribut extends PO implements I_UNS_QAMonitoringAttribut, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130617L;

    /** Standard Constructor */
    public X_UNS_QAMonitoringAttribut (Properties ctx, int UNS_QAMonitoringAttribut_ID, String trxName)
    {
      super (ctx, UNS_QAMonitoringAttribut_ID, trxName);
      /** if (UNS_QAMonitoringAttribut_ID == 0)
        {
			setneedLabResult (false);
// N
			setUNS_QAAttributeTest_ID (0);
			setUNS_QAMonitoringAttribut_ID (0);
			setUNS_QAStatus_Type_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAMonitoringAttribut (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAMonitoringAttribut[")
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
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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

	/** Set Calculated.
		@param IsCalculated 
		The value is calculated by the system
	  */
	public void setIsCalculated (boolean IsCalculated)
	{
		set_ValueNoCheck (COLUMNNAME_IsCalculated, Boolean.valueOf(IsCalculated));
	}

	/** Get Calculated.
		@return The value is calculated by the system
	  */
	public boolean isCalculated () 
	{
		Object oo = get_Value(COLUMNNAME_IsCalculated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Confirmation with Lab Result.
		@param needLabResult Confirmation with Lab Result	  */
	public void setneedLabResult (boolean needLabResult)
	{
		set_ValueNoCheck (COLUMNNAME_needLabResult, Boolean.valueOf(needLabResult));
	}

	/** Get Confirmation with Lab Result.
		@return Confirmation with Lab Result	  */
	public boolean isneedLabResult () 
	{
		Object oo = get_Value(COLUMNNAME_needLabResult);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_UNS_QAAttributeTest getUNS_QAAttributeTest() throws RuntimeException
    {
		return (I_UNS_QAAttributeTest)MTable.get(getCtx(), I_UNS_QAAttributeTest.Table_Name)
			.getPO(getUNS_QAAttributeTest_ID(), get_TrxName());	}

	/** Set Attribute Test.
		@param UNS_QAAttributeTest_ID Attribute Test	  */
	public void setUNS_QAAttributeTest_ID (int UNS_QAAttributeTest_ID)
	{
		if (UNS_QAAttributeTest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAAttributeTest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAAttributeTest_ID, Integer.valueOf(UNS_QAAttributeTest_ID));
	}

	/** Get Attribute Test.
		@return Attribute Test	  */
	public int getUNS_QAAttributeTest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAAttributeTest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Monitoring Attribut.
		@param UNS_QAMonitoringAttribut_ID Monitoring Attribut	  */
	public void setUNS_QAMonitoringAttribut_ID (int UNS_QAMonitoringAttribut_ID)
	{
		if (UNS_QAMonitoringAttribut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMonitoringAttribut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMonitoringAttribut_ID, Integer.valueOf(UNS_QAMonitoringAttribut_ID));
	}

	/** Get Monitoring Attribut.
		@return Monitoring Attribut	  */
	public int getUNS_QAMonitoringAttribut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAMonitoringAttribut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAMonitoringAttribut_UU.
		@param UNS_QAMonitoringAttribut_UU UNS_QAMonitoringAttribut_UU	  */
	public void setUNS_QAMonitoringAttribut_UU (String UNS_QAMonitoringAttribut_UU)
	{
		set_Value (COLUMNNAME_UNS_QAMonitoringAttribut_UU, UNS_QAMonitoringAttribut_UU);
	}

	/** Get UNS_QAMonitoringAttribut_UU.
		@return UNS_QAMonitoringAttribut_UU	  */
	public String getUNS_QAMonitoringAttribut_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAMonitoringAttribut_UU);
	}

	public I_UNS_QAMonitoringLab getUNS_QAMonitoringLab() throws RuntimeException
    {
		return (I_UNS_QAMonitoringLab)MTable.get(getCtx(), I_UNS_QAMonitoringLab.Table_Name)
			.getPO(getUNS_QAMonitoringLab_ID(), get_TrxName());	}

	/** Set Monitoring Laboratory.
		@param UNS_QAMonitoringLab_ID Monitoring Laboratory	  */
	public void setUNS_QAMonitoringLab_ID (int UNS_QAMonitoringLab_ID)
	{
		if (UNS_QAMonitoringLab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMonitoringLab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMonitoringLab_ID, Integer.valueOf(UNS_QAMonitoringLab_ID));
	}

	/** Get Monitoring Laboratory.
		@return Monitoring Laboratory	  */
	public int getUNS_QAMonitoringLab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAMonitoringLab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UNS_QAStatus_Type getUNS_QAStatus_Type() throws RuntimeException
    {
		return (I_UNS_QAStatus_Type)MTable.get(getCtx(), I_UNS_QAStatus_Type.Table_Name)
			.getPO(getUNS_QAStatus_Type_ID(), get_TrxName());	}

	/** Set Attribute Type.
		@param UNS_QAStatus_Type_ID Attribute Type	  */
	public void setUNS_QAStatus_Type_ID (int UNS_QAStatus_Type_ID)
	{
		if (UNS_QAStatus_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_Type_ID, Integer.valueOf(UNS_QAStatus_Type_ID));
	}

	/** Get Attribute Type.
		@return Attribute Type	  */
	public int getUNS_QAStatus_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}