/**
 * 
 */
package com.uns.model.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCostDetail;
import org.compiere.model.MDocType;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.ProductCost;
import org.compiere.util.Env;

import com.uns.base.acct.Doc;
import com.uns.base.acct.DocLine;

/**
 * @author AzHaidar
 *
 */
public class Doc_Inventory extends Doc {

	private int				m_Reversal_ID = 0;
	@SuppressWarnings("unused")
	private String			m_DocStatus = "";

	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_Inventory(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
	}

	/**
	 * 
	 * @param as
	 * @param rs
	 * @param trxName
	 */
	public Doc_Inventory(MAcctSchema as, ResultSet rs, String trxName) {
		super(as, MInventory.class, rs, MDocType.DOCBASETYPE_MaterialPhysicalInventoryImport, trxName);
	} // Doc_Movement

	/**
	 *  Load Document Details
	 *  @return error message or null
	 */
	protected String loadDocumentDetails()
	{
		setC_Currency_ID (NO_CURRENCY);
		MInventory inventory = (MInventory)getPO();
		setDateDoc (inventory.getMovementDate());
		setDateAcct(inventory.getMovementDate());
		m_Reversal_ID = inventory.getReversal_ID();//store original (voided/reversed) document
		m_DocStatus = inventory.getDocStatus();
		//	Contained Objects
		p_lines = loadLines(inventory);
		log.fine("Lines=" + p_lines.length);
		return null;
	}   //  loadDocumentDetails

	/**
	 *	Load Invoice Line
	 *	@param inventory inventory
	 *  @return DocLine Array
	 */
	private DocLine[] loadLines(MInventory inventory)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MInventoryLine[] lines = inventory.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MInventoryLine line = lines[i];
			//	nothing to post
			if (line.getQtyBook().compareTo(line.getQtyCount()) == 0
				&& line.getQtyInternalUse().signum() == 0)
				continue;
			//
			DocLine docLine = new DocLine (line, this);
			BigDecimal Qty = line.getQtyInternalUse();
			if (Qty.signum() != 0)
				Qty = Qty.negate();		//	Internal Use entered positive
			else
			{
				BigDecimal QtyBook = line.getQtyBook();
				BigDecimal QtyCount = line.getQtyCount();
				Qty = QtyCount.subtract(QtyBook);
			}
			docLine.setQty (Qty, false);		// -5 => -5
			docLine.setReversalLine_ID(line.getReversalLine_ID());
			docLine.setAmount(line.getNewCostPrice());

			log.fine(docLine.toString());
			list.add (docLine);
		}

		//	Return Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	//	loadLines

	/**
	 *  Get Balance
	 *  @return Zero (always balanced)
	 */
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  MMI.
	 *  <pre>
	 *  Inventory
	 *      Inventory       DR      CR
	 *      InventoryDiff   DR      CR   (or Charge)
	 *  </pre>
	 *  @param as account schema
	 *  @return Fact
	 */
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		MInventory inventory = (MInventory)getPO();
		if (!MDocType.DOCBASETYPE_MaterialPhysicalInventoryImport.equals(inventory.getC_DocType().getDocBaseType()))
		{
			p_Error = "The integration feature is only for importing initial product/material balance.";
			return null;
		}
		
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID(as.getC_Currency_ID());

		//  Line pointers
		FactLine dr = null;
		FactLine cr = null;
		//if ()

		for (int i = 0; i < p_lines.length; i++)
		{
			DocLine line = p_lines[i];
			
			if (line.getQty().signum() == 0)
				continue;
				
			BigDecimal costs = null;
			if (!isReversal(line))
			{
				// MZ Goodwill
				// if Physical Inventory CostDetail is exist then get Cost from Cost Detail
				//costs = line.getProductCosts(as, line.getAD_Org_ID(), true, "M_InventoryLine_ID=?");
				costs = line.getAmtSource();
				// end MZ
				/*
				if (costs == null || costs.signum() == 0)
				{
					p_Error = "No Costs for " + line.getProduct().getName();
					return null;
				}
				*/
			}
			else
			{
				costs = BigDecimal.ZERO;
			}
			//  Inventory       DR      CR
			dr = fact.createLine(line,
				line.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
				as.getC_Currency_ID(), costs);
			//  may be zero difference - no line created.
			if (dr == null)
				continue;
			dr.setM_Locator_ID(line.getM_Locator_ID());
			if (isReversal(line))
			{
				//	Set AmtAcctDr from Original Phys.Inventory
				if (!dr.updateReverseLine (MInventory.Table_ID,
						m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
				{
					p_Error = "Original Physical Inventory not posted yet";
					return null;
				}
			}

			//  InventoryDiff   DR      CR
			//	or Charge
			MAccount invDiff = null;
			String docBaseType = ((MInventory)getPO()).getC_DocType().getDocBaseType();
			
			if (docBaseType.equals(MDocType.DOCBASETYPE_MaterialPhysicalInventoryImport)
					&& line.getC_Charge_ID() == 0) {
				invDiff = (MAccount) as.getAcctSchemaGL().getOffsetInitialValue_A();
			}
			
			if (invDiff == null) {
				if (isReversal(line) && line.getC_Charge_ID() != 0) {
					invDiff = line.getChargeAccount(as, costs);
				} else {
					invDiff = line.getChargeAccount(as, costs.negate());
				}
			}
			
			if (invDiff == null)
				invDiff = getAccount(Doc.ACCTTYPE_InvDifferences, as);
			
			cr = fact.createLine(line, invDiff, as.getC_Currency_ID(), costs.negate());
			
			if (cr == null)
				continue;
			cr.setM_Locator_ID(line.getM_Locator_ID());
			cr.setQty(line.getQty().negate());
			if (line.getC_Charge_ID() != 0)	//	explicit overwrite for charge
				cr.setAD_Org_ID(line.getAD_Org_ID());

			if (isReversal(line))
			{
				//	Set AmtAcctCr from Original Phys.Inventory
				if (!cr.updateReverseLine (MInventory.Table_ID,
						m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
				{
					p_Error = "Original Physical Inventory not posted yet";
					return null;
				}
				costs = cr.getAcctBalance(); //get original cost
			}

			//	Cost Detail
			if (!MCostDetail.createInventory(as, line.getAD_Org_ID(),
				line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
				line.get_ID(), 0,
				costs, line.getQty(),
				line.getDescription(), getTrxName()))
			{
				p_Error = "Failed to create cost detail record";
				return null;
			}
		}
		//
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}   //  createFact

	private boolean isReversal(DocLine line) {
		return m_Reversal_ID !=0 && line.getReversalLine_ID() != 0;
	}

}   //  Doc_Inventory
