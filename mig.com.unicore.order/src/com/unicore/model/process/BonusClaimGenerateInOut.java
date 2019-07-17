/**
 * 
 */
package com.unicore.model.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.unicore.base.model.MInOut;
import com.unicore.base.model.MInOutLine;
import com.unicore.model.MUNSBonusClaim;
import com.unicore.model.MUNSBonusClaimLine;

/**
 * @author root
 *
 */
public class BonusClaimGenerateInOut extends SvrProcess {

	/**
	 * 
	 */
	public BonusClaimGenerateInOut() {
		super();
	}
	
	private String m_documentNo = null;
	private MUNSBonusClaim m_model = null;
	private int m_warehouseID	= 0;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] pis = getParameter();
		for(ProcessInfoParameter pi : pis)
		{
			String name = pi.getParameterName();
			if(null == name)
				continue;
			else if(name.equals("DocumentNo"))
				m_documentNo = pi.getParameterAsString();
			else if(name.equals("M_Warehouse_ID"))
				m_warehouseID = pi.getParameterAsInt();
			else 
				log.log(Level.SEVERE, "Unknown parameter " + name);
				
		}
		
		m_model = new MUNSBonusClaim(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		try
		{
			MInOut inout = new MInOut(m_model);
			inout.setM_Warehouse_ID(m_warehouseID);
			if(m_documentNo != null)
				inout.setDocumentNo(m_documentNo);
			inout.saveEx();
			
			int defaultLocator_ID = DB.getSQLValue(
					get_TrxName(), "SELECT M_Locator_ID FROM M_Locator WHERE M_Warehouse_ID = ? AND IsDefault = ?", m_warehouseID, "Y");
			if(defaultLocator_ID <= 0)
			{
				String whsName = DB.getSQLValueString(get_TrxName(), "SELECT Name FROM M_Warehouse WHERE M_Warehouse_ID = ? ", m_warehouseID);
				throw new AdempiereException("No default locator of warehouse " + whsName);
			}
			for(MUNSBonusClaimLine claimLine : m_model.getLines(true, null))
			{
				if(claimLine.getDecisionConfirm().equals(MUNSBonusClaimLine.DECISIONCONFIRM_Discard))
					continue;
				
				MInOutLine line = new MInOutLine(inout);
				line.setAD_Org_ID(claimLine.getAD_Org_ID());
				line.setM_Product_ID(claimLine.getProductBonus_ID());
				line.setC_UOM_ID(claimLine.getUOMBonus_ID());
				line.setM_Locator_ID(defaultLocator_ID);
				line.setM_AttributeSetInstance_ID(MAttributeSetInstance.create(
						getCtx(), (MProduct)claimLine.getProductBonus(), get_TrxName()).get_ID());
				line.setQty(claimLine.getQtyClaimed());
				line.saveEx();				
			}
		} catch (Exception ex)
		{
			log.log(Level.SEVERE, ex.getMessage());
			throw new AdempiereException("Could not create ship/receipt : " + ex.getMessage());
		}
		
		return "Success";
	}

}
