/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.qad.model.MUNSQAContainerInspection;

/**
 * @author menjangan
 *
 */
public class MUNSContainerArrival extends X_UNS_ContainerArrival implements DocAction, DocOptions {
	
	public MUNSContainerArrivalLine[] m_lines = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_ContainerArrival_ID
	 * @param trxName
	 */
	public MUNSContainerArrival(Properties ctx, int UNS_ContainerArrival_ID,
			String trxName) {
		super(ctx, UNS_ContainerArrival_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSContainerArrival(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @getLines
	 * @param requery
	 * @return
	 */
	protected MUNSContainerArrivalLine[] getLines(boolean requery)
	{
		if (m_lines != null
				&& !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSContainerArrivalLine> list =
				new Query(getCtx(), MUNSContainerArrivalLine.Table_Name, MUNSContainerArrivalLine
						.COLUMNNAME_UNS_ContainerArrival_ID + "=?", get_TrxName())
		.setParameters(getUNS_ContainerArrival_ID()).setOrderBy(
				MUNSContainerArrivalLine.COLUMNNAME_UNS_ContainerArrivalLine_ID)
				.list();
		
		m_lines = new MUNSContainerArrivalLine[list.size()];
		list.toArray(m_lines);
		return m_lines;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSContainerArrivalLine[] getLines()
	{
		return getLines(false);
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		// TODO Auto-generated method stub
		
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
    			|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
    		options[index++] = DocumentEngine.ACTION_Prepare;
    	}
    	
    	// If status = Completed, add "Reactivte" in the list
    	if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
    		options[index++] = DocumentEngine.ACTION_Reverse_Correct;
    		options[index++] = DocumentEngine.ACTION_Void;
    	}   	
    	
		return index;
	}
	
	/**
	 * 
	 */
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() == 0)
			return;
		
		String sql = "UPDATE UNS_ContainerArrivalLine SET Processed='"
				+ (processed ? "Y" : "N")
				+ "' WHERE UNS_ContainerArrival_ID =" + getUNS_ContainerArrival_ID();
			int noLine = DB.executeUpdate(sql, get_TrxName());
			m_lines = null;
			log.fine(processed + " - Lines=" + noLine);
	}

	@Override
	public void setDocStatus(String newStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDocStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean processIt(String action) throws Exception {
		// TODO Auto-generated method stub

		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		MUNSContainerArrivalLine[] mLines = getLines();
		if (mLines == null || mLines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
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

	@Override
	public boolean approveIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		// TODO Auto-generated method stub
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
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = updateContainerManagement();
		if (m_processMsg!=null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = createUpdateInspection();
		if (m_processMsg!=null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);	
		setDocStatus(STATUS_Completed);
		//m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		if (DOCSTATUS_Closed.equals(getDocStatus())
			|| DOCSTATUS_Reversed.equals(getDocStatus())
			|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			return false;
		}
		
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Approved.equals(getDocStatus())
				|| DOCSTATUS_NotApproved.equals(getDocStatus()) )
			{
				//	Set lines to 0
				MUNSContainerArrivalLine[] lines = getLines(false);
				for (int i = 0; i < lines.length; i++)
				{
					MUNSContainerArrivalLine line = lines[i];
					BigDecimal old = line.getQty();
					if (old.signum() != 0)
					{
						line.setQty(Env.ZERO);
						line.addDescription("Void (" + old + ")");
						line.save(get_TrxName());
					}
				}
				//
				// Void Confirmations
				setDocStatus(DOCSTATUS_Voided); // need to set & save docstatus to be able to check it in MInOutConfirm.voidIt()
				saveEx();
			}
			else
			{
				return reverseCorrectIt();
			}

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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		
		setDocAction(DOCACTION_Complete);
		setProcessed(false);
		return true;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocAction() {
		// TODO Auto-generated method stub
		return null;
	}

	public int createLinesFrom() {
		int count = 0;
		MUNSPackingSlip ps = new MUNSPackingSlip(getCtx(), getUNS_PackingSlip_ID(), get_TrxName());
		int idBongkarMuat = GeneralCustomization.get_ID(get_TrxName(), MUNSBongkarMuat.Table_Name, 
				MUNSBongkarMuat.COLUMNNAME_UNS_PackingSlip_ID, 
				"=", getUNS_PackingSlip_ID());
		MUNSBongkarMuat ul = new MUNSBongkarMuat(getCtx(), idBongkarMuat, get_TrxName());
		for (MUNSPackingSlipSupplier pss : ps.getLines()){
			for(MUNSPackingSlipLine psl : pss.getLines()){
				MUNSContainerArrivalLine caLine = new MUNSContainerArrivalLine(getCtx(), 0, get_TrxName());
				caLine.setAD_Org_ID(getAD_Org_ID());
				caLine.setUNS_ContainerArrival_ID(get_ID());
				caLine.setC_UOM_ID(psl.getC_UOM_ID());
				caLine.setDescription(psl.getDescription());
				caLine.setM_Product_ID(psl.getM_Product_ID());
				caLine.setQty(psl.getQtyInvoiced());
				caLine.setC_OrderLine_ID(psl.getC_OrderLine_ID());
				caLine.setM_InOutLine_ID(psl.getM_InOutLine_ID());
				caLine.setUNS_BongkarMuatLine_ID(getBongkarMualLine(ul, psl.getM_InOutLine_ID()));
				if (!caLine.save())
					throw new IllegalArgumentException("Can't create Lines");
				count++;
			}
		}
		return count;
	}
	
	private int getBongkarMualLine(MUNSBongkarMuat ul, int inoutLine) {
		for (MUNSBongkarMuatLine line : ul.getLines()){
			if (line.getM_InOutLine_ID()==inoutLine){
				return line.get_ID();
			}
		}
		return 0;
	}

	private String updateContainerManagement(){
		//update Container Management
		MUNSContainerManagement ContainerManagement =
				new MUNSContainerManagement(getCtx(), getUNS_ContainerManagement_ID(), get_TrxName());
				
		ContainerManagement.setCurrentStatus(MUNSContainerManagement.CURRENTSTATUS_ArrivedPlusWaitingForShipment);
		ContainerManagement.setShippingLine_ID(getShippingLine_ID());
		ContainerManagement.setArrivedBy_ID(getShipped_ID());
		ContainerManagement.setLastArrivalDate(getArriveDate());
		if (!ContainerManagement.save()){
			return "Error when update container management " + ContainerManagement.getContainerNo();
		}
		return null;
	}

	private String createUpdateInspection() {
		MUNSQAContainerInspection qaContainer = new MUNSQAContainerInspection(getCtx(), 0, get_TrxName());
		qaContainer.createContainerInspection(getPackingSlip());
		return null;
	}
	
	public MUNSPackingSlip getPackingSlip() {
		return new MUNSPackingSlip(getCtx(), getUNS_PackingSlip_ID(), get_TrxName());
	}
	
}
