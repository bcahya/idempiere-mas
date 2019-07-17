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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_MO_SchedulerLines
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MO_SchedulerLines extends PO implements I_UNS_MO_SchedulerLines, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130607L;

    /** Standard Constructor */
    public X_UNS_MO_SchedulerLines (Properties ctx, int UNS_MO_SchedulerLines_ID, String trxName)
    {
      super (ctx, UNS_MO_SchedulerLines_ID, trxName);
      /** if (UNS_MO_SchedulerLines_ID == 0)
        {
			setCreateProduction (null);
// N
			setDescription (null);
			setScheduledDate (new Timestamp( System.currentTimeMillis() ));
			setUNS_MO_Scheduler_ID (0);
			setUNS_MO_SchedulerLines_ID (0);
			setUNS_ProductionSchedule_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MO_SchedulerLines (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_UNS_MO_SchedulerLines[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Create Production.
		@param CreateProduction Create Production	  */
	public void setCreateProduction (String CreateProduction)
	{
		set_Value (COLUMNNAME_CreateProduction, CreateProduction);
	}

	/** Get Create Production.
		@return Create Production	  */
	public String getCreateProduction () 
	{
		return (String)get_Value(COLUMNNAME_CreateProduction);
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

	/** Set Scheduled Date.
		@param ScheduledDate Scheduled Date	  */
	public void setScheduledDate (Timestamp ScheduledDate)
	{
		set_Value (COLUMNNAME_ScheduledDate, ScheduledDate);
	}

	/** Get Scheduled Date.
		@return Scheduled Date	  */
	public Timestamp getScheduledDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ScheduledDate);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getScheduledDate()));
    }

	public com.uns.model.I_UNS_MO_Scheduler getUNS_MO_Scheduler() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MO_Scheduler)MTable.get(getCtx(), com.uns.model.I_UNS_MO_Scheduler.Table_Name)
			.getPO(getUNS_MO_Scheduler_ID(), get_TrxName());	}

	/** Set MO Scheduler.
		@param UNS_MO_Scheduler_ID MO Scheduler	  */
	public void setUNS_MO_Scheduler_ID (int UNS_MO_Scheduler_ID)
	{
		if (UNS_MO_Scheduler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MO_Scheduler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MO_Scheduler_ID, Integer.valueOf(UNS_MO_Scheduler_ID));
	}

	/** Get MO Scheduler.
		@return MO Scheduler	  */
	public int getUNS_MO_Scheduler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MO_Scheduler_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MO Scheduler Lines.
		@param UNS_MO_SchedulerLines_ID MO Scheduler Lines	  */
	public void setUNS_MO_SchedulerLines_ID (int UNS_MO_SchedulerLines_ID)
	{
		if (UNS_MO_SchedulerLines_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MO_SchedulerLines_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MO_SchedulerLines_ID, Integer.valueOf(UNS_MO_SchedulerLines_ID));
	}

	/** Get MO Scheduler Lines.
		@return MO Scheduler Lines	  */
	public int getUNS_MO_SchedulerLines_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MO_SchedulerLines_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MO_SchedulerLines_UU.
		@param UNS_MO_SchedulerLines_UU UNS_MO_SchedulerLines_UU	  */
	public void setUNS_MO_SchedulerLines_UU (String UNS_MO_SchedulerLines_UU)
	{
		set_Value (COLUMNNAME_UNS_MO_SchedulerLines_UU, UNS_MO_SchedulerLines_UU);
	}

	/** Get UNS_MO_SchedulerLines_UU.
		@return UNS_MO_SchedulerLines_UU	  */
	public String getUNS_MO_SchedulerLines_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MO_SchedulerLines_UU);
	}

	public com.uns.model.I_UNS_ProductionSchedule getUNS_ProductionSchedule() throws RuntimeException
    {
		return (com.uns.model.I_UNS_ProductionSchedule)MTable.get(getCtx(), com.uns.model.I_UNS_ProductionSchedule.Table_Name)
			.getPO(getUNS_ProductionSchedule_ID(), get_TrxName());	}

	/** Set Production Schedule.
		@param UNS_ProductionSchedule_ID Production Schedule	  */
	public void setUNS_ProductionSchedule_ID (int UNS_ProductionSchedule_ID)
	{
		if (UNS_ProductionSchedule_ID < 1) 
			set_Value (COLUMNNAME_UNS_ProductionSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_ProductionSchedule_ID, Integer.valueOf(UNS_ProductionSchedule_ID));
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
}