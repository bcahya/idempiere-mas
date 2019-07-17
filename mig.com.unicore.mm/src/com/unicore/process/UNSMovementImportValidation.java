/**
 * 
 */
package com.unicore.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Properties;
import jxl.Sheet;
import com.unicore.base.model.MMovement;
import com.unicore.base.model.MMovementLine;

import org.compiere.model.MDocType;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.importer.ImporterValidation;
import com.uns.model.MProduct;
import com.uns.model.process.SimpleImportXLS;

/**
 * @author root
 *
 */
public class UNSMovementImportValidation implements ImporterValidation {
	
	private Sheet m_sheet = null;
	private Properties m_ctx = null;
	private String m_trxName = null;
	private int m_C_DocType_ID = 0;
	
	//document no
	private final String m_kolomKodeTransfer = "A";
	//tanggal movement
	private final String m_kolomTanggalMindahin = "B";
	//organisasi 
	private final String m_kolomOrganisasi = "K";
	//movement qty
	private final String m_kolomJumlahBarang = "H";
	//uom
	private final String m_kolomSatuanBarang = "I";
	//value of product
	private final String m_kolomKodeProduk = "G";

	/**
	 * 
	 */
	public UNSMovementImportValidation() {
		super();
	}
	
	public UNSMovementImportValidation(Properties ctx, Sheet sheet, String trxName)
	{
		m_ctx = ctx;
		m_sheet = sheet;
		m_trxName = trxName;
		
		m_C_DocType_ID = MDocType.getDocType(MDocType.DOCBASETYPE_MaterialMovement);
	}
	

	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
	}
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) 
	{
		MMovementLine model = (MMovementLine) po;
		if(model.getM_Product_ID() <= 0)
			return SimpleImportXLS.DONT_SAVE;
		else if(model.getM_Locator_ID() <= 0)
			return SimpleImportXLS.DONT_SAVE;
		else if(model.getM_LocatorTo_ID() <=0)
			return SimpleImportXLS.DONT_SAVE;
		
		String productKey = m_sheet.getCell(m_kolomKodeProduk + currentRow).getContents();
		MProduct product = (MProduct) m_cache.get(productKey);
		if(null == product)
		{
			product = new MProduct(m_ctx, model.getM_Product_ID(), m_trxName);
			m_cache.put(productKey, product);
		}
		
		BigDecimal quantity = new BigDecimal(m_sheet.getCell(m_kolomJumlahBarang + currentRow).getContents());
		
		String simbolSatuan = m_sheet.getCell(m_kolomSatuanBarang + currentRow).getContents();
		if(null == simbolSatuan)
			return "Null UOM";
		
		Integer C_UOM_ID = (Integer) m_cache.get(simbolSatuan);
		if(null == C_UOM_ID)
		{
			String sql = "SELECT C_UOM_ID FROM C_UOM WHERE UPPER(UOMSymbol) = UPPER(TRIM(?))";
			C_UOM_ID = DB.getSQLValue(m_trxName, sql, simbolSatuan);
			if(C_UOM_ID <= 0)
				return "Unknown symbol of UOM " + simbolSatuan;
			
			m_cache.put(simbolSatuan, C_UOM_ID);
		}
		
		if(C_UOM_ID == product.getUOMConversionL1_ID())
			model.set_ValueOfColumn("UOMMovementQtyL1", quantity);
		else if(C_UOM_ID == product.getUOMConversionL2_ID())
			model.set_ValueOfColumn("UOMMovementQtyL2", quantity);
		else if(C_UOM_ID == product.getUOMConversionL3_ID())
			model.set_ValueOfColumn("UOMMovementQtyL3", quantity);
		else if(C_UOM_ID == product.getUOMConversionL4_ID())
			model.set_ValueOfColumn("UOMMovementQtyL4", quantity);
		else
			return "Can't Match UOM";
		
		if(model.getMovementQty().signum() == 0)
			model.setMovementQty(Env.ONE);
		
		if(model.getM_Locator_ID() == model.getM_LocatorTo_ID()
				&& model.getM_AttributeSetInstance_ID() == model.getM_AttributeSetInstance_ID())
			return SimpleImportXLS.DONT_SAVE;
		
		String parentDocumentNo = m_sheet.getCell(m_kolomKodeTransfer + currentRow).getContents();
		MMovement parent = getParentModel(parentDocumentNo, currentRow);
		model.setM_Movement_ID(parent.get_ID());
		
		
		return null;
	}
	

	private MMovement getParentModel(String keyReference, int currentRow)
	{
		Object oo = m_cache.get(keyReference);
		if(oo != null && oo instanceof MMovement == true)
			return (MMovement) oo;
		
		MMovement parent =  null;
		String sql = "SELECT * FROM M_Movement WHERE UPPER(DocumentNo) = UPPER(TRIM(?))";
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		try {
			stm = DB.prepareStatement(sql, m_trxName);
			stm.setString(1, keyReference);
			rs = stm.executeQuery();
			if(rs.next()) {
				parent = new MMovement(m_ctx, rs, m_trxName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(null == parent)
			parent = createParent(keyReference, currentRow);
		
		m_cache.put(keyReference, parent);
		return parent;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
	 */
	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveAllRow(java.util.Hashtable, org.compiere.model.PO[])
	 */
	@Override
	public String afterSaveAllRow(Hashtable<String, PO> poRefMap, PO[] pos) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#getPOReferenceMap()
	 */
	@Override
	public Hashtable<String, PO> getPOReferenceMap() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Hashtable<Object, Object> m_cache = new Hashtable<>();
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private MMovement createParent(String DocumentNo, int currentRow)
	{
		MMovement move = new MMovement(m_ctx, 0, m_trxName);
		String value = m_sheet.getCell(m_kolomOrganisasi + currentRow).getContents();
		Integer AD_Org_ID = (Integer) m_cache.get(value);
		
		if(null == AD_Org_ID)
		{
			String sql = "SELECT AD_Org_ID FROM AD_Org WHERE UPPER(Value) = UPPER(TRIM(?))";
			AD_Org_ID = DB.getSQLValue(m_trxName, sql, value);
		}
		
		value = m_sheet.getCell(m_kolomTanggalMindahin + currentRow).getContents();
		Timestamp tanggalMindahin = Timestamp.valueOf(value);
		
		move.setAD_Org_ID(AD_Org_ID);
		move.setAD_OrgTrx_ID(AD_Org_ID);
		move.setC_DocType_ID(m_C_DocType_ID);
		move.setDocumentNo(m_sheet.getCell(m_kolomKodeTransfer + currentRow).getContents());
//		move.setDateReceived(tanggalMindahin);
		move.setDescription(":: Auto Generate Fom Import ::");
		move.setIsInternalWarehouseConfirm(false);
		move.setMovementDate(tanggalMindahin);
		move.setIsInTransit(false);
		move.saveEx();	
		
		return move;
	}
}
