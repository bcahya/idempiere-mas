/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.compiere.model.MStorageOnHand;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.I_UNS_Production;
import com.uns.model.I_UNS_ProductionSchedule;
import com.uns.model.I_UNS_Production_Detail;
import com.uns.model.I_UNS_Production_Worker;
import com.uns.model.I_UNS_SrcProduct_Detail;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSProduction;
import com.uns.model.MUNSProductionDetail;
import com.uns.model.MUNSProductionWorker;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSResourceInOut;
import com.uns.model.MUNSSERLineProduct;
import com.uns.model.MUNSSrcProductDetail;
import com.uns.model.X_UNS_ProductionSchedule;
import com.uns.model.X_UNS_SrcProduct_Detail;
import com.uns.model.factory.UNSPPICModelFactory;



/**
 * @author YAKA
 *
 */
public class CalloutProduction extends CalloutEngine implements IColumnCallout {

	/**
	 * 
	 */
	public String selectWorkStation(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer rsc_ID = (Integer)value;
		if (rsc_ID == null || rsc_ID.intValue() == 0)
			return "";

		MUNSResource rsc = new MUNSResource(ctx, rsc_ID, null);
		int count = getCountDB(rsc_ID);
		MUNSResourceInOut rscIO = getResourceInOut(ctx, rsc_ID, false);
		
		if (count<0)
			throw new AdempiereException("Error when search resource output.");
		else if (count==1){
			mTab.setValue("OutputType", MUNSProduction.OUTPUTTYPE_Single);
			mTab.setValue("M_Locator_ID", rscIO.getM_Locator_ID());
			mTab.setValue("M_Product_ID", rscIO.getM_Product_ID());
		} else if (MUNSProduction.OUTPUTTYPE_Optional.equals(rscIO.getOutputType())){
			mTab.setValue("OutputType", rscIO.getOutputType());
			mTab.setValue("M_Locator_ID", rscIO.getM_Locator_ID());
		} else if (MUNSProduction.OUTPUTTYPE_MultiOptional.equals(rscIO.getOutputType())){
			rscIO = getResourceInOut(ctx, rsc_ID, true);
			
			mTab.setValue("OutputType", rscIO.getOutputType());
			mTab.setValue("M_Locator_ID", rscIO.getM_Locator_ID());
		} else if (count>1){
			rscIO = rsc.getPrimaryOutput();
			
			mTab.setValue("OutputType", rscIO.getOutputType());
			mTab.setValue("M_Locator_ID", rscIO.getM_Locator_ID());
			mTab.setValue("M_Product_ID", rscIO.getM_Product_ID());
		}
		else
			throw new AdempiereException("Not found resource output.");
		
		mTab.setValue(MUNSProduction.COLUMNNAME_Supervisor_ID, rsc.getParent().getSupervisor_ID());
		mTab.setValue(MUNSProduction.COLUMNNAME_WorkerResultType, rsc.getPaymentMethod());
		mTab.setValue(MUNSResource.COLUMNNAME_MustSyncWithMPS, rsc.isMustSyncWithMPS());
		mTab.setValue(MUNSResource.COLUMNNAME_IsWorkerBase, rsc.isWorkerBase());
		mTab.setValue(MUNSResource.COLUMNNAME_IsEndingStockBase, rsc.isEndingStockBase());

		return "";
	}

	public String calculatePlanQty(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) 
	{	
		BigDecimal endingQty = (BigDecimal)value;
		if (endingQty == null || endingQty.compareTo(Env.ZERO) == 0)
			return "";
		
		BigDecimal available = (BigDecimal) mTab.getValue(MUNSProductionDetail.COLUMNNAME_QtyAvailable);	
		//mTab.setValue(MUNSProductionDetail.COLUMNNAME_PlannedQty, available.subtract(endingQty));
		mTab.setValue(MUNSProductionDetail.COLUMNNAME_MovementQty, available.subtract(endingQty).negate());
		mTab.setValue(MUNSProductionDetail.COLUMNNAME_QtyUsed, available.subtract(endingQty));
				
		return "";
	}
	
	public String negateMovementQty(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) 
	{	
		BigDecimal endingQty = (BigDecimal)value;
		if (endingQty == null || endingQty.compareTo(Env.ZERO) == 0)
			return "";
		if (endingQty.intValue()>0 
				&& !mTab.getValueAsBoolean(MUNSProductionDetail.COLUMNNAME_IsEndProduct))
			mTab.setValue(MUNSProductionDetail.COLUMNNAME_MovementQty, endingQty.negate());
				
		return "";
	}
	
