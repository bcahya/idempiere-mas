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

/** Generated Interface for UNS_Cvs_PhysicalProductMA
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Cvs_PhysicalProductMA 
{

    /** TableName=UNS_Cvs_PhysicalProductMA */
    public static final String Table_Name = "UNS_Cvs_PhysicalProductMA";

    /** AD_Table_ID=1000229 */
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

    /** Column name DateMaterialPolicy */
    public static final String COLUMNNAME_DateMaterialPolicy = "DateMaterialPolicy";

	/** Set Date  Material Policy.
	  * Time used for LIFO and FIFO Material Policy
	  */
	public void setDateMaterialPolicy (Timestamp DateMaterialPolicy);

	/** Get Date  Material Policy.
	  * Time used for LIFO and FIFO Material Policy
	  */
	public Timestamp getDateMaterialPolicy();

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

    /** Column name IsAutoGenerated */
    public static final String COLUMNNAME_IsAutoGenerated = "IsAutoGenerated";

	/** Set Auto Generated	  */
	public void setIsAutoGenerated (boolean IsAutoGenerated);

	/** Get Auto Generated	  */
	public boolean isAutoGenerated();

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

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;

    /** Column name MovementQty */
    public static final String COLUMNNAME_MovementQty = "MovementQty";

	/** Set Movement Quantity.
	  * Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty);

	/** Get Movement Quantity.
	  * Quantity of a product moved.
	  */
	public BigDecimal getMovementQty();

    /** Column name UNS_Cvs_PhysicalProduct_ID */
    public static final String COLUMNNAME_UNS_Cvs_PhysicalProduct_ID = "UNS_Cvs_PhysicalProduct_ID";

	/** Set Canvas Physical Product	  */
	public void setUNS_Cvs_PhysicalProduct_ID (int UNS_Cvs_PhysicalProduct_ID);

	/** Get Canvas Physical Product	  */
	public int getUNS_Cvs_PhysicalProduct_ID();

	public com.unicore.model.I_UNS_Cvs_PhysicalProduct getUNS_Cvs_PhysicalProduct() throws RuntimeException;

    /** Column name UNS_Cvs_PhysicalProductMA_UU */
    public static final String COLUMNNAME_UNS_Cvs_PhysicalProductMA_UU = "UNS_Cvs_PhysicalProductMA_UU";

	/** Set UNS_Cvs_PhysicalProductMA_UU	  */
	public void setUNS_Cvs_PhysicalProductMA_UU (String UNS_Cvs_PhysicalProductMA_UU);

	/** Get UNS_Cvs_PhysicalProductMA_UU	  */
	public String getUNS_Cvs_PhysicalProductMA_UU();

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
