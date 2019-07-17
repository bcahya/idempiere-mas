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

/** Generated Interface for UNS_CustomerPoint
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_CustomerPoint 
{

    /** TableName=UNS_CustomerPoint */
    public static final String Table_Name = "UNS_CustomerPoint";

    /** AD_Table_ID=1000235 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AccumulatedPointNotYetValid */
    public static final String COLUMNNAME_AccumulatedPointNotYetValid = "AccumulatedPointNotYetValid";

	/** Set Accumulated Point Not Yet Valid	  */
	public void setAccumulatedPointNotYetValid (BigDecimal AccumulatedPointNotYetValid);

	/** Get Accumulated Point Not Yet Valid	  */
	public BigDecimal getAccumulatedPointNotYetValid();

    /** Column name AccumulatedPointValid */
    public static final String COLUMNNAME_AccumulatedPointValid = "AccumulatedPointValid";

	/** Set Accumulated Point Valid.
	  * Accumulated Point Valid
	  */
	public void setAccumulatedPointValid (BigDecimal AccumulatedPointValid);

	/** Get Accumulated Point Valid.
	  * Accumulated Point Valid
	  */
	public BigDecimal getAccumulatedPointValid();

    /** Column name AccumulatedRedeemed */
    public static final String COLUMNNAME_AccumulatedRedeemed = "AccumulatedRedeemed";

	/** Set AccumulatedRedeemed	  */
	public void setAccumulatedRedeemed (int AccumulatedRedeemed);

	/** Get AccumulatedRedeemed	  */
	public int getAccumulatedRedeemed();

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

	/** Set Business Partner Group.
	  * Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/** Get Business Partner Group.
	  * Business Partner Group
	  */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException;

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

//    /** Column name CurrentPoint */
//    public static final String COLUMNNAME_CurrentPoint = "CurrentPoint";
//
//	/** Set CurrentPoint	  */
//	public void setCurrentPoint (BigDecimal CurrentPoint);
//
//	/** Get CurrentPoint	  */
//	public BigDecimal getCurrentPoint();

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

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Sales Transaction.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx);

	/** Get Sales Transaction.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

    /** Column name LatestRedemptionDate */
    public static final String COLUMNNAME_LatestRedemptionDate = "LatestRedemptionDate";

	/** Set LatestRedemptionDate	  */
	public void setLatestRedemptionDate (Timestamp LatestRedemptionDate);

	/** Get LatestRedemptionDate	  */
	public Timestamp getLatestRedemptionDate();

    /** Column name LatestTrxDate */
    public static final String COLUMNNAME_LatestTrxDate = "LatestTrxDate";

	/** Set LatestTrxDate	  */
	public void setLatestTrxDate (Timestamp LatestTrxDate);

	/** Get LatestTrxDate	  */
	public Timestamp getLatestTrxDate();

    /** Column name StartTrxDate */
    public static final String COLUMNNAME_StartTrxDate = "StartTrxDate";

	/** Set StartTrxDate	  */
	public void setStartTrxDate (Timestamp StartTrxDate);

	/** Get StartTrxDate	  */
	public Timestamp getStartTrxDate();

    /** Column name UNS_CustomerPoint_ID */
    public static final String COLUMNNAME_UNS_CustomerPoint_ID = "UNS_CustomerPoint_ID";

	/** Set Customer Point	  */
	public void setUNS_CustomerPoint_ID (int UNS_CustomerPoint_ID);

	/** Get Customer Point	  */
	public int getUNS_CustomerPoint_ID();

    /** Column name UNS_CustomerPoint_UU */
    public static final String COLUMNNAME_UNS_CustomerPoint_UU = "UNS_CustomerPoint_UU";

	/** Set UNS_CustomerPoint_UU	  */
	public void setUNS_CustomerPoint_UU (String UNS_CustomerPoint_UU);

	/** Get UNS_CustomerPoint_UU	  */
	public String getUNS_CustomerPoint_UU();

    /** Column name UNS_Outlet_Type_ID */
    public static final String COLUMNNAME_UNS_Outlet_Type_ID = "UNS_Outlet_Type_ID";

	/** Set Outlet Type	  */
	public void setUNS_Outlet_Type_ID (int UNS_Outlet_Type_ID);

	/** Get Outlet Type	  */
	public int getUNS_Outlet_Type_ID();

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
