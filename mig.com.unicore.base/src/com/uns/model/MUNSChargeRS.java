/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.PeriodClosedException;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

/**
 * @author root
 *
 */
public class MUNSChargeRS extends X_UNS_Charge_RS implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7208713241447247054L;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSChargeDetail[] m_lines = null;
	private MUNSChargeRS m_reference = null;
	public static String	REVERSE_INDICATOR = "^_^";
	private boolean m_reversal = false;

	/**
	 * @param ctx
	 * @param UNS_Charge_RS_ID
	 * @param trxName
	 */
	public MUNSChargeRS(Properties ctx, int UNS_Charge_RS_ID, String trxName) {
		super(ctx, UNS_Charge_RS_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSChargeRS(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(!isRequest() && getReference_ID() > 0)
		{
			MUNSChargeRS referer = (MUNSChargeRS) getReference();
			if(referer.getC_BPartner_ID() != getC_BPartner_ID())
				throw new AdempiereUserError(Msg.getMsg(getCtx(), "Not Synchron Business Partner", true));
			else if(is_ValueChanged(COLUMNNAME_Reference_ID))
			{
				if(!deleteLines())
					throw new AdempiereException(Msg.getMsg(getCtx(), "Failed When Deleting Lines", true));
			}
		}
		
		//tambahan
		if(Util.isEmpty(getDescription(),true)) {
			String name=getName();
			setDescription(name);
		}
		
		
		boolean isMustSame = MSysConfig.getBooleanValue(MSysConfig.SET_ORG_WITH_ORG_EMPLOYEE_ON_SETTLEMENT, false);
		if(isMustSame && getUNS_Shipping_ID() <= 0)
		{
			String sql = "SELECT emp.AD_Org_ID FROM UNS_Employee emp"
					+ " INNER JOIN AD_User us ON us.UNS_Employee_ID = emp.UNS_Employee_ID"
					+ " WHERE us.C_BPartner_ID = ? AND us.IsActive = 'Y' AND emp.IsActive = 'Y'";
			int org = DB.getSQLValue(get_TrxName(), sql, getC_BPartner_ID());
			if(org > 0 && getAD_Org_ID() != org)
				setAD_Org_ID(org);
		}
		
		if(getUNS_Shipping_ID() > 0)
		{
			setAD_Org_ID(DB.getSQLValue(get_TrxName(), "SELECT AD_Org_ID FROM UNS_Shipping"
					+ " WHERE UNS_Shipping_ID = ?", getUNS_Shipping_ID()));
		}
			
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean beforeDelete()
	{
		if(!deleteLines())
			throw new AdempiereException(Msg.getMsg(getCtx(), "Failed When Deleting Lines", true));
		return super.beforeDelete();
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
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		if (log.isLoggable(Level.INFO))
			log.info(toString());
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
		
		getLines(true);
		
		if(m_lines == null || m_lines.length == 0)
		{
			m_processMsg = Msg.getMsg("", "No Lines");
			return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = isRequest() ? onPreparingRequest() : onPreparingSettlement();
		if(!Util.isEmpty(m_processMsg, true))
			return DocAction.STATUS_Invalid;
		
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
		
		if (!isReversal())
		{
			boolean isTrigerFromConfirmation = Env.getContext(Env.getCtx(), MUNSChargeConfirmation.CONFIRMATION_EVENT) == "Y";
			MUNSChargeConfirmation confirmation = getCreateConfirm();
			if(!confirmation.getDocStatus().equals(DOCSTATUS_Completed)
					&& !confirmation.getDocStatus().equals(DOCSTATUS_Closed) && ! isTrigerFromConfirmation)
			{
				m_processMsg = Msg.getMsg("", "Please open  Charge Confirmation " 
						.concat(confirmation.getDocumentNo()).concat(confirmation.getName()));
				return DocAction.STATUS_InProgress;
			}
			
			m_processMsg = isRequest() ? onCompletingRequest() : onCompletingSettlement();
			if(!Util.isEmpty(m_processMsg, true))
				return DocAction.STATUS_Invalid;
			
		}
		
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
		
		if (isRequest())
		{
			try
			{
				MUNSChargeRS settlement = getSettlement();
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
		
		String sql = "UPDATE UNS_ShippingCrewIncentive SET CreateSettlement = 'N'"
				+ ", UNS_Charge_RS_ID = null WHERE UNS_Charge_RS_ID=?";
		DB.executeUpdate(sql, get_ID(), get_TrxName());
		
		if (isRequest() || getDocStatus().equals(DOCSTATUS_Drafted)
				|| getDocStatus().equals(DOCSTATUS_InProgress)
				|| getDocStatus().equals(DOCSTATUS_Invalid)
				|| getDocStatus().equals(DOCSTATUS_NotApproved)
				|| getDocStatus().equals(DOCSTATUS_Approved))
		{
			MUNSChargeConfirmation confirm = MUNSChargeConfirmation.get(this);
			if (null != confirm)
			{
				String fromConfirm = Env.getContext(Env.getCtx(), 
						MUNSChargeConfirmation.CONFIRMATION_EVENT);
				
				if (null == fromConfirm)
					fromConfirm = "N";
				
				boolean isFromConfirm = fromConfirm.equals("Y");
				
				if (!confirm.isProcessed() && !isFromConfirm)
				{
					confirm.deleteEx(true);
				}
				else if (!isFromConfirm)
				{
					m_processMsg = "Please void confirmation first.";
					return false;
				}
			}
			
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
			
		}
		else
		{
			boolean accrual = false;
			try 
			{
				MPeriod.testPeriodOpen(getCtx(), getDateTrx(), 
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
		
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		addDescription(Msg.getMsg(getCtx(), "Voided"));
		setDocStatus(DOCSTATUS_Voided);
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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (null != m_processMsg)
			return false;
		
		if (isRequest())
			return voidIt();
		
		String sql = "UPDATE UNS_ShippingCrewIncentive SET CreateSettlement = 'N'"
				+ ", UNS_Charge_RS_ID = null WHERE UNS_Charge_RS_ID=?";
		DB.executeUpdate(sql, get_ID(), get_TrxName());
		
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
		
		MUNSChargeRS reversal = reverse(false);
		if (null == reversal)
			return false;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (null != m_processMsg)
			return false;
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (null != m_processMsg)
			return false;
		
		if (isRequest())
			return voidIt();
		
		String sql = "UPDATE UNS_ShippingCrewIncentive SET CreateSettlement = 'N'"
				+ ", UNS_Charge_RS_ID = null WHERE UNS_Charge_RS_ID=?";
		DB.executeUpdate(sql, get_ID(), get_TrxName());
		
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
		
		MUNSChargeRS reversal = reverse(true);
		if (null == reversal)
			return false;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, 
				ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (null != m_processMsg)
			return false;
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		try
		{
			if (!isRequest())
				return false;
			
			MUNSChargeConfirmation confirm = MUNSChargeConfirmation.get(this);
			if (null != confirm)
			{
				String status = confirm.getDocStatus();
				boolean isComplete = !status.equals(DOCSTATUS_Drafted);
				if (isComplete)
				{
					m_processMsg = "Completed confirmation";
					return false;
				}
				confirm.deleteEx(true);
			}
		}
		catch (Exception e)
		{
			m_processMsg = e.getMessage();
			return false;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		setDocStatus(DOCSTATUS_InProgress);
		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
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
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		return getGrandTotal();
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
		confirmation.setName("Confirmation of "
				.concat(getDocumentNo()).concat("-").concat(getName()) );
		confirmation.setConfirmationType(MUNSChargeConfirmation.CONFIRMATIONTYPE_ChargeRequestConfirmation);
		confirmation.setRequestDate(getDateTrx());
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
		
		m_lines = MUNSChargeDetail.gets(this);
		return m_lines;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSChargeRS getReference()
	{
		if(null != m_reference)
		{
			m_reference.set_TrxName(get_TrxName());
			return m_reference;
		}
		m_reference = new MUNSChargeRS(getCtx(), getReference_ID(), get_TrxName());
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
		//Do nothing
		return null;
	}

	/**
	 * Validate on preparing Request document
	 * @return {@link String} error message
	 */
	private String onPreparingRequest()
	{
		getLines(true);		
		return null;
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
	private String onCompletingRequest()
	{
		
		return null;
	}
	
	public boolean isRequest()
	{
		return getC_DocType().getDocBaseType().equals(MDocType.DOCBASETYPE_ChargeRequest);
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
				"DELETE FROM UNS_Charge_Detail WHERE UNS_Charge_RS_ID = ? AND Reference_ID IS NOT NULL"
				, new Object[] {get_ID()}, false, get_TrxName());
		return result != -1;
	}
	
	/**
	 * 
	 * @param description
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
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) 
	{
		if (docStatus.equals(DOCSTATUS_Drafted))
		{
			options[index++] = DocAction.ACTION_Prepare;
		}
		else if (docStatus.equals(DOCSTATUS_Completed))
		{
			options[index++] = DocAction.ACTION_ReActivate;
			options[index++] = DocAction.ACTION_Void;
			
			if (!isRequest())
			{
				options[index++] = DocAction.ACTION_Reverse_Accrual;
				options[index++] = DocAction.ACTION_Reverse_Correct;
			}
		}
		
		return index;
	}
	
	private MUNSChargeRS reverse (boolean accrual)
	{
		Timestamp reversalDate = accrual ? Env.getContextAsDate(getCtx(), "#Date") : getDateTrx();
		if (reversalDate == null) {
			reversalDate = new Timestamp(System.currentTimeMillis());
		}
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (!MPeriod.isOpen(getCtx(), reversalDate, dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return null;
		}
		
		MUNSChargeRS reversal = new MUNSChargeRS(getCtx(), 0, get_TrxName());
		PO.copyValues(this, reversal, getAD_Client_ID(), getAD_Org_ID());
		reversal.setReference_ID(getReference_ID());
		reversal.setDocStatus(DOCSTATUS_Drafted);
		reversal.setDocAction(DocAction.ACTION_Complete);
		reversal.setProcessed(false);
		reversal.setIsApproved(false);
		reversal.setPosted(false);
		reversal.setDateTrx(reversalDate);
		reversal.setDateConfirm(reversalDate);
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
				PO.copyValues(cd, reversalLine);
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
	
	public boolean isReversal ()
	{
		return m_reversal;
	}
	
	public void setReversal (boolean reversal)
	{
		m_reversal = reversal;
	}
	
	public MUNSChargeRS getSettlement ()
	{
		if (!isRequest())
		{
			return null;
		}
		
		String sql = "SELECT * FROM UNS_Charge_RS WHERE Reference_ID = ?"
				+ " AND DocStatus NOT IN ('RE','VO')";
		MUNSChargeRS stl = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			st.setInt(1, get_ID());
			rs = st.executeQuery();
			if (rs.next())
			{
				stl = new MUNSChargeRS(getCtx(), rs, get_TrxName());
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
	
	/**
	 * 	Document Status is Complete or Closed
	 *	@return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
			|| DOCSTATUS_InProgress.equals(ds)
			|| DOCSTATUS_Closed.equals(ds)
			|| DOCSTATUS_Reversed.equals(ds);
	}	//	isComplete
}