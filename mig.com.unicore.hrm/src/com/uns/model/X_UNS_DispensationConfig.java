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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_DispensationConfig
 *  @author iDempiere (generated) 
 *  @version Release 2.0 - $Id$ */
public class X_UNS_DispensationConfig extends PO implements I_UNS_DispensationConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141004L;

    /** Standard Constructor */
    public X_UNS_DispensationConfig (Properties ctx, int UNS_DispensationConfig_ID, String trxName)
    {
      super (ctx, UNS_DispensationConfig_ID, trxName);
      /** if (UNS_DispensationConfig_ID == 0)
        {
			setIsBorongan (false);
// N
			setIsContract1 (false);
// N
			setIsContract2 (false);
// N
			setIsPermanent (false);
// N
			setIsRecontract1 (false);
// N
			setIsRecontract2 (false);
// N
			setIsSequenceContract (false);
// N
			setUNS_DispensationConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_DispensationConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_DispensationConfig[")
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

	/** Set Borongan.
		@param IsBorongan 
		This dispensation active for employee with status borongan ? (chcklist this box if true)
	  */
	public void setIsBorongan (boolean IsBorongan)
	{
		set_Value (COLUMNNAME_IsBorongan, Boolean.valueOf(IsBorongan));
	}

	/** Get Borongan.
		@return This dispensation active for employee with status borongan ? (chcklist this box if true)
	  */
	public boolean isBorongan () 
	{
		Object oo = get_Value(COLUMNNAME_IsBorongan);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Contract 1.
		@param IsContract1 
		Payable for contract 1
	  */
	public void setIsContract1 (boolean IsContract1)
	{
		set_Value (COLUMNNAME_IsContract1, Boolean.valueOf(IsContract1));
	}

	/** Get Contract 1.
		@return Payable for contract 1
	  */
	public boolean isContract1 () 
	{
		Object oo = get_Value(COLUMNNAME_IsContract1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Contract 2.
		@param IsContract2 
		This dispensation active for employee with status Harian? (chcklist this box if true)
	  */
	public void setIsContract2 (boolean IsContract2)
	{
		set_Value (COLUMNNAME_IsContract2, Boolean.valueOf(IsContract2));
	}

	/** Get Contract 2.
		@return This dispensation active for employee with status Harian? (chcklist this box if true)
	  */
	public boolean isContract2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsContract2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Permanent.
		@param IsPermanent 
		This dispensation active for Employee winth contract type Permanent
	  */
	public void setIsPermanent (boolean IsPermanent)
	{
		set_Value (COLUMNNAME_IsPermanent, Boolean.valueOf(IsPermanent));
	}

	/** Get Permanent.
		@return This dispensation active for Employee winth contract type Permanent
	  */
	public boolean isPermanent () 
	{
		Object oo = get_Value(COLUMNNAME_IsPermanent);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Reconrtact 1.
		@param IsRecontract1 
		This dispensation active for Employee winth contract type recontract 1
	  */
	public void setIsRecontract1 (boolean IsRecontract1)
	{
		set_Value (COLUMNNAME_IsRecontract1, Boolean.valueOf(IsRecontract1));
	}

	/** Get Reconrtact 1.
		@return This dispensation active for Employee winth contract type recontract 1
	  */
	public boolean isRecontract1 () 
	{
		Object oo = get_Value(COLUMNNAME_IsRecontract1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Recontract 2.
		@param IsRecontract2 
		This dispensation active for Employee winth contract type recontract 2
	  */
	public void setIsRecontract2 (boolean IsRecontract2)
	{
		set_Value (COLUMNNAME_IsRecontract2, Boolean.valueOf(IsRecontract2));
	}

	/** Get Recontract 2.
		@return This dispensation active for Employee winth contract type recontract 2
	  */
	public boolean isRecontract2 () 
	{
		Object oo = get_Value(COLUMNNAME_IsRecontract2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sequence Contract.
		@param IsSequenceContract 
		This dispensation active for Employee winth contract type Sequence Contract
	  */
	public void setIsSequenceContract (boolean IsSequenceContract)
	{
		set_Value (COLUMNNAME_IsSequenceContract, Boolean.valueOf(IsSequenceContract));
	}

	/** Get Sequence Contract.
		@return This dispensation active for Employee winth contract type Sequence Contract
	  */
	public boolean isSequenceContract () 
	{
		Object oo = get_Value(COLUMNNAME_IsSequenceContract);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Max Dispensation.
		@param MaxDispensation Max Dispensation	  */
	public void setMaxDispensation (int MaxDispensation)
	{
		set_Value (COLUMNNAME_MaxDispensation, Integer.valueOf(MaxDispensation));
	}

	/** Get Max Dispensation.
		@return Max Dispensation	  */
	public int getMaxDispensation () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxDispensation);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getMaxDispensation()));
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

	/** Set Dispensation Configuration.
		@param UNS_DispensationConfig_ID Dispensation Configuration	  */
	public void setUNS_DispensationConfig_ID (int UNS_DispensationConfig_ID)
	{
		if (UNS_DispensationConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_DispensationConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_DispensationConfig_ID, Integer.valueOf(UNS_DispensationConfig_ID));
	}

	/** Get Dispensation Configuration.
		@return Dispensation Configuration	  */
	public int getUNS_DispensationConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DispensationConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_DispensationConfig_UU.
		@param UNS_DispensationConfig_UU UNS_DispensationConfig_UU	  */
	public void setUNS_DispensationConfig_UU (String UNS_DispensationConfig_UU)
	{
		set_Value (COLUMNNAME_UNS_DispensationConfig_UU, UNS_DispensationConfig_UU);
	}

	/** Get UNS_DispensationConfig_UU.
		@return UNS_DispensationConfig_UU	  */
	public String getUNS_DispensationConfig_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_DispensationConfig_UU);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}