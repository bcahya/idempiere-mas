/**
 * 
 */
package com.unicore.importer;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import jxl.Sheet;
import com.uns.base.model.Query;
import com.uns.model.MProduct;
import com.uns.model.factory.UNSFinanceModelFactory;
import com.uns.model.process.SimpleImportXLS;

import org.adempiere.util.IProcessUI;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrg;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.PO;
import org.compiere.model.X_C_Invoice;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.jfree.util.Log;

import com.uns.importer.ImporterValidation;
import com.uns.util.UNSTimeUtil;

/**
 * @author menjangan
 *
 */
public class InvoiceV2ImporterValidation_v2 implements ImporterValidation {
	
	//private Sheet m_sheet = null;
	private Properties m_ctx = null;
	private String m_trxName = null;
	private boolean m_IsCurrTrxImport = false;
	Hashtable<String, PO> m_poRefMap = null;
	Hashtable<String, Object> m_freeColVals;
	private MInvoice m_invoice = null;
	String m_currentDivisionPrefix;
	IProcessUI m_processMonitor = null;
	
	static Timestamp CUTOFFSisaDAY;
	
	public static final String COL_BPARTNER = "KD_BPartner";
	public static final String COL_BP_LOCATION = "BPartnerLocation";
	public static final String COL_KD_FAKTUR = "KD_Faktur";
	public static final String COL_KD_PRODUK = "KD_Produk";
	public static final String COL_QTY = "Qty";
	public static final String COL_PRICE = "Price";
	public static final String COL_DISCOUNTLINE = "DiscountLine";
	public static final String COL_DOCDATE = "DocDate";
	public static final String COL_DUEDATE = "DueDate";
	public static final String COL_GRANDTOTAL = "GrandTotal";
	public static final String COL_ADDITIONAL_DISCOUNT = "AddDiscountAmt";
	public static final String COL_PPN = "PPN";
	public static final String COL_SISA = "Saldo";
	public static final String COL_ISSOTRX = "IsSOTrx";
	public static final String COL_CURRS_ID = "Currency";
	public static final String COL_IS_CURR_TRX = "CurrentTrx";
	public static final String COL_SALES = "Salesman";
	public static final String COL_DIVISION = "Division";
	public static final String COL_IsCN = "IsCN";
	
	public static final int DIV_SR = 1000031;
	public static final int DIV_FL = 1000029;
	public static final int DIV_MAS = 1000030;
	public static final int DIV_DUDMAS = 1000032;
	
	public static final int AP_INVOICE = 1000005;
	public static final int AR_INVOICE = 1000002;
	public static final int AP_CREDITMEMO = 1000006;
	public static final int AR_CREDITMEMO = 1000004;
	
	public static final int MM_RECEIPT = 1000014;
	public static final int MM_SHIPMENT = 1000011;
	public static final int MM_VENDOR_RETURN = 1000013;
	public static final int MM_CUSTOMER_RETURN = 1000015;
	
	public static final String PREFIX_SR = "SR";
	public static final String PREFIX_MAS = "MAS";
	public static final String PREFIX_FL = "FL";
	
	public static final String PREFIX_WH_GOOD = "G.Good.";
	public static final String PREFIX_LOCATOR_GOOD = "Good-";
	
	public static final String PREFIX_WH_SC = "G.SC.";
	public static final String PREFIX_LOCATOR_SC = "StkCare-";
	
	public static Hashtable<String, Integer> FL_ORG_MAP = new Hashtable<>();
	
	static {
		FL_ORG_MAP.put("MAS-PST", 1000012);
		FL_ORG_MAP.put("MAS-KTB", 1000013);
		FL_ORG_MAP.put("MAS-BTA", 1000014);
		FL_ORG_MAP.put("MAS-TLB", 1000015);

		FL_ORG_MAP.put("SR-PST", 1000016);
		FL_ORG_MAP.put("SR-KTB", 1000017);
		FL_ORG_MAP.put("SR-BTA", 1000019);
		FL_ORG_MAP.put("SR-TLB", 1000020);

		FL_ORG_MAP.put("FL-PST", 1000006);
		FL_ORG_MAP.put("FL-KTB", 1000005);
		FL_ORG_MAP.put("FL-BTA", 1000004);
		FL_ORG_MAP.put("FL-TLB", 1000007);
		FL_ORG_MAP.put("SR", 1000011);
		
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 11, 29, 0, 0, 0);
		
		CUTOFFSisaDAY = new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 
	 */
	public InvoiceV2ImporterValidation_v2() {
		super();
	}
	
