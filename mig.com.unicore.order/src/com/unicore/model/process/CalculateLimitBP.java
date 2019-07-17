/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MInvoice;
import org.compiere.model.MPayment;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.model.MUNSBPLimitRecommendation;
import com.unicore.model.MUNSSalesTargetLine;

/**
 * @author root
 *
 */
public class CalculateLimitBP extends SvrProcess {
	
	private int									m_record_ID				= 0;
	private MUNSBPLimitRecommendation			m_model					= null;
	private String								m_trxName				= null;
	private Properties							m_ctx					= null;
	private String								m_periodType			= null;
	public static final String					QUARTERLY				= "QT";
	public static final String					HALF_YEAR				= "HY";
	public static final String					YEARLY					= "YE";
	private BigDecimal							m_multiply				= Env.ONE;
	
	public void setMultiply(BigDecimal multiply)
	{
		this.m_multiply = multiply;
	}
	
	public BigDecimal getMultiply()
	{
		return this.m_multiply;
	}
	
	public void setPeriodType(String periodType)
	{
		this.m_periodType = periodType;
	}
	
	public  String getPeriodType()
	{
		return this.m_periodType;
	}
	
	/**
	 * 
	 */
	public CalculateLimitBP() {
		super();
	}
	
	public void setCtx(Properties ctx)
	{
		this.m_ctx = ctx;
	}
	
	@Override
	public Properties getCtx()
	{
		if(null != m_ctx)
			return this.m_ctx;
		
		setCtx(super.getCtx());
		return this.m_ctx;
	}
	
	public void set_TrxName(String trxName)
	{
		this.m_trxName = trxName;
	}
	
	@Override
	public String get_TrxName()
	{
		if(null != this.m_trxName)
			return this.m_trxName;
		
		set_TrxName(super.get_TrxName());
		return m_trxName;
	}
	
	public void setRecord_ID(int record_ID)
	{
		this.m_record_ID = record_ID;
	}
	
	@Override
	public int getRecord_ID()
	{
		if(this.m_record_ID > 0)
			return this.m_record_ID;
		
		m_record_ID = super.getRecord_ID();
		return this.m_record_ID;
	}
	
	public void setModel(MUNSBPLimitRecommendation BPLimitRecommend)
	{
		this.m_model = BPLimitRecommend;
	}
	
	public MUNSBPLimitRecommendation getModel()
	{
		return this.m_model;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] pips = getParameter();
		
		for(ProcessInfoParameter pip : pips)
		{
			String paramName = pip.getParameterName();
			if(null == paramName)
				continue;
			else if("Multiplier".equals(paramName))
				setMultiply(pip.getParameterAsBigDecimal());
			else if("PeriodType".equals(paramName))
				setPeriodType(pip.getParameterAsString());
			else
				log.log(Level.SEVERE, "Unknown parameter" + paramName);
			
		}
		
		this.m_record_ID = getRecord_ID();
		m_model = new MUNSBPLimitRecommendation(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		Timestamp currentDate = Env.getContextAsDate(getCtx(), "#Date");
		if(null == currentDate)
		{
			currentDate = new Timestamp(System.currentTimeMillis());
		}
		
		int monthInPeriod = 0;
		if(getPeriodType().equals(MUNSSalesTargetLine.TARGETPERIODIC_Quarterly))
		{
			monthInPeriod = 3;
		}
		else if(getPeriodType().equals(MUNSSalesTargetLine.TARGETPERIODIC_HalfYear))
		{
			monthInPeriod = 6;
		}
		else if(getPeriodType().equals(MUNSSalesTargetLine.TARGETPERIODIC_Yearly))
		{
			monthInPeriod = 12;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(currentDate.getTime());
		cal.add(Calendar.MONTH, -monthInPeriod);
		
		BigDecimal maxInvoiceInMonth		= Env.ZERO;
		for(int i=0; i<monthInPeriod; i++)
		{
			cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			Timestamp startDate = new Timestamp(cal.getTimeInMillis());
			
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			Timestamp endDate = new Timestamp(cal.getTimeInMillis());
			
			BigDecimal inMonthValue = getGrandTotal(startDate, endDate);
			
			if(maxInvoiceInMonth.compareTo(inMonthValue) < 0)
				maxInvoiceInMonth = inMonthValue;
			
			cal.add(Calendar.MONTH, 1);
		}
		
		maxInvoiceInMonth = maxInvoiceInMonth.multiply(getMultiply());
		if(getMultiply().signum() <= 0)
			setMultiply(Env.ONE);
		
		BigDecimal newLimit = maxInvoiceInMonth.multiply(getMultiply());
		m_model.setNewLimit(newLimit);
		m_model.saveEx();
		return "success";
	}
	
	private BigDecimal getGrandTotal(Timestamp start, Timestamp end)
	{
		String sql = "SELECT COALESCE(SUM(i.GrandTotal),0) FROM C_Invoice i WHERE i.DocStatus = ? " 
						+ " AND i.C_BPartner_ID = ? AND i.dateInvoiced BETWEEN ? AND ? "
						+ " AND NOT EXISTS(SELECT 1 FROM C_PaymentAllocate pa WHERE pa.C_Invoice_ID = i.C_Invoice_ID "
						+ " AND pa.C_Payment_ID IN (SELECT p.C_Payment_ID FROM C_Payment p WHERE "
						+ " p.DocStatus = ?))"; 
		
		BigDecimal result = DB.getSQLValueBD(
				get_TrxName(), sql, MInvoice.DOCSTATUS_Completed
				, m_model.getC_BPartner_ID(), start, /**end for test*/ new Timestamp(System.currentTimeMillis())
				, MPayment.DOCSTATUS_Completed);
		
		return result;
	}
}
