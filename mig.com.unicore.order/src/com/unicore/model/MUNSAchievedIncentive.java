/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPeriod;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author menjangan
 *
 */
public class MUNSAchievedIncentive extends X_UNS_AchievedIncentive {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSAcvIncentiveByPeriod[] m_AcvByPeriods = null;
	private MUNSAchievedIncentiveLine[] m_lines = null;
	

	/**
	 * @param ctx
	 * @param UNS_AchievedIncentive_ID
	 * @param trxName
	 */
	public MUNSAchievedIncentive(Properties ctx, int UNS_AchievedIncentive_ID,
			String trxName) {
		super(ctx, UNS_AchievedIncentive_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSAchievedIncentive(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 
	 * @return
	 */
	public MUNSAcvIncentiveByPeriod[] getIncentivePeriods()
	{
		return getIncentivePeriods(false);
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSAcvIncentiveByPeriod[] getIncentivePeriods(boolean requery)
	{
		if(null != m_AcvByPeriods && !requery)
		{
			set_TrxName(m_AcvByPeriods, get_TrxName());
			return m_AcvByPeriods;
		}
		
		List<MUNSAcvIncentiveByPeriod> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSAcvIncentiveByPeriod.Table_Name
				, MUNSAcvIncentiveByPeriod.COLUMNNAME_UNS_AchievedIncentive_ID + "=?", get_TrxName())
				.setParameters(getUNS_AchievedIncentive_ID()).list();
		
		m_AcvByPeriods = new MUNSAcvIncentiveByPeriod[list.size()];
		list.toArray(m_AcvByPeriods);
		
		return m_AcvByPeriods;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSAchievedIncentiveLine[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
			return m_lines;
		
		List<MUNSAchievedIncentiveLine> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSAchievedIncentiveLine.Table_Name
				, MUNSAchievedIncentiveLine.COLUMNNAME_UNS_AchievedIncentive_ID + "=?"
				, get_TrxName())
				.setParameters(getUNS_AchievedIncentive_ID()).list();
		
		m_lines  = new MUNSAchievedIncentiveLine[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	public MUNSAchievedIncentiveLine[] getLines()
	{
		return getLines(false);
	}
	
	/**
	 * 
	 * @param salesRep_ID
	 * @param C_Year_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSAchievedIncentive get(int salesRep_ID, int C_Year_ID, String trxName)
	{
		MUNSAchievedIncentive record = null;
		
		StringBuilder prepareSQL = new StringBuilder("SELECT * FROM ")
		.append(Table_Name).append(" WHERE ").append(COLUMNNAME_C_BPartner_ID)
		.append("=").append(salesRep_ID).append(" AND ").append(COLUMNNAME_C_Year_ID)
		.append("=").append(C_Year_ID);
		
		String sql = prepareSQL.toString();
		
		PreparedStatement st	= null;
		ResultSet rs			= null;
		
		try {
			st = DB.prepareStatement(sql, trxName);
			rs = st.executeQuery();
			if(rs.next())
				record = new MUNSAchievedIncentive(Env.getCtx(), rs, trxName);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DB.close(rs, st);
		}
		return record;
	}
	
	/**
	 * 
	 * @param C_Period_ID
	 * @return
	 */
	public MUNSAcvIncentiveByPeriod getAcvIncentivePeriodOf(int C_Period_ID)
	{
		int acvIncentivePeriod_ID = DB.getSQLValue(
				get_TrxName(), "SELECT UNS_AcvIncentiveByPeriod_ID FROM UNS_AcvIncentiveByPeriod WHERE UNS_AchievedIncentive_ID =?"
				, C_Period_ID);
		if(acvIncentivePeriod_ID <= 0)
			return null;
		
		MUNSAcvIncentiveByPeriod periodIncentive = new MUNSAcvIncentiveByPeriod(getCtx(), acvIncentivePeriod_ID, get_TrxName());
		return periodIncentive;
	}
	
	/**
	 * 
	 * @param month
	 * @param value
	 */
	public void setAchievement(int month, BigDecimal value)
	{
		switch (month)
		{
			case 1 :
				setAchievementJan(value);
				break;
			case 2 :
				setAchievementFeb(value);
				break;
			case 3 :
				setAchievementMarch(value);
				break;
			case 4 :
				setAchievementApr(value);
				break;
			case 5 :
				setAchievementMay(value);
				break;
			case 6 :
				setAchievementJun(value);
				break;
			case 7 :
				setAchievementJul(value);
				break;
			case 8 :
				setAchievementAug(value);
				break;
			case 9 :
				setAchievementSep(value);
				break;
			case 10 :
				setAchievementOct(value);
				break;
			case 11 :
				setAchievementNov(value);
				break;
			case 12 :
				setAchievementDec(value);
				break;
			default :
				throw new AdempiereException("Unknown month " + month);
		}
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public BigDecimal getAchievement(int month)
	{
		switch (month) {
		case 1 :
			return getAchievementJan();
		case 2 :
			return getAchievementFeb();
		case 3 :
			return getAchievementMarch();
		case 4 :
			return getAchievementApr();
		case 5 :
			return getAchievementMay();
		case 6 :
			return getAchievementJun();
		case 7 :
			return getAchievementJul();
		case 8 :
			return getAchievementAug();
		case 9 :
			return getAchievementSep();
		case 10 :
			return getAchievementOct();
		case 11 :
			return getAchievementNov();
		case 12 :
			return getAchievementDec();
		default:
			throw new AdempiereException("Unknown month " + month);
		}
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		return super.beforeSave(newRecord);
	}
	
	public void generateMonthlyRecord()
	{
		for(int i=1; i<=12; i++)
		{
			MUNSAcvIncentiveByPeriod periodIncentive = new MUNSAcvIncentiveByPeriod(this);
			int C_Period_ID = DB.getSQLValue(
					get_TrxName(), "SELECT C_Period_ID FROM C_Period_WHERE PeriodNo = ? AND C_Year_ID = ?"
					, i, getC_Year_ID());
			if(C_Period_ID <= 0)
				throw new AdempiereException("No period no " + i);
			
			periodIncentive.setC_Period_ID(C_Period_ID);
			periodIncentive.saveEx();
		}
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public MUNSAcvIncentiveByPeriod getOf(Timestamp date)
	{
		MUNSAcvIncentiveByPeriod line = null;
		for(MUNSAcvIncentiveByPeriod acvPeriod : getIncentivePeriods())
		{
			if(!date.before(acvPeriod.getEndDate()) && !date.after(acvPeriod.getStartDate()))
				continue;
			
			line = acvPeriod;
			break;
		}
		
		return line;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public MUNSAcvIncentiveByPeriod getCreateOf(Timestamp date)
	{
		MUNSAcvIncentiveByPeriod retVal = getOf(date);
		if(null != retVal)
			return retVal;
		
		retVal = new MUNSAcvIncentiveByPeriod(this);
		MPeriod period = MPeriod.get(getCtx(), date, getAD_Org_ID(), get_TrxName());
		retVal.setC_Period_ID(period.get_ID());
		retVal.saveEx();
		return retVal;
	}
}
