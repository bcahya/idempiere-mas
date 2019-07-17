/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.unicore.model.MUNSPackingList;
import com.unicore.model.MUNSPackingListLine;
import com.unicore.model.MUNSPackingListOrder;

/**
 * @author ALBURHANY
 *
 */
public class CreatePLFromRayon extends SvrProcess {

	private int 			p_packinglist_id;
    private MUNSPackingList m_packinglist;
    private int 			m_Rayon_ID;
    private Timestamp 		m_dateFrom;
    private Timestamp		m_dateTo;
    private String			m_dateCriteria;
	    
	/**
	 * 
	 */
	public CreatePLFromRayon() {
	}

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++)
        {
            String name = para[i].getParameterName();
            if (name.equals("UNS_Rayon_ID"))
                m_Rayon_ID = para[i].getParameterAsInt();
            else if (name.equals("DateFrom"))
                m_dateFrom = para[i].getParameterAsTimestamp();
            else if (name.equals("DateTo"))
                m_dateTo = para[i].getParameterAsTimestamp();
            else if (name.equals("DateCriteria"))
                m_dateCriteria = para[i].getParameterAsString();
            else
            	log.log(Level.SEVERE, "Unknown Parameter: " + name);
        }
        
        p_packinglist_id = getRecord_ID();
        m_packinglist = new MUNSPackingList(getCtx(), p_packinglist_id, get_TrxName());
    }

	@Override
	protected String doIt() throws Exception {

		if(m_Rayon_ID == 0)
			throw new AdempiereException("Please define Rayon for generating Packing List");
		
//		String whereClause = " DocStatus='CO' AND C_Order_ID NOT IN (SELECT C_Order_ID FROM UNS_PackingList_Order)"
//				+ " AND C_BPartner_ID IN (SELECT C_BPartner_ID FROM C_BPartner_Location WHERE UNS_Rayon_ID = ?)";
		
		String whereClause = " DocStatus IN ('CO', 'CL') AND C_Order_ID IN ("
				+ " 	SELECT ol.C_Order_ID FROM C_OrderLine ol WHERE ol.QtyOrdered > ol.QtyPacked) "
				+ " AND C_BPartner_ID IN (SELECT C_BPartner_ID FROM C_BPartner_Location WHERE UNS_Rayon_ID = ?)"
				+ " AND ";
		
		if (m_dateCriteria != null && m_dateCriteria.equals("DO")) {
			whereClause += " DateOrdered BETWEEN ? AND ?";
		}
		else {
			whereClause += " DatePromised BETWEEN ? AND ?";
		}
		
		List<MOrder> order = new Query(getCtx(), MOrder.Table_Name, whereClause, get_TrxName()).
				setParameters(m_Rayon_ID, m_dateFrom, m_dateTo).list();
		
		for (MOrder orderList : order)
		{
			MUNSPackingListOrder listOrder = new MUNSPackingListOrder(getCtx(), 0, get_TrxName());
			
			listOrder.setC_Order_ID(orderList.get_ID());
			listOrder.setUNS_PackingList_ID(p_packinglist_id);
			listOrder.setAD_Org_ID(m_packinglist.getAD_Org_ID());
			listOrder.setM_Warehouse_ID(orderList.getM_Warehouse_ID());
			listOrder.setSalesRep_ID(orderList.getSalesRep_ID());
			listOrder.setUNS_Rayon_ID(m_Rayon_ID);
			listOrder.saveEx();
			
			String idOrderLine = " C_Order_ID = ?";
			List<MOrderLine> orderLine = new Query(getCtx(), MOrderLine.Table_Name, idOrderLine, get_TrxName()).
					setParameters(orderList.get_ID()).list();
			
			for (MOrderLine oLine : orderLine)
			{
				MUNSPackingListLine listLine = new MUNSPackingListLine(getCtx(), 0, get_TrxName());
				
				BigDecimal qtyOrdered = oLine.getQtyOrdered().subtract(oLine.getQtyPacked());
				
				listLine.setAD_Org_ID(oLine.getAD_Org_ID());
				listLine.setUNS_PackingList_Order_ID(listOrder.get_ID());
				listLine.setC_OrderLine_ID(oLine.get_ID());
				listLine.setM_Product_ID(oLine.getM_Product_ID());
				listLine.setC_UOM_ID(oLine.getC_UOM_ID());
				listLine.setQtyEntered(qtyOrdered);
				listLine.setTargetQty(qtyOrdered);
				listLine.setMovementQty(qtyOrdered);
				listLine.setUOMQtyEnteredL1(qtyOrdered);
				listLine.setUOMTargetQtyL1(qtyOrdered);
				listLine.setUOMMovementQtyL1(qtyOrdered);
				listLine.saveEx();
			}
		}
		return null;
	}
}
