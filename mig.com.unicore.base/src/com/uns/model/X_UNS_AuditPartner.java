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

/** Generated Model for UNS_AuditPartner
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_AuditPartner extends PO implements I_UNS_AuditPartner, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170603L;

    /** Standard Constructor */
    public X_UNS_AuditPartner (Properties ctx, int UNS_AuditPartner_ID, String trxName)
    {
      super (ctx, UNS_AuditPartner_ID, trxName);
      /** if (UNS_AuditPartner_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_PaymentTerm_ID (0);
			setTmp_SO_CreditLimit (Env.ZERO);
// 0
			setUNS_Audit_ID (0);
			setUNS_AuditPartner_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_AuditPartner (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_AuditPartner[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
    {
		return (org.compiere.model.I_C_PaymentTerm)MTable.get(getCtx(), org.compiere.model.I_C_PaymentTerm.Table_Name)
			.getPO(getC_PaymentTerm_ID(), get_TrxName());	}

	/** Set Payment Term.
		@param C_PaymentTerm_ID 
		The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Payment Term.
		@return The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Temporary SO Credit Limit.
		@param Tmp_SO_CreditLimit Temporary SO Credit Limit	  */
	public void setTmp_SO_CreditLimit (BigDecimal Tmp_SO_CreditLimit)
	{
		set_Value (COLUMNNAME_Tmp_SO_CreditLimit, Tmp_SO_CreditLimit);
	}

	/** Get Temporary SO Credit Limit.
		@return Temporary SO Credit Limit	  */
	public BigDecimal getTmp_SO_CreditLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Tmp_SO_CreditLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		throw new IllegalArgumentException ("TotalAmt is virtual column");	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Invoice.
		@param TotalInvoice Total Invoice	  */
	public void setTotalInvoice (int TotalInvoice)
	{
		throw new IllegalArgumentException ("TotalInvoice is virtual column");	}

	/** Get Total Invoice.
		@return Total Invoice	  */
	public int getTotalInvoice () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TotalInvoice);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_UNS_Audit getUNS_Audit() throws RuntimeException
    {
		return (I_UNS_Audit)MTable.get(getCtx(), I_UNS_Audit.Table_Name)
			.getPO(getUNS_Audit_ID(), get_TrxName());	}

	/** Set Aduit.
		@param UNS_Audit_ID Aduit	  */
	public void setUNS_Audit_ID (int UNS_Audit_ID)
	{
		if (UNS_Audit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Audit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Audit_ID, Integer.valueOf(UNS_Audit_ID));
	}

	/** Get Aduit.
		@return Aduit	  */
	public int getUNS_Audit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Audit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Partner.
		@param UNS_AuditPartner_ID Partner	  */
	public void setUNS_AuditPartner_ID (int UNS_AuditPartner_ID)
	{
		if (UNS_AuditPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AuditPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AuditPartner_ID, Integer.valueOf(UNS_AuditPartner_ID));
	}

	/** Get Partner.
		@return Partner	  */
	public int getUNS_AuditPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AuditPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_AuditPartner_UU.
		@param UNS_AuditPartner_UU UNS_AuditPartner_UU	  */
	public void setUNS_AuditPartner_UU (String UNS_AuditPartner_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_AuditPartner_UU, UNS_AuditPartner_UU);
	}

	/** Get UNS_AuditPartner_UU.
		@return UNS_AuditPartner_UU	  */
	public String getUNS_AuditPartner_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_AuditPartner_UU);
	}
}