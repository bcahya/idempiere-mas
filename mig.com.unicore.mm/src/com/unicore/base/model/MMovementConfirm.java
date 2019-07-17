/**
 * 
 */
package com.unicore.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.PeriodClosedException;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MLocator;
import org.compiere.model.MMovementLineConfirm;
import org.compiere.model.MMovementLineMA;
import org.compiere.model.MOrg;
import org.compiere.model.MPeriod;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTransaction;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.uns.base.model.Query;
import com.uns.model.MProduct;

import com.unicore.model.factory.UniCoreMaterialManagementModelFactory;

/**
 * @author MENJANGAN
 *
 */
public class MMovementConfirm extends org.compiere.model.MMovementConfirm implements DocOptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String REVERSE_INDICATOR = "^_^";
	private boolean m_isReversal = false;

	/**
	 * @param ctx
	 * @param M_MovementConfirm_ID
	 * @param trxName
	 */
	public MMovementConfirm(Properties ctx, int M_MovementConfirm_ID,
			String trxName) {
		super(ctx, M_MovementConfirm_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMovementConfirm(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * @param move
	 */
	public MMovementConfirm(MMovement move) {
		super(move);
	}
	
	
	/**
	 * 
	 * @param move
	 * @param checkExisting
	 * @return
	 */
	public static MMovementConfirm create (
			MMovement move
			, boolean checkExisting
			, String ConfirmationType)
	{
		if (checkExisting)
		{
			MMovementConfirm[] confirmations = move.getConfirmations(false);
			if (confirmations.length > 0)
			{
				MMovementConfirm confirm = confirmations[0];
				return confirm;
			}
		}

		MMovementConfirm confirm = new MMovementConfirm (move);
		confirm.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_MaterialMovementConfirmation));
		
		if(MMovementConfirm.CONFIRMATIONTYPE_DestinationWarehouseConfirmation
				.equals(ConfirmationType))
		{
			confirm.setConfirmationType(ConfirmationType);
			confirm.setAD_Org_ID(move.getDestinationWarehouse().getAD_Org_ID());
		}
		else if(MMovementConfirm.CONFIRMATIONTYPE_InternalWarehouseConfirmation
				.equals(ConfirmationType))
		{
			confirm.setConfirmationType(ConfirmationType);
			confirm.setAD_Org_ID(move.getAD_Org_ID());
		}
		else if(MMovementConfirm.CONFIRMATIONTYPE_HeadOfficeConfirmation
				.equals(ConfirmationType))
		{
			MOrg orgFrom = new MOrg(Env.getCtx(), move.getAD_Org_ID(), move.get_TrxName());
			MOrg parent = orgFrom.getParent();
			
			if(null == parent)
				return null;
			
			if(parent.getAD_Org_ID() == move.getDestinationWarehouse().getAD_Org_ID())
				return null;
			
			confirm.setAD_Org_ID(parent.get_ID());
			confirm.setConfirmationType(MMovementConfirm.CONFIRMATIONTYPE_HeadOfficeConfirmation);
		}
		
		confirm.setReceiptDate(move.getMovementDate());
		confirm.saveEx(move.get_TrxName());
		
		MMovementLine[] moveLines = move.getLines(false);
		for (int i = 0; i < moveLines.length; i++)
		{
			MMovementLine mLine = moveLines[i];
			MMovementLineConfirm cLine = new MMovementLineConfirm (confirm);
			cLine.setMovementLine(mLine);
			cLine.saveEx(move.get_TrxName());
		}
		return confirm;
	}	
	
	@Override
	public String prepareIt()
	{
		MMovementLineConfirm[] lines = getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			if (!lines[i].isFullyConfirmed() 
					&& !isDestinationWarehouseConfirm() && !isReversal())
			{
				m_processMsg = "Can't process document with quantity difference";
				return DocAction.STATUS_Invalid;
			}
		}
		return super.prepareIt();
	}
	
	
	@Override
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

		//	Implicit Approval
		if (!isApproved())
			approveIt();
		if (log.isLoggable(Level.INFO)) log.info("completeIt - " + toString());
		//
		m_inventoryDoc = new ArrayList<MInventory>();
		MMovement move = new MMovement (getCtx(), getM_Movement_ID(), get_TrxName());
		MMovementLineConfirm[] lines = getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MMovementLineConfirm confirm = lines[i];
			confirm.set_TrxName(get_TrxName());
			if (!isDestinationWarehouseConfirm() && !confirm.processLine ())
			{
				m_processMsg = "ShipLine not saved - " + confirm;
				return DocAction.STATUS_Invalid;
			}
			if ((confirm.isFullyConfirmed() && confirm.getScrappedQty().signum() == 0)
					|| isDestinationWarehouseConfirm())
			{
				confirm.setProcessed(true);
				confirm.saveEx(get_TrxName());
			}
			else
			{
				log.log(Level.SEVERE, "completeIt - Scrapped=" + confirm.getScrappedQty()
					+ " - Difference=" + confirm.getDifferenceQty());
				
				if (m_processMsg == null)
					m_processMsg = "Differnce Doc not created";
				return DocAction.STATUS_Invalid;
			}
		}	//	for all lines
		
		if(isDestinationWarehouseConfirm())
		{
			if(move.isComplete()) {
				String returnStatus = moveToDestinationLocator();
				if (returnStatus != null) {
					return returnStatus;
				}
			}
			else
			{
				m_processMsg = "Can't complete destination warehouse confirmation " +
						"because the movement document in status " + move.getDocStatus();
				return DocAction.STATUS_Invalid;
			}
		}
		
		
		//complete movement
		setProcessed(true);
		saveEx();
		if(!isDestinationWarehouseConfirm())
		{
			if(!MMovement.DOCSTATUS_Completed.equals(move.getDocStatus()))
			{
				boolean ok = move.processIt(DocAction.ACTION_Complete);
				if(!ok) {
					m_processMsg = move.getProcessMsg();
					return DocAction.STATUS_Invalid;
				}
				
				if (m_inventoryInfo != null)
				{
					//complete inventory doc
					for(MInventory inventory : m_inventoryDoc)
					{
						ok = inventory.processIt(DocAction.ACTION_Complete);
						if (!ok) 
						{
							m_processMsg = inventory.getProcessMsg();
							return DocAction.STATUS_Invalid;
						}
					}
					
					if(MMovementConfirm.CONFIRMATIONTYPE_InternalWarehouseConfirmation
							.equals(getConfirmationType()))
					{
						move.setMovementDate(getReceiptDate());
						move.save();
					}
					
					m_processMsg = " @M_Inventory_ID@: " + m_inventoryInfo;
					addDescription(Msg.translate(getCtx(), "M_Inventory_ID") 
						+ ": " + m_inventoryInfo);
				}	
			}
		}		
		
		m_inventoryDoc = null;
		
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			setProcessed(false);
			return DocAction.STATUS_Invalid;
		}
		
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt
	
	private String moveToDestinationLocator()
	{
		MMovementLineConfirm[] lines = getLines(false);
		for(MMovementLineConfirm line : lines)
		{
			MTransaction trxFrom = null;
			MMovementLine lineMove = new MMovementLine(
					getCtx(), line.getM_MovementLine_ID(), get_TrxName());
			
			MLocator locatorTo = (MLocator)lineMove.getM_LocatorTo();
			MLocator locatorFrom = lineMove.getIntransitLocator(false);

			if(null == locatorFrom)
			{
				m_processMsg = "Locator From is not defined, " +
						"if this document is intransit material movement maybe the intransit locator has ben renamed or not created. " +
						"Please contact the administrator to fix it.";
				return DocAction.STATUS_Invalid;
			}
			
			if(lineMove.getM_AttributeSetInstance_ID() > 0
					&& lineMove.getM_AttributeSetInstanceTo_ID() == 0)
			{
				m_processMsg = "Atribut set instance to must be defined if you chose attribute set instance";
				return DocAction.STATUS_Invalid;
			}
			
			if(lineMove.getM_AttributeSetInstanceTo_ID() == 0)
			{
				MMovementLineMA[] moveLineMA = MMovementLineMA.get(
						getCtx(), lineMove.get_ID(), get_TrxName());
				
				BigDecimal tobeConfirmedQty = line.getConfirmedQty();
				
				for(int i=0; i< moveLineMA.length; i++)
				{
					BigDecimal maQty = moveLineMA[i].getMovementQty();
					if (tobeConfirmedQty.compareTo(maQty) < 0)
						maQty = tobeConfirmedQty;
						
					//Update Storage 
					if (!MStorageOnHand.add(getCtx(),locatorFrom.getM_Warehouse_ID(),
							locatorFrom.getM_Locator_ID(),
							lineMove.getM_Product_ID(), 
							moveLineMA[i].getM_AttributeSetInstance_ID(),
							maQty.negate(),
							moveLineMA[i].getDateMaterialPolicy(), 
							get_TrxName()))
					{
						String lastError = CLogger.retrieveErrorString("");
						m_processMsg = "Cannot correct Inventory OnHand (MA) - " + lastError + " of product "
								+ MProduct.get(getCtx(), lineMove.getM_Product_ID());
						return DocAction.STATUS_Invalid;
					}

					//Update Storage 
					if (!MStorageOnHand.add(getCtx(),locatorTo.getM_Warehouse_ID(),
							locatorTo.getM_Locator_ID(),
							lineMove.getM_Product_ID(), 
							moveLineMA[i].getM_AttributeSetInstance_ID(),
							maQty,
							moveLineMA[i].getDateMaterialPolicy(), 
							get_TrxName()))
					{
						String lastError = CLogger.retrieveErrorString("");
						m_processMsg = "Cannot correct Inventory OnHand (MA) - " + lastError + " of product "
								+ MProduct.get(getCtx(), lineMove.getM_Product_ID());
						return DocAction.STATUS_Invalid;
					}
					
					trxFrom = new MTransaction (
							getCtx(), 
							locatorFrom.getAD_Org_ID(), 
							MTransaction.MOVEMENTTYPE_MovementFrom,
							locatorFrom.getM_Locator_ID(), 
							lineMove.getM_Product_ID(), 
							moveLineMA[i].getM_AttributeSetInstance_ID(),
							maQty.negate(), 
							getReceiptDate(), 
							get_TrxName());
					trxFrom.setM_MovementLine_ID(line.getM_MovementLine_ID());
					if (!trxFrom.save())
					{
						m_processMsg = "Transaction From not inserted";
						return DocAction.STATUS_Invalid;
					}
					//
					MTransaction trxTo = new MTransaction (
							getCtx(), 
							locatorTo.getAD_Org_ID(), 
							MTransaction.MOVEMENTTYPE_MovementTo,
							locatorTo.getM_Locator_ID(), 
							lineMove.getM_Product_ID(), 
							moveLineMA[i].getM_AttributeSetInstance_ID(),
							maQty, 
							getReceiptDate(), 
							get_TrxName());
					trxTo.setM_MovementLine_ID(line.getM_MovementLine_ID());
					if (!trxTo.save())
					{
						m_processMsg = "Transaction To not inserted";
						return DocAction.STATUS_Invalid;
					}
					
					tobeConfirmedQty = tobeConfirmedQty.subtract(maQty);
					if (tobeConfirmedQty.signum() <= 0)
						break;
				}
			}
			
			if(trxFrom == null)
			{
				MAttributeSetInstance asi = new MAttributeSetInstance(
						getCtx(), lineMove.getM_AttributeSetInstanceTo_ID(), get_TrxName());
				Timestamp datePolicy = asi.getCreated();
				//Update Storage 
				if (!MStorageOnHand.add(getCtx(),locatorFrom.getM_Warehouse_ID(),
						locatorFrom.getM_Locator_ID(),
						lineMove.getM_Product_ID(), 
						lineMove.getM_AttributeSetInstanceTo_ID(),
						line.getConfirmedQty().negate(),
						datePolicy, 
						get_TrxName()))
				{
					String lastError = CLogger.retrieveErrorString("");
					m_processMsg = "Cannot correct Inventory OnHand (MA) - " + lastError + " of product "
							+ MProduct.get(getCtx(), lineMove.getM_Product_ID());
					return DocAction.STATUS_Invalid;
				}

				//Update Storage 
				if (!MStorageOnHand.add(getCtx(),locatorTo.getM_Warehouse_ID(),
						locatorTo.getM_Locator_ID(),
						lineMove.getM_Product_ID(), 
						lineMove.getM_AttributeSetInstanceTo_ID(),
						line.getConfirmedQty(),
						datePolicy, 
						get_TrxName()))
				{
					String lastError = CLogger.retrieveErrorString("");
					m_processMsg = "Cannot correct Inventory OnHand (MA) - " + lastError + " of product "
							+ MProduct.get(getCtx(), lineMove.getM_Product_ID());
					return DocAction.STATUS_Invalid;
				}

				//
				trxFrom = new MTransaction (
						getCtx(), 
						locatorFrom.getAD_Org_ID(), 
						MTransaction.MOVEMENTTYPE_MovementFrom,
						locatorFrom.getM_Locator_ID(), 
						lineMove.getM_Product_ID(), 
						lineMove.getM_AttributeSetInstanceTo_ID(),
						line.getConfirmedQty().negate(), 
						getReceiptDate(), 
						get_TrxName());
				trxFrom.setM_MovementLine_ID(line.getM_MovementLine_ID());
				if (!trxFrom.save())
				{
					m_processMsg = "Transaction From not inserted";
					return DocAction.STATUS_Invalid;
				}
				//
				MTransaction trxTo = new MTransaction (
						getCtx(), 
						locatorTo.getAD_Org_ID(), 
						MTransaction.MOVEMENTTYPE_MovementTo,
						locatorTo.getM_Locator_ID(), 
						lineMove.getM_Product_ID(), 
						lineMove.getM_AttributeSetInstanceTo_ID(),
						line.getConfirmedQty(), 
						getReceiptDate(), 
						get_TrxName());
				trxTo.setM_MovementLine_ID(line.getM_MovementLine_ID());
				if (!trxTo.save())
				{
					m_processMsg = "Transaction To not inserted";
					return DocAction.STATUS_Invalid;
				}
			}

		}
		
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String generateInventoryLostInvoice(int M_PriceList_ID)
	{
		String retVal = "";
		if(!DOCSTATUS_Completed.equals(getDocStatus()))
			retVal = "Can't create intransit claim. Please complete intransit confirmation first.";
		else if(!isDestinationWarehouseConfirm())
			retVal = "Can't create intransit claim. The confirmation hasn't destination warehouse document.";
		else if(isHasCreatedClaim())
			retVal = "Claim has been created";
		else if(isFullyConfirmed())
			retVal = "can't create claim for fully confirmed document";
		else
			retVal = createInventoryLostClaim(M_PriceList_ID);
		return retVal;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean isHasCreatedClaim()
	{
		return getC_Invoice_ID() > 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isFullyConfirmed()
	{
		MMovementLineConfirm[] lineConfirms = getLines(false);
		for(MMovementLineConfirm lineConfirm : lineConfirms)
		{
			if(lineConfirm.getDifferenceQty().compareTo(BigDecimal.ZERO) > 0)
				return false;
			if(lineConfirm.getScrappedQty().compareTo(BigDecimal.ZERO) > 0)
				return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * @return
	 */
	private String createInventoryLostClaim(int M_PriceList_ID)
	{
		MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
		invoice.setM_PriceList_ID(M_PriceList_ID);
		return invoice.createInventoryLostInvoice(this);
	}
	
	
	public static MMovementConfirm getOfInvoice(int M_invoice_ID, String trxName)
	{
		return Query.get(
				Env.getCtx(), 
				UniCoreMaterialManagementModelFactory.EXTENSION_ID, 
				Table_Name, 
				COLUMNNAME_C_Invoice_ID + "=?", 
				trxName).
				setParameters(M_invoice_ID).
				firstOnly();
	}
	
	
	/**
	 * Return true if this warehouse with confirmation type CDW
	 * @return boolean true if this warehouse is destination
	 */
	public boolean isDestinationWarehouseConfirm()
	{
		return MMovementConfirm.CONFIRMATIONTYPE_DestinationWarehouseConfirmation
				.equals(getConfirmationType());
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isParentOrgConfirmation()
	{
		return MMovementConfirm.CONFIRMATIONTYPE_HeadOfficeConfirmation
				.equals(getConfirmationType());
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isInternalWarehouseConfirmation()
	{
		return MMovementConfirm.CONFIRMATIONTYPE_InternalWarehouseConfirmation
				.equals(getConfirmationType());
	}
	
	@Override
	public boolean voidIt ()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		if (DOCSTATUS_Closed.equals(getDocStatus())
			|| DOCSTATUS_Reversed.equals(getDocStatus())
			|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}
		
		if (isDestinationWarehouseConfirm() 
				&& DOCSTATUS_Completed.equals(getDocStatus()))
		{
			boolean accrual = false;
			try 
			{
				MPeriod.testPeriodOpen(getCtx(), getReceiptDate(),
						getC_DocType_ID(), getAD_Org_ID());
			}
			catch (PeriodClosedException e) 
			{
				accrual = true;
			}
			
			if (accrual)
				return reverseAccrualIt();
			else
				return reverseCorrectIt();
		}
		else
		{
			// Before Void
			m_processMsg = ModelValidationEngine.get().fireDocValidate(
					this,ModelValidator.TIMING_BEFORE_VOID);
			if (m_processMsg != null)
				return false;
			
			//	Set lines to 0
			MMovementLineConfirm[] lines = getLines(false);
			for (int i = 0; i < lines.length; i++)
			{
				MMovementLineConfirm line = lines[i];
				BigDecimal old = line.getConfirmedQty();
				if (old.compareTo(Env.ZERO) != 0)
				{
					line.setConfirmedQty(Env.ZERO);
					line.addDescription(" <> Void (" + old + ")");
					line.saveEx(get_TrxName());
				}
			}
		}
	
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
			
		setProcessed(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);
		return true;
	}
	
	@Override
	public boolean reverseCorrectIt ()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());

		if (!isDestinationWarehouseConfirm())
			return voidIt();
		
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		MMovementConfirm reversal = reverse(false);
		if (reversal == null)
			return false;
		
		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		return true;
	}
	
	@Override
	public boolean reverseAccrualIt ()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());

		if (!isDestinationWarehouseConfirm())
			return voidIt();
		
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		MMovementConfirm reversal = reverse(true);
		if (reversal == null)
			return false;
		
		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		return true;
	}
	
	
	private MMovementConfirm reverse (boolean accrual)
	{
		Timestamp reversalDate = accrual ? Env.getContextAsDate(getCtx(), "#Date")
				: getReceiptDate();
		if (reversalDate == null) {
			reversalDate = new Timestamp(System.currentTimeMillis());
		}
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (!MPeriod.isOpen(getCtx(), reversalDate, dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return null;
		}

		//	Deep Copy
		MMovementConfirm reversal = new MMovementConfirm(
				getCtx(), 0, get_TrxName());
		copyValues(this, reversal, getAD_Client_ID(), getAD_Org_ID());
		reversal.setC_DocType_ID(getC_DocType_ID());
		reversal.setDocStatus(DOCSTATUS_Drafted);
		reversal.setDocAction(DOCACTION_Complete);
		reversal.setConfirmationType(getConfirmationType());
		reversal.setIsApproved (false);
		reversal.setPosted(false);
		reversal.setProcessed(false);
		reversal.setReceiptDate(reversalDate);
		reversal.setDocumentNo(getDocumentNo() + REVERSE_INDICATOR);	//	indicate reversals
		reversal.addDescription("{->" + getDocumentNo() + ")");
		reversal.setReversal_ID(getM_MovementConfirm_ID());
		if (!reversal.save())
		{
			m_processMsg = "Could not create Movement Confirm Reversal";
			return null;
		}
		reversal.setReversal(true);
		//	Reverse Line Qty
		MMovementLineConfirm[] oLines = getLines(true);
		for (int i = 0; i < oLines.length; i++)
		{
			MMovementLineConfirm oLine = oLines[i];
			MMovementLineConfirm rLine = new MMovementLineConfirm(
					getCtx(), 0, get_TrxName());
			copyValues(oLine, rLine, oLine.getAD_Client_ID(), oLine.getAD_Org_ID());
			rLine.setM_MovementConfirm_ID(reversal.getM_MovementConfirm_ID());
			rLine.set_ValueOfColumn("ReversalLine_ID", 
					oLine.getM_MovementLineConfirm_ID());
			//
			rLine.setConfirmedQty(oLine.getConfirmedQty().negate());
			rLine.setTargetQty(Env.ZERO);
			rLine.setScrappedQty(Env.ZERO);
			rLine.setProcessed(false);
			if (!rLine.save())
			{
				m_processMsg = "Could not create Movement Reversal Line";
				return null;
			}
			oLine.set_ValueOfColumn("ReversalLine_ID", 
					rLine.getM_MovementLineConfirm_ID());
			
		}
		//
		if (!reversal.processIt(DocAction.ACTION_Complete))
		{
			m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
			return null;
		}
		reversal.closeIt();
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		reversal.saveEx();
		
		//	Update Reversed (this)
		addDescription("(" + reversal.getDocumentNo() + "<-)");
		//FR [ 1948157  ]
		setReversal_ID(reversal.getM_MovementConfirm_ID());
		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed);	//	may come from void
		setDocAction(DOCACTION_None);
			
		return reversal;
	}
	
	public void setReversal_ID (int reversal_ID)
	{
		set_Value("Reversal_ID", reversal_ID);
	}
	
	public int getReversal_ID ()
	{
		return get_ValueAsInt("Reversal_ID");
	}
	
	public void setReversal (boolean reversal)
	{
		m_isReversal = reversal;
	}
	
	public boolean isReversal ()
	{
		return m_isReversal;
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		if (docStatus.equals(DocAction.STATUS_Drafted))
		{
			options[index++] = DocAction.ACTION_Prepare;
			options[index++] = DocAction.ACTION_Void;
		}
		
		if (docStatus.equals(DocAction.STATUS_Completed))
		{
			if (isDestinationWarehouseConfirm())
			{
				options[index++] = DocAction.ACTION_Reverse_Accrual;
				options[index++] = DocAction.ACTION_Reverse_Correct;	
			}
			else
			{
				options[index++] = DocAction.ACTION_Void;
			}
		}
		return index;
	}
}
