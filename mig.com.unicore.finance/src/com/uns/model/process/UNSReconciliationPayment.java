/**
 * 
 */
package com.uns.model.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MPayment;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 * @author Burhani Adam
 *
 */
public class UNSReconciliationPayment extends SvrProcess {

	/**
	 * 
	 */
	public UNSReconciliationPayment() {
		// TODO Auto-generated constructor stub
	}
	
	private int m_Org_ID = 0;
	private Timestamp m_DateFrom = null;
	private Timestamp m_DateTo = null;
	private int m_Account_ID = 0;
	private boolean m_IncludeReversed = false;
	private IProcessUI m_ui = null;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter param : params)
		{
			if (param.getParameterName().equals("AD_Org_ID"))
				m_Org_ID = param.getParameterAsInt();
			else if(param.getParameterName().equals("DateFrom"))
				m_DateFrom = param.getParameterAsTimestamp();
			else if(param.getParameterName().equals("DateTo"))
				m_DateTo = param.getParameterAsTimestamp();
			else if(param.getParameterName().equals("C_BankAccount_ID"))
				m_Account_ID = param.getParameterAsInt();
			else if(param.getParameterName().equals("IsIncludeReversed"))
				m_IncludeReversed = param.getParameterAsBoolean();
			else
				log.log(Level.INFO, "Unknown Parameter: " + param.getParameterName());
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		m_ui = Env.getProcessUI(getCtx());
		String sql = "SELECT ARRAY_TO_STRING(ARRAY_AGG(p.C_Payment_ID), ';')"
				+ " FROM C_Payment p WHERE NOT EXISTS (SELECT 1 FROM C_BankStatementLine bsl"
				+ " WHERE bsl.C_Payment_ID = p.C_Payment_ID)";
		if(m_Org_ID > 0)
			sql += " AND p.AD_Org_ID = " + m_Org_ID;
		if(m_DateFrom != null)
			sql += " AND p.DateTrx >= '" + m_DateFrom + "'";
		if(m_DateTo != null)
			sql += " AND p.DateTrx <= '" + m_DateTo + "'";
		if(m_Account_ID > 0)
			sql += " AND p.C_BankAccount_ID = " + m_Account_ID;
		if(m_IncludeReversed)
			sql += " AND p.DocStatus IN ('CO', 'CL', 'RE')";
		else
			sql += " AND p.DocStatus IN ('CO', 'CL')";
		
		String ids = DB.getSQLValueString(get_TrxName(), sql);
		if(Util.isEmpty(ids, true))
			return "Success";
		
		String[] id = ids.split(";");
		
		for(int i = 0; i < id.length; i++)
		{
			m_ui.statusUpdate("Payment " + i + " of " + id.length + " Payments");
			MPayment pay = new MPayment(getCtx(), new Integer(id[i]), get_TrxName());
			MBankStatement stmt = MBankStatement.getOpen(pay.getC_BankAccount_ID(), get_TrxName(), true);
			stmt.saveEx();
			MBankStatementLine bsl = new MBankStatementLine(stmt);
			bsl.setPayment(pay);
			if(!bsl.save())
				throw new AdempiereException("Failed when trying create statement line");
		}
		
		return "Success reconciliation " + id.length + " Payments";
	}
}