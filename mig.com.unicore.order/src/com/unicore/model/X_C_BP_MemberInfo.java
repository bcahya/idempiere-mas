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
package com.unicore.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for C_BP_MemberInfo
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_C_BP_MemberInfo extends PO implements I_C_BP_MemberInfo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150331L;

    /** Standard Constructor */
    public X_C_BP_MemberInfo (Properties ctx, int C_BP_MemberInfo_ID, String trxName)
    {
      super (ctx, C_BP_MemberInfo_ID, trxName);
      /** if (C_BP_MemberInfo_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_BP_MemberInfo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_BP_MemberInfo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Birthday.
		@param Birthday 
		Birthday or Anniversary day
	  */
	public void setBirthday (Timestamp Birthday)
	{
		set_Value (COLUMNNAME_Birthday, Birthday);
	}

	/** Get Birthday.
		@return Birthday or Anniversary day
	  */
	public Timestamp getBirthday () 
	{
		return (Timestamp)get_Value(COLUMNNAME_Birthday);
	}

	/** Set C_BP_MemberInfo_ID.
		@param C_BP_MemberInfo_ID C_BP_MemberInfo_ID	  */
	public void setC_BP_MemberInfo_ID (int C_BP_MemberInfo_ID)
	{
		if (C_BP_MemberInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_MemberInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_MemberInfo_ID, Integer.valueOf(C_BP_MemberInfo_ID));
	}

	/** Get C_BP_MemberInfo_ID.
		@return C_BP_MemberInfo_ID	  */
	public int getC_BP_MemberInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_MemberInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_BP_MemberInfo_UU.
		@param C_BP_MemberInfo_UU C_BP_MemberInfo_UU	  */
	public void setC_BP_MemberInfo_UU (String C_BP_MemberInfo_UU)
	{
		set_ValueNoCheck (COLUMNNAME_C_BP_MemberInfo_UU, C_BP_MemberInfo_UU);
	}

	/** Get C_BP_MemberInfo_UU.
		@return C_BP_MemberInfo_UU	  */
	public String getC_BP_MemberInfo_UU () 
	{
		return (String)get_Value(COLUMNNAME_C_BP_MemberInfo_UU);
	}

	/** Registered = RGS */
	public static final String CURRENTSTATUS_Registered = "RGS";
	/** Unregistered = UGS */
	public static final String CURRENTSTATUS_Unregistered = "UGS";
	/** Reregistered = RRS */
	public static final String CURRENTSTATUS_Reregistered = "RRS";
	/** None = NN */
	public static final String CURRENTSTATUS_None = "NN";
	/** Set CurrentStatus.
		@param CurrentStatus 
		Status Uptodate
	  */
	public void setCurrentStatus (String CurrentStatus)
	{

		set_Value (COLUMNNAME_CurrentStatus, CurrentStatus);
	}

	/** Get CurrentStatus.
		@return Status Uptodate
	  */
	public String getCurrentStatus () 
	{
		return (String)get_Value(COLUMNNAME_CurrentStatus);
	}

	/** Set NIP.
		@param NIP 
		The registered identifier of member, bpartner, or employee.
	  */
	public void setNIP (String NIP)
	{
		set_Value (COLUMNNAME_NIP, NIP);
	}

	/** Get NIP.
		@return The registered identifier of member, bpartner, or employee.
	  */
	public String getNIP () 
	{
		return (String)get_Value(COLUMNNAME_NIP);
	}

	public I_UNS_Golongan getUNS_Golongan() throws RuntimeException
    {
		return (I_UNS_Golongan)MTable.get(getCtx(), I_UNS_Golongan.Table_Name)
			.getPO(getUNS_Golongan_ID(), get_TrxName());	}

	/** Set Golongan ID.
		@param UNS_Golongan_ID Golongan ID	  */
	public void setUNS_Golongan_ID (int UNS_Golongan_ID)
	{
		if (UNS_Golongan_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Golongan_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Golongan_ID, Integer.valueOf(UNS_Golongan_ID));
	}

	/** Get Golongan ID.
		@return Golongan ID	  */
	public int getUNS_Golongan_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Golongan_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Member Registration ID.
		@param UNS_MemberRegistration_ID Member Registration ID	  */
	public void setUNS_MemberRegistration_ID (int UNS_MemberRegistration_ID)
	{
		if (UNS_MemberRegistration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MemberRegistration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MemberRegistration_ID, Integer.valueOf(UNS_MemberRegistration_ID));
	}

	/** Get Member Registration ID.
		@return Member Registration ID	  */
	public int getUNS_MemberRegistration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MemberRegistration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TMT.
		@param UNS_TMT 
		Date of Registered or Unregistred Members
	  */
	public void setUNS_TMT (Timestamp UNS_TMT)
	{
		set_Value (COLUMNNAME_UNS_TMT, UNS_TMT);
	}

	/** Get TMT.
		@return Date of Registered or Unregistred Members
	  */
	public Timestamp getUNS_TMT () 
	{
		return (Timestamp)get_Value(COLUMNNAME_UNS_TMT);
	}

	/** Set Unit.
		@param UNS_Unit Unit	  */
	public void setUNS_Unit (String UNS_Unit)
	{
		set_Value (COLUMNNAME_UNS_Unit, UNS_Unit);
	}

	/** Get Unit.
		@return Unit	  */
	public String getUNS_Unit () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Unit);
	}
}