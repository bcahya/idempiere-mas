/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.Env;

import com.uns.base.model.MOrder;
import com.uns.base.model.MOrderLine;


/**
 * @author menjangan
 *
 */
public class MUNSSER extends X_UNS_SER implements DocAction, DocOptions {
	
	private MUNSSERLineBuyer[] m_lines = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_SER_ID
	 * @param trxName
	 */
	public MUNSSER(Properties ctx, int UNS_SER_ID, String trxName) {
		super(ctx, UNS_SER_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSER(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static String cekBuyer(MOrder order)
	{
		String Msg = null;
		//TODO SER
//		int UNS_SER_ID = order.getUNS_SER_ID();
//		MUNSSER SER = new MUNSSER(order.getCtx(), UNS_SER_ID, order.get_TrxName());
//		MUNSSERLineBuyer[] mLines = SER.getLines();
//
//		boolean listedInSER = false;
//		
//		for (int i=0; i<mLines.length; i++)
//		{
//			MUNSSERLineBuyer mLine = mLines[i];
//			if (order.getC_BPartnerSR_ID() == mLine.getC_BPartner_ID())
//			{
//				listedInSER = true;
//				break;
//			}
//		}
//		if (!listedInSER)
//		{
//			Msg = "Buyer " + order.getC_BPartnerSR().getName() +
//					" Is Not Available In SER Line Buyer ";
//		}
		return Msg;
	}
	
	/**
	 * 
	 * @param SER_ID
	 * @param mProduct
	 * @param qty
	 * @param trxName
	 * @return
	 */
	public static String cekProductAvalability(MOrderLine OrderLine)
	{
		String Msg = null;
//		int M_Product_ID = OrderLine.getM_Product_ID();
//		int C_UOM_ID = OrderLine.getC_UOM_ID();
//		BigDecimal qty = OrderLine.getQtyEntered();
//		MOrder Order = OrderLine.getParent();
		//TODO SER
//		MUNSSER SER = new MUNSSER(Env.getCtx(), Order.getUNS_SER_ID(), OrderLine.get_TrxName());
//		MUNSSERLineBuyer[] linesBuyer = SER.getLines(false);
//		
//		boolean buyerExistInSER = false;
//		boolean availableProduct = false;
//		
//		for (int i=0; i<linesBuyer.length; i++)
//		{
//			MUNSSERLineBuyer lineBuyer = linesBuyer[i];
//			if (Order.getC_BPartnerSR_ID() != lineBuyer.getC_BPartner_ID())
//				continue;
//			buyerExistInSER = true;
//			MUNSSERLineProduct[] linesPack = lineBuyer.getLines(false);
//			
//			for (MUNSSERLineProduct linePack : linesPack)
//			{
//				if (M_Product_ID == linePack.getM_Product_ID())
//				{
//					if (C_UOM_ID != linePack.getC_UOM_ID())
//					{
//						BigDecimal conversion =
//								MUOMConversion.convertProductFrom(
//										OrderLine.getCtx(), M_Product_ID
//										, linePack.getC_UOM_ID(), qty);
//						if (null != conversion && conversion.signum() > 0)
//						{
//							qty = conversion;
//						}
//						else
//						{
//							Msg ="Can't Convert from " + OrderLine.getC_UOM().getName() 
//									+ " To " + linePack.getC_UOM().getName() 
//									+ " orderLine = " + OrderLine.getLine() 
//									+ "_" + OrderLine.getM_Product().getName();
//							return Msg;
//						}
//					}
//					BigDecimal restOfSERQty = linePack.getQty().subtract(linePack.getQtyOrdered()).subtract(qty);
//					if (restOfSERQty.compareTo(BigDecimal.ZERO) < 0)
//					{
//						Msg = "Quantity Un-ordered left in SER of product - " 
//								+linePack.getM_Product().getName()
//								+ " is less than the new order line. Please revise the order line quantity!";
//						return Msg;
//					}
//					availableProduct = true;
//				}
//			}
//		
//			if(!availableProduct)
//			{
//				MProduct product = MProduct.get(Env.getCtx(), M_Product_ID);
//				Msg = "No product " + product.getName()
//						+ " in Sales Enquiry " + Order.getUNS_SER_ID();
//				return Msg;
//			}
//			if (!buyerExistInSER)
//				Msg = "Buyer " + Order.getC_BPartnerSR().getName() +
//						" Is Not Available In SER Line Buyer ";
//			return Msg;
//		}
		return Msg;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	protected MUNSSERLineBuyer[] getLines(boolean requery)
	{
		if (m_lines != null
				&& !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSSERLineBuyer> mList =
				new Query(getCtx(), MUNSSERLineBuyer.Table_Name
						, MUNSSERLineBuyer.COLUMNNAME_UNS_SER_ID + " =?"
						, get_TrxName())
		.setParameters(getUNS_SER_ID())
		.setOrderBy(MUNSSERLineBuyer.COLUMNNAME_UNS_SERLineBuyer_ID)
		.list();
		
		m_lines = new MUNSSERLineBuyer[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSSERLineBuyer[] getLines()
	{
		return getLines(false);
	}
	
	protected boolean IsAvailableResource()
	{
		//nantinya akan berhubungan dengan manufacturing
		return true;
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
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action);
		
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
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
			m_justPrepared = true;
			
		if (!IsAvailableResource())
		{
			m_processMsg = "Resource Is Not Available";
			return DocAction.STATUS_Invalid;
		}
		
		//cek lines
		MUNSSERLineBuyer[] mLines = getLines();
		if (mLines == null || mLines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		
		for (int i=0; i<mLines.length; i++)
		{
			MUNSSERLineBuyer line = mLines[i];
			MUNSSERLineProduct[] mLinesSerPack = line.getLines();
			if (mLinesSerPack == null || mLinesSerPack.length == 0)
			{
				m_processMsg = "@NoLines@";
				return DocAction.STATUS_Invalid;
			}
			for (MUNSSERLineProduct lineSerPack : mLinesSerPack)
			{
				lineSerPack.setProcessed(true);
				lineSerPack.save();
			}
			line.setProcessed(true);
			line.save();
		}
		
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		setProcessed(true);
		return DocAction.STATUS_InProgress;
		
	}

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		
		setDateApproval(Env.getContextAsDate(getCtx(), "Date"));
		save();
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
		
		setProcessed(true);	
		
		if (!isApproved())
			approveIt();
//		m_processMsg = info.toString();
		//
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
				MUNSSERLineBuyer[] lines = getLines(false);
				for (int i = 0; i < lines.length; i++)
				{
					MUNSSERLineBuyer line = lines[i];
					MUNSSERLineProduct[] mLinesSerPack = line.getLines();
					for (MUNSSERLineProduct lineSerPack : mLinesSerPack)
					{
						BigDecimal oldqty = lineSerPack.getQtyAvailable();
						BigDecimal oldvol = lineSerPack.getVolume();
						BigDecimal oldPrice = lineSerPack.getPrice();
						
						if (oldPrice.signum() != 0
								|| oldvol.signum() != 0
								|| oldqty.signum() != 0)
						{
							lineSerPack.setQtyAvailable(Env.ZERO);
							lineSerPack.setVolume(Env.ZERO);
							lineSerPack.setPrice(Env.ZERO);
							lineSerPack.addDescription("Void ( " + oldPrice 
									+ " - " + oldqty + " - " + oldvol);
							lineSerPack.save(get_TrxName());
						}
					}
					BigDecimal old = line.getQtyAvailable();
					if (old.signum() != 0)
					{
						line.setQtyAvailable(Env.ZERO);
						line.addDescription("Void (" + old + ")");
						line.save(get_TrxName());
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
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
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
		return null;
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

}
