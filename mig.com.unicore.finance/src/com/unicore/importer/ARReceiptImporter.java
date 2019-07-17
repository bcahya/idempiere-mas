/**
 * 
 */
package com.unicore.importer;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import jxl.Sheet;
import com.unicore.base.model.MPaymentAllocate;
import com.unicore.model.factory.UNSFinanceModelFactory;
import com.uns.base.model.MInvoice;
import com.uns.base.model.Query;
import com.uns.model.process.SimpleImportXLS;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPayment;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.jfree.util.Log;

import com.uns.importer.ImporterValidation;

/**
 * @author root
 *
 */
public class ARReceiptImporter implements ImporterValidation {
	
	private static CLogger log = CLogger.getCLogger(ARReceiptImporter.class);
	
	private Properties m_Ctx = null;
	//private Sheet m_Sheet = null;
	private String m_trxName = null;
	private int m_C_Currency_ID = 303;
	
	static final String COL_KD_Pelunasan 	= "KD_Pelunasan";
	static final String COL_KD_Faktur		= "KD_Faktur";
	static final String COL_KD_Relasi		= "KD_Relasi";
	static final String COL_GrandTotalInvoice = "OriInvoice_GrandTotal";
	static final String COL_Sisa			= "Sisa";
	static final String COL_PayAmt			= "PayAmt";
	static final String COL_KD_CN			= "KD_CN";
	static final String COL_Jml_CN			= "Jml_CN";
	static final String COL_KD_Giro			= "KD_Giro";
	static final String COL_Jml_Giro		= "Jml_Giro";
	static final String COL_Pembulatan		= "Pembulatan";
	static final String COL_KurangBayar		= "KurangBayar";
	static final String COL_Selisih			= "Selisih";
	static final String COL_SisaPelunasan	= "SisaPelunasan";
	static final String COL_Discount		= "Discount";
	static final String COL_TransferedAmt	= "TransferedAmt";
	static final String COL_BiayaTransfer	= "BiayaTransfer";
	static final String COL_BankCashAccount = "BankCashAccount";
	static final String COL_Keterangan		= "Keterangan";
	
	static final int BIAYA_TRANSFER_ID = 1000014;
	
	static final Hashtable<String, Integer> DEPO_MAP_ID = new Hashtable<>();
	
	static final Timestamp CUTOFF_SISA_DAY;
	
