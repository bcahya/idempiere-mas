/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Msg;

/**
 * @author ALBURHANY
 *
 */
public class MUNSPointSchema extends X_UNS_PointSchema implements DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4149504883989671899L;

	/**
	 * @param ctx
	 * @param UNS_PointSchema_ID
	 * @param trxName
	 */
	public MUNSPointSchema(Properties ctx, int UNS_PointSchema_ID,
			String trxName) {
		super(ctx, UNS_PointSchema_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPointSchema(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MUNSPointSchema[");
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
		return Msg.getElement(getCtx(), "UNS_PointSchema_ID") + " " + getDocumentNo();
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
		if(getC_BPartner_ID() == 0 && getC_BP_Group_ID() == 0 && getUNS_Outlet_Type_ID() == 0)
		{
			StringBuffer sql = new StringBuffer
					("SELECT UNS_PointSchema_ID FROM UNS_PointSchema"
							+ " WHERE (AD_Org_ID = 0 OR AD_Org_ID = " + getAD_Org_ID() + ")"
							+ " AND PointType = '" + getPointType() + "'"
							+ " AND DocStatus <> 'CL'"
							+ " AND UNS_PointSchema_ID <> " + getUNS_PointSchema_ID());
			int retVal = DB.getSQLValue(get_TrxName(), sql.toString());
			
			if(retVal >= 1)
				throw new AdempiereException("Duplicate Point Schema");
		}
		
		if(getC_BP_Group_ID() == 0 && getUNS_Outlet_Type_ID() == 0)
		{
			StringBuffer sql = new StringBuffer
					("SELECT UNS_PointSchema_ID FROM UNS_PointSchema"
							+ " WHERE (AD_Org_ID = 0 OR AD_Org_ID = " + getAD_Org_ID() + ")"
							+ " AND PointType = '" + getPointType() + "'"
							+ " AND DocStatus <> 'CL'"
							+ " AND UNS_PointSchema_ID <> " + getUNS_PointSchema_ID()
							+ " AND C_BPartner_ID = " + getC_BPartner_ID());
			int retVal = DB.getSQLValue(get_TrxName(), sql.toString());
			
			if(retVal >= 1)
				throw new AdempiereException("Duplicate Point Schema");
		}
		
		if(getC_BPartner_ID() == 0 && getUNS_Outlet_Type_ID() == 0)
		{
			StringBuffer sql = new StringBuffer
					("SELECT UNS_PointSchema_ID FROM UNS_PointSchema"
							+ " WHERE (AD_Org_ID = 0 OR AD_Org_ID = " + getAD_Org_ID() + ")"
							+ " AND PointType = '" + getPointType() + "'"
							+ " AND DocStatus <> 'CL'"
							+ " AND UNS_PointSchema_ID <> " + getUNS_PointSchema_ID()
							+ " AND C_BP_Group_ID = " + getC_BP_Group_ID());
			int retVal = DB.getSQLValue(get_TrxName(), sql.toString());
			
			if(retVal >= 1)
				throw new AdempiereException("Duplicate Point Schema");
		}
		
		if(getC_BPartner_ID() == 0 && getC_BP_Group_ID() == 0)
		{
			StringBuffer sql = new StringBuffer
					("SELECT UNS_PointSchema_ID FROM UNS_PointSchema"
							+ " WHERE (AD_Org_ID = 0 OR AD_Org_ID = " + getAD_Org_ID() + ")"
							+ " AND PointType = '" + getPointType() + "'"
							+ " AND DocStatus <> 'CL'"
							+ " AND UNS_PointSchema_ID <> " + getUNS_PointSchema_ID()
							+ " AND UNS_Outlet_Type_ID = " + getUNS_Outlet_Type_ID());
			int retVal = DB.getSQLValue(get_TrxName(), sql.toString());
			
			if(retVal >= 1)
				throw new AdempiereException("Duplicate Point Schema");
		}		
		return true;
	}	
	
	protected boolean beforeDelete()
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
		setProcessing(false);
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
		
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		cekLine();
		
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
		
		if (!closeIt())
			return false;
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	voidIt
	
	/**
	 * 	Close Document.
	 * 	Cancel not delivered Qunatities
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

		return false;
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
		
		return false;
	}	//	reverseAccrualIt
	
	/** 
	 * 	Re-activate
	 * 	@return true if success 
	 */
	public boolean reActivateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("reActivateIt - " + toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

	//	setProcessed(false);
		if (! reverseCorrectIt())
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		return true;
	}	//	reActivateIt

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
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

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean cekLine()
	{
		StringBuffer sql = new StringBuffer
				("SELECT UNS_PS_Product_ID FROM UNS_PS_Product"
						+ " WHERE UNS_PointSchema_ID = " + getUNS_PointSchema_ID());
		int idPSProduct = DB.getSQLValue(get_TrxName(), sql.toString());
		
		if (idPSProduct <= -1)
			throw new AdempiereException("Please define Product in tab Point Schema Product for complete this document");
		
		if (idPSProduct >= 1)
		{
			StringBuffer sqlLine = new StringBuffer
					("SELECT UNS_PointSchema_Line_ID FROM UNS_PointSchema_Line"
							+ " WHERE UNS_PS_Product_ID = " + idPSProduct);
			int idLine = DB.getSQLValue(get_TrxName(), sqlLine.toString());
			
			if (idLine <= -1)
				throw new AdempiereException("Please define Value Point in tab Point Schema Line for complete this document");
		}
		
		return true;
	}
	
//	/**
//	 * 
//	 * @param C_BPartner_ID
//	 * @param C_BP_Group_ID
//	 * @param UNS_Outlet_Grade_ID
//	 * @param UNS_Outlet_Type_ID
//	 * @return MUNSPointSchema
//	 */
//	public MUNSPointSchema cekPoint (int C_BPartner_ID, int C_BP_Group_ID, int UNS_Outlet_Grade_ID, int UNS_Outlet_Type_ID)
//	{
//		String whereClause = null;
//		int param = 0;
//		
//		if(C_BPartner_ID != 0)
//		{
//			whereClause = " C_BPartner_ID = ?";
//			param = C_BPartner_ID;
//		}
//		else if
//			(C_BP_Group_ID != 0)
//		{
//			whereClause = " C_BP_Group_ID = ?";
//			param = C_BP_Group_ID;
//		}
//		else if
//			(UNS_Outlet_Grade_ID != 0)
//		{
//			whereClause = " UNS_Outlet_Grade_ID = ?";
//			param = UNS_Outlet_Grade_ID;
//		}
//		else if
//			(UNS_Outlet_Type_ID != 0)
//		{
//			whereClause = " UNS_Outlet_Type_ID = ?";
//			param = UNS_Outlet_Type_ID;
//		}
//		
//		MUNSPointSchema point = new Query(getCtx(), MUNSPointSchema.Table_Name, whereClause, get_TrxName()).
//				setParameters(param).first();
//		
//		return point;
//	}
	
	/**
	 * 
	 * @param ctx
	 * @param C_BPartner_ID
	 * @param PointType
	 * @param trxName
	 * @return {@link MUNSPointSchema}
	 */
	public static MUNSPointSchema getByBPartner (Properties ctx, int C_BPartner_ID, String PointType, String trxName)
	{
		MUNSPointSchema pSchema = new Query(ctx, Table_Name, COLUMNNAME_C_BPartner_ID + "=? AND " + COLUMNNAME_PointType + "=?"
				, trxName)
				.setParameters(C_BPartner_ID, PointType)
					.first();
		
		return (MUNSPointSchema) pSchema;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param C_BP_Group_ID
	 * @param PointType
	 * @param trxName
	 * @return {@link MUNSPointSchema}
	 */
	public static MUNSPointSchema getByBPGroup (Properties ctx, int C_BP_Group_ID, String PointType, String trxName)
	{
		MUNSPointSchema pSchema = new Query(ctx, Table_Name, COLUMNNAME_C_BP_Group_ID+"=? AND " + COLUMNNAME_PointType + "=?"
				, trxName)
				.setParameters(C_BP_Group_ID, PointType)
					.first();
		
		return (MUNSPointSchema) pSchema;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_Outlet_Grade_ID
	 * @param PointType
	 * @param trxName
	 * @return {@link MUNSPointSchema}
	 */
	public static MUNSPointSchema getByOutletGrade (Properties ctx, int UNS_Outlet_Grade_ID, String PointType, String trxName)
	{
		MUNSPointSchema pSchema = new Query(ctx, Table_Name, COLUMNNAME_UNS_Outlet_Grade_ID+"=? AND " + COLUMNNAME_PointType + "=?"
				, trxName)
				.setParameters(UNS_Outlet_Grade_ID, PointType)
					.first();
		
		return (MUNSPointSchema) pSchema;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_Outlet_Type_ID
	 * @param PointType
	 * @param trxName
	 * @return {@link MUNSPointSchema}
	 */
	public static MUNSPointSchema getByOutletType (Properties ctx, int UNS_Outlet_Type_ID, String PointType, String trxName)
	{	
		MUNSPointSchema pSchema = new Query(ctx, Table_Name, COLUMNNAME_UNS_Outlet_Type_ID+"=? AND " + COLUMNNAME_PointType + "=?"
				, trxName)
				.setParameters(UNS_Outlet_Type_ID, PointType)
					.first();
		
		return (MUNSPointSchema) pSchema;
	}
}
