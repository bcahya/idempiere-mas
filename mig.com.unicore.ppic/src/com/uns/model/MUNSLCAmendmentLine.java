/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author menjangan
 *
 */
public class MUNSLCAmendmentLine extends X_UNS_LC_AmendmentLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_LC_AmendmentLine_ID
	 * @param trxName
	 */
	public MUNSLCAmendmentLine(Properties ctx, int UNS_LC_AmendmentLine_ID,
			String trxName) {
		super(ctx, UNS_LC_AmendmentLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLCAmendmentLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSLCAmendment getParent()
	{
		return (MUNSLCAmendment)getUNS_LC_Amendment();
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getUNS_LC_Lines_ID() <= 0)
		{
			MUNSLCAmendment parent = getParent();
			MUNSLCBalance lcBalance = (MUNSLCBalance) parent.getUNS_LC_Balance();
			MUNSLCLines lcLine = lcBalance.getLCLineOf(getM_Product_Category_ID());
			if(null != lcLine)
			{
				setUNS_LC_Lines_ID(lcLine.get_ID());
				setPrevLCQuantity(lcLine.getLCQuantity());
			}
		}
		return super.beforeSave(newRecord);
	}
}
