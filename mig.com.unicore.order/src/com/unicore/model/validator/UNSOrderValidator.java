/**
 * 
 */
package com.unicore.model.validator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MBPartner;
import org.compiere.model.MBankAccount;
import org.compiere.model.MClient;
import org.compiere.model.MCost;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocator;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrg;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.MProductPricing;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MSysConfig;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.util.Util;
import org.compiere.wf.MWorkflow;

import com.uns.base.model.Query;
import com.uns.util.MessageBox;

import com.unicore.model.I_DiscountModel;
import com.unicore.model.MUNSDiscountTrx;
import com.unicore.model.MUNSPackingList;
import com.unicore.model.MUNSSOConfirmation;
import com.unicore.model.MUNSSOConfirmationLine;
import com.unicore.model.UNSInvoiceDiscountModel;
import com.unicore.model.UNSOrderDiscountModel;
import com.unicore.model.process.CalculateDiscount;

/**
 * @author root
 * copy right 2015 RumahKijang@UntaSoft
 */
public class UNSOrderValidator implements ModelValidator {

	private boolean checkDiffInv = MSysConfig.getBooleanValue(MSysConfig.CHECK_DIFFERENCE_ON_INVOICE, true); 
	private boolean checkDiffOrd = MSysConfig.getBooleanValue(MSysConfig.CHECK_DIFFERENCE_ON_ORDER, true);
	/**
	 * 
	 */
	public UNSOrderValidator() {
		super();
	}
	
	int m_AD_Client_ID = 0;
	CLogger log = CLogger.getCLogger(UNSOrderValidator.class);

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#initialize(org.compiere.model.ModelValidationEngine, org.compiere.model.MClient)
	 */
	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
		
		if(client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
			log.log(Level.INFO, client.toString());
		}
		else
		{
			log.log(Level.INFO, "Initializing global validator -" + this.toString());
		}
		
