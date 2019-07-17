/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import com.uns.base.model.MOrder;


/**
 * @author menjangan
 *
 */
public class MUNSOrderShipment extends X_UNS_OrderShipment {
	
	private MOrder m_parent = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_OrderShipment_ID
	 * @param trxName
	 */
	public MUNSOrderShipment(Properties ctx, int UNS_OrderShipment_ID,
			String trxName) {
		super(ctx, UNS_OrderShipment_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSOrderShipment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MOrder getParent()
	{
		if (null == m_parent)
		{
			m_parent = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		}
		
		return m_parent;
	}
	
	@Override
	public boolean beforeSave(boolean newRecord)
	{
		if (!is_new())
		{
			//
		}
		return true;
	}

}
