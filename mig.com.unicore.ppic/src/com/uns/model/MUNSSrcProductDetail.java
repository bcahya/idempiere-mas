/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MStorageOnHand;
import org.compiere.model.X_M_StorageOnHand;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSSrcProductDetail extends X_UNS_SrcProduct_Detail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSProductionSchedule m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_SrcProduct_Detail_ID
	 * @param trxName
	 */
	public MUNSSrcProductDetail(Properties ctx, int UNS_SrcProduct_Detail_ID,
			String trxName) {
		super(ctx, UNS_SrcProduct_Detail_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSrcProductDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSProductionSchedule getParent()
	{
		if (m_parent == null)
			m_parent = new MUNSProductionSchedule(getCtx(), getUNS_ProductionSchedule_ID(), get_TrxName());
		return m_parent;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getParent().getPSType().equals(X_UNS_ProductionSchedule.PSTYPE_MasterProductionSchedule))
			return true;
		
		if (!getParent().getPSType().equals(X_UNS_ProductionSchedule.PSTYPE_Rebundle))
		{
			BigDecimal prevTotalQty = getTotalLinesQty();
			if (!newRecord)
				prevTotalQty = prevTotalQty.subtract((BigDecimal)(get_ValueOld(COLUMNNAME_UsedQty)));
			
			BigDecimal totalQtyToUse = prevTotalQty.add(getUsedQty());
			
			if (totalQtyToUse.compareTo(getParent().getQtyOrdered()) > 0)
			{
				setUsedQty(getParent().getQtyOrdered().subtract(prevTotalQty));
			}
		}		
		return true;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getTotalLinesQty()
	{
		MUNSProductionSchedule productionSchedule = getParent();
		MUNSSrcProductDetail[] srcProductDetails = productionSchedule.getLinesSrcProductDetail(false);
		BigDecimal totalQty = BigDecimal.ZERO;
		for (MUNSSrcProductDetail srcProductDetail : srcProductDetails)
		{
			totalQty = totalQty.add(srcProductDetail.getQtyUom());
		}
		
		return totalQty;
	}
	
	/**
	 * 
	 * @param M_AttributeSetInstance_ID
	 * @param trxName
	 * @return
	 */
	public static MStorageOnHand[] getStorageByAtribute(int M_AttributeSetInstance_ID, String trxName)
	{
		MStorageOnHand[] storageOnHand = null;
		
		final String whereClause = X_M_StorageOnHand.COLUMNNAME_M_AttributeSetInstance_ID + "=?";
		List<MStorageOnHand> list = Query.get(
				Env.getCtx(), UNSPPICModelFactory.getExtensionID(), X_M_StorageOnHand.Table_Name
				, whereClause, trxName).setParameters(M_AttributeSetInstance_ID).list();
		
		storageOnHand = new MStorageOnHand[list.size()];
		list.toArray(storageOnHand);
		
		return storageOnHand;
	}
	
	public MUNSProductionDetail getProductionDetail()
	{
		MUNSProductionDetail pd = null;
		String sql = "SELECT * FROM UNS_Production_Detail pd WHERE pd.M_AttributeSetInstance_ID =?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = DB.prepareStatement(sql, get_TrxName());
			stm.setInt(1,getM_AttributeSetInstance_ID());
			rs = stm.executeQuery();
			if (rs.next())
				pd = new MUNSProductionDetail(getCtx(), rs, get_TrxName());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DB.close(rs, stm);
		}
		return pd;
	}

}
