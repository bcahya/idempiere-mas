/**
 * 
 */
package com.unicore.model.factory;

import java.sql.ResultSet;

import org.compiere.model.PO;
import org.compiere.util.Env;

import com.uns.base.UNSAbstractModelFactory;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MMovement;
import com.unicore.base.model.MMovementConfirm;
import com.unicore.base.model.MMovementLine;

/**
 * @author MENJANGAN
 *
 */
public class UniCoreMaterialManagementModelFactory extends
		UNSAbstractModelFactory {
	
	
	public static final String EXTENSION_ID = "UniCoreMaterialManagementModelFactory";

	/**
	 * 
	 */
	public UniCoreMaterialManagementModelFactory() {
		super();
	}
	
	@Override
	public PO getPO(String tableName, int record_id, String trxName)
	{
		PO po = null;
		if(MMovement.Table_Name.equals(tableName))
			po = new MMovement(Env.getCtx(), record_id, trxName);
		else if(MMovementConfirm.Table_Name.equals(tableName))
			po = new MMovementConfirm(Env.getCtx(), record_id, trxName);
		else if(MMovementLine.Table_Name.equals(tableName))
			po = new MMovementLine(Env.getCtx(), record_id, trxName);
		else if(MInvoice.Table_Name.equals(tableName))
			po = new MInvoice(Env.getCtx(), record_id, trxName);
		else
			po = super.getPO(tableName, record_id, trxName);
		
		
		return po;
	}
	
	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName)
	{
		PO po = null;
		if(MMovement.Table_Name.equals(tableName))
			po = new MMovement(Env.getCtx(), rs, trxName);
		else if(MMovementConfirm.Table_Name.equals(tableName))
			po = new MMovementConfirm(Env.getCtx(), rs, trxName);
		else if(MMovementLine.Table_Name.equals(tableName))
			po = new MMovementLine(Env.getCtx(), rs, trxName);
		else if(MInvoice.Table_Name.equals(tableName))
			po = new MInvoice(Env.getCtx(), rs, trxName);
		else
			po = super.getPO(tableName, rs, trxName);
		
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