		engine.addDocValidate(MOrder.Table_Name, this);
		engine.addDocValidate(MInvoice.Table_Name, this);
		engine.addDocValidate(MInOut.Table_Name, this);
		engine.addModelChange(MOrder.Table_Name, this);
		engine.addModelChange(MOrderLine.Table_Name, this);
		engine.addModelChange(MInvoice.Table_Name, this);
		engine.addModelChange(MInvoiceLine.Table_Name, this);
		engine.addModelChange(MMovementLine.Table_Name, this);
		engine.addModelChange(MPaymentAllocate.Table_Name, this);
		engine.addDocValidate(MUNSPackingList.Table_Name, this);
		engine.addDocValidate(MMovementLine.Table_Name, this);
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#getAD_Client_ID()
	 */
	@Override
	public int getAD_Client_ID() {
		return m_AD_Client_ID;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#login(int, int, int)
	 */
	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		return null;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private String invoiceLineOnCreate(PO po, int type)
	{
		MInvoiceLine doc = (MInvoiceLine) po;
		if(doc.getC_OrderLine_ID() <= 0)
			return null;
		
		MOrderLine oLine = new MOrderLine(doc.getCtx(), doc.getC_OrderLine_ID(), doc.get_TrxName());
		
		String msg = doValidateSwap(po);
		if(!Util.isEmpty(msg, true))
			return msg;
		
		String sql = "SELECT MAX(UNS_SOCOnfirmation_ID) FROM UNS_SOConfirmation "
				+ " WHERE C_Order_ID = ? AND ConfirmationType = ?";
		int value = DB.getSQLValue(po.get_TrxName(), sql, doc.getC_OrderLine().getC_Order_ID()
				, MUNSSOConfirmation.CONFIRMATIONTYPE_OrderToInvoice);
		if(value <= 0)
		{
			if(oLine.getQtyEntered().compareTo(doc.getQtyEntered()) != 0)
			{
				doc.setDiscount(Env.ZERO);
				doc.setDiscountAmt(Env.ZERO);
				doc.setQtyBonuses(Env.ZERO);
				doc.setPrice(doc.getPriceList());
				return null;
			}
			
			if(type == TYPE_AFTER_NEW)
			{
				copyDiscount(oLine, doc);
			}
			else if(type == TYPE_BEFORE_NEW)
			{
				doc.setQtyBonuses(oLine.getQtyBonuses());
			}
		}
		else
		{
			MUNSSOConfirmation soConfirmation = new MUNSSOConfirmation(po.getCtx(), value, po.get_TrxName());
			if(null == soConfirmation.getConfirmChanges())
			{
				return "Not completed SO Confirmation... Please open So Confirmation of Order " 
						+ oLine.getC_Order().getDocumentNo();
			}
			if(soConfirmation.getConfirmChanges().equals(MUNSSOConfirmation.CONFIRMCHANGES_Approve))
			{
				if(oLine.getQtyEntered().compareTo(doc.getQtyEntered()) != 0)
				{
					doc.setDiscount(Env.ZERO);
					doc.setDiscountAmt(Env.ZERO);
					doc.setQtyBonuses(Env.ZERO);
					doc.setPrice(doc.getPriceList());
					
					return null;
				}

				if(type == TYPE_AFTER_NEW)
				{
					copyDiscount(oLine, doc);
				}
				else if(type == TYPE_BEFORE_NEW)
				{
					doc.setQtyBonuses(oLine.getQtyBonuses());
					doc.setDiscount(oLine.getDiscount());
					doc.setDiscountAmt(oLine.getDiscountAmt());
				}
			}
			else if(soConfirmation.getConfirmChanges().equals(MUNSSOConfirmation.CONFIRMCHANGES_Recalculate)
					&& type == TYPE_BEFORE_NEW)
			{
				MUNSSOConfirmationLine line = soConfirmation.getLineOrder(doc.getC_OrderLine_ID());
				if(line == null)
				{
					return null;
				}
				
				doc.setDiscount(Env.ZERO);
				doc.setDiscountAmt(Env.ZERO);
				doc.setQtyBonuses(Env.ZERO);
				doc.setPrice();
			}
			else if(soConfirmation.getConfirmChanges().equals(MUNSSOConfirmation.CONFIRMCHANGES_Customize)
					&& type == TYPE_BEFORE_NEW)
			{
				MUNSSOConfirmationLine line = soConfirmation.getLineOrder(doc.getC_OrderLine_ID());
				if(line == null)
					return null;
				
				doc.setDiscount(Env.ZERO);
				doc.setDiscountAmt(Env.ZERO);
				doc.setQtyBonuses(Env.ZERO);
				doc.setPrice(line.getPriceActual());
				doc.setPriceList(line.getPriceList());
			}
		}		
		return null;
	}
	
	/**
	 * Validate order on Change
	 * @param po
	 */
	private String orderOnChange(PO po)
	{
		MOrder order = (MOrder) po;
		Timestamp datePromised = order.getDatePromised();
		Timestamp dateOrdered = order.getDateOrdered();
		
		if(datePromised == null || datePromised.before(dateOrdered))
		{
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(((Timestamp) po.get_Value(MOrder.COLUMNNAME_DateOrdered)).getTime());
			cal.add(Calendar.DATE, 2);
			datePromised = new Timestamp(cal.getTimeInMillis());
			order.setDatePromised(datePromised); 	
		}
		return null;
	}
	
	/**
	 * Get Lines of PO. 
	 * @param po
	 * @return {@link PO}[]
	 */
	private PO[] getLines(PO po)
	{
		PO[] pos = null;
		if(po instanceof MInvoice)
		{
			MInvoice invoice =  (MInvoice) po;
			pos =  invoice.getLines();
		}
		else if(po instanceof MOrder)
		{
			MOrder order = (MOrder) po;
			pos = order.getLines();
		}
		else if(po instanceof MInOut)
		{
			MInOut inout = (MInOut) po;
			pos = inout.getLines();
		}
		else
		{
			pos = new PO[0];
		}
		
		return pos;
	}
	
	/**
	 * 
	 * @param order
	 * @param date
	 * @return
	 */
	private String checkValidPrice(PO po, Timestamp date)
	{
		if(po.get_TableName().equals(MInvoice.Table_Name) && !checkDiffInv)
			return "";
		if(po.get_TableName().equals(MOrder.Table_Name) && !checkDiffOrd)
			return "";
		
		StringBuilder msg = new StringBuilder();
		PO[] lines = getLines(po);
		for(int i=0; i<lines.length; i++)
		{
			MProductPricing pp = getProductPricing(
					po.get_ValueAsInt("C_BPartner_ID"), po.get_ValueAsInt("C_BPartner_Location_ID")
					, (BigDecimal)lines[i].get_Value("QtyEntered"), po.get_ValueAsBoolean("IsSOTrx")
					, po.get_ValueAsInt("M_PriceList_ID"), lines[i].get_ValueAsInt("M_Product_ID")
					, date, po.get_TrxName());
			
			boolean condition1 = ((BigDecimal) lines[i].get_Value("PriceList")).compareTo(pp.getPriceList()) == 0;
			
			if(!condition1)
			{
				msg.append("Diferent Price List in Price List on line " 
							+ lines[i].get_ValueAsInt("Line"));
			}
		}
		
		return msg.toString();
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private String invoiceOnChange(PO po)
	{
		MInvoice doc = (MInvoice) po;
		if(!doc.is_ValueChanged("C_Order_ID"))
			return null;
		if(!doc.isSOTrx())
			return null;
		
		Integer oldOrder = doc.get_ValueOldAsInt("C_Order_ID");
		if(oldOrder > 0 && oldOrder != doc.getC_Order_ID())
			return "Not Updatable C_Order";
		
		if(doc.getC_Order_ID() <= 0)
			return null;
		
		MOrder order = new MOrder(doc.getCtx(), doc.getC_Order_ID(), doc.get_TrxName());
		
		if (doc.is_ValueChanged(MInvoice.COLUMNNAME_C_Order_ID)
				&& order.getDiscountAmt().signum() == 1)
		{
			doc.setAddDiscountAmt(order.getDiscountAmt());
		}
		
		if(order.isLockPricing())
		{
			boolean confirmOK = confirmOK(order, doc.getDateInvoiced());
			if(confirmOK)
				return null;
		}
		
		String validPrice = checkValidPrice(order, doc.getDateInvoiced());
		String validDiscount = checkDiscount(order, doc.getDateInvoiced());
		String retVal = null;
		
		if(!Util.isEmpty(validPrice, true) || !Util.isEmpty(validDiscount, true))
			retVal = validPrice + validDiscount;
		
		if(retVal != null && !confirmOK(order, doc.getDateInvoiced()))
		{
			createConfirmation(
					order, doc.getDateInvoiced(), retVal
					,MUNSSOConfirmation.CONFIRMATIONTYPE_OrderToInvoice);
		}
		else
		{
			String sql = "SELECT MAX(UNS_SOCOnfirmation_ID) FROM UNS_SOConfirmation "
					+ " WHERE C_Order_ID = ? AND ConfirmationType = ?";
			int value = DB.getSQLValue(po.get_TrxName(), sql, order.get_ID()
					, MUNSSOConfirmation.CONFIRMATIONTYPE_OrderToInvoice);
			if(value <= 0)
			{
				copyDiscount(order, doc);
			}
			retVal = null;
		}
		
		String toString = retVal; //20150226 jika langsung return retval maka akan menjadi null. makanya diginiin. kondisi pakai java 1.7 eclipse kepler idempiere3.0
		return toString;
	}
	
	/**
	 * Validate invoice on create
	 * @param po
	 */
	private String invoiceOnCreate(PO po, int type)
	{
		MInvoice invoice = (MInvoice) po;
		
		if(invoice.getC_Order_ID() <= 0)
			return null;
		if(!invoice.isSOTrx())
			return null;
		
		MOrder order = new MOrder(invoice.getCtx(), invoice.getC_Order_ID(), 
				invoice.get_TrxName());
		if(type == TYPE_BEFORE_NEW)
		{
			if (order.getDiscountAmt().signum() == 1)
				invoice.setAddDiscountAmt(order.getDiscountAmt());
		}
		
		if(order.isLockPricing())
		{
			boolean confirmOK = confirmOK(order, invoice.getDateInvoiced());
			if(confirmOK)
				return null;
		}
		
		String retval = null;
		String invalidMsg = checkValidPrice(order, invoice.getDateInvoiced());
		String validateDiscountMsg = checkDiscount(order, invoice.getDateInvoiced());
		
		if((!Util.isEmpty(invalidMsg, true) || !Util.isEmpty(validateDiscountMsg, true)))
			retval = invalidMsg + validateDiscountMsg;
		
		if(retval != null && !confirmOK(order, invoice.getDateInvoiced()))
		{
			createConfirmation(order, invoice.getDateInvoiced(), invalidMsg.concat(validateDiscountMsg)
					, MUNSSOConfirmation.CONFIRMATIONTYPE_OrderToInvoice);
			if(order.is_ValueChanged(MOrder.COLUMNNAME_DatePromised))
				invoice.setDateInvoiced(order.getDatePromised());
		}
		else
		{
			String sql = "SELECT MAX(UNS_SOCOnfirmation_ID) FROM UNS_SOConfirmation "
					+ " WHERE C_Order_ID = ? AND ConfirmationType = ?";
			int value = DB.getSQLValue(po.get_TrxName(), sql, order.get_ID()
					, MUNSSOConfirmation.CONFIRMATIONTYPE_OrderToInvoice);
			if(value <= 0 && type == TYPE_AFTER_NEW)
			{
				copyDiscount(order, invoice);
			}
			retval = null;
		}
		String toString = retval;
		
		return toString;
	}
	
	/**
	 * 
	 * @param C_Order_ID
	 * @param trxName
	 * @return
	 */
	private boolean confirmOK(PO po, Timestamp date)
	{
		MUNSSOConfirmation[] confirmation = MUNSSOConfirmation.get(po, po.get_TrxName());
		if ((null == confirmation || confirmation.length == 0)) {
			if (po instanceof I_C_Invoice) {
				MOrder order = new MOrder(po.getCtx(), po.get_ValueAsInt("C_Order_ID"), po.get_TrxName());
				return confirmOK(order, date);
			}
		}
		int confirmCount = confirmation.length;
		int invalidCount = 0;
		Timestamp overwritenDate = null;
		
		for(MUNSSOConfirmation confirm : confirmation)
		{
			if(!confirm.getDocStatus().equals(MUNSSOConfirmation.DOCSTATUS_Completed)
					&& !confirm.getDocStatus().equals(MUNSSOConfirmation.DOCSTATUS_Closed)
					&& Env.getContextAsInt(Env.getCtx(), MUNSSOConfirmation.IS_EVENT_FROM_CONFIRMATION) <= 0)
			{
				invalidCount++;
				continue;
			}
			Timestamp validDate = confirm.getValidTo();
			
			if(null == validDate)
			{
				return true;
			}
			
			boolean notValid = date.after(validDate);
			if(notValid)
			{
				invalidCount++;
				continue;
			}
			
			if(confirm.getConfirmChanges().equals(MUNSSOConfirmation.CONFIRMCHANGES_Void))
			{
				invalidCount++;
				continue;
			}
			
			if(confirm.getOverwriteDate() != null)
				overwritenDate = confirm.getOverwriteDate();
		}
		
		if(confirmCount > 0 && invalidCount < confirmCount)
		{
			if(overwritenDate != null)
			{
				if(po instanceof MOrder)
				{
					MOrder order = (MOrder) po;
					order.setDatePromised(overwritenDate);
				}
				else if(po instanceof MInvoice)
				{
					MInvoice invoice = (MInvoice) po;
					invoice.setDateInvoiced(overwritenDate);
				}
			}
			return true;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#modelChange(org.compiere.model.PO, int)
	 */
	@Override
	public String modelChange(PO po, int type) throws Exception {
		String msg = null;
		if(po.get_TableName().equals(MOrder.Table_Name))
		{			
			if(type != TYPE_BEFORE_CHANGE && type != TYPE_BEFORE_NEW)
				return null;
			
			msg =  orderOnChange(po);
			if(Util.isEmpty(msg, true))
				msg = checkManualDiscount(po);
		}
		else if (po.get_TableName().equals(MOrderLine.Table_Name))
		{				
			if(type == TYPE_AFTER_CHANGE && po.is_ValueChanged("QtyEntered"))
			{
				MRequisitionLine reqLine = new MRequisitionLine(po.getCtx(), po.get_ValueAsInt("M_RequisitionLine_ID"),
												po.get_TrxName());
				if(reqLine.get_ID() > 0)
				{
					BigDecimal valueOld = (BigDecimal)po.get_ValueOld("QtyEntered");
					BigDecimal newValue = (BigDecimal)po.get_Value("QtyEntered");
					BigDecimal diff = valueOld.subtract(newValue);
					reqLine.setQtyOrdered(reqLine.getQtyOrdered().subtract(diff));
					reqLine.saveEx();
				}
			}
			
			if(type == TYPE_BEFORE_DELETE)
			{
				MRequisitionLine reqLine = new MRequisitionLine(po.getCtx(), po.get_ValueAsInt("M_RequisitionLine_ID"),
												po.get_TrxName());
				if(reqLine.get_ID() > 0)
				{
					reqLine.setQtyOrdered(reqLine.getQtyOrdered().subtract((BigDecimal)po.get_Value("QtyEntered")));
					reqLine.saveEx();
				}
				
			}
			
			msg = doValidateSwap(po);
		}
		else if(po.get_TableName().equals(MInvoice.Table_Name))
		{
			MInvoice invoice = (MInvoice) po;
			if (invoice.isReversal())
			{
				return msg;
			}
			if(type == TYPE_BEFORE_NEW || type == TYPE_AFTER_NEW)
			{
				msg = invoiceOnCreate(po, type);
				if(!Util.isEmpty(msg, true))
					return msg;
			}
			else if(type == TYPE_BEFORE_CHANGE)
			{
				msg = invoiceOnChange(po);
			}
			else if(type == TYPE_AFTER_CHANGE)
			{
				msg = validateOnOrderToInvoice(po);
				if(Util.isEmpty(msg, true))
					msg = checkManualDiscount(po);
			}
			
//			if((type == TYPE_AFTER_CHANGE && po.is_ValueChanged(X_C_Invoice.COLUMNNAME_C_BPartner_ID)) 
//					|| (type == TYPE_AFTER_NEW && po.get_ValueAsInt(X_C_Invoice.COLUMNNAME_C_BPartner_ID) > 0))
//			{
//				if(po.is_ValueChanged(X_C_Invoice.COLUMNNAME_C_BPartner_ID)
//						|| po.get_ValueAsString("VirtualAccount") == null)
//				{
//					MBPBankAccount[] bpAccount = MBPBankAccount.getOfBPartner(po.getCtx(), po.get_ValueAsInt("C_BPartner_ID"));
//					po.set_ValueOfColumn("VirtualAccount", bpAccount[0].getVirtualAccount());
//				}
//			}
		}
		else if(po.get_TableName().equals(MInvoiceLine.Table_Name))
		{
			MInvoiceLine invLine = (MInvoiceLine) po;
			if (invLine.isReversal())
				return msg;
			if(type == TYPE_AFTER_NEW)
			{
				msg = invoiceLineOnCreate(po, type);
				if(!Util.isEmpty(msg, true))
					return msg;
			}
			else if(type == TYPE_BEFORE_NEW) {
				msg = invoiceLineOnCreate(po, type);
				String sql = "SELECT DocumentNo FROM C_Invoice WHERE "
				+ " C_Invoice_ID IN (SELECT C_Invoice_ID FROM "
				+ " C_InvoiceLine WHERE M_InoutLine_ID = ? ) "
				+ " AND DocStatus NOT IN ('VO','RE')";
				String result = DB.getSQLValueString(po.get_TrxName(), sql, 
						po.get_ValueAsInt("M_InOutLine_ID"));
				if (!Util.isEmpty(result, true) && !"Y".equals(Env.getContext(
						Env.getCtx(), "ON_ProcessNotInvoiceReceiptProcessor")))
				{
					msg = "Invoice already exists, invoice doc no " + result;
					return msg;
				}
			}else if(type == TYPE_BEFORE_CHANGE)
				msg = invoiceLineOnChange(po);
		}
		else if(po.get_TableName().equals(MPaymentAllocate.Table_Name))
		{
			if(type != TYPE_AFTER_CHANGE && type != TYPE_AFTER_NEW)
				return null;
			
			MPaymentAllocate allocate = (MPaymentAllocate) po;
			
			if(allocate.getC_Invoice_ID() <= 0)
				return null;
			
			MInvoice invoice = new MInvoice(po.getCtx(), 
					allocate.getC_Invoice_ID(), po.get_TrxName());
			
			String docBaseType = invoice.getC_DocTypeTarget().getDocBaseType();
			if (docBaseType.equals(MDocType.DOCBASETYPE_ARCreditMemo))
			{
				msg = allocateAROfCN(allocate, invoice);
			}
//			else if (docBaseType.equals(MDocType.DOCBASETYPE_ARInvoice))
//			{
//				msg = allocateCNOfAR(allocate, invoice);
//			}
		}
		else if(po.get_TableName().equals(MMovementLine.Table_Name))
		{
			if(type == TYPE_AFTER_CHANGE && po.is_ValueChanged("MovementQty"))
			{
				MRequisitionLine reqLine = new MRequisitionLine(po.getCtx(), po.get_ValueAsInt("M_RequisitionLine_ID"),
						po.get_TrxName());
				if(reqLine.get_ID() > 0)
				{
					BigDecimal valueOld = (BigDecimal)po.get_ValueOld("MovementQty");
					BigDecimal newValue = (BigDecimal)po.get_Value("MovementQty");
					BigDecimal diff = valueOld.subtract(newValue);
					reqLine.setMovementQty(reqLine.getMovementQty().subtract(diff));
					reqLine.saveEx();
				}
			}
			
			if(type == TYPE_BEFORE_DELETE)
			{
				MRequisitionLine reqLine = new MRequisitionLine(po.getCtx(), po.get_ValueAsInt("M_RequisitionLine_ID"),
												po.get_TrxName());
				if(reqLine.get_ID() > 0)
				{
					reqLine.setMovementQty(reqLine.getMovementQty().subtract((BigDecimal)po.get_Value("MovementQty")));
					reqLine.saveEx();
				}
			}
		}
		
		return msg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#docValidate(org.compiere.model.PO, int)
	 */
	@Override
	public String docValidate(PO po, int timing) {
		if(po.get_TableName().equals(MOrder.Table_Name))
		{
			if(timing == TIMING_AFTER_COMPLETE && po.get_Value("Reversal_ID") == null)
			{
				updateDiscountBudget(po, false);
			}
			else if(timing == TIMING_AFTER_REACTIVATE)
			{
				updateDiscountBudget(po, true);
			}
			else if(timing == TIMING_AFTER_REVERSEACCRUAL)
			{
				updateDiscountBudget(po, true);
			}
			else if(timing == TIMING_AFTER_REVERSECORRECT)
			{
				updateDiscountBudget(po, true);
			}
			else if(timing == TIMING_AFTER_VOID)
			{
				updateDiscountBudget(po, true);
			}
			else if(timing == TIMING_AFTER_PREPARE)
			{
				MOrder order = (MOrder) po;
				if(order.isLockPricing() && !confirmOK(order, order.getDatePromised()))
				{
					String clause = "LOCK PRICING CONFIRMATION";
					createConfirmation(order, order.getDatePromised(), clause
							, MUNSSOConfirmation.CONFIRMATIONTYPE_OrderConfirmation);
					return "Waiting approval of locked order";
				}
				String invalidDiscountMsg = checkDiscount(order, order.getDatePromised());
				String invalidPriceMsg = checkValidPrice(order, order.getDatePromised());
				String limitInvoices = checkInvoiceCountLimit(order);
				String limitBalance = checkCreditLimit(order);
				if((!Util.isEmpty(invalidDiscountMsg, true) || !Util.isEmpty(invalidPriceMsg, true)
						|| !Util.isEmpty(limitInvoices, true))
						&& !confirmOK(order, order.getDatePromised()))
				{
					createConfirmation(
							order, order.getDatePromised(), limitInvoices.concat(invalidPriceMsg).concat(invalidDiscountMsg)
							, MUNSSOConfirmation.CONFIRMATIONTYPE_OrderConfirmation);
					return invalidDiscountMsg + invalidPriceMsg + limitInvoices;
				}
				if((!Util.isEmpty(limitBalance, true) || !Util.isEmpty(limitInvoices, true))
						&& !confirmOK(order, order.getDatePromised()))
				{
					limitBalance = Util.isEmpty(limitBalance, true) ? "" : limitBalance + "\n";
					limitInvoices = Util.isEmpty(limitInvoices, true) ? "" : limitInvoices;
					
					String msg = limitBalance + limitInvoices;
					
					createConfirmation(order, order.getDatePromised(), msg, 
							MUNSSOConfirmation.CONFIRMATIONTYPE_OrderConfirmation);
					return msg;
				}
			}
		}
		else if(po.get_TableName().equals(MInvoice.Table_Name))
		{
			if(timing == TIMING_BEFORE_VOID
					|| timing == TIMING_BEFORE_REVERSEACCRUAL
					|| timing == TIMING_BEFORE_REVERSECORRECT)
			{
				String errormsg = isInvoiceAllocated(po);
				if(null != errormsg)
					return errormsg;
			}
			else if(timing == TIMING_AFTER_COMPLETE)
			{
				String msg = doValidateOnCompleteSwapInv(po);
				if(!Util.isEmpty(msg, true))
					return msg;
				if (po.get_Value("Reversal_ID") == null)
					updateDiscountBudget(po, false);
			}
			else if(timing == TIMING_AFTER_REACTIVATE)
			{
				updateDiscountBudget(po, true);
			}
			else if(timing == TIMING_AFTER_REVERSEACCRUAL)
			{
				updateDiscountBudget(po, true);
			}
			else if(timing == TIMING_AFTER_REVERSECORRECT)
			{
				updateDiscountBudget(po, true);
			}
			else if(timing == TIMING_AFTER_VOID)
			{
				updateDiscountBudget(po, true);
			}
			else if(timing == TIMING_AFTER_PREPARE)
			{
				MInvoice invoice = (MInvoice) po;
				if(invoice.isReversal())
					return null;
				if(invoice.getC_Order_ID() <= 0)
					return null;
				
				MOrder order = (MOrder) invoice.getC_Order();
				boolean confirmOK = false;
				
				String docSubTypeCash = MDocType.DOCSUBTYPESO_CashOrder;
				
				if(docSubTypeCash.equals(order.getC_DocTypeTarget()
						.getDocSubTypeSO()))
				{
					return null;
				}
				
				if(order.isLockPricing())
					confirmOK = confirmOK(order, invoice.getDateInvoiced());
				
				if(confirmOK)
					return checkDiferentPrice(invoice);
				
				String invalidDiscountMsg = checkDiscount(invoice, invoice.getDateInvoiced());
				String invalidPriceMsg = checkValidPrice(invoice, invoice.getDateInvoiced());
				
				if((!Util.isEmpty(invalidDiscountMsg, true) || !Util.isEmpty(invalidPriceMsg, true))
						&& !confirmOK(po, invoice.getDateInvoiced()))
				{
					createConfirmation(
							po, invoice.getDateInvoiced(), invalidDiscountMsg.concat(invalidPriceMsg)
							, MUNSSOConfirmation.CONFIRMATIONTYPE_InvoiceConfirmation);
				
					return invalidDiscountMsg + invalidPriceMsg;
				}
				
				MInvoiceLine[] lines = invoice.getLines();
				for(MInvoiceLine line : lines)
				{
					if(line.getC_OrderLine_ID() <= 0)
						continue;
					
					MOrderLine oLine = new MOrderLine(po.getCtx(), line.getC_OrderLine_ID(), po.get_TrxName());
					if(oLine.getQtyBonuses().compareTo(line.getQtyBonuses()) == 0)
						continue;
					
					oLine.setQtyBonuses(line.getQtyBonuses());
					oLine.saveEx();
				}
			}
		}
		else if(po.get_TableName().equals(MInOut.Table_Name))
		{
			if(timing == TIMING_AFTER_COMPLETE)
			{
				if (po.get_Value("Reversal_ID") == null)
					updateDiscountBudget(po, false);
				String errMsg = createInternalUserFromInout(po);
				if(!Util.isEmpty(errMsg, true))
					return errMsg;
			}
			else if(timing == TIMING_AFTER_REACTIVATE)
				updateDiscountBudget(po, true);
			else if(timing == TIMING_AFTER_REVERSEACCRUAL)
				updateDiscountBudget(po, true);
			else if(timing == TIMING_AFTER_REVERSECORRECT)
				updateDiscountBudget(po, true);
			else if(timing == TIMING_AFTER_VOID)
				updateDiscountBudget(po, true);
			else if(timing == TIMING_BEFORE_PREPARE)
				return validateOnShipmentProcessAutoComplete(po);
				
		}
		return null;
	}
	
	/**
	 * 
	 * @param invoice
	 * @return
	 */
	private String checkDiferentPrice(MInvoice invoice)
	{
		MInvoiceLine[] lines = invoice.getLines();
		for(int i=0; i<lines.length; i++)
		{
			MInvoiceLine line = lines[i];
			String sql = "SELECT PriceActual FROM C_OrderLine WHERE C_OrderLine_ID = ?";
			BigDecimal orderPriceActual = DB.getSQLValueBD(invoice.get_TrxName(), sql, line.getC_OrderLine_ID());
			if(orderPriceActual.compareTo(line.getPriceActual()) != 0)
				return "Order is Locked but price diferent has found in price on order and invoice";
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param listDiscount
	 * @param date
	 * @return
	 */
	private String checkEffectivedateOfDiscount(
			MUNSDiscountTrx discountTrx, Timestamp date)
	{
		checkDiffInv = false;
		if (!checkDiffInv)
			return "";
		StringBuilder msg = new StringBuilder();
		if(discountTrx.isZeroDiscount())
			discountTrx.deleteEx(true);
		
		String sql = null;
		int param_ID = 0;
		
		if(discountTrx.getM_DiscountSchema_ID() > 0)
		{
			sql = "SELECT ValidTo FROM M_DiscountSchema WHERE M_DiscountSchema_ID = ?";
			param_ID = discountTrx.getM_DiscountSchema_ID();
		}
		else if(discountTrx.getM_DiscountSchemaBreak_ID() > 0)
		{
			sql = "SELECT ValidTo FROM M_DiscountSchema WHERE M_DiscountSchema_ID = "
					+ " (SELECT M_DiscountSchema_ID FROM M_DiscountSchemaBreak WHERE M_DiscountSchemaBreak_ID = ?)";
			param_ID = discountTrx.getM_DiscountSchemaBreak_ID();
		}
		else if(discountTrx.getUNS_DSBreakLine_ID() > 0)
		{
			sql = "SELECT ValidTo FROM M_DiscountSchema WHERE M_DiscountSchema_ID = "
					+ " (SELECT M_DiscountSchema_ID FROM M_DiscountSchemaBreak WHERE M_DiscountSchemaBreak_ID = "
					+ " (SELECT M_DiscountSchemaBreak_ID FROM UNS_DSBreakLine WHERE UNS_DSBreakLine_ID = ?))";
			param_ID = discountTrx.getUNS_DSBreakLine_ID();
		}
		
		if(null == sql)
			return msg.toString();
		
		Timestamp ValidDate = DB.getSQLValueTS(discountTrx.get_TrxName(), sql, param_ID);
		
		if(null == ValidDate)
			return msg.toString();
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ValidDate.getTime());
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
		ValidDate = new Timestamp(cal.getTimeInMillis());
		
		if(ValidDate.compareTo(date) == 1)
			return msg.toString();
		
		msg.append("Discount kadaluarsa pada nomor urut :")
		.append(discountTrx.getSeqNo()).append("\n");
		return msg.toString();
	}
	
	
	/**
	 * 
	 * @param order
	 * @return
	 */
	private MUNSSOConfirmation createConfirmation(PO po, Timestamp date, String clause, String confirmationEvent)
	{
		MUNSSOConfirmation[] confirms = MUNSSOConfirmation.get(po, po.get_TrxName());
		MUNSSOConfirmation confirm = null;
		for(int i=0; i<confirms.length; i++)
		{
			confirm = confirms[i];
			if(confirm.getDocStatus().equals(MUNSSOConfirmation.DOCSTATUS_Completed)
					|| confirm.getDocStatus().equals(MUNSSOConfirmation.DOCSTATUS_Closed))
			{
				Timestamp validDate = confirm.getValidTo();
				
				if(null == validDate)
					break;
				
				int compare = validDate.compareTo(date);
				if(compare < 0)
				{
					confirm = null;
					continue;
				}
			}
			
			if(!confirmationEvent.equals(confirm.getConfirmationType()))
				confirm = null;
		}
		
		if(confirm == null)
		{	
			Trx trx = Trx.get(Trx.createTrxName(), true);
			trx.start();
			confirm = new MUNSSOConfirmation(po);
			confirm.setValidTo(date);
			confirm.setDescription(clause);
			confirm.setConfirmationType(confirmationEvent);
			try {
				confirm.saveEx(trx.getTrxName());
				confirm.createLines();
				Thread thread = new Thread(new ThreadSave(trx));
				thread.setName("Try Commit Trx");
				thread.start();
			} catch (Exception ex) {
				throw new AdempiereException(ex.getMessage());
			} finally {
			}
		}
		
		if(confirm.getDocStatus().equals(MUNSSOConfirmation.DOCSTATUS_Drafted)
				|| confirm.getDocStatus().equals(MUNSSOConfirmation.DOCSTATUS_InProgress)
				|| confirm.getDocStatus().equals(MUNSSOConfirmation.DOCSTATUS_Invalid))
		{
			MessageBox.showMsg(po.getCtx(),
					po.getProcessInfo(), "Please open SO Confirmation!".concat(confirm.getDocumentNo())
					, "Information", MessageBox.OK, MessageBox.ICONINFORMATION);
		}
		
		return confirm;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @param Qty
	 * @param IsSOTrx
	 * @param M_PriceList_ID
	 * @param M_Product_ID
	 * @param date
	 * @param trxName
	 * @return
	 */
	private MProductPricing getProductPricing(
			int C_BPartner_ID, int C_BPartner_Location_ID, BigDecimal Qty, boolean IsSOTrx
			, int M_PriceList_ID, int M_Product_ID, Timestamp date, String trxName)
	{
		MProductPricing pp = new MProductPricing (M_Product_ID, C_BPartner_ID, Qty, IsSOTrx, C_BPartner_Location_ID);
		pp.setM_PriceList_ID(M_PriceList_ID);
		
		if(M_PriceList_ID <= 0)
			throw new AdempiereException("Price list not set");	
	
		String sql = "SELECT plv.M_PriceList_Version_ID "
			+ "FROM M_PriceList_Version plv "
			+ "WHERE plv.M_PriceList_ID=? "						//	1
			+ " AND plv.ValidFrom <= ? ";
		if (pp.isIgnoredUnprocessedPrice())
		{
			sql += " AND plv.IsApproved = 'Y' AND plv.DocStatus IN ('CO')";
		}
		sql 
			+= "ORDER BY plv.ValidFrom DESC";

		int M_PriceList_Version_ID = DB.getSQLValueEx(trxName, sql, M_PriceList_ID, date);
		if (M_PriceList_Version_ID > 0 )
			pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);
		
		pp.setPriceDate(date);
		return pp;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public MUNSDiscountTrx[] getDiscountTrx(PO po)
	{
		String sql = "SELECT * FROM UNS_DiscountTrx WHERE "
				+ po.get_TableName() + "_ID = ?";
		
		List<MUNSDiscountTrx> list = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = DB.prepareStatement(sql, po.get_TrxName());
			st.setInt(1, po.get_ID());
			rs = st.executeQuery();
			while (rs.next())
			{
				MUNSDiscountTrx trx = new MUNSDiscountTrx(po.getCtx(), rs, po.get_TrxName());
				list.add(trx);
			}
		} catch (SQLException e) {
			throw new AdempiereException(e.getMessage());
		} finally {
			DB.close(rs, st);
		}
		
		MUNSDiscountTrx[] retVal = new MUNSDiscountTrx[list.size()];
		list.toArray(retVal);
		return retVal;
	}
	
	/**
	 * 
	 * @param po
	 * @param to
	 */
	private void copyDiscount(PO po, PO to)
	{
		MUNSDiscountTrx[] trxs = MUNSDiscountTrx.get(po);
		for(int i=0; i<trxs.length; i++)
		{
			MUNSDiscountTrx newTrx = new MUNSDiscountTrx(to.getCtx(), 0, to.get_TrxName());
			PO.copyValues(trxs[i], newTrx);
			newTrx.setIsChangedByUser(false);
			newTrx.set_ValueOfColumn(po.get_TableName() + "_ID", null);
			newTrx.set_ValueOfColumn(to.get_TableName() + "_ID", to.get_ID());
			newTrx.setAD_Org_ID(to.getAD_Org_ID());
			newTrx.saveEx();
		}
	}
	
	
	/**
	 * 
	 * @param po
	 * @param revert
	 */
	private void updateDiscountBudget(PO po, boolean revert)
	{
		PO refPO = null;
		if(po instanceof MInOut)
		{
			if(po.get_ValueAsInt(MInOut.COLUMNNAME_C_Invoice_ID) > 0)
			{
				refPO = new MInvoice(
						po.getCtx(), po.get_ValueAsInt(MInOut.COLUMNNAME_C_Invoice_ID), po.get_TrxName());
			}
			else if(po.get_ValueAsInt(MInOut.COLUMNNAME_C_Order_ID) > 0)
			{
				refPO = new MOrder(
						po.getCtx(), po.get_ValueAsInt(MInOut.COLUMNNAME_C_Order_ID), po.get_TrxName());
			}
		}
		else if(po instanceof MInOutLine)
		{
			String sql = "SELECT ".concat(MInvoiceLine.COLUMNNAME_C_InvoiceLine_ID)
					.concat( " FROM ").concat(MInvoiceLine.Table_Name)
					.concat(" WHERE ").concat(MInvoiceLine.COLUMNNAME_M_InOutLine_ID)
					.concat("=?");
			int retVal = DB.getSQLValue(po.get_TrxName(), sql, po.get_ID());
			if(retVal > 0)
			{
				refPO = new MInvoiceLine(po.getCtx(), retVal, po.get_TrxName());
			}
			else
			{
				sql = "SELECT ".concat(MInOutLine.COLUMNNAME_C_OrderLine_ID)
						.concat(" FROM ").concat(MInOutLine.Table_Name)
						.concat(" WHERE ").concat(MInOutLine.COLUMNNAME_M_InOutLine_ID)
						.concat("=?");
				retVal = DB.getSQLValue(po.get_TrxName(), sql, po.get_ID());
				if(retVal > 0)
				{
					refPO = new MOrderLine(po.getCtx(), retVal, po.get_TrxName());
				}
			}
			
		}
		else
		{
			refPO = po;
		}
		
		if(refPO == null)
			return;
		
		MUNSDiscountTrx[] trxs = MUNSDiscountTrx.get(refPO);
		for(MUNSDiscountTrx trx : trxs)
		{
			if(po.get_TableName().equals(MOrder.Table_Name))
				trx.updateReserveQty(revert);
			else if(po.get_TableName().equals(MInvoice.Table_Name))
				trx.updateInvoicedQty(revert);
			else if(po.get_TableName().equals(MInOut.Table_Name))
				trx.updateShipReceiptQty(revert);
			else if(po.get_TableName().equals(MOrderLine.Table_Name))
				trx.updateReserveQty(revert);
			else if(po.get_TableName().equals(MInvoiceLine.Table_Name))
				trx.updateInvoicedQty(revert);
			else if(po.get_TableName().equals(MInOutLine.Table_Name))
				trx.updateShipReceiptQty(revert);
			else
				throw new AdempiereException("Unhandled table " + po.get_TableName());
		}
		
		PO[] pos = getLines(po);
		for(int i=0; i<pos.length; i++)
		{
			updateDiscountBudget(pos[i], revert);
		}
	}
	
	private String invoiceLineOnChange(PO po)
	{
		MInvoiceLine line = (MInvoiceLine) po;
		
		String msg = doValidateSwap(po);
		if(!Util.isEmpty(msg, true))
			return msg;
		
		if(!line.is_ValueChanged(MInvoiceLine.COLUMNNAME_QtyEntered))
			return null;
		
		try
		{
			int deleted = deleteDiscountTrx(line);
			line.setDiscount(Env.ZERO);
			line.setDiscountAmt(Env.ZERO);
			line.setQtyBonuses(Env.ZERO);
			line.setPrice(line.getPriceList());
			
			if(deleted == 0)
				return null;
			
			MInvoice invoice = (MInvoice) line.getC_Invoice();
			deleted = deleteDiscountTrx(invoice);
			
			for(MInvoiceLine otherLine : invoice.getLines())
			{
				if(otherLine.get_ID() == line.get_ID())
					continue;
				
				deleted = deleteDiscountTrx(otherLine);
			}
		}
		catch (Exception e)
		{
			msg = e.getMessage();
		}
		return msg;
	}
	
	private int deleteDiscountTrx(PO po)
	{
		int deleted = 0;
		MUNSDiscountTrx[] discsTrx = MUNSDiscountTrx.get(po);		
		for(MUNSDiscountTrx discTrx : discsTrx)
		{
			discTrx.deleteEx(true);
			deleted++;
		}
		return deleted;
	}
	
	private static final String[] 	LocValPart_ORDER = new String[] {"PST", "KTB", "TLB", "BTA"}; 
	private static final String 	GOODLOC_PREFIX = "Good-";
	private static final String		STOCKCARE_PREFIX = "StkCare-";
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private String validateOnShipmentProcessAutoComplete(PO po)
	{
		String event = Env.getContext(Env.getCtx(), "ON_IMPORT");
		boolean onTest = MSysConfig.getBooleanValue(MSysConfig.ON_TEST, false);
		boolean ok = (!Util.isEmpty(event, true) && event.equals("Y"))
				|| onTest;
		
		if(!ok)
			return null;
		
		MInOut io = (MInOut) po;
		Hashtable<String, PhysicalTmp> physicalTmpMap = new Hashtable<>();
		

		if(!io.getMovementType().equals(MInOut.MOVEMENTTYPE_CustomerShipment)
				&& !io.getMovementType().equals(MInOut.MOVEMENTTYPE_VendorReturns))
			return null;	
		
		for (MInOutLine line : io.getLines())
		{
			if(line.getM_Product().getM_AttributeSet_ID() > 0 && !io.isSOTrx()
					&& !io.getMovementType().equals(MInOut.MOVEMENTTYPE_VendorReturns))
			{	
				MAttributeSetInstance.initAttributeValuesFrom(
						line, 
						MInOutLine.COLUMNNAME_M_Product_ID, 
						MInOutLine.COLUMNNAME_M_AttributeSetInstance_ID, 
						po.get_TrxName());

				line.saveEx();
			}
		}
		
		MInOutLine[] lines = io.getLines();
		for(MInOutLine line : lines)
		{
			String keyTmp = "Stock-" + line.getM_Product_ID() + "-" + line.getM_Locator_ID() + "-" 
						+ line.getM_AttributeSetInstance_ID();
			PhysicalTmp tmp = physicalTmpMap.get(keyTmp);
			if(null == tmp)
			{
				tmp = new PhysicalTmp(
						line.getM_Locator_ID(), line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(), Env.ZERO);
				physicalTmpMap.put(keyTmp, tmp);
			}
			
			tmp.Quantity = tmp.Quantity.add(line.getMovementQty());
		}
		
		Properties ctx = io.getCtx();
		String trxName = io.get_TrxName();
		
		Hashtable<String, MMovement> mapMovement = new Hashtable<>();
		Hashtable<String, MInventory> mapInventory = new Hashtable<>();

		String IOOrgValue = MOrg.get(ctx, io.getAD_Org_ID()).getValue();
		String IODepoStr = IOOrgValue.substring(IOOrgValue.indexOf('-') + 1);
		String orgDivPart = IOOrgValue.substring(0, IOOrgValue.indexOf('-'));
		String orgDivPusat = orgDivPart + "-" + IODepoStr;
		
		int orgMoveFrom_ID = MOrg.get(ctx, orgDivPusat, trxName).get_ID();
		
		String locatorSQL = "SELECT M_Locator_ID FROM M_Locator WHERE Value=?";
		
		String documentNo = PREVIX_PHYSICAL_DOCNO.concat(io.getDocumentNo());
		MWarehouse whs = MWarehouse.get(ctx, io.getM_Warehouse_ID());
		String key = (new StringBuilder(documentNo).append(whs.getValue())).toString();
		
		for(PhysicalTmp required : physicalTmpMap.values())
		{
			BigDecimal ioOrgOnHand = MStorageOnHand.getQtyOnHandForLocator(
					required.M_Product_ID, required.M_Locator_ID, required.M_AttributeSetInstance_ID, po.get_TrxName());			
			
//			if(required.Quantity.compareTo(onHand) <= 0)
//				continue;

			MCost ioOrgCost = new Query(ctx, MCost.Table_Name, 
					"AD_Org_ID=? AND M_Product_ID=? AND M_CostElement_ID=1000004", trxName)
				.setParameters(io.getAD_Org_ID(), required.M_Product_ID)
				.first();
			
			ioOrgOnHand = ioOrgCost == null? Env.ZERO : 
				ioOrgCost.getCurrentQty().compareTo(ioOrgOnHand) < 0? ioOrgCost.getCurrentQty() : ioOrgOnHand;
			
			if (ioOrgOnHand.compareTo(required.Quantity) >= 0)
				continue;
			
			BigDecimal requiredQty = required.Quantity.subtract(ioOrgOnHand);
			
			MMovement move = mapMovement.get(key);
			
			String sql = "SELECT SUM(soh.QtyOnHand) FROM M_StorageOnHand soh, M_Locator loc "
						+ "WHERE soh.M_Product_ID=? AND soh.M_Locator_ID=loc.M_Locator_ID AND loc.Value IN (?, ?)";
			BigDecimal totalOHAtDepo = DB.getSQLValueBDEx(
					trxName, sql, required.M_Product_ID, GOODLOC_PREFIX + IOOrgValue, STOCKCARE_PREFIX + IOOrgValue);
			
			if (totalOHAtDepo != null && totalOHAtDepo.compareTo(requiredQty) < 0) 
			{
				String whGudang = "G." + IODepoStr;
				int whGudang_ID = DB.getSQLValueEx(trxName, "SELECT M_Warehouse_ID FROM M_Warehouse WHERE Value=?", whGudang);
				MStorageOnHand[] sohListAtGudang = 
						MStorageOnHand.getWarehouse(ctx, whGudang_ID, required.M_Product_ID, required.M_AttributeSetInstance_ID, 
								null, true, true, 0, trxName, false);
				
				for (MStorageOnHand sohAtGudang : sohListAtGudang) 
				{
					BigDecimal ohQty = sohAtGudang.getQtyOnHand();

					BigDecimal moveQty = ohQty.compareTo(requiredQty) >= 0? requiredQty : ohQty;
					if(ohQty.compareTo(requiredQty.setScale(0, BigDecimal.ROUND_UP)) >= 0)
						moveQty = requiredQty.setScale(0, BigDecimal.ROUND_UP);
					
					requiredQty = requiredQty.subtract(moveQty);
					
					move = createMovementToFulfillShipment(
							move, io, required, documentNo, orgMoveFrom_ID, sohAtGudang.getM_Locator_ID(), moveQty);
					
					if (requiredQty.signum() <=0)
						break;
				}
			}
			
			if (requiredQty.signum() <= 0) {
				mapMovement.put(key, move);
				continue;
			}
			
			for(String depoStr : LocValPart_ORDER) 
			{
				if (IODepoStr.equals(depoStr))
					continue;
				
				String depoLocator = 
					new  StringBuilder(GOODLOC_PREFIX).append(orgDivPart).append("-").append(depoStr).toString();
				
				int locFrom_ID = DB.getSQLValueEx(trxName, locatorSQL,  depoLocator);
				
				BigDecimal onHandDepo = 
					MStorageOnHand.getQtyOnHandForLocator(required.M_Product_ID, locFrom_ID,
														  required.M_AttributeSetInstance_ID, trxName);
				
				if (onHandDepo.signum() > 0) {
					
					MCost depoCost = new Query(ctx, MCost.Table_Name, 
							"AD_Org_ID=? AND M_Product_ID=? AND M_CostElement_ID=1000004", trxName)
						.setParameters(MLocator.get(ctx, locFrom_ID).getAD_Org_ID(), required.M_Product_ID)
						.first();
					
					if (depoCost == null || depoCost.getCurrentQty().signum() <= 0)
						continue;
					
					if (depoCost.getCurrentQty().compareTo(onHandDepo) < 0)
						onHandDepo = depoCost.getCurrentQty();
					
					BigDecimal moveQty = onHandDepo.compareTo(requiredQty) >= 0 ? requiredQty : onHandDepo;
					if(onHandDepo.compareTo(requiredQty.setScale(0, BigDecimal.ROUND_UP)) >= 0)
						moveQty = requiredQty.setScale(0, BigDecimal.ROUND_UP);
					
					requiredQty = requiredQty.subtract(moveQty);
					
					move = createMovementToFulfillShipment(
							move, io, required, documentNo, orgMoveFrom_ID, locFrom_ID, moveQty);
				}
				
				if (requiredQty.signum() <=0)
					break;
			}
			
			if (requiredQty.signum() <= 0) {
				mapMovement.put(key, move);
				continue;
			}
			
			// If still not fulfilled then move it from stock care locator. Start with IO's org stock care loc.
			String IOStkCareLocator = 
					new  StringBuilder(STOCKCARE_PREFIX).append(IOOrgValue).toString();
			int IOStkCareLocator_ID = DB.getSQLValueEx(trxName, locatorSQL,  IOStkCareLocator);
			BigDecimal stkCareIOOnHand = 
					MStorageOnHand.getQtyOnHandForLocator(required.M_Product_ID, IOStkCareLocator_ID,
														  required.M_AttributeSetInstance_ID, trxName);
			if (stkCareIOOnHand.signum() > 0) 
			{
				BigDecimal availableCostQty = ioOrgCost.getCurrentQty().subtract(ioOrgOnHand);
				if (availableCostQty.signum() > 0) 
				{
					stkCareIOOnHand = availableCostQty.compareTo(stkCareIOOnHand) >= 0? stkCareIOOnHand : availableCostQty;
					
					BigDecimal moveQty = stkCareIOOnHand.compareTo(requiredQty) >= 0 ? requiredQty : stkCareIOOnHand;
					if(stkCareIOOnHand.compareTo(requiredQty.setScale(0, BigDecimal.ROUND_UP)) >= 0)
						moveQty = requiredQty.setScale(0, BigDecimal.ROUND_UP);
					
					requiredQty = requiredQty.subtract(moveQty);
					
					move = createMovementToFulfillShipment(
							move, io, required, documentNo, orgMoveFrom_ID, IOStkCareLocator_ID, moveQty);
				}
			}
			
			if (requiredQty.signum() <=0) {
				mapMovement.put(key, move);
				continue;
			}
			
			// If still not fulfilled then move it from other depos' stock care.
			for(String depoStr : LocValPart_ORDER) 
			{
				if (IODepoStr.equals(depoStr))
					continue;
				
				String depoLocator = 
					new  StringBuilder(STOCKCARE_PREFIX).append(orgDivPart).append("-")
					.append(depoStr).toString();
				
				int locFrom_ID = DB.getSQLValueEx(trxName, locatorSQL,  depoLocator);
				
				BigDecimal onHandDepo = 
					MStorageOnHand.getQtyOnHandForLocator(required.M_Product_ID, locFrom_ID,
														  required.M_AttributeSetInstance_ID, trxName);
				
				if (onHandDepo.signum() > 0) {
					
					BigDecimal moveQty = onHandDepo.compareTo(requiredQty) >= 0 ? requiredQty : onHandDepo;
					if(onHandDepo.compareTo(requiredQty.setScale(0, BigDecimal.ROUND_UP)) >= 0)
						moveQty = requiredQty.setScale(0, BigDecimal.ROUND_UP);
					
					requiredQty = requiredQty.subtract(moveQty);
					
					move = createMovementToFulfillShipment(
							move, io, required, documentNo, orgMoveFrom_ID, locFrom_ID, moveQty);
				}
				
				if (requiredQty.signum() <=0)
					break;
			}			

			if (requiredQty.signum() <= 0) {
				mapMovement.put(key, move);
			}
			else { // Masih belum terpenuhi juga, maka buat physical.
				
				MCost cost = new Query(ctx, MCost.Table_Name, 
						"AD_Org_ID=? AND M_Product_ID=? AND M_CostElement_ID=1000004", trxName)
					.setParameters(io.getAD_Org_ID(), required.M_Product_ID)
					.first();
				
				MInventory inventory = mapInventory.get(key);
				
				
				if(null == inventory)
				{
					inventory = new MInventory(whs, trxName);
					inventory.setDocumentNo(documentNo);
					if (cost == null 
							|| cost.getCurrentQty().signum() <= 0 
							|| cost.getCurrentQty().compareTo(requiredQty) < 0) {
						inventory.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_MaterialPhysicalInventoryImport));
						ioOrgOnHand = Env.ZERO;
					}
					else {
						inventory.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_MaterialPhysicalInventory));
					}
					inventory.setDescription("Atomatically created to meet the needs of items on shipment document "
							.concat(io.getDocumentNo()));
					inventory.setMovementDate(io.getMovementDate());
					inventory.saveEx();
					mapInventory.put(key, inventory);
				}
				
				MInventoryLine iLine = new MInventoryLine(
						inventory, required.M_Locator_ID, required.M_Product_ID
						, required.M_AttributeSetInstance_ID
						, ioOrgOnHand, required.Quantity);
				
				iLine.setIsUPCBasedCounter(false);
				iLine.setInventoryType(MInventoryLine.INVENTORYTYPE_InventoryDifference);
				
				if (inventory.getC_DocType_ID() == MDocType.getDocType(MDocType.DOCBASETYPE_MaterialPhysicalInventoryImport)) 
				{
					String whereClause = "M_Product_ID=? AND C_Invoice_ID=? AND PriceList > 0";
					MInvoiceLine invLine = new Query(ctx, MInvoiceLine.Table_Name, whereClause, trxName)
						.setParameters(required.M_Product_ID, io.getC_Invoice_ID()).first();
				
					if (invLine != null) {
						BigDecimal landedCost = invLine.getPriceList().multiply(BigDecimal.valueOf(0.12));
						
						iLine.setNewCostPrice(requiredQty.multiply(landedCost));
					}
				}
				
				iLine.saveEx();
				
//				return "Tidak ada stok yang dapat dipindahkan untuk DO Pengiriman " + io.getDocumentNo() +
//						" untuk produk " + MProduct.get(ctx, required.M_Product_ID);
			}
		}
		
		for(MMovement move : mapMovement.values())
		{
			try
			{
				ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(
						move, DocAction.ACTION_Complete);
				if(pi.isError())
				{
					return pi.getSummary();
				}
			}
			catch (Exception e)
			{
				return e.getMessage();
			}
		}
		
		for(MInventory inventory : mapInventory.values())
		{
			try
			{
				ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(
						inventory, DocAction.ACTION_Complete);
				if(pi.isError())
				{
					return pi.getSummary();
				}
			}
			catch (Exception e)
			{
				return e.getMessage();
			}
		}
		
		return null;
	}
	
	private final String PREVIX_PHYSICAL_DOCNO = "IO-";
	
	private MMovement createMovementToFulfillShipment(MMovement move,
			MInOut io, PhysicalTmp required, String documentNo, int orgFrom_ID, int locFrom_ID, BigDecimal qty)
	{
		if (move == null) {
			move = new MMovement(io.getCtx(), 0, io.get_TrxName());
			move.setDocumentNo(documentNo);
			move.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_MaterialMovement));
			move.setAD_Org_ID(orgFrom_ID);
			move.setDescription("Atomatically created to meet the needs of items on shipment document "
					.concat(io.getDocumentNo()));
			move.setMovementDate(io.getMovementDate());
			move.setDestinationWarehouse_ID(io.getM_Warehouse_ID());
			
			move.saveEx();
		}
		
		MMovementLine moveLine = new MMovementLine(move);
		
		int orgLocator_ID = MLocator.get(io.getCtx(), locFrom_ID).getAD_Org_ID();
		moveLine.setAD_Org_ID(orgLocator_ID);
		
		if (required.M_AttributeSetInstance_ID > 0)
			moveLine.setM_AttributeSetInstance_ID(required.M_AttributeSetInstance_ID);
		
		moveLine.setM_Product_ID(required.M_Product_ID);
		moveLine.setM_LocatorTo_ID(required.M_Locator_ID);
		moveLine.setM_Locator_ID(locFrom_ID);
		moveLine.setMovementQty(qty);
		
		moveLine.saveEx();
		
		return move;
	}
	
