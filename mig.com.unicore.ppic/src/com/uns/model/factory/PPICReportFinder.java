/**
 * 
 */
package com.uns.model.factory;

import java.io.InputStream;

import org.adempiere.base.IReportFinder;

/**
 * @author Ahong
 *
 */
public class PPICReportFinder implements IReportFinder {

	/**
	 * 
	 */
	public PPICReportFinder() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IReportFinder#getReportAsStream(java.lang.String)
	 */
	@Override
	public InputStream getReportAsStream(String name) {
		// TODO Auto-generated method stub
		return getClass().getClassLoader().getResourceAsStream(name);
	}

}