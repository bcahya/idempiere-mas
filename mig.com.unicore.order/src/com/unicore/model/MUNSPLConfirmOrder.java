/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/**
 * @author UNTA_Andy
 * 
 */
public class MUNSPLConfirmOrder extends X_UNS_PL_ConfirmOrder
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8327563910363290136L;

	/**
	 * @param ctx
	 * @param UNS_PL_ConfirmOrder_ID
	 * @param trxName
	 */
	public MUNSPLConfirmOrder(Properties ctx, int UNS_PL_ConfirmOrder_ID, String trxName)
	{
		super(ctx, UNS_PL_ConfirmOrder_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPLConfirmOrder(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}


	public void setOrder(MUNSPLConfirmProduct cProduct, MUNSPLReturnOrder PLOrder)
	{
		setClientOrg(cProduct);

		setUNS_PL_ReturnOrder_ID(PLOrder.get_ID());
		setM_Product_ID(cProduct.getM_Product_ID());

		setQty(MUNSPLReturnOrder.getQtyProduct(cProduct.getM_Product_ID()
				,PLOrder.getUNS_PackingList_Order_ID()
				,get_TrxName()));
	}

	private void setQty(BigDecimal qtyProduct)
	{
		setTargetQty(qtyProduct);
		setConfirmedQty(qtyProduct);
		setDifferenceQty(Env.ZERO);
		setScrappedQty(Env.ZERO);
	}

}
