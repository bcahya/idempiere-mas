/**
 * 
 */
package com.unicore.model.factory;

import java.sql.ResultSet;

import org.compiere.model.PO;
import org.compiere.util.Env;

import com.uns.base.UNSAbstractModelFactory;

import com.unicore.base.model.MInOut;
import com.unicore.base.model.MInOutLine;
import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;

/**
 * @author UNTA-Andy
 * 
 */
public class UNSOrderModelFactory extends UNSAbstractModelFactory
{

	public static final String EXTENSION_ID = "UNSOrderModelFactory";

	/**
	 * 
	 */
	public UNSOrderModelFactory()
	{
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) 
	{
		PO po = null;
		
		if (MOrder.Table_Name.equals(tableName)) {
			po = new MOrder(Env.getCtx(), Record_ID, trxName);
		} 
		else if (MOrderLine.Table_Name.equals(tableName)) {
			po = new MOrderLine(Env.getCtx(), Record_ID, trxName);
		} 
		else if(MInOut.Table_Name.equals(tableName))
		{
			po = new MInOut(Env.getCtx(), Record_ID, trxName);
		}
		else if(MInOutLine.Table_Name.equals(tableName))
		{
			po = new MInOutLine(Env.getCtx(), Record_ID, trxName);
		}
		else if(MInvoice.Table_Name.equals(tableName))
		{
			po = new MInvoice(Env.getCtx(), Record_ID, trxName);
		}
		else if(MInvoiceLine.Table_Name.equals(tableName))
		{
			po = new MInvoiceLine(Env.getCtx(), Record_ID, trxName);
		}
		else {
			po = super.getPO(tableName, Record_ID, trxName);
		}
		
		return po;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) 
	{
		PO po = null;

		if (MOrder.Table_Name.equals(tableName)) {
			po = new MOrder(Env.getCtx(), rs, trxName);
		} 
		else if (MOrderLine.Table_Name.equals(tableName)) {
			po = new MOrderLine(Env.getCtx(), rs, trxName);
		} 
		else if(MInOut.Table_Name.equals(tableName))
		{
			po = new MInOut(Env.getCtx(), rs, trxName);
		}
		else if(MInOutLine.Table_Name.equals(tableName))
		{
			po = new MInOutLine(Env.getCtx(), rs, trxName);
		}
		else if(MInvoice.Table_Name.equals(tableName))
		{
			po = new MInvoice(Env.getCtx(), rs, trxName);
		}
		else if(MInvoiceLine.Table_Name.equals(tableName))
		{
			po = new MInvoiceLine(Env.getCtx(), rs, trxName);
		}
		else {
			po = super.getPO(tableName, rs, trxName);
		}
		
		return po;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uns.base.UNSAbstractModelFactory#getInternalClass(java.lang.String)
	 */
	@Override
	protected Class<?> getInternalClass(String className) throws ClassNotFoundException
	{
		Class<?> clazz = Class.forName(className);
		return clazz;
	}

}
