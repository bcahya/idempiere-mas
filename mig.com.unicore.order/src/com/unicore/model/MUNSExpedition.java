/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MCurrency;
import org.compiere.model.MDocType;
import org.compiere.model.MLocation;
import org.compiere.model.MProductPricing;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import com.uns.util.MessageBox;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;

import com.uns.base.model.Query;

/**
 * @author ALBURHANY, Menjangan
 *
 */
public class MUNSExpedition extends X_UNS_Expedition implements DocAction, DocOptions {

	private MLocation m_LocationDest; 
	private MLocation m_LocationOrigin;
	private int m_UOM;
	private MCurrency m_Currency = null;
	boolean isJobSpecOrder = false;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1134013853992838214L;

	public MUNSExpedition(Properties ctx, int UNS_Expedition_ID, String trxName) {
		super(ctx, UNS_Expedition_ID, trxName);
		// TODO Auto-generated constructor stub
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
		if(!isMultiplePath())
		{	
			m_LocationOrigin = (MLocation) getOrigin();
			m_LocationDest = (MLocation) getDestination();
			
			MProductPricing pp = new MProductPricing(m_LocationOrigin, m_LocationDest);
			pp.setM_PriceList_ID(getM_PriceList_ID());
			pp.setPriceDate(getDateDoc());
			
			if(newRecord || is_ValueChanged(COLUMNNAME_Destination_ID) || is_ValueChanged(COLUMNNAME_Origin_ID)
					|| is_ValueChanged(COLUMNNAME_M_PriceList_ID))
			{
				if(!pp.calculatePrice())
				{
					throw new AdempiereException(pp.getMessage());
				}
				
				setDistance(pp.getDistance());
				setPriceList(pp.getPriceList());
				setPriceLimit(pp.getPriceLimit());
				setPriceActual(pp.getPriceStd());
			}
		}
		
		notif(isMultiplePath());
		
		if(is_ValueChanged(COLUMNNAME_IsMultiplePath))
			if(isMultiplePath())
				clearField();
		
		return true;
	}	
	
