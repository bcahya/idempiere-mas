/**
 * 
 */
package com.unicore.base.doc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCostDetail;
import org.compiere.model.MMovementLineConfirm;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MProduct;

import com.unicore.base.model.MMovementConfirm;
import com.unicore.base.model.MMovementLine;

/**
 * @author menjangan
 *
 */
public class Doc_MovementConfirm extends Doc {

	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_MovementConfirm(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
	}
	
	public Doc_MovementConfirm(MAcctSchema as, ResultSet rs, String trxName)
	{
		super(as, MMovementConfirm.class, rs, null, trxName);
	}
	
	private MMovementConfirm m_confirm;
	private int m_reversalID;

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		m_confirm = (MMovementConfirm) getPO();
		m_reversalID = m_confirm.get_ValueAsInt("Reversal_ID");
		Timestamp movementDate = m_confirm.getM_Movement().getMovementDate();
		
		setDateDoc(movementDate);
		setDateAcct(movementDate);
		
		String confirmType = m_confirm.getConfirmationType();
		if(confirmType == null 
				|| MMovementConfirm.CONFIRMATIONTYPE_InternalWarehouseConfirmation.equals(confirmType)
				|| MMovementConfirm.CONFIRMATIONTYPE_HeadOfficeConfirmation.equals(confirmType))
			return null;
		
		p_lines = loadLines();
		if (log.isLoggable(Level.FINE)) log.fine("Lines=" + p_lines.length);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}

	/* (non-Javadoc)
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) {
		
		ArrayList<Fact> facts = new ArrayList<Fact>();
		String confirmType = m_confirm.getConfirmationType();
		if(confirmType == null 
				|| MMovementConfirm.CONFIRMATIONTYPE_InternalWarehouseConfirmation.equals(confirmType)
				|| MMovementConfirm.CONFIRMATIONTYPE_HeadOfficeConfirmation.equals(confirmType))
			return facts;
		
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID(as.getC_Currency_ID());

		//  Line pointers
		FactLine dr = null;
		FactLine cr = null;

		for (int i = 0; i < p_lines.length; i++)
		{
			DocLine line = p_lines[i];
			BigDecimal costs = null;
			MProduct product = MProduct.get(getCtx(), line.getM_Product_ID());
			
			if (!isReversal(line))
			{
				String sql = "SELECT amt/qty FROM M_CostDetail WHERE "
						+ " M_MovementLine_ID = ? AND AD_Org_ID = ?";
				costs = DB.getSQLValueBD(getTrxName(), sql, ((MMovementLineConfirm)p_lines[i].
						getPO()).getM_MovementLine_ID(),p_lines[i].getAD_Org_ID());
				if (null != costs)
				{
					costs = costs.multiply(p_lines[i].getQty()).abs();
				}
			}
			else
			{
				costs = Env.ZERO;
			}
			
			if (costs == null)
			{
				p_Error = "No Costs for " + line.getProduct().getName();
				log.log(Level.WARNING, p_Error);
				return null;
			}
	
			dr = fact.createLine(line,
				line.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
				as.getC_Currency_ID(), costs, null);
			if (dr == null)
				continue;
			
			dr.setM_Locator_ID(line.getM_Locator_ID());
			dr.setQty(line.getQty().negate());	//	outgoing
			
			if (isReversal(line))
			{
				//	Set AmtAcctDr from Original Move Confirm.
				if (!dr.updateReverseLine (MMovementConfirm.Table_ID,
						m_reversalID, line.getReversalLine_ID(),Env.ONE))
				{
					if (!product.isStocked())	
					{ //	ignore service
						fact.remove(dr);
						continue;
					}
					p_Error = "Original Move Confirm not posted yet";
					return null;
				}
			}
			
			cr = fact.createLine(line,
				line.getAccount(ProductCost.ACCTTYPE_P_AssetIntransit, as),
				as.getC_Currency_ID(), null, costs);
			if (cr == null)
				continue;
			cr.setM_Locator_ID(line.getM_Locator_ID());
			cr.setQty(line.getQty());
			

			if (isReversal(line))
			{
				//	Set AmtAcctDr from Original Move Confirm.
				if (!cr.updateReverseLine (MMovementConfirm.Table_ID,
						m_reversalID, line.getReversalLine_ID(),Env.ONE))
				{
					if (!product.isStocked())	
					{ //	ignore service
						fact.remove(cr);
						continue;
					}
					p_Error = "Original Move Confirm not posted yet";
					return null;
				}
			}
			
			//	Only for between-org movements
			if (dr.getAD_Org_ID() != cr.getAD_Org_ID())
			{
				String costingLevel = line.getProduct().getCostingLevel(as);
				if (!MAcctSchema.COSTINGLEVEL_Organization.equals(costingLevel))
					continue;
				//
				String description = line.getDescription();
				if (description == null)
					description = "";
				//	Cost Detail From
				if (!MCostDetail.createMovementConfirm(as, dr.getAD_Org_ID(), 	//	locator org
					line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
					line.get_ID(), 0,
					costs.negate(), line.getQty().negate(), true,
					description + "(|->)", getTrxName()))
				{
					p_Error = "Failed to create cost detail record";
					return null;
				}
				//	Cost Detail To
				if (!MCostDetail.createMovementConfirm(as, cr.getAD_Org_ID(),	//	locator org
					line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
					line.get_ID(), 0,
					costs, line.getQty(), false,
					description + "(|<-)", getTrxName()))
				{
					p_Error = "Failed to create cost detail record";
					return null;
				}
			}
		}

		facts.add(fact);
		return facts;
	}
	
	private DocLine[] loadLines()
	{
		List<DocLine> lineList = new ArrayList<DocLine>();
		MMovementLineConfirm[] cl = m_confirm.getLines(true);
		
		for(int i=0; i<cl.length; i++)
		{
			MMovementLineConfirm confirm = cl[i];
			MMovementLine req = new MMovementLine(getCtx(), confirm.getM_MovementLine_ID(), getTrxName());
			
			DocLine dl = new DocLine(confirm, this);
			dl.setReversalLine_ID(confirm.get_ValueAsInt("ReversalLine_ID"));
			
			int product = req.getM_Product_ID();
			int locator = req.getM_LocatorTo_ID() > 0 ? req.getM_LocatorTo_ID() : req.getM_Locator_ID();

			int asi = req.getM_AttributeSetInstanceTo_ID() > 0 
					? req.getM_AttributeSetInstanceTo_ID() : req.getM_AttributeSetInstance_ID();
			
			dl.setM_Product_ID(product);
			dl.setM_Locator_ID(locator);
			dl.setM_AttributesetInstance_ID(asi);
			
			dl.setQty(confirm.getConfirmedQty(), false);
			if (log.isLoggable(Level.FINE)) log.fine(dl.toString());
			lineList.add(dl);
		}
		
		DocLine[] lines = new DocLine[lineList.size()];
		lines = lineList.toArray(lines);
		return lines;
	}

	public boolean isReversal()
	{
		return isReversal(p_lines[0]);
	}

	private boolean isReversal(DocLine line) 
	{
		return m_reversalID !=0 && line.getReversalLine_ID() != 0;
	}
}
