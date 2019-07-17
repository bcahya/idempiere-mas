/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankAccount;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.util.MessageBox;

import com.unicore.base.model.MBankStatementLine;
import com.unicore.model.factory.UNSFinanceModelFactory;
import com.uns.base.model.Query;
import com.uns.model.IUNSApprovalInfo;

/**
 * @author menjangan
 *
 */
public class MUNSTransferBalanceRequest extends X_UNS_TransferBalance_Request 
												implements DocAction, DocOptions, IUNSApprovalInfo
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSTransferBalanceConfirm[] m_confirmations = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSPettyCashRequest[] m_pettycashreq = null;
	private boolean m_force = false;

	/**
	 * @param ctx
	 * @param UNS_TransferBalance_Request_ID
	 * @param trxName
	 */
	public MUNSTransferBalanceRequest(Properties ctx,
			int UNS_TransferBalance_Request_ID, String trxName) {
		super(ctx, UNS_TransferBalance_Request_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSTransferBalanceRequest(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
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
		setDocAction(DOCACTION_Prepare);
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
		
		if(getAccountTo_ID() == 0)
		{
			m_processMsg = "Field mandatory Account To!";
			return DocAction.STATUS_Invalid;
		}
		
		BigDecimal trx = getTrxAmt().negate();
		if(trx.compareTo(getAmountRequested()) == -1)
		{
			boolean validate = MSysConfig.getBooleanValue(MSysConfig.VALIDATE_AMOUNT_REQUEST_ON_TB, false);
			if(validate)
			{
				m_processMsg = "Amount request must small than or equals amount transaction";
				return DocAction.STATUS_Invalid;
			}
			else
			{
				int retVal = MessageBox.showMsg(this, getCtx(), 
						"Continue with transaction amount smaller than amount requisition",
						"Confirmation..",
						MessageBox.YESNOCANCEL, MessageBox.ICONQUESTION);
				if(retVal == MessageBox.RETURN_CANCEL || MessageBox.RETURN_NO == retVal)
				{
					m_processMsg = "Process has cancelled";
					return DocAction.STATUS_Invalid;
				}
			}
		}
		
		m_processMsg = createConfirmation();
		if(null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_PREPARE);
		
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
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
//		Implicit Approval
		if (!isApproved())
			approveIt();
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
//		MUNSTransferBalanceConfirm[] confirms = getConfirmations(true);
//		for(MUNSTransferBalanceConfirm confirm : confirms)
//		{
//			if(!confirm.getDocStatus().equals("CO")
//					&& !confirm.getDocStatus().equals("CL")
//					&& !isForce())
//			{
//				m_processMsg = "Open: @Transfer Confirm@ - " 
//						+ confirm.getDocumentNo();
//					return DocAction.STATUS_InProgress;
//			}
//		}
		
//		if(getAccountFrom_ID() == 0)
//		{
//			m_processMsg = "Field mandatory Account From!";
//			return DocAction.STATUS_Invalid;
//		}
		
//		if(getAccountTo_ID() == 0)
//		{
//			m_processMsg = "Field mandatory Account To!";
//			return DocAction.STATUS_Invalid;
//		}

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
		
//		MUNSTransferBalanceConfirm[] confirms = getConfirmations(true);
//		for (int i=0; i<confirms.length; i++)
//		{
//			MUNSTransferBalanceConfirm confirm = confirms[i];
//			String status = confirm.getDocStatus();
//			if (!DOCSTATUS_Drafted.equals(status) && !isForce())
//			{
//				m_processMsg = "Confirmation is processed.";
//				return false;
//			}
//			else if (!isForce())
//			{
//				confirm.deleteEx(true);
//			}
//		}
		
		getLines(true);
		for (int i=0; i<m_pettycashreq.length; i++)
		{
			MUNSPettyCashRequest r = m_pettycashreq[i];
			r.addDescription("<>voided : " + r.getAmountRequested());
			r.setAmountRequested(Env.ZERO);
			r.saveEx();
		}
		
		String sql = "UPDATE C_BankStatementLine SET UNS_TransferBalance_Request_ID = null "
				+ " WHERE UNS_TransferBalance_Request_ID = ?";
		int updated = DB.executeUpdate(sql, get_ID(), get_TrxName());
		if(log.isLoggable(Level.INFO))
			log.info(updated + " record updated");
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_VOID);
		if (null != m_processMsg)
		{
			return false;
		}
		
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DocAction.ACTION_None);
		setProcessed(true);
		return true;
	}

	@Override
	public boolean closeIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (null != m_processMsg)
			return false;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_CLOSE);
		if (null != m_processMsg)
			return false;
		
		setDocStatus(DOCSTATUS_Closed);
		setDocAction(DocAction.ACTION_None);
		setProcessed(true);
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		m_processMsg = "Revers is not implemented.";
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		m_processMsg = "Reverse is not implemented.";
		return false;
	}

	@Override
	public boolean reActivateIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (null != m_processMsg)
			return false;
		
		MUNSTransferBalanceConfirm[] confirms = getConfirmations(true);
		for (int i=0; i<confirms.length; i++)
		{
			String status = confirms[i].getDocStatus();
			if (!DOCSTATUS_Drafted.equals(status))
			{
				m_processMsg = "Confirmation is processed.";
				return false;
			}
			
			confirms[i].deleteEx(true);
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (null != m_processMsg)
			return false;
		
		setDocStatus(DOCSTATUS_InProgress);
		setProcessed(false);
		setDocAction(DOCACTION_Complete);
		return true;
	}

	@Override
	public String getSummary() {
		MBankAccount account = MBankAccount.get(getCtx(), getAccountTo_ID());
		DecimalFormat df = new DecimalFormat("#,###.00");
		StringBuilder bSummary = new StringBuilder("#Request:")
		.append(getDocumentNo())
		.append("#Account:").append(account.get_Value("Name"))
		.append("#Request:")
		.append(df.format(getAmountRequested()))
		.append("#Transaction:")
		.append(df.format(getTrxAmt()));
		
		return bSummary.toString();
	}

	@Override
	public String getDocumentInfo() {
		return getSummary();
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt()
	{
		BigDecimal diff = getAmountRequested().subtract(getTrxAmt());
		return diff;
	}


	/**
	 * Return null if success or return error message if not success.
	 * @return
	 */
	public String createConfirmation()
	{
		String errorMsg = null;
		
		MUNSTransferBalanceConfirm[] confirms = getConfirmations(true);
		
		if(null != confirms && confirms.length > 0)
			return errorMsg;
		
		MUNSTransferBalanceConfirm confirmation = 
				new MUNSTransferBalanceConfirm(getCtx(), 0, get_TrxName());
		errorMsg = confirmation.createFrom(this);
		
		if(null != errorMsg)
			return errorMsg;
		
		return errorMsg;
	}
	
	/**
	 * 
	 * @param requery
	 * @return {@link MUNSTransferBalanceConfirm}[]
	 */
	public MUNSTransferBalanceConfirm[] getConfirmations(boolean requery)
	{
		if(null != m_confirmations
				&& !requery)
			return m_confirmations;
		
		String whereClause = MUNSTransferBalanceConfirm
						.COLUMNNAME_UNS_TransferBalance_Request_ID + " =?";
		
		List<MUNSTransferBalanceConfirm> list = Query.get(
				getCtx(), UNSFinanceModelFactory.EXTENSION_ID
				, MUNSTransferBalanceConfirm.Table_Name, whereClause, get_TrxName())
				.setParameters(getUNS_TransferBalance_Request_ID()).list();
		
		m_confirmations = new MUNSTransferBalanceConfirm[list.size()];

		m_confirmations = list.toArray(m_confirmations);

		return m_confirmations;
	}
	
	@Override
	public boolean beforeSave(boolean newRecord)
	{
		if(getAccountTo_ID() == 0)
		{
			throw new AdempiereException("Field mandatory Bank Account To!");
		}
		
		MBankAccount ba = new MBankAccount(
				getCtx(), getAccountTo_ID(), get_TrxName());
		setPrevBalanceAmt(ba.getCurrentBalance());
		
		if(REQUESTTYPE_Balance.equals(getRequestType()))
		{
			X_UNS_PettyCashRequest[] lines = getLines(true);

			for(X_UNS_PettyCashRequest line : lines)
			{
				if(!line.delete(true))
					return false;
			}
							
			MBankStatementLine[] sLines = MBankStatementLine.getOf(
					getUNS_TransferBalance_Request_ID(), 0, get_TrxName());
			for(MBankStatementLine sLine : sLines)
			{
				sLine.setUNS_TransferBalance_Request_ID(0);
				sLine.save();
			}
		}
		
		return super.beforeSave(newRecord);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSPettyCashRequest[] getLines(boolean requery)
	{
		if(null != m_pettycashreq
				&& !requery)
			return m_pettycashreq;
		
		List<MUNSPettyCashRequest> list = Query.get(
				getCtx(), UNSFinanceModelFactory.EXTENSION_ID
				, MUNSPettyCashRequest.Table_Name
				, MUNSPettyCashRequest.COLUMNNAME_UNS_TransferBalance_Request_ID + "=?"
				, get_TrxName()).setParameters(getUNS_TransferBalance_Request_ID())
				.list();
		
		MUNSPettyCashRequest[] req = new MUNSPettyCashRequest[list.size()];
		m_pettycashreq = list.toArray(req);
		
		return m_pettycashreq;
	}
	
	/**
	 * 
	 * @return
	 */
	public X_UNS_PettyCashRequest[] getLines()
	{
		return getLines(false);
	}
	
	@Override
	public boolean beforeDelete()
	{
		if(!REQUESTTYPE_PettyCash.equals(getRequestType()))
			return super.beforeDelete();
		
		X_UNS_PettyCashRequest[] lines = getLines(true);
		for(X_UNS_PettyCashRequest line : lines)
		{
			if(!line.delete(true))
				return false;
		}
		
		MBankStatementLine[] sLines = MBankStatementLine.getOf(
				getUNS_TransferBalance_Request_ID(), 0, get_TrxName());
		for(MBankStatementLine sLine : sLines)
		{
			sLine.setUNS_TransferBalance_Request_ID(0);
			sLine.save();
		}
		
		return super.beforeDelete();
	}

	public void setIsForce (boolean force)
	{
		m_force = force;
	}
	
	public boolean isForce ()
	{
		return m_force;
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
			options[index++] = DocAction.ACTION_ReActivate;
			options[index++] = DocAction.ACTION_Void;
		}
		return index;
	}
	
	@Override
	public List<Object[]> getApprovalInfoColumnClassAccessable() {
		List<Object[]> list = new ArrayList<>();
		list.add(new Object[]{Timestamp.class, true});
		list.add(new Object[]{BigDecimal.class, true});
		list.add(new Object[]{String.class, true});
		return list;
	}

	@Override
	public String[] getDetailTableHeader() {
		String[] headers = new String[] {
				"Date","Amount","Description"
		};
		return headers;
	}

	@Override
	public List<Object[]> getDetailTableContent()
	{
		List<Object[]> list = new ArrayList<>();
		String sql = "SELECT DATE(StatementLineDate) AS LineDate, StmtAmt AS Amount,"
				+ " getdescstatementline(C_BankStatementLine_ID) AS Description"
				+ " FROM C_BankStatementLine"
				+ " WHERE UNS_TransferBalance_Request_ID = ?";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			st.setInt(1, get_ID());
			rs = st.executeQuery();
			
			while (rs.next())
			{
				int count = 0;
				Object[] rowData = new Object[3];
				rowData[count] = rs.getObject("LineDate");
				rowData[++count] = rs.getObject("Amount");
				rowData[++count] = rs.getObject("Description");
				
				list.add(rowData);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, st);
		}
		
//		getLines();
//		for (int i=0; i<m_pettycashreq.length; i++)
//		{
//			Object[] objs = new Object[2];
//			objs[0] = m_pettycashreq[i].getDescription();
//			objs[1] = m_pettycashreq[i].getAmountRequested();
//			list.add(objs);
//		}
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