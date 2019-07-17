/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.uns.model.MUNSInvoiceBC;

/**
 * @author YAKA
 *
 */
public class CalloutBeaCukai extends CalloutEngine implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutBeaCukai() {

	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue = null;
		if (MUNSInvoiceBC.COLUMNNAME_QtyUsed.equals(mField.getColumnName()))
		{
			retValue = calcBeaCukai(ctx, WindowNo, mTab, mField, value);
		} else if (MUNSInvoiceBC.COLUMNNAME_ImportDutyPersent.equals(mField.getColumnName()))
		{
			retValue = calcEntryFee(ctx, WindowNo, mTab, mField, value);
		}
		
		return retValue;
	}

	private String calcEntryFee(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		Integer EntryFeePersent = (Integer) value;
		if (EntryFeePersent == null || EntryFeePersent.intValue() == 0)
			return "";
		
		BigDecimal CIFAmtIDR = (BigDecimal) mTab.getValue(MUNSInvoiceBC.COLUMNNAME_CIFAmtIDR);
		mTab.setValue(MUNSInvoiceBC.COLUMNNAME_ImportDutyPersent, CIFAmtIDR.multiply(new BigDecimal(EntryFeePersent)));
		
		return null;
	}
	
	private String calcBeaCukai(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		BigDecimal QtyUsed = (BigDecimal) value;
		if (QtyUsed == null || QtyUsed.intValue() == 0)
			return "";
		
		int id = (Integer) mTab.getValue(MUNSInvoiceBC.COLUMNNAME_UNS_InvoiceBC_ID);
		MUNSInvoiceBC invBC = new MUNSInvoiceBC(ctx, id, null);
		BigDecimal[] valueBD = invBC.calculateBC((Integer) mTab.getValue(MUNSInvoiceBC.COLUMNNAME_M_Product_ID) ,QtyUsed, 
				(BigDecimal) mTab.getValue(MUNSInvoiceBC.COLUMNNAME_Price));
		mTab.setValue(MUNSInvoiceBC.COLUMNNAME_TotalAmt, valueBD[0]);
		mTab.setValue(MUNSInvoiceBC.COLUMNNAME_CIFAmtUSD, valueBD[1]);
		mTab.setValue(MUNSInvoiceBC.COLUMNNAME_CIFAmtIDR, valueBD[2]);
		mTab.setValue(MUNSInvoiceBC.COLUMNNAME_ImportDutyPersent, valueBD[3]);
		mTab.setValue(MUNSInvoiceBC.COLUMNNAME_ImportDutyAmt, valueBD[4]);
		
		return null;
	}

}
