/**
 * 
 */
package com.untacore.process;

import java.util.logging.Level;

import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;


/**
 * @author AzHaidar
 *
 */
public class ValidateInventory extends SvrProcess 
{

	/**
	 * 
	 */
	public ValidateInventory() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] params = getParameter();
		for(ProcessInfoParameter param : params)
		{
			if(param.getParameterName() == null)
				;
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + param.getParameterName());
		}
	} //prepare

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		MInventory inv = new MInventory(getCtx(), getRecord_ID(), get_TrxName());
		
		MInventoryLine[] lines = inv.getLines(true);
		
		StringBuilder victims = new StringBuilder();
		
		for (MInventoryLine line : lines)
		{
			String victimErr = line.validateQtyBook(inv.getMovementDate());
			
			if (victimErr != null)
			{
				if (victims.length() > 0)
					victims.append("; ");
				victims.append(victimErr);
			}
			else 
			{
				line.validateQtyCount();
				
				if (!line.save())
				{
					String errMsg = CLogger.retrieveErrorString("Failed when validating line #" + line.getLine());
					log.severe(errMsg);
					return "Error : " + errMsg;
				}
			}
		}
		
		if (victims.length() > 0)
		{
			return "There are victim error caused by unexpected movement(s) of # " + victims.toString();
		}
		
		return "Physical inventory validated #" + lines.length + " of lines";
	}

}
