/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.unicore.model.MUNSVATDocNo;
import com.uns.model.MUNSVAT;
import com.uns.model.MUNSVATLine;

/**
 * @author Burhani Adam
 *
 */
public class GenerateTaxInvoicesNo extends SvrProcess {
	
	private String m_TaxInvoiceNo = null;
	private int indexOf = 0;
	public BigDecimal taxInvoiceNoInt = Env.ZERO;
	
	/**
	 * 
	 */
	public GenerateTaxInvoicesNo() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
//		ProcessInfoParameter[] para = getParameter();
//		for (int i = 0; i < para.length; i++)
//		{
//			String name = para[i].getParameterName();
//			if (name.equals("TaxInvoiceNo"))
//				m_TaxInvoiceNo = para[i].getParameter().toString();
//			else
//				log.log(Level.SEVERE, "Unknown Parameter: " + name);
//		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		MUNSVAT vat = new MUNSVAT(getCtx(), getRecord_ID(), get_TrxName());
//		if(!vat.isProcessed())
//			return null; 
//		initialTaxInvoiceNo();
//		String startNo = m_TaxInvoiceNo.substring(0, indexOf);
		MUNSVATLine[] lines = MUNSVATLine.getByParent(getCtx(), vat.get_ID(), get_TrxName());
//		for(MUNSVATLine line : lines)
//		{
//			line.setTaxInvoiceNo(String.valueOf(startNo + taxInvoiceNoInt.setScale(0)));
//			line.saveEx();
//			taxInvoiceNoInt = taxInvoiceNoInt.add(Env.ONE);
//			count += 1;
//		}
		for(MUNSVATLine line : lines)
		{
			if(!Util.isEmpty(line.getTaxInvoiceNo(), true))
				continue;
			initialTaxInvoiceNo(vat, line);
			line.setAuto(true);
			line.setTaxInvoiceNo(m_TaxInvoiceNo);
			line.saveEx();
		}
		
		return "Success Update / Check " + lines.length + " Invoices";
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private void initialTaxInvoiceNo()
	{
		for(int i = 0; i <= m_TaxInvoiceNo.length(); i++)
		{
			if(String.valueOf(m_TaxInvoiceNo.charAt(i)).equals("0"))
				continue;
			else
			{
				indexOf = i;
				break;
			}
		}
		System.out.println(m_TaxInvoiceNo.substring(indexOf));
		taxInvoiceNoInt = new BigDecimal(m_TaxInvoiceNo.substring(indexOf));
	}
	
	private void initialTaxInvoiceNo(MUNSVAT vat, MUNSVATLine line)
	{
		MUNSVATDocNo dn = null;
		
		if(line.isReplacement())
		{
			dn = MUNSVATDocNo.getByVATLine(getCtx(), line.get_ID(), line.get_TrxName());
			m_TaxInvoiceNo = dn.getTaxInvoiceNo().replace(dn.getUNS_VATRegisteredSequences().getPrefixNo(),
															dn.getUNS_VATRegisteredSequences().getReplacementPrefix());
		}
		else
		{	
			dn = MUNSVATDocNo.getUnUseByDiv(vat.getCtx(), vat.getAD_Org_ID(), vat.get_TrxName());
			if(dn == null)
				throw new AdempiereException("Tax Invoice No not found");
			m_TaxInvoiceNo = dn.getTaxInvoiceNo();
			dn.setUsageStatus("U");
			dn.setC_Invoice_ID(line.getC_Invoice_ID());
			dn.setUNS_VATLine_ID(line.getUNS_VATLine_ID());
			dn.saveEx();
		}
	}	
	
//	public static void main(String[]args)
//	{
//		BigDecimal satu = BigDecimal.valueOf(1204591);
//		BigDecimal dua = BigDecimal.valueOf(120459.1);
//		
//		BigDecimal hasilsatu = satu.divide(BigDecimal.valueOf(10), 0, RoundingMode.HALF_UP);
//		BigDecimal hasildua = dua.divide(BigDecimal.valueOf(10), 0, RoundingMode.HALF_UP);
//		
//		System.out.println(hasilsatu);
//		System.out.println(hasildua);
//	}
}
