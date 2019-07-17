/**
 * 
 */
package com.unicore.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.adempiere.exceptions.PeriodClosedException;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MDocType;
import org.compiere.model.MLocator;
import org.compiere.model.MMovementLineMA;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MSysConfig;
import org.compiere.model.MWarehouse;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTransaction;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.uns.base.model.Query;

import com.unicore.model.factory.UniCoreMaterialManagementModelFactory;

/**
 * @author Menjangan, Setyaka
 *
 */
public class MMovement extends org.compiere.model.MMovement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MMovementConfirm[] m_DestinationConfirms;
	private MMovementConfirm[] m_confirms;
	private MMovementConfirm[] m_parentConfirm;
	private MMovementLine[] m_lines = null;
	private final String INITIAL_IMPORT_CTX = "ON_IMPORT";
	

	/**
	 * @param ctx
	 * @param M_Movement_ID
	 * @param trxName
	 */
	public MMovement(Properties ctx, int M_Movement_ID, String trxName) {
		super(ctx, M_Movement_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMovement(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	@Override
	public MMovementLine[] getLines (boolean requery)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		final String whereClause = "M_Movement_ID=?";
		List<MMovementLine> list = Query.get(
				getCtx(), 
				UniCoreMaterialManagementModelFactory.EXTENSION_ID, 
				I_M_MovementLine.Table_Name, whereClause, get_TrxName())
		 										.setParameters(getM_Movement_ID())
		 										.setOrderBy(MMovementLine.COLUMNNAME_Line)
		 										.list();
		m_lines = new MMovementLine[list.size ()];
		list.toArray (m_lines);
		return m_lines;
	}	//	getLines
	
	@Override
	public String prepareIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getMovementDate(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
		
		MMovementLine[] lines = getLines(false);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}
		
		//internal confirmation
		if(super.isInternalWarehouseConfirm() && !isReversal())
			createInternalConfirmation();
		
		String event = Env.getContext(Env.getCtx(), INITIAL_IMPORT_CTX);
		boolean isOnImport = !Util.isEmpty(event, true) && event.equals("Y");
		
		if(isOnImport)
		{
			validateOnCompleteProcess();
//			for(MInventory inventory : m_mapInventory.values())
//			{
//				try
//				{
//					ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(
//							inventory, DocAction.ACTION_Complete);
//					if(pi.isError())
//					{
//						m_processMsg = pi.getSummary();
//						return DocAction.STATUS_Invalid;
//					}
//				}
//				catch (Exception e)
//				{
//					m_processMsg = e.getMessage();
//					return DocAction.STATUS_Invalid;
//				}
//			}
		}
		else if(getAD_Org_ID() != getDestinationWarehouse().getAD_Org_ID()
				&& !isReversal()) {
			String NeedSuperordinateConfirmation = 
					MSysConfig.getValue(MSysConfig.INTRANSIT_MOVE_NEEDS_SUPERORDINATE_CONFIRMATION, 
							"Y", getAD_Client_ID());
			if (NeedSuperordinateConfirmation.equals("Y"))
				createParentConfirmation();
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}
	
	/**
	 * Terkait Date Material Policy :
	 * 1. Jika user menentukan (create ASI-To di setiap line), maka ikuti/gunakan tanggal movement sbg DateMPolicy.
	 * 2. Jika user tidak menentukan ASI-To, maka:
	 * 		a. Jika perpindahannya masih dalam warehouse yang sama, maka gunakan ASI-From sbg ASI-To,
	 * 			dan gunakan tanggal creatednya sbg DateMPolicy.
	 * 		b. Jika perpindahannya ke warehouse yang berbeda, maka buat ASI baru sbg ASI-To dan copy values 
	 * 		   dari ASI-From. Gunakan tanggal movement sbg DateMPolicy.
	 */
	public String completeIt()
	{
//		Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		//	Outstanding (not processed) Incoming Confirmations ?
		
		MMovementConfirm[] confirmations = getConfirmations(true);
		for (int i = 0; i < confirmations.length; i++)
		{
			MMovementConfirm confirm = confirmations[i];
			if (!confirm.isProcessed())
			{
				m_processMsg = "Open: @M_MovementConfirm_ID@ - " 
					+ confirm.getDocumentNo();
				return DocAction.STATUS_InProgress;
			}
		}
		
		MMovementConfirm[] parentConfirmations = getParentConfirmations(true);
		for (int i = 0; i < parentConfirmations.length; i++)
		{
			MMovementConfirm confirm = parentConfirmations[i];
			if (!confirm.isProcessed())
			{
				m_processMsg = "Open: @M_MovementConfirm_ID@ - " 
					+ confirm.getDocumentNo();
				return DocAction.STATUS_InProgress;
			}
		}
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		if (log.isLoggable(Level.INFO)) log.info(toString());
		
		//
		MMovementLine[] lines = getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MMovementLine line = lines[i];
			
			if(line.getMovementQty().signum() == 0)
				continue;
			
			MTransaction trxFrom = null; 
			
			MLocator locatorTo = MLocator.get(getCtx(), line.getM_LocatorTo_ID());
			if(isInTransit())
				locatorTo = line.getIntransitLocator(false);
			if(null == locatorTo)
			{
				m_processMsg = "destination locator is Not defined, " +
						"if this document is in-transit movement the in-transit locator could not be created. " +
						"Please contact the administrator to fix it";
				return DocAction.STATUS_Invalid;
			}
			MLocator locatorFrom = MLocator.get(getCtx(), line.getM_Locator_ID());
			
			boolean isDifferentWarehouse = 
					(locatorFrom.getM_Warehouse_ID() != locatorTo.getM_Warehouse_ID())? true : false;
			
			//Stock Movement - Counterpart MOrder.reserveStock
			MProduct product = line.getProduct();
			if (product != null 
					&& product.isStocked() )
			{
				//Ignore the Material Policy when is Reverse Correction
				if(!isReversal()) {
					BigDecimal qtyOnLineMA = MMovementLineMA.getManualQty(line.getM_MovementLine_ID(), get_TrxName());
					BigDecimal movementQty = line.getMovementQty();

					if(qtyOnLineMA.compareTo(movementQty)>0)
					{
						// More then line qty on attribute tab for line 10
						m_processMsg = "@Over_Qty_On_Attribute_Tab@ " + line.getLine();
						return DOCSTATUS_Invalid;
					}
					
					checkMaterialPolicy(line,movementQty.subtract(qtyOnLineMA));
				}
				
				if (line.getM_AttributeSetInstance_ID() == 0)
				{
					MMovementLineMA mas[] = MMovementLineMA.get(getCtx(),
							line.getM_MovementLine_ID(), get_TrxName());
					
					MAttributeSetInstance asiTo = null;
					
					boolean isNotDefinedASITo = false;
					if (line.getM_AttributeSetInstanceTo_ID() > 0)
						asiTo = (MAttributeSetInstance) line.getM_AttributeSetInstanceTo();
					else
						isNotDefinedASITo = true;
					
					for (int j = 0; j < mas.length; j++)
					{
						MMovementLineMA ma = mas[j];
						
						//
						//MLocator locator = MLocator.get(getCtx(), line.getM_Locator_ID());
										//new MLocator (getCtx(), line.getM_Locator_ID(), get_TrxName());
							
						//Update Storage 
						if (!MStorageOnHand.add(getCtx(),
								locatorFrom.getM_Warehouse_ID(),
								line.getM_Locator_ID(),
								line.getM_Product_ID(), 
								ma.getM_AttributeSetInstance_ID(),
								ma.getMovementQty().negate(),
								ma.getDateMaterialPolicy(), 
								get_TrxName()))
						{
							String lastError = CLogger.retrieveErrorString("");
							m_processMsg = "Cannot adjust Inventory OnHand (MA) - " + lastError + " of product "
									+ MProduct.get(getCtx(), line.getM_Product_ID());
							return DocAction.STATUS_Invalid;
						}
						
						//if (isNotDefinedASITo && ma.getM_AttributeSetInstance_ID() > 0) {
						if (isNotDefinedASITo) {
							
							if (ma.getM_AttributeSetInstance_ID() > 0) {
								MAttributeSetInstance asiFrom = 
										(MAttributeSetInstance) ma.getM_AttributeSetInstance();
								
								if (isInTransit() || isDifferentWarehouse) {
									if (line.getM_AttributeSetInstanceTo_ID() == 0) {
										asiTo = asiFrom.createCopyAttributeValues(product);										
										line.set_ValueNoCheck(
												MMovementLine.COLUMNNAME_M_AttributeSetInstanceTo_ID, asiTo.get_ID());
										line.saveEx();
										isNotDefinedASITo = false;
									}
								}
								else { 
									asiTo = asiFrom;
								}
							}
							else if (isInTransit() || isDifferentWarehouse) {
								asiTo = MAttributeSetInstance.initAttributeValuesFrom(
										line, 
										MMovementLine.COLUMNNAME_M_Product_ID, 
										MMovementLine.COLUMNNAME_M_AttributeSetInstanceTo_ID, 
										get_TrxName());
								line.saveEx();
								isNotDefinedASITo = false;
							}
						}
						
						Timestamp dateMPolicyTo = asiTo != null ? asiTo.getCreated() : ma.getDateMaterialPolicy();
						int M_AttributeSetInstanceTo_ID = asiTo != null ? asiTo.getM_AttributeSetInstance_ID()
								: ma.getM_AttributeSetInstance_ID();
						
						//Update Storage 
						if (!MStorageOnHand.add(getCtx(),locatorTo.getM_Warehouse_ID(),
								locatorTo.getM_Locator_ID(),
								line.getM_Product_ID(), 
								M_AttributeSetInstanceTo_ID,
								ma.getMovementQty(),
								dateMPolicyTo,
								get_TrxName()))
						{
							String lastError = CLogger.retrieveErrorString("");
							m_processMsg = "Cannot correct Inventory OnHand (MA) - " + lastError + " of product "
									+ MProduct.get(getCtx(), line.getM_Product_ID());
							return DocAction.STATUS_Invalid;
						}

						//
						trxFrom = new MTransaction (getCtx(), locatorFrom.getAD_Org_ID(), 
								MTransaction.MOVEMENTTYPE_MovementFrom,
								line.getM_Locator_ID(), line.getM_Product_ID(), ma.getM_AttributeSetInstance_ID(),
								ma.getMovementQty().negate(), 
								getMovementDate(), get_TrxName());
						trxFrom.setM_MovementLine_ID(line.getM_MovementLine_ID());
						if (!trxFrom.save())
						{
							m_processMsg = "Transaction From not inserted (MA)";
							return DocAction.STATUS_Invalid;
						}
						//
						MTransaction trxTo = new MTransaction (getCtx(), locatorTo.getAD_Org_ID(), 
								MTransaction.MOVEMENTTYPE_MovementTo,
								locatorTo.getM_Locator_ID(), line.getM_Product_ID(), 
								M_AttributeSetInstanceTo_ID, ma.getMovementQty(), 
								getMovementDate(), get_TrxName());
						trxTo.setM_MovementLine_ID(line.getM_MovementLine_ID());
						if (!trxTo.save())
						{
							m_processMsg = "Transaction To not inserted (MA)";
							return DocAction.STATUS_Invalid;
						}
					}
				}
				//	Fallback - We have ASI
				if (trxFrom == null)
				{
					MAttributeSetInstance asi = (MAttributeSetInstance) line.getM_AttributeSetInstance();
					
					Timestamp dateMPolicy = asi.getCreated();
					
					MLocator locator = new MLocator (getCtx(), line.getM_Locator_ID(), get_TrxName());

					//Update Storage 
					if (!MStorageOnHand.add(getCtx(),locator.getM_Warehouse_ID(),
							line.getM_Locator_ID(),
							line.getM_Product_ID(), 
							line.getM_AttributeSetInstance_ID(),
							line.getMovementQty().negate(),dateMPolicy, get_TrxName()))
					{
						String lastError = CLogger.retrieveErrorString("");
						m_processMsg = "Cannot correct Inventory OnHand (MA) - " + lastError + " of product "
								+ MProduct.get(getCtx(), line.getM_Product_ID());
						return DocAction.STATUS_Invalid;
					}

					//Timestamp dateMPolicyTo = asiTo != null ? asiTo.getCreated() : getMovementDate();
					MAttributeSetInstance asiTo = null;
					
					if (line.getM_AttributeSetInstanceTo_ID() > 0) {
						asiTo = (MAttributeSetInstance) line.getM_AttributeSetInstanceTo();
					}
					else {//if (line.getM_AttributeSetInstanceTo_ID() == 0) {
						if (isDifferentWarehouse && !isReversal()) {
							asiTo = asi.createCopyAttributeValues(product);
							line.set_ValueNoCheck(
									MMovementLine.COLUMNNAME_M_AttributeSetInstanceTo_ID, asiTo.get_ID());
							line.saveEx();
						}
						else { 
							asiTo = asi;
						}
					}
					dateMPolicy = asiTo.getCreated();
					
					
					//Update Storage 
					if (!MStorageOnHand.add(getCtx(),locatorTo.getM_Warehouse_ID(),
							locatorTo.getM_Locator_ID(),
							line.getM_Product_ID(), 
							asiTo.getM_AttributeSetInstance_ID(),
							line.getMovementQty(),dateMPolicy, get_TrxName()))
					{
						String lastError = CLogger.retrieveErrorString("");
						m_processMsg = "Cannot correct Inventory OnHand (MA) - " + lastError + " of product "
								+ MProduct.get(getCtx(), line.getM_Product_ID());
						return DocAction.STATUS_Invalid;
					}

					//
					trxFrom = new MTransaction (getCtx(), line.getAD_Org_ID(), 
							MTransaction.MOVEMENTTYPE_MovementFrom,
							line.getM_Locator_ID(), line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
							isReversal() ? line.getMovementQty() : 
								line.getMovementQty().negate(), getMovementDate(), get_TrxName());
					trxFrom.setM_MovementLine_ID(line.getM_MovementLine_ID());
					if (!trxFrom.save())
					{
						m_processMsg = "Transaction From not inserted";
						return DocAction.STATUS_Invalid;
					}
					//
					MTransaction trxTo = new MTransaction (getCtx(), locatorTo.getAD_Org_ID(), 
							MTransaction.MOVEMENTTYPE_MovementTo,
							locatorTo.getM_Locator_ID(), line.getM_Product_ID(), 
							asiTo.getM_AttributeSetInstance_ID(),
							line.getMovementQty(), getMovementDate(), 
									get_TrxName());
					trxTo.setM_MovementLine_ID(line.getM_MovementLine_ID());
					if (!trxTo.save())
					{
						m_processMsg = "Transaction To not inserted";
						return DocAction.STATUS_Invalid;
					}
				}	//	Fallback
			} // product stock	
			if(isInTransit()
					&& (line.getM_AttributeSetInstanceTo() == null
						|| line.getM_AttributeSetInstanceTo_ID() <= 0))
			{
				m_processMsg = "This is ASI case not linked with line. Please contact Administrator..!";
				return DocAction.STATUS_Invalid;
			}
		}	//	for all lines
		//	User Validation
		
		//	Confirmation of destination warehouse
		if (isInTransit())
		{
			if (isReversal())
			{
				MMovementConfirm[] destConfirms = getDestinationWarehouseConfirmations(true);
				for (int i=0; i<destConfirms.length; i++)
				{
					String destConfStatus = destConfirms[i].getDocStatus();
					boolean destIsVoided = destConfStatus.equals(DOCSTATUS_Reversed)
							|| destConfStatus.equals(DOCSTATUS_Voided);
					if (!destIsVoided)
					{
						m_processMsg = "Please void / reverse destination confirmation first."
								+ " Destination confirm doc : " + destConfirms[i].getDocumentNo();
						return DocAction.STATUS_Invalid;
					}
				}
			}
			else
			{
				createDestinationWarehouseConfirmation();	
			}
		}
		if(getC_Order_ID() > 0)
		{
			onCanvasMove();
		}
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}
		
		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		//
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}
	
	protected void createDestinationWarehouseConfirmation()
	{
		MMovementConfirm[] destinationConfirm = getDestinationWarehouseConfirmations(true);
		if(destinationConfirm.length > 0)
			return;
		
		MMovementConfirm.create(
				this, false, MMovementConfirm.CONFIRMATIONTYPE_DestinationWarehouseConfirmation);		
	}
	
	
	@Override
	public MMovementConfirm[] getConfirmations(boolean requery)
	{
		if (m_confirms != null && !requery)
			return m_confirms;
		
		String whereClause = COLUMNNAME_M_Movement_ID
				+ " = ? AND "
				+ MMovementConfirm.COLUMNNAME_ConfirmationType
				+ " = ?";
		
		List<MMovementConfirm> list = Query.get(
				getCtx(),
				UniCoreMaterialManagementModelFactory.EXTENSION_ID, 
				MMovementConfirm.Table_Name, 
				whereClause, 
				get_TrxName()).
				setParameters(
						get_ID(), MMovementConfirm.CONFIRMATIONTYPE_InternalWarehouseConfirmation ).
				list();
		
		m_confirms = list.toArray(new MMovementConfirm[list.size()]);
		
		return m_confirms;
	}
	
	public MMovementConfirm[] getParentConfirmations(boolean requery)
	{
		if(m_parentConfirm != null
				&& !requery)
			return m_parentConfirm;
		
		String whereClause = MMovementConfirm.COLUMNNAME_M_Movement_ID + " = " + getM_Movement_ID()
								+ " AND " 
								+ MMovementConfirm.COLUMNNAME_ConfirmationType + " =?";
		List<MMovementConfirm> list = Query.get(
				getCtx(), 
				UniCoreMaterialManagementModelFactory.EXTENSION_ID, 
				MMovementConfirm.Table_Name, 
				whereClause, 
				get_TrxName()).
				setParameters(MMovementConfirm.CONFIRMATIONTYPE_HeadOfficeConfirmation).
				list();
		
		m_parentConfirm = list.toArray(new MMovementConfirm[list.size()]);
		return m_parentConfirm;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MMovementConfirm[] getDestinationWarehouseConfirmations(boolean requery)
	{
		if(m_DestinationConfirms != null && !requery)
			return m_DestinationConfirms;
		
		String whereClause = MMovement.COLUMNNAME_M_Movement_ID 
				+ "=? AND "
				+ MMovementConfirm.COLUMNNAME_ConfirmationType
				+ " = ?";
		
		List<MMovementConfirm> destinationConfirmations = Query.get(
				getCtx(), 
				UniCoreMaterialManagementModelFactory.EXTENSION_ID, 
				MMovementConfirm.Table_Name, 
				whereClause, 
				get_TrxName()).
				setParameters(getM_Movement_ID(),  MMovementConfirm.CONFIRMATIONTYPE_DestinationWarehouseConfirmation).
				list();
		
		m_DestinationConfirms = destinationConfirmations.toArray(
				new MMovementConfirm[destinationConfirmations.size()]);
		
		return m_DestinationConfirms;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		boolean isDifferentWhsOrg = false;
		
		MDocType docType = MDocType.get(getCtx(), getC_DocType_ID());
		
		if (getDestinationWarehouse_ID() > 0) {
			MWarehouse whsDest = MWarehouse.get(getCtx(), getDestinationWarehouse_ID());
			isDifferentWhsOrg = whsDest.getAD_Org_ID() != getAD_Org_ID();
		}
		else {
			//if (isInTransit() && getDestinationWarehouse_ID() == 0)
			if (docType.isInTransit())
			{
				log.saveError("InTransitRequireDestWarehouse", "Please select destination warehouse!");
				return false;
			}
		}
		
		if (isDifferentWhsOrg && !docType.isInTransit()) {
			//String sql = "SELECT C_DocType_ID FROM C_DocType WHERE IsIntransit='Y' AND DocBaseType=?";
			docType = Query.get(
					getCtx(), UniCoreMaterialManagementModelFactory.EXTENSION_ID, MDocType.Table_Name,
					"IsInTransit='Y' AND DocBaseType=?", get_TrxName())
					.setParameters(MDocType.DOCBASETYPE_MaterialMovement)
					.firstOnly();
			setC_DocType_ID(docType.get_ID());
		}
//		else if (!isDifferentWhsOrg && docType.isInTransit()) {
//			docType = Query.get(
//					getCtx(), UniCoreMaterialManagementModelFactory.EXTENSION_ID, MDocType.Table_Name,
//					"IsInTransit='N' AND DocBaseType=?", get_TrxName())
//					.setParameters(MDocType.DOCBASETYPE_MaterialMovement)
//					.firstOnly();
//			setC_DocType_ID(docType.get_ID());
//		}
		
		super.setIsInTransit(docType.isInTransit());
		
		return super.beforeSave(newRecord);
	}
	
	private void createParentConfirmation()
	{
		MMovementConfirm[] confirmations = getParentConfirmations(false);
		if(confirmations.length > 0)
			return;
		
		MMovementConfirm.create(
				this, false, MMovementConfirm.CONFIRMATIONTYPE_HeadOfficeConfirmation);
	}
	
	/**
	 * 
	 */
	private void createInternalConfirmation()
	{
		//check internal confirmation isexist
		MMovementConfirm[] confirmations = getConfirmations(false);
		if (confirmations.length > 0)
			return;
		
		//	Create Confirmation for internal warehouse
		MMovementConfirm.create (
				this, false, MMovementConfirm.CONFIRMATIONTYPE_InternalWarehouseConfirmation);
	}
	
	public MWarehouse getIntransitWarehouse()
	{
		return Query.get(
				getCtx(), 
				UniCoreMaterialManagementModelFactory.EXTENSION_ID, 
				MWarehouse.Table_Name, 
				MWarehouse.COLUMNNAME_Value + " = ?", 
				get_TrxName()).
				setParameters(getDestinationWarehouse().
						getValue() + "-Intransit").
						firstOnly();
	}
	
	private  Hashtable<Integer, BigDecimal> getMappingProductQtyMove()
    {
    	Hashtable<Integer, BigDecimal> myMap = new Hashtable<>();
    	MMovementLine[] records = getLines(false);
    	for(int i=0; i<records.length; i++)
    	{
    		BigDecimal tmp = myMap.get(records[i].getM_Product_ID());
    		
    		if(null == tmp)
    			tmp = Env.ZERO;
    		
    		tmp = tmp.add(records[i].getMovementQty());
    		myMap.put(records[i].getM_Product_ID(), tmp);
    	}
    	return myMap;
    }
	
	/**
	 * 
	 */
	public void onCanvasMove()
	{
		MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		MOrderLine[] lines = order.getLines();
		Hashtable<Integer, BigDecimal> mappingProductQty = getMappingProductQtyMove();
		
		for(MOrderLine line : lines)
		{
			BigDecimal remaining = mappingProductQty.get(line.getM_Product_ID());
			if(null == remaining)
			{
				line.deleteEx(true);
				continue;
			}
			
			BigDecimal tmp = remaining.subtract(line.getQtyOrdered());
			if(tmp.signum() == -1)
			{
				remaining = line.getQtyOrdered().add(tmp);
			}
			
			line.setQtyEntered(remaining.subtract(line.getQtyBonuses()));
			line.setQtyOrdered(remaining);
//			line.setPrice();
			line.saveEx();
			tmp = remaining.subtract(line.getQtyOrdered());
			mappingProductQty.put(line.getM_Product_ID(), tmp);
		}
		
		for(BigDecimal remaining : mappingProductQty.values())
		{
			if(remaining.signum() == 0)
				continue;
			
			throw new AdempiereUserError("Over Movement Quantity");
		}
	}
	
	private void validateOnCompleteProcess()
	{
		MMovementLine[] lines = getLines(false);
		for(int i=0; i<lines.length; i++)
		{
			MMovementLine line = lines[i];
			BigDecimal qtyOnHand = MStorageOnHand.getQtyOnHandForLocator(
					line.getM_Product_ID(), line.getM_Locator_ID(), line.getM_AttributeSetInstance_ID()
					, get_TrxName());
			
			if(qtyOnHand.compareTo(line.getMovementQty()) >= 0)
				continue;

			setToZero(line);
		}
	}
	
	private void setToZero(MMovementLine line)
	{
		line.setDescription("Auto set to zero prev qty = " + line.getMovementQty());
		line.setMovementQty(Env.ZERO);
		line.saveEx();
	}
	
	public boolean voidIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
				
		if (DOCSTATUS_Closed.equals(getDocStatus())
			|| DOCSTATUS_Reversed.equals(getDocStatus())
			|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}

		//	Not Processed
		if (DOCSTATUS_Drafted.equals(getDocStatus())
			|| DOCSTATUS_Invalid.equals(getDocStatus())
			|| DOCSTATUS_InProgress.equals(getDocStatus())
			|| DOCSTATUS_Approved.equals(getDocStatus())
			|| DOCSTATUS_NotApproved.equals(getDocStatus()) )
		{
			// Before Void
			m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
			if (m_processMsg != null)
				return false;
			
			//	Set lines to 0
			MMovementLine[] lines = getLines(false);
			for (int i = 0; i < lines.length; i++)
			{
				MMovementLine line = lines[i];
				BigDecimal old = line.getMovementQty();
				if (old.compareTo(Env.ZERO) != 0)
				{
					line.setMovementQty(Env.ZERO);
					line.addDescription("Void (" + old + ")");
					line.saveEx(get_TrxName());
				}
			}
			
			MMovementConfirm[] internalConfirms = getConfirmations(true);
			for (int i=0; i<internalConfirms.length; i++)
			{
				MMovementConfirm internal = internalConfirms[i];
				boolean isOK = internal.processIt(DocAction.ACTION_Void);
				if (!isOK)
				{
					m_processMsg = internal.getProcessMsg();
					return false;
				}
				internal.saveEx();
			}
			MMovementConfirm[] superOrdinateCOnfirms = getParentConfirmations(true);
			for (int i=0; i<superOrdinateCOnfirms.length; i++)
			{
				MMovementConfirm superO = superOrdinateCOnfirms[i];
				boolean isOK = superO.processIt(DocAction.ACTION_Void);
				if (!isOK)
				{
					m_processMsg = superO.getProcessMsg();
					return false;
				}
				superO.saveEx();
			}
			MMovementConfirm[] destinationConfirms = getDestinationWarehouseConfirmations(true);
			for (int i=0; i<destinationConfirms.length; i++)
			{
				MMovementConfirm destination = destinationConfirms[i];
				String destConfStatus = destination.getDocStatus();
				boolean destIsVoided = destConfStatus.equals(DOCSTATUS_Reversed)
						|| destConfStatus.equals(DOCSTATUS_Voided);
				if (!destIsVoided)
				{
					m_processMsg = "Please void / reverse destination confirmation first."
							+ " Destination confirm doc : " + destination.getDocumentNo();
					return false;
				}
			}
		}
		else
		{
			boolean accrual = false;
			try 
			{
				MPeriod.testPeriodOpen(getCtx(), getMovementDate(), getC_DocType_ID(), getAD_Org_ID());
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
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
			
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	//	voidIt
	
	public boolean reverseCorrectIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		MMovement reversal = reverse(false);
		if (reversal == null)
			return false;
		
		m_processMsg = reversal.getDocumentNo();
		
		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	reverseCorrectionIt
	
	private MMovement reverse(boolean accrual)
	{
		Timestamp reversalDate = accrual ? Env.getContextAsDate(getCtx(), "#Date") : getMovementDate();
		if (reversalDate == null) {
			reversalDate = new Timestamp(System.currentTimeMillis());
		}
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (!MPeriod.isOpen(getCtx(), reversalDate, dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return null;
		}
		
		MMovementConfirm[] internalConfirms = getConfirmations(true);
		for (int i=0; i<internalConfirms.length; i++)
		{
			MMovementConfirm internal = internalConfirms[i];
			boolean isOK = internal.processIt(DocAction.ACTION_Void);
			if (!isOK)
			{
				m_processMsg = internal.getProcessMsg();
				return null;
			}
			internal.saveEx();
		}
		MMovementConfirm[] superOrdinateCOnfirms = getParentConfirmations(true);
		for (int i=0; i<superOrdinateCOnfirms.length; i++)
		{
			MMovementConfirm superO = superOrdinateCOnfirms[i];
			boolean isOK = superO.processIt(DocAction.ACTION_Void);
			if (!isOK)
			{
				m_processMsg = superO.getProcessMsg();
				return null;
			}
			superO.saveEx();
		}
		MMovementConfirm[] destinationConfirms = getDestinationWarehouseConfirmations(true);
		for (int i=0; i<destinationConfirms.length; i++)
		{
			MMovementConfirm destination = destinationConfirms[i];
			String destConfStatus = destination.getDocStatus();
			boolean destIsVoided = destConfStatus.equals(DOCSTATUS_Reversed)
					|| destConfStatus.equals(DOCSTATUS_Voided);
			if (!destIsVoided)
			{
				m_processMsg = "Please void / reverse destination confirmation first."
						+ " Destination confirm doc : " + destination.getDocumentNo();
				return null;
			}
		}

		//	Deep Copy
		MMovement reversal = new MMovement(getCtx(), 0, get_TrxName());
		copyValues(this, reversal, getAD_Client_ID(), getAD_Org_ID());
		reversal.setDocStatus(DOCSTATUS_Drafted);
		reversal.setDocAction(DOCACTION_Complete);
		reversal.setIsApproved (false);
		reversal.setIsInTransit (false);
		reversal.setPosted(false);
		reversal.setProcessed(false);
		reversal.setMovementDate(reversalDate);
		reversal.setDocumentNo(getDocumentNo() + REVERSE_INDICATOR);	//	indicate reversals
		reversal.addDescription("{->" + getDocumentNo() + ")");
		//FR [ 1948157  ]
		reversal.setReversal_ID(getM_Movement_ID());
		if (!reversal.save())
		{
			m_processMsg = "Could not create Movement Reversal";
			return null;
		}
		reversal.setReversal(true);
		//	Reverse Line Qty
		MMovementLine[] oLines = getLines(true);
		for (int i = 0; i < oLines.length; i++)
		{
			MMovementLine oLine = oLines[i];
			MMovementLine rLine = new MMovementLine(getCtx(), 0, get_TrxName());
			copyValues(oLine, rLine, oLine.getAD_Client_ID(), oLine.getAD_Org_ID());
			rLine.setM_Movement_ID(reversal.getM_Movement_ID());
			//AZ Goodwill			
			// store original (voided/reversed) document line
			rLine.setReversalLine_ID(oLine.getM_MovementLine_ID());
			//
			rLine.setMovementQty(rLine.getMovementQty().negate());
			rLine.setTargetQty(Env.ZERO);
			rLine.setScrappedQty(Env.ZERO);
			rLine.setConfirmedQty(Env.ZERO);
			rLine.setM_AttributeSetInstanceTo_ID(oLine.getM_AttributeSetInstanceTo_ID());
			rLine.setProcessed(false);
			if (!rLine.save())
			{
				m_processMsg = "Could not create Movement Reversal Line";
				return null;
			}
			
			//We need to copy MA
			if (rLine.getM_AttributeSetInstance_ID() == 0)
			{
				MMovementLineMA mas[] = MMovementLineMA.get(getCtx(),
						oLine.getM_MovementLine_ID(), get_TrxName());
				for (int j = 0; j < mas.length; j++)
				{
					MMovementLineMA ma = new MMovementLineMA (rLine, 
							mas[j].getM_AttributeSetInstance_ID(),
							mas[j].getMovementQty().negate(),mas[j].getDateMaterialPolicy(),true);
					ma.saveEx();
				}
			}
			
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
		setReversal_ID(reversal.getM_Movement_ID());
		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed);	//	may come from void
		setDocAction(DOCACTION_None);
			
		return reversal;
	}
	
	/**
	 * 	Reverse Accrual - none
	 * 	@return false 
	 */
	public boolean reverseAccrualIt()
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		MMovement reversal = reverse(true);
		if (reversal == null)
			return false;
		
		m_processMsg = reversal.getDocumentNo();
		
		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		return true;
	}	//	reverseAccrualIt
}
