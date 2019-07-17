/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PartType;
import org.compiere.model.MProduct;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.uns.util.DBUtils;

import com.uns.base.model.Query;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSResourceInOut;
import com.uns.model.X_UNS_MRPToFGBOMProduct;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author Haryadi
 *
 */
public class MRPFGBomProductSetting extends SvrProcess {

	private MUNSResource m_Resource;
	/**
	 * 
	 */
	public MRPFGBomProductSetting() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		m_Resource = new MUNSResource(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		// First, remove all existing map.
		String sql = "DELETE FROM UNS_MRPToFGBOMProduct WHERE UNS_Resource_ID=" + m_Resource.get_ID();
		
		DB.executeUpdateEx(sql, get_TrxName());
		
		sql = "SELECT DISTINCT bom.M_ProductBOM_ID " +
				"FROM M_Product_BOM bom, UNS_Resource_InOut rio, M_Product p " +
				"WHERE bom.M_Product_ID=rio.M_Product_ID AND bom.M_ProductBOM_ID=p.M_Product_ID " +
				"  AND p.IsPurchased='Y' AND rio.InOutType='O' ";

		if (m_Resource.getResourceType().equals(MUNSResource.RESOURCETYPE_4WorkStation))
			sql += " AND rio.UNS_Resource_ID = ?";
		else if (m_Resource.getResourceType().equals(MUNSResource.RESOURCETYPE_3WorkCenter))
			sql += " AND rio.UNS_Resource_ID IN (SELECT ws.UNS_Resource_ID FROM UNS_Resource ws " +
					"	WHERE ws.ResourceParent_ID IN (SELECT wc.UNS_Resource_ID FROM UNS_Resource wc " +
					"		WHERE wc.UNS_Resource_ID=?))";
		else if (m_Resource.getResourceType().equals(MUNSResource.RESOURCETYPE_2ProductionLine))
			sql += " AND rio.UNS_Resource_ID IN (SELECT ws.UNS_Resource_ID FROM UNS_Resource ws " +
					"	WHERE ws.ResourceParent_ID IN (SELECT wc.UNS_Resource_ID FROM UNS_Resource wc " +
					"		WHERE wc.ResourceParent_ID IN (SELECT pl.UNS_Resource_ID FROM UNS_Resource pl " +
					"			WHERE pl.UNS_Resource_ID=?)))";
		else if (m_Resource.getResourceType().equals(MUNSResource.RESOURCETYPE_1Plant))
			sql += " AND rio.UNS_Resource_ID IN (SELECT ws.UNS_Resource_ID FROM UNS_Resource ws " +
					"	WHERE ws.ResourceParent_ID IN (SELECT wc.UNS_Resource_ID FROM UNS_Resource wc " +
					"		WHERE wc.ResourceParent_ID IN (SELECT pl.UNS_Resource_ID FROM UNS_Resource pl " +
					"			WHERE pl.ResourceParent_ID=?)))";
		
		Integer[] materialList = DBUtils.getIntValues(get_TrxName(), sql, m_Resource.get_ID());
		
		//sql = "SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio, M_Product p "
		//	+ "WHERE rio.M_Product_ID=p.M_Product_ID AND rio.InOutType='O' AND p.IsSold='Y' AND rio.UNS_Resource_ID=?";
		String whereClause = "InOutType='O' AND UNS_Resource_ID=? " 
				+ "AND M_Product_ID IN (SELECT p.M_Product_ID FROM M_Product p WHERE p.IsSold='Y')";
		
		List<MUNSResourceInOut> rioList = Query
				.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
					 MUNSResourceInOut.Table_Name, whereClause, get_TrxName())
				.setParameters(m_Resource.get_ID())
				.list();
		
		//Integer[] fgList = DBUtils.getIntValues(get_TrxName(), sql, m_Resource.get_ID());
		
		for(int material : materialList)
		{
			for (MUNSResourceInOut rio : rioList)
			{
				BigDecimal bomQty = MUNSResourceInOut.getBOMQty(rio.getM_Product_ID(), material, get_TrxName());
				
				if (bomQty == null || bomQty.signum() <=0)
					continue;
				
				X_UNS_MRPToFGBOMProduct mrpFGMap = new X_UNS_MRPToFGBOMProduct(getCtx(), 0, get_TrxName());
				
				mrpFGMap.setM_Product_ID(rio.getM_Product_ID());
				mrpFGMap.setM_ProductBOM_ID(material);
				mrpFGMap.setBOMQty(bomQty);
				mrpFGMap.setUNS_Resource_ID(m_Resource.get_ID());
				mrpFGMap.setIsPrimary(rio.isPrimaryOutput());
				
				
				I_M_PartType partType = MProduct.get(getCtx(), material).getM_PartType();
				boolean isMainPart = (partType == null || partType.getM_PartType_ID() == 0)? 
						false : partType.getName().equals("Main Part");
				mrpFGMap.setIsMainPart(isMainPart);
				
				if (!mrpFGMap.save())
					throw new AdempiereException("Failed when saving new MRP-FG BOM Map record.");
			}
		}
		
		return null;
	}
	
	

}
