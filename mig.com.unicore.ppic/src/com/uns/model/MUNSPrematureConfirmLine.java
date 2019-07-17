/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.AdempiereUserError;


/**
 * @author AzHaidar
 *
 */
public class MUNSPrematureConfirmLine extends X_UNS_PrematureConfirm_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7515153457327118725L;

	/**
	 * @param ctx
	 * @param UNS_PrematureConfirm_Line_ID
	 * @param trxName
	 */
	public MUNSPrematureConfirmLine(Properties ctx,
			int UNS_PrematureConfirm_Line_ID, String trxName) {
		super(ctx, UNS_PrematureConfirm_Line_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPrematureConfirmLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getPackages() != null && getPallets() != null)
			throw new AdempiereUserError("Please only define either one of Pallets or Packages to be approved");
		//TODO PREMATURE CONFIRM
//		if (getPallets() != null)
//		{
//			BigDecimal approvedQty = 
//					PalletHelper.getQuantityOfPalletSequence(getCtx(), get_TrxName(), 
//															 getM_Product_ID(), 
//															 getM_Locator_ID(), 
//															 getM_AttributeSetInstance_ID(), 
//															 getPallets());
//			if (approvedQty.compareTo(getOnHoldQty()) > 0)
//				throw new AdempiereUserError("The formated string of pallets you entered is greater Qty than OnHoldQty.");
//			setApprovedQty(approvedQty);
//		}
//		else if (getPackages() != null)
//		{
//			BigDecimal approvedQty = 
//					PalletHelper.getQuantityOfPackageSequence(getCtx(), get_TrxName(), 
//															 getM_Product_ID(), 
//															 getM_Locator_ID(), 
//															 getM_AttributeSetInstance_ID(), 
//															 getPackages());
//			if (approvedQty.compareTo(getOnHoldQty()) > 0)
//				throw new AdempiereUserError("The formated string of pallets you entered is greater Qty than OnHoldQty.");
//			setApprovedQty(approvedQty);
//		}
		return true;
	}
}
