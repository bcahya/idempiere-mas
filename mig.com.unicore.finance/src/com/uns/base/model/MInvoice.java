/**
 * 
 */
package com.uns.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_InvoicePaySchedule;
import org.compiere.model.MInvoicePaySchedule;
import org.compiere.model.MOrder;
import org.compiere.model.Query;
import org.compiere.util.DB;


/**
 * @author YAKA
 *
 */
public class MInvoice extends org.compiere.model.MInvoice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7445823786071731284L;
	private static Hashtable<Object, Object> s_cache;

	/**
	 * 
	 * @param ctx
	 * @param C_Invoice_ID
	 * @param trxName
	 */
	public MInvoice(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 	Create Invoice from Order
	 *	@param order order
	 *	@param C_DocTypeTarget_ID target document type
	 *	@param invoiceDate date or null
	 */
	public MInvoice (MOrder order, int C_DocTypeTarget_ID, Timestamp invoiceDate)
	{
		super(order, C_DocTypeTarget_ID, invoiceDate);
	}
	
	public MInvoicePaySchedule[] getInvPaySchedule(){
		String whereClauseFinal = "C_Invoice_ID=? ";
		List<MInvoicePaySchedule> list = new Query(getCtx(), I_C_InvoicePaySchedule.Table_Name, whereClauseFinal, get_TrxName())
										.setParameters(getC_Invoice_ID())
										.setOrderBy(I_C_InvoicePaySchedule.COLUMNNAME_C_InvoicePaySchedule_ID)
										.list();
		return list.toArray(new MInvoicePaySchedule[list.size()]);
	}
	
	public BigDecimal getGrandTotalInv() {
		// Find GrandTotal Invoice 
		String sql = "SELECT SUM(LineNetAmt) FROM C_InvoiceLine WHERE C_Invoice_ID = ? AND IsActive='Y' ";
		BigDecimal grandtotal = DB.getSQLValueBD(get_TrxName(), sql, getC_Invoice_ID());
		
		if (null == grandtotal)
			throw new AdempiereException("Grandtotal invoice "+ getDocumentNo() +" is ZERO.");
		 
		return grandtotal;
	}
	
	/**
	 * 	Get MInvoice from Cache
	 *	@param ctx context
	 *	@param C_Invoice_ID id
	 *	@return MInvoice
	 */
	public static MInvoice get (Properties ctx, int C_Invoice_ID)
	{
		Integer key = new Integer (C_Invoice_ID);
		MInvoice retValue = (MInvoice) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MInvoice (ctx, C_Invoice_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	} //	get
	
	public boolean isBilled()
	{
		String sql = "SELECT COUNT (*) FROM UNS_BillingGroup WHERE UNS_BillingGroup_ID IN "
				+ " (SELECT DISTINCT(UNS_BillingGroup_ID) FROM UNS_Billing WHERE UNS_Billing_ID IN "
				+ " (SELECT DISTINCT(UNS_Billing_ID) FROM UNS_BillingLine WHERE C_Invoice_ID = ?))"
				+ " AND (DocStatus = 'CO' OR DocStatus = 'CL')";
		int val = DB.getSQLValue(get_TrxName(), sql, get_ID());
		return val > 0;
	}
	
	public boolean isCanvas()
	{
		String sql = "SELECT COUNT(*) FROM UNS_Cvs_Rpt WHERE"
				+ " C_Invoice_ID = ?";
		return DB.getSQLValue(get_TrxName(), sql, get_ID()) > 0;
	}
}
