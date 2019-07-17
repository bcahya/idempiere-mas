/**
 * 
 */
package com.untacore.model.importer;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import com.unicore.base.model.MMovement;
import com.unicore.base.model.MMovementLine;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MOrg;
import org.compiere.model.PO;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.jfree.util.Log;

import com.unicore.model.X_UNS_Product_Division;
import com.uns.importer.ImporterValidation;
import com.uns.model.MProduct;
import com.uns.model.process.SimpleImportXLS;

/**
 * @author root
 *
 */
public class UNSMovementImporter implements ImporterValidation {
	
	private Sheet m_sheet = null;
	private Properties m_ctx = null;
	private String m_trxName = null;
	private final int DOCUMENT_TYPE = MDocType.getDocType(MDocType.DOCBASETYPE_MaterialMovement);
	private final String KOLOM_KODE_PERPINDAHAN = "A";
	private final String KOLOM_TANGGAL_PERPINDAHAN = "B";
	private final String KOLOM_DARI_CABANG = "C";
	private final String KOLOM_ASAL_GUDANG = "D";
	private final String KOLOM_INISIAL_ASAL_GUDANG = "E";
	private final String KOLOM_KE_CABANG = "F";
	private final String KOLOM_TUJUAN_GUDANG = "G";
	private final String KOLOM_INISIAL_TUJUAN_GUDANG = "H";
	private final String KOLOM_KODE_BARANG = "I";
	private final String KOLOM_KUANTITAS = "J";
	private final String KOLOM_KODE_SATUAN = "K";
	private final String INISIAL_GUDANG_CANVAS = "Canvas";
	private final String INISIAL_GUDANG_CANGKANG = "Cangkang";
	private final String INISIAL_GUDANG_SPPART = "SPPART";
	private Hashtable<Object, Object> CACHE = new Hashtable<>();
	private final String CACHE_PRODUCT = "PRD-";	
	private final String CACHE_DIVISION = "DIV-";
	private final String CACHE_ORGANIZATION = "ORG-";
	private final String CACHE_LOCATOR = "LOC-";
	private final String CACHE_SATUAN = "UOM-";
	private final String CACHE_PERPINDAHAN = "MOV-";
	private Hashtable<String, MovementLineTmp> mapMoveLineTmp = new Hashtable<>();
	
	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
	}
	
	private MMovementLine getExistingRecord(
			int M_Product_ID, int M_LocatorFrom_ID, int M_LocatorTo_ID, int M_WarehouseTo_ID
			, String movementCode, Timestamp movementDate)
	{
		MMovementLine line = null;
		String sql = "SELECT * FROM M_MovementLine WHERE M_Product_ID = ? AND M_Locator_ID = ? "
				+ " AND M_LocatorTo_ID = ? AND M_Movement_ID = (SELECT M_Movement_ID FROM "
				+ " M_Movement WHERE Description = ? AND MovementDate = ? "
				+ " AND DestinationWarehouse_ID = ? AND DocStatus = 'DR')";
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, m_trxName);
			st.setInt(1, M_Product_ID);
			st.setInt(2, M_LocatorFrom_ID);
			st.setInt(3, M_LocatorTo_ID);
			st.setString(4, movementCode);
			st.setTimestamp(5, movementDate);
			st.setInt(6, M_WarehouseTo_ID);
			rs = st.executeQuery();
			if(rs.next())
			{
				line = new MMovementLine(m_ctx, rs, m_trxName);
			}
		}
		catch (SQLException e)
		{
			
		}
		finally
		{
			DB.close(rs, st);
		}
		
		return line;
	}
	
	private String getKodeSatuan(int row)
	{
		Cell cell = m_sheet.getCell(KOLOM_KODE_SATUAN + row);
		if(null == cell)
			return null;
		String content = cell.getContents();
		return content;
	}
	
	private BigDecimal getKuantitas(int row)
	{
		Cell cell = m_sheet.getCell(KOLOM_KUANTITAS + row);
		if(null == cell)
			return null;
		
		String content = cell.getContents();
		if(null == content)
			return null;
		
		BigDecimal kuantitasDipindahkan = Env.ZERO;
		try
		{
			kuantitasDipindahkan = new BigDecimal(content);
		}
		catch (Exception e)
		{
			throw new AdempiereUserError(e.getMessage());
		}
		
		return kuantitasDipindahkan;
	}
	
	private String getKodeBarang(int row)
	{
		Cell cell = m_sheet.getCell(KOLOM_KODE_BARANG + row);
		if(null == cell)
			return null;
		String content = cell.getContents();
		return content;
	}
	
	private String getGudangTujuan(int row)
	{
		Cell cell = m_sheet.getCell(KOLOM_TUJUAN_GUDANG + row);
		if(null == cell)
			return null;
		String content = cell.getContents();
		return content;
	}
	
	private String getInisialGudangTujuan(int row)
	{
		Cell cell = m_sheet.getCell(KOLOM_INISIAL_TUJUAN_GUDANG + row);
		if(null == cell)
			return null;
		String content = cell.getContents();
		return content;
	}
	
	private String getInisialCabangTujuan(int row)
	{
		Cell cell = m_sheet.getCell(KOLOM_KE_CABANG + row);
		if(null == cell)
			return null;
		String content = cell.getContents();
		return content;
	}
	
	private String getGudangAsal(int row)
	{
		Cell cell = m_sheet.getCell(KOLOM_ASAL_GUDANG + row);
		if(null == cell)
			return null;
		String content = cell.getContents();
		return content;
	}
	
	private String getInisialGudangAsal(int row)
	{
		Cell cell = m_sheet.getCell(KOLOM_INISIAL_ASAL_GUDANG + row);
		if(null == cell)
			return null;
		String content = cell.getContents();
		return content;
	}
	
	private String getInisialCabangAsal(int row)
	{
		Cell cell = m_sheet.getCell(KOLOM_DARI_CABANG + row);
		if(null == cell)
			return null;
		String content = cell.getContents();
		return content;
	}
	
	private Timestamp getTanggalPerpindahan(int row)
	{
		Cell cell = m_sheet.getCell(KOLOM_TANGGAL_PERPINDAHAN + row);
		if(null == cell)
			return null;
		
		String content = cell.getContents();
		if(null == content)
			return null;
		
		Timestamp tanggalPerpindahan = Timestamp.valueOf(content);
		return tanggalPerpindahan;
	}
	
	private String getKodePerpindahan(int row)
	{
		Cell cell = m_sheet.getCell(KOLOM_KODE_PERPINDAHAN + row);
		if(null == cell)
			return null;
		String content = cell.getContents();
		return content;
	}
	
	
	/**
	 * 
	 */
	public UNSMovementImporter() {
		super();
	}
	
	public UNSMovementImporter(Properties ctx, Sheet sheet, String trxName)
	{
		m_ctx = ctx; m_sheet = sheet; m_trxName = trxName;
	}
	
	private MProduct getProduct(String value)
	{
		if(null == value)
			throw new AdempiereException("Product value is not defined");
		
		MProduct product = (MProduct) CACHE.get(CACHE_PRODUCT.concat(value));
		if(null != product)
			return product;
		
		product = MProduct.getBySearchKey(value, m_trxName);
		if(null != product)
			CACHE.put(CACHE_PRODUCT.concat(value), product);
		return product;
	}
	
	private String getDivisionVal(MProduct product)
	{
		String division = (String) CACHE.get(CACHE_DIVISION.concat(product.getValue()));
		if(null != division)
			return division;
		
		X_UNS_Product_Division div = product.getDivisions(false)[0];
		if(null == div || div.get_ID() == 0)
			return null;
		
		MOrg org = (MOrg) CACHE.get(CACHE_ORGANIZATION + div.getDivision_ID());
		if(null == org)
			org = MOrg.get(m_ctx, div.getDivision_ID());
		
		division = org.getValue();
		division = division.substring(1);
		if(division.equalsIgnoreCase("UDMAS"))
			division = "SR";
		CACHE.put(CACHE_DIVISION.concat(product.getValue()), division);
		
		return division;
	}
	

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) 
	{
		String kodePerpindahan = getKodePerpindahan(currentRow);
		Timestamp tanggalPerpindahan = getTanggalPerpindahan(currentRow);
		String inisialCabangAsal = getInisialCabangAsal(currentRow);
		String inisialCabangTujuan = getInisialCabangTujuan(currentRow);
		String inisialGudangAsal = getInisialGudangAsal(currentRow);
		String inisialGudangTujuan = getInisialGudangTujuan(currentRow);
		
		String productValue = getKodeBarang(currentRow);
		MProduct product = getProduct(productValue);
		if(null == product)
			return "Not found product with value ".concat(productValue);
		
		String namaLocatorAsal = null;
		String namaLocatorTujuan = null;
		
		if(inisialGudangAsal.equals(INISIAL_GUDANG_CANGKANG))
		{
//			String division = getDivisionVal(product);
//			if(null == division)
//				throw new AdempiereException("Division is not set. " .concat(productValue).concat("-")
//						.concat(product.getName()));
			String namaGudangAsal = getGudangAsal(currentRow).trim();
			namaLocatorAsal = inisialGudangAsal.concat("-").concat("UDMAS").concat("-")
					.concat(namaGudangAsal);
		}
		else if(inisialGudangAsal.equals(INISIAL_GUDANG_CANVAS)
				|| inisialGudangAsal.equals(INISIAL_GUDANG_SPPART))
		{
			String namaGudangAsal = getGudangAsal(currentRow).trim();
			namaLocatorAsal = inisialGudangAsal.concat("-").concat(inisialCabangAsal)
					.concat(Util.isEmpty(namaGudangAsal, true) ? "" : "-").concat(namaGudangAsal);
		}
		else
		{
			String division = getDivisionVal(product);
			if(null == division)
				throw new AdempiereException("Division is not set. " .concat(productValue).concat("-")
						.concat(product.getName()));
			
			namaLocatorAsal = inisialGudangAsal.concat("-").concat(division).concat("-")
					.concat(inisialCabangAsal);
		}
		
		if(inisialGudangTujuan.equals(INISIAL_GUDANG_CANGKANG))
		{
//			String division = getDivisionVal(product);
//			if(null == division)
//				throw new AdempiereException("Division is not set. " .concat(productValue).concat("-")
//						.concat(product.getName()));
			String namaGudangTujuan = getGudangTujuan(currentRow).trim();
			namaLocatorAsal = inisialGudangTujuan.concat("-").concat("UDMAS").concat("-")
					.concat(namaGudangTujuan);
		}
		else if(inisialGudangTujuan.equals(INISIAL_GUDANG_CANVAS)
				|| inisialGudangTujuan.equals(INISIAL_GUDANG_SPPART))
		{
			String namaGudangTujuan = getGudangTujuan(currentRow).trim();
			namaLocatorTujuan = inisialGudangTujuan.concat("-").concat(inisialCabangTujuan)
					.concat(Util.isEmpty(namaGudangTujuan, true) ? "" : "-").concat(namaGudangTujuan);
		}
		else
		{
			String division = getDivisionVal(product);
			if(null == division)
				throw new AdempiereException("Division is not set. " .concat(productValue).concat("-")
						.concat(product.getName()));
			
			namaLocatorTujuan = inisialGudangTujuan.concat("-").concat(division).concat("-")
					.concat(inisialCabangTujuan);
		}
		
		MLocator locatorFrom = getM_Locator(namaLocatorAsal);
		if(null == locatorFrom)
			return "Can't find locator from... " .concat(namaLocatorAsal);
		MLocator locatorTo = getM_Locator(namaLocatorTujuan);
		if(null == locatorTo)
			return "Can't find locator to..." .concat(namaLocatorTujuan);
		
		if(locatorFrom.get_ID() == locatorTo.get_ID())
		{
			Log.info("BARIS TIDAK DI PROSES--> BARIS KE " + currentRow + " LOKATOR ASAL DAN LOKATOR TUJUAN SAMA");
			return SimpleImportXLS.DONT_SAVE;
		}
		
		BigDecimal qty = getKuantitas(currentRow);
		String simbolSatuan = getKodeSatuan(currentRow);
		if(null == simbolSatuan)
			return "Null UOM";
		
		Integer C_UOM_ID = (Integer) CACHE.get(CACHE_SATUAN.concat(simbolSatuan));
		if(null == C_UOM_ID)
		{
			String sql = "SELECT C_UOM_ID FROM C_UOM WHERE UPPER(UOMSymbol) = UPPER(TRIM(?))";
			C_UOM_ID = DB.getSQLValue(m_trxName, sql, simbolSatuan);
			if(C_UOM_ID <= 0)
				return "Unknown symbol of UOM " + simbolSatuan;
			
			CACHE.put(CACHE_SATUAN.concat(simbolSatuan), C_UOM_ID);
		}
		
		if(C_UOM_ID != product.getC_UOM_ID())
			qty = product.convertTo(C_UOM_ID, product.getC_UOM_ID(), qty);
		
		if(qty.signum() == 0)
			return SimpleImportXLS.DONT_SAVE;
		
		String key = product.get_ID() + "-" + locatorFrom.get_ID() + "-" + locatorTo.get_ID() + "-" 
				+ locatorTo.getM_Warehouse_ID() + "-" + kodePerpindahan + "-" + tanggalPerpindahan;
		MovementLineTmp tmpModel = mapMoveLineTmp.get(key);
		if(null == tmpModel)
		{
			tmpModel = new MovementLineTmp(
					locatorFrom, locatorTo, product, Env.ZERO, kodePerpindahan, tanggalPerpindahan);
			mapMoveLineTmp.put(key, tmpModel);			
		}
		
		tmpModel.theQty = tmpModel.theQty.add(qty);
		
		return SimpleImportXLS.DONT_SAVE;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
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
		
		for(MovementLineTmp tmp : mapMoveLineTmp.values())
		{
			MMovementLine line = getExistingRecord(
					tmp.theProduct.get_ID(), tmp.theLocatorFrom.get_ID(), tmp.theLocatorTo.get_ID()
					, tmp.theLocatorTo.getM_Warehouse_ID(), tmp.theMoveCode, tmp.theMoveDate);
			if(null == line)
			{
				line = new MMovementLine(m_ctx, 0, m_trxName);
				line.setAD_Org_ID(tmp.theLocatorFrom.getAD_Org_ID());
				line.setDescription("::Auto Generate From Import");
				line.setM_Locator_ID(tmp.theLocatorFrom.get_ID());
				line.setM_LocatorTo_ID(tmp.theLocatorTo.get_ID());
				line.setM_Product_ID(tmp.theProduct.get_ID());
			}
			
			line.setMovementQty(tmp.theQty);
			if(line.getM_Movement_ID() == 0)
				setCreateParent(line, tmp.theMoveCode, tmp.theMoveDate);
			try
			{
				line.saveEx();
			}
			catch (Exception e)
			{
				throw new AdempiereException("Error while trying to save movement line " + e.getMessage());
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#getPOReferenceMap()
	 */
	@Override
	public Hashtable<String, PO> getPOReferenceMap() {
		return null;
	}
	
	private MLocator getM_Locator(String value)
	{
		MLocator locator = (MLocator) CACHE.get(CACHE_LOCATOR.concat(value));
		if(null != locator)
			return locator;
		
		String sql = "SELECT * FROM M_Locator WHERE UPPER(Value)= ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, m_trxName);
			st.setString(1, value.trim().toUpperCase());
			rs = st.executeQuery();
			if(rs.next())
			{
				locator = new MLocator(m_ctx, rs, m_trxName);
				CACHE.put(CACHE_LOCATOR, value);
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException("Error while trying to get Locator :: "
					.concat(e.getMessage()));
		}
		finally
		{
			DB.close(rs, st);
		}
		
		return locator;
	}
	
	
	private void setCreateParent(MMovementLine child, String kodePerpindahan, Timestamp tanggalPerpindahan)
	{
		String key = CACHE_PERPINDAHAN + kodePerpindahan + "-"+ tanggalPerpindahan 
				+ "-" + child.getM_LocatorTo().getM_Warehouse_ID();
		MMovement move = (MMovement) CACHE.get(key);
		if(null != move)
		{
			child.setM_Movement_ID(move.get_ID());
			return;
		}
		
		String sql = "SELECT * FROM M_Movement WHERE Description = ? AND MovementDate = ? AND DestinationWarehouse_ID = ? AND DocStatus = 'DR'";
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, m_trxName);
			st.setString(1, kodePerpindahan);
			st.setTimestamp(2, tanggalPerpindahan);
			st.setInt(3, child.getM_LocatorTo().getM_Warehouse_ID());
			rs = st.executeQuery();
			if(rs.next())
			{
				move = new MMovement(m_ctx, rs, m_trxName);
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, st);
		}
		
		if(null == move)
		{
			move = new MMovement(m_ctx, 0, m_trxName);			
			move.setAD_Org_ID(child.getAD_Org_ID());
			move.setAD_OrgTrx_ID(child.getAD_Org_ID());
			move.setC_DocType_ID(DOCUMENT_TYPE);
			move.setDestinationWarehouse_ID(child.getM_LocatorTo().getM_Warehouse_ID());
			move.setDescription(kodePerpindahan);
			move.setIsInternalWarehouseConfirm(false);
			move.setMovementDate(tanggalPerpindahan);
			move.setIsInTransit(false);
			move.saveEx();	
		}
		
		child.setM_Movement_ID(move.get_ID());
		CACHE.put(key, move);
	}
}

class MovementLineTmp
{
	public MovementLineTmp(MLocator locFrom, MLocator locTo, MProduct product, BigDecimal qty, String  moveCode, Timestamp moveDate)
	{
		theLocatorFrom = locFrom;
		theLocatorTo = locTo;
		theProduct = product;
		theQty = qty;
		theOrg = theLocatorFrom.getAD_Org_ID();
		theMoveCode = moveCode;
		theMoveDate = moveDate;
	}
	
	MLocator theLocatorFrom = null;
	MLocator theLocatorTo = null;
	MProduct theProduct = null;
	BigDecimal theQty = Env.ZERO;
	int theOrg = 0;
	String theMoveCode = null;
	Timestamp theMoveDate = null;
}