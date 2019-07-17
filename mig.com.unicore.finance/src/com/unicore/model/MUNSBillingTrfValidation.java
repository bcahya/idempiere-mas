/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPayment;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.unicore.model.process.UNSBankTransfer;

/**
 * @author ALBURHANY
 * 
 */
public class MUNSBillingTrfValidation extends X_UNS_BillingTrfValidation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2072792308820786050L;

	/**
	 * @param ctx
	 * @param UNS_BillingTrfValidation_ID
	 * @param trxName
	 */
	public MUNSBillingTrfValidation(Properties ctx,
			int UNS_BillingTrfValidation_ID, String trxName) {
		super(ctx, UNS_BillingTrfValidation_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBillingTrfValidation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Before Save
	 * 
	 * @param newRecord
	 *            new
	 * @return true
	 */
	protected boolean beforeSave(boolean newRecord)
	{
		if(newRecord && getUNS_BillingTrf_ID() > 0)
		{
			MUNSBillingTrf group = (MUNSBillingTrf) getUNS_BillingTrf();
			setDateDoc(group.getDateDoc());
			setDateAcct(group.getDateAcct());
			setC_BPartner_ID(group.getC_BPartner_ID());
			setC_Currency_ID(group.getC_Currency_ID());
			setC_Charge_ID(group.getC_Charge_ID());
		}
		
		if(getAD_Org_ID() != getAccountFrom().getAD_Org_ID())
			setAD_Org_ID(getAccountFrom().getAD_Org_ID());

//		if(!newRecord && is_ValueChanged(COLUMNNAME_AmountTrf))
//		{
//			if(getAmountTrf().compareTo((BigDecimal) get_ValueOld(COLUMNNAME_AmountTrf)) == 1)
//				log.saveError("Error", "");
//			if(getAmountTrf().compareTo(getAmount()) == 1)
//				throw new AdempiereUserError("Amount Transferred not bigger than Total Amount");
			
//			generatePayments(getAmountTrf());
			
//			if(getAmountTrf().compareTo(getAmount()) == -1)
//			{
//				BigDecimal diffNow = Env.ZERO;
//				diffNow = getAmountTrf().subtract(getAmount());
//				
//				for(MUNSBillingTrfDetail tfd : getDetail())
//				{
//					if(tfd.getAmountTrf().compareTo(diffNow) == 0)
//					{
//						tfd.delete(true);
//						break;
//					}
//					
//					if(tfd.getAmountTrf().compareTo(diffNow) == -1)
//					{
//						diffNow = diffNow.subtract(tfd.getAmountTrf());
//						tfd.delete(true);
//						continue;
//					}
//					
//					if(tfd.getAmountTrf().compareTo(diffNow) == 1)
//					{
//						tfd.setAmountTrf(tfd.getAmountTrf().subtract(diffNow));
//						tfd.setDescription("Auto subtract amount transfer :: by System");
//						tfd.saveEx();
//						break;
//					}
//				}
//			}
//		}
		
		return true;
	}

	protected boolean afterSave(boolean newRecord, boolean success)
	{
		String err = allowedTransfer();
		if(!Util.isEmpty(err, true))
		{
			log.saveError("Error", err);
			return false;
		}
		
		if(!newRecord && is_ValueChanged(COLUMNNAME_AmountTrf))
		{
			if(!deleteinvoice() || !deleteDetail())
			{
				log.saveError("Error", "Failed when trying delete Detail OR Invoices");
				return false;
			}
		}
		if((newRecord || is_ValueChanged(COLUMNNAME_AmountTrf)) && getAmountTrf().compareTo(Env.ZERO) == 1)
		{
			generatePayments(getAmountTrf());
		}
		
		String msg = updateHeader();
		if(!Util.isEmpty(msg, true))
		{
			log.saveError("Error", msg);
			return false;
		}
		
		return true;
	}
	
	public String updateHeader()
	{
		String m_message = null;
		String sql = "UPDATE UNS_BillingTrf bt SET AmountTrf = "
				+ " (SELECT COALESCE(SUM(btv.AmountTrf),0) FROM UNS_BillingTrfValidation btv WHERE"
				+ " bt.UNS_BillingTrf_ID = btv.UNS_BillingTrf_ID), DifferenceAmt = bt.Amount - "
				+ " (SELECT COALESCE(SUM(btv.AmountTrf),0) FROM UNS_BillingTrfValidation btv WHERE"
				+ " bt.UNS_BillingTrf_ID = btv.UNS_BillingTrf_ID)"
				+ " WHERE bt.UNS_BillingTrf_ID = ?";
		int count = DB.executeUpdate(sql, getUNS_BillingTrf_ID(), get_TrxName());
		if(count < 0)
			m_message= "Failed when trying update amount transferred";
		
		return m_message;
	}

	protected boolean beforeDelete()
	{
		if(getC_Payment_ID() >0)
		{
			MPayment p = new MPayment(getCtx(), getC_Payment_ID(), get_TrxName());
			if(p.isComplete())
			{
				log.saveError("Error", "Payment has processed");
				return false;
			}
		}
		
		if(!deleteinvoice() || !deleteDetail())
		{
			log.saveError("Error", "Failed when trying delete Detail OR Invoices");
			return false;
		}
		return true;
	}
	
	protected boolean afterDelete(boolean success)
	{
		String delPay = "DELETE FROM C_Payment WHERE C_Payment_ID=" + getC_Payment_ID()
				+ " OR C_Payment_ID=" + getReference_ID();
		int countPay = DB.executeUpdate(delPay, get_TrxName());
		if(countPay < 0)
			throw new AdempiereException("Cannot delete Payment, please contact Administrator");
		
		return true;
	}
	
	private boolean deleteDetail()
	{
		String sql = "DELETE FROM UNS_BillingTrf_Detail WHERE UNS_BillingTrfValidation_ID = ?";
		int count = DB.executeUpdate(sql, get_ID(), false, get_TrxName());
		
		return count >= 0;
	}
	
	private boolean deleteinvoice()
	{
		String sql = "DELETE FROM UNS_BillingTrf_Invoice tfi WHERE EXISTS"
				+ " (SELECT 1 FROM UNS_BIllingTrf_Detail tfd WHERE tfd.UNS_BIllingTrf_Detail_ID"
				+ " = tfi.UNS_BIllingTrf_Detail_ID AND tfd.UNS_BIllingTrfValidation_ID = ?)";
		int count = DB.executeUpdate(sql, get_ID(), false, get_TrxName());
		
		return count >= 0;
	}

	/** Process Message */
	private String m_processMsg = null;
		
	public boolean processIt(String action)
	{
		if(Util.isEmpty(action, true))
			return true;
		
		if(!action.equals(DocAction.ACTION_Complete))
		{
			MPayment payAP = new MPayment(getCtx(), getC_Payment_ID(), get_TrxName());
			
			if(!payAP.processIt(DocAction.ACTION_Void) || !payAP.save())
			{				
				m_processMsg = payAP.getProcessMsg();
				return false;
			}
			
			if(action.equals(DocAction.ACTION_ReActivate))
			{
				setC_Payment_ID(-1);
				setReference_ID(-1);
				setProcessed(false);
			}
			return true;
		}
		else
		{
			if (getDetail().length == 0)
				throw new AdempiereUserError(
						"Please define detail for complete this document");
	
			if(getC_Payment_ID() > 0)
			{
				MPayment payAP = new MPayment(getCtx(), getC_Payment_ID(), get_TrxName());
			
				if(payAP.getTotalAmt().compareTo(getAmountTrf()) != 0 && !payAP.isComplete())
				{
					payAP.setPayAmt(getAmountTrf());
					if(!payAP.save())
					{
						m_processMsg = "Failed when trying update payment amount";
						return false;
					}
					MPayment payAR = new MPayment(getCtx(), getReference_ID(), get_TrxName());
					payAR.setPayAmt(getAmountTrf());
					if(!payAR.save())
					{
						m_processMsg = "Failed when trying update payment amount";
						return false;
					}	
				}
			}
			if(getC_Payment_ID() <= 0)
			{
				UNSBankTransfer bt = new UNSBankTransfer(getCtx(), "",
						getDescription(), getC_BPartner_ID(), getC_Currency_ID(), 0,
						getC_Charge_ID(), null, getAmountTrf(), getAccountFrom_ID(),
						getAccountTo_ID(), getDateAcct(), getDateAcct(), getAD_Org_ID(),
						getAccountTo().getAD_Org_ID(), false, get_TrxName());
		
				m_processMsg = bt.doIt();
				if (null != m_processMsg)
					return false;
		
				setC_Payment_ID(bt.getPaymentBankFrom_ID());
				setReference_ID(bt.getPaymentBankTo_ID());
			}
			
			MPayment paymentBankFrom = (MPayment) getC_Payment();
			if (!paymentBankFrom.isComplete() && 
					(!paymentBankFrom.processIt(MPayment.DOCACTION_Complete) || !paymentBankFrom.save())) {
				log.warning("Payment Process Failed: " + paymentBankFrom + " - "
						+ paymentBankFrom.getProcessMsg());
				m_processMsg = "Payment Process Failed: " + paymentBankFrom + " - "
						+ paymentBankFrom.getProcessMsg();
				return false;
			}
	
			MPayment paymentBankTo = (MPayment) getReference();
			if (!paymentBankTo.isComplete() &&
					(!paymentBankTo.processIt(MPayment.DOCACTION_Complete) || !paymentBankTo.save())) {
				log.warning("Payment Process Failed: " + paymentBankTo + " - "
						+ paymentBankTo.getProcessMsg());
				m_processMsg = "Payment Process Failed: " + paymentBankTo + " - "
						+ paymentBankTo.getProcessMsg();
				return false;
			}
			setProcessed(true);
		}
//		setProcessed(true);
		
		return true;
	} // prepareIt
	
	public String getProcessMsg(){return m_processMsg;}
	
	public MUNSBillingTrfDetail[] getDetail() {
		List<MUNSBillingTrfDetail> trfDetail = new Query(getCtx(),
				MUNSBillingTrfDetail.Table_Name,
				COLUMNNAME_UNS_BillingTrfValidation_ID + "=?", get_TrxName())
				.setParameters(get_ID()).setOrderBy(X_UNS_BillingTrf_Detail.COLUMNNAME_UNS_PaymentReceipt_ID + " DESC").list();

		return trfDetail.toArray(new MUNSBillingTrfDetail[trfDetail.size()]);
	}

	public BigDecimal TotalAmt() {
		BigDecimal totalAmtPay = Env.ZERO;
		BigDecimal totalAmtPayReceipt = Env.ZERO;
		
		String sqlAmtPay = "SELECT COALESCE(SUM(td.AmountTrf), 0) FROM UNS_BillingTrf_Detail td"
				+ " INNER JOIN UNS_BillingTrfValidation tv ON tv.UNS_BillingTrfValidation_ID = td.UNS_BillingTrfValidation_ID"
				+ " INNER JOIN UNS_PaymentReceipt pr ON pr.UNS_PaymentReceipt_ID = td.UNS_PaymentReceipt_ID"
				+ " AND pr.C_BankAccount_ID <> tv.AccountTo_ID"
				+ " WHERE tv.UNS_BillingTrfValidation_ID = ?";
		totalAmtPay = DB.getSQLValueBD(get_TrxName(), sqlAmtPay, get_ID());
		
		String sqlAmtPayReceipt = "SELECT COALESCE(SUM(td.AmountTrf), 0) FROM UNS_BillingTrf_Detail td"
				+ " INNER JOIN UNS_BillingTrfValidation tv ON tv.UNS_BillingTrfValidation_ID = td.UNS_BillingTrfValidation_ID"
				+ " INNER JOIN C_Payment p ON p.C_Payment_ID = td.C_Payment_ID"
				+ " AND p.C_BankAccount_ID <> tv.AccountTo_ID"
				+ " WHERE td.UNS_BillingTrfValidation_ID = ?";
		totalAmtPayReceipt = DB.getSQLValueBD(get_TrxName(), sqlAmtPayReceipt, get_ID());
		
		BigDecimal totalAmt = totalAmtPay.add(totalAmtPayReceipt);
		return totalAmt;
	}
		
	private void generatePayments(BigDecimal amount)
	{
		String sql = "SELECT p.C_Payment_ID, p.PayAmt FROM C_Payment p"
				+ " WHERE p.C_BankAccount_ID = ? AND p.IsReceipt = 'Y'"
				+ " AND p.DocStatus IN ('CO', 'CL') AND p.DateTrx BETWEEN ? AND ?";
		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		try
		{
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, getAccountFrom_ID());
			stmt.setTimestamp(2, getUNS_BillingTrf().getDateFrom());
			stmt.setTimestamp(3, getUNS_BillingTrf().getDateTo());
			rs = stmt.executeQuery();
			while (rs.next())
			{
				BigDecimal payAmt = rs.getBigDecimal(2);
				BigDecimal amt = Env.ZERO;
				if(amount.compareTo(payAmt) == 1)
					amt = payAmt;
				else
					amt = amount;
				
				MUNSBillingTrfDetail detail = MUNSBillingTrfDetail.getCreate(getCtx(), get_ID(), 
												rs.getInt(1), amt, get_TrxName());
				if(detail == null)
					continue;
				
				amount = amount.subtract(detail.getAmountTrf());
				if(amount.compareTo(Env.ZERO) == 0)
					break;
			}
		} 
		catch (Exception e)
		{
			
		}
	}
	
	private String allowedTransfer()
	{
		MUNSBillingTrf trf = new MUNSBillingTrf(getCtx(), getUNS_BillingTrf_ID(), get_TrxName());
		String sql1 = "SELECT COALESCE(SUM(btd.AmountTrf),0) FROM UNS_BillingTrf_Detail btd"
				+ " WHERE EXISTS (SELECT 1 FROM UNS_BillingTrfValidation btv WHERE btv.UNS_BillingTrfValidation_ID = "
				+ " btd.UNS_BillingTrfValidation_ID AND btv.UNS_BillingTrfValidation_ID <> ?"
				+ " AND btv.AccountFrom_ID = ? AND EXISTS (SELECT 1 FROM UNS_BillingTrf bt"
				+ " WHERE bt.UNS_BillingTrf_ID = btv.UNS_BillingTrf_ID"
				+ " AND bt.DocStatus NOT IN ('VO', 'RE'))) AND EXISTS (SELECT 1 FROM C_Payment p WHERE p.C_Payment_ID"
				+ " = btd.C_Payment_ID AND p.DateTrx BETWEEN ? AND ?)";
		BigDecimal amtTrf = DB.getSQLValueBD(get_TrxName(), sql1,
								new Object[]{get_ID(), getAccountFrom_ID(), trf.getDateFrom(), trf.getDateTo()});
		
		String sql2 = "SELECT COALESCE(SUM(p.PayAmt),0) FROM C_Payment p"
				+ " WHERE p.C_BankAccount_ID = ? AND p.IsReceipt = 'Y'"
				+ " AND p.DocStatus IN ('CO', 'CL') AND p.DateTrx BETWEEN ? AND ?";
		BigDecimal totalAmt = DB.getSQLValueBD(get_TrxName(), sql2,
				new Object[]{getAccountFrom_ID(), trf.getDateFrom(), trf.getDateTo()});
		
		BigDecimal remaining = totalAmt.subtract(amtTrf);
		if(getAmountTrf().compareTo(remaining) == 1)
		{
			DecimalFormat df = new DecimalFormat("#,###.00");
			return "Over Amount Transfer"
					+ "\n#Total Amount :: " + df.format(totalAmt)
					+ "\n#Amount Transferred :: "+ df.format(amtTrf)
					+ "\n#Remaining Amount :: " + df.format(remaining)
					+ "\n#Transfer Amount :: " + df.format(getAmountTrf())
					+ "\n#Over Amount :: " + df.format(getAmountTrf().subtract(remaining));
		}
		
		return null;
	}
}