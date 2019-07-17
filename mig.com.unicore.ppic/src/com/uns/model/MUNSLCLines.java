/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_M_Product;

/**
 * @author menjangan
 *
 */
public class MUNSLCLines extends X_UNS_LC_Lines {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String m_AttrProduct = "45A";

	/**
	 * @param ctx
	 * @param UNS_LC_Lines_ID
	 * @param trxName
	 */
	public MUNSLCLines(Properties ctx, int UNS_LC_Lines_ID, String trxName) {
		super(ctx, UNS_LC_Lines_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLCLines(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean beforeSave(boolean newRecord)
	{
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean succes)
	{
		MUNSLCBalance lcBalance = getParent();
		MUNSLCAttributes lcAttribute = lcBalance.getLCAttributeHasItems(m_AttrProduct);
		if(null == lcAttribute)
		{
			MUNSLCAttributeConf attrConf = MUNSLCAttributeConf.get(getCtx(), m_AttrProduct, get_TrxName());
			if(null == attrConf)
			{
				attrConf = new MUNSLCAttributeConf(getCtx(), 0, get_TrxName());
				attrConf.setAD_Org_ID(getAD_Org_ID());
				attrConf.setDataType(MUNSLCAttributeConf.DATATYPE_Product);
				attrConf.setDescription("Definision of Goods / Service (Product)");
				attrConf.setHasItems(true);
				attrConf.setValue(m_AttrProduct);
				attrConf.setName("Definition of Goods / Service 45A");
				attrConf.save();
			}
			lcAttribute = new MUNSLCAttributes(getCtx(), 0, get_TrxName());
			lcAttribute.setAD_Org_ID(getAD_Org_ID());
			lcAttribute.setAttributeNumber(m_AttrProduct);
			lcAttribute.setDataType(attrConf.getDataType());
			lcAttribute.setUNS_LC_Balance_ID(getUNS_LC_Balance_ID());
			lcAttribute.setUNS_LC_AttributeConf_ID(attrConf.get_ID());
			lcAttribute.setName(attrConf.getName());
			lcAttribute.setHasItems(attrConf.isHasItems());
			lcAttribute.save();
		}
		return super.afterSave(newRecord, succes);
	}
	
	public BigDecimal getSOQtyLeft()
	{
		return getLCQuantity().subtract(getSOQuantity());
	}
	
	public BigDecimal getShipmentQtyLeft()
	{
		return getLCQuantity().subtract(getShipmentQuantity());
	}	
	
	public boolean isAllowedQtySO(BigDecimal qty)
	{
		if (qty.compareTo(getSOQtyLeft()) > 0)
			return false;
		return true;
	}
	
	public boolean isAllowedQtyShipment(BigDecimal qty)
	{
		if (qty.compareTo(getShipmentQtyLeft()) > 0)
			return false;		
		return true;
	}
	
	public MUNSLCBalance getParent()
	{
		return (MUNSLCBalance)getUNS_LC_Balance();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUniqueLine()
	{
		return getM_Product_Category_ID() + "-" + getC_UOM_ID();
	}
	
	
	public static String getUniqueLine(I_M_Product product)
	{
		return product.getM_Product_Category_ID() + "-" + product.getC_UOM_ID();
	}

}
