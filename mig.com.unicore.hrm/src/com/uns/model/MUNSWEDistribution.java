/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MInventoryLineMA;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

//import com.uns.util.MUNSAppsContext;
//import com.uns.util.UNSApps;

/**
 * @author Admin_UNS
 * 
 */
public class MUNSWEDistribution extends X_UNS_WEDistribution implements
		DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8873715430030764877L;
	private String m_processMsg;
	private boolean m_justPrepared = false;
	private MUNSWEDistributionLine[] m_lines;
	private int m_warehouseTo;

	/**
	 * @param ctx
	 * @param UNS_WEDistribution_ID
	 * @param trxName
	 */
	public MUNSWEDistribution(Properties ctx, int UNS_WEDistribution_ID,
			String trxName) {
		super(ctx, UNS_WEDistribution_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSWEDistribution(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)) {
    		options[index++] = DocumentEngine.ACTION_Prepare;
    		options[index++] = DocumentEngine.ACTION_Void;
    	}
		
		if (docStatus.equals(DocumentEngine.STATUS_InProgress)
    			|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
    		options[index++] = DocumentEngine.ACTION_Complete;
    	}

    	if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
    		options[index++] = DocumentEngine.ACTION_Close;
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
		
		m_processMsg = checkLines();
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		
		MMovement move = createMovement();
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		//process Inventory Move
		if (DOCSTATUS_Drafted.equals(move.getDocStatus())
				|| DOCSTATUS_InProgress.equals(move.getDocStatus())) {
			move.processIt(DOCACTION_Complete);
		}
		else if (DOCSTATUS_Invalid.equals(move.getDocStatus())) {
			m_processMsg = "You have invalid Inventory Move no. '" + move.getDocumentNo() + 
				  "'. Please revise the Inventory Move document manually.";
		}
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);
		m_justPrepared = true;
		
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		setIsApproved(false);
		setProcessed(false);
		setDocStatus(DOCSTATUS_NotApproved);
		setDocAction(DOCSTATUS_Completed);
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
		
		MInventory internalUse = createInventory();
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		//process Internal Inventory Use
		if (DOCSTATUS_Drafted.equals(internalUse.getDocStatus())
				|| DOCSTATUS_InProgress.equals(internalUse.getDocStatus())) {
			internalUse.processIt(DOCACTION_Complete);
		}
		else if (DOCSTATUS_Invalid.equals(internalUse.getDocStatus())) {
			m_processMsg = "You have invalid Internal Inventory Use no. '" + internalUse.getDocumentNo() + 
				  "'. Please revise the Internal Inventory Use document manualy.";
		}
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		return null;
	}

	@Override
	public boolean voidIt() {
		log.info(toString());
		// Before void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);

		// After void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;
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
		// not implement yet
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// not implement yet
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// not implement yet
		return false;
	}

	@Override
	public String getSummary() {
		String summary = "Distribution "+getUNS_RecidenceGroup().getName()
				+ ", Total Used : "+getApprovalAmt();
		return summary;
	}

	@Override
	public String getDocumentInfo() {
		String info = getDocumentNo()+"["+getSummary()+"]";
		return info;
	}

	@Override
	public File createPDF() {
		// not implement yet
		return null;
	}

	@Override
	public String getProcessMsg() {
		
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {

		return getAD_User_ID();
	}

	@Override
	public int getC_Currency_ID() {
		
		return 0;
	}
	
	@Override
	public String toString(){
		return getDocumentInfo();
	}
	
	private String checkLines() {
		if ("N".equals(getGenerateList()))
			return "Please generate transaction before process.";
		
		for (MUNSWEDistributionLine line : getLines(false)){
			if(line.isActive() && line.getQtyInternalUse().signum()<1)
				return "Please input used quantity Or Delete Transaction.";
			if (m_warehouseTo == 0)
				m_warehouseTo = line.getM_LocatorTo().getM_Warehouse_ID();
		}
		
		//QASTATUS
//		//check Available product
//		MStorageOnHand[] listStorage = MStorageOnHand.getOfQAStatus(getCtx(), getM_Locator_ID(), 
//				getM_Product_ID(), MStorageOnHand.QASTATUS_DEFAULT, get_TrxName());
//		BigDecimal qtyOnHand = BigDecimal.ZERO;
//		for(MStorageOnHand soh : listStorage){	//SUM QtyRelease
//			qtyOnHand = qtyOnHand.add(soh.getReleaseQty());
//		}
//		if (qtyOnHand.signum()==0 || qtyOnHand.compareTo(getApprovalAmt())<0)
//			return "Product to distribution is not available.";
		
		return null;
	}
	
	public MUNSWEDistributionLine[] getLines(String whereClause) {
		String whereClauseFinal = "UNS_Material_Request_ID=? ";
		if (whereClause != null)
			whereClauseFinal = whereClauseFinal+ "AND " +whereClause;

		List<MUNSWEDistributionLine> list = new Query(getCtx(),
				MUNSWEDistributionLine.Table_Name, whereClauseFinal,
				get_TrxName())
				.setParameters(new Object[] { getUNS_WEDistribution_ID() })
				.setOrderBy(MUNSWEDistributionLine.COLUMNNAME_UNS_WEDistributionLine_ID)
				.list();

		return list.toArray(new MUNSWEDistributionLine[list.size()]);
	}

	/**
	 * Get list from MUNSWEDistributionLine
	 * 
	 * @param query
	 * @return
	 */
	private MUNSWEDistributionLine[] getLines(boolean query) {
		if (m_lines == null || query)
			m_lines = getLines(null);

		return m_lines;
	}
	
	private MMovement createMovement()
	{
		MMovement movement = new MMovement(getCtx(), 0, get_TrxName());
		movement.setAD_Org_ID(getAD_Org_ID());
		movement.setAD_User_ID(getAD_User_ID());
		movement.setC_DocType_ID(MDocType.getDocType("WED"));
		movement.setMovementDate(getMovementDate());
		movement.setPOReference("WED-"+getDocumentNo());
		movement.setDescription("** Auto generate from Water & Electricity Distribution :" +
				getDocumentNo() + " **");
	//TODO Water Electric Distribution
//		movement.setM_WarehouseTo_ID(m_warehouseTo);
//		movement.setApprovalAmt(getApprovalAmt());
//		movement.setMovementType(MMovement.MOVEMENTTYPE_ProductionMMType);
		movement.setDocStatus(DOCSTATUS_Drafted);
		movement.setDocAction(DOCACTION_Prepare);
		movement.saveEx();
		
		for (MUNSWEDistributionLine line : getLines(false)){
			if (!createUpdateLines(line, movement)){
				m_processMsg = "Error when generate movement Line";
				return null;
			}
		}
		
		return movement;
	}

	private MInventory createInventory()
	{
		MInventory inventoryUse = new MInventory(getCtx(), 0, get_TrxName());
		inventoryUse.setAD_Org_ID(getAD_Org_ID());
		inventoryUse.setC_DocType_ID(MDocType.getDocType("MMI"));
		inventoryUse.setM_Warehouse_ID(m_warehouseTo);
		inventoryUse.setDescription("** Auto generate from Water & Electricity Distribution :" +
				getDocumentNo() + " **");
		inventoryUse.setMovementDate(getMovementDate());
		inventoryUse.setApprovalAmt(getApprovalAmt());
		inventoryUse.setDocStatus(DOCSTATUS_Drafted);
		inventoryUse.setDocAction(DOCACTION_Prepare);
		
		for (MUNSWEDistributionLine line : getLines(false)){
			if (!createUpdateLines(line, inventoryUse)){
				m_processMsg = "Error when generate Inventory Use Line";
				return null;
			} 
		}
		
		return inventoryUse;
	}
	
	public boolean createUpdateLines(PO source, PO header) 
	{
		if (header.get_TableName().equals(MMovement.Table_Name))
		{
			//create inventory move line
			MMovement move = (MMovement) header;
			MUNSWEDistributionLine line = (MUNSWEDistributionLine) source;
			MMovementLine mLine = new MMovementLine(move);
			mLine.setM_Product_ID(line.getUNS_WEDistribution().getM_Product_ID());
			mLine.setM_Locator_ID(line.getUNS_WEDistribution().getM_Locator_ID());
			mLine.setM_LocatorTo_ID(line.getM_LocatorTo_ID());
			mLine.setMovementQty(line.getQtyInternalUse());
			//TODO Water Electric Distribution
//			mLine.setQAStatus(MStorageOnHand.QASTATUS_DEFAULT);
			mLine.setDescription("** Auto Generate from Water & Electricity Distribution :" +
				line.getDescription() + " **");	
			
			if (mLine.save())
				return updateID(line, true, mLine.get_ID());
			
			return false;
		} else if (header.get_TableName().equals(MInventory.Table_Name)) {
			//create inventory use line
			MInventory inventory = (MInventory) header;
			MUNSWEDistributionLine line = (MUNSWEDistributionLine) source;
			
			MInventoryLine iLine = new MInventoryLine(inventory, line.getM_LocatorTo_ID(),
					line.getUNS_WEDistribution().getM_Product_ID(), 0, null, null, 
					line.getQtyInternalUse());
			//TODO Water Electric Distribution
//			iLine.setQAStatus(MStorageOnHand.QASTATUS_DEFAULT);
			iLine.setDescription("** Auto Generate from Water & Electricity Distribution :" +
				line.getDescription() + " **");
			
			if (iLine.save()){
				if(line.getM_MovementLine().getM_AttributeSetInstanceTo_ID()==0){
					//Make sure no MA Line
					MInventoryLineMA.deleteInventoryLineMA(iLine.get_ID(), get_TrxName());
//					for (MMovementLineMA mLineMA : MMovementLineMA.get(line.getCtx(), 
//							line.getM_MovementLine_ID(), line.get_TrxName())){
						//TODO Water Electric Distribution
//						MInventoryLineMA iLineMA = new MInventoryLineMA(iLine, 
//								mLineMA.getM_AttributeSetInstance_ID(),	mLineMA.getMovementQty());
//						iLineMA.saveEx();
//					}
				} else
					iLine.setM_AttributeSetInstance_ID(
							line.getM_MovementLine().getM_AttributeSetInstanceTo_ID());
				iLine.saveEx();
				
				return updateID(line, true, iLine.get_ID());
			}
			return false;
		} else if (header.get_TableName().equals(MUNSWEDistribution.Table_Name)){
			//generate water & electricity Transaction
			MUNSWEDistribution wed = (MUNSWEDistribution) header;
			MUNSMessBuildingBlock housing = (MUNSMessBuildingBlock) source;
			for (MUNSMessPartition room : housing.getLines(false)){
				MUNSWEDistributionLine line = null;
				int[] lineID = MUNSWEDistributionLine.getAllIDs(MUNSWEDistributionLine.Table_Name, 
					"UNS_WEDistribution_ID = " + header.get_ID() +
					" AND UNS_Mess_Partition_ID = " + room.get_ID(), get_TrxName());
				if (lineID.length==0)
					line = new MUNSWEDistributionLine(wed, room);
				else
					line = new MUNSWEDistributionLine(wed.getCtx(),lineID[0],wed.get_TrxName());
				
				line.setQtyPInternalUse(room.getLastUsed(wed.getM_Product_ID()));
				line.setQtyInternalUse(line.getQtyPInternalUse());
				if(!line.save())
					return false;
			}
			return true;
		}
		
		return false;
	}
	
	private boolean updateID(MUNSWEDistributionLine line, Boolean isMove, int value) {
		if (isMove)
			line.setM_MovementLine_ID(value);
		else
			line.setM_InventoryLine_ID(value);
		return line.save();
	}
	
	public static boolean IsWater(Properties ctx, int M_Product_ID, String trxName)
	{
//		if (M_Product_ID == MUNSAppsContext.get(UNSApps.ELC_PWH, ctx, trxName)
//				.getM_Product_ID())
//				return false;	
//		if (M_Product_ID == MUNSAppsContext.get(UNSApps.ELC_TBN, ctx, trxName)
//				.getM_Product_ID())
//				return false;
//		if (M_Product_ID == MUNSAppsContext.get(UNSApps.WT_ACF1, ctx, trxName)
//				.getM_Product_ID())
//				return true;
//
//		if (M_Product_ID == MUNSAppsContext.get(UNSApps.WT_ACF2, ctx, trxName)
//				.getM_Product_ID())
//				return true;
//
//		if (M_Product_ID == MUNSAppsContext.get(UNSApps.WT_AST, ctx, trxName)
//				.getM_Product_ID())
//				return true;
//
//		if (M_Product_ID == MUNSAppsContext.get(UNSApps.WT_PT, ctx, trxName)
//				.getM_Product_ID())
//				return true;
//
//		if (M_Product_ID == MUNSAppsContext.get(UNSApps.WT_RAW, ctx, trxName)
//				.getM_Product_ID())
//				return true;
//
//		if (M_Product_ID == MUNSAppsContext.get(UNSApps.WT_RO, ctx, trxName)
//				.getM_Product_ID())
//				return true;
		
		throw new AdempiereException("Cannot find configuration [Application Reference]");
	}
	
}
