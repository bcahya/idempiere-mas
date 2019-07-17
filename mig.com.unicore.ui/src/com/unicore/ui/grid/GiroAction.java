/**
 * @author UntaCore ERP. (Burhani Adam)
 */

package com.unicore.ui.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;
import org.compiere.minigrid.IMiniTable;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import com.uns.model.MUNSCustomerBG;
import com.uns.model.MUNSCustomerBGAction;

/**
 *  Create Charge from Accounts
 *
 *  @author Jorg Janke
 *  @version $Id: Charge.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class GiroAction
{
	/**	Window No			*/
	public int         m_WindowNo = 0;

	/**	Logger			*/
	public static CLogger log = CLogger.getCLogger(GiroAction.class);
	
	/**
	 *  @param isOnlyOverDue
	 *  @param bPartner_ID
	 *  @param bpGroup_ID
	 *  @param bank_ID
	 *  @param SalesRep_ID
	 *  @param SalesRegion_ID
	 *  @param dateFrom
	 *  @param dateTo
	 *  @param isUseDueDate
	 */
	public Vector<Vector<Object>> getData(boolean isOnlyOverDue, int bPartner_ID, int rayon_ID, int bank_ID, int SalesRep_ID
			, int SalesRegion_ID, Timestamp dateFrom, Timestamp dateTo, boolean isUseDueDate, String giro)
	{
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT bp.Name AS Partner, b.Name AS Bank, bg.UNS_CustomerBG_ID, bg.Name AS Giro,"
				+ " bg.ReceiptDate AS Receipt, bg.DueDate AS Due,"
				+ " bg.GrandTotal AS Total, bg.PaidAmt AS Allocated, bg.LimitAmt AS Limit, COALESCE(us.Name, '') AS Sales,"
				+ " COALESCE((SELECT sl.Name FROM AD_User sl WHERE EXISTS (SELECT 1 FROM UNS_CustomerBG_Action bga"
				+ " WHERE bga.SalesRep_ID = sl.AD_User_ID AND bga.DocStatus IN ('CO', 'CL') AND"
				+ " bga.UNS_CustomerBG_ID = bg.UNS_CustomerBG_ID AND bga.Action = 'HB')),'') AS Assigned"
				+ " FROM UNS_CustomerBG bg"
				+ " INNER JOIN C_Bank b ON b.C_Bank_ID = bg.C_Bank_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = bg.C_BPartner_ID"
				+ " LEFT JOIN AD_User us ON us.AD_User_ID = bg.SalesRep_ID"
				+ " WHERE bg.DocStatus NOT IN ('VO', 'CO', 'RE', 'CL')");
		if(!Util.isEmpty(giro, true))
			sql.append(" AND UPPER(bg.Name) Like UPPER('").append(giro).append("')");
		else
		{
			if (isUseDueDate)
				sql.append(" AND bg.DueDate BETWEEN ? AND ?");
			else
				sql.append(" AND bg.ReceiptDate BETWEEN ? AND ?");
			if (isOnlyOverDue)
				sql.append(" AND bg.DueDate <= '").append(dateTo).append("'");
			if (bPartner_ID > 0 )
				sql.append(" AND bg.C_BPartner_ID = ").append(bPartner_ID);
			if (rayon_ID > 0)
				sql.append(" AND EXISTS (SELECT 1 FROM C_BPartner_Location bpl WHERE bpl.C_BPartner_ID = bp.C_BPartner_ID"
						+ " AND bpl.UNS_Rayon_ID = ").append(rayon_ID).append(")");
			if (bank_ID > 0)
				sql.append(" AND b.C_Bank_ID = " + bank_ID);
			if (SalesRep_ID > 0)
				sql.append(" AND bg.SalesRep_ID = ").append(SalesRep_ID);
			if (SalesRegion_ID > 0)
				sql.append(" AND EXISTS (SELECT 1 FROM AD_Org org WHERE org.AD_Org_ID = bg.AD_Org_ID"
						+ " AND org.C_SalesRegion_ID = ").append(SalesRegion_ID).append(")");
		}
		
		sql.append(" ORDER BY bg.DueDate ASC, bp.Name ASC");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			if(Util.isEmpty(giro, true))
			{
				pstmt.setTimestamp(1, dateFrom);
				pstmt.setTimestamp(2, dateTo);
			}
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(10);
				line.add(new Boolean(false));       //  0-Selection
				line.add(rs.getString(1));
				line.add(rs.getString(2));
				KeyNamePair pp = new KeyNamePair(rs.getInt(3), rs.getString(4));
				line.add(pp);                       //  1-Value
				line.add(rs.getTimestamp(5));
				line.add(rs.getTimestamp(6));
				line.add(rs.getBigDecimal(7));
				line.add(rs.getBigDecimal(8));
				line.add(rs.getBigDecimal(9));
				line.add(rs.getString(10));          //  2-Name
				line.add(rs.getString(11));
				data.add(line);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return data;
	}
	
	public Vector<String> getColumnGiro()
	{
		//  Header Info
		Vector<String> columnNames = new Vector<String>(12);
		columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		columnNames.add(Msg.translate(Env.getCtx(), "C_Bank_ID"));
		columnNames.add("Giro No");
		columnNames.add(Msg.translate(Env.getCtx(), "ReceiptDate"));
		columnNames.add(Msg.translate(Env.getCtx(), "DueDate"));
		columnNames.add("GrandTotal");
		columnNames.add(Msg.translate(Env.getCtx(), "Allocated"));
		columnNames.add(Msg.translate(Env.getCtx(), "LimitAmt"));
		columnNames.add(Msg.translate(Env.getCtx(), "SalesRep_ID"));
		columnNames.add("Assigned");
		return columnNames;
	}
	
	public void setColumnGiro(IMiniTable dataTable)
	{
		dataTable.setColumnClass(0, Boolean.class, false);      //  0-Selection
		dataTable.setColumnClass(1, String.class, true);        //  1-Value
		dataTable.setColumnClass(2, String.class, true);        //  2-Name
		dataTable.setColumnClass(3, String.class, true);        //  2-Name
		dataTable.setColumnClass(4, Timestamp.class, true);       //  3-Expense
		dataTable.setColumnClass(5, Timestamp.class, true);       //  3-Expense
		dataTable.setColumnClass(6, BigDecimal.class, true);       //  3-Expense
		dataTable.setColumnClass(7, BigDecimal.class, true);       //  3-Expense
		dataTable.setColumnClass(8, BigDecimal.class, true);       //  3-Expense
		dataTable.setColumnClass(9, String.class, true);       //  3-Expense
		dataTable.setColumnClass(10, String.class, true);       //  3-Expense
		//  Table UI
		dataTable.autoSize();
	}

	public boolean saveGiro(IMiniTable miniTable, String action, int C_BankAccount_ID,
			Timestamp dateAction, int assigned_ID, String trxName)
	{		
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{				
				KeyNamePair pp = (KeyNamePair) miniTable.getValueAt(i, 3); // Product
				MUNSCustomerBG bg = new MUNSCustomerBG(Env.getCtx(), new Integer (pp.getKey()), trxName);
				
				String sql = "SELECT UNS_CustomerBG_Action_ID FROM UNS_CustomerBG_Action"
						+ " WHERE Processed = 'N' AND UNS_CustomerBG_ID = ?";
				int action_ID = DB.getSQLValue(trxName, sql, bg.get_ID());
				MUNSCustomerBGAction bgAction = null;
				if(action_ID > 0)
					bgAction = new MUNSCustomerBGAction(Env.getCtx(), action_ID, trxName);
				else
					bgAction = new MUNSCustomerBGAction(bg);
				
				bgAction.setDateDoc(dateAction);
				bgAction.setC_BankAccount_ID(C_BankAccount_ID);
				bgAction.setAction(action);
				bgAction.processIt(DocAction.ACTION_Complete);
				bgAction.setSalesRep_ID(assigned_ID);
				bgAction.saveEx();
			}	
		}
		return true;
	}
}
