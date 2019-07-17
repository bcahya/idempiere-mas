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

/** Generated Model for UNS_QAMonitoringLabLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAMonitoringLabLine extends PO implements I_UNS_QAMonitoringLabLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130523L;

    /** Standard Constructor */
    public X_UNS_QAMonitoringLabLine (Properties ctx, int UNS_QAMonitoringLabLine_ID, String trxName)
    {
      super (ctx, UNS_QAMonitoringLabLine_ID, trxName);
      /** if (UNS_QAMonitoringLabLine_ID == 0)
        {
			setUNS_QALabTest_ID (0);
			setUNS_QAMonitoringLab_ID (0);
			setUNS_QAMonitoringLabLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAMonitoringLabLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAMonitoringLabLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public com.uns.qad.model.I_UNS_QALabTest getUNS_QALabTest() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QALabTest)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QALabTest.Table_Name)
			.getPO(getUNS_QALabTest_ID(), get_TrxName());	}

	/** Set Labolatory Test.
		@param UNS_QALabTest_ID Labolatory Test	  */
	public void setUNS_QALabTest_ID (int UNS_QALabTest_ID)
	{
		if (UNS_QALabTest_ID < 1) 
			set_Value (COLUMNNAME_UNS_QALabTest_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_QALabTest_ID, Integer.valueOf(UNS_QALabTest_ID));
	}

	/** Get Labolatory Test.
		@return Labolatory Test	  */
	public int getUNS_QALabTest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QALabTest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.qad.model.I_UNS_QAMonitoringLab getUNS_QAMonitoringLab() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAMonitoringLab)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAMonitoringLab.Table_Name)
			.getPO(getUNS_QAMonitoringLab_ID(), get_TrxName());	}

	/** Set Monitoring Labolatory.
		@param UNS_QAMonitoringLab_ID Monitoring Labolatory	  */
	public void setUNS_QAMonitoringLab_ID (int UNS_QAMonitoringLab_ID)
	{
		if (UNS_QAMonitoringLab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMonitoringLab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMonitoringLab_ID, Integer.valueOf(UNS_QAMonitoringLab_ID));
	}

	/** Get Monitoring Labolatory.
		@return Monitoring Labolatory	  */
	public int getUNS_QAMonitoringLab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAMonitoringLab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Monitoring Test.
		@param UNS_QAMonitoringLabLine_ID Monitoring Test	  */
	public void setUNS_QAMonitoringLabLine_ID (int UNS_QAMonitoringLabLine_ID)
	{
		if (UNS_QAMonitoringLabLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMonitoringLabLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMonitoringLabLine_ID, Integer.valueOf(UNS_QAMonitoringLabLine_ID));
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

	/** Set UNS_QAMonitoringLabLine_UU.
		@param UNS_QAMonitoringLabLine_UU UNS_QAMonitoringLabLine_UU	  */
	public void setUNS_QAMonitoringLabLine_UU (String UNS_QAMonitoringLabLine_UU)
	{
		set_Value (COLUMNNAME_UNS_QAMonitoringLabLine_UU, UNS_QAMonitoringLabLine_UU);
	}

	/** Get UNS_QAMonitoringLabLine_UU.
		@return UNS_QAMonitoringLabLine_UU	  */
	public String getUNS_QAMonitoringLabLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAMonitoringLabLine_UU);
	}
}