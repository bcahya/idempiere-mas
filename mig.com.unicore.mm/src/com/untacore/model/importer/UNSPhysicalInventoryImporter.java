/**
 * 
 */
package com.untacore.model.importer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MLocator;
import org.compiere.model.MOrg;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.unicore.model.X_UNS_Product_Division;
import com.uns.importer.ImporterValidation;
import com.uns.model.MProduct;
import com.uns.model.process.SimpleImportXLS;

/**
 * @author akar
 *
 */
public class UNSPhysicalInventoryImporter implements ImporterValidation {

	/**
	 * 
	 */
	public UNSPhysicalInventoryImporter() {
		super();
	}
	
	private StringBuilder m_errorMsg = new StringBuilder();
	private Properties m_ctx = null;
	private Sheet m_sheet = null;
	private String m_trxName = null;
	private final String COLUMN_KD_OPNAME = "A";
	private final String COLUMN_TGL_TRANSAKSI = "B";
	private final String COLUMN_KD_CABANG = "C";
	private final String COLUMN_KD_BARANG = "D";
	private final String COLUMN_QTY_FISIK = "E";
	private final String COLUMN_STATUS_GUDANG = "F";
	private Hashtable<Object, Object> m_localCache = new Hashtable<>();
	private final String CACHE_LOC = "LOC-";
	private final String CACHE_ORG = "ORG-";
	private final String CACHE_PRD = "PRD-";
	private final String CACHE_DIV = "DIV-";
	private Hashtable<String, MInventory> m_mapInventory = new Hashtable<>();
	private final String INVENTORY_TYPE = MInventoryLine.INVENTORYTYPE_InventoryDifference;
	private final String CACHE_QTY_BUKU = "QTY_BOOK-";
	
