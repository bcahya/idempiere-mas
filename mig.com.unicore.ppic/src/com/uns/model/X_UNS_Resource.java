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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Resource
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Resource extends PO implements I_UNS_Resource, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20150312L;

    /** Standard Constructor */
    public X_UNS_Resource (Properties ctx, int UNS_Resource_ID, String trxName)
    {
      super (ctx, UNS_Resource_ID, trxName);
      /** if (UNS_Resource_ID == 0)
        {
			setIsParentEndNode (true);
// Y
			setIsParentStartNode (true);
// Y
			setIsRework (false);
// N
			setIsWorkerBase (false);
			setMustSyncWithMPS (true);
// Y
			setName (null);
			setResourceType (null);
			setUNS_Resource_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_UNS_Resource (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Resource[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** Set Copy From.
		@param CopyFrom 
		Copy From Record
	  */
	public void setCopyFrom (String CopyFrom)
	{
		set_Value (COLUMNNAME_CopyFrom, CopyFrom);
	}

	/** Get Copy From.
		@return Copy From Record
	  */
	public String getCopyFrom () 
	{
		return (String)get_Value(COLUMNNAME_CopyFrom);
	}

	/** Set Copy Lines.
		@param CopyLines Copy Lines	  */
	public void setCopyLines (String CopyLines)
	{
		set_Value (COLUMNNAME_CopyLines, CopyLines);
	}

	/** Get Copy Lines.
		@return Copy Lines	  */
	public String getCopyLines () 
	{
		return (String)get_Value(COLUMNNAME_CopyLines);
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

	/** Set Generate Input/Output.
		@param GenerateInOut Generate Input/Output	  */
	public void setGenerateInOut (String GenerateInOut)
	{
		set_Value (COLUMNNAME_GenerateInOut, GenerateInOut);
	}

	/** Get Generate Input/Output.
		@return Generate Input/Output	  */
	public String getGenerateInOut () 
	{
		return (String)get_Value(COLUMNNAME_GenerateInOut);
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

	/** Set Is Parent's End Node.
		@param IsParentEndNode 
		This is to indicate if this resource is parent's end node.
	  */
	public void setIsParentEndNode (boolean IsParentEndNode)
	{
		set_Value (COLUMNNAME_IsParentEndNode, Boolean.valueOf(IsParentEndNode));
	}

	/** Get Is Parent's End Node.
		@return This is to indicate if this resource is parent's end node.
	  */
	public boolean isParentEndNode () 
	{
		Object oo = get_Value(COLUMNNAME_IsParentEndNode);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Parent's Start Node.
		@param IsParentStartNode 
		This is to indicate if this resource is parent's start node.
	  */
	public void setIsParentStartNode (boolean IsParentStartNode)
	{
		set_Value (COLUMNNAME_IsParentStartNode, Boolean.valueOf(IsParentStartNode));
	}

	/** Get Is Parent's Start Node.
		@return This is to indicate if this resource is parent's start node.
	  */
	public boolean isParentStartNode () 
	{
		Object oo = get_Value(COLUMNNAME_IsParentStartNode);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Rework.
		@param IsRework 
		This resource's main job is considered as rework (reprocess)
	  */
	public void setIsRework (boolean IsRework)
	{
		set_Value (COLUMNNAME_IsRework, Boolean.valueOf(IsRework));
	}

	/** Get Is Rework.
		@return This resource's main job is considered as rework (reprocess)
	  */
	public boolean isRework () 
	{
		Object oo = get_Value(COLUMNNAME_IsRework);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Subcontracting.
		@param IsSubcontracting Is Subcontracting	  */
	public void setIsSubcontracting (boolean IsSubcontracting)
	{
		set_Value (COLUMNNAME_IsSubcontracting, Boolean.valueOf(IsSubcontracting));
	}

	/** Get Is Subcontracting.
		@return Is Subcontracting	  */
	public boolean isSubcontracting () 
	{
		Object oo = get_Value(COLUMNNAME_IsSubcontracting);
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

	/** Set Max Machine Suprv 1.
		@param MaxMachineSuprv1 Max Machine Suprv 1	  */
	public void setMaxMachineSuprv1 (int MaxMachineSuprv1)
	{
		set_Value (COLUMNNAME_MaxMachineSuprv1, Integer.valueOf(MaxMachineSuprv1));
	}

	/** Get Max Machine Suprv 1.
		@return Max Machine Suprv 1	  */
	public int getMaxMachineSuprv1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxMachineSuprv1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Max Machine Suprv 2.
		@param MaxMachineSuprv2 Max Machine Suprv 2	  */
	public void setMaxMachineSuprv2 (int MaxMachineSuprv2)
	{
		set_Value (COLUMNNAME_MaxMachineSuprv2, Integer.valueOf(MaxMachineSuprv2));
	}

	/** Get Max Machine Suprv 2.
		@return Max Machine Suprv 2	  */
	public int getMaxMachineSuprv2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxMachineSuprv2);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Daily = DAY */
	public static final String PAYMENTMETHOD_Daily = "DAY";
	/** Group Result = GRR */
	public static final String PAYMENTMETHOD_GroupResult = "GRR";
	/** Personal Result = PSR */
	public static final String PAYMENTMETHOD_PersonalResult = "PSR";
	/** Set Result Method.
		@param PaymentMethod Result Method	  */
	public void setPaymentMethod (String PaymentMethod)
	{

		set_Value (COLUMNNAME_PaymentMethod, PaymentMethod);
	}

	/** Get Result Method.
		@return Result Method	  */
	public String getPaymentMethod () 
	{
		return (String)get_Value(COLUMNNAME_PaymentMethod);
	}

	/** Monthly = 01 */
	public static final String PAYROLLTERM_Monthly = "01";
	/** Weekly = 02 */
	public static final String PAYROLLTERM_Weekly = "02";
	/** 2 Weekly = 03 */
	public static final String PAYROLLTERM_2Weekly = "03";
	/** Harian Bulanan = 04 */
	public static final String PAYROLLTERM_HarianBulanan = "04";
	/** Set Payroll Term.
		@param PayrollTerm Payroll Term	  */
	public void setPayrollTerm (String PayrollTerm)
	{

		set_Value (COLUMNNAME_PayrollTerm, PayrollTerm);
	}

	/** Get Payroll Term.
		@return Payroll Term	  */
	public String getPayrollTerm () 
	{
		return (String)get_Value(COLUMNNAME_PayrollTerm);
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

	/** Set Reset MRP FG BOM.
		@param ResetMRPFGBOM Reset MRP FG BOM	  */
	public void setResetMRPFGBOM (String ResetMRPFGBOM)
	{
		set_Value (COLUMNNAME_ResetMRPFGBOM, ResetMRPFGBOM);
	}

	/** Get Reset MRP FG BOM.
		@return Reset MRP FG BOM	  */
	public String getResetMRPFGBOM () 
	{
		return (String)get_Value(COLUMNNAME_ResetMRPFGBOM);
	}

	public com.uns.model.I_UNS_Resource getResourceParent() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource)MTable.get(getCtx(), com.uns.model.I_UNS_Resource.Table_Name)
			.getPO(getResourceParent_ID(), get_TrxName());	}

	/** Set Resource Parent.
		@param ResourceParent_ID 
		Parent of Entity
	  */
	public void setResourceParent_ID (int ResourceParent_ID)
	{
		if (ResourceParent_ID < 1) 
			set_Value (COLUMNNAME_ResourceParent_ID, null);
		else 
			set_Value (COLUMNNAME_ResourceParent_ID, Integer.valueOf(ResourceParent_ID));
	}

	/** Get Resource Parent.
		@return Parent of Entity
	  */
	public int getResourceParent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ResourceParent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 6. Machine = MC */
	public static final String RESOURCETYPE_6Machine = "MC";
	/** 1. Plant = PA */
	public static final String RESOURCETYPE_1Plant = "PA";
	/** 2. Production Line = PL */
	public static final String RESOURCETYPE_2ProductionLine = "PL";
	/** 3. Work Center = WC */
	public static final String RESOURCETYPE_3WorkCenter = "WC";
	/** 5. Worker = WO */
	public static final String RESOURCETYPE_5Worker = "WO";
	/** 4. Work Station = WS */
	public static final String RESOURCETYPE_4WorkStation = "WS";
	/** Set Resource Type.
		@param ResourceType Resource Type	  */
	public void setResourceType (String ResourceType)
	{

		set_Value (COLUMNNAME_ResourceType, ResourceType);
	}

	/** Get Resource Type.
		@return Resource Type	  */
	public String getResourceType () 
	{
		return (String)get_Value(COLUMNNAME_ResourceType);
	}

	/** Set Supervisor.
		@param Supervisor1_ID 
		Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor1_ID (int Supervisor1_ID)
	{
		if (Supervisor1_ID < 1) 
			set_Value (COLUMNNAME_Supervisor1_ID, null);
		else 
			set_Value (COLUMNNAME_Supervisor1_ID, Integer.valueOf(Supervisor1_ID));
	}

	/** Get Supervisor.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Supervisor.
		@param Supervisor2a_ID 
		Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor2a_ID (int Supervisor2a_ID)
	{
		if (Supervisor2a_ID < 1) 
			set_Value (COLUMNNAME_Supervisor2a_ID, null);
		else 
			set_Value (COLUMNNAME_Supervisor2a_ID, Integer.valueOf(Supervisor2a_ID));
	}

	/** Get Supervisor.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor2a_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor2a_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Supervisor.
		@param Supervisor2b_ID 
		Supervisor for this user/organization - used for escalation and approval
	  */
	public void setSupervisor2b_ID (int Supervisor2b_ID)
	{
		if (Supervisor2b_ID < 1) 
			set_Value (COLUMNNAME_Supervisor2b_ID, null);
		else 
			set_Value (COLUMNNAME_Supervisor2b_ID, Integer.valueOf(Supervisor2b_ID));
	}

	/** Get Supervisor.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor2b_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor2b_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Manufacture Resource.
		@param UNS_Resource_ID Manufacture Resource	  */
	public void setUNS_Resource_ID (int UNS_Resource_ID)
	{
		if (UNS_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_ID, Integer.valueOf(UNS_Resource_ID));
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

	/** Set UNS_Resource_UU.
		@param UNS_Resource_UU UNS_Resource_UU	  */
	public void setUNS_Resource_UU (String UNS_Resource_UU)
	{
		set_Value (COLUMNNAME_UNS_Resource_UU, UNS_Resource_UU);
	}

	/** Get UNS_Resource_UU.
		@return UNS_Resource_UU	  */
	public String getUNS_Resource_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Resource_UU);
	}

	public com.uns.model.I_UNS_SlotType getUNS_SlotType() throws RuntimeException
    {
		return (com.uns.model.I_UNS_SlotType)MTable.get(getCtx(), com.uns.model.I_UNS_SlotType.Table_Name)
			.getPO(getUNS_SlotType_ID(), get_TrxName());	}

	/** Set Slot Type.
		@param UNS_SlotType_ID Slot Type	  */
	public void setUNS_SlotType_ID (int UNS_SlotType_ID)
	{
		if (UNS_SlotType_ID < 1) 
			set_Value (COLUMNNAME_UNS_SlotType_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_SlotType_ID, Integer.valueOf(UNS_SlotType_ID));
	}

	/** Get Slot Type.
		@return Slot Type	  */
	public int getUNS_SlotType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_SlotType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getValue());
    }
}