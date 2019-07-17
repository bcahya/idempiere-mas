/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutConfirm;
import org.compiere.model.MInOutLineConfirm;
import org.compiere.model.MNonBusinessDay;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_M_InOut;
import org.compiere.model.X_M_InOutConfirm;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.util.ErrorMsg;

import com.uns.base.model.Query;
import com.uns.model.MUNSBiayaBongkarMuat;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSBongkarMuat extends X_UNS_BongkarMuat implements DocAction,DocOptions{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean m_reversal = false;
	private String m_processMsg = null;
	private boolean	m_justPrepared = false;
	private MUNSBongkarMuatLine[] m_lines = null;
	//private int m_CostElement = UNSApps.getRefAsInt(UNSApps.CSTE_MTR);
	//private int m_ChargeUnloading = UNSApps.getRefAsInt(UNSApps.CHRG_BKR);
	/**
	 * 
	 * @param From
	 * @return
	 */
	protected MUNSBongkarMuat CopyFrom(MUNSBongkarMuat From,String trxName)
	{
		MUNSBongkarMuat to = new MUNSBongkarMuat(getCtx(), 0, get_TrxName());
		copyValues(From, to, From.getAD_Client_ID(), From.getAD_Org_ID());
		to.set_ValueNoCheck("UNS_BongkarMuat_ID",I_ZERO);
		to.setDateAcct(From.getDateAcct());
		to.setDescription(From.getDescription());
		to.setDocStatus(DOCSTATUS_Drafted);
		to.setDocAction(DOCACTION_Complete);	
		to.setPosted(false);
		to.setIsApproved(false);
		to.setProcessed(false);
		to.setUNS_PackingSlip_ID(From.getUNS_PackingSlip_ID());
		to.setC_DocType_ID(From.getC_DocType_ID());
		to.setRef_UNS_BongkarMuat_ID(From.getUNS_BongkarMuat_ID());
		
		if (!to.save(trxName))
			throw new IllegalStateException("Could not create Bongkar Muat");
		
		if (to.copyLinesFrom(From) <=0)
			throw new IllegalStateException("Coul'd not create Bongkar Muat Line");
		
		return to;
	}
	
	/**
	 * 
	 * @param from
	 * @return
	 */
	protected int copyLinesFrom(MUNSBongkarMuat from)
	{
		if (isProcessed() || isPosted() || from == null)
			return 0;
		int count =0;
		MUNSBongkarMuatLine[] lines = from.getLines();
		for (int i=0; i< lines.length; i++)
		{
			MUNSBongkarMuatLine line = lines[i];
			MUNSBongkarMuatLine newLine = new MUNSBongkarMuatLine(this);
			newLine.set_TrxName(get_TrxName());
			newLine.setC_BPartner_ID(line.getC_BPartner_ID());
			newLine.setC_UOM_ID(line.getC_UOM_ID());
			newLine.setDescription(line.getDescription());
			newLine.setM_InOut_ID(line.getM_InOut_ID());
			newLine.setC_Order_ID(line.getC_Order_ID());
			newLine.setC_OrderLine_ID(line.getC_OrderLine_ID());
			newLine.setM_InOutConfirm_ID(line.getM_InOutConfirm_ID());
			newLine.setM_InOutLine_ID(line.getM_InOutLine_ID());
			newLine.setM_InOutLineConfirm_ID(line.getM_InOutLineConfirm_ID());
			newLine.setM_Product_ID(line.getM_Product_ID());
			newLine.setPriceCost(line.getPriceCost());
			newLine.setQty(line.getQty());
			newLine.setTypeBongkar(line.getTypeBongkar());
			newLine.setRef_UNS_BongkarMuatLine_ID(line.getUNS_BongkarMuatLine_ID());
			if(newLine.save())
				count++;
		}
		if(lines.length != count)
		{
			log.log(Level.SEVERE, "Line difference - From=" + lines.length + " <> Saved=" + count);
			count = -1;
		}
		return count;
	}
	
	private MOrg m_AD_Org_ID = null;
	private String getNameOfDepartement(int AD_Org_ID)
	{
		if (null != m_AD_Org_ID && m_AD_Org_ID.getAD_Org_ID() == AD_Org_ID)
			return m_AD_Org_ID.getName();
		
		return MOrg.get(getCtx(), AD_Org_ID).getName();
	}
	
	public MUNSResource getWorkstation() {
		return new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
	}
	
	private boolean deleteLines()
	{
		for(MUNSBongkarMuatLine mLine : getLines())
		{
			if(!mLine.delete(true))
				return false;
		}
		for(MUNSBongkarMuatWorker mLine : getWorkers())
		{
			if(!mLine.delete(true))
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public String createLines()
	{
		if (getCreateFrom().equals("Y"))
			if(!deleteLines())
			{
				m_processMsg = "Failed to delete old line";
				return m_processMsg;
			}
		MUNSPackingSlip ps = 
				new MUNSPackingSlip(getCtx(), getUNS_PackingSlip_ID(), get_TrxName());
		MUNSPackingSlipSupplier[] pssLines = ps.getLines();
		for (int i=0; i< pssLines.length; i++)
		{
			MUNSPackingSlipSupplier pss = pssLines[i];
			MUNSPackingSlipLine[] pslLines = pss.getLines();
			for(MUNSPackingSlipLine psl : pslLines)
			{
				MInOut io = new MInOut(getCtx(), pss.getM_InOut_ID(), get_TrxName());
				MInOutConfirm confirm = new MInOutConfirm(
						getCtx(), pss.getM_InOutConfirm_ID(), get_TrxName());
				MInOutLineConfirm lc = new MInOutLineConfirm(
						getCtx(), psl.getM_InOutLineConfirm_ID(), get_TrxName());
				if (!io.getDocStatus().equals(X_M_InOut.DOCSTATUS_Completed)
						|| !confirm.getDocStatus().equals(
								X_M_InOutConfirm.DOCSTATUS_Completed))
				{
					throw new IllegalArgumentException("Material receipt is not complete");
				}
				
				MUNSBongkarMuatLine bm = new MUNSBongkarMuatLine(getCtx(), 0, get_TrxName());
				bm.setC_BPartner_ID(pss.getC_BPartner_ID());
				bm.setAD_Org_ID(getAD_Org_ID());
				bm.setC_UOM_ID(psl.getC_UOM_ID());
				bm.setDescription(psl.getDescription());
				bm.setM_InOut_ID(pss.getM_InOut_ID());
				bm.setM_InOutConfirm_ID(pss.getM_InOutConfirm_ID());
				bm.setM_InOutLine_ID(psl.getM_InOutLine_ID());
				bm.setM_InOutLineConfirm_ID(psl.getM_InOutLineConfirm_ID());
				bm.setM_AttributeSetInstance_ID(psl.getM_InOutLine().getM_AttributeSetInstance_ID());
				bm.setM_Product_ID(psl.getM_Product_ID());
				bm.setPriceCost(BigDecimal.ZERO);
				bm.setLineNetAmt(BigDecimal.ZERO);
				bm.setQty(lc.getConfirmedQty());
				bm.setUNS_BongkarMuat_ID(getUNS_BongkarMuat_ID());
				bm.setC_Order_ID(pss.getC_Order_ID());
				bm.setC_OrderLine_ID(psl.getC_OrderLine_ID());
				if (!bm.save())
					throw new IllegalArgumentException(
							"Can't create Lines");
			}
		}
		generateUnloadingWorker();
		return "";
	}
	
	private BigDecimal getCostProduct(String handlingType, int M_Product_ID, int C_UOM_ID, int AD_Org_ID)
	{
		BigDecimal biayaBongkar = MUNSBiayaBongkarMuat.getBiayaBongkarMuat(
				handlingType, false, M_Product_ID, C_UOM_ID,AD_Org_ID, get_TrxName());
		if (null == biayaBongkar || biayaBongkar.compareTo(BigDecimal.ZERO)<=0)
		{
			MProduct product = MProduct.get(getCtx(), M_Product_ID);
			throw new IllegalArgumentException(
					"NO COST BONGKAR MUAT FOR "+ product.getName()
			+ " Departement " + getNameOfDepartement(AD_Org_ID));
		}
		return biayaBongkar;
	}
	
	
	/**
	 * 
	 * @param description
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}
	
	/**
	 * @param ctx
	 * @param UNS_BongkarMuat_ID
	 * @param trxName
	 */
	public MUNSBongkarMuat(Properties ctx, int UNS_BongkarMuat_ID,
			String trxName) {
		super(ctx, UNS_BongkarMuat_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	/*
	public void createLandedCost() throws Exception {
		MUNSPackingSlip packingSlip = new MUNSPackingSlip(getCtx(), getUNS_PackingSlip_ID(), get_TrxName());
		MUNSPackingSlipSupplier[] pss = packingSlip.getLines();
		for (MUNSPackingSlipSupplier ps : pss){
			try {
				generateLandedCost(ps);
			} catch (Exception e){
				log.log(Level.SEVERE,e.getMessage().toString().toUpperCase());
				m_processMsg = e.getMessage().toString().toUpperCase();
			}
		}
	}
	*/
	/*
	private void generateLandedCost(MUNSPackingSlipSupplier ps) throws Exception {
		Hashtable<Integer, Integer> M_Product_IDTmp = new Hashtable<Integer, Integer>();
		BigDecimal costUnloading = BigDecimal.ZERO;
		BigDecimal qtyEntered = BigDecimal.ZERO;
		String description = "AUTO GENERATE FROM UNLOADING COST " + getDocumentNo();
		int C_Invoice_ID = ps.getC_Invoice_ID();
		if (C_Invoice_ID <=0){
			throw new IllegalArgumentException("NO INVOICE");
		}
		
		MInvoice invoice = new MInvoice(getCtx(), C_Invoice_ID, get_TrxName());
		int M_inOut_ID = DB.getSQLValue(get_TrxName(), "SELECT M_InOut_ID FROM M_InOut WHERE C_Order_ID = ?",invoice.getC_Order_ID());
		MInvoiceLine[] invoiceLines = invoice.getLines();
		for (MInvoiceLine invoiceLine : invoiceLines ) {
			if (invoiceLine.getC_Charge_ID() <= 0) {
				BigDecimal qty = invoiceLine.getQtyInvoiced();
				int M_Product_ID = invoiceLine.getM_Product_ID();
				M_Product_IDTmp.put(M_Product_ID, M_Product_ID);
				int C_UOM_ID = invoiceLine.getC_UOM_ID();
				int AD_Org_ID = getAD_Org_ID();
				BigDecimal cost = MUNSBiayaBongkarMuat.getBiayaBongkarMuat(
						M_Product_ID, C_UOM_ID, AD_Org_ID, get_TrxName());
				if(null == cost || cost.compareTo(BigDecimal.ZERO) <=0) {
					throw new Exception("NO COST FOR PRODUCT " +invoiceLine.getM_Product().getName());
				}
				qtyEntered = qtyEntered.add(qty);
				costUnloading = costUnloading.add(cost);
			}
		}
		MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
		invoiceLine.setC_Charge_ID(m_ChargeUnloading);
		invoiceLine.setDescription(description);
		invoiceLine.setPriceActual(costUnloading);
		invoiceLine.setQtyEntered(qtyEntered);
		invoiceLine.setQtyInvoiced(qtyEntered);
		invoiceLine.setPrice(costUnloading);
		invoiceLine.setTax();
		if (invoiceLine.save()){
			Iterator<Integer> m_ProductIterator = M_Product_IDTmp.values().iterator();
			while (m_ProductIterator.hasNext()) {
				int product_ID = m_ProductIterator.next();
				MLandedCost landedCost = new MLandedCost(getCtx(), 0, get_TrxName());
				landedCost.setC_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
				landedCost.setAD_Org_ID(invoiceLine.getAD_Org_ID());
				landedCost.setDescription(description);
				landedCost.setM_CostElement_ID(m_CostElement);
				landedCost.setM_Product_ID(product_ID);
				landedCost.setM_InOut_ID(M_inOut_ID);
				landedCost.setLandedCostDistribution(X_C_LandedCost.LANDEDCOSTDISTRIBUTION_Costs);
				if (landedCost.save()){
					landedCost.allocateCosts();
				}
				else {
					throw new Exception("Can't Create landed Cost");
				}
			}
		}
	}
	*/

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBongkarMuat(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx,rs,trxName);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	protected MUNSBongkarMuatLine[] getLines(boolean requery)
	{
		if (m_lines != null
				&& !requery)
		{
			set_TrxName(m_lines,get_TrxName());
			return m_lines;
		}
		String wc = MUNSBongkarMuatLine.COLUMNNAME_UNS_BongkarMuat_ID + "=?";
		List<MUNSBongkarMuatLine> mList = Query.get(
				getCtx(),UNSPPICModelFactory.getExtensionID(),
				MUNSBongkarMuatLine.Table_Name, wc
						,get_TrxName()).setParameters(getUNS_BongkarMuat_ID())
						.setOrderBy(
								MUNSBongkarMuatLine.COLUMNNAME_UNS_BongkarMuatLine_ID)
								.list();
		m_lines = new MUNSBongkarMuatLine[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
	
	/**
	 * 
	 */
	public boolean beforeDelete()
	{
		if (!deleteLines())
		{
			ErrorMsg.setErrorMsg(getCtx(), "deleteError", "Could not delete Lines");
			return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public MUNSBongkarMuatLine[] getLines()
	{
		return getLines(false);
	}
	
	/**
	 * 
	 * @param reversal
	 */
	private void setReversal(boolean reversal)
	{
		m_reversal = reversal;
	}
	
	public boolean isReversal()
	{
		return m_reversal;
	}
	
	/**
	 * 
	 */
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() == 0)
			return;
		
		String sql = "UPDATE UNS_BongkarMuatLine SET Processed='"
				+ (processed ? "Y" : "N")
				+ "' WHERE UNS_BongkarMuat_ID=" + getUNS_BongkarMuat_ID();
			int noLine = DB.executeUpdate(sql, get_TrxName());
			m_lines = null;
			log.fine(processed + " - Lines=" + noLine);
	}
	
	

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		MUNSBongkarMuatLine[] lines = getLines();
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
		//setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
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
		
		//m_processMsg = info.toString();
		//
		/*
		try {
			createLandedCost();
		}catch (Exception e){
			log.log(Level.SEVERE, e.getMessage().toString().toUpperCase());
			return DocAction.STATUS_Invalid;
		}
		*/
		m_processMsg = createReceivableOfWorker();
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = createWorkerAbsence();
		if(null != m_processMsg)
		{
			setProcessed(false);
			return DocAction.STATUS_Invalid;
		}
		
		setProcessed(true);	
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
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
				MUNSBongkarMuatLine[] lines = getLines(false);
				for (int i = 0; i < lines.length; i++)
				{
					MUNSBongkarMuatLine line = lines[i];
					BigDecimal old = line.getQty();
					if (old.signum() != 0)
					{
						line.setQty(Env.ZERO);
						line.addDescription("Void (" + old + ")");
						line.save(get_TrxName());
					}
				}
				//
				// Void Confirmations
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		MUNSBongkarMuat reversal = CopyFrom(this, get_TrxName());
		if (reversal == null)
		{
			m_processMsg = "Could not create Ship Reversal";
			return false;
		}
		reversal.setReversal(true);
		
		MUNSBongkarMuatLine[] bmLines = getLines(); //bongkar muat lines
		MUNSBongkarMuatLine[] rLines = reversal.getLines(false); //reversal lines
		for(int i=0; i<rLines.length; i++)
		{
			MUNSBongkarMuatLine rLine = rLines[i];
			rLine.setQty(rLine.getQty().negate());
			rLine.setReversalLine_ID(bmLines[i].getM_InOutLine_ID());
			if (!rLine.save(get_TrxName()))
			{
				m_processMsg = "Could not correct Ship Reversal Line";
				return false;
			}
			reversal.setReversal(true);
		}
		reversal.addDescription("{->" + getDocumentNo() + ")");
		//FR1948157
		reversal.setReversal_ID(getUNS_BongkarMuat_ID());
		reversal.saveEx(get_TrxName());
		
		try {
			if (!reversal.processIt(DocAction.ACTION_Complete)
					|| !reversal.getDocStatus().equals(DocAction.STATUS_Completed))
				{
					m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
					return false;
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			reversal.closeIt();
			reversal.setProcessing (false);
			reversal.setDocStatus(DOCSTATUS_Reversed);
			reversal.setDocAction(DOCACTION_None);
			reversal.saveEx(get_TrxName());
			//
			addDescription("(" + reversal.getDocumentNo() + "<-)");
			
			//
			// Void Confirmations
			setDocStatus(DOCSTATUS_Reversed); // need to set & save docstatus to be able to check it in MInOutConfirm.voidIt()
			saveEx();

			// After reverseCorrect
			m_processMsg = ModelValidationEngine.get().fireDocValidate(
					this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
			if (m_processMsg != null)
				return false;

			m_processMsg = reversal.getDocumentNo();
			//FR1948157
			//this.setReversal_ID(reversal.getUNS_BongkarMuat_ID());
			setProcessed(true);
			setDocStatus(DOCSTATUS_Reversed);		//	 may come from void
			setDocAction(DOCACTION_None);
			return true;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(":")
		//	.append(Msg.translate(getCtx(),"TotalLines")).append("=").append(getTotalLines())
			.append(" (#").append(getLines().length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
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
		return Env.ZERO;
	}
	
	public void generateUnloadingWorker()
	{
		MUNSResource workStation = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
		List<MUNSResourceWorkerLine> listOfWorkerLines = workStation.getListOfWorker();
		for(MUNSResourceWorkerLine worker : listOfWorkerLines)
		{
			MUNSEmployee employee = new MUNSEmployee(getCtx(), worker.getLabor_ID(), get_TrxName());
			MUNSBongkarMuatWorker bm_Worker = new MUNSBongkarMuatWorker(getCtx(), 0, get_TrxName());
			bm_Worker.setUNS_BongkarMuat_ID(get_ID());
			bm_Worker.setLabor_ID(worker.getLabor_ID());
			bm_Worker.setUNS_Job_Role_ID(worker.getUNS_Job_Role_ID());
			bm_Worker.setAD_Org_ID(getAD_Org_ID());
			bm_Worker.setReplacementLabor_ID(worker.getLabor_ID());
			bm_Worker.setC_BPartner_ID(employee.getVendor_ID());
			bm_Worker.setReceivableAmt(BigDecimal.ZERO);
			bm_Worker.setPayrollTerm(worker.getPayrollTerm());
			bm_Worker.save();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<MUNSBongkarMuatWorker> getWorkers()
	{
		return Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID()
				, MUNSBongkarMuatWorker.Table_Name
				, MUNSBongkarMuatWorker.COLUMNNAME_UNS_BongkarMuat_ID + " = " + getUNS_BongkarMuat_ID()
				, get_TrxName())
				.list();
	}
	
	/**
	 * 
	 * @param listOfWorker
	 * @return
	 */
	private String createPresence(List<MUNSBongkarMuatWorker> listOfWorker)
	{
		String msg = null;
		Calendar calendarBongkarMuat = Calendar.getInstance();
		calendarBongkarMuat.setTimeInMillis(getDateAcct().getTime());
		boolean isNonBusinessDay = MNonBusinessDay.isNonBusinessDay(getCtx(), getDateAcct(), getAD_Org_ID(), get_TrxName());
		MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
		
		for (MUNSBongkarMuatWorker worker : listOfWorker)
		{
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
			MUNSHalfPeriodPresence halfPeriodPresence = MUNSHalfPeriodPresence.get(
					getCtx(), labor_ID, getDateAcct(), getAD_Org_ID(), rsc.getResourceParent_ID(), get_TrxName());
			

			X_UNS_Employee employee = new X_UNS_Employee(getCtx(), worker.getLabor_ID(), get_TrxName());
			
			if (null == halfPeriodPresence)
			{
				msg = "Please create worker periodic presence for " + employee.getName();
				return msg;
			}
			

//			MUNSResourceWorkerLine workerOfResource = MUNSResourceWorkerLine.getWorkerOfOrg(
//				getCtx(), getAD_Org_ID(), getLabor_ID(), get_TrxName());

			MUNSResourceWorkerLine workerOfResource = MUNSResourceWorkerLine.getWorker(
					getCtx(), getUNS_Resource_ID(), labor_ID, get_TrxName());
			
			if (null == workerOfResource)
				continue;

			MUNSSlotType shift = rsc.getSlotType();
			MUNSWorkerPresence wp = halfPeriodPresence.getWorkerPresence(
										getDateAcct(),rsc.getUNS_Resource_ID() );
			if (null == wp)
				wp = new MUNSWorkerPresence(getCtx(), 0, get_TrxName());
			
			wp.setUNS_HalfPeriod_Presence_ID(halfPeriodPresence.get_ID());
			wp.setAD_Org_ID(getAD_Org_ID());
			wp.setWorkDate(getDateAcct());
			wp.setUNS_Resource_ID(getUNS_Resource_ID()); 
			wp.setReceivableAmt(wp.getReceivableAmt().add(worker.getReceivableAmt()));
			if (!shift.IsWorkDay(calendarBongkarMuat.get(Calendar.DAY_OF_WEEK)))
				if (isNonBusinessDay)
					wp.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburNasional);
				else
					wp.setDayType(MUNSWorkerPresence.DAYTYPE_HariLiburMingguan);
			else
				wp.setDayType(MUNSWorkerPresence.DAYTYPE_HariKerjaBiasa);
			
			wp.setPresenceStatus(MUNSWorkerPresence.PRESENCESTATUS_FullDay);
			if(isOverTime())
			{
				wp.setOvertime(getHoursOverTime());
				wp.setOT_Unloading_ID(getUNS_BongkarMuat_ID());
			}
			else
			{
				wp.setUNS_BongkarMuat_ID(getUNS_BongkarMuat_ID());
			}
			if(!wp.save())
				throw new AdempiereException("Failed to create Presence");
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isNoWorkDay()
	{
		MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
		if(rsc.getUNS_SlotType_ID() <= 0)
			return true;
		MUNSSlotType slotType = (MUNSSlotType) rsc.getSlotType();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getDateAcct().getTime());
		return !slotType.IsWorkDay(cal.get(Calendar.DAY_OF_WEEK));
	}
	
	/**
	 * 
	 * @return
	 */
	protected String createDailyReceivable()
	{
		m_processMsg = generateCostOfProductLines();
		if(null != m_processMsg)
			return m_processMsg;
		return createPresence(getWorkers());
	}
	
	/**
	 * Buat pembayaran untuk worker utama, dan kemudian return sisanya untuk worker non primary
	 * @param amt
	 * @param listOfWorker
	 * @return
	 */
	protected double createReceivableForPrimPortion(double amt, List<MUNSBongkarMuatWorker> listOfWorker)
	{
		double sisaAmt = amt;
		for (MUNSBongkarMuatWorker worker : getWorkers())
		{
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
			
			MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
			MUNSResourceWorkerLine rscWorker  = rsc.getWorker(labor_ID);
//					MUNSResourceWorkerLine.getWorker(getCtx(), getAD_Org_ID(), labor_ID, get_TrxName());
			if(null == rscWorker)
				continue;
			if(!rscWorker.isPrimePortion())
				continue;
			
			double proportion = rscWorker.getResultProportion().doubleValue();
			proportion /= 100;
			double pay = sisaAmt * proportion;
			sisaAmt -= pay;
			worker.setReceivableAmt(new BigDecimal(pay));
			worker.save();
			listOfWorker.add(worker);
		}
		return sisaAmt;
	}
	
	/**
	 * 
	 * @return
	 */
	protected String createGroupResultReceivable()
	{
		m_processMsg = generateCostOfProductLines();
		if(null != m_processMsg)
			return m_processMsg;
		
		List<MUNSBongkarMuatWorker> listOfWorker = new ArrayList<MUNSBongkarMuatWorker>();
		double totalAmtForNonPrimPortion = createReceivableForPrimPortion(
				getTotalAmt().doubleValue(), listOfWorker);
		
		for(MUNSBongkarMuatWorker worker : getWorkers())
		{
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
			MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
			MUNSResourceWorkerLine rscWorker = rsc.getWorker(labor_ID);
					
//					MUNSResourceWorkerLine.getWorker(getCtx(), getAD_Org_ID(), labor_ID, get_TrxName());
			if(null == rscWorker)
				continue;
			if(rscWorker.isPrimePortion())
				continue;
			
			double proportion = rscWorker.getResultProportion().doubleValue();
			proportion /= 100;
			double pay = totalAmtForNonPrimPortion * proportion;
			worker.setReceivableAmt(new BigDecimal(pay));
			worker.save();
			listOfWorker.add(worker);
		}
		return createPresence(listOfWorker);
	}
	
	/**
	 * 
	 * @return
	 */
	protected String createPersonalResultReceivable()
	{
		List<MUNSBongkarMuatWorker> listOfWorker = new ArrayList<MUNSBongkarMuatWorker>();
		double totalCost = 0.0;
		for(MUNSBongkarMuatWorker worker : getWorkers())
		{
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();

//			MUNSResourceWorkerLine workerOfResource = MUNSResourceWorkerLine.getWorkerOfOrg(
//				getCtx(), getAD_Org_ID(), getLabor_ID(), get_TrxName());

			MUNSResourceWorkerLine workerOfResource = MUNSResourceWorkerLine.getWorker(
					getCtx(), getUNS_Resource_ID(), labor_ID, get_TrxName());
			
			if(null == workerOfResource)
				continue;
			
			totalCost += worker.getReceivableAmt().doubleValue();
			listOfWorker.add(worker);
		}
		String msg = createCostOfProductLine(totalCost);
		if(null != msg)
			return msg;
		return createPresence(listOfWorker);
	}
	
	protected String createCostOfProductLine(double totalCost)
	{
		double totalQty = getTotalQty();
		for(MUNSBongkarMuatLine mLine : getLines())
		{
			double proportion = mLine.getQty().doubleValue() / totalQty;
			double lineNet = totalCost * proportion;
			double cost = lineNet / mLine.getQty().doubleValue();
			mLine.setLineNetAmt(new BigDecimal(lineNet));
			mLine.setPriceCost(new BigDecimal(cost));
			if(!mLine.save())
			{
				return "Failed to update line " + mLine.getLine() + " " + mLine.getM_Product().getName();
			}
		}
		return null;
	}
	
	protected String createReceivableOfWorker()
	{
		String payMethod = getWorkerResultType();
		if(MUNSResource.PAYMENTMETHOD_Daily.equals(payMethod))
			m_processMsg = createDailyReceivable();
		else if(MUNSResource.PAYMENTMETHOD_GroupResult.equals(payMethod))
			m_processMsg = createGroupResultReceivable();
		else if(MUNSResource.PAYMENTMETHOD_PersonalResult.equals(payMethod))
			m_processMsg = createPersonalResultReceivable();
		else
			m_processMsg = "Unknown worker result type " + payMethod;
		return m_processMsg;
	}
	
	protected String createWorkerAbsence()
	{
		MUNSResource rsc = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
		for(MUNSResourceWorkerLine rscWorker : rsc.getListOfWorker())
		{
			if(rscWorker.isAdditional())
				continue;
			
			MUNSBongkarMuatWorker worker  = getWorker(rscWorker.getLabor_ID());
			if(null != worker)
				continue;
			
			MUNSHalfPeriodPresence halfPresence =
					MUNSHalfPeriodPresence.get(getCtx(), rscWorker.getLabor_ID(), getDateAcct(), 
							rscWorker.getAD_Org_ID(), rsc.getResourceParent_ID(), get_TrxName());
			
			if(null == halfPresence)
				throw new AdempiereUserError("Not found worker periodic presence");
			
			if(!halfPresence.createAbsence(getDateAcct(), getUNS_Resource_ID()))
				return "Failed to create presence of worker";
		}
		return null;
	}
	
	public MUNSBongkarMuatWorker getWorker(int laborID)
	{
		for(MUNSBongkarMuatWorker bmWorker : getWorkers())
		{
			int labor_ID = bmWorker.getLabor_ID();
			if(bmWorker.getReplacementLabor_ID() > 0)
				labor_ID = bmWorker.getReplacementLabor_ID();
			if(labor_ID == laborID)
				return bmWorker;
		}
		return null;
	}
	
	public double getTotalQty()
	{
		double totalQty = 0.0;
		for(MUNSBongkarMuatLine mLine : getLines())
		{
			totalQty += mLine.getQty().doubleValue();
		}
		return totalQty;
	}
	
	private String generateCostOfProductLines()
	{
		double totalQty = getTotalQty();
		String payMethod = getWorkerResultType();
		double totalBiayaBongkar = getCostOfProductByDailyPay();
		double totalCost = 0.0;
		double biayaBongkar = 0.0;
		double cost = 0.0;
		for(MUNSBongkarMuatLine mLine : getLines())
		{			
			if (mLine.getTypeBongkar() == null || mLine.getTypeBongkar().equals("")) {
				setProcessed(false);
				mLine.setProcessed(false);
				throw new AdempiereException("You have not set Unloading Type for Line of " + mLine.getM_Product().getValue());
			}
			if(MUNSResource.PAYMENTMETHOD_GroupResult.equals(payMethod))
			{
				biayaBongkar = getCostProduct(mLine.getTypeBongkar(),
						mLine.getM_Product_ID(), mLine.getC_UOM_ID(), mLine.getAD_Org_ID())
						.doubleValue();
				cost = biayaBongkar;
				biayaBongkar *= mLine.getQty().doubleValue();
			}
			else if(MUNSResource.PAYMENTMETHOD_Daily.equals(payMethod))
			{
				double qty = mLine.getQty().doubleValue();
				double proportion = qty / totalQty;
				biayaBongkar = totalBiayaBongkar * proportion;
				cost = biayaBongkar / mLine.getQty().doubleValue();
			}
			else
			{
				m_processMsg = "Unknown Resource Payment Method " + payMethod;
				return m_processMsg;
			}
			mLine.setLineNetAmt(new BigDecimal(biayaBongkar));
			mLine.setPriceCost(new BigDecimal(cost));
			if(!mLine.save())
			{
				m_processMsg = "Failed to update line "
							+ mLine.getLine() + mLine.getM_Product().getName();
				return m_processMsg;
			}
			totalCost += biayaBongkar;
		}
		setTotalAmt(new BigDecimal(totalCost));
		return m_processMsg;
	}
	
	private double getCostOfProductByDailyPay()
	{
		double cost = 0.0;
		for(MUNSBongkarMuatWorker worker : getWorkers())
		{
			int labor_ID = worker.getLabor_ID();
			if(worker.getReplacementLabor_ID() > 0)
				labor_ID = worker.getReplacementLabor_ID();
//			MUNSResourceWorkerLine workerOfResource = MUNSResourceWorkerLine.getWorkerOfOrg(
//				getCtx(), getAD_Org_ID(), getLabor_ID(), get_TrxName());

			MUNSResourceWorkerLine workerOfResource = MUNSResourceWorkerLine.getWorker(
					getCtx(), getUNS_Resource_ID(), labor_ID, get_TrxName());
			
			if(null == workerOfResource)
				continue;
			
			MUNSProductionPayConfig payConfig =
					MUNSProductionPayConfig.get(
							getCtx(), 
							worker.getAD_Org_ID(), 
							getDateAcct(), 
							get_TrxName());
			if(null == payConfig)
				throw new AdempiereUserError("Not found production pay config");
			
			BigDecimal pay = BigDecimal.ZERO;
			
			if(MNonBusinessDay.isNonBusinessDay(getCtx(), getDateAcct(), getAD_Org_ID(), get_TrxName()))
			{
				pay = payConfig.getPayFullNationalHoliday(true);
				if(isOverTime())
					pay = payConfig.getOverTimeNationalHoliday(true);
			}
			else if(isNoWorkDay())
			{
				pay = payConfig.getPayFullHoliday(true);
				if(isOverTime())
					pay = payConfig.getOverTimeHoliday(true);
			}
			else
			{
				pay = payConfig.getPayFullDay(true);
				if(isOverTime())
					pay = payConfig.getPayOverTimePerHours(true);
			}
			
			worker.setReceivableAmt(pay);
			worker.save();
			cost += worker.getReceivableAmt().doubleValue();
		}
		return cost;
	}

}
