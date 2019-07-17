/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.unicore.model.MUNSBillingLineGiro;
import com.unicore.model.MUNSBillingLineResult;
import com.uns.model.MUNSCustomerBG;

/**
 * @author setyaka
 * 
 */
public class CreateCustomerBG extends SvrProcess {

	private PO m_record = null;;
	private String m_GiroNumber = null;
	private int m_C_Bank_ID = 0;
	private Timestamp m_DueDate = null;
	private Timestamp m_ReceiptDate = null;
	private BigDecimal m_amtGiro = Env.ZERO;
	private int m_C_BPartner_ID = 0;
	private int m_AD_Org_ID = 0;
	private String m_AccountNo = null;
	private int m_C_Charge_ID = 0;
	private int m_AD_User_ID = 0;
	private BigDecimal m_chargeAmt = Env.ZERO; 

	/**
	 * 
	 */
	public CreateCustomerBG() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("GiroNumber"))
				m_GiroNumber = para[i].getParameter().toString();
			else if (name.equals("C_Bank_ID"))
				m_C_Bank_ID = para[i].getParameterAsInt();
			else if (name.equals("AccountNo"))
				m_AccountNo = para[i].getParameterAsString();
			else if (name.equals("DueDate"))
				m_DueDate = (Timestamp) para[i].getParameter();
			else if (name.equals("ReceiptDate"))
				m_ReceiptDate = (Timestamp) para[i].getParameter();
			else if (name.equals("LimitAmt"))
				m_DueDate = (Timestamp) para[i].getParameter();
			else if (name.equals("AmountGiro"))
				m_amtGiro = para[i].getParameterAsBigDecimal();
			else if (name.equals("C_BPartner_ID"))
				m_C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_Org_ID"))
				m_AD_Org_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Charge_ID"))
				m_C_Charge_ID = para[i].getParameterAsInt();
			else if (name.equals("ChargeAmt"))
				m_chargeAmt = para[i].getParameterAsBigDecimal();
			else if (name.equals("AD_User_ID"))
				m_AD_User_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		if(getRecord_ID() > 0)
		{
			if(getTable_ID() == MUNSBillingLineResult.Table_ID)
			{
				m_record = new MUNSBillingLineResult(getCtx(), getRecord_ID(), get_TrxName());	
			}
			else if(getTable_ID() == MUNSBillingLineGiro.Table_ID)
			{
				m_record = new MUNSBillingLineGiro(getCtx(), getRecord_ID(), get_TrxName());
			}
			if(m_record instanceof MUNSBillingLineResult)
			{
				MUNSBillingLineResult result = (MUNSBillingLineResult) m_record;
				result.m_force = true;
				m_C_BPartner_ID = result.getC_Invoice().getC_BPartner_ID();
				m_AD_Org_ID = result.getParent().getUNS_BillingGroup_Result().getAD_Org_ID();
			}
			else if(m_record instanceof MUNSBillingLineGiro)
			{
				MUNSBillingLineGiro lineGiro = (MUNSBillingLineGiro) m_record;
				lineGiro.m_force = true;
				m_C_BPartner_ID = lineGiro.getParent().getC_Invoice().getC_BPartner_ID();
				m_AD_Org_ID = lineGiro.getParent().getParent().getUNS_BillingGroup_Result().getAD_Org_ID();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		if(m_amtGiro.compareTo(Env.ZERO) <= 0)
			throw new AdempiereUserError("Please update amount giro (Cannot 0 or Negate)");
		MUNSCustomerBG cust = new MUNSCustomerBG(getCtx(), 0, get_TrxName());
		
		if(Util.isEmpty(m_GiroNumber.trim()) || Util.isEmpty(m_AccountNo.trim()))
			throw new AdempiereException("#Giro Number #Account No - disallowed null");
		
		cust.setAD_Org_ID(m_AD_Org_ID);
		cust.setDocStatus(MUNSCustomerBG.DOCSTATUS_InProgress);
		cust.setGrandTotal(Env.ZERO);
		cust.setPaymentStatus(MUNSCustomerBG.PAYMENTSTATUS_OnProcess);
		cust.setC_BPartner_ID(m_C_BPartner_ID);
		cust.setC_Bank_ID(m_C_Bank_ID);
		cust.setAccountNo(m_AccountNo);
		cust.setName(m_GiroNumber);
		cust.setDueDate(m_DueDate);
		cust.setReceiptDate(m_ReceiptDate);
		cust.setLimitAmt(m_amtGiro);
		cust.setSalesRep_ID(m_AD_User_ID);
		
		if (m_C_Charge_ID > 0 && m_chargeAmt.signum() > 0)
		{
			cust.set_ValueOfColumn("C_Charge_ID", m_C_Charge_ID);
			cust.set_ValueOfColumn("ChargeAmt", m_chargeAmt);
		}
		
		cust.saveEx();
		
		
		if(m_record != null)
		{
			m_record.set_ValueOfColumn("UNS_CustomerBG_ID", cust.get_ID());
			m_record.set_ValueOfColumn("CreateCustomerBG", "Y");
			m_record.saveEx();	
		}
		
		return "Customer BG has Create, " + cust.getName();
	}
}
