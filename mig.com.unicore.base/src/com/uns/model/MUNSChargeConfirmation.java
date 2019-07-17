/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.I_C_BankStatement;
import org.compiere.model.MBankStatement;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.compiere.wf.MWorkflow;

import com.unicore.ui.ISortTabRecord;

import com.uns.util.ErrorMsg;

/**
 * @author root
 *
 */
public class MUNSChargeConfirmation extends X_UNS_Charge_Confirmation implements
		DocAction, DocOptions, ISortTabRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6235463603475588066L;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSChargeDetail[] m_lines = null;
	public static final String CONFIRMATION_EVENT = "ON_CONFIRMATION_COMPLETE";

	/**
	 * @param ctx
	 * @param UNS_Charge_Confirmation_ID
	 * @param trxName
	 */
	public MUNSChargeConfirmation(Properties ctx,
			int UNS_Charge_Confirmation_ID, String trxName) {
		super(ctx, UNS_Charge_Confirmation_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSChargeConfirmation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSChargeConfirmation(PO po)
	{
		this(po.getCtx(), 0, po.get_TrxName());
		set_Value(po.get_TableName().concat("_ID"), po.get_ID());
		setC_BPartner_ID(po.get_ValueAsInt("C_BPartner_ID"));
		if(po.get_ValueAsInt("UNS_Shipping_ID") > 0)
			setUNS_Shipping_ID(po.get_ValueAsInt("UNS_Shipping_ID"));
		setClientOrg(po);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		log.info("Processing document " .concat(toString()));
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		if (log.isLoggable(Level.INFO))
			log.info("unlockIt - " + toString());
		setProcessed(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		setProcessed(false);
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if(getC_BankStatement_ID() ==0)
		{
			throw new AdempiereUserError(Msg.getMsg(Env.getAD_Language(getCtx()), "Field Mandatory Bank Statement"));
		}
		else if(getDateConfirm() == null || getDateConfirm().before(getRequestDate()))
		{
			throw new AdempiereUserError(Msg.getMsg(Env.getAD_Language(getCtx()), "Invalid Date Confirm"));
		}
		
		getLines(true);
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
			m_justPrepared = true;
		
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		log.info(toString());
		if(!isApproved())
			setIsApproved(true);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		log.info(toString());
		setProcessed(false);
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
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = doCreateStatementLines();
		if(m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = doCompleteReference();
		if (!Util.isEmpty(m_processMsg, true))
			return DocAction.STATUS_Invalid;
		
		if (!isApproved())
		{
			approveIt();
		}
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);	
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
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
		
		m_processMsg = doVoidReference();
		if (!Util.isEmpty(m_processMsg, true))
		{
			return false;
		}
		if (getC_BankStatement_ID() > 0)
		{
			MBankStatement stmt = (MBankStatement) getC_BankStatement();
			StringBuilder bCriteria = new StringBuilder("SELECT ")
			.append(" ARRAY_TO_STRING(ARRAY_AGG(")
			.append(MUNSChargeDetail.COLUMNNAME_C_BankStatementLine_ID)
			.append("),',')").append(" FROM ").append(
					MUNSChargeDetail.Table_Name)
			.append(" WHERE ").append(Table_Name).append("_ID = ?");
			String sql = bCriteria.toString();
			String values = DB.getSQLValueString(get_TrxName(), sql, get_ID());
			if (!Util.isEmpty(values, true))
			{
				bCriteria = new StringBuilder(MUNSChargeDetail.
						COLUMNNAME_C_BankStatementLine_ID).append(" IN (")
						.append(values).append(")");
				values = bCriteria.toString();
			}
			boolean ok = stmt.deleteLines(values);
			if (!ok)
			{
				m_processMsg = "Couldn't delete statement line.";
				return false;
			}
		}
		getLines(true);
		for (int i=0; i<m_lines.length; i++)
		{
			m_lines[i].addDescription("<>Voided confirm Amount : " + 
					m_lines[i].getAmountConfirmed());
			m_lines[i].setAmountConfirmed(Env.ZERO);
			m_lines[i].setC_BankStatementLine_ID(-1);
			m_lines[i].saveEx();
		}
		
		addDescription(Msg.getMsg(getCtx(), "Voided"));
		setDocStatus(DOCSTATUS_Voided); 
		saveEx();
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		
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
		log.info(toString());
		// Before reActivate
		m_processMsg = "Reactivate is not allowed";
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo()).append(":").append(" (#");
		if (getName() != null && getName().length() > 0)
			sb.append(" - ").append(getName());
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		return getSummary();
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
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		if(getUNS_Charge_RS_ID() > 0)
			return ((MUNSChargeRS)getUNS_Charge_RS()).getC_Currency_ID();
		else if(getUNS_Shipping_ID() > 0)
			return DB.getSQLValue(get_TrxName()
					, "SELECT C_Currency_ID FROM UNS_Shipping WHERE UNS_Shipping_ID = ?", getUNS_Shipping_ID());
		else
			return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return Env.ZERO;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public static MUNSChargeConfirmation get(PO po)
	{
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(Table_Name)
				.append(" WHERE ").append(po.get_TableName()).append("_ID = ? AND ")
				.append(COLUMNNAME_IsActive).append("= ?");
		String sql = sb.toString();
		
		MUNSChargeConfirmation record = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, po.get_TrxName());
			st.setInt(1, po.get_ID());
			st.setString(2, "Y");
			rs = st.executeQuery();
			while (rs.next())
			{
				record = new MUNSChargeConfirmation(po.getCtx(), rs, po.get_TrxName());
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
		
		return record;
	}
	
	/**
	 * Create Bank Statement Line and linking statement line to lines.
	 * @return {@link String} Error Message
	 */
	private String doCreateStatementLines()
	{
		I_C_BankStatement stmt = getC_BankStatement();
		MUNSChargeDetail[] details = getLines(false);
		String msg = "";
		for(int i=0; i<details.length; i++)
		{
			MUNSChargeDetail detail = details[i];
			if(detail.isVoucher())
				continue;
			else if(detail.getAmtSource().signum() == 0)
				continue;
			
			msg = msg.concat(detail.doCreateStatementLine((MBankStatement) stmt, getDateConfirm()));
		}
		return msg.equals("") ? null : msg;
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
	 * Create Lines
	 * @param po
	 */
	public void createLinesFrom(PO po)
	{
		m_lines = MUNSChargeDetail.gets(po);
		for(int i=0; i<m_lines.length; i++)
		{
			if (m_lines[i].isVoucher())	{	continue;}
			m_lines[i].onConfirmationCreated(get_ID());
		}
	}
	
	/**
	 * Do complete reference document.
	 * @return {@link String} error message
	 */
	protected String doCompleteReference()
	{
		if (getUNS_Charge_RS_ID() > 0)
			return doCompleteChargeRS();
		else if (getUNS_Shipping_ID() > 0)
			return doCompleteShipping();
		else
			return Msg.getMsg("", "Confirmation hasn't have reference");
	}
	
	/**
	 * Do complete Charge Request / Settlement
	 * @return {@link String} error message
	 */
	protected String doCompleteChargeRS()
	{
		MUNSChargeRS po = new MUNSChargeRS(
				getCtx(), getUNS_Charge_RS_ID(), get_TrxName());
		po.setDateConfirm(getDateConfirm());
		return doActionReference(po, DocAction.ACTION_Complete);
	}
	
	/**
	 * Do complete shipping reference
	 * @return {@link String} error message
	 */
	protected String doCompleteShipping()
	{
		MTable table = MTable.get(getCtx(), "UNS_Shipping");
		table.setExtensionHandler("UNSOrderModelFactory");
		PO po = table.getPO(getUNS_Shipping_ID(), get_TrxName());
		String docStatus = po.get_ValueAsString("DocStatus");
		if(docStatus.equals("CO") || docStatus.equals("CL"))
			return "";
		return doActionReference(po, DocAction.ACTION_Complete);
	}
	
	/**
	 * Do complete reference document
	 * @param po
	 * @return {@link String} error message
	 */
	private String doActionReference(PO po, String action)
	{
		try
		{
			Env.setContext(Env.getCtx(), CONFIRMATION_EVENT, "Y");
			ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(po, action);
			if (pi.isError())
			{
				return pi.getSummary();
			}
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
		finally
		{
			Env.setContext(Env.getCtx(), CONFIRMATION_EVENT, "N");
		}
		return null;
	}
	
	/**
	 * 
	 */
	protected void updateLines()
	{
		MUNSChargeDetail[] lines = getLines(true);
		for(int i= 0; i<lines.length; i++)
		{
			lines[i].setUNS_Charge_Confirmation_ID(-1);
			lines[i].setAmountConfirmed(Env.ZERO);
			lines[i].saveEx();
		}
	}
	
	@Override
	protected boolean beforeDelete()
	{
		getLines(true);
		
		for (int i=0; i<m_lines.length; i++)
		{
			m_lines[i].setUNS_Charge_Confirmation_ID(-1);
			m_lines[i].setAmountConfirmed(Env.ZERO);
			m_lines[i].saveEx();
		}
		
		return super.beforeDelete();
	}
	
	protected String doVoidReference ()
	{
		if (getUNS_Charge_RS_ID() > 0)
			return doVoidChargeRS ();
		else if (getUNS_Shipping_ID() > 0)
			return doVoidShipping ();
		else
			return Msg.getMsg("", "Confirmation hasn't have reference");
	}
	
	protected String doVoidChargeRS ()
	{
		MUNSChargeRS po = new MUNSChargeRS(
				getCtx(), getUNS_Charge_RS_ID(), get_TrxName());
		po.setDateConfirm(getDateConfirm());
		return doActionReference(po, DocAction.ACTION_Void);
	}
	
	protected String doVoidShipping ()
	{
		MTable table = MTable.get(getCtx(), "UNS_Shipping");
		table.setExtensionHandler("UNSOrderModelFactory");
		PO po = table.getPO(getUNS_Shipping_ID(), get_TrxName());
		return doActionReference(po, DocAction.ACTION_Void);
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		if (docStatus.equals(DocAction.STATUS_Drafted))
		{
			options[index++] = DocAction.ACTION_Prepare;
		}
		else if (docStatus.equals(DOCSTATUS_Completed))
		{
			options[index++] = DocAction.ACTION_Void;
		}
		return index;
	}
	
	public void addDescription(String description) 
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription

	@Override
	public String beforeSaveTabRecord(int parentRecord_ID) {

		return null;
	}
	
	private boolean doUpdateHeader ()
	{
		int group_id = get_ValueAsInt("UNS_ChargeConfirmationGroup_ID");
		boolean ok = doUpdateHeader(group_id);
		if (!ok)
			return ok;
		group_id = get_ValueOldAsInt("UNS_ChargeConfirmationGroup_ID");
		ok = doUpdateHeader(group_id);
		
		return ok;
	}
	
	private boolean doUpdateHeader (int parent_ID)
	{
		String sql = "UPDATE UNS_ChargeConfirmationGroup SET GrandTotal = "
				+ " (SELECT COALESCE(SUM (GrandTotal), 0) FROM "
				+ " UNS_Charge_Confirmation WHERE "
				+ " UNS_ChargeConfirmationGroup_ID =  "
				+ " UNS_ChargeConfirmationGroup.UNS_ChargeConfirmationGroup_ID "
				+ " ) WHERE UNS_ChargeConfirmationGroup_ID = ? ";
		int success = DB.executeUpdateEx(
				sql, new Object[] {parent_ID}, 
				get_TrxName());
		
		return success != -1;
	}
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!doUpdateHeader())
		{
			ErrorMsg.setErrorMsg(getCtx(), "FailedWhenUpdateHeader", "");
			return false;
		}
		
		return super.afterSave(newRecord, success);
	}
	
	@Override
	protected boolean afterDelete (boolean success)
	{
		if (!success)
			return false;
		if (!doUpdateHeader())
		{
			ErrorMsg.setErrorMsg(getCtx(), "FailedWhenUpdateHeader", "");
			return false;
		}
		return super.afterDelete(success);
	}

	@Override
	public String beforeRemoveSelection() {
		// TODO Auto-generated method stub
		return null;
	}
}
