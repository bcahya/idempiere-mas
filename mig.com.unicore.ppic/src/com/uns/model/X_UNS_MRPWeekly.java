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

/** Generated Model for UNS_MRPWeekly
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MRPWeekly extends PO implements I_UNS_MRPWeekly, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20131005L;

    /** Standard Constructor */
    public X_UNS_MRPWeekly (Properties ctx, int UNS_MRPWeekly_ID, String trxName)
    {
      super (ctx, UNS_MRPWeekly_ID, trxName);
      /** if (UNS_MRPWeekly_ID == 0)
        {
			setAOR (Env.ZERO);
// 0
			setAOREQ (Env.ZERO);
// 0
			setASOR (Env.ZERO);
// 0
			setActualProduced (Env.ZERO);
// 0
			setIsUpdated (false);
// N
			setM_Product_ID (0);
			setPOH (Env.ZERO);
// 0
			setProjectedRequirement (Env.ZERO);
// 0
			setQtyMiscUsed (Env.ZERO);
// 0
			setSOH (Env.ZERO);
// 0
			setScheduledRequirement (Env.ZERO);
// 0
			setUNS_MRPWeekly_ID (0);
			setUNS_MRP_ID (0);
			setWeekNo (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MRPWeekly (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MRPWeekly[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AOR.
		@param AOR 
		Actual Order Receipt
	  */
	public void setAOR (BigDecimal AOR)
	{
		set_ValueNoCheck (COLUMNNAME_AOR, AOR);
	}

	/** Get AOR.
		@return Actual Order Receipt
	  */
	public BigDecimal getAOR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AOR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AORE.
		@param AORE 
		Actual Order Release
	  */
	public void setAORE (BigDecimal AORE)
	{
		set_Value (COLUMNNAME_AORE, AORE);
	}

	/** Get AORE.
		@return Actual Order Release
	  */
	public BigDecimal getAORE () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AORE);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AOREQ.
		@param AOREQ 
		Actual Order Requested (Issued Requisition)
	  */
	public void setAOREQ (BigDecimal AOREQ)
	{
		set_ValueNoCheck (COLUMNNAME_AOREQ, AOREQ);
	}

	/** Get AOREQ.
		@return Actual Order Requested (Issued Requisition)
	  */
	public BigDecimal getAOREQ () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AOREQ);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ASOR.
		@param ASOR 
		(Actual Scheduled Order Receipt), is the actual schedule to receive material stated by date promised on the PO.
	  */
	public void setASOR (BigDecimal ASOR)
	{
		set_ValueNoCheck (COLUMNNAME_ASOR, ASOR);
	}

	/** Get ASOR.
		@return (Actual Scheduled Order Receipt), is the actual schedule to receive material stated by date promised on the PO.
	  */
	public BigDecimal getASOR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ASOR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Actual Manufactured.
		@param ActualProduced 
		Latest amount actually manufactured in UOM quantity
	  */
	public void setActualProduced (BigDecimal ActualProduced)
	{
		set_ValueNoCheck (COLUMNNAME_ActualProduced, ActualProduced);
	}

	/** Get Actual Manufactured.
		@return Latest amount actually manufactured in UOM quantity
	  */
	public BigDecimal getActualProduced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualProduced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set Has Updated.
		@param IsUpdated 
		Has Updated
	  */
	public void setIsUpdated (boolean IsUpdated)
	{
		set_Value (COLUMNNAME_IsUpdated, Boolean.valueOf(IsUpdated));
	}

	/** Get Has Updated.
		@return Has Updated
	  */
	public boolean isUpdated () 
	{
		Object oo = get_Value(COLUMNNAME_IsUpdated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set POH.
		@param POH 
		Projected On Hand
	  */
	public void setPOH (BigDecimal POH)
	{
		set_Value (COLUMNNAME_POH, POH);
	}

	/** Get POH.
		@return Projected On Hand
	  */
	public BigDecimal getPOH () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_POH);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PO Issue Plan Date.
		@param POIssuePlanDate PO Issue Plan Date	  */
	public void setPOIssuePlanDate (Timestamp POIssuePlanDate)
	{
		set_Value (COLUMNNAME_POIssuePlanDate, POIssuePlanDate);
	}

	/** Get PO Issue Plan Date.
		@return PO Issue Plan Date	  */
	public Timestamp getPOIssuePlanDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_POIssuePlanDate);
	}

	/** Set POR.
		@param POR 
		Projected Order Receipts
	  */
	public void setPOR (BigDecimal POR)
	{
		set_Value (COLUMNNAME_POR, POR);
	}

	/** Get POR.
		@return Projected Order Receipts
	  */
	public BigDecimal getPOR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_POR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PORE.
		@param PORE 
		Projected Order Release
	  */
	public void setPORE (BigDecimal PORE)
	{
		set_Value (COLUMNNAME_PORE, PORE);
	}

	/** Get PORE.
		@return Projected Order Release
	  */
	public BigDecimal getPORE () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PORE);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_OrderLine getPORef() throws RuntimeException
    {
		return (org.compiere.model.I_C_OrderLine)MTable.get(getCtx(), org.compiere.model.I_C_OrderLine.Table_Name)
			.getPO(getPORef_ID(), get_TrxName());	}

	/** Set PO Reference.
		@param PORef_ID PO Reference	  */
	public void setPORef_ID (int PORef_ID)
	{
		if (PORef_ID < 1) 
			set_Value (COLUMNNAME_PORef_ID, null);
		else 
			set_Value (COLUMNNAME_PORef_ID, Integer.valueOf(PORef_ID));
	}

	/** Get PO Reference.
		@return PO Reference	  */
	public int getPORef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PORef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PO No.
		@param PO_No PO No	  */
	public void setPO_No (BigDecimal PO_No)
	{
		set_Value (COLUMNNAME_PO_No, PO_No);
	}

	/** Get PO No.
		@return PO No	  */
	public BigDecimal getPO_No () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PO_No);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Projected Requirement.
		@param ProjectedRequirement 
		Projected requirement of material based on forecasting (master) production schedule.
	  */
	public void setProjectedRequirement (BigDecimal ProjectedRequirement)
	{
		set_ValueNoCheck (COLUMNNAME_ProjectedRequirement, ProjectedRequirement);
	}

	/** Get Projected Requirement.
		@return Projected requirement of material based on forecasting (master) production schedule.
	  */
	public BigDecimal getProjectedRequirement () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProjectedRequirement);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Misc. Used Qty.
		@param QtyMiscUsed 
		Miscellaneous used qty (other used besides quantity to deliver)
	  */
	public void setQtyMiscUsed (BigDecimal QtyMiscUsed)
	{
		set_ValueNoCheck (COLUMNNAME_QtyMiscUsed, QtyMiscUsed);
	}

	/** Get Misc. Used Qty.
		@return Miscellaneous used qty (other used besides quantity to deliver)
	  */
	public BigDecimal getQtyMiscUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyMiscUsed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set On Hand Quantity.
		@param QtyOnHand 
		On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	/** Get On Hand Quantity.
		@return On Hand Quantity
	  */
	public BigDecimal getQtyOnHand () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHand);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_RequisitionLine getRequisitionRef() throws RuntimeException
    {
		return (org.compiere.model.I_M_RequisitionLine)MTable.get(getCtx(), org.compiere.model.I_M_RequisitionLine.Table_Name)
			.getPO(getRequisitionRef_ID(), get_TrxName());	}

	/** Set Requisition Reference.
		@param RequisitionRef_ID Requisition Reference	  */
	public void setRequisitionRef_ID (int RequisitionRef_ID)
	{
		if (RequisitionRef_ID < 1) 
			set_Value (COLUMNNAME_RequisitionRef_ID, null);
		else 
			set_Value (COLUMNNAME_RequisitionRef_ID, Integer.valueOf(RequisitionRef_ID));
	}

	/** Get Requisition Reference.
		@return Requisition Reference	  */
	public int getRequisitionRef_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RequisitionRef_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SOH.
		@param SOH 
		Scheduled (Stock) On Hand based on Manufacturing Order (Schedule)
	  */
	public void setSOH (BigDecimal SOH)
	{
		set_ValueNoCheck (COLUMNNAME_SOH, SOH);
	}

	/** Get SOH.
		@return Scheduled (Stock) On Hand based on Manufacturing Order (Schedule)
	  */
	public BigDecimal getSOH () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SOH);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SOR.
		@param SOR 
		Schedule Order Receipt
	  */
	public void setSOR (BigDecimal SOR)
	{
		set_Value (COLUMNNAME_SOR, SOR);
	}

	/** Get SOR.
		@return Schedule Order Receipt
	  */
	public BigDecimal getSOR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SOR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Scheduled Requirement.
		@param ScheduledRequirement 
		Scheduled material requirement based on ordered/scheduled manufacturing process (defined in Manufacturing Order)
	  */
	public void setScheduledRequirement (BigDecimal ScheduledRequirement)
	{
		set_ValueNoCheck (COLUMNNAME_ScheduledRequirement, ScheduledRequirement);
	}

	/** Get Scheduled Requirement.
		@return Scheduled material requirement based on ordered/scheduled manufacturing process (defined in Manufacturing Order)
	  */
	public BigDecimal getScheduledRequirement () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ScheduledRequirement);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set Material Requirement Weekly.
		@param UNS_MRPWeekly_ID Material Requirement Weekly	  */
	public void setUNS_MRPWeekly_ID (int UNS_MRPWeekly_ID)
	{
		if (UNS_MRPWeekly_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MRPWeekly_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MRPWeekly_ID, Integer.valueOf(UNS_MRPWeekly_ID));
	}

	/** Get Material Requirement Weekly.
		@return Material Requirement Weekly	  */
	public int getUNS_MRPWeekly_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MRPWeekly_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MRPWeekly_UU.
		@param UNS_MRPWeekly_UU UNS_MRPWeekly_UU	  */
	public void setUNS_MRPWeekly_UU (String UNS_MRPWeekly_UU)
	{
		set_Value (COLUMNNAME_UNS_MRPWeekly_UU, UNS_MRPWeekly_UU);
	}

	/** Get UNS_MRPWeekly_UU.
		@return UNS_MRPWeekly_UU	  */
	public String getUNS_MRPWeekly_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MRPWeekly_UU);
	}

	public com.uns.model.I_UNS_MRP getUNS_MRP() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MRP)MTable.get(getCtx(), com.uns.model.I_UNS_MRP.Table_Name)
			.getPO(getUNS_MRP_ID(), get_TrxName());	}

	/** Set Material Requirement Production.
		@param UNS_MRP_ID Material Requirement Production	  */
	public void setUNS_MRP_ID (int UNS_MRP_ID)
	{
		if (UNS_MRP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MRP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MRP_ID, Integer.valueOf(UNS_MRP_ID));
	}

	/** Get Material Requirement Production.
		@return Material Requirement Production	  */
	public int getUNS_MRP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MRP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Week No.
		@param WeekNo Week No	  */
	public void setWeekNo (int WeekNo)
	{
		set_Value (COLUMNNAME_WeekNo, Integer.valueOf(WeekNo));
	}

	/** Get Week No.
		@return Week No	  */
	public int getWeekNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WeekNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}