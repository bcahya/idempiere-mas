/**
 * 
 */
package com.uns.model.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.model.MFactAcct;
import org.compiere.model.MMatchInv;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.KeyNamePair;

/**
 * @author menjangan
 *
 */
public class VoidPostErrorReceiptProcessor extends SvrProcess {

	private Timestamp m_dateTo = null;
	private Timestamp m_dateFrom = null;
	private String m_errorMsg = null;
	
	/**
	 * 
	 */
	public VoidPostErrorReceiptProcessor() 
	{
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{	
		ProcessInfoParameter[] params = getParameter();
		for (int i=0; i<params.length; i++)
		{
			if ("DateTo".equals(params[i].getParameterName()))
				m_dateTo = params[i].getParameterAsTimestamp();
			else if ("DateFrom".equals(params[i].getParameterName()))
				m_dateFrom = params[i].getParameterAsTimestamp();
			else
				log.log(Level.SEVERE, "Unknown Parameter " + params[i].getParameterName());
		}
		
		if (m_dateTo == null)
			m_dateTo = Timestamp.valueOf("2016-12-31 23:59:59");
		if (m_dateFrom == null)
			m_dateFrom = Timestamp.valueOf("1988-01-01 00:00:01");
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		if (!processReceipt())
			throw new AdempiereException(m_errorMsg);
		return null;
	}
	
	private List<KeyNamePair> getRecords ()
	{
		String sql = "SELECT M_InOut_ID, DocumentNo FROM M_InOut WHERE "
				+ " Posted = 'E' AND DocStatus IN ('CO','CL','RE') AND "
				+ " MovementDate BETWEEN ? AND ?  AND MovementType "
				+ " IN ('V+', 'V-', 'C+') ORDER BY DocumentNo";
		List<KeyNamePair> list = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			st.setTimestamp(1, m_dateFrom);
			st.setTimestamp(2, m_dateTo);
			rs = st.executeQuery();
			while (rs.next())
				list.add(new KeyNamePair(rs.getInt(1), rs.getString(2)));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return list;
	}
	
	
	private boolean processReceipt ()
	{
		processUI.statusUpdate("Colect receipt & return to vendor record...");
		List<KeyNamePair> list = getRecords();
		String sql;
		for (int i=0; i<list.size(); i++)
		{
			int displayIdx = i+1;
			processUI.statusUpdate("Processing document " + list.get(i).getName() 
					+ " - [" + displayIdx + " of " + list.size() + "]");
			sql = "UPDATE M_InOutLine SET Description = '***Voided={MovementQty=' || "
					+ " MovementQty || '}***', MovementQty = 0 WHERE M_InOut_ID = ?";
			int ok = DB.executeUpdate(sql, list.get(i).getKey(), false, get_TrxName());
			if (ok == -1)
			{
				m_errorMsg = "Failed when try to update inout line. InOut Doc no " + list.get(i).getName();	
				break;
			}
			sql = "UPDATE M_InOut SET DocStatus = 'VO', Posted = 'Y', DocAction = '--', Description ="
					+ " Description || '***Voided By Query***' WHERE M_InOut_ID = ?";
			ok = DB.executeUpdate(sql, list.get(i).getKey(), false, get_TrxName());
			if (ok == -1)
			{
				m_errorMsg = "Failed when try to update inout. InOut Doc no " + list.get(i).getName();	
				break;
			}
			
			sql = "SELECT ARRAY_TO_STRING(ARRAY_AGG (M_MatchInv_ID), ';') FROM M_MatchInv WHERE M_InOutLine_ID "
					+ " IN (SELECT M_InOutLine_ID FROM M_InOutLine WHERE M_InOut_ID = ?)";
			String matchInvIDsStr = DB.getSQLValueString(get_TrxName(), sql, list.get(i).getKey());
			if (matchInvIDsStr != null)
			{
				String[] matchInvIDsStrArr = matchInvIDsStr.split(";");
				for (String matchInvIDStr : matchInvIDsStrArr)
				{
					try
					{
						int matchInvID = Integer.valueOf(matchInvIDStr);
						MFactAcct.deleteEx(MMatchInv.Table_ID, matchInvID, get_TrxName());
					}
					catch (NumberFormatException ex)
					{
						m_errorMsg =  "Failed to confirm match invoice from string to integer "
								+ ex.getMessage();
						break;
					}
					catch (DBException ex)
					{
						m_errorMsg = ex.getMessage();
						break;
					}
				}
				if (m_errorMsg != null)
					break;
			}
			
			sql = "DELETE FROM M_MatchInv WHERE M_InOutLine_ID IN (SELECT M_InOutLine_ID "
					+ " FROM M_InOutLine WHERE M_InOut_ID = ?)";
			ok = DB.executeUpdate(sql, list.get(i).getKey(), false, get_TrxName());
			if (ok == -1)
			{
				m_errorMsg = "Failed when try to Delete Match Inv. InOut Doc no " + list.get(i).getName();	
				break;
			}
		}
		if (m_errorMsg != null)
		{
			return false;
		}
		
		return true;
	}
}
