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

/** Generated Model for UNS_QAStatus_PRCLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAStatus_PRCLine extends PO implements I_UNS_QAStatus_PRCLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140219L;
    /** Standard Constructor */
    public X_UNS_QAStatus_PRCLine (Properties ctx, int UNS_QAStatus_PRCLine_ID, String trxName)
    {
      super (ctx, UNS_QAStatus_PRCLine_ID, trxName);
      /** if (UNS_QAStatus_PRCLine_ID == 0)
        {
			setCodeNo (null);
			setM_AttributeSetInstance_ID (0);
			setM_Locator_ID (0);
			setM_Product_ID (0);
			setNC (null);
			setNCQty (Env.ZERO);
			setOnHold (null);
			setOnHoldQty (Env.ZERO);
			setQAStatus (null);
// PI
			setQty (Env.ZERO);
			setRelease (null);
			setReleaseQty (Env.ZERO);
			setShelfLifeMonth (0);
			setUNS_QAStatus_PRC_ID (0);
			setUNS_QAStatus_PRCLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAStatus_PRCLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAStatus_PRCLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Trx Department.
		@param AD_OrgTrx_ID 
		Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Department.
		@return Performing or initiating Department
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Package No.
		@param CodeNo 
		Bag No / Pallet No / Bottle
	  */
	public void setCodeNo (String CodeNo)
	{
		set_Value (COLUMNNAME_CodeNo, CodeNo);
	}

	/** Get Package No.
		@return Bag No / Pallet No / Bottle
	  */
	public String getCodeNo () 
	{
		return (String)get_Value(COLUMNNAME_CodeNo);
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

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
    {
		return (org.compiere.model.I_M_AttributeSetInstance)MTable.get(getCtx(), org.compiere.model.I_M_AttributeSetInstance.Table_Name)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
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

	public org.compiere.model.I_M_Transaction getM_Transaction() throws RuntimeException
    {
		return (org.compiere.model.I_M_Transaction)MTable.get(getCtx(), org.compiere.model.I_M_Transaction.Table_Name)
			.getPO(getM_Transaction_ID(), get_TrxName());	}

	/** Set Inventory Transaction.
		@param M_Transaction_ID Inventory Transaction	  */
	public void setM_Transaction_ID (int M_Transaction_ID)
	{
		if (M_Transaction_ID < 1) 
			set_Value (COLUMNNAME_M_Transaction_ID, null);
		else 
			set_Value (COLUMNNAME_M_Transaction_ID, Integer.valueOf(M_Transaction_ID));
	}

	/** Get Inventory Transaction.
		@return Inventory Transaction	  */
	public int getM_Transaction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Transaction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Non Conformance.
		@param NC Non Conformance	  */
	public void setNC (String NC)
	{
		set_Value (COLUMNNAME_NC, NC);
	}

	/** Get Non Conformance.
		@return Non Conformance	  */
	public String getNC () 
	{
		return (String)get_Value(COLUMNNAME_NC);
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

	/** Set Hold.
		@param OnHold Hold	  */
	public void setOnHold (String OnHold)
	{
		set_Value (COLUMNNAME_OnHold, OnHold);
	}

	/** Get Hold.
		@return Hold	  */
	public String getOnHold () 
	{
		return (String)get_Value(COLUMNNAME_OnHold);
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
	/** Pending Inspection/Lab Test = PL */
	public static final String QASTATUS_PendingInspectionLabTest = "PL";
	/** Release = RE */
	public static final String QASTATUS_Release = "RE";
	/** Premature Released = PR */
	public static final String QASTATUS_PrematureReleased = "PR";
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

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Release.
		@param Release Release	  */
	public void setRelease (String Release)
	{
		set_Value (COLUMNNAME_Release, Release);
	}

	/** Get Release.
		@return Release	  */
	public String getRelease () 
	{
		return (String)get_Value(COLUMNNAME_Release);
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

	/** Set Shelf Life Month.
		@param ShelfLifeMonth 
		Shelf Life in Month
	  */
	public void setShelfLifeMonth (int ShelfLifeMonth)
	{
		set_Value (COLUMNNAME_ShelfLifeMonth, Integer.valueOf(ShelfLifeMonth));
	}

	/** Get Shelf Life Month.
		@return Shelf Life in Month
	  */
	public int getShelfLifeMonth () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfLifeMonth);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production Detail.
		@param UNS_Production_Detail_ID Production Detail	  */
	public void setUNS_Production_Detail_ID (int UNS_Production_Detail_ID)
	{
		if (UNS_Production_Detail_ID < 1) 
			set_Value (COLUMNNAME_UNS_Production_Detail_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Production_Detail_ID, Integer.valueOf(UNS_Production_Detail_ID));
	}

	/** Get Production Detail.
		@return Production Detail	  */
	public int getUNS_Production_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.qad.model.I_UNS_QAStatus_PRC getUNS_QAStatus_PRC() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAStatus_PRC)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAStatus_PRC.Table_Name)
			.getPO(getUNS_QAStatus_PRC_ID(), get_TrxName());	}

	/** Set Production Inspection.
		@param UNS_QAStatus_PRC_ID Production Inspection	  */
	public void setUNS_QAStatus_PRC_ID (int UNS_QAStatus_PRC_ID)
	{
		if (UNS_QAStatus_PRC_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_PRC_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_PRC_ID, Integer.valueOf(UNS_QAStatus_PRC_ID));
	}

	/** Get Production Inspection.
		@return Production Inspection	  */
	public int getUNS_QAStatus_PRC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_PRC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production Inspection Line.
		@param UNS_QAStatus_PRCLine_ID Production Inspection Line	  */
	public void setUNS_QAStatus_PRCLine_ID (int UNS_QAStatus_PRCLine_ID)
	{
		if (UNS_QAStatus_PRCLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_PRCLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_PRCLine_ID, Integer.valueOf(UNS_QAStatus_PRCLine_ID));
	}

	/** Get Production Inspection Line.
		@return Production Inspection Line	  */
	public int getUNS_QAStatus_PRCLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_PRCLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAStatus_PRCLine_UU.
		@param UNS_QAStatus_PRCLine_UU UNS_QAStatus_PRCLine_UU	  */
	public void setUNS_QAStatus_PRCLine_UU (String UNS_QAStatus_PRCLine_UU)
	{
		set_Value (COLUMNNAME_UNS_QAStatus_PRCLine_UU, UNS_QAStatus_PRCLine_UU);
	}

	/** Get UNS_QAStatus_PRCLine_UU.
		@return UNS_QAStatus_PRCLine_UU	  */
	public String getUNS_QAStatus_PRCLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAStatus_PRCLine_UU);
	}
}