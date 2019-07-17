/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MInOutLineMA;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.wf.MWorkflow;

import com.unicore.model.MUNSBPCanvasser;
import com.uns.base.model.Query;
import com.uns.util.MessageBox;

import com.unicore.base.model.MInOut;
import com.unicore.base.model.MInOutLine;
import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.model.factory.UNSOrderModelFactory;
import com.unicore.model.process.InvoiceCalculateDiscount;

/**
 * @author root
 * 
 */
public class MUNSCvsRpt extends X_UNS_Cvs_Rpt implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -791773179004172113L;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSCvsRptCustomer[] m_lines = null;
	private int M_Currency_ID = 0;
	private int m_precission = 0;
	private int M_PriceList_ID = 0;
	private int M_Warehouse_ID = 0;
	private boolean m_noLines = false;

	/**
	 * @param ctx
	 * @param UNS_Cvs_Rpt_ID
	 * @param trxName
	 */
	public MUNSCvsRpt(Properties ctx, int UNS_Cvs_Rpt_ID, String trxName) 
	{
		super(ctx, UNS_Cvs_Rpt_ID, trxName);
		initialRequirements();
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCvsRpt(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
		initialRequirements();
	}

	private void initialRequirements() {
		if (getUNS_Cvs_Rpt_ID() <= 0)
			return;

		MCurrency cur = MCurrency.get(getCtx(), "IDR");
		M_Currency_ID = cur.get_ID();
		m_precission = cur.getStdPrecision();
		M_PriceList_ID = DB
				.getSQLValue(
						get_TrxName(),
						(new StringBuilder(
								"SELECT o.M_PriceList_ID FROM C_Order o WHERE ")
								.append("o.C_BPartner_ID = ? AND ")
								.append(" (o.DocStatus = 'CO' OR o.DocStatus ")
								.append(" = 'CL') AND o.DatePromised <= ? ")
								.append(" ORDER BY o.DatePromised DESC"))
								.toString(),
						getC_BPartner_ID(), getDateDoc());
		M_Warehouse_ID = DB
				.getSQLValue(
						get_TrxName(),
						(new StringBuilder(
								"SELECT M_Warehouse_ID FROM M_Locator WHERE ")
								.append("M_Locator_ID = (SELECT M_Locator_ID ")
								.append("FROM UNS_BP_Canvasser WHERE ")
								.append("UNS_BP_Canvasser_ID = ")
								.append("(SELECT UNS_BP_Canvasser_ID FROM ")
								.append("UNS_BP_Canvasser WHERE ")
								.append("C_BPartner_ID = ?))"))
								.toString(), getC_BPartner_ID());
	}

	@Override
	public boolean processIt(String action) throws Exception 
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	@Override
	public boolean unlockIt() 
	{
		log.info(toString());
		return true;
	}

	@Override
	public boolean invalidateIt() 
	{
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() 
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}

		MUNSCvsRptCustomer[] lines = getLines(true);
		if (null == lines || lines.length == 0) 
		{
			String message = "This is document no lines, are you sure to complete ? ";
			int retVal = MessageBox.showMsg(this, getCtx(), message, 
					"No Lines Confirmation"
					, MessageBox.YESNO, MessageBox.ICONWARNING);
			
			if(retVal == MessageBox.RETURN_YES)
			{
				log.info("no lines");
				m_noLines = true;
			}
			else
			{
				m_processMsg = "@No Lines@";
				return DocAction.STATUS_Invalid;
			}
		}
		
		if(!m_noLines)
		{
			for (MUNSCvsRptCustomer customer : lines)
			{
				customer.setProcessed(true);
				customer.saveEx();
				MUNSCvsRptProduct[] products = customer.getLines(false);
				for (MUNSCvsRptProduct product : products)
				{
					product.setProcessed(true);
					product.saveEx();
				}
			}
		}

		MUNSBPCanvasser cvsInfo = MUNSBPCanvasser.getOf(getC_BPartner_ID(),
				get_TrxName());
		if (null == cvsInfo) 
		{
			throw new AdempiereException("Canvasser Info is not set");
		}

		if (!cvsInfo.isMustReturnRemainingItems())
		{
			if (cvsInfo.getLastPhysical() == null) 
			{
				cvsInfo.setLastPhysical(getDateDoc());
				cvsInfo.saveEx();
			}

			int daysPhysical = cvsInfo.getDaysPhysical();
			StringBuilder sb = new StringBuilder("SELECT TIMESTAMP '")
					.append(cvsInfo.getLastPhysical())
					.append("' + INTERVAL '1 DAY' * ").append(daysPhysical);

			String sql = sb.toString();

			Timestamp dateMustPhysical = DB.getSQLValueTS(get_TrxName(), sql);

			if (getDateDoc().compareTo(dateMustPhysical) >= 0) 
			{
				m_processMsg = "Can't Commplete processing document."
						+ " please create physical First";
				return DocAction.STATUS_Invalid;
			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}

		m_justPrepared = true;

		if (!DOCACTION_Complete.equals(getDocAction()))
		{
			setDocAction(DOCACTION_Complete);
		}
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() 
	{
		log.info(toString());
		if (!isApproved())
		{
			setIsApproved(true);
		}
		return true;
	}

	@Override
	public boolean rejectIt() 
	{
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() 
	{
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}
		if("CO".equals(getDocStatus()) || "CL".equals(getDocStatus()))
		{
			return DocAction.STATUS_Completed;
		}
		
		log.info(toString());

		if (!m_justPrepared) 
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		MUNSBPCanvasser cvsInfo = MUNSBPCanvasser.getOf(getC_BPartner_ID(),
				get_TrxName());
		cvsInfo.setLastReport(getDateDoc());
		cvsInfo.saveEx();
		
		boolean createShipmentInvoice = isCreateShipmentInvoice();
		if(createShipmentInvoice)
		{
			m_processMsg = createShipmentInvoice();
			if (!Util.isEmpty(m_processMsg, true)) 
			{
				return DocAction.STATUS_Invalid;
			}

			MInvoice invoice = new MInvoice(
					getCtx(), getC_Invoice_ID(), get_TrxName());
			if(!invoice.isComplete()) 
			{
				if(!m_consolidate)
				{
					String message = "Automaticaly complete Invoice Document ? ";
					int retVal = MessageBox.showMsg(this, getCtx(), message, 
							"Auto Complete Confirmation"
							, MessageBox.YESNO, MessageBox.ICONWARNING);
					
					if(retVal != MessageBox.RETURN_YES)
					{
						m_processMsg = "Please complete Invoice Document"
								+ " before do next process.";
						return DocAction.STATUS_InProgress;
					}
				}
				else if(m_consolidate && !m_AutoComplete)
				{
					return DocAction.STATUS_InProgress;
				}
			}
			
			m_processMsg = completingShipmentInvoicePayment();
			if (!Util.isEmpty(m_processMsg, true))
			{
				return DocAction.STATUS_Invalid;
			}
			
		}
		else if(!createShipmentInvoice)
		{
			if(getC_Invoice_ID() > 0)
			{
				MInvoice invoice = new MInvoice(
						getCtx(), getC_Invoice_ID(), get_TrxName());
				try
				{
					invoice.deleteEx(true);
				} 
				catch (Exception e)
				{
					log.log(Level.SEVERE, "Failed on delete invoice");
					if(!invoice.getDocStatus().equals(
							MInvoice.DOCSTATUS_Drafted))
					{
						try
						{
							ProcessInfo pi = MWorkflow
									.runDocumentActionWorkflow(
											invoice, DocAction.ACTION_Void);
							if(pi.isError())
							{
								m_processMsg = pi.getSummary();
								return DocAction.STATUS_Invalid;
							}
						}
						catch (Exception ee)
						{
							m_processMsg = ee.getMessage();
							return DocAction.STATUS_Invalid;
						}
					}
				}
			}
			if(getM_InOut_ID() > 0)
			{
				MInOut inOut = new MInOut(
						getCtx(), getM_InOut_ID(), get_TrxName());
				try
				{
					inOut.deleteEx(true);
				} 
				catch (Exception e)
				{
					log.log(Level.SEVERE, "Failed on delete invoice");
					if(!inOut.getDocStatus().equals(MInvoice.DOCSTATUS_Drafted))
					{
						try
						{
							ProcessInfo pi = MWorkflow.
									runDocumentActionWorkflow(
											inOut, DocAction.ACTION_Void);
							if(pi.isError())
							{
								m_processMsg = pi.getSummary();
								return DocAction.STATUS_Invalid;
							}
						}
						catch (Exception ee)
						{
							m_processMsg = ee.getMessage();
							return DocAction.STATUS_Invalid;
						}
					}
				}
			}
		}
		
		if(getC_Invoice_ID() > 0 && !isItemMatchWithInvoice())
		{
			m_processMsg = "Not match item with invoice";
			return DocAction.STATUS_Invalid;
		}
		if(!isConsolidate() && cvsInfo.getQtyOnHand().signum() != 0 
				&& cvsInfo.isMustReturnRemainingItems())
		{
			m_processMsg = "Please return remaining items into warehouse"
					+ " before completing this document.";
			return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		if (DOCSTATUS_Closed.equals(getDocStatus())
				|| DOCSTATUS_Reversed.equals(getDocStatus())
				|| DOCSTATUS_Voided.equals(getDocStatus())) {
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}
		
		if (getC_Invoice_ID() > 0)
		{
			MInvoice invoice = new MInvoice(getCtx(), getC_Invoice_ID(), 
					get_TrxName());
			if (!"VO".equals(invoice.getDocStatus())
					&& !"RE".equals(invoice.getDocStatus()))
			{
				boolean ok = invoice.processIt(DocAction.ACTION_Void);
				if (!ok)
				{
					m_processMsg = "Failed when void invoice " 
								+ invoice.getProcessMsg();
					return false;
				}
				invoice.saveEx();
			}
		}
		if (getM_InOut_ID() > 0)
		{
			MInOut inout = new MInOut(getCtx(), getM_InOut_ID(), get_TrxName());
			if (!"VO".equals(inout.getDocStatus())
					&& !"RE".equals(inout.getDocStatus()))
			{
				boolean ok = inout.processIt(DocAction.ACTION_Void);
				if (!ok)
				{
					m_processMsg = "Failed when void shipment";
					return false;
				}
				inout.saveEx();
			}
		}

		setDocStatus(DOCSTATUS_Voided); 
		saveEx();

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public boolean closeIt() {
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		return false;
	}

	@Override
	public boolean reActivateIt() {
		
		MInvoice invoice =  new MInvoice(
				getCtx(), getC_Invoice_ID(), get_TrxName());
		
		boolean ok = invoice.processIt(DocAction.ACTION_Reverse_Correct);
		if (!ok)
		{
			m_processMsg = invoice.getProcessMsg();
			return false;
		}
		invoice.saveEx();
		
		MInOut inout = new MInOut(getCtx(), getM_InOut_ID(), get_TrxName());
		ok = inout.processIt(DOCACTION_Reverse_Correct);
		if (!ok)
		{
			m_processMsg = inout.getProcessMsg();
			return false;
		}
		inout.saveEx();
		getLines(true);
		for (int i=0; i<m_lines.length; i++)
		{
			m_lines[i].setProcessed(false);
			m_lines[i].saveEx();
			MUNSCvsRptProduct[] products = m_lines[i].getLines(true);
			for (int x=0; x<products.length; x++)
			{
				products[x].setProcessed(false);
				products[x].saveEx();
			}
		}
		
		setM_InOut_ID(-1);
		setC_Invoice_ID(-1);

		setProcessed(false);
		setDocStatus(DOCSTATUS_Drafted);
		setDocAction(DOCACTION_Complete);
		return true;
	}

	@Override
	public String getSummary() {
		return (new StringBuilder("Canvas Report ").append(getName()))
				.toString();
	}

	@Override
	public String getDocumentInfo() {
		return getSummary();
	}

	@Override
	public File createPDF() {
		return null;
	}

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		return M_Currency_ID;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return Env.ZERO;
	}

	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSCvsRptCustomer[] getLines(boolean requery) {
		if (null != m_lines && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}

		List<MUNSCvsRptCustomer> list = Query
				.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID,
						MUNSCvsRptCustomer.Table_Name, Table_Name + "_ID = ?",
						get_TrxName()).setParameters(get_ID()).list();

		m_lines = new MUNSCvsRptCustomer[list.size()];
		list.toArray(m_lines);

		return m_lines;
	}

	/**
	 * 
	 * @return {@link String} Error Message
	 */
	private String createShipmentInvoice() 
	{
		try 
		{
			boolean newInvoice = getC_Invoice_ID() == 0;
			boolean newShipment = getM_InOut_ID() == 0;
			MInvoice invoice = new MInvoice(
					getCtx(), getC_Invoice_ID(), get_TrxName());
			if (invoice.get_ID() == 0)
			{
				invoice.setIsSOTrx(true);
				invoice.setAD_Org_ID(getAD_Org_ID());
				invoice.setAD_OrgTrx_ID(getAD_Org_ID());
				invoice.setBPartner((MBPartner) getC_BPartner());
				invoice.setC_Currency_ID(getC_Currency_ID());
				invoice.setC_DocTypeTarget_ID(MDocType.getDocType(MDocType.DOCBASETYPE_ARInvoice));
				invoice.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_ARInvoice));
				invoice.setDateAcct(getDateDoc());
				invoice.setDateInvoiced(getDateDoc());
				invoice.setDateOrdered(getDateDoc());
				invoice.setM_PriceList_ID(M_PriceList_ID);
				invoice.setPaymentRule(getPaymentRule());
				invoice.setSalesRep_ID(getSalesRep_ID());
				invoice.setDescription("::Auto generated from canvas report " + getDocumentNo());
				invoice.saveEx();
			}
			
			MInOut shipment = new MInOut(
					getCtx(), getM_InOut_ID(), get_TrxName());
			if (shipment.get_ID() == 0)
			{
				shipment = new MInOut(invoice,
						MDocType.getDocType(
								MDocType.DOCBASETYPE_MaterialDelivery),
						getDateDoc(), M_Warehouse_ID);
			}
			
			shipment.setC_Invoice_ID(invoice.get_ID());
			shipment.saveEx();
			
			boolean reCreate = false;
			if (invoice.getDocStatus().equals(DOCSTATUS_Reversed)
					|| invoice.getDocStatus().equals(DOCSTATUS_Voided))
			{
				setC_Invoice_ID(0);
				reCreate = true;
			}
			
			if (shipment.getDocStatus().equals(DOCSTATUS_Reversed)
					|| shipment.getDocStatus().equals(DOCSTATUS_Voided))
			{
				setM_InOut_ID(0);
				reCreate = true;
			}
			
			if (reCreate)
			{
				return createShipmentInvoice();
			}
			
			setC_Invoice_ID(invoice.get_ID());
			setM_InOut_ID(shipment.get_ID());
			saveEx();
			
			if (newInvoice)
			{
				createInvoiceLines(invoice);
				if(!m_consolidate)
				{
					String msg = "Do you want to calculate discount?";
					int answer = MessageBox.showMsg(this, getCtx(), msg, 
							"Calculate Discount ?", MessageBox.YESNO, 
							MessageBox.ICONQUESTION);
					if (answer == MessageBox.RETURN_YES)
					{
						invoice.setProcessInfo(this.getProcessInfo());
						UNSInvoiceDiscountModel dm = new UNSInvoiceDiscountModel(invoice);
						Thread worker = new Thread(new InvoiceCalculateDiscount(dm));
						worker.run();
					}
				}
				else
				{
					if(m_CalcDisc)
					{
						invoice.setProcessInfo(this.getProcessInfo());
						UNSInvoiceDiscountModel dm = new UNSInvoiceDiscountModel(invoice);
						Thread worker = new Thread(new InvoiceCalculateDiscount(dm));
						worker.run();
					}
				}
			}
			if (newShipment)
			{
				createShipmentLines(shipment);
			}
		} 
		catch (Exception e) 
		{
			return "Error" + e.getMessage();
		}
		
		return null;
	}

	private String completingShipmentInvoicePayment() {
		MInvoice invoice = new MInvoice(getCtx(), getC_Invoice_ID(),
				get_TrxName());
		MInOut shipment = new MInOut(getCtx(), getM_InOut_ID(), get_TrxName());

		try {
			if(!invoice.isComplete())
			{
				ProcessInfo invoiceInfo = MWorkflow.runDocumentActionWorkflow(
						invoice, DocAction.ACTION_Complete);
				if(invoiceInfo.isError())
				{
					return invoiceInfo.getSummary();
				}
			}
			
			if(!shipment.isComplete())
			{
				ProcessInfo inOutInfo = MWorkflow.runDocumentActionWorkflow(
						shipment, DocAction.ACTION_Complete);
				if(inOutInfo.isError())
				{
					return inOutInfo.getSummary();
				}
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}

	public int getPrecission() {
		return m_precission;
	}
	
	/**
	 * Check sold Qty for create shipment and invoice.
	 * @return
	 */
	public boolean isCreateShipmentInvoice()
	{
		if(m_noLines)
			return false;
		getLines(false);
		for (int i = 0; i < m_lines.length; i++) 
		{
			MUNSCvsRptCustomer customer = m_lines[i];
			MUNSCvsRptProduct[] products = customer.getLines(false);
			for (int j=0; j<products.length; j++)
			{
				if(products[j].getQtySold().signum() == 1)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param shipment
	 */
	private void createShipmentLines(MInOut shipment)
	{
		MUNSCvsRptCustomer[] customers = getLines(false);
		MUNSBPCanvasser cvsInfo = MUNSBPCanvasser.getOf(getC_BPartner_ID(), 
				get_TrxName());
		for (int u=0; u<customers.length; u++)
		{
			MUNSCvsRptProduct[] products = customers[u].getLines(false);
			for (int n=0; n<products.length; n++)
			{
				MInOutLine shipLine = shipment.getByProduct(
						products[n].getM_Product_ID());
				if (null == shipLine)
				{
					shipLine = new MInOutLine(shipment);
					shipLine.setQty(Env.ZERO);
					shipLine.setM_Product_ID(products[n].getM_Product_ID(), true);
					shipLine.setM_Locator_ID(cvsInfo.getM_Locator_ID());
					shipLine.saveEx();
					shipment.addLines(shipLine);
				}
				
				MInOutLineMA[] imas = MInOutLineMA.get(
						getCtx(), shipLine.get_ID(), get_TrxName());
			
				if (products[n].getM_AttributeSetInstance_ID() > 0
						&& shipLine.getM_AttributeSetInstance_ID() == 0
						&& imas.length == 0)
				{
					shipLine.setM_AttributeSetInstance_ID(
							products[n].getM_AttributeSetInstance_ID());
				}
				else if (shipLine.getM_AttributeSetInstance_ID() > 0
						&& products[n].getM_AttributeSetInstance_ID() 
						!= shipLine.getM_AttributeSetInstance_ID())
				{
					MInOutLineMA ma = new MInOutLineMA(
							shipLine, 
							shipLine.getM_AttributeSetInstance_ID(), 
							shipLine.getQtyEntered(), 
							shipLine.getM_AttributeSetInstance().
							getCreated(), false);
					ma.saveEx();
					shipLine.setM_AttributeSetInstance_ID(-1);
				}
				
				if (shipLine.getM_AttributeSetInstance_ID() == 0
						&& products[n].getM_AttributeSetInstance_ID() > 0)
				{
					MInOutLineMA ma = null;
					for (int o=0; o<imas.length; o++)
					{
						if (imas[o].getM_AttributeSetInstance_ID() == 
								products[n].getM_AttributeSetInstance_ID())
						{
							ma = imas[o];
							break;
						}
					}
					
					if (null == ma)
					{
						ma = new MInOutLineMA(
								shipLine, 
								products[n].getM_AttributeSetInstance_ID(), 
								Env.ZERO, 
								products[n].getM_AttributeSetInstance().
								getCreated(), false);
					}
					
					ma.setMovementQty(products[n].getQtySold());
					ma.saveEx();
				}
				
				shipLine.setQty(shipLine.getQtyEntered()
						.add(products[n].getQtySold()));
				shipLine.saveEx();
				
				MUNSCvsRptProductMA[] mas = products[n].getLines();
				for (int s=0; s<mas.length; s++)
				{
					MInOutLineMA ma = null;
					for (int e=0;e<imas.length; e++)
					{
						if (imas[e].isAutoGenerated()
								|| (imas[e].getM_AttributeSetInstance_ID()
								!= mas[s].getM_AttributeSetInstance_ID()
								&& imas[e].getDateMaterialPolicy()
								.compareTo(mas[s].getDateMaterialPolicy()) != 0))
						{
							continue;
						}
					}
					
					if (null == ma)
					{
						ma = new MInOutLineMA(
								shipLine, 
								mas[s].getM_AttributeSetInstance_ID(), 
								Env.ZERO, 
								mas[s].getDateMaterialPolicy(), false);
					}
					
					BigDecimal qty = ma.getMovementQty();
					qty = qty.add(mas[s].getMovementQty());
					ma.setMovementQty(qty);
					ma.saveEx();
				}
				
				products[n].setM_InOutLine_ID(shipLine.get_ID());
			}
		}
	}
	
	/**
	 * 
	 * @param invoice
	 */
	private void createInvoiceLines(MInvoice invoice)
	{
		MUNSCvsRptCustomer[] customers = getLines(false);
		
		for (int u=0; u<customers.length; u++)
		{
			MUNSCvsRptProduct[] products = customers[u].getLines(false);
			for (int n=0; n<products.length; n++)
			{
				MInvoiceLine invLine = invoice.getByProductAndPrice(
						products[n].getM_Product_ID(), 
						products[n].getPriceActual());
				
				if (null == invLine)
				{
					invLine = new MInvoiceLine(invoice);
					invLine.setM_Product_ID(products[n].getM_Product_ID());
					invLine.setProcessed(false);
					invLine.setAD_OrgTrx_ID(invoice.getAD_Org_ID());
					invLine.setC_UOM_ID(products[n].getC_UOM_ID());
					invLine.setDescription(
							"::Auto create from Canvas Report.");
					if(products[n].isProductBonuses())
						invLine.setPrice(Env.ZERO);
					else
						invLine.setPrice(products[n].getPriceList());
					invLine.setPriceLimit(products[n].getPriceLimit());
					invLine.setPriceList(products[n].getPriceList());
					invLine.setQtyBonuses(Env.ZERO);
					invLine.setQty(Env.ZERO);
					invLine.setisProductBonuses(products[n].isProductBonuses());
					invoice.addLines(invLine);
				}
			
				invLine.setQty(invLine.getQtyInvoiced().add(
						products[n].getQtySold()));
				invLine.setM_InOutLine_ID(products[n].getM_InOutLine_ID());
				invLine.saveEx();
				
				products[n].setC_InvoiceLine_ID(invLine.get_ID());
			}
		}
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) 
	{
		if (DOCSTATUS_Completed.equals(docStatus))
		{
			options[index++] = DocAction.ACTION_Void;
			options[index++] = DocAction.ACTION_ReActivate;
		}
		
		return index;
	}
	
	protected void updateHeader()
	{
		if(getUNS_Cvs_RptGroup_ID() > 0)
		{
			BigDecimal totalLines = DB.getSQLValueBD(
					get_TrxName()
					, "SELECT COALESCE(SUM(GrandTotal), 0) FROM UNS_Cvs_Rpt"
							+ " WHERE UNS_Cvs_RptGroup_ID = ?", 
							getUNS_Cvs_RptGroup_ID());
			
			String update = "UPDATE UNS_Cvs_RptGroup SET GrandTotal = ? WHERE UNS_Cvs_RptGroup_ID = ?";
			int ok = DB.executeUpdate(
					update, new Object[]{totalLines, 
							getUNS_Cvs_RptGroup_ID()}
					, false , get_TrxName());
			if(ok == -1)
			{
				throw new AdempiereException("Failed on update header");
			}
		}
	}
	
	private boolean m_consolidate = false;
	public boolean isConsolidate()
	{
		return m_consolidate;
	}
	
	public void setConsolidate(boolean consolidate)
	{
		m_consolidate = consolidate;
	}
	
	private boolean m_CalcDisc = false;
	public boolean isCalcDisc()
	{
		return m_CalcDisc;
	}
	
	public void setCalcDisc(boolean calcDisc)
	{
		m_CalcDisc = calcDisc;
	}
	
	private boolean m_AutoComplete = false;
	public boolean isAutoComplete()
	{
		return m_AutoComplete;
	}
	
	public void setAutoComplete(boolean autoComplete)
	{
		m_AutoComplete = autoComplete;
	}
	
	private boolean isItemMatchWithInvoice()
	{
		String sql = "SELECT M_Product_ID, SUM(QtyInvoiced) FROM C_InvoiceLine"
				+ " WHERE C_Invoice_ID = ? AND QtyInvoiced > 0 GROUP BY M_Product_ID ORDER BY M_Product_ID";
		List<List<Object>> invoices = DB.getSQLArrayObjectsEx(get_TrxName(), sql, getC_Invoice_ID());
		
		sql = "SELECT pr.M_Product_ID, SUM(pr.QtySold) FROM UNS_Cvs_RptProduct pr"
				+ " INNER JOIN UNS_Cvs_RptCustomer cs ON cs.UNS_Cvs_RptCustomer_ID = pr.UNS_Cvs_RptCustomer_ID"
				+ " WHERE cs.UNS_Cvs_Rpt_ID = ? AND pr.QtySold > 0 GROUP BY pr.M_Product_ID ORDER BY pr.M_Product_ID";
		List<List<Object>> canvas = DB.getSQLArrayObjectsEx(get_TrxName(), sql, get_ID());

		for(int i=0;i<invoices.size();i++)
		{
			boolean checked = false;
			int prInv = Integer.parseInt((invoices.get(i).get(0).toString()));
			for(int j=0;j<canvas.size();j++)
			{
				int prCnv = Integer.parseInt((invoices.get(j).get(0).toString()));
				if(prInv != prCnv)
					continue;
				else
				{
					BigDecimal qtyInv = (BigDecimal) invoices.get(i).get(1);
					BigDecimal qtyCanvas = (BigDecimal) canvas.get(j).get(1);
					if(qtyInv.compareTo(qtyCanvas) != 0)
						return false;
					else
					{
						checked = true;
						break;
					}
				}
			}
			if(!checked)
				return false;
		}
		
		return true;
	}
	
	public static MUNSCvsRpt getOfInvoice(Properties ctx, int C_Invoice_ID, String trxName)
	{
		MUNSCvsRpt rpt = Query.get(ctx, UNSOrderModelFactory.EXTENSION_ID,
				Table_Name, COLUMNNAME_C_Invoice_ID + "=?", trxName).setParameters(C_Invoice_ID)
					.firstOnly();
		
		return rpt;
	}
}