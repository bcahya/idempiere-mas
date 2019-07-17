/**
 * 
 */
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
import org.compiere.model.MCostElement;

import com.uns.base.model.MCostDetail;
import com.uns.model.I_UNS_Employee;
import com.uns.model.MUNSUtilitiesUses;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author menjangan
 *
 */
public class Doc_UNSUtilitiesUses extends Doc {

	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSUtilitiesUses(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
		// TODO Auto-generated constructor stub
	}

	public Doc_UNSUtilitiesUses(MAcctSchema as, ResultSet rs, String trxName)
	{
		super (as, MUNSUtilitiesUses.class, rs, null,trxName);
	}

	
	private MUNSUtilitiesUses m_UtilitiesUse = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		// TODO Auto-generated method stub
		m_UtilitiesUse = (MUNSUtilitiesUses)getPO();
		setDateDoc(m_UtilitiesUse.getUpdated());
		setDateAcct(m_UtilitiesUse.getUpdated());
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		// TODO Auto-generated method stub
		return m_UtilitiesUse.getCost();
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@SuppressWarnings("deprecation")
  @Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		// TODO Auto-generated method stub
		ArrayList<Fact> facts = new ArrayList<Fact>();
		DocLine docLine = new DocLine(m_UtilitiesUse, this);
		docLine.setAmount(m_UtilitiesUse.getCost());
		int sectionOfDepartment = 0;
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID (as.getC_Currency_ID());
		FactLine dr = null;
		FactLine cr = null;
		int debitAcct_ID = 0;
		int creditAcct_ID = 0;
		BigDecimal cost = docLine.getAmtSource();
		
		MCostDetail costDetail = MCostDetail.get(
				getCtx(), MUNSUtilitiesUses.COLUMNNAME_UNS_Utilities_Uses_ID + " = ?"
				, UNSHRMModelFactory.getExtensionID()
				, docLine.get_ID(), docLine.getM_AttributeSetInstance_ID()
				, as.get_ID(), getTrxName());
		
		if (null != costDetail)
			cost = costDetail.getAmt();
		else
			cost = m_UtilitiesUse.getCost();
		
		
		if (m_UtilitiesUse.getPayableTo().equals(MUNSUtilitiesUses.PAYABLETO_Employee))
		{
			debitAcct_ID = 1000217;
			creditAcct_ID = 1000105;
			I_UNS_Employee employee = m_UtilitiesUse.getUNS_Employee();
			sectionOfDepartment = employee.getC_BPartner_ID();
		}
		else if(m_UtilitiesUse.getPayableTo().equals(MUNSUtilitiesUses.PAYABLETO_Office))
		{
			debitAcct_ID = 1000217;
			creditAcct_ID = 1000105;
		}
		else if(m_UtilitiesUse.getPayableTo().equals(MUNSUtilitiesUses.PAYABLETO_SumateraTimurIndonesia))
		{
			debitAcct_ID = 1000217;
			creditAcct_ID = 1000105;
		}
		
		else
		{
			return facts;
		}

			MAccount accountDebit = MAccount.get(
					getCtx(), getAD_Client_ID(), getAD_Org_ID(), as.get_ID()
					, debitAcct_ID, 0, 0, sectionOfDepartment
					, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			
			dr = fact.createLine(docLine, accountDebit,
					as.getC_Currency_ID(), cost , null);
				//
			if (dr == null)
			{
				p_Error = "DR not created: " + docLine;
				log.log(Level.WARNING, p_Error);
				return null;
			}
			dr.setC_BPartner_ID(sectionOfDepartment);
				
				
			MAccount accountCredit = MAccount.get(
						getCtx(), getAD_Client_ID(), getAD_Org_ID(), as.get_ID()
						, creditAcct_ID, 0, 0, sectionOfDepartment
						, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
				
			cr = fact.createLine(docLine, accountCredit,
					as.getC_Currency_ID(), null, cost );
					//
			if (cr == null)
			{
				p_Error = "DR not created: " + docLine;
				log.log(Level.WARNING, p_Error);
				return null;
			}
			cr.setC_BPartner_ID(sectionOfDepartment);
			
			MCostElement[] costElements = MCostElement.getNonCostingMethods(m_UtilitiesUse);
			MCostElement costElement = null;
			for (int i=0; i < costElements.length;)
			{
				costElement = costElements[i];
				break;
			}
		if(!MCostDetail.createUtilitiesUses(
				as, docLine.getAD_Org_ID(),UNSHRMModelFactory.getExtensionID(), docLine.getM_Product_ID()
				, docLine.getM_AttributeSetInstance_ID(), docLine.get_ID()
				, costElement.get_ID(), docLine.getAmtSource(), docLine.getQty(), null, getTrxName()));

		facts.add(fact);
		return facts;
	}

}