	/*
	private String allocateCNOfAR(MPaymentAllocate allocate, MInvoice invoice)
	{
		String c_invoices_id = DB.getSQLValueString(
				allocate.get_TrxName(), "SELECT ARRAY_TO_STRING(ARRAY_AGG("
						+ " C_Invoice_ID), ';') FROM C_Invoice"
						+ " WHERE CN_Invoice_ID = ? AND IsPaid = 'N'"
				, allocate.getC_Invoice_ID());
		
		if(null == c_invoices_id || c_invoices_id.length() == 0)
			return null;
		
		String[] c_invStrings = c_invoices_id.split(";");
		if(c_invStrings == null || c_invStrings.length == 0)
			return null;
		
		for(int i=0; i<c_invStrings.length; i++)
		{
			Integer c_invoice_id = new Integer(c_invStrings[i]);
			String sql = "SELECT 1 FROM C_PaymentAllocate WHERE"
					+ " C_Payment_ID = ? AND C_Invoice_ID = ?";
			boolean exists = DB.getSQLValue(allocate.get_TrxName(), sql, 
					allocate.getC_Payment_ID(), c_invoice_id) > 0;
			if(exists)
			{
				continue;
			}
			
			BigDecimal cnInvoiceOpen = DB.getSQLValueBD(allocate.get_TrxName(), 
					"SELECT InvoiceOpen(?, ?)", c_invoice_id, 0);
			
			MPaymentAllocate cnAllocate = new MPaymentAllocate(allocate.getCtx(), 
					0, allocate.get_TrxName());
			cnAllocate.setAD_Org_ID(allocate.getAD_Org_ID());
			cnAllocate.setC_Payment_ID(allocate.getC_Payment_ID());
			cnAllocate.setC_Invoice_ID(c_invoice_id);
			cnAllocate.setAmount(cnInvoiceOpen);
			cnAllocate.setOverUnderAmt(Env.ZERO);
			cnAllocate.setInvoiceAmt(cnInvoiceOpen);
			cnAllocate.setPayToOverUnderAmount(cnInvoiceOpen);
			try
			{
				cnAllocate.saveEx();
			}
			catch (Exception e)
			{
				return e.getMessage();
			}
		}
		return null;
	}
	*/
	private String allocateAROfCN(MPaymentAllocate allocate, MInvoice invoice)
	{
		if (invoice.getCN_Invoice_ID() == 0)
		{
			return null;
		}
		else if (invoice.getCN_Invoice().isPaid())
		{
			return null;
		}
		
		BigDecimal arInvoiceOpen = DB.getSQLValueBD(allocate.get_TrxName(), 
				"SELECT InvoiceOpen(?,?)", invoice.getCN_Invoice_ID(), 0);
		
		String sql = "SELECT 1 FROM C_PaymentAllocate WHERE"
				+ " C_Payment_ID = ? AND C_Invoice_ID = ?";
		
		boolean exists = DB.getSQLValue(allocate.get_TrxName(), sql, 
				allocate.getC_Payment_ID(), invoice.getCN_Invoice_ID()) > 0;
				
		if(exists)
		{
			return null;
		}
		
		BigDecimal cnInvoiceOpen = DB.getSQLValueBD(allocate.get_TrxName(), 
				"SELECT InvoiceOpen(?, ?)", invoice.get_ID(), 0);
		
		BigDecimal overUnderPayment = arInvoiceOpen.add(cnInvoiceOpen);
		
		MPaymentAllocate cnAllocate = new MPaymentAllocate(allocate.getCtx(), 
				0, allocate.get_TrxName());
		cnAllocate.setAD_Org_ID(allocate.getAD_Org_ID());
		cnAllocate.setC_Payment_ID(allocate.getC_Payment_ID());
		cnAllocate.setC_Invoice_ID(invoice.getCN_Invoice_ID());
		cnAllocate.setAmount(cnInvoiceOpen.negate());
		cnAllocate.setOverUnderAmt(overUnderPayment);
		cnAllocate.setInvoiceAmt(arInvoiceOpen);
		cnAllocate.setPayToOverUnderAmount(cnInvoiceOpen.negate());

		try
		{
			cnAllocate.saveEx();
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
		return null;
	}
	
	private String checkDiscount (PO po, Timestamp date)
	{
		if(po.get_TableName().equals(MInvoice.Table_Name) && !checkDiffInv)
			return "";
		if(po.get_TableName().equals(MOrder.Table_Name) && !checkDiffOrd)
			return "";
		
		StringBuilder prepareMsg = new StringBuilder();
		MUNSDiscountTrx[] trxs = MUNSDiscountTrx.get(po);
		for(MUNSDiscountTrx trx : trxs)
		{
			if(trx.isOverBudget())
			{
				prepareMsg.append(
						"Quantiti over budget discount trx " + trx.getName());
			}
			else if (trx.isNeedRecalculate())
			{
				String msg = "Some transaction has changed by user."
						+ " to take the efect of discount you must to "
						+ " run recalculate process. Automaticaly run"
						+ " calculation ? ";
				int retVal = MessageBox.showMsg(po, po.getCtx(), msg, 
						"Recaulculate Discount ?", MessageBox.YESNO, 
						MessageBox.ICONWARNING);
				
				if (retVal == MessageBox.RETURN_NO)
				{
					throw new AdempiereUserError(
							"Please run recalculate discount first");
				}
				
				I_DiscountModel model = po.get_TableName().equals(
						MOrder.Table_Name) ? new UNSOrderDiscountModel(po) 
					: po.get_TableName().equals(MInvoice.Table_Name) ? 
							new UNSInvoiceDiscountModel(po) : null;
				boolean isInvoice = po.get_TableName().equals(
						MOrder.Table_Name) ? false : true;
				CalculateDiscount calc = new CalculateDiscount(model, isInvoice, po.get_ID());
				calc.setIsUpdate(true);
				calc.run();
			}
			else if (trx.isChangedByUser())
			{
				prepareMsg.append(
						"Custom discount on trx " + trx.getName());
			}
			
			prepareMsg.append(checkEffectivedateOfDiscount(trx, date));
		}
		
		PO[] pos = getLines(po);
		for(PO poLine : pos)
		{
			prepareMsg.append(checkDiscount(poLine, date));
		}
		
		String msg = prepareMsg.toString();
		if(Util.isEmpty(msg, true))
		{
			return "";
		}
		
		return msg;
	}
	
	/**
	 * 
	 * @param order
	 * @return
	 */
	private String doValidateSwap (PO po)
	{
		String sql = "SELECT M_InOutLine_ID FROM M_InOutLine WHERE "
				+ " C_OrderLineSwap_ID = ?";
		
		int value = DB.getSQLValue(po.get_TrxName(), sql, 
				po instanceof I_C_Invoice ? po.get_ValueAsInt("C_OrderLine_ID")
						: po.get_ID());
		if (value <= 0)
			return null;
		
		sql = "SELECT PriceActual FROM C_InvoiceLine WHERE M_InOutLine_ID = ?";
		BigDecimal cnActual = DB.getSQLValueBD(po.get_TrxName(), sql, value);
		BigDecimal priceActual = (BigDecimal) po.get_Value("PriceActual");
		BigDecimal diff = cnActual.subtract(priceActual);
		
		if (diff.signum() != 0)
		{
			return Msg.getMsg(Env.getCtx(), "OrderInvoiceSwapDifferencePrice");
		}
		
		return null;
	}
	
	private String doValidateOnCompleteSwapInv (PO po)
	{
		int C_Order_ID = po.get_ValueAsInt("C_Order_ID"); 
		if (C_Order_ID <= 0)
		{
			return null;
		}
		
		String sql = "SELECT Distinct(C_Invoice_ID) FROM C_InvoiceLine WHERE "
				+ " M_InOutLine_ID IN (SELECT M_InOutLine_ID FROM M_InOutLine "
				+ " WHERE C_OrderLineSwap_ID IN (SELECT C_OrderLine_ID FROM "
				+ " C_OrderLine WHERE C_Order_ID = ?))";
		int cn_Invoice_ID = DB.getSQLValue(po.get_TrxName(), sql, C_Order_ID);
		if(cn_Invoice_ID <= 0)
		{
			return null;
		}
		
		sql = "UPDATE C_Invoice SET CN_Invoice_ID = ? WHERE C_Invoice_ID = ?";
		int value = DB.executeUpdateEx(sql, new Object[]{po.get_ID(), 
				cn_Invoice_ID}, 
				po.get_TrxName());
		if(value == -1)
			return "Failed when update invoice";
		
		MPayment payment = new MPayment(po.getCtx(), 0, po.get_TrxName());
		payment.setAD_Org_ID(po.getAD_Org_ID());
		payment.setC_DocType_ID(true);
		payment.setC_BPartner_ID(po.get_ValueAsInt("C_BPartner_ID"));
		payment.setBankCash((new Query(po.getCtx(),MBankAccount.Table_Name,
				"AD_Org_ID=? AND C_Currency_ID=?",po.get_TrxName())
		.setParameters(po.getAD_Org_ID(), po.get_Value("C_Currency_ID"))
		.setOrderBy("IsDefault DESC")
		.first()).get_ID(), true, MPayment.TENDERTYPE_Cash);
		payment.setDateTrx((Timestamp)po.get_Value("DateInvoiced"));
		payment.setDateAcct(payment.getDateTrx());
		payment.setC_Currency_ID(po.get_ValueAsInt("C_Currency_ID"));
		payment.saveEx();
		
		MPaymentAllocate pa = new MPaymentAllocate(po.getCtx(), 0, 
				po.get_TrxName());
		
		BigDecimal pay = DB.getSQLValueBD(po.get_TrxName(), 
				"SELECT InvoiceOpen(?,?)", cn_Invoice_ID,0);
		
		pa.setAD_Org_ID(payment.getAD_Org_ID());
		pa.setC_Payment_ID(payment.get_ID());
		pa.setC_Invoice_ID(cn_Invoice_ID);
		pa.setAmount(pay);
		pa.setPayToOverUnderAmount(pay);
		pa.setOverUnderAmt(Env.ZERO);
		pa.setInvoiceAmt(pay);
		pa.saveEx();
		
		boolean ok = payment.processIt(DocAction.ACTION_Complete);
		if (!ok)
		{
			return "Failed when complete payment :: " + payment.getProcessMsg();
		}
		
		return null;
	}
	
	private String checkInvoiceCountLimit(PO po)
	{		
		if(!po.get_ValueAsBoolean("IsSOTrx"))
			return null;
		MBPartner bp = new MBPartner(po.getCtx(), po.get_ValueAsInt("C_BPartner_ID"), po.get_TrxName());
		if(bp == null || bp.get_ID() <= 0)
			return null;
		if(!bp.isLimitedSO())
			return null;
		if(bp.get_ValueAsInt("InvoiceCountLimit") <= 0)
			return null;
		String listDocNo = null;
		
		int partner_ID = bp.get_ID();
		int org_ID = po.getAD_Org_ID();
		boolean tmp =  MSysConfig.getBooleanValue(MSysConfig.SEE_TO_TMP_RECORD, false);
		
		listDocNo = DB.getSQLValueString(po.get_TrxName(), "SELECT InvoiceCountunPaid(?,?,?)", 
				new Object[]{partner_ID, org_ID, tmp});
		
		if(!Util.isEmpty(listDocNo, true))
		{
			String DocNos[] = listDocNo.split(";");
			if((DocNos.length + 1) > bp.get_ValueAsInt("InvoiceCountLimit"))
				return "Invoice Count Limit " + bp.get_ValueAsInt("InvoiceCountLimit") + " for Customer :: " + bp.getName()
						+ " , " + (DocNos.length) + " invoices not paid. (" + listDocNo + ")";
		}
		
		return null;
	}
	
	private String checkCreditLimit(PO po)
	{
		if(!po.get_ValueAsBoolean("IsSOTrx"))
			return null;
		MBPartner bp = new MBPartner(po.getCtx(), po.get_ValueAsInt("C_BPartner_ID"), po.get_TrxName());
		if(bp == null || bp.get_ID() <= 0)
			return null;
		if(!bp.isLimitedSO())
			return null;

		BigDecimal balance = Env.ZERO;
		if(!MSysConfig.getBooleanValue(MSysConfig.SEE_TO_TMP_RECORD, false))
			balance = bp.getTotalOpenBalance();
		else
			balance = DB.getSQLValueBD(po.get_TrxName(), "SELECT getTmpOpenBalance(?)", bp.get_ID());
		
		BigDecimal compare = ((BigDecimal) po.get_Value("GrandTotal")).add(balance);
		if(bp.getSO_CreditLimit().compareTo(compare) == -1)
			return "Over Credit Limit. Balance : " + balance.setScale(2)
					+ " , Credit Limit : " + bp.getSO_CreditLimit().setScale(2);
		
		return null;
	}
	
	private String createInternalUserFromInout(PO po)
	{ 
		String sql = "SELECT COUNT(*) FROM UNS_ArmadaCost ac WHERE EXISTS"
				+ " (SELECT 1 FROM M_InOutLine iol WHERE iol.M_InOutLine_ID = ac.M_InOutLine_ID"
				+ " AND iol.M_InOut_ID=?)";
		int count = DB.getSQLValue(po.get_TrxName(), sql, po.get_ID());
		if(count == 0)
			return null;
		
		sql = "SELECT io.AD_Org_ID, io.M_Warehouse_ID, io.MovementDate, io.DocumentNo, ARRAY_TO_STRING(ARRAY_AGG(ar.Name), ' :: '),"
				+ " ac.C_Charge_ID, SUM(ac.Qty), iol.M_Locator_ID, iol.M_Product_ID, iol.M_AttributeSetInstance_ID"
				+ " FROM M_InOut io"
				+ " INNER JOIN M_InOutLine iol ON iol.M_InOut_ID = io.M_InOut_ID"
				+ " INNER JOIN UNS_ArmadaCost ac ON ac.M_InOutLine_ID = iol.M_InOutLine_ID"
				+ " INNER JOIN UNS_Armada ar ON ar.UNS_Armada_ID = ac.UNS_Armada_ID"
				+ " WHERE io.M_InOut_ID = ?"
				+ " GROUP BY io.M_InOut_ID, iol.M_InOutLine_ID, ac.C_Charge_ID";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			stmt = DB.prepareStatement(sql, po.get_TrxName());
			stmt.setInt(1, po.get_ID());
			rs = stmt.executeQuery();
			MInventory inven = new MInventory(po.getCtx(), 0, po.get_TrxName());
			while (rs.next())
			{
				if(inven.get_ID() <= 0)
				{
					inven.setAD_Org_ID(rs.getInt(1));
					inven.setM_Warehouse_ID(rs.getInt(2));
					inven.setMovementDate(rs.getTimestamp(3));
					int dt = DB.getSQLValue(po.get_TrxName(), "SELECT C_DocType_ID FROM C_DocType WHERE DocSubTypeInv = 'IU'");
					inven.setC_DocType_ID(dt);
					inven.setDescription("Auto Generated from Receipt :: " + rs.getString(4));
					if(!inven.save())
						return inven.getProcessMsg();			
				}
				
				MInventoryLine line = new MInventoryLine(inven, rs.getInt(8),
						rs.getInt(9), rs.getInt(10), null, null, rs.getBigDecimal(7));
				line.setC_Charge_ID(rs.getInt(6));
				line.setDescription("Generated for Armada (" + rs.getString(5) + ")");
				if(!line.save())
				{
					return "Failed when trying create internal use lines";
				}
			}
			boolean ok = inven.processIt("CO");
			if(!ok)
				return inven.getProcessMsg();
		} 
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		
		return null;
	}
	
