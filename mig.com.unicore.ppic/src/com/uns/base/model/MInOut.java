/**
 * 
 */
package com.uns.base.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.process.DocAction;

/**
 * @author menjangan
 *
 */
public class MInOut extends org.compiere.model.MInOut {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4031513400017665301L;
	
	/**
	 * 
	 * @param ctx
	 * @param M_InOut_ID
	 * @param trxName
	 */
	public MInOut(Properties ctx, int M_InOut_ID, String trxName)
	{
		super(ctx, M_InOut_ID, trxName);
	}
	
	public MInOut(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
	
	@Override
	public String completeIt()
	{
		super.completeIt();
		
		//FIXME add Column LC_Ballance_ID
//		if(getUNS_LC_Balance_ID() <= 0)
//			return DocAction.STATUS_Completed;
//		
//		MUNSLCBalance lcBalance = new MUNSLCBalance(getCtx(), getUNS_LC_Balance_ID(), get_TrxName());
//		Hashtable<String, MUNSLCLines> mapOfLCLines = lcBalance.getMapOfLCLines();
//		if(!lcBalance.isAllowedShipment(getMovementDate()))
//			throw new AdempiereUserError(
//					"Shipment date has exceeds the LC Balance latest shipment date" );
//		
//		for (MInOutLine inoutLine : getLines())
//		{
//			MUNSLCLines lcLine = mapOfLCLines.get(MUNSLCLines.getUniqueLine(inoutLine.getM_Product()));
//			if(null == lcLine)
//				continue;
//			
//			if(inoutLine.getC_OrderLine_ID() <= 0)
//				continue;
//			
//			I_C_OrderLine orderLine = inoutLine.getC_OrderLine();
//			BigDecimal price = orderLine.getPriceActual();
//			BigDecimal lineNetAmt = inoutLine.getConfirmedQty().multiply(price);
//			
//			if(!lcBalance.isAllowedAmtShipment(lineNetAmt))
//			{
//				throw new AdempiereUserError("Amount has exceeds the LC Balance");
//			}
//			
//			if (!lcLine.isAllowedQtyShipment(inoutLine.getConfirmedQty()))
//			{
//				throw new AdempiereUserError("Quantity has exceeds the LC Balance");
//			}
//			lcLine.setShipmentAmount(lcLine.getShipmentAmount().add(lineNetAmt));
//			lcLine.setShipmentQuantity(lcLine.getShipmentQuantity().add(inoutLine.getConfirmedQty()));
//			lcLine.save();
//			X_UNS_LC_Shipment lcShipment = new X_UNS_LC_Shipment(getCtx(), 0, get_TrxName());
//			lcShipment.setAD_Org_ID(getAD_Org_ID());
//			lcShipment.setC_UOM_ID(inoutLine.getC_UOM_ID());
//			lcShipment.setDescription(inoutLine.getDescription());
//			lcShipment.setM_AttributeSetInstance_ID(inoutLine.getM_AttributeSetInstance_ID());
//			lcShipment.setM_InOut_ID(getM_InOut_ID());
//			lcShipment.setUNS_LC_Lines_ID(lcLine.get_ID());
//			if (!lcShipment.save())
//				throw new AdempiereUserError("Can't create LC SO");
//		}
		return DocAction.STATUS_Completed;
	}
	
}
