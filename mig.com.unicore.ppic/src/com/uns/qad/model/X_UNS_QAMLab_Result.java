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
package com.uns.qad.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_QAMLab_Result
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAMLab_Result extends PO implements I_UNS_QAMLab_Result, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140312L;

    /** Standard Constructor */
    public X_UNS_QAMLab_Result (Properties ctx, int UNS_QAMLab_Result_ID, String trxName)
    {
      super (ctx, UNS_QAMLab_Result_ID, trxName);
      /** if (UNS_QAMLab_Result_ID == 0)
        {
			setC_DocType_ID (0);
			setCodeNo (null);
// -
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setInfo (false);
// N
			setInspectionType (null);
			setIsApproved (false);
// N
			setM_AttributeSetInstance_ID (0);
			setM_Locator_ID (0);
			setM_Product_ID (0);
			setNC (null);
// -
			setNCQty (Env.ZERO);
// 0
			setOnHold (null);
// -
			setOnHoldQty (Env.ZERO);
// 0
			setProcessed (false);
// N
			setProcessing (false);
			setQAStatus (null);
// OH
			setQty (Env.ZERO);
// 0
			setRelease (null);
// -
			setReleaseQty (Env.ZERO);
// 0
			setUNS_QAMLab_Result_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAMLab_Result (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAMLab_Result[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Batch Document No.
		@param BatchDocumentNo 
		Document Number of the Batch
	  */
	public void setBatchDocumentNo (String BatchDocumentNo)
	{
		throw new IllegalArgumentException ("BatchDocumentNo is virtual column");	}

	/** Get Batch Document No.
		@return Document Number of the Batch
	  */
	public String getBatchDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_BatchDocumentNo);
	}

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Package No.
		@param CodeNo 
		Bag No / Pallet No / Bottle
	  */
	public void setCodeNo (String CodeNo)
	{
		set_Value (COLUMNNAME_CodeNo, CodeNo);
	}

	/** Get Package No.
		@return Bag No / Pallet No / Bottle
	  */
	public String getCodeNo () 
	{
		return (String)get_Value(COLUMNNAME_CodeNo);
	}

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Confirmed = CF */
	public static final String DOCACTION_Confirmed = "CF";
	/** Finished = FN */
	public static final String DOCACTION_Finished = "FN";
	/** Cancelled = CN */
	public static final String DOCACTION_Cancelled = "CN";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Create Result Lines.
		@param GenerateTo 
		Create Result Lines
	  */
	public void setGenerateTo (String GenerateTo)
	{
		set_Value (COLUMNNAME_GenerateTo, GenerateTo);
	}

	/** Get Create Result Lines.
		@return Create Result Lines
	  */
	public String getGenerateTo () 
	{
		return (String)get_Value(COLUMNNAME_GenerateTo);
	}

	/** Set Info.
		@param Info 
		Information
	  */
	public void setInfo (boolean Info)
	{
		set_Value (COLUMNNAME_Info, Boolean.valueOf(Info));
	}

	/** Get Info.
		@return Information
	  */
	public boolean isInfo () 
	{
		Object oo = get_Value(COLUMNNAME_Info);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Inspection Date.
		@param InspectionDate Inspection Date	  */
	public void setInspectionDate (Timestamp InspectionDate)
	{
		set_Value (COLUMNNAME_InspectionDate, InspectionDate);
	}

	/** Get Inspection Date.
		@return Inspection Date	  */
	public Timestamp getInspectionDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_InspectionDate);
	}

	/** InspectionType AD_Reference_ID=1000144 */
	public static final int INSPECTIONTYPE_AD_Reference_ID=1000144;
	/** Chemical = C */
	public static final String INSPECTIONTYPE_Chemical = "C";
	/** Microbiological = M */
	public static final String INSPECTIONTYPE_Microbiological = "M";
	/** Requested Product = R */
	public static final String INSPECTIONTYPE_RequestedProduct = "R";
	/** PM/SM Product = P */
	public static final String INSPECTIONTYPE_PMSMProduct = "P";
	/** Set Inspection Type.
		@param InspectionType Inspection Type	  */
	public void setInspectionType (String InspectionType)
	{

		set_Value (COLUMNNAME_InspectionType, InspectionType);
	}

	/** Get Inspection Type.
		@return Inspection Type	  */
	public String getInspectionType () 
	{
		return (String)get_Value(COLUMNNAME_InspectionType);
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
    {
		return (org.compiere.model.I_M_AttributeSetInstance)MTable.get(getCtx(), org.compiere.model.I_M_AttributeSetInstance.Table_Name)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Locator getM_Locator() throws RuntimeException
    {
		return (I_M_Locator)MTable.get(getCtx(), I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_MovementLine getM_MovementLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_MovementLine)MTable.get(getCtx(), org.compiere.model.I_M_MovementLine.Table_Name)
			.getPO(getM_MovementLine_ID(), get_TrxName());	}

	/** Set Move Line.
		@param M_MovementLine_ID 
		Inventory Move document Line
	  */
	public void setM_MovementLine_ID (int M_MovementLine_ID)
	{
		if (M_MovementLine_ID < 1) 
			set_Value (COLUMNNAME_M_MovementLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_MovementLine_ID, Integer.valueOf(M_MovementLine_ID));
	}

	/** Get Move Line.
		@return Inventory Move document Line
	  */
	public int getM_MovementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_MovementLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Non Conformance.
		@param NC Non Conformance	  */
	public void setNC (String NC)
	{
		set_Value (COLUMNNAME_NC, NC);
	}

	/** Get Non Conformance.
		@return Non Conformance	  */
	public String getNC () 
	{
		return (String)get_Value(COLUMNNAME_NC);
	}

	/** Set Quantity NC.
		@param NCQty Quantity NC	  */
	public void setNCQty (BigDecimal NCQty)
	{
		set_Value (COLUMNNAME_NCQty, NCQty);
	}

	/** Get Quantity NC.
		@return Quantity NC	  */
	public BigDecimal getNCQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NCQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Hold.
		@param OnHold Hold	  */
	public void setOnHold (String OnHold)
	{
		set_Value (COLUMNNAME_OnHold, OnHold);
	}

	/** Get Hold.
		@return Hold	  */
	public String getOnHold () 
	{
		return (String)get_Value(COLUMNNAME_OnHold);
	}

	/** Set Quantity OnHold.
		@param OnHoldQty Quantity OnHold	  */
	public void setOnHoldQty (BigDecimal OnHoldQty)
	{
		set_Value (COLUMNNAME_OnHoldQty, OnHoldQty);
	}

	/** Get Quantity OnHold.
		@return Quantity OnHold	  */
	public BigDecimal getOnHoldQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OnHoldQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Posted.
		@param Posted 
		Posting status
	  */
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Posted.
		@return Posting status
	  */
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed On.
		@param ProcessedOn 
		The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn)
	{
		set_Value (COLUMNNAME_ProcessedOn, ProcessedOn);
	}

	/** Get Processed On.
		@return The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProcessedOn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Production Date.
		@param ProductionDate Production Date	  */
	public void setProductionDate (Timestamp ProductionDate)
	{
		throw new IllegalArgumentException ("ProductionDate is virtual column");	}

	/** Get Production Date.
		@return Production Date	  */
	public Timestamp getProductionDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ProductionDate);
	}

	/** QAStatus AD_Reference_ID=1000128 */
	public static final int QASTATUS_AD_Reference_ID=1000128;
	/** On Hold = OH */
	public static final String QASTATUS_OnHold = "OH";
	/** Release = RE */
	public static final String QASTATUS_Release = "RE";
	/** Premature Released = PR */
	public static final String QASTATUS_PrematureReleased = "PR";
	/** Reject = RJ */
	public static final String QASTATUS_Reject = "RJ";
	/** Reprocess = RP */
	public static final String QASTATUS_Reprocess = "RP";
	/** Blending = BL */
	public static final String QASTATUS_Blending = "BL";
	/** Resorting = RS */
	public static final String QASTATUS_Resorting = "RS";
	/** Regrade = RG */
	public static final String QASTATUS_Regrade = "RG";
	/** Repacking = RA */
	public static final String QASTATUS_Repacking = "RA";
	/** Non Conformance = NC */
	public static final String QASTATUS_NonConformance = "NC";
	/** Pending Inspection/Lab Test = PL */
	public static final String QASTATUS_PendingInspectionLabTest = "PL";
	/** Incubation = IC */
	public static final String QASTATUS_Incubation = "IC";
	/** Non QA = NQ */
	public static final String QASTATUS_NonQA = "NQ";
	/** QA Tested = QT */
	public static final String QASTATUS_QATested = "QT";
	/** Set QA Status.
		@param QAStatus QA Status	  */
	public void setQAStatus (String QAStatus)
	{

		set_Value (COLUMNNAME_QAStatus, QAStatus);
	}

	/** Get QA Status.
		@return QA Status	  */
	public String getQAStatus () 
	{
		return (String)get_Value(COLUMNNAME_QAStatus);
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reference No.
		@param ReferenceNo 
		Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo (String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Reference No.
		@return Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo () 
	{
		return (String)get_Value(COLUMNNAME_ReferenceNo);
	}

	/** Set Release.
		@param Release Release	  */
	public void setRelease (String Release)
	{
		set_Value (COLUMNNAME_Release, Release);
	}

	/** Get Release.
		@return Release	  */
	public String getRelease () 
	{
		return (String)get_Value(COLUMNNAME_Release);
	}

	/** Set Quantity Release.
		@param ReleaseQty Quantity Release	  */
	public void setReleaseQty (BigDecimal ReleaseQty)
	{
		set_Value (COLUMNNAME_ReleaseQty, ReleaseQty);
	}

	/** Get Quantity Release.
		@return Quantity Release	  */
	public BigDecimal getReleaseQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ReleaseQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Remark.
		@param Remark Remark	  */
	public void setRemark (String Remark)
	{
		set_Value (COLUMNNAME_Remark, Remark);
	}

	/** Get Remark.
		@return Remark	  */
	public String getRemark () 
	{
		return (String)get_Value(COLUMNNAME_Remark);
	}

	/** Set Person In Charge.
		@param UNS_Employee_ID Person In Charge	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_Value (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
	}

	/** Get Person In Charge.
		@return Person In Charge	  */
	public int getUNS_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Laboratory Result.
		@param UNS_QAMLab_Result_ID Laboratory Result	  */
	public void setUNS_QAMLab_Result_ID (int UNS_QAMLab_Result_ID)
	{
		if (UNS_QAMLab_Result_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMLab_Result_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMLab_Result_ID, Integer.valueOf(UNS_QAMLab_Result_ID));
	}

	/** Get Laboratory Result.
		@return Laboratory Result	  */
	public int getUNS_QAMLab_Result_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAMLab_Result_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAMLab_Result_UU.
		@param UNS_QAMLab_Result_UU UNS_QAMLab_Result_UU	  */
	public void setUNS_QAMLab_Result_UU (String UNS_QAMLab_Result_UU)
	{
		set_Value (COLUMNNAME_UNS_QAMLab_Result_UU, UNS_QAMLab_Result_UU);
	}

	/** Get UNS_QAMLab_Result_UU.
		@return UNS_QAMLab_Result_UU	  */
	public String getUNS_QAMLab_Result_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAMLab_Result_UU);
	}

	public com.uns.qad.model.I_UNS_QAMLab_Summary getUNS_QAMLab_Summary() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAMLab_Summary)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAMLab_Summary.Table_Name)
			.getPO(getUNS_QAMLab_Summary_ID(), get_TrxName());	}

	/** Set Monitoring Laboratory Summary.
		@param UNS_QAMLab_Summary_ID Monitoring Laboratory Summary	  */
	public void setUNS_QAMLab_Summary_ID (int UNS_QAMLab_Summary_ID)
	{
		if (UNS_QAMLab_Summary_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMLab_Summary_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMLab_Summary_ID, Integer.valueOf(UNS_QAMLab_Summary_ID));
	}

	/** Get Monitoring Laboratory Summary.
		@return Monitoring Laboratory Summary	  */
	public int getUNS_QAMLab_Summary_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAMLab_Summary_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.qad.model.I_UNS_QAMonitoringLab getUNS_QAMonitoringLab() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAMonitoringLab)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAMonitoringLab.Table_Name)
			.getPO(getUNS_QAMonitoringLab_ID(), get_TrxName());	}

	/** Set Monitoring Laboratory.
		@param UNS_QAMonitoringLab_ID Monitoring Laboratory	  */
	public void setUNS_QAMonitoringLab_ID (int UNS_QAMonitoringLab_ID)
	{
		if (UNS_QAMonitoringLab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMonitoringLab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAMonitoringLab_ID, Integer.valueOf(UNS_QAMonitoringLab_ID));
	}

	/** Get Monitoring Laboratory.
		@return Monitoring Laboratory	  */
	public int getUNS_QAMonitoringLab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAMonitoringLab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.qad.model.I_UNS_QAStatus_PRCLine getUNS_QAStatus_PRCLine() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAStatus_PRCLine)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAStatus_PRCLine.Table_Name)
			.getPO(getUNS_QAStatus_PRCLine_ID(), get_TrxName());	}

	/** Set Production Inspection Line.
		@param UNS_QAStatus_PRCLine_ID Production Inspection Line	  */
	public void setUNS_QAStatus_PRCLine_ID (int UNS_QAStatus_PRCLine_ID)
	{
		if (UNS_QAStatus_PRCLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_PRCLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_PRCLine_ID, Integer.valueOf(UNS_QAStatus_PRCLine_ID));
	}

	/** Get Production Inspection Line.
		@return Production Inspection Line	  */
	public int getUNS_QAStatus_PRCLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_PRCLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WO Request.
		@param UNS_WO_Request_ID WO Request	  */
	public void setUNS_WO_Request_ID (int UNS_WO_Request_ID)
	{
		if (UNS_WO_Request_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_WO_Request_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_WO_Request_ID, Integer.valueOf(UNS_WO_Request_ID));
	}

	/** Get WO Request.
		@return WO Request	  */
	public int getUNS_WO_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_WO_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}