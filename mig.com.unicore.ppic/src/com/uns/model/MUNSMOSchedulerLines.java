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
public class MUNSMOSchedulerLines extends X_UNS_MO_SchedulerLines {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_MO_SchedulerLines_ID
	 * @param trxName
	 */
	public MUNSMOSchedulerLines(Properties ctx, int UNS_MO_SchedulerLines_ID,
			String trxName) {
		super(ctx, UNS_MO_SchedulerLines_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMOSchedulerLines(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSMOScheduler getParent()
	{
		return new MUNSMOScheduler(getCtx(), getUNS_MO_Scheduler_ID(), get_TrxName());
	}

}
