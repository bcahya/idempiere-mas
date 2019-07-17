/**
 * 
 */
package com.uns.model.process;

import java.sql.Timestamp;
import java.util.List;

import org.compiere.model.MInvoice;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.unicore.model.MUNSBillingGroup;
import com.unicore.model.MUNSVATDocInOut;
import com.unicore.model.MUNSVATDocNo;
import com.uns.base.model.Query;

/**
 * @author ALBURHANY
 *
 */
public class ProcessPrintFakturPajak extends SvrProcess {

	/**
	 * 
	 */
	public ProcessPrintFakturPajak() {
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
		createVATDocNo();
		return null;
	}
	
	private int newVAT(int AD_Org_ID)
	{
		StringBuffer sql = new StringBuffer
				("SELECT UNS_VATDocNo_ID FROM UNS_VATDocNo WHERE UsageStatus <> 'U'"
						+ " AND UNS_VATDocNo_ID NOT IN (SELECT UNS_VATDocNo_ID FROM UNS_VATDocInOut)"
						+ " AND AD_Org_ID IN (SELECT Parent_Org_ID FROM AD_OrgInfo WHERE AD_Org_ID = " + AD_Org_ID + ")"
						+ " ORDER BY SequenceUsedNo");
		int idVAT = DB.getSQLValue(get_TrxName(), sql.toString());
		
		if(idVAT <= -1)
		{
//			throw new AdempiereException("No VAT not used");
		}
		
		return idVAT;
	}
	
	private int oldVAT(int C_Invoice_ID)
	{
		String sql = "SELECT UNS_VATDocNo_ID FROM C_InvoiceTax WHERE C_Invoice_ID = " + C_Invoice_ID;
		int idVat = DB.getSQLValue(get_TrxName(), sql);
		
		return idVat;
	}
	
	private String createVATDocNo() throws Exception
	{
		MUNSBillingGroup billing = new MUNSBillingGroup(getCtx(), getRecord_ID(), get_TrxName());
		
		String whereClause = "C_Invoice_ID IN (SELECT C_Invoice_ID FROM UNS_BillingLine"
				+ " WHERE UNS_Billing_ID IN (SELECT UNS_Billing_ID FROM UNS_Billing"
				+ " WHERE isActive = 'Y' AND UNS_BillingGroup_ID = " + billing.getUNS_BillingGroup_ID() + "))";
		
		List<MInvoice> invList = 
				new Query(getCtx(), MInvoice.Table_Name, whereClause, get_TrxName())
				.list();
		
		for (MInvoice inv : invList)
		{			
			MOrg org = new MOrg(getCtx(), inv.getAD_Org_ID(), get_TrxName());
			MOrgInfo orgInfo = MOrgInfo.get(getCtx(), org.get_ID(), false, get_TrxName());
			MUNSVATDocInOut docVAT = new MUNSVATDocInOut(getCtx(), 0, get_TrxName());
			
			String sql = "SELECT C_Invoice_ID FROM UNS_VATDocInOut WHERE isSOTrx = 'Y' AND C_Invoice_ID = " + inv.get_ID();
			
			if(DB.getSQLValue(get_TrxName(), sql) >= 1)
				continue;
			
			if(!inv.getC_BPartner().isPKP())
				continue;
			
			if(!orgInfo.getOrgTax().getC_TaxCategory().getName().equals("PPN"))
				continue;
				
			if(inv.getM_RMA_ID() == 0 && newVAT(inv.getAD_Org_ID()) <= -1)
				continue;
			
			java.util.Date date = new java.util.Date();
			inv.setPrintedBy(getProcessInfo().getAD_User_ID());
			inv.setIsPrinted(true);
			inv.setDatePrinted(new Timestamp(date.getTime()));
			inv.saveEx();
						
			if(inv.getM_RMA_ID() != 0)
			{		
				String sqlSO = "SELECT C_Invoice_ID FROM C_Invoice WHERE C_Order_ID = (SELECT C_Order_ID FROM M_InOut"
						+ " WHERE M_InOut_ID = (SELECT M_InOut FROM M_RMA WHERE M_RMA_ID = " + inv.getM_RMA_ID() + "))";
				int idOldInv = DB.getSQLValue(get_TrxName(), sqlSO);
				
				docVAT.setisReplacement(true);
				docVAT.setAD_Org_ID(inv.getAD_Org_ID());
				docVAT.setC_Invoice_ID(inv.getC_Invoice_ID());
				docVAT.setC_BPartner_ID(inv.getC_BPartner_ID());
				docVAT.setPrintedBy(inv.getPrintedBy());
				docVAT.setPrintedDate(inv.getDatePrinted());
				docVAT.setIsPrinted(true);
				docVAT.setIsSOTrx(true);
				docVAT.setUNS_VATDocNo_ID(oldVAT(idOldInv));
				docVAT.processIt(DocAction.ACTION_Complete);
				docVAT.saveEx();
				
				MUNSVATDocNo docNo = new MUNSVATDocNo(getCtx(),oldVAT(inv.get_ID()), get_TrxName());
				docNo.setUsageStatus("U");
				docNo.saveEx();
			}
			
			if(inv.getM_RMA_ID() == 0)
			{
				docVAT.setAD_Org_ID(inv.getAD_Org_ID());
				docVAT.setC_Invoice_ID(inv.getC_Invoice_ID());
				docVAT.setC_BPartner_ID(inv.getC_BPartner_ID());
				docVAT.setPrintedBy(inv.getPrintedBy());
				docVAT.setPrintedDate(inv.getDatePrinted());
				docVAT.setIsPrinted(true);
				docVAT.setIsSOTrx(true);
				docVAT.setUNS_VATDocNo_ID(newVAT(inv.getAD_Org_ID()));
				docVAT.processIt(DocAction.ACTION_Complete);
				docVAT.saveEx();
				
				MUNSVATDocNo docNo = new MUNSVATDocNo(getCtx(), newVAT(inv.getAD_Org_ID()), get_TrxName());
				docNo.setUsageStatus("U");
				docNo.saveEx();
			}
		}
		return null;
	}
}