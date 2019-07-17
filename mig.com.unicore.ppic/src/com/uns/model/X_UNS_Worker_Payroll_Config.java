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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_Worker_Payroll_Config
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_Worker_Payroll_Config extends PO implements I_UNS_Worker_Payroll_Config, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130111L;

    /** Standard Constructor */
    public X_UNS_Worker_Payroll_Config (Properties ctx, int UNS_Worker_Payroll_Config_ID, String trxName)
    {
      super (ctx, UNS_Worker_Payroll_Config_ID, trxName);
      /** if (UNS_Worker_Payroll_Config_ID == 0)
        {
			setC_UOM_ID (0);
			setCost (Env.ZERO);
			setM_Product_ID (0);
			setUNS_Job_Role_ID (0);
			setUNS_Worker_Payroll_Config_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Worker_Payroll_Config (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Worker_Payroll_Config[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cost.
		@param Cost 
		Cost information
	  */
	public void setCost (BigDecimal Cost)
	{
		set_Value (COLUMNNAME_Cost, Cost);
	}

	/** Get Cost.
		@return Cost information
	  */
	public BigDecimal getCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Cost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** PaymentTerm AD_Reference_ID=195 */
	public static final int PAYMENTTERM_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTTERM_Cash = "B";
	/** Credit Card = K */
	public static final String PAYMENTTERM_CreditCard = "K";
	/** Direct Deposit = T */
	public static final String PAYMENTTERM_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTTERM_Check = "S";
	/** On Credit = P */
	public static final String PAYMENTTERM_OnCredit = "P";
	/** Direct Debit = D */
	public static final String PAYMENTTERM_DirectDebit = "D";
	/** Mixed POS Payment = M */
	public static final String PAYMENTTERM_MixedPOSPayment = "M";
	/** Set Payment Term.
		@param PaymentTerm 
		Payment Term
	  */
	public void setPaymentTerm (String PaymentTerm)
	{

		set_Value (COLUMNNAME_PaymentTerm, PaymentTerm);
	}

	/** Get Payment Term.
		@return Payment Term
	  */
	public String getPaymentTerm () 
	{
		return (String)get_Value(COLUMNNAME_PaymentTerm);
	}

	public com.uns.model.I_UNS_Job_Role getUNS_Job_Role() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Job_Role)MTable.get(getCtx(), com.uns.model.I_UNS_Job_Role.Table_Name)
			.getPO(getUNS_Job_Role_ID(), get_TrxName());	}

	/** Set Job Role.
		@param UNS_Job_Role_ID Job Role	  */
	public void setUNS_Job_Role_ID (int UNS_Job_Role_ID)
	{
		if (UNS_Job_Role_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Job_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Job_Role_ID, Integer.valueOf(UNS_Job_Role_ID));
	}

	/** Get Job Role.
		@return Job Role	  */
	public int getUNS_Job_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Job_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Worker Payroll Configuration.
		@param UNS_Worker_Payroll_Config_ID Worker Payroll Configuration	  */
	public void setUNS_Worker_Payroll_Config_ID (int UNS_Worker_Payroll_Config_ID)
	{
		if (UNS_Worker_Payroll_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Worker_Payroll_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Worker_Payroll_Config_ID, Integer.valueOf(UNS_Worker_Payroll_Config_ID));
	}

	/** Get Worker Payroll Configuration.
		@return Worker Payroll Configuration	  */
	public int getUNS_Worker_Payroll_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Worker_Payroll_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Worker_Payroll_Config_UU.
		@param UNS_Worker_Payroll_Config_UU UNS_Worker_Payroll_Config_UU	  */
	public void setUNS_Worker_Payroll_Config_UU (String UNS_Worker_Payroll_Config_UU)
	{
		set_Value (COLUMNNAME_UNS_Worker_Payroll_Config_UU, UNS_Worker_Payroll_Config_UU);
	}

	/** Get UNS_Worker_Payroll_Config_UU.
		@return UNS_Worker_Payroll_Config_UU	  */
	public String getUNS_Worker_Payroll_Config_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Worker_Payroll_Config_UU);
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}