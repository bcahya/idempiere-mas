/**
 * 
 */
package com.unicore.model.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MOrder;
import org.compiere.model.MPriceList;
import org.compiere.model.MProductPrice;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Msg;
import org.compiere.wf.MWorkflow;

import com.uns.util.MessageBox;

import com.unicore.base.model.MOrderLine;
import com.unicore.model.MUNSPreOrder;
import com.unicore.model.MUNSPreOrderLine;

/**
 * @author ALBURHANY
 *
 */
public class CreateSalesOrder extends SvrProcess {

	private int m_PriceList_ID = 0;
	private int m_Order_ID = 0;
	private int m_SalesRep_ID = 0;
	private int m_Warehouse_ID = 0;
	/**
	 * 
	 */
	public CreateSalesOrder() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] params = getParameter();
		for(ProcessInfoParameter param : params)
		{
			if(param.getParameterName() == null)
				;
			else if(param.getParameterName().equals("M_PriceList_ID"))
				m_PriceList_ID = param.getParameterAsInt();
			else if(param.getParameterName().equals("SalesRep_ID"))
				m_SalesRep_ID = param.getParameterAsInt();
			else if(param.getParameterName().equals("C_Order_ID"))
				m_Order_ID = param.getParameterAsInt();
			else if(param.getParameterName().equals("M_Warehouse_ID"))
				m_Warehouse_ID = param.getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + param.getParameterName());
		}
	}
	
	/**
	 * Check parameter
	 * @param C_Order_ID
	 * @param M_PriceList_ID
	 * @param M_Warehouse_ID
	 * @param SalesRep_ID
	 * @return null
	 */
	public String cekParam(int C_Order_ID, int M_PriceList_ID, int M_Warehouse_ID, int SalesRep_ID)
	{
		if(C_Order_ID == 0)
		{
			if(M_PriceList_ID == 0)
				throw new AdempiereException("Please Define PriceList");
			
			if(M_Warehouse_ID == 0)
				throw new AdempiereException("Please Define Warehouse");
			
			if(SalesRep_ID == 0)
				throw new AdempiereException("Please Define Sales Representative");
		}
		
//		if(C_Order_ID != 0)
//		{
//			MOrder order = new MOrder(getCtx(), C_Order_ID, get_TrxName());
//			m_PriceList_ID = order.getM_PriceList_ID();
//			m_Warehouse_ID = order.getM_Warehouse_ID();
//			m_SalesRep_ID = order.getSalesRep_ID();
//		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{	
		cekParam(m_Order_ID, m_PriceList_ID, m_Warehouse_ID, m_SalesRep_ID);
		
		MUNSPreOrder preOrder = new MUNSPreOrder(getCtx(), getRecord_ID(), get_TrxName());
		MPriceList priceList = new MPriceList(getCtx(), m_PriceList_ID, get_TrxName());
		
		MOrder order = new MOrder(getCtx(), m_Order_ID, get_TrxName());
		
		StringBuffer sql1 = new StringBuffer
				("SELECT C_BPartner_Location_ID FROM C_BPartner_Location WHERE C_BPartner_ID = " + preOrder.getC_BPartner_ID());
		if (DB.getSQLValue(get_TrxName(), sql1.toString()) <= -1)
				throw new AdempiereException("Not Location Customer");
				
		MBPartnerLocation bpl = new MBPartnerLocation(getCtx(), DB.getSQLValue(get_TrxName(), sql1.toString()), get_TrxName());
		
		if(m_Order_ID == 0)
		{
			order.setAD_Org_ID(preOrder.getAD_Org_ID());
			order.setC_DocTypeTarget_ID(1000385);
			order.setC_BPartner_ID(preOrder.getC_BPartner_ID());
			order.setAD_Org_ID(preOrder.getAD_Org_ID());
			order.setC_BPartner_Location_ID(bpl.get_ID());
			order.setM_PriceList_ID(m_PriceList_ID);
			order.setM_Warehouse_ID(m_Warehouse_ID);
			order.setSalesRep_ID(m_SalesRep_ID);
			order.setDescription(preOrder.getDescription());
			order.saveEx();
		}
		
		if(m_Order_ID != 0)
		{
			m_PriceList_ID = order.getM_PriceList_ID();
			m_SalesRep_ID = order.getSalesRep_ID();
			m_Warehouse_ID = order.getM_Warehouse_ID();
		}
		
		preOrder.setC_Order_ID(order.get_ID());
		preOrder.setCreateSO("Y");
		preOrder.saveEx();
		
		for (MUNSPreOrderLine poLine : preOrder.getLines(getRecord_ID()))
		{
			String sql = "SELECT M_PriceList_Version_ID FROM M_ProductPrice WHERE M_Product_ID = " + poLine.getM_Product_ID()
						+ " AND M_PriceList_Version_ID IN "
						+ " (SELECT M_PriceList_Version_ID FROM M_PriceList_Version WHERE M_PriceList_ID = " + m_PriceList_ID + ")";
				
			if (DB.getSQLValue(get_TrxName(), sql) <= -1)
					throw new AdempiereException("Product " + poLine.getM_Product().getName()
							+ " Not In Price List " + priceList.getName());
				
			if (DB.getSQLValue(get_TrxName(), sql) >= 1)
			{
				MOrderLine orderLine = new MOrderLine(getCtx(), 0, get_TrxName());
				MProductPrice productPrice = new MProductPrice(getCtx(), DB.getSQLValue(get_TrxName(), sql),
						poLine.getM_Product_ID(), get_TrxName());
				
				orderLine.setAD_Org_ID(poLine.getAD_Org_ID());
				orderLine.setPrice(productPrice.getPriceStd());
				orderLine.setM_Product_ID(poLine.getM_Product_ID());
				orderLine.setQtyEntered(poLine.getQtyOrdered());
				orderLine.setC_Order_ID(order.get_ID());
				orderLine.setPriceEntered(productPrice.getPriceStd());
				orderLine.saveEx();
			}
		}
		
		int retVal = MessageBox.showMsg(getCtx(), getProcessInfo()
				, "Sales Order has been created. \n" + 
				  "Do you want to complete that document ?"
				, "Status document of Sales Order"
				, MessageBox.YESNO
				, MessageBox.ICONQUESTION);
		if(retVal == MessageBox.RETURN_YES)
		{
			try
			{
				if (!order.isComplete())
				{
					ProcessInfo orderInfo = MWorkflow.runDocumentActionWorkflow(order, DocAction.ACTION_Complete);
						if(orderInfo.isError())
						{
							return orderInfo.getSummary();
						}
				}
			}
			catch (Exception e) {
				return e.getMessage();
			}
		}
		else 
			return null;
		
		String message = Msg.parseTranslation(getCtx(), "Sales Order has Created (No Document : " + order.getDocumentNo() + ")");
		addBufferLog(0, order.getDateOrdered(), order.getGrandTotal(), message, order.get_Table_ID(), order.get_ID());
		
		return message;
	}
}