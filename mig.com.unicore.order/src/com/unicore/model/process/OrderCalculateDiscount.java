/**
 * 
 */
package com.unicore.model.process;

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;

import com.unicore.base.model.MOrder;
import com.unicore.model.I_DiscountModel;
import com.unicore.model.UNSOrderDiscountModel;

/**
 * @author root
 *
 */
public class OrderCalculateDiscount extends SvrProcess {
	
	private I_DiscountModel m_model = null;
	
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
	
	public OrderCalculateDiscount() {
		super();
	}
	
	public OrderCalculateDiscount(I_DiscountModel model)
	{
		m_model =  model;
		m_model.initialRequirements();
	}
	
	public void run()
	{
		try {
			doIt();
		} catch (Exception e) {
			throw new AdempiereException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		MOrder order = new MOrder(getCtx(), getRecord_ID(), get_TrxName());
		order.setProcessInfo(getProcessInfo());
		m_model = new UNSOrderDiscountModel(order);
		m_model.initialRequirements();
		m_justUpdate = getParameter()[0].getParameterAsBoolean();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		CalculateDiscount calc = new CalculateDiscount(m_model, false, getRecord_ID());
		calc.prepare();
		calc.setIsUpdate(m_justUpdate);
		return calc.doIt();
	}
	
	private boolean m_justUpdate = false;
	
	public void setIsJustUpdate(boolean isJustUpate)
	{
		m_justUpdate = isJustUpate;
	}
}
