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

/** Generated Interface for UNS_MP1Form_Detail
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_MP1Form_Detail 
{

    /** TableName=UNS_MP1Form_Detail */
    public static final String Table_Name = "UNS_MP1Form_Detail";

    /** AD_Table_ID=1000393 */
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

    /** Column name Add1 */
    public static final String COLUMNNAME_Add1 = "Add1";

	/** Set 1st Add	  */
	public void setAdd1 (BigDecimal Add1);

	/** Get 1st Add	  */
	public BigDecimal getAdd1();

    /** Column name Add2 */
    public static final String COLUMNNAME_Add2 = "Add2";

	/** Set 2nd Add	  */
	public void setAdd2 (BigDecimal Add2);

	/** Get 2nd Add	  */
	public BigDecimal getAdd2();

    /** Column name Add3 */
    public static final String COLUMNNAME_Add3 = "Add3";

	/** Set 3rd Add	  */
	public void setAdd3 (BigDecimal Add3);

	/** Get 3rd Add	  */
	public BigDecimal getAdd3();

    /** Column name Add4 */
    public static final String COLUMNNAME_Add4 = "Add4";

	/** Set 4th Add	  */
	public void setAdd4 (BigDecimal Add4);

	/** Get 4th Add	  */
	public BigDecimal getAdd4();

    /** Column name Add5 */
    public static final String COLUMNNAME_Add5 = "Add5";

	/** Set 5th Add	  */
	public void setAdd5 (BigDecimal Add5);

	/** Get 5th Add	  */
	public BigDecimal getAdd5();

    /** Column name AdditionalQty */
    public static final String COLUMNNAME_AdditionalQty = "AdditionalQty";

	/** Set Additional Quantity	  */
	public void setAdditionalQty (BigDecimal AdditionalQty);

	/** Get Additional Quantity	  */
	public BigDecimal getAdditionalQty();

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

    /** Column name BadMeat */
    public static final String COLUMNNAME_BadMeat = "BadMeat";

	/** Set Bad Meat	  */
	public void setBadMeat (BigDecimal BadMeat);

	/** Get Bad Meat	  */
	public BigDecimal getBadMeat();

    /** Column name CCSExtra */
    public static final String COLUMNNAME_CCSExtra = "CCSExtra";

	/** Set TMPG Extra	  */
	public void setCCSExtra (BigDecimal CCSExtra);

	/** Get TMPG Extra	  */
	public BigDecimal getCCSExtra();

    /** Column name CoconutShell */
    public static final String COLUMNNAME_CoconutShell = "CoconutShell";

	/** Set Coconut Shell	  */
	public void setCoconutShell (BigDecimal CoconutShell);

	/** Get Coconut Shell	  */
	public BigDecimal getCoconutShell();

    /** Column name CoconutWaterBS */
    public static final String COLUMNNAME_CoconutWaterBS = "CoconutWaterBS";

	/** Set Coconut Water BS	  */
	public void setCoconutWaterBS (BigDecimal CoconutWaterBS);

	/** Get Coconut Water BS	  */
	public BigDecimal getCoconutWaterBS();

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

    /** Column name EmploymentStatus */
    public static final String COLUMNNAME_EmploymentStatus = "EmploymentStatus";

	/** Set Employment Status	  */
	public void setEmploymentStatus (String EmploymentStatus);

	/** Get Employment Status	  */
	public String getEmploymentStatus();

    /** Column name EndingStock */
    public static final String COLUMNNAME_EndingStock = "EndingStock";

	/** Set Ending Stock	  */
	public void setEndingStock (BigDecimal EndingStock);

	/** Get Ending Stock	  */
	public BigDecimal getEndingStock();

    /** Column name FillIn */
    public static final String COLUMNNAME_FillIn = "FillIn";

	/** Set Fill In	  */
	public void setFillIn (BigDecimal FillIn);

	/** Get Fill In	  */
	public BigDecimal getFillIn();

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

    /** Column name KC */
    public static final String COLUMNNAME_KC = "KC";

	/** Set K Cungkil	  */
	public void setKC (BigDecimal KC);

	/** Get K Cungkil	  */
	public BigDecimal getKC();

    /** Column name KCExtra */
    public static final String COLUMNNAME_KCExtra = "KCExtra";

	/** Set KC Extra	  */
	public void setKCExtra (BigDecimal KCExtra);

	/** Get KC Extra	  */
	public BigDecimal getKCExtra();

    /** Column name LastStock */
    public static final String COLUMNNAME_LastStock = "LastStock";

	/** Set Last Stock	  */
	public void setLastStock (BigDecimal LastStock);

	/** Get Last Stock	  */
	public BigDecimal getLastStock();

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

    /** Column name M_MovementLine_ID */
    public static final String COLUMNNAME_M_MovementLine_ID = "M_MovementLine_ID";

	/** Set Move Line.
	  * Inventory Move document Line
	  */
	public void setM_MovementLine_ID (int M_MovementLine_ID);

	/** Get Move Line.
	  * Inventory Move document Line
	  */
	public int getM_MovementLine_ID();

	public org.compiere.model.I_M_MovementLine getM_MovementLine() throws RuntimeException;

    /** Column name Nomer */
    public static final String COLUMNNAME_Nomer = "Nomer";

	/** Set Number	  */
	public void setNomer (int Nomer);

	/** Get Number	  */
	public int getNomer();

    /** Column name OVT_Production_ID */
    public static final String COLUMNNAME_OVT_Production_ID = "OVT_Production_ID";

	/** Set Overtime Production	  */
	public void setOVT_Production_ID (int OVT_Production_ID);

	/** Get Overtime Production	  */
	public int getOVT_Production_ID();

    /** Column name PreReject */
    public static final String COLUMNNAME_PreReject = "PreReject";

	/** Set Pre Reject.
	  * Pre Reject for LKU
	  */
	public void setPreReject (BigDecimal PreReject);

	/** Get Pre Reject.
	  * Pre Reject for LKU
	  */
	public BigDecimal getPreReject();

    /** Column name ProductionQty */
    public static final String COLUMNNAME_ProductionQty = "ProductionQty";

	/** Set Production Qty.
	  * Quantity of products to produce
	  */
	public void setProductionQty (BigDecimal ProductionQty);

	/** Get Production Qty.
	  * Quantity of products to produce
	  */
	public BigDecimal getProductionQty();

    /** Column name RawMeat */
    public static final String COLUMNNAME_RawMeat = "RawMeat";

	/** Set Raw Meat	  */
	public void setRawMeat (BigDecimal RawMeat);

	/** Get Raw Meat	  */
	public BigDecimal getRawMeat();

    /** Column name SkinMeat */
    public static final String COLUMNNAME_SkinMeat = "SkinMeat";

	/** Set Skin Meat	  */
	public void setSkinMeat (BigDecimal SkinMeat);

	/** Get Skin Meat	  */
	public BigDecimal getSkinMeat();

    /** Column name StartingStock */
    public static final String COLUMNNAME_StartingStock = "StartingStock";

	/** Set Starting Stock	  */
	public void setStartingStock (BigDecimal StartingStock);

	/** Get Starting Stock	  */
	public BigDecimal getStartingStock();

    /** Column name UNS_MP1Form_Detail_ID */
    public static final String COLUMNNAME_UNS_MP1Form_Detail_ID = "UNS_MP1Form_Detail_ID";

	/** Set MP1 Detail	  */
	public void setUNS_MP1Form_Detail_ID (int UNS_MP1Form_Detail_ID);

	/** Get MP1 Detail	  */
	public int getUNS_MP1Form_Detail_ID();

    /** Column name UNS_MP1Form_Detail_UU */
    public static final String COLUMNNAME_UNS_MP1Form_Detail_UU = "UNS_MP1Form_Detail_UU";

	/** Set UNS_MP1Form_Detail_UU	  */
	public void setUNS_MP1Form_Detail_UU (String UNS_MP1Form_Detail_UU);

	/** Get UNS_MP1Form_Detail_UU	  */
	public String getUNS_MP1Form_Detail_UU();

    /** Column name UNS_MP1Form_ID */
    public static final String COLUMNNAME_UNS_MP1Form_ID = "UNS_MP1Form_ID";

	/** Set MP1 Form	  */
	public void setUNS_MP1Form_ID (int UNS_MP1Form_ID);

	/** Get MP1 Form	  */
	public int getUNS_MP1Form_ID();

	public com.uns.model.I_UNS_MP1Form getUNS_MP1Form() throws RuntimeException;

    /** Column name UNS_Parrer1_ID */
    public static final String COLUMNNAME_UNS_Parrer1_ID = "UNS_Parrer1_ID";

	/** Set Parrer-1	  */
	public void setUNS_Parrer1_ID (int UNS_Parrer1_ID);

	/** Get Parrer-1	  */
	public int getUNS_Parrer1_ID();

    /** Column name UNS_Parrer2_ID */
    public static final String COLUMNNAME_UNS_Parrer2_ID = "UNS_Parrer2_ID";

	/** Set Parrer-2	  */
	public void setUNS_Parrer2_ID (int UNS_Parrer2_ID);

	/** Get Parrer-2	  */
	public int getUNS_Parrer2_ID();

    /** Column name UNS_Parrer3_ID */
    public static final String COLUMNNAME_UNS_Parrer3_ID = "UNS_Parrer3_ID";

	/** Set Parrer-3	  */
	public void setUNS_Parrer3_ID (int UNS_Parrer3_ID);

	/** Get Parrer-3	  */
	public int getUNS_Parrer3_ID();

    /** Column name UNS_Production_ID */
    public static final String COLUMNNAME_UNS_Production_ID = "UNS_Production_ID";

	/** Set Production	  */
	public void setUNS_Production_ID (int UNS_Production_ID);

	/** Get Production	  */
	public int getUNS_Production_ID();

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Manufacture Resource	  */
	public int getUNS_Resource_ID();

    /** Column name UNS_Sheller_ID */
    public static final String COLUMNNAME_UNS_Sheller_ID = "UNS_Sheller_ID";

	/** Set Sheller	  */
	public void setUNS_Sheller_ID (int UNS_Sheller_ID);

	/** Get Sheller	  */
	public int getUNS_Sheller_ID();

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

    /** Column name WhiteMeatBW */
    public static final String COLUMNNAME_WhiteMeatBW = "WhiteMeatBW";

	/** Set White Meat BW	  */
	public void setWhiteMeatBW (BigDecimal WhiteMeatBW);

	/** Get White Meat BW	  */
	public BigDecimal getWhiteMeatBW();
}
