package com.uns.qad.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

public class MUNSQAMonitoringLab extends X_UNS_QAMonitoringLab {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7410559136926500585L;
	private MUNSQAMonitoringLabLine[] m_line;

	public MUNSQAMonitoringLab(Properties ctx, int UNS_QAMonitoringLab_ID,
			String trxName) {
		super(ctx, UNS_QAMonitoringLab_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSQAMonitoringLab(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	* @param requery
	* @return MUNSQAMonitoringLabLine[]
	*/
	protected MUNSQAMonitoringLabLine[] getLine(boolean requery){
		if (m_line != null	&& !requery){
			set_TrxName(m_line, get_TrxName());
			return m_line;
		}
		
		List<MUNSQAMonitoringLabLine> mList = new Query(getCtx(), MUNSQAMonitoringLabLine.Table_Name
			, MUNSQAMonitoringLabLine.COLUMNNAME_UNS_QAMonitoringLab_ID + " =?", get_TrxName())
			.setParameters(get_ID())
			.setOrderBy(MUNSQAMonitoringLabLine.COLUMNNAME_UNS_QAMonitoringLabLine_ID).list();
			
		m_line = new MUNSQAMonitoringLabLine[mList.size()];
		mList.toArray(m_line);
		return m_line;
	}
		
	/**
	 * 
	 * @return MUNSQAMonitoringLabLine[]
	 */
	public MUNSQAMonitoringLabLine[] getLine(){
		return getLine(false);
	}

}
