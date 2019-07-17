/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author root
 *
 */
public class MUNSCvsLimitProduct extends X_UNS_Cvs_LimitProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2876069225820086901L;
	private MUNSCvsLimit m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_Cvs_LimitProduct_ID
	 * @param trxName
	 */
	public MUNSCvsLimitProduct(Properties ctx, int UNS_Cvs_LimitProduct_ID,
			String trxName) {
		super(ctx, UNS_Cvs_LimitProduct_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCvsLimitProduct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * getParent
	 */
	public MUNSCvsLimit getParent()
	{
		return getParent(false);
	}

	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSCvsLimit getParent(boolean requery)
	{
		if(null != m_parent && !requery)
			return m_parent;
		
		m_parent = (MUNSCvsLimit) getUNS_Cvs_Limit();
		return m_parent;
	}
}
