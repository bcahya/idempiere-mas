/**
 * 
 */
package com.unicore.model.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Util;

import com.unicore.model.MUNSPLConfirm;

/**
 * @author Burhani Adam
 *
 */
public class ReOpenPackingList extends SvrProcess {

	/**
	 * 
	 */
	public ReOpenPackingList() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{		
		String sql = "SELECT ARRAY_TO_STRING(ARRAY_AGG(UNS_PL_Confirm_ID),';') FROM UNS_PL_Confirm"
				+ " WHERE UNS_PackingList_ID=?";
		String list = DB.getSQLValueString(get_TrxName(), sql, getRecord_ID());
		
		if(!Util.isEmpty(list, true))
		{
			String idString[] = list.split(";");
			for(int i = 0; i < idString.length; i++)
			{
				MUNSPLConfirm plc = new MUNSPLConfirm(getCtx(), new Integer (idString[i]), get_TrxName());
				plc.deleteEx(false, get_TrxName());
			}
		}
		
		sql = "UPDATE UNS_PackingList SET DocStatus = 'DR', DocAction = 'CO', processed = 'N' WHERE UNS_PackingList_ID=?";
		DB.executeUpdate(sql, getRecord_ID(), get_TrxName());
		
		return "ReOpen Succes";
	}
}
