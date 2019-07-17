/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;
import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_M_PartType;
import org.compiere.model.MProduct;
import org.compiere.model.MProductBOM;
import org.compiere.util.AdempiereUserError;

import com.uns.model.I_UNS_Resource_InOut;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSResourceInOut;
import com.uns.model.X_UNS_Resource_InOut;

/**
 * @author menjangan
 *
 */
public class CalloutResource extends CalloutEngine implements IColumnCallout {

	I_UNS_Resource_InOut m_Output;
	
	/**
	 * 
	 */
	public CalloutResource() {
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue = doConvert(ctx, WindowNo, mTab, mField, value, oldValue);
		return retValue;
	}
	
	
	/**
	 * 
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String maxCapsMT(
			Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal maxCapsMT = (BigDecimal)value;
		
		Object objProdID = mTab.getValue(X_UNS_Resource_InOut.COLUMNNAME_M_Product_ID);
		
		if (objProdID == null) return null;
		
		int M_Product_ID = (Integer) objProdID;
		
		if (null == maxCapsMT
				|| maxCapsMT.compareTo(BigDecimal.ZERO) <= 0)
			return "";
		
		if (M_Product_ID <= 0)
			return "";
		
		if (isCalloutActive())
			return "";
		
		MProduct product = MProduct.get(ctx, M_Product_ID);
		BigDecimal qtyUOM = convertMTToUOM(product, maxCapsMT);
		mTab.setValue(X_UNS_Resource_InOut.COLUMNNAME_MaxCaps, qtyUOM);
		
		return "";
	}
	
	
	/**
	 * 
	 * @param ctx
	 * @param windowNo
	 * @param mField
	 * @param mTab
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String maxCapsUOM(
			Properties ctx, int windowNo, GridField mField, GridTab mTab, Object value, Object oldValue)
	{
		BigDecimal maxCapsUOM = (BigDecimal) value;

		Object objProdID = mTab.getValue(X_UNS_Resource_InOut.COLUMNNAME_M_Product_ID);
		
		if (objProdID == null) return null;
		
		int M_Product_ID = (Integer) objProdID;
		
		if (null == maxCapsUOM
				|| maxCapsUOM.compareTo(BigDecimal.ZERO) <= 0)
			return "";
		
		if (M_Product_ID <= 0)
			return "";
		
		if (isCalloutActive())
			return "";
		
		BigDecimal qtyMT = convertUOMToMT(MProduct.get(ctx, M_Product_ID), maxCapsUOM);
		mTab.setValue(X_UNS_Resource_InOut.COLUMNNAME_MaxCapsMT, qtyMT);
		
		
		return "";
	}
	
	

	/**
	 * 
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String optimumMaxCapsMT(
			Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (null == value)
			return "";
		BigDecimal maxCapsMT = (BigDecimal)value;

		Object objProdID = mTab.getValue(X_UNS_Resource_InOut.COLUMNNAME_M_Product_ID);
		
		if (objProdID == null) return null;
		
		int M_Product_ID = (Integer) objProdID;
		
		if (M_Product_ID <= 0)
			return "";
		
		if (null == maxCapsMT
			|| maxCapsMT.compareTo(BigDecimal.ZERO) <= 0)
			return "";
		
		if (isCalloutActive())
			return "";
		
		MProduct product = MProduct.get(ctx, M_Product_ID);
		BigDecimal qtyUOM = convertMTToUOM(product, maxCapsMT);
		mTab.setValue(X_UNS_Resource_InOut.COLUMNNAME_OptimumCaps, qtyUOM);
		
		return "";
	}
	
	
	/**
	 * 
	 * @param ctx
	 * @param windowNo
	 * @param mField
	 * @param mTab
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String optimumMaxCapsUOM(
			Properties ctx, int windowNo, GridField mField, GridTab mTab, Object value, Object oldValue)
	{
		BigDecimal maxCapsUOM = (BigDecimal) value;

		Object objProdID = mTab.getValue(X_UNS_Resource_InOut.COLUMNNAME_M_Product_ID);
		
		if (objProdID == null) return null;
		
		int M_Product_ID = (Integer) objProdID;
		
		if (null == maxCapsUOM
				|| maxCapsUOM.compareTo(BigDecimal.ZERO) <= 0)
			return "";
		
		if (M_Product_ID <= 0)
			return "";
		
		if (isCalloutActive())
			return "";
		
		BigDecimal qtyMT = convertUOMToMT(MProduct.get(ctx, M_Product_ID), maxCapsUOM);
		mTab.setValue(X_UNS_Resource_InOut.COLUMNNAME_OptimumCapsMT, qtyMT);
		
		
		return "";
	}
	
	/**
	 * 
	 * @param product
	 * @param maxCapsMT
	 * @return
	 */
	private BigDecimal convertMTToUOM(MProduct product, BigDecimal maxCapsMT)
	{
		BigDecimal qtyUOM = BigDecimal.ZERO;
		BigDecimal weight = product.getWeight();
		if (null == weight || weight.compareTo(BigDecimal.ZERO) <= 0)
		{
			throw new AdempiereUserError(
					"Please Devined Weight " + product.getValue() + product.getName());
		}
		qtyUOM = maxCapsMT.multiply(new BigDecimal(1000));
		qtyUOM = qtyUOM.divide(weight, 2, RoundingMode.HALF_UP);
		return qtyUOM;
	}
	
