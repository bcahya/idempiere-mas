/**
 * 
 */
package com.unicore.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MPeriod;
import org.compiere.model.MUser;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSAcvIncentiveByPeriod extends X_UNS_AcvIncentiveByPeriod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSAchievedIncentiveLine[] m_lines = null;
	private MUNSAchievedIncentive m_parent = null;
	
	/**
	 * @param ctx
	 * @param UNS_AcvIncentiveByPeriod_ID
	 * @param trxName
	 */
	public MUNSAcvIncentiveByPeriod(Properties ctx,
			int UNS_AcvIncentiveByPeriod_ID, String trxName) {
		super(ctx, UNS_AcvIncentiveByPeriod_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSAcvIncentiveByPeriod(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSAchievedIncentiveLine[] getLines(boolean requery)
	{
		if (null != m_lines && !requery)
			return m_lines;
		
		List<MUNSAchievedIncentiveLine> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSAchievedIncentiveLine.Table_Name
				, MUNSAchievedIncentiveLine.COLUMNNAME_UNS_AcvIncentiveByPeriod_ID + "=?"
				, get_TrxName())
				.setParameters(getUNS_AcvIncentiveByPeriod_ID()).list();
		
		m_lines = new MUNSAchievedIncentiveLine[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSAchievedIncentiveLine[] getLines()
	{
		return getLines(false);
	}
	
	/**
	 * 
	 */
	public MUNSAchievedIncentive getParent()
	{
		if(null != m_parent)
			return m_parent;
		
		return (MUNSAchievedIncentive) getUNS_AchievedIncentive();
	}
	
	/**
	 * 
	 * @param parent
	 */
	public MUNSAcvIncentiveByPeriod(MUNSAchievedIncentive parent)
	{
		super(parent.getCtx(), parent.get_ID(), parent.get_TrxName());
		this.m_parent = parent;
		this.setAD_Org_ID(parent.getAD_Org_ID());
		this.setUNS_AchievedIncentive_ID(parent.get_ID());
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(newRecord)
		{
			MPeriod period = new MPeriod(getCtx(), getC_Period_ID(), get_TrxName());
			Timestamp startDate = period.getStartDate();
			Timestamp endDate = period.getEndDate();
			setStartDate(startDate);
			setEndDate(endDate);
			
			MAcctSchema[] acctSchemas = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID(), get_TrxName());
			MAcctSchema acctSchema = acctSchemas[0];
			
			if(acctSchema.isSTIUsingPayrollDate())
				synchronizeDate();
		}
		
		return super.beforeSave(newRecord);
	}
	
	/**
	 * 
	 * @param C_InvoiceLine_ID
	 * @param UNS_Incentive_ID
	 * @param createNew
	 * @return
	 */
	public MUNSAchievedIncentiveLine getAchievedIncentiveLine(int C_InvoiceLine_ID, int UNS_Incentive_ID, boolean createNew)
	{
		MUNSAchievedIncentiveLine line = null;
		for(int i=0; i<getLines().length; i++)
		{
			line = getLines()[i];
			if(line.getC_InvoiceLine_ID() == C_InvoiceLine_ID)
				return line;
			
			line = null;
		}
		if(createNew)
		{
			line = new MUNSAchievedIncentiveLine(this);
			line.setC_InvoiceLine_ID(C_InvoiceLine_ID);
			line.saveEx();
		}
		
		return line;
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		updateHeader();
		return super.afterSave(newRecord, success);
	}
	
	/**
	 * 
	 */
	private void updateHeader()
	{
		MUNSAchievedIncentive parent = getParent();
		MPeriod period = new MPeriod(getCtx(), getC_Period_ID(), get_TrxName());
		int month = period.getPeriodNo();
		parent.setAchievement(month, getAmount());
		parent.saveEx();
	}
	
	/**
	 * Synchronize start date and end date with payroll start date end payroll date end.
	 */
	public void synchronizeDate()
	{
		String sql = "SELECT PayrollDateStart FROM UNS_PayrollConfiguration WHERE ? "
				.concat("BETWEEN ValidFrom AND ValidTo AND ? BETWEEN ValidFrom AND ValidTo");
		int Start = DB.getSQLValue(get_TrxName(), sql, getStartDate(), getEndDate());
		sql = "SELECT PayrollDateEnd FROM UNS_PayrollConfiguration WHERE ? "
				.concat("BETWEEN ValidFrom AND ValidTo AND ? BETWEEN ValidFrom AND ValidTo");
		int end = DB.getSQLValue(get_TrxName(), sql, getStartDate(), getEndDate());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(getStartDate().getTime());
		double medianOfMonth = calendar.getActualMaximum(Calendar.DATE) / 2.0;
		
		if (Start > medianOfMonth)
			calendar.add(Calendar.MONTH, -1);
		
		calendar.set(Calendar.DATE, Start);
		Timestamp newStart = new Timestamp(calendar.getTimeInMillis());
		
		if(end < Start)
			calendar.add(Calendar.MONTH, 1);
		
		calendar.set(Calendar.DATE, end);
		
		Timestamp newEnd = new Timestamp(calendar.getTimeInMillis());
		
		setStartDate(newStart);
		setEndDate(newEnd);		
	}
	
	/**
	 * 
	 * @param date
	 * @param trxName
	 * @return
	 */
	public static MUNSAcvIncentiveByPeriod get(int C_BPartner_ID, Timestamp date , String trxName)
	{
		MUNSAcvIncentiveByPeriod model = null;
		String sql = "SELECT * FROM UNS_AcvIncentiveByPeriod WHERE ? BETWEEN StartDate AND EndDate AND UNS_AchievedIncentive_ID = "
				.concat("(SELECT UNS_AchievedIncentive_ID FROM UNS_AchievedIncentive WHERE C_BPartner_ID = ?)");
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = DB.prepareStatement(sql, trxName);
			st.setTimestamp(1, date);
			st.setInt(2, C_BPartner_ID);
			rs = st.executeQuery();
			if(rs.next())
				model = new MUNSAcvIncentiveByPeriod(Env.getCtx(), rs, trxName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	
	/***
	 * 
	 * @param date
	 * @param trxName
	 * @return
	 */
	public static MUNSAcvIncentiveByPeriod getCreate(Properties ctx, int SalesRep_ID, int C_BPartner_ID,
			Timestamp date , String trxName)
	{
		int personID = 0;
		if(SalesRep_ID > 0)
		{
			MUser user = MUser.get(ctx, SalesRep_ID);
			personID = user.getC_BPartner_ID();
		}
		else if(C_BPartner_ID > 0)
			personID = C_BPartner_ID;
		
		MUNSAcvIncentiveByPeriod model = get(personID, date, trxName);
		if(null != model)
			return model;

		model = new MUNSAcvIncentiveByPeriod(ctx, 0, trxName);
		MAcctSchema[] schemas = MAcctSchema.getClientAcctSchema(Env.getCtx(), Env.getAD_Client_ID(Env.getCtx()), trxName);
		MAcctSchema schema = schemas[0];
		MPeriod period = null;
		
		if(schema.isSTIUsingPayrollDate())
		{
			String sql = "SELECT PayrollDateStart FROM UNS_PayrollConfiguration WHERE ? BETWEEN ValidFrom AND ValidTo";
			int Start = DB.getSQLValue(trxName, sql, date);
			sql = "SELECT PayrollDateEnd FROM UNS_PayrollConfiguration WHERE ? BETWEEN ValidFrom AND ValidTo";
			int end = DB.getSQLValue(trxName, sql, date);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(date.getTime());
			int current = calendar.get(Calendar.DATE);
			double medianOfMonth = calendar.getActualMaximum(Calendar.DATE) / 2.0;
			
			if(current < Start && Start > medianOfMonth)
				calendar.add(Calendar.MONTH, -1);
			
			calendar.set(Calendar.DATE, Start);
			Timestamp newStart = new Timestamp(calendar.getTimeInMillis());
			
			if(end < Start)
				calendar.add(Calendar.MONTH, 1);
			
			calendar.set(Calendar.DATE, end);
			Timestamp newEnd = new Timestamp(calendar.getTimeInMillis());
			if(Start < medianOfMonth)
				calendar.add(Calendar.DATE, -1);
			
			int C_Period_ID = DB.getSQLValue(
					trxName, "SELECT C_Period_ID FROM C_Period WHERE PeriodNo = ? AND C_Year_ID = (SELECT C_Year_ID FROM C_Year WHERE FiscalYear = ?"
					, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
			
			period = new MPeriod(Env.getCtx(), C_Period_ID, trxName);
			model.setC_Period_ID(C_Period_ID);
			model.setStartDate(newStart);
			model.setEndDate(newEnd);		
		}
		else
		{
			period = MPeriod.get(Env.getCtx(), date, Env.getAD_Org_ID(Env.getCtx()), trxName);
			model.setC_Period_ID(period.get_ID());
			model.setStartDate(period.getStartDate());
			model.setEndDate(period.getEndDate());		
		}
		
		int C_Year_ID = period.getC_Year_ID();
		
		MUNSAchievedIncentive acvIncentive = MUNSAchievedIncentive.get(personID, C_Year_ID, trxName);
		if(null == acvIncentive)
		{
			acvIncentive = new MUNSAchievedIncentive(Env.getCtx(), 0, trxName);
			if(SalesRep_ID > 0)
				acvIncentive.setAchievementType(X_UNS_AchievedIncentive.ACHIEVEMENTTYPE_Sales);
			else
				acvIncentive.setAchievementType(X_UNS_AchievedIncentive.ACHIEVEMENTTYPE_Customer);
			acvIncentive.setC_BPartner_ID(personID);
			acvIncentive.setC_Year_ID(C_Year_ID);
			acvIncentive.setAD_Org_ID(0);
			acvIncentive.saveEx();
		}
		
		model.setUNS_AchievedIncentive_ID(acvIncentive.get_ID());
		model.saveEx();
		
		return model;
	}
	
	/**
	 * 
	 * @param C_InvoiceLine_ID
	 * @param createNew
	 * @return
	 */
	public MUNSAchievedIncentiveLine getAchievedIncentiveLine(int C_InvoiceLine_ID, boolean createNew)
	{
		MUNSAchievedIncentiveLine line = null;
		for(int i=0; i<getLines().length; i++)
		{
			line = getLines()[i];
			if(line.getC_InvoiceLine_ID() == C_InvoiceLine_ID)
				return line;
			
			line = null;
		}
		if(createNew)
		{
			line = new MUNSAchievedIncentiveLine(this);
			line.setC_InvoiceLine_ID(C_InvoiceLine_ID);
			line.saveEx();
		}
		
		return line;
	}
}
