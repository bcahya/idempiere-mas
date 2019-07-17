/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MProduct;
import org.compiere.util.AdempiereUserError;

/**
 * @author AzHaidar
 *
 */
public class MUNSActualCostItem extends X_UNS_ActualCostItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8852328381266254505L;

	/**
	 * @param ctx
	 * @param UNS_ActualCostItem_ID
	 * @param trxName
	 */
	public MUNSActualCostItem(Properties ctx, int UNS_ActualCostItem_ID,
			String trxName) {
		super(ctx, UNS_ActualCostItem_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSActualCostItem(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * New instance based on itemConfig. It'snt saved yet.
	 * 
	 * @param ctx
	 * @param itemConfig
	 * @param trxName
	 */
	public MUNSActualCostItem(Properties ctx, MUNSActualCostConfigItem itemConfig, MAcctSchema as, String trxName) 
	{
		super(ctx, 0, trxName);
		
		setAD_Org_ID(itemConfig.getAD_Org_ID());
		setName(itemConfig.getName());
		setDescription(itemConfig.getDescription());
		setM_Product_ID(itemConfig.getM_Product_ID());
		setC_UOM_ID(itemConfig.getC_UOM_ID());
		setC_ElementValue_ID(itemConfig.getC_ElementValue_ID());
		setIsJointCost(itemConfig.isJointCost());
		setUNS_JointCostGroup_ID(itemConfig.getUNS_JointCostGroup_ID());
		setDistributableCostAllocation(BigDecimal.ZERO);
		setTotalDistributableCost(BigDecimal.ZERO);
		setProductType(itemConfig.getProductType());
		/**
		List<MCostElement> ceList = 
				MCostElement.getByCostingMethod(ctx, MCostElement.COSTINGMETHOD_StandardCosting);
		MCostElement ce = null;
		for (MCostElement ceTmp : ceList)
		{
			if (ceTmp.isStandardCosting())
			{
				ce = ceTmp;
				break;
			}
		}
		if (ce == null)
			throw new AdempiereUserError("You have not defined any Standard Costs for " + getM_Product().getName());
		
		MCost cost = 
				MCost.get((MProduct) getM_Product(), 0, 
						  (MAcctSchema) getUNS_ActualCostReconcile().getC_AcctSchema(), 
						  0, 
						  ce.get_ID(), 
						  trxName);
		if (cost == null)
			throw new AdempiereUserError("You have not defined any Standard Cost Value for " + getM_Product().getName());
		*/
		BigDecimal currentCost = 
				MCost.getCurrentCost((MProduct) getM_Product(), 0, 
						as, 0, MCostElement.COSTINGMETHOD_StandardCosting, 
						BigDecimal.ONE, 0, false, trxName);
		if (currentCost == null)
			throw new AdempiereUserError("You have not defined any Standard Cost Value for " + getM_Product().getName());
		
		setCurrentCostPrice(currentCost);
	}
}
