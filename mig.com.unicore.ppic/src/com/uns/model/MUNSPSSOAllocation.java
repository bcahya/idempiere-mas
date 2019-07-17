/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import com.uns.base.model.MOrderLine;


/**
 * @author YAKA
 *
 */
public class MUNSPSSOAllocation extends X_UNS_PSSOAllocation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5111790168621491240L;

	/**
	 * @param ctx
	 * @param UNS_PSSOAllocation_ID
	 * @param trxName
	 */
	public MUNSPSSOAllocation(Properties ctx, int UNS_PSSOAllocation_ID,
			String trxName) {
		super(ctx, UNS_PSSOAllocation_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPSSOAllocation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static BigDecimal getUnAllocationQty(MOrderLine ol, MUNSProductionSchedule ps, Properties ctx, String trxName) {
		BigDecimal unAllocation = ol.getQtyEntered();
		
		for (MUNSPSSOAllocation real : ol.getAllocation()){
			unAllocation = ps.getQtyUom().subtract(real.getQtyUom());
		}
		
		return unAllocation;
	}
	
	public static String createSOAllocation(MOrderLine m_orderline, MUNSProductionSchedule m_ps, Properties ctx, String trxName) {
		MUNSPSSOAllocation new_soallocation = new MUNSPSSOAllocation(ctx, 0, trxName);
		BigDecimal unAllocation = MUNSPSSOAllocation.getUnAllocationQty(m_orderline, m_ps, ctx, trxName);
		BigDecimal qty_real = Env.ZERO;
		int compare = unAllocation.compareTo(m_ps.getQtyUom());
		if (compare<0)
			qty_real = unAllocation;
		else
			qty_real = m_ps.getQtyUom();
		
		new_soallocation.setQtyUom(qty_real);
		BigDecimal MTProduct = m_ps.getSERLineProduct().getM_Product().getWeight().multiply(BigDecimal.valueOf(0.0001));
		new_soallocation.setQtyMT(new_soallocation.getQtyUom().multiply(MTProduct));
		new_soallocation.setUNS_ProductionSchedule_ID(m_ps.get_ID());
		new_soallocation.setC_OrderLine_ID(m_orderline.get_ID());
		new_soallocation.setClientOrg(m_ps);
		
		if (!new_soallocation.save()){
			throw new AdempiereException("Error when create realization");
		}
	//TODO SO Allocation
//		MOrderLine setOL = new MOrderLine(ctx, m_orderline.get_ID(), trxName);
//		setOL.setQtyAllocated(setOL.getQtyAllocated().add(qty_real));
//		setOL.saveEx();
		
		if (compare<0)
			return "SO Allocation created. SO Line still have unallocation";
		else if (compare>0)
			return "SO Allocation created. Manufacturing Order " + m_ps.getDocumentNo()+" still have unallocation";
		else
			return "SO Allocation created. ";
	}
	
	@Override
	protected boolean beforeDelete() {
//		MOrderLine setOL = new MOrderLine(getCtx(), getC_OrderLine_ID(), get_TrxName());
		//TODO SO Allocation
//		setOL.setQtyAllocated(setOL.getQtyAllocated().subtract(getQtyUom()));
//		setOL.saveEx();
		
		return super.beforeDelete();
	}
	
	public MUNSProductionSchedule getParent(){
		return new MUNSProductionSchedule(getCtx(), getUNS_ProductionSchedule_ID(), get_TrxName());
	}
}
