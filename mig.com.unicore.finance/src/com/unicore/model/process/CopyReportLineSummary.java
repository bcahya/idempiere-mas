/**
 * 
 */
package com.unicore.model.process;

import java.util.logging.Level;

import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.report.MReportLine;
import org.compiere.report.MReportSource;
import org.compiere.util.DB;

import com.uns.model.MReportLineSummary;
import com.uns.model.X_PA_ReportLineColumns;

/**
 * @author AzHaidar
 * 
 */
public class CopyReportLineSummary extends SvrProcess 
{
	/** The Record */
	private int p_Record_ID = 0;
	private int M_PA_ReportLineSummaryToCopy_ID = 0;
	private boolean m_DeleteOld = false;
	private boolean m_ContinueSeqNo = true;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	protected void prepare() 
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("DeleteOld"))
				m_DeleteOld  = para[i].getParameterAsBoolean();
			else if (name.equals("ContinueSeqNo"))
				m_ContinueSeqNo  = para[i].getParameterAsBoolean();
			else if (name.equals("PA_ReportLineSet_ID"))
				;//m_PA_ReportLineSetToCopy_ID = para[i].getParameterAsInt();
			else if (name.equals("PA_ReportLineSummary_ID"))
				M_PA_ReportLineSummaryToCopy_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_Record_ID = getRecord_ID();
	} // prepare

	/**
	 * Process
	 * 
	 * @return message
	 * @throws Exception
	 */
	protected String doIt() throws Exception 
	{
		MReportLineSummary thisLineSummary = new MReportLineSummary(getCtx(), p_Record_ID, get_TrxName());
		MReportLineSummary lineSummaryToCopy = new MReportLineSummary(getCtx(), M_PA_ReportLineSummaryToCopy_ID, get_TrxName());
		
		StringBuffer msg = new StringBuffer();
		
		int seqNoReportLine = 0;
		if (m_DeleteOld)
		{
			String msgTemp = deleteOldLineSummary();
			msg.append(msgTemp);
		}
		
		if(m_ContinueSeqNo) {
			String sql = "SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM PA_ReportLine "
					+ "WHERE PA_ReportLineSummary_ID=(SELECT PA_ReportLineSummary_ID FROM PA_ReportLineSummary rls "
					+ "		WHERE rls.PA_ReportLineSet_ID=? AND "
					+ "			rls.SeqNo=(SELECT MAX(SeqNo) FROM PA_ReportLineSummary rls2 "
					+ "				WHERE rls2.PA_ReportLineSet_ID=rls.PA_ReportLineSet_ID AND rls2.SeqNo < ?))";
			seqNoReportLine = DB.getSQLValue(get_TrxName(), sql, 
					thisLineSummary.getPA_ReportLineSet_ID(), thisLineSummary.getSeqNo());
		}
		
		int lineCount = 0;
		int lineColumnsCount = 0;
		int sourceCount = 0;
		
		MReportLine[] rLines = lineSummaryToCopy.getLines();
		
		for (MReportLine rLine : rLines)
		{
			MReportLine newRLine = new MReportLine(getCtx(), 0, get_TrxName());
			PO.copyValues(rLine, newRLine);
			newRLine.setPA_ReportLineSet_ID(thisLineSummary.getPA_ReportLineSet_ID());
			newRLine.setPA_ReportLineSummary_ID(thisLineSummary.get_ID());
			newRLine.setAD_Org_ID(thisLineSummary.getAD_Org_ID());
			
			String sql = "SELECT PA_ReportLine_ID FROM PA_ReportLine "
					+ "WHERE PA_ReportLineSet_ID=? AND "
					+ "Name=(SELECT Name FROM PA_ReportLine il WHERE il.PA_ReportLine_ID=?)";
			int oper_1_ID = DB.getSQLValueEx(
					get_TrxName(), sql, thisLineSummary.getPA_ReportLineSet_ID(), rLine.getOper_1_ID());
			if (oper_1_ID < 0)
				oper_1_ID = 0;
			
			int oper_2_ID = DB.getSQLValueEx(
					get_TrxName(), sql, thisLineSummary.getPA_ReportLineSet_ID(), rLine.getOper_2_ID());
			if (oper_2_ID < 0)
				oper_2_ID = 0;
			
			newRLine.setOper_1_ID(oper_1_ID);
			newRLine.setOper_2_ID(oper_2_ID);
			
			if (m_ContinueSeqNo) {
				newRLine.setSeqNo(seqNoReportLine);
				seqNoReportLine += 1;
			}
			newRLine.saveEx();
			
			lineCount++;
			
			X_PA_ReportLineColumns[] lineCols = rLine.getSpecificColumns();
			
			for (X_PA_ReportLineColumns lineColumn : lineCols)
			{
				X_PA_ReportLineColumns newLineColumn = new X_PA_ReportLineColumns(getCtx(), 0, get_TrxName());
				PO.copyValues(lineColumn, newLineColumn);
				newLineColumn.setPA_ReportLine_ID(newRLine.get_ID());
				newLineColumn.setAD_Org_ID(thisLineSummary.getAD_Org_ID());
				
				if (lineColumn.getLineReferal_ID() > 0)
				{
					int lineReferal_ID = DB.getSQLValueEx(get_TrxName(), sql, 
							thisLineSummary.getPA_ReportColumnSet_ID(), lineColumn.getLineReferal_ID());
					if (lineReferal_ID >=0)
						newLineColumn.setLineReferal_ID(lineReferal_ID);
				}
				
				newLineColumn.saveEx();
				
				lineColumnsCount++;
			}
			
			MReportSource[] sources = rLine.getSources();
			
			for (MReportSource source : sources)
			{
				MReportSource newSource = new MReportSource(getCtx(), 0, get_TrxName());
				PO.copyValues(source, newSource);
				newSource.setPA_ReportLine_ID(newRLine.get_ID());
				newSource.setAD_Org_ID(thisLineSummary.getAD_Org_ID());
				newSource.saveEx();
				
				sourceCount++;
			}
		}

		msg.append(" ReportLine copied=" + lineCount + "# ")
		.append("ReportLineColumns copied=" + lineColumnsCount + "# ")
		.append("ReportSource copied=" + sourceCount + "# ");
		
		return msg.toString();
	} // doIt
	
	protected String deleteOldLineSummary()
	{
		StringBuffer msg = new StringBuffer();
		
		String sql = "DELETE FROM PA_ReportSource WHERE PA_ReportLine_ID IN "
				+ "(SELECT PA_ReportLine_ID FROM PA_ReportLine WHERE PA_ReportLineSummary_ID=?)";
		
		int count = DB.executeUpdate(sql, getRecord_ID(), get_TrxName());
		
		msg.append("ReportSource Deleted #" + count + "; ");
		
		sql = "DELETE FROM PA_ReportLineColumns WHERE PA_ReportLine_ID IN "
				+ "(SELECT PA_ReportLine_ID FROM PA_ReportLine WHERE PA_ReportLineSummary_ID=?)";
		count = DB.executeUpdate(sql, getRecord_ID(), get_TrxName());
		
		msg.append("ReportLineColumns Deleted #" + count + "; ");
		
		sql = "DELETE FROM PA_ReportLine WHERE PA_ReportLineSummary_ID=?";
		count = DB.executeUpdate(sql, getRecord_ID(), get_TrxName());
		
		msg.append("ReportLine Deleted #" + count + "; ");
		
		return msg.toString();
	}

}
