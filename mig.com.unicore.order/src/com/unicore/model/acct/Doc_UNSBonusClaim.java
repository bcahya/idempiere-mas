/**
 * 
 */
package com.unicore.model.acct;

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
import org.compiere.model.ProductCost;
import org.compiere.util.Env;

import com.unicore.model.MUNSBonusClaim;
import com.unicore.model.MUNSBonusClaimLine;

/**
 * @author root
 *
 */
public class Doc_UNSBonusClaim extends Doc {

	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSBonusClaim(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
	}
	
	public Doc_UNSBonusClaim(MAcctSchema as, ResultSet rs, String trxName)
	{
		super(as, MUNSBonusClaim.class, rs, null, trxName);
	}
	
	private MUNSBonusClaim m_model = null;

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		m_model = (MUNSBonusClaim) getPO();
		this.setC_Currency_ID(m_model.getC_Currency_ID());
		this.setDateAcct(m_model.getDateAcct());
		this.setDateDoc(m_model.getDateDoc());
		this.setC_BPartner_ID(m_model.getC_BPartner_ID());
		this.setDocumentType(m_model.getDocTypeInvoice().getDocBaseType());
		this.loadLines();
		return null;
	}
	
	private void loadLines() 
	{
		MUNSBonusClaimLine[] lines = m_model.getLines();
		ArrayList<DocLine> listClaimLine = new ArrayList<DocLine>();
		
		for(MUNSBonusClaimLine line : lines)
		{
			DocLine dl = new DocLine(line, this);
			dl.setAmount(Env.ZERO);
			dl.setQty(line.getQtyClaimed(), m_model.isSOTrx());
			listClaimLine.add(dl);
		}
		
		p_lines = new DocLine[listClaimLine.size()];
		listClaimLine.toArray(p_lines);
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
		
		for(DocLine line : p_lines)
		{
			Fact fact = new Fact(this, as, Fact.POST_Actual);
			MUNSBonusClaimLine claimLine = (MUNSBonusClaimLine) line.getPO();
			if(claimLine.getQtyClaimed().signum() == 0)
				continue;
			
			MAccount crAcct = null;
			MAccount drAcct = null;
			
			if(m_model.isSOTrx())
			{
				crAcct = line.getAccount(ProductCost.ACCTTYPE_P_BonusSales, as);
				drAcct = line.getAccount(ProductCost.ACCTTYPE_P_BonusSalesNotShip, as);
			}
			else
			{
				crAcct = line.getAccount(ProductCost.ACCTTYPE_P_Bonus, as);
				drAcct = line.getAccount(ProductCost.ACCTTYPE_P_BonusNotReceipt, as);
			}
			
			FactLine cr = fact.createLine(line, crAcct, getC_Currency_ID(), claimLine.getAmtClaimed().negate());
			
			if(cr == null)
			{
				p_Error = "CR not created: " + line;
				log.log(Level.WARNING, p_Error);
				return null;
			}
			
			cr.setM_Product_ID(claimLine.getProductBonus_ID());
			
			FactLine dr = fact.createLine(line, drAcct, getC_Currency_ID(), claimLine.getAmtClaimed());
			
			if(dr == null)
			{
				p_Error = "DR not created: " + line;
				log.log(Level.WARNING, p_Error);
				return null;
			}
			
			dr.setM_Product_ID(claimLine.getProductBonus_ID());
			
			facts.add(fact);
		}
		
		return facts;
	}
}
