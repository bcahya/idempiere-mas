/**
 * 
 */
package com.uns.model.process;

import org.compiere.process.SvrProcess;

import com.uns.model.MUNSBongkarMuat;

/**
 * @author menjangan
 *
 */
public class BongkarMuatCreateLines extends SvrProcess {
	
	private int m_RecordID = 0;

	/**
	 * 
	 */
	public BongkarMuatCreateLines() {
		
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {

		m_RecordID =getRecord_ID();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		String msg = null;
		MUNSBongkarMuat bongkarMuat = 
				new MUNSBongkarMuat(getCtx(), m_RecordID, get_TrxName());
		msg = bongkarMuat.createLines();
		if (msg.equals(""))
		{
			bongkarMuat.setCreateFrom("Y");
			bongkarMuat.save();
		}
		else {
			throw new IllegalArgumentException("Cant Create Lines");
		}
		return msg;
	}

}
