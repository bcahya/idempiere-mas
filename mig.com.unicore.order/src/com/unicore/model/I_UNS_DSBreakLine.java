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

/** Generated Interface for UNS_DSBreakLine
 *  @author iDempiere (generated) 
 *  @version Release 2.1
 */
@SuppressWarnings("all")
public interface I_UNS_DSBreakLine 
{

    /** TableName=UNS_DSBreakLine */
    public static final String Table_Name = "UNS_DSBreakLine";

    /** AD_Table_ID=1000020 */
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

    /** Column name BreakDiscount */
    public static final String COLUMNNAME_BreakDiscount = "BreakDiscount";

	/** Set Discount Value.
	  * Trade discount in Percent for the Discount Type "Percent", or trade discount in amount or quantity for the Discount Type "Flat Value" or "Flat Product".
	  */
	public void setBreakDiscount (BigDecimal BreakDiscount);

	/** Get Discount Value.
	  * Trade discount in Percent for the Discount Type "Percent", or trade discount in amount or quantity for the Discount Type "Flat Value" or "Flat Product".
	  */
	public BigDecimal getBreakDiscount();

    /** Column name BreakValue */
    public static final String COLUMNNAME_BreakValue = "BreakValue";

	/** Set Break Value.
	  * Low Value of trade discount break level
	  */
	public void setBreakValue (BigDecimal BreakValue);

	/** Get Break Value.
	  * Low Value of trade discount break level
	  */
	public BigDecimal getBreakValue();

    /** Column name BreakValueTo */
    public static final String COLUMNNAME_BreakValueTo = "BreakValueTo";

	/** Set Break Value To.
	  * Low Value of trade discount break level
	  */
	public void setBreakValueTo (BigDecimal BreakValueTo);

	/** Get Break Value To.
	  * Low Value of trade discount break level
	  */
	public BigDecimal getBreakValueTo();

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

	public com.unicore.model.I_M_DiscountSchemaBreak getM_DiscountSchemaBreak() throws RuntimeException;

    /** Column name MovementQty */
    public static final String COLUMNNAME_MovementQty = "MovementQty";

	/** Set Movement Quantity.
	  * Quantity of a product moved.
	  */
	public void setMovementQty (BigDecimal MovementQty);

	/** Get Movement Quantity.
	  * Quantity of a product moved.
	  */
	public BigDecimal getMovementQty();

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

    /** Column name NofMultiples */
    public static final String COLUMNNAME_NofMultiples = "NofMultiples";

	/** Set Number of Multiples	  */
	public void setNofMultiples (BigDecimal NofMultiples);

	/** Get Number of Multiples	  */
	public BigDecimal getNofMultiples();

    /** Column name PBonus1_ID */
    public static final String COLUMNNAME_PBonus1_ID = "PBonus1_ID";

	/** Set 1st Product Bonus	  */
	public void setPBonus1_ID (int PBonus1_ID);

	/** Get 1st Product Bonus	  */
	public int getPBonus1_ID();

	public org.compiere.model.I_M_Product getPBonus1() throws RuntimeException;

    /** Column name PBonus2_ID */
    public static final String COLUMNNAME_PBonus2_ID = "PBonus2_ID";

	/** Set 2nd Product Bonus	  */
	public void setPBonus2_ID (int PBonus2_ID);

	/** Get 2nd Product Bonus	  */
	public int getPBonus2_ID();

	public org.compiere.model.I_M_Product getPBonus2() throws RuntimeException;

    /** Column name PBonus3_ID */
    public static final String COLUMNNAME_PBonus3_ID = "PBonus3_ID";

	/** Set 3rd Product Bonus	  */
	public void setPBonus3_ID (int PBonus3_ID);

	/** Get 3rd Product Bonus	  */
	public int getPBonus3_ID();

	public org.compiere.model.I_M_Product getPBonus3() throws RuntimeException;

    /** Column name PBonus4_ID */
    public static final String COLUMNNAME_PBonus4_ID = "PBonus4_ID";

	/** Set 4th Product Bonus	  */
	public void setPBonus4_ID (int PBonus4_ID);

