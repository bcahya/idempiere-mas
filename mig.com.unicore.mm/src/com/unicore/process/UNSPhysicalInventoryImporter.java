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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
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
public class UNSPhysicalInventoryImporter implements ImporterValidation {

	private Properties m_Ctx = null;
	private Sheet m_Sheet = null;
	private String m_trxNme = null;
	private Hashtable<Object, Object> m_cache = new Hashtable<>();
	private int m_AD_Org_ID = 0;
	private final String m_columnTransactionCode = "B";
	private final String m_columnPhysicalDate = "D";
	private final String m_columnQtyL1 = "F";
	private final String m_columnUOML1 = "G";
	private final String m_columnQtyL2 = "H";
	private final String m_columnUOML2 = "I";
	private final String m_columnQtyL3 = "J";
	private final String m_columnUOML3 = "K";
	private final String m_columnQtyL4 = "L";
	private final String m_columnUOML4 = "M";
	private final String m_columnLocator = "C";
	private int m_C_DocType_ID = 0;
	public UNSPhysicalInventoryImporter(Properties ctx, Sheet sheet, String trxName)
	{
		m_Ctx = ctx;
		m_Sheet = sheet;
		m_trxNme = trxName;
		m_C_DocType_ID = MDocType.getDocType(MDocType.DOCBASETYPE_MaterialPhysicalInventory);
	}
	/**
	 * 
	 */
	public UNSPhysicalInventoryImporter() {
		super();
	}