	/**
	 * 
	 * @param product
	 * @param maxCapsUOM
	 * @return
	 */
	private BigDecimal convertUOMToMT(MProduct product, BigDecimal maxCapsUOM)
	{
		BigDecimal qtyMT = BigDecimal.ZERO ;
		BigDecimal weight = product.getWeight();
		if (null == weight || weight.compareTo(BigDecimal.ZERO) <= 0)
		{
			throw new AdempiereUserError(
					"Please Devined Weight " + product.getValue() + product.getName());
		}
		qtyMT = maxCapsUOM.multiply(weight);
		qtyMT = qtyMT.multiply(new BigDecimal(0.001));
		return qtyMT;
	}
	
	/**
	 * 
	 * @param output
	 * @return
	 */
	protected boolean isMultiOutput(I_UNS_Resource_InOut output)
	{
		return output.getOutputType().equals(MUNSResourceInOut.OUTPUTTYPE_Multi);
	}
	
	public String doConvert(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		m_Output = GridTabWrapper.create(mTab, I_UNS_Resource_InOut.class);
		String retValue = null;
		//if (!isMultiOutput(m_Output))
		//{
			doIndividualUpdate(ctx, WindowNo, mTab, mField, value, oldValue);
		/*}
		else 
		{
			retValue = updateAllMultiOutput(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		*/
		return retValue;
	}
	
	
	protected String updateAllMultiOutput(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		String retValue = "";
		boolean isPrimary = mTab.getValueAsBoolean(MUNSResourceInOut.COLUMNNAME_IsPrimary);

		if (!isPrimary) 
		{
			doIndividualUpdate(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else
		{
			if(m_Output.getMaxCaps().compareTo(BigDecimal.ZERO) == 0
					&& m_Output.getMaxCapsMT().compareTo(BigDecimal.ZERO) == 0)
				return retValue;
			
			doIndividualUpdate(ctx, WindowNo, mTab, mField, value, oldValue);

			m_Output = GridTabWrapper.create(mTab, I_UNS_Resource_InOut.class);
			
			MUNSResource rsc = new MUNSResource (ctx, m_Output.getUNS_Resource_ID(), null);
			
			MUNSResourceInOut[] outputLines = rsc.getOutputLines();
			MProductBOM[] boms = MProductBOM.getBOMLines((MProduct) m_Output.getM_Product());
			BigDecimal bomQty = BigDecimal.ZERO;
			for (MProductBOM bom : boms)
			{
				//I_M_PartType partType = bom.getM_ProductBOM().getM_PartType();
				I_M_PartType partType = bom.getM_PartType();
				
				if (null != partType && partType.getName().equals("Main Part"))
				{
					bomQty = bom.getBOMQty();
				}
			}
			BigDecimal realInputMax = m_Output.getMaxCaps().multiply(bomQty);
			BigDecimal realInputMaxMT = 
					m_Output.getMaxCapsMT().multiply(bomQty).multiply(m_Output.getM_Product().getWeight());
			BigDecimal realInputOptimum = m_Output.getOptimumCaps().multiply(bomQty);
			BigDecimal realInputOptimumMT = 
					m_Output.getOptimumCapsMT().multiply(bomQty).multiply(m_Output.getM_Product().getWeight());
			BigDecimal realActualInput = m_Output.getActualMaxCaps().multiply(bomQty);
			BigDecimal realActualInputMT = 
					m_Output.getActualMaxCapsMT().multiply(bomQty).multiply(m_Output.getM_Product().getWeight());
			
			for (MUNSResourceInOut output : outputLines)
			{
				if (output.getM_Product_ID() == m_Output.getM_Product_ID())
					continue;
				
				boms = MProductBOM.getBOMLines((MProduct) output.getM_Product());
				bomQty = BigDecimal.ZERO;
				for (MProductBOM bom : boms)
				{
					//I_M_PartType partType = bom.getM_ProductBOM().getM_PartType();
					I_M_PartType partType = bom.getM_PartType();
					
					if (null != partType && partType.getName().equals("Main Part"))
					{
						bomQty = bom.getBOMQty();
					}
				}
				output.setMaxCaps(realInputMax.divide(bomQty,4, RoundingMode.HALF_UP));
				output.setMaxCapsMT(realInputMaxMT.divide(bomQty,4, RoundingMode.HALF_UP)
						.multiply(output.getM_Product().getWeight()));
				output.setOptimumCaps(realInputOptimum.divide(bomQty,4, RoundingMode.HALF_UP));
				output.setOptimumCapsMT(realInputOptimumMT.divide(bomQty,4, RoundingMode.HALF_UP)
						.multiply(output.getM_Product().getWeight()));
				output.setActualMaxCaps(realActualInput.divide(bomQty,4, RoundingMode.HALF_UP));
				output.setActualMaxCapsMT(realActualInputMT.divide(bomQty)
						.multiply(output.getM_Product().getWeight()));
				output.save();
			}
			
		}
		return "";
	}
	
	private String doIndividualUpdate(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		String retValue = "";
		if (mField.getColumnName().equals(X_UNS_Resource_InOut.COLUMNNAME_MaxCaps))
		{
			retValue = maxCapsUOM(ctx, WindowNo, mField, mTab, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Resource_InOut.COLUMNNAME_MaxCapsMT))
		{
			retValue = maxCapsMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Resource_InOut.COLUMNNAME_OptimumCaps))
		{
			retValue = optimumMaxCapsUOM(ctx, WindowNo, mField, mTab, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Resource_InOut.COLUMNNAME_OptimumCapsMT))
		{
			retValue = optimumMaxCapsMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Resource_InOut.COLUMNNAME_OutputType))
		{
			retValue = changeOutputType(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		return retValue;
	}
	
	/**
	 * If the output type change to be 'Optional' or 'OPT' then make sure the isPrimary flag set to false.
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String changeOutputType(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		
		if (X_UNS_Resource_InOut.OUTPUTTYPE_Multi.equals(value)
				|| X_UNS_Resource_InOut.OUTPUTTYPE_MultiOptional.equals(value))
			return null;
		
		I_UNS_Resource_InOut output = GridTabWrapper.create(mTab, I_UNS_Resource_InOut.class);
		output.setIsPrimary(false);
		
		return null;
	}
	
}