	static {
		DEPO_MAP_ID.put("PST", 1000025);
		DEPO_MAP_ID.put("KTB", 1000026);
		DEPO_MAP_ID.put("BTA", 1000027);
		DEPO_MAP_ID.put("TLB", 1000028);
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 11, 29, 0, 0, 0);
		CUTOFF_SISA_DAY = new Timestamp(cal.getTimeInMillis());
	}
	
	private String		kdPelunasan;
	private String		kdFaktur;
	private String		kdRelasi;
	private BigDecimal 	grandTotalInvoice;
	private BigDecimal 	sisa;
	private BigDecimal 	payAmt;
	private String		kdCN;
	private BigDecimal 	jmlCN;
	private String		kdGiro;
	//private BigDecimal 	jmlGiro;
	//private BigDecimal	pembulatan;
	//private	BigDecimal	kurangBayar;
	//private BigDecimal 	selisih;
	private BigDecimal 	sisaPelunasan;
	private BigDecimal	discount;
	private	BigDecimal 	transferedAmt;
	private BigDecimal	biayaTransfer;
	private	String		bankCashAccount;
	//static final String COL_
	
	public ARReceiptImporter(Properties ctx, Sheet sheet, String trxName)
	{
		m_Ctx = ctx;
		//m_Sheet = sheet;
		m_trxName = trxName;
	}
	/**
	 * 
	 */
	public ARReceiptImporter() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable, int)
	 */
	@Override
	public String beforeSave(
			Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{
		MPayment payment = (MPayment) po;
		
		Timestamp dateTrx = payment.getDateTrx();
		payment.setDateAcct(dateTrx);
		
		kdPelunasan			= (String) freeColVals.get(COL_KD_Pelunasan);
		kdFaktur			= (String) freeColVals.get(COL_KD_Faktur);
		kdRelasi			= (String) freeColVals.get(COL_KD_Relasi);
		grandTotalInvoice 	= (BigDecimal) freeColVals.get(COL_GrandTotalInvoice);
		sisa				= (BigDecimal) freeColVals.get(COL_Sisa);
		payAmt				= (BigDecimal) freeColVals.get(COL_PayAmt);
		kdCN				= freeColVals.get(COL_KD_CN) == null ? null : (String) freeColVals.get(COL_KD_CN);
		jmlCN				= (BigDecimal) freeColVals.get(COL_Jml_CN);
		kdGiro			 	= freeColVals.get(COL_KD_Giro) == null ? null : (String) freeColVals.get(COL_KD_Giro);;
		//jmlGiro				= (BigDecimal) freeColVals.get(COL_Jml_Giro);;
		//pembulatan			= (BigDecimal) freeColVals.get(COL_Pembulatan);
		//kurangBayar			= (BigDecimal) freeColVals.get(COL_KurangBayar);
		//selisih				= (BigDecimal) freeColVals.get(COL_Selisih);
		sisaPelunasan		= (BigDecimal) freeColVals.get(COL_SisaPelunasan);
		discount			= (BigDecimal) freeColVals.get(COL_Discount);
		transferedAmt		= (BigDecimal) freeColVals.get(COL_TransferedAmt);
		biayaTransfer		= (BigDecimal) freeColVals.get(COL_BiayaTransfer);
		bankCashAccount		= freeColVals.get(COL_BankCashAccount) == null ? 
				null : (String) freeColVals.get(COL_BankCashAccount);
		
		if (sisa.compareTo(sisaPelunasan) == 0)
			return SimpleImportXLS.DONT_SAVE;
		
		if (kdGiro != null && !kdGiro.isEmpty() && payAmt.signum() == 0 && !kdGiro.equalsIgnoreCase("NULL")) {
			return SimpleImportXLS.DONT_SAVE;
		}
		
		if (payment.get_ID() == 0)
		{
			payment.setDocumentNo(new StringBuilder(kdPelunasan).append("-").append(kdRelasi).toString());
			payment.setDateAcct(payment.getDateTrx());
			payment.setC_DocType_ID(1000008);
			payment.setC_Currency_ID(m_C_Currency_ID);

			String depoStr = kdFaktur.substring(kdFaktur.lastIndexOf(".") + 1);
			
			payment.setAD_Org_ID(DEPO_MAP_ID.get(depoStr));
			
			int bankCash_ID = 0;
			String kdBankCashAcc = bankCashAccount;
			if (bankCashAccount == null || bankCashAccount.isEmpty() || bankCashAccount.equalsIgnoreCase("NULL")) {
				if (transferedAmt.signum() > 0)
					kdBankCashAcc = "Bank1-" + depoStr;
				else
					kdBankCashAcc = "KB" + depoStr.charAt(0);
			}
			
			bankCash_ID = DB.getSQLValueEx(m_trxName, 
					"SELECT C_BankAccount_ID FROM C_BankAccount WHERE LOWER(Value)=?", 
					kdBankCashAcc.toLowerCase());
			
			String sql = "SELECT C_BPartner_ID FROM C_BPartner_Location WHERE BPartnerSearchKey=?";
			int bp_ID = DB.getSQLValue(m_trxName, sql, kdRelasi);
			
			payment.setC_BankAccount_ID(bankCash_ID);
			payment.setC_BPartner_ID(bp_ID);
		}
		else if (payment.isComplete()) {
			return SimpleImportXLS.DONT_SAVE;
		}
		
		BigDecimal paidAmt = payAmt;
		
		if (transferedAmt.signum() > 0) 
		{
			payment.setTenderType(MPayment.TENDERTYPE_DirectDebit);
			
			paidAmt = transferedAmt;
			
			if (biayaTransfer.signum() > 0) {
				payment.setC_Charge_ID(BIAYA_TRANSFER_ID);
				payment.setChargeAmt(biayaTransfer);
				
				//Karena di galaxy hanya salah satu line pelunasan yang dikurangi biaya transfer, maka 
				//hanya jika ada biaya transfer harus ditambah lagi ke paidAmt.
				paidAmt = paidAmt.add(biayaTransfer);
			}
		}
		else 
			payment.setTenderType(MPayment.TENDERTYPE_Cash);
		
		paidAmt = paidAmt.add(jmlCN);
		
		payment.disableModelValidation();
		payment.saveEx();
		
		String sql = "DELETE FROM C_PaymentAllocate pa WHERE pa.C_Payment_ID=? AND pa.C_Invoice_ID IN ("
				+ "		SELECT i.C_Invoice_ID FROM C_Invoice i WHERE i.ReferenceNo=?)";
		int count = DB.executeUpdateEx(sql, new Object[]{payment.get_ID(), kdFaktur}, m_trxName);
		Log.warn("Payment Allocate(s) deleted (AR Invoice): " + count);
		
		String whereClause = "ReferenceNo=? AND IsSOTrx='Y'";
		List<MInvoice> invList = 
				Query.get(m_Ctx, UNSFinanceModelFactory.EXTENSION_ID, MInvoice.Table_Name, whereClause, m_trxName)
				.setParameters(kdFaktur)
				.list();
		
		boolean isPaid = 
				sisaPelunasan.signum() == 0 && (kdGiro == null || kdGiro.isEmpty() || kdGiro.equalsIgnoreCase("NULL"))? 
						true : false;
		//BigDecimal maxDeviation = BigDecimal.valueOf(5000);
		
		BigDecimal totalCNOnSystem = Env.ZERO;
		BigDecimal cnToWriteOff = Env.ZERO;
		List<MInvoice> cnList = null;
		
		String allocationDescription = "";
		
		if (kdCN != null && !kdCN.isEmpty() && !kdCN.equalsIgnoreCase("NULL"))
		{
			sql = "DELETE FROM C_PaymentAllocate pa WHERE pa.C_Payment_ID=? AND pa.C_Invoice_ID IN ("
					+ "		SELECT i.C_Invoice_ID FROM C_Invoice i WHERE i.ReferenceNo=?)";
			count = DB.executeUpdateEx(sql, new Object[]{payment.get_ID(), kdCN}, m_trxName);
			Log.warn("Payment Allocate(s) deleted (AR Credit Memo): " + count);
			
			cnList = Query.get(m_Ctx, UNSFinanceModelFactory.EXTENSION_ID, 
					MInvoice.Table_Name, whereClause, m_trxName)
			.setParameters(kdCN)
			.list();

			if (cnList != null && cnList.size() > 0) 
			{
				sql = "SELECT SUM(GrandTotal) FROM C_Invoice WHERE ReferenceNo=? "
						+ "AND IsSOTrx='Y' AND C_DocType_ID=1000004 AND DocStatus<>'RE'";
				totalCNOnSystem = DB.getSQLValueBD(m_trxName, sql, kdCN);

				cnToWriteOff = jmlCN.subtract(totalCNOnSystem);
				
				if (cnToWriteOff.signum() > 0) {
					paidAmt = paidAmt.subtract(cnToWriteOff);
				}
				
				for (MInvoice cnInv : cnList)
				{
					MPaymentAllocate allocation = new MPaymentAllocate(m_Ctx, 0, m_trxName);
					
					BigDecimal cnToPay = cnInv.getGrandTotal().negate();
					if (cnToWriteOff.signum() < 0) {
						
						BigDecimal portion = 
								cnInv.getGrandTotal().divide(totalCNOnSystem, 10, BigDecimal.ROUND_HALF_UP);
						if (portion.compareTo(new BigDecimal(0.99)) > 0)
							portion = Env.ONE;
						
						BigDecimal portionCNToWOFF =
								cnToWriteOff.multiply(portion).setScale(3, BigDecimal.ROUND_HALF_DOWN);
						
						allocation.setWriteOffAmt(portionCNToWOFF);
						cnToPay = cnToPay.subtract(portionCNToWOFF);
					}
					
					allocation.setInvoiceAmt(cnInv.getGrandTotal().negate());
					allocation.setAmount(cnToPay);
					allocation.setPayToOverUnderAmount(cnToPay);
					allocation.setC_Invoice_ID(cnInv.get_ID());
					allocation.setC_Payment_ID(payment.get_ID());
					allocation.setAD_Org_ID(cnInv.getAD_Org_ID());
					allocation.disableModelValidation();
					allocation.saveEx();
				}
				
//				if (cnToWriteOff.signum() < 0)
//					// Reset agar hanya di write-off di payment allocate utk CN Invoice saja.
//					cnToWriteOff = Env.ZERO; 
			}
			else {
				cnToWriteOff = jmlCN;
				paidAmt = paidAmt.subtract(cnToWriteOff);
				
				allocationDescription = 
						"Write-off dari CN nomor " + kdCN + 
						" yang tidak dimigrasikan (CN sebelum tanggal 30 Desember).";
			}
		}

		for (MInvoice inv : invList)
		{
			log.info("Creating Payment Allocation for invoice number of " + inv.getDocumentNo());
			if (inv.getGrandTotal().signum()==0)
			{
				inv.setIsPaid(true);
				inv.saveEx();
				continue;
			}
			
			if (inv.getDateInvoiced().before(CUTOFF_SISA_DAY)) {
				sql = "SELECT SUM(GrandTotal) FROM C_Invoice WHERE ReferenceNo=?";
				BigDecimal totalSameRef = DB.getSQLValueBD(m_trxName, sql, inv.getReferenceNo());
				if (totalSameRef.signum() > 0)
					grandTotalInvoice = totalSameRef;
			}
			
			BigDecimal portion = inv.getGrandTotal().divide(grandTotalInvoice, 10, BigDecimal.ROUND_HALF_UP);
			if (portion.compareTo(new BigDecimal(0.99)) > 0)
				portion = Env.ONE;
			BigDecimal portionPayAmt = paidAmt.multiply(portion).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			BigDecimal portionDiscount = discount.multiply(portion).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			//BigDecimal portionSisa = sisa.multiply(portion).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			
			BigDecimal writeOff = Env.ZERO;
			BigDecimal overUnderAmt = Env.ZERO;
			
			MPaymentAllocate allocation = new MPaymentAllocate(m_Ctx, 0, m_trxName);
			//sql = "SELECT COALESCE(SUM(Amount),0) + COALESCE(SUM(WriteOffAmt),0) + COALESCE(SUM(DiscountAmt),0) "
			//		+ "+ COALESCE(SUM(OverUnderAmt,0)) + COALESCE(SUM(WithholdingAmt), 0) FROM C_PaymentAllocate "
			//sql = "SELECT COALESCE(SUM(Amount + WriteOffAmt + DiscountAmt + WithholdingAmt + overunderamt),0) "
			sql = "SELECT COALESCE(SUM(Amount + WriteOffAmt + DiscountAmt + WithholdingAmt),0) "
					+ "FROM C_PaymentAllocate WHERE C_Invoice_ID=? "
					+ "	AND C_Payment_ID IN (SELECT C_Payment_ID FROM C_Payment WHERE DateTrx < ?)";
			BigDecimal totalPaidInvAmt = DB.getSQLValueBDEx(m_trxName, sql, inv.get_ID(), payment.getDateTrx());
			BigDecimal sisaInvAmt = inv.getGrandTotal().subtract(totalPaidInvAmt);
			
			if (sisaInvAmt.signum() <= 0)
				continue;
			
			BigDecimal difference = portionPayAmt.subtract(sisaInvAmt);
			//BigDecimal perbedaanSisa = sisaInvAmt.subtract(portionSisa);
			
			if (cnToWriteOff.signum() > 0) {
				BigDecimal portionWriteOff = 
						cnToWriteOff.multiply(portion).setScale(2, BigDecimal.ROUND_HALF_DOWN);
				BigDecimal totalPayment = portionPayAmt.add(portionWriteOff);
				
				if (totalPayment.compareTo(sisaInvAmt) > 0)
					portionWriteOff = totalPayment.subtract(sisaInvAmt);
				writeOff = portionWriteOff;
				
				difference = portionPayAmt.subtract(sisaInvAmt.subtract(writeOff));
				overUnderAmt = difference.negate();
			}
			else if (isPaid)
			{
//				boolean isAllowedBedasisa = perbedaanSisa.abs().compareTo(BigDecimal.valueOf(10)) <= 0? true : false;
//				if (!isAllowedBedasisa) {
//					overUnderAmt = difference.negate();
//				}
//				else {
					if (difference.signum() > 0)
						writeOff = difference.negate(); // Kelebihan bayar, tapi hrs lunas tanpa sisa.
					else if (portionDiscount.signum() != 0)
						portionDiscount = difference.negate(); // Kurang bayar dan jadikan diskon (gak boleh sisa).
					else 
						writeOff = difference.negate(); // Kurang bayar dan gak boleh ada sisa.
//				}
			}
			else {
				
				BigDecimal sisaBayarIni = sisaInvAmt.subtract(portionPayAmt).subtract(portionDiscount);
				
				//overUnderAmt = difference.negate();
				overUnderAmt = sisaBayarIni;
			}
			String keterangan = (String) freeColVals.get(COL_Keterangan);
			
			if (!Util.isEmpty(keterangan, true) && !keterangan.equals("NULL"))
				allocationDescription += " - " + keterangan;
			
			allocation.setDescription(allocationDescription);
			allocation.setInvoiceAmt(sisaInvAmt);
			allocation.setAmount(portionPayAmt);
			allocation.setPayToOverUnderAmount(portionPayAmt);
			allocation.setWriteOffAmt(writeOff);
			allocation.setOverUnderAmt(overUnderAmt);
			allocation.setDiscountAmt(portionDiscount);
			allocation.setC_Payment_ID(payment.get_ID());
			allocation.setAD_Org_ID(inv.getAD_Org_ID());
			allocation.setC_Invoice_ID(inv.get_ID());
			allocation.disableModelValidation();
			allocation.saveEx();
		}
		
//		if (cnList == null)
//			return null;
		String errMsg = updatePaymentAmounts(payment);
		
		return errMsg;
	}
	
	private String updatePaymentAmounts(MPayment payment)
	{
		BigDecimal payAmt = Env.ZERO;
		BigDecimal writeOffAmt = Env.ZERO;
		BigDecimal overUnderAmt = Env.ZERO;
		BigDecimal discountAmt = Env.ZERO;
		BigDecimal withholdingAmt = Env.ZERO;
		
		StringBuilder sql = new StringBuilder ("SELECT COALESCE(SUM(Amount), 0), COALESCE(SUM(WriteOffAmt), 0),  ")
		.append(" COALESCE(SUM(OverUnderAmt), 0), COALESCE(SUM(DiscountAmt), 0), COALESCE(SUM(WithholdingAmt), 0) ")
		.append(" FROM C_PaymentAllocate WHERE C_Payment_ID=").append(payment.get_ID());
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = DB.prepareStatement(sql.toString(), m_trxName);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				payAmt = payAmt.add(rs.getBigDecimal(1));
				writeOffAmt = writeOffAmt.add(rs.getBigDecimal(2));
				overUnderAmt = overUnderAmt.add(rs.getBigDecimal(3));
				discountAmt = discountAmt.add(rs.getBigDecimal(4));
				withholdingAmt = withholdingAmt.add(rs.getBigDecimal(5));
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
		finally {
			try {
				stmt.close();
				rs.close();
			}
			catch (SQLException ex) {
					ex.printStackTrace();
					return ex.getMessage();
			}
		}
		
		BigDecimal totalAmount = payAmt;
		if (payment.getChargeAmt().signum() != 0)
			payAmt = totalAmount.subtract(payment.getChargeAmt());
		
		payment.setPayAmt(payAmt);
		payment.setWriteOffAmt(writeOffAmt);
		payment.setOverUnderAmt(overUnderAmt);
		payment.setDiscountAmt(discountAmt);
		payment.setWithholdingAmt(withholdingAmt);
		
		if (overUnderAmt.compareTo(Env.ZERO) != 0)
			payment.setIsOverUnderPayment(true);
		
		payment.setVendorBeginningBalance(Env.ZERO);
		payment.setVendorEndingBalance(Env.ZERO);
		
//		if (payment.getC_BPartner_ID() > 0) 
//		{
//			//I_C_BPartner bp = MBPartner.get(m_Ctx, payment.getC_BPartner_ID());
//			BigDecimal totalToCorrectBPOpenBalance = 
//					totalAmount.add(writeOffAmt).add(discountAmt);//.add(withholdingAmt);
//
//			sql = new StringBuilder("SELECT TotalOpenBalance FROM C_BPartner WHERE C_BPartner_ID=" + payment.getC_BPartner_ID());
//			
//			BigDecimal bpBeginningBalance = DB.getSQLValueBD(m_trxName, sql.toString());
//			if (bpBeginningBalance == null)
//				bpBeginningBalance = Env.ZERO;
//			
//			BigDecimal bpEndingBalance = bpBeginningBalance;
//			
//			if (payment.isReceipt())
//				bpEndingBalance = bpBeginningBalance.subtract(totalToCorrectBPOpenBalance);
//			else 
//				bpEndingBalance = bpBeginningBalance.add(totalToCorrectBPOpenBalance);
//			
//			payment.setVendorBeginningBalance(bpBeginningBalance);
//			payment.setVendorEndingBalance(bpEndingBalance);
//		}		
		if (!payment.save())
			throw new AdempiereException("Failed when refreshing payment amount.");
		
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

	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
		
	}
}
