/**
 * 
 */
package com.uns.model.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSForecastHeader;

/**
 * @author menjangan
 *
 */
public class GenerateForecast extends SvrProcess {
	
	/** Record ID */
	private int m_Record_ID = 0;
	private String m_allocationMethod = INPUTALLOCATION_AllPlantOutput;
	
	public static final String INPUTALLOCATION_ExcludePerBuyer = "XBYR";
	public static final String INPUTALLOCATION_ExcludeGeneral = "XGNR";
	public static final String INPUTALLOCATION_AllPlantOutput = "ALLP";
	

	/**
	 * 
	 */
	public GenerateForecast() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (name.equals("AllocationMethod"))
				m_allocationMethod = para[i].getParameter().toString();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_Record_ID = getRecord_ID();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{		
		String my_Msg = "";
		MUNSForecastHeader forecastHeader = new MUNSForecastHeader(getCtx(), m_Record_ID, get_TrxName());
		
		if (!forecastHeader.deleteForecasts())
			throw new AdempiereException("Failed to reset existing forecasts data.");
		
		my_Msg = forecastHeader.generateIt(m_allocationMethod);
		
		return my_Msg;
	}

}
