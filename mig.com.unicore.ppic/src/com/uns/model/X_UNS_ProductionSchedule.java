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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_ProductionSchedule
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_ProductionSchedule extends PO implements I_UNS_ProductionSchedule, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131102L;

    /** Standard Constructor */
    public X_UNS_ProductionSchedule (Properties ctx, int UNS_ProductionSchedule_ID, String trxName)
    {
      super (ctx, UNS_ProductionSchedule_ID, trxName);
      /** if (UNS_ProductionSchedule_ID == 0)
        {
			setC_UOM_ID (0);
			setDocAction (null);
// CO
			setHasStickerInfo (false);
// N
			setIsComplete (null);
// N
			setM_Product_ID (0);
			setProcessed (false);
// N
			setProductionRemarks (null);
// -
			setPSType (null);
// MPS
			setQtyManufactured (Env.ZERO);
// 0
			setQtyOrdered (Env.ZERO);
// @QtyMPS@
			setStickerRemarks (null);
// -
			setUNS_Manufacturing_Order_ID (0);
			setUNS_ProductionSchedule_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_ProductionSchedule (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_ProductionSchedule[")
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

	/** Set Create Production.
		@param CreateProduction Create Production	  */
	public void setCreateProduction (String CreateProduction)
	{
		set_ValueNoCheck (COLUMNNAME_CreateProduction, CreateProduction);
	}

	/** Get Create Production.
		@return Create Production	  */
	public String getCreateProduction () 
	{
		return (String)get_Value(COLUMNNAME_CreateProduction);
	}

	/** Set Create SO Allocation.
		@param CreatePSSOAllocation Create SO Allocation	  */
	public void setCreatePSSOAllocation (String CreatePSSOAllocation)
	{
		set_Value (COLUMNNAME_CreatePSSOAllocation, CreatePSSOAllocation);
	}

	/** Get Create SO Allocation.
		@return Create SO Allocation	  */
	public String getCreatePSSOAllocation () 
	{
		return (String)get_Value(COLUMNNAME_CreatePSSOAllocation);
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

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
    }

	/** Set Has Sticker Info.
		@param HasStickerInfo Has Sticker Info	  */
	public void setHasStickerInfo (boolean HasStickerInfo)
	{
		set_Value (COLUMNNAME_HasStickerInfo, Boolean.valueOf(HasStickerInfo));
	}

	/** Get Has Sticker Info.
		@return Has Sticker Info	  */
	public boolean isHasStickerInfo () 
	{
		Object oo = get_Value(COLUMNNAME_HasStickerInfo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Complete.
		@param IsComplete 
		It is complete
	  */
	public void setIsComplete (String IsComplete)
	{
		set_Value (COLUMNNAME_IsComplete, IsComplete);
	}

	/** Get Complete.
		@return It is complete
	  */
	public String getIsComplete () 
	{
		return (String)get_Value(COLUMNNAME_IsComplete);
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

	/** Set Processed On.
		@param ProcessedOn 
		The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn)
	{
		set_Value (COLUMNNAME_ProcessedOn, ProcessedOn);
	}

	/** Get Processed On.
		@return The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProcessedOn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Production Remarks.
		@param ProductionRemarks Production Remarks	  */
	public void setProductionRemarks (String ProductionRemarks)
	{
		set_Value (COLUMNNAME_ProductionRemarks, ProductionRemarks);
	}

	/** Get Production Remarks.
		@return Production Remarks	  */
	public String getProductionRemarks () 
	{
		return (String)get_Value(COLUMNNAME_ProductionRemarks);
	}

	public org.compiere.model.I_M_Product getProductSticker() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getProductSticker_ID(), get_TrxName());	}

	/** Set Sticker.
		@param ProductSticker_ID Sticker	  */
	public void setProductSticker_ID (int ProductSticker_ID)
	{
		if (ProductSticker_ID < 1) 
			set_Value (COLUMNNAME_ProductSticker_ID, null);
		else 
			set_Value (COLUMNNAME_ProductSticker_ID, Integer.valueOf(ProductSticker_ID));
	}

	/** Get Sticker.
		@return Sticker	  */
	public int getProductSticker_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ProductSticker_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** PSType AD_Reference_ID=1000044 */
	public static final int PSTYPE_AD_Reference_ID=1000044;
	/** Master Production Schedule = MPS */
	public static final String PSTYPE_MasterProductionSchedule = "MPS";
	/** Restickering = RSI */
	public static final String PSTYPE_Restickering = "RSI";
	/** Rework = RWK */
	public static final String PSTYPE_Rework = "RWK";
	/** Rebundle = RBD */
	public static final String PSTYPE_Rebundle = "RBD";
	/** Reprocess = RPR */
	public static final String PSTYPE_Reprocess = "RPR";
	/** Set Production Type.
		@param PSType 
		The type of the production (i.e.: Reprocess, Rebundle, Rework, Re-stickering, MPS)
	  */
	public void setPSType (String PSType)
	{

		set_Value (COLUMNNAME_PSType, PSType);
	}

	/** Get Production Type.
		@return The type of the production (i.e.: Reprocess, Rebundle, Rework, Re-stickering, MPS)
	  */
	public String getPSType () 
	{
		return (String)get_Value(COLUMNNAME_PSType);
	}

	/** Set Qty Allocated.
		@param QtyAllocated Qty Allocated	  */
	public void setQtyAllocated (BigDecimal QtyAllocated)
	{
		set_Value (COLUMNNAME_QtyAllocated, QtyAllocated);
	}

	/** Get Qty Allocated.
		@return Qty Allocated	  */
	public BigDecimal getQtyAllocated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAllocated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Manufactured Quantity.
		@param QtyManufactured 
		Quantity in product's UoM that already manufactured for the requested/ordered product.
	  */
	public void setQtyManufactured (BigDecimal QtyManufactured)
	{
		set_ValueNoCheck (COLUMNNAME_QtyManufactured, QtyManufactured);
	}

	/** Get Manufactured Quantity.
		@return Quantity in product's UoM that already manufactured for the requested/ordered product.
	  */
	public BigDecimal getQtyManufactured () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyManufactured);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set MPS Quantity.
		@param QtyMPS MPS Quantity	  */
	public void setQtyMPS (BigDecimal QtyMPS)
	{
		set_Value (COLUMNNAME_QtyMPS, QtyMPS);
	}

	/** Get MPS Quantity.
		@return MPS Quantity	  */
	public BigDecimal getQtyMPS () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMPS);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecasted Qty (MT).
		@param QtyMT 
		Quantity of Raw Material Forecast in Metric Ton
	  */
	public void setQtyMT (BigDecimal QtyMT)
	{
		set_ValueNoCheck (COLUMNNAME_QtyMT, QtyMT);
	}

	/** Get Forecasted Qty (MT).
		@return Quantity of Raw Material Forecast in Metric Ton
	  */
	public BigDecimal getQtyMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty OnHand (MT).
		@param QtyMTOnHand Qty OnHand (MT)	  */
	public void setQtyMTOnHand (BigDecimal QtyMTOnHand)
	{
		set_ValueNoCheck (COLUMNNAME_QtyMTOnHand, QtyMTOnHand);
	}

	/** Get Qty OnHand (MT).
		@return Qty OnHand (MT)	  */
	public BigDecimal getQtyMTOnHand () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMTOnHand);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set On Hand Quantity.
		@param QtyOnHand 
		On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	/** Get On Hand Quantity.
		@return On Hand Quantity
	  */
	public BigDecimal getQtyOnHand () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHand);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ordered/Planned Qty.
		@param QtyOrdered 
		Ordered Quantity in Product's UoM
	  */
	public void setQtyOrdered (BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Ordered/Planned Qty.
		@return Ordered Quantity in Product's UoM
	  */
	public BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Forecasted Qty (UOM).
		@param QtyUom 
		Quantity of Raw Material Forecast in Metric Ton
	  */
	public void setQtyUom (BigDecimal QtyUom)
	{
		set_ValueNoCheck (COLUMNNAME_QtyUom, QtyUom);
	}

	/** Get Forecasted Qty (UOM).
		@return Quantity of Raw Material Forecast in Metric Ton
	  */
	public BigDecimal getQtyUom () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUom);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_Product getReplacementProduct() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getReplacementProduct_ID(), get_TrxName());	}

	/** Set Replacement Product.
		@param ReplacementProduct_ID Replacement Product	  */
	public void setReplacementProduct_ID (int ReplacementProduct_ID)
	{
		if (ReplacementProduct_ID < 1) 
			set_Value (COLUMNNAME_ReplacementProduct_ID, null);
		else 
			set_Value (COLUMNNAME_ReplacementProduct_ID, Integer.valueOf(ReplacementProduct_ID));
	}

	/** Get Replacement Product.
		@return Replacement Product	  */
	public int getReplacementProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ReplacementProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getSourceProduct() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getSourceProduct_ID(), get_TrxName());	}

	/** Set Source Product.
		@param SourceProduct_ID Source Product	  */
	public void setSourceProduct_ID (int SourceProduct_ID)
	{
		if (SourceProduct_ID < 1) 
			set_Value (COLUMNNAME_SourceProduct_ID, null);
		else 
			set_Value (COLUMNNAME_SourceProduct_ID, Integer.valueOf(SourceProduct_ID));
	}

	/** Get Source Product.
		@return Source Product	  */
	public int getSourceProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SourceProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sticker Info.
		@param StickerInfo Sticker Info	  */
	public void setStickerInfo (String StickerInfo)
	{
		set_Value (COLUMNNAME_StickerInfo, StickerInfo);
	}

	/** Get Sticker Info.
		@return Sticker Info	  */
	public String getStickerInfo () 
	{
		return (String)get_Value(COLUMNNAME_StickerInfo);
	}

	/** Set Sticker Remarks.
		@param StickerRemarks Sticker Remarks	  */
	public void setStickerRemarks (String StickerRemarks)
	{
		set_Value (COLUMNNAME_StickerRemarks, StickerRemarks);
	}

	/** Get Sticker Remarks.
		@return Sticker Remarks	  */
	public String getStickerRemarks () 
	{
		return (String)get_Value(COLUMNNAME_StickerRemarks);
	}

	/** Set Target Production Date End.
		@param Target_PD_End Target Production Date End	  */
	public void setTarget_PD_End (Timestamp Target_PD_End)
	{
		set_Value (COLUMNNAME_Target_PD_End, Target_PD_End);
	}

	/** Get Target Production Date End.
		@return Target Production Date End	  */
	public Timestamp getTarget_PD_End () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Target_PD_End);
	}

	/** Set Target Production Date Start.
		@param Target_PD_Start Target Production Date Start	  */
	public void setTarget_PD_Start (Timestamp Target_PD_Start)
	{
		set_Value (COLUMNNAME_Target_PD_Start, Target_PD_Start);
	}

	/** Get Target Production Date Start.
		@return Target Production Date Start	  */
	public Timestamp getTarget_PD_Start () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Target_PD_Start);
	}

	public com.uns.model.I_UNS_Manufacturing_Order getUNS_Manufacturing_Order() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Manufacturing_Order)MTable.get(getCtx(), com.uns.model.I_UNS_Manufacturing_Order.Table_Name)
			.getPO(getUNS_Manufacturing_Order_ID(), get_TrxName());	}

	/** Set Manufacturing Order.
		@param UNS_Manufacturing_Order_ID Manufacturing Order	  */
	public void setUNS_Manufacturing_Order_ID (int UNS_Manufacturing_Order_ID)
	{
		if (UNS_Manufacturing_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Manufacturing_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Manufacturing_Order_ID, Integer.valueOf(UNS_Manufacturing_Order_ID));
	}

	/** Get Manufacturing Order.
		@return Manufacturing Order	  */
	public int getUNS_Manufacturing_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Manufacturing_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_MPSProductRsc_Weekly getUNS_MPSProductRsc_Weekly() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MPSProductRsc_Weekly)MTable.get(getCtx(), com.uns.model.I_UNS_MPSProductRsc_Weekly.Table_Name)
			.getPO(getUNS_MPSProductRsc_Weekly_ID(), get_TrxName());	}

	/** Set MPS P-Rsc Weekly.
		@param UNS_MPSProductRsc_Weekly_ID 
		MPS Product Resource Weekly
	  */
	public void setUNS_MPSProductRsc_Weekly_ID (int UNS_MPSProductRsc_Weekly_ID)
	{
		if (UNS_MPSProductRsc_Weekly_ID < 1) 
			set_Value (COLUMNNAME_UNS_MPSProductRsc_Weekly_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_MPSProductRsc_Weekly_ID, Integer.valueOf(UNS_MPSProductRsc_Weekly_ID));
	}

	/** Get MPS P-Rsc Weekly.
		@return MPS Product Resource Weekly
	  */
	public int getUNS_MPSProductRsc_Weekly_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MPSProductRsc_Weekly_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production Schedule.
		@param UNS_ProductionSchedule_ID Production Schedule	  */
	public void setUNS_ProductionSchedule_ID (int UNS_ProductionSchedule_ID)
	{
		if (UNS_ProductionSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductionSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductionSchedule_ID, Integer.valueOf(UNS_ProductionSchedule_ID));
	}

	/** Get Production Schedule.
		@return Production Schedule	  */
	public int getUNS_ProductionSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ProductionSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_ProductionSchedule_UU.
		@param UNS_ProductionSchedule_UU UNS_ProductionSchedule_UU	  */
	public void setUNS_ProductionSchedule_UU (String UNS_ProductionSchedule_UU)
	{
		set_Value (COLUMNNAME_UNS_ProductionSchedule_UU, UNS_ProductionSchedule_UU);
	}

	/** Get UNS_ProductionSchedule_UU.
		@return UNS_ProductionSchedule_UU	  */
	public String getUNS_ProductionSchedule_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_ProductionSchedule_UU);
	}

	/** Set SER Line Product.
		@param UNS_SERLineProduct_ID SER Line Product	  */
	public void setUNS_SERLineProduct_ID (int UNS_SERLineProduct_ID)
	{
		if (UNS_SERLineProduct_ID < 1) 
			set_Value (COLUMNNAME_UNS_SERLineProduct_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_SERLineProduct_ID, Integer.valueOf(UNS_SERLineProduct_ID));
	}

	/** Get SER Line Product.
		@return SER Line Product	  */
	public int getUNS_SERLineProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SERLineProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}