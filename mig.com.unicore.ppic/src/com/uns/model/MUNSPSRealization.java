/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author YAKA
 *
 */
public class MUNSPSRealization extends X_UNS_PSRealization {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5946117075105047651L;

	/**
	 * @param ctx
	 * @param UNS_PSRealization_ID
	 * @param trxName
	 */
	public MUNSPSRealization(Properties ctx, int UNS_PSRealization_ID,
			String trxName) {
		super(ctx, UNS_PSRealization_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPSRealization(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	public static BigDecimal getUnRealizationQty(
			MUNSProductionSchedule ps, MUNSProductionDetail pd, Properties ctx, String trxName) 
	{
		BigDecimal unRealization = pd.getMovementQty();
		
		for (MUNSPSRealization real : ps.getLinesReal()){
			unRealization = ps.getQtyUom().subtract(real.getQtyUom());
		}
		
		return unRealization;
	}

	public MUNSProductionSchedule getParent() {
		return new MUNSProductionSchedule(getCtx(), getUNS_ProductionSchedule_ID(), get_TrxName());
	}
	
	/**
	 * 
	 * @param trxName
	 * @param UNS_PD_ID
	 * @return MUNSPSRealization
	 */
	public static MUNSPSRealization get(Properties ctx, String trxName, int UNS_PD_ID)
	{
		String sql = "SELECT UNS_PSRealization_ID FROM UNS_PSRealization WHERE UNS_Production_Detail_ID=?";
		int log = DB.getSQLValue(trxName, sql, UNS_PD_ID);
		if (log==-1)
			return null;
		return new MUNSPSRealization(ctx, log, trxName);
	}
	
	public boolean updateMPS()
	{
		int UNS_MPSProductWeekly_ID = getParent().getUNS_MPSProductRsc_Weekly_ID();
		if(UNS_MPSProductWeekly_ID == 0)
			return false;
		MUNSMPSProductWeekly mpsProductWeekly = new MUNSMPSProductWeekly(
				getCtx(), UNS_MPSProductWeekly_ID, get_TrxName());
		mpsProductWeekly.setProjectedActualBalance(getQtyUom());
		mpsProductWeekly.save();
		return true;
	}
	
	/**
	 * 
	 * @param m_ps
	 * @param m_pd
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public static String createRealization(
			MUNSProductionSchedule m_ps, MUNSProductionDetail m_pd, Properties ctx, String trxName)
	{
		MUNSPSRealization new_real = new MUNSPSRealization(ctx, 0, trxName);

		new_real.setClientOrg(m_pd);
		new_real.setQtyUom(m_pd.getMovementQty());
		BigDecimal MTProduct = m_pd.getM_Product().getWeight().multiply(BigDecimal.valueOf(0.001));
		new_real.setQtyMT(new_real.getQtyUom().multiply(MTProduct));
		new_real.setRealization_PD(m_pd.getParent().getMovementDate());
		new_real.setUNS_Production_Detail_ID(m_pd.get_ID());
		new_real.setUNS_ProductionSchedule_ID(m_ps.get_ID());
		
		if (!new_real.save()){
			throw new AdempiereException("Error when create Production Schedule realization.");
		}
		m_ps.setQtyManufactured(m_ps.getQtyManufactured().add(new_real.getQtyUom()));
		
		if (!m_ps.save()){
			throw new AdempiereException("Error when updating qty realization of Production Schedule.");
		}
		return "Realization created, Production Scheduled updated.";
	}


	/**
	 * 
	 * @param m_ps
	 * @param m_pd
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public static String createRealization_Old(
			MUNSProductionSchedule m_ps, MUNSProductionDetail m_pd, Properties ctx, String trxName)
	{
		MUNSPSRealization new_real = new MUNSPSRealization(ctx, 0, trxName);
		BigDecimal unRealization = MUNSPSRealization.getUnRealizationQty(m_ps, m_pd, ctx, trxName);
		BigDecimal qty_pd = m_pd.getMovementQty();
		BigDecimal qty_real = Env.ZERO;
		int compare = unRealization.compareTo(qty_pd);
		if (compare<0)
			qty_real = unRealization;
		else
			qty_real = qty_pd;
		
		new_real.setClientOrg(m_pd);
		new_real.setQtyUom(qty_real);
		BigDecimal MTProduct = m_pd.getM_Product().getWeight().multiply(BigDecimal.valueOf(0.0001));
		new_real.setQtyMT(new_real.getQtyUom().multiply(MTProduct));
		new_real.setRealization_PD(m_pd.getParent().getMovementDate());
		new_real.setUNS_Production_Detail_ID(m_pd.get_ID());
		new_real.setUNS_ProductionSchedule_ID(m_ps.get_ID());
		
		if (!new_real.save()){
			throw new AdempiereException("Error when create realization");
		}
		
		if (compare<0)
			return "Realization created. Production Detail still have unrealization";
		else if (compare>0)
			return "Realization created. Manufacturing Order still have unrealization";
		else
			return "Realization created.";
	}
}
