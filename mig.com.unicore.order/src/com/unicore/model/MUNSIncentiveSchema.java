/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

import com.uns.base.model.Query;
import com.uns.util.MessageBox;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author menjangan, BurhaniAdam
 *
 */
public class MUNSIncentiveSchema extends X_UNS_IncentiveSchema implements
		DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param ctx
	 * @param UNS_IncentiveSchema_ID
	 * @param trxName
	 */
	public MUNSIncentiveSchema(Properties ctx, int UNS_IncentiveSchema_ID,
			String trxName) {
		super(ctx, UNS_IncentiveSchema_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSIncentiveSchema(Properties ctx, ResultSet rs, String trxName) {
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

	protected boolean beforeSave(boolean newRecord)
	{
		if(newRecord || is_ValueChanged(COLUMNNAME_ValidFrom) || is_ValueChanged(COLUMNNAME_C_Year_ID))
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(getValidFrom());
			String year = String.valueOf(cal.get(Calendar.YEAR));
			
			if(!year.equals(getC_Year().getFiscalYear()))
			{
				log.saveError("Error", "Valid from date and year not match");
				return false;
			}
		}
		if(!newRecord && is_ValueChanged(COLUMNNAME_SchemaType))
		{
			MUNSIncentive[] lines = getLines();
			if(lines.length > 0)
			{
				int retVal = MessageBox.showMsg(this, getCtx(), 
						"Changes to Schema Type will be deleted lines. Continue?",
						"Delete lines confirmation.", MessageBox.YESNO, MessageBox.ICONQUESTION);
				
				if(retVal == MessageBox.RETURN_YES || MessageBox.RETURN_OK == retVal)
				{
					for(int i = 0; i < lines.length; i++)
					{
						if(!lines[i].delete(false))
						{
							log.saveError("Error", "Failed when trying delete lines.");
							return false;
						}
					}
				}
				else
				{
					log.saveError("Error", "Action has cancelled");
					return false;
				}
			}
		}
		return true;
	}
	
	
	protected boolean beforeDelete()
	{
		MUNSIncentive[] lines = getLines();
		
		for(int i = 0; i < lines.length; i++)
		{
			if(!lines[i].delete(false))
			{
				log.saveError("Error", "Failed when trying delete lines.");
				return false;
			}
		}
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		log.info(toString());
		setProcessing(false);
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
		
		MUNSIncentive[] lines = getLines();
		if(lines.length == 0)
		{
			m_processMsg = "No lines !!!";
			return DocAction.STATUS_Invalid;
		}
		
		for(MUNSIncentive line : lines)
		{
			line.setProcessed(true);
			line.saveEx();
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
		
//		Re-Check
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
		approveIt();
		//m_processMsg = info.toString();
	
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		String sql = "UPDATE UNS_Incentive SET Processed = 'N' WHERE UNS_IncentiveSchema_ID = ?";
		DB.executeUpdate(sql, get_ID(), get_TrxName());
		
		sql = "UPDATE UNS_IncentiveSchemaConfig ic SET Processed = 'N' WHERE EXISTS"
				+ " (SELECT 1 FROM UNS_Incentive i WHERE ic.UNS_Incentive_ID = i.UNS_Incentive_ID"
				+ " AND i.UNS_IncentiveSchema_ID = ?)";
		DB.executeUpdate(sql, get_ID(), get_TrxName());
		
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
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}
	/**================================================================================**/
	
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSIncentive[] m_lines = null;
	
	/**================================================================================**/
	
	public MUNSIncentive[] getLines()
	{
		return getLines(false, null);
	}
	
	public MUNSIncentive[] getLines(boolean requery, String orderByColumn)
	{
		if(null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		if(null == orderByColumn)
			orderByColumn = MUNSIncentive.COLUMNNAME_UNS_Incentive_ID;
		
		String whereClause = MUNSIncentive.COLUMNNAME_UNS_IncentiveSchema_ID + "=?";
		
		List<MUNSIncentive> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSIncentive.Table_Name
				, whereClause, get_TrxName()).setParameters(getUNS_IncentiveSchema_ID())
				.setOrderBy(orderByColumn).list();
		
		m_lines = new MUNSIncentive[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param date
	 * @param BPSalesRep_ID
	 * @param trxName
	 * @return
	 */
	public static List<MUNSIncentiveSchema> get(Properties ctx, int C_Year_ID, int BPSalesRep_ID, String trxName)
	{
		if(C_Year_ID <= 0)
		{
			throw new AdempiereException("Incentive Schme : Error on get Icentive Schema. " +
					"null of parameter C_Year_ID.");
		}
		
		if(BPSalesRep_ID <= 0)
		{
			throw new AdempiereException("Incentive Schme : Error on get Icentive Schema. " +
					"null of Sales Representative date.");
		}
		
		List<MUNSIncentiveSchema> schemas = new ArrayList<MUNSIncentiveSchema>();
		
		MBPartner bp = new MBPartner(ctx, BPSalesRep_ID, trxName);
		String salesType = bp.getSalesType();
		String salesLevel = bp.getSalesLevel();
		
		StringBuilder prepareSQL = new StringBuilder("SELECT * FROM ").append(Table_Name)
				.append(" WHERE ").append(COLUMNNAME_C_Year_ID).append("=").append(C_Year_ID)
				.append(" AND CASE WHEN ").append(COLUMNNAME_C_BPartner_ID).append(" > 0 THEN ")
				.append(COLUMNNAME_C_BPartner_ID).append("=").append(BPSalesRep_ID)
				.append(" ELSE 1=1 END ").append(" AND CASE WHEN ").append(COLUMNNAME_SalesType)
				.append(" IS NOT NULL THEN ").append(COLUMNNAME_SalesType)
				.append("='").append(salesType).append("' ELSE 1=1 END ")
				.append(" AND CASE WHEN ").append(COLUMNNAME_SalesLevel).append(" IS NOT NULL THEN ")
				.append(COLUMNNAME_SalesLevel).append("='").append(salesLevel).append("'")
				.append(" ELSE 1=1 END ").append(" AND ").append(COLUMNNAME_DocStatus)
				.append("='").append(DOCSTATUS_Completed).append("'");
		
		String sql = prepareSQL.toString();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = DB.prepareStatement(sql, trxName);
			rs = st.executeQuery();
			while (rs.next())
			{
				schemas.add(new MUNSIncentiveSchema(ctx, rs, trxName));
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			DB.close(rs, st);
		}
		
		return schemas;
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		if(docStatus.equals(DOCSTATUS_Completed))
		{
			options[index++] = DOCACTION_Re_Activate;
		}
		return index;
	}
}