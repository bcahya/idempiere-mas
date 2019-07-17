/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;

import com.uns.model.X_UNS_Forecast_Input;

/**
 * @author menjangan
 *
 */
public class CalloutForecastOutput extends CalloutEngine
		implements IColumnCallout {
	
	private BigDecimal m_Weight = BigDecimal.ZERO;

	/**
	 * 
	 */
	public CalloutForecastOutput() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		String retValue = null;
		
		if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJan))
		{
			retValue = forecastMarketingJan(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingFeb))
		{
			retValue = forecastMarketingFeb(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingMar))
		{
			retValue = forecastMarketingMar(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingApr))
		{
			retValue = forecastMarketingApr(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingMay))
		{
			retValue = forecastMarketingMay(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJun))
		{
			retValue = forecastMarketingJun(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJul))
		{
			retValue = forecastMarketingJul(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingAgt))
		{
			retValue = forecastMarketingAgt(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingSep))
		{
			retValue = forecastMarketingSep(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingOct))
		{
			retValue = forecastMarketingOct(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingNov))
		{
			retValue = forecastMarketingNov(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingDec))
		{
			retValue = forecastMarketingDec(ctx, WindowNo, mTab, mField, value, oldValue);
		}		
		
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJanMT))
		{
			retValue = forecastMarketingJanMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingFebMT))
		{
			retValue = forecastMarketingFebMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingMarMT))
		{
			retValue = forecastMarketingMarMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingAprMT))
		{
			retValue = forecastMarketingAprMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingMayMT))
		{
			retValue = forecastMarketingMayMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJunMT))
		{
			retValue = forecastMarketingJunMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJulMT))
		{
			retValue = forecastMarketingJulMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingAgtMT))
		{
			retValue = forecastMarketingAgtMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingSepMT))
		{
			retValue = forecastMarketingSepMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingOctMT))
		{
			retValue = forecastMarketingOctMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingNovMT))
		{
			retValue = forecastMarketingNovMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingDecMT))
		{
			retValue = forecastMarketingDecMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJan))
		{
			retValue = forecastTargetedJan(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedFeb))
		{
			retValue = forecastTargetedFeb(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedMar))
		{
			retValue = forecastTargetedMar(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedApr))
		{
			retValue = forecastTargetedApr(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedMay))
		{
			retValue = forecastTargetedMay(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJun))
		{
			retValue = forecastTargetedJun(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJul))
		{
			retValue = forecastTargetedJul(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedAgt))
		{
			retValue = forecastTargetedAgt(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedSep))
		{
			retValue = forecastTargetedSep(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedOct))
		{
			retValue = forecastTargetedOct(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedNov))
		{
			retValue = forecastTargetedNov(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedDec))
		{
			retValue = forecastTargetedDec(ctx, WindowNo, mTab, mField, value, oldValue);
		}		
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJanMT))
		{
			retValue = forecastTargetedJanMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedFebMT))
		{
			retValue = forecastTargetedFebMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedMarMT))
		{
			retValue = forecastTargetedMarMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedAprMT))
		{
			retValue = forecastTargetedAprMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedMayMT))
		{
			retValue = forecastTargetedMayMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJunMT))
		{
			retValue = forecastTargetedJunMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJulMT))
		{
			retValue = forecastTargetedJulMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedAgtMT))
		{
			retValue = forecastTargetedAgtMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedSepMT))
		{
			retValue = forecastTargetedSepMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedOctMT))
		{
			retValue = forecastTargetedOctMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedNovMT))
		{
			retValue = forecastTargetedNovMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedDecMT))
		{
			retValue = forecastTargetedDecMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}		
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_M_Product_ID))
		{
			retValue = product(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		
		return retValue;
	}
	
	private void initProduct(int M_Product_ID)
	{
		MProduct product = MProduct.get(Env.getCtx(), M_Product_ID);
		if (null == product)
			throw new AdempiereUserError("Please chose product before inserting quantity");
		
		m_Weight = product.getWeight();
		
		if (null == m_Weight || m_Weight.compareTo(BigDecimal.ZERO) <= 0)
			throw new AdempiereUserError("Please define weight of product " + product.getName());
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
	private String product(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		Integer M_Product_ID = (Integer) mTab.getValue(X_UNS_Forecast_Input.COLUMNNAME_M_Product_ID);
		
		if (M_Product_ID <= 0 )
			return "";
		
		if (isCalloutActive())
			return "";
		
		MProduct product = MProduct.get(ctx, M_Product_ID);
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_C_UOM_ID, product.getC_UOM_ID());
		
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
	protected String forecastMarketingJanMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJan, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";
		
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJan, convertMTToUOM(qtyMT));
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
	protected String forecastMarketingFebMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingFeb, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingFeb, convertMTToUOM(qtyMT));
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
	protected String forecastMarketingMarMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingMar, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingMar, convertMTToUOM(qtyMT));
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
	protected String forecastMarketingAprMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingApr, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingApr, convertMTToUOM(qtyMT));
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
	protected String forecastMarketingMayMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingMay, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingMay, convertMTToUOM(qtyMT));
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
	protected String forecastMarketingJunMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJun, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJun, convertMTToUOM(qtyMT));
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
	protected String forecastMarketingJulMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJul, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJul, convertMTToUOM(qtyMT));
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
	protected String forecastMarketingAgtMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingAgt, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingAgt, convertMTToUOM(qtyMT));
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
	protected String forecastMarketingSepMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingSep, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingSep, convertMTToUOM(qtyMT));
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
	protected String forecastMarketingOctMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingOct, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingOct, convertMTToUOM(qtyMT));
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
	protected String forecastMarketingNovMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingNov, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingNov, convertMTToUOM(qtyMT));
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
	protected String forecastMarketingDecMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingDec, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingDec, convertMTToUOM(qtyMT));
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
	protected String forecastMarketingJan(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJanMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJanMT, convertUOMToMT(qtyUOM));
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
	protected String forecastMarketingFeb(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingFebMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingFebMT, convertUOMToMT(qtyUOM));
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
	protected String forecastMarketingMar(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingMarMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingMarMT, convertUOMToMT(qtyUOM));
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
	protected String forecastMarketingApr(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingAprMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingAprMT, convertUOMToMT(qtyUOM));
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
	protected String forecastMarketingMay(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingMayMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingMayMT, convertUOMToMT(qtyUOM));
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
	protected String forecastMarketingJun(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{	
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJunMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJunMT, convertUOMToMT(qtyUOM));
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
	protected String forecastMarketingJul(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJulMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingJulMT, convertUOMToMT(qtyUOM));
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
	protected String forecastMarketingAgt(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingAgtMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingAgtMT, convertUOMToMT(qtyUOM));
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
	protected String forecastMarketingSep(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingSepMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingSepMT, convertUOMToMT(qtyUOM));
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
	protected String forecastMarketingOct(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingOctMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingOctMT, convertUOMToMT(qtyUOM));
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
	protected String forecastMarketingNov(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingNovMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingNovMT, convertUOMToMT(qtyUOM));
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
	protected String forecastMarketingDec(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingDecMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastMarketingDecMT, convertUOMToMT(qtyUOM));
		return "";
	}

	
	/********************************************************************************************/
	
	
	protected String forecastTargetedJanMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJan, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";
		
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJan, convertMTToUOM(qtyMT));
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
	protected String forecastTargetedFebMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedFeb, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedFeb, convertMTToUOM(qtyMT));
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
	protected String forecastTargetedMarMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedMar, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedMar, convertMTToUOM(qtyMT));
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
	protected String forecastTargetedAprMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedApr, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedApr, convertMTToUOM(qtyMT));
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
	protected String forecastTargetedMayMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedMay, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedMay, convertMTToUOM(qtyMT));
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
	protected String forecastTargetedJunMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJun, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJun, convertMTToUOM(qtyMT));
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
	protected String forecastTargetedJulMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJul, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJul, convertMTToUOM(qtyMT));
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
	protected String forecastTargetedAgtMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedAgt, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedAgt, convertMTToUOM(qtyMT));
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
	protected String forecastTargetedSepMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedSep, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedSep, convertMTToUOM(qtyMT));
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
	protected String forecastTargetedOctMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedOct, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedOct, convertMTToUOM(qtyMT));
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
	protected String forecastTargetedNovMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedNov, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedNov, convertMTToUOM(qtyMT));
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
	protected String forecastTargetedDecMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedDec, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedDec, convertMTToUOM(qtyMT));
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
	protected String forecastTargetedJan(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJanMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJanMT, convertUOMToMT(qtyUOM));
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
	protected String forecastTargetedFeb(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedFebMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedFebMT, convertUOMToMT(qtyUOM));
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
	protected String forecastTargetedMar(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{	
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedMarMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedMarMT, convertUOMToMT(qtyUOM));
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
	protected String forecastTargetedApr(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedAprMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedAprMT, convertUOMToMT(qtyUOM));
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
	protected String forecastTargetedMay(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedMayMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedMayMT, convertUOMToMT(qtyUOM));
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
	protected String forecastTargetedJun(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJunMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJunMT, convertUOMToMT(qtyUOM));
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
	protected String forecastTargetedJul(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJulMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedJulMT, convertUOMToMT(qtyUOM));
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
	protected String forecastTargetedAgt(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedAgtMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedAgtMT, convertUOMToMT(qtyUOM));
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
	protected String forecastTargetedSep(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedSepMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedSepMT, convertUOMToMT(qtyUOM));
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
	protected String forecastTargetedOct(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedOctMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedOctMT, convertUOMToMT(qtyUOM));
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
	protected String forecastTargetedNov(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedNovMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedNovMT, convertUOMToMT(qtyUOM));
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
	protected String forecastTargetedDec(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedDecMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastTargetedDecMT, convertUOMToMT(qtyUOM));
		return "";
	}

	
	
	/**
	 * 
	 * @param qtyMT
	 * @return
	 */
	private BigDecimal convertMTToUOM(BigDecimal qtyMT)
	{
		double qtyUOM = qtyMT.doubleValue() * 1000 / m_Weight.doubleValue();
		return new BigDecimal(qtyUOM);
	}
	
	/**
	 * 
	 * @param qtyUOM
	 * @return
	 */
	private BigDecimal convertUOMToMT(BigDecimal qtyUOM)
	{
		double qtyMT = qtyUOM.doubleValue() / 1000 * m_Weight.doubleValue();
		return new BigDecimal(qtyMT);
	}

}
