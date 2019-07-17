package com.uns.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.compiere.util.TimeUtil;
import org.compiere.util.Util;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;

public class MUNSAttConfiguration extends X_UNS_AttConfiguration {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8097659092811616140L;

	/**
	 * 
	 * @param ctx
	 * @param UNS_AttConfiguration_ID
	 * @param trxName
	 */
	public MUNSAttConfiguration(Properties ctx, int UNS_AttConfiguration_ID,
			String trxName) 
	{
		super(ctx, UNS_AttConfiguration_ID, trxName);
	}

	/**
	 * 
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSAttConfiguration(Properties ctx, ResultSet rs, String trxName) 
	{
		super(ctx, rs, trxName);
	}
	
	public String validatePresenceStatusOnOut (
			Timestamp timeOut, Timestamp checkTime)
	{
		if (null == checkTime)
		{
			return MUNSDailyPresence.PRESENCESTATUS_Mangkir;
		}
		
		timeOut = TimeUtil.trunc(timeOut, TimeUtil.TRUNC_DAY);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeOut.getTime());
		calendar.add(Calendar.MINUTE, (getMaxEarLierFSOut() * -1));
		//check earlier
		if (checkTime.getTime() < calendar.getTimeInMillis()
				&& getEarlierFSOutPresence() != null)
		{
			return getEarlierFSOutPresence();
		}
		
		return MUNSDailyPresence.PRESENCESTATUS_FullDay;
	}
	
	public String validatePresenceStatusOnIn (
			Timestamp timeIn, Timestamp checkTime)
	{
		if (null == checkTime)
		{
			return MUNSDailyPresence.PRESENCESTATUS_Mangkir;
		}
		
		timeIn = TimeUtil.trunc(timeIn, TimeUtil.TRUNC_DAY);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeIn.getTime());
		calendar.add(Calendar.MINUTE, getMaxLateFSIn());
		//check Late
		if (checkTime.getTime() > calendar.getTimeInMillis()
				&& getLateFSInPresence() != null)
		{
			return getLateFSInPresence();
		}
		
		return MUNSDailyPresence.PRESENCESTATUS_FullDay;
	}
	
	public String validatePresenceStatus (
			Timestamp inTime, Timestamp outTime, Timestamp checkTime)
	{
		if (null == checkTime)
		{
			return MUNSDailyPresence.PRESENCESTATUS_Mangkir;
		}
		
		String presenceStatus = validatePresenceStatusOnIn(inTime, checkTime);
		if (MUNSDailyPresence.PRESENCESTATUS_FullDay.equals(presenceStatus))
		{
			presenceStatus = validatePresenceStatusOnOut(outTime, checkTime);
		}
		
		return presenceStatus;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param trxName
	 * @param date
	 * @return {@link MUNSAttConfiguration}[] or null if date is null
	 */
	public static MUNSAttConfiguration[] getValids (
			Properties ctx, String trxName, Timestamp date)
	{
		if (date == null)
		{
			return null;
		}
		
		List<MUNSAttConfiguration> list = Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, 
				Table_Name, COLUMNNAME_ValidFrom + " <= ?", 
				trxName).setParameters(date).setOrderBy("ValidFrom Desc")
				.list();
		
		MUNSAttConfiguration[] attConfigs = 
				new MUNSAttConfiguration[list.size()];
		list.toArray(attConfigs);
		
		return attConfigs;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param date @Mandatory@
	 * @param AD_Org_ID
	 * @param employmentType
	 * @param secOfDpt_ID
	 * @param C_JobCategory_ID
	 * @param trxName
	 * @return {@link MUNSAttConfiguration}
	 */
	public static MUNSAttConfiguration get (
			Properties ctx, Timestamp date, int AD_Org_ID,
			String employmentType, int secOfDpt_ID, int C_JobCategory_ID, 
			String trxName)
	{
		if (null == date)
		{
			return null;
		}
		
		MUNSAttConfiguration[] configs = getValids(ctx, trxName, date);
		int idxMax = -1;
		int matchLevel = 0;
		
		for (int i=0; i<configs.length; i++)
		{
			MUNSAttConfiguration config = configs[i];
			if (!config.isActive())
			{
				continue;
			}
			
			int thisMatchLevel = 0;
			if (AD_Org_ID > 0 && (config.getAD_Org_ID() == AD_Org_ID
					|| config.getAD_Org_ID() == 0))
			{
				thisMatchLevel++;
			}
			if (!Util.isEmpty(employmentType) && employmentType.equals(
					config.getEmploymentType()))
			{
				thisMatchLevel++;
			}
			if (secOfDpt_ID > 0 && secOfDpt_ID == config.getSectionOfDept_ID())
			{
				thisMatchLevel++;
			}
			if (C_JobCategory_ID > 0 && 
					C_JobCategory_ID == config.getC_JobCategory_ID())
			{
				thisMatchLevel++;
			}
			
			if (thisMatchLevel > matchLevel)
			{
				idxMax = i;
				matchLevel = thisMatchLevel;
				if (matchLevel == 4)
				{
					break;
				}
			}
		}
		
		if (idxMax == -1)
		{
			return null;
		}
		
		return configs[idxMax];
	}
	
	public static MUNSAttConfiguration get(Properties ctx, int UNS_AttConfiguration_ID, String trxName)
	{
		MUNSAttConfiguration attConfig = new Query(ctx, Table_Name, 
				COLUMNNAME_UNS_AttConfiguration_ID + "=?", trxName).
				setParameters(UNS_AttConfiguration_ID).firstOnly();
		
		return attConfig;
	}
}