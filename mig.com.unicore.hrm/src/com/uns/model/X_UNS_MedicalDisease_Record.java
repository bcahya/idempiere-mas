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

/** Generated Model for UNS_MedicalDisease_Record
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MedicalDisease_Record extends PO implements I_UNS_MedicalDisease_Record, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140205L;

    /** Standard Constructor */
    public X_UNS_MedicalDisease_Record (Properties ctx, int UNS_MedicalDisease_Record_ID, String trxName)
    {
      super (ctx, UNS_MedicalDisease_Record_ID, trxName);
      /** if (UNS_MedicalDisease_Record_ID == 0)
        {
			setUNS_DiseaseList_ID (0);
			setUNS_MedicalDisease_Record_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MedicalDisease_Record (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MedicalDisease_Record[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set pass_to_departement.
		@param pass_to_departement pass_to_departement	  */
	public void setpass_to_departement (String pass_to_departement)
	{
		set_Value (COLUMNNAME_pass_to_departement, pass_to_departement);
	}

	/** Get pass_to_departement.
		@return pass_to_departement	  */
	public String getpass_to_departement () 
	{
		return (String)get_Value(COLUMNNAME_pass_to_departement);
	}

	/** Set Remarks.
		@param Remarks Remarks	  */
	public void setRemarks (String Remarks)
	{
		set_Value (COLUMNNAME_Remarks, Remarks);
	}

	/** Get Remarks.
		@return Remarks	  */
	public String getRemarks () 
	{
		return (String)get_Value(COLUMNNAME_Remarks);
	}

	public com.uns.model.I_UNS_DiseaseList getUNS_DiseaseList() throws RuntimeException
    {
		return (com.uns.model.I_UNS_DiseaseList)MTable.get(getCtx(), com.uns.model.I_UNS_DiseaseList.Table_Name)
			.getPO(getUNS_DiseaseList_ID(), get_TrxName());	}

	/** Set Disease List.
		@param UNS_DiseaseList_ID Disease List	  */
	public void setUNS_DiseaseList_ID (int UNS_DiseaseList_ID)
	{
		if (UNS_DiseaseList_ID < 1) 
			set_Value (COLUMNNAME_UNS_DiseaseList_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_DiseaseList_ID, Integer.valueOf(UNS_DiseaseList_ID));
	}

	/** Get Disease List.
		@return Disease List	  */
	public int getUNS_DiseaseList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_DiseaseList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_MCU_Info getUNS_MCU_Info() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MCU_Info)MTable.get(getCtx(), com.uns.model.I_UNS_MCU_Info.Table_Name)
			.getPO(getUNS_MCU_Info_ID(), get_TrxName());	}

	/** Set MCU Info.
		@param UNS_MCU_Info_ID 
		UNS_MCU_Info_ID
	  */
	public void setUNS_MCU_Info_ID (int UNS_MCU_Info_ID)
	{
		if (UNS_MCU_Info_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MCU_Info_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MCU_Info_ID, Integer.valueOf(UNS_MCU_Info_ID));
	}

	/** Get MCU Info.
		@return UNS_MCU_Info_ID
	  */
	public int getUNS_MCU_Info_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MCU_Info_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Medical Disease Record.
		@param UNS_MedicalDisease_Record_ID Medical Disease Record	  */
	public void setUNS_MedicalDisease_Record_ID (int UNS_MedicalDisease_Record_ID)
	{
		if (UNS_MedicalDisease_Record_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MedicalDisease_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MedicalDisease_Record_ID, Integer.valueOf(UNS_MedicalDisease_Record_ID));
	}

	/** Get Medical Disease Record.
		@return Medical Disease Record	  */
	public int getUNS_MedicalDisease_Record_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MedicalDisease_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MedicalDisease_Record_UU.
		@param UNS_MedicalDisease_Record_UU UNS_MedicalDisease_Record_UU	  */
	public void setUNS_MedicalDisease_Record_UU (String UNS_MedicalDisease_Record_UU)
	{
		set_Value (COLUMNNAME_UNS_MedicalDisease_Record_UU, UNS_MedicalDisease_Record_UU);
	}

	/** Get UNS_MedicalDisease_Record_UU.
		@return UNS_MedicalDisease_Record_UU	  */
	public String getUNS_MedicalDisease_Record_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MedicalDisease_Record_UU);
	}

	public com.uns.model.I_UNS_MedicalRecord getUNS_MedicalRecord() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MedicalRecord)MTable.get(getCtx(), com.uns.model.I_UNS_MedicalRecord.Table_Name)
			.getPO(getUNS_MedicalRecord_ID(), get_TrxName());	}

	/** Set Medical Record.
		@param UNS_MedicalRecord_ID Medical Record	  */
	public void setUNS_MedicalRecord_ID (int UNS_MedicalRecord_ID)
	{
		if (UNS_MedicalRecord_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MedicalRecord_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MedicalRecord_ID, Integer.valueOf(UNS_MedicalRecord_ID));
	}

	/** Get Medical Record.
		@return Medical Record	  */
	public int getUNS_MedicalRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MedicalRecord_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}