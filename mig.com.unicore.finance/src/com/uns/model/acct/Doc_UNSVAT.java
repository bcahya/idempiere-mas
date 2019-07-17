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
import org.compiere.acct.DocLine;
import org.compiere.acct.DocTax;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaDefault;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MUNSVAT;


/**
 * @author Burhani Adam
 *
 */
public class Doc_UNSVAT extends Doc {
	
	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSVAT(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
	}
	
	public Doc_UNSVAT(MAcctSchema as, ResultSet rs, String trxName) {
		super(as, MUNSVAT.class, rs, null, trxName);
	}
	
	private MUNSVAT m_VAT = null;

	@Override
	protected String loadDocumentDetails()
	{
		m_VAT = (MUNSVAT)getPO();
		setDateAcct(m_VAT.getDateAcct());
		setDateDoc(m_VAT.getDateReport());
		
		return null;
	}

	@Override
	public BigDecimal getBalance() {
		// TODO Auto-generated method stub
		return Env.ZERO;
	}

	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as)
	{
		ArrayList<Fact> facts = new ArrayList<Fact>();
		if(m_VAT.getDocStatus().equals("VO"))
			return facts;
		String sql = "SELECT AD_Org_ID, COALESCE(SUM(DifferenceTaxAmt),0) FROM UNS_VATLine"
				+ " WHERE UNS_VAT_ID = ? GROUP BY AD_Org_ID";
		List<List<Object>> columns = DB.getSQLArrayObjectsEx(getTrxName(), sql, m_VAT.get_ID());
		DocLine docLine = new DocLine(m_VAT, this);
		docLine.setAmount(m_VAT.getDifferenceTaxAmt());
		MAcctSchemaDefault def = MAcctSchemaDefault.get(getCtx(), as.get_ID());
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		
		for(int i = 0; i < columns.size(); i++)
		{
			BigDecimal diffTax = (BigDecimal) columns.get(i).get(1);
			setC_Currency_ID (as.getC_Currency_ID());
			FactLine dr = null;
			FactLine cr = null;
			MAccount acct = null;
			int AD_Org_ID = ((BigDecimal) columns.get(i).get(0)).intValue();
			
			if(m_VAT.isIncomeTax())
			{
				if(diffTax.signum() > 0)
				{
					//dr
					acct = (MAccount) def.getWriteOff_A();
					dr = fact.createLine(docLine, acct,
							getC_Currency_ID(), diffTax, null);
					dr.setAD_Org_ID(AD_Org_ID);
					//end dr
					
					//cr
					acct = getAcct(DocTax.ACCTTYPE_TaxCredit, as, m_VAT.getC_Tax_ID());
					cr = fact.createLine(docLine, acct, getC_Currency_ID(), null, diffTax);
					cr.setAD_Org_ID(AD_Org_ID);
					//end cr
				}
				else if(diffTax.signum() < 0)
				{				
					diffTax = diffTax.negate();
					//dr
					acct = getAcct(DocTax.ACCTTYPE_TaxCredit, as, m_VAT.getC_Tax_ID());
					
					dr = fact.createLine(docLine, acct,
							getC_Currency_ID(), diffTax, null);
					dr.setAD_Org_ID(AD_Org_ID);
					//end dr
					
					//cr
					if(def.isUseDiffAcctCreditWriteoff() && def.getW_Differences_Acct() > 0)
						acct = (MAccount) def.getWriteOffCredit_A();
					else
						acct = (MAccount) def.getWriteOff_A();
					cr = fact.createLine(docLine, acct, getC_Currency_ID(), null, diffTax);
					cr.setAD_Org_ID(AD_Org_ID);
					//end cr
				}
			}
			else
			{
				if(diffTax.signum() > 0)
				{
					//dr
					acct = getAcct(DocTax.ACCTTYPE_TaxDue, as, m_VAT.getC_Tax_ID());
					dr = fact.createLine(docLine, acct,
							getC_Currency_ID(), diffTax, null);
					dr.setAD_Org_ID(AD_Org_ID);
					//end dr
					
					//cr
					if(def.isUseDiffAcctCreditWriteoff() && def.getW_Differences_Acct() > 0)
						acct = (MAccount) def.getWriteOffCredit_A();
					else
						acct = (MAccount) def.getWriteOff_A();
					cr = fact.createLine(docLine, acct, getC_Currency_ID(), null, diffTax);
					cr.setAD_Org_ID(AD_Org_ID);
					//end cr
				}
				else if(diffTax.signum() < 0)
				{
					diffTax = diffTax.negate();
					
					//dr
					acct = (MAccount) def.getWriteOff_A();
					dr = fact.createLine(docLine, acct, getC_Currency_ID(), diffTax, null);
					dr.setAD_Org_ID(AD_Org_ID);
					//end dr
					
					//cr
					acct = getAcct(DocTax.ACCTTYPE_TaxDue, as, m_VAT.getC_Tax_ID());
					
					cr = fact.createLine(docLine, acct,
							getC_Currency_ID(), null, diffTax);
					cr.setAD_Org_ID(AD_Org_ID);
					//end cr
				}
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
		if (AcctType < DocTax.ACCTTYPE_TaxDue || AcctType > DocTax.ACCTTYPE_TaxExpense)
			return null;
		//
		String sql = "SELECT T_Due_Acct, T_Credit_Acct, T_Expense_Acct "
				+ "FROM C_Tax_Acct WHERE C_Tax_ID=? AND C_AcctSchema_ID=?";
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
				validCombination_ID = rs.getInt(AcctType+1);    //  1..3
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