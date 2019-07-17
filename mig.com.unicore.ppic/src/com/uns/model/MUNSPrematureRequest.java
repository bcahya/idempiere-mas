/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInOutLineMA;
import org.compiere.model.MProduct;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;
/**
 * @author AzHaidar
 *
 */
public class MUNSPrematureRequest extends X_UNS_PrematureRequest implements
		DocOptions, DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2029413390888042366L;
	
	private String m_processMsg = null;
	
	private boolean m_justPrepared = false;

	private X_UNS_PrematureReq_Line[] m_lines = null;
	
	/**
	 * @param ctx
	 * @param UNS_PrematureRequest_ID
	 * @param trxName
	 */
	public MUNSPrematureRequest(Properties ctx, int UNS_PrematureRequest_ID,
			String trxName) {
		super(ctx, UNS_PrematureRequest_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPrematureRequest(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocOptions#customizeValidActions(java.lang.String, java.lang.Object, java.lang.String, java.lang.String, int, java.lang.String[], java.lang.String[], int)
	 */
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		// If status = Drafted, add "Prepare" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
				|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
			options[index++] = DocumentEngine.ACTION_Prepare;
		}

		// If status = Completed, add "Reverse Correct" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
			options[index++] = DocumentEngine.ACTION_Reverse_Correct;
			options[index++] = DocumentEngine.ACTION_Void;
		}

		return index;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception 
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessed(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() 
	{
		log.info(toString());
		setDocAction(DocAction.ACTION_Invalidate);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
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
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		setProcessed(true);
		return DocAction.STATUS_InProgress;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() 
	{
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		log.info(toString());
		setIsApproved(false);
		setProcessed(false);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
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
		MUNSPrematureConfirmation confirmation = 
				MUNSPrematureConfirmation.getOfRequest(getCtx(), get_ID(), get_TrxName());
		if (null == confirmation) {
			createConfirmation();
			setDescription("Waiting for Requested Department Confirmation.");
			return DocAction.STATUS_InProgress;
		}
		
		if (!DocAction.STATUS_Completed.equals(confirmation.getDocStatus())) {
			setDescription("Waiting for Requested Department Confirmation.");
			return DocAction.STATUS_InProgress;
		}
		
		for (X_UNS_PrematureReq_Line reqLine : getLines())
		{
			if (reqLine.getReleaseQty().compareTo(BigDecimal.ZERO) == 0)
				continue;
			//TODO QASTATUS PALLET
//			if (null == reqLine.getPallets() && null == reqLine.getPackages())
//			{
//				BigDecimal newOnHoldQty = reqLine.getOnHoldQty().subtract(reqLine.getApprovedQty());
//				MStorageOnHand.setOnholdQty(reqLine.getM_Product_ID(), 
//											reqLine.getM_Locator_ID(), 
//											reqLine.getM_AttributeSetInstance_ID(), 
//											newOnHoldQty, get_TrxName());
//				BigDecimal newReleaseQty = reqLine.getReleaseQty().add(reqLine.getApprovedQty());
//				MStorageOnHand.setReleaseQty(reqLine.getM_Product_ID(), 
//											 reqLine.getM_Locator_ID(), 
//											 reqLine.getM_AttributeSetInstance_ID(), 
//											 newReleaseQty, get_TrxName());
//			}
//			else if (null != reqLine.getPallets())
//			{
//				PalletHelper.setQAStatusOfPallets(reqLine.getPallets(), 
//											 	reqLine.getM_Product_ID(),
//											 	reqLine.getM_Locator_ID(), 
//											 	reqLine.getM_AttributeSetInstance_ID(), 
//											 	PalletHelper.QASTATUS_RELEASE, getCtx(), get_TrxName());
//			}
//			else if (null != reqLine.getPackages())
//			{
//				PalletHelper.setQAStatusOfPackages(reqLine.getPallets(), 
//												 reqLine.getM_Product_ID(),
//												 reqLine.getM_Locator_ID(), 
//												 reqLine.getM_AttributeSetInstance_ID(), 
//												 PalletHelper.QASTATUS_RELEASE, getCtx(), get_TrxName());
//			}
			@SuppressWarnings("deprecation")
			MStorageOnHand storage = MStorageOnHand.get(getCtx(), reqLine.getM_Locator_ID(), 
														reqLine.getM_Product_ID(), 
														reqLine.getM_AttributeSetInstance_ID(), 
														get_TrxName());
			//TODO QASTATUS
//			storage.setIsPrematureReleased(true);
			storage.save();
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		//setDateDoc(new Timestamp(Calendar.getInstance().getTimeInMillis())); // Reset the request timestamp to today.

		setProcessed(true);
		setDocAction(DocAction.ACTION_Close);
		m_processMsg = "Completed.";
		return DocAction.STATUS_Completed;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() 
	{
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DocAction.ACTION_None);

		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
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
		setDocAction(DocAction.ACTION_Close);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() 
	{
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() 
	{
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

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reActivateIt() {

		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// No 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() 
	{
		return m_processMsg;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param ShipmentArrangement_ID
	 * @param trxName
	 * @return
	 */
	public static String createFGPRR(Properties ctx, int ShipmentArrangement_ID, String trxName)
	{
		String retMsg = null;
		
		MUNSShipmentSchedule shipment = 
				MUNSShipmentSchedule.get(ctx, ShipmentArrangement_ID, trxName);//new MUNSShipmentSchedule(ctx, ShipmentArrangement_ID, trxName);
		
		if (null == shipment)
			throw new AdempiereUserError("Can not found Shipment Record.", "Please try another shipment.");
		
		MUNSPrematureRequest EmergeRelease = new MUNSPrematureRequest(ctx, 0, trxName);
		EmergeRelease.setC_DocType_ID(MDocType.getDocType("PFG"));
		EmergeRelease.setRequestedDate(new Timestamp((new Date()).getTime()));
		EmergeRelease.setExpectedReleaseDate(new Timestamp((new Date()).getTime()));
		EmergeRelease.setReason("Needed by Shipment with Document No of: " + shipment.getDocumentNo());
		EmergeRelease.setPriorityRule(PRIORITYRULE_High);
		//EmergeRelease.set
		if (!EmergeRelease.save())
			throw new AdempiereException("Failed when create new Premature Release Request.");
		
		int count = 0;
		// Get on hold products.
		for (MUNSContainerDeparture containerList : shipment.getContainerDeparture())
		{
			for (MUNSSOShipment soShipment : containerList.getSOShipments())
			{
				MInOutLine ioLine = (MInOutLine) soShipment.getM_InOutLine();
				
				MProduct product = (MProduct) ioLine.getM_Product();
				
				MInOutLineMA[] ioLMAList = MInOutLineMA.get(ctx, ioLine.get_ID(), trxName);
				
				for (MInOutLineMA ioLMA : ioLMAList)
				{
					//TODO please replace this with proper storage check.
					//BigDecimal onHoldQty = BigDecimal.ZERO; //MStorageOnHand.getOnHoldQty(ctx, ioLine.getM_Product_ID(), ioLine.getM_Locator_ID(), ioLMA.getM_AttributeSetInstance_ID(), trxName);
					//BigDecimal releaseQty = BigDecimal.ZERO; //MStorageOnHand.getReleasedQty(ctx, ioLine.getM_Product_ID(), ioLine.getM_Locator_ID(), ioLMA.getM_AttributeSetInstance_ID(), trxName);
					
					//TODO for QA STATUS
					BigDecimal onHoldQty = Env.ZERO;
//							MStorageOnHand.getOnHoldQty(ioLine.getM_Product_ID(), 
//														ioLine.getM_Locator_ID(), 
//														ioLMA.getM_AttributeSetInstance_ID(), 
//														trxName);
					BigDecimal releaseQty = Env.ZERO;
//							MStorageOnHand.getReleaseQty(ioLine.getM_Product_ID(), 
//														 ioLine.getM_Locator_ID(), 
//														 ioLMA.getM_AttributeSetInstance_ID(), 
//														 trxName);
					
					BigDecimal prematureNeedQty = ioLMA.getMovementQty().subtract(releaseQty);

					if (prematureNeedQty.compareTo(BigDecimal.ZERO) <= 0)
						continue;
					if (onHoldQty.compareTo(prematureNeedQty) < 0)
						throw new AdempiereUserError("Unsync shipment data for Product of [" + product.getValue() + 
													 "] Attribute [" + ioLMA.getM_AttributeSetInstance().getDescription() +
													 "] at Locator [" + ioLine.getM_Locator().getValue() + "]");
					
					X_UNS_PrematureReq_Line reqLine = new X_UNS_PrematureReq_Line(ctx, 0, trxName);
					
					reqLine.setUNS_PrematureRequest_ID(EmergeRelease.get_ID());
					reqLine.setM_Product_ID(product.get_ID());
					reqLine.setM_Locator_ID(ioLine.getM_Locator_ID());
					reqLine.setM_AttributeSetInstance_ID(ioLMA.getM_AttributeSetInstance_ID());
					reqLine.setOnHoldQty(onHoldQty);
					reqLine.setRequestedQty(prematureNeedQty);
					reqLine.setReleaseQty(BigDecimal.ZERO);
					
					if (!reqLine.save())
						throw new AdempiereException("Failed when create new Line of Premature Release Request.");
					count++;
				}
			}
		}
		if (count == 0)
			throw new AdempiereUserError("There's no Premature Release Request created.");
		
		return retMsg;
	}

	/**
	 * 
	 */
	private void createConfirmation()
	{
		MUNSPrematureConfirmation confirmation = new MUNSPrematureConfirmation(getCtx(), 0, get_TrxName());
		confirmation.setC_DocType_ID(getC_DocType_ID());
		confirmation.setAD_Org_ID(getRequestedOrg_ID());
		confirmation.setExpectedReleaseDate(getExpectedReleaseDate());
		confirmation.setPriorityRule(getPriorityRule());
		confirmation.setReason(getReason());
		confirmation.setRequestedDate(getRequestedDate());
		
		if (!confirmation.save())
			throw new AdempiereException("Failed when creating Premature Release Confirmation.");
		
		X_UNS_PrematureReq_Line[] lines = getLines();
		if (null == lines)
			throw new AdempiereUserError("Premature Release Request does not have lines.");
		
		for (X_UNS_PrematureReq_Line line : lines)
		{
			X_UNS_PrematureConfirm_Line confirmLine = new X_UNS_PrematureConfirm_Line(getCtx(), 0, get_TrxName());
			
			confirmLine.setUNS_PrematureConfirmation_ID(confirmation.get_ID());
			confirmLine.setUNS_PrematureReq_Line_ID(line.get_ID());
			confirmLine.setM_Product_ID(line.getM_Product_ID());
			confirmLine.setM_Locator_ID(line.getM_Locator_ID());
			confirmLine.setM_AttributeSetInstance_ID(line.getM_AttributeSetInstance_ID());
			confirmLine.setOnHoldQty(line.getOnHoldQty());
			confirmLine.setRequestedQty(line.getRequestedQty());
			confirmLine.setReleaseQty(line.getReleaseQty());
			confirmLine.setApprovedQty(BigDecimal.ZERO);
			confirmLine.setDescription(line.getDescription());
			
			if (!confirmLine.save())
				throw new AdempiereException("Failed when creating new Line of Premature Release Request.");
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public X_UNS_PrematureReq_Line[] getLines(boolean requery) {

		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}

		List<X_UNS_PrematureReq_Line> list = Query
				.get(getCtx(), UNSPPICModelFactory.getExtensionID(),
						X_UNS_PrematureReq_Line.Table_Name, "UNS_PrematureRequest_ID=? ",
						get_TrxName())
				.setParameters(new Object[] { get_ID() })
				.setOrderBy(X_UNS_PrematureReq_Line.COLUMNNAME_UNS_PrematureReq_Line_ID)
				.list();

		m_lines = new X_UNS_PrematureReq_Line[list.size()];
		list.toArray(m_lines);
		return m_lines;
	}

	/**
	 * 
	 * @return
	 */
	public X_UNS_PrematureReq_Line[] getLines() {
		return getLines(false);
	}
}