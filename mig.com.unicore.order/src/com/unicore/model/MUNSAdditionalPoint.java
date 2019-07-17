/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;


/**
 * @author ALBURHANY
 *
 */
public class MUNSAdditionalPoint extends X_UNS_AdditionalPoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5009145469621772178L;

	/**
	 * @param ctx
	 * @param UNS_AdditionalPoint_ID
	 * @param trxName
	 */
	public MUNSAdditionalPoint(Properties ctx, int UNS_AdditionalPoint_ID,
			String trxName) {
		super(ctx, UNS_AdditionalPoint_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSAdditionalPoint(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean beforeSave (boolean newRecord)
	{
		if (getCurrentPoint().subtract(getValuePoint()).compareTo(Env.ZERO) == -1)
			throw new AdempiereException("Not Enough Point");
		
		return true;
	}
	
	public MUNSAdditionalPoint[] getByParent (int UNS_CustomerRedeem_ID)
	{
		String whereClause = "UNS_CustomerRedeem_ID = ?";
		
		List<MUNSAdditionalPoint> list = Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID,
				MUNSAdditionalPoint.Table_Name, whereClause, get_TrxName()).setParameters(UNS_CustomerRedeem_ID)
				.list();
		
		return list.toArray(new MUNSAdditionalPoint[list.size()]);
	}

}
