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
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for UNS_DiscountTrx
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_DiscountTrx 
{

    /** TableName=UNS_DiscountTrx */
    public static final String Table_Name = "UNS_DiscountTrx";

    /** AD_Table_ID=1000021 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Company.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Department.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Department.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name BonusesPrice */
    public static final String COLUMNNAME_BonusesPrice = "BonusesPrice";

	/** Set BonusesPrice	  */
	public void setBonusesPrice (BigDecimal BonusesPrice);

	/** Get BonusesPrice	  */
	public BigDecimal getBonusesPrice();

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/** Set Invoice Line.
	  * Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/** Get Invoice Line.
	  * Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID();

	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException;

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException;

    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/** Set Sales Order Line.
	  * Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/** Get Sales Order Line.
	  * Sales Order Line
	  */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DiscountedAmt */
    public static final String COLUMNNAME_DiscountedAmt = "DiscountedAmt";

	/** Set Discounted Amt	  */
	public void setDiscountedAmt (BigDecimal DiscountedAmt);

	/** Get Discounted Amt	  */
	public BigDecimal getDiscountedAmt();

    /** Column name DiscountType */
    public static final String COLUMNNAME_DiscountType = "DiscountType";

	/** Set Discount Type.
	  * Type of trade discount calculation
	  */
	public void setDiscountType (String DiscountType);

	/** Get Discount Type.
	  * Type of trade discount calculation
	  */
	public String getDiscountType();

    /** Column name DiscountValue1st */
    public static final String COLUMNNAME_DiscountValue1st = "DiscountValue1st";

	/** Set DiscountValue1st	  */
	public void setDiscountValue1st (BigDecimal DiscountValue1st);

	/** Get DiscountValue1st	  */
	public BigDecimal getDiscountValue1st();

    /** Column name DiscountValue2nd */
    public static final String COLUMNNAME_DiscountValue2nd = "DiscountValue2nd";

	/** Set DiscountValue2nd	  */
	public void setDiscountValue2nd (BigDecimal DiscountValue2nd);

	/** Get DiscountValue2nd	  */
	public BigDecimal getDiscountValue2nd();

    /** Column name DiscountValue3rd */
    public static final String COLUMNNAME_DiscountValue3rd = "DiscountValue3rd";

	/** Set DiscountValue3rd	  */
	public void setDiscountValue3rd (BigDecimal DiscountValue3rd);

	/** Get DiscountValue3rd	  */
	public BigDecimal getDiscountValue3rd();

    /** Column name DiscountValue4th */
    public static final String COLUMNNAME_DiscountValue4th = "DiscountValue4th";

	/** Set DiscountValue4th	  */
	public void setDiscountValue4th (BigDecimal DiscountValue4th);

	/** Get DiscountValue4th	  */
	public BigDecimal getDiscountValue4th();

    /** Column name DiscountValue5th */
    public static final String COLUMNNAME_DiscountValue5th = "DiscountValue5th";

	/** Set DiscountValue5th	  */
	public void setDiscountValue5th (BigDecimal DiscountValue5th);

	/** Get DiscountValue5th	  */
	public BigDecimal getDiscountValue5th();

    /** Column name FifthDiscount */
    public static final String COLUMNNAME_FifthDiscount = "FifthDiscount";

	/** Set 5th Discount %.
	  * Fifth discount percentage
	  */
	public void setFifthDiscount (BigDecimal FifthDiscount);

	/** Get 5th Discount %.
	  * Fifth discount percentage
	  */
	public BigDecimal getFifthDiscount();

    /** Column name FirstDiscount */
    public static final String COLUMNNAME_FirstDiscount = "FirstDiscount";

	/** Set 1st Discount %	  */
	public void setFirstDiscount (BigDecimal FirstDiscount);

	/** Get 1st Discount %	  */
	public BigDecimal getFirstDiscount();

    /** Column name FlatPercentDiscount */
    public static final String COLUMNNAME_FlatPercentDiscount = "FlatPercentDiscount";

	/** Set Flat Discount %	  */
	public void setFlatPercentDiscount (BigDecimal FlatPercentDiscount);

	/** Get Flat Discount %	  */
	public BigDecimal getFlatPercentDiscount();

    /** Column name FlatValueDiscount */
    public static final String COLUMNNAME_FlatValueDiscount = "FlatValueDiscount";

	/** Set Flat Value Discount	  */
	public void setFlatValueDiscount (BigDecimal FlatValueDiscount);

	/** Get Flat Value Discount	  */
	public BigDecimal getFlatValueDiscount();

    /** Column name FourthDiscount */
    public static final String COLUMNNAME_FourthDiscount = "FourthDiscount";

	/** Set 4th Discount %.
	  * Fourth discount percentage
	  */
	public void setFourthDiscount (BigDecimal FourthDiscount);

	/** Get 4th Discount %.
	  * Fourth discount percentage
	  */
	public BigDecimal getFourthDiscount();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsChangedByUser */
    public static final String COLUMNNAME_IsChangedByUser = "IsChangedByUser";

	/** Set Changed By User	  */
	public void setIsChangedByUser (boolean IsChangedByUser);

	/** Get Changed By User	  */
	public boolean isChangedByUser();

    /** Column name IsNeedRecalculate */
    public static final String COLUMNNAME_IsNeedRecalculate = "IsNeedRecalculate";

	/** Set Need Recalculate	  */
	public void setIsNeedRecalculate (boolean IsNeedRecalculate);

	/** Get Need Recalculate	  */
	public boolean isNeedRecalculate();

    /** Column name isReCheck */
    public static final String COLUMNNAME_isReCheck = "isReCheck";

	/** Set Re Check	  */
	public void setisReCheck (boolean isReCheck);

	/** Get Re Check	  */
	public boolean isReCheck();

    /** Column name M_DiscountSchema_ID */
    public static final String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/** Set Discount Schema.
	  * Schema to calculate the trade discount percentage
	  */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/** Get Discount Schema.
	  * Schema to calculate the trade discount percentage
	  */
	public int getM_DiscountSchema_ID();

    /** Column name M_DiscountSchemaBreak_ID */
    public static final String COLUMNNAME_M_DiscountSchemaBreak_ID = "M_DiscountSchemaBreak_ID";

	/** Set Discount Schema Break.
	  * Trade Discount Break
	  */
	public void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID);

	/** Get Discount Schema Break.
	  * Trade Discount Break
	  */
	public int getM_DiscountSchemaBreak_ID();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name ProductBonus_ID */
    public static final String COLUMNNAME_ProductBonus_ID = "ProductBonus_ID";

	/** Set Product Bonus	  */
	public void setProductBonus_ID (int ProductBonus_ID);

	/** Get Product Bonus	  */
	public int getProductBonus_ID();

	public org.compiere.model.I_M_Product getProductBonus() throws RuntimeException;

    /** Column name QtyBonuses */
    public static final String COLUMNNAME_QtyBonuses = "QtyBonuses";

	/** Set Bonuses Qty.
	  * Bonuses Qty
	  */
	public void setQtyBonuses (BigDecimal QtyBonuses);

	/** Get Bonuses Qty.
	  * Bonuses Qty
	  */
	public BigDecimal getQtyBonuses();

    /** Column name QtyValDiscounted */
    public static final String COLUMNNAME_QtyValDiscounted = "QtyValDiscounted";

	/** Set Qty Or Value Discounted	  */
	public void setQtyValDiscounted (BigDecimal QtyValDiscounted);

	/** Get Qty Or Value Discounted	  */
	public BigDecimal getQtyValDiscounted();

    /** Column name SecondDiscount */
    public static final String COLUMNNAME_SecondDiscount = "SecondDiscount";

	/** Set 2nd Discount %.
	  * Second discount percentage
	  */
	public void setSecondDiscount (BigDecimal SecondDiscount);

	/** Get 2nd Discount %.
	  * Second discount percentage
	  */
	public BigDecimal getSecondDiscount();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name ThirdDiscount */
    public static final String COLUMNNAME_ThirdDiscount = "ThirdDiscount";

	/** Set 3rd Discount %.
	  * Third discount percentage
	  */
	public void setThirdDiscount (BigDecimal ThirdDiscount);

	/** Get 3rd Discount %.
	  * Third discount percentage
	  */
	public BigDecimal getThirdDiscount();

    /** Column name UNS_BonusClaimLine_ID */
    public static final String COLUMNNAME_UNS_BonusClaimLine_ID = "UNS_BonusClaimLine_ID";

	/** Set Bonus Claim Line	  */
	public void setUNS_BonusClaimLine_ID (int UNS_BonusClaimLine_ID);

	/** Get Bonus Claim Line	  */
	public int getUNS_BonusClaimLine_ID();

    /** Column name UNS_DiscountBonus_ID */
    public static final String COLUMNNAME_UNS_DiscountBonus_ID = "UNS_DiscountBonus_ID";

	/** Set Discount Bonus	  */
	public void setUNS_DiscountBonus_ID (int UNS_DiscountBonus_ID);

	/** Get Discount Bonus	  */
	public int getUNS_DiscountBonus_ID();

    /** Column name UNS_DiscountTrx_ID */
    public static final String COLUMNNAME_UNS_DiscountTrx_ID = "UNS_DiscountTrx_ID";

	/** Set Discount Bonus Transcation	  */
	public void setUNS_DiscountTrx_ID (int UNS_DiscountTrx_ID);

	/** Get Discount Bonus Transcation	  */
	public int getUNS_DiscountTrx_ID();

    /** Column name UNS_DiscountTrx_UU */
    public static final String COLUMNNAME_UNS_DiscountTrx_UU = "UNS_DiscountTrx_UU";

	/** Set UNS_DiscountTrx_UU	  */
	public void setUNS_DiscountTrx_UU (String UNS_DiscountTrx_UU);

	/** Get UNS_DiscountTrx_UU	  */
	public String getUNS_DiscountTrx_UU();

    /** Column name UNS_DSBreakLine_ID */
    public static final String COLUMNNAME_UNS_DSBreakLine_ID = "UNS_DSBreakLine_ID";

	/** Set Discount Break Line	  */
	public void setUNS_DSBreakLine_ID (int UNS_DSBreakLine_ID);

	/** Get Discount Break Line	  */
	public int getUNS_DSBreakLine_ID();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
