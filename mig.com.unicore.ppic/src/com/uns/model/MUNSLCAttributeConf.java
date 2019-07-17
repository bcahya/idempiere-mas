/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.AdempiereUserError;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSLCAttributeConf extends X_UNS_LC_AttributeConf {

	/**
	 * 
	 */
	private static final long serialVersionUID = 605702819597811419L;

	/**
	 * @param ctx
	 * @param UNS_LC_AttributeConf_ID
	 * @param trxName
	 */
	public MUNSLCAttributeConf(Properties ctx, int UNS_LC_AttributeConf_ID,
			String trxName) {
		super(ctx, UNS_LC_AttributeConf_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSLCAttributeConf(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getDataType().equals(DATATYPE_Product) && !getValue().equalsIgnoreCase("45A"))
			throw new AdempiereUserError("Data type product only for attribute 45A");
		setValue(getValue().toUpperCase());
		return super.beforeSave(newRecord);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param AttrNumber
	 * @param trxName
	 * @return
	 */
	public static MUNSLCAttributeConf get(Properties ctx, String AttrNumber, String trxName)
	{
		return Query.get(
				ctx
				, UNSPPICModelFactory.getExtensionID()
				, Table_Name, COLUMNNAME_Value + " = '" + AttrNumber +"'"
				, trxName)
				.firstOnly();
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{		
		return super.afterSave(newRecord, success);
	}

}
