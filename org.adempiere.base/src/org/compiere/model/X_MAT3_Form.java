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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for MAT3_Form
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_MAT3_Form extends PO implements I_MAT3_Form, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131115L;

    /** Standard Constructor */
    public X_MAT3_Form (Properties ctx, int MAT3_Form_ID, String trxName)
    {
      super (ctx, MAT3_Form_ID, trxName);
      /** if (MAT3_Form_ID == 0)
        {
			setAccessLevel (null);
			setEntityType (null);
// U
			setIsBetaFunctionality (false);
			setMAT3FormPreview (null);
			setMAT3_Form_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_MAT3_Form (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_MAT3_Form[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Form getAD_Form() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Form)MTable.get(getCtx(), org.compiere.model.I_AD_Form.Table_Name)
			.getPO(getAD_Form_ID(), get_TrxName());	}

	/** Set Special Form.
		@param AD_Form_ID 
		Special Form
	  */
	public void setAD_Form_ID (int AD_Form_ID)
	{
		if (AD_Form_ID < 1) 
			set_Value (COLUMNNAME_AD_Form_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Form_ID, Integer.valueOf(AD_Form_ID));
	}

	/** Get Special Form.
		@return Special Form
	  */
	public int getAD_Form_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Form_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** AccessLevel AD_Reference_ID=5 */
	public static final int ACCESSLEVEL_AD_Reference_ID=5;
	/** Organization = 1 */
	public static final String ACCESSLEVEL_Organization = "1";
	/** Client+Organization = 3 */
	public static final String ACCESSLEVEL_ClientPlusOrganization = "3";
	/** System only = 4 */
	public static final String ACCESSLEVEL_SystemOnly = "4";
	/** All = 7 */
	public static final String ACCESSLEVEL_All = "7";
	/** System+Client = 6 */
	public static final String ACCESSLEVEL_SystemPlusClient = "6";
	/** Client only = 2 */
	public static final String ACCESSLEVEL_ClientOnly = "2";
	/** Set Data Access Level.
		@param AccessLevel 
		Access Level required
	  */
	public void setAccessLevel (String AccessLevel)
	{

		set_Value (COLUMNNAME_AccessLevel, AccessLevel);
	}

	/** Get Data Access Level.
		@return Access Level required
	  */
	public String getAccessLevel () 
	{
		return (String)get_Value(COLUMNNAME_AccessLevel);
	}

	/** Set Classname.
		@param Classname 
		Java Classname
	  */
	public void setClassname (String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	/** Get Classname.
		@return Java Classname
	  */
	public String getClassname () 
	{
		return (String)get_Value(COLUMNNAME_Classname);
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

	/** EntityType AD_Reference_ID=389 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entity Type.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entity Type.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	public String getEntityType () 
	{
		return (String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Beta Functionality.
		@param IsBetaFunctionality 
		This functionality is considered Beta
	  */
	public void setIsBetaFunctionality (boolean IsBetaFunctionality)
	{
		set_Value (COLUMNNAME_IsBetaFunctionality, Boolean.valueOf(IsBetaFunctionality));
	}

	/** Get Beta Functionality.
		@return This functionality is considered Beta
	  */
	public boolean isBetaFunctionality () 
	{
		Object oo = get_Value(COLUMNNAME_IsBetaFunctionality);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Business Logic Class.
		@param MAT3BusinessLogicClass 
		Business Logic Class
	  */
	public void setMAT3BusinessLogicClass (String MAT3BusinessLogicClass)
	{
		set_Value (COLUMNNAME_MAT3BusinessLogicClass, MAT3BusinessLogicClass);
	}

	/** Get Business Logic Class.
		@return Business Logic Class
	  */
	public String getMAT3BusinessLogicClass () 
	{
		return (String)get_Value(COLUMNNAME_MAT3BusinessLogicClass);
	}

	/** Set MAT3ConfirmPanel.
		@param MAT3ConfirmPanel MAT3ConfirmPanel	  */
	public void setMAT3ConfirmPanel (boolean MAT3ConfirmPanel)
	{
		set_Value (COLUMNNAME_MAT3ConfirmPanel, Boolean.valueOf(MAT3ConfirmPanel));
	}

	/** Get MAT3ConfirmPanel.
		@return MAT3ConfirmPanel	  */
	public boolean isMAT3ConfirmPanel () 
	{
		Object oo = get_Value(COLUMNNAME_MAT3ConfirmPanel);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set MAT3ConfirmPanel_withCancel.
		@param MAT3ConfirmPanel_withCancel MAT3ConfirmPanel_withCancel	  */
	public void setMAT3ConfirmPanel_withCancel (boolean MAT3ConfirmPanel_withCancel)
	{
		set_Value (COLUMNNAME_MAT3ConfirmPanel_withCancel, Boolean.valueOf(MAT3ConfirmPanel_withCancel));
	}

	/** Get MAT3ConfirmPanel_withCancel.
		@return MAT3ConfirmPanel_withCancel	  */
	public boolean isMAT3ConfirmPanel_withCancel () 
	{
		Object oo = get_Value(COLUMNNAME_MAT3ConfirmPanel_withCancel);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set MAT3ConfirmPanel_withRefresh.
		@param MAT3ConfirmPanel_withRefresh MAT3ConfirmPanel_withRefresh	  */
	public void setMAT3ConfirmPanel_withRefresh (boolean MAT3ConfirmPanel_withRefresh)
	{
		set_Value (COLUMNNAME_MAT3ConfirmPanel_withRefresh, Boolean.valueOf(MAT3ConfirmPanel_withRefresh));
	}

	/** Get MAT3ConfirmPanel_withRefresh.
		@return MAT3ConfirmPanel_withRefresh	  */
	public boolean isMAT3ConfirmPanel_withRefresh () 
	{
		Object oo = get_Value(COLUMNNAME_MAT3ConfirmPanel_withRefresh);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Preview.
		@param MAT3FormPreview Preview	  */
	public void setMAT3FormPreview (String MAT3FormPreview)
	{
		set_Value (COLUMNNAME_MAT3FormPreview, MAT3FormPreview);
	}

	/** Get Preview.
		@return Preview	  */
	public String getMAT3FormPreview () 
	{
		return (String)get_Value(COLUMNNAME_MAT3FormPreview);
	}

	/** Set Is Modal.
		@param MAT3IsModal Is Modal	  */
	public void setMAT3IsModal (boolean MAT3IsModal)
	{
		set_Value (COLUMNNAME_MAT3IsModal, Boolean.valueOf(MAT3IsModal));
	}

	/** Get Is Modal.
		@return Is Modal	  */
	public boolean isMAT3IsModal () 
	{
		Object oo = get_Value(COLUMNNAME_MAT3IsModal);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Listener Class.
		@param MAT3ListenerClass 
		Listener Class
	  */
	public void setMAT3ListenerClass (String MAT3ListenerClass)
	{
		set_Value (COLUMNNAME_MAT3ListenerClass, MAT3ListenerClass);
	}

	/** Get Listener Class.
		@return Listener Class
	  */
	public String getMAT3ListenerClass () 
	{
		return (String)get_Value(COLUMNNAME_MAT3ListenerClass);
	}

	/** Set N. Columns.
		@param MAT3NCols 
		Number of columns
	  */
	public void setMAT3NCols (int MAT3NCols)
	{
		set_Value (COLUMNNAME_MAT3NCols, Integer.valueOf(MAT3NCols));
	}

	/** Get N. Columns.
		@return Number of columns
	  */
	public int getMAT3NCols () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3NCols);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set N. Rows.
		@param MAT3NRows 
		Number of rows
	  */
	public void setMAT3NRows (int MAT3NRows)
	{
		set_Value (COLUMNNAME_MAT3NRows, Integer.valueOf(MAT3NRows));
	}

	/** Get N. Rows.
		@return Number of rows
	  */
	public int getMAT3NRows () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3NRows);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Webui Listener Class.
		@param MAT3WebuiListenerClass 
		Listener Class for ZK web user interface
	  */
	public void setMAT3WebuiListenerClass (String MAT3WebuiListenerClass)
	{
		set_Value (COLUMNNAME_MAT3WebuiListenerClass, MAT3WebuiListenerClass);
	}

	/** Get Webui Listener Class.
		@return Listener Class for ZK web user interface
	  */
	public String getMAT3WebuiListenerClass () 
	{
		return (String)get_Value(COLUMNNAME_MAT3WebuiListenerClass);
	}

	/** Set MAT3_Form.
		@param MAT3_Form_ID MAT3_Form	  */
	public void setMAT3_Form_ID (int MAT3_Form_ID)
	{
		if (MAT3_Form_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MAT3_Form_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MAT3_Form_ID, Integer.valueOf(MAT3_Form_ID));
	}

	/** Get MAT3_Form.
		@return MAT3_Form	  */
	public int getMAT3_Form_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3_Form_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MAT3_Form_UU.
		@param MAT3_Form_UU 
		MAT3_Form_UU
	  */
	public void setMAT3_Form_UU (String MAT3_Form_UU)
	{
		set_ValueNoCheck (COLUMNNAME_MAT3_Form_UU, MAT3_Form_UU);
	}

	/** Get MAT3_Form_UU.
		@return MAT3_Form_UU
	  */
	public String getMAT3_Form_UU () 
	{
		return (String)get_Value(COLUMNNAME_MAT3_Form_UU);
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
}