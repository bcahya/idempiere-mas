/**
 * 
 */
package com.unicore.model.process;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.unicore.model.MUNSShippingCrewIncentive;
import com.unicore.model.MUNSShippingCrewInctvLine;
import com.unicore.model.X_UNS_ShippingIncentiveSch;
import com.unicore.model.X_UNS_ShippingInctvSch_Line;

/**
 * @author Burhani Adam
 *
 */
public class CalcShippingIncentive extends SvrProcess {

	private String m_processMsg = null;
	private java.sql.Timestamp m_DateFrom = null;
	private java.sql.Timestamp m_DateTo = null;
	private String sql = null;
	private MUNSShippingCrewIncentive ci = null;
	private int m_Org_ID = 0;
	/**
	 * 
	 */
	public CalcShippingIncentive() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter param : params)
		{
			if (param.getParameterName().equals("AD_Org_ID"))
				m_Org_ID = param.getParameterAsInt();
			else if(param.getParameterName().equals("DateFrom"))
				m_DateFrom = param.getParameterAsTimestamp();
			else if(param.getParameterName().equals("DateTo"))
				m_DateTo = param.getParameterAsTimestamp();
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		return calcShippingIncentive();
	}
	
	public String calcShippingIncentive()
	{
		m_processMsg = null;
		int idSch = 0;
		String getSch = null;
		
//		if(m_Org_ID > 0)
//		{
		
		if(m_Org_ID <= 0)
			return "Please define organization for running this process";
		
		getSch = "SELECT UNS_ShippingIncentiveSch_ID FROM UNS_ShippingIncentiveSch WHERE AD_Org_ID=? AND ValidFrom <= ?"
				+ " AND DocStatus = 'CO'";
		idSch = DB.getSQLValue(get_TrxName(), getSch, m_Org_ID, m_DateFrom);
//		}
//		if(idSch <= 0)
//		{
//			getSch = "SELECT UNS_ShippingIncentiveSch_ID FROM UNS_ShippingIncentiveSch WHERE ValidFrom <= ?"
//						+ " AND DocStatus = 'CO'";
//			idSch = DB.getSQLValue(get_TrxName(), getSch, m_DateFrom);
//		}
		
		if(idSch <= 0)
			return "Schema Incentive not found";
		
		String finalWhereClause = "sh.DocStatus IN ('CO', 'CL') AND (sh.DateDoc BETWEEN ? AND ?)"
				+ " AND EXISTS (SELECT 1 FROM UNS_ShippingFreight sf WHERE sf.UNS_Shipping_ID = sh.UNS_Shipping_ID)"
				+ " AND NOT EXISTS (SELECT 1 FROM UNS_ShippingCrewInctv_Line cil"
				+ " WHERE sh.UNS_Shipping_ID = cil.UNS_Shipping_ID AND cil.IncentiveType = ?)";
		if(m_Org_ID > 0)
			finalWhereClause += " AND sh.AD_Org_ID="+m_Org_ID + " ";
		
		m_processMsg = null;
		
		String getSchLine = "SELECT sc.CrewType, scl.UNS_ShippingInctvSch_Line_ID, scl.IncentiveType, scl.MinRitase, scl.Reason,"
				+ " scl.IncentiveValue, sc.AD_Org_ID, sc.UNS_ShippingIncentiveSch_ID, scl.Origin_ID, scl.Destination_ID,"
				+ " scl.HelperValue, scl.UNS_Rayon_ID FROM UNS_ShippingInctvSch_Line scl"
				+ " INNER JOIN UNS_ShippingIncentiveSch sc ON sc.UNS_ShippingIncentiveSch_ID = scl.UNS_ShippingIncentiveSch_ID"
				+ " WHERE sc.UNS_ShippingIncentiveSch_ID=?";
		PreparedStatement stmtLine = null;
		ResultSet rsLine = null;
		
		try
		{
			stmtLine = DB.prepareStatement(getSchLine, get_TrxName());
			stmtLine.setInt(1, idSch);
//			stmtLine.setTimestamp(2, m_DateTo);
//			stmtLine.setInt(3, m_Org_ID);
			rsLine = stmtLine.executeQuery();
		
			while(rsLine.next())
			{
				// TODO
				if(rsLine.getString(3).equals(X_UNS_ShippingInctvSch_Line.INCENTIVETYPE_DailyIncentive))
				{
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Driver))
					{
						sql = "SELECT sh.UNS_Employee_ID FROM UNS_Shipping sh"
								+ " WHERE " + finalWhereClause + " AND COUNT(*) >= ?"
								+ " AND UNS_Employee_ID IS NOT NULL"
								+ " GROUP BY sh.UNS_Employee_ID, sh.DateDoc";
					}
				
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Helper1))
					{						
						sql = "SELECT sh.Helper1_ID FROM UNS_Shipping sh"
							+ " WHERE " + finalWhereClause + " AND COUNT(*) >= ?"
									+ "AND Helper1_ID IS NOT NULL"
							+ " GROUP BY sh.Helper1_ID, sh.DateDoc";				
					}
					
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Helper2))
					{	
						sql = "SELECT sh.Helper2_ID FROM UNS_Shipping sh"
								+ " WHERE " + finalWhereClause + " AND COUNT(*) >= ?"
										+ "AND Helper2_ID IS NOT NULL"
								+ " GROUP BY sh.Helper2_ID, sh.DateDoc";
					}
					
					PreparedStatement stmt = null;
					ResultSet rs = null;
					
					try
					{
						stmt = DB.prepareStatement(sql, get_TrxName());
						stmt.setTimestamp(1, m_DateFrom);
						stmt.setTimestamp(2, m_DateTo);
						stmt.setString(3, rsLine.getString(3));
						stmt.setInt(4, rsLine.getInt(4));
						rs = stmt.executeQuery();
						while(rs.next())
						{
							ci = MUNSShippingCrewIncentive.getOfEmployee(getCtx(), rs.getInt(1), m_Org_ID, get_TrxName());
							if(ci == null)
							{
								ci = new MUNSShippingCrewIncentive(getCtx(), 0, get_TrxName());
								ci.setAD_Org_ID(m_Org_ID);
								ci.setUNS_Employee_ID(rs.getInt(1));
								ci.setDateDoc(new Timestamp(System.currentTimeMillis()));
								ci.setDateFrom(m_DateFrom);
								ci.setDateTo(m_DateTo);
								if(!ci.save())
									m_processMsg = "Failed when trying create Crew Incentive header";
							}
							
							MUNSShippingCrewInctvLine il = new MUNSShippingCrewInctvLine(getCtx(), 0, get_TrxName());
							il.setUNS_ShippingCrewIncentive_ID(ci.get_ID());
							il.setIncentiveType(rsLine.getString(3));
							il.setAmount(rsLine.getBigDecimal(6));
							il.setHelperAmount(rsLine.getBigDecimal(11));
							il.setUNS_ShippingIncentiveSch_ID(rsLine.getInt(8));
							il.setUNS_ShippingInctvSch_Line_ID(rsLine.getInt(2));
							if(!il.save())
								m_processMsg = "Failed when trying create Crew Incentive Line";
						}
					}
					catch (SQLException e)
					{
						m_processMsg = e.getMessage();
						return m_processMsg;
					}
				}
				
				// TODO
				if(rsLine.getString(3).equals(X_UNS_ShippingInctvSch_Line.INCENTIVETYPE_Rittase))
				{
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Driver))
					{
						sql = "SELECT sh.UNS_Employee_ID, COUNT(sh.*) FROM UNS_Shipping sh WHERE "
							+ " UNS_Employee_ID IS NOT NULL AND " + finalWhereClause
							+ " GROUP BY sh.UNS_Employee_ID" ;
					}	
					
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Helper1))
					{
						sql = "SELECT sh.helper1_ID, COUNT(sh.*) FROM UNS_Shipping sh WHERE "
								+ "sh.Helper1_ID IS NOT NULL AND " + finalWhereClause
								+ " GROUP BY sh.Helper1_ID" ;
					}
					
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Helper2))
					{
						sql = "SELECT sh.helper2_ID, COUNT(sh.*) FROM UNS_Shipping sh WHERE "
								+ "sh.Helper2_ID IS NOT NULL AND " + finalWhereClause
								+ " GROUP BY sh.Helper2_ID" ;
					}
					PreparedStatement stmt = null;
					ResultSet rs = null;
					
					try
					{
						stmt = DB.prepareStatement(sql, get_TrxName());
						stmt.setTimestamp(1, m_DateFrom);
						stmt.setTimestamp(2, m_DateTo);
						stmt.setString(3, rsLine.getString(3));
						rs = stmt.executeQuery();
						while(rs.next())
						{
							ci = MUNSShippingCrewIncentive.getOfEmployee(getCtx() ,rs.getInt(1), m_Org_ID, get_TrxName());
							if(ci == null)
							{
								ci = new MUNSShippingCrewIncentive(getCtx(), 0, get_TrxName());
								ci.setAD_Org_ID(m_Org_ID);
								ci.setUNS_Employee_ID(rs.getInt(1));
								ci.setDateDoc(new Timestamp(System.currentTimeMillis()));
								ci.setDateFrom(m_DateFrom);
								ci.setDateTo(m_DateTo);
								if(!ci.save())
									m_processMsg = "Failed when trying create Crew Incentive header";
							}
							
							MUNSShippingCrewInctvLine il = new MUNSShippingCrewInctvLine(getCtx(), 0, get_TrxName());
							il.setUNS_ShippingCrewIncentive_ID(ci.get_ID());
							il.setAmount(rsLine.getBigDecimal(6));
							il.setHelperAmount(rsLine.getBigDecimal(11));
							il.setIncentiveType(rsLine.getString(3));
							il.setUNS_ShippingIncentiveSch_ID(rsLine.getInt(8));
							il.setUNS_ShippingInctvSch_Line_ID(rsLine.getInt(2));
							if(!il.save())
								m_processMsg = "Failed when trying create Crew Incentive Line";
						}
					}
					catch (SQLException e)
					{
						m_processMsg = e.getMessage();
						return m_processMsg;
					}
				}
						
				// TODO
				if(rsLine.getString(3).equals(X_UNS_ShippingInctvSch_Line.INCENTIVETYPE_IncentiveDeduction))
				{
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Driver))
					{
						sql = "SELECT sh.UNS_Employee_ID, sh.UNS_Shipping_ID, COUNT(ro.isCancelled) FROM UNS_Shipping sh"
							+ " INNER JOIN UNS_ShippingFreight sf ON sf.UNS_Shipping_ID = sh.UNS_Shipping_ID"
							+ " INNER JOIN UNS_PL_Return plr ON plr.UNS_PackingList_ID = sf.UNS_PackingList_ID"
							+ " INNER JOIN UNS_PL_ReturnOrder ro ON ro.UNS_PL_Return_ID = plr.UNS_PL_Return_ID"
							+ " WHERE " + finalWhereClause + " AND ro.isCancelled='Y' AND ro.Reason = ?"
							+ " AND UNS_Employee_ID IS NOT NULL"
							+ " GROUP BY sh.UNS_Shipping_ID";
					}	
					
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Helper1))
					{	
						sql = "SELECT sh.Helper1_ID, sh.UNS_Shipping_ID, COUNT(ro.isCancelled) FROM UNS_Shipping sh"
								+ " INNER JOIN UNS_ShippingFreight sf ON sf.UNS_Shipping_ID = sh.UNS_Shipping_ID"
								+ " INNER JOIN UNS_PL_Return plr ON plr.UNS_PackingList_ID = sf.UNS_PackingList_ID"
								+ " INNER JOIN UNS_PL_ReturnOrder ro ON ro.UNS_PL_Return_ID = plr.UNS_PL_Return_ID"
								+ " WHERE " + finalWhereClause + " AND ro.isCancelled='Y' AND ro.Reason = ?"
								+ " AND sh.Helper1_ID IS NOT NULL"
								+ " GROUP BY sh.UNS_Shipping_ID";
					}
					
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Helper2))
					{	
						sql = "SELECT sh.Helper2_ID, sh.UNS_Shipping_ID, COUNT(ro.isCancelled) FROM UNS_Shipping sh"
								+ " INNER JOIN UNS_ShippingFreight sf ON sf.UNS_Shipping_ID = sh.UNS_Shipping_ID"
								+ " INNER JOIN UNS_PL_Return plr ON plr.UNS_PackingList_ID = sf.UNS_PackingList_ID"
								+ " INNER JOIN UNS_PL_ReturnOrder ro ON ro.UNS_PL_Return_ID = plr.UNS_PL_Return_ID"
								+ " WHERE " + finalWhereClause + " AND ro.isCancelled='Y' AND ro.Reason = ?"
								+ " AND sh.Helper2_ID IS NOT NULL"
								+ " GROUP BY sh.UNS_Shipping_ID";
					}
					
					PreparedStatement stmt = null;
					ResultSet rs = null;
					
					try
					{
						stmt = DB.prepareStatement(sql, get_TrxName());
						stmt.setTimestamp(1, m_DateFrom);
						stmt.setTimestamp(2, m_DateTo);
						stmt.setString(3, rsLine.getString(5));
						stmt.setString(4, rsLine.getString(3));
						rs = stmt.executeQuery();
						while(rs.next())
						{
							ci = MUNSShippingCrewIncentive.getOfEmployee(getCtx() ,rs.getInt(1), m_Org_ID, get_TrxName());
							if(ci == null)
							{
								ci = new MUNSShippingCrewIncentive(getCtx(), 0, get_TrxName());
								ci.setAD_Org_ID(m_Org_ID);
								ci.setUNS_Employee_ID(rs.getInt(1));
								ci.setDateDoc(new Timestamp(System.currentTimeMillis()));
								ci.setDateFrom(m_DateFrom);
								ci.setDateTo(m_DateTo);
								if(!ci.save())
									m_processMsg = "Failed when trying create Crew Incentive header";
							}
							
							MUNSShippingCrewInctvLine il = new MUNSShippingCrewInctvLine(getCtx(), 0, get_TrxName());
							il.setUNS_ShippingCrewIncentive_ID(ci.get_ID());
							il.setUNS_Shipping_ID(rs.getInt(2));
							il.setAmount(rsLine.getBigDecimal(6).multiply(rs.getBigDecimal(3)));
							il.setHelperAmount(rsLine.getBigDecimal(11).multiply(rs.getBigDecimal(3)));
							il.setIncentiveType(rsLine.getString(3));
							il.setUNS_ShippingIncentiveSch_ID(rsLine.getInt(8));
							il.setUNS_ShippingInctvSch_Line_ID(rsLine.getInt(2));
							if(!il.save())
								m_processMsg = "Failed when trying create Crew Incentive Line";
						}
					}
					catch (SQLException e)
					{
						m_processMsg = e.getMessage();
						return m_processMsg;
					}
				}

				// TODO
				if(rsLine.getString(3).equals(X_UNS_ShippingInctvSch_Line.INCENTIVETYPE_OutletIncentive))
				{
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Driver))
					{
						sql = "SELECT sh.UNS_Employee_ID, sh.UNS_Shipping_ID, COUNT(ro.isCancelled) FROM UNS_Shipping sh"
							+ " INNER JOIN UNS_ShippingFreight sf ON sf.UNS_ShippingFreight_ID = sh.UNS_ShippingFreight_ID"
							+ " INNER JOIN UNS_PL_Return plr ON plr.UNS_PackingList_ID = sf.UNS_PackingList_ID"
							+ " INNER JOIN UNS_PL_ReturnOrder ro ON ro.UNS_PL_Return_ID = plr.UNS_PL_Return_ID"
							+ " WHERE " +  finalWhereClause + " AND ro.isCancelled='N'"
							+ " AND sh.UNS_Employee_ID IS NOT NULL"
							+ " GROUP BY sh.UNS_Shipping_ID";
					}
					
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Helper1))
					{
						sql = "SELECT sh.Helper1_ID, sh.UNS_Shipping_ID, COUNT(ro.isCancelled) FROM UNS_Shipping sh"
								+ " INNER JOIN UNS_ShippingFreight sf ON sf.UNS_ShippingFreight_ID = sh.UNS_ShippingFreight_ID"
								+ " INNER JOIN UNS_PL_Return plr ON plr.UNS_PackingList_ID = sf.UNS_PackingList_ID"
								+ " INNER JOIN UNS_PL_ReturnOrder ro ON ro.UNS_PL_Return_ID = plr.UNS_PL_Return_ID"
								+ " WHERE " +  finalWhereClause + " AND ro.isCancelled='N'"
								+ " AND sh.Helper1_ID IS NOT NULL"
								+ " GROUP BY sh.UNS_Shipping_ID";
					}	
					
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Helper2))
					{
						sql = "SELECT sh.Helper2_ID, sh.UNS_Shipping_ID, COUNT(ro.isCancelled) FROM UNS_Shipping sh"
								+ " INNER JOIN UNS_ShippingFreight sf ON sf.UNS_ShippingFreight_ID = sh.UNS_ShippingFreight_ID"
								+ " INNER JOIN UNS_PL_Return plr ON plr.UNS_PackingList_ID = sf.UNS_PackingList_ID"
								+ " INNER JOIN UNS_PL_ReturnOrder ro ON ro.UNS_PL_Return_ID = plr.UNS_PL_Return_ID"
								+ " WHERE " +  finalWhereClause + " AND ro.isCancelled='N'"
								+ " AND sh.Helper2_ID IS NOT NULL"
								+ " GROUP BY sh.UNS_Shipping_ID";
					}
					PreparedStatement stmt = null;
					ResultSet rs = null;
					
					try
					{
						stmt = DB.prepareStatement(sql, get_TrxName());
						stmt.setTimestamp(1, m_DateFrom);
						stmt.setTimestamp(2, m_DateTo);
						stmt.setString(3, rsLine.getString(3));
						rs = stmt.executeQuery();
						while(rs.next())
						{
							ci = MUNSShippingCrewIncentive.getOfEmployee(getCtx() ,rs.getInt(1), m_Org_ID, get_TrxName());
							if(ci == null)
							{
								ci = new MUNSShippingCrewIncentive(getCtx(), 0, get_TrxName());
								ci.setAD_Org_ID(m_Org_ID);
								ci.setUNS_Employee_ID(rs.getInt(1));
								ci.setDateDoc(new Timestamp(System.currentTimeMillis()));
								ci.setDateFrom(m_DateFrom);
								ci.setDateTo(m_DateTo);
								if(!ci.save())
									m_processMsg = "Failed when trying create Crew Incentive header";
							}
							
							MUNSShippingCrewInctvLine il = new MUNSShippingCrewInctvLine(getCtx(), 0, get_TrxName());
							il.setUNS_ShippingCrewIncentive_ID(ci.get_ID());
							il.setUNS_Shipping_ID(rs.getInt(2));
							il.setAmount(rsLine.getBigDecimal(6).multiply(rs.getBigDecimal(3)));
							il.setHelperAmount(rsLine.getBigDecimal(11).multiply(rs.getBigDecimal(3)));
							il.setIncentiveType(rsLine.getString(3));
							il.setUNS_ShippingIncentiveSch_ID(rsLine.getInt(8));
							il.setUNS_ShippingInctvSch_Line_ID(rsLine.getInt(2));
							if(!il.save())
								m_processMsg = "Failed when trying create Crew Incentive Line";
						}
					}
					catch (SQLException e)
					{
						m_processMsg = e.getMessage();
						return m_processMsg;
					}
				}
				
				// TODO
				if(rsLine.getString(3).equals(X_UNS_ShippingInctvSch_Line.INCENTIVETYPE_CityToCity))
				{
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Driver))
					{
						sql = "SELECT sh.UNS_Employee_ID, sh.UNS_Shipping_ID, 1 FROM UNS_Shipping sh"
							+ " WHERE " +  finalWhereClause + " AND sh.Origin_ID=? AND sh.Destination_ID=?"
							+ " AND sh.UNS_Employee_ID IS NOT NULL AND sh.UseCity = 'Y'";
					}
					
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Helper1))
					{
						sql = "SELECT sh.Helper1_ID, sh.UNS_Shipping_ID, 1 FROM UNS_Shipping sh"
							+ " WHERE " +  finalWhereClause + " AND sh.Origin_ID=? AND sh.Destination_ID=?"
							+ " AND sh.Helper1_ID IS NOT NULL AND sh.UseCity = 'Y'";
					}
					
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Helper2))
					{
						sql = "SELECT sh.Helper2_ID, sh.UNS_Shipping_ID, 1 FROM UNS_Shipping sh"
							+ " WHERE " +  finalWhereClause + " AND sh.Origin_ID=? AND sh.Destination_ID=?"
							+ " AND sh.Helper2_ID IS NOT NULL AND sh.UseCity = 'Y'";
					}
					
					PreparedStatement stmt = null;
					ResultSet rs = null;
					
					try
					{
						stmt = DB.prepareStatement(sql, get_TrxName());
						stmt.setTimestamp(1, m_DateFrom);
						stmt.setTimestamp(2, m_DateTo);
						stmt.setString(3, rsLine.getString(3));
						stmt.setInt(4, rsLine.getInt(9));
						stmt.setInt(5, rsLine.getInt(10));
						
						rs = stmt.executeQuery();
						while(rs.next())
						{
							ci = MUNSShippingCrewIncentive.getOfEmployee(getCtx() ,rs.getInt(1), m_Org_ID, get_TrxName());
							if(ci == null)
							{
								ci = new MUNSShippingCrewIncentive(getCtx(), 0, get_TrxName());
								ci.setAD_Org_ID(m_Org_ID);
								ci.setUNS_Employee_ID(rs.getInt(1));
								ci.setDateDoc(new Timestamp(System.currentTimeMillis()));
								ci.setDateFrom(m_DateFrom);
								ci.setDateTo(m_DateTo);
								if(!ci.save())
									m_processMsg = "Failed when trying create Crew Incentive header";
							}
							
							MUNSShippingCrewInctvLine il = new MUNSShippingCrewInctvLine(getCtx(), 0, get_TrxName());
							il.setUNS_ShippingCrewIncentive_ID(ci.get_ID());
							il.setUNS_Shipping_ID(rs.getInt(2));
							il.setAmount(rsLine.getBigDecimal(6).multiply(rs.getBigDecimal(3)));
							il.setHelperAmount(rsLine.getBigDecimal(11).multiply(rs.getBigDecimal(3)));
							il.setIncentiveType(rsLine.getString(3));
							il.setUNS_ShippingIncentiveSch_ID(rsLine.getInt(8));
							il.setUNS_ShippingInctvSch_Line_ID(rsLine.getInt(2));
							if(!il.save())
								m_processMsg = "Failed when trying create Crew Incentive Line";
						}
					}
					catch (SQLException e)
					{
						m_processMsg = e.getMessage();
						return m_processMsg;
					}
				}
				
				// TODO
				if(rsLine.getString(3).equals(X_UNS_ShippingInctvSch_Line.INCENTIVETYPE_Rayon))
				{
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Driver))
					{
						sql = "SELECT sh.UNS_Employee_ID, sh.UNS_Shipping_ID, 1 FROM UNS_Shipping sh"
							+ " WHERE " +  finalWhereClause + " AND sh.UNS_Rayon_ID=?"
							+ " AND sh.UNS_Employee_ID IS NOT NULL AND sh.UseCity = 'N'";
					}
					
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Helper1))
					{
						sql = "SELECT sh.Helper1_ID, sh.UNS_Shipping_ID, 1 FROM UNS_Shipping sh"
							+ " WHERE " +  finalWhereClause + " AND sh.UNS_Rayon_ID=?"
							+ " AND sh.Helper1_ID IS NOT NULL AND sh.UseCity = 'N'";
					}
					
					if(rsLine.getString(1).equals(X_UNS_ShippingIncentiveSch.CREWTYPE_Helper2))
					{
						sql = "SELECT sh.Helper2_ID, sh.UNS_Shipping_ID, 1 FROM UNS_Shipping sh"
							+ " WHERE " +  finalWhereClause + " AND sh.UNS_Rayon_ID=?"
							+ " AND sh.Helper2_ID IS NOT NULL AND sh.UseCity = 'N'";
					}
					
					PreparedStatement stmt = null;
					ResultSet rs = null;
					
					try
					{
						stmt = DB.prepareStatement(sql, get_TrxName());
						stmt.setTimestamp(1, m_DateFrom);
						stmt.setTimestamp(2, m_DateTo);
						stmt.setString(3, rsLine.getString(3));
						stmt.setInt(3, rsLine.getInt(12));
						rs = stmt.executeQuery();
						while(rs.next())
						{
							ci = MUNSShippingCrewIncentive.getOfEmployee(getCtx() ,rs.getInt(1), m_Org_ID, get_TrxName());
							if(ci == null)
							{
								ci = new MUNSShippingCrewIncentive(getCtx(), 0, get_TrxName());
								ci.setAD_Org_ID(m_Org_ID);
								ci.setUNS_Employee_ID(rs.getInt(1));
								ci.setDateDoc(new Timestamp(System.currentTimeMillis()));
								ci.setDateFrom(m_DateFrom);
								ci.setDateTo(m_DateTo);
								if(!ci.save())
									m_processMsg = "Failed when trying create Crew Incentive header";
							}
							
							MUNSShippingCrewInctvLine il = new MUNSShippingCrewInctvLine(getCtx(), 0, get_TrxName());
							il.setUNS_ShippingCrewIncentive_ID(ci.get_ID());
							il.setUNS_Shipping_ID(rs.getInt(2));
							il.setAmount(rsLine.getBigDecimal(6).multiply(rs.getBigDecimal(3)));
							il.setHelperAmount(rsLine.getBigDecimal(11).multiply(rs.getBigDecimal(3)));
							il.setIncentiveType(rsLine.getString(3));
							il.setUNS_ShippingIncentiveSch_ID(rsLine.getInt(8));
							il.setUNS_ShippingInctvSch_Line_ID(rsLine.getInt(2));
							if(!il.save())
								m_processMsg = "Failed when trying create Crew Incentive Line";
						}
					}
					catch (SQLException e)
					{
						m_processMsg = e.getMessage();
						return m_processMsg;
					}
				}
			}
		}
		catch (SQLException e)
		{
			m_processMsg = e.getMessage();
			return m_processMsg;
		}
		
		return "Success";
	}
}