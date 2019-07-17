/**
 * 
 */
package com.unicore.model.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.model.MBPartner;
import org.compiere.model.MPeriod;
import org.compiere.model.MUser;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import com.unicore.model.MUNSIncentive;

/**
 * @author Burhani Adam
 *
 */
public class CalculateIncentiveSales extends SvrProcess {

	/**
	 * 
	 */
	public CalculateIncentiveSales() {
		// TODO Auto-generated constructor stub
	}
	
	private int AD_User_ID = 0;
	private int C_Period_ID = 0;
	private String IncentiveType = null;
	private IProcessUI m_ui = null;
	private boolean m_DeleteOld = false;
	private int C_BPartner_ID = 0;
	private boolean isCustomer = false;
	private String SchemaType = null;
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
			if (name.equals("SalesRep_ID"))
				AD_User_ID = para[i].getParameterAsInt();
			else if (name.equals("C_Period_ID"))
				C_Period_ID = para[i].getParameterAsInt();
			else if (name.equals("IncentiveType"))
				IncentiveType = para[i].getParameterAsString();
			else if (name.equals("DeleteOld"))
				m_DeleteOld  = para[i].getParameterAsBoolean();
			else if (name.equals("C_BPartner_ID"))
				C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("IsCustomer"))
				isCustomer = para[i].getParameterAsBoolean();
			else if (name.equals("SchemaType"))
				SchemaType = para[i].getParameterAsString();
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
		MPeriod period = MPeriod.get(getCtx(), C_Period_ID);
		if(!isCustomer)
		{
			if(AD_User_ID > 0)
			{
				MUser user = MUser.get(getCtx(), AD_User_ID);
				if(m_DeleteOld)
				{
					String sql = "DELETE FROM UNS_AchievedIncentive_Line WHERE UNS_AcvIncentiveByPeriod_ID IN"
							+ " (SELECT UNS_AcvIncentiveByPeriod_ID FROM UNS_AcvIncentiveByPeriod"
							+ " WHERE C_Period_ID = ? AND UNS_AchievedIncentive_ID IN (SELECT UNS_AchievedIncentive_ID"
							+ " FROM UNS_AchievedIncentive WHERE C_BPartner_ID = ?))";
					boolean failed = DB.executeUpdate(sql, new Object[]{C_Period_ID, user.getC_BPartner_ID()}, 
							false, get_TrxName()) < 0;
					if(failed)
						throw new AdempiereException("Failed to delete existing record");
				}
				m_ui.statusUpdate("Kalkulasi sales ke 1 (" + user.getName() + ") dari 1 sales.");
				ArrayList<MUNSIncentive> incentives = MUNSIncentive.getBySalesRep(getCtx(), 
						(MBPartner) user.getC_BPartner(), period, SchemaType, IncentiveType, true, get_TrxName());
				for(int i = 0; i < incentives.size(); i++)
				{
					
					m_ui.statusUpdate("Kalkulasi sales ke 1 (" + user.getName() + ") dari 1 sales."
							+ " Skema incentive ke " + (i + 1) + " dari " + incentives.size() + " skema incentive");
					incentives.get(i).calc(incentives.get(i).getUNS_IncentiveSchema().getSchemaType(), period, user);
				}
			}
			else
			{
				List<MUser> users = MUser.getSalesRep(getCtx(), get_TrxName());
				Trx trx = Trx.get(get_TrxName(), true);
				for(int i = 0; i < users.size(); i++)
				{
					String trxName = trx.getTrxName();
					if(m_DeleteOld)
					{
						String sql = "DELETE FROM UNS_AchievedIncentive_Line WHERE UNS_AcvIncentiveByPeriod_ID IN"
								+ " (SELECT UNS_AcvIncentiveByPeriod_ID FROM UNS_AcvIncentiveByPeriod"
								+ " WHERE C_Period_ID = ? AND UNS_AchievedIncentive_ID IN (SELECT UNS_AchievedIncentive_ID"
								+ " FROM UNS_AchievedIncentive WHERE C_BPartner_ID = ?))";
						boolean failed = DB.executeUpdate(sql, new Object[]{C_Period_ID, users.get(i).getC_BPartner_ID()}, 
								false, trxName) < 0;
						if(failed)
							throw new AdempiereException("Failed to delete existing record");
					}
					m_ui.statusUpdate("Kalkulasi sales ke " + (i + 1) + " (" + users.get(i).getName() + ") "
							+ " dari " + users.size() + " sales.");
					ArrayList<MUNSIncentive> incentives = MUNSIncentive.getBySalesRep(getCtx(), 
							(MBPartner) users.get(i).getC_BPartner(), period, SchemaType, IncentiveType, true, trxName);
					for(int j = 0; j < incentives.size(); j++)
					{
						m_ui.statusUpdate("Kalkulasi sales ke " + (i + 1) + " (" + users.get(i).getName() + ") "
								+ " dari " + users.size() + " sales."
								+ " Skema incentive ke " + (j + 1) + " dari " + incentives.size() + " skema incentive");
						incentives.get(j).calc(incentives.get(j).getUNS_IncentiveSchema().getSchemaType(), period, users.get(i));
					}
					trx.commit(true);
					System.gc();
				}
			}
		}
		else
		{
			if(C_BPartner_ID > 0)
			{
				MBPartner bp = MBPartner.get(getCtx(), C_BPartner_ID);
				ArrayList<MUNSIncentive> incentives = MUNSIncentive.getBySalesRep(getCtx(), 
						bp, period, null, null, false, get_TrxName());
				for(int j = 0; j < incentives.size(); j++)
				{					
					m_ui.statusUpdate("Kalkulasi customer ke " + 1+ " (" + bp.getName() + ") "
							+ " dari " + 1 + " Customer."
							+ " Skema incentive ke " + (j + 1) + " dari " + incentives.size() + " skema incentive");
					
					incentives.get(j).calcCustomerTarget(
							incentives.get(j).getUNS_IncentiveSchema().getSchemaType(), 
								C_BPartner_ID, period);
				}
			}
			else
			{
				MBPartner[] customers = getSpecifiedCustomer(period);
				ArrayList<MUNSIncentive> incentives = MUNSIncentive.getBySalesRep(getCtx(), 
						null, period, null, null, false, get_TrxName());
				for(int i = 0; i < customers.length; i++)
				{
					for(int j = 0; j < incentives.size(); j++)
					{
						m_ui.statusUpdate("Kalkulasi customer ke " + (i + 1) + " (" + customers[i].getName() + ") "
								+ " dari " + customers.length + " Customer."
								+ " Skema incentive ke " + (j + 1) + " dari " + incentives.size() + " skema incentive");
						
						incentives.get(j).calcCustomerTarget(
								incentives.get(j).getUNS_IncentiveSchema().getSchemaType(), 
									customers[i].get_ID(), period);
					}
				}
			}
		}
		return "Success";
	}
	
	private MBPartner[] getSpecifiedCustomer(MPeriod period)
	{
		List<MBPartner> customers = new ArrayList<>();
		String sql = "SELECT bp.* FROM C_BPartner bp WHERE (EXISTS"
				+ " (SELECT 1 FROM C_Invoice iv WHERE iv.C_BPartner_ID = bp.C_BPartner_ID"
				+ " AND iv.DocStatus IN ('CO', 'CL') AND iv.IsSOTrx = 'Y' AND iv.DateInvoiced BETWEEN ? AND ?)"
				+ " OR EXISTS (SELECT 1 FROM C_Order o WHERE o.C_BPartner_ID = bp.C_BPartner_ID"
				+ " AND o.DocStatus IN ('CO', 'CL') AND o.IsSOTrx = 'Y' AND o.DateOrdered BETWEEN ? AND ?))"
				+ " AND bp.IsCustomer = 'Y'";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setTimestamp(1, period.getStartDate());
			stmt.setTimestamp(2, period.getEndDate());
			stmt.setTimestamp(3, period.getStartDate());
			stmt.setTimestamp(4, period.getEndDate());
			rs = stmt.executeQuery();
			while(rs.next())
			{
				customers.add(new MBPartner(getCtx(), rs, get_TrxName()));
			}
		} catch (SQLException e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return customers.toArray(new MBPartner[customers.size()]);
	}
}
