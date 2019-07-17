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
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSLCAmendment extends X_UNS_LC_Amendment implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSLCAmendmentLine[] m_Lines = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSLCAmdAttributes[] m_AmendmentAttributes = null;
	private static final String m_DescOfGoodsOrService = "45A";

	/**
	 * @param ctx
	 * @param UNS_LC_Amendment_ID
	 * @param trxName
	 */
	public MUNSLCAmendment(Properties ctx, int UNS_LC_Amendment_ID,
			String trxName) {
		super(ctx, UNS_LC_Amendment_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLCAmendment(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	public MUNSLCAmendmentLine[] getLines(boolean requery)
	{
		if (null != m_Lines && !requery)
		{
			set_TrxName(m_Lines, get_TrxName());
			return m_Lines;
		}
		
		List<MUNSLCAmendmentLine> list = Query.get(getCtx(), 
				UNSPPICModelFactory.getExtensionID(), MUNSLCAmendmentLine.Table_Name
				, MUNSLCAmendmentLine.COLUMNNAME_UNS_LC_Amendment_ID + " = " 
				+ getUNS_LC_Amendment_ID(), get_TrxName()).list();
		
		m_Lines = new MUNSLCAmendmentLine[list.size()];
		list.toArray(m_Lines);		
		return m_Lines;
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
	public String completeIt() 
	{
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
		
		if(m_processMsg != null)
			return DocAction.STATUS_Invalid;
		updateLCAttributes();
		
		setProcessed(true);	
		approveIt();
		//m_processMsg = info.toString();
	
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		// TODO Auto-generated method stub
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

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
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return getAD_Org_ID();
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
	
	/**
	 * 
	 * @return
	 */
	public MUNSLCAmdAttributes createNewAmendmentAttr()
	{
		MUNSLCAmdAttributes amdAttr = new MUNSLCAmdAttributes(getCtx(), 0, get_TrxName());
		MUNSLCAttributeConf attributeConf = MUNSLCAttributeConf.get(
				getCtx(), m_DescOfGoodsOrService, get_TrxName());
		if(null == attributeConf)
		{
			attributeConf = new MUNSLCAttributeConf(getCtx(), 0, get_TrxName());
			attributeConf.setAD_Org_ID(getAD_Org_ID());
			attributeConf.setDataType(MUNSLCAttributeConf.DATATYPE_Product);
			attributeConf.setDescription("Description of Goods Or Service");
			attributeConf.setHasItems(true);
			attributeConf.setName("Description of Goods Or Service");
			attributeConf.setValue(m_DescOfGoodsOrService);
			attributeConf.saveEx();
		}
		
		amdAttr.setUNS_LC_AttributeConf_ID(attributeConf.get_ID());
		amdAttr.setAD_Org_ID(getAD_Org_ID());
		amdAttr.setAttributeNumber(attributeConf.getValue());
		amdAttr.setDataType(attributeConf.getDataType());
		amdAttr.setDescription(attributeConf.getDescription());
		amdAttr.setHasItems(attributeConf.isHasItems());
		amdAttr.setName(attributeConf.getName());
		amdAttr.setUNS_LC_Amendment_ID(get_ID());
		amdAttr.saveEx();
		return amdAttr;
	}
	
	/**
	 * 
	 */
	public void updateLCAttributes()
	{
		MUNSLCBalance lcBalance = (MUNSLCBalance)getUNS_LC_Balance();
		for(MUNSLCAmendmentLine lcAmendmentLine : getLines(true))
		{
			MUNSLCAmdAttributes amdAttr = getAmendmentAttrOf(m_DescOfGoodsOrService);
			if(null == amdAttr)
				amdAttr = createNewAmendmentAttr();
			
			MUNSLCLines lcLine = lcBalance.getLCLineOf(lcAmendmentLine.getM_Product_Category_ID());
			if(null == lcLine)
			{
				lcLine = new MUNSLCLines(getCtx(), 0, get_TrxName());
				lcLine.setUNS_LC_Balance_ID(lcBalance.get_ID());
				lcLine.setAD_Org_ID(lcBalance.getAD_Org_ID());
				lcLine.setDescription("Created By LC Amendment " + getName());
				lcLine.setM_Product_Category_ID(lcAmendmentLine.getM_Product_Category_ID());
			}
			else
			{
				lcLine.setDescription("Updated By LC Amendment " + getName());
			}
			lcLine.setLCQuantity(lcAmendmentLine.getLCQuantity());
			lcLine.saveEx();
		}
		
		for(MUNSLCAmdAttributes lcAmendmentAttr : getAmendmentAttributes(true))
		{
			I_UNS_LC_AttributeConf attrConf = lcAmendmentAttr.getUNS_LC_AttributeConf();
			String attr = attrConf.getValue();
			MUNSLCAttributes lcAttr = lcBalance.getLCAttribute(attr);
			if(null == lcAttr)
			{
				lcAttr = new MUNSLCAttributes(getCtx(), 0, get_TrxName());
				lcAttr.setAD_Org_ID(getAD_Org_ID());
				lcAttr.setUNS_LC_AttributeConf_ID(lcAmendmentAttr.getUNS_LC_AttributeConf_ID());
				lcAttr.setName(lcAmendmentAttr.getName());
				lcAttr.setUNS_LC_Balance_ID(lcBalance.get_ID());
			}
			lcAttr.setAttributeNumber(lcAmendmentAttr.getAttributeNumber());
			lcAttr.setHasItems(lcAmendmentAttr.isHasItems());
			lcAttr.setDataType(lcAmendmentAttr.getDataType());
			lcAttr.setAttributeValueBigDecimal(lcAmendmentAttr.getNewAttributeValueBD());
			lcAttr.setAttributeValueDate(lcAmendmentAttr.getNewAttributeValueDate());
			lcAttr.setAttributeValueInteger(lcAmendmentAttr.getNewAttributeValueInt());
			lcAttr.setAttributeValueMemo(lcAmendmentAttr.getNewAttributeValueMemo());
			lcAttr.setAttributeValueString(lcAmendmentAttr.getNewAttributeValueString());
			lcAttr.setAttributeValueText(lcAmendmentAttr.getNewAttributeValueText());
			lcAttr.setC_Currency_ID(lcAmendmentAttr.getNewCurrency_ID());
			lcAttr.saveEx();
			
			lcAmendmentAttr.setUNS_LC_Attributes_ID(lcAttr.get_ID());
			lcAmendmentAttr.saveEx();
			if(lcAttr.isHasItems())
				lcAttr.updateItems(lcAmendmentAttr.getAttrItems(false));
				
			if (MUNSLCAttributes.CurrencyAmount_32B.equals(lcAmendmentAttr.getAttributeNumber()))
			{
				// Amamendment di attribute 32B adalah penambahan atau pengurangannya.
				// Untuk nilai setelah perubahan ada di attribute nomor 34B.
				lcBalance.setC_Currency_ID(lcAmendmentAttr.getNewCurrency_ID());
				lcBalance.setTotalAmt(lcAmendmentAttr.getNewAttributeValueBD().add(lcBalance.getTotalAmt()));
			}
			if (MUNSLCAttributes.PercentageCreditAmountTolerance_39A.equals(lcAmendmentAttr.getAttributeNumber()))
			{
				lcBalance.setToleranceAmt(lcAmendmentAttr.getNewAttributeValueAsBigDecimal());
			}
		}
		lcBalance.saveEx();
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSLCAmdAttributes[] getAmendmentAttributes(boolean requery)
	{
		if(null != m_AmendmentAttributes && !requery)
		{
			set_TrxName(m_AmendmentAttributes, get_TrxName());
			return m_AmendmentAttributes;
		}
		
		String tableName = MUNSLCAmdAttributes.Table_Name;
		String whereClause = MUNSLCAmdAttributes.COLUMNNAME_UNS_LC_Amendment_ID + " =?";
		List<MUNSLCAmdAttributes> list = Query.get(
				getCtx()
				, UNSPPICModelFactory.getExtensionID()
				, tableName
				, whereClause
				, get_TrxName())
				.setParameters(getUNS_LC_Amendment_ID())
				.list();
		m_AmendmentAttributes = new MUNSLCAmdAttributes[list.size()];
		return list.toArray(m_AmendmentAttributes);
	}
	
	public MUNSLCAmdAttributes getAmendmentAttrOf(String attributeNumber)
	{
		for(MUNSLCAmdAttributes lcAmdAttr : getAmendmentAttributes(false))
		{
			if(lcAmdAttr.getAttributeNumber().equals(attributeNumber))
				return lcAmdAttr;
		}
		return null;
	}
}
