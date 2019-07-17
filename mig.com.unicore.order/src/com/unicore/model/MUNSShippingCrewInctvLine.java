package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

public class MUNSShippingCrewInctvLine extends X_UNS_ShippingCrewInctv_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7297474274515405280L;

	public MUNSShippingCrewInctvLine(Properties ctx,
			int UNS_ShippingCrewInctv_Line_ID, String trxName) {
		super(ctx, UNS_ShippingCrewInctv_Line_ID, trxName);
	}

	public MUNSShippingCrewInctvLine(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
	}
	
	protected boolean beforeSave(boolean newRecord)
	{
//		MUNSShippingCrewIncentive ci = new MUNSShippingCrewIncentive(getCtx(), getUNS_ShippingCrewIncentive_ID(), get_TrxName());
		
		if(getUNS_Shipping_ID() > 0)
		{
			MUNSShipping sh = new MUNSShipping(getCtx(), getUNS_Shipping_ID(), get_TrxName());
			setAD_Org_ID(sh.getAD_Org_ID());
			setDateDoc(sh.getDateDoc());
		}
		
//		if(newRecord && ci.get_ID() > 0)
//		{
//			ci.setGrandTotal(ci.getGrandTotal().add(getAmount().add(getHelperAmount())));
//			ci.saveEx();
//		}
//		else if(!newRecord && (is_ValueChanged(COLUMNNAME_Amount) || is_ValueChanged(COLUMNNAME_HelperAmount)))
//		{
//			BigDecimal diff = Env.ZERO;
//			if(is_ValueChanged(COLUMNNAME_Amount))
//				diff = diff.add(getAmount().subtract((BigDecimal) get_ValueOld(COLUMNNAME_Amount)));
//			if(is_ValueChanged(COLUMNNAME_HelperAmount))
//				diff = diff.add(getHelperAmount().subtract((BigDecimal) get_ValueOld(COLUMNNAME_HelperAmount)));
//			ci.setGrandTotal(ci.getGrandTotal().add(diff));
//			ci.saveEx();
//		}			
		return true;
	}
	
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		return upHeader();
	}
	
	protected boolean beforeDelete()
	{
		BigDecimal amount = (BigDecimal) get_ValueOld(COLUMNNAME_Amount);
		BigDecimal helperAmt = (BigDecimal) get_ValueOld(COLUMNNAME_HelperAmount);
		MUNSShippingCrewIncentive ci = new MUNSShippingCrewIncentive(getCtx(), getUNS_ShippingCrewIncentive_ID(), get_TrxName());
		ci.setGrandTotal(ci.getGrandTotal().subtract(amount).subtract(helperAmt));
		
		return ci.save();
	}
	
	public boolean upHeader()
	{		
		MUNSShippingCrewIncentive ci = new MUNSShippingCrewIncentive(
						getCtx(), getUNS_ShippingCrewIncentive_ID(), get_TrxName());
		BigDecimal amount = Env.ZERO;
		
		String sql = "SELECT SUM(Amount) + SUM(HelperAmount) FROM UNS_ShippingCrewInctv_Line"
				+ " WHERE UNS_ShippingCrewIncentive_ID = ?";
		amount = DB.getSQLValueBD(get_TrxName(), sql, getUNS_ShippingCrewIncentive_ID());
		if(amount == null)
			amount = Env.ZERO;
		
		ci.setGrandTotal(amount.add(ci.getAmountBonuses()));
		
		return ci.save();
	}
}