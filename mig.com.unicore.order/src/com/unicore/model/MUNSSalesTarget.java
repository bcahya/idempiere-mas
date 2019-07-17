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
public class MUNSSalesTarget extends X_UNS_SalesTarget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSSalesTargetLine[] m_lines	= null;

	/**
	 * @param ctx
	 * @param UNS_SalesTarget_ID
	 * @param trxName
	 */
	public MUNSSalesTarget(Properties ctx, int UNS_SalesTarget_ID,
			String trxName) {
		super(ctx, UNS_SalesTarget_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSalesTarget(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSSalesTargetLine[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSSalesTargetLine> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSSalesTargetLine.Table_Name
				, MUNSSalesTargetLine.COLUMNNAME_UNS_SalesTarget_ID +"=?"
				, get_TrxName()).setParameters(getUNS_SalesTarget_ID()).list();
		
		m_lines = new MUNSSalesTargetLine[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	public MUNSSalesTargetLine[] getLines()
	{
		return getLines(false);
	}

}
