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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for UNS_MP1Form_Detail
 *  @author iDempiere (generated) 
 *  @version Release 1.0a - $Id$ */
public class X_UNS_MP1Form_Detail extends PO implements I_UNS_MP1Form_Detail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20140212L;

    /** Standard Constructor */
    public X_UNS_MP1Form_Detail (Properties ctx, int UNS_MP1Form_Detail_ID, String trxName)
    {
      super (ctx, UNS_MP1Form_Detail_ID, trxName);
      /** if (UNS_MP1Form_Detail_ID == 0)
        {
			setNomer (0);
			setProductionQty (Env.ZERO);
// 0
			setUNS_MP1Form_Detail_ID (0);
			setUNS_MP1Form_ID (0);
			setUNS_Resource_ID (0);
        } */
    }

    /** Load Constructor */
    public X_UNS_MP1Form_Detail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_MP1Form_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set 1st Add.
		@param Add1 1st Add	  */
	public void setAdd1 (BigDecimal Add1)
	{
		set_Value (COLUMNNAME_Add1, Add1);
	}

	/** Get 1st Add.
		@return 1st Add	  */
	public BigDecimal getAdd1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Add1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 2nd Add.
		@param Add2 2nd Add	  */
	public void setAdd2 (BigDecimal Add2)
	{
		set_Value (COLUMNNAME_Add2, Add2);
	}

	/** Get 2nd Add.
		@return 2nd Add	  */
	public BigDecimal getAdd2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Add2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 3rd Add.
		@param Add3 3rd Add	  */
	public void setAdd3 (BigDecimal Add3)
	{
		set_Value (COLUMNNAME_Add3, Add3);
	}

	/** Get 3rd Add.
		@return 3rd Add	  */
	public BigDecimal getAdd3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Add3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 4th Add.
		@param Add4 4th Add	  */
	public void setAdd4 (BigDecimal Add4)
	{
		set_Value (COLUMNNAME_Add4, Add4);
	}

	/** Get 4th Add.
		@return 4th Add	  */
	public BigDecimal getAdd4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Add4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set 5th Add.
		@param Add5 5th Add	  */
	public void setAdd5 (BigDecimal Add5)
	{
		set_Value (COLUMNNAME_Add5, Add5);
	}

	/** Get 5th Add.
		@return 5th Add	  */
	public BigDecimal getAdd5 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Add5);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Additional Quantity.
		@param AdditionalQty Additional Quantity	  */
	public void setAdditionalQty (BigDecimal AdditionalQty)
	{
		set_Value (COLUMNNAME_AdditionalQty, AdditionalQty);
	}

	/** Get Additional Quantity.
		@return Additional Quantity	  */
	public BigDecimal getAdditionalQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AdditionalQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bad Meat.
		@param BadMeat Bad Meat	  */
	public void setBadMeat (BigDecimal BadMeat)
	{
		set_Value (COLUMNNAME_BadMeat, BadMeat);
	}

	/** Get Bad Meat.
		@return Bad Meat	  */
	public BigDecimal getBadMeat () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BadMeat);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TMPG Extra.
		@param CCSExtra TMPG Extra	  */
	public void setCCSExtra (BigDecimal CCSExtra)
	{
		set_Value (COLUMNNAME_CCSExtra, CCSExtra);
	}

	/** Get TMPG Extra.
		@return TMPG Extra	  */
	public BigDecimal getCCSExtra () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CCSExtra);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Coconut Shell.
		@param CoconutShell Coconut Shell	  */
	public void setCoconutShell (BigDecimal CoconutShell)
	{
		set_Value (COLUMNNAME_CoconutShell, CoconutShell);
	}

	/** Get Coconut Shell.
		@return Coconut Shell	  */
	public BigDecimal getCoconutShell () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CoconutShell);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Coconut Water BS.
		@param CoconutWaterBS Coconut Water BS	  */
	public void setCoconutWaterBS (BigDecimal CoconutWaterBS)
	{
		set_Value (COLUMNNAME_CoconutWaterBS, CoconutWaterBS);
	}

	/** Get Coconut Water BS.
		@return Coconut Water BS	  */
	public BigDecimal getCoconutWaterBS () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CoconutWaterBS);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** EmploymentStatus AD_Reference_ID=1000062 */
	public static final int EMPLOYMENTSTATUS_AD_Reference_ID=1000062;
	/** Borongan CV = 01 */
	public static final String EMPLOYMENTSTATUS_BoronganCV = "01";
	/** Training CV = 02 */
	public static final String EMPLOYMENTSTATUS_TrainingCV = "02";
	/** Harian CV = 03 */
	public static final String EMPLOYMENTSTATUS_HarianCV = "03";
	/** Harian = 04 */
	public static final String EMPLOYMENTSTATUS_Harian = "04";
	/** Bulanan = 05 */
	public static final String EMPLOYMENTSTATUS_Bulanan = "05";
	/** Contract = 06 */
	public static final String EMPLOYMENTSTATUS_Contract = "06";
	/** Set Employment Status.
		@param EmploymentStatus Employment Status	  */
	public void setEmploymentStatus (String EmploymentStatus)
	{

		set_Value (COLUMNNAME_EmploymentStatus, EmploymentStatus);
	}

	/** Get Employment Status.
		@return Employment Status	  */
	public String getEmploymentStatus () 
	{
		return (String)get_Value(COLUMNNAME_EmploymentStatus);
	}

	/** Set Ending Stock.
		@param EndingStock Ending Stock	  */
	public void setEndingStock (BigDecimal EndingStock)
	{
		set_Value (COLUMNNAME_EndingStock, EndingStock);
	}

	/** Get Ending Stock.
		@return Ending Stock	  */
	public BigDecimal getEndingStock () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EndingStock);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Fill In.
		@param FillIn Fill In	  */
	public void setFillIn (BigDecimal FillIn)
	{
		set_Value (COLUMNNAME_FillIn, FillIn);
	}

	/** Get Fill In.
		@return Fill In	  */
	public BigDecimal getFillIn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FillIn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set K Cungkil.
		@param KC K Cungkil	  */
	public void setKC (BigDecimal KC)
	{
		set_Value (COLUMNNAME_KC, KC);
	}

	/** Get K Cungkil.
		@return K Cungkil	  */
	public BigDecimal getKC () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KC);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set KC Extra.
		@param KCExtra KC Extra	  */
	public void setKCExtra (BigDecimal KCExtra)
	{
		set_Value (COLUMNNAME_KCExtra, KCExtra);
	}

	/** Get KC Extra.
		@return KC Extra	  */
	public BigDecimal getKCExtra () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_KCExtra);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Last Stock.
		@param LastStock Last Stock	  */
	public void setLastStock (BigDecimal LastStock)
	{
		set_Value (COLUMNNAME_LastStock, LastStock);
	}

	/** Get Last Stock.
		@return Last Stock	  */
	public BigDecimal getLastStock () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LastStock);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public org.compiere.model.I_M_MovementLine getM_MovementLine() throws RuntimeException
    {
		return (org.compiere.model.I_M_MovementLine)MTable.get(getCtx(), org.compiere.model.I_M_MovementLine.Table_Name)
			.getPO(getM_MovementLine_ID(), get_TrxName());	}

	/** Set Move Line.
		@param M_MovementLine_ID 
		Inventory Move document Line
	  */
	public void setM_MovementLine_ID (int M_MovementLine_ID)
	{
		if (M_MovementLine_ID < 1) 
			set_Value (COLUMNNAME_M_MovementLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_MovementLine_ID, Integer.valueOf(M_MovementLine_ID));
	}

	/** Get Move Line.
		@return Inventory Move document Line
	  */
	public int getM_MovementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_MovementLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Number.
		@param Nomer Number	  */
	public void setNomer (int Nomer)
	{
		set_Value (COLUMNNAME_Nomer, Integer.valueOf(Nomer));
	}

	/** Get Number.
		@return Number	  */
	public int getNomer () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Nomer);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Overtime Production.
		@param OVT_Production_ID Overtime Production	  */
	public void setOVT_Production_ID (int OVT_Production_ID)
	{
		if (OVT_Production_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_OVT_Production_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_OVT_Production_ID, Integer.valueOf(OVT_Production_ID));
	}

	/** Get Overtime Production.
		@return Overtime Production	  */
	public int getOVT_Production_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OVT_Production_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Production Qty.
		@param ProductionQty 
		Quantity of products to produce
	  */
	public void setProductionQty (BigDecimal ProductionQty)
	{
		set_Value (COLUMNNAME_ProductionQty, ProductionQty);
	}

	/** Get Production Qty.
		@return Quantity of products to produce
	  */
	public BigDecimal getProductionQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProductionQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Raw Meat.
		@param RawMeat Raw Meat	  */
	public void setRawMeat (BigDecimal RawMeat)
	{
		set_Value (COLUMNNAME_RawMeat, RawMeat);
	}

	/** Get Raw Meat.
		@return Raw Meat	  */
	public BigDecimal getRawMeat () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RawMeat);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Skin Meat.
		@param SkinMeat Skin Meat	  */
	public void setSkinMeat (BigDecimal SkinMeat)
	{
		set_Value (COLUMNNAME_SkinMeat, SkinMeat);
	}

	/** Get Skin Meat.
		@return Skin Meat	  */
	public BigDecimal getSkinMeat () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SkinMeat);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Starting Stock.
		@param StartingStock Starting Stock	  */
	public void setStartingStock (BigDecimal StartingStock)
	{
		set_Value (COLUMNNAME_StartingStock, StartingStock);
	}

	/** Get Starting Stock.
		@return Starting Stock	  */
	public BigDecimal getStartingStock () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_StartingStock);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set MP1 Detail.
		@param UNS_MP1Form_Detail_ID MP1 Detail	  */
	public void setUNS_MP1Form_Detail_ID (int UNS_MP1Form_Detail_ID)
	{
		if (UNS_MP1Form_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_MP1Form_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_MP1Form_Detail_ID, Integer.valueOf(UNS_MP1Form_Detail_ID));
	}

	/** Get MP1 Detail.
		@return MP1 Detail	  */
	public int getUNS_MP1Form_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_MP1Form_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_MP1Form_Detail_UU.
		@param UNS_MP1Form_Detail_UU UNS_MP1Form_Detail_UU	  */
	public void setUNS_MP1Form_Detail_UU (String UNS_MP1Form_Detail_UU)
	{
		set_Value (COLUMNNAME_UNS_MP1Form_Detail_UU, UNS_MP1Form_Detail_UU);
	}

	/** Get UNS_MP1Form_Detail_UU.
		@return UNS_MP1Form_Detail_UU	  */
	public String getUNS_MP1Form_Detail_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_MP1Form_Detail_UU);
	}

	public com.uns.model.I_UNS_MP1Form getUNS_MP1Form() throws RuntimeException
    {
		return (com.uns.model.I_UNS_MP1Form)MTable.get(getCtx(), com.uns.model.I_UNS_MP1Form.Table_Name)
			.getPO(getUNS_MP1Form_ID(), get_TrxName());	}

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

	/** Set Parrer-1.
		@param UNS_Parrer1_ID Parrer-1	  */
	public void setUNS_Parrer1_ID (int UNS_Parrer1_ID)
	{
		if (UNS_Parrer1_ID < 1) 
			set_Value (COLUMNNAME_UNS_Parrer1_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Parrer1_ID, Integer.valueOf(UNS_Parrer1_ID));
	}

	/** Get Parrer-1.
		@return Parrer-1	  */
	public int getUNS_Parrer1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Parrer1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Parrer-2.
		@param UNS_Parrer2_ID Parrer-2	  */
	public void setUNS_Parrer2_ID (int UNS_Parrer2_ID)
	{
		if (UNS_Parrer2_ID < 1) 
			set_Value (COLUMNNAME_UNS_Parrer2_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Parrer2_ID, Integer.valueOf(UNS_Parrer2_ID));
	}

	/** Get Parrer-2.
		@return Parrer-2	  */
	public int getUNS_Parrer2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Parrer2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Parrer-3.
		@param UNS_Parrer3_ID Parrer-3	  */
	public void setUNS_Parrer3_ID (int UNS_Parrer3_ID)
	{
		if (UNS_Parrer3_ID < 1) 
			set_Value (COLUMNNAME_UNS_Parrer3_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Parrer3_ID, Integer.valueOf(UNS_Parrer3_ID));
	}

	/** Get Parrer-3.
		@return Parrer-3	  */
	public int getUNS_Parrer3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Parrer3_ID);
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

	/** Set Sheller.
		@param UNS_Sheller_ID Sheller	  */
	public void setUNS_Sheller_ID (int UNS_Sheller_ID)
	{
		if (UNS_Sheller_ID < 1) 
			set_Value (COLUMNNAME_UNS_Sheller_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_Sheller_ID, Integer.valueOf(UNS_Sheller_ID));
	}

	/** Get Sheller.
		@return Sheller	  */
	public int getUNS_Sheller_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Sheller_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set White Meat BW.
		@param WhiteMeatBW White Meat BW	  */
	public void setWhiteMeatBW (BigDecimal WhiteMeatBW)
	{
		set_Value (COLUMNNAME_WhiteMeatBW, WhiteMeatBW);
	}

	/** Get White Meat BW.
		@return White Meat BW	  */
	public BigDecimal getWhiteMeatBW () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WhiteMeatBW);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}