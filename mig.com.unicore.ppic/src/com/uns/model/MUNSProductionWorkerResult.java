/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

import com.uns.model.MProduct;

/**
 * @author YAKA
 *
 */
public class MUNSProductionWorkerResult extends X_UNS_Production_WorkerResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2679115193551951539L;
	public boolean m_isFormModification = false;
	public boolean m_SpecialSave = false;
	private MUNSProductionWorker m_parent;

	/**
	 * @param ctx
	 * @param UNS_Production_WorkerResult_ID
	 * @param trxName
	 */
	public MUNSProductionWorkerResult(Properties ctx,
			int UNS_Production_WorkerResult_ID, String trxName) {
		super(ctx, UNS_Production_WorkerResult_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSProductionWorkerResult(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSProductionWorkerResult(MUNSProductionWorker header) 
	{
		super(header.getCtx(), 0, header.get_TrxName());

		m_parent= header;
		
		setClientOrg(header);
		setUNS_Production_Worker_ID(header.get_ID());
	}
	
	@Override
	public MUNSProductionWorker getParent() {
		if (m_parent == null || m_parent.get_ID() == 0)
			m_parent = new MUNSProductionWorker(getCtx(), getUNS_Production_Worker_ID(), get_TrxName());
		
		return m_parent;
	}
	
//	public static boolean generateWorkerResult(Properties ctx, String trxName,
//			MUNSProductionWorker worker, BigDecimal resultProportion) {
//
//		// products used in production
//		MUNSProduction production = new MUNSProduction(ctx, worker.getUNS_Production_ID(), trxName);
//		MUNSProductionWorkerResult pWorkerResult = new MUNSProductionWorkerResult(worker);
//		
//		setWOResult (pWorkerResult, 
//				production.isPersonalResult(), 
//				production.getM_Product_ID(), 
//				production.getProductionQty(), 
//				resultProportion, false);
//		
//		if (!pWorkerResult.save())
//			return false;
//		
//		return true;
//	}
	
//	private static void setWOResult(MUNSProductionWorkerResult pWorkerResult, boolean personalResult, 
//			int M_Product_ID, BigDecimal productionQty,	BigDecimal resultProportion, boolean isPrimePortion) {
//		pWorkerResult.setM_Product_ID(M_Product_ID);
//		//int precision = MProduct.get(pWorkerResult.getCtx(), M_Product_ID).getC_UOM().getStdPrecision();
//		int precision = MUOM.getPrecision(pWorkerResult.getCtx(), MProduct.get(pWorkerResult.getCtx(), M_Product_ID).getC_UOM_ID());
//		if(personalResult)
//			pWorkerResult.setProductionQty(Env.ZERO);
//		else if (!isPrimePortion)
//		{
//			String sql = "SELECT COUNT(*) FROM UNS_Production_Worker"
//					+ " WHERE UNS_Production_ID=" + pWorkerResult.getParent().getUNS_Production_ID()
//					+ " ";
//			BigDecimal workerCount = DB.getSQLValueBD(pWorkerResult.get_TrxName(), sql);
//			
//			pWorkerResult.setProductionQty(productionQty
//					.divide(workerCount, 20, BigDecimal.ROUND_HALF_UP)
//					.setScale(precision, RoundingMode.HALF_DOWN));
//		} 
//		else 
//		{
//			pWorkerResult.setProductionQty(productionQty
//					.multiply(resultProportion.divide(Env.ONEHUNDRED, 20, BigDecimal.ROUND_HALF_UP))
//					.setScale(precision, RoundingMode.HALF_DOWN));
//		}
//	}

//	public static boolean generateWorkerResultFROM(Properties ctx, String trxName,
//			MUNSProductionWorker worker, BigDecimal resultProportion , MUNSMP1FormDetail formDetail) {
//
//		MUNSMP1Form mp1Form = formDetail.getMP1Form();
//		MUNSProduction production = new MUNSProduction(ctx, worker.getUNS_Production_ID(), trxName);
//		MUNSJobRole jobRole = new MUNSJobRole(ctx, worker.getUNS_Job_Role_ID(), trxName);
//		
//		for (MUNSProductionDetail detail : production.getLines()) {
//			if (!detail.isEndProduct())
//				continue;
//
//			MUNSProductionWorkerResult WOrs = new MUNSProductionWorkerResult(worker);
//			WOrs.m_SpecialSave = true;
//
//			if ((UNSApps.getRefAsInt(UNSApps.PRD_CWBS) == detail.getM_Product_ID()
//					|| UNSApps.getRefAsInt(UNSApps.PRD_RWM) == detail.getM_Product_ID()
//					|| UNSApps.getRefAsInt(UNSApps.PRD_KCL) == detail.getM_Product_ID()
//					|| UNSApps.getRefAsInt(UNSApps.PRD_PRJ) == detail.getM_Product_ID())
//					&& mp1Form.isKBPecahSegar()) 
//			{
//				continue;
//			} 
//			else if (UNSApps.getRefAsInt(UNSApps.PRD_WMBW) != detail.getM_Product_ID()
//					&& mp1Form.isKBPecahSegar() && jobRole.getName().equalsIgnoreCase("Parrer")) 
//			{
//				continue;
//			}
//			// Force to detail value
//			else {
//				setWOResult (WOrs,	false, 
//						detail.getM_Product_ID(), 
//						detail.getMovementQty(), resultProportion, false);
//			}
//
//			WOrs.setM_Product_ID(detail.getM_Product_ID());
//			
//			if (!WOrs.save())
//				return false;
//
//		}
//
//		return true;
//	}
	
//	public static boolean generateWorkerResultMULTI(Properties ctx, String trxName,
//			MUNSProductionWorker worker, BigDecimal resultProportion, boolean isPrimePortion) {
//
//		MUNSProduction production = new MUNSProduction(ctx, worker.getUNS_Production_ID(), trxName);
//		MUNSProductionOutPlan[] productionOPlans = production.getOutputs();
//		for (MUNSProductionOutPlan productionOPlan : productionOPlans) {
//			MUNSProductionWorkerResult pWorkerResult = new MUNSProductionWorkerResult(worker);
//			pWorkerResult.setM_Product_ID(productionOPlan.getM_Product_ID());
////			setWOResult (pWorkerResult, 
////					production.isPersonalResult(), 
////					productionOPlan.getM_Product_ID(), 
////					productionOPlan.getQtyPlan(), 
////					resultProportion, isPrimePortion);
//			
//			if (!pWorkerResult.save())
//				return false;
//			}
//		
//		return true;
//	}
	
	public boolean updateOutPlan() 
	{
		MUNSProductionWorker pwo = 
				new MUNSProductionWorker(getCtx(), getUNS_Production_Worker_ID(), get_TrxName());
		MUNSProduction p = new MUNSProduction(getCtx(), pwo.getUNS_Production_ID(), get_TrxName());
		
		if (p.isPersonalResult())
		{
		  String sql = "SELECT SUM(pwr.productionqty) FROM UNS_Production_WorkerResult pwr "
			+ "INNER JOIN UNS_Production_Worker pwo ON pwr.UNS_Production_Worker_ID=pwo.UNS_Production_Worker_ID "
			+ "WHERE pwo.UNS_Production_ID="+p.getUNS_Production_ID();
		  BigDecimal productionQty = DB.getSQLValueBD(get_TrxName(), sql);
		
		  for (MUNSProductionDetail pd : p.getLines())
		  {
			if (getM_Product_ID()==pd.getM_Product_ID())
			{
				pd.setPlannedQty(pd.getPlannedQty().add(productionQty));
				pd.setMovementQty(pd.getMovementQty().add(productionQty));
				pd.saveEx();
			}
		  }
		}
		return true;
	}
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		MUNSProduction production = getParent().getParent();
		
		if (!production.isPersonalResult())
			return true;
		
		StringBuilder sql = 
				new StringBuilder("SELECT SUM(pwr.ProductionQty) FROM UNS_Production_WorkerResult pwr ")
				.append("WHERE pwr.M_Product_ID=? AND pwr.UNS_Production_Worker_ID IN ")
				.append("	(SELECT pw.UNS_Production_Worker_ID FROM UNS_Production_Worker pw ")
				.append("	 WHERE pw.UNS_Production_ID=?)");
		
		BigDecimal totalProductionQty =
				DB.getSQLValueBD(get_TrxName(), sql.toString(), getM_Product_ID(), production.get_ID());
		
		sql = new StringBuilder("SELECT COUNT(pd.*) FROM UNS_Production_Detail pd ")
				.append(" WHERE pd.M_Product_ID=? AND pd.UNS_Production_ID=?");
		int countPD = DB.getSQLValue(get_TrxName(), sql.toString(), getM_Product_ID(), production.get_ID());
		
		BigDecimal avgProductionQty = totalProductionQty;
		
		if (countPD > 1) {
			int precision = MProduct.get(getCtx(), getM_Product_ID()).getUOMPrecision();
			avgProductionQty = totalProductionQty.divide(BigDecimal.valueOf(countPD), precision, BigDecimal.ROUND_HALF_UP);
		}
		
		sql = new StringBuilder("UPDATE UNS_Production_Detail SET MovementQty=?, QtyUsed=? ")
				.append("WHERE UNS_Production_ID=? AND M_Product_ID=? AND IsEndProduct='Y'");
		
		int count = 
				DB.executeUpdateEx(sql.toString(), 
						new Object[]{avgProductionQty, avgProductionQty, production.get_ID(), getM_Product_ID()}, 
						get_TrxName());
		
//		if (!m_SpecialSave && production.isWorkerBase() &&  isConsiderOutput())
//			return updateOutPlan();
		
		return count > 0;
	}	//	afterSave
}
