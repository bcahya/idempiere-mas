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

/** Generated Model for UNS_QAStatus_NutLabTest
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAStatus_NutLabTest extends PO implements I_UNS_QAStatus_NutLabTest, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130520L;

    /** Standard Constructor */
    public X_UNS_QAStatus_NutLabTest (Properties ctx, int UNS_QAStatus_NutLabTest_ID, String trxName)
    {
      super (ctx, UNS_QAStatus_NutLabTest_ID, trxName);
      /** if (UNS_QAStatus_NutLabTest_ID == 0)
        {
			setAnalysisDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setC_UOM_ID (0);
			setCalculationType (null);
// N
			setCoconutType (null);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setPickedQty (Env.ZERO);
			setProcessed (false);
// N
			setUNS_QAStatus_NutLabTest_ID (0);
			setUNS_QAStatus_Type_ID (0);
			setWeight (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAStatus_NutLabTest (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAStatus_NutLabTest[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Calculation.
		@param CalculationType Calculation	  */
	public void setCalculationType (String CalculationType)
	{
		set_Value (COLUMNNAME_CalculationType, CalculationType);
	}

	/** Get Calculation.
		@return Calculation	  */
	public String getCalculationType () 
	{
		return (String)get_Value(COLUMNNAME_CalculationType);
	}

	/** CoconutType AD_Reference_ID=1000132 */
	public static final int COCONUTTYPE_AD_Reference_ID=1000132;
	/** KBA1 = A1 */
	public static final String COCONUTTYPE_KBA1 = "A1";
	/** KBA2 = A2 */
	public static final String COCONUTTYPE_KBA2 = "A2";
	/** KBA3 = A3 */
	public static final String COCONUTTYPE_KBA3 = "A3";
	/** KBA4 = A4 */
	public static final String COCONUTTYPE_KBA4 = "A4";
	/** KBB1 = B1 */
	public static final String COCONUTTYPE_KBB1 = "B1";
	/** KBB2 = B2 */
	public static final String COCONUTTYPE_KBB2 = "B2";
	/** KBB3 = B3 */
	public static final String COCONUTTYPE_KBB3 = "B3";
	/** KBB4 = B4 */
	public static final String COCONUTTYPE_KBB4 = "B4";
	/** Set Coconut Type.
		@param CoconutType Coconut Type	  */
	public void setCoconutType (String CoconutType)
	{

		set_ValueNoCheck (COLUMNNAME_CoconutType, CoconutType);
	}

	/** Get Coconut Type.
		@return Coconut Type	  */
	public String getCoconutType () 
	{
		return (String)get_Value(COLUMNNAME_CoconutType);
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

	/** Set DM (%).
		@param DM 
		Dry Matter in percent.
	  */
	public void setDM (BigDecimal DM)
	{
		set_Value (COLUMNNAME_DM, DM);
	}

	/** Get DM (%).
		@return Dry Matter in percent.
	  */
	public BigDecimal getDM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Fat Weight (g).
		@param FatWeight 
		Fat Weight in gram.
	  */
	public void setFatWeight (BigDecimal FatWeight)
	{
		set_Value (COLUMNNAME_FatWeight, FatWeight);
	}

	/** Get Fat Weight (g).
		@return Fat Weight in gram.
	  */
	public BigDecimal getFatWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FatWeight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FC DB (%).
		@param FCDB 
		Fat Content Dry Basic in percent.
	  */
	public void setFCDB (BigDecimal FCDB)
	{
		set_Value (COLUMNNAME_FCDB, FCDB);
	}

	/** Get FC DB (%).
		@return Fat Content Dry Basic in percent.
	  */
	public BigDecimal getFCDB () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FCDB);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FC WB (%).
		@param FCWB 
		Fat Content Wet Basic in percent.
	  */
	public void setFCWB (BigDecimal FCWB)
	{
		set_Value (COLUMNNAME_FCWB, FCWB);
	}

	/** Get FC WB (%).
		@return Fat Content Wet Basic in percent.
	  */
	public BigDecimal getFCWB () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FCWB);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set MC (%).
		@param MC 
		Moisture Content in percent.
	  */
	public void setMC (BigDecimal MC)
	{
		set_Value (COLUMNNAME_MC, MC);
	}

	/** Get MC (%).
		@return Moisture Content in percent.
	  */
	public BigDecimal getMC () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MC);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Picked Quantity.
		@param PickedQty Picked Quantity	  */
	public void setPickedQty (BigDecimal PickedQty)
	{
		set_Value (COLUMNNAME_PickedQty, PickedQty);
	}

	/** Get Picked Quantity.
		@return Picked Quantity	  */
	public BigDecimal getPickedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PickedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Sample DB (g).
		@param SampleDB 
		Sample Dry Basic in gram.
	  */
	public void setSampleDB (BigDecimal SampleDB)
	{
		set_Value (COLUMNNAME_SampleDB, SampleDB);
	}

	/** Get Sample DB (g).
		@return Sample Dry Basic in gram.
	  */
	public BigDecimal getSampleDB () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SampleDB);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sample Dry Nut (g).
		@param SampleDC 
		Sample Dry Coconut in gram.
	  */
	public void setSampleDC (BigDecimal SampleDC)
	{
		set_Value (COLUMNNAME_SampleDC, SampleDC);
	}

	/** Get Sample Dry Nut (g).
		@return Sample Dry Coconut in gram.
	  */
	public BigDecimal getSampleDC () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SampleDC);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sample WB (g).
		@param SampleWB 
		Sample Wet Basic in gram.
	  */
	public void setSampleWB (BigDecimal SampleWB)
	{
		set_Value (COLUMNNAME_SampleWB, SampleWB);
	}

	/** Get Sample WB (g).
		@return Sample Wet Basic in gram.
	  */
	public BigDecimal getSampleWB () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SampleWB);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Coconut Labolatory Test.
		@param UNS_QAStatus_NutLabTest_ID Coconut Labolatory Test	  */
	public void setUNS_QAStatus_NutLabTest_ID (int UNS_QAStatus_NutLabTest_ID)
	{
		if (UNS_QAStatus_NutLabTest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_NutLabTest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_NutLabTest_ID, Integer.valueOf(UNS_QAStatus_NutLabTest_ID));
	}

	/** Get Coconut Labolatory Test.
		@return Coconut Labolatory Test	  */
	public int getUNS_QAStatus_NutLabTest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_NutLabTest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_QAStatus_NutLabTest_UU.
		@param UNS_QAStatus_NutLabTest_UU UNS_QAStatus_NutLabTest_UU	  */
	public void setUNS_QAStatus_NutLabTest_UU (String UNS_QAStatus_NutLabTest_UU)
	{
		set_Value (COLUMNNAME_UNS_QAStatus_NutLabTest_UU, UNS_QAStatus_NutLabTest_UU);
	}

	/** Get UNS_QAStatus_NutLabTest_UU.
		@return UNS_QAStatus_NutLabTest_UU	  */
	public String getUNS_QAStatus_NutLabTest_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAStatus_NutLabTest_UU);
	}

	public com.uns.qad.model.I_UNS_QAStatus_Type getUNS_QAStatus_Type() throws RuntimeException
    {
		return (com.uns.qad.model.I_UNS_QAStatus_Type)MTable.get(getCtx(), com.uns.qad.model.I_UNS_QAStatus_Type.Table_Name)
			.getPO(getUNS_QAStatus_Type_ID(), get_TrxName());	}

	/** Set Attribute Type.
		@param UNS_QAStatus_Type_ID Attribute Type	  */
	public void setUNS_QAStatus_Type_ID (int UNS_QAStatus_Type_ID)
	{
		if (UNS_QAStatus_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_QAStatus_Type_ID, Integer.valueOf(UNS_QAStatus_Type_ID));
	}

	/** Get Attribute Type.
		@return Attribute Type	  */
	public int getUNS_QAStatus_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_QAStatus_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Weight.
		@param Weight 
		Weight of a product
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Weight.
		@return Weight of a product
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}