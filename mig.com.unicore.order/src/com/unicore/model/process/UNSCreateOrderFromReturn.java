package com.unicore.model.process;

import java.math.BigDecimal;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.unicore.base.model.MInOut;
import com.unicore.base.model.MInOutLine;
import com.unicore.base.model.MOrder;
import com.unicore.base.model.MOrderLine;

/**
 * 
 * @author root
 * @see www.untasoft.com
 * Create Sales / Purchase Order From Custome Return
 */
public class UNSCreateOrderFromReturn extends SvrProcess {
	
	private int p_M_PriceList_ID = 0;
	private String p_DocumentNo = null;
	private MInOut m_inOut = null;

	public UNSCreateOrderFromReturn() 
	{
		super ();
	}

	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] params = getParameter();
		for (int i=0; i<params.length; i++)
		{
			ProcessInfoParameter param = params[i];
			String paramName = param.getParameterName();
			if(paramName.equals("DocumentNo"))
				p_DocumentNo = param.getParameterAsString();
			else if (paramName.equals("M_PriceList_ID"))
				p_M_PriceList_ID = param.getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown parameter " + paramName);
		}
		
		m_inOut = new MInOut(getCtx(), getRecord_ID(), get_TrxName());
	}

	@Override
	protected String doIt() throws Exception {
		int C_OrderSwap_ID = m_inOut.get_ValueAsInt("C_OrderSwap_ID");
		if(C_OrderSwap_ID > 0)
		{
			return "Order already created";
		}
		
		MOrder order = new MOrder(getCtx(), 0, get_TrxName());
		order.setClientOrg(getAD_Client_ID(), m_inOut.getAD_Org_ID());
		if(p_DocumentNo != null)
		{
			order.setDocumentNo(p_DocumentNo);
		}
		
		MBPartner partner = new MBPartner(getCtx(), m_inOut.getC_BPartner_ID(), 
				get_TrxName());
		order.setBPartner(partner);
		order.setM_PriceList_ID(p_M_PriceList_ID);
		order.setAD_OrgTrx_ID(m_inOut.getAD_Org_ID());
		order.setAD_User_ID(m_inOut.getAD_User_ID());
		order.setC_Activity_ID(m_inOut.getC_Activity_ID());
		order.setC_Campaign_ID(m_inOut.getC_Campaign_ID());
		order.setC_Charge_ID(m_inOut.getC_Charge_ID());
		order.setC_Currency_ID(m_inOut.getC_Currency_ID());
		order.setIsSOTrx(m_inOut.isSOTrx());
		order.setC_DocTypeTarget_ID();
		order.setC_Project_ID(m_inOut.getC_Project_ID());
		order.setSalesRep_ID(m_inOut.getSalesRep_ID());
		order.setM_Warehouse_ID(m_inOut.getM_Warehouse_ID());
		order.saveEx();
		
		MInOutLine[] ioLines = m_inOut.getLines(true);
		for (int i=0; i<ioLines.length; i++)
		{
			MOrderLine oLine = new MOrderLine(order);
			oLine.setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
			oLine.setC_Activity_ID(ioLines[i].getC_Activity_ID());
			oLine.setC_Campaign_ID(ioLines[i].getC_Campaign_ID());
			oLine.setC_Charge_ID(ioLines[i].getC_Charge_ID());
			oLine.setC_Project_ID(ioLines[i].getC_Project_ID());
			oLine.setC_UOM_ID(ioLines[i].getC_UOM_ID());
			oLine.setDatePromised(order.getDatePromised());
			oLine.setisProductBonuses(false);
			oLine.setM_AttributeSetInstance_ID(ioLines[i].
					getM_AttributeSetInstance_ID());
			oLine.setM_Product_ID(ioLines[i].getM_Product_ID());
			oLine.setM_Warehouse_ID(order.getM_Warehouse_ID());
			oLine.setQty(ioLines[i].getMovementQty());
			
			String sql = " SELECT CONCAT (PriceActual, ';', PriceLimit, ';', "
					+ " PriceList) FROM C_InvoiceLine WHERE C_InvoiceLine_ID "
					+ " = ? ";
			String result = DB.getSQLValueString(get_TrxName(), sql, 
					ioLines[i].getC_InvoiceLine_ID());
			if (null == result)
				throw new AdempiereException(
						"credit memo for the return of the material has not created");
			
			String[] values = result.split(";");
			oLine.setPriceList(new BigDecimal(values[2]));
			oLine.setPriceLimit(new BigDecimal(values[1]));
			oLine.setPrice(new BigDecimal(values[0]));
			
			oLine.saveEx();
			
			ioLines[i].set_ValueOfColumn("C_OrderLineSwap_ID", oLine.get_ID());
			ioLines[i].saveEx();
		}
		
		m_inOut.set_ValueOfColumn("C_OrderSwap_ID", order.get_ID());
		m_inOut.saveEx();
		
		return "Created Order Swap " + order.getDocumentNo();
	}

}
