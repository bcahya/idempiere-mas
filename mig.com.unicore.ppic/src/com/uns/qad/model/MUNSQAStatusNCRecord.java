/**
 * 
 */
package com.uns.qad.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

import com.uns.model.I_C_DocType;

/**
 * @author YAKA
 *
 */
public class MUNSQAStatusNCRecord extends X_UNS_QAStatus_NCRecord implements
		DocOptions, DocAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4139955342902466627L;
	private String m_processMsg;
	private boolean m_justPrepared;

	/**
	 * @param ctx
	 * @param UNS_QAStatus_NCRecord_ID
	 * @param trxName
	 */
	public MUNSQAStatusNCRecord(Properties ctx, int UNS_QAStatus_NCRecord_ID,
			String trxName) {
		super(ctx, UNS_QAStatus_NCRecord_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSQAStatusNCRecord(Properties ctx, ResultSet rs, String trxName) {
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
		
		String msg = checkInput();
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
		

		m_processMsg = actionForNC(); 
		
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

	public boolean createNCRecord(MUNSQAStatusInOutLine qasioLine) {
		setClientOrg(qasioLine);
		setUNS_QAStatus_InOutLine_ID(qasioLine.get_ID());
		setC_DocType_ID(MDocType.getDocType(I_C_DocType.DOCBASETYPE_QAD));
		setDateDoc(new Timestamp( System.currentTimeMillis() ));
		setFromPRC(false);
		setQty(qasioLine.getNCQty());
		setDescription(qasioLine.getRemark());
		setDocStatus(STATUS_Drafted);
		setDocAction(ACTION_Prepare);
		setPDAStatus(PDASTATUS_Reject);
		return save(get_TrxName());
	}
	
	public boolean createNCRecord(MUNSQAStatusPRCLine qasPRCLine) {
		setClientOrg(qasPRCLine);
		setUNS_QAStatus_PRCLine_ID(qasPRCLine.get_ID());
		setC_DocType_ID(MDocType.getDocType(I_C_DocType.DOCBASETYPE_QAD));
		setDateDoc(new Timestamp( System.currentTimeMillis() ));
		setFromPRC(true);
		setQty(qasPRCLine.getNCQty());
		setCartonSequence(qasPRCLine.getNC());
		setDescription(qasPRCLine.getRemark());
		setDocStatus(STATUS_Drafted);
		setDocAction(ACTION_Prepare);
		setPDAStatus(PDASTATUS_Reprocess);
		return save(get_TrxName());
	}
	
	private String checkInput() {
		String msg = "Please input all field before process document:";
		if (getStartTime()==null) return msg += " Start Time";
		if (getEndTime()==null) return msg += " End Time";
		if (getProposedDispositionAction()==null) return  msg += "PDA Status/Description";
		if (getInformedBy()==null) return msg += " Informed By";
		if (getReceivedBy()==null) return msg += " Received By";
		if (getActByDT()==null) return msg += " Action Date Time";
		if (getActByName()==null) return msg += " Action By";
		if (getVrfByDT()==null) return msg += " Verified Date Time";
		if (getVrfByName()==null) return msg += " Verified By";
		if (getResult()==null) return msg += " Result";
		
		msg = "Invalid date, please input the correct date.";
		if (getEndTime().compareTo(getStartTime()) < 0) 
			return msg += " End Time < Start Time";
		if (getActByDT().compareTo( new Timestamp( System.currentTimeMillis())) > 0) 
			return msg += " Action Time > Current Time";
		if (getVrfByDT().compareTo( new Timestamp( System.currentTimeMillis())) > 0) 
			return msg += " Verified Time > Current Time";
		
		return null;
	}
	
	@Override
	public MUNSQAStatusInOutLine getParent() {
		
		return new MUNSQAStatusInOutLine(getCtx(), getUNS_QAStatus_InOutLine_ID(), get_TrxName());
	}
	
	public MUNSQAStatusPRCLine getParentPRC() {
		
		return new MUNSQAStatusPRCLine(getCtx(), getUNS_QAStatus_InOutLine_ID(), get_TrxName());
	}
	
	public boolean checkAllNCStatus(Boolean fromPRC){
		if (fromPRC){
			MUNSQAStatusPRC prc = getParentPRC().getParent();
			return prc.checkNCRecord();
		} else {
			MUNSQAStatusInOut io = getParent().getParent();
			return io.checkNCRecord();
		}
	}

	@SuppressWarnings("deprecation")
	private String actionForNC() {
		Data dt = null;
		String retVal = "";
//		String remark = "Automatic created from NC ";
		
		if (isFromPRC()){
			MUNSQAStatusPRCLine line = new MUNSQAStatusPRCLine(getCtx(), 
					getUNS_QAStatus_PRCLine_ID(), get_TrxName());
			dt = new Data(MStorageOnHand.get(getCtx(), line.getM_Locator_ID(), 
						line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(), get_TrxName()), 
					getPDAStatus());
//			remark += "Production Inspection Document No. "+ getDocumentNo();
		} else {
			I_M_InOutLine ioLine = getUNS_QAStatus_InOutLine().getM_InOutLine();
			dt = new Data(MStorageOnHand.get(getCtx(), ioLine.getM_Locator_ID(), 
						ioLine.getM_Product_ID(), ioLine.getM_AttributeSetInstance_ID(), get_TrxName()), 
					getPDAStatus());
//			remark += "Ship/Receipt Document No. "+ getDocumentNo();
		}
		
		//set data
		dt.setMoveLoc(getM_Locator_ID());
		dt.setMoveOrg(getAD_OrgTrx_ID());
		dt.setUserID(getAD_User_ID());
		
		//set storage and 
		ArrayList<MStorageOnHand> sto = new ArrayList<MStorageOnHand>();
		sto.add(dt.getSto());
//		int[] locatorTo = {dt.getMoveLoc()};
		//TODO QASTATUS
//		if (isCreateMovement()){
//			MMovement move = MMovement.getFirst(getCtx(), dt.getSto().getAD_Org_ID(), get_TrxName());
//			
//			if (move == null)
//				move = MMovement.createUpdateFrom(getCtx(), dt.getSto().getAD_Org_ID(), sto, locatorTo,
//						0, getPDAStatus(), remark, getCartonSequence(), getQty(), get_TrxName());
//			else
//				MMovement.createUpdateFrom(getCtx(), dt.getSto().getAD_Org_ID(), sto, locatorTo, 
//						move.get_ID(), getPDAStatus(), remark, getCartonSequence(), getQty(), get_TrxName());
//
//			retVal += "Movement DocNo:"+ move.getDocumentNo() +" created. ";
//		}
		
//		if(isUseMaterials()){
//			MInventory inven = MInventory.getFirst(getCtx(),  dt.sto.getAD_Org_ID(), 
//					dt.sto.getM_Warehouse_ID(), get_TrxName());
//			
//			if (inven == null)
//				inven = MInventory.createUpdateFrom(getCtx(), dt.sto.getAD_Org_ID(), sto, locatorTo,
//						0, getPDAStatus(), remark, getCartonSequence(), getQty(), get_TrxName());
//			else
//				MInventory.createUpdateFrom(getCtx(), dt.sto.getAD_Org_ID(), sto, locatorTo,
//						inven.get_ID(), getPDAStatus(), remark, getCartonSequence(), getQty(), get_TrxName());
//			
//			CreateNote(inven, dt);
//			retVal += "Internal Inventory Use DocNo:"+ inven.getDocumentNo() +" created.";
//		}
		
		return retVal;
	}
	
//	private void CreateNote(MInventory inventory, Data dt) {
//		StringBuffer message = new StringBuffer(MMessage.get(getCtx(), "Confirmation").getMsgText())
//					.append(" - ");
//		message.append(inventory.toString());
//		
//		StringBuffer reff = new StringBuffer();
//		reff.append(inventory.toString());
//		
//		MNote note = new MNote(getCtx(), MMessage.getAD_Message_ID(getCtx(), "Confirmation"), 
//				dt.getUserID(), inventory.get_Table_ID(), inventory.get_ID(), reff.toString(), 
//				message.toString(), get_TrxName()); 
//		
//		if(!note.save())
//			throw new AdempiereException("Can't create notice.");
//		
//	}

}

class Data{
	MStorageOnHand sto = null;
	int moveOrg = 0;
	int moveLoc = 0;
	int userID = 0;
	String QAStatus = null;

	public Data() {
		super();
	}
	
	public Data(MStorageOnHand sto, String QAStatus) {
		super();
		this.sto = sto;
		this.QAStatus = QAStatus;
	}

	public MStorageOnHand getSto() {
		return sto;
	}

	public void setSto(MStorageOnHand sto) {
		this.sto = sto;
	}

	public String getQAStatus() {
		return QAStatus;
	}

	public void setQAStatus(String QAStatus) {
		this.QAStatus = QAStatus;
	}

	public int getMoveOrg() {
		return moveOrg;
	}

	public void setMoveOrg(int moveOrg) {
		this.moveOrg = moveOrg;
	}

	public int getMoveLoc() {
		return moveLoc;
	}

	public void setMoveLoc(int moveLoc) {
		this.moveLoc = moveLoc;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}