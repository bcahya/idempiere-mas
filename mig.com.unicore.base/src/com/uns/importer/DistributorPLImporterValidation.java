/**
 * 
 */
package com.uns.importer;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Properties;

import jxl.Sheet;
import com.uns.model.process.SimpleImportXLS;

import org.compiere.model.MProductPrice;
import org.compiere.model.PO;
import org.compiere.util.DB;

/**
 * @author AzHaidar
 *
 */
public class DistributorPLImporterValidation implements ImporterValidation {

	protected Properties 	m_ctx = null;
	protected String		m_trxName = null;
	protected Sheet			m_sheet	= null;
	protected Hashtable<String, PO> m_PORefMap = null;
	
	public static final String	COL_DIVISION = "Division";
	public static final String COL_PRICE = "BasicPrice";

	public static final int DIV_SR = 1000031;
	public static final int DIV_FL = 1000029;
	public static final int DIV_MAS = 1000030;
	public static final int DIV_DUDMAS = 1000032;
		
	/**
	 * 
	 */
	public DistributorPLImporterValidation(Properties ctx, Sheet sheet, String trxName) {
		m_ctx = ctx;
		m_trxName = trxName;
		m_sheet = sheet;
	}

	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
		
	}
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int row) 
	{	
		Object isZeroable = freeColVals.get("I");
		
		BigDecimal basicPrice = (BigDecimal) freeColVals.get(COL_PRICE);
		
		if (isZeroable != null )
		{
			boolean isZeroableTF = (Boolean) isZeroable;
			//BigDecimal priceList = ((MProductPrice) po).getPriceList();
			
			if (!isZeroableTF && basicPrice.signum() == 0)
				return SimpleImportXLS.DONT_SAVE;
		}
		
		MProductPrice pp = (MProductPrice) po;
		
		String sql = "SELECT Division_ID FROM UNS_Product_Division WHERE M_Product_ID=?";
		int division_ID = DB.getSQLValue(m_trxName, sql, pp.getM_Product_ID());
		
		BigDecimal price = basicPrice;
		
		if (division_ID == DIV_DUDMAS || division_ID == DIV_SR) // PPN Org, use Non PPN Price.
		{
			price = basicPrice.multiply(new BigDecimal (1.1));
			BigDecimal priceDivTen = price.divide(BigDecimal.TEN, 1, BigDecimal.ROUND_UP);
			long exactPrice = priceDivTen.longValue();
			double decimalAmt = priceDivTen.doubleValue() - exactPrice;
			
			if (decimalAmt <= 0.2) {
				price = new BigDecimal (exactPrice * 10);
			}
		}
		
		pp.setPrices(price, price, price);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
	 */
	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int row) 
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveAllRow(java.util.Hashtable, org.compiere.model.PO[])
	 */
	@Override
	public String afterSaveAllRow(Hashtable<String, PO> poRefMap, PO[] pos) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#getPOReferenceMap()
	 */
	@Override
	public Hashtable<String, PO> getPOReferenceMap() {
		return null;
	}

}
