/**
 * 
 */
package com.uns.model.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;

import com.uns.model.I_UNS_Employee;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSReimbursement;

/**
 * @author Haryadi
 *
 */
public class Doc_UNSReimbursement extends Doc 
{

	private MUNSReimbursement m_reimbursement = null;
	
	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSReimbursement(
			MAcctSchema as, Class<?> clazz, ResultSet rs, String defaultDocumentType, String trxName) 
	{
		super(as, clazz, rs, defaultDocumentType, trxName);
	}

	/**
	 * 
	 * @param as
	 * @param rs
	 * @param trxName
	 */
	public Doc_UNSReimbursement(MAcctSchema as,ResultSet rs,String trxName)
	{
		super(as, MUNSReimbursement.class, rs, null, trxName);
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() 
	{
		m_reimbursement = (MUNSReimbursement) getPO();
		setDateDoc(m_reimbursement.getRequestDate());
		setDateAcct(m_reimbursement.getDateAcct());
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		return m_reimbursement.getTotalPaid();
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) 
	{
		if (m_reimbursement.getTotalPaid() == null || m_reimbursement.getTotalPaid().signum() <= 0)
			return null;
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID (as.getC_Currency_ID());
		
		FactLine dr = null;
		FactLine cr = null;
		
		I_UNS_Employee employee = m_reimbursement.getUNS_Employee();
		
		// Debit the employee's medical expense account.
		MAccount medicalExpenseAcct = MUNSPayrollConfiguration.getAccountOf(
				getCtx(), MUNSPayrollConfiguration.COLUMNNAME_E_Medical_Expense_Acct, getTrxName());
		
		dr = fact.createLine(null, medicalExpenseAcct,
				as.getC_Currency_ID(), m_reimbursement.getTotalPaid(), null);
		dr.setAD_Org_ID(employee.getAD_Org_ID());
	
		dr.setC_BPartner_ID(employee.getC_BPartner_ID());
		
		// Credit the cash/bank account.
		String sql = "SELECT B_Asset_Acct FROM C_BankAccount_Acct " +
				" WHERE C_BankAccount_ID=? AND C_AcctSchema_ID=?";
		
		int cashValidCombination = DB.getSQLValue(
				getTrxName(), sql, m_reimbursement.getC_BankAccount_ID(), as.get_ID());

		if (cashValidCombination <= 0)
			throw new AdempiereException("The accounting combination of cash account has not been initialized.");
		
		MAccount cashAcct = MAccount.get(getCtx(), cashValidCombination);
		
		cr = fact.createLine(null, cashAcct,
				as.getC_Currency_ID(), null, m_reimbursement.getTotalPaid());
		cr.setAD_Org_ID(getAD_Org_ID());
		cr.setC_BPartner_ID(employee.getC_BPartner_ID());

		facts.add(fact);
		
		return facts;
	}
}
