/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProductBOM;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.unicore.ui.ISortTabRecord;
import com.uns.model.MProduct;
import com.uns.util.MessageBox;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author YAKA
 * 
 */
public class MUNSProductionOutPlan extends X_UNS_Production_OutPlan implements ISortTabRecord 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5544932027007948033L;
	
	private MUNSProduction parent = null;
	private MUNSResourceInOut rio = null;
	

	/**
	 * @param ctx
	 * @param UNS_Production_OutPlan_ID
	 * @param trxName
	 */
	public MUNSProductionOutPlan(Properties ctx, int UNS_Production_OutPlan_ID,
			String trxName) {
		super(ctx, UNS_Production_OutPlan_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSProductionOutPlan(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}
	
	public MUNSProduction getParent(){
		return new MUNSProduction(getCtx(), getUNS_Production_ID(), get_TrxName());
	}

	/**
	 * 
	 * @param ctx
	 * @param trxName
	 * @param production
	 * @param outputType
	 * @return
	 */
	public static boolean generateOutPlan(Properties ctx, String trxName,
			MUNSProduction production, String outputType) {
		
		MUNSResource rsc = new MUNSResource(ctx, production.getUNS_Resource_ID(), trxName);
		MUNSResourceInOut[] rscOutLines = null;
		
		if (outputType.equals(MUNSProduction.OUTPUTTYPE_Single) 
				|| outputType.equals(MUNSProduction.OUTPUTTYPE_Multi))
			rscOutLines = rsc.getOutputLines();
		else
			rscOutLines = rsc.getOutputLinesForProductOf(production.getM_Product_ID());
		
		int primaryPOP_ID = 0;
		for (MUNSResourceInOut rio : rscOutLines)
		{
			if (rio.isPrimary() || rio.getM_Product_ID() == production.getM_Product_ID()) {
				MUNSProductionOutPlan pop = new MUNSProductionOutPlan(ctx, 0, trxName);

				pop.setUNS_Production_ID(production.getUNS_Production_ID());
				pop.setUNS_Resource_InOut_ID(rio.get_ID());
				pop.setClientOrg(production);
				
				if(rio.getM_Locator_ID() <= 0)
					throw new AdempiereException("Failed when trying to generate output plan: "
							+ "Locator default is not set for resource output of product " 
							+ rio.getM_Product().getValue());
				
				pop.setOutputPlan(rio);
				
				if (rio.isPrimary() && rio.getM_Product_ID()==production.getM_Product_ID()){
					pop.setQtyPlan(production.getProductionQty());
				}
				
				if (!pop.save())
					return false;
				primaryPOP_ID = pop.get_ID();
			}
		}
		
 		for (MUNSResourceInOut rio : rscOutLines) 
 		{
 			if (rio.isPrimary() || rio.getM_Product_ID() == production.getM_Product_ID())
 				continue;
 			
			MUNSProductionOutPlan pop = new MUNSProductionOutPlan(ctx, 0, trxName);

			pop.setUNS_Production_ID(production.getUNS_Production_ID());
			pop.setClientOrg(production);
			
			if(rio.getM_Locator_ID() <= 0)
				throw new AdempiereException("Failed when trying to generate output plan: "
						+ "Locator default is not set for resource output of product " 
						+ rio.getM_Product().getValue());
			
			pop.setOutputPlan(rio);
			
			pop.setQtyPlan(BigDecimal.valueOf(-1));
			
			if (!rio.isPrimary() && (production.isMulti() || production.isMultiOptional()))
				pop.setPrimaryOutput_ID(primaryPOP_ID);
			
			if (!pop.save())
				return false;
		}
 		
 		production.setGenerateOutPlan("Y");
 		production.isGeneratingOutPlan = true;
 		boolean succeed = production.save();
 		production.isGeneratingOutPlan = false;
		
 		return succeed;
	}

	private void setOutputPlan(MUNSResourceInOut rscOut)
	{
		setM_Locator_ID(rscOut.getM_Locator_ID());
		setM_Product_ID(rscOut.getM_Product_ID());
		setC_UOM_ID(rscOut.getUOMCaps_ID());
		setIsPrimary(rscOut.isPrimary());
		setUNS_Resource_InOut_ID(rscOut.get_ID());
	}

	@Override
	public String beforeSaveTabRecord(int parentRecord_ID) 
	{
		if (rio == null)
			rio = new MUNSResourceInOut(getCtx(), getUNS_Resource_InOut_ID(), get_TrxName());
		//setUNS_Production_ID(parentRecord_ID);
		if (parent == null)
			parent = (MUNSProduction) getUNS_Production();
		
		setM_Product_ID(rio.getM_Product_ID());
		setIsPrimary(rio.isPrimary());
		setM_Locator_ID(rio.getM_Locator_ID());
		
		MProduct product = MProduct.get(getCtx(), getM_Product_ID());
		
		setC_UOM_ID(product.getC_UOM_ID());
		
		BigDecimal qtyPlanned = BigDecimal.valueOf(-1);
		
		if (parent.getProductionQty().compareTo(Env.ZERO) > 0 
			&& (rio.isMulti() && getM_Product_ID() != parent.getM_Product_ID()))
		{
			MProduct primaryProduct = MProduct.get(getCtx(), parent.getM_Product_ID());
			
			BigDecimal mainPrimaryBomQty = 
					MUNSResourceInOut.getMainPartBOMQtyOf(primaryProduct, MProductBOM.BOMTYPE_StandardPart);
			BigDecimal currProductBOMQty = 
					MUNSResourceInOut.getMainPartBOMQtyOf(product, MProductBOM.BOMTYPE_StandardPart);
			
			if (mainPrimaryBomQty.compareTo(Env.ZERO) > 0 || currProductBOMQty.compareTo(Env.ZERO) > 0)
			{
				BigDecimal mainInputQty = parent.getProductionQty().multiply(mainPrimaryBomQty);
				
				qtyPlanned = mainInputQty.divide(currProductBOMQty, product.getUOMPrecision(), BigDecimal.ROUND_HALF_UP);
			}			
		}
		else if (getM_Product_ID() == parent.getM_Product_ID()) {
			qtyPlanned = parent.getProductionQty();
		}
		
		setQtyPlan(qtyPlanned);
		
		return null;
	}
	
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (parent == null)
			parent = (MUNSProduction) getUNS_Production();
		
		if (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Single)
				|| parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Optional))
			setIsPrimary(true);
			
		if (newRecord)
		{
			if (rio == null && getUNS_Resource_InOut_ID() == 0) 
			{
				if (!parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Single))
				{
					if (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_MultiOptional))
					{
						throw new AdempiereException ("You can only add optional, multi or multi-optional output "
							+ "plan from output selection tab.");
					}
					else { // Optional or multi output.
						
						String sql = "SELECT UNS_Resource_InOut_ID FROM UNS_Resource_InOut "
								+ "WHERE UNS_Resource_ID=? AND M_Product_ID=?";
						
						if (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Multi))
							sql += " AND IsPrimary=" + (this.isPrimary()? "'Y'" : "'N'");
						
						int rio_ID = 
								DB.getSQLValueEx(
										get_TrxName(), sql, parent.getUNS_Resource_ID(), getM_Product_ID());
						
						if (rio_ID <= 0)
							throw new AdempiereException (
									"Cannot add an output that is not defined in Manufacturing Resource.");
						
						setUNS_Resource_InOut_ID(rio_ID);
					}
				}
				else // Single output type.
				{
					return true;
				}
			}
			else if (rio == null)
				rio = new MUNSResourceInOut(getCtx(), getUNS_Resource_InOut_ID(), get_TrxName());
			
			if (!rio.isPrimary())
			{
				String sql;
				if (rio.isMultiOptional())
					sql = "SELECT UNS_Production_OutPlan_ID FROM UNS_Production_OutPlan "
							+ "WHERE UNS_Production_ID=? AND UNS_Resource_InOut_ID=" 
							+ rio.getUNS_OutputLink_ID();
				else
					sql = "SELECT UNS_Production_OutPlan_ID FROM UNS_Production_OutPlan "
							+ "WHERE UNS_Production_ID=? AND IsPrimary='Y'";
				
				int primaryOutput_ID = DB.getSQLValueEx(get_TrxName(), sql, getUNS_Production_ID());
				
				if (primaryOutput_ID > 0)
					setPrimaryOutput_ID(primaryOutput_ID);
				else 
					throw new AdempiereException("Faield: A non-primary output can only be added "
							+ "after it's primary output has been added.\nYou can do multi selection on output "
							+ "selection tab with Primary-Output is on top of list.");
			}
			
			return true;
		}
		
		if (is_ValueChanged(COLUMNNAME_QtyPlan) 
				|| is_ValueChanged(COLUMNNAME_M_Locator_ID) 
				|| is_ValueChanged(COLUMNNAME_BOMType))
		{
			//String oldBOMType = getBOMType();
			
			//if (is_ValueChanged(COLUMNNAME_BOMType))
			//	oldBOMType = (String) get_ValueOld(COLUMNNAME_BOMType);
			String whereClause = "UNS_Production_ID=? AND M_Product_ID=? AND IsEndProduct='Y'";
			
			if (getUNS_Resource_InOut_ID() > 0)
				whereClause += " AND UNS_Resource_InOut_ID=" + getUNS_Resource_InOut_ID();
			
			MUNSProductionDetail outDetail = 
					Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSProductionDetail.Table_Name, 
							whereClause, get_TrxName())
						 .setParameters(getUNS_Production_ID(), getM_Product_ID())
						 .first();
			
			//outDetail.setBOMType(getBOMType());
			outDetail.setM_Locator_ID(getM_Locator_ID());
			
			if (is_ValueChanged(COLUMNNAME_QtyPlan))
				outDetail.setPlannedQty(getQtyPlan());
			
			boolean isChangeDetailMovementQty = false;
			
			if (outDetail.getQtyUsed().signum() <= 0)
			{ 
			  // artinya output detail belum di set nilai used dan movement qty nya, sdgkan user 
			  // merevisi output plan yang sangat mungkin merupakan nilai output sebenarnya, shg planned qty == movementQty.
				isChangeDetailMovementQty = true;
			}
			else if (is_ValueChanged(COLUMNNAME_QtyPlan)){
				String msg = Msg.getMsg(getCtx(), "UseAndMovementQuantityChangeMsg");
				String title = Msg.getMsg(getCtx(), "UseAndMovementQuantityChangeTitle");
				int answer = MessageBox.showMsg(this, getCtx(), msg, title, MessageBox.YESNO, MessageBox.ICONQUESTION);
				if (answer == MessageBox.RETURN_YES)
					isChangeDetailMovementQty = true;
			}
			
			if (isChangeDetailMovementQty) {
				outDetail.setQtyUsed(getQtyPlan());
				outDetail.setMovementQty(getQtyPlan());
			}
			
			outDetail.saveEx();
		}
		
		return true;
	}		
	
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!newRecord || !success)
			return true;
		
		if (parent == null)
			parent = (MUNSProduction) getUNS_Production();
		
		String sql = "SELECT UNS_Production_Detail_ID FROM UNS_Production_Detail"
				+ " WHERE UNS_Production_ID=? AND M_Product_ID=? AND IsEndProduct='Y'";
		
		if(getUNS_Resource_InOut_ID() > 0)
			sql += " AND UNS_Resource_InOut_ID=" + getUNS_Resource_InOut_ID();
		
		int existingPD = 
				DB.getSQLValueEx(
						get_TrxName(), sql, getUNS_Production_ID(), getM_Product_ID());
