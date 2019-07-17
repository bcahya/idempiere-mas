/**
 * 
 */
package com.uns.model.process;

import java.util.List;
import java.util.logging.Level;
import org.adempiere.util.IProcessUI;
import org.compiere.model.I_PA_Goal;
import org.compiere.model.MGoal;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

/**
 * @author Burhani Adam
 *
 */
public class UpdateGoals extends SvrProcess {

	/**
	 * 
	 */
	public UpdateGoals() {
		// TODO Auto-generated constructor stub
	}
	
	private boolean isManual = true;
	private IProcessUI m_ui = null;

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
			if (name.equals("isManual"))
				isManual = para[i].getParameterAsBoolean();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		List<MGoal> list = new Query(getCtx(),I_PA_Goal.Table_Name,null,null)
		.setOrderBy("SeqNo")
		.setOnlyActiveRecords(true)
		.list();
		if(isManual)
			m_ui = Env.getProcessUI(getCtx());
		
		int count = 1;
		int length = list.size();
		for(MGoal goal : list)
		{
			String msg = "Update goal " + goal.getName() + ". Goal ke " + count + " dari " + length + " goal.";
			if(!isManual)
				log.log(Level.INFO, msg);
			else
				m_ui.statusUpdate(msg);
			goal.updateGoal(true);
			goal.saveEx();
			count++;
		}
		return null;
	}

}
