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
import java.util.logging.Level;

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
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTransaction;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.importer.ImporterValidation;
import com.uns.util.UNSTimeUtil;

/**
 * @author menjangan
 *
 */
public class InvoiceVFixingReturImporterValidation implements ImporterValidation {
	
	private static CLogger log = CLogger.getCLogger(InvoiceVFixingReturImporterValidation.class);
	
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
	public InvoiceVFixingReturImporterValidation() {
		super();
	}
	
	public InvoiceVFixingReturImporterValidation(Properties ctx, Sheet sheet, String trxName)
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
//		List<MInvoice> invList = new Query(m_ctx, MInvoice.Table_Name, "C_DocType_ID=?", m_trxName)
//			.setParameters(1000004)
//			.list();
//		
//		for (MInvoice inv : invList)
//		{
//			MInvoiceLine[] ilines = inv.getLines(true);
//			int nextLineNo = 1;
//			
//			for (int i=0; i < ilines.length; i++)
//			{
//				MInvoiceLine il = ilines[i];
//				
//				if (i == 0)
//					nextLineNo = il.getLine();
//				else if (il.getLine() != nextLineNo) {
//					il.set_ValueNoCheck(MInvoiceLine.COLUMNNAME_Line, nextLineNo);
//					il.setProcessed(false);
//					il.saveEx();
//				}
//				nextLineNo++;
//			}
//		}
//		if (1==1)
//			return SimpleImportXLS.DONT_SAVE;
		
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
		//Boolean isCN = (Boolean) freeColVals.get(COL_IsCN);
		
		String depoStr = kdFaktur.substring(kdFaktur.indexOf('.') + 1);
		
		//Object bp_Obj = freeColVals.get(COL_BPARTNER);
		//int bp_ID = (Integer) bp_Obj;
		sql = "SELECT IsPKP FROM C_BPartner WHERE C_BPartner_ID=?";
		String VATStatus = DB.getSQLValueStringEx(m_trxName, sql, BP_ID);
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
				//boolean isCN = (Boolean) freeColVals.get(COL_IsCN);
				//if (isPPNOrg && (isToNonPPNCust || isCN))
				if (isPPNOrg && isToNonPPNCust)
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
		
		if (!m_invoice.getDocStatus().equals(MInvoice.DOCSTATUS_Drafted)) {
			m_invoice.setDocStatus(MInvoice.DOCSTATUS_Drafted);
			m_invoice.setDocAction(MInvoice.DOCACTION_Complete);
			m_invoice.setProcessed(false);
			m_invoice.setPosted(false);
		}
		m_invoice.saveEx();
		
		invLine.setQty(qty);
		invLine.setDiscountAmt(discountLine);
		invLine.setPriceList(priceList);
		invLine.setPrice(priceList);
		
		invLine.setC_Invoice_ID(m_invoice.get_ID());
		invLine.setProcessed(false);
		
		if (invLine.getM_InOutLine_ID() > 0) {
			MInOut io = new Query(
					m_ctx, MInOut.Table_Name, 
					"M_InOut_ID=(SELECT M_InOut_ID FROM M_InOutLine WHERE M_InOutLine_ID=?)", m_trxName)
					.setParameters(invLine.getM_InOutLine_ID())
					.first();
			
			if (io == null)
				return "Cannot find MInOut for InOutLine_ID of " + invLine.getM_InOutLine_ID() + " Invoice of " +
						m_invoice.getReferenceNo();
			
			io.setDocStatus(MInvoice.DOCSTATUS_Drafted);
			io.setDocAction(MInvoice.DOCACTION_Complete);
			io.setProcessed(false);
			io.setPosted(false);
			io.saveEx();
		}

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
		
