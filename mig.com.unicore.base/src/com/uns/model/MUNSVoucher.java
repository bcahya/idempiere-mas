/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Env;

/**
 * @author root
 *
 */
public class MUNSVoucher extends X_UNS_Voucher implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2186521724759012019L;
	private MUNSVoucherCode[] m_codes = null;
	private MUNSVoucherBP[] m_partners = null;
	private MUNSVoucherFuel[] m_fuels = null;
	private MUNSVoucherArmada[] m_armadas = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	/**
	 * @param ctx
	 * @param UNS_Voucher_ID
	 * @param trxName
	 */
	public MUNSVoucher(Properties ctx, int UNS_Voucher_ID, String trxName) {
		super(ctx, UNS_Voucher_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSVoucher(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		log.info(toString());
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		MUNSVoucherCode[] childs1 = getVoucherCodes(true);
		if(null == childs1 || childs1.length == 0)
		{
			m_processMsg = "No Lines Voucher Code";
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
		setIsApproved(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());
		
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
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
		log.info(toString());
		// Before Void
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
		
		setDocStatus(DOCSTATUS_Voided); // need to set & save docstatus to be able to check it in MInOutConfirm.voidIt()
		saveEx();

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
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
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
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
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		return Env.ZERO;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSVoucherBP[] getVoucherPartners(boolean requery)
	{
		if(null != m_partners && !requery)
		{
			set_TrxName(m_partners, get_TrxName());
			return m_partners;
		}
		
		String whereClause = Table_Name.concat("_ID = ?");
		List<MUNSVoucherBP> list = new Query(
				getCtx(), MUNSVoucherBP.Table_Name, whereClause, get_TrxName())
				.setParameters(get_ID()).list();
		
		m_partners = new MUNSVoucherBP[list.size()];
		list.toArray(m_partners);
		
		return m_partners;
	}
	
	/**
	 * Get partner requery = false;
	 * @return
	 */
	public MUNSVoucherBP[] getVoucherPartners()
	{
		return getVoucherPartners(false);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSVoucherCode[] getVoucherCodes(boolean requery)
	{
		if(null != m_codes && !requery)
		{
			set_TrxName(m_codes, get_TrxName());
			return m_codes;
		}
		
		String whereClause = Table_Name.concat("_ID = ?");
		List<MUNSVoucherCode> list = new Query(
				getCtx(), MUNSVoucherCode.Table_Name, whereClause, get_TrxName())
				.setParameters(get_ID())
				.setOrderBy(MUNSVoucherCode.COLUMNNAME_Name).list();
		
		m_codes = new MUNSVoucherCode[list.size()];
		list.toArray(m_codes);
		
		return m_codes;
	}
	
	/**
	 * 
	 * @return {@link MUNSVoucherCode}[]
	 */
	public MUNSVoucherCode[] getVoucherCodes()
	{
		return getVoucherCodes(false);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSVoucherArmada[] getVoucherArmadas(boolean requery)
	{
		if(null != m_armadas && !requery)
		{
			set_TrxName(m_armadas, get_TrxName());
			return m_armadas;
		}
		
		List<MUNSVoucherArmada> list = new Query(
				getCtx(), MUNSVoucherArmada.Table_Name
				, MUNSVoucherArmada.COLUMNNAME_UNS_Voucher_ID.concat("= ?"), get_TrxName())
				.setParameters(get_ID()).list();
		m_armadas = new MUNSVoucherArmada[list.size()];
		list.toArray(m_armadas);
		
		return m_armadas;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSVoucherArmada[] getVoucherArmadas()
	{
		return getVoucherArmadas(false);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSVoucherFuel[] getVoucherFuels(boolean requery)
	{
		if(null != m_fuels && !requery)
		{
			set_TrxName(m_fuels, get_TrxName());
			return m_fuels;
		}
		
		List<MUNSFuel> list = new Query(
				getCtx(), MUNSVoucherFuel.Table_Name
				, MUNSVoucherFuel.COLUMNNAME_UNS_Voucher_ID.concat("= ?"), get_TrxName())
				.setParameters(get_ID()).list();
		
		m_fuels = new MUNSVoucherFuel[list.size()];
		list.toArray(m_fuels);
		
		return m_fuels;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSVoucherFuel[] getVOucherFuels()
	{
		return getVoucherFuels(false);
	}
	
	
	/**
	 * Get unused voucher
	 * @return {@link MUNSVoucherCode}
	 */
	public MUNSVoucherCode getUnusedVoucher()
	{
		getVoucherCodes(false);
		MUNSVoucherCode code = null;
		
		for(int i=0; i<m_codes.length; i++)
		{
			if(m_codes[i].isUsed())
				continue;
			code = m_codes[i];
			break;
		}
		
		return code;
	}
	
	/**
	 * Get voucher code model.
	 * @param code
	 * @param onlyNotUse
	 * @return
	 */
	public MUNSVoucherCode getVoucherCode(String code, boolean onlyNotUse)
	{
		return MUNSVoucherCode.get(get_TrxName(), code, get_ID(), onlyNotUse);
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSVoucherFuel getDefaultFuel()
	{
		getVoucherFuels(false);
		MUNSVoucherFuel fuel = null;
		
		for(int i=0; i<m_fuels.length; i++)
		{
			if(!m_fuels[i].isDefault())
				continue;
			
			fuel = m_fuels[i];
		}
		
		return fuel;
	}
}
