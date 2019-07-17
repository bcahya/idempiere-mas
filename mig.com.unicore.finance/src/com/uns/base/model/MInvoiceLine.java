/**
 * 
 */
package com.uns.base.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MInOutLine;

import com.uns.model.MUNSInvoiceBC;
import com.uns.model.X_UNS_InvoiceBC;
import com.uns.model.factory.UNSFinanceModelFactory;

/**
 * @author YAKA
 *
 */
public class MInvoiceLine extends org.compiere.model.MInvoiceLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8845242439540026328L;
	private MUNSInvoiceBC[] m_lines;

	/**
	 * 	Get Invoice Line referencing InOut Line
	 *	@param sLine shipment line
	 *	@return (first) invoice line
	 */
	public static MInvoiceLine getOfInOutLine (MInOutLine sLine)
	{
		if (sLine == null)
			return null;
		final String whereClause = I_M_InOutLine.COLUMNNAME_M_InOutLine_ID+"=?";
		
		//List<MInvoiceLine> list = Query.(sLine.getCtx(),Table_Name,whereClause,sLine.get_TrxName())
		List<MInvoiceLine> list = Query.get(sLine.getCtx(), UNSFinanceModelFactory.EXTENSION_ID, Table_Name, whereClause, sLine.get_TrxName())
		.setParameters(sLine.getM_InOutLine_ID())
		.list();
		
		MInvoiceLine retValue = null;
		if (list.size() > 0) {
			retValue = list.get(0);
			if (list.size() > 1){
				//s_log.warning("More than one C_InvoiceLine of " + sLine);
			}
		}

		return retValue;
	}	//	getOfInOutLine

	public MInvoiceLine (MInvoice invoice)
	{
		super(invoice);
	}
	/**
	 * 
	 * @param ctx
	 * @param C_InvoiceLine_ID
	 * @param trxName
	 */
	public MInvoiceLine(Properties ctx, int C_InvoiceLine_ID, String trxName) {
		super(ctx, C_InvoiceLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoiceLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		// TODO Auto-generated method stub
		/**
		if (newRecord) {
			int C_OrderLine_ID = getC_OrderLine_ID();
		
			if (C_OrderLine_ID > 0) {
				MOrderLine orderLine = new MOrderLine(getCtx(), C_OrderLine_ID, get_TrxName());
				setM_AttributeSetInstance_ID(orderLine.getM_AttributeSetInstance_ID());
			}
		}
		
		MAttributeSetInstance.initAttributeValuesFrom(
				this, COLUMNNAME_M_Product_ID, COLUMNNAME_M_AttributeSetInstance_ID, get_TrxName());
		*/
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		// TODO Auto-generated method stub
		
		return super.afterSave(newRecord, success);
	}

	@Override
	protected boolean afterDelete(boolean success) {
		// TODO Auto-generated method stub
		return super.afterDelete(success);
	}

	/**
	 * 	Get Invoice Lines of Invoice
	 * 	@param whereClause starting with AND
	 * 	@return lines
	 */
	private MUNSInvoiceBC[] getLines (String whereClause)
	{
		String whereClauseFinal = "C_InvoiceLine_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		List<MUNSInvoiceBC> list = Query.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID, X_UNS_InvoiceBC.Table_Name, whereClauseFinal, get_TrxName())
										.setParameters(getC_InvoiceLine_ID())
										.setOrderBy(X_UNS_InvoiceBC.COLUMNNAME_UNS_InvoiceBC_ID)
										.list();
		return list.toArray(new MUNSInvoiceBC[list.size()]);
	}	//	getLines

	/**
	 * 	Get Invoice Lines
	 * 	@param requery
	 * 	@return lines
	 */
	public MUNSInvoiceBC[] getLines (boolean requery)
	{
		if (m_lines == null || m_lines.length == 0 || requery)
			m_lines = getLines(null);
		set_TrxName(m_lines, get_TrxName());
		return m_lines;
	}	//	getLines

	/**
	 * 	Get Lines of Invoice
	 * 	@return lines
	 */
	public MUNSInvoiceBC[] getLines()
	{
		return getLines(false);
	}	//	getLines
	
}
