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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_LeaveReservedConfig
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_LeaveReservedConfig extends PO implements I_UNS_LeaveReservedConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130530L;

    /** Standard Constructor */
    public X_UNS_LeaveReservedConfig (Properties ctx, int UNS_LeaveReservedConfig_ID, String trxName)
    {
      super (ctx, UNS_LeaveReservedConfig_ID, trxName);
      /** if (UNS_LeaveReservedConfig_ID == 0)
        {
			setC_JobCategory_ID (0);
			setLeaveClaimReserved (0);
			setMaxMaternity (0);
// 2
			setNationality (null);
// WNI
			setUNS_LeaveReservedConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_LeaveReservedConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_LeaveReservedConfig[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_JobCategory getC_JobCategory() throws RuntimeException
    {
		return (org.compiere.model.I_C_JobCategory)MTable.get(getCtx(), org.compiere.model.I_C_JobCategory.Table_Name)
			.getPO(getC_JobCategory_ID(), get_TrxName());	}

	/** Set Position Category.
		@param C_JobCategory_ID 
		Job Position Category
	  */
	public void setC_JobCategory_ID (int C_JobCategory_ID)
	{
		if (C_JobCategory_ID < 1) 
			set_Value (COLUMNNAME_C_JobCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_JobCategory_ID, Integer.valueOf(C_JobCategory_ID));
	}

	/** Get Position Category.
		@return Job Position Category
	  */
	public int getC_JobCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_JobCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Leave Claim Reserved.
		@param LeaveClaimReserved Leave Claim Reserved	  */
	public void setLeaveClaimReserved (int LeaveClaimReserved)
	{
		set_Value (COLUMNNAME_LeaveClaimReserved, Integer.valueOf(LeaveClaimReserved));
	}

	/** Get Leave Claim Reserved.
		@return Leave Claim Reserved	  */
	public int getLeaveClaimReserved () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LeaveClaimReserved);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Maternity Claim Reserved.
		@param MaternityClaimReserved Maternity Claim Reserved	  */
	public void setMaternityClaimReserved (int MaternityClaimReserved)
	{
		set_Value (COLUMNNAME_MaternityClaimReserved, Integer.valueOf(MaternityClaimReserved));
	}

	/** Get Maternity Claim Reserved.
		@return Maternity Claim Reserved	  */
	public int getMaternityClaimReserved () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaternityClaimReserved);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Max. Maternity.
		@param MaxMaternity Max. Maternity	  */
	public void setMaxMaternity (int MaxMaternity)
	{
		set_Value (COLUMNNAME_MaxMaternity, Integer.valueOf(MaxMaternity));
	}

	/** Get Max. Maternity.
		@return Max. Maternity	  */
	public int getMaxMaternity () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxMaternity);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Nationality AD_Reference_ID=1000052 */
	public static final int NATIONALITY_AD_Reference_ID=1000052;
	/** Warga Negara Indonesia = WNI */
	public static final String NATIONALITY_WargaNegaraIndonesia = "WNI";
	/** Warga Negara Asing = WNA */
	public static final String NATIONALITY_WargaNegaraAsing = "WNA";
	/** Set Nationality.
		@param Nationality Nationality	  */
	public void setNationality (String Nationality)
	{

		set_Value (COLUMNNAME_Nationality, Nationality);
	}

	/** Get Nationality.
		@return Nationality	  */
	public String getNationality () 
	{
		return (String)get_Value(COLUMNNAME_Nationality);
	}

	/** Set UNS_LeaveReservedConfig.
		@param UNS_LeaveReservedConfig_ID UNS_LeaveReservedConfig	  */
	public void setUNS_LeaveReservedConfig_ID (int UNS_LeaveReservedConfig_ID)
	{
		if (UNS_LeaveReservedConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_LeaveReservedConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_LeaveReservedConfig_ID, Integer.valueOf(UNS_LeaveReservedConfig_ID));
	}

	/** Get UNS_LeaveReservedConfig.
		@return UNS_LeaveReservedConfig	  */
	public int getUNS_LeaveReservedConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_LeaveReservedConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_LeaveReservedConfig_UU.
		@param UNS_LeaveReservedConfig_UU UNS_LeaveReservedConfig_UU	  */
	public void setUNS_LeaveReservedConfig_UU (String UNS_LeaveReservedConfig_UU)
	{
		set_Value (COLUMNNAME_UNS_LeaveReservedConfig_UU, UNS_LeaveReservedConfig_UU);
	}

	/** Get UNS_LeaveReservedConfig_UU.
		@return UNS_LeaveReservedConfig_UU	  */
	public String getUNS_LeaveReservedConfig_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_LeaveReservedConfig_UU);
	}
}