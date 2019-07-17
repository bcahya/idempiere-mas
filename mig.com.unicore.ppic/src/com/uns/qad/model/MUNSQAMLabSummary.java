package com.uns.qad.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

public class MUNSQAMLabSummary extends X_UNS_QAMLab_Summary 
	implements DocOptions, DocAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8641256736571178470L;
	private MUNSQAMLabResult[] m_lines;
	private String m_processMsg;
	private boolean m_justPrepared;

	public MUNSQAMLabSummary(Properties ctx, int UNS_QAMLab_Summary_ID,
			String trxName) {
		super(ctx, UNS_QAMLab_Summary_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSQAMLabSummary(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public boolean updateMove(int move){
		setM_Movement_ID(move);
		if (!save())
			return false;
		return true;
	}
	
	/**
	* @param requery
	* @return MUNSQAMLabResultLine[]
	*/
	protected MUNSQAMLabResult[] getLines(boolean requery){
		if (m_lines != null	&& !requery){
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSQAMLabResult> mList = new Query(getCtx(), MUNSQAMLabResult.Table_Name
			, MUNSQAMLabResult.COLUMNNAME_UNS_QAMLab_Summary_ID + " =?", get_TrxName())
			.setParameters(get_ID())
			.setOrderBy(MUNSQAMLabResult.COLUMNNAME_UNS_QAMLab_Result_ID).list();
			
		m_lines = new MUNSQAMLabResult[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
		
	/**
	 * 
	 * @return MUNSQAMLabResultLine[]
	 */
	public MUNSQAMLabResult[] getLines(){
		return getLines(false);
	}
	
	public int getProductionOrgID() {
		int prod_id;
		for (MUNSQAMLabResult r : getLines()){
			prod_id = r.getProductionDeptID();
			if (prod_id!=0)
				return prod_id;
		}
		
		throw new AdempiereException("Can't fount production.");
	}
	
	public static MUNSQAStatusPRCLine[] getPRCLine(Properties ctx, String trxName, int summary_id) {
		ArrayList<MUNSQAStatusPRCLine> list = new ArrayList<MUNSQAStatusPRCLine>();

		String sql = "SELECT UNS_QAStatus_PRCLine_ID FROM UNS_QAMLab_Result "
				+ "WHERE UNS_QAMLab_Summary_ID = ? GROUP BY UNS_QAStatus_PRCLine_ID";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, summary_id);
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MUNSQAStatusPRCLine(ctx, rs.getInt(1), trxName));
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (SQLException ex) {
			throw new AdempiereException("Unable to load prc lines", ex);
		} finally {
			DB.close(rs, pstmt);
		}

		MUNSQAStatusPRCLine[] retValue = new MUNSQAStatusPRCLine[list.size()];
		list.toArray(retValue);
		return retValue;
	}
	

	/**
	 * 
	 */
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {

		// If status = Drafted, add "Prepare" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
				|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
			options[index++] = DocumentEngine.ACTION_Prepare;
		}
		
		// If status = Completed, add "Reactive" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
			options[index++] = DocumentEngine.ACTION_Close;
			options[index++] = DocumentEngine.ACTION_ReActivate;
		}

		return index;
	}

	@Override
	public boolean processIt(String processAction) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessed(false);
		return true;
	}
	
	@Override
	public boolean invalidateIt() {
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		log.info(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		/** not use period
		if (!MPeriod.isOpen(getCtx(), getDateDoc(),
				com.uns.model.I_C_DocType.DOCBASETYPE_QAD,
				getAD_Org_ID())) {
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		} */
					
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		log.info(toString());
		setIsApproved(false);
		setProcessed(false);
		return true;
	}

	@Override
	public String completeIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());
		
		//Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {

		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
				
		//Input code to void here
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		setProcessed(true);
		setDocAction(DOCACTION_None);
		
		return true;
	}

	@Override
	public boolean closeIt() {
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		
		//input code to close here
		
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;		
		
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		//not implement yet
		
		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		return voidIt();
	}

	@Override
	public boolean reverseAccrualIt() {
		log.info(toString());
		// Before reverseAccurual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		//not implement yet
		
		// After reverseAccurual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		return voidIt();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		log.info(toString());
		// Before reverseAccurual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		//not implement yet
		
		// After reverseAccurual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		return reActivateIt();
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		return "Summary Result : " +getDocumentNo();
	}

	@Override
	public File createPDF() {
		// not used yet
		return null;
	}

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		return getCreatedBy();
	}

	@Override
	public int getC_Currency_ID() {
		// not use currency
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// don't have approval amount
		return null;
	}

	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return getDocumentNo();
	}
}
