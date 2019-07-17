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

/** Generated Model for MAT3_FormContainer
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_MAT3_FormContainer extends PO implements I_MAT3_FormContainer, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131213L;

    /** Standard Constructor */
    public X_MAT3_FormContainer (Properties ctx, int MAT3_FormContainer_ID, String trxName)
    {
      super (ctx, MAT3_FormContainer_ID, trxName);
      /** if (MAT3_FormContainer_ID == 0)
        {
			setMAT3_FormContainer_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_MAT3_FormContainer (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MAT3_FormContainer[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Container No.
		@param ContainerNo 
		The container number mainly used as the number of context idenfication
	  */
	public void setContainerNo (int ContainerNo)
	{
		set_Value (COLUMNNAME_ContainerNo, Integer.valueOf(ContainerNo));
	}

	/** Get Container No.
		@return The container number mainly used as the number of context idenfication
	  */
	public int getContainerNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ContainerNo);
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

	/** Set Height.
		@param Height Height	  */
	public void setHeight (int Height)
	{
		set_Value (COLUMNNAME_Height, Integer.valueOf(Height));
	}

	/** Get Height.
		@return Height	  */
	public int getHeight () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Height);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_MAT3_FormContainer getMAT3_ContainerParent() throws RuntimeException
    {
		return (org.compiere.model.I_MAT3_FormContainer)MTable.get(getCtx(), org.compiere.model.I_MAT3_FormContainer.Table_Name)
			.getPO(getMAT3_ContainerParent_ID(), get_TrxName());	}

	/** Set Container Parent.
		@param MAT3_ContainerParent_ID Container Parent	  */
	public void setMAT3_ContainerParent_ID (int MAT3_ContainerParent_ID)
	{
		if (MAT3_ContainerParent_ID < 1) 
			set_Value (COLUMNNAME_MAT3_ContainerParent_ID, null);
		else 
			set_Value (COLUMNNAME_MAT3_ContainerParent_ID, Integer.valueOf(MAT3_ContainerParent_ID));
	}

	/** Get Container Parent.
		@return Container Parent	  */
	public int getMAT3_ContainerParent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3_ContainerParent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_MAT3_Form getMAT3_Form() throws RuntimeException
    {
		return (org.compiere.model.I_MAT3_Form)MTable.get(getCtx(), org.compiere.model.I_MAT3_Form.Table_Name)
			.getPO(getMAT3_Form_ID(), get_TrxName());	}

	/** Set Form.
		@param MAT3_Form_ID Form	  */
	public void setMAT3_Form_ID (int MAT3_Form_ID)
	{
		if (MAT3_Form_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MAT3_Form_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MAT3_Form_ID, Integer.valueOf(MAT3_Form_ID));
	}

	/** Get Form.
		@return Form	  */
	public int getMAT3_Form_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3_Form_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Form Container.
		@param MAT3_FormContainer_ID Form Container	  */
	public void setMAT3_FormContainer_ID (int MAT3_FormContainer_ID)
	{
		if (MAT3_FormContainer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MAT3_FormContainer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MAT3_FormContainer_ID, Integer.valueOf(MAT3_FormContainer_ID));
	}

	/** Get Form Container.
		@return Form Container	  */
	public int getMAT3_FormContainer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3_FormContainer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MAT3_FormContainer_UU.
		@param MAT3_FormContainer_UU 
		MAT3_FormContainer_UU
	  */
	public void setMAT3_FormContainer_UU (String MAT3_FormContainer_UU)
	{
		set_ValueNoCheck (COLUMNNAME_MAT3_FormContainer_UU, MAT3_FormContainer_UU);
	}

	/** Get MAT3_FormContainer_UU.
		@return MAT3_FormContainer_UU
	  */
	public String getMAT3_FormContainer_UU () 
	{
		return (String)get_Value(COLUMNNAME_MAT3_FormContainer_UU);
	}

	/** Set Column Pos.
		@param MAT3ColPos Column Pos	  */
	public void setMAT3ColPos (int MAT3ColPos)
	{
		set_Value (COLUMNNAME_MAT3ColPos, Integer.valueOf(MAT3ColPos));
	}

	/** Get Column Pos.
		@return Column Pos	  */
	public int getMAT3ColPos () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3ColPos);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Column Span.
		@param MAT3ColSpan 
		Column Span
	  */
	public void setMAT3ColSpan (int MAT3ColSpan)
	{
		set_Value (COLUMNNAME_MAT3ColSpan, Integer.valueOf(MAT3ColSpan));
	}

	/** Get Column Span.
		@return Column Span
	  */
	public int getMAT3ColSpan () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3ColSpan);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** MAT3ContainerType AD_Reference_ID=1000196 */
	public static final int MAT3CONTAINERTYPE_AD_Reference_ID=1000196;
	/** Container = C */
	public static final String MAT3CONTAINERTYPE_Container = "C";
	/** Table = G */
	public static final String MAT3CONTAINERTYPE_Table = "G";
	/** Panel = P */
	public static final String MAT3CONTAINERTYPE_Panel = "P";
	/** Tab = T */
	public static final String MAT3CONTAINERTYPE_Tab = "T";
	/** Set Container Type.
		@param MAT3ContainerType Container Type	  */
	public void setMAT3ContainerType (String MAT3ContainerType)
	{

		set_Value (COLUMNNAME_MAT3ContainerType, MAT3ContainerType);
	}

	/** Get Container Type.
		@return Container Type	  */
	public String getMAT3ContainerType () 
	{
		return (String)get_Value(COLUMNNAME_MAT3ContainerType);
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

	/** Set Row Pos.
		@param MAT3RowPos Row Pos	  */
	public void setMAT3RowPos (int MAT3RowPos)
	{
		set_Value (COLUMNNAME_MAT3RowPos, Integer.valueOf(MAT3RowPos));
	}

	/** Get Row Pos.
		@return Row Pos	  */
	public int getMAT3RowPos () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3RowPos);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Row Span.
		@param MAT3RowSpan 
		Row Span
	  */
	public void setMAT3RowSpan (int MAT3RowSpan)
	{
		set_Value (COLUMNNAME_MAT3RowSpan, Integer.valueOf(MAT3RowSpan));
	}

	/** Get Row Span.
		@return Row Span
	  */
	public int getMAT3RowSpan () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3RowSpan);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** SelectionMode AD_Reference_ID=1000202 */
	public static final int SELECTIONMODE_AD_Reference_ID=1000202;
	/** Selection Not Allowed = NOS */
	public static final String SELECTIONMODE_SelectionNotAllowed = "NOS";
	/** Single Selection Mode = SGL */
	public static final String SELECTIONMODE_SingleSelectionMode = "SGL";
	/** Multi Interval Selection Mode = MLTINT */
	public static final String SELECTIONMODE_MultiIntervalSelectionMode = "MLTINT";
	/** Single Interval Selection Mode = SGLINT */
	public static final String SELECTIONMODE_SingleIntervalSelectionMode = "SGLINT";
	/** Set Selection Mode.
		@param SelectionMode Selection Mode	  */
	public void setSelectionMode (String SelectionMode)
	{

		set_Value (COLUMNNAME_SelectionMode, SelectionMode);
	}

	/** Get Selection Mode.
		@return Selection Mode	  */
	public String getSelectionMode () 
	{
		return (String)get_Value(COLUMNNAME_SelectionMode);
	}

	/** Set Width.
		@param Width Width	  */
	public void setWidth (int Width)
	{
		set_Value (COLUMNNAME_Width, Integer.valueOf(Width));
	}

	/** Get Width.
		@return Width	  */
	public int getWidth () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Width);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}