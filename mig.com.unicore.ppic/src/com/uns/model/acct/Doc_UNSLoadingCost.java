/**
 * 
 */
package com.uns.model.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MProduct;

import com.uns.base.acct.Doc;
import com.uns.base.acct.DocLine;
import com.uns.base.model.I_C_DocType;
import com.uns.model.MUNSLoadingCost;
import com.uns.model.MUNSLoadingCostLine;
import com.uns.model.MUNSLoadingCostWorker;
import com.uns.model.X_UNS_Employee;

/**
 * @author menjangan
 *
 */
public class Doc_UNSLoadingCost extends Doc {

	public Doc_UNSLoadingCost(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSLoadingCost(MAcctSchema as, ResultSet rs, String trxName) {
		super(as, MUNSLoadingCost.class, rs, null, trxName);
		// TODO Auto-generated constructor stub
	}
	
	private DocLine[] m_DocLinesWorker = null;
	private MUNSLoadingCost m_LoadingCost = null;
	

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		// TODO Auto-generated method stub
		m_LoadingCost = (MUNSLoadingCost) getPO();
		setDateDoc(m_LoadingCost.getDateAcct());
		LoadLines();
		initDocLineWorker();
		log.fine("Lines=" + p_lines.length);		
		return null;
	}
	
	private void LoadLines() {
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		for(MUNSLoadingCostWorker worker : m_LoadingCost.getWorkers())
		{
			X_UNS_Employee employee = new X_UNS_Employee(getCtx(), worker.getLabor_ID(), getTrxName());
			DocLine docLine = new DocLine(worker, this);
			docLine.setC_BPartner_ID(employee.getC_BPartner_ID());
			docLine.setAmount(worker.getReceivableAmt());
			list.add(docLine);
		}
		p_lines = new DocLine[list.size()];
		list.toArray(p_lines);
	}
	
	private void initDocLineWorker() 
	{
		List<DocLine> listOfDocLine = new ArrayList<DocLine>();
		for(MUNSLoadingCostLine lcLine : m_LoadingCost.getLines(true))
		{
			DocLine docLine = new DocLine(lcLine, this);
			docLine.setC_BPartner_ID(lcLine.getC_BPartner_ID());
			docLine.setQty(lcLine.getQty(), false);
			docLine.setAmount(lcLine.getLineNetAmt());
			listOfDocLine.add(docLine);
		}
		m_DocLinesWorker = new DocLine[listOfDocLine.size()];
		listOfDocLine.toArray(m_DocLinesWorker);
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		// TODO Auto-generated method stub
		return BigDecimal.ZERO;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		// TODO Auto-generated method stub
		ArrayList<Fact> facts = new ArrayList<Fact>();
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID (as.getC_Currency_ID());

		//  Line pointers
		FactLine dr = null;
		FactLine cr = null;
		
		if (getDocumentType().equals(I_C_DocType.DocBaseType_LoadingCost)
				|| getDocumentType().equals(I_C_DocType.DocBaseType_BongkarMuat))
		{			
			for(DocLine docLineWorker : m_DocLinesWorker)
			{
				// Elaine 2008/06/26
				int C_Currency_ID = as.getC_Currency_ID();
				//
				DocLine line = docLineWorker;
				
				BigDecimal costs = null;
				MProduct product = line.getProduct();

					costs = line.getAmtSource();

				if (costs == null || costs.signum() == 0)
				{
					p_Error = "Resubmit - No Costs for " + product.getName();
					log.log(Level.WARNING, p_Error);
					return null;
				}
				//  Inventory/Asset			DR
				//1000217 -- biaya hutang gaji
				//1000273 -- biaya distribusi						
				MAccount assetDr = MAccount.get(getCtx(), getAD_Client_ID(), getAD_Org_ID(), as.getC_AcctSchema_ID()
						, 1000273, 0, line.getM_Product_ID(), line.getC_BPartner_ID(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
						
				dr = fact.createLine(line, assetDr,
					C_Currency_ID, costs, null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + line;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				dr.setC_BPartner_ID(line.getC_BPartner_ID());
				dr.setLocationFromBPartner(getC_BPartner_Location_ID(), true); 
				dr.setLocationFromLocator(line.getM_Locator_ID(), false); 
				dr.setQty(line.getQty());
			}
			
			for (int i = 0; i < p_lines.length; i++)
			{
				// Elaine 2008/06/26
				int C_Currency_ID = as.getC_Currency_ID();
				//
				DocLine line = p_lines[i];
				
				BigDecimal costs = null;
				MProduct product = line.getProduct();

					costs = line.getAmtSource();

				if (costs == null || costs.signum() == 0)
				{
					p_Error = "Resubmit - No Costs for " + product.getName();
					log.log(Level.WARNING, p_Error);
					return null;
				}
				
				MAccount assetCR = MAccount.get(
						getCtx(), getAD_Client_ID(), getAD_Org_ID(), as.getC_AcctSchema_ID(), 1000217, 0
						, line.getM_Product_ID(), line.getC_BPartner_ID(),0, 0, 0, 0
						, 0, 0, 0, 0, 0
						, 0, 0);
	
				cr = fact.createLine(line,assetCR,
					C_Currency_ID, null, costs);
	
				if (cr == null)
				{
					p_Error = "CR not created: " + line;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				cr.setC_BPartner_ID(line.getC_BPartner_ID());
				cr.setM_Locator_ID(line.getM_Locator_ID());
				cr.setLocationFromBPartner(getC_BPartner_Location_ID(), true);   //  from Loc
				cr.setLocationFromLocator(line.getM_Locator_ID(), false);   //  to Loc
				cr.setQty(line.getQty());
			}
		}	
		else
		{
			p_Error = "DocumentType unknown: " + getDocumentType();
			log.log(Level.SEVERE, p_Error);
			return null;
		}
		//
		facts.add(fact);
		return facts;
	}

}
