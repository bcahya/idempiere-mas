/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * @author root
 *
 */
public class MUNSDiscountOrg extends X_UNS_Discount_Org {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4432021098360247654L;
	private MDiscountSchema m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_Discount_Org_ID
	 * @param trxName
	 */
	public MUNSDiscountOrg(Properties ctx, int UNS_Discount_Org_ID,
			String trxName) {
		super(ctx, UNS_Discount_Org_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSDiscountOrg(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSDiscountOrg(MDiscountSchema parent)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		set_Value(parent.get_TableName() + "_ID", parent.get_ID());
		m_parent = parent;
	}
	
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MDiscountSchema getParent(boolean requery)
	{
		if(null != m_parent && !requery)
			return m_parent;
		
		m_parent = new MDiscountSchema(getCtx(), getM_DiscountSchema_ID(), get_TrxName());
		return m_parent;
	}
	
	public MDiscountSchema getParent()
	{
		return getParent(false);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getSeqNo() <= 0)
		{
			String sql = "SELECT MAX(SeqNo) FROM UNS_Discount_Customer WHERE M_DiscountSchema_ID = ?";
			int seq = DB.getSQLValue(get_TrxName(), sql, getM_DiscountSchema_ID());
			if(~seq == 0)
				seq = 0;
			
			seq += 10;
			setSeqNo(seq);
		}
		return super.beforeSave(newRecord);
	}
}
