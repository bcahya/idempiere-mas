/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;
import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.uns.model.I_UNS_LC_Attributes;
import com.uns.model.X_UNS_LC_AttributeConf;
import com.uns.model.X_UNS_LC_Attributes;

/**
 * @author menjangan
 *
 */
public class CalloutLCAttribute implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutLCAttribute() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		// TODO Auto-generated method stub
		if(null == value)
			return null;
		if(mField.getColumnName().equals(X_UNS_LC_Attributes.COLUMNNAME_UNS_LC_AttributeConf_ID))
		{
			X_UNS_LC_AttributeConf lcAttributeConf = 
					new X_UNS_LC_AttributeConf(ctx, new Integer(value.toString()), null);
			I_UNS_LC_Attributes lcAttribute = GridTabWrapper.create(mTab, I_UNS_LC_Attributes.class);
			lcAttribute.setName(lcAttributeConf.getName());
			lcAttribute.setAttributeNumber(lcAttributeConf.getValue());
			lcAttribute.setHasItems(lcAttributeConf.isHasItems());
			lcAttribute.setDescription("Atribute for " + lcAttribute.getName() 
					+ " with attribute number " + lcAttributeConf.getValue());
			lcAttribute.setDataType(lcAttributeConf.getDataType());
		}
		return null;
	}

}
