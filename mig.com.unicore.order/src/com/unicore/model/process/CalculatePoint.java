/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.model.MUNSCustomerPoint;
import com.unicore.model.MUNSCustomerPointLine;
import com.unicore.model.MUNSPointSchemaLine;

/**
 * @author ALBURHANY
 *
 */
public class CalculatePoint extends SvrProcess {

	int idPointSchema;
	String[] values;
	String[] Qtys;
	String type;
	String typeAll;
	/**
	 * 
	 */
	public CalculatePoint() {
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
		cekPackingList();
		cekPointSchema();
		return calculatePoint(idPointSchema);
	}
	
	public boolean cekPackingList() throws Exception
	{
		MInvoice inv = new MInvoice(getCtx(), getRecord_ID(), get_TrxName());
		
		StringBuffer sql = new StringBuffer
				("SELECT C_Invoice_ID FROM UNS_PackingList_Order WHERE"
						+ " C_Invoice_ID = " + inv.getC_Invoice_ID());
		int idInv = DB.getSQLValue(get_TrxName(), sql.toString());
		
		if(idInv <= -1)
		{
			type = "OSMC";
		}
		
		return true;
	}
	
	public String cekPointSchema() throws Exception
	{			
		MInvoice inv = new MInvoice(getCtx(), getRecord_ID(), get_TrxName());
		
		//getPointType
		StringBuffer sqlPackingList = new StringBuffer
				("SELECT UNS_PackingList_ID FROM UNS_PackingList"
						+ " WHERE DocStatus = 'PR' OR DocStatus = 'CO' AND UNS_PackingList_ID IN"
						+ " (SELECT UNS_PackingList_ID FROM UNS_PackingList_Order"
						+ " WHERE C_Invoice_ID = " + inv.getC_Invoice_ID() + ")");
		int idPackingList = DB.getSQLValue(get_TrxName(), sqlPackingList.toString());
		
		StringBuffer sqlPointType = new StringBuffer
				("SELECT COUNT (DISTINCT (C_BPartner_ID)) FROM C_Invoice WHERE C_Invoice_ID IN"
						+ " (SELECT C_Invoice_ID FROM UNS_PackingList_Order"
						+ " WHERE UNS_PackingList_ID =?)");
		int pointType = DB.getSQLValue(get_TrxName(), sqlPointType.toString(), idPackingList);
		
		if (pointType == 1)
		{
			type = "OSOC";
			typeAll = "OSMC";
		}
		
		if (pointType > 1)
			type= "OSMC";
		
		//getGroupBPartner
		StringBuffer sqlGroupBP = new StringBuffer
				("SELECT C_BP_Group_ID FROM C_BPartner WHERE C_BPartner_ID = " + inv.getC_BPartner_ID());
		int idGroupBP = DB.getSQLValue(get_TrxName(), sqlGroupBP.toString());

		//getOutletType
		StringBuffer sqlOutletType = new StringBuffer
				("SELECT UNS_Outlet_Type_ID FROM C_BPartner WHERE C_BPartner_ID = " + inv.getC_BPartner_ID());
		int idOutletType = DB.getSQLValue(get_TrxName(), sqlOutletType.toString());
		
		//getPointSchema
		StringBuffer sqlPointSchema = new StringBuffer
				("SELECT UNS_PointSchema_ID FROM UNS_PointSchema WHERE DocStatus = 'CO'"
						+ " AND AD_Org_ID = " + inv.getAD_Org_ID()
						+ " OR (PointType = " + type
						+ " OR PointType = " + typeAll + ")"
						+ " OR AD_Org_ID = 0"
						+ " OR C_BPartner_ID = " + inv.getC_BPartner_ID()
						+ " OR C_BP_Group_ID = " + idGroupBP
						+ " OR UNS_Outlet_Type_ID = " + idOutletType
						+ " ORDER BY C_BPartner_ID");
		idPointSchema = DB.getSQLValue(get_TrxName(), sqlPointSchema.toString());
		
		if (idPointSchema <= -1)
			throw new AdempiereException("Not Found Point Schema..!!");
		
		return null;
	}
	
