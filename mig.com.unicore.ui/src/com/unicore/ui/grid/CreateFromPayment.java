/**
 * 
 */
package com.unicore.ui.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.IStatusBar;
import org.compiere.grid.CreateFrom;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.globalqss.model.LCO_MInvoice;

import com.unicore.base.model.MInvoice;

/**
 * @author Burhani Adam
 *
 */
public class CreateFromPayment extends CreateFrom {

	/**
	 * @param gridTab
	 */
	public CreateFromPayment(GridTab gridTab) {
		super(gridTab);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.ICreateFrom#getWindow()
	 */
	@Override
	public Object getWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#dynInit()
	 */
	@Override
	public boolean dynInit() throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#info(org.compiere.minigrid.IMiniTable, org.compiere.apps.IStatusBar)
	 */
	@Override
	public void info(IMiniTable miniTable, IStatusBar statusBar) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.grid.CreateFrom#save(org.compiere.minigrid.IMiniTable, java.lang.String)
	 */
	@Override
	public boolean save(IMiniTable miniTable, String trxName)
	{
		int C_Payment_ID = ((Integer) getGridTab().getValue("C_Payment_ID")).intValue();
		MPayment payment = new MPayment(Env.getCtx(), C_Payment_ID, trxName);
		
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair) miniTable.getValueAt(i, 8);
				org.compiere.model.MInvoice iv = MInvoice.get(Env.getCtx(), pp.getKey());
				BigDecimal amt = (BigDecimal) miniTable.getValueAt(i, 1);
				BigDecimal writeOff = (BigDecimal) miniTable.getValueAt(i, 2);
				BigDecimal discAmt = (BigDecimal) miniTable.getValueAt(i, 3);
				BigDecimal overUnderAmt = (BigDecimal) miniTable.getValueAt(i, 4);
				BigDecimal whAmt = (BigDecimal) miniTable.getValueAt(i, 5);
				BigDecimal openAmt = (BigDecimal) miniTable.getValueAt(i, 6);
				MPaymentAllocate all = new MPaymentAllocate(payment.getCtx(), 0, trxName);
				all.setC_Payment_ID(payment.get_ID());
				all.setAD_Org_ID(iv.getAD_Org_ID());
				all.setC_Invoice_ID(iv.get_ID());
				all.setInvoiceAmt(openAmt);
				all.setAmount(amt);
				all.setPayToOverUnderAmount(amt);
				all.setWriteOffAmt(writeOff);
				all.setWithholdingAmt(whAmt);
				all.setDiscountAmt(discAmt);
				all.setOverUnderAmt(overUnderAmt);
				all.saveEx();
			}
		}
		return true;
	}
	
	protected Vector<Vector<Object>> getInvoiceData(int C_Payment_ID)
	{
		MPayment payment = new MPayment(Env.getCtx(), C_Payment_ID, null);
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		String sql = "SELECT InvoiceOpen(iv.C_Invoice_ID,0) AS OpenAmt, 0, 0, 0, iv.GrandTotal AS GrandTotal, "
				+ " iv.DocumentNo AS InvoiceNo, iv.C_Invoice_ID, iv.DateInvoiced AS DateInvoiced,"
				+ " DATE_PART('day', now() - (iv.DateInvoiced + pt.NetDays)) AS Aging, COALESCE(usr.RealName, usr.Name) AS Sales"
				+ " FROM C_Invoice iv"
				+ " INNER JOIN C_PaymentTerm pt ON pt.C_PaymentTerm_ID = iv.C_PaymentTerm_ID"
				+ " INNER JOIN AD_User usr ON usr.AD_User_ID = iv.SalesRep_ID"
				+ " WHERE iv.C_BPartner_ID = ? AND iv.DocStatus IN ('CO', 'CL')"
				+ " AND NOT EXISTS (SELECT 1 FROM C_PaymentAllocate pa WHERE pa.C_Payment_ID = ?"
				+ " AND pa.C_Invoice_ID = iv.C_Invoice_ID)"
				+ " AND iv.IsPaid = 'N' AND iv.IsSOTrx = ? ORDER BY Aging DESC";
		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		try {
			stmt = DB.prepareStatement(sql, null);
			stmt.setInt(1, payment.getC_BPartner_ID());
			stmt.setInt(2, payment.get_ID());
			stmt.setString(3, payment.isReceipt() ? "Y" : "N");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Vector<Object> line = new Vector<Object>();
				KeyNamePair pp = new KeyNamePair(rs.getInt(7), rs.getString(6));
				int C_Invoice_ID = pp.getKey();
				BigDecimal PercentOpen = Env.ZERO;
				BigDecimal BaseWithholdingAmt = Env.ZERO;
				BigDecimal WithholdingAmt = Env.ZERO;

				try {
					PercentOpen = LCO_MInvoice.getPercentInvoiceOpenAmt(C_Invoice_ID, 0);
					BaseWithholdingAmt = LCO_MInvoice.getInvoiceBaseWithholding(pp.getKey());
				} catch (SQLException e) {
					log.log (Level.SEVERE, "", e);
					throw new AdempiereException(e.getLocalizedMessage());
				}
				WithholdingAmt = BaseWithholdingAmt.multiply(PercentOpen);
				line.add(new Boolean(false));
				line.add(rs.getBigDecimal(1).subtract(WithholdingAmt));
				line.add(Env.ZERO);
				line.add(Env.ZERO);
				line.add(Env.ZERO);
				line.add(WithholdingAmt);
				line.add(rs.getBigDecimal(1));
				line.add(rs.getBigDecimal(5));
				line.add(pp);
				line.add(rs.getTimestamp(8));
				line.add(rs.getInt(9));
				line.add(rs.getString(10));
				
				data.add(line);
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			DB.close(rs, stmt);
			rs = null; stmt = null;
		}
		return data;
	}
	
	protected Vector<String> getOISColumnNames()
	{
		Vector<String> columnNames = new Vector<String>(12);
	    columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
	    columnNames.add("Paid");
	    columnNames.add("Write Off");
	    columnNames.add("Discount");
	    columnNames.add("Over Under");
	    columnNames.add("Withholding");
	    columnNames.add(Msg.translate(Env.getCtx(), "Balance"));
	    columnNames.add(Msg.translate(Env.getCtx(), "GrandTotal"));
	    columnNames.add("Invoice");
	    columnNames.add("Date");
	    columnNames.add(Msg.translate(Env.getCtx(), "Aging"));
	    columnNames.add("Sales");
	    
	    return columnNames;	
	}
	
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);		//Select
		miniTable.setColumnClass(1, BigDecimal.class, false);		//  Org
		miniTable.setColumnClass(2, BigDecimal.class, false);		//  Partner
		miniTable.setColumnClass(3, BigDecimal.class, false);		//  DocumentNo
		miniTable.setColumnClass(4, BigDecimal.class, false);		//  DateInvoiced
		miniTable.setColumnClass(5, BigDecimal.class, true);	//  GrandTotal
		miniTable.setColumnClass(6, BigDecimal.class, true);	//  OpenAmt
		miniTable.setColumnClass(7, BigDecimal.class, true);	//  OpenAmt
		miniTable.setColumnClass(8, String.class, true);		//  Sales
		miniTable.setColumnClass(9, Timestamp.class, true);		//  Status
		miniTable.setColumnClass(10, Integer.class, true);		//  Sales
		miniTable.setColumnClass(11, String.class, true);		//  Sales
		
		//  Table UI
		miniTable.autoSize();
	}
}