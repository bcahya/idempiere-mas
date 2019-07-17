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
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSBonusClaimConfirm extends X_UNS_BonusClaimConfirm implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSBonusClaimLineConfirm[] m_lines = null;

	/**
	 * @param ctx
	 * @param UNS_BonusClaimConfirm_ID
	 * @param trxName
	 */
	public MUNSBonusClaimConfirm(Properties ctx, int UNS_BonusClaimConfirm_ID,
			String trxName) {
		super(ctx, UNS_BonusClaimConfirm_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBonusClaimConfirm(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	private String m_processMsg = null;

	@Override
	public boolean processIt(String action) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		if (log.isLoggable(Level.INFO)) log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	private boolean m_justPrepared = false;
	
	@Override
	public String prepareIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		//	Lines
		MUNSBonusClaimLineConfirm[] lines = getLines(true);
		if (lines == null || lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		
		for(MUNSBonusClaimLineConfirm line : lines)
		{
			if(line.getDecisionConfirm() == null)
				throw new AdempiereUserError("Decision confirm in Bonus Claim Confirm Line is mandatory!");
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		if (log.isLoggable(Level.INFO)) log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		if (log.isLoggable(Level.INFO)) log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		if (DOCACTION_Prepare.equals(getDocAction()))
		{
			setProcessed(false);
			return DocAction.STATUS_InProgress;
		}
		
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
		
		
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		
		MUNSBonusClaimLineConfirm[] lines = getLines();
		for(MUNSBonusClaimLineConfirm confirm : lines)
		{
			MUNSBonusClaimLine claimLine = new MUNSBonusClaimLine(
					getCtx(), confirm.getUNS_BonusClaimLine_ID(), get_TrxName());
			claimLine.setDecisionConfirm(confirm.getDecisionConfirm());
			claimLine.saveEx();
		}
		
		if (log.isLoggable(Level.INFO)) log.info(toString());
		StringBuilder info = new StringBuilder();
			

		setProcessed(true);	
		m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_BonusClaim_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSBonusClaimConfirm[] get(Properties ctx, int UNS_BonusClaim_ID, String trxName)
	{
		List<MUNSBonusClaimConfirm> list = Query.get(
				ctx, UNSOrderModelFactory.EXTENSION_ID, Table_Name, COLUMNNAME_UNS_BonusClaim_ID + "=?"
				,trxName).setParameters(UNS_BonusClaim_ID).list();

		MUNSBonusClaimConfirm[] confirms = new MUNSBonusClaimConfirm[list.size()];
		list.toArray(confirms);
		return confirms;
	}
	
	public MUNSBonusClaimConfirm(MUNSBonusClaim claim)
	{
		super(claim.getCtx(), 0, claim.get_TrxName());
		setClientOrg(claim);
		copyValues(claim, this);
		setUNS_BonusClaim_ID(claim.get_ID());
	}
	
	public void createLinesConfirm()
	{
		MUNSBonusClaim bonusClaim = new MUNSBonusClaim(getCtx(), getUNS_BonusClaim_ID(), get_TrxName());
		MUNSBonusClaimLine[] claimLines = bonusClaim.getLines();
		for(MUNSBonusClaimLine claimLine : claimLines)
		{
			MUNSBonusClaimLineConfirm lineConfirm = new MUNSBonusClaimLineConfirm(this);
			copyValues(claimLine, lineConfirm);
			lineConfirm.setUNS_BonusClaimConfirm_ID(get_ID());
			lineConfirm.setUNS_BonusClaimLine_ID(claimLine.getUNS_BonusClaimLine_ID());
			lineConfirm.saveEx();
		}
	}
	
	public MUNSBonusClaimLineConfirm[] getLines()
	{
		return getLines(false);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSBonusClaimLineConfirm[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSBonusClaimLineConfirm> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSBonusClaimLineConfirm.Table_Name
				, MUNSBonusClaimLineConfirm.COLUMNNAME_UNS_BonusClaimConfirm_ID + "=?", get_TrxName())
				.setParameters(getUNS_BonusClaimConfirm_ID()).list();
		
		m_lines = new MUNSBonusClaimLineConfirm[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
}
