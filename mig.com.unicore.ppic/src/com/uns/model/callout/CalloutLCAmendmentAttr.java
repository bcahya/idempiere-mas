/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.uns.model.I_UNS_LC_AmdAttributes;
import com.uns.model.I_UNS_LC_AttributeConf;
import com.uns.model.MUNSLCAmdAttributes;
import com.uns.model.MUNSLCAttributes;
import com.uns.model.X_UNS_LC_AttributeConf;

/**
 * @author menjangan
 *
 */
public class CalloutLCAmendmentAttr implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutLCAmendmentAttr() {
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
		if(mField.getColumnName().equals(MUNSLCAmdAttributes.COLUMNNAME_UNS_LC_Attributes_ID))
			retValue = lcAttr(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSLCAmdAttributes.COLUMNNAME_UNS_LC_AttributeConf_ID))
			retValue = AttrConf(ctx, WindowNo, mTab, mField, value, oldValue);
		return retValue;
	}
	
	public String lcAttr(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
			return null;
		Integer lcAttr_ID = (Integer)value;
		MUNSLCAttributes lcAttribute = new MUNSLCAttributes(ctx, lcAttr_ID, null);
		I_UNS_LC_AttributeConf attrConfig = lcAttribute.getUNS_LC_AttributeConf();
		I_UNS_LC_AmdAttributes lcAmendmentAttr = GridTabWrapper.create(mTab, I_UNS_LC_AmdAttributes.class);
		lcAmendmentAttr.setDataType(attrConfig.getDataType());
		lcAmendmentAttr.setName(attrConfig.getName());
		lcAmendmentAttr.setAttributeNumber(attrConfig.getValue());
		lcAmendmentAttr.setHasItems(attrConfig.isHasItems());
		lcAmendmentAttr.setUNS_LC_AttributeConf_ID(attrConfig.getUNS_LC_AttributeConf_ID());
		lcAmendmentAttr.setDescription(attrConfig.getDescription());
		lcAmendmentAttr.setPrevAttributeValueBD(lcAttribute.getAttributeValueBigDecimal());
		lcAmendmentAttr.setPrevAttributeValueDate(lcAttribute.getAttributeValueDate());
		lcAmendmentAttr.setPrevAttributeValueInt(lcAttribute.getAttributeValueInteger());
		lcAmendmentAttr.setPrevAttributeValueMemo(lcAttribute.getAttributeValueMemo());
		lcAmendmentAttr.setPrevAttributeValueString(lcAttribute.getAttributeValueString());
		lcAmendmentAttr.setPrevAttributeValueText(lcAttribute.getAttributeValueText());
		lcAmendmentAttr.setPrevCurrency_ID(lcAttribute.getC_Currency_ID());
		return null;
	}
	
	public String AttrConf(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
			return null;
		Integer attrConf_ID = (Integer)value;
		X_UNS_LC_AttributeConf attrConfig = new X_UNS_LC_AttributeConf(ctx, attrConf_ID, null);
		I_UNS_LC_AmdAttributes lcAmendmentAttr = GridTabWrapper.create(mTab, I_UNS_LC_AmdAttributes.class);
		lcAmendmentAttr.setName(attrConfig.getName());
		lcAmendmentAttr.setAttributeNumber(attrConfig.getValue());
		lcAmendmentAttr.setHasItems(attrConfig.isHasItems());
		lcAmendmentAttr.setDataType(attrConfig.getDataType());
		lcAmendmentAttr.setDescription(attrConfig.getDescription());
		return null;
	}

}
