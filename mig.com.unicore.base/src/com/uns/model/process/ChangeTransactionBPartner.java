/**
 * 
 */
package com.uns.model.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.acct.DocManager;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MTable;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Burhani Adam
 *
 */
public class ChangeTransactionBPartner extends SvrProcess {

	private final int Table_BPartner_ID = 291;
	private final int Table_BPartnerLocation_ID = 293;
	private int C_BPartner_ID = 0;
	private int C_BPartnerTo_ID = 0;
	private int C_BPartner_Location_ID = 0;
	private int C_LocationBPartnerTo = 0;
	private boolean m_UseOriginLoc = true;
	private boolean success = false;
	private IProcessUI m_ui = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] params = getParameter();
		for(ProcessInfoParameter param : params)
		{
			if(param.getParameterName() == null)
				;
			else if(param.getParameterName().equals("C_BPartner_ID"))
				C_BPartner_ID = param.getParameterAsInt();
			else if(param.getParameterName().equals("C_BPartnerTo_ID"))
				C_BPartnerTo_ID = param.getParameterAsInt();
			else if(param.getParameterName().equals("UseOriginLoc"))
				m_UseOriginLoc = param.getParameterAsBoolean();
			else if(param.getParameterName().equals("LocationBPartnerTo"))
				C_LocationBPartnerTo = param.getParameterAsInt();
			else if(param.getParameterName().equals("C_BPartner_Location_ID"))
				C_BPartner_Location_ID = param.getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + param.getParameterName());
		}
		if(C_BPartner_ID == 0 || C_BPartnerTo_ID == 0)
			throw new AdempiereException("All Paramater is Mandatory");
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		m_ui = Env.getProcessUI(getCtx());
		
		String[] tableIDs = tableIDs();
		String tableName = null;
		int i = 0;
		if(m_UseOriginLoc)
		{
			String upLoc = "UPDATE C_BPartner_Location SET C_BPartner_ID = ? WHERE C_BPartner_ID = ?";
			success = DB.executeUpdate(upLoc, new Object[]{C_BPartnerTo_ID, C_BPartner_ID}, false, get_TrxName()) > 0
					? true : false;
			if(!success)
				throw new AdempiereException("Failed when trying merger location");
			
			MBPartnerLocation locOri = new MBPartnerLocation(getCtx(), C_BPartner_Location_ID, get_TrxName());
			MBPartnerLocation locTo = new MBPartnerLocation(getCtx(), 0, get_TrxName());
			MBPartnerLocation.copyValues(locOri, locTo);
			locTo.setAD_Org_ID(locOri.getAD_Org_ID());
			locTo.setC_BPartner_ID(C_BPartner_ID);
			locTo.saveEx();
		}
		
		for(i = 0; i < tableIDs.length; i++)
		{
			int table_ID = new Integer (tableIDs[i]);
			tableName = MTable.getTableName(getCtx(), table_ID);
			
			String checkColumn = "SELECT COUNT(*) FROM AD_Table co WHERE co.AD_Table_ID=? AND co.AD_Table_ID IN"
					+ " (SELECT c.AD_Table_ID FROM AD_Column c WHERE c.ColumnName = 'C_BPartner_ID' AND c.AD_Table_ID = co.AD_Table_ID)"
					+ " AND co.AD_Table_ID IN (SELECT o.AD_Table_ID FROM AD_Column o WHERE o.ColumnName = 'Posted' AND o.AD_Table_ID = co.AD_Table_ID)";
			boolean needPost = DB.getSQLValue(get_TrxName(), checkColumn, table_ID) > 0 ? true : false;
			
			String StringIDs = null;
			if(needPost)
			{
				String idList = "SELECT Array_To_String(Array_Agg(" + tableName + "_ID),';') FROM " + tableName
						 		+ " WHERE C_BPartner_ID = ?";
				StringIDs = DB.getSQLValueString(get_TrxName(), idList, C_BPartner_ID);
			}
			
			if(tableName.equals("C_BP_Customer_Acct") || tableName.equals("C_BP_Vendor_Acct")
					|| tableName.equals("C_BPartner_Location"))
				continue;
			
			String[] column = null;
			
			if(!m_UseOriginLoc)
			{
				column = upLocation(table_ID);
				if(null != column)
				{
					m_ui.statusUpdate("lagi update Business Partner di " + tableName + " sebanyak " + column.length + " data");
					for(int i2 = 0; i2 < column.length; i2++)
					{						
						String upLocation = "UPDATE " + tableName + " SET " + column[i2] + " = ? WHERE C_BPartner_ID = ?" ;
						success = DB.executeUpdate(upLocation, new Object[]{C_LocationBPartnerTo, C_BPartner_ID}, false, get_TrxName())
											< 0 ? false : true;
						if(!success)
							throw new AdempiereException("Failed when trying update Table " + tableName);
					}
				}
			}
			
			column = upBPartner(table_ID);
			if(null != column)
			{
				for(int i3 = 0; i3 < column.length; i3++)
				{
					m_ui.statusUpdate("lagi update Business Partner Location di " + tableName + " sebanyak " + column.length + " data");
					
					String upBPartner = "UPDATE " + tableName + " SET " + column[i3] + " = ? WHERE " + column[i3] + " = ?" ;
					success = DB.executeUpdate(upBPartner, new Object[]{C_BPartnerTo_ID, C_BPartner_ID}, false, get_TrxName())
										< 0 ? false : true;
					if(!success)
						throw new AdempiereException("Failed when trying update Table " + tableName);
				}
			}
			
			if(needPost)
			{
				if(null != StringIDs)
				{
					String[] StringID = StringIDs.split(";");
					for(int i4 = 0; i4 < StringID.length;)
					{
						m_ui.statusUpdate("lagi Repost Dokumen ke " + i4 + " untuk " + tableName + " sebanyak " + StringID.length + " data");
						int intID = new Integer (StringID[i4]);
						DocManager.postDocument(
								MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID(), 
										get_TrxName()), table_ID, intID, 
								true, true, get_TrxName());
						i4++;
					}
				}
			}
		}
		return "Has Updated " + i + " Table";
	}
	
	public String[] tableIDs()
	{
		String value[];
		
		String sql = "SELECT Array_To_String(Array_Agg(AD_Table_ID),';') FROM AD_Table WHERE AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Column"
				+ " WHERE ColumnSQL IS NULL AND (ColumnName = 'C_BPartner_ID' OR AD_Reference_ID IN (SELECT AD_Reference_ID FROM AD_Reference"
				+ " WHERE ValidationType = 'T' AND AD_Reference_ID IN (SELECT AD_Reference_ID FROM AD_Ref_Table WHERE AD_Table_ID=?))"
				+ " OR AD_Reference_Value_ID IN (SELECT AD_Reference_ID FROM AD_Reference"
				+ " WHERE ValidationType = 'T' AND AD_Reference_ID IN (SELECT AD_Reference_ID FROM AD_Ref_Table WHERE AD_Table_ID=?))))"
				+ " AND AD_Table_ID <> ? AND isView = 'N'";
		String values = DB.getSQLValueString(get_TrxName(), sql, Table_BPartner_ID, Table_BPartner_ID, Table_BPartner_ID);
		
		value = values.split(";");
		
		return value;		
	}
	
	public String[] upBPartner(int AD_Table_ID)
	{
		String value[];
		
		String sql = "SELECT Array_To_String(Array_Agg(ColumnName),';') FROM AD_Column WHERE ColumnSQL IS NULL"
				+ " AND (ColumnName = 'C_BPartner_ID' OR AD_Reference_ID IN (SELECT AD_Reference_ID FROM AD_Reference"
				+ " WHERE ValidationType = 'T' AND AD_Reference_ID IN (SELECT AD_Reference_ID FROM AD_Ref_Table WHERE AD_Table_ID=?))"
				+ " OR AD_Reference_Value_ID IN (SELECT AD_Reference_ID FROM AD_Reference"
				+ " WHERE ValidationType = 'T' AND AD_Reference_ID IN (SELECT AD_Reference_ID FROM AD_Ref_Table WHERE AD_Table_ID=?)))"
				+ " AND AD_Table_ID = ?";
		String values = DB.getSQLValueString(get_TrxName(), sql, Table_BPartner_ID, Table_BPartner_ID, AD_Table_ID);
		
		if(null != values)
		{
			value = values.split(";");
			return value;
		}
		
		return null;
	}
	
	public String[] upLocation(int AD_Table_ID)
	{
		String value[];
		
		String sql = "SELECT Array_To_String(Array_Agg(ColumnName),';') FROM AD_Column WHERE ColumnSQL IS NULL"
				+ " AND (ColumnName = 'C_BPartner_Location_ID' OR AD_Reference_ID IN (SELECT AD_Reference_ID FROM AD_Reference"
				+ " WHERE ValidationType = 'T' AND AD_Reference_ID IN (SELECT AD_Reference_ID FROM AD_Ref_Table WHERE AD_Table_ID=?))"
				+ " OR AD_Reference_Value_ID IN (SELECT AD_Reference_ID FROM AD_Reference"
				+ " WHERE ValidationType = 'T' AND AD_Reference_ID IN (SELECT AD_Reference_ID FROM AD_Ref_Table WHERE AD_Table_ID=?)))"
				+ " AND AD_Table_ID = ?";
		String values = DB.getSQLValueString(get_TrxName(), sql, Table_BPartnerLocation_ID, Table_BPartnerLocation_ID, AD_Table_ID);
		
		if(null != values)
		{
			value = values.split(";");
			return value;
		}
		return null;
	}
}