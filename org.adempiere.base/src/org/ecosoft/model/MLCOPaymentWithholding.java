/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.ecosoft.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.MPriceList;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Invoice Withholding Model
 *	
 *  @author Kitti U. - EcoSoft Co., Ltd.
 *  @version $Id: MLCOPaymentWithholding.java, 2010/12/02 11:44:05 ecosoft Exp $
 */
public class MLCOPaymentWithholding extends X_LCO_PaymentWithholding
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**	Static Logger	*/
	
	//private static CLogger	s_log	= CLogger.getCLogger (MLCOPaymentWithholding.class);

	/**************************************************************************
	 * 	Default Constructor
	 *	@param ctx context
	 *	@param MLCOPaymentWithholding_ID id
	 *	@param trxName transaction
	 */
	public MLCOPaymentWithholding (Properties ctx, int MLCOPaymentWithholding_ID, String trxName)
	{
		super(ctx, MLCOPaymentWithholding_ID, trxName);
	}	//	MLCOPaymentWithholding

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MLCOPaymentWithholding(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MLCOPaymentWithholding

	/**************************************************************************
	 * 	Before Save
	 *	@param newRecord
	 *	@return true if save
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		return true;
	}	//	beforeSave

	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return saved
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		
		/*
		 * Only when user update tax amount on Payment Withholding Tab, 
		 * we want to update the Diff Amount back to Payment Allocation Tab.
		 */

		Properties ctx = getCtx();
		int C_PaymentAllocate_ID = getC_PaymentAllocate_ID();
		int C_Invoice_ID = getC_Invoice_ID();
		String trxName = get_TrxName();
		MPaymentAllocate pa = new MPaymentAllocate(getCtx(), C_PaymentAllocate_ID, trxName);
		
		// If nothing change in Payment Withholding Tab, do nothing
		/*if (get_ValueDifference("TaxBaseAmt") == null || ((BigDecimal)get_ValueDifference("TaxBaseAmt")).equals(Env.ZERO)
				|| get_ValueDifference("TaxAmt") == null || ((BigDecimal)get_ValueDifference("TaxAmt")).equals(Env.ZERO))
			return true;*/

		int pricelist_id = DB.getSQLValue(null, 
				"SELECT M_PriceList_ID FROM C_Invoice WHERE C_Invoice_ID=?", 
				C_Invoice_ID);
		int stdPrecision = MPriceList.getStandardPrecision(ctx, pricelist_id);	

		// 1. Get new Total Withholding Tax Amount for this Payment Allocation
		BigDecimal withholdingAmt = getWithholdingAmt(ctx, C_PaymentAllocate_ID, C_Invoice_ID, trxName);
		
		// 2. Get new Tax Amount (VAT)
		// Tax Amount (VAT) will be adjusted with only the Diff of Withholding Tax Base Amount of invoice line 
		// ** that by itself also have Tax (VAT)
		//BigDecimal oldInvoiceAmt = pa.get_ValueOld("InvoiceAmt") == null ? Env.ZERO : (BigDecimal) pa.get_ValueOld("InvoiceAmt");
		BigDecimal oldTaxBaseAmt = pa.getInvoiceAmt().subtract((BigDecimal)pa.get_Value("TaxAmt")).subtract(pa.getOverUnderAmt());
		BigDecimal avgRate = MLCOPaymentWithholding.getAvgerageTaxRate(ctx, C_PaymentAllocate_ID, C_Invoice_ID, trxName);
		// Only if this PaymentWithholding line also being paid for TaxAmt (VAT)
		// ---> How can we detect???, some Invoice Line with Withholding may not have VAT!!! or VAT with different Rate!!!
		BigDecimal baseTaxDiff = get_ValueDifference("TaxBaseAmt") == null ? Env.ZERO : (BigDecimal)get_ValueDifference("TaxBaseAmt");
		// New Tax Base Amount = (old amount + diff) x average rate
		BigDecimal newTaxBaseAmt = oldTaxBaseAmt.add(baseTaxDiff);
		BigDecimal proRateTaxAmt = avgRate.multiply(newTaxBaseAmt);
		
		// 3. Update OverUnderAmount
		BigDecimal overUnderAmt = pa.getInvoiceAmt().subtract(newTaxBaseAmt).subtract(proRateTaxAmt);
		
		// 4. Update 1 - 3
		return updateWithholdingAmount(C_PaymentAllocate_ID, withholdingAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP), trxName)
				&& updatePayAllocateTaxAmt(C_PaymentAllocate_ID, proRateTaxAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP), trxName)
				&& updateOverUnderAmount(C_PaymentAllocate_ID, overUnderAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP), trxName) 
				&& updatePayAmount(C_PaymentAllocate_ID, withholdingAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP), trxName);
	}	//	afterSave

	/**
	 * 	After Delete
	 *	@param success success
	 *	@return deleted
	 */
	protected boolean afterDelete (boolean success)
	{
		if (!success)
			return success;
		return true;
	}	//	afterDelete
	
	/**
	 *	Get Withholding Amount
	 *	@return withholdingAmt
	 */
	public static BigDecimal getWithholdingAmt(Properties ctx, int C_PaymentAllocate_ID, int C_Invoice_ID, String trxName)
	{
		BigDecimal withholdingAmt = Env.ZERO;
		
		String sql = "SELECT COALESCE(SUM(TaxAmt),0) FROM LCO_PaymentWithholding WHERE IsActive = 'Y' " +
			"AND IsCalcOnPayment = 'Y' AND C_PaymentAllocate_ID =?";

		PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
		ResultSet rs = null;
		
		try {
			pstmt.setInt(1, C_PaymentAllocate_ID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				withholdingAmt = rs.getBigDecimal(1);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return withholdingAmt;
	}	//
	
	/**
	 *	Update Withholding in Payment Allocate
	 *	@return true if updated with withholding
	 */
	public static boolean updateWithholdingAmount(int C_PaymentAllocate_ID, BigDecimal withholdingAmt, String trxName)
	{
		String sql = 
			"UPDATE C_PaymentAllocate "
			+ " SET WithholdingAmt=" + withholdingAmt
			+ " WHERE C_PaymentAllocate_ID=?";
		int no = DB.executeUpdate(sql, C_PaymentAllocate_ID, trxName);

		return no == 1;
	}
	
	/**
	 *	Update pro-rate Tax Amount (VAT)
	 *	@return true if updated with tax amount
	 */
	public static boolean updatePayAllocateTaxAmt(int C_PaymentAllocate_ID, BigDecimal proRateTaxAmt, String trxName)
	{
		String sql = 
			"UPDATE C_PaymentAllocate "
			+ " SET TaxAmt=" + proRateTaxAmt
			+ " WHERE C_PaymentAllocate_ID=?";
		int no = DB.executeUpdate(sql, C_PaymentAllocate_ID, trxName);

		return no == 1;
	}	//
	
	/**
	 *	Update Over Under Amount from total based withholding in Payment Allocate
	 *	@return true if updated with withholding
	 */
	public static boolean updateOverUnderAmount(int C_PaymentAllocate_ID, BigDecimal overUnderAmt, String trxName)
	{
		//	Update Invoice Header
		String sql = 
			"UPDATE C_PaymentAllocate "
			+ " SET OverUnderAmt= " + overUnderAmt
			+ " WHERE C_PaymentAllocate_ID=?";
		int no = DB.executeUpdate(sql, C_PaymentAllocate_ID, trxName);

		return no == 1;
	}	//	
	
	/**
	 *	Update Over Under Amount from total based withholding in Payment Allocate
	 *	@return true if updated with withholding
	 */
	public static boolean updatePayAmount(int C_PaymentAllocate_ID, BigDecimal WithholdingAmt, String trxName)
	{
		String sql = 
			"UPDATE C_PaymentAllocate "
			+ " SET Amount=InvoiceAmt - " + WithholdingAmt + " - WriteoffAmt - OverUnderAmt - DiscountAmt "
			+ "WHERE C_PaymentAllocate_ID=?";
		int no = DB.executeUpdate(sql, C_PaymentAllocate_ID, trxName);

		return no == 1;
	}
	
	/**
	 *	Get Average Tax Rate
	 *	@return avgRate
	 */
	public static BigDecimal getAvgerageTaxRate(Properties ctx, int C_PaymentAllocate_ID, int C_Invoice_ID, String trxName)
	{
		BigDecimal avgRate = Env.ZERO;
		
		String sql = "SELECT TotalTax / TotalLines as AvgRate FROM "
			+ "(SELECT COALESCE(i.TotalLines,0) As TotalLines, COALESCE(SUM(il.TaxAmt),0) as TotalTax "
			+ "From C_Invoice i, C_InvoiceLine il Where il.C_Invoice_ID = i.C_Invoice_ID "
			+ "AND i.C_Invoice_ID = ? "
			+ "AND i.IsActive = 'Y' "
			+ "Group By TotalLines) o";

		PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
		ResultSet rs = null;
		
		try {
			pstmt.setInt(1, C_Invoice_ID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				avgRate = rs.getBigDecimal(1);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return avgRate;
	}
		
}	//