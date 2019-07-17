/**
 * 
 */
package com.uns.model.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSShipmentSchedule;

/**
 * @author Haryadi
 *
 */
public class CheckStockAvailabilityForShipment extends SvrProcess 
{
	private MUNSShipmentSchedule m_ShipmentArrangement = null;
	
	/**
	 * 
	 */
	public CheckStockAvailabilityForShipment() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		int shipmentArrangementID = getRecord_ID();
		if (shipmentArrangementID == 0)
			throw new AdempiereException("Cannot processing without a record id of shipment arrangement document.");
		
		m_ShipmentArrangement = new MUNSShipmentSchedule(getCtx(), shipmentArrangementID, get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		m_ShipmentArrangement.checkAvailability();
		m_ShipmentArrangement.saveEx();
		return null;
	}

}
