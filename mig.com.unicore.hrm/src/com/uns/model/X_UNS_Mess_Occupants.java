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
/** Generated Model - DO NOT CHANGE */
package com.uns.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_Mess_Occupants
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Mess_Occupants extends PO implements I_UNS_Mess_Occupants, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130513L;

    /** Standard Constructor */
    public X_UNS_Mess_Occupants (Properties ctx, int UNS_Mess_Occupants_ID, String trxName)
    {
      super (ctx, UNS_Mess_Occupants_ID, trxName);
      /** if (UNS_Mess_Occupants_ID == 0)
        {
			setIsChargeToUtilitiesUses (false);
// N
			setIsFam1Employee (false);
// N
			setUNS_Mess_Occupants_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Mess_Occupants (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_UNS_Mess_Occupants[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Entry Date.
		@param EntryDate Entry Date	  */
	public void setEntryDate (Timestamp EntryDate)
	{
		set_Value (COLUMNNAME_EntryDate, EntryDate);
	}

	/** Get Entry Date.
		@return Entry Date	  */
	public Timestamp getEntryDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EntryDate);
	}

	public com.uns.model.I_UNS_Employee getFamEmployee1() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getFamEmployee1_ID(), get_TrxName());	}

	/** Set Family1 (Employee).
		@param FamEmployee1_ID Family1 (Employee)	  */
	public void setFamEmployee1_ID (int FamEmployee1_ID)
	{
		if (FamEmployee1_ID < 1) 
			set_Value (COLUMNNAME_FamEmployee1_ID, null);
		else 
			set_Value (COLUMNNAME_FamEmployee1_ID, Integer.valueOf(FamEmployee1_ID));
	}

	/** Get Family1 (Employee).
		@return Family1 (Employee)	  */
	public int getFamEmployee1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FamEmployee1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Employee getFamEmployee2() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getFamEmployee2_ID(), get_TrxName());	}

	/** Set Family2 (Employee).
		@param FamEmployee2_ID Family2 (Employee)	  */
	public void setFamEmployee2_ID (int FamEmployee2_ID)
	{
		if (FamEmployee2_ID < 1) 
			set_Value (COLUMNNAME_FamEmployee2_ID, null);
		else 
			set_Value (COLUMNNAME_FamEmployee2_ID, Integer.valueOf(FamEmployee2_ID));
	}

	/** Get Family2 (Employee).
		@return Family2 (Employee)	  */
	public int getFamEmployee2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FamEmployee2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Employee getFamEmployee3() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getFamEmployee3_ID(), get_TrxName());	}

	/** Set Family3 (Employee).
		@param FamEmployee3_ID Family3 (Employee)	  */
	public void setFamEmployee3_ID (int FamEmployee3_ID)
	{
		if (FamEmployee3_ID < 1) 
			set_Value (COLUMNNAME_FamEmployee3_ID, null);
		else 
			set_Value (COLUMNNAME_FamEmployee3_ID, Integer.valueOf(FamEmployee3_ID));
	}

	/** Get Family3 (Employee).
		@return Family3 (Employee)	  */
	public int getFamEmployee3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FamEmployee3_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Employee getFamEmployee4() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getFamEmployee4_ID(), get_TrxName());	}

	/** Set Family4 (Employee).
		@param FamEmployee4_ID Family4 (Employee)	  */
	public void setFamEmployee4_ID (int FamEmployee4_ID)
	{
		if (FamEmployee4_ID < 1) 
			set_Value (COLUMNNAME_FamEmployee4_ID, null);
		else 
			set_Value (COLUMNNAME_FamEmployee4_ID, Integer.valueOf(FamEmployee4_ID));
	}

	/** Get Family4 (Employee).
		@return Family4 (Employee)	  */
	public int getFamEmployee4_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FamEmployee4_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Employee getFamEmployee5() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getFamEmployee5_ID(), get_TrxName());	}

	/** Set Family5 (Employee).
		@param FamEmployee5_ID Family5 (Employee)	  */
	public void setFamEmployee5_ID (int FamEmployee5_ID)
	{
		if (FamEmployee5_ID < 1) 
			set_Value (COLUMNNAME_FamEmployee5_ID, null);
		else 
			set_Value (COLUMNNAME_FamEmployee5_ID, Integer.valueOf(FamEmployee5_ID));
	}

	/** Get Family5 (Employee).
		@return Family5 (Employee)	  */
	public int getFamEmployee5_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FamEmployee5_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Employee getFamEmployee6() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getFamEmployee6_ID(), get_TrxName());	}

	/** Set Family6 (Employee).
		@param FamEmployee6_ID Family6 (Employee)	  */
	public void setFamEmployee6_ID (int FamEmployee6_ID)
	{
		if (FamEmployee6_ID < 1) 
			set_Value (COLUMNNAME_FamEmployee6_ID, null);
		else 
			set_Value (COLUMNNAME_FamEmployee6_ID, Integer.valueOf(FamEmployee6_ID));
	}

	/** Get Family6 (Employee).
		@return Family6 (Employee)	  */
	public int getFamEmployee6_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FamEmployee6_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Family 1.
		@param Family1 Family 1	  */
	public void setFamily1 (String Family1)
	{
		set_Value (COLUMNNAME_Family1, Family1);
	}

	/** Get Family 1.
		@return Family 1	  */
	public String getFamily1 () 
	{
		return (String)get_Value(COLUMNNAME_Family1);
	}

	/** Set Family 2.
		@param Family2 Family 2	  */
	public void setFamily2 (String Family2)
	{
		set_Value (COLUMNNAME_Family2, Family2);
	}

	/** Get Family 2.
		@return Family 2	  */
	public String getFamily2 () 
	{
		return (String)get_Value(COLUMNNAME_Family2);
	}

	/** Set Family 3.
		@param Family3 Family 3	  */
	public void setFamily3 (String Family3)
	{
		set_Value (COLUMNNAME_Family3, Family3);
	}

	/** Get Family 3.
		@return Family 3	  */
	public String getFamily3 () 
	{
		return (String)get_Value(COLUMNNAME_Family3);
	}

	/** Set Family 4.
		@param Family4 Family 4	  */
	public void setFamily4 (String Family4)
	{
		set_Value (COLUMNNAME_Family4, Family4);
	}

	/** Get Family 4.
		@return Family 4	  */
	public String getFamily4 () 
	{
		return (String)get_Value(COLUMNNAME_Family4);
	}

	/** Set Family 5.
		@param Family5 Family 5	  */
	public void setFamily5 (String Family5)
	{
		set_Value (COLUMNNAME_Family5, Family5);
	}

	/** Get Family 5.
		@return Family 5	  */
	public String getFamily5 () 
	{
		return (String)get_Value(COLUMNNAME_Family5);
	}

	/** Set Family 6.
		@param Family6 Family 6	  */
	public void setFamily6 (String Family6)
	{
		set_Value (COLUMNNAME_Family6, Family6);
	}

	/** Get Family 6.
		@return Family 6	  */
	public String getFamily6 () 
	{
		return (String)get_Value(COLUMNNAME_Family6);
	}

	/** Set Charge To Utilities Uses.
		@param IsChargeToUtilitiesUses Charge To Utilities Uses	  */
	public void setIsChargeToUtilitiesUses (boolean IsChargeToUtilitiesUses)
	{
		set_Value (COLUMNNAME_IsChargeToUtilitiesUses, Boolean.valueOf(IsChargeToUtilitiesUses));
	}

	/** Get Charge To Utilities Uses.
		@return Charge To Utilities Uses	  */
	public boolean isChargeToUtilitiesUses () 
	{
		Object oo = get_Value(COLUMNNAME_IsChargeToUtilitiesUses);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Famaliy-1 Is Employee.
		@param IsFam1Employee 
		Famaliy-1 Is Employee
	  */
	public void setIsFam1Employee (boolean IsFam1Employee)
	{
		set_Value (COLUMNNAME_IsFam1Employee, Boolean.valueOf(IsFam1Employee));
	}

	/** Get Famaliy-1 Is Employee.
		@return Famaliy-1 Is Employee
	  */
	public boolean isFam1Employee () 
	{
		Object oo = get_Value(COLUMNNAME_IsFam1Employee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Famaliy-2 Is Employee.
		@param IsFam2Employee 
		Famaliy-2 Is Employee
	  */
	public void setIsFam2Employee (boolean IsFam2Employee)
	{
		set_Value (COLUMNNAME_IsFam2Employee, Boolean.valueOf(IsFam2Employee));
	}

	/** Get Famaliy-2 Is Employee.
		@return Famaliy-2 Is Employee
	  */
	public boolean isFam2Employee () 
	{
		Object oo = get_Value(COLUMNNAME_IsFam2Employee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Famaliy-3 Is Employee.
		@param IsFam3Employee 
		Famaliy-3 Is Employee
	  */
	public void setIsFam3Employee (boolean IsFam3Employee)
	{
		set_Value (COLUMNNAME_IsFam3Employee, Boolean.valueOf(IsFam3Employee));
	}

	/** Get Famaliy-3 Is Employee.
		@return Famaliy-3 Is Employee
	  */
	public boolean isFam3Employee () 
	{
		Object oo = get_Value(COLUMNNAME_IsFam3Employee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Famaliy-4 Is Employee.
		@param IsFam4Employee 
		Famaliy-4 Is Employee
	  */
	public void setIsFam4Employee (boolean IsFam4Employee)
	{
		set_Value (COLUMNNAME_IsFam4Employee, Boolean.valueOf(IsFam4Employee));
	}

	/** Get Famaliy-4 Is Employee.
		@return Famaliy-4 Is Employee
	  */
	public boolean isFam4Employee () 
	{
		Object oo = get_Value(COLUMNNAME_IsFam4Employee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Famaliy-5 Is Employee.
		@param IsFam5Employee 
		Famaliy-5 Is Employee
	  */
	public void setIsFam5Employee (boolean IsFam5Employee)
	{
		set_Value (COLUMNNAME_IsFam5Employee, Boolean.valueOf(IsFam5Employee));
	}

	/** Get Famaliy-5 Is Employee.
		@return Famaliy-5 Is Employee
	  */
	public boolean isFam5Employee () 
	{
		Object oo = get_Value(COLUMNNAME_IsFam5Employee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Famaliy-6 Is Employee.
		@param IsFam6Employee 
		Famaliy-6 Is Employee
	  */
	public void setIsFam6Employee (boolean IsFam6Employee)
	{
		set_Value (COLUMNNAME_IsFam6Employee, Boolean.valueOf(IsFam6Employee));
	}

	/** Get Famaliy-6 Is Employee.
		@return Famaliy-6 Is Employee
	  */
	public boolean isFam6Employee () 
	{
		Object oo = get_Value(COLUMNNAME_IsFam6Employee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Leave Date.
		@param LeaveDate Leave Date	  */
	public void setLeaveDate (Timestamp LeaveDate)
	{
		set_Value (COLUMNNAME_LeaveDate, LeaveDate);
	}

	/** Get Leave Date.
		@return Leave Date	  */
	public Timestamp getLeaveDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LeaveDate);
	}

	/** Set Non Employee Name.
		@param NonEmpName Non Employee Name	  */
	public void setNonEmpName (String NonEmpName)
	{
		set_Value (COLUMNNAME_NonEmpName, NonEmpName);
	}

	/** Get Non Employee Name.
		@return Non Employee Name	  */
	public String getNonEmpName () 
	{
		return (String)get_Value(COLUMNNAME_NonEmpName);
	}

	/** OccupantsType AD_Reference_ID=1000082 */
	public static final int OCCUPANTSTYPE_AD_Reference_ID=1000082;
	/** Employee = 01 */
	public static final String OCCUPANTSTYPE_Employee = "01";
	/** Non Employee = 02 */
	public static final String OCCUPANTSTYPE_NonEmployee = "02";
	/** Set Occupants Type.
		@param OccupantsType Occupants Type	  */
	public void setOccupantsType (String OccupantsType)
	{

		set_Value (COLUMNNAME_OccupantsType, OccupantsType);
	}

	/** Get Occupants Type.
		@return Occupants Type	  */
	public String getOccupantsType () 
	{
		return (String)get_Value(COLUMNNAME_OccupantsType);
	}

	public com.uns.model.I_UNS_Employee getUNS_Employee() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Employee)MTable.get(getCtx(), com.uns.model.I_UNS_Employee.Table_Name)
			.getPO(getUNS_Employee_ID(), get_TrxName());	}

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_Value (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getUNS_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mess Occupants.
		@param UNS_Mess_Occupants_ID Mess Occupants	  */
	public void setUNS_Mess_Occupants_ID (int UNS_Mess_Occupants_ID)
	{
		if (UNS_Mess_Occupants_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Mess_Occupants_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Mess_Occupants_ID, Integer.valueOf(UNS_Mess_Occupants_ID));
	}

	/** Get Mess Occupants.
		@return Mess Occupants	  */
	public int getUNS_Mess_Occupants_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Mess_Occupants_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS Mess Occupants UU.
		@param UNS_Mess_Occupants_UU UNS Mess Occupants UU	  */
	public void setUNS_Mess_Occupants_UU (String UNS_Mess_Occupants_UU)
	{
		set_Value (COLUMNNAME_UNS_Mess_Occupants_UU, UNS_Mess_Occupants_UU);
	}

	/** Get UNS Mess Occupants UU.
		@return UNS Mess Occupants UU	  */
	public String getUNS_Mess_Occupants_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Mess_Occupants_UU);
	}

	public com.uns.model.I_UNS_Mess_Partition getUNS_Mess_Partition() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Mess_Partition)MTable.get(getCtx(), com.uns.model.I_UNS_Mess_Partition.Table_Name)
			.getPO(getUNS_Mess_Partition_ID(), get_TrxName());	}

	/** Set Mess Partition .
		@param UNS_Mess_Partition_ID Mess Partition 	  */
	public void setUNS_Mess_Partition_ID (int UNS_Mess_Partition_ID)
	{
		if (UNS_Mess_Partition_ID < 1) 
			set_Value (COLUMNNAME_UNS_Mess_Partition_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Mess_Partition_ID, Integer.valueOf(UNS_Mess_Partition_ID));
	}

	/** Get Mess Partition .
		@return Mess Partition 	  */
	public int getUNS_Mess_Partition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Mess_Partition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}