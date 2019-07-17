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
public class CalloutForecastInput extends CalloutEngine implements
		IColumnCallout {

	BigDecimal m_Weight = BigDecimal.ZERO;
	/**
	 * 
	 */
	public CalloutForecastInput() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		String retValue = null;
		
		if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJan))
		{
			retValue = forecastRMPJan(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPFeb))
		{
			retValue = forecastRMPFeb(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPMar))
		{
			retValue = forecastRMPMar(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPApr))
		{
			retValue = forecastRMPApr(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPMay))
		{
			retValue = forecastRMPMay(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJun))
		{
			retValue = forecastRMPJun(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJul))
		{
			retValue = forecastRMPJul(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPAgt))
		{
			retValue = forecastRMPAgt(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPSep))
		{
			retValue = forecastRMPSep(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPOct))
		{
			retValue = forecastRMPOct(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPNov))
		{
			retValue = forecastRMPNov(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPDec))
		{
			retValue = forecastRMPDec(ctx, WindowNo, mTab, mField, value, oldValue);
		}		
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJanMT))
		{
			retValue = forecastRMPJanMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPFebMT))
		{
			retValue = forecastRMPFebMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPMarMT))
		{
			retValue = forecastRMPMarMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPAprMT))
		{
			retValue = forecastRMPAprMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPMayMT))
		{
			retValue = forecastRMPMayMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJunMT))
		{
			retValue = forecastRMPJunMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJulMT))
		{
			retValue = forecastRMPJulMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPAgtMT))
		{
			retValue = forecastRMPAgtMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPSepMT))
		{
			retValue = forecastRMPSepMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPOctMT))
		{
			retValue = forecastRMPOctMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPNovMT))
		{
			retValue = forecastRMPNovMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPDecMT))
		{
			retValue = forecastRMPDecMT(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if (mField.getColumnName().equals(X_UNS_Forecast_Input.COLUMNNAME_M_Product_ID))
		{
			retValue = product(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		
		
		// TODO Auto-generated method stub
		return retValue;
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 */
	private void initProduct(int M_Product_ID)
	{
		MProduct product = MProduct.get(Env.getCtx(), M_Product_ID);
		if (null == product)
			throw new AdempiereUserError("Please chose product before inserting quantity");
		
		m_Weight = product.getWeight();
		
		if (null == m_Weight || m_Weight.compareTo(BigDecimal.ZERO) <= 0)
			throw new AdempiereUserError("Please define weight of product " + product.getName());
	}
	
	private String product(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (value == null)
			return "";
		
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
	protected String forecastRMPJanMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJan, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";
		
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJan, convertMTToUOM(qtyMT));
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
	protected String forecastRMPFebMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPFeb, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPFeb, convertMTToUOM(qtyMT));
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
	protected String forecastRMPMarMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPMar, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPMar, convertMTToUOM(qtyMT));
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
	protected String forecastRMPAprMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPApr, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPApr, convertMTToUOM(qtyMT));
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
	protected String forecastRMPMayMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPMay, BigDecimal.ZERO);
			return "";
		}
			
		
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPMay, convertMTToUOM(qtyMT));
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
	protected String forecastRMPJunMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJun, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJun, convertMTToUOM(qtyMT));
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
	protected String forecastRMPJulMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJul, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";
		
		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJul, convertMTToUOM(qtyMT));
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
	protected String forecastRMPAgtMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPAgt, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPAgt, convertMTToUOM(qtyMT));
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
	protected String forecastRMPSepMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPSep, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPSep, convertMTToUOM(qtyMT));
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
	protected String forecastRMPOctMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPOct, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPOct, convertMTToUOM(qtyMT));
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
	protected String forecastRMPNovMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPNov, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPNov, convertMTToUOM(qtyMT));
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
	protected String forecastRMPDecMT(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyMT = (BigDecimal) value;
		
		if (null == qtyMT || qtyMT.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPDec, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPDec, convertMTToUOM(qtyMT));
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
	protected String forecastRMPJan(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJanMT, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJanMT, convertUOMToMT(qtyUOM));
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
	protected String forecastRMPFeb(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPFebMT, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPFebMT, convertUOMToMT(qtyUOM));
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
	protected String forecastRMPMar(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPMarMT, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPMarMT, convertUOMToMT(qtyUOM));
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
	protected String forecastRMPApr(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPAprMT, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPAprMT, convertUOMToMT(qtyUOM));
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
	protected String forecastRMPMay(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPMayMT, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPMayMT, convertUOMToMT(qtyUOM));
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
	protected String forecastRMPJun(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJunMT, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJunMT, convertUOMToMT(qtyUOM));
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
	protected String forecastRMPJul(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJulMT, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPJulMT, convertUOMToMT(qtyUOM));
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
	protected String forecastRMPAgt(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPAgtMT, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPAgtMT, convertUOMToMT(qtyUOM));
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
	protected String forecastRMPSep(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPSepMT, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPSepMT, convertUOMToMT(qtyUOM));
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
	protected String forecastRMPOct(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPOctMT, BigDecimal.ZERO);
			return "";
		}
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPOctMT, convertUOMToMT(qtyUOM));
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
	protected String forecastRMPNov(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPNovMT, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPNovMT, convertUOMToMT(qtyUOM));
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
	protected String forecastRMPDec(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal qtyUOM = (BigDecimal) value;
		
		if (null == qtyUOM || qtyUOM.compareTo(BigDecimal.ZERO) <= 0)
		{
			mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPDecMT, BigDecimal.ZERO);
			return "";
		}
		
		if (isCalloutActive())
			return "";

		initProduct(((Integer) mTab.getValue("M_Product_ID")).intValue());
		mTab.setValue(X_UNS_Forecast_Input.COLUMNNAME_ForecastRMPDecMT, convertUOMToMT(qtyUOM));
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
