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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_BPartner_Location
 *  @author iDempiere (generated) 
 *  @version Release 5.1 - $Id$ */
public class X_C_BPartner_Location extends PO implements I_C_BPartner_Location, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171031L;

    /** Standard Constructor */
    public X_C_BPartner_Location (Properties ctx, int C_BPartner_Location_ID, String trxName)
    {
      super (ctx, C_BPartner_Location_ID, trxName);
      /** if (C_BPartner_Location_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_Location_ID (0);
			setIsBillTo (true);
// Y
			setIsPayFrom (true);
// Y
			setIsRemitTo (true);
// Y
			setIsShipTo (true);
// Y
			setName (null);
// .
        } */
    }

    /** Load Constructor */
    public X_C_BPartner_Location (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_BPartner_Location[")
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

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
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

	/** Set C_BPartner_Location_UU.
		@param C_BPartner_Location_UU C_BPartner_Location_UU	  */
	public void setC_BPartner_Location_UU (String C_BPartner_Location_UU)
	{
		set_Value (COLUMNNAME_C_BPartner_Location_UU, C_BPartner_Location_UU);
	}

	/** Get C_BPartner_Location_UU.
		@return C_BPartner_Location_UU	  */
	public String getC_BPartner_Location_UU () 
	{
		return (String)get_Value(COLUMNNAME_C_BPartner_Location_UU);
	}

	public I_C_Location getC_Location() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_SalesRegion getC_SalesRegion() throws RuntimeException
    {
		return (org.compiere.model.I_C_SalesRegion)MTable.get(getCtx(), org.compiere.model.I_C_SalesRegion.Table_Name)
			.getPO(getC_SalesRegion_ID(), get_TrxName());	}

	/** Set Sales Region.
		@param C_SalesRegion_ID 
		Sales coverage region
	  */
	public void setC_SalesRegion_ID (int C_SalesRegion_ID)
	{
		if (C_SalesRegion_ID < 1) 
			set_Value (COLUMNNAME_C_SalesRegion_ID, null);
		else 
			set_Value (COLUMNNAME_C_SalesRegion_ID, Integer.valueOf(C_SalesRegion_ID));
	}

	/** Get Sales Region.
		@return Sales coverage region
	  */
	public int getC_SalesRegion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SalesRegion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Customer Address ID.
		@param CustomerAddressID Customer Address ID	  */
	public void setCustomerAddressID (String CustomerAddressID)
	{
		set_Value (COLUMNNAME_CustomerAddressID, CustomerAddressID);
	}

	/** Get Customer Address ID.
		@return Customer Address ID	  */
	public String getCustomerAddressID () 
	{
		return (String)get_Value(COLUMNNAME_CustomerAddressID);
	}

	/** Set Fax.
		@param Fax 
		Facsimile number
	  */
	public void setFax (String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	/** Get Fax.
		@return Facsimile number
	  */
	public String getFax () 
	{
		return (String)get_Value(COLUMNNAME_Fax);
	}

	/** Set Invoice Address.
		@param IsBillTo 
		Business Partner Invoice/Bill Address
	  */
	public void setIsBillTo (boolean IsBillTo)
	{
		set_Value (COLUMNNAME_IsBillTo, Boolean.valueOf(IsBillTo));
	}

	/** Get Invoice Address.
		@return Business Partner Invoice/Bill Address
	  */
	public boolean isBillTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsBillTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ISDN.
		@param ISDN 
		ISDN or modem line
	  */
	public void setISDN (String ISDN)
	{
		set_Value (COLUMNNAME_ISDN, ISDN);
	}

	/** Get ISDN.
		@return ISDN or modem line
	  */
	public String getISDN () 
	{
		return (String)get_Value(COLUMNNAME_ISDN);
	}

	/** Set Pay-From Address.
		@param IsPayFrom 
		Business Partner pays from that address and we'll send dunning letters there
	  */
	public void setIsPayFrom (boolean IsPayFrom)
	{
		set_Value (COLUMNNAME_IsPayFrom, Boolean.valueOf(IsPayFrom));
	}

	/** Get Pay-From Address.
		@return Business Partner pays from that address and we'll send dunning letters there
	  */
	public boolean isPayFrom () 
	{
		Object oo = get_Value(COLUMNNAME_IsPayFrom);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Remit-To Address.
		@param IsRemitTo 
		Business Partner payment address
	  */
	public void setIsRemitTo (boolean IsRemitTo)
	{
		set_Value (COLUMNNAME_IsRemitTo, Boolean.valueOf(IsRemitTo));
	}

	/** Get Remit-To Address.
		@return Business Partner payment address
	  */
	public boolean isRemitTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsRemitTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Ship Address.
		@param IsShipTo 
		Business Partner Shipment Address
	  */
	public void setIsShipTo (boolean IsShipTo)
	{
		set_Value (COLUMNNAME_IsShipTo, Boolean.valueOf(IsShipTo));
	}

	/** Get Ship Address.
		@return Business Partner Shipment Address
	  */
	public boolean isShipTo () 
	{
		Object oo = get_Value(COLUMNNAME_IsShipTo);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
	}

	/** Set 2nd Phone.
		@param Phone2 
		Identifies an alternate telephone number.
	  */
	public void setPhone2 (String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	/** Get 2nd Phone.
		@return Identifies an alternate telephone number.
	  */
	public String getPhone2 () 
	{
		return (String)get_Value(COLUMNNAME_Phone2);
	}
	
	//TAMBAHAN
	/**
	 * Set BPartner Search Key.
	 * 
	 * @param BPartnerSearchKey The search key of this record's business partner.
	 */
	public void setBPartnerSearchKey(String BPartnerSearchKey) {
		set_Value(COLUMNNAME_BPartnerSearchKey, BPartnerSearchKey);
	}

	/**
	 * Get BPartner Search Key.
	 * 
	 * @return The search key of this record's business partner.
	 */
	public String getBPartnerSearchKey() {
		return (String) get_Value(COLUMNNAME_BPartnerSearchKey);
	}
	
	/**
	 * Set Tax Address.
	 * 
	 * @param TaxAddress The address of Business Partner registered on Tax Office
	 */
	public void setTaxAddress(String TaxAddress) {
		set_Value(COLUMNNAME_TaxAddress, TaxAddress);
	}

	/**
	 * Get Tax Address.
	 * 
	 * @return The address of Business Partner registered on Tax Office
	 */
	public String getTaxAddress() {
		return (String) get_Value(COLUMNNAME_TaxAddress);
	}

	/**
	 * Set Name for Tax.
	 * 
	 * @param TaxName The name identification of Business Partner registered on Tax
	 *                Office
	 */
	public void setTaxName(String TaxName) {
		set_Value(COLUMNNAME_TaxName, TaxName);
	}

	/**
	 * Get Name for Tax.
	 * 
	 * @return The name identification of Business Partner registered on Tax Office
	 */
	public String getTaxName() {
		return (String) get_Value(COLUMNNAME_TaxName);
	}

	/**
	 * Set Tax Serial No.
	 * 
	 * @param TaxSerialNo The serial number of Business Partner Tax
	 */
	public void setTaxSerialNo(String TaxSerialNo) {
		set_Value(COLUMNNAME_TaxSerialNo, TaxSerialNo);
	}

	/**
	 * Get Tax Serial No.
	 * 
	 * @return The serial number of Business Partner Tax
	 */
	public String getTaxSerialNo() {
		return (String) get_Value(COLUMNNAME_TaxSerialNo);
	}
	
	/**
	 * Set Rayon.
	 * 
	 * @param UNS_Rayon_ID Rayon
	 */
	public void setUNS_Rayon_ID(int UNS_Rayon_ID) {
		if (UNS_Rayon_ID < 1)
			set_Value(COLUMNNAME_UNS_Rayon_ID, null);
		else
			set_Value(COLUMNNAME_UNS_Rayon_ID, Integer.valueOf(UNS_Rayon_ID));
	}

	/**
	 * Get Rayon.
	 * 
	 * @return Rayon
	 */
	public int getUNS_Rayon_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_UNS_Rayon_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Search Key.
	 * 
	 * @param Value Search key for the record in the format required - must be
	 *              unique
	 */
	public void setValue(String Value) {
		set_Value(COLUMNNAME_Value, Value);
	}

	/**
	 * Get Search Key.
	 * 
	 * @return Search key for the record in the format required - must be unique
	 */
	public String getValue() {
		return (String) get_Value(COLUMNNAME_Value);
	}
	
	/**
	 * Set Cluster Area.
	 * 
	 * @param UNS_Cluster_ID Cluster Area
	 */
	public void setUNS_Cluster_ID(int UNS_Cluster_ID) {
		if (UNS_Cluster_ID < 1)
			set_Value(COLUMNNAME_UNS_Cluster_ID, null);
		else
			set_Value(COLUMNNAME_UNS_Cluster_ID, Integer.valueOf(UNS_Cluster_ID));
	}

	/**
	 * Get Cluster Area.
	 * 
	 * @return Cluster Area
	 */
	public int getUNS_Cluster_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_UNS_Cluster_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Market Area (Pasar).
	 * 
	 * @param UNS_Pasar_ID Market Area (Pasar)
	 */
	public void setUNS_Pasar_ID(int UNS_Pasar_ID) {
		if (UNS_Pasar_ID < 1)
			set_Value(COLUMNNAME_UNS_Pasar_ID, null);
		else
			set_Value(COLUMNNAME_UNS_Pasar_ID, Integer.valueOf(UNS_Pasar_ID));
	}

	/**
	 * Get Market Area (Pasar).
	 * 
	 * @return Market Area (Pasar)
	 */
	public int getUNS_Pasar_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_UNS_Pasar_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Latitude.
	 * 
	 * @param Latitude The latitude of an area, minus means south otherwise point to
	 *                 north
	 */
	public void setLatitude(BigDecimal Latitude) {
		set_ValueNoCheck(COLUMNNAME_Latitude, Latitude);
	}

	/**
	 * Get Latitude.
	 * 
	 * @return The latitude of an area, minus means south otherwise point to north
	 */
	public BigDecimal getLatitude() {
		BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Latitude);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Longitude.
	 * 
	 * @param Longitude Longitude of an area. Minus value point to West, otherwise
	 *                  point to East.
	 */
	public void setLongitude(BigDecimal Longitude) {
		set_ValueNoCheck(COLUMNNAME_Longitude, Longitude);
	}

	/**
	 * Get Longitude.
	 * 
	 * @return Longitude of an area. Minus value point to West, otherwise point to
	 *         East.
	 */
	public BigDecimal getLongitude() {
		BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Longitude);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

}