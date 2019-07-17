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

/** Generated Model for UNS_JointCostGroup
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_JointCostGroup extends PO implements I_UNS_JointCostGroup, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130624L;

    /** Standard Constructor */
    public X_UNS_JointCostGroup (Properties ctx, int UNS_JointCostGroup_ID, String trxName)
    {
      super (ctx, UNS_JointCostGroup_ID, trxName);
      /** if (UNS_JointCostGroup_ID == 0)
        {
			setPostingType (null);
// 'A'
			setUNS_JointCostGroup_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_JointCostGroup (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_UNS_JointCostGroup[")
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** PostingType AD_Reference_ID=125 */
	public static final int POSTINGTYPE_AD_Reference_ID=125;
	/** Actual = A */
	public static final String POSTINGTYPE_Actual = "A";
	/** Budget = B */
	public static final String POSTINGTYPE_Budget = "B";
	/** Commitment = E */
	public static final String POSTINGTYPE_Commitment = "E";
	/** Statistical = S */
	public static final String POSTINGTYPE_Statistical = "S";
	/** Reservation = R */
	public static final String POSTINGTYPE_Reservation = "R";
	/** Set PostingType.
		@param PostingType 
		The type of posted amount for the transaction
	  */
	public void setPostingType (String PostingType)
	{

		set_Value (COLUMNNAME_PostingType, PostingType);
	}

	/** Get PostingType.
		@return The type of posted amount for the transaction
	  */
	public String getPostingType () 
	{
		return (String)get_Value(COLUMNNAME_PostingType);
	}

	/** Set Joint Cost Group.
		@param UNS_JointCostGroup_ID 
		Joint Cost Group
	  */
	public void setUNS_JointCostGroup_ID (int UNS_JointCostGroup_ID)
	{
		if (UNS_JointCostGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_JointCostGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_JointCostGroup_ID, Integer.valueOf(UNS_JointCostGroup_ID));
	}

	/** Get Joint Cost Group.
		@return Joint Cost Group
	  */
	public int getUNS_JointCostGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_JointCostGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_JointCostGroup_UU.
		@param UNS_JointCostGroup_UU 
		UNS_JointCostGroup_UU
	  */
	public void setUNS_JointCostGroup_UU (String UNS_JointCostGroup_UU)
	{
		set_Value (COLUMNNAME_UNS_JointCostGroup_UU, UNS_JointCostGroup_UU);
	}

	/** Get UNS_JointCostGroup_UU.
		@return UNS_JointCostGroup_UU
	  */
	public String getUNS_JointCostGroup_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_JointCostGroup_UU);
	}
}