	private String negateMovementQtyL1to4(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) 
	{
		BigDecimal levelQty = (BigDecimal)value;
		if (levelQty == null || levelQty.compareTo(Env.ZERO) == 0)
			return "";
		if (levelQty.intValue()>0 
				&& !mTab.getValueAsBoolean(MUNSProductionDetail.COLUMNNAME_IsEndProduct))
			mTab.setValue(mField.getColumnName(), levelQty.negate());
		
		return "";
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String SERLine(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		Integer UNS_SERLineProduct_ID = (Integer)value;
		
		if (null == UNS_SERLineProduct_ID || UNS_SERLineProduct_ID.intValue() <=0)
			return "";
		
		if (isCalloutActive())
			return "";
		
		MUNSSERLineProduct SERLineProduct = new MUNSSERLineProduct(ctx, UNS_SERLineProduct_ID, null);
		int M_Product_ID = SERLineProduct.getM_Product_ID();
		mTab.setValue(X_UNS_ProductionSchedule.COLUMNNAME_SourceProduct_ID, M_Product_ID);
		
		return "";
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return ""
	 */
	private String Product( Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		Integer M_Product_ID = (Integer) value;
		BigDecimal qtyMT = BigDecimal.ZERO;
		BigDecimal qtyUOM = BigDecimal.ZERO;
		String PSType = (String) mTab.getValue(X_UNS_ProductionSchedule.COLUMNNAME_PSType);
		
		if (PSType.equals(X_UNS_ProductionSchedule.PSTYPE_MasterProductionSchedule))
			return "";
		
		if (null == M_Product_ID || M_Product_ID.intValue() <= 0)
			return "";
		
		if (isCalloutActive())
			return "";
		
		MProduct product = MProduct.get(ctx, M_Product_ID);
	
		MStorageOnHand[] storageOnHand = MStorageOnHand.getOfProduct(ctx, M_Product_ID, null);
		for (MStorageOnHand soh : storageOnHand)
		{
			qtyUOM = qtyUOM.add(soh.getQtyOnHand());
		}
		
		qtyMT = product.getWeight().multiply(qtyUOM);
		
		mTab.setValue(X_UNS_ProductionSchedule.COLUMNNAME_QtyOnHand, qtyUOM);
		mTab.setValue(X_UNS_ProductionSchedule.COLUMNNAME_QtyMTOnHand, qtyMT);
		
		return "";
	}
	

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) 
	{
		String retValue = null;
		if (I_UNS_Production.COLUMNNAME_UNS_Resource_ID.equals(mField.getColumnName()))
		{
			retValue = selectWorkStation(ctx, WindowNo, mTab, mField, value);
		}
		else if (I_UNS_Production.COLUMNNAME_M_Product_ID.equals(mField.getColumnName()))
		{
			retValue = selectProduct(ctx, WindowNo, mTab, mField, value);
		}
		else if (I_UNS_Production_Detail.COLUMNNAME_EndingStock.equals(mField.getColumnName()))
		{
			retValue = calculatePlanQty(ctx, WindowNo, mTab, mField, value);
		}
		else if (I_UNS_ProductionSchedule.COLUMNNAME_SourceProduct_ID.equals(mField.getColumnName()))
		{
			retValue = Product(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (I_UNS_ProductionSchedule.COLUMNNAME_SourceProduct_ID.equals(mField.getColumnName()))
		{
			retValue = SERLine(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (I_UNS_SrcProduct_Detail.COLUMNNAME_M_AttributeSetInstance_ID.equals(mField.getColumnName()))
		{
			retValue = AttributeInstance(ctx, WindowNo, mTab, mField, value, oldValue);
		} 
		else if (I_UNS_Production_Detail.COLUMNNAME_MovementQty.equals(mField.getColumnName()))
		{
			retValue = negateMovementQty(ctx, WindowNo, mTab, mField, value);
		}
		else if (I_UNS_Production_Detail.COLUMNNAME_UOMMovementQtyL1.equals(mField.getColumnName())
				|| I_UNS_Production_Detail.COLUMNNAME_UOMMovementQtyL2.equals(mField.getColumnName())
				|| I_UNS_Production_Detail.COLUMNNAME_UOMMovementQtyL3.equals(mField.getColumnName())
				|| I_UNS_Production_Detail.COLUMNNAME_UOMMovementQtyL4.equals(mField.getColumnName()))
		{
			retValue = negateMovementQtyL1to4(ctx, WindowNo, mTab, mField, value);
		}
		else if (I_UNS_Production_Worker.COLUMNNAME_Labor_ID.equals(mField.getColumnName()))
		{
			retValue = setFieldsBasedOnLabor(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (I_UNS_Production_Worker.COLUMNNAME_ReplacementLabor_ID.equals(mField.getColumnName()))
		{
			retValue = setFieldsBasedOnReplacementLabor(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		return retValue;
	}
	
	
	private String selectProduct(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		Integer M_Product_ID = (Integer) value;
		
		if (null == M_Product_ID || M_Product_ID.intValue() <= 0)
			return "";
		
		if (isCalloutActive())
			return "";
		
		MUNSResource rsc = new MUNSResource(ctx, (Integer) mTab.getValue(MUNSProduction.COLUMNNAME_UNS_Resource_ID), null);
		for (MUNSResourceInOut rscIO : rsc.getOutputLines()){
			if (rscIO.getM_Product_ID()==M_Product_ID)
				mTab.setValue(MUNSProduction.COLUMNNAME_M_Locator_ID, rscIO.getM_Product_ID());
		}
		
		return null;
	}

	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String AttributeInstance(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		Integer M_AttributeSetInstance_ID = (Integer)value;

		if (null == M_AttributeSetInstance_ID || M_AttributeSetInstance_ID.intValue() <=0)
			return "";
		
		GridTab parent = mTab.getParentTab();
		MProduct product = MProduct.get(
				ctx,(Integer) parent.getValue(X_UNS_ProductionSchedule.COLUMNNAME_SourceProduct_ID));
		BigDecimal weight = product.getWeight();
		
		BigDecimal qtyUOM = BigDecimal.ZERO;
		BigDecimal qtyMT = BigDecimal.ZERO;
		MStorageOnHand[] storageOnHands = MUNSSrcProductDetail.getStorageByAtribute(M_AttributeSetInstance_ID, null);
		for (MStorageOnHand storageOnHand : storageOnHands)
		{
			qtyUOM = storageOnHand.getQtyOnHand();
		}
		
		qtyMT = qtyUOM.multiply(weight);
		mTab.setValue(X_UNS_SrcProduct_Detail.COLUMNNAME_QtyUom, qtyUOM);
		mTab.setValue(X_UNS_SrcProduct_Detail.COLUMNNAME_QtyMT, qtyMT);
		
		return "";
	}
	
	/**
	 * Set default production worker's fields.
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String setFieldsBasedOnLabor(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (value == null)
			return null;
		
		MUNSEmployee labor = MUNSEmployee.get(ctx, (Integer) value);
		
		if (labor == null)
			return "Cannot find worker's detail record.";
		
		mTab.setValue(MUNSProductionWorker.COLUMNNAME_C_BPartner_ID, labor.getVendor_ID());
		mTab.setValue(MUNSProductionWorker.COLUMNNAME_ReplacementLabor_ID, labor.get_ID());
		
		GridTab parentTab = mTab.getParentTab();
		
		int rscId = (Integer) parentTab.getValue(MUNSProduction.COLUMNNAME_UNS_Resource_ID);
		
		MUNSResource rsc = new MUNSResource (ctx, rscId, null);
		
		mTab.setValue(MUNSProductionWorker.COLUMNNAME_IsAdditional, rsc.isAdditionalWorker(labor.get_ID()));
		
		return null;
	}

	/**
	 * Set default production worker's fields.
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String setFieldsBasedOnReplacementLabor(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (value == null)
			return null;
		
		MUNSEmployee labor = MUNSEmployee.get(ctx, (Integer) value);
		
		if (labor == null)
			return "Cannot find worker's detail record.";
		
		mTab.setValue(MUNSProductionWorker.COLUMNNAME_C_BPartner_ID, labor.getVendor_ID());
		
		GridTab parentTab = mTab.getParentTab();
		
		int rscId = (Integer) parentTab.getValue(MUNSProduction.COLUMNNAME_UNS_Resource_ID);
		
		MUNSResource rsc = new MUNSResource (ctx, rscId, null);
		
		mTab.setValue(MUNSProductionWorker.COLUMNNAME_IsAdditional, rsc.isAdditionalWorker(labor.get_ID()));
		
		return null;
	}
	

	private int getCountDB(int rsc_id){
		String sql = "SELECT count(*) FROM UNS_Resource_InOut WHERE InOutType='"
				+ MUNSResourceInOut.INOUTTYPE_Output + "' AND UNS_Resource_ID="
				+ rsc_id;
		return DB.getSQLValue(null, sql);
	}
	
	private MUNSResourceInOut getResourceInOut(Properties ctx, int rsc_id, boolean isMulti){
		String whereClause = "InOutType='" + MUNSResourceInOut.INOUTTYPE_Output + "' " 
				+ "AND UNS_Resource_ID=" + rsc_id;
		
		if (isMulti)
			whereClause= whereClause + " AND IsPrimary='Y'";
		
		MUNSResourceInOut io = Query.get(ctx, UNSPPICModelFactory.getExtensionID(), 
				MUNSResourceInOut.Table_Name, whereClause, null).first();
		
		return io;
	}
	
}
