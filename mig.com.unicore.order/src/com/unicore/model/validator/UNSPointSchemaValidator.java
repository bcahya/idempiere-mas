/**
 * 
 */
package com.unicore.model.validator;

import java.math.BigDecimal;
import java.util.List;

import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.MUNSCustomerPoint;
import com.unicore.model.MUNSCustomerPointLine;
import com.unicore.model.MUNSPSProduct;
import com.unicore.model.MUNSPackingListOrder;
import com.unicore.model.MUNSPointSchema;
import com.unicore.model.MUNSPointSchemaLine;

/**
 * @author ALBURHANY
 *
 */
public class UNSPointSchemaValidator implements ModelValidator {
	
	private MUNSPointSchema m_pointschema = null;
	int idPointSchema;
	int idPSProduct;
	String[] values;
	String[] Qtys;
	String pointType;
	String typeAll;

	/**
	 * 
	 */
	public UNSPointSchemaValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
		
		engine.addDocValidate(MInvoice.Table_Name, this);
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
		
		if(po.get_Table_ID() != MInvoice.Table_ID)
			return null;
		if(type != TYPE_BEFORE_CHANGE && type != TYPE_BEFORE_NEW)
			return null;
		
		if(po.get_Table_ID() == MInvoice.Table_ID)
		{
			MInvoice invoice = (MInvoice) po;
			
			if(invoice.is_ValueChanged(MInvoice.COLUMNNAME_IsPaid))
			{
				String ispaid = invoice.isPaid() ? "Y" : "N";
				String sql = "UPDATE UNS_CustomerPoint_Line SET IsValid ='" + ispaid
						+ "' WHERE C_InvoiceLine_ID IN (SELECT C_InvoiceLine_ID FROM C_InvoiceLine WHERE C_Invoice_ID ="
						+ invoice.get_ID() + ")";
				DB.executeUpdate(sql, po.get_TrxName());
			}
		}
		return null;
	}

	@Override
	public String docValidate(PO po, int timing) {

		if (po.get_TableName().equals(MInvoice.Table_Name) && timing == TIMING_AFTER_COMPLETE)
		{	
			m_pointschema = null;
			String existPS = "SELECT UNS_PointSchema_ID FROM UNS_PointSchema WHERE DocStatus = 'CO'";
			if(DB.getSQLValue(po.get_TrxName(), existPS) <= 0)
				return null;
			else
			{
				MInvoice inv = new MInvoice(po.getCtx(), po.get_ID(), po.get_TrxName());
				
				String whereClause = "UNS_PackingList_ID IN"
						+ " (SELECT UNS_PackingList_ID FROM UNS_PackingList_Order WHERE C_Invoice_ID = ?)";
				List<MUNSPackingListOrder> plOrder = new Query(po.getCtx(), MUNSPackingListOrder.Table_Name,
						whereClause, po.get_TrxName())
							.setParameters(po.get_ID())
								.list();
				
				if(plOrder.size() == 1)
					pointType = "OSOC";
				else if (plOrder.size() == 0)
					pointType = "OSMC";
				else
					pointType = "OSMC";
				
				if(pointType == "OSOC")
				{
					m_pointschema = MUNSPointSchema.getByBPartner(po.getCtx(), inv.getC_BPartner_ID(), pointType, po.get_TrxName());
					if(m_pointschema == null)
					{
						m_pointschema = MUNSPointSchema.getByBPGroup(po.getCtx(), inv.getC_BPartner().getC_BP_Group_ID(),
								pointType, po.get_TrxName());
					}
					if (m_pointschema == null)
					{
						m_pointschema = MUNSPointSchema.getByOutletGrade(po.getCtx(), inv.getC_BPartner().getUNS_Outlet_Grade_ID(),
								pointType, po.get_TrxName());
					}
					if(m_pointschema == null)
					{
						m_pointschema = MUNSPointSchema.getByOutletType(po.getCtx(), inv.getC_BPartner().getUNS_Outlet_Type_ID(),
								pointType, po.get_TrxName());
					}
				}
				
				if(m_pointschema == null)
				{
					pointType = "OSMC";
					m_pointschema = MUNSPointSchema.getByBPartner(po.getCtx(), inv.getC_BPartner_ID(), pointType, po.get_TrxName());		
				}
				if(m_pointschema == null)
				{
					pointType = "OSMC";
					m_pointschema = MUNSPointSchema.getByBPGroup(po.getCtx(), inv.getC_BPartner().getC_BP_Group_ID(),
							pointType, po.get_TrxName());		
				}
				if(m_pointschema == null)
				{
					pointType = "OSMC";
					m_pointschema = MUNSPointSchema.getByOutletGrade(po.getCtx(), inv.getC_BPartner().getUNS_Outlet_Grade_ID(),
							pointType, po.get_TrxName());		
				}
				if(m_pointschema == null)
				{
					pointType = "OSMC";
					m_pointschema = MUNSPointSchema.getByOutletType(po.getCtx(), inv.getC_BPartner().getUNS_Outlet_Type_ID(),
							pointType, po.get_TrxName());		
				}
				if(m_pointschema == null)
				{	
					m_pointschema = new MUNSPointSchema(po.getCtx(), DB.getSQLValue(po.get_TrxName(), existPS), po.get_TrxName());	
					
					String whereClauseinv = "C_Invoice_ID = " + inv.get_ID();
					List <MInvoiceLine> invLine = 
							new Query(po.getCtx(), MInvoiceLine.Table_Name, whereClauseinv, po.get_TrxName())
							.list();
					
					String whereClausePSp = "UNS_PointSchema_ID = ?";
					List <MUNSPSProduct> psProduct = 
							new Query(po.getCtx(), MUNSPSProduct.Table_Name, whereClausePSp, po.get_TrxName())
								.setParameters(m_pointschema.get_ID())
									.list();
					
					for (MInvoiceLine invL : invLine)
					{
						for (MUNSPSProduct psProduct2 : psProduct)
						{
							if (invL.getM_Product_ID() == psProduct2.getM_Product_ID())
							{
								idPSProduct = psProduct2.get_ID();
								break;
							}
							
							if (invL.getM_Product().getM_Product_Category_ID() == psProduct2.getM_Product_Category_ID())
							{
								idPSProduct = psProduct2.get_ID();
								break;
							}	
						}
						
						String whereClausePSLine = "UNS_PS_Product_ID = ?";
						List <MUNSPointSchemaLine> pLine = new Query(po.getCtx(), MUNSPointSchemaLine.Table_Name,
								whereClausePSLine, po.get_TrxName())
									.setParameters(idPSProduct).setOrderBy("Qty DESC")
										.list();
						
						BigDecimal point = Env.ZERO;
						BigDecimal Quantity = Env.ZERO;
						BigDecimal get_point = Env.ZERO;
						Quantity = invL.getQtyInvoiced();
						
						for (MUNSPointSchemaLine psLine : pLine)
						{											
							if(Quantity.compareTo(psLine.getQty()) == 1)
							{
								point = Quantity.divide(psLine.getQty(), 0, BigDecimal.ROUND_FLOOR);
								get_point = get_point.add((point.multiply(psLine.getValuePoint())));
								Quantity = Quantity.subtract(point.multiply(psLine.getQty()));
							}
							
							if(Quantity.compareTo(psLine.getQty()) == 0)
							{
								get_point = get_point.add(psLine.getValuePoint());
								break;
							}
							
							if(Quantity.compareTo(psLine.getQty()) == -1)
							{
								continue;
							}
						}
						
						StringBuffer sqlCustomerPoint = new StringBuffer
								("SELECT UNS_CustomerPoint_ID FROM UNS_CustomerPoint"
										+ " WHERE C_BPartner_ID = (SELECT C_BPartner_ID FROM C_Invoice"
										+ " WHERE C_Invoice_ID = " + inv.getC_Invoice_ID() + ")");
						int idCustomerPoint = DB.getSQLValue(po.get_TrxName(), sqlCustomerPoint.toString());
						
						if (idCustomerPoint <= -1)
						{
							MUNSCustomerPoint newCustomer = new MUNSCustomerPoint(po.getCtx(), 0, po.get_TrxName());
							newCustomer.setAD_Org_ID(inv.getAD_Org_ID());
							newCustomer.setC_BPartner_ID(inv.getC_BPartner_ID());
							newCustomer.setStartTrxDate(inv.getDateInvoiced());
							newCustomer.setLatestTrxDate(inv.getDateInvoiced());
							newCustomer.setIsSOTrx(inv.isSOTrx());
							newCustomer.saveEx();
							idCustomerPoint = newCustomer.get_ID();
						}
						
						if (idCustomerPoint >= 1)
						{
							MUNSCustomerPoint customerPoint = new MUNSCustomerPoint(po.getCtx(), idCustomerPoint, po.get_TrxName());
							customerPoint.setLatestTrxDate(inv.getDateInvoiced());
							customerPoint.saveEx();
						}
						
						MUNSCustomerPointLine newCSLine = new MUNSCustomerPointLine(po.getCtx(), 0, po.get_TrxName());
						newCSLine.setUNS_CustomerPoint_ID(idCustomerPoint);
						newCSLine.setUNS_PointSchema_ID(idPointSchema);
						newCSLine.setPoint(get_point);
						newCSLine.setM_Product_ID(invL.getM_Product_ID());
						newCSLine.setQty(invL.getQtyInvoiced());
						newCSLine.setDateInvoiced(inv.getDateInvoiced());
						newCSLine.setC_InvoiceLine_ID(invL.getC_InvoiceLine_ID());
						newCSLine.setIsValid(inv.isPaid());
						newCSLine.saveEx();
					}			
				}
			}
		}
		if (po.get_TableName().equals(MInvoice.Table_Name) && timing == TIMING_BEFORE_REVERSEACCRUAL)
		{
			StringBuffer sql = new StringBuffer
					("DELETE UNS_CustomerPoint_Line WHERE C_InvoiceLine_ID IN"
							+ " (SELECT C_InvoiceLine_ID FROM C_InvoiceLine WHERE C_Invoice_ID = " + po.get_ID() + ")");
			DB.executeUpdate(sql.toString(), po.get_TrxName());
		}
		return null;
	}
}
