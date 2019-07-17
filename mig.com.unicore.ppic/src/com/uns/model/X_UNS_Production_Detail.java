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
import org.compiere.util.KeyNamePair;

/** Generated Model for UNS_Production_Detail
 *  @author iDempiere (generated) 
 *  @version Release 2.1 - $Id$ */
public class X_UNS_Production_Detail extends PO implements I_UNS_Production_Detail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180216L;

    /** Standard Constructor */
    public X_UNS_Production_Detail (Properties ctx, int UNS_Production_Detail_ID, String trxName)
    {
      super (ctx, UNS_Production_Detail_ID, trxName);
      /** if (UNS_Production_Detail_ID == 0)
        {
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM UNS_Production_Detail WHERE UNS_Production_ID=@UNS_Production_ID@
			setM_Locator_ID (0);
// @M_Locator_ID@
			setM_Product_ID (0);
			setMovementQty (Env.ZERO);
// 0
			setProcessed (false);
			setUNS_Production_Detail_ID (0);
			setUNS_Production_ID (0);
			setUOMMovementQtyL1 (Env.ZERO);
// 0
			setUOMMovementQtyL2 (Env.ZERO);
// 0
			setUOMMovementQtyL3 (Env.ZERO);
// 0
			setUOMMovementQtyL4 (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_UNS_Production_Detail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_UNS_Production_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set End Product.
		@param IsEndProduct 
		End Product of production
	  */
	public void setIsEndProduct (boolean IsEndProduct)
	{
		set_Value (COLUMNNAME_IsEndProduct, Boolean.valueOf(IsEndProduct));
	}

	/** Get End Product.
		@return End Product of production
	  */
	public boolean isEndProduct () 
	{
		Object oo = get_Value(COLUMNNAME_IsEndProduct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Primary.
		@param IsPrimary 
		Indicates if this is the primary budget
	  */
	public void setIsPrimary (boolean IsPrimary)
	{
		set_Value (COLUMNNAME_IsPrimary, Boolean.valueOf(IsPrimary));
	}

	/** Get Primary.
		@return Indicates if this is the primary budget
	  */
	public boolean isPrimary () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrimary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
    {
		return (I_M_AttributeSetInstance)MTable.get(getCtx(), I_M_AttributeSetInstance.Table_Name)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.compiere.model.I_M_Product_BOM getM_Product_BOM() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product_BOM)MTable.get(getCtx(), org.compiere.model.I_M_Product_BOM.Table_Name)
			.getPO(getM_Product_BOM_ID(), get_TrxName());	}

	/** Set BOM Line.
		@param M_Product_BOM_ID BOM Line	  */
	public void setM_Product_BOM_ID (int M_Product_BOM_ID)
	{
		if (M_Product_BOM_ID < 1) 
			set_Value (COLUMNNAME_M_Product_BOM_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_BOM_ID, Integer.valueOf(M_Product_BOM_ID));
	}

	/** Get BOM Line.
		@return BOM Line	  */
	public int getM_Product_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_BOM_ID);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getM_Product_ID()));
    }

	/** Set Movement Quantity.
		@param MovementQty 
		Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	/** Get Movement Quantity.
		@return Quantity of a product moved.
	  */
	public BigDecimal getMovementQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Planned Quantity.
		@param PlannedQty 
		Planned quantity for this project
	  */
	public void setPlannedQty (BigDecimal PlannedQty)
	{
		set_Value (COLUMNNAME_PlannedQty, PlannedQty);
	}

	/** Get Planned Quantity.
		@return Planned quantity for this project
	  */
	public BigDecimal getPlannedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PlannedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Production_Detail getPrimaryOutput() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Production_Detail)MTable.get(getCtx(), com.uns.model.I_UNS_Production_Detail.Table_Name)
			.getPO(getPrimaryOutput_ID(), get_TrxName());	}

	/** Set Primary Output.
		@param PrimaryOutput_ID 
		The related primary output of this non-primary output.
	  */
	public void setPrimaryOutput_ID (int PrimaryOutput_ID)
	{
		if (PrimaryOutput_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PrimaryOutput_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PrimaryOutput_ID, Integer.valueOf(PrimaryOutput_ID));
	}

	/** Get Primary Output.
		@return The related primary output of this non-primary output.
	  */
	public int getPrimaryOutput_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrimaryOutput_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Available Quantity.
		@param QtyAvailable 
		Available Quantity (On Hand - Reserved)
	  */
	public void setQtyAvailable (BigDecimal QtyAvailable)
	{
		throw new IllegalArgumentException ("QtyAvailable is virtual column");	}

	/** Get Available Quantity.
		@return Available Quantity (On Hand - Reserved)
	  */
	public BigDecimal getQtyAvailable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyAvailable);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Used.
		@param QtyUsed Quantity Used	  */
	public void setQtyUsed (BigDecimal QtyUsed)
	{
		set_Value (COLUMNNAME_QtyUsed, QtyUsed);
	}

	/** Get Quantity Used.
		@return Quantity Used	  */
	public BigDecimal getQtyUsed () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyUsed);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public com.uns.model.I_UNS_Production_Detail getReversalLine() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Production_Detail)MTable.get(getCtx(), com.uns.model.I_UNS_Production_Detail.Table_Name)
			.getPO(getReversalLine_ID(), get_TrxName());	}

	/** Set Reversal Line.
		@param ReversalLine_ID 
		Use to keep the reversal line ID for reversing costing purpose
	  */
	public void setReversalLine_ID (int ReversalLine_ID)
	{
		if (ReversalLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ReversalLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ReversalLine_ID, Integer.valueOf(ReversalLine_ID));
	}

	/** Get Reversal Line.
		@return Use to keep the reversal line ID for reversing costing purpose
	  */
	public int getReversalLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ReversalLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public com.uns.model.I_UNS_Production_Detail getUNS_PD_Reff() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Production_Detail)MTable.get(getCtx(), com.uns.model.I_UNS_Production_Detail.Table_Name)
			.getPO(getUNS_PD_Reff_ID(), get_TrxName());	}

	/** Set Production DetailID2.
		@param UNS_PD_Reff_ID 
		Tricky solution to enable more than 1 included tab in production detail.
	  */
	public void setUNS_PD_Reff_ID (int UNS_PD_Reff_ID)
	{
		if (UNS_PD_Reff_ID < 1) 
			set_Value (COLUMNNAME_UNS_PD_Reff_ID, null);
		else 
			set_Value (COLUMNNAME_UNS_PD_Reff_ID, Integer.valueOf(UNS_PD_Reff_ID));
	}

	/** Get Production DetailID2.
		@return Tricky solution to enable more than 1 included tab in production detail.
	  */
	public int getUNS_PD_Reff_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_PD_Reff_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Production Detail.
		@param UNS_Production_Detail_ID Production Detail	  */
	public void setUNS_Production_Detail_ID (int UNS_Production_Detail_ID)
	{
		if (UNS_Production_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Production_Detail_ID, Integer.valueOf(UNS_Production_Detail_ID));
	}

	/** Get Production Detail.
		@return Production Detail	  */
	public int getUNS_Production_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Production_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UNS_Production_Detail_UU.
		@param UNS_Production_Detail_UU UNS_Production_Detail_UU	  */
	public void setUNS_Production_Detail_UU (String UNS_Production_Detail_UU)
	{
		set_Value (COLUMNNAME_UNS_Production_Detail_UU, UNS_Production_Detail_UU);
	}

	/** Get UNS_Production_Detail_UU.
		@return UNS_Production_Detail_UU	  */
	public String getUNS_Production_Detail_UU () 
	{
		return (String)get_Value(COLUMNNAME_UNS_Production_Detail_UU);
	}

	public com.uns.model.I_UNS_Production getUNS_Production() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Production)MTable.get(getCtx(), com.uns.model.I_UNS_Production.Table_Name)
			.getPO(getUNS_Production_ID(), get_TrxName());	}

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

	public com.uns.model.I_UNS_Resource_InOut getUNS_Resource_InOut() throws RuntimeException
    {
		return (com.uns.model.I_UNS_Resource_InOut)MTable.get(getCtx(), com.uns.model.I_UNS_Resource_InOut.Table_Name)
			.getPO(getUNS_Resource_InOut_ID(), get_TrxName());	}

	/** Set Manufacture Resource InOut.
		@param UNS_Resource_InOut_ID Manufacture Resource InOut	  */
	public void setUNS_Resource_InOut_ID (int UNS_Resource_InOut_ID)
	{
		if (UNS_Resource_InOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_InOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_UNS_Resource_InOut_ID, Integer.valueOf(UNS_Resource_InOut_ID));
	}

	/** Get Manufacture Resource InOut.
		@return Manufacture Resource InOut	  */
	public int getUNS_Resource_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UNS_Resource_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getUOMConversionL1() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMConversionL1_ID(), get_TrxName());	}

	/** Set UOM Conversion L1.
		@param UOMConversionL1_ID 
		The conversion of the product's base UOM to the biggest package (Level 1)
	  */
	public void setUOMConversionL1_ID (int UOMConversionL1_ID)
	{
		if (UOMConversionL1_ID < 1) 
			set_Value (COLUMNNAME_UOMConversionL1_ID, null);
		else 
			set_Value (COLUMNNAME_UOMConversionL1_ID, Integer.valueOf(UOMConversionL1_ID));
	}

	/** Get UOM Conversion L1.
		@return The conversion of the product's base UOM to the biggest package (Level 1)
	  */
	public int getUOMConversionL1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMConversionL1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getUOMConversionL2() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMConversionL2_ID(), get_TrxName());	}

	/** Set UOM Conversion L2.
		@param UOMConversionL2_ID 
		The conversion of the product's base UOM to the number 2 level package (if defined)
	  */
	public void setUOMConversionL2_ID (int UOMConversionL2_ID)
	{
		if (UOMConversionL2_ID < 1) 
			set_Value (COLUMNNAME_UOMConversionL2_ID, null);
		else 
			set_Value (COLUMNNAME_UOMConversionL2_ID, Integer.valueOf(UOMConversionL2_ID));
	}

	/** Get UOM Conversion L2.
		@return The conversion of the product's base UOM to the number 2 level package (if defined)
	  */
	public int getUOMConversionL2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMConversionL2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getUOMConversionL3() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMConversionL3_ID(), get_TrxName());	}

	/** Set UOM Conversion L3.
		@param UOMConversionL3_ID 
		The conversion of the product's base UOM to the number 3 level package (if defined)
	  */
	public void setUOMConversionL3_ID (int UOMConversionL3_ID)
	{
		if (UOMConversionL3_ID < 1) 
			set_Value (COLUMNNAME_UOMConversionL3_ID, null);
		else 
			set_Value (COLUMNNAME_UOMConversionL3_ID, Integer.valueOf(UOMConversionL3_ID));
	}

	/** Get UOM Conversion L3.
		@return The conversion of the product's base UOM to the number 3 level package (if defined)
	  */
	public int getUOMConversionL3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMConversionL3_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_UOM getUOMConversionL4() throws RuntimeException
    {
		return (org.compiere.model.I_C_UOM)MTable.get(getCtx(), org.compiere.model.I_C_UOM.Table_Name)
			.getPO(getUOMConversionL4_ID(), get_TrxName());	}

	/** Set UOM Conversion L4.
		@param UOMConversionL4_ID 
		The conversion of the product's base UOM to the number 4 level package (if defined)
	  */
	public void setUOMConversionL4_ID (int UOMConversionL4_ID)
	{
		if (UOMConversionL4_ID < 1) 
			set_Value (COLUMNNAME_UOMConversionL4_ID, null);
		else 
			set_Value (COLUMNNAME_UOMConversionL4_ID, Integer.valueOf(UOMConversionL4_ID));
	}

	/** Get UOM Conversion L4.
		@return The conversion of the product's base UOM to the number 4 level package (if defined)
	  */
	public int getUOMConversionL4_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UOMConversionL4_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Movement Qty L1.
		@param UOMMovementQtyL1 Movement Qty L1	  */
	public void setUOMMovementQtyL1 (BigDecimal UOMMovementQtyL1)
	{
		set_Value (COLUMNNAME_UOMMovementQtyL1, UOMMovementQtyL1);
	}

	/** Get Movement Qty L1.
		@return Movement Qty L1	  */
	public BigDecimal getUOMMovementQtyL1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMMovementQtyL1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Movement Qty L2.
		@param UOMMovementQtyL2 Movement Qty L2	  */
	public void setUOMMovementQtyL2 (BigDecimal UOMMovementQtyL2)
	{
		set_Value (COLUMNNAME_UOMMovementQtyL2, UOMMovementQtyL2);
	}

	/** Get Movement Qty L2.
		@return Movement Qty L2	  */
	public BigDecimal getUOMMovementQtyL2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMMovementQtyL2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Movement Qty L3.
		@param UOMMovementQtyL3 Movement Qty L3	  */
	public void setUOMMovementQtyL3 (BigDecimal UOMMovementQtyL3)
	{
		set_Value (COLUMNNAME_UOMMovementQtyL3, UOMMovementQtyL3);
	}

	/** Get Movement Qty L3.
		@return Movement Qty L3	  */
	public BigDecimal getUOMMovementQtyL3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMMovementQtyL3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Movement Qty L4.
		@param UOMMovementQtyL4 Movement Qty L4	  */
	public void setUOMMovementQtyL4 (BigDecimal UOMMovementQtyL4)
	{
		set_Value (COLUMNNAME_UOMMovementQtyL4, UOMMovementQtyL4);
	}

	/** Get Movement Qty L4.
		@return Movement Qty L4	  */
	public BigDecimal getUOMMovementQtyL4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UOMMovementQtyL4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}