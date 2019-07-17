/**
 * 
 */
package com.uns.model.process;

import java.util.logging.Level;

import org.compiere.model.MPayment;
import org.compiere.model.X_C_Payment;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.wf.MWorkflow;

import com.uns.model.MUNSVATPayment;

/**
 * @author Burhani Adam
 *
 */
public class CreateReplacePaymentFromTaxPayment extends SvrProcess {

	/**
	 * 
	 */
	public CreateReplacePaymentFromTaxPayment() {
		// TODO Auto-generated constructor stub
	}
	
	private int p_Payment_ID = 0;
	private MPayment p_Payment = null;
	private MUNSVATPayment p_VATPayment = null;
	private int p_VATPayment_ID = 0;

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter param : params)
		{
			if(param.getParameterName() == null)
				return ;
			else if(param.getParameterName().equals("Payment_ID"))
				p_Payment_ID = param.getParameterAsInt();
			else if(param.getParameterName().equals("UNS_VATPayment_ID"))
				p_VATPayment_ID = param.getParameterAsInt();
			else {
				log.log(Level.SEVERE, "Unknowon Parameter" + param.getParameterName());
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		if(getRecord_ID() > 0)
			p_VATPayment_ID = getRecord_ID();
		p_VATPayment = new MUNSVATPayment(getCtx(), p_VATPayment_ID, get_TrxName());
		if(!linkPayment(p_Payment_ID))
			return "Payment not created";
		p_VATPayment.setC_Payment_ID(p_Payment.get_ID());
		p_VATPayment.saveEx();
		
		return "Payment " + p_Payment.getDocumentNo() + " has created and linked.";
	}
	
	private boolean linkPayment(int C_Payment_ID)
	{
		if(p_VATPayment == null || p_VATPayment.get_ID() <= 0)
			return false;
		
		if(C_Payment_ID > 0)
		{
			p_Payment = new MPayment(getCtx(), p_Payment_ID, get_TrxName());
			if(p_Payment.getC_BankAccount_ID() != p_VATPayment.getC_BankAccount_ID()
					|| p_Payment.getC_Charge_ID() != p_VATPayment.getC_Charge_ID()
						|| p_Payment.getTotalAmt().compareTo(p_VATPayment.getPayAmt()) != 0)
				return false;
			else
			{
				p_VATPayment.setC_Payment_ID(C_Payment_ID);
				return p_VATPayment.save();
			}
		}
		else
			 return createPayment();
	}
	
	private boolean createPayment()
	{
		if(p_VATPayment == null)
			return false;
		if(p_VATPayment.getC_Payment_ID() > 0)
			return false;
		
		MPayment pay = new MPayment(getCtx(), 0, get_TrxName());
		if(p_VATPayment.getPayAmt().signum() > 0)
			pay.setIsReceipt(false);
		else
			pay.setIsReceipt(true);
		pay.setAD_Org_ID(p_VATPayment.getAD_Org_ID());
		pay.setAD_OrgTrx_ID(p_VATPayment.getAD_OrgTrx_ID());
		pay.setC_BankAccount_ID(p_VATPayment.getC_BankAccount_ID());
		pay.setDateAcct(p_VATPayment.getDateAcct());
		pay.setDateTrx(p_VATPayment.getDateTrx());
		pay.setC_BPartner_ID(p_VATPayment.getC_BPartner_ID());
		pay.setDescription("Auto Create from Tax Payment " + p_VATPayment.getDocumentNo());
		pay.setC_Charge_ID(p_VATPayment.getC_Charge_ID());
		pay.setPayAmt(p_VATPayment.getPayAmt());
		pay.setTenderType(p_VATPayment.getTenderType());
		pay.setC_Currency_ID(303);
		if(p_VATPayment.getTenderType().equals(X_C_Payment.TENDERTYPE_Check))
			pay.setCheckNo(p_VATPayment.getCheckNo());
		if(!pay.save())
			return false;
		else
			p_Payment = pay;
		
		ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(pay, DocAction.ACTION_Complete);
		if(pi.isError())
		{
			throw new AdempiereUserError("Can't complete payment.." + pay.getDocumentNo() + " ." + pi.getSummary());
		}
		
		return true;
	}
}