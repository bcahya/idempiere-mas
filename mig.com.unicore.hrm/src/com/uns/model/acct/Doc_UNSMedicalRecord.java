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
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.I_UNS_Contract_Recommendation;
import com.uns.model.I_UNS_Employee;
import com.uns.model.MUNSContractRecommendation;
import com.uns.model.MUNSMedicalRecord;
import com.uns.model.MUNSPayrollConfiguration;

/**
 * @author menjangan
 *
 */
public class Doc_UNSMedicalRecord extends Doc {

	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSMedicalRecord(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
	}
	
	public Doc_UNSMedicalRecord(MAcctSchema as,ResultSet rs,String trxName)
	{
		super(as,MUNSMedicalRecord.class,rs,null,trxName);
	}
	
	private MUNSMedicalRecord m_MedicalRecord = null;

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		m_MedicalRecord = (MUNSMedicalRecord)getPO();
		setDateDoc(m_MedicalRecord.getUpdated());
		setDateAcct(m_MedicalRecord.getmedical_date());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		return m_MedicalRecord.getCashPayment().add(m_MedicalRecord.getPayrollPayment());
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) 
	{
		ArrayList<Fact> facts = new ArrayList<Fact>();
		int sectionOfDepartment = 0;
		I_UNS_Employee employee = null;
		
		if (m_MedicalRecord.getC_InvoiceLine_ID() > 0)
			return null;
		
		if (m_MedicalRecord.getUNS_Employee_ID() > 0)
		{
			employee = m_MedicalRecord.getUNS_Employee();
			sectionOfDepartment = employee.getC_BPartner_ID();
		}
		
		BigDecimal cashPayment = m_MedicalRecord.getCashPayment();
		BigDecimal payrollPayment = Env.ZERO;
		
		if (!m_MedicalRecord.ishospital_referal())
			payrollPayment = m_MedicalRecord.getPayrollPayment();
		
		if ((null == cashPayment || cashPayment.signum() <= 0) 
			&& (null == payrollPayment || payrollPayment.signum() <= 0))
			return facts;
		
		FactLine dr = null;
		FactLine cr = null;
		
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID (as.getC_Currency_ID());

		if(null != cashPayment && cashPayment.signum() > 0)
		{
			String sql = "SELECT B_Asset_Acct FROM C_BankAccount_Acct " +
					" WHERE C_BankAccount_ID=? AND C_AcctSchema_ID=?";
			
			int cashValidCombination = DB.getSQLValue(
					getTrxName(), sql, m_MedicalRecord.getC_BankAccount_ID(), as.get_ID());

			if (cashValidCombination <= 0)
				throw new AdempiereException("The accounting combination of cash account has not been initialized.");
			
			MAccount cashAcct = MAccount.get(getCtx(), cashValidCombination);
			
			dr = fact.createLine(null, cashAcct,
					as.getC_Currency_ID(), cashPayment, null);
			dr.setAD_Org_ID(getAD_Org_ID());

			//if (sectionOfDepartment > 0)
			//	dr.setC_BPartner_ID(sectionOfDepartment);
		}
		
		// Hutang Gaji.
		if(null != payrollPayment && payrollPayment.signum() > 0 && !m_MedicalRecord.ishospital_referal())
		{
			String hutangAcctToPost = MUNSPayrollConfiguration.COLUMNNAME_HutangGajiBulananAcct_ID;
			I_UNS_Contract_Recommendation contract = employee.getUNS_Contract_Recommendation();
			
			if (MUNSContractRecommendation.NEXTCONTRACTTYPE_BoronganCV.equals(
					contract.getNextContractType()))
				hutangAcctToPost = MUNSPayrollConfiguration
									.COLUMNNAME_HutangUpahBuruhDirectAcct_ID;

			MAccount hutangPayrollAcct = MUNSPayrollConfiguration.getAccountOf(
					getCtx(), hutangAcctToPost, getTrxName());
			
			dr = fact.createLine(null, hutangPayrollAcct,
					as.getC_Currency_ID(), payrollPayment, null);
			dr.setAD_Org_ID(employee.getAD_Org_ID());

			if (sectionOfDepartment > 0)
				dr.setC_BPartner_ID(sectionOfDepartment);
		}
		
		DocLine docLine = new DocLine(m_MedicalRecord, this);
		docLine.setAmount(cashPayment.add(payrollPayment));
		
		MAccount obatAcct = MUNSPayrollConfiguration.getAccountOf(
				getCtx(), MUNSPayrollConfiguration.COLUMNNAME_P_Obat_Acct, getTrxName());
		
		cr = fact.createLine(docLine, obatAcct,
				as.getC_Currency_ID(), null, docLine.getAmtSource());
				//
		
		if (cr == null)
		{
			p_Error = "DR not created: " + docLine;
			log.log(Level.WARNING, p_Error);
			return null;
		}
		if (sectionOfDepartment > 0)
			cr.setC_BPartner_ID(sectionOfDepartment);

		facts.add(fact);
		
		return facts;
	}

}
