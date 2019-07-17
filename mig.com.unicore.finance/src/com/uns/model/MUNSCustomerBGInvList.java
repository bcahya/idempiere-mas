/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * @author setyaka
 * 
 */
public class MUNSCustomerBGInvList extends X_UNS_CustomerBG_InvList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7056055735288500256L;

	/**
	 * @param ctx
	 * @param UNS_CustomerBG_InvList_ID
	 * @param trxName
	 */
	public MUNSCustomerBGInvList(Properties ctx, int UNS_CustomerBG_InvList_ID, String trxName) {
		super(ctx, UNS_CustomerBG_InvList_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCustomerBGInvList(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSCustomerBGInvList(MUNSCustomerBG giro) {
		this(giro.getCtx(), 0, giro.get_TrxName());

		setClientOrg(giro);
		setUNS_CustomerBG_ID(giro.get_ID());
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		return updateHeaderAmount();
	}
	
	@Override protected boolean afterDelete(boolean success)
	{
		return updateHeaderAmount();
	}

	private boolean updateHeaderAmount() {

		MUNSCustomerBG cbg = new MUNSCustomerBG(getCtx(), getUNS_CustomerBG_ID(), get_TrxName());
		// Find Total Amount
//		String sql = "SELECT SUM(NetAmtToInvoice) FROM UNS_CustomerBG_InvList WHERE UNS_CustomerBG_ID = ? AND IsActive='Y' ";
//		BigDecimal grandtotal = DB.getSQLValueBD(get_TrxName(), sql, getUNS_CustomerBG_ID());

		String sqlPaid = "SELECT SUM(PaidAmt) FROM UNS_CustomerBG_InvList WHERE UNS_CustomerBG_ID = ? AND IsActive='Y' ";
		BigDecimal paidAmt = DB.getSQLValueBD(get_TrxName(), sqlPaid, getUNS_CustomerBG_ID());
		if (paidAmt == null)
		{
			paidAmt = Env.ZERO;
		}
//		cbg.setGrandTotal(grandtotal == null ? Env.ZERO : grandtotal);
		cbg.setGrandTotal(paidAmt);
		cbg.setPaidAmt(paidAmt);

		if (!cbg.save(get_TrxName()))
		{
			log.saveError("Cannot Update Header", "Cannot Save Header");
			return false;
		}

		return true;
	}
	
	/**
	 * 
	 * @param UNS_Billing_LineResult_ID
	 * @param UNS_CustomerBG_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSCustomerBGInvList get(
			int UNS_Billing_LineResult_ID, int UNS_CustomerBG_ID, String trxName)
	{
		String sql = "SELECT * FROM " . concat(Table_Name) . concat(" WHERE ") 
				. concat(COLUMNNAME_UNS_CustomerBG_ID) . concat(" = ? AND ") 
				. concat(COLUMNNAME_UNS_BillingLine_Result_ID) . concat(" = ? ");
		
		MUNSCustomerBGInvList record = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, trxName);
			st.setInt(1, UNS_CustomerBG_ID);
			st.setInt(2, UNS_Billing_LineResult_ID);
			rs = st.executeQuery();
			if (rs.next())
			{
				record = new MUNSCustomerBGInvList(Env.getCtx(), rs, trxName);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return record;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getC_Order_ID() > 0)
		{
			MOrder order = (MOrder) getC_Order();
			setNetAmtToInvoice(order.getGrandTotal());
			setC_Invoice_ID(-1);
			setAD_Org_ID(order.getAD_Org_ID());
		}
		else if (getC_Invoice_ID() > 0)
		{
			MInvoice invoice = (MInvoice) getC_Invoice();
			setNetAmtToInvoice(invoice.getOpenAmt());
			setC_Order_ID(-1);
			setAD_Org_ID(invoice.getAD_Org_ID());
		}
		
		if(getC_PaymentAllocate_ID() > 0)
		{
			setC_Payment_ID(getC_PaymentAllocate().getC_Payment_ID());
		}
		
		MUNSCustomerBG customerBG = (MUNSCustomerBG) getUNS_CustomerBG();
		BigDecimal limitAmt = customerBG.getLimitAmt();
		BigDecimal totalUsed = Env.ZERO;
		for (MUNSCustomerBGInvList invList : customerBG.getLinesBGInv(true))
		{
			if(invList.get_ID() == get_ID())
			{
				continue;
			}
			
			totalUsed = totalUsed.add(invList.getPaidAmt());
		}
		
		totalUsed = totalUsed.add(getPaidAmt());
		if(totalUsed.compareTo(limitAmt) == 1)
		{
			throw new AdempiereUserError(Msg.getMsg(getCtx(), "Over Amount Giro"));
		}
		
		return super.beforeSave(newRecord);
	}
}
