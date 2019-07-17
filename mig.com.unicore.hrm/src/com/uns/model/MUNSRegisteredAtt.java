/**
 * 
 */
package com.uns.model;

import java.util.Properties;

import com.uns.base.model.Query;

/**
 * @author MuhAmin
 *
 */
public class MUNSRegisteredAtt extends X_UNS_RegisteredAtt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3312094363770077401L;

	public MUNSRegisteredAtt(Properties ctx, int UNS_RegisteredAtt_ID,
			String trxName) {
		super(ctx, UNS_RegisteredAtt_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param ctx
	 * @param SerialNo
	 * @param TrxName
	 * @return dfgh
	 */
	public static MUNSRegisteredAtt getBySerialNo (Properties ctx, String  SerialNo, String TrxName)
	{
		MUNSRegisteredAtt reg = new Query(ctx, MUNSRegisteredAtt.Table_Name, "SerialNo=?", TrxName)
							.setParameters(SerialNo).first();
		return reg;
	}

}
