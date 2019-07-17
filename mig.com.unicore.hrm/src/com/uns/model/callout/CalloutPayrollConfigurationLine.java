/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;

import com.uns.model.I_UNS_PayCriteria;
import com.uns.model.I_UNS_PayRollLine;
import com.uns.model.MUNSPayRollLine;
import com.uns.model.X_UNS_PayCriteria;

/**
 * @author menjangan
 *
 */
public class CalloutPayrollConfigurationLine implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutPayrollConfigurationLine() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals(MUNSPayRollLine.COLUMNNAME_M_Product_ID))
			MProduct(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSPayRollLine.COLUMNNAME_UNS_PayCriteria_ID))
			payCriteria(ctx, WindowNo, mTab, mField, value, oldValue);
		
		return null;
		
	}
	
	private String payCriteria(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if(null == value)
			return null;
		I_UNS_PayRollLine criteriaLine = GridTabWrapper.create(mTab, I_UNS_PayRollLine.class);
		I_UNS_PayCriteria payCriteria = criteriaLine.getUNS_PayCriteria();
		criteriaLine.setIsSwitchTodaily(payCriteria.isSwitchTodaily());
		criteriaLine.setIsTargetBased(payCriteria.isTargetBased());
		criteriaLine.setCriteriaType(payCriteria.getCriteriaType());
		criteriaLine.setIsGradualCalculation(payCriteria.isGradualCalculation());
		
		if (!payCriteria.getCriteriaType().equals(X_UNS_PayCriteria.CRITERIATYPE_Premi))
			criteriaLine.setPremiCalculateBased(null);
		else if (!payCriteria.getCriteriaType().equals(X_UNS_PayCriteria.CRITERIATYPE_Product))
			criteriaLine.setM_Product_ID(0);
		
		return null;
	}
	
	private String MProduct(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if (null == value)
		{
			mTab.setValue(MUNSPayRollLine.COLUMNNAME_C_UOM_ID, null);
			return null;
		}
		Integer M_Product_ID = (Integer) value;
		MProduct product = MProduct.get(ctx, M_Product_ID);
		mTab.setValue(MUNSPayRollLine.COLUMNNAME_C_UOM_ID, product.getC_UOM_ID());
		// TODO Auto-generated method stub
		return null;
	}
}
