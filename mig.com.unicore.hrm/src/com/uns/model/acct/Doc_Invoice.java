/**
 * 
 */
package com.uns.model.acct;

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
import org.compiere.model.MCurrency;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MTax;
import org.compiere.model.PO;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MUNSMedicalRecord;

/**
 * @author Haryadi
 *
 */
public class Doc_Invoice extends Doc {

	/**
	 * @param as
	 * @param rs
	 * @param trxName
	 */
	public Doc_Invoice(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
	}

	public Doc_Invoice(MAcctSchema as, ResultSet rs, String trxName) {
		super(as, MInvoice.class, rs, null, trxName);
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
		MInvoice invoice = (MInvoice)getPO();
		setDateDoc(invoice.getDateInvoiced());
		setIsTaxIncluded(invoice.isTaxIncluded());
		//	Amounts
		setAmount(Doc.AMTTYPE_Gross, invoice.getGrandTotal());
		setAmount(Doc.AMTTYPE_Net, invoice.getTotalLines());
		setAmount(Doc.AMTTYPE_Charge, invoice.getChargeAmt());

		//	Contained Objects
		m_taxes = loadTaxes();
		p_lines = loadLines(invoice);
		log.fine("Lines=" + p_lines.length + ", Taxes=" + m_taxes.length);
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
				log.fine(taxLine.toString());
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
			DocLineInvoice docLine = new DocLineInvoice(line, this);
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
					log.fine("LineNetAmt=" + LineNetAmt + " - Tax=" + LineNetAmtTax);
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
			log.fine(docLine.toString());
			list.add(docLine);
		}

		//	Convert to Array
		DocLineInvoice[] dls = new DocLineInvoice[list.size()];
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
		log.fine(toString() + " Balance=" + retValue + sb.toString());
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for API, APC.
	 *  <pre>
	 *
	 *  API
	 *      Payables                CR
	 *      Charge          DR
	 *      TaxCredit       DR
	 *      Expense         DR
	 *
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

		//  ** API
		if (getDocumentType().equals(DOCTYPE_APInvoice))
		{
			BigDecimal grossAmt = getAmount(Doc.AMTTYPE_Gross);
			BigDecimal serviceAmt = Env.ZERO;

			// Employee Loan
			for (int i = 0; i < p_lines.length; i++)
			{
				DocLineInvoice line = (DocLineInvoice) p_lines[i];
				
				MInvoiceLine invLine = line.m_InvLine;
				
				MUNSMedicalRecord[] employeeRecList = 
						MUNSMedicalRecord.getMedicalOfInvoiceLine(getCtx(), invLine.get_ID(), getTrxName());
				
				for (MUNSMedicalRecord employeeRec : employeeRecList)
				{
					FactLine tl = fact.createLine (line, line.getAccount(ProductCost.ACCTTYPE_P_Expense, as),
							getC_Currency_ID(), employeeRec.getMedicalCosts(), null);
					
					tl.setAD_Org_ID(employeeRec.getEmployeeDepID());
					tl.setC_BPartner_ID(employeeRec.getUNS_Employee().getC_BPartner_ID());
				}
			}
			
			//  TaxCredit       DR
			for (int i = 0; i < m_taxes.length; i++)
			{
				FactLine tl = fact.createLine(null, m_taxes[i].getAccount(m_taxes[i].getAPTaxType(), as),
					getC_Currency_ID(), m_taxes[i].getAmount(), null);
				if (tl != null)
					tl.setC_Tax_ID(m_taxes[i].getC_Tax_ID());
			}
			//  Set Locations
			FactLine[] fLines = fact.getLines();
			for (int i = 0; i < fLines.length; i++)
			{
				if (fLines[i] != null)
				{
					fLines[i].setLocationFromBPartner(getC_BPartner_Location_ID(), true);  //  from Loc
					fLines[i].setLocationFromOrg(fLines[i].getAD_Org_ID(), false);    //  to Loc
				}
			}

			//  Liability               CR
			int payables_ID = getValidCombination_ID (Doc.ACCTTYPE_V_Liability, as);
			int payablesServices_ID = getValidCombination_ID (Doc.ACCTTYPE_V_Liability_Services, as);

			if (m_allLinesItem || !as.isPostServices()
				|| payables_ID == payablesServices_ID)
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
				fact.createLine(null, MAccount.get(getCtx(), payables_ID),
					getC_Currency_ID(), null, grossAmt);
			if (serviceAmt.signum() != 0)
				fact.createLine(null, MAccount.get(getCtx(), payablesServices_ID),
					getC_Currency_ID(), null, serviceAmt);
			//
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
}   //  Doc_Invoice

class DocLineInvoice extends DocLine
{
	MInvoiceLine m_InvLine = (MInvoiceLine) p_po;
	
	DocLineInvoice (PO po, Doc doc)
	{
		super(po, doc);
	}	
}