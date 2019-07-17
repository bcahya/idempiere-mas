/**
 * 
 */
package com.unicore.model.process;


import java.util.Properties;

import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPayment;
import org.compiere.model.MPaymentAllocate;
import org.compiere.model.PO;

/**
 * @author root
 *
 */
public class CalculateIncentive {
	
	private Properties						m_ctx							= null;
	private String							m_trxName						= null;
	private PO								m_PO							= null;

	public CalculateIncentive()
	{
		super();
	}
	
	public void set_TrxName(String trxName)
	{
		this.m_trxName = trxName;
	}
	
	public String get_TrxName()
	{
		return m_trxName;
	}
	
	public void setCtx(Properties ctx)
	{
		this.m_ctx = ctx;
	}
	
	public Properties getCtx()
	{
		return m_ctx;
	}
	
	public CalculateIncentive(PO po)
	{
		set_TrxName(po.get_TrxName());
		setCtx(po.getCtx());
		m_PO = po;
	}
	
	/***
	 * calculate deduction of incentive.
	 * only for void reverse dsj
	 */
	public void calculate()
	{		
		String DocAction = m_PO.get_ValueAsString("DocAction");
		if(!DocAction.equals(MInvoice.DOCACTION_Re_Activate)
				&& !DocAction.equals(MInvoice.DOCACTION_Reverse_Accrual)
				&& !DocAction.equals(MInvoice.DOCACTION_Reverse_Correct)
				&& !DocAction.equals(MInvoice.DOCACTION_Void))
			return;
		
		if(m_PO instanceof MInvoice)
		{
			MInvoiceLine[] lines = ((MInvoice) m_PO).getLines();
			for(int i=0; i<lines.length; i++)
			{
//				calculate(MUNSAchievedIncentiveLine.get(lines[i], get_TrxName()));
			}
		}
		else if(m_PO instanceof MPayment)
		{
			MPaymentAllocate[] allocats = MPaymentAllocate.get((MPayment) m_PO);
			for(int i=0; i<allocats.length; i++)
			{
//				calculate(MUNSAchievedIncentiveLine.get(allocats[i], get_TrxName()));
			}
		}
	}
	
//	private void calculate(MUNSAchievedIncentiveLine acvLine)
//	{
//		if(null == acvLine)
//			return;
//		
//		BigDecimal deductionAmt = DB.getSQLValueBD(
//				get_TrxName(), "SELECT COALESCE(SUM(Amount), 0) FROM UNS_AchievedIncentive_Line WHERE " + m_PO.get_TableName() + "_ID = ?"
//				, m_PO.get_ID());
//		deductionAmt = deductionAmt.negate();
//		Timestamp date = null;
//		
//		if(m_PO.get_TableName().equals(MInvoice.Table_Name))
//			date = (Timestamp) m_PO.get_Value("DateInvoiced");
//		else if(m_PO.get_TableName().equals(MPayment.Table_Name))
//			date = (Timestamp)m_PO.get_Value("DateTrx");
//		else
//			throw new AdempiereException("Unhandled Table " + m_PO.get_TableName());
//		
//		MUNSAcvIncentiveByPeriod acvPeriod = acvLine.getParent().getCreateOf(date);
//		MUNSAchievedIncentiveLine acvIncentiveLine = new MUNSAchievedIncentiveLine(acvPeriod);
//		acvIncentiveLine.set_ValueOfColumn(m_PO.get_TableName() + "_ID", m_PO.get_ID());
//		acvIncentiveLine.setAmount(deductionAmt);
//		acvIncentiveLine.saveEx();
//	}
}

