/**
 * 
 */
package com.uns.model.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.X_C_BP_Vendor_Acct;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.I_UNS_Employee;
import com.uns.model.MUNSEmployeeLoan;
import com.uns.model.MUNSPayrollConfiguration;

/**
 * @author menjangan
 *
 */
public class Doc_UNSEmployeeLoan extends Doc {

	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSEmployeeLoan(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
	}
	
	public Doc_UNSEmployeeLoan(MAcctSchema as, ResultSet rs, String trxName) {
		super(as, MUNSEmployeeLoan.class, rs, null, trxName);
	}

	private MUNSEmployeeLoan m_EmployeeLoan = null;
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() 
	{
		m_EmployeeLoan = (MUNSEmployeeLoan)getPO();
		setDateAcct(m_EmployeeLoan.getTrxDate());
		setDateDoc(m_EmployeeLoan.getRequestDate());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) 
	{
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		I_UNS_Employee employee = m_EmployeeLoan.getUNS_Employee();
		DocLine docLine = new DocLine(m_EmployeeLoan, this);
		docLine.setAmount(m_EmployeeLoan.getLoanAmt());
		
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID (as.getC_Currency_ID());
		FactLine dr = null;
		FactLine cr = null;
		BigDecimal totalPayment = Env.ZERO;
		
		if (MUNSEmployeeLoan.LOANTYPE_Medical.equals(m_EmployeeLoan.getLoanType()))
		{
			if (m_EmployeeLoan.getAllowancePayment().signum() > 0)
			{
				MAccount medicalExpenseAcct = MUNSPayrollConfiguration.getAccountOf(
						getCtx(), MUNSPayrollConfiguration.COLUMNNAME_E_Medical_Expense_Acct, getTrxName());
				
				dr = fact.createLine(null, medicalExpenseAcct,
						getC_Currency_ID(), m_EmployeeLoan.getAllowancePayment(), null);
				dr.setAD_Org_ID(employee.getAD_Org_ID());
				
				totalPayment = totalPayment.add(m_EmployeeLoan.getAllowancePayment());
			}
			
			if (m_EmployeeLoan.getCashPayment().signum() > 0)
			{
				String sql = "SELECT B_Asset_Acct FROM C_BankAccount_Acct " +
						" WHERE C_BankAccount_ID=? AND C_AcctSchema_ID=?";
				
				int cashValidCombination = DB.getSQLValue(
						getTrxName(), sql, m_EmployeeLoan.getC_BankAccount_ID(), as.get_ID());

				if (cashValidCombination <= 0)
					throw new AdempiereException("The accounting combination of cash account has not been initialized.");
				
				MAccount cashAcct = MAccount.get(getCtx(), cashValidCombination);
				
				dr = fact.createLine(null, cashAcct,
						as.getC_Currency_ID(), m_EmployeeLoan.getCashPayment(), null);
				dr.setAD_Org_ID(getAD_Org_ID());

				totalPayment = totalPayment.add(m_EmployeeLoan.getCashPayment());
				
				//if (sectionOfDepartment > 0)
				//	dr.setC_BPartner_ID(sectionOfDepartment);
			}

			MAccount employeeLoanAcct = MUNSPayrollConfiguration.getAccountOf(
					getCtx(), MUNSPayrollConfiguration.COLUMNNAME_P_Employee_Loan_Acct, getTrxName());
			if (dr != null) {
				cr = fact.createLine(null, employeeLoanAcct,
						getC_Currency_ID(), null, totalPayment);
				
				cr.setAD_Org_ID(employee.getAD_Org_ID());
			}
		}
		else // Non Medical Referral. 
		{
			MAccount employeeLoanAcct = MUNSPayrollConfiguration.getAccountOf(
					getCtx(), MUNSPayrollConfiguration.COLUMNNAME_P_Employee_Loan_Acct, getTrxName());
			
			dr = fact.createLine(docLine, employeeLoanAcct,
					as.getC_Currency_ID(), docLine.getAmtSource(), null);
				//
			if (dr == null)
			{
				p_Error = "DR not created: " + docLine;
				log.log(Level.WARNING, p_Error);
				return null;
			}
			dr.setC_BPartner_ID(employee.getC_BPartner_ID());
			dr.setAD_Org_ID(employee.getAD_Org_ID());
			
			int creditValidCombination = 0;
			int koperasiID = 0;
			MAccount creditAcct = null;
			if (MUNSEmployeeLoan.LOANTYPE_Company.equals(m_EmployeeLoan.getLoanType())) 
			{
				String sql = "SELECT B_InTransit_Acct FROM C_BankAccount_Acct " +
						" WHERE C_BankAccount_ID=? AND C_AcctSchema_ID=?";
				
				creditValidCombination = DB.getSQLValue(
						getTrxName(), sql, m_EmployeeLoan.getC_BankAccount_ID(), as.get_ID());

				if (creditValidCombination <= 0)
					throw new AdempiereException("The accounting combination of cash account has not been initialized.");
				
				creditAcct = MAccount.get(getCtx(), creditValidCombination);
			}
			else if (MUNSEmployeeLoan.LOANTYPE_Koperasi.equals(m_EmployeeLoan.getLoanType()))
			{
				koperasiID = 0;//UNSApps.getRefAsInt(UNSApps.BP_KOPERASI);
				if (koperasiID <= 0)
					throw new AdempiereException("BP_KOPERASI context has not been set yet. " +
							"Please contact your System Administrator!");
				
				String sql = "SELECT " + X_C_BP_Vendor_Acct.COLUMNNAME_V_Liability_Acct + " FROM C_BP_Vendor_Acct "
						+ "WHERE C_BPartner_ID=";
				
				creditValidCombination = DB.getSQLValue(getTrxName(), sql);

				if (creditValidCombination <= 0)
					throw new AdempiereException("The accounting combination of liability to koperasi has not been set yet.");
				
				creditAcct = MAccount.get(getCtx(), creditValidCombination);
			}				
				
			cr = fact.createLine(docLine, creditAcct,
					as.getC_Currency_ID(), null,  docLine.getAmtSource());
					//
			if (cr == null)
			{
				p_Error = "DR not created: " + docLine;
				log.log(Level.WARNING, p_Error);
				return null;
			}
			if (koperasiID == 0)
				cr.setC_BPartner_ID(employee.getC_BPartner_ID());
			else
				cr.setC_BPartner_ID(koperasiID);
		}
		
		facts.add(fact);
		return facts;
	}
}