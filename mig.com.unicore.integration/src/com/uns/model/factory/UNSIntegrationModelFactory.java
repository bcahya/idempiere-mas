/**
 * 
 */
package com.uns.model.factory;

import java.sql.ResultSet;

import org.compiere.model.PO;
import org.compiere.util.Env;

import com.unicore.model.MUNSPackingList;
import com.uns.base.UNSAbstractModelFactory;

import com.uns.model.MUNSCriteriaResult;
import com.uns.model.override.MUNSHalfPeriodPresence;
import com.uns.model.override.MUNSLeavePermissionTrx;
import com.uns.model.override.MUNSWorkerPresence;

/**
 * @author menjangan
 *
 */
public class UNSIntegrationModelFactory extends UNSAbstractModelFactory {

	
	public static final String EXTENSION_ID = "UNSIntegrationModelFactory";
	/**
	 * 
	 */
	public UNSIntegrationModelFactory() {
	}
	
	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) 
	{
		PO po = null;
	
		if (MUNSHalfPeriodPresence.Table_Name.equals(tableName))
		{
			po = new MUNSHalfPeriodPresence(Env.getCtx(), Record_ID, trxName);
		}
		else if (MUNSWorkerPresence.Table_Name.equals(tableName))
		{
			po = new MUNSWorkerPresence(Env.getCtx(), Record_ID, trxName);
		}
		else if(MUNSCriteriaResult.Table_Name.equals(tableName))
		{
			po = new MUNSCriteriaResult(Env.getCtx(), Record_ID, trxName);
		}
		else if(MUNSLeavePermissionTrx.Table_Name.equals(tableName))
		{
			po = new MUNSLeavePermissionTrx(Env.getCtx(), Record_ID, trxName);
		}
		else if (MUNSPackingList.Table_Name.equals(tableName))
		{
			po = new MUNSPackingList(Env.getCtx(), Record_ID, trxName);
		}
		else
		{
			po = super.getPO(tableName, Record_ID, trxName);
		}
		return po;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) 
	{
		PO po = null;
		if (MUNSHalfPeriodPresence.Table_Name.equals(tableName))
		{
			po = new MUNSHalfPeriodPresence(Env.getCtx(), rs, trxName);
		}
		else if (MUNSWorkerPresence.Table_Name.equals(tableName))
		{
			po = new MUNSWorkerPresence(Env.getCtx(), rs, trxName);
		}
		else if(MUNSCriteriaResult.Table_Name.equals(tableName))
		{
			po = new MUNSCriteriaResult(Env.getCtx(), rs, trxName);
		}
		else if(MUNSLeavePermissionTrx.Table_Name.equals(tableName))
		{
			po = new MUNSLeavePermissionTrx(Env.getCtx(), rs, trxName);
		}
		else if (MUNSPackingList.Table_Name.equals(tableName))
		{
			po = new MUNSPackingList(Env.getCtx(), rs, trxName);
		} 
		else
		{
			po = super.getPO(tableName, rs, trxName);
		}
		return po;
	}

	/* (non-Javadoc)
	 * @see com.uns.base.UNSAbstractModelFactory#getInternalClass(java.lang.String)
	 */
	@Override
	protected Class<?> getInternalClass(String className)
			throws ClassNotFoundException {
		Class<?> clazz = Class.forName(className);
		return clazz;
	}

}
