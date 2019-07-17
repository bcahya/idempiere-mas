/**
 * 
 */
package com.unicore.base.doc;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.DocTax;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MCurrency;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MProduct;
import org.compiere.model.MTax;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.base.model.MInvoice;



/**
 * @author menjangan
 *
 */
public class Doc_Invoice extends org.compiere.acct.Doc_Invoice {

	/**
	 * @param as
	 * @param rs
	 * @param trxName
	 */
	public Doc_Invoice(MAcctSchema as, ResultSet rs, String trxName) {
		super(as, rs, trxName);
	}
	
	/** Contained Optional Tax Lines    */
	private DocTax[]        m_taxes = null;
	/** Currency Precision				*/
	private int				m_precision = -1;
	/** All lines are Service			*/
	private boolean			m_allLinesService = true;
	/** All lines are product item		*/
	private boolean			m_allLinesItem = true;

	/**
	 *  Load Specific Document Details
	 *  @return error message or null
	 */
	protected String loadDocumentDetails ()
	{
		MInvoice invoice = new MInvoice(getCtx(), getPO().get_ID(), getTrxName());
		setDateDoc(invoice.getDateInvoiced());
		setIsTaxIncluded(invoice.isTaxIncluded());
		//	Amounts
		setAmount(Doc.AMTTYPE_Gross, invoice.getGrandTotal());
		setAmount(Doc.AMTTYPE_Net, invoice.getTotalLines());
		setAmount(Doc.AMTTYPE_Charge, invoice.getChargeAmt());

		//	Contained Objects
		m_taxes = loadTaxes();
		p_lines = loadLines(invoice);
		if (log.isLoggable(Level.FINE)) log.fine("Lines=" + p_lines.length + ", Taxes=" + m_taxes.length);
		return null;
	}   //  loadDocumentDetails

	/**
	 *	Load Invoice Taxes
	 *  @return DocTax Array
	 */
	private DocTax[] loadTaxes()
	{
		ArrayList<DocTax> list = new ArrayList<DocTax>();
		String sql = "SELECT it.C_Tax_ID, t.Name, t.Rate, it.TaxBaseAmt, it.TaxAmt, t.IsSalesTax "
				+ "FROM C_Tax t, C_InvoiceTax it "
				+ "WHERE t.C_Tax_ID=it.C_Tax_ID AND it.C_Invoice_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, getTrxName());
			pstmt.setInt(1, get_ID());
			rs = pstmt.executeQuery();
			//
			while (rs.next())
			{
				int C_Tax_ID = rs.getInt(1);
				String name = rs.getString(2);
				BigDecimal rate = rs.getBigDecimal(3);
				BigDecimal taxBaseAmt = rs.getBigDecimal(4);
				BigDecimal amount = rs.getBigDecimal(5);
				boolean salesTax = "Y".equals(rs.getString(6));
				//
				DocTax taxLine = new DocTax(C_Tax_ID, name, rate,
					taxBaseAmt, amount, salesTax);
				if (log.isLoggable(Level.FINE)) log.fine(taxLine.toString());
				list.add(taxLine);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return null;
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//	Return Array
		DocTax[] tl = new DocTax[list.size()];
		list.toArray(tl);
		return tl;
	}	//	loadTaxes

	/**
	 *	Load Invoice Line
	 *	@param invoice invoice
	 *  @return DocLine Array
	 */
	private DocLine[] loadLines (MInvoice invoice)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		//
		MInvoiceLine[] lines = invoice.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MInvoiceLine line = lines[i];
			if (line.isDescription())
				continue;
			DocLine docLine = new DocLine(line, this);
			//	Qty
			BigDecimal Qty = line.getQtyInvoiced();
			boolean cm = getDocumentType().equals(DOCTYPE_ARCredit)
				|| getDocumentType().equals(DOCTYPE_APCredit);
			docLine.setQty(cm ? Qty.negate() : Qty, invoice.isSOTrx());
			//
			BigDecimal LineNetAmt = line.getLineNetAmt();
			BigDecimal PriceList = line.getPriceList();
			int C_Tax_ID = docLine.getC_Tax_ID();
			//	Correct included Tax
			if (isTaxIncluded() && C_Tax_ID != 0)
			{
				MTax tax = MTax.get(getCtx(), C_Tax_ID);
				if (!tax.isZeroTax())
				{
					BigDecimal LineNetAmtTax = tax.calculateTax(LineNetAmt, true, getStdPrecision());
					if (log.isLoggable(Level.FINE)) log.fine("LineNetAmt=" + LineNetAmt + " - Tax=" + LineNetAmtTax);
					LineNetAmt = LineNetAmt.subtract(LineNetAmtTax);
					for (int t = 0; t < m_taxes.length; t++)
					{
						if (m_taxes[t].getC_Tax_ID() == C_Tax_ID)
						{
							m_taxes[t].addIncludedTax(LineNetAmtTax);
							break;
						}
					}
					BigDecimal PriceListTax = tax.calculateTax(PriceList, true, getStdPrecision());
					PriceList = PriceList.subtract(PriceListTax);
				}
			}	//	correct included Tax

			docLine.setAmount (LineNetAmt, PriceList, Qty);	//	qty for discount calc
			if (docLine.isItem())
				m_allLinesService = false;
			else
				m_allLinesItem = false;
			//
			if (log.isLoggable(Level.FINE)) log.fine(docLine.toString());
			list.add(docLine);
		}

