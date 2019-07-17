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

/** Generated Interface for UNS_Forecast_Date
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_Forecast_Date 
{

    /** TableName=UNS_Forecast_Date */
    public static final String Table_Name = "UNS_Forecast_Date";

    /** AD_Table_ID=1000307 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

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

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

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

    /** Column name DecidedQty */
    public static final String COLUMNNAME_DecidedQty = "DecidedQty";

	/** Set Targeted-F Qty (UOM).
	  * Quantity Forecast By Targeted
	  */
	public void setDecidedQty (BigDecimal DecidedQty);

	/** Get Targeted-F Qty (UOM).
	  * Quantity Forecast By Targeted
	  */
	public BigDecimal getDecidedQty();

    /** Column name DecidedQtyMT */
    public static final String COLUMNNAME_DecidedQtyMT = "DecidedQtyMT";

	/** Set Targeted Qty (MT)	  */
	public void setDecidedQtyMT (BigDecimal DecidedQtyMT);

	/** Get Targeted Qty (MT)	  */
	public BigDecimal getDecidedQtyMT();

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

    /** Column name ExpectedCostIDR */
    public static final String COLUMNNAME_ExpectedCostIDR = "ExpectedCostIDR";

	/** Set Expected Cost (IDR)	  */
	public void setExpectedCostIDR (BigDecimal ExpectedCostIDR);

	/** Get Expected Cost (IDR)	  */
	public BigDecimal getExpectedCostIDR();

    /** Column name ExpectedCostSGD */
    public static final String COLUMNNAME_ExpectedCostSGD = "ExpectedCostSGD";

	/** Set Expected Cost (SGD)	  */
	public void setExpectedCostSGD (BigDecimal ExpectedCostSGD);

	/** Get Expected Cost (SGD)	  */
	public BigDecimal getExpectedCostSGD();

    /** Column name ExpectedCostUSD */
    public static final String COLUMNNAME_ExpectedCostUSD = "ExpectedCostUSD";

	/** Set Expected Cost (USD)	  */
	public void setExpectedCostUSD (BigDecimal ExpectedCostUSD);

	/** Get Expected Cost (USD)	  */
	public BigDecimal getExpectedCostUSD();

    /** Column name ExpectedRevenueIDR */
    public static final String COLUMNNAME_ExpectedRevenueIDR = "ExpectedRevenueIDR";

	/** Set Expected Revenue (IDR)	  */
	public void setExpectedRevenueIDR (BigDecimal ExpectedRevenueIDR);

	/** Get Expected Revenue (IDR)	  */
	public BigDecimal getExpectedRevenueIDR();

    /** Column name ExpectedRevenueSGD */
    public static final String COLUMNNAME_ExpectedRevenueSGD = "ExpectedRevenueSGD";

	/** Set Expected Revenue (SGD)	  */
	public void setExpectedRevenueSGD (BigDecimal ExpectedRevenueSGD);

	/** Get Expected Revenue (SGD)	  */
	public BigDecimal getExpectedRevenueSGD();

    /** Column name ExpectedRevenueUSD */
    public static final String COLUMNNAME_ExpectedRevenueUSD = "ExpectedRevenueUSD";

	/** Set Expected Revenue (USD)	  */
	public void setExpectedRevenueUSD (BigDecimal ExpectedRevenueUSD);

	/** Get Expected Revenue (USD)	  */
	public BigDecimal getExpectedRevenueUSD();

    /** Column name ForcastedDate */
    public static final String COLUMNNAME_ForcastedDate = "ForcastedDate";

	/** Set Forcasted Date	  */
	public void setForcastedDate (Timestamp ForcastedDate);

	/** Get Forcasted Date	  */
	public Timestamp getForcastedDate();

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

    /** Column name IsPrimary */
    public static final String COLUMNNAME_IsPrimary = "IsPrimary";

	/** Set Is Primary Output.
	  * Indicates if this is the primary budget
	  */
	public void setIsPrimary (boolean IsPrimary);

	/** Get Is Primary Output.
	  * Indicates if this is the primary budget
	  */
	public boolean isPrimary();

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

    /** Column name MaxCaps */
    public static final String COLUMNNAME_MaxCaps = "MaxCaps";

	/** Set Max. Capaciy	  */
	public void setMaxCaps (BigDecimal MaxCaps);

	/** Get Max. Capaciy	  */
	public BigDecimal getMaxCaps();

    /** Column name Monthly_ID */
    public static final String COLUMNNAME_Monthly_ID = "Monthly_ID";

	/** Set Monthly	  */
	public void setMonthly_ID (int Monthly_ID);

	/** Get Monthly	  */
	public int getMonthly_ID();

	public com.uns.model.I_UNS_Forecast_Date getMonthly() throws RuntimeException;

    /** Column name OptimumCaps */
    public static final String COLUMNNAME_OptimumCaps = "OptimumCaps";

	/** Set Optimum Caps	  */
	public void setOptimumCaps (BigDecimal OptimumCaps);

	/** Get Optimum Caps	  */
	public BigDecimal getOptimumCaps();

    /** Column name QtyMForecast */
    public static final String COLUMNNAME_QtyMForecast = "QtyMForecast";

	/** Set Qty MF.
	  * Qunatity forecast of Marketing by Finish Good Product
	  */
	public void setQtyMForecast (BigDecimal QtyMForecast);

	/** Get Qty MF.
	  * Qunatity forecast of Marketing by Finish Good Product
	  */
	public BigDecimal getQtyMForecast();

    /** Column name QtyMForecastMT */
    public static final String COLUMNNAME_QtyMForecastMT = "QtyMForecastMT";

	/** Set Qty MF (MT).
	  * Qunatity forecast of Marketing by Finish Good Product
	  */
	public void setQtyMForecastMT (BigDecimal QtyMForecastMT);

	/** Get Qty MF (MT).
	  * Qunatity forecast of Marketing by Finish Good Product
	  */
	public BigDecimal getQtyMForecastMT();

    /** Column name QtyMT */
    public static final String COLUMNNAME_QtyMT = "QtyMT";

	/** Set Qty MT	  */
	public void setQtyMT (BigDecimal QtyMT);

	/** Get Qty MT	  */
	public BigDecimal getQtyMT();

    /** Column name QtyUom */
    public static final String COLUMNNAME_QtyUom = "QtyUom";

	/** Set Qty UOM	  */
	public void setQtyUom (BigDecimal QtyUom);

	/** Get Qty UOM	  */
	public BigDecimal getQtyUom();

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

    /** Column name TotalExpectedCostIDR */
    public static final String COLUMNNAME_TotalExpectedCostIDR = "TotalExpectedCostIDR";

	/** Set Total Expected Cost (IDR)	  */
	public void setTotalExpectedCostIDR (BigDecimal TotalExpectedCostIDR);

	/** Get Total Expected Cost (IDR)	  */
	public BigDecimal getTotalExpectedCostIDR();

    /** Column name TotalExpectedCostSGD */
    public static final String COLUMNNAME_TotalExpectedCostSGD = "TotalExpectedCostSGD";

	/** Set Total Expected Cost (SGD)	  */
	public void setTotalExpectedCostSGD (BigDecimal TotalExpectedCostSGD);

	/** Get Total Expected Cost (SGD)	  */
	public BigDecimal getTotalExpectedCostSGD();

    /** Column name TotalExpectedCostUSD */
    public static final String COLUMNNAME_TotalExpectedCostUSD = "TotalExpectedCostUSD";

	/** Set Total Expected Cost (USD)	  */
	public void setTotalExpectedCostUSD (BigDecimal TotalExpectedCostUSD);

	/** Get Total Expected Cost (USD)	  */
	public BigDecimal getTotalExpectedCostUSD();

    /** Column name TotalExpectedRevenueIDR */
    public static final String COLUMNNAME_TotalExpectedRevenueIDR = "TotalExpectedRevenueIDR";

	/** Set Total Expected Revenue (IDR)	  */
	public void setTotalExpectedRevenueIDR (BigDecimal TotalExpectedRevenueIDR);

	/** Get Total Expected Revenue (IDR)	  */
	public BigDecimal getTotalExpectedRevenueIDR();

    /** Column name TotalExpectedRevenueSGD */
    public static final String COLUMNNAME_TotalExpectedRevenueSGD = "TotalExpectedRevenueSGD";

	/** Set Total Expected Revenue (SGD)	  */
	public void setTotalExpectedRevenueSGD (BigDecimal TotalExpectedRevenueSGD);

	/** Get Total Expected Revenue (SGD)	  */
	public BigDecimal getTotalExpectedRevenueSGD();

    /** Column name TotalExpectedRevenueUSD */
    public static final String COLUMNNAME_TotalExpectedRevenueUSD = "TotalExpectedRevenueUSD";

	/** Set Total Expected Revenue (USD)	  */
	public void setTotalExpectedRevenueUSD (BigDecimal TotalExpectedRevenueUSD);

	/** Get Total Expected Revenue (USD)	  */
	public BigDecimal getTotalExpectedRevenueUSD();

    /** Column name UNS_Forecast_Date_ID */
    public static final String COLUMNNAME_UNS_Forecast_Date_ID = "UNS_Forecast_Date_ID";

	/** Set Forecast Date	  */
	public void setUNS_Forecast_Date_ID (int UNS_Forecast_Date_ID);

	/** Get Forecast Date	  */
	public int getUNS_Forecast_Date_ID();

    /** Column name UNS_Forecast_Date_UU */
    public static final String COLUMNNAME_UNS_Forecast_Date_UU = "UNS_Forecast_Date_UU";

	/** Set Forecast Date UU	  */
	public void setUNS_Forecast_Date_UU (String UNS_Forecast_Date_UU);

	/** Get Forecast Date UU	  */
	public String getUNS_Forecast_Date_UU();

    /** Column name UNS_Forecast_Header_ID */
    public static final String COLUMNNAME_UNS_Forecast_Header_ID = "UNS_Forecast_Header_ID";

	/** Set Manufacturing Forecast	  */
	public void setUNS_Forecast_Header_ID (int UNS_Forecast_Header_ID);

	/** Get Manufacturing Forecast	  */
	public int getUNS_Forecast_Header_ID();

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

	public com.uns.model.I_UNS_Forecast_Date getWeekly() throws RuntimeException;

    /** Column name WeekNo */
    public static final String COLUMNNAME_WeekNo = "WeekNo";

	/** Set Week No	  */
	public void setWeekNo (int WeekNo);

	/** Get Week No	  */
	public int getWeekNo();

    /** Column name Yearly_ID */
    public static final String COLUMNNAME_Yearly_ID = "Yearly_ID";

	/** Set Yearly	  */
	public void setYearly_ID (int Yearly_ID);

	/** Get Yearly	  */
	public int getYearly_ID();

	public com.uns.model.I_UNS_Forecast_Date getYearly() throws RuntimeException;
}