	protected boolean afterSave (boolean newRecord, boolean success)
	{	
		
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
		
		if(isMultiplePath())
		{
			if(getPath(get_ID()).length == 0)
				throw new AdempiereException("Please define path");
				
			for(MUNSExpeditionPath expPath : getPath(get_ID()))
			{
				if(expPath.getDetail(expPath.get_ID()).length == 0)
					throw new AdempiereException("Please define detail. Path : " + expPath.getOrigin().toString()
							+ " - " + expPath.getDestination().toString());
			}
		}
		
		if(!isMultiplePath())
		{
			if(getDetail(get_ID()).length == 0)
				throw new AdempiereException("Please define detail");
		}
		
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
		
		if(isCreateInvoice())
			createInv(isMultiplePath());

		if(!isMultiplePath())
		{
			m_processMsg = doInvoiceAction(getC_Invoice_ID(), 
					DocAction.ACTION_Complete);
			if (null != m_processMsg)
			{
				return DocAction.STATUS_Invalid;
			}
		}
		else
		{
			for(MUNSExpeditionPath expPath : getPath(get_ID()))
			{
				m_processMsg = doInvoiceAction(expPath.getC_Invoice_ID(), 
						DocAction.ACTION_Complete);
				if (null != m_processMsg)
				{
					return DocAction.STATUS_Invalid;
				}
			}
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
		
		if(isCreateInvoice())
		{
			if(!isMultiplePath())
			{
				m_processMsg = doInvoiceAction(getC_Invoice_ID(), 
						DocAction.ACTION_Void);
				if (null != m_processMsg)
				{
					return false;
				}
			}
			else
			{
				for(MUNSExpeditionPath expPath : getPath(get_ID()))
				{
					m_processMsg = doInvoiceAction(expPath.getC_Invoice_ID(), 
							DocAction.ACTION_Void);
					if (null != m_processMsg)
					{
						return false;
					}
				}
			}
		}
		
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
		
		if(isCreateInvoice())
		{
			if(!isMultiplePath())
			{
				m_processMsg = doInvoiceAction(getC_Invoice_ID(), 
						DocAction.ACTION_ReActivate);
				if (null != m_processMsg)
				{
					return false;
				}
			}
			else
			{
				for(MUNSExpeditionPath expPath : getPath(get_ID()))
				{
					m_processMsg = doInvoiceAction(expPath.getC_Invoice_ID(),
							DocAction.ACTION_ReActivate);
					if (null != m_processMsg)
					{
						return false;
					}
				}
			}
		}
		
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
		return super.getC_Currency_ID();
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return getTotalAmt();
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
	
	public MUNSExpeditionPath[] getPath (int UNS_Expedition_ID)
	{
		List<MUNSExpeditionPath> expPath = new Query(getCtx(), MUNSExpeditionPath.Table_Name,
				COLUMNNAME_UNS_Expedition_ID + "=?", get_TrxName()).setParameters(UNS_Expedition_ID).list();
		
		return expPath.toArray(new MUNSExpeditionPath[expPath.size()]);	
	}
	
	public MUNSExpeditionDetail[] getDetail (int UNS_Expedition_ID)
	{
		List<MUNSExpeditionDetail> expDetail = new Query(getCtx(), MUNSExpeditionDetail.Table_Name,
				COLUMNNAME_UNS_Expedition_ID + "=?", get_TrxName()).setParameters(UNS_Expedition_ID).list();
		
		return expDetail.toArray(new MUNSExpeditionDetail[expDetail.size()]);
	}

	public boolean notif(boolean m_MultiplePath)
	{
		int exist = 0;
		if(m_MultiplePath)
		{
			String sql = "SELECT 1 FROM UNS_ExpeditionDetail WHERE UNS_Expedition_ID = " + get_ID();
			exist = DB.getSQLValue(get_TrxName(), sql.toString());
		}
		
		if(!m_MultiplePath)
		{
			String sql = "SELECT 1 FROM UNS_ExpeditionPath WHERE UNS_Expedition_ID = " + get_ID();
			exist = DB.getSQLValue(get_TrxName(), sql.toString());
		}
		
		if( !isJobSpecOrder && exist >= 1 && !isMultiplePath())
		{
			String msg = Msg.getMsg(getCtx(), "This Action will be delete detail record");
			String title = Msg.getMsg(getCtx(), "Confirm action");
			int retVal = MessageBox.showMsg(this,
					getCtx()
					, msg
					, title
					, MessageBox.YESNO
					, MessageBox.ICONQUESTION);
			if(retVal == MessageBox.RETURN_NO || retVal == MessageBox.RETURN_NONE)
				throw new AdempiereException("Action has been cancelled");
			
			if(retVal == MessageBox.RETURN_OK || retVal == MessageBox.RETURN_YES)
			{				
				if(m_MultiplePath)
				{
					for(MUNSExpeditionDetail expDetail : getDetail(get_ID()))
					{
						expDetail.deleteEx(true, get_TrxName());
					}
				}
				
				if(!m_MultiplePath)
				{
					for(MUNSExpeditionPath expPath : getPath(get_ID()))
					{
						for(MUNSExpeditionDetail expDetail : expPath.getDetail(expPath.get_ID()))
						{
							expDetail.deleteEx(true, get_TrxName());
						}
						expPath.deleteEx(true, get_TrxName());
					}
				}
			}
		}
		
		return true;
	}
	
	public String createInv(boolean m_IsMultiplePath)
	{
		m_UOM = DB.getSQLValue(get_TrxName(), "SELECT C_UOM_ID FROM C_UOM WHERE Name = 'Each'");
		
		if(!m_IsMultiplePath)
		{
			MInvoice inv = new MInvoice(getCtx(), 0, get_TrxName());
			String invoiceType = null;
			
			inv.setAD_Org_ID(getAD_Org_ID());
			inv.setDocumentNo("EXP-" + getDocumentNo());
			String desc = (new StringBuilder("Tonase : ").append(getTonase()
					.setScale(2, RoundingMode.HALF_DOWN)).append(" - Volume : ")
					.append(getVolume().setScale(2, RoundingMode.HALF_DOWN))
					.append(" #").append(getDescription() == null ? "" 
							: getDescription()).append("#")).toString();
			inv.setDescription(desc);
			if(!isSOTrx())
			{
				invoiceType = MDocType.DOCBASETYPE_APInvoice;
			}
			else
			{
				invoiceType = MDocType.DOCBASETYPE_ARInvoice;
			}
			inv.setC_DocTypeTarget_ID(invoiceType);
			inv.setDateAcct(getDateDoc());
			inv.setDateInvoiced(getDateDoc());
			inv.setC_BPartner_ID(getC_BPartner_ID());
			inv.setC_BPartner_Location_ID(getC_BPartner_Location_ID());
			inv.setM_PriceList_ID(getM_PriceList_ID());
			inv.setC_Currency_ID(getC_Currency_ID());			
			inv.setPaymentRule(getPaymentRule());
			for (int i=0; i<100; i++)
			{
				String docNo = inv.getDocumentNo() + "-" + i;
				String sql = "SELECT 1 FROM C_Invoice WHERE DocumentNo = ?";
				int value = DB.getSQLValue(get_TrxName(), sql, 
						docNo);
				if (value > 0)
					continue;
				
				inv.setDocumentNo(docNo);
				break;
			}
			inv.saveEx();
			
			for (MUNSExpeditionDetail expDetail : getDetail(getUNS_Expedition_ID()))
			{
				MInvoiceLine invLine = new MInvoiceLine(inv);
				
				if(expDetail.getC_Charge_ID() > 0)
					invLine.setC_Charge_ID(expDetail.getC_Charge_ID());
				else
					invLine.setM_Product_ID(expDetail.getM_Product_ID());
				invLine.setC_UOM_ID(m_UOM);
				invLine.setQty(Env.ONE);
				invLine.setQtyInvoiced(Env.ONE);
				invLine.setPriceEntered(expDetail.getLineNetAmt());
				invLine.setPriceActual(expDetail.getLineNetAmt());
				invLine.setPriceList(expDetail.getLineNetAmt());
				invLine.setPriceLimit(expDetail.getLineNetAmt());
				desc = (new StringBuilder("Tonase : ").append(getTonase()
						.setScale(2,RoundingMode.HALF_DOWN)).append(
								" - Volume : ").append(getVolume().setScale(
										2, RoundingMode.HALF_DOWN))
						.append(" #").append(expDetail.getItemDescription() 
								== null ? "" : expDetail.getItemDescription())
								.append("#")).toString();
				invLine.setDescription(desc);
				invLine.saveEx();
			}
			
			setC_Invoice_ID(inv.get_ID());
//			boolean ok = inv.processIt(DOCACTION_Complete);
//			if(!ok)
//				throw new AdempiereException(inv.getProcessMsg());
//			if(ok)
				inv.saveEx();
		}
		
		if(m_IsMultiplePath)
		{
			Hashtable<Integer, MInvoice> mapInv = new Hashtable<>();
			
			for(MUNSExpeditionPath expPath : getPath(get_ID()))
			{
				expPath.createInvfromPath(mapInv);
			}
		}
		
		return "Invoice has been created";
	}
	
	public boolean clearField()
	{
		setM_PriceList_ID(-1);
		setOrigin_ID(-1);
		setDestination_ID(-1);
		setPriceActual(Env.ZERO);
		setPriceList(Env.ZERO);
		setPriceLimit(Env.ZERO);
		return true;
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
				String orderType, String isSOTrx, int AD_Table_ID,
					String[] docAction, String[] options, int index)
	{
		if (docStatus.equals(DocAction.STATUS_Drafted))
		{
			options[index++] = DocAction.ACTION_Prepare;
		}
		if (docStatus.equals(DocAction.STATUS_Completed))
		{
			options[index++] = DocAction.ACTION_ReActivate;
			options[index++] = DocAction.ACTION_Void;
		}	
		return index;
	}
	
	@Override
	public void setTotalAmt(BigDecimal TotalAmt)
	{
		int precision = getCurrency().getStdPrecision();
		BigDecimal roundValue = TotalAmt.setScale(precision, RoundingMode.HALF_UP);
		
		super.setTotalAmt(roundValue);
	}
	
	public MCurrency getCurrency()
	{
		if(m_Currency != null)
			return m_Currency;
		m_Currency = (MCurrency) getC_Currency();
		
		return m_Currency;
	}
	
	private String doInvoiceAction (int C_Invoice_ID, String action)
	{
		boolean isActionComplete = action.equals("CO") ? true : false;
		if (C_Invoice_ID <= 0)
		{
			return null;
		}
		
		MInvoice inv = new MInvoice(
				getCtx(), getC_Invoice_ID(), get_TrxName());
		if(isActionComplete)
		{
			String msg = Msg.getMsg(getCtx(), "Invoice has created, do you want auto complete invoice?");
			String title = Msg.getMsg(getCtx(), "Confirm action");
			int retVal = MessageBox.showMsg(this,
					getCtx()
					, msg
					, title
					, MessageBox.YESNO
					, MessageBox.ICONQUESTION);
			if(retVal == MessageBox.RETURN_YES || retVal == MessageBox.RETURN_OK)
			{
				boolean ok = inv.processIt(action);
				if (!ok)
				{
					return inv.getProcessMsg();
				}
				
				for (int i=0; i<100; i++)
				{
					String docNo = inv.getDocumentNo() + "-" + i;
					String sql = "SELECT 1 FROM C_Invoice WHERE DocumentNo = ?";
					int value = DB.getSQLValue(get_TrxName(), sql, 
							docNo);
					if (value > 0)
						continue;
					
					inv.setDocumentNo(docNo);
					break;
				}
				
				try
				{
					inv.saveEx();
				}
				catch (Exception ex)
				{
					return ex.getMessage();
				}
			}
		}
		
		if(!isActionComplete)
		{
			boolean ok = inv.processIt("VO");
			if (!ok)
			{
				return inv.getProcessMsg();
			}			
			try
			{
				inv.saveEx();
			}
			catch (Exception ex)
			{
				return ex.getMessage();
			}
			setC_Invoice_ID(-1);
		}
		
		return null;
	}
	public boolean isJobSpecOrder(boolean _isJobSpecOrder)
	{
		isJobSpecOrder = _isJobSpecOrder;
		
		return isJobSpecOrder;
	}
	
	public static MUNSExpedition getExpedition(PO po, String IsSOTrx) 
	{
		MUNSExpedition expedition = null;
		StringBuilder wc = new StringBuilder(COLUMNNAME_DocStatus)
		.append(" NOT IN ('").append(DOCSTATUS_Voided).append("','")
		.append(DOCSTATUS_Reversed).append("')");
		
		String tableName = po.get_TableName();
		wc.append(" AND EXISTS (SELECT 1 FROM ").append(MUNSExpeditionDetail.Table_Name)
			.append(" WHERE ").append(Table_Name).append(".").append(Table_Name).append("_ID")
				.append("=").append(MUNSExpeditionDetail.Table_Name).append(".").append(Table_Name)
					.append("_ID").append(" AND ").append(MUNSExpeditionDetail.Table_Name).append(".")
						.append(tableName).append("_ID").append("=").append(po.get_ID()).append(")");
		
		String sql = "SELECT UNS_Expedition_ID FROM UNS_Expedition WHERE " + wc.toString();
		if(!Util.isEmpty(IsSOTrx, true))
			sql += " AND IsSOTrx = '" + IsSOTrx + "'";
		int expID = DB.getSQLValue(po.get_TrxName(), sql);
		if(expID > 0)
			expedition = new MUNSExpedition(po.getCtx(), expID, po.get_TrxName());

		return expedition;
	} // getExpedition
	
	public static MUNSExpedition getJobOrder(Properties ctx, int JobSO_ID, String trxName)
	{
		MUNSExpedition expedition = null;
		
		String whereClause = "JobSO_ID = ? AND DocStatus NOT IN ('VO', 'RE')";
		
		String sql = "SELECT UNS_Expedition_ID FROM UNS_Expedition WHERE " + whereClause;
		int expID = DB.getSQLValue(trxName, sql, JobSO_ID);
		if(expID > 0)
			expedition = new MUNSExpedition(ctx, expID, trxName);

		return expedition;
	}
	
	public static MUNSExpedition getByReference(Properties ctx, int Reference_ID, String trxName)
	{
		MUNSExpedition expedition = null;
		
		String whereClause = "Reference_ID = ? AND DocStatus NOT IN ('VO', 'RE')";
		
		String sql = "SELECT UNS_Expedition_ID FROM UNS_Expedition WHERE " + whereClause;
		int expID = DB.getSQLValue(trxName, sql, Reference_ID);
		if(expID > 0)
			expedition = new MUNSExpedition(ctx, expID, trxName);

		return expedition;
	}
}