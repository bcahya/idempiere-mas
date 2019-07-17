package com.uns.model.callout;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.base.IColumnCallout;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrgInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.unicore.model.I_UNS_BillingLine_Result;
import com.unicore.model.MUNSBillingResult;
import com.unicore.model.X_UNS_BillingLine_Result;
import com.uns.model.MUNSBillingLine;
import com.uns.model.MUNSCustomerBG;
import com.uns.model.X_UNS_Billing;
import com.uns.model.X_UNS_BillingLine;

/**
 *	Billing Callouts	
 *	
 *  @author YAKA
 */
public class CalloutBilling extends CalloutEngine implements IColumnCallout
{
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String bPartner (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_BPartner_ID = (Integer)value;
		if (C_BPartner_ID == null || C_BPartner_ID.intValue() == 0)
			return "";

		String sql = "SELECT p.C_PaymentTerm_ID,l.C_BPartner_Location_ID "
			+ " FROM C_BPartner p "
			+ " LEFT OUTER JOIN C_BPartner_Location l "
			+ " ON (p.C_BPartner_ID=l.C_BPartner_ID AND l.IsBillTo='Y' AND l.IsActive='Y') "
			+ " WHERE p.C_BPartner_ID=? AND p.IsActive='Y'";		//	#1

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_BPartner_ID.intValue());
			rs = pstmt.executeQuery();
			//
			if (rs.next())
			{
				//	Location
				int locID = rs.getInt("C_BPartner_Location_ID");
				if (C_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String loc = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_Location_ID");
					if (loc.length() > 0)
						locID = Integer.parseInt(loc);
				}
				if (locID == 0)
					mTab.setValue("C_BPartner_Location_ID", null);
				else
					mTab.setValue("C_BPartner_Location_ID", new Integer(locID));
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "bPartner", e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "";
	}	//	bPartner	
	
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String clear (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		mTab.setValue(mField.getColumnName(), null);
		return "";
	}

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		String retValue = null;
		
