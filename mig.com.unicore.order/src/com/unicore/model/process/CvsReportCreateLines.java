/**
 * 
 */
package com.unicore.model.process;

import org.compiere.process.SvrProcess;

/**
 * @author root
 *@deprecated
 */
public class CvsReportCreateLines extends SvrProcess {

	/**
	 * 
	 */
	public CvsReportCreateLines() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// No Action

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		return "Proses ini sudah tidak bisa digunakan setelah adanya canvas customer";
	}

}
