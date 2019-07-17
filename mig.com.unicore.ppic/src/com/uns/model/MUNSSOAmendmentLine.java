/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

import com.uns.base.model.MOrder;
import com.uns.base.model.MOrderLine;

/**
 * @author menjangan
 *
 */
public class MUNSSOAmendmentLine extends X_UNS_SO_AmendmentLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5557089303378038656L;

	/**
	 * @param ctx
	 * @param UNS_SO_AmendmentLine_ID
	 * @param trxName
	 */
	public MUNSSOAmendmentLine(Properties ctx, int UNS_SO_AmendmentLine_ID,
			String trxName) {
		super(ctx, UNS_SO_AmendmentLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSOAmendmentLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSSOAmendmentLine(MUNSSOAmendment amendment)
	{
		super(amendment.getCtx(), 0, amendment.get_TrxName());
		setAD_Org_ID(amendment.getAD_Org_ID());
		setUNS_SO_Amendment_ID(amendment.getUNS_SO_Amendment_ID());
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		int lineNo = DB.getSQLValue(get_TrxName(), 
				"SELECT COALESCE(MAX(" + COLUMNNAME_LineNo + "), 0) + 1 FROM " 
		+ Table_Name + " WHERE " + COLUMNNAME_UNS_SO_Amendment_ID 
		+ " = " + getUNS_SO_Amendment_ID());
		setLineNo(lineNo);
		
		if(getC_OrderLine_ID() <= 0)
		{
			MUNSSOAmendment parent = getParent();
			MOrder order = new MOrder(getCtx(), parent.getC_Order_ID(), get_TrxName());
			MOrderLine orderLine = order.getOrderLineOf(getM_Product_ID());
			if(null != orderLine)
			{
				setC_OrderLine_ID(orderLine.get_ID());
				setPrevAmt(orderLine.getLineNetAmt());
				setPrevPrice(orderLine.getPriceActual());
				setPrevQuantity(orderLine.getQtyOrdered());
			}
		}
		return super.beforeSave(newRecord);
	}

	public MUNSSOAmendment getParent()
	{
		return (MUNSSOAmendment)getUNS_SO_Amendment();
	}
}
