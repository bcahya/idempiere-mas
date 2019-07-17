/**
 * 
 */
package com.unicore.model.process;

import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.uns.util.MessageBox;

import com.unicore.model.MUNSSalesTargetLine;
import com.unicore.model.MUNSSalesTargetPeriodic;

/**
 * @author root
 *
 */
public class GenerateSalesTarget extends SvrProcess {
	
	private int m_Record_ID = 0;
	private MUNSSalesTargetLine m_model = null;
	private int m_Year = 2014;

	/**
	 * 
	 */
	public GenerateSalesTarget() {
		super();
	}
	
	public void setRecord_ID(int record_ID)
	{
		m_Record_ID = record_ID;
	}
	
	public int getRecord_ID()
	{
		if(super.getRecord_ID() > 0)
			return super.getRecord_ID();
		
		return m_Record_ID;
	}
	
	

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		for(ProcessInfoParameter pi : getParameter())
		{
			String paramName = pi.getParameterName();
			if(paramName == null)
				continue;
		}
		preparing();
	}
	
	public void preparing()
	{
		if(getRecord_ID() == 0)
			throw new AdempiereException("Required Parameter Record_ID. Record_ID");
		
		m_model = new MUNSSalesTargetLine(getCtx(), getRecord_ID(), get_TrxName());
		m_Year = new Integer(m_model.getParent().getC_Year().getFiscalYear());
	}
	
	public void run()
	{
		try
		{
			doIt();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Error on Process Sales Target : " + ex.getLocalizedMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{		
		MUNSSalesTargetPeriodic[] periodics = m_model.getLines();
		
		int record = 0;
		
		if(m_model.getTargetPeriodic().equals(MUNSSalesTargetLine.TARGETPERIODIC_Monthly))
			record =  12;
		else if(m_model.getTargetPeriodic().equals(MUNSSalesTargetLine.TARGETPERIODIC_Quarterly))
			record = 4;
		else if(m_model.getTargetPeriodic().equals(MUNSSalesTargetLine.TARGETPERIODIC_HalfYear))
			record = 2;
		else if(m_model.getTargetPeriodic().equals(MUNSSalesTargetLine.TARGETPERIODIC_Yearly))
			record = 1;
		else
			throw new AdempiereException("Unhandled Target Periodic " + m_model.getTargetPeriodic());
	
		boolean isDeletedPrev = true;
		if(periodics != null && periodics.length > 0)
		{
			String msg = Msg.getMsg(getCtx(), "DeletePreviousDataMsg");
			String title = Msg.getMsg(getCtx(), "DeletePreviousDataTitle");
			//TODO For webui
			int retVal = MessageBox.showMsg(
					getCtx(), getProcessInfo(), msg, title , 
					MessageBox.YESNOCANCEL, MessageBox.ICONQUESTION);
			
			if(retVal == MessageBox.RETURN_YES)
				remove(periodics);
			else if(retVal == MessageBox.RETURN_CANCEL)
				return "Process Aborted";
			else
				isDeletedPrev = false;
		}
		
		if(isDeletedPrev)
			generateSalesTarget(record);
		else
			calculateSalesTarget(periodics);
		
		return null;
	}
	
	/**
	 * 
	 * @param periodics
	 */
	private void calculateSalesTarget(MUNSSalesTargetPeriodic[] periodics)
	{
		for(MUNSSalesTargetPeriodic periodic : periodics)
		{
			GenerateDetailSalesTarget generateDetail = new GenerateDetailSalesTarget(periodic);
			generateDetail.run();
		}
	}
	
	private void generateSalesTarget(int record)
	{
		int prevEnd = 0;
		for(int i=prevEnd; i<record; i++)
		{
			MUNSSalesTargetPeriodic periodic = new MUNSSalesTargetPeriodic(m_model);
			int currentStart = ++prevEnd;
			int currentEnd = currentStart + (12 / record)-1;
			Timestamp startDate = null;
			Timestamp endDate = null;
			int C_PeriodStart_ID = 0;
			int C_PeriodEnd_ID = 0;
			
			String sql = "SELECT CONCAT(COALESCE(s.C_Period_ID, 0), '@', COALESCE(e.C_Period_ID, 0)"
					+ " , '@', s.StartDate, '@', e.endDate) FROM C_Year y LEFT JOIN C_Period s ON "
					+ " s.C_Year_ID = y.C_Year_ID  AND s.periodNo = ? LEFT JOIN C_Period e ON "
					+ " y.C_Year_ID = e.C_Year_ID AND e.PeriodNo = ? WHERE y.FiscalYear = '" + m_Year + "'";
			
			String result = DB.getSQLValueString(get_TrxName(), sql, currentStart, currentEnd);
			
			String[] resultSplit = result.split("@");
			C_PeriodStart_ID = new Integer(resultSplit[0]);
			C_PeriodEnd_ID = new Integer(resultSplit[1]);
			startDate = Timestamp.valueOf(resultSplit[2]);
			endDate = Timestamp.valueOf(resultSplit[3]);
			
			if(C_PeriodStart_ID <= 0)
			{
				throw new AdempiereException("Calculate Incentive : Period No " + currentStart);
			}
			else if(C_PeriodEnd_ID <= 0)
			{
				throw new AdempiereException("Calculate Incentive" );
			}
			
			periodic.setPeriodStart_ID(C_PeriodStart_ID);
			periodic.setPeriodEnd_ID(C_PeriodEnd_ID);
			periodic.setStartDate(startDate);
			periodic.setEndDate(endDate);
			if(m_model.isUseSameSchemaForEveryPeriod())
			{
				periodic.setDeduction(m_model.getDeduction());
				periodic.setIncentive(m_model.getIncentive());
				periodic.setTOPTarget(m_model.getTOPTarget());
				periodic.setValueTarget(m_model.getValueTarget());
			}
			else
			{
				periodic.setDeduction(Env.ZERO);
				periodic.setIncentive(Env.ZERO);
				periodic.setTOPTarget(Env.ZERO);
				periodic.setValueTarget(Env.ZERO);
			}
			
			periodic.saveEx();
			
			GenerateDetailSalesTarget generateDetail = new GenerateDetailSalesTarget(periodic);
			generateDetail.run();
			prevEnd = currentEnd;
		}
	}
	
	/**
	 * 
	 * @param pos
	 */
	private void remove(PO[] pos)
	{
		for(PO po :pos)
		{
			po.deleteEx(true);
		}
	}
}
