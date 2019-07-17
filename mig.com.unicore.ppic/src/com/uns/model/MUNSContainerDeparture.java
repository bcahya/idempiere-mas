/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;


/**
 * @author YAKA
 *
 */
public class MUNSContainerDeparture extends X_UNS_ContainerDeparture {

	/**
	 * 
	 */
	private static final long serialVersionUID = 486995638721836379L;
	
	public static final String EXTENSION_ID = "com.uns.model.factory.UNSPPICModelFactory";

	/**
	 * @param ctx
	 * @param UNS_ContainerDeparture_ID
	 * @param trxName
	 */
	public MUNSContainerDeparture(Properties ctx,
			int UNS_ContainerDeparture_ID, String trxName) {
		super(ctx, UNS_ContainerDeparture_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSContainerDeparture(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSSOShipment[] getSOShipments(){
		
		String wClause = COLUMNNAME_UNS_ContainerDeparture_ID + "=?";
		List<MUNSSOShipment> list = new Query(getCtx(), MUNSSOShipment.Table_Name,
				wClause, get_TrxName()).setParameters(getUNS_ContainerDeparture_ID())
				.setOrderBy(MUNSSOShipment.COLUMNNAME_UNS_SOShipment_ID).list();

		return list.toArray(new MUNSSOShipment[list.size()]);
	}
	
	/**
	 * Update container management status based on this container departure status.
	 * @return
	 */
	public boolean updateContainerManagementStatus()
	{
		MUNSContainerManagement cm = (MUNSContainerManagement)getUNS_ContainerManagement();
		cm.setCurrentStatus(MUNSContainerManagement.CURRENTSTATUS_ShippedToCustomer);
		cm.setDepartedBy_ID(getUNS_ShipmentSchedule().getDropShip_BPartner_ID());
		cm.setLastDepartureDate(getUNS_ShipmentSchedule().getETD());
		
		return cm.save();
	}
	/**
	public MUNSContainerManagement getUNS_ContainerManagement()
	{
		return (MUNSContainerManagement) MTable.get(getCtx(), Table_Name)
			.getPO(getUNS_ContainerManagement_ID(), get_TrxName());
	}
	*/

	
	public MUNSShipmentSchedule getHeader(){
		return new MUNSShipmentSchedule(getCtx(), getUNS_ShipmentSchedule_ID(), get_TrxName());
	}
}
