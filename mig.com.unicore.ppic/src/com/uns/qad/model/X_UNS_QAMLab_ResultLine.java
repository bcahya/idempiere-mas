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

/** Generated Model for UNS_QAMLab_ResultLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAMLab_ResultLine extends PO implements I_UNS_QAMLab_ResultLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131219L;

    /** Standard Constructor */
    public X_UNS_QAMLab_ResultLine (Properties ctx, int UNS_QAMLab_ResultLine_ID, String trxName)
    {
      super (ctx, UNS_QAMLab_ResultLine_ID, trxName);
      /** if (UNS_QAMLab_ResultLine_ID == 0)
        {
			setResult (Env.ZERO);
// 0
			setUNS_QALabTest_ID (0);
			setUNS_QAMLab_ResultLine_ID (0);
			setUNS_QAMonitoringLabLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAMLab_ResultLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAMLab_ResultLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Operator Name.
		@param Operator Operator Name	  */
	public void setOperator (String Operator)
	{
		set_Value (COLUMNNAME_Operator, Operator);
	}

	/** Get Operator Name.
		@return Operator Name	  */
	public String getOperator () 
	{
		return (String)get_Value(COLUMNNAME_Operator);
	}

	/** Set Remark.
		@param Remark Remark	  */
	public void setRemark (String Remark)
	{
		set_Value (COLUMNNAME_Remark, Remark);
	}

	/** Get Remark.
		@return Remark	  */
	public String getRemark () 
	{
		return (String)get_Value(COLUMNNAME_Remark);
	}

	/** Set Result.
		@param Result 
		Result of the action taken
	  */
	public void setResult (BigDecimal Result)
	{
		set_Value (COLUMNNAME_Result, Result);
	}

	/** Get Result.
		@return Result of the action taken
	  */
	public BigDecimal getResult () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Result);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Second Result.
		@param Result2 
		Result of the action taken
	  */
	public void setResult2 (String Result2)
	{
		set_Value (COLUMNNAME_Result2, Result2);
	}

	/** Get Second Result.
		@return Result of the action taken
	  */
	public String getResult2 () 
	{
		return (String)get_Value(COLUMNNAME_Result2);
	}

	public com.uns.qad.model.I_UNS_QALabTest getUNS_QALabTest() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QALabTest)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QALabTest.Table_Name)
			.getPO(getUNS_QALabTest_ID(), get_TrxName());	}

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

	public com.uns.qad.model.I_UNS_QAMLab_Result getUNS_QAMLab_Result() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAMLab_Result)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAMLab_Result.Table_Name)
			.getPO(getUNS_QAMLab_Result_ID(), get_TrxName());	}

	/** Set Laboratory Result.
		@param UNS_QAMLab_Result_ID Laboratory Result	  */
	public void setUNS_QAMLab_Result_ID (int UNS_QAMLab_Result_ID)
	{
		if (UNS_QAMLab_Result_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMLab_Result_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMLab_Result_ID, Integer.valueOf(UNS_QAMLab_Result_ID));
	}

	/** Get Laboratory Result.
		@return Laboratory Result	  */
	public int getUNS_QAMLab_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAMLab_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.qad.model.I_UNS_QAMLab_ResultInfo getUNS_QAMLab_ResultInfo() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAMLab_ResultInfo)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAMLab_ResultInfo.Table_Name)
			.getPO(getUNS_QAMLab_ResultInfo_ID(), get_TrxName());	}

	/** Set Laboratory Result Info.
		@param UNS_QAMLab_ResultInfo_ID Laboratory Result Info	  */
	public void setUNS_QAMLab_ResultInfo_ID (int UNS_QAMLab_ResultInfo_ID)
	{
		if (UNS_QAMLab_ResultInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMLab_ResultInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMLab_ResultInfo_ID, Integer.valueOf(UNS_QAMLab_ResultInfo_ID));
	}

	/** Get Laboratory Result Info.
		@return Laboratory Result Info	  */
	public int getUNS_QAMLab_ResultInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAMLab_ResultInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Laboratory Result Line.
		@param UNS_QAMLab_ResultLine_ID Laboratory Result Line	  */
	public void setUNS_QAMLab_ResultLine_ID (int UNS_QAMLab_ResultLine_ID)
	{
		if (UNS_QAMLab_ResultLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMLab_ResultLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMLab_ResultLine_ID, Integer.valueOf(UNS_QAMLab_ResultLine_ID));
	}

	/** Get Laboratory Result Line.
		@return Laboratory Result Line	  */
	public int getUNS_QAMLab_ResultLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAMLab_ResultLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAMLab_ResultLine_UU.
		@param UNS_QAMLab_ResultLine_UU UNS_QAMLab_ResultLine_UU	  */
	public void setUNS_QAMLab_ResultLine_UU (String UNS_QAMLab_ResultLine_UU)
	{
		set_Value (COLUMNNAME_UNS_QAMLab_ResultLine_UU, UNS_QAMLab_ResultLine_UU);
	}

	/** Get UNS_QAMLab_ResultLine_UU.
		@return UNS_QAMLab_ResultLine_UU	  */
	public String getUNS_QAMLab_ResultLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAMLab_ResultLine_UU);
	}

	public com.uns.qad.model.I_UNS_QAMonitoringLabLine getUNS_QAMonitoringLabLine() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAMonitoringLabLine)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAMonitoringLabLine.Table_Name)
			.getPO(getUNS_QAMonitoringLabLine_ID(), get_TrxName());	}

	/** Set Monitoring Test.
		@param UNS_QAMonitoringLabLine_ID Monitoring Test	  */
	public void setUNS_QAMonitoringLabLine_ID (int UNS_QAMonitoringLabLine_ID)
	{
		if (UNS_QAMonitoringLabLine_ID < 1) 
			set_Value (COLUMNNAME_UNS_QAMonitoringLabLine_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_QAMonitoringLabLine_ID, Integer.valueOf(UNS_QAMonitoringLabLine_ID));
	}

	/** Get Monitoring Test.
		@return Monitoring Test	  */
	public int getUNS_QAMonitoringLabLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAMonitoringLabLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WO Request Line.
		@param UNS_WO_RequestLine_ID WO Request Line	  */
	public void setUNS_WO_RequestLine_ID (int UNS_WO_RequestLine_ID)
	{
		if (UNS_WO_RequestLine_ID < 1) 
			set_Value (COLUMNNAME_UNS_WO_RequestLine_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_WO_RequestLine_ID, Integer.valueOf(UNS_WO_RequestLine_ID));
	}

	/** Get WO Request Line.
		@return WO Request Line	  */
	public int getUNS_WO_RequestLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_WO_RequestLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}