/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MStorageOnHand;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author root
 *
 */
public class MUNSCvsRptProduct extends X_UNS_Cvs_RptProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID	= 5445753378941958749L;
	private MUNSCvsRptCustomer m_parent					= null;
	private MUNSCvsRptProductMA[] m_lines		= null;

	/**
	 * @param ctx
	 * @param UNS_Cvs_RptProduct_ID
	 * @param trxName
	 */
	public MUNSCvsRptProduct(Properties ctx, int UNS_Cvs_RptProduct_ID,
			String trxName) {
		super(ctx, UNS_Cvs_RptProduct_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCvsRptProduct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSCvsRptProduct(MUNSCvsRptCustomer parent)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setUNS_Cvs_RptCustomer_ID(parent.get_ID());
	}
	
	public void setParent(MUNSCvsRptCustomer parent)
	{
		this.m_parent = parent;
	}
	
	public MUNSCvsRptCustomer getParent(boolean requery)
	{
		if(null != m_parent && !requery)
			return m_parent;
		
		m_parent = (MUNSCvsRptCustomer) getUNS_Cvs_RptCustomer();
		return m_parent;
	}
	
	public MUNSCvsRptCustomer getParent()
	{
		return getParent(false);
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSCvsRptProductMA[] getLines()
	{
		return getLines(false);
	}
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSCvsRptProductMA[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSCvsRptProductMA> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSCvsRptProductMA.Table_Name
				, Table_Name + "_ID = ?", get_TrxName())
				.setParameters(get_ID()).list();
		
		m_lines = new MUNSCvsRptProductMA[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	@Override
	public BigDecimal getQtyAvailable()
	{
		if(super.getQtyAvailable().signum() > 0)
			return super.getQtyAvailable();
		
		BigDecimal qtyAvailable = MStorageOnHand.getQtyOnHandForLocator(
				getM_Product_ID(), getM_Locator_ID(), getM_AttributeSetInstance_ID(), get_TrxName());
		set_Value(COLUMNNAME_QtyAvailable, qtyAvailable);
		return super.getQtyAvailable();
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(!isManual())
		{
			boolean needSetPrice = getPriceLimit().signum() == 0 || getPriceList().signum() == 0 ? true : false;
			if(needSetPrice)
			{
				SimplePrice price = new SimplePrice(
						getUNS_Cvs_RptCustomer().getUNS_Cvs_Rpt().getC_BPartner_ID(), getM_Product_ID(), get_TrxName());
				
				if(getPriceLimit().signum() == 0) setPriceLimit(price.getPriceLimit());
				if(getPriceList().signum() == 0) setPriceList(price.getPriceList());
			}
		}
		
		if(getQtySold().compareTo(getQtyAvailable()) > 0)
		{
			log.saveError("Error", "Over qty sold. Qty sold can't bigger than qty available");
			return false;
		}
		
		if(isProductBonuses())
			setPriceActual(Env.ZERO);
		
		setLineNetAmt();
		
		return super.beforeSave(newRecord);
	}
	
	public void setLineNetAmt()
	{
		setLineNetAmt(getPriceActual().multiply(getQtySold()));
	}
	
	public boolean afterSave(boolean newRecord, boolean success)
	{
		if(getUOMQtySoldL1().signum() != 0 && getUOMConversionL1_ID() <= 0)
		{
			log.saveError("Error", "for product " + getM_Product().getName() + " not avaliable UOM Convertion L1");
			return false;
		}
		else if(getUOMQtySoldL2().signum() != 0 && getUOMConversionL2_ID() <= 0)
		{
			log.saveError("Error", "for product " + getM_Product().getName() + " not avaliable UOM Convertion L2");
			return false;
		}
		else if(getUOMQtySoldL3().signum() != 0 && getUOMConversionL3_ID() <= 0)
		{
			log.saveError("Error", "for product " + getM_Product().getName() + " not avaliable UOM Convertion L3");
			return false;
		}
		else if(getUOMQtySoldL4().signum() != 0 && getUOMConversionL4_ID() <= 0)
		{
			log.saveError("Error", "for product " + getM_Product().getName() + " not avaliable UOM Convertion L4");
			return false;
		}
		
		if(getQtySold().compareTo(getQtyAvailable()) > 0)
		{
			log.saveError("Error", "Over qty sold. Qty sold can't bigger than qty available");
			return false;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE UNS_Cvs_RptProduct SET ").append(COLUMNNAME_LineNetAmt).append("=")
		.append(getPriceActual()).append("*").append(getQtySold()).append(" WHERE ")
		.append(COLUMNNAME_UNS_Cvs_RptProduct_ID).append("=").append(get_ID());
		DB.executeUpdate(sb.toString(), get_TrxName());
		
		updateHeader();
		return super.afterSave(newRecord, success);
	}
	
	private void updateHeader()
	{
		BigDecimal totalLines = DB.getSQLValueBD(
				get_TrxName()
				, "SELECT COALESCE(SUM(LineNetAmt), 0) FROM UNS_Cvs_RptProduct WHERE UNS_Cvs_RptCustomer_ID = ?"
				, getParent().get_ID());
		
		String update = "UPDATE UNS_Cvs_RptCustomer SET TotalLines = ? WHERE UNS_Cvs_RptCustomer_ID = ?";
		int ok = DB.executeUpdate(
				update, new Object[]{totalLines, getParent().get_ID()}
				, false , get_TrxName());
		if(ok == -1)
		{
			throw new AdempiereException("Failed on update header");
		}
		
		getParent().updateHeader();
	}
	
	protected boolean afterDelete(boolean success)
	{
		updateHeader();
		
		return true;
	}
	
	public void setPrice()
	{
		SimplePrice price = new SimplePrice(
				getParent().getParent().getC_BPartner_ID(), getM_Product_ID(), get_TrxName());
//		price.setPrecission(getParent().getParent().getPrecission());
		
		setPriceList(price.getPriceList());
		setPriceLimit(price.getPriceLimit());
		setPriceActual(price.getPriceActual());
	}
	
	public boolean isProductBonuses()
	{
		return get_ValueAsBoolean("isProductBonuses");
	}
}
