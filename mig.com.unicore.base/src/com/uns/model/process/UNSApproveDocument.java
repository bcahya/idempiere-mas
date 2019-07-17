package com.uns.model.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Util;
import org.compiere.wf.MWFActivity;

public class UNSApproveDocument extends SvrProcess {
	
	private int m_AD_Table_ID = 0;
	private String m_WhereClause = null;

	public UNSApproveDocument() 
	{
		super ();
	}

	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] params = getParameter();
		for (int i=0; i<params.length; i++)
		{
			ProcessInfoParameter param = params[i];
			if (param.getParameterName().equals("AD_Table_ID"))
			{
				m_AD_Table_ID = param.getParameterAsInt();
			}
			else if (param.getParameterName().equals("WhereClause"))
			{
				m_WhereClause = param.getParameterAsString();
			}
			else
			{
				log.log(Level.SEVERE, "Unknown parameter " + 
							param.getParameterName());
			}
		}
	}

	@Override
	protected String doIt() throws Exception {
		int countOk = 0, countNotOk = 0;
		StringBuilder sb = new StringBuilder("SELECT * FROM ")
		.append(MWFActivity.Table_Name).append(" WHERE ")
		.append(MWFActivity.COLUMNNAME_Processed).append(" = 'N' ")
		.append(" AND ").append(MWFActivity.COLUMNNAME_WFState).append("='OS'");
		if (m_AD_Table_ID > 0)
		{
			sb.append(" AND ").append(MWFActivity.COLUMNNAME_AD_Table_ID)
			.append(" = ").append(m_AD_Table_ID);
		}
		
		if (!Util.isEmpty(m_WhereClause, true))
		{
			sb.append(" AND ").append(m_WhereClause);
		}
		
		String sql = sb.toString();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			rs = st.executeQuery();
			while (rs.next())
			{
				MWFActivity activity = new MWFActivity(getCtx(), rs, get_TrxName());
				boolean ok = activity.setUserChoice(activity.getAD_User_ID(),
						"Y",  DisplayType.YesNo, "Auto approve from process");
				if (ok)
				{
					countOk++;
				}
				else
				{
					countNotOk++;
				}
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally
		{
			DB.close(rs, st);
		}
		
		return countOk + " record approved, " + countNotOk + " not approved";
	}

}
