/**
 * 
 */
package com.uns.model.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSPackingSlip;
import com.uns.model.MUNSPackingSlipSupplier;

/**
 * @author menjangan
 *
 */
public class PackingSlipGenerateReceipt extends SvrProcess {
	
	private int m_RecordID = 0;
	private boolean m_isGroupBySupplier = false;

	/**
	 * 
	 */
	public PackingSlipGenerateReceipt() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] param = getParameter();
		for (int i = 0; i < param.length; i++)
		{
			String paramName = param[i].getParameterName();
			if (paramName.equals("GroupBySupplier"))
				m_isGroupBySupplier = param[i].getParameter_ToAsBoolean();
		}
		
		m_RecordID = getRecord_ID();

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		String msg= "";
		if (m_RecordID <= 0)
			throw new IllegalArgumentException("No PackingSlip");
		
		MUNSPackingSlip PackingSLip =
				new MUNSPackingSlip(getCtx(), m_RecordID, get_TrxName());
		msg = PackingSLip.generateReceipt(m_isGroupBySupplier);
		if (msg != "")
		{
			throw new IllegalArgumentException(msg);
		}
		PackingSLip.setGenerateTo("Y");
		PackingSLip.save();
		//Direct modification by ITD-Andy 29/07/13
		for (MUNSPackingSlipSupplier pss : PackingSLip.getLines()){
			msg = msg + pss.getM_InOut().getDocumentNo() + "; ";
		}			
		return "Material Receipt "+ msg +"has been created";
	}

}
