/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author eko
 *
 */
public class MUNSMessPartition extends X_UNS_Mess_Partition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSMessOccupants[] m_Lines = null;
	/**
	 * @param ctx
	 * @param UNS_Mess_Partition_ID
	 * @param trxName
	 */
	public MUNSMessPartition(Properties ctx, int UNS_Mess_Partition_ID,
			String trxName) {
		super(ctx, UNS_Mess_Partition_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMessPartition(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MUNSMessPartition get(Properties ctx, int M_Locator_ID, String trxName)
	{
		return Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, COLUMNNAME_M_Locator_ID + " = " 
						+ M_Locator_ID, trxName).firstOnly();
	}
	
	public MUNSMessOccupants getOccupants()
	{
		return Query.get(
				getCtx(), UNSHRMModelFactory.EXTENSION_ID, MUNSMessOccupants.Table_Name
				, MUNSMessOccupants.COLUMNNAME_UNS_Mess_Partition_ID + " = " + getUNS_Mess_Partition_ID()
				, get_TrxName()).firstOnly();
	}
	
	public List<MUNSMessOccupants> getOccupantsToChargeElectricityAndWaterUses()
	{
		return Query.get(
				getCtx(), UNSHRMModelFactory.getExtensionID()
				, MUNSMessOccupants.Table_Name
				, MUNSMessOccupants.COLUMNNAME_UNS_Mess_Partition_ID 
				+ " = " + getUNS_Mess_Partition_ID() + " AND " 
						+ MUNSMessOccupants.COLUMNNAME_IsChargeToUtilitiesUses + " = 'Y' AND "
						+ COLUMNNAME_IsActive + " = 'Y'"
						, get_TrxName())
						.list();
	}
	
	public MUNSMessOccupants[] getLines(boolean requery)
	{
		if(null != m_Lines && !requery)
		{
			set_TrxName(m_Lines, get_TrxName());
			return m_Lines;
		}
		
		String WhereClause = MUNSMessOccupants.COLUMNNAME_UNS_Mess_Partition_ID + "=? AND "
				+ MUNSMessOccupants.COLUMNNAME_IsActive + " ='Y'";
		
		List<MUNSMessOccupants>list = Query.get(
				getCtx(), UNSHRMModelFactory.getExtensionID()
				, MUNSMessOccupants.Table_Name
				, WhereClause
				, get_TrxName())
				.setParameters(getUNS_Mess_Partition_ID())
				.list();
		m_Lines = new MUNSMessOccupants[list.size()];
		list.toArray(m_Lines);
		return m_Lines;
	}
	
	private boolean updateHeader()
	{
		int maxUntukDihuni = 0;
		int sudahDiHuni = 0;
		int tersediaUntukDihuni = 0;
		MUNSMessBuildingBlock parent = getParent();
		for(MUNSMessPartition mp : parent.getLines(false))
		{
			maxUntukDihuni += mp.getMaximumToOccupy();
			sudahDiHuni += mp.getOccupiedByOccupants();
			tersediaUntukDihuni += mp.getAvailableToOccupy();
		}
		parent.setMaximumToOccupy(maxUntukDihuni);
		parent.setAvailableToOccupy(tersediaUntukDihuni);
		parent.setOccupiedByOccupants(sudahDiHuni);
		if(!parent.save())
			return false;
		
		return true;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		int maxUntukDihuni = getMaximumToOccupy();
		int sudahDiHuni = getOccupiedByOccupants();
		int tersediaUntukDihuni = maxUntukDihuni - sudahDiHuni;
		setAvailableToOccupy(tersediaUntukDihuni);
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if(!updateHeader())
			throw new AdempiereException("Failed To Update Header");
		return super.afterSave(newRecord, success);
	}

	@Override
	protected boolean afterDelete(boolean success) {
		if(!updateHeader())
			throw new AdempiereException("Failed To Update Header");
		return super.afterDelete(success);
	}

	@Override
	protected boolean beforeDelete() {
		for(MUNSMessOccupants occ : getLines(false))
			occ.delete(false);
		
		return super.beforeDelete();
	}

	public MUNSMessBuildingBlock getParent()
	{
		return (MUNSMessBuildingBlock)getUNS_Mess_BuildingBlock();
	}
	
	public BigDecimal getLastUsed(int M_Product_ID) {
		if (MUNSWEDistribution.IsWater(getCtx(), M_Product_ID, get_TrxName()))
			return getLastElectricityUsed();
		else 
			return getLastWaterUsed();
		
	}
	
	public void setLastUsed(int M_Product_ID, BigDecimal value) {
		if (MUNSWEDistribution.IsWater(getCtx(), M_Product_ID, get_TrxName()))
			setLastWaterUsed(value);
		else 
			setLastElectricityUsed(value);
		
	}
}
