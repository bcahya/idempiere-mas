package com.uns.qad.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPeriod;
import org.compiere.model.MProduct;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

import com.uns.util.MessageBox;

import com.uns.model.GeneralCustomization;
import com.uns.model.MUNSProduction;

public class MUNSQAMLabResult extends X_UNS_QAMLab_Result 
	implements DocOptions, DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 455081773983376160L;
	private MUNSQAMLabResultLine[] m_lines;
	private MUNSQAMLabResultInfo[] m_info;
	protected String m_processMsg;
	protected boolean m_justPrepared;
	private MUNSQAStatusPRCLine m_PRCLine;

	public static final int LOCATOR_MaterialInspection = 0;//UNSApps.getRefAsInt(UNSApps.LOCATOR_MaterialInspection);
	public static final int LOCATOR_InspectedProduct = 0;//UNSApps.getRefAsInt(UNSApps.LOCATOR_InspectedProduct);
	public static final int LOCATOR_InspectedResult = 0;//UNSApps.getRefAsInt(UNSApps.LOCATOR_InspectedResult);
	

	public MUNSQAMLabResult(Properties ctx, int UNS_QAMLab_Result_ID,
			String trxName) {
		super(ctx, UNS_QAMLab_Result_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSQAMLabResult(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSQAMLabResult(Properties ctx, String trxName, MUNSQAStatusPRCLine line){
		this(ctx, 0 , trxName);
		setAD_Org_ID(GeneralCustomization.getDept_ID(get_TrxName(), "QAD"));
		setM_AttributeSetInstance_ID(line.getM_AttributeSetInstance_ID());
		setM_Locator_ID(line.getM_Locator_ID());
		setQty(line.getQty());
		setUNS_QAStatus_PRCLine_ID(line.get_ID());
	}
	
	@Override
	public MUNSQAMLabSummary getParent() {
		return new MUNSQAMLabSummary(getCtx(), getUNS_QAMLab_Summary_ID(), get_TrxName());
	}
	
	public MUNSQAStatusPRCLine getPRCLine() {
		if(m_PRCLine == null)
			m_PRCLine = new MUNSQAStatusPRCLine(getCtx(), getUNS_QAStatus_PRCLine_ID(), get_TrxName());
		
		return m_PRCLine;
	}
	
	/**
	 * @author YAKA
	 * @author AzHaidar - change the method name from checkExistingRecord to be it now. Return true if exists instead.
	 * 
	 * @param trxName
	 * @param monitoring
	 * @param prcLine
	 * @return
	 */
	public static boolean isExist(String trxName, int monitoring, int prcLine){
		String sql = "SELECT COUNT(*) FROM UNS_QAMLab_Result WHERE UNS_QAMonitoringLab_ID = ? AND " +
				"UNS_QAStatus_PRCLine_ID = ?";
		int count = DB.getSQLValue(trxName, sql, monitoring, prcLine);
		if (count >0)
			return true;
		return false;
	}

	/**
	* @param requery
	* @return MUNSQAMLabResultLine[]
	*/
	protected MUNSQAMLabResultLine[] getLines(boolean requery){
		if (m_lines != null	&& !requery){
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSQAMLabResultLine> mList = new Query(getCtx(), MUNSQAMLabResultLine.Table_Name
			, MUNSQAMLabResultLine.COLUMNNAME_UNS_QAMLab_Result_ID + " =?", get_TrxName())
			.setParameters(get_ID())
			.setOrderBy(MUNSQAMLabResultLine.COLUMNNAME_UNS_QAMLab_ResultLine_ID).list();
			
		m_lines = new MUNSQAMLabResultLine[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
		
	/**
	 * 
	 * @return MUNSQAMLabResultLine[]
	 */
	public MUNSQAMLabResultLine[] getLines(){
		return getLines(false);
	}

	/**
	* @param requery
	* @return MUNSQAMLabResultInfo[]
	*/
	protected MUNSQAMLabResultInfo[] getInfo(boolean requery){
		if (m_info != null	&& !requery){
			set_TrxName(m_info, get_TrxName());
			return m_info;
		}
		
		List<MUNSQAMLabResultInfo> mList = new Query(getCtx(), MUNSQAMLabResultInfo.Table_Name
			, MUNSQAMLabResultInfo.COLUMNNAME_UNS_QAMLab_Result_ID + " =?", get_TrxName())
			.setParameters(get_ID())
			.setOrderBy(MUNSQAMLabResultInfo.COLUMNNAME_UNS_QAMLab_ResultInfo_ID).list();
			
		m_info = new MUNSQAMLabResultInfo[mList.size()];
		mList.toArray(m_info);
		return m_info;
	}
		
	/**
	 * 
	 * @return MUNSQAMLabResultInfo[]
	 */
	public MUNSQAMLabResultInfo[] getInfo(){
		return getInfo(true);
	}
	
	public boolean deleteAllLines(){
		for (MUNSQAMLabResultLine line : getLines()) {
			if (!line.delete(false))
				return false;
		}
		
		for (MUNSQAMLabResultInfo info : getInfo()) {
			for (MUNSQAMLabResultLine line : info.getLines()) {
				if (!line.delete(false))
					return false;
			}
		}
		
		return true;
	}
	
	public boolean updateSummary(int summary){
		setUNS_QAMLab_Summary_ID(summary);
		if (!save())
			return false;
		return true;
	}
	
	public boolean updateMoveLine(int line){
		setM_MovementLine_ID(line);
		if (!save())
			return false;
		return true;
	}
	
	public int getProductionDeptID(){
		int p_id = getUNS_QAStatus_PRCLine().getUNS_QAStatus_PRC().getUNS_Production_ID();
		MUNSProduction prod = new MUNSProduction(getCtx(), p_id, get_TrxName());
		return prod.getAD_Org_ID();
	}
	
	public static MUNSQAMLabResult createMonitoring(Properties ctx, MStorageOnHand soh, 
			BigDecimal sampleQty, boolean isReturnAfterInspection, MProduct[] listOfProductService,
			String InspectionType, int locatorID, int UNS_WO_Request_ID, String trxName) {
		MUNSQAMLabResult result = new MUNSQAMLabResult(ctx, 0, trxName);
		result.createFrom(soh, sampleQty, listOfProductService, InspectionType, UNS_WO_Request_ID);
		
		if (!isReturnAfterInspection){
			result.createMovementUpdateLine(locatorID);
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private void createMovementUpdateLine(int locatorID) {
		ArrayList<MStorageOnHand> listOfStorage = new ArrayList<MStorageOnHand>();
		listOfStorage.add(MStorageOnHand.get(getCtx(), getM_Locator_ID(), getM_Product_ID(), 
				getM_AttributeSetInstance_ID(), get_TrxName()));
		
//		int[] listOfLocator = {locatorID};
//		String remark = "Automatic created from Lab Result ";
//		if(getReferenceNo()!=null)
//			remark += getReferenceNo();
		//TODO QA STATUS
//		MMovement move = MMovement.getFirst(getCtx(), listOfStorage.get(0).getAD_Org_ID(), get_TrxName());
//		if (move == null)
//			move = MMovement.createUpdateFrom(getCtx(), listOfStorage.get(0).getAD_Org_ID(), 
//					listOfStorage, listOfLocator, 0, listOfStorage.get(0).getQAStatus(), remark, 
//					null, getQty(), get_TrxName());
//		else
//			move = MMovement.createUpdateFrom(getCtx(), listOfStorage.get(0).getAD_Org_ID(), 
//					listOfStorage, listOfLocator, move.get_ID(), listOfStorage.get(0).getQAStatus(), remark, 
//					null, getQty(), get_TrxName());
//			
//		setM_MovementLine_ID(move.getLines(false)[move.getLines(false).length-1].get_ID());	
		setM_Locator_ID(getM_MovementLine().getM_LocatorTo_ID());
		if (!save())
			throw new AdempiereException("Error when update MLabResult.");
	}

	private void createFrom(MStorageOnHand soh, BigDecimal sampleQty, MProduct[] listOfProductService,
			String InspectionType, int UNS_WO_Request_ID){
		setAD_Org_ID(GeneralCustomization.getDept_ID(get_TrxName(), "QAD"));
		setUNS_WO_Request_ID(UNS_WO_Request_ID);
		setM_AttributeSetInstance_ID(soh.getM_AttributeSetInstance_ID());
		setM_Product_ID(soh.getM_Product_ID());
		setM_Locator_ID(soh.getM_Locator_ID());
		setQty(sampleQty);
		setInspectionType(InspectionType);
		if (!save())
			throw new AdempiereException("Error when create MLabResult.");
		for(MProduct product : listOfProductService){
			MUNSQALabTest lt = MUNSQALabTest.getByProduct(getCtx(), product.get_ID(), get_TrxName());
			MUNSQAMLabResultLine line = new MUNSQAMLabResultLine(getCtx(), 0, get_TrxName());
			line.setAD_Org_ID(getAD_Org_ID());
			line.setUNS_QALabTest_ID(lt.get_ID());
			line.setUNS_QAMLab_Result_ID(get_ID());
			line.setUNS_QAMonitoringLabLine_ID(GeneralCustomization.get_ID(get_TrxName(), MUNSQAMonitoringLabLine.Table_Name,
					MUNSQAMonitoringLabLine.COLUMNNAME_UNS_QALabTest_ID, "=", lt.get_ID()));
			
			if(!line.save())
				throw new AdempiereException("Error when create MLabResult Line.");
		}
	}
	
	public Integer[] getProductServiceList() {
		ArrayList<Integer> retVal = new ArrayList<Integer>();
		if (!isInfo()){
			for (MUNSQAMLabResultLine line : getLines()){
				int prodServID = line.getUNS_QALabTest().getProductService_ID();
				if (prodServID == 0)
					throw new AdempiereException("Please configurate product service at " +
							line.getUNS_QALabTest().getName());
				retVal.add(prodServID);
			}
		} else {
			for (MUNSQAMLabResultInfo info : getInfo()){
				for (MUNSQAMLabResultLine line : info.getLines()){
					int prodServID =line.getUNS_QALabTest().getProductService_ID();
					if (prodServID == 0)
						throw new AdempiereException("Please configurate product service at " +
								line.getUNS_QALabTest().getName());
					retVal.add(prodServID);
				}
			}
		}
		Integer[] prodServIDs = new Integer[retVal.size()];
		retVal.toArray(prodServIDs);
		return prodServIDs;
	}
	
	public MUNSQAMLabResultLine[] getAllLines() {
		ArrayList<MUNSQAMLabResultLine> retVal = new ArrayList<MUNSQAMLabResultLine>();
		if (!isInfo()){
			for (MUNSQAMLabResultLine line : getLines()){
				retVal.add(line);
			}
		} else {
			for (MUNSQAMLabResultInfo info : getInfo()){
				for (MUNSQAMLabResultLine line : info.getLines()){
					retVal.add(line);
				}
			}
		}
		MUNSQAMLabResultLine[] lines = new MUNSQAMLabResultLine[retVal.size()];
		retVal.toArray(lines);
		return lines;
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
		m_justPrepared = false;
		
		if(!isInfo() && getLines().length<=0)
			throw new AdempiereException("No Lines. Please create lines before complete.");
		BigDecimal totalQty = getReleaseQty().add(getOnHoldQty()).add(getNCQty());
		if(getQty().compareTo(totalQty) != 0)
			m_processMsg = "Not syncron Qty QA Status with total qty";
		if(null != m_processMsg)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		if (!MPeriod.isOpen(getCtx(), getInspectionDate(), com.uns.model.I_C_DocType.DOCBASETYPE_QAD, getAD_Org_ID())) {
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
					
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

//		if(MStorageOnHand.checkQAStatus(getQAStatus()).equals(QASTATUS_OnHold))
//			m_processMsg = "Can't prepare because QA Status still On HOLD";
//		if (m_processMsg != null)
//			return DocAction.STATUS_Invalid;
//		
		if (getM_MovementLine_ID()!=0 &&
				!getM_MovementLine().getM_Movement().getDocStatus().equals(DOCSTATUS_Completed))
			m_processMsg = "Complete Inventory Move before next process.";
		if (m_processMsg != null)
			return DocAction.STATUS_Drafted;
		//TODO QA STATUS
//		if(!MStorageOnHand.QASTATUS_QATested.equals(getQAStatus()))
//			setQAStatus(MStorageOnHand.QASTATUS_QATested);
		if(null != getOnHold() && !"".equals(getOnHold()) && !"-".equals(getOnHold())){
			int isContinue = MessageBox.showMsg(this, getCtx(), "Are you sure to process on hold data??", 
							"Confirmation", MessageBox.YESNO, MessageBox.ICONQUESTION);
			
			String session = getCtx().getProperty("servlet.sessionId");
			if (session == null) 
			{
				if (isContinue == MessageBox.RETURN_YES)
				{
					m_processMsg = null;
				}
				else 
				{
					m_processMsg = "Can't prepare because QA Status still On HOLD";
				}
			}
			else {
				if (isContinue == MessageBox.RETURN_YES)
				{
					m_processMsg = null;
				}
				else 
				{
					m_processMsg = "Can't prepare because QA Status still On HOLD";
				}
			}
		}
		if (m_processMsg != null)
			return DocAction.STATUS_Drafted;
		
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
	public String completeIt() {
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
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if(getUNS_QAStatus_PRCLine_ID()!=0 && !getPRCLine().updateQAStatus()){
			m_processMsg = "Can't update PRC Line.";
			return DocAction.STATUS_Invalid;
		} else 
		{
			if (getM_MovementLine_ID()!=0)
			{
				if(!getM_MovementLine().getM_Movement().getDocStatus().equals("CO"))
				{
					m_processMsg = "Please complete Movement "+ getM_MovementLine().getM_Movement().getDocumentNo();
					return DocAction.STATUS_Invalid;
				} 
				else 
				{
//					MStorageOnHand sto = MStorageOnHand.get(getCtx(), getM_MovementLine().getM_LocatorTo_ID(), 
//							getM_MovementLine().getM_Product_ID(), 
//							getM_MovementLine().getM_AttributeSetInstanceTo_ID(), get_TrxName());
					//TODO QA Status
					//MStorageOnHand.setQAStatus(null, sto, getQty(), get_TrxName());
				}
			} 
//			else
//				MStorageOnHand.add(getCtx(), getM_Locator_ID(), getM_Product_ID(), getM_AttributeSetInstance_ID(), 
//						getQty(), getQAStatus(), false, get_TrxName());
		}
		
		updatePRCLine();
		
		setProcessed(true);
		setDocAction(DOCACTION_Close);
//		if(1==1)
//			throw new AdempiereException();
		return DocAction.STATUS_Completed;
	}

	private void updatePRCLine() {
		getPRCLine();
		m_PRCLine.setCodeNo(getCodeNo());
		m_PRCLine.setQty(getQty());
		m_PRCLine.setRelease(getRelease());
		m_PRCLine.setReleaseQty(getReleaseQty());
		m_PRCLine.setOnHold(getOnHold());
		m_PRCLine.setOnHoldQty(getOnHoldQty());
		m_PRCLine.setNC(getNC());
		m_PRCLine.setNCQty(getNCQty());
		//TODO QA STATUS
		//m_PRCLine.setQAStatus(MStorageOnHand.QASTATUS_QATested);
		m_PRCLine.save();
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
		log.info(toString());
		// Before reverseAccurual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		//not implement yet
		
		// After reverseAccurual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		setProcessed(true);
		setDocAction(DOCACTION_None);
		setDocStatus(DOCSTATUS_Drafted);
		return true;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		return "Laboratorium Result : " +getDocumentNo();
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

	@Override
	public String getDocumentNo() {

		return getReferenceNo();
	}

	public void updateQAStatus() {
		if (!isInfo()) {
			for (MUNSQAMLabResultLine line : getLines()){
				if(!MUNSQALabTest.checkThresHold(getCtx(), line.getUNS_QALabTest_ID(), 
						line.getResult())){
					//TODO QA Status
//					setNC("1-"+getQty().toBigInteger().toString());
//					BigDecimal qtyNC = PalletHelper.getQuantityOfPackageSequence(getNC());
//					setNCQty(qtyNC);
//					setQAStatus(QASTATUS_NonConformance);
					save();
					return;
				}
			}
		} else {
			for (MUNSQAMLabResultInfo info : getInfo()){
				for (MUNSQAMLabResultLine line : info.getLines()){
					if(!MUNSQALabTest.checkThresHold(getCtx(), line.getUNS_QALabTest_ID(), 
							line.getResult())){
						//TODO QA Status
//						setNC("1-"+getQty().toBigInteger().toString());
//						BigDecimal qtyNC = PalletHelper.getQuantityOfPackageSequence(getNC());
//						setNCQty(qtyNC);
//						setQAStatus(QASTATUS_NonConformance);
						save();
						return;
					}
				}
			}
		}
		setRelease("1-"+getQty().toBigInteger().toString());
		//TODO QA Status
//		BigDecimal releaseQty = PalletHelper.getQuantityOfPackageSequence(getRelease());
//		setReleaseQty(releaseQty);
//		setQAStatus(MStorageOnHand.QASTATUS_QATested);
		save();
	}
	
}
