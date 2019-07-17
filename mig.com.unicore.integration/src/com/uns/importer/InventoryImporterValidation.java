/**
 * 
 */
package com.uns.importer;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

import jxl.Sheet;

import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.PO;
import org.compiere.util.Env;

import com.uns.model.MProduct;

/**
 * @author Haryadi
 *
 */
public class InventoryImporterValidation implements ImporterValidation {

	protected Properties 	m_ctx = null;
	protected String		m_trxName = null;
	protected Sheet			m_sheet	= null;
	protected Hashtable<String, PO> m_PORefMap = null;
	protected int m_recordCount = 0;
	
	static final String COL_QtyCount = "QtyCount";
	
	/**
	 * 
	 */
	public InventoryImporterValidation(Properties ctx, Sheet sheet, String trxName) {
		m_ctx = ctx;
		m_trxName = trxName;
		m_sheet = sheet;
	}

	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
	}
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{
		MInventoryLine invLine = (MInventoryLine) po;
		invLine.setQtyBook(Env.ZERO);
		invLine.setInventoryType(MInventoryLine.INVENTORYTYPE_InventoryDifference);
		invLine.setDescription("**Auto generated (Initial Import)**");
		
		BigDecimal lowestQty = (BigDecimal) freeColVals.get(COL_QtyCount);
		BigDecimal highestQty = getQty(invLine, lowestQty);
		invLine.setQtyCount(highestQty);
		
		if (invLine.getM_AttributeSetInstance_ID() == 0)
			MAttributeSetInstance.initAttributeValuesFrom(
					invLine, 
					MInventoryLine.COLUMNNAME_M_Product_ID, 
					MInventoryLine.COLUMNNAME_M_AttributeSetInstance_ID, 
					m_trxName);	
		if (invLine.getM_AttributeSetInstance_ID() == 0)
			return "Error: Cannot initialize ASI for product of " + invLine.getM_Product() + "; row: " + currentRow;
			
		return null;
	}
	
	/**
	 * Get the quantity of the invoiceLine.
	 * @param invLine
	 * @return
	 */
	private BigDecimal getQty(MInventoryLine invLine, BigDecimal lowestQty)
	{
		//BigDecimal qtyOfLowestUoM = (BigDecimal) m_freeColVals.get(COL_QTY);
		BigDecimal qty = lowestQty;
		
		MProduct product = MProduct.get(m_ctx, invLine.getM_Product_ID());
		
		BigDecimal lowestToBaseConvQty = product.getConversionQtyLowestToBase();
		
		qty = lowestQty.divide(lowestToBaseConvQty, 5, BigDecimal.ROUND_DOWN);
		
		return qty;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO)
	 *
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals) 
	{
		
		MInventoryLine invLine = (MInventoryLine)po;
		if(m_recordCount == 0)
			m_recordCount = 2;
		
		if(invLine.getQtyCount().signum() != 1)
		{
			m_recordCount++;
			return SimpleImportXLS.DONT_SAVE;
		}
		
		if(invLine.getM_Product_ID() <= 0)
		{
			Cell cel = m_sheet.getCell("A"+ m_recordCount);
			String cellVal = cel.getContents();
			
			throw new AdempiereUserError("Product Not Foudn, product : " + cellVal);
		}
		if(invLine.getM_Locator_ID() <= 0)
		{
			Cell cel = m_sheet.getCell("G"+ m_recordCount);
			String cellVal = cel.getContents();
			throw new AdempiereUserError("Locator not found, locator " + cellVal);
		}

		int AD_Org_ID = invLine.getM_Locator().getAD_Org_ID();
		invLine.setAD_Org_ID(AD_Org_ID);
		
		MInventory invRef = getSetPORefMap(poRefMap, po);
		invLine.setM_Inventory_ID(invRef.get_ID());
		invLine.setQtyBook(Env.ZERO);
		invLine.setInventoryType(MInventoryLine.INVENTORYTYPE_InventoryDifference);
//		invLine.setQAStatus(MInventoryLine.QASTATUS_Release);
		invLine.setDescription("**Auto generated while executing the import process**");
		
		MAttributeSetInstance.initAttributeValuesFrom(
				invLine, 
				MInventoryLine.COLUMNNAME_M_Product_ID, 
				MInventoryLine.COLUMNNAME_M_AttributeSetInstance_ID, 
				m_trxName);	
		
		m_recordCount++;
		return null;
	}
	*/

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO)
	 */
	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals,int currentRow) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param poRefMap
	 * @param po
	 * @return
	 */
	MInventory getSetPORefMap(Hashtable<String, PO> poRefMap, PO po) 
	{
		
		if (poRefMap == null) {
			poRefMap = new Hashtable<String, PO>();
		}
		m_PORefMap = poRefMap;

		MInventoryLine invLine = (MInventoryLine) po;
		String key = new StringBuilder("").append(invLine.getM_Locator().getM_Warehouse_ID()).toString();
		MInventory poRef = (MInventory) m_PORefMap.get(key);
		
		if (poRef == null)
		{			
			int invID = 0;
			if (invLine.getM_Inventory_ID() > 0)
				invID = invLine.getM_Inventory_ID();
			
			poRef = new MInventory(m_ctx, invID, m_trxName);
			poRef.setAD_Org_ID(po.getAD_Org_ID());
			poRef.setM_Warehouse_ID(invLine.getM_Locator().getM_Warehouse_ID());
			poRef.setMovementDate(new Timestamp(new Date().getTime()));
			poRef.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_MaterialPhysicalInventoryImport));
			poRef.setDescription("**Auto generated document while running the import process.**");
			poRef.saveEx();
			
			m_PORefMap.put(key, poRef);
		}
		//po.set_ValueOfColumn(MInventoryLine.COLUMNNAME_M_Inventory_ID, poRef.get_ID());
		
		return poRef;
	}
}
