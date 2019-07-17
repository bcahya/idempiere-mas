package com.uns.util;

import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

/** Not used	*/
public class LoadUndefinedContext extends SvrProcess {

	int p_AppsContextID= 0;
	
	public LoadUndefinedContext() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		ProcessInfoParameter[] param = getParameter();
		for (int i=0; i< param.length; i++)
		{
			String paramName = param[i].getParameterName();
			if (param[i].getParameterName() == null)
				;
			else
			{
				log.log(Level.SEVERE, "Unknown Parameter: " + paramName);
			}
		}
		// TODO Auto-generated method stub

		p_AppsContextID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		String msg = null;
		//MUNSAppsContext AppsContext = new MUNSAppsContext(getCtx(),p_AppsContextID,get_TrxName());
		
		return msg;
	}

}
