/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author eko
 * @updated By Menjangan
 * @Untasoft
 */
public class MUNSRecidenceGroup extends X_UNS_RecidenceGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSMessBuildingBlock[] m_Lines = null;

	/**
	 * @param ctx
	 * @param UNS_RecidenceGroup_ID
	 * @param trxName
	 */
	public MUNSRecidenceGroup(Properties ctx, int UNS_RecidenceGroup_ID,
			String trxName) {
		super(ctx, UNS_RecidenceGroup_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSRecidenceGroup(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSMessBuildingBlock[] getLines(boolean requery)
	{
		if(!requery && null != m_Lines)
		{
			set_TrxName(m_Lines, get_TrxName());
			return m_Lines;
		}
		
		String WhereClause = MUNSMessBuildingBlock.COLUMNNAME_UNS_RecidenceGroup_ID + "=? AND "
				+ MUNSMessBuildingBlock.COLUMNNAME_IsActive + " ='Y'";
		
		List<MUNSMessBuildingBlock>list = Query.get(
				getCtx(), UNSHRMModelFactory.getExtensionID()
				, MUNSMessBuildingBlock.Table_Name
				, WhereClause
				, get_TrxName())
				.setParameters(getUNS_RecidenceGroup_ID())
				.list();
		m_Lines = new MUNSMessBuildingBlock[list.size()];
		list.toArray(m_Lines);
		return m_Lines;
	}

	@Override
	protected boolean beforeDelete() {
		for(MUNSMessBuildingBlock bd : getLines(false))
			bd.delete(false);
		
		return super.beforeDelete();
	}
	
}
