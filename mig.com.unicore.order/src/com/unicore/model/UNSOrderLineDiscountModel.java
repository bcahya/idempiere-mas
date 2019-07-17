/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.model.PO;

import com.uns.model.MProduct;

import com.unicore.base.model.MOrderLine;

/**
 * @author root
 *
 */
public class UNSOrderLineDiscountModel implements I_DiscountModelLine {

	private MOrderLine m_model = null;
	private MProduct m_product = null;
	
	
	/**
	 * 
	 */
	public UNSOrderLineDiscountModel(PO po)
	{
		if(null == po)
			throw new AdempiereException("Null PO");
		
		setModel((MOrderLine) po); 
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setModel(org.compiere.model.PO)
	 */
	@Override
	public void setModel(PO po) 
	{
		if(null == po)
			throw new AdempiereException("Null PO");
		
		m_model = (MOrderLine) po;
		setProduct(new MProduct(getModel().getCtx(), getModel().getM_Product_ID(), getModel().get_TrxName()));
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getModel()
	 */
	@Override
	public MOrderLine getModel() 
	{
		return m_model;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getProduct()
	 */
	@Override
	public MProduct getProduct() 
	{
		return m_product;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setProduct(com.uns.model.MProduct)
	 */
	@Override
	public void setProduct(MProduct proruct) 
	{
		m_product = proruct;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setDiscountAmt(java.math.BigDecimal)
	 */
	@Override
	public void setDiscountAmt(BigDecimal discountAmt) 
	{
		getModel().setDiscountAmt(discountAmt);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getDiscountAmt()
	 */
	@Override
	public BigDecimal getDiscountAmt() 
	{
		return getModel().getDiscountAmt();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setDiscount(java.math.BigDecimal)
	 */
	@Override
	public void setDiscount(BigDecimal discount) 
	{
		getModel().setDiscount(discount);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getDiscount()
	 */
	@Override
	public BigDecimal getDiscount() 
	{
		return getModel().getDiscount();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setPrice(java.math.BigDecimal)
	 */
	@Override
	public void setPrice(BigDecimal price) 
	{
		getModel().setPriceActual(price);
		getModel().setPriceEntered(price);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setPrice()
	 */
	@Override
	public void setPrice() 
	{
		getModel().setPrice();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setHeaderInfo(org.compiere.model.PO)
	 */
	@Override
	public void setHeaderInfo(PO header) 
	{
		getModel().setHeaderInfo((MOrder) header);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setRefLine_ID(int)
	 */
	@Override
	public void setRefLine_ID(int ref_ID) 
	{
		getModel().setRef_OrderLine_ID(ref_ID);
		getModel().setLink_OrderLine_ID(ref_ID);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#isDiscountBonuse()
	 */
	@Override
	public boolean isProductBonuses() 
	{
		return getModel().isProductBonuses();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setIsDiscountBonus(boolean)
	 */
	@Override
	public void setIsProductBonus(boolean isDiscountBonus) 
	{
		getModel().setisProductBonuses(isProductBonuses());
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getPriceList()
	 */
	@Override
	public BigDecimal getPriceList() 
	{
		return getModel().getPriceList();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getPriceEntered()
	 */
	@Override
	public BigDecimal getPriceEntered() 
	{
		return getModel().getPriceEntered();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getPriceActual()
	 */
	@Override
	public BigDecimal getPriceActual() 
	{
		return getModel().getPriceActual();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setPriceEntered(java.math.BigDecimal)
	 */
	@Override
	public void setPriceEntered(BigDecimal priceEntered) 
	{
		getModel().setPriceEntered(priceEntered);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setPriceActual(java.math.BigDecimal)
	 */
	@Override
	public void setPriceActual(BigDecimal priceActual) 
	{
		getModel().setPriceActual(priceActual);
	}

	@Override
	public BigDecimal getQtyEntered() 
	{
		return getModel().getQtyEntered();
	}

	@Override
	public void setQtyEntered(BigDecimal qtyEntered) 
	{
		getModel().setQtyEntered(qtyEntered);
	}

	@Override
	public BigDecimal getQtyBonuses() 
	{
		return getModel().getQtyBonuses();
	}

	@Override
	public void setQtyBonuses(BigDecimal qtyBonuses) 
	{
		getModel().setQtyBonuses(qtyBonuses);
	}

	@Override
	public void setQtyMerge(BigDecimal qtyMerge) 
	{
		getModel().setQtyOrdered(qtyMerge);
	}

	@Override
	public BigDecimal getQtyMerge() 
	{
		return getModel().getQtyOrdered();
	}

	@Override
	public void setLineNetAmt(BigDecimal lineNetAmt) 
	{
		getModel().setLineNetAmt(lineNetAmt);
	}

	@Override
	public BigDecimal getLineNetAmt() 
	{
		return getModel().getLineNetAmt();
	}
	
	@Override
	public int getPrecision()
	{
		return getModel().getPrecision();
	}
	
	public void setQty(BigDecimal qty)
	{
		getModel().setQtyEntered(qty);
		getModel().setQtyOrdered(qty);
	}

	@Override
	public void setPriceList(BigDecimal priceList) 
	{
		getModel().setPriceList(priceList);;
	}

	@Override
	public void setPriceLimit(BigDecimal priceLimit) 
	{
		getModel().setPriceLimit(priceLimit);
	}

	@Override
	public BigDecimal getPriceLimit() 
	{
		return getModel().getPriceLimit();
	}

	@Override
	public void setLineNetAmt() 
	{
		getModel().setLineNetAmt();
	}

	@Override
	public void setPriceCost(BigDecimal priceCOst) 
	{
		getModel().setPriceCost(priceCOst);
	}

	@Override
	public BigDecimal getPriceCost() 
	{
		return getModel().getPriceCost();
	}

	@Override
	public int getRefLine_ID() {
		return getModel().getRef_OrderLine_ID();
	}

	@Override
	public int getLinkedLine_ID() {
		return getModel().getLink_OrderLine_ID();
	}

}
