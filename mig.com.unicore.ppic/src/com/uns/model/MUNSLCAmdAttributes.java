/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSLCAmdAttributes extends X_UNS_LC_AmdAttributes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3808194132908277923L;
	private MUNSLCAmendmentAttrItems[] m_attrItems = null;

	/**
	 * @param ctx
	 * @param UNS_LC_AmdAttributes_ID
	 * @param trxName
	 */
	public MUNSLCAmdAttributes(Properties ctx, int UNS_LC_AmdAttributes_ID,
			String trxName) {
		super(ctx, UNS_LC_AmdAttributes_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLCAmdAttributes(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSLCAmendment getParent()
	{
		return (MUNSLCAmendment)getUNS_LC_Amendment();
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSLCAmendmentAttrItems[] getAttrItems(boolean requery)
	{
		if(null != m_attrItems && !requery)
		{
			set_TrxName(m_attrItems, get_TrxName());
			return m_attrItems;
		}
		
		String whereClause = MUNSLCAmendmentAttrItems.COLUMNNAME_UNS_LC_AmdAttributes_ID + " =?";
		List<MUNSLCAmendmentAttrItems> list = Query.get(
				getCtx()
				, UNSPPICModelFactory.getExtensionID()
				, MUNSLCAmendmentAttrItems.Table_Name
				, whereClause, get_TrxName())
				.setParameters(getUNS_LC_AmdAttributes_ID())
				.list();
		m_attrItems = new MUNSLCAmendmentAttrItems[list.size()];
		return list.toArray(m_attrItems);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getUNS_LC_Attributes_ID() <= 0)
		{
			MUNSLCBalance lcBalance = (MUNSLCBalance) getParent().getUNS_LC_Balance();
			MUNSLCAttributes lcAttr = lcBalance.getLCAttribute(getAttributeNumber());
			if(null != lcAttr)
			{
				setUNS_LC_Attributes_ID(lcAttr.get_ID());
				setPrevAttributeValueBD(lcAttr.getAttributeValueBigDecimal());
				setPrevAttributeValueDate(lcAttr.getAttributeValueDate());
				setPrevAttributeValueInt(lcAttr.getAttributeValueInteger());
				setPrevAttributeValueMemo(lcAttr.getAttributeValueMemo());
				setPrevAttributeValueString(lcAttr.getAttributeValueString());
				setPrevAttributeValueText(lcAttr.getAttributeValueText());
				setPrevCurrency_ID(lcAttr.getC_Currency_ID());
			}
		}
		return super.beforeSave(newRecord);
	}

	/**
	 * 
	 * @return
	 */
	public BigDecimal getNewAttributeValueAsBigDecimal()
	{
		BigDecimal bdValue = getNewAttributeValueBD();
		
		if (bdValue == null || bdValue.signum() == 0)
			return bdValue = new BigDecimal(getNewAttributeValueInt());
		
		return bdValue;
	}

}
