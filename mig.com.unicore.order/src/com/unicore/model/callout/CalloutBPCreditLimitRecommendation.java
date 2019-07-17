/**
 * 
 */
package com.unicore.model.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.model.I_UNS_BPLimitRecommendation;
import com.unicore.model.MUNSBPLimitRecommendation;

/**
 * @author root
 *
 */
public class CalloutBPCreditLimitRecommendation implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutBPCreditLimitRecommendation() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		if(mField.getColumnName().equals(MUNSBPLimitRecommendation.COLUMNNAME_C_BPartner_ID))
			ActionBPartner(ctx, WindowNo, mTab, mField, value, oldValue);
		
		return null;
	}
	
	private void ActionBPartner(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		I_UNS_BPLimitRecommendation model = GridTabWrapper.create(mTab, I_UNS_BPLimitRecommendation.class);
		if(null == value)
		{
			model.setCurrentLimit(Env.ZERO);
			return;
		}
		
		String sql = "SELECT SO_CreditLimit FROM C_BPartner WHERE C_BPartner_ID = ?";
		BigDecimal credit = DB.getSQLValueBD(null, sql, value);
		model.setCurrentLimit(credit);
	}

}
