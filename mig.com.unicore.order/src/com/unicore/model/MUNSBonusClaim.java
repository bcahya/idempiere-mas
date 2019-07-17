/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MFactAcct;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.compiere.wf.MWorkflow;

import com.uns.base.model.Query;
import com.uns.util.UNSApps;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.base.model.MOrderLine;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author UNTA-Andy
 * 
 */
public class MUNSBonusClaim extends X_UNS_BonusClaim implements DocAction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -62292826602901146L;
	private MUNSBonusClaimLine[] m_lines;
	private MDiscountSchemaBreak m_DiscountSchemaBreak;
	private boolean m_force = false;
	
	public void setIsForce(boolean force) {
		m_force = force;
	}

	/**
	 * @param ctx
	 * @param UNS_BonusClaim_ID
	 * @param trxName
	 */
	public MUNSBonusClaim(Properties ctx, int UNS_BonusClaim_ID, String trxName)
	{
		super(ctx, UNS_BonusClaim_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBonusClaim(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
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
	}	//	processIt
	
	/**	Process Message 			*/
	private String		m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean		m_justPrepared = false;

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
		if (log.isLoggable(Level.INFO)) log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	//	invalidateIt

	/**************************************************************************
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid) 
	 */
	public String prepareIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		
		if(isNeedConfirm())
		{
			getCreateConfirmation();
		}
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
		
		//	Lines
		MUNSBonusClaimLine[] lines = getLines(true, MOrderLine.COLUMNNAME_M_Product_ID);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		
		return DocAction.STATUS_InProgress;
	}	//	prepareIt

	/**
	 * 	Approve Document
	 * 	@return true if success 
	 */
	public boolean  approveIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}	//	approveIt
	
	/**
	 * 	Reject Approval
	 * 	@return true if success 
	 */
	public boolean rejectIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt

	@Override
	public String completeIt()
	{		
		//	Just prepare
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
		
		MUNSBonusClaimConfirm[] confirms = MUNSBonusClaimConfirm.get(getCtx(), get_ID(), get_TrxName());
		for (MUNSBonusClaimConfirm confirm : confirms)
		{
			if(!confirm.getDocStatus().equals(MUNSBonusClaimConfirm.DOCSTATUS_Completed)
					&& !confirm.getDocStatus().equals(MUNSBonusClaimConfirm.DOCSTATUS_Closed)
					&& !m_force)
			{
				m_processMsg = "Please open Bonus Claim Confirm " + confirm.getDocumentNo();
				return DocAction.STATUS_InProgress;
			}
		}
		
		if (!isApproved())
			approveIt();
		MUNSBonusClaimLine[] lines = getLines(true,null);
		if(lines.length <= 0)
		{
			m_processMsg = "No Lines";
			return DocAction.STATUS_Invalid;
		}
		int validCounter = 0;
		for(int i=0; i<lines.length; i++)
		{
			if(lines[i].getDecisionConfirm().equals(MUNSBonusClaimLine.DECISIONCONFIRM_Discard))
				continue;
			
			validCounter++;
		}
		
		if(validCounter == 0)
		{
			m_processMsg = "No applied lines";
			return DocAction.STATUS_Invalid;
		}
		
		createAndCompleteInvoice();
		
		if (log.isLoggable(Level.INFO)) log.info(toString());
		StringBuilder info = new StringBuilder();
			
		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		setProcessed(true);	
		m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt
	
	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			/* a42niem - BF IDEMPIERE-63 - check if document has been completed before */ 
			if (this.getProcessedOn().signum() == 0) {
				setDateAcct(new Timestamp (System.currentTimeMillis()));
			}
		}
		if (dt.isOverwriteSeqOnComplete()) {
			/* a42niem - BF IDEMPIERE-63 - check if document has been completed before */ 
			if (this.getProcessedOn().signum() == 0) {
				String value = DB.getDocumentNo(getC_DocType_ID(), get_TrxName(), true, this);
				if (value != null)
					setDocumentNo(value);
			}
		}
	}

	@Override
	public boolean voidIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		addDescription(Msg.getMsg(getCtx(), "Voided"));
				
		//if (!createReversals())
		//	return false;
		
		/* globalqss - 2317928 - Reactivating/Voiding order must reset posted */
		MFactAcct.deleteEx(MUNSBonusClaim.Table_ID, getUNS_BonusClaim_ID(), get_TrxName());
		setPosted(false);
		
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
		
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}
	
	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription

	@Override
	public boolean closeIt()
	{
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

	/**
	 * Reverse Correction - same void
	 * 
	 * @return true if success
	 */
	public boolean reverseCorrectIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reverseCorrect
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this,
						ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this,
						ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return voidIt();
	} // reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 * 
	 * @return false
	 */
	public boolean reverseAccrualIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reverseAccrual
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this,
						ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this,
						ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	} // reverseAccrualIt

	@Override
	public boolean reActivateIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;	
				
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		String DocSubTypeSO = dt.getDocSubTypeSO();
		
		//	Replace Prepay with POS to revert all doc
		if (MDocType.DOCSUBTYPESO_PrepayOrder.equals (DocSubTypeSO))
		{
			MDocType newDT = null;
			MDocType[] dts = MDocType.getOfClient (getCtx());
			for (int i = 0; i < dts.length; i++)
			{
				MDocType type = dts[i];
				if (MDocType.DOCSUBTYPESO_PrepayOrder.equals(type.getDocSubTypeSO()))
				{
					if (type.isDefault() || newDT == null)
						newDT = type;
				}
			}
			if (newDT == null)
				return false;
			else
				setC_DocType_ID (newDT.getC_DocType_ID());
		}

	//	if (!createReversals())
	//			return false;

		/* globalqss - 2317928 - Reactivating/Voiding order must reset posted */
		MFactAcct.deleteEx(MUNSBonusClaim.Table_ID, getUNS_BonusClaim_ID(), get_TrxName());
		setPosted(false);
		
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	}

	/*************************************************************************
	 * Get Summary
	 * 
	 * @return Summary of Document
	 */
	public String getSummary()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		// : Grand Total = 123.00 (#1)
		sb.append(": ").append(Msg.translate(getCtx(), "GrandTotal")).append("=")
				.append(getGrandTotal());
		if (m_lines != null)
			sb.append(" (#").append(m_lines.length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	} // getSummary

	/**************************************************************************
	 * String Representation
	 * 
	 * @return info
	 */
	public String toString()
	{
		StringBuffer sb =
				new StringBuffer("MOrder[").append(get_ID()).append("-").append(getDocumentNo())
						.append(", C_DocType_ID=").append(getC_DocType_ID()).append(", DocSourceType=")
						.append(getDocSourceType()).append(", GrandTotal=").append(getGrandTotal()).append("]");
		return sb.toString();
	} // toString

	/**
	 * Get Document Info
	 * 
	 * @return document info (untranslated)
	 */
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getNameTrl() + " " + getDocumentNo();
	} // getDocumentInfo

	@Override
	public File createPDF()
	{
		try
		{
			File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;

	}

	/**
	 * Create PDF file
	 * 
	 * @param file output file
	 * @return file if success
	 */
	public File createPDF(File file)
	{
		ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.ORDER, get_ID(), get_TrxName());
		if (re == null)
			return null;
		MPrintFormat format = re.getPrintFormat();
		// We have a Jasper Print Format
		// ==============================
		if (format.getJasperProcess_ID() > 0)
		{
			ProcessInfo pi = new ProcessInfo("", format.getJasperProcess_ID());
			pi.setRecord_ID(get_ID());
			pi.setIsBatch(true);

			ServerProcessCtl.process(pi, null);

			return pi.getPDFReport();
		}
		// Standard Print Format (Non-Jasper)
		// ==================================
		return re.getPDF(file);
	} // createPDF

	@Override
	public String getProcessMsg()
	{
		
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID()
	{
		
		return getUpdatedBy();
	}

	@Override
	public BigDecimal getApprovalAmt()
	{
		return getGrandTotal();
	}

	/**************************************************************************
	 * Get Lines of Order
	 * 
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return lines
	 */
	public MUNSBonusClaimLine[] getLines(String whereClause, String orderClause)
	{
		// red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		StringBuilder whereClauseFinal =
				new StringBuilder(MUNSBonusClaimLine.COLUMNNAME_UNS_BonusClaim_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MOrderLine.COLUMNNAME_Line;
		//
		List<MUNSBonusClaimLine> list =
				Query
						.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSBonusClaimLine.Table_Name,
								whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID())
						.setOrderBy(orderClause).list();

		return list.toArray(new MUNSBonusClaimLine[list.size()]);
	} // getLines

	/**
	 * Get Lines of Order
	 * 
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	public MUNSBonusClaimLine[] getLines(boolean requery, String orderBy)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;
		else
			orderClause += "Line";
		m_lines = getLines(null, orderClause);
		return m_lines;
	} // getLines

	/**
	 * Get Lines of Order. (used by web store)
	 * 
	 * @return lines
	 */
	public MUNSBonusClaimLine[] getLines()
	{
		return getLines(false, null);
	} // getLines

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		getDiscountSchema(false);
		
		if (!m_DiscountSchemaBreak.getParent().validDate(getDateFrom(),getDateTo()))
			throw new AdempiereException("Not Valid Date!");
		setGrandTotal(getTotalLines());
		if(getC_DocType().getDocBaseType().equals(MDocType.DOCBASETYPE_DiscountAfterTransaction))
			setIsSOTrx(true);
		
		return super.beforeSave(newRecord);
	}

	private MDiscountSchemaBreak getDiscountSchema(boolean reload)
	{
		if (m_DiscountSchemaBreak == null || reload)
			m_DiscountSchemaBreak = new MDiscountSchemaBreak(
					getCtx(), getM_DiscountSchemaBreak_ID(), get_TrxName());
		
			return m_DiscountSchemaBreak;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeDelete()
	 */
	@Override
	protected boolean beforeDelete()
	{
		for (MUNSBonusClaimLine line : getLines())
		{
			if (!line.delete(true))
				return false;
		}

		return super.beforeDelete();
	}
	
	public MUNSBonusClaimConfirm[] getCreateConfirmation()
	{
		MUNSBonusClaimConfirm[] confirms = MUNSBonusClaimConfirm.get(getCtx(), get_ID(), get_TrxName());
		if(confirms != null && confirms.length > 0)
			return confirms;
		
		MUNSBonusClaimConfirm bonusClaimConfirm = new MUNSBonusClaimConfirm(this);
		bonusClaimConfirm.saveEx();
		bonusClaimConfirm.createLinesConfirm();
		confirms = new MUNSBonusClaimConfirm[1];
		confirms[0] = bonusClaimConfirm;
		return confirms;
	}

	public void removeLines()
	{
		for(MUNSBonusClaimLine line : getLines())
		{
			line.deleteEx(true);
		}
	}
	
	/**
	 * Create and complete invoice claim document.
	 */
	private void createAndCompleteInvoice()
	{
		MInvoice invoice = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
		if (getC_Invoice_ID() == 0)
		{
			invoice.setInvoice(this);
		}

		int C_Charge_ID = UNSApps.getRefAsInt(UNSApps.CHRG_DISKONSUSULAN);
		invoice.setC_BPartner_ID(getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(getC_BPartner_Location_ID());
		invoice.setC_Currency_ID(getC_Currency_ID());
		invoice.setC_DocTypeTarget_ID(getDocTypeInvoice_ID());
		invoice.setDateInvoiced(new Timestamp(System.currentTimeMillis()));
		invoice.setAD_Org_ID(getAD_Org_ID());
		invoice.saveEx();
		
		setC_Invoice_ID(invoice.get_ID());
		saveEx();
		
		for (MUNSBonusClaimLine line : getLines())
		{
			if(line.getDecisionConfirm().equals(MUNSBonusClaimLine.DECISIONCONFIRM_Discard))
				continue;
			MInvoiceLine invLine = new MInvoiceLine(getCtx(), line.getC_InvoiceLine_ID(), get_TrxName());
		
			if (invLine.getC_InvoiceLine_ID() == 0){
				invLine = new MInvoiceLine(invoice);
				invLine.setAD_Org_ID(invoice.getAD_Org_ID());
//				MAttributeSetInstance asi = MAttributeSetInstance.create(getCtx(), (MProduct) invLine.getM_Product(), get_TrxName());
//				invLine.setM_AttributeSetInstance_ID(asi.get_ID());
				invLine.setC_Charge_ID(C_Charge_ID); 
			}
			
			invLine.setQty(1);
			invLine.setPrice(line.getAmtClaimed());
			invLine.setLineNetAmt(line.getAmtClaimed());
			invLine.setC_Tax_ID(line.getC_Tax_ID());
			
			invLine.saveEx();

			line.setC_InvoiceLine_ID(invLine.get_ID());
			line.saveEx();
		}
		try
		{
			ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(invoice, DocAction.ACTION_Complete);
			if(pi.isError())
			{
				throw new AdempiereException("Failed on complete invoice :: " + pi.getSummary());
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException("Failed on complete invoice :: " + e.getMessage());
		}
	}
}
