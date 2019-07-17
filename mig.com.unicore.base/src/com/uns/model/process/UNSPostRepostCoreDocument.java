/**
 * 
 */
package com.uns.model.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.util.IProcessUI;
import org.compiere.acct.DocManager;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MInOut;
import org.compiere.model.MInventory;
import org.compiere.model.MInvoice;
import org.compiere.model.MMatchInv;
import org.compiere.model.MMovement;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.compiere.util.Util;

/**
 * @author root
 *
 */
public class UNSPostRepostCoreDocument extends SvrProcess {

	private Timestamp m_dateFrom = null;
	private Timestamp m_dateTo = null;
	private boolean m_force = false;
	private MAcctSchema[] m_ass = null;
	private String m_errorLog = "";
	private IProcessUI m_processMonitor;
	
	/**
	 * 
	 */
	public UNSPostRepostCoreDocument() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] params = getParameter();
		int m_acctSchema_ID = 0;
		for(ProcessInfoParameter param : params)
		{
			if(param.getParameterName() == null)
				continue;
			if(param.getParameterName().equals("DateFrom"))
				m_dateFrom = param.getParameterAsTimestamp();
			else if(param.getParameterName().equals("DateTo"))
				m_dateTo = param.getParameterAsTimestamp();
			else if(param.getParameterName().equals("IsForce"))
				m_force = param.getParameterAsBoolean();
			else if(param.getParameterName().equals("C_AcctSchema_ID"))
				m_acctSchema_ID = param.getParameterAsInt();
			else
				log.log(Level.WARNING, "UNKNOWN PARAMETER " .concat(param.getParameterName()));
		}
		
		if(m_acctSchema_ID > 0)
			m_ass = new MAcctSchema[] {new MAcctSchema(getCtx(), m_acctSchema_ID, get_TrxName())};
		else
			m_ass = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		m_processMonitor = Env.getProcessUI(getCtx());
		
		List<Helper> helpers = new ArrayList<>();
		helpers.add(new Helper(MInvoice.Table_Name, MInvoice.Table_ID,  MInvoice.COLUMNNAME_DateInvoiced, null));
		helpers.add(new Helper(MInOut.Table_Name, MInOut.Table_ID, MInOut.COLUMNNAME_MovementDate, "MovementType IN ('V+','C+')"));
		helpers.add(new Helper(MMatchInv.Table_Name, MMatchInv.Table_ID, MMatchInv.COLUMNNAME_DateTrx
				, "M_InoutLine_ID IN (SELECT M_InoutLine_ID FROM M_InoutLine WHERE M_Inout_ID IN "
						+ "(SELECT M_Inout_ID FROM M_Inout WHERE MovementType IN ('V+') AND M_Inout.Posted = 'Y'))"));
		helpers.add(new Helper(MInOut.Table_Name, MInOut.Table_ID, MInOut.COLUMNNAME_MovementDate, "MovementType IN ('V-','C-')"));
		helpers.add(new Helper(MMatchInv.Table_Name, MMatchInv.Table_ID, MMatchInv.COLUMNNAME_DateTrx
				, "M_InoutLine_ID IN (SELECT M_InoutLine_ID FROM M_InoutLine WHERE M_Inout_ID IN "
						+ "(SELECT M_Inout_ID FROM M_Inout WHERE MovementType IN ('V+') AND M_Inout.Posted = 'Y'))"));
		helpers.add(new Helper(MInventory.Table_Name, MInventory.Table_ID, MInventory.COLUMNNAME_MovementDate, null));
		helpers.add(new Helper(MMovement.Table_Name, MMovement.Table_ID, MMovement.COLUMNNAME_MovementDate, null));
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(m_dateTo.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
		m_dateTo = new Timestamp(calendar.getTimeInMillis());
		postSession(helpers, m_dateFrom);
		if(!Util.isEmpty(m_errorLog, true))
		{
			m_errorLog = "Process stoped ".concat(m_errorLog);
		}
		return m_errorLog;
	}
	
	/**
	 * 
	 * @param helpers
	 * @param date
	 */
	private void postSession(List<Helper> helpers, Timestamp date) throws InterruptedException
	{
		if(date.compareTo(m_dateTo) > 0)
			return;
		long sleepTimes = sleepTimes();
		if (sleepTimes > 0)
		{
			long timemilis = System.currentTimeMillis();
			timemilis = timemilis + sleepTimes;
			Timestamp endWait = new Timestamp(timemilis);
			String msg = "Process will be hold until " 
					+ endWait.toString() + " Last Processed date " 
					+ date.toString();
			
			log.log(Level.INFO, msg);
			m_processMonitor.statusUpdate(msg);
			
			Thread.sleep(sleepTimes);
		}
		
		for(int i=0; i< helpers.size(); i++)
		{
			doPost(helpers.get(i), date);
			if(!Util.isEmpty(m_errorLog, true))
				m_errorLog = m_errorLog.concat(" \n Last processed date ") + date;
		}
		
		date = TimeUtil.addDays(date, 1);		
		postSession(helpers, date);
	}
	
	/**
	 * Post Document
	 * @param helper
	 * @param date
	 */
	public void doPost(Helper helper, Timestamp date)
	{
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(helper.TABLE_NAME)
				.append(" WHERE ProcessedOn IS NOT NULL AND ProcessedON > 0 ").append(" AND ")
				.append(helper.DATE_COLUMN).append("::TIMESTAMP::DATE = '").append(date)
				.append("'::TIMESTAMP::DATE AND Posted = 'E'");
		
		String msg = "Date: " + date + ": Reposting " + helper.TABLE_NAME;
		m_processMonitor.statusUpdate(msg);
		
		if(!Util.isEmpty(helper.WHERECLAUSE, true))
		{
			sb.append(" AND ").append(helper.WHERECLAUSE);
		}
		
		sb.append(" ORDER BY ").append(helper.DATE_COLUMN);
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String trxName = Trx.createTrxName("POST");
		Trx myTrx = Trx.get(trxName, true);
		
		try
		{
			
			st = DB.prepareStatement(sb.toString(), trxName);
			rs = st.executeQuery();
			while (rs.next())
			{
				String posted = rs.getString("Posted");
				boolean repost = !posted.equals("N");
				String error = DocManager.postDocument(m_ass, helper.TABLE_ID, rs, m_force, repost, trxName);
				if(!Util.isEmpty(error, true))
				{
					m_errorLog = m_errorLog.concat(error).concat("\n ********************** \n");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, st);
			try
			{
				myTrx.commit();
				myTrx.close();
				myTrx = null;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private long sleepTimes ()
	{
		long sleepTimes = 0;
		int start = 8;
		int end = 19;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		int curHour = cal.get(Calendar.HOUR_OF_DAY);
		int curMinute = cal.get(Calendar.MINUTE);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		
		if (day != 1 && curHour >= start && curHour < end)
		{
			sleepTimes = end - curHour;
			curMinute = curMinute * 60 * 1000;
			sleepTimes = sleepTimes * 60 *60 * 1000;
			sleepTimes = sleepTimes - curMinute;
		}
		
		return sleepTimes;
	}
}

class Helper
{
	String TABLE_NAME = null;
	String DATE_COLUMN = null;
	String WHERECLAUSE = null;
	int TABLE_ID = 0;

	/**
	 * 
	 * @param tableName
	 * @param tableID
	 * @param dateColumn
	 * @param whereClause
	 */
	public Helper(String tableName,int tableID, String dateColumn, String whereClause)
	{
		this.TABLE_NAME = tableName; 
		this.DATE_COLUMN = dateColumn; 
		this.WHERECLAUSE = whereClause;
		this.TABLE_ID = tableID;
	}
}