	private String validateOnOrderToInvoice(PO po)
	{
		if(po.get_ValueAsBoolean("IsSOTrx") || po.get_ValueAsInt("C_Order_ID") <= 0)
			return "";
		if(!po.is_ValueChanged("C_Order_ID"))
			return "";
		MOrder order = new MOrder(po.getCtx(), po.get_ValueAsInt("C_Order_ID"), po.get_TrxName());
		MDocType dt = MDocType.get(po.getCtx(), order.getC_DocType_ID());
		if(!Util.isEmpty(dt.getDocSubTypeSO(), true) && (dt.getDocSubTypeSO().equals(MDocType.DOCSUBTYPESO_CashOrder) ||
				dt.getDocSubTypeSO().equals(MDocType.DOCSUBTYPESO_PrepayOrder)))
			return "";
		boolean isItem = false;
		for(MOrderLine line : order.getLines())
		{
			if(line.getM_Product().getProductType().equals("I"))
			{
				isItem = true;
				break;
			}
		}
		if(!isItem)
			return "";
		
		String sql = "SELECT COUNT(o.*) FROM C_Order o"
				+ " INNER JOIN M_InOut io ON io.C_Order_ID = o.C_Order_ID"
				+ " WHERE o.C_Order_ID = ? AND io.DocStatus IN ('CO', 'CL')";
		boolean valid = DB.getSQLValue(po.get_TrxName(), sql,
				order.get_ID()) > 0;
		if(!valid)
			return "Order must have a complete receipt";
		
		return null;
	}
	
