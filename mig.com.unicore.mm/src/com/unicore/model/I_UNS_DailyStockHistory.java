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

/** Generated Interface for UNS_DailyStockHistory
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_DailyStockHistory 
{

    /** TableName=UNS_DailyStockHistory */
    public static final String Table_Name = "UNS_DailyStockHistory";

    /** AD_Table_ID=1000242 */
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

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name EndingStock */
    public static final String COLUMNNAME_EndingStock = "EndingStock";

	/** Set Ending Stock	  */
	public void setEndingStock (BigDecimal EndingStock);

	/** Get Ending Stock	  */
	public BigDecimal getEndingStock();

    /** Column name InitialBalance */
    public static final String COLUMNNAME_InitialBalance = "InitialBalance";

	/** Set Initial Balance	  */
	public void setInitialBalance (BigDecimal InitialBalance);

	/** Get Initial Balance	  */
	public BigDecimal getInitialBalance();

    /** Column name InQty */
    public static final String COLUMNNAME_InQty = "InQty";

	/** Set In Quantity	  */
	public void setInQty (BigDecimal InQty);

	/** Get In Quantity	  */
	public BigDecimal getInQty();

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

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name OutQty */
    public static final String COLUMNNAME_OutQty = "OutQty";

	/** Set Out Quantity	  */
	public void setOutQty (BigDecimal OutQty);

	/** Get Out Quantity	  */
	public BigDecimal getOutQty();

    /** Column name UNS_DailyStockHistory_ID */
    public static final String COLUMNNAME_UNS_DailyStockHistory_ID = "UNS_DailyStockHistory_ID";

	/** Set DailyStockHistory	  */
	public void setUNS_DailyStockHistory_ID (int UNS_DailyStockHistory_ID);

	/** Get DailyStockHistory	  */
	public int getUNS_DailyStockHistory_ID();

    /** Column name UNS_DailyStockHistory_UU */
    public static final String COLUMNNAME_UNS_DailyStockHistory_UU = "UNS_DailyStockHistory_UU";

	/** Set UNS_DailyStockHistory_UU	  */
	public void setUNS_DailyStockHistory_UU (String UNS_DailyStockHistory_UU);

	/** Get UNS_DailyStockHistory_UU	  */
	public String getUNS_DailyStockHistory_UU();

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
