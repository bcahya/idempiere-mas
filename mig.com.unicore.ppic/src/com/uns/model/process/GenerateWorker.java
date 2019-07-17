/**
 * 
 */
package com.uns.model.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSProduction;
import com.uns.model.MUNSProductionWorker;

/**
 * @author YAKA
 *
 */
public class GenerateWorker extends SvrProcess {

	/** The Record */
	private int m_Record_ID = 0;
	
	/** Record MUNSResource */
	private MUNSProduction m_production = null;

	/**
	 * Get Record
	 * 
	 * @return Record
	 */
	protected MUNSProduction getRecord() {
		return new MUNSProduction(getCtx(), m_Record_ID, get_TrxName());
	} // getResourceRecord

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_Record_ID = getRecord_ID();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		m_production = getRecord();
		
		String msg = null;
		
		try {
			MUNSProductionWorker.generateWorker(getCtx(), get_TrxName(), m_production);
		}
		catch (AdempiereException ex) {
			msg = ex.getMessage();
		}
		
		return msg;
	}

}
