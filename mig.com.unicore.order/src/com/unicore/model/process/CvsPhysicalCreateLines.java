/**
 * 
 */
package com.unicore.model.process;

import org.compiere.process.SvrProcess;

import com.unicore.model.MUNSCvsPhysical;

/**
 * @author root
 *
 */
public class CvsPhysicalCreateLines extends SvrProcess {
	
	private MUNSCvsPhysical m_model = null;

	/**
	 * 
	 */
	public CvsPhysicalCreateLines() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		m_model = new MUNSCvsPhysical(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		return m_model.generateListProduct();
	}

}
