/**
 * 
 */
package com.unicore.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/**
 * @author menjangan
 *
 */
public class MInOutLine extends org.compiere.model.MInOutLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param M_InOutLine_ID
	 * @param trxName
	 */
	public MInOutLine(Properties ctx, int M_InOutLine_ID, String trxName) {
		super(ctx, M_InOutLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInOutLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param inout
	 */
	public MInOutLine(MInOut inout) {
		super(inout);
		// TODO Auto-generated constructor stub
	}
	
	private MOrderLine m_OrderLine = null;
	public BigDecimal getPriceOrder(boolean isDiscountAfterTrx)
	{
		if(null == m_OrderLine)
		{
			m_OrderLine = new MOrderLine(getCtx(), getC_OrderLine_ID(), get_TrxName());
		}
		BigDecimal price = Env.ZERO;
		if(isDiscountAfterTrx)
		{
			price = m_OrderLine.getPriceEntered();
		}
		else
		{
			price = m_OrderLine.getPriceList();
		}
		return price;
	}
	
	public BigDecimal getAmtOrder(boolean isDiscountAfterTrx)
	{
		BigDecimal price = getPriceOrder(isDiscountAfterTrx);
		BigDecimal amt = price.multiply(getConfirmedQty());
		return amt;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(is_ValueChanged(COLUMNNAME_QtyEntered))
			setMovementQty(getQtyEntered());
		return super.beforeSave(newRecord);
	}
}
