package com.uns.model.process;

import java.sql.Timestamp;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSChargeConfirmationGroup;
import com.uns.util.ErrorMsg;

public class GenerateDetailConfirmation extends SvrProcess {

	public GenerateDetailConfirmation() {
		super ();
	}

	@Override
	protected void prepare() {
		ProcessInfoParameter[] pis = getParameter();
		for (ProcessInfoParameter pi : pis)
		{
			if (pi.getParameterName().equals("DateFrom"))
			{
				p_dateFrom = pi.getParameterAsTimestamp();
			}
			else if (pi.getParameterName().equals("DateTo"))
			{
				p_dateTo = pi.getParameterAsTimestamp();
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		if (null == p_dateFrom || null == p_dateTo)
		{
			ErrorMsg.setErrorMsg(getCtx(), "MandatoryParameterDate", "");
			return "Mandatory Parameter DateFrom And Date To";
		}
		MUNSChargeConfirmationGroup group = new MUNSChargeConfirmationGroup(
				getCtx(), getRecord_ID(), get_TrxName());
		return group.doLoadDetails(p_dateFrom, p_dateTo);
	}

	private Timestamp p_dateFrom = null;
	private Timestamp p_dateTo = null;
}
