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
import org.compiere.model.I_M_InOut;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MProduct;
import org.compiere.model.ProductCost;
import org.compiere.util.Env;

import com.uns.base.acct.Doc;
import com.uns.base.acct.DocLine;
import com.uns.base.model.I_C_DocType;
import com.uns.base.model.MCostDetail;
import com.uns.model.MUNSBongkarMuat;
import com.uns.model.MUNSBongkarMuatLine;
import com.uns.model.MUNSBongkarMuatWorker;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.X_UNS_Employee;

/**
 * @author menjangan
 *
 */
public class Doc_UNSBongkarMuat extends Doc {
	
	/**
	 * @param ass
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSBongkarMuat(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, MUNSBongkarMuat.class, rs, null, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public Doc_UNSBongkarMuat(MAcctSchema as, ResultSet rs, String trxName)
	{
		super (as, MUNSBongkarMuat.class, rs, null,trxName);
	}

	private int m_ReversalID = 0;
	private String m_DocStatus = "";
//	private DocLine[] m_costLines = null;
	private MUNSBongkarMuat m_BongkarMuat = null;

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		m_BongkarMuat = (MUNSBongkarMuat) getPO();
		setDateDoc(m_BongkarMuat.getDateAcct());
		m_ReversalID = m_BongkarMuat.getReversal_ID();
		m_DocStatus = m_BongkarMuat.getDocStatus();
		p_lines = LoadWorkerLines();
//		m_costLines = LoadLines(bongkarMuat);
		log.fine("Lines=" + p_lines.length);
		return null;
		// TODO Auto-generated method stub
	}
	
	protected DocLine[] LoadWorkerLines() {
		ArrayList<DocLine> list = new ArrayList<DocLine>();
	
		for (MUNSBongkarMuatWorker worker : m_BongkarMuat.getWorkers())
		{
			X_UNS_Employee employee = new X_UNS_Employee(getCtx(), worker.getLabor_ID(), getTrxName());
			DocLine docLine = new DocLine(worker, this);
			docLine.setC_BPartner_ID(employee.getC_BPartner_ID());
			docLine.setAmount(worker.getReceivableAmt());
//			docLine.setReversalLine_ID(worker.getReversalLine_ID());
			list.add(docLine);
		}
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}

	/*
	protected DocLine[] LoadLines(MUNSBongkarMuat bongkarMuat) {
		ArrayList<DocLine> list = new ArrayList<DocLine>();
	
		for (MUNSBongkarMuatLine line : bongkarMuat.getLines())
		{
			//X_UNS_Employee employee = new X_UNS_Employee(getCtx(), line.get_ID(), getTrxName());
			DocLine docLine = new DocLine(line, this);
			docLine.setAmount(line.getLineNetAmt());
			docLine.setQty(line.getQty(), false);
			docLine.setReversalLine_ID(line.getReversalLine_ID());
			list.add(docLine);
		}
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}
	*/

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		// TODO Auto-generated method stub
		BigDecimal retValue = Env.ZERO;
		return retValue;
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
		
		if (!getDocumentType().equals(I_C_DocType.DocBaseType_BongkarMuat))
		{
			p_Error = "DocumentType unknown: " + getDocumentType();
			log.log(Level.SEVERE, p_Error);
			return null;
		}
		
		MUNSBongkarMuatLine mLines[] = m_BongkarMuat.getLines();

		for (int i = 0; i < mLines.length; i++)
		{
			// Elaine 2008/06/26
			int C_Currency_ID = as.getC_Currency_ID();
			//create docLine
			DocLine line = new DocLine(mLines[i], this);
			line.setAmount(mLines[i].getLineNetAmt());
			line.setQty(BigDecimal.ZERO, false);
			line.setReversalLine_ID(mLines[i].getReversalLine_ID());
			
			BigDecimal costs = null;
			MProduct product = line.getProduct();
			//get costing method for product
			
			if (m_DocStatus.equals(MUNSBongkarMuat.DOCSTATUS_Reversed) && m_ReversalID !=0 && line.getReversalLine_ID() != 0)
				costs = BigDecimal.ZERO;
			else
				costs = line.getAmtSource();
				

			if (costs == null || costs.signum() == 0)
			{
				p_Error = "Resubmit - No Costs for " + product.getName();
				log.log(Level.WARNING, p_Error);
				return null;
			}
			
			I_M_InOut inout = mLines[i].getM_InOut();
			//  Inventory/Asset			DR
			dr = fact.createLine(line, line.getProductCost().getAccount(ProductCost.ACCTTYPE_P_CostAdjustment, as), 
								 C_Currency_ID, costs, null);
			//
			if (dr == null)
			{
				p_Error = "DR not created: " + line;
				log.log(Level.WARNING, p_Error);
				return null;
			}
			dr.setAD_Org_ID(inout.getAD_Org_ID());
			
			if (m_DocStatus.equals(MUNSBongkarMuat.DOCSTATUS_Reversed) && m_ReversalID !=0 && line.getReversalLine_ID() != 0)
			{
				//	Set AmtAcctDr from Original Shipment/Receipt
				if (!dr.updateReverseLine (MUNSBongkarMuat.Table_ID, m_ReversalID, line.getReversalLine_ID(),Env.ONE))
				{
					p_Error = "Original Receipt not posted yet";
					return null;
				}
			}

			if (!MCostDetail.createUnloadingAdjustment(
					as, inout.getAD_Org_ID(),
					line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
					line.get_ID(), 0, costs, line.getQty(),
					"**Adjustment From unloading document**", getTrxName()))
			{
				p_Error = "Failed to create cost detail record";
				return null;
			}
		}

		for (int i = 0; i < p_lines.length; i++)
		{
			// Elaine 2008/06/26
			int C_Currency_ID = as.getC_Currency_ID();
			//
			DocLine line = p_lines[i];
			
			BigDecimal costs = null;
			MProduct product = line.getProduct();
			//get costing method for product
			
			if (m_DocStatus.equals(MUNSBongkarMuat.DOCSTATUS_Reversed) && m_ReversalID !=0)
				costs = BigDecimal.ZERO;
			else
				costs = line.getAmtSource();

			if (costs == null || costs.signum() == 0)
			{
				p_Error = "Resubmit - No Costs for " + product.getName();
				log.log(Level.WARNING, p_Error);
				return null;
			}
			//  Inventory/Asset			DR
			
			MAccount assetCr = MAccount.get(as.getCtx(), 
					MUNSPayrollConfiguration.getHutangUpahBuruhDirectAcct(getCtx(), getTrxName()).getC_ValidCombination_ID());
			
			cr = fact.createLine(line, assetCr, C_Currency_ID, null, costs);

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

			if (m_DocStatus.equals(MUNSBongkarMuat.DOCSTATUS_Reversed) && m_ReversalID !=0 && line.getReversalLine_ID() != 0)
			{
				//	Set AmtAcctDr from Original Shipment/Receipt
				if (!dr.updateReverseLine (MUNSBongkarMuat.Table_ID, m_ReversalID, line.getReversalLine_ID(),Env.ONE))
				{
					p_Error = "Original Receipt not posted yet";
					return null;
				}
			}
		}
		//
		facts.add(fact);
		return facts;
	}
}
