/**
 * 
 */
package com.uns.model.validator;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import com.unicore.base.model.MBankStatementLine;
import com.uns.model.process.SimpleImportXLS;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankStatement;
import org.compiere.model.PO;
import org.compiere.util.Env;

import com.uns.importer.ImporterValidation;

/**
 * @author menjangan
 *
 */
public class ImportBankStatementValidation implements ImporterValidation {

	private Properties m_Ctx = null;
	private String m_TrxName = null;
	private Sheet m_activeShett = null;
	private MBankStatement m_model = null;
	private final String BCA_SHEET = "BCA";
	private final String BRI_SHEET = "BRI";
	private final String PANIN_SHEET = "PANIN";
	private final String MANDIRI_SHEET = "MANDIRI";
	private final String RABO_SHEET = "RABO";
	private final String PERMATA_SHEET = "PERMATA";
	/**
	 * 
	 */
	public ImportBankStatementValidation() {
		super();
	}
	
	/**
	 * 
	 * @param ctx
	 * @param sheet
	 * @param trxName
	 */
	public ImportBankStatementValidation(Properties ctx, Sheet sheet, String trxName){
		this.m_Ctx = ctx;
		this.m_TrxName = trxName;
		this.m_activeShett = sheet;
	}

	@Override
	public void setTrxName(String trxName) {
		m_TrxName = trxName;
	}
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{
		int totalRow = (Integer) m_activeShett.getRows();
		System.out.println("Import Bank Statment Validation :: Max Row : " + totalRow);
		System.out.println("Import Bank Statment Validation :: Current Row :" + currentRow);
		
		if(currentRow > m_activeShett.getRows())
			return SimpleImportXLS.DONT_SAVE;
		
		MBankStatementLine stm = (MBankStatementLine) po;
		if(null == m_model)
		{
			int C_BankStatement_ID = Env.getContextAsInt(m_Ctx, MBankStatement.COLUMNNAME_C_BankStatement_ID);
			if(C_BankStatement_ID <= 0)
				throw new AdempiereException("Tidak bisa mengambil data Bank Statement pada Konteks.");
			 m_model = new MBankStatement(m_Ctx, C_BankStatement_ID, m_TrxName);
		}
		
		String sheetName = m_activeShett.getName();
		String msg = null;
		
		if(sheetName.equals(BCA_SHEET))
			msg = beforeSaveBCA(poRefMap, po, freeColVals,currentRow);
		else if(sheetName.equals(BRI_SHEET))
			msg = beforeSaveBRI(poRefMap, po, freeColVals,currentRow);
		else if(sheetName.equals(MANDIRI_SHEET))
			msg = beforeSaveMandiri(poRefMap, po, freeColVals, currentRow);
		else if(sheetName.equals(PANIN_SHEET))
			msg = beforeSavePanin(poRefMap, po, freeColVals, currentRow);
		else if(sheetName.equals(PERMATA_SHEET))
			msg = beforeSavePermata(poRefMap, po, freeColVals, currentRow);
		else if(sheetName.equals(RABO_SHEET))
			msg = beforeSaveRabo(poRefMap, po, freeColVals, currentRow);
		else 
			throw new AdempiereException("Validasi untuk importer Bank " + sheetName + " belum ada!!!" );
		
		poRefMap.put("" + stm.get_ID(), po);
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO)
	 */
	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) {
		poRefMap.put("" + po.get_ID(), po);
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
		return null;
	}
	
	/**
	 * 
	 * @param poRefMap
	 * @param po
	 * @param freeColVals
	 * @return
	 */
	public String beforeSaveBRI(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{		
		MBankStatementLine stm = (MBankStatementLine) po;
		stm.m_IsOnImport = true;
		stm.setAmount(Env.ZERO);
		stm.setAD_Org_ID(m_model.getAD_Org_ID());
		stm.setC_BankStatement_ID(m_model.get_ID());
		stm.setC_Currency_ID(m_model.getC_BankAccount().getC_Currency_ID());
		
		BigDecimal amount = Env.ZERO;
		
		Cell cellContent = m_activeShett.getCell("C" + currentRow); // find by cell name.
		if (cellContent == null || "".equals(cellContent))
			cellContent = m_activeShett.getCell(4, currentRow);
		
		String cellValue = cellContent.getContents();
		if(cellValue == null || "".equals(cellValue))
			cellValue = "0";
		amount = new BigDecimal(cellValue);
		if(amount.signum() > 0)
			amount = amount.negate();
		
		if(amount.signum() == 0)
		{
			cellContent = m_activeShett.getCell("D" + currentRow); // find by cell name.
			if (cellContent == null || "".equals(cellContent))
				cellContent = m_activeShett.getCell(5, currentRow);
			
			cellValue = cellContent.getContents();
			if(cellValue == null || "".equals(cellValue))
				cellValue = "0";
			amount = new BigDecimal(cellValue);
		}
		stm.setStmtAmt(amount);
		stm.setAmount(amount.signum() > 0 ? amount : amount.negate());
		
		if(amount.signum() > 0)
			stm.setTransactionType(MBankStatementLine.TRANSACTIONTYPE_ARTransaction);
		else
			stm.setTransactionType(MBankStatementLine.TRANSACTIONTYPE_APTransaction);
		
		return null;
	}

	/**
	 * 
	 * @param poRefMap
	 * @param po
	 * @param freeColVals
	 * @return
	 */
	public String beforeSaveBCA(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{
		return "Belum di buat importer untuk bank BCA !!!";
	}

	/**
	 * 
	 * @param poRefMap
	 * @param po
	 * @param freeColVals
	 * @return
	 */
	public String beforeSavePanin(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{
		MBankStatementLine stm = (MBankStatementLine) po;
		stm.m_IsOnImport = true;
		stm.setAmount(Env.ZERO);
		stm.setAD_Org_ID(m_model.getAD_Org_ID());
		stm.setC_BankStatement_ID(m_model.get_ID());
		stm.setC_Currency_ID(m_model.getC_BankAccount().getC_Currency_ID());
		
		BigDecimal amount = Env.ZERO;
		
		Cell cellContent = m_activeShett.getCell("D" + currentRow); // find by cell name.
		if (cellContent == null || "".equals(cellContent))
			cellContent = m_activeShett.getCell(4, currentRow);
		
		String cellValue = cellContent.getContents();
		if(cellValue == null || "".equals(cellValue))
			cellValue = "0";
		amount = new BigDecimal(cellValue);
		if(amount.signum() > 0)
			amount = amount.negate();
		
		if(amount.signum() == 0)
		{
			cellContent = m_activeShett.getCell("E" + currentRow); // find by cell name.
			if (cellContent == null || "".equals(cellContent))
				cellContent = m_activeShett.getCell(5, currentRow);
			
			cellValue = cellContent.getContents();
			if(cellValue == null || "".equals(cellValue))
				cellValue = "0";
			amount = new BigDecimal(cellValue);
		}
		stm.setStmtAmt(amount);
		stm.setAmount(amount.signum() > 0 ? amount : amount.negate());
		
		if(amount.signum() > 0)
			stm.setTransactionType(MBankStatementLine.TRANSACTIONTYPE_ARTransaction);
		else
			stm.setTransactionType(MBankStatementLine.TRANSACTIONTYPE_APTransaction);
		
		return null;
	}

	/**
	 * 
	 * @param poRefMap
	 * @param po
	 * @param freeColVals
	 * @return
	 */
	public String beforeSaveMandiri(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow)
	{
		MBankStatementLine stm = (MBankStatementLine) po;
		stm.m_IsOnImport = true;
		stm.setAmount(Env.ZERO);
		stm.setAD_Org_ID(m_model.getAD_Org_ID());
		stm.setC_BankStatement_ID(m_model.get_ID());
		stm.setC_Currency_ID(m_model.getC_BankAccount().getC_Currency_ID());
		
		BigDecimal amount = Env.ZERO;
		Cell cellContent = m_activeShett.getCell("D" + currentRow); // find by cell name.
		if (cellContent == null || "".equals(cellContent))
			cellContent = m_activeShett.getCell(4, currentRow);
		
		String cellValue = cellContent.getContents();
		if(cellValue == null || "".equals(cellValue))
			cellValue = "0";
		amount = new BigDecimal(cellValue);
		if(amount.signum() > 0)
			amount = amount.negate();
		
		if(amount.signum() == 0)
		{
			cellContent = m_activeShett.getCell("E" + currentRow); // find by cell name.
			if (cellContent == null || "".equals(cellContent))
				cellContent = m_activeShett.getCell(5, currentRow);
			
			cellValue = cellContent.getContents();
			if(cellValue == null || "".equals(cellValue))
				cellValue = "0";
			amount = new BigDecimal(cellValue);
		}
		stm.setStmtAmt(amount);
		stm.setAmount(amount.signum() > 0 ? amount : amount.negate());
		
		if(amount.signum() > 0)
			stm.setTransactionType(MBankStatementLine.TRANSACTIONTYPE_ARTransaction);
		else
			stm.setTransactionType(MBankStatementLine.TRANSACTIONTYPE_APTransaction);
		
		return null;
	}

	/**
	 * 
	 * @param poRefMap
	 * @param po
	 * @param freeColVals
	 * @return
	 */
	public String beforeSavePermata(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{	
		return beforeSaveMandiri(poRefMap, po, freeColVals, currentRow);
	}

	/**
	 * 
	 * @param poRefMap
	 * @param po
	 * @param freeColVals
	 * @return
	 */
	public String beforeSaveRabo(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow)
	{
		return beforeSavePermata(poRefMap, po, freeColVals, currentRow);
	}		
}
