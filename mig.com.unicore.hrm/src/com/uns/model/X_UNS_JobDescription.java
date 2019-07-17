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

/** Generated Model for UNS_JobDescription
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_JobDescription extends PO implements I_UNS_JobDescription, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130321L;

    /** Standard Constructor */
    public X_UNS_JobDescription (Properties ctx, int UNS_JobDescription_ID, String trxName)
    {
      super (ctx, UNS_JobDescription_ID, trxName);
      /** if (UNS_JobDescription_ID == 0)
        {
			setUNS_JobDescription_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_JobDescription (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_JobDescription[")
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

	public org.compiere.model.I_C_Job getC_Job() throws RuntimeException
    {
		return (org.compiere.model.I_C_Job)MTable.get(getCtx(), org.compiere.model.I_C_Job.Table_Name)
			.getPO(getC_Job_ID(), get_TrxName());	}

	/** Set Position.
		@param C_Job_ID 
		Job Position
	  */
	public void setC_Job_ID (int C_Job_ID)
	{
		if (C_Job_ID < 1) 
			set_Value (COLUMNNAME_C_Job_ID, null);
		else 
			set_Value (COLUMNNAME_C_Job_ID, Integer.valueOf(C_Job_ID));
	}

	/** Get Position.
		@return Job Position
	  */
	public int getC_Job_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Job_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Job Description.
		@param JobDescription Job Description	  */
	public void setJobDescription (String JobDescription)
	{
		set_Value (COLUMNNAME_JobDescription, JobDescription);
	}

	/** Get Job Description.
		@return Job Description	  */
	public String getJobDescription () 
	{
		return (String)get_Value(COLUMNNAME_JobDescription);
	}

	/** Set Job Specification.
		@param JobSpecification Job Specification	  */
	public void setJobSpecification (String JobSpecification)
	{
		set_Value (COLUMNNAME_JobSpecification, JobSpecification);
	}

	/** Get Job Specification.
		@return Job Specification	  */
	public String getJobSpecification () 
	{
		return (String)get_Value(COLUMNNAME_JobSpecification);
	}

	/** Set Job Role.
		@param UNS_Job_Role_ID Job Role	  */
	public void setUNS_Job_Role_ID (int UNS_Job_Role_ID)
	{
		if (UNS_Job_Role_ID < 1) 
			set_Value (COLUMNNAME_UNS_Job_Role_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Job_Role_ID, Integer.valueOf(UNS_Job_Role_ID));
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

	/** Set Job Description.
		@param UNS_JobDescription_ID Job Description	  */
	public void setUNS_JobDescription_ID (int UNS_JobDescription_ID)
	{
		if (UNS_JobDescription_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_JobDescription_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_JobDescription_ID, Integer.valueOf(UNS_JobDescription_ID));
	}

	/** Get Job Description.
		@return Job Description	  */
	public int getUNS_JobDescription_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_JobDescription_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_JobDescription_UU.
		@param UNS_JobDescription_UU UNS_JobDescription_UU	  */
	public void setUNS_JobDescription_UU (String UNS_JobDescription_UU)
	{
		set_Value (COLUMNNAME_UNS_JobDescription_UU, UNS_JobDescription_UU);
	}

	/** Get UNS_JobDescription_UU.
		@return UNS_JobDescription_UU	  */
	public String getUNS_JobDescription_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_JobDescription_UU);
	}
}