/**
 * 
 */
package com.unicore.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.MClient;
import org.compiere.model.MInOutLineMA;
import org.compiere.model.MLocator;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTransaction;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import com.uns.base.model.Query;
import com.uns.model.MProduct;
import com.uns.util.MessageBox;

import com.unicore.base.model.MInOutLine;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * 
 * 
 */
public class MUNSPLConfirm extends X_UNS_PL_Confirm implements DocAction, DocOptions
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7908608207497071993L;
	private boolean m_isMandatoryShipping = MSysConfig.getBooleanValue(
			MSysConfig.MANDATORY_SHIPPING_ON_PACKING, true);
	/**
	 * @param ctx
	 * @param UNS_PL_Confirm_ID
	 * @param trxName
	 */
	public MUNSPLConfirm(Properties ctx, int UNS_PL_Confirm_ID, String trxName)
	{
		super(ctx, UNS_PL_Confirm_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPLConfirm(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**************************************************************************
	 * Process document
	 * 
	 * @param processAction document action
	 * @return true if performed
	 */
	public boolean processIt(String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	} // processIt

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;
	private MUNSPLConfirmProduct[] m_Products;

	/**
	 * Unlock Document.
	 * 
	 * @return true if success
	 */
	public boolean unlockIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	} // unlockIt

	/**
	 * Invalidate Document
	 * 
	 * @return true if success
	 */
	public boolean invalidateIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	} // invalidateIt

	/**************************************************************************
	 * Prepare Document
	 * 
	 * @return new status (In Progress or Invalid)
	 */
	public String prepareIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// Lines
		MUNSPLConfirmProduct[] products = getProducts(true, null);
		if (products.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}

		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		setProcessed(true);

		return DocAction.STATUS_InProgress;
	} // prepareIt

	/**
	 * Approve Document
	 * 
	 * @return true if success
	 */
	public boolean approveIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	} // approveIt

	/**
	 * Reject Approval
	 * 
	 * @return true if success
	 */
	public boolean rejectIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	} // rejectIt

	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (!getUNS_PackingList().isPickup() && !isReversal() && getConfirmType().endsWith(CONFIRMTYPE_PackingListConfirmation))
		{
			MUNSShipping shipping = MUNSShipping.getShipping((MUNSPackingList) getUNS_PackingList());
			String sql = "SELECT COUNT(*) FROM UNS_ExpeditionDetail ed WHERE ed.UNS_PackingList_ID=?"
					+ " AND EXISTS(SELECT 1 FROM UNS_Expedition e WHERE e.DocStatus IN ('CO', 'CL')"
					+ " AND ed.UNS_Expedition_ID = e.UNS_Expedition_ID AND e.IsSOTrx = 'N')";
			int exists = DB.getSQLValue(get_TrxName(), sql, getUNS_PackingList_ID());
			if ((shipping == null || shipping.get_ID() == 0) && exists <= 0)
			{
				if (m_isMandatoryShipping)
				{
					m_processMsg = "Please create Shipping Document first OR Expedition.";
				}
				else
				{
					String msg = Msg.getMsg(getCtx(), "CompletePLWithoutShippingMsg");
					String title = Msg.getMsg(getCtx(), "CompletePLWithoutShippingTitle");
					int userChoise = MessageBox.showMsg(this, 
							getCtx(), msg, title, MessageBox.YESNO, MessageBox.ICONQUESTION);
					
					if(userChoise == MessageBox.RETURN_NO)
					{
						m_processMsg = "Please create Shipping Document first.";
					}
				}
			}
		}
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}
		
		if(isReversal())
		{
			m_processMsg = move();
			if (!Util.isEmpty(m_processMsg, true))
			{
				return DocAction.STATUS_Invalid;
			}
		}
		
		// Implicit Approval
		if (!isApproved())
			approveIt();

		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		setProcessed(true);
