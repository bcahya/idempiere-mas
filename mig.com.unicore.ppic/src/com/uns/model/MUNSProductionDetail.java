/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.compiere.model.MActivity;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MInOutLineMA;
import org.compiere.model.MLocator;
import org.compiere.model.MProduct;
import org.compiere.model.MProductBOM;
import org.compiere.model.MProductCategory;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTransaction;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.ui.ISortTabRecord;
import com.uns.util.MessageBox;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;


/**
 * @author YAKA
 * 
 */
public class MUNSProductionDetail extends X_UNS_Production_Detail implements ISortTabRecord
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8050850755063641679L;

	private MUNSProduction parent;

	public boolean m_isFormModification = false;

	public boolean m_isProcess = false;
	
	private Hashtable<Integer, MUNSProductionDetail> m_mapPrimaryOutputs = null;
	private Hashtable<Integer, MUNSProductionDetail> m_mapNonPrimaryOutputs = null;
	private Hashtable<Integer, MUNSProductionDetail> m_mapInputs = null;
	
	private MProductBOM m_BOM = null;
	
	/**
	 * Get production details for given array of MAttributeSetInstance_ID.
	 * @param ctx
	 * @param asiList
	 * @param trxName
	 * @return an array of production detail of which the asiList is attached to.
	 * @throws SQLException
	 */
	public static MUNSProductionDetail[] getDetailsOf(Properties ctx, MInOutLineMA[] iomaList, String trxName)
		throws SQLException
	{
		ArrayList<MUNSProductionDetail> detailList = new ArrayList<MUNSProductionDetail>();
		MUNSProductionDetail[] details = null;
		
		for (MInOutLineMA ioma : iomaList)
		{
			String sql = "SELECT pd.* FROM UNS_Production_Detail pd " +
					" INNER JOIN UNS_Production_DetailMA pdma ON pd.UNS_Production_Detail_ID=pdma.UNS_Production_Detail_ID " +
					" WHERE pdma.M_AttributeSetInstance_ID=" + ioma.getM_AttributeSetInstance_ID();
			
			PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				detailList.add(new MUNSProductionDetail(ctx, rs, trxName));
		}
		if (detailList.size() > 0)
		{
			details = new MUNSProductionDetail[detailList.size()];
			detailList.toArray(details);
		}
		return details;
	}
	
	/**
	 * Get production details for given of MAttributeSetInstance_ID.
	 * @param ctx
	 * @param asi
	 * @param trxName
	 * @return an array of production detail of which the asi is attached to.
	 * @throws SQLException
	 */
	public static MUNSProductionDetail[] getDetail(Properties ctx, int M_AttributeSetInstance_ID, String trxName)
		throws SQLException
	{
		ArrayList<MUNSProductionDetail> detailList = new ArrayList<MUNSProductionDetail>();
		MUNSProductionDetail[] details = null;
		
		String sql = "SELECT pd.* FROM UNS_Production_Detail pd " +
					" INNER JOIN UNS_Production_DetailMA pdma ON pd.UNS_Production_Detail_ID=pdma.UNS_Production_Detail_ID " +
					" WHERE pdma.M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID;
			
		PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
				detailList.add(new MUNSProductionDetail(ctx, rs, trxName));
		
		if (detailList.size() > 0)
		{
			details = new MUNSProductionDetail[detailList.size()];
			detailList.toArray(details);
		}
		return details;
	}

	/**
	 * @param ctx
	 * @param UNS_Production_Detail_ID
	 * @param trxName
	 */
	public MUNSProductionDetail(Properties ctx, int UNS_Production_Detail_ID,
			String trxName) {
		super(ctx, UNS_Production_Detail_ID, trxName);
		/*
		if (UNS_Production_Detail_ID == 0) {
			setLine(0); // @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM
						// UNS_Production_Detail WHERE
						// UNS_Production_ID=@UNS_Production_ID@
			setM_AttributeSetInstance_ID(0);
			setM_Locator_ID(0); // @M_Locator_ID@
			setM_Product_ID(0);
			setUNS_Production_Detail_ID(0);
			setUNS_Production_ID(0);
			setMovementQty(Env.ZERO);
			setProcessed(false);
		}
		*/
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSProductionDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * create Parent Constructor
	 * 
	 * @param MUNSProduction
	 */
	public MUNSProductionDetail(MUNSProduction header) {
		super(header.getCtx(), 0, header.get_TrxName());
		setUNS_Production_ID(header.get_ID());
		setAD_Client_ID(header.getAD_Client_ID());
		setAD_Org_ID(header.getAD_Org_ID());
		parent = header;
	}
	
	/**
	 * 
	 * @return
	 */
	public Hashtable<Integer, MUNSProductionDetail> getAllPrimaryOutputMap()
	{
		if (m_mapPrimaryOutputs != null)
			return m_mapPrimaryOutputs;
		
		m_mapPrimaryOutputs = new Hashtable<>();
		
		MUNSProductionDetail[] allPrims = getOutputs(true);
		
		for (MUNSProductionDetail primOut : allPrims) 
		{
			if (!this.isEndProduct())
			{
				//
			}
			
			m_mapPrimaryOutputs.put(primOut.get_ID(), primOut);
		}
		
		return m_mapPrimaryOutputs;
	} // getAllPrimaryOutputMap
	
	/**
	 * 
	 * @return
	 */
	public Hashtable<Integer, MUNSProductionDetail> getAllNonPrimaryOutputMap()
	{
		if (m_mapNonPrimaryOutputs != null)
			return m_mapNonPrimaryOutputs;
		
		m_mapNonPrimaryOutputs = new Hashtable<>();
		
		MUNSProductionDetail[] allPrims = getOutputs(false);
		
		for (MUNSProductionDetail primOut : allPrims) {
			m_mapNonPrimaryOutputs.put(primOut.get_ID(), primOut);
		}
		
		return m_mapNonPrimaryOutputs;
	} // getAllNonPrimaryOutputMap
	
	
	/**
	 * 
	 * @return
	 */
	public Hashtable<Integer, MUNSProductionDetail> getAllInputMap()
	{
		if (m_mapInputs != null || !this.isEndProduct())
			return m_mapInputs;
		
		m_mapInputs = new Hashtable<>();
		
		for (MUNSProductionDetail input : getAllPossibleInputDetails()) {
			m_mapInputs.put(input.get_ID(), input);
		}
		
		return m_mapInputs;
	} // getAllInputMap
	
	
	/**
	 * 
	 * @param date
	 * @param sourceList 
	 * @return "" for success, error string if failed
	 */
	public String createTransactions(Timestamp date, boolean mustBeStocked, MUNSSrcProductDetail[] sourceList) 
	{
		if (parent == null)
			parent = (MUNSProduction) getUNS_Production();
//		if (true)
//			throw new AdempiereException();
		// delete existing ASI records
		//int deleted = deleteMA();
		//log.log(Level.FINE, "Deleted " + deleted + " attribute records ");
		MProduct prod = new MProduct(getCtx(), getM_Product_ID(), get_TrxName());
		log.log(Level.FINE, "Loaded Product " + prod.toString());

		if (prod.getProductType().compareTo(MProduct.PRODUCTTYPE_Item) != 0) {
			// no need to do any movements
			log.log(Level.FINE, "Production Line " + getLine()
					+ " does not require stock movement");
			return "";
		}
		StringBuilder errorString = new StringBuilder();

		//if getMovementQty is zero, not create MA 
//		if (getMovementQty().intValue()==0)
		//karna banyak yang dibawah 1 dan lebih dari 0 jadi tidak potong stock.
		if (getMovementQty().compareTo(Env.ZERO)==0)
			return errorString.toString();

		String trxType = "";
		if (isEndProduct())
			trxType = 
			!isReversalLine()? MTransaction.MOVEMENTTYPE_ProductionPlus : MTransaction.MOVEMENTTYPE_Production_;
		else 
			trxType = 
			!isReversalLine()? MTransaction.MOVEMENTTYPE_Production_ : MTransaction.MOVEMENTTYPE_ProductionPlus;
		
		// create transactions and update stock used in production (raw material)
		BigDecimal qtyToMove = getMovementQty().negate();
		
		MTransaction mTrx = null;
		
		if (getM_AttributeSetInstance_ID() == 0)
		{
			if (!isEndProduct() && !isReversalLine())
				errorString.append( createMALines(sourceList) );
			
			if (!errorString.toString().trim().isEmpty())
				return errorString.toString();
			
			MUNSProductionDetailMA[] maLines = getMALines();
			
			for (MUNSProductionDetailMA ma : maLines)
			{
				if (!MStorageOnHand.add(getCtx(), 
						getM_Locator().getM_Warehouse_ID(), 
						getM_Locator_ID(), 
						getM_Product_ID(), 
						ma.getM_AttributeSetInstance_ID(), 
						ma.getMovementQty(), 
						ma.getDateMaterialPolicy(), 
						get_TrxName())) 
				{
					log.log(Level.SEVERE, "Could not update storage for " + toString());
					errorString.append("Could not update storage for " + toString() + "\n");
				}
				
				mTrx = new MTransaction(getCtx(), getAD_Org_ID(), trxType,
										  getM_Locator_ID(), getM_Product_ID(), 
										  ma.getM_AttributeSetInstance_ID(), ma.getMovementQty(), 
										  date, get_TrxName());
				mTrx.setUNS_Production_Detail_ID(get_ID());
				
				if (!mTrx.save(get_TrxName())) {
					log.log(Level.SEVERE, "Could not save transaction for " + toString());
					errorString.append("Could not save transaction for " + toString() + "\n");
				} else
					log.log(Level.FINE, "Saved transaction for " + toString());
				
				qtyToMove = qtyToMove.subtract(ma.getMovementQty().negate());
			}
		}
		
		if (mTrx == null)
		{
			if (!MStorageOnHand.add(getCtx(), 
					getM_Locator().getM_Warehouse_ID(), 
					getM_Locator_ID(), 
					getM_Product_ID(), 
					getM_AttributeSetInstance_ID(), 
					getMovementQty(), null, get_TrxName())) 
			{
				log.log(Level.SEVERE, "Could not update storage for " + toString());
				errorString.append("Could not update storage for " + toString() + "\n");
			}
			
			mTrx = new MTransaction(getCtx(), getAD_Org_ID(), trxType,
									  getM_Locator_ID(), getM_Product_ID(), 
									  getM_AttributeSetInstance_ID(), getMovementQty(), 
									  date, get_TrxName());
			mTrx.setUNS_Production_Detail_ID(get_ID());
			
			if (!mTrx.save(get_TrxName())) {
				log.log(Level.SEVERE, "Could not save transaction for " + toString());
				errorString.append("Could not save transaction for " + toString() + "\n");
			} else
				log.log(Level.FINE, "Saved transaction for " + toString());
			
			qtyToMove = qtyToMove.subtract(getMovementQty().negate());
		}

		if (!isEndProduct() && qtyToMove.signum() != 0) 
		{
			MLocator loc = new MLocator(getCtx(), getM_Locator_ID(),
					get_TrxName());
			errorString.append("Insufficient qty on hand of " + prod.toString() + " at " + loc.toString() + "\n");
		}

		return errorString.toString();
	}

	private int deleteMA() {
		String sql = "DELETE FROM UNS_Production_DetailMA WHERE UNS_Production_Detail_ID = "
				+ get_ID();
		int count = DB.executeUpdateEx(sql, get_TrxName());
		return count;
	}

	public String toString() {
		if (getM_Product_ID() == 0)
			return ("No product defined for production Detail " + getLine());
		MProduct product = new MProduct(getCtx(), getM_Product_ID(),
				get_TrxName());
		return ("Production line:" + getLine() + " -- " + getMovementQty()
				+ " of " + product.getValue());
	}

	public static MUNSProductionDetail getSamePDetail(Properties ctx,
			String trxName, MProduct product) {
		String sql = "SELECT UNS_Production_Detail_ID FROM UNS_Production_Detail WHERE M_Product_ID=?";
		int pd_id = DB.getSQLValue(trxName, sql, product.get_ID());
		if (pd_id < 0)
			throw new AdempiereException("Error when search BOM.");

		return new MUNSProductionDetail(ctx, pd_id, trxName);
	}
	
	public MUNSProduction getParent()
	{
		if (parent == null)
			parent = (MUNSProduction) getUNS_Production();
		
		return parent;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		if (isReversalLine())
			return true;
		
		if (parent == null)
			parent = new MUNSProduction(getCtx(), getUNS_Production_ID(),
					get_TrxName());
		
		if (newRecord)// && getUNS_Resource_InOut_ID() == 0)
		{
			if (isEndProduct() && !isPrimary() && super.getPrimaryOutput_ID() == 0)
			{
				String sql = "SELECT UNS_Production_Detail_ID FROM UNS_Production_Detail "
						+ "WHERE UNS_Production_ID=" + parent.get_ID();
				
				if (parent.isMulti()) {
					sql += " AND IsEndProduct='Y' AND IsPrimary='Y'";
				}
				else {
					sql += " AND UNS_Resource_InOut_ID = "
						+ "		(SELECT rio.UNS_OutputLink_ID FROM UNS_Resource_InOut rio "
						+ "		WHERE rio.UNS_Resource_InOut_ID= " + getUNS_Resource_InOut_ID() + ")";
				}
				int primaryOutput_ID = DB.getSQLValueEx(get_TrxName(), sql);
				
				if (primaryOutput_ID <= 0)
					throw new AdempiereException("Cannot create production detail of non-primary output "
							+ "before added the primary output first.");
				
				setPrimaryOutput_ID(primaryOutput_ID);
			}
//			String sql = "SELECT IsPrimary FROM UNS_Production_OutPlan "
//						+ "WHERE M_Product_ID=? AND UNS_Production_ID=?";
//			String outPlan = 
//					DB.getSQLValueStringEx(get_TrxName(), sql, getM_Product_ID(), getUNS_Production_ID());
			
//			if (isPrimary()) {
//				setIsEndProduct(true);
//				
//				if (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Single))
//					outPlan = "Y";
//					
//				setIsPrimary(outPlan.equalsIgnoreCase("Y")? true : false);
//			}
//			else
//				setIsEndProduct(false);
		}
		
//		if (MUNSProduction.OUTPUTTYPE_Multi.equals(parent.getOutputType())  
//				|| MUNSProduction.OUTPUTTYPE_MultiOptional.equals(parent.getOutputType()))
//		{
//			String sql = "SELECT IsPrimary FROM UNS_Production_OutPlan "
//					+ "WHERE M_Product_ID=? AND UNS_Production_ID=?";
//			String outPlan = 
//					DB.getSQLValueStringEx(get_TrxName(), sql, getM_Product_ID(), getUNS_Production_ID());
//			
//			if (outPlan != null) {
//				setIsEndProduct(true);
//				setIsPrimary(outPlan.equalsIgnoreCase("Y")? true : false);
//			}
//			else
//				setIsEndProduct(false);
//			
//			if (!this.isPrimary())
//			{
//				
//			}
//			
//			/** update @MUNSProductionOutPlan *
//			//if (parent.getPrimaryOutput().getM_Product_ID() == getM_Product_ID()){
//			if (parent.getPrimaryOutput().getM_Product_ID() == getM_Product_ID()){
//				setIsEndProduct(true);
//				//parent.getPrimaryOutput().setQtyPlan(getMovementQty());
//				//if(!parent.getPrimaryOutput().save())
//				//	return false;
//			} else
//				for (MUNSProductionOutPlan pop : parent.getOtherOutput()) {
//					if (pop.getM_Product_ID() == getM_Product_ID()) {
//						setIsEndProduct(true);
//						//pop.setQtyPlan(getMovementQty());
//						//if(!pop.save())
//						//	return false;
//						break;
//					}
//					setIsEndProduct(false);
//				}
//			*/
//		}
//		else if (!MUNSProduction.PSTYPE_Reprocess.equals(parent.getPSType()))
//			if (parent.getM_Product_ID()==getM_Product_ID())
//				setIsEndProduct(true);
//			else
//				setIsEndProduct(false);
		
		boolean isSingleAttribute = false;
		if (getUNS_Resource_InOut_ID() > 0)
			isSingleAttribute = getUNS_Resource_InOut().isSingleAttribute();
		
		if(newRecord && isEndProduct() && !isSingleAttribute)
		{
			MAttributeSetInstance asi = MAttributeSetInstance.initAttributeValuesFrom(
					this, COLUMNNAME_M_Product_ID,COLUMNNAME_M_AttributeSetInstance_ID, get_TrxName());
			
			setM_AttributeSetInstance_ID(asi.get_ID());
			asi.setCreated(parent.getMovementDate());
		
			if (!asi.save())
				return false;
		}
		
		if (!newRecord && is_ValueChanged(COLUMNNAME_MovementQty) && !parent.isEndingStockBase())
		{
			BigDecimal newQtyMovement = getMovementQty();
			BigDecimal qtyUsed = newQtyMovement.abs();
			
			newQtyMovement = this.isEndProduct() ? newQtyMovement.abs() : newQtyMovement.abs().negate();
			
			setMovementQty(newQtyMovement);
			setQtyUsed(qtyUsed);
		}

		/** Standart QA on IDempiere (not Used)
		if (isEndProduct() && getM_AttributeSetInstance_ID() != 0) {
			String where = "M_QualityTest_ID IN (SELECT M_QualityTest_ID "
					+ "FROM M_Product_QualityTest WHERE M_Product_ID=?) "
					+ "AND M_QualityTest_ID NOT IN (SELECT M_QualityTest_ID "
					+ "FROM M_QualityTestResult WHERE M_AttributeSetInstance_ID=?)";

			List<MQualityTest> tests = new Query(getCtx(),
					MQualityTest.Table_Name, where, get_TrxName())
					.setOnlyActiveRecords(true)
					.setParameters(getM_Product_ID(),
							getM_AttributeSetInstance_ID()).list();
			// create quality control results
			for (MQualityTest test : tests) {
				test.createResult(getM_AttributeSetInstance_ID());
			}
		}
		*/

		//setQtyUsed(getQtyUsed().subtract(getEndingStock()));
		
		if (!isEndProduct()) {
			if (getQtyUsed().compareTo(Env.ZERO)==0)
				//setQtyUsed(getPlannedQty());
				setQtyUsed(Env.ZERO);
			else
				setQtyUsed(getMovementQty().abs()); // to set with absolutely positive movement quantity.
			
			if (getMovementQty().compareTo(Env.ZERO)!=0){
				setMovementQty(getMovementQty().abs().negate()); // Just to make sure, it's negative movement.
				setQtyUsed(getMovementQty().abs()); //to set with absolutely positive movement quantity.
			}
		}
		
		if (parent.isEndingStockBase() & newRecord & !m_isFormModification)
			setMovementQty(Env.ZERO);
		
		/** Handle by Callout
		if (!isEndProduct() && parent.isEndingStockBase() && !isProcessed())
		{
			if (getEndingStock().signum() < 0)
				throw new AdempiereException("Cannot set ending stock into negative value.");
			setMovementQty(getQtyAvailable().subtract(getEndingStock()).negate());
			setQtyUsed(getMovementQty().negate());
		}
		*/
		
		if (isEndProduct() && is_ValueChanged(COLUMNNAME_M_Product_ID))
		{
//			MProduct product = MProduct.get(getCtx(), getM_Product_ID());
//			String sni = "SNI: " + product.getSerialNumber();
//			setSNINumber(sni);
		}
		
		if (getLine()==0){
			String sql = "SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM UNS_Production_Detail "
					+ "WHERE UNS_Production_ID=?";
			int line = //getParent().getLines().length * 10;
					DB.getSQLValueEx(get_TrxName(), sql, getUNS_Production_ID());
			setLine(line);
			//setLine(100+line);
		}
		
//		String sql = "DELETE FROM UNS_Production_DetailMA WHERE UNS_Production_Detail_ID =? ";
//		
//		DB.executeUpdateEx(
//				sql, new Object[]{get_ID()}, get_TrxName());
		
		return true;
	}

	@Override
	protected boolean beforeDelete() 
	{
		if (!this.isEndProduct()) {
			deleteMA();
		}
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) 
	{
		if (isReversalLine())
			return true;
		
		if (newRecord)
		{
			//Tricky solution to copy this ID into UNS_PD_Reff_ID to enable this production detail has 
			//two included tab.
			String sql = "UPDATE UNS_Production_Detail SET UNS_PD_Reff_ID=" + this.get_ID() + 
					" WHERE UNS_Production_Detail_ID=" + this.get_ID();
			DB.executeUpdateEx(sql, get_TrxName());
		}
		
		String updateParentSQL = null;
		
		if (!newRecord && isEndProduct() && isPrimary() && is_ValueChanged(COLUMNNAME_MovementQty))
		{
//			if (getParent().getOutputType().equals(MUNSProduction.OUTPUTTYPE_Single)
//				|| (getParent().getOutputType().equals(MUNSProduction.OUTPUTTYPE_Optional)
//						 && parent.getM_Product_ID() == this.getM_Product_ID())
//				|| (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Multi) && isPrimary()))
			if (this.getM_Product_ID() == parent.getM_Product_ID())
			{
				MProduct product = MProduct.get(getCtx(), getM_Product_ID());
				
				BigDecimal movementQty = 
						getMovementQty().abs().setScale(product.getUOMPrecision(), BigDecimal.ROUND_HALF_UP);
				
				updateParentSQL = "UPDATE UNS_Production SET ProductionQty=" + movementQty + 
						" WHERE UNS_Production_ID=" + this.getUNS_Production_ID();
				//DB.executeUpdateEx(sql, get_TrxName());
			}
		}
		
		if (newRecord && !m_isProcess) {
			//parent.setIsCreated(MUNSProduction.ISCREATED_Yes);
			updateParentSQL = "UPDATE UNS_Production SET IsCreated='Y'" + 
					" WHERE UNS_Production_ID=" + this.getUNS_Production_ID();
		}
		
		if (isEndProduct() && isPrimary())
		{
			if ((newRecord && getMovementQty().signum() > 0)
				|| (!newRecord && is_ValueChanged(COLUMNNAME_MovementQty)))
			{
				String sql = "SELECT COUNT(*) FROM ("
						+ "	SELECT bom.BOMType FROM UNS_Production_Detail pd, M_Product_BOM bom "
						+ "	WHERE pd.PrimaryOutput_ID=? AND bom.M_Product_BOM_ID=pd.M_Product_BOM_ID "
						+ "		AND pd.IsEndProduct='N'"
						+ "	GROUP BY bom.BOMType) AS DistinctProductionBOM";
				
				int differencesBOMType = DB.getSQLValue(get_TrxName(), sql, getUNS_Production_Detail_ID());
				
				if (differencesBOMType > 1) {
					String msg = "The input detail with multiple BOM Type cannot automatically calculated.";
					String title = "BOM quantity not calculated.";
					
					MessageBox.showMsg(this, getCtx(), msg, title, MessageBox.OK, MessageBox.ICONINFORMATION);
				}
				else {
					String whereClause = "PrimaryOutput_ID=? AND IsEndProduct='N'";
					List<MUNSProductionDetail> inputList = 
							Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), Table_Name, whereClause, get_TrxName())
							.setParameters(getUNS_Production_Detail_ID())
							.list();
					
					for (MUNSProductionDetail input : inputList)
					{
						MProductBOM bom = (MProductBOM) input.getM_Product_BOM();
						
						if (bom.getFormulaType().equals(MProductBOM.FORMULATYPE_BasicFormula)) 
						{
							MProduct product = MProduct.get(getCtx(), input.getM_Product_ID());
							
							BigDecimal requiredQty = getMovementQty().multiply(bom.getBOMQty());
							requiredQty = requiredQty.setScale(product.getUOMPrecision(), BigDecimal.ROUND_HALF_UP);
							
							input.setMovementQty(requiredQty.abs().negate());
							input.setQtyUsed(requiredQty.abs());
							input.saveEx();
						}
					}
				}
			}
		}
		
		if (updateParentSQL != null)
			DB.executeUpdateEx(updateParentSQL, get_TrxName());
		
		return true;
		//return parent.save();
	}
	
	@Override
	protected boolean afterDelete(boolean success)
	{
		if (!success)
			return true;
		
		if (isEndProduct())
		{
			StringBuilder sql = 
					new StringBuilder ("DELETE FROM UNS_Production_OutPlan ")
					.append("WHERE UNS_Production_ID=? AND M_Product_ID=?");// AND BOMType=?");

			if (getUNS_Resource_InOut_ID() > 0)
				sql.append(" AND UNS_Resource_InOut_ID=").append(getUNS_Resource_InOut_ID());
			
			DB.executeUpdateEx(sql.toString(), 
					new Object[]{getUNS_Production_ID(), getM_Product_ID()},// getBOMType()}, 
					get_TrxName());
		}
		
		return true;
	}

	/**
	 * 
	 * @param trxName
	 * @param M_AttributeSetInstance_ID
	 * @return MUNSProductionDetail
	 */
	public static MUNSProductionDetail get(String trxName, int M_AttributeSetInstance_ID)
	{
		MUNSProductionDetail productionDetail = null;
		String sql = "SELECT * FROM UNS_Production_Detail pd WHERE pd.M_AttributeSetInstance_ID =?";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = DB.prepareStatement(sql, trxName);
			stm.setInt(1, M_AttributeSetInstance_ID);
			rs = stm.executeQuery();
			if (rs.next())
				productionDetail = new MUNSProductionDetail(Env.getCtx(), rs, trxName);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			DB.close(rs, stm);
		}
		
		return productionDetail;
	}
	
	public String update(MUNSSrcProductDetail srcProductionDetail)
	{
		String msg = null;
		MUNSProductionSchedule ps =  srcProductionDetail.getParent();
		BigDecimal qtyToReplace = srcProductionDetail.getQtyUom();
		setEndingStock(getMovementQty().subtract(qtyToReplace));
		/*
		setDescription(
				""+qtyToReplace +" Product has replace to " + ps.getUNS_ProductionSchedule_ID() 
				+ "_" +ps.getName());
		*/
		I_M_Product replacedProduct = getM_Product();
		I_M_Product newProduct = ps.getM_Product();
		setDescription(
				""+qtyToReplace + " (" + replacedProduct.getC_UOM().getName() + ") of " + replacedProduct.getName() 
				+ " has been replaced to " + newProduct.getName());
		if (!save())
			msg = "Can't replace production detail " +getUNS_Production_Detail_ID()
			+ "_" + getM_Product().getName();
		
		ps.voidIt();		
		return msg;
	}

	@Override
	public String beforeSaveTabRecord(int parentRecord_ID) 
	{
		if (get_ID() > 0)
			return null;
		
		if (parent == null)
			parent = new MUNSProduction(getCtx(), parentRecord_ID, get_TrxName());
		
		MProductBOM theBOM = (MProductBOM) this.getM_Product_BOM();
		
		setAD_Org_ID(parent.getAD_Org_ID());
		setM_Product_ID(theBOM.getM_ProductBOM_ID());
		setIsEndProduct(false);
		setUNS_Production_ID(parentRecord_ID);
		setM_Locator_ID(
				MUNSResourceLocator.getInputLocatorOf(
						parent.getUNS_Resource_ID(), getM_Product_ID(), get_TrxName()));
		
		int theOutputID = this.getPrimaryOutput_ID();
		
		if (theOutputID <= 0)
			return "Failed while getting the output of this input.\nThe error might occurred "
					+ "because you were adding the output plan not from output selection or "
					+ "please check your Product BOM or Manufacturing Resource Output configuration for "
					+ "product input of " + MProduct.get(getCtx(), getM_Product_ID());
		
		setPrimaryOutput_ID(theOutputID);
		
		MUNSProductionDetail thePrimaryOutput = (MUNSProductionDetail) getPrimaryOutput();
		
		BigDecimal bomQty = theBOM.getBOMQtyBasedOnFormulaType();
		
		BigDecimal qtyPlanned = thePrimaryOutput.getPlannedQty().multiply(bomQty);
		BigDecimal qtyUsed = BigDecimal.valueOf(-1);
		BigDecimal qtyMovement = Env.ZERO;
		
		if (thePrimaryOutput.getQtyUsed().compareTo(Env.ZERO) > 0)
		{
			BigDecimal theOutputQtyUsed = thePrimaryOutput.getQtyUsed();
			BigDecimal theOutputQtyPlanned = thePrimaryOutput.getPlannedQty();
			
			if (theBOM.getFormulaType().equals(MProductBOM.FORMULATYPE_PreciseTotalOutputFormula)) 
			{
				//@TODO seharusnya perhitungan precice total output formula ini mempertimbangkan output
				// yang memiliki UOM berbeda antara primary dan non-primary.
				MUNSProductionDetail[] allNonPrimaries = thePrimaryOutput.getNonPrimaryOutputsOfPrimary();
				
				for (MUNSProductionDetail nonPrimaryOutput : allNonPrimaries)
				{
					theOutputQtyUsed = theOutputQtyUsed.add(nonPrimaryOutput.getQtyUsed());
					theOutputQtyPlanned = theOutputQtyPlanned.add(nonPrimaryOutput.getPlannedQty());
				}
			}
			qtyUsed = theOutputQtyUsed.multiply(bomQty).abs();
			qtyPlanned = theOutputQtyPlanned.multiply(bomQty);
			qtyMovement = qtyUsed.negate();
		}		
		
		setPlannedQty(qtyPlanned);
		setQtyUsed(qtyUsed);
		setMovementQty(qtyMovement);
		
		return null;
	}
		
