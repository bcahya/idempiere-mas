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

/** Generated Interface for UNS_QALabTest
 *  @author iDempiere (generated) 
 *  @version Release 1.0a
 */

public interface I_UNS_QALabTest 
{

    /** TableName=UNS_QALabTest */
    public static final String Table_Name = "UNS_QALabTest";

    /** AD_Table_ID=1000286 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name AccByNAC */
    public static final String COLUMNNAME_AccByNAC = "AccByNAC";

	/** Set Accredited by National Accreditation Committee	  */
	public void setAccByNAC (boolean AccByNAC);

	/** Get Accredited by National Accreditation Committee	  */
	public boolean isAccByNAC();

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

    /** Column name DecimalPoint */
    public static final String COLUMNNAME_DecimalPoint = "DecimalPoint";

	/** Set Decimal Point.
	  * Decimal Point in the data file - if any
	  */
	public void setDecimalPoint (String DecimalPoint);

	/** Get Decimal Point.
	  * Decimal Point in the data file - if any
	  */
	public String getDecimalPoint();

    /** Column name Equipment */
    public static final String COLUMNNAME_Equipment = "Equipment";

	/** Set Equipment	  */
	public void setEquipment (String Equipment);

	/** Get Equipment	  */
	public String getEquipment();

    /** Column name InternationalReferences */
    public static final String COLUMNNAME_InternationalReferences = "InternationalReferences";

	/** Set International References	  */
	public void setInternationalReferences (String InternationalReferences);

	/** Get International References	  */
	public String getInternationalReferences();

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

    /** Column name MaxValue */
    public static final String COLUMNNAME_MaxValue = "MaxValue";

	/** Set Max Value	  */
	public void setMaxValue (BigDecimal MaxValue);

	/** Get Max Value	  */
	public BigDecimal getMaxValue();

    /** Column name MinValue */
    public static final String COLUMNNAME_MinValue = "MinValue";

	/** Set Min Value	  */
	public void setMinValue (BigDecimal MinValue);

	/** Get Min Value	  */
	public BigDecimal getMinValue();

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

    /** Column name NegativeResult */
    public static final String COLUMNNAME_NegativeResult = "NegativeResult";

	/** Set Negative Result	  */
	public void setNegativeResult (String NegativeResult);

	/** Get Negative Result	  */
	public String getNegativeResult();

    /** Column name ParameterValue */
    public static final String COLUMNNAME_ParameterValue = "ParameterValue";

	/** Set Parameter Value	  */
	public void setParameterValue (String ParameterValue);

	/** Get Parameter Value	  */
	public String getParameterValue();

    /** Column name PassByValue */
    public static final String COLUMNNAME_PassByValue = "PassByValue";

	/** Set Passing By Value	  */
	public void setPassByValue (boolean PassByValue);

	/** Get Passing By Value	  */
	public boolean isPassByValue();

    /** Column name PositiveResult */
    public static final String COLUMNNAME_PositiveResult = "PositiveResult";

	/** Set Positive Result	  */
	public void setPositiveResult (String PositiveResult);

	/** Get Positive Result	  */
	public String getPositiveResult();

    /** Column name ProductService_ID */
    public static final String COLUMNNAME_ProductService_ID = "ProductService_ID";

	/** Set Product Service	  */
	public void setProductService_ID (int ProductService_ID);

	/** Get Product Service	  */
	public int getProductService_ID();

	public org.compiere.model.I_M_Product getProductService() throws RuntimeException;

    /** Column name TestHour */
    public static final String COLUMNNAME_TestHour = "TestHour";

	/** Set Test Hour	  */
	public void setTestHour (BigDecimal TestHour);

	/** Get Test Hour	  */
	public BigDecimal getTestHour();

    /** Column name TestType */
    public static final String COLUMNNAME_TestType = "TestType";

	/** Set Test Type	  */
	public void setTestType (String TestType);

	/** Get Test Type	  */
	public String getTestType();

    /** Column name ThresHold_Type */
    public static final String COLUMNNAME_ThresHold_Type = "ThresHold_Type";

	/** Set Threshold Type	  */
	public void setThresHold_Type (String ThresHold_Type);

	/** Get Threshold Type	  */
	public String getThresHold_Type();

    /** Column name UNS_QALabTest_ID */
    public static final String COLUMNNAME_UNS_QALabTest_ID = "UNS_QALabTest_ID";

	/** Set Laboratory Test	  */
	public void setUNS_QALabTest_ID (int UNS_QALabTest_ID);

	/** Get Laboratory Test	  */
	public int getUNS_QALabTest_ID();

    /** Column name UNS_QALabTest_Type_ID */
    public static final String COLUMNNAME_UNS_QALabTest_Type_ID = "UNS_QALabTest_Type_ID";

	/** Set Laboratory Test Type	  */
	public void setUNS_QALabTest_Type_ID (int UNS_QALabTest_Type_ID);

	/** Get Laboratory Test Type	  */
	public int getUNS_QALabTest_Type_ID();

	public I_UNS_QALabTest_Type getUNS_QALabTest_Type() throws RuntimeException;

    /** Column name UNS_QALabTest_UU */
    public static final String COLUMNNAME_UNS_QALabTest_UU = "UNS_QALabTest_UU";

	/** Set UNS_QALabTest_UU	  */
	public void setUNS_QALabTest_UU (String UNS_QALabTest_UU);

	/** Get UNS_QALabTest_UU	  */
	public String getUNS_QALabTest_UU();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name Value2 */
    public static final String COLUMNNAME_Value2 = "Value2";

	/** Set Old Code.
	  * Old Code laboratory test.
	  */
	public void setValue2 (String Value2);

	/** Get Old Code.
	  * Old Code laboratory test.
	  */
	public String getValue2();
}
