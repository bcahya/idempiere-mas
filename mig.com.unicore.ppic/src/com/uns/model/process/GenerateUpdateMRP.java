package com.uns.model.process;
/**
 * 
 *
package com.uns.model.process;

import org.compiere.model.MInOut;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MRPUpdateGenerator;
import com.uns.model.MUNSMPSHeader;
import com.uns.model.MUNSProduction;

/**
 * @author menjangan
 *
 *
public class GenerateUpdateMRP extends SvrProcess {
	
	private int m_M_InOut_ID = 0;
	private int m_UNS_Production_ID = 0;
	private int m_Record_ID = 0;
	/**
	 * 
	 *
	public GenerateUpdateMRP() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 *
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter[] param = getParameter();
		for (int i=0; i<param.length; i++)
		{
			String paramName = param[i].getParameterName();
			if (paramName.equals(MInOut.COLUMNNAME_M_InOut_ID))
			{
				m_M_InOut_ID = param[i].getParameterAsInt();
			}
			else if (paramName.equals(MUNSProduction.COLUMNNAME_UNS_Production_ID))
			{
				m_UNS_Production_ID = param[i].getParameterAsInt();
			}
		}
		m_Record_ID = getRecord_ID();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 *
	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		String msg = null;
		if (m_M_InOut_ID <= 0 && m_UNS_Production_ID <= 0)
		{
			msg = "Please choise production or receipt at the list";
			throw new IllegalArgumentException(msg);
		}
		
		MUNSMPSHeader mpsHeader = new MUNSMPSHeader(getCtx(), m_Record_ID, get_TrxName());
		
		if (m_M_InOut_ID > 0)
		{
			MInOut inout = new MInOut(getCtx(), m_M_InOut_ID, get_TrxName());
			MRPUpdateGenerator mrpUpdateGenerator = new MRPUpdateGenerator(mpsHeader);
			mrpUpdateGenerator.generateUpdateMRP(inout);
		}
		else if (m_UNS_Production_ID > 0)
		{
			MUNSProduction production = new MUNSProduction(getCtx(), m_UNS_Production_ID, get_TrxName());
			MRPUpdateGenerator mrpUpdateGenerator = new MRPUpdateGenerator(mpsHeader);
			mrpUpdateGenerator.generateUpdateMRP(production);
		}
		return msg;
	}
}
*/