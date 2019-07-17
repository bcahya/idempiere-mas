/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrderLine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.MProduct;

import com.unicore.base.model.MInOutLine;


/**
 * @author UNTA_Andy
 * 
 */
public class MUNSPackingListLine extends X_UNS_PackingList_Line
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5120654599454101151L;
	
	private boolean m_isReversal = false;

	/**
	 * @param ctx
	 * @param UNS_PackingList_Line_ID
	 * @param trxName
	 */
	public MUNSPackingListLine(Properties ctx, int UNS_PackingList_Line_ID, String trxName)
	{
		super(ctx, UNS_PackingList_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPackingListLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSPackingListLine(com.unicore.base.model.MOrderLine oline) {
        this(oline.getCtx(), 0, oline.get_TrxName());
        
        setClientOrg(oline);
        setC_OrderLine_ID(oline.get_ID());
        setM_Product_ID(oline.getM_Product_ID());
        setC_UOM_ID(oline.getC_UOM_ID());
        setQty(oline.getQtyOrdered().subtract(oline.getQtyDelivered()));        
    }

    /**
	 * 
	 * @param parent
	 */
	public void initline(MUNSPackingListOrder parent)
	{
		setClientOrg(parent);
		setUNS_PackingList_Order_ID(parent.get_ID());

		m_parent = parent;
	}

	MUNSPackingListOrder m_parent = null;
	private boolean m_automatic = false;

	public MUNSPackingListOrder getParent()
	{
		if (m_parent == null)
			m_parent = (MUNSPackingListOrder) getUNS_PackingList_Order();
		
		return m_parent;
	}
	
	public boolean isReversal() {
		return m_isReversal;
	}
	
	public void setIsReversal(boolean reversal) {
		m_isReversal = reversal;
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
		
		Query query = 
				new Query(getCtx(), MOrderLine.Table_Name, "C_OrderLine_ID=" + getC_OrderLine_ID(), get_TrxName());
		query.setForUpdate(true);
		
		MOrderLine ol = query.first();
		BigDecimal qtyPacked = ol.getQtyPacked();
		BigDecimal oldShipped = (BigDecimal) get_ValueOld(COLUMNNAME_QtyShipping);
		
		if (newRecord) 
		{
			qtyPacked = qtyPacked.add(getQtyShipping());
		} 
		else if(is_ValueChanged(COLUMNNAME_QtyShipping))
		{
			qtyPacked = qtyPacked.subtract(oldShipped);
			qtyPacked = qtyPacked.add(getQtyShipping());
		}

		if(qtyPacked.compareTo(ol.getQtyPacked()) != 0)
		{
			ol.setQtyPacked(qtyPacked);
			ol.saveEx();
		}
		
		return super.afterSave(newRecord, success);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#afterSave(boolean, boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(newRecord)
		{
			String duplicateLine = "SELECT 1 FROM UNS_PackingList_Line WHERE UNS_PackingList_Order_ID = " + getUNS_PackingList_Order_ID()
					+ " AND C_OrderLine_ID = " + getC_OrderLine_ID();
			
			if(DB.getSQLValue(get_TrxName(), duplicateLine) >= 1)
				throw new AdempiereUserError("Duplicate Line.");
			
			setQtyShipping(getQtyEntered());
		}
		
		if(getC_OrderLine_ID() == 0 && getC_InvoiceLine_ID() == 0)
		{
			throw new AdempiereUserError("Please define Order Line or Invoice Line.");
		}
		
//		if(!isAutomatic() && getQtyEntered().compareTo(Env.ZERO) <= 0)
//		{
//			log.saveError("Error", "Please update quantity or remove it if not used");
//			return false;
//		}
		if (isAutomatic())
			return super.beforeSave(newRecord);
		
		if (!newRecord && is_ValueChanged(COLUMNNAME_QtyEntered))
		{
			MProduct product = MProduct.get(getCtx(), getM_Product_ID());
			I_C_OrderLine orderLine = getC_OrderLine();
			BigDecimal qtyPacked = orderLine.getQtyPacked();
			
			BigDecimal oldEntered = (BigDecimal) get_ValueOld(COLUMNNAME_QtyEntered);
			
			qtyPacked = qtyPacked.subtract(oldEntered);
			
			BigDecimal qtyAvailable = orderLine.getQtyOrdered().subtract(qtyPacked);
			BigDecimal qtyLowestBase = product.getConversionQtyLowestToBase();
			BigDecimal qtyDiff = qtyAvailable.multiply(qtyLowestBase);

			if(qtyDiff.compareTo(Env.ONE) >= 0 && qtyDiff.compareTo(Env.ONE.negate()) <= 0){
//			if (qtyAvailable.compareTo(getQtyEntered()) < 0) {
				throw new AdempiereUserError(
						"Quantity entered (" + getQtyEntered() + ") is greater than available quantity to pack (" 
						+ qtyAvailable + ")");
			}
			setQtyShipping(getQtyEntered());
			
			if(getM_InOutLine_ID() > 0)
			{
				MInOutLine ioLine = new MInOutLine(getCtx(), getM_InOutLine_ID(), get_TrxName());
				if(!ioLine.getParent().getDocStatus().equals("VO")
						&& !ioLine.getParent().getDocStatus().equals("RE"))
				{
					if(!ioLine.isProcessed())
						ioLine.setQty(getQtyEntered());
					if(ioLine.isProcessed() || !ioLine.save())
					{
						log.saveError("Error", "Failed update quantity shipment line. "
											+ CLogger.retrieveErrorString("Processed"));
						return false;
					}
				}
			}
			
			if(getC_InvoiceLine_ID() > 0)
			{
				MInvoiceLine ivLine = new MInvoiceLine(getCtx(), getC_InvoiceLine_ID(), get_TrxName());
				if(!ivLine.getParent().getDocStatus().equals("VO")
						&& !ivLine.getParent().getDocStatus().equals("RE"))
				{
					if(!ivLine.isProcessed())
					{
						ivLine.setQty(getQtyEntered());
						if(!ivLine.save())
						{
							log.saveError("Error", "Failed update quantity invoice line. "
										+ CLogger.retrieveErrorString("Processed"));
							return false;	
						}
					}
				}
			}
		}
		
		if(is_ValueChanged(COLUMNNAME_C_OrderLine_ID) && getC_OrderLine_ID() > 0)
		{
			String sql = "SELECT C_InvoiceLine_ID FROM C_InvoiceLine WHERE C_OrderLine_ID = ? AND C_Invoice_ID = ?";
			int C_InvoiceLine_ID = DB.getSQLValue(get_TrxName(), sql, getC_OrderLine_ID(), getParent().getC_Invoice_ID());
			setC_InvoiceLine_ID(C_InvoiceLine_ID);
		}
		else if(is_ValueChanged(COLUMNNAME_C_InvoiceLine_ID) && getC_InvoiceLine_ID() > 0)
		{
			String sql = "SELECT C_OrderLine_ID FROM C_InvoiceLine WHERE C_InvoiceLine_ID = ?";
			int C_OrderLine_ID = DB.getSQLValue(get_TrxName(), sql, getC_InvoiceLine_ID());
			setC_OrderLine_ID(C_OrderLine_ID);
		}

		return super.beforeSave(newRecord);
	}

	private int m_invoiceline_id = 0;
	private int m_inoutline_id = 0;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#afterDelete(boolean)
	 */
	@Override
	protected boolean afterDelete(boolean success)
	{
		if (!success)
			return success;
		
		Query query = 
				new Query(getCtx(), MOrderLine.Table_Name, "C_OrderLine_ID=" + getC_OrderLine_ID(), get_TrxName());
		query.setForUpdate(true);
		
		MOrderLine ol = query.first();
		
		ol.setQtyPacked(ol.getQtyPacked().subtract(getQtyEntered()));
		ol.saveEx();
		
		if(m_invoiceline_id > 0)
		{
			MInvoiceLine invLine = new MInvoiceLine(getCtx(), m_invoiceline_id, get_TrxName());
			if(!invLine.isProcessed())
				invLine.delete(false, get_TrxName());
		}
		if(m_inoutline_id > 0)
		{
			MInOutLine ioLine = new MInOutLine(getCtx(), m_inoutline_id, get_TrxName());
			if(!ioLine.isProcessed())
			{
				String sql = "UPDATE C_InvoiceLine SET M_InOutLine_ID = NULL WHERE M_InOutLine_ID = ?";
				DB.executeUpdate(sql, m_inoutline_id, false, get_TrxName());
				ioLine.delete(false, get_TrxName());
			}
		}
 
		return true;
	}
	
	@Override
	protected boolean beforeDelete()
	{
		if(getC_InvoiceLine_ID() > 0)
			m_invoiceline_id = getC_InvoiceLine_ID();
		if(getM_InOutLine_ID() > 0)
			m_inoutline_id = getM_InOutLine_ID();
		
		return true;
	}
	
	public boolean isAutomatic()
	{
		return m_automatic ;
	}
	
	public void setAutomatic(boolean automatic)
	{
		this.m_automatic = automatic;
	}
	
	public void setQty(BigDecimal qty){
	    setTargetQty(qty);
	    setQtyEntered(qty);
	    setMovementQty(qty);
	}
	
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}
}
