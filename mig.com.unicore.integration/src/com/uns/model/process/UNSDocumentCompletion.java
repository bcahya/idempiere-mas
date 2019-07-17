/**
 * 
 */
package com.uns.model.process;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Trx;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSIntegrationModelFactory;

/**
 * @author Haryadi
 *
 */
public class UNSDocumentCompletion extends SvrProcess 
{

	private int m_AD_Table_ID;
	private int m_AD_ColumnDate_ID;
	private int m_AD_Org_ID;
	private Timestamp m_DateFrom = null;
	private Timestamp m_DateTo = null;
	private boolean m_isIgnoreError = false;
	private boolean m_isIncludeInProgress = false;
	private boolean m_isIncludeInvalid = false;
	
	/**
	 * 
	 */
	public UNSDocumentCompletion() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] param = getParameter();
		for (int i = 0; i < param.length; i++)
		{
			String paramName = param[i].getParameterName();
			if (param[i].getParameter() == null)
				;
			else if (paramName.equals("AD_Table_ID"))
				m_AD_Table_ID = param[i].getParameterAsInt();
			else if (paramName.equals("AD_Column_ID"))
				m_AD_ColumnDate_ID = param[i].getParameterAsInt();
			else if (paramName.equals("AD_Org_ID"))
				m_AD_Org_ID = param[i].getParameterAsInt();
			else if (paramName.equals("DateFrom"))
				m_DateFrom = (Timestamp) param[i].getParameter();			
			else if (paramName.equals("DateTo"))
				m_DateTo = (Timestamp) param[i].getParameter();
			else if (paramName.equals("IgnoreError"))
				m_isIgnoreError = param[i].getParameter_ToAsBoolean();
			else if (paramName.equals("IncludeInProgress"))
				m_isIncludeInProgress = param[i].getParameter_ToAsBoolean();
			else if (paramName.equals("IncludeInvalid"))
				m_isIncludeInvalid = param[i].getParameter_ToAsBoolean();
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		String tableName = MTable.getTableName(getCtx(), m_AD_Table_ID);
		
		MColumn dateColumn = MColumn.get(getCtx(), m_AD_ColumnDate_ID);
		
		String dateColName = dateColumn.getColumnName();
		String dateFieldName = dateColumn.getName();
		
		String whereClause = dateColName + " BETWEEN ? AND ? AND DocStatus NOT IN (?, ?)";
		
		if (m_AD_Org_ID > 0)
			whereClause += " AND AD_Org_ID=" + m_AD_Org_ID;
		
		if (!m_isIncludeInProgress)
			whereClause += " AND DocStatus <> 'IP'";
		
		if (!m_isIncludeInvalid)
			whereClause += " AND DocStatus <> 'IN'";
		
		List<PO> poList = Query.get(getCtx(), UNSIntegrationModelFactory.EXTENSION_ID, 
									tableName, whereClause, get_TrxName())
				.setParameters(m_DateFrom, m_DateTo, DocAction.STATUS_Completed, DocAction.STATUS_Closed)
				.setOrderBy(dateColName + ", DocumentNo")
				.list();
		int count = 1;
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		log.setLevel(Level.INFO);
		
		String trxName = get_TrxName();
		int completedDoc = 0;
		int invalidDoc = 0;
		for (PO po : poList)
		{
			if (!(po instanceof DocAction))
				throw new AdempiereException("The selected table is not a transactional document.");
			
			DocAction da = (DocAction) po;
			String errMsg = null;
			
			try {
				po.set_TrxName(trxName);
				boolean succeed = da.processIt(DocAction.ACTION_Complete);
				
				if (!succeed)
				{
					errMsg = da.getProcessMsg();
				}
			}
			catch (Exception ex)
			{
				if (m_isIgnoreError)
					errMsg = ex.getMessage();
				else {
					ex.printStackTrace();
					throw new AdempiereException(ex.getMessage());
				}
			}
			
			if (errMsg == null) {
				//log.info(count + ". Completed : Document No: " + da.getDocumentNo() + 
				//		 dateFieldName + ": " + po.get_Value(dateColName));
				addLog(da.get_ID(), ts, null, count + ". Document No:" + da.getDocumentNo() + " "  
					+ dateFieldName + ": " + po.get_Value(dateColName) + " [Completed].");
				completedDoc++;
			}
			else {
				//log.info(count + ". Invalid : Document No: " + da.getDocumentNo() + 
				//		 dateFieldName + ": " + po.get_Value(dateColName) + ".\n Error Msg:" + errMsg);
				addLog(da.get_ID(), ts, null, 
					   count + ". Document No:" + da.getDocumentNo() + " " 
					   + dateFieldName + ": " + po.get_Value(dateColName) + " [Invalid].\n Error Msg:" + errMsg);
				invalidDoc++;
				
				if (!m_isIgnoreError)
					return errMsg;
			}
			count++;
			if (count % 300 == 0)
			{
				Trx trx = Trx.get(trxName, false);
				trx.commit();
				trx.close();
				
				trxName = Trx.createTrxName();
			}
		}
		return count + " documents processed; completed:" + completedDoc + "; invalid:" + invalidDoc;
	}

}
