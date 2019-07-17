/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;

import org.compiere.model.PO;
import org.compiere.util.Env;

import com.uns.model.MProduct;

import com.unicore.base.model.MInvoiceLine;

/**
 * @author root
 *
 */
public class UNSInvoiceLineDiscountModel implements I_DiscountModelLine {

	private MInvoiceLine m_model = null;
	/**
	 * 
	 */
	public UNSInvoiceLineDiscountModel() {
		super();
	}
	
	public UNSInvoiceLineDiscountModel(PO model) {
		setModel(model);
		initializeReuirement();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setModel(org.compiere.model.PO)
	 */
	@Override
	public void setModel(PO po) {
		m_model = (MInvoiceLine) po;
		initializeReuirement();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getModel()
	 */
	@Override
	public MInvoiceLine getModel() {
		return m_model;
	}
	
	private MProduct m_product = null;

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getProduct()
	 */
	@Override
	public MProduct getProduct() {
		return m_product;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setProduct(com.uns.model.MProduct)
	 */
	@Override
	public void setProduct(MProduct proruct) {
		m_product = proruct;
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setDiscountAmt(java.math.BigDecimal)
	 */
	@Override
	public void setDiscountAmt(BigDecimal discountAmt) {
		getModel().setDiscountAmt(discountAmt);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getDiscountAmt()
	 */
	@Override
	public BigDecimal getDiscountAmt() {
		return getModel().getDiscountAmt();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setDiscount(java.math.BigDecimal)
	 */
	@Override
	public void setDiscount(BigDecimal discount) {
		getModel().setDiscount(discount);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getDiscount()
	 */
	@Override
	public BigDecimal getDiscount() {
		return getModel().getDiscount();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setPrice(java.math.BigDecimal)
	 */
	@Override
	public void setPrice(BigDecimal price) {
		getModel().setPrice(price);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setPrice()
	 */
	@Override
	public void setPrice() {
		getModel().setPrice();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setHeaderInfo(org.compiere.model.PO)
	 */
	@Override
	public void setHeaderInfo(PO header) {
		// no action
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setRefLine_ID(int)
	 */
	@Override
	public void setRefLine_ID(int ref_ID) {
		getModel().setRef_InvoiceLine_ID(ref_ID);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#isProductBonuses()
	 */
	@Override
	public boolean isProductBonuses() {
		return getModel().isProductBonuses();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setIsProductBonus(boolean)
	 */
	@Override
	public void setIsProductBonus(boolean isDiscountBonus) {
		getModel().setisProductBonuses(isDiscountBonus);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getPriceList()
	 */
	@Override
	public BigDecimal getPriceList() {
		return getModel().getPriceList();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getPriceEntered()
	 */
	@Override
	public BigDecimal getPriceEntered() {
		return getModel().getPriceEntered();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getPriceActual()
	 */
	@Override
	public BigDecimal getPriceActual() {
		return getModel().getPriceActual();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setPriceEntered(java.math.BigDecimal)
	 */
	@Override
	public void setPriceEntered(BigDecimal priceEntered) {
		getModel().setPriceEntered(priceEntered);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setPriceActual(java.math.BigDecimal)
	 */
	@Override
	public void setPriceActual(BigDecimal priceActual) {
		getModel().setPriceActual(priceActual);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setQtyMerge(java.math.BigDecimal)
	 */
	@Override
	public void setQtyMerge(BigDecimal qtyMerge) {
		getModel().setQtyInvoiced(qtyMerge);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getQtyMerge()
	 */
	@Override
	public BigDecimal getQtyMerge() {
		return getModel().getQtyInvoiced();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getQtyEntered()
	 */
	@Override
	public BigDecimal getQtyEntered() {
		return getModel().getQtyEntered();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setQtyEntered(java.math.BigDecimal)
	 */
	@Override
	public void setQtyEntered(BigDecimal qtyEntered) {
		getModel().setQtyEntered(qtyEntered);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getQtyBonuses()
	 */
	@Override
	public BigDecimal getQtyBonuses() {
		return getModel().getQtyBonuses();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setQtyBonuses(java.math.BigDecimal)
	 */
	@Override
	public void setQtyBonuses(BigDecimal qtyBonuses) {
		getModel().setQtyBonuses(qtyBonuses);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setLineNetAmt(java.math.BigDecimal)
	 */
	@Override
	public void setLineNetAmt(BigDecimal lineNetAmt) {
		getModel().setLineNetAmt(lineNetAmt);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getLineNetAmt()
	 */
	@Override
	public BigDecimal getLineNetAmt() {
		return getModel().getLineNetAmt();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getPrecision()
	 */
	@Override
	public int getPrecision() {
		return getModel().getPrecision();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setQty(java.math.BigDecimal)
	 */
	@Override
	public void setQty(BigDecimal qty) {
		getModel().setQty(qty);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setPriceList(java.math.BigDecimal)
	 */
	@Override
	public void setPriceList(BigDecimal priceList) {
		getModel().setPriceList(priceList);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setPriceLimit(java.math.BigDecimal)
	 */
	@Override
	public void setPriceLimit(BigDecimal priceLimit) {
		getModel().setPriceLimit(priceLimit);
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getPriceLimit()
	 */
	@Override
	public BigDecimal getPriceLimit() {
		return getModel().getPriceLimit();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setLineNetAmt()
	 */
	@Override
	public void setLineNetAmt() {
		getModel().setLineNetAmt();
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#setPriceCost(java.math.BigDecimal)
	 */
	@Override
	public void setPriceCost(BigDecimal priceCOst) {
		//NO Action
	}

	/* (non-Javadoc)
	 * @see com.unicore.model.I_DiscountModelLine#getPriceCost()
	 */
	@Override
	public BigDecimal getPriceCost() {
		return Env.ZERO;
	}

	public void initializeReuirement()
	{
		setProduct(new MProduct(getModel().getCtx(), getModel().getM_Product_ID(), getModel().get_TrxName()));
	}

	@Override
	public int getRefLine_ID() {
		return getModel().getRef_InvoiceLine_ID();
	}

	@Override
	public int getLinkedLine_ID() {
		return getModel().getRef_InvoiceLine_ID();
	}
}
