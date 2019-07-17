/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInOutLine;
import org.compiere.model.MLocator;
import org.compiere.model.MMovementConfirm;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import com.uns.base.model.Query;

import com.unicore.base.model.MInOut;
import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author UNTA_Andy
 * 
 */
public class MUNSPackingList extends X_UNS_PackingList implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2162010234301294254L;

	/**
	 * @param ctx
	 * @param UNS_PackingList_ID
	 * @param trxName
	 */
	public MUNSPackingList(Properties ctx, int UNS_PackingList_ID, String trxName) 
	{
		super(ctx, UNS_PackingList_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPackingList(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}

	/**************************************************************************
	 * Process document
	 * 
	 * @param processAction document action
	 * @return true if performed
	 */
	public boolean processIt(String processAction) 
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	} // processIt

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;
	private MUNSPackingListOrder[] m_orders;

	/**
	 * Unlock Document.
	 * 
	 * @return true if success
	 */
	public boolean unlockIt() 
	{
		if (log.isLoggable(Level.INFO))
			log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	} // unlockIt

	/**
	 * Invalidate Document
	 * 
	 * @return true if success
	 */
	public boolean invalidateIt() 
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		setProcessed(false);
		setDocAction(DOCACTION_Prepare);
		return true;
	} // invalidateIt

	public  void createShipmentInvoice(MUNSPackingList pl) 
	{
		for (MUNSPackingListOrder plo : pl.getOrders())
		{
			boolean isAllowedToOverwriteInvoice = false;
			if (plo.getC_Invoice_ID() == 0 && plo.getC_Order_ID() == 0)
			{
				throw new AdempiereUserError("PL Order does not have invoice and order.");
			}
			
			MOrder order = null;
			MInvoice invoice = null;
			if(plo.getC_Order_ID() > 0)
				
			{
				order = new MOrder(plo.getCtx(), plo.getC_Order_ID(), plo.get_TrxName());
				String orderDocSubtype = MDocType.get(getCtx(), order.getC_DocTypeTarget_ID()).getDocSubTypeSO();
				if(orderDocSubtype.equals(MDocType.DOCSUBTYPESO_CashOrder)
						|| orderDocSubtype.equals(MDocType.DOCSUBTYPESO_PrepayOrder))
				{
					if(plo.getC_Invoice_ID() == 0)
					{
						throw new AdempiereException("Cash Order or Prepay Order has not been invoiced yet.");
					}
					
					invoice = new MInvoice(getCtx(), plo.getC_Invoice_ID(), get_TrxName());
					if(!invoice.isComplete())
					{
						throw new AdempiereUserError("Invoice of Cash Order or Prepay Order is not completed.");
					}
					isAllowedToOverwriteInvoice = !invoice.isComplete();	
				}
			}
			
			if(plo.getC_Invoice_ID() > 0 && invoice == null)
			{
				invoice = new MInvoice(getCtx(), plo.getC_Invoice_ID(), get_TrxName());
				isAllowedToOverwriteInvoice = !invoice.isComplete();
				if(isAllowedToOverwriteInvoice && invoice.getDateInvoiced() != getDateDoc())
				{
					invoice.setDateInvoiced(getDateDoc());
					invoice.setDateAcct(getDateDoc());
					invoice.saveEx();
				}
			}
			
			MInOut io = null;
			if (plo.getM_InOut_ID() == 0)
			{
				if (order != null)
				{
					io = new MInOut(order, MDocType.getDocType(MDocType.DOCBASETYPE_MaterialDelivery),
									pl.getDateDoc());	
					io.setM_Warehouse_ID(plo.getM_Warehouse_ID());
				}
				else if (invoice != null)
				{
					io = new MInOut(invoice, MDocType.getDocType(MDocType.DOCBASETYPE_MaterialDelivery),
									pl.getDateDoc(), plo.getM_Warehouse_ID());
				}
				
				if(getReference_ID() > 0)
					io.setDocumentNo(oldDocumentNo(plo.getReference_ID(), false));
				io.saveEx();
				plo.setM_InOut_ID(io.get_ID());
				plo.saveEx();
			} 
			else
			{
				io = new MInOut(plo.getCtx(), plo.getM_InOut_ID(), plo.get_TrxName());
				if(!io.isComplete() && io.getMovementDate() != getDateDoc())
				{
					io.setMovementDate(getDateDoc());
					io.setDateAcct(getDateDoc());
					io.saveEx();
				}
			}

			if (invoice == null)
			{
				invoice = new MInvoice(order, MDocType.getDocType(MDocType.DOCBASETYPE_ARInvoice), pl.getDateDoc());
				invoice.setOrder(order);
				if(plo.getReference_ID() > 0)
					invoice.setDocumentNo(oldDocumentNo(plo.getReference_ID(), true));
				invoice.setGridTab(this.getGridTab());
				invoice.saveEx();
				io.setC_Invoice_ID(invoice.get_ID());
				io.saveEx();
				plo.setC_Invoice_ID(invoice.get_ID());
				plo.saveEx();
				isAllowedToOverwriteInvoice = true;
			}
			else
			{
				if(!io.isComplete())
				{
					io.setC_Invoice_ID(invoice.get_ID());
					io.saveEx();
				}
			}
			
			if (order != null && invoice != null &&  invoice.getC_Order_ID() != order.get_ID())
			{
				throw new AdempiereException("Invoice and Order not matched.");
			}
			else if (order != null && io != null && io.getC_Order_ID() != order.get_ID())
			{
				throw new AdempiereException("Order and Shipment not matched.");
			}
			else if (io != null && invoice != null && io.getC_Invoice_ID() != invoice.get_ID())
			{
				throw new AdempiereException("Shipment and Invoice not matched.");
			}
			
			for (MUNSPackingListLine pll : plo.getLines())
			{
				MOrderLine line = null;
				boolean isFullShipment = false;
				BigDecimal qtyBonus = Env.ZERO;
				if(pll.getC_OrderLine_ID() > 0)
				{
					line = new MOrderLine(pll.getCtx(), pll.getC_OrderLine_ID(), pll.get_TrxName());
					isFullShipment = line.getQtyOrdered().doubleValue()
							== pll.getMovementQty().doubleValue();
					qtyBonus = line.getQtyBonuses();
				}
				
				MInvoiceLine iline = null;
				MInOutLine ioline = null;
				if (pll.getC_InvoiceLine_ID() == 0)
				{
					if(!isAllowedToOverwriteInvoice)
					{
						throw new AdempiereException("Can't add line to the completed invoice.");
					}
					
					iline = new MInvoiceLine(invoice);
					iline.setOrderLine(line);
					iline.setDiscountAmt(line.getDiscountAmt());
					if(isFullShipment)
					{
						iline.setQty(pll.getMovementQty().subtract(qtyBonus));
						iline.setQtyBonuses(qtyBonus);
					}
					else
					{
						iline.setQty(pll.getMovementQty());
					}
					
					iline.saveEx();
					pll.setC_InvoiceLine_ID(iline.get_ID());
					pll.setAutomatic(true);
					pll.saveEx();
					pll.setAutomatic(false);
				}
				else
				{
					iline = new MInvoiceLine(pll.getCtx(), pll.getC_InvoiceLine_ID(), pll.get_TrxName());
					if(iline.getQtyInvoiced().compareTo(pll.getMovementQty()) != 0
							&& isAllowedToOverwriteInvoice)
					{
						//hanya masuk ke sini jika invoicenya belum complete.
						// ini bisa terjadi jika user membuat packing list berdasarkan order dan ordernya bukan prepay atau cash.
						if (isFullShipment)
						{
							iline.setQtyEntered(pll.getMovementQty().subtract(qtyBonus));
							iline.setQtyBonuses(qtyBonus);
						}
						else
						{
							iline.setQty(pll.getMovementQty());
						}
						
						iline.saveEx();
					}
				}

				if (pll.getM_InOutLine_ID() == 0)
				{
					ioline = new MInOutLine(io);
					if (!isPickup())
					{
						MWarehouse whs = MWarehouse.get(getCtx(), plo.getM_Warehouse_ID());
						ioline.setM_Locator_ID(whs.getIntransitCustomerLocator_ID(true));
					}
					else
					{
						ioline.setM_Locator_ID(pll.getM_Locator_ID());
					}
					
					ioline.setInvoiceLine(iline, ioline.getM_Locator_ID(), pll.getTargetQty());
					ioline.setQty(pll.getMovementQty());
					ioline.saveEx();
					pll.setM_InOutLine_ID(ioline.get_ID());
					pll.setAutomatic(true);
					pll.saveEx();
					pll.setAutomatic(false);
				} 
				else
				{
					ioline = new MInOutLine(pll.getCtx(), pll.getM_InOutLine_ID(), pll.get_TrxName());
					if(ioline.getMovementQty().compareTo(pll.getMovementQty()) != 0)
					{
						ioline.setQty(pll.getMovementQty());
						ioline.saveEx();
					}
				}
				
				if(ioline.getC_OrderLine_ID() == 0 )
				{
					ioline.setC_OrderLine_ID(line.get_ID());
					ioline.saveEx();
				}
				if(iline.getM_InOutLine_ID() == 0)
				{
					iline.setM_InOutLine_ID(ioline.get_ID());
					iline.saveEx();
				}
			}
		}
	}

	/**
	 * Approve Document
	 * 
	 * @return true if success
	 */
	public boolean approveIt() {
		if (log.isLoggable(Level.INFO))
			log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	} // approveIt

	/**
	 * Reject Approval
	 * 
	 * @return true if success
	 */
	public boolean rejectIt() {
		if (log.isLoggable(Level.INFO))
			log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	} // rejectIt

	/**************************************************************************
	 * Prepare Document
	 * 
	 * @return new status (In Progress or Invalid)
	 */
	public String prepareIt() 
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		String sql = "SELECT 1 FROM UNS_PackingList_Order WHERE UNS_PackingList_ID = ?";
		int exists = DB.getSQLValue(get_TrxName(), sql, get_ID());
		if (exists <= 0) {
			m_processMsg = "@NoLines@";
			return DOCSTATUS_Invalid;
		}
