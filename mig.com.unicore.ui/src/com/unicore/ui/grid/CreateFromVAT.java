/**
 * 
 */
package com.unicore.ui.grid;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.IStatusBar;
import org.compiere.grid.CreateFrom;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoiceTax;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import com.unicore.model.MUNSCvsRpt;
import com.unicore.model.MUNSCvsRptCustomer;
import com.unicore.model.MUNSCvsRptProduct;
import com.uns.base.model.MInvoice;
import com.uns.model.MUNSVAT;
import com.uns.model.MUNSVATInvLine;
import com.uns.model.MUNSVATLine;

/**
 * @author Burhani Adam
 *
 */
public class CreateFromVAT extends CreateFrom {

	/**
	 * @param gridTab
	 */
	public CreateFromVAT(GridTab gridTab) {
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
		return false;
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
		int UNS_VAT_ID = ((Integer) getGridTab().getValue("UNS_VAT_ID")).intValue();
		MUNSVAT vat = new MUNSVAT(Env.getCtx(), UNS_VAT_ID, trxName);
		
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{
				KeyNamePair pp = (KeyNamePair) miniTable.getValueAt(i, 1); // Product
				int C_Invoice_ID = pp.getKey();
				MInvoice inv = new MInvoice(Env.getCtx(), C_Invoice_ID, trxName);
				boolean isReplacement = Util.isEmpty((String)miniTable.getValueAt(i, 5), true) ? false : true;
				if(!inv.isCanvas())
				{
					MUNSVATLine line = new MUNSVATLine(vat);
					line.setAuto(true);
					line.setisReplacement(isReplacement);
					if(isReplacement)
					{
						line.setTaxInvoiceNo((String) miniTable.getValueAt(i, 5));
						String sql = "SELECT MAX(vl.UNS_VATLine_ID) FROM UNS_VATLine vl"
								+ " WHERE vl.C_Invoice_ID = ? AND EXISTS"
								+ " (SELECT 1 FROM UNS_VAT v WHERE v.DocStatus IN ('CO', 'CL')"
								+ " AND v.UNS_VAT_ID = vl.UNS_VAT_ID)";
						int ref = DB.getSQLValue(trxName, sql, C_Invoice_ID);
						if(ref <= 0)
							throw new AdempiereException("Not found reference reporting"
									+ " for invoice " + inv.getDocumentNo());
						else
						{
							MUNSVATLine reference = new MUNSVATLine(Env.getCtx(), ref, trxName);
							line.copyFrom(vat, reference);
						}
					}
					else
						line.setInvoice(inv);
					
					if(!line.save())
					{
						log.saveError("Error", "Failed when trying save lines");
						return false;
					}
				}
				else
				{
					//TODO
//					if(isReplacement)
//					{
//						String sql = "SELECT MAX(vl.UNS_VATLine_ID) FROM UNS_VATLine vl"
//								+ " WHERE vl.C_Invoice_ID = ? AND EXISTS"
//								+ " (SELECT 1 FROM UNS_VAT v WHERE v.DocStatus IN ('CO', 'CL')"
//								+ " AND v.UNS_VAT_ID = vl.UNS_VAT_ID) AND ReferenceNo = ?";
//						int ref = DB.getSQLValue(trxName, sql, C_Invoice_ID);
//						if(ref <= 0)
//							throw new AdempiereException("Not found reference reporting"
//									+ " for invoice " + inv.getDocumentNo());
//						else
//						{
//							MUNSVATLine reference = new MUNSVATLine(Env.getCtx(), ref, trxName);
//							line.copyFrom(vat, reference);
//						}
//					}
//					else
//					{
						MUNSCvsRpt rpt = MUNSCvsRpt.getOfInvoice(Env.getCtx(), inv.get_ID(), trxName);
						if(rpt == null)
							return false;
						MUNSCvsRptCustomer[] customer = rpt.getLines(true);
						for(int j=0;j<customer.length;j++)
						{
							setInvoice(inv, customer[j], vat, trxName, j);
						}
//					}
				}
			}
		}
		
