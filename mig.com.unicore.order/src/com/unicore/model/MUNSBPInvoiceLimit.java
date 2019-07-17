/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * @author root
 *
 */
public class MUNSBPInvoiceLimit extends X_UNS_BP_InvoiceLimit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_BP_InvoiceLimit_ID
	 * @param trxName
	 */
	public MUNSBPInvoiceLimit(Properties ctx, int UNS_BP_InvoiceLimit_ID,
			String trxName) {
		super(ctx, UNS_BP_InvoiceLimit_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBPInvoiceLimit(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static Integer getLimit(int C_BPartner_ID, int M_Product_Category_ID, String trxName)
	{
		StringBuilder sql = new StringBuilder("SELECT ").append(COLUMNNAME_Limit)
				.append(" FROM ").append(Table_Name).append(" WHERE ")
				.append(COLUMNNAME_C_BPartner_ID).append("=").append(C_BPartner_ID)
				.append(" AND ").append(COLUMNNAME_M_Product_Category_ID)
				.append(M_Product_Category_ID);
		
		Integer retVal = DB.getSQLValue(trxName, sql.toString());
		
		return retVal;
	}

}
