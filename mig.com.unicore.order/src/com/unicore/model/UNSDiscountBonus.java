/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import org.compiere.model.MTable;
import org.compiere.util.Env;

/**
 * @author UNTA-Andy
 *
 */
public class UNSDiscountBonus
{
	
	private int M_Product_ID = 0;
	private int M_ProductBonus_ID = 0;
	private int C_UOM_ID = 0;
	private int C_UOMBonus_ID = 0;
	private BigDecimal qty = Env.ZERO;
	private BigDecimal qtyBonus = Env.ZERO;
	private BigDecimal price = Env.ZERO;
	private BigDecimal priceBonus = Env.ZERO;
	private BigDecimal lineNetAmount = Env.ZERO;
	private BigDecimal lineNetAmountBonus = Env.ZERO;
	private BigDecimal DiscountBonus = Env.ZERO;
	private Boolean isDiscountBonus = false;
	private Boolean isSameProduct = true;
	private int Org_Trx_ID = 0;
	private int Table_ID = 0;
	private int Record_ID = 0;
	private Properties ctx;
	private String TrxName;
	private boolean isNeedConfirm = false;
	private BigDecimal m_QtyMix = Env.ZERO;
	private BigDecimal m_lineNetAmtMix = Env.ZERO;
	private BigDecimal m_conversionRate = Env.ONE;
	
	/**
	 * 
	 */
	public UNSDiscountBonus()
	{
		// TODO Auto-generated constructor stub
	}

	public UNSDiscountBonus(Properties ctx, int table_ID, int record_ID, String TrxName){
		setCtx(ctx);
		setTable_ID(table_ID);
		setRecord_ID(record_ID);
		setTrxName(TrxName);
	}
	
	public void setDataValue(int Product_ID, int UOM_ID, BigDecimal qty, BigDecimal price, BigDecimal lineNetAmount, Boolean isDiscountBonus)
	{
		setIsDiscountBonus(isDiscountBonus);
		setIsSameProduct(false);
		
		if (!isDiscountBonus){
			setM_Product_ID(Product_ID);
			setC_UOM_ID(UOM_ID);
			setQty(qty);
			setPrice(price);
			setLineNetAmount(lineNetAmount);
		} else {
			setM_ProductBonus_ID(Product_ID);
			setC_UOMBonus_ID(UOM_ID);
			setQtyBonus(qty);
			setPriceBonus(price);
			setLineNetAmountBonus(lineNetAmount);
		}
	}
	
	/**
	 * @return the trxName
	 */
	public String getTrxName()
	{
		return TrxName;
	}

	/**
	 * @param trxName the trxName to set
	 */
	public void setTrxName(String trxName)
	{
		TrxName = trxName;
	}

	/**
	 * @return the ctx
	 */
	public Properties getCtx()
	{
		return ctx;
	}

	/**
	 * @param ctx the ctx to set
	 */
	public void setCtx(Properties ctx)
	{
		this.ctx = ctx;
	}

	/**
	 * @return the table_ID
	 */
	public int getTable_ID()
	{
		return Table_ID;
	}

	/**
	 * @param table_ID the table_ID to set
	 */
	public void setTable_ID(int table_ID)
	{
		Table_ID = table_ID;
	}

	/**
	 * @return the record_ID
	 */
	public int getRecord_ID()
	{
		return Record_ID;
	}

	/**
	 * @param record_ID the record_ID to set
	 */
	public void setRecord_ID(int record_ID)
	{
		Record_ID = record_ID;
	}

	/**
	 * @return the isSameProduct
	 */
	public Boolean getIsSameProduct()
	{
		return isSameProduct;
	}

	/**
	 * @param isSameProduct the isSameProduct to set
	 */
	public void setIsSameProduct(Boolean isSameProduct)
	{
		this.isSameProduct = isSameProduct;
	}

	/**
	 * @return the discountBonus
	 */
	public BigDecimal getDiscountBonus()
	{
		return DiscountBonus;
	}

	/**
	 * @param discountBonus the discountBonus to set
	 */
	public void setDiscountBonus(BigDecimal discountBonus)
	{
		DiscountBonus = discountBonus;
	}

	/**
	 * @return the m_Product_ID
	 */
	public int getM_Product_ID()
	{
		return M_Product_ID;
	}

	/**
	 * @param m_Product_ID the m_Product_ID to set
	 */
	public void setM_Product_ID(int m_Product_ID)
	{
		M_Product_ID = m_Product_ID;
	}

	/**
	 * @return the m_ProductBonus_ID
	 */
	public int getM_ProductBonus_ID()
	{
		return M_ProductBonus_ID;
	}

	/**
	 * @param m_ProductBonus_ID the m_ProductBonus_ID to set
	 */
	public void setM_ProductBonus_ID(int m_ProductBonus_ID)
	{
		M_ProductBonus_ID = m_ProductBonus_ID;
	}

	/**
	 * @return the c_UOM_ID
	 */
	public int getC_UOM_ID()
	{
		return C_UOM_ID;
	}

