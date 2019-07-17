/**
 * 
 */
package com.unicore.model.process;

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;

import com.unicore.base.model.MInvoice;
import com.unicore.model.I_DiscountModel;
import com.unicore.model.UNSInvoiceDiscountModel;

/**
 * @author root
 *
 */
public class InvoiceCalculateDiscount extends SvrProcess implements Runnable{

	
	private UNSInvoiceDiscountModel m_model = null;
	private boolean m_justUpdate = false;
	
	/**
	 * 
	 */
	public String get_TrxName()
	{
		return super.get_TrxName() == null ? m_model.get_TrxName() : super.get_TrxName();
	}
	
	/**
	 * 
	 */
	public Properties getCtx()
	{
		return super.getCtx() == null ? m_model.getCtx() : super.getCtx();
	}

	/**
	 * 
	 */
	public InvoiceCalculateDiscount() {
		super();
	}
	
	public InvoiceCalculateDiscount(I_DiscountModel model)
	{
		m_model = (UNSInvoiceDiscountModel) model;
		m_model.initialRequirements();
	}
	
	public void run()
	{
		try {
			doIt();
		} catch (Exception e) {
			throw new AdempiereException( e.getMessage());
		}
	}
	

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		MInvoice model = new MInvoice(getCtx(), getRecord_ID(), get_TrxName());
		model.setProcessInfo(getProcessInfo());
		m_model = new UNSInvoiceDiscountModel(model);
		m_model.initialRequirements();
//		m_justUpdate = getParameter()[0].getParameterAsBoolean();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		CalculateDiscount calc = new CalculateDiscount(m_model, true, 0);
		calc.setIsUpdate(m_justUpdate);
		calc.prepare();
		return calc.doIt();
	}
	
	public void setJustUpdate(boolean isJustUpdate)
	{
		m_justUpdate = isJustUpdate;
	}

}
