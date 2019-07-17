/**
 * 
 */
package com.uns.model.factory;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import com.unicore.model.process.UNSBankTransfer;
import com.uns.model.process.SimpleImportXLS;

import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.uns.importer.ImporterValidation;

/**
 * @author root
 *
 */
public class UNSPBImporter implements ImporterValidation {

	/**
	 * 
	 */
	public UNSPBImporter() {
		super();
	}

	private Properties m_Ctx = null;
	private Sheet m_Sheet = null;
	private String m_trxName = null;
	private Hashtable<Object, Object> m_cache = new Hashtable<>();
	private final int m_C_Charge_ID = 1000005;
	private String m_columnReference = "A";
	private String m_columnDate = "B";
	private String m_columnDesc = "C";
	private String m_columnAmt = "D";
	private String m_columnAcctFrom = "E";
	private String m_columnAcctTo = "F";
	private final String m_bpName = "FLGUser";
	
	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
	}
	
	private String getReference(int currentRow)
	{
		Cell cell = m_Sheet.getCell(m_columnReference + currentRow);
		if(null == cell)
			return null;
		
		String value =  cell.getContents();
		return value;
	}
	
	private Timestamp getDate(int currentRow)
	{
		Cell cell = m_Sheet.getCell(m_columnDate + currentRow);
		if(null == cell)
			return null;
		String tmp = cell.getContents();
		
		if(Util.isEmpty(tmp, true))
			return null;
		
		Timestamp value =  Timestamp.valueOf(tmp);
		return value;
	}
	
	private String getDecription(int currentRow)
	{
		Cell cell = m_Sheet.getCell(m_columnDesc + currentRow);
		if(null == cell)
			return null;
		
		String value =  cell.getContents();
		return value;
	}
	
	private BigDecimal getAmt(int currentRow)
	{
		Cell cell = m_Sheet.getCell(m_columnAmt + currentRow);
		if(null == cell)
			return Env.ZERO;
		
		String tmp = cell.getContents();
		if(Util.isEmpty(tmp, true))
			return Env.ZERO;
		
		BigDecimal value =  new BigDecimal(cell.getContents());
		return value;
	}
	
	private int getAccountFrom_ID(int currentRow)
	{
		Cell cell = m_Sheet.getCell(m_columnAcctFrom + currentRow);
		if(null == cell)
			return 0;
		
		String tmp =  cell.getContents();
		if(Util.isEmpty(tmp, true))
			return 0;
		
		Integer retVal = (Integer) m_cache.get(tmp);
		if(null != retVal)
			return retVal;
		
		String sql = "SELECT C_BankAccount_ID FROM C_BankAccount WHERE UPPER(TRIM(Name)) = '"
				.concat(tmp.toUpperCase().trim()).concat("'");
		retVal = DB.getSQLValue(m_trxName, sql);
		if(retVal > 0)
			m_cache.put(tmp, retVal);

		return retVal;
	}
	
	private int getAccountTo_ID(int currentRow)
	{
		Cell cell = m_Sheet.getCell(m_columnAcctTo + currentRow);
		if(null == cell)
			return 0;
		
		String tmp =  cell.getContents();
		if(Util.isEmpty(tmp, true))
			return 0;
		
		Integer retVal = (Integer) m_cache.get(tmp);
		if(null != retVal)
			return retVal;
		
		String sql = "SELECT C_BankAccount_ID FROM C_BankAccount WHERE UPPER(TRIM(Name)) = '"
				.concat(tmp.toUpperCase().trim()).concat("'");
		retVal = DB.getSQLValue(m_trxName, sql);
		if(retVal > 0)
			m_cache.put(tmp, retVal);
		
		return retVal;
	}
	
	private int getAD_Org_ID(int C_BankAccount_ID)
	{
		String key = (new StringBuilder("AD_Org_ID").append("-").append(C_BankAccount_ID)).toString();
		Integer AD_Org_ID = (Integer) m_cache.get(key);
		if(null == AD_Org_ID)
		{
			String sql = "SELECT AD_Org_ID FROM C_BankAccount WHERE C_BankAccount_ID = ?";
			AD_Org_ID = DB.getSQLValue(m_trxName, sql, C_BankAccount_ID);
			if(AD_Org_ID > 0)
				m_cache.put(key, AD_Org_ID);
			else
				AD_Org_ID = -1;
		}

		return AD_Org_ID;
	}
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable, int)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) 
	{
		
		Timestamp dateImported = (Timestamp) m_cache.get("DateImported");
		if(null == dateImported)
		{
			dateImported = new Timestamp(System.currentTimeMillis());
			m_cache.put("DateImported", dateImported);
		}
		String userImported = (String) m_cache.get("UserImported");
		if(null == userImported)
		{
			MUser user = MUser.get(m_Ctx);
			userImported = user.getRealName();
			m_cache.put("UserImported", userImported);
		}
		Integer C_BPartner_ID = (Integer) m_cache.get(m_bpName);
		if(null == C_BPartner_ID)
		{
			String SQL = "SELECT C_BPartner_ID FROM C_BPartner WHERE UPPER(TRIM(Name)) = '"
					.concat(m_bpName.trim().toUpperCase()).concat("'");
			C_BPartner_ID = DB.getSQLValue(m_trxName, SQL);
			if(C_BPartner_ID > 0)
				m_cache.put(m_bpName, C_BPartner_ID);
		}
		StringBuilder sb = new StringBuilder(":: AUTO GENERATE FROM IMPORT ON ")
			.append(dateImported).append(" BY ").append(userImported).append("\n")
			.append(getDecription(currentRow)).append("\n").append(getReference(currentRow));
		
		UNSBankTransfer bt = new UNSBankTransfer(
				m_Ctx, null, sb.toString(), C_BPartner_ID, 0 /**CUrrency_ID */, 0 /**ConversionType */
				, m_C_Charge_ID, null /**ChequeNo*/, getAmt(currentRow), getAccountFrom_ID(currentRow)
				, getAccountTo_ID(currentRow), getDate(currentRow), getDate(currentRow)
				,getAD_Org_ID(getAccountFrom_ID(currentRow)), getAD_Org_ID(getAccountTo_ID(currentRow))
				, true, m_trxName);
		
		bt.doIt();
		
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

}
