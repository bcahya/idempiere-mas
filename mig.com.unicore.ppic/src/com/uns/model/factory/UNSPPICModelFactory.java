/**
 * 
 */
package com.uns.model.factory;

import java.sql.ResultSet;

import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.uns.base.UNSAbstractModelFactory;

import com.uns.base.model.MCostDetail;
import com.uns.base.model.MInOut;
import com.uns.base.model.MOrder;
import com.uns.base.model.MOrderLine;

/**
 * @author menjangan
 *
 */
public class UNSPPICModelFactory extends UNSAbstractModelFactory {
	
	private static final String EXTENSION_HANDLER = "UNSPPICModelFactory";

	/**
	 * 
	 */
	public UNSPPICModelFactory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/* (non-Javadoc)
	 * @see org.adempiere.base.IModelFactory#getClass(java.lang.String)
	 */
	@Override
	public Class<?> getClass(String tableName) 
	{
		if (tableName == null || tableName.endsWith("_Trl"))
			return null;

		//Try to get it from super class first.
		Class<?> clazz = super.getClass(tableName);

		if (clazz != null)
			return clazz;

		String className = tableName;
		className = Util.replace(className, "_", "");

		// First, try if it is from com.uns.qad.model of the QAD features.		
		StringBuffer name = new StringBuffer("com.uns.qad.model").append(".M").append(className);

		clazz = getPOclass(name.toString(), tableName);
		if (clazz != null)
		{
			s_classCache.put(tableName, clazz);
			return clazz;
		}

		// It is possible doesn't have M model, then try to get the default from com.uns.model for the X model type.
		name = new StringBuffer("com.uns.qad.model").append(".X_").append(tableName);
		clazz = getPOclass(name.toString(), tableName);
		if (clazz != null)
		{
			s_classCache.put(tableName, clazz);
			return clazz;
		}		
		return null;
	}

	
	public PO getPO(String tableName, int recordID, String trxName)
	{
		PO po = null;
		
		if (MOrderLine.Table_Name.equals(tableName))
		{
			po = new MOrderLine(Env.getCtx(), recordID, trxName);
		}
		else if (MOrder.Table_Name.equals(tableName))
		{
			po = new MOrder(Env.getCtx(), recordID, trxName);
		}
		else if (MCostDetail.Table_Name.equals(tableName))
		{
			po = new MCostDetail(Env.getCtx(), recordID, trxName);
		}
		else if(MInOut.Table_Name.equals(tableName))
		{
			po = new MInOut(Env.getCtx(), recordID, trxName);
		}
		else 
		{
			po = super.getPO(tableName, recordID, trxName);
		}
		return po;
	}
	
	public PO getPO(String tableName, ResultSet rs, String trxName)
	{
		PO po = null;
		
		if (MOrderLine.Table_Name.equals(tableName))
		{
			po = new MOrderLine(Env.getCtx(), rs, trxName);
		}
		else if (MOrder.Table_Name.equals(tableName))
		{
			po = new MOrder(Env.getCtx(), rs, trxName);
		}
		else if (MCostDetail.Table_Name.equals(tableName))
		{
			po = new MCostDetail(Env.getCtx(), rs, trxName);
		}
		else if(MInOut.Table_Name.equals(tableName))
		{
			po = new MInOut(Env.getCtx(), rs, trxName);
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
		// TODO Auto-generated method stub
		return clazz;
	}
	
	public static String getExtensionID()
	{
		String extensionID = EXTENSION_HANDLER;
		return extensionID;
	}

}
