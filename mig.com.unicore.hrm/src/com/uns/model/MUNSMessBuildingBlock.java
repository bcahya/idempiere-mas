/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author eko
 * @updated Menjangan
 * @Untasoft
 */
public class MUNSMessBuildingBlock extends X_UNS_Mess_BuildingBlock {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSMessPartition[] m_Lines = null;
	/**
	 * @param ctx
	 * @param UNS_Mess_BuildingBlock_ID
	 * @param trxName
	 */
	public MUNSMessBuildingBlock(Properties ctx, int UNS_Mess_BuildingBlock_ID,
			String trxName) {
		super(ctx, UNS_Mess_BuildingBlock_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMessBuildingBlock(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSMessPartition[] getLines(boolean requery)
	{
		if(!requery && null != m_Lines)
		{
			set_TrxName(m_Lines, get_TrxName());
			return m_Lines;
		}
		
		String WhereClause = MUNSMessPartition.COLUMNNAME_UNS_Mess_BuildingBlock_ID + "=? AND "
				+ MUNSMessPartition.COLUMNNAME_IsActive + " ='Y'";
		
		List<MUNSMessPartition>list = Query.get(
				getCtx(), UNSHRMModelFactory.getExtensionID()
				, MUNSMessPartition.Table_Name
				, WhereClause
				, get_TrxName())
				.setParameters(getUNS_Mess_BuildingBlock_ID())
				.list();
		m_Lines = new MUNSMessPartition[list.size()];
		list.toArray(m_Lines);
		return m_Lines;
	}
	
	private boolean updateHeader()
	{
		MUNSRecidenceGroup parent = getParent();
		int maxUntukDihuni = 0;
		int sudahDiHuni = 0;
		int tersediaUntukDihuni = 0;
		for (MUNSMessBuildingBlock mbb : parent.getLines(true))
		{
			maxUntukDihuni += mbb.getMaximumToOccupy();
			sudahDiHuni += mbb.getOccupiedByOccupants();
			tersediaUntukDihuni += mbb.getAvailableToOccupy();
		}
		parent.setMaximumToOccupy(maxUntukDihuni);
		parent.setOccupiedByOccupants(sudahDiHuni);
		parent.setAvailableToOccupy(tersediaUntukDihuni);
		if(!parent.save())
			return false;
		
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if(!updateHeader())
			throw new AdempiereException("Failed to update header");
		return super.afterSave(newRecord, success);
	}

	@Override
	protected boolean beforeDelete() {
		for(MUNSMessPartition mp : getLines(false))
			mp.delete(false);
		
		return super.beforeDelete();
	}

	@Override
	protected boolean afterDelete(boolean success) {
		if(!updateHeader())
			throw new AdempiereException("Failed To Update Header");
		return super.afterDelete(success);
	}

	public MUNSRecidenceGroup getParent()
	{
		return (MUNSRecidenceGroup)getUNS_RecidenceGroup();
	}

}
