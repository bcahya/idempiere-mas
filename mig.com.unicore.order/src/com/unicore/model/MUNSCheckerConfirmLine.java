/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

/**
 * @author ALBURHANY
 *
 */
public class MUNSCheckerConfirmLine extends X_UNS_CheckerConfirm_Line {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2941316145523362802L;

	/**
	 * @param ctx
	 * @param UNS_CheckerConfirm_Line_ID
	 * @param trxName
	 */
	public MUNSCheckerConfirmLine(Properties ctx,
			int UNS_CheckerConfirm_Line_ID, String trxName) {
		super(ctx, UNS_CheckerConfirm_Line_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSCheckerConfirmLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	public static void main (String [] args)
	{
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTimeInMillis(Timestamp.valueOf("2015-01-03 00:00:00").getTime());
		System.out.println("sekarang week "+ cal.get(Calendar.WEEK_OF_YEAR));
		System.out.println("Hari dalam minggu " +cal.get(Calendar.DAY_OF_WEEK));
	}
	
	public MUNSCheckerConfirmLine (MUNSCheckerLine cl, int UNS_CheckerConfirmation_ID)
	{
		this(cl.getCtx(), 0, cl.get_TrxName());
		
		setUNS_CheckerConfirmation_ID(UNS_CheckerConfirmation_ID);
		setUNS_CheckerLine_ID(cl.get_ID());
		setDescription(cl.getDescription());
	}
}