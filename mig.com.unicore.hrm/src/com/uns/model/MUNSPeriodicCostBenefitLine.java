/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
//import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSPeriodicCostBenefitLine extends X_UNS_PeriodicCostBenefitLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8053469072628125735L;
	
	private MUNSPeriodicCostBenefit m_periodCost = null;

	/**
	 * @param ctx
	 * @param UNS_PeriodicCostBenefitLine_ID
	 * @param trxName
	 */
	public MUNSPeriodicCostBenefitLine(Properties ctx,
			int UNS_PeriodicCostBenefitLine_ID, String trxName) {
		super(ctx, UNS_PeriodicCostBenefitLine_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPeriodicCostBenefitLine(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSPeriodicCostBenefitLine (MUNSPeriodicCostBenefit parent) {
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setUNS_PeriodicCostBenefit_ID(parent.get_ID());
	}
	
	@Override
	protected boolean beforeSave (boolean newRecord) {
		
		if(getParent().getC_Period_ID() > 0)
		{
			String sql = "SELECT 1 FROM UNS_PeriodicCostBenefitLine pcbl"
					+ " INNER JOIN UNS_PeriodicCostBenefit pcb"
					+ " ON pcb.UNS_PeriodicCostBenefit_ID = pcbl.UNS_PeriodicCostBenefit_ID"
					+ " WHERE pcb.C_Year_ID = ? AND pcb.C_Period_ID = ? AND pcb.CostBenefitType = ?"
					+ " AND pcbl.UNS_Employee_ID = ? AND pcbl.UNS_PeriodicCostBenefitLine_ID <> ?";
			boolean exists = DB.getSQLValue(
					get_TrxName(), sql, getParent().getC_Year_ID(),getParent().getC_Period_ID(),
					getParent().getCostBenefitType(), getUNS_Employee_ID(), getUNS_PeriodicCostBenefitLine_ID()) == 1;
			if(exists)
			{
				log.saveError("SaveError", "Disallowed duplicate employee in one period with Cost Benefit Type is same");
				return false;
			}
		}
		
		if (!isProcessed()) {
			String sql = "SELECT COALESCE (PrevAmount, 0) FROM UNS_PeriodicCostBenefitLine pcl INNER JOIN "
					+ " UNS_PeriodicCostBenefit pc ON pc.UNS_PeriodicCostBenefit_ID = pcl.UNS_PeriodicCostBenefit_ID "
					+ " AND pc.DateTo < ? AND pc.CostBenefitType = ? AND pc.DocStatus IN ('CO','CL') "
					+ " WHERE UNS_Employee_ID = ? AND pcl.UNS_PeriodicCostBenefitLine_ID <> ? AND pcl.IsActive = ? "
					+ " ORDER BY pc.DateTo Desc ";
			BigDecimal prevAmount = DB.getSQLValueBD(get_TrxName(), sql, getParent().getDateFrom(), 
					getParent().getCostBenefitType(), getUNS_Employee_ID(), 
					getUNS_PeriodicCostBenefitLine_ID(), "Y");
			if (prevAmount == null)
				prevAmount = Env.ZERO;
			setPrevAmount(prevAmount);
		}
		
		if (getPaidAmt().compareTo(getAmount()) == 1) {
			log.saveError("SaveError", "Paid amount could not greather than Amount");
			return false;
		}
		
		BigDecimal remainingAmt = (getPrevAmount().add(getAmount())).subtract(getPaidAmt());
		setRemainingAmount(remainingAmt);
		return super.beforeSave(newRecord);
	}
	
	public static MUNSPeriodicCostBenefitLine getCurrentEmplloye (int employeeID, String type, String trxName) {
		String wc = " UNS_Employee_ID = ?";
		String jc = " INNER JOIN UNS_PeriodicCostBenefit pcb ON pcb.UNS_PeriodicCostBenefit_ID = UNS_PeriodicCostBenefitLine."
				+ "UNS_PeriodicCostBenefit_ID AND pcb.DocStatus IN ('CO','CL') AND pcb.CostBenefitType = ? ";
		MUNSPeriodicCostBenefitLine result = Query.get(
				Env.getCtx(), UNSHRMModelFactory.EXTENSION_ID, Table_Name, wc, trxName).
				addJoinClause(jc).setParameters(type, employeeID).setOrderBy("pcb.DateTo").
				first();
		return result;
	}
	
//	public static MUNSPeriodicCostBenefitLine[] getProcessedEmployee (int employeeID, String type, String trxName) {
//		List<MUNSPeriodicCostBenefitLine> list = Query.get(
//				Env.getCtx(), UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
//				COLUMNNAME_UNS_Employee_ID + "= ? AND RemainingAmount != 0 AND processed = 'Y' AND EXISTS "
//						+ " (SELECT IsActive FROM UNS_PeriodicCostBenefit WHERE UNS_PeriodicCostBenefit_ID = "
//						+ " UNS_PeriodicCostBenefitLine.UNS_PeriodicCostBenefit_ID AND CostBenefitType = ?)", 
//				trxName).setParameters(employeeID, type).list();
//		MUNSPeriodicCostBenefitLine[] result = new MUNSPeriodicCostBenefitLine[list.size()];
//		list.toArray(result);
//		return result;
//	}
	
	public static boolean pay (int employeeID, BigDecimal amount, String type, String trxName) {
		MUNSPeriodicCostBenefitLine line = getCurrentEmplloye(employeeID, type, trxName);
		if (line == null)
			return true;
		DB.getDatabase().forUpdate(line, 30);
		if (amount.signum() == 0)
			return true;
		BigDecimal remaining = line.getRemainingAmount();
		BigDecimal payAmt = amount;
		if (payAmt.compareTo(remaining) == 1) {
			payAmt = remaining;
		} 
		line.setPaidAmt(payAmt);
		if (!line.save())
			return false;
		amount = amount.subtract(payAmt);
		return true;
	}
	
	@Override
	protected boolean afterSave (boolean newRecord, boolean success) {
		
		updateHeader();
		
		return super.afterSave(newRecord, success);
	}
	
	@Override
	protected boolean afterDelete(boolean success) {
		
		updateHeader();
		
		return super.afterDelete(success);
	}
	
	protected void updateHeader() {
		
		String sql = "SELECT COALESCE(SUM(Amount),0) FROM UNS_PeriodicCostBenefitLine WHERE isActive = ?"
				+ " AND UNS_PeriodicCostBenefit_ID = ?";
		BigDecimal totalAmt = DB.getSQLValueBD(get_TrxName(), sql, "Y", getUNS_PeriodicCostBenefit_ID());
		
		//update header
		sql = "UPDATE UNS_PeriodicCostBenefit SET TotalAmt = "+totalAmt
				+ " WHERE UNS_PeriodicCostBenefit_ID = ?";
		boolean ok = DB.executeUpdate(sql, getUNS_PeriodicCostBenefit_ID(), get_TrxName()) != -1;
		if(!ok)
			throw new AdempiereException("SQL Error. Cannot update header");
	}
	
	public MUNSPeriodicCostBenefit getParent() {
		
		if(m_periodCost != null)
			return m_periodCost;
		
		m_periodCost = (MUNSPeriodicCostBenefit) getUNS_PeriodicCostBenefit();
		return m_periodCost;
	}
}