//		sql = "SELECT COUNT(*) FROM UNS_PackingList_Order WHERE "
//				+ " UNS_PackingList_ID = ? AND NOT EXISTS "
//				+ " (SELECT * FROM UNS_PackingList_Line WHERE UNS_PackingList_Order_ID  "
//				+ " = UNS_PackingList_Order.UNS_PackingList_Order_ID) ";
//		
//		int count = DB.getSQLValue(get_TrxName(), sql, get_ID());
		//Sesepuh FL nambahin ya...Apri
		sql ="SELECT od.Documentno FROM UNS_PackingList_Order ol "
				+ "INNER JOIN C_Order od on od.C_Order_ID=ol.C_Order_ID "
				+ "WHERE  ol.UNS_PackingList_ID = ? "
				+ "AND NOT EXISTS  (SELECT * FROM UNS_PackingList_Line WHERE UNS_PackingList_Order_ID  = ol.UNS_PackingList_Order_ID) ";
		String docno = DB.getSQLValueString(get_TrxName(), sql, get_ID()); 
		if (!Util.isEmpty(docno)) {
			m_processMsg = "@NoLines@"+" IN Order "+docno;
			return DOCSTATUS_Invalid;
		}		
		
		//CASH-ORDER
		sql = "SELECT COUNT (*) FROM UNS_PackingList_Order WHERE UNS_PackingList_ID = ? "
				+ " AND EXISTS (SELECT * FROM C_Order od WHERE od.C_Order_ID = "
				+ " UNS_PackingList_Order.C_Order_ID AND EXISTS ( SELECT * "
				+ " FROM C_DocType WHERE C_DocType_ID = od.C_DocTypeTarget_ID "
				+ " AND DocSubTypeSO = ?) AND NOT EXISTS"
				+ " (SELECT * FROM C_Invoice iv WHERE iv.isPaid='Y' AND iv.C_Order_ID ="
				+ " od.C_Order_ID AND iv.DocStatus IN ('CO', 'CL')))";
		
		int count = DB.getSQLValue(get_TrxName(), sql, get_ID(), MDocType.DOCSUBTYPESO_CashOrder);
		if (count > 0) {
			m_processMsg = "The order list have unpaid cash order.";
			return DOCSTATUS_Invalid;
		}
		
		//PREPAY-Order
		sql = "SELECT COUNT (*) FROM UNS_PackingList_Order WHERE UNS_PackingList_ID = ? "
				+ " AND EXISTS (SELECT * FROM C_Order od WHERE od.C_Order_ID = "
				+ " UNS_PackingList_Order.C_Order_ID AND EXISTS ( SELECT * "
				+ " FROM C_DocType WHERE C_DocType_ID = od.C_DocTypeTarget_ID "
				+ " AND DocSubTypeSO = ?) AND NOT EXISTS(SELECT * FROM C_PaymentAllocate cpa"
				+ " WHERE cpa.C_Order_ID = od.C_Order_ID AND EXISTS"
				+ " (SELECT * FROM C_Payment cp WHERE cp.C_Payment_ID = cpa.C_Payment_ID AND DocStatus IN ('CO', 'CL'))))";
		count = DB.getSQLValue(get_TrxName(), sql, get_ID(), MDocType.DOCSUBTYPESO_PrepayOrder);
		if (count > 0) {
			m_processMsg = "The order list have unpayment prepay order.";
			return DOCSTATUS_Invalid;
		}

		sql = " SELECT ml.Value, mp.Name, COALESCE(SUM(pll.QtyEntered - pll.QtyRequest), 0), "
				+ " (SELECT COALESCE (SUM(soh.QtyOnHand), 0) FROM M_StorageOnHand soh WHERE"
				+ " soh.M_Product_ID = mp.M_Product_ID AND soh.M_Locator_ID = ml.M_Locator_ID)"
				+ " FROM UNS_PackingList_Line pll "
				+ "	INNER JOIN UNS_PackingList_Order plo ON pll.UNS_PackingList_Order_ID=plo.UNS_PackingList_Order_ID "
				+ " INNER JOIN UNS_PackingList pl ON pl.UNS_PackingList_ID=plo.UNS_PackingList_ID "
				+ " INNER JOIN M_Product mp ON mp.M_Product_ID=pll.M_Product_ID "
				+ " INNER JOIN M_Locator ml ON ml.M_Locator_ID = pll.M_Locator_ID "
				+ " WHERE mp.ProductType='I' AND pl.UNS_PackingList_ID= ? "
				+ " GROUP BY ml.M_Locator_ID, mp.M_Product_ID";
			
		StringBuilder buildErrorMsg = new StringBuilder();

		PreparedStatement stmt = DB.prepareStatement(sql, get_TrxName());
		ResultSet rs = null;
		try 
		{
			stmt.setInt(1, get_ID());
			rs = stmt.executeQuery();
			while (rs.next())
			{
				String locator = rs.getString(1);
				String product = rs.getString(2);
				BigDecimal totalQtyEntered = rs.getBigDecimal(3);
				BigDecimal qtyAvailable = rs.getBigDecimal(4);
				BigDecimal qtyDiff = totalQtyEntered.subtract(qtyAvailable);
				BigDecimal sepuluhribu = new BigDecimal(10000);
				BigDecimal nolkomanolnolnolsatu = Env.ONE.divide(sepuluhribu);
				if(qtyDiff.compareTo(Env.ZERO) == 1 && qtyDiff.compareTo(nolkomanolnolnolsatu) == 1)
				{
					if(!Util.isEmpty(buildErrorMsg.toString(), true));
					{
						buildErrorMsg.append(" && ");
					}
					buildErrorMsg.append("@NotEnoughStocked@: ").append(product)
					.append(" At Locator of ").append(locator);
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			m_processMsg = e.getMessage();
			return DOCSTATUS_Invalid;
		}
		finally {
			DB.close(rs, stmt);
		}
		
		String errorMsg = buildErrorMsg.toString();
		if(!Util.isEmpty(errorMsg, true))
		{
			m_processMsg = errorMsg;
			return DocAction.STATUS_Invalid;
		}

		try
		{
			createShipmentInvoice(this);
		} 
		catch (Exception e)
		{
			m_processMsg = "Error while doing process, ".concat(e.toString());
			return DOCSTATUS_Invalid;
		}
		
		if(getReference_ID() > 0)
		{
			sql = "UPDATE UNS_ShippingFreight SET UNS_PackingList_ID = ? WHERE UNS_PackingList_ID = ?";
			DB.executeUpdate(sql, new Object[]{get_ID(), getReference_ID()}, false, get_TrxName());
			sql = "UPDATE UNS_ExpeditionDetail SET UNS_PackingList_ID= ?  WHERE UNS_packingList_ID = ?";
			DB.executeUpdate(sql, new Object[]{get_ID(), getReference_ID()}, false, get_TrxName());
		}
		m_justPrepared = true;
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		setProcessed(true);
		saveEx();

		return DocAction.STATUS_InProgress;
	} // prepareIt

	@Override
	public String completeIt() 
	{
		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (DocAction.STATUS_InProgress.equals(status)) 
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_processMsg = MUNSPLConfirm.create(this);
		if(!Util.isEmpty(m_processMsg, true))
		{
			log.log(Level.INFO, m_processMsg);
			return DocAction.STATUS_InProgress;
		}
		
		try
		{	
			if (!isPickup())
			{
				MUNSPLReturn.createPLReturn(this);
			}
			
		} catch (Exception e)
		{
			throw new AdempiereException("Error when create PL Return, ".concat(e.toString()));
		}
		
		if(!completeDestination())
		{
			return DocAction.STATUS_Invalid;
		}
		
		if (isPickup())
		{
			m_processMsg = shipOrder(false);
			if(!Util.isEmpty(m_processMsg, true))
				return DocAction.STATUS_Invalid;
		}
		else
		{
			m_processMsg = moveItems();
			if(!Util.isEmpty(m_processMsg, true))
				return DocAction.STATUS_Invalid;
		}
		// Implicit Approval
		if (!isApproved())
			approveIt();
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		setProcessed(true);
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	} // completeIt

	@Override
	public boolean voidIt() 
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
//		if (!DOCSTATUS_Drafted.equals(getDocStatus())) {
		if(isProcessed())
		{
			m_processMsg = shipOrder(true);
			if (!Util.isEmpty(m_processMsg, true)) 
			{
				return false;
			}
		}

		MUNSPLReturn plReturn = 
				Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPLReturn.Table_Name, 
						MUNSPLReturn.COLUMNNAME_UNS_PackingList_ID + " = " + get_ID(), get_TrxName())
						.first();
		
		if(null != plReturn && !plReturn.getDocStatus().equals("VO"))
		{
			boolean ok = plReturn.processIt(DocAction.ACTION_Void);
			if (!ok)
			{
				m_processMsg = plReturn.getProcessMsg();
				return false;
			}
			plReturn.saveEx();
		}

		
		addDescription(Msg.getMsg(getCtx(), "Voided"));
		
		cancelIt();
		if(m_processMsg != null)
			return false;
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);
		return true;
	}
	

	/**
	 * Cancel this Packing List document.
	 */
	private void cancelIt() 
	{
		MUNSPLConfirm[] plConfirmList = MUNSPLConfirm.gets(this);
		
		for (MUNSPLConfirm plConfirm : plConfirmList)
		{			
			if (!plConfirm.processIt(DOCACTION_Void)) {
				m_processMsg = "Failed when void PL Confirmation of " 
						+ plConfirm.getDocumentNo();
				return;
			}
			
			plConfirm.saveEx();			
		}
		MUNSPackingListOrder[] ploList = getLines(null, null);
		
		for (MUNSPackingListOrder plo : ploList)
		{			
			setVolume(Env.ZERO);
			setTonase(Env.ZERO);
			saveEx();
			
			MUNSPackingListLine[] plLineList = plo.getLines();
			
			for (MUNSPackingListLine plLine : plLineList)
			{
				addDescription("Voided : " + plLine.getTargetQty());
				plLine.setIsReversal(true);
				plLine.setQty(Env.ZERO);
				plLine.setQtyShipping(Env.ZERO);
				plLine.saveEx();
			}
		}
	}
	
	/**
	 * Add to Description
	 * 
	 * @param description text
	 */
	public void addDescription(String description) {
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription

	@Override
	public boolean closeIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before Close

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		return true;
	}

	/**
	 * Reverse Correction - same void
	 * 
	 * @return true if success
	 */
	public boolean reverseCorrectIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return voidIt();
	} // reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 * 
	 * @return false
	 */
	public boolean reverseAccrualIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return voidIt();
	} // reverseAccrualIt

	@Override
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		MUNSPLConfirm[] confirms = MUNSPLConfirm.gets(this);
		for (int i=0; i<confirms.length; i++)
		{
			if (!confirms[i].getDocStatus().equals("DR"))
			{
				m_processMsg = "Failed to reactivate document. Confirmation already processed."
						+ " confirmation :: " + confirms[i].getDocumentNo();
				return false;
			}
			
			confirms[i].deleteEx(true);
			--i;
		}
		
		
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	}

	/*************************************************************************
	 * Get Summary
	 * 
	 * @return Summary of Document
	 */
	public String getSummary() {
		StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		// : Grand Total = 123.00 (#1)
		sb.append(": ").append(Msg.translate(getCtx(), "Tonase")).append("=").append(getTonase().setScale(5, RoundingMode.HALF_UP))
				.append(Msg.translate(getCtx(), ", Volume")).append("=").append(getVolume().setScale(5, RoundingMode.HALF_UP));
		if (m_orders != null)
			sb.append(" (#").append(m_orders.length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	} // getSummary

	/**************************************************************************
	 * String Representation
	 * 
	 * @return info
	 */
	public String toString() {
		StringBuffer sb =
				new StringBuffer("MPackingSlip[").append(get_ID()).append("-").append(getDocumentNo())
						.append(", Tonase=").append(getTonase()).append(", Volume=").append(getVolume()).append("]");
		return sb.toString();
	} // toString

	/**
	 * Get Document Info
	 * 
	 * @return document info (untranslated)
	 */
	public String getDocumentInfo() {
		return "Packing List :" + getDocumentNo();
	} // getDocumentInfo

	@Override
	public File createPDF() {
		try
		{
			File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		} catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;

	}

	/**
	 * Create PDF file
	 * 
	 * @param file output file
	 * @return file if success
	 */
	public File createPDF(File file) {
		ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.ORDER, get_ID(), get_TrxName());
		if (re == null)
			return null;
		MPrintFormat format = re.getPrintFormat();
		// We have a Jasper Print Format
		// ==============================
		if (format.getJasperProcess_ID() > 0)
		{
			ProcessInfo pi = new ProcessInfo("", format.getJasperProcess_ID());
			pi.setRecord_ID(get_ID());
			pi.setIsBatch(true);

			ServerProcessCtl.process(pi, null);

			return pi.getPDFReport();
		}
		// Standard Print Format (Non-Jasper)
		// ==================================
		return re.getPDF(file);
	} // createPDF

	@Override
	public String getProcessMsg() {

		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {

		return getUpdatedBy();
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return Env.ZERO;
	}

	/**************************************************************************
	 * Get Orders
	 * 
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return orders
	 */
	public MUNSPackingListOrder[] getLines(String whereClause, String orderClause) 
	{
		StringBuilder whereClauseFinal = new StringBuilder(MUNSPackingListOrder.COLUMNNAME_UNS_PackingList_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(" AND ").append(whereClause);

		if (orderClause == null || orderClause.length() == 0)
			orderClause = MUNSPackingListOrder.COLUMNNAME_UNS_PackingList_Order_ID;
		
		List<MUNSPackingListOrder> list =
				Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPackingListOrder.Table_Name,
						whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID()).setOrderBy(orderClause)
						.list();

		return list.toArray(new MUNSPackingListOrder[list.size()]);
	} // getLines

	/**
	 * Get Lines of Order
	 * 
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	public MUNSPackingListOrder[] getOrders(boolean requery, String orderBy) {
		if (m_orders != null && !requery)
		{
			set_TrxName(m_orders, get_TrxName());
			return m_orders;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;

		m_orders = getLines(null, orderClause);
		return m_orders;
	} // getLines

	/**
	 * Get Lines of Order. (used by web store)
	 * 
	 * @return lines
	 */
	public MUNSPackingListOrder[] getOrders() {
		return getOrders(false, null);
	} // getLines

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) {

		return super.beforeSave(newRecord);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeDelete()
	 */
	@Override
	protected boolean beforeDelete() {
		for (MUNSPackingListOrder order : getOrders())
		{
			if (!order.delete(true))
				return false;
		}

		return super.beforeDelete();
	}

	@Override
	public int getC_Currency_ID() {
		return 0;
	}

	public void setUpdateParent() {
		BigDecimal tonase = Env.ZERO;
		BigDecimal volume = Env.ZERO;
		for (MUNSPackingListOrder order : getOrders())
		{
			tonase = tonase.add(order.getTonase());
			volume = volume.add(order.getVolume());
		}

		StringBuilder sb =
				new StringBuilder("UPDATE ").append(MUNSPackingList.Table_Name).append(" SET ")
						.append(MUNSPackingList.COLUMNNAME_Tonase).append("=").append(tonase).append(", ")
						.append(MUNSPackingList.COLUMNNAME_Volume).append("=").append(volume).append(" WHERE ")
						.append(MUNSPackingList.COLUMNNAME_UNS_PackingList_ID).append("=").append(get_ID());

		if (DB.executeUpdate(sb.toString(), get_TrxName()) == -1)
			throw new AdempiereException("Error when update parent.");

	}

	@Override
	public int customizeValidActions(String docStatus, Object processing, String orderType, String isSOTrx,
			int AD_Table_ID, String[] docAction, String[] options, int index) {
		if (docStatus.equals(DocAction.STATUS_Drafted))
		{
			options[index++] = DocAction.ACTION_Prepare;
		}
		
		return index;
	}

	public String createOrderList(int SalesRep_ID, Timestamp dateFrom, int UNS_Rayon_ID) {
		MUNSPackingListOrder[] plos = getLines(" SalesRep_ID=" + SalesRep_ID + " ", null);

		List<com.unicore.base.model.MOrder> lorders =
				com.unicore.base.model.MOrder.getNotShipBySalesAndDate(getCtx(), SalesRep_ID
						, UNS_Rayon_ID, dateFrom, getDateDoc(),isSOTrx() ? "Y" : "N", get_TrxName());

		int orderCount = 0;
		for (com.unicore.base.model.MOrder order : lorders)
		{
			boolean skip = false;
			for (MUNSPackingListOrder plo : plos)
			{
				if (plo.getC_Order_ID() != order.get_ID())
					continue;

				skip = true;
				break;
			}

			if (skip)
				continue;

			orderCount++;
			MUNSPackingListOrder plo = new MUNSPackingListOrder(order);

			plo.setUNS_PackingList_ID(get_ID());

			for (com.unicore.base.model.MOrderLine oline : order.getLines())
			{
				if(oline.getQtyOrdered().compareTo(oline.getQtyPacked()) <= 0)
					continue;
				
				if (plo.get_ID() == 0 && !plo.save())
				{
					throw new AdempiereException("Error while try create order list");
				}
				
				MUNSPackingListLine pll = new MUNSPackingListLine(oline);

				MLocator loc = MLocator.getDefault(MWarehouse.get(getCtx(), oline.getM_Warehouse_ID()));
				pll.setM_Locator_ID(loc.get_ID());
				pll.setUNS_PackingList_Order_ID(plo.get_ID());
				try {
					pll.saveEx();
				} catch (Exception e) {
					throw new AdempiereException("Error while try create Order Line" + e.getMessage());
				}
			}
		}

		return "Created " + orderCount + " lines of order.";
	}
	
	
	protected String moveItems()
	{
		StringBuilder sb = new StringBuilder();
		MUNSPLConfirm[] confirms = MUNSPLConfirm.gets(this);
		for(MUNSPLConfirm confirm : confirms)
		{
			confirm.setGridTab(getGridTab());
			confirm.setProcessInfo(getProcessInfo());
			String error = confirm.move();
			if(Util.isEmpty(error, true))
			{
				continue;
			}
			sb.append(error);
		}
		
		String msg = sb.toString();
		if(!Util.isEmpty(msg, true))
		{
			return msg;
		}
		
		return null;
	}
	
	protected String shipOrder(boolean isVoid)
	{
		MUNSPackingListOrder[] orders = getOrders(true, null);
		for(int i=0; i<orders.length; i++)
		{
			MInOut inout = new MInOut(getCtx(), orders[i].getM_InOut_ID(), 
					get_TrxName());
			String action = isVoid ? DocAction.ACTION_Void 
					: DocAction.ACTION_Complete;
			boolean ok = inout.processIt(action);
			if (!ok)
			{
				return "Can't " + action + " Shipment Document : " 
						+ inout.getProcessMsg();
			}
			if (!inout.save()) {
				return CLogger.retrieveErrorString("Could not update shipment");
			}
			MInvoice inv = new MInvoice(getCtx(), orders[i].getC_Invoice_ID(), 
					get_TrxName());
			if (ok)
			{
				boolean cashOrPrepay = false;
				if (inv.getC_Order_ID() > 0)
				{
					String sql = " SELECT DocSubTypeSO FROM C_DocType WHERE "
							+ " C_DocType_ID = (SELECT C_DocTypeTarget_ID FROM "
							+ " C_Order WHERE C_Order_ID = ?) ";
					String dsType = DB.getSQLValueString(get_TrxName(), sql, 
							inv.getC_Order_ID());
					cashOrPrepay = MDocType.DOCSUBTYPESO_CashOrder.equals(dsType) 
							|| MDocType.DOCSUBTYPESO_PrepayOrder.equals(dsType);
				}
				
				if (!cashOrPrepay)
				{
					ok = inv.processIt(action);
					if (!ok)
					{
						return "Can't complete Invoice Document : " 
								+ inv.getProcessMsg();
					}
					
					if (!inv.save()) {
						return CLogger.retrieveErrorString("Could not update Invoice");
					}
				}
			}
		}
		
		return null;
	}
	
	private String oldDocumentNo(int Reference_ID, boolean isInvoice)
	{
		String docNo = null;
		String tableName = isInvoice ? "C_Invoice" : "M_InOut";

		String sql = "SELECT DocumentNo FROM " + tableName + " WHERE " + tableName + "_ID ="
				+ " (SELECT " + tableName + "_ID FROM UNS_PackingList_Order WHERE UNS_PackingList_Order_ID=?)";
		docNo = DB.getSQLValueString(get_TrxName(), sql, Reference_ID);
		
		String upAddVO = "UPDATE " + tableName + " SET DocumentNo = DocumentNo || '_VO' WHERE " + tableName + "_ID = "
				+ " (SELECT " + tableName + "_ID FROM UNS_PackingList_Order WHERE UNS_PackingList_Order_ID=?)";
		DB.executeUpdate(upAddVO, Reference_ID, get_TrxName());
		
		return docNo;
	}
	
	private boolean completeDestination()
	{
		String sql = "SELECT ARRAY_TO_STRING(ARRAY_AGG(DISTINCT(M_MovementConfirm_ID)), ';')"
				+ " FROM M_MovementConfirm mc"
				+ " INNER JOIN M_MovementLine ml ON ml.M_Movement_ID = mc.M_Movement_ID"
				+ " INNER JOIN M_RequisitionLine rl ON rl.M_RequisitionLine_ID = ml.M_RequisitionLine_ID"
				+ " INNER JOIN M_Requisition r ON r.M_Requisition_ID = rl.M_Requisition_ID"
				+ " WHERE r.UNS_PackingList_ID = ?";
		String id = DB.getSQLValueString(get_TrxName(), sql, get_ID());
		if(Util.isEmpty(id, true))
			return true;
		
		String[] ids = id.split(";");
		
		for(int i = 0; i < ids.length; i++)
		{
			int idInt = Integer.parseInt(ids[i]);
			PO po = Query.get(getCtx(), "UniCoreMaterialManagementModelFactory", "M_MovementConfirm",
					"M_MovementConfirm_ID=?", get_TrxName()).setParameters(idInt).first();
			
			if(!((MMovementConfirm) po).processIt(DocAction.ACTION_Complete) || !po.save())
			{
				m_processMsg = "Error : Destination Warehouse Confirm.";
				return false;
			}
		}
		return true;
	}
}