	/** Get 4th Product Bonus	  */
	public int getPBonus4_ID();

	public org.compiere.model.I_M_Product getPBonus4() throws RuntimeException;

    /** Column name PBonus5_ID */
    public static final String COLUMNNAME_PBonus5_ID = "PBonus5_ID";

	/** Set 5th Product Bonus	  */
	public void setPBonus5_ID (int PBonus5_ID);

	/** Get 5th Product Bonus	  */
	public int getPBonus5_ID();

	public org.compiere.model.I_M_Product getPBonus5() throws RuntimeException;

    /** Column name PQty1_ID */
    public static final String COLUMNNAME_PQty1_ID = "PQty1_ID";

	/** Set 1st Product Qty	  */
	public void setPQty1_ID (BigDecimal PQty1_ID);

	/** Get 1st Product Qty	  */
	public BigDecimal getPQty1_ID();

    /** Column name PQty2_ID */
    public static final String COLUMNNAME_PQty2_ID = "PQty2_ID";

	/** Set 2nd Product Qty	  */
	public void setPQty2_ID (BigDecimal PQty2_ID);

	/** Get 2nd Product Qty	  */
	public BigDecimal getPQty2_ID();

    /** Column name PQty3_ID */
    public static final String COLUMNNAME_PQty3_ID = "PQty3_ID";

	/** Set 3rd Product Qty	  */
	public void setPQty3_ID (BigDecimal PQty3_ID);

	/** Get 3rd Product Qty	  */
	public BigDecimal getPQty3_ID();

    /** Column name PQty4_ID */
    public static final String COLUMNNAME_PQty4_ID = "PQty4_ID";

	/** Set 4th Product Qty	  */
	public void setPQty4_ID (BigDecimal PQty4_ID);

	/** Get 4th Product Qty	  */
	public BigDecimal getPQty4_ID();

    /** Column name PQty5_ID */
    public static final String COLUMNNAME_PQty5_ID = "PQty5_ID";

	/** Set 5th Product Qty	  */
	public void setPQty5_ID (BigDecimal PQty5_ID);

	/** Get 5th Product Qty	  */
	public BigDecimal getPQty5_ID();

    /** Column name QtyAllocated */
    public static final String COLUMNNAME_QtyAllocated = "QtyAllocated";

	/** Set Qty Allocated	  */
	public void setQtyAllocated (BigDecimal QtyAllocated);

	/** Get Qty Allocated	  */
	public BigDecimal getQtyAllocated();

    /** Column name QtyInvoiced */
    public static final String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/** Set Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced);

	/** Get Quantity Invoiced.
	  * Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced();

    /** Column name QtyReserved */
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";

	/** Set Reserved Quantity.
	  * Reserved Quantity
	  */
	public void setQtyReserved (BigDecimal QtyReserved);

	/** Get Reserved Quantity.
	  * Reserved Quantity
	  */
	public BigDecimal getQtyReserved();

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

    /** Column name UNS_DSBreakLine_ID */
    public static final String COLUMNNAME_UNS_DSBreakLine_ID = "UNS_DSBreakLine_ID";

	/** Set Discount Break Line	  */
	public void setUNS_DSBreakLine_ID (int UNS_DSBreakLine_ID);

	/** Get Discount Break Line	  */
	public int getUNS_DSBreakLine_ID();

    /** Column name UNS_DSBreakLine_UU */
    public static final String COLUMNNAME_UNS_DSBreakLine_UU = "UNS_DSBreakLine_UU";

	/** Set UNS_DSBreakLine_UU	  */
	public void setUNS_DSBreakLine_UU (String UNS_DSBreakLine_UU);

	/** Get UNS_DSBreakLine_UU	  */
	public String getUNS_DSBreakLine_UU();

    /** Column name UnallocatedBudget */
    public static final String COLUMNNAME_UnallocatedBudget = "UnallocatedBudget";

	/** Set Unallocated Budget	  */
	public void setUnallocatedBudget (BigDecimal UnallocatedBudget);

	/** Get Unallocated Budget	  */
	public BigDecimal getUnallocatedBudget();

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