		if (X_UNS_Billing.COLUMNNAME_C_BPartner_ID.equals(mField.getColumnName()))
		{
			retValue = bPartner(ctx, WindowNo, mTab, mField, value);
		}
		else if(X_UNS_BillingLine.COLUMNNAME_C_Invoice_ID.equals(mField.getColumnName()))
		{
			retValue = C_Invoic_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if(mTab.getTableName().equals("UNS_BillingLine_Result") && mField.getColumnName().equals("UNS_BillingLine_ID"))
		{
			retValue = UNS_BillingLine_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if(mTab.getTableName().equals("UNS_BillingLine_Result") && mField.getColumnName().equals("UNS_CustomerBG_ID"))
		{
			retValue = UNS_CustomerBG_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if(X_UNS_BillingLine_Result.COLUMNNAME_PaymentStatus.equals(mField.getColumnName()))
		{
			retValue = PaymentStatus(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if(X_UNS_BillingLine_Result.COLUMNNAME_PaidAmt.equals(mField.getColumnName()))
		{
			retValue = PaidAmt(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if(X_UNS_BillingLine_Result.COLUMNNAME_PaidAmtGiro.equals(mField.getColumnName()))
		{
			retValue = PaidAmtGiro(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		else if(X_UNS_BillingLine_Result.COLUMNNAME_WriteOffAmt.equals(mField.getColumnName()))
		{
			retValue = writeOff(ctx, WindowNo, mTab, mField, value, oldValue);
		}
		
		return retValue;
	}
	
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String C_Invoic_ID(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue)
	
	{
		if(null == value)
			return null;
		
//		MUNSBillingResult br = new MUNSBillingResult(ctx, (Integer) mTab.getValue("UNS_Billing_Result_ID"), null);
		int C_Invoice_ID = (Integer) value;
		MInvoice invoice = new MInvoice(ctx, C_Invoice_ID, null);
		Timestamp dateInvoiced = invoice.getDateInvoiced();
		I_C_PaymentTerm paymentTerm = invoice.getC_PaymentTerm();
		if(null == paymentTerm)
			throw new AdempiereException("Invoice is not have payment term");
		
		int duedays = paymentTerm.getNetDays();
		Timestamp dueDate = TimeUtil.addDays(dateInvoiced, duedays);
		mTab.setValue(MUNSBillingLine.COLUMNNAME_DateInvoiced, dateInvoiced);
		mTab.setValue(MUNSBillingLine.COLUMNNAME_DueDate, dueDate);
		mTab.setValue(MUNSBillingLine.COLUMNNAME_C_PaymentTerm_ID, invoice.getC_PaymentTerm_ID());
//		if(br.getUNS_BillingGroup_Result().isAutoGenerated())
			mTab.setValue(MUNSBillingLine.COLUMNNAME_NetAmtToInvoice, invoice.getGrandTotal());
//		else
//			mTab.setValue(MUNSBillingLine.COLUMNNAME_NetAmtToInvoice, DB.getSQLValueBD(null, "SELECT invoiceopen(?,0)",
//					C_Invoice_ID));
		mTab.setValue(MUNSBillingLine.COLUMNNAME_Description, invoice.getDescription());
		mTab.setValue(MUNSBillingLine.COLUMNNAME_SalesRep_ID, invoice.getSalesRep_ID());
		
		return null;
	}
	
	/**
	 * Untuk billing result.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String UNS_BillingLine_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
			return null;
		MUNSBillingResult br = new MUNSBillingResult(ctx, (Integer) mTab.getValue("UNS_Billing_Result_ID"), null);
		X_UNS_BillingLine bill = new X_UNS_BillingLine(ctx, (Integer) value, null);
		if(br.getUNS_BillingGroup_Result().isAutoGenerated())
			mTab.setValue(MUNSBillingLine.COLUMNNAME_NetAmtToInvoice, bill.getC_Invoice().getGrandTotal());
		else
			mTab.setValue(MUNSBillingLine.COLUMNNAME_NetAmtToInvoice, DB.getSQLValueBD(null, "SELECT invoiceopen(?,0)",
					bill.getC_Invoice_ID()));
		mTab.setValue("C_Invoice_ID", bill.getC_Invoice_ID());
		return null;
	}
	
	public String UNS_CustomerBG_ID(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
		{
			mTab.setValue(X_UNS_BillingLine_Result.COLUMNNAME_ReceiptDate, null);
			return null;
		}
		
		MUNSCustomerBG bg = new MUNSCustomerBG(ctx, (Integer) value, null);
		mTab.setValue(X_UNS_BillingLine_Result.COLUMNNAME_ReceiptDate, bg.getReceiptDate());
		
		return null;
	}
	
	public String PaymentStatus(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
			return null;
		BigDecimal nol = Env.ZERO;
		BigDecimal netAmt = (BigDecimal) mTab.getValue(X_UNS_BillingLine_Result.COLUMNNAME_NetAmtToInvoice);
		
		if(value.equals(X_UNS_BillingLine_Result.PAYMENTSTATUS_Paid))
		{
			mTab.setValue("PaidAmt", netAmt);
			mTab.setValue("PaidAmtGiro", nol);
			mTab.setValue("AmountTrf", nol);
			mTab.setValue("WriteOffAmt", nol);
			mTab.setValue("DifferenceAmt", nol);
		}
		
		if(value.equals(X_UNS_BillingLine_Result.PAYMENTSTATUS_NotPaid))
		{
			mTab.setValue("PaidAmt", nol);
			mTab.setValue("PaidAmtGiro", nol);
			mTab.setValue("AmountTrf", nol);
			mTab.setValue("WriteOffAmt", nol);
			mTab.setValue("DifferenceAmt", netAmt);
		}
		
		if(value.equals(X_UNS_BillingLine_Result.PAYMENTSTATUS_PaidByGiro))
		{
			mTab.setValue("PaidAmtGiro", netAmt);
			mTab.setValue("PaidAmt", nol);
			mTab.setValue("AmountTrf", nol);
			mTab.setValue("WriteOffAmt", nol);
			mTab.setValue("DifferenceAmt", nol);
		}
		
		if(value.equals(X_UNS_BillingLine_Result.PAYMENTSTATUS_PaidByCashAndGiro))
		{
			mTab.setValue("PaidAmtGiro", nol);
			mTab.setValue("PaidAmt", nol);
			mTab.setValue("AmountTrf", nol);
			mTab.setValue("WriteOffAmt", nol);
			mTab.setValue("DifferenceAmt", nol);
		}
		
		if(value.equals(X_UNS_BillingLine_Result.PAYMENTSTATUS_PaidByTransfer))
		{
			mTab.setValue("PaidAmtGiro", nol);
			mTab.setValue("PaidAmt", nol);
			mTab.setValue("AmountTrf", netAmt);
			mTab.setValue("WriteOffAmt", nol);
			mTab.setValue("DifferenceAmt", nol);
		}
		
		return null;
	}
	
	public String PaidAmt(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal val = value == null ? Env.ZERO : (BigDecimal) value;
		I_UNS_BillingLine_Result line = GridTabWrapper.create(mTab, I_UNS_BillingLine_Result.class);
//		if(val.signum() == 0)
//		{
//			if(line.getPaidAmtGiro().signum() != 0)
//				line.setPaymentStatus(X_UNS_BillingLine_Result.PAYMENTSTATUS_PaidByGiro);
//			else
//			{
//				line.setPaymentStatus(X_UNS_BillingLine_Result.PAYMENTSTATUS_NotPaid);
//				mTab.setValue("PaidAmt", Env.ZERO);
//				mTab.setValue("PaidAmtGiro", Env.ZERO);
//				mTab.setValue("AmountTrf", Env.ZERO);
//				mTab.setValue("WriteOffAmt", Env.ZERO);
//				mTab.setValue("DifferenceAmt", line.getNetAmtToInvoice());
//			}
//		}
//		else
//		{
//			if(line.getPaidAmtGiro().signum() == 0)
//				line.setPaymentStatus(X_UNS_BillingLine_Result.PAYMENTSTATUS_Paid);
//			else
//				line.setPaymentStatus(X_UNS_BillingLine_Result.PAYMENTSTATUS_PaidByCashAndGiro);
//		}
		
		BigDecimal totalPaid = val.add(line.getPaidAmtGiro()).
									add(line.getAmountTrf()).add(line.getWriteOffAmt());
		BigDecimal diference = line.getNetAmtToInvoice().subtract(totalPaid);
		line.setDifferenceAmt(diference);
		return null;
	}
	
	public String PaidAmtGiro(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		BigDecimal val = value == null ? Env.ZERO : (BigDecimal) value;
		I_UNS_BillingLine_Result line = GridTabWrapper.create(mTab, I_UNS_BillingLine_Result.class);
//		if(val.signum() == 0)
//		{
//			if(line.getPaidAmt().signum() != 0)
//				line.setPaymentStatus(X_UNS_BillingLine_Result.PAYMENTSTATUS_Paid);
//			else
//			{
//				line.setPaymentStatus(X_UNS_BillingLine_Result.PAYMENTSTATUS_NotPaid);
//				mTab.setValue("PaidAmt", Env.ZERO);
//				mTab.setValue("PaidAmtGiro", Env.ZERO);
//				mTab.setValue("AmountTrf", Env.ZERO);
//				mTab.setValue("WriteOffAmt", Env.ZERO);
//				mTab.setValue("DifferenceAmt", line.getNetAmtToInvoice());
//			}
//		}
//		else
//		{
//			if(line.getPaidAmt().signum() == 0)
//				line.setPaymentStatus(X_UNS_BillingLine_Result.PAYMENTSTATUS_PaidByGiro);
//			else
//				line.setPaymentStatus(X_UNS_BillingLine_Result.PAYMENTSTATUS_PaidByCashAndGiro);
//		}
		
		BigDecimal totalPaid = line.getPaidAmt().add(val).
				add(line.getAmountTrf()).add(line.getWriteOffAmt());
		BigDecimal diference = line.getNetAmtToInvoice().subtract(totalPaid);
		line.setDifferenceAmt(diference);
		
		return null;
	}
	
	private String writeOff(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		I_UNS_BillingLine_Result line = GridTabWrapper.create(mTab, I_UNS_BillingLine_Result.class);
		MOrgInfo info = MOrgInfo.get(ctx, line.getAD_Org_ID(), true, null);
		BigDecimal val = (BigDecimal) value;
		if(info.getMaxWriteOffSales().signum() > 0 &&
				info.getMaxWriteOffSales().compareTo(val) == -1)
		{
			mTab.fireDataStatusEEvent("Over Amount",
					"Allowed write off amount is " + info.getMaxWriteOffSales().setScale(2)
					+ " or lower",
					false);
			line.setWriteOffAmt((BigDecimal) oldValue);
			return null;
		}
		String payStatus = line.getPaymentStatus();
		BigDecimal old = (BigDecimal) oldValue;
		if(null == value || (BigDecimal) value == Env.ZERO)
		{
			line.setDifferenceAmt((BigDecimal) oldValue);
		}
		else
		{
			if(line.getDifferenceAmt().signum() == 0)
			{
				if(payStatus.equals(X_UNS_BillingLine_Result.PAYMENTSTATUS_NotPaid))
					line.setWriteOffAmt(Env.ZERO);
				else if(payStatus.equals(X_UNS_BillingLine_Result.PAYMENTSTATUS_Paid))
					line.setPaidAmt((line.getPaidAmt().add(old)).subtract(val));
				else if(payStatus.equals(X_UNS_BillingLine_Result.PAYMENTSTATUS_PaidByGiro))
					line.setPaidAmtGiro((line.getPaidAmtGiro().add(old)).subtract(val));
				else if(payStatus.equals(X_UNS_BillingLine_Result.PAYMENTSTATUS_PaidByTransfer))
					line.setAmountTrf((line.getAmountTrf().add(old)).subtract(val));
			}
			else
			{
				if(payStatus.equals(X_UNS_BillingLine_Result.PAYMENTSTATUS_NotPaid))
					line.setWriteOffAmt(Env.ZERO);
				else
					line.setDifferenceAmt((line.getDifferenceAmt().add(old)).subtract(val));
			}
		}
		
		return null;
	}
}
