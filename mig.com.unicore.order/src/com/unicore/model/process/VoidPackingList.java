/**
 * 
 */
package com.unicore.model.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Util;

import com.unicore.model.MUNSPackingList;
import com.unicore.model.MUNSPackingListLine;
import com.unicore.model.MUNSPackingListOrder;

/**
 * @author AzHaidar, ALBURHANY
 *
 */
public class VoidPackingList extends SvrProcess 
{

	private String m_Reason = null;
	private MUNSPackingList m_PL = null;
	private boolean m_CreateReplacement;
	private StringBuilder errMsg = new StringBuilder();
	
	/**
	 * 
	 */
	public VoidPackingList() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++)
        {
            String name = para[i].getParameterName();
            if (name.equals("Reason"))
                m_Reason = para[i].getParameterAsString();
            else if (name.equals("isCreateReplacement"))
            	m_CreateReplacement = para[i].getParameterAsBoolean();
            else
            	log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
        
        m_PL = new MUNSPackingList(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{		
		StringBuilder result = new StringBuilder();
		
		if (m_Reason == null || Util.isEmpty(m_Reason, true))
			throw new FillMandatoryException("You have to supply some reason to void a Packing List document.");
		
		if (m_PL == null || m_PL.get_ID() == 0)
			throw new AdempiereException("Cannot void a null document.");
		
		MUser usr = MUser.get(getCtx());
		
		StringBuilder desc = new StringBuilder();
		desc.append(" Void From PL(").append(m_PL.getDocumentNo()).
		append(") By ").append(usr.getName()).append(" :: ").append(m_Reason);
		
		m_PL.addDescription(m_Reason);
		
		if(m_CreateReplacement)
		{
			result.append("Replacement :: ").append(createReplacement()).append(". ");
			if(!Util.isEmpty(errMsg.toString(), true))
				throw new AdempiereException(errMsg.toString());
		}
		if (!m_PL.processIt(MUNSPackingList.ACTION_Void))
			throw new AdempiereException(m_PL.getProcessMsg());
		else
		{
			String sql = "UPDATE C_Invoice ci SET Description = COALESCE(ci.Description,'') || ? "
					+ " FROM UNS_PackingList_Order plo"
					+ " WHERE plo.UNS_PackingList_ID = ? AND plo.C_Invoice_ID = ci.C_Invoice_ID";
			int count = DB.executeUpdate(sql, new Object[]{desc.toString(), m_PL.get_ID()}, false, get_TrxName());
			if(count < 0)
				throw new AdempiereException("Failed when update invoices description");
		}
		if(m_CreateReplacement)
		{
			m_PL.setDocumentNo(m_PL.getDocumentNo() + "_VO");
		}
		m_PL.setDescription(desc.toString());
		m_PL.saveEx();
		
		result.append("This document has voided.");
		
		return result.toString();
	}
	
	private String createReplacement() throws Exception
	{
		MUNSPackingList newPL = new MUNSPackingList(getCtx(), 0, get_TrxName());
		
		PO.copyValues(m_PL, newPL);
		newPL.setAD_Org_ID(m_PL.getAD_Org_ID());
		newPL.setDocumentNo(m_PL.getDocumentNo());
		newPL.setReference_ID(m_PL.get_ID());
		if(!newPL.save())
		{
			errMsg.append("Failed when trying create replacement header");
			return errMsg.toString();
		}
		
		MUNSPackingListOrder oldPLO[] = m_PL.getLines(null, null);
		
		for(int i=0; i < oldPLO.length; i++)
		{
			MUNSPackingListOrder newPLO = new MUNSPackingListOrder(getCtx(), 0, get_TrxName());
			PO.copyValues(oldPLO[i], newPLO);
			newPLO.setAD_Org_ID(oldPLO[i].getAD_Org_ID());
			newPLO.setUNS_PackingList_ID(newPL.get_ID());
			newPLO.setReference_ID(oldPLO[i].get_ID());
			if(!newPLO.save())
			{
				errMsg.append("Failed when trying create replacement orders");
				return errMsg.toString();
			}
			
			MUNSPackingListLine oldPLL[] = oldPLO[i].getLines();
			
			for(int j=0; j < oldPLL.length; j++)
			{
				MUNSPackingListLine newPLL = new MUNSPackingListLine(getCtx(), 0, get_TrxName());
				PO.copyValues(oldPLL[j], newPLL);
				newPLL.setAD_Org_ID(oldPLL[j].getAD_Org_ID());
				newPLL.setUNS_PackingList_Order_ID(newPLO.get_ID());
				newPLL.setReference_ID(oldPLL[j].get_ID());
				newPLL.setAutomatic(true);						
				if(!newPLL.save())
				{
					errMsg.append("Failed when trying create replacement lines");
					return errMsg.toString();
				}
			}
		}
		return newPL.getDocumentNo();
	}
}