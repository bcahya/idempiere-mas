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
package org.globalqss.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MConversionRate;
import org.compiere.model.MCurrency;
import org.compiere.model.MInvoice;
import org.compiere.model.MPriceList;
import org.compiere.model.MTax;
import org.compiere.util.DB;
import org.compiere.util.Env;

/******************************************************************************
 * Entity Type = ECS_LCO
 * Overwrite Version 9249
 * #1 	ECS_LCO: 	fillWithholding() new call out to replace fillWriteoff
 * #2	ECS_LCO:	amounts_payment(), amounts_allocate(), replace amounts
 ******************************************************************************/

/**
 *	LCO_CalloutWithholding
 *
 *  @author Carlos Ruiz - globalqss - Quality Systems & Solutions - http://globalqss.com 
 *  @version  $Id: LCO_CalloutWithholding.java,v 1.3 2007/05/09 10:43:28 cruiz Exp $
 */
public class LCO_CalloutWithholding extends CalloutEngine
{
	/**
	 *	The string in the Callout field is: 
	 *  <code>org.compiere.model.LCO_CalloutWithholding.fillIsUse</code> 
	 *
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @param oldValue The old value
	 *	@return error message or "" if OK
	 */
	public String fillIsUse (Properties ctx, int WindowNo,
		GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		log.info("");		
		int wht_id = ((Integer) mTab.getValue("LCO_WithholdingType_ID")).intValue();
		
		String sql = "SELECT IsUseBPISIC, IsUseBPTaxPayerType, IsUseBPCity, IsUseOrgISIC, IsUseOrgTaxPayerType, IsUseOrgCity, IsUseWithholdingCategory, IsUseProductTaxCategory "
			           + "FROM LCO_WithholdingRuleConf WHERE LCO_WithholdingType_ID=?";		//	#1

		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, wht_id);
			ResultSet rs = pstmt.executeQuery();
			//
			if (rs.next()) {
				mTab.setValue("IsUseBPISIC", rs.getString("IsUseBPISIC"));
				mTab.setValue("IsUseBPTaxPayerType", rs.getString("IsUseBPTaxPayerType"));
				mTab.setValue("IsUseBPCity", rs.getString("IsUseBPCity"));
				mTab.setValue("IsUseOrgISIC", rs.getString("IsUseOrgISIC"));
				mTab.setValue("IsUseOrgTaxPayerType", rs.getString("IsUseOrgTaxPayerType"));
				mTab.setValue("IsUseOrgCity", rs.getString("IsUseOrgCity"));
				mTab.setValue("IsUseWithholdingCategory", rs.getString("IsUseWithholdingCategory"));
				mTab.setValue("IsUseProductTaxCategory", rs.getString("IsUseProductTaxCategory"));
			} else {
				mTab.setValue("IsUseBPISIC", "N");
				mTab.setValue("IsUseBPTaxPayerType", "N");
				mTab.setValue("IsUseBPCity", "N");
				mTab.setValue("IsUseOrgISIC", "N");
				mTab.setValue("IsUseOrgTaxPayerType", "N");
				mTab.setValue("IsUseOrgCity", "N");
				mTab.setValue("IsUseWithholdingCategory", "N");
				mTab.setValue("IsUseProductTaxCategory", "N");
				log.warning("Rule not configured for withholding type");
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return e.getLocalizedMessage();
		}

