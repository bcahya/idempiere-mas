/**
 * 
 */
package com.uns.model.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSPackingSlip;
import com.uns.model.MUNSPackingSlipSupplier;

/**
 * @author menjangan
 *
 */
public class PackingSlipCreateLines extends SvrProcess {

	private int m_RecorID = 0;
	/**
	 * 
	 */
	public PackingSlipCreateLines() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		m_RecorID = getRecord_ID();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		String msg = null;
		MUNSPackingSlip pSlip = new MUNSPackingSlip(getCtx(),
				m_RecorID, get_TrxName());
		MUNSPackingSlipSupplier[] psSuplier = pSlip.getLines();
		if (psSuplier.length == 0)
			return("Cannot create lines, you don't have suplier. " +
					"User must create packing slip supplier before create lines.");
		
		for (int i=0; i< psSuplier.length; i++)
		{
			MUNSPackingSlipSupplier packingSupplier = psSuplier[i];
			msg = packingSupplier.createLines();
			if (!packingSupplier.save())
				throw new AdempiereException("Failed when updating Supplier Line.");
			if (msg != null)
				return msg;
		}
		pSlip.setCreateFrom("Y");
		pSlip.save();
		return msg;
	}

}
