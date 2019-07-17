/**
 * 
 */
package com.uns.model.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MUNSVATInvLine;
import com.uns.model.MUNSVATLine;

/**
 * @author Burhani Adam
 *
 */
public class AdjustToCurrentInvoice extends SvrProcess{

	/**
	 * 
	 */
	public AdjustToCurrentInvoice() {
		// TODO Auto-generated constructor stub
	}
	
	private boolean includeDraftDoc = false;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("IncludeDraftDocument"))
				includeDraftDoc = para[i].getParameterAsBoolean();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		String sql = "SELECT vl.* FROM UNS_VATLine vl"
				+ " INNER JOIN UNS_VAT v ON v.UNS_VAT_ID = vl.UNS_VAT_ID"
				+ " INNER JOIN C_Invoice iv ON iv.C_Invoice_ID = vl.C_Invoice_ID"
				+ " INNER JOIN C_InvoiceTax t ON t.C_Invoice_ID = iv.C_Invoice_ID"
				+ " AND t.C_Tax_ID = v.C_Tax_ID"
				+ " WHERE v.UNS_VAT_ID = ? AND vl.IsManual = 'N'"
				+ " AND t.TaxAmt <> vl.TaxAmt";
		if(includeDraftDoc)
			sql += " AND iv.DocStatus NOT IN ('VO', 'RE')";
		else
			sql += " AND iv.DocStatus IN ('CO', 'CL')";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, getRecord_ID());
			rs = stmt.executeQuery();
			while(rs.next())
			{
				MUNSVATLine vLine = new MUNSVATLine(getCtx(), rs, get_TrxName());
				MUNSVATInvLine[] lines = MUNSVATInvLine.getByParent(getCtx(), vLine.get_ID(), null, get_TrxName());
				for(MUNSVATInvLine line : lines)
				{
					line.deleteEx(false, get_TrxName());
				}
				
				vLine.setGrandTotal(vLine.getC_Invoice().getGrandTotal());
				vLine.setReCreateLines(true);
				vLine.setBeforeTaxAmt(Env.ZERO);
				vLine.setTaxAmt(Env.ZERO);
				vLine.setRevisionBeforeTaxAmt(Env.ZERO);
				vLine.setRevisionTaxAmt(Env.ZERO);
				vLine.setRevisionAmt(Env.ZERO);
				vLine.setDifferenceTaxAmt(Env.ZERO);
				vLine.saveEx();
				
				count++;
			}
		} catch (SQLException e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return "Success adjust " + count + " " + (count > 1 ? "invoices." : "invoice.");
	}
}
