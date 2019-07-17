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

/** Generated Model for UNS_AttConfiguration
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_AttConfiguration extends PO implements I_UNS_AttConfiguration, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160529L;

    /** Standard Constructor */
    public X_UNS_AttConfiguration (Properties ctx, int UNS_AttConfiguration_ID, String trxName)
    {
      super (ctx, UNS_AttConfiguration_ID, trxName);
      /** if (UNS_AttConfiguration_ID == 0)
        {
			setEarlierFSOutPresence (null);
			setLateFSInPresence (null);
			setMaxEarlierFSIn (0);
			setMaxEarLierFSOut (0);
			setMaxLateFSIn (0);
			setMaxLateFSOut (0);
			setSingleFSPresence (null);
			setUNS_AttConfiguration_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_UNS_AttConfiguration (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_AttConfiguration[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Full Day = FLD */
	public static final String EARLIERFSOUTPRESENCE_FullDay = "FLD";
	/** Half Day = HLD */
	public static final String EARLIERFSOUTPRESENCE_HalfDay = "HLD";
	/** Izin = IZN */
	public static final String EARLIERFSOUTPRESENCE_Izin = "IZN";
	/** Libur = LBR */
	public static final String EARLIERFSOUTPRESENCE_Libur = "LBR";
	/** Lembur = LMR */
	public static final String EARLIERFSOUTPRESENCE_Lembur = "LMR";
	/** Mangkir = MKR */
	public static final String EARLIERFSOUTPRESENCE_Mangkir = "MKR";
	/** Reversed = RVD */
	public static final String EARLIERFSOUTPRESENCE_Reversed = "RVD";
	/** Reversal = RVL */
	public static final String EARLIERFSOUTPRESENCE_Reversal = "RVL";
	/** Set EarlierFSOutPresence.
		@param EarlierFSOutPresence EarlierFSOutPresence	  */
	public void setEarlierFSOutPresence (String EarlierFSOutPresence)
	{

		set_Value (COLUMNNAME_EarlierFSOutPresence, EarlierFSOutPresence);
	}

	/** Get EarlierFSOutPresence.
		@return EarlierFSOutPresence	  */
	public String getEarlierFSOutPresence () 
	{
		return (String)get_Value(COLUMNNAME_EarlierFSOutPresence);
	}

	/** Company = COM */
	public static final String EMPLOYMENTTYPE_Company = "COM";
	/** Sub Contract = SUB */
	public static final String EMPLOYMENTTYPE_SubContract = "SUB";
	/** Set Employment Type.
		@param EmploymentType Employment Type	  */
	public void setEmploymentType (String EmploymentType)
	{

		set_Value (COLUMNNAME_EmploymentType, EmploymentType);
	}

	/** Get Employment Type.
		@return Employment Type	  */
	public String getEmploymentType () 
	{
		return (String)get_Value(COLUMNNAME_EmploymentType);
	}

	/** Full Day = FLD */
	public static final String LATEFSINPRESENCE_FullDay = "FLD";
	/** Half Day = HLD */
	public static final String LATEFSINPRESENCE_HalfDay = "HLD";
	/** Izin = IZN */
	public static final String LATEFSINPRESENCE_Izin = "IZN";
	/** Libur = LBR */
	public static final String LATEFSINPRESENCE_Libur = "LBR";
	/** Lembur = LMR */
	public static final String LATEFSINPRESENCE_Lembur = "LMR";
	/** Mangkir = MKR */
	public static final String LATEFSINPRESENCE_Mangkir = "MKR";
	/** Reversed = RVD */
	public static final String LATEFSINPRESENCE_Reversed = "RVD";
	/** Reversal = RVL */
	public static final String LATEFSINPRESENCE_Reversal = "RVL";
	/** Set LateFSInPresence.
		@param LateFSInPresence LateFSInPresence	  */
	public void setLateFSInPresence (String LateFSInPresence)
	{

		set_Value (COLUMNNAME_LateFSInPresence, LateFSInPresence);
	}

	/** Get LateFSInPresence.
		@return LateFSInPresence	  */
	public String getLateFSInPresence () 
	{
		return (String)get_Value(COLUMNNAME_LateFSInPresence);
	}

	/** Set MaxEarlierFSIn.
		@param MaxEarlierFSIn MaxEarlierFSIn	  */
	public void setMaxEarlierFSIn (int MaxEarlierFSIn)
	{
		set_Value (COLUMNNAME_MaxEarlierFSIn, Integer.valueOf(MaxEarlierFSIn));
	}

	/** Get MaxEarlierFSIn.
		@return MaxEarlierFSIn	  */
	public int getMaxEarlierFSIn () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxEarlierFSIn);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MaxEarLierFSOut.
		@param MaxEarLierFSOut MaxEarLierFSOut	  */
	public void setMaxEarLierFSOut (int MaxEarLierFSOut)
	{
		set_Value (COLUMNNAME_MaxEarLierFSOut, Integer.valueOf(MaxEarLierFSOut));
	}

	/** Get MaxEarLierFSOut.
		@return MaxEarLierFSOut	  */
	public int getMaxEarLierFSOut () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxEarLierFSOut);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MaxLateFSIn.
		@param MaxLateFSIn MaxLateFSIn	  */
	public void setMaxLateFSIn (int MaxLateFSIn)
	{
		set_Value (COLUMNNAME_MaxLateFSIn, Integer.valueOf(MaxLateFSIn));
	}

	/** Get MaxLateFSIn.
		@return MaxLateFSIn	  */
	public int getMaxLateFSIn () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxLateFSIn);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MaxLateFSOut.
		@param MaxLateFSOut MaxLateFSOut	  */
	public void setMaxLateFSOut (int MaxLateFSOut)
	{
		set_Value (COLUMNNAME_MaxLateFSOut, Integer.valueOf(MaxLateFSOut));
	}

	/** Get MaxLateFSOut.
		@return MaxLateFSOut	  */
	public int getMaxLateFSOut () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxLateFSOut);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_C_BPartner getSectionOfDept() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getSectionOfDept_ID(), get_TrxName());	}

	/** Set SectionOfDept_ID.
		@param SectionOfDept_ID SectionOfDept_ID	  */
	public void setSectionOfDept_ID (int SectionOfDept_ID)
	{
		if (SectionOfDept_ID < 1) 
			set_Value (COLUMNNAME_SectionOfDept_ID, null);
		else 
			set_Value (COLUMNNAME_SectionOfDept_ID, Integer.valueOf(SectionOfDept_ID));
	}

	/** Get SectionOfDept_ID.
		@return SectionOfDept_ID	  */
	public int getSectionOfDept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SectionOfDept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Full Day = FLD */
	public static final String SINGLEFSPRESENCE_FullDay = "FLD";
	/** Half Day = HLD */
	public static final String SINGLEFSPRESENCE_HalfDay = "HLD";
	/** Izin = IZN */
	public static final String SINGLEFSPRESENCE_Izin = "IZN";
	/** Libur = LBR */
	public static final String SINGLEFSPRESENCE_Libur = "LBR";
	/** Lembur = LMR */
	public static final String SINGLEFSPRESENCE_Lembur = "LMR";
	/** Mangkir = MKR */
	public static final String SINGLEFSPRESENCE_Mangkir = "MKR";
	/** Reversed = RVD */
	public static final String SINGLEFSPRESENCE_Reversed = "RVD";
	/** Reversal = RVL */
	public static final String SINGLEFSPRESENCE_Reversal = "RVL";
	/** Set SingleFSPresence.
		@param SingleFSPresence SingleFSPresence	  */
	public void setSingleFSPresence (String SingleFSPresence)
	{

		set_Value (COLUMNNAME_SingleFSPresence, SingleFSPresence);
	}

	/** Get SingleFSPresence.
		@return SingleFSPresence	  */
	public String getSingleFSPresence () 
	{
		return (String)get_Value(COLUMNNAME_SingleFSPresence);
	}

	/** Set UNS_AttConfiguration_ID.
		@param UNS_AttConfiguration_ID UNS_AttConfiguration_ID	  */
	public void setUNS_AttConfiguration_ID (int UNS_AttConfiguration_ID)
	{
		if (UNS_AttConfiguration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_AttConfiguration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_AttConfiguration_ID, Integer.valueOf(UNS_AttConfiguration_ID));
	}

	/** Get UNS_AttConfiguration_ID.
		@return UNS_AttConfiguration_ID	  */
	public int getUNS_AttConfiguration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_AttConfiguration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_AttConfiguration_UU.
		@param UNS_AttConfiguration_UU UNS_AttConfiguration_UU	  */
	public void setUNS_AttConfiguration_UU (String UNS_AttConfiguration_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_AttConfiguration_UU, UNS_AttConfiguration_UU);
	}

	/** Get UNS_AttConfiguration_UU.
		@return UNS_AttConfiguration_UU	  */
	public String getUNS_AttConfiguration_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_AttConfiguration_UU);
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

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}