/*	
	public String beforeSaveTabRecord(int parentRecord_ID) 
	{
		isSavingFromRecordOrderTab = true;
		
		if (parent == null)
			parent = new MUNSProduction(getCtx(), parentRecord_ID, get_TrxName());
		
		setAD_Org_ID(parent.getAD_Org_ID());
		setIsEndProduct(false);
		setUNS_Production_ID(parentRecord_ID);
		setM_Locator_ID(
				MUNSResourceLocator.getInputLocatorOf(
						parent.getUNS_Resource_ID(), getM_Product_ID(), get_TrxName()));
		
		//MUNSProductionOutPlan[] outs = parent.getOutputs();
		MUNSProductionDetail[] detailOutputs = parent.getOutputLines();
		
		if (detailOutputs == null || detailOutputs.length == 0)
			return "Please select/generate at least one product output before select/generate product input.";
		
		BigDecimal tQty = BigDecimal.valueOf(-1);
		
		if (parent.isWorkerBase()) {
			setQtyUsed(tQty);
			return null;
		}
			
		MProduct theProduct = MProduct.get(getCtx(), getM_Product_ID());
		
		if (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Single)) 
		{
			MProductBOM bom = MProductBOM.get(
					getCtx(), parent.getM_Product_ID(), getM_Product_ID(), parent.getBOMType(), get_TrxName());
			
			if (bom == null)
				return "You have to define a BOM for product ouput of " + parent.getM_Product().getValue() + 
						" with product input " + theProduct.getValue() + ", and BOMType of " + 
						MRefList.getListName(getCtx(), MProductBOM.BOMTYPE_AD_Reference_ID, parent.getBOMType());
			
			BigDecimal targetOutQty = 
					detailOutputs[0].getQtyUsed().signum() > 0? 
							detailOutputs[0].getMovementQty() : detailOutputs[0].getPlannedQty();
			
			BigDecimal bomQty = Env.ZERO;
			if (bom.getFormulaType().equals(MProductBOM.FORMULATYPE_OnFieldFormula)) {
				tQty = BigDecimal.valueOf(-1);
			}			
			if (bom.getFormulaType().equals(MProductBOM.FORMULATYPE_BasicFormula)) {
				bomQty = bom.getBOMQty();
				tQty = targetOutQty.multiply(bomQty);				
			}
			else if (bom.getFormulaType().equals(MProductBOM.FORMULATYPE_PreciseTotalOutputFormula)) {
				bomQty = bom.getFormulaQty();
				tQty = targetOutQty.multiply(bomQty);				
			}
			
			BigDecimal plannedQty = detailOutputs[0].getPlannedQty().multiply(bomQty);
			
			setPlannedQty(plannedQty);
			setQtyUsed(tQty);
			setMovementQty(tQty.abs().negate());
		}
		else if (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Optional)) 
		{
			BigDecimal tPlannedQty = Env.ZERO;
			
			for (MUNSProductionDetail out : detailOutputs)
			{
				MProductBOM bom = MProductBOM.get(
						getCtx(), out.getM_Product_ID(), getM_Product_ID(), out.getBOMType(), get_TrxName());
				
				if (bom == null)
					return "You have to define a BOM for product ouput of " + out.getM_Product().getValue() + 
							" with product input " + theProduct.getValue() + ", and BOMType of " + 
							MRefList.getListName(getCtx(), MProductBOM.BOMTYPE_AD_Reference_ID, out.getBOMType());
				
				BigDecimal targetOutQty = out.getQtyUsed().signum() > 0? out.getQtyUsed() : out.getPlannedQty();
				
				BigDecimal bomQty = Env.ZERO;
				if (bom.getFormulaType().equals(MProductBOM.FORMULATYPE_OnFieldFormula)) {
					continue;
				}
				else  {
					bomQty = (bom.getFormulaType().equals(MProductBOM.FORMULATYPE_BasicFormula))? 
							bom.getBOMQty() : bom.getFormulaQty();
				}
				BigDecimal inputQty = targetOutQty.multiply(bomQty);
				BigDecimal inputPlannedQty = out.getPlannedQty().multiply(bomQty);
						
				tQty = tQty.add(inputQty);
				tPlannedQty = tPlannedQty.add(inputPlannedQty);
			}
			
			setPlannedQty(tPlannedQty);
			setQtyUsed(tQty);
			setMovementQty(tQty.abs().negate());
		}
		else if (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Multi)) 
		{
			MUNSProductionDetail[] primaryOutputs = getOutputs(true);
			
			if (primaryOutputs.length == 0)
				return "Please select/generate product output plan before selecting product input.";
			
			MUNSProductionDetail primOut = primaryOutputs[0];
			 
			MProductBOM bom = MProductBOM.get(
					getCtx(), primOut.getM_Product_ID(), getM_Product_ID(), primOut.getBOMType(), get_TrxName());
			
			if (bom == null)
				return "You have to define a BOM for product ouput of " + primOut.getM_Product().getValue() + 
						" with product input " + theProduct.getValue() + ", and BOMType of " + 
						MRefList.getListName(getCtx(), MProductBOM.BOMTYPE_AD_Reference_ID, primOut.getBOMType());
			
			BigDecimal bomQty = Env.ZERO;
			
			BigDecimal targetOutputPlannedQty = primOut.getPlannedQty();
			
			if (bom.getFormulaType().equals(MProductBOM.FORMULATYPE_OnFieldFormula)) {
				;
			}
			else { 
				BigDecimal targetCalculatedOutputQty = Env.ZERO;
				
				if (bom.getFormulaType().equals(MProductBOM.FORMULATYPE_BasicFormula)) 
				{
					bomQty = bom.getBOMQty();	
					targetCalculatedOutputQty = 
							primOut.getQtyUsed().signum() > 0? primOut.getQtyUsed() : primOut.getPlannedQty();			
				}
				else if (bom.getFormulaType().equals(MProductBOM.FORMULATYPE_PreciseTotalOutputFormula))
				{
					bomQty = bom.getFormulaQty();
					
					StringBuilder sql = new StringBuilder("SELECT SUM(PlannedQty), SUM(QtyUsed)")
							.append(" FROM UNS_Production_Detail WHERE IsEndProduct='Y' AND UNS_Production_ID=?");
							//.append(" AND ");
					List<List<Object>> sumQtyList = 
							DB.getSQLArrayObjectsEx(get_TrxName(), sql.toString(), getUNS_Production_ID());
					
					targetOutputPlannedQty = (BigDecimal) sumQtyList.get(0).get(0);
					targetCalculatedOutputQty = (BigDecimal) sumQtyList.get(0).get(1);
				}
				
				tQty = targetCalculatedOutputQty.multiply(bomQty);
			}
			BigDecimal tPlannedQty = targetOutputPlannedQty.multiply(bomQty);
					
			setPlannedQty(tPlannedQty);
			setQtyUsed(tQty);
			setMovementQty(tQty.abs().negate());
		}
		else if (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_MultiOptional)) 
		{
			//Make sure all of output lines's qty has been set.
			String notSetProductQty = "";
			for (MUNSProductionDetail out : detailOutputs)
			{
				if (out.getQtyUsed().signum() < 0 || out.getMovementQty().signum() < 0) 
				{
					if (!notSetProductQty.isEmpty())
						notSetProductQty += ", ";
					
					notSetProductQty += MProduct.get(getCtx(), out.getM_Product_ID()).getValue();
				}
			}
			
			if (!notSetProductQty.isEmpty())
				return "For Multi Optional production's output type, You have to set all of the end-product's "
						+ "qty used and movement qty before generate/select the production input detail.\n"
						+ " Product that is not completed are : " + notSetProductQty;
			
			MUNSProductionDetail[] primaryOutputs = getOutputs(true);
			
			BigDecimal totalInputPlannedQty = Env.ZERO;
			BigDecimal totalInputUsedQty = Env.ZERO;
			
			for (int i=0; i < primaryOutputs.length; i++)
			{
				MUNSProductionDetail primOut = primaryOutputs[i];
				
				MProductBOM bom = MProductBOM.get(
						getCtx(), primOut.getM_Product_ID(), getM_Product_ID(), primOut.getBOMType(), get_TrxName());
				
				if (bom == null)
					continue;
				
				BigDecimal bomQty = Env.ZERO;
				
				BigDecimal currInputPlannedQty = Env.ZERO;
				BigDecimal currInputUsedQty = Env.ZERO;
				
				BigDecimal targetPlannedOutputQty = primOut.getPlannedQty();
				BigDecimal targetUsedOutputQty = primOut.getQtyUsed();
				
				if (bom.getFormulaType().equals(MProductBOM.FORMULATYPE_BasicFormula)
					|| bom.getFormulaType().equals(MProductBOM.FORMULATYPE_OnFieldFormula)) 
				{
					bomQty = bom.getBOMQty();	
					targetUsedOutputQty = 
							primOut.getQtyUsed().signum() > 0? primOut.getQtyUsed() : primOut.getPlannedQty();			
				}
				else if (bom.getFormulaType().equals(MProductBOM.FORMULATYPE_PreciseTotalOutputFormula))
				{
					bomQty = bom.getFormulaQty();
					
					MUNSProductionDetail[] nonPrimaryOutputs = primOut.getNonPrimaryOutputsOfPrimary();
					
					for (MUNSProductionDetail nonPrimOut : nonPrimaryOutputs)
					{
						String sql = "SELECT SUM(PlannedQty), SUM(QtyUsed) FROM UNS_Production_Detail "
								+ "WHERE IsEndProduct='Y' AND M_Product_ID IN "
								+ "(SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio "
								+ " WHERE rio.UNS_Resource_ID=? AND rio.UNS_Resource_InOut_ID IN "
								+ "		(SELECT rioLink.UNS_OutputLink_ID FROM UNS_Resource_InOut rioLink "
								+ "		 WHERE rioLink.UNS_Resource_ID=? AND rioLink.UNS_OutputLink_ID IS NOT NULL "
								+ "			AND rioLink.M_Product_ID=?))";
						List<List<Object>> primaryDetailQties = 
								DB.getSQLArrayObjectsEx(
										get_TrxName(), sql, 
										parent.getUNS_Resource_ID(), parent.getUNS_Resource_ID(),
										nonPrimOut.getM_Product_ID());
						BigDecimal totalPrimaryPlannedQties = (BigDecimal) primaryDetailQties.get(0).get(0);
						BigDecimal totalPrimaryUsedQties = (BigDecimal) primaryDetailQties.get(0).get(1);
						
						BigDecimal primOutPlannedPortion = 
								primOut.getPlannedQty().divide(totalPrimaryPlannedQties, 10, BigDecimal.ROUND_HALF_UP);
						BigDecimal primOutUsedPortion = 
								primOut.getQtyUsed().divide(totalPrimaryUsedQties, 10, BigDecimal.ROUND_HALF_UP);
						
						BigDecimal nonPrimOutPlannedPortionQty = 
								primOutPlannedPortion.multiply(nonPrimOut.getPlannedQty());
						BigDecimal nonPrimOutUsedPortionQty = 
								primOutUsedPortion.multiply(nonPrimOut.getQtyUsed());
						
						targetPlannedOutputQty = targetPlannedOutputQty.add(nonPrimOutPlannedPortionQty);
						targetUsedOutputQty = targetUsedOutputQty.add(nonPrimOutUsedPortionQty);
					}
				}
				currInputPlannedQty	= targetPlannedOutputQty.multiply(bomQty);
				currInputUsedQty 	= targetUsedOutputQty.multiply(bomQty);
				
				totalInputPlannedQty	= totalInputPlannedQty.add(currInputPlannedQty);
				totalInputUsedQty 		= totalInputUsedQty.add(currInputUsedQty);
			}
			
			setPlannedQty(totalInputPlannedQty);
			setQtyUsed(totalInputUsedQty);
			setMovementQty(totalInputUsedQty.abs().negate());			
		}
		
		return null;
	}
*/		
	
	/**
	 * Get all of the outputs for this production.
	 * @isPrimary Get only primary or non primary output details (true/false).
	 * @return return the production details that is set as end product, return empty array if not exists.
	 *  If this is EndProduct and nonPrimary then return all possible primary/non-primary output of this product-id, 
	 *  If this is EndProduct and Primary then return all primary/non-primary output of this detail resource.
	 *  If this is an input (EndProduct=false) then return all primary/non-primary output of this input.
	 */
	public MUNSProductionDetail[] getOutputs(boolean isGetPrimaryOutput)
	{
		if (parent == null)
			parent = (MUNSProduction) getUNS_Production();		
		
		StringBuilder whereClause =
				new StringBuilder("M_Product_ID IN (SELECT pop.M_Product_ID FROM UNS_Production_OutPlan pop ")
				.append("WHERE pop.UNS_Production_ID=").append(getUNS_Production_ID());
		
		String isPrimaryStr = isGetPrimaryOutput? "'Y'" : "'N'";
		
		//if (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_MultiOptional)
		//		|| parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Multi))
		whereClause.append(" AND pop.IsPrimary=").append(isPrimaryStr);
		
		if (this.isEndProduct()) 
		{
			if (!this.isPrimary() && parent.isMultiOptional())
			{
				whereClause
				.append(" AND M_Product_ID IN (SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio")
				.append("	WHERE rio.InOutType='O' AND rio.UNS_Resource_ID=").append(parent.getUNS_Resource_ID())
				.append("		AND rio.UNS_Resource_InOut_ID IN (")
				.append("		SELECT rio1.UNS_OutputLink_ID FROM UNS_Resource_InOut rio1 ")
				.append("		WHERE rio1.InOutType='O' AND rio1.UNS_OutputLink_ID IS NOT NULL AND rio1.UNS_OutputLink_ID>0 ")
				.append(" 			AND rio1.M_Product_ID=").append(getM_Product_ID()).append(")").append("))");
			}
			else // if this is a primary output detail, then just get all the outputs. 
				whereClause.append(")");
		}
		else if (this.getPrimaryOutput_ID() > 0) 
		{
			if (isGetPrimaryOutput)
				return new MUNSProductionDetail[]{(MUNSProductionDetail) getPrimaryOutput()};
			else {
				whereClause = new StringBuilder("UNS_Production_ID=").append(getUNS_Production_ID())
				.append(" AND PrimaryOutput_ID=").append(getPrimaryOutput_ID())
				.append(" AND IsEndProduct='Y' AND IsPrimary='N'");
			}
		}
		else {// this is not end product (it is an input) and not related to any primary output.
			
			if (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Single)
				|| parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Multi)) {
				whereClause.append(")");
			}
			else if (isGetPrimaryOutput 
					|| parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Optional)) {
				whereClause.append(" AND pop.M_Product_ID IN ")
				.append("	(SELECT bom.M_Product_ID FROM M_Product_BOM bom ")
				.append("	  WHERE bom.M_ProductBOM_ID=").append(getM_Product_ID())
				.append(" 			AND (bom.BOMType='0.P' OR bom.BOMType IN ")
				.append("			(SELECT pop1.BOMType FROM UNS_Production_OutPlan pop1 ")
				.append("		 	WHERE pop1.IsPrimary=").append(isPrimaryStr)
				.append("				AND pop1.UNS_Production_ID=").append(getUNS_Production_ID() + ")))) ")
				.append("AND UNS_Production_ID=").append(getUNS_Production_ID()).append(" AND IsEndProduct='Y'");
			}
			else { // GetNonPrimaryOutput and it is multi optional.
				whereClause.append(" AND pop.M_Product_ID IN ")
				.append("(SELECT rioNonPrim.M_Product_ID FROM UNS_Resource_InOut rioNonPrim")
				.append(" WHERE rioNonPrim.UNS_Resource_ID=").append(parent.getUNS_Resource_ID())
				.append(" 	AND rioNonPrim.UNS_OutputLink_ID IN ")
				.append("	(SELECT rioPrim.UNS_Resource_InOut_ID FROM UNS_Resource_InOut rioPrim ")
				.append("	 WHERE rioPrim.UNS_Reosurce_ID=").append(parent.getUNS_Resource_ID())
				.append("		AND rioPrim.M_Product_ID IN ")
				.append("		(SELECT bom.M_Product_ID FROM M_Product_BOM bom ")
				.append("	  	 WHERE bom.M_ProductBOM_ID=").append(getM_Product_ID())
				.append(" 			AND (bom.BOMType='0.P' OR bom.BOMType IN ")
				.append("			(SELECT pop1.BOMType FROM UNS_Production_OutPlan pop1 ")
				.append("		 	WHERE pop1.IsPrimary=").append(isPrimaryStr)
				.append("				AND pop1.UNS_Production_ID=").append(getUNS_Production_ID() + ")))))) ")
				.append("AND UNS_Production_ID=").append(getUNS_Production_ID()).append(" AND IsEndProduct='Y'");
			}
		}
		
		List<MUNSProductionDetail> outList =
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
						Table_Name, whereClause.toString(), get_TrxName())
				//.setParameters(getUNS_Production_ID())
				.list();
		
		MUNSProductionDetail[] retPrimOuts = new MUNSProductionDetail[outList.size()];
		
		outList.toArray(retPrimOuts);
				
		return retPrimOuts;
	}

	/**
	 * Get all of the non primary outputs for this production output (end product) detail.
	 * 
	 * @return return an array of the production details that is set as non primary end product, 
	 * 		   or an empty array if not exists or null if this instance "is not an end product". 
	 */
	public MUNSProductionDetail[] getNonPrimaryOutputsOfPrimary()
	{
		if (parent == null)
			parent = (MUNSProduction) getUNS_Production();
		
		if (!isEndProduct() 
				|| parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Single)
				|| parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Optional))
			return null;
		
		StringBuilder whereClause =
				new StringBuilder("IsEndProduct='Y' AND IsPrimary='N' AND M_Product_ID IN ")
				.append("(SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio ")
				.append("WHERE rio.IsPrimary='N' AND InOutType='O' AND UNS_Resource_ID=")
				.append(parent.getUNS_Resource_ID());
		
		if (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_MultiOptional)) 
		{
			whereClause.append(" AND UNS_OutputLink_ID=");
			
			if (getUNS_Resource_InOut_ID() > 0)
				whereClause.append(getUNS_Resource_InOut_ID());
			else
				whereClause.append("(SELECT rio1.UNS_Resource_InOut_ID FROM UNS_Resource_InOut rio1 ")
				.append("WHERE rio1.IsPrimary='Y' AND rio1.InOutType='O' ")
				.append("rio1.UNS_Resource_ID=").append(parent.getUNS_Resource_ID())
				.append(" AND rio1.M_Product_ID=").append(getM_Product_ID()).append(")");
		}
		
		whereClause.append(")");
		
		List<MUNSProductionDetail> outList =
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
						Table_Name, whereClause.toString(), get_TrxName())
				//.setParameters(false, getUNS_Production_ID(), getM_Product_ID(), getUNS_Production_ID(), "Y")
				.list();
		
		MUNSProductionDetail[] retPrimOuts = new MUNSProductionDetail[outList.size()];
		
		outList.toArray(retPrimOuts);
				
		return retPrimOuts;
	}
	
	/**
	 * Get all details that is possible as the input of this production (output) detail.
	 * NOTE: Only use this method if the production schema ignores relation between Primary Output with 
	 * it's Non-Primary Output and the inputs.
	 * If this production detail is the end product (output) then return all related inputs correlated with
	 * this output product's of BOM. If this is non primary output then return all inputs of all primary output
	 * that has this non primary output. Notes: this will only select the Standard Part 
	 *  and this primary output's BOMType.
	 * @return
	 */
	public MUNSProductionDetail[] getAllPossibleInputDetails()
	{
		if (parent == null)
			parent = (MUNSProduction) getUNS_Production();
		
		String whereClause = "IsEndProduct='N' AND UNS_Production_ID=" + getUNS_Production_ID();

		if (this.isEndProduct() 
			&& (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Optional) 
				|| parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_MultiOptional))) 
		{
			whereClause += " AND M_Product_ID IN (SELECT bom.M_ProductBOM_ID FROM M_Product_BOM bom "
					+ "WHERE bom.M_Product_ID";
			
			String theOutputSelection = 
					"=" + getM_Product_ID() + " AND BOMType IN ('" + getBOMType() + "', '0.P')) ";
			
			if (parent.getOutputType().equals(MUNSProduction.OUTPUTTYPE_MultiOptional) 
					&& !this.isPrimary())
			{
				theOutputSelection =
						" IN (SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio "
						+ "WHERE rio.UNS_Resource_InOut_ID IN "
						+ "(SELECT rio1.UNS_OutputLink_ID FROM UNS_Resource_InOut rio1"
						+ " WHERE rio1.M_Product_ID=" + getM_Product_ID() + "))"
						+ " AND (BOMType='0.P' OR BOMType IN (SELECT pd.BOMType FROM UNS_Production_Detail pd"
						+ " 	WHERE pd.IsEndProduct='Y' AND pd.IsPrimary='Y' AND pd.M_Product_ID IN "
						+ " 		(SELECT rio2.M_Product_ID FROM UNS_Resource_InOut rio2 "
						+ "			 WHERE rio2.UNS_Resource_InOut_ID IN "
						+ "				(SELECT rio3.UNS_OutputLink_ID FROM UNS_Resource_InOut rio3"
						+ " 				WHERE rio3.M_Product_ID=" + getM_Product_ID() + ")))))";
			}
			
			whereClause += theOutputSelection;
		}
		
		List<MUNSProductionDetail> inputList = 
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
						Table_Name, whereClause, get_TrxName()).list();
		
		MUNSProductionDetail[] retInputs = new MUNSProductionDetail[inputList.size()];
		
		inputList.toArray(retInputs);
		
		return retInputs;
	}
	
	/**
	 * Get all of the input details of this production output.
	 * @return return all of the input of this production output, or null if this instance is an input detail.
	 */
	public MUNSProductionDetail[] getInputs()
	{
		if (!this.isEndProduct())
			return null;
		
		String whereClause = "IsEndProduct='N' AND PrimaryOutput_ID=";
		
		if (this.isPrimary())
			whereClause += get_ID();
		else
			whereClause += this.getPrimaryOutput_ID();
		
		List<MUNSProductionDetail> inputList = 
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
						Table_Name, whereClause, get_TrxName())
				.setOrderBy(COLUMNNAME_Line + " ASC")
				.list();
		
		MUNSProductionDetail[] retInputs = new MUNSProductionDetail[inputList.size()];
		
		inputList.toArray(retInputs);
		
		return retInputs;
	}
	
	/**
	 * Get all of the non-primary output details of this production output.
	 * @return return all of the non-primary output of this primary output, 
	 * 		   or null if this instance is an input detail or a non-primary output.
	 */
	public MUNSProductionDetail[] getNonPrimaryOutputs()
	{
		if (!this.isEndProduct() || !this.isPrimary())
			return null;
		
		String whereClause = "IsEndProduct='Y' AND IsPrimary='N' AND PrimaryOutput_ID=" + get_ID();
		
		List<MUNSProductionDetail> inputList = 
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
						Table_Name, whereClause, get_TrxName())
				.setOrderBy(COLUMNNAME_Line + " ASC")
				.list();
		
		MUNSProductionDetail[] retNonPrimaries = new MUNSProductionDetail[inputList.size()];
		
		inputList.toArray(retNonPrimaries);
		
		return retNonPrimaries;
	}
	
	/**
	 * Get this MProductBOM instance.
	 * 
	 * @return
	 */
	public MProductBOM getM_Product_BOM()
	{
		if (m_BOM != null)
			return m_BOM;
		
		m_BOM = (MProductBOM) super.getM_Product_BOM();
		return m_BOM;
	}
	
	/**
	 * 
	 */
	public MUNSProductionDetail getPrimaryOutput()
	{
		int primaryOutputID = getPrimaryOutput_ID();
		
		if (primaryOutputID <= 0)
			return null;
		
		return new MUNSProductionDetail (getCtx(), primaryOutputID, get_TrxName());
	}
	
	private int m_primaryOutput_ID = -1;
	
	/**
	 * Get the primary output ID of this product detail.
	 */
	public int getPrimaryOutput_ID()
	{
		//if (super.getPrimaryOutput_ID() > 0)
		if (m_primaryOutput_ID < 0 && super.getPrimaryOutput_ID() > 0) {
			m_primaryOutput_ID = super.getPrimaryOutput_ID();
			return super.getPrimaryOutput_ID();
		}
		else if (m_primaryOutput_ID >= 0)
			return m_primaryOutput_ID;
		
		String sql = "";
		
		if (this.isEndProduct()) {
			if (this.isPrimary() || parent.isSingle() || parent.isOptional())
				return this.get_ID();
			else if (this.getUNS_Resource_InOut_ID() > 0) {
				sql = "SELECT UNS_Production_Detail_ID FROM UNS_Production_Detail "
						+ "WHERE UNS_Production_ID=" + getUNS_Production_ID() + " AND IsPrimary='Y' "
						+ " 	AND UNS_Resource_InOut_ID=("
						+ "			SELECT rio.UNS_OutputLink_ID FROM UNS_Resource_InOut rio "
						+ "			WHERE rio.UNS_Resource_InOut_ID=" + getUNS_Resource_InOut_ID() + ")";
			}
			else {
				sql = "SELECT UNS_Production_Detail_ID FROM UNS_Production_Detail "
						+ "WHERE UNS_Production_ID=" + getUNS_Production_ID() + " AND IsPrimary='Y' "
						+ "	AND M_Product_ID=(SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio "
						+ " 		WHERE rio.IsPrimary= rio.UNS_Resource_ID=" + parent.getUNS_Resource_ID();
			}
		}
		else {
			if (this.getM_Product_BOM_ID() > 0) {
				sql = "SELECT UNS_Production_Detail_ID FROM UNS_Production_Detail "
						+ " WHERE UNS_Production_ID=" + getUNS_Production_ID() + " AND IsPrimary='Y' "
						+ "   AND M_Product_ID=" + getM_Product_BOM().getM_Product_ID();
			}
			else {
				sql = "SELECT UNS_Production_Detail_ID FROM UNS_Production_Detail "
						+ " WHERE UNS_Production_ID=" + getUNS_Production_ID() + " AND IsPrimary='Y' "
						+ "   AND M_Product_ID IN (SELECT bom.M_Product_ID FROM M_Product_BOM bom "
						+ "		WHERE bom.M_ProductBOM_ID=" + this.getM_Product_ID() + ")";
			}
		}
		
		int primaryOutput_ID = DB.getSQLValueEx(get_TrxName(), sql);
		
		if (primaryOutput_ID == -1)
			primaryOutput_ID = 0;
		
		m_primaryOutput_ID = primaryOutput_ID;
		
		return m_primaryOutput_ID;
	}
	
	private int m_Activity_ID = -1;
	
	/**
	 * Get this production's detail of activity.
	 * The activity is based on the primary output by which this detail is related to. 
	 * @return
	 */
	public int getActivityOfThisDetail()
	{
		if (m_Activity_ID >= 0)
			return m_Activity_ID;
		
		MUNSProductionDetail primaryPD = getPrimaryOutput();
		
		if (primaryPD == null) {
			m_Activity_ID = 0;
			return m_Activity_ID;
		}
		
		MProduct primaryOutput = MProduct.get(getCtx(), primaryPD.getM_Product_ID());
		MProductCategory theOutCat = MProductCategory.get(getCtx(), primaryOutput.getM_Product_Category_ID());

		m_Activity_ID = MActivity.getIDOf(getCtx(), theOutCat.getValue(), get_TrxName());
		
		if (m_Activity_ID > 0)
			return m_Activity_ID;
		
		MActivity theActivity = MActivity.create(
				getCtx(), theOutCat.getValue(), theOutCat.getName(), get_TrxName());
		
		m_Activity_ID = theActivity.get_ID();
		
		return m_Activity_ID;
	}

	/**
	public static void createDetails(MUNSProduction production,
			MUNSMP1FormDetail detail, boolean ovtProduction) {
		Properties ctx = production.getCtx();
		String trxName  = production.get_TrxName();
		MUNSMP1Form mp1Form = detail.getMP1Form();
		
		//MAIN END PRODUCT
		MProduct finishedProduct = new MProduct(ctx, production.getM_Product_ID(), trxName);
		MUNSProductionOutPlan productionOP = production.getPrimaryOutput();
		
		BigDecimal productionQty = BigDecimal.ZERO;
		if (mp1Form.getM_Product_ID()==UNSApps.getRefAsInt(UNSApps.PRD_KBA))
			productionQty = detail.getWhiteMeatBW();
		else if (!ovtProduction)
			productionQty = detail.getKC();
		else
			productionQty = detail.getKCExtra();
				
		int locatorID = productionOP.getM_Locator_ID();
		int lineno = 100;
		
		MUNSProductionDetail line = new MUNSProductionDetail(production);
		line.m_isFormModification = true;
		
		line.setLine(lineno);
		line.setM_Product_ID(finishedProduct.getM_Product_ID());
		line.setM_Locator_ID(locatorID);
		line.setMovementQty(productionQty);
		line.setPlannedQty(productionQty);
		
		line.saveEx();

		MUNSProductionOutPlan[] otherOPs = production.getOtherOutput();
		
		//SECONDARY END PRODUCT
		for (MUNSProductionOutPlan otherOP : otherOPs) 
		{
			//IF KB Pecah Segar SKIP CW, RWM, BDM, PRJ
			if (mp1Form.isKBPecahSegar()){
				if (UNSApps.getRefAsInt(UNSApps.PRD_CWBS)==otherOP.getM_Product_ID())
					continue;
				else if (UNSApps.getRefAsInt(UNSApps.PRD_RWM)==otherOP.getM_Product_ID())
					continue;
//				else if (UNSApps.getRefAsInt(UNSApps.PRD_PRJ)==otherOP.getM_Product_ID())
//					continue;
				else if (UNSApps.getRefAsInt(UNSApps.PRD_KCL)==otherOP.getM_Product_ID())
					continue;
			} else {
				if (UNSApps.getRefAsInt(UNSApps.PRD_KCL)==otherOP.getM_Product_ID())
					continue;
			}
			
			lineno = lineno + 10;
			
			MUNSProductionDetail pd = new MUNSProductionDetail(production);
			pd.m_isFormModification = true;
			
			pd.setLine(lineno);
			pd.setM_Product_ID(otherOP.getM_Product_ID());
			pd.setM_Locator_ID(otherOP.getM_Locator_ID());
			
			if (UNSApps.getRefAsInt(UNSApps.PRD_CWBS)==otherOP.getM_Product_ID())
			{
				pd.setMovementQty(detail.getCoconutWaterBS());
				pd.setPlannedQty(detail.getCoconutWaterBS());
			}
			else if (UNSApps.getRefAsInt(UNSApps.PRD_CCS)==otherOP.getM_Product_ID())
			{
				if (!ovtProduction) {
					pd.setMovementQty(detail.getCoconutShell());
					pd.setPlannedQty(detail.getCoconutShell());
				} else {
					pd.setMovementQty(detail.getCCSExtra());
					pd.setPlannedQty(detail.getCCSExtra());
				}
			}
			else if (UNSApps.getRefAsInt(UNSApps.PRD_SKM)==otherOP.getM_Product_ID())
			{
				pd.setMovementQty(detail.getSkinMeat());
				pd.setPlannedQty(detail.getSkinMeat());
			}
			else if (UNSApps.getRefAsInt(UNSApps.PRD_RWM)==otherOP.getM_Product_ID())
			{
				pd.setMovementQty(detail.getRawMeat());
				pd.setPlannedQty(detail.getRawMeat());
			}
			else if (UNSApps.getRefAsInt(UNSApps.PRD_BDM)==otherOP.getM_Product_ID())
			{
				pd.setMovementQty(detail.getBadMeat());
				pd.setPlannedQty(detail.getBadMeat());
			}
			else if (UNSApps.getRefAsInt(UNSApps.PRD_PRJ)==otherOP.getM_Product_ID())
			{
				pd.setMovementQty(detail.getPreReject());
				pd.setPlannedQty(detail.getPreReject());
			}
			else if (UNSApps.getRefAsInt(UNSApps.PRD_KCL)==otherOP.getM_Product_ID())
			{
				if (!ovtProduction) {
					pd.setMovementQty(detail.getKC());
					pd.setPlannedQty(detail.getKC());
				} else {
					pd.setMovementQty(detail.getKCExtra());
					pd.setPlannedQty(detail.getKCExtra());
				}
			}
			//Force to ZERO value
			else
			{
				pd.setMovementQty(BigDecimal.ZERO);
				pd.setPlannedQty(BigDecimal.ZERO);
			}
			//Throw Exception if Product not found in configuration
//			else
//				throw new AdempiereException("Error at Resource Output Configuration, " +
//						"not found Product:"+ otherOP.getM_Product().getValue() +".");

			pd.saveEx();
		}
		
		//MATERIAL PRODUCT
		lineno = lineno + 10;
		MUNSProductionDetail material = new MUNSProductionDetail(production);
		material.m_isFormModification = true;
		
		MProduct primaryBOM = MProductBOM.getBOMLines(finishedProduct)[0].getProduct();
		if (primaryBOM.get_ID() != mp1Form.getM_Product_ID())
			throw new AdempiereException("Error at BOM Configuration, Main Product is "+primaryBOM.getValue());
		int inputLocator = MUNSProduction
				.getLocator(ctx, production.getUNS_Resource_ID(), primaryBOM.get_ID(), trxName);
		
		material.setLine(lineno);
		material.setM_Product_ID(primaryBOM.get_ID());
		material.setM_Locator_ID(inputLocator);
		material.setMovementQty(detail.getProductionQty().negate());
		material.setPlannedQty(detail.getProductionQty());
		material.setEndingStock(detail.getEndingStock());
		
		material.saveEx();
	}

	public void updateProductionDetail(MUNSMP1FormDetail detail, boolean ovtProduction) {
		m_isFormModification = true;
		if (getM_Product_ID() == UNSApps.getRefAsInt(UNSApps.PRD_WMBW))
		{
			setMovementQty(detail.getWhiteMeatBW());
			setPlannedQty(detail.getWhiteMeatBW());
		} 
		else if (UNSApps.getRefAsInt(UNSApps.PRD_CWBS)==getM_Product_ID())
		{
			setMovementQty(detail.getCoconutWaterBS());
			setPlannedQty(detail.getCoconutWaterBS());
		}
		else if (UNSApps.getRefAsInt(UNSApps.PRD_CCS)==getM_Product_ID())
		{
			if (!ovtProduction) {
				setMovementQty(detail.getCoconutShell());
				setPlannedQty(detail.getCoconutShell());
			} else {
				setMovementQty(detail.getCCSExtra());
				setPlannedQty(detail.getCCSExtra());
			}
		}
		else if (UNSApps.getRefAsInt(UNSApps.PRD_SKM)==getM_Product_ID())
		{
			setMovementQty(detail.getSkinMeat());
			setPlannedQty(detail.getSkinMeat());
		}
		else if (UNSApps.getRefAsInt(UNSApps.PRD_RWM)==getM_Product_ID())
		{
			setMovementQty(detail.getRawMeat());
			setPlannedQty(detail.getRawMeat());
		}
		else if (UNSApps.getRefAsInt(UNSApps.PRD_BDM)==getM_Product_ID())
		{
			setMovementQty(detail.getBadMeat());
			setPlannedQty(detail.getBadMeat());
		}
		else if (UNSApps.getRefAsInt(UNSApps.PRD_PRJ)==getM_Product_ID())
		{
			setMovementQty(detail.getPreReject());
			setPlannedQty(detail.getPreReject());
		}
		else if (UNSApps.getRefAsInt(UNSApps.PRD_KBA)==getM_Product_ID())
		{
			setMovementQty(detail.getProductionQty().negate());
			setQtyUsed(detail.getProductionQty());
			setPlannedQty(detail.getProductionQty());
			setEndingStock(detail.getEndingStock());
		} 
		else if (UNSApps.getRefAsInt(UNSApps.PRD_KCL)==getM_Product_ID())
		{
			if (!ovtProduction) {
				setMovementQty(detail.getKC());
				setPlannedQty(detail.getKC());
			} else {
				setMovementQty(detail.getKCExtra());
				setPlannedQty(detail.getKCExtra());
			}
		}
		else if (UNSApps.getRefAsInt(UNSApps.PRD_KBB)==getM_Product_ID())
		{
			setMovementQty(detail.getProductionQty().negate());
			setQtyUsed(detail.getProductionQty());
			setPlannedQty(detail.getProductionQty());
			setEndingStock(detail.getEndingStock());
		} 
		//Throw Exception if Product not found in configuration
//		else
//			throw new AdempiereException("Error at Resource Output Configuration, " +
//					"not found Product:"+ getM_Product().getValue() +".");
	}
	*/

	/**
	 * To indicate if this production detail line is for reversal.
	 * @return
	 */
	public boolean isReversalLine()
	{
		return getReversalLine_ID() > 0;
	} // isReversalLine
	
	/**
	 * Get all the line MA of this production detail.
	 * @return
	 */
	public MUNSProductionDetailMA[] getMALines()
	{
		List<MUNSProductionDetailMA> maList = 
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
						MUNSProductionDetailMA.Table_Name, 
						"UNS_Production_Detail_ID=" + get_ID(), 
						get_TrxName())
				.list();
		
		MUNSProductionDetailMA[] retMAs = new MUNSProductionDetailMA[maList.size()];
		
		maList.toArray(retMAs);
		
		return retMAs;
	}

	/**
	 * Create MA Lines for input details.
	 * @param sourceList
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String createMALines(MUNSSrcProductDetail[] sourceList)
	{
		StringBuilder errorString = new StringBuilder();
		
		MStorageOnHand[] storages = null;
		if(null != sourceList)
		{
			ArrayList<MStorageOnHand> list = new ArrayList<MStorageOnHand>();
			for(int i=0; i<=sourceList.length; i++)
			{
				int loc_id = MStorageOnHand.getM_Locator_ID(MWarehouse.getForOrg(getCtx(), 
															getAD_Org_ID())[0].get_ID(), getM_Product_ID(), 
															sourceList[i].getM_AttributeSetInstance_ID(), 
															sourceList[i].getQtyUom(), get_TrxName());
				/** add storage on hand with QAStatus @author YAKA */ 
				list.add(MStorageOnHand.get(getCtx(), loc_id, getM_Product_ID(), 
											sourceList[i].getM_AttributeSetInstance_ID(), 
											get_TrxName()/*, MStorageOnHand.PoorQAStatus, false*/));
			}
			MStorageOnHand[] soh = new MStorageOnHand[list.size()];
			storages = list.toArray(soh);
		} 
		else {
			storages = MStorageOnHand.getAll(getCtx(), getM_Product_ID(), getM_Locator_ID(), get_TrxName());
		}
		
		if (storages == null || storages.length <=0) {
			log.log(Level.SEVERE, "Could not complete production " + toString());
			errorString.append("Could not complete production: no inventory available for " + 
								getM_Product().getValue() + " at location-" + getM_Locator().getValue() + ".\n");
		}
		
		MUNSProductionDetailMA lineMA = null;
		// Remove all MA of this production detail first.
		String sql = 
				"DELETE FROM UNS_Production_DetailMA WHERE IsAutoGenerated='Y' AND UNS_Production_Detail_ID=?";
		int count = DB.executeUpdateEx(sql, new Object[]{get_ID()}, get_TrxName());
		log.log(Level.INFO, "Resetting Line MAs : " + count);
		
		BigDecimal qtyToMove = getMovementQty().abs();
		BigDecimal availableQty = Env.ZERO;

		for (int sl = 0; sl < storages.length; sl++) 
		{
			MStorageOnHand storage = storages[sl];

			BigDecimal lineQty = storage.getQtyOnHand();
			availableQty = availableQty.add(lineQty);
			
			log.log(Level.FINE, "QtyAvailable " + lineQty);
			
			if (lineQty.signum() > 0) 
			{
				if (lineQty.compareTo(qtyToMove) > 0)
					lineQty = qtyToMove;

				lineMA = MUNSProductionDetailMA.get(this, storage.getM_AttributeSetInstance_ID(), 
						storage.getDateMaterialPolicy());
				
				if (storage.getM_AttributeSetInstance_ID() == 0)
					lineMA.setDateMaterialPolicy(storage.getDateMaterialPolicy());
				
				lineMA.setMovementQty(lineMA.getMovementQty().add(lineQty.negate()));
//				lineMA.set_ValueOfColumn(COLUMNNAME_Created, getParent().getMovementDate());
				
				if (!lineMA.save(get_TrxName())) {
					log.log(Level.SEVERE, "Could not save MA for " + toString());
					errorString.append("Could not save MA for " + toString() + "\n");
				} else
					log.log(Level.FINE, "Saved MA for " + toString());
			}
			
			qtyToMove = qtyToMove.subtract(lineQty);
			
			if (qtyToMove.signum() == 0)
				break;
		} // for available storages
		
		if (qtyToMove.signum() > 0)
		{
			String errMsg = "Insufficient available quantity for input of " + getM_Product() + " on locator " 
					+ getM_Locator() + ".\n Available=" + availableQty + " Required=" + getMovementQty().abs() + ".";
			errorString.append(errMsg);
			log.log(Level.SEVERE, errMsg);
		}
		
		return errorString.toString();
	} //createMALines
	
	/**
	 * 
	 * @param pReversal
	 * @param pdReversed
	 * @return
	 */
	public String initReversalDetail(MUNSProduction pReversal, MUNSProductionDetail pdReversed)
	{
		copyValues(pdReversed, this, pdReversed.getAD_Client_ID(), pdReversed.getAD_Org_ID());
		
		//this.setClientOrg(pdReversed.getAD_Client_ID(), pdReversed.getAD_Org_ID());
		this.setUNS_Production_ID(pReversal.getUNS_Production_ID());
		this.setReversalLine_ID(pdReversed.getUNS_Production_Detail_ID());
		this.setMovementQty(pdReversed.getMovementQty().negate());
		this.setQtyUsed(pdReversed.getQtyUsed());
		this.setProcessed(false);

		if (isEndProduct() && pdReversed.getM_AttributeSetInstance_ID() > 0) {
			this.setM_AttributeSetInstance_ID(pdReversed.getM_AttributeSetInstance_ID());
		}
		else 
		{
			this.saveEx();
			
			MUNSProductionDetailMA[] maLines = pdReversed.getMALines();
			for (MUNSProductionDetailMA ma : maLines)
			{
				MUNSProductionDetailMA reversalMA = 
						new MUNSProductionDetailMA(
								this, 
								ma.getM_AttributeSetInstance_ID(), 
								ma.getMovementQty().negate());
				reversalMA.setUPC(ma.getUPC());
				if (!reversalMA.save()) {
					return "Could not create Reversal Line MA of Production Detail (Input) of " 
							+ pdReversed.getM_Product();
				}
			}
		}
		
		return null;
	} // initReversalDetail

	@Override
	public String beforeRemoveSelection() {
		// TODO Auto-generated method stub
		return null;
	}
}

