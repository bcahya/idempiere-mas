/**
 * 
 */
package com.uns.model.process;

import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereSystemError;

import com.uns.base.model.MOrder;
import com.uns.base.model.MOrderLine;
import com.uns.model.MUNSSOAmendment;
import com.uns.model.MUNSSOAmendmentLine;

/**
 * @author menjangan
 *
 */
public class SO_AmendmentCreateLines extends SvrProcess {

	/**
	 * 
	 */
	public SO_AmendmentCreateLines() {
		// TODO Auto-generated constructor stub
	}
	
	private MUNSSOAmendment m_SOAmendment = null;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		m_SOAmendment = new MUNSSOAmendment(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		MOrder order = new MOrder(getCtx(), m_SOAmendment.getC_Order_ID(), get_TrxName());
		if(!m_SOAmendment.deleteLines())
			throw new AdempiereSystemError("Failed to delete old lines");
		for (MOrderLine oLine : order.getLines())
		{
			MUNSSOAmendmentLine soAmendmentLine = new MUNSSOAmendmentLine(m_SOAmendment);
			soAmendmentLine.setC_OrderLine_ID(oLine.get_ID());
			soAmendmentLine.setC_UOM_ID(oLine.getC_UOM_ID());
			soAmendmentLine.setDescription("AUTO Generate");
			soAmendmentLine.setC_OrderLine_ID(oLine.getC_OrderLine_ID());
			soAmendmentLine.setM_Product_ID(oLine.getM_Product_ID());
			soAmendmentLine.setPrevQuantity(oLine.getQtyOrdered());
			soAmendmentLine.save();
		}
		return null;
	}

}
