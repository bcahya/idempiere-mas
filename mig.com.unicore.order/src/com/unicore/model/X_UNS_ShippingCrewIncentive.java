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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_ShippingCrewIncentive
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_ShippingCrewIncentive extends PO implements I_UNS_ShippingCrewIncentive, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170826L;

    /** Standard Constructor */
    public X_UNS_ShippingCrewIncentive (Properties ctx, int UNS_ShippingCrewIncentive_ID, String trxName)
    {
      super (ctx, UNS_ShippingCrewIncentive_ID, trxName);
      /** if (UNS_ShippingCrewIncentive_ID == 0)
        {
			setCreateSettlement (null);
// N
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setDateTo (new Timestamp( System.currentTimeMillis() ));
			setGrandTotal (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_UNS_ShippingCrewIncentive (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ShippingCrewIncentive[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount Bonuses.
		@param AmountBonuses Amount Bonuses	  */
	public void setAmountBonuses (BigDecimal AmountBonuses)
	{
		set_Value (COLUMNNAME_AmountBonuses, AmountBonuses);
	}

	/** Get Amount Bonuses.
		@return Amount Bonuses	  */
	public BigDecimal getAmountBonuses () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmountBonuses);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Create Settlement.
		@param CreateSettlement Create Settlement	  */
	public void setCreateSettlement (String CreateSettlement)
	{
		set_Value (COLUMNNAME_CreateSettlement, CreateSettlement);
	}

	/** Get Create Settlement.
		@return Create Settlement	  */
	public String getCreateSettlement () 
	{
		return (String)get_Value(COLUMNNAME_CreateSettlement);
	}

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Date From.
		@param DateFrom 
		Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Date From.
		@return Starting date for a range
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Date Processed.
		@param DateProcessed Date Processed	  */
	public void setDateProcessed (Timestamp DateProcessed)
	{
		set_Value (COLUMNNAME_DateProcessed, DateProcessed);
	}

	/** Get Date Processed.
		@return Date Processed	  */
	public Timestamp getDateProcessed () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateProcessed);
	}

	/** Set Date To.
		@param DateTo 
		End date of a date range
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Date To.
		@return End date of a date range
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_ValueNoCheck (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getGrandTotal()));
    }

	/** Set Print Document.
		@param PrintDocument Print Document	  */
	public void setPrintDocument (String PrintDocument)
	{
		set_Value (COLUMNNAME_PrintDocument, PrintDocument);
	}

	/** Get Print Document.
		@return Print Document	  */
	public String getPrintDocument () 
	{
		return (String)get_Value(COLUMNNAME_PrintDocument);
	}

	/** Set Charge Request / Settlement.
		@param UNS_Charge_RS_ID Charge Request / Settlement	  */
	public void setUNS_Charge_RS_ID (int UNS_Charge_RS_ID)
	{
		if (UNS_Charge_RS_ID < 1) 
			set_Value (COLUMNNAME_UNS_Charge_RS_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Charge_RS_ID, Integer.valueOf(UNS_Charge_RS_ID));
	}

	/** Get Charge Request / Settlement.
		@return Charge Request / Settlement	  */
	public int getUNS_Charge_RS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Charge_RS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set Shipping Crew Incentive ID.
		@param UNS_ShippingCrewIncentive_ID Shipping Crew Incentive ID	  */
	public void setUNS_ShippingCrewIncentive_ID (int UNS_ShippingCrewIncentive_ID)
	{
		if (UNS_ShippingCrewIncentive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ShippingCrewIncentive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ShippingCrewIncentive_ID, Integer.valueOf(UNS_ShippingCrewIncentive_ID));
	}

	/** Get Shipping Crew Incentive ID.
		@return Shipping Crew Incentive ID	  */
	public int getUNS_ShippingCrewIncentive_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ShippingCrewIncentive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipping Crew Incentive UU.
		@param UNS_ShippingCrewIncentive_UU Shipping Crew Incentive UU	  */
	public void setUNS_ShippingCrewIncentive_UU (String UNS_ShippingCrewIncentive_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_ShippingCrewIncentive_UU, UNS_ShippingCrewIncentive_UU);
	}

	/** Get Shipping Crew Incentive UU.
		@return Shipping Crew Incentive UU	  */
	public String getUNS_ShippingCrewIncentive_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ShippingCrewIncentive_UU);
	}
}