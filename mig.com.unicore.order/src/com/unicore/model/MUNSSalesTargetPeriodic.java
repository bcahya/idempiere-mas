/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author root
 *
 */
public class MUNSSalesTargetPeriodic extends X_UNS_SalesTargetPeriodic {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3789224465732389929L;
	private MUNSSalesTargetLine m_parent = null;
	private MUNSSalesTargetDetail[] m_lines = null;

	/**
	 * @param ctx
	 * @param UNS_SalesTargetPeriodic_ID
	 * @param trxName
	 */
	public MUNSSalesTargetPeriodic(Properties ctx,
			int UNS_SalesTargetPeriodic_ID, String trxName) {
		super(ctx, UNS_SalesTargetPeriodic_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSalesTargetPeriodic(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSSalesTargetLine getParent()
	{
		if(null != m_parent)
			return m_parent;
		
		m_parent = new MUNSSalesTargetLine(getCtx(), getUNS_SalesTargetLine_ID(), get_TrxName());
		return m_parent;
	}
	
	public MUNSSalesTargetDetail[] getLines(boolean requery)
	{
		if(null != m_lines && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSSalesTargetDetail> list = Query.get(
				getCtx(), UNSOrderModelFactory.EXTENSION_ID, MUNSSalesTargetDetail.Table_Name
				, MUNSSalesTargetDetail.COLUMNNAME_UNS_SalesTargetPeriodic_ID + "=?", get_TrxName())
				.setParameters(getUNS_SalesTargetPeriodic_ID()).list();
		
		m_lines = new MUNSSalesTargetDetail[list.size()];
		list.toArray(m_lines);
		
		return m_lines;
	}
	
	public MUNSSalesTargetDetail[] getLines()
	{
		return getLines(false);
	}
	
	public MUNSSalesTargetPeriodic(MUNSSalesTargetLine parent)
	{
		super(parent.getCtx(), 0, parent.get_TrxName());
		setUNS_SalesTargetLine_ID(parent.get_ID());
		setAD_Org_ID(parent.getAD_Org_ID());
	}
	
	@Override 
	public boolean beforeSave(boolean newRecord)
	{
		if(newRecord)
		{
			MAcctSchema[] schemas = MAcctSchema.getClientAcctSchema(getCtx(), getAD_Client_ID(), get_TrxName());
			MAcctSchema schema = schemas[0];
			
			if(schema.isSTIUsingPayrollDate())
				synchronizeDate();;
		}
		return super.beforeSave(newRecord);
	}
	
	public void synchronizeDate()
	{
		int record;
		if(getParent().getTargetPeriodic().equals(MUNSSalesTargetLine.TARGETPERIODIC_Monthly))
			record =  1;
		else if(getParent().getTargetPeriodic().equals(MUNSSalesTargetLine.TARGETPERIODIC_Quarterly))
			record = 3;
		else if(getParent().getTargetPeriodic().equals(MUNSSalesTargetLine.TARGETPERIODIC_HalfYear))
			record = 6;
		else if(getParent().getTargetPeriodic().equals(MUNSSalesTargetLine.TARGETPERIODIC_Yearly))
			record = 12;
		else
			throw new AdempiereException("Unhandled Target Periodic " + getParent().getTargetPeriodic());
		
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
			calendar.add(Calendar.MONTH, record);
		else
			calendar.add(Calendar.MONTH, record-1);
		
		calendar.set(Calendar.DATE, end);
		
		Timestamp newEnd = new Timestamp(calendar.getTimeInMillis());
		
		setStartDate(newStart);
		setEndDate(newEnd);		
	}
}
