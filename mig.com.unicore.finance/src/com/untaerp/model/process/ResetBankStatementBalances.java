/**
 * 
 */
package com.untaerp.model.process;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBankAccount;
import org.compiere.model.MBankStatement;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.util.MessageBox;

/**
 * @author AzHaidar, BurhaniAdam
 * @see www.untasoft.com
 */
public class ResetBankStatementBalances extends SvrProcess 
{
	private int				m_bankStatement_ID;
	private int				m_bankAccount_ID;
	private boolean			m_fromStatement;
	
	/**
	 * 
	 */
	public ResetBankStatementBalances() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		if(getRecord_ID() > 0)
		{
			m_bankStatement_ID = getRecord_ID();
			m_fromStatement = true;
		}
		else
		{
			ProcessInfoParameter[] params = getParameter();
			for(ProcessInfoParameter param : params)
			{
				if(param.getParameterName() == null)
					;
				else if(param.getParameterName().equals("C_BankAccount_ID"))
					m_bankAccount_ID = param.getParameterAsInt();
			}
			m_fromStatement = false;
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		int count = 0;
		if(m_fromStatement)
		{
			balanceIt(m_bankStatement_ID);
			count++;
		}
		else
		{
			String sql = "SELECT C_BankStatement_ID FROM C_BankStatement WHERE C_BankAccount_ID = ?"
					+ " AND DocStatus IN ('CO', 'CL') ORDER BY StatementDate ASC, Created ASC";
			if(m_bankAccount_ID > 0)
			{
				int firsStatement = DB.getSQLValue(get_TrxName(), sql, m_bankAccount_ID);
				balanceIt(firsStatement);
				count++;
			}
			else
			{
				int retVal = MessageBox.showMsg(getCtx(), getProcessInfo(), 
						"Action with null parameter will be reset balance for all account."
						+ "\nDo you want to continue?", 
						"Parameter null confirmation.", MessageBox.YESNO, MessageBox.ICONQUESTION);
				if(retVal == MessageBox.RETURN_NO || MessageBox.RETURN_CANCEL == retVal)
					return "Transaction has cancelled.";
				List<MBankAccount> accounts = new Query(getCtx(), MBankAccount.Table_Name, "", get_TrxName()).list();
				for(MBankAccount account : accounts)
				{
					int firsStatement = DB.getSQLValue(get_TrxName(), sql, account.get_ID());
					balanceIt(firsStatement);
					count++;
				}
			}
		}
		
		return count + " Bank/Cash balances reset successfully.";
	} // doIt
	
	private void balanceIt(int C_BankStatement_ID)
	{
		if(C_BankStatement_ID <= 0)
			return;
		MBankStatement stmt = new MBankStatement(getCtx(), C_BankStatement_ID, get_TrxName());
		String sql = "SELECT MAX(StatementLineDate) FROM C_BankStatementLine WHERE C_BankStatement_ID=?";
		
		//Timestamp maxStmtLineDate = DB.getSQLValueTS(get_TrxName(), sql, stmt.get_ID());
		
		sql = "SELECT COALESCE (SUM(bsl.StmtAmt), 0) FROM C_BankStatementLine bsl "
				+ "INNER JOIN C_BankStatement bs ON bs.C_BankStatement_ID=bsl.C_BankStatement_ID "
				+ "WHERE bs.DocStatus NOT IN ('VO', 'RE') "
				+ "		AND bs.C_BankAccount_ID=? " //AND bsl.StatementLineDate < ? "
				+ "		AND bs.C_BankStatement_ID != ? AND bs.StatementDate < ?"
				+ " AND bs.Created < ?";
		
		BigDecimal beginningBalance = 
				DB.getSQLValueBD(get_TrxName(), sql, 
						stmt.getC_BankAccount_ID(), //maxStmtLineDate, 
						stmt.get_ID(), stmt.getStatementDate(),
						stmt.getCreated());
		
		if (beginningBalance == null)
			beginningBalance = Env.ZERO;
		
		stmt.setBeginningBalance(beginningBalance);
		
		sql = "SELECT COALESCE (SUM(StmtAmt), 0) FROM C_BankStatementLine WHERE C_BankStatement_ID=?";
		BigDecimal stmtDiff = DB.getSQLValueBD(get_TrxName(), sql, stmt.get_ID());
		
		stmt.setStatementDifference(stmtDiff);
		BigDecimal endingBalance = beginningBalance.add(stmtDiff);
		stmt.setEndingBalance(endingBalance);
		if(!stmt.save())
			throw new AdempiereException("Error while saving current statement balances.");
		
		sql = "C_BankAccount_ID=? AND StatementDate>=? AND Created > ? "
				+ "AND DocStatus IN ('CO', 'CL') AND C_BankStatement_ID<>?";
		
		int[] stmtIDs = new Query(getCtx(), MBankStatement.Table_Name, sql, get_TrxName())
						.setParameters(stmt.getC_BankAccount_ID(), stmt.getStatementDate(), stmt.getCreated(), stmt.get_ID())
						.setOrderBy("StatementDate, Created ASC")
						.getIDs();
		
		BigDecimal currentBABalance = endingBalance;
		
		for (int i=0; i < stmtIDs.length; i++)
		{
			int nextStmtID = stmtIDs[i];
			
			sql = "SELECT COALESCE(SUM(bsl.StmtAmt), 0) FROM C_BankStatementLine bsl "
					+ "WHERE bsl.C_BankStatement_ID=" + nextStmtID;
			
			beginningBalance = endingBalance;
			stmtDiff = DB.getSQLValueBD(get_TrxName(), sql);
			endingBalance = beginningBalance.add(stmtDiff);
			
			sql = "UPDATE C_BankStatement SET BeginningBalance=?, StatementDifference=?, EndingBalance=? "
					+ "WHERE C_BankStatement_ID=" + nextStmtID;
			
			int count = DB.executeUpdate(sql, new Object[]{beginningBalance, stmtDiff, endingBalance}, 
										false, get_TrxName());
			
			if (count <= 0) {
				sql = "SELECT DocumentNo FROM C_BankStatement WHERE C_BankStatement_ID=" + nextStmtID;
				String documentNo = DB.getSQLValueString(get_TrxName(), sql);
				throw new AdempiereException(
						"Error while updating balances for statement document no [" + documentNo + "]");
			}
			
			//sql = "SELECT DocStatus FROM C_BankStatement WHERE C_BankStatement_ID=" + nextStmtID;
			//String currStatus = DB.getSQLValueString(get_TrxName(), sql);
			
			//if (currStatus.equals(MBankStatement.DOCSTATUS_Completed) 
			//		|| currStatus.equals(MBankStatement.DOCSTATUS_Closed))
			//{
				currentBABalance = endingBalance;
			//}			
		}
		sql = "UPDATE C_BankAccount SET CurrentBalance=? WHERE C_BankAccount_ID=" + stmt.getC_BankAccount_ID();
		
		int count = DB.executeUpdate(sql, new Object[]{currentBABalance}, false, get_TrxName());
		
		if (count <= 0)
		{
			sql = "SELECT Name FROM C_BankAccount WHERE C_BankAccount_ID=" + stmt.getC_BankAccount_ID();
			String BAName = DB.getSQLValueString(get_TrxName(), sql);
			throw new AdempiereException(
					"Error while updating current balance for Bank/Cash account of [" + BAName + "]");
		}
	}
}