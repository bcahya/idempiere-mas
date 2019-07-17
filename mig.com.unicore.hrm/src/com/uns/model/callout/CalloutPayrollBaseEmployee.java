/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.uns.model.MUNSContractRecommendation;
import com.uns.model.MUNSPayrollBaseEmployee;

/**
 * @author menjangan
 *
 */
public class CalloutPayrollBaseEmployee implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutPayrollBaseEmployee() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(
	 * java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField
	 * , java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue = "";
		if (value == null)
			return retValue;
		
		if (mField.getColumnName().equals( MUNSPayrollBaseEmployee.COLUMNNAME_UNS_Contract_Recommendation_ID))
			retValue = UNS_Contract_Recommendation_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		// TODO Auto-generated method stub
		return null;
	}

	
	protected String UNS_Contract_Recommendation_ID (
			Properties ctx, int WindowNon, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		Integer UNS_Contract_Recommendation_ID = (Integer)value;
		if (UNS_Contract_Recommendation_ID <= 0)
			return "";
		
		MUNSContractRecommendation contractRecommendation = 
				new MUNSContractRecommendation(ctx, UNS_Contract_Recommendation_ID, null);
		mTab.setValue(MUNSPayrollBaseEmployee.COLUMNNAME_AD_Org_ID, contractRecommendation.getAD_Org_ID());
		
		return "";
	}
}
