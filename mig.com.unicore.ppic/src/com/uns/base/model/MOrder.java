/**
 * 
 */
package com.uns.base.model;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Util;

import com.uns.util.ErrorMsg;

import com.uns.base.model.Query;
import com.uns.model.MUNSOrderShipment;
import com.uns.model.MUNSSER;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MOrder extends org.compiere.model.MOrder {
	
	private MOrderLine[] m_lines = null;
	private String sMsg = null;

	/**
	 * 
	 * @param ctx
	 * @param C_Order_ID
	 * @param trxName
	 */
	public MOrder(Properties ctx, int C_Order_ID, String trxName) {
		super(ctx, C_Order_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MOrder(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		String Msg = null;
		Msg = MUNSSER.cekBuyer(this);
		if (Msg != null)
		{
			ErrorMsg.setErrorMsg(getCtx(), "SaveError", Msg);
			return false;
		}
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean sucess)
	{
		if (!sucess)
			return false;
		if (newRecord)
		{
			MUNSOrderShipment OrderShipment = new MUNSOrderShipment(getCtx(), 0, get_TrxName());
			OrderShipment.setC_Order_ID(getC_Order_ID());
			OrderShipment.setAD_Org_ID(getAD_Org_ID());
			
			if (!OrderShipment.save())
				return false;
			
		}
		
		return super.afterSave(newRecord, sucess);
	}
	
	@Override
	public MOrderLine[] getLines (String whereClause, String orderClause)
	{
		//red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		StringBuilder whereClauseFinal = new StringBuilder(MOrderLine.COLUMNNAME_C_Order_ID+"=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MOrderLine.COLUMNNAME_Line;
		//
		List<MOrderLine> list = 
				Query.get(getCtx(), UNSPPICModelFactory.getExtensionID(), MOrderLine.Table_Name, whereClauseFinal.toString(), get_TrxName())
										.setParameters(getC_Order_ID())
										.setOrderBy(orderClause)
										.list();
		for (MOrderLine ol : list) {
			ol.setHeaderInfo(this);
		}
		//
		return list.toArray(new MOrderLine[list.size()]);		
	}	//	getLines
	
	@Override
	public MOrderLine[] getLines (boolean requery, String orderBy)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;
		else
			orderClause += "Line";
		m_lines = getLines(null, orderClause);
		return m_lines;
	}	//	getLines
	
	@Override
	public MOrderLine[] getLines()
	{
		return getLines(false, null);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String completeIt()
	{
		sMsg = super.completeIt();
		
		//FIXME SER for SALES MARKETING		
//		MOrderLine[] mLines = getLines();
//		MUNSSER SER = new MUNSSER(getCtx(), getUNS_SER_ID(), get_TrxName());
//		for (int i=0; i<mLines.length; i++)
//		{
//			MOrderLine mLine = mLines[i];
//			MUNSSERLineBuyer[] linesBuyer = SER.getLines();
//			
//			for (MUNSSERLineBuyer lineBuyer : linesBuyer)
//			{
//				MUNSSERLineProduct[] linesProd = lineBuyer.getLines();
//				for (int j=0; j<linesProd.length; j++)
//				{
//					MUNSSERLineProduct lineProd = linesProd[j];
//					if (lineProd.getM_Product_ID() == mLine.getM_Product_ID())
//					{
//						BigDecimal qty = BigDecimal.ZERO;
//						if (lineProd.getC_UOM_ID() != mLine.getC_UOM_ID())
//						{
//							BigDecimal conv = MUOMConversion.convertProductFrom(
//									getCtx(), mLine.getM_Product_ID(), lineProd.getC_UOM_ID()
//									, mLine.getQtyEntered());
//							if (null != conv)
//							{
//								qty = conv;
//							}
//							else
//							{
//								sMsg = "Can't convert From " + mLine.getC_UOM().getName()
//										+ " to " + lineProd.getC_UOM().getName()
//										+ " || in Line " + mLine.getLine() + "_"
//										+ mLine.getM_Product_ID();
//								super.setDocStatus(DOCSTATUS_Invalid);
//								
//								break;
//							}
//						}
//						lineProd.setQtyOrdered(qty);
//						lineProd.save();
//					}
//				}
//			}
//		}
//		
//		if(!isSOTrx())
//			return DocAction.STATUS_Completed;
//		
//		if (isSOTrx() && getUNS_LC_Balance_ID() > 0)
//		{
//			MUNSLCBalance lcBalance = new MUNSLCBalance(getCtx(), getUNS_LC_Balance_ID(), get_TrxName());
//			Hashtable<String, MUNSLCLines> mapOFLCLines = lcBalance.getMapOfLCLines();
//			//int Currency_ID = lcBalance.getC_Currency_ID();
//			int Currency_ID = lcBalance.get_ValueAsInt(MUNSLCBalance.COLUMNNAME_C_Currency_ID);
//			if(Currency_ID <= 0)
//				throw new AdempiereUserError("Currency in LC Balance has not set");
//			if(getC_Currency_ID() != Currency_ID)
//				throw new AdempiereUserError("currency must be the same as the currency in the LC Balance");
//			if(!lcBalance.isAllowedPartialShipment())
//				if(isPartialShipment())
//					throw new AdempiereUserError("LC Balance is not allowed partial shipment");
//			
//			for(MOrderLine line : getLines())
//			{
//				MUNSLCLines lcLine = mapOFLCLines.get(MUNSLCLines.getUniqueLine(line.getM_Product()));
//				if (null == lcLine)
//					continue;
//				if (!lcLine.isAllowedQtySO(line.getQtyOrdered()))
//					throw new AdempiereUserError("Quantity exceeds the LC Balance");
//				if(!lcBalance.isAllowedAmtSO(line.getLineNetAmt()))
//					throw new AdempiereUserError("amount exceeds the LC Balance");
//				
//				double oldLCAmtUsed = lcBalance.getTotalAmtUsed().doubleValue();
//				double newLCAmtUsed = oldLCAmtUsed + line.getLineNetAmt().doubleValue();
//				lcBalance.setTotalAmtUsed(new BigDecimal(newLCAmtUsed));
//				
//				lcLine.setSOQuantity(lcLine.getSOQuantity().add(line.getQtyOrdered()));
//				lcLine.setSOAmount(lcLine.getSOAmount().add(line.getLineNetAmt()));
//				lcLine.save();
//				X_UNS_LC_SO lcSO = new X_UNS_LC_SO(getCtx(), 0, get_TrxName());
//				lcSO.setAD_Org_ID(getAD_Org_ID());
//				lcSO.setC_Order_ID(getC_Order_ID());
//				lcSO.setC_UOM_ID(line.getC_UOM_ID());
//				lcSO.setDescription(line.getDescription());
//				lcSO.setM_AttributeSetInstance_ID(line.getM_AttributeSetInstance_ID());
//				lcSO.setM_Product_ID(line.getM_Product_ID());
//				lcSO.setTotalAmt(line.getLineNetAmt());
//				lcSO.setUNS_LC_Lines_ID(lcLine.get_ID());
//				if (!lcSO.save())
//					throw new AdempiereUserError("Can't create LC SO");
//			}
//			lcBalance.save();
//		}

		if (sMsg != null)
			return DocAction.STATUS_Invalid;
		
		return DocAction.STATUS_Completed;
	}
	
	public boolean HasShipped()
	{
		return DB.getSQLValue(
				get_TrxName()
				, "SELECT COUNT(" + MInOut.COLUMNNAME_C_Order_ID + ") FROM " 
				+ MInOut.Table_Name + " WHERE " + MInOut.COLUMNNAME_C_Order_ID + " = " 
						+ getC_Order_ID()) > 0;
	}
	
	public MOrderLine getOrderLineOf(int M_product_ID)
	{
		return Query.get(
				getCtx()
				, UNSPPICModelFactory.getExtensionID()
				, MOrderLine.Table_Name
				, MOrderLine.COLUMNNAME_C_Order_ID + " = " + getC_Order_ID() 
				+ " AND " + MOrderLine.COLUMNNAME_M_Product_ID + " = " + M_product_ID
				, get_TrxName())
				.firstOnly();
	}
	
	public Hashtable<Integer, MOrderLine> getMapOfOrderLine()
	{
		Hashtable<Integer, MOrderLine> mapOfOrderLine = new Hashtable<Integer, MOrderLine>();
		for(MOrderLine ol : getLines())
		{
			mapOfOrderLine.put(ol.getM_Product_ID(), ol);
		}
		return mapOfOrderLine;
	}
	
		//FIXME isPartialShipment for SALES MARKETING
//	/**
//	 * 
//	 * @return true if this order has partial shipment
//	 */
//	public boolean isPartialShipment()
//	{
//		String sql = "SELECT COUNT(C_Order_ID) FROM C_Order WHERE " 
//					+ MUNSLCBalance.COLUMNNAME_UNS_LC_Balance_ID + " = " 
//							+ getUNS_LC_Balance_ID();
//		return DB.getSQLValue(get_TrxName(), sql) > 1;
//	}

}
