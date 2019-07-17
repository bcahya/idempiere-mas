/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutConfirm;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInOutLineConfirm;
import org.compiere.model.MLocator;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MWarehouse;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

import com.uns.model.MUNSKapal;

import com.uns.base.model.MInvoice;
import com.uns.base.model.MInvoiceLine;
import com.uns.model.factory.UNSFinanceModelFactory;
import com.uns.model.factory.UNSPPICModelFactory;
import com.uns.qad.model.MUNSQAContainerInspection;
import com.uns.qad.model.MUNSQAStatusContainer;
import com.uns.qad.model.MUNSQAStatusInOut;
import com.uns.qad.model.MUNSQAStatusInOutLine;
import com.uns.qad.model.X_UNS_QAStatusContainer;


/**
 * @author YAKA
 *
 */
public class MUNSShipmentSchedule extends X_UNS_ShipmentSchedule implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6442593469474399471L;
	public static final String EXTENSION_ID = "com.uns.model.factory.UNSPPICModelFactory";
	private String m_processMsg;
	private boolean m_justPrepared;
	
	/**
	 * Return Shipment Schedule instance if found, null otherwise.
	 * 
	 * @param ctx
	 * @param UNS_ShipmentSchedule_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSShipmentSchedule get(Properties ctx, int UNS_ShipmentSchedule_ID, String trxName) {
		MUNSShipmentSchedule shipmentSchd =
				com.uns.base.model.Query
				.get(ctx, UNSPPICModelFactory.getExtensionID(), 
						MUNSShipmentSchedule.Table_Name, "UNS_ShipmentSchedule_ID=?", trxName)
				.setParameters(UNS_ShipmentSchedule_ID)
				.first();
		return shipmentSchd;
	}

	/**
	 * @param ctx
	 * @param UNS_ShipmentSchedule_ID
	 * @param trxName
	 */
	public MUNSShipmentSchedule(Properties ctx, int UNS_ShipmentSchedule_ID,
			String trxName) {
		super(ctx, UNS_ShipmentSchedule_ID, trxName);
		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSShipmentSchedule(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}
	
	public MUNSContainerDeparture[] getContainerDeparture(){
		
		String wClause = COLUMNNAME_UNS_ShipmentSchedule_ID + "=?";
		List<MUNSContainerDeparture> list = new Query(getCtx(), MUNSContainerDeparture.Table_Name,
				wClause, get_TrxName()).setParameters(getUNS_ShipmentSchedule_ID())
				.setOrderBy(MUNSContainerDeparture.COLUMNNAME_UNS_ContainerDeparture_ID).list();

		return list.toArray(new MUNSContainerDeparture[list.size()]);
	}

	@Override
	public boolean processIt(String processAction) throws Exception 
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	}

	@Override
	public boolean rejectIt() {
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public boolean reverseAccrualIt() {
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}

	@Override
	public boolean reverseCorrectIt() {
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return voidIt();
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessed(false);
		return true;
	}

	@Override
	public boolean voidIt() {
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		// TODO Void It not implement (Coding here)

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		return true;
	}

	/**
	 * Add to Description
	 * 
	 * @param description
	 *            text
	 */
	public void addDescription(String description) {
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription

	public int getC_Currency_ID() {
		return 0;
	}

	/**
	 * Customize Valid Actions
	 * 
	 * @param docStatus
	 * @param processing
	 * @param orderType
	 * @param isSOTrx
	 * @param AD_Table_ID
	 * @param docAction
	 * @param options
	 * @return Number of valid options
	 */
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {

		// If status = Drafted, add "Prepare" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
				|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
			options[index++] = DocumentEngine.ACTION_Prepare;
		}

		// If status = Completed, add "Reactivte" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
			options[index++] = DocumentEngine.ACTION_ReActivate;
			//options[index++] = DocumentEngine.ACTION_Void;
		}

		return index;
	}
	
	public boolean approveIt() {
		log.info(toString());
		// setIsApproved(true);
		return true;
	}

	@Override
	public boolean closeIt() {
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public String prepareIt() 
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (!MPeriod.isOpen(
				getCtx(), getETD(), com.uns.model.I_C_DocType.DOCBASETYPE_ShipmentSchedule, getAD_Org_ID())) 
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (!checkAvailability())
		{
			// availability 
			m_processMsg = "Not enough stock. " +
					"Please run 'Check Availability' process to update it's remarks of availabilities status.";
			return DocAction.STATUS_Drafted;
		}
		
		createShipment();
		/*
		if(getUNS_QAContainerInspection_ID() != 0 && createContainerInspection()== null && !m_justPrepared)
		{
			//createShipment();
		}
		*/
		m_processMsg = checkContainerInspection();
		if(m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}
		
		m_justPrepared = true;
		
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public String completeIt() 
	{
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());

		// Re-Check
		if (!m_justPrepared) 
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		/*
		m_processMsg = checkContainerInspection();
		if (m_processMsg != null) {
			return DocAction.STATUS_InProgress;
		}
		
		m_processMsg = isNeedPrematureRelease();
		if (m_processMsg != null) {
			return DocAction.STATUS_InProgress;
		}
	
		m_processMsg = validateConfirmation();
		if (m_processMsg != null) {
			setConfirmationStatus("Waiting for shipment confirmation of: " + m_processMsg);
			m_processMsg = "You still have uncomplete shipment confirmation: " + m_processMsg;
			return DocAction.STATUS_InProgress;
		}
		*/	
		//Check status on Ship/Receipt Inspection (hold or not)
		String prematureMsg = isNeedPrematureRelease();
		String qaHoldShipmentMsg = validateQAHoldShipment();

		if (prematureMsg != null) {
			setConfirmationStatus("Waiting for premature release request of: " + prematureMsg + "\n");
			m_processMsg = "You still have to wait for Premature Request of: " + prematureMsg + "\n";
			return DocAction.STATUS_InProgress;
		}
		if (qaHoldShipmentMsg != null) {
			setConfirmationStatus("Waiting for shipment inspection of: " + qaHoldShipmentMsg + "\n");
			m_processMsg = "You still have uncompleted shipment inspection: " + qaHoldShipmentMsg + "\n";
			return DocAction.STATUS_InProgress;
		}
		
		String shipmentCompletionStatus = checkCustomerShipmentsCompletionStatus();
		if (shipmentCompletionStatus != null) {
			setConfirmationStatus("Waiting for shipment document completion of: " + shipmentCompletionStatus);
			m_processMsg = "You still have uncompleted shipment: " + shipmentCompletionStatus;
			return DocAction.STATUS_InProgress;
		}
		
		// Check Shipment (Customer) for completion.
		
		/*
		//try to automatically complete all customer shipments.
		m_processMsg = completeAllCustomerShipments();
		if (null != m_processMsg)
			return DOCSTATUS_Invalid;
		*/
		if (!isInvoiceCreated(get_TrxName(), getUNS_ShipmentSchedule_ID()))
		{
			// create billing and invoice status.
			try {
				Hashtable<String, MInvoice> invoiceMap = createInvoices();
				if (invoiceMap == null || invoiceMap.size() <= 0)
				{ 
					m_processMsg = "No invoice is created.";
					return DocAction.STATUS_Invalid;
				}
				else { // just to make sure The Tax & Bea Cukai are calculated & set in the invoice from it's lines.
					for (MInvoice inv : invoiceMap.values())
					{
						//FIXME will use at Sales Management
//						inv.recalculateBeaCukai();
						//inv.setProcessed(true);
						inv.processIt(DOCACTION_Prepare);
						//inv.save();
					}
				}
				setConfirmationStatus("Waiting for invoice completion of: " + getUncompletedInvoices());
				m_processMsg = "Invoices are created and waiting to be completed.";
				return DocAction.STATUS_InProgress;
			}
			catch (Exception ex) {
				ex.printStackTrace();
				m_processMsg = "Failed when creating Billing/Invoices.";
				return DocAction.STATUS_Invalid;
			}
		}
		// check if this shipment billing/invoice(s) are completed.
		m_processMsg = getUncompletedInvoices();
		if (m_processMsg != null)
		{
			setConfirmationStatus("Waiting for invoice completion of: " + m_processMsg);
			m_processMsg = "Still waiting these invoice(s) to be comleted: " + m_processMsg;
			return DocAction.STATUS_InProgress;
		}
		else {
			setConfirmationStatus("Invoices are completed.");
		}
				
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		/**
		 * move this lines into method of completeAllCustomerShipments();
		for(MUNSContainerDeparture cd : getContainerDeparture()){
			if(!MUNSContainerManagement.changeStatusContainer(getCtx(), get_TrxName(), cd.getUNS_ContainerManagement_ID(),
					MUNSContainerManagement.CURRENTSTATUS_ShippedToCustomer)){
				m_processMsg = "Can't update current status at Container management";
				return DocAction.STATUS_Invalid;
			}
		}
		*/
		
		setProcessed(true);
		// m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		if (get_ValueOld(COLUMNNAME_UNS_Kapal_ID) == null
			|| (!get_Value(COLUMNNAME_UNS_Kapal_ID).equals(get_ValueOld(COLUMNNAME_UNS_Kapal_ID))))
		{
			int C_Country_ID = getDropShip_Location().getC_Location().getC_Country_ID();
			
			String currentNextVoyageNo = 
					MUNSKapal.getCurrentNextVoyageNo(getCtx(), getUNS_Kapal_ID(), C_Country_ID, get_TrxName());
			
			setVoyage(currentNextVoyageNo);
		}
		
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean checkAvailability()
	{
		boolean availability = true;
		
		String retHeaderMsg = "";
//		Hashtable<String, BigDecimal> allocatedMap = new Hashtable<String, BigDecimal>();
		
		for(MUNSContainerDeparture cd : getContainerDeparture())
		{
			String containerNo = cd.getUNS_ContainerManagement().getContainerNo();
			
			for(MUNSSOShipment sos : cd.getSOShipments())
			{
				String retMsg = "";
//				int noteCount = 1;
				
				MProduct product = MProduct.get(getCtx(), sos.getM_Product_ID());
				String mmPolicy = product.getMMPolicy();
				
				MStorageOnHand[] sohList = MStorageOnHand.getWarehouse(
						getCtx(), sos.getM_Warehouse_ID(), 				// context, M_Warehouse_ID
						sos.getM_Product_ID(), 0,  						// M_Product_ID, M_AttributeSetInstance_ID
						null, MProductCategory.MMPOLICY_FiFo.equals(mmPolicy), 	// minGuaranteeDate, isFIFO 
						true, 0, get_TrxName()); 						// positveOnly, m_locator_id
				
				boolean needPrematureRequest = false;
				BigDecimal sosAllocationQty = sos.getQtyUom();
				
				for (MStorageOnHand soh : sohList)
				{
					if (sos.getMinProductionDate() != null)
					{
						// FIXME seharusnya PD diambil dari Attribute Instance utk mengakomodasi pengiriman barang
						// hasil pembelian.
						String sql = "SELECT p.ProductionDate " +
								"FROM UNS_Production p INNER JOIN UNS_Production_Detail pd " +
								"	  ON p.UNS_Production_ID=pd.UNS_Production_ID " +
								"WHERE pd.M_Product_ID=? AND pd.M_AttributeSetInstance_ID=?"; 
						
						Timestamp theASIPD = DB.getSQLValueTSEx(
								get_TrxName(), sql, soh.getM_Product_ID(), soh.getM_AttributeSetInstance_ID());
						if (theASIPD == null)
							continue; 
						
						if (theASIPD.before(sos.getMinProductionDate()))
							continue;
					}
//					String allocatedKey = sos.getM_Product_ID() + "-" + sos.getM_Warehouse_ID() + "-"
//							+ soh.getM_Locator_ID() + "-" + soh.getM_AttributeSetInstance_ID();
					//TODO Shipment Schedule
//					BigDecimal possibleToShipQty = 
//							soh.getQtyOnHand().subtract(soh.getOnHoldQty()).subtract(soh.getNCQty());
//					
//					BigDecimal allocatedQty = allocatedMap.get(allocatedKey);
//					
//					if (allocatedQty != null && allocatedQty.compareTo(possibleToShipQty) >= 0)
//						continue;
//					
//					BigDecimal availableToAllocate = 
//							allocatedQty == null? possibleToShipQty : possibleToShipQty.subtract(allocatedQty);
//					
//					allocatedQty = availableToAllocate;
//					
//					if (availableToAllocate.compareTo(sosAllocationQty) > 0)
//					{
//						allocatedQty = availableToAllocate.subtract(sosAllocationQty);
//						sosAllocationQty = Env.ZERO;
//					}
//					else {
//						sosAllocationQty = sosAllocationQty.subtract(allocatedQty);
//					}
//					
//					String locatorName = MLocator.get(getCtx(), soh.getM_Locator_ID()).getValue();
//
//					String sql = "SELECT Description FROM M_AttributeSetInstance WHERE M_AttributeSetInstance=?";
//					String asiDesc = DB.getSQLValueString(get_TrxName(), sql, soh.getM_AttributeSetInstance_ID());
//					
//					retMsg += noteCount++ + ". Allocated from " + locatorName + " :: " + asiDesc 
//						   + " qty of " + allocatedQty + " " + product.getUOMSymbol() ;
//					
//					if (soh.isInsideIncubationPeriod()) {
//						needPrematureRequest = true;
//						retMsg += " :: <<subject to premature release request>>.";
//					}
//					
//					retMsg += "\n";
//					
//					allocatedMap.put(allocatedKey, allocatedQty);
					
					if (sosAllocationQty.signum() == 0)
						break;
				}
				
				if (sosAllocationQty.signum() == 0) 
				{
					if (needPrematureRequest)
						retMsg = "Availability Status ::\n<<Can be fulfilled with premature request>>\n" + retMsg;
					else 
						retMsg = "Availability Status ::\n<<Fully fulfilled>>\n" + retMsg;
				}
				else {
					availability = false;
					
					if (needPrematureRequest)
						retMsg = "Availability Status ::\n<<Need premature request and requires new production " +
								"at least for qty of " + sosAllocationQty + product.getUOMSymbol() + ">>\n" + retMsg;
					else 
						retMsg = "Availability Status ::\n<<Requires new production " +
								"at least for qty of " + sosAllocationQty + product.getUOMSymbol() + ">>\n" + retMsg;
				}
				
				sos.setRemarks(retMsg);
				sos.saveEx();
				
				String sql = "SELECT DocumentNo FROM C_Order WHERE C_Order_ID=" +
						"(SELECT ol.C_Order_ID FROM C_OrderLine ol WHERE ol.C_OrderLine_ID=?)";
				String orderNo = DB.getSQLValueString(get_TrxName(), sql, sos.getC_OrderLine_ID());
						
				retHeaderMsg += "Availability Status for Container:" + containerNo 
							 + ", SO No:" + orderNo + " of Product:" + product.getValue() 
							 + "\n=====================================================\n" 
							 + retMsg + "\n";
			}
		}
		setRemarks(retHeaderMsg);
		saveEx();
		
		return availability;
	}
	
	private String validateQAHoldShipment() 
	{
		for(MUNSContainerDeparture cd : getContainerDeparture())
		{
			for(MUNSSOShipment sos : cd.getSOShipments())
			{
				MUNSQAStatusInOutLine line = 
						MUNSQAStatusInOutLine.get(getCtx(), sos.getM_InOutLine_ID(),
												  MUNSQAStatusInOutLine.COLUMNNAME_M_InOutLine_ID, 
												  get_TrxName());
				MUNSQAStatusInOut qasio = line.getParent();
				
				if(qasio.isHoldShipment()){
					if(!qasio.getDocStatus().equals(MUNSQAStatusInOut.DOCSTATUS_Completed))
						return qasio.getDocumentInfo();
				}
			}
		}
		return null;
	}

	/**
	 * Is there any shipment confirmation not completed yet?
	 * @return List of unconfirmed order id.
	 *
	private String validateConfirmation()
	{
		String listOfUnconfirmed = "";
		
		for(MUNSContainerDeparture cd : getContainerDeparture())
		{
			for(MUNSSOShipment sos : cd.getSOShipments())
			{
				MInOutLine shipmentLine = (MInOutLine) sos.getM_InOutLine();
				if (null == shipmentLine)
					throw new AdempiereUserError("Fatal Error: unsync data integration. Please contact your system administrator.");

				MInOut shipment = (MInOut) sos.getM_InOutLine().getM_InOut();
				if (null == shipment)
					throw new AdempiereUserError("Fatal Error: unsync data integration. Please contact your system administrator.");
					
				if (sos.getQtyUom().compareTo(BigDecimal.ZERO) <=0)
				{
					listOfUnconfirmed += sos.getM_Product().getValue() + " of " +
							sos.getC_OrderLine().getC_Order().getC_BPartnerSR().getValue() + "; ";
				}
			}
		}
		return (listOfUnconfirmed.length() > 1) ? listOfUnconfirmed : null;
	}
	*/

	/**
	 * Set Processed. Propagate to Lines
	 * 
	 * @param processed
	 *            processed
	 */
	public void setProcessed(boolean processed) {
		super.setProcessed(processed);
		setConfirmationStatus("All shipment processes are completely confirmed.");
		if (get_ID() == 0)
			return;
		/**
		String set = "SET Processed='" + (processed ? "Y" : "N")
				+ "' WHERE UNS_ShipmentSchedule_ID=" + getUNS_ShipmentSchedule_ID();
		int noLine = DB.executeUpdateEx("UPDATE UNS_Billing_Line " + set,
				get_TrxName());
		log.fine("setProcessed - " + processed + " - Lines=" + noLine);
		*/
	} // setProcessed

	@Override
	public File createPDF() {
		/*
		 * try { File temp = File.createTempFile(get_TableName()+get_ID()+"_",
		 * ".pdf"); return createPDF (temp); } catch (Exception e) {
		 * log.severe("Could not create PDF - " + e.getMessage()); }
		 */
		return null;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		return BigDecimal.ZERO;
	}

	@Override
	public int getDoc_User_ID() {
		return getCreatedBy();
	}

	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public String getSummary() {
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}

	@Override
	public boolean invalidateIt() {
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public boolean reActivateIt() {
		return false;
	}

	/**
	@Override
	public String getDocumentNo() {
		return getDocumentNo();
	}
	**/
	
	
	public static boolean cekShipment(Properties ctx, String trxName, int C_Order_ID)
	{
		MOrder order = new MOrder(ctx, C_Order_ID, trxName);
		for(MOrderLine ol : order.getLines()){
			if(ol.getCreateShipment().equalsIgnoreCase("N"))
				return false;
		}
		return true;
	}
	
	public static MInOut getSameInOut(Properties ctx, String trxName, int C_Order_ID, 
			Timestamp movementDate, int C_Org_ID, int docShipment_ID)
	{
		String wClauseFinal = MOrder.COLUMNNAME_C_Order_ID + "=" + C_Order_ID;
		
		List<MInOut> list = new Query(ctx, MInOut.Table_Name, wClauseFinal, trxName)
							.list();

		if(list.size()<1)
			return null;
		
		MInOut[] ioArray  = new MInOut[list.size()];
		list.toArray(ioArray);
		return ioArray[0];
	}

	public static void setOrderShipment(Properties ctx, String trxName, MOrderLine oline){
		oline.setCreateShipment("Y");
		if(!oline.save())
			throw new AdempiereException("Can't save order line");
		MOrder order = oline.getParent();
		
		if(cekShipment(ctx, trxName, order.getC_Order_ID())){
			order.setIsDelivered(true);
			if(!order.save())
				throw new AdempiereException("Can't save order");
		}
	}
		
	/**
	 * Create Shipment(To Customer) records for this shipment schedule details.
	 */
	public void createShipment()
	{
		Hashtable<String, MInOut> inoutMap = new Hashtable<String, MInOut>();
//		Hashtable<String, BigDecimal> allocatedMap = new Hashtable<String, BigDecimal>();
		Hashtable<Integer, MInOut> resettedMap = new Hashtable<Integer, MInOut>();
		
		for(MUNSContainerDeparture cd : getContainerDeparture())
		{
			for(MUNSSOShipment sos : cd.getSOShipments())
			{
				MOrderLine oline = new MOrderLine(getCtx(), sos.getC_OrderLine_ID(), get_TrxName());
				MOrder order = (MOrder) oline.getC_Order();
				MWarehouse theWHS = MWarehouse.get(getCtx(), sos.getM_Warehouse_ID(), get_TrxName());

				//if (DOCSTATUS_InProgress.equals(getDocStatus()) || sos.getM_InOutLine_ID() > 0)
				String tmpKey = getAllocationGroupingKey(order, sos);
				
				if (sos.getM_InOutLine_ID() > 0)
				{
					inoutMap.put(tmpKey, (MInOut) sos.getM_InOutLine().getM_InOut());
					continue;
				}
				
				if (oline.getQtyOrdered().compareTo(oline.getQtyDelivered()) <= 0)
					throw new AdempiereUserError("All order of '" + oline.getM_Product().getValue() + 
												 " for order '" + order.getDocumentNo() + "' has been fulfilled.", 
												 "You need to revise the shipment schedule.");
				
				MInOut io = inoutMap.get(tmpKey);
				if( io == null)
				{
					io = new MInOut(order, MDocType.getDocType(MDocType.DOCBASETYPE_MaterialDelivery), getETD());
					io.setAD_Org_ID(theWHS.getAD_Org_ID());
					io.setMovementType(MInOut.MOVEMENTTYPE_CustomerShipment);
					io.setAD_OrgTrx_ID(getAD_Org_ID());
					
					if (!io.save(get_TrxName()))
						throw new AdempiereException(CLogger.getCLogger (io.getClass()).toString());
					
					inoutMap.put(tmpKey, io);
				}
				else {
					if (DOCSTATUS_Completed.equals(io.getDocStatus()))
						continue;
					else if(DOCSTATUS_InProgress.equals(io.getDocStatus()))
					{
//						MInOutLine[] ioLines = io.getLines();
//						for (MInOutLine ioLine : ioLines)
//						{
							//TODO SHIPMENT SCHEDULE
//							MInOutLineMA[] mas = ioLine.getMAs();
//							if (mas == null || mas.length == 0)
//							{
//								String allocatedKey = sos.getM_Product_ID() + "-" + sos.getM_Warehouse_ID() + "-"
//										+ ioLine.getM_Locator_ID() + "-" + ioLine.getM_AttributeSetInstance_ID();
//								
//								BigDecimal allocatedQty = allocatedMap.get(allocatedKey);
//								
//								if (allocatedQty == null || allocatedQty.compareTo(ioLine.getMovementQty()) != 0)
//									allocatedMap.put(allocatedKey, ioLine.getMovementQty());
//							}
//							else {
//								for (MInOutLineMA ma : mas)
//								{
//									String allocatedKey = sos.getM_Product_ID() + "-" + sos.getM_Warehouse_ID() + "-"
//											+ ma.getM_Locator_ID() + "-" + ma.getM_AttributeSetInstance_ID();
//									
//									BigDecimal allocatedQty = allocatedMap.get(allocatedKey);
//									
//									if (allocatedQty == null || allocatedQty.compareTo(ma.getMovementQty()) != 0)
//										allocatedMap.put(allocatedKey, ma.getMovementQty());
//								}
//							}
//						}
//						continue;
					}
					
					if (resettedMap.get(io.get_ID()) != null)
						continue;
					
					//Delete lines and all it's MAs.
					String sql = "DELETE FROM M_InOutLineMA WHERE M_InOutLine_ID IN " +
							" (SELECT iol.M_InOutLine_ID FROM M_InOutLine iol WHERE iol.M_InOut_ID=?)";
					DB.executeUpdateEx(sql, get_TrxName(), io.get_ID());
					
					sql = "DELETE FROM M_InOutLine WHERE M_InOut_ID=?";
					DB.executeUpdateEx(sql, get_TrxName(), io.get_ID());
					
					resettedMap.put(io.get_ID(), io);
				}
				
				MInOutLine ioLine = new MInOutLine(io);
				ioLine.setC_OrderLine_ID(oline.getC_OrderLine_ID());
				ioLine.setLine(oline.getLine());
				ioLine.setC_UOM_ID(oline.getC_UOM_ID());
				ioLine.setM_AttributeSetInstance_ID(sos.getM_AttributeSetInstance_ID());
				ioLine.setM_Product_ID(oline.getM_Product_ID());
				ioLine.setDescription(
						sos.getDescription() + "\n" + sos.getRemarkProduct() + "\n" + sos.getRemarkSO());
				//ioLine.setC_Charge_ID(oLine.getC_Charge_ID());
				//ioLine.setOrderLine(oline, 0, sos.getQtyUom());
				ioLine.setQty(sos.getQtyUom());
				ioLine.saveEx();
				
				MProduct product = MProduct.get(getCtx(), sos.getM_Product_ID());
				String mmPolicy = product.getMMPolicy();
				
				MStorageOnHand[] sohList = MStorageOnHand.getWarehouse(
						getCtx(), sos.getM_Warehouse_ID(), 				// context, M_Warehouse_ID
						sos.getM_Product_ID(), 0,  						// M_Product_ID, M_AttributeSetInstance_ID
						null, MProductCategory.MMPOLICY_FiFo.equals(mmPolicy), 	// minGuaranteeDate, isFIFO 
						true, 0, get_TrxName()); 						// positveOnly, m_locator_id
				
				BigDecimal sosAllocationQty = sos.getQtyUom();
				
				for (MStorageOnHand soh : sohList)
				{
					if (sos.getMinProductionDate() != null)
					{
						// FIXME seharusnya PD diambil dari Attribute Instance utk mengakomodasi pengiriman barang
						// hasil pembelian.
						String sql = "SELECT p.ProductionDate " +
								"FROM UNS_Production p INNER JOIN UNS_Production_Detail pd " +
								"	  ON p.UNS_Production_ID=pd.UNS_Production_ID " +
								"WHERE pd.M_Product_ID=? AND pd.M_AttributeSetInstance_ID=?"; 
						
						Timestamp theASIPD = DB.getSQLValueTSEx(
								get_TrxName(), sql, soh.getM_Product_ID(), soh.getM_AttributeSetInstance_ID());
						if (theASIPD == null)
							continue; 
						
						if (theASIPD.before(sos.getMinProductionDate()))
							continue;
					}
					
//					String allocatedKey = sos.getM_Product_ID() + "-" + sos.getM_Warehouse_ID() + "-"
//							+ soh.getM_Locator_ID() + "-" + soh.getM_AttributeSetInstance_ID();
				//TODO SHIPMENT SCHEDULE
//					BigDecimal possibleToShipQty = 
//							soh.getQtyOnHand().subtract(soh.getOnHoldQty()).subtract(soh.getNCQty());
//					
//					BigDecimal allocatedQty = allocatedMap.get(allocatedKey);
//					
//					if (allocatedQty != null && allocatedQty.compareTo(possibleToShipQty) >= 0)
//						continue;
//					
//					BigDecimal availableToAllocate = 
//							allocatedQty == null? possibleToShipQty : possibleToShipQty.subtract(allocatedQty);
//					
//					allocatedQty = availableToAllocate;
//					
//					if(sohList.length==1) 
//					{
//						ioLine.setM_AttributeSetInstance_ID(soh.getM_AttributeSetInstance_ID());
//						ioLine.setM_Locator_ID(soh.getM_Locator_ID());
//						sos.setM_AttributeSetInstance_ID(soh.getM_AttributeSetInstance_ID());
//						sosAllocationQty = Env.ZERO;
//						break;
//					}
//					else if (availableToAllocate.compareTo(sosAllocationQty) > 0)
//					{
//						allocatedQty = availableToAllocate.subtract(sosAllocationQty);
//						sosAllocationQty = Env.ZERO;
//					}
//					else {
//						sosAllocationQty = sosAllocationQty.subtract(allocatedQty);
//					}
//					
//					ioLine.setM_Locator_ID(null); // Just set the line's locator to null.
//					
//					MInOutLineMA ma = new MInOutLineMA(ioLine, soh.getM_AttributeSetInstance_ID(), allocatedQty);
//					ma.setM_Locator_ID(soh.getM_Locator_ID());
//					ma.saveEx();
//					
//					allocatedMap.put(allocatedKey, allocatedQty);
					
					if (sosAllocationQty.signum() == 0)
						break;
				}
				
				if (sosAllocationQty.signum() > 0) {
					throw new AdempiereException("No stock available. " +
							"Probably there are more than one Shipment process running asynchronously.");
				}
				
				ioLine.saveEx();
				sos.setM_InOutLine_ID(ioLine.get_ID());
				
				//oline.setQtyDelivered(oline.getQtyDelivered().add(sos.getQtyUom()));
				oline.setQtyReserved(oline.getQtyReserved().add(ioLine.getConfirmedQty()));
				if(!oline.save())
					throw new AdempiereException("Can't save order line");
				
				if (!sos.save())
					throw new AdempiereException ("Error when setting Shipment Line detail to Shipment SO Allocation.");
			}
		}
		if (inoutMap.isEmpty())
		{
			log.info("MUNSShipmentSchedule.createShipment: No Shipment (to customer) were created.");
			return;
		}
	}

	/**
	 * Create Shipment(To Customer) records for this shipment schedule details.
	 */
	public void createShipment_OLD()
	{
		Hashtable<String, MInOut> inoutMap = new Hashtable<String, MInOut>();
		
		for(MUNSContainerDeparture cd : getContainerDeparture())
		{
			for(MUNSSOShipment sos : cd.getSOShipments())
			{
				MOrderLine oline = new MOrderLine(getCtx(), sos.getC_OrderLine_ID(), get_TrxName());
				MOrder order = (MOrder) oline.getC_Order();

				//if (DOCSTATUS_InProgress.equals(getDocStatus()) || sos.getM_InOutLine_ID() > 0)
				String tmpKey = getAllocationGroupingKey(order, sos);
				
				if (sos.getM_InOutLine_ID() > 0)
				{
					inoutMap.put(tmpKey, (MInOut) sos.getM_InOutLine().getM_InOut());
					continue;
				}
				
				if (oline.getQtyOrdered().compareTo(oline.getQtyDelivered()) <= 0)
					throw new AdempiereUserError("All order of '" + oline.getM_Product().getValue() + 
												 " for order '" + order.getDocumentNo() + "' has been fulfilled.", 
												 "You need to revise the shipment schedule.");
				
				MInOut io = inoutMap.get(tmpKey);
				if( io == null)
				{
					io = new MInOut(order, MDocType.getDocType(MDocType.DOCBASETYPE_MaterialDelivery), getETD());
					io.setAD_Org_ID(getShipReceiveDept_ID());
					io.setMovementType(MInOut.MOVEMENTTYPE_CustomerShipment);
					io.setAD_OrgTrx_ID(getAD_Org_ID());
					
					if (!io.save(get_TrxName()))
						throw new AdempiereException(CLogger.getCLogger (io.getClass()).toString());
					
					inoutMap.put(tmpKey, io);
				}
				
				MInOutLine ioLine = new MInOutLine(io);
				ioLine.setC_OrderLine_ID(oline.getC_OrderLine_ID());
				ioLine.setLine(oline.getLine());
				ioLine.setC_UOM_ID(oline.getC_UOM_ID());
				ioLine.setM_AttributeSetInstance_ID(sos.getM_AttributeSetInstance_ID());
				ioLine.setM_Product_ID(oline.getM_Product_ID());
				ioLine.setDescription(
						sos.getDescription() + "\n" + sos.getRemarkProduct() + "\n" + sos.getRemarkSO());
				//ioLine.setC_Charge_ID(oLine.getC_Charge_ID());
				//ioLine.setOrderLine(oline, 0, sos.getQtyUom());
				ioLine.setQty(sos.getQtyUom());
				
				MStorageOnHand[] listSoh= generateSOH(getCtx(), ioLine.getM_Product_ID(), 
						ioLine.getM_Locator_ID(), ioLine.getQtyEntered(), get_TrxName());
				
				if(listSoh.length==1) 
				{
					ioLine.setM_AttributeSetInstance_ID(listSoh[0].getM_AttributeSetInstance_ID());
					ioLine.setM_Locator_ID(listSoh[0].getM_Locator_ID());
					sos.setM_AttributeSetInstance_ID(listSoh[0].getM_AttributeSetInstance_ID());
				}
				else {
					
//					BigDecimal count = ioLine.getQtyEntered();
//					for(MStorageOnHand soh : listSoh)
//					{
					//TODO SHIPMENT SCHEDULE
//						MInOutLineMA ma = new MInOutLineMA(ioLine, soh.getM_AttributeSetInstance_ID(), count);
//						ma.saveEx();
//						count = count.subtract(soh.getReleaseQty());
//					}
				}
				if (!ioLine.save(get_TrxName()))
					throw new AdempiereException(CLogger.getCLogger (ioLine.getClass()).toString());
				sos.setM_InOutLine_ID(ioLine.get_ID());
				
				createInOutLineMAFor(sos, false);
				
				//Create new InOut Confirm or return exiting record base InOut
//				MInOutConfirm ioConfirm = MInOutConfirm.create(io, MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm, true);
//				//Check the confirm new or not
//				if(!(ioConfirm.getLines(false)[0].getM_InOutLine_ID()==ioLine.get_ID()))
//				{
//					MInOutLineConfirm ioLConfirm = new MInOutLineConfirm(ioConfirm);
//					ioLConfirm.setInOutLine(ioLine);
//					if (!ioLConfirm.save(get_TrxName()))
//						throw new AdempiereException("Can't save shipment confirmation line");
//				}
//				
//				oline.setCreateShipment("Y");
				//oline.setQtyDelivered(oline.getQtyDelivered().add(sos.getQtyUom()));
				oline.setQtyReserved(oline.getQtyReserved().add(ioLine.getConfirmedQty()));
				if(!oline.save())
					throw new AdempiereException("Can't save order line");
				
				if (!sos.save())
					throw new AdempiereException ("Error when setting Shipment Line detail to Shipment SO Allocation.");
			}
		}
		if (inoutMap.isEmpty())
		{
			log.info("MUNSShipmentSchedule.createShipment: No Shipment (to customer) were created.");
			return;
		}
//		for (MInOut io : inoutMap.values())
//		{
//			createConfirmation(io);
//		}
	}
	
	
	/**
	 * 	Create the missing next Confirmation
	 */
	public void createConfirmation(MInOut io)
	{
		boolean ship = true;
		//	Nothing to do
		
		if (!ship)
		{
			log.fine("No need");
			return;
		}

		if (ship)
		{
			boolean havePick = false;
			boolean haveShip = false;
			MInOutConfirm[] confirmations = io.getConfirmations(false);
			for (int i = 0; i < confirmations.length; i++)
			{
				MInOutConfirm confirm = confirmations[i];
				if (MInOutConfirm.CONFIRMTYPE_PickQAConfirm.equals(confirm.getConfirmType()))
				{
					if (!confirm.isProcessed())		//	wait intil done
					{
						log.fine("Unprocessed: " + confirm);
						return;
					}
					havePick = true;
				}
				else if (MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm.equals(confirm.getConfirmType()))
					haveShip = true;
			}
			//	Create Pick
			if (!havePick)
			{
				// No need for now (harr: 2013.03.25)
				//MInOutConfirm.create (io, MInOutConfirm.CONFIRMTYPE_PickQAConfirm, false);
				//return;
			}
			//	Create Ship
			if (!haveShip)
			{
				MInOutConfirm.create (io, MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm, false);
				return;
			}
			return;
		}
		//	Create just one
		if (ship) {
			MInOutConfirm confirm = MInOutConfirm.create (io, MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm, true);
			confirm.setAD_Org_ID(getShipReceiveConfirmDept_ID());
			confirm.save();
			for (MInOutLineConfirm lineConf : confirm.getLines(false))
			{
				lineConf.setAD_Org_ID(getShipReceiveConfirmDept_ID());
				lineConf.save();
			}
		}
	}	//	createConfirmation

	
	/**
	 * Create Shipment(To Customer) records for this shipment schedule details.
	 */
	public void createShipment_Old()
	{
		for(MUNSContainerDeparture cd : getContainerDeparture())
		{
			for(MUNSSOShipment sos : cd.getSOShipments())
			{
				if (DOCSTATUS_InProgress.equals(getDocStatus()) && sos.getM_InOutLine_ID() > 0)
					continue;
				MOrderLine oline = new MOrderLine(getCtx(), sos.getC_OrderLine_ID(), get_TrxName());
				MOrder order = (MOrder) oline.getC_Order();
				
				if (oline.getQtyOrdered().compareTo(oline.getQtyDelivered()) <= 0)
					throw new AdempiereUserError("All order of '" + oline.getM_Product().getValue() + 
												 " for order '" + order.getDocumentNo() + "' has been fulfilled.", 
												 "You need to revise the shipment schedule.");
				
				MInOut io = getSameInOut(getCtx(), get_TrxName(), order.get_ID(), getETD(), getAD_Org_ID(), 
						MDocType.getDocType(MDocType.DOCBASETYPE_MaterialDelivery));
				if( io == null){
					io = new MInOut(order, MDocType.getDocType(MDocType.DOCBASETYPE_MaterialDelivery), getETD());
					if (!io.save(get_TrxName()))
						throw new AdempiereException("Can't save shipment");
				}
				
				MInOutLine ioLine = new MInOutLine(io);
				//ioLine.setOrderLine(oline, sos.getM_Locator_ID(), sos.getQtyUom());
				ioLine.setQty(sos.getQtyUom());
				ioLine.setM_AttributeSetInstance_ID(sos.getM_AttributeSetInstance_ID());
				if (!ioLine.save(get_TrxName()))
					throw new AdempiereException("Can't save shipment line");
				sos.setM_InOutLine_ID(ioLine.get_ID());
				
				createInOutLineMAFor(sos, false);
				
				//Create new InOut Confirm or return exiting record base InOut
				MInOutConfirm ioConfirm = MInOutConfirm.create(io, MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm, true);
				//Check the confirm new or not
				if(!(ioConfirm.getLines(false)[0].getM_InOutLine_ID()==ioLine.get_ID()))
				{
					MInOutLineConfirm ioLConfirm = new MInOutLineConfirm(ioConfirm);
					ioLConfirm.setInOutLine(ioLine);
					if (!ioLConfirm.save(get_TrxName()))
						throw new AdempiereException("Can't save shipment confirmation line");
				}
				
				oline.setCreateShipment("Y");
				//oline.setQtyDelivered(oline.getQtyDelivered().add(sos.getQtyUom()));
				if(!oline.save())
					throw new AdempiereException("Can't save order line");
				
				if(cekShipment(getCtx(), get_TrxName(), order.getC_Order_ID())){
					order.setIsDelivered(true);
					if(!order.save())
						throw new AdempiereException("Can't save order");
				}
				if (!sos.save())
					throw new AdempiereException ("Error when setting Shipment Line detail to Shipment SO Allocation.");
			}
		}
	}
	
	/**
	 * 
	 * @param sos
	 * @param useConfirmedQty
	 */
	private void createInOutLineMAFor (MUNSSOShipment sos, boolean useConfirmedQty)
	{
//		MInOutLine ioLine = (MInOutLine) sos.getM_InOutLine();
		
		BigDecimal unallocatedQty = (useConfirmedQty) ? sos.getQtyUom() : sos.getQtyUom();
		
		BigDecimal totalAvailableQty = BigDecimal.ZERO;

		// Get only positives storages of the product in the warehouse.
//		MStorageOnHand[] sohList = 
//				MStorageOnHand.getWarehouse(getCtx(), 
//						getM_Warehouse_ID(), sos.getM_Product_ID(), 0, null, true, true, 0, 
//						get_TrxName());
		
//		for (MStorageOnHand soh : sohList)
//		{
		//TODO SHIPMENT SCHEDULE
//			if (soh.isInsideIncubationPeriod())
//				continue;
//			
//			BigDecimal availableQty = soh.getReleaseQty();
//			
//			if (availableQty.compareTo(BigDecimal.ZERO) <= 0)
//				continue;
//
//			totalAvailableQty = totalAvailableQty.add(availableQty);
//			BigDecimal movementQty = (availableQty.compareTo(unallocatedQty)>=0)? unallocatedQty : availableQty;
//			unallocatedQty = unallocatedQty.subtract(movementQty);
//			
//			MInOutLineMA ioLineMA = 
//					new MInOutLineMA(ioLine, soh.getM_AttributeSetInstance_ID(), movementQty);
//			ioLineMA.setM_InOutLine_ID(ioLine.get_ID());
//			if (!ioLineMA.save())
//				throw new AdempiereException("Failed when creating shipment detail attributes.");
//			if (unallocatedQty.compareTo(BigDecimal.ZERO) == 0)
//				break;
//		}
		if (unallocatedQty.compareTo(BigDecimal.ZERO) > 0)
			throw new AdempiereUserError("The shipment schedule quantity for product of '" + sos.getM_Product().getValue() +
										 "' exceeding available on hand qty: " + totalAvailableQty);
		
	}
	
	/**
	 * 
	 * @param sos
	 * @param useConfirmedQty
	 */
	void createInOutLineMAFor_Old (MUNSSOShipment sos, boolean useConfirmedQty)
	{
		MOrderLine oline = (MOrderLine) sos.getC_OrderLine();
		int M_Warehouse_ID = oline.getC_Order().getM_Warehouse_ID();
//		MInOutLine ioLine = (MInOutLine) sos.getM_InOutLine();
		//check if there are storage available for the ordered product.
		MAttributeSetInstance[] availableMas = getAvailableProductFor(oline, M_Warehouse_ID);
		if (availableMas == null)
			throw new AdempiereUserError("There are no available storage on hand left for product '" + 
										 oline.getM_Product().getValue() + "'", 
										 "You need to revise the shipment schedule.");

		BigDecimal unallocatedQty;
		if (useConfirmedQty)
			unallocatedQty = sos.getQtyUom();
		else
			unallocatedQty = sos.getQtyUom();
		
		BigDecimal totalAvailableQty = BigDecimal.ZERO;
		for (int i=0; i < availableMas.length; i++)
		{
			BigDecimal availableQty = MStorageOnHand.getQtyOnHand(oline.getM_Product_ID(), 
																  M_Warehouse_ID, 
																  availableMas[i].getM_AttributeSetInstance_ID(), 
																  get_TrxName());
			if (availableQty.compareTo(BigDecimal.ZERO) <= 0)
				continue;
			totalAvailableQty = totalAvailableQty.add(availableQty);
			BigDecimal movementQty = (availableQty.compareTo(unallocatedQty)>=0)? unallocatedQty : availableQty;
			unallocatedQty = unallocatedQty.subtract(movementQty);
			if (i == (availableMas.length - 1) && (unallocatedQty.compareTo(BigDecimal.ZERO) > 0))
				throw new AdempiereUserError("The shipment schedule quantity for product of '" + sos.getM_Product().getValue() +
											 "' exceeding available order allocation qty: " + totalAvailableQty);
			
		//TODO SHIPMENT SCHEDULE
//			MInOutLineMA ioLineMA = 
//					new MInOutLineMA(ioLine, availableMas[i].getM_AttributeSetInstance_ID(), movementQty);
//			ioLineMA.setM_InOutLine_ID(ioLine.get_ID());
//			if (!ioLineMA.save())
//				throw new AdempiereException("Failed when creating shipment detail attributes.");
			if (unallocatedQty.compareTo(BigDecimal.ZERO) == 0)
				break;
		}
	}
	
	/**
	 * 
	 * @param orderLine
	 * @return
	 */
	private MAttributeSetInstance[] getAvailableProductFor (I_C_OrderLine orderLine, int M_Warehouse_ID)
	{
		MAttributeSetInstance[] productAttributes = null;
		//MStorageOnHand[] storageList = MStorageOnHand.getOfProduct(getCtx(), orderLine.getM_Product_ID(), get_TrxName());
		
		String sql = "SELECT pdma.M_AttributeSetInstance_ID FROM " + X_UNS_Production_DetailMA.Table_Name + " pdma " +
				" INNER JOIN UNS_PSRealization psr ON pdma.UNS_Production_Detail_ID=psr.UNS_Production_Detail_ID " +
				" INNER JOIN UNS_PSSOAllocation pssoa ON psr.UNS_ProductionSchedule_ID=pssoa.UNS_ProductionSchedule_ID " +
				" WHERE pssoa.C_OrderLine_ID=" + orderLine.getC_OrderLine_ID() + " ORDER BY M_AttributeSetInstance_ID ASC";
		
		PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName());
		Vector<MAttributeSetInstance> masList = new Vector<MAttributeSetInstance>();
		try 
		{
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				MAttributeSetInstance mas = new MAttributeSetInstance(getCtx(), rs.getInt(1), get_TrxName());
				BigDecimal qtyOnHand = 
						MStorageOnHand.getQtyOnHand(orderLine.getM_Product_ID(), 
												    M_Warehouse_ID, mas.getM_AttributeSetInstance_ID(), 
												    get_TrxName());
				if (qtyOnHand.compareTo(BigDecimal.ZERO) > 0)
				{
					masList.add(mas);
				}
			}
		}
		catch (SQLException ex)
		{
			throw new AdempiereException("Failed when getting available product attributes.");
		}
		if (masList.size() > 0)
		{
			productAttributes = new MAttributeSetInstance[masList.size()];
			masList.copyInto(productAttributes);
		}
		return productAttributes;
	}

	/**
	 * 
	 * @return
	 */
	public Hashtable<String, MInvoice> createInvoices() throws Exception
	{
		Hashtable<String, MInvoice> invoiceMap = new Hashtable<String, MInvoice>();

		for(MUNSContainerDeparture cd : getContainerDeparture())
		{
			for(MUNSSOShipment sos : cd.getSOShipments())
			{
				if (sos.getQtyUom().compareTo(BigDecimal.ZERO) <= 0)
				{
					sos.setDescription("Canceled not included in the current shipment (zero confirmed quantity).");
					sos.save();
					continue;
				}
				
				MOrderLine orderLine = (MOrderLine) sos.getC_OrderLine();
				MOrder order = (MOrder) orderLine.getC_Order();
				String tmpKey = getAllocationGroupingKey(order, sos);
				MInvoice inv = invoiceMap.get(tmpKey);
				if (inv == null)
				{
					inv = new MInvoice(order, MDocType.getDocType(MDocType.DOCBASETYPE_ARInvoice), getETD());
					inv.setAD_Org_ID(getInvoicingDept_ID());
					inv.setDocStatus(MInvoice.DOCSTATUS_Drafted);
					inv.setDocAction(MInvoice.DOCACTION_Prepare);
					//FIXME add BEA CUKAI at Shipment
//					inv.setUNS_BCCode_ID(sos.getUNS_BCCode_ID());
//					inv.setBCDocNo("[Replace this with proper Custom (BC) Document RefNo.]");
//					inv.setUNS_ShipmentSchedule_ID(getUNS_ShipmentSchedule_ID());
					inv.setProcessed(false);
					if (!inv.save())
						throw new AdempiereUserError("Can't create Invoice.");
					invoiceMap.put(tmpKey, inv);
				}
				
				MInvoiceLine invLine = new MInvoiceLine(inv);
				invLine.setQty(sos.getQtyUom());
				invLine.setQtyEntered(sos.getQtyUom());
				invLine.setQtyInvoiced(sos.getQtyUom());
				invLine.setShipLine((MInOutLine) sos.getM_InOutLine());
				invLine.setPrice(order.getM_PriceList_ID(), order.getC_BPartner_ID(), order.getC_BPartner_Location_ID());
				invLine.setPriceActual(orderLine.getPriceActual());
				invLine.setPriceEntered(orderLine.getPriceEntered());
				//invLine.setM_InOutLine_ID(sos.getM_InOutLine_ID());
				invLine.setLineNetAmt();
				if (!invLine.save())
					throw new AdempiereUserError("Failed when saving Invoice Line.");
				/*
				if (sos.getUNS_BCCode_ID() > 0 
						&& X_UNS_BCCode.BCSTATUS_Local_Out.equals(sos.getUNS_BCCode().getBCStatus()))
				{
					MInOutLineMA[] iolMA = MInOutLineMA.get(getCtx(), sos.getM_InOutLine_ID(), get_TrxName());
					MUNSProductionDetail[] pdList = null;
					if(iolMA.length>0){
						pdList = MUNSProductionDetail.getDetailsOf(getCtx(), iolMA, get_TrxName());
					} else {
						pdList = MUNSProductionDetail.getDetail(getCtx(), sos.getM_InOutLine().getM_AttributeSetInstance_ID(), get_TrxName());
					}
					for (MUNSProductionDetail pd : pdList)
					{
						BigDecimal actualQtyUsed = getInOutLineMAQty(iolMA, pd.getM_AttributeSetInstance_ID());
						MUNSProduction production = (MUNSProduction) pd.getUNS_Production();
						for(MUNSImportContent ic : production.getImportContent())
							if(null == MUNSInvoiceBC.createFromIC(getCtx(), get_TrxName(), ic, 
																  pd.getMovementQty(), actualQtyUsed, invLine))//sos.getConfirmedQty(), invLine))
								throw new AdempiereUserError("Failed when creating import content list.");
					}
				}
				*/
				MInOutLine ioLine = (MInOutLine) sos.getM_InOutLine();
				ioLine.setIsInvoiced(true);
				// just to make sure it has been set.
				ioLine.setM_AttributeSetInstance_ID(sos.getM_AttributeSetInstance_ID());
				if (!ioLine.save())
					throw new AdempiereException ("Failed when setting shipment detail as being invoiced.");
				
				sos.setC_InvoiceLine_ID(invLine.getC_InvoiceLine_ID());
				if (!sos.save())
					throw new AdempiereException("Failed when setting invoice line.");

				setCreateInvoice(true);
				saveEx();
			} 
		}
		return invoiceMap;
	}
	
	/**
	 * 
	 * @param order
	 * @param sos
	 * @return
	 */
	private String getAllocationGroupingKey(MOrder order, MUNSSOShipment sos)
	{
		String tmpKey = order.getC_BPartner_ID() + "-" + sos.getUNS_BCCode().getValue();
		
		return tmpKey;
	}
	
	/**
	 * 
	 * @param iolMAList
	 * @param M_ASI
	 * @return
	 *
	private BigDecimal getInOutLineMAQty (MInOutLineMA[] iolMAList, int M_ASI)
	{
		BigDecimal qtyUsed = BigDecimal.ZERO;
		
		for (MInOutLineMA iolMA : iolMAList)
		{
			if (iolMA.getM_AttributeSetInstance_ID() != M_ASI)
				continue;
			qtyUsed = iolMA.getMovementQty();
		}
		
		return qtyUsed;
	}
	*/
	
	/**
	 * 
	 * @return null if all shipment document are completed, otherwise return the uncompleted document no.
	 */
	private String checkCustomerShipmentsCompletionStatus()
	{
		String msg = "";
		for(MUNSContainerDeparture cd : getContainerDeparture())
		{
			for(MUNSSOShipment sos : cd.getSOShipments())
			{
				MInOutLine ioLine = (MInOutLine) sos.getM_InOutLine();
				MInOut io = (MInOut) ioLine.getM_InOut();
				if (DOCSTATUS_Completed.equals(io.getDocStatus()))
					continue;
				
				if (!msg.isEmpty())
					msg += "; ";
				
				msg += io.getDocumentNo();
			}
			if (!cd.updateContainerManagementStatus())
				throw new AdempiereException("Failed when updating container management status.");
		}
		return "".equals(msg)? null : msg;
	}
	
	/**
	 * Try to complete all customer shipment records.
	 * @return null or a message if there is an error in a customer shipment record. 
	 *
	private String completeAllCustomerShipments()
	{
		String msg = "";
		for(MUNSContainerDeparture cd : getContainerDeparture())
		{
			for(MUNSSOShipment sos : cd.getSOShipments())
			{
				MInOutLine ioLine = (MInOutLine) sos.getM_InOutLine();
				MInOut io = (MInOut) ioLine.getM_InOut();
				if (DOCSTATUS_Completed.equals(io.getDocStatus()))
					continue;
				// Remaks these lines as ASI can be connected with import contents and have already calculated in
				// the invoices.
				//delete all MInOutLineMA and replaced with the new ones.
				DB.executeUpdate("DELETE FROM M_InOutLineMA WHERE M_InOutLine_ID=" + sos.getM_InOutLine_ID(), get_TrxName());
				createInOutLineMAFor(sos, true);
				
				//update order-line delivered-qty with the new sos-confirmedqty.
				MOrderLine orderLine = (MOrderLine) sos.getC_OrderLine();
				orderLine.setQtyDelivered(orderLine.getQtyDelivered().add(sos.getQtyUom()));
				orderLine.setQtyInvoiced(orderLine.getQtyInvoiced().add(sos.getQtyUom()));
				orderLine.save();
				//String ioDocStatus = io.getDocStatus();
				if (DOCSTATUS_WaitingConfirmation.equals(io.getDocStatus()) 
						|| DOCSTATUS_Drafted.equals(io.getDocStatus())
						|| DOCSTATUS_InProgress.equals(io.getDocStatus())) {
					io.processIt(DOCACTION_Complete);
				}
				else if (DOCSTATUS_Invalid.equals(io.getDocStatus())) {
					msg = "You have invalid shipment document no. '" + io.getDocumentNo() + 
						  "'. Please revise the shipment document manualy.";
				}
				//create match invoice.
				/**
				 * Sales order doesnt need match inv.
				MInvoiceLine invLine = MInvoiceLine.getOfInOutLine(ioLine);
				MMatchInv mInv = new MMatchInv(invLine, io.getUpdated(), sos.getConfirmedQty());
				if (!mInv.save())
					throw new AdempiereUserError("Match Invoice-Shipment can not be saved.");
				*
				
			}
			if (!cd.updateContainerManagementStatus())
				throw new AdempiereException("Failed when updating container management status.");
		}
		return "".equals(msg)? null : msg;
	}
	*/
	/**
	 * To check if 
	 * @return
	 */
	public static boolean isInvoiceCreated(String trxName, int UNS_ShipmentSchedule_ID)
	{
		int result = 
				DB.getSQLValue(trxName, 
							  "SELECT count(C_Invoice_ID) FROM C_Invoice " +
							  "WHERE UNS_ShipmentSchedule_ID=" + UNS_ShipmentSchedule_ID);
		if (result <= 0)
			return false;
		
		return true;
	}
	
	/**
	 * 
	 * @return null if all of this shipment's invoices are completed or closed. 
	 */
	private String getUncompletedInvoices()
	{
		String uncompletedInvoices = "";
		
		List<MInvoice> invoices = 
				//new Query(getCtx(), MInvoice.Table_Name, 
				//		" UNS_ShipmentSchedule_ID="+getUNS_ShipmentSchedule_ID(), get_TrxName())
				com.uns.base.model.Query.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID, MInvoice.Table_Name,  
								" UNS_ShipmentSchedule_ID="+getUNS_ShipmentSchedule_ID(), get_TrxName())
				.list();
		for (MInvoice invoice : invoices)
		{
			if (!DOCSTATUS_Completed.equals(invoice.getDocStatus()) 
					&& !DOCSTATUS_Closed.equals(invoice.getDocStatus()))
				uncompletedInvoices += invoice.getDocumentNo() + "; ";
		}
		return "".equals(uncompletedInvoices)? null : uncompletedInvoices;
	}
	
	private String checkContainerInspection()
	{
		String msg = null;
		if (0==getUNS_QAContainerInspection_ID())
		{
			MUNSQAContainerInspection ci = new MUNSQAContainerInspection(getCtx(), 0, get_TrxName());
			ci.createContainerInspection(this);
			msg = "Please complete container inspection : " + ci.getDocumentNo()
					+ ", before proceeding to next process. ";
			setConfirmationStatus(msg);
			setUNS_QAContainerInspection_ID(ci.get_ID());
		} 
		else {
		//if (getUNS_QAContainerInspection_ID()!=0){
			MUNSQAContainerInspection ci = new MUNSQAContainerInspection(getCtx(), getUNS_QAContainerInspection_ID(), get_TrxName());
			msg = "";
			for(MUNSQAStatusContainer qasc : ci.getLines())
			{
				if (qasc.getQAStatus().equals(X_UNS_QAStatusContainer.QASTATUS_Release)){
					continue;
				}
				msg = msg + "Container not (QA) released yet: " + ci.getDocumentInfo() + "\n";
				setConfirmationStatus(msg);
			}
			msg = msg.isEmpty()? null : msg;
		}
		
		//if(!save(get_TrxName()))
		//	throw new AdempiereException("Error when create container inspection");
		saveEx();
		return msg;
	}
	
	/**
	 * 
	 * @return
	 */
	private String isNeedPrematureRelease()
	{
		String msg = "";
		
		for (MUNSContainerDeparture containerList : getContainerDeparture())
		{
			for (MUNSSOShipment soShipment : containerList.getSOShipments())
			{
				MInOutLine ioLine = (MInOutLine) soShipment.getM_InOutLine();
				
//				MProduct product = (MProduct) soShipment.getM_Product();
				
				if (ioLine.getM_Locator_ID() > 0 && ioLine.getM_AttributeSetInstance_ID() > 0)
				{
//					MStorageOnHand storage = 
//							MStorageOnHand.get(getCtx(), ioLine.getM_Locator_ID(), 
//											   product.getM_Product_ID(), 
//											   ioLine.getM_AttributeSetInstance_ID(), 
//											   get_TrxName());
				//TODO SHIPMENT SCHEDULE
//					if (storage.isInsideIncubationPeriod()) 
//					{
//						if (msg != null && !msg.isEmpty())
//							msg += "::";
//						String locatorName = MLocator.get(getCtx(), ioLine.getM_Locator_ID()).getValue();
//						msg += "[" + locatorName + "-" + ioLine.getM_AttributeSetInstance().getDescription() + "]";
//						/*
//						msg = "There are some products inside the incubation period. " +
//								"You need to create premature release request.";
//						return msg;
//						*/
//					}
					/*
					if (storage.getReleaseQty().compareTo(ioLine.getMovementQty()) < 0) {
						msg = "There are some products do not have sufficient released quantity. " +
								"You need to create premature release request.";
						return msg;
					}
					*/
				}
				else {
//					MInOutLineMA[] ioLMAList = MInOutLineMA.get(getCtx(), ioLine.get_ID(), get_TrxName());
					
//					for (MInOutLineMA ioLMA : ioLMAList)
//					{
					//TODO SHIPMENT SCHEDULE
//						MStorageOnHand storage = 
//								MStorageOnHand.get(getCtx(), ioLMA.getM_Locator_ID(), 
//												   product.getM_Product_ID(), 
//												   ioLMA.getM_AttributeSetInstance_ID(), 
//												   get_TrxName());
//						if (storage.isInsideIncubationPeriod()) 
//						{
//							if (msg != null && !msg.isEmpty())
//								msg += "::";
//							String locatorName = MLocator.get(getCtx(), ioLine.getM_Locator_ID()).getValue();
//							msg += "[" + locatorName + "-" + ioLine.getM_AttributeSetInstance().getDescription() + "]";
//						}
//					}
				}
			}
		}
		return msg.isEmpty()? null : msg;
	}
	
	public static MStorageOnHand[] generateSOH(Properties ctx, int M_Product_ID, int M_Locator_ID,
			BigDecimal qtyNeeded, String trxName)
	{
		MStorageOnHand[] listSto = MStorageOnHand.getOfLocator(ctx, M_Product_ID, M_Locator_ID, trxName);
		if (listSto==null)
			throw new AdempiereException("Not found " + MProduct.get(ctx, M_Product_ID).getName()
				+" in Locator " + MLocator.get(ctx, M_Locator_ID).getValue());
		
		ArrayList<MStorageOnHand> arraySto = new ArrayList<MStorageOnHand>();
//		BigDecimal countQty = qtyNeeded;
		
//		for(MStorageOnHand sto : listSto)
//		{
		//TODO SHIPMENT SCHEDULE
//			arraySto.add(sto);
//			countQty=countQty.add(sto.getReleaseQty().negate());
//			if(sto.getReleaseQty().compareTo(countQty)<=0)
//				break;
//		}
		MStorageOnHand[] retVal = arraySto.toArray(new MStorageOnHand[arraySto.size()]);
		
		return retVal;
	}
	
	
}
