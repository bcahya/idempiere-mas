/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.PeriodClosedException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MDocType;
import org.compiere.model.MMovement;
import org.compiere.model.MPeriod;
import org.compiere.model.MSysConfig;
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
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import com.uns.base.model.Query;
import com.uns.model.MUNSArmada;
import com.uns.model.MUNSChargeConfirmation;
import com.uns.model.MUNSChargeDetail;
import com.uns.model.MUNSChargeRS;

import com.unicore.base.model.MInOut;
import com.unicore.base.model.MOrder;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author Menjangan
 * @See www.untasoft.com
 * 
 */
public class MUNSShipping extends X_UNS_Shipping implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3800365215614863793L;
	private MUNSChargeDetail[] m_lines = null;
	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;
	private MUNSShipping m_reference = null;
	private MUNSShippingFreight[] m_freights = null;
	public static final String REVERSE_INDICATOR = "^_^";
	private boolean m_reversal = false;
 
	/**
	 * @param ctx
	 * @param UNS_Shiping_ID
	 * @param trxName
	 */
	public MUNSShipping(Properties ctx, int UNS_Shiping_ID, String trxName) 
	{
		super(ctx, UNS_Shiping_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSShipping(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}
	

	/**************************************************************************
	 * GetShipping
	 * 
	 * @param PO
	 *            is can be {@link MUNSPackingList} or
	 *            {@link MUNSPackingListLine}
	 * @return true if found
	 */
	public static MUNSShipping getShipping(PO po) 
	{
		StringBuilder wc = new StringBuilder(COLUMNNAME_DocStatus)
		.append(" NOT IN ('").append(DOCSTATUS_Voided).append("','")
		.append(DOCSTATUS_Reversed).append("')");
		
		String tableName = po.get_TableName();
		switch (tableName)
		{
			case MUNSPackingList.Table_Name :
				MUNSPackingList pl = (MUNSPackingList) po;
				wc.append( " AND EXISTS(SELECT ").append(MUNSShippingFreight
						.COLUMNNAME_UNS_Shipping_ID).append(" FROM ")
						.append(MUNSShippingFreight.Table_Name)
						.append(" WHERE ").append(MUNSShippingFreight
								.COLUMNNAME_UNS_Shipping_ID)
						.append("=").append(Table_Name).append(".")
						.append(COLUMNNAME_UNS_Shipping_ID)
						.append(" AND ").append(MUNSShippingFreight
								.COLUMNNAME_UNS_PackingList_ID).append(" = ")
						.append(pl.get_ID()).append(")");
				break;
			case MUNSExpedition.Table_Name :
				MUNSExpedition exp = (MUNSExpedition) po;
				wc.append( " AND EXISTS(SELECT ").append(MUNSShippingFreight
						.COLUMNNAME_UNS_Shipping_ID).append(" FROM ")
						.append(MUNSShippingFreight.Table_Name)
						.append(" WHERE ").append(MUNSShippingFreight
								.COLUMNNAME_UNS_Shipping_ID)
						.append("=").append(Table_Name).append(".")
						.append(COLUMNNAME_UNS_Shipping_ID)
						.append(" AND ").append(MUNSShippingFreight
								.COLUMNNAME_UNS_Expedition_ID).append(" = ")
						.append(exp.get_ID()).append(")");
				break;
			case MOrder.Table_Name :
				MOrder order = (MOrder) po;
				wc.append( " AND EXISTS(SELECT ").append(MUNSShippingFreight
						.COLUMNNAME_UNS_Shipping_ID).append(" FROM ")
						.append(MUNSShippingFreight.Table_Name)
						.append(" WHERE ").append(MUNSShippingFreight
								.COLUMNNAME_UNS_Shipping_ID)
						.append("=").append(Table_Name).append(".")
						.append(COLUMNNAME_UNS_Shipping_ID)
						.append(" AND ").append(MUNSShippingFreight
								.COLUMNNAME_C_Order_ID).append(" = ")
						.append(order.get_ID()).append(")");
				break;
			case MMovement.Table_Name :
				MMovement move = (MMovement) po;
				wc.append( " AND EXISTS(SELECT ").append(MUNSShippingFreight
						.COLUMNNAME_UNS_Shipping_ID).append(" FROM ")
						.append(MUNSShippingFreight.Table_Name)
						.append(" WHERE ").append(MUNSShippingFreight
								.COLUMNNAME_UNS_Shipping_ID)
						.append("=").append(Table_Name).append(".")
						.append(COLUMNNAME_UNS_Shipping_ID)
						.append(" AND ").append(MUNSShippingFreight
								.COLUMNNAME_M_Movement_ID).append(" = ")
						.append(move.get_ID()).append(")");
				break;
			case MInOut.Table_Name :
				MInOut io = (MInOut) po;
				wc.append( " AND EXISTS(SELECT ").append(MUNSShippingFreight
						.COLUMNNAME_UNS_Shipping_ID).append(" FROM ")
						.append(MUNSShippingFreight.Table_Name)
						.append(" WHERE ").append(MUNSShippingFreight
								.COLUMNNAME_UNS_Shipping_ID)
						.append("=").append(Table_Name).append(".")
						.append(COLUMNNAME_UNS_Shipping_ID)
						.append(" AND ").append(MUNSShippingFreight
								.COLUMNNAME_M_InOut_ID).append(" = ")
						.append(io.get_ID()).append(")");
				break;
			default :
				throw new AdempiereException("Unhandled for reference " + tableName);
		}
		
		MUNSShipping shipping = Query.get(po.getCtx(), UNSOrderModelFactory.EXTENSION_ID,
				MUNSShipping.Table_Name, wc.toString(), po.get_TrxName()).first();

		return shipping;
	} // getShipping

	/**************************************************************************
	 * Process document
	 * 
	 * @param processAction
	 *            document action
	 * @return true if performed
	 */
	public boolean processIt(String processAction) {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	} // processIt

	/**
	 * Unlock Document.
	 * 
	 * @return true if success
	 */
	public boolean unlockIt() {
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
	public boolean invalidateIt() {
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
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		m_processMsg = ModelValidationEngine.get()
				.fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// Lines
		MUNSChargeDetail[] lines = getLines(true);
		if (lines.length == 0)
		{
			log.log(Level.INFO, "No line cost");
		}
		
		MUNSShippingFreight[] freights = getFreights(true);
//		if (freights.length == 0 && !isSettlement())
//		{
//			m_processMsg = "Freights is not defined.";
//			return DocAction.STATUS_Invalid;
//		}

		m_processMsg = isSettlement() ? onPreparingSettlement() : onPreparingShipping();
		if(!Util.isEmpty(m_processMsg, true))
			return DocAction.STATUS_Invalid;
		
		for (int i=0; i<m_freights.length; i++)
		{
			freights[i].setProcessed(true);
			freights[i].saveEx();
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		setProcessed(true);
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
		setProcessed(false);
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

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if (!isReversal())
		{
			if(getLinesNoVoucher(true).length > 0 && needCreateChargeConfirm())
			{
				MUNSChargeConfirmation confirmation = getCreateConfirm();
				
				boolean isNeedConfirm = MSysConfig.getBooleanValue(MSysConfig.NEED_CONFIRM_CHARGE_ON_SHIPPING, true);
				if(isNeedConfirm)
				{
					boolean isHitFromConfirmation = Env.getContext(Env.getCtx(), MUNSChargeConfirmation.CONFIRMATION_EVENT) == "Y";
					if(!confirmation.getDocStatus().equals(DOCSTATUS_Completed)
							&& !confirmation.getDocStatus().equals(DOCSTATUS_Closed) && !isHitFromConfirmation)
					{
						m_processMsg = Msg.getMsg("", "Please open Charge Confirmation " 
								.concat(confirmation.getDocumentNo()).concat(confirmation.getName()));
						return DocAction.STATUS_InProgress;
					}
				}
			}

			m_processMsg = isSettlement() ? onCompletingSettlement() : onCompletingShipping();
			if(!Util.isEmpty(m_processMsg, true))
				return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		
		// Implicit Approval
		if (!isApproved())
			approveIt();

		getLines(true);
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		StringBuilder info = new StringBuilder();

		setProcessed(true);
		m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	} // completeIt

	@Override
	public boolean voidIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		
		if (DOCSTATUS_Closed.equals(getDocStatus())
				|| DOCSTATUS_Reversed.equals(getDocStatus())
				|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		if (!isSettlement())
		{
			try
			{
				MUNSShipping settlement = getSettlement();
				if (null != settlement)
				{
					boolean ok = settlement.processIt(DocAction.ACTION_Void);
					if (!ok)
					{
						m_processMsg = settlement.getProcessMsg();
						return false;
					}
					
					settlement.saveEx();
				}
			}
			catch (Exception ex)
			{
				m_processMsg = ex.getMessage();
				return false;
			}
		}
		
		if (!isSettlement() || DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| DOCSTATUS_Approved.equals(getDocStatus())
				|| DOCSTATUS_NotApproved.equals(getDocStatus()))
		{
			getLines(true);
			
			for (int i=0; i<m_lines.length; i++)
			{
				m_lines[i].addDescription("<>Voided Requested Amount : " + 
						m_lines[i].getAmount());
				m_lines[i].setAmount(Env.ZERO);
				m_lines[i].setLiters(Env.ZERO);
				m_lines[i].saveEx();
				if (!m_lines[i].isVoucher())
				{
					continue;
				}
				
				m_lines[i].doCancelVoucher();
			}
			
			MUNSChargeConfirmation confirm = MUNSChargeConfirmation.get(this);
			if (null != confirm)
			{
				String onConfirm = Env.getContext(Env.getCtx(), 
						MUNSChargeConfirmation.CONFIRMATION_EVENT);
				
				if (null == onConfirm)
					onConfirm = "N";
				
				boolean isOnConfirm = "Y".equals(onConfirm);
				
				if (!confirm.isProcessed() && !isOnConfirm)
				{
					confirm.deleteEx(true);
				}
				else if (!isOnConfirm)
				{
					m_processMsg = "Please void confirmation first.";
					return false;
				}
			}
		}
		else
		{
			boolean accrual = false;
			try 
			{
				MPeriod.testPeriodOpen(getCtx(), getDateDoc(), 
						getC_DocType_ID(), getAD_Org_ID());
			}
			catch (PeriodClosedException e) 
			{
				accrual = true;
			}
			
			if (accrual)
				return reverseAccrualIt();
			else
				return reverseCorrectIt();
		}
		
		addDescription(Msg.getMsg(getCtx(), "Voided"));
		setDocStatus(DOCSTATUS_Voided);
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}

	/**
	 * Add to Description
	 * 
	 * @param description
	 *            text
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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		if (!isSettlement())
			return voidIt();
		
		MUNSChargeConfirmation confirm = MUNSChargeConfirmation.get(this);
		if (null != confirm)
		{
			String fromConfirm = Env.getContext(Env.getCtx(), 
					MUNSChargeConfirmation.CONFIRMATION_EVENT);
			
			if (null == fromConfirm)
				fromConfirm = "N";
			
			boolean isFromConfirm = fromConfirm.equals("Y");
			if (!isFromConfirm)
			{
				m_processMsg = "Could not reverse confirmed document.";
				return false;
			}
		}
		
		MUNSShipping reversal = reverse(false);
		if (null == reversal)
			return false;
		
		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return true;
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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		if (!isSettlement())
			return voidIt();
		
		MUNSChargeConfirmation confirm = MUNSChargeConfirmation.get(this);
		if (null != confirm)
		{
			String fromConfirm = Env.getContext(Env.getCtx(), 
					MUNSChargeConfirmation.CONFIRMATION_EVENT);
			
			if (null == fromConfirm)
				fromConfirm = "N";
			
			boolean isFromConfirm = fromConfirm.equals("Y");
			if (!isFromConfirm)
			{
				m_processMsg = "Could not reverse confirmed document.";
				return false;
			}
		}
		
		MUNSShipping reversal = reverse(true);
		if (null == reversal)
			return false;
		
		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return true;
	} // reverseAccrualIt

	@Override
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		try
		{
			MUNSChargeConfirmation confirm = MUNSChargeConfirmation.get(this);
			if (null != confirm)
			{
				String status = confirm.getDocStatus();
				boolean isComplete = !status.equals(DOCSTATUS_Drafted);
				if (isComplete)
				{
					m_processMsg = "Completed confirmation";
				}
				confirm.deleteEx(true);
				return false;
			}
			
			getFreights(true);
			for (int i=0; i<m_freights.length; i++)
			{
				m_freights[i].setProcessed(true);
				m_freights[i].saveEx();
			}
		}
		catch (Exception e)
		{
			m_processMsg = e.getMessage();
			return false;
		}
		
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		setDocStatus(DOCSTATUS_Drafted);
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
		if (getLines(false) != null)
			sb.append(" (#").append(getLines(false).length).append(")");
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
		StringBuffer sb = new StringBuffer("MUNSShipping[").append(get_ID())
				.append("-").append(getDocumentNo()).append(", Name=")
				.append(getName()).append("]");
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
	 * @param file
	 *            output file
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
		return getGrandTotal();
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return super.getC_Currency_ID();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{		
		if(getUNS_Armada_ID() == 0)
		{
			MUNSArmada armada = MUNSArmada.getDefault(get_TrxName());
			setUNS_Armada_ID(armada.get_ID());
			setUNS_Employee_ID(armada.getUNS_Employee_ID());
		}
		
		if(getC_Currency_ID() == 0)
		{
			MAcctSchema[] schems = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID(), get_TrxName());
			setC_Currency_ID(schems[0].getC_Currency_ID());
		}
		
		if(getUNS_Employee_ID() > 0 && Util.isEmpty(getDriver(), true)){
			String name = DB.getSQLValueString(get_TrxName(),
					"select name from uns_employee where uns_employee_id = ?", 
					getUNS_Employee_ID());
			if(null != name && !name.equals(getDriver()))
				setDriver(name);
		}
		
		if(isSettlement() && getUNS_Shipping_Reff_ID() > 0)
		{
			MUNSShipping referer = (MUNSShipping) getUNS_Shipping_Reff();
			if(getC_BPartner_ID() != referer.getC_BPartner_ID())
				throw new AdempiereException(Msg.getMsg(getCtx(), "Not Synchron Business Partner"));
			if(is_ValueChanged(COLUMNNAME_UNS_Shipping_Reff_ID))
			{
				if(!deleteLines())
					throw new AdempiereException(Msg.getMsg(getCtx(), "Failed When Deleting Lines"));
			}
		}
		
		if(getStatus().equals(X_UNS_Shipping.STATUS_Delivery))
		{
			String msg = validationPrevShipping();
			if(!Util.isEmpty(msg, true))
			{
				log.saveError("Error", msg);
				return false;
			}
		}
		initialRitase();

		return super.beforeSave(newRecord);
	}
	
	/**
	 * 
	 * @param force
	 * @return
	 */
	protected boolean deleteLines()
	{
		int result = -1;
		result = DB.executeUpdate(
				"DELETE FROM UNS_Charge_Detail WHERE UNS_Shipping_ID = ?"
				+ " AND Reference_ID IS NOT NULL"
				, new Object[] {get_ID()}, false, get_TrxName());
		if (result != -1)
		{
			result = DB.executeUpdate(
					"DELETE FROM UNS_ShippingFreight WHERE UNS_Shipping_ID = ?"
					, new Object[] {get_ID()}, false, get_TrxName());
		}
		
		return result != -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeDelete()
	 */
	@Override
	protected boolean beforeDelete() {
		if(!deleteLines())
			throw new AdempiereException(Msg.getMsg(getCtx(), 
					"Failed When Deleting Lines"));
		return super.beforeDelete();
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing, String orderType, String isSOTrx,
			int AD_Table_ID, String[] docAction, String[] options, int index) {
		if (docStatus.equals(DOCSTATUS_Drafted))
		{
			options[index++] = DocAction.ACTION_Prepare;
		}
		else if (docStatus.equals(DOCSTATUS_Completed))
		{
			options[index++] = DocAction.ACTION_ReActivate;
			options[index++] = DocAction.ACTION_Void;
//			if (isSettlement())
//			{
//				options[index++] = DocAction.ACTION_Reverse_Accrual;
//				options[index++] = DocAction.ACTION_Reverse_Correct;
//			}
		}
		return index;
	}
	
	/**
	 * 
	 */
	private void initialRitase()
	{
		setRitArmada(initialArmadaRitase());
		setRitDriver(initialDriverRitase());
		if(getHelper1_ID() > 0)
			setRitHelper1(initialHelperRitase(getHelper1_ID()));
		if(getHelper2_ID() > 0)
			setRitHelper2(initialHelperRitase(getHelper2_ID()));
		if(getHelper3_ID() > 0)
			setRitHelper3(initialHelperRitase(getHelper3_ID()));
	}
	
	private String initialArmadaRitase()
	{
		StringBuilder sqlBuild = new StringBuilder("SELECT MAX(").append(COLUMNNAME_RitArmada)
				.append(") FROM ").append(Table_Name).append(" WHERE ")
				.append(COLUMNNAME_UNS_Armada_ID).append("=?").append(" AND ")
				.append(COLUMNNAME_DateDoc).append("=?").append(" AND ")
				.append(COLUMNNAME_DocStatus).append(" IN('CO','CL') AND ")
				.append(COLUMNNAME_UNS_Shipping_ID).append("<> ? AND ")
				.append(COLUMNNAME_Status).append(" = ? ");
		
		String currentRit = RITARMADA_1;
		String lastRit = DB.getSQLValueString(
				get_TrxName(), sqlBuild.toString(), getUNS_Armada_ID()
				, getDateDoc(), getUNS_Shipping_ID(), STATUS_ReturnShipping);
		if(null != lastRit)
		{
			Integer LastInt = new Integer(lastRit);
			Integer currentInt = LastInt+1;
			currentRit = currentInt.toString();
		}
		
		return currentRit;
	}
	
	private String initialDriverRitase()
	{
		StringBuilder sqlBuild = new StringBuilder("SELECT MAX(").append(COLUMNNAME_RitDriver)
				.append(") FROM ").append(Table_Name).append(" WHERE ")
				.append(COLUMNNAME_UNS_Employee_ID).append("=?").append(" AND ")
				.append(COLUMNNAME_DateDoc).append("=?").append(" AND ")
				.append(COLUMNNAME_DocStatus).append(" IN('CO','CL') AND ")
				.append(COLUMNNAME_UNS_Shipping_ID).append("<> ? AND ")
				.append(COLUMNNAME_Status).append(" = ? ");
		
		String currentRit = RITDRIVER_1;
		String lastRit = DB.getSQLValueString(
				get_TrxName(), sqlBuild.toString(), getUNS_Employee_ID()
				, getDateDoc(), get_ID(), STATUS_ReturnShipping);
		if(null != lastRit)
		{
			Integer LastInt = new Integer(lastRit);
			Integer currentInt = LastInt+1;
			currentRit = currentInt.toString();
		}
		
		return currentRit;
	}
	
	
	/**
	 * 
	 * @param Columnname
	 * @return
	 */
	private String initialHelperRitase(int helper_ID)
	{
		Integer lastInt = 0;
		StringBuilder sqlBuild = new StringBuilder("SELECT * ").append(" FROM ")
				.append(Table_Name).append(" WHERE ").append(COLUMNNAME_DateDoc)
				.append("=? AND (").append(COLUMNNAME_DocStatus).append("=? OR ")
				.append(COLUMNNAME_DocStatus).append("=?) AND (")
				.append(COLUMNNAME_Helper1_ID).append("=? OR ").append(COLUMNNAME_Helper2_ID)
				.append(" = ? OR ").append(COLUMNNAME_Helper3_ID).append("= ?) AND ")
				.append(COLUMNNAME_UNS_Shipping_ID).append(" <> ? AND ")
				.append(COLUMNNAME_Status).append(" = ? ");
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = DB.prepareStatement(sqlBuild.toString(), get_TrxName());
			st.setTimestamp(1, getDateDoc());
			st.setString(2, DOCSTATUS_Completed);
			st.setString(3, DOCSTATUS_Closed);
			st.setInt(4, helper_ID);
			st.setInt(5, helper_ID);
			st.setInt(6, helper_ID);
			st.setInt(7, get_ID());
			st.setString(8, STATUS_ReturnShipping);
			rs = st.executeQuery();
			while (rs.next()) {
				String LastRitase = null;
				int rsHelper1_ID = rs.getInt(COLUMNNAME_Helper1_ID);
				int rsHelper2_ID = rs.getInt(COLUMNNAME_Helper2_ID);
				int rsHelper3_ID = rs.getInt(COLUMNNAME_Helper3_ID);
				if(rsHelper1_ID == helper_ID)
					LastRitase = rs.getString(COLUMNNAME_RitHelper1);
				else if(rsHelper2_ID == helper_ID)
					LastRitase = rs.getString(COLUMNNAME_RitHelper2);
				else if(rsHelper3_ID == helper_ID)
					LastRitase = rs.getString(COLUMNNAME_RitHelper3);
				
				Integer tmp = Integer.valueOf(LastRitase);
				if(tmp <= lastInt)
					continue;
				
				lastInt = tmp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Integer currentInt = lastInt+1;
		String currentRit = currentInt.toString();
		
		return currentRit;
	}
	
	public boolean isSettlement()
	{
		return getStatus().equals(STATUS_ReturnShipping) || getStatus().equals(STATUS_AdditionalSettlement);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if((newRecord || is_ValueChanged(COLUMNNAME_UNS_Shipping_Reff_ID))
				&& isSettlement() && getUNS_Shipping_Reff_ID() > 0)
		{
			MUNSShipping sh = new MUNSShipping(getCtx(), getUNS_Shipping_Reff_ID(), get_TrxName());
			for(MUNSShippingFreight sf : sh.getFreights(true))
			{
				if(sf.getFreightType().equals(X_UNS_ShippingFreight.FREIGHTTYPE_Expedition)
						&& sf.getUNS_Expedition_ID() > 0)
				{
					MUNSExpedition exp = MUNSExpedition.getByReference(getCtx(), sf.getUNS_Expedition_ID(), get_TrxName());
					if(exp != null && (exp.getDocStatus().equals(DocAction.STATUS_Completed)
							|| exp.getDocStatus().equals(DocAction.STATUS_Closed)))
					{
						MUNSShippingFreight freight = MUNSShippingFreight.getByParentAndReference(getCtx(),
								get_ID(), exp, get_TrxName());
						if(freight == null)
						{
							freight = new MUNSShippingFreight(this);
							freight.setUNS_Shipping_ID(get_ID());
							freight.setFreightType(X_UNS_ShippingFreight.FREIGHTTYPE_Expedition);
							freight.setUNS_Expedition_ID(exp.get_ID());
							freight.setDescription("**Added automatically base on reference expedition**");
							freight.saveEx();
						}
					}
				}
			}
		}
		return super.afterSave(newRecord, success);
	}
		
	/**
	 * Get Confirmation of charge and create if not exists
	 * @return {@link MUNSChargeConfirmation}
	 */
	public MUNSChargeConfirmation getCreateConfirm()
	{
		MUNSChargeConfirmation confirmation = MUNSChargeConfirmation.get(this);
		if(null != confirmation)
			return confirmation;
		
		confirmation = new MUNSChargeConfirmation(this);
		confirmation.setName("Confirmation of Shipping Charge "
				.concat(getDocumentNo()).concat("-").concat(getName()));
		confirmation.setConfirmationType(MUNSChargeConfirmation.CONFIRMATIONTYPE_ChargeRequestConfirmation);
		confirmation.setRequestDate(getDateDoc());
		confirmation.saveEx();
		confirmation.createLinesFrom(this);
		return confirmation;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSChargeDetail[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		m_lines =  MUNSChargeDetail.gets(this);
		return m_lines;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSChargeDetail[] getLinesNoVoucher (boolean requery)
	{
		List<MUNSChargeDetail> list = new ArrayList<>();
		getLines(requery);
		for (int i=0; i<m_lines.length; i++)
		{
			if (m_lines[i].isVoucher())	{	continue;}
			list.add(m_lines[i]);
		}
		MUNSChargeDetail[] retVal = new MUNSChargeDetail[list.size()];
		list.toArray(retVal);
		
		return retVal;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSShipping getReference()
	{
		if(null != m_reference)
		{
			m_reference.set_TrxName(get_TrxName());
			return m_reference;
		}
		m_reference = new MUNSShipping(getCtx(), getUNS_Shipping_Reff_ID(), get_TrxName());
		return m_reference;
	}
	
	/**
	 * Check all charge of Charge Request has been realized.
	 * @param settlement
	 * @return
	 */
	public boolean allChargeHasRealizedOn(MUNSChargeRS settlement)
	{
		for(MUNSChargeDetail thisLine : getLines(false))
		{
			if(!thisLine.hasRealizedOn(settlement.getLines(false)))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Validate on preparing Request document
	 * @return {@link String} error message
	 */
	private String onPreparingSettlement()
	{
		return null;
	}

	/**
	 * Validate on preparing Request document
	 * @return {@link String} error message
	 */
	private String onPreparingShipping()
	{
		if(!getStatus().equals(X_UNS_Shipping.STATUS_Delivery))
			return "";
		
		return validationPrevShipping();
	}
	
	private String validationPrevShipping()
	{
		String whereClause = "DocStatus IN ('CO', 'CL')"
				+ " AND UNS_Employee_ID = ? AND (DateDoc BETWEEN '2018-01-01'::timestamp AND ?)"
				+ " AND Status = 'DV' AND UNS_Shipping_ID <> ? AND ShippingType NOT IN('EE', 'PU') ";
		String msg = null;
		
		MUNSShipping shipping = new Query(getCtx(), Table_Name, whereClause, get_TrxName())
									.setParameters(getUNS_Employee_ID(), getDateDoc(), get_ID())
										.setOrderBy("DateDoc DESC").first();
		if(shipping == null)
			return msg;
		MUNSShipping settlement = MUNSShipping.getByReference(getCtx(), shipping.get_ID(), get_TrxName());

		if(settlement == null)
			msg = "Please create shipping settlement first for shipping document " + shipping.getDocumentNo();
		else if((!settlement.getDocStatus().equals(DocAction.STATUS_Completed)
				&& !settlement.getDocStatus().equals(DocAction.STATUS_Closed)))
			msg = "Please complete shipping settlement first " + settlement.getDocumentNo();
		
		return msg;
	}

	/**
	 * Validate on completing Settlement document
	 * @return {@link String} error message
	 */
	private String onCompletingSettlement()
	{
		for(int i=0; i<m_lines.length; i++)
		{
			if(m_lines[i].isCancelled())
				m_lines[i].doCancelVoucher();
		}
		
		return null;
	}
	
	/**
	 * Validate on completing Request document
	 * @return {@link String} error message
	 */
	private String onCompletingShipping()
	{
		return null;
	}
	
	/**
	 * Get freight
	 * @param requery
	 * @return
	 */
	public MUNSShippingFreight[] getFreights (boolean requery)
	{
		if (null != m_freights && !requery)
		{
			set_TrxName(m_freights, get_TrxName());
			return m_freights;
		}
		
		List<MUNSShippingFreight> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, 
				MUNSShippingFreight.Table_Name, Table_Name + "_ID = ?", 
				get_TrxName()).setParameters(get_ID()).
				setOnlyActiveRecords(true).list();
		
		m_freights = new MUNSShippingFreight[list.size()];
		list.toArray(m_freights);
		
		return m_freights;
	}
	
	private MUNSShipping reverse (boolean accrual)
	{
		Timestamp reversalDate = accrual ? Env.getContextAsDate(getCtx(), "#Date") : getDateDoc();
		if (reversalDate == null) {
			reversalDate = new Timestamp(System.currentTimeMillis());
		}
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (!MPeriod.isOpen(getCtx(), reversalDate, dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return null;
		}
		
		MUNSShipping reversal = new MUNSShipping(getCtx(), 0, get_TrxName());
		PO.copyValues(this, reversal, getAD_Client_ID(), getAD_Org_ID());
		reversal.setUNS_Shipping_Reff_ID(getUNS_Shipping_Reff_ID());
		reversal.setDocStatus(DOCSTATUS_Drafted);
		reversal.setDocAction(DocAction.ACTION_Complete);
		reversal.setProcessed(false);
		reversal.setIsApproved(false);
		reversal.setPosted(false);
		reversal.setDateDoc(reversalDate);
		reversal.setTonase(Env.ZERO);
		reversal.setVolume(Env.ZERO);
		reversal.setTotalAmt(Env.ZERO);
		reversal.setGrandTotal(Env.ZERO);
		reversal.setDocumentNo(getDocumentNo() + REVERSE_INDICATOR);
		reversal.addDescription("{->" + getDocumentNo() + "}");
		reversal.setReversal_ID(get_ID());
		
		try
		{
			reversal.saveEx();
			reversal.setReversal(true);
			
			getLines(true);
			for (int i=0; i<m_lines.length; i++)
			{
				MUNSChargeDetail cd = m_lines[i];
				MUNSChargeDetail reversalLine = new MUNSChargeDetail(reversal);
				PO.copyValues(cd, reversalLine, getAD_Client_ID(), getAD_Org_ID());
				reversalLine.setReversalLine_ID(cd.get_ID());
				reversalLine.setAmount(cd.getAmount().negate());
				reversalLine.setAmountConfirmed(cd.getAmountConfirmed().negate());
				reversalLine.saveEx();
				cd.setReversalLine_ID(reversalLine.get_ID());
				cd.saveEx();
			}
			
			if (!reversal.processIt(DocAction.ACTION_Complete))
			{
				m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
				return null;
			}
			
			reversal.setDocStatus(DOCSTATUS_Reversed);
			reversal.setDocAction(DOCACTION_None);
			reversal.saveEx();
			
			//	Update Reversed (this)
			addDescription("(" + reversal.getDocumentNo() + "<-)");
			//FR [ 1948157  ]
			setReversal_ID(reversal.get_ID());
		}
		catch (Exception ex)
		{
			m_processMsg = ex.getMessage();
			return null;
		}

		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed);	//	may come from void
		setDocAction(DOCACTION_None);
		
		return reversal;
	}
	
	public MUNSShipping getSettlement ()
	{
		if (isSettlement())
		{
			return null;
		}
		
		String sql = "SELECT * FROM UNS_Shipping WHERE UNS_Shipping_Reff_ID = ?"
				+ " AND DocStatus NOT IN ('RE','VO')";
		MUNSShipping stl = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			st.setInt(1, get_ID());
			rs = st.executeQuery();
			if (rs.next())
			{
				stl = new MUNSShipping(getCtx(), rs, get_TrxName());
			}
		}
		catch (SQLException ex)
		{
			throw new AdempiereException(ex.getMessage());
		}
		finally
		{
			DB.close(rs, st);
		}
		
		return stl;
	}
	
	public boolean isReversal ()
	{
		return m_reversal;
	}
	
	public void setReversal (boolean reversal)
	{
		m_reversal = reversal;
	}
	
	protected boolean needCreateChargeConfirm()	{
		if(!isSettlement())
			return true;
		
		String sql = "SELECT COUNT(*) FROM UNS_Charge_Detail cd WHERE ChargeType = 'CS' AND cd.UNS_Shipping_ID = ?"
				+ " AND (cd.Reference_ID <= 0 OR cd.Reference_ID IS NULL OR cd.Amount <> (SELECT COALESCE(cdd.AmountConfirmed,0) FROM UNS_Charge_Detail cdd"
				+ " WHERE cdd.UNS_Charge_Detail_ID = cd.Reference_ID))";
		
		return DB.getSQLValue(get_TrxName(), sql, get_ID()) > 0;
	}
	
	public static MUNSShipping getByReference(Properties ctx, int UNS_Shipping_Reff_ID, String trxName)
	{
		String whereClause = "UNS_Shipping_Reff_ID = ? AND DocStatus NOT IN ('VO', 'RE')"; 
		MUNSShipping shipping = Query.get(ctx, UNSOrderModelFactory.EXTENSION_ID,
				MUNSShipping.Table_Name, whereClause, trxName).setParameters(UNS_Shipping_Reff_ID).first();

		return shipping;
	}
}
