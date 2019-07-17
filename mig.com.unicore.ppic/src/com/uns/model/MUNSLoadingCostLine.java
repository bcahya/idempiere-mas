/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.MUOM;
import org.compiere.util.DB;

import com.uns.util.ErrorMsg;

/**
 * @author menjangan
 *
 */
public class MUNSLoadingCostLine extends X_UNS_LoadingCost_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_LoadingCost_Line_ID
	 * @param trxName
	 */
	public MUNSLoadingCostLine(Properties ctx, int UNS_LoadingCost_Line_ID,
			String trxName) {
		super(ctx, UNS_LoadingCost_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLoadingCostLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public void addDescription (String description)
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
	public BigDecimal getTotalLineNetAmt() {
		BigDecimal netAmt = BigDecimal.ZERO;
		MUNSLoadingCostLine[] lcs = getParent().getLines(true);
		for (MUNSLoadingCostLine lc : lcs) {
			netAmt = netAmt.add(lc.getLineNetAmt());
		}
		return netAmt;
	}
	
	private MProduct m_Product = null;
	public void setC_UOM() {
		m_Product = MProduct.get(getCtx(), getM_Product_ID());
		int C_UOM_ID = m_Product.getC_UOM_ID();
		setC_UOM_ID(C_UOM_ID);
	}
	
	public void setPriceCost() {
		BigDecimal cost = MUNSBiayaBongkarMuat.getBiayaBongkarMuat(
												getTypeBongkar(), true, 
												getM_Product_ID(), getC_UOM_ID(), 
												getAD_Org_ID(), get_TrxName());
		if (cost.compareTo(BigDecimal.ZERO) <= 0) {
			MUOM uom = new MUOM(getCtx(), getC_UOM_ID(), get_TrxName());
			MOrg org = new MOrg(getCtx(), getAD_Org_ID(), get_TrxName());
			throw new IllegalArgumentException("NO COST FOR " + m_Product.getName()
					+ " Whith UOM " + uom.getName() + " AND Departement " + org.getName());
		}
		setPriceCost(cost);
	}
	
	/**
	 * 
	 */
	private MUNSLoadingCost m_Parent = null;
	
	/**
	 * 
	 * @return
	 */
	public MUNSLoadingCost getParent() {
		if (null == m_Parent) {
			m_Parent = new MUNSLoadingCost(getCtx(), getUNS_LoadingCost_ID(), get_TrxName());
		}
		
		return m_Parent;
	}
	
	private boolean updateHeader() {
		MUNSLoadingCost lc = getParent();
		lc.setTotalAmt(getTotalLineNetAmt());
		if (!lc.save()) {
			ErrorMsg.setErrorMsg(getCtx(), "SaveError", "Cant't Update Loading Cost");
			return false;
		}
		return true;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {
		if (getLine() == 0) {
			int lineNo = DB.getSQLValue(
					get_TrxName(),  "SELECT COALESCE(MAX(Line),0)+10 FROM UNS_LoadingCost_Line WHERE UNS_LoadingCost_ID =?"
					, getUNS_LoadingCost_ID());
			setLine(lineNo);
		}
//		setLineNetAmt(getPriceCost().multiply(getQty()));
		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean sucess) {
		if (!updateHeader() || !sucess) {
			return false;
		}
		return true;
	}
}
