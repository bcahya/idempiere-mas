/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

/**
 * @author Burhani Adam
 *
 */
public class UpdateAmountTaxInvoice extends SvrProcess {
	
	private int m_RoundingMode = 0;
	private int m_RoundingScale = 0;
	private int m_VAT_ID = 0;
	private boolean isHeader = true;
	private MUNSVATLine m_VATLine;
	
	/**
	 * 
	 */
	public UpdateAmountTaxInvoice() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter param : params)
		{
			if (param.getParameterName() == null);
			else if (param.getParameterName().equals("RoundingMode"))
				m_RoundingMode = param.getParameterAsInt();
			else if (param.getParameterName().equals("RoundingScale"))
				m_RoundingScale = param.getParameterAsInt();
			else if (param.getParameterName().equals("IsHeader"))
				isHeader = param.getParameterAsBoolean();
			else {
				log.log(Level.SEVERE, "Unknowon Parameter" + param.getParameterName());
			}
		}
		
		if(isHeader)
		{
			m_VAT_ID = getRecord_ID();
		}
		else 
		{
			m_VATLine = new MUNSVATLine(getCtx(), getRecord_ID(), get_TrxName());
			if(m_VATLine == null)
				throw new AdempiereException("Cannout found VAT Line");
			
			m_VAT_ID = m_VATLine.getUNS_VAT_ID();
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		MUNSVATLine[] lines = MUNSVATLine.getByParent(getCtx(), m_VAT_ID, get_TrxName());
		for(MUNSVATLine line : lines)
		{
			if(!isHeader && !line.equals(m_VATLine))
				continue;
			
			MUNSVATInvLine[] iLines = MUNSVATInvLine.getByParent(getCtx(), line.get_ID(), null, get_TrxName());
			for(MUNSVATInvLine iLine : iLines)
			{
				BigDecimal bdRoundingScale = Env.ONE;
				if(m_RoundingScale == 0)
					m_RoundingScale = 1;
				BigDecimal revBefTaxAmt = iLine.getBeforeTaxAmt().divide(BigDecimal.valueOf(m_RoundingScale), 0, m_RoundingMode);
				revBefTaxAmt = revBefTaxAmt.multiply(BigDecimal.valueOf(m_RoundingScale));
				iLine.setRevisionBeforeTaxAmt(revBefTaxAmt);
				BigDecimal rateTax = iLine.getC_Tax().getRate();
				BigDecimal roundedTax = revBefTaxAmt.multiply(iLine.getQtyInvoiced());
				BigDecimal discAmt = iLine.getDiscountAmt().divide(bdRoundingScale, 0, m_RoundingMode);
				discAmt = discAmt.multiply(bdRoundingScale);
				roundedTax = roundedTax.subtract(discAmt);
				
				if (m_RoundingScale > 0)
				{
					bdRoundingScale = BigDecimal.valueOf(m_RoundingScale);
					
					roundedTax = roundedTax.divide(bdRoundingScale, 0, m_RoundingMode);
					
					roundedTax = roundedTax.multiply(bdRoundingScale);
				}
				else 
				{
					int absRound = m_RoundingScale * -1;
					roundedTax = roundedTax.setScale(absRound, m_RoundingMode);
				}
				
				BigDecimal taxAmt = (roundedTax.multiply(rateTax)).divide(Env.ONEHUNDRED);
				taxAmt = taxAmt.divide(bdRoundingScale, 0, m_RoundingMode);
				taxAmt = taxAmt.multiply(bdRoundingScale);
				iLine.setRevisionTaxAmt(taxAmt);
				
				iLine.setRevisionDiscAmt(discAmt);
				BigDecimal netAmt = iLine.getRevisionBeforeTaxAmt().multiply(iLine.getQtyInvoiced());
				netAmt = netAmt.divide(bdRoundingScale, 0, m_RoundingMode);
				netAmt = netAmt.multiply(bdRoundingScale);
				iLine.setRevisionAmt(netAmt.subtract(discAmt));
				if(iLine.getRevisionAmt().compareTo(Env.ZERO) == -1)
				{
					iLine.setRevisionDiscAmt(iLine.getRevisionDiscAmt().add(iLine.getRevisionAmt()));
					iLine.setRevisionAmt(Env.ZERO);
					iLine.setRevisionTaxAmt(Env.ZERO);
				}
				iLine.setAuto(true);
				if(!iLine.save())
					throw new AdempiereException("failed when update invoice lines amount");
			}
			
			if(!isHeader)
				break;
		}
		
		return "Success Update " + lines.length + " Invoices";
	}
	
	public static void main(String[] trs)
	{
		BigDecimal a = BigDecimal.valueOf(-0.0000001);
		int b = a.signum();
		System.out.println(b);
	}
}
