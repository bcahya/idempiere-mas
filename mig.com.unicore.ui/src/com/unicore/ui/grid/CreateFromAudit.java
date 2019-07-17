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

import org.compiere.apps.IStatusBar;
import org.compiere.grid.CreateFrom;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MDocType;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import com.uns.base.model.MInvoice;
import com.uns.model.MUNSAudit;
import com.uns.model.MUNSAuditDocument;
import com.uns.model.MUNSAuditPartner;

/**
 * @author Burhani Adam
 *
 */
public class CreateFromAudit extends CreateFrom {

	/**
	 * @param gridTab
	 */
	public CreateFromAudit(GridTab gridTab) {
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
		int UNS_Audit_ID = ((Integer) getGridTab().getValue("UNS_Audit_ID")).intValue();
		MUNSAudit audit = new MUNSAudit(Env.getCtx(), UNS_Audit_ID, trxName);
		
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{
				MUNSAuditPartner partner = null;
				KeyNamePair pp = (KeyNamePair) miniTable.getValueAt(i, 3); // Product
				int C_Invoice_ID = pp.getKey();
				MInvoice inv = new MInvoice(Env.getCtx(), C_Invoice_ID, trxName);
				partner = MUNSAuditPartner.getOfParentAndPartner(audit, inv.getC_BPartner_ID());
				if(null == partner)
				{
					partner = new MUNSAuditPartner(Env.getCtx(), 0, trxName);
					partner.setUNS_Audit_ID(audit.get_ID());
					partner.setAD_Org_ID(audit.getAD_Org_ID());
					partner.setC_BPartner_ID(inv.getC_BPartner_ID());
					partner.setC_BPartner_Location_ID(inv.getC_BPartner_Location_ID());
					partner.setC_PaymentTerm_ID(partner.getC_BPartner().getC_PaymentTerm_ID());
					partner.saveEx();
				}
				
				MUNSAuditDocument docs = new MUNSAuditDocument(Env.getCtx(), 0, trxName);
				docs.setUNS_AuditPartner_ID(partner.get_ID());
				docs.setInvoice(inv);
				docs.setOpenAmt((BigDecimal)miniTable.getValueAt(i, 6));
				docs.saveEx();
			}
		}
		
