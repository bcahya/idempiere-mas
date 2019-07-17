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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_AllowanceConfig
 *  @author iDempiere (generated) 
 *  @version Release 2.0 - $Id$ */
public class X_UNS_AllowanceConfig extends PO implements I_UNS_AllowanceConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20141015L;

    /** Standard Constructor */
    public X_UNS_AllowanceConfig (Properties ctx, int UNS_AllowanceConfig_ID, String trxName)
    {
      super (ctx, UNS_AllowanceConfig_ID, trxName);
      /** if (UNS_AllowanceConfig_ID == 0)
        {
			setAllowanceBaseOnSallary (false);
// N
			setAllowanceMultiplier (Env.ZERO);
// 1
			setC_JobCategory_ID (0);
			setContractType (null);
			setMedicalAllowance (Env.ZERO);
			setUNS_AllowanceConfig_ID (0);
			setUNS_PayrollConfiguration_ID (0);
// @0|UNS_PayrollConfiguration_ID@
        } */
    }

    /** Load Constructor */
    public X_UNS_AllowanceConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_AllowanceConfig[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Allowance Base On Sallary.
		@param AllowanceBaseOnSallary Allowance Base On Sallary	  */
	public void setAllowanceBaseOnSallary (boolean AllowanceBaseOnSallary)
	{
		set_Value (COLUMNNAME_AllowanceBaseOnSallary, Boolean.valueOf(AllowanceBaseOnSallary));
	}

	/** Get Allowance Base On Sallary.
		@return Allowance Base On Sallary	  */
	public boolean isAllowanceBaseOnSallary () 
	{
		Object oo = get_Value(COLUMNNAME_AllowanceBaseOnSallary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allowance Multiplier.
		@param AllowanceMultiplier Allowance Multiplier	  */
	public void setAllowanceMultiplier (BigDecimal AllowanceMultiplier)
	{
		set_Value (COLUMNNAME_AllowanceMultiplier, AllowanceMultiplier);
	}

	/** Get Allowance Multiplier.
		@return Allowance Multiplier	  */
	public BigDecimal getAllowanceMultiplier () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AllowanceMultiplier);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Contract 1 = CN1 */
	public static final String CONTRACTTYPE_Contract1 = "CN1";
	/** Contract 2 = CN2 */
	public static final String CONTRACTTYPE_Contract2 = "CN2";
	/** Permanen = PRM */
	public static final String CONTRACTTYPE_Permanen = "PRM";
	/** Recontract 1 = RC1 */
	public static final String CONTRACTTYPE_Recontract1 = "RC1";
	/** Recontract 2 = RC2 */
	public static final String CONTRACTTYPE_Recontract2 = "RC2";
	/** Squence Contract = SCT */
	public static final String CONTRACTTYPE_SquenceContract = "SCT";
	/** Borongan = BRG */
	public static final String CONTRACTTYPE_Borongan = "BRG";
	/** Borongan CV = BCV */
	public static final String CONTRACTTYPE_BoronganCV = "BCV";
	/** Borongan Harian = BRH */
	public static final String CONTRACTTYPE_BoronganHarian = "BRH";
	/** Borongan Harian CV = BHC */
	public static final String CONTRACTTYPE_BoronganHarianCV = "BHC";
	/** Set Contract Type.
		@param ContractType 
		Contract Type
	  */
	public void setContractType (String ContractType)
	{

		set_Value (COLUMNNAME_ContractType, ContractType);
	}

	/** Get Contract Type.
		@return Contract Type
	  */
	public String getContractType () 
	{
		return (String)get_Value(COLUMNNAME_ContractType);
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

	/** Set Medical Allowance.
		@param MedicalAllowance 
		The yearly employee's medical allowance amount
	  */
	public void setMedicalAllowance (BigDecimal MedicalAllowance)
	{
		set_Value (COLUMNNAME_MedicalAllowance, MedicalAllowance);
	}

	/** Get Medical Allowance.
		@return The yearly employee's medical allowance amount
	  */
	public BigDecimal getMedicalAllowance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MedicalAllowance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Allowance Configuration.
		@param UNS_AllowanceConfig_ID Allowance Configuration	  */
	public void setUNS_AllowanceConfig_ID (int UNS_AllowanceConfig_ID)
	{
		if (UNS_AllowanceConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AllowanceConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AllowanceConfig_ID, Integer.valueOf(UNS_AllowanceConfig_ID));
	}

	/** Get Allowance Configuration.
		@return Allowance Configuration	  */
	public int getUNS_AllowanceConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AllowanceConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set uns_allowanceconfig_uu.
		@param uns_allowanceconfig_uu uns_allowanceconfig_uu	  */
	public void setuns_allowanceconfig_uu (String uns_allowanceconfig_uu)
	{
		set_Value (COLUMNNAME_uns_allowanceconfig_uu, uns_allowanceconfig_uu);
	}

	/** Get uns_allowanceconfig_uu.
		@return uns_allowanceconfig_uu	  */
	public String getuns_allowanceconfig_uu () 
	{
		return (String)get_Value(COLUMNNAME_uns_allowanceconfig_uu);
	}

	public com.uns.model.I_UNS_PayrollConfiguration getUNS_PayrollConfiguration() throws RuntimeException
    {
		return (com.uns.model.I_UNS_PayrollConfiguration)MTable.get(getCtx(), com.uns.model.I_UNS_PayrollConfiguration.Table_Name)
			.getPO(getUNS_PayrollConfiguration_ID(), get_TrxName());	}

	/** Set Payroll Configuration.
		@param UNS_PayrollConfiguration_ID Payroll Configuration	  */
	public void setUNS_PayrollConfiguration_ID (int UNS_PayrollConfiguration_ID)
	{
		if (UNS_PayrollConfiguration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PayrollConfiguration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PayrollConfiguration_ID, Integer.valueOf(UNS_PayrollConfiguration_ID));
	}

	/** Get Payroll Configuration.
		@return Payroll Configuration	  */
	public int getUNS_PayrollConfiguration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PayrollConfiguration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}