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

/** Generated Model for UNS_PayCriteria
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_PayCriteria extends PO implements I_UNS_PayCriteria, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150309L;

    /** Standard Constructor */
    public X_UNS_PayCriteria (Properties ctx, int UNS_PayCriteria_ID, String trxName)
    {
      super (ctx, UNS_PayCriteria_ID, trxName);
      /** if (UNS_PayCriteria_ID == 0)
        {
			setCode (null);
			setCriteriaType (null);
			setIsGradualCalculation (false);
// N
			setIsSwitchTodaily (false);
// N
			setIsTargetBased (false);
// N
			setName (null);
			setUNS_PayCriteria_ID (0);
			setUNS_ProductionPayConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PayCriteria (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PayCriteria[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Validation code.
		@param Code 
		Validation Code
	  */
	public void setCode (String Code)
	{
		set_Value (COLUMNNAME_Code, Code);
	}

	/** Get Validation code.
		@return Validation Code
	  */
	public String getCode () 
	{
		return (String)get_Value(COLUMNNAME_Code);
	}

	/** Accomplishment = ACP */
	public static final String CRITERIATYPE_Accomplishment = "ACP";
	/** Daily = DAY */
	public static final String CRITERIATYPE_Daily = "DAY";
	/** Product = PRD */
	public static final String CRITERIATYPE_Product = "PRD";
	/** Premi = PRM */
	public static final String CRITERIATYPE_Premi = "PRM";
	/** Set Criteria Type.
		@param CriteriaType Criteria Type	  */
	public void setCriteriaType (String CriteriaType)
	{

		set_Value (COLUMNNAME_CriteriaType, CriteriaType);
	}

	/** Get Criteria Type.
		@return Criteria Type	  */
	public String getCriteriaType () 
	{
		return (String)get_Value(COLUMNNAME_CriteriaType);
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

	/** Set Is Gradual Calculation.
		@param IsGradualCalculation 
		Indication to calculate the payment gradually based on the target achivement (step-by-step).
	  */
	public void setIsGradualCalculation (boolean IsGradualCalculation)
	{
		set_Value (COLUMNNAME_IsGradualCalculation, Boolean.valueOf(IsGradualCalculation));
	}

	/** Get Is Gradual Calculation.
		@return Indication to calculate the payment gradually based on the target achivement (step-by-step).
	  */
	public boolean isGradualCalculation () 
	{
		Object oo = get_Value(COLUMNNAME_IsGradualCalculation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Switched To Daily.
		@param IsSwitchTodaily 
		If out of premi constraints, worker's receivable is switched to daily pay
	  */
	public void setIsSwitchTodaily (boolean IsSwitchTodaily)
	{
		set_Value (COLUMNNAME_IsSwitchTodaily, Boolean.valueOf(IsSwitchTodaily));
	}

	/** Get Switched To Daily.
		@return If out of premi constraints, worker's receivable is switched to daily pay
	  */
	public boolean isSwitchTodaily () 
	{
		Object oo = get_Value(COLUMNNAME_IsSwitchTodaily);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Target Based.
		@param IsTargetBased Target Based	  */
	public void setIsTargetBased (boolean IsTargetBased)
	{
		set_Value (COLUMNNAME_IsTargetBased, Boolean.valueOf(IsTargetBased));
	}

	/** Get Target Based.
		@return Target Based	  */
	public boolean isTargetBased () 
	{
		Object oo = get_Value(COLUMNNAME_IsTargetBased);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Pay Criteria.
		@param UNS_PayCriteria_ID Pay Criteria	  */
	public void setUNS_PayCriteria_ID (int UNS_PayCriteria_ID)
	{
		if (UNS_PayCriteria_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PayCriteria_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PayCriteria_ID, Integer.valueOf(UNS_PayCriteria_ID));
	}

	/** Get Pay Criteria.
		@return Pay Criteria	  */
	public int getUNS_PayCriteria_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PayCriteria_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_PayCriteria_UU.
		@param UNS_PayCriteria_UU UNS_PayCriteria_UU	  */
	public void setUNS_PayCriteria_UU (String UNS_PayCriteria_UU)
	{
		set_Value (COLUMNNAME_UNS_PayCriteria_UU, UNS_PayCriteria_UU);
	}

	/** Get UNS_PayCriteria_UU.
		@return UNS_PayCriteria_UU	  */
	public String getUNS_PayCriteria_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PayCriteria_UU);
	}

	public com.uns.model.I_UNS_ProductionPayConfig getUNS_ProductionPayConfig() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ProductionPayConfig)MTable.get(getCtx(), com.uns.model.I_UNS_ProductionPayConfig.Table_Name)
			.getPO(getUNS_ProductionPayConfig_ID(), get_TrxName());	}

	/** Set Production Pay Config.
		@param UNS_ProductionPayConfig_ID Production Pay Config	  */
	public void setUNS_ProductionPayConfig_ID (int UNS_ProductionPayConfig_ID)
	{
		if (UNS_ProductionPayConfig_ID < 1) 
			set_Value (COLUMNNAME_UNS_ProductionPayConfig_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_ProductionPayConfig_ID, Integer.valueOf(UNS_ProductionPayConfig_ID));
	}

	/** Get Production Pay Config.
		@return Production Pay Config	  */
	public int getUNS_ProductionPayConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ProductionPayConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}