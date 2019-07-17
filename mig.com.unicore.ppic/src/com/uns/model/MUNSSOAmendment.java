/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

import com.uns.util.UNSTimeUtil;

import com.uns.base.model.*;
import com.uns.model.factory.UNSPPICModelFactory;
import com.uns.model.process.MPSUpdateGenerator;

/**
 * @author menjangan
 *
 */
public class MUNSSOAmendment extends X_UNS_SO_Amendment implements DocAction, DocOptions {

	/**
	 * 
	 */
	private MUNSSOAmendmentLine[] m_lines = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6103105705488769604L;

	/**
	 * @param ctx
	 * @param UNS_SO_Amendment_ID
	 * @param trxName
	 */
	public MUNSSOAmendment(Properties ctx, int UNS_SO_Amendment_ID,
			String trxName) {
		super(ctx, UNS_SO_Amendment_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSOAmendment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSSOAmendmentLine[] getLines(boolean requery)
	{
		if (null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		String whereClause = MUNSSOAmendmentLine.COLUMNNAME_UNS_SO_Amendment_ID + " = ?";
		
		List<MUNSSOAmendmentLine> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID()
				, MUNSSOAmendmentLine.Table_Name, whereClause, get_TrxName())
				.setParameters(getUNS_SO_Amendment_ID())
				.list();
		
		m_lines = new MUNSSOAmendmentLine[list.size()];
		return list.toArray(m_lines);
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
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		
		log.info(toString());
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
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
	
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
			m_justPrepared = true;
			
		MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		if(order.HasShipped())
		{
			m_processMsg = "Can't change SO . SO has been fully/partially shipped.";
			return DocAction.STATUS_Invalid;
		}
			
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		setProcessed(true);
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
		
		m_processMsg = updateSO();
		if (null != m_processMsg)
			return DocAction.STATUS_Invalid;
		setProcessed(true);	
		//m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		
		return false;
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
		
		return null;
	}

	@Override
	public String getDocumentNo() {
		
		return null;
	}
	
	public boolean deleteLines()
	{
		for(MUNSSOAmendmentLine SOAmendmentLine : getLines(true))
		{
			if(!SOAmendmentLine.delete(true))
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	protected String updateSO()
	{
		MUNSLCBalance lcBalance = null;		
		MPSUpdateGenerator mpsUpdateGenerator = new MPSUpdateGenerator(this);
		mpsUpdateGenerator.updateMPS(getMapOfAmendmentLines());
		MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		//FIXME add UNS_LC_Balance_ID
//		if(order.getUNS_LC_Balance_ID() > 0)
//		{
//			lcBalance = new MUNSLCBalance(getCtx(), order.getUNS_LC_Balance_ID(), get_TrxName());
//		}
		
		Hashtable<Integer, MOrderLine> mapOfOrderLine = order.getMapOfOrderLine();
		for(MUNSSOAmendmentLine amendmentLine : getLines(true))
		{
			MOrderLine orderLine = mapOfOrderLine.get(amendmentLine.getM_Product_ID());
			if(null == orderLine)
			{
				orderLine = new MOrderLine(getCtx(), 0, get_TrxName());
				orderLine.setAD_Org_ID(getAD_Org_ID());
				orderLine.setC_BPartner_ID(order.getC_BPartner_ID());
				orderLine.setC_Order_ID(order.get_ID());
				orderLine.setC_UOM_ID(amendmentLine.getC_UOM_ID());
				orderLine.setM_Product_ID(amendmentLine.getM_Product_ID());
			}
			orderLine.setQty(amendmentLine.getQuantity());
			orderLine.setQtyOrdered(amendmentLine.getQuantity());
			orderLine.setQtyEntered(amendmentLine.getQuantity());
			orderLine.setPrice(amendmentLine.getNewPrice());
			orderLine.setPriceActual(amendmentLine.getNewPrice());
			orderLine.setPriceEntered(amendmentLine.getNewPrice());
			orderLine.setPriceList(amendmentLine.getNewPrice());
			orderLine.setLineNetAmt(amendmentLine.getNewAmt());
			if(!orderLine.save())
				throw new AdempiereException("Failed to save Order Line");
			BigDecimal prevQtyOrdered = amendmentLine.getPrevQuantity();
			BigDecimal newQtyOrdered = amendmentLine.getQuantity();
			double prevOrderAmt = amendmentLine.getPrevAmt().doubleValue();
			double newOrderAmt = amendmentLine.getNewAmt().doubleValue();
			
			if(null != lcBalance)
			{
				MUNSLCLines lcLine = lcBalance.getLCLineOf(amendmentLine.getM_Product().getM_Product_Category_ID());
				if (null != lcLine)
				{
					double oldLCQty = lcLine.getSOQuantity().doubleValue();
					double oldLCAmt = lcLine.getSOAmount().doubleValue();
					double newQty = (oldLCQty - prevQtyOrdered.doubleValue()) + newQtyOrdered.doubleValue();
					double newAmt = (oldLCAmt - prevOrderAmt) + newOrderAmt;
					lcLine.setSOAmount(new BigDecimal(newAmt));
					lcLine.setSOQuantity(new BigDecimal(newQty));
					lcLine.save();
				}
			}
			
		//FIXME add isSyncWithMPS
			// Update MPS if necessary.
//			if (!orderLine.isSyncWithMPS())
//				continue;
			//else update the appropriate MPS.
			updateMPS(orderLine, newQtyOrdered);
		}
		return null;
	}

	
	/**
	 * 
	 * @return
	 */
	public Hashtable<Integer, MUNSSOAmendmentLine> getMapOfAmendmentLines()
	{
		Hashtable<Integer, MUNSSOAmendmentLine> map = new Hashtable<Integer, MUNSSOAmendmentLine>();
		for (MUNSSOAmendmentLine amendmentLine : getLines(false))
		{
			map.put(amendmentLine.getM_Product_ID(), amendmentLine);
		}
		return map;
	}
	
	/**
	 * 
	 * @param soLine
	 * @return
	 */
	String updateMPS(MOrderLine soLine, BigDecimal newQtyOrdered)
	{
		I_M_Product product = soLine.getM_Product();
		String msg = "SO Line of product [" + product.getValue() + "] has been sycn to MPS.";
		
		BigDecimal prevQtyOrderedUOM = soLine.getQtyEntered();
		BigDecimal prevQtyOrderedMT = prevQtyOrderedUOM.multiply(product.getWeight()).multiply(new BigDecimal(0.001));
		BigDecimal newQtyOrderedMT = newQtyOrdered.multiply(product.getWeight()).multiply(new BigDecimal(0.001));
		
		Timestamp datePromised = soLine.getC_Order().getDatePromised();
		if (null == datePromised)
			datePromised = soLine.getC_Order().getDatePromised();
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(datePromised.getTime());
		int month = cal.get(Calendar.MONTH);

		int weekNo = UNSTimeUtil.getProductionWeekNo(datePromised);
		
		// Create the MPS Product Tab for the SO Line.
		MUNSMPSProduct[] mpsProductList = MUNSMPSProduct.get(getCtx(), 
													   datePromised, 
													   soLine.getM_Product_ID(), 
													   get_TrxName());
		for (MUNSMPSProduct mpsProduct : mpsProductList) 
		{
			// subtract it with previous ordered qty.
			mpsProduct.setTotalActualToOrderUOM(mpsProduct.getTotalActualToOrderUOM().subtract(prevQtyOrderedUOM));
			mpsProduct.setTotalActualToOrderMT(mpsProduct.getTotalActualToOrderMT().subtract(prevQtyOrderedMT));
			
			mpsProduct.setActualToOrderUOM(month, mpsProduct.getActualToOrderUOM(month).subtract(prevQtyOrderedUOM));
			mpsProduct.setActualToOrderMT(month, mpsProduct.getActualToOrderMT(month).subtract(prevQtyOrderedMT));
			
			// add it with new ordered qty.
			mpsProduct.setTotalActualToOrderUOM(mpsProduct.getTotalActualToOrderUOM().add(newQtyOrdered));
			mpsProduct.setTotalActualToOrderMT(mpsProduct.getTotalActualToOrderMT().add(newQtyOrderedMT));
			
			mpsProduct.setActualToOrderUOM(month, mpsProduct.getActualToOrderUOM(month).add(newQtyOrdered));
			mpsProduct.setActualToOrderMT(month, mpsProduct.getActualToOrderMT(month).add(newQtyOrderedMT));
			
			if (mpsProduct.getWeekToBeUpdated() != 0 && mpsProduct.getWeekToBeUpdated() > weekNo)
				mpsProduct.setWeekToBeUpdated(weekNo);
			
			if (mpsProduct.save())
				throw new AdempiereException("Failed when create new MPS Product.");
			
			// Create the MPS Product Weekly Tab for the SO Line.
			MUNSMPSProductWeekly mpsProductWeekly =
					MUNSMPSProductWeekly.get(getCtx(), mpsProduct, weekNo, get_TrxName());
			
			mpsProductWeekly.setActualToOrderUOM(mpsProductWeekly.getActualToOrderUOM().subtract(prevQtyOrderedUOM));
			mpsProductWeekly.setActualToOrderMT(mpsProductWeekly.getActualToOrderMT().subtract(prevQtyOrderedMT));

			mpsProductWeekly.setActualToOrderUOM(mpsProductWeekly.getActualToOrderUOM().add(newQtyOrdered));
			mpsProductWeekly.setActualToOrderMT(mpsProductWeekly.getActualToOrderMT().add(newQtyOrderedMT));
			
			if (mpsProductWeekly.save())
				throw new AdempiereException("Failed when create new MPS Product Weekly.");
			
			MUNSMPSHeader mpsHeader = (MUNSMPSHeader) mpsProduct.getUNS_MPSHeader();
	
			if (!mpsHeader.isSyncDatabase())
			{
				mpsHeader.setIsSyncDatabase(true);
	
				if (!mpsHeader.save())
					throw new AdempiereException("Failed when updating MPS based on SO amandment.");
			}
		}
		//FIXME add isSyncWithMPS
//		soLine.setIsSyncWithMPS(true);
		if (!soLine.save())
			throw new AdempiereException ("Failed when updating SO Line-MPS sychronize status.");

		return msg;
	}
}
