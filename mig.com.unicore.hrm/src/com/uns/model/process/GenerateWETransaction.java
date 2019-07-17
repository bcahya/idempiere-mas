/**
 * 
 */
package com.uns.model.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;

import com.uns.model.MUNSMessBuildingBlock;
import com.uns.model.MUNSRecidenceGroup;
import com.uns.model.MUNSWEDistribution;

/**
 * @author Admin_UNS
 *
 */
public class GenerateWETransaction extends SvrProcess {

		
	private int m_Record_ID;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		m_Record_ID = getRecord_ID();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		MUNSWEDistribution wed = 
				new MUNSWEDistribution(getCtx(), m_Record_ID, get_TrxName());
		int count = 0;
		
		MUNSMessBuildingBlock[] listMess = 
				((MUNSRecidenceGroup) wed.getUNS_RecidenceGroup()).getLines(false);
		
		if (listMess.length==0 )
			throw new AdempiereUserError("Recidence Group don't have active record.");
		
		for (MUNSMessBuildingBlock housing : listMess){
			if (!wed.createUpdateLines(housing, wed))
				throw new AdempiereException("Error when create ");
			count = count +1;
		}
		
		return "Created "+count+" transaction.";
	}

}
