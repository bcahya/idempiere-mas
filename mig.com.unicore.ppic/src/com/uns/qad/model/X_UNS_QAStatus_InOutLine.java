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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_QAStatus_InOutLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAStatus_InOutLine extends PO implements I_UNS_QAStatus_InOutLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140219L;

    /** Standard Constructor */
    public X_UNS_QAStatus_InOutLine (Properties ctx, int UNS_QAStatus_InOutLine_ID, String trxName)
    {
      super (ctx, UNS_QAStatus_InOutLine_ID, trxName);
      /** if (UNS_QAStatus_InOutLine_ID == 0)
        {
			setConfirmedQty (Env.ZERO);
// 0
			setM_InOutLine_ID (0);
			setQAStatus (null);
// OH
			setSampleQty (Env.ZERO);
// 0
			setTargetQty (Env.ZERO);
			setUNS_QAStatus_InOut_ID (0);
			setUNS_QAStatus_InOutLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAStatus_InOutLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAStatus_InOutLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Certificate of Analysis.
		@param COA 
		Certificate of Analysis
	  */
	public void setCOA (String COA)
	{
		set_Value (COLUMNNAME_COA, COA);
	}

	/** Get Certificate of Analysis.
		@return Certificate of Analysis
	  */
	public String getCOA () 
	{
		return (String)get_Value(COLUMNNAME_COA);
	}

	/** Set Confirmed Quantity.
		@param ConfirmedQty 
		Confirmation of a received quantity
	  */
	public void setConfirmedQty (BigDecimal ConfirmedQty)
	{
		set_Value (COLUMNNAME_ConfirmedQty, ConfirmedQty);
	}

	/** Get Confirmed Quantity.
		@return Confirmation of a received quantity
	  */
	public BigDecimal getConfirmedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ConfirmedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Expire On.
		@param Expiration 
		Expire On
	  */
	public void setExpiration (Timestamp Expiration)
	{
		set_Value (COLUMNNAME_Expiration, Expiration);
	}

	/** Get Expire On.
		@return Expire On
	  */
	public Timestamp getExpiration () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Expiration);
	}

	/** Set Is Return After Inspection.
		@param IsReturnAfterInspection Is Return After Inspection	  */
	public void setIsReturnAfterInspection (boolean IsReturnAfterInspection)
	{
		set_Value (COLUMNNAME_IsReturnAfterInspection, Boolean.valueOf(IsReturnAfterInspection));
	}

	/** Get Is Return After Inspection.
		@return Is Return After Inspection	  */
	public boolean isReturnAfterInspection () 
	{
		Object oo = get_Value(COLUMNNAME_IsReturnAfterInspection);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOutLine)MTable.get(getCtx(), org.compiere.model.I_M_InOutLine.Table_Name)
			.getPO(getM_InOutLine_ID(), get_TrxName());	}

	/** Set Shipment/Receipt Line.
		@param M_InOutLine_ID 
		Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Shipment/Receipt Line.
		@return Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
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
		throw new IllegalArgumentException ("M_Product_ID is virtual column");	}

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

	/** Set Quantity NC.
		@param NCQty Quantity NC	  */
	public void setNCQty (BigDecimal NCQty)
	{
		set_Value (COLUMNNAME_NCQty, NCQty);
	}

	/** Get Quantity NC.
		@return Quantity NC	  */
	public BigDecimal getNCQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NCQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity OnHold.
		@param OnHoldQty Quantity OnHold	  */
	public void setOnHoldQty (BigDecimal OnHoldQty)
	{
		set_Value (COLUMNNAME_OnHoldQty, OnHoldQty);
	}

	/** Get Quantity OnHold.
		@return Quantity OnHold	  */
	public BigDecimal getOnHoldQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OnHoldQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** QAStatus AD_Reference_ID=1000128 */
	public static final int QASTATUS_AD_Reference_ID=1000128;
	/** On Hold = OH */
	public static final String QASTATUS_OnHold = "OH";
	/** Release = RE */
	public static final String QASTATUS_Release = "RE";
	/** Premature Released = PR */
	public static final String QASTATUS_PrematureReleased = "PR";
	/** Pending Inspection/Lab Test = PL */
	public static final String QASTATUS_PendingInspectionLabTest = "PL";
	/** Reject = RJ */
	public static final String QASTATUS_Reject = "RJ";
	/** Reprocess = RP */
	public static final String QASTATUS_Reprocess = "RP";
	/** Blending = BL */
	public static final String QASTATUS_Blending = "BL";
	/** Resorting = RS */
	public static final String QASTATUS_Resorting = "RS";
	/** Regrade = RG */
	public static final String QASTATUS_Regrade = "RG";
	/** Repacking = RA */
	public static final String QASTATUS_Repacking = "RA";
	/** Non Conformance = NC */
	public static final String QASTATUS_NonConformance = "NC";
	/** Incubation = IC */
	public static final String QASTATUS_Incubation = "IC";
	/** Set QA Status.
		@param QAStatus QA Status	  */
	public void setQAStatus (String QAStatus)
	{

		set_Value (COLUMNNAME_QAStatus, QAStatus);
	}

	/** Get QA Status.
		@return QA Status	  */
	public String getQAStatus () 
	{
		return (String)get_Value(COLUMNNAME_QAStatus);
	}

	/** Set Quantity Release.
		@param ReleaseQty Quantity Release	  */
	public void setReleaseQty (BigDecimal ReleaseQty)
	{
		set_Value (COLUMNNAME_ReleaseQty, ReleaseQty);
	}

	/** Get Quantity Release.
		@return Quantity Release	  */
	public BigDecimal getReleaseQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ReleaseQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Sample Qty.
		@param SampleQty Sample Qty	  */
	public void setSampleQty (BigDecimal SampleQty)
	{
		set_Value (COLUMNNAME_SampleQty, SampleQty);
	}

	/** Get Sample Qty.
		@return Sample Qty	  */
	public BigDecimal getSampleQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SampleQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Target Quantity.
		@param TargetQty 
		Target Movement Quantity
	  */
	public void setTargetQty (BigDecimal TargetQty)
	{
		set_Value (COLUMNNAME_TargetQty, TargetQty);
	}

	/** Get Target Quantity.
		@return Target Movement Quantity
	  */
	public BigDecimal getTargetQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TargetQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.qad.model.I_UNS_QAStatus_InOut getUNS_QAStatus_InOut() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAStatus_InOut)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAStatus_InOut.Table_Name)
			.getPO(getUNS_QAStatus_InOut_ID(), get_TrxName());	}

	/** Set Ship/Receipt Inspection.
		@param UNS_QAStatus_InOut_ID Ship/Receipt Inspection	  */
	public void setUNS_QAStatus_InOut_ID (int UNS_QAStatus_InOut_ID)
	{
		if (UNS_QAStatus_InOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_InOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_InOut_ID, Integer.valueOf(UNS_QAStatus_InOut_ID));
	}

	/** Get Ship/Receipt Inspection.
		@return Ship/Receipt Inspection	  */
	public int getUNS_QAStatus_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ship/Receipt Inspection Line.
		@param UNS_QAStatus_InOutLine_ID Ship/Receipt Inspection Line	  */
	public void setUNS_QAStatus_InOutLine_ID (int UNS_QAStatus_InOutLine_ID)
	{
		if (UNS_QAStatus_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_InOutLine_ID, Integer.valueOf(UNS_QAStatus_InOutLine_ID));
	}

	/** Get Ship/Receipt Inspection Line.
		@return Ship/Receipt Inspection Line	  */
	public int getUNS_QAStatus_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAStatus_InOutLine_UU.
		@param UNS_QAStatus_InOutLine_UU UNS_QAStatus_InOutLine_UU	  */
	public void setUNS_QAStatus_InOutLine_UU (String UNS_QAStatus_InOutLine_UU)
	{
		set_Value (COLUMNNAME_UNS_QAStatus_InOutLine_UU, UNS_QAStatus_InOutLine_UU);
	}

	/** Get UNS_QAStatus_InOutLine_UU.
		@return UNS_QAStatus_InOutLine_UU	  */
	public String getUNS_QAStatus_InOutLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAStatus_InOutLine_UU);
	}

	public com.uns.qad.model.I_UNS_QAStatus_Type getUNS_QAStatus_Type() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAStatus_Type)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAStatus_Type.Table_Name)
			.getPO(getUNS_QAStatus_Type_ID(), get_TrxName());	}

	/** Set Attribute Type.
		@param UNS_QAStatus_Type_ID Attribute Type	  */
	public void setUNS_QAStatus_Type_ID (int UNS_QAStatus_Type_ID)
	{
		if (UNS_QAStatus_Type_ID < 1) 
			set_Value (COLUMNNAME_UNS_QAStatus_Type_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_QAStatus_Type_ID, Integer.valueOf(UNS_QAStatus_Type_ID));
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