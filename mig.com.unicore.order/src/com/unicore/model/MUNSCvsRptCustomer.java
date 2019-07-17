package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

public class MUNSCvsRptCustomer extends X_UNS_Cvs_RptCustomer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 673027392971032073L;
	private MUNSCvsRpt m_parent = null;
	private MUNSCvsRptProduct[] m_lines = null;

	
	/**
	 * Construct
	 * @param ctx
	 * @param UNS_Cvs_RptCustomer_ID
	 * @param trxName
	 */
	public MUNSCvsRptCustomer(Properties ctx, int UNS_Cvs_RptCustomer_ID,
			String trxName) 
	{
		super(ctx, UNS_Cvs_RptCustomer_ID, trxName);
	}

	/**
	 * Construct
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCvsRptCustomer(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Get parent
	 * @return {@link MUNSCvsRpt}
	 */
	public MUNSCvsRpt getParent()
	{
		if (null == m_parent)
		{
			m_parent = (MUNSCvsRpt) getUNS_Cvs_Rpt();
		}
		
		return m_parent;
	}
	
	/**
	 * Get Lines
	 * @param requery
	 * @return {@link MUNSCvsRptProduct}[]
	 */
	public MUNSCvsRptProduct[] getLines(boolean requery)
	{
		if (null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSCvsRptProduct> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, 
				MUNSCvsRptProduct.Table_Name, Table_Name + "_ID = ?", 
				get_TrxName()).setParameters(get_ID()).list();
		
		m_lines = new MUNSCvsRptProduct[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	protected void updateHeader()
	{
		BigDecimal totalLines = DB.getSQLValueBD(
				get_TrxName()
				, "SELECT COALESCE(SUM(TotalLines), 0) FROM UNS_Cvs_RptCustomer"
						+ " WHERE UNS_Cvs_Rpt_ID = ?", 
						getParent().get_ID());
		
		String update = "UPDATE UNS_Cvs_Rpt SET TotalLines = ?, "
				+ " GrandTotal = ? WHERE UNS_Cvs_Rpt_ID = ?";
		int ok = DB.executeUpdate(
				update, new Object[]{totalLines, totalLines, 
						getParent().get_ID()}
				, false , get_TrxName());
		if(ok == -1)
		{
			throw new AdempiereException("Failed on update header");
		}
		
		getParent().updateHeader();
	}
}
