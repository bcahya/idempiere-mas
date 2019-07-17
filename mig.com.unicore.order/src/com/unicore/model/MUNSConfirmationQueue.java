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
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author ALBURHANY
 *
 */
public class MUNSConfirmationQueue extends X_UNS_ConfirmationQueue implements DocAction {

	private MUNSConfirmationQueueLine[] m_ConfirmationQueueLine = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6320453354400366498L;

	/**
	 * @param ctx
	 * @param UNS_ConfirmationQueue_ID
	 * @param trxName
	 */
	public MUNSConfirmationQueue(Properties ctx, int UNS_ConfirmationQueue_ID,
			String trxName) {
		super(ctx, UNS_ConfirmationQueue_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSConfirmationQueue(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSConfirmationQueueLine[] getLine() 
	{
		if (m_ConfirmationQueueLine != null)
			return m_ConfirmationQueueLine;
		
		List<MUNSConfirmationQueueLine> list = null;
		list = Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSConfirmationQueueLine.Table_Name, 
						 "UNS_ConfirmationQueue_Line_ID=" + get_ID(), get_TrxName())
					.list();
		MUNSConfirmationQueueLine[] retValue = new MUNSConfirmationQueueLine[list.size()];
		list.toArray(retValue);
		m_ConfirmationQueueLine = retValue;
		
		return retValue;
	}
	
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUNSConfirmationQueue[");
		sb.append(get_ID()).append("-").append(getDocumentNo())
			.append(",Status=").append(getDocStatus()).append(",Action=").append(getDocAction())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	public File createPDF ()
	{
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		return true; //	beforeSave
	}	
	
	protected boolean beforeDelete()
	{
		return true;
	}

	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (processAction, getDocAction());
	}	//	process
	
	/**	Process Message 			*/
	private String			m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean 		m_justPrepared = false;

	/**
	 * 	Unlock Document.
	 * 	@return true if success 
	 */
	public boolean unlockIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}	//	unlockIt
	
	/**
	 * 	Invalidate Document
	 * 	@return true if success 
	 */
	public boolean invalidateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("invalidateIt - " + toString());
		return true;
	}	//	invalidateIt
	
	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid) 
	 */
	public String prepareIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}	//	prepareIt
	
	/**
	 * 	Approve Document
	 * 	@return true if success 
	 */
	public boolean  approveIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}	//	approveIt
	
	/**
	 * 	Reject Approval
	 * 	@return true if success 
	 */
	public boolean rejectIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt
	
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		for (MUNSConfirmationQueueLine confQueueLine : getLine())
		{
			StringBuffer sqlOrgWH = new StringBuffer
					("SELECT UNS_OrgWarehouse_ID FROM UNS_OrgWarehouse"
							+ " WHERE DocStatus = 'CO' AND AD_Org_ID = " + getAD_Org_ID());
			int idOrg = DB.getSQLValue(get_TrxName(), sqlOrgWH.toString()) >= 1 ?
					DB.getSQLValue(get_TrxName(), sqlOrgWH.toString()) : 0;
			
			MUNSOrgWarehouse orgWh = new MUNSOrgWarehouse(getCtx(), idOrg, get_TrxName());
			BigDecimal qty = Env.ZERO;
			if(idOrg <= -1)
			{
				MOrderLine orderLine = new MOrderLine(getCtx(), confQueueLine.getC_OrderLine_ID(), get_TrxName());
				MOrder order = new MOrder(getCtx(), orderLine.getC_Order_ID(), get_TrxName());
				qty = MStorageOnHand.getQtyOnHand(confQueueLine.getM_Product_ID(), order.getM_Warehouse_ID(), 0, get_TrxName());
			}
			
			if(idOrg > 0)
			{
				for(MUNSOrgWarehouseLine whLine : orgWh.getLines(confQueueLine.getAD_Org_ID()))
				{
					qty =
					qty.add(MStorageOnHand.getQtyOnHand(confQueueLine.getM_Product_ID(), whLine.getM_Warehouse_ID(), 0, get_TrxName()));							
				}
			}
			
			StringBuffer sqlQtyQueue = new StringBuffer
					("SELECT SUM(QTY) FROM UNS_OrderQueue_Line WHERE isReserved = 'Y'"
							+ " AND C_OrderLine_ID IN (SELECT C_OrderLine_ID FROM C_OrderLine WHERE"
							+ " C_Order_ID IN (SELECT C_Order_ID FROM C_Payment WHERE DocStatus = 'CO'))"
							+ " AND Sequence < " + confQueueLine.getSequence()
							+ " AND UNS_OrderQueue_ID = (SELECT UNS_OrderQueue_ID FROM UNS_OrderQueue"
							+ " WHERE M_Product_ID = " + confQueueLine.getM_Product_ID()
							+ " AND AD_Org_ID = " + confQueueLine.getAD_Org_ID() + ")");
			BigDecimal qtyReserved = DB.getSQLValueBD(get_TrxName(), sqlQtyQueue.toString()) == null ?
					Env.ZERO : DB.getSQLValueBD(get_TrxName(), sqlQtyQueue.toString());
			
			if(qty.compareTo(qtyReserved.add(confQueueLine.getQty())) == -1)
			{
				throw new AdempiereException("Stock not avaliable");
			}
			
			if(qty.compareTo(qtyReserved.add(confQueueLine.getQty())) != -1)
			{
				StringBuffer sqlIDQLine = new StringBuffer
						("SELECT UNS_OrderQueue_Line_ID FROM UNS_OrderQueue_Line"
								+ " WHERE C_OrderLine_ID = " + confQueueLine.getC_OrderLine_ID());
				int idQLine = DB.getSQLValue(get_TrxName(), sqlIDQLine.toString());
				MUNSOrderQueueLine queueLine = new MUNSOrderQueueLine(getCtx(), idQLine, get_TrxName());
				queueLine.setStatusQueue("BA");
				queueLine.saveEx();
			}
		}
		
		
		if (!isApproved())
			approveIt();
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}
		
		MUNSPackingList packingList = new MUNSPackingList(getCtx(), getUNS_PackingList_ID(), get_TrxName());
		packingList.processIt(ACTION_Prepare);
		//
		setProcessed(true);
		setDocAction(ACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt

	/**
	 * 	Void Document.
	 * 	Same as Close.
	 * 	@return true if success 
	 */
	public boolean voidIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("voidIt - " + toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		if (!closeIt())
			return false;
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	voidIt
	
	/**
	 * 	Close Document.
	 * 	Cancel not delivered Quantities
	 * 	@return true if success 
	 */
	public boolean closeIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("closeIt - " + toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	closeIt
	
	/**
	 * 	Reverse Correction
	 * 	@return true if success 
	 */
	public boolean reverseCorrectIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reverseCorrectIt - " + toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	reverseCorrectionIt
	
	/**
	 * 	Reverse Accrual - none
	 * 	@return true if success 
	 */
	public boolean reverseAccrualIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reverseAccrualIt - " + toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;				
		
		return false;
	}	//	reverseAccrualIt
	
	/** 
	 * 	Re-activate
	 * 	@return true if success 
	 */
	public boolean reActivateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reActivateIt - " + toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

	//	setProcessed(false);
		if (! reverseCorrectIt())
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		return true;
	}	//	reActivateIt

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}
}
