/**
 * 
 */
package com.unicore.model.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.acct.DocManager;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MBankStatement;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceTax;
import org.compiere.model.MPayment;
import org.compiere.model.MUser;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.util.MessageBox;

import com.unicore.model.MUNSExpedition;

/**
 * @author Burhani Adam
 *
 */
public class UpdatePartnerOrgExp extends SvrProcess {

	/**
	 * 
	 */
	public UpdatePartnerOrgExp() {
		// TODO Auto-generated constructor stub
	}
	
	private int Partner_ID = 0;
	private int PartnerLocation_ID = 0;
	private int Org_ID = 0;
	private IProcessUI m_ui = null;
	private String queryUpdate = null;
	

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("AD_Org_ID"))
				Org_ID = para[i].getParameterAsInt();
			else if (name.equals("C_BPartner_ID"))
				Partner_ID = para[i].getParameterAsInt();
			else if (name.equals("C_BPartner_Location_ID"))
				PartnerLocation_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		m_ui = Env.getProcessUI(getCtx());
		String userName = MUser.getNameOfUser(Env.getAD_User_ID(getCtx()));
		String finalmessage = "sabar ya " + userName;
		
		m_ui.statusUpdate("**update expedition** " + finalmessage);
		MUNSExpedition exp = new MUNSExpedition(getCtx(), getRecord_ID(), get_TrxName());
		
		queryUpdate = "UPDATE UNS_Expedition SET AD_Org_ID = ?, C_BPartner_ID = ?, C_BPartner_Location_ID = ?"
				+ " WHERE UNS_Expedition_ID = ?";
		DB.executeUpdate(queryUpdate, new Object[]{Org_ID, Partner_ID, PartnerLocation_ID,
					exp.get_ID()}, true, get_TrxName());
		
		queryUpdate = "UPDATE UNS_ExpeditionDetail SET AD_Org_ID = ? WHERE UNS_Expedition_ID = ?";
		DB.executeUpdate(queryUpdate, new Object[]{Org_ID, exp.get_ID()}, true, get_TrxName());
		
		if(exp.getC_Invoice_ID() > 0)
		{
			m_ui.statusUpdate("**update invoice dan posting ulang** " + finalmessage);
			MInvoice inv = MInvoice.get(getCtx(), exp.getC_Invoice_ID());
			if(inv.isComplete())
			{
				queryUpdate = "UPDATE C_Invoice SET AD_Org_ID = ?, C_BPartner_ID = ?, C_BPartner_Location_ID = ?"
						+ " WHERE C_Invoice_ID = ?";
				DB.executeUpdate(queryUpdate, new Object[]{Org_ID, Partner_ID, PartnerLocation_ID, 
						inv.get_ID()}, true, get_TrxName());
				queryUpdate = "UPDATE C_InvoiceLine SET AD_Org_ID = ? WHERE C_Invoice_ID = ?";
				DB.executeUpdate(queryUpdate, new Object[]{Org_ID, inv.get_ID()}, true, get_TrxName());
				
				queryUpdate = "UPDATE C_InvoiceTax SET AD_Org_ID = ? WHERE C_Invoice_ID = ?";
				DB.executeUpdate(queryUpdate, new Object[]{Org_ID, inv.get_ID()}, true, get_TrxName());
				
				DocManager.postDocument(
						MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID(), 
								get_TrxName()), MInvoice.Table_ID, inv.get_ID(), 
						true, true, get_TrxName());
			}
			else
			{
				inv.setAD_Org_ID(Org_ID);
				inv.setC_BPartner_ID(Partner_ID);
				inv.setC_BPartner_Location_ID(PartnerLocation_ID);
				inv.saveEx();
				
				for(org.compiere.model.MInvoiceLine line : inv.getLines())
				{
					line.setAD_Org_ID(Org_ID);
					line.saveEx();
				}
				
				for(MInvoiceTax tax : inv.getTaxes(true))
				{
					tax.setAD_Org_ID(Org_ID);
					tax.saveEx();
				}
			}
			
			m_ui.statusUpdate("**update payment dan posting ulang** " + finalmessage);
			String sql = "SELECT p.C_Payment_ID FROM C_Payment p WHERE p.C_Invoice_ID = ? OR EXISTS"
					+ " (SELECT 1 FROM C_PaymentAllocate pa WHERE pa.C_Payment_ID = p.C_Payment_ID"
					+ " AND pa.C_Invoice_ID = ?)";
			ResultSet rs = null;
			PreparedStatement stmt = null;
			try {
				stmt = DB.prepareStatement(sql, get_TrxName());
				stmt.setInt(1, inv.get_ID());
				stmt.setInt(2, inv.get_ID());
				rs = stmt.executeQuery();
				while (rs.next())
				{
					MPayment pay = new MPayment(getCtx(), rs.getInt(1), get_TrxName());
					queryUpdate = "UPDATE C_Payment SET C_BPartner_ID = ? WHERE C_payment_ID = ?";
					DB.executeUpdate(queryUpdate, new Object[]{Partner_ID, pay.get_ID()}, true, get_TrxName());
					queryUpdate = "UPDATE C_PaymentAllocate SET AD_Org_ID = ? WHERE C_Invoice_ID = ?";
					DB.executeUpdate(queryUpdate, new Object[]{Org_ID, inv.get_ID()}, true, get_TrxName());
					
					if(pay.isComplete())
					{
						DocManager.postDocument(
								MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID(), 
										get_TrxName()), MPayment.Table_ID, pay.get_ID(), 
								true, true, get_TrxName());
						
						String querySQL = "SELECT C_BankStatement_ID FROM C_BankStatementLine"
								+ " WHERE C_Payment_ID = ?";
						int bst = DB.getSQLValue(get_TrxName(), querySQL, pay.get_ID());
						if(bst > 0)
						{
							queryUpdate = "UPDATE C_BankStatementLine SET C_BPartner_ID = ?"
									+ " WHERE C_Payment_ID = ?";
							DB.executeUpdate(queryUpdate, new Object[]{Partner_ID, pay.get_ID()}, true, get_TrxName());
							
							MBankStatement bs = new MBankStatement(getCtx(), bst, get_TrxName());
							if(bs.isComplete())
							{
								int retVal = MessageBox.showMsg(getCtx(), getProcessInfo(), "posting ulang statement akan memakan waktu "
										+ "\n lama kalau statement linenya banyak."
										+ "\n Yes untuk melanjutkan posting ulang statement."
										+ "\n No untuk tidak posting ulang statement.", 
										"posting ulang statement", MessageBox.YESNO, MessageBox.ICONQUESTION);
								if(retVal == MessageBox.RETURN_YES || retVal == MessageBox.RETURN_OK)
								{
									DocManager.postDocument(
											MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID(), 
													get_TrxName()), MBankStatement.Table_ID, bs.get_ID(), 
											true, true, get_TrxName());
								}
							}
						}
					}
				}
			} catch (Exception e) {
				throw new AdempiereException(e.getMessage());
			}
		}		
		return "sukses";
	}
}
