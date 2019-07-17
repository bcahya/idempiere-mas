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
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_Production
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Production extends PO implements I_UNS_Production, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150311L;

    /** Standard Constructor */
    public X_UNS_Production (Properties ctx, int UNS_Production_ID, String trxName)
    {
      super (ctx, UNS_Production_ID, trxName);
      /** if (UNS_Production_ID == 0)
        {
			setBOMType (null);
// 0.P
			setC_DocType_ID (0);
// @SQL=SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='PCO'
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setHasStickerInfo (false);
// N
			setIsApproved (false);
// N
			setIsCreated (null);
// N
			setIsOverTime (false);
// N
			setMovementDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setPSType (null);
// MPS
			setPosted (false);
			setProcessed (false);
// N
			setProductionDate (new Timestamp( System.currentTimeMillis() ));
			setUNS_Production_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_Production (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Production[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** BOMType AD_Reference_ID=279 */
	public static final int BOMTYPE_AD_Reference_ID=279;
	/** Standard Part = 0.P */
	public static final String BOMTYPE_StandardPart = "0.P";
	/** Optional Part = O */
	public static final String BOMTYPE_OptionalPart = "O";
	/** In alternative Group 1 = 1 */
	public static final String BOMTYPE_InAlternativeGroup1 = "1";
	/** In alternative Group 2 = 2 */
	public static final String BOMTYPE_InAlternativeGroup2 = "2";
	/** In alternaltve Group 3 = 3 */
	public static final String BOMTYPE_InAlternaltveGroup3 = "3";
	/** In alternative Group 4 = 4 */
	public static final String BOMTYPE_InAlternativeGroup4 = "4";
	/** In alternative Group 5 = 5 */
	public static final String BOMTYPE_InAlternativeGroup5 = "5";
	/** In alternative Group 6 = 6 */
	public static final String BOMTYPE_InAlternativeGroup6 = "6";
	/** In alternative Group 7 = 7 */
	public static final String BOMTYPE_InAlternativeGroup7 = "7";
	/** In alternative Group 8 = 8 */
	public static final String BOMTYPE_InAlternativeGroup8 = "8";
	/** In alternative Group 9 = 9 */
	public static final String BOMTYPE_InAlternativeGroup9 = "9";
	/** Predicted Reject Material = R */
	public static final String BOMTYPE_PredictedRejectMaterial = "R";
	/** WIP Bihun - F1 = WPBHN1 */
	public static final String BOMTYPE_WIPBihun_F1 = "WPBHN1";
	/** WIP Bihun - F2 = WPBHN2 */
	public static final String BOMTYPE_WIPBihun_F2 = "WPBHN2";
	/** WIP Bihun - F3 = WPBHN3 */
	public static final String BOMTYPE_WIPBihun_F3 = "WPBHN3";
	/** Set BOM Type.
		@param BOMType 
		Type of BOM
	  */
	public void setBOMType (String BOMType)
	{

		set_Value (COLUMNNAME_BOMType, BOMType);
	}

	/** Get BOM Type.
		@return Type of BOM
	  */
	public String getBOMType () 
	{
		return (String)get_Value(COLUMNNAME_BOMType);
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

	/** Set Create lines from.
		@param CreateFrom 
		Process which will generate a new document lines based on an existing document
	  */
	public void setCreateFrom (String CreateFrom)
	{
		set_Value (COLUMNNAME_CreateFrom, CreateFrom);
	}

	/** Get Create lines from.
		@return Process which will generate a new document lines based on an existing document
	  */
	public String getCreateFrom () 
	{
		return (String)get_Value(COLUMNNAME_CreateFrom);
	}

	/** Set Create Realization.
		@param CreatePSRealization Create Realization	  */
	public void setCreatePSRealization (String CreatePSRealization)
	{
		set_Value (COLUMNNAME_CreatePSRealization, CreatePSRealization);
	}

	/** Get Create Realization.
		@return Create Realization	  */
	public String getCreatePSRealization () 
	{
		return (String)get_Value(COLUMNNAME_CreatePSRealization);
	}

	/** Set Date Promised.
		@param DatePromised 
		Date Order was promised
	  */
	public void setDatePromised (Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Date Promised.
		@return Date Order was promised
	  */
	public Timestamp getDatePromised () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised);
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

		set_ValueNoCheck (COLUMNNAME_DocStatus, DocStatus);
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

	/** Set Generate Output Plan.
		@param GenerateOutPlan Generate Output Plan	  */
	public void setGenerateOutPlan (String GenerateOutPlan)
	{
		set_Value (COLUMNNAME_GenerateOutPlan, GenerateOutPlan);
	}

	/** Get Generate Output Plan.
		@return Generate Output Plan	  */
	public String getGenerateOutPlan () 
	{
		return (String)get_Value(COLUMNNAME_GenerateOutPlan);
	}

	/** Set Has Sticker Info.
		@param HasStickerInfo Has Sticker Info	  */
	public void setHasStickerInfo (boolean HasStickerInfo)
	{
		set_Value (COLUMNNAME_HasStickerInfo, Boolean.valueOf(HasStickerInfo));
	}

	/** Get Has Sticker Info.
		@return Has Sticker Info	  */
	public boolean isHasStickerInfo () 
	{
		Object oo = get_Value(COLUMNNAME_HasStickerInfo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overtime (Hours).
		@param HoursOverTime 
		The over time hours is pointing to the hours outside of (after) normal work hours
	  */
	public void setHoursOverTime (BigDecimal HoursOverTime)
	{
		set_Value (COLUMNNAME_HoursOverTime, HoursOverTime);
	}

	/** Get Overtime (Hours).
		@return The over time hours is pointing to the hours outside of (after) normal work hours
	  */
	public BigDecimal getHoursOverTime () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HoursOverTime);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Complete.
		@param IsComplete 
		It is complete
	  */
	public void setIsComplete (String IsComplete)
	{
		set_Value (COLUMNNAME_IsComplete, IsComplete);
	}

	/** Get Complete.
		@return It is complete
	  */
	public String getIsComplete () 
	{
		return (String)get_Value(COLUMNNAME_IsComplete);
	}

	/** IsCreated AD_Reference_ID=319 */
	public static final int ISCREATED_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISCREATED_Yes = "Y";
	/** No = N */
	public static final String ISCREATED_No = "N";
	/** Set Records created.
		@param IsCreated Records created	  */
	public void setIsCreated (String IsCreated)
	{

		set_Value (COLUMNNAME_IsCreated, IsCreated);
	}

	/** Get Records created.
		@return Records created	  */
	public String getIsCreated () 
	{
		return (String)get_Value(COLUMNNAME_IsCreated);
	}

	/** Set Ending Stock Base.
		@param IsEndingStockBase Ending Stock Base	  */
	public void setIsEndingStockBase (boolean IsEndingStockBase)
	{
		set_Value (COLUMNNAME_IsEndingStockBase, Boolean.valueOf(IsEndingStockBase));
	}

	/** Get Ending Stock Base.
		@return Ending Stock Base	  */
	public boolean isEndingStockBase () 
	{
		Object oo = get_Value(COLUMNNAME_IsEndingStockBase);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Has Overtime Hours.
		@param IsOverTime 
		If the production has overtime hours, then set it to true (checked)
	  */
	public void setIsOverTime (boolean IsOverTime)
	{
		set_Value (COLUMNNAME_IsOverTime, Boolean.valueOf(IsOverTime));
	}

	/** Get Has Overtime Hours.
		@return If the production has overtime hours, then set it to true (checked)
	  */
	public boolean isOverTime () 
	{
		Object oo = get_Value(COLUMNNAME_IsOverTime);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Personal Result.
		@param IsPersonalResult Personal Result	  */
	public void setIsPersonalResult (boolean IsPersonalResult)
	{
		set_Value (COLUMNNAME_IsPersonalResult, Boolean.valueOf(IsPersonalResult));
	}

	/** Get Personal Result.
		@return Personal Result	  */
	public boolean isPersonalResult () 
	{
		Object oo = get_Value(COLUMNNAME_IsPersonalResult);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Worker Base.
		@param IsWorkerBase Worker Base	  */
	public void setIsWorkerBase (boolean IsWorkerBase)
	{
		set_Value (COLUMNNAME_IsWorkerBase, Boolean.valueOf(IsWorkerBase));
	}

	/** Get Worker Base.
		@return Worker Base	  */
	public boolean isWorkerBase () 
	{
		Object oo = get_Value(COLUMNNAME_IsWorkerBase);
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

	/** Set Output Locator.
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

	/** Get Output Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product (Output).
		@param M_Product_ID 
		The output of product to be produced
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product (Output).
		@return The output of product to be produced
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Movement Date.
		@param MovementDate 
		Date a product was moved in or out of inventory
	  */
	public void setMovementDate (Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	/** Get Movement Date.
		@return Date a product was moved in or out of inventory
	  */
	public Timestamp getMovementDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MovementDate);
	}

	/** Set Must Synchronize With MPS.
		@param MustSyncWithMPS Must Synchronize With MPS	  */
	public void setMustSyncWithMPS (boolean MustSyncWithMPS)
	{
		set_Value (COLUMNNAME_MustSyncWithMPS, Boolean.valueOf(MustSyncWithMPS));
	}

	/** Get Must Synchronize With MPS.
		@return Must Synchronize With MPS	  */
	public boolean isMustSyncWithMPS () 
	{
		Object oo = get_Value(COLUMNNAME_MustSyncWithMPS);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Multi = MLT */
	public static final String OUTPUTTYPE_Multi = "MLT";
	/** Multi Optional = MOP */
	public static final String OUTPUTTYPE_MultiOptional = "MOP";
	/** Optional = OPT */
	public static final String OUTPUTTYPE_Optional = "OPT";
	/** Single = SGL */
	public static final String OUTPUTTYPE_Single = "SGL";
	/** Set Output Type.
		@param OutputType Output Type	  */
	public void setOutputType (String OutputType)
	{

		set_Value (COLUMNNAME_OutputType, OutputType);
	}

	/** Get Output Type.
		@return Output Type	  */
	public String getOutputType () 
	{
		return (String)get_Value(COLUMNNAME_OutputType);
	}

	/** Master Production Schedule = MPS */
	public static final String PSTYPE_MasterProductionSchedule = "MPS";
	/** Rebundle = RBD */
	public static final String PSTYPE_Rebundle = "RBD";
	/** Reprocess = RPR */
	public static final String PSTYPE_Reprocess = "RPR";
	/** Restickering = RSI */
	public static final String PSTYPE_Restickering = "RSI";
	/** Rework = RWK */
	public static final String PSTYPE_Rework = "RWK";
	/** Set Production Type.
		@param PSType 
		The type of the production (i.e.: Reprocess, Rebundle, Rework, Re-stickering, MPS)
	  */
	public void setPSType (String PSType)
	{

		set_Value (COLUMNNAME_PSType, PSType);
	}

	/** Get Production Type.
		@return The type of the production (i.e.: Reprocess, Rebundle, Rework, Re-stickering, MPS)
	  */
	public String getPSType () 
	{
		return (String)get_Value(COLUMNNAME_PSType);
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

	public org.compiere.model.I_M_Product getProductSticker() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getProductSticker_ID(), get_TrxName());	}

	/** Set Sticker.
		@param ProductSticker_ID Sticker	  */
	public void setProductSticker_ID (int ProductSticker_ID)
	{
		if (ProductSticker_ID < 1) 
			set_Value (COLUMNNAME_ProductSticker_ID, null);
		else 
			set_Value (COLUMNNAME_ProductSticker_ID, Integer.valueOf(ProductSticker_ID));
	}

	/** Get Sticker.
		@return Sticker	  */
	public int getProductSticker_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ProductSticker_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Production Quantity.
		@param ProductionQty 
		Quantity of products to produce
	  */
	public void setProductionQty (BigDecimal ProductionQty)
	{
		set_Value (COLUMNNAME_ProductionQty, ProductionQty);
	}

	/** Get Production Quantity.
		@return Quantity of products to produce
	  */
	public BigDecimal getProductionQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProductionQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Production getReversal() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Production)MTable.get(getCtx(), com.uns.model.I_UNS_Production.Table_Name)
			.getPO(getReversal_ID(), get_TrxName());	}

	/** Set Reversal ID.
		@param Reversal_ID 
		ID of document reversal
	  */
	public void setReversal_ID (int Reversal_ID)
	{
		if (Reversal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Reversal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
	}

	/** Get Reversal ID.
		@return ID of document reversal
	  */
	public int getReversal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Reversal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sticker Info.
		@param StickerInfo Sticker Info	  */
	public void setStickerInfo (String StickerInfo)
	{
		set_Value (COLUMNNAME_StickerInfo, StickerInfo);
	}

	/** Get Sticker Info.
		@return Sticker Info	  */
	public String getStickerInfo () 
	{
		return (String)get_Value(COLUMNNAME_StickerInfo);
	}

	/** Set Supervisor.
		@param Supervisor_ID 
		Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor_ID (int Supervisor_ID)
	{
		if (Supervisor_ID < 1) 
			set_Value (COLUMNNAME_Supervisor_ID, null);
		else 
			set_Value (COLUMNNAME_Supervisor_ID, Integer.valueOf(Supervisor_ID));
	}

	/** Get Supervisor.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production Pay Config.
		@param UNS_ProductionPayConfig_ID Production Pay Config	  */
	public void setUNS_ProductionPayConfig_ID (int UNS_ProductionPayConfig_ID)
	{
		if (UNS_ProductionPayConfig_ID < 1) 
			set_Value (COLUMNNAME_UNS_ProductionPayConfig_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_ProductionPayConfig_ID, Integer.valueOf(UNS_ProductionPayConfig_ID));
	}

	/** Get Production Pay Config.
		@return Production Pay Config	  */
	public int getUNS_ProductionPayConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ProductionPayConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production Schedule.
		@param UNS_ProductionSchedule_ID Production Schedule	  */
	public void setUNS_ProductionSchedule_ID (int UNS_ProductionSchedule_ID)
	{
		if (UNS_ProductionSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductionSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_ProductionSchedule_ID, Integer.valueOf(UNS_ProductionSchedule_ID));
	}

	/** Get Production Schedule.
		@return Production Schedule	  */
	public int getUNS_ProductionSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_ProductionSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set UNS_Production_UU.
		@param UNS_Production_UU UNS_Production_UU	  */
	public void setUNS_Production_UU (String UNS_Production_UU)
	{
		set_Value (COLUMNNAME_UNS_Production_UU, UNS_Production_UU);
	}

	/** Get UNS_Production_UU.
		@return UNS_Production_UU	  */
	public String getUNS_Production_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Production_UU);
	}

	public com.uns.model.I_UNS_Resource getUNS_Resource() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource)MTable.get(getCtx(), com.uns.model.I_UNS_Resource.Table_Name)
			.getPO(getUNS_Resource_ID(), get_TrxName());	}

	/** Set Manufacture Resource.
		@param UNS_Resource_ID Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID)
	{
		if (UNS_Resource_ID < 1) 
			set_Value (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
	}

	/** Get Manufacture Resource.
		@return Manufacture Resource	  */
	public int getUNS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Daily = DAY */
	public static final String WORKERRESULTTYPE_Daily = "DAY";
	/** Group Result = GRR */
	public static final String WORKERRESULTTYPE_GroupResult = "GRR";
	/** Personal Result = PSR */
	public static final String WORKERRESULTTYPE_PersonalResult = "PSR";
	/** Set Worker Result Type.
		@param WorkerResultType Worker Result Type	  */
	public void setWorkerResultType (String WorkerResultType)
	{

		set_Value (COLUMNNAME_WorkerResultType, WorkerResultType);
	}

	/** Get Worker Result Type.
		@return Worker Result Type	  */
	public String getWorkerResultType () 
	{
		return (String)get_Value(COLUMNNAME_WorkerResultType);
	}
}