/**
 * 
 */
package com.unicore.model.validator;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;
import com.unicore.model.MUNSOrderQueue;
import com.unicore.model.MUNSOrderQueueLine;
import com.unicore.model.MUNSOrgWarehouse;
import com.unicore.model.MUNSOrgWarehouseLine;
import com.unicore.model.MUNSPLReturnOrder;
import com.unicore.model.MUNSPackingList;
import com.unicore.model.MUNSPackingListLine;
import com.unicore.model.MUNSPackingListOrder;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author ALBURHANY
 *
 */
public class UNSOrderQueueValidator implements ModelValidator {
	
	private MUNSPLReturnOrder[] m_PLReturn = null;
	private MUNSPackingListLine[] m_PackingListLine = null;
	private int OrderQueue = 0;

	/**
	 * 
	 */
	public UNSOrderQueueValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
	
		engine.addDocValidate(MUNSPackingList.Table_Name, this);
		engine.addDocValidate(MUNSPLReturnOrder.Table_Name, this);
		engine.addDocValidate(MOrder.Table_Name, this);
		engine.addDocValidate(MPayment.Table_Name, this);
	}

	@Override
	public int getAD_Client_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public MUNSPLReturnOrder[] getOrders(PO po) 
	{
		if (m_PLReturn != null)
			return m_PLReturn;
		
		List<MUNSPLReturnOrder> list = null;
		list = Query.get(po.getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPLReturnOrder.Table_Name, 
						 "UNS_PL_Return_ID=" + po.get_ID(), po.get_TrxName())
					.list();
		MUNSPLReturnOrder[] retValue = new MUNSPLReturnOrder[list.size()];
		list.toArray(retValue);
		m_PLReturn = retValue;
		
		return retValue;
	}
	
	public MUNSPackingListLine[] getLines(PO po, MUNSPackingListOrder orders)
	{
		if (m_PackingListLine != null)
			return m_PackingListLine;
		
		List<MUNSPackingListLine> list = null;
		list = Query.get(po.getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPackingListLine.Table_Name, 
						 "UNS_PackingList_Order_ID=" + orders.get_ID(), po.get_TrxName())
					.list();
		MUNSPackingListLine[] retValue = new MUNSPackingListLine[list.size()];
		list.toArray(retValue);
		m_PackingListLine = retValue;
		
		return retValue;
	}

	@Override
	public String docValidate(PO po, int timing) {
		
		if (po.get_TableName().equals(MUNSPackingList.Table_Name) && timing == TIMING_AFTER_PREPARE)
		{			
			String whereClause = "UNS_PackingList_ID = ?";
			
			List<MUNSPackingListOrder> listOrder = new Query(po.getCtx(), MUNSPackingListOrder.Table_Name,
					whereClause, po.get_TrxName()).setParameters(po.get_ID()).list();
			
			for (MUNSPackingListOrder pOrder : listOrder)
			{								
				String whereClausee = "UNS_PackingList_Order_ID = ?";
				
				List<MUNSPackingListLine> listLine = new Query(po.getCtx(), MUNSPackingListLine.Table_Name,
						whereClausee, po.get_TrxName()).setParameters(pOrder.get_ID()).list();
				
				for (MUNSPackingListLine pLine : listLine)
				{
					BigDecimal qty = Env.ZERO;
					
					StringBuffer sqlOrgWH = new StringBuffer
							("SELECT UNS_OrgWarehouse_ID FROM UNS_OrgWarehouse"
									+ " WHERE DocStatus = 'CO' AND AD_Org_ID = " + pLine.getAD_Org_ID());
					
					int idOrg = DB.getSQLValue(po.get_TrxName(), sqlOrgWH.toString()) >= 1 ?
							DB.getSQLValue(po.get_TrxName(), sqlOrgWH.toString()) : 0;
					
					MUNSOrgWarehouse orgWh = new MUNSOrgWarehouse(po.getCtx(), idOrg, po.get_TrxName());
					MOrderLine orderLine = new MOrderLine(po.getCtx(), pLine.getC_OrderLine_ID(), po.get_TrxName());
					if(idOrg <= 0)
					{
						qty = MStorageOnHand.getQtyOnHand(pLine.getM_Product_ID(), orderLine.getM_Warehouse_ID(), 0, po.get_TrxName());
					}
					
					if(idOrg > 0)
					{
						for(MUNSOrgWarehouseLine whLine : orgWh.getLines(pLine.getAD_Org_ID()))
						{
							qty =
							qty.add(MStorageOnHand.getQtyOnHand(pLine.getM_Product_ID(), whLine.getM_Warehouse_ID(), 0, po.get_TrxName()));							
						}
					}
					
					MUNSOrderQueue orderQueue = MUNSOrderQueue.get(
							pLine.getM_Product_ID(), orderLine.getAD_Org_ID(), po.get_TrxName());
					
					MUNSOrderQueueLine queueLine = MUNSOrderQueueLine.get(pLine.getC_OrderLine_ID(), pLine.getM_Product_ID(),
					pLine.getAD_Org_ID(), po.get_TrxName());
					
					StringBuffer sqlQtyReserved = new StringBuffer
							("SELECT SUM(Qty) FROM UNS_OrderQueue_Line WHERE UNS_OrderQueue_ID = " + orderQueue.get_ID()
									+ " AND Sequence < " + queueLine.getSequence()
									+ " AND isReserved = 'Y' OR (StatusQueue <> 'Q' AND StatusQueue <> 'WBA')");
					BigDecimal qtyReserved = DB.getSQLValueBD(po.get_TrxName(), sqlQtyReserved.toString());
					if(qtyReserved == null)
						qtyReserved = Env.ZERO;

					StringBuffer sqlQtyConfirm = new StringBuffer
							("SELECT SUM(Qty) FROM UNS_ConfirmationQueue_Line WHERE M_Product_ID = " + pLine.getM_Product_ID()
									+ " AND AD_Org_ID = " + pLine.getAD_Org_ID()
									+ " AND UNS_ConfirmationQueue_ID ="
									+ " (SELECT UNS_ConfirmationQueue_ID FROM UNS_ConfirmationQueue"
									+ " WHERE UNS_PackingList_ID = " + po.get_ID()
									+ " AND (DocStatus = 'CO' OR DocStatus = 'CL'))");
					BigDecimal qtyConfirm = DB.getSQLValueBD(po.get_TrxName(), sqlQtyConfirm.toString());
					
					if(qtyConfirm == null)
						qtyConfirm = Env.ZERO;
					
					if(qty.compareTo(qtyReserved.add(qtyConfirm.add(queueLine.getQty()))) == -1)
					{
						throw new AdempiereException("Need approval - running process Creating Confirmation Order");
					}
					
					if(qty.compareTo(qtyReserved.add(qtyConfirm.add(queueLine.getQty()))) != -1)
					{
						queueLine.setStatusQueue("PR");
						queueLine.setUNS_PackingList_ID(po.get_ID());
						queueLine.saveEx();
					}
				}
			}			
		}
		
		if (po.get_TableName().equals(MUNSPLReturnOrder.Table_Name) && timing == TIMING_AFTER_COMPLETE)
		{
			for(MUNSPLReturnOrder orders : getOrders(po))
			{
				MUNSPackingListOrder listOrder = new MUNSPackingListOrder(po.getCtx(), orders.getUNS_PackingList_Order_ID(), po.get_TrxName());
				
				for(MUNSPackingListLine listLine : getLines(po, listOrder))
				{
					StringBuffer sqlIDQLine = new StringBuffer
							("SELECT UNS_OrderQueue_Line_ID FROM UNS_OrderQueue_Line"
									+ " WHERE C_OrderLine_ID = " + listLine.getC_OrderLine_ID());
					int idQLine = DB.getSQLValue(po.get_TrxName(), sqlIDQLine.toString());
					
					MUNSOrderQueueLine queueLine = new MUNSOrderQueueLine(po.getCtx(), idQLine, po.get_TrxName());
					
					if(orders.isCancelled())
					{	
						if(queueLine.isReserved())
							queueLine.setStatusQueue("RQ");
						if(!queueLine.isReserved())
							queueLine.setStatusQueue("Q");
						queueLine.saveEx();
					}
				
					if(!orders.isCancelled())
					{
						if(listLine.getQtyShipping() == queueLine.getQty())
						{
							StringBuffer sqlDeleteQueue = new StringBuffer
								("DELETE FROM UNS_OrderQueue_Line WHERE UNS_OrderQueue_Line_ID = " + queueLine.get_ID());
							int deleteQueue = DB.executeUpdate(sqlDeleteQueue.toString(), po.get_TrxName());
						
							if(deleteQueue <= -1)
								throw new AdempiereException("Error Query For Delete Order Queue");
						}
						
						if(listLine.getQtyShipping() != queueLine.getQty())
							queueLine.setQty(queueLine.getQty().subtract(listLine.getQtyShipping()));
					}
				}
			}
		}
		
		if (po.get_TableName().equals(MOrder.Table_Name) && timing == TIMING_AFTER_COMPLETE)
		{
			if(po.get_ValueAsBoolean("isSOTrx"))
			{
				MOrder salesOrder = new MOrder(po.getCtx(), po.get_ID(), po.get_TrxName());
				
				//int idOrg = salesOrder.getSource_Org_ID() > 1 ? salesOrder.getSource_Org_ID() : salesOrder.getAD_Org_ID();
				
				for (MOrderLine orderLine : salesOrder.getLines())
				{
					StringBuffer sqlOrderQueue = new StringBuffer
							("SELECT UNS_OrderQueue_ID FROM UNS_OrderQueue"
									+ " WHERE M_Product_ID = "+ orderLine.getM_Product_ID()
									+ " AND AD_Org_ID = " + salesOrder.getM_Warehouse().getAD_Org_ID());
					int idOrderQueue = DB.getSQLValue(po.get_TrxName(), sqlOrderQueue.toString());
					
					OrderQueue = idOrderQueue >= 1 ? idOrderQueue : 0;
					
					MUNSOrderQueue orderQueue = new MUNSOrderQueue(po.getCtx(), OrderQueue, po.get_TrxName());
					
					if(idOrderQueue <= -1)
					{
						orderQueue.setAD_Org_ID(salesOrder.getM_Warehouse().getAD_Org_ID());
						orderQueue.setM_Product_ID(orderLine.getM_Product_ID());
						orderQueue.setIsSOTrx(true);
						orderQueue.saveEx();
					}
					
					MUNSOrderQueueLine queueLine = new MUNSOrderQueueLine(po.getCtx(), 0, po.get_TrxName());
					
					StringBuffer sqlSequence = new StringBuffer
							("SELECT MAX(Sequence) FROM UNS_OrderQueue_Line"
									+ " WHERE UNS_OrderQueue_ID = " + orderQueue.get_ID());
					BigDecimal maxSequence = DB.getSQLValueBD(po.get_TrxName(), sqlSequence.toString());
					
					if(maxSequence == null)	
						maxSequence = Env.ZERO;
					
					queueLine.setAD_Org_ID(orderLine.getAD_Org_ID());
					queueLine.setUNS_OrderQueue_ID(orderQueue.get_ID());
					queueLine.setSequence(maxSequence.add(Env.ONE));
					queueLine.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
					queueLine.setC_BPartner_ID(salesOrder.getC_BPartner_ID());
					queueLine.setDatePromised(orderLine.getDatePromised());
					queueLine.setQty(orderLine.getQtyEntered());		
//					queueLine.setisReserved(salesOrder.isReserved());
//					if(salesOrder.isReserved())
//						queueLine.setStatusQueue("RQ");
//					if(!salesOrder.isReserved())
//						queueLine.setStatusQueue("Q");
					queueLine.saveEx();
				}
			}
		}
		
		if (po.get_TableName().equals(MOrder.Table_Name) && timing == TIMING_AFTER_REACTIVATE)
		{
			if(po.get_ValueAsBoolean("isSOTrx"))
			{
				StringBuffer sql = new StringBuffer
						("DELETE FROM UNS_OrderQueue_Line WHERE C_OrderLine_ID IN"
								+ " (SELECT C_OrderLine_ID FROM C_OrderLine WHERE C_Order_ID = " + po.get_ID() +")");
				int deleteQueueLine = DB.executeUpdate(sql.toString(), po.get_TrxName());
				
				if(deleteQueueLine <= -1)
					throw new AdempiereException("Error Query For Delete Order Queue Line");
			}
		}
		
		if (po.get_TableName().equals(MOrder.Table_Name) && timing == TIMING_AFTER_VOID)
		{
			if(po.get_ValueAsBoolean("isSOTrx"))
			{
				StringBuffer sql = new StringBuffer
						("DELETE FROM UNS_OrderQueue_Line WHERE C_OrderLine_ID IN"
								+ " (SELECT C_OrderLine_ID FROM C_OrderLine WHERE C_Order_ID = " + po.get_ID() +")");
				int deleteQueueLine = DB.executeUpdate(sql.toString(), po.get_TrxName());
				
				if(deleteQueueLine <= -1)
					throw new AdempiereException("Error Query For Delete Order Queue Line");
			}
		}
		
		if (po.get_TableName().equals(MPayment.Table_Name) && timing == TIMING_AFTER_COMPLETE)
		{
			MPayment payment = new MPayment(po.getCtx(), po.get_ID(), po.get_TrxName());
			
			if(payment.getC_Order_ID() != 0)
			{
				StringBuffer sql = new StringBuffer
					("UPDATE UNS_OrderQueue_Line set isReserved = 'Y', StatusQueue = (CASE WHEN StatusQueue = 'Q' THEN 'RQ' END)"
								+ " WHERE C_OrderLine_ID IN (SELECT C_OrderLine_ID FROM C_OrderLine"
								+ " WHERE C_Order_ID = " + payment.getC_Order_ID() + ")");
				int delete = DB.executeUpdate(sql.toString(), po.get_TrxName());
				
				if(delete <= -1)
					throw new AdempiereException("Error Query For Update Status Reserved In Order Queue Line");
			}
			
			if(payment.getC_Order_ID() == 0)
			{
				String sql = "SELECT C_PaymentAllocate_ID FROM C_PaymentAllocate WHERE C_Payment_ID = " + po.get_ID();
				int allocate = DB.getSQLValue(po.get_TrxName(), sql.toString());
				
				MPaymentAllocate pAllocate = new MPaymentAllocate(po.getCtx(), allocate, po.get_TrxName());
				
				if(pAllocate.getC_Invoice_ID() !=0)
				{
					StringBuffer sqlUpdate = new StringBuffer
							("UPDATE UNS_OrderQueue_Line set isReserved = 'Y', StatusQueue = (CASE WHEN StatusQueue = 'Q' THEN 'RQ' END)"
										+ " WHERE C_OrderLine_ID IN (SELECT C_OrderLine_ID FROM C_OrderLine"
										+ " WHERE C_Order_ID = " + pAllocate.getC_Invoice().getC_Order_ID() + ")");
						int delete = DB.executeUpdate(sqlUpdate.toString(), po.get_TrxName());
						
					if(delete <= -1)
						throw new AdempiereException("Error Query For Update Status Reserved In Order Queue Line");
				}
			}
		}
		return null;
	}
}