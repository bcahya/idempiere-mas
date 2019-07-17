/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_BankStatementLine;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

/**
 * @author Burhani Adam
 *
 */
public class MUNSUnloadingCost extends X_UNS_UnloadingCost implements DocOptions, DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1937372744592194263L;

	/**
	 * @param ctx
	 * @param UNS_UnloadingCost_ID
	 * @param trxName
	 */
	public MUNSUnloadingCost(Properties ctx, int UNS_UnloadingCost_ID,
			String trxName) {
		super(ctx, UNS_UnloadingCost_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSUnloadingCost(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	protected boolean beforeSave(boolean newRecord)
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
		setProcessed(false);
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
		
		if(isReceipt() && getC_BankAccount_ID() <= 0)
		{
			m_processMsg = "Mandatory field Bank/Cash Account";
			return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}	//	prepareIt
	
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	
	public String completeIt()
	{
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		if(!actionToComplete())
			return DocAction.STATUS_Invalid;
		
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}		
		//
		setProcessed(true);
		setDocAction(ACTION_Close);
		setIsApproved(true);
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
		
		String sql = "SELECT COUNT(*) FROM UNS_UnloadingCostLine ucl WHERE"
				+ " EXISTS (SELECT 1 FROM UNS_UnloadingCostLine cl WHERE"
				+ " ucl.UNS_UnloadingCostLine_ID = cl.Reference_ID AND EXISTS"
				+ " (SELECT 1 FROM UNS_UnloadingCost uc WHERE uc.UNS_UnloadingCost_ID = cl.UNS_UnloadingCost_ID"
				+ " AND uc.DocStatus NOT IN ('VO', 'RE')))"
				+ " AND ucl.UNS_UnloadingCost_ID = ?";
		int count = DB.getSQLValue(get_TrxName(), sql, get_ID());
		if(count > 0)
		{
			m_processMsg = "One or more lines has linked with other document (unvoided)";
			return false;
		}
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		setDocStatus(DOCSTATUS_Voided);
		setProcessed(true);
		setDocAction(DocAction.ACTION_None);
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
		
		setDocStatus(DOCSTATUS_Closed);
		setDocAction(DocAction.ACTION_None);
		setProcessed(true);
		
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

		return voidIt();
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
		
		return voidIt();
	}	//	reverseAccrualIt
	
	/** 
	 * 	Re-activate
	 * 	@return true if success 
	 */
	public boolean reActivateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reActivateIt - " + toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		if(isReceipt())
		{
			String sql = "SELECT COUNT(*) FROM UNS_UnloadingCostLine ucl WHERE"
					+ " EXISTS (SELECT 1 FROM UNS_UnloadingCostLine cl WHERE"
					+ " ucl.UNS_UnloadingCostLine_ID = cl.Reference_ID)"
					+ " AND ucl.UNS_UnloadingCost_ID = ?";
			int count = DB.getSQLValue(get_TrxName(), sql, get_ID());
			if(count > 0)
			{
				m_processMsg = "One or more lines has linked with other document";
				return false;
			}
			if(getC_BankStatement_ID() > 0)
			{
				MBankStatement st = new MBankStatement(getCtx(), getC_BankStatement_ID(), get_TrxName());
				if(st.isComplete())
				{
					m_processMsg = "Statement has completed";
					return false;
				}
				else
				{
					for(MUNSUnloadingCostLine line : getLines())
					{
						if(line.getC_BankStatementLine_ID() > 0)
						{
							MBankStatementLine sl = new MBankStatementLine(getCtx(), line.getC_BankStatementLine_ID(),
									get_TrxName());
							line.setC_BankStatementLine_ID(-1);
							line.saveEx();
							sl.setIsManual(true);
							sl.saveEx();
							sl.deleteEx(false, get_TrxName());
						}
					}
					setC_BankStatement_ID(-1);
				}
			}
		}
		else
		{
			if(getC_Invoice_ID() > 0)
			{
				MInvoice inv = MInvoice.get(getCtx(), getC_Invoice_ID());
				boolean ok = inv.processIt(DocAction.ACTION_Void);
				if(!ok || !inv.save())
				{
					m_processMsg = inv.getProcessMsg();
					return false;
				}
				setC_Invoice_ID(-1);
			}
		}
		setProcessed(false);
		setDocAction(DOCACTION_Prepare);
		setDocStatus(DOCSTATUS_Drafted);
		saveEx();
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	reActivateIt

	@Override
	public String getSummary() {
		return getDocumentInfo();
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return getGrandTotal();
	}

	@Override
	public boolean approveIt() {
		setIsApproved(true);
		setProcessed(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		setIsApproved(false);
		setProcessed(false);
		return true;
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
				String orderType, String isSOTrx, int AD_Table_ID,
					String[] docAction, String[] options, int index)
	{
		if (docStatus.equals(DocAction.STATUS_Completed))
		{
			options[index++] = DocAction.ACTION_ReActivate;
			options[index++] = DocAction.ACTION_Void;
		}	
		return index;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean actionToComplete()
	{
		if(isReceipt())
		{
			if(getC_BankAccount_ID() <=0)
			{
				m_processMsg = "Mandatory field Bank / Cash Account";
				return false;
			}
			createStatement();
		}
		else
		{
			createInvoice();
		}
		
		return true;
	}
	
	private void createStatement()
	{
		MBankStatement st = MBankStatement.getOpen(getC_BankAccount_ID(), get_TrxName(), true);
		setC_BankStatement_ID(st.get_ID());
		
		for(MUNSUnloadingCostLine line : getLines())
		{
			if(line.getC_BankStatementLine_ID() > 0)
				continue;
			MBankStatementLine stl = new MBankStatementLine(st);
			stl.setC_Charge_ID(getC_Charge_ID());
			stl.setC_BPartner_ID(getC_BPartner_ID());
			stl.setC_Currency_ID(Env.getContextAsInt(getCtx(), "$C_Currency_ID"));
			stl.setStatementLineDate(getDateTrx());
			stl.setTransactionType(X_C_BankStatementLine.TRANSACTIONTYPE_ARTransaction);
			stl.setAmount(line.getAmount());
			stl.setChargeAmt(line.getAmount());
			stl.setStmtAmt(line.getAmount());
			stl.saveEx();
			line.setC_BankStatementLine_ID(stl.get_ID());
			line.saveEx();
		}
	}
	
	private void createInvoice()
	{
		if(getC_Invoice_ID() > 0)
			return;
		
		MInvoice inv = new MInvoice(getCtx(), 0, get_TrxName());
		inv.setClientOrg(getAD_Client_ID(), getAD_Org_ID());
		inv.setDateInvoiced(getDateTrx());
		inv.setC_BPartner_ID(getC_BPartner_ID());
		inv.setIsSOTrx(false);
		inv.setC_DocTypeTarget_ID();
		inv.saveEx();
		
		MInvoiceLine invL = new MInvoiceLine(inv);
		invL.setQty(Env.ONE);
		invL.setC_Charge_ID(getC_Charge_ID());
		invL.setPrice(getGrandTotal());
		invL.setC_Tax_ID(getC_Tax_ID());
		invL.saveEx();
		
		try {
			inv.processIt(DocAction.ACTION_Complete);
			inv.saveEx();
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage() + " _ " + inv.getProcessMsg());
		}
		
		setC_Invoice_ID(inv.get_ID());
	}
	
	public List<MUNSUnloadingCostLine> getLines()
	{
		List<MUNSUnloadingCostLine> list = new Query(getCtx(), MUNSUnloadingCostLine.Table_Name,
				COLUMNNAME_UNS_UnloadingCost_ID + "=?", get_TrxName()).setParameters(get_ID()).list();
		return list;
	}
}