		if (oInv == null)
		{
			String whereClause = MInvoice.COLUMNNAME_ReferenceNo + "=? AND AD_Org_ID=?";  
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
		
		if (docType_ID == 0)
			return null;
		
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
			
			MInvoice theInvoice = (MInvoice) po;
			
			if (theInvoice.getDateAcct().before(cutOffDate))
				continue;
			
			theInvoice.set_TrxName(m_trxName);
			
			if (m_processMonitor != null)
				m_processMonitor.statusUpdate("Reprocessing faktur of: " + theInvoice.getDocumentNo());
			log.info("Reprocessing faktur of : " + theInvoice.getDocumentNo());
			
			if (theInvoice.getAddDiscountAmt().signum() > 0)
				allInvoicesWithAdditionalDiscount.add(theInvoice);
			
			if (docType_ID == 0)
				docType_ID = analyzeIODocType(theInvoice);
			
			errMsg = createShipReceiptFromInvoice(theInvoice, docType_ID);
			
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
	
	static String analyzeMovementType(MInvoice inv)
	{
		String movementType = MInOut.MOVEMENTTYPE_VendorReceipts;
		if (inv.getC_DocType_ID() == AR_INVOICE)
			movementType = MInOut.MOVEMENTTYPE_CustomerShipment;
		else if (inv.getC_DocType_ID() == AR_CREDITMEMO)
			movementType = MInOut.MOVEMENTTYPE_CustomerReturns;
		else if (inv.getC_DocType_ID() == AP_CREDITMEMO)
			movementType = MInOut.MOVEMENTTYPE_VendorReturns;
		return movementType;
	} // analyzeIODocType
	
	static String analyzeMovementType(int IODocType_ID)
	{
		String movementType = MInOut.MOVEMENTTYPE_VendorReceipts;
		if (IODocType_ID == MM_SHIPMENT)
			movementType = MInOut.MOVEMENTTYPE_CustomerShipment;
		else if (IODocType_ID == MM_CUSTOMER_RETURN)
			movementType = MInOut.MOVEMENTTYPE_CustomerReturns;
		else if (IODocType_ID == MM_VENDOR_RETURN)
			movementType = MInOut.MOVEMENTTYPE_VendorReturns;
		return movementType;
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
		
		MInOut io = new Query(ctx, MInOut.Table_Name, "C_Invoice_ID=? AND IsSOTrx=? AND DocStatus<>?", trxName)
		.setParameters(theInvoice.get_ID(), theInvoice.isSOTrx(), MInOut.DOCSTATUS_Reversed)
		.first();
	
		String orgValue = MOrg.get(ctx, theInvoice.getAD_Org_ID()).getValue();
		int invOrg_ID = theInvoice.getAD_Org_ID();
		
		if (io == null || io.getDocStatus().equals(MInOut.DOCSTATUS_Reversed))
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
			io.setMovementType(analyzeMovementType(docType_ID));
			io.setAD_Org_ID(invOrg_ID);
			io.setDocumentNo(theInvoice.getDocumentNo());
			io.setC_Invoice_ID(theInvoice.get_ID());
			io.saveEx();

			sql = "SELECT M_Locator_ID FROM M_Locator WHERE Value=? ";

			String locValue = PREFIX_LOCATOR_GOOD + orgValue;
			if (orgValue.equals("SNR"))
				locValue = "LOC-SparePart-SNR";
			else if (theInvoice.getC_DocType_ID() == AR_CREDITMEMO
					|| theInvoice.getC_DocType_ID() == AP_CREDITMEMO)
				locValue = PREFIX_LOCATOR_SC + orgValue;
			
			int M_Locator_ID = DB.getSQLValueEx(trxName, sql, locValue);

			for (MInvoiceLine il : theInvoice.getLines(true))
			{
				if (il.getQtyInvoiced().signum() == 0)
					continue;
				createNewShipReceiptLine(io, il, M_Locator_ID, trxName);
			}
		}
		else if (io.getDocStatus().equals(MInOut.DOCSTATUS_Completed)){
			io.setDocStatus(MInOut.DOCSTATUS_Drafted);
			io.setDocAction(MInOut.DOCACTION_Complete);
			io.setProcessed(false);
			io.setPosted(false);
			io.saveEx();
			
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
//				if (il.getQtyInvoiced().signum() == 0)
//					continue;
				MInOutLine iol = null;
				if (il.getM_InOutLine_ID() > 0)
					iol = new Query(ctx, MInOutLine.Table_Name, "M_InOutLine_ID=?", trxName)
						.setParameters(il.getM_InOutLine_ID())
						.firstOnly();
				
				if (iol == null) {
					createNewShipReceiptLine(io, il, M_Locator_ID, trxName);
				}
				else {
					updateStorageOnHand(ctx, theInvoice, io, iol, trxName);
					iol.setM_InOut_ID(io.get_ID());
					iol.setQty(il.getQtyInvoiced());
					iol.setC_UOM_ID(il.getC_UOM_ID());
					
					iol.saveEx();
					
					il.setM_InOutLine_ID(iol.get_ID());
					il.saveEx();
				}
			}
		}
		else {
			
			String sql = "SELECT M_Locator_ID FROM M_Locator WHERE Value=? ";
	
			String locValue = PREFIX_LOCATOR_GOOD + orgValue;
			if (orgValue.equals("SNR"))
				locValue = "LOC-SparePart-SNR";
			else if (theInvoice.getC_DocType_ID() == AR_CREDITMEMO
					|| theInvoice.getC_DocType_ID() == AP_CREDITMEMO)
				locValue = PREFIX_LOCATOR_SC + orgValue;
			
			int M_Locator_ID = DB.getSQLValueEx(trxName, sql, locValue);
			
			Hashtable<Integer, MInOutLine> processedIOLMap = new Hashtable<Integer, MInOutLine>();
	
			for (MInvoiceLine il : theInvoice.getLines(true))
			{
				MInOutLine iol = null;
				if (il.getM_InOutLine_ID() > 0)
					iol = new Query(ctx, MInOutLine.Table_Name, "M_InOutLine_ID=?", trxName)
						.setParameters(il.getM_InOutLine_ID())
						.firstOnly();
				
				if (iol == null) {
					iol = createNewShipReceiptLine(io, il, M_Locator_ID, trxName);
					processedIOLMap.put(iol.get_ID(), iol);
				}
				else {
					iol.setProcessed(false);
					
					sql = "SELECT M_Product_ID FROM M_CostDetail WHERE M_InOutLine_ID=?";
					int costDetailProduct_ID = 
							DB.getSQLValueEx(trxName, sql, iol.get_ID());
					
					if (costDetailProduct_ID <= 0 || costDetailProduct_ID != il.getM_Product_ID()) 
					{
						il.setM_InOutLine_ID(0);
						
						processedIOLMap.put(iol.get_ID(), iol);
						
						if (costDetailProduct_ID <= 0) 
						{
							iol.deleteEx(true);
						}
						else { //costDetailProduct_ID != il.getM_Product_ID()
							updateStorageOnHand(ctx, theInvoice, io, iol, trxName);
							iol.setQty(Env.ZERO);
							iol.saveEx();
						}
						
						iol = createNewShipReceiptLine(io, il, M_Locator_ID, trxName);						

						processedIOLMap.put(iol.get_ID(), iol);
					}
					else { // Exists in cost detail and same product with iol.
						updateStorageOnHand(ctx, theInvoice, io, iol, trxName);
						iol.setM_InOut_ID(io.get_ID());
						iol.setQty(il.getQtyInvoiced());
						iol.setC_UOM_ID(il.getC_UOM_ID());
						
						iol.saveEx();
						
						processedIOLMap.put(iol.get_ID(), iol);
						
						il.setM_InOutLine_ID(iol.get_ID());
						il.saveEx();
					}
				}				
			}
			
			// Try to process no linked IOL.
			MInOutLine[] ioLines = io.getLines(true);
			
			for (MInOutLine iol : ioLines)
			{
				MInOutLine iolProcessed = processedIOLMap.get(iol.get_ID());
				
				if (iolProcessed == null) {
					updateStorageOnHand(ctx, theInvoice, io, iol, trxName);
					iol.setQty(Env.ZERO);
					iol.saveEx();
				}
			}
		}
		
