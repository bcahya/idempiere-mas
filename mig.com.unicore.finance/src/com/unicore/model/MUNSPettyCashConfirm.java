/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

/**
 * @author menjangan
 *
 */
public class MUNSPettyCashConfirm extends X_UNS_PettyCash_Confirm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_PettyCash_Confirm_ID
	 * @param trxName
	 */
	public MUNSPettyCashConfirm(Properties ctx, int UNS_PettyCash_Confirm_ID,
			String trxName) {
		super(ctx, UNS_PettyCash_Confirm_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPettyCashConfirm(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public boolean afterSave(boolean newRecord, boolean success)
	{
		updateHeader();
		return super.afterSave(newRecord, success);
	}
	
	@Override
	public boolean afterDelete(boolean success)
	{
		updateHeader();
		return super.afterDelete(success);
	}
	
	private void updateHeader()
	{
		String sql ="UPDATE UNS_TransferBalance_Confirm tb " +
				" SET AmountConfirmed = (SELECT COALESCE(SUM(AmountConfirmed),0) " +
				"FROM UNS_PettyCash_Confirm pc " +
				"WHERE pc.UNS_TransferBalance_Confirm_ID =tb.UNS_TransferBalance_Confirm_ID) " +
				", AmountRequested = (SELECT COALESCE(SUM(AmountRequested),0) " +
				"FROM UNS_PettyCash_Confirm pc " +
				" WHERE pc.UNS_TransferBalance_Confirm_ID =tb.UNS_TransferBalance_Confirm_ID)" +
				" WHERE tb.UNS_TransferBalance_Confirm_ID=?";
		
		int result = DB.executeUpdate(sql,getUNS_TransferBalance_Confirm_ID(), get_TrxName());
		
		if(result < 0)
			throw new AdempiereException("Failed to update header!");
	}

	public void addDescription(String description) 
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}

}