	@Override
	public void setTrxName(String trxName) {
		m_trxNme = trxName;
	}
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable, int)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) 
	{
		MInventoryLine model = (MInventoryLine) po;
		if(model.getM_Product_ID() <= 0)
			return SimpleImportXLS.DONT_SAVE;
		else if(model.getM_Locator_ID() <= 0)
			return SimpleImportXLS.DONT_SAVE;
		else if(model.getAD_Org_ID() <= 0)
			return SimpleImportXLS.DONT_SAVE;
		
		m_AD_Org_ID = model.getAD_Org_ID();
		MProduct product =(MProduct) m_cache.get(model.getM_Product_ID());
		if(null == product)
		{
			product = new MProduct(m_Ctx, model.getM_Product_ID(), m_trxNme);
			m_cache.put(model.getM_Product_ID(), product);
		}
		
		String oo1 = m_Sheet.getCell(m_columnQtyL1 + currentRow).getContents();
		String oo2 = m_Sheet.getCell(m_columnQtyL2 + currentRow).getContents();
		String oo3 = m_Sheet.getCell(m_columnQtyL3 + currentRow).getContents();
		String oo4 = m_Sheet.getCell(m_columnQtyL4 + currentRow).getContents();
		
		BigDecimal qty1 = oo1 == null ? Env.ZERO : new BigDecimal(oo1);
		BigDecimal qty2 = oo2 == null ? Env.ZERO : new BigDecimal(oo2);
		BigDecimal qty3 = oo3 == null ? Env.ZERO : new BigDecimal(oo3);
		BigDecimal qty4 = oo4 == null ? Env.ZERO : new BigDecimal(oo4);
		
		oo1 = m_Sheet.getCell(m_columnUOML1 + currentRow).getContents();
		oo2 = m_Sheet.getCell(m_columnUOML2 + currentRow).getContents();
		oo3 = m_Sheet.getCell(m_columnUOML3 + currentRow).getContents();
		oo4 = m_Sheet.getCell(m_columnUOML4 + currentRow).getContents();
		
		Integer UOML1 = initialUOM(oo1);
		Integer UOML2 = initialUOM(oo2);
		Integer UOML3 = initialUOM(oo3);
		Integer UOML4 = initialUOM(oo4);
		
		if(UOML1 > 0)
		{
			if(!initialQty(product, model, UOML1, qty1))
				return "Can't match UOM " + oo1;
		}
		if(UOML2 > 0)
		{
			if(!initialQty(product, model, UOML2, qty2))
				return "Can't match UOM " + oo2;
		}
		if(UOML3 > 0)
		{
			if(!initialQty(product, model, UOML3, qty3))
				return "Can't match UOM " + oo3;
		}
		if(UOML4 > 0)
		{
			if(!initialQty(product, model, UOML4, qty4))
				return "Can't match UOM " + oo4;
		}
		
		String parentReference = m_Sheet.getCell(m_columnTransactionCode + currentRow).getContents();
		MInventory parent = initialParent(parentReference, currentRow);
		model.setM_Inventory_ID(parent.get_ID());
		
		return null;
	}
	
	/**
	 * 
	 * @param reference
	 * @param currentRow
	 * @return
	 */
	private MInventory initialParent(String reference, int currentRow)
	{
		MInventory parent = (MInventory) m_cache.get(reference);
		if(null != parent)
			return parent;
		
		String sql = "SELECT * FROM M_Inventory WHERE DocumentNo = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = DB.prepareStatement(sql, m_trxNme);
			st.setString(1, reference);
			rs = st.executeQuery();
			if(rs.next()) {
				parent = new MInventory(m_Ctx, rs, m_trxNme);
				m_cache.put(reference, parent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(null != parent)
			return parent;
		
		String locCode = m_Sheet.getCell(m_columnLocator + currentRow).getContents();
		Integer whs_id = (Integer) m_cache.get(locCode);
		
		if(null == whs_id)
		{
			sql = "SELECT M_Warehouse_ID FROM M_Locator WHERE UPPER(Value) = UPPER(TRIM(?))";
			whs_id = DB.getSQLValue(m_trxNme, sql, locCode);
			m_cache.put(locCode, whs_id);
		}
		
		String dateString = m_Sheet.getCell(m_columnPhysicalDate + currentRow).getContents();
		Timestamp physicalDate = Timestamp.valueOf(dateString);
		
		parent = new MInventory(m_Ctx, 0, m_trxNme);
		parent.setAD_Org_ID(m_AD_Org_ID);
		parent.setAD_OrgTrx_ID(m_AD_Org_ID);
		parent.setC_DocType_ID(m_C_DocType_ID);
		parent.setDescription(" :: Auto generate from import :: ");
		parent.setDocumentNo(reference);
		parent.setM_Warehouse_ID(whs_id);
		parent.setMovementDate(physicalDate);
		parent.saveEx();
		
		return parent;
	}
	
	/**
	 * 
	 * @param product
	 * @param model
	 * @param C_UOM_ID
	 * @param qty
	 * @return
	 */
	private boolean initialQty(MProduct product,MInventoryLine model, int C_UOM_ID, BigDecimal qty)
	{
		if(product.getUOMConversionL1_ID() == C_UOM_ID)
			model.set_ValueOfColumn("UOMQtyCountL1", qty);
		else if(product.getUOMConversionL2_ID() == C_UOM_ID)
			model.set_ValueOfColumn("UOMQtyCountL2", qty);
		else if(product.getUOMConversionL3_ID() == C_UOM_ID)
			model.set_ValueOfColumn("UOMQtyCountL3", qty);
		else if(product.getUOMConversionL4_ID() == C_UOM_ID)
			model.set_ValueOfColumn("UOMQtyCountL4", qty);
		else return false;
		
		return true;
	}
	
	/**
	 * 
	 * @param UOMSymbol
	 * @return
	 */
	private int initialUOM(String UOMSymbol)
	{
		if(null == UOMSymbol || UOMSymbol.trim().isEmpty())
			return 0;
		
		Integer C_UOM_ID = (Integer) m_cache.get(UOMSymbol);
		if(C_UOM_ID != null && C_UOM_ID >  0)
			return C_UOM_ID;
		
		String sql = "SELECT C_UOM_ID FROM C_UOM WHERE UPPER(UOMSymbol) = UPPER(TRIM(?))";
		C_UOM_ID = DB.getSQLValue(m_trxNme, sql, UOMSymbol);
		
		if(C_UOM_ID <= 0)
			throw new AdempiereException("Unknown UOM" + UOMSymbol);
		
		m_cache.put(UOMSymbol, C_UOM_ID);
		return C_UOM_ID;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable, int)
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

}
