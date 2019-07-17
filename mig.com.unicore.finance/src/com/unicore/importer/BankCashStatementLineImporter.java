/**
 * 
 */
package com.unicore.importer;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Properties;

import jxl.Sheet;

import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
//import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.importer.ImporterValidation;

/**
 * @author AzHaidar
 *
 */
public class BankCashStatementLineImporter implements ImporterValidation {
//
//	private static CLogger log = CLogger.getCLogger(BankCashStatementLineImporter.class);
//	
	private Properties m_Ctx = null;
	private String m_trxName = null;
	
	static final String COL_DateStatement 	= "DateStatement";
	static final String COL_KD_KAS			= "KD_KAS";
	static final String COL_Description		= "Description";
	static final String COL_Charge			= "Charge";
	static final String COL_Debit			= "Debit";
	static final String COL_Kredit			= "Kredit";
	static final String COL_KD_BANK_CASH	= "KD_BankCash";
	
	/**
	 * 
	 */
	public BankCashStatementLineImporter(Properties ctx, Sheet sheet, String trxName)
	{
		m_Ctx = ctx;
		//m_Sheet = sheet;
		m_trxName = trxName;
	}
	
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable, int)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) 
	{
		MBankStatementLine bsl = (MBankStatementLine) po;
		
		bsl.setDateAcct(bsl.getStatementLineDate());
		
		BigDecimal debit = freeColVals.get(COL_Debit) == null? Env.ZERO : (BigDecimal) freeColVals.get(COL_Debit);
		BigDecimal kredit = freeColVals.get(COL_Kredit) == null? Env.ZERO : (BigDecimal) freeColVals.get(COL_Kredit);
		
		BigDecimal amount = kredit;
		BigDecimal signum = Env.ONE.negate();
		String trxType = MBankStatementLine.TRANSACTIONTYPE_APTransaction;
		
		if (kredit.compareTo(Env.ZERO) == 0 && debit.compareTo(Env.ZERO) > 0) {
			amount = debit;
			signum = signum.abs();
			trxType = MBankStatementLine.TRANSACTIONTYPE_ARTransaction;
		}
		
		bsl.setIsManual(true);
		bsl.setValutaDate(bsl.getStatementLineDate());
		bsl.setTransactionType(trxType);
		bsl.setAmount(amount);
		bsl.setStmtAmt(amount.multiply(signum));
		bsl.setChargeAmt(amount.multiply(signum));
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(bsl.getStatementLineDate());
		
		int month = cal.get(Calendar.MONTH) + 1;
		String monthStr = String.valueOf(month);
		if (month < 10)
			monthStr = "0" + monthStr;
		
		int year = cal.get(Calendar.YEAR);
		
		String kdBankCash = (String) freeColVals.get(COL_KD_BANK_CASH);
		
		String referenceNo = bsl.getReferenceNo();
		String depoStr = referenceNo.substring(referenceNo.length() - 3);
		String depoValue = "G-" + depoStr;
		
//		if (kdBankCash == null) {
			kdBankCash = "KK" + String.valueOf(depoStr.charAt(0));
			
		String sql = "SELECT C_BankAccount_ID FROM C_BankAccount WHERE Value=? AND AD_Org_ID = ("
				+ "		SELECT AD_Org_ID FROM AD_Org WHERE Value=?)";
		int kdBankAcc_ID = DB.getSQLValueEx(m_trxName, sql, kdBankCash, depoValue);
		
		if (kdBankAcc_ID <= 0)
			return "Cannot find bank/cash account for row of " + currentRow;
		
		String documentNo = 
				new StringBuilder("PC:").append(year).append(".")
				.append(monthStr).append(kdBankCash).toString();
		
		MBankStatement statement = 
				new Query(m_Ctx, MBankStatement.Table_Name, "DocumentNo=?", m_trxName)
				.setParameters(documentNo).firstOnly();
		
		if (statement == null) 
		{
			sql = "SELECT C_BankStatement_ID FROM C_BankStatement WHERE C_BankAccount_ID=? AND DocStatus='DR'";
			int prevDraftedStmt_ID = DB.getSQLValueEx(m_trxName, sql, kdBankAcc_ID);
			
			if (prevDraftedStmt_ID > 0)
			{
				MBankStatement stmt = new MBankStatement(m_Ctx, prevDraftedStmt_ID, m_trxName);
				if (stmt.get_ID() > 0)
				{
					stmt.processIt(DocAction.ACTION_Complete);
					stmt.disableModelValidation();
					stmt.saveEx();
				}
			}
			
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			Timestamp tgl1 = new Timestamp(cal.getTimeInMillis());
			
			statement = new MBankStatement(m_Ctx, 0, m_trxName);
			statement.setDocumentNo(documentNo);
			statement.setName(documentNo + " " + tgl1);
			statement.setC_BankAccount_ID(kdBankAcc_ID);
			statement.setStatementDate(tgl1);
		}
		
		statement.setDateAcct(statement.getStatementDate());
		statement.setIsManual(true);
		
		statement.saveEx();
		
		bsl.setC_BankStatement_ID(statement.getC_BankStatement_ID());
		
		return null;
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

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#setTrxName(java.lang.String)
	 */
	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
	}

}
