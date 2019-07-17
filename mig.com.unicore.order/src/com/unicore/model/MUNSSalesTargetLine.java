/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author root
 *
 */
public class MUNSSalesTargetLine extends X_UNS_SalesTargetLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSSalesTarget m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_SalesTargetLine_ID
	 * @param trxName
	 */
	public MUNSSalesTargetLine(Properties ctx, int UNS_SalesTargetLine_ID,
			String trxName) {
		super(ctx, UNS_SalesTargetLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSalesTargetLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSSalesTarget getParent()
	{
		if(m_parent != null)
			return m_parent;
		
		m_parent = new MUNSSalesTarget(getCtx(), getUNS_SalesTarget_ID(), get_TrxName());
		return m_parent;
	}
	
	private MUNSSalesTargetPeriodic[] m_lines = null;
	private List<MUNSSalesTargetPeriodic> m_listLines = null;
	
	public MUNSSalesTargetPeriodic[] getLines()
	{
		return getLines(false, null);
	}
	
	public MUNSSalesTargetPeriodic[] getLines(boolean requery, String orderByColumn)
	{
		if(null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		if(null == orderByColumn || orderByColumn.isEmpty())
			orderByColumn = MUNSSalesTargetPeriodic.COLUMNNAME_UNS_SalesTargetPeriodic_ID;
		
		List<MUNSSalesTargetPeriodic> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSSalesTargetPeriodic.Table_Name
				, MUNSSalesTargetPeriodic.COLUMNNAME_UNS_SalesTargetLine_ID + "=?"
				, get_TrxName())
				.setParameters(getUNS_SalesTargetLine_ID()).setOrderBy(orderByColumn).list();
		
		m_lines = new MUNSSalesTargetPeriodic[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	public List<MUNSSalesTargetPeriodic> getListTargetPeriodic(boolean requery)
	{
		if(null != m_listLines && !requery)
			return m_listLines;
		
		m_listLines = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSSalesTargetPeriodic.Table_Name
				, MUNSSalesTargetPeriodic.COLUMNNAME_UNS_SalesTargetLine_ID + "=?"
				, get_TrxName())
				.setParameters(getUNS_SalesTargetLine_ID()).list();
		
		return m_listLines;
	}

}
