/**
 * 
 */
package com.unicore.model.process;

import java.util.logging.Level;

import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.report.MReportLine;
import org.compiere.report.MReportLineSet;
import org.compiere.report.MReportSource;
import org.compiere.util.DB;

import com.uns.model.MReportLineSummary;
import com.uns.model.X_PA_ReportLineColumns;

/**
 * @author AzHaidar
 * 
 */
public class CopyReportLineSet extends SvrProcess 
{
	/** The Record */
	private int p_Record_ID = 0;
	private int m_PA_ReportLineSetToCopy_ID = 0;
	private boolean m_DeleteOld = false;

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
			else if (name.equals("PA_ReportLineSet_ID"))
				m_PA_ReportLineSetToCopy_ID = para[i].getParameterAsInt();
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
		MReportLineSet thisRLSet = new MReportLineSet(getCtx(), p_Record_ID, get_TrxName());
		MReportLineSet rlSetToCopy = new MReportLineSet(getCtx(), m_PA_ReportLineSetToCopy_ID, get_TrxName());
		
		StringBuffer msg = new StringBuffer();
		
		int seqNoReportLine = 0;
		int seqNoSummary = 0;
		if (m_DeleteOld)
		{
			String msgTemp = deleteOldLineSet();
			msg.append(msgTemp);
		}
		else {
			String sql = "SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM PA_ReportLine "
					+ "WHERE PA_ReportLineSet_ID=?";
			seqNoReportLine = DB.getSQLValue(get_TrxName(), sql, thisRLSet.get_ID());

			sql = "SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM PA_ReportLineSummary "
					+ "WHERE PA_ReportLineSet_ID=?";
			seqNoSummary = DB.getSQLValue(get_TrxName(), sql, thisRLSet.get_ID());
		}
		
		MReportLineSummary[] summariesToCopy = rlSetToCopy.getLineSummaries();
		
		int summaryCount = 0;
		int lineCount = 0;
		int lineColumnsCount = 0;
		int sourceCount = 0;
		
		for (MReportLineSummary summaryToCopy : summariesToCopy)
		{
			MReportLineSummary newSummary = new MReportLineSummary(getCtx(), 0, get_TrxName());
			PO.copyValues(summaryToCopy, newSummary);
			newSummary.setPA_ReportLineSet_ID(thisRLSet.get_ID());
			newSummary.setAD_Org_ID(thisRLSet.getAD_Org_ID());
			
			if (!m_DeleteOld) {
				newSummary.setSeqNo(seqNoSummary);
				seqNoSummary += 10;
			}
			
			newSummary.saveEx();
			
			summaryCount++;
			
			MReportLine[] rLines = summaryToCopy.getLines();
			
			for (MReportLine rLine : rLines)
			{
				MReportLine newRLine = new MReportLine(getCtx(), 0, get_TrxName());
				PO.copyValues(rLine, newRLine);
				newRLine.setPA_ReportLineSet_ID(thisRLSet.get_ID());
				newRLine.setPA_ReportLineSummary_ID(newSummary.get_ID());
				newRLine.setAD_Org_ID(thisRLSet.getAD_Org_ID());
				
				String sql = "SELECT PA_ReportLine_ID FROM PA_ReportLine "
						+ "WHERE PA_ReportLineSet_ID=? AND "
						+ "Name=(SELECT Name FROM PA_ReportLine il WHERE il.PA_ReportLine_ID=?)";
				int oper_1_ID = DB.getSQLValueEx(get_TrxName(), sql, thisRLSet.get_ID(), rLine.getOper_1_ID());
				if (oper_1_ID < 0)
					oper_1_ID = 0;
				
				int oper_2_ID = DB.getSQLValueEx(get_TrxName(), sql, thisRLSet.get_ID(), rLine.getOper_2_ID());
				if (oper_2_ID < 0)
					oper_2_ID = 0;
				
				newRLine.setOper_1_ID(oper_1_ID);
				newRLine.setOper_2_ID(oper_2_ID);
				
				if (!m_DeleteOld) {
					newRLine.setSeqNo(seqNoReportLine);
					seqNoReportLine += 10;
				}
				newRLine.saveEx();
				
				lineCount++;
				
				X_PA_ReportLineColumns[] lineCols = rLine.getSpecificColumns();
				
				for (X_PA_ReportLineColumns lineColumn : lineCols)
				{
					X_PA_ReportLineColumns newLineColumn = new X_PA_ReportLineColumns(getCtx(), 0, get_TrxName());
					PO.copyValues(lineColumn, newLineColumn);
					newLineColumn.setPA_ReportLine_ID(newRLine.get_ID());
					newLineColumn.setAD_Org_ID(thisRLSet.getAD_Org_ID());
					
					if (lineColumn.getLineReferal_ID() > 0)
					{
						int lineReferal_ID = 
								DB.getSQLValueEx(get_TrxName(), sql, thisRLSet.get_ID(), lineColumn.getLineReferal_ID());
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
					newSource.setAD_Org_ID(thisRLSet.getAD_Org_ID());
					newSource.saveEx();
					
					sourceCount++;
				}
			}
		}
		msg.append("Summary copied=" + summaryCount + "# ")
		.append("ReportLine copied=" + lineCount + "# ")
		.append("ReportLineColumns copied=" + lineColumnsCount + "# ")
		.append("ReportSource copied=" + sourceCount + "# ");
		
		return msg.toString();
	} // doIt
	
	protected String deleteOldLineSet()
	{
		StringBuffer msg = new StringBuffer();
		
		String sql = "DELETE FROM PA_ReportSource WHERE PA_ReportLine_ID IN "
				+ "(SELECT PA_ReportLine_ID FROM PA_ReportLine WHERE PA_ReportLineSummary_ID IN ("
				+ "	SELECT PA_ReportLineSummary_ID FROM PA_ReportLineSummary WHERE PA_ReportLineSet_ID=?))";
		
		int count = DB.executeUpdate(sql, getRecord_ID(), get_TrxName());
		
		msg.append("ReportSource Deleted #" + count + "; ");
		
		sql = "DELETE FROM PA_ReportLineColumns WHERE PA_ReportLine_ID IN "
				+ "(SELECT PA_ReportLine_ID FROM PA_ReportLine WHERE PA_ReportLineSummary_ID IN ("
				+ "	SELECT PA_ReportLineSummary_ID FROM PA_ReportLineSummary WHERE PA_ReportLineSet_ID=?))";
		count = DB.executeUpdate(sql, getRecord_ID(), get_TrxName());
		
		msg.append("ReportLineColumns Deleted #" + count + "; ");
		
		sql = "DELETE FROM PA_ReportLine WHERE PA_ReportLineSummary_ID IN ("
				+ "	SELECT PA_ReportLineSummary_ID FROM PA_ReportLineSummary WHERE PA_ReportLineSet_ID=?)";
		count = DB.executeUpdate(sql, getRecord_ID(), get_TrxName());
		
		msg.append("ReportLine Deleted #" + count + "; ");
		
		sql = "DELETE FROM PA_ReportLineSummary WHERE PA_ReportLineSet_ID=?";
		count = DB.executeUpdate(sql, getRecord_ID(), get_TrxName());
		
		msg.append("ReportLineSummary Deleted #" + count + "; ");
		
		return msg.toString();
	}

}
