/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/**
 * @author root
 *
 */
public class MUNSSalesTargetDetail extends X_UNS_SalesTargetDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4994761169578153506L;
	private MUNSSalesTargetPeriodic m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_SalesTargetDetail_ID
	 * @param trxName
	 */
	public MUNSSalesTargetDetail(Properties ctx, int UNS_SalesTargetDetail_ID,
			String trxName) {
		super(ctx, UNS_SalesTargetDetail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSalesTargetDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSSalesTargetDetail(MUNSSalesTargetPeriodic parent)
	{
		super(parent.getCtx(), 0, parent.get_TrxName());
		m_parent = parent;
		setUNS_SalesTargetPeriodic_ID(parent.get_ID());
		setAD_Org_ID(parent.getAD_Org_ID());
	}
	
	public MUNSSalesTargetPeriodic getParent()
	{
		if(null != m_parent)
			return m_parent;
		
		m_parent = new MUNSSalesTargetPeriodic(getCtx(), getUNS_SalesTargetPeriodic_ID(), get_TrxName());
		
		return m_parent;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		BigDecimal targetValue = getParent().getValueTarget();
		BigDecimal topTarget = getParent().getTOPTarget();
		BigDecimal valueRate = Env.ZERO;
		BigDecimal topRate = Env.ZERO;
		
		if(targetValue.signum() != 0)
			valueRate = getAmtAchieved().multiply(Env.ONEHUNDRED).divide(targetValue, 0, RoundingMode.HALF_DOWN);
		if(topTarget.signum() != 0)
			topRate = getTOPRate().multiply(Env.ONEHUNDRED).divide(topTarget, 0, RoundingMode.HALF_UP);
		
		if(getAmtAchieved().compareTo(targetValue) == 1)
			setIncentiveAmt(getParent().getIncentive());
		else if(getTOPRate().compareTo(getParent().getTOPTarget()) == 1)
			setIncentiveAmt(getParent().getIncentive());
		else
			setDeduction(getParent().getDeduction());
		
		setPercentAmt(valueRate);
		setPercentRate(topRate);
		return super.beforeSave(newRecord);
	}

}
