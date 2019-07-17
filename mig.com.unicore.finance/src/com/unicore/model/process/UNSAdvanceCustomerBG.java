/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.uns.model.MUNSCustomerBG;

/**
 * @author Toriq
 *
 */
public class UNSAdvanceCustomerBG extends SvrProcess {

	/**
	 * 
	 */
	private String m_GiroNumber=null;
	private int m_C_Bank_ID=0;
	private Timestamp m_DueDate=null;
	private Timestamp m_ReceiptDate=null;
	private BigDecimal m_AmountGiro=Env.ZERO;
	private String m_Message=null;
	private String m_AccountNo = null;
	private int m_C_Bpartner_ID=0;
	private BigDecimal m_totgiro=Env.ZERO ;
	private int m_C_Charge_ID = 0;
	private BigDecimal m_chargeAmt = Env.ZERO;
		
	public UNSAdvanceCustomerBG() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		ProcessInfoParameter[] para = getParameter();
		for (int i =0 ; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("GiroNumber"))
				m_GiroNumber = para[i].getParameterAsString();
			else if (name.equals("C_BPartner_ID"))
				m_C_Bpartner_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Bank_ID"))
				m_C_Bank_ID = para[i].getParameterAsInt();
			else if (name.equals("AccountNo"))
				m_AccountNo = para[i].getParameterAsString();
			else if (name.equals("DueDate"))
				m_DueDate = para[i].getParameterAsTimestamp();
			else if (name.equals("ReceiptDate"))
				m_ReceiptDate = para[i].getParameterAsTimestamp();
			else if (name.equals("AmountGiro"))
				m_AmountGiro=para[i].getParameterAsBigDecimal();
			else if (name.equals("C_Charge_ID"))
				m_C_Charge_ID = para[i].getParameterAsInt();
			else if (name.equals("ChargeAmt"))
				m_chargeAmt = para[i].getParameterAsBigDecimal();
			else
				log.log(Level.SEVERE,"Data Not Found : "+ name);
		}
		if (Util.isEmpty(m_GiroNumber) && m_C_Bank_ID==0 && m_DueDate==null && m_ReceiptDate==null &&
				m_AmountGiro.compareTo(Env.ZERO)==0)
		{
			throw new AdempiereUserError("No data change..!!");
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		
		MUNSCustomerBG bg=new MUNSCustomerBG(getCtx(),getRecord_ID(), get_TrxName());
		
		if (m_AmountGiro.signum()>0 || m_C_Bpartner_ID>0)
		{
			if (!statusResult(getRecord_ID()))
			    throw new AdempiereUserError("Giro has processed....!!");
		}
		if (!validateAmount(bg))
			throw new AdempiereUserError("This Allowed Amount giro less then paid amount.."+m_totgiro.setScale(2) +
					" has allocated");
		if (!validateDueDate(bg))
			throw new AdempiereUserError("Date DueDate can't smaller than Receipt Date");
		
		updateBG(bg);
		
		return m_Message;
		
	}
	
	public boolean statusResult(int UNS_CustomerBG_ID)
	{
		String sql="SELECT 1 from Uns_BillingLine_Result brl where brl.Uns_CustomerBG_ID="+UNS_CustomerBG_ID
				+ " and brl.Uns_Billing_Result_id in(select br.Uns_Billing_Result_id from Uns_Billing_Result br where"
				+ " br.uns_billinggroup_result_id in(select uns_billinggroup_result_id from "
				+ "uns_billinggroup_result where docstatus in('CO','CL')))";
		int res=DB.getSQLValue(get_TrxName(), sql);
		if (res>=1)
			return false;
		return true;
	}
	
	public boolean validateAmount(MUNSCustomerBG customerBG)
	{
		String sql="SELECT coalesce(sum(brl.paidamtgiro),0) FROM UNS_BillingLine_Result brl "
				+ "where brl.UNS_CustomerBG_ID="+customerBG.get_ID();
		BigDecimal res=DB.getSQLValueBDEx(get_TrxName(), sql);
		
		String lsql="SELECT coalesce(sum(bl.paidamtgiro),0) FROM UNS_BillingLine_Giro bl "
				+ "where bl.UNS_CustomerBG_ID="+customerBG.get_ID();
		BigDecimal lres=DB.getSQLValueBDEx(get_TrxName(), lsql);
		
		m_totgiro=res.add(lres);
		if (m_AmountGiro.compareTo(Env.ZERO) ==1)
		{
			if(m_AmountGiro.compareTo(m_totgiro)==-1)
			{
				return false;				
			}				
		}
		return true;		
	}
	
	public boolean validateDueDate (MUNSCustomerBG customerBG)
	{
		if (m_DueDate != null)
		{
			if (m_DueDate.compareTo(customerBG.getReceiptDate())==-1)
			{
				return false;
			}
		}
		return true;
	}
	
	public String updateBG (MUNSCustomerBG customerBG)
	{
		if (m_GiroNumber!=null || !Util.isEmpty(m_GiroNumber))
			customerBG.setName(m_GiroNumber);
		if (m_C_Bank_ID>0)
			customerBG.setC_Bank_ID(m_C_Bank_ID);
		if (m_C_Bpartner_ID>0)
			customerBG.setC_BPartner_ID(m_C_Bpartner_ID);
		if (m_DueDate!=null)
		    customerBG.setDueDate(m_DueDate);
		if (m_ReceiptDate!=null)
			customerBG.setReceiptDate(m_ReceiptDate);
		if (m_AmountGiro.compareTo(Env.ZERO)==1);
			customerBG.setLimitAmt(m_AmountGiro);
		if (m_AccountNo!= null || !Util.isEmpty(m_AccountNo))
			customerBG.setAccountNo(m_AccountNo);
		if (null != m_chargeAmt && m_C_Charge_ID > 0)
		{
			customerBG.set_ValueOfColumn("C_Charge_ID", m_C_Charge_ID);
			customerBG.set_ValueOfColumn("ChargeAmt", m_chargeAmt);
		}
		if (!customerBG.save())
			throw new AdempiereUserError("Data can't save...!!");
		
		m_Message = "Update complet";			
		
		return m_Message;		
	}
	
}
