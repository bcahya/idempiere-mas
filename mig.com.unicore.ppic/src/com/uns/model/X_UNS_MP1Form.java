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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_MP1Form
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MP1Form extends PO implements I_UNS_MP1Form, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140226L;

    /** Standard Constructor */
    public X_UNS_MP1Form (Properties ctx, int UNS_MP1Form_ID, String trxName)
    {
      super (ctx, UNS_MP1Form_ID, trxName);
      /** if (UNS_MP1Form_ID == 0)
        {
			setAD_OrgTo_ID (0);
// 0
			setAD_OrgTrx_ID (0);
			setIsCreated (false);
// N
			setIsKBPecahSegar (false);
// N
			setUNS_MP1Form_ID (0);
			setUNS_Resource_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MP1Form (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MP1Form[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Org getAD_OrgTo() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Org)MTable.get(getCtx(), org.compiere.model.I_AD_Org.Table_Name)
			.getPO(getAD_OrgTo_ID(), get_TrxName());	}

	/** Set Inter-Department.
		@param AD_OrgTo_ID 
		Departmention valid for intercompany documents
	  */
	public void setAD_OrgTo_ID (int AD_OrgTo_ID)
	{
		if (AD_OrgTo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_OrgTo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_OrgTo_ID, Integer.valueOf(AD_OrgTo_ID));
	}

	/** Get Inter-Department.
		@return Departmention valid for intercompany documents
	  */
	public int getAD_OrgTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Trx Department.
		@param AD_OrgTrx_ID 
		Performing or initiating Department
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
    }

	/** Set Records created.
		@param IsCreated Records created	  */
	public void setIsCreated (boolean IsCreated)
	{
		set_Value (COLUMNNAME_IsCreated, Boolean.valueOf(IsCreated));
	}

	/** Get Records created.
		@return Records created	  */
	public boolean isCreated () 
	{
		Object oo = get_Value(COLUMNNAME_IsCreated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set KB Pecah Segar.
		@param IsKBPecahSegar KB Pecah Segar	  */
	public void setIsKBPecahSegar (boolean IsKBPecahSegar)
	{
		set_Value (COLUMNNAME_IsKBPecahSegar, Boolean.valueOf(IsKBPecahSegar));
	}

	/** Get KB Pecah Segar.
		@return KB Pecah Segar	  */
	public boolean isKBPecahSegar () 
	{
		Object oo = get_Value(COLUMNNAME_IsKBPecahSegar);
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

	public org.compiere.model.I_M_Movement getM_Movement() throws RuntimeException
    {
		return (org.compiere.model.I_M_Movement)MTable.get(getCtx(), org.compiere.model.I_M_Movement.Table_Name)
			.getPO(getM_Movement_ID(), get_TrxName());	}

	/** Set Inventory Move.
		@param M_Movement_ID 
		Movement of Inventory
	  */
	public void setM_Movement_ID (int M_Movement_ID)
	{
		if (M_Movement_ID < 1) 
			set_Value (COLUMNNAME_M_Movement_ID, null);
		else 
			set_Value (COLUMNNAME_M_Movement_ID, Integer.valueOf(M_Movement_ID));
	}

	/** Get Inventory Move.
		@return Movement of Inventory
	  */
	public int getM_Movement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Movement_ID);
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
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** Set Open/View Form Input.
		@param OpenFormInput 
		Open the form to input this document details in one page
	  */
	public void setOpenFormInput (String OpenFormInput)
	{
		set_Value (COLUMNNAME_OpenFormInput, OpenFormInput);
	}

	/** Get Open/View Form Input.
		@return Open the form to input this document details in one page
	  */
	public String getOpenFormInput () 
	{
		return (String)get_Value(COLUMNNAME_OpenFormInput);
	}

	/** Set Pre Reject.
		@param PreReject 
		Pre Reject for LKU
	  */
	public void setPreReject (BigDecimal PreReject)
	{
		set_Value (COLUMNNAME_PreReject, PreReject);
	}

	/** Get Pre Reject.
		@return Pre Reject for LKU
	  */
	public BigDecimal getPreReject () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PreReject);
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

	/** Set Remarks.
		@param Remarks Remarks	  */
	public void setRemarks (String Remarks)
	{
		set_Value (COLUMNNAME_Remarks, Remarks);
	}

	/** Get Remarks.
		@return Remarks	  */
	public String getRemarks () 
	{
		return (String)get_Value(COLUMNNAME_Remarks);
	}

	/** Shift AD_Reference_ID=1000168 */
	public static final int SHIFT_AD_Reference_ID=1000168;
	/** One = 1 */
	public static final String SHIFT_One = "1";
	/** Two = 2 */
	public static final String SHIFT_Two = "2";
	/** Three = 3 */
	public static final String SHIFT_Three = "3";
	/** Set Shift.
		@param Shift Shift	  */
	public void setShift (String Shift)
	{

		set_Value (COLUMNNAME_Shift, Shift);
	}

	/** Get Shift.
		@return Shift	  */
	public String getShift () 
	{
		return (String)get_Value(COLUMNNAME_Shift);
	}

	/** Set Supervisor Sheller 2.
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

	/** Get Supervisor Sheller 2.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Supervisor Parrer 1.
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

	/** Get Supervisor Parrer 1.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor2a_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor2a_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Supervisor Parrer 2.
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

	/** Get Supervisor Parrer 2.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor2b_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor2b_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Supervisor Sheller 1.
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

	/** Get Supervisor Sheller 1.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	public int getSupervisor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Teoritis Black Meat.
		@param TeoritisBKM Teoritis Black Meat	  */
	public void setTeoritisBKM (BigDecimal TeoritisBKM)
	{
		set_Value (COLUMNNAME_TeoritisBKM, TeoritisBKM);
	}

	/** Get Teoritis Black Meat.
		@return Teoritis Black Meat	  */
	public BigDecimal getTeoritisBKM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TeoritisBKM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Teoritis KC Busuk.
		@param TeoritisBM Teoritis KC Busuk	  */
	public void setTeoritisBM (BigDecimal TeoritisBM)
	{
		set_Value (COLUMNNAME_TeoritisBM, TeoritisBM);
	}

	/** Get Teoritis KC Busuk.
		@return Teoritis KC Busuk	  */
	public BigDecimal getTeoritisBM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TeoritisBM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Teoritis Raw Meat.
		@param TeoritisRM Teoritis Raw Meat	  */
	public void setTeoritisRM (BigDecimal TeoritisRM)
	{
		set_Value (COLUMNNAME_TeoritisRM, TeoritisRM);
	}

	/** Get Teoritis Raw Meat.
		@return Teoritis Raw Meat	  */
	public BigDecimal getTeoritisRM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TeoritisRM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Teoritis Skin Meat.
		@param TeoritisSM Teoritis Skin Meat	  */
	public void setTeoritisSM (BigDecimal TeoritisSM)
	{
		set_Value (COLUMNNAME_TeoritisSM, TeoritisSM);
	}

	/** Get Teoritis Skin Meat.
		@return Teoritis Skin Meat	  */
	public BigDecimal getTeoritisSM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TeoritisSM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set MP1 Form.
		@param UNS_MP1Form_ID MP1 Form	  */
	public void setUNS_MP1Form_ID (int UNS_MP1Form_ID)
	{
		if (UNS_MP1Form_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MP1Form_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MP1Form_ID, Integer.valueOf(UNS_MP1Form_ID));
	}

	/** Get MP1 Form.
		@return MP1 Form	  */
	public int getUNS_MP1Form_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MP1Form_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MP1Form_UU.
		@param UNS_MP1Form_UU UNS_MP1Form_UU	  */
	public void setUNS_MP1Form_UU (String UNS_MP1Form_UU)
	{
		set_Value (COLUMNNAME_UNS_MP1Form_UU, UNS_MP1Form_UU);
	}

	/** Get UNS_MP1Form_UU.
		@return UNS_MP1Form_UU	  */
	public String getUNS_MP1Form_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MP1Form_UU);
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
}