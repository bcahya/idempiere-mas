/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author root
 *
 */
public class MUNSVoucherBP extends X_UNS_VoucherBP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4105359976855841816L;
	private MUNSVoucher m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_VoucherBP_ID
	 * @param trxName
	 */
	public MUNSVoucherBP(Properties ctx, int UNS_VoucherBP_ID, String trxName) {
		super(ctx, UNS_VoucherBP_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSVoucherBP(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSVoucher getParent()
	{
		if(m_parent != null)
			return m_parent;
		m_parent = (MUNSVoucher) getUNS_Voucher();
		return m_parent;
	}

}
