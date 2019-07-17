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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for MAT3_FormContainer
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_MAT3_FormContainer 
{

    /** TableName=MAT3_FormContainer */
    public static final String Table_Name = "MAT3_FormContainer";

    /** AD_Table_ID=1000384 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Company.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

    /** Column name ContainerNo */
    public static final String COLUMNNAME_ContainerNo = "ContainerNo";

	/** Set Container No.
	  * The container number mainly used as the number of context idenfication
	  */
	public void setContainerNo (int ContainerNo);

	/** Get Container No.
	  * The container number mainly used as the number of context idenfication
	  */
	public int getContainerNo();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Height */
    public static final String COLUMNNAME_Height = "Height";

	/** Set Height	  */
	public void setHeight (int Height);

	/** Get Height	  */
	public int getHeight();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name MAT3_ContainerParent_ID */
    public static final String COLUMNNAME_MAT3_ContainerParent_ID = "MAT3_ContainerParent_ID";

	/** Set Container Parent	  */
	public void setMAT3_ContainerParent_ID (int MAT3_ContainerParent_ID);

	/** Get Container Parent	  */
	public int getMAT3_ContainerParent_ID();

	public org.compiere.model.I_MAT3_FormContainer getMAT3_ContainerParent() throws RuntimeException;

    /** Column name MAT3_Form_ID */
    public static final String COLUMNNAME_MAT3_Form_ID = "MAT3_Form_ID";

	/** Set Form	  */
	public void setMAT3_Form_ID (int MAT3_Form_ID);

	/** Get Form	  */
	public int getMAT3_Form_ID();

	public org.compiere.model.I_MAT3_Form getMAT3_Form() throws RuntimeException;

    /** Column name MAT3_FormContainer_ID */
    public static final String COLUMNNAME_MAT3_FormContainer_ID = "MAT3_FormContainer_ID";

	/** Set Form Container	  */
	public void setMAT3_FormContainer_ID (int MAT3_FormContainer_ID);

	/** Get Form Container	  */
	public int getMAT3_FormContainer_ID();

    /** Column name MAT3_FormContainer_UU */
    public static final String COLUMNNAME_MAT3_FormContainer_UU = "MAT3_FormContainer_UU";

	/** Set MAT3_FormContainer_UU.
	  * MAT3_FormContainer_UU
	  */
	public void setMAT3_FormContainer_UU (String MAT3_FormContainer_UU);

	/** Get MAT3_FormContainer_UU.
	  * MAT3_FormContainer_UU
	  */
	public String getMAT3_FormContainer_UU();

    /** Column name MAT3ColPos */
    public static final String COLUMNNAME_MAT3ColPos = "MAT3ColPos";

	/** Set Column Pos	  */
	public void setMAT3ColPos (int MAT3ColPos);

	/** Get Column Pos	  */
	public int getMAT3ColPos();

    /** Column name MAT3ColSpan */
    public static final String COLUMNNAME_MAT3ColSpan = "MAT3ColSpan";

	/** Set Column Span.
	  * Column Span
	  */
	public void setMAT3ColSpan (int MAT3ColSpan);

	/** Get Column Span.
	  * Column Span
	  */
	public int getMAT3ColSpan();

    /** Column name MAT3ContainerType */
    public static final String COLUMNNAME_MAT3ContainerType = "MAT3ContainerType";

	/** Set Container Type	  */
	public void setMAT3ContainerType (String MAT3ContainerType);

	/** Get Container Type	  */
	public String getMAT3ContainerType();

    /** Column name MAT3NCols */
    public static final String COLUMNNAME_MAT3NCols = "MAT3NCols";

	/** Set N. Columns.
	  * Number of columns
	  */
	public void setMAT3NCols (int MAT3NCols);

	/** Get N. Columns.
	  * Number of columns
	  */
	public int getMAT3NCols();

    /** Column name MAT3NRows */
    public static final String COLUMNNAME_MAT3NRows = "MAT3NRows";

	/** Set N. Rows.
	  * Number of rows
	  */
	public void setMAT3NRows (int MAT3NRows);

	/** Get N. Rows.
	  * Number of rows
	  */
	public int getMAT3NRows();

    /** Column name MAT3RowPos */
    public static final String COLUMNNAME_MAT3RowPos = "MAT3RowPos";

	/** Set Row Pos	  */
	public void setMAT3RowPos (int MAT3RowPos);

	/** Get Row Pos	  */
	public int getMAT3RowPos();

    /** Column name MAT3RowSpan */
    public static final String COLUMNNAME_MAT3RowSpan = "MAT3RowSpan";

	/** Set Row Span.
	  * Row Span
	  */
	public void setMAT3RowSpan (int MAT3RowSpan);

	/** Get Row Span.
	  * Row Span
	  */
	public int getMAT3RowSpan();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name SelectionMode */
    public static final String COLUMNNAME_SelectionMode = "SelectionMode";

	/** Set Selection Mode	  */
	public void setSelectionMode (String SelectionMode);

	/** Get Selection Mode	  */
	public String getSelectionMode();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name Width */
    public static final String COLUMNNAME_Width = "Width";

	/** Set Width	  */
	public void setWidth (int Width);

	/** Get Width	  */
	public int getWidth();
}
