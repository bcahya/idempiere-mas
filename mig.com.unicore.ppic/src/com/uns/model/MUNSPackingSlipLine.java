/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.PO;

/**
 * @author menjangan
 *
 */
public class MUNSPackingSlipLine extends X_UNS_PackingSlipLine 
{
	
	MUNSPackingSlipSupplier m_parent = null;

	private MUNSPackingSlip m_grandParent = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_PackingSlipLine_ID
	 * @param trxName
	 */
	public MUNSPackingSlipLine(Properties ctx, int UNS_PackingSlipLine_ID,
			String trxName) {
		super(ctx, UNS_PackingSlipLine_ID, trxName);
	}
	
	/**
	 * get parent MUNSPackingSlipSupplier
	 * @return
	 */
	public PO getParent()
	{
		if(m_parent == null)
			m_parent = new MUNSPackingSlipSupplier(getCtx(),
					getUNS_PackingSlipSupplier_ID(),
					get_TrxName());
		
		return m_parent;
	}
	
	/**
	 * get parent MUNSPackingSlipSupplier
	 * @return
	 */
	MUNSPackingSlip getGrandParent()
	{
		if(m_grandParent == null)
			
			m_grandParent = new MUNSPackingSlip(getCtx(),
					m_parent.getUNS_PackingSlip_ID(),
					get_TrxName());
		
		return m_grandParent;
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPackingSlipLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean sucess) 
	{
		MAttributeSetInstance.resetDescription(getCtx(), getM_AttributeSetInstance_ID(), get_TrxName());
		return true;
	}


	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		MAttributeSetInstance.initAttributeValuesFrom(
				this, COLUMNNAME_M_Product_ID, COLUMNNAME_M_AttributeSetInstance_ID, get_TrxName());
//		setM_AttributeSetInstance_ID(AttributeSetInstance.CreateASI(getCtx(), get_TrxName(), 
//				getM_Product_ID(), m_grandParent.getBCNo(), getHSCode(), getCOA()).get_ID());

		return super.beforeSave(newRecord);
	}

	// Add by ITD-Andy 29/07/13
	public static MUNSPackingSlipLine getNew(
			Properties ctx, String trxName,
			MInvoiceLine iLine, MUNSPackingSlipSupplier pss)
	{
		MUNSPackingSlipLine[] pslines = pss.getLines();
		int psl_id = 0;
		if (pslines.length!=0)
			for (MUNSPackingSlipLine psl : pss.getLines())
			{
				if (psl.getC_InvoiceLine_ID()==iLine.get_ID() 
					&& psl.getC_OrderLine_ID()==iLine.getC_OrderLine_ID()
					&& psl.getM_Product_ID()==iLine.getM_Product_ID())
					psl_id = psl.get_ID();
			}
			
		return new MUNSPackingSlipLine(ctx, psl_id, trxName);
	}
}
