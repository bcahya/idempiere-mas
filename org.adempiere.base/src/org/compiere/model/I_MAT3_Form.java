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

/** Generated Interface for MAT3_Form
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_MAT3_Form 
{

    /** TableName=MAT3_Form */
    public static final String Table_Name = "MAT3_Form";

    /** AD_Table_ID=1000380 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Form_ID */
    public static final String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/** Set Special Form.
	  * Special Form
	  */
	public void setAD_Form_ID (int AD_Form_ID);

	/** Get Special Form.
	  * Special Form
	  */
	public int getAD_Form_ID();

	public org.compiere.model.I_AD_Form getAD_Form() throws RuntimeException;

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AccessLevel */
    public static final String COLUMNNAME_AccessLevel = "AccessLevel";

	/** Set Data Access Level.
	  * Access Level required
	  */
	public void setAccessLevel (String AccessLevel);

	/** Get Data Access Level.
	  * Access Level required
	  */
	public String getAccessLevel();

    /** Column name Classname */
    public static final String COLUMNNAME_Classname = "Classname";

	/** Set Classname.
	  * Java Classname
	  */
	public void setClassname (String Classname);

	/** Get Classname.
	  * Java Classname
	  */
	public String getClassname();

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

    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/** Set Entity Type.
	  * Dictionary Entity Type;
 Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType);

	/** Get Entity Type.
	  * Dictionary Entity Type;
 Determines ownership and synchronization
	  */
	public String getEntityType();

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

    /** Column name IsBetaFunctionality */
    public static final String COLUMNNAME_IsBetaFunctionality = "IsBetaFunctionality";

	/** Set Beta Functionality.
	  * This functionality is considered Beta
	  */
	public void setIsBetaFunctionality (boolean IsBetaFunctionality);

	/** Get Beta Functionality.
	  * This functionality is considered Beta
	  */
	public boolean isBetaFunctionality();

    /** Column name MAT3BusinessLogicClass */
    public static final String COLUMNNAME_MAT3BusinessLogicClass = "MAT3BusinessLogicClass";

	/** Set Business Logic Class.
	  * Business Logic Class
	  */
	public void setMAT3BusinessLogicClass (String MAT3BusinessLogicClass);

	/** Get Business Logic Class.
	  * Business Logic Class
	  */
	public String getMAT3BusinessLogicClass();

    /** Column name MAT3ConfirmPanel */
    public static final String COLUMNNAME_MAT3ConfirmPanel = "MAT3ConfirmPanel";

	/** Set MAT3ConfirmPanel	  */
	public void setMAT3ConfirmPanel (boolean MAT3ConfirmPanel);

	/** Get MAT3ConfirmPanel	  */
	public boolean isMAT3ConfirmPanel();

    /** Column name MAT3ConfirmPanel_withCancel */
    public static final String COLUMNNAME_MAT3ConfirmPanel_withCancel = "MAT3ConfirmPanel_withCancel";

	/** Set MAT3ConfirmPanel_withCancel	  */
	public void setMAT3ConfirmPanel_withCancel (boolean MAT3ConfirmPanel_withCancel);

	/** Get MAT3ConfirmPanel_withCancel	  */
	public boolean isMAT3ConfirmPanel_withCancel();

    /** Column name MAT3ConfirmPanel_withRefresh */
    public static final String COLUMNNAME_MAT3ConfirmPanel_withRefresh = "MAT3ConfirmPanel_withRefresh";

	/** Set MAT3ConfirmPanel_withRefresh	  */
	public void setMAT3ConfirmPanel_withRefresh (boolean MAT3ConfirmPanel_withRefresh);

	/** Get MAT3ConfirmPanel_withRefresh	  */
	public boolean isMAT3ConfirmPanel_withRefresh();

    /** Column name MAT3FormPreview */
    public static final String COLUMNNAME_MAT3FormPreview = "MAT3FormPreview";

	/** Set Preview	  */
	public void setMAT3FormPreview (String MAT3FormPreview);

	/** Get Preview	  */
	public String getMAT3FormPreview();

    /** Column name MAT3IsModal */
    public static final String COLUMNNAME_MAT3IsModal = "MAT3IsModal";

	/** Set Is Modal	  */
	public void setMAT3IsModal (boolean MAT3IsModal);

	/** Get Is Modal	  */
	public boolean isMAT3IsModal();

    /** Column name MAT3ListenerClass */
    public static final String COLUMNNAME_MAT3ListenerClass = "MAT3ListenerClass";

	/** Set Listener Class.
	  * Listener Class
	  */
	public void setMAT3ListenerClass (String MAT3ListenerClass);

	/** Get Listener Class.
	  * Listener Class
	  */
	public String getMAT3ListenerClass();

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

    /** Column name MAT3WebuiListenerClass */
    public static final String COLUMNNAME_MAT3WebuiListenerClass = "MAT3WebuiListenerClass";

	/** Set Webui Listener Class.
	  * Listener Class for ZK web user interface
	  */
	public void setMAT3WebuiListenerClass (String MAT3WebuiListenerClass);

	/** Get Webui Listener Class.
	  * Listener Class for ZK web user interface
	  */
	public String getMAT3WebuiListenerClass();

    /** Column name MAT3_Form_ID */
    public static final String COLUMNNAME_MAT3_Form_ID = "MAT3_Form_ID";

	/** Set MAT3_Form	  */
	public void setMAT3_Form_ID (int MAT3_Form_ID);

	/** Get MAT3_Form	  */
	public int getMAT3_Form_ID();

    /** Column name MAT3_Form_UU */
    public static final String COLUMNNAME_MAT3_Form_UU = "MAT3_Form_UU";

	/** Set MAT3_Form_UU.
	  * MAT3_Form_UU
	  */
	public void setMAT3_Form_UU (String MAT3_Form_UU);

	/** Get MAT3_Form_UU.
	  * MAT3_Form_UU
	  */
	public String getMAT3_Form_UU();

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
}
