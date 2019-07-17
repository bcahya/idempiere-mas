/**
 * 
 */
package com.uns.model.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.acct.Doc;
import org.compiere.acct.DocTax;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MUNSVATPayment;

/**
 * @author Burhani Adam
 *
 */
public class Doc_UNSVATPayment extends Doc {

	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSVATPayment(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public Doc_UNSVATPayment(MAcctSchema as, ResultSet rs, String trxName) {
		super(as, MUNSVATPayment.class, rs, null, trxName);
	}
	
	private MUNSVATPayment m_VATPayment = null;

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails()
	{
		m_VATPayment = (MUNSVATPayment)getPO();
		setDateAcct(m_VATPayment.getDateAcct());
		setDateDoc(m_VATPayment.getDateDoc());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		// TODO Auto-generated method stub
		return Env.ZERO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as)
	{
		setC_Currency_ID(as.getC_Currency_ID());
		ArrayList<Fact> facts = new ArrayList<Fact>();
		if(m_VATPayment.getDocStatus().equals("VO"))
			return facts;
		String sql = "SELECT l.AD_Org_ID, COALESCE(SUM(CASE WHEN l.IsReplacement = 'N'"
				+ " AND ((v.IsSOTrx = 'Y' AND v.IsCreditMemo = 'N') OR"
				+ " (v.IsSOTrx = 'N' AND v.IsCreditMemo = 'Y')) THEN"
				+ " l.RevisionAmt WHEN l.IsReplacement = 'Y' AND"
				+ " ((v.IsSOTrx = 'Y' AND v.IsCreditMemo = 'N') OR"
				+ " (v.IsSOTrx = 'N' AND v.IsCreditMemo = 'Y'))"
				+ " THEN l.DifferenceTaxAmt ELSE 0 END),0),"
				+ " COALESCE(SUM(CASE WHEN l.IsReplacement = 'N'"
				+ " AND ((v.IsSOTrx = 'N' AND v.IsCreditMemo = 'N') OR"
				+ " (v.IsSOTrx = 'Y' AND v.IsCreditMemo = 'Y'))"
				+ " THEN l.RevisionAmt WHEN l.IsReplacement = 'Y' AND"
				+ " ((v.IsSOTrx = 'N' AND v.IsCreditMemo = 'N') OR"
				+ " (v.IsSOTrx = 'Y' AND v.IsCreditMemo = 'Y')) THEN"
				+ " l.DifferenceTaxAmt ELSE 0 END),0), v.C_Tax_ID"
				+ " FROM UNS_VATLine l"
				+ " INNER JOIN UNS_VAT v ON v.UNS_VAT_ID = l.UNS_VAT_ID"
				+ " WHERE v.UNS_VATPayment_ID=? GROUP BY l.AD_Org_ID, v.C_Tax_ID";
		List<List<Object>> columns = DB.getSQLArrayObjectsEx(getTrxName(), sql, m_VATPayment.get_ID());
//		DocLine docLine = new DocLine(m_VATPayment, this);
//		docLine.setAmount(m_VATPayment.getTotalCurBallance());
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		
		for(int i = 0; i < columns.size(); i++)
		{
			setC_Currency_ID (as.getC_Currency_ID());
			FactLine dr = null;
			FactLine cr = null;
			int AD_Org_ID = ((BigDecimal) columns.get(i).get(0)).intValue();
			BigDecimal keluaran = (BigDecimal) columns.get(i).get(1);
			BigDecimal masukan = (BigDecimal) columns.get(i).get(2);
			int C_Tax_ID = ((BigDecimal) columns.get(i).get(3)).intValue();
			MAccount kurangBayarAcct = getAcct(DocTax.ACCTTYPE_TaxLiability, as, C_Tax_ID);
			MAccount lebihBayarAcct = getAcct(DocTax.ACCTTYPE_TaxReceivables, as, C_Tax_ID);
			MAccount keluaranAcct = getAcct(DocTax.ACCTTYPE_TaxDue, as, C_Tax_ID);
			MAccount masukanAcct = getAcct(DocTax.ACCTTYPE_TaxCredit, as, C_Tax_ID);
			BigDecimal kurangBayar = Env.ZERO;
			BigDecimal lebihBayar = Env.ZERO;
			
			if(keluaran.compareTo(masukan) == 0)
			{
				dr = fact.createLine(null, keluaranAcct, getC_Currency_ID(), keluaran, null);
				dr.setAD_Org_ID(AD_Org_ID);
				cr = fact.createLine(null, masukanAcct, getC_Currency_ID(), null, masukan);
				cr.setAD_Org_ID(AD_Org_ID);
			}
			else if(keluaran.compareTo(masukan) == 1)
			{
				if(masukan.signum() != 0)
				{
					dr = fact.createLine(null, keluaranAcct, getC_Currency_ID(), keluaran, null);
					dr.setAD_Org_ID(AD_Org_ID);
					cr = fact.createLine(null, masukanAcct, getC_Currency_ID(), null, masukan);
					cr.setAD_Org_ID(AD_Org_ID);
				}
				kurangBayar = keluaran.subtract(masukan);
				cr = fact.createLine(null, kurangBayarAcct, getC_Currency_ID(), null, kurangBayar);
				cr.setAD_Org_ID(m_VATPayment.getAD_OrgTrx_ID());
				cr.setAD_OrgTrx_ID(AD_Org_ID);
			}
			else if(keluaran.compareTo(masukan) == -1)
			{
				dr = fact.createLine(null, keluaranAcct, getC_Currency_ID(), keluaran, null);
				dr.setAD_Org_ID(AD_Org_ID);
				cr = fact.createLine(null, masukanAcct, getC_Currency_ID(), null, masukan);
				cr.setAD_Org_ID(AD_Org_ID);
				lebihBayar = masukan.subtract(keluaran);
				cr = fact.createLine(null, lebihBayarAcct, getC_Currency_ID(), null, lebihBayar);
				cr.setAD_Org_ID(AD_Org_ID);
				cr.setAD_OrgTrx_ID(m_VATPayment.getAD_OrgTrx_ID());
			}
		}
		
		facts.add(fact);
		return facts;
	}
	
	/**
	 *	Get Account
	 *  @param AcctType see ACCTTYPE_*
	 *  @param as account schema
	 *  @return Account
	 */
	public MAccount getAcct (int AcctType, MAcctSchema as, int C_Tax_ID)
	{
		if (AcctType < DocTax.ACCTTYPE_TaxDue || AcctType > DocTax.ACCTTYPE_TaxReceivables)
			return null;
		//
		String sql = "SELECT T_Due_Acct, T_Credit_Acct, T_Expense_Acct, T_Liability_Acct, T_Receivables_Acct"
				+ " FROM C_Tax_Acct WHERE C_Tax_ID=? AND C_AcctSchema_ID=?";
		int validCombination_ID = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_Tax_ID);
			pstmt.setInt(2, as.getC_AcctSchema_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
				validCombination_ID = rs.getInt(AcctType+1);    //  1..5
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		if (validCombination_ID == 0)
			return null;
		return MAccount.get(as.getCtx(), validCombination_ID);
	}   //  getAccount
}