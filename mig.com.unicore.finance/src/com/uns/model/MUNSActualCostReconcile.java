/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MProductPricing;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_AD_Org;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSFinanceModelFactory;

/**
 * @author AzHaidar
 *
 */
public class MUNSActualCostReconcile extends X_UNS_ActualCostReconcile
		implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2419447891902724106L;
	
	private String m_processMsg = null;
	
	private boolean m_justPrepared = false;
	
	private Hashtable<Integer, BigDecimal> m_MapOfActualItemRevenue = new Hashtable<Integer, BigDecimal>();

	/**
	 * Items of this actual cost.
	 */
	private MUNSActualCostItem[] m_actualItems = null;
	
	private Hashtable<Integer, MUNSActualCostItem[]> m_actualItemsTable = 
			new Hashtable<Integer, MUNSActualCostItem[]>();

	/**
	 * Item Lines of this actual cost.
	 */
	private X_UNS_ActualCostItemLine[] m_actualLines = null;
	
	private Hashtable<Integer, X_UNS_ActualCostItemLine[]> m_actualLinesTable = 
			new Hashtable<Integer, X_UNS_ActualCostItemLine[]>();

	/**
	 * @param ctx
	 * @param UNS_ActualCostReconcile_ID
	 * @param trxName
	 */
	public MUNSActualCostReconcile(Properties ctx,
			int UNS_ActualCostReconcile_ID, String trxName) {
		super(ctx, UNS_ActualCostReconcile_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSActualCostReconcile(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * Get Items of this Actual Cost.
	 * @param requery
	 * @return
	 */
	public MUNSActualCostItem[] getActualItems(boolean requery, int AD_Org_ID) 
	{
		if (m_actualItemsTable.isEmpty() && !requery) {
			//set_TrxName(m_actualLines, get_TrxName());
			MUNSActualCostItem[] actualItems = m_actualItemsTable.get(AD_Org_ID);
			if (actualItems != null)
				return actualItems;
		}

		List<MUNSActualCostConfigItem> list = Query
				.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID,
						MUNSActualCostItem.Table_Name, "UNS_ActualCostReconcile_ID=? AND AD_Org_ID=? AND IsActive=?",
						get_TrxName())
				.setParameters(new Object[] { getUNS_ActualCostReconcile_ID(), AD_Org_ID, "Y" })
				.setOrderBy(MUNSActualCostItem.COLUMNNAME_UNS_ActualCostItem_ID).list();

		MUNSActualCostItem[] actualLines = new MUNSActualCostItem[list.size()];
		list.toArray(actualLines);
		m_actualItemsTable.put(AD_Org_ID, actualLines);
		
		return actualLines;
	}
	
	/**
	 * Get Items of this Actual Cost.
	 * @param requery
	 * @return
	 */
	public MUNSActualCostItem[] getActualItems(boolean requery) 
	{
		if (m_actualItems != null && !requery) {
			set_TrxName(m_actualItems, get_TrxName());
			return m_actualItems;
		}
		
		String orderByClause = MUNSActualCostItem.COLUMNNAME_AD_Org_ID + ", " + 
						 	   MUNSActualCostItem.COLUMNNAME_UNS_JointCostGroup_ID;

		List<MUNSActualCostConfigItem> list = Query
				.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID,
						MUNSActualCostItem.Table_Name, "UNS_ActualCostReconcile_ID=? AND IsActive=?",
						get_TrxName())
				.setParameters(new Object[] { getUNS_ActualCostReconcile_ID(), "Y" })
				.setOrderBy(orderByClause).list();

		m_actualItems = new MUNSActualCostItem[list.size()];
		list.toArray(m_actualItems);
		return m_actualItems;
	}
	
	/**
	 * Load this Actual Cost items.
	 * @return
	 */
	public MUNSActualCostItem[] getActualItems()
	{
		return getActualItems(false);
	}

	/**
	 * Get Items of this Actual Cost.
	 * @param requery
	 * @return
	 */
	public X_UNS_ActualCostItemLine[] getActualLines(boolean requery, int AD_Org_ID) 
	{
		if (m_actualLinesTable.isEmpty() && !requery) {
			//set_TrxName(m_actualLines, get_TrxName());
			X_UNS_ActualCostItemLine[] actualLines = m_actualLinesTable.get(AD_Org_ID);
			if (actualLines != null)
				return actualLines;
		}

		List<MUNSActualCostConfigItem> list = Query
				.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID,
						X_UNS_ActualCostItemLine.Table_Name, "UNS_ActualCostReconcile_ID=? AND AD_Org_ID=? AND IsActive=?",
						get_TrxName())
				.setParameters(new Object[] { getUNS_ActualCostReconcile_ID(), AD_Org_ID, "Y" })
				.setOrderBy(X_UNS_ActualCostItemLine.COLUMNNAME_UNS_ActualCostItemLine_ID).list();

		X_UNS_ActualCostItemLine[] actualLines = new X_UNS_ActualCostItemLine[list.size()];
		list.toArray(actualLines);
		m_actualLinesTable.put(AD_Org_ID, actualLines);
		
		return actualLines;
	}
	
	/**
	 * Get Item Lines of this Actual Cost.
	 * @param requery
	 * @return
	 */
	public X_UNS_ActualCostItemLine[] getActualLines(boolean requery) 
	{
		if (m_actualLines != null && !requery) {
			set_TrxName(m_actualLines, get_TrxName());
			return m_actualLines;
		}

		List<MUNSActualCostConfigItem> list = Query
				.get(getCtx(), UNSFinanceModelFactory.EXTENSION_ID,
						X_UNS_ActualCostItemLine.Table_Name, "UNS_ActualCostReconcile_ID=? AND IsActive=? ",
						get_TrxName())
				.setParameters(new Object[] { getUNS_ActualCostReconcile_ID(), "Y" })
				.setOrderBy(X_UNS_ActualCostItemLine.COLUMNNAME_UNS_ActualCostItemLine_ID).list();

		m_actualLines = new X_UNS_ActualCostItemLine[list.size()];
		list.toArray(m_actualLines);
		return m_actualLines;
	}
	
	/**
	 * Load this Actual Cost item Lines.
	 * @return
	 */
	public X_UNS_ActualCostItemLine[] getActualLines()
	{
		return getActualLines(false);
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocOptions#customizeValidActions(java.lang.String, java.lang.Object, java.lang.String, java.lang.String, int, java.lang.String[], java.lang.String[], int)
	 */
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) 
	{
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
		StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(":")
		//	.append(Msg.translate(getCtx(),"TotalLines")).append("=").append(getTotalLines())
			.append(" (#").append(getActualItems(false).length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		StringBuilder msgreturn = new StringBuilder().append(dt.getName()).append(" ").append(getDocumentNo());
		return msgreturn.toString();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		try
		{
			StringBuilder msgfile = new StringBuilder().append(get_TableName()).append(get_ID()).append("_");
			File temp = File.createTempFile(msgfile.toString(), ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
		ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.MANUFACTURING_ORDER, get_ID(), get_TrxName());
		if (re == null)
			return null;
		MPrintFormat format = re.getPrintFormat();
		// We have a Jasper Print Format
		// ==============================
		if(format.getJasperProcess_ID() > 0)	
		{
			ProcessInfo pi = new ProcessInfo ("", format.getJasperProcess_ID());
			pi.setRecord_ID ( get_ID() );
			pi.setIsBatch(true);
			
			ServerProcessCtl.process(pi, null);
			
			return pi.getPDFReport();
		}
		// Standard Print Format (Non-Jasper)
		// ==================================
		return re.getPDF(file);
	}	//	createPDF

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
		return getCreatedBy();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		return Env.getContextAsInt(getCtx(),"$C_Currency_ID");
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		return Env.ZERO;
	}

	/**
	 * 
	 * @param reconciliationHeader
	 * @return
	 */
	public String distributeAllActualCost()
	{
		if (getActualItems() == null || getActualItems().length == 0)
			throw new AdempiereUserError("You have not load the reconciliation configuration yet.");
		
		// First, we need to reload all the Actual Cost Item and the Lines records by which grouped by department
		// and also try to validate it.
		for (MUNSActualCostItem actualItem : getActualItems())
		{
			if (!validateItemConfiguration (getActualItems (false, actualItem.getAD_Org_ID())))
				throw new AdempiereUserError(
						"Joint Cost flag definition inconsistent for Dept. of " + 
						new X_AD_Org(getCtx(), actualItem.getAD_Org_ID(), get_TrxName()).getName());
			
			if (null == getActualLines(false, actualItem.getAD_Org_ID()))
				throw new AdempiereUserError(
						"You have not defined any distributable costs (lines) for Dept. of " + 
						new X_AD_Org(getCtx(), actualItem.getAD_Org_ID(), get_TrxName()).getName());
			
			if (!MUNSActualCostConfigItem.PRODUCTTYPE_FinishedGoods.equals(actualItem.getProductType()))
				continue;
			
			BigDecimal productPrice = getPriceOf(actualItem.getM_Product_ID(), actualItem.getTotalQty(), true);

			if (productPrice == null || productPrice.signum()<=0)
				throw new AdempiereException("Failed: Cannot distribute cost to FG Produc-Type with undefined price.");

			BigDecimal productRevenue = productPrice.multiply(actualItem.getTotalQty());
			
			m_MapOfActualItemRevenue.put(actualItem.get_ID(), productRevenue);			
		}
		
		// Now we can process the distributable cost from the Hashtable of actual items and actual lines records.
		while (!allCostsAreAdjusted())
		{
			for (MUNSActualCostItem[] items : m_actualItemsTable.values())
				distributeActualCostOf(items);
		}
		String[] columnsToScale = new String[]{
				MUNSActualCostItem.COLUMNNAME_AdjustedCostPrice,
				MUNSActualCostItem.COLUMNNAME_DistributableCostAllocation,
				MUNSActualCostItem.COLUMNNAME_TotalActualCost,
				MUNSActualCostItem.COLUMNNAME_TotalCurrentCost,
				MUNSActualCostItem.COLUMNNAME_TotalDistributableCost,
				MUNSActualCostItem.COLUMNNAME_TotalQty,
				MUNSActualCostItem.COLUMNNAME_TotalUsedQty,
				MUNSActualCostItem.COLUMNNAME_CurrentUsedQty,
				MUNSActualCostItem.COLUMNNAME_CurrentCostPrice,
				MUNSActualCostItem.COLUMNNAME_QtyOnHand
		};
		for (MUNSActualCostItem[] items : m_actualItemsTable.values())
			for (MUNSActualCostItem actualItem : items)
				GeneralCustomization.setScaleOf(actualItem, columnsToScale, 2, BigDecimal.ROUND_HALF_UP, true);
		
		columnsToScale = new String[]{
				X_UNS_ActualCostItemLine.COLUMNNAME_ActualCost,
				X_UNS_ActualCostItemLine.COLUMNNAME_AdjustedCostPrice,
				X_UNS_ActualCostItemLine.COLUMNNAME_AllocatedCost,
				X_UNS_ActualCostItemLine.COLUMNNAME_CurrentCost,
				X_UNS_ActualCostItemLine.COLUMNNAME_CurrentUsedQty,
				X_UNS_ActualCostItemLine.COLUMNNAME_CurrentUsedCost,
				X_UNS_ActualCostItemLine.COLUMNNAME_TotalUsedQty
		};
		for (X_UNS_ActualCostItemLine actualLine : getActualLines())
		{
			GeneralCustomization.setScaleOf(actualLine, columnsToScale, 2, BigDecimal.ROUND_HALF_UP, true);
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean allCostsAreAdjusted()
	{
		boolean allAreAdjusted = true;
		
		for (X_UNS_ActualCostItem[] items : m_actualItemsTable.values())
		{
			for (X_UNS_ActualCostItem item : items)
			{
				for (X_UNS_ActualCostItemLine[] lines : m_actualLinesTable.values())
				{
					for (X_UNS_ActualCostItemLine line : lines)
					{
						if (line.getUNS_ActualCostItem_ID() == 0)
							continue;
						if (line.getUNS_ActualCostItem_ID() != item.get_ID())
							continue;
						if (line.getAdjustedCostPrice().compareTo(item.getAdjustedCostPrice()) != 0)
							return false;
					}
				}
			}
		}
		
		return allAreAdjusted;
	}

	
	/**
	 * 
	 * @param items
	 * @return
	 */
	private String distributeActualCostOf (MUNSActualCostItem[] items)
	{
		MUNSActualCostItem firstItem = items[0];
		
		Hashtable<Integer, BigDecimal> MapOfSeparableCostGroup = new Hashtable<Integer, BigDecimal>();
		Hashtable<Integer, BigDecimal> MapOfDistributableCostGroup = new Hashtable<Integer, BigDecimal>();
		Hashtable<Integer, BigDecimal> MapOfTotalGroupGrossProfit = new Hashtable<Integer, BigDecimal>();
		initCostGroupMap(items, MapOfSeparableCostGroup, MapOfDistributableCostGroup, MapOfTotalGroupGrossProfit);
		
		// Get all the joint group's item actual cost. 
		if (firstItem.isJointCost())
		{
			for (MUNSActualCostItem item : items)
			{
				BigDecimal actualCost = item.getTotalActualCost();
				if (actualCost.compareTo(BigDecimal.ZERO) == 0)
					actualCost = item.getTotalCurrentCost().add(item.getCostVariance());

				if (MUNSActualCostConfigItem.PRODUCTTYPE_FinishedGoods.equals(item.getProductType()))
				{
					BigDecimal groupGrossProfit = MapOfTotalGroupGrossProfit.get(item.getUNS_JointCostGroup_ID());
					
					BigDecimal itemRevenue = m_MapOfActualItemRevenue.get(item.get_ID());
					
					if (groupGrossProfit == null)
						groupGrossProfit = itemRevenue.subtract(actualCost);
					else
						groupGrossProfit = groupGrossProfit.add(itemRevenue.subtract(actualCost));
					
					MapOfTotalGroupGrossProfit.put(item.getUNS_JointCostGroup_ID(), groupGrossProfit);
				}
				else 
				{
					BigDecimal separableCost = MapOfSeparableCostGroup.get(item.getUNS_JointCostGroup_ID());
	
					if (separableCost == null)
						MapOfSeparableCostGroup.put(item.getUNS_JointCostGroup_ID(), actualCost);
					else 
						MapOfSeparableCostGroup.put(item.getUNS_JointCostGroup_ID(), separableCost.add(actualCost));
				}
				
				//totalJointCost = totalJointCost.add (item.getTotalActualCost());
			}
		}

		// Update all values of distributable costs
		BigDecimal totalDistributableCost = BigDecimal.ZERO;
		for (X_UNS_ActualCostItemLine dLine : m_actualLinesTable.get(firstItem.getAD_Org_ID()))
		{
			//String lineName = dLine.getName();
			// if the ActualItem is for man-per-hour adjustment then it should only allocating the allocated proporsion.
			BigDecimal allocatedProportion = BigDecimal.ONE;
			if (X_UNS_ActualCostItem.PRODUCTTYPE_ManPerHour.equals(firstItem.getProductType()) 
				&& firstItem.getTotalQty().signum() > 0) {
				allocatedProportion = 
						//firstItem.getTotalUsedQty().divide(firstItem.getTotalQty(), 20, BigDecimal.ROUND_HALF_UP);
						firstItem.getCurrentUsedQty().divide(firstItem.getTotalQty(), 20, BigDecimal.ROUND_HALF_UP);
			}
			// Jika bukan utk joint-cost-group dan mrp NoSpecificConsumptionType, maka hanya mjd totalDistributableCost.
			if (X_UNS_ActualCostItemLine.PRODUCTTYPE_NoSpecificConsumptionType.equals(dLine.getProductType())
				&& dLine.getUNS_ActualCostItem_ID() == 0)
				//&& dLine.getUNS_JointCostGroup_ID() == 0)
			{
				BigDecimal toBeAllocated = dLine.getActualCost().multiply(allocatedProportion);
				dLine.setAllocatedCost(toBeAllocated);
				
				if (dLine.getUNS_JointCostGroup_ID() > 0)
				{   // The item's line cost is for a joint cost group,
					// it must be allocated to the proper joint group only.
					BigDecimal distributableCost = MapOfDistributableCostGroup.get(dLine.getUNS_JointCostGroup_ID());
					
					if (distributableCost == null)
						MapOfDistributableCostGroup.put(dLine.getUNS_JointCostGroup_ID(), toBeAllocated);
					else 
						MapOfDistributableCostGroup.put(
								dLine.getUNS_JointCostGroup_ID(), distributableCost.add(toBeAllocated));
				}
				else { 
					totalDistributableCost = totalDistributableCost.add(toBeAllocated);
				}
				if (!dLine.save())
					throw new AdempiereException("Failed when updating allocated cost of " + dLine.getName());
				continue;
			}

			// We have to get the actual cost from the proper dept.'s actual cost item.
			I_UNS_ActualCostItem actualItem = dLine.getUNS_ActualCostItem();
			
			/*
			 * This is to handle the unsync data from two different Hashtable, so it can use the updated 
			 * actualItem.
			 */
			I_UNS_ActualCostItem[] actualItems = m_actualItemsTable.get(actualItem.getAD_Org_ID());
			for (I_UNS_ActualCostItem item : actualItems) {
				if (item.getUNS_ActualCostItem_ID() == actualItem.getUNS_ActualCostItem_ID()) {
					actualItem = item;
					break;
				}
			}

			BigDecimal toBeAllocated = actualItem.getDistributableCostAllocation();
			dLine.setAdjustedCostPrice(actualItem.getAdjustedCostPrice());
			if (X_UNS_JointCostGroup.POSTINGTYPE_Statistical.equals(dLine.getUNS_JointCostGroup().getPostingType()))
			{
				dLine.setActualCost(actualItem.getTotalActualCost());
				toBeAllocated = actualItem.getTotalActualCost();
			}
			/*
			else if (MUNSActualCostItem.PRODUCTTYPE_WorkInProcess.equals(actualItem.getProductType()))
			{
				dLine.setActualCost(toBeAllocated);
				//toBeAllocated = 
				//		toBeAllocated.multiply(dLine.getTotalUsedQty())
				//		.divide(actualItem.getTotalUsedQty(), 20, BigDecimal.ROUND_HALF_UP);
			}
			else if (MUNSActualCostItem.PRODUCTTYPE_Utilities.equals(actualItem.getProductType())
					|| MUNSActualCostItem.PRODUCTTYPE_ManPerHour.equals(actualItem.getProductType()))
			{
				//dLine.setActualCost(dLine.getTotalUsedQty().multiply(actualItem.getAdjustedCostPrice()));
				dLine.setActualCost(dLine.getCurrentUsedQty().multiply(actualItem.getAdjustedCostPrice()));
				// Alokasikan semua biaya utilities/man-per-hour, mengingat saat ini semua itu tidak termasuk
				// kedalam biaya produksi.
				toBeAllocated = dLine.getActualCost().subtract(dLine.getCurrentCost());
			}
			*/
			else if (MUNSActualCostItem.PRODUCTTYPE_RawMaterial.equals(actualItem.getProductType())
					|| MUNSActualCostItem.PRODUCTTYPE_FinishedGoods.equals(actualItem.getProductType())
					|| MUNSActualCostItem.PRODUCTTYPE_WorkInProcess.equals(actualItem.getProductType())
					|| MUNSActualCostItem.PRODUCTTYPE_Utilities.equals(actualItem.getProductType())
					|| MUNSActualCostItem.PRODUCTTYPE_ManPerHour.equals(actualItem.getProductType()))
			{
				dLine.setActualCost(dLine.getCurrentUsedQty().multiply(actualItem.getAdjustedCostPrice()));
				toBeAllocated = dLine.getActualCost().subtract(dLine.getCurrentUsedCost());
			}
			// We leave the distributable cost allocation as it is, but multiply it with allocatedProportion for
			// ManPerHour adjustment item.
			//dLine.setAllocatedCost(toBeAllocated.multiply(allocatedProportion));
			dLine.setAllocatedCost(toBeAllocated);
			
			if (dLine.getUNS_JointCostGroup_ID() > 0)
			{   // The item's line cost is for a joint cost group,
				// it must be allocated to the proper joint group only.
				BigDecimal distributableCost = MapOfDistributableCostGroup.get(dLine.getUNS_JointCostGroup_ID());
				
				if (distributableCost == null)
					MapOfDistributableCostGroup.put(dLine.getUNS_JointCostGroup_ID(), toBeAllocated);
				else 
					MapOfDistributableCostGroup.put(
							dLine.getUNS_JointCostGroup_ID(), distributableCost.add(toBeAllocated));
			}
			else { 
				totalDistributableCost = totalDistributableCost.add(toBeAllocated);
			}
			if (!dLine.save())
				throw new AdempiereException("Failed when updating Adjusted Cost Price of Item Line of " + dLine.getName());
		}

		// Now we already have updated actual cost of detail cost lines from other dept., 
		// Now time to update the actual of the Item Cost.
		
		if (firstItem.isJointCost())
		{
			/* 
			 * Ada kemungkinan s'tu departemen memiliki > 1 cost-group.
			 * So, pertama2 kita harus alokasikan distributable-cost per group ke dalam group masing2 sesuai 
			 * dengan proporsi setiap item dibandingkan total cost dlm group-nya. 
			 * Proses dibawah ini pada intinya adalah untuk meng-update nilai totalActualCost dari suatu item dg
			 * biaya yang perlu dialokasikan pd item tsb yang terkait dg group dari item tsb.
			 */
			for (MUNSActualCostItem item : items)
			{
				BigDecimal totalCostGroup = MapOfDistributableCostGroup.get(item.getUNS_JointCostGroup_ID());

				if (totalCostGroup == null)
					continue;

				BigDecimal actualCost = item.getTotalActualCost();
				if (actualCost.compareTo(BigDecimal.ZERO) == 0) {
					actualCost = item.getTotalCurrentCost().add(item.getCostVariance());
					item.setTotalActualCost(actualCost);
				}

				BigDecimal joinProporsion = Env.ZERO;
				if (MUNSActualCostConfigItem.PRODUCTTYPE_FinishedGoods.equals(item.getProductType()))
				{
					BigDecimal totalGroupGrossProfit = MapOfTotalGroupGrossProfit.get(item.getUNS_JointCostGroup_ID());
					
					BigDecimal itemGrossProfit = m_MapOfActualItemRevenue.get(item.get_ID()).subtract(actualCost);
					
					joinProporsion = (totalGroupGrossProfit == null || totalGroupGrossProfit.signum()==0)? Env.ZERO 
									: itemGrossProfit.divide(totalGroupGrossProfit, 20, BigDecimal.ROUND_HALF_UP);
				}
				else 
				{
					BigDecimal totalSeparableCostGroup = MapOfSeparableCostGroup.get(item.getUNS_JointCostGroup_ID());
					joinProporsion = (totalSeparableCostGroup == null || totalSeparableCostGroup.signum()==0)?
							Env.ZERO : actualCost.divide(totalSeparableCostGroup, 20, BigDecimal.ROUND_HALF_UP);
				}
				
				BigDecimal costGroupAllocated = totalCostGroup.multiply(joinProporsion);
				if (X_UNS_JointCostGroup.POSTINGTYPE_Statistical.equals(item.getUNS_JointCostGroup().getPostingType()))
					costGroupAllocated = costGroupAllocated.subtract(item.getTotalCurrentCost());

				item.setDistributableCostAllocation(costGroupAllocated);
				item.setTotalDistributableCost(totalCostGroup);
				// Tambahkan totalCurrentCost dengan proporsional cost of current item.
				item.setTotalActualCost(item.getTotalCurrentCost().add(costGroupAllocated));
				BigDecimal totalActualCost = item.getTotalQty().signum() == 0 ? Env.ZERO :
						item.getTotalActualCost().divide(item.getTotalQty(), 2, BigDecimal.ROUND_HALF_UP);
				item.setAdjustedCostPrice(totalActualCost);
				
				if (!item.save())
					throw new AdempiereException("Failed when updating the actual cost item of " + item.getName());
			}

			/*
			 * Sekarang kita dapat mendistribusikan seluruh biaya (distributable) cost yang tidak terkait dengan
			 * group-cost (namun terkait dengan departemen tsb) atau dengan kata lain proporsi pembagiannya adalah
			 * itemActualCost/totalAllGroupCosts.
			 */
			if (totalDistributableCost.compareTo(BigDecimal.ZERO) != 0)
			{
				// Setelah mengalokasikan biaya group, maka dapatkan total cost dari seluruh item dari dept. tsb.
				BigDecimal totalAllGroupCostsOrGrossProfit = BigDecimal.ZERO;
				for (MUNSActualCostItem item : items) {
					
					// Statistical cost group is applied only to its group.
					if (X_UNS_JointCostGroup.POSTINGTYPE_Statistical.equals(item.getUNS_JointCostGroup().getPostingType()))
						continue;
					
					BigDecimal actualCost = item.getTotalActualCost();
					
					if (actualCost.compareTo(BigDecimal.ZERO) == 0) {
						actualCost = item.getTotalCurrentCost().add(item.getCostVariance());
						item.setTotalActualCost(actualCost);
					}
					
					if (MUNSActualCostConfigItem.PRODUCTTYPE_FinishedGoods.equals(item.getProductType()))
					{
						BigDecimal itemRevenue = m_MapOfActualItemRevenue.get(item.get_ID());
						totalAllGroupCostsOrGrossProfit = totalAllGroupCostsOrGrossProfit.add(itemRevenue.subtract(actualCost));
					}
					else {
						totalAllGroupCostsOrGrossProfit = totalAllGroupCostsOrGrossProfit.add(actualCost);
					}
				}
				
				for (MUNSActualCostItem item : items)
				{
					// Statistical cost group is applied only to its group.
					if (X_UNS_JointCostGroup.POSTINGTYPE_Statistical.equals(item.getUNS_JointCostGroup().getPostingType()))
						continue;

					BigDecimal actualCost = item.getTotalActualCost();
	
					BigDecimal joinProporsion = Env.ZERO;
					if (MUNSActualCostConfigItem.PRODUCTTYPE_FinishedGoods.equals(item.getProductType())
						&& (totalAllGroupCostsOrGrossProfit != null && totalAllGroupCostsOrGrossProfit.signum() != 0))
					{
						BigDecimal itemRevenue = m_MapOfActualItemRevenue.get(item.get_ID());
						
						BigDecimal itemGrossProfit = itemRevenue.subtract(actualCost);
						
						joinProporsion = itemGrossProfit.divide(totalAllGroupCostsOrGrossProfit, 20, BigDecimal.ROUND_HALF_UP);
					}
					else if (totalAllGroupCostsOrGrossProfit != null && totalAllGroupCostsOrGrossProfit.signum() != 0)
					{ 
						joinProporsion = actualCost.divide(totalAllGroupCostsOrGrossProfit, 20, BigDecimal.ROUND_HALF_UP);
					}
					BigDecimal distributableCostAllocated = totalDistributableCost.multiply(joinProporsion);
	
					BigDecimal totalCostGroup = 
							MapOfDistributableCostGroup.get(item.getUNS_JointCostGroup_ID());
	
					item.setTotalDistributableCost(totalDistributableCost.add(totalCostGroup));
					item.setDistributableCostAllocation(
							item.getDistributableCostAllocation().add(distributableCostAllocated));
					
					// Tambahkan totalCurrentCost dengan proporsional cost of current item.
					item.setTotalActualCost(item.getTotalCurrentCost().add(item.getDistributableCostAllocation()));

					BigDecimal adjustedCostPrice = (item.getTotalQty().signum()==0)? Env.ZERO 
							: item.getTotalActualCost().divide(item.getTotalQty(), 2, BigDecimal.ROUND_HALF_UP);
					item.setAdjustedCostPrice(adjustedCostPrice);
					
					if (!item.save())
						throw new AdempiereException("Failed when updating the actual cost item of " + item.getName());
				}
			}
		}
		else if (MUNSActualCostItem.PRODUCTTYPE_ManPerHour.equals(firstItem.getProductType()))
		{
			// untuk Man Per Hour totalDistributableCost adalah total seluruh biaya pada departemen, kemudian 
			// distributableCostAllocation = totalDistributableCost * (totalUsedQty/totalQty), sedangkan
			// AdjustedCostPrice = distributableCostAllocation / totalUsedQty.
			firstItem.setTotalDistributableCost (totalDistributableCost);

			// Ambil proporsi yang dialokasikan sesuai dg totalUsedQty/totalQty.
			//BigDecimal totalUsedQtyProporsion = firstItem.getTotalQty().signum() <=0 ? Env.ZERO
			//		: firstItem.getTotalUsedQty().divide(firstItem.getTotalQty(), 20, BigDecimal.ROUND_HALF_UP);
			
			//BigDecimal distributableCostAllocation = totalDistributableCost.multiply(totalUsedQtyProporsion);
			
			BigDecimal adjustedCostPrice = (firstItem.getCurrentUsedQty().signum()==0)? Env.ZERO 
					: totalDistributableCost.divide(firstItem.getCurrentUsedQty(), 2, BigDecimal.ROUND_HALF_UP);
			
			firstItem.setDistributableCostAllocation(firstItem.getTotalDistributableCost());
			firstItem.setTotalActualCost(totalDistributableCost);
			firstItem.setAdjustedCostPrice(adjustedCostPrice);

			if (!firstItem.save())
				throw new AdempiereException("Failed when updating the actual cost item of " + firstItem.getName());
		}
		// This condition to handle if RMP, WIP, FG, & Utilities are set not as joint cost group products.
		else if (MUNSActualCostItem.PRODUCTTYPE_RawMaterial.equals(firstItem.getProductType())
				|| MUNSActualCostItem.PRODUCTTYPE_WorkInProcess.equals(firstItem.getProductType())
				|| MUNSActualCostItem.PRODUCTTYPE_Utilities.equals(firstItem.getProductType())
				|| MUNSActualCostItem.PRODUCTTYPE_FinishedGoods.equals(firstItem.getProductType()))
		{
			// Untuk WIP/Utilities dan RM totalDistributableCost adalah total seluruh biaya diluar biaya produksi atau 
			// biaya pembelian. Dan jika bukan joint cost group:
			// distributableCostAllocation = totalDistributableCost, sedangkan
			// AdjustedCostPrice = (distributableCostAllocation + totalCurrentCost) / totalQty.
			
			firstItem.setTotalDistributableCost (totalDistributableCost);
			BigDecimal totalActualCost = totalDistributableCost.add(firstItem.getTotalCurrentCost());

			BigDecimal adjustedCostPrice = firstItem.getTotalQty().signum() ==0 ? Env.ZERO 
						: totalActualCost.divide(firstItem.getTotalQty(), 2, BigDecimal.ROUND_HALF_UP);

			firstItem.setDistributableCostAllocation(totalDistributableCost);
			firstItem.setTotalActualCost(totalActualCost);
			firstItem.setAdjustedCostPrice(adjustedCostPrice);

			if (!firstItem.save())
				throw new AdempiereException("Failed when updating the actual cost item of " + firstItem.getName());
		}
		
		
		return null;
	}
	
	/**
	 * It is valid if only all items are consistently defined as not-joint-cost or joint-cost.
	 * 
	 * @param items
	 * @return
	 */
	private boolean validateItemConfiguration (MUNSActualCostItem[] items)
	{
		boolean isJointCost = items[0].isJointCost();
		
		// If not joint cost and have multi items throw exception.
		if (isJointCost)
			for (MUNSActualCostItem item : items)
				if (!item.isJointCost())
					return false;

		if (!isJointCost && items.length > 1)
			return false;

		return true;
	}
	
	/**
	 * Get the priceStd of a product for the given quantity.
	 * 
	 * @param M_Product_ID
	 * @param Qty
	 * @param isSOTrx
	 * @return
	 */
	protected BigDecimal getPriceOf(int M_Product_ID, BigDecimal Qty, boolean isSOTrx)
	{
		int C_BPartner_ID = 0;
		int C_BPartner_Location_ID = 0;
		MProductPricing pp = new MProductPricing(M_Product_ID, C_BPartner_ID, Qty, isSOTrx, C_BPartner_Location_ID);
		//
		pp.setM_PriceList_ID(getM_PriceList_ID());
		pp.setPriceDate(getDateAcct());
		BigDecimal priceStd = pp.getPriceStd();
		return priceStd;
	}
	
	/**
	 * Init all cost-group with zero value for all of item's cost group.
	 * @param items
	 * @param separableCostGroup
	 * @param distributableCostGroup
	 * @param totalGroupGrossProfit
	 */
	private void initCostGroupMap (MUNSActualCostItem[] items,
								   Hashtable<Integer, BigDecimal> separableCostGroup,
								   Hashtable<Integer, BigDecimal> distributableCostGroup,
								   Hashtable<Integer, BigDecimal> totalGroupGrossProfit) 
	{
		for (MUNSActualCostItem item : items)
		{
			separableCostGroup.put(item.getUNS_JointCostGroup_ID(), Env.ZERO);
			distributableCostGroup.put(item.getUNS_JointCostGroup_ID(), Env.ZERO);
			if (MUNSActualCostItem.PRODUCTTYPE_FinishedGoods.equals(item.getProductType()))
				totalGroupGrossProfit.put(item.getUNS_JointCostGroup_ID(), Env.ZERO);
		}
	}
	
}