//public String OLD_createTransactions(Timestamp date, boolean mustBeStocked, MUNSSrcProductDetail[] sourceList) 
//{
//	if (parent == null)
//		parent = (MUNSProduction) getUNS_Production();
//	
//	// delete existing ASI records
//	int deleted = deleteMA();
//	log.log(Level.FINE, "Deleted " + deleted + " attribute records ");
//	MProduct prod = new MProduct(getCtx(), getM_Product_ID(), get_TrxName());
//	log.log(Level.FINE, "Loaded Product " + prod.toString());
//
//	if (prod.getProductType().compareTo(MProduct.PRODUCTTYPE_Item) != 0) {
//		// no need to do any movements
//		log.log(Level.FINE, "Production Line " + getLine()
//				+ " does not require stock movement");
//		return "";
//	}
//	StringBuilder errorString = new StringBuilder();
//
//	//if getMovementQty is zero, not create MA 
//	if (getMovementQty().intValue()==0)
//		return errorString.toString();
//
//	MAttributeSetInstance asi = 
//			new MAttributeSetInstance(getCtx(), getM_AttributeSetInstance_ID(), get_TrxName());
//
//	String asiString = asi.getDescription();
//	if (asiString == null)
//		asiString = "";
//		
//	log.log(Level.FINEST, "asi Description is: " + asiString);
//	// create transactions for the production result.
//	if (getMovementQty().compareTo(Env.ZERO) > 0) 
//	{	
//		MUNSProductionDetailMA lineMA = new MUNSProductionDetailMA(this, asi.get_ID(), getMovementQty());
//		
//		if (!lineMA.save(get_TrxName())) {
//			log.log(Level.SEVERE, "Could not save MA for " + toString());
//			errorString.append("Could not save MA for " + toString() + "\n");
//		}
//		//TODO QA STATUS
////		MUNSResourceInOut rio = getParent().getResource().getResourceOutput(getM_Product_ID());
////		String QAStatus = "-";
////		if (rio != null && rio.isQAMonitoring()) 
////		{
////			MProductCategory prodCat = (MProductCategory)getM_Product().getM_Product_Category();
////			//QAStatus = prodCat.getInitialQAStatus();
////		}
////		boolean possibleCreatePallet = prod.isStockedOnPallet();
//		
//		if (!MStorageOnHand.add(getCtx(), 
//								getM_Locator().getM_Warehouse_ID(), 
//								getM_Locator_ID(),
//								getM_Product_ID(), 
//								asi.get_ID(), 
//								getMovementQty(), parent.getMovementDate(),
//								get_TrxName())) {
//			log.log(Level.SEVERE, "Could not create or update storage for " + toString());
//			errorString.append("Could not save transaction for " + toString() + "\n");
//		}
//		log.log(Level.FINE, "Created finished goods line " + getLine());
//
//		MTransaction matTrx = new MTransaction(getCtx(), getAD_Org_ID(), MTransaction.MOVEMENTTYPE_ProductionPlus, 
//											   getM_Locator_ID(), getM_Product_ID(), asi.get_ID(),
//											   getMovementQty(), date, get_TrxName());
//		matTrx.setUNS_Production_Detail_ID(get_ID());
//		if (!matTrx.save(get_TrxName())) {
//			log.log(Level.SEVERE, "Could not save transaction for " + toString());
//			errorString.append("Could not save transaction for " + toString() + "\n");
//		}
//		return errorString.toString();
//	}
//
//	// create transactions and update stock used in production (raw material)
//	BigDecimal qtyToMove = getMovementQty().negate();
//	
//	MStorageOnHand[] storages = null;
//	if(null != sourceList)
//	{
//		ArrayList<MStorageOnHand> list = new ArrayList<MStorageOnHand>();
//		for(int i=0; i<=sourceList.length; i++)
//		{
//			int loc_id = MStorageOnHand.getM_Locator_ID(MWarehouse.getForOrg(getCtx(), 
//														getAD_Org_ID())[0].get_ID(), getM_Product_ID(), 
//														sourceList[i].getM_AttributeSetInstance_ID(), 
//														sourceList[i].getQtyUom(), get_TrxName());
//			/** add storage on hand with QAStatus @author YAKA */ 
//			list.add(MStorageOnHand.get(getCtx(), loc_id, getM_Product_ID(), 
//										sourceList[i].getM_AttributeSetInstance_ID(), 
//										get_TrxName()/*, MStorageOnHand.PoorQAStatus, false*/));
//		}
//		MStorageOnHand[] soh = new MStorageOnHand[list.size()];
//		storages = list.toArray(soh);
//	} 
//	else {
//		storages = MStorageOnHand.getAll(getCtx(), getM_Product_ID(), getM_Locator_ID(), get_TrxName());
//	}
//	
//	if (storages == null || storages.length <=0) {
//		log.log(Level.SEVERE, "Could not complete production " + toString());
//		errorString.append("Could not complete production: no inventory available for " + 
//							getM_Product().getValue() + " at location-" + getM_Locator().getValue() + ".\n");
//	}
//	
//	MUNSProductionDetailMA lineMA = null;
//	// Remove all MA of this production detail first.
//	String sql = "DELETE FROM UNS_Production_DetailMA WHERE UNS_Production_Detail_ID=" + get_ID();
//	DB.executeUpdateEx(sql, get_TrxName());
//	
//	MTransaction matTrx = null;
//
//	for (int sl = 0; sl < storages.length; sl++) 
//	{
//		MStorageOnHand storage = storages[sl];
//
//		BigDecimal lineQty = storage.getQtyOnHand();
//		//TODO QA STATUS
////			if (MUNSProduction.PSTYPE_Reprocess.equals(getParent().getPSType()))
//////				if (MStorageOnHand.QASTATUS_NonConformance.equals(storage.getQAStatus())
//////					|| MStorageOnHand.QASTATUS_Release.equals(storage.getQAStatus()))
//////					lineQty = storage.getNCQty();
//////				else
//////					lineQty = Env.ZERO;
////				lineQty = storage.getNCQty();
////			else 
////				lineQty = storage.getReleaseQty();
//		
//		log.log(Level.FINE, "QtyAvailable " + lineQty);
//		if (lineQty.signum() > 0) 
//		{
//			if (lineQty.compareTo(qtyToMove) > 0)
//				lineQty = qtyToMove;
//
//			MAttributeSetInstance slASI = 
//					new MAttributeSetInstance(getCtx(), 
//											storage.getM_AttributeSetInstance_ID(), 
//											get_TrxName());
//
//			String slASIString = slASI.getDescription();
//			if (slASIString == null)
//				slASIString = "";
//
//			log.log(Level.FINEST, "slASI-Description =" + slASIString);
//
//			if (slASIString.compareTo(asiString) == 0 || asi.getM_AttributeSet_ID() == 0)
//			// storage matches specified ASI or is a costing asi (inc. 0)
//			// This process will move negative stock on hand quantities
//			{
//				lineMA = MUNSProductionDetailMA.get(this, storage.getM_AttributeSetInstance_ID());
//				
//				lineMA.setMovementQty(lineMA.getMovementQty().add(lineQty.negate()));
//				
//				if (!lineMA.save(get_TrxName())) {
//					log.log(Level.SEVERE, "Could not save MA for " + toString());
//					errorString.append("Could not save MA for " + toString() + "\n");
//				} else
//					log.log(Level.FINE, "Saved MA for " + toString());
//				
////					String QAStatusToSync = MStorageOnHand.QASTATUS_Release;
////					if (MUNSProduction.PSTYPE_Reprocess.equals(getUNS_Production().getPSType()))
////						QAStatusToSync = MStorageOnHand.QASTATUS_NonConformance;
//				
//				//storages[sl].changeQtyOnHand(lineQty, false);
//				if (!MStorageOnHand.add(getCtx(), 
//										storage.getM_Locator().getM_Locator_ID(), 
//										storage.getM_Locator_ID(), 
//										storage.getM_Product_ID(), 
//										storage.getM_AttributeSetInstance_ID(), 
//										lineQty.negate(), /*QAStatusToSync, false */ null, get_TrxName())) {
//					log.log(Level.SEVERE, "Could not update storage for " + toString());
//					errorString.append("Could not update storage for " + toString() + "\n");
//				}
////					// Just try to synchronize pallet info.
////					PalletHelper.moveOut(getCtx(), 
////										 (MProduct) getM_Product(), 
////										 getM_Locator_ID(), 
////										 storage.getM_AttributeSetInstance_ID(), 
////										 lineQty, QAStatusToSync, get_TrxName());
//				
//				matTrx = new MTransaction(getCtx(), getAD_Org_ID(), MTransaction.MOVEMENTTYPE_Production_,
//										  getM_Locator_ID(), getM_Product_ID(), slASI.get_ID(),
//										  lineQty.negate(), date, get_TrxName());
//				matTrx.setUNS_Production_Detail_ID(get_ID());
//				
//				if (!matTrx.save(get_TrxName())) {
//					log.log(Level.SEVERE, "Could not save transaction for " + toString());
//					errorString.append("Could not save transaction for " + toString() + "\n");
//				} else
//					log.log(Level.FINE, "Saved transaction for " + toString());
//				qtyToMove = qtyToMove.subtract(lineQty);
//				log.log(Level.FINE, getLine() + " Qty moved = " + lineQty + ", Remaining = " + qtyToMove);
//			}
//		}
//
//		if (qtyToMove.signum() == 0)
//			break;
//
//	} // for available storages
//	
//	if (!(qtyToMove.signum() == 0)) {
//		if (mustBeStocked) {
//			MLocator loc = new MLocator(getCtx(), getM_Locator_ID(),
//					get_TrxName());
//			errorString.append("Insufficient qty on hand of " + prod.toString() + " at " + loc.toString() + "\n");
//		} else {
//			// In PSG and or in Proper production processes, this mechanism is disallowed.
//			// So just return the error msg.
//			//errorString.append("Insufficient qty on hand of " + prod.toString() + " at " + loc.toString() + "\n");
//			//return errorString;
//			MStorageOnHand storage = MStorageOnHand.get(Env.getCtx(), getM_Locator_ID(), 
//														getM_Product_ID(), 0, get_TrxName());
//			if (storage == null) {
//				storage = new MStorageOnHand(Env.getCtx(), 0, get_TrxName());
//				storage.setM_Locator_ID(getM_Locator_ID());
//				storage.setM_Product_ID(getM_Product_ID());
//				storage.setM_AttributeSetInstance_ID(0);
//				storage.saveEx();
//			}
//
//			BigDecimal lineQty = qtyToMove;
//			MAttributeSetInstance slASI = 
//					new MAttributeSetInstance(getCtx(), storage.getM_AttributeSetInstance_ID(), get_TrxName());
//			String slASIString = slASI.getDescription();
//			if (slASIString == null)
//				slASIString = "";
//
//			log.log(Level.FINEST, "slASI-Description =" + slASIString);
//
//			if (slASIString.compareTo(asiString) == 0 || asi.getM_AttributeSet_ID() == 0)
//			// storage matches specified ASI or is a costing asi (inc. 0)
//			// This process will move negative stock on hand quantities
//			{
//				// lineMA = new MUNSProductionDetailMA( this,
//				// storage.getM_AttributeSetInstance_ID(),
//				// lineQty.negate());
//				lineMA = MUNSProductionDetailMA.get(this, storage.getM_AttributeSetInstance_ID());
//				lineMA.setMovementQty(lineMA.getMovementQty().add(lineQty.negate()));
//
//				if (!lineMA.save(get_TrxName())) {
//					log.log(Level.SEVERE, "Could not save MA for " + toString());
//					errorString.append("Could not save MA for " + toString() + "\n");
//				} else
//					log.log(Level.FINE, "Saved MA for " + toString());
//				
//				matTrx = new MTransaction(getCtx(), getAD_Org_ID(), "P-",
//										getM_Locator_ID(), getM_Product_ID(), asi.get_ID(),
//										lineQty.negate(), date, get_TrxName());
//				matTrx.setUNS_Production_Detail_ID(get_ID());
//
//				if (!matTrx.save(get_TrxName())) {
//					log.log(Level.SEVERE, "Could not save transaction for " + toString());
//					errorString.append("Could not save transaction for " + toString() + "\n");
//				} else
//					log.log(Level.FINE, "Saved transaction for " + toString());
//				
//				storage.changeQtyOnHand(lineQty, false);
//				if (!storage.save(get_TrxName())) {
//					log.log(Level.SEVERE, "Could not update storage for " + toString());
//					errorString.append("Could not update storage for " + toString() + "\n");
//				}
//				qtyToMove = qtyToMove.subtract(lineQty);
//				log.log(Level.FINE, getLine() + " Qty moved = " + lineQty + ", Remaining = " + qtyToMove);
//			}
//		}
//	}
//	return errorString.toString();
//}

