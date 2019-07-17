/**
 * 
 */
package com.uns.base.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MRegion;
import org.compiere.util.DB;

import com.unicore.util.GeocodingUtils;

/**
 * @author AzHaidar
 *
 */
public class MCity extends org.compiere.model.MCity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1111662656500427317L;

	/**
	 * @param ctx
	 * @param C_City_ID
	 * @param trxName
	 */
	public MCity(Properties ctx, int C_City_ID, String trxName) {
		super(ctx, C_City_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MCity(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * @param region
	 * @param cityName
	 */
	public MCity(MRegion region, String cityName) {
		super(region, cityName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		
		if (newRecord)
		{
			resetCoordinates();
		}
		
		return super.beforeSave(newRecord);
	} // beforeSave
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		//if (newRecord)
		//	resetCoordinates();
		return super.afterSave(newRecord, success);
	}
	
	public String resetCoordinates()
	{
		String sql = "SELECT Name FROM C_Region WHERE C_Region_ID=?";
		
		String region = getC_Region_ID() == 0? 
				"" : DB.getSQLValueString(get_TrxName(), sql, getC_Region_ID());
		
		sql = "SELECT CountryCode FROM C_Country WHERE C_Country_ID=?";
		
		String country = getC_Region_ID() == 0? 
				"" : DB.getSQLValueString(get_TrxName(), sql, getC_Country_ID());
		
		String address = getName() + ", " + region + ", " + country;
		
		return new GeocodingUtils().setCoordinates(this, address);
	} // resetCoordinates
}
