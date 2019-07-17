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

/** Generated Interface for UNS_Production_OutPlan
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Production_OutPlan 
{

    /** TableName=UNS_Production_OutPlan */
    public static final String Table_Name = "UNS_Production_OutPlan";

    /** AD_Table_ID=1000060 */
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

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name BOMType */
    public static final String COLUMNNAME_BOMType = "BOMType";

	/** Set BOM Type.
	  * Type of BOM
	  */
	public void setBOMType (String BOMType);

	/** Get BOM Type.
	  * Type of BOM
	  */
	public String getBOMType();

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

    /** Column name IsPrimary */
    public static final String COLUMNNAME_IsPrimary = "IsPrimary";

	/** Set Primary.
	  * Indicates if this is the primary budget
	  */
	public void setIsPrimary (boolean IsPrimary);

	/** Get Primary.
	  * Indicates if this is the primary budget
	  */
	public boolean isPrimary();

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

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

    /** Column name PrimaryOutput_ID */
    public static final String COLUMNNAME_PrimaryOutput_ID = "PrimaryOutput_ID";

	/** Set Primary Output.
	  * The related primary output of this non-primary output.
	  */
	public void setPrimaryOutput_ID (int PrimaryOutput_ID);

	/** Get Primary Output.
	  * The related primary output of this non-primary output.
	  */
	public int getPrimaryOutput_ID();

    /** Column name QtyPlan */
    public static final String COLUMNNAME_QtyPlan = "QtyPlan";

	/** Set Quantity Plan.
	  * Planned Quantity
	  */
	public void setQtyPlan (BigDecimal QtyPlan);

	/** Get Quantity Plan.
	  * Planned Quantity
	  */
	public BigDecimal getQtyPlan();

    /** Column name UNS_Production_ID */
    public static final String COLUMNNAME_UNS_Production_ID = "UNS_Production_ID";

	/** Set Production	  */
	public void setUNS_Production_ID (int UNS_Production_ID);

	/** Get Production	  */
	public int getUNS_Production_ID();

	public com.uns.model.I_UNS_Production getUNS_Production() throws RuntimeException;

    /** Column name UNS_Production_OutPlan_ID */
    public static final String COLUMNNAME_UNS_Production_OutPlan_ID = "UNS_Production_OutPlan_ID";

	/** Set Production Output Plan	  */
	public void setUNS_Production_OutPlan_ID (int UNS_Production_OutPlan_ID);

	/** Get Production Output Plan	  */
	public int getUNS_Production_OutPlan_ID();

    /** Column name UNS_Production_OutPlan_UU */
    public static final String COLUMNNAME_UNS_Production_OutPlan_UU = "UNS_Production_OutPlan_UU";

	/** Set UNS_Production_OutPlan_UU	  */
	public void setUNS_Production_OutPlan_UU (String UNS_Production_OutPlan_UU);

	/** Get UNS_Production_OutPlan_UU	  */
	public String getUNS_Production_OutPlan_UU();

    /** Column name UNS_Resource_InOut_ID */
    public static final String COLUMNNAME_UNS_Resource_InOut_ID = "UNS_Resource_InOut_ID";

	/** Set Manufacture Resource InOut	  */
	public void setUNS_Resource_InOut_ID (int UNS_Resource_InOut_ID);

	/** Get Manufacture Resource InOut	  */
	public int getUNS_Resource_InOut_ID();

	public com.uns.model.I_UNS_Resource_InOut getUNS_Resource_InOut() throws RuntimeException;

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
