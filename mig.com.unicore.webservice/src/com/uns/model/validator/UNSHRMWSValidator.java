/**
 * 
 */
package com.uns.model.validator;

import java.util.Map;
import java.util.Properties;
import org.compiere.model.MWebServiceType;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.idempiere.adInterface.x10.ADLoginRequest;
import org.idempiere.adInterface.x10.DataField;
import org.idempiere.webservices.IWSValidator;
import org.idempiere.webservices.fault.IdempiereServiceFault;

import com.uns.model.MUNSCheckInOut;

/**
 * @author Menjangan
 *
 */
public class UNSHRMWSValidator implements IWSValidator {

	/**
	 * 
	 */
	public UNSHRMWSValidator() 
	{
		super ();
	}

	/* (non-Javadoc)
	 * @see org.idempiere.webservices.IWSValidator#validate(
	 * org.compiere.model.PO, org.compiere.model.MWebServiceType, 
	 * org.idempiere.adInterface.x10.DataField[], int Time, 
	 * java.lang.String, java.util.Map)
	 */
	@Override
	public void validate(PO po, MWebServiceType m_webserviceType,
			DataField[] fields, int time, String trxName,
			Map<String, Object> requestCtx) throws IdempiereServiceFault 
	{
		
		switch (time)
		{
			case  TIMING_BEFORE_SAVE :
				doValidateBeforeSave(po, m_webserviceType, fields, 
						trxName, requestCtx);
				break;
		}
	}

	/* (non-Javadoc)
	 * @see org.idempiere.webservices.IWSValidator#login(
	 * org.idempiere.adInterface.x10.ADLoginRequest, java.util.Properties, 
	 * org.compiere.model.MWebServiceType, int)
	 */
	@Override
	public void login(ADLoginRequest loginRequest, Properties ctx,
			MWebServiceType m_webserviceType, int time)
			throws IdempiereServiceFault 
	{
		
	}
	
	private void doValidateCheckInOut (PO po, MWebServiceType m_webserviceType,
			DataField[] fields, String trxName,
			Map<String, Object> requestCtx) throws IdempiereServiceFault
	{
		
		MUNSCheckInOut  checkInOut = (MUNSCheckInOut) po;		
		String attName =  getValueFromFields("AttendanceName", fields);
		int AD_Org_ID = DB.getSQLValue(
				checkInOut.get_TrxName(), 
				" SELECT AD_Org_ID FROM UNS_Employee WHERE UNS_Employee_ID = ( "
				+ " SELECT UNS_Employee_ID FROM UNS_Employee WHERE "
				+ " AttendanceName = ?) ", 
				attName);
		
		if (AD_Org_ID >0)
		{
			checkInOut.setAD_Org_ID(AD_Org_ID);
		}
	}
	
	private String getValueFromFields (String key, DataField[] fields)
	{
		String value = null;
		for (int i=0; i< fields.length; i++)
		{
			DataField field = fields[i];
			if (field.getColumn().equals(key))
			{
				value = field.getVal();
				break;
			}
		}
		
		return value;
	}
	
	private void doValidateBeforeSave (PO po, MWebServiceType m_webserviceType,
			DataField[] fields, String trxName,
			Map<String, Object> requestCtx) throws IdempiereServiceFault
	{
		String tableName = po.get_TableName();
		switch (tableName)
		{
			case MUNSCheckInOut.Table_Name :
				doValidateCheckInOut(po, m_webserviceType, fields, 
						trxName, requestCtx);
				break;
		}
	}
}
