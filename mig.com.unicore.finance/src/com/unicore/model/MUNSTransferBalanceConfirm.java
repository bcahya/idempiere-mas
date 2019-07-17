/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankAccount;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.compiere.wf.MWorkflow;

import com.uns.util.UNSApps;

import com.unicore.base.model.MBankStatementLine;
import com.unicore.base.model.MPayment;
import com.unicore.model.factory.UNSFinanceModelFactory;
import com.unicore.model.process.UNSBankTransfer;
import com.uns.base.model.Query;
import com.uns.model.IUNSApprovalInfo;
import com.uns.model.MUNSChequebook;

/**
 * @author menjangan
 *
 */
public class MUNSTransferBalanceConfirm extends X_UNS_TransferBalance_Confirm 
implements DocAction, DocOptions, IUNSApprovalInfo
{
	
	private static final long serialVersionUID = 1L;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSPettyCashConfirm[] m_pettyCashConfirm = null;
	private int m_charge_id = 0;

	/**
	 * @param ctx
	 * @param UNS_TransferBalance_Confirm_ID
	 * @param trxName
	 */
	public MUNSTransferBalanceConfirm(Properties ctx,
			int UNS_TransferBalance_Confirm_ID, String trxName) 
	{
		super(ctx, UNS_TransferBalance_Confirm_ID, trxName);
		initialCharge();
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSTransferBalanceConfirm(Properties ctx, ResultSet rs,
			String trxName) 
	{
		super(ctx, rs, trxName);
		initialCharge();
	}

	@Override
	public boolean processIt(String action) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if(getAccountFrom_ID() == 0)
		{
			m_processMsg = "Field mandatory Account From!";
			return DocAction.STATUS_Invalid;
		}
		
		if(getAccountTo_ID() == 0)
		{
			m_processMsg = "Field mandatory Account To!";
			return DocAction.STATUS_Invalid;
		}
		
		if (getAmountConfirmed().signum() == 0)
		{
			m_processMsg = "Zero comfirmed amount.";
			return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		setIsApproved(true);
		setProcessed(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		setProcessed(false);
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
//		Implicit Approval
		if (!isApproved())
			approveIt();
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		MUNSTransferBalanceRequest request = new MUNSTransferBalanceRequest(
				getCtx(), getUNS_TransferBalance_Request_ID(), get_TrxName());
		
		request.setAmountConfirmed(getAmountConfirmed());
		request.setAccountFrom_ID(getAccountFrom_ID());
		request.save();
		
		if(REQUESTTYPE_PettyCash.equals(getRequestType()))
		{
			X_UNS_PettyCash_Confirm[] lines = getLines(true);
			for(X_UNS_PettyCash_Confirm line : lines)
			{
				X_UNS_PettyCashRequest reqLine = new X_UNS_PettyCashRequest(
						getCtx(), line.getUNS_PettyCashRequest_ID(), get_TrxName());
				reqLine.setAmountConfirmed(line.getAmountConfirmed());
				if(!reqLine.save())
				{
					m_processMsg = "Can't update Request Line !!!";
					return DocAction.STATUS_Invalid;
				}
			}
		}
		
		if(isFullyConfirmed())
		{			
			ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(request, DocAction.ACTION_Complete);
			if(pi.isError())
			{
				m_processMsg = "Can't complete Request Document!. " +
						"please contact the administrator to fix it!";
				return DocAction.STATUS_Invalid;
			}
		}
		
		UNSBankTransfer bt = new UNSBankTransfer(
				getCtx(), "", getDescription(), getC_BPartner_ID(), 
				getC_Currency_ID(), 0, getC_Charge_ID(), getCheckNo(), 
				getAmountConfirmed(), getAccountFrom_ID(), 
				getAccountTo_ID(), getDateConfirm(), getDateConfirm(), 
				getAD_Org_ID(), request.getAD_Org_ID(), true, get_TrxName());
		
		m_processMsg = bt.doIt();
		if (null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		setPaymentFrom_ID(bt.getPaymentBankFrom_ID());
		setPaymentTo_ID(bt.getPaymentBankTo_ID());
		saveEx();
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {

		if (DOCSTATUS_Closed.equals(getDocStatus())
			|| DOCSTATUS_Reversed.equals(getDocStatus())
			|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_VOID);
		if (null != m_processMsg)
		{
			return false;
		}
		
		if (DOCSTATUS_Closed.equals(getDocStatus())
				|| DOCSTATUS_Reversed.equals(getDocStatus())
				|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}
		
		MUNSTransferBalanceRequest request = new MUNSTransferBalanceRequest(
				getCtx(), getUNS_TransferBalance_Request_ID(), get_TrxName());
		
		if (!DOCSTATUS_Voided.equals(request.getDocStatus()))
		{
			try {
				request.setIsForce(true);
				boolean ok = request.processIt(DocAction.ACTION_Void);
				if (!ok)
				{
					m_processMsg = request.getProcessMsg();
					return false;
				}
				request.saveEx();
				
				if (null != m_processMsg)
					return false;
				
			} catch (Exception e) {
				m_processMsg = e.getMessage();
				return false;
			}	
		}
		
		getLines(true);
		for (int i=0; i<m_pettyCashConfirm.length; i++)
		{
			MUNSPettyCashConfirm l = m_pettyCashConfirm[i];
			l.addDescription("<>Voided : " + l.getAmountConfirmed());
			l.setAmountConfirmed(Env.ZERO);
			l.saveEx();
		}
		
		MPayment payment = new MPayment(getCtx(), getPaymentFrom_ID(), 
				get_TrxName());
		payment.setGridTab(getGridTab());
		String payStatus = payment.getDocStatus();
		if (!"RE".equals(payStatus) && !"VO".equals(payStatus))
		{
			payment.m_force = true;
			boolean ok = payment.processIt(DocAction.ACTION_Void);
			if (!ok)
			{
				m_processMsg = "Falied when void payment :: " 
							+ payment.getProcessMsg();
				return false;
			}
			payment.saveEx();
		}
		
		addDescription("::Voided::");
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_VOID);
		if (null != m_processMsg)
		{
			return false;
		}
		
		setDocStatus(DOCSTATUS_Voided);
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public boolean closeIt() {
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (null != m_processMsg)
		{
			return false;
		}
		
		m_processMsg = doPaymentAction(getPaymentFrom_ID(), 
				DocAction.ACTION_Close);
		if (null != m_processMsg)
			return false;
		m_processMsg = doPaymentAction(getPaymentTo_ID(), DocAction.ACTION_Close);
		if (null != m_processMsg)
			return false;
		
		//define logic
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_CLOSE);
		if (null != m_processMsg)
		{
			return false;
		}
		
		setProcessed(true);
		setDocStatus(DOCSTATUS_Closed);
		setDocAction(DocAction.ACTION_None);
		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		m_processMsg = "Reverse is not implemented";
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		m_processMsg = "Reverse is not implemented";
		return false;
	}

	@Override
	public boolean reActivateIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;	
		
		if(getPaymentFrom_ID() > 0)
		{
			MPayment payFrom = new MPayment(getCtx(), getPaymentFrom_ID(), get_TrxName());
			if(!payFrom.processIt(DocAction.ACTION_Void) || !payFrom.save())
			{
				m_processMsg = payFrom.getProcessMsg();
				return false;
			}
			setPaymentFrom_ID(-1);
		}
		
		if(getPaymentTo_ID() > 0)
		{
			MPayment payTo = new MPayment(getCtx(), getPaymentTo_ID(), get_TrxName());
			if(!payTo.getDocStatus().equals("VO") && !payTo.getDocStatus().equals("RE"))
			{
				if(!payTo.processIt(DocAction.ACTION_Void) || !payTo.save())
				{
					m_processMsg = payTo.getProcessMsg();
					return false;
				}
			}
			setPaymentTo_ID(-1);
		}
		
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	}

	@Override
	public String getSummary() {
		StringBuilder bSummary = new StringBuilder("Petty Cash Confirmation [")
		.append(getDocumentNo()).append("]").append("|Request Document [")
		.append("").append(getUNS_TransferBalance_Request()
				.getDocumentNo()).append("]").append("|Approval Amount : ")
				.append(getApprovalAmt());
		return bSummary.toString();
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
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return getAmountConfirmed();
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public String createFrom(MUNSTransferBalanceRequest request)
	{
		setAD_Org_ID(request.getAD_OrgTo_ID());
		setAD_OrgFrom_ID(request.getAD_Org_ID());
		setAccountTo_ID(request.getAccountTo_ID());
		setAccountFrom_ID(request.getAccountFrom_ID());
		setAmountConfirmed(request.getAmountRequested());
		setAmountRequested(request.getAmountRequested());
		setC_BPartner_ID(request.getC_BPartner_ID());
		setDateConfirm(request.getDateRequired());
		setDateRequired(request.getDateRequired());
		setDescription(request.getDescription());
		setUNS_TransferBalance_Request_ID(request.get_ID());
		setRequestType(request.getRequestType());
		
		if(!save())
		{
			return "Can't create confirmatioins!. " +
					"Please contact administrator to fix it";	
		}
		
		if(!request.getRequestType().equals(REQUESTTYPE_PettyCash))
			return null;
		
		X_UNS_PettyCashRequest[] reqLines = request.getLines(true);
		
		for(X_UNS_PettyCashRequest reqLine :reqLines)
		{
			X_UNS_PettyCash_Confirm confLine = new X_UNS_PettyCash_Confirm(
										getCtx(), 0, get_TrxName());
			confLine.setAD_Org_ID(getAD_Org_ID());
			confLine.setAmountConfirmed(reqLine.getAmountRequested());
			confLine.setAmountRequested(reqLine.getAmountRequested());
			confLine.setDescription(reqLine.getDescription());
			confLine.setUNS_TransferBalance_Confirm_ID(get_ID());
			confLine.setUNS_PettyCashRequest_ID(reqLine.get_ID());
			confLine.save();
		}
		
		//load statement line
		MBankStatementLine[] sLines = MBankStatementLine.getOf(
				request.get_ID(), 0, get_TrxName());
		
		for(MBankStatementLine sLine : sLines)
		{
			sLine.setUNS_TransferBalance_Confirm_ID(this.get_ID());
			sLine.save();
		}
			
		return null;
	}
	
	/**
	 * return true if amount confirmed = amount requested
	 * @return
	 */
	public boolean isFullyConfirmed()
	{
		if(getRequestType().equals(REQUESTTYPE_Balance))
		{
			return getAmountConfirmed().compareTo(getAmountRequested()) == 0;
		}
		else if(getRequestType().equals(REQUESTTYPE_PettyCash))
		{
			X_UNS_PettyCash_Confirm[] confLines = getLines(true);
			for(X_UNS_PettyCash_Confirm confLine : confLines)
			{
				if(confLine.getAmountRequested().compareTo(
						confLine.getAmountConfirmed()) != 0)
					return false;
			}
		}
		else
		{
			log.log(Level.SEVERE, "Unknown Request Type : " + getRequestType());
		}
		
		return true;
	}
	
	@Override
	public boolean beforeSave(boolean newRecord)
	{
		if(!newRecord)
			if(getAccountFrom_ID() == 0)
				throw new AdempiereException("Field mandatory Account From");
		if(getAccountFrom_ID() > 0)
		{
			MBankAccount ba = new MBankAccount(getCtx(), getAccountFrom_ID(), get_TrxName());
			setPrevBalanceAmt(ba.getCurrentBalance());
		}
		
		if (newRecord || is_ValueChanged(COLUMNNAME_CheckNo))
		{
			String error = checkChequeNo();
			if (null != error)
			{
				throw new AdempiereUserError(error);
			}
		}
				
		return super.beforeSave(newRecord);
	}
	
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSPettyCashConfirm[] getLines(boolean requery)
	{
		if(null != m_pettyCashConfirm
				&& !requery)
			return m_pettyCashConfirm;
		
		List<MUNSPettyCashConfirm> list = Query.get(
				getCtx(), UNSFinanceModelFactory.EXTENSION_ID
				, MUNSPettyCashConfirm.Table_Name
				, MUNSPettyCashConfirm.COLUMNNAME_UNS_TransferBalance_Confirm_ID + "=?"
				, get_TrxName()).setParameters(get_ID())
				.list();
		
		MUNSPettyCashConfirm[]  conf = new MUNSPettyCashConfirm[list.size()];
		m_pettyCashConfirm = list.toArray(conf);
		
		return m_pettyCashConfirm;
	}
	
	/**
	 * 
	 * @return
	 */
	public X_UNS_PettyCash_Confirm[] getLines()
	{
		return getLines(false);
	}
	
	public void addDescription(String description) 
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}
	
	@Override
	protected boolean beforeDelete ()
	{
		getLines();
		for (int i=0; i<m_pettyCashConfirm.length; i++)
		{
			m_pettyCashConfirm[i].deleteEx(true);
		}
		return super.beforeDelete();
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) 
	{
		if (DOCSTATUS_Drafted.equals(docStatus))
		{
			options[index++] = DocAction.ACTION_Prepare;
		}
		else if (DOCSTATUS_Completed.equals(docStatus))
		{
			options[index++] = DocAction.ACTION_Void;
			options[index++] = DocAction.ACTION_ReActivate;
		}
		return index;
	}
	
	private String doPaymentAction (int C_Payment_ID, String action)
	{
		if (C_Payment_ID <= 0)
		{
			return null;
		}
		
		MPayment payment = new MPayment(getCtx(), C_Payment_ID, get_TrxName());
		boolean ok = payment.processIt(action);
		if (!ok)
		{
			return payment.getProcessMsg();
		}
		payment.saveEx();
		
		return null;
	}
	
	private String checkChequeNo()
	{
		String msg = null;
		String chequeNo = getCheckNo();
		if (Util.isEmpty(chequeNo, true))
		{
			return null;
		}
		
		MUNSChequebook cb = MUNSChequebook.isRegistered(
				getCtx(), get_TrxName(), chequeNo);
		
		if ((cb == null))
		{
			return Msg.getMsg(getCtx(), "Check-No of " + chequeNo +  
					" is not registered yet.");
		}
		
		String cekNum = MUNSChequebook.getProperChequeNumberFormat(chequeNo, cb);
		
		if (cb.isUsed(cekNum,-1)) {
			msg = Msg.getMsg(getCtx(), "Check-No of " + chequeNo +  
					" has been used.");
		}
		else {
			chequeNo = cekNum;
		}
		setCheckNo(chequeNo);
		return msg;
	}
	
	private void initialCharge ()
	{
		m_charge_id = UNSApps.getRefAsInt(UNSApps.CHRG_PSL);
	}
	
	public int getC_Charge_ID ()
	{
		return m_charge_id;
	}

	@Override
	public List<Object[]> getApprovalInfoColumnClassAccessable() {
		List<Object[]> list = new ArrayList<>();
		list.add(new Object[]{String.class, true});
		list.add(new Object[]{BigDecimal.class, true});
		list.add(new Object[]{BigDecimal.class, true});
		return list;
	}

	@Override
	public String[] getDetailTableHeader() {
		String[] headers = new String[] {
				"Description","Requsted Amount", "Confirmed Amount"
		};
		return headers;
	}

	@Override
	public List<Object[]> getDetailTableContent() {
		List<Object[]> list = new ArrayList<>();
		getLines();
		for (int i=0; i<m_pettyCashConfirm.length; i++)
		{
			Object[] objs = new Object[3];
			objs[0] = m_pettyCashConfirm[i].getDescription();
			objs[1] = m_pettyCashConfirm[i].getAmountRequested();
			objs[2] = m_pettyCashConfirm[i].getAmountConfirmed();
			list.add(objs);
		}
		return list;
	}

	@Override
	public boolean isShowAttachmentDetail() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTableIDDetail() {
		// TODO Auto-generated method stub
		return 0;
	}
}
