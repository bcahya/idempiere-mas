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

/** Generated Interface for UNS_Mess_Occupants
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_Mess_Occupants 
{

    /** TableName=UNS_Mess_Occupants */
    public static final String Table_Name = "UNS_Mess_Occupants";

    /** AD_Table_ID=1000158 */
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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name EntryDate */
    public static final String COLUMNNAME_EntryDate = "EntryDate";

	/** Set Entry Date	  */
	public void setEntryDate (Timestamp EntryDate);

	/** Get Entry Date	  */
	public Timestamp getEntryDate();

    /** Column name FamEmployee1_ID */
    public static final String COLUMNNAME_FamEmployee1_ID = "FamEmployee1_ID";

	/** Set Family1 (Employee)	  */
	public void setFamEmployee1_ID (int FamEmployee1_ID);

	/** Get Family1 (Employee)	  */
	public int getFamEmployee1_ID();

	public com.uns.model.I_UNS_Employee getFamEmployee1() throws RuntimeException;

    /** Column name FamEmployee2_ID */
    public static final String COLUMNNAME_FamEmployee2_ID = "FamEmployee2_ID";

	/** Set Family2 (Employee)	  */
	public void setFamEmployee2_ID (int FamEmployee2_ID);

	/** Get Family2 (Employee)	  */
	public int getFamEmployee2_ID();

	public com.uns.model.I_UNS_Employee getFamEmployee2() throws RuntimeException;

    /** Column name FamEmployee3_ID */
    public static final String COLUMNNAME_FamEmployee3_ID = "FamEmployee3_ID";

	/** Set Family3 (Employee)	  */
	public void setFamEmployee3_ID (int FamEmployee3_ID);

	/** Get Family3 (Employee)	  */
	public int getFamEmployee3_ID();

	public com.uns.model.I_UNS_Employee getFamEmployee3() throws RuntimeException;

    /** Column name FamEmployee4_ID */
    public static final String COLUMNNAME_FamEmployee4_ID = "FamEmployee4_ID";

	/** Set Family4 (Employee)	  */
	public void setFamEmployee4_ID (int FamEmployee4_ID);

	/** Get Family4 (Employee)	  */
	public int getFamEmployee4_ID();

	public com.uns.model.I_UNS_Employee getFamEmployee4() throws RuntimeException;

    /** Column name FamEmployee5_ID */
    public static final String COLUMNNAME_FamEmployee5_ID = "FamEmployee5_ID";

	/** Set Family5 (Employee)	  */
	public void setFamEmployee5_ID (int FamEmployee5_ID);

	/** Get Family5 (Employee)	  */
	public int getFamEmployee5_ID();

	public com.uns.model.I_UNS_Employee getFamEmployee5() throws RuntimeException;

    /** Column name FamEmployee6_ID */
    public static final String COLUMNNAME_FamEmployee6_ID = "FamEmployee6_ID";

	/** Set Family6 (Employee)	  */
	public void setFamEmployee6_ID (int FamEmployee6_ID);

	/** Get Family6 (Employee)	  */
	public int getFamEmployee6_ID();

	public com.uns.model.I_UNS_Employee getFamEmployee6() throws RuntimeException;

    /** Column name Family1 */
    public static final String COLUMNNAME_Family1 = "Family1";

	/** Set Family 1	  */
	public void setFamily1 (String Family1);

	/** Get Family 1	  */
	public String getFamily1();

    /** Column name Family2 */
    public static final String COLUMNNAME_Family2 = "Family2";

	/** Set Family 2	  */
	public void setFamily2 (String Family2);

	/** Get Family 2	  */
	public String getFamily2();

    /** Column name Family3 */
    public static final String COLUMNNAME_Family3 = "Family3";

	/** Set Family 3	  */
	public void setFamily3 (String Family3);

	/** Get Family 3	  */
	public String getFamily3();

    /** Column name Family4 */
    public static final String COLUMNNAME_Family4 = "Family4";

	/** Set Family 4	  */
	public void setFamily4 (String Family4);

	/** Get Family 4	  */
	public String getFamily4();

    /** Column name Family5 */
    public static final String COLUMNNAME_Family5 = "Family5";

	/** Set Family 5	  */
	public void setFamily5 (String Family5);

	/** Get Family 5	  */
	public String getFamily5();

    /** Column name Family6 */
    public static final String COLUMNNAME_Family6 = "Family6";

	/** Set Family 6	  */
	public void setFamily6 (String Family6);

	/** Get Family 6	  */
	public String getFamily6();

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

    /** Column name IsChargeToUtilitiesUses */
    public static final String COLUMNNAME_IsChargeToUtilitiesUses = "IsChargeToUtilitiesUses";

	/** Set Charge To Utilities Uses	  */
	public void setIsChargeToUtilitiesUses (boolean IsChargeToUtilitiesUses);

	/** Get Charge To Utilities Uses	  */
	public boolean isChargeToUtilitiesUses();

    /** Column name IsFam1Employee */
    public static final String COLUMNNAME_IsFam1Employee = "IsFam1Employee";

	/** Set Famaliy-1 Is Employee.
	  * Famaliy-1 Is Employee
	  */
	public void setIsFam1Employee (boolean IsFam1Employee);

	/** Get Famaliy-1 Is Employee.
	  * Famaliy-1 Is Employee
	  */
	public boolean isFam1Employee();

    /** Column name IsFam2Employee */
    public static final String COLUMNNAME_IsFam2Employee = "IsFam2Employee";

	/** Set Famaliy-2 Is Employee.
	  * Famaliy-2 Is Employee
	  */
	public void setIsFam2Employee (boolean IsFam2Employee);

	/** Get Famaliy-2 Is Employee.
	  * Famaliy-2 Is Employee
	  */
	public boolean isFam2Employee();

    /** Column name IsFam3Employee */
    public static final String COLUMNNAME_IsFam3Employee = "IsFam3Employee";

	/** Set Famaliy-3 Is Employee.
	  * Famaliy-3 Is Employee
	  */
	public void setIsFam3Employee (boolean IsFam3Employee);

	/** Get Famaliy-3 Is Employee.
	  * Famaliy-3 Is Employee
	  */
	public boolean isFam3Employee();

    /** Column name IsFam4Employee */
    public static final String COLUMNNAME_IsFam4Employee = "IsFam4Employee";

	/** Set Famaliy-4 Is Employee.
	  * Famaliy-4 Is Employee
	  */
	public void setIsFam4Employee (boolean IsFam4Employee);

	/** Get Famaliy-4 Is Employee.
	  * Famaliy-4 Is Employee
	  */
	public boolean isFam4Employee();

    /** Column name IsFam5Employee */
    public static final String COLUMNNAME_IsFam5Employee = "IsFam5Employee";

	/** Set Famaliy-5 Is Employee.
	  * Famaliy-5 Is Employee
	  */
	public void setIsFam5Employee (boolean IsFam5Employee);

	/** Get Famaliy-5 Is Employee.
	  * Famaliy-5 Is Employee
	  */
	public boolean isFam5Employee();

    /** Column name IsFam6Employee */
    public static final String COLUMNNAME_IsFam6Employee = "IsFam6Employee";

	/** Set Famaliy-6 Is Employee.
	  * Famaliy-6 Is Employee
	  */
	public void setIsFam6Employee (boolean IsFam6Employee);

	/** Get Famaliy-6 Is Employee.
	  * Famaliy-6 Is Employee
	  */
	public boolean isFam6Employee();

    /** Column name LeaveDate */
    public static final String COLUMNNAME_LeaveDate = "LeaveDate";

	/** Set Leave Date	  */
	public void setLeaveDate (Timestamp LeaveDate);

	/** Get Leave Date	  */
	public Timestamp getLeaveDate();

    /** Column name NonEmpName */
    public static final String COLUMNNAME_NonEmpName = "NonEmpName";

	/** Set Non Employee Name	  */
	public void setNonEmpName (String NonEmpName);

	/** Get Non Employee Name	  */
	public String getNonEmpName();

    /** Column name OccupantsType */
    public static final String COLUMNNAME_OccupantsType = "OccupantsType";

	/** Set Occupants Type	  */
	public void setOccupantsType (String OccupantsType);

	/** Get Occupants Type	  */
	public String getOccupantsType();

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException;

    /** Column name UNS_Mess_Occupants_ID */
    public static final String COLUMNNAME_UNS_Mess_Occupants_ID = "UNS_Mess_Occupants_ID";

	/** Set Mess Occupants	  */
	public void setUNS_Mess_Occupants_ID (int UNS_Mess_Occupants_ID);

	/** Get Mess Occupants	  */
	public int getUNS_Mess_Occupants_ID();

    /** Column name UNS_Mess_Occupants_UU */
    public static final String COLUMNNAME_UNS_Mess_Occupants_UU = "UNS_Mess_Occupants_UU";

	/** Set UNS Mess Occupants UU	  */
	public void setUNS_Mess_Occupants_UU (String UNS_Mess_Occupants_UU);

	/** Get UNS Mess Occupants UU	  */
	public String getUNS_Mess_Occupants_UU();

    /** Column name UNS_Mess_Partition_ID */
    public static final String COLUMNNAME_UNS_Mess_Partition_ID = "UNS_Mess_Partition_ID";

	/** Set Mess Partition 	  */
	public void setUNS_Mess_Partition_ID (int UNS_Mess_Partition_ID);

	/** Get Mess Partition 	  */
	public int getUNS_Mess_Partition_ID();

	public com.uns.model.I_UNS_Mess_Partition getUNS_Mess_Partition() throws RuntimeException;

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
