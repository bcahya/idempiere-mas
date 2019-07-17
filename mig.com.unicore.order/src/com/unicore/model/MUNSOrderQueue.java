package com.unicore.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

public class MUNSOrderQueue extends X_UNS_OrderQueue {
	
	private MUNSOrderQueueLine[] m_OrderQueueLine = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8405426977540976323L;

	public MUNSOrderQueue(Properties ctx, int UNS_OrderQueue_ID, String trxName) {
		super(ctx, UNS_OrderQueue_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSOrderQueue(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSOrderQueueLine[] getLines(int UNS_OrderQueue_ID)
	{
		if (m_OrderQueueLine != null)
			return m_OrderQueueLine;
		
		List<MUNSOrderQueueLine> list = null;
		list = Query.get(getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSOrderQueueLine.Table_Name, 
						 "UNS_OrderQueue_ID=" + UNS_OrderQueue_ID, get_TrxName())
					.list();
		MUNSOrderQueueLine[] retValue = new MUNSOrderQueueLine[list.size()];
		list.toArray(retValue);
		m_OrderQueueLine = retValue;
		
		return retValue;
	}
	
	/**
	 * @param C_OrderLine_ID
	 * @return ID
	 */
	
	public static MUNSOrderQueue get(int C_OrderLine_ID, int AD_Org_ID, String trxName)
	{
		MUNSOrderQueue record = null;
		String sql = "SELECT * FROM " + Table_Name + " WHERE " + COLUMNNAME_M_Product_ID + " = ?"
				+ " AND " + COLUMNNAME_AD_Org_ID + " = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, trxName);
			st.setInt(1, C_OrderLine_ID);
			st.setInt(2, AD_Org_ID);
			rs = st.executeQuery();
			if(rs.next())
			{
				record = new MUNSOrderQueue(Env.getCtx(), rs, trxName);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, st);

		}
		return record;
	}

}
