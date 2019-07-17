/**
 * 
 */
package com.uns.model.process;

import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.compiere.wf.MWorkflow;

import com.uns.util.MessageBox;

/**
 * @author root
 *
 */
public class UNSCompletingIncompleteDocument extends SvrProcess {
	
	private int TABLE_ID = 0;
	private int DOCUMENT_TYPE_ID = 0;
	private String EXTENSION_HANDLER = null;
	private Timestamp START_DATE = null;
	private Timestamp END_DATE = null;
	private int DATE_COLUMN_ID = 0;
	private IProcessUI PROCESS_UI = null;
	private int TOTAL_SUCCESS = 0;
	private int TOTAL_FAILED = 0;
	private String DOC_ACTION = null;
	private String USER = null;
	private String ADVANCE_WHERECLAUSE = null;
	private final String INITIAL_IMPORT_CTX = "ON_IMPORT";

	/**
	 * 
	 */
	public UNSCompletingIncompleteDocument() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] pis = getParameter();
		for(ProcessInfoParameter pi : pis)
		{
			String paramName = pi.getParameterName();
			if(paramName.equals("AD_Table_ID"))
				TABLE_ID = pi.getParameterAsInt();
			else if(paramName.equals("C_DocType_ID"))
				DOCUMENT_TYPE_ID = pi.getParameterAsInt();
			else if(paramName.equals("ExtensionHandler"))
				EXTENSION_HANDLER = pi.getParameterAsString();
			else if(paramName.equals("AD_Column_ID"))
				DATE_COLUMN_ID = pi.getParameterAsInt();
			else if(paramName.equals("StartDate"))
				START_DATE = pi.getParameterAsTimestamp();
			else if(paramName.equals("EndDate"))
				END_DATE = pi.getParameterAsTimestamp();
			else if(paramName.equals("DocAction"))
				DOC_ACTION = pi.getParameterAsString();
			else if(paramName.equals("WhereClause"))
				ADVANCE_WHERECLAUSE = pi.getParameterAsString();
			else
				throw new AdempiereException("Unhandled parameter name " + paramName);
		}
		
		USER = Env.getContext(getCtx(), "AD_User_Name");
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		if(TABLE_ID == 0)
		{
			throw new AdempiereException("Field mandatory Table");
		}
		if(DOCUMENT_TYPE_ID == 0 && !continueProcess("Document Type"))
		{
			return "Process aborted";
		}
		if(Util.isEmpty(EXTENSION_HANDLER, true) && !continueProcess("Extension Handler"))
		{
			return "Process aborted";
		}
		if(START_DATE == null && !continueProcess("Start Date"))
		{
			return "Process aborted";
		}
		if(END_DATE == null && !continueProcess("End Date"))
		{
			return "Process aborted";
		}
		if((START_DATE != null || END_DATE != null) && DATE_COLUMN_ID == 0)
		{
			throw new AdempiereUserError("Field mandatory Date Column");
		}
		if(Util.isEmpty(DOC_ACTION, true))
		{
			throw new AdempiereUserError("Field mandatory Document Action");
		}

		PROCESS_UI = Env.getProcessUI(getCtx());
		Env.setContext(Env.getCtx(), INITIAL_IMPORT_CTX, true);
		
		if(Util.isEmpty(USER, true))
		{
			int AD_User_ID = getAD_User_ID();
			String sql = "SELECT Name FROM AD_User WHERE AD_User_ID = ?";
			USER = DB.getSQLValueString(get_TrxName(), sql, AD_User_ID);
		}
		
		MTable processed = MTable.get(getCtx(), TABLE_ID);
		if(!Util.isEmpty(EXTENSION_HANDLER, true))
			processed.setExtensionHandler(EXTENSION_HANDLER);
		
		MColumn col = DATE_COLUMN_ID == 0 ? null : MColumn.get(getCtx(), DATE_COLUMN_ID);
		StringBuilder sb = new StringBuilder("SELECT ARRAY_TO_STRING(ARRAY_AGG(").append(processed.getTableName())
				.append("_ID").append("), ';')").append(" FROM ").append(processed.getTableName());
		
		if(DOC_ACTION.equals(DocAction.ACTION_Complete))
		{
			sb.append(" WHERE DocStatus IN ('DR', 'IN', 'IP')");
		}
		else if(DOC_ACTION.equals(DocAction.ACTION_Reverse_Accrual)
				|| DOC_ACTION.equals(DocAction.ACTION_Reverse_Correct))
		{
			sb.append(" WHERE DocStatus = 'CO'");
		}
		else if(DOC_ACTION.equals(DocAction.ACTION_Void))
		{
			sb.append(" WHERE DocStatus NOT IN ('VO','RE','CL') ");
		}
		else if(DOC_ACTION.equals(DocAction.ACTION_Close))
		{
			sb.append(" WHERE DocStatus NOT IN ('RE','VO'"); 
		}
		else
		{
			throw new AdempiereUserError("Proses belum dibuat.");
		}
		
		if(DOCUMENT_TYPE_ID > 0)
		{
			sb.append(" AND ").append("C_DocType_ID = ").append(DOCUMENT_TYPE_ID);
		}
		
		if(col != null)
		{
			if(START_DATE != null || END_DATE != null)
			{
				sb.append(" AND ");
			}
			if(START_DATE != null && END_DATE != null)
			{
				sb.append(col.getColumnName()).append(" BETWEEN '").append(START_DATE)
				.append("' AND '").append(END_DATE).append("'");
			}
			else if(START_DATE != null)
			{
				sb.append(col.getColumnName()).append(" >= '").append(START_DATE).append("'");
			}
			else if(END_DATE != null)
			{
				sb.append(col.getColumnName()).append(" <= '").append(END_DATE).append("'");
			}
		}
		
		if(!Util.isEmpty(ADVANCE_WHERECLAUSE, true))
		{
			sb.append(" AND ").append(ADVANCE_WHERECLAUSE);
		}
		
		String sql = sb.toString();
		String values = DB.getSQLValueString(get_TrxName(), sql);
		
		if(null == values)
		{
			return "Semua dokumen dengan parameter yang anda masukkan sudah terproses sebelumnya...";
		}
		
		StringBuilder msg = new StringBuilder();
		String[] vals = values.split(";");
		
		if(vals == null || vals.length == 0)
		{
			return "Semua dokumen dengan parameter yang anda masukkan sudah terkomplit sebelumnya...";
		}
		
		int allcount = 0;
		
		for(int i=0; i< vals.length; i++)
		{
			PROCESS_UI.statusUpdate("::: Memproses Dokumen [" + ++allcount + " Dari " + vals.length 
					+ "(Berhasil " + TOTAL_SUCCESS + " Gagal " + TOTAL_FAILED + ")]. Tolong sabar sedikit yah " 
					+ USER + " :) :::");
			PO po = processed.getPO(new Integer(vals[i]), get_TrxName());			
			PROCESS_UI.statusUpdate(runAction(po));
		}
		
		msg.append("Total dokumen yang berhasil di proses ---> " + TOTAL_SUCCESS + "\n");
		msg.append("Total dokumen yang gagal di proses ---> " + TOTAL_FAILED + "\n");
		Env.setContext(Env.getCtx(), INITIAL_IMPORT_CTX, false);
		
		return msg.toString();
	}
	
	/**
	 * 
	 * @param nullValue
	 * @return
	 */
	private boolean continueProcess(String nullValue)
	{
		String msg = Msg.getMsg(getCtx(), "CompleteDocumentConfirmMsg");
		String title = Msg.getMsg(getCtx(), "CompleteDocumentConfirmTitle");
		int confirm = MessageBox.showMsg(getCtx(), getProcessInfo(), 
				msg + nullValue + " ?"
				, title
				, MessageBox.YESNO
				, MessageBox.ICONQUESTION);
		
		return confirm == MessageBox.RETURN_YES;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	private synchronized String runAction(PO po)
	{
		try
		{
			ProcessInfo pi = MWorkflow.runDocumentActionWorkflow(po, DOC_ACTION);
			if(!pi.isError())
			{
				++TOTAL_SUCCESS;
				return "Berhasil :: " + po.toString() + " - " + pi.getSummary();
			}
			
			++TOTAL_FAILED;
			return "Gagal proses dokumen " + po.toString() + " karena " + pi.getSummary() + "\n";
		}
		catch (Exception e)
		{
			++TOTAL_FAILED;
			return "Gagal proses dokumen " + po.toString() + " karena " + e.getMessage() + "\n";
		}
	}
}
