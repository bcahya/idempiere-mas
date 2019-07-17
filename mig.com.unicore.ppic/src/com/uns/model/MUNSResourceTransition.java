package com.uns.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

public class MUNSResourceTransition extends X_UNS_Resource_Transition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6666277168128178219L;

	/**
	 * resource inOut For Transition
	 */
	private MUNSResourceInOut[] m_lines = null;
	
	/**
	 * 
	 * @param ctx
	 * @param UNS_Resource_Transition_ID
	 * @param trxName
	 */
	public MUNSResourceTransition(Properties ctx,
			int UNS_Resource_Transition_ID, String trxName) {
		super(ctx, UNS_Resource_Transition_ID, trxName);
	}

	/**
	 * 
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSResourceTransition(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public MUNSResource getResource(int rsc) {
		return new MUNSResource(getCtx(), rsc, get_TrxName());
	}

	public MUNSResource getResource() {
		return (MUNSResource) super.getUNS_Resource();
	}

	protected boolean setParentStartNode(int rsc, boolean startNode) {

		MUNSResource resource = getResource(rsc);
		resource.setIsParentStartNode(startNode);

		if (!resource.save())
			return resource.save();

		return true;
	}

	protected boolean setParentEndNode(int rsc, boolean endNode) {
		MUNSResource resource = getResource(rsc);
		resource.setIsParentEndNode(endNode);

		if (!resource.save())
			return resource.save();

		return true;
	}

	/**
	 * 
	 * @return
	 */
	protected boolean resetNodeStatus() 
	{
		String sql = "SELECT count(*) FROM UNS_Resource_Transition "
				+ "WHERE IsActive='Y' AND UNS_Resource_ID=" + getUNS_Resource_ID();
		int countTransition = DB.getSQLValue(get_TrxName(), sql);

		if (countTransition > 0) {
			sql = "SELECT t.UNS_Resource_ID, t.NextResource_ID FROM UNS_Resource r "
					+ "INNER JOIN UNS_Resource_Transition t ON r.UNS_Resource_id=t.UNS_Resource_ID "
					+ "WHERE r.IsActive='Y' AND t.IsActive='Y' AND r.ResourceParent_ID=" 
					+ getResource().getResourceParent_ID();
			PreparedStatement ps = DB.prepareStatement(sql, get_TrxName());
			try {
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					int r_id = rs.getInt(1);
					int nr_id = rs.getInt(2);
					setParentEndNode(r_id, false);
					setParentStartNode(nr_id, false);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * After save.
	 */
	protected boolean afterSave(boolean newRecord, boolean sucess) {

		return resetNodeStatus();
	}
	
	/**
	 * 	Executed after Delete operation.
	 * 	@param success true if record deleted
	 *	@return true if delete is a success
	 */
	protected boolean afterDelete (boolean success)
	{
		/*
		 * Reset all this resource's parent's child state (start/end node).
		 */
		MUNSResource parentRsc = (MUNSResource) getResource().getResourceParent();
		
		for (MUNSResource child : parentRsc.getLines())
		{
			String sql = "SELECT COUNT(*) FROM UNS_Resource_Transition rt WHERE rt.IsActive='Y' AND rt.NextResource_ID=?";
			int prevCount = DB.getSQLValue(get_TrxName(), sql, child.get_ID());
			
			boolean isParentStartNode = (prevCount <= 0);
			
			child.setIsParentStartNode(isParentStartNode);
			
			sql = "SELECT COUNT(*) FROM UNS_Resource_Transition rt WHERE rt.IsActive='Y' AND rt.UNS_Resource_ID=?";
			int nextCount = DB.getSQLValue(get_TrxName(), sql, child.get_ID());
			
			boolean isParentEndNode = (nextCount <= 0);
			
			child.setIsParentEndNode(isParentEndNode);
			
			child.save();
		}
		return success;
	} 	//	afterDelete
	
	public MUNSResourceInOut[] getLines(boolean requery)
	{
		if(m_lines != null
				&& !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		String whereClauseFinal = "UNS_Resource_Transition_ID=? AND InOutType=?";
		List<X_UNS_Resource_InOut> list = Query
				.get(getCtx(), UNSPPICModelFactory.getExtensionID(),
						X_UNS_Resource_InOut.Table_Name, whereClauseFinal,
						get_TrxName())
				.setParameters(new Object[] { getUNS_Resource_Transition_ID(), MUNSResourceInOut.INOUTTYPE_Input })
				.setOrderBy(X_UNS_Resource_InOut.COLUMNNAME_UNS_Resource_InOut_ID)
				.setOnlyActiveRecords(true)
				.list();

		m_lines = new MUNSResourceInOut[list.size()];
		list.toArray(m_lines);

		return m_lines;
	}

	/**
	 * 
	 * @return
	 */
	public MUNSResourceInOut[]  getLines()
	{
		return getLines(false);
	}
	
	public String toString()
	{
		return super.toString() + "[" + getName() + "]";
	}
}
