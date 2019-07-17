/**
 * 
 */
package com.uns.model.process;

import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.uns.model.MUNSResource;
import com.uns.model.MUNSResourceInOut;

/**
 * @author AzHaidar
 *
 */
public class CopyResourceInOut extends SvrProcess {

	private Properties 	m_Ctx;
	private String 		m_TrxName;
	
	private boolean 				m_isDeleteOld 	= true;
	private MUNSResourceInOut 		m_rioToCopy 	= null;
	private MUNSResource			m_theRsc		= null;
	private MUNSResource			m_rscFrom		= null;

	/**
	 * 
	 */
	public CopyResourceInOut() {
		m_Ctx = getCtx();
		m_TrxName = get_TrxName();
	}

	@Override
	protected void prepare() 
	{	
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if ("DeleteOld".equals(name))
				m_isDeleteOld = para[i].getParameterAsBoolean();
			else if ("UNS_Resource_InOut_ID".equals(name))
			{
				int rioToCopyID = para[i].getParameterAsInt();
				
				if (rioToCopyID > 0)
					m_rioToCopy = new MUNSResourceInOut(getCtx(), rioToCopyID, get_TrxName());
			}
			else if ("UNS_Resource_ID".equals(name)) 
				m_rscFrom = new MUNSResource(getCtx(), para[i].getParameterAsInt(), get_TrxName());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		
		m_Ctx = getCtx();
		m_TrxName = get_TrxName();
		
		m_theRsc = new MUNSResource(m_Ctx, getRecord_ID(), m_TrxName);
	}	//prepare

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		if (m_isDeleteOld)
		{
			if (m_rioToCopy != null)
			{
				String sql = "DELETE FROM UNS_Resource_InOut deleted WHERE deleted.UNS_Resource_ID=? "
						+ "AND deleted.InOutType='O' AND "
						+ " CASE WHEN deleted.OutputType='MOP' "
						+ " THEN deleted.M_Product_ID=? OR deleted.M_Product_ID IN "
						+ "		(SELECT rio.M_Product_ID FROM UNS_Resource_InOut rio "
						+ "		 WHERE rio.UNS_OutputLink_ID=deleted.UNS_Resource_InOut_ID)"
						+ "	WHEN deleted.OutputType='MLT' THEN"
						+ "		1=1 "
						+ " ELSE deleted.M_Product_ID=? END";
				
				DB.executeUpdateEx(sql, 
						new Object[]{m_theRsc.get_ID(), m_rioToCopy.getM_Product_ID(), m_rioToCopy.getM_Product_ID()},  
						m_TrxName);
			}
			else {
				String sql = "DELETE FROM UNS_Resource_InOut deleted WHERE deleted.UNS_Resource_ID=? AND deleted.InOutType='O'";
				
				DB.executeUpdateEx(sql, new Object[]{m_theRsc.get_ID()}, m_TrxName);
			}
		}
		
		MUNSResourceInOut[] rioToCopyList = null;
		
		if (m_rioToCopy != null)
		{
			if (m_rioToCopy.isMultiOptional()) {
				rioToCopyList = m_rscFrom.getOutputLinesForProductOf(m_rioToCopy.getM_Product_ID());
			}
			else if (m_rioToCopy.isMulti()) {
				rioToCopyList = m_rscFrom.getOutputLines();
			}
			else 
				rioToCopyList = new MUNSResourceInOut[]{m_rioToCopy};
		}
		else {
			rioToCopyList = m_rscFrom.getOutputLines();
		}
		
		for (MUNSResourceInOut primaryRIO : rioToCopyList)
		{
			if (!primaryRIO.isPrimary() && !primaryRIO.isOptional() && !primaryRIO.isSingle())
				continue;
			
			MUNSResourceInOut newRio = new MUNSResourceInOut(m_Ctx, 0, m_TrxName);
			
			PO.copyValues(primaryRIO, newRio);
			newRio.setAD_Org_ID(m_theRsc.getAD_Org_ID());
			newRio.setUNS_Resource_ID(m_theRsc.get_ID());
			newRio.saveEx();
			
			for (MUNSResourceInOut nonPrimaryRIO : rioToCopyList)
			{
				if (nonPrimaryRIO.isPrimary() || (nonPrimaryRIO.isOptional() || nonPrimaryRIO.isSingle()))
					continue;
				if (nonPrimaryRIO.isMultiOptional() 
						&& primaryRIO.getUNS_Resource_InOut_ID() != nonPrimaryRIO.getUNS_OutputLink_ID())
					continue;
				MUNSResourceInOut newNonPrimaryRio = new MUNSResourceInOut(m_Ctx, 0, m_TrxName);
				
				PO.copyValues(nonPrimaryRIO, newNonPrimaryRio);
				newNonPrimaryRio.setAD_Org_ID(m_theRsc.getAD_Org_ID());
				newNonPrimaryRio.setUNS_Resource_ID(m_theRsc.get_ID());
				newNonPrimaryRio.setUNS_OutputLink_ID(newRio.get_ID());
				
				newNonPrimaryRio.saveEx();
			}
		}
		
		return "Compeleted. Total resource outputs copied " + rioToCopyList.length;
	}

}
