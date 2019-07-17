/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.X_UNS_Product_StickerInfo;
import com.uns.util.ErrorMsg;

import com.uns.base.model.Query;
import com.uns.model.MUNSSERLineProduct;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author YAKA
 * @Rev menjangan
 * 
 */
public class MUNSProductionSchedule extends X_UNS_ProductionSchedule implements DocAction, DocOptions{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7503960148583912651L;

	/**
	 * @param ctx
	 * @param UNS_ProductionSchedule_ID
	 * @param trxName
	 */
	public MUNSProductionSchedule(Properties ctx,
			int UNS_ProductionSchedule_ID, String trxName) {
		super(ctx, UNS_ProductionSchedule_ID, trxName);
		
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSProductionSchedule(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
	}
	
	public MUNSSERLineProduct getSERLineProduct(){
		return new MUNSSERLineProduct(getCtx(), getUNS_SERLineProduct_ID(), get_TrxName());
	}
	
	public MUNSPSRealization[] getLinesReal() {
		String wc = MUNSPSRealization.COLUMNNAME_UNS_ProductionSchedule_ID + "=?";
		List<MUNSPSRealization> mList = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSPSRealization.Table_Name, wc, get_TrxName())
				.setParameters(getUNS_ProductionSchedule_ID())
				.setOrderBy(MUNSPSRealization.COLUMNNAME_UNS_PSRealization_ID)
				.list();

		return mList.toArray(new MUNSPSRealization[mList.size()]);
	}
	
	/**
	 * 
	 * @return
	 */
	protected boolean deleteRealization()
	{
		MUNSPSRealization[] lines = getLinesReal();
		for (MUNSPSRealization line : lines)
		{
			if (!line.delete(true))
				return false;
		}
		return true;
	}
	
	public MUNSPSSOAllocation[] getLinesAllo() {
		String wc = MUNSPSSOAllocation.COLUMNNAME_UNS_ProductionSchedule_ID + "=?";
		List<MUNSPSSOAllocation> mList = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSPSSOAllocation.Table_Name, wc
						,get_TrxName()).setParameters(getUNS_ProductionSchedule_ID())
						.setOrderBy(
								MUNSPSSOAllocation.COLUMNNAME_UNS_PSSOAllocation_ID)
								.list();

