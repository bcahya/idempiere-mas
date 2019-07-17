/**
 * 
 */
package com.uns.model.override;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Properties;

import com.uns.model.MUNSResource;
import com.uns.model.MUNSSlotType;

/**
 * @author menjangan
 *
 */
public class MUNSWorkerPresence extends com.uns.model.MUNSWorkerPresence {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6370316832646182044L;

	/**
	 * 
	 * @param ctx
	 * @param UNS_Worker_Presence_ID
	 * @param trxName
	 */
	public MUNSWorkerPresence(Properties ctx, int UNS_Worker_Presence_ID, String trxName) 
	{
		super(ctx, UNS_Worker_Presence_ID, trxName);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSWorkerPresence(Properties ctx, ResultSet rs, String trxName) {
		
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getUNS_SlotType_ID()
	{
		int st_ID = 0;
		if(getUNS_Resource().getUNS_SlotType_ID() > 0)
			st_ID = getUNS_Resource().getUNS_SlotType_ID();
		return st_ID;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSResource getUNS_Resource()
	{
		return new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getWorkDate().getTime());
		MUNSSlotType slotType = new MUNSSlotType(getCtx(), getUNS_SlotType_ID(), get_TrxName());
		if(isNonBusinessDay())
			setDayType(DAYTYPE_HariLiburNasional);
		else if(!slotType.IsWorkDay(cal.get(Calendar.DAY_OF_WEEK)))
		{
			setDayType(DAYTYPE_HariLiburMingguan);
			super.setIsWorkDay(false);
		}
		else
			setDayType(DAYTYPE_HariKerjaBiasa);		
		
		return super.beforeSave(newRecord);
	}

}
