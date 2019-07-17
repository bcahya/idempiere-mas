package com.uns.model.factory;

import java.sql.ResultSet;

import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.PO;
import org.compiere.util.Env;

import com.uns.base.UNSAbstractModelFactory;

import com.unicore.base.model.MBankStatementLine;
import com.uns.base.model.MInvoice;
import com.uns.base.model.MInvoiceLine;

/**
 * Model factory
 * 
 * @author YAKA
 */
public class UNSFinanceModelFactory extends UNSAbstractModelFactory {

	/**
	 * The extension ID reference.
	 */
	public static final String EXTENSION_ID = "UNSFinanceModelFactory";

	/**
	 * 
	 */
	public UNSFinanceModelFactory() {
		super();
	}


	@Override
	public PO getPO(String tableName, int Record_ID, String trxName) {
		PO po = null;
		if (MInvoice.Table_Name.equals(tableName))
		{
			po = new MInvoice(Env.getCtx(), Record_ID, trxName);
		}
		else if (MInvoiceLine.Table_Name.equals(tableName))
		{
			po = new MInvoiceLine(Env.getCtx(), Record_ID, trxName);
		}
		else if (MPayment.Table_Name.equals(tableName))
		{
			po = new MPayment(Env.getCtx(), Record_ID, trxName);
		}
		else if (MPaymentAllocate.Table_Name.equals(tableName))
		{
			po = new MPaymentAllocate(Env.getCtx(), Record_ID, trxName);
		}
		else if (MPaymentAllocate.Table_Name.equals(tableName))
		{
			po = new MBankStatementLine(Env.getCtx(), Record_ID, trxName);
		}
		else
		{
			po = super.getPO(tableName, Record_ID, trxName);
		}
		return po;
	}

	@Override
	public PO getPO(String tableName, ResultSet rs, String trxName) {
		PO po = null;
		if (MInvoice.Table_Name.equals(tableName))
		{
			po = new MInvoice(Env.getCtx(), rs, trxName);
		}
		else if (MInvoiceLine.Table_Name.equals(tableName))
		{
			po = new MInvoiceLine(Env.getCtx(), rs, trxName);
		}
		else if (MPayment.Table_Name.equals(tableName))
		{
			po = new MPayment(Env.getCtx(), rs, trxName);
		}
		else if (MPaymentAllocate.Table_Name.equals(tableName))
		{
			po = new MPaymentAllocate(Env.getCtx(), rs, trxName);
		}
		else if (MPaymentAllocate.Table_Name.equals(tableName))
		{
			po = new MBankStatementLine(Env.getCtx(), rs, trxName);
		}
		else
		{
			po = super.getPO(tableName, rs, trxName);
		}
		return po;
	}


	@Override
	protected Class<?> getInternalClass(String className) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(className);
		// if (clazz == null)
		// clazz = super.getInternalClass(className);
		return clazz;
	}
}
