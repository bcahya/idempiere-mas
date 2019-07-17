package com.unicore.model.process;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MBankAccount;
import org.compiere.model.MPayment;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import com.unicore.model.MUNSBillingTrfValidation;
import com.unicore.model.MUNSTransferBalanceRequest;
/**
 *  Bank Transfer. Generate two Payments entry
 *  
 *  For Bank Transfer From Bank Account "A" 
 *                 
 *	@author victor.perez@e-evoltuion.com
 *	modify by nurseha :D for suport Transfer Balance Confirmation
 *	
 **/
public class UNSBankTransfer
{
	
	public UNSBankTransfer ()
	{
		super();
	}
	
	protected CLogger			log = CLogger.getCLogger (getClass());
	private Properties m_ctx;
	private String m_trxname;
	private String 		p_DocumentNo= "";				// Document No
	private String 		p_Description= "";				// Description
	private int 		p_C_BPartner_ID = 0;   			// Business Partner to be used as bridge
	private int			p_C_Currency_ID = 0;			// Payment Currency
	private int 		p_C_ConversionType_ID = 0;		// Payment Conversion Type
	private int			p_C_Charge_ID = 0;				// Charge to be used as bridge
	private String 		p_ChequeNo = null;
	private BigDecimal 	p_Amount = Env.ZERO;  			// Amount to be transfered between the accounts
	private int 		p_From_C_BankAccount_ID = 0;	// Bank Account From
	private int 		p_To_C_BankAccount_ID= 0;		// Bank Account To
	private Timestamp	p_StatementDate = null;  		// Date Statement
	private Timestamp	p_DateAcct = null; 
	private int			m_AD_OrgFrom_ID = 0;
	private int			m_AD_OrgTo_ID = 0;
	private int			m_paymentFrom_ID = 0;
	private int			m_paymentTo_ID = 0;
	private boolean		m_isAutoCompletePayment = true;
	
	public UNSBankTransfer(MUNSTransferBalanceRequest request)
	{
		this(request.getCtx(), "", request.getDescription(), request.getC_BPartner_ID()
				, request.getC_Currency_ID(), 0, 0, null, request.getAmountConfirmed()
				, request.getAccountFrom_ID(), request.getAccountTo_ID()
				, request.getDateRequired(), request.getDateRequired()
				, request.getAD_Org_ID(), request.getAD_OrgTo_ID()
				, true, request.get_TrxName());
	}
	
	public UNSBankTransfer(MUNSBillingTrfValidation trfValidation)
	{
		this(trfValidation.getCtx(), "", trfValidation.getDescription(), trfValidation.getC_BPartner_ID()
				, trfValidation.getC_Currency_ID(), 0, trfValidation.getC_Charge_ID(), null
				, trfValidation.getAmountTrf(), trfValidation.getAccountFrom_ID()
				, trfValidation.getAccountTo_ID(), trfValidation.getDateDoc(), trfValidation.getDateAcct()
				, trfValidation.getAD_Org_ID(), trfValidation.getAccountTo().getAD_Org_ID()
				, false, trfValidation.get_TrxName());
	}
	
	public UNSBankTransfer(Properties ctx, String documentNo, String description
			, int C_BPartner_ID, int C_Currency_ID, int conversionType_ID, int C_Charge_ID
			, String chequeNo, BigDecimal amt, int C_BankAcctFrom_ID, int C_BankAcctTo_ID
			, Timestamp statementDate, Timestamp dateAcct, int AD_OrgFrom_ID, int AD_OrgTo_ID
			, boolean isAutoCompletePayment, String trxName)
	{
		m_ctx =  ctx;
		m_trxname = trxName;
		p_DocumentNo= documentNo;				// Document No
		p_Description= description;				// Description
		p_C_BPartner_ID = C_BPartner_ID;   			// Business Partner to be used as bridge
		p_C_Currency_ID = C_Currency_ID <= 0 ? Env.getContextAsInt(m_ctx, "$C_Currency_ID") : C_Currency_ID;			// Payment Currency
		p_C_ConversionType_ID = conversionType_ID;		// Payment Conversion Type
		p_C_Charge_ID = C_Charge_ID;				// Charge to be used as bridge
		p_ChequeNo = chequeNo;
		p_Amount = amt;  			// Amount to be transfered between the accounts
		p_From_C_BankAccount_ID = C_BankAcctFrom_ID;	// Bank Account From
		p_To_C_BankAccount_ID= C_BankAcctTo_ID;		// Bank Account To
		p_StatementDate = statementDate;  		// Date Statement
		p_DateAcct = dateAcct; 
		m_AD_OrgFrom_ID = AD_OrgFrom_ID;
		m_AD_OrgTo_ID = AD_OrgTo_ID;
		m_isAutoCompletePayment = isAutoCompletePayment;
	}

