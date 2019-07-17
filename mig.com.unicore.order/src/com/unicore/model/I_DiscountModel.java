/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MBPartner;
import org.compiere.model.PO;

/**
 * @author root
 *
 */
public interface I_DiscountModel 
{
	
	/**
	 * Set PO for this model
	 * @param po
	 */
	public void setModel(PO po);
	
	/**
	 * get Model
	 * @return
	 */
	public PO getModel();
	
	/**
	 * get Lines discount model Implements I_DiscountModelLine.
	 * @param requery
	 * @return Array of I_DiscountModelLine.
	 */
	public List<I_DiscountModelLine> getLines(boolean requery);
	
	/**
	 * Get line of product
	 * @param M_Product_ID
	 * @return
	 */
	public I_DiscountModelLine getByProduct(int M_Product_ID, int refLine_ID, boolean isDiscountedToBonuses);
	
	/**
	 * Set Discount percent.
	 * @param discount
	 */
	public void setDiscount(BigDecimal discount);
	
	/**
	 * Get Discount percent
	 * @return {@link BigDecimal} discount percent.
	 */
	public BigDecimal getDiscount();
	
	/**
	 * Set Discount Amt
	 * @param discount
	 */
	public void setDiscountAmt(BigDecimal discount);
	
	/**
	 * Get Discount Amt 
	 * @return {@link BigDecimal} discount Amount
	 */
	public BigDecimal getDiscountAmt();
	
	/**
	 * Set Grand Total
	 * @param GrandTotal
	 */
	public void setGrandTotal(BigDecimal GrandTotal);
	
	/**
	 * Get Grand Total
	 * @return {@link BigDecimal} Grand Total.
	 */
	public BigDecimal getGrandTotal();
	
	/**
	 * Set Total Lines
	 * @param TotalLines
	 */
	public void setTotalLines(BigDecimal TotalLines);
	
	/**
	 * Get Total Lines
	 * @return BigDecimal Total Lines
	 */
	public BigDecimal getTotalLines();
	
	/**
	 * Set Customer/Vendor
	 * @param Bpartner
	 */
	public void setBpartner(MBPartner Bpartner);
	
	/**
	 * Get Customer/Vendor
	 * @return MBPartner Customer/Vendor
	 */
	public MBPartner getBPartner();
	
	/**
	 * Set Sales Representative
	 * @param partner
	 */
	public void setSalesRep(MBPartner partner);
	
	/**
	 * get Sales Representative
	 * @return MBPartner of sales rep.
	 */
	public MBPartner getSalesRep();
	
	/**
	 * this day is customer birthday or no
	 * @return boolean true if is customer Birthday
	 */
	public boolean isBirthday();
	
	/**
	 * set this day is birthday of customer or no
	 * @param boolean isBirthday
	 */
	public void setIsBirthDay(boolean isBirthday);
	
	/**
	 * Get Transaction Name
	 * @return
	 */
	public String get_TrxName();
	
	/**
	 * Get Context
	 * @return
	 */
	public Properties getCtx();
	
	/**
	 * Date Discount
	 * @return
	 */
	public Timestamp getDateDiscounted();
	
	/**
	 * 
	 * @param dateDiscounted
	 */
	public void setDateDiscounted(Timestamp dateDiscounted);
	
	/**
	 * Is Sales Transaction ?
	 * @return
	 */
	public boolean isSOTrx();
	
	public void initialRequirements();
	
	public boolean isPickup();
	
	/**
	 * Document is cash payment
	 * @return
	 */
	public boolean isCash();
}