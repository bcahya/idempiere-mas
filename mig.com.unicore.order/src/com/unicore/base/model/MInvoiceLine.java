/**
 * 
 */
package com.unicore.base.model;

import java.sql.ResultSet;
import java.util.Properties;


/**
 * @author UNTA-Andy
 * 
 */
public class MInvoiceLine extends org.compiere.model.MInvoiceLine
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7530443759523381705L;
	private MInvoice m_parent;

	/**
	 * @param ctx
	 * @param C_InvoiceLine_ID
	 * @param trxName
	 */
	public MInvoiceLine(Properties ctx, int C_InvoiceLine_ID, String trxName)
	{
		super(ctx, C_InvoiceLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param invoice
	 */
	public MInvoiceLine(MInvoice invoice)
	{
		super(invoice);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoiceLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.MInvoiceLine#getParent()
	 */
	@Override
	public MInvoice getParent()
	{
		if (m_parent == null)
			m_parent = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
		return m_parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.MInvoiceLine#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (newRecord)
		{
			if (getC_OrderLine_ID() != 0)
			{
				MInvoiceLine[] lines = getParent().getLines(true);
				MOrderLine oLine = new MOrderLine(getCtx(), getC_OrderLine_ID(), get_TrxName());
				setQtyBonuses(oLine.getQtyBonuses());
				if (oLine.getRef_OrderLine_ID() > 0 && lines.length > 0)
				{
					for (MInvoiceLine line : lines)
					{
						if (line.getC_OrderLine_ID() == oLine.getRef_OrderLine_ID())
						{
							setRef_InvoiceLine_ID(line.get_ID());
							line.save();
						}
					}
				}
			}
		}
	
//		setQtyInvoiced(getQtyEntered().add(getQtyBonuses()));
		
		return super.beforeSave(newRecord);
	}
	
}
