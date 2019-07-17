/**
 * 
 */
package com.uns.model.process;

import java.util.Properties;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.uns.model.MUNSMPSHeader;

/**
 * @author AzHaidar
 * @Untasoft 
 */
public class MRPUpdateProcessor extends SvrProcess {
	
	private Properties m_ctx = null;
	private String m_trxName = null;
	private MUNSMPSHeader m_MPSHeader = null;
	
	/**
	 * 
	 */
	public MRPUpdateProcessor() {
		
	}
	
	public MRPUpdateProcessor(MUNSMPSHeader mpsHeader)
	{
		m_ctx = mpsHeader.getCtx();
		m_trxName = mpsHeader.get_TrxName();
		m_MPSHeader = mpsHeader;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{	
		m_ctx = getCtx();
		m_trxName = get_TrxName();
		m_MPSHeader= new MUNSMPSHeader(m_ctx, getRecord_ID(), m_trxName);
		//if(m_MPSHeader.getPrevMPS_ID() > 0)
		//	m_prevMPS = (MUNSMPSHeader)m_MPSHeader.getPrevMPS();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		String msg = null;

		String sql = "SELECT * FROM UpdateMRPLines(?)";
		msg = DB.getSQLValueString(get_TrxName(), sql, m_MPSHeader.get_ID());

		return msg;
	}	
}
