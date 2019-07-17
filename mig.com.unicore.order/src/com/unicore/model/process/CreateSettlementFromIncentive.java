/**
 * 
 */
package com.unicore.model.process;

import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.uns.model.MUNSChargeDetail;
import com.uns.model.MUNSChargeRS;
import com.uns.model.X_UNS_Charge_Detail;
import com.uns.util.MessageBox;

import com.unicore.model.MUNSShippingCrewIncentive;

/**
 * @author Burhani Adam
 *
 */
public class CreateSettlementFromIncentive extends SvrProcess {

	/**
	 * 
	 */
	public CreateSettlementFromIncentive() {
		// TODO Auto-generated constructor stub
	}
	
	private Timestamp dateTrx = null;
	private int m_Charge_ID = 0;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter param : params)
		{	
			if (param.getParameterName().equals(
					"DateTrx")) {
				dateTrx = param.getParameterAsTimestamp();
			}
			if (param.getParameterName().equals("C_Charge_ID")) {
				m_Charge_ID = param.getParameterAsInt();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		MUNSShippingCrewIncentive inc = new MUNSShippingCrewIncentive(getCtx(), getRecord_ID(), get_TrxName());
		if(inc.getCreateSettlement().equals("Y"))
			return "Settlement has created";
		
		MUNSChargeRS rs = new MUNSChargeRS(getCtx(), 0, get_TrxName());
		rs.setAD_Org_ID(inc.getAD_Org_ID());
		rs.setDateTrx(dateTrx);
		rs.setName("Auto Generated From Shipping Incentive");
		rs.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_Settlement));
		String sql = "SELECT C_BPartner_ID FROM AD_User WHERE UNS_Employee_ID=?";
		int emp = DB.getSQLValue(get_TrxName(), sql, inc.getUNS_Employee_ID());
		if(emp <= 0)
			return "Employee not have Business Partner";
		else
			rs.setC_BPartner_ID(emp);
		if(!rs.save())
			return "Error :: Settlement not created";
		
		MUNSChargeDetail cd = new MUNSChargeDetail(rs);
		cd.setChargeType(X_UNS_Charge_Detail.CHARGETYPE_Cost);
		cd.setC_Charge_ID(m_Charge_ID);
		cd.setAmount(inc.getGrandTotal());
		cd.setDescription("Auto Generated From Shipping Incentive");
		if(!cd.save())
			return "Error :: Charge Detail not created";
		
		int retVal = MessageBox.showMsg(inc, getCtx(),
				"Settlement has created, Do you want to complete?", "Settlement Confirmation", 
				MessageBox.YESNO, MessageBox.ICONINFORMATION);
		if(retVal == MessageBox.RETURN_OK || retVal == MessageBox.RETURN_YES)
		{
			try {
				rs.processIt(DocAction.ACTION_Complete);
				rs.saveEx();
			} catch (Exception e) {
				throw new AdempiereException(e.getMessage());
			}
		}
		
		inc.setUNS_Charge_RS_ID(rs.get_ID());
		inc.setDateProcessed(new Timestamp(System.currentTimeMillis()));
		inc.setCreateSettlement("Y");
		inc.saveEx();
		
		return "Settlement has created. No :: " + rs.getDocumentNo();
	}
}