	public InvoiceV2ImporterValidation_v2(Properties ctx, Sheet sheet, String trxName)
	{
		this.m_ctx = ctx;
		//this.m_sheet = sheet;
		this.m_trxName = trxName;
		m_processMonitor = Env.getProcessUI(ctx);
	}

	
	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
	}
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{
		int BP_ID = (Integer) freeColVals.get(COL_BPARTNER);
		boolean isSOTrx = (Boolean) freeColVals.get(COL_ISSOTRX);
		m_IsCurrTrxImport = (Boolean) freeColVals.get(COL_IS_CURR_TRX);
		
		if (BP_ID == 1000051 && !isSOTrx && !m_IsCurrTrxImport)
			return SimpleImportXLS.DONT_SAVE;
		
		BigDecimal totalPPN = (BigDecimal) freeColVals.get(COL_PPN);
		boolean isSR = (!isSOTrx && totalPPN.signum() == 0);
		
		m_poRefMap = poRefMap;
		m_freeColVals = freeColVals;
		
		MInvoiceLine invLine = (MInvoiceLine) po;
		
		String sql = "SELECT p.C_UOM_ID, pd.Division_ID FROM M_Product p, UNS_Product_Division pd "
				+ "WHERE p.M_Product_ID=pd.M_Product_ID AND p.M_Product_ID=?";
		
		List<List<Object>> pDetails = DB.getSQLArrayObjectsEx(m_trxName, sql, invLine.getM_Product_ID());
		
		if (pDetails == null || pDetails.size() == 0)
			return "Cannot save row " + currentRow + ": cannot find Division and or UOM of Product " 
					+ invLine.getM_Product();
		
		int UOM_ID = ((BigDecimal) (pDetails.get(0).get(0))).intValue();
		int division_ID = ((BigDecimal) (pDetails.get(0).get(1))).intValue();
		
		invLine.setC_UOM_ID(UOM_ID);
		
		String kdFaktur = (String) freeColVals.get(COL_KD_FAKTUR);
		Boolean isCN = (Boolean) freeColVals.get(COL_IsCN);
		
		//------------------------
		// Handler barang2 UDMAS
		//------------------------
		InvoiceImporterConsolidator_v2 consolidator = (InvoiceImporterConsolidator_v2) m_poRefMap.get(kdFaktur);
		
		if (isSOTrx && !isCN && m_IsCurrTrxImport)
		{
			if (consolidator == null) {
				consolidator = new InvoiceImporterConsolidator_v2(m_ctx, 0, m_trxName);
				consolidator.kdFaktur = kdFaktur;
				m_poRefMap.put(kdFaktur, consolidator);
			}

			boolean isUDMASProduct = (division_ID == DIV_DUDMAS);
			
			consolidator.invLines.add(invLine);
			consolidator.freeColsVals.add(freeColVals);
			consolidator.lineDivisions.add(division_ID);
			consolidator.lineUDMASStatus.add(isUDMASProduct);
	
			if (isUDMASProduct) 
			{
				consolidator.isHasUDMASProduct = true;
				return SimpleImportXLS.DONT_SAVE;
			}
			else 
				consolidator.isHasNonUDMASProduct = true;
		}		
		// End of Handler Barang-2 UDMAS.
		
		String depoStr = kdFaktur.substring(kdFaktur.indexOf('.') + 1);
		
		Object bp_Obj = freeColVals.get(COL_BPARTNER);
		int bp_ID = (Integer) bp_Obj;
		sql = "SELECT IsPKP FROM C_BPartner WHERE C_BPartner_ID=?";
		String VATStatus = DB.getSQLValueStringEx(m_trxName, sql, bp_ID);
		boolean isVATPartner = VATStatus.equalsIgnoreCase("Y")? true : false;
		
		int org_ID = 0;
		
		if (isSR) {
			org_ID = FL_ORG_MAP.get("SR");
			m_currentDivisionPrefix = PREFIX_SR;
		}
		else if (division_ID == DIV_MAS) {
			org_ID = FL_ORG_MAP.get(PREFIX_MAS + "-" + depoStr);
			m_currentDivisionPrefix = PREFIX_MAS;
		}
		else if (division_ID == DIV_FL) {
			org_ID = FL_ORG_MAP.get(PREFIX_FL + "-" + depoStr);
			m_currentDivisionPrefix = PREFIX_FL;
		}
		else if (division_ID == DIV_DUDMAS) 
		{
			if (isVATPartner) {
				org_ID = FL_ORG_MAP.get(PREFIX_MAS + "-" + depoStr);
				m_currentDivisionPrefix = PREFIX_MAS;
			}
			else {
				org_ID = FL_ORG_MAP.get(PREFIX_SR + "-" + depoStr);
				m_currentDivisionPrefix = PREFIX_SR;
			}
		}
		else if (division_ID == DIV_SR) {
			org_ID = FL_ORG_MAP.get(PREFIX_SR + "-" + depoStr);
			m_currentDivisionPrefix = PREFIX_SR;
		}
		
		if (org_ID == 0)
			return "Cannot find organization id for kd_faktur: " + freeColVals.get(COL_KD_FAKTUR) + 
					" for product of: " + invLine.getM_Product();
		
		invLine.setAD_Org_ID(org_ID);
		
		String msg = analyzeInvoiceOrg(invLine, freeColVals);
		
		if (msg != null)
			return msg;
		
		recalculateInvoiceLine(m_invoice, invLine, freeColVals, division_ID, isVATPartner);
		
		if (isSOTrx && consolidator != null) {
			if (consolidator.invoice1 == null)
				consolidator.invoice1 = m_invoice;
			else if (consolidator.invoice2 == null)
				consolidator.invoice2 = m_invoice;
			else if (consolidator.invoice3 == null)
				consolidator.invoice3 = m_invoice;
			else if (consolidator.invoice4 == null)
				consolidator.invoice4 = m_invoice;
		}
		
		return null;
	} // beforeSave
	
	protected String recalculateInvoiceLine(
			MInvoice invoice, MInvoiceLine invLine, 
			Hashtable<String, Object> freeColVals, int division_ID, boolean isVATPartner)
	{
		m_invoice = invoice;
		m_IsCurrTrxImport = (Boolean) freeColVals.get(COL_IS_CURR_TRX);
		m_freeColVals = freeColVals;
		boolean isSOTrx = (Boolean) freeColVals.get(COL_ISSOTRX);
		BigDecimal totalPPN = (BigDecimal) freeColVals.get(COL_PPN);
		boolean isSR = (!isSOTrx && totalPPN.signum() == 0);
		boolean isCountSisa = ((Timestamp) freeColVals.get(COL_DOCDATE)).before(CUTOFFSisaDAY);
		
		BigDecimal grandTotal = (BigDecimal) freeColVals.get(COL_GRANDTOTAL);
		BigDecimal sisa = (BigDecimal) freeColVals.get(COL_SISA);
		BigDecimal addDiscount = (BigDecimal) freeColVals.get(COL_ADDITIONAL_DISCOUNT);
		BigDecimal discountLine = (BigDecimal) freeColVals.get(COL_DISCOUNTLINE);
		BigDecimal priceList = getPriceList(invLine);
		BigDecimal qty = getQty(invLine);
		BigDecimal oriPL = (BigDecimal) freeColVals.get(COL_PRICE);
		BigDecimal oriQty = (BigDecimal) freeColVals.get(COL_QTY);
		BigDecimal totalPrice = oriPL.multiply(oriQty).subtract(discountLine);
		
		boolean isPPNOrg = (division_ID == DIV_MAS || division_ID == DIV_FL);

		if (priceList.signum() == 0 || totalPrice.signum() <= 0)
		{
			invLine.setisProductBonuses(true);
		}
		else {			
			if (!m_IsCurrTrxImport && isCountSisa)
			{
				BigDecimal prosentase = Env.ONE;
				
				prosentase = sisa.divide(grandTotal, 10, BigDecimal.ROUND_UP);
				addDiscount = 
						prosentase.multiply(addDiscount).setScale(2, BigDecimal.ROUND_DOWN);
				
				qty = prosentase.multiply(qty);
				discountLine = discountLine.multiply(prosentase);
			}
			
			if (isSOTrx)
			{
				boolean isToNonPPNCust = !isVATPartner;
				boolean isCN = (Boolean) freeColVals.get(COL_IsCN);
				if (isPPNOrg && (isToNonPPNCust || isCN))
				{
					BigDecimal targetLineAmt = qty.multiply(priceList).subtract(discountLine);
					
					priceList = priceList.divide(new BigDecimal(1.1), 10, BigDecimal.ROUND_UP);
					
					if (discountLine.signum() > 0)
					{
						BigDecimal targetB4Tax = targetLineAmt.divide(new BigDecimal(1.1), 10, BigDecimal.ROUND_HALF_UP);
								
						discountLine = qty.multiply(priceList).subtract(targetB4Tax);
					}
				}
				else if (!isPPNOrg && !isToNonPPNCust) // Bukan PPN Org (i.e. SR) tapi ke PPN Customer.
				{
					BigDecimal targetLineAmt = qty.multiply(priceList).subtract(discountLine).multiply(new BigDecimal(1.1));
					
					priceList = priceList.multiply(new BigDecimal(1.1));
					discountLine = qty.multiply(priceList).subtract(targetLineAmt);
				}
			}
			else {
				int BP_ID = (Integer) freeColVals.get(COL_BPARTNER);
				
				if (!isSR) {
					if ((!isPPNOrg && totalPPN.signum() > 0) ||  BP_ID == 1000051 )
					{
						BigDecimal targetLineAmt = 
								qty.multiply(priceList).subtract(discountLine).multiply(new BigDecimal(1.1));
						
						priceList = priceList.multiply(new BigDecimal(1.1));
						discountLine = qty.multiply(priceList).subtract(targetLineAmt);
					}
				}
			}
		}
		
		m_invoice.setAddDiscountAmt(addDiscount);
		
		m_invoice.saveEx();
		
		invLine.setQty(qty);
		invLine.setDiscountAmt(discountLine);
		invLine.setPriceList(priceList);
		invLine.setPrice(priceList);
		
		invLine.setC_Invoice_ID(m_invoice.get_ID());

		return null;
	}
	
	/**
	 * 
	 * @param inv
	 * @param invLine
	 * @param freeColVals
	 * @return
	 */
	private String analyzeInvoiceOrg(MInvoiceLine invLine, Hashtable<String, Object> freeColVals)
	{
		String kdFaktur = (String) freeColVals.get(COL_KD_FAKTUR);
		int orgID = invLine.getAD_Org_ID();
		//invLine.setAD_Org_ID(orgID);
		
		Object oInv = m_poRefMap.get(kdFaktur + "-" + orgID);
		MInvoice inv = null;
		kdFaktur = "XX-" + kdFaktur + "--" + m_currentDivisionPrefix;
		
		if (oInv == null)
		{
			String whereClause = MInvoice.COLUMNNAME_DocumentNo + "=? AND AD_Org_ID=?";  
			inv = Query.get(m_ctx, UNSFinanceModelFactory.EXTENSION_ID, MInvoice.Table_Name, whereClause, m_trxName)
					.setParameters(kdFaktur, orgID).first();
			
			if (inv == null)
				inv = createNewHeader(invLine, freeColVals);
			
			m_poRefMap.put(kdFaktur + "-" + orgID, inv);
		}
		else 
			inv = (MInvoice) oInv;
		
		if (inv == null)
			return "Failed when trying to create new invoice for invoice number " + kdFaktur;
		
		m_invoice = inv;
		
		return null;
	}
	
	/**
	 * Get the quantity of the invoiceLine.
	 * @param invLine
	 * @return
	 */
	private BigDecimal getQty(MInvoiceLine invLine)
	{
		BigDecimal qtyOfLowestUoM = (BigDecimal) m_freeColVals.get(COL_QTY);
		BigDecimal qty = qtyOfLowestUoM;
		
		MProduct product = MProduct.get(m_ctx, invLine.getM_Product_ID());
		
		BigDecimal lowestToBaseConvQty = product.getConversionQtyLowestToBase();
		
		qty = qtyOfLowestUoM.divide(lowestToBaseConvQty, 5, BigDecimal.ROUND_DOWN);
		
		return qty;
	}

	/**
	 * Get the price list of the invoiceLine.
	 * @param invLine
	 * @return
	 */
	private BigDecimal getPriceList(MInvoiceLine invLine)
	{
		BigDecimal priceOfLowestUoM = (BigDecimal) m_freeColVals.get(COL_PRICE);
		
		if (priceOfLowestUoM.signum() == 0)
			return Env.ZERO;
		
		BigDecimal price = priceOfLowestUoM;
		
		MProduct product = MProduct.get(m_ctx, invLine.getM_Product_ID());
		
		BigDecimal lowestToBaseConvQty = product.getConversionQtyLowestToBase();
		
		price = priceOfLowestUoM.multiply(lowestToBaseConvQty);
		
		return price;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
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
	public String afterSaveAllRow(Hashtable<String, PO> poRefMap, PO[] pos) 
	{
		int docType_ID = 0;
		
		Calendar cutOffCal = Calendar.getInstance();
		cutOffCal.set(2014, 11, 31, 0, 0, 0);
		cutOffCal.set(Calendar.MILLISECOND, 0);
		Timestamp cutOffDate = new Timestamp(cutOffCal.getTimeInMillis());

		/**
		String sql = "SELECT C_DocType_ID FROM C_DocType WHERE Name=? AND AD_Client_ID=" + pos[0].getAD_Client_ID();
		if (pos[0].get_ValueAsBoolean(MInvoice.COLUMNNAME_IsSOTrx)) {
			docType_ID = DB.getSQLValueEx(m_trxName, sql, "MM Shipment");
		}
		else {
			docType_ID = DB.getSQLValueEx(m_trxName, sql, "MM Receipt");
		}
		*/
		
		List<MInvoice> allInvoicesWithAdditionalDiscount = new ArrayList<>();
		
		Collection<PO> valCollections = poRefMap.values();
		
		int countPOToProcess = valCollections.size();
		if (m_processMonitor != null)
			m_processMonitor.statusUpdate("Number of PO to be processed afterSavingAllRows : " + countPOToProcess);
		
		for (PO po : valCollections)
		{
			String errMsg = null;
			
			InvoiceImporterConsolidator_v2 consolidator;
			if (po instanceof InvoiceImporterConsolidator_v2) 
			{
				consolidator = (InvoiceImporterConsolidator_v2) po;
				
				if (m_processMonitor != null)
					m_processMonitor.statusUpdate("Processing InvoiceConsolidator for faktur of : " + consolidator.kdFaktur);
				Log.warn("Processing InvoiceConsolidator for faktur of : " + consolidator.kdFaktur);

				if (!consolidator.isHasUDMASProduct)
					continue;
				
				errMsg = consolidator.consolidateInvoiceLines();
				if (errMsg != null)
					errMsg = "Error while consolidating UDMAS Product into proper invoice. No Faktur : " 
							 + consolidator.kdFaktur +"\n ErrMsg: " + errMsg ;
				
				if (consolidator.m_createdInvoice != null 
						&& consolidator.m_createdInvoice.getAddDiscountAmt().signum() > 0)
					allInvoicesWithAdditionalDiscount.add(consolidator.m_createdInvoice);
			}
			else 
			{
				MInvoice theInvoice = (MInvoice) po;
				
				if (theInvoice.getDateAcct().before(cutOffDate))
					continue;
				
				if (m_processMonitor != null)
					m_processMonitor.statusUpdate("Reprocessing faktur of : " + theInvoice.getDocumentNo());
				Log.warn("Reprocessing faktur of : " + theInvoice.getDocumentNo());
				
				if (theInvoice.getAddDiscountAmt().signum() > 0)
					allInvoicesWithAdditionalDiscount.add(theInvoice);
				
				if (docType_ID == 0)
					docType_ID = analyzeIODocType(theInvoice);
				
				errMsg = createShipReceiptFromInvoice(theInvoice, docType_ID);
			}
			
			if (errMsg != null)
				return errMsg;
		}
		
		// -- Update all invoice's discount amount.
		for (MInvoice invoice : allInvoicesWithAdditionalDiscount)
		{
			String errMsg = updateInvoiceAdditionalDiscount(m_ctx, invoice.get_ID(), m_trxName);
			if (errMsg != null)
				return errMsg;
		}
		
		return null;
	} // beforeSaveAllRow
	
	private int analyzeIODocType(MInvoice inv)
	{
		int C_DocType_ID = MM_RECEIPT;
		if (inv.getC_DocType_ID() == AR_INVOICE)
			C_DocType_ID = MM_SHIPMENT;
		else if (inv.getC_DocType_ID() == AR_CREDITMEMO)
			C_DocType_ID = MM_CUSTOMER_RETURN;
		else if (inv.getC_DocType_ID() == AP_CREDITMEMO)
			C_DocType_ID = MM_VENDOR_RETURN;
		return C_DocType_ID;
	} // analyzeIODocType
	
	String updateInvoiceAdditionalDiscount(Properties ctx, int C_Invoice_ID, String trxName)
	{
		MInvoice invoice = new MInvoice(ctx, C_Invoice_ID, trxName);
		// -- Update the additional discount based on the invoice's total amount.
		BigDecimal invoiceAmt = invoice.getTotalLines();
		BigDecimal invoiceAddDiscAmt = invoice.getAddDiscountAmt();
		
		String sql = "SELECT SUM(TotalLines) FROM C_Invoice WHERE ReferenceNo=?";
		
		BigDecimal totalInvoiceAmt = DB.getSQLValueBD(trxName, sql, invoice.getReferenceNo());
		
		BigDecimal invPortion = invoiceAmt.divide(totalInvoiceAmt, 10, BigDecimal.ROUND_HALF_DOWN);
		BigDecimal addDiscPortionAmt = 
				invPortion.multiply(invoiceAddDiscAmt).setScale(2, BigDecimal.ROUND_HALF_DOWN);
		
		invoice.setAddDiscountAmt(addDiscPortionAmt);
		
		if (!invoice.save())
			return "Failed when updating additional discount amount for invoice no: " + invoice.getReferenceNo();

		return null;
	} // updateInvoiceAdditionalDiscount

	static String createShipReceiptFromInvoice(MInvoice theInvoice, int docType_ID)
	{
		String trxName 	= theInvoice.get_TrxName();
		Properties ctx	= theInvoice.getCtx();
		
		MInOut io = new Query(ctx, MInOut.Table_Name, "C_Invoice_ID=? AND IsSOTrx=?", trxName)
		.setParameters(theInvoice.get_ID(), theInvoice.isSOTrx())
		.first();
	
		String orgValue = MOrg.get(ctx, theInvoice.getAD_Org_ID()).getValue();
		int invOrg_ID = theInvoice.getAD_Org_ID();
		
		if (io == null) 
		{
			String sql = "SELECT M_Warehouse_ID FROM M_Warehouse WHERE Value=?";
			
			String whName = PREFIX_WH_GOOD + orgValue;
			
			if (orgValue.equals("SNR"))
				whName = "G.SparePart.SNR";
			else if (theInvoice.getC_DocType_ID() == AR_CREDITMEMO
					|| theInvoice.getC_DocType_ID() == AP_CREDITMEMO)
				whName = PREFIX_WH_SC + orgValue;
			
			int wh_ID = DB.getSQLValueEx(trxName, sql, whName);
					
			io = new MInOut(theInvoice, docType_ID, theInvoice.getDateAcct(), wh_ID);
			io.setMovementType(InvoiceV2ImporterValidation.analyzeMovementType(docType_ID));
			io.setAD_Org_ID(invOrg_ID);
			io.setDocumentNo(theInvoice.getDocumentNo());
			io.setC_Invoice_ID(theInvoice.get_ID());
			io.saveEx();
		}
		else {
			String sql = "UPDATE C_InvoiceLine SET M_InOutLine_ID=NULL WHERE C_Invoice_ID=" + theInvoice.get_ID();
			DB.executeUpdateEx(sql, trxName);
			
			sql = "DELETE FROM M_InOutLine WHERE M_InOut_ID=" + io.get_ID();
			DB.executeUpdateEx(sql, trxName);
		}
		
		String sql = "SELECT M_Locator_ID FROM M_Locator WHERE Value=? ";

		String locValue = PREFIX_LOCATOR_GOOD + orgValue;
		if (orgValue.equals("SNR"))
			locValue = "LOC-SparePart-SNR";
		else if (theInvoice.getC_DocType_ID() == AR_CREDITMEMO
				|| theInvoice.getC_DocType_ID() == AP_CREDITMEMO)
			locValue = PREFIX_LOCATOR_SC + orgValue;
		
		int M_Locator_ID = DB.getSQLValueEx(trxName, sql, locValue);

		for (MInvoiceLine il : theInvoice.getLines(true))
		{
			MInOutLine iol = new MInOutLine(io);
			
			//iol.setM_InOut_ID(io.get_ID());
			iol.setM_Product_ID(il.getM_Product_ID());
			iol.setC_UOM_ID(il.getC_UOM_ID());

			if (!io.isSOTrx() || io.getC_DocType_ID() == MM_CUSTOMER_RETURN)
				MAttributeSetInstance.initAttributeValuesFrom(
						iol, MInOutLine.COLUMNNAME_M_Product_ID, 
						MInOutLine.COLUMNNAME_M_AttributeSetInstance_ID, trxName);
			
			iol.setInvoiceLine(il, M_Locator_ID, il.getQtyInvoiced());
			iol.setQty(il.getQtyInvoiced());
			
			iol.saveEx();
			
			il.setM_InOutLine_ID(iol.get_ID());
			il.saveEx();
		}
		
		return null;
	} // createShipmentFromInvoice
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#getPOReferenceMap()
	 */
	@Override
	public Hashtable<String, PO> getPOReferenceMap() {
		return m_poRefMap;
	}
	
	/**
	 * 
	 * @param poRefMap
	 * @param invLine
	 * @param freeColVals
	 * @return
	 */
	protected MInvoice createNewHeader(MInvoiceLine invLine, Hashtable<String, Object> freeColVals)
	{
		MInvoice header = new MInvoice(m_ctx, 0, m_trxName);
		header.setAD_Org_ID(invLine.getAD_Org_ID());
		header.setDateAcct((Timestamp) freeColVals.get(COL_DOCDATE));
		header.setDateInvoiced((Timestamp) freeColVals.get(COL_DOCDATE));
		boolean isCountSisa = ((Timestamp) freeColVals.get(COL_DOCDATE)).before(CUTOFFSisaDAY);
		
		
		boolean isSOTrx = (Boolean) freeColVals.get(COL_ISSOTRX);
		boolean isCN = (Boolean) freeColVals.get(COL_IsCN);
		
		int C_DocType_ID = AP_INVOICE;
		if (isSOTrx && !isCN)
			C_DocType_ID = AR_INVOICE;
		else if (isSOTrx && isCN)
			C_DocType_ID = AR_CREDITMEMO;
		else if (!isSOTrx && isCN)
			C_DocType_ID = AP_CREDITMEMO;
			
		String invReffNo = (String) freeColVals.get(COL_KD_FAKTUR);
		
		header.setReferenceNo(invReffNo);
		header.setC_DocType_ID(C_DocType_ID);
		header.setC_DocTypeTarget_ID(C_DocType_ID);
		header.setDocumentNo("XX-" + invReffNo + "--" + m_currentDivisionPrefix);

		Timestamp docDate = (Timestamp) freeColVals.get(COL_DOCDATE);
		Timestamp dueDate = (Timestamp) freeColVals.get(COL_DUEDATE);
		
		int TOP = UNSTimeUtil.getDaysBetween(docDate, dueDate);
		
		//String paymentRule = MInvoice.PAYMENTRULE_OnCredit;
		
		if (TOP == 0) {
			header.setPaymentRule(MInvoice.PAYMENTRULE_Cash);
			//paymentRule = MInvoice.PAYMENTRULE_Cash;
		}
		else {
			String topValue = "TOP-" + TOP + "D";
			String sql = "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE Value=?";
			
			int paytermID = DB.getSQLValueEx(m_trxName, sql, topValue);
			
			header.setPaymentRule(MInvoice.PAYMENTRULE_OnCredit);
			header.setC_PaymentTerm_ID(paytermID);
		}
		
		header.setSalesRep_ID((Integer) freeColVals.get(COL_SALES));
		header.setC_Currency_ID((Integer) freeColVals.get(COL_CURRS_ID));
		header.setIsSOTrx(isSOTrx);
		header.setDescription("No Faktur : " + invReffNo);
		Object bp_Obj = freeColVals.get(COL_BPARTNER);
		int bp_ID = (Integer) bp_Obj;
		header.setC_BPartner_ID(bp_ID);
		
		if (isSOTrx)
			header.setC_BPartner_Location_ID((Integer) freeColVals.get(COL_BP_LOCATION));
		else {
			String sql = "SELECT C_BPartner_Location_ID FROM C_BPartner_Location WHERE C_BPartner_ID=?";
			int[] bpLoc_IDs = DB.getIDsEx(m_trxName, sql, header.getC_BPartner_ID());
			
			if (bpLoc_IDs != null && bpLoc_IDs.length > 0)
				header.setC_BPartner_Location_ID(bpLoc_IDs[0]);
		}
		
		BigDecimal grandTotal = (BigDecimal) freeColVals.get(COL_GRANDTOTAL);
		BigDecimal addDiscount = (BigDecimal) freeColVals.get(COL_ADDITIONAL_DISCOUNT);
		boolean isCurrentTrxImport = (Boolean) freeColVals.get(COL_IS_CURR_TRX);
		if (!isCurrentTrxImport && isCountSisa)
		{
			BigDecimal sisa = (BigDecimal) freeColVals.get(COL_SISA);
			addDiscount = 
					sisa.divide(grandTotal, 10, BigDecimal.ROUND_UP)
					.multiply(addDiscount).setScale(2, BigDecimal.ROUND_DOWN);
		}
		
		header.setAddDiscountAmt(addDiscount);
		
		header.saveEx();
		
		return header;
	}
}

