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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_BCNumber_GeneratorLine
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_BCNumber_GeneratorLine extends PO implements I_UNS_BCNumber_GeneratorLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130730L;

    /** Standard Constructor */
    public X_UNS_BCNumber_GeneratorLine (Properties ctx, int UNS_BCNumber_GeneratorLine_ID, String trxName)
    {
      super (ctx, UNS_BCNumber_GeneratorLine_ID, trxName);
      /** if (UNS_BCNumber_GeneratorLine_ID == 0)
        {
			setAD_OrgTrx_ID (0);
			setBCCodeNo (null);
			setBCNo (null);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setUNS_BCNumber_Generator_ID (0);
			setUNS_BCNumber_GeneratorLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_BCNumber_GeneratorLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_BCNumber_GeneratorLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Org getAD_OrgTrx() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Org)MTable.get(getCtx(), org.compiere.model.I_AD_Org.Table_Name)
			.getPO(getAD_OrgTrx_ID(), get_TrxName());	}

	/** Set Trx Organization.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Organization.
		@return Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BC Code No.
		@param BCCodeNo 
		Number reference of BC Code (Customer / Clearance)
	  */
	public void setBCCodeNo (String BCCodeNo)
	{
		set_Value (COLUMNNAME_BCCodeNo, BCCodeNo);
	}

	/** Get BC Code No.
		@return Number reference of BC Code (Customer / Clearance)
	  */
	public String getBCCodeNo () 
	{
		return (String)get_Value(COLUMNNAME_BCCodeNo);
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

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	public com.uns.model.I_UNS_BCNumber_Generator getUNS_BCNumber_Generator() throws RuntimeException
    {
		return (com.uns.model.I_UNS_BCNumber_Generator)MTable.get(getCtx(), com.uns.model.I_UNS_BCNumber_Generator.Table_Name)
			.getPO(getUNS_BCNumber_Generator_ID(), get_TrxName());	}

	/** Set BC Number Generator.
		@param UNS_BCNumber_Generator_ID BC Number Generator	  */
	public void setUNS_BCNumber_Generator_ID (int UNS_BCNumber_Generator_ID)
	{
		if (UNS_BCNumber_Generator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BCNumber_Generator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BCNumber_Generator_ID, Integer.valueOf(UNS_BCNumber_Generator_ID));
	}

	/** Get BC Number Generator.
		@return BC Number Generator	  */
	public int getUNS_BCNumber_Generator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BCNumber_Generator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BC Number Generator Line.
		@param UNS_BCNumber_GeneratorLine_ID BC Number Generator Line	  */
	public void setUNS_BCNumber_GeneratorLine_ID (int UNS_BCNumber_GeneratorLine_ID)
	{
		if (UNS_BCNumber_GeneratorLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_BCNumber_GeneratorLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_BCNumber_GeneratorLine_ID, Integer.valueOf(UNS_BCNumber_GeneratorLine_ID));
	}

	/** Get BC Number Generator Line.
		@return BC Number Generator Line	  */
	public int getUNS_BCNumber_GeneratorLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_BCNumber_GeneratorLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_BCNumber_GeneratorLine_UU.
		@param UNS_BCNumber_GeneratorLine_UU UNS_BCNumber_GeneratorLine_UU	  */
	public void setUNS_BCNumber_GeneratorLine_UU (String UNS_BCNumber_GeneratorLine_UU)
	{
		set_Value (COLUMNNAME_UNS_BCNumber_GeneratorLine_UU, UNS_BCNumber_GeneratorLine_UU);
	}

	/** Get UNS_BCNumber_GeneratorLine_UU.
		@return UNS_BCNumber_GeneratorLine_UU	  */
	public String getUNS_BCNumber_GeneratorLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_BCNumber_GeneratorLine_UU);
	}
}