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
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * @author toriq
 *
 */
public class MUNSDocChecking extends X_UNS_DocChecking implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5115554972286243920L;
	
	
	/**
	 * 
	 */
	public MUNSDocChecking(Properties ctx, int UNS_DocChecking_ID, String trxName) {
		
		super(ctx, UNS_DocChecking_ID, trxName);
	}
	
	public MUNSDocChecking(Properties ctx, ResultSet rs, String trxName){
		super(ctx, rs, trxName);
	}
	
	public MUNSDocChecking(Properties ctx, String trxName ){
		this(ctx, 0, trxName);
		setDocAction(DocAction.ACTION_Prepare);
		setDocAction(DocAction.STATUS_Drafted);
		setProcessed(false);
	}
	
	public static MUNSDocChecking getDocChecking(Properties ctx, int UNS_DocChecking_ID, String trxName) {
		return new MUNSDocChecking(ctx, UNS_DocChecking_ID, trxName);
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		
		return 0;
	}

	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUNSExpedition[");
		sb.append(get_ID()).append("-").append(getDocumentNo())
			.append(",Status=").append(getDocStatus()).append(",Action=").append(getDocAction())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 * 	Get Document Info
	 *	@return document info
	 */
	public String getDocumentInfo()
	{
		return Msg.getElement(getCtx(), "UNS_Expedition_ID") + " " + getDocumentNo();
	}	//	getDocumentInfo
	
	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	public File createPDF ()
	{
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		
		return true;
	}	
	
	protected boolean afterSave (boolean newRecord, boolean success)
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
		return Env.ZERO;
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
	
	/*
	 * 
	 */
		
	public int recalcDocChecking(int SalesRep_ID, int UNSRayon_ID, Timestamp DateFrom,
			Timestamp DateTo, boolean DeleteOld)
	{	
		int count = 0;
		if (DeleteOld)
		{		
			//TODO cukup panggil method deleteLines() yang sudah dibuat sebelumnya.
			/**
			String sqldel ="DELETE FROM UNS_DocCheckingLine Where UNS_DocChecking_ID in "
					+ " (SELECT UNS_DocChecking_ID from UNS_DocChecking Where UNS_DocChecking_ID = ? )";
			
			PreparedStatement pstmtdel = DB.prepareStatement(sqldel, get_TrxName());
			pstmtdel.setInt(1, get_ID());
			int nodel = pstmtdel.executeUpdate();
			log.config("UNS_DocChecking deleted =" + nodel);
			pstmtdel.close();
			*/
			
			if(!deleteLines())
				return -1;
		}
		MUNSDocumentSchema ds = new MUNSDocumentSchema(getCtx(), getUNS_DocumentSchema_ID(), get_TrxName());
		ds.generateDocLines(this);
				
//				//persiapan insert datanya cui
//				m_ui = Env.getProcessUI(getCtx());
//				String sql ="SELECT ci.c_bpartner_id,ci.c_bpartner_location_id,ci.c_doctype_id,invoiceopen(ci.c_invoice_id,null) as sisa, "
//						+ "ry.name,ci.c_invoice_id,cbpl.bpartnername from c_invoice ci "
//						+ "left join c_bpartner_location cbpl on cbpl.c_bpartner_location_id=ci.c_bpartner_location_id "
//						+ "left join uns_rayon ry on ry.uns_rayon_id=cbpl.uns_rayon_id "
//						+ "left join c_doctype cd on cd.c_doctype_id=ci.c_doctype_id "
//						+ "where ci.docstatus in('CO','CL') and cd.docbasetype='ARI' and invoiceopen(ci.c_invoice_id,null)>0 and ci.dateinvoiced>= ? and  ci.dateinvoiced<= ? "
//						+ "and ci.salesrep_id= ? and ry.uns_rayon_id= ?";
//				ResultSet rs=null;			
//				PreparedStatement pstmt = null;
//				pstmt = DB.prepareStatement(sql, get_TrxName());
//				pstmt.setTimestamp(1, getDateFrom());
//				pstmt.setTimestamp(2, getDateTo());
//				pstmt.setInt(3, getSalesRep_ID());
//				pstmt.setInt(4, getUNS_Rayon_ID());
//				rs = pstmt.executeQuery();
//				while (rs.next()) {
//					MUNSDocCheckingLine cl=new MUNSDocCheckingLine(getCtx(), 0, get_TrxName());
//					cl.setC_BPartner_ID(rs.getInt(1));
//					cl.setC_BPartner_Location_ID(rs.getInt(2));
//					cl.setC_DocType_ID(rs.getInt(3));
//					cl.setAmount(rs.getBigDecimal(4));
//					cl.setC_Invoice_ID(rs.getInt(6));
//					cl.setUNS_DocChecking_ID(getUNS_DocChecking_ID());
//					cl.save();
//					message = "Created record [ "+ m_salesName +" | "+ rs.getString(5) +"|"+rs.getString(7)+" | "+ count +" ]";
//					m_ui.statusUpdate(message);
//					count ++;
//				}
//				rs.close();
//				pstmt.close();
//		 	  }	
//			else {
				
//				m_salesName =DB.getSQLValueString(get_TrxName(), "SELECT Name FROM AD_USER WHERE AD_USer_ID = ?", getSalesRep_ID());
//				
//				m_ui = Env.getProcessUI(getCtx());
//				int m_docchekcing_id = getUNS_DocChecking_ID();
//				String sql ="SELECT ci.c_bpartner_id,ci.c_bpartner_location_id,ci.c_doctype_id, "
//						+ "invoiceopen(ci.c_invoice_id,null) as sisa, "
//						+ "ry.name,ci.c_invoice_id,cbpl.bpartnername from c_invoice ci "
//						+ "left join c_bpartner_location cbpl on cbpl.c_bpartner_location_id=ci.c_bpartner_location_id "
//						+ "left join uns_rayon ry on ry.uns_rayon_id=cbpl.uns_rayon_id "
//						+ "left join c_doctype cd on cd.c_doctype_id=ci.c_doctype_id "
//						+ "where ci.docstatus in('CO','CL') and cd.docbasetype='ARI' "
//						+ "and invoiceopen(ci.c_invoice_id,null)>0 and ci.dateinvoiced>= ? and  ci.dateinvoiced<= ? "
//						+ "and ci.salesrep_id= ? and ry.uns_rayon_id= ? "
//						+ "AND ci.c_invoice_id NOT IN(select dl.c_invoice_id from UNS_DocCheckingLine dl "
//						+ "where dl.UNS_DocChecking_ID="+m_docchekcing_id+")";
//				ResultSet rs=null;			
//				PreparedStatement pstmt = null;
//				try
//				{
//					pstmt = DB.prepareStatement(sql, get_TrxName());
//					pstmt.setTimestamp(1, getDateFrom());
//					pstmt.setTimestamp(2, getDateTo());
//					pstmt.setInt(3, getSalesRep_ID());
//					pstmt.setInt(4, getUNS_Rayon_ID());
//					rs = pstmt.executeQuery();
//					while (rs.next())
//					{
//						MUNSDocCheckingLine cl=new MUNSDocCheckingLine(getCtx(), 0, get_TrxName());
//						cl.setC_BPartner_ID(rs.getInt(1));
//						cl.setC_BPartner_Location_ID(rs.getInt(2));
//						cl.setC_DocType_ID(rs.getInt(3));
//						cl.setAmount(rs.getBigDecimal(4));
//						cl.setC_Invoice_ID(rs.getInt(6));
//						cl.setUNS_DocChecking_ID(getUNS_DocChecking_ID());
//						cl.save();
//						message = "Created record [ "+ m_salesName +" | "+ rs.getString(5) +"|"+rs.getString(7)+" | "+ count +" ]";
//						m_ui.statusUpdate(message);
//						count ++;
//					}
//				}
//				catch(SQLException e)
//				{
//					throw new AdempiereException(e.toString());
//				}				
		return count;
	}
	
	protected boolean beforeDelete()
	{	
		// TODO
		/** ini harusnya langsung kasih id saja tanpa harus in terlebih dahulu
		String sqldel ="DELETE FROM UNS_DocCheckingLine Where UNS_DocChecking_ID in "
				+ " (SELECT UNS_DocChecking_ID from UNS_DocChecking Where UNS_DocChecking_ID = ? )";
		*/
//		String sqldel ="DELETE FROM UNS_DocCheckingLine Where UNS_DocChecking_ID = ?";
//		PreparedStatement pstmtdel = DB.prepareStatement(sqldel, get_TrxName());
//		pstmtdel.setInt(1, get_ID());
//		int nodel = pstmtdel.executeUpdate();
//		log.config("UNS_DocChecking deleted =" + nodel);
//		pstmtdel.close();
		
		return deleteLines();		
	}	
	
	//TODO dibuat method seperti ini supaya ketika mau delete lines baik dari proses generate ataupun hapus dokumen
	//cukup panggil method ini saja, tidak perlu buat 2 kali.
	private boolean deleteLines()
	{
		try {
			// TODO
			/** ini harusnya langsung kasih id saja tanpa harus in terlebih dahulu
			String sqldel ="DELETE FROM UNS_DocCheckingLine Where UNS_DocChecking_ID in "
					+ " (SELECT UNS_DocChecking_ID from UNS_DocChecking Where UNS_DocChecking_ID = ? )";
			*/
			String sqldel ="DELETE FROM UNS_DocCheckingLine Where UNS_DocChecking_ID = ?";
			PreparedStatement pstmtdel = DB.prepareStatement(sqldel, get_TrxName());
			pstmtdel.setInt(1, get_ID());
			int nodel = pstmtdel.executeUpdate();
			log.config("UNS_DocChecking deleted =" + nodel);
			pstmtdel.close();
		} catch (SQLException e)
		{
			log.log(Level.SEVERE, "", e);
			return false;			
		}
		
		return true;
	}
}