class InvoiceImporterConsolidator_v2 extends X_C_Invoice
{
	private static final long serialVersionUID = 3179687309496904284L;
	
	static final Hashtable<Integer, Integer> PRODUCTION_ORG_WH = new Hashtable<>();
	static final Hashtable<Integer, Integer> PRODUCTION_ORG_LOC = new Hashtable<>();
	
	static {
		PRODUCTION_ORG_WH.put(1000008, 1000009); // Warehouse BHN
		PRODUCTION_ORG_WH.put(1000009, 1000011); // Warehouse SBN
		PRODUCTION_ORG_WH.put(1000010, 1000010); // Warehouse TEH

		PRODUCTION_ORG_LOC.put(1000008, 1000023); // Locator BHN
		PRODUCTION_ORG_LOC.put(1000009, 1000031); // Locator SBN
		PRODUCTION_ORG_LOC.put(1000010, 1000034); // Locator TEH
	}
	
	List<MInvoiceLine> 				invLines = new ArrayList<>();
	List<Hashtable<String, Object>> freeColsVals = new ArrayList<>();
	List<Integer>					lineDivisions = new ArrayList<>();
	List<Boolean>					lineUDMASStatus = new ArrayList<>();
	
	boolean 	isHasUDMASProduct = false;
	boolean		isHasNonUDMASProduct = false;
	String 		kdFaktur;
	MInvoice 	invoice1;
	MInvoice 	invoice2;
	MInvoice 	invoice3;
	MInvoice 	invoice4;
	Properties 	ctx;
	String		trxName;
	int 		purchaserOrg_ID;
	MInvoice	m_createdInvoice = null;

