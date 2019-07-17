/**
 * 
 */
package com.unicore.model.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author HandiWindu
 *
 */

public class SetInvoiceOpenAmt extends SvrProcess {

	/**
	 * 
	 */
	public SetInvoiceOpenAmt() {
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
		DB.executeUpdate("UPDATE UNS_BillingLine bl SET NetAmtToInvoice = InvoiceOpen(C_Invoice_ID,0) "
				+ "WHERE EXISTS (SELECT 1 FROM UNS_Billing b WHERE b.UNS_Billing_ID = bl.UNS_Billing_ID AND b.UNS_BillingGroup_ID =?)",
								getRecord_ID(), false, get_TrxName());
							
		DB.executeUpdate("UPDATE UNS_Billing b SET TotalAmt= (SELECT COALESCE (SUM (NetAmtToInvoice),0)"
				+ " FROM UNS_BillingLine bl WHERE bl.UNS_Billing_ID=b.UNS_Billing_ID AND b.UNS_BillingGroup_ID =?)"
				+ " WHERE b.UNS_BillingGroup_ID =?",
						new Object[]{getRecord_ID(), getRecord_ID()}, false, get_TrxName());
		
		DB.executeUpdate("UPDATE UNS_BillingGroup SET GrandTotal= (SELECT COALESCE (SUM (TotalAmt),0) "
				+ "FROM UNS_Billing WHERE UNS_Billing.UNS_BillingGroup_ID=UNS_BillingGroup.UNS_BillingGroup_ID) WHERE UNS_BillingGroup_ID=?",
						getRecord_ID(), false, get_TrxName());
						
		return "Success";
		
	}



}
