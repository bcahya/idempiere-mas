package com.uns.qad.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

public class MUNSQALabTestType extends X_UNS_QALabTest_Type {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3524092608467168679L;
	private MUNSQALabTest[] m_lines;

	public MUNSQALabTestType(Properties ctx, int UNS_QALabTest_Type_ID,
			String trxName) {
		super(ctx, UNS_QALabTest_Type_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSQALabTestType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	* @param requery
	* @return MUNSQALabTest[]
	*/
	protected MUNSQALabTest[] getLines(boolean requery){
		if (m_lines != null	&& !requery){
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSQALabTest> mList = new Query(getCtx(), MUNSQALabTest.Table_Name
			, MUNSQALabTest.COLUMNNAME_UNS_QALabTest_Type_ID + " =?", get_TrxName())
			.setParameters(get_ID())
			.setOrderBy(MUNSQALabTest.COLUMNNAME_UNS_QALabTest_ID).list();
			
		m_lines = new MUNSQALabTest[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
		
	/**
	 * 
	 * @return MUNSQALabTest[]
	 */
	public MUNSQALabTest[] getLines(){
		return getLines(false);
	}
}