	InvoiceImporterConsolidator_v2(Properties ctx, int id, String trxName)
	{
		super(ctx, 0, trxName);
		this.ctx = ctx;
		this.trxName = trxName;
	}
	
	protected String consolidateInvoiceLines()
	{
		if (!isHasUDMASProduct)
			return null;
		
		InvoiceV2ImporterValidation_v2 invoiceValidation = new InvoiceV2ImporterValidation_v2(ctx, null, trxName);
		
		Hashtable<String, Object> freeColVals = freeColsVals.get(0);
		
		int C_BPartner_ID = (Integer) freeColVals.get(InvoiceV2ImporterValidation_v2.COL_BPARTNER);
		String sql = "SELECT IsPKP FROM C_BPartner WHERE C_BPartner_ID=?";
		String VATStatus = DB.getSQLValueStringEx(trxName, sql, C_BPartner_ID);
		boolean isVATCustomer = VATStatus.equalsIgnoreCase("Y")? true : false;
		
		BigDecimal totalPPN = (BigDecimal) freeColVals.get(InvoiceV2ImporterValidation_v2.COL_PPN);
		
		String depoStr = (String) freeColVals.get(InvoiceV2ImporterValidation_v2.COL_KD_FAKTUR);
		depoStr = depoStr.substring(depoStr.length() - 3);
		
		int org_ID = 0;
		MInvoice inv = null;
		
		Hashtable<Integer, Boolean> UDMASStatusMap = new Hashtable<>();
		int BPOfSROrMAS_ID = 0;
		
		// -- Organization decision here. Jk ke PPN Customer, maka barang UDMAS harus dijual melalui PT. MAS,
		// -- jk ke Non PPN harus dijual melalui UD. SR. Jika barang2 non UDMAS organization nya sudah ke PT. MAS, 
		// -- atau ke SR, maka satukan fakturnya, jika tidak maka buat invoice (header) baru.
		boolean isNewOrgInvoice = false;
		
		for (int i=0; i < invLines.size(); i++)
		{
			MInvoiceLine invLine = invLines.get(i);
			freeColVals = freeColsVals.get(i);
			
			boolean isUDMASLine = lineUDMASStatus.get(i);
			
			if (invLine.get_ID() > 0)
				UDMASStatusMap.put(invLine.get_ID(), isUDMASLine);
			
			if (!isUDMASLine)
				continue;
			
			if (org_ID == 0)
			{
				sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE Value=?";
				if (isVATCustomer || totalPPN.signum() > 0) {
					org_ID = InvoiceV2ImporterValidation_v2.FL_ORG_MAP.get(
							InvoiceV2ImporterValidation_v2.PREFIX_MAS + "-" + depoStr);
					BPOfSROrMAS_ID = DB.getSQLValueEx(trxName, sql, InvoiceV2ImporterValidation_v2.PREFIX_MAS);
					invoiceValidation.m_currentDivisionPrefix = InvoiceV2ImporterValidation_v2.PREFIX_MAS;
				}
				else {
					org_ID = InvoiceV2ImporterValidation_v2.FL_ORG_MAP.get(
							InvoiceV2ImporterValidation_v2.PREFIX_SR + "-" + depoStr);
					BPOfSROrMAS_ID = DB.getSQLValueEx(trxName, sql, InvoiceV2ImporterValidation_v2.PREFIX_SR);
					invoiceValidation.m_currentDivisionPrefix = InvoiceV2ImporterValidation_v2.PREFIX_SR;
				}
				
				purchaserOrg_ID = org_ID;
			}
			
			invLine.setAD_Org_ID(org_ID);
			invLine.setTax();
			
			if (inv == null)
			{
				if (invoice1 != null && invoice1.getAD_Org_ID() == org_ID)
					inv = invoice1;
				else if (invoice2 != null && invoice2.getAD_Org_ID() == org_ID)
					inv = invoice2;
				else if (invoice3 != null && invoice3.getAD_Org_ID() == org_ID)
					inv = invoice3;
				else if (invoice4 != null && invoice4.getAD_Org_ID() == org_ID)
					inv = invoice4;

				if (inv == null) {
					String kdFaktur = (String) freeColVals.get(InvoiceV2ImporterValidation_v2.COL_KD_FAKTUR);
					
					kdFaktur = "XX-" + kdFaktur + "--" + invoiceValidation.m_currentDivisionPrefix;
					
					String whereClause = MInvoice.COLUMNNAME_DocumentNo + "=? AND AD_Org_ID=?";  
					inv = Query.get(ctx, UNSFinanceModelFactory.EXTENSION_ID, 
									MInvoice.Table_Name, whereClause, trxName)
							.setParameters(kdFaktur, org_ID)
							.first();
					
					if (inv == null)
						inv = invoiceValidation.createNewHeader(invLine, freeColVals);
					
					isNewOrgInvoice = true;
					m_createdInvoice = inv;
				}
			}
			
			invoiceValidation.recalculateInvoiceLine(inv, invLine, freeColVals, lineDivisions.get(i), isVATCustomer);
			invLine.setC_Invoice_ID(inv.get_ID());
			
			invLine.saveEx();
			
			UDMASStatusMap.put(invLine.get_ID(), isUDMASLine);
		}
		
		if (inv == null)// || !depoStr.equals(InvoiceV2ImporterValidation.PREFIX_MAS))
			return null;
		
		//--- Create AP Invoice of MAS and AR Invoice of UDMAS.
		int BP_UDMAS_ID = 1011109;
		sql = "SELECT C_DocType_ID FROM C_DocType WHERE Name=? AND AD_Client_ID=" + inv.getAD_Client_ID();
		int DocTypeMMShipment_ID = DB.getSQLValueEx(trxName, sql, "MM Shipment");
		int DocTypeMMReceipt_ID = DB.getSQLValueEx(trxName, sql, "MM Receipt");
		
		if (isNewOrgInvoice) {
			inv = new MInvoice(ctx, inv.get_ID(), trxName);
			InvoiceV2ImporterValidation_v2.createShipReceiptFromInvoice(inv, DocTypeMMShipment_ID);
		}
		
		String invReffNo = (String) freeColVals.get(InvoiceV2ImporterValidation_v2.COL_KD_FAKTUR);		
		invReffNo = "XX-" + invReffNo + "-Auto-InterOrgTrx";
		
		Object[] params = new Object[] {invReffNo};
		
		sql = "UPDATE C_InvoiceLine SET M_InOutLine_ID=NULL "
				+ "WHERE C_Invoice_ID IN (SELECT C_Invoice_ID FROM C_Invoice "
				+ "		WHERE DocumentNo=?)";
		int count = DB.executeUpdateEx(sql, params, trxName);
		log.info(count + " invoice lines unlinked from its InOutLine for document no of " + invReffNo);
		
		sql = "DELETE FROM M_InOut WHERE DocumentNo=?";
		count = DB.executeUpdateEx(sql, params, trxName);
		log.info(count + " records of InOut documents deleted for faktur no " + invReffNo);
		
		sql = "DELETE FROM C_PaymentAllocate pa WHERE pa.C_Payment_ID IN ("
				+ "		SELECT p.C_Payment_ID FROM C_Payment p WHERE p.DocumentNo=?)";
		count = DB.executeUpdateEx(sql, params, trxName);
		log.info(count + " records of Payment Allocation(s) deleted for faktur no " + invReffNo);

		sql = "DELETE FROM C_Payment WHERE DocumentNo=?";
		count = DB.executeUpdateEx(sql, params, trxName);
		log.info(count + " records of Payment(s) deleted for faktur no " + invReffNo);
		
		sql = "DELETE FROM C_Invoice WHERE DocumentNo=?";
		count = DB.executeUpdateEx(sql, params, trxName);
		log.info(count + " records of auto generated inter-org invoice transaction deleted for faktur no " + invReffNo);
		
		// -- Faktur Pembelian (AP) ke vendor UDMAS.
		MInvoice APInvoice = createNewInvoice(org_ID, freeColVals, false, BP_UDMAS_ID);
		
		sql = "SELECT M_Warehouse_ID FROM M_Warehouse "
				+ "WHERE Value=(? || (SELECT org.Value FROM AD_Org org WHERE org.AD_Org_ID=?))";
		
		int wh_ID = DB.getSQLValueEx(trxName, sql, InvoiceV2ImporterValidation_v2.PREFIX_WH_GOOD, org_ID);
				
		MInOut receipt = new MInOut(APInvoice, DocTypeMMReceipt_ID, APInvoice.getDateAcct(), wh_ID);
		receipt.setMovementType(InvoiceV2ImporterValidation.analyzeMovementType(APInvoice));
		receipt.setDocumentNo(APInvoice.getDocumentNo());
		receipt.setC_Invoice_ID(APInvoice.get_ID());
		receipt.setDescription("***Auto-Inter-org selling-purchasing transaction***");
		receipt.saveEx();

		sql = "SELECT M_Locator_ID FROM M_Locator "
				+ "WHERE Value=(? || (SELECT org.Value FROM AD_Org org WHERE org.AD_Org_ID=?))";
		
		int locator_ID = DB.getSQLValueEx(trxName, sql, InvoiceV2ImporterValidation_v2.PREFIX_LOCATOR_GOOD, org_ID);
				
		Hashtable<Integer, List<MInvoiceLine>> buyFromMap = new Hashtable<>();
		
		// -- SR atau MAS beli (buat AP Invoice) ke BPartner UDMAS. 
		for (MInvoiceLine invLine : inv.getLines(true))
		{
			if (!UDMASStatusMap.get(invLine.get_ID()))
				continue;
			
			sql = "SELECT rio.AD_Org_ID FROM UNS_Resource_InOut rio WHERE (rio.M_Product_ID=? AND "
					+ " rio.UNS_Resource_ID IN (SELECT rsc.UNS_Resource_ID FROM UNS_Resource rsc "
					+ "		WHERE rsc.ResourceType='PL')) OR (rio.M_Product_ID IN "
					+ "		(SELECT bom.M_Product_ID FROM M_Product_BOM bom where bom.M_ProductBOM_ID=?)) ";
			
			int AD_Org_ID = DB.getSQLValueEx(trxName, sql, invLine.getM_Product_ID(), invLine.getM_Product_ID());
			
			if (AD_Org_ID <= 0)
			{
				sql = "SELECT pc.Name FROM M_Product_Category pc "
						+ "WHERE pc.M_Product_Category_ID=(SELECT p.M_Product_Category_ID FROM M_Product p "
						+ "		WHERE p.M_Product_ID=?)";
				String pcName = DB.getSQLValueString(trxName, sql, invLine.getM_Product_ID());
				
				if (pcName == null)
					return "Failed: Cannot find proper UDMAS Organization for product of " + invLine.getM_Product();
				else if (pcName.toUpperCase().indexOf("TEH") >= 0)
					AD_Org_ID = 1000010;
				else if (pcName.toUpperCase().indexOf("SABUN") >= 0)
					AD_Org_ID = 1000009;
				else //if (pcName.toUpperCase().indexOf("SABUN") >= 0)
					AD_Org_ID = 1000008;
			}
			
			List<MInvoiceLine> buyFromList = buyFromMap.get(AD_Org_ID);
			
			if (buyFromList == null)
			{
				buyFromList = new ArrayList<>();
				buyFromMap.put(AD_Org_ID, buyFromList);
			}
			
			buyFromList.add(invLine);
			
			MInvoiceLine newAPLine = new MInvoiceLine(APInvoice);
			newAPLine.setM_Product_ID(invLine.getM_Product_ID());
			newAPLine.setC_UOM_ID(invLine.getC_UOM_ID());
			newAPLine.setQty(invLine.getQtyEntered());
			newAPLine.setPrice();
			if (invLine.getDiscount().compareTo(new BigDecimal(99)) >= 0)
				newAPLine.setDiscount(Env.ONEHUNDRED);
			else {
				newAPLine.setDiscount(invLine.getDiscount());
			}
			/**
			BigDecimal discAmt = newAPLine.getQtyEntered().multiply(newAPLine.getPriceEntered())
					.multiply(newAPLine.getDiscount().divide(Env.ONEHUNDRED, 4, BigDecimal.ROUND_HALF_DOWN));
			newAPLine.setDiscountAmt(discAmt);
			*/
			newAPLine.setisProductBonuses(invLine.isProductBonuses());
			
			MInOutLine iol = new MInOutLine(receipt);
			iol.setM_Product_ID(newAPLine.getM_Product_ID());
			iol.setQty(newAPLine.getQtyEntered());
			iol.setC_UOM_ID(newAPLine.getC_UOM_ID());
			iol.setM_Locator_ID(locator_ID);
			iol.saveEx();
			
			newAPLine.setM_InOutLine_ID(iol.get_ID());
			newAPLine.saveEx();
		}
		
		// -- Create The AP Payment.
		APInvoice = new MInvoice(ctx, APInvoice.get_ID(), trxName);
		
		if (APInvoice.getGrandTotal().signum() > 0)
			createNewPayment(APInvoice);
		else {
			APInvoice.setIsPaid(true);
			APInvoice.saveEx();
		}
		
		// -- BHN, SBN, atau Teh jual ke BPartner of MAS or SR.
		for (int UDMAS_ID : buyFromMap.keySet())
		{
			MInvoice ARUDMAS = createNewInvoice(UDMAS_ID, freeColVals, true, BPOfSROrMAS_ID);
			log.info("=== UDMAS of " + UDMAS_ID + "; DocNo: " + ARUDMAS.getDocumentNo());
			MInOut shipment = new MInOut(
					ARUDMAS, DocTypeMMShipment_ID, ARUDMAS.getDateAcct(), PRODUCTION_ORG_WH.get(UDMAS_ID));
			shipment.setDocumentNo(ARUDMAS.getDocumentNo());
			shipment.setC_Invoice_ID(ARUDMAS.get_ID());
			shipment.setDescription("***Auto-Inter-org selling-purchasing transaction***");
			shipment.saveEx();

			for (MInvoiceLine oriInvLine : buyFromMap.get(UDMAS_ID))
			{
				MInvoiceLine newARLine = new MInvoiceLine(ARUDMAS);
				newARLine.setM_Product_ID(oriInvLine.getM_Product_ID());
				newARLine.setC_UOM_ID(oriInvLine.getC_UOM_ID());
				newARLine.setQty(oriInvLine.getQtyEntered());
				newARLine.setPrice();
				
				if (newARLine.getPriceEntered().signum() <= 0 )
				{
					BigDecimal price = oriInvLine.getPriceEntered().multiply(new BigDecimal(0.925))
							.setScale(2, BigDecimal.ROUND_HALF_UP);
					//newARLine.setPrice(price);
					newARLine.setPriceActual(price);
					newARLine.setPriceEntered(price);
					newARLine.setPriceList(price);
					newARLine.setPriceLimit(price);
				}
				
				newARLine.setisProductBonuses(oriInvLine.isProductBonuses());
				if (oriInvLine.isProductBonuses()) {
					newARLine.setDiscount(Env.ONEHUNDRED);
				} else {
					newARLine.setDiscount(oriInvLine.getDiscount());
				}
				
				MInOutLine iol = new MInOutLine(shipment);
				iol.setM_Product_ID(newARLine.getM_Product_ID());
				iol.setQty(newARLine.getQtyEntered());
				iol.setC_UOM_ID(newARLine.getC_UOM_ID());
				iol.setM_Locator_ID(PRODUCTION_ORG_LOC.get(UDMAS_ID));
				iol.saveEx();
				
				newARLine.setM_InOutLine_ID(iol.get_ID());
				newARLine.saveEx();
			}
			// -- Create The AR Payment.
			ARUDMAS = new MInvoice(ctx, ARUDMAS.get_ID(), trxName);
			if (ARUDMAS.getGrandTotal().signum() > 0)
				createNewPayment(ARUDMAS);
			else {
				ARUDMAS.setIsPaid(true);
				ARUDMAS.saveEx();
			}
		}
		
		return null;
	} // consolidateInvoiceLines
	
