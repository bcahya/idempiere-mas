/**
 * 
 */
package com.uns.model.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MOrg;
import org.compiere.model.MProduct;
import org.compiere.model.ProductCost;
import org.compiere.util.Env;

import com.uns.base.acct.Doc;
import com.uns.base.acct.DocLine;
import com.uns.model.I_C_DocType;
import com.uns.model.MUNSProductionDetail;
import com.uns.qad.model.MUNSQALabTest;
import com.uns.qad.model.MUNSQAMLabResult;
import com.uns.qad.model.MUNSQAMLabResultInfo;
import com.uns.qad.model.MUNSQAMLabResultLine;
import com.uns.qad.model.MUNSQAStatusPRCLine;

/**
 * @author YAKA
 * 
 */
public class Doc_UNSQAMLabResultInfo extends Doc {

	private MUNSQAMLabResultInfo m_labResultInfo = null;

	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSQAMLabResultInfo(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
	}

	/**
	 * Constructor
	 * 
	 * @param as
	 *            accounting schema
	 * @param rs
	 *            record
	 * @param trxName
	 *            trx
	 */
	public Doc_UNSQAMLabResultInfo(MAcctSchema as, ResultSet rs, String trxName) {
		super(as, MUNSQAMLabResultInfo.class, rs, DOCTYPE_UNSQualityAssurance, trxName);
	} // DOC_UNSQualityAssurance

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		if (getPO().get_Table_ID()!=MUNSQAMLabResultInfo.Table_ID)
			return null;
		
		m_labResultInfo = (MUNSQAMLabResultInfo) getPO();
		
		MUNSQAMLabResult m_labResult = getMUNSQAMLabResult();
		
		setDateDoc(m_labResult.getInspectionDate());
		setDateAcct(m_labResult.getInspectionDate());
		
		LoadLines();
		log.fine("Lines=" + p_lines.length);
		
		return null;
	}
	
	private void LoadLines() {
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		for(MUNSQAMLabResultLine line : m_labResultInfo.getLines())
		{
			DocLine docLine = new DocLine(line, this);
			
			docLine.setAmount(BigDecimal.ZERO); //Default is Zero
			list.add(docLine);
		}
		
		p_lines = new DocLine[list.size()];
		list.toArray(p_lines);
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
		
		int AD_OrgTrx_ID = 0;
		MUNSQAMLabResult result = null;
		
		if (getDocumentType().equals(I_C_DocType.DOCBASETYPE_QAD))
		{
			for (int i = 0; i < p_lines.length; i++)
			{
				// Elaine 2008/06/26
				int C_Currency_ID = as.getC_Currency_ID();
				BigDecimal qty = Env.ONE;

				DocLine line = p_lines[i];
				
				if (AD_OrgTrx_ID==0){
					result = getMUNSQAMLabResult();
					MUNSQAStatusPRCLine prcLine = new MUNSQAStatusPRCLine(getCtx(), result.getUNS_QAStatus_PRCLine_ID(), getTrxName());
					MUNSProductionDetail pd = new MUNSProductionDetail(getCtx(), prcLine.getUNS_Production_Detail_ID(), getTrxName());
					
					AD_OrgTrx_ID = pd.getAD_Org_ID();
				}
				
				//Check is QAService
				ProductCost qaServPC = null;
				MUNSQALabTest labTest = MUNSQALabTest.get(getCtx(), line.getUNS_QALabTest_ID());
				MProduct service = MProduct.get(getCtx(), labTest.getProductService_ID());
				qaServPC = new ProductCost(getCtx(), service.get_ID(), 0, getTrxName());
				qaServPC.setQty(Env.ONE);
				qty = Env.ONE;
				
				BigDecimal costs = qaServPC.getProductCosts(as, AD_OrgTrx_ID, null, 0, true);
				
				if (costs == null || costs.signum() == 0)
				{
					//skip post process
					p_Error = "Resubmit - No Costs for " + qaServPC.getProduct().getValue();
					log.log(Level.WARNING, p_Error);
					return null;
				}
				//  Inventory/Asset			DR
				MAccount assetDr = qaServPC.getAccount(ProductCost.ACCTTYPE_P_Expense, as);
				
				dr = fact.createLine(null, assetDr, C_Currency_ID, costs, null);
				//
				if (dr == null)
				{
					p_Error = "DR not created: " + line;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				
				dr.setM_Product_ID(qaServPC.getProduct().get_ID());
				dr.setQty(qty);
				dr.setAD_Org_ID(AD_OrgTrx_ID);
				//dr.setM_Locator_ID(result.getM_Locator_ID());
			}
			
			AD_OrgTrx_ID = MOrg.get(getCtx(), "QAD", getTrxName()).get_ID();
			for (int i = 0; i < p_lines.length; i++)
			{
				// Elaine 2008/06/26
				int C_Currency_ID = as.getC_Currency_ID();
				BigDecimal qty = Env.ONE;
				
				DocLine line = p_lines[i];
				
				//Check is QAService
				ProductCost qaServPC = null;
				MUNSQALabTest labTest = MUNSQALabTest.get(getCtx(), line.getUNS_QALabTest_ID());
				MProduct service = MProduct.get(getCtx(), labTest.getProductService_ID());
				qaServPC = new ProductCost(getCtx(), service.get_ID(), 0, getTrxName());
				qaServPC.setQty(Env.ONE);
				qty = Env.ONE;
				
				BigDecimal costs = qaServPC.getProductCosts(as, AD_OrgTrx_ID, null, 0, true);
				
				if (costs == null || costs.signum() == 0)
				{
					//skip post process
					p_Error = "Resubmit - No Costs for " + qaServPC.getProduct().getValue();
					log.log(Level.WARNING, p_Error);
					return null;
				}
				//  Inventory/Asset			CR
				MAccount assetDr = qaServPC.getAccount(ProductCost.ACCTTYPE_P_Asset, as);
				
				cr = fact.createLine(null, assetDr, C_Currency_ID, null, costs);
				//
				if (cr == null)
				{
					p_Error = "DR not created: " + line;
					log.log(Level.WARNING, p_Error);
					return null;
				}
				
				cr.setM_Product_ID(qaServPC.getProduct().get_ID());
				cr.setQty(qty);
				cr.setAD_Org_ID(AD_OrgTrx_ID);
				//cr.setM_Locator_ID(result.getM_Locator_ID());
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
