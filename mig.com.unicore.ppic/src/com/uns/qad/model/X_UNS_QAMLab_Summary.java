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

/** Generated Model for UNS_QAMLab_Summary
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_QAMLab_Summary extends PO implements I_UNS_QAMLab_Summary, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130611L;

    /** Standard Constructor */
    public X_UNS_QAMLab_Summary (Properties ctx, int UNS_QAMLab_Summary_ID, String trxName)
    {
      super (ctx, UNS_QAMLab_Summary_ID, trxName);
      /** if (UNS_QAMLab_Summary_ID == 0)
        {
			setCumulatedQty (Env.ZERO);
			setDateReceived (new Timestamp( System.currentTimeMillis() ));
			setDeliveryTime_Actual (new Timestamp( System.currentTimeMillis() ));
			setDeliveryTime_Promised (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// PR
			setDocStatus (null);
// DR
			setIsApproved (false);
// N
			setM_LocatorTo_ID (0);
			setM_Product_ID (0);
			setMovementDate (new Timestamp( System.currentTimeMillis() ));
			setName (null);
			setProcessed (false);
// N
			setProcessedOn (Env.ZERO);
			setTemperature (Env.ZERO);
			setUNS_Employee_ID (0);
			setUNS_QAMLab_Summary_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_QAMLab_Summary (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_QAMLab_Summary[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accumulated Qty.
		@param CumulatedQty 
		Total Quantity
	  */
	public void setCumulatedQty (BigDecimal CumulatedQty)
	{
		set_Value (COLUMNNAME_CumulatedQty, CumulatedQty);
	}

	/** Get Accumulated Qty.
		@return Total Quantity
	  */
	public BigDecimal getCumulatedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CumulatedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Date received.
		@param DateReceived 
		Date a product was received
	  */
	public void setDateReceived (Timestamp DateReceived)
	{
		set_Value (COLUMNNAME_DateReceived, DateReceived);
	}

	/** Get Date received.
		@return Date a product was received
	  */
	public Timestamp getDateReceived () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateReceived);
	}

	/** Set Actual Delivery Time.
		@param DeliveryTime_Actual 
		Actual days between order and delivery
	  */
	public void setDeliveryTime_Actual (Timestamp DeliveryTime_Actual)
	{
		set_Value (COLUMNNAME_DeliveryTime_Actual, DeliveryTime_Actual);
	}

	/** Get Actual Delivery Time.
		@return Actual days between order and delivery
	  */
	public Timestamp getDeliveryTime_Actual () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DeliveryTime_Actual);
	}

	/** Set Promised Delivery Time.
		@param DeliveryTime_Promised 
		Promised days between order and delivery
	  */
	public void setDeliveryTime_Promised (Timestamp DeliveryTime_Promised)
	{
		set_Value (COLUMNNAME_DeliveryTime_Promised, DeliveryTime_Promised);
	}

	/** Get Promised Delivery Time.
		@return Promised days between order and delivery
	  */
	public Timestamp getDeliveryTime_Promised () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DeliveryTime_Promised);
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

	public I_M_Locator getM_LocatorTo() throws RuntimeException
    {
		return (I_M_Locator)MTable.get(getCtx(), I_M_Locator.Table_Name)
			.getPO(getM_LocatorTo_ID(), get_TrxName());	}

	/** Set Locator To.
		@param M_LocatorTo_ID 
		Location inventory is moved to
	  */
	public void setM_LocatorTo_ID (int M_LocatorTo_ID)
	{
		if (M_LocatorTo_ID < 1) 
			set_Value (COLUMNNAME_M_LocatorTo_ID, null);
		else 
			set_Value (COLUMNNAME_M_LocatorTo_ID, Integer.valueOf(M_LocatorTo_ID));
	}

	/** Get Locator To.
		@return Location inventory is moved to
	  */
	public int getM_LocatorTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_LocatorTo_ID);
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

	/** Set Temperature.
		@param Temperature Temperature	  */
	public void setTemperature (BigDecimal Temperature)
	{
		set_Value (COLUMNNAME_Temperature, Temperature);
	}

	/** Get Temperature.
		@return Temperature	  */
	public BigDecimal getTemperature () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Temperature);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Employee.
		@param UNS_Employee_ID Employee	  */
	public void setUNS_Employee_ID (int UNS_Employee_ID)
	{
		if (UNS_Employee_ID < 1) 
			set_Value (COLUMNNAME_UNS_Employee_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Employee_ID, Integer.valueOf(UNS_Employee_ID));
	}

	/** Get Employee.
		@return Employee	  */
	public int getUNS_Employee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Employee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** Set UNS_QAMLab_Summary_UU.
		@param UNS_QAMLab_Summary_UU UNS_QAMLab_Summary_UU	  */
	public void setUNS_QAMLab_Summary_UU (String UNS_QAMLab_Summary_UU)
	{
		set_Value (COLUMNNAME_UNS_QAMLab_Summary_UU, UNS_QAMLab_Summary_UU);
	}

	/** Get UNS_QAMLab_Summary_UU.
		@return UNS_QAMLab_Summary_UU	  */
	public String getUNS_QAMLab_Summary_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_QAMLab_Summary_UU);
	}
}