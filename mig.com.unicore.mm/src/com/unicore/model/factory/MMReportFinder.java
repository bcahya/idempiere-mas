/**
 * 
 */
package com.unicore.model.factory;

import java.io.InputStream;

import org.adempiere.base.IReportFinder;

/**
 * @author Ahong
 *
 */
public class MMReportFinder implements IReportFinder {

	/**
	 * 
	 */
	public MMReportFinder() {
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IReportFinder#getReportAsStream(java.lang.String)
	 */
	public InputStream getReportAsStream(String name) {
		return getClass().getClassLoader().getResourceAsStream(name);
	}

}