//		MUNSProductionDetail outDetail = 
//				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSProductionDetail.Table_Name, 
//						"UNS_Production_ID=? AND M_Product_ID=? AND IsEndProduct='Y' AND BOMType=?", get_TrxName())
//					 .setParameters(getUNS_Production_ID(), getM_Product_ID(), getBOMType())
//					 .first();
		if (existingPD <= 0)
		{
			MUNSProductionDetail outDetail = new MUNSProductionDetail(getCtx(), 0, get_TrxName());
			
			outDetail.setM_Product_ID(getM_Product_ID());
			outDetail.setM_Locator_ID(getM_Locator_ID());
			outDetail.setUNS_Production_ID(getUNS_Production_ID());
			outDetail.setIsPrimary(isPrimary());
			outDetail.setIsEndProduct(true);
			outDetail.setBOMType(getBOMType());
			outDetail.setPlannedQty(getQtyPlan());
			outDetail.setQtyUsed(BigDecimal.valueOf(-1));
			outDetail.setUNS_Resource_InOut_ID(getUNS_Resource_InOut_ID());
			outDetail.setAD_Org_ID(getAD_Org_ID());
			if (!outDetail.save())
				return false;
		}
		
		if (!newRecord && is_ValueChanged(COLUMNNAME_QtyPlan))
		{
			String updateHeader = 
					"UPDATE UNS_Production SET ProductionQty=? WHERE UNS_Production_ID=?";
			DB.executeUpdateEx(
					updateHeader, new Object[]{getQtyPlan(), getUNS_Production_ID()}, get_TrxName());
		}
		
		return true;
	}
	
	@Override
	protected boolean afterDelete(boolean success)
	{
		if (!success)
			return true;
		
		if (this.isPrimary()) {
			StringBuilder sql = 
					new StringBuilder ("DELETE FROM UNS_Production_Detail ")
					.append("WHERE PrimaryOutput_ID = ")
					.append(" (SELECT pd.UNS_Production_Detail_ID FROM UNS_Production_Detail pd ")
					.append("	WHERE pd.UNS_Production_ID=? AND pd.M_Product_ID=? AND pd.IsPrimary='Y')");
			
			DB.executeUpdateEx(sql.toString(), 
					new Object[]{getUNS_Production_ID(), getM_Product_ID()}, 
					get_TrxName());
		}
	
		StringBuilder sql = 
				new StringBuilder ("DELETE FROM UNS_Production_Detail ")
				.append("WHERE UNS_Production_ID=? AND M_Product_ID=? AND IsEndProduct='Y'");// AND BOMType=?");
		
		if (getUNS_Resource_InOut_ID() > 0)
			sql.append(" AND UNS_Resource_InOut_ID=").append(getUNS_Resource_InOut_ID());
		
		DB.executeUpdateEx(sql.toString(), 
				new Object[]{getUNS_Production_ID(), getM_Product_ID()},// "Y", getBOMType()}, 
				get_TrxName());
	
		
		return true;
	}

	@Override
	public String beforeRemoveSelection() {
		// TODO Auto-generated method stub
		return null;
	}

}
