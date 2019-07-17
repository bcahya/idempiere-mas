/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;

import com.uns.util.ErrorMsg;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author YAKA
 *
 */
public class MUNSResourceWorkerLine extends X_UNS_Resource_WorkerLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7389835350424871845L;

	/**
	 * @param ctx
	 * @param UNS_Resource_WorkerLine_ID
	 * @param trxName
	 */
	public MUNSResourceWorkerLine(Properties ctx,
			int UNS_Resource_WorkerLine_ID, String trxName) {
		super(ctx, UNS_Resource_WorkerLine_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSResourceWorkerLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean sucess)
	{
		X_UNS_Employee worker = new X_UNS_Employee(getCtx(), getLabor_ID(), get_TrxName());
		I_UNS_Contract_Recommendation contract = worker.getUNS_Contract_Recommendation();
		
		if((MUNSContractRecommendation.NEXTCONTRACTTYPE_Borongan.equals(contract.getNextContractType())
					|| MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganCV.equals(contract.getNextContractType())
					|| MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarian.equals(contract.getNextContractType())
					|| MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarianCV.equals(contract.getNextContractType()))
					&& !isValidProportion())
		
//		if (EMPLOYMENTSTATUS_Borongan.equals(getEmploymentStatus()) && !isValidProportion())
			return false;
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isValidProportion()
	{
		BigDecimal totalPrimePortion = Env.ZERO;
		BigDecimal totalNonPrimePortion = Env.ZERO;
		
		for (MUNSResourceWorkerLine worker : getParent().getWorkerLines(true))
		{
			X_UNS_Employee pekerja = new X_UNS_Employee(getCtx(), worker.getLabor_ID(), get_TrxName());
			I_UNS_Contract_Recommendation contract = pekerja.getUNS_Contract_Recommendation();
			
			if((!MUNSContractRecommendation.NEXTCONTRACTTYPE_Borongan.equals(contract.getNextContractType())
					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganCV.equals(contract.getNextContractType())
					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarian.equals(contract.getNextContractType())
					&& !MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganHarianCV.equals(contract.getNextContractType()))
					|| worker.get_ID() == get_ID())
			
//			if (!EMPLOYMENTSTATUS_Borongan.equals(worker.getEmploymentStatus())
//				|| worker.get_ID() == get_ID())
				continue;
			
			if (worker.isPrimePortion())
				totalPrimePortion = totalPrimePortion.add(worker.getResultProportion());
			else
				totalNonPrimePortion  = totalNonPrimePortion.add(worker.getResultProportion());
		}
		totalPrimePortion = totalPrimePortion.setScale(4, BigDecimal.ROUND_HALF_DOWN);
		if (totalPrimePortion.compareTo(Env.ONEHUNDRED) > 0)
		{
			ErrorMsg.setErrorMsg(getCtx(),
					"SaveError", "Accumulation of Primary Result Proportion cannot greater than 100%");
			return false;
		}
		/*
		if (totalNonPrimePortion.compareTo(Env.ONEHUNDRED) > 0)
		{
			ErrorMsg.setErrorMsg(getCtx(), "SaveError", 
					"Accumulation of Non-Primary result proportion cannot greater than 100%");
			return false;
		}
		*/		
		return true;
	}
	
	@Override
	public MUNSResource getParent()
	{
		return (MUNSResource)getUNS_Resource();
	}
	
	/**
	 * Get Worker Of Departement
	 * @param ctx
	 * @param AD_Org_ID
	 * @param trxName
	 * @return
	 * @deprecated
	 */
	public static List<MUNSResourceWorkerLine>  getOrgWorkers(Properties ctx, int AD_Org_ID, String trxName)
	{
		return Query.get(
				ctx, UNSPPICModelFactory.getExtensionID(), Table_Name
				, COLUMNNAME_AD_Org_ID + " = " + AD_Org_ID, trxName)
				.list();
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		checkDuplicateWorker();
		return super.beforeSave(newRecord);
	}
	
	private void checkDuplicateWorker()
	{
		MUNSResource workerRsc = getParent();
		MUNSResourceWorkerLine[] workers = workerRsc.getWorkerLines(true);
		for(MUNSResourceWorkerLine worker : workers)
		{
			if(worker.get_ID() == get_ID())
				continue;
			if(worker.getLabor_ID() != getLabor_ID())
				continue;
			
			throw new AdempiereUserError("Duplicate Worker.");
		}
	}
	
	/**
	 * 
	 * @param ctx
	 * @param AD_Org_ID
	 * @param employeeID
	 * @param trxName
	 * @return
	 * @deprecated should not get it only by org, as a worker can be presenting multi org in resources.
	 */
	public static MUNSResourceWorkerLine getWorkerOfOrg(Properties ctx, int AD_Org_ID, int employeeID, String trxName)
	{
		for (MUNSResourceWorkerLine worker : getOrgWorkers(ctx, AD_Org_ID, trxName))
		{
			if(employeeID == worker.getLabor_ID())
				return worker;
		}
		return null;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param AD_Org_ID
	 * @param employeeID
	 * @param trxName
	 * @return
	 */
	public static MUNSResourceWorkerLine[] getNonAdditionalWorkerOf(
			Properties ctx, int employeeID, String trxName)
	{
		List<MUNSResourceWorkerLine> rscWorkerList =
				Query.get(ctx, UNSPPICModelFactory.getExtensionID(), MUNSResourceWorkerLine.Table_Name, 
						  "Labor_ID=? AND IsAdditional=?", trxName)
					 .setParameters(employeeID, 'N')
					 .list();
		
		MUNSResourceWorkerLine[] rscWorker = new MUNSResourceWorkerLine[rscWorkerList.size()];
		rscWorkerList.toArray(rscWorker);
		return rscWorker;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param AD_Org_ID
	 * @param employeeID
	 * @param trxName
	 * @return
	 */
	public static MUNSResourceWorkerLine getWorker(
			Properties ctx, int resourceId, int employeeID, String trxName)
	{
		String whereClause = "UNS_Resource_ID=? AND Labor_ID=?";

		MUNSResourceWorkerLine worker =
				Query.get(ctx, UNSPPICModelFactory.getExtensionID(), Table_Name, whereClause, trxName)
				.setParameters(resourceId, employeeID)
				.firstOnly();
		
		return worker;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSResource getWorkCenter()
	{
		return getWorkCenter((MUNSResource) getUNS_Resource());
	}
	
	/**
	 * 
	 * @param rsc
	 * @return
	 */
	private MUNSResource getWorkCenter(MUNSResource rsc)
	{
		if (rsc == null)
			return rsc;
		
		if (rsc.isWorkCenter())
			return rsc;
		
		return getWorkCenter((MUNSResource) rsc.getResourceParent());
	}	
}
