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
package com.uns.qad.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for UNS_QAStatus_NC
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAStatus_NC extends PO implements I_UNS_QAStatus_NC, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130423L;

    /** Standard Constructor */
    public X_UNS_QAStatus_NC (Properties ctx, int UNS_QAStatus_NC_ID, String trxName)
    {
      super (ctx, UNS_QAStatus_NC_ID, trxName);
      /** if (UNS_QAStatus_NC_ID == 0)
        {
			setIsComplete (null);
			setProcessed (false);
			setUNS_QAStatus_NC_ID (0);
			setUNS_QAStatusContainer_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAStatus_NC (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAStatus_NC[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Date Time.
		@param AccByDT Date Time	  */
	public void setAccByDT (Timestamp AccByDT)
	{
		set_Value (COLUMNNAME_AccByDT, AccByDT);
	}

	/** Get Date Time.
		@return Date Time	  */
	public Timestamp getAccByDT () 
	{
		return (Timestamp)get_Value(COLUMNNAME_AccByDT);
	}

	/** Set Name.
		@param AccByName Name	  */
	public void setAccByName (String AccByName)
	{
		set_Value (COLUMNNAME_AccByName, AccByName);
	}

	/** Get Name.
		@return Name	  */
	public String getAccByName () 
	{
		return (String)get_Value(COLUMNNAME_AccByName);
	}

	/** Set Date Time.
		@param ActByDT Date Time	  */
	public void setActByDT (Timestamp ActByDT)
	{
		set_Value (COLUMNNAME_ActByDT, ActByDT);
	}

	/** Get Date Time.
		@return Date Time	  */
	public Timestamp getActByDT () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ActByDT);
	}

	/** Set Name.
		@param ActByName Name	  */
	public void setActByName (String ActByName)
	{
		set_Value (COLUMNNAME_ActByName, ActByName);
	}

	/** Get Name.
		@return Name	  */
	public String getActByName () 
	{
		return (String)get_Value(COLUMNNAME_ActByName);
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

	/** Set Complete.
		@param IsComplete 
		It is complete
	  */
	public void setIsComplete (String IsComplete)
	{
		set_Value (COLUMNNAME_IsComplete, IsComplete);
	}

	/** Get Complete.
		@return It is complete
	  */
	public String getIsComplete () 
	{
		return (String)get_Value(COLUMNNAME_IsComplete);
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

	/** Set Proposed Corective Action.
		@param ProposedCorectiveAction Proposed Corective Action	  */
	public void setProposedCorectiveAction (String ProposedCorectiveAction)
	{
		set_Value (COLUMNNAME_ProposedCorectiveAction, ProposedCorectiveAction);
	}

	/** Get Proposed Corective Action.
		@return Proposed Corective Action	  */
	public String getProposedCorectiveAction () 
	{
		return (String)get_Value(COLUMNNAME_ProposedCorectiveAction);
	}

	/** Set QA Disposition.
		@param QADisposition 
		QA Disposition and/or Proposed Corrective. Action for Non-conformances
	  */
	public void setQADisposition (String QADisposition)
	{
		set_Value (COLUMNNAME_QADisposition, QADisposition);
	}

	/** Get QA Disposition.
		@return QA Disposition and/or Proposed Corrective. Action for Non-conformances
	  */
	public String getQADisposition () 
	{
		return (String)get_Value(COLUMNNAME_QADisposition);
	}

	/** Set QA Status Non-Conformance.
		@param UNS_QAStatus_NC_ID QA Status Non-Conformance	  */
	public void setUNS_QAStatus_NC_ID (int UNS_QAStatus_NC_ID)
	{
		if (UNS_QAStatus_NC_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_NC_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_NC_ID, Integer.valueOf(UNS_QAStatus_NC_ID));
	}

	/** Get QA Status Non-Conformance.
		@return QA Status Non-Conformance	  */
	public int getUNS_QAStatus_NC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_NC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAStatus_NC_UU.
		@param UNS_QAStatus_NC_UU UNS_QAStatus_NC_UU	  */
	public void setUNS_QAStatus_NC_UU (String UNS_QAStatus_NC_UU)
	{
		set_Value (COLUMNNAME_UNS_QAStatus_NC_UU, UNS_QAStatus_NC_UU);
	}

	/** Get UNS_QAStatus_NC_UU.
		@return UNS_QAStatus_NC_UU	  */
	public String getUNS_QAStatus_NC_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAStatus_NC_UU);
	}

	public com.uns.qad.model.I_UNS_QAStatusContainer getUNS_QAStatusContainer() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAStatusContainer)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAStatusContainer.Table_Name)
			.getPO(getUNS_QAStatusContainer_ID(), get_TrxName());	}

	/** Set QA Status Container.
		@param UNS_QAStatusContainer_ID QA Status Container	  */
	public void setUNS_QAStatusContainer_ID (int UNS_QAStatusContainer_ID)
	{
		if (UNS_QAStatusContainer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatusContainer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatusContainer_ID, Integer.valueOf(UNS_QAStatusContainer_ID));
	}

	/** Get QA Status Container.
		@return QA Status Container	  */
	public int getUNS_QAStatusContainer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatusContainer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Time.
		@param VrfByDT Date Time	  */
	public void setVrfByDT (Timestamp VrfByDT)
	{
		set_Value (COLUMNNAME_VrfByDT, VrfByDT);
	}

	/** Get Date Time.
		@return Date Time	  */
	public Timestamp getVrfByDT () 
	{
		return (Timestamp)get_Value(COLUMNNAME_VrfByDT);
	}

	/** Set Name.
		@param VrfByName Name	  */
	public void setVrfByName (String VrfByName)
	{
		set_Value (COLUMNNAME_VrfByName, VrfByName);
	}

	/** Get Name.
		@return Name	  */
	public String getVrfByName () 
	{
		return (String)get_Value(COLUMNNAME_VrfByName);
	}
}