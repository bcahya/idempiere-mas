/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MBPartner;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.model.MUNSBPCanvasser;
import com.uns.base.model.Query;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author root
 *
 */
public class MUNSCvsPhysical extends X_UNS_Cvs_Physical implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID		= 6642926315365130522L;
	private String m_processMsg						= null;
	private boolean m_justPrepared					= false;
	private MUNSCvsPhysicalProduct[] m_lines		= null;
//	private int m_precission						= 0;
	private int C_Currency_ID						= 0;
	private int m_salesRep_ID						= 0;
	private int M_PriceList_ID						= 0;
	
	private void initialRequirements()
	{
		MCurrency cur = MCurrency.get(getCtx(), "IDR");
		C_Currency_ID = cur.get_ID();
//		m_precission = cur.getStdPrecision();
		m_salesRep_ID = DB.getSQLValue(
				get_TrxName(), "SELECT AD_User_ID FROM AD_User WHERE C_BPartner_ID = ?"
				, getC_BPartner_ID());
		
		M_PriceList_ID	= DB.getSQLValue(
				get_TrxName(), (new StringBuilder("SELECT o.M_PriceList_ID FROM C_Order o WHERE ")
				.append("o.C_BPartner_ID = ? AND o.DatePromised <= ? AND o.DatePromised = ")
				.append("(SELECT MAX(datePromised) FROM C_Order WHERE C_BPartner_ID = ")
				.append("o.C_BPartner_ID)")).toString(), getC_BPartner_ID(), getMovementDate());
	}

	/**
	 * @param ctx
	 * @param UNS_Cvs_Physical_ID
	 * @param trxName
	 */
	public MUNSCvsPhysical(Properties ctx, int UNS_Cvs_Physical_ID,
			String trxName) {
		super(ctx, UNS_Cvs_Physical_ID, trxName);
		
		if(getUNS_Cvs_Physical_ID() > 0)
		{
			initialRequirements();
		}
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCvsPhysical(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
		if(getUNS_Cvs_Physical_ID() > 0)
		{
			initialRequirements();
		}
	}

	@Override
	public boolean processIt(String action) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info(toString());
		return true;
	}

	@Override
	public boolean invalidateIt() {
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		
		MUNSCvsPhysicalProduct[] lines = getLines();
		if(null == lines || lines.length == 0)
		{
			m_processMsg = "@No Lines@";
			return DocAction.STATUS_Invalid;
		}
		
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		log.info(toString());
		if(!isApproved())
			setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());
		
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		MUNSBPCanvasser cvsInfo = MUNSBPCanvasser.getOf(getC_BPartner_ID(), get_TrxName());
		cvsInfo.setLastPhysical(getMovementDate());
		cvsInfo.saveEx();
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);	
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		if (DOCSTATUS_Closed.equals(getDocStatus())
			|| DOCSTATUS_Reversed.equals(getDocStatus())
			|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}
		
		setDocStatus(DOCSTATUS_Voided); // need to set & save docstatus to be able to check it in MInOutConfirm.voidIt()
		saveEx();

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
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
		return false;
	}

	@Override
	public String getSummary() {
		StringBuilder summary = new StringBuilder(getDocumentNo())
		.append(" :: " ).append(getC_BPartner().getName());
		return summary.toString();
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
		return C_Currency_ID;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return Env.ZERO;
	}

	/**
	 * Get Lines.
	 * @return
	 */
	public MUNSCvsPhysicalProduct[] getLines()
	{
		return getLines(false);
	}
	/**
	 * Get Lines
	 * @param requery
	 * @return
	 */
	public MUNSCvsPhysicalProduct[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSCvsPhysicalProduct> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID
				, MUNSCvsPhysicalProduct.Table_Name, Table_Name + "_ID =?"
				, 	get_TrxName())
				.setParameters(get_ID()).list();
		
		m_lines = new MUNSCvsPhysicalProduct[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	public String createInvoiceInventoryLose()
	{
		try
		{
			MUNSCvsPhysicalProduct[] lines = getLines();
			MInvoice invoice = null;			
			for(int i=0; i<lines.length; i++)
			{
				BigDecimal diff = lines[i].getQtyBook().subtract(lines[i].getQtyCount());
				
				if(diff.signum() <= 0)
					continue;
		
				if(null == invoice)
				{
					invoice = new MInvoice(getCtx(), 0, get_TrxName());
					invoice.setAD_Org_ID(getAD_Org_ID());
					invoice.setAD_OrgTrx_ID(getAD_Org_ID());
					invoice.setBPartner((MBPartner) getC_BPartner());
					invoice.setC_Currency_ID(getC_Currency_ID());
					invoice.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_ARInvoice));
					invoice.setIsSOTrx(true);
					invoice.setC_DocTypeTarget_ID();
					invoice.setDateAcct(getMovementDate());
					invoice.setDateInvoiced(getMovementDate());
					invoice.setDateOrdered(getMovementDate());
					invoice.setM_PriceList_ID(M_PriceList_ID);
					invoice.setPaymentRule(MInvoice.PAYMENTRULE_Cash);
					invoice.setSalesRep_ID(m_salesRep_ID);
					invoice.saveEx();
					setC_Invoice_ID(invoice.get_ID());
					saveEx();
				}
				
				MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
				invoiceLine.setM_Product_ID(lines[i].getM_Product_ID());
				invoiceLine.setA_Processed(false);
				invoiceLine.setAD_OrgTrx_ID(invoice.getAD_Org_ID());
				SimplePrice price = new SimplePrice(
						getC_BPartner_ID(), lines[i].getM_Product_ID(), get_TrxName());
//				price.setPrecission(m_precission);
				invoiceLine.setC_UOM_ID(lines[i].getM_Product().getC_UOM_ID());
				invoiceLine.setDescription("::Auto create from Canvas Report");
				invoiceLine.setisProductBonuses(false);
				invoiceLine.setPrice(price.getPriceActual());
				invoiceLine.setPriceLimit(price.getPriceLimit());
				invoiceLine.setPriceList(price.getPriceList());
				invoiceLine.setQty(lines[i].getM_Product_ID());
				invoiceLine.setQtyBonuses(Env.ZERO);
				invoiceLine.saveEx();
			}
		}
		catch (Exception e)
		{
			return e.getMessage();
		}			
		
		return null;
	}
	
	public String generateListProduct()
	{
		try
		{
			MUNSBPCanvasser canvasserInfo = MUNSBPCanvasser.getOf(getC_BPartner_ID(), get_TrxName());
			MStorageOnHand[] storages = MStorageOnHand.getOfLocator(
					getCtx(),canvasserInfo.getM_Locator_ID() , get_TrxName());
			
			for(MStorageOnHand storage : storages)
			{
				MUNSCvsPhysicalProduct product = new MUNSCvsPhysicalProduct(this);
				product.setM_Product_ID(storage.getM_Product_ID());
				product.setM_AttributeSetInstance_ID(storage.getM_AttributeSetInstance_ID());
				product.setC_UOM_ID(storage.getM_Product().getC_UOM_ID());
				product.setIsActive(true);
				product.setM_Locator_ID(storage.getM_Locator_ID());
				product.setProcessed(false);
				product.setQtyBook(storage.getQtyOnHand());
				product.setQtyCount(Env.ZERO);
				product.saveEx();
			}
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
		return null;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		
		return super.beforeSave(newRecord);
	}
	
	
}
