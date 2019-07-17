/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author AzHaidar
 *
 */
public class MUNSProductionWRAttributes extends X_UNS_Production_WRAttributes {

	/**
	 * 
	 */
	private static final long serialVersionUID = -933604026408286987L;

	/**
	 * @param ctx
	 * @param UNS_Production_WRAttributes_ID
	 * @param trxName
	 */
	public MUNSProductionWRAttributes(Properties ctx,
			int UNS_Production_WRAttributes_ID, String trxName) {
		super(ctx, UNS_Production_WRAttributes_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSProductionWRAttributes(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getM_Product_ID() == 0)
			throw new FillMandatoryException(COLUMNNAME_M_Product_ID);
		
		if (getUPC() != null && !getUPC().isEmpty() && getM_Product_ID() == 0)
		{
			String upcCodeParts = getUPC().replaceAll("[a-zA-Z]+[0-9]+", "");
			int M_Product_ID = MProduct.getIDByUPC(getCtx(), upcCodeParts, true, get_TrxName());
			
			if (M_Product_ID > 0)
				setM_Product_ID(M_Product_ID);
		}
		
		boolean isUPCChanged = is_ValueChanged(COLUMNNAME_UPC);
		
		if ((isUPCChanged && getM_AttributeSetInstance_ID() > 0) || 
				(getUPC() != null && !getUPC().isEmpty() 
					&& getM_AttributeSetInstance_ID() == 0 && getM_Product_ID() > 0)) 
		{
			MProduct product = MProduct.get(getCtx(), getM_Product_ID());
			
			MAttributeSetInstance.initAttributeValuesFrom(
					this, product, COLUMNNAME_M_AttributeSetInstance_ID, get_TrxName());
			
			MAttributeSet attrSet = (MAttributeSet) product.getM_AttributeSet();
			
			if (attrSet.isUniqueUPCPerUnit())
				setProductionQty(Env.ONE);
		}
		
		if (getProductionQty().signum() <= 0)
			setProductionQty(Env.ONE);
		
		String sql = "SELECT 1 FROM UNS_Production_Detail "
				+ "WHERE UNS_Production_ID = ? AND M_Product_ID=? AND IsEndProduct='Y'";
		
		int production_ID = getUNS_Production_Worker().getUNS_Production_ID();
		int product_ID = getM_Product_ID();
		int existingPD = 
				DB.getSQLValueEx(get_TrxName(), sql, production_ID, product_ID);
		if (existingPD <= 0)
			throw new AdempiereException("Failed: The product is not on the production detail list.");
		
		return true;
	} // beforeSave
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		String whereClause = "UNS_Production_Worker_ID=? AND M_Product_ID=?";
		MUNSProductionWorkerResult pwr =
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
						MUNSProductionWorkerResult.Table_Name, whereClause, get_TrxName())
				.setParameters(getUNS_Production_Worker_ID(), getM_Product_ID())
				.firstOnly();
		if (pwr == null)
		{
			pwr = new MUNSProductionWorkerResult((MUNSProductionWorker) getUNS_Production_Worker());
			pwr.setM_Product_ID(getM_Product_ID());
			//pwr.saveEx();
		}
		String sql = "SELECT SUM(pwra.ProductionQty) FROM UNS_Production_WRAttributes pwra "
				+ "	WHERE pwra.UNS_Production_Worker_ID=? AND pwra.M_Product_ID=?";
		BigDecimal totalProductionQty = DB.getSQLValueBD(
				get_TrxName(), sql, getUNS_Production_Worker_ID(), getM_Product_ID());
		
		pwr.setProductionQty(totalProductionQty);
		if (!pwr.save())
			return false;
		
		//TODO - ada kemungkinan secondary output pun diberikan asi tersendiri, yang mana secondary output bisa saja 
		// merupakan bagian dari 2 primary output yang berbeda sehingga perlu penanganan khusus saat pembuatan 
		// production detail line MA nya. 
		// Namun hingga saat class ini dibuatpertama kali di Tedmond (Palembang) belum diketemukan kasus 
		// secondary output yang identifikasikan dengan 1 ASI utk setiap worker result nya.
		MUNSProductionDetailMA ma = new MUNSProductionDetailMA(getCtx(), 0, get_TrxName());
		
		sql = "SELECT UNS_Production_Detail_ID FROM UNS_Production_Detail "
				+ "WHERE M_Product_ID=? AND "
				+ "UNS_Production_ID=(SELECT pw.UNS_Production_ID FROM UNS_Production_Worker pw "
				+ "		WHERE pw.UNS_Production_Worker_ID=?)";
		int pd_ID = DB.getSQLValue(get_TrxName(), sql, getM_Product_ID(), getUNS_Production_Worker_ID());
		
		ma.setUNS_Production_Detail_ID(pd_ID);
		ma.setM_AttributeSetInstance_ID(getM_AttributeSetInstance_ID());
		ma.setUPC(getUPC());
		ma.setMovementQty(getProductionQty());
		ma.setDateMaterialPolicy(getM_AttributeSetInstance().getCreated());
		return ma.save();
	} // afterSave
	
	@Override
	protected boolean afterDelete (boolean success)
	{
		if (!success)
			return !success;
		
		String sql = "DELETE FROM UNS_Production_DetailMA WHERE M_AttributeSetInstance_ID=? ";
		
		DB.executeUpdateEx(
				sql, new Object[]{getM_AttributeSetInstance_ID()}, get_TrxName());
		
		sql = "DELETE FROM M_AttributeInstance WHERE M_AttributeSetInstance_ID=" + getM_AttributeSetInstance_ID();
		DB.executeUpdateEx(sql, get_TrxName());
		sql = "DELETE FROM M_AttributeSetInstance WHERE M_AttributeSetInstance_ID=" + getM_AttributeSetInstance_ID();
		DB.executeUpdateEx(sql, get_TrxName());
		
		return true;
	} // afterDelete
}