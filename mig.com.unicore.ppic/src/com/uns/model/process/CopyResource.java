/**
 * 
 */
package com.uns.model.process;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.base.model.Query;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSResourceInOut;
import com.uns.model.MUNSResourceLocator;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author user
 *
 */
public class CopyResource extends SvrProcess
{

	private int m_copyFromId;
	private MUNSResource resource;

	/**
	 * 
	 */
	public CopyResource()
	{
		// TODO Auto-generated constructor stub
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
			if (para[i].getParameter() == null)
				;
			else if (name.equals("UNS_Resource_ID"))
				m_copyFromId = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
		
		resource = new MUNSResource(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		int count = 0;
		// Get product price from the source product
		if (resource.getOutputLines().length == 0){
			List<MUNSResourceInOut> rios = Query
					.get(getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSResourceInOut.Table_Name, "UNS_Resource_ID=?", get_TrxName())
											.setParameters(new Object[]{m_copyFromId})
											.setOnlyActiveRecords(true)
											.list();

			// Copy Output
			MUNSResourceInOut rioSrc;
			for (Iterator<MUNSResourceInOut> it = rios.iterator(); it.hasNext();) {
				rioSrc = it.next();
				MUNSResourceInOut rioDst = new MUNSResourceInOut(getCtx(), 0, get_TrxName());
				PO.copyValues(rioSrc, rioDst);
				rioDst.setAD_Org_ID(rioSrc.getAD_Org_ID());
				rioDst.setUNS_Resource_ID(getRecord_ID());
				rioDst.setUNS_OutputLink_ID(rioSrc.getUNS_OutputLink_ID());
				rioDst.setIsPrimary(rioSrc.isPrimary());
				rioDst.setOptimumCaps(rioSrc.getOptimumCaps());
				rioDst.setOptimumCapsMT(rioSrc.getOptimumCapsMT());
				rioDst.setMaxCapsMT(rioSrc.getMaxCapsMT());
				rioDst.setMaxCaps(rioSrc.getMaxCaps());
				rioDst.setActualMaxCaps(rioSrc.getMaxCaps());
				rioDst.setActualMaxCapsMT(rioSrc.getMaxCapsMT());rioDst.saveEx(get_TrxName());
				rioDst.save(get_TrxName());
			}
			count = rios.size();
		}
		
		if (resource.getLocatorLines().length == 0){
			List<MUNSResourceLocator> rlocs = Query
					.get(getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSResourceLocator.Table_Name, "UNS_Resource_ID=?", get_TrxName())
											.setParameters(new Object[]{m_copyFromId})
											.setOnlyActiveRecords(true)
											.list();

			// Copy Output
			MUNSResourceLocator rlocSrc;
			for (Iterator<MUNSResourceLocator> it = rlocs.iterator(); it.hasNext();) {
				rlocSrc = it.next();
				MUNSResourceLocator rlocDst = new MUNSResourceLocator(getCtx(), 0, get_TrxName());
				PO.copyValues(rlocSrc, rlocDst);
				rlocDst.setAD_Org_ID(rlocSrc.getAD_Org_ID());
				rlocDst.setUNS_Resource_ID(getRecord_ID());
				rlocDst.save(get_TrxName());
			}
			count = count + rlocs.size();
		}
		
		// TODO Auto-generated method stub
		return "@Copied@="+count;
	}

}