		return null;
	} // createShipmentFromInvoice
	
	static String updateStorageOnHand(
			Properties ctx, MInvoice theInvoice, MInOut io, MInOutLine iol, String trxName)
	{
		iol.setProcessed(false);
		
		BigDecimal qtyToRemove = iol.getMovementQty();
		BigDecimal qtyRemoved = Env.ZERO;
		
		
		if (iol.getM_AttributeSetInstance_ID() > 0)
		{
			MStorageOnHand soh = 
					MStorageOnHand.get(
							ctx, iol.getM_Locator_ID(), iol.getM_Product_ID(), 
							iol.getM_AttributeSetInstance_ID(), null, trxName);
			if (soh != null) {
				BigDecimal currToRemove = 
						(soh.getQtyOnHand().compareTo(qtyToRemove) >= 0) ? 
								qtyToRemove : soh.getQtyOnHand();

				DB.getDatabase().forUpdate(soh, 0);
				
				soh.setQtyOnHand(currToRemove);
				soh.saveEx();
				
				qtyToRemove = qtyToRemove.subtract(currToRemove);
				qtyRemoved = qtyRemoved.add(currToRemove);
			}
		}
		
		if (qtyToRemove.signum() > 0) {
			MStorageOnHand[] sohList = 
					MStorageOnHand.getAll(
							ctx, iol.getM_Product_ID(), iol.getM_Locator_ID(), trxName);
			
			for (MStorageOnHand soh : sohList){
				BigDecimal currToRemove = 
						(soh.getQtyOnHand().compareTo(qtyToRemove) >= 0) ? 
								qtyToRemove : soh.getQtyOnHand();

				DB.getDatabase().forUpdate(soh, 0);
				
				soh.setQtyOnHand(currToRemove);
				soh.saveEx();
				
				qtyToRemove = qtyToRemove.subtract(currToRemove);
				qtyRemoved = qtyRemoved.add(currToRemove);
				
				if (qtyToRemove.signum() <= 0)
					break;
			}
		}
		
		if (qtyToRemove.signum() > 0)
			log.log(Level.SEVERE, 
					"Produk" + iol.getM_Product() +" Quantity tidak habis dikoreksi, masih sisa sebesar: " + 
					qtyToRemove + " Invoice of " + theInvoice);
		
		MTransaction mtrx = new MTransaction (ctx, iol.getAD_Org_ID(),
				MTransaction.MOVEMENTTYPE_InventoryOut, iol.getM_Locator_ID(),
				iol.getM_Product_ID(), iol.getM_AttributeSetInstance_ID(),
				qtyRemoved, io.getMovementDate(), trxName);
		mtrx.setM_InOutLine_ID(iol.getM_InOutLine_ID());
		if (!mtrx.save())
		{
			return "Could not create Material Transaction for Product of " + iol.getM_Product() 
					+ ". Quantity masih sisa sebesar: " + qtyToRemove + " Invoice of " + theInvoice;
		}
		return null;
	}
	
	static MInOutLine createNewShipReceiptLine(
			MInOut io, MInvoiceLine il, int M_Locator_ID, String trxName)
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
		
		return iol;
	}
	
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
		header.setDocumentNo(invReffNo + "--" + m_currentDivisionPrefix);

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
