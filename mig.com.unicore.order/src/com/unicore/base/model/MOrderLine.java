/**
 * 
 */
package com.unicore.base.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
//import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MWarehouse;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.MUNSDiscountTrx;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author UNTA-Andy
 * 
 */
public class MOrderLine extends org.compiere.model.MOrderLine
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2461216245462830089L;
	private MOrder m_parent;

	/**
	 * @param ctx
	 * @param C_OrderLine_ID
	 * @param trxName
	 */
	public MOrderLine(Properties ctx, int C_OrderLine_ID, String trxName)
	{
		super(ctx, C_OrderLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param order
	 */
	public MOrderLine(MOrder order)
	{
		super(order);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MOrderLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.MOrderLine#getParent()
	 */
	@Override
	public MOrder getParent()
	{
		if (m_parent == null)
			m_parent = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());

		return m_parent;
	}

	/*
	 * @return line without saving
	 */
	public static MOrderLine get(MOrder parent, int M_Product_ID)
	{
		MOrderLine retValue = null;
		for (MOrderLine line : parent.getLines())
		{
			if (line.getM_Product_ID() == M_Product_ID)
			{
				retValue = line;
				break;
			}
		}

		if (retValue == null)
		{
			retValue = new MOrderLine(parent);
			retValue.setProduct(MProduct.get(parent.getCtx(), M_Product_ID));
			retValue.setisProductBonuses(true);
			parent.addLines(retValue);
			retValue.setQty(Env.ZERO);
		}

		retValue.setHeaderInfo(parent);
		return retValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.MOrderLine#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
//		if ((newRecord || is_ValueChanged(COLUMNNAME_DiscountAmt))
//				&& getDiscountAmt().compareTo(Env.ZERO) > 0 
//				&& getPriceList().compareTo(Env.ZERO) > 0)
//		{
//			// if (getPriceList().)
//			BigDecimal actualPrice =
//					getPriceList().subtract(
//							getDiscountAmt().divide(getQtyEntered(), getPrecision(), RoundingMode.HALF_UP));
//			setPrice(actualPrice);
//		}
//		else if ((newRecord || is_ValueChanged(COLUMNNAME_StandartDiscountAmt))
//				&& getStandartDiscount().compareTo(Env.ZERO) > 0 
//				&& getPriceList().compareTo(Env.ZERO) > 0)
//		{
//			BigDecimal actualPrice =
//					getPriceList().subtract(
//							getStandartDiscountAmt()
//									.divide(getQtyEntered(), getPrecision(), RoundingMode.HALF_UP));
//			setPrice(actualPrice);
//		}

		//Auto fill ASI if found single ASI on Storage
//		if (getM_AttributeSetInstance_ID() == 0 && getParent().isSOTrx()){
//			MLocator locator = Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MLocator.Table_Name, 
//					"M_Warehouse_ID = ? AND value LIKE ?", get_TrxName())
//					.setParameters(getM_Warehouse_ID(), "Good%").firstOnly();
//				
//			MStorageOnHand[] listsoh = MStorageOnHand.getAll(getCtx(), getM_Product_ID(), locator.get_ID(), get_TrxName());
//			
//			for (MStorageOnHand soh : listsoh){
//				if (soh.getQtyOnHand().compareTo(getQtyOrdered().add(getQtyBonuses())) > 0)
//					setM_AttributeSetInstance_ID(soh.getM_AttributeSetInstance_ID());
//			}			
//		}
		
//		boolean retval = super.beforeSave(newRecord);
//
//		if (retval)
//		{
//			// setLineNetAmt(getLineNetAmt().subtract(getDiscount()));
//			setQtyOrdered(getQtyEntered().add(getQtyBonuses()));
//		}

		return super.beforeSave(newRecord);
	}
	
	public boolean afterSave(boolean newRecord, boolean success)
	{
		MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		if(order.getUNS_OrderGroup_ID() > 0)
		{
			if(!order.afterSave(newRecord, success))
			{
				log.saveError("Error", "Failed when trying update header");
				return false;
			}
		}
		return super.afterSave(newRecord, success);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.MOrderLine#setDiscount()
	 * 
	 * @Override public void setDiscount() { // DO NOTHING //super.setDiscount(); }
	 */

	/**
	 * Calculate Extended Amt. May or may not include tax
	 */
	public void setLineNetAmt()
	{
//		BigDecimal discFromBonus = getQtyBonuses().multiply(getPriceList());
		BigDecimal bd = getPriceActual().multiply(getQtyOrdered());
//		getQtyEntered().multiply(getPriceList()).subtract(getDiscountAmt()).add(discFromBonus);

		boolean documentLevel = getTax().isDocumentLevel(this);

		// juddm: Tax Exempt & Tax Included in Price List & not Document Level - Adjust Line Amount
		// http://sourceforge.net/tracker/index.php?func=detail&aid=1733602&group_id=176962&atid=879332
		if (isTaxIncluded() && !documentLevel && getPriceList().compareTo(Env.ZERO) > 0)
		{
			BigDecimal taxStdAmt = Env.ZERO, taxThisAmt = Env.ZERO;

			MTax orderTax = getTax();
			MTax stdTax = null;

			// get the standard tax
			if (getProduct() == null)
			{
				if (getCharge() != null) // Charge
				{
					stdTax =
							new MTax(getCtx(), ((MTaxCategory) getCharge().getC_TaxCategory()).getDefaultTax()
									.getC_Tax_ID(), get_TrxName());
				}

			}
			else
				// Product
				stdTax =
						new MTax(getCtx(), ((MTaxCategory) getProduct().getC_TaxCategory()).getDefaultTax()
								.getC_Tax_ID(), get_TrxName());

			if (stdTax != null)
			{
				if (log.isLoggable(Level.FINE))
				{
					log.fine("stdTax rate is " + stdTax.getRate());
					log.fine("orderTax rate is " + orderTax.getRate());
				}

				taxThisAmt = taxThisAmt.add(orderTax.calculateTax(bd, isTaxIncluded(), getPrecision()));
				taxStdAmt = taxStdAmt.add(stdTax.calculateTax(bd, isTaxIncluded(), getPrecision()));

				bd = bd.subtract(taxStdAmt).add(taxThisAmt);

				if (log.isLoggable(Level.FINE))
					log.fine("Price List includes Tax and Tax Changed on Order Line: New Tax Amt: "
							+ taxThisAmt + " Standard Tax Amt: " + taxStdAmt + " Line Net Amt: " + bd);
			}

		}
		int precision = getPrecision();
		if (bd.scale() > precision)
			bd = bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
		super.setLineNetAmt(bd);
	} // setLineNetAmt
	
	private MUNSDiscountTrx[] m_discountsTrx = null;
	
	public MUNSDiscountTrx[] getDiscountsTrx(boolean requery)
	{
		if(null != m_discountsTrx && !requery)
		{
			set_TrxName(m_discountsTrx, get_TrxName());
			return m_discountsTrx;
		}
		
		List<MUNSDiscountTrx> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSDiscountTrx.Table_Name
				, MUNSDiscountTrx.COLUMNNAME_C_OrderLine_ID + "=?", get_TrxName())
				.setParameters(getC_OrderLine_ID())
				.setOrderBy(MUNSDiscountTrx.COLUMNNAME_SeqNo).list();
		
		m_discountsTrx = new MUNSDiscountTrx[list.size()];
		list.toArray(m_discountsTrx);
		
		return m_discountsTrx;		
	}
	
	public MLocator getDefaultLocator()
	{
		MLocator loc = MLocator.getDefault((MWarehouse) getM_Warehouse());
		if(null == loc)
			throw new AdempiereUserError("No default locator from warehouse " 
							+ getM_Warehouse().getName());
		return loc;
	}
	
	
	/**
	 * Get Array of Canvas Order line. order by canvas order date.
	 * FOFO (First Order First Out)
	 * @param C_BPartner_ID
	 * @param M_Product_ID
	 * @param trxName
	 * @return
	 */
	public static MOrderLine[] getFOFO(int C_BPartner_ID, int M_Product_ID, String trxName)
	{
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(Table_Name)
				.append(" WHERE ").append(COLUMNNAME_M_Product_ID).append("= ? ")
				.append(" AND ").append(COLUMNNAME_C_Order_ID).append(" IN (SELECT ")
				.append(COLUMNNAME_C_Order_ID).append(" FROM ").append(MOrder.Table_Name)
				.append(" WHERE ").append(MOrder.COLUMNNAME_C_BPartner_ID).append(" = ?")
				.append(" AND (").append(COLUMNNAME_QtyOrdered).append("-")
				.append(COLUMNNAME_QtyDelivered).append(")").append("<> 0")
				.append(" ORDER BY ").append(MOrder.COLUMNNAME_DateOrdered).append(")");
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = sb.toString();
		List<MOrderLine> list = new ArrayList<>();
		MOrderLine[] retVals = null;
		
		try
		{
			st = DB.prepareStatement(sql, trxName);
			st.setInt(1, M_Product_ID);
			st.setInt(2, C_BPartner_ID);
			rs = st.executeQuery();
			while (rs.next())
			{
				MOrderLine record = new MOrderLine(Env.getCtx(), rs, trxName);
				list.add(record);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, st);
		}
		
		retVals = new MOrderLine[list.size()];
		list.toArray(retVals);
		
		return retVals;
	}
}
