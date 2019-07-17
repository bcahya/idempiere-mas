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

/** Generated Model for UNS_QAStatus_PRC
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAStatus_PRC extends PO implements I_UNS_QAStatus_PRC, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140301L;

    /** Standard Constructor */
    public X_UNS_QAStatus_PRC (Properties ctx, int UNS_QAStatus_PRC_ID, String trxName)
    {
      super (ctx, UNS_QAStatus_PRC_ID, trxName);
      /** if (UNS_QAStatus_PRC_ID == 0)
        {
			setAD_OrgTrx_ID (0);
			setAnalysisDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setC_DocType_ID (0);
// @SQL=SELECT C_DocType.C_DocType_ID FROM C_DocType WHERE C_DocType.DocBaseType = 'QAD'
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setExpectedResultDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setInspectionType (null);
			setIsApproved (false);
// N
			setProcessed (false);
// N
			setProductionDate (new Timestamp( System.currentTimeMillis() ));
			setUNS_Production_ID (0);
			setUNS_QAStatus_PRC_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAStatus_PRC (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAStatus_PRC[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

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

	/** Set Analysis Date.
		@param AnalysisDate Analysis Date	  */
	public void setAnalysisDate (Timestamp AnalysisDate)
	{
		set_Value (COLUMNNAME_AnalysisDate, AnalysisDate);
	}

	/** Get Analysis Date.
		@return Analysis Date	  */
	public Timestamp getAnalysisDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_AnalysisDate);
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

	/** Set Expected Result Date.
		@param ExpectedResultDate Expected Result Date	  */
	public void setExpectedResultDate (Timestamp ExpectedResultDate)
	{
		set_Value (COLUMNNAME_ExpectedResultDate, ExpectedResultDate);
	}

	/** Get Expected Result Date.
		@return Expected Result Date	  */
	public Timestamp getExpectedResultDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ExpectedResultDate);
	}

	/** InspectionType AD_Reference_ID=1000211 */
	public static final int INSPECTIONTYPE_AD_Reference_ID=1000211;
	/** Locator Inspection = LI */
	public static final String INSPECTIONTYPE_LocatorInspection = "LI";
	/** Production Inspection = PI */
	public static final String INSPECTIONTYPE_ProductionInspection = "PI";
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

	/** Set Production Date.
		@param ProductionDate Production Date	  */
	public void setProductionDate (Timestamp ProductionDate)
	{
		set_Value (COLUMNNAME_ProductionDate, ProductionDate);
	}

	/** Get Production Date.
		@return Production Date	  */
	public Timestamp getProductionDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ProductionDate);
	}

	/** Set Production.
		@param UNS_Production_ID Production	  */
	public void setUNS_Production_ID (int UNS_Production_ID)
	{
		if (UNS_Production_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_ID, Integer.valueOf(UNS_Production_ID));
	}

	/** Get Production.
		@return Production	  */
	public int getUNS_Production_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production Inspection.
		@param UNS_QAStatus_PRC_ID Production Inspection	  */
	public void setUNS_QAStatus_PRC_ID (int UNS_QAStatus_PRC_ID)
	{
		if (UNS_QAStatus_PRC_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_PRC_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_PRC_ID, Integer.valueOf(UNS_QAStatus_PRC_ID));
	}

	/** Get Production Inspection.
		@return Production Inspection	  */
	public int getUNS_QAStatus_PRC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_PRC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAStatus_PRC_UU.
		@param UNS_QAStatus_PRC_UU UNS_QAStatus_PRC_UU	  */
	public void setUNS_QAStatus_PRC_UU (String UNS_QAStatus_PRC_UU)
	{
		set_Value (COLUMNNAME_UNS_QAStatus_PRC_UU, UNS_QAStatus_PRC_UU);
	}

	/** Get UNS_QAStatus_PRC_UU.
		@return UNS_QAStatus_PRC_UU	  */
	public String getUNS_QAStatus_PRC_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAStatus_PRC_UU);
	}
}