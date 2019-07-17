/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.uns.base.model.Query;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MOrder;
import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author UNTA_Andy
 * 
 */
public class MUNSPackingListOrder extends X_UNS_PackingList_Order
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -119953123735538698L;
	private MUNSPackingListLine[] m_lines;

	/**
	 * @param ctx
	 * @param UNS_PackingList_Order_ID
	 * @param trxName
	 */
	public MUNSPackingListOrder(Properties ctx, int UNS_PackingList_Order_ID, String trxName)
	{
		super(ctx, UNS_PackingList_Order_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPackingListOrder(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSPackingListOrder(MOrder order) {
        this(order.getCtx(), 0, order.get_TrxName());
        
        setClientOrg(order);
        
        setC_Order_ID(order.get_ID());
        setSalesRep_ID(order.getSalesRep_ID());
        setM_Warehouse_ID(order.getM_Warehouse_ID());
        
        MBPartnerLocation bpLocation = new MBPartnerLocation(getCtx(), order.getC_BPartner_Location_ID(), get_TrxName());
        setUNS_Rayon_ID(bpLocation.getUNS_Rayon_ID());
    }

    /**************************************************************************
	 * Get Orders
	 * 
	 * @param whereClause where clause or null (starting with AND)
	 * @param orderClause order clause
	 * @return orders
	 */
	public MUNSPackingListLine[] getLines(String whereClause, String orderClause)
	{
		// red1 - using new Query class from Teo / Victor's MDDOrder.java implementation
		StringBuilder whereClauseFinal =
				new StringBuilder(MUNSPackingListLine.COLUMNNAME_UNS_PackingList_Order_ID + "=? ");
		if (!Util.isEmpty(whereClause, true))
			whereClauseFinal.append(whereClause);
		if (orderClause.length() == 0)
			orderClause = MUNSPackingListLine.COLUMNNAME_UNS_PackingList_Line_ID;
		//
		List<MUNSPackingListLine> list =
				Query
						.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSPackingListLine.Table_Name,
								whereClauseFinal.toString(), get_TrxName()).setParameters(get_ID())
						.setOrderBy(orderClause).list();

		return list.toArray(new MUNSPackingListLine[list.size()]);
	} // getLines

	/**
	 * Get Lines of Order
	 * 
	 * @param requery requery
	 * @param orderBy optional order by column
	 * @return lines
	 */
	public MUNSPackingListLine[] getLines(boolean requery, String orderBy)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		String orderClause = "";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;

		m_lines = getLines(null, orderClause);
		return m_lines;
	} // getLines

	/**
	 * Get Lines of Order. (used by web store)
	 * 
	 * @return lines
	 */
	public MUNSPackingListLine[] getLines()
	{
		return getLines(false, null);
	} // getLines

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getC_Invoice_ID() == 0 && getC_Order_ID() == 0)
		{
			throw new AdempiereUserError("Please define Order or invoice.");
		}
		boolean validOrg = false;
		if(getC_Invoice_ID() > 0)
		{
			validOrg = getC_Invoice().getAD_Org_ID() == getAD_Org_ID();
		}
		else if(getC_Order_ID() > 0)
		{
			validOrg = getC_Order().getAD_Org_ID() == getAD_Org_ID();
		}

		if(!validOrg && getAD_Org_ID() != getM_Warehouse().getAD_Org_ID())
		{
			throw new AdempiereException("Not valid Organization");
		}
		
		boolean isChanged = false;
		
		if(is_ValueChanged(COLUMNNAME_C_Order_ID) && getC_Order_ID() > 0)
		{
			I_C_Order order = getC_Order();
			String docSubTypeSO = order.getC_DocTypeTarget().getDocSubTypeSO();
			if (docSubTypeSO.equals(MDocType.DOCSUBTYPESO_CashOrder)
					|| docSubTypeSO.equals(MDocType.DOCSUBTYPESO_PrepayOrder))
			{
				int C_Invoice_ID = DB.getSQLValue(get_TrxName(),
						"SELECT C_Invoice_ID FROM C_Invoice WHERE C_Order_ID = ? AND DocStatus IN (?,?)"
						, getC_Order_ID(), "CO", "CL");

				setC_Invoice_ID(C_Invoice_ID);
				isChanged = true;
			}
		}
		else if(is_ValueChanged(COLUMNNAME_C_Invoice_ID) && getC_Invoice_ID() > 0)
		{
			int C_Order_ID = DB.getSQLValue(
					get_TrxName(), "SELECT C_Order_ID FROM C_Invoice WHERE C_Invoice_ID = ?", getC_Invoice_ID());
			setC_Order_ID(C_Order_ID);
			isChanged = true;
		}
		
		if(isChanged)
		{
			analyzeLine();
		}
				
		return super.beforeSave(newRecord);
	}
	
	protected void analyzeLine()
	{
		getLines(true, null);
		for(int i=0; i<m_lines.length; i++)
		{
			int C_OrderLine_ID = m_lines[i].getC_OrderLine_ID();
			int C_InvoiceLine_ID = m_lines[i].getC_InvoiceLine_ID();
			if(C_OrderLine_ID > 0)
			{
				int C_Order_ID = DB.getSQLValue(
						get_TrxName(), "SELECT C_Order_ID FROM C_OrderLine WHERE C_OrderLine_ID = ?"
						, C_OrderLine_ID);
				if(C_Order_ID != getC_Order_ID())
				{
					m_lines[i].deleteEx(true);
				}
			}
			if(C_InvoiceLine_ID > 0)
			{
				int C_Invoice_ID = DB.getSQLValue(
						get_TrxName(), "SELECT C_Invoice_ID FROM C_InvoiceLine WHERE C_InvoiceLine_ID = ?"
						, C_InvoiceLine_ID);
				if(C_Invoice_ID != getC_Invoice_ID())
				{
					m_lines[i].deleteEx(true);
				}
			}
		}
	}
	
	private int m_invoice_id = 0;
	private int m_shipment_id = 0;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeDelete()
	 */
	@Override
	protected boolean beforeDelete()
	{
		if(getC_Invoice_ID() > 0)
			m_invoice_id = getC_Invoice_ID();
		if(getM_InOut_ID() > 0)
			m_shipment_id = getM_InOut_ID();
		
		for (MUNSPackingListLine line : getLines())
		{
			if (!line.delete(true))
				return false;
		}

		return super.beforeDelete();
	}

	private final BigDecimal EXMIN3 = new BigDecimal(0.001);
	
	@Override
	protected boolean afterDelete(boolean success)
	{
		if(m_invoice_id > 0)
		{
			org.compiere.model.MInvoice inv = MInvoice.get(getCtx(), m_invoice_id);
			if(!inv.isProcessed())
				inv.delete(false, get_TrxName());
		}
		if(m_shipment_id > 0)
		{
			MInOut io = new MInOut(getCtx(), m_shipment_id, get_TrxName());
			if(!io.isProcessed())
				io.delete(false, get_TrxName());
		}
		
		return true;
	}

	MUNSPackingList m_parent = null;

	@Override
	public MUNSPackingList getParent()
	{
		if (m_parent == null)
			m_parent = (MUNSPackingList) getUNS_PackingList();

		return m_parent;
	}

	public void initOrder(MUNSPackingList parent)
	{
		setClientOrg(parent);

		setUNS_PackingList_ID(parent.get_ID());
		m_parent = parent;
	}

	public void setUpdateParent()
	{
		BigDecimal tonase = Env.ZERO;
		BigDecimal volume = Env.ZERO;
		for (MUNSPackingListLine line : getLines())
		{
			tonase =
					tonase.add(line.getM_Product().getGrossWeight().multiply(line.getQtyEntered())
							.multiply(EXMIN3));
			volume =
					volume.add(line.getM_Product().getVolume().multiply(line.getQtyEntered())
							.multiply(line.getQtyEntered()).multiply(EXMIN3).multiply(EXMIN3));
		}

		StringBuilder sb = new StringBuilder("UPDATE ").append(MUNSPackingListOrder.Table_Name).append(" SET ")
				.append(MUNSPackingListOrder.COLUMNNAME_Tonase).append("=").append(tonase).append(", ")
				.append(MUNSPackingListOrder.COLUMNNAME_Volume).append("=").append(volume).append(" WHERE ")
				.append(MUNSPackingListOrder.COLUMNNAME_UNS_PackingList_Order_ID).append("=").append(get_ID());
		
		if(DB.executeUpdate(sb.toString(), get_TrxName())== -1)
			throw new AdempiereException("Error when update parent.");
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#afterSave(boolean, boolean)
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		getParent().setUpdateParent();
		
		if (!newRecord && is_ValueChanged(COLUMNNAME_M_Warehouse_ID)
				&& getM_InOut_ID() > 0)
		{
			MInOut io = new MInOut(getCtx(), getM_InOut_ID(), get_TrxName());
			
			io.setM_Warehouse_ID(this.getM_Warehouse_ID());
			io.saveEx();
		}

		return super.afterSave(newRecord, success);
	}
	
	
	public static MUNSPackingListOrder getByInvoice (Properties ctx, int C_Invoice_ID, String trxName)
	{
		MUNSPackingListOrder plOrder = Query.get(ctx, UNSOrderModelFactory.EXTENSION_ID, Table_Name,
				COLUMNNAME_C_Invoice_ID + "=?", trxName).setParameters(C_Invoice_ID)
					.first();
		return plOrder;
	}
}
