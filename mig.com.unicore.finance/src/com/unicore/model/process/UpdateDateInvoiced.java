/**
 * 
 */
package com.unicore.model.process;

import java.sql.Timestamp;
import java.util.logging.Level;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author HandiWindu
 *
 */
public class UpdateDateInvoiced extends SvrProcess {

	/**
	 * 
	 */
	public UpdateDateInvoiced() {
		// TODO Auto-generated constructor stub
	}
	
	private Timestamp m_DateInvoiced = null;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("DateInvoiced"))
				m_DateInvoiced = (Timestamp) para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		String sql = "UPDATE UNS_VATLine SET DateInvoiced = ? WHERE UNS_VAT_ID=?";
		DB.executeUpdate(sql, new Object[]{m_DateInvoiced, getRecord_ID()}, false, get_TrxName());
		return "Success";
	}
}