	public UNSPhysicalInventoryImporter(Properties ctx, Sheet sheet, String trxName)
	{
		this.m_ctx = ctx; this.m_sheet = sheet; this.m_trxName = trxName;
	}

	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
	}
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable, int)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) {
		
		MProduct product = getProduct(currentRow);
		
		MLocator locator = getM_Locator(currentRow);
		if(null == locator)
			throw new AdempiereException("Can't initialize locator in row " + currentRow);
		MInventoryLine record = getExistsRecord(
				product.get_ID(), locator.get_ID(), locator.getM_Warehouse_ID()
				, getKodeOpname(currentRow), getTanggalTransaksi(currentRow));
		if(null == record)
		{
			record = new MInventoryLine(m_ctx, 0, m_trxName);
			record.setAD_Org_ID(locator.getAD_Org_ID());
			record.setM_Locator_ID(locator.get_ID());
			record.setInventoryType(INVENTORY_TYPE);
			record.setM_Product_ID(product.get_ID());
		}
		
		BigDecimal qtyBook = (BigDecimal) m_localCache.get(CACHE_QTY_BUKU.concat(product.getValue()));
		if(null == qtyBook)
		{
			qtyBook = MStorageOnHand.getQtyOnHandForLocator(
					product.get_ID(), locator.get_ID(), 0, m_trxName);
		}
		if(product.getValue().equals("HC 500"))
		{
			String test = "Berenti";
			test.toString();
		}
		BigDecimal qtyCount = getQtyFisik(currentRow);
		
		BigDecimal conversionVal = product.getConversionQtyLowestToBase();
		qtyCount = qtyCount.divide(conversionVal, product.getPrecission(product.getBaseLevelAsInt()), RoundingMode.UP);
		record.setQtyBook(qtyBook);
		record.setQtyCount(qtyCount);
		
		if(record.get_ID() == 0)
			setCreateInventory(record, getKodeOpname(currentRow), getTanggalTransaksi(currentRow));
		
		record.saveEx();
		return SimpleImportXLS.DONT_SAVE;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable, int)
	 */
	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) {
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
	
	/**
	 * 
	 * @param currentRow
	 * @return
	 */
	private String getKodeOpname(int currentRow)
	{
		Cell cell = m_sheet.getCell(COLUMN_KD_OPNAME + currentRow);
		if(null == cell)
			return null;
		
		String content = cell.getContents();
		if(null == content)
			return null;
		
		return content;
	}
	
	private Timestamp getTanggalTransaksi(int currentRow)
	{
		Cell cell = m_sheet.getCell(COLUMN_TGL_TRANSAKSI + currentRow);
		if(null == cell)
			return null;
		
		String content = cell.getContents();
		if(null == content)
			return null;
		
		content = content.replaceAll("\"", "");
		Timestamp tanggalTransaksi = Timestamp.valueOf(content);
		return tanggalTransaksi;
	}
	
	private String getKodeCabang(int currentRow)
	{
		Cell cell = m_sheet.getCell(COLUMN_KD_CABANG + currentRow);
		if(null == cell)
			return null;
		
		String content = cell.getContents();
		if(null == content)
			return null;
		
		return content;
	}
	
	private String getKodeBarang(int currentRow)
	{
		Cell cell = m_sheet.getCell(COLUMN_KD_BARANG + currentRow);
		if(null == cell)
			return null;
		
		String content = cell.getContents();
		if(null == content)
			return null;
		
		return content;
	}
	
	private BigDecimal getQtyFisik(int currentRow)
	{
		Cell cell = m_sheet.getCell(COLUMN_QTY_FISIK + currentRow);
		if(null == cell)
			return null;
		
		String content = cell.getContents();
		if(null == content)
			return null;
		
		BigDecimal qtyFisik = Env.ZERO;
		try
		{
			qtyFisik = new BigDecimal(content);
		}
		catch (Exception e)
		{
			throw new AdempiereUserError("Can't cast to BigDecimal : " + content);
		}
		
		return qtyFisik;
	}
	
	/**
	 * 
	 * @param currentRow
	 * @return
	 */
	private String getStatusGudang(int currentRow)
	{
		Cell cell = m_sheet.getCell(COLUMN_STATUS_GUDANG + currentRow);
		if(null == cell)
			return null;
		
		String content = cell.getContents();
		if(null == content)
			return null;
		
		return content;
	}
	
	/**
	 * 
	 * @param currentRow
	 * @return
	 */
	private MProduct getProduct(int currentRow)
	{
		String kodeProduk = getKodeBarang(currentRow);
		if(null == kodeProduk)
		{
			if(!Util.isEmpty(m_errorMsg.toString(), true))
				m_errorMsg.append("\n");
			
			m_errorMsg.append("Kode produk tidak diisi pada baris ke " + currentRow);
		}
		
		MProduct product = (MProduct) m_localCache.get(CACHE_PRD.concat(kodeProduk));
		if(null != product)
			return product;
		
		product = MProduct.getBySearchKey(kodeProduk, m_trxName);
		if(null == product)
		{
			throw new AdempiereUserError("Not found product whit value " + kodeProduk + " at row " + currentRow);
		}
		
		m_localCache.put(CACHE_PRD.concat(kodeProduk), product);
		return product;
	}
	
	/**
	 * 
	 * @param currentRow
	 * @return
	 */
	private String getDivisionVal(int currentRow)
	{
		MProduct product = getProduct(currentRow);
		String division = (String) m_localCache.get(CACHE_DIV.concat(product.getValue()));
		if(null != division)
			return division;
		
		X_UNS_Product_Division[] divs = product.getDivisions(false);
		if (null == divs || divs.length == 0)
		{
			throw new AdempiereUserError("Division is not set " + product.getValue());
		}
		
		X_UNS_Product_Division div = divs[0];
		if(null == div || div.get_ID() == 0)
			return null;
		
		MOrg org = (MOrg) m_localCache.get(CACHE_ORG + div.getAD_Org_ID());
		if(null == org)
			org = MOrg.get(m_ctx, div.getDivision_ID());
		
		division = org.getValue();
		division = division.substring(1);
		if(division.equalsIgnoreCase("UDMAS"))
			division = "SR";
		m_localCache.put(CACHE_DIV.concat(product.getValue()), division);
		return division;
	}
	
	
	/**
	 * 
	 * @param currentRow
	 * @return
	 */
	private MLocator getM_Locator(int currentRow)
	{
		String var1 = getStatusGudang(currentRow);
		String var2 = getDivisionVal(currentRow);
		String var3 = getKodeCabang(currentRow);
		String locval = var1.concat("-").concat(var2).concat("-").concat(var3);
		MLocator locator = (MLocator) m_localCache.get(CACHE_LOC.concat(locval));
		if(null != locator)
			return locator;
		
		String sql = "SELECT * FROM M_Locator WHERE UPPER(Value) = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, m_trxName);
			st.setString(1, locval.trim().toUpperCase());
			rs = st.executeQuery();
			if(rs.next())
			{
				locator = new MLocator(m_ctx, rs, m_trxName);
				m_localCache.put(CACHE_LOC . concat(locval), locator);
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
		
		return locator;
	}
	
	/**
	 * Get existing record.
	 * @param M_Product_ID
	 * @param M_Locator_ID
	 * @param M_Warehouse_ID
	 * @param Description
	 * @return
	 */
	private MInventoryLine getExistsRecord(
			int M_Product_ID, int M_Locator_ID, int M_Warehouse_ID, String Description,Timestamp tglOpname)
	{
		String wc = "M_Product_ID = ? AND M_Locator_ID = ?  AND M_Inventory_ID "
				+ "= (SELECT M_Inventory_ID FROM M_Inventory WHERE M_Warehouse_ID "
				+ "= ? AND Description = ? AND DocStatus = 'DR' AND MovementDate = ?)";
		tglOpname = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd 00:00:00")
		.format(tglOpname));
		MInventoryLine record = new Query(m_ctx, MInventoryLine.Table_Name, 
				wc, m_trxName).setParameters(M_Product_ID,M_Locator_ID,
						M_Warehouse_ID,Description, tglOpname).first();
		return record;
	}
	
	/**
	 * 
	 * @param line
	 * @param kodeOpname
	 * @param tglOpname
	 */
	private void setCreateInventory(MInventoryLine line, String kodeOpname, Timestamp tglOpname)
	{
		int M_Warehouse_ID = line.getM_Locator().getM_Warehouse_ID();
		tglOpname = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(tglOpname));
		String key = "" + M_Warehouse_ID +"_" + kodeOpname + tglOpname;
		MInventory parent = m_mapInventory.get(key);
		if(null != parent)
		{
			line.setM_Inventory_ID(parent.get_ID());
			return;
		}
		
		String sql = "SELECT * FROM M_Inventory WHERE M_Warehouse_ID = ? AND Description = ? AND DocStatus = 'DR' AND MovementDate = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, m_trxName);
			st.setInt(1, line.getM_Locator().getM_Warehouse_ID());
			st.setString(2, kodeOpname);
			st.setTimestamp(3, tglOpname);
			rs = st.executeQuery();
			if(rs.next())
			{
				parent = new MInventory(m_ctx, rs, m_trxName);
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
		
		if(null == parent)
		{
			parent = new MInventory((MWarehouse) line.getM_Locator().getM_Warehouse(), m_trxName);
			parent.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_MaterialPhysicalInventory));
			parent.setDescription(kodeOpname);
			parent.setMovementDate(tglOpname);
			parent.saveEx();
		}
		
		m_mapInventory.put(key, parent);
		line.setM_Inventory_ID(parent.get_ID());
	}
}
