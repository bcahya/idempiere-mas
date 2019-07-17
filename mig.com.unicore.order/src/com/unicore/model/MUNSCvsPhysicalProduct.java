/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author root
 *
 */
public class MUNSCvsPhysicalProduct extends X_UNS_Cvs_PhysicalProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID		= 5818423815407030353L;
	private MUNSCvsPhysical m_parent				= null;
	private MUNSCvsPhysicalProductMA[] m_lines		= null;

	/**
	 * @param ctx
	 * @param UNS_Cvs_PhysicalProduct_ID
	 * @param trxName
	 */
	public MUNSCvsPhysicalProduct(Properties ctx,
			int UNS_Cvs_PhysicalProduct_ID, String trxName) {
		super(ctx, UNS_Cvs_PhysicalProduct_ID, trxName);
	}

	/**
	 * 
	 * getParent
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCvsPhysicalProduct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSCvsPhysicalProduct(MUNSCvsPhysical parent)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setUNS_Cvs_Physical_ID(parent.get_ID());
	}
	
	public MUNSCvsPhysical getParent(boolean requery)
	{
		if(null != m_parent && !requery)
		{
			return m_parent;
		}
		
		m_parent = (MUNSCvsPhysical) getUNS_Cvs_Physical();
		return m_parent;
	}
	
	/**
	 * getParent
	 */
	public MUNSCvsPhysical getParent()
	{
		return getParent(false);
	}
	
	/**
	 * Get Lines
	 * @return
	 */
	public MUNSCvsPhysicalProductMA[] getLines()
	{
		return getLines(false);
	}
	
	/**
	 * Get Lines
	 * @param requery
	 * @return
	 */
	public MUNSCvsPhysicalProductMA[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSCvsPhysicalProductMA> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID
				, MUNSCvsPhysicalProductMA.Table_Name, Table_Name + "_ID = ?"
				, get_TrxName())
				.setParameters(get_ID()).list();
		
		m_lines = new MUNSCvsPhysicalProductMA[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
}
