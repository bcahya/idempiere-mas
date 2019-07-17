/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/**
 * @author menjangan
 *
 */
public class MUNSLoadingCostWorker extends X_UNS_LoadingCost_Worker {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2799463782760460156L;

	/**
	 * @param ctx
	 * @param UNS_LoadingCost_Worker_ID
	 * @param trxName
	 */
	public MUNSLoadingCostWorker(Properties ctx, int UNS_LoadingCost_Worker_ID,
			String trxName) {
		super(ctx, UNS_LoadingCost_Worker_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLoadingCostWorker(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSLoadingCost getParent()
	{
		return (MUNSLoadingCost)getUNS_LoadingCost();
	}
	
	public boolean afterSave(boolean newRecord, boolean success)
	{
		int labor_ID = getLabor_ID();
		if(getReplacementLabor_ID() > 0)
			labor_ID = getReplacementLabor_ID();
		
		MUNSResource rsc = new MUNSResource(getCtx(), getParent().getUNS_Resource_ID(), get_TrxName());
		for(MUNSResource rscWorker : rsc.getResourceWorker())
		{
			MUNSResourceWorkerLine workerOfRsc = rscWorker.getWorker(labor_ID);
			if(null != workerOfRsc)
				continue;
			
			MUNSEmployee employee = new MUNSEmployee(getCtx(), getLabor_ID(), get_TrxName());
			workerOfRsc = new MUNSResourceWorkerLine(getCtx(), 0, get_TrxName());
			workerOfRsc.setAD_Org_ID(getAD_Org_ID());
			workerOfRsc.setPayrollTerm(employee.getPayrollTerm());
			workerOfRsc.setIsPrimePortion(false);
			workerOfRsc.setLabor_ID(getLabor_ID());
			workerOfRsc.setResultProportion(Env.ZERO);
			workerOfRsc.setUNS_Job_Role_ID(getUNS_Job_Role_ID());
			workerOfRsc.setUNS_Resource_ID(getParent().getUNS_Resource_ID());//TODO
			workerOfRsc.setIsAdditional(true);
			workerOfRsc.save();
		}
		return super.afterSave(newRecord, success);
	}

}
