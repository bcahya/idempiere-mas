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

/** Generated Interface for UNS_SrcProduct_Detail
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_SrcProduct_Detail 
{

    /** TableName=UNS_SrcProduct_Detail */
    public static final String Table_Name = "UNS_SrcProduct_Detail";

    /** AD_Table_ID=1000127 */
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

    /** Column name AvailableQty */
    public static final String COLUMNNAME_AvailableQty = "AvailableQty";

	/** Set Available Qty.
	  * The quantity of the source product (OnHand - OnHold - NC)
	  */
	public void setAvailableQty (BigDecimal AvailableQty);

	/** Get Available Qty.
	  * The quantity of the source product (OnHand - OnHold - NC)
	  */
	public BigDecimal getAvailableQty();

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

    /** Column name QtyMT */
    public static final String COLUMNNAME_QtyMT = "QtyMT";

	/** Set Raw Material-F Qty (MT).
	  * Quantity of Raw Material Forecast in Metric Ton
	  */
	public void setQtyMT (BigDecimal QtyMT);

	/** Get Raw Material-F Qty (MT).
	  * Quantity of Raw Material Forecast in Metric Ton
	  */
	public BigDecimal getQtyMT();

    /** Column name QtyUom */
    public static final String COLUMNNAME_QtyUom = "QtyUom";

	/** Set Raw Material-F Qty (UOM).
	  * Quantity of Raw Material Forecast in Metric Ton
	  */
	public void setQtyUom (BigDecimal QtyUom);

	/** Get Raw Material-F Qty (UOM).
	  * Quantity of Raw Material Forecast in Metric Ton
	  */
	public BigDecimal getQtyUom();

    /** Column name UNS_ProductionSchedule_ID */
    public static final String COLUMNNAME_UNS_ProductionSchedule_ID = "UNS_ProductionSchedule_ID";

	/** Set Production Schedule	  */
	public void setUNS_ProductionSchedule_ID (int UNS_ProductionSchedule_ID);

	/** Get Production Schedule	  */
	public int getUNS_ProductionSchedule_ID();

	public com.uns.model.I_UNS_ProductionSchedule getUNS_ProductionSchedule() throws RuntimeException;

    /** Column name UNS_SrcProduct_Detail_ID */
    public static final String COLUMNNAME_UNS_SrcProduct_Detail_ID = "UNS_SrcProduct_Detail_ID";

	/** Set Source Product Detail	  */
	public void setUNS_SrcProduct_Detail_ID (int UNS_SrcProduct_Detail_ID);

	/** Get Source Product Detail	  */
	public int getUNS_SrcProduct_Detail_ID();

    /** Column name UNS_SrcProduct_Detail_UU */
    public static final String COLUMNNAME_UNS_SrcProduct_Detail_UU = "UNS_SrcProduct_Detail_UU";

	/** Set UNS_SrcProduct_Detail_UU	  */
	public void setUNS_SrcProduct_Detail_UU (String UNS_SrcProduct_Detail_UU);

	/** Get UNS_SrcProduct_Detail_UU	  */
	public String getUNS_SrcProduct_Detail_UU();

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

    /** Column name UsedQty */
    public static final String COLUMNNAME_UsedQty = "UsedQty";

	/** Set Qty To Use.
	  * Quantity to be used as the source product of the target production (order) for selected ASI
	  */
	public void setUsedQty (BigDecimal UsedQty);

	/** Get Qty To Use.
	  * Quantity to be used as the source product of the target production (order) for selected ASI
	  */
	public BigDecimal getUsedQty();
}
