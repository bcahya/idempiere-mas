package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.PO;

public class MUNSBCNumberTransaction extends X_UNS_BCNumber_Transaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3168992330692996349L;

	public MUNSBCNumberTransaction(Properties ctx,
			int UNS_BCNumber_Transaction_ID, String trxName) {
		super(ctx, UNS_BCNumber_Transaction_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSBCNumberTransaction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static boolean createTranscation(MUNSBCNumberGeneratorLine line, PO po){
		MUNSBCNumberTransaction transaction = new MUNSBCNumberTransaction(line.getCtx(),
				0, line.get_TrxName());
		transaction.setClientOrg(line);
		transaction.setUNS_BCNumber_GeneratorLine_ID(line.get_ID());
		if(po.get_TableName().equals(MInOut.Table_Name))
			transaction.setM_InOut_ID(po.get_ID());
		else if (po.get_TableName().equals(MInvoice.Table_Name))
			transaction.setC_Invoice_ID(po.get_ID());
		else if (po.get_TableName().equals("UNS_PackingSlip"))
			transaction.setUNS_PackingSlip_ID(po.get_ID());
		else 
			return false;
		
		transaction.saveEx();
		
		return true;
	}

}
