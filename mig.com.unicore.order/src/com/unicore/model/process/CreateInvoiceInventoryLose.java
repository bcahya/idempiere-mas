/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MCurrency;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

import com.unicore.model.MUNSBPCanvasser;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.model.MUNSCvsPhysical;
import com.unicore.model.MUNSCvsPhysicalProduct;
import com.unicore.model.SimplePrice;

/**
 * @author root
 *
 */
public class CreateInvoiceInventoryLose extends SvrProcess {
	
	private MUNSCvsPhysical m_model = null;

	/**
	 * 
	 */
	public CreateInvoiceInventoryLose() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		m_model = new MUNSCvsPhysical(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		if(m_model == null)
			throw new AdempiereException(" No Record ID");
		
		if(m_model.getC_Invoice_ID() > 0)
			return "Invoice has been generated";
		
		MUNSBPCanvasser cvsInfo = MUNSBPCanvasser.getOf(m_model.getC_BPartner_ID(), get_TrxName());
		if(null == cvsInfo)
			throw new AdempiereUserError("Canvasser Info is not set.");
		
		int AD_User_ID = DB.getSQLValue(
				get_TrxName(), "SELECT AD_User_ID FROM AD_User WHERE C_BPartner_ID = ?"
				, m_model.getC_BPartner_ID());
		if(AD_User_ID <= 0)
			throw new AdempiereUserError("User Of Canvasser is not set.");
		MCurrency cur = MCurrency.get(getCtx(), "IDR");
		
		int C_DocTypeInv_ID	= DB.getSQLValue(
				get_TrxName(), "SELECT C_DocType_ID FROM C_DocType WHERE DocSubTypeInv = 'ARI'");
		int M_PriceList_ID	= DB.getSQLValue(
				get_TrxName(), (new StringBuilder("SELECT o.M_PriceList_ID FROM C_Order o WHERE ")
				.append("o.C_BPartner_ID = ? AND o.DatePromised <= ? AND o.DatePromised = ")
				.append("(SELECT MAX(datePromised) FROM C_Order WHERE C_BPartner_ID = ")
				.append("o.C_BPartner_ID)")).toString(), m_model.getC_BPartner_ID(), m_model.getMovementDate());
		
		MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
		invoice.setAD_Org_ID(m_model.getAD_Org_ID());
		invoice.setAD_OrgTrx_ID(m_model.getAD_OrgTrx_ID());
		invoice.setBPartner((MBPartner)m_model. getC_BPartner());
		invoice.setC_Currency_ID(cur.get_ID());
		invoice.setC_DocType_ID(C_DocTypeInv_ID);
		invoice.setIsSOTrx(true);
		invoice.setC_DocTypeTarget_ID();
		invoice.setDateAcct(m_model.getMovementDate());
		invoice.setDateInvoiced(m_model.getMovementDate());
		invoice.setDateOrdered(m_model.getMovementDate());
		invoice.setM_PriceList_ID(M_PriceList_ID);
		invoice.setPaymentRule(MInvoice.PAYMENTRULE_Cash);
		invoice.setSalesRep_ID(AD_User_ID);
		
		MUNSCvsPhysicalProduct[] modelLines = m_model.getLines();
		for(MUNSCvsPhysicalProduct modelLine : modelLines)
		{
			BigDecimal diff = modelLine.getQtyBook().subtract(modelLine.getQtyCount());
			if(diff.signum() == 0)
				continue;
				
			if(invoice.get_ID() == 0)
				invoice.saveEx();
			
			SimplePrice price = new SimplePrice(
					m_model.getC_BPartner_ID(), modelLine.getM_Product_ID(), get_TrxName());
			MInvoiceLine line = new MInvoiceLine(invoice);
			line.setQty(diff);
			line.setPrice(price.getPriceActual());
			line.setPriceLimit(price.getPriceLimit());
			line.setPriceList(price.getPriceList());
			line.setM_AttributeSetInstance_ID(modelLine.getM_AttributeSetInstance_ID());
			line.saveEx();
			
			modelLine.setC_InvoiceLine_ID(line.get_ID());
			modelLine.saveEx();
		}
		
		m_model.setC_Invoice_ID(invoice.get_ID());
		m_model.saveEx();
		
		return null;
	}

}
