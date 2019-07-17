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

/** Generated Interface for UNS_Resource_InOut
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Resource_InOut 
{

    /** TableName=UNS_Resource_InOut */
    public static final String Table_Name = "UNS_Resource_InOut";

    /** AD_Table_ID=1000042 */
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

    /** Column name ActualMaxCaps */
    public static final String COLUMNNAME_ActualMaxCaps = "ActualMaxCaps";

	/** Set Actual Max Caps	  */
	public void setActualMaxCaps (BigDecimal ActualMaxCaps);

	/** Get Actual Max Caps	  */
	public BigDecimal getActualMaxCaps();

    /** Column name ActualMaxCapsMT */
    public static final String COLUMNNAME_ActualMaxCapsMT = "ActualMaxCapsMT";

	/** Set Actual Max Capacities (MT)	  */
	public void setActualMaxCapsMT (BigDecimal ActualMaxCapsMT);

	/** Get Actual Max Capacities (MT)	  */
	public BigDecimal getActualMaxCapsMT();

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

    /** Column name DailyAvgProductionHours */
    public static final String COLUMNNAME_DailyAvgProductionHours = "DailyAvgProductionHours";

	/** Set Daily Avg Production Hours	  */
	public void setDailyAvgProductionHours (BigDecimal DailyAvgProductionHours);

	/** Get Daily Avg Production Hours	  */
	public BigDecimal getDailyAvgProductionHours();

    /** Column name Day1ProductionHours */
    public static final String COLUMNNAME_Day1ProductionHours = "Day1ProductionHours";

	/** Set Sunday Work Hours	  */
	public void setDay1ProductionHours (BigDecimal Day1ProductionHours);

	/** Get Sunday Work Hours	  */
	public BigDecimal getDay1ProductionHours();

    /** Column name Day2ProductionHours */
    public static final String COLUMNNAME_Day2ProductionHours = "Day2ProductionHours";

	/** Set Monday Production Hours	  */
	public void setDay2ProductionHours (BigDecimal Day2ProductionHours);

	/** Get Monday Production Hours	  */
	public BigDecimal getDay2ProductionHours();

    /** Column name Day3ProductionHours */
    public static final String COLUMNNAME_Day3ProductionHours = "Day3ProductionHours";

	/** Set Tuesday Production Hours	  */
	public void setDay3ProductionHours (BigDecimal Day3ProductionHours);

	/** Get Tuesday Production Hours	  */
	public BigDecimal getDay3ProductionHours();

    /** Column name Day4ProductionHours */
    public static final String COLUMNNAME_Day4ProductionHours = "Day4ProductionHours";

	/** Set Wednesday Production Hours	  */
	public void setDay4ProductionHours (BigDecimal Day4ProductionHours);

	/** Get Wednesday Production Hours	  */
	public BigDecimal getDay4ProductionHours();

    /** Column name Day5ProductionHours */
    public static final String COLUMNNAME_Day5ProductionHours = "Day5ProductionHours";

	/** Set Thursday Production Hours	  */
	public void setDay5ProductionHours (BigDecimal Day5ProductionHours);

	/** Get Thursday Production Hours	  */
	public BigDecimal getDay5ProductionHours();

    /** Column name Day6ProductionHours */
    public static final String COLUMNNAME_Day6ProductionHours = "Day6ProductionHours";

	/** Set Friday Production Hours	  */
	public void setDay6ProductionHours (BigDecimal Day6ProductionHours);

	/** Get Friday Production Hours	  */
	public BigDecimal getDay6ProductionHours();

    /** Column name Day7ProductionHours */
    public static final String COLUMNNAME_Day7ProductionHours = "Day7ProductionHours";

	/** Set Saturday Production Hours	  */
	public void setDay7ProductionHours (BigDecimal Day7ProductionHours);

	/** Get Saturday Production Hours	  */
	public BigDecimal getDay7ProductionHours();

    /** Column name ExpectedProductionCost */
    public static final String COLUMNNAME_ExpectedProductionCost = "ExpectedProductionCost";

	/** Set Expected Production Cost	  */
	public void setExpectedProductionCost (BigDecimal ExpectedProductionCost);

	/** Get Expected Production Cost	  */
	public BigDecimal getExpectedProductionCost();

    /** Column name InOutType */
    public static final String COLUMNNAME_InOutType = "InOutType";

	/** Set InOut Type	  */
	public void setInOutType (String InOutType);

	/** Get InOut Type	  */
	public String getInOutType();

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

	/** Set Primary.
	  * Indicates if this is the primary budget
	  */
	public void setIsPrimary (boolean IsPrimary);

	/** Get Primary.
	  * Indicates if this is the primary budget
	  */
	public boolean isPrimary();

    /** Column name IsSingleAttribute */
    public static final String COLUMNNAME_IsSingleAttribute = "IsSingleAttribute";

	/** Set Is Single Attribute.
	  * To indicate that this product's stock is single ASI (One Product's Stock One ASI)
	  */
	public void setIsSingleAttribute (boolean IsSingleAttribute);

	/** Get Is Single Attribute.
	  * To indicate that this product's stock is single ASI (One Product's Stock One ASI)
	  */
	public boolean isSingleAttribute();

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/** Set Locator.
	  * Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID);

	/** Get Locator.
	  * Warehouse Locator
	  */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException;

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

    /** Column name MaxCapsMT */
    public static final String COLUMNNAME_MaxCapsMT = "MaxCapsMT";

	/** Set Max Caps (MT)	  */
	public void setMaxCapsMT (BigDecimal MaxCapsMT);

	/** Get Max Caps (MT)	  */
	public BigDecimal getMaxCapsMT();

    /** Column name OptimumCaps */
    public static final String COLUMNNAME_OptimumCaps = "OptimumCaps";

	/** Set Optimum Caps	  */
	public void setOptimumCaps (BigDecimal OptimumCaps);

	/** Get Optimum Caps	  */
	public BigDecimal getOptimumCaps();

    /** Column name OptimumCapsMT */
    public static final String COLUMNNAME_OptimumCapsMT = "OptimumCapsMT";

	/** Set Optimum Caps MT	  */
	public void setOptimumCapsMT (BigDecimal OptimumCapsMT);

	/** Get Optimum Caps MT	  */
	public BigDecimal getOptimumCapsMT();

    /** Column name OutputLinkName */
    public static final String COLUMNNAME_OutputLinkName = "OutputLinkName";

	/** Set Output Link Name.
	  * The value of product that is set as it's Output Link.
	  */
	public void setOutputLinkName (String OutputLinkName);

	/** Get Output Link Name.
	  * The value of product that is set as it's Output Link.
	  */
	public String getOutputLinkName();

    /** Column name OutputType */
    public static final String COLUMNNAME_OutputType = "OutputType";

	/** Set Output Type	  */
	public void setOutputType (String OutputType);

	/** Get Output Type	  */
	public String getOutputType();

    /** Column name QAMonitoring */
    public static final String COLUMNNAME_QAMonitoring = "QAMonitoring";

	/** Set QA Monitoring.
	  * This product must be monitoring by QA before next process.
	  */
	public void setQAMonitoring (boolean QAMonitoring);

	/** Get QA Monitoring.
	  * This product must be monitoring by QA before next process.
	  */
	public boolean isQAMonitoring();

    /** Column name ResourceName */
    public static final String COLUMNNAME_ResourceName = "ResourceName";

	/** Set Resource Name.
	  * The name of this linked resource id.
	  */
	public void setResourceName (String ResourceName);

	/** Get Resource Name.
	  * The name of this linked resource id.
	  */
	public String getResourceName();

    /** Column name ToBeForecast */
    public static final String COLUMNNAME_ToBeForecast = "ToBeForecast";

	/** Set ToBeForecast	  */
	public void setToBeForecast (boolean ToBeForecast);

	/** Get ToBeForecast	  */
	public boolean isToBeForecast();

    /** Column name UNS_OutputLink_ID */
    public static final String COLUMNNAME_UNS_OutputLink_ID = "UNS_OutputLink_ID";

	/** Set Output Link.
	  * The resource output this output linked to.
	  */
	public void setUNS_OutputLink_ID (int UNS_OutputLink_ID);

	/** Get Output Link.
	  * The resource output this output linked to.
	  */
	public int getUNS_OutputLink_ID();

	public com.uns.model.I_UNS_Resource_InOut getUNS_OutputLink() throws RuntimeException;

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Manufacture Resource	  */
	public int getUNS_Resource_ID();

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException;

    /** Column name UNS_Resource_InOut_ID */
    public static final String COLUMNNAME_UNS_Resource_InOut_ID = "UNS_Resource_InOut_ID";

	/** Set Manufacture Resource InOut	  */
	public void setUNS_Resource_InOut_ID (int UNS_Resource_InOut_ID);

	/** Get Manufacture Resource InOut	  */
	public int getUNS_Resource_InOut_ID();

    /** Column name UNS_Resource_InOut_UU */
    public static final String COLUMNNAME_UNS_Resource_InOut_UU = "UNS_Resource_InOut_UU";

	/** Set UNS_Resource_InOut_UU	  */
	public void setUNS_Resource_InOut_UU (String UNS_Resource_InOut_UU);

	/** Get UNS_Resource_InOut_UU	  */
	public String getUNS_Resource_InOut_UU();

    /** Column name UNS_Resource_Transition_ID */
    public static final String COLUMNNAME_UNS_Resource_Transition_ID = "UNS_Resource_Transition_ID";

	/** Set Resource Transition	  */
	public void setUNS_Resource_Transition_ID (int UNS_Resource_Transition_ID);

	/** Get Resource Transition	  */
	public int getUNS_Resource_Transition_ID();

    /** Column name UOMCaps_ID */
    public static final String COLUMNNAME_UOMCaps_ID = "UOMCaps_ID";

	/** Set UOM Capaciy	  */
	public void setUOMCaps_ID (int UOMCaps_ID);

	/** Get UOM Capaciy	  */
	public int getUOMCaps_ID();

	public org.compiere.model.I_C_UOM getUOMCaps() throws RuntimeException;

    /** Column name UOMTime_ID */
    public static final String COLUMNNAME_UOMTime_ID = "UOMTime_ID";

	/** Set UOM Time	  */
	public void setUOMTime_ID (int UOMTime_ID);

	/** Get UOM Time	  */
	public int getUOMTime_ID();

	public org.compiere.model.I_C_UOM getUOMTime() throws RuntimeException;

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();
}
