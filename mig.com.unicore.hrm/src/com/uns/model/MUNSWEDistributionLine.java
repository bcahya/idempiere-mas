/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

/**
 * @author Admin_UNS
 *
 */
public class MUNSWEDistributionLine extends X_UNS_WEDistributionLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3486943190255639355L;
	private MUNSWEDistribution m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_MedicalRecord_ID
	 * @param trxName
	 */
	public MUNSWEDistributionLine(Properties ctx, int UNS_WEDistributionLine_ID,
			String trxName) {
		super(ctx, UNS_WEDistributionLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSWEDistributionLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param header
	 */
	public MUNSWEDistributionLine(MUNSWEDistribution parent, MUNSMessPartition room) {
		this(parent.getCtx(), 0, parent.get_TrxName());
		if (parent.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");

		m_parent = parent;
		setClientOrg(parent);
		setUNS_WEDistribution_ID(parent.get_ID());
		setDescription(room.getDescription());
		setM_LocatorTo_ID(room.getM_Locator_ID());
		setUNS_Mess_BuildingBlock_ID(room.getUNS_Mess_BuildingBlock_ID());
		setUNS_Mess_Partition_ID(room.get_ID());
	}
	
	@Override
	public MUNSWEDistribution getParent() {
		if(m_parent==null)
			m_parent = (MUNSWEDistribution) getUNS_WEDistribution();

		return m_parent;
	}
	
	public boolean beforeSave(boolean newRecord){
		//	Set Line No
		if (getLine() == 0){
			String sql = "SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM " +
					"UNS_WEDistributionLine WHERE UNS_WEDistribution_ID=? AND UNS_Mess_BuildingBlock_ID=?";
			int ii = DB.getSQLValue (get_TrxName(), sql, getUNS_WEDistribution_ID(), getUNS_Mess_BuildingBlock_ID());
			setLine (ii);
		}
		return super.beforeSave(newRecord);
	}
}