	/**
	 * @param c_UOM_ID the c_UOM_ID to set
	 */
	public void setC_UOM_ID(int c_UOM_ID)
	{
		C_UOM_ID = c_UOM_ID;
	}

	/**
	 * @return the c_UOMBonus_ID
	 */
	public int getC_UOMBonus_ID()
	{
		return C_UOMBonus_ID;
	}

	/**
	 * @param c_UOMBonus_ID the c_UOMBonus_ID to set
	 */
	public void setC_UOMBonus_ID(int c_UOMBonus_ID)
	{
		C_UOMBonus_ID = c_UOMBonus_ID;
	}

	/**
	 * @return the qty
	 */
	public BigDecimal getQty()
	{
		return qty;
	}

	/**
	 * @param qty the qty to set
	 */
	public void setQty(BigDecimal qty)
	{
		this.qty = qty;
	}

	/**
	 * @return the qtyBonus
	 */
	public BigDecimal getQtyBonus()
	{
		return qtyBonus;
	}

	/**
	 * @param qtyBonus the qtyBonus to set
	 */
	public void setQtyBonus(BigDecimal qtyBonus)
	{
		this.qtyBonus = qtyBonus;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice()
	{
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	/**
	 * @return the priceBonus
	 */
	public BigDecimal getPriceBonus()
	{
		return priceBonus;
	}

	/**
	 * @param priceBonus the priceBonus to set
	 */
	public void setPriceBonus(BigDecimal priceBonus)
	{
		this.priceBonus = priceBonus;
	}

	/**
	 * @return the lineNetAmount
	 */
	public BigDecimal getLineNetAmount()
	{
		return lineNetAmount;
	}

	/**
	 * @param lineNetAmount the lineNetAmount to set
	 */
	public void setLineNetAmount(BigDecimal lineNetAmount)
	{
		this.lineNetAmount = lineNetAmount;
	}

	/**
	 * @return the lineNetAmountBonus
	 */
	public BigDecimal getLineNetAmountBonus()
	{
		return lineNetAmountBonus;
	}

	/**
	 * @param lineNetAmountBonus the lineNetAmountBonus to set
	 */
	public void setLineNetAmountBonus(BigDecimal lineNetAmountBonus)
	{
		this.lineNetAmountBonus = lineNetAmountBonus;
	}

	/**
	 * @return the isDiscountBonus
	 */
	public Boolean getIsDiscountBonus()
	{
		return isDiscountBonus;
	}

	/**
	 * @param isDiscountBonus the isDiscountBonus to set
	 */
	public void setIsDiscountBonus(Boolean isDiscountBonus)
	{
		this.isDiscountBonus = isDiscountBonus;
	}
	
	/**
	 * Set Organization transaction
	 * @param AD_Org_ID
	 */
	public void setOrgTrx_ID(int AD_Org_ID)
	{
		this.Org_Trx_ID = AD_Org_ID;
	}
	
	public int getOrgTrx_ID()
	{
		return this.Org_Trx_ID;
	}
	
	/**************************************************************************
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("UNSDiscountBonus[")
			.append(MTable.getTableName(getCtx(), getTable_ID())).append("-").append(getRecord_ID())
			.append(",Product=").append(getM_Product_ID())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	public boolean isNeedConfirmation()
	{
		return this.isNeedConfirm;
	}
	
	public void setNeedConfirmation(boolean isNeedConfirm)
	{
		this.isNeedConfirm = isNeedConfirm;
	}
	
	public BigDecimal getQtyMix()
	{
		return m_QtyMix;
	}
	
	/**
	 * 
	 * @param qtyMix
	 */
	public void setQtyMix(BigDecimal qtyMix)
	{
		this.m_QtyMix = qtyMix;
	}
	
	public void setLineNetAmtMix(BigDecimal lineNetAmt)
	{
		this.m_lineNetAmtMix = lineNetAmt;
	}
	
	public BigDecimal getLinenetAmtMix()
	{
		return this.m_lineNetAmtMix;
	}
	
	public BigDecimal getConversionRate ()
	{
		return m_conversionRate;
	}
	
	public void setConversionRate (BigDecimal conversionRate)
	{
		m_conversionRate = conversionRate;
	}
	
	public BigDecimal getConversionMixQty ()
	{		
		return getQtyMix().multiply(getConversionRate()).setScale(2, RoundingMode.HALF_UP);
	}
	
	public BigDecimal getConversionQty ()
	{
		return getQty().multiply(getConversionRate()).setScale(2, RoundingMode.HALF_UP);
	}
	
	public void setConversionMixQty (BigDecimal value)
	{
		setQtyMix(value.divide(getConversionRate(), 5 , RoundingMode.HALF_EVEN)); 
	}
	
	public void setConversionQty (BigDecimal value)
	{
		setQty(value.divide(getConversionRate(), 5, RoundingMode.HALF_EVEN));
	}
}
