/**
 * 
 */
package com.uns.model.process;

import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSProduction;
import com.uns.model.MUNSProductionOutPlan;

/**
 * @author YAKA
 *
 */
public class GenerateOutputPlan extends SvrProcess {

	/** The Record */
	private int m_Record_ID = 0;
	
	/** Record MUNSProduction */
	private MUNSProduction m_production;
	
	private Properties m_Ctx;
	private String m_TrxName;
	
	public GenerateOutputPlan(){
		super();
	}
	

	public Properties getM_Ctx() {
		return m_Ctx;
	}

	public void setM_Ctx(Properties m_Ctx) {
		this.m_Ctx = m_Ctx;
	}

	public String getM_TrxName() {
		return m_TrxName;
	}

	public void setM_TrxName(String m_TrxName) {
		this.m_TrxName = m_TrxName;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param production
	 * @param trxName
	 * @return
	 * @throws Exception
	 */
	public static String doIt(Properties ctx, MUNSProduction production, String trxName)
		throws Exception
	{
		GenerateOutputPlan outPlan = new GenerateOutputPlan();
		outPlan.m_Ctx = ctx;
		outPlan.m_TrxName = trxName;
		outPlan.m_Record_ID = production.get_ID();
		outPlan.m_production = production;
		return outPlan.doIt();
	}

	/**
	 * Get Record
	 * 
	 * @return Record
	 */
	protected MUNSProduction getRecord() {
		return new MUNSProduction(m_Ctx, m_Record_ID, m_TrxName);
	} // getProductionRecord

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_Record_ID = getRecord_ID();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		setM_Ctx(getCtx());
		setM_TrxName(get_TrxName());
		
		m_production = getRecord();
		
		String msg = null;

		m_production.deleteOutputs(m_TrxName);
		m_production.deleteWorkers(m_TrxName);
		
		try {
			if (MUNSProduction.OUTPUTTYPE_Multi.equals(m_production.getOutputType())
					|| MUNSProduction.OUTPUTTYPE_MultiOptional.equals(m_production.getOutputType())){
				MUNSProductionOutPlan.generateOutPlan(
						m_Ctx, m_TrxName, m_production, m_production.getOutputType());
			}
			m_production.setGenerateOutPlan("Y");
			m_production.saveEx();
			msg = "Output Plan has been created.";
		}
		catch (AdempiereException ex) {
			msg = ex.getMessage();
		}
		
		return msg;
	}

}
