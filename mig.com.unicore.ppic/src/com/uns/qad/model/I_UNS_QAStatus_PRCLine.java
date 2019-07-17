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
package com.uns.qad.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_QAStatus_PRCLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_QAStatus_PRCLine 
{

    /** TableName=UNS_QAStatus_PRCLine */
    public static final String Table_Name = "UNS_QAStatus_PRCLine";

    /** AD_Table_ID=1000271 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

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

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/** Set Trx Department.
	  * Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/** Get Trx Department.
	  * Performing or initiating Department
	  */
	public int getAD_OrgTrx_ID();

    /** Column name CodeNo */
    public static final String COLUMNNAME_CodeNo = "CodeNo";

	/** Set Package No.
	  * Bag No / Pallet No / Bottle
	  */
	public void setCodeNo (String CodeNo);

	/** Get Package No.
	  * Bag No / Pallet No / Bottle
	  */
	public String getCodeNo();

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

    /** Column name GenerateList */
    public static final String COLUMNNAME_GenerateList = "GenerateList";

	/** Set Generate List.
	  * Generate List
	  */
	public void setGenerateList (String GenerateList);

	/** Get Generate List.
	  * Generate List
	  */
	public String getGenerateList();

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

    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/** Set Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/** Get Attribute Set Instance.
	  * Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Locator.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Locator.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException;

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

    /** Column name M_Transaction_ID */
    public static final String COLUMNNAME_M_Transaction_ID = "M_Transaction_ID";

	/** Set Inventory Transaction	  */
	public void setM_Transaction_ID (int M_Transaction_ID);

	/** Get Inventory Transaction	  */
	public int getM_Transaction_ID();

	public org.compiere.model.I_M_Transaction getM_Transaction() throws RuntimeException;

    /** Column name NC */
    public static final String COLUMNNAME_NC = "NC";

	/** Set Non Conformance	  */
	public void setNC (String NC);

	/** Get Non Conformance	  */
	public String getNC();

    /** Column name NCQty */
    public static final String COLUMNNAME_NCQty = "NCQty";

	/** Set Quantity NC	  */
	public void setNCQty (BigDecimal NCQty);

	/** Get Quantity NC	  */
	public BigDecimal getNCQty();

    /** Column name OnHold */
    public static final String COLUMNNAME_OnHold = "OnHold";

	/** Set Hold	  */
	public void setOnHold (String OnHold);

	/** Get Hold	  */
	public String getOnHold();

    /** Column name OnHoldQty */
    public static final String COLUMNNAME_OnHoldQty = "OnHoldQty";

	/** Set Quantity OnHold	  */
	public void setOnHoldQty (BigDecimal OnHoldQty);

	/** Get Quantity OnHold	  */
	public BigDecimal getOnHoldQty();

    /** Column name QAStatus */
    public static final String COLUMNNAME_QAStatus = "QAStatus";

	/** Set QA Status	  */
	public void setQAStatus (String QAStatus);

	/** Get QA Status	  */
	public String getQAStatus();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

    /** Column name Release */
    public static final String COLUMNNAME_Release = "Release";

	/** Set Release	  */
	public void setRelease (String Release);

	/** Get Release	  */
	public String getRelease();

    /** Column name ReleaseQty */
    public static final String COLUMNNAME_ReleaseQty = "ReleaseQty";

	/** Set Quantity Release	  */
	public void setReleaseQty (BigDecimal ReleaseQty);

	/** Get Quantity Release	  */
	public BigDecimal getReleaseQty();

    /** Column name Remark */
    public static final String COLUMNNAME_Remark = "Remark";

	/** Set Remark	  */
	public void setRemark (String Remark);

	/** Get Remark	  */
	public String getRemark();

    /** Column name ShelfLifeMonth */
    public static final String COLUMNNAME_ShelfLifeMonth = "ShelfLifeMonth";

	/** Set Shelf Life Month.
	  * Shelf Life in Month
	  */
	public void setShelfLifeMonth (int ShelfLifeMonth);

	/** Get Shelf Life Month.
	  * Shelf Life in Month
	  */
	public int getShelfLifeMonth();

    /** Column name UNS_Production_Detail_ID */
    public static final String COLUMNNAME_UNS_Production_Detail_ID = "UNS_Production_Detail_ID";

	/** Set Production Detail	  */
	public void setUNS_Production_Detail_ID (int UNS_Production_Detail_ID);

	/** Get Production Detail	  */
	public int getUNS_Production_Detail_ID();

    /** Column name UNS_QAStatus_PRC_ID */
    public static final String COLUMNNAME_UNS_QAStatus_PRC_ID = "UNS_QAStatus_PRC_ID";

	/** Set Production Inspection	  */
	public void setUNS_QAStatus_PRC_ID (int UNS_QAStatus_PRC_ID);

	/** Get Production Inspection	  */
	public int getUNS_QAStatus_PRC_ID();

	public com.uns.qad.model.I_UNS_QAStatus_PRC getUNS_QAStatus_PRC() throws RuntimeException;

    /** Column name UNS_QAStatus_PRCLine_ID */
    public static final String COLUMNNAME_UNS_QAStatus_PRCLine_ID = "UNS_QAStatus_PRCLine_ID";

	/** Set Production Inspection Line	  */
	public void setUNS_QAStatus_PRCLine_ID (int UNS_QAStatus_PRCLine_ID);

	/** Get Production Inspection Line	  */
	public int getUNS_QAStatus_PRCLine_ID();

    /** Column name UNS_QAStatus_PRCLine_UU */
    public static final String COLUMNNAME_UNS_QAStatus_PRCLine_UU = "UNS_QAStatus_PRCLine_UU";

	/** Set UNS_QAStatus_PRCLine_UU	  */
	public void setUNS_QAStatus_PRCLine_UU (String UNS_QAStatus_PRCLine_UU);

	/** Get UNS_QAStatus_PRCLine_UU	  */
	public String getUNS_QAStatus_PRCLine_UU();

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