//public String createTransactions(Timestamp date, boolean mustBeStocked, MUNSSrcProductDetail[] sourceList) 
//{
//	if (parent == null)
//		parent = (MUNSProduction) getUNS_Production();
//	
//	// delete existing ASI records
//	int deleted = deleteMA();
//	log.log(Level.FINE, "Deleted " + deleted + " attribute records ");
//	MProduct prod = new MProduct(getCtx(), getM_Product_ID(), get_TrxName());
//	log.log(Level.FINE, "Loaded Product " + prod.toString());
//
//	if (prod.getProductType().compareTo(MProduct.PRODUCTTYPE_Item) != 0) {
//		// no need to do any movements
//		log.log(Level.FINE, "Production Line " + getLine()
//				+ " does not require stock movement");
//		return "";
//	}
//	StringBuilder errorString = new StringBuilder();
//
//	//if getMovementQty is zero, not create MA 
//	if (getMovementQty().intValue()==0)
//		return errorString.toString();
//
//	//Create output storage.
//	//if (getMovementQty().compareTo(Env.ZERO) > 0)
//	if (isEndProduct())
//	{	
//		if (!isReversalLine())
//		{
//			String sql = "UPDATE M_AttributeSetInstance SET Created=?";
//			DB.executeUpdateEx(sql, new Object[]{date}, get_TrxName());
//			
//			MUNSProductionDetailMA lineMA = 
//					new MUNSProductionDetailMA(this, getM_AttributeSetInstance_ID(), getMovementQty());
//			
//			if (!lineMA.save(get_TrxName())) {
//				log.log(Level.SEVERE, "Could not save MA for " + toString());
//				errorString.append("Could not save MA for " + toString() + "\n");
//			}
//		}
//		else { // jika ini adalah reversal line, maka cek apakah storage tersebut masih ada.
//			BigDecimal qtyOnHand = 
//					MStorageOnHand.getQtyOnHandForLocator(
//							getM_Product_ID(), 
//							getM_Locator_ID(), 
//							getM_AttributeSetInstance_ID(), 
//							get_TrxName());
//			if (qtyOnHand.compareTo(getMovementQty().abs()) < 0)
//			{
//				errorString.append("Could not finalized the reversal process.\n "
//						+ "The stock previously produced somehow has been used by other transaction.\n "
//						+ "You need to reverse it manually.");
//			}
//		}
//		
//		//TODO QA STATUS
////		MUNSResourceInOut rio = getParent().getResource().getResourceOutput(getM_Product_ID());
////		String QAStatus = "-";
////		if (rio != null && rio.isQAMonitoring()) 
////		{
////			MProductCategory prodCat = (MProductCategory)getM_Product().getM_Product_Category();
////			//QAStatus = prodCat.getInitialQAStatus();
////		}
////		boolean possibleCreatePallet = prod.isStockedOnPallet();
//		
//		if (!MStorageOnHand.add(getCtx(), 
//								getM_Locator().getM_Warehouse_ID(), 
//								getM_Locator_ID(),
//								getM_Product_ID(), 
//								getM_AttributeSetInstance_ID(), 
//								getMovementQty(), null, //parent.getMovementDate(),
//								get_TrxName())) {
//			log.log(Level.SEVERE, "Could not create or update storage for " + toString());
//			errorString.append("Could not save transaction for " + toString() + "\n");
//		}
//		log.log(Level.FINE, "Created finished goods line " + getLine());
//
//		MTransaction matTrx = new MTransaction(
//				getCtx(), getAD_Org_ID(), MTransaction.MOVEMENTTYPE_ProductionPlus, 
//				getM_Locator_ID(), getM_Product_ID(), getM_AttributeSetInstance_ID(),
//				getMovementQty(), date, get_TrxName());
//		matTrx.setUNS_Production_Detail_ID(get_ID());
//		if (!matTrx.save(get_TrxName())) {
//			log.log(Level.SEVERE, "Could not save transaction for " + toString());
//			errorString.append("Could not save transaction for " + toString() + "\n");
//		}
//		return errorString.toString();
//	}
//
//	// create transactions and update stock used in production (raw material)
//	BigDecimal qtyToMove = getMovementQty().negate();
//	
//	MTransaction mTrx = null;
//	
//	if (getM_AttributeSetInstance_ID() == 0)
//	{
//		if (!isReversalLine())
//			errorString.append( createMALines(sourceList) );
//		
//		if (!errorString.toString().trim().isEmpty())
//			return errorString.toString();
//		
//		MUNSProductionDetailMA[] maLines = getMALines();
//		
//		for (MUNSProductionDetailMA ma : maLines)
//		{
//			if (!MStorageOnHand.add(getCtx(), 
//					getM_Locator().getM_Warehouse_ID(), 
//					getM_Locator_ID(), 
//					getM_Product_ID(), 
//					ma.getM_AttributeSetInstance_ID(), 
//					ma.getMovementQty(), null, get_TrxName())) 
//			{
//				log.log(Level.SEVERE, "Could not update storage for " + toString());
//				errorString.append("Could not update storage for " + toString() + "\n");
//			}
//			
//			mTrx = new MTransaction(getCtx(), getAD_Org_ID(), MTransaction.MOVEMENTTYPE_Production_,
//									  getM_Locator_ID(), getM_Product_ID(), 
//									  ma.getM_AttributeSetInstance_ID(), ma.getMovementQty().negate(), 
//									  date, get_TrxName());
//			mTrx.setUNS_Production_Detail_ID(get_ID());
//			
//			if (!mTrx.save(get_TrxName())) {
//				log.log(Level.SEVERE, "Could not save transaction for " + toString());
//				errorString.append("Could not save transaction for " + toString() + "\n");
//			} else
//				log.log(Level.FINE, "Saved transaction for " + toString());
//			
//			qtyToMove = qtyToMove.subtract(ma.getMovementQty().negate());
//		}
//	}
//	
//	if (mTrx == null)
//	{
//		if (!MStorageOnHand.add(getCtx(), 
//				getM_Locator().getM_Warehouse_ID(), 
//				getM_Locator_ID(), 
//				getM_Product_ID(), 
//				getM_AttributeSetInstance_ID(), 
//				getMovementQty(), null, get_TrxName())) 
//		{
//			log.log(Level.SEVERE, "Could not update storage for " + toString());
//			errorString.append("Could not update storage for " + toString() + "\n");
//		}
//		
//		mTrx = new MTransaction(getCtx(), getAD_Org_ID(), MTransaction.MOVEMENTTYPE_Production_,
//								  getM_Locator_ID(), getM_Product_ID(), 
//								  getM_AttributeSetInstance_ID(), getMovementQty(), 
//								  date, get_TrxName());
//		mTrx.setUNS_Production_Detail_ID(get_ID());
//		
//		if (!mTrx.save(get_TrxName())) {
//			log.log(Level.SEVERE, "Could not save transaction for " + toString());
//			errorString.append("Could not save transaction for " + toString() + "\n");
//		} else
//			log.log(Level.FINE, "Saved transaction for " + toString());
//		
//		qtyToMove = qtyToMove.subtract(getMovementQty().negate());
//	}
//
//	if (qtyToMove.signum() != 0) 
//	{
//		MLocator loc = new MLocator(getCtx(), getM_Locator_ID(),
//				get_TrxName());
//		errorString.append("Insufficient qty on hand of " + prod.toString() + " at " + loc.toString() + "\n");
//	}
//
//	return errorString.toString();
//}
//