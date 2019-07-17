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

/** Generated Model for UNS_QAStatus_NCRecord
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAStatus_NCRecord extends PO implements I_UNS_QAStatus_NCRecord, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140219L;

    /** Standard Constructor */
    public X_UNS_QAStatus_NCRecord (Properties ctx, int UNS_QAStatus_NCRecord_ID, String trxName)
    {
      super (ctx, UNS_QAStatus_NCRecord_ID, trxName);
      /** if (UNS_QAStatus_NCRecord_ID == 0)
        {
			setC_DocType_ID (0);
			setCreateMovement (false);
// N
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDescription (null);
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setFromPRC (false);
			setIsApproved (false);
// N
			setPDAStatus (null);
// NC
			setProcessed (false);
// N
			setQty (Env.ZERO);
			setUNS_QAStatus_NCRecord_ID (0);
			setUseMaterials (false);
// N
        } */
    }

    /** Load Constructor */
    public X_UNS_QAStatus_NCRecord (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAStatus_NCRecord[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Time.
		@param ActByDT Time	  */
	public void setActByDT (Timestamp ActByDT)
	{
		set_Value (COLUMNNAME_ActByDT, ActByDT);
	}

	/** Get Time.
		@return Time	  */
	public Timestamp getActByDT () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ActByDT);
	}

	/** Set Name.
		@param ActByName Name	  */
	public void setActByName (String ActByName)
	{
		set_Value (COLUMNNAME_ActByName, ActByName);
	}

	/** Get Name.
		@return Name	  */
	public String getActByName () 
	{
		return (String)get_Value(COLUMNNAME_ActByName);
	}

	public org.compiere.model.I_AD_Org getAD_OrgTrx() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Org)MTable.get(getCtx(), org.compiere.model.I_AD_Org.Table_Name)
			.getPO(getAD_OrgTrx_ID(), get_TrxName());	}

	/** Set Trx Department.
		@param AD_OrgTrx_ID 
		Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Department.
		@return Performing or initiating Department
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set Notification to.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Notification to.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
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

	/** Set Package Sequence.
		@param CartonSequence 
		Carton or Package Number Sequence
	  */
	public void setCartonSequence (String CartonSequence)
	{
		set_Value (COLUMNNAME_CartonSequence, CartonSequence);
	}

	/** Get Package Sequence.
		@return Carton or Package Number Sequence
	  */
	public String getCartonSequence () 
	{
		return (String)get_Value(COLUMNNAME_CartonSequence);
	}

	/** Set Create Movement.
		@param CreateMovement 
		Automatic created movemet to Trx Department.
	  */
	public void setCreateMovement (boolean CreateMovement)
	{
		set_Value (COLUMNNAME_CreateMovement, Boolean.valueOf(CreateMovement));
	}

	/** Get Create Movement.
		@return Automatic created movemet to Trx Department.
	  */
	public boolean isCreateMovement () 
	{
		Object oo = get_Value(COLUMNNAME_CreateMovement);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
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

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set End Time.
		@param EndTime 
		End of the time span
	  */
	public void setEndTime (Timestamp EndTime)
	{
		set_Value (COLUMNNAME_EndTime, EndTime);
	}

	/** Get End Time.
		@return End of the time span
	  */
	public Timestamp getEndTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndTime);
	}

	/** Set PRC.
		@param FromPRC 
		This identification from PRC or not
	  */
	public void setFromPRC (boolean FromPRC)
	{
		set_ValueNoCheck (COLUMNNAME_FromPRC, Boolean.valueOf(FromPRC));
	}

	/** Get PRC.
		@return This identification from PRC or not
	  */
	public boolean isFromPRC () 
	{
		Object oo = get_Value(COLUMNNAME_FromPRC);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Informed By.
		@param InformedBy Informed By	  */
	public void setInformedBy (String InformedBy)
	{
		set_Value (COLUMNNAME_InformedBy, InformedBy);
	}

	/** Get Informed By.
		@return Informed By	  */
	public String getInformedBy () 
	{
		return (String)get_Value(COLUMNNAME_InformedBy);
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

	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
    {
		return (org.compiere.model.I_M_Locator)MTable.get(getCtx(), org.compiere.model.I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
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

	/** PDAStatus AD_Reference_ID=1000128 */
	public static final int PDASTATUS_AD_Reference_ID=1000128;
	/** On Hold = OH */
	public static final String PDASTATUS_OnHold = "OH";
	/** Release = RE */
	public static final String PDASTATUS_Release = "RE";
	/** Premature Released = PR */
	public static final String PDASTATUS_PrematureReleased = "PR";
	/** Pending Inspection/Lab Test = PL */
	public static final String PDASTATUS_PendingInspectionLabTest = "PL";
	/** Reject = RJ */
	public static final String PDASTATUS_Reject = "RJ";
	/** Reprocess = RP */
	public static final String PDASTATUS_Reprocess = "RP";
	/** Blending = BL */
	public static final String PDASTATUS_Blending = "BL";
	/** Resorting = RS */
	public static final String PDASTATUS_Resorting = "RS";
	/** Regrade = RG */
	public static final String PDASTATUS_Regrade = "RG";
	/** Repacking = RA */
	public static final String PDASTATUS_Repacking = "RA";
	/** Non Conformance = NC */
	public static final String PDASTATUS_NonConformance = "NC";
	/** Incubation = IC */
	public static final String PDASTATUS_Incubation = "IC";
	/** Set PDA Status.
		@param PDAStatus PDA Status	  */
	public void setPDAStatus (String PDAStatus)
	{

		set_Value (COLUMNNAME_PDAStatus, PDAStatus);
	}

	/** Get PDA Status.
		@return PDA Status	  */
	public String getPDAStatus () 
	{
		return (String)get_Value(COLUMNNAME_PDAStatus);
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

	/** Set PDA Description.
		@param ProposedDispositionAction PDA Description	  */
	public void setProposedDispositionAction (String ProposedDispositionAction)
	{
		set_Value (COLUMNNAME_ProposedDispositionAction, ProposedDispositionAction);
	}

	/** Get PDA Description.
		@return PDA Description	  */
	public String getProposedDispositionAction () 
	{
		return (String)get_Value(COLUMNNAME_ProposedDispositionAction);
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

	/** Set Received By.
		@param ReceivedBy Received By	  */
	public void setReceivedBy (String ReceivedBy)
	{
		set_Value (COLUMNNAME_ReceivedBy, ReceivedBy);
	}

	/** Get Received By.
		@return Received By	  */
	public String getReceivedBy () 
	{
		return (String)get_Value(COLUMNNAME_ReceivedBy);
	}

	/** Set Result.
		@param Result 
		Result of the action taken
	  */
	public void setResult (String Result)
	{
		set_Value (COLUMNNAME_Result, Result);
	}

	/** Get Result.
		@return Result of the action taken
	  */
	public String getResult () 
	{
		return (String)get_Value(COLUMNNAME_Result);
	}

	/** Set Start Time.
		@param StartTime 
		Time started
	  */
	public void setStartTime (Timestamp StartTime)
	{
		set_Value (COLUMNNAME_StartTime, StartTime);
	}

	/** Get Start Time.
		@return Time started
	  */
	public Timestamp getStartTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartTime);
	}

	public com.uns.qad.model.I_UNS_QAStatus_InOutLine getUNS_QAStatus_InOutLine() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAStatus_InOutLine)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAStatus_InOutLine.Table_Name)
			.getPO(getUNS_QAStatus_InOutLine_ID(), get_TrxName());	}

	/** Set Ship/Receipt Inspection Line.
		@param UNS_QAStatus_InOutLine_ID Ship/Receipt Inspection Line	  */
	public void setUNS_QAStatus_InOutLine_ID (int UNS_QAStatus_InOutLine_ID)
	{
		if (UNS_QAStatus_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_InOutLine_ID, Integer.valueOf(UNS_QAStatus_InOutLine_ID));
	}

	/** Get Ship/Receipt Inspection Line.
		@return Ship/Receipt Inspection Line	  */
	public int getUNS_QAStatus_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Non-Conformance Record.
		@param UNS_QAStatus_NCRecord_ID Non-Conformance Record	  */
	public void setUNS_QAStatus_NCRecord_ID (int UNS_QAStatus_NCRecord_ID)
	{
		if (UNS_QAStatus_NCRecord_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_NCRecord_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_NCRecord_ID, Integer.valueOf(UNS_QAStatus_NCRecord_ID));
	}

	/** Get Non-Conformance Record.
		@return Non-Conformance Record	  */
	public int getUNS_QAStatus_NCRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_NCRecord_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAStatus_NCRecord_UU.
		@param UNS_QAStatus_NCRecord_UU UNS_QAStatus_NCRecord_UU	  */
	public void setUNS_QAStatus_NCRecord_UU (String UNS_QAStatus_NCRecord_UU)
	{
		set_Value (COLUMNNAME_UNS_QAStatus_NCRecord_UU, UNS_QAStatus_NCRecord_UU);
	}

	/** Get UNS_QAStatus_NCRecord_UU.
		@return UNS_QAStatus_NCRecord_UU	  */
	public String getUNS_QAStatus_NCRecord_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAStatus_NCRecord_UU);
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

	/** Set Use Materials.
		@param UseMaterials 
		Currently used material with created internal material use.
	  */
	public void setUseMaterials (boolean UseMaterials)
	{
		set_Value (COLUMNNAME_UseMaterials, Boolean.valueOf(UseMaterials));
	}

	/** Get Use Materials.
		@return Currently used material with created internal material use.
	  */
	public boolean isUseMaterials () 
	{
		Object oo = get_Value(COLUMNNAME_UseMaterials);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Time.
		@param VrfByDT Time	  */
	public void setVrfByDT (Timestamp VrfByDT)
	{
		set_Value (COLUMNNAME_VrfByDT, VrfByDT);
	}

	/** Get Time.
		@return Time	  */
	public Timestamp getVrfByDT () 
	{
		return (Timestamp)get_Value(COLUMNNAME_VrfByDT);
	}

	/** Set Name.
		@param VrfByName Name	  */
	public void setVrfByName (String VrfByName)
	{
		set_Value (COLUMNNAME_VrfByName, VrfByName);
	}

	/** Get Name.
		@return Name	  */
	public String getVrfByName () 
	{
		return (String)get_Value(COLUMNNAME_VrfByName);
	}
}