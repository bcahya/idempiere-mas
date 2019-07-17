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

/** Generated Interface for UNS_ShippingCrewIncentive
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_ShippingCrewIncentive 
{

    /** TableName=UNS_ShippingCrewIncentive */
    public static final String Table_Name = "UNS_ShippingCrewIncentive";

    /** AD_Table_ID=1000236 */
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

    /** Column name AmountBonuses */
    public static final String COLUMNNAME_AmountBonuses = "AmountBonuses";

	/** Set Amount Bonuses	  */
	public void setAmountBonuses (BigDecimal AmountBonuses);

	/** Get Amount Bonuses	  */
	public BigDecimal getAmountBonuses();

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

    /** Column name CreateSettlement */
    public static final String COLUMNNAME_CreateSettlement = "CreateSettlement";

	/** Set Create Settlement	  */
	public void setCreateSettlement (String CreateSettlement);

	/** Get Create Settlement	  */
	public String getCreateSettlement();

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

    /** Column name DateFrom */
    public static final String COLUMNNAME_DateFrom = "DateFrom";

	/** Set Date From.
	  * Starting date for a range
	  */
	public void setDateFrom (Timestamp DateFrom);

	/** Get Date From.
	  * Starting date for a range
	  */
	public Timestamp getDateFrom();

    /** Column name DateProcessed */
    public static final String COLUMNNAME_DateProcessed = "DateProcessed";

	/** Set Date Processed	  */
	public void setDateProcessed (Timestamp DateProcessed);

	/** Get Date Processed	  */
	public Timestamp getDateProcessed();

    /** Column name DateTo */
    public static final String COLUMNNAME_DateTo = "DateTo";

	/** Set Date To.
	  * End date of a date range
	  */
	public void setDateTo (Timestamp DateTo);

	/** Get Date To.
	  * End date of a date range
	  */
	public Timestamp getDateTo();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();

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

    /** Column name PrintDocument */
    public static final String COLUMNNAME_PrintDocument = "PrintDocument";

	/** Set Print Document	  */
	public void setPrintDocument (String PrintDocument);

	/** Get Print Document	  */
	public String getPrintDocument();

    /** Column name UNS_Charge_RS_ID */
    public static final String COLUMNNAME_UNS_Charge_RS_ID = "UNS_Charge_RS_ID";

	/** Set Charge Request / Settlement	  */
	public void setUNS_Charge_RS_ID (int UNS_Charge_RS_ID);

	/** Get Charge Request / Settlement	  */
	public int getUNS_Charge_RS_ID();

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

    /** Column name UNS_ShippingCrewIncentive_ID */
    public static final String COLUMNNAME_UNS_ShippingCrewIncentive_ID = "UNS_ShippingCrewIncentive_ID";

	/** Set Shipping Crew Incentive ID	  */
	public void setUNS_ShippingCrewIncentive_ID (int UNS_ShippingCrewIncentive_ID);

	/** Get Shipping Crew Incentive ID	  */
	public int getUNS_ShippingCrewIncentive_ID();

    /** Column name UNS_ShippingCrewIncentive_UU */
    public static final String COLUMNNAME_UNS_ShippingCrewIncentive_UU = "UNS_ShippingCrewIncentive_UU";

	/** Set Shipping Crew Incentive UU	  */
	public void setUNS_ShippingCrewIncentive_UU (String UNS_ShippingCrewIncentive_UU);

	/** Get Shipping Crew Incentive UU	  */
	public String getUNS_ShippingCrewIncentive_UU();

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
