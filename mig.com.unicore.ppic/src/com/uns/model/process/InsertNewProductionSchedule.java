/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import com.uns.model.MUNSManufacturingOrder;
import com.uns.model.MUNSProductionSchedule;

/**
 * @author YAKA
 *
 */
public class InsertNewProductionSchedule extends SvrProcess implements ProcessCall {

	private int record_ID;
	private Properties m_Ctx = null;
	private String m_TrxName = null;
	private int m_M_Product_ID;
	private String m_PSType;
	private BigDecimal m_qtyToSchedule = Env.ZERO;
	private int m_UNS_MPSProductRsc_Weekly_ID;
	private MUNSManufacturingOrder m_MO;

	/**
	 * 
	 */

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("M_Product_ID"))
				m_M_Product_ID = para[i].getParameterAsInt();
			else if (name.equals("QtyOrdered"))
				m_qtyToSchedule = (BigDecimal) para[i].getParameter();
			else if (name.equals("PSType"))
				m_PSType = (String) para[i].getParameter();
			else if (name.equals("UNS_MPSProductRsc_Weekly_ID"))
				m_UNS_MPSProductRsc_Weekly_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_Ctx = getCtx();
		m_TrxName = get_TrxName();
		record_ID = getRecord_ID(); 
		m_MO = new MUNSManufacturingOrder(m_Ctx, record_ID, m_TrxName);
	}
	
	/**
	 * 
	 */
	public InsertNewProductionSchedule() {
		
	}

	/**
	 * 
	 * @param ctx
	 * @param UNS_ProductionSchedule_ID
	 * @param trxName
	 */
	public InsertNewProductionSchedule(Properties ctx, int UNS_ProductionSchedule_ID, String trxName)
	{
		this.m_Ctx = ctx;
		this.record_ID = UNS_ProductionSchedule_ID;
		this.m_TrxName = trxName;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		MUNSProductionSchedule ps = new MUNSProductionSchedule(m_Ctx, 0, m_TrxName);

		//ps.setAD_Org_ID(m_MO.getProductionDepartment_ID());
		ps.setUNS_Manufacturing_Order_ID(m_MO.get_ID());
		ps.setM_Product_ID(m_M_Product_ID);
		ps.setPSType(m_PSType);
		ps.setUNS_MPSProductRsc_Weekly_ID(m_UNS_MPSProductRsc_Weekly_ID);
		ps.setQtyOrdered(m_qtyToSchedule);
		ps.setC_UOM_ID(ps.getM_Product().getC_UOM_ID());
		/*
		MUNSMPSProductRscWeekly mpsPRScWeekly = 
				new MUNSMPSProductRscWeekly(m_Ctx, m_UNS_MPSProductRsc_Weekly_ID, m_TrxName);
		ps.setQtyMPS(mpsPRScWeekly.getActualToStockUOM());
		if(mpsPRScWeekly.getActualToOrderUOM().compareTo(BigDecimal.ZERO) > 0)
		{
			ps.setQtyMT(mpsPRScWeekly.getActualToOrderMT());
			ps.setQtyMT(mpsPRScWeekly.getActualToOrderMT());
		}
		*/		
		ps.saveEx();
		
		//addLog(record_ID, p.getProductionDate(), p.getProductionQty(), ps.getDocumentNo(), ps.get_Table_ID(), ps.get_ID());
		addLog("New production schedule has been inserted.");
		return "Production Schedule has been created.";
	}
}
