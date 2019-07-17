/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.uns.model.MUNSChargeDetail;
import com.uns.model.MUNSVoucher;
import com.uns.model.MUNSVoucherCode;

/**
 * @author root
 *
 */
public class CalloutChargeDetail extends CalloutEngine implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutChargeDetail() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		switch (mField.getColumnName()) {
			case MUNSChargeDetail.COLUMNNAME_UNS_Voucher_ID :
				return onVoucherChange(ctx, WindowNo, mTab, mField, value, oldValue);
			case MUNSChargeDetail.COLUMNNAME_ChargeType :
				return onChargeTypeChange(ctx, WindowNo, mTab, mField, value, oldValue);
			default : return null;
		}
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String onChargeTypeChange(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue)
	{
		
		return null;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String onVoucherChange(Properties ctx, int WindowNo, GridTab mTab, 
			GridField mField, Object value, Object oldValue)
	{
		if(null == value)
		{
			mTab.setValue(MUNSChargeDetail.COLUMNNAME_C_Charge_ID, null);
			mTab.setValue(MUNSChargeDetail.COLUMNNAME_IsFuel, false);
			mTab.setValue(MUNSChargeDetail.COLUMNNAME_Liters, null);
			mTab.setValue(MUNSChargeDetail.COLUMNNAME_UNS_Fuel_ID, null);
			mTab.setValue(MUNSChargeDetail.COLUMNNAME_UNS_Voucher_Code_ID, null);
			mTab.setValue(MUNSChargeDetail.COLUMNNAME_VoucherCode, null);
			return null;
		}
		
		MUNSVoucher voucher = new MUNSVoucher(ctx, (Integer) value, null);
		MUNSVoucherCode code = voucher.getUnusedVoucher();
		
		if(null == code)
		{
			throw new AdempiereUserError(Msg.getMsg(Env.getAD_Language(ctx), "Semua nomor voucher dari buku voucher yang anda pilih sudah terpakai."));
		}
		
		mTab.setValue(MUNSChargeDetail.COLUMNNAME_IsFuel, voucher.getVoucherType()
				.equals(MUNSVoucher.VOUCHERTYPE_FuelVoucher));
		mTab.setValue(MUNSChargeDetail.COLUMNNAME_C_Charge_ID, voucher.getC_Charge_ID());
		mTab.setValue(MUNSChargeDetail.COLUMNNAME_Liters, Env.ZERO);
		mTab.setValue(MUNSChargeDetail.COLUMNNAME_UNS_Fuel_ID, null);
		mTab.setValue(MUNSChargeDetail.COLUMNNAME_UNS_Voucher_Code_ID, code.get_ID());
		mTab.setValue(MUNSChargeDetail.COLUMNNAME_VoucherCode, code.getName());
		
		return null;
	}

}
