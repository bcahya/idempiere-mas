/**
 * 
 */
package com.unicore.process;

import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MMovementConfirm;

/**
 * @author menjangan
 *
 */
public class ProcessInvoiceInventoryLost extends SvrProcess {

	private int m_movementConfirm_ID = 0;
	private int m_pricelist_id = 0;
	/**
	 * 
	 */
	public ProcessInvoiceInventoryLost() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] params = getParameter();
		for(ProcessInfoParameter param : params)
		{
			if(param.getParameterName() == null)
				;
			else if(param.getParameterName().equals(MMovementConfirm.COLUMNNAME_M_MovementConfirm_ID))
				m_movementConfirm_ID = param.getParameterAsInt();
			else if(param.getParameterName().equals(MInvoice.COLUMNNAME_M_PriceList_ID))
				m_pricelist_id = param.getParameterAsInt();	
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + param.getParameterName());
		}

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		if(m_movementConfirm_ID <= 0)
			return "Movement Confirmation is mandatory";
		if(m_pricelist_id <= 0)
			return "Price List is mandatory";
		MMovementConfirm confirm = new MMovementConfirm(getCtx(), m_movementConfirm_ID, get_TrxName());
		String msg = confirm.generateInventoryLostInvoice(m_pricelist_id);
		if("".equals(msg))
			msg = "Sucess :).";
		
		return msg;
	}

}