	/**
	 *  Perform process.
	 *  @return Message (translated text)
	 *  @throws Exception if not successful
	 */
	public String doIt()
	{
		if (log.isLoggable(Level.INFO)) log.info("From Bank="+p_From_C_BankAccount_ID+" - To Bank="+p_To_C_BankAccount_ID
				+ " - C_BPartner_ID="+p_C_BPartner_ID+"- C_Charge_ID= "+p_C_Charge_ID+" - Amount="+p_Amount+" - DocumentNo="+p_DocumentNo
				+ " - Description="+p_Description+ " - Statement Date="+p_StatementDate+
				" - Date Account="+p_DateAcct);

		if (p_To_C_BankAccount_ID == 0 || p_From_C_BankAccount_ID == 0)
			return "Banks required";


		if (p_To_C_BankAccount_ID == p_From_C_BankAccount_ID)
			return "Banks From and To must be different";
		
		if (p_C_BPartner_ID == 0)
			return "Business Partner required";
		
		if (p_C_Currency_ID == 0)
			return "Currency required";
		
		if (p_Amount.signum() == 0)
			return "Amount required";

		//	Login Date
		if (p_StatementDate == null)
			p_StatementDate = Env.getContextAsDate(Env.getCtx(), "#Date");
		
		if (p_StatementDate == null)
			p_StatementDate = new Timestamp(System.currentTimeMillis());			

		if (p_DateAcct == null)
			p_DateAcct = p_StatementDate;

		return generateBankTransfer();
	}
	

