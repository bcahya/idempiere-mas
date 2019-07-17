/**
 * 
 */
package com.uns.qad.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author YAKA
 *
 */
public class MUNSQAStatusType extends X_UNS_QAStatus_Type {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6833475751712849152L;
	private MUNSQAMonitoringAttribut[] m_lines;

	/**
	 * @param ctx
	 * @param UNS_QAStatus_PMSMType_ID
	 * @param trxName
	 */
	public MUNSQAStatusType(Properties ctx, int UNS_QAStatus_PMSMType_ID,
			String trxName) {
		super(ctx, UNS_QAStatus_PMSMType_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSQAStatusType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static int get(Properties ctx, String category, String trxName){

		String sql = "SELECT UNS_QAStatus_Type_ID FROM UNS_QAStatus_Type " +
				"WHERE category LIKE '" + category + "'";
		int id = DB.getSQLValue(trxName, sql);
		
		if(id==-1)
			throw new AdempiereException("Can't find ID of UNS_QAStatus_Type");
		
		return id;
	}

	/**
	 * 
	 * @param requery
	 * @return MUNSQAMonitoringAttribut[]
	 */
	protected MUNSQAMonitoringAttribut[] getLines(boolean requery){
		if (m_lines != null	&& !requery){
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSQAMonitoringAttribut> mList =
				new Query(getCtx(), MUNSQAMonitoringAttribut.Table_Name
						, MUNSQAMonitoringAttribut.COLUMNNAME_UNS_QAStatus_Type_ID + " =?"
						, get_TrxName())
		.setParameters(getUNS_QAStatus_Type_ID())
		.setOrderBy(MUNSQAMonitoringAttribut.COLUMNNAME_UNS_QAMonitoringAttribut_ID).list();
		
		m_lines = new MUNSQAMonitoringAttribut[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
	
	/**
	 * 
	 * @return MUNSQAMonitoringAttribut[]
	 */
	public MUNSQAMonitoringAttribut[] getLines()
	{
		return getLines(false);
	}

}
