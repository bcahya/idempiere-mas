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

/** Generated Interface for UNS_ShippingInctvSch_Line
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_ShippingInctvSch_Line 
{

    /** TableName=UNS_ShippingInctvSch_Line */
    public static final String Table_Name = "UNS_ShippingInctvSch_Line";

    /** AD_Table_ID=1000235 */
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

    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/** Set Period.
	  * Period of the Calendar
	  */
	public void setC_Period_ID (int C_Period_ID);

	/** Get Period.
	  * Period of the Calendar
	  */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException;

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

    /** Column name DateIncentive */
    public static final String COLUMNNAME_DateIncentive = "DateIncentive";

	/** Set Date Incentive	  */
	public void setDateIncentive (Timestamp DateIncentive);

	/** Get Date Incentive	  */
	public Timestamp getDateIncentive();

    /** Column name Destination_ID */
    public static final String COLUMNNAME_Destination_ID = "Destination_ID";

	/** Set Destination	  */
	public void setDestination_ID (int Destination_ID);

	/** Get Destination	  */
	public int getDestination_ID();

	public org.compiere.model.I_C_City getDestination() throws RuntimeException;

    /** Column name HelperValue */
    public static final String COLUMNNAME_HelperValue = "HelperValue";

	/** Set Helper Value	  */
	public void setHelperValue (BigDecimal HelperValue);

	/** Get Helper Value	  */
	public BigDecimal getHelperValue();

    /** Column name IncentiveType */
    public static final String COLUMNNAME_IncentiveType = "IncentiveType";

	/** Set Incentive Type.
	  * Type of incentive (Sales Incentive/Billing Incentive)
	  */
	public void setIncentiveType (String IncentiveType);

	/** Get Incentive Type.
	  * Type of incentive (Sales Incentive/Billing Incentive)
	  */
	public String getIncentiveType();

    /** Column name IncentiveValue */
    public static final String COLUMNNAME_IncentiveValue = "IncentiveValue";

	/** Set Incentive Value	  */
	public void setIncentiveValue (int IncentiveValue);

	/** Get Incentive Value	  */
	public int getIncentiveValue();

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

    /** Column name minRitase */
    public static final String COLUMNNAME_minRitase = "minRitase";

	/** Set Minimum Ritase	  */
	public void setminRitase (String minRitase);

	/** Get Minimum Ritase	  */
	public String getminRitase();

    /** Column name Origin_ID */
    public static final String COLUMNNAME_Origin_ID = "Origin_ID";

	/** Set Origin	  */
	public void setOrigin_ID (int Origin_ID);

	/** Get Origin	  */
	public int getOrigin_ID();

	public org.compiere.model.I_C_City getOrigin() throws RuntimeException;

    /** Column name Reason */
    public static final String COLUMNNAME_Reason = "Reason";

	/** Set Reason	  */
	public void setReason (String Reason);

	/** Get Reason	  */
	public String getReason();

    /** Column name UNS_Rayon_ID */
    public static final String COLUMNNAME_UNS_Rayon_ID = "UNS_Rayon_ID";

	/** Set Rayon	  */
	public void setUNS_Rayon_ID (int UNS_Rayon_ID);

	/** Get Rayon	  */
	public int getUNS_Rayon_ID();

    /** Column name UNS_ShippingIncentiveSch_ID */
    public static final String COLUMNNAME_UNS_ShippingIncentiveSch_ID = "UNS_ShippingIncentiveSch_ID";

	/** Set Shipping Incentive Schema ID	  */
	public void setUNS_ShippingIncentiveSch_ID (int UNS_ShippingIncentiveSch_ID);

	/** Get Shipping Incentive Schema ID	  */
	public int getUNS_ShippingIncentiveSch_ID();

    /** Column name UNS_ShippingInctvSch_Line_ID */
    public static final String COLUMNNAME_UNS_ShippingInctvSch_Line_ID = "UNS_ShippingInctvSch_Line_ID";

	/** Set Shipping Incentive Schema Line ID	  */
	public void setUNS_ShippingInctvSch_Line_ID (int UNS_ShippingInctvSch_Line_ID);

	/** Get Shipping Incentive Schema Line ID	  */
	public int getUNS_ShippingInctvSch_Line_ID();

    /** Column name UNS_ShippingInctvSch_Line_UU */
    public static final String COLUMNNAME_UNS_ShippingInctvSch_Line_UU = "UNS_ShippingInctvSch_Line_UU";

	/** Set Shipping Incentive Schema Line UU	  */
	public void setUNS_ShippingInctvSch_Line_UU (String UNS_ShippingInctvSch_Line_UU);

	/** Get Shipping Incentive Schema Line UU	  */
	public String getUNS_ShippingInctvSch_Line_UU();

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
