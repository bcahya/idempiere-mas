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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_CheckerConfirmation
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_CheckerConfirmation extends PO implements I_UNS_CheckerConfirmation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160313L;

    /** Standard Constructor */
    public X_UNS_CheckerConfirmation (Properties ctx, int UNS_CheckerConfirmation_ID, String trxName)
    {
      super (ctx, UNS_CheckerConfirmation_ID, trxName);
      /** if (UNS_CheckerConfirmation_ID == 0)
        {
			setC_BPartner_ID (0);
			setCheckerType (null);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setProcessed (false);
			setSalesRep_ID (0);
			setUNS_CheckerConfirmation_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_CheckerConfirmation (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_UNS_CheckerConfirmation[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Requested Organization.
		@param AD_OrgFrom_ID 
		The organization/branch/sub-ordinate to be requested
	  */
	public void setAD_OrgFrom_ID (int AD_OrgFrom_ID)
	{
		if (AD_OrgFrom_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgFrom_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgFrom_ID, Integer.valueOf(AD_OrgFrom_ID));
	}

	/** Get Requested Organization.
		@return The organization/branch/sub-ordinate to be requested
	  */
	public int getAD_OrgFrom_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgFrom_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Branch Checker = BC */
	public static final String CHECKERTYPE_BranchChecker = "BC";
	/** Market Checker = MC */
	public static final String CHECKERTYPE_MarketChecker = "MC";
	/** Set Checker Type.
		@param CheckerType Checker Type	  */
	public void setCheckerType (String CheckerType)
	{

		set_ValueNoCheck (COLUMNNAME_CheckerType, CheckerType);
	}

	/** Get Checker Type.
		@return Checker Type	  */
	public String getCheckerType () 
	{
		return (String)get_Value(COLUMNNAME_CheckerType);
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

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_ValueNoCheck (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
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

	/** Set Notes.
		@param Notes Notes	  */
	public void setNotes (String Notes)
	{
		set_Value (COLUMNNAME_Notes, Notes);
	}

	/** Get Notes.
		@return Notes	  */
	public String getNotes () 
	{
		return (String)get_Value(COLUMNNAME_Notes);
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

	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.unicore.model.I_UNS_Checker getUNS_Checker() throws RuntimeException
    {
		return (com.unicore.model.I_UNS_Checker)MTable.get(getCtx(), com.unicore.model.I_UNS_Checker.Table_Name)
			.getPO(getUNS_Checker_ID(), get_TrxName());	}

	/** Set Checker.
		@param UNS_Checker_ID Checker	  */
	public void setUNS_Checker_ID (int UNS_Checker_ID)
	{
		if (UNS_Checker_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Checker_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Checker_ID, Integer.valueOf(UNS_Checker_ID));
	}

	/** Get Checker.
		@return Checker	  */
	public int getUNS_Checker_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Checker_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Checker Confirmation.
		@param UNS_CheckerConfirmation_ID Checker Confirmation	  */
	public void setUNS_CheckerConfirmation_ID (int UNS_CheckerConfirmation_ID)
	{
		if (UNS_CheckerConfirmation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_CheckerConfirmation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_CheckerConfirmation_ID, Integer.valueOf(UNS_CheckerConfirmation_ID));
	}

	/** Get Checker Confirmation.
		@return Checker Confirmation	  */
	public int getUNS_CheckerConfirmation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_CheckerConfirmation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_CheckerConfirmation_UU.
		@param UNS_CheckerConfirmation_UU UNS_CheckerConfirmation_UU	  */
	public void setUNS_CheckerConfirmation_UU (String UNS_CheckerConfirmation_UU)
	{
		set_ValueNoCheck (COLUMNNAME_UNS_CheckerConfirmation_UU, UNS_CheckerConfirmation_UU);
	}

	/** Get UNS_CheckerConfirmation_UU.
		@return UNS_CheckerConfirmation_UU	  */
	public String getUNS_CheckerConfirmation_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_CheckerConfirmation_UU);
	}
}