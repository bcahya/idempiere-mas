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

/** Generated Model for UNS_PackingSlipSupplier
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_PackingSlipSupplier extends PO implements I_UNS_PackingSlipSupplier, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140201L;

    /** Standard Constructor */
    public X_UNS_PackingSlipSupplier (Properties ctx, int UNS_PackingSlipSupplier_ID, String trxName)
    {
      super (ctx, UNS_PackingSlipSupplier_ID, trxName);
      /** if (UNS_PackingSlipSupplier_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setM_Locator_ID (0);
			setProcessed (false);
// N
			setUNS_PackingSlipSupplier_ID (0);
			setUNS_PackingSlip_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_PackingSlipSupplier (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_PackingSlipSupplier[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bea Cukai No.
		@param BCNo Bea Cukai No	  */
	public void setBCNo (String BCNo)
	{
		set_Value (COLUMNNAME_BCNo, BCNo);
	}

	/** Get Bea Cukai No.
		@return Bea Cukai No	  */
	public String getBCNo () 
	{
		return (String)get_Value(COLUMNNAME_BCNo);
	}

	/** Set Bill of Lading No.
		@param BLNo Bill of Lading No	  */
	public void setBLNo (String BLNo)
	{
		set_Value (COLUMNNAME_BLNo, BLNo);
	}

	/** Get Bill of Lading No.
		@return Bill of Lading No	  */
	public String getBLNo () 
	{
		return (String)get_Value(COLUMNNAME_BLNo);
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Vendor/Supplier.
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

	/** Get Vendor/Supplier.
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

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (org.compiere.model.I_C_Invoice)MTable.get(getCtx(), org.compiere.model.I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Create lines from.
		@param CreateFrom 
		Process which will generate a new document lines based on an existing document
	  */
	public void setCreateFrom (String CreateFrom)
	{
		set_Value (COLUMNNAME_CreateFrom, CreateFrom);
	}

	/** Get Create lines from.
		@return Process which will generate a new document lines based on an existing document
	  */
	public String getCreateFrom () 
	{
		return (String)get_Value(COLUMNNAME_CreateFrom);
	}

	public org.compiere.model.I_M_InOutConfirm getM_InOutConfirm() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOutConfirm)MTable.get(getCtx(), org.compiere.model.I_M_InOutConfirm.Table_Name)
			.getPO(getM_InOutConfirm_ID(), get_TrxName());	}

	/** Set Ship/Receipt Confirmation.
		@param M_InOutConfirm_ID 
		Material Shipment or Receipt Confirmation
	  */
	public void setM_InOutConfirm_ID (int M_InOutConfirm_ID)
	{
		if (M_InOutConfirm_ID < 1) 
			set_Value (COLUMNNAME_M_InOutConfirm_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutConfirm_ID, Integer.valueOf(M_InOutConfirm_ID));
	}

	/** Get Ship/Receipt Confirmation.
		@return Material Shipment or Receipt Confirmation
	  */
	public int getM_InOutConfirm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutConfirm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
    {
		return (org.compiere.model.I_M_InOut)MTable.get(getCtx(), org.compiere.model.I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
    {
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Packing Slip Supplier.
		@param UNS_PackingSlipSupplier_ID Packing Slip Supplier	  */
	public void setUNS_PackingSlipSupplier_ID (int UNS_PackingSlipSupplier_ID)
	{
		if (UNS_PackingSlipSupplier_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingSlipSupplier_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingSlipSupplier_ID, Integer.valueOf(UNS_PackingSlipSupplier_ID));
	}

	/** Get Packing Slip Supplier.
		@return Packing Slip Supplier	  */
	public int getUNS_PackingSlipSupplier_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingSlipSupplier_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PackingSlip Supplier UU.
		@param UNS_PackingSlipSupplier_UU PackingSlip Supplier UU	  */
	public void setUNS_PackingSlipSupplier_UU (String UNS_PackingSlipSupplier_UU)
	{
		set_Value (COLUMNNAME_UNS_PackingSlipSupplier_UU, UNS_PackingSlipSupplier_UU);
	}

	/** Get PackingSlip Supplier UU.
		@return PackingSlip Supplier UU	  */
	public String getUNS_PackingSlipSupplier_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_PackingSlipSupplier_UU);
	}

	public com.uns.model.I_UNS_PackingSlip getUNS_PackingSlip() throws RuntimeException
    {
		return (com.uns.model.I_UNS_PackingSlip)MTable.get(getCtx(), com.uns.model.I_UNS_PackingSlip.Table_Name)
			.getPO(getUNS_PackingSlip_ID(), get_TrxName());	}

	/** Set Packing Slip.
		@param UNS_PackingSlip_ID Packing Slip	  */
	public void setUNS_PackingSlip_ID (int UNS_PackingSlip_ID)
	{
		if (UNS_PackingSlip_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingSlip_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_PackingSlip_ID, Integer.valueOf(UNS_PackingSlip_ID));
	}

	/** Get Packing Slip.
		@return Packing Slip	  */
	public int getUNS_PackingSlip_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PackingSlip_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}