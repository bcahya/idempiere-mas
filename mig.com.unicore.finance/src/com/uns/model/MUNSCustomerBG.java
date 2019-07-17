/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.util.Env;

import com.unicore.model.MUNSBillingLineResult;
import com.unicore.model.factory.UNSFinanceModelFactory;
import com.uns.base.model.Query;

/**
 * @author setyaka
 *
 */
public class MUNSCustomerBG extends X_UNS_CustomerBG {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2453013529948943418L;
	private MUNSCustomerBGAction[] m_linesBGAction = null;
	private MUNSCustomerBGInvList[] m_linesBGInv = null;

	/**
	 * @param ctx
	 * @param UNS_CustomerBG_ID
	 * @param trxName
	 */
	public MUNSCustomerBG(Properties ctx, int UNS_CustomerBG_ID, String trxName) {
		super(ctx, UNS_CustomerBG_ID, trxName);
		
		if (UNS_CustomerBG_ID == 0)
        {
			setPaymentStatus (PAYMENTSTATUS_OnProcess);
			setDocStatus ("IP");
			
			setGrandTotal (Env.ZERO);
			setLimitAmt (Env.ZERO);
			setPaidAmt (Env.ZERO);
        }
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCustomerBG(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSCustomerBG(MUNSBillingLineResult lineResult){
		this(lineResult.getCtx(), 0, lineResult.get_TrxName());
		
		setClientOrg(lineResult);
		setDocStatus(DOCSTATUS_InProgress);
		setGrandTotal(Env.ZERO);
		
		setPaymentStatus(PAYMENTSTATUS_OnProcess);
	}
	
	public MUNSCustomerBGInvList[] getLinesBGInv (boolean requery){
		if(null != m_linesBGInv && !requery)
		{
			set_TrxName(m_linesBGInv, get_TrxName());
			return m_linesBGInv;
		}
		
		List<MUNSCustomerBGInvList> list = Query.get(
				getCtx(), UNSFinanceModelFactory.EXTENSION_ID, MUNSCustomerBGInvList.Table_Name, 
				COLUMNNAME_UNS_CustomerBG_ID+"=?", get_TrxName()).setParameters(get_ID())
				.setOrderBy(MUNSCustomerBGInvList.COLUMNNAME_UNS_CustomerBG_InvList_ID)
				.list();

		m_linesBGInv = new MUNSCustomerBGInvList[list.size()];
		list.toArray(m_linesBGInv);

		return m_linesBGInv;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSCustomerBGAction[] getLinesBGAction(boolean requery)
	{
		if(null != m_linesBGAction && !requery)
		{
			set_TrxName(m_linesBGAction, get_TrxName());
			return m_linesBGAction;
		}
		
		List<MUNSCustomerBGAction> list = Query.get(
				getCtx(), UNSFinanceModelFactory.EXTENSION_ID, MUNSCustomerBGAction.Table_Name
				, MUNSCustomerBGAction.COLUMNNAME_UNS_CustomerBG_ID + "=?"
				, get_TrxName()).setOrderBy("UNS_CustomerBG_Action_ID").setParameters(get_ID()).list();
		
		m_linesBGAction = new MUNSCustomerBGAction[list.size()];
		list.toArray(m_linesBGAction);
		
		return m_linesBGAction;
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		return super.beforeSave(newRecord);
	}
}
