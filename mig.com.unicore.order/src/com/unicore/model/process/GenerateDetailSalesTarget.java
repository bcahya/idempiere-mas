/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.unicore.model.MUNSSalesTargetDetail;
import com.unicore.model.MUNSSalesTargetPeriodic;

/**
 * @author root
 *
 */
public class GenerateDetailSalesTarget extends SvrProcess {

	private MUNSSalesTargetPeriodic m_model = null;
	private Properties m_ctx = null;
	private String m_trxName = null;
	private int m_record_id = 0;
	
	/**
	 * 
	 */
	public GenerateDetailSalesTarget() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		m_model = new MUNSSalesTargetPeriodic(getCtx(), getRecord_ID(), get_TrxName());
	}
	
	public void run()
	{
		try {
			prepare();
			doIt();
		} catch (Exception e) {
			throw new AdempiereException(this.getClass().getName() + " : " + e);
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		MUNSSalesTargetDetail[] details = m_model.getLines();
		if(null == details || details.length <= 0)
			return generateDetails();
		
		return calculateDetail();
	}
	
	private String calculateDetail()
	{
		MUNSSalesTargetDetail[] details = m_model.getLines();
		for(MUNSSalesTargetDetail detail : details)
		{
			BigDecimal rateValue = getAmtAcvOf(detail.getC_BPartner_ID(), m_model.getStartDate(), m_model.getEndDate());
			BigDecimal rateTop = getTOPRate(detail.getC_BPartner_ID(), m_model.getStartDate(), m_model.getEndDate());
			detail.setAmtAchieved(rateValue);
			detail.setTOPRate(rateTop);
			detail.saveEx();
		}
		return null;
	}
	
	private String generateDetails()
	{
		Integer[] C_BPartner_IDs = initialBPartner();
		if(C_BPartner_IDs.length <= 0)
			return null;
		for(Integer C_BPartner_ID : C_BPartner_IDs)
		{
			MUNSSalesTargetDetail detail = new MUNSSalesTargetDetail(m_model);
			detail.setC_BPartner_ID(C_BPartner_ID);
			detail.setAmtAchieved(getAmtAcvOf(C_BPartner_ID, m_model.getStartDate(), m_model.getEndDate()));
			detail.setTOPRate(getTOPRate(C_BPartner_ID, m_model.getStartDate(), m_model.getEndDate()));
			detail.saveEx();			
		}		
		return null;
	}
	
	public void setRecord_ID(int record_ID)
	{
		this.m_record_id = record_ID;
	}
	
	public void setCtx(Properties ctx)
	{
		this.m_ctx = ctx;
	}
	
	public void setTrxName(String trxname)
	{
		this.m_trxName = trxname;
	}
	
	public GenerateDetailSalesTarget(Properties ctx, int record_ID, String trxName)
	{
		setCtx(ctx);
		setRecord_ID(record_ID);
		setTrxName(trxName);
	}
	
	@Override
	public int getRecord_ID()
	{
		return m_record_id > 0 ? m_record_id : super.getRecord_ID();
	}
	
	@Override
	public Properties getCtx()
	{
		return m_ctx != null ? m_ctx : super.getCtx();
	}
	
	@Override
	public String get_TrxName()
	{
		return m_trxName != null ? m_trxName : super.get_TrxName();
	}
	
	
	/**
	 * 
	 * @return
	 */
	private Integer[] initialBPartner()
	{
		Integer[] C_BPartner_IDs = {m_model.getParent().getC_BPartner_ID()};
		if(m_model.getParent().getC_BPartner_ID() > 0)
			return C_BPartner_IDs;
		
		String sql = "SELECT array_to_string( array_agg(C_BPartner_ID),'@' )  FROM C_Bpartner WHERE "
				+ "IsSalesRep = 'Y' AND IsEmployee = 'Y' ";
		if(m_model.getParent().getSalesType() != null)
			sql = sql.concat(" AND SalesType = '").concat(m_model.getParent().getSalesType()).concat("'");
		if(m_model.getParent().getSalesLevel() != null)
			sql = sql.concat(" AND SalesLevel = '").concat(m_model.getParent().getSalesLevel()).concat("'");
		
		String result = DB.getSQLValueString(get_TrxName(), sql);
		if(result == null)
			return new Integer[0];
		
		String[] records = result.split("@");
		C_BPartner_IDs = new Integer[records.length];
		
		for(int i=0; i<records.length; i++)
			C_BPartner_IDs[i] = new Integer(records[i]);
		
		return C_BPartner_IDs;
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @param start
	 * @param end
	 * @return
	 */
	private BigDecimal getAmtAcvOf(int C_BPartner_ID, Timestamp start, Timestamp end)
	{
		String sql = "SELECT COALESCE(SUM(Amount), 0) FROM C_PaymentAllocate pa "
				+ " WHERE pa.C_Payment_ID IN (SELECT C_Payment_ID "
				+ " FROM C_Payment WHERE DateTrx BETWEEN ? AND ? AND EXISTS (SELECT 1 FROM "
				+ " C_Invoice WHERE C_Invoice_ID = pa.C_Invoice_ID AND EXISTS (SELECT 1 FROM "
				+ " AD_User WHERE AD_User_ID = C_Invoice.SalesRep_ID AND AD_User.C_Bpartner_ID = ?)))";
		
		
		return DB.getSQLValueBD(get_TrxName(), sql, start, end, C_BPartner_ID);
	}
	
	/**
	 * 
	 * @param C_BPartner_ID
	 * @param start
	 * @param end
	 * @return
	 */
	private BigDecimal getTOPRate(int C_BPartner_ID, Timestamp start, Timestamp end)
	{
		String sql = "SELECT array_to_string(array_agg(CONCAT(C_Invoice_ID, '@', DateInvoiced)), ';') "
				+ " FROM C_Invoice WHERE DateInvoiced BETWEEN ? AND ? AND IsSOTrx = ? "
				+ " AND (DocStatus = 'CO' OR DocStatus = 'CL') AND EXISTS (SELECT 1 FROM "
				+ " AD_User WHERE C_BPartner_ID = ? AND AD_User_ID = C_Invoice.SalesRep_ID)";
		
		String values = DB.getSQLValueString(get_TrxName(), sql, start, end, "Y", C_BPartner_ID);
		if(null == values || values.isEmpty())
			return Env.ZERO;
		
		String[] split1 = values.split(";");
		if(split1 == null || split1.length <= 0)
			return Env.ZERO;
		
		double totalTop = 0;
		int count = 0;
		for(String splitted1 : split1)
		{
			String[] invoice = splitted1.split("@");
			if(invoice == null || invoice.length <= 0)
				continue;
			
			sql = "SELECT array_to_string(array_agg(DateTrx), '@') from C_Payment "
					+ " WHERE C_Payment_ID IN (SELECT C_Payment_ID "
					+ " FROM C_PaymentAllocate WHERE C_Invoice_ID = ?)";
			
			Integer C_Invoice_ID = new Integer(invoice[0]);
			Timestamp dateInvoiced = Timestamp.valueOf(invoice[1]);
			
			String values2 = DB.getSQLValueString(get_TrxName(), sql, C_Invoice_ID);
			if(null == values2)
				continue;
			
			String[] split2 = values2.split("@");
			if(split2 == null || split2.length <= 0)
				continue;
			
			for(String payment : split2)
			{
				Timestamp dateTrx = Timestamp.valueOf(payment);
				int top = TimeUtil.getDaysBetween(dateInvoiced, dateTrx);
				totalTop += top;
				count ++;
			}
		}
		if(count == 0)
			return Env.ZERO;
		
		double rate = totalTop / count;
		return new BigDecimal(rate);
	}
	
	/**
	 * 
	 * @param periodic
	 */
	public GenerateDetailSalesTarget(MUNSSalesTargetPeriodic periodic)
	{
		setRecord_ID(periodic.get_ID());
		setCtx(periodic.getCtx());
		setTrxName(periodic.get_TrxName());
	}

}
