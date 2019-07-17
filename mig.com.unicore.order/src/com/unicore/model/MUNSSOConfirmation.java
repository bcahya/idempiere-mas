/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.uns.base.model.Query;
import com.uns.util.MessageBox;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;
import com.unicore.model.factory.UNSOrderModelFactory;
import com.unicore.model.process.InvoiceCalculateDiscount;
import com.unicore.model.process.OrderCalculateDiscount;

/**
 * @author menjangan
 *
 */
public class MUNSSOConfirmation extends X_UNS_SOConfirmation implements
		DocAction {
	public static final String IS_EVENT_FROM_CONFIRMATION = "IS_EVENT_FROM_CONFIRMATION";
	private MUNSSOConfirmationLine[] m_lines;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_SOConfirmation_ID
	 * @param trxName
	 */
	public MUNSSOConfirmation(Properties ctx, int UNS_SOConfirmation_ID,
			String trxName) {
		super(ctx, UNS_SOConfirmation_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSOConfirmation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSSOConfirmation(PO po)
	{
		super(po.getCtx(), 0, po.get_TrxName());
		set_Value(po.get_TableName().concat("_ID"), po.get_ID());
		setAD_Org_ID(po.getAD_Org_ID());
		setC_BPartner_ID(po.get_ValueAsInt("C_BPartner_ID"));
		setC_BPartner_Location_ID(po.get_ValueAsInt("C_BPartner_Location_ID"));
		setC_DocType_ID(po.get_ValueAsInt("C_DocType_ID"));
		setDateAcct(Env.getContextAsDate(Env.getCtx(), "#Date"));
		setDocAction("CO");
		setDocStatus("DR");
		setGrandTotal((BigDecimal) po.get_Value("GrandTotal"));
		setTotalLines((BigDecimal) po.get_Value("TotalLines"));
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		return false;
	}

	private String m_processMsg = "";
	private boolean m_justPrepared = false;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if(getConfirmationType() == null)
			throw new AdempiereUserError("Confirmation type is mandatory");
		
		if(getConfirmChanges() == null)
			throw new AdempiereUserError("Decision Confirm is mandatory");
		
		if(getValidTo() == null)
		{
			String message = Msg.getMsg(getCtx(), "UnlimitedDateConfirmMsg");
			String title = Msg.getMsg(getCtx(), "UnlimitedDateConfirmTitle");
			int retVal = MessageBox.showMsg(this, getCtx(), message, title, MessageBox.YESNO, MessageBox.ICONQUESTION);
			if(MessageBox.RETURN_NO == retVal)
			{
				return DocAction.STATUS_Drafted;
			}
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		super.setIsApproved(true);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		setIsApproved(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		processConfirmDocument();
		
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		setProcessed(false);
		setDocStatus("DR");
		setDocAction("CO");
		setIsApproved(false);
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return m_processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return getGrandTotal();
	}
	
	@Override
	public int getC_Currency_ID() {
		return 0;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSSOConfirmationLine[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
			return m_lines;
		
		List<MUNSSOConfirmationLine> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSSOConfirmationLine.Table_Name
				, MUNSSOConfirmationLine.COLUMNNAME_UNS_SOConfirmation_ID + " = ? "
				, get_TrxName()).setParameters(getUNS_SOConfirmation_ID()).list();
		
		m_lines = new MUNSSOConfirmationLine[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	/**
	 * 
	 * @param C_Order_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSSOConfirmation[] get(PO po, String trxName)
	{
		final String whereClause = po.get_TableName() + "_ID = ?";
		List<MUNSSOConfirmation> list = Query.get(
				Env.getCtx(), UNSOrderModelFactory.EXTENSION_ID
				, MUNSSOConfirmation.Table_Name, whereClause
				, trxName).setParameters(po.get_ID())
				.setOrderBy(COLUMNNAME_DocumentNo)
				.list();
		MUNSSOConfirmation[] soConfirms = new MUNSSOConfirmation[list.size()];
		list.toArray(soConfirms);
		
		return soConfirms;
	}
	
	/**
	 * Get or create if not found SO / PO confirmation from PO / SO.
	 * @param C_Order_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSSOConfirmation[] getCreateConfirmation(PO po)
	{
		MUNSSOConfirmation[] confirm = get(po, po.get_TrxName());
		
		if(null != confirm && confirm.length > 0)
			return confirm;
		
		MUNSSOConfirmation soConfirmation = new MUNSSOConfirmation(po);
		soConfirmation.saveEx();
		soConfirmation.createLines();
		
		confirm = new MUNSSOConfirmation[1];
		confirm[0] = soConfirmation;
		
		return confirm;
	}
	
	/***
	 * 
	 */
	public void createLines()
	{
		if(getConfirmationType().equals(CONFIRMATIONTYPE_InvoiceConfirmation))
			createLinesFromInvoice();
		else
			createLinesFromOrder();
	}
	
	private void createLinesFromOrder()
	{
		MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		for(MOrderLine oLine : order.getLines())
		{
			if(oLine.isProductBonuses())
				continue;
			
			MUNSSOConfirmationLine confirmLine = new MUNSSOConfirmationLine(this);
			
			confirmLine.setC_OrderLine_ID(oLine.getC_OrderLine_ID());
			confirmLine.setC_UOM_ID(oLine.getC_UOM_ID());
			confirmLine.setM_AttributeSetInstance_ID(oLine.getM_AttributeSetInstance_ID());
			confirmLine.setM_Product_ID(oLine.getM_Product_ID());
			confirmLine.setPriceActual(oLine.getPriceActual());
			confirmLine.setPriceEntered(oLine.getPriceEntered());
			confirmLine.setPriceList(oLine.getPriceList());
			confirmLine.setQtyEntered(oLine.getQtyEntered());
			confirmLine.saveEx();
		}
	}
	
	private void createLinesFromInvoice()
	{
		MInvoice invoice = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
		for(MInvoiceLine line : invoice.getLines(false))
		{
			if(line.isProductBonuses())
				continue;
			
			MUNSSOConfirmationLine confirmLine = new MUNSSOConfirmationLine(this);
			
			confirmLine.setC_InvoiceLine_ID(line.get_ID());
			confirmLine.setC_UOM_ID(line.getC_UOM_ID());
			confirmLine.setM_AttributeSetInstance_ID(line.getM_AttributeSetInstance_ID());
			confirmLine.setM_Product_ID(line.getM_Product_ID());
			confirmLine.setPriceActual(line.getPriceActual());
			confirmLine.setPriceEntered(line.getPriceEntered());
			confirmLine.setPriceList(line.getPriceList());
			confirmLine.setQtyEntered(line.getQtyEntered());
			
			confirmLine.saveEx();
		}
	}
	
	@Override
	public boolean beforeSave(boolean newRecord)
	{
		if(getC_Invoice_ID() == 0 && getC_Order_ID() == 0)
			throw new AdempiereException("No order or invoice");
		
		return super.beforeSave(newRecord);
	}
	
	private void processConfirmDocument()
	{
		if(getConfirmationType().equals(CONFIRMATIONTYPE_OrderConfirmation))
			onOrder();
		else if(getConfirmationType().equals(CONFIRMATIONTYPE_OrderToInvoice))
			onOrderToInvoice();
		else if(getConfirmationType().equals(CONFIRMATIONTYPE_InvoiceConfirmation))
			onInvoice();
	}

	private void onInvoice()
	{
		MInvoice document = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
		if(getConfirmChanges().equals(CONFIRMCHANGES_Approve))
		{
			processConfirmedDocument(document, DocAction.ACTION_Complete);
		}
		else if(getConfirmChanges().equals(CONFIRMCHANGES_Void))
		{
			processConfirmedDocument(document, DocAction.ACTION_Void);
		}
		else if(getConfirmChanges().equals(CONFIRMCHANGES_Customize))
		{
			MUNSSOConfirmationLine[] lines = getLines(false);
			boolean isPricingChanged = false;
			for(int i=0; i<lines.length; i++)
			{
				MUNSSOConfirmationLine line = lines[i];
				MInvoiceLine documentLine = document.getLineOf(line.getC_InvoiceLine_ID());
				if(null == documentLine)
					continue;
				if(documentLine.getPriceActual().compareTo(line.getPriceActual()) == 0)
				{
					continue;
				}
				
				if(!isPricingChanged)
				{
					isPricingChanged = true;
				}
				
				documentLine.setPriceActual(line.getPriceActual());
				documentLine.saveEx();
			}
			
			if(isPricingChanged)
			{
				String sql = "DELETE FROM UNS_DiscountTrx WHERE C_Invoice_ID = ?";
				int retVal = DB.executeUpdate(sql, document.get_ID(), false, get_TrxName());
				if(retVal == -1)
					throw new AdempiereException("Failed To delete iscount trx");
				sql = "DELETE FROM UNS_DiscountTrx WHERE C_InvoiceLine_ID IN "
						+ "(SELECT C_InvoiceLine_ID FROM C_Invoice WHERE C_Invoice_ID = ?";
				retVal = DB.executeUpdate(sql, document.get_ID(), false, get_TrxName());
				if(retVal == -1)
					throw new AdempiereException("Failed to delte discount trx");
				return;
			}
			
			document.setProcessInfo(getProcessInfo());
			UNSInvoiceDiscountModel discountModel = new UNSInvoiceDiscountModel(document);
			InvoiceCalculateDiscount calc = new InvoiceCalculateDiscount(discountModel);
			calc.setJustUpdate(true);
			calc.run();
			
			processConfirmedDocument(document, DocAction.ACTION_Complete);
		}
		else if(getConfirmChanges().equals(CONFIRMCHANGES_Recalculate))
		{
			document.setAddDiscount(Env.ZERO);
			document.setAddDiscountAmt(Env.ZERO);
			MUNSSOConfirmationLine[] lines = getLines(false);
			for(int i=0; i<lines.length; i++)
			{
				MUNSSOConfirmationLine line = lines[i];
				MInvoiceLine documentLine = document.getLineOf(line.getC_InvoiceLine_ID());
				if(null == documentLine)
					continue;
				documentLine.setDiscount(Env.ZERO);
				documentLine.setDiscountAmt(Env.ZERO);
				documentLine.setPrice();
				documentLine.setLineNetAmt();
				documentLine.saveEx();
			}
			
			UNSInvoiceDiscountModel discountModel = new UNSInvoiceDiscountModel(document);
			InvoiceCalculateDiscount calc = new InvoiceCalculateDiscount(discountModel);
			calc.setJustUpdate(false);
			calc.run();
			
			processConfirmedDocument(document, DocAction.ACTION_Complete);
		}
	}
	
	private void onOrder()
	{
		MOrder document = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		if(getConfirmChanges().equals(CONFIRMCHANGES_Approve))
		{
			processConfirmedDocument(document, DocAction.ACTION_Complete);
		}
		else if(getConfirmChanges().equals(CONFIRMCHANGES_Void))
		{
			processConfirmedDocument(document, DocAction.ACTION_Void);
		}
		else if(getConfirmChanges().equals(CONFIRMCHANGES_Customize))
		{
			MUNSSOConfirmationLine[] lines = getLines(false);
			boolean isPricingChanged = false;
			for(int i=0; i<lines.length; i++)
			{
				MUNSSOConfirmationLine line = lines[i];
				MOrderLine documentLine = document.getLineOf(line.getC_OrderLine_ID());
				if(null == documentLine)
					continue;
				if(documentLine.getPriceActual().compareTo(line.getPriceActual()) == 0)
				{
					continue;
				}
				
				if(!isPricingChanged)
				{
					isPricingChanged = true;
				}
				
//				documentLine.setPriceActual(line.getPriceActual());
				documentLine.setPrice(line.getPriceActual());
				documentLine.saveEx();
			}
			
			if(isPricingChanged)
			{
				String sql = "DELETE FROM UNS_DiscountTrx WHERE C_Order_ID = ?";
				int retVal = DB.executeUpdate(sql, document.get_ID(), false, get_TrxName());
				if(retVal == -1)
					throw new AdempiereException("Failed To delete iscount trx");
				sql = "DELETE FROM UNS_DiscountTrx WHERE C_OrderLine_ID IN "
						+ "(SELECT C_OrderLine_ID FROM C_OrderLine WHERE C_Order_ID = ?)";
				retVal = DB.executeUpdate(sql, document.get_ID(), false, get_TrxName());
				if(retVal == -1)
					throw new AdempiereException("Failed to delte discount trx");
				return;
			}
			
			UNSOrderDiscountModel discountModel = new UNSOrderDiscountModel(document);
			OrderCalculateDiscount calc = new OrderCalculateDiscount(discountModel);
			calc.setIsJustUpdate(true);
			calc.run();
			
			processConfirmedDocument(document, DocAction.ACTION_Complete);
		}
		else if(getConfirmChanges().equals(CONFIRMCHANGES_Recalculate))
		{
			MUNSSOConfirmationLine[] lines = getLines(false);
			for(int i=0; i<lines.length; i++)
			{
				MUNSSOConfirmationLine line = lines[i];
				MOrderLine documentLine = document.getLineOf(line.getC_OrderLine_ID());
				if(null == documentLine)
					continue;
				documentLine.setHeaderInfo(document);
				documentLine.setOrder(document);
				documentLine.setPrice();
				documentLine.setLineNetAmt();
				documentLine.saveEx();
			}
			
			UNSOrderDiscountModel discountModel = new UNSOrderDiscountModel(document);
			OrderCalculateDiscount calc = new OrderCalculateDiscount(discountModel);
			calc.setIsJustUpdate(false);
			calc.run();
			
			processConfirmedDocument(document, DocAction.ACTION_Complete);
		}
	}
	
	private void onOrderToInvoice()
	{
		MOrder document = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		if(getConfirmChanges().equals(CONFIRMCHANGES_Void))
			processConfirmedDocument(document, DocAction.ACTION_Void);
		
//		createInvoiceFromOrder(document);
	}
	
	/**
	 * 
	 * @param order
	 *
	private void createInvoiceFromOrder(MOrder order)
	{
		MInvoice invoice = new MInvoice(order, 0, order.getDatePromised());
		if(!CONFIRMCHANGES_Approve.equals(getConfirmChanges()))
		{
			invoice.setAddDiscount(Env.ZERO);
			invoice.setAddDiscountAmt(Env.ZERO);
		}
		invoice.saveEx();
		
		if(CONFIRMCHANGES_Approve.equals(getConfirmChanges()))
			copyOrderDiscountToInvoice(invoice);
		//can't customize on order to invoice
		MOrderLine[] oLines = order.getLines();
		Env.setContext(Env.getCtx(), IS_EVENT_FROM_CONFIRMATION, 1);
		for(int i=0; i<oLines.length; i++)
		{
			MInvoiceLine line = new MInvoiceLine(invoice);
			line.setOrderLine(oLines[i]);
			line.setQty(oLines[i].getQtyEntered());
			if(!CONFIRMCHANGES_Approve.equals(getConfirmChanges()))
			{
				line.setDiscount(Env.ZERO);
				line.setDiscountAmt(Env.ZERO);
				line.setPrice(Env.ZERO);
				line.setPrice();
				line.setLineNetAmt();
			}
			
			line.saveEx();
			if(CONFIRMCHANGES_Approve.equals(getConfirmChanges()))
				copyOrderLineDiscountToInvoiceLine(line);
		}
		Env.setContext(Env.getCtx(), IS_EVENT_FROM_CONFIRMATION, -1);
	}
	*/
	
	protected void copyOrderLineDiscountToInvoiceLine(MInvoiceLine line)
	{
		MOrderLine orderLine = new MOrderLine(getCtx(), line.getC_OrderLine_ID(), get_TrxName());
		MUNSDiscountTrx[] listdiscountTrx = orderLine.getDiscountsTrx(false);
		for(int i=0; i<listdiscountTrx.length; i++)
		{
			MUNSDiscountTrx discounttrx = new MUNSDiscountTrx(getCtx(), 0, get_TrxName());
			PO.copyValues(listdiscountTrx[i], discounttrx);
			discounttrx.setC_OrderLine_ID(-1);
			discounttrx.setC_InvoiceLine_ID(line.get_ID());
			discounttrx.saveEx();
		}
	}
	
	protected void copyOrderDiscountToInvoice(MInvoice invoice)
	{
		MOrder order = new MOrder(getCtx(), invoice.getC_Order_ID(), get_TrxName());
		MUNSDiscountTrx[] listDiscountTrx = order.getDiscountsTrx(false);
		for(int i=0; i<listDiscountTrx.length; i++)
		{
			MUNSDiscountTrx discounttrx = new MUNSDiscountTrx(getCtx(), 0, get_TrxName());
			PO.copyValues(listDiscountTrx[i], discounttrx);
			discounttrx.setC_Order_ID(-1);
			discounttrx.setC_Invoice_ID(invoice.get_ID());
			discounttrx.saveEx();
		}
	}
	
	
	private void processConfirmedDocument(PO po, String DocAction)
	{
		if(po instanceof MInvoice)
		{
			MInvoice doc = (MInvoice) po;
			if(doc.getDocStatus().equals(DOCSTATUS_Invalid))
				doc.setDocStatus(DOCSTATUS_InProgress);
			if(!doc.getDocStatus().equals(DOCSTATUS_Drafted)
					&& !doc.getDocStatus().equals(DOCSTATUS_InProgress))
				return;
			Env.setContext(Env.getCtx(), IS_EVENT_FROM_CONFIRMATION, 1);
			boolean ok = doc.processIt(DocAction);
			if(!ok)
				log.info("Unable to complete invoice document " .concat(doc.getDocumentNo()));
			doc.saveEx();
			Env.setContext(Env.getCtx(), IS_EVENT_FROM_CONFIRMATION, -1);
		}
		else if(po instanceof MOrder)
		{
			MOrder doc = (MOrder) po;
			if(doc.getDocStatus().equals(DOCSTATUS_Invalid))
				doc.setDocStatus(DOCSTATUS_InProgress);
			if(!doc.getDocStatus().equals(DOCSTATUS_Drafted)
					&& !doc.getDocStatus().equals(DOCSTATUS_InProgress))
				return;

			Env.setContext(Env.getCtx(), IS_EVENT_FROM_CONFIRMATION, 1);
			boolean ok = doc.processIt(DocAction);
			if(!ok)
				log.info("Unable to complete order document ".concat(doc.getDocumentNo()));
			doc.saveEx();
			Env.setContext(Env.getCtx(), IS_EVENT_FROM_CONFIRMATION, -1);
		}
	}
	
	/**
	 * 
	 * @param C_OrderLine_ID
	 * @return
	 */
	public MUNSSOConfirmationLine getLineOrder(int C_OrderLine_ID)
	{
		MUNSSOConfirmationLine line = null;
		for(int i=0; i< getLines(false).length; i++)
		{
			if(C_OrderLine_ID != getLines(false)[i].getC_OrderLine_ID())
				continue;
			line = getLines(false)[i];
		}
		return line;
	}
}
