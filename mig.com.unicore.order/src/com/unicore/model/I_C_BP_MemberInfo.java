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

/** Generated Interface for C_BP_MemberInfo
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_C_BP_MemberInfo 
{

    /** TableName=C_BP_MemberInfo */
    public static final String Table_Name = "C_BP_MemberInfo";

    /** AD_Table_ID=1000223 */
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

    /** Column name Birthday */
    public static final String COLUMNNAME_Birthday = "Birthday";

	/** Set Birthday.
	  * Birthday or Anniversary day
	  */
	public void setBirthday (Timestamp Birthday);

	/** Get Birthday.
	  * Birthday or Anniversary day
	  */
	public Timestamp getBirthday();

    /** Column name C_BP_MemberInfo_ID */
    public static final String COLUMNNAME_C_BP_MemberInfo_ID = "C_BP_MemberInfo_ID";

	/** Set C_BP_MemberInfo_ID	  */
	public void setC_BP_MemberInfo_ID (int C_BP_MemberInfo_ID);

	/** Get C_BP_MemberInfo_ID	  */
	public int getC_BP_MemberInfo_ID();

    /** Column name C_BP_MemberInfo_UU */
    public static final String COLUMNNAME_C_BP_MemberInfo_UU = "C_BP_MemberInfo_UU";

	/** Set C_BP_MemberInfo_UU	  */
	public void setC_BP_MemberInfo_UU (String C_BP_MemberInfo_UU);

	/** Get C_BP_MemberInfo_UU	  */
	public String getC_BP_MemberInfo_UU();

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

    /** Column name CurrentStatus */
    public static final String COLUMNNAME_CurrentStatus = "CurrentStatus";

	/** Set CurrentStatus.
	  * Status Uptodate
	  */
	public void setCurrentStatus (String CurrentStatus);

	/** Get CurrentStatus.
	  * Status Uptodate
	  */
	public String getCurrentStatus();

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

    /** Column name NIP */
    public static final String COLUMNNAME_NIP = "NIP";

	/** Set NIP.
	  * The registered identifier of member, bpartner, or employee.
	  */
	public void setNIP (String NIP);

	/** Get NIP.
	  * The registered identifier of member, bpartner, or employee.
	  */
	public String getNIP();

    /** Column name UNS_Golongan_ID */
    public static final String COLUMNNAME_UNS_Golongan_ID = "UNS_Golongan_ID";

	/** Set Golongan ID	  */
	public void setUNS_Golongan_ID (int UNS_Golongan_ID);

	/** Get Golongan ID	  */
	public int getUNS_Golongan_ID();

	public I_UNS_Golongan getUNS_Golongan() throws RuntimeException;

    /** Column name UNS_MemberRegistration_ID */
    public static final String COLUMNNAME_UNS_MemberRegistration_ID = "UNS_MemberRegistration_ID";

	/** Set Member Registration ID	  */
	public void setUNS_MemberRegistration_ID (int UNS_MemberRegistration_ID);

	/** Get Member Registration ID	  */
	public int getUNS_MemberRegistration_ID();

    /** Column name UNS_TMT */
    public static final String COLUMNNAME_UNS_TMT = "UNS_TMT";

	/** Set TMT.
	  * Date of Registered or Unregistred Members
	  */
	public void setUNS_TMT (Timestamp UNS_TMT);

	/** Get TMT.
	  * Date of Registered or Unregistred Members
	  */
	public Timestamp getUNS_TMT();

    /** Column name UNS_Unit */
    public static final String COLUMNNAME_UNS_Unit = "UNS_Unit";

	/** Set Unit	  */
	public void setUNS_Unit (String UNS_Unit);

	/** Get Unit	  */
	public String getUNS_Unit();

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
