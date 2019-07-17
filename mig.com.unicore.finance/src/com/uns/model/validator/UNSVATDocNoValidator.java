/**
 * 
 */
package com.uns.model.validator;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.DB;

import com.unicore.model.MUNSVATDocInOut;
import com.unicore.model.MUNSVATDocNo;

/**
 * @author ALBURHANY
 *
 */
public class UNSVATDocNoValidator implements ModelValidator {

	/**
	 * 
	 */
	public UNSVATDocNoValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		engine.addDocValidate(MInvoice.Table_Name, this);
	}

	@Override
	public int getAD_Client_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception {
		
		if(po.get_TableName().equals(MInvoiceTax.Table_Name))
		{
			if(type != TYPE_BEFORE_CHANGE)
				return null;
			
			MInvoiceTax invTax = (MInvoiceTax) po;
			
			String sql = "SELECT UNS_VatDocInOut_ID FROM UNS_VatDocInOut WHERE DocStatus IN ('CO', 'CL') AND"
					+ " C_Invoice_ID = " + invTax.getC_Invoice_ID();
			int idVat = DB.getSQLValue(po.get_TrxName(), sql); 
			
			if(idVat <= 0)
				idVat = -1;
			
			invTax.setUNS_VATDocInOut_ID(idVat);
			invTax.saveEx();
		}
		
		return null;
	}

	@Override
	public String docValidate(PO po, int timing) {

		if (po.get_TableName().equals(MInvoice.Table_Name) && timing == TIMING_BEFORE_PREPARE)
		{			
			if(po.get_ValueAsBoolean("isSOTrx"))
			{
				MInvoice inv = new MInvoice(po.getCtx(), po.get_ID(), po.get_TrxName());
				
				MOrg org = new MOrg(po.getCtx(), po.get_ValueAsInt("AD_Org_ID"), po.get_TrxName());
				MOrgInfo orgInfo = org.getInfo();
				
				if (!inv.getC_BPartner().isPKP() || !orgInfo.getOrgTax().getName().equals("PPN"))
					return null;
				
				StringBuffer vatSQL = new StringBuffer
						("SELECT UNS_VATDocNo_ID FROM UNS_VATDocNo WHERE UsageStatus <> 'U'");
				int noVAT = DB.getSQLValue(po.get_TrxName(), vatSQL.toString());
				
				if (!inv.getC_BPartner().isVATDocOnCreate() || noVAT <= 0)
					return null;
				
				StringBuffer idInvTax = new StringBuffer
						("SELECT C_InvoiceTax_ID FROM C_InvoiceTax WHERE C_Invoice_ID = " + po.get_ID());
				int idTax = DB.getSQLValue(po.get_TrxName(), idInvTax.toString());
				
				MInvoiceTax invTax = new MInvoiceTax(po.getCtx(), idTax, po.get_TrxName());
				invTax.setUNS_VATDocNo_ID(noVAT);
				invTax.saveEx();
				
				MUNSVATDocInOut vatOut = new MUNSVATDocInOut(po.getCtx(), 0, po.get_TrxName());
				
				vatOut.setAD_Org_ID(inv.getAD_Org_ID());
				vatOut.setUNS_VATDocNo_ID(noVAT);
				vatOut.setIsSOTrx(true);
				vatOut.setC_BPartner_ID(inv.getC_BPartner_ID());
				vatOut.setDateInvoiced(inv.getDateInvoiced());
				vatOut.saveEx();
			}
			
			if(!po.get_ValueAsBoolean("isSOTrx"))
			{
				MBPartner bpartner = new MBPartner(po.getCtx(), po.get_ValueAsInt("C_BPartner_ID"), po.get_TrxName());
				MOrg org = new MOrg(po.getCtx(), po.get_ValueAsInt("AD_Org_ID"), po.get_TrxName());
				MOrgInfo orgInfo = org.getInfo();
				
				if (bpartner.isPKP() && orgInfo.getOrgTax().getName().equals("PPN"))
				{
					StringBuffer sql = new StringBuffer
							("SELECT UNS_VATDocInOut_ID FROM UNS_VATDocInOut WHERE DocStatus IN ('CO', 'CL')"
									+ " AND isSOTrx = 'N' AND C_Invoice_ID = " + po.get_ID());
					
					if(DB.getSQLValue(po.get_TrxName(), sql.toString()) <= -1)
						throw new AdempiereException("Please create VAT document receival first");
				}
			}
		}
		
		if (po.get_TableName().equals(MInvoice.Table_Name) && timing == TIMING_AFTER_VOID)
		{
			if (po.get_ValueAsBoolean("isSOTrx"))
			{
				StringBuffer sql = new StringBuffer
						("SELECT UNS_VATDocInOut_ID FROM C_InvoiceTax"
								+ " WHERE C_Invoice_ID = " + po.get_ID());
				int idVATDoc = DB.getSQLValue(po.get_TrxName(), sql.toString());

				if (idVATDoc >= 1)
				{
					MUNSVATDocInOut vatInOut = new MUNSVATDocInOut(po.getCtx(), idVATDoc, po.get_TrxName());
					MUNSVATDocNo vatDoc = new MUNSVATDocNo(po.getCtx(), vatInOut.getUNS_VATDocNo_ID(), po.get_TrxName());
					vatDoc.setUsageStatus("C");
					vatDoc.saveEx();
				}
			}
			
			if(!po.get_ValueAsBoolean("isSOTrx"))
				return null;
		}
		return null;
	}
}