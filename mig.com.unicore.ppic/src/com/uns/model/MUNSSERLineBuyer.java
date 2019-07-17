/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.util.DB;

/**
 * @author menjangan
 *
 */
public class MUNSSERLineBuyer extends X_UNS_SERLineBuyer {
	
	private MUNSSER m_parent = null;
	private MUNSSERLineProduct[] m_lines = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_SERBuyerProduct_ID
	 * @param trxName
	 */
	public MUNSSERLineBuyer(Properties ctx, int UNS_SERBuyerProduct_ID,
			String trxName) {
		super(ctx, UNS_SERBuyerProduct_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSERLineBuyer(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSSER getParent()
	{
		if (m_parent == null)
		{
			m_parent = new MUNSSER(getCtx(), getUNS_SER_ID(), get_TrxName());
		}
		return m_parent;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	protected MUNSSERLineProduct[] getLines(boolean requery)
	{
		if (m_lines != null
				&& !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSSERLineProduct> mList = 
				new Query(getCtx(), MUNSSERLineProduct.Table_Name
						, I_UNS_SERLineProduct.COLUMNNAME_UNS_SERLineBuyer_ID + " =?"
						, get_TrxName()).setParameters(getUNS_SERLineBuyer_ID())
						.setOrderBy(MUNSSERLineProduct.COLUMNNAME_UNS_SERLineProduct_ID)
						.list();
		m_lines = new MUNSSERLineProduct[mList.size()];
		mList.toArray(m_lines);
		
		return m_lines;
	}
	
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSSERLineProduct[] getLines()
	{
		return getLines(false);
	}
	
	@Override
	public boolean beforeSave(boolean newRecord)
	{
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 FROM " + Table_Name 
					+ " WHERE " + COLUMNNAME_UNS_SER_ID + " =?";
			int ii = DB.getSQLValueEx (get_TrxName(), sql, getUNS_SER_ID());
			setLine (ii);
		}
		
		setAVGFOB();
		
		return true;
	}
	
	public boolean afterSave(boolean newRecord, boolean sucess)
	{
		if (!updateHeader() || !sucess)
			return false;
		
		return true;
	}
	
	/**
	 * 
	 */
	public void setAVGFOB()
	{
		if (getQtyAvailable().compareTo(BigDecimal.ZERO) > 0)
		{
			BigDecimal total = BigDecimal.ZERO;
			total = getTotalLines().divide(getQtyAvailable(),RoundingMode.HALF_UP);
			setFOB(total);
		}

	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getLineNetSalesTOtal()
	{
		BigDecimal salesTotal = BigDecimal.ZERO;
		MUNSSERLineBuyer[] mLines = getParent().getLines();
		
		for (int i=0; i<mLines.length; i++)
		{
			MUNSSERLineBuyer mLine = mLines[i];
			salesTotal = salesTotal.add(mLine.getSalesTotal());
		}
		
		return salesTotal;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean updateHeader()
	{
		getParent().setSalesTotal(getLineNetSalesTOtal());
		if (!getParent().save())
			return false;
		
		return true;
	}

}
