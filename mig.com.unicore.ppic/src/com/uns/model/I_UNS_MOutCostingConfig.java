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

/** Generated Interface for UNS_MOutCostingConfig
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_MOutCostingConfig 
{

    /** TableName=UNS_MOutCostingConfig */
    public static final String Table_Name = "UNS_MOutCostingConfig";

    /** AD_Table_ID=1000114 */
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

    /** Column name ConversionBased */
    public static final String COLUMNNAME_ConversionBased = "ConversionBased";

	/** Set Conversion Based	  */
	public void setConversionBased (String ConversionBased);

	/** Get Conversion Based	  */
	public String getConversionBased();

    /** Column name ConversionPercent */
    public static final String COLUMNNAME_ConversionPercent = "ConversionPercent";

	/** Set Conversion Percent	  */
	public void setConversionPercent (BigDecimal ConversionPercent);

	/** Get Conversion Percent	  */
	public BigDecimal getConversionPercent();

    /** Column name ConversionProduct_ID */
    public static final String COLUMNNAME_ConversionProduct_ID = "ConversionProduct_ID";

	/** Set Conversion Product	  */
	public void setConversionProduct_ID (int ConversionProduct_ID);

	/** Get Conversion Product	  */
	public int getConversionProduct_ID();

	public org.compiere.model.I_M_Product getConversionProduct() throws RuntimeException;

    /** Column name CostingMethod */
    public static final String COLUMNNAME_CostingMethod = "CostingMethod";

	/** Set Costing Method.
	  * Indicates how Costs will be calculated
	  */
	public void setCostingMethod (String CostingMethod);

	/** Get Costing Method.
	  * Indicates how Costs will be calculated
	  */
	public String getCostingMethod();

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

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Price List.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/** Get Price List.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException;

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

    /** Column name M_Product_To_ID */
    public static final String COLUMNNAME_M_Product_To_ID = "M_Product_To_ID";

	/** Set To Product.
	  * Product to be converted to (must have UOM Conversion defined to From Product)
	  */
	public void setM_Product_To_ID (int M_Product_To_ID);

	/** Get To Product.
	  * Product to be converted to (must have UOM Conversion defined to From Product)
	  */
	public int getM_Product_To_ID();

	public org.compiere.model.I_M_Product getM_Product_To() throws RuntimeException;

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

    /** Column name StandartCosting */
    public static final String COLUMNNAME_StandartCosting = "StandartCosting";

	/** Set Standart Costing	  */
	public void setStandartCosting (BigDecimal StandartCosting);

	/** Get Standart Costing	  */
	public BigDecimal getStandartCosting();

    /** Column name UNS_MOutCostingConfig_ID */
    public static final String COLUMNNAME_UNS_MOutCostingConfig_ID = "UNS_MOutCostingConfig_ID";

	/** Set Multi Output Costing	  */
	public void setUNS_MOutCostingConfig_ID (int UNS_MOutCostingConfig_ID);

	/** Get Multi Output Costing	  */
	public int getUNS_MOutCostingConfig_ID();

    /** Column name UNS_MOutCostingConfig_UU */
    public static final String COLUMNNAME_UNS_MOutCostingConfig_UU = "UNS_MOutCostingConfig_UU";

	/** Set UNS_MOutCostingConfig_UU	  */
	public void setUNS_MOutCostingConfig_UU (String UNS_MOutCostingConfig_UU);

	/** Get UNS_MOutCostingConfig_UU	  */
	public String getUNS_MOutCostingConfig_UU();

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
