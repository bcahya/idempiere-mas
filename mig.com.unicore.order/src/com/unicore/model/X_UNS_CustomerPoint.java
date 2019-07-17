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

/** Generated Model for UNS_CustomerPoint
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_CustomerPoint extends PO implements I_UNS_CustomerPoint, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150527L;

    /** Standard Constructor */
    public X_UNS_CustomerPoint (Properties ctx, int UNS_CustomerPoint_ID, String trxName)
    {
      super (ctx, UNS_CustomerPoint_ID, trxName);
      /** if (UNS_CustomerPoint_ID == 0)
        {
			setUNS_CustomerPoint_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_CustomerPoint (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_CustomerPoint[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accumulated Point Not Yet Valid.
		@param AccumulatedPointNotYetValid Accumulated Point Not Yet Valid	  */
	public void setAccumulatedPointNotYetValid (BigDecimal AccumulatedPointNotYetValid)
	{
		throw new IllegalArgumentException ("AccumulatedPointNotYetValid is virtual column");	}

	/** Get Accumulated Point Not Yet Valid.
		@return Accumulated Point Not Yet Valid	  */
	public BigDecimal getAccumulatedPointNotYetValid () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AccumulatedPointNotYetValid);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Accumulated Point Valid.
		@param AccumulatedPointValid 
		Accumulated Point Valid
	  */
	public void setAccumulatedPointValid (BigDecimal AccumulatedPointValid)
	{
		throw new IllegalArgumentException ("AccumulatedPointValid is virtual column");	}

	/** Get Accumulated Point Valid.
		@return Accumulated Point Valid
	  */
	public BigDecimal getAccumulatedPointValid () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AccumulatedPointValid);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AccumulatedRedeemed.
		@param AccumulatedRedeemed AccumulatedRedeemed	  */
	public void setAccumulatedRedeemed (int AccumulatedRedeemed)
	{
		set_Value (COLUMNNAME_AccumulatedRedeemed, Integer.valueOf(AccumulatedRedeemed));
	}

	/** Get AccumulatedRedeemed.
		@return AccumulatedRedeemed	  */
	public int getAccumulatedRedeemed () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AccumulatedRedeemed);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
    {
		return (org.compiere.model.I_C_BP_Group)MTable.get(getCtx(), org.compiere.model.I_C_BP_Group.Table_Name)
			.getPO(getC_BP_Group_ID(), get_TrxName());	}

	/** Set Business Partner Group.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Business Partner Group.
		@return Business Partner Group
	  */
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CurrentPoint.
		@param CurrentPoint CurrentPoint	  */
	public void setCurrentPoint (BigDecimal CurrentPoint)
	{
		throw new IllegalArgumentException ("CurrentPoint is virtual column");	}

	/** Get CurrentPoint.
		@return CurrentPoint	  */
//	public BigDecimal getCurrentPoint () 
//	{
//		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrentPoint);
//		if (bd == null)
//			 return Env.ZERO;
//		return bd;
//	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set LatestRedemptionDate.
		@param LatestRedemptionDate LatestRedemptionDate	  */
	public void setLatestRedemptionDate (Timestamp LatestRedemptionDate)
	{
		set_Value (COLUMNNAME_LatestRedemptionDate, LatestRedemptionDate);
	}

	/** Get LatestRedemptionDate.
		@return LatestRedemptionDate	  */
	public Timestamp getLatestRedemptionDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LatestRedemptionDate);
	}

	/** Set LatestTrxDate.
		@param LatestTrxDate LatestTrxDate	  */
	public void setLatestTrxDate (Timestamp LatestTrxDate)
	{
		set_Value (COLUMNNAME_LatestTrxDate, LatestTrxDate);
	}

	/** Get LatestTrxDate.
		@return LatestTrxDate	  */
	public Timestamp getLatestTrxDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LatestTrxDate);
	}

	/** Set StartTrxDate.
		@param StartTrxDate StartTrxDate	  */
	public void setStartTrxDate (Timestamp StartTrxDate)
	{
		set_Value (COLUMNNAME_StartTrxDate, StartTrxDate);
	}

	/** Get StartTrxDate.
		@return StartTrxDate	  */
	public Timestamp getStartTrxDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartTrxDate);
	}

	/** Set Customer Point.
		@param UNS_CustomerPoint_ID Customer Point	  */
	public void setUNS_CustomerPoint_ID (int UNS_CustomerPoint_ID)
	{
		if (UNS_CustomerPoint_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerPoint_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CustomerPoint_ID, Integer.valueOf(UNS_CustomerPoint_ID));
	}

	/** Get Customer Point.
		@return Customer Point	  */
	public int getUNS_CustomerPoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CustomerPoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_CustomerPoint_UU.
		@param UNS_CustomerPoint_UU UNS_CustomerPoint_UU	  */
	public void setUNS_CustomerPoint_UU (String UNS_CustomerPoint_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_CustomerPoint_UU, UNS_CustomerPoint_UU);
	}

	/** Get UNS_CustomerPoint_UU.
		@return UNS_CustomerPoint_UU	  */
	public String getUNS_CustomerPoint_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_CustomerPoint_UU);
	}

	/** Set Outlet Type.
		@param UNS_Outlet_Type_ID Outlet Type	  */
	public void setUNS_Outlet_Type_ID (int UNS_Outlet_Type_ID)
	{
		if (UNS_Outlet_Type_ID < 1) 
			set_Value (COLUMNNAME_UNS_Outlet_Type_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Outlet_Type_ID, Integer.valueOf(UNS_Outlet_Type_ID));
	}

	/** Get Outlet Type.
		@return Outlet Type	  */
	public int getUNS_Outlet_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Outlet_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}