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
package com.uns.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_PSRealization
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_PSRealization 
{

    /** TableName=UNS_PSRealization */
    public static final String Table_Name = "UNS_PSRealization";

    /** AD_Table_ID=1000095 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

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

    /** Column name QtyMT */
    public static final String COLUMNNAME_QtyMT = "QtyMT";

	/** Set Qty MT	  */
	public void setQtyMT (BigDecimal QtyMT);

	/** Get Qty MT	  */
	public BigDecimal getQtyMT();

    /** Column name QtyUom */
    public static final String COLUMNNAME_QtyUom = "QtyUom";

	/** Set Qty UOM	  */
	public void setQtyUom (BigDecimal QtyUom);

	/** Get Qty UOM	  */
	public BigDecimal getQtyUom();

    /** Column name Realization_PD */
    public static final String COLUMNNAME_Realization_PD = "Realization_PD";

	/** Set Realization Production Date	  */
	public void setRealization_PD (Timestamp Realization_PD);

	/** Get Realization Production Date	  */
	public Timestamp getRealization_PD();

    /** Column name Targeted_PD */
    public static final String COLUMNNAME_Targeted_PD = "Targeted_PD";

	/** Set Targeted Production Date	  */
	public void setTargeted_PD (Timestamp Targeted_PD);

	/** Get Targeted Production Date	  */
	public Timestamp getTargeted_PD();

    /** Column name UNS_Production_Detail_ID */
    public static final String COLUMNNAME_UNS_Production_Detail_ID = "UNS_Production_Detail_ID";

	/** Set UNS Production Detail	  */
	public void setUNS_Production_Detail_ID (int UNS_Production_Detail_ID);

	/** Get UNS Production Detail	  */
	public int getUNS_Production_Detail_ID();

    /** Column name UNS_ProductionSchedule_ID */
    public static final String COLUMNNAME_UNS_ProductionSchedule_ID = "UNS_ProductionSchedule_ID";

	/** Set Production Schedule	  */
	public void setUNS_ProductionSchedule_ID (int UNS_ProductionSchedule_ID);

	/** Get Production Schedule	  */
	public int getUNS_ProductionSchedule_ID();

	public com.uns.model.I_UNS_ProductionSchedule getUNS_ProductionSchedule() throws RuntimeException;

    /** Column name UNS_PSRealization_ID */
    public static final String COLUMNNAME_UNS_PSRealization_ID = "UNS_PSRealization_ID";

	/** Set Production Schedule Realization	  */
	public void setUNS_PSRealization_ID (int UNS_PSRealization_ID);

	/** Get Production Schedule Realization	  */
	public int getUNS_PSRealization_ID();

    /** Column name UNS_PSRealization_UU */
    public static final String COLUMNNAME_UNS_PSRealization_UU = "UNS_PSRealization_UU";

	/** Set UNS_PSRealization_UU	  */
	public void setUNS_PSRealization_UU (String UNS_PSRealization_UU);

	/** Get UNS_PSRealization_UU	  */
	public String getUNS_PSRealization_UU();

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