	public String calculatePoint(int UNS_PointSchema_ID) throws Exception
	{
		MInvoice inv = new MInvoice(getCtx(), getRecord_ID(), get_TrxName());
		StringBuffer sqlProduct = new StringBuffer
				("SELECT array_to_string (array_agg(C_InvoiceLine_ID), ';') FROM C_InvoiceLine"
						+ " WHERE C_Invoice_ID = " + inv.getC_Invoice_ID()
						+ " AND (M_Product_ID IN (SELECT M_Product_ID"
						+ " FROM UNS_PS_Product WHERE UNS_PointSchema_ID = " + UNS_PointSchema_ID + ")"
						+ " OR M_Product_ID IN (SELECT M_Product_ID FROM M_Product"
						+ " WHERE M_Product_Category_ID IN (SELECT M_Product_Category_ID FROM UNS_PS_Product"
						+ " WHERE UNS_PointSchema_ID = "+ UNS_PointSchema_ID +")))");
		
		String value = DB.getSQLValueString(get_TrxName(), sqlProduct.toString());
		
		if (value == null)
			throw new AdempiereException("Not Product Same With Product Point Schema..!!");
		
		values = value.split(";");
		
		for (String idString : values)
		{
			Integer idAsInt = new Integer(idString);
			MInvoiceLine invLine = new MInvoiceLine(getCtx(), idAsInt, get_TrxName());
			
			StringBuffer sqlPSProduct = new StringBuffer
					("SELECT UNS_PS_Product_ID FROM UNS_PS_Product"
							+ " WHERE M_Product_ID ="
							+ " (SELECT M_Product_ID FROM C_InvoiceLine"
							+ " WHERE C_InvoiceLine_ID = " + idAsInt + ")"
							+ " OR M_Product_Category_ID = (SELECT M_Product_Category_ID FROM M_Product"
							+ " WHERE M_Product_ID = (SELECT M_Product_ID FROM C_InvoiceLine"
							+ " WHERE C_InvoiceLine_ID = " + idAsInt + "))");
			int PSProduct = DB.getSQLValue(get_TrxName(), sqlPSProduct.toString());			
			
			StringBuffer sqlLine = new StringBuffer
					("SELECT array_to_string(array_agg(UNS_PointSchema_Line_ID),';') FROM"
							+ " (SELECT UNS_PointSchema_Line_ID FROM UNS_PointSchema_Line WHERE"
							+ " UNS_PS_Product_ID = " + PSProduct + " ORDER BY Qty DESC) A");
			String Qty = DB.getSQLValueString(get_TrxName(), sqlLine.toString());
			Qtys = Qty.split(";");
			
			BigDecimal point = Env.ZERO;
			BigDecimal Quantity = Env.ZERO;
			BigDecimal get_point = Env.ZERO;
			Quantity = invLine.getQtyEntered();
			
			for (String qtyString : Qtys)
			{
				Integer idInt = new Integer(qtyString);
				MUNSPointSchemaLine pointLine = new MUNSPointSchemaLine(getCtx(), idInt, get_TrxName());
								
				if(Quantity.compareTo(pointLine.getQty()) == 1)
				{
					point = Quantity.divide(pointLine.getQty(), 0, BigDecimal.ROUND_FLOOR);
					get_point = get_point.add((point.multiply(pointLine.getValuePoint())));
					Quantity = Quantity.subtract(point.multiply(pointLine.getQty()));
				}
				
				if(Quantity.compareTo(pointLine.getQty()) == 0)
				{
					get_point = get_point.add((point.multiply(pointLine.getValuePoint())));
					break;
				}
				
				if(Quantity.compareTo(pointLine.getQty()) == -1)
				{
					break;
				}
			}
			
			StringBuffer sqlCustomerPoint = new StringBuffer
					("SELECT UNS_CustomerPoint_ID FROM UNS_CustomerPoint"
							+ " WHERE C_BPartner_ID = (SELECT C_BPartner_ID FROM C_Invoice"
							+ " WHERE C_Invoice_ID = " + inv.getC_Invoice_ID() + ")");
			int idCustomerPoint = DB.getSQLValue(get_TrxName(), sqlCustomerPoint.toString());
			
			if (idCustomerPoint <= -1)
			{
				MUNSCustomerPoint newCustomer = new MUNSCustomerPoint(getCtx(), 0, get_TrxName());
				newCustomer.setC_BPartner_ID(inv.getC_BPartner_ID());
				newCustomer.setStartTrxDate(inv.getDateInvoiced());
				newCustomer.setLatestTrxDate(inv.getDateInvoiced());
				//newCustomer.setAccumulatedLine(get_point);
				newCustomer.setCurrentPoint(get_point);
				newCustomer.setIsSOTrx(inv.isSOTrx());
				newCustomer.saveEx();
			}
			
			StringBuffer sql = new StringBuffer
					("SELECT UNS_CustomerPoint_ID FROM UNS_CustomerPoint"
							+ " WHERE C_BPartner_ID = (SELECT C_BPartner_ID FROM C_Invoice"
							+ " WHERE C_Invoice_ID = " + inv.getC_Invoice_ID() + ")");
			int idCP = DB.getSQLValue(get_TrxName(), sql.toString());
			
			if (idCustomerPoint >= 1)
			{
				MUNSCustomerPoint customerPoint = new MUNSCustomerPoint(getCtx(), idCP, get_TrxName());
				customerPoint.setLatestTrxDate(inv.getDateInvoiced());
				//customerPoint.setAccumulatedLine(customerPoint.getAccumulatedLine().add(get_point));
				//customerPoint.setCurrentPoint(customerPoint.getCurrentPoint().add(get_point));
				customerPoint.saveEx();
			}
			
			MUNSCustomerPointLine newCSLine = new MUNSCustomerPointLine(getCtx(), 0, get_TrxName());
			newCSLine.setUNS_CustomerPoint_ID(idCP);
			newCSLine.setUNS_PointSchema_ID(idPointSchema);
			newCSLine.setPoint(get_point);
			newCSLine.setM_Product_ID(invLine.getM_Product_ID());
			newCSLine.setQty(invLine.getQtyEntered());
			newCSLine.setDateInvoiced(inv.getDateInvoiced());
			newCSLine.setC_InvoiceLine_ID(invLine.getC_InvoiceLine_ID());
			newCSLine.saveEx();
			
			inv.setCalculateDiscount("Y");
			inv.saveEx();
		}
		return null;
	}
}
