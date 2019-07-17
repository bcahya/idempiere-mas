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

/** Generated Model for MAT3_FormField
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_MAT3_FormField extends PO implements I_MAT3_FormField, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131115L;

    /** Standard Constructor */
    public X_MAT3_FormField (Properties ctx, int MAT3_FormField_ID, String trxName)
    {
      super (ctx, MAT3_FormField_ID, trxName);
      /** if (MAT3_FormField_ID == 0)
        {
			setEntityType (null);
// U
			setIsAutocomplete (false);
// 'N'
			setIsCentrallyMaintained (true);
// Y
			setIsDisplayed (true);
// Y
			setIsEncrypted (false);
			setIsFieldOnly (false);
			setIsHeading (false);
			setIsReadOnly (false);
			setIsSameLine (false);
// N
			setIsUpdateable (true);
// Y
			setMAT3_FormContainer_ID (0);
			setMAT3_FormField_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_MAT3_FormField (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_MAT3_FormField[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_FieldGroup getAD_FieldGroup() throws RuntimeException
    {
		return (org.compiere.model.I_AD_FieldGroup)MTable.get(getCtx(), org.compiere.model.I_AD_FieldGroup.Table_Name)
			.getPO(getAD_FieldGroup_ID(), get_TrxName());	}

	/** Set Field Group.
		@param AD_FieldGroup_ID 
		Logical grouping of fields
	  */
	public void setAD_FieldGroup_ID (int AD_FieldGroup_ID)
	{
		if (AD_FieldGroup_ID < 1) 
			set_Value (COLUMNNAME_AD_FieldGroup_ID, null);
		else 
			set_Value (COLUMNNAME_AD_FieldGroup_ID, Integer.valueOf(AD_FieldGroup_ID));
	}

	/** Get Field Group.
		@return Logical grouping of fields
	  */
	public int getAD_FieldGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_FieldGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_ValueNoCheck (COLUMNNAME_AD_Form_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Form_ID, Integer.valueOf(AD_Form_ID));
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

	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Process)MTable.get(getCtx(), org.compiere.model.I_AD_Process.Table_Name)
			.getPO(getAD_Process_ID(), get_TrxName());	}

	/** Set Process.
		@param AD_Process_ID 
		Process or Report
	  */
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Process.
		@return Process or Report
	  */
	public int getAD_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Reference)MTable.get(getCtx(), org.compiere.model.I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_ID(), get_TrxName());	}

	/** Set Reference.
		@param AD_Reference_ID 
		System Reference and Validation
	  */
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Reference.
		@return System Reference and Validation
	  */
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Reference getAD_Reference_Value() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Reference)MTable.get(getCtx(), org.compiere.model.I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_Value_ID(), get_TrxName());	}

	/** Set Reference Key.
		@param AD_Reference_Value_ID 
		Required to specify, if data type is Table or List
	  */
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID)
	{
		if (AD_Reference_Value_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, Integer.valueOf(AD_Reference_Value_ID));
	}

	/** Get Reference Key.
		@return Required to specify, if data type is Table or List
	  */
	public int getAD_Reference_Value_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_Value_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Val_Rule)MTable.get(getCtx(), org.compiere.model.I_AD_Val_Rule.Table_Name)
			.getPO(getAD_Val_Rule_ID(), get_TrxName());	}

	/** Set Dynamic Validation.
		@param AD_Val_Rule_ID 
		Dynamic Validation Rule
	  */
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, Integer.valueOf(AD_Val_Rule_ID));
	}

	/** Get Dynamic Validation.
		@return Dynamic Validation Rule
	  */
	public int getAD_Val_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Val_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Callout.
		@param Callout 
		Fully qualified class names and method - separated by semicolons
	  */
	public void setCallout (String Callout)
	{
		set_Value (COLUMNNAME_Callout, Callout);
	}

	/** Get Callout.
		@return Fully qualified class names and method - separated by semicolons
	  */
	public String getCallout () 
	{
		return (String)get_Value(COLUMNNAME_Callout);
	}

	/** Set DB Column Name.
		@param ColumnName 
		Name of the column in the database
	  */
	public void setColumnName (String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	/** Get DB Column Name.
		@return Name of the column in the database
	  */
	public String getColumnName () 
	{
		return (String)get_Value(COLUMNNAME_ColumnName);
	}

	/** Set Default Logic.
		@param DefaultValue 
		Default value hierarchy, separated by ;
	  */
	public void setDefaultValue (String DefaultValue)
	{
		set_Value (COLUMNNAME_DefaultValue, DefaultValue);
	}

	/** Get Default Logic.
		@return Default value hierarchy, separated by ;
	  */
	public String getDefaultValue () 
	{
		return (String)get_Value(COLUMNNAME_DefaultValue);
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

	/** Set Display Length.
		@param DisplayLength 
		Length of the display in characters
	  */
	public void setDisplayLength (int DisplayLength)
	{
		set_Value (COLUMNNAME_DisplayLength, Integer.valueOf(DisplayLength));
	}

	/** Get Display Length.
		@return Length of the display in characters
	  */
	public int getDisplayLength () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DisplayLength);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Display Logic.
		@param DisplayLogic 
		If the Field is displayed, the result determines if the field is actually displayed
	  */
	public void setDisplayLogic (String DisplayLogic)
	{
		set_Value (COLUMNNAME_DisplayLogic, DisplayLogic);
	}

	/** Get Display Logic.
		@return If the Field is displayed, the result determines if the field is actually displayed
	  */
	public String getDisplayLogic () 
	{
		return (String)get_Value(COLUMNNAME_DisplayLogic);
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

	/** Set Format Pattern.
		@param FormatPattern 
		The pattern used to format a number or date.
	  */
	public void setFormatPattern (String FormatPattern)
	{
		set_Value (COLUMNNAME_FormatPattern, FormatPattern);
	}

	/** Get Format Pattern.
		@return The pattern used to format a number or date.
	  */
	public String getFormatPattern () 
	{
		return (String)get_Value(COLUMNNAME_FormatPattern);
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

	/** Set Info Factory Class.
		@param InfoFactoryClass 
		Fully qualified class name that implements the InfoFactory interface
	  */
	public void setInfoFactoryClass (String InfoFactoryClass)
	{
		set_Value (COLUMNNAME_InfoFactoryClass, InfoFactoryClass);
	}

	/** Get Info Factory Class.
		@return Fully qualified class name that implements the InfoFactory interface
	  */
	public String getInfoFactoryClass () 
	{
		return (String)get_Value(COLUMNNAME_InfoFactoryClass);
	}

	/** Set Autocomplete.
		@param IsAutocomplete 
		Automatic completion for textfields
	  */
	public void setIsAutocomplete (boolean IsAutocomplete)
	{
		set_Value (COLUMNNAME_IsAutocomplete, Boolean.valueOf(IsAutocomplete));
	}

	/** Get Autocomplete.
		@return Automatic completion for textfields
	  */
	public boolean isAutocomplete () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutocomplete);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Centrally maintained.
		@param IsCentrallyMaintained 
		Information maintained in System Element table
	  */
	public void setIsCentrallyMaintained (boolean IsCentrallyMaintained)
	{
		set_Value (COLUMNNAME_IsCentrallyMaintained, Boolean.valueOf(IsCentrallyMaintained));
	}

	/** Get Centrally maintained.
		@return Information maintained in System Element table
	  */
	public boolean isCentrallyMaintained () 
	{
		Object oo = get_Value(COLUMNNAME_IsCentrallyMaintained);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Displayed.
		@param IsDisplayed 
		Determines, if this field is displayed
	  */
	public void setIsDisplayed (boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, Boolean.valueOf(IsDisplayed));
	}

	/** Get Displayed.
		@return Determines, if this field is displayed
	  */
	public boolean isDisplayed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisplayed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Encrypted.
		@param IsEncrypted 
		Display or Storage is encrypted
	  */
	public void setIsEncrypted (boolean IsEncrypted)
	{
		set_Value (COLUMNNAME_IsEncrypted, Boolean.valueOf(IsEncrypted));
	}

	/** Get Encrypted.
		@return Display or Storage is encrypted
	  */
	public boolean isEncrypted () 
	{
		Object oo = get_Value(COLUMNNAME_IsEncrypted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Field Only.
		@param IsFieldOnly 
		Label is not displayed
	  */
	public void setIsFieldOnly (boolean IsFieldOnly)
	{
		set_Value (COLUMNNAME_IsFieldOnly, Boolean.valueOf(IsFieldOnly));
	}

	/** Get Field Only.
		@return Label is not displayed
	  */
	public boolean isFieldOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsFieldOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Heading only.
		@param IsHeading 
		Field without Column - Only label is displayed
	  */
	public void setIsHeading (boolean IsHeading)
	{
		set_Value (COLUMNNAME_IsHeading, Boolean.valueOf(IsHeading));
	}

	/** Get Heading only.
		@return Field without Column - Only label is displayed
	  */
	public boolean isHeading () 
	{
		Object oo = get_Value(COLUMNNAME_IsHeading);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mandatory.
		@param IsMandatory 
		Data entry is required in this column
	  */
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Mandatory.
		@return Data entry is required in this column
	  */
	public boolean isMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Read Only.
		@param IsReadOnly 
		Field is read only
	  */
	public void setIsReadOnly (boolean IsReadOnly)
	{
		set_Value (COLUMNNAME_IsReadOnly, Boolean.valueOf(IsReadOnly));
	}

	/** Get Read Only.
		@return Field is read only
	  */
	public boolean isReadOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Same Line.
		@param IsSameLine 
		Displayed on same line as previous field
	  */
	public void setIsSameLine (boolean IsSameLine)
	{
		set_Value (COLUMNNAME_IsSameLine, Boolean.valueOf(IsSameLine));
	}

	/** Get Same Line.
		@return Displayed on same line as previous field
	  */
	public boolean isSameLine () 
	{
		Object oo = get_Value(COLUMNNAME_IsSameLine);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Updatable.
		@param IsUpdateable 
		Determines, if the field can be updated
	  */
	public void setIsUpdateable (boolean IsUpdateable)
	{
		set_Value (COLUMNNAME_IsUpdateable, Boolean.valueOf(IsUpdateable));
	}

	/** Get Updatable.
		@return Determines, if the field can be updated
	  */
	public boolean isUpdateable () 
	{
		Object oo = get_Value(COLUMNNAME_IsUpdateable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set MAT3ColPos.
		@param MAT3ColPos MAT3ColPos	  */
	public void setMAT3ColPos (int MAT3ColPos)
	{
		set_Value (COLUMNNAME_MAT3ColPos, Integer.valueOf(MAT3ColPos));
	}

	/** Get MAT3ColPos.
		@return MAT3ColPos	  */
	public int getMAT3ColPos () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3ColPos);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MAT3EditorClass.
		@param MAT3EditorClass MAT3EditorClass	  */
	public void setMAT3EditorClass (String MAT3EditorClass)
	{
		set_Value (COLUMNNAME_MAT3EditorClass, MAT3EditorClass);
	}

	/** Get MAT3EditorClass.
		@return MAT3EditorClass	  */
	public String getMAT3EditorClass () 
	{
		return (String)get_Value(COLUMNNAME_MAT3EditorClass);
	}

	/** Set MAT3RowPos.
		@param MAT3RowPos MAT3RowPos	  */
	public void setMAT3RowPos (int MAT3RowPos)
	{
		set_Value (COLUMNNAME_MAT3RowPos, Integer.valueOf(MAT3RowPos));
	}

	/** Get MAT3RowPos.
		@return MAT3RowPos	  */
	public int getMAT3RowPos () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3RowPos);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MAT3RowSpan.
		@param MAT3RowSpan MAT3RowSpan	  */
	public void setMAT3RowSpan (int MAT3RowSpan)
	{
		set_Value (COLUMNNAME_MAT3RowSpan, Integer.valueOf(MAT3RowSpan));
	}

	/** Get MAT3RowSpan.
		@return MAT3RowSpan	  */
	public int getMAT3RowSpan () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3RowSpan);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_MAT3_FormContainer getMAT3_FormContainer() throws RuntimeException
    {
		return (org.compiere.model.I_MAT3_FormContainer)MTable.get(getCtx(), org.compiere.model.I_MAT3_FormContainer.Table_Name)
			.getPO(getMAT3_FormContainer_ID(), get_TrxName());	}

	/** Set MAT3_FormContainer.
		@param MAT3_FormContainer_ID MAT3_FormContainer	  */
	public void setMAT3_FormContainer_ID (int MAT3_FormContainer_ID)
	{
		if (MAT3_FormContainer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MAT3_FormContainer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MAT3_FormContainer_ID, Integer.valueOf(MAT3_FormContainer_ID));
	}

	/** Get MAT3_FormContainer.
		@return MAT3_FormContainer	  */
	public int getMAT3_FormContainer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3_FormContainer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MAT3_FormField.
		@param MAT3_FormField_ID MAT3_FormField	  */
	public void setMAT3_FormField_ID (int MAT3_FormField_ID)
	{
		if (MAT3_FormField_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MAT3_FormField_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MAT3_FormField_ID, Integer.valueOf(MAT3_FormField_ID));
	}

	/** Get MAT3_FormField.
		@return MAT3_FormField	  */
	public int getMAT3_FormField_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MAT3_FormField_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MAT3_FormField_UU.
		@param MAT3_FormField_UU 
		MAT3_FormField_UU
	  */
	public void setMAT3_FormField_UU (String MAT3_FormField_UU)
	{
		set_ValueNoCheck (COLUMNNAME_MAT3_FormField_UU, MAT3_FormField_UU);
	}

	/** Get MAT3_FormField_UU.
		@return MAT3_FormField_UU
	  */
	public String getMAT3_FormField_UU () 
	{
		return (String)get_Value(COLUMNNAME_MAT3_FormField_UU);
	}

	/** Set Mandatory Logic.
		@param MandatoryLogic Mandatory Logic	  */
	public void setMandatoryLogic (String MandatoryLogic)
	{
		set_Value (COLUMNNAME_MandatoryLogic, MandatoryLogic);
	}

	/** Get Mandatory Logic.
		@return Mandatory Logic	  */
	public String getMandatoryLogic () 
	{
		return (String)get_Value(COLUMNNAME_MandatoryLogic);
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

	/** ObscureType AD_Reference_ID=291 */
	public static final int OBSCURETYPE_AD_Reference_ID=291;
	/** Obscure Digits but last 4 = 904 */
	public static final String OBSCURETYPE_ObscureDigitsButLast4 = "904";
	/** Obscure Digits but first/last 4 = 944 */
	public static final String OBSCURETYPE_ObscureDigitsButFirstLast4 = "944";
	/** Obscure AlphaNumeric but first/last 4 = A44 */
	public static final String OBSCURETYPE_ObscureAlphaNumericButFirstLast4 = "A44";
	/** Obscure AlphaNumeric but last 4 = A04 */
	public static final String OBSCURETYPE_ObscureAlphaNumericButLast4 = "A04";
	/** Set Obscure.
		@param ObscureType 
		Type of obscuring the data (limiting the display)
	  */
	public void setObscureType (String ObscureType)
	{

		set_Value (COLUMNNAME_ObscureType, ObscureType);
	}

	/** Get Obscure.
		@return Type of obscuring the data (limiting the display)
	  */
	public String getObscureType () 
	{
		return (String)get_Value(COLUMNNAME_ObscureType);
	}

	/** Set Preferred Width.
		@param PreferredWidth 
		Preferred width in pixels
	  */
	public void setPreferredWidth (int PreferredWidth)
	{
		set_Value (COLUMNNAME_PreferredWidth, Integer.valueOf(PreferredWidth));
	}

	/** Get Preferred Width.
		@return Preferred width in pixels
	  */
	public int getPreferredWidth () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PreferredWidth);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Read Only Logic.
		@param ReadOnlyLogic 
		Logic to determine if field is read only (applies only when field is read-write)
	  */
	public void setReadOnlyLogic (String ReadOnlyLogic)
	{
		set_Value (COLUMNNAME_ReadOnlyLogic, ReadOnlyLogic);
	}

	/** Get Read Only Logic.
		@return Logic to determine if field is read only (applies only when field is read-write)
	  */
	public String getReadOnlyLogic () 
	{
		return (String)get_Value(COLUMNNAME_ReadOnlyLogic);
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Value Format.
		@param VFormat 
		Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public void setVFormat (String VFormat)
	{
		set_Value (COLUMNNAME_VFormat, VFormat);
	}

	/** Get Value Format.
		@return Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public String getVFormat () 
	{
		return (String)get_Value(COLUMNNAME_VFormat);
	}

	/** Set Max. Value.
		@param ValueMax 
		Maximum Value for a field
	  */
	public void setValueMax (String ValueMax)
	{
		set_Value (COLUMNNAME_ValueMax, ValueMax);
	}

	/** Get Max. Value.
		@return Maximum Value for a field
	  */
	public String getValueMax () 
	{
		return (String)get_Value(COLUMNNAME_ValueMax);
	}

	/** Set Min. Value.
		@param ValueMin 
		Minimum Value for a field
	  */
	public void setValueMin (String ValueMin)
	{
		set_Value (COLUMNNAME_ValueMin, ValueMin);
	}

	/** Get Min. Value.
		@return Minimum Value for a field
	  */
	public String getValueMin () 
	{
		return (String)get_Value(COLUMNNAME_ValueMin);
	}
}