		//	Convert to Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);

		//	Included Tax - make sure that no difference
		if (isTaxIncluded())
		{
			for (int i = 0; i < m_taxes.length; i++)
			{
				if (m_taxes[i].isIncludedTaxDifference())
				{
					BigDecimal diff = m_taxes[i].getIncludedTaxDifference();
					for (int j = 0; j < dls.length; j++)
					{
						if (dls[j].getC_Tax_ID() == m_taxes[i].getC_Tax_ID())
						{
							dls[j].setLineNetAmtDifference(diff);
							break;
						}
					}	//	for all lines
				}	//	tax difference
			}	//	for all taxes
		}	//	Included Tax difference

		//	Return Array
		return dls;
	}	//	loadLines

	/**
	 * 	Get Currency Precision
	 *	@return precision
	 */
	private int getStdPrecision()
	{
		if (m_precision == -1)
			m_precision = MCurrency.getStdPrecision(getCtx(), getC_Currency_ID());
		return m_precision;
	}	//	getPrecision


	/**************************************************************************
	 *  Get Source Currency Balance - subtracts line and tax amounts from total - no rounding
	 *  @return positive amount, if total invoice is bigger than lines
	 */
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		StringBuilder sb = new StringBuilder (" [");
		//  Total
		retValue = retValue.add(getAmount(Doc.AMTTYPE_Gross));
		sb.append(getAmount(Doc.AMTTYPE_Gross));
		//  - Header Charge
		retValue = retValue.subtract(getAmount(Doc.AMTTYPE_Charge));
		sb.append("-").append(getAmount(Doc.AMTTYPE_Charge));
		//  - Tax
		for (int i = 0; i < m_taxes.length; i++)
		{
			retValue = retValue.subtract(m_taxes[i].getAmount());
			sb.append("-").append(m_taxes[i].getAmount());
		}
		//  - Lines
		for (int i = 0; i < p_lines.length; i++)
		{
			retValue = retValue.subtract(p_lines[i].getAmtSource());
			sb.append("-").append(p_lines[i].getAmtSource());
		}
		sb.append("]");
		//
		if (log.isLoggable(Level.FINE)) log.fine(toString() + " Balance=" + retValue + sb.toString());
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  ARI, ARC, ARF, API, APC.
	 *  <pre>
	 *  ARI, ARF
	 *      Receivables     DR
	 *      Charge                  CR
	 *      TaxDue                  CR
	 *      Revenue                 CR
	 *
	 *  ARC
	 *      Receivables             CR
	 *      Charge          DR
	 *      TaxDue          DR
	 *      Revenue         RR
	 *
	 *  API
	 *      Payables                CR
	 *      Charge          DR
	 *      TaxCredit       DR
	 *      Expense         DR
	 *
	 *  APC
	 *      Payables        DR
	 *      Charge                  CR
	 *      TaxCredit               CR
	 *      Expense                 CR
	 *  </pre>
	 *  @param as accounting schema
	 *  @return Fact
	 */
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		//
		ArrayList<Fact> facts = new ArrayList<Fact>();
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);

		//  Cash based accounting
		if (!as.isAccrual())
			return facts;

		//  ** ARI, ARF
		if (getDocumentType().equals(DOCTYPE_ARInvoice))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;

			//  Header Charge           CR
			BigDecimal amt = getAmount(Doc.AMTTYPE_Charge);
			if (amt != null && amt.signum() != 0)
				fact.createLine(null, getAccount(Doc.ACCTTYPE_Charge, as),
					getC_Currency_ID(), null, amt);
			//  TaxDue                  CR
			for (int i = 0; i < m_taxes.length; i++)
			{
				amt = m_taxes[i].getAmount();
				if (amt != null && amt.signum() != 0)
				{
					FactLine tl = fact.createLine(null, m_taxes[i].getAccount(DocTax.ACCTTYPE_TaxDue, as),
						getC_Currency_ID(), null, amt);
					if (tl != null)
						tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
				}
			}
			//  Revenue                 CR
			for (int i = 0; i < p_lines.length; i++)
			{
				BigDecimal cost = p_lines[i].getProductCosts(as, p_lines[i].getAD_Org_ID(), false); 
				if(cost.compareTo(BigDecimal.ZERO) == 0)
					cost = MCost.getCurrentCost(
							new MProduct(getCtx(), p_lines[i].getM_Product_ID(), getTrxName()), 
							p_lines[i].getM_AttributeSetInstance_ID(), 
							as, 
							p_lines[i].getAD_Org_ID(), 
							as.getCostingMethod(), 
							p_lines[i].getQty(), 
							0, 
							false, 
							getTrxName());
				
				fact.createLine (p_lines[i],
						p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Asset, as),
						getC_Currency_ID(), null, cost);
				
				amt = p_lines[i].getAmtSource();
				
				BigDecimal dAmt = null;
				if (as.isTradeDiscountPosted())
				{
					BigDecimal discount = p_lines[i].getDiscount();
					if (discount != null && discount.signum() != 0)
					{
						amt = amt.add(discount);
						dAmt = discount;
						fact.createLine (p_lines[i],
								p_lines[i].getAccount(ProductCost.ACCTTYPE_P_TDiscountGrant, as),
								getC_Currency_ID(), dAmt, null);
					}
				}
				fact.createLine (p_lines[i],
					p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Revenue, as),
					getC_Currency_ID(), null, amt);
				
				fact.createLine (p_lines[i],
						p_lines[i].getAccount(ProductCost.ACCTTYPE_P_Cogs, as),
						getC_Currency_ID(), cost,null);
				
				if (!p_lines[i].isItem())
				{
					grossAmt = grossAmt.subtract(amt);
					serviceAmt = serviceAmt.add(amt);
				}
			}
			//  Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), true);      //  from Loc
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), false);  //  to Loc
				}
			}

			//  Receivables     DR
			int receivables_ID = getValidCombination_ID(Doc.ACCTTYPE_C_Receivable, as);
			int receivablesServices_ID = getValidCombination_ID (Doc.ACCTTYPE_C_Receivable_Services, as);
			if (m_allLinesItem || !as.isPostServices()
				|| receivables_ID == receivablesServices_ID)
			{
				grossAmt = getAmount(Doc.AMTTYPE_Gross);
				serviceAmt = Env.ZERO;
			}
			else if (m_allLinesService)
			{
				serviceAmt = getAmount(Doc.AMTTYPE_Gross);
				grossAmt = Env.ZERO;
			}
			if (grossAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivables_ID),
					getC_Currency_ID(), grossAmt, null);
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), receivablesServices_ID),
					getC_Currency_ID(), serviceAmt, null);
			
		}
		else
		{
			p_Error = "DocumentType unknown: " + getDocumentType();
			log.log(Level.SEVERE, p_Error);
			fact = null;
		}
		//
		facts.add(fact);
		return facts;
	}   //  createFact
}
