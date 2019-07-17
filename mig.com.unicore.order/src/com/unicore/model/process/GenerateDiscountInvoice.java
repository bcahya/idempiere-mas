/**
 * 
 */
package com.unicore.model.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MInvoiceLine;
import com.unicore.model.MUNSBonusClaim;
import com.unicore.model.MUNSBonusClaimLine;

/**
 * @author user
 * 
 */
public class GenerateDiscountInvoice extends SvrProcess
{

	private int p_UNS_BonusClaim;
	private MUNSBonusClaim m_UNSBonusClaim;

	/**
	 * 
	 */
	public GenerateDiscountInvoice()
	{
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_UNS_BonusClaim = getRecord_ID();
		m_UNSBonusClaim = new MUNSBonusClaim(getCtx(), p_UNS_BonusClaim, get_TrxName());

		if (m_UNSBonusClaim.getDocStatus().equals(MUNSBonusClaim.DOCSTATUS_Completed)
				|| m_UNSBonusClaim.getDocStatus().equals(MUNSBonusClaim.DOCSTATUS_Closed))
			;
		else
			throw new AdempiereException("Not Complete Document!");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{

		MInvoice invoice = new MInvoice(getCtx(), m_UNSBonusClaim.getC_Invoice_ID(), get_TrxName());
		if (m_UNSBonusClaim.getC_Invoice_ID() == 0)
		{
			invoice.setInvoice(m_UNSBonusClaim);
		}

		invoice.setC_BPartner_ID(m_UNSBonusClaim.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(m_UNSBonusClaim.getC_BPartner_Location_ID());
		invoice.setC_Currency_ID(m_UNSBonusClaim.getC_Currency_ID());
		invoice.setC_DocTypeTarget_ID(m_UNSBonusClaim.getDocTypeInvoice_ID());
		invoice.setDateInvoiced(new Timestamp(System.currentTimeMillis()));
		invoice.setAD_Org_ID(m_UNSBonusClaim.getAD_Org_ID());
		invoice.saveEx();
		
		m_UNSBonusClaim.setC_Invoice_ID(invoice.get_ID());
		m_UNSBonusClaim.saveEx();
		
		for (MUNSBonusClaimLine line : m_UNSBonusClaim.getLines())
		{
			if(line.getDecisionConfirm().equals(MUNSBonusClaimLine.DECISIONCONFIRM_Discard))
				continue;
			MInvoiceLine invLine = new MInvoiceLine(getCtx(), line.getC_InvoiceLine_ID(), get_TrxName());
		
			if (invLine.getC_InvoiceLine_ID() == 0){
				invLine = new MInvoiceLine(invoice);
				invLine.setAD_Org_ID(invoice.getAD_Org_ID());
				MAttributeSetInstance asi = MAttributeSetInstance.create(getCtx(), (MProduct) invLine.getM_Product(), get_TrxName());
				invLine.setM_AttributeSetInstance_ID(asi.get_ID());
				invLine.setC_Charge_ID(1000013); //FIXME hardcode
			}
			
			invLine.setQty(1);
			invLine.setPrice(line.getAmtClaimed());
			invLine.setLineNetAmt(line.getAmtClaimed());
			invLine.setC_Tax_ID(line.getC_Tax_ID());
			
			invLine.saveEx();

			line.setC_InvoiceLine_ID(invLine.get_ID());
			line.saveEx();
		}

		return "Invoice has ben created, "+ invoice.toString();
	}

}
