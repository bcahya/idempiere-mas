/**
 * 
 */
package com.unicore.base.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MBankStatement;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.model.MUNSTransferBalanceRequest;

/**
 * @author menjangan
 *
 */
public class MBankStatementLine extends org.compiere.model.MBankStatementLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//TAMBAHAN
	public boolean m_IsOnImport = false;

	/**
	 * @param ctx
	 * @param C_BankStatementLine_ID
	 * @param trxName
	 */
	public MBankStatementLine(Properties ctx, int C_BankStatementLine_ID,
			String trxName) {
		super(ctx, C_BankStatementLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MBankStatementLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param statement
	 */
	public MBankStatementLine(MBankStatement statement) {
		super(statement);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param statement
	 * @param lineNo
	 */
	public MBankStatementLine(MBankStatement statement, int lineNo) {
		super(statement, lineNo);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param C_BankAccount_ID
	 * @param trxName
	 * @return
	 */
	public static MBankStatementLine[] getLastOf(int C_BankAccount_ID, Timestamp DateRequired, String trxName)
	{
		List<MBankStatementLine> list = new ArrayList<MBankStatementLine>();
		
		String sql = "SELECT bsl.* FROM C_BankStatementLine bsl " +
				"INNER JOIN C_BankStatement bs " +
				"ON bs.C_BankStatement_ID = bsl.C_BankStatement_ID " +
				"WHERE bs.C_BankAccount_ID =? " +
				"AND bsl.UNS_TransferBalance_Request_ID IS NULL " +
				"AND bsl.StatementLineDate <= ?" +
//				"AND bs.StatementDate IN " +
//				"(SELECT MAX(bs2.StatementDate) " +
//				"FROM C_BankStatement bs2 " +
//				"WHERE bs2.C_BankAccount_ID = bs.C_BankAccount_ID " +
				" AND bs.DocStatus IN ('CO', 'CL')" +
				" AND bsl.TransactionType = 'APT'";
		
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		try
		{
			stm = DB.prepareStatement(sql, trxName);
			stm.setInt(1, C_BankAccount_ID);
			stm.setTimestamp(2, DateRequired);
			rs = stm.executeQuery();
			while (rs.next())
			{
				MBankStatementLine sLine = new MBankStatementLine(Env.getCtx(), rs, trxName);
				list.add(sLine);
			}
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			DB.close(rs, stm);
		}
		
		MBankStatementLine[] sLines = new MBankStatementLine[list.size()];
		sLines = list.toArray(sLines);
		
		return sLines;
	}
	
	/**
	 * 
	 * @param UNS_TransferBalanceRequest_ID
	 * @param trxName
	 * @return
	 */
	public static MBankStatementLine[] getOf(
			int UNS_TransferBalanceRequest_ID,
			int UNS_TransferBalace_Confirm_ID, 
			String trxName)
	{
		List<MBankStatementLine> list = new ArrayList<MBankStatementLine>();
		
		StringBuilder sb = new StringBuilder("SELECT * FROM C_BankStatementLine");
		
		if(UNS_TransferBalanceRequest_ID > 0
				|| UNS_TransferBalace_Confirm_ID > 0)
		{
			sb.append(" WHERE ");
		}
		
		if(UNS_TransferBalanceRequest_ID > 0)
		{
			sb.append(" UNS_TransferBalance_Request_ID=? ");
		}
		
		if(UNS_TransferBalace_Confirm_ID > 0)
		{
			if(UNS_TransferBalanceRequest_ID > 0)
				sb.append(" AND ");
			sb.append("UNS_TransferBalance_Confirm_ID=?");
		}
		
		String sql = sb.toString();
		
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		try{
			int idx = 0;
			stm = DB.prepareStatement(sql, trxName);
			
			if(UNS_TransferBalanceRequest_ID > 0)
			{
				stm.setInt(++idx, UNS_TransferBalanceRequest_ID);
			}
			if(UNS_TransferBalace_Confirm_ID > 0)
			{
				stm.setInt(idx++, UNS_TransferBalace_Confirm_ID);
			}
			
			rs = stm.executeQuery();
			while (rs.next())
			{
				MBankStatementLine stmL = new MBankStatementLine(
						Env.getCtx(), rs, trxName);
				list.add(stmL);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DB.close(rs, stm);
		}
		
		MBankStatementLine[] sLines = new MBankStatementLine[list.size()];
		sLines = list.toArray(sLines);
		
		return sLines;
	}
	
	public boolean beforeSave(boolean newRecord)
	{
		if(getUNS_TransferBalance_Request_ID() > 0)
		{
			MUNSTransferBalanceRequest tf = new MUNSTransferBalanceRequest(getCtx(), getUNS_TransferBalance_Request_ID(), get_TrxName());
			tf.setTrxAmt(tf.getTrxAmt().add(getStmtAmt().negate()));
			tf.saveEx();
		}
		
		return true;
	}
	
	public static MBankStatementLine getOfPayment(Properties ctx, int C_Payment_ID, String trxName)
	{
		MBankStatementLine line = null;
		
		String sql = "SELECT C_BankStatementLine_ID FROM C_BankStatementLine WHERE C_Payment_ID = ?";
		int id = DB.getSQLValue(trxName, sql, C_Payment_ID);
		
		if(id<=0)
			return null;
		
		line = new MBankStatementLine(ctx, id, trxName);
		
		return line;
	}
}
