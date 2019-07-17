/**
 * 
 */
package com.unicore.model.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author Burhani Adam
 *
 */
public class VoidTaxInvoiceNo extends SvrProcess {

	/**
	 * 
	 */
	public VoidTaxInvoiceNo() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		DB.executeUpdate("UPDATE UNS_VATDocNo SET UsageStatus = 'VO'"
							+ " WHERE UNS_VATDocNo_ID = ?",
								getRecord_ID(), false, get_TrxName());
		return "Success";
	}
}
