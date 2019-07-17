/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.GridTab;
import org.compiere.model.MBankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.unicore.ui.ISortTabRecord;

import com.uns.util.UNSApps;

/**
 * @author root
 *
 */
public class MUNSChargeDetail extends X_UNS_Charge_Detail implements ISortTabRecord{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7618161426501241009L;
	private MUNSChargeDetail m_reference = null;
	private PO m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_Charge_Detail_ID
	 * @param trxName
	 */
	public MUNSChargeDetail(Properties ctx, int UNS_Charge_Detail_ID,
			String trxName) {
		super(ctx, UNS_Charge_Detail_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSChargeDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @param parent
	 */
	public MUNSChargeDetail(PO parent)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		set_Value(parent.get_TableName()+"_ID", parent.get_ID());
		setClientOrg(parent);
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public static MUNSChargeDetail[] gets(PO po)
	{
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(Table_Name)
				.append(" WHERE ").append(po.get_TableName()).append("_ID = ? AND ")
				.append(COLUMNNAME_IsActive).append(" = ? ");
		String sql = sb.toString();
		
		List<MUNSChargeDetail> list = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, po.get_TrxName());
			st.setInt(1, po.get_ID());
			st.setString(2, "Y");
			rs = st.executeQuery();
			while (rs.next())
			{
				MUNSChargeDetail cd = new MUNSChargeDetail(po.getCtx(), rs, po.get_TrxName());
				cd.m_parent = po;
				list.add(cd);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			DB.close(rs, st);
		}
		
		MUNSChargeDetail[] records = new MUNSChargeDetail[list.size()];
		list.toArray(records);
		
		return records;
	}
	
	/**
	 * Get reference charge detail
	 * @return {@link MUNSChargeDetail}
	 */
	public MUNSChargeDetail getReference()
	{
		if(null != m_reference)
		{
			m_reference.set_TrxName(get_TrxName());
			return m_reference;
		}
		
		m_reference = new MUNSChargeDetail(getCtx(), getReference_ID(), get_TrxName());
		return m_reference;
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getRequestAmt()
	{
		BigDecimal amt = getReference().getAmountConfirmed();
		return amt == null ? Env.ZERO : amt;
	}
	
	/**
	 * Get amount source (this amount confirmed - reference amount confirmed)
	 * @return {@link BigDecimal} amount confirmed - reference amount confirmed
	 */
	public BigDecimal getAmtSource()
	{
		return getAmountConfirmed().subtract(getRequestAmt());
	}
	
	/**
	 * Update Header {UNS__Charge_Confirmation, UNS_Charge_RS, UNS_Shipping}
	 * @return boolean success ? true : false;
	 */
	protected boolean updateHeader()
	{
		String[] headers = {
				COLUMNNAME_UNS_Charge_Confirmation_ID,
				COLUMNNAME_UNS_Charge_RS_ID,
				COLUMNNAME_UNS_Shipping_ID
				};
		
		StringBuilder sb = new StringBuilder("UPDATE ").append("@Param1@")
				.append(" SET ").append(MUNSChargeRS.COLUMNNAME_GrandTotal)
				.append("=(SELECT COALESCE(SUM (").append("@Param4@")
				.append("), 0) FROM ").append(Table_Name).append(" WHERE ")
				.append("@Param2@=@Param1@.@Param2@ AND ").append(COLUMNNAME_IsActive).append("=?)")
				.append(" WHERE ").append("@Param3@=?");
		boolean ok = true;
		
		for(int i=0; i<headers.length; i++)
		{
			String sql = sb.toString();
			sql = sql.replace("@Param1@", headers[i].replace("_ID", ""));
			sql = sql.replace("@Param2@", headers[i]);
			sql = sql.replace("@Param3@", headers[i]);
			
			if (headers[i].equals(COLUMNNAME_UNS_Charge_Confirmation_ID))
				sql = sql.replace("@Param4@", COLUMNNAME_AmountConfirmed);
			else 
				sql = sql.replace("@Param4@", COLUMNNAME_Amount);
			
			int result = DB.executeUpdate(
					sql, 
					new Object[] {"Y", get_Value(headers[i])}, 
					false, 
					get_TrxName());
			if(result == -1)
			{
				ok = false;
				break;
			}
		}
		
		return ok;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getChargeType().equals(CHARGETYPE_Voucher) && !isSettlement() 
				&& (newRecord || is_ValueChanged(COLUMNNAME_UNS_Voucher_ID)))
		{
			if(getUNS_Voucher_ID() == 0)
			{
				throw new AdempiereUserError("Please define voucher book first");
			}
			
			MUNSVoucher voucher = null;
			MUNSVoucherCode voucherCode = null;
			
			if(getUNS_Voucher_Code_ID() > 0)
			{
				voucherCode = new MUNSVoucherCode(getCtx(), getUNS_Voucher_Code_ID(), get_TrxName());
				voucher = voucherCode.getParent();
			}
			else
			{
				voucher = new MUNSVoucher(getCtx(), getUNS_Voucher_ID(), get_TrxName());
				voucherCode = voucher.getUnusedVoucher();
			}
			
			if(null != voucherCode && voucherCode.isUsed())
			{
				voucherCode = voucher.getUnusedVoucher();
			}
			
			if(null == voucherCode)
			{
				throw new AdempiereUserError(
						"The voucher book hasn't have unused voucher");
			}
			
			setUNS_Voucher_Code_ID(getUNS_Voucher_Code_ID());
			setVoucherCode(voucherCode.getName());
			
			if(is_ValueChanged(COLUMNNAME_UNS_Voucher_Code_ID))
			{
				doUseVoucher();
			}
			if(voucher.getVoucherType().equals(MUNSVoucher.VOUCHERTYPE_FuelVoucher))
			{
				MUNSVoucherFuel fuel = voucher.getDefaultFuel();
				if(null != fuel)
				{
					setUNS_Fuel_ID(fuel.get_ID());
				}
			}
			
			if(voucher.getVoucherType().equals(MUNSVoucher.VOUCHERTYPE_FuelVoucher) && getUNS_Fuel_ID() == 0)
			{
				throw new AdempiereUserError("Fuel is mandatory");
			}
		}
		
		if(is_ValueChanged(COLUMNNAME_UNS_Voucher_Code_ID))
		{
			int oldVoucher_id = get_ValueOldAsInt(COLUMNNAME_UNS_Voucher_Code_ID);
			if(oldVoucher_id > 0)
			{
				doCancelVoucher(oldVoucher_id);
			}
		}
		
		if(!newRecord && is_ValueChanged("C_InvoiceLine_ID"))
		{
			if(get_ValueAsInt("C_InvoiceLine_ID") > 0)
			{
				MInvoiceLine line = new MInvoiceLine(getCtx(), (Integer) get_Value("C_InvoiceLine_ID"), get_TrxName());
				if(line.isProcessed())
					throw new AdempiereException("line has processed");
				String sql = "SELECT SUM(cd.Liters) FROM UNS_Charge_Detail cd WHERE cd.C_InvoiceLine_ID=?";
				BigDecimal liters = DB.getSQLValueBD(get_TrxName(), sql, line.get_ID());
				if(liters == null)
					liters = Env.ZERO;
				if(liters.signum() == 0)
				{
					sql = "SELECT COUNT(*) FROM UNS_Charge_Detail cd WHERE cd.C_InvoiceLine_ID=?";
					liters = DB.getSQLValueBD(get_TrxName(), sql, line.get_ID());
				}
				line.setQtyEntered(liters.add(getLiters().signum() == 0 ? Env.ONE : getLiters()));
				line.saveEx();
			}
			else
			{
				MInvoiceLine line = new MInvoiceLine(getCtx(), get_ValueOldAsInt("C_InvoiceLine_ID"), get_TrxName());
				BigDecimal liters = getLiters().signum() == 0 ? Env.ONE : getLiters();
				line.setQty(line.getQtyEntered().subtract(liters));
				line.saveEx();
			}
		}
		
//		boolean ok = getAmountConfirmed().compareTo(getAmount()) <= 0;
//		if(!ok && !is)
//		{
//			throw new AdempiereUserError(
//					Msg.getMsg("", "Amount Confirmed can't greater than Amount"));
//		}
//		
//		if(getChargeType().equals(CHARGETYPE_Cost) && getAmount().signum() == 0
//				&& getReference_ID() == 0)
//		{
//			throw new AdempiereUserError("Zero / null amount is not allowed");
//		}
		
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if(!success)
			return super.afterSave(newRecord, success);
		else if(!updateHeader())
			throw new AdempiereException(Msg.getMsg("", "Failed when update header"));
		
		return super.afterSave(newRecord, success);
	}
	
	@Override
	protected boolean beforeDelete()
	{
		if(getChargeType().equals(CHARGETYPE_Voucher))
		{
			doCancelVoucher();
		}
		return super.beforeDelete();
	}
	
	/**
	 * Create statement 
	 * @param stmt
	 * @return null when success or error message on failed to create statement line.
	 */
	public String doCreateStatementLine(MBankStatement stmt, Timestamp dateTrx)
	{
		MBankStatementLine stmtLine = null;
		if(!getChargeType().equals(CHARGETYPE_Cost))
			return "";
		else if(getC_BankStatementLine_ID() > 0)
			stmtLine = new MBankStatementLine(getCtx(), getC_BankStatementLine_ID(), get_TrxName());
		else
			stmtLine = new MBankStatementLine(stmt);
		try
		{
			int C_BPartner_ID = getC_BPartner_ID();
			if (C_BPartner_ID == 0)
			{
				C_BPartner_ID = getParent().get_ValueAsInt("C_BPartner_ID");
			}
			stmtLine.setC_BPartner_ID(C_BPartner_ID);
			int C_Charge_ID = UNSApps.getRefAsInt(UNSApps.CHRG_INTRANSITCASH);			
			BigDecimal amtSrc = getAmtSource().negate();
			String transactionType = MBankStatementLine.TRANSACTIONTYPE_APTransaction;
			
			if(amtSrc.signum() == 1)
			{
				transactionType = MBankStatementLine.TRANSACTIONTYPE_ARTransaction;
			}
		
			stmtLine.setChargeAmt(amtSrc);
			stmtLine.setStmtAmt(amtSrc);
			stmtLine.setTransactionType(transactionType);
			stmtLine.setAmount(amtSrc.signum() == -1 ? amtSrc.negate() : amtSrc);
			stmtLine.setChargeAmt(amtSrc);
			stmtLine.setStmtAmt(amtSrc);
			stmtLine.setDateAcct(dateTrx);
			stmtLine.setEftValutaDate(dateTrx);
			stmtLine.setStatementLineDate(dateTrx);
			stmtLine.setEftStatementLineDate(dateTrx);
			stmtLine.setC_Currency_ID(((MUNSChargeConfirmation) getParent()).getC_Currency_ID());
			stmtLine.setC_Charge_ID(C_Charge_ID);
			stmtLine.setDescription(getDescription());
			stmtLine.saveEx();	
			setC_BankStatementLine_ID(stmtLine.get_ID());
			saveEx();
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
		
		return "";
	}
	
	
	/**
	 * Set confirmed amt and confirmation ID
	 * @param UNS_Charge_Confirmation_ID
	 */
	public void onConfirmationCreated(int UNS_Charge_Confirmation_ID)
	{
		setAmountConfirmed(getAmount());
		setUNS_Charge_Confirmation_ID(UNS_Charge_Confirmation_ID);
		saveEx();
	}
	
	public void voucherOnSettlement()
	{
		if(!CHARGETYPE_Voucher.equals(getChargeType()))
			return;
		if(!isCancelled())
			return;
		
		doCancelVoucher();
	}
	
	/**
	 * Set voucher is used and used date to date confirm.
	 */
	protected void doUseVoucher()
	{
		if(!CHARGETYPE_Voucher.equals(getChargeType()))
			return;
		
		MUNSVoucherCode vc = new MUNSVoucherCode(getCtx(), getUNS_Voucher_Code_ID(), get_TrxName());
		if(vc.isUsed())
		{
			throw new AdempiereException("Can't update voucher. Voucher is used.");
		}
		vc.setIsUsed(true);
		vc.setUseDate((Timestamp)getParent().get_Value("DateConfirm"));
		vc.saveEx();
	}
	
	/**
	 * Cancel Voucher
	 */
	private void doCancelVoucher(int UNS_Voucher_Code_ID)
	{
		MUNSVoucherCode vc = new MUNSVoucherCode(getCtx(), getUNS_Voucher_Code_ID(), get_TrxName());
		vc.setIsUsed(false);
		vc.setUseDate(null);
		vc.saveEx();
	}
	
	/**
	 * Cancel voucher.
	 */
	public void doCancelVoucher()
	{
		if(!CHARGETYPE_Voucher.equals(getChargeType()))
			return;
		
		doCancelVoucher(getUNS_Voucher_Code_ID());
	}
	
	
	/**
	 * Sort Record Tab Logic
	 */
	public String beforeSaveTabRecord (int parentRecord_ID)
	{
		if(get_ValueAsInt("C_InvoiceLine_ID") > 0)
		{
			return null;
		}
		if(getReference_ID() > 0)
		{
			MUNSChargeDetail reference = getReference();
			PO.copyValues(reference, this);
			setAmount(reference.getAmountConfirmed());
			setAmountConfirmed(Env.ZERO);
			setC_BankStatementLine_ID(-1);
		}
		return null;
	}
	
	/**
	 * Check this detail has been realized or not.
	 * @param settlement
	 * @return
	 */
	public boolean hasRealizedOn(MUNSChargeDetail[] settlementDetails)
	{
		if(settlementDetails.length == 0)
			return true;
		
		for(MUNSChargeDetail settlementDetail : settlementDetails)
		{
			if(get_ID() == settlementDetail.get_ID())
				return true;
		}
		
		return false;
	}
	
	/**
	 * Check charge detail is voucher or not
	 * @return
	 */
	public boolean isVoucher()
	{
		return getChargeType().equals(CHARGETYPE_Voucher);
	}
	
	@Override
	public PO getParent()
	{
		if(null != m_parent)
		{
			m_parent.set_TrxName(get_TrxName());
			return m_parent;
		}
		
		GridTab mTab = getGridTab();
		String parentTableName = null;
		if(null != mTab)
		{
			GridTab parentTab = mTab.getParentTab();
			parentTableName = parentTab.getTableName();
		}
		
		if (null == parentTableName)
		{
			if (getUNS_Charge_RS_ID() > 0)
			{
				parentTableName = "UNS_Charge_RS";
			}
			else if (getUNS_Shipping_ID() > 0)
			{
				parentTableName = "UNS_Shipping";
			}
			else if (getUNS_Charge_Confirmation_ID() > 0)
			{
				parentTableName = "UNS_Charge_Confirmation";
			}
		}
		
		MTable table = MTable.get(getCtx(), parentTableName);
		table.setExtensionHandler(null);
		m_parent = table.getPO(get_ValueAsInt(parentTableName.concat("_ID")), get_TrxName());
		return m_parent;
	}
	
	/**
	 * Check this line is settlement line or no.
	 * @return boolean 
	 */
	public boolean isSettlement()
	{
		PO po = getParent();
		boolean ok = false;
		if(po.get_TableName().equals(MUNSChargeRS.Table_Name))
			ok = !((MUNSChargeRS) po).isRequest();
		else if(po.get_TableName().equals("UNS_Shipping"))
			ok = ((String)po.get_Value("Status")).equals("RS");
		return ok;
	}
	
	public void addDescription(String description) 
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription

	@Override
	public String beforeRemoveSelection() {
		// TODO Auto-generated method stub
		return null;
	}
}
