/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import com.uns.base.model.MInvoice;
import com.uns.base.model.MInvoiceLine;
import com.uns.model.process.SimpleImportXLS;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.PO;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.jfree.util.Log;

import com.uns.importer.ImporterValidation;

/**
 * @author menjangan
 *
 */
public class InvoiceImporterValidation implements ImporterValidation {
	
	private Sheet m_sheet = null;
	private Properties m_ctx = null;
	private String m_trxName = null;
	private int m_recordCount = 0;

	/**
	 * 
	 */
	public InvoiceImporterValidation() {
		super();
	}
	
	public InvoiceImporterValidation(Properties ctx, Sheet sheet, String trxName)
	{
		this.m_ctx = ctx;
		this.m_sheet = sheet;
		this.m_trxName = trxName;
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
			Hashtable<String, Object> freeColVals, int currentRow) {
		if(m_recordCount == 0)
			m_recordCount = 2;
		
		MInvoice header = getHeader(po, poRefMap);
		MInvoiceLine line = (MInvoiceLine) poRefMap.get(String.valueOf(header.get_ID()));
		if(line == null)
		{
			String chargeAPI = "Inisialisasi Saldo Hutang";
			String chargeARI = "Inisialisasi Saldo Piutang";
			String thisCharge = header.getC_DocType().getDocBaseType().equals("API") ? chargeAPI : chargeARI;
			String sql = "SELECT C_Charge_ID FROM C_Charge WHERE C_Charge.Name LIKE ?";
			int C_Charge_ID = DB.getSQLValue(m_trxName, sql, thisCharge);
			if(C_Charge_ID <= 0)
				throw new AdempiereUserError("Charge " + thisCharge + " is not found");
			line = new MInvoiceLine(header);
			line.setC_Charge_ID(C_Charge_ID);
			line.setQtyInvoiced(BigDecimal.ONE);
			line.setQty(BigDecimal.ONE);
		}
		
		String cellNameAmt = new StringBuilder("F").append(m_recordCount).toString();
		Cell cellAmt = m_sheet.getCell(cellNameAmt);
		BigDecimal val = new BigDecimal(cellAmt.getContents());
		line.setPriceEntered(val);
		line.setPriceActual(val);
		line.setLineNetAmt();
		poRefMap.put(String.valueOf(header.get_ID()), line);
		
		m_recordCount++;
		return SimpleImportXLS.DONT_SAVE;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
	 */
	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) {
		// TODO Auto-generated method stub
		return SimpleImportXLS.DONT_SAVE;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveAllRow(java.util.Hashtable, org.compiere.model.PO[])
	 */
	@Override
	public String afterSaveAllRow(Hashtable<String, PO> poRefMap, PO[] pos) {
		
		ArrayList<MInvoice> invoiceList = new ArrayList<MInvoice>();
		for(PO po : poRefMap.values())
		{
			if(po instanceof MInvoice == false)
			{
				po.save();
				continue;
			}
			
			MInvoice inv = (MInvoice) po;
			invoiceList.add(inv);
		}
		for(MInvoice inv : invoiceList)
		{
			try {
				inv.processIt(MInvoice.DOCACTION_Complete);
				inv.saveEx();
			} catch (Exception ex) {
				StringBuilder sb = new StringBuilder("Failed to complete invoice document : ")
						.append(inv.getDocumentNo())
						.append(". Caused by : ")
						.append(ex.getMessage());
				String errMsg = sb.toString();
				throw new AdempiereException(errMsg);
			}
		}
		return SimpleImportXLS.DONT_SAVE;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#getPOReferenceMap()
	 */
	@Override
	public Hashtable<String, PO> getPOReferenceMap() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private MInvoice getHeader(PO po, Hashtable<String, PO> poMapRef)
	{		
		String cellNameNoFaktur		= new StringBuilder("B").append(m_recordCount).toString();
		Cell cellNoFaktur			= m_sheet.getCell(cellNameNoFaktur);
		String noFaktur				= cellNoFaktur.getContents();
		PO headerPO = poMapRef.get(noFaktur);
		
		if(null != headerPO && headerPO instanceof MInvoice == true)
			return (MInvoice) headerPO;
		
		int AD_Org_ID				= 0;
		int C_BPartner_ID			= 0;
		Timestamp tglFaktur			= null;
		Timestamp tglJatuhTempo		= null;
		int C_DocType_ID 			= 0;
		int C_Currency_ID			= 0;
		int C_BPartnerLocation_ID 	= 0;
		
		String cellNameAD_Org				= new StringBuilder("I").append(m_recordCount).toString();
		String cellNameBPartner				= new StringBuilder("A").append(m_recordCount).toString();
		String cellNameTglFaktur			= new StringBuilder("C").append(m_recordCount).toString();
		String cellNameTglJatuhTempo		= new StringBuilder("D").append(m_recordCount).toString();
		String cellNameC_DocType			= new StringBuilder("G").append(m_recordCount).toString();
		String cellNameC_Currency			= new StringBuilder("H").append(m_recordCount).toString();
		
		Cell cellAD_Org			= m_sheet.getCell(cellNameAD_Org);
		Cell cellBPartner		= m_sheet.getCell(cellNameBPartner);
		Cell cellTglFaktur		= m_sheet.getCell(cellNameTglFaktur);
		Cell cellTglJatuhTempo	= m_sheet.getCell(cellNameTglJatuhTempo);
		Cell cellC_DocType		= m_sheet.getCell(cellNameC_DocType);
		Cell cellC_Currency		= m_sheet.getCell(cellNameC_Currency);
		
		if(null != cellAD_Org)
		{
			String orgValue = cellAD_Org.getContents();
			if(null == orgValue)
				orgValue = "HO";
			
			String sql = "SELECT AD_Org_ID FROM AD_Org WHERE AD_Org.Value LIKE ?";
			AD_Org_ID = DB.getSQLValue(m_trxName, sql, orgValue);
			if(AD_Org_ID <= 0)
				throw new AdempiereUserError("Not Found Organization Wiht Search Key : " + orgValue);
		}
		
		if(null != cellBPartner)
		{
			String BpartnerName = cellBPartner.getContents();
			if(null == BpartnerName)
				throw new AdempiereUserError("Business Partner is not defined");
			
			String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE C_BPartner.Name like ?";
			
			C_BPartner_ID = DB.getSQLValue(m_trxName, sql, BpartnerName);
			if(C_BPartner_ID <=0)
				throw new AdempiereUserError("Not found BPartner with name : " + BpartnerName);
			
			String sql2 = "SELECT C_BPartner_Location_ID FROM C_BPartner_Location " +
							"WHERE C_BPartner_ID = " + C_BPartner_ID;
			
			C_BPartnerLocation_ID = DB.getSQLValue(m_trxName, sql2);
			if(C_BPartnerLocation_ID <= 0)
				throw new AdempiereUserError("Busines partner location is not set : " + BpartnerName);
		}

		if(null != cellTglFaktur)
		{
			String tempVal = cellTglFaktur.getContents();
			try {
				tempVal += " 00:00:01";
				tglFaktur = Timestamp.valueOf(tempVal);
			} catch (Exception ex) {
				String eMsg = "Failed Cast tanggal jatuh tempo to Timestamp " + tempVal;
				Log.info(eMsg);
				System.out.println(eMsg);
				tglFaktur = new Timestamp(System.currentTimeMillis());
			}
		}
		
		if(null != cellTglJatuhTempo)
		{
			String tempVal = cellTglJatuhTempo.getContents();
			try {
				tempVal += " 00:00:01";
				tglJatuhTempo = Timestamp.valueOf(tempVal);
			} catch (Exception ex) {
				String eMsg = "Failed Cast tanggal faktur to Timestamp " + tempVal;
				Log.info(eMsg);
				System.out.println(eMsg);
				tglJatuhTempo = new Timestamp(System.currentTimeMillis());
			}
		}
		
		if(null != cellC_DocType)
		{
			String DocBaseType = cellC_DocType.getContents();
			if(null == DocBaseType)
				DocBaseType = "ARI";
			
			C_DocType_ID = MDocType.getDocType(DocBaseType);
			
			if(C_DocType_ID <= 0)
				throw new AdempiereUserError("Not found Document Type with DocBaseType : " + DocBaseType);
		}
		
		if(null != cellC_Currency)
		{
			String currencyVal = cellC_Currency.getContents();
			if(null == currencyVal)
				throw new AdempiereUserError("Currency is not defined");
			
			String sql = "SELECT C_Currency_ID FROM C_Currency WHERE C_Currency.ISO_Code LIKE ?";
			C_Currency_ID = DB.getSQLValue(m_trxName, sql, currencyVal);
			if(C_Currency_ID <= 0)
				throw new AdempiereUserError("Not found currency with iso code : " + currencyVal);
		}
		
		MInvoice header = new MInvoice(m_ctx, 0, m_trxName);
		header.setAD_Org_ID(AD_Org_ID);
		header.setDateAcct(tglJatuhTempo);
		header.setDateInvoiced(tglFaktur);
		header.setC_DocType_ID(C_DocType_ID);
		header.setC_DocTypeTarget_ID(C_DocType_ID);
		header.setC_BPartner_ID(C_BPartner_ID);
		header.setC_BPartner_Location_ID(C_BPartnerLocation_ID);
		header.setC_Currency_ID(C_Currency_ID);
		header.setIsSOTrx(header.getC_DocType().isSOTrx());
		header.setPaymentRule(MInvoice.PAYMENTRULE_DirectDebit);
		header.setDescription("No Faktur : " + noFaktur);
		header.saveEx();
		poMapRef.put(noFaktur, header);
	
		return header;
	}
}
