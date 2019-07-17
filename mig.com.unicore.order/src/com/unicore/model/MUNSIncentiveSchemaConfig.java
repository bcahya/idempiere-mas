/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPaymentAllocate;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.uns.util.MessageBox;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MOrderLine;

/**
 * @author Burhani Adam
 *
 */
public class MUNSIncentiveSchemaConfig extends X_UNS_IncentiveSchemaConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 923016885956870442L;

	/**
	 * @param ctx
	 * @param UNS_IncentiveSchemaConfig_ID
	 * @param trxName
	 */
	public MUNSIncentiveSchemaConfig(Properties ctx,
			int UNS_IncentiveSchemaConfig_ID, String trxName) {
		super(ctx, UNS_IncentiveSchemaConfig_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSIncentiveSchemaConfig(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean analyzePaymentAlloc(MPaymentAllocate all, MUNSIncentive incentive)
	{
		if(!incentive.getIncentiveType().equals(X_UNS_Incentive.INCENTIVETYPE_Billing)
				|| !incentive.isQualify(all.getAD_Org_ID(), all.getC_Payment().getC_BPartner_ID(), 0, 0
						, all.getC_Payment().getTenderType()))
			return false;
		
		if(all.getAmount().compareTo(Env.ZERO) == -1)
			return true;
		
		Timestamp dateTrx = all.getC_Payment().getDateTrx();
		Calendar cal = Calendar.getInstance();
		cal.setTime(all.getC_Invoice().getDateInvoiced());
		Timestamp dateInv = new Timestamp(cal.getTimeInMillis());
		
		int range = TimeUtil.getDaysBetween(dateInv, dateTrx);
		
		if(range >= getRangeFrom().intValue() && range <= getRangeTo().intValue())
			return true;
		else
			return false;
	}
	
	public static void main(String[] args)
	{
		Timestamp a = Timestamp.valueOf("2017-10-20 00:00:00");
		Timestamp b = Timestamp.valueOf("2017-10-10 00:00:00");
		int diff = TimeUtil.getDaysBetween(a, b);
		System.out.println(diff);
		
//		BigDecimal xx = BigDecimal.valueOf(1299).divide(Env.ONEHUNDRED, 0, RoundingMode.DOWN);
//		System.out.println(xx);
	}
	
	public boolean analyzeInvoiceLine(MInvoiceLine line, MUNSIncentive incentive)
	{
		if(!incentive.getIncentiveType().equals(X_UNS_Incentive.INCENTIVETYPE_SalesInvoice)
				|| !incentive.isQualify(line.getAD_Org_ID(), line.getC_Invoice().getC_BPartner_ID(),
						line.getC_Invoice().getC_BPartner_Location_ID(), line.getM_Product_ID(), null))
			return false;
		
		return true;
	}
	
	public boolean analyzeInvoice(MInvoice inv, MUNSIncentive incentive)
	{
		if(!incentive.getIncentiveType().equals(X_UNS_Incentive.INCENTIVETYPE_SalesInvoice) || 
				!incentive.isQualify(inv.getAD_Org_ID(), inv.getC_BPartner_ID(), inv.getC_BPartner_Location_ID(), 0, null))
			return false;
		
		BigDecimal openToDate = DB.getSQLValueBD(get_TrxName(), 
				"SELECT InvoiceOpen(?,0,?)", 
						inv.getC_Invoice_ID());
		if(openToDate == null)
			openToDate = Env.ZERO;
		if(openToDate.compareTo(Env.ZERO) == 0)
			return true;
		
		return true;
	}
	
	public boolean analyzeOrder(MOrderLine line, MUNSIncentive incentive)
	{
		if(!incentive.getIncentiveType().equals(X_UNS_Incentive.INCENTIVETYPE_SalesOrder)
				|| !incentive.isQualify(line.getAD_Org_ID(), line.getC_Order().getC_BPartner_ID(),
						line.getC_Order().getC_BPartner_Location_ID(), line.getM_Product_ID(), null))
			return false;
		
		return true;
	}
	
	public boolean analyzeNewOutlet(MInvoice invoice, MUNSIncentive incentive)
	{
		if(!incentive.getIncentiveType().equals(X_UNS_Incentive.INCENTIVETYPE_NewOutlet)
				|| !incentive.isQualify(invoice.getAD_Org_ID(), invoice.getC_BPartner_ID(),
						invoice.getC_BPartner_Location_ID(), 0, null))
			return false;
		
		return true;
	}
	
	public boolean analyzeDeductionInvoice(MInvoice invoice, MUNSIncentive incentive)
	{
		if(!incentive.getIncentiveType().equals(X_UNS_Incentive.INCENTIVETYPE_DeductionInvoice)
				|| !incentive.isQualify(invoice.getAD_Org_ID(), invoice.getC_BPartner_ID(),
						invoice.getC_BPartner_Location_ID(), 0, null))
			return false;
		
		return true;
	}
	
	public boolean analyzeShipping(MUNSShipping sh, MUNSIncentive incentive)
	{
		if(!incentive.getIncentiveType().equals(X_UNS_Incentive.INCENTIVETYPE_Delivery))
			return false;
		
		return true;
	}
	
	public static ArrayList<MUNSIncentiveSchemaConfig> getOfParent(MUNSIncentive incentive)
	{
		ArrayList<MUNSIncentiveSchemaConfig> configs = new ArrayList<>();
		
		String sql = "SELECT * FROM UNS_IncentiveSchemaConfig"
				+ " WHERE UNS_Incentive_ID = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			stmt = DB.prepareStatement(sql, incentive.get_TrxName());
			stmt.setInt(1, incentive.get_ID());
			rs = stmt.executeQuery();
			while (rs.next())
			{
				configs.add(new MUNSIncentiveSchemaConfig(incentive.getCtx(), rs, incentive.get_TrxName()));
			}
		}
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		}
		
		return configs;
	}
	
	public boolean beforeSave(boolean newRecord)
	{
		if(!isIncentiveAmount() && getIncentive().compareTo(Env.ONEHUNDRED) == 1)
		{
			int retVal = MessageBox.showMsg(this, getCtx(), "This scheme use percent in calculation incentive."
					+ "\n Are you sure to set Percent with value greater than 100?", "Confirmation percent value.", 
					MessageBox.YESNO, MessageBox.ICONQUESTION);
			if(retVal == MessageBox.RETURN_CANCEL || MessageBox.RETURN_NO == retVal)
			{
				log.saveError("Error", "Transaction has been canceled");
				return false;
			}
		}
		
		if(getRangeTo().signum() == 0 && getRangeFrom().signum() != 0)
		{
			log.saveError("Error", "Please set range to");
			return false;
		}
		
		if(getRangeTo().compareTo(getRangeFrom()) == -1)
		{
			log.saveError("Error", "Range from can't bigger than range to");
			return false;
		}
		
		if(getAgeFrom() > getAgeTo())
		{
			log.saveError("Error", "Age from can't bigger than age to");
			return false;
		}
		
		if(getAgeTo() == 0 && getAgeFrom() != 0)
		{
			log.saveError("Error", "Please set age to");
			return false;
		}
		
		if(getRangeFrom().signum() > 0)
		{
			String sql = "SELECT COUNT(*) FROM UNS_IncentiveSchemaConfig WHERE"
					+ " RangeFrom = 0 AND RangeTo = 0 AND UNS_IncentiveSchemaConfig_ID <> ?"
					+ " AND UNS_Incentive_ID = ?";
			boolean exists = DB.getSQLValue(get_TrxName(), sql, get_ID(), getUNS_Incentive_ID()) > 0;
			if(exists)
			{
				log.saveError("Error", "Has found another configuration with range from zero and range to zero");
				return false;
			}
			
			sql = "SELECT COUNT(*) FROM UNS_IncentiveSchemaConfig WHERE"
				+ " (RangeFrom BETWEEN ? AND ?) AND UNS_IncentiveSchemaConfig_ID <> ?"
				+ " AND UNS_Incentive_ID = ?";
			exists = DB.getSQLValue(get_TrxName(), sql, getRangeFrom(), getRangeTo(),
					get_ID(), getUNS_Incentive_ID()) > 0;
			if(exists)
			{
				log.saveError("Error", "Has found another configuration with "
						+ " range from between this range from and range to");
				return false;
			}
		}
		
		if(getAgeFrom() > 0)
		{
			String sql = "SELECT COUNT(*) FROM UNS_IncentiveSchemaConfig WHERE"
					+ " AgeFrom = 0 AND AgeTo = 0 AND UNS_IncentiveSchemaConfig_ID <> ?"
					+ " AND UNS_Incentive_ID = ?";
			boolean exists = DB.getSQLValue(get_TrxName(), sql, get_ID(), getUNS_Incentive_ID()) > 0;
			if(exists)
			{
				log.saveError("Error", "Has found another configuration with age from zero and age zero");
				return false;
			}
			
			sql = "SELECT COUNT(*) FROM UNS_IncentiveSchemaConfig WHERE"
					+ " (AgeTo BETWEEN ? AND ? OR AgeFrom BETWEEN ? AND ?) AND UNS_IncentiveSchemaConfig_ID <> ?"
					+ " AND UNS_Incentive_ID = ?";
				exists = DB.getSQLValue(get_TrxName(), sql, getAgeFrom(), getAgeTo(), getAgeFrom(), getAgeTo(),
						get_ID(), getUNS_Incentive_ID()) > 0;
				if(exists)
				{
					log.saveError("Error", "Has found another configuration with "
							+ " age from between this age from and age to");
					return false;
				}
		}
		
		if(getQtyMultiplier().signum() > 0 && !isIncentiveAmount())
		{
			log.saveError("Error", "Cannot use multiply qty if incentive on percent");
			return false;
		}
		return true;
	}
}