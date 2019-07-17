package com.uns.model.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCharge;
import org.compiere.util.Env;

import com.uns.model.MUNSLoanInstallment;
import com.uns.model.MUNSPayrollConfiguration;

/**
 * 
 * @author root
 * @see www.untasoft.com
 */
public class Doc_UNSLoanInstallment extends Doc {
	
	private MUNSLoanInstallment m_model = null;

	public Doc_UNSLoanInstallment(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) 
	{
		super(as, clazz, rs, defaultDocumentType, trxName);
	}
	
	public Doc_UNSLoanInstallment (MAcctSchema as, ResultSet rs , String trxName)
	{
		this (as, MUNSLoanInstallment.class, rs, null, trxName);
	}

	@Override
	protected String loadDocumentDetails() {
		m_model = (MUNSLoanInstallment) getPO();
		setDateAcct(m_model.getPaidDate());
		setDateDoc(m_model.getPaidDate());
		setC_BPartner_ID(m_model.getUNS_Employee_Loan().getUNS_Employee_ID());
		
		return null;
	}

	@Override
	public BigDecimal getBalance() {
		return Env.ZERO;
	}

	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		ArrayList<Fact> facts = new ArrayList<>();
		if (!m_model.isCashPayment())
			return facts;
		
		setC_Currency_ID(as.getC_Currency_ID());
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		
		DocLine dl = new DocLine(m_model, this);
		BigDecimal amt = m_model.getPaidAmt();
		
		MAccount drAcct = MCharge.getAccount(m_model.getC_Charge_ID(), as);
		
		FactLine dr = fact.createLine(null, drAcct, getC_Currency_ID(), amt);
		
		if (null == dr)
		{
			p_Error = "DR not created: " + dl;
			log.log(Level.WARNING, p_Error);
			return null;
		}
		
		MAccount cdAcct = MUNSPayrollConfiguration.getAccountOf(getCtx(), 
				MUNSPayrollConfiguration.COLUMNNAME_P_Employee_Loan_Acct, 
				getTrxName());
		
		FactLine cr = fact.createLine(dl, cdAcct, getC_Currency_ID(), 
				amt.negate());
		
		if (null == cr)
		{
			p_Error = "CR not created: " + dl;
			log.log(Level.WARNING, p_Error);
			return null;
		}
		
		facts.add(fact);
		return facts;
	}

}