//		m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	} // completeIt

	/**
	 * 
	 * @param munsplConfirm
	 * @param shipping
	 * @param isShipment
	 * @return
	 */
	protected String move()
	{		
		StringBuilder diffConfirmedMsg = new StringBuilder();
		for (MUNSPLConfirmProduct cp : getProducts())
		{
			MLocator intransitLoc = null;
			Vector<Object[]> attrs = new Vector<>();
			MWarehouse wh = MWarehouse.get(getCtx(), cp.getM_Warehouse_ID());
			int M_AttributeSetInstance_ID = cp.getM_AttributeSetInstance_ID();
			MLocator[] locators = wh.getLocators(true);
			for(int i=0; i<locators.length; i++)
			{
				if(!locators[i].isActive())
					continue;
				if(!locators[i].getValue().equals(wh.getValue().concat("-").
						concat(MWarehouse.INITIAL_INTRANSIT_CUSTOMER_LOC)))
					continue;
				if(!locators[i].isInTransit())
					continue;
				intransitLoc = locators[i];
				break;
			}
			
			if(intransitLoc == null)
			{
				intransitLoc = new MLocator(wh, wh.getValue());
				intransitLoc.setValue(wh.getValue().concat("-").
						concat(MWarehouse.INITIAL_INTRANSIT_CUSTOMER_LOC));
				intransitLoc.setX(wh.getValue());
				intransitLoc.setY(wh.getName());
				intransitLoc.setZ(MWarehouse.INITIAL_INTRANSIT_CUSTOMER_LOC);
				intransitLoc.setIsInTransit(true);
				intransitLoc.saveEx();
			}
			
			if(M_AttributeSetInstance_ID > 0)
			{
				Object[] obj = new Object[3];
				obj[0] = cp.getM_AttributeSetInstance_ID();
				obj[1] = null;
				obj[2] = cp.getConfirmedQty();
				attrs.add(obj);
				if (!MStorageOnHand.add(getCtx(), cp.getM_Warehouse_ID(), cp.getM_Locator_ID(),
						cp.getM_Product_ID(), M_AttributeSetInstance_ID
						, (isShipment() ? cp.getConfirmedQty().negate() : cp.getConfirmedQty())
						, null, get_TrxName()))
				{
					{
						String lastError = CLogger.retrieveErrorString("");
						return "Cannot correct Inventory OnHand (MA) - " + lastError;
					}
				}

				// move to armada locator
				if (!MStorageOnHand.add(getCtx(), intransitLoc.getM_Warehouse_ID(), intransitLoc.get_ID()
						, cp.getM_Product_ID(), M_AttributeSetInstance_ID, 
						(isShipment() ? cp.getConfirmedQty() : cp.getConfirmedQty().negate())
						, null, get_TrxName()))
				{

					String lastError = CLogger.retrieveErrorString("");
					return "Cannot correct Inventory OnHand (MA) - " + lastError;
				}

				//
				MTransaction trxFrom =
						new MTransaction(getCtx(), cp.getAD_Org_ID(), MTransaction.MOVEMENTTYPE_PackedOut,
								cp.getM_Locator_ID(), cp.getM_Product_ID(),M_AttributeSetInstance_ID
								, (isShipment() ? cp.getConfirmedQty().negate() : cp.getConfirmedQty()),
								getDateDoc(), get_TrxName());
				trxFrom.setUNS_PL_ConfirmProduct_ID(cp.get_ID());
				if (!trxFrom.save())
				{
					return "Transaction From not inserted";
				}
				//
				MTransaction trxTo =
						new MTransaction(getCtx(), getAD_Org_ID(), MTransaction.MOVEMENTTYPE_PackedIn,
								intransitLoc.get_ID(), cp.getM_Product_ID(), M_AttributeSetInstance_ID
								, (isShipment() ? cp.getConfirmedQty() : cp.getConfirmedQty().negate())
								, getDateDoc(), get_TrxName());
				trxTo.setUNS_PL_ConfirmProduct_ID(cp.get_ID());
				if (!trxTo.save())
				{
					return "Transaction To not inserted";
				}
			}
			else
			{
				if(isShipment() && !isReversal())
					initialProductAttribute(cp);
				
				MUNSPLConfirmMA[] mas = cp.getMAs(true, null);
				for(MUNSPLConfirmMA ma : mas)
				{
					if (!MStorageOnHand.add(getCtx(), cp.getM_Warehouse_ID(), cp.getM_Locator_ID(),
							cp.getM_Product_ID(), ma.getM_AttributeSetInstance_ID()
							, (isShipment() ? ma.getMovementQty().negate() : ma.getMovementQty())
							, ma.getDateMaterialPolicy(), get_TrxName()))
					{
						{
							String lastError = CLogger.retrieveErrorString("");
							return "Cannot correct Inventory OnHand (MA) - " + lastError;
						}
					}

					// move to armada locator
					if (!MStorageOnHand.add(getCtx(), intransitLoc.getM_Warehouse_ID(), intransitLoc.get_ID(), cp.getM_Product_ID(),
							ma.getM_AttributeSetInstance_ID(), 
							(isShipment() ? ma.getMovementQty() : ma.getMovementQty().negate()), 
							ma.getDateMaterialPolicy(), get_TrxName()))
					{

						String lastError = CLogger.retrieveErrorString("");
						return "Cannot correct Inventory OnHand (MA) - " + lastError;
					}

					//
					MTransaction trxFrom =
							new MTransaction(getCtx(), cp.getAD_Org_ID(), MTransaction.MOVEMENTTYPE_PackedOut,
									cp.getM_Locator_ID(), cp.getM_Product_ID(), ma.getM_AttributeSetInstance_ID()
									, (isShipment() ? ma.getMovementQty().negate() : ma.getMovementQty()),
									getDateDoc(), get_TrxName());
					trxFrom.setUNS_PL_ConfirmProduct_ID(cp.get_ID());
					if (!trxFrom.save())
					{
						return "Transaction From not inserted";
					}
					//
					MTransaction trxTo =
							new MTransaction(getCtx(), getAD_Org_ID(), MTransaction.MOVEMENTTYPE_PackedIn,
									intransitLoc.get_ID(), cp.getM_Product_ID(), ma.getM_AttributeSetInstance_ID()
									, (isShipment() ? ma.getMovementQty() : ma.getMovementQty().negate())
									, getDateDoc(), get_TrxName());
					trxTo.setUNS_PL_ConfirmProduct_ID(cp.get_ID());
					if (!trxTo.save())
					{
						return "Transaction To not inserted";
					}
					
					if(isShipment())
					{
						Object[] obj = new Object[3];
						obj[0] = ma.getM_AttributeSetInstance_ID();
						obj[1] = ma.getDateMaterialPolicy();
						obj[2] = ma.getMovementQty();
						attrs.add(obj);
					}
				}
			}
			
			if(isShipment() && !isReversal())
			{
				for (Object[] obj : attrs) {
					if (null == obj) {
						continue;
					}
					
					BigDecimal movementQty = (BigDecimal) obj[2];
					Timestamp dateMPolicy = (Timestamp) obj[1];
					Integer asi_ID = (Integer) obj[0];
					
					MUNSPLConfirmLine[] lines = cp.getLines(true, null);
					for (int i=0; i<lines.length; i++) {
						MUNSPLConfirmLine line = lines[i];
						
						String sum = "SELECT COALESCE(SUM(MovementQty),0) FROM M_InOutLineMA WHERE M_InOutLine_ID=?";
						BigDecimal totalQtyMA = DB.getSQLValueBD(get_TrxName(), sum, line.getM_InOutLine_ID());
						if(totalQtyMA.compareTo(line.getQtyEntered()) == 0)
							continue;
						
						BigDecimal required = line.getQtyEntered().subtract(totalQtyMA);
						BigDecimal usedQty = Env.ZERO;
						if (required.compareTo(movementQty) == 1) {
							usedQty = movementQty;
							movementQty = Env.ZERO;
						} else {
							usedQty = required;
							movementQty = movementQty.subtract(required);
						}
						
						MInOutLine ioLine = new MInOutLine(getCtx(), line.getM_InOutLine_ID(), get_TrxName());
						MInOutLineMA ioMA = MInOutLineMA.addOrCreate(ioLine, asi_ID, usedQty, dateMPolicy, false);
						ioMA.saveEx();
						
						if (movementQty.signum()== 0) {
							break;
						} else if (required.signum() == 0) {
							break;
						}
					}
				}
			}
		}
		
		if (diffConfirmedMsg.length() > 0 && !isReversal())
		{
			StringBuilder warningMsg = new StringBuilder("There are different confirmed quantities for order of: \n ")
			.append(diffConfirmedMsg);
			String title = "Please confirm for new confirmed quantities!";
			
			int answered = MessageBox.showMsg(this, getCtx(), warningMsg.toString(), 
					title, MessageBox.OKCANCEL, MessageBox.ICONWARNING);
			
			if (answered == MessageBox.RETURN_CANCEL) {
				m_processMsg = new StringBuilder("Differences: ").append(diffConfirmedMsg).toString();
				return DOCSTATUS_InProgress;
			}
		}
		
		return null;
	}

	@Override
	public boolean voidIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before Void
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		
		if ("CO".equals(getDocStatus()) || "CL".equals(getDocStatus()))
		{
			String sql = "SELECT DocStatus FROM UNS_PackingList WHERE "
					+ " UNS_PackingList_ID = ?";
			String plStatus = DB.getSQLValueString(get_TrxName(), sql, 
					getUNS_PackingList_ID());
			if(DOCSTATUS_Completed.equals(plStatus))
			{
				return reverseCorrectIt();
			}
		}

		MUNSPLConfirmProduct[] productList = getProducts();
		
		for (MUNSPLConfirmProduct confirmProduct : productList)
		{
			MUNSPLConfirmLine[] confirmLineList = confirmProduct.getLines();
			
			for (MUNSPLConfirmLine plConfirmLine : confirmLineList)
			{
				plConfirmLine.setQtyEntered(Env.ZERO);
				plConfirmLine.setTargetQty(Env.ZERO);
				plConfirmLine.saveEx();
			}
			
			confirmProduct.setConfirmedQty(Env.ZERO);
			confirmProduct.setTargetQty(Env.ZERO);
			confirmProduct.setMovementQty(Env.ZERO);
			confirmProduct.setDifferenceQty(Env.ZERO);
			confirmProduct.saveEx();
		}
		
		addDescription(Msg.getMsg(getCtx(), "Voided"));

		// After Void
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);
		return true;
	}

	/**
	 * Add to Description
	 * 
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription

	@Override
	public boolean closeIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before Close

		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
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

		MUNSPLConfirm reversal = reverse(false);
		if (reversal == null)
			return false;
		else if(!reversal.getDocStatus().equals("RE") && !reversal.getDocStatus().equals("VO"))
		{
			throw new AdempiereException("ERROR.. PLEASE CONTACT ADMINISTRATOR SYSTEM Unta-ERP");
		}

		// After reverseAccrual
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this,
						ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		m_processMsg = reversal.getDocumentNo();
		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed);
		setDocAction(DOCACTION_None);
		
		return true;
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
		
		MUNSPLConfirm reversal = reverse(true);
		if (reversal == null)
			return false;
		else if(!reversal.getDocStatus().equals("RE") && !reversal.getDocStatus().equals("VO"))
		{
			throw new AdempiereException("ERROR.. PLEASE CONTACT ADMINISTRATOR SYSTEM Unta-ERP");
		}

		// After reverseAccrual
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this,
						ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		m_processMsg = reversal.getDocumentNo();
		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed);		//	 may come from void
		setDocAction(DOCACTION_None);
		
		return true;
	} // reverseAccrualIt
	
	private boolean m_reversal = false;
	
	public boolean isReversal()
	{
		return m_reversal;
	}
	
	private void setReversal (boolean reverse)
	{
		m_reversal = reverse;
	}

	/**
	 * 
	 * @return
	 */
	private MUNSPLConfirm reverse(boolean accrual) 
	{
		Timestamp dateDoc = getDateDoc();
		if (accrual)
			dateDoc = new Timestamp(System.currentTimeMillis());
		
		MUNSPLConfirm reversal = new MUNSPLConfirm(getCtx(), 0, get_TrxName());
		copyValues(this, reversal);
		reversal.setAD_Org_ID(getAD_Org_ID());
		if (getConfirmType().equals(CONFIRMTYPE_PackingListConfirmation))
			reversal.setUNS_PackingList_ID(getUNS_PackingList_ID());
		else
			reversal.setUNS_PL_Return_ID(getUNS_PL_Return_ID());
		reversal.setDocumentNo(getDocumentNo() + "^"); 
		reversal.setDateDoc(dateDoc);
		reversal.setReversal_ID(this.get_ID());
		reversal.setReversal(true);
		reversal.saveEx();
		
		MUNSPLConfirmProduct[] confirmProductList = getProducts();
		
		for (MUNSPLConfirmProduct plcp : confirmProductList)
		{
			MUNSPLConfirmProduct reversalProduct = new MUNSPLConfirmProduct(getCtx(), 0, get_TrxName());
			copyValues(plcp, reversalProduct);
			reversalProduct.setAD_Org_ID(plcp.getAD_Org_ID());
			reversalProduct.setUNS_PL_Confirm_ID(reversal.get_ID());
			reversalProduct.setM_Product_ID(plcp.getM_Product_ID());
			reversalProduct.setMovementQty(plcp.getMovementQty().negate());
			reversalProduct.setDifferenceQty(plcp.getDifferenceQty().negate());
			reversalProduct.setTargetQty(plcp.getTargetQty().negate());
			reversalProduct.setConfirmedQty(plcp.getConfirmedQty().negate());
			reversalProduct.setM_Warehouse_ID(plcp.getM_Warehouse_ID());
			reversalProduct.setM_Locator_ID(plcp.getM_Locator_ID());
			reversalProduct.setC_UOM_ID(plcp.getC_UOM_ID());
			reversalProduct.setM_AttributeSetInstance_ID(plcp.getM_AttributeSetInstance_ID());
			reversalProduct.saveEx();
			
			if (plcp.getM_AttributeSetInstance_ID() > 0)
				continue;
			
			MUNSPLConfirmMA[] maList = plcp.getMAs();
			
			for (MUNSPLConfirmMA ma : maList)
			{
				MUNSPLConfirmMA reversalMA = new MUNSPLConfirmMA(getCtx(), 0, get_TrxName());
				//copyValues(ma, reversalMA);
				if (ma.getM_AttributeSetInstance_ID() > 0)
					reversalMA.setM_AttributeSetInstance_ID(ma.getM_AttributeSetInstance_ID());
				
				reversalMA.setAD_Org_ID(ma.getAD_Org_ID());
				reversalMA.setMovementQty(ma.getMovementQty().negate());
				reversalMA.setDateMaterialPolicy(ma.getDateMaterialPolicy());
				reversalMA.setIsAutoGenerated(false);
				reversalMA.setUNS_PL_ConfirmProduct_ID(reversalProduct.get_ID());
				reversalMA.saveEx();
			}
			reversalProduct.createReversalLine(plcp);
		}
		
		if (!reversal.processIt(DocAction.ACTION_Complete))
		{
			m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
			return null;
		}
		
		reversal.closeIt();
		reversal.setProcessing (false);
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		reversal.saveEx(get_TrxName());
		//
		StringBuilder msgadd = new StringBuilder("(").append(reversal.getDocumentNo()).append("<-)");
		addDescription(msgadd.toString());
		
		//
		// Void Confirmations
		setDocStatus(DOCSTATUS_Reversed);
		saveEx();
		setReversal_ID(reversal.get_ID());

		return reversal;
	}

	@Override
	public boolean reActivateIt()
	{
		if (log.isLoggable(Level.INFO))
			log.info(toString());
		// Before reActivate
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		if(getConfirmType().equals(X_UNS_PL_Confirm.CONFIRMTYPE_ShipmentReturnConfirmation))
		{
			MUNSPLReturn result = new MUNSPLReturn(getCtx(), getUNS_PL_Return_ID(), get_TrxName());
			if(result.getDocStatus().equals(DOCSTATUS_Completed) || result.getDocStatus().equals(DOCSTATUS_Closed))
			{
				m_processMsg = "Packing List Return has processed..!!";
				return false;
			}
		}
		else
		{
			MUNSPackingList pl = new MUNSPackingList(getCtx(), getUNS_PackingList_ID(), get_TrxName());
			if(pl.getDocStatus().equals(DOCSTATUS_Completed) || pl.getDocStatus().equals(DOCSTATUS_Closed))
			{
				m_processMsg = "Packing List has processed..!!";
				return false;
			}
		}
		
		// After reActivate
		m_processMsg =
				ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
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
		sb.append(": ").append(Msg.translate(getCtx(), "Tonase")).append("=").append(getTonase().setScale(5, RoundingMode.HALF_UP))
				.append(Msg.translate(getCtx(), ", Volume")).append("=").append(getVolume().setScale(5, RoundingMode.HALF_UP));
		if (m_Products != null)
			sb.append(" (#").append(m_Products.length).append(")");
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
				new StringBuffer("MPLConfirm[").append(get_ID()).append("-").append(getDocumentNo())
						.append(", Tonase=").append(getTonase().setScale(3, RoundingMode.HALF_UP))
						.append(", Volume=").append(getVolume().setScale(3, RoundingMode.HALF_UP))
						.append("]");
		return sb.toString();
	} // toString

	/**
	 * Get Document Info
	 * 
	 * @return document info (untranslated)
	 */
	public String getDocumentInfo()
	{
		return "Packing List :" + getDocumentNo();
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
		return Env.ZERO;
	}

	@Override
	public int getC_Currency_ID()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**************************************************************************
	 * Get Orders
	 * 
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return orders
	 */
	public MUNSPLConfirmProduct[] getProducts(String whereClause, String orderClause)
	{
		// red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		StringBuilder whereClauseFinal =
				new StringBuilder(MUNSPLConfirmProduct.COLUMNNAME_UNS_PL_Confirm_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (Util.isEmpty(orderClause, true))
			orderClause = MUNSPLConfirmProduct.COLUMNNAME_UNS_PL_ConfirmProduct_ID;
		//
		List<MUNSPLConfirmProduct> list =
				Query
						.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPLConfirmProduct.Table_Name,
								whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID())
						.setOrderBy(orderClause).list();

		return list.toArray(new MUNSPLConfirmProduct[list.size()]);
	} // getLines

	/**
	 * Get Lines of Order
	 * 
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	public MUNSPLConfirmProduct[] getProducts(boolean requery, String orderBy)
	{
		if (m_Products != null && !requery)
		{
			set_TrxName(m_Products, get_TrxName());
			return m_Products;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;

		m_Products = getProducts(null, orderClause);
		return m_Products;
	} // getLines

	/**
	 * Get Lines of Order. (used by web store)
	 * 
	 * @return lines
	 */
	public MUNSPLConfirmProduct[] getProducts()
	{
		return getProducts(false, null);
	} // getLines

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		Timestamp datePL = null;
		if(getUNS_PL_Return_ID() > 0)
		     datePL = getUNS_PL_Return().getDateDoc();
		else
		     datePL = getUNS_PackingList().getDateDoc();
		
		if(!newRecord && getDateDoc().before(datePL))
		{
			log.saveError("Error", "Date confirm can't big from date return");
			return false;
		}
		return super.beforeSave(newRecord);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeDelete()
	 */
	@Override
	protected boolean beforeDelete()
	{
		for (MUNSPLConfirmProduct product : getProducts())
		{
			if (!product.delete(true))
				return false;
		}

		return super.beforeDelete();
	}
	
	public static MUNSPLConfirm get(MUNSPackingList pl)
	{
		MUNSPLConfirm retval = null;
		
		StringBuilder sql =
				new StringBuilder("SELECT ").append(COLUMNNAME_UNS_PL_Confirm_ID)
						.append(" FROM ").append(Table_Name).append(" WHERE ").append(COLUMNNAME_UNS_PackingList_ID)
						.append("=").append(pl.get_ID());
		int ID = DB.getSQLValue(pl.get_TrxName(), sql.toString());
		
		if (ID <=0)
			 return retval;
		retval =  new MUNSPLConfirm(pl.getCtx(), ID, pl.get_TrxName());
		 
		return retval;
	}

	/**
	 * 
	 * @param po {@link PO}
	 */
	public static String createConfirmation(PO po)
	{
		String retval = null;
		String headerColumnName = null;
		boolean packinglist = true;

		if (po.get_TableName().equalsIgnoreCase(MUNSPackingList.Table_Name))
		{
			headerColumnName = MUNSPackingList.COLUMNNAME_UNS_PackingList_ID;
		}
		else if (po.get_TableName().equalsIgnoreCase(MUNSPLReturn.Table_Name))
		{
			headerColumnName = MUNSPLReturn.COLUMNNAME_UNS_PL_Return_ID;
			packinglist = false;
		}

		StringBuilder sql =
				new StringBuilder("SELECT ").append(MUNSPLConfirm.COLUMNNAME_UNS_PL_Confirm_ID)
						.append(" FROM ").append(MUNSPLConfirm.Table_Name).append(" WHERE ").append(headerColumnName)
						.append("=").append(po.get_ID());
		int ID = DB.getSQLValue(po.get_TrxName(), sql.toString());

		MUNSPLConfirm confirm = new MUNSPLConfirm(po.getCtx(), ID == -1 ? 0 : ID, po.get_TrxName());
		if (ID > 0)
		{
			if (confirm.getDocStatus().equalsIgnoreCase(MUNSPLConfirm.DOCSTATUS_Completed)
					|| confirm.getDocStatus().equalsIgnoreCase(MUNSPLConfirm.DOCSTATUS_Closed))
				return retval;

			retval = "Please complete packing list confirmation #" + confirm.getDocumentNo() + " first.";
		}
		else
		{
			confirm.initConfirm(po);
			confirm.saveEx();

			if(!confirm.generateLines(po, packinglist)){
				confirm.deleteEx(true);
				
				return null;
			}
			retval =
					"Packing List Confirm has been created, Document No = ".concat(confirm.getDocumentNo());
		}

		return retval;
	}

	public void initConfirm(PO po)
	{
		setClientOrg(po);

//		setDateDoc(new Timestamp(System.currentTimeMillis()));
		setTonase(Env.ZERO);
		setVolume(Env.ZERO);

		if (po.get_TableName().equalsIgnoreCase(MUNSPackingList.Table_Name))
		{
			setUNS_PackingList_ID(po.get_ID());
			setConfirmType(CONFIRMTYPE_PackingListConfirmation);
			setDateDoc(Timestamp.valueOf(po.get_Value("DateDoc").toString()));
		}
		else if (po.get_TableName().equalsIgnoreCase(MUNSPLReturn.Table_Name))
		{
			setUNS_PL_Return_ID(po.get_ID());
			setConfirmType(CONFIRMTYPE_ShipmentReturnConfirmation);
			setDateDoc(Timestamp.valueOf(po.get_Value("DateDoc").toString()));
		}
	}

	private boolean generateLines(PO po, boolean isPackingList)
	{
		if (isPackingList)
		{
			MUNSPackingList packingList = (MUNSPackingList) po;
			for (MUNSPackingListOrder order : packingList.getOrders())
			{
				createlines(order);
			}
		}
		else
		{
			MUNSPLReturn plReturn = (MUNSPLReturn) po;
			setUNS_PackingList_ID(plReturn.getUNS_PackingList_ID());
			setUNS_PL_Return_ID(plReturn.getUNS_PL_Return_ID());
			saveEx();

			int counter = 0;
			for (MUNSPLReturnOrder order : plReturn.getROders())
			{
				if (!order.isCancelled())
					continue;
				if(order.isPartialCancelation())
				{
					createlines(order);
					counter++;
					continue;
				}
				counter++;
				MUNSPackingListOrder plo =
						new MUNSPackingListOrder(getCtx(), order.getUNS_PackingList_Order_ID(), get_TrxName());

				createlines(plo);
			}
			
			if (counter == 0)
				return false;
		}
		
		return true;
	}

	private void createlines(MUNSPackingListOrder plo)
	{
		for (MUNSPackingListLine line : plo.getLines())
		{
			MUNSPLConfirmProduct plCProduct = MUNSPLConfirmProduct.getNewConfirmProduct(line, this);
			plCProduct.setQty(line.getMovementQty());
			plCProduct.saveEx();
			MUNSPLConfirmLine plCLine = MUNSPLConfirmLine.getNewConfirmLine(line, plCProduct);
			plCLine.setLine(line);
			plCLine.setPartialCancel(false);
			plCLine.saveEx();
		}
	}
	
	private void createlines(MUNSPLReturnOrder ro)
	{
		for(MUNSPLReturnLine line : ro.getLines())
		{
			MUNSPLConfirmProduct plCProduct = MUNSPLConfirmProduct.getNewConfirmProduct(line, this);
			plCProduct.setQty(line.getCancelledQty());
			plCProduct.saveEx();
			MUNSPLConfirmLine plCLine = MUNSPLConfirmLine.getNewReturnConfirmLine(line, plCProduct);
			plCLine.setAD_Org_ID(line.getAD_Org_ID());
			plCLine.setUNS_PL_ConfirmProduct_ID(plCProduct.get_ID());
			plCLine.setQtyEntered(line.getCancelledQty());
			plCLine.setTargetQty(line.getCancelledQty());
			plCLine.setPartialCancel(true);
			plCLine.setM_Product_ID(line.getM_Product_ID());
			plCLine.saveEx();
		}
	}

	public void updateTonaseVolume()
	{
		BigDecimal tonase = Env.ZERO;
		BigDecimal volume = Env.ZERO;

		for (MUNSPLConfirmProduct product : getProducts())
		{
			tonase = tonase.add(product.getMovementQty().multiply(product.getM_Product().getGrossWeight())
							.multiply(new BigDecimal(0.001)));
			volume = volume.add(product.getMovementQty().multiply(product.getM_Product().getVolume())
							.multiply(new BigDecimal(0.001)).multiply(new BigDecimal(0.001)));
		}

		StringBuilder sb =
				new StringBuilder("UPDATE ").append(MUNSPLConfirm.Table_Name).append(" SET ")
						.append(MUNSPLConfirm.COLUMNNAME_Tonase).append("=").append(tonase).append(", ")
						.append(MUNSPLConfirm.COLUMNNAME_Volume).append("=").append(volume).append(" WHERE ")
						.append(MUNSPLConfirm.COLUMNNAME_UNS_PL_Confirm_ID).append("=")
						.append(get_ID());

		if (DB.executeUpdate(sb.toString(), get_TrxName()) == -1)
			throw new AdempiereException("Error when update parent.");

	}


	@Override
	public int customizeValidActions(String docStatus, Object processing, String orderType,
			String isSOTrx, int AD_Table_ID, String[] docAction, String[] options, int index)
	{
		if (docStatus.equals(DocAction.STATUS_Drafted))
		{
			options[index++] = DocAction.ACTION_Prepare;
		}
		else if (docStatus.equals(DocAction.STATUS_Completed))
		{
			options[index++] = DocAction.ACTION_ReActivate;
		}
		return index;
	}
	
	/**
	 * 
	 * @param cp
	 */
	private void initialProductAttribute(MUNSPLConfirmProduct cp)
	{
		if(!cp.deleteLinesMA())
			throw new AdempiereException("Can't delete old line MA");
		
		BigDecimal qty = cp.getConfirmedQty();
		qty = qty.subtract(MUNSPLConfirmMA.getTotalQty(true, cp.get_ID(), 
				get_TrxName()));
		
		Timestamp guarantedDate = cp.getParent().getDateDoc();
		MWarehouse wh = (MWarehouse) cp.getM_Warehouse();
		MProduct product = MProduct.get(getCtx(), cp.getM_Product_ID());
		
		int M_Locator_ID = 0;
		if ((isShipment() && !isReversal()) || (!isShipment() && isReversal()))
		{
			M_Locator_ID = cp.getM_Locator_ID();
		}
		else if ((!isShipment() && !isReversal()) || (isShipment() && isReversal()))
		{
			M_Locator_ID = DB.getSQLValue(get_TrxName(), 
					" SELECT M_Locator_ID FROM M_Locator WHERE Value = ? " + 
					" AND M_Warehouse_ID = ?"
					, wh.getValue().concat("-").concat(
							MWarehouse.INITIAL_INTRANSIT_CUSTOMER_LOC), 
							wh.get_ID());
		}
		
		MStorageOnHand[] storages = MStorageOnHand.getWarehouse(
				getCtx(), cp.getM_Warehouse_ID(), cp.getM_Product_ID(), 0, guarantedDate
				, MClient.MMPOLICY_FiFo.equals(product.getMMPolicy()), wh.isDisallowNegativeInv()
				, M_Locator_ID, get_TrxName(), true);
		
		MUNSPLConfirmMA[] manualMAs = cp.getMAs(true, 
				MUNSPLConfirmMA.COLUMNNAME_M_AttributeSetInstance_ID);
		
		for(MStorageOnHand storage : storages)
		{
			MUNSPLConfirmMA manualMA = null;
			for (MUNSPLConfirmMA ma : manualMAs)
			{
				if (ma.getM_AttributeSetInstance_ID() == storage.getM_AttributeSetInstance_ID()) {
					manualMA = ma;
					break;
				}
			}
			
			if (manualMA != null) {
				storage.setQtyOnHand(storage.getQtyOnHand().subtract(manualMA.getMovementQty()));
			}
			else
			{
				if (storage.getQtyOnHand().compareTo(qty) >= 0)
				{
					MUNSPLConfirmMA ma = MUNSPLConfirmMA.getCreate(
							cp, storage.getM_AttributeSetInstance_ID(), storage.getDateMaterialPolicy());
					ma.setUNS_PL_ConfirmProduct_ID(cp.get_ID());
					ma.setMovementQty(ma.getMovementQty().add(qty));
					ma.setIsAutoGenerated(true);
					ma.saveEx();
					qty = Env.ZERO;
				}
				else
				{
					MUNSPLConfirmMA ma = MUNSPLConfirmMA.getCreate(
							cp, storage.getM_AttributeSetInstance_ID(), storage.getDateMaterialPolicy());
					ma.setMovementQty(ma.getMovementQty().add(storage.getQtyOnHand()));
					ma.setUNS_PL_ConfirmProduct_ID(cp.get_ID());
					ma.setIsAutoGenerated(true);
					ma.saveEx();
					qty = qty.subtract(storage.getQtyOnHand());
					if (log.isLoggable(Level.FINE)) log.fine( ma + ", QtyToDeliver=" + qty);
				}
			}
			
			if (qty.signum() == 0)
				break;
		}
		
		if (qty.signum() != 0)
		{
			if (log.isLoggable(Level.WARNING)) log.fine("##difference : " + qty);
			MUNSPLConfirmMA ma = MUNSPLConfirmMA.getCreate(
					cp, cp.getM_AttributeSetInstance_ID(), getDateDoc());
			ma.setMovementQty(qty);
			ma.saveEx();
		}
	}
	
	public boolean isShipment()
	{
		return getConfirmType().endsWith(CONFIRMTYPE_PackingListConfirmation);
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	public static MUNSPLConfirm[] gets(PO model)
	{
		String whereclause = (new StringBuilder(model.get_TableName()).append("_ID = ?")).toString();
		List<MUNSPackingList> list = Query.get(
				model.getCtx(), UNSOrderModelFactory.EXTENSION_ID, Table_Name
				, whereclause, model.get_TrxName()).setParameters(model.get_ID())
				.setOrderBy(COLUMNNAME_DocumentNo).list();
		MUNSPLConfirm[] records = new MUNSPLConfirm[list.size()];
		list.toArray(records);
		return records;
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	public static String create(PO model)
	{
		boolean consolidateConfirm = false;
		String confirmType = "";
		if(model instanceof I_UNS_PackingList)
		{
			I_UNS_PackingList pl = (I_UNS_PackingList) model;
			consolidateConfirm = pl.isConsolidateConfirmation();
			confirmType = "Packing List";
		}
		else if(model instanceof I_UNS_PL_Return)
		{
			I_UNS_PL_Return plr = (I_UNS_PL_Return) model;
			String consolidate = DB.getSQLValueString(
					model.get_TrxName()
					, "SELECT ConsolidateConfirmation FROM UNS_PackingList WHERE UNS_PackingList_ID = ? "
					,plr.getUNS_PackingList_ID());
			consolidateConfirm = consolidate != null && consolidate.equals("Y");
			confirmType = "Packing List Return";
		}
		
		if(consolidateConfirm)
			return MUNSPLConfirm.createConfirmation(model);
		
		MUNSPLConfirm[] confirms = MUNSPLConfirm.gets(model);
		if(null == confirms || confirms.length == 0)
		{
			confirms = createConsolidateConfirm(model);
		}
		
		StringBuilder msg = new StringBuilder();
		
		for(MUNSPLConfirm confirm : MUNSPLConfirm.gets(model))
		{
			if(!confirm.getDocStatus().equals(MUNSPLConfirm.DOCSTATUS_Completed)
					&& !confirm.getDocStatus().equals(MUNSPLConfirm.DOCSTATUS_Closed))
			{	
				if(!Util.isEmpty(msg.toString(), true))
					msg.append(" && ");
				
				msg.append("Please complete " + confirmType + " warehouse confirmation --> ")
					.append(confirm.getDocumentNo());
			}
		}
		
		if(!Util.isEmpty(msg.toString(), true))
		{
			String title = Msg.getMsg(model.getCtx(), "Confirmation needed");
			MessageBox.showMsg(model, 
					model.getCtx(), msg.toString(), title, MessageBox.OK
					, MessageBox.ICONINFORMATION);
		}
		
		return msg.toString();
	}
	
	/**
	 * 
	 * @param model
	 */
	public static MUNSPLConfirm[] createConsolidateConfirm(PO model)
	{
		Hashtable<Integer, MUNSPLConfirm> mappingByWarehouse = new Hashtable<>();
		if(model instanceof I_UNS_PackingList)
		{
			MUNSPackingList packingList = (MUNSPackingList) model;
			for (MUNSPackingListOrder order : packingList.getOrders())
			{
				int M_Warehouse_ID = order.getM_Warehouse_ID();
				MUNSPLConfirm confirm = mappingByWarehouse.get(M_Warehouse_ID);
				if(null == confirm)
				{
					confirm = new MUNSPLConfirm(model.getCtx(), 0, model.get_TrxName());
					I_M_Warehouse whs = order.getM_Warehouse();
					confirm.initConfirm(model);
					//overwrite org
					confirm.setAD_Org_ID(whs.getAD_Org_ID());
					confirm.setAD_Client_ID(whs.getAD_Client_ID());
					confirm.saveEx();
					
					mappingByWarehouse.put(M_Warehouse_ID, confirm);
				}
								
				confirm.createlines(order);
			}
		}
		else if( model instanceof I_UNS_PL_Return)
		{
			MUNSPLReturn plReturn = (MUNSPLReturn) model;
			for (MUNSPLReturnOrder order : plReturn.getROders())
			{
				if (!order.isCancelled())
					continue;
				
				MUNSPackingListOrder plo =
						new MUNSPackingListOrder(
								model.getCtx(), order.getUNS_PackingList_Order_ID()
								, model.get_TrxName());

				int M_Warehouse_ID = plo.getM_Warehouse_ID();
				MUNSPLConfirm confirm = mappingByWarehouse.get(M_Warehouse_ID);
				if(null == confirm)
				{
					confirm = new MUNSPLConfirm(model.getCtx(), 0, model.get_TrxName());
					I_M_Warehouse whs = plo.getM_Warehouse();
					confirm.initConfirm(model);
					//overwrite org
					confirm.setAD_Org_ID(whs.getAD_Org_ID());
					confirm.setAD_Client_ID(whs.getAD_Client_ID());
					confirm.saveEx();
					
					mappingByWarehouse.put(M_Warehouse_ID, confirm);
				}

				confirm.createlines(plo);
			}
		}
		else
		{
			throw new AdempiereException("Unable to create instance. " + model.get_TableName());
		}
		
		MUNSPLConfirm[] confirms = new MUNSPLConfirm[mappingByWarehouse.size()];
		mappingByWarehouse.values().toArray(confirms);
		return confirms;
	}
}