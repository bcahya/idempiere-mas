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
import java.util.List;
import java.util.Properties;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author root
 *
 */
public class MUNSCvsLimit extends X_UNS_Cvs_Limit implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4360636461725964723L;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSCvsLimitProduct[] m_lines = null;

	/**
	 * @param ctx
	 * @param UNS_Cvs_Limit_ID
	 * @param trxName
	 */
	public MUNSCvsLimit(Properties ctx, int UNS_Cvs_Limit_ID, String trxName) {
		super(ctx, UNS_Cvs_Limit_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCvsLimit(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	public boolean processIt(String action) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info(toString());
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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		MUNSCvsLimitProduct[] lines = getLines();
		if (lines == null || lines.length == 0)
		{
			m_processMsg = "@NoLines@";
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
		log.info(toString());
		if(!isApproved())
			setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		log.info(toString());
		setIsApproved(false);
		return true;
	}

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

	@Override
	public boolean reverseCorrectIt() {
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		return false;
	}

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

	@Override
	public String getSummary() {
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo()).append(":").append(" (#").append(getLines().length).append(")");
		if (getName() != null && getName().length() > 0)
			sb.append(" - ").append(getName());
		return sb.toString();
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
		return Env.ZERO;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSCvsLimitProduct[] getLines()
	{
		return getLines(false);
	}
	
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSCvsLimitProduct[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
			return m_lines;
		
		String clause = MUNSCvsLimitProduct.COLUMNNAME_UNS_Cvs_Limit_ID
				+ " = " + get_ID();
		
		return getLines(clause);
	}
	
	/**
	 * 
	 * @param whereClause
	 * @return
	 */
	public MUNSCvsLimitProduct[] getLines(String whereClause)
	{
		List<MUNSCvsLimitProduct> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID
				, MUNSCvsLimitProduct.Table_Name, whereClause, get_TrxName())
				.list();
		
		m_lines = new MUNSCvsLimitProduct[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	/**
	 * Check limit of grand total of canvas order.
	 * @param canvasType
	 * @param date
	 * @param amt
	 * @param trxName
	 * @return boolean true if is over.
	 */
	public static boolean isOverGrandTotal(
			int AD_Org_ID, String canvasType, Timestamp date, BigDecimal amt, String trxName)
	{
		StringBuilder sb = new StringBuilder("SELECT a.limitAmt FROM ").append(Table_Name)
				.append(" a WHERE (a.").append(COLUMNNAME_CanvasType).append(" = ? OR a.")
				.append(COLUMNNAME_CanvasType).append(" IS NULL) AND a.")
				.append(COLUMNNAME_ValidFrom).append(" = (SELECT MAX(b.")
				.append(COLUMNNAME_ValidFrom).append(") FROM ").append(Table_Name)
				.append(" b WHERE b.").append(COLUMNNAME_CanvasType).append(" = a.")
				.append(COLUMNNAME_CanvasType).append(" AND b.").append(COLUMNNAME_ValidFrom)
				.append(" <= ?)  AND (").append(COLUMNNAME_AD_Org_ID).append(" = ? OR ")
				.append(COLUMNNAME_AD_Org_ID).append(" = 0) ORDER BY a.")
				.append(COLUMNNAME_AD_Org_ID).append(", ").append(COLUMNNAME_CanvasType);
		
		String sql = sb.toString();
		BigDecimal limitAmt = DB.getSQLValueBD(trxName, sql, canvasType, date, AD_Org_ID);
		if(null == limitAmt)
			return false;
		
		boolean over = limitAmt.compareTo(amt) == -1;
		return over;
	}
	
	public static boolean isOverQtyOrAmt(
			int AD_Org_ID, String canvasType, Timestamp date, int M_Product_ID, BigDecimal qty
			, BigDecimal amt, String trxName)
	{
		String finalSql = "SELECT limitQty, LimitAmt FROM UNS_Cvs_LimitProduct ";
		String whereClause = "";
		String sql = "SELECT ARRAY_TO_STRING(ARRAY_AGG(UNS_Cvs_Limit_ID),',') FROM UNS_Cvs_Limit "
				+ "WHERE CanvasType = ? AND ValidFrom <= ?"
				+ " AND AD_Org_ID = ?";
		String exists = DB.getSQLValueString(trxName, sql, canvasType, date, AD_Org_ID);
		
		if(exists != null)
			whereClause += " WHERE UNS_Cvs_Limit_ID IN (" + exists + ")";
		else
			whereClause += " WHERE UNS_Cvs_Limit_ID IN (SELECT UNS_Cvs_Limit_ID FROM UNS_Cvs_Limit "
					+ " WHERE ValidFrom <= '" + date + "' AND (AD_Org_ID = ? OR AD_Org_ID = 0) "
							+ " ORDER BY ValidFrom) ";

		whereClause += " AND  ((M_Product_ID = ? OR M_Product_Category_ID = (SELECT "
				+ " M_Product_Category_ID FROM M_Product WHERE M_Product_ID = ?)) "
				+ " OR (M_Product_ID IS NULL AND M_Product_Category_ID IS NULL)) "
				+ " ORDER BY M_Product_ID, M_Product_Category_ID";
		
		finalSql += whereClause;
		
		PreparedStatement st		= null;
		ResultSet rs				= null;
		BigDecimal limitAmt			= Env.ZERO;
		BigDecimal limitQty			= Env.ZERO;
		boolean over = false;
		try
		{
			st = DB.prepareStatement(finalSql, trxName);
			st.setInt(1, M_Product_ID);
			st.setInt(2, M_Product_ID);
			if(exists == null)
				st.setInt(3, AD_Org_ID);
			rs = st.executeQuery();
			if (rs.next())
			{
				limitQty	= rs.getBigDecimal(1);
				limitAmt	= rs.getBigDecimal(2);
				over = limitQty.compareTo(qty) == -1;
				if(!over)
					over = limitAmt.compareTo(amt) == -1;
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
		
		return over;
	}

}