	private String checkManualDiscount(PO po)
	{
		if(!po.get_ValueAsBoolean("IsSOTrx") || po.get_Value("DocStatus").equals("CO")
				|| po.get_Value("DocStatus").equals("CL")
					|| po.get_Value("DocStatus").equals("RE")
						|| po.get_Value("DocStatus").equals("VO"))
			return "";
		
		String parent = null;
		String discColumn = null;
		if(po instanceof MInvoice)
		{
			parent = "C_Invoice";
			discColumn = "AddDiscountAmt";
		}
		else if(po instanceof MOrder)
		{
			parent = "C_Order";
			discColumn = "DiscountAmt";
		}
		else
			return "";
		
		BigDecimal addDiscAmt = (BigDecimal) po.get_Value(discColumn);
		
		if(addDiscAmt.signum() == 0)
			return "";
		
		BigDecimal totalLines = (BigDecimal) po.get_Value("TotalLines");
		BigDecimal discAmount = Env.ZERO;
		MUNSDiscountTrx[] discountsTrx = MUNSDiscountTrx.getOf(parent, po.get_ID(), po.get_TrxName());
		MBPartner cust = MBPartner.get(po.getCtx(), po.get_ValueAsInt("C_BPartner_ID"));
		
		for(MUNSDiscountTrx trx : discountsTrx)
		{
			StringBuilder sql = new StringBuilder();
			if(trx.getProductBonus_ID() > 0 && trx.getBonusesPrice().signum() > 0 && trx.getQtyBonuses().signum() > 0)
			{
				sql.append("SELECT COUNT(*) FROM ").append(parent).append("Line")
				.append(" WHERE M_Product_ID = ").append(trx.getProductBonus_ID())
				.append(" AND IsProductBonuses = 'Y' AND ").append(parent).append("_ID = ?");
				if(DB.getSQLValue(po.get_TrxName(), sql.toString(), po.get_ID()) > 0)
					continue;
				
				discAmount = discAmount.add(trx.getBonusesPrice().multiply(trx.getQtyBonuses()));
			}
		}
		addDiscAmt = addDiscAmt.subtract(discAmount);
		if(addDiscAmt.compareTo(Env.ONE) == 1)
		{
			BigDecimal divide = addDiscAmt.divide(totalLines, 5, RoundingMode.HALF_UP);
			BigDecimal inPerc = divide.multiply(Env.ONEHUNDRED);
			
			if(inPerc.compareTo(cust.getLimit_Discount()) == 1)
				return "Over limit discount. Limit discount partner " + cust.getLimit_Discount() + "%";
		}
		
		return "";
	}
	
