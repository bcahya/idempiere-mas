/**
 * 
 */
package com.unicore.base.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.MInvoiceBatch;
import org.compiere.model.MInvoiceBatchLine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.base.model.MInOut;
import com.unicore.model.MUNSBonusClaim;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author UNTA-Andy
 * 
 */
public class MInvoice extends org.compiere.model.MInvoice
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6773998541463654447L;
	private MInvoiceLine[] m_lines;

	/**
	 * @param ctx
	 * @param C_Invoice_ID
	 * @param trxName
	 */
	public MInvoice(Properties ctx, int C_Invoice_ID, String trxName)
	{
		super(ctx, C_Invoice_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoice(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param order
	 * @param C_DocTypeTarget_ID
	 * @param invoiceDate
	 */
	public MInvoice(org.compiere.model.MOrder order, int C_DocTypeTarget_ID, Timestamp invoiceDate)
	{
		super(order, C_DocTypeTarget_ID, invoiceDate);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ship
	 * @param invoiceDate
	 */
	public MInvoice(MInOut ship, Timestamp invoiceDate)
	{
		super(ship, invoiceDate);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param batch
	 * @param line
	 */
	public MInvoice(MInvoiceBatch batch, MInvoiceBatchLine line)
	{
		super(batch, line);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.MInvoice#getLines(boolean)
	 */
	@Override
	public MInvoiceLine[] getLines(boolean requery)
	{
		if (m_lines == null || m_lines.length == 0 || requery)
			m_lines = getLines(null);
		set_TrxName(m_lines, get_TrxName());
		return m_lines;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.MInvoice#getLines()
	 */
	public MInvoiceLine[] getLines()
	{
		// TODO Auto-generated method stub
		return getLines(false);
	}

	/**
	 * Get Invoice Lines of Invoice
	 * 
	 * @param whereClause starting with AND
	 * @return lines
	 */
	private MInvoiceLine[] getLines(String whereClause)
	{
		String whereClauseFinal = "C_Invoice_ID=? ";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		List<MInvoiceLine> list =
				Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, I_C_InvoiceLine.Table_Name, whereClauseFinal, get_TrxName())
						.setParameters(getC_Invoice_ID()).setOrderBy(I_C_InvoiceLine.COLUMNNAME_Line).list();
		return list.toArray(new MInvoiceLine[list.size()]);
	} // getLines

	public static MInvoice[] getInvoiceByDate(Properties ctx, Timestamp dateFrom, Timestamp dateTo, String trxName)
	{
		String dateCheck = MInvoice.COLUMNNAME_DateInvoiced;

		StringBuilder whereClauseFinal =
				new StringBuilder(dateCheck).append(" BETWEEN '").append(dateFrom).append("' AND '")
						.append(dateTo).append("'");

		List<MInvoice> list =
				Query.get(ctx, UNSOrderModelFactory.EXTENSION_ID, MInvoice.Table_Name, whereClauseFinal.toString(), trxName).setOrderBy(
						dateCheck).list();

		//
		return list.toArray(new MInvoice[list.size()]);
	}

	public void setInvoice(MUNSBonusClaim UNSBonusClaim)
	{
		setClientOrg(UNSBonusClaim);
		setC_DocType_ID(UNSBonusClaim.getDocTypeInvoice_ID());
		setC_DocTypeTarget_ID(UNSBonusClaim.getDocTypeInvoice_ID());
		
		setDocAction(DOCACTION_Prepare);
		setDocStatus(DOCSTATUS_Drafted);
	}
	
	public String PrepareIt()
	{
		return super.prepareIt();
	}	
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @param trxName
	 * @return
	 */
	public static MInvoice[] getNonPayable(int C_BPartner_ID, String trxName)
	{
		String sql = "SELECT i.* FROM C_Invoice i WHERE i.C_BPartner_ID = ? AND i.DocStatus = 'CO' " +
				"AND NOT EXIST (SELECT 1 FROM C_PaymentAllocate p WHERE p.C_Invoice_ID = i.C_Incoice_ID" +
				" AND p.DocStatus = 'CO')";
		
		List<MInvoice> list = new ArrayList<MInvoice>();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = DB.prepareStatement(sql, trxName);
			st.setInt(1, C_BPartner_ID);
			rs = st.executeQuery();
			while (rs.next()) {
				MInvoice invoice = new MInvoice(Env.getCtx(), rs, trxName);
				list.add(invoice);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DB.close(rs, st);
		}
		
		MInvoice[] invoices = new MInvoice[list.size()];
		list.toArray(invoices);
		
		return invoices;
	}
	
	/**
	 * 
	 * @param line
	 */
	public void addLines(MInvoiceLine line)
	{
        if (null == m_lines)
        {
        	m_lines = new MInvoiceLine[1];
        }
        else
        {
        	m_lines = Arrays.copyOf(m_lines, m_lines.length + 1);
        }
        m_lines[m_lines.length - 1] = line;
        set_TrxName(m_lines, get_TrxName());
	}
	
	/**
	 * 
	 * @param M_InvoiceLine_ID
	 * @return
	 */
	public MInvoiceLine getLineOf(int M_InvoiceLine_ID)
	{
		MInvoiceLine[] lines = getLines();
		MInvoiceLine line = null;
		for(int i=0; i< lines.length; i++)
		{
			if(lines[i].get_ID() != M_InvoiceLine_ID)
				continue;
			line = lines[i];
			break;
		}
		return line;
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @param price
	 * @return
	 */
	public MInvoiceLine getByProductAndPrice(int M_Product_ID, BigDecimal price)
	{
		getLines(false);
		for (MInvoiceLine line : m_lines)
		{
			if (line.getM_Product_ID() != M_Product_ID)
			{
				continue;
			}
			else if (line.getPriceActual().compareTo(price) != 0)
			{
				continue;
			}
			
			return line;
		}
		
		return null;
	}
}