		return "";
	}	//	fillIsUse
	
	// Called from LCO_InvoiceWithholding.C_Tax_ID
	public String fillPercentFromTax (Properties ctx, int WindowNo,
			GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		log.info("");
		int taxint = 0;
		BigDecimal percent = null;
		if (value != null)
			taxint = ((Integer) value).intValue();
		else 
			return "";
		
		if (taxint != 0) {
			MTax tax = new MTax(ctx, taxint, null);
			percent = tax.getRate();
		}
		mTab.setValue(MLCOInvoiceWithholding.COLUMNNAME_Percent, percent);

		return recalc_taxamt(ctx, WindowNo, mTab, mField, value, oldValue);
	}	//	fillPercentFromTax

	// Called from LCO_InvoiceWithholding.C_Tax_ID and tax base
	public String recalc_taxamt(Properties ctx, int WindowNo,
			GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		log.info("");
		
		// don't recalculate if callout called from field TaxBaseAmt and didn't change 
		if (mField.getColumnName().equals("TaxBaseAmt") && value != null && oldValue != null) {
			BigDecimal newtaxbaseamt = (BigDecimal) value;
			BigDecimal oldtaxbaseamt = (BigDecimal) oldValue;
			if (newtaxbaseamt.compareTo(oldtaxbaseamt) == 0) {
				// the field hasn't changed, don't recalc
				return "";
			}
		}

		BigDecimal percent = (BigDecimal) mTab.getValue(MLCOInvoiceWithholding.COLUMNNAME_Percent);
		BigDecimal taxbaseamt = (BigDecimal) mTab.getValue(MLCOInvoiceWithholding.COLUMNNAME_TaxBaseAmt);

		BigDecimal taxamt = null;
		if (percent != null && taxbaseamt != null) {
			int pricelist_id = DB.getSQLValue(null, 
					"SELECT M_PriceList_ID FROM C_Invoice WHERE C_Invoice_ID=?", 
					((Integer) mTab.getValue(MLCOInvoiceWithholding.COLUMNNAME_C_Invoice_ID)).intValue());
			taxamt = percent.multiply(taxbaseamt).divide(Env.ONEHUNDRED);
			int stdPrecision = MPriceList.getStandardPrecision(ctx, pricelist_id);
			taxamt = taxamt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP);
		}
		mTab.setValue(MLCOInvoiceWithholding.COLUMNNAME_TaxAmt, taxamt);

		return "";
	}	//	fillPercentFromTax

	// Called from C_Payment.C_Invoice_ID
	public String fillWriteOff (Properties ctx, int WidowNo,
			GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		log.info("");
		if (isCalloutActive())
			return "";
		Integer invInt = (Integer) value;
		int inv_id = 0;
		if (value != null)
			inv_id = invInt.intValue();
		
		String sql = 
				"SELECT NVL(SUM(TaxAmt),0) "
				+ "  FROM LCO_InvoiceWithholding "
				+ " WHERE C_Invoice_ID = ? "
				+ "   AND IsCalcOnPayment = 'Y'"
				+ "   AND Processed = 'N'"
				+ "   AND C_AllocationLine_ID IS NULL"
				+ "   AND IsActive = 'Y'";

		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, inv_id);
			ResultSet rs = pstmt.executeQuery();
			//
			if (rs.next()) {
				mTab.setValue("WriteOffAmt", rs.getBigDecimal(1));
			} else {
				mTab.setValue("WriteOffAmt", Env.ZERO);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return e.getLocalizedMessage();
		}

		return "";
	}	//	fillWriteOff

	// ECS_LCO #1
	public String fillTaxes (Properties ctx, int WindowNo,
		GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		Integer C_Invoice_ID = (Integer)value;
		int C_InvoicePaySchedule_ID = 0;
		
		if (isCalloutActive()		//	assuming it is resetting value
			|| C_Invoice_ID == null || C_Invoice_ID.intValue() == 0)
			return "";
		
		if (isCalloutActive() || value == null)
			return "";

		if (mField.getColumnName().equals("C_Invoice_ID")) 
		{
			// KTU
			if (Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_Invoice_ID") == C_Invoice_ID.intValue()
				&& Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID") != 0)
				C_InvoicePaySchedule_ID = Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID");
			
			int pricelist_id = DB.getSQLValue(null, 
					"SELECT M_PriceList_ID FROM C_Invoice WHERE C_Invoice_ID=?", 
					C_Invoice_ID.intValue());
			int stdPrecision = MPriceList.getStandardPrecision(ctx, pricelist_id);				
			
			BigDecimal PercentOpen = Env.ZERO;
			BigDecimal BaseWithholdingAmt = Env.ZERO;
			BigDecimal WithholdingAmt = Env.ZERO;
			BigDecimal TotalTax = Env.ZERO;
			BigDecimal TaxAmt = Env.ZERO;

			try {
				// Get Invoice Open
				PercentOpen = LCO_MInvoice.getPercentInvoiceOpenAmt(C_Invoice_ID.intValue(), C_InvoicePaySchedule_ID);
				// Calculate for Base Withholding
				BaseWithholdingAmt = LCO_MInvoice.getInvoiceBaseWithholding(C_Invoice_ID);
				// Get Total Invoice Tax
				TotalTax = LCO_MInvoice.getInvoiceBaseTax(C_Invoice_ID.intValue());
				
			} catch (SQLException e) {
				log.log (Level.SEVERE, "", e);
				return e.getLocalizedMessage ();
			}
			
			// 1. Fill Withholding Tax
			WithholdingAmt = BaseWithholdingAmt.multiply(PercentOpen);
			mTab.setValue("WithholdingAmt", (WithholdingAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP)));
			
			// 2. Fill Pro-Rate Invoice Tax
			TaxAmt = TotalTax.multiply(PercentOpen);
			mTab.setValue("TaxAmt", TaxAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP));
		}
		
		return "";
	}	//	fillTotalWithholdingTax
	// ECS_LCO	
	
	// UNTASOFT
	/**
	 * 
	 */
	public String fillCashTaxes (Properties ctx, int WindowNo,
			GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		Integer C_Invoice_ID = (Integer)value;
		int C_InvoicePaySchedule_ID = 0;
		
		if (isCalloutActive()		//	assuming it is resetting value
			|| C_Invoice_ID == null || C_Invoice_ID.intValue() == 0)
			return "";
		
		if (isCalloutActive() || value == null)
			return "";

		if (mField.getColumnName().equals("C_Invoice_ID")) {
			// KTU
			if (Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_Invoice_ID") == C_Invoice_ID.intValue()
				&& Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID") != 0)
				C_InvoicePaySchedule_ID = Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID");
			
			int pricelist_id = DB.getSQLValue(null, 
					"SELECT M_PriceList_ID FROM C_Invoice WHERE C_Invoice_ID=?", 
					C_Invoice_ID.intValue());
			int stdPrecision = MPriceList.getStandardPrecision(ctx, pricelist_id);				
			
			BigDecimal PercentOpen = Env.ZERO;
			BigDecimal BaseWithholdingAmt = Env.ZERO;
			BigDecimal WithholdingAmt = Env.ZERO;
			BigDecimal TotalTax = Env.ZERO;
			BigDecimal TaxAmt = Env.ZERO;

			try {
				// Get Invoice Open
				PercentOpen = LCO_MInvoice.getPercentInvoiceOpenAmt(C_Invoice_ID.intValue(), C_InvoicePaySchedule_ID);
				// Calculate for Base Withholding
				BaseWithholdingAmt = LCO_MInvoice.getInvoiceBaseWithholding(C_Invoice_ID);
				// Get Total Invoice Tax
				TotalTax = LCO_MInvoice.getInvoiceBaseTax(C_Invoice_ID.intValue());
				
			} catch (SQLException e) {
				log.log (Level.SEVERE, "", e);
				return e.getLocalizedMessage ();
			}
			MInvoice inv = new MInvoice(ctx, C_Invoice_ID, null);
			BigDecimal amountSign = Env.ONE;
			if ((inv.isSOTrx() && inv.isCreditMemo()) //Not AR and Not Credit Memo.
					|| (!inv.isSOTrx() && !inv.isCreditMemo()))
				amountSign = amountSign.negate();
			
			// 1. Fill Withholding Tax
			WithholdingAmt = BaseWithholdingAmt.multiply(PercentOpen).multiply(amountSign);
			mTab.setValue("WithholdingAmt", (WithholdingAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP)));
			
			// 2. Fill Pro-Rate Invoice Tax
			TaxAmt = TotalTax.multiply(PercentOpen).negate();
			mTab.setValue("TaxAmt", TaxAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP));
		}
		
		return "";
	}// UNTASOFT	

	// ECS_LCO #2
	/**
	 *  Payment_Amounts.
	 *	Change of:
	 *		- X IsOverUnderPayment -> set OverUnderAmt to 0
	 *		- C_Currency_ID, C_ConvesionRate_ID -> convert all
	 *		- PayAmt, DiscountAmt, WriteOffAmt, OverUnderAmt -> PayAmt
	 *			make sure that add up to InvoiceOpenAmt
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @param oldValue Old Value
	 *  @return null or error message
	 */
	public String amounts_allocate (Properties ctx, int WindowNo, GridTab mTab, GridField mField, 
		Object value, Object oldValue)
	{
		if (isCalloutActive())		//	assuming it is resetting value
			return "";
		//	No Invoice
		
		int C_PaymentAllocate_ID = Env.getContextAsInt(ctx, WindowNo, "C_PaymentAllocate_ID");
		Integer C_Invoice_ID = (Integer)mTab.getValue("C_Invoice_ID");
		if(C_Invoice_ID == null)
			C_Invoice_ID = 0;
		Integer C_Order_ID = (Integer)mTab.getValue("C_Order_ID");
		if(C_Order_ID == null)
			C_Order_ID = 0;
		if (C_Invoice_ID == 0 && C_Order_ID == 0)
			return "";
		
		// As C_Invoice_ID != 0, get Standard Precision from Invoice
		int pricelist_id = -1;
		if(C_Invoice_ID > 0)
		{
			pricelist_id = DB.getSQLValue(null, 
					"SELECT M_PriceList_ID FROM C_Invoice WHERE C_Invoice_ID=?", C_Invoice_ID.intValue());
		}
		else if(C_Order_ID > 0)
		{
			pricelist_id = DB.getSQLValue(null, 
					"SELECT M_PriceList_ID FROM C_Order WHERE C_Order_ID=?", C_Order_ID.intValue());
		}
		
		int stdPrecision = MPriceList.getStandardPrecision(ctx, pricelist_id);	
		
		//	Get Info from Tab
		BigDecimal Amount = (BigDecimal)mTab.getValue("Amount");
		BigDecimal DiscountAmt = (BigDecimal)mTab.getValue("DiscountAmt");
		BigDecimal WriteOffAmt = (BigDecimal)mTab.getValue("WriteOffAmt");
		BigDecimal OverUnderAmt = (BigDecimal)mTab.getValue("OverUnderAmt");
		BigDecimal InvoiceAmt = (BigDecimal)mTab.getValue("InvoiceAmt");
		BigDecimal WithholdingAmt = (BigDecimal)mTab.getValue("WithholdingAmt");
		BigDecimal PayToOvUnAmount = (BigDecimal)mTab.getValue("PayToOverUnderAmount");
		log.fine("Amt=" + Amount + ", Discount=" + DiscountAmt
			+ ", WriteOff=" + WriteOffAmt + ", OverUnder=" + OverUnderAmt
			+ ", Invoice=" + InvoiceAmt + ", WithholdingAmt=" + WithholdingAmt);
		

		// Get percent of open invoice
		int C_InvoicePaySchedule_ID = 0;
		
		if (C_Invoice_ID > 0 && Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_Invoice_ID") == C_Invoice_ID.intValue()
				&& Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID") != 0)
			C_InvoicePaySchedule_ID = Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID");
		
		//	Changed Column
		String colName = mField.getColumnName();
		
		if ((colName.equals("C_Invoice_ID") || colName.equals("C_Order_ID"))
				&& WithholdingAmt.signum() > 0)
		{ // Load pertama kali saat invoice atau order dipilih.
			Amount = InvoiceAmt.subtract(WithholdingAmt);
			PayToOvUnAmount = Amount;
		}
		
		if ("PayToOverUnderAmount".equals(colName) || "Amount".equals(colName)
				|| "WriteOffAmt".equals(colName))
		{
			BigDecimal discountedAmt = (BigDecimal) value;
			if ("WriteOffAmt".equals(colName))
			{
				discountedAmt = (BigDecimal) mTab.getValue("Amount");
				discountedAmt = discountedAmt.add((BigDecimal) value);
			}
			Timestamp ts = (Timestamp)mTab.getParentTab().getValue ("DateTrx");
			if (ts == null)
				ts = new Timestamp (System.currentTimeMillis ());
			String sql = "SELECT unspaymentdiscount(?,?,?,?)";
			DiscountAmt = DB.getSQLValueBD(null, sql, C_Invoice_ID, ts, 
					C_InvoicePaySchedule_ID, discountedAmt);
			if (null == DiscountAmt)
			{
				DiscountAmt = Env.ZERO;
			}
			mTab.setValue("DiscountAmt", DiscountAmt);
		}
		//  PayAmt - calculate write off
		if (colName.equals("Amount") || colName.equals("PayToOverUnderAmount"))
		{
			if (colName.equals("PayToOverUnderAmount")) {
				Amount = PayToOvUnAmount;
			}
			try {
				// Get the total of withholding that paid in payment.
				// 1. Recalculate WHT
				// If Payment Allocate record is not saved, We need to get from Invoice. Else, get from Payment.
				BigDecimal baseTax = C_PaymentAllocate_ID == 0 ? 
						LCO_MInvoice.getInvoiceBaseWithholding(C_Invoice_ID) :
						LCO_MInvoice.getPaymentBaseWithholding(C_PaymentAllocate_ID);
						
				BigDecimal totalPayment = Amount.add(WriteOffAmt).add(DiscountAmt);
				
				BigDecimal invWithoutWH = InvoiceAmt.subtract(baseTax);
				BigDecimal percentPayment =  totalPayment.divide(invWithoutWH, 6, BigDecimal.ROUND_HALF_UP);
				
				BigDecimal PercentInvoiceOpen = 
						LCO_MInvoice.getPercentInvoiceOpenAmt(C_Invoice_ID.intValue(), C_InvoicePaySchedule_ID);
				
				if (C_PaymentAllocate_ID == 0) {
					WithholdingAmt = baseTax.multiply(percentPayment).multiply(PercentInvoiceOpen);
				}
				else { // If C_PaymentAllocate record exists. 
					WithholdingAmt = baseTax.multiply(percentPayment);
				}
				
				WithholdingAmt = WithholdingAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP);
	
				mTab.setValue("WithholdingAmt", WithholdingAmt);
				
				OverUnderAmt = InvoiceAmt.subtract(Amount).subtract(DiscountAmt).subtract(WriteOffAmt).subtract(WithholdingAmt);
				mTab.setValue("Amount", Amount);
				mTab.setValue("PayToOverUnderAmount", Amount);
				mTab.setValue("OverUnderAmt", OverUnderAmt);
			} 
			catch (SQLException e) {
				log.log (Level.SEVERE, "", e);
				return e.getLocalizedMessage ();
			}
		}
		else if (colName.equals("OverUnderAmt"))
		{
			try {			
			
				// Get percentage of the payment
				BigDecimal percent_payment = (InvoiceAmt.subtract(OverUnderAmt)).divide(InvoiceAmt, 6, BigDecimal.ROUND_HALF_UP);
				
				BigDecimal PercentInvoiceOpen = LCO_MInvoice.getPercentInvoiceOpenAmt(C_Invoice_ID.intValue(), C_InvoicePaySchedule_ID);
			
				// 1. Recalculate WHT
				// If Payment Allocate record is not saved, We need to get from Invoice. Else, get from Payment.
				BigDecimal baseTax = C_PaymentAllocate_ID == 0 ? 
						LCO_MInvoice.getInvoiceBaseWithholding(C_Invoice_ID) :
						LCO_MInvoice.getPaymentBaseWithholding(C_PaymentAllocate_ID);		

				if (C_PaymentAllocate_ID == 0) {
					WithholdingAmt = baseTax.multiply(percent_payment).multiply(PercentInvoiceOpen);
				}
				else { // If C_PaymentAllocate record exists. 
					WithholdingAmt = baseTax.multiply(percent_payment);
				}
			
				mTab.setValue("WithholdingAmt", WithholdingAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP));
				
				// 2. Recalculate Amount
				Amount = InvoiceAmt.subtract(DiscountAmt).subtract(WriteOffAmt).subtract(OverUnderAmt).subtract(WithholdingAmt);
				mTab.setValue("Amount", Amount.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP));
				mTab.setValue("PayToOverUnderAmount", Amount.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP));
				
				// 3. Recalculate Tax Amount (VAT)
				BigDecimal TotalTax = LCO_MInvoice.getInvoiceBaseTax(C_Invoice_ID.intValue());
				BigDecimal TaxAmt = percent_payment.multiply(PercentInvoiceOpen).multiply(TotalTax);
				mTab.setValue("TaxAmt", TaxAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP));
			
			} catch (SQLException e) {
				log.log (Level.SEVERE, "", e);
				return e.getLocalizedMessage ();
			}
		}
		else    //  calculate Amount
		{
			try {
				BigDecimal baseTax = C_PaymentAllocate_ID == 0 ? 
						LCO_MInvoice.getInvoiceBaseWithholding(C_Invoice_ID) :
						LCO_MInvoice.getPaymentBaseWithholding(C_PaymentAllocate_ID);
				
				BigDecimal totalPayment = Amount.add(WriteOffAmt).add(DiscountAmt);
				
				BigDecimal invWithoutWH = InvoiceAmt.subtract(baseTax);
				BigDecimal percentPayment =  totalPayment.divide(invWithoutWH, 6, BigDecimal.ROUND_HALF_UP);
				
				BigDecimal PercentInvoiceOpen = 
						LCO_MInvoice.getPercentInvoiceOpenAmt(C_Invoice_ID.intValue(), C_InvoicePaySchedule_ID);
				
				if (C_PaymentAllocate_ID == 0) {
					WithholdingAmt = baseTax.multiply(percentPayment).multiply(PercentInvoiceOpen);
				}
				else { // If C_PaymentAllocate record exists. 
					WithholdingAmt = baseTax.multiply(percentPayment);
				}
				
				WithholdingAmt = WithholdingAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP);
			} 
			catch (SQLException e) {
				log.log (Level.SEVERE, "", e);
				return e.getLocalizedMessage ();
			}
			
			OverUnderAmt = InvoiceAmt.subtract(Amount).subtract(DiscountAmt).subtract(WriteOffAmt).subtract(WithholdingAmt);
			mTab.setValue("WithholdingAmt", WithholdingAmt);
			mTab.setValue("OverUnderAmt", OverUnderAmt.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP));
			mTab.setValue("Amount", Amount.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP));
			mTab.setValue("PayToOverUnderAmount", Amount.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP));
		}

		return "";
	}	//	amounts
	
	/**
	 * Payment_Amounts. Change of: - IsOverUnderPayment -> set OverUnderAmt to 0 -
	 * C_Currency_ID, C_ConvesionRate_ID -> convert all - PayAmt, DiscountAmt,
	 * WriteOffAmt, OverUnderAmt -> PayAmt make sure that add up to
	 * InvoiceOpenAmt
	 * @param ctx context
	 * @param WindowNo current Window No
	 * @param mTab Grid Tab
	 * @param mField Grid Field
	 * @param value New Value
	 * @param oldValue Old Value
	 * @return null or error message
	 */
	public String amounts_payment(Properties ctx, int WindowNo, GridTab mTab,
		GridField mField, Object value, Object oldValue)
	{
		if (isCalloutActive ()) // assuming it is resetting value
			return "";
		int C_Invoice_ID = Env.getContextAsInt (ctx, WindowNo, "C_Invoice_ID");
		// New Payment
		if (Env.getContextAsInt (ctx, WindowNo, "C_Payment_ID") == 0
			&& Env.getContextAsInt (ctx, WindowNo, "C_BPartner_ID") == 0
			&& C_Invoice_ID == 0)
			return "";
		// Changed Column
		String colName = mField.getColumnName ();
		if (colName.equals ("IsOverUnderPayment") // Set Over/Under Amt to
			// Zero
			|| !"Y".equals (Env.getContext (ctx, WindowNo, "IsOverUnderPayment")))
			mTab.setValue ("OverUnderAmt", Env.ZERO);
		int C_InvoicePaySchedule_ID = 0;
		if (Env.getContextAsInt (ctx, WindowNo, Env.TAB_INFO, "C_Invoice_ID") == C_Invoice_ID
			&& Env.getContextAsInt (ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID") != 0)
		{
			C_InvoicePaySchedule_ID = Env.getContextAsInt (ctx, WindowNo, Env.TAB_INFO, "C_InvoicePaySchedule_ID");
		}
		// Get Open Amount & Invoice Currency
		BigDecimal InvoiceOpenAmt = Env.ZERO;
		int C_Currency_Invoice_ID = 0;
		if (C_Invoice_ID != 0)
		{
			Timestamp ts = (Timestamp)mTab.getValue ("DateTrx");
			if (ts == null)
				ts = new Timestamp (System.currentTimeMillis ());
			String sql = "SELECT C_BPartner_ID,C_Currency_ID," // 1..2
				+ " invoiceOpen(C_Invoice_ID,?)," // 3 #1
				+ " invoiceDiscount(C_Invoice_ID,?,?), IsSOTrx " // 4..5 #2/3
				+ "FROM C_Invoice WHERE C_Invoice_ID=?"; // #4
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement (sql, null);
				pstmt.setInt (1, C_InvoicePaySchedule_ID);
				pstmt.setTimestamp (2, ts);
				pstmt.setInt (3, C_InvoicePaySchedule_ID);
				pstmt.setInt (4, C_Invoice_ID);
				rs = pstmt.executeQuery ();
				if (rs.next ())
				{
					C_Currency_Invoice_ID = rs.getInt (2);
					InvoiceOpenAmt = rs.getBigDecimal (3); // Set Invoice Open
					// Amount
					if (InvoiceOpenAmt == null)
						InvoiceOpenAmt = Env.ZERO;
				}
			}
			catch (SQLException e)
			{
				log.log (Level.SEVERE, sql, e);
				return e.getLocalizedMessage ();
			}
			finally
			{
				DB.close (rs, pstmt);
				rs = null;
				pstmt = null;
			}
		} // get Invoice Info
		log.fine ("Open=" + InvoiceOpenAmt + ", C_Invoice_ID=" + C_Invoice_ID
			+ ", C_Currency_ID=" + C_Currency_Invoice_ID);
		// Get Info from Tab
		BigDecimal PayAmt = (BigDecimal)mTab.getValue ("PayAmt");
		BigDecimal DiscountAmt = 
				mTab.getValue ("DiscountAmt") == null? Env.ZERO : (BigDecimal)mTab.getValue ("DiscountAmt");
		BigDecimal WriteOffAmt = 
				mTab.getValue ("WriteOffAmt") == null? Env.ZERO : (BigDecimal)mTab.getValue ("WriteOffAmt");
		BigDecimal OverUnderAmt = 
				mTab.getValue ("OverUnderAmt") == null? Env.ZERO : (BigDecimal)mTab.getValue ("OverUnderAmt");
		BigDecimal WithholdingAmt = 
				mTab.getValue ("WithholdingAmt") == null? Env.ZERO : (BigDecimal)mTab.getValue ("WithholdingAmt");
		log.fine ("Pay=" + PayAmt + ", Discount=" + DiscountAmt + ", WriteOff="
			+ WriteOffAmt + ", OverUnderAmt=" + OverUnderAmt + ", WithholdingAmt=" + WithholdingAmt);
		
		// Get Currency Info
		int C_Currency_ID = ((Integer)mTab.getValue ("C_Currency_ID"))
			.intValue ();
		MCurrency currency = MCurrency.get (ctx, C_Currency_ID);
		Timestamp ConvDate = (Timestamp)mTab.getValue ("DateTrx");
		int C_ConversionType_ID = 0;
		Integer ii = (Integer)mTab.getValue ("C_ConversionType_ID");
		if (ii != null)
			C_ConversionType_ID = ii.intValue ();
		int AD_Client_ID = Env.getContextAsInt (ctx, WindowNo, "AD_Client_ID");
		int AD_Org_ID = Env.getContextAsInt (ctx, WindowNo, "AD_Org_ID");
		// Get Currency Rate
		BigDecimal CurrencyRate = Env.ONE;
		if ((C_Currency_ID > 0 && C_Currency_Invoice_ID > 0 && C_Currency_ID != C_Currency_Invoice_ID)
			|| colName.equals ("C_Currency_ID")
			|| colName.equals ("C_ConversionType_ID"))
		{
			log.fine ("InvCurrency=" + C_Currency_Invoice_ID + ", PayCurrency="
				+ C_Currency_ID + ", Date=" + ConvDate + ", Type="
				+ C_ConversionType_ID);
			CurrencyRate = MConversionRate.getRate (C_Currency_Invoice_ID,
				C_Currency_ID, ConvDate, C_ConversionType_ID, AD_Client_ID,
				AD_Org_ID);
			if (CurrencyRate == null || CurrencyRate.compareTo (Env.ZERO) == 0)
			{
				// mTab.setValue("C_Currency_ID", new
				// Integer(C_Currency_Invoice_ID)); // does not work
				if (C_Currency_Invoice_ID == 0)
					return ""; // no error message when no invoice is selected
				return "NoCurrencyConversion";
			}
			//
			InvoiceOpenAmt = InvoiceOpenAmt.multiply (CurrencyRate).setScale (
				currency.getStdPrecision (), BigDecimal.ROUND_HALF_UP);
			log.fine ("Rate=" + CurrencyRate + ", InvoiceOpenAmt="
				+ InvoiceOpenAmt);
		}
		// Currency Changed - convert all
		if (colName.equals ("C_Currency_ID")
			|| colName.equals ("C_ConversionType_ID"))
		{
			PayAmt = PayAmt.multiply (CurrencyRate).setScale (
				currency.getStdPrecision (), BigDecimal.ROUND_HALF_UP);
			mTab.setValue ("PayAmt", PayAmt);
			DiscountAmt = DiscountAmt.multiply (CurrencyRate).setScale (
				currency.getStdPrecision (), BigDecimal.ROUND_HALF_UP);
			mTab.setValue ("DiscountAmt", DiscountAmt);
			WriteOffAmt = WriteOffAmt.multiply (CurrencyRate).setScale (
				currency.getStdPrecision (), BigDecimal.ROUND_HALF_UP);
			mTab.setValue ("WriteOffAmt", WriteOffAmt);
			OverUnderAmt = OverUnderAmt.multiply (CurrencyRate).setScale (
				currency.getStdPrecision (), BigDecimal.ROUND_HALF_UP);
			mTab.setValue ("OverUnderAmt", OverUnderAmt);
			WithholdingAmt = WithholdingAmt.multiply (CurrencyRate).setScale (
					currency.getStdPrecision (), BigDecimal.ROUND_HALF_UP);
				mTab.setValue ("WithholdingAmt", WithholdingAmt);
		}
		/*
		// No Invoice - Set Discount, Witeoff, Under/Over to 0
		else if (C_Invoice_ID == 0)
		{
			if (Env.ZERO.compareTo (DiscountAmt) != 0)
				mTab.setValue ("DiscountAmt", Env.ZERO);
			if (Env.ZERO.compareTo (WriteOffAmt) != 0)
				mTab.setValue ("WriteOffAmt", Env.ZERO);
			if (Env.ZERO.compareTo (OverUnderAmt) != 0)
				mTab.setValue ("OverUnderAmt", Env.ZERO);
			if (Env.ZERO.compareTo (WithholdingAmt) != 0)
				mTab.setValue ("WithholdingAmt", Env.ZERO);			
		}*/
		else if (C_Invoice_ID > 0 && colName.equals ("PayAmt")
			&& mTab.get_ValueAsString ("DocStatus").equals ("DR")
			&& "Y"
				.equals (Env.getContext (ctx, WindowNo, "IsOverUnderPayment")))
		{
			OverUnderAmt = InvoiceOpenAmt.subtract (PayAmt).subtract (
				DiscountAmt).subtract (WriteOffAmt).subtract(WithholdingAmt);
			mTab.setValue ("OverUnderAmt", OverUnderAmt);
		}
		else if (C_Invoice_ID > 0 && colName.equals ("PayAmt")
			&& mTab.get_ValueAsString ("DocStatus").equals ("DR"))
		{
			WriteOffAmt = InvoiceOpenAmt.subtract (PayAmt).subtract (
				DiscountAmt).subtract (OverUnderAmt).subtract(WithholdingAmt);
			mTab.setValue ("WriteOffAmt", WriteOffAmt);
		}
		else if (C_Invoice_ID > 0 && colName.equals ("IsOverUnderPayment")
			&& mTab.get_ValueAsString ("DocStatus").equals ("DR"))
		{
			boolean overUnderPaymentActive = "Y".equals (Env.getContext (ctx,
				WindowNo, "IsOverUnderPayment"));
			if (overUnderPaymentActive)
			{
				OverUnderAmt = InvoiceOpenAmt.subtract (PayAmt).subtract (
					DiscountAmt).subtract(WithholdingAmt);
				mTab.setValue ("WriteOffAmt", Env.ZERO);
				mTab.setValue ("OverUnderAmt", OverUnderAmt);
			}else{
				WriteOffAmt = InvoiceOpenAmt.subtract (PayAmt).subtract (
					DiscountAmt).subtract(WithholdingAmt);
				mTab.setValue ("WriteOffAmt", WriteOffAmt);
				mTab.setValue ("OverUnderAmt", Env.ZERO);
			}
		}
		// Added Lines By Goodwill (02-03-2006)
		// Reason: we must make the callout is called just when docstatus is
		// draft
		// Old Code : else // calculate PayAmt
		// New Code :
		/*
		else if (mTab.get_ValueAsString ("DocStatus").equals ("DR")) // calculate
		// PayAmt
		// End By Goodwill
		{
			PayAmt = InvoiceOpenAmt.subtract (DiscountAmt).subtract (
				WriteOffAmt).subtract (OverUnderAmt).subtract(WithholdingAmt);
			mTab.setValue ("PayAmt", PayAmt);
		}*/
		return "";
	} // amounts	
	
	
	/**
	 * 
	 * @param ctx
	 * @param WidowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String payToOverUnderAmount (Properties ctx, int WidowNo,
			GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		return "";
	}
}	//	LCO_CalloutWithholding