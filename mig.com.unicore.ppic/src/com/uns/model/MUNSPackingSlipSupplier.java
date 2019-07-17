/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.PO;
import org.compiere.model.Query;

import com.uns.util.ErrorMsg;

/**
 * @author menjangan
 *
 */
public class MUNSPackingSlipSupplier extends X_UNS_PackingSlipSupplier {
	
	private MUNSPackingSlip m_parent = null;
	private MUNSPackingSlipLine[] m_Lines = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_PackingSlipSupplier_ID
	 * @param trxName
	 */
	public MUNSPackingSlipSupplier(Properties ctx,
			int UNS_PackingSlipSupplier_ID, String trxName) {
		super(ctx, UNS_PackingSlipSupplier_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPackingSlipSupplier(Properties ctx, ResultSet rs, 
			String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @return
	 */
	public PO getParent()
	{
		if (m_parent == null)
			m_parent = new MUNSPackingSlip(getCtx(),
					getUNS_PackingSlip_ID(), 
					get_TrxName());
		
		return m_parent;
	}
	
	@Override
	public boolean beforeSave(boolean newRecord)
	{
		return true;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	MUNSPackingSlipLine[] getLines(boolean requery)
	{
		if (m_Lines != null
				&& !requery)
		{
			set_TrxName(m_Lines,get_TrxName());
			return m_Lines;
		}
		String wClause = COLUMNNAME_UNS_PackingSlipSupplier_ID + "=?";
		List<MUNSPackingSlipLine> mList =
				new Query(getCtx(), MUNSPackingSlipLine.Table_Name, wClause, get_TrxName())
		.setParameters(getUNS_PackingSlipSupplier_ID())
		.setOrderBy(MUNSPackingSlipLine.COLUMNNAME_UNS_PackingSlipLine_ID)
		.list();
		m_Lines = new MUNSPackingSlipLine[mList.size()];
		mList.toArray(m_Lines);
		
		return m_Lines;
	}
	
	public MUNSPackingSlipLine[] getLines()
	{
		return getLines(false);
	}

	
	/**
	 * Create Lines From Invoice
	 * @return
	 */
	public String createLines()
	{
		String msg = null;
		MUNSPackingSlip packingSlip = (MUNSPackingSlip) getParent();
		/** Comment by ITD-Andy 29/07/13
		if (packingSlip.getCreateFrom().equals("Y")){
			throw new IllegalArgumentException("Line Has Been Created, Cant Create Again");
		}
		*/
		MInvoice invoice = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
		packingSlip.setDateOrdered(invoice.getDateOrdered());
		packingSlip.save();
		MInvoiceLine[] iLines = invoice.getLines();
		if (iLines == null || iLines.length == 0)
			return "Packing-Slip create lines failed. Can not find any invoice defined.";
		for (int i=0; i< iLines.length; i++)
		{
			MInvoiceLine iLine = iLines[i];
			MOrderLine oLine = new MOrderLine(getCtx(), 
					iLine.getC_OrderLine_ID(),get_TrxName());
			MProduct product = MProduct.get(getCtx(), iLine.getM_Product_ID());
			//Direct modification by ITD-Andy 29/07/13
			MUNSPackingSlipLine packingSL = MUNSPackingSlipLine.getNew(getCtx(),get_TrxName(), iLine, this);
			packingSL.setUNS_PackingSlipSupplier_ID(getUNS_PackingSlipSupplier_ID());
			packingSL.setQtyOrdered(oLine.getQtyOrdered());
			packingSL.setC_UOM_ID(iLine.getC_UOM_ID());
			packingSL.setQtyInvoiced(iLine.getQtyInvoiced());
			packingSL.setDescription(product.getName());
			//TODO PACKING SLIP
//			I_UNS_BCProductCategory bcProdCat = 
//					new X_UNS_BCProductCategory(getCtx(), product.getUNS_BCProductCategory_ID(), get_TrxName());
//			packingSL.setHSCode(bcProdCat.getHSCode());
//			if (product.getC_UOM_ID() == iLine.getC_UOM_ID())
//			{
//				//Direct modification by ITD-Andy 29/07/13
//				packingSL.setBrutoQuantity(product.getGrossWeight().multiply(packingSL.getQtyInvoiced()));
//				packingSL.setWeight(product.getWeight().multiply(packingSL.getQtyInvoiced()));
//				if (packingSL.getBrutoQuantity().intValue()==0 || packingSL.getWeight().intValue()==0)
//					packingSL.setDescription(packingSL.getDescription()+", Please make configure Net Weight and Gross Weight at Product."); 
//			}
//			else {
//				/** Comment by ITD-Andy 29/07/13
//				//BigDecimal QtyInvoice = MUOMConversion.convert(
//				//		iLine.getC_UOM_ID(), product.getC_UOM_ID(),
//				//		iLine.getQtyInvoiced(),true);
//				//BigDecimal GrossWeight = BigDecimal.ZERO;   //QtyInvoice.multiply(BigDecimal.ZERO);//belum di ganti
//				BigDecimal NettWeight = BigDecimal.ZERO;   //QtyInvoice.multiply(product.getWeight());
//				packingSL.setBrutoQuantity(BigDecimal.ZERO);
//				packingSL.setWeight(NettWeight);
//				*/
//				return "UOM at Invoice not equals with UOM product.";
//			}
			packingSL.setPMSMCode(product.getValue());
			packingSL.setC_InvoiceLine_ID(iLine.getC_InvoiceLine_ID());
			packingSL.setC_OrderLine_ID(iLine.getC_OrderLine_ID());
			packingSL.setM_Product_ID(iLine.getM_Product_ID());
			packingSL.setM_AttributeSetInstance_ID(iLine.getM_AttributeSetInstance_ID());
			if (!packingSL.save())
				msg = "Can't Create Line";
		}
		return msg;
	}
	
	/**
	 * 
	 * @return
	 */
	boolean deleteLines()
	{
		MUNSPackingSlipLine[] list = getLines();
		for (int i=0; i< list.length; i ++)
		{
			MUNSPackingSlipLine line = list[i];
			if (!line.delete(true))
				return false;
		}
		return true;
	}
	

	@Override
	public boolean beforeDelete()
	{
		if (!deleteLines())
		{
			ErrorMsg.setErrorMsg(getCtx(), "saveError", "Can't Delete Line");
			return false;
		}
		return true;
	}

}
