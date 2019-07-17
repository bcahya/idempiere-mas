/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSFinanceModelFactory;

/**
 * @author AzHaidar
 *
 */
public class MUNSActualCostConfig extends X_UNS_ActualCostConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6146917115333121980L;

	/**
	 * Items of this actual cost configuration.
	 */
	private MUNSActualCostConfigItem[] m_lines = null;

	/**
	 * @param ctx
	 * @param UNS_ActualCostConfig_ID
	 * @param trxName
	 */
	public MUNSActualCostConfig(Properties ctx, int UNS_ActualCostConfig_ID,
			String trxName) {
		super(ctx, UNS_ActualCostConfig_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSActualCostConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	/**
	 * Get Items of this Actual Cost Configuration.
	 * @param requery
	 * @return
	 */
	public MUNSActualCostConfigItem[] getConfigItems(boolean requery) 
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}

		List<MUNSActualCostConfigItem> list = Query
				.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID,
						MUNSActualCostConfigItem.Table_Name, "UNS_ActualCostConfig_ID=? AND IsActive=?",
						get_TrxName())
				.setParameters(getUNS_ActualCostConfig_ID(), "Y")
				.setOrderBy(MUNSActualCostConfigItem.COLUMNNAME_UNS_ActualCostConfigItem_ID).list();

		m_lines = new MUNSActualCostConfigItem[list.size()];
		list.toArray(m_lines);
		return m_lines;
	}
	
	/**
	 * Load this Actual Cost Configuration items.
	 * @return
	 */
	public MUNSActualCostConfigItem[] getConfigItems()
	{
		return getConfigItems(false);
	}

	/**
	 * Lines of this cost config item.
	 */
	private MUNSActualCostConfigLine[] m_configLines = null;

	/**
	 * Get childs of this resource.
	 * @param requery
	 * @return
	 */
	public MUNSActualCostConfigLine[] getConfigLines(boolean requery) 
	{
		if (m_configLines != null && !requery) {
			set_TrxName(m_configLines, get_TrxName());
			return m_configLines;
		}

		List<MUNSActualCostConfigLine> list = Query
				.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID,
						X_UNS_ActualCostConfigLine.Table_Name, "UNS_ActualCostConfig_ID=? AND IsActive=?",
						get_TrxName())
				.setParameters(getUNS_ActualCostConfig_ID(), "Y")
				.setOrderBy(X_UNS_ActualCostConfigLine.COLUMNNAME_UNS_ActualCostConfigLine_ID).list();

		m_configLines = new MUNSActualCostConfigLine[list.size()];
		list.toArray(m_configLines);
		return m_configLines;
	}
	
	/**
	 * Get childs of this resource for the given AD_Org_ID.
	 * @param requery
	 * @return
	 */
	public X_UNS_ActualCostConfigLine[] getConfigLines(int AD_Org_ID) 
	{
		List<X_UNS_ActualCostConfigLine> list = Query
				.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID,
						X_UNS_ActualCostConfigLine.Table_Name, "UNS_ActualCostConfig_ID=? AND AD_Org_ID=? ",
						get_TrxName())
				.setParameters(new Object[] { getUNS_ActualCostConfig_ID(), AD_Org_ID })
				.setOrderBy(X_UNS_ActualCostConfigLine.COLUMNNAME_UNS_ActualCostConfigLine_ID).list();

		X_UNS_ActualCostConfigLine[] configLines = new X_UNS_ActualCostConfigLine[list.size()];
		list.toArray(configLines);
		return configLines;
	}
	
	/**
	 * Load current lines of cost item.
	 * @return
	 */
	public MUNSActualCostConfigLine[] getConfigLines()
	{
		return getConfigLines(false);
	}
	
}
