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

/** Generated Interface for UNS_QAStatus_InOutLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_QAStatus_InOutLine 
{

    /** TableName=UNS_QAStatus_InOutLine */
    public static final String Table_Name = "UNS_QAStatus_InOutLine";

    /** AD_Table_ID=1000241 */
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

    /** Column name COA */
    public static final String COLUMNNAME_COA = "COA";

	/** Set Certificate of Analysis.
	  * Certificate of Analysis
	  */
	public void setCOA (String COA);

	/** Get Certificate of Analysis.
	  * Certificate of Analysis
	  */
	public String getCOA();

    /** Column name ConfirmedQty */
    public static final String COLUMNNAME_ConfirmedQty = "ConfirmedQty";

	/** Set Confirmed Quantity.
	  * Confirmation of a received quantity
	  */
	public void setConfirmedQty (BigDecimal ConfirmedQty);

	/** Get Confirmed Quantity.
	  * Confirmation of a received quantity
	  */
	public BigDecimal getConfirmedQty();

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

    /** Column name Expiration */
    public static final String COLUMNNAME_Expiration = "Expiration";

	/** Set Expire On.
	  * Expire On
	  */
	public void setExpiration (Timestamp Expiration);

	/** Get Expire On.
	  * Expire On
	  */
	public Timestamp getExpiration();

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

    /** Column name IsReturnAfterInspection */
    public static final String COLUMNNAME_IsReturnAfterInspection = "IsReturnAfterInspection";

	/** Set Is Return After Inspection	  */
	public void setIsReturnAfterInspection (boolean IsReturnAfterInspection);

	/** Get Is Return After Inspection	  */
	public boolean isReturnAfterInspection();

    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/** Set Shipment/Receipt Line.
	  * Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/** Get Shipment/Receipt Line.
	  * Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException;

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

    /** Column name NCQty */
    public static final String COLUMNNAME_NCQty = "NCQty";

	/** Set Quantity NC	  */
	public void setNCQty (BigDecimal NCQty);

	/** Get Quantity NC	  */
	public BigDecimal getNCQty();

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

    /** Column name SampleQty */
    public static final String COLUMNNAME_SampleQty = "SampleQty";

	/** Set Sample Qty	  */
	public void setSampleQty (BigDecimal SampleQty);

	/** Get Sample Qty	  */
	public BigDecimal getSampleQty();

    /** Column name TargetQty */
    public static final String COLUMNNAME_TargetQty = "TargetQty";

	/** Set Target Quantity.
	  * Target Movement Quantity
	  */
	public void setTargetQty (BigDecimal TargetQty);

	/** Get Target Quantity.
	  * Target Movement Quantity
	  */
	public BigDecimal getTargetQty();

    /** Column name UNS_QAStatus_InOut_ID */
    public static final String COLUMNNAME_UNS_QAStatus_InOut_ID = "UNS_QAStatus_InOut_ID";

	/** Set Ship/Receipt Inspection	  */
	public void setUNS_QAStatus_InOut_ID (int UNS_QAStatus_InOut_ID);

	/** Get Ship/Receipt Inspection	  */
	public int getUNS_QAStatus_InOut_ID();

	public com.uns.qad.model.I_UNS_QAStatus_InOut getUNS_QAStatus_InOut() throws RuntimeException;

    /** Column name UNS_QAStatus_InOutLine_ID */
    public static final String COLUMNNAME_UNS_QAStatus_InOutLine_ID = "UNS_QAStatus_InOutLine_ID";

	/** Set Ship/Receipt Inspection Line	  */
	public void setUNS_QAStatus_InOutLine_ID (int UNS_QAStatus_InOutLine_ID);

	/** Get Ship/Receipt Inspection Line	  */
	public int getUNS_QAStatus_InOutLine_ID();

    /** Column name UNS_QAStatus_InOutLine_UU */
    public static final String COLUMNNAME_UNS_QAStatus_InOutLine_UU = "UNS_QAStatus_InOutLine_UU";

	/** Set UNS_QAStatus_InOutLine_UU	  */
	public void setUNS_QAStatus_InOutLine_UU (String UNS_QAStatus_InOutLine_UU);

	/** Get UNS_QAStatus_InOutLine_UU	  */
	public String getUNS_QAStatus_InOutLine_UU();

    /** Column name UNS_QAStatus_Type_ID */
    public static final String COLUMNNAME_UNS_QAStatus_Type_ID = "UNS_QAStatus_Type_ID";

	/** Set Attribute Type	  */
	public void setUNS_QAStatus_Type_ID (int UNS_QAStatus_Type_ID);

	/** Get Attribute Type	  */
	public int getUNS_QAStatus_Type_ID();

	public com.uns.qad.model.I_UNS_QAStatus_Type getUNS_QAStatus_Type() throws RuntimeException;

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
