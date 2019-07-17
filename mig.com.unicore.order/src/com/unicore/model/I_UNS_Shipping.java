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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_Shipping
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_Shipping 
{

    /** TableName=UNS_Shipping */
    public static final String Table_Name = "UNS_Shipping";

    /** AD_Table_ID=1000267 */
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

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/** Set Address.
	  * Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID);

	/** Get Address.
	  * Location or Address
	  */
	public int getC_Location_ID();

	public org.compiere.model.I_C_Location getC_Location() throws RuntimeException;

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

    /** Column name Destination_ID */
    public static final String COLUMNNAME_Destination_ID = "Destination_ID";

	/** Set Destination	  */
	public void setDestination_ID (int Destination_ID);

	/** Get Destination	  */
	public int getDestination_ID();

	public org.compiere.model.I_C_City getDestination() throws RuntimeException;

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

    /** Column name Driver */
    public static final String COLUMNNAME_Driver = "Driver";

	/** Set Driver	  */
	public void setDriver (String Driver);

	/** Get Driver	  */
	public String getDriver();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();

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

    /** Column name Helper1 */
    public static final String COLUMNNAME_Helper1 = "Helper1";

	/** Set Helper 1	  */
	public void setHelper1 (String Helper1);

	/** Get Helper 1	  */
	public String getHelper1();

    /** Column name Helper1_ID */
    public static final String COLUMNNAME_Helper1_ID = "Helper1_ID";

	/** Set Helper 1	  */
	public void setHelper1_ID (int Helper1_ID);

	/** Get Helper 1	  */
	public int getHelper1_ID();

    /** Column name Helper2 */
    public static final String COLUMNNAME_Helper2 = "Helper2";

	/** Set Helper 2	  */
	public void setHelper2 (String Helper2);

	/** Get Helper 2	  */
	public String getHelper2();

    /** Column name Helper2_ID */
    public static final String COLUMNNAME_Helper2_ID = "Helper2_ID";

	/** Set Helper 2	  */
	public void setHelper2_ID (int Helper2_ID);

	/** Get Helper 2	  */
	public int getHelper2_ID();

    /** Column name Helper3 */
    public static final String COLUMNNAME_Helper3 = "Helper3";

	/** Set Helper 3	  */
	public void setHelper3 (String Helper3);

	/** Get Helper 3	  */
	public String getHelper3();

    /** Column name Helper3_ID */
    public static final String COLUMNNAME_Helper3_ID = "Helper3_ID";

	/** Set Helper 3	  */
	public void setHelper3_ID (int Helper3_ID);

	/** Get Helper 3	  */
	public int getHelper3_ID();

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

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Sales Transaction.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx);

	/** Get Sales Transaction.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

    /** Column name LastOM */
    public static final String COLUMNNAME_LastOM = "LastOM";

	/** Set Last Odometer.
	  * Last Odometer
	  */
	public void setLastOM (BigDecimal LastOM);

	/** Get Last Odometer.
	  * Last Odometer
	  */
	public BigDecimal getLastOM();

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

    /** Column name Origin_ID */
    public static final String COLUMNNAME_Origin_ID = "Origin_ID";

	/** Set Origin	  */
	public void setOrigin_ID (int Origin_ID);

	/** Get Origin	  */
	public int getOrigin_ID();

	public org.compiere.model.I_C_City getOrigin() throws RuntimeException;

    /** Column name PaymentTerm */
    public static final String COLUMNNAME_PaymentTerm = "PaymentTerm";

	/** Set Payment Term.
	  * Payment Term
	  */
	public void setPaymentTerm (String PaymentTerm);

	/** Get Payment Term.
	  * Payment Term
	  */
	public String getPaymentTerm();

    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/** Set Posted.
	  * Posting status
	  */
	public void setPosted (boolean Posted);

	/** Get Posted.
	  * Posting status
	  */
	public boolean isPosted();

    /** Column name PrevOM */
    public static final String COLUMNNAME_PrevOM = "PrevOM";

	/** Set Previous Odometer	  */
	public void setPrevOM (BigDecimal PrevOM);

	/** Get Previous Odometer	  */
	public BigDecimal getPrevOM();

    /** Column name PrintCost */
    public static final String COLUMNNAME_PrintCost = "PrintCost";

	/** Set Printing Cost	  */
	public void setPrintCost (String PrintCost);

	/** Get Printing Cost	  */
	public String getPrintCost();

    /** Column name PrintDeliveryOrder */
    public static final String COLUMNNAME_PrintDeliveryOrder = "PrintDeliveryOrder";

	/** Set Print Delivery Order	  */
	public void setPrintDeliveryOrder (String PrintDeliveryOrder);

	/** Get Print Delivery Order	  */
	public String getPrintDeliveryOrder();

    /** Column name PrintDocument */
    public static final String COLUMNNAME_PrintDocument = "PrintDocument";

	/** Set Print Document	  */
	public void setPrintDocument (String PrintDocument);

	/** Get Print Document	  */
	public String getPrintDocument();

    /** Column name PrintFuelVoucher */
    public static final String COLUMNNAME_PrintFuelVoucher = "PrintFuelVoucher";

	/** Set Print Fuel Voucher	  */
	public void setPrintFuelVoucher (String PrintFuelVoucher);

	/** Get Print Fuel Voucher	  */
	public String getPrintFuelVoucher();

    /** Column name PrintSPK */
    public static final String COLUMNNAME_PrintSPK = "PrintSPK";

	/** Set Print SPK	  */
	public void setPrintSPK (String PrintSPK);

	/** Get Print SPK	  */
	public String getPrintSPK();

    /** Column name PrintVoyageVoucher */
    public static final String COLUMNNAME_PrintVoyageVoucher = "PrintVoyageVoucher";

	/** Set Print Voyage Voucher	  */
	public void setPrintVoyageVoucher (String PrintVoyageVoucher);

	/** Get Print Voyage Voucher	  */
	public String getPrintVoyageVoucher();

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

    /** Column name ProcessedOn */
    public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";

	/** Set Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn);

	/** Get Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/** Set Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo);

	/** Get Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo();

    /** Column name Reversal_ID */
    public static final String COLUMNNAME_Reversal_ID = "Reversal_ID";

	/** Set Reversal ID.
	  * ID of document reversal
	  */
	public void setReversal_ID (int Reversal_ID);

	/** Get Reversal ID.
	  * ID of document reversal
	  */
	public int getReversal_ID();

	public com.unicore.model.I_UNS_Shipping getReversal() throws RuntimeException;

    /** Column name RitArmada */
    public static final String COLUMNNAME_RitArmada = "RitArmada";

	/** Set Rit Armada	  */
	public void setRitArmada (String RitArmada);

	/** Get Rit Armada	  */
	public String getRitArmada();

    /** Column name RitDriver */
    public static final String COLUMNNAME_RitDriver = "RitDriver";

	/** Set Driver Ritase	  */
	public void setRitDriver (String RitDriver);

	/** Get Driver Ritase	  */
	public String getRitDriver();

    /** Column name RitHelper1 */
    public static final String COLUMNNAME_RitHelper1 = "RitHelper1";

	/** Set Helper 1 Ritase	  */
	public void setRitHelper1 (String RitHelper1);

	/** Get Helper 1 Ritase	  */
	public String getRitHelper1();

    /** Column name RitHelper2 */
    public static final String COLUMNNAME_RitHelper2 = "RitHelper2";

	/** Set Rit Helper 2	  */
	public void setRitHelper2 (String RitHelper2);

	/** Get Rit Helper 2	  */
	public String getRitHelper2();

    /** Column name RitHelper3 */
    public static final String COLUMNNAME_RitHelper3 = "RitHelper3";

	/** Set Ritase Helper 3	  */
	public void setRitHelper3 (String RitHelper3);

	/** Get Ritase Helper 3	  */
	public String getRitHelper3();

    /** Column name ShippingType */
    public static final String COLUMNNAME_ShippingType = "ShippingType";

	/** Set Shipping Type	  */
	public void setShippingType (String ShippingType);

	/** Get Shipping Type	  */
	public String getShippingType();

    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/** Set Status.
	  * Status of the currently running check
	  */
	public void setStatus (String Status);

	/** Get Status.
	  * Status of the currently running check
	  */
	public String getStatus();

    /** Column name Tonase */
    public static final String COLUMNNAME_Tonase = "Tonase";

	/** Set Tonase.
	  * Indicate total tonase
	  */
	public void setTonase (BigDecimal Tonase);

	/** Get Tonase.
	  * Indicate total tonase
	  */
	public BigDecimal getTonase();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

    /** Column name UNS_Armada_ID */
    public static final String COLUMNNAME_UNS_Armada_ID = "UNS_Armada_ID";

	/** Set Armada	  */
	public void setUNS_Armada_ID (int UNS_Armada_ID);

	/** Get Armada	  */
	public int getUNS_Armada_ID();

    /** Column name UNS_Employee_ID */
    public static final String COLUMNNAME_UNS_Employee_ID = "UNS_Employee_ID";

	/** Set Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID);

	/** Get Employee	  */
	public int getUNS_Employee_ID();

    /** Column name UNS_Rayon_ID */
    public static final String COLUMNNAME_UNS_Rayon_ID = "UNS_Rayon_ID";

	/** Set Rayon	  */
	public void setUNS_Rayon_ID (int UNS_Rayon_ID);

	/** Get Rayon	  */
	public int getUNS_Rayon_ID();

    /** Column name UNS_Shipping_ID */
    public static final String COLUMNNAME_UNS_Shipping_ID = "UNS_Shipping_ID";

	/** Set Shipping Document	  */
	public void setUNS_Shipping_ID (int UNS_Shipping_ID);

	/** Get Shipping Document	  */
	public int getUNS_Shipping_ID();

    /** Column name UNS_Shipping_Reff_ID */
    public static final String COLUMNNAME_UNS_Shipping_Reff_ID = "UNS_Shipping_Reff_ID";

	/** Set Shipping Reference	  */
	public void setUNS_Shipping_Reff_ID (int UNS_Shipping_Reff_ID);

	/** Get Shipping Reference	  */
	public int getUNS_Shipping_Reff_ID();

	public com.unicore.model.I_UNS_Shipping getUNS_Shipping_Reff() throws RuntimeException;

    /** Column name UNS_Shipping_UU */
    public static final String COLUMNNAME_UNS_Shipping_UU = "UNS_Shipping_UU";

	/** Set UNS_Shipping_UU	  */
	public void setUNS_Shipping_UU (String UNS_Shipping_UU);

	/** Get UNS_Shipping_UU	  */
	public String getUNS_Shipping_UU();

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

    /** Column name UseCity */
    public static final String COLUMNNAME_UseCity = "UseCity";

	/** Set Use City	  */
	public void setUseCity (boolean UseCity);

	/** Get Use City	  */
	public boolean isUseCity();

    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/** Set Volume.
	  * Volume of a product
	  */
	public void setVolume (BigDecimal Volume);

	/** Get Volume.
	  * Volume of a product
	  */
	public BigDecimal getVolume();
}
