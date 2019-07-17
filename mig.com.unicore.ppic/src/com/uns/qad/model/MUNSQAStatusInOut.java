/**
 * 
 */
package com.uns.qad.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutConfirm;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInOutLineConfirm;
import org.compiere.model.MPeriod;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

import com.uns.model.MProduct;

import com.uns.model.GeneralCustomization;
import com.uns.model.I_C_DocType;

/**
 * @author YAKA
 *
 */
public class MUNSQAStatusInOut extends X_UNS_QAStatus_InOut implements
		DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2175533333438274894L;
	public static final String EXTENSION_ID = "com.uns.model.factory.UNSPPICModelFactory";
	private String m_processMsg;
	private boolean m_justPrepared;
	private MUNSQAStatusInOutLine[] m_lines;
	private boolean m_reActive = false;
	private MProduct[] m_listServiceProduct;

	public MUNSQAStatusInOut(Properties ctx, int UNS_QAStatus_InOut_ID,
			String trxName) {
		super(ctx, UNS_QAStatus_InOut_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSQAStatusInOut(Properties ctx, ResultSet rs,
			String trxName) {
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
		
		String msg = checkBeforePrepare();
		if (msg!=null)
			throw new IllegalArgumentException(msg);
		
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
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,
				ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if (checkQAStatus(X_UNS_QAStatus_InOutLine.QASTATUS_Release, true)){
			setDocAction(DOCACTION_Prepare);
			return DocAction.STATUS_InProgress;
		}		

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
		if(!CONFIRMTYPE_ShipmentConfirmation.equals(getConfirmType())){
			m_processMsg = checkCompleteMInOut();
			if (m_processMsg!=null)
				return DocAction.STATUS_InProgress;
		}
		
		m_processMsg = createUpdateResult();
		if (m_processMsg!=null)
			return DocAction.STATUS_InProgress;

		if (checkQAStatus(X_UNS_QAStatus_PRCLine.QASTATUS_OnHold, false)){
			m_processMsg = "Can't process because QA Status still hold or waiting other process complete.";
			return DocAction.STATUS_InProgress;
		}
		
		if (!checkStatusInOut() && (m_reActive 
				|| checkQAStatus(X_UNS_QAStatus_InOutLine.QASTATUS_NonConformance, false))
				|| !checkNCRecord()){

			m_processMsg = "Please conform NC Ship/Receipt : "+
					getLastNCRecord().getDocumentInfo();
			
			return DocAction.STATUS_InProgress;
		}
		
		//update StorageOhHand
//		for (MUNSQAStatusInOutLine line : getLines()){
//			MInOutLine iol = new MInOutLine(getCtx(), getM_InOut_ID(), get_TrxName());
//			for(MInOutLineMA ioma : iol.getMAs()){
//				MStorageOnHand soh = MStorageOnHand.get(getCtx(), iol.getM_Locator_ID(),
//					iol.getM_Product_ID(), 
//					ioma.getM_AttributeSetInstance_ID(), 
//					get_TrxName());
//				soh.setQtyQAStatus(line.getReleaseQty(), line.getOnHoldQty(), line.getNCQty());
//			}
//		}
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());
		
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
		
		for(MUNSQAStatusInOutLine statusInOutLine : getLines()){
			for (MUNSQAStatusNCRecord ncRecord : statusInOutLine.getNCLines()){
				if (!ncRecord.closeIt())
					return false;
			}
		}
			
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

	@Override
	public boolean reActivateIt() {
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;	
		
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;		
		
		m_reActive=true;
		setProcessed(false);
		setDocAction(DOCACTION_Prepare);
		setDocStatus(STATUS_InProgress);
		return true;
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
	
	@Override
	protected boolean beforeDelete() {
		if (isProcessed())
			return false;
		
		for (MUNSQAStatusInOutLine siol : getLines()){
			siol.deleteEx(false, get_TrxName());
		}
		
		return super.beforeDelete();
	}
	
	/**
	 * 
	 * @param requery
	 * @return MUNSQAStatusInOutLine[]
	 */
	protected MUNSQAStatusInOutLine[] getLines(boolean requery){
		if (m_lines != null	&& !requery){
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSQAStatusInOutLine> mList =
				new Query(getCtx(), MUNSQAStatusInOutLine.Table_Name
						, MUNSQAStatusInOutLine.COLUMNNAME_UNS_QAStatus_InOut_ID + " =?"
						, get_TrxName())
		.setParameters(getUNS_QAStatus_InOut_ID())
		.setOrderBy(MUNSQAStatusInOutLine.COLUMNNAME_UNS_QAStatus_InOutLine_ID).list();
		
		m_lines = new MUNSQAStatusInOutLine[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
	
	/**
	 * 
	 * @return MUNSQAStatusInOutLine[]
	 */
	public MUNSQAStatusInOutLine[] getLines()
	{
		return getLines(false);
	}

	public boolean create(MInOut io, boolean isReceipt) {
		setC_DocType_ID(MDocType.getDocType(I_C_DocType.DOCBASETYPE_QAD));
		setDateDoc(new Timestamp( System.currentTimeMillis() ));
		setInspectionDate(new Timestamp( System.currentTimeMillis() ));
		if (isReceipt)
			setConfirmType(CONFIRMTYPE_ReceiptConfirmation);
		else 
			setConfirmType(CONFIRMTYPE_ShipmentConfirmation);
		setM_InOut_ID(io.get_ID());
		setIsHoldShipment(true);
		setAD_Org_ID(GeneralCustomization.getDept_ID(get_TrxName(), "QAD"));
		setDocAction(DOCACTION_Prepare);
		setDocStatus(DOCSTATUS_Drafted);
		if (!save())
			return false;
		for (MInOutLine ioLine : io.getLines()){
			MUNSQAStatusInOutLine qasioLine = new MUNSQAStatusInOutLine(getCtx(), 0, get_TrxName());
			qasioLine.setAD_Org_ID(getAD_Org_ID());
			qasioLine.setUNS_QAStatus_InOut_ID(get_ID());
			qasioLine.setM_InOutLine_ID(ioLine.get_ID());
			qasioLine.setTargetQty(ioLine.getConfirmedQty());
			if (isReceipt)
				qasioLine.setUNS_QAStatus_Type_ID(MUNSQAStatusType.get(getCtx(),
						MUNSQAStatusType.CATEGORY_PMSM, get_TrxName()));
			if(!qasioLine.save())
				return false;
		}
		return true;
	}

	private String checkBeforePrepare() {
		for(MUNSQAStatusInOutLine qasioLine : getLines()){
			if (getConfirmType().equals(CONFIRMTYPE_ReceiptConfirmation)){
				if (qasioLine.getExpiration()==null || qasioLine.getExpiration().before(getInspectionDate()))
					return "Please input Expiration Date at Lines : "+qasioLine.getM_Product().getName();
			} else {
				if (qasioLine.getCOA()==null || qasioLine.getCOA().length()==0)
					return "Please input Expiration Date at Lines : "+qasioLine.getM_Product().getName();
			}
			if (qasioLine.getUNS_QAStatus_Type_ID()==0)
				return "Please select the type of Attribute";
			if (qasioLine.getLines().length<1)
				return "Please input Attribute Test";
		}
		return null;
	}
	
	private boolean checkQAStatus(String QAStatus, Boolean negasi){
//		for(MUNSQAStatusInOutLine sc : getLines()){
			if (negasi){
//				if (!MStorageOnHand.checkQAStatus(sc.getQAStatus()).equals(QAStatus))
//					return true;
//			} else {
//				if (MStorageOnHand.checkQAStatus(sc.getQAStatus()).equals(QAStatus))
//					return true;
			}
//		}
		return false;
	}
	
	public boolean checkStatusInOut(){
		m_processMsg = "Please conform Non Conformance Ship/Receipt: ";
		if (checkQAStatus(X_UNS_QAStatus_InOutLine.QASTATUS_Release, true)){
			for(MUNSQAStatusInOutLine qasioLine : getLines()){
				//if qasioLine with NC status will enter block if
//				if (MStorageOnHand.checkQAStatus(qasioLine.getQAStatus())
//						.equals(X_UNS_QAStatus_InOutLine.QASTATUS_NonConformance)){
					if (qasioLine.getNCLines().length == 0){
						MUNSQAStatusNCRecord sncr = new MUNSQAStatusNCRecord(getCtx(), 0, get_TrxName());
						if (!sncr.createNCRecord(qasioLine))
							throw new AdempiereException("Error when create Non Conformance");
						m_processMsg = m_processMsg + sncr.getDocumentInfo() + " ";
					} else if(qasioLine.getLastNCRecord()!=null)
						m_processMsg = m_processMsg + qasioLine.getLastNCRecord().getDocumentInfo() + " ";
	//			}
			}
		}
		
		return true;
	}
	
	public boolean checkNCRecord(){
		for(MUNSQAStatusInOutLine qasioLine : getLines()){
			if(qasioLine.getLastNCRecord()!=null && 
					!qasioLine.getLastNCRecord().getDocStatus().equals(STATUS_Completed))
				return false;
		}
		return true;
	}
	

	private MUNSQAStatusNCRecord getLastNCRecord(){
		for(MUNSQAStatusInOutLine qasioLine : getLines()){
			MUNSQAStatusNCRecord retVal = qasioLine.getLastNCRecord();
			if(retVal != null)
				return retVal;
		}
		return null;
	}
	
	public static boolean checkShipReceipt(MInOut io){
//		for (MInOutLine line : io.getLines()){
//			int pc_id = line.getM_Product().getM_Product_Category_ID();
//			if (MProductCategory.checkParentProductCategory(io.getCtx(), pc_id)){
//				return true;
//			}
//		}
		return false;
	}
	
	/**
	 * @author YAKA
	 * @param MInOut io 
	 * @return null when true
	 */
	public static String createShipReceiptInspection(MInOut io, MInOutConfirm ioc, boolean isReceipt) {
		MUNSQAStatusInOut qasInOut = new MUNSQAStatusInOut(io.getCtx(), 0, io.get_TrxName());
		if (!qasInOut.create(io, isReceipt))
			return "Error when create Ship/Receipt Inspection. ";
		if (!qasInOut.update(ioc, isReceipt))
			return "Error when update Ship/Receipt Inspection. ";
		return null;
	}

	private boolean update(MInOutConfirm ioc, boolean isReceipt) {
		for (MInOutLineConfirm iolc : ioc.getLines(false)){
			for (MUNSQAStatusInOutLine line : getLines()){
				if(line.getM_InOutLine_ID()==iolc.getM_InOutLine_ID()){
					line.setTargetQty(iolc.getConfirmedQty());
					if(line.save()){
						if(isReceipt)
							line.createListAttributeTest();
						else {
							line.setConfirmedQty(iolc.getConfirmedQty());
							line.setQAStatus(MUNSQAStatusInOutLine.QASTATUS_Release);
						}
					} else
						return false;
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	private String createUpdateResult() {
		String msg = null;
		for (MUNSQAStatusInOutLine line : getLines()){
			MInOutLine io = new MInOutLine(getCtx(), line.getM_InOutLine_ID(), get_TrxName());
			for(MUNSQAStatusAttributeTest att : line.getLines()){
				
				//check laboratory result is exist and has completed or not.
				if (att.getUNS_QAMLab_Result_ID()!=0){
					if ( att.getUNS_QAMLab_Result().getDocStatus()!=DOCSTATUS_Completed)
						msg += att.getUNS_QAMLab_Result().getReferenceNo()+ "; ";
					
					continue;
				}
				
				//check need laboratory result or not.
				int id = GeneralCustomization.get_ID(get_TrxName(), MUNSQAMonitoringAttribut.Table_Name,
						MUNSQAMonitoringAttribut.COLUMNNAME_UNS_QAAttributeTest_ID,
						"=", att.getUNS_QAAttributeTest_ID());
				MProduct[] listOfP = listServiceProduct(id);
				if(id<=0 || listOfP==null )
					continue;
				
				//create laboratory result
				MUNSQAMLabResult result = MUNSQAMLabResult.createMonitoring(getCtx(), 
					MStorageOnHand.get(getCtx(), io.getM_Locator_ID(), io.getM_Product_ID(), 
							io.getM_AttributeSetInstance_ID(), get_TrxName()), 
					line.getSampleQty(), line.isReturnAfterInspection(), 
					listServiceProduct(id), MUNSQAMLabResult.INSPECTIONTYPE_PMSMProduct,
					MUNSQAMLabResult.LOCATOR_InspectedProduct, 0, get_TrxName());
				att.setUNS_QAMLab_Result_ID(result.get_ID());
				if(att.save(get_TrxName())){
					msg += result.getDocumentNo()+ "; ";
				} else
					throw new AdempiereException("Error when create labolatory result for Attribute " 
							+ att.getUNS_QAAttributeTest().getName());
			}
		}
			
		if (msg!=null){
			return "Please complete labolatory result:" + msg;
		} 
		
		return msg;
	}
	
	private MProduct[] listServiceProduct(int id){
		if (m_listServiceProduct!=null && m_listServiceProduct.length>0)
			return m_listServiceProduct;
		
		MUNSQAMonitoringAttribut ma = new MUNSQAMonitoringAttribut(getCtx(), id, get_TrxName());
		if (!ma.isneedLabResult())
			return null;
		m_listServiceProduct = new MProduct[0];
		MUNSQAMonitoringLab ml = new MUNSQAMonitoringLab(getCtx(), ma.getUNS_QAMonitoringLab_ID(), get_TrxName());
		MUNSQAMonitoringLabLine[] mll = ml.getLine();
		for(int i = 0;i<mll.length;i++){
			m_listServiceProduct[i] = new MProduct(getCtx(), new MUNSQALabTest(getCtx(),
					mll[i].getUNS_QALabTest_ID(), get_TrxName()).getProductService_ID(), 
					get_TrxName());
		}
		
		return m_listServiceProduct;
	}

	private String checkCompleteMInOut() {
		if (!getM_InOut().getDocStatus().equals(DOCSTATUS_Completed))
			return "Please complete material receipt: " + getM_InOut().getDocumentNo();
		
		return null;
	}
}
