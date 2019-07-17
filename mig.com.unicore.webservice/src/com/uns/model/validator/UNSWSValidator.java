/**
 * 
 */
package com.uns.model.validator;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Properties;

import org.compiere.model.MWebServiceType;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.idempiere.adInterface.x10.ADLoginRequest;
import org.idempiere.adInterface.x10.DataField;
import org.idempiere.webservices.IWSValidator;
import org.idempiere.webservices.fault.IdempiereServiceFault;

import com.uns.model.MUNSQueueWS;

/**
 * @author Burhani Adam
 *
 */
public class UNSWSValidator implements IWSValidator{

	private StringBuilder m_Msg = new StringBuilder();
	private String tableName = null;
	
	@Override
	public void validate(PO po, MWebServiceType m_webserviceType,
			DataField[] fields, int time, String trxName,
			Map<String, Object> requestCtx) throws IdempiereServiceFault
	{
		parseMessage(fields);
		MUNSQueueWS ws = new MUNSQueueWS(po.getCtx(), po.get_ID(), po.get_TrxName());
		String sql = "SELECT AD_Table_ID FROM AD_Table WHERE TableName = ?";
		int table_ID = DB.getSQLValue(ws.get_TrxName(), sql, tableName);
		ws.setReferenced_Table_ID(table_ID);
		ws.setMessage(m_Msg.toString());
		ws.setDateReceived(new Timestamp(System.currentTimeMillis()));
		ws.saveEx();
	}

	@Override
	public void login(ADLoginRequest loginRequest, Properties ctx,
			MWebServiceType m_webserviceType, int time)
			throws IdempiereServiceFault {
		
	}
	
	private void parseMessage(DataField[] fields)
	{
		for(int i=0; i<fields.length; i++)
		{
			DataField field = fields[i];
			if(field.getColumn().equals("Referenced_Table_ID"))
			{
				tableName = field.getVal();
				continue;
			}
			else if(field.getColumn().equals("AD_Org_ID"))
			{
				
			}
			m_Msg.append(field.getColumn()).append("=");
			boolean last = fields.length - i == 1;
			if(last)
				m_Msg.append(field.getVal()).append(";");
			else
				m_Msg.append(field.getVal()).append(";\n");
		}
	}
}