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

/** Generated Interface for UNS_Production_WorkerResult
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_Production_WorkerResult 
{

    /** TableName=UNS_Production_WorkerResult */
    public static final String Table_Name = "UNS_Production_WorkerResult";

    /** AD_Table_ID=1000091 */
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

    /** Column name InsentifPemborong */
    public static final String COLUMNNAME_InsentifPemborong = "InsentifPemborong";

	/** Set Contractor Incentive.
	  * The incentive for contractor based on worker's production result (for Borongan) or worker's presence (for Non-Borongan)
	  */
	public void setInsentifPemborong (BigDecimal InsentifPemborong);

	/** Get Contractor Incentive.
	  * The incentive for contractor based on worker's production result (for Borongan) or worker's presence (for Non-Borongan)
	  */
	public BigDecimal getInsentifPemborong();

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

    /** Column name IsConsiderOutput */
    public static final String COLUMNNAME_IsConsiderOutput = "IsConsiderOutput";

	/** Set Consider Output	  */
	public void setIsConsiderOutput (boolean IsConsiderOutput);

	/** Get Consider Output	  */
	public boolean isConsiderOutput();

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

    /** Column name ProductionQty */
    public static final String COLUMNNAME_ProductionQty = "ProductionQty";

	/** Set Production Quantity.
	  * Quantity of products to produce
	  */
	public void setProductionQty (BigDecimal ProductionQty);

	/** Get Production Quantity.
	  * Quantity of products to produce
	  */
	public BigDecimal getProductionQty();

    /** Column name ReceivableAmt */
    public static final String COLUMNNAME_ReceivableAmt = "ReceivableAmt";

	/** Set Receivable Amt	  */
	public void setReceivableAmt (BigDecimal ReceivableAmt);

	/** Get Receivable Amt	  */
	public BigDecimal getReceivableAmt();

    /** Column name UNS_Production_Worker_ID */
    public static final String COLUMNNAME_UNS_Production_Worker_ID = "UNS_Production_Worker_ID";

	/** Set Production Worker	  */
	public void setUNS_Production_Worker_ID (int UNS_Production_Worker_ID);

	/** Get Production Worker	  */
	public int getUNS_Production_Worker_ID();

    /** Column name UNS_Production_WorkerResult_ID */
    public static final String COLUMNNAME_UNS_Production_WorkerResult_ID = "UNS_Production_WorkerResult_ID";

	/** Set Production Worker Result	  */
	public void setUNS_Production_WorkerResult_ID (int UNS_Production_WorkerResult_ID);

	/** Get Production Worker Result	  */
	public int getUNS_Production_WorkerResult_ID();

    /** Column name UNS_Production_WorkerResult_UU */
    public static final String COLUMNNAME_UNS_Production_WorkerResult_UU = "UNS_Production_WorkerResult_UU";

	/** Set UNS_Production_WorkerResult_UU	  */
	public void setUNS_Production_WorkerResult_UU (String UNS_Production_WorkerResult_UU);

	/** Get UNS_Production_WorkerResult_UU	  */
	public String getUNS_Production_WorkerResult_UU();

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