		return true;
	}
	
	/**
	 *  Load Data - Invoice
	 *  @param UNS_Audit_ID
	 *  @param C_BPartner_ID
	 *  @param C_BPartner_Group_ID
	 *  @param SalesRep_ID
	 *  @param UNS_Rayon_ID
	 *  @param isReturn
	 *  @param isVoided
	 *  @param PackingListNO
	 */
	protected Vector<Vector<Object>> getInvoiceData (int UNS_Audit_ID, int C_BPartner_ID,
			int C_BPartner_Group_ID, int SalesRep_ID, int UNS_Rayon_ID, boolean isReturn, boolean isCantSelected,
			Timestamp dateFrom, Timestamp dateTo, String DocumentNo, String PackingListNo, boolean isResultAudit)
	{
		MUNSAudit audit = new MUNSAudit(Env.getCtx(), UNS_Audit_ID, null);

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuilder sql = new StringBuilder("SELECT org.Value, bpl.BPartnerName, iv.DocumentNo,"
				+ " iv.C_Invoice_ID, iv.DateInvoiced, iv.GrandTotal, invoiceopen(iv.C_Invoice_ID, 0)"
				+ " + (SELECT COALESCE(SUM(pa.Amount + pa.WriteOffAmt), 0) FROM"
				+ " C_PaymentAllocate pa WHERE pa.C_Invoice_ID = iv.C_Invoice_ID AND EXISTS"
				+ " (SELECT 1 FROM C_Payment p WHERE p.C_Payment_ID = pa.C_Payment_ID AND p.DocStatus IN ('CO', 'CL')"
				+ " AND p.DateTrx > ?)),"
				+ " iv.DocStatus, usr.Name, bp.C_BPartner_ID FROM C_Invoice iv"
				+ " INNER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = iv.C_BPartner_Location_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = bpl.C_BPartner_ID"
				+ " INNER JOIN AD_Org org ON org.AD_Org_ID = iv.AD_Org_ID"
				+ " INNER JOIN AD_User usr ON usr.AD_User_ID = iv.SalesRep_ID"
				+ " WHERE org.C_SalesRegion_ID = (SELECT C_SalesRegion_ID FROM AD_Org WHERE AD_Org_ID=?)"
				+ " AND iv.IsSOTrx = ? AND iv.C_DocType_ID = ? AND iv.DateInvoiced BETWEEN ? AND ? AND NOT EXISTS"
				+ " (SELECT 1 FROM UNS_AuditDocument ad WHERE ad.C_Invoice_ID = iv.C_Invoice_ID AND EXISTS"
				+ " (SELECT 1 FROM UNS_AuditPartner ap WHERE ap.UNS_AuditPartner_ID = ad.UNS_AuditPartner_ID"
				+ " AND ap.UNS_Audit_ID=?))");
		
		if(!Util.isEmpty(DocumentNo))
			sql.append(" AND iv.Documentno = '").append(DocumentNo).append("'");
		
		if(!isCantSelected)
		{
			sql.append(" AND iv.DocStatus IN ('CO', 'CL') AND (iv.isPaid = 'N' OR (iv.isPaid = 'Y' AND EXISTS"
					+ " (SELECT 1 FROM C_PaymentAllocate pa WHERE pa.C_Invoice_ID = iv.C_Invoice_ID AND EXISTS"
					+ " (SELECT 1 FROM C_Payment p WHERE pa.C_Payment_ID = p.C_Payment_ID AND p.DocStatus IN ('CO', 'CL')"
					+ " AND p.DateTrx > '").append(audit.getDateAcct()).append("'))))");
		}
		else
			sql.append(" AND (iv.DocStatus NOT IN ('CO', 'CL') OR iv.isPaid = 'Y')");
		
		if(C_BPartner_ID > 0)
			sql.append(" AND bp.C_BPartner_ID = ").append(C_BPartner_ID);
		
		if(C_BPartner_Group_ID > 0)
			sql.append(" AND bp.C_BP_Group_ID = ").append(C_BPartner_Group_ID);
		
		if(SalesRep_ID > 0)
			sql.append(" AND usr.AD_User_ID = ").append(SalesRep_ID);
		
		if(UNS_Rayon_ID > 0)
			sql.append(" AND bpl.UNS_Rayon_ID = ").append(UNS_Rayon_ID);
		
		if(!Util.isEmpty(PackingListNo))
			sql.append(" AND iv.C_invoice_id IN(SELECT po.C_Invoice_ID FROM UNS_PackingList_Order po WHERE "
					+ "po.UNS_PackingList_ID IN(SELECT pl.UNS_PackingList_ID FROM UNS_PackingList pl WHERE "
					+ "pl.Documentno ='").append(PackingListNo).append("'))");
//				
//		if(isResultAudit)
//			sql.append(" AND (EXISTS (SELECT 1 FROM UNS_AuditDocument ad"
//					+ " WHERE ad.C_Invoice_ID = iv.C_Invoice_ID))");
		
		sql.append(" ORDER BY bpl.BPartnerName, iv.DateInvoiced, iv.DocumentNo");
		
		if (log.isLoggable(Level.FINER)) log.finer(sql.toString());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			int dt = 0;
			if(isSOTrx && !isReturn)
				dt = MDocType.getDocType(MDocType.DOCBASETYPE_ARInvoice);
			else if(!isSOTrx && !isReturn)
				dt = MDocType.getDocType(MDocType.DOCBASETYPE_APInvoice);
			else if(isSOTrx && isReturn)
				dt = MDocType.getDocType(MDocType.DOCBASETYPE_ARCreditMemo);
			else
				dt = MDocType.getDocType(MDocType.DOCBASETYPE_APCreditMemo);
			
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setTimestamp(1, audit.getDateAcct());
			pstmt.setInt(2, audit.getAD_Org_ID());
			pstmt.setString(3, audit.isSOTrx() ? "Y" : "N");
			pstmt.setInt(4, dt);
			pstmt.setTimestamp(5, dateFrom);
			pstmt.setTimestamp(6, dateTo);
			pstmt.setInt(7, audit.get_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if(isResultAudit)
				{
					String sql2 = "SELECT MAX(ad.DateAcct) FROM UNS_Audit ad WHERE EXISTS"
							+ " (SELECT 1 FROM UNS_AuditPartner ap WHERE ap.UNS_Audit_ID"
							+ " = ad.UNS_Audit_ID AND ap.C_BPartner_ID = ?)"
							+ " AND ad.DocStatus = 'CO'";
					Timestamp lastAudit = DB.getSQLValueTS(null, sql2, rs.getInt(10));
					String sqll = "SELECT 1 FROM UNS_AuditDocument WHERE C_Invoice_ID = ?";
					boolean exists = DB.getSQLValue(null, sqll, rs.getInt(4)) > 0;
					if(!exists)
					{
						Timestamp dateInv = rs.getTimestamp(5);
						if(lastAudit != null && dateInv.before(lastAudit))
							continue;
					}
				}
				Vector<Object> line = new Vector<Object>();
				line.add(new Boolean(false));
				line.add(rs.getString(1));
				line.add(rs.getString(2));
				KeyNamePair pp = new KeyNamePair(rs.getInt(4), rs.getString(3).trim());
				line.add(pp);
				line.add(rs.getTimestamp(5));
				line.add(rs.getBigDecimal(6));
				line.add(rs.getBigDecimal(7));
				line.add(rs.getString(8));
				line.add(rs.getString(9));
				
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
	
	protected Vector<String> getOISColumnNames()
	{
		Vector<String> columnNames = new Vector<String>(9);
	    columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
	    columnNames.add(Msg.translate(Env.getCtx(), "Org"));
	    columnNames.add(Msg.translate(Env.getCtx(), "Partner"));
	    columnNames.add(Msg.translate(Env.getCtx(), "DocumentNo"));
	    columnNames.add(Msg.translate(Env.getCtx(), "DateInvoiced"));
	    columnNames.add(Msg.translate(Env.getCtx(), "GrandTotal"));
	    columnNames.add(Msg.translate(Env.getCtx(), "OpenAmt"));
	    columnNames.add(Msg.translate(Env.getCtx(), "Status"));
	    columnNames.add(Msg.translate(Env.getCtx(), "Sales"));
	    
	    return columnNames;
	}
	
	protected void configureMiniTable (IMiniTable miniTable, boolean isCantSelected)
	{
		if(!isCantSelected)
			miniTable.setColumnClass(0, Boolean.class, false);		//  Selection
		else
			miniTable.setColumnClass(0, Boolean.class, true);		//  Selection-readOnly
		
		miniTable.setColumnClass(1, String.class, true);		//  Org
		miniTable.setColumnClass(2, String.class, true);		//  Partner
		miniTable.setColumnClass(3, String.class, true);		//  DocumentNo
		miniTable.setColumnClass(4, Timestamp.class, true);		//  DateInvoiced
		miniTable.setColumnClass(5, BigDecimal.class, true);	//  GrandTotal
		miniTable.setColumnClass(6, BigDecimal.class, true);	//  OpenAmt
		miniTable.setColumnClass(7, String.class, true);		//  Status
		miniTable.setColumnClass(8, String.class, true);		//  Sales
		
		//  Table UI
		miniTable.autoSize();	
	}
}
