/**
 * 
 */
package com.unicore.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.GeneralCustomization;

/**
 * @author setyaka
 * 
 */
public class MUNSHandoverInv extends X_UNS_Handover_Inv {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5548749771161079119L;

	/**
	 * @param ctx
	 * @param UNS_Handover_Inv_ID
	 * @param trxName
	 */
	public MUNSHandoverInv(Properties ctx, int UNS_Handover_Inv_ID, String trxName) {
		super(ctx, UNS_Handover_Inv_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSHandoverInv(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MUNSHandoverInv get(int UNS_BillingLineResult_ID, String trxName)
	{
		String sql = "SELECT * FROM " + Table_Name + " WHERE " + COLUMNNAME_UNS_BillingLine_Result_ID + "=?";
		MUNSHandoverInv record = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, trxName);
			st.setInt(1, UNS_BillingLineResult_ID);
			rs = st.executeQuery();
			if (rs.next())
			{
				record = new MUNSHandoverInv(Env.getCtx(), rs, trxName);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			DB.close(rs, st);
		}
		
		return record;
	}

	public static MUNSHandoverInv getCreateByBillingGroup(MUNSBillingLineResult lineResult,
			int UNS_PR_Allocation_ID) {
		int get_ID = GeneralCustomization.get_ID(lineResult.get_TrxName(), MUNSHandoverInv.Table_Name,
						MUNSHandoverInv.COLUMNNAME_UNS_BillingLine_Result_ID, "=", lineResult.get_ID());
		if (get_ID > 0)
		{
			MUNSHandoverInv hInv = new MUNSHandoverInv(lineResult.getCtx(), get_ID, lineResult.get_TrxName());
			return hInv;
		}

		MInvoice inv = MInvoice.get(lineResult.getCtx(), lineResult.getC_Invoice_ID());

		MUNSHandoverInv hInv = new MUNSHandoverInv(lineResult.getCtx(), 0, lineResult.get_TrxName());

		hInv.setClientOrg(lineResult);
		hInv.setUNS_BillingLine_Result_ID(lineResult.get_ID());
		hInv.setC_Invoice_ID(inv.get_ID());
		hInv.setAcceptedBy(lineResult.getAcceptedBy());
		hInv.setSalesRep_ID(inv.getSalesRep_ID());
		hInv.setC_BPartner_ID(inv.getC_BPartner_ID());
		hInv.setUNS_PR_Allocation_ID(UNS_PR_Allocation_ID);

		if (lineResult.isPaid())
			hInv.setInvoiceCollectionType(INVOICECOLLECTIONTYPE_HandoverFinancePaid);
		else if (lineResult.isHandover())
			hInv.setInvoiceCollectionType(INVOICECOLLECTIONTYPE_HandoverCustomer);
		else
			hInv.setInvoiceCollectionType(INVOICECOLLECTIONTYPE_HandoverFinanceNotPaid);

		if (!hInv.save())
			throw new AdempiereException("Error while trying create Handover Invoice");

		return hInv;
	}
}
