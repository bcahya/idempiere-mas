/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.PO;
import org.compiere.util.DB;

/**
 * @author Burhani Adam
 *
 */
public class MUNSUnloadingCostLine extends X_UNS_UnloadingCostLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -318757997086761776L;

	/**
	 * @param ctx
	 * @param UNS_UnloadingCostLine_ID
	 * @param trxName
	 */
	public MUNSUnloadingCostLine(Properties ctx, int UNS_UnloadingCostLine_ID,
			String trxName) {
		super(ctx, UNS_UnloadingCostLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSUnloadingCostLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean beforeSave(boolean newRecord)
	{
		if(newRecord && getReference_ID() > 0)
		{
			int id = getUNS_UnloadingCost_ID();
			MUNSUnloadingCostLine reference = (MUNSUnloadingCostLine) getReference();
			PO.copyValues(reference, this);
			setUNS_UnloadingCost_ID(id);
			setPayAmt(reference.getAmount());
		}
		return true;
	}
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		upHeader();
		return true;
	}
	
	protected boolean beforeDelete()
	{
		
		return true;
	}
	
	protected boolean afterDelete(boolean success)
	{
		upHeader();
		return true;
	}
	
	private void upHeader()
	{
		String columnAmt = getUNS_UnloadingCost().isReceipt() ? "Amount" : "PayAmt";
		String sql = "UPDATE UNS_UnloadingCost SET GrandTotal = (SELECT "
				+ " COALESCE(SUM(" + columnAmt + "),0) FROM UNS_UnloadingCostLine"
				+ " WHERE UNS_UnloadingCost_ID = ?) WHERE UNS_UnloadingCost_ID = ?";
		DB.executeUpdate(sql, new Object[]{getUNS_UnloadingCost_ID(), getUNS_UnloadingCost_ID()}, false, get_TrxName());
	}
}
