/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPayment;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.unicore.model.MUNSBillingTrfDetail;
import com.unicore.model.MUNSBillingTrfValidation;
import com.unicore.model.MUNSPaymentReceipt;

/**
 * @author ALBURHANY
 *
 */
public class UNSGeneratePReceipt extends SvrProcess {

	private String m_message = null;
	private MUNSBillingTrfValidation tfv = null;
	private Properties m_ctx = null;
	private String m_trxName = null;
	/**
	 * 
	 */
	public UNSGeneratePReceipt() {
		// TODO Auto-generated constructor stub
	}
	
	public UNSGeneratePReceipt(Properties ctx, MUNSBillingTrfValidation paramTFV, String trxName)
	{
		m_ctx = ctx;
		m_trxName = trxName;
		tfv = paramTFV;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{ 
		return createPayment() + createPaymentReceipt() + " Record created";
	}

	public String delExistingRecord()
	{
		m_message = null;
		
		String sql = "DELETE FROM UNS_BillingTrf_Invoice WHERE UNS_BillingTrf_Detail_ID IN "
				+ " (SELECT UNS_BillingTrf_Detail_ID FROM UNS_BillingTrf_Detail WHERE "
				+ " UNS_BillingTrfValidation_ID = " + getRecord_ID() + ")";
		int count = DB.executeUpdate(sql, m_trxName);
		if(count < 0)
			m_message = "Error when trying delete billing trasnfer validation invoice";
	
		sql = "DELETE FROM UNS_BillingTrf_Detail WHERE UNS_BillingTrfValidation_ID = " + getRecord_ID();
		count = DB.executeUpdate(sql, m_trxName);
		
		if(count < 0)
			m_message = "Error when trying delete billing trasnfer validation line";
		
		return m_message;
	}
	
	public int createPaymentReceipt()
	{
//		MUNSBillingTrfValidation tfv = new MUNSBillingTrfValidation(m_ctx, getRecord_ID(), m_trxName);
		
		String sql = "SELECT pr.UNS_PaymentReceipt_ID, pr.ReceiptAmt, pr.AD_Org_ID FROM UNS_PaymentReceipt pr"
				+ " WHERE pr.DocStatus IN ('CO', 'CL') AND pr.C_BankAccount_ID = ? "
				+ " AND EXISTS (SELECT 1 FROM UNS_PReceipt_Group prg WHERE prg.UNS_PReceipt_Group_ID"
				+ " = pr.UNS_PReceipt_Group_ID AND prg.DateReceived BETWEEN ? AND ?)";

		ResultSet rs=null;
		PreparedStatement pstmt= null;
		int count = 0;
		
		try {
			pstmt = DB.prepareStatement(sql,m_trxName);
			pstmt.setInt(1, tfv.getAccountFrom_ID());
			pstmt.setTimestamp(2, tfv.getDateFrom());
			pstmt.setTimestamp(3, tfv.getDateTo());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BigDecimal TotalTrf = MUNSBillingTrfDetail.getTotalTrf(MUNSPaymentReceipt.Table_Name, rs.getInt(1), m_trxName);
				tfv.setAmount(tfv.getAmount().add(rs.getBigDecimal(2)));
				tfv.saveEx();
				if(TotalTrf.compareTo(rs.getBigDecimal(2)) >= 0)
					continue;
				MUNSBillingTrfDetail td = new MUNSBillingTrfDetail(m_ctx, 0, m_trxName);
				td.setUNS_BillingTrfValidation_ID(tfv.get_ID());
				td.setUNS_PaymentReceipt_ID(rs.getInt(1));
				td.saveEx();
				count ++;
			}
		} catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
		}
		
		return count;
	}
	
	public int createPayment()
	{
//		MUNSBillingTrfValidation tfv = new MUNSBillingTrfValidation(m_ctx, getRecord_ID(), m_trxName);
		String sql = "SELECT p.C_Payment_ID, p.PayAmt FROM C_Payment p"
				+ " WHERE p.DocStatus IN ('CO', 'CL') AND p.isReceipt = 'Y' AND p.UNS_PR_Allocation_ID IS NULL"
				+ " AND p.C_BankAccount_ID=? AND p.DateTrx BETWEEN ? AND ?";
		
		ResultSet rs=null;
		PreparedStatement pstmt= null;
		int count = 0;
		
		try {
			pstmt = DB.prepareStatement(sql,m_trxName);
			pstmt.setInt(1, tfv.getAccountFrom_ID());
			pstmt.setTimestamp(2, tfv.getDateFrom());
			pstmt.setTimestamp(3, tfv.getDateTo());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BigDecimal TotalTrf = MUNSBillingTrfDetail.getTotalTrf(MPayment.Table_Name, rs.getInt(1), m_trxName);
				tfv.setAmount(tfv.getAmount().add(rs.getBigDecimal(2)));
				tfv.saveEx();
				if(TotalTrf.compareTo(rs.getBigDecimal(2)) >= 0)
					continue;
				MUNSBillingTrfDetail td = new MUNSBillingTrfDetail(m_ctx, 0, m_trxName);
				td.setUNS_BillingTrfValidation_ID(tfv.get_ID());
				td.setC_Payment_ID(rs.getInt(1));
				td.saveEx();
				count ++;
			}
		} catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
		}
		
		return count;
	}
}