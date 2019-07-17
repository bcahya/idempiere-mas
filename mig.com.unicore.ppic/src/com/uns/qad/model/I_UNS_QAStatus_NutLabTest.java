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
package com.uns.qad.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_QAStatus_NutLabTest
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_QAStatus_NutLabTest 
{

    /** TableName=UNS_QAStatus_NutLabTest */
    public static final String Table_Name = "UNS_QAStatus_NutLabTest";

    /** AD_Table_ID=1000242 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

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

    /** Column name AnalysisDate */
    public static final String COLUMNNAME_AnalysisDate = "AnalysisDate";

	/** Set Analysis Date	  */
	public void setAnalysisDate (Timestamp AnalysisDate);

	/** Get Analysis Date	  */
	public Timestamp getAnalysisDate();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;

    /** Column name CalculationType */
    public static final String COLUMNNAME_CalculationType = "CalculationType";

	/** Set Calculation	  */
	public void setCalculationType (String CalculationType);

	/** Get Calculation	  */
	public String getCalculationType();

    /** Column name CoconutType */
    public static final String COLUMNNAME_CoconutType = "CoconutType";

	/** Set Coconut Type	  */
	public void setCoconutType (String CoconutType);

	/** Get Coconut Type	  */
	public String getCoconutType();

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

    /** Column name DM */
    public static final String COLUMNNAME_DM = "DM";

	/** Set DM (%).
	  * Dry Matter in percent.
	  */
	public void setDM (BigDecimal DM);

	/** Get DM (%).
	  * Dry Matter in percent.
	  */
	public BigDecimal getDM();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name FatWeight */
    public static final String COLUMNNAME_FatWeight = "FatWeight";

	/** Set Fat Weight (g).
	  * Fat Weight in gram.
	  */
	public void setFatWeight (BigDecimal FatWeight);

	/** Get Fat Weight (g).
	  * Fat Weight in gram.
	  */
	public BigDecimal getFatWeight();

    /** Column name FCDB */
    public static final String COLUMNNAME_FCDB = "FCDB";

	/** Set FC DB (%).
	  * Fat Content Dry Basic in percent.
	  */
	public void setFCDB (BigDecimal FCDB);

	/** Get FC DB (%).
	  * Fat Content Dry Basic in percent.
	  */
	public BigDecimal getFCDB();

    /** Column name FCWB */
    public static final String COLUMNNAME_FCWB = "FCWB";

	/** Set FC WB (%).
	  * Fat Content Wet Basic in percent.
	  */
	public void setFCWB (BigDecimal FCWB);

	/** Get FC WB (%).
	  * Fat Content Wet Basic in percent.
	  */
	public BigDecimal getFCWB();

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

    /** Column name MC */
    public static final String COLUMNNAME_MC = "MC";

	/** Set MC (%).
	  * Moisture Content in percent.
	  */
	public void setMC (BigDecimal MC);

	/** Get MC (%).
	  * Moisture Content in percent.
	  */
	public BigDecimal getMC();

    /** Column name PickedQty */
    public static final String COLUMNNAME_PickedQty = "PickedQty";

	/** Set Picked Quantity	  */
	public void setPickedQty (BigDecimal PickedQty);

	/** Get Picked Quantity	  */
	public BigDecimal getPickedQty();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Remark */
    public static final String COLUMNNAME_Remark = "Remark";

	/** Set Remark	  */
	public void setRemark (String Remark);

	/** Get Remark	  */
	public String getRemark();

    /** Column name SampleDB */
    public static final String COLUMNNAME_SampleDB = "SampleDB";

	/** Set Sample DB (g).
	  * Sample Dry Basic in gram.
	  */
	public void setSampleDB (BigDecimal SampleDB);

	/** Get Sample DB (g).
	  * Sample Dry Basic in gram.
	  */
	public BigDecimal getSampleDB();

    /** Column name SampleDC */
    public static final String COLUMNNAME_SampleDC = "SampleDC";

	/** Set Sample Dry Nut (g).
	  * Sample Dry Coconut in gram.
	  */
	public void setSampleDC (BigDecimal SampleDC);

	/** Get Sample Dry Nut (g).
	  * Sample Dry Coconut in gram.
	  */
	public BigDecimal getSampleDC();

    /** Column name SampleWB */
    public static final String COLUMNNAME_SampleWB = "SampleWB";

	/** Set Sample WB (g).
	  * Sample Wet Basic in gram.
	  */
	public void setSampleWB (BigDecimal SampleWB);

	/** Get Sample WB (g).
	  * Sample Wet Basic in gram.
	  */
	public BigDecimal getSampleWB();

    /** Column name UNS_QAStatus_NutLabTest_ID */
    public static final String COLUMNNAME_UNS_QAStatus_NutLabTest_ID = "UNS_QAStatus_NutLabTest_ID";

	/** Set Coconut Labolatory Test	  */
	public void setUNS_QAStatus_NutLabTest_ID (int UNS_QAStatus_NutLabTest_ID);

	/** Get Coconut Labolatory Test	  */
	public int getUNS_QAStatus_NutLabTest_ID();

    /** Column name UNS_QAStatus_NutLabTest_UU */
    public static final String COLUMNNAME_UNS_QAStatus_NutLabTest_UU = "UNS_QAStatus_NutLabTest_UU";

	/** Set UNS_QAStatus_NutLabTest_UU	  */
	public void setUNS_QAStatus_NutLabTest_UU (String UNS_QAStatus_NutLabTest_UU);

	/** Get UNS_QAStatus_NutLabTest_UU	  */
	public String getUNS_QAStatus_NutLabTest_UU();

    /** Column name UNS_QAStatus_Type_ID */
    public static final String COLUMNNAME_UNS_QAStatus_Type_ID = "UNS_QAStatus_Type_ID";

	/** Set Attribute Type	  */
	public void setUNS_QAStatus_Type_ID (int UNS_QAStatus_Type_ID);

	/** Get Attribute Type	  */
	public int getUNS_QAStatus_Type_ID();

	public com.uns.qad.model.I_UNS_QAStatus_Type getUNS_QAStatus_Type() throws RuntimeException;

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

    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";

	/** Set Weight.
	  * Weight of a product
	  */
	public void setWeight (BigDecimal Weight);

	/** Get Weight.
	  * Weight of a product
	  */
	public BigDecimal getWeight();
}
