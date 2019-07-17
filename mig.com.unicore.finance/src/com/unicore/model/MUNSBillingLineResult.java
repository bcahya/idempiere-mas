/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.unicore.ui.ISortTabRecord;

import com.unicore.model.factory.UNSFinanceModelFactory;
import com.uns.base.model.Query;
import com.uns.model.MUNSBillingLine;
import com.uns.model.MUNSCustomerBG;
import com.uns.model.MUNSCustomerBGAction;
import com.uns.model.MUNSCustomerBGInvList;

/**
 * @author setyaka
 * 
 */
public class MUNSBillingLineResult extends X_UNS_BillingLine_Result implements ISortTabRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3229932720624012744L;
	private MUNSBillingResult m_parent;
	private MUNSBillingLineGiro[] m_lines = null;
	public boolean m_force = false;

	/**
	 * @param ctx
	 * @param UNS_BillingLine_Confirm_ID
	 * @param trxName
	 */
	public MUNSBillingLineResult(Properties ctx, int UNS_BillingLine_Confirm_ID, String trxName) {
		super(ctx, UNS_BillingLine_Confirm_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBillingLineResult(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSBillingLineResult(MUNSBillingResult parent) {
		this(parent.getCtx(), 0, parent.get_TrxName());

		setClientOrg(parent);
		setUNS_Billing_Result_ID(parent.get_ID());
		setDifferenceAmt(Env.ZERO);
		setNetAmtToInvoice(Env.ZERO);
		setPaidAmt(Env.ZERO);
		setPaymentStatus(PAYMENTSTATUS_NotPaid);
	}
	
	@Override
	protected boolean afterDelete(boolean success)
	{
		return updateHeader();
	}
	
	protected boolean updateHeader()
	{
		MUNSBillingResult bc = (MUNSBillingResult) getUNS_Billing_Result();

		String sql = "SELECT SUM(NetAmtToInvoice) FROM UNS_BillingLine_Result WHERE UNS_Billing_Result_ID = ? AND IsActive='Y' ";
		BigDecimal totalLineNetAmt = DB.getSQLValueBD(get_TrxName(), sql, bc.getUNS_Billing_Result_ID());

		sql = "SELECT SUM(PaidAmt) FROM UNS_BillingLine_Result WHERE UNS_Billing_Result_ID = ? AND IsActive='Y' ";
		BigDecimal paidAmt = DB.getSQLValueBD(get_TrxName(), sql, bc.getUNS_Billing_Result_ID());

		sql = "SELECT SUM(PaidAmtGiro) FROM UNS_BillingLine_Result WHERE UNS_Billing_Result_ID = ? AND IsActive='Y'";
		BigDecimal paidAmtByGiro = DB.getSQLValueBD(get_TrxName(), sql, getUNS_Billing_Result_ID());
		
		sql = "SELECT SUM(ReceiptAmt) FROM UNS_BillingLine_Result WHERE UNS_Billing_Result_ID = ? AND IsActive= 'Y'";
		BigDecimal receiptAmt = DB.getSQLValueBD(get_TrxName(), sql, getUNS_Billing_Result_ID());
		
		sql = "SELECT SUM(ReceiptAmtGiro) FROM UNS_BillingLine_Result WHERE UNS_Billing_Result_ID = ? AND IsActive = 'Y'";
		BigDecimal receiptAmtGiro = DB.getSQLValueBD(get_TrxName(), sql, getUNS_Billing_Result_ID());
		
		sql = "SELECT SUM(AmountTrf) FROM UNS_BillingLine_Result WHERE UNS_Billing_Result_ID = ? AND IsActive = 'Y'";
		BigDecimal amountTrf = DB.getSQLValueBD(get_TrxName(), sql, getUNS_Billing_Result_ID());
		
		BigDecimal totalPaid = paidAmt.add(paidAmtByGiro).add(amountTrf);
		BigDecimal differenceAmt = totalLineNetAmt.subtract(totalPaid);

		sql = "UPDATE UNS_Billing_Result SET TotalAmt=" + totalLineNetAmt + ", paidAmt=" + paidAmt
				+ ", PaidAmtGiro = " + paidAmtByGiro + ", ReceiptAmt = " + receiptAmt 
				+ ", ReceiptAmtGiro = " + receiptAmtGiro + ", DifferenceAmt=" + differenceAmt
				+ ", AmountTrf = " + amountTrf
				+ " WHERE UNS_Billing_Result_ID ="+ bc.getUNS_Billing_Result_ID();

		int ok = DB.executeUpdate(sql, get_TrxName());
		if(ok == -1)
		{
			throw new AdempiereException("Failed when traying to update Billing Result");
		}

		updatePaymentReceiptGiro();
		
		return bc.updateHeader();
	}
	
	private void updatePaymentReceiptGiro()
	{
		int UNS_GroupingBilingResult_ID = getUNS_Billing_Result().getUNS_BillingGroup_Result_ID();
		MUNSPaymentReceipt pReceipt = MUNSPaymentReceipt.get(
				get_TrxName(), UNS_GroupingBilingResult_ID, getAD_Org_ID());
		if (null != pReceipt)
		{
			pReceipt.setReceiptAmtGiro(pReceipt.getReceiptAmtGiro().add(getReceiptAmtGiro()));
			pReceipt.save();
		}
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		return updateHeader();
	}

	public boolean isPaid() {
		// TODO Auto-generated method stub
		return getPaymentStatus().equalsIgnoreCase(PAYMENTSTATUS_Paid)
				|| getPaymentStatus().equals(PAYMENTSTATUS_PaidByCashAndGiro)
					|| getPaymentStatus().equals(PAYMENTSTATUS_PaidByTransfer);
	}

	public boolean isGiro() {
		// TODO Auto-generated method stub
		return getPaymentStatus().equalsIgnoreCase(PAYMENTSTATUS_PaidByGiro)
				|| getPaymentStatus().equals(PAYMENTSTATUS_PaidByCashAndGiro);
	}

	public boolean isHandover() {
		// TODO Auto-generated method stub
		return getPaymentStatus().equalsIgnoreCase(PAYMENTSTATUS_HandoverInvoice);
	}

	public boolean isNotPaid() {
		// TODO Auto-generated method stub
		return getPaymentStatus().equalsIgnoreCase(PAYMENTSTATUS_NotPaid);
	}

	public MUNSBillingResult getParent() {
		if (m_parent == null)
			m_parent = new MUNSBillingResult(getCtx(), getUNS_Billing_Result_ID(), get_TrxName());

		return m_parent;
	}
	
	
	protected boolean beforeSave(boolean newRecord)
	{
		if(!m_force && (getPaymentStatus().equals(PAYMENTSTATUS_PaidByCashAndGiro)
				|| getPaymentStatus().equals(PAYMENTSTATUS_PaidByGiro)))
		{
			validateGiro(newRecord);
		}
		
		if(getC_Invoice_ID() > 0)
		{
			setAD_Org_ID(getC_Invoice().getAD_Org_ID());
		}
		
		if(getPaymentStatus().equals(PAYMENTSTATUS_HandoverInvoice)
				|| getPaymentStatus().equals(PAYMENTSTATUS_NotPaid))
		{
			if(getPaidAmt().signum() != 0 || getPaidAmtGiro().signum() != 0 || getAmountTrf().signum() != 0)
			{
				throw new AdempiereUserError("You are define the payment status "
						+ "to Handover Invoice/Not Paid. Please set pay amount and pay amt giro to 0");
			}
		}
		else if(getPaymentStatus().equals(PAYMENTSTATUS_Paid))
		{
			if(getPaidAmt().signum() == 0)
				throw new AdempiereUserError("Please update paid amount");
			else if(getPaidAmtGiro().signum() != 0)
				throw new AdempiereUserError("Please update paid amount by Giro to 0");
			else if(getAmountTrf().signum() != 0)
				throw new AdempiereUserError("Please update amount transferred to 0");
		}
		else if (isGiro())
		{
			getLines(true);
			if(m_lines.length > 0 && getUNS_CustomerBG_ID() > 0)
			{
				throw new AdempiereUserError("You have define some giro on the Giro List tab"
						+ ". Leave blank giro number on Invoice Billing.");
			}
		}
		
		if(getPaymentStatus().equals(PAYMENTSTATUS_PaidByTransfer))
		{
			if(getAmountTrf().signum() == 0)
			{
				log.saveError("Error", "Please update Amount Transferred if you select Paid By Transfer");
				return false;
			}
			else if(getPaidAmt().signum() != 0 || getPaidAmtGiro().signum() != 0)
			{
				log.saveError("Error", "Please update Paid Amount OR Paid Amount Giro to ZERO if you select Paid By Transfer");
				return false;
			}
			else if(getReceiptDate() == null)
			{
				log.saveError("Error", "Please define date transaction");
				return false;
			}
		}
		
		if(getUNS_CustomerBG_ID() > 0)
		{
			setReceiptDate(getUNS_CustomerBG().getReceiptDate());
		}
		
		BigDecimal totalPaid = getPaidAmt().add(getPaidAmtGiro()).add(getAmountTrf()).add(getWriteOffAmt());
		BigDecimal diference = getNetAmtToInvoice().subtract(totalPaid);
		setDifferenceAmt(diference);
		
		if(!newRecord && isHasReceipt() && getC_PaymentAllocate_ID() > 0)
		{
			if(is_ValueChanged(COLUMNNAME_PaidAmt) || is_ValueChanged(COLUMNNAME_AmountTrf))
			{
				int C_Payment_ID = getC_PaymentAllocate().getC_Payment_ID();
				MPayment pay = new MPayment(getCtx(), C_Payment_ID, get_TrxName());
				String status = pay.getDocStatus();
				if("CO".equals(status) || status.equals("CL"))
				{
					log.saveError("Error", "Can't make changes as payment "
							+ "has been processed, please contact admin");
					return false;
				}
				String sql = "SELECT COUNT(*) FROM C_PaymentAllocate WHERE"
						+ " C_Payment_ID = ?";
				int count = DB.getSQLValue(get_TrxName(), sql, C_Payment_ID);
				if(count == 1)
				{
					setC_PaymentAllocate_ID(-1);
					if(!status.equals("VO") && !("RE").equals(status))
						pay.deleteEx(false, get_TrxName());
				}
				else
				{
					MPaymentAllocate all = new MPaymentAllocate(getCtx(), getC_PaymentAllocate_ID(), get_TrxName());
					setC_PaymentAllocate_ID(-1);
					all.deleteEx(false, get_TrxName());
				}
				setIsHasReceipt(false);
				setReceiptAmt(Env.ZERO);
				DB.executeUpdate("UPDATE UNS_Handover_Inv SET InvoiceCollectionType = 'HN'"
						+ " WHERE UNS_BillingLine_Result_ID = ?", get_ID(), get_TrxName());
			}
		}
		return super.beforeSave(newRecord);
	}
	
	public void validateGiro(boolean newRecord)
	{
		String prevPaymentStatus = (String) get_ValueOld(COLUMNNAME_PaymentStatus);
		boolean needCheck = prevPaymentStatus.equals(PAYMENTSTATUS_PaidByCashAndGiro)
				|| prevPaymentStatus.equals(PAYMENTSTATUS_PaidByGiro);
		
		if(getPaymentStatus().equals(PAYMENTSTATUS_PaidByCashAndGiro))
		{
			if(getPaidAmt().signum() == 0)
				throw new AdempiereUserError("Please update paid amount");
			else if(getPaidAmtGiro().signum() == 0 && getUNS_CustomerBG_ID() > 0)
				throw new AdempiereUserError("Please update paid amount by giro");
			
			if(getUNS_CustomerBG_ID() > 0)
			{
				String sql = "SELECT COALESCE(LimitAmt, 0) FROM UNS_CustomerBG WHERE UNS_CustomerBG_ID = ?";
				BigDecimal amtGiro = DB.getSQLValueBD(get_TrxName(), sql, getUNS_CustomerBG_ID());
				sql = "SELECT COALESCE(SUM(PaidAmtGiro), 0) FROM UNS_BillingLine_Result WHERE "
						+ " UNS_CustomerBG_ID = ? AND UNS_BillingLine_Result_ID <> ?";
				BigDecimal AmtUsed = DB.getSQLValueBD(get_TrxName(), sql, getUNS_CustomerBG_ID(), get_ID());
				sql = "SELECT COALESCE(SUM(PaidAmtGiro), 0) FROM UNS_BillingLine_Giro WHERE "
						+ " UNS_CustomerBG_ID = ?";
				AmtUsed = AmtUsed.add(DB.getSQLValueBD(get_TrxName(), sql, getUNS_CustomerBG_ID()));
				BigDecimal unallocated = amtGiro.subtract(AmtUsed);
				
				if(getPaidAmtGiro().compareTo(unallocated) > 0)
				{
					throw new AdempiereUserError("Over amount giro. unallocated amount giro : " + unallocated);
				}
			}
			else
			{
				if(newRecord || !needCheck)
				{
					return;
				}
				
				MUNSBillingLineGiro[] giroList = getLines(true);
				if(giroList.length == 0)
				{
					throw new AdempiereUserError(Msg.getMsg(getCtx(), "Not defined giro"));
				}
				
				for(int i=0; i<giroList.length; i++)
				{
					giroList[i].validateGiro(newRecord);
				}
			}
		}
		else if(getPaymentStatus().equals(PAYMENTSTATUS_PaidByGiro))
		{
			if(getPaidAmtGiro().signum() == 0 && getUNS_CustomerBG_ID() > 0)
				throw new AdempiereUserError("Please update paid amount by giro");
			else if(getPaidAmt().signum() != 0)
				throw new AdempiereUserError("Please set paid amount to 0");
			
			if(getUNS_CustomerBG_ID() > 0)
			{
				String sql = "SELECT COALESCE(LimitAmt, 0) FROM UNS_CustomerBG WHERE UNS_CustomerBG_ID = ?";
				BigDecimal amtGiro = DB.getSQLValueBD(get_TrxName(), sql, getUNS_CustomerBG_ID());
				sql = "SELECT COALESCE(SUM(PaidAmtGiro), 0) FROM UNS_BillingLine_Result WHERE "
						+ " UNS_CustomerBG_ID = ? AND UNS_BillingLine_Result_ID <> ?";
				BigDecimal AmtUsed = DB.getSQLValueBD(get_TrxName(), sql, getUNS_CustomerBG_ID(), get_ID());
				sql = "SELECT COALESCE(SUM(PaidAmtGiro), 0) FROM UNS_BillingLine_Giro WHERE "
						+ " UNS_CustomerBG_ID = ?";
				AmtUsed = AmtUsed.add(DB.getSQLValueBD(get_TrxName(), sql, getUNS_CustomerBG_ID()));
				BigDecimal unallocated = amtGiro.subtract(AmtUsed);
				
				if(getPaidAmtGiro().compareTo(unallocated) > 0)
				{
					throw new AdempiereUserError("Over amount giro. unallocated amount giro : " + unallocated);
				}
			}
			else
			{
				if(newRecord || !needCheck)
				{
					return;
				}
				
				MUNSBillingLineGiro[] giroList = getLines(true);
				if(giroList.length == 0)
				{
					throw new AdempiereUserError(Msg.getMsg(getCtx(), "Not defined giro"));
				}
				
				for(int i=0; i<giroList.length; i++)
				{
					giroList[i].validateGiro(newRecord);
				}
			}
		}
		
	}

	@Override
	public String beforeSaveTabRecord(int parentRecord_ID) {
		MUNSBillingLine bl = (MUNSBillingLine) getUNS_BillingLine();
		setC_Invoice_ID(bl.getC_Invoice_ID());
		if(getUNS_Billing_Result().getUNS_BillingGroup_Result().isAutoGenerated())
			setNetAmtToInvoice(bl.getNetAmtToInvoice());
		else
			setNetAmtToInvoice(DB.getSQLValueBD(get_TrxName(), "SELECT invoiceopen(?,0)", bl.getC_Invoice_ID()));
		setPaymentStatus(PAYMENTSTATUS_NotPaid);
		setPaidAmt(Env.ZERO);
		setPaidAmtGiro(Env.ZERO);
		setReceiptAmt(Env.ZERO);
		setReceiptAmtGiro(Env.ZERO);
		setDifferenceAmt(Env.ZERO);
		return null;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSBillingLineGiro[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSBillingLineGiro> list = Query.get(
				getCtx(), UNSFinanceModelFactory.EXTENSION_ID, MUNSBillingLineGiro.Table_Name
				, Table_Name + "_ID = ?", get_TrxName()).setParameters(get_ID()).list();
		
		m_lines = new MUNSBillingLineGiro[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	public void createCustomerBGFromList()
	{
		getLines(true);
		for(int i=0; i<m_lines.length; i++)
		{
			MUNSCustomerBG giro = new MUNSCustomerBG(getCtx(), m_lines[i].getUNS_CustomerBG_ID(), get_TrxName());

			MUNSCustomerBGInvList invlist = MUNSCustomerBGInvList.get(get_ID(), giro.get_ID(), get_TrxName());
			if(null == invlist)
			{
				invlist = new MUNSCustomerBGInvList(giro);
				invlist.setUNS_BillingLine_Result_ID(get_ID());
				invlist.setNetAmtToInvoice(getNetAmtToInvoice());
				invlist.setPaidAmt(m_lines[i].getPaidAmtGiro());
				invlist.setC_Invoice_ID(getC_Invoice_ID());

				if (!invlist.save())
					throw new AdempiereException("Error while trying create Invoice Billed Giro");
			}
			
			if (giro.getLinesBGAction(true)== null || giro.getLinesBGAction(false).length <= 0)
			{
				MUNSCustomerBGAction action = new MUNSCustomerBGAction(giro);
				action.saveEx();
			}
		}
	}

	@Override
	public String beforeRemoveSelection() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
