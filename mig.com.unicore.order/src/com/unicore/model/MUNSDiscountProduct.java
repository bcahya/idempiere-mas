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
public class MUNSDiscountProduct extends X_UNS_Discount_Product {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3724787570521388403L;
	private MDiscountSchemaBreak m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_Discount_Product_ID
	 * @param trxName
	 */
	public MUNSDiscountProduct(Properties ctx, int UNS_Discount_Product_ID,
			String trxName) {
		super(ctx, UNS_Discount_Product_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSDiscountProduct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param parent
	 */
	public MUNSDiscountProduct(MDiscountSchemaBreak parent)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setM_DiscountSchemaBreak_ID(parent.get_ID());
		m_parent = parent;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MDiscountSchemaBreak getParent(boolean requery)
	{
		if(null != m_parent && !requery)
			return m_parent;
		
		m_parent = new MDiscountSchemaBreak(getCtx(), getM_DiscountSchemaBreak_ID(), get_TrxName());
		return m_parent;
	}
	
	/**
	 * 
	 */
	public MDiscountSchemaBreak getParent()
	{
		return getParent(false);
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 */
	public boolean isInMyProductCategory(int M_Product_ID)
	{
		int M_Product_Category_ID = DB.getSQLValue(
				get_TrxName()
				, "SELECT M_Product_Category_ID FROM M_Product WHERE M_Product_ID = ?"
				, M_Product_ID);
		
		return M_Product_Category_ID == getM_Product_Category_ID();		
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getSeqNo() <= 0)
		{
			String sql = "SELECT MAX(SeqNo) FROM UNS_Discount_Customer WHERE M_DiscountSchemaBreak_ID = ?";
			
			int seq = DB.getSQLValue(get_TrxName(), sql, getM_DiscountSchemaBreak_ID());
			if(~seq == 0)
				seq = 0;
			
			seq += 10;
			setSeqNo(seq);
		}
		if (newRecord && getM_Product_ID() > 0) {
			String sql = "SELECT C_UOM_ID FROM M_Product WHERE M_Product_ID = ?";
			int C_UOM_ID = DB.getSQLValue(get_TrxName(), sql, getM_Product_ID());
			set_ValueE("C_UOM_ID", C_UOM_ID);
		}
		return super.beforeSave(newRecord);
	}

}
