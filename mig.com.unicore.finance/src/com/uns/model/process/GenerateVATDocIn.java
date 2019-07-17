/**
 * 
 */
package com.uns.model.process;

import java.util.List;
import org.compiere.model.MInvoice;
import org.compiere.process.SvrProcess;

import com.unicore.model.MUNSVATDocInOut;
import com.uns.base.model.Query;

/**
 * @author ALBURHANY
 *
 */
public class GenerateVATDocIn extends SvrProcess {

	/**
	 * 
	 */
	public GenerateVATDocIn() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	
	protected void prepare()
	{
	
	}
	
	protected String doIt() throws Exception 
	{
		String whereClause = "DocumentNo = '1000369' AND isSOTrx = 'N' AND C_Invoice_ID NOT IN (SELECT C_Invoice_ID FROM UNS_VATDocInOut) "
				+ " AND C_BPartner_ID IN (SELECT C_BPartner_ID FROM C_BPartner WHERE isPKP = 'Y')"
				+ " AND AD_Org_ID IN (SELECT AD_Org_ID FROM AD_OrgInfo WHERE OrgTax_ID IN"
				+ " (SELECT C_Tax_ID FROM C_Tax WHERE Name = 'PPN'))";
		
		List<MInvoice> invList = 
				new Query(getCtx(), MInvoice.Table_Name, whereClause, get_TrxName())
				.list();
		
		for (MInvoice inv : invList)
		{
			MUNSVATDocInOut VATDoc = new MUNSVATDocInOut(getCtx(), 0, get_TrxName());
			
			VATDoc.setC_Invoice_ID(inv.get_ID());
			VATDoc.setAD_Org_ID(inv.getAD_Org_ID());
			VATDoc.setDateInvoiced(inv.getDateInvoiced());
			VATDoc.setC_BPartner_ID(inv.getC_BPartner_ID());
			VATDoc.setDescription("Generate From Invoice : " + inv.getDocumentNo());
			VATDoc.setIsSOTrx(false);
			VATDoc.setPrintedBy(inv.getPrintedBy());
			VATDoc.setPrintedDate(inv.getDatePrinted());
			VATDoc.saveEx();
		}	
		
		return "Success Generate " + invList.size() + " Invoice (vendor)";
	}
}