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
public class OrderReportFinder implements IReportFinder {

	/**
	 * 
	 */
	public OrderReportFinder() {
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IReportFinder#getReportAsStream(java.lang.String)
	 */
	@Override
	public InputStream getReportAsStream(String name) {
		return getClass().getClassLoader().getResourceAsStream(name);
	}

}
