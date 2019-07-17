/**
 * 
 */
package com.uns.model.process;

import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.qad.model.MUNSQAStatusNC;

/**
 * @author YAKA
 *
 */
public class ReleaseCI extends SvrProcess {


	private int status_nc;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++){
			String name = para[i].getParameterName();
			log.fine("prepare - " + para[i]);
			if (para[i].getParameter() == null)
				;
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		
		status_nc = getRecord_ID();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		MUNSQAStatusNC snc = new MUNSQAStatusNC(getCtx(), status_nc, get_TrxName());
		return snc.completeIt();
	}

}
