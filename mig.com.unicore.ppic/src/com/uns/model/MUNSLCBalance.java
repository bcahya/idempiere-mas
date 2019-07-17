/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MCurrency;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSLCBalance extends X_UNS_LC_Balance implements DocAction, DocOptions {
	
	private MUNSLCOtherCondition[] m_OtherConditions = null;
	private MUNSLCLines[] m_LCLines = null;
	private MUNSLCAttributes[] m_AttributesLines = null;
	private MUNSLCAmendment[] m_Amendment = null;
	private String m_processMsg = null;
	private boolean m_justPrepared = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String m_latestShipDateAttr = "44C";
	
	/**
	 * @param ctx
	 * @param UNS_LC_Balance_ID
	 * @param trxName
	 */
	public MUNSLCBalance(Properties ctx, int UNS_LC_Balance_ID, String trxName) {
		super(ctx, UNS_LC_Balance_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLCBalance(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSLCOtherCondition[] getOtherCondition(boolean requery) {
		if (null != m_OtherConditions && !requery)
		{
			set_TrxName(m_OtherConditions, get_TrxName());
			return m_OtherConditions;
		}
		
		List<MUNSLCOtherCondition> list = Query.get(getCtx(), 
				UNSPPICModelFactory.getExtensionID(), MUNSLCOtherCondition.Table_Name
				, MUNSLCOtherCondition.COLUMNNAME_UNS_LC_Balance_ID + " = " 
				+ getUNS_LC_Balance_ID(), get_TrxName()).list();
		
		m_OtherConditions = new MUNSLCOtherCondition[list.size()];
		list.toArray(m_OtherConditions);
		
		return m_OtherConditions;
	}	
	
	
	public MUNSLCLines[] getLCLines(boolean requery) {
		if (null != m_LCLines && !requery)
		{
			set_TrxName(m_LCLines, get_TrxName());
			return m_LCLines;
		}
		
		List<MUNSLCOtherCondition> list = Query.get(getCtx(), 
				UNSPPICModelFactory.getExtensionID(), MUNSLCLines.Table_Name
				, MUNSLCLines.COLUMNNAME_UNS_LC_Balance_ID + " = " 
				+ getUNS_LC_Balance_ID(), get_TrxName()).list();
		
		m_LCLines = new MUNSLCLines[list.size()];
		list.toArray(m_LCLines);
		
		return m_LCLines;
	}	
	
	
	public MUNSLCAmendment[] getAmendment(boolean requery) {
		if (null != m_Amendment && !requery)
		{
			set_TrxName(m_Amendment, get_TrxName());
			return m_Amendment;
		}
		
		List<MUNSLCOtherCondition> list = Query.get(getCtx(), 
				UNSPPICModelFactory.getExtensionID(), MUNSLCAmendment.Table_Name
				, MUNSLCAmendment.COLUMNNAME_UNS_LC_Balance_ID + " = " 
				+ getUNS_LC_Balance_ID(), get_TrxName()).list();
		
		m_Amendment = new MUNSLCAmendment[list.size()];
		list.toArray(m_Amendment);
		
		return m_Amendment;
	}
	
	public Hashtable<String, MUNSLCLines> getMapOfLCLines()
	{
		Hashtable<String, MUNSLCLines> mapOfLCLines = new Hashtable<String, MUNSLCLines>();
		for(MUNSLCLines line : getLCLines(false))
		{
			mapOfLCLines.put(line.getUniqueLine(), line);
		}
		return mapOfLCLines;
	}
	
	protected boolean beforeSave(boolean newRecord)
	{

		resetGrandTotal();
		/*
		if(is_ValueChanged(COLUMNNAME_TotalAmt)
				|| is_ValueChanged(COLUMNNAME_TotalAmtUsed))
		*/
		updateAmtLeft();
		
		return super.beforeSave(newRecord);
	}

	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		
		
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
		
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		
		log.info(toString());
		setProcessing(false);
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
		
			m_justPrepared = true;
		
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		setProcessed(true);
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
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);	
		approveIt();
		//m_processMsg = info.toString();
	
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		
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
		
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		
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
		
		return null;
	}

	@Override
	public String getDocumentNo() {
		
		return null;
	}

	@Override
	public String getDocumentInfo() {
		
		return null;
	}

	@Override
	public File createPDF() {
		
		return null;
	}

	@Override
	public String getProcessMsg() {
		
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		
		return getAD_Org_ID();
	}

	@Override
	public int getC_Currency_ID() {
		
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		
		return null;
	}
	
	public MUNSLCLines getLCLineOf(int M_Product_Category_ID)
	{
		return Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID()
				, MUNSLCLines.Table_Name, COLUMNNAME_UNS_LC_Balance_ID + " = " + getUNS_LC_Balance_ID()
				+ " AND " + MUNSLCLines.COLUMNNAME_M_Product_Category_ID + " = " + M_Product_Category_ID
				+ " AND " + COLUMNNAME_IsActive + " = 'Y'"
				,  get_TrxName())
				.firstOnly();
	}
	
	protected void updateAmtLeft()
	{
		double totalAmt = getGrandTotal().doubleValue();
		double totalAmtUsed = getTotalAmtUsed().doubleValue();
		double totalAmtLeft = totalAmt - totalAmtUsed;
		setTotalAmtLeft(new BigDecimal(totalAmtLeft));
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSLCAttributes[] getLCAttributes(boolean requery)
	{
		if(null != m_AttributesLines && !requery)
		{
			set_TrxName(m_AttributesLines, get_TrxName());
			return m_AttributesLines;
		}
		
		String whereClause = COLUMNNAME_UNS_LC_Balance_ID + " =?";
		List<MUNSLCAttributes> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID()
				, MUNSLCAttributes.Table_Name
				, whereClause
				, get_TrxName())
				.setParameters(getUNS_LC_Balance_ID())
				.list();
		m_AttributesLines = new MUNSLCAttributes[list.size()];
		return list.toArray(m_AttributesLines);
	}
	
	/**
	 * 
	 * @param amt
	 * @return
	 */
	public boolean isAllowedAmtSO(BigDecimal amt)
	{
		return getTotalAmtLeft().compareTo(amt) >= 0;
	}
	
	public boolean isAllowedAmtShipment(BigDecimal amt)
	{
		return getTotalAmtUsed().compareTo(amt) >=0;
	}
	
	public boolean isAllowedShipment(Timestamp date)
	{
		Timestamp latestShipedDate = (Timestamp)getValueOfAttribute(m_latestShipDateAttr);
		if(null == latestShipedDate)
			throw new AdempiereUserError("Attribut 44C - Latest Shipment Date has not set");
		
		return date.getTime() <= latestShipedDate.getTime();
	}
	
	/**
	 * 
	 * @param p_Attribute
	 * @return
	 */
	public Object getValueOfAttribute(String p_Attribute)
	{
		Object object = null;
		MUNSLCAttributes attribute = getLCAttributeNotHaveItems(p_Attribute);
		if(null == attribute)
			return object;
		if(attribute.getDataType().equals(MUNSLCAttributes.DATATYPE_Integer))
			object = attribute.getAttributeValueInteger();
		else if(attribute.getDataType().equals(MUNSLCAttributes.DATATYPE_Date))
			object = attribute.getAttributeValueDate();
		else if(attribute.getDataType().equals(MUNSLCAttributes.DATATYPE_Memo))
			object = attribute.getAttributeValueMemo();
		else if(attribute.getDataType().equals(MUNSLCAttributes.DATATYPE_Number))
			object = attribute.getAttributeValueBigDecimal();
		else if(attribute.getDataType().equals(MUNSLCAttributes.DATATYPE_String))
			object = attribute.getAttributeValueString();
		else if(attribute.getDataType().equals(MUNSLCAttributes.DATATYPE_Text))
			object = attribute.getAttributeValueText();
		else if(attribute.getDataType().equals(MUNSLCAttributes.DATATYPE_Product))
			object = null;
		else
			throw new AdempiereUserError("Unknown Data Type" + attribute.getDataType());
		return object;
	}
	
	/**
	 * 
	 * @param attrbute
	 * @return
	 */
	public MUNSLCAttributes getLCAttribute(String attrbute)
	{
		for (MUNSLCAttributes lcAttributes : getLCAttributes(false))
		{
			if(lcAttributes.getAttributeNumber().equals(attrbute))
				return lcAttributes;
		}
		return null;
	}
	
	/**
	 * 
	 * @param attrbute
	 * @return
	 */
	public MUNSLCAttributes getLCAttributeHasItems(String attrbute)
	{
		for (MUNSLCAttributes lcAttributes : getLCAttributes(false))
		{
			if(!lcAttributes.isHasItems())
				continue;
			if(lcAttributes.getAttributeNumber().equals(attrbute))
				return lcAttributes;
		}
		return null;
	}
	
	/**
	 * 
	 * @param Attribute
	 * @return
	 */
	public MUNSLCAttributes getLCAttributeNotHaveItems(String Attribute)
	{
		for(MUNSLCAttributes lcAttributes : getLCAttributes(false))
		{
			if(lcAttributes.isHasItems())
				continue;
			if(lcAttributes.getAttributeNumber().equals(Attribute))
				return lcAttributes;
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isAllowedPartialShipment()
	{
		String partialShipment = (String)getValueOfAttribute("43P");
		return "ALLOWED".equals(partialShipment);
	}
	
	/**
	 * 
	 * @param lcAmtAttr
	 * @param maxTolerance (maximum tolerance is maxTolerance/100).
	 */
	public void resetGrandTotal()
	{
		BigDecimal maxToleranceAmt = getToleranceAmt().multiply(new BigDecimal(0.01)).multiply(getTotalAmt());
		
		int currencyPrecision = MCurrency.getStdPrecision(getCtx(), getC_Currency_ID());
		setGrandTotal(getTotalAmt().add(maxToleranceAmt).setScale(currencyPrecision, BigDecimal.ROUND_HALF_DOWN));
	}
	
	
}
