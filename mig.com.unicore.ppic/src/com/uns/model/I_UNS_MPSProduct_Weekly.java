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

/** Generated Interface for UNS_MPSProduct_Weekly
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_MPSProduct_Weekly 
{

    /** TableName=UNS_MPSProduct_Weekly */
    public static final String Table_Name = "UNS_MPSProduct_Weekly";

    /** AD_Table_ID=1000314 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AATP */
    public static final String COLUMNNAME_AATP = "AATP";

	/** Set Actual ATP.
	  * Actual Available To Production/Promise
	  */
	public void setAATP (BigDecimal AATP);

	/** Get Actual ATP.
	  * Actual Available To Production/Promise
	  */
	public BigDecimal getAATP();

    /** Column name Acc_ATP */
    public static final String COLUMNNAME_Acc_ATP = "Acc_ATP";

	/** Set Projected CATP.
	  * Projected Cummulated Available To Promise
	  */
	public void setAcc_ATP (BigDecimal Acc_ATP);

	/** Get Projected CATP.
	  * Projected Cummulated Available To Promise
	  */
	public BigDecimal getAcc_ATP();

    /** Column name ActualAvailableBalances */
    public static final String COLUMNNAME_ActualAvailableBalances = "ActualAvailableBalances";

	/** Set Actual Available Balances.
	  * Actual Available Balances
	  */
	public void setActualAvailableBalances (BigDecimal ActualAvailableBalances);

	/** Get Actual Available Balances.
	  * Actual Available Balances
	  */
	public BigDecimal getActualAvailableBalances();

    /** Column name ActualProduced */
    public static final String COLUMNNAME_ActualProduced = "ActualProduced";

	/** Set Total Actual Manufactured.
	  * Latest amount actually manufactured in UOM quantity
	  */
	public void setActualProduced (BigDecimal ActualProduced);

	/** Get Total Actual Manufactured.
	  * Latest amount actually manufactured in UOM quantity
	  */
	public BigDecimal getActualProduced();

    /** Column name ActualScheduledMT */
    public static final String COLUMNNAME_ActualScheduledMT = "ActualScheduledMT";

	/** Set Total Actual Scheduled (MT).
	  * Total Actual Scheduled (MT)
	  */
	public void setActualScheduledMT (BigDecimal ActualScheduledMT);

	/** Get Total Actual Scheduled (MT).
	  * Total Actual Scheduled (MT)
	  */
	public BigDecimal getActualScheduledMT();

    /** Column name ActualScheduledUOM */
    public static final String COLUMNNAME_ActualScheduledUOM = "ActualScheduledUOM";

	/** Set Total Actual Scheduled (UOM).
	  * Total Actual Scheduled (UOM)
	  */
	public void setActualScheduledUOM (BigDecimal ActualScheduledUOM);

	/** Get Total Actual Scheduled (UOM).
	  * Total Actual Scheduled (UOM)
	  */
	public BigDecimal getActualScheduledUOM();

    /** Column name ActualToOrderMT */
    public static final String COLUMNNAME_ActualToOrderMT = "ActualToOrderMT";

	/** Set Actual To Order (MT).
	  * Actual To Order (MT)
	  */
	public void setActualToOrderMT (BigDecimal ActualToOrderMT);

	/** Get Actual To Order (MT).
	  * Actual To Order (MT)
	  */
	public BigDecimal getActualToOrderMT();

    /** Column name ActualToOrderUOM */
    public static final String COLUMNNAME_ActualToOrderUOM = "ActualToOrderUOM";

	/** Set Actual To Order (UOM).
	  * Actual To Order (UOM)
	  */
	public void setActualToOrderUOM (BigDecimal ActualToOrderUOM);

	/** Get Actual To Order (UOM).
	  * Actual To Order (UOM)
	  */
	public BigDecimal getActualToOrderUOM();

    /** Column name ActualToStockMT */
    public static final String COLUMNNAME_ActualToStockMT = "ActualToStockMT";

	/** Set Master Scheduled (MT).
	  * Master Scheduled (MT)
	  */
	public void setActualToStockMT (BigDecimal ActualToStockMT);

	/** Get Master Scheduled (MT).
	  * Master Scheduled (MT)
	  */
	public BigDecimal getActualToStockMT();

    /** Column name ActualToStockUOM */
    public static final String COLUMNNAME_ActualToStockUOM = "ActualToStockUOM";

	/** Set Master Scheduled (UOM).
	  * Master Scheduled (UOM)
	  */
	public void setActualToStockUOM (BigDecimal ActualToStockUOM);

	/** Get Master Scheduled (UOM).
	  * Master Scheduled (UOM)
	  */
	public BigDecimal getActualToStockUOM();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
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

    /** Column name ATP */
    public static final String COLUMNNAME_ATP = "ATP";

	/** Set Projected ATP.
	  * Projected Available To Promise
	  */
	public void setATP (BigDecimal ATP);

	/** Get Projected ATP.
	  * Projected Available To Promise
	  */
	public BigDecimal getATP();

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

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

    /** Column name EndOfIncubation */
    public static final String COLUMNNAME_EndOfIncubation = "EndOfIncubation";

	/** Set End Of Incubation	  */
	public void setEndOfIncubation (Timestamp EndOfIncubation);

	/** Get End Of Incubation	  */
	public Timestamp getEndOfIncubation();

    /** Column name IncubationDays */
    public static final String COLUMNNAME_IncubationDays = "IncubationDays";

	/** Set Incubation Days.
	  * Incubation period of product in days
	  */
	public void setIncubationDays (int IncubationDays);

	/** Get Incubation Days.
	  * Incubation period of product in days
	  */
	public int getIncubationDays();

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

    /** Column name MPSDate */
    public static final String COLUMNNAME_MPSDate = "MPSDate";

	/** Set Date	  */
	public void setMPSDate (Timestamp MPSDate);

	/** Get Date	  */
	public Timestamp getMPSDate();

    /** Column name ProjectedActualBalance */
    public static final String COLUMNNAME_ProjectedActualBalance = "ProjectedActualBalance";

	/** Set Projected Available Balances.
	  * Projected Available Balances
	  */
	public void setProjectedActualBalance (BigDecimal ProjectedActualBalance);

	/** Get Projected Available Balances.
	  * Projected Available Balances
	  */
	public BigDecimal getProjectedActualBalance();

    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/** Set Delivered Quantity.
	  * Delivered Quantity
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered);

	/** Get Delivered Quantity.
	  * Delivered Quantity
	  */
	public BigDecimal getQtyDelivered();

    /** Column name QtyMiscUsed */
    public static final String COLUMNNAME_QtyMiscUsed = "QtyMiscUsed";

	/** Set Misc. Used Qty.
	  * Miscellaneous used qty (other used besides quantity to deliver)
	  */
	public void setQtyMiscUsed (BigDecimal QtyMiscUsed);

	/** Get Misc. Used Qty.
	  * Miscellaneous used qty (other used besides quantity to deliver)
	  */
	public BigDecimal getQtyMiscUsed();

    /** Column name QtyMT */
    public static final String COLUMNNAME_QtyMT = "QtyMT";

	/** Set Targeted-F Qty (MT).
	  * Quantity of Targeted Forecast in Metric Ton
	  */
	public void setQtyMT (BigDecimal QtyMT);

	/** Get Targeted-F Qty (MT).
	  * Quantity of Targeted Forecast in Metric Ton
	  */
	public BigDecimal getQtyMT();

    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/** Set On Hand Quantity.
	  * On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand);

	/** Get On Hand Quantity.
	  * On Hand Quantity
	  */
	public BigDecimal getQtyOnHand();

    /** Column name QtyUom */
    public static final String COLUMNNAME_QtyUom = "QtyUom";

	/** Set Targeted-F Qty (UOM).
	  * Quantity of Raw Targeted Forecast in Metric Ton
	  */
	public void setQtyUom (BigDecimal QtyUom);

	/** Get Targeted-F Qty (UOM).
	  * Quantity of Raw Targeted Forecast in Metric Ton
	  */
	public BigDecimal getQtyUom();

    /** Column name RSFS */
    public static final String COLUMNNAME_RSFS = "RSFS";

	/** Set RSFS.
	  * Actual Rest Stock For Sales
	  */
	public void setRSFS (BigDecimal RSFS);

	/** Get RSFS.
	  * Actual Rest Stock For Sales
	  */
	public BigDecimal getRSFS();

    /** Column name SafetyStock */
    public static final String COLUMNNAME_SafetyStock = "SafetyStock";

	/** Set Safety Stock Qty.
	  * Safety stock is a term used to describe a level of stock that is maintained below the cycle stock to buffer against stock-outs
	  */
	public void setSafetyStock (BigDecimal SafetyStock);

	/** Get Safety Stock Qty.
	  * Safety stock is a term used to describe a level of stock that is maintained below the cycle stock to buffer against stock-outs
	  */
	public BigDecimal getSafetyStock();

    /** Column name ScheduledAvaialbleBalances */
    public static final String COLUMNNAME_ScheduledAvaialbleBalances = "ScheduledAvaialbleBalances";

	/** Set Schedule Available Balances.
	  * Schedule Available Balances
	  */
	public void setScheduledAvaialbleBalances (BigDecimal ScheduledAvaialbleBalances);

	/** Get Schedule Available Balances.
	  * Schedule Available Balances
	  */
	public BigDecimal getScheduledAvaialbleBalances();

    /** Column name SOH */
    public static final String COLUMNNAME_SOH = "SOH";

	/** Set SOH.
	  * Scheduled (Stock) On Hand
	  */
	public void setSOH (BigDecimal SOH);

	/** Get SOH.
	  * Scheduled (Stock) On Hand
	  */
	public BigDecimal getSOH();

    /** Column name SRSFS */
    public static final String COLUMNNAME_SRSFS = "SRSFS";

	/** Set Scheduled RSFS.
	  * Schedule Rest Stock For Sales
	  */
	public void setSRSFS (BigDecimal SRSFS);

	/** Get Scheduled RSFS.
	  * Schedule Rest Stock For Sales
	  */
	public BigDecimal getSRSFS();

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

    /** Column name Stock */
    public static final String COLUMNNAME_Stock = "Stock";

	/** Set Stock	  */
	public void setStock (BigDecimal Stock);

	/** Get Stock	  */
	public BigDecimal getStock();

    /** Column name UNS_MPSProduct_ID */
    public static final String COLUMNNAME_UNS_MPSProduct_ID = "UNS_MPSProduct_ID";

	/** Set MPS Product	  */
	public void setUNS_MPSProduct_ID (int UNS_MPSProduct_ID);

	/** Get MPS Product	  */
	public int getUNS_MPSProduct_ID();

	public com.uns.model.I_UNS_MPSProduct getUNS_MPSProduct() throws RuntimeException;

    /** Column name UNS_MPSProduct_Weekly_ID */
    public static final String COLUMNNAME_UNS_MPSProduct_Weekly_ID = "UNS_MPSProduct_Weekly_ID";

	/** Set MPS Product Weekly	  */
	public void setUNS_MPSProduct_Weekly_ID (int UNS_MPSProduct_Weekly_ID);

	/** Get MPS Product Weekly	  */
	public int getUNS_MPSProduct_Weekly_ID();

    /** Column name UNS_MPSProduct_Weekly_UU */
    public static final String COLUMNNAME_UNS_MPSProduct_Weekly_UU = "UNS_MPSProduct_Weekly_UU";

	/** Set UNS_MPSProduct_Weekly_UU	  */
	public void setUNS_MPSProduct_Weekly_UU (String UNS_MPSProduct_Weekly_UU);

	/** Get UNS_MPSProduct_Weekly_UU	  */
	public String getUNS_MPSProduct_Weekly_UU();

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

    /** Column name Weekly_ID */
    public static final String COLUMNNAME_Weekly_ID = "Weekly_ID";

	/** Set Weekly	  */
	public void setWeekly_ID (int Weekly_ID);

	/** Get Weekly	  */
	public int getWeekly_ID();

    /** Column name WeekNo */
    public static final String COLUMNNAME_WeekNo = "WeekNo";

	/** Set Week No	  */
	public void setWeekNo (int WeekNo);

	/** Get Week No	  */
	public int getWeekNo();
}
