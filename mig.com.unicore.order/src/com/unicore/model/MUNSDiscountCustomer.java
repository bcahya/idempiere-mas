/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MBPartner;
import org.compiere.util.DB;

/**
 * @author root
 *
 */
public class MUNSDiscountCustomer extends X_UNS_Discount_Customer {

	private MDiscountSchema m_parentSchema = null;
	private MDiscountSchemaBreak m_parentBreak = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4266505864088513088L;

	/**
	 * @param ctx
	 * @param UNS_Discount_Customer_ID
	 * @param trxName
	 */
	public MUNSDiscountCustomer(Properties ctx, int UNS_Discount_Customer_ID,
			String trxName) {
		super(ctx, UNS_Discount_Customer_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSDiscountCustomer(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MDiscountSchema getParentSchema()
	{
		if(null != m_parentSchema)
			return m_parentSchema;
		
		m_parentSchema = new MDiscountSchema(getCtx(), getM_DiscountSchema_ID(), get_TrxName());
		
		return m_parentSchema;
	}
	
	public MDiscountSchemaBreak getParentBreak()
	{
		if(null != m_parentBreak)
			return m_parentBreak;
		
		m_parentBreak = new MDiscountSchemaBreak(getCtx(), getM_DiscountSchemaBreak_ID(), get_TrxName());
		
		return m_parentBreak;
	}
	

	/**
	 * 
	 * @param partner
	 * @return
	 */
	public boolean isInMyBPGroup(MBPartner partner)
	{
		if(partner.getC_BP_Group_ID() == getC_BP_Group_ID())
			return true;
		
		return false;
	}
	
	
	/**
	 * 
	 * @param parent
	 */
	public MUNSDiscountCustomer(MDiscountSchema parent)
	{
		super(parent.getCtx(), 0, parent.get_TrxName());
		setAD_Org_ID(parent.getAD_Org_ID());
		setM_DiscountSchema_ID(parent.get_ID());
		m_parentSchema = parent;
	}
	
	/**
	 * 
	 * @param parent
	 */
	public MUNSDiscountCustomer(MDiscountSchemaBreak parent)
	{
		super(parent.getCtx(), 0, parent.get_TrxName());
		setAD_Org_ID(parent.getAD_Org_ID());
		setM_DiscountSchemaBreak_ID(parent.get_ID());
		m_parentBreak = parent;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getSeqNo() <= 0)
		{
			String sql = null;
			int param = 0;
			if(getM_DiscountSchema_ID() > 0)
			{
				sql = "SELECT MAX(SeqNo) FROM UNS_Discount_Customer WHERE M_DiscountSchema_ID = ?";
				param = getM_DiscountSchema_ID();
			}
			else if(getM_DiscountSchemaBreak_ID() > 0)
			{
				sql = "SELECT MAX(SeqNo) FROM UNS_Discount_Customer WHERE M_DiscountSchemaBreak_ID = ?";
				param = getM_DiscountSchemaBreak_ID();
			}
			
			int seq = DB.getSQLValue(get_TrxName(), sql, param);
			if(~seq == 0)
				seq = 0;
			
			seq += 10;
			setSeqNo(seq);
		}
		return super.beforeSave(newRecord);
	}

}
