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

/** Generated Interface for UNS_ProdAllocTrans
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_ProdAllocTrans 
{

    /** TableName=UNS_ProdAllocTrans */
    public static final String Table_Name = "UNS_ProdAllocTrans";

    /** AD_Table_ID=1000108 */
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

    /** Column name Agt */
    public static final String COLUMNNAME_Agt = "Agt";

	/** Set August	  */
	public void setAgt (BigDecimal Agt);

	/** Get August	  */
	public BigDecimal getAgt();

    /** Column name Apr */
    public static final String COLUMNNAME_Apr = "Apr";

	/** Set April	  */
	public void setApr (BigDecimal Apr);

	/** Get April	  */
	public BigDecimal getApr();

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

    /** Column name Dec */
    public static final String COLUMNNAME_Dec = "Dec";

	/** Set December	  */
	public void setDec (BigDecimal Dec);

	/** Get December	  */
	public BigDecimal getDec();

    /** Column name Feb */
    public static final String COLUMNNAME_Feb = "Feb";

	/** Set February	  */
	public void setFeb (BigDecimal Feb);

	/** Get February	  */
	public BigDecimal getFeb();

    /** Column name InputAllocation */
    public static final String COLUMNNAME_InputAllocation = "InputAllocation";

	/** Set Input Allocation	  */
	public void setInputAllocation (BigDecimal InputAllocation);

	/** Get Input Allocation	  */
	public BigDecimal getInputAllocation();

    /** Column name InputRequirement */
    public static final String COLUMNNAME_InputRequirement = "InputRequirement";

	/** Set Input Requirement	  */
	public void setInputRequirement (BigDecimal InputRequirement);

	/** Get Input Requirement	  */
	public BigDecimal getInputRequirement();

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

    /** Column name Jan */
    public static final String COLUMNNAME_Jan = "Jan";

	/** Set January	  */
	public void setJan (BigDecimal Jan);

	/** Get January	  */
	public BigDecimal getJan();

    /** Column name Jul */
    public static final String COLUMNNAME_Jul = "Jul";

	/** Set July	  */
	public void setJul (BigDecimal Jul);

	/** Get July	  */
	public BigDecimal getJul();

    /** Column name Jun */
    public static final String COLUMNNAME_Jun = "Jun";

	/** Set June	  */
	public void setJun (BigDecimal Jun);

	/** Get June	  */
	public BigDecimal getJun();

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

    /** Column name Mar */
    public static final String COLUMNNAME_Mar = "Mar";

	/** Set March	  */
	public void setMar (BigDecimal Mar);

	/** Get March	  */
	public BigDecimal getMar();

    /** Column name May */
    public static final String COLUMNNAME_May = "May";

	/** Set May	  */
	public void setMay (BigDecimal May);

	/** Get May	  */
	public BigDecimal getMay();

    /** Column name NextResource_ID */
    public static final String COLUMNNAME_NextResource_ID = "NextResource_ID";

	/** Set Next Resource	  */
	public void setNextResource_ID (int NextResource_ID);

	/** Get Next Resource	  */
	public int getNextResource_ID();

	public com.uns.model.I_UNS_Resource getNextResource() throws RuntimeException;

    /** Column name Nov */
    public static final String COLUMNNAME_Nov = "Nov";

	/** Set November	  */
	public void setNov (BigDecimal Nov);

	/** Get November	  */
	public BigDecimal getNov();

    /** Column name Oct */
    public static final String COLUMNNAME_Oct = "Oct";

	/** Set October	  */
	public void setOct (BigDecimal Oct);

	/** Get October	  */
	public BigDecimal getOct();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Sep */
    public static final String COLUMNNAME_Sep = "Sep";

	/** Set September	  */
	public void setSep (BigDecimal Sep);

	/** Get September	  */
	public BigDecimal getSep();

    /** Column name UNS_Forecast_Header_ID */
    public static final String COLUMNNAME_UNS_Forecast_Header_ID = "UNS_Forecast_Header_ID";

	/** Set Manufacturing Forecast	  */
	public void setUNS_Forecast_Header_ID (int UNS_Forecast_Header_ID);

	/** Get Manufacturing Forecast	  */
	public int getUNS_Forecast_Header_ID();

	public com.uns.model.I_UNS_Forecast_Header getUNS_Forecast_Header() throws RuntimeException;

    /** Column name UNS_ProdAllocTrans_ID */
    public static final String COLUMNNAME_UNS_ProdAllocTrans_ID = "UNS_ProdAllocTrans_ID";

	/** Set Product Allocation Transition	  */
	public void setUNS_ProdAllocTrans_ID (int UNS_ProdAllocTrans_ID);

	/** Get Product Allocation Transition	  */
	public int getUNS_ProdAllocTrans_ID();

    /** Column name UNS_ProdAllocTrans_UU */
    public static final String COLUMNNAME_UNS_ProdAllocTrans_UU = "UNS_ProdAllocTrans_UU";

	/** Set Product Allocation Transition UU	  */
	public void setUNS_ProdAllocTrans_UU (String UNS_ProdAllocTrans_UU);

	/** Get Product Allocation Transition UU	  */
	public String getUNS_ProdAllocTrans_UU();

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Manufacture Resource	  */
	public int getUNS_Resource_ID();

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException;

    /** Column name UNS_Resource_Transition_ID */
    public static final String COLUMNNAME_UNS_Resource_Transition_ID = "UNS_Resource_Transition_ID";

	/** Set Manufacturing Resource Transition	  */
	public void setUNS_Resource_Transition_ID (int UNS_Resource_Transition_ID);

	/** Get Manufacturing Resource Transition	  */
	public int getUNS_Resource_Transition_ID();

	public com.uns.model.I_UNS_Resource_Transition getUNS_Resource_Transition() throws RuntimeException;

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
