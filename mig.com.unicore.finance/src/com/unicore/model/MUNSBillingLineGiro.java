/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.uns.model.MUNSCustomerBG;

/**
 * @author root
 *
 */
public class MUNSBillingLineGiro extends X_UNS_BillingLine_Giro {

	/**
	 * 
	 * @param ctx
	 * @param UNS_BillingLine_Giro_ID
	 * @param trxName
	 */
	public MUNSBillingLineGiro(Properties ctx, int UNS_BillingLine_Giro_ID,
			String trxName) {
		super(ctx, UNS_BillingLine_Giro_ID, trxName);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBillingLineGiro(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	private static final long serialVersionUID = 4558074413720739906L;
	private MUNSBillingLineResult m_parent = null;
	public boolean m_force = false;
	
	public MUNSBillingLineResult getParent()
	{
		if(null == m_parent)
		{
			m_parent = new MUNSBillingLineResult(
					getCtx(), getUNS_BillingLine_Result_ID(), get_TrxName());
		}
		
		return m_parent;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		validateGiro(newRecord);
		if(getUNS_CustomerBG_ID() > 0)
		{
			setReceiptDate(new MUNSCustomerBG(getCtx(), getUNS_CustomerBG_ID(), get_TrxName()).getReceiptDate());
		}
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if(!newRecord)
		{
			updateHeader(newRecord);
		}
		
		return super.afterSave(newRecord, success);
	}
	
	@Override
	protected boolean afterDelete(boolean success)
	{
		updateHeader(true);
		return super.afterDelete(success);
	}
	
	private void updateHeader(boolean force)
	{
		BigDecimal totalAmtGiro = Env.ZERO;
		BigDecimal totalReceiptGiro = Env.ZERO;
		MUNSBillingLineGiro[] giroList = getParent().getLines(true);
		for(int i=0; i<giroList.length; i++)
		{
			totalAmtGiro = totalAmtGiro.add(giroList[i].getPaidAmtGiro());
			totalReceiptGiro = totalReceiptGiro.add(giroList[i].getReceiptAmtGiro());
		}
		
		getParent().setPaidAmtGiro(totalAmtGiro);
		getParent().setReceiptAmtGiro(totalReceiptGiro);
		getParent().setUNS_CustomerBG_ID(-1);
		getParent().m_force = force;
		getParent().saveEx();
	}
	
	public void validateGiro(boolean newRecord)
	{
		if(!newRecord && getUNS_CustomerBG_ID() == 0)
		{
			throw new AdempiereUserError(Msg.getMsg(getCtx(), "You must define giro number or create new customer giro"));
		}
		if(getPaidAmtGiro().signum() == 0 && !newRecord && !m_force)
		{
			throw new AdempiereUserError("Please update paid amount by giro");
		}
		
		if(getUNS_CustomerBG_ID() > 0)
		{
			String sql = "SELECT COALESCE(LimitAmt, 0) FROM UNS_CustomerBG WHERE UNS_CustomerBG_ID = ?";
			BigDecimal amtGiro = DB.getSQLValueBD(get_TrxName(), sql, getUNS_CustomerBG_ID());
			sql = "SELECT COALESCE(SUM(PaidAmtGiro), 0) FROM UNS_BillingLine_Result WHERE "
					+ " UNS_CustomerBG_ID = ? ";
			BigDecimal AmtUsed = DB.getSQLValueBD(get_TrxName(), sql, getUNS_CustomerBG_ID());
			sql = "SELECT COALESCE(SUM(PaidAmtGiro), 0) FROM UNS_BillingLine_Giro WHERE "
					+ " UNS_CustomerBG_ID = ? AND UNS_BillingLine_Giro_ID <> ?";
			AmtUsed = AmtUsed.add(DB.getSQLValueBD(get_TrxName(), sql, getUNS_CustomerBG_ID(), get_ID()));
			BigDecimal unallocated = amtGiro.subtract(AmtUsed);
			
			if(getPaidAmtGiro().compareTo(unallocated) > 0)
			{
				throw new AdempiereUserError("Over amount giro. unallocated amount giro : " + unallocated);
			}		
		}
	}
}
