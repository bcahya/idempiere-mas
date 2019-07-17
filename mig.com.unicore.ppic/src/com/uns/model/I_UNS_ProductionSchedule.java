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
package com.uns.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_ProductionSchedule
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_ProductionSchedule 
{

    /** TableName=UNS_ProductionSchedule */
    public static final String Table_Name = "UNS_ProductionSchedule";

    /** AD_Table_ID=1000094 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Company.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name CreateProduction */
    public static final String COLUMNNAME_CreateProduction = "CreateProduction";

	/** Set Create Production	  */
	public void setCreateProduction (String CreateProduction);

	/** Get Create Production	  */
	public String getCreateProduction();

    /** Column name CreatePSSOAllocation */
    public static final String COLUMNNAME_CreatePSSOAllocation = "CreatePSSOAllocation";

	/** Set Create SO Allocation	  */
	public void setCreatePSSOAllocation (String CreatePSSOAllocation);

	/** Get Create SO Allocation	  */
	public String getCreatePSSOAllocation();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name HasStickerInfo */
    public static final String COLUMNNAME_HasStickerInfo = "HasStickerInfo";

	/** Set Has Sticker Info	  */
	public void setHasStickerInfo (boolean HasStickerInfo);

	/** Get Has Sticker Info	  */
	public boolean isHasStickerInfo();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsComplete */
    public static final String COLUMNNAME_IsComplete = "IsComplete";

	/** Set Complete.
	  * It is complete
	  */
	public void setIsComplete (String IsComplete);

	/** Get Complete.
	  * It is complete
	  */
	public String getIsComplete();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name ProcessedOn */
    public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";

	/** Set Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn);

	/** Get Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn();

    /** Column name ProductionRemarks */
    public static final String COLUMNNAME_ProductionRemarks = "ProductionRemarks";

	/** Set Production Remarks	  */
	public void setProductionRemarks (String ProductionRemarks);

	/** Get Production Remarks	  */
	public String getProductionRemarks();

    /** Column name ProductSticker_ID */
    public static final String COLUMNNAME_ProductSticker_ID = "ProductSticker_ID";

	/** Set Sticker	  */
	public void setProductSticker_ID (int ProductSticker_ID);

	/** Get Sticker	  */
	public int getProductSticker_ID();

	public org.compiere.model.I_M_Product getProductSticker() throws RuntimeException;

    /** Column name PSType */
    public static final String COLUMNNAME_PSType = "PSType";

	/** Set Production Type.
	  * The type of the production (i.e.: Reprocess, Rebundle, Rework, Re-stickering, MPS)
	  */
	public void setPSType (String PSType);

	/** Get Production Type.
	  * The type of the production (i.e.: Reprocess, Rebundle, Rework, Re-stickering, MPS)
	  */
	public String getPSType();

    /** Column name QtyAllocated */
    public static final String COLUMNNAME_QtyAllocated = "QtyAllocated";

	/** Set Qty Allocated	  */
	public void setQtyAllocated (BigDecimal QtyAllocated);

	/** Get Qty Allocated	  */
	public BigDecimal getQtyAllocated();

    /** Column name QtyManufactured */
    public static final String COLUMNNAME_QtyManufactured = "QtyManufactured";

	/** Set Manufactured Quantity.
	  * Quantity in product's UoM that already manufactured for the requested/ordered product.
	  */
	public void setQtyManufactured (BigDecimal QtyManufactured);

	/** Get Manufactured Quantity.
	  * Quantity in product's UoM that already manufactured for the requested/ordered product.
	  */
	public BigDecimal getQtyManufactured();

    /** Column name QtyMPS */
    public static final String COLUMNNAME_QtyMPS = "QtyMPS";

	/** Set MPS Quantity	  */
	public void setQtyMPS (BigDecimal QtyMPS);

	/** Get MPS Quantity	  */
	public BigDecimal getQtyMPS();

    /** Column name QtyMT */
    public static final String COLUMNNAME_QtyMT = "QtyMT";

	/** Set Forecasted Qty (MT).
	  * Quantity of Raw Material Forecast in Metric Ton
	  */
	public void setQtyMT (BigDecimal QtyMT);

	/** Get Forecasted Qty (MT).
	  * Quantity of Raw Material Forecast in Metric Ton
	  */
	public BigDecimal getQtyMT();

    /** Column name QtyMTOnHand */
    public static final String COLUMNNAME_QtyMTOnHand = "QtyMTOnHand";

	/** Set Qty OnHand (MT)	  */
	public void setQtyMTOnHand (BigDecimal QtyMTOnHand);

	/** Get Qty OnHand (MT)	  */
	public BigDecimal getQtyMTOnHand();

    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/** Set On Hand Quantity.
	  * On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand);

	/** Get On Hand Quantity.
	  * On Hand Quantity
	  */
	public BigDecimal getQtyOnHand();

    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/** Set Ordered/Planned Qty.
	  * Ordered Quantity in Product's UoM
	  */
	public void setQtyOrdered (BigDecimal QtyOrdered);

	/** Get Ordered/Planned Qty.
	  * Ordered Quantity in Product's UoM
	  */
	public BigDecimal getQtyOrdered();

    /** Column name QtyUom */
    public static final String COLUMNNAME_QtyUom = "QtyUom";

	/** Set Forecasted Qty (UOM).
	  * Quantity of Raw Material Forecast in Metric Ton
	  */
	public void setQtyUom (BigDecimal QtyUom);

	/** Get Forecasted Qty (UOM).
	  * Quantity of Raw Material Forecast in Metric Ton
	  */
	public BigDecimal getQtyUom();

    /** Column name ReplacementProduct_ID */
    public static final String COLUMNNAME_ReplacementProduct_ID = "ReplacementProduct_ID";

	/** Set Replacement Product	  */
	public void setReplacementProduct_ID (int ReplacementProduct_ID);

	/** Get Replacement Product	  */
	public int getReplacementProduct_ID();

	public org.compiere.model.I_M_Product getReplacementProduct() throws RuntimeException;

    /** Column name SourceProduct_ID */
    public static final String COLUMNNAME_SourceProduct_ID = "SourceProduct_ID";

	/** Set Source Product	  */
	public void setSourceProduct_ID (int SourceProduct_ID);

	/** Get Source Product	  */
	public int getSourceProduct_ID();

	public org.compiere.model.I_M_Product getSourceProduct() throws RuntimeException;

    /** Column name StickerInfo */
    public static final String COLUMNNAME_StickerInfo = "StickerInfo";

	/** Set Sticker Info	  */
	public void setStickerInfo (String StickerInfo);

	/** Get Sticker Info	  */
	public String getStickerInfo();

    /** Column name StickerRemarks */
    public static final String COLUMNNAME_StickerRemarks = "StickerRemarks";

	/** Set Sticker Remarks	  */
	public void setStickerRemarks (String StickerRemarks);

	/** Get Sticker Remarks	  */
	public String getStickerRemarks();

    /** Column name Target_PD_End */
    public static final String COLUMNNAME_Target_PD_End = "Target_PD_End";

	/** Set Target Production Date End	  */
	public void setTarget_PD_End (Timestamp Target_PD_End);

	/** Get Target Production Date End	  */
	public Timestamp getTarget_PD_End();

    /** Column name Target_PD_Start */
    public static final String COLUMNNAME_Target_PD_Start = "Target_PD_Start";

	/** Set Target Production Date Start	  */
	public void setTarget_PD_Start (Timestamp Target_PD_Start);

	/** Get Target Production Date Start	  */
	public Timestamp getTarget_PD_Start();

    /** Column name UNS_Manufacturing_Order_ID */
    public static final String COLUMNNAME_UNS_Manufacturing_Order_ID = "UNS_Manufacturing_Order_ID";

	/** Set Manufacturing Order	  */
	public void setUNS_Manufacturing_Order_ID (int UNS_Manufacturing_Order_ID);

	/** Get Manufacturing Order	  */
	public int getUNS_Manufacturing_Order_ID();

	public com.uns.model.I_UNS_Manufacturing_Order getUNS_Manufacturing_Order() throws RuntimeException;

    /** Column name UNS_MPSProductRsc_Weekly_ID */
    public static final String COLUMNNAME_UNS_MPSProductRsc_Weekly_ID = "UNS_MPSProductRsc_Weekly_ID";

	/** Set MPS P-Rsc Weekly.
	  * MPS Product Resource Weekly
	  */
	public void setUNS_MPSProductRsc_Weekly_ID (int UNS_MPSProductRsc_Weekly_ID);

	/** Get MPS P-Rsc Weekly.
	  * MPS Product Resource Weekly
	  */
	public int getUNS_MPSProductRsc_Weekly_ID();

	public com.uns.model.I_UNS_MPSProductRsc_Weekly getUNS_MPSProductRsc_Weekly() throws RuntimeException;

    /** Column name UNS_ProductionSchedule_ID */
    public static final String COLUMNNAME_UNS_ProductionSchedule_ID = "UNS_ProductionSchedule_ID";

	/** Set Production Schedule	  */
	public void setUNS_ProductionSchedule_ID (int UNS_ProductionSchedule_ID);

	/** Get Production Schedule	  */
	public int getUNS_ProductionSchedule_ID();

    /** Column name UNS_ProductionSchedule_UU */
    public static final String COLUMNNAME_UNS_ProductionSchedule_UU = "UNS_ProductionSchedule_UU";

	/** Set UNS_ProductionSchedule_UU	  */
	public void setUNS_ProductionSchedule_UU (String UNS_ProductionSchedule_UU);

	/** Get UNS_ProductionSchedule_UU	  */
	public String getUNS_ProductionSchedule_UU();

    /** Column name UNS_SERLineProduct_ID */
    public static final String COLUMNNAME_UNS_SERLineProduct_ID = "UNS_SERLineProduct_ID";

	/** Set SER Line Product	  */
	public void setUNS_SERLineProduct_ID (int UNS_SERLineProduct_ID);

	/** Get SER Line Product	  */
	public int getUNS_SERLineProduct_ID();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