	/**
	 * Generate BankTransfer()
	 *
	 */
	private String generateBankTransfer()
	{

		MBankAccount mBankFrom = new MBankAccount(m_ctx,p_From_C_BankAccount_ID, m_trxname);
		MBankAccount mBankTo = new MBankAccount(m_ctx,p_To_C_BankAccount_ID, m_trxname);
		
		MPayment paymentBankFrom = new MPayment(m_ctx, 0 ,  m_trxname);
		paymentBankFrom.setAD_Org_ID(m_AD_OrgFrom_ID);
		paymentBankFrom.setC_BankAccount_ID(mBankFrom.getC_BankAccount_ID());
		paymentBankFrom.setDocumentNo(p_DocumentNo);
		paymentBankFrom.setDateAcct(p_DateAcct);
		paymentBankFrom.setDateTrx(p_StatementDate);
		if (p_ChequeNo != null && !p_ChequeNo.isEmpty())
		{
			paymentBankFrom.setTenderType(MPayment.TENDERTYPE_Check);
			paymentBankFrom.set_ValueNoCheck("CheckNo",p_ChequeNo);
			paymentBankFrom.setDisbursementDate(p_StatementDate);
		}
		else 
			paymentBankFrom.setTenderType(MPayment.TENDERTYPE_DirectDeposit);
		
		paymentBankFrom.setDescription(p_Description);
		paymentBankFrom.setC_BPartner_ID (p_C_BPartner_ID);
		paymentBankFrom.setC_Currency_ID(p_C_Currency_ID);
		if (p_C_ConversionType_ID > 0)
			paymentBankFrom.setC_ConversionType_ID(p_C_ConversionType_ID);	
		paymentBankFrom.setPayAmt(p_Amount);
		paymentBankFrom.setOverUnderAmt(Env.ZERO);
		paymentBankFrom.setC_DocType_ID(false);
		paymentBankFrom.setC_Charge_ID(p_C_Charge_ID);
		paymentBankFrom.saveEx();
		m_paymentFrom_ID = paymentBankFrom.get_ID();
		
		if(m_isAutoCompletePayment)
		{
			if(!paymentBankFrom.processIt(MPayment.DOCACTION_Complete)) {
				log.warning("Payment Process Failed: " + paymentBankFrom + " - " + paymentBankFrom.getProcessMsg());
				return "Payment Process Failed: " + paymentBankFrom + " - " + paymentBankFrom.getProcessMsg();
			}
		}
		else
		{
			if(!paymentBankFrom.processIt(MPayment.DOCACTION_Prepare))
			{
				log.warning("Payment Process Failed: " + paymentBankFrom + " - " + paymentBankFrom.getProcessMsg());
				return "Payment Process Failed: " + paymentBankFrom + " - " + paymentBankFrom.getProcessMsg();
			}
		}
		paymentBankFrom.saveEx();
	

		MPayment paymentBankTo = new MPayment(m_ctx, 0 ,  m_trxname);
		paymentBankTo.setAD_Org_ID(m_AD_OrgTo_ID);
		paymentBankTo.setC_BankAccount_ID(mBankTo.getC_BankAccount_ID());
		paymentBankTo.setDocumentNo(p_DocumentNo);
		paymentBankTo.setDateAcct(p_DateAcct);
		paymentBankTo.setDateTrx(p_StatementDate);
		paymentBankTo.setTenderType(MPayment.TENDERTYPE_DirectDeposit);
		paymentBankTo.setDescription(p_Description);
		paymentBankTo.setC_BPartner_ID (p_C_BPartner_ID);
		paymentBankTo.setC_Currency_ID(p_C_Currency_ID);
		if (p_C_ConversionType_ID > 0)
			paymentBankFrom.setC_ConversionType_ID(p_C_ConversionType_ID);	
		paymentBankTo.setPayAmt(p_Amount);
		paymentBankTo.setOverUnderAmt(Env.ZERO);
		paymentBankTo.setC_DocType_ID(true);
		paymentBankTo.setC_Charge_ID(p_C_Charge_ID);
		paymentBankTo.set_ValueOfColumn("Reference_ID", paymentBankFrom.get_ID());
		paymentBankTo.saveEx();
		paymentBankFrom.set_ValueOfColumn("Reference_ID", paymentBankTo.get_ID());
		paymentBankFrom.saveEx();
		m_paymentTo_ID = paymentBankTo.get_ID();
		
		if(m_isAutoCompletePayment)
		{
			if (!paymentBankTo.processIt(MPayment.DOCACTION_Complete)) {
				log.warning("Payment Process Failed: " + paymentBankTo + " - " + paymentBankTo.getProcessMsg());
				return "Payment Process Failed: " + paymentBankTo + " - " + paymentBankTo.getProcessMsg();
			}
		}
		else
		{
			if(!paymentBankTo.processIt(MPayment.DOCACTION_Prepare))
			{
				log.warning("Payment Process Failed: " + paymentBankTo + " - " + paymentBankTo.getProcessMsg());
				return "Payment Process Failed: " + paymentBankTo + " - " + paymentBankTo.getProcessMsg();
			}
		}
		paymentBankTo.saveEx();
		
		return null;

	}  //  createCashLines
	
	public int getPaymentBankFrom_ID ()
	{
		return m_paymentFrom_ID;
	}
	
	public int getPaymentBankTo_ID ()
	{
		return m_paymentTo_ID;
	}
	
	public void setCheckNo (String checkNo)
	{
		p_ChequeNo = checkNo;
	}
}	//	ImmediateBankTransfer