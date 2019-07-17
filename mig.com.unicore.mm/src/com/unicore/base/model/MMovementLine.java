/**
 * 
 */
package com.unicore.base.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MLocator;
import org.compiere.model.MMovement;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;

import com.uns.base.model.Query;

import com.unicore.model.factory.UniCoreMaterialManagementModelFactory;

/**
 * @author menjangan
 *
 */
public class MMovementLine extends org.compiere.model.MMovementLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String INITIAL_INTRANSIT_WAREHOUSE = "-Loc-Intransit";

	/**
	 * @param ctx
	 * @param M_MovementLine_ID
	 * @param trxName
	 */
	public MMovementLine(Properties ctx, int M_MovementLine_ID, String trxName) {
		super(ctx, M_MovementLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MMovementLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parent
	 */
	public MMovementLine(MMovement parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getParent().isInTransit())
		{
			initIntransitLocator();
		}
		if(getM_AttributeSetInstance_ID() > 0 && newRecord)
		{
			MAttributeSetInstance asi = MAttributeSetInstance.createCopyOfASI(
					this, 
					COLUMNNAME_M_AttributeSetInstance_ID, 
					COLUMNNAME_M_Product_ID, 
					COLUMNNAME_M_AttributeSetInstanceTo_ID, 
					get_TrxName());
			
			Timestamp movementDate = getParent().getMovementDate();
			
			if(asi.getCreated() != movementDate)
				asi.setCreated(movementDate);
		}
		return super.beforeSave(newRecord);
	}
	

	/**
	 * Cek locator bisa terbuat atau tidak.
	 */
	public void initIntransitLocator()
	{
		if(null == getIntransitLocator(true))
			throw new AdempiereException("can't initialize intransit locator");
	}
	
	
	/**
	 * Mendapatkan intransit locator. jika null maka akan membuat locator dan warehouse baru.
	 * jika tidak maka akan return locator intransit
	 * locator intransit valuenya slalu "[value_destinationwhs]-intransit-loc-intransit"
	 * @return {@link MLocator}
	 */
	public MLocator getIntransitLocator(boolean createNewIfNotExist)
	{
		MWarehouse destinationWhs = new MWarehouse(
					getCtx(), getParent().getDestinationWarehouse_ID(), get_TrxName());
		
		String whsVal = destinationWhs.getValue();
		
		String whereClause = MLocator.COLUMNNAME_Value + " =?";
		String locIntransitVal = whsVal + INITIAL_INTRANSIT_WAREHOUSE;
		
		MLocator intransitLocator = Query.get(
										getCtx()
										, UniCoreMaterialManagementModelFactory.EXTENSION_ID
										, MLocator.Table_Name
										, whereClause
										, get_TrxName())
										.setParameters(locIntransitVal)
										.firstOnly();

		if(null == intransitLocator && createNewIfNotExist)
			return createIntransitLocator(destinationWhs);
		
		return intransitLocator;
	}

	
	/**
	 * Membuat locator baru
	 * Return locator valuenya slalu "[value_whs]-loc-intransit"
	 * @param whs
	 * @return {@link MLocator}
	 */
	private MLocator createIntransitLocator(MWarehouse whs)
	{
		String locValue = whs.getValue() + INITIAL_INTRANSIT_WAREHOUSE;
		MLocator intransitLoc = new MLocator(whs, locValue);
		intransitLoc.setAD_Org_ID(whs.getAD_Org_ID());
		intransitLoc.setIsActive(true);
		intransitLoc.setM_Warehouse_ID(whs.get_ID());
		intransitLoc.setIsInTransit(true);
		intransitLoc.setXYZ(locValue, "2", "3");
		intransitLoc.save(get_TrxName());
		return intransitLoc;
	}
	
	/**
	 * Mendapatkan intransit warehouse
	 * return warehouse jika menemukan warehouse
	 * return null jika tidak menemukan warehouse dan tidak creatNewIfNotExist
	 * jika warehouse null dan createNewIfNotExist maka akan membuat warehouse baru
	 * @param whs
	 * @param createNewIfNotExist
	 * @return {@link MWarehouse}
	 */
	public MWarehouse getIntransitWarehouse(MWarehouse whs, boolean createNewIfNotExist)
	{
		MWarehouse intransitWhs = Query.get(
									getCtx(), 
									UniCoreMaterialManagementModelFactory.EXTENSION_ID, 
									MWarehouse.Table_Name, MWarehouse.COLUMNNAME_Value + "=?", 
									get_TrxName()).
									setParameters(whs.getValue() + "-Intransit").
									firstOnly();
		if(null == intransitWhs && createNewIfNotExist)
			return createIntransitWhs(whs);
		
		return intransitWhs;
	}
	
	/**
	 * Membuat warehouse baru
	 * Return whs dengan nama "[destination_whs_name]-intransit" dan value [destination_whs_value]-intransit
	 * @param whs
	 * @return {@link MWarehouse}
	 */
	private MWarehouse createIntransitWhs(MWarehouse whs)
	{
		MWarehouse intransitWhs = new MWarehouse(getCtx(), 0, get_TrxName());
		PO.copyValues(whs, intransitWhs);
		intransitWhs.setValue(whs.getValue() + "-Intransit");
		intransitWhs.setName(whs.getName() + "-Intransit");
		intransitWhs.setAD_Org_ID(whs.getAD_Org_ID());
		intransitWhs.setIsInTransit(true);
		intransitWhs.save(get_TrxName());
		return intransitWhs;
	}
}
