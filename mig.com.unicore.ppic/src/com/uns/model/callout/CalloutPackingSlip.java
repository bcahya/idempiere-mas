/**
 * 
 */
package com.uns.model.callout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.Query;
import org.compiere.util.DB;

import com.uns.model.MUNSPackingSlipSupplier;
import com.uns.model.X_UNS_PackingSlipSupplier;

/**
 * @author menjangan
 *
 */
public class CalloutPackingSlip extends CalloutEngine implements IColumnCallout{

	/**
	 * 
	 */
	public CalloutPackingSlip() {
	}

	/**
	 * 
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String invoice(Properties ctx, int windowNo,
			GridTab mTab, GridField mField, Object value)
	{
		String msg = "";
		Integer M_Invoice_ID = (Integer)value;
		if (isCalloutActive())
			return msg;
		
		if (null == M_Invoice_ID || M_Invoice_ID.intValue() <= 0)
			return msg;
		
		int invoiceID = Integer.parseInt(Integer.toString(M_Invoice_ID));
		MInvoice invoice = MInvoice.get(ctx, invoiceID);
		
		mTab.setValue(X_UNS_PackingSlipSupplier.COLUMNNAME_C_Order_ID, invoice.getC_Order_ID());
		//mTab.setValue(X_UNS_PackingSlipSupplier.COLUMNNAME_M_Warehouse_ID, invoice.getC_Order().getM_Warehouse_ID());
		
		MInvoiceLine[] invLines = invoice.getLines();
		int ioID = (invLines == null || invLines.length == 0)? 0 : invLines[0].getM_InOutLine().getM_InOut_ID();
		if (ioID > 0)
			mTab.setValue(X_UNS_PackingSlipSupplier.COLUMNNAME_M_InOut_ID, ioID);
		mTab.setValue(MUNSPackingSlipSupplier.COLUMNNAME_C_BPartner_ID, invoice.getC_BPartner_ID());
		return msg;
	}

	/**
	 * 
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String inout(Properties ctx, int windowNo,
			GridTab mTab, GridField mField, Object value)
	{
		String msg = "";
		Integer M_InOut_ID = (Integer)value;
		if (isCalloutActive())
			return msg;
		
		if (null == M_InOut_ID || M_InOut_ID.intValue() <= 0)
			return msg;
		
		int inoutID = Integer.parseInt(Integer.toString(M_InOut_ID));
		MInOut io = new MInOut(ctx, inoutID, null);
		
		mTab.setValue(X_UNS_PackingSlipSupplier.COLUMNNAME_C_Order_ID, io.getC_Order_ID());
		//mTab.setValue(X_UNS_PackingSlipSupplier.COLUMNNAME_M_Warehouse_ID, io.getM_Warehouse_ID());
		
		String whereClause = "C_Invoice_ID IN (SELECT invLine.C_Invoice_ID FROM C_InvoiceLine invLine " +
				" WHERE invLine.M_InOutLine_ID IN (SELECT ioLine.M_InOutLine_ID FROM M_InOutLine ioLine " +
				" WHERE ioLine.M_InOut_ID=?))";
		MInvoice invoice = new Query(ctx, MInvoice.Table_Name, whereClause, null)
			.setParameters(io.get_ID())
			.first();
		if (invoice != null)
			mTab.setValue(X_UNS_PackingSlipSupplier.COLUMNNAME_C_Invoice_ID, invoice.get_ID());
		
		mTab.setValue(MUNSPackingSlipSupplier.COLUMNNAME_C_BPartner_ID, io.getC_BPartner_ID());
		return msg;
	}

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		String retValue = null;
		if (MUNSPackingSlipSupplier.COLUMNNAME_C_BPartner_ID.equals(mField.getColumnName()))
		{
			retValue = bpartner(ctx, WindowNo, mTab, mField, value);
		}
		/*
		if (MUNSPackingSlipSupplier.COLUMNNAME_C_Invoice_ID.equals(mField.getColumnName()))
		{
			retValue = invoice(ctx, WindowNo, mTab, mField, value);
		}
		if (MUNSPackingSlipSupplier.COLUMNNAME_M_InOut_ID.equals(mField.getColumnName()))
		{
			retValue = inout(ctx, WindowNo, mTab, mField, value);
		}
		*/
		return retValue;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String bpartner (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_BPartner_ID = (Integer)value;
		if (C_BPartner_ID == null || C_BPartner_ID.intValue() == 0)
			return "";

		String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
			+ "p.M_PriceList_ID,p.PaymentRule,p.POReference,"
			+ "p.SO_Description,p.IsDiscountPrinted,"
			+ "p.SO_CreditLimit-p.SO_CreditUsed AS CreditAvailable,"
			+ "l.C_BPartner_Location_ID,c.AD_User_ID "
			+ "FROM C_BPartner p, C_BPartner_Location l, AD_User c "
			+ "WHERE l.IsActive='Y' AND p.C_BPartner_ID=l.C_BPartner_ID(+)"
			+ " AND p.C_BPartner_ID=c.C_BPartner_ID(+)"
			+ " AND p.C_BPartner_ID=?";		//	1

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_BPartner_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				//	Location
				Integer ii = new Integer(rs.getInt("C_BPartner_Location_ID"));
				if (rs.wasNull())
					mTab.setValue("C_BPartner_Location_ID", null);
				else
					mTab.setValue("C_BPartner_Location_ID", ii);
				//	Contact
				ii = new Integer(rs.getInt("AD_User_ID"));
				if (rs.wasNull())
					mTab.setValue("AD_User_ID", null);
				else
					mTab.setValue("AD_User_ID", ii);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return "";
	}	//	bpartner


}
