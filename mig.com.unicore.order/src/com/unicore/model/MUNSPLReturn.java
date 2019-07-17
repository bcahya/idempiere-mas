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
import org.compiere.model.MInOut;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.compiere.wf.MWorkflow;

import com.uns.base.model.Query;

import com.unicore.base.model.MInvoice;
import com.unicore.model.factory.UNSOrderModelFactory;
import com.unicore.model.process.InvoiceCalculateDiscount;

/**
 * @author UNTASoft_Andy_Menjangan_AzHaidar_Burhani
 * 
 */
public class MUNSPLReturn extends X_UNS_PL_Return implements DocAction, DocOptions
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8667250410414034575L;
	
	/**
	 * @param ctx
	 * @param UNS_PL_Return_ID
	 * @param trxName
	 */
	public MUNSPLReturn(Properties ctx, int UNS_PL_Return_ID, String trxName)
	{
		super(ctx, UNS_PL_Return_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPLReturn(Properties ctx, ResultSet rs, String trxName)
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
	String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;
	private MUNSPLReturnOrder[] m_ROrder;

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
		setDocAction(DOCACTION_Prepare);
		return true;
	} // invalidateIt

	/**************************************************************************
	 * Prepare Document
	 * 
	 * @return new status (In Progress or Invalid)
	 */
	public String prepareIt()
	{
		if (getDateDoc() == null || getDateDoc().before(getUNS_PackingList().getDateDoc()))
		{
			m_processMsg = "Mandatory or invalid Document Date!";
			return DocAction.STATUS_Drafted;
		}

		if (log.isLoggable(Level.INFO))
			log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		// Lines
		MUNSPLReturnOrder[] rorderList = getROders(true, null);
		if (rorderList.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		
		for (MUNSPLReturnOrder rorder : rorderList)
		{
			if (rorder.isPartialCancelation())
			{
				String sql = "SELECT count(*) FROM UNS_PL_ReturnLine WHERE CancelledQty = 0.0 AND UNS_PL_ReturnOrder_ID=?";
				int count = DB.getSQLValueEx(get_TrxName(), sql, rorder.get_ID());
				
				if (count > 0)
				{
					m_processMsg = "There " + (count > 1? "are" : "is") + count 
							+ " cancelled line" + (count > 1? "s" : "") + " with zero quantity. "
							+ "Please either remove the zero quantity line "
							+ "or set it to non zero cancelled quantity. "
							+ " (Sales Order " 
							+ rorder.getUNS_PackingList_Order().getC_Order().getDocumentNo() + ")";
					return DocAction.STATUS_Invalid;
				}
			}
		}
		
		boolean isMandatoryShipping = MSysConfig.getBooleanValue(
				MSysConfig.MANDATORY_SHIPPING_ON_PACKING, true);
		
		if (isMandatoryShipping)
		{
			MUNSPackingList pl = (MUNSPackingList) getUNS_PackingList();
			MUNSShipping ship = null;
			MUNSShipping shipSett = null;
			MUNSExpedition jobOrder = null;
			ship = MUNSShipping.getShipping(pl);
			jobOrder = MUNSExpedition.getExpedition(pl, "N");
			if(ship != null && ship.getStatus().equals(X_UNS_Shipping.STATUS_Delivery))
			{
				if(!ship.isProcessed())
				{
					m_processMsg = "Please complete Shipping Document first. #" + ship.getDocumentNo();
					return DocAction.STATUS_Invalid;
				}
				shipSett = MUNSShipping.getByReference(getCtx(), ship.get_ID(), get_TrxName());
				if(shipSett == null)
				{
					m_processMsg = "Please create Shipping Settlement. #Shipping Document " + ship.getDocumentNo();
					return DocAction.STATUS_Invalid;
				}
				else if(!shipSett.getDocStatus().equals(DocAction.STATUS_Completed)
						&& !shipSett.getDocStatus().equals(DocAction.STATUS_Closed))
				{
					m_processMsg = "Please complete Shipping Settlement first. #" + shipSett.getDocumentNo();
					return DocAction.STATUS_Invalid;
				}
			}
			else if(jobOrder != null)
			{
				if(!jobOrder.isProcessed())
				{
					m_processMsg = "Please complete Job Order first. #" + jobOrder.getDocumentNo();
					return DocAction.STATUS_Invalid;
				}
				
				MUNSExpedition jobReceipt = MUNSExpedition.getJobOrder(getCtx(), jobOrder.get_ID(), get_TrxName());
				if(jobReceipt != null)
				{
					if(!jobReceipt.getDocStatus().equals(DocAction.STATUS_Completed)
							&& !jobReceipt.getDocStatus().equals(DocAction.STATUS_Closed))
					{
						m_processMsg = "Please complete Job Receipt first. #" + jobReceipt.getDocumentNo();
						return DocAction.STATUS_Invalid;
					}
				}
				else
				{
					m_processMsg ="Please create Job Receipt first. #Job Order " + jobOrder.getDocumentNo();
					return DocAction.STATUS_Invalid;
				}
				
				ship = MUNSShipping.getShipping(jobReceipt);
				
				if(ship != null && ship.getStatus().equals(X_UNS_Shipping.STATUS_Delivery))
				{
					if(!ship.isProcessed())
					{
						m_processMsg = "Please complete Shipping Document first. #" + ship.getDocumentNo();
						return DocAction.STATUS_Invalid;
					}
					shipSett = MUNSShipping.getByReference(getCtx(), ship.get_ID(), get_TrxName());
					if(shipSett == null)
					{
						m_processMsg = "Please create Shipping Settlement. #Shipping Document " + ship.getDocumentNo();
						return DocAction.STATUS_Invalid;
					}
					else if(!shipSett.getDocStatus().equals(DocAction.STATUS_Completed)
							&& !shipSett.getDocStatus().equals(DocAction.STATUS_Closed))
					{
						m_processMsg = "Please complete Shipping Settlement first. #" + shipSett.getDocumentNo();
						return DocAction.STATUS_Invalid;
					}
				}
				else if(ship == null)
				{
					m_processMsg = "Please create Shipping Document first. #Job Receipt " + jobReceipt.getDocumentNo();
					return DocAction.STATUS_Invalid;
				}
			}
			else
			{
				m_processMsg = "Please create Shipping Document OR Job Order first. #Packing List " + pl.getDocumentNo();
				return DocAction.STATUS_Invalid;
			}
		}
		
		m_processMsg =ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;

		return DocAction.STATUS_InProgress;
	} // prepareIt

	/**
	 * Approve Document
	 * 
	 * @return true if success
	 */
	public boolean approveIt()
	{
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
	public boolean rejectIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	} // rejectIt

	@Override
	public String completeIt()
	{
		// Just prepare
		if (DOCACTION_Prepare.equals(getDocAction()))
		{
			setProcessed(false);
			return DocAction.STATUS_InProgress;
		}

		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_processMsg = MUNSPLConfirm.create(this);
		if(!Util.isEmpty(m_processMsg, true))
		{
			setProcessed(true);
			return DocAction.STATUS_InProgress;
		}
		
		m_processMsg = completingOtherDocument(this);
		if (m_processMsg != null) {
			setProcessed(true);
			return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = moveItems();
		if (!Util.isEmpty(m_processMsg,  true)) 
		{
			setProcessed(true);
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

	/**
	 * 
	 * @param plReturn
	 * @return
	 */
	private static String completingOtherDocument(MUNSPLReturn plReturn)
	{
		for (MUNSPLReturnOrder ro : plReturn.getROders())
		{
			String addDescription = null;
			if(ro.isCancelled() && !ro.isPartialCancelation())
			{
				String reason = null;
				switch (ro.getReason()) {
				case X_UNS_PL_ReturnOrder.REASON_OtherReason:
					reason = "Other Reason";
				case X_UNS_PL_ReturnOrder.REASON_Terlambat:
					reason = "Terlambat";
				case X_UNS_PL_ReturnOrder.REASON_TokoTidakDitemukan:
					reason = "Toko Tidak Ditemukan";
				case X_UNS_PL_ReturnOrder.REASON_Tutup:
					reason = "Tutup";
				default:
					break;
				}
				addDescription = "::" + reason + "(" + ro.getDescription() + ")::";
			}
			
			MUNSPackingListOrder plo =
					new MUNSPackingListOrder(ro.getCtx(), ro.getUNS_PackingList_Order_ID(), ro.get_TrxName());
			String docAction =
					(ro.isCancelled()) && !ro.isPartialCancelation()? 
							DocAction.ACTION_Void : DocAction.ACTION_Complete;
			
			MInOut io = (MInOut) plo.getM_InOut();
			if(!io.isComplete())
			{	
				try
				{
					if(ro.isCancelled() && !ro.isPartialCancelation())
					{
						io.setDescription(io.getDescription() + " " + addDescription);
					}
					
					ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(io, docAction);
					if(pi.isError())
					{
						throw new AdempiereException("Error when trying to complete Shipment (Customer) " + pi.getSummary());
					}
					
					io.saveEx();
				}
				catch (Exception e)
				{
					throw new AdempiereException("Error when trying to complete Shipment (Customer) :: " + e.getMessage());
				}
			}
			com.unicore.base.model.MInvoice iv = new MInvoice(plReturn.getCtx(), plo.getC_Invoice_ID(), plReturn.get_TrxName());
			if(!iv.isComplete())
			{
				try
				{
					if(ro.isCancelled() && !ro.isPartialCancelation())
					{
						iv.setDescription(iv.getDescription() + " " + addDescription);
					}
					
					if(ro.isPartialCancelation())
					{
						for(org.compiere.model.MInOutLine ioLine : io.getLines())
						{
							MInvoiceLine line = MInvoiceLine.getOfInOutLine(ioLine);
							if(ioLine.getMovementQty().compareTo(line.getQtyInvoiced()) == 0)
								continue;
							line.setQty(ioLine.getMovementQty());
							if(line.getQtyEntered().compareTo(Env.ZERO) == 0)
								line.setIsActive(false);
							line.setIsEnforcePriceLimit(false);
							line.saveEx();
						}
						iv.setProcessInfo(plReturn.getProcessInfo());
						UNSInvoiceDiscountModel dm = new UNSInvoiceDiscountModel(iv);
						Thread worker = new Thread(new InvoiceCalculateDiscount(dm));
						worker.run();
					}
					iv.setC_DocType_ID(iv.getC_DocTypeTarget_ID());
					ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(
							iv, docAction);
					if(pi.isError())
					{
						throw new AdempiereException("Error when completing Invoice (Customer) " + pi.getSummary());
					}

					iv.saveEx();
				}
				catch (Exception e)
				{
					throw new AdempiereException("Error when completing Invoice (customer) :: " + e.getMessage());
				}
			}
			
			if(ro.isCancelled() && ro.isCancelOrder() && !ro.isPartialCancelation())
			{
				org.compiere.model.MOrder order = (org.compiere.model.MOrder) plo.getC_Order();
				if(!order.getDocStatus().equals("VO") && !order.getDocStatus().equals("RE"))
				{
					order.setDescription(!Util.isEmpty(order.getDescription(),true) ? order.getDescription() : ""
											+ " " + addDescription);
					order.processIt(DocAction.ACTION_Void);
					order.saveEx();
				}
			}
				
		}
		return null;
	}

	@Override
	public boolean voidIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		addDescription(Msg.getMsg(getCtx(), "Voided"));

		
		MUNSPLConfirm[] plReturnConfirmList = MUNSPLConfirm.gets(this);
		
		for (MUNSPLConfirm plConfirm : plReturnConfirmList)
		{
			plConfirm.addDescription("{Voided by Packing List Result (Return) void action (PL Return:" 
									+ getDocumentNo() + ")}.");
			
			if (!plConfirm.processIt(DOCACTION_Void)) {
				m_processMsg = "Failed when void PL Confirmation of " + plConfirm.getDocumentNo();
				return false;
			}
			
			plConfirm.saveEx();	
		}
		
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
	 * Add to Description
	 * 
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription

	@Override
	public boolean closeIt()
	{
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
	public boolean reverseCorrectIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return voidIt();
	} // reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 * 
	 * @return false
	 */
	public boolean reverseAccrualIt()
	{
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

		return false;
	} // reverseAccrualIt

	@Override
	public boolean reActivateIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// TODO palce coding reActivateIt here

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
	public String getSummary()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		if (m_ROrder != null)
			sb.append(" (#").append(m_ROrder.length).append(")");
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
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MPackingSlipReturn[").append(get_ID()).append("-")
						.append(getDocumentNo()).append(", PackingList_ID=")
						.append(getUNS_PackingList().getUNS_PackingList_ID()).append("]");
		return sb.toString();
	} // toString

	/**
	 * Get Document Info
	 * 
	 * @return document info (untranslated)
	 */
	public String getDocumentInfo()
	{
		return "Packing List :" + getDocumentNo();
	} // getDocumentInfo

	@Override
	public File createPDF()
	{
		try
		{
			File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (Exception e)
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
	public File createPDF(File file)
	{
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
	public String getProcessMsg()
	{

		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID()
	{

		return getUpdatedBy();
	}

	@Override
	public BigDecimal getApprovalAmt()
	{
		return Env.ZERO;
	}

	@Override
	public int getC_Currency_ID()
	{
		return 0;
	}

	/**************************************************************************
	 * Get Orders
	 * 
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return orders
	 */
	public MUNSPLReturnOrder[] getROders(String whereClause, String orderClause)
	{
		// red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		StringBuilder whereClauseFinal =
				new StringBuilder(MUNSPLReturnOrder.COLUMNNAME_UNS_PL_Return_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MUNSPLReturnOrder.COLUMNNAME_UNS_PL_ReturnOrder_ID;
		//
		List<MUNSPLReturnOrder> list =
				Query
						.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPLReturnOrder.Table_Name,
								whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID())
						.setOrderBy(orderClause).list();

		return list.toArray(new MUNSPLReturnOrder[list.size()]);
	} // getLines

	/**
	 * Get Lines of Order
	 * 
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	public MUNSPLReturnOrder[] getROders(boolean requery, String orderBy)
	{
		if (m_ROrder != null && !requery)
		{
			set_TrxName(m_ROrder, get_TrxName());
			return m_ROrder;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;

		m_ROrder = getROders(null, orderClause);
		return m_ROrder;
	} // getLines

	/**
	 * Get Lines of Order. (used by web store)
	 * 
	 * @return lines
	 */
	public MUNSPLReturnOrder[] getROders()
	{
		return getROders(false, null);
	} // getLines

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{

		return super.beforeSave(newRecord);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeDelete()
	 */
	@Override
	protected boolean beforeDelete()
	{
		for (MUNSPLReturnOrder rorder : getROders())
		{
			if (!rorder.delete(true))
				return false;
		}

		return super.beforeDelete();
	}

	public static void createPLReturn(MUNSPackingList pl)
	{
		MUNSPLReturn plr = new MUNSPLReturn(pl.getCtx(), 0, pl.get_TrxName());

		plr.initReturn(pl);
		plr.saveEx();

		for (MUNSPackingListOrder order : pl.getOrders())
		{
			MUNSPLReturnOrder plro = new MUNSPLReturnOrder(order.getCtx(), 0, order.get_TrxName());
			plro.setPLOrder(plr);
			plro.setUNS_PackingList_Order_ID(order.get_ID());
			plro.saveEx();
		}
	}

	private void initReturn(MUNSPackingList pl)
	{
		setClientOrg(pl);
		setUNS_PackingList_ID(pl.get_ID());
	}
	
	@Override
	public void setProcessed(boolean processed) {
		for (MUNSPLReturnOrder order : getROders(true, "")){
			order.setProcessed(processed);
			order.saveEx();
		}
		
		super.setProcessed(processed);
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing, String orderType,
			String isSOTrx, int AD_Table_ID, String[] docAction, String[] options, int index)
	{
		if (docStatus.equals(DocAction.STATUS_Drafted))
		{
			options[index++] = DocAction.ACTION_Prepare;
		}
		return index;
	}
	
	
	public String moveItems()
	{
		StringBuilder sb = new StringBuilder();
		MUNSPLConfirm[] confirms = MUNSPLConfirm.gets(this);
		for(MUNSPLConfirm confirm : confirms)
		{
			String error = confirm.move();
			if(Util.isEmpty(error, true))
				continue;
			
			sb.append(error);
		}
		
		String msg = sb.toString();
		if(!Util.isEmpty(msg, true))
			return msg;
		
		return null;
	}

}
