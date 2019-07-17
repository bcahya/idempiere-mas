/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.model.MStorageOnHand;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.MUNSConfirmationQueue;
import com.unicore.model.MUNSConfirmationQueueLine;
import com.unicore.model.MUNSOrderQueue;
import com.unicore.model.MUNSOrderQueueLine;
import com.unicore.model.MUNSOrgWarehouse;
import com.unicore.model.MUNSOrgWarehouseLine;
import com.unicore.model.MUNSPackingList;
import com.unicore.model.MUNSPackingListLine;
import com.unicore.model.MUNSPackingListOrder;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author ALBURHANY
 *
 */
public class ProcessCreateQueueConfirmation extends SvrProcess {
	
	private MUNSPackingListLine[] m_PackingListLine = null;
	private MUNSPackingListOrder[] m_PackingListOrder = null;
	//private int m_employee_ID = 0;
	
	/**
	 * 
	 */
	public ProcessCreateQueueConfirmation() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
//		ProcessInfoParameter[] params = getParameter();
//		for(ProcessInfoParameter param : params)
//		{
//			if(param.getParameterName() == null)
//				;
//			else if(param.getParameterName().equals("UNS_Employee_ID"))
//				m_employee_ID = param.getParameterAsInt();
//			else
//				log.log(Level.SEVERE, "Unknown Parameter: " + param.getParameterName());
//		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		return cekQueue();
	}
	
	public MUNSPackingListOrder[] getOrders() 
	{
		if (m_PackingListOrder != null)
			return m_PackingListOrder;
		
		List<MUNSPackingListOrder> list = null;
		list = Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPackingListOrder.Table_Name, 
						 "UNS_PackingList_ID=" + getRecord_ID(), get_TrxName())
					.list();
		MUNSPackingListOrder[] retValue = new MUNSPackingListOrder[list.size()];
		list.toArray(retValue);
		m_PackingListOrder = retValue;
		
		return retValue;
	}
	
	public MUNSPackingListLine[] getLines(MUNSPackingListOrder orders)
	{
		if (m_PackingListLine != null)
			return m_PackingListLine;
		
		List<MUNSPackingListLine> list = null;
		list = Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPackingListLine.Table_Name, 
						 "UNS_PackingList_Order_ID=" + orders.get_ID(), get_TrxName())
					.list();
		MUNSPackingListLine[] retValue = new MUNSPackingListLine[list.size()];
		list.toArray(retValue);
		m_PackingListLine = retValue;
		
		return retValue;
	}
	
	public String cekQueue()
	{
		MUNSPackingList packingList = new MUNSPackingList(getCtx(), getRecord_ID(), get_TrxName());
		for (MUNSPackingListOrder orders : getOrders())
		{			
			MOrder order = new MOrder(getCtx(), orders.getC_Order_ID(), get_TrxName());
			BigDecimal qty = Env.ZERO;
			
			for(MUNSPackingListLine lines : getLines(orders))
			{
				StringBuffer sqlOrgWH = new StringBuffer
						("SELECT UNS_OrgWarehouse_ID FROM UNS_OrgWarehouse"
								+ " WHERE DocStatus = 'CO' AND AD_Org_ID = " + packingList.getAD_Org_ID());
				int idOrg = DB.getSQLValue(get_TrxName(), sqlOrgWH.toString()) >= 1 ?
						DB.getSQLValue(get_TrxName(), sqlOrgWH.toString()) : 0;
				
				MUNSOrgWarehouse orgWh = new MUNSOrgWarehouse(getCtx(), idOrg, get_TrxName());
				
				if(idOrg == 0)
				{
					qty = MStorageOnHand.getQtyOnHand(lines.getM_Product_ID(), order.getM_Warehouse_ID(), 0, get_TrxName());
				}
				
				if(idOrg > 0)
				{
					for(MUNSOrgWarehouseLine whLine : orgWh.getLines(lines.getAD_Org_ID()))
					{
						qty =
						qty.add(MStorageOnHand.getQtyOnHand(lines.getM_Product_ID(), whLine.getM_Warehouse_ID(), 0, get_TrxName()));							
					}
				}
				 
				MUNSOrderQueue orderQueue = MUNSOrderQueue.get(
						lines.getM_Product_ID(), packingList.getAD_Org_ID(), get_TrxName());
				MUNSOrderQueueLine queueLine = MUNSOrderQueueLine.get(lines.getC_OrderLine_ID(), lines.getM_Product_ID(), lines.getAD_Org_ID(), get_TrxName());
				
				StringBuffer sqlQtyReserved = new StringBuffer
						("SELECT SUM(Qty) FROM UNS_OrderQueue_Line WHERE UNS_OrderQueue_ID = " + orderQueue.get_ID()
								+ " AND Sequence < " + queueLine.getSequence()
								+ " AND isReserved = 'Y' OR (StatusQueue <> 'Q' AND StatusQueue <> 'WBA')");
				BigDecimal qtyReserved = DB.getSQLValueBD(get_TrxName(), sqlQtyReserved.toString());
				
				StringBuffer sqlQtyConfirm = new StringBuffer
						("SELECT SUM(Qty) FROM UNS_ConfirmationQueue_Line WHERE M_Product_ID = " + lines.getM_Product_ID()
								+ " AND AD_Org_ID = " + packingList.getAD_Org_ID()
								+ " AND UNS_ConfirmationQueue_ID ="
								+ " (SELECT UNS_ConfirmationQueue_ID FROM UNS_ConfirmationQueue"
								+ " WHERE UNS_PackingList_ID = " + getRecord_ID()
								+ " AND (DocStatus = 'CO' OR DocStatus = 'CL'))");
				BigDecimal qtyConfirm = DB.getSQLValueBD(get_TrxName(), sqlQtyConfirm.toString());
				
				if(qtyConfirm == null)
					qtyConfirm = Env.ZERO;
				
				if(qty.compareTo(qtyReserved.add(qtyConfirm.add(queueLine.getQty()))) != -1)
				{
					throw new AdempiereException("No approval queue required. You can proceed without creating confirmation");
				}
				
				if(qty.compareTo(qtyReserved.add(qtyConfirm.add(queueLine.getQty()))) == -1)
				{
					StringBuffer sqlCekCQDR = new StringBuffer
							("SELECT UNS_ConfirmationQueue_ID FROM UNS_ConfirmationQueue"
									+ " WHERE DocStatus = 'DR' AND UNS_PackingList_ID = " + getRecord_ID());
					int idCekDR = DB.getSQLValue(get_TrxName(), sqlCekCQDR.toString());
					
					if(idCekDR >= 1)
						throw new AdempiereException("Status : In Progress");
					
					StringBuffer sqlCekCO = new StringBuffer
							("SELECT UNS_ConfirmationQueue_ID FROM UNS_ConfirmationQueue"
									+ " WHERE DocStatus = 'CO' AND UNS_PackingList_ID = " + getRecord_ID());
					int idCekCO = DB.getSQLValue(get_TrxName(), sqlCekCO.toString());
					
					if(idCekCO >= 1)
						throw new AdempiereException("Status : Confirmed");

					MUNSConfirmationQueue confQueue = new MUNSConfirmationQueue(getCtx(), 0, get_TrxName());
					java.util.Date date = new java.util.Date();
					
					confQueue.setDateRequired(new Timestamp(date.getTime()));
					confQueue.setUNS_PackingList_ID(getRecord_ID());
					confQueue.setDatePackingList(packingList.getDateDoc());
					//confQueue.setUNS_Employee_ID(m_employee_ID);
					confQueue.saveEx();
					
					MUNSConfirmationQueueLine cqLine = new MUNSConfirmationQueueLine(getCtx(), 0, get_TrxName());
					
					cqLine.setUNS_ConfirmationQueue_ID(confQueue.get_ID());
					cqLine.setC_OrderLine_ID(lines.getC_OrderLine_ID());
					cqLine.setM_Product_ID(lines.getM_Product_ID());
					cqLine.setQty(lines.getQtyShipping());
					cqLine.setSequence(queueLine.getSequence());
					cqLine.saveEx();
					
					queueLine.setStatusQueue("WBA");
					queueLine.saveEx();
				}
			}
		}
		return "Succes Create Confirmation Order Queue";
	}
	}
