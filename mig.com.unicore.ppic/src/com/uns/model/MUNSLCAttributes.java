/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 * @Untasoft
 */
public class MUNSLCAttributes extends X_UNS_LC_Attributes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8003452980651005140L;
	private MUNSLCAttrItems[] m_AttributeItems = null;
	public static String PercentageCreditAmountTolerance_39A = "39A";
	public static String CurrencyAmount_32B = "32B";
//	private MUNSLCLines[] m_ProductAttrItems = null;

	/**
	 * @param ctx
	 * @param UNS_LC_Attributes_ID
	 * @param trxName
	 */
	public MUNSLCAttributes(Properties ctx, int UNS_LC_Attributes_ID,
			String trxName) {
		super(ctx, UNS_LC_Attributes_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLCAttributes(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/*
	public static MUNSLCAttributes get(Properties ctx, String lcNo, String trxName) 
	{
		MUNSLCAttributes lcAttr = Query
				.get(ctx, UNSPPICModelFactory.getExtensionID(), 
					Table_Name, "Value=", trxName)
				.setParameters(lcNo)
				.firstOnly();
		return lcAttr;
	}
	*/
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		/*
		if(getDataType().equals(DATATYPE_Product))
			MessageBox.showMsg(
					getCtx(), 
					"You have just create LC Attribute for Product, " +
					"after create this you must define product in tab LC product(45A)"
							, "Attention"
							, MessageBox.OK
							, MessageBox.ICONINFORMATION);
		*/
		//I_UNS_LC_AttributeConf lcAttributeConf = getUNS_LC_AttributeConf();
		MUNSLCBalance lcBalance = (MUNSLCBalance)getUNS_LC_Balance();
		
		if(getAttributeNumber().equalsIgnoreCase(CurrencyAmount_32B))
		{
			lcBalance.setGrandTotal(getAttributeValueBigDecimal());
			lcBalance.setC_Currency_ID(getC_Currency_ID());
			
			/*
			MUNSLCAttributes lcTolerance = get(getCtx(), PercentageCreditAmountTolerance_39A, get_TrxName());
			if (lcTolerance != null)
			{
				lcBalance.setTotalAmount(getAttributeValueBigDecimal(), lcTolerance.getAttributeValueAsBigDecimal());
			}
			*/
			lcBalance.saveEx();
		}
		else if (getAttributeNumber().equalsIgnoreCase(PercentageCreditAmountTolerance_39A))
		{
			/*
			MUNSLCAttributes lcAmtAttr = get(getCtx(), CurrencyAmount_32B, get_TrxName());
			if (lcAmtAttr == null)
				return true;
			
			lcBalance.setTotalAmount(lcAmtAttr.getAttributeValueBigDecimal(), getAttributeValueAsBigDecimal());
			*/
			lcBalance.setToleranceAmt(getAttributeValueAsBigDecimal());
			lcBalance.saveEx();
		}
		return super.afterSave(newRecord, success);
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getAttributeValueAsBigDecimal()
	{
		BigDecimal bdValue = getAttributeValueBigDecimal();
		
		if (bdValue == null || bdValue.signum() == 0)
			return bdValue = new BigDecimal(getAttributeValueInteger());
		
		return bdValue;
	}
	
	/**
	 * 
	 * @param itemNumber
	 * @return
	 */
	public MUNSLCAttrItems getItem(int itemNumber)
	{
		for(MUNSLCAttrItems attrItem : getAttributeItems(false))
		{
			if(attrItem.getItemNo() != itemNumber)
				continue;
			return attrItem;
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Hashtable<Integer, MUNSLCAttrItems> getMapOfAttrItems()
	{
		Hashtable<Integer, MUNSLCAttrItems> mapOfAttrItems = new Hashtable<Integer, MUNSLCAttrItems>();
		for(MUNSLCAttrItems attrItem : getAttributeItems(false))
		{
			mapOfAttrItems.put(attrItem.getItemNo(), attrItem);
		}
		return mapOfAttrItems;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSLCAttrItems[] getAttributeItems(boolean requery)
	{
		if(null != m_AttributeItems && !requery)
		{
			set_TrxName(m_AttributeItems, get_TrxName());
			return m_AttributeItems;
		}
		
		String whereClause = MUNSLCAttrItems.COLUMNNAME_UNS_LC_Attributes_ID + " =?";
		List<MUNSLCAttrItems> list = Query.get(
				getCtx()
				, UNSPPICModelFactory.getExtensionID()
				, MUNSLCAttrItems.Table_Name
				, whereClause, get_TrxName())
				.setParameters(getUNS_LC_Attributes_ID())
				.list();
		
		m_AttributeItems = new MUNSLCAttrItems[list.size()];
		return list.toArray(m_AttributeItems);
	}
	
	/**
	 * 
	 * @param amendmentItems
	 * @return
	 */
	public boolean updateItems(MUNSLCAmendmentAttrItems[] amendmentItems)
	{
		Hashtable<Integer, MUNSLCAttrItems> mapOfAttrItems = getMapOfAttrItems();
		for(MUNSLCAmendmentAttrItems amdAttrItem : amendmentItems)
		{
			MUNSLCAttrItems attrItem = mapOfAttrItems.get(amdAttrItem.getItemNo());
			if( null == attrItem)
				attrItem = new MUNSLCAttrItems(getCtx(), 0, get_TrxName());
			
			attrItem.setAD_Org_ID(amdAttrItem.getAD_Org_ID());
			attrItem.setDescription(amdAttrItem.getDescription());
			attrItem.setItemNo(amdAttrItem.getItemNo());
			attrItem.setUNS_LC_Attributes_ID(get_ID());
			if(!attrItem.save())
				return false;
		}
		return true;
	}	
}