		return mList.toArray(new MUNSPSSOAllocation[mList.size()]);
	}

	/**
	 * 
	 * @return
	 */
	protected boolean deleteAlocation()
	{
		MUNSPSSOAllocation[] lines = getLinesAllo();
		for (MUNSPSSOAllocation line : lines)
		{
			if (!line.delete(true))
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 */
	public boolean beforeDelete()
	{
		String sql = "DELETE FROM UNS_Production_StickerInfo WHERE UNS_ProductionSchedule_ID=?";
		DB.executeUpdate(sql, getUNS_ProductionSchedule_ID(), get_TrxName());
		if (!deleteRealization())
		{
			ErrorMsg.setErrorMsg(getCtx(), "deleteError", "Could not delete Realization");
			return false;
		} 
		
		if (!deleteAlocation())
		{
			ErrorMsg.setErrorMsg(getCtx(), "deleteError", "Could not delete Allocation");
			return false;
		} 
		
		return true;
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getPSType().equals(X_UNS_ProductionSchedule.PSTYPE_MasterProductionSchedule))
		{
			setSourceProduct_ID(0);
		}
		if (!DOCSTATUS_Completed.equals(getDocStatus()) && getQtyOrdered().signum() == 0)
			throw new FillMandatoryException("Cannot save zero quantity to manufacture order.");
			
		if (isHasStickerInfo())
			setHasStickerInfo(true);

		return true;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (isHasStickerInfo())
		{
			setHasStickerInfo(true);
			DB.executeUpdate("DELETE FROM UNS_Production_StickerInfo WHERE UNS_ProductionSchedule_ID=?", 
							 getUNS_ProductionSchedule_ID(), get_TrxName());
			List<X_UNS_Product_StickerInfo> prodStickerList = 
					Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
							X_UNS_Product_StickerInfo.Table_Name, 
							"M_Product_ID=" + getM_Product_ID(), get_TrxName())
					.list();
			for (X_UNS_Product_StickerInfo prodSticker : prodStickerList)
			{
				X_UNS_Production_StickerInfo newStickerInfo = 
						new X_UNS_Production_StickerInfo(getCtx(), 0, get_TrxName());
				newStickerInfo.setUNS_ProductionSchedule_ID(getUNS_ProductionSchedule_ID());
				newStickerInfo.setProductSticker_ID(prodSticker.getProductSticker_ID());
				newStickerInfo.setName(prodSticker.getName());
				newStickerInfo.setStickerInfo(prodSticker.getStickerInfo());
				newStickerInfo.setStickerRemarks(prodSticker.getStickerRemarks());
				newStickerInfo.saveEx();
				//m_processMsg = "Create default sticker info from product failed. You need to create it manually.";
			}
		}
		return true;
	}
	
	/**
	 * 
	 */
	public boolean isHasStickerInfo()
	{
		if (super.isHasStickerInfo())
			return true;

		int countSticker = DB.getSQLValue(null, 
				"SELECT count(*) FROM UNS_Product_StickerInfo WHERE M_Product_ID=?", 
				getM_Product_ID());
		
		return (countSticker > 0);
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

	private String m_processMsg = null;
	
	@Override
	public boolean processIt(String action) throws Exception {
		
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		
		log.info(toString());
		return true;
	}

	@Override
	public boolean invalidateIt() {
		
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}

	private boolean m_justPrepared = false;
	
	@Override
	public String prepareIt() 
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		//TODO not use MPS
//		if (PSTYPE_MasterProductionSchedule.equals(getPSType()) 
//			|| PSTYPE_Rebundle.equals(getPSType()))
//			if(!(getUNS_MPSProductRsc_Weekly_ID()>0))
//				throw new AdempiereUserError("Please input master resource product weekly before complete.");
		
		if (getQtyOrdered().compareTo(Env.ZERO) <= 0)
			throw new AdempiereUserError ("Cannot completing zero ordered quantity schedule.");
		
		if (isHasStickerInfo())
		{
			int countUndefinedSticker = DB.getSQLValue(get_TrxName(), 
					"SELECT COUNT(*) FROM UNS_Production_StickerInfo " +
					" WHERE ProductSticker_ID Is Null OR ProductSticker_ID=0 AND UNS_Production_StickerInfo=?",
					getUNS_ProductionSchedule_ID());
			if (countUndefinedSticker > 0) {
				m_processMsg = "Cannot complete schedule while there's an undefined Product Sticker.";
				return STATUS_Invalid;
			}
		}
		
		//TODO NO MPS 
//		if ((PSTYPE_Rebundle.equals(getPSType()) 
//			 || PSTYPE_Restickering.equals(getPSType())
//			 || PSTYPE_MasterProductionSchedule.equals(getPSType()))
//			&& getUNS_MPSProductRsc_Weekly_ID() == 0 
//			&& getM_Product().isSold())
//		{
//			MUNSManufacturingOrder mo = (MUNSManufacturingOrder) getUNS_Manufacturing_Order();
//			
//			
//			// Create the MPS for this production schedule.
//			MUNSMPSProduct mpsProduct = MUNSMPSProduct.getCreate(getCtx(), 
//														   mo.getUNS_MPSHeader_ID(), 
//														   getM_Product_ID(), 
//														   get_TrxName());
//			if (mpsProduct.save())
//				throw new AdempiereException("Failed when create new MPS Product.");
//			
//			int weekNo = Integer.valueOf(mo.getWeekNo());
//			
//			MUNSMPSProductWeekly mpsProductWeekly =
//					MUNSMPSProductWeekly.get(getCtx(), mpsProduct, weekNo, get_TrxName());
//
//			if (mpsProductWeekly.save())
//				throw new AdempiereException("Failed when create new MPS Product Weekly.");
//			
//			MUNSResource rsc = 
//					MUNSResource.getChildOf(getCtx(), get_TrxName(), 
//										   mo.getProductionDepartment_ID(), 
//										   getM_Product_ID(), 
//										   mo.getUNS_MPSHeader().getUNS_Forecast_Header().getUNS_Resource_ID());
//			if (rsc == null)
//				throw new AdempiereUserError ("Cannot Rebundle schedule for undefined Manufacturing Resource of the product.");
//			
//			MUNSMPSProductResource mpsProductRsc = 
//					MUNSMPSProductResource.get(getCtx(), 
//											   mpsProduct.getUNS_MPSProduct_ID(), 
//											   rsc.getUNS_Resource_ID(), 
//											   get_TrxName());
//			if (mpsProductRsc.save())
//				throw new AdempiereException("Failed when create new MPS Product Resource.");
//				
//			MUNSMPSProductRscWeekly mpsProductRscWeekly =  
//					MUNSMPSProductRscWeekly.get(getCtx(), 
//												mpsProductRsc.getUNS_MPSProduct_Resource_ID(), 
//												weekNo, 
//												get_TrxName());
//			
//			if (mpsProductRscWeekly.save())
//				throw new AdempiereException("Failed when create new MPS Product Resource Weekly.");
//			
//			setUNS_MPSProductRsc_Weekly_ID(mpsProductRscWeekly.getUNS_MPSProductRsc_Weekly_ID());
//		}

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
		
		return false;
	}

	@Override
	public boolean rejectIt() {
		
		log.info(toString());
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
		/**
		MUNSSrcProductDetail[] srcProductDetails = getLinesSrcProductDetail(false);
		if (!getPSType().equals(PSTYPE_MasterProductionSchedule))
		{
			if (null == srcProductDetails)
			{
				m_processMsg = "NO LINES PRODUCT DETAIL TO REBUNDLE";
				return DocAction.STATUS_Invalid;
			}
			else if(!isValidQty())
			{
				m_processMsg = "Quantity is invalid, the total quantity of lines Source Product" +
						"must be same with quantity in production schedule header";
				return DocAction.STATUS_Invalid;
			}
			else
			{
				//updateProduction(); // Salah kaprah!!! (noted by Harry)
				if (m_processMsg != null)
					return DocAction.STATUS_Invalid;
			}
		} else {
			if (!MUNSMPSProductRscWeekly.updateFromMO(this))
				return DOCSTATUS_Invalid;
		}
		*/
		//TODO NO MPS
//		if (PSTYPE_MasterProductionSchedule.equals(getPSType())
//			|| PSTYPE_Rebundle.equals(getPSType())
//			|| PSTYPE_Restickering.equals(getPSType()))
//		{
//			MUNSMPSProductRscWeekly mpsRscWeekly = (MUNSMPSProductRscWeekly) getUNS_MPSProductRsc_Weekly();
//			boolean updated = mpsRscWeekly.updateFromMO(getCtx(), this, get_TrxName());
//			if (!updated)
//			{
//				m_processMsg += "Error while updating MPS.";
//				return DocAction.STATUS_Invalid;
//			}
//		}
		
		setProcessed(true);	
		//m_processMsg = info.toString();
		//
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
			
				//
				// Void Confirmations
				setDocStatus(DOCSTATUS_Voided); // need to set & save docstatus to be able to check it in MInOutConfirm.voidIt()
				saveEx();
			}
			else
			{
				if (getPSType().equals(PSTYPE_MasterProductionSchedule)) {
					setDocStatus(DOCSTATUS_Voided); // need to set & save docstatus to be able to check it in MInOutConfirm.voidIt()
					saveEx();
				}
				else {

					return reverseCorrectIt();
				}
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
	public boolean reverseCorrectIt() 
	{	
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;
		
		MUNSProductionSchedule reversal = this;

		//FR1948157
		
		try {
			if (!reversal.processIt(DocAction.ACTION_Complete)
					|| !reversal.getDocStatus().equals(DocAction.STATUS_Completed))
				{
					m_processMsg = "Reversal ERROR: " + reversal.getProcessMsg();
					return false;
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		reversal.closeIt();
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		reversal.saveEx(get_TrxName());
		//
		
		//
		// Void Confirmations
		setDocStatus(DOCSTATUS_Reversed); // need to set & save docstatus to be able to check it in MInOutConfirm.voidIt()
		saveEx();

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		m_processMsg = reversal.getDocumentNo();
		//FR1948157
		//this.setReversal_ID(reversal.getUNS_BongkarMuat_ID());
		setProcessed(true);
		setDocStatus(DOCSTATUS_Reversed);		//	 may come from void
		setDocAction(DOCACTION_None);
		return true;
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
		
		return false;
	}

	@Override
	public String getSummary() {
		
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
		
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		
		return null;
	}


	private MUNSSrcProductDetail[] m_linesProductDetail = null;
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSSrcProductDetail[] getLinesSrcProductDetail(boolean requery)
	{
		if (null != m_linesProductDetail && !requery)
		{
			set_TrxName(m_linesProductDetail, get_TrxName());
			return m_linesProductDetail;
		}
		final String whereClause = X_UNS_SrcProduct_Detail.COLUMNNAME_UNS_ProductionSchedule_ID+ "=?";
		List<X_UNS_SrcProduct_Detail> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), X_UNS_SrcProduct_Detail.Table_Name
				, whereClause, get_TrxName()).setParameters(getUNS_ProductionSchedule_ID())
				.setOrderBy(X_UNS_SrcProduct_Detail.COLUMNNAME_UNS_SrcProduct_Detail_ID)
				.list();
		
		m_linesProductDetail = new MUNSSrcProductDetail[list.size()];
		list.toArray(m_linesProductDetail);
		
		return m_linesProductDetail;
	}
	
	/**
	 * 
	 * @return
	 *
	private boolean isValidQty()
	{
		MUNSSrcProductDetail[] srcProductDetails = getLinesSrcProductDetail(false);
		BigDecimal totalQty = BigDecimal.ZERO;
		for (MUNSSrcProductDetail srcProductDetail : srcProductDetails)
		{
			totalQty = totalQty.add(srcProductDetail.getQtyUom());
		}
		if (totalQty.compareTo(getQtyUom()) != 0)
			return false;
		
		return true;
	}
	*/
	public MUNSProductionSchedule[] get(int M_SERLineProduct_ID) {
		MUNSProductionSchedule[] ps = null;
		final String whereClause = COLUMNNAME_UNS_SERLineProduct_ID + "=? AND UNS_ProductionSchedule_ID !=? AND PsType = 'MPS'";
		List<X_UNS_ProductionSchedule> list = Query.get(
				getCtx(), UNSPPICModelFactory.getExtensionID(), Table_Name, whereClause, get_TrxName())
				.setParameters(M_SERLineProduct_ID, getUNS_ProductionSchedule_ID())
				.list();
		ps = new MUNSProductionSchedule[list.size()];
		list.toArray(ps);
		return ps;
	}
	/*
	private void updateProductionSchedule() 
	{
		MUNSProductionSchedule[] scheduleToReplaces = get(getUNS_SERLineProduct_ID());
		for (MUNSProductionSchedule scheduleToReplace : scheduleToReplaces) {
			BigDecimal subtracterUOM = getQtyUom().divide(new BigDecimal(scheduleToReplaces.length));
			BigDecimal subtracterMT = getQtyMT().divide(new BigDecimal(scheduleToReplaces.length));
			scheduleToReplace.setQtyUom(scheduleToReplace.getQtyUom().subtract(subtracterUOM));
			scheduleToReplace.setQtyMT(scheduleToReplace.getQtyMT().subtract(subtracterMT));
			scheduleToReplace.setDocStatus(DOCSTATUS_Voided);
			scheduleToReplace.setDocAction(DocAction.ACTION_None);
			scheduleToReplace.setProcessed(true);
			//scheduleToReplace.setName(scheduleToReplace.getName() + " Replace To " + getName());
			scheduleToReplace.save();
//			scheduleToReplace.voidIt();
		}
	}
	*/
	/*
	private void updateProduction()
	{
		updateProductionSchedule();
		MUNSSrcProductDetail[] srcProductionDetails = getLinesSrcProductDetail(false);
		for(MUNSSrcProductDetail srcProductionDetail : srcProductionDetails)
		{
			MUNSProductionDetail productionDetail = srcProductionDetail.getProductionDetail();
			if (null == productionDetail)
				m_processMsg = "NO PRODUCTION DETAIL TO REBUNDLE";
			else
				m_processMsg = productionDetail.update(srcProductionDetail);
		}
	}
	*/
	public boolean createPSfromProduction(MUNSProductionDetail pd)
	{
		MOrg org = MOrg.get(getCtx(), pd.getAD_Org_ID());
		MProduct product = MProduct.get(getCtx(), pd.getM_Product_ID());
		setClientOrg(pd);
		setDocAction(DOCACTION_Prepare);
		setDocStatus(DOCSTATUS_Drafted);
		setPSType(X_UNS_ProductionSchedule.PSTYPE_MasterProductionSchedule);
		setQtyMT(pd.getMovementQty().multiply(product.getWeight()).multiply(new BigDecimal(0.001)));
		setQtyUom(pd.getMovementQty());
		setDescription ("Manufacturing Order " + org.getName() + " "+pd.getM_Product().getName());
		setProductionRemarks("Production "+pd.getM_Product().getName());
		setStickerRemarks("(-)");
		return save(get_TrxName());
	}
	
	public static boolean createManufacturingOrder(Properties ctx, String trxName, MUNSProductionDetail pd) {
		MUNSProductionSchedule new_MO = new MUNSProductionSchedule(ctx, 0, trxName);
		if(!new_MO.createPSfromProduction(pd))
				return false;
			
		MUNSPSRealization.createRealization(new_MO, pd, ctx, trxName);
		return true;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_MPSProductRscDaily_ID
	 * @param trxName
	 * @return
	 */
	public static List<MUNSProductionSchedule> getByMPS(Properties ctx, int UNS_MPSProductRscDaily_ID, String trxName)
	{
		return Query.get(
				ctx, UNSPPICModelFactory.getExtensionID()
				, Table_Name, COLUMNNAME_UNS_MPSProductRsc_Weekly_ID + " = " 
				+ UNS_MPSProductRscDaily_ID, trxName).list();
	}
	
	public boolean hasRealization()
	{
		return DB.getSQLValue(
				get_TrxName(), "SELECT COUNT(" + MUNSPSRealization.COLUMNNAME_UNS_PSRealization_ID 
				+ ") FROM " + MUNSPSRealization.Table_Name + " WHERE " 
						+ MUNSPSRealization.COLUMNNAME_UNS_ProductionSchedule_ID + " = " 
				+ getUNS_ProductionSchedule_ID()) > 0;
	}

}
