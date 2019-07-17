/**
 * 
 */
package com.unicore.model.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Util;

import com.unicore.model.MUNSBillingGroup;

/**
 * @author setyaka
 * 
 */
public class ProcessGenerateBilling extends SvrProcess {
	/** The Record */
	private int p_Record_ID = 0;
	private int m_SalesRep_ID = 0;
	private boolean m_DeleteOld = false;
	private String m_InvCollection = null;
	private boolean m_ExcludeBilledInvoice = false;
	private Timestamp m_dateFrom = null;
	private Timestamp m_dateTo = null;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("SalesRep_ID"))
				m_SalesRep_ID  = para[i].getParameterAsInt();
			else if (name.equals("DeleteOld"))
				m_DeleteOld  = para[i].getParameterAsBoolean();
			else if (name.equals("InvoiceCollectionType"))
				m_InvCollection  = para[i].getParameterAsString();
			else if (name.equals("IsExcludeBilledInvoice"))
				m_ExcludeBilledInvoice = para[i].getParameterAsBoolean();
			else if (para[i].getParameterName().equals("DateFrom"))
				m_dateFrom = para[i].getParameterAsTimestamp();
			else if (para[i].getParameterName().equals("DateTo"))
				m_dateTo = para[i].getParameterAsTimestamp();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_Record_ID = getRecord_ID();
	} // prepare

	/**
	 * Process
	 * 
	 * @return message
	 * @throws Exception
	 */
	protected String doIt() throws Exception {
		int cnt = 0;
		if(p_Record_ID <= 0)
		{
			throw new AdempiereException("No Record ID");
		}
		
		MUNSBillingGroup bilGroup = new MUNSBillingGroup(getCtx(), p_Record_ID, get_TrxName());
		if (bilGroup.getUNS_BillingGroup_ID() == 0)
			throw new AdempiereUserError("@No@ @Billing@");
		if(bilGroup.isProcessed())
			return "Can't process processed document.";
		if (m_SalesRep_ID != bilGroup.getSalesRep_ID())
			bilGroup.setSalesRep_ID(m_SalesRep_ID);
		if(Util.isEmpty(m_InvCollection, true))
			throw new AdempiereException("Colection Type in not defined");
		if(m_dateTo != null && m_dateTo.after(bilGroup.getDateDoc()))
			throw new AdempiereException("You can not search invoices with past the document date");

		cnt = bilGroup.recalcBilling(m_SalesRep_ID, m_DeleteOld,
				m_InvCollection, m_ExcludeBilledInvoice, m_dateFrom, m_dateTo);

		if (cnt == -1)
			throw new AdempiereUserError("Error calculating billing, please check log");

		bilGroup.setGenerateBillingLine("Y");
		if (bilGroup.save())
			return "@Inserted@=" + cnt;
		
		throw new AdempiereUserError("Error calculating billing line, please check log");
	} // doIt

}
