package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

import com.uns.base.UNSDefaultModelFactory;
import com.uns.base.model.Query;

/**
 * 
 * @author Menjangan
 * @see www.untasoft.com
 *
 */
public class MUNSChargeConfirmationGroup extends X_UNS_ChargeConfirmationGroup 
implements DocAction, DocOptions
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9075537187555592187L;
	private MUNSChargeConfirmation[] m_lines = null;

	public MUNSChargeConfirmationGroup(Properties ctx,
			int UNS_ChargeConfirmationGroup_ID, String trxName) 
	{
		super(ctx, UNS_ChargeConfirmationGroup_ID, trxName);
	}

	public MUNSChargeConfirmationGroup(Properties ctx, ResultSet rs,
			String trxName) 
	{
		super(ctx, rs, trxName);
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) 
	{
		if (DOCSTATUS_Completed.equals(docStatus))
		{
			options[index++] = DocAction.ACTION_Void;
		}
		return index;
	}
	
	private boolean m_justPrepared = false;
	private String m_processMsg = null;

	@Override
	public boolean processIt(String action) throws Exception {
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		setProcessed(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		setProcessed(false);
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		doCheckLines();
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_PREPARE);
		if (null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() 
	{
		setProcessed(true);
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		setProcessed(false);
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!status.equals(DOCSTATUS_InProgress))
				return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_COMPLETE);
		if (null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = doAction(getDocAction());
		if ( null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_COMPLETE);
		if (null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_VOID);
		if (null != m_processMsg)
			return false;

		if (DOCSTATUS_Closed.equals(getDocStatus())
				|| DOCSTATUS_Reversed.equals(getDocStatus())
				|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}
	
		m_processMsg = doAction(getDocAction());
		if (null != m_processMsg)
			return false;
		
		addDescription("**Voided**");
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_VOID);
		if (null != m_processMsg)
			return false;
		
		setProcessed(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public boolean closeIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_CLOSE);
		if (null != m_processMsg)
			return false;

		m_processMsg = doAction(getDocAction());
		if (null != m_processMsg)
			return false;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_CLOSE);
		if (null != m_processMsg)
			return false;
		
		setProcessed(true);
		setDocStatus(DOCSTATUS_Closed);
		setDocAction(DOCACTION_None);
		return true;	
	}

	@Override
	public boolean reverseCorrectIt() {
		m_processMsg = "reverse is not implemented";
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		m_processMsg = "reverse is not implemented";
		return false;
	}

	@Override
	public boolean reActivateIt() {
		m_processMsg = "Disallowed re-activate";
		return false;
	}

	@Override
	public String getSummary() {
		StringBuilder sb = new StringBuilder(getDocumentNo()).append(", ")
				.append(getName()).append(", Grand Total ")
				.append(getGrandTotal()).append(", ").append(getDateConfirm());
		String summary = sb.toString();
		return summary;
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
		return getGrandTotal();
	}

	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSChargeConfirmation[] getLines (boolean requery)
	{
		if (null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSChargeConfirmation> list = Query.get(
				getCtx(), UNSDefaultModelFactory.EXTENSION_ID, 
				MUNSChargeConfirmation.Table_Name, Table_Name + "_ID = ?", 
				get_TrxName()).setParameters(get_ID()).
				setOnlyActiveRecords(true).
				setOrderBy(COLUMNNAME_DocumentNo).list();
		
		m_lines =  new MUNSChargeConfirmation[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	protected void doCheckLines ()
	{
		getLines(true);
		for (int i=0; i<m_lines.length; i++)
		{
			MUNSChargeConfirmation line = m_lines[i];
			line.setC_BankStatement_ID(getC_BankStatement_ID());
			line.setDateConfirm(getDateConfirm());
			line.saveEx();
		}
	}
	
	private String doAction (String action)
	{
		if (!action.equals("VO") && !action.equals("CO") 
				&& !action.equals("CL"))
			return "Invalid action " + action;
		
		getLines(true);
		
		for (int i=0; i<m_lines.length; i++)
		{
			MUNSChargeConfirmation line = m_lines[i];
			String docStatus = line.getDocStatus();
		
			if (docStatus.equals(action) || docStatus.equals("CL"))
				continue;
			String msg = doAction(line, action);
			if (null != msg)
				return msg;
		}
		
		return null;
	}
	
	private String doAction (MUNSChargeConfirmation confirm, String action)
	{
		try
		{
			boolean ok = confirm.processIt(action);
			if (!ok)
			{
				return confirm.getProcessMsg();
			}
			confirm.saveEx();
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
		
		return null;
	}
	
	public void addDescription(String description) 
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription
	
	public String doLoadDetails (Timestamp dateFrom, Timestamp dateTo)
	{
		String sql = " UPDATE UNS_Charge_Confirmation SET "
				+ " UNS_ChargeConfirmationGroup_ID = ? WHERE "
				+ " (RequestDate BETWEEN ? AND ? OR DateConfirm BETWEEN ? AND ?) "
				+ " AND AD_Org_ID = ? ";
		
		int n = DB.executeUpdateEx(
				sql, new Object[]{get_ID(), dateFrom, dateTo, dateFrom, dateTo,
						getAD_Org_ID()},
				get_TrxName());
		
		if (!doUpdateTotal())
			return "Faled when update Grand Total";
		
		return n + " record created";
	}
	
	public boolean doUpdateTotal ()
	{
		String sql = "UPDATE UNS_ChargeConfirmationGroup SET GrandTotal = "
				+ " (SELECT COALESCE(SUM (GrandTotal), 0) FROM "
				+ " UNS_Charge_Confirmation WHERE "
				+ " UNS_ChargeConfirmationGroup_ID =  "
				+ " UNS_ChargeConfirmationGroup.UNS_ChargeConfirmationGroup_ID "
				+ " ) WHERE UNS_ChargeConfirmationGroup_ID = ? ";
		int success = DB.executeUpdateEx(
				sql, new Object[] {get_ID()}, get_TrxName());
		
		return success != -1;
	}
	
	@Override
	protected boolean beforeDelete ()
	{
		doUnlinkChild();
		return super.beforeDelete();
	}
	
	private void doUnlinkChild ()
	{
		String sql = "UPDATE UNS_Charge_Confirmation set "
				+ " UNS_ChargeConfirmationGroup_ID = null WHERE "
				+ " UNS_ChargeConfirmationGroup_ID = ? ";
		int retVal = DB.executeUpdateEx(sql, new Object[] {get_ID()}, 
				get_TrxName());
		log.info(retVal + " record charge confirmation updated");
	}
}
