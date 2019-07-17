/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MInvoice;
import org.compiere.util.DB;

import com.unicore.model.X_UNS_VATDocInOut;

/**
 * @author ALBURHANY
 *
 */
public class CalloutVATDocInOut implements IColumnCallout
	{
	
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue = null;
		if (X_UNS_VATDocInOut.COLUMNNAME_C_Invoice_ID.equals(mField.getColumnName()))
		{
			retValue = cInvoice(ctx, WindowNo, mTab, mField, value);
		}
		if (X_UNS_VATDocInOut.COLUMNNAME_ReceivedBy_ID.equals(mField.getColumnName()))
		{
			retValue = receivedBy(ctx, WindowNo, mTab, mField, value);
		}
		if (X_UNS_VATDocInOut.COLUMNNAME_DeliveredBy_ID.equals(mField.getColumnName()))
		{
			retValue = deliveredBy(ctx, WindowNo, mTab, mField, value);
		}
		return retValue;
	}
	
	/**
	 * 
	 */
	public CalloutVATDocInOut() {
		// TODO Auto-generated constructor stub
	}
	
	public String cInvoice (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_Invoice_ID = (Integer)value;
		
		if (C_Invoice_ID == null || C_Invoice_ID.intValue() == 0)
			return "";

		MInvoice invoice = MInvoice.get(ctx, C_Invoice_ID);
		
//		String sql = "SELECT CONCAT (C_BPartner_ID, ';', DateInvoiced) FROM C_Invoice WHERE C_Invoice_ID = ?";
//		
//		String forSplit = DB.getSQLValueString(null, sql, C_Invoice_ID);
//		
//		if(null == forSplit)
//			return null;
//		
//		String[] values = forSplit.split(";");
//		String oo1 = values[0];
//		String oo2 = values[1];
//		
//		if(null == oo1)
//			return null;
//		if(null == oo2)
//			return null;
//		
//		int C_BPartner_ID = new Integer(oo1);
//		Timestamp DateInvoiced = Timestamp.valueOf(oo2);
		
		mTab.setValue("AD_Org_ID", invoice.getAD_Org_ID());
		mTab.setValue("C_BPartner_ID", invoice.getC_BPartner_ID());
		mTab.setValue("DateInvoiced", invoice.getDateInvoiced());
		
		return "";
	}	// cInvoice	
	
	
	public String receivedBy (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer ReceivedBy_ID = (Integer)value;
		
		if (ReceivedBy_ID == null || ReceivedBy_ID.intValue() == 0)
			return "";

		String sql = "SELECT name FROM UNS_Employee WHERE UNS_Employee_ID = ?";
		
		String name = DB.getSQLValueString(null, sql, ReceivedBy_ID);
		
		if(null == name)
			return null;
		
		mTab.setValue("ReceivedBy", name);
		
		return "";
	}	// receivedBy
	
	public String deliveredBy (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer DeliveredBy_ID = (Integer)value;
		
		if (DeliveredBy_ID == null || DeliveredBy_ID.intValue() == 0)
			return "";

		String sql = "SELECT name FROM UNS_Employee WHERE UNS_Employee_ID = ?";
		
		String name = DB.getSQLValueString(null, sql, DeliveredBy_ID);
		
		if(null == name)
			return null;
		
		mTab.setValue("DeliveredBy", name);
		
		return "";
	}	// deliveredBy
	
	public String clear (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		mTab.setValue(mField.getColumnName(), null);
		return "";
	}

}