	//--Create payment document for the invoice.
	MPayment createNewPayment(MInvoice invoice)
	{
		MPayment payment = new MPayment(ctx, 0, trxName);
		
		payment.setC_DocType_ID(invoice.isSOTrx());
		payment.setAD_Org_ID(invoice.getAD_Org_ID());
		payment.setC_BPartner_ID(invoice.getC_BPartner_ID());
		payment.setPayAmt(invoice.getGrandTotal());
		payment.setC_Currency_ID(303);
		payment.setDescription("***Auto-Inter-org selling-purchasing transaction***");
		payment.setDocumentNo(invoice.getDocumentNo());

		payment.setDateTrx(invoice.getDateInvoiced());
		
		Timestamp dateAcct = TimeUtil.addDays(invoice.getDateInvoiced(), invoice.getC_PaymentTerm().getNetDays());
		payment.setDateAcct(dateAcct);
		payment.setTenderType(MPayment.TENDERTYPE_DirectDeposit);
		
		String sql = "SELECT C_BankAccount_ID FROM C_BankAccount "
				+ "WHERE Value=('Bank1-' || (SELECT org.Value FROM AD_Org org WHERE org.AD_Org_ID=?))";
		int bankAcc_ID = DB.getSQLValue(trxName, sql, invoice.getAD_Org_ID());
		
		payment.setC_BankAccount_ID(bankAcc_ID);
		
		payment.saveEx();
		
		MPaymentAllocate payAllocate = new MPaymentAllocate(ctx, 0, trxName);
		payAllocate.setAD_Org_ID(invoice.getAD_Org_ID());
		payAllocate.setC_Payment_ID(payment.get_ID());
		payAllocate.setC_Invoice_ID(invoice.get_ID());
		payAllocate.setAmount(invoice.getGrandTotal());
		payAllocate.setInvoiceAmt(invoice.getGrandTotal());
		payAllocate.setPayToOverUnderAmount(invoice.getGrandTotal());
		payAllocate.saveEx();
		
		return payment;
	}
	
