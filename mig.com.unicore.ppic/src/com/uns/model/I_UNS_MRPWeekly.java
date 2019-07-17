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

/** Generated Interface for UNS_MRPWeekly
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_MRPWeekly 
{

    /** TableName=UNS_MRPWeekly */
    public static final String Table_Name = "UNS_MRPWeekly";

    /** AD_Table_ID=1000337 */
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

    /** Column name AOR */
    public static final String COLUMNNAME_AOR = "AOR";

	/** Set AOR.
	  * Actual Order Receipt
	  */
	public void setAOR (BigDecimal AOR);

	/** Get AOR.
	  * Actual Order Receipt
	  */
	public BigDecimal getAOR();

    /** Column name AORE */
    public static final String COLUMNNAME_AORE = "AORE";

	/** Set AORE.
	  * Actual Order Release
	  */
	public void setAORE (BigDecimal AORE);

	/** Get AORE.
	  * Actual Order Release
	  */
	public BigDecimal getAORE();

    /** Column name AOREQ */
    public static final String COLUMNNAME_AOREQ = "AOREQ";

	/** Set AOREQ.
	  * Actual Order Requested (Issued Requisition)
	  */
	public void setAOREQ (BigDecimal AOREQ);

	/** Get AOREQ.
	  * Actual Order Requested (Issued Requisition)
	  */
	public BigDecimal getAOREQ();

    /** Column name ASOR */
    public static final String COLUMNNAME_ASOR = "ASOR";

	/** Set ASOR.
	  * (Actual Scheduled Order Receipt), is the actual schedule to receive material stated by date promised on the PO.
	  */
	public void setASOR (BigDecimal ASOR);

	/** Get ASOR.
	  * (Actual Scheduled Order Receipt), is the actual schedule to receive material stated by date promised on the PO.
	  */
	public BigDecimal getASOR();

    /** Column name ActualProduced */
    public static final String COLUMNNAME_ActualProduced = "ActualProduced";

	/** Set Actual Manufactured.
	  * Latest amount actually manufactured in UOM quantity
	  */
	public void setActualProduced (BigDecimal ActualProduced);

	/** Get Actual Manufactured.
	  * Latest amount actually manufactured in UOM quantity
	  */
	public BigDecimal getActualProduced();

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

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

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

    /** Column name IsUpdated */
    public static final String COLUMNNAME_IsUpdated = "IsUpdated";

	/** Set Has Updated.
	  * Has Updated
	  */
	public void setIsUpdated (boolean IsUpdated);

	/** Get Has Updated.
	  * Has Updated
	  */
	public boolean isUpdated();

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

    /** Column name POH */
    public static final String COLUMNNAME_POH = "POH";

	/** Set POH.
	  * Projected On Hand
	  */
	public void setPOH (BigDecimal POH);

	/** Get POH.
	  * Projected On Hand
	  */
	public BigDecimal getPOH();

    /** Column name POIssuePlanDate */
    public static final String COLUMNNAME_POIssuePlanDate = "POIssuePlanDate";

	/** Set PO Issue Plan Date	  */
	public void setPOIssuePlanDate (Timestamp POIssuePlanDate);

	/** Get PO Issue Plan Date	  */
	public Timestamp getPOIssuePlanDate();

    /** Column name POR */
    public static final String COLUMNNAME_POR = "POR";

	/** Set POR.
	  * Projected Order Receipts
	  */
	public void setPOR (BigDecimal POR);

	/** Get POR.
	  * Projected Order Receipts
	  */
	public BigDecimal getPOR();

    /** Column name PORE */
    public static final String COLUMNNAME_PORE = "PORE";

	/** Set PORE.
	  * Projected Order Release
	  */
	public void setPORE (BigDecimal PORE);

	/** Get PORE.
	  * Projected Order Release
	  */
	public BigDecimal getPORE();

    /** Column name PORef_ID */
    public static final String COLUMNNAME_PORef_ID = "PORef_ID";

	/** Set PO Reference	  */
	public void setPORef_ID (int PORef_ID);

	/** Get PO Reference	  */
	public int getPORef_ID();

	public org.compiere.model.I_C_OrderLine getPORef() throws RuntimeException;

    /** Column name PO_No */
    public static final String COLUMNNAME_PO_No = "PO_No";

	/** Set PO No	  */
	public void setPO_No (BigDecimal PO_No);

	/** Get PO No	  */
	public BigDecimal getPO_No();

    /** Column name ProjectedRequirement */
    public static final String COLUMNNAME_ProjectedRequirement = "ProjectedRequirement";

	/** Set Projected Requirement.
	  * Projected requirement of material based on forecasting (master) production schedule.
	  */
	public void setProjectedRequirement (BigDecimal ProjectedRequirement);

	/** Get Projected Requirement.
	  * Projected requirement of material based on forecasting (master) production schedule.
	  */
	public BigDecimal getProjectedRequirement();

    /** Column name QtyMiscUsed */
    public static final String COLUMNNAME_QtyMiscUsed = "QtyMiscUsed";

	/** Set Misc. Used Qty.
	  * Miscellaneous used qty (other used besides quantity to deliver)
	  */
	public void setQtyMiscUsed (BigDecimal QtyMiscUsed);

	/** Get Misc. Used Qty.
	  * Miscellaneous used qty (other used besides quantity to deliver)
	  */
	public BigDecimal getQtyMiscUsed();

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

    /** Column name RequisitionRef_ID */
    public static final String COLUMNNAME_RequisitionRef_ID = "RequisitionRef_ID";

	/** Set Requisition Reference	  */
	public void setRequisitionRef_ID (int RequisitionRef_ID);

	/** Get Requisition Reference	  */
	public int getRequisitionRef_ID();

	public org.compiere.model.I_M_RequisitionLine getRequisitionRef() throws RuntimeException;

    /** Column name SOH */
    public static final String COLUMNNAME_SOH = "SOH";

	/** Set SOH.
	  * Scheduled (Stock) On Hand based on Manufacturing Order (Schedule)
	  */
	public void setSOH (BigDecimal SOH);

	/** Get SOH.
	  * Scheduled (Stock) On Hand based on Manufacturing Order (Schedule)
	  */
	public BigDecimal getSOH();

    /** Column name SOR */
    public static final String COLUMNNAME_SOR = "SOR";

	/** Set SOR.
	  * Schedule Order Receipt
	  */
	public void setSOR (BigDecimal SOR);

	/** Get SOR.
	  * Schedule Order Receipt
	  */
	public BigDecimal getSOR();

    /** Column name ScheduledRequirement */
    public static final String COLUMNNAME_ScheduledRequirement = "ScheduledRequirement";

	/** Set Scheduled Requirement.
	  * Scheduled material requirement based on ordered/scheduled manufacturing process (defined in Manufacturing Order)
	  */
	public void setScheduledRequirement (BigDecimal ScheduledRequirement);

	/** Get Scheduled Requirement.
	  * Scheduled material requirement based on ordered/scheduled manufacturing process (defined in Manufacturing Order)
	  */
	public BigDecimal getScheduledRequirement();

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

    /** Column name UNS_MRPWeekly_ID */
    public static final String COLUMNNAME_UNS_MRPWeekly_ID = "UNS_MRPWeekly_ID";

	/** Set Material Requirement Weekly	  */
	public void setUNS_MRPWeekly_ID (int UNS_MRPWeekly_ID);

	/** Get Material Requirement Weekly	  */
	public int getUNS_MRPWeekly_ID();

    /** Column name UNS_MRPWeekly_UU */
    public static final String COLUMNNAME_UNS_MRPWeekly_UU = "UNS_MRPWeekly_UU";

	/** Set UNS_MRPWeekly_UU	  */
	public void setUNS_MRPWeekly_UU (String UNS_MRPWeekly_UU);

	/** Get UNS_MRPWeekly_UU	  */
	public String getUNS_MRPWeekly_UU();

    /** Column name UNS_MRP_ID */
    public static final String COLUMNNAME_UNS_MRP_ID = "UNS_MRP_ID";

	/** Set Material Requirement Production	  */
	public void setUNS_MRP_ID (int UNS_MRP_ID);

	/** Get Material Requirement Production	  */
	public int getUNS_MRP_ID();

	public com.uns.model.I_UNS_MRP getUNS_MRP() throws RuntimeException;

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

    /** Column name WeekNo */
    public static final String COLUMNNAME_WeekNo = "WeekNo";

	/** Set Week No	  */
	public void setWeekNo (int WeekNo);

	/** Get Week No	  */
	public int getWeekNo();
}
