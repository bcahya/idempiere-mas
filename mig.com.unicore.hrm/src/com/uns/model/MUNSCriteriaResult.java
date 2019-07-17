/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author menjangan
 *
 */
public class MUNSCriteriaResult extends X_UNS_CriteriaResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1637962074013261494L;

	/**
	 * @param ctx
	 * @param UNS_CriteriaResult_ID
	 * @param trxName
	 */
	public MUNSCriteriaResult(Properties ctx, int UNS_CriteriaResult_ID,
			String trxName) {
		super(ctx, UNS_CriteriaResult_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCriteriaResult(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		/*
		MUNSPayRollLine CriteriaLine = new MUNSPayRollLine(getCtx(), getUNS_PayRollLine_ID(), get_TrxName());
		MUNSPayRollPremiTarget criteriaTarget = 
				CriteriaLine.getCriteriaTarget(getResult());
		if(null != criteriaTarget)
			setResultAmt(criteriaTarget.getPay());
			*/
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		return super.afterSave(newRecord, success);
	}
	
	public MUNSHalfPeriodPresence getParent()
	{
		return (MUNSHalfPeriodPresence)getUNS_HalfPeriod_Presence();
	}
}
