/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MProductPricing;
import org.compiere.model.ProductCost;
import org.compiere.util.Env;

import com.uns.base.acct.DocLine;
import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author YAKA
 *
 */
public class MUNSMOutCostingConfig extends X_UNS_MOutCostingConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4028924034103170942L;

	/**
	 * @param ctx
	 * @param UNS_MOutCostingConfig_ID
	 * @param trxName
	 */
	public MUNSMOutCostingConfig(Properties ctx, int UNS_MOutCostingConfig_ID,
			String trxName) {
		super(ctx, UNS_MOutCostingConfig_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMOutCostingConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param product_id
	 * @param trxName
	 * @return
	 */
	public static MUNSMOutCostingConfig get(Properties ctx, int product_id, String trxName)
	{
		/*
		MUNSMOutCostingConfig costing = null;
		String sql = "SELECT * FROM UNS_MOutCostingConfig WHERE M_Product_ID = " + product_id;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();
			if (rs.next())
				costing = new MUNSMOutCostingConfig(ctx, rs, trxName);
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (SQLException ex) {
			throw new AdempiereException("Unable to load costing configuration",
					ex);
		} finally {
			DB.close(rs, pstmt);
		}
		*/
		return get(ctx, product_id, false, trxName);
	}

	/**
	 * 
	 * @param ctx
	 * @param product_id
	 * @param isProductCostConversion
	 * @param trxName
	 * @return
	 */
	public static MUNSMOutCostingConfig get(
			Properties ctx, int product_id, boolean isProductCostConversion, String trxName)
	{
		MUNSMOutCostingConfig costing = null;
		String sql = "M_Product_ID=" + product_id;
		if (isProductCostConversion)
			sql += " AND M_Product_To_ID IS NOT NULL AND CostingMethod='" 
				+ MUNSMOutCostingConfig.COSTINGMETHOD_ConversionOfProduct + "'";
		
		costing = Query
				.get(ctx, UNSPPICModelFactory.getExtensionID(), MUNSMOutCostingConfig.Table_Name, sql, trxName)
				.first();
		
		return costing;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param trxName
	 * @param as
	 * @param org_id
	 * @param costZero
	 * @param line
	 * @param lines
	 * @param bomCost
	 * @return
	 */
	public static BigDecimal getNotSetCosting(Properties ctx, String trxName, MAcctSchema as, 
			int org_id, boolean isMultiOptional, boolean costZero, 
			DocLine line, DocLine[] lines, BigDecimal bomCost) 
	{
//		BigDecimal outputPortion = Env.ZERO;
//		
//		if (isMultiOptional) {
//			BigDecimal totalPrimaryOutput = line.getQty();
//			
//			for (int i=0; i < lines.length; i++)
//			{
//				MUNSProductionDetail pd = (MUNSProductionDetail) lines[i].getPO();
//				if (pd.isPrimary()) {
//					totalPrimaryOutput = totalPrimaryOutput.add(pd.getQtyUsed());
//				}
//			}
//			outputPortion = line.getQty().divide(totalPrimaryOutput, 10, BigDecimal.ROUND_UNNECESSARY);
//		}
		//int currPrecision = as.getStdPrecision();
		
		BigDecimal[] cost = new BigDecimal[2];
		for (DocLine line0 : lines)
		{
			MUNSProductionDetail pd = (MUNSProductionDetail) line0.getPO();
			
			if (!pd.isEndProduct() || pd.isPrimary())
				continue;
			
			BigDecimal outputPortion = Env.ONE;
			
			if (pd.getPrimaryOutput_ID() <= 0) 
			{
				BigDecimal totalPrimariesOutputQty = Env.ZERO;
				Collection<MUNSProductionDetail> relatedPrimaryList = pd.getAllPrimaryOutputMap().values();
			
				for (MUNSProductionDetail primary : relatedPrimaryList) {
					totalPrimariesOutputQty = totalPrimariesOutputQty.add(primary.getMovementQty());
				}
				
				outputPortion = pd.getPrimaryOutput().getMovementQty()
						.divide(totalPrimariesOutputQty, 10, BigDecimal.ROUND_UNNECESSARY);
			}
			
			MUNSMOutCostingConfig multiOut = MUNSMOutCostingConfig.get(ctx, line0.getM_Product_ID(), trxName);
			
			if (multiOut != null)
			{
				if (line.getQty().signum()>0){
					cost = multiOut.getCosting(as, org_id, costZero, line0, bomCost, outputPortion);
					bomCost = cost[1];
				}
			}
		}
		return bomCost;
	}
	
	/**
	 * Get standard or conversion based costing of a product represented in line. 
	 * @param as
	 * @param org_id
	 * @param costZero
	 * @param line
	 * @param bomCost
	 * @return return 2 row of BigDecimal, in which index 0 is the converted cost, and index 1 is the difference 
	 *         between bomCost to converted cost.
	 */
	public BigDecimal[] getCosting(MAcctSchema as, int org_id, 
								   boolean costZero, DocLine line, 
								   BigDecimal bomCost)
	{	
		return getCosting(as, org_id, costZero, line, bomCost, Env.ONE);
	}

	/**
	 * Get standard or conversion based costing of a product represented in line.
	 *  
	 * @param as
	 * @param org_id
	 * @param costZero
	 * @param line
	 * @param bomCost
	 * @param qtyPortion the portion of non-primary line's quantity to be calculated.
	 * @return return 2 row of BigDecimal, in which index 0 is the converted cost, and index 1 is the difference 
	 *         between bomCost to converted cost.
	 */
	public BigDecimal[] getCosting(MAcctSchema as, int org_id, 
								   boolean costZero, DocLine line, 
								   BigDecimal bomCost, BigDecimal qtyPortion)
	{
		BigDecimal[] cost = new BigDecimal[2];		
		if(COSTINGMETHOD_StandartCosting.equals(getCostingMethod())){
			cost[0] = getStandartCosting().multiply(line.getQty().multiply(qtyPortion));
			cost[1] = bomCost.subtract(cost[0]);
		} else if(COSTINGMETHOD_ConversionOfProduct.equals(getCostingMethod())){
			
			BigDecimal convertingCost = BigDecimal.ZERO; 
			if (CONVERSIONBASED_PriceAtTrxDate.equals(getConversionBased()))
			{
				MProductPricing pp = 
						new MProductPricing(
								getConversionProduct_ID(), 0, line.getQty().multiply(qtyPortion), false, 0);
				pp.setM_PriceList_ID(getM_PriceList_ID());
				pp.setPriceDate(line.getDateDoc());
				convertingCost = pp.getPriceStd();
			}
			else {			
				ProductCost pc = new ProductCost(getCtx(), getConversionProduct_ID(), 
												 line.getM_AttributeSetInstance_ID(), get_TrxName());
				pc.setQty(BigDecimal.ONE);
				convertingCost = pc.getProductCosts(as, org_id, null, line.getC_OrderLine_ID(), costZero);
				if (convertingCost == null || convertingCost.signum() <= 0)
				{
					MProductPricing pp = 
							new MProductPricing(
									getConversionProduct_ID(), 0, line.getQty().multiply(qtyPortion), false, 0);
					pp.setM_PriceList_ID(getM_PriceList_ID());
					pp.setPriceDate(line.getDateDoc());
					convertingCost = pp.getPriceStd();
				}
			}
			if (!costZero && (convertingCost == null || convertingCost.signum() <= 0))
				throw new AdempiereException("Zero cost for conversion product of " 
											 + line.getProduct().getName());
			convertingCost = 
					convertingCost.multiply(
							getConversionPercent().divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP));
			cost[0] = convertingCost.multiply(line.getQty().multiply(qtyPortion));
			cost[1] = bomCost.subtract(cost[0]);
		}
		
		return cost;
	}
}
