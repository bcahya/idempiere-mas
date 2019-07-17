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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_IncentiveComponent
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_IncentiveComponent 
{

    /** TableName=UNS_IncentiveComponent */
    public static final String Table_Name = "UNS_IncentiveComponent";

    /** AD_Table_ID=1000369 */
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

    /** Column name AD_Ref_List_ID */
    public static final String COLUMNNAME_AD_Ref_List_ID = "AD_Ref_List_ID";

	/** Set Reference List.
	  * Reference List based on Table
	  */
	public void setAD_Ref_List_ID (int AD_Ref_List_ID);

	/** Get Reference List.
	  * Reference List based on Table
	  */
	public int getAD_Ref_List_ID();

	public org.compiere.model.I_AD_Ref_List getAD_Ref_List() throws RuntimeException;

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/** Set Promotion Business Partner Group.
	  * Promotion Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/** Get Promotion Business Partner Group.
	  * Promotion Business Partner Group
	  */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException;

    /** Column name ComponentType */
    public static final String COLUMNNAME_ComponentType = "ComponentType";

	/** Set Component Type.
	  * Component Type
	  */
	public void setComponentType (String ComponentType);

	/** Get Component Type.
	  * Component Type
	  */
	public String getComponentType();

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

    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/** Set Product Category.
	  * Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/** Get Product Category.
	  * Category of a Product
	  */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException;

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

    /** Column name TenderType */
    public static final String COLUMNNAME_TenderType = "TenderType";

	/** Set Tender type.
	  * Method of Payment
	  */
	public void setTenderType (String TenderType);

	/** Get Tender type.
	  * Method of Payment
	  */
	public String getTenderType();

    /** Column name UNS_IncentiveComponent_ID */
    public static final String COLUMNNAME_UNS_IncentiveComponent_ID = "UNS_IncentiveComponent_ID";

	/** Set Incentive Component	  */
	public void setUNS_IncentiveComponent_ID (int UNS_IncentiveComponent_ID);

	/** Get Incentive Component	  */
	public int getUNS_IncentiveComponent_ID();

    /** Column name UNS_IncentiveComponent_UU */
    public static final String COLUMNNAME_UNS_IncentiveComponent_UU = "UNS_IncentiveComponent_UU";

	/** Set UNS_IncentiveComponent_UU	  */
	public void setUNS_IncentiveComponent_UU (String UNS_IncentiveComponent_UU);

	/** Get UNS_IncentiveComponent_UU	  */
	public String getUNS_IncentiveComponent_UU();

    /** Column name UNS_Incentive_ID */
    public static final String COLUMNNAME_UNS_Incentive_ID = "UNS_Incentive_ID";

	/** Set Incentive	  */
	public void setUNS_Incentive_ID (int UNS_Incentive_ID);

	/** Get Incentive	  */
	public int getUNS_Incentive_ID();

	public com.unicore.model.I_UNS_Incentive getUNS_Incentive() throws RuntimeException;

    /** Column name UNS_Outlet_Grade_ID */
    public static final String COLUMNNAME_UNS_Outlet_Grade_ID = "UNS_Outlet_Grade_ID";

	/** Set Outlet Grade	  */
	public void setUNS_Outlet_Grade_ID (int UNS_Outlet_Grade_ID);

	/** Get Outlet Grade	  */
	public int getUNS_Outlet_Grade_ID();

    /** Column name UNS_Outlet_Type_ID */
    public static final String COLUMNNAME_UNS_Outlet_Type_ID = "UNS_Outlet_Type_ID";

	/** Set Outlet Type	  */
	public void setUNS_Outlet_Type_ID (int UNS_Outlet_Type_ID);

	/** Get Outlet Type	  */
	public int getUNS_Outlet_Type_ID();

    /** Column name UNS_Rayon_ID */
    public static final String COLUMNNAME_UNS_Rayon_ID = "UNS_Rayon_ID";

	/** Set Rayon	  */
	public void setUNS_Rayon_ID (int UNS_Rayon_ID);

	/** Get Rayon	  */
	public int getUNS_Rayon_ID();

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
