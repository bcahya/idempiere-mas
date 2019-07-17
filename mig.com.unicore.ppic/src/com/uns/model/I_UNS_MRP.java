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

/** Generated Interface for UNS_MRP
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_MRP 
{

    /** TableName=UNS_MRP */
    public static final String Table_Name = "UNS_MRP";

    /** AD_Table_ID=1000315 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
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

    /** Column name EndWeekNo */
    public static final String COLUMNNAME_EndWeekNo = "EndWeekNo";

	/** Set End Week No.
	  * End Week No
	  */
	public void setEndWeekNo (int EndWeekNo);

	/** Get End Week No.
	  * End Week No
	  */
	public int getEndWeekNo();

    /** Column name ExpectedStorageOnHand */
    public static final String COLUMNNAME_ExpectedStorageOnHand = "ExpectedStorageOnHand";

	/** Set Expected Storage OnHand	  */
	public void setExpectedStorageOnHand (BigDecimal ExpectedStorageOnHand);

	/** Get Expected Storage OnHand	  */
	public BigDecimal getExpectedStorageOnHand();

    /** Column name GrandTotalMt */
    public static final String COLUMNNAME_GrandTotalMt = "GrandTotalMt";

	/** Set Grand Total (MT)	  */
	public void setGrandTotalMt (BigDecimal GrandTotalMt);

	/** Get Grand Total (MT)	  */
	public BigDecimal getGrandTotalMt();

    /** Column name GrandTotalUOM */
    public static final String COLUMNNAME_GrandTotalUOM = "GrandTotalUOM";

	/** Set Grand Total (UOM)	  */
	public void setGrandTotalUOM (BigDecimal GrandTotalUOM);

	/** Get Grand Total (UOM)	  */
	public BigDecimal getGrandTotalUOM();

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

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name LeadTime */
    public static final String COLUMNNAME_LeadTime = "LeadTime";

	/** Set Lead Time	  */
	public void setLeadTime (int LeadTime);

	/** Get Lead Time	  */
	public int getLeadTime();

    /** Column name MOQ */
    public static final String COLUMNNAME_MOQ = "MOQ";

	/** Set MOQ	  */
	public void setMOQ (BigDecimal MOQ);

	/** Get MOQ	  */
	public BigDecimal getMOQ();

    /** Column name MRPParent_ID */
    public static final String COLUMNNAME_MRPParent_ID = "MRPParent_ID";

	/** Set MRP Parent	  */
	public void setMRPParent_ID (int MRPParent_ID);

	/** Get MRP Parent	  */
	public int getMRPParent_ID();

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

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name NeedToUpdate */
    public static final String COLUMNNAME_NeedToUpdate = "NeedToUpdate";

	/** Set Need To Update.
	  * Need To Update
	  */
	public void setNeedToUpdate (boolean NeedToUpdate);

	/** Get Need To Update.
	  * Need To Update
	  */
	public boolean isNeedToUpdate();

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

    /** Column name SafetyStock */
    public static final String COLUMNNAME_SafetyStock = "SafetyStock";

	/** Set Safety Stock Qty.
	  * Safety stock is a term used to describe a level of stock that is maintained below the cycle stock to buffer against stock-outs
	  */
	public void setSafetyStock (BigDecimal SafetyStock);

	/** Get Safety Stock Qty.
	  * Safety stock is a term used to describe a level of stock that is maintained below the cycle stock to buffer against stock-outs
	  */
	public BigDecimal getSafetyStock();

    /** Column name StartWeekNo */
    public static final String COLUMNNAME_StartWeekNo = "StartWeekNo";

	/** Set Start Week No.
	  * Start Week No
	  */
	public void setStartWeekNo (int StartWeekNo);

	/** Get Start Week No.
	  * Start Week No
	  */
	public int getStartWeekNo();

    /** Column name TotalGrossManufacturMT */
    public static final String COLUMNNAME_TotalGrossManufacturMT = "TotalGrossManufacturMT";

	/** Set Total Gross Manufactur (MT)	  */
	public void setTotalGrossManufacturMT (BigDecimal TotalGrossManufacturMT);

	/** Get Total Gross Manufactur (MT)	  */
	public BigDecimal getTotalGrossManufacturMT();

    /** Column name TotalGrossManufacturUOM */
    public static final String COLUMNNAME_TotalGrossManufacturUOM = "TotalGrossManufacturUOM";

	/** Set Total Gross Manufactur (UOM).
	  * Total Gross Manufacturing Matric Ton
	  */
	public void setTotalGrossManufacturUOM (BigDecimal TotalGrossManufacturUOM);

	/** Get Total Gross Manufactur (UOM).
	  * Total Gross Manufacturing Matric Ton
	  */
	public BigDecimal getTotalGrossManufacturUOM();

    /** Column name UNS_MPSHeader_ID */
    public static final String COLUMNNAME_UNS_MPSHeader_ID = "UNS_MPSHeader_ID";

	/** Set Master Production Schedule	  */
	public void setUNS_MPSHeader_ID (int UNS_MPSHeader_ID);

	/** Get Master Production Schedule	  */
	public int getUNS_MPSHeader_ID();

	public com.uns.model.I_UNS_MPSHeader getUNS_MPSHeader() throws RuntimeException;

    /** Column name UNS_MPSProduct_ID */
    public static final String COLUMNNAME_UNS_MPSProduct_ID = "UNS_MPSProduct_ID";

	/** Set MPS Product	  */
	public void setUNS_MPSProduct_ID (int UNS_MPSProduct_ID);

	/** Get MPS Product	  */
	public int getUNS_MPSProduct_ID();

	public com.uns.model.I_UNS_MPSProduct getUNS_MPSProduct() throws RuntimeException;

    /** Column name UNS_MRP_ID */
    public static final String COLUMNNAME_UNS_MRP_ID = "UNS_MRP_ID";

	/** Set Material Requirement Production	  */
	public void setUNS_MRP_ID (int UNS_MRP_ID);

	/** Get Material Requirement Production	  */
	public int getUNS_MRP_ID();

    /** Column name UNS_MRP_UU */
    public static final String COLUMNNAME_UNS_MRP_UU = "UNS_MRP_UU";

	/** Set Material Requirement Production UU	  */
	public void setUNS_MRP_UU (String UNS_MRP_UU);

	/** Get Material Requirement Production UU	  */
	public String getUNS_MRP_UU();

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
