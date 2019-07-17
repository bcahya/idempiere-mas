package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.MInvoice;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import com.unicore.ui.ISortTabRecord;

/**
 *	Model class for BillingLine
 *	
 *  @author Kitti U. - Ecosoft Co., Ltd.
 *	@version $Id: MUNSBillingLine.java, v1 2010/12/31 06:53:26 ktu Exp $
 */

public class MUNSBillingLine extends X_UNS_BillingLine implements ISortTabRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MUNSBillingLine(Properties ctx, int UNS_Billing_Line_ID,
			String trxName) {
		super(ctx, UNS_Billing_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSBillingLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	protected boolean afterDelete(boolean success) {
		updateHeaderAmount();
		return true;
	}

	protected boolean afterSave(boolean newRecord, boolean success) {
		updateHeaderAmount();
		return true;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		if(getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(" + COLUMNNAME_Line + "), 0) FROM " + Table_Name
					+ " WHERE " + COLUMNNAME_UNS_Billing_ID + "= ?";
			int max = DB.getSQLValue(get_TrxName(), sql, getUNS_Billing_ID());
			setLine(max+10);
		}
		
		I_C_PaymentTerm paymentTerm = (I_C_PaymentTerm) getC_PaymentTerm();
		int dueDay = paymentTerm.getNetDays();
		Timestamp duedate = TimeUtil.addDays(getDateInvoiced(), dueDay);
		setDueDate(duedate);
		if(getC_Invoice_ID() > 0)
		{
			setAD_Org_ID(getC_Invoice().getAD_Org_ID());
		}
		return super.beforeSave(newRecord);
	}

	private boolean updateHeaderAmount(){
		
		MUNSBilling billHdr = getParent();
		
		// Find Total Bill Amount 
		String sql = "SELECT COALESCE(SUM(NetAmtToInvoice), 0) FROM UNS_BillingLine WHERE UNS_Billing_ID = ? AND IsActive='Y' ";
		BigDecimal totalLineNetAmt = DB.getSQLValueBD(get_TrxName(), sql, billHdr.getUNS_Billing_ID());
		
		sql = "SELECT COALESCE(SUM(ImportDutyAmt), 0) FROM UNS_BillingLine WHERE UNS_Billing_ID = ? AND IsActive='Y' ";
		BigDecimal importDuty = DB.getSQLValueBD(get_TrxName(), sql, billHdr.getUNS_Billing_ID());
		
		sql = "SELECT COALESCE(SUM(PPh22Import), 0) FROM UNS_BillingLine WHERE UNS_Billing_ID = ? AND IsActive='Y' ";
		BigDecimal pph22Import = DB.getSQLValueBD(get_TrxName(), sql, billHdr.getUNS_Billing_ID());
		
		billHdr.setImportDutyAmt(importDuty);
		billHdr.setPPh22Import(pph22Import);
		billHdr.setGrandTotal(totalLineNetAmt);
		billHdr.setTotalAmt(totalLineNetAmt);
		
		if(!billHdr.save(get_TrxName())){
			log.saveError("Cannot Update Billing Header", "Cannot Save Billing Header");
			return false;
		}

		return true;
	}
	
	private MUNSBilling parent = null ;
	
	public MUNSBilling getParent(){
		if(parent == null)
			parent = MUNSBilling.getBilling(getCtx(), getUNS_Billing_ID(), get_TrxName());
		
		return parent;
	}

	@Override
	public String beforeSaveTabRecord(int parentRecord_ID) {
		if(getC_Invoice_ID() <= 0)
			return null;
		
		MInvoice invoice = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
		MUNSBilling billing = new MUNSBilling(getCtx(), getUNS_Billing_ID(), get_TrxName());
		setAD_Org_ID(billing.getAD_Org_ID());
		setDateInvoiced(invoice.getDateInvoiced());
		setC_PaymentTerm_ID(invoice.getC_PaymentTerm_ID());
		setNetAmtToInvoice(invoice.getGrandTotal());
		return null;
	}

	@Override
	public String beforeRemoveSelection() {
		// TODO Auto-generated method stub
		return null;
	}
}