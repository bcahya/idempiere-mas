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

/** Generated Model for UNS_AchievedIncentive
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_AchievedIncentive extends PO implements I_UNS_AchievedIncentive, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180401L;

    /** Standard Constructor */
    public X_UNS_AchievedIncentive (Properties ctx, int UNS_AchievedIncentive_ID, String trxName)
    {
      super (ctx, UNS_AchievedIncentive_ID, trxName);
      /** if (UNS_AchievedIncentive_ID == 0)
        {
			setAchievementType (null);
			setC_BPartner_ID (0);
			setUNS_AchievedIncentive_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_AchievedIncentive (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_AchievedIncentive[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Achievement April.
		@param AchievementApr Achievement April	  */
	public void setAchievementApr (BigDecimal AchievementApr)
	{
		set_Value (COLUMNNAME_AchievementApr, AchievementApr);
	}

	/** Get Achievement April.
		@return Achievement April	  */
	public BigDecimal getAchievementApr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AchievementApr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Achievement August.
		@param AchievementAug Achievement August	  */
	public void setAchievementAug (BigDecimal AchievementAug)
	{
		set_Value (COLUMNNAME_AchievementAug, AchievementAug);
	}

	/** Get Achievement August.
		@return Achievement August	  */
	public BigDecimal getAchievementAug () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AchievementAug);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Achievement December.
		@param AchievementDec Achievement December	  */
	public void setAchievementDec (BigDecimal AchievementDec)
	{
		set_Value (COLUMNNAME_AchievementDec, AchievementDec);
	}

	/** Get Achievement December.
		@return Achievement December	  */
	public BigDecimal getAchievementDec () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AchievementDec);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Achievement February.
		@param AchievementFeb Achievement February	  */
	public void setAchievementFeb (BigDecimal AchievementFeb)
	{
		set_Value (COLUMNNAME_AchievementFeb, AchievementFeb);
	}

	/** Get Achievement February.
		@return Achievement February	  */
	public BigDecimal getAchievementFeb () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AchievementFeb);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Achievement January.
		@param AchievementJan Achievement January	  */
	public void setAchievementJan (BigDecimal AchievementJan)
	{
		set_Value (COLUMNNAME_AchievementJan, AchievementJan);
	}

	/** Get Achievement January.
		@return Achievement January	  */
	public BigDecimal getAchievementJan () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AchievementJan);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Achievement July.
		@param AchievementJul Achievement July	  */
	public void setAchievementJul (BigDecimal AchievementJul)
	{
		set_Value (COLUMNNAME_AchievementJul, AchievementJul);
	}

	/** Get Achievement July.
		@return Achievement July	  */
	public BigDecimal getAchievementJul () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AchievementJul);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Achievement June.
		@param AchievementJun Achievement June	  */
	public void setAchievementJun (BigDecimal AchievementJun)
	{
		set_Value (COLUMNNAME_AchievementJun, AchievementJun);
	}

	/** Get Achievement June.
		@return Achievement June	  */
	public BigDecimal getAchievementJun () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AchievementJun);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Achievement March.
		@param AchievementMarch Achievement March	  */
	public void setAchievementMarch (BigDecimal AchievementMarch)
	{
		set_Value (COLUMNNAME_AchievementMarch, AchievementMarch);
	}

	/** Get Achievement March.
		@return Achievement March	  */
	public BigDecimal getAchievementMarch () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AchievementMarch);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Achievement May.
		@param AchievementMay Achievement May	  */
	public void setAchievementMay (BigDecimal AchievementMay)
	{
		set_Value (COLUMNNAME_AchievementMay, AchievementMay);
	}

	/** Get Achievement May.
		@return Achievement May	  */
	public BigDecimal getAchievementMay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AchievementMay);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Achievement November.
		@param AchievementNov Achievement November	  */
	public void setAchievementNov (BigDecimal AchievementNov)
	{
		set_Value (COLUMNNAME_AchievementNov, AchievementNov);
	}

	/** Get Achievement November.
		@return Achievement November	  */
	public BigDecimal getAchievementNov () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AchievementNov);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Achievement October.
		@param AchievementOct Achievement October	  */
	public void setAchievementOct (BigDecimal AchievementOct)
	{
		set_Value (COLUMNNAME_AchievementOct, AchievementOct);
	}

	/** Get Achievement October.
		@return Achievement October	  */
	public BigDecimal getAchievementOct () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AchievementOct);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Achievement September.
		@param AchievementSep Achievement September	  */
	public void setAchievementSep (BigDecimal AchievementSep)
	{
		set_Value (COLUMNNAME_AchievementSep, AchievementSep);
	}

	/** Get Achievement September.
		@return Achievement September	  */
	public BigDecimal getAchievementSep () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AchievementSep);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Customer = CST */
	public static final String ACHIEVEMENTTYPE_Customer = "CST";
	/** Sales = SLS */
	public static final String ACHIEVEMENTTYPE_Sales = "SLS";
	/** Set Achievement Type.
		@param AchievementType Achievement Type	  */
	public void setAchievementType (String AchievementType)
	{

		set_Value (COLUMNNAME_AchievementType, AchievementType);
	}

	/** Get Achievement Type.
		@return Achievement Type	  */
	public String getAchievementType () 
	{
		return (String)get_Value(COLUMNNAME_AchievementType);
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Year getC_Year() throws RuntimeException
    {
		return (org.compiere.model.I_C_Year)MTable.get(getCtx(), org.compiere.model.I_C_Year.Table_Name)
			.getPO(getC_Year_ID(), get_TrxName());	}

	/** Set Year.
		@param C_Year_ID 
		Calendar Year
	  */
	public void setC_Year_ID (int C_Year_ID)
	{
		if (C_Year_ID < 1) 
			set_Value (COLUMNNAME_C_Year_ID, null);
		else 
			set_Value (COLUMNNAME_C_Year_ID, Integer.valueOf(C_Year_ID));
	}

	/** Get Year.
		@return Calendar Year
	  */
	public int getC_Year_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Year_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Generate Period Record.
		@param GeneratePeriodRecord Generate Period Record	  */
	public void setGeneratePeriodRecord (String GeneratePeriodRecord)
	{
		set_Value (COLUMNNAME_GeneratePeriodRecord, GeneratePeriodRecord);
	}

	/** Get Generate Period Record.
		@return Generate Period Record	  */
	public String getGeneratePeriodRecord () 
	{
		return (String)get_Value(COLUMNNAME_GeneratePeriodRecord);
	}

	/** Set Achieved Incentive.
		@param UNS_AchievedIncentive_ID Achieved Incentive	  */
	public void setUNS_AchievedIncentive_ID (int UNS_AchievedIncentive_ID)
	{
		if (UNS_AchievedIncentive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AchievedIncentive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AchievedIncentive_ID, Integer.valueOf(UNS_AchievedIncentive_ID));
	}

	/** Get Achieved Incentive.
		@return Achieved Incentive	  */
	public int getUNS_AchievedIncentive_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AchievedIncentive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_AchievedIncentive_UU.
		@param UNS_AchievedIncentive_UU UNS_AchievedIncentive_UU	  */
	public void setUNS_AchievedIncentive_UU (String UNS_AchievedIncentive_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_AchievedIncentive_UU, UNS_AchievedIncentive_UU);
	}

	/** Get UNS_AchievedIncentive_UU.
		@return UNS_AchievedIncentive_UU	  */
	public String getUNS_AchievedIncentive_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_AchievedIncentive_UU);
	}
}