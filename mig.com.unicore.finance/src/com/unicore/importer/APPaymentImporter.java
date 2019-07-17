/**
 * 
 */
package com.unicore.importer;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import jxl.Sheet;
import com.unicore.base.model.MPaymentAllocate;
import com.unicore.model.factory.UNSFinanceModelFactory;
import com.uns.base.model.Query;
import com.uns.model.process.SimpleImportXLS;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrg;
import org.compiere.model.MPayment;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
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
public class APPaymentImporter implements ImporterValidation {
	
	private static CLogger log = CLogger.getCLogger(APPaymentImporter.class);
	
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
	static final String COL_DATE_INVOICED	= "DateInvoiced";
	static final String COL_DUEDATE_INVOICED	= "DueDateInvoiced";
	
	static final int BIAYA_TRANSFER_ID = 1000014;
	
	static final Hashtable<String, Integer> DEPO_MAP_ID = new Hashtable<>();
	
	static final Timestamp CUTOFF_SISA_DAY;
	
	static {
		DEPO_MAP_ID.put("PST", 1000025);
		DEPO_MAP_ID.put("KTB", 1000026);
		DEPO_MAP_ID.put("BTA", 1000027);
		DEPO_MAP_ID.put("TLB", 1000028);
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 11, 31, 0, 0, 0);
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
	private BigDecimal 	sisaPelunasan;
	private BigDecimal	discount;
	private	BigDecimal 	transferedAmt;
	private BigDecimal	biayaTransfer;
	private	String		bankCashAccount;
	//static final String COL_
	
	public APPaymentImporter(Properties ctx, Sheet sheet, String trxName)
	{
		m_Ctx = ctx;
		//m_Sheet = sheet;
		m_trxName = trxName;
	}
	/**
	 * 
	 */
	public APPaymentImporter() {
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
			int APDocType_ID = MDocType.getDocType(MDocType.DOCBASETYPE_APPayment);
			payment.setC_DocType_ID(APDocType_ID);
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
			
			//String sql = "SELECT C_BPartner_ID FROM C_BPartner_Location WHERE BPartnerSearchKey=?";
			MBPartner vendor = MBPartner.get(m_Ctx, kdRelasi);
			//int bp_ID = DB.getSQLValue(m_trxName, sql, kdRelasi);
			
			payment.setC_BankAccount_ID(bankCash_ID);
			payment.setC_BPartner_ID(vendor.get_ID());
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
		
		payment.saveEx();
		
		String sql = "DELETE FROM C_PaymentAllocate pa WHERE pa.C_Payment_ID=? AND pa.C_Invoice_ID IN ("
				+ "		SELECT i.C_Invoice_ID FROM C_Invoice i WHERE i.ReferenceNo=?)";
		int count = DB.executeUpdateEx(sql, new Object[]{payment.get_ID(), kdFaktur}, m_trxName);
		Log.warn("Payment Allocate(s) deleted (AR Invoice): " + count);
		
		String whereClause = "ReferenceNo=? AND IsSOTrx='N'";
		List<MInvoice> invList = 
				Query.get(m_Ctx, UNSFinanceModelFactory.EXTENSION_ID, MInvoice.Table_Name, whereClause, m_trxName)
				.setParameters(kdFaktur)
				.list();
		
		if (invList == null || invList.size() == 0) {
			MInvoice inv = createAPInvoice(freeColVals, payment);
			
			if (invList == null)
				invList = new ArrayList<MInvoice>();
			
			invList.add(inv);
		}
		
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
				int APCreditDT_ID = MDocType.getDocType(MDocType.DOCBASETYPE_APCreditMemo);
				
				sql = "SELECT SUM(GrandTotal) FROM C_Invoice WHERE ReferenceNo=? "
						+ "AND IsSOTrx='N' AND C_DocType_ID=? AND DocStatus<>'RE'";
				totalCNOnSystem = DB.getSQLValueBD(m_trxName, sql, kdCN, APCreditDT_ID);

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
				if (difference.abs().compareTo(portionDiscount.abs()) == 0)
					;
				else if (difference.signum() > 0) {
					writeOff = difference.negate(); // Kelebihan bayar, tapi hrs lunas tanpa sisa.
					portionDiscount = Env.ZERO;
				}
				else if (portionDiscount.signum() != 0) {
					portionDiscount = difference.negate(); // Kurang bayar dan jadikan diskon (gak boleh sisa).
				}
				else  {
					writeOff = difference.negate(); // Kurang bayar dan gak boleh ada sisa.
					portionDiscount = Env.ZERO;
				}
			}
			else {
				
				BigDecimal sisaBayarIni = sisaInvAmt.subtract(portionPayAmt).subtract(portionDiscount);
				
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
			allocation.saveEx();
		}
		
		return null;
	}
	
	Hashtable<Integer, Integer> m_VendorOrgMap = new Hashtable<Integer, Integer>();
	
	MInvoice createAPInvoice(Hashtable<String, Object> freeColVals, MPayment payment)
	{
		Timestamp dateInvoiced = (Timestamp) freeColVals.get(COL_DATE_INVOICED);
		
		if (!dateInvoiced.before(CUTOFF_SISA_DAY))
			return null;
		
		MInvoice inv = new MInvoice(m_Ctx, 0, m_trxName);
		
		Integer AD_Org_ID = m_VendorOrgMap.get(payment.getC_BPartner_ID());
		
		String sql = "SELECT div.Value FROM AD_Org div "
				+ "WHERE div.AD_Org_ID IN (SELECT Division_ID FROM UNS_Product_Division WHERE M_Product_ID IN "
				+ "		(SELECT M_Product_ID FROM C_BPartner_Product WHERE C_BPartner_ID=? ORDER BY M_Product_ID)"
				+ "		ORDER BY M_Product_ID)";
		String division = DB.getSQLValueStringEx(m_trxName, sql, payment.getC_BPartner_ID());
		if (division == null)
			throw new AdempiereException("Cannot Division for vendor of " + payment.getC_BPartner().getValue());
		
		division = division.substring(1);

			if (AD_Org_ID == null) 
		{
			String depoStr = kdFaktur.substring(kdFaktur.lastIndexOf(".") + 1);
			
			MOrg org = MOrg.get(m_Ctx, division + "-" + depoStr, m_trxName);
			
			AD_Org_ID = org.get_ID();
		}
		
		inv.setAD_Org_ID(AD_Org_ID);
		inv.setC_BPartner_ID(payment.getC_BPartner_ID());
		inv.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_APInvoice));
		inv.setC_DocTypeTarget_ID(inv.getC_DocType_ID());
		
		String invReffNo = (String) freeColVals.get(COL_KD_Faktur);
		
		inv.setReferenceNo(invReffNo);
		inv.setDocumentNo(invReffNo + "--" + division);

		//Timestamp dueDate = (Timestamp) freeColVals.get(COL_DUEDATE_INVOICED);
		
		int TOP = 45; //UNSTimeUtil.getDaysBetween(dateInvoiced, dueDate);
		
		if (TOP == 0) {
			inv.setPaymentRule(MInvoice.PAYMENTRULE_Cash);
		}
		else {
			String topValue = "TOP-" + TOP + "D";
			sql = "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE Value=?";
			
			int paytermID = DB.getSQLValueEx(m_trxName, sql, topValue);
			
			if (paytermID <= 0) {
				topValue = "TOP-" + TOP + "45";
				sql = "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE Value=?";
				
				paytermID = DB.getSQLValueEx(m_trxName, sql, topValue);
			}
			
			inv.setPaymentRule(MInvoice.PAYMENTRULE_OnCredit);
			inv.setC_PaymentTerm_ID(paytermID);
		}
		
		inv.setSalesRep_ID(1000013);
		inv.setC_Currency_ID(303);
		inv.setIsSOTrx(false);
		inv.setDescription("No Faktur : " + invReffNo + "; Real total=" + grandTotalInvoice);
		
		inv.saveEx();
		
		MInvoiceLine invLine = new MInvoiceLine(inv);
		inv.setC_Charge_ID(1000011);
		
		BigDecimal expectedBalance = sisa;
		
		if (division.equals("MAS") || division.equals("FL"))
			expectedBalance = sisa.subtract(sisa.multiply(BigDecimal.valueOf(0.1)));
		
		invLine.setPrice(expectedBalance);
		invLine.setQty(Env.ONE);
		invLine.setDescription("Total initial invoice value = " + grandTotalInvoice);
		invLine.saveEx();
		
		inv.processIt(DocAction.ACTION_Complete);
		inv.saveEx();
		
		return inv;
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

	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
		
	}
}
