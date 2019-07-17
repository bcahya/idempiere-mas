/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.DocManager;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBankStatement;
import org.compiere.model.MPayment;
import org.compiere.model.X_C_BankStatement;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.unicore.base.model.MBankStatementLine;

/**
 * @author Burhani Adam
 *
 */
public class PaymentChangeAccount extends SvrProcess {

	/**
	 * 
	 */
	public PaymentChangeAccount() {
		// TODO Auto-generated constructor stub
	}
	
	private int payment_id = 0;
	private int bankAccount_ID = 0;
	private String whereClause = null;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if(name.equals("C_Payment_ID"))
				payment_id = para[i].getParameterAsInt();
			else if(name.equals("C_BankAccount_ID"))
				bankAccount_ID = para[i].getParameterAsInt();
			else if(name.equals("WhereClause"))
				whereClause = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if(bankAccount_ID <= 0 || (payment_id <= 0 && Util.isEmpty(whereClause)))
		{
			throw new AdempiereException("please correct parameter");
		}
		String sql = "SELECT C_Payment_ID, C_BankAccount_ID FROM C_Payment";
		boolean needAnd = false;
		if(!Util.isEmpty(whereClause))
		{
			sql += " WHERE " + whereClause;
			needAnd = true;
		}
		if(payment_id > 0)
		{
			sql += needAnd ? " AND " : " WHERE " + "C_Payment_ID = " + payment_id;
		}
		
		PreparedStatement stm = null;
		ResultSet rs = null;
		ArrayList<Integer> listStatement = new ArrayList<Integer>();
		listStatement.add(1);
		ArrayList<Integer> listAccount = new ArrayList<Integer>();
		listAccount.add(1);
	
		try
		{
			stm = DB.prepareStatement(sql, get_TrxName());
			rs = stm.executeQuery();
			while (rs.next())
			{
				payment_id = rs.getInt(1);
				if(bankAccount_ID == rs.getInt(2))
					continue;
				
				String upPayment = "UPDATE C_Payment SET C_BankAccount_ID = ?, AD_Org_ID = (SELECT AD_Org_ID FROM C_BankAccount WHERE C_BankAccount_ID = ?)"
						+ " WHERE C_Payment_ID = ?";
				if(DB.executeUpdate(upPayment, new Object[]{bankAccount_ID, bankAccount_ID, payment_id}, false, get_TrxName()) < 0)
					throw new AdempiereException("Failed when trying update payment");
				
				DocManager.postDocument(MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID(), 
						get_TrxName()), MPayment.Table_ID, payment_id, true, true, get_TrxName());
				
				MBankStatementLine line = MBankStatementLine.getOfPayment(getCtx(), payment_id, get_TrxName());
				
				if(line == null)
					continue;
				
				MBankStatement oldBST = new MBankStatement(getCtx(), line.getC_BankStatement_ID(), get_TrxName());
				
				MBankStatement newBST = MBankStatement.getOpen(bankAccount_ID, get_TrxName(), true);
				line.setC_BankStatement_ID(newBST.get_ID());
				line.setProcessed(false);
				line.saveEx();
				
				for(int i = 0; i < listStatement.size(); i++)
				{
					if(listStatement.get(i) == oldBST.get_ID())
						break;
					if(listStatement.size() == (i + 1))
						listStatement.add(oldBST.get_ID());
				}
				
				for(int i = 0; i < listStatement.size(); i++)
				{
					if(listStatement.get(i) == newBST.get_ID())
						break;
					if(listStatement.size() == (i + 1))
						listStatement.add(newBST.get_ID());
				}
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage() + CLogger.retrieveErrorString("Error"));
		}
		
		if(listStatement.size() == 0)
			return "Success";
		
		for(int i = 0; i < listStatement.size(); i++)
		{
			if(listStatement.get(i) == 1)
				continue;
			
			MBankStatement bst = new MBankStatement(getCtx(), listStatement.get(i), get_TrxName());
			
			bst.set_ValueNoCheck(X_C_BankStatement.COLUMNNAME_StatementDifference, getTotalStmtAmt(listStatement.size(), 0));
			bst.set_ValueNoCheck(X_C_BankStatement.COLUMNNAME_EndingBalance, bst.getBeginningBalance().add(bst.getStatementDifference()));
			if(!bst.save())
				throw new AdempiereException("failed update Statement");
			
			for(int j = 0; j < listAccount.size(); j++)
			{
				if(listAccount.get(j) == bst.getC_BankAccount_ID())
					break;
				if(listAccount.size() == (j + 1))
					listAccount.add(bst.getC_BankAccount_ID());
			}
			
			DocManager.postDocument(MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID(), 
					get_TrxName()), MBankStatement.Table_ID, listStatement.get(i), true, true, get_TrxName());
		}
		
		for(int i = 0; i < listAccount.size(); i++)
		{
			if(listAccount.get(i) == 1)
				continue;
			DB.executeUpdate("UPDATE C_BankAccount SET CurrentBalance = ? WHERE C_BankAccount_ID = ?",
					new Object[]{getTotalStmtAmt(0, listAccount.get(i)), listAccount.get(i)}, false, get_TrxName());
		}
		
		return "Success";
	}
	
	private BigDecimal getTotalStmtAmt(int C_BankStatement_ID, int C_BankAccount_ID)
	{
		BigDecimal amount = Env.ZERO;
		String sql = "SELECT COALESCE(SUM(StmtAmt),0) FROM C_BankStatementLine";
		if(C_BankStatement_ID > 0)
		{
			sql += " WHERE C_BankStatement_ID = ?";
		}
		else if(C_BankAccount_ID > 0)
		{
			sql += " WHERE C_BankStatement_ID IN (SELECT bst.C_BankStatement_ID FROM C_BankStatement bst"
					+ " WHERE bst.C_BankAccount_ID = ? AND bst.DocStatus IN ('CO', 'CL'))";
		}
		else
			return amount;
		
		amount = DB.getSQLValueBD(get_TrxName(), sql, C_BankStatement_ID > 0 ? C_BankStatement_ID : C_BankAccount_ID);
		
		return amount;
	}
}