	private String isInvoiceAllocated(PO po)
	{
		String errormsg = null;
		String sql = "SELECT ARRAY_TO_STRING(ARRAY_AGG(p.DocumentNo), ';') FROM"
				+ " C_Payment p WHERE (p.C_Invoice_ID = ? OR C_Payment_ID IN (SELECT C_Payment_ID FROM "
				+ "C_PaymentAllocate pa WHERE pa.C_Invoice_ID = ?)) AND DocStatus NOT IN ('VO','RE')";
		String documentno = DB.getSQLValueString(
				po.get_TrxName(), sql, po.get_ID(), po.get_ID() );
		if(documentno != null)
			errormsg = "Invoice has allocated. Please check Payment with Document No = "+ documentno;
		
		return errormsg;
	}
}

class PhysicalTmp {
	int M_Locator_ID = 0;
	int M_Product_ID = 0;
	int M_AttributeSetInstance_ID = 0;
	BigDecimal Quantity = Env.ZERO;
	
	public PhysicalTmp(
			int M_Locator_ID, int M_Product_ID,int M_AttributeSetInstance_ID, BigDecimal qty)
	{
		this.M_Locator_ID = M_Locator_ID;
		this.M_Product_ID = M_Product_ID;
		this.M_AttributeSetInstance_ID = M_AttributeSetInstance_ID;
		this.Quantity = qty;
	}
}

class ThreadSave implements Runnable {
	private Trx m_trx;

	@Override
	public void run() {
		m_trx.commit();
		m_trx.close();
	}
	
	public ThreadSave(Trx trx) {
		m_trx = trx;
	}
}
