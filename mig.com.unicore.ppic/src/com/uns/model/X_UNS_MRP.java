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

/** Generated Model for UNS_MRP
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MRP extends PO implements I_UNS_MRP, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131005L;

    /** Standard Constructor */
    public X_UNS_MRP (Properties ctx, int UNS_MRP_ID, String trxName)
    {
      super (ctx, UNS_MRP_ID, trxName);
      /** if (UNS_MRP_ID == 0)
        {
			setC_UOM_ID (0);
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setEndWeekNo (0);
			setGrandTotalMt (Env.ZERO);
			setGrandTotalUOM (Env.ZERO);
			setIsApproved (false);
// N
			setM_Product_ID (0);
			setNeedToUpdate (false);
// N
			setProcessed (false);
// N
			setStartWeekNo (0);
			setTotalGrossManufacturMT (Env.ZERO);
// 0
			setTotalGrossManufacturUOM (Env.ZERO);
// 0
			setUNS_MRP_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MRP (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MRP[")
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

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Confirmed = CF */
	public static final String DOCACTION_Confirmed = "CF";
	/** Finished = FN */
	public static final String DOCACTION_Finished = "FN";
	/** Cancelled = CN */
	public static final String DOCACTION_Cancelled = "CN";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set End Week No.
		@param EndWeekNo 
		End Week No
	  */
	public void setEndWeekNo (int EndWeekNo)
	{
		set_ValueNoCheck (COLUMNNAME_EndWeekNo, Integer.valueOf(EndWeekNo));
	}

	/** Get End Week No.
		@return End Week No
	  */
	public int getEndWeekNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EndWeekNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Expected Storage OnHand.
		@param ExpectedStorageOnHand Expected Storage OnHand	  */
	public void setExpectedStorageOnHand (BigDecimal ExpectedStorageOnHand)
	{
		set_Value (COLUMNNAME_ExpectedStorageOnHand, ExpectedStorageOnHand);
	}

	/** Get Expected Storage OnHand.
		@return Expected Storage OnHand	  */
	public BigDecimal getExpectedStorageOnHand () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ExpectedStorageOnHand);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Grand Total (MT).
		@param GrandTotalMt Grand Total (MT)	  */
	public void setGrandTotalMt (BigDecimal GrandTotalMt)
	{
		set_Value (COLUMNNAME_GrandTotalMt, GrandTotalMt);
	}

	/** Get Grand Total (MT).
		@return Grand Total (MT)	  */
	public BigDecimal getGrandTotalMt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotalMt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Grand Total (UOM).
		@param GrandTotalUOM Grand Total (UOM)	  */
	public void setGrandTotalUOM (BigDecimal GrandTotalUOM)
	{
		set_Value (COLUMNNAME_GrandTotalUOM, GrandTotalUOM);
	}

	/** Get Grand Total (UOM).
		@return Grand Total (UOM)	  */
	public BigDecimal getGrandTotalUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotalUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lead Time.
		@param LeadTime Lead Time	  */
	public void setLeadTime (int LeadTime)
	{
		set_Value (COLUMNNAME_LeadTime, Integer.valueOf(LeadTime));
	}

	/** Get Lead Time.
		@return Lead Time	  */
	public int getLeadTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LeadTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MOQ.
		@param MOQ MOQ	  */
	public void setMOQ (BigDecimal MOQ)
	{
		set_Value (COLUMNNAME_MOQ, MOQ);
	}

	/** Get MOQ.
		@return MOQ	  */
	public BigDecimal getMOQ () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MOQ);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set MRP Parent.
		@param MRPParent_ID MRP Parent	  */
	public void setMRPParent_ID (int MRPParent_ID)
	{
		if (MRPParent_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MRPParent_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MRPParent_ID, Integer.valueOf(MRPParent_ID));
	}

	/** Get MRP Parent.
		@return MRP Parent	  */
	public int getMRPParent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MRPParent_ID);
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

	/** Set Need To Update.
		@param NeedToUpdate 
		Need To Update
	  */
	public void setNeedToUpdate (boolean NeedToUpdate)
	{
		set_ValueNoCheck (COLUMNNAME_NeedToUpdate, Boolean.valueOf(NeedToUpdate));
	}

	/** Get Need To Update.
		@return Need To Update
	  */
	public boolean isNeedToUpdate () 
	{
		Object oo = get_Value(COLUMNNAME_NeedToUpdate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Safety Stock Qty.
		@param SafetyStock 
		Safety stock is a term used to describe a level of stock that is maintained below the cycle stock to buffer against stock-outs
	  */
	public void setSafetyStock (BigDecimal SafetyStock)
	{
		set_Value (COLUMNNAME_SafetyStock, SafetyStock);
	}

	/** Get Safety Stock Qty.
		@return Safety stock is a term used to describe a level of stock that is maintained below the cycle stock to buffer against stock-outs
	  */
	public BigDecimal getSafetyStock () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SafetyStock);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Start Week No.
		@param StartWeekNo 
		Start Week No
	  */
	public void setStartWeekNo (int StartWeekNo)
	{
		set_ValueNoCheck (COLUMNNAME_StartWeekNo, Integer.valueOf(StartWeekNo));
	}

	/** Get Start Week No.
		@return Start Week No
	  */
	public int getStartWeekNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_StartWeekNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Total Gross Manufactur (MT).
		@param TotalGrossManufacturMT Total Gross Manufactur (MT)	  */
	public void setTotalGrossManufacturMT (BigDecimal TotalGrossManufacturMT)
	{
		set_Value (COLUMNNAME_TotalGrossManufacturMT, TotalGrossManufacturMT);
	}

	/** Get Total Gross Manufactur (MT).
		@return Total Gross Manufactur (MT)	  */
	public BigDecimal getTotalGrossManufacturMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalGrossManufacturMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Gross Manufactur (UOM).
		@param TotalGrossManufacturUOM 
		Total Gross Manufacturing Matric Ton
	  */
	public void setTotalGrossManufacturUOM (BigDecimal TotalGrossManufacturUOM)
	{
		set_Value (COLUMNNAME_TotalGrossManufacturUOM, TotalGrossManufacturUOM);
	}

	/** Get Total Gross Manufactur (UOM).
		@return Total Gross Manufacturing Matric Ton
	  */
	public BigDecimal getTotalGrossManufacturUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalGrossManufacturUOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_MPSHeader getUNS_MPSHeader() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MPSHeader)MTable.get(getCtx(), com.uns.model.I_UNS_MPSHeader.Table_Name)
			.getPO(getUNS_MPSHeader_ID(), get_TrxName());	}

	/** Set Master Production Schedule.
		@param UNS_MPSHeader_ID Master Production Schedule	  */
	public void setUNS_MPSHeader_ID (int UNS_MPSHeader_ID)
	{
		if (UNS_MPSHeader_ID < 1) 
			set_Value (COLUMNNAME_UNS_MPSHeader_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_MPSHeader_ID, Integer.valueOf(UNS_MPSHeader_ID));
	}

	/** Get Master Production Schedule.
		@return Master Production Schedule	  */
	public int getUNS_MPSHeader_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MPSHeader_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_MPSProduct getUNS_MPSProduct() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MPSProduct)MTable.get(getCtx(), com.uns.model.I_UNS_MPSProduct.Table_Name)
			.getPO(getUNS_MPSProduct_ID(), get_TrxName());	}

	/** Set MPS Product.
		@param UNS_MPSProduct_ID MPS Product	  */
	public void setUNS_MPSProduct_ID (int UNS_MPSProduct_ID)
	{
		if (UNS_MPSProduct_ID < 1) 
			set_Value (COLUMNNAME_UNS_MPSProduct_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_MPSProduct_ID, Integer.valueOf(UNS_MPSProduct_ID));
	}

	/** Get MPS Product.
		@return MPS Product	  */
	public int getUNS_MPSProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MPSProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Material Requirement Production.
		@param UNS_MRP_ID Material Requirement Production	  */
	public void setUNS_MRP_ID (int UNS_MRP_ID)
	{
		if (UNS_MRP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MRP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MRP_ID, Integer.valueOf(UNS_MRP_ID));
	}

	/** Get Material Requirement Production.
		@return Material Requirement Production	  */
	public int getUNS_MRP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MRP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Material Requirement Production UU.
		@param UNS_MRP_UU Material Requirement Production UU	  */
	public void setUNS_MRP_UU (String UNS_MRP_UU)
	{
		set_Value (COLUMNNAME_UNS_MRP_UU, UNS_MRP_UU);
	}

	/** Get Material Requirement Production UU.
		@return Material Requirement Production UU	  */
	public String getUNS_MRP_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MRP_UU);
	}
}