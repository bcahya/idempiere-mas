/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author menjangan
 *
 */
public class MUNSContainerArrivalLine extends X_UNS_ContainerArrivalLine {
	
	private MUNSContainerArrival m_parent = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_ContainerArrivalLine_ID
	 * @param trxName
	 */
	public MUNSContainerArrivalLine(Properties ctx,
			int UNS_ContainerArrivalLine_ID, String trxName) {
		super(ctx, UNS_ContainerArrivalLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSContainerArrivalLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSContainerArrival getParent()
	{
		if (null != m_parent)
		{
			m_parent = 
					new MUNSContainerArrival(getCtx(), getUNS_ContainerArrival_ID(), get_TrxName());
		}
		
		return m_parent;
	}
	
	/**
	 * 
	 * @param description
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}

}