	MInvoice createNewInvoice(int org_ID, Hashtable<String, Object> freeColVals, boolean isSOTrx, int BPartner_ID)
	{
		MInvoice header = new MInvoice(ctx, 0, trxName);
		header.setDescription("***Inter-org selling-purchasing transaction***");
		header.setAD_Org_ID(org_ID);
		header.setDateAcct((Timestamp) freeColVals.get(InvoiceV2ImporterValidation_v2.COL_DOCDATE));
		header.setDateInvoiced((Timestamp) freeColVals.get(InvoiceV2ImporterValidation_v2.COL_DOCDATE));
		header.setC_BPartner_ID(BPartner_ID);
		String sql = "SELECT C_BPartner_Location_ID FROM C_BPartner_Location WHERE C_BPartner_ID=?";
		int[] bpLoc_IDs = DB.getIDsEx(trxName, sql, header.getC_BPartner_ID());
		
		if (bpLoc_IDs != null && bpLoc_IDs.length > 0)
			header.setC_BPartner_Location_ID(bpLoc_IDs[0]);
		
		
		String invReffNo = (String) freeColVals.get(InvoiceV2ImporterValidation_v2.COL_KD_FAKTUR);
		
		//invReffNo += "-Auto-InterOrgTrx";
		invReffNo = "XX-" + invReffNo + "-Auto-InterOrgTrx";
		
		int C_DocType_ID = 1000005;
		int PL_ID = 1000092;
		if (isSOTrx) // isSOTrx
		{
			//invReffNo += "-InterOrgARTrx";
			C_DocType_ID = 1000002;
		}
		else {
			//invReffNo += "-Auto-InterOrgAPTrx";
			PL_ID = 1000010;
		}
		
		header.setReferenceNo(invReffNo);
		header.setC_DocType_ID(C_DocType_ID);
		header.setC_DocTypeTarget_ID(C_DocType_ID);
		header.setDocumentNo(invReffNo);
		header.setM_PriceList_ID(PL_ID);

		int TOP = 30;
		
		//String paymentRule = MInvoice.PAYMENTRULE_OnCredit;
		
		if (TOP == 0) {
			header.setPaymentRule(MInvoice.PAYMENTRULE_Cash);
			//paymentRule = MInvoice.PAYMENTRULE_Cash;
		}
		else {
			String topValue = "TOP-" + TOP + "D";
			sql = "SELECT C_PaymentTerm_ID FROM C_PaymentTerm WHERE Value=?";
			
			int paytermID = DB.getSQLValueEx(trxName, sql, topValue);
			
			header.setPaymentRule(MInvoice.PAYMENTRULE_OnCredit);
			header.setC_PaymentTerm_ID(paytermID);
		}
		
		header.setSalesRep_ID((Integer) freeColVals.get(InvoiceV2ImporterValidation_v2.COL_SALES));
		header.setC_Currency_ID((Integer) freeColVals.get(InvoiceV2ImporterValidation_v2.COL_CURRS_ID));
		header.setIsSOTrx(isSOTrx);
		header.setDescription("No Faktur : " + invReffNo);
		
		header.saveEx();
		
		return header;
	} // createNewInvoice
	
} // InvoiceImporterConsolidator
