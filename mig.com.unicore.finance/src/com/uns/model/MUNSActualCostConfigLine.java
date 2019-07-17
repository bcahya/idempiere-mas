/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MElementValue;
import org.compiere.model.MOrg;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSFinanceModelFactory;

/**
 * @author AzHaidar
 *
 */
public class MUNSActualCostConfigLine extends X_UNS_ActualCostConfigLine {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6818119465749252063L;

	/**
	 * @param ctx
	 * @param UNS_ActualCostConfigItem_ID
	 * @param trxName
	 */
	public MUNSActualCostConfigLine(Properties ctx,
			int UNS_ActualCostConfigItem_ID, String trxName) {
		super(ctx, UNS_ActualCostConfigItem_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSActualCostConfigLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	private X_UNS_ActualCostXclLineList[] m_xclList;
	

	/**
	 * 
	 * @return
	 */
	public X_UNS_ActualCostXclLineList[] getExcludeElementValues()
	{
		return getExcludeElementValues(false);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public X_UNS_ActualCostXclLineList[] getExcludeElementValues(boolean requery)
	{
		if (m_xclList != null && !requery) {
			set_TrxName(m_xclList, get_TrxName());
			return m_xclList;
		}

		List<MElementValue> list = 
				Query.get (getCtx(), UNSFinanceModelFactory.EXTENSION_ID, X_UNS_ActualCostXclLineList.Table_Name, 
						"UNS_ActualCostConfigLine_ID=? AND IsActive=?", get_TrxName())
				.setParameters(new Object[] { get_ID(), "Y" })
				.setOrderBy(COLUMNNAME_C_ElementValue_ID).list();

		m_xclList = new X_UNS_ActualCostXclLineList[list.size()];
		list.toArray(m_xclList);

		return m_xclList;
	}

	/**
	 * Get the excluded list for the given parameters.
	 * @param ctx
	 * @param AD_Client_ID
	 * @param AD_Org_ID
	 * @param lineName
	 * @param trxName
	 * @return
	 */
	public static X_UNS_ActualCostXclLineList[] getExcludeElementValues(
			Properties ctx, int AD_Client_ID, int AD_Org_ID, String lineName, String trxName)
	{
		String whereClause = 
				"UNS_ActualCostConfigLine_ID=(SELECT confLine.UNS_ActualCostConfigLine_ID " +
				"							  FROM UNS_ActualCostConfigLine confLine " +
				"							  WHERE confLine.AD_Client_ID=? AND confLine.IsActive='Y'" +
				"								AND confLine.AD_Org_ID=? AND confLine.Name=?)" +
				" AND IsActive='Y'";
		List<MElementValue> list = 
				Query.get (ctx, UNSFinanceModelFactory.EXTENSION_ID, X_UNS_ActualCostXclLineList.Table_Name, 
						whereClause, trxName)
				.setParameters(new Object[] { AD_Client_ID, AD_Org_ID, lineName })
				.setOrderBy(COLUMNNAME_C_ElementValue_ID).list();

		X_UNS_ActualCostXclLineList[] xclList = new X_UNS_ActualCostXclLineList[list.size()];
		list.toArray(xclList);

		return xclList;
	}
	
	/**
	 *	String representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUNSActualCostConfigLine[")
			.append (get_ID()).append("-").append(MOrg.get(getCtx(), getAD_Org_ID()).getValue())
			.append(",").append(getName())
			.append ("]");
		return sb.toString ();
	}	//	toString
}
