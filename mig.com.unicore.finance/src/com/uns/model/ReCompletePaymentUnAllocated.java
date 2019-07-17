/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.adempiere.util.IProcessUI;
import org.compiere.model.MInvoice;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.base.model.MPayment;

/**
 * @author Burhani Adam
 *
 */
public class ReCompletePaymentUnAllocated extends SvrProcess
{
	/**
	 * 
	 */
	public ReCompletePaymentUnAllocated() {
		// TODO Auto-generated constructor stub
	}
	
	private IProcessUI m_ui = null;
	private String status = null;
	private boolean needAction = true;

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String doIt() throws Exception
	{
		m_ui = Env.getProcessUI(getCtx());
		
		String sql = "SELECT COUNT(*) FROM C_Payment cp WHERE"
				+ " EXISTS (SELECT 1 FROM C_PaymentAllocate pa WHERE pa.C_Payment_ID = cp.C_Payment_ID"
				+ " AND pa.C_AllocationLine_ID IS NULL) AND cp.Description LIKE '%**Auto ReOpen and ReComplete By System**%'";
		int count = DB.getSQLValue(get_TrxName(), sql);

		sql = "SELECT C_Payment_ID FROM C_Payment cp WHERE"
				+ " EXISTS (SELECT 1 FROM C_PaymentAllocate pa WHERE pa.C_Payment_ID = cp.C_Payment_ID"
				+ " AND pa.C_AllocationLine_ID IS NULL) AND cp.Description LIKE '%**Auto ReOpen and ReComplete By System**%'";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int on = 0;
		try {
			stmt = DB.prepareStatement(sql, get_TrxName());
			rs = stmt.executeQuery();
			while (rs.next())
			{
				status = "Total Data --> " + count + ", ini data ke-" + on;
				m_ui.statusUpdate(status);
				needAction = true;
				org.compiere.model.MPayment pay = new MPayment(getCtx(), rs.getInt(1), get_TrxName());
				org.compiere.model.MPaymentAllocate[] all = org.compiere.model.MPaymentAllocate.get(pay);
				for(org.compiere.model.MPaymentAllocate allocate : all)
				{
					MInvoice inv = MInvoice.get(getCtx(), allocate.getC_Invoice_ID());
					on += 1;
					
					BigDecimal payAmt = allocate.getAmount().add(allocate.getWriteOffAmt()).add(allocate.getDiscountAmt());
					BigDecimal openAmt = DB.getSQLValueBD(get_TrxName(), "SELECT InvoiceOpen(?,0)", inv.get_ID());
					if(payAmt.compareTo(openAmt) != 0)
					{
						needAction = false;
						String closePayment = "UPDATE C_Payment SET DocStatus = 'CO', DocAction = 'CL', Processed = 'Y'"
								+ " WHERE C_Payment_ID=?";
						DB.executeUpdate(closePayment, pay.get_ID(), get_TrxName());
						continue;
					}
				}
				if(!needAction)
					continue;
//				String openPayment = "UPDATE C_Payment SET DocStatus = 'DR', DocAction = 'CO', Processed = 'N'"
//						+ " WHERE C_Payment_ID=?";
//				DB.executeUpdate(openPayment, pay.get_ID(), get_TrxName());
//				String desc = "**Auto ReOpen and ReComplete By System**";
//				pay.setDescription(pay.getDescription() == null ? desc : pay.getDescription() + " || " + desc);
				boolean ok = pay.processIt("CO");
				if(!ok)
				{
					log.warning(pay.getDocumentNo());
					throw new AdempiereSystemError(pay.getProcessMsg());
				}
				pay.saveEx(get_TrxName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
