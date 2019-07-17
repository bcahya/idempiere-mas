/**
 * 
 */
package com.uns.model.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MOrg;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import com.uns.util.MessageBox;
import com.uns.util.UNSApps;

import com.uns.model.MUNSChargeDetail;
import com.uns.model.MUNSChargeRS;
import com.uns.model.MUNSMonthlyPayrollEmployee;
import com.uns.model.MUNSPayrollEmployee;

/**
 * @author root
 *
 */
public class CreatePayrollPayment extends SvrProcess 
{
	private final String PAYMENT_TYPE_DETAILED_EMPLOYEE = "DE";
	private final String PAYMENT_TYPE_DETAILED_SECTION_DPT = "DS";
	private String m_payrollPaymentType = "";
	private int m_AD_User_ID = 0;
	private MUNSMonthlyPayrollEmployee m_model = null;
	private MBPartner m_partner = null;

	/**
	 * 
	 */
	public CreatePayrollPayment() 
	{
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] pis = getParameter();
		
		for(ProcessInfoParameter pi : pis) 
		{
			String name = pi.getParameterName();
			if (name.equals("AD_User_ID")) 
			{
				m_AD_User_ID = pi.getParameterAsInt();
			}
			else 
			{
				log.log(Level.SEVERE, "Unknown Parameter" + name);
			}
		}
		
		setModel(new MUNSMonthlyPayrollEmployee(
				getCtx(), getRecord_ID(), get_TrxName()));
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		
		int retVal = MessageBox.showMsg(
				getCtx(), getProcessInfo(), 
				"Proses ini masih dalam tahap pembangunan, "
				+ " silahkan coba beberapa hari lagi"
				, "Proses Masih Dalam Pembangunan"
				, MessageBox.OK, MessageBox.ICONINFORMATION);
		
		if(retVal == MessageBox.RETURN_OK) 
		{
			return "Terima kasih atas pengertiannya";
		}
		
		//TODO
		/**
		 * Harus di definisikan per field.
		 * karena nanti di gaji juga postingannya beda.
		 */
		if(m_AD_User_ID == 0) 
		{
			throw new AdempiereException("Can't initialized user");
		}
		
		if(getModel() == null) 
		{
			throw new AdempiereException("Process No PO");
		}
		
//		if(getModel().getUNS_Charge_RS_ID() > 0) 
//		{
//			return Msg.getMsg(getCtx(), 
//					"Document Has Linked To Charge Request");
//		}
		
		if(null == getPartner(true)) {
			throw new AdempiereUserError(
					Msg.getCleanMsg(getCtx(), 
							"User Is Not Linked To Business Partner"));
		}
		
		//TODO add column datedoc
		Timestamp date = new Timestamp(System.currentTimeMillis());
		MOrg org = MOrg.get(getCtx(), getModel().getAD_Org_ID());
		MUNSChargeRS request = new MUNSChargeRS(getCtx(), 0, get_TrxName());
		request.setDateTrx(date);
		request.setAD_Org_ID(getModel().getAD_Org_ID());
		request.setC_BPartner_ID(getPartner(false).get_ID());
		request.setC_Currency_ID(getModel().getC_Currency_ID());
		request.setC_DocType_ID(MDocType.getDocType("CHR"));
		request.setDescription(":: Auto Generated From Monthly Payroll Employee ::");
		request.setName("Monthy Payroll Request " + org.getName());
		request.saveEx();
		
		if(m_payrollPaymentType.equals(PAYMENT_TYPE_DETAILED_EMPLOYEE)) {
			for(MUNSPayrollEmployee payEmployee : getModel().getLines(false)) {
				MUNSChargeDetail cd = new MUNSChargeDetail(request);
				cd.setAmount(payEmployee.getTakeHomePay());
				cd.setC_Charge_ID(UNSApps.getRefAsInt(UNSApps.CHRG_HUTANG_GAJI));
				cd.setC_BPartner_ID(payEmployee.getUNS_Employee().getC_BPartner_ID());
				cd.setChargeType(MUNSChargeDetail.CHARGETYPE_Cost);
				cd.setDescription("Biaya Gaji Employee " + payEmployee.getUNS_Employee().getName());
				cd.saveEx();
			}
		}
		else if(m_payrollPaymentType.equals(PAYMENT_TYPE_DETAILED_SECTION_DPT)) {
			Hashtable<Integer, MUNSChargeDetail> mapCDetail = new Hashtable<>();
			for(MUNSPayrollEmployee payEmployee : getModel().getLines(false)) {
				I_C_BPartner sectDept = payEmployee.getUNS_Employee().getC_BPartner();
				
				MUNSChargeDetail cd = mapCDetail.get(sectDept.getC_BPartner_ID());
				if(null == cd) {
					cd = new MUNSChargeDetail(request);
					cd.setC_Charge_ID(UNSApps.getRefAsInt(UNSApps.CHRG_HUTANG_GAJI));
					cd.setC_BPartner_ID(sectDept.getC_BPartner_ID());
					cd.setChargeType(MUNSChargeDetail.CHARGETYPE_Cost);
					cd.setDescription("Biaya Gaji Section Dept" + sectDept.getName());
				}
				cd.setAmount(cd.getAmount().add(payEmployee.getTakeHomePay()));
			}
			
			for(MUNSChargeDetail cd : mapCDetail.values()) {
				cd.saveEx();
			}
		}
		
//		getModel().setUNS_Charge_RS_ID(request.get_ID());
		getModel().saveEx();
		
		return null;
	}
	
	private MBPartner getPartner(boolean requery) {
		if(null != m_partner && !requery) {
			return m_partner;
		}
		
		String sql = "SELECT * FROM C_BPartner WHERE C_BPartner_ID ="
				+ " (SELECT C_BPartner_ID FROM AD_User WHERE AD_User_ID = ?)";
		
		MBPartner partner = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = DB.prepareStatement(sql, get_TrxName());
			st.setInt(1, m_AD_User_ID);
			rs = st.executeQuery();
			if (rs.next()) {
				partner = new MBPartner(getCtx(), rs, get_TrxName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(rs, st);
		}
		return partner;
	}
	
	public MUNSMonthlyPayrollEmployee getModel() {
		return m_model;
	}
	
	public void setModel(MUNSMonthlyPayrollEmployee model) {
		this.m_model = model;
	}
}
