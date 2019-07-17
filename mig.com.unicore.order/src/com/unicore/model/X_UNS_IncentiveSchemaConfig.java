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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_IncentiveSchemaConfig
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_IncentiveSchemaConfig extends PO implements I_UNS_IncentiveSchemaConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180401L;

    /** Standard Constructor */
    public X_UNS_IncentiveSchemaConfig (Properties ctx, int UNS_IncentiveSchemaConfig_ID, String trxName)
    {
      super (ctx, UNS_IncentiveSchemaConfig_ID, trxName);
      /** if (UNS_IncentiveSchemaConfig_ID == 0)
        {
			setAgeFrom (0);
// 0
			setAgeTo (0);
// 0
			setIncentive (Env.ZERO);
// 0
			setIsIncentiveAmount (false);
// N
			setIsSupervisorContinuous (false);
// N
			setIsSupervisorReward (false);
// N
			setProcessed (false);
// N
			setQtyMultiplier (Env.ZERO);
// 0
			setRangeFrom (Env.ZERO);
// 0
			setRangeTo (Env.ZERO);
// 0
			setUNS_Incentive_ID (0);
			setUNS_IncentiveSchemaConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_IncentiveSchemaConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_IncentiveSchemaConfig[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Age From.
		@param AgeFrom Age From	  */
	public void setAgeFrom (int AgeFrom)
	{
		set_Value (COLUMNNAME_AgeFrom, Integer.valueOf(AgeFrom));
	}

	/** Get Age From.
		@return Age From	  */
	public int getAgeFrom () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AgeFrom);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Age To.
		@param AgeTo Age To	  */
	public void setAgeTo (int AgeTo)
	{
		set_Value (COLUMNNAME_AgeTo, Integer.valueOf(AgeTo));
	}

	/** Get Age To.
		@return Age To	  */
	public int getAgeTo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AgeTo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Incentive %.
		@param Incentive Incentive %	  */
	public void setIncentive (BigDecimal Incentive)
	{
		set_Value (COLUMNNAME_Incentive, Incentive);
	}

	/** Get Incentive %.
		@return Incentive %	  */
	public BigDecimal getIncentive () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Incentive);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Incentive Amount.
		@param IsIncentiveAmount Incentive Amount	  */
	public void setIsIncentiveAmount (boolean IsIncentiveAmount)
	{
		set_Value (COLUMNNAME_IsIncentiveAmount, Boolean.valueOf(IsIncentiveAmount));
	}

	/** Get Incentive Amount.
		@return Incentive Amount	  */
	public boolean isIncentiveAmount () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncentiveAmount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Supervisor Continuous.
		@param IsSupervisorContinuous Supervisor Continuous	  */
	public void setIsSupervisorContinuous (boolean IsSupervisorContinuous)
	{
		set_Value (COLUMNNAME_IsSupervisorContinuous, Boolean.valueOf(IsSupervisorContinuous));
	}

	/** Get Supervisor Continuous.
		@return Supervisor Continuous	  */
	public boolean isSupervisorContinuous () 
	{
		Object oo = get_Value(COLUMNNAME_IsSupervisorContinuous);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Supervisor Reward.
		@param IsSupervisorReward Supervisor Reward	  */
	public void setIsSupervisorReward (boolean IsSupervisorReward)
	{
		set_Value (COLUMNNAME_IsSupervisorReward, Boolean.valueOf(IsSupervisorReward));
	}

	/** Get Supervisor Reward.
		@return Supervisor Reward	  */
	public boolean isSupervisorReward () 
	{
		Object oo = get_Value(COLUMNNAME_IsSupervisorReward);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Multiplier Quantity.
		@param QtyMultiplier 
		Value to multiply quantities by for generating commissions.
	  */
	public void setQtyMultiplier (BigDecimal QtyMultiplier)
	{
		set_Value (COLUMNNAME_QtyMultiplier, QtyMultiplier);
	}

	/** Get Multiplier Quantity.
		@return Value to multiply quantities by for generating commissions.
	  */
	public BigDecimal getQtyMultiplier () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMultiplier);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Range From.
		@param RangeFrom Range From	  */
	public void setRangeFrom (BigDecimal RangeFrom)
	{
		set_Value (COLUMNNAME_RangeFrom, RangeFrom);
	}

	/** Get Range From.
		@return Range From	  */
	public BigDecimal getRangeFrom () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RangeFrom);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Range To.
		@param RangeTo Range To	  */
	public void setRangeTo (BigDecimal RangeTo)
	{
		set_Value (COLUMNNAME_RangeTo, RangeTo);
	}

	/** Get Range To.
		@return Range To	  */
	public BigDecimal getRangeTo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RangeTo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.unicore.model.I_UNS_Incentive getUNS_Incentive() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Incentive)MTable.get(getCtx(), com.unicore.model.I_UNS_Incentive.Table_Name)
			.getPO(getUNS_Incentive_ID(), get_TrxName());	}

	/** Set Incentive.
		@param UNS_Incentive_ID Incentive	  */
	public void setUNS_Incentive_ID (int UNS_Incentive_ID)
	{
		if (UNS_Incentive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Incentive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Incentive_ID, Integer.valueOf(UNS_Incentive_ID));
	}

	/** Get Incentive.
		@return Incentive	  */
	public int getUNS_Incentive_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Incentive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Configuration.
		@param UNS_IncentiveSchemaConfig_ID Configuration	  */
	public void setUNS_IncentiveSchemaConfig_ID (int UNS_IncentiveSchemaConfig_ID)
	{
		if (UNS_IncentiveSchemaConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveSchemaConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_IncentiveSchemaConfig_ID, Integer.valueOf(UNS_IncentiveSchemaConfig_ID));
	}

	/** Get Configuration.
		@return Configuration	  */
	public int getUNS_IncentiveSchemaConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_IncentiveSchemaConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_IncentiveSchemaConfig_UU.
		@param UNS_IncentiveSchemaConfig_UU UNS_IncentiveSchemaConfig_UU	  */
	public void setUNS_IncentiveSchemaConfig_UU (String UNS_IncentiveSchemaConfig_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_IncentiveSchemaConfig_UU, UNS_IncentiveSchemaConfig_UU);
	}

	/** Get UNS_IncentiveSchemaConfig_UU.
		@return UNS_IncentiveSchemaConfig_UU	  */
	public String getUNS_IncentiveSchemaConfig_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_IncentiveSchemaConfig_UU);
	}
}