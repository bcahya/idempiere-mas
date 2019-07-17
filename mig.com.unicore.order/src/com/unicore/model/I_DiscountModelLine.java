/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;

import org.compiere.model.PO;

import com.uns.model.MProduct;

/**
 * @author root
 *
 */
public interface I_DiscountModelLine {

	/**
	 * Set PO of this model.
	 * @param po
	 */
	public void setModel(PO po);
	
	/**
	 * get Model.
	 * @return {@link PO} 
	 */
	public PO getModel();
	
	/**
	 * Get Product 
	 * @return MProduct
	 */
	public MProduct getProduct();
	
	/**
	 * Set Product
	 * @param MProduct proruct
	 */
	public void setProduct(MProduct proruct);
	
	/**
	 * Set Discount Amount
	 * @param BigDecimal discountAmt
	 */
	public void setDiscountAmt(BigDecimal discountAmt);
	
	/**
	 * Get Discount Amount
	 * @return {@link BigDecimal} Discount Amount
	 */
	public BigDecimal getDiscountAmt();
	
	/**
	 * Set Discount percent
	 * @param BigDecimal discount
	 */
	public void setDiscount(BigDecimal discount);
	
	/**
	 * Get Discount percent
	 * @return {@link BigDecimal} Discount %
	 */
	public BigDecimal getDiscount();
	
	/**
	 * SetPrice
	 * @param price
	 */
	public void setPrice(BigDecimal price);
	
	/**
	 * Set Price...
	 */
	public void setPrice();
	
	/**
	 * Set Header Info
	 * @param header
	 */
	public void setHeaderInfo(PO header);
	
	/**
	 * Set refrence ID of Line
	 * @param ref_ID
	 */
	public void setRefLine_ID(int ref_ID);
	
	/**
	 * This model is discount bonus
	 * @return booelan true if this is discount bonus.
	 */
	public boolean isProductBonuses();
	
	/**
	 * 
	 * @param isDiscountBonus
	 */
	public void setIsProductBonus(boolean isDiscountBonus);
	
	/**
	 * Get List Price.
	 * @return
	 */
	public BigDecimal getPriceList();
	
	/**
	 * Get Price Entered Of PO
	 * @return
	 */
	public BigDecimal getPriceEntered();
	
	/**
	 * Get Price Actual of PO
	 * @return
	 */
	public BigDecimal getPriceActual();
	
	/**
	 * 
	 * @param priceEntered
	 */
	public void setPriceEntered(BigDecimal priceEntered);
	
	/**
	 * 
	 * @param priceActual
	 */
	public void setPriceActual(BigDecimal priceActual);
	
	/**
	 * set Qty Merge of Qty bonuses And Qty entered
	 * @param qtyMerge
	 */
	public void setQtyMerge(BigDecimal qtyMerge);
	
	/**
	 * Get Qty Merge Qty Bonuses + Entered
	 * @return
	 */
	public BigDecimal getQtyMerge();
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getQtyEntered();
	
	/**
	 * 
	 * @param qtyEntered
	 */
	public void setQtyEntered(BigDecimal qtyEntered);
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getQtyBonuses();
	
	/**
	 * 
	 * @param qtyBonuses
	 */
	public void setQtyBonuses(BigDecimal qtyBonuses);
	
	/**
	 * Set Line net amount
	 * @param lineNetAmt
	 */
	public void setLineNetAmt(BigDecimal lineNetAmt);
	
	/**
	 * Get Line Net Amount
	 * @return
	 */
	public BigDecimal getLineNetAmt();
	
	/**
	 * Get Precision.
	 * @return
	 */
	public int getPrecision();
	
	/**
	 * 
	 * @param qty
	 */
	public void setQty(BigDecimal qty);
	
	/**
	 * 
	 * @param priceList
	 */
	public void setPriceList(BigDecimal priceList);
	
	/**
	 * 
	 * @param priceLimit
	 */
	public void setPriceLimit(BigDecimal priceLimit);
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getPriceLimit();
	
	/**
	 * 
	 */
	public void setLineNetAmt();
	
	/**
	 * 
	 * @param priceCOst
	 */
	public void setPriceCost(BigDecimal priceCOst);
	
	
	/**
	 * 
	 */
	public BigDecimal getPriceCost();
	
	public int getRefLine_ID();
	
	public int getLinkedLine_ID();
}
