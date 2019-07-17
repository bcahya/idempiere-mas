/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Toriq
 *
 */
public class MUNSCustomerSalesPricing extends X_UNS_Customer_SalesPricing {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8389478072173510243L;

	/**
	 * @param ctx
	 * @param UNS_Customer_SalesPricing_ID
	 * @param trxName
	 */
	public MUNSCustomerSalesPricing(Properties ctx,
			int UNS_Customer_SalesPricing_ID, String trxName) {
		super(ctx, UNS_Customer_SalesPricing_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCustomerSalesPricing(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

}
