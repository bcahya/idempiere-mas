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
public class MUNSPettyCashRequest extends X_UNS_PettyCashRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_PettyCashRequest_ID
	 * @param trxName
	 */
	public MUNSPettyCashRequest(Properties ctx, int UNS_PettyCashRequest_ID,
			String trxName) {
		super(ctx, UNS_PettyCashRequest_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPettyCashRequest(Properties ctx, ResultSet rs, String trxName) {
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
		String sql ="UPDATE UNS_TransferBalance_Request tb " +
				" SET AmountRequested = (SELECT COALESCE(SUM(AmountRequested),0) " +
				"FROM UNS_PettyCashRequest pc " +
				"WHERE pc.UNS_TransferBalance_Request_ID =tb.UNS_TransferBalance_Request_ID) " +
				" , AmountConfirmed = (SELECT COALESCE(SUM(AmountConfirmed),0) " +
				"FROM UNS_PettyCashRequest pc " +
				"WHERE pc.UNS_TransferBalance_Request_ID =tb.UNS_TransferBalance_Request_ID) " +
				"WHERE tb.UNS_TransferBalance_Request_ID=?";
		
		int result = DB.executeUpdate(sql,getUNS_TransferBalance_Request_ID(), get_TrxName());
		
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
