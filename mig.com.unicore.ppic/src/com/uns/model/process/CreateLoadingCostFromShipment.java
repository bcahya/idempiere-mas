/**
 * 
 */
package com.uns.model.process;

import org.compiere.process.SvrProcess;

import com.uns.model.MUNSLoadingCost;

/**
 * @author menjangan
 *
 */
public class CreateLoadingCostFromShipment extends SvrProcess {

	/**
	 * 
	 */
	public CreateLoadingCostFromShipment() {
	}
	
	private int m_Record_ID;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		m_Record_ID =getRecord_ID();

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		MUNSLoadingCost lc = new MUNSLoadingCost(getCtx(), m_Record_ID, get_TrxName());
		String msg = lc.createLinesFromShipment();
		if (null != msg) {
			throw new IllegalArgumentException(msg);
		}
		return null;
	}

}
