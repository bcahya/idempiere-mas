/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author eko
 *
 */
public class MUNSLeaveReservedConfig extends X_UNS_LeaveReservedConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_LeaveReservedConfig_ID
	 * @param trxName
	 */
	public MUNSLeaveReservedConfig(Properties ctx,
			int UNS_LeaveReservedConfig_ID, String trxName) {
		super(ctx, UNS_LeaveReservedConfig_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLeaveReservedConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param ctx
	 * @param hrdLevel
	 * @param trxName
	 * @return {@link MUNSLeaveReservedConfig}
	 */
	public static MUNSLeaveReservedConfig get(Properties ctx, int positionCategory, String nationality, String trxName)
	{
		if (nationality==null)
			nationality="WNI";
		return Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, COLUMNNAME_C_JobCategory_ID + 
				"=? AND IsActive='Y' AND Nationality=?", trxName)
				.setParameters(positionCategory,nationality).firstOnly();
	}

}
