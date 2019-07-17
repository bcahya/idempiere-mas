/**
 * 
 */
package com.uns.model.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.unicore.model.MUNSImportSimpleTable;
import com.unicore.model.X_UNS_ImportSimpleColumn;

/**
 * @author AzHaidar
 *
 */
public class CopyImportSimpleColumns extends SvrProcess {

	MUNSImportSimpleTable m_TableFrom = null;
	MUNSImportSimpleTable m_TableTo = null;
	/**
	 * 
	 */
	public CopyImportSimpleColumns() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] param = getParameter();
		for (int i=0; i<param.length; i++)
		{
			String paramName = param[i].getParameterName();
			if (paramName.equals("ImportSimpleTable_ID")) {
				//m_isProcessAllTable = param[i].getParameterAsBoolean();
				m_TableFrom = new MUNSImportSimpleTable(getCtx(), param[i].getParameterAsInt(), get_TrxName());
			}
		}
		m_TableTo = new MUNSImportSimpleTable(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		String sql = "DELETE FROM UNS_ImportSimpleColumn WHERE UNS_ImportSimpleTable_ID=" + m_TableTo.get_ID();
		DB.executeUpdate(sql, get_TrxName());
		
		for (X_UNS_ImportSimpleColumn columnFrom : m_TableFrom.getLines(false))
		{
			X_UNS_ImportSimpleColumn copiedColumn = new X_UNS_ImportSimpleColumn(getCtx(), 0, get_TrxName());
			
			copiedColumn.setName(columnFrom.getName());
			copiedColumn.setNameAlias(columnFrom.getNameAlias());
			copiedColumn.setColumnNo(columnFrom.getColumnNo());
			copiedColumn.setAD_Column_ID(columnFrom.getAD_Column_ID());
			copiedColumn.setAD_Reference_ID(columnFrom.getAD_Reference_ID());
			copiedColumn.setAD_Table_ID(columnFrom.getAD_Table_ID());
			copiedColumn.setIsReferToListValue(columnFrom.isReferToListValue());
			copiedColumn.setColumnReferer_ID(columnFrom.getColumnReferer_ID());
			copiedColumn.setUppercaseCharacter(columnFrom.isUppercaseCharacter());
			copiedColumn.setValueIndex(columnFrom.getValueIndex());
			copiedColumn.setNameIndex(columnFrom.getNameIndex());
			copiedColumn.setIndexCount(columnFrom.getIndexCount());
			copiedColumn.setDefaultValue(columnFrom.getDefaultValue());
			copiedColumn.setDescription(columnFrom.getDescription());
			copiedColumn.setIsReversal(columnFrom.isReversal());
			copiedColumn.setSeparatorChar(columnFrom.getSeparatorChar());
			copiedColumn.setUNS_ImportSimpleTable_ID(m_TableTo.get_ID());
			copiedColumn.setTrim(columnFrom.isTrim());
			copiedColumn.setUseLikeClause(columnFrom.isUseLikeClause());
			copiedColumn.setUseWhereClause(columnFrom.isUseWhereClause());
			copiedColumn.setWhereClause(columnFrom.getWhereClause());
			if (!copiedColumn.save())
				throw new AdempiereException("Failed copying column [" + columnFrom.getName() + "] - ");
		}
		
		return "Completed.";
	}

}
