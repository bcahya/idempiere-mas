/**
 * 
 */
package com.unicore.importer;

import java.util.Hashtable;
import java.util.Properties;

import jxl.Sheet;
import com.uns.model.process.SimpleImportXLS;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MBank;
import org.compiere.model.MBankAccount;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.importer.ImporterValidation;

/**
 * @author root
 *
 */
public class UNSBankCashImporter implements ImporterValidation {
	
	private Properties m_Ctx = null;
	private Sheet m_Sheet = null;
	private String m_trxName = null;
	private String m_columnBankCash = "B";
	private String m_columnRoutingNo = "C";
	private int m_C_Currency_ID = 0;
	private Hashtable<Object, Object> m_cache = new Hashtable<>();

	
	public UNSBankCashImporter(Properties ctx, Sheet sheet, String trxName)
	{
		m_Ctx = ctx;
		m_Sheet = sheet;
		m_trxName = trxName;
		
		MAcctSchema[] schema = MAcctSchema.getClientAcctSchema(ctx, Env.getAD_Client_ID(ctx));
		m_C_Currency_ID = schema[0].getC_Currency_ID();
	}
	/**
	 * 
	 */
	public UNSBankCashImporter() {
		super();
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
			Hashtable<String, Object> freeColVals, int currentRow) 
	{
		MBankAccount bankAccount = (MBankAccount) po;
		
		String headerRef = m_Sheet.getCell(m_columnBankCash + currentRow).getContents();
		if(null == headerRef)
			return SimpleImportXLS.DONT_SAVE;
		
		Integer C_Bank_ID = (Integer) m_cache.get(headerRef);
		if(null == C_Bank_ID)
		{
			String sql = "SELECT C_Bank_ID FROM C_Bank WHERE UPPER(name) = UPPER(TRIM(?))";
			C_Bank_ID = DB.getSQLValue(m_trxName, sql, headerRef);
			
			if(C_Bank_ID <= 0)
			{
				MBank bank = new MBank(m_Ctx, 0, m_trxName);
				bank.setAD_Org_ID(bankAccount.getAD_Org_ID());
				bank.setDescription(" :: Auto Generate From Import ::");
				bank.setName(headerRef);
				
				String routingNo = m_Sheet.getCell(m_columnRoutingNo + currentRow).getContents();
				if(null == routingNo)
					return SimpleImportXLS.DONT_SAVE;
				
				bank.setRoutingNo(routingNo);
				bank.setSwiftCode(routingNo);
				bank.saveEx();
				
				C_Bank_ID = bank.get_ID();
			}
		}
		
		bankAccount.setC_Bank_ID(C_Bank_ID);
		bankAccount.setC_Currency_ID(m_C_Currency_ID);
		if(bankAccount.getAccountNo() == null)
			bankAccount.setAccountNo("-");
		
		return null;
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
