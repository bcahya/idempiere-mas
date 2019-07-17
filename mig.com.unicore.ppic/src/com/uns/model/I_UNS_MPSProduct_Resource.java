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

/** Generated Interface for UNS_MPSProduct_Resource
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */
public interface I_UNS_MPSProduct_Resource 
{

    /** TableName=UNS_MPSProduct_Resource */
    public static final String Table_Name = "UNS_MPSProduct_Resource";

    /** AD_Table_ID=1000335 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name ActualProduced */
    public static final String COLUMNNAME_ActualProduced = "ActualProduced";

	/** Set Total Actual Manufactured.
	  * Latest amount actually manufactured in UOM quantity
	  */
	public void setActualProduced (BigDecimal ActualProduced);

	/** Get Total Actual Manufactured.
	  * Latest amount actually manufactured in UOM quantity
	  */
	public BigDecimal getActualProduced();

    /** Column name ActualScheduledMT */
    public static final String COLUMNNAME_ActualScheduledMT = "ActualScheduledMT";

	/** Set Total Actual Scheduled (MT).
	  * Total Actual Scheduled (MT)
	  */
	public void setActualScheduledMT (BigDecimal ActualScheduledMT);

	/** Get Total Actual Scheduled (MT).
	  * Total Actual Scheduled (MT)
	  */
	public BigDecimal getActualScheduledMT();

    /** Column name ActualScheduledUOM */
    public static final String COLUMNNAME_ActualScheduledUOM = "ActualScheduledUOM";

	/** Set Total Actual Scheduled (UOM).
	  * Total Actual Scheduled (UOM)
	  */
	public void setActualScheduledUOM (BigDecimal ActualScheduledUOM);

	/** Get Total Actual Scheduled (UOM).
	  * Total Actual Scheduled (UOM)
	  */
	public BigDecimal getActualScheduledUOM();

    /** Column name ActualToOrderMT */
    public static final String COLUMNNAME_ActualToOrderMT = "ActualToOrderMT";

	/** Set Actual To Order (MT).
	  * Actual To Order (MT)
	  */
	public void setActualToOrderMT (BigDecimal ActualToOrderMT);

	/** Get Actual To Order (MT).
	  * Actual To Order (MT)
	  */
	public BigDecimal getActualToOrderMT();

    /** Column name ActualToOrderUOM */
    public static final String COLUMNNAME_ActualToOrderUOM = "ActualToOrderUOM";

	/** Set Actual To Order (UOM).
	  * Actual To Order (UOM)
	  */
	public void setActualToOrderUOM (BigDecimal ActualToOrderUOM);

	/** Get Actual To Order (UOM).
	  * Actual To Order (UOM)
	  */
	public BigDecimal getActualToOrderUOM();

    /** Column name ActualToStockMT */
    public static final String COLUMNNAME_ActualToStockMT = "ActualToStockMT";

	/** Set Master Scheduled (MT).
	  * Master Scheduled (MT)
	  */
	public void setActualToStockMT (BigDecimal ActualToStockMT);

	/** Get Master Scheduled (MT).
	  * Master Scheduled (MT)
	  */
	public BigDecimal getActualToStockMT();

    /** Column name ActualToStockUOM */
    public static final String COLUMNNAME_ActualToStockUOM = "ActualToStockUOM";

	/** Set Master Scheduled (UOM).
	  * Master Scheduled (UOM)
	  */
	public void setActualToStockUOM (BigDecimal ActualToStockUOM);

	/** Get Master Scheduled (UOM).
	  * Master Scheduled (UOM)
	  */
	public BigDecimal getActualToStockUOM();

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

    /** Column name UNS_MPSProduct_ID */
    public static final String COLUMNNAME_UNS_MPSProduct_ID = "UNS_MPSProduct_ID";

	/** Set MPS Product	  */
	public void setUNS_MPSProduct_ID (int UNS_MPSProduct_ID);

	/** Get MPS Product	  */
	public int getUNS_MPSProduct_ID();

	public com.uns.model.I_UNS_MPSProduct getUNS_MPSProduct() throws RuntimeException;

    /** Column name UNS_MPSProduct_Resource_ID */
    public static final String COLUMNNAME_UNS_MPSProduct_Resource_ID = "UNS_MPSProduct_Resource_ID";

	/** Set MPS Product Resource	  */
	public void setUNS_MPSProduct_Resource_ID (int UNS_MPSProduct_Resource_ID);

	/** Get MPS Product Resource	  */
	public int getUNS_MPSProduct_Resource_ID();

    /** Column name UNS_MPSProduct_resource_UU */
    public static final String COLUMNNAME_UNS_MPSProduct_resource_UU = "UNS_MPSProduct_resource_UU";

	/** Set UNS_MPSProduct_resource_UU	  */
	public void setUNS_MPSProduct_resource_UU (String UNS_MPSProduct_resource_UU);

	/** Get UNS_MPSProduct_resource_UU	  */
	public String getUNS_MPSProduct_resource_UU();

    /** Column name UNS_Resource_ID */
    public static final String COLUMNNAME_UNS_Resource_ID = "UNS_Resource_ID";

	/** Set Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID);

	/** Get Manufacture Resource	  */
	public int getUNS_Resource_ID();

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException;

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
