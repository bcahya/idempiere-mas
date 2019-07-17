/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.model.MProductBOM;
import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSProduction;
import com.uns.model.MUNSProductionOutPlan;
import com.uns.model.MUNSProductionSchedule;
import com.uns.model.MUNSResourceInOut;

/**
 * @author YAKA
 *
 */
public class CreateProduction extends SvrProcess implements ProcessCall {

	private int record_ID;
	private Properties m_Ctx = null;
	private String m_TrxName = null;
	private int m_unsRscID = 0;
	private BigDecimal m_qtyToManufacture = null;

	/**
	 * 
	 */

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("UNS_Resource_ID"))
				m_unsRscID = para[i].getParameterAsInt();
			else if (name.equals("Quantity"))
				m_qtyToManufacture = (BigDecimal) para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_Ctx = getCtx();
		m_TrxName = get_TrxName();
		record_ID = getRecord_ID(); 
	}
	
	public CreateProduction() {
		
	}
	
	public CreateProduction(Properties ctx, int UNS_ProductionSchedule_ID, String trxName)
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
		if (record_ID < 1)
			throw new AdempiereException("Record not found");
		MUNSProductionSchedule ps = new MUNSProductionSchedule(m_Ctx, record_ID, m_TrxName);
		MUNSProduction p = null;
		
		p = new MUNSProduction(ps, m_unsRscID, m_qtyToManufacture);
		
		if(!p.save())
			throw new AdempiereException("Production can't create.");
		
		if (p.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Multi))
		{
			BigDecimal mainPartBOMQty = MUNSResourceInOut.getMainPartBOMQtyOf(
					(MProduct) p.getM_Product(), MProductBOM.BOMTYPE_StandardPart);
			BigDecimal inputQty = ps.getQtyOrdered().multiply(mainPartBOMQty);
			
			GenerateOutputPlan.doIt(m_Ctx, p, m_TrxName);
			
			for (MUNSProductionOutPlan outputPlan : p.getOutputs())
			{
				if (outputPlan.getM_Product_ID() == ps.getM_Product_ID())
				{
					outputPlan.setQtyPlan(ps.getQtyOrdered());
					outputPlan.saveEx();
					continue;
				}
				mainPartBOMQty = MUNSResourceInOut.getMainPartBOMQtyOf(
						(MProduct) outputPlan.getM_Product(), MProductBOM.BOMTYPE_StandardPart);
				if (mainPartBOMQty.signum() > 0)
				{
					outputPlan.setQtyPlan(inputQty.divide(mainPartBOMQty, 6, BigDecimal.ROUND_HALF_UP));
					outputPlan.saveEx();
				}
			}
			//for (MUNSProductionOutPlan)
		}
		addLog(record_ID, p.getProductionDate(), p.getProductionQty(), p.getDocumentNo(), p.get_Table_ID(), record_ID);
		return "Production created.";
	}
}
