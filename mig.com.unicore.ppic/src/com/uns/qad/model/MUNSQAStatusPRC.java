/**
 * 
 */
package com.uns.qad.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MDocType;
import org.compiere.model.MOrg;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

import com.uns.model.GeneralCustomization;
import com.uns.model.I_C_DocType;
import com.uns.model.MUNSProduction;
import com.uns.model.MUNSProductionDetail;
import com.uns.model.MUNSResourceInOut;

/**
 * @author YAKA
 *
 */
public class MUNSQAStatusPRC extends X_UNS_QAStatus_PRC implements DocOptions,
		DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3770832936563242426L;
	public static final String EXTENSION_ID = "com.uns.model.factory.UNSPPICModelFactory";
	private String m_processMsg;
	private boolean m_justPrepared;
	private MUNSQAStatusPRCLine[] m_lines;
//	private boolean m_reActive = false;

	/**
	 * @param ctx
	 * @param UNS_QAStatus_PRC_ID
	 * @param trxName
	 */
	public MUNSQAStatusPRC(Properties ctx, int UNS_QAStatus_PRC_ID,
			String trxName) {
		super(ctx, UNS_QAStatus_PRC_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSQAStatusPRC(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {

		// If status = Drafted, add "Prepare" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
				|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
			options[index++] = DocumentEngine.ACTION_Prepare;
		}
		
		// If status = Completed, add "Reactive" in the list
		if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
			options[index++] = DocumentEngine.ACTION_Close;
			options[index++] = DocumentEngine.ACTION_ReActivate;
		}

		return index;
	}

	@Override
	public boolean processIt(String processAction) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
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
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (!MPeriod.isOpen(getCtx(), getDateDoc(),
				com.uns.model.I_C_DocType.DOCBASETYPE_QAD,
				getAD_Org_ID())) {
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
		
		if (checkQAStatus(X_UNS_QAStatus_PRCLine.QASTATUS_Release, true)){
			return DocAction.STATUS_InProgress;
		}
					
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
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
		setProcessed(false);
		return true;
	}

	@Override
	public String completeIt() 
	{
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());
		
		//Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		if (checkQAStatus(X_UNS_QAStatus_PRCLine.QASTATUS_OnHold, false)){
			m_processMsg = "Can not complete the document because QA Status still on hold or waiting other process.";
			return DocAction.STATUS_InProgress;
		}
		
		/** Check Lab result created.(Must be created.) */
		if (checkQAStatus(X_UNS_QAStatus_PRCLine.QASTATUS_Release, false)
				&& !getLabResult()){
			m_processMsg = "Lab result must be created before completing PRC Status.";
			return DocAction.STATUS_InProgress;
		}
			
		/**
		if (!checkStatusPRC() && (checkQAStatus(X_UNS_QAStatus_PRCLine.QASTATUS_NonConformance, false) )
				|| !checkNCRecord())
		{	
			if (m_reActive)
			{
				m_processMsg = "Please conform NC Production: ";
				if (checkQAStatus(X_UNS_QAStatus_PRCLine.QASTATUS_NonConformance, false))
				{
					for(MUNSQAStatusPRCLine prcLine : getLines())
					{
						if(MStorageOnHand.checkQAStatus(prcLine.getQAStatus())
							.equals(X_UNS_QAStatus_PRCLine.QASTATUS_NonConformance))
						{
							MUNSQAStatusNCRecord sncr = new MUNSQAStatusNCRecord(getCtx(), 0, get_TrxName());
							if (!sncr.createNCRecord(prcLine))
								throw new AdempiereException("Error when create Non Conformance");
							m_processMsg = m_processMsg + sncr.getDocumentInfo();
						}
					}
				}	
				m_reActive = false;
			}
			return DocAction.STATUS_InProgress;
		}
		
		//update StorageOhHand
		for (MUNSQAStatusPRCLine line : getLines())
		{
			MStorageOnHand[] arrayOfSOH = MStorageOnHand.getOfASI(
					getCtx(), line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(), get_TrxName());
			
			org.compiere.model.MProduct product = MProduct.get(getCtx(), line.getM_Product_ID());
			
			if (product.isStockedOnPallet())
			{
				BigDecimal qtyRelease = PalletHelper.getQuantityOfPackageSequence(line.getRelease());
				BigDecimal qtyOnHold = PalletHelper.getQuantityOfPackageSequence(line.getOnHold());
				BigDecimal qtyNC = PalletHelper.getQuantityOfPackageSequence(line.getNC());
				BigDecimal qtyPackages = qtyRelease.add(qtyOnHold).add(qtyNC);
				for (MStorageOnHand soh : arrayOfSOH)
				{
					if ((line.getRelease() != null && !line.getRelease().equals(""))
						|| (line.getOnHold() != null && !line.getOnHold().equals(""))
						|| (line.getNC() != null && !line.getNC().equals(""))) 
					{
						BigDecimal qtyOnHand = soh.getQtyOnHand();
						soh.setQAStatus(line.getQAStatus());
						soh.save(get_TrxName());
						qtyPackages = qtyPackages.subtract(qtyOnHand);
						
						if (qtyRelease.signum()>0)
							PalletHelper.moveFromToQAStatus(line.getRelease(), 
														   line.getM_Product_ID(), 
														   soh.getM_Locator_ID(), 
														   line.getM_AttributeSetInstance_ID(),
														   MStorageOnHand.QASTATUS_PendingInspectionLabTest,
														   PalletHelper.QASTATUS_RELEASE,
														   line.getQAStatus(),
														   getCtx(), 
														   get_TrxName());
						if (qtyOnHold.signum()>0)
							PalletHelper.moveFromToQAStatus(line.getOnHold(),
															line.getM_Product_ID(),
															soh.getM_Locator_ID(),
															line.getM_AttributeSetInstance_ID(),
															MStorageOnHand.QASTATUS_PendingInspectionLabTest,
															PalletHelper.QASTATUS_ONHOLD,
															line.getQAStatus(),
															getCtx(), 
															get_TrxName());
						if (qtyNC.signum()>0)
							PalletHelper.moveFromToQAStatus(line.getNC(),
															line.getM_Product_ID(),
															soh.getM_Locator_ID(),
															line.getM_AttributeSetInstance_ID(),
															MStorageOnHand.QASTATUS_PendingInspectionLabTest,
															PalletHelper.QASTATUS_NC,
															line.getQAStatus(),
															getCtx(), 
															get_TrxName());
					}
					else {
						PalletHelper.moveAllPackagesFromToQAStatus(
								line.getM_Product_ID(),
								soh.getM_Locator_ID(), 
								line.getM_AttributeSetInstance_ID(),
								MStorageOnHand.QASTATUS_PendingInspectionLabTest,
								MStorageOnHand.checkQAStatus(line.getQAStatus()),
								line.getQAStatus(),
								getCtx(), get_TrxName());
					}
				}
				if (qtyPackages.signum() != 0)
					throw new AdempiereException("You can only either mention all of the packages to set it's QA Status, or " +
							"leave it blank to set all of the packages to the expected QAStatus.");
			}
			else 
			{
				int precision = product.getC_UOM().getStdPrecision();
				for (MStorageOnHand soh : arrayOfSOH)
				{
					BigDecimal proportion = 
							soh.getQtyOnHand().divide(line.getQty(), precision, BigDecimal.ROUND_HALF_UP);
					BigDecimal proportionNCQty = proportion.multiply(line.getReleaseQty());
					BigDecimal proportionOnHoldQty = proportion.multiply(line.getReleaseQty());
					BigDecimal proportionReleaseQty = 
							soh.getQtyOnHand().subtract(proportionNCQty).subtract(proportionOnHoldQty);
					
					soh.setQtyQAStatus(proportionReleaseQty, proportionOnHoldQty, proportionNCQty);
					soh.setQAStatus(MStorageOnHand.checkQAStatus(line.getQAStatus()));
					soh.saveEx();
						//throw new AdempiereException("Failed: when updating storage status.");
				}
			}
		}
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());
		
		**/
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);
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
				
		//Input code to void here
		
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
		
		//input code to close here
		
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;		
		
		setDocAction(DOCACTION_None);
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		//not implement yet
		
		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		return voidIt();
	}

	@Override
	public boolean reverseAccrualIt() {
		log.info(toString());
		// Before reverseAccurual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		//not implement yet
		
		// After reverseAccurual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;
		
		return voidIt();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
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
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}

	@Override
	public File createPDF() {
		// not used yet
		return null;
	}

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		return getCreatedBy();
	}

	@Override
	public int getC_Currency_ID() {
		// not use currency
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// don't have approval amount
		return null;
	}
	
	/**
	 * 
	 * @param requery
	 * @return MUNSQAStatusPRCLine[]
	 */
	protected MUNSQAStatusPRCLine[] getLines(boolean requery){
		if (m_lines != null	&& !requery){
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSQAStatusPRCLine> mList =
				new Query(getCtx(), MUNSQAStatusPRCLine.Table_Name
						, MUNSQAStatusPRCLine.COLUMNNAME_UNS_QAStatus_PRC_ID + " =?"
						, get_TrxName())
		.setParameters(getUNS_QAStatus_PRC_ID())
		.setOrderBy(MUNSQAStatusPRCLine.COLUMNNAME_UNS_QAStatus_PRCLine_ID).list();
		
		m_lines = new MUNSQAStatusPRCLine[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
	
	/**
	 * 
	 * @return MUNSQAStatusPRCLine[]
	 */
	public MUNSQAStatusPRCLine[] getLines()
	{
		return getLines(false);
	}

	private boolean checkQAStatus(String QAStatus, Boolean negasi){
//		for(MUNSQAStatusPRCLine sc : getLines()){
//			if (negasi){
//				if (!MStorageOnHand.checkQAStatus(sc.getQAStatus()).equals(QAStatus))
//					return true;
//			} else {
//				if (MStorageOnHand.checkQAStatus(sc.getQAStatus()).equals(QAStatus))
//					return true;
//			}
//		}
		return false;
	}
	
	public boolean checkStatusPRC(){
		m_processMsg = "Please conform the NC : ";
		if (checkQAStatus(X_UNS_QAStatus_PRCLine.QASTATUS_Release, true))
		{
			for(MUNSQAStatusPRCLine prcLine : getLines()){
				//if qasioLine not release will enter block if
				if(!prcLine.getQAStatus().equals(X_UNS_QAStatus_PRCLine.QASTATUS_Release)){
					//if qasioLine with OnHold status will enter block if
//					if (MStorageOnHand.checkQAStatus(prcLine.getQAStatus())
//							.equals(X_UNS_QAStatus_PRCLine.QASTATUS_NonConformance)){
//						if (prcLine.getNCLines().length == 0){
//							MUNSQAStatusNCRecord sncr = new MUNSQAStatusNCRecord(getCtx(), 0, get_TrxName());
//							if (!sncr.createNCRecord(prcLine))
//								throw new AdempiereException("Error when create Non Conformance");
//							m_processMsg = m_processMsg + sncr.getDocumentInfo() + " ";
//						} else {
//							if (!prcLine.getLastNCRecord().getDocStatus().equals(STATUS_Completed))
//								m_processMsg = m_processMsg + prcLine.getLastNCRecord().getDocumentInfo() + " ";
//							else
//								return true;
//						}
//					}
				}
			}
			return false;
		}
		
		return true;
	}

	public boolean checkNCRecord() {
		for(MUNSQAStatusPRCLine prcLine : getLines()){
			if(prcLine.getLastNCRecord() == null)
				continue;
			else if(!prcLine.getLastNCRecord().getDocStatus().equals(STATUS_Completed))
				return false;
		}
		return true;
	}
	
	public String createQAMonitoring(MUNSProduction prod)
	{
		setAD_Org_ID(MOrg.get(getCtx(), "QAD", get_TrxName()).get_ID());
		
		setDateDoc(prod.getMovementDate());
		setAnalysisDate(prod.getMovementDate());
		setExpectedResultDate(prod.getMovementDate());
		setProductionDate(prod.getProductionDate());

		setUNS_Production_ID(prod.get_ID());
		setC_DocType_ID(MDocType.getDocType(I_C_DocType.DOCBASETYPE_QAD));
		
		setInspectionType(INSPECTIONTYPE_ProductionInspection);
		setDocAction(ACTION_Prepare);
		setDocStatus(DOCSTATUS_InProgress);
		if(!save(get_TrxName()))
			 return "Can't create PRC Line Inspection";
		
		for(MUNSProductionDetail pd : prod.getOutputLines())
		{
			if (pd.isEndProduct() 
					&& MUNSResourceInOut.checkQAMonitoring(getCtx(), prod, pd.getM_Product_ID()))
			{
				MUNSQAStatusPRCLine prcLine = new MUNSQAStatusPRCLine(getCtx(), 0, get_TrxName());
//				MProductCategory prodCat = (MProductCategory)pd.getM_Product().getM_Product_Category();
		
				prcLine.setAD_Org_ID(getAD_Org_ID());
				prcLine.setAD_OrgTrx_ID(pd.getAD_Org_ID());
				prcLine.setM_Locator_ID(pd.getM_Locator_ID());
				prcLine.setM_Product_ID(pd.getM_Product_ID());
				prcLine.setM_AttributeSetInstance_ID(pd.getM_AttributeSetInstance_ID());
				prcLine.setUNS_QAStatus_PRC_ID(get_ID());
				prcLine.setUNS_Production_Detail_ID(pd.get_ID());

				prcLine.setCodeNo("1-"+pd.getMovementQty().intValue());
				prcLine.setRelease("-");
				prcLine.setReleaseQty(BigDecimal.ZERO);
				prcLine.setOnHold("-");
				prcLine.setOnHoldQty(BigDecimal.ZERO);
				prcLine.setNC("-");
				prcLine.setNCQty(BigDecimal.ZERO);
				prcLine.setQty(pd.getMovementQty());
//				prcLine.setQAStatus(prodCat.getInitialQAStatus());
				
				if (!prcLine.save(get_TrxName()))
					 return "Can't create PRC Line Inspection";
			}
		}
		return "";
	}
	
	@Override
	protected boolean beforeDelete() {
		for (MUNSQAStatusPRCLine prcLine : getLines()){
			if(!prcLine.delete(false))
				return false;
		}
		return super.beforeDelete();
	}
	
	private boolean getLabResult(){
		for (MUNSQAStatusPRCLine prcLine : getLines()){
			int id = GeneralCustomization.get_ID(get_TrxName(), MUNSQAMLabResult.Table_Name,
					MUNSQAMLabResult.COLUMNNAME_UNS_QAStatus_PRCLine_ID, "=", prcLine.get_ID());
			if (id>0)
				return true;
		}
		return false;
	}

}
