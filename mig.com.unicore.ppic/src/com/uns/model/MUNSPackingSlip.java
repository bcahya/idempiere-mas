/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.util.ErrorMsg;

import com.uns.base.model.MInvoiceLine;

/**
 * @author menjangan
 *
 */
public class MUNSPackingSlip extends X_UNS_PackingSlip implements DocAction, DocOptions {
	
	MUNSPackingSlipSupplier[] m_Lines = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSContainerArrival[] m_ArrivedContainer;
	
	/**
	 * generate Material Receipt
	 * @param isGroupBySupplier true if you want to group the receipt line by its supplier.
	 * @return
	 */
	public String generateReceipt(boolean isGroupBySupplier)
	{
		String msg="";
		//if (getGenerateTo().equals("Y"))
		//	throw new IllegalArgumentException("Receipt Has Been Created, Can't Create Again");
		
		Hashtable<Integer, MInOut> ioMap = new Hashtable<Integer, MInOut>();
		
		int mmReceiptDT_ID = MDocType.getDocType(MDocType.DOCBASETYPE_MaterialReceipt);
		
		for (MUNSPackingSlipSupplier pss : getLines())
		{
			MUNSPackingSlipLine[] slipLines = pss.getLines();
			MInOut io = null;
			
			if (pss.getC_Invoice_ID() > 0)
			{
				MInvoice invoice = (MInvoice) pss.getC_Invoice();
				org.compiere.model.MInvoiceLine[] invLines = invoice.getLines();
				boolean invoiceHasReceipts = false;
				
				for (org.compiere.model.MInvoiceLine invLine : invLines)
				{
					if (invLine.getM_InOutLine_ID() > 0) {
						for (MUNSPackingSlipLine sl : slipLines)
						{
							if (sl.getC_InvoiceLine_ID() == invLine.getC_InvoiceLine_ID()) 
							{
								sl.setM_InOutLine_ID(invLine.getM_InOutLine_ID());
								if (io == null) 
								{
									io = (MInOut) invLine.getM_InOutLine().getM_InOut();
								}
								sl.save();
							}
						}
						invoiceHasReceipts = true;
					}
				}
				if (invoiceHasReceipts) 
				{
					pss.setM_InOut_ID(io.get_ID());
					pss.saveEx();
					continue;
				}
			}
			
			if (pss.getM_InOut_ID()>0)
				continue;
			
			if (isGroupBySupplier)
				io = ioMap.get(pss.getC_BPartner_ID());
			else
				io = ioMap.get(pss.get_ID());
			
			if (io == null) 
			{
				io = new MInOut(getCtx(), 0, get_TrxName());
			
				io.setAD_Org_ID(getShipReceiveDept_ID());
				io.setIsSOTrx(false);
				io.setC_DocType_ID(mmReceiptDT_ID);
				
				if (pss.getC_Invoice_ID() > 0)
					io.setC_Invoice_ID(pss.getC_Invoice_ID());
				
				io.setMovementDate(getShipDate());
				io.setDateAcct(getShipDate());
				io.setC_BPartner_ID(pss.getC_BPartner_ID());
				io.setC_BPartner_Location_ID(pss.getC_BPartner_Location_ID());
				io.setAD_User_ID(pss.getAD_User_ID());
				io.setSalesRep_ID(pss.getSalesRep_ID());
				io.setC_Order_ID(pss.getC_Order_ID());
				io.setDateOrdered(getDateOrdered());
				io.setM_Warehouse_ID(getM_Warehouse_ID());
				io.setPriorityRule(MInOut.PRIORITYRULE_Medium);
				io.setFreightCostRule(MInOut.FREIGHTCOSTRULE_FreightIncluded);
				io.setMovementType(MInOut.MOVEMENTTYPE_VendorReceipts);
				io.setDocStatus(DOCSTATUS_InProgress);

				if (isGroupBySupplier)
					ioMap.put(pss.getC_BPartner_ID(), io);
				else
					ioMap.put(pss.get_ID(), io);
				io.saveEx();
			}
			
			pss.setM_InOut_ID(io.getM_InOut_ID());
			for (MUNSPackingSlipLine psl : slipLines)
			{
				if (psl.getM_InOutLine_ID() > 0)
					continue;
				
				MInOutLine ioLine = new MInOutLine(io);
				ioLine.setAD_Org_ID(getShipReceiveDept_ID());
				ioLine.setM_InOut_ID(io.getM_InOut_ID());
				ioLine.setM_Product_ID(psl.getM_Product_ID());
				ioLine.setM_Locator_ID(psl.getM_Locator_ID());
				ioLine.setQtyEntered(psl.getQtyDelivered());
				ioLine.setMovementQty(psl.getQtyDelivered());
				ioLine.setC_UOM_ID(psl.getC_UOM_ID());
				ioLine.setIsInvoiced(psl.getC_InvoiceLine_ID() > 0? true : false);
				ioLine.setC_OrderLine_ID(psl.getC_OrderLine_ID());
				ioLine.setM_AttributeSetInstance_ID(psl.getM_AttributeSetInstance_ID());
				//TODO Sales Management BPartner Original
//				ioLine.setBPartner_Original_ID(psl.getBPartner_Original_ID());
//				ioLine.setDescription(Msg.getAmtInWords(Env.getLanguage(getCtx()), sl.getQtyInvoiced().toString()));
				
				if (ioLine.save())
				{
					psl.setM_InOutLine_ID(ioLine.getM_InOutLine_ID());
					if (psl.getC_InvoiceLine_ID() > 0)
					{
						MInvoiceLine invoiceLine = new MInvoiceLine(getCtx(), psl.getC_InvoiceLine_ID(), get_TrxName());
						invoiceLine.setM_InOutLine_ID(ioLine.get_ID());
						if(!invoiceLine.save())
							throw new AdempiereException("Can not update invoice line");
						pss.setC_Invoice_ID(invoiceLine.getC_Invoice_ID());
					}
					psl.saveEx();
				}
				else
				{
					msg = "Can't Create Receipt. ";
					return msg;
				}
				//io.processIt(DOCACTION_Prepare);
				//io.save();
			}			
			pss.save();
		}
		
		return "";
	}

	@Deprecated
	public String generateReceipt()
	{
		String msg="";
		if (getGenerateTo().equals("Y"))
			throw new IllegalArgumentException("Receipt Has Been Created, Can't Create Again");
		
		for (MUNSPackingSlipSupplier ps : getLines())
		{
			boolean invoiceHasReceipts = false;
			MInvoice invoice = (MInvoice) ps.getC_Invoice();
			org.compiere.model.MInvoiceLine[] invLines = invoice.getLines();
			MUNSPackingSlipLine[] slipLines = ps.getLines();
			
			MInOut io = null;
			
			//MInOutLineConfirm[] ioLineConfirms = null;
			for (org.compiere.model.MInvoiceLine invLine : invLines)
			{
				if (invLine.getM_InOutLine_ID() > 0) {
					for (MUNSPackingSlipLine sl : slipLines)
					{
						if (sl.getC_InvoiceLine_ID() == invLine.getC_InvoiceLine_ID()) 
						{
							sl.setM_InOutLine_ID(invLine.getM_InOutLine_ID());
							if (io == null) 
							{
								io = (MInOut) invLine.getM_InOutLine().getM_InOut();
								/*
								MInOutConfirm[] ioConfirms = io.getConfirmations(false);
								MInOutConfirm ioConfirm = null;
								if (ioConfirms == null || ioConfirms.length == 0)
									ioConfirm = 
										MInOutConfirm.create(io, MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm, false);
								else
									ioConfirm = ioConfirms[ioConfirms.length - 1];
								ioLineConfirms = ioConfirm.getLines(false);
								*/
							}
							/*
							for (MInOutLineConfirm ioLineConfirm : ioLineConfirms)
							{
								if (ioLineConfirm.getM_InOutLine_ID() == ioLineConfirm.getM_InOutLine_ID())
									sl.setM_InOutLineConfirm_ID(ioLineConfirm.get_ID());
							}
							*/
							sl.save();
						}
					}
					invoiceHasReceipts = true;
				}
			}
			if (invoiceHasReceipts) 
			{
				ps.setM_InOut_ID(io.get_ID());
				ps.saveEx();
				continue;
			}
			
			io = new MInOut(getCtx(), 0, get_TrxName());
			io.setAD_Org_ID(getShipReceiveDept_ID());
			io.setIsSOTrx(false);
			io.setC_DocType_ID();
			io.setC_Invoice_ID(ps.getC_Invoice_ID());
			io.setMovementDate(getShipDate());
			io.setDateAcct(getShipDate());
			io.setC_BPartner_ID(ps.getC_BPartner_ID());
			io.setC_BPartner_Location_ID(
					ps.getC_Invoice().getC_BPartner_Location_ID());
			io.setC_Order_ID(ps.getC_Order_ID());
			io.setDateOrdered(getDateOrdered());
			io.setM_Warehouse_ID(getM_Warehouse_ID());
			io.setPriorityRule(MInOut.PRIORITYRULE_Medium);
			io.setFreightCostRule(MInOut.FREIGHTCOSTRULE_FreightIncluded);
			io.setMovementType(MInOut.MOVEMENTTYPE_VendorReceipts);
			if (io.save())
			{
				ps.setM_InOut_ID(io.getM_InOut_ID());
				for (MUNSPackingSlipLine sl : slipLines)
				{
					MInOutLine ioLine = new MInOutLine(io);
					ioLine.setAD_Org_ID(getShipReceiveDept_ID());
					ioLine.setM_InOut_ID(io.getM_InOut_ID());
					ioLine.setM_Product_ID(sl.getM_Product_ID());
					ioLine.setM_Locator_ID(ps.getM_Locator_ID());
					ioLine.setQtyEntered(sl.getQtyInvoiced());
					ioLine.setC_UOM_ID(sl.getC_UOM_ID());
					ioLine.setIsInvoiced(true);
					ioLine.setC_OrderLine_ID(sl.getC_OrderLine_ID());
					ioLine.setM_AttributeSetInstance_ID(sl.getM_AttributeSetInstance_ID());
//					ioLine.setDescription(
//							Msg.getAmtInWords(Env.getLanguage(getCtx()), 
//									sl.getQtyInvoiced().toString()));
					if (ioLine.save())
					{
						sl.setM_InOutLine_ID(ioLine.getM_InOutLine_ID());
						MInvoiceLine invoiceLine = new MInvoiceLine(getCtx(), sl.getC_InvoiceLine_ID(), get_TrxName());
						invoiceLine.setM_InOutLine_ID(ioLine.get_ID());
						if(!invoiceLine.save())
							throw new AdempiereException("Can not update invoice line");
					//	sl.setM_InOutLineConfirm_ID();
						sl.save();
					}
					else
					{
						msg = "Can't Create Receipt. ";
						return msg;
					}
				}
				/*
				MInOutConfirm ioConfirm = MInOutConfirm.create(
						io, MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm, false);
				if (ioConfirm == null){
					msg= "Can't Create Confirmation. ";
					return msg;
				}
				ioConfirm.setAD_Org_ID(getShipReceiveConfirmDept_ID());
				ioConfirm.save();
				*/
				/*
				io.prepareIt();
				io.setProcessed(true);
				io.setDocAction(MInOut.DOCACTION_Complete);
				io.setDocStatus(MInOut.DOCSTATUS_InProgress);
				if (!io.save())
					throw new IllegalArgumentException("Can't Create Receipt");
				*/
				io.processIt(DOCACTION_Prepare);
				//ps.setM_InOutConfirm_ID(ioConfirm.getM_InOutConfirm_ID());
				ps.save();
				/*
				MInOutLineConfirm[] lineConfirm = ioConfirm.getLines(false);
				for (MInOutLineConfirm lineConf : lineConfirm)
				{
					lineConf.setAD_Org_ID(getShipReceiveConfirmDept_ID());
					
					for(MUNSPackingSlipLine sLine : slipLines)
					{
						if (lineConf.getM_InOutLine_ID() == sLine.getM_InOutLine_ID())
						{
							sLine.setM_InOutLineConfirm_ID(lineConf.getM_InOutLineConfirm_ID());
							sLine.save();
							lineConf.setTargetQty(sLine.getQtyOrdered());
						}
					}
					lineConf.save();
				}
				*/
			}			
		}
		
		return "";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_PackingSlip_ID
	 * @param trxName
	 */
	public MUNSPackingSlip(Properties ctx, int UNS_PackingSlip_ID,
			String trxName) {
		super(ctx, UNS_PackingSlip_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPackingSlip(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	public static Integer get_ID(MInOut inOut, String trxName) {
		
		String sql = "SELECT UNS_PackingSlip_ID ps From UNS_PackingSlipSupplier psl where psl.M_InOut_ID = ?";
		PreparedStatement st= null;
		ResultSet rs = null;
		try {
			st = DB.prepareStatement(sql, trxName);
			st.setInt(1, inOut.getM_InOut_ID());
			rs = st.executeQuery();
			if (rs.next()){
				return rs.getInt(1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			DB.close(rs, st);
		}
		
		return null;
	}
	
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	MUNSPackingSlipSupplier[] getLines(boolean requery)
	{
		if (m_Lines != null && !requery) {
			set_TrxName(m_Lines, get_TrxName());
			return m_Lines;
		}
		String wClause = COLUMNNAME_UNS_PackingSlip_ID + "=?";
		List<MUNSPackingSlipSupplier> mList = new Query(
				getCtx(), MUNSPackingSlipSupplier.Table_Name,
				wClause, get_TrxName())
		.setParameters(getUNS_PackingSlip_ID())
		.setOrderBy(MUNSPackingSlipSupplier
				.COLUMNNAME_UNS_PackingSlipSupplier_ID)
		.list();
		
		m_Lines = new MUNSPackingSlipSupplier[mList.size()];
		mList.toArray(m_Lines);
		return m_Lines;
	}
	
	/**
	 * getLines
	 * @return
	 */
	public MUNSPackingSlipSupplier[] getLines()
	{
		return getLines(false);
	}
	
	public boolean beforeSave(boolean newRecord)
	{
		return true;
	}
	
	boolean deleteLines()
	{
		MUNSPackingSlipSupplier[] mLines = getLines();
		for (int i=0; i < mLines.length; i++)
		{
			MUNSPackingSlipSupplier line = mLines[i];
			line.delete(true, get_TrxName());
		}
		return true;
	}
	
	public boolean beforeDelete()
	{
		if (!deleteLines())
		{
			ErrorMsg.setErrorMsg(getCtx(), 
					"saveError", "Can't Delete Line");
			return false;
		}		
		return true;
	}
	
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() == 0)
			return;
		
		String sql = "UPDATE UNS_PackingSlipSupplier SET Processed='"
				+ (processed ? "Y" : "N")
				+ "' WHERE UNS_PackingSLip_ID=" + getUNS_PackingSlip_ID();
			int noLine = DB.executeUpdate(sql, get_TrxName());
			m_Lines = null;
			log.fine(processed + " - Lines=" + noLine);
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		
		
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
    			|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
    		options[index++] = DocumentEngine.ACTION_Prepare;
    	}
    	
    	// If status = Completed, add "Reactivte" in the list
    	if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
    		options[index++] = DocumentEngine.ACTION_Reverse_Correct;
    		options[index++] = DocumentEngine.ACTION_Void;
    	}   	
    	
		return index;
	}

	@Override
	public boolean processIt(String action) throws Exception {
		

		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action);
		
	}

	@Override
	public boolean unlockIt() {
		

		log.info(toString());
		setProcessing(false);
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
		
		//cek lines
		MUNSPackingSlipSupplier[] mLines = getLines();
		if (mLines == null || mLines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		
		for (int i=0; i<mLines.length; i++)
		{
			MUNSPackingSlipSupplier line = mLines[i];
			MUNSPackingSlipLine[] mLinesSerPack = line.getLines();
			if (mLinesSerPack == null || mLinesSerPack.length == 0)
			{
				m_processMsg = "@NoLines@";
				return DocAction.STATUS_Invalid;
			}
		}
			
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		for (int i=0; i<mLines.length; i++)
		{
			MUNSPackingSlipSupplier line = mLines[i];
			MUNSPackingSlipLine[] mLinesSerPack = line.getLines();
			for (MUNSPackingSlipLine lineSerPack : mLinesSerPack)
			{
				lineSerPack.setProcessed(true);
				lineSerPack.save();
			}
			line.setProcessed(true);
			line.save();
		}
		
		m_justPrepared = true;
		
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		setProcessed(true);
		return DocAction.STATUS_InProgress;
		
	}

	@Override
	public boolean approveIt() {
		
		
		save();
		log.info(toString());
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
		
		if (!isApproved())
			approveIt();
//		m_processMsg = info.toString();
		//
		//m_processMsg += "Material Receipt records are created.";
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
		
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Approved.equals(getDocStatus())
				|| DOCSTATUS_NotApproved.equals(getDocStatus()) )
			{

				//	Set lines to 0
				MUNSPackingSlipSupplier[] lines = getLines(false);
				for (int i = 0; i < lines.length; i++)
				{
					MUNSPackingSlipSupplier line = lines[i];
					MUNSPackingSlipLine[] mLinesSerPack = line.getLines();
					for (MUNSPackingSlipLine lineSerPack : mLinesSerPack)
					{
						BigDecimal oldqty = lineSerPack.getQtyOrdered();
						BigDecimal oldvol = lineSerPack.getQtyInvoiced();
						BigDecimal oldPrice = lineSerPack.getBrutoQuantity();
						
						if (oldPrice.signum() != 0
								|| oldvol.signum() != 0
								|| oldqty.signum() != 0)
						{
							lineSerPack.setQtyInvoiced(Env.ZERO);
							lineSerPack.setQtyOrdered(Env.ZERO);
							lineSerPack.setBrutoQuantity(Env.ZERO);
							lineSerPack.save(get_TrxName());
						}
					}
				}
				
				setDocStatus(DOCSTATUS_Voided); // need to set & save docstatus to be able to check it in MInOutConfirm.voidIt()
				saveEx();
			}
			else
			{
				return reverseCorrectIt();
			}

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
		
		return false;
	}

	@Override
	public String getSummary() {
		
		return null;
	}

	@Override
	public String getDocumentInfo() {
		
		return null;
	}

	@Override
	public File createPDF() {
		
		return null;
	}

	@Override
	public String getProcessMsg() {
		
		return null;
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

	public MUNSContainerArrival[] getArrivedContainer() {
		return getArrivedContainer(false);
	}
	
	public MUNSContainerArrival[] getArrivedContainer(boolean requery) {
		if (m_ArrivedContainer != null && !requery) {
			set_TrxName(m_ArrivedContainer, get_TrxName());
			return m_ArrivedContainer;
		}
		String wClause = COLUMNNAME_UNS_PackingSlip_ID + "=?";
		List<MUNSContainerArrival> list = new Query(
				getCtx(), MUNSContainerArrival.Table_Name,
				wClause, get_TrxName())
		.setParameters(getUNS_PackingSlip_ID())
		.setOrderBy(MUNSContainerArrival
				.COLUMNNAME_UNS_ContainerArrival_ID)
		.list();
		
		m_ArrivedContainer = new MUNSContainerArrival[list.size()];
		list.toArray(m_ArrivedContainer);
		return m_ArrivedContainer;
	}

}
