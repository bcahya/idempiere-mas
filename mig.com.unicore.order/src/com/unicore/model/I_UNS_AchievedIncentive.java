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

/** Generated Interface for UNS_AchievedIncentive
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_AchievedIncentive 
{

    /** TableName=UNS_AchievedIncentive */
    public static final String Table_Name = "UNS_AchievedIncentive";

    /** AD_Table_ID=1000149 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AchievementApr */
    public static final String COLUMNNAME_AchievementApr = "AchievementApr";

	/** Set Achievement April	  */
	public void setAchievementApr (BigDecimal AchievementApr);

	/** Get Achievement April	  */
	public BigDecimal getAchievementApr();

    /** Column name AchievementAug */
    public static final String COLUMNNAME_AchievementAug = "AchievementAug";

	/** Set Achievement August	  */
	public void setAchievementAug (BigDecimal AchievementAug);

	/** Get Achievement August	  */
	public BigDecimal getAchievementAug();

    /** Column name AchievementDec */
    public static final String COLUMNNAME_AchievementDec = "AchievementDec";

	/** Set Achievement December	  */
	public void setAchievementDec (BigDecimal AchievementDec);

	/** Get Achievement December	  */
	public BigDecimal getAchievementDec();

    /** Column name AchievementFeb */
    public static final String COLUMNNAME_AchievementFeb = "AchievementFeb";

	/** Set Achievement February	  */
	public void setAchievementFeb (BigDecimal AchievementFeb);

	/** Get Achievement February	  */
	public BigDecimal getAchievementFeb();

    /** Column name AchievementJan */
    public static final String COLUMNNAME_AchievementJan = "AchievementJan";

	/** Set Achievement January	  */
	public void setAchievementJan (BigDecimal AchievementJan);

	/** Get Achievement January	  */
	public BigDecimal getAchievementJan();

    /** Column name AchievementJul */
    public static final String COLUMNNAME_AchievementJul = "AchievementJul";

	/** Set Achievement July	  */
	public void setAchievementJul (BigDecimal AchievementJul);

	/** Get Achievement July	  */
	public BigDecimal getAchievementJul();

    /** Column name AchievementJun */
    public static final String COLUMNNAME_AchievementJun = "AchievementJun";

	/** Set Achievement June	  */
	public void setAchievementJun (BigDecimal AchievementJun);

	/** Get Achievement June	  */
	public BigDecimal getAchievementJun();

    /** Column name AchievementMarch */
    public static final String COLUMNNAME_AchievementMarch = "AchievementMarch";

	/** Set Achievement March	  */
	public void setAchievementMarch (BigDecimal AchievementMarch);

	/** Get Achievement March	  */
	public BigDecimal getAchievementMarch();

    /** Column name AchievementMay */
    public static final String COLUMNNAME_AchievementMay = "AchievementMay";

	/** Set Achievement May	  */
	public void setAchievementMay (BigDecimal AchievementMay);

	/** Get Achievement May	  */
	public BigDecimal getAchievementMay();

    /** Column name AchievementNov */
    public static final String COLUMNNAME_AchievementNov = "AchievementNov";

	/** Set Achievement November	  */
	public void setAchievementNov (BigDecimal AchievementNov);

	/** Get Achievement November	  */
	public BigDecimal getAchievementNov();

    /** Column name AchievementOct */
    public static final String COLUMNNAME_AchievementOct = "AchievementOct";

	/** Set Achievement October	  */
	public void setAchievementOct (BigDecimal AchievementOct);

	/** Get Achievement October	  */
	public BigDecimal getAchievementOct();

    /** Column name AchievementSep */
    public static final String COLUMNNAME_AchievementSep = "AchievementSep";

	/** Set Achievement September	  */
	public void setAchievementSep (BigDecimal AchievementSep);

	/** Get Achievement September	  */
	public BigDecimal getAchievementSep();

    /** Column name AchievementType */
    public static final String COLUMNNAME_AchievementType = "AchievementType";

	/** Set Achievement Type	  */
	public void setAchievementType (String AchievementType);

	/** Get Achievement Type	  */
	public String getAchievementType();

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

    /** Column name C_Year_ID */
    public static final String COLUMNNAME_C_Year_ID = "C_Year_ID";

	/** Set Year.
	  * Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID);

	/** Get Year.
	  * Calendar Year
	  */
	public int getC_Year_ID();

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException;

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

    /** Column name GeneratePeriodRecord */
    public static final String COLUMNNAME_GeneratePeriodRecord = "GeneratePeriodRecord";

	/** Set Generate Period Record	  */
	public void setGeneratePeriodRecord (String GeneratePeriodRecord);

	/** Get Generate Period Record	  */
	public String getGeneratePeriodRecord();

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

    /** Column name UNS_AchievedIncentive_ID */
    public static final String COLUMNNAME_UNS_AchievedIncentive_ID = "UNS_AchievedIncentive_ID";

	/** Set Achieved Incentive	  */
	public void setUNS_AchievedIncentive_ID (int UNS_AchievedIncentive_ID);

	/** Get Achieved Incentive	  */
	public int getUNS_AchievedIncentive_ID();

    /** Column name UNS_AchievedIncentive_UU */
    public static final String COLUMNNAME_UNS_AchievedIncentive_UU = "UNS_AchievedIncentive_UU";

	/** Set UNS_AchievedIncentive_UU	  */
	public void setUNS_AchievedIncentive_UU (String UNS_AchievedIncentive_UU);

	/** Get UNS_AchievedIncentive_UU	  */
	public String getUNS_AchievedIncentive_UU();

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
