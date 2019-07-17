/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import org.compiere.model.MProduct;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;

import com.uns.model.MUNSOtherProductConf;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSMRP extends X_UNS_MRP implements DocAction, DocOptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSMRPWeekly[] m_listWeeklysMRP = null;
	private MUNSMRP[] m_Childs = null;

	/**
	 * @param ctx
	 * @param UNS_MRP_ID
	 * @param trxName
	 */
	public MUNSMRP(Properties ctx, int UNS_MRP_ID, String trxName) {
		super(ctx, UNS_MRP_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMRP(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 
	 * @param mpsProduct
	 * @param BOMQty
	 * @param material
	 */
	private void initQty(MUNSMPSProduct mpsProduct, double BOMQty,MProduct material)
	{
		double actualMPSQtyUOM = 0.0;
		if(mpsProduct.getTotalActualToOrderUOM().compareTo(BigDecimal.ZERO) > 0)
		{
			actualMPSQtyUOM = mpsProduct.getTotalActualToOrderUOM().doubleValue();
		}
		else
		{
			actualMPSQtyUOM = mpsProduct.getTotalActualToStockUOM().doubleValue();
		}		
		
		double materialWeight = material.getWeight().doubleValue();
		double totalQtyMaterial = actualMPSQtyUOM * BOMQty;
		double totalQtyMaterialMT = totalQtyMaterial * materialWeight /1000;
		
		setGrandTotalMt(new BigDecimal(totalQtyMaterialMT));
		setGrandTotalUOM(new BigDecimal(totalQtyMaterial));
		setTotalGrossManufacturMT(new BigDecimal(totalQtyMaterialMT));
		setTotalGrossManufacturUOM(new BigDecimal(totalQtyMaterial));
	}
	
	/**
	 * 
	 * @param mpsProduct
	 * @param BOM
	 */
	private void setQty(
			MUNSMPSProduct mpsProduct, MProduct material
			, Hashtable<Integer, BigDecimal> expectedStorageOnHands)
	{
		int materialProduct_ID = material.getM_Product_ID();
		BigDecimal BOMQty = BigDecimal.ZERO;
		BOMQty = MUNSResource.getBOMQty(mpsProduct.getM_Product_ID(), materialProduct_ID, get_TrxName());
		initQty(mpsProduct, BOMQty.doubleValue(), material);

		double QtyUOMOnHand = 0.0;
		MStorageOnHand[] StorageOnHand = MStorageOnHand.getOfProduct(
				getCtx(), materialProduct_ID, get_TrxName());
		
		BigDecimal expectedStorageOnHandOfProduct = 
				expectedStorageOnHands.get(materialProduct_ID); 
		if (null != expectedStorageOnHandOfProduct)
		{
			QtyUOMOnHand = expectedStorageOnHandOfProduct.doubleValue();
		}
		else
		{
			for (MStorageOnHand SOH : StorageOnHand)
			{
				QtyUOMOnHand += SOH.getQtyOnHand().doubleValue();
			}
		}

		setExpectedStorageOnHand(new BigDecimal(QtyUOMOnHand));
	}
	
	/**
	 * 
	 * @param mpsProduct
	 * @param material
	 * @return
	 */
	public void generateListProduct(
			MUNSMPSProduct mpsProduct, MProduct material
			, Hashtable<Integer, BigDecimal> expectedStorageOnHands)
	{
		setUNS_MPSProduct_ID(mpsProduct.getUNS_MPSProduct_ID());
		setAD_Org_ID(mpsProduct.getAD_Org_ID());
		setQty(mpsProduct, material, expectedStorageOnHands );	
		MUNSOtherProductConf opc= MUNSOtherProductConf.get(
				getCtx(), material.getM_Product_ID(), get_TrxName());
		if (null == opc) {
			throw new IllegalArgumentException(
					"No Product Configuration " +material.getValue() + "-" +material.getName());
		}
		//TODO MRP
//		int leadTime = DB.getSQLValue(
//				get_TrxName(), "SELECT MAX(" + MBPartnerProduct.COLUMNNAME_LeadTime + ") FROM "
//				+ MBPartnerProduct.Table_Name + " WHERE "
//				+ MBPartnerProduct.COLUMNNAME_M_Product_ID + "=?", material.getM_Product_ID());
//		
//		setC_UOM_ID(material.getC_UOM_ID());		
//		setName("MRP_"+material.getValue()+"_"+material.getName());
//		setM_Product_ID(material.getM_Product_ID());
//		setSafetyStock(opc.getSafetyStock());
//		setMOQ(opc.getMOQ());
//		setLeadTime(leadTime);
		
	}
	
	/**
	 * 
	 * @param From
	 */
	public void copyFrom(MUNSMRP From)
	{
		setAD_Org_ID(From.getAD_Org_ID());
		setC_UOM_ID(From.getC_UOM_ID());
		setDescription(From.getDescription());
		setLeadTime(From.getLeadTime());
		setM_Product_ID(From.getM_Product_ID());
		setMOQ(From.getMOQ());
		setGrandTotalMt(From.getGrandTotalMt());
		setGrandTotalUOM(From.getGrandTotalUOM());
		setLeadTime(From.getLeadTime());
		setSafetyStock(From.getSafetyStock());
		setExpectedStorageOnHand(From.getExpectedStorageOnHand());
	}
	
	
	public void setScaleAllQty()
	{
		//set Qty MT Jan --> Dec
		setGrandTotalMt(getGrandTotalMt().setScale(2, RoundingMode. HALF_UP));
		setGrandTotalUOM(getGrandTotalUOM().setScale(2, RoundingMode. HALF_UP));
	}
	
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSMRPWeekly[] getWeeklysLines(boolean requery)
	{
		if (null != m_listWeeklysMRP && !requery)
		{
			set_TrxName(m_listWeeklysMRP, get_TrxName());
			return m_listWeeklysMRP;
		}
		
		final String whereClause = X_UNS_MRPWeekly.COLUMNNAME_UNS_MRP_ID + "=?";
		List<X_UNS_MRPWeekly> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), X_UNS_MRPWeekly.Table_Name, whereClause
				, get_TrxName()).setParameters(getUNS_MRP_ID())
				.setOrderBy(X_UNS_MRPWeekly.COLUMNNAME_WeekNo)
				.list();
		
		m_listWeeklysMRP = new MUNSMRPWeekly[list.size()];
		list.toArray(m_listWeeklysMRP);
		return m_listWeeklysMRP;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSMRPWeekly[] getWeeklysLines()
	{
		return getWeeklysLines(false);
	}
	
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSMRP[] getChilds(boolean requery)
	{
		if (null != m_Childs && !requery)
		{
			set_TrxName(m_Childs, get_TrxName());
			return m_Childs;
		}
		
		final String whereClause = X_UNS_MRP.COLUMNNAME_MRPParent_ID + "=?";
		List<X_UNS_MRP> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), Table_Name, whereClause
				, get_TrxName())
				.setParameters(getUNS_MRP_ID())
				.setOrderBy(COLUMNNAME_UNS_MRP_ID)
				.list();
		m_Childs = new MUNSMRP[list.size()];
		list.toArray(m_Childs);
		return m_Childs;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean deleteLinesWeekly()
	{
		MUNSMRPWeekly[] weeklysMRP = getWeeklysLines();
		for (MUNSMRPWeekly weeklyMRP : weeklysMRP) 
		{
			if (!weeklyMRP.delete(true))
				return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public MUNSMRP[] getChilds()
	{
		return getChilds(false);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean deleteChilds()
	{
		MUNSMRP[] childs = getChilds();
		for (MUNSMRP child : childs)
		{
			MUNSMRPWeekly[] weeklysOfChilds = child.getWeeklysLines();
			if (null != weeklysOfChilds)
			{
				child.deleteLinesWeekly();
			}
			if (!child.delete(true))
				return false;
		}
		return true;
	}
	
	@Override
	public boolean beforeDelete()
	{
		if (getMRPParent_ID() <=0)
		{
			if (!deleteLinesWeekly())
				return false;
		}
		else
		{
			if (!deleteChilds())
				return false;
		}

		return super.beforeDelete();
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
	
	private String m_processMsg = null;
	private boolean m_justPrepared = false;

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
		return false;
	}

	@Override
	public boolean invalidateIt() {
		// TODO Auto-generated method stub
		return false;
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
		return false;
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
		
		setProcessed(true);	
		
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}
		
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
		return false;
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
	
	
		
	/**
	 * 
	 * @return
	 */
	public BigDecimal getOnHandQty()
	{
		return getExpectedStorageOnHand();
	}
	
	public Hashtable<Integer, MUNSMRPWeekly> getMapOfMRPWeekly()
	{
		Hashtable<Integer, MUNSMRPWeekly> mapOfMRPWeekly =
				new Hashtable<Integer, MUNSMRPWeekly>();
		
		for(MUNSMRPWeekly mrpWeekly : getWeeklysLines())
		{
			mapOfMRPWeekly.put(mrpWeekly.getWeekNo(), mrpWeekly);
		}
		
		return mapOfMRPWeekly;
	}
	
	/*
	public BigDecimal getLastPrevOnHand(int endWeek)
	{
		 BigDecimal lastOnHand =  DB.getSQLValueBD(
				get_TrxName(), "SELECT " + MUNSMRPWeekly.COLUMNNAME_AROH 
				+ " FROM " + MUNSMRPWeekly.Table_Name 
				+ " WHERE " + MUNSMRPWeekly.COLUMNNAME_UNS_MRP_ID + " = ?" 
				+ " AND " + MUNSMRPWeekly.COLUMNNAME_WeekNo 
				+ " = ?"
				+ " AND " + MUNSMRPWeekly.COLUMNNAME_IsActive + " = 'Y'"
				,  getUNS_MRP_ID(), endWeek);
		 if (null == lastOnHand)
			 lastOnHand = BigDecimal.ZERO;
		 
		return lastOnHand;
	}
	*/
}