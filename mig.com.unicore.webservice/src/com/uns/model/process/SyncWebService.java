/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.unicore.model.MUNSPreOrder;
import com.unicore.model.MUNSPreOrderLine;
import com.uns.model.MUNSQueueWS;

/**
 * @author Burhani Adam
 *
 */
public class SyncWebService extends SvrProcess {

	private int org_ID = 0;
	private int salesRep_ID = 0;
	private int partner_ID = 0;
	private Timestamp dateDoc = null;
	private Timestamp dateProm = null;
	private String desc = null;
	private int product_ID = 0;
	private BigDecimal qty = Env.ZERO;
	
	/**
	 * 
	 */
	public SyncWebService() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		String sql = "SELECT ARRAY_TO_STRING(ARRAY_AGG(DISTINCT(Referenced_Table_ID)),';') FROM UNS_Queue_WS"
				+ " WHERE Status <> 'S'";
		String value = DB.getSQLValueString(get_TrxName(), sql);
		if(Util.isEmpty(value, true))
			return "No data can be synced";
		String[] values = value.split(";");
		
		for(int i = 0; i < values.length; i++)
		{
			if(MUNSPreOrderLine.Table_ID == Integer.parseInt(values[i]))
			{
				SyncPreOrderWS(MUNSPreOrderLine.Table_ID);
			}
		}
		return "Success";
	}
	
	
	private void SyncPreOrderWS(int Table_ID)
	{
		for(MUNSQueueWS ws : MUNSQueueWS.getOfTable(getCtx(), Table_ID, get_TrxName()))
		{
			MUNSPreOrder pOrder = null;
			String[] rows = ws.getMessage().split(";");
			for(int i = 0; i < rows.length; i++)
			{
				String[] row = rows[i].split("=");
				String column = row[0];
				String value = row[1];
				
				switch (column.trim()) { 
				case "AD_Org_ID" : 
				{
					org_ID = DB.getSQLValue(get_TrxName(), "SELECT AD_Org_ID FROM AD_Org WHERE Value=?", value);
					if(org_ID <= 0)
					{
						ws.setHelp("Not found Organization with key " + value);
						ws.setStatus("F");
						ws.saveEx();
						return;
					}
					continue;
				}
				case "PartnerValue" : 
				{
					partner_ID = DB.getSQLValue(get_TrxName(), "SELECT C_BPartner_ID FROM C_BPartner WHERE Value=?", value);
					if(partner_ID <= 0)
					{
						ws.setHelp("Not found partner with key " + value);
						ws.setStatus("F");
						ws.saveEx();
						return;
					}
					continue;
				}
				case "SalesRep_ID" :
				{
					salesRep_ID = DB.getSQLValue(get_TrxName(), "SELECT AD_User_ID FROM AD_User WHERE Name=?", value);
					if(salesRep_ID <= 0)
					{
						ws.setHelp("Not found user with name " + value);
						ws.setStatus("F");
						ws.saveEx();
						return;
					}
					continue;
				}
				case "Description" : desc = value;
					continue;
				case "M_Product_ID" : 
				{
					product_ID = DB.getSQLValue(get_TrxName(), "SELECT M_Product_ID FROM M_Product WHERE Value=?", value);
					if(product_ID <= 0)
					{
						ws.setHelp("Not found product with key " + value);
						ws.setStatus("F");
						ws.saveEx();
						return;						
					}
					continue;
				}
				case "Qty" : qty = new BigDecimal(value);
					continue;
				case "DateDoc" : dateDoc = Timestamp.valueOf(value);
					continue;
				case "DatePromised" : dateProm = Timestamp.valueOf(value);
					continue;
				default:
					break;
				}
			}
			
			String sql = "SELECT UNS_PreOrder_ID FROM UNS_PreOrder WHERE DateDoc=? AND DatePromised=? AND SalesRep_ID=?"
					+ " AND C_BPartner_ID=? AND CreateSO = 'N'";
			int exists = DB.getSQLValue(get_TrxName(), sql, new Object[]{dateDoc, dateProm, salesRep_ID, partner_ID});
			if(exists > 0)
				pOrder = new MUNSPreOrder(getCtx(), exists, get_TrxName());
			else
			{
				pOrder = new MUNSPreOrder(getCtx(), 0, get_TrxName());
				pOrder.setAD_Org_ID(org_ID);
				pOrder.setC_BPartner_ID(partner_ID);
				pOrder.setDateDoc(dateDoc);
				pOrder.setDatePromised(dateProm);
				pOrder.setDescription(desc);
				pOrder.setSalesRep_ID(salesRep_ID);
				pOrder.saveEx();
			}
			
			MUNSPreOrderLine pol = new MUNSPreOrderLine(getCtx(), 0, get_TrxName());
			pol.setUNS_PreOrder_ID(pOrder.get_ID());
			pol.setAD_Org_ID(org_ID);
			pol.setM_Product_ID(product_ID);
			pol.setQtyOrdered(qty);
			if(pol.save())
			{
				ws.setStatus("S");
				ws.setDeliveredDate(new Timestamp(System.currentTimeMillis()));
				ws.saveEx();
			}
		}
	}
}