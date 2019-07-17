/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author ALBURHANY
 *
 */
public class MUNSCustomerRedeemLine extends X_UNS_CustomerRedeem_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1200871406047018362L;

	/**
	 * @param ctx
	 * @param UNS_CustomerRedeem_Line_ID
	 * @param trxName
	 */
	public MUNSCustomerRedeemLine(Properties ctx,
			int UNS_CustomerRedeem_Line_ID, String trxName) {
		super(ctx, UNS_CustomerRedeem_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCustomerRedeemLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean beforeSave (boolean newRecord)
	{
		//cek amount point
		if(getCurrentPoint().subtract(getValuePoint()).compareTo(Env.ZERO) == -1)
		{
			throw new AdempiereException("Not Enough Point");
		}
		
		return true;
	}

	public MUNSCustomerRedeemLine[] getByParent (int UNS_CustomerReedem_ID, String trxName)
	{
		String whereClause = "UNS_CustomerRedeem_ID = ?";
		List<MUNSCustomerRedeemLine> list =  Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSCustomerRedeemLine.Table_Name,
				whereClause, get_TrxName()).setParameters(UNS_CustomerReedem_ID).list();
		
		return list.toArray(new MUNSCustomerRedeemLine[list.size()]);
	}
}
