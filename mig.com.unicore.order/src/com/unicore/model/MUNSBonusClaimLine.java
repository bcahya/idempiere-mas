/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.util.DB;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;


/**
 * @author UNTA-Andy
 * 
 */
public class MUNSBonusClaimLine extends X_UNS_BonusClaimLine
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2691757803904411089L;
	private MUNSBonusClaim m_parent = null;
	
	/**
	 * @param ctx
	 * @param UNS_BonusClaimLine_ID
	 * @param trxName
	 */
	public MUNSBonusClaimLine(Properties ctx, int UNS_BonusClaimLine_ID, String trxName)
	{
		super(ctx, UNS_BonusClaimLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBonusClaimLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		String SQL =
				"SELECT count(UNS_BonusClaimLine_ID) FROM UNS_BonusClaimLine WHERE UNS_BonusClaim_ID=?";
		int line = DB.getSQLValue(get_TrxName(), SQL, getUNS_BonusClaim_ID());
		if(line == 0)
			setLine(10);
		else
			setLine(line * 10);
		
		setLineNetAmt();
		
		return super.beforeSave(newRecord);
	}
	
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		updateHear();
		return super.afterSave(newRecord, success);
	}
	
	private MUNSDiscountTrx[] m_dTrx = null;
	public MUNSDiscountTrx[] getDiscountTransactions(boolean requery)
	{
		if(null != m_dTrx && !requery)
		{
			set_TrxName(m_dTrx, get_TrxName());
			return m_dTrx;
		}
		
		List<MUNSDiscountTrx> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSDiscountTrx.Table_Name
				, MUNSDiscountTrx.COLUMNNAME_UNS_BonusClaimLine_ID + "=?"
				, get_TrxName()).setParameters(get_ID()).list();
		
		m_dTrx = new MUNSDiscountTrx[list.size()];
		list.toArray(m_dTrx);
		
		return m_dTrx;
	}
	
	@Override
	protected boolean beforeDelete()
	{
		deleteLines();
		return super.beforeDelete();
	}
	
	public void deleteLines()
	{
		String sql = "UPDATE UNS_DiscountTrx SET UNS_BonusClaimLine_ID = null WHERE UNS_BonusClaimLine_ID=?";
		DB.executeUpdate(sql, get_ID(), get_TrxName());
//		for(MUNSDiscountTrx trx : getDiscountsTrx(true))
//		{
//			trx.setUNS_BonusClaimLine_ID(-1);
//			trx.saveEx();
//		}
	}

	public void setDataValue(UNSDiscountBonus discountBonus)
	{
		setM_Product_ID(discountBonus.getM_Product_ID());
		setC_UOM_ID(discountBonus.getC_UOM_ID());
		setProductBonus_ID(discountBonus.getM_ProductBonus_ID());
		setUOMBonus_ID(discountBonus.getC_UOMBonus_ID());
		setQtyAchieved(discountBonus.getQty());
		setAmtAchieved(discountBonus.getLineNetAmount());
		setQtyClaimed(discountBonus.getQtyBonus());
		setAmtClaimed(discountBonus.getDiscountBonus());
		setLineNetAmt();
	}
	
	private void setLineNetAmt()
	{
		setLineNetAmt(getAmtClaimed());
	}

	public static int getIDbyProductBonus(int m_Product_ID, int pBonus1_ID, String trxName)
	{
		String SQL =
				"SELECT UNS_BonusClaimLine_ID FROM UNS_BonusClaimLine WHERE M_Product_ID=? OR ProductBonus_ID=?";
		int id = DB.getSQLValue(trxName, SQL, m_Product_ID, pBonus1_ID);
		
		if (id == -1)
			return 0;
		
		return id;
	}
	
	private MUNSDiscountTrx[] m_lines = null;
	
	public MUNSDiscountTrx[] getDiscountsTrx(boolean requery)
	{
		if(null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSDiscountTrx> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSDiscountTrx.Table_Name
				, MUNSDiscountTrx.COLUMNNAME_UNS_BonusClaimLine_ID + "=?"
				, get_TrxName()).setParameters(get_ID()).list();
		
		m_lines = new MUNSDiscountTrx[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	private void updateHear()
	{
		String sql = "SELECT SUM(LineNetAmt) FROM UNS_BonusClaimLine WHERE UNS_BonusClaim_ID = ?";
		MUNSBonusClaim parent = getParent();
		parent.setTotalLines(DB.getSQLValueBD(get_TrxName(), sql, getUNS_BonusClaim_ID()));
		parent.saveEx();
	}

	public MUNSBonusClaim getParent()
	{
		if(null != m_parent)
			return m_parent;
		
		m_parent = new MUNSBonusClaim(getCtx(), getUNS_BonusClaim_ID(), get_TrxName());
		return m_parent;
	}
}