		return true;
	}

	/**
	 *  Load Data - Invoice Not Reported
	 *  @param UNS_VAT_ID
	 *  @param C_BPartner_ID
	 *  @param C_BPartner_Group_ID
	 */
	protected Vector<Vector<Object>> getInvoiceData (int UNS_VAT_ID, int C_BPartner_ID,
			int C_BPartner_Group_ID, int C_BankAccount_ID, boolean isPKP, boolean isReplacement,
			int Region_ID, int M_Product_ID)
	{
		MUNSVAT vat = new MUNSVAT(Env.getCtx(), UNS_VAT_ID, null);

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuilder sql = new StringBuilder("SELECT iv.DocumentNo, iv.C_Invoice_ID,"
				+ " iv.DateInvoiced, bpl.BPartnerName, COALESCE(bpl.TaxName, ''),"
				+ " iv.TotalLines, SUM(it.TaxAmt), iv.GrandTotal FROM C_Invoice iv"
				+ " INNER JOIN C_InvoiceTax it ON it.C_Invoice_ID = iv.C_Invoice_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = iv.C_BPartner_ID"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = iv.C_BPartner_Location_ID"
				+ " INNER JOIN C_DocType dt ON dt.C_DocType_ID = iv.C_DocType_ID"
				+ " WHERE iv.DocStatus IN ('CO', 'CL') AND iv.AD_Org_ID IN (SELECT oi.AD_Org_ID FROM AD_OrgInfo oi WHERE"
				+ " oi.Parent_Org_ID=?) AND iv.DateInvoiced BETWEEN ? AND ?"
				+ " AND it.C_Tax_ID = ?");
		
		sql.append(" AND dt.C_DocType_ID IN (SELECT t.C_DocType_ID FROM C_DocType t"
				+ " WHERE t.C_DocType_ID = dt.C_DocType_ID AND t.DocBaseType = '");
		
		if(vat.isSOTrx())
		{
			if(vat.isCreditMemo())
				sql.append(MDocType.DOCBASETYPE_ARCreditMemo);
			else
				sql.append(MDocType.DOCBASETYPE_ARInvoice);
		}
		else
		{
			if(vat.isCreditMemo())
				sql.append(MDocType.DOCBASETYPE_APCreditMemo);
			else
				sql.append(MDocType.DOCBASETYPE_APInvoice);
		}
		sql.append("')");
		
		if(isPKP)
			sql.append(" AND TRIM(bpl.TaxName) <> ''");
		else
			sql.append(" AND TRIM(bpl.TaxName) IS NULL");
			
		
		if(!isReplacement) 
			sql.append(" AND NOT EXISTS (SELECT 1 FROM UNS_VATLine l WHERE l.C_Invoice_ID = iv.C_Invoice_ID"
						+ " AND EXISTS (SELECT 1 FROM UNS_VAT vat WHERE vat.UNS_VAT_ID = l.UNS_VAT_ID AND vat.DocStatus"
						+ " NOT IN ('VO', 'RE')))");
		else 
			sql.append(" AND EXISTS (SELECT 1 FROM UNS_VATLine l WHERE l.C_Invoice_ID = iv.C_Invoice_ID"
					+ " AND EXISTS (SELECT 1 FROM UNS_VAT vat WHERE vat.UNS_VAT_ID = l.UNS_VAT_ID AND vat.DocStatus"
					+ " IN ('CO', 'CL')))");
		
		if(C_BPartner_ID > 0)
			sql.append(" AND iv.C_BPartner_ID = ").append(C_BPartner_ID);
		
		if(C_BPartner_Group_ID > 0)
			sql.append(" AND bp.C_BP_Group_ID = ").append(C_BPartner_Group_ID);
		
		if(C_BankAccount_ID > 0)
			sql.append(" AND EXISTS (SELECT 1 FROM C_PaymentAllocate pa WHERE"
					+ " pa.C_Invoice_ID = iv.C_Invoice_ID AND EXISTS"
					+ " (SELECT 1 FROM C_Payment p WHERE p.C_Payment_ID = pa.C_Payment_ID"
					+ " AND p.DocStatus IN ('CO', 'CL') AND p.C_BankAccount_ID = ").append(C_BankAccount_ID)
					.append("))");
		if(Region_ID > 0)
			sql.append(" AND iv.AD_Org_ID IN (SELECT sr.AD_Org_ID FROM AD_Org sr WHERE sr.C_SalesRegion_ID = ").append(Region_ID)
			.append(")");
		
		if(M_Product_ID > 0)
			sql.append(" AND EXISTS (SELECT 1 FROM C_InvoiceLine il WHERE il.C_Invoice_ID = iv.C_Invoice_ID AND"
					+ " il.M_Product_ID = ").append(M_Product_ID).append(")");
		
		sql.append(" GROUP BY iv.C_Invoice_ID, bpl.C_BPartner_Location_ID");
		sql.append(" ORDER BY iv.DateInvoiced, iv.DocumentNo ASC");
		
		if (log.isLoggable(Level.FINER)) log.finer(sql.toString());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, vat.getAD_Org_ID());
			pstmt.setTimestamp(2, vat.getDateFrom());
			pstmt.setTimestamp(3, vat.getDateTo());
			pstmt.setInt(4, vat.getC_Tax_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false));           //  0-Selection
				KeyNamePair pp = new KeyNamePair(rs.getInt(2), rs.getString(1).trim());
				line.add(pp);
				line.add(rs.getTimestamp(3));
				line.add(rs.getString(4));
				line.add(rs.getString(5));
				String getTaxNo = "SELECT COALESCE(vl.TaxInvoiceNo,'') FROM UNS_VatLine vl WHERE vl.C_Invoice_ID = ? AND vl.isReplacement = 'N'"
						+ " AND EXISTS (SELECT 1 FROM UNS_VAT vat WHERE vat.UNS_VAT_ID = vl.UNS_VAT_ID AND vat.DocStatus"
						+ " IN ('CO', 'CL'))";
				String taxNo = DB.getSQLValueString(null, getTaxNo, pp.getKey());
				line.add(taxNo);
				line.add(rs.getBigDecimal(6));
				line.add(rs.getBigDecimal(7));
				line.add(rs.getBigDecimal(8));

				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			//throw new DBException(e, sql.toString());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return data;
	}
	
	protected Vector<String> getOISColumnNames(boolean isReplacement)
	{
		int columns = 9;
		Vector<String> columnNames = new Vector<String>(columns);
	    columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
	    columnNames.add(Msg.translate(Env.getCtx(), "DocumentNo"));
	    columnNames.add(Msg.translate(Env.getCtx(), "DateInvoiced"));
	    columnNames.add(Msg.translate(Env.getCtx(), "BPartnerName"));
	    columnNames.add(Msg.translate(Env.getCtx(), "TaxName")); 
	   	columnNames.add(Msg.translate(Env.getCtx(), "TaxInvoiceNo"));
	    columnNames.add(Msg.translate(Env.getCtx(), "TotalLines"));
	    columnNames.add(Msg.translate(Env.getCtx(), "TaxAmt"));
	    columnNames.add(Msg.translate(Env.getCtx(), "GrandTotal"));
	    
	    return columnNames;	
	}
	
	protected void configureMiniTable (IMiniTable miniTable, boolean isReplacement)
	{
		int i = 0;
		miniTable.setColumnClass(i++, Boolean.class, false);	//  Selection
		miniTable.setColumnClass(i++, String.class, true);		//  DocumentNo
		miniTable.setColumnClass(i++, Timestamp.class, true);	//  DateInvoiced
		miniTable.setColumnClass(i++, String.class, true);		//  BPartnerName
		miniTable.setColumnClass(i++, String.class, true);		//  TaxName
		miniTable.setColumnClass(i++, String.class, true);	// TaxInvoiceNo (if is replacement)
		miniTable.setColumnClass(i++, BigDecimal.class, true);	//  TotalLines
		miniTable.setColumnClass(i++, BigDecimal.class, true);	//  TaxAmt
		miniTable.setColumnClass(i++, BigDecimal.class, true);	//  GrandTotal
		
		//  Table UI
		miniTable.autoSize();	
	}
	
	public void setInvoice(MInvoice inv, MUNSCvsRptCustomer customer, MUNSVAT vat, String trxName, int sequence)
	{
		MUNSVATLine line = new MUNSVATLine(Env.getCtx(), 0, trxName);
		line.setUNS_VAT_ID(vat.get_ID());
		line.setAD_Org_ID(customer.getAD_Org_ID());
		line.setC_Invoice_ID(inv.get_ID());
		line.setTaxSerialNo("000000000000000");
		line.setReferenceNo(inv.getDocumentNo() + "(" + (sequence + 1) + ")");
		line.setDateInvoiced(inv.getDateInvoiced());
		line.setC_BPartner_ID(inv.getC_BPartner_ID());
		line.setC_BPartner_Location_ID(inv.getC_BPartner_Location_ID());
		line.setTaxName(customer.getUNS_CvsCustomer().getName());
		line.setTaxAddress(customer.getUNS_CvsCustomer_Location().getName());
		line.setIgnoreValidation(true);
		line.setIsCanvas(true);
		line.saveEx();
		
		for(MUNSCvsRptProduct product : customer.getLines(true))
		{
			MUNSVATInvLine lines = MUNSVATInvLine.getOfProductParent(Env.getCtx(), line.get_ID(), 
					product.getM_Product_ID(), trxName);
			if(lines == null)
			{
				lines =	new MUNSVATInvLine(Env.getCtx(), 0, trxName);
				lines.setUNS_VATLine_ID(line.get_ID());
				lines.setAD_Org_ID(line.getAD_Org_ID());
				lines.setM_Product_ID(product.getM_Product_ID());
			}
			lines.setQtyInvoiced(lines.getQtyInvoiced().add(product.getQtySold()));
			BigDecimal price = getPrice(inv, vat, product.getM_Product_ID());
			lines.setBeforeTaxAmt(price);
			lines.setRevisionBeforeTaxAmt(price);
			lines.setC_Tax_ID(vat.getC_Tax_ID());
			lines.setC_UOM_ID(product.getC_UOM_ID());
			lines.saveEx();
		}
	}
	
	private BigDecimal getPrice(MInvoice inv, MUNSVAT vat, int M_Product_ID)
	{
		BigDecimal price = mapPrice.get(M_Product_ID);
		MInvoiceTax[] taxs  = MInvoiceTax.getByInvoice(Env.getCtx(), inv.get_ID(), null);
		boolean includeTax = false;
		BigDecimal rate = Env.ZERO;
		for(MInvoiceTax tax : taxs)
		{
			if(tax.getC_Tax_ID() == vat.getC_Tax_ID())
			{
				includeTax = tax.isTaxIncluded();
				rate = tax.getC_Tax().getRate();
				rate = rate.divide(Env.ONEHUNDRED);
				rate = rate.add(Env.ONE);
			}
		}
		if(price == null)
		{
			String sql = "SELECT SUM(QtyInvoiced), SUM(LineNetAmt) FROM C_InvoiceLine WHERE M_Product_ID = ?"
					+ " AND C_Invoice_ID = ?";
			List<Object> oo = DB.getSQLValueObjectsEx(null, sql, new Object[]{M_Product_ID, inv.get_ID()});
			BigDecimal qty = oo.get(0) == null ? Env.ZERO : (BigDecimal) oo.get(0);
			if(qty.compareTo(Env.ZERO) == 0)
				return Env.ZERO;
			
			BigDecimal total = oo.get(1) == null ? Env.ZERO : (BigDecimal) oo.get(1);
			if(total.compareTo(Env.ZERO) == 0)
				return Env.ZERO;
			
			if(includeTax)
				total = total.divide(rate);
			
			price = total.divide(qty);
			price = price.setScale(2, RoundingMode.HALF_UP);
			mapPrice.put(M_Product_ID, price);
		}
		
		return price;
	}
	
	private Hashtable<Integer, BigDecimal> mapPrice = new Hashtable<>();
}