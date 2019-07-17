/**
 * 
 */
package com.uns.model.process;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import com.uns.base.UNSAbstractModelFactory;
import com.uns.base.model.Query;
import com.uns.importer.AbstractDataImporter;
import com.uns.importer.ImporterValidation;
import com.uns.util.MessageBox;

import org.adempiere.base.IModelFactory;
import org.adempiere.base.Service;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.I_AD_Table;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Org;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import com.unicore.model.I_UNS_ImportSimpleColumn;
import com.unicore.model.I_UNS_ImportSimpleTable;
import com.unicore.model.MUNSImportSimpleTable;
import com.unicore.model.MUNSImportSimpleXLS;
import com.unicore.model.X_UNS_ImportSimpleColumn;

/**
 * @author AzHaidar
 *
 */
public class SimpleImportXLS extends SvrProcess 
{
	public final static String DONT_SAVE = "-1";
	
	private MUNSImportSimpleXLS m_ImportXLS = null;
	private MUNSImportSimpleTable m_ImportTable = null;
	private boolean m_isProcessAllTable = false;
	private Hashtable<String, PO> m_PORefMap = new Hashtable<String, PO>();
	private int m_startLineNo = 0;
	private int m_endLineNo = -1;
	private int	m_savePointTrx = -1;
	private int m_Client_ID = -1;

	private IProcessUI m_processMonitor;

	
	private String m_trxName1;
	private String m_trx4Target;
	private Properties m_ctx;
	
	public void setTrxName1(String trxName)
	{
		m_trxName1 = trxName;
	}
	
	
	@Override
	public String get_TrxName()
	{
		if(m_trxName1 != null)
			return m_trxName1;
		
		m_trxName1 = super.get_TrxName();
		return m_trxName1;
	}
	
	@Override
	public int getAD_Client_ID()
	{
		if(m_Client_ID > -1)
			return m_Client_ID;
		
		m_Client_ID = super.getAD_Client_ID();
		return m_Client_ID;
	}
	
	public void setCtx(Properties ctx)
	{
		m_ctx = ctx;
	}
	
	@Override
	public Properties getCtx() {
		if(null != m_ctx)
			return m_ctx;
		
		m_ctx = super.getCtx();
		return m_ctx;
	}
	
	/*
	 * Helper member variable to store the reference, so we can check if a column is used as table reference.
	 */
	private String m_tableColumnRef = "";

	/**
	 * 
	 */
	public SimpleImportXLS() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		m_trxName1 = get_TrxName();
		m_trx4Target = m_trxName1;//Trx.createTrxName();
		
		ProcessInfoParameter[] param = getParameter();
		for (int i=0; i<param.length; i++)
		{
			String paramName = param[i].getParameterName();
			if (paramName.equals("A_Processed")) {
				m_isProcessAllTable = param[i].getParameterAsBoolean();
				m_ImportTable = new MUNSImportSimpleTable(getCtx(), getRecord_ID(), m_trxName1);
			}
			else if (paramName.equals("StartNo"))
				m_startLineNo = param[i].getParameterAsInt();
			else if (paramName.equals("EndNo"))
				m_endLineNo = param[i].getParameterAsInt();
			else if (paramName.equals("SavePointTrx"))
				m_savePointTrx = param[i].getParameterAsInt();

		}
		
		if (m_ImportTable == null)
			m_ImportXLS = new MUNSImportSimpleXLS(getCtx(), getRecord_ID(), m_trxName1);
		else 
			//if (m_isProcessAllTable && m_ImportTable != null)
			m_ImportXLS = (MUNSImportSimpleXLS) m_ImportTable.getUNS_ImportSimpleXLS();
	}
	
	public SimpleImportXLS(Properties ctx, MUNSImportSimpleXLS ImportSimpleXLS, MUNSImportSimpleTable ImportTable
			, boolean isProcessAllTable, int startLineNo, int endLineNo, int savePointTrx, int AD_Client_ID, String trxName)
	{
		m_ctx =  ctx;
		m_ImportXLS = ImportSimpleXLS;
		m_ImportTable = ImportTable;
		m_isProcessAllTable = isProcessAllTable;
		m_Client_ID = AD_Client_ID;
		m_trxName1 = trxName;
		m_trx4Target = m_trxName1;
	}


	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	public String doIt() throws Exception 
	{
		//log.setLevel(Level.INFO);
		m_processMonitor = Env.getProcessUI(getCtx());
		
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(new File(m_ImportXLS.getFile_Directory()));
		}
		catch (Exception ex) {
			throw new AdempiereException("Error while opening file: " + ex.getMessage());
		}
		
		if (m_ImportTable != null && !m_isProcessAllTable)
		{
			Sheet sheet = wb.getSheet(m_ImportTable.getName());
			if (sheet == null)
				throw new AdempiereUserError("Worksheet " + m_ImportTable.getName() + " doesn't exist.");

			boolean isSucceed = importSimpleTable(wb, m_ImportTable);
			if (isSucceed)
				return "Completed.";
			else 
				return "Complete with errors.";
		}
		
		// We asked to process all tables configured in the m_ImportXLS.
		MUNSImportSimpleTable[] tableList = m_ImportXLS.getLines(false);
		
		// First verify the worksheets.
		for (MUNSImportSimpleTable table : tableList) 
		{
			Sheet sheet = wb.getSheet(table.getName());
			if (sheet == null)
				throw new AdempiereUserError("Worksheet " + table.getName() + " doesn't exist.");
		}

		for (MUNSImportSimpleTable table : tableList) 
		{
			m_ImportTable = table;
			boolean isSucceed = importSimpleTable(wb, table);
			
			if (table.getI_ErrorMsg() != null && !"".equals(table.getI_ErrorMsg()))
				m_ImportXLS.setI_ErrorMsg(m_ImportXLS.getI_ErrorMsg() + "\n" + table.getI_ErrorMsg());
			if (!isSucceed)
				return "Aborted with errors.";
		}
		if (m_ImportXLS.getI_ErrorMsg() != null && !"".equals(m_ImportXLS.getI_ErrorMsg()))
			m_ImportXLS.setI_ErrorMsg("Complete with Errors: " + m_ImportXLS.getI_ErrorMsg());
		m_ImportXLS.setI_IsImported(true);
		m_ImportXLS.saveEx();
		return "Import completed.";
	}
	
	/**
	 * 
	 * @param table
	 * @return
	 */
	public boolean importSimpleTable(Workbook wb, MUNSImportSimpleTable table)
	{
		m_ImportTable = table;
		Sheet sheet = wb.getSheet(table.getName());
		if(null == sheet)
			throw new AdempiereException("Can't found sheet. You must set your sheet name on your work book to " 
					+ table.getName());
		
		List<IModelFactory > factoryList = null;
		
		if (table.getExtensionHandler() != null && !"".equals(table.getExtensionHandler()))
		{
			factoryList = 
					Service.locator().list(IModelFactory.class, table.getExtensionHandler(), null).getServices();
			if (factoryList == null || factoryList.isEmpty()) {
				log.log(Level.SEVERE, "Undefined Extension Handler of : " + table.getExtensionHandler() 
						+ " for table:"+ table.getAD_Table().getTableName());
				return false;
			}
		}
		// If using Class Importer, then import the Worksheet from it.
		if (table.getImporterClass() != null && !"".equals(table.getImporterClass()))
		{
			AbstractDataImporter importer = null;
			if (factoryList != null) {
				for(IModelFactory factory : factoryList) {
					importer = ((UNSAbstractModelFactory) factory)
							.getImporter(table.getImporterClass(), sheet, m_trx4Target);
					if (importer != null)
						break;
				}
			}
			return importer.doImport();
		}
		
		ImporterValidation validator = null;
		if (table.getModelValidationClass() != null && !"".equals(table.getModelValidationClass()))
		{
			if (factoryList != null) {
				for(IModelFactory factory : factoryList) {
					validator = ((UNSAbstractModelFactory) factory)
							.getImporterValidation(table.getModelValidationClass(), getCtx(), sheet, m_trx4Target);
					if (validator != null)
						break;
				}
			}
			
			if (validator == null)
				throw new AdempiereException("Cannot find validator class of " + table.getModelValidationClass());
		}
		
		setTableColumnRef (table);
		
		X_UNS_ImportSimpleColumn[] columnList = null;
		I_UNS_ImportSimpleTable tableReference = table.getImportTableReference();
		if (table.getImportTableReference_ID() > 0)
			columnList = ((MUNSImportSimpleTable)tableReference).getLines(false);
		else
			columnList = table.getLines(false);
		
		if (columnList == null || columnList.length < 1)
			throw new AdempiereException("You have not defined any column definition yet.");
		/**
		for (X_UNS_ImportSimpleColumn column : columnList)
		{
			Hashtable<String,Object> columnAttr = new Hashtable<String,Object>();
			columnAttr.put(SIMPLE_COULUMN, column);
			columnAttr.put(AD_COULUMN, column.getAD_Column());
			columnAttr.put(COLUMN_TABLE_REFF, column.getAD_Table());
			columnAttr.put(COLUMN_REFF, column.getAD_Reference());
			
			m_columnAttrMap.put(column.getName(), columnAttr);
		}
		*/
		
		table.setI_ErrorMsg("");
		String validationMsg = null;
		
		// Go through the rows from row 1. row 0 is for titles.
		if (m_startLineNo <= 1)
			m_startLineNo = 2;
		else if (m_startLineNo >= sheet.getRows())
			m_startLineNo = sheet.getRows()-1;
		
		if (m_endLineNo <= 0 || m_endLineNo > sheet.getRows())
			m_endLineNo = sheet.getRows();
		
		//PO[] pos = new PO[sheet.getRows() - 1];
		int rowSize = m_endLineNo - m_startLineNo + 1;
		PO[] pos = new PO[rowSize];
		int ix = 0;
		
		int currentSavePoint = resetSavePointTrx(rowSize) + m_startLineNo;
		
		for (int row=m_startLineNo; row <= m_endLineNo; row++)
		{
			if (row == currentSavePoint)
			{
				commitTrx();
				currentSavePoint += m_savePointTrx;
				//log.info("\n========================\nTrx committed at row " + row + "\n========================\n");
				if (m_processMonitor != null)
					m_processMonitor.statusUpdate(
							"\n========================\nTrx committed at row " + row + "\n========================\n");

//				if (rowSize <= 2500)
//					if (m_processMonitor != null)
//						m_processMonitor.statusUpdate(
//								"\n========================\nTrx committed at row " + row + "\n========================\n");
					//addLog("\n========================\nTrx committed at row " + row + "\n========================\n");
			}
			
			//PO po = getPO(table, sheet.getCell(updateRefCol.getName()+(row+1)).getContents().trim());
			PO po = getPO(sheet, row);
			pos[ix++] = po;
			
			String msgCreateUpdate = (po.get_ID() > 0)? "Update" : "Create";
			String rowValues = "";
			int errMsgCountBy80 = 1;
			
			// Go through row's column and set it to the PO.
			int colCount = 0;
			Hashtable<String, Object> freeColVals = new Hashtable<String, Object>();
			
			//for (int col=0; col < columnList.length; col++)
			for (X_UNS_ImportSimpleColumn column : columnList)
			{
				//X_UNS_ImportSimpleColumn column = columnList[col];
				Object columnValue = getValueOf(sheet, column, row);
				//if (columnValue == null)
				//	continue;
				
				if (column.getAD_Column_ID() > 0 && (columnValue != null))
				{
					I_AD_Column ADColumn = column.getAD_Column();
					String columnName = ADColumn.getColumnName();
					po.set_ValueNoCheck(columnName, columnValue);
				}
				
				String cellName = column.getName() + (row);
				
				if (columnValue == null) 
				{
					Cell cellContent = sheet.getCell(cellName); // find by cell name.
					if (cellContent == null)
						cellContent = sheet.getCell(column.getColumnNo(), row);
					
					columnValue = cellContent.getContents();
				}
				
				freeColVals.put(column.getName(), columnValue);
				freeColVals.put("ACTIVE_ROW", row);
				
				if(column.getNameAlias() != null)
					freeColVals.put(column.getNameAlias(), columnValue);
				
				if (rowValues.length() > 80 * errMsgCountBy80)
				{
					rowValues += "\n";
					errMsgCountBy80++;
				}
				rowValues += (colCount == 0)? columnValue : "; " + columnValue;
				colCount++;
			}
			if (validator != null) {
				validator.setTrxName(m_trx4Target);
				validationMsg = validator.beforeSave(m_PORefMap, po, freeColVals, row);
			
				if (validationMsg != null && validationMsg.equalsIgnoreCase(DONT_SAVE))
				{
					String dontSaveMsg = 
							msgCreateUpdate + " sheet[" + table.getName() + "] row[" + (row) + "]: defined as dont need to save.";
					
					log.info(dontSaveMsg);
					if (rowSize <= 2500 && m_ImportXLS == null)
						addLog(dontSaveMsg);
					continue;
				}
			}
			
			//log.info(msgCreateUpdate + " sheet[" + table.getName() + "] row[" + (row) + "]:" + msgCreateUpdate + "d.");
			if (m_processMonitor != null)
				m_processMonitor.statusUpdate(
						msgCreateUpdate + " sheet[" + table.getName() + "] row[" + (row) + "]:" + msgCreateUpdate + "d.");
			
			if (validationMsg != null || !po.save()) 
			{
				//po.saveEx();
				if (validationMsg != null)
					table.setI_ErrorMsg(
							table.getI_ErrorMsg() + "\nBefore save validation error msg: " + validationMsg);
				Exception exLog = CLogger.retrieveException();
				String logMsg = "";
				if (exLog != null)
					logMsg = exLog.getMessage();
				
				if(null != table.getI_ErrorMsg() && !table.getI_ErrorMsg().isEmpty())
					logMsg = logMsg.concat("\n").concat(table.getI_ErrorMsg());
				
				if (rowSize <= 2500 && m_ImportXLS == null)
					addLog(msgCreateUpdate + " Failed for row " + (row));
				log.severe("Error on sheet[" + table.getName() + "] row[" + (row) + "] Values:" + rowValues);
				
				String errorMsgHack = "Error On Sheet: " + table.getName() + " - row [" + (row) + "] (" + logMsg + ");";
				table.setI_ErrorMsg(table.getI_ErrorMsg() + "\n" + errorMsgHack);
				
				String errorMsg = "Error On Sheet: " + table.getName() + " - row " + (row) + "\nRow Values: " + 
						rowValues + "\n" + logMsg;
				int isContinue = 
						MessageBox.showMsg(getCtx(), getProcessInfo(), 
								 errorMsg + "\nContinue it anyway?", 
								"Error", MessageBox.YESNO, MessageBox.ICONQUESTION);
				if (isContinue == MessageBox.RETURN_NO)
				{
					table.saveEx();
					return false;
				}
				else {
					int isBackOneRow = MessageBox.showMsg(getCtx(), getProcessInfo(), "Retry processing row " + (row) + "?", 
							"Retry?", MessageBox.YESNO, MessageBox.ICONINFORMATION);
					if (isBackOneRow == MessageBox.RETURN_YES) 
					{
						try {
							wb.close();
							wb = Workbook.getWorkbook(new File(m_ImportXLS.getFile_Directory()));
						}
						catch (Exception ex) {
							throw new AdempiereException("Error while opening file: " + ex.getMessage());
						}
						sheet = wb.getSheet(table.getName());
						row--;
					}
				}
			}
			else {
				if (rowSize <= 2500 && m_ImportXLS == null)
					addLog("Worksheet: " + table.getName() + " - Row [" + (row) + "] "+ msgCreateUpdate +"d.");

				if (validator != null) {
					validator.setTrxName(m_trx4Target);
					validationMsg = validator.afterSaveRow(m_PORefMap, po, freeColVals, row);
					if (validationMsg != null) {
						if (rowSize <= 15000)
							addLog("Error occured after executing model validation on saving a row: " + validationMsg);
						table.setI_ErrorMsg(
								table.getI_ErrorMsg() + "\nAfter save validation error msg: " + validationMsg);
					}
				}
			}
			
		}
		
		if (validator != null)
		{
			m_processMonitor.statusUpdate(
					"All row imported, running afterSaveAllRow validation ...");

			commitTrx();
			validator.setTrxName(m_trx4Target);
			validationMsg = validator.afterSaveAllRow(m_PORefMap, pos);
		
			m_processMonitor.statusUpdate(
					"... afterSaveAllRow validation DONE.");
			
			if (validationMsg != null)
				table.setI_ErrorMsg(
						table.getI_ErrorMsg() + "\nAfter saving all row validation error msg: " + validationMsg);
		}
		addLog("Completed. " + rowSize + " total number of rows were imported.");
		table.setI_IsImported(true);
		table.saveEx();

		return true;
	}

	/**
	 * Get the value of column in a row.
	 * @param sheet
	 * @param column
	 * @param row
	 * @return
	 */
	Object getValueOf (Sheet sheet, I_UNS_ImportSimpleColumn column, int row)
	{
		Object columnValue = null;
		
		I_AD_Column ADColumn = column.getAD_Column();
		String cellName = column.getName() + (row);
		Cell cellContent = sheet.getCell(cellName); // find by cell name.
		if (cellContent == null)
			cellContent = sheet.getCell(column.getColumnNo(), row);
		
		String cellValue = cellContent.getContents();
		
		columnValue = cellContent.getContents();
		if (cellValue == null || "".equals(cellValue) || cellContent.equals(CellType.EMPTY))
			columnValue = column.getDefaultValue();
		
		if (columnValue == null || "".equals(columnValue))
			return null;
		else if (cellValue != null && ("NULL".equalsIgnoreCase(cellValue.trim()) || "-".equals(cellValue.trim())))
			return null;
		
		I_AD_Reference columnRef = column.getAD_Reference();
		
		if (columnRef.getName().equalsIgnoreCase("String")
			|| columnRef.getName().equalsIgnoreCase("Text")
			|| columnRef.getName().equalsIgnoreCase("Text Long"))
		{
			
			int fieldLength = (column.getAD_Column_ID() > 0)? ADColumn.getFieldLength() : cellValue.length();
			int cellLength = cellValue.length();
			fieldLength = (fieldLength > 0)? fieldLength : cellLength;
			columnValue = (fieldLength >= cellLength) ? columnValue : cellValue.substring(0, fieldLength);
			if (isSetAsTableID(column) || column.isTrim())
				columnValue = ((String)columnValue).trim();
			//columnValue = ((String) columnValue).replace("'", "''");
		}
		if (columnRef.getName().equalsIgnoreCase("Table Direct") 
			|| columnRef.getName().equalsIgnoreCase("Table")
			|| columnRef.getName().equalsIgnoreCase("Search")
			|| columnRef.getName().equalsIgnoreCase("Account"))
		{
			int idReferer = 0;
			if (column.getColumnReferer_ID() == 0 && column.isUseWhereClause())
			{
				idReferer = getIDForColumnReferenceOf(column, sheet, row, ((String) columnValue).trim());
			}
			else {
				idReferer = getIDForColumnReferenceOf(column, ((String) columnValue).trim());
			}
			
			if (idReferer < 0)
				return null;
			else if (idReferer == 0 && !column.getAD_Table().getTableName().equals(X_AD_Org.Table_Name))
				return null;
			columnValue = idReferer;
		}
		else if (columnRef.getName().equalsIgnoreCase("List") && !column.isReferToListValue() && column.getAD_Column_ID() > 0) 
		{
			String sql = "SELECT value FROM AD_Ref_List WHERE AD_Reference_ID=? AND name=?";
			columnValue = DB.getSQLValueString(m_trxName1, 
											   sql, 
											   ADColumn.getAD_Reference_Value_ID(), 
											   cellValue);
		}
		else if (columnRef.getName().equalsIgnoreCase("Yes-No"))
		{
			columnValue = cellValue.equalsIgnoreCase("Y") 
					|| cellValue.equalsIgnoreCase("Yes") || cellValue.equalsIgnoreCase("T")?
					true : false;
		}
		else if (columnRef.getName().equalsIgnoreCase("Amount")
				|| columnRef.getName().equalsIgnoreCase("Number")
				|| columnRef.getName().equalsIgnoreCase("Quantity")
				|| columnRef.getName().equalsIgnoreCase("Costs+Prices")) 
		{
			if (cellValue != null && !"".equals(cellValue) && !"-".equals(cellValue) && !cellContent.equals(CellType.EMPTY)){
				try
				{
					columnValue = BigDecimal.valueOf(((NumberCell)cellContent).getValue());
				} catch (Exception ex) 
				{
					columnValue = BigDecimal.valueOf(Double.valueOf((String)columnValue));
				}
			} else if (columnValue != null && !"".equals((String)columnValue) && !"-".equals((String)columnValue))
				columnValue = BigDecimal.valueOf(Double.valueOf((String)columnValue));
			else
				columnValue = BigDecimal.ZERO;
		}
		else if ((columnRef.getName().equalsIgnoreCase("Time")
				  || columnRef.getName().equalsIgnoreCase("Date+Time")
				  || columnRef.getName().equalsIgnoreCase("Date"))
				 && columnValue != null && !"".equals(columnValue))
		{
			if (cellContent.getType().equals(CellType.DATE))
			{
				columnValue = new Timestamp(((DateCell) cellContent).getDate().getTime());
			}
			else {
				SimpleDateFormat dateFormat = new SimpleDateFormat(column.getDateFormat());
				
				try {
					columnValue = new Timestamp(dateFormat.parse((String) columnValue).getTime());
				}catch(Exception ex) {
					throw new AdempiereException ("Cannot parse date format of cell " + cellName);
				}
			}
		}
		/*
		else if (columnRef.getName().equalsIgnoreCase("Time")
				 && columnValue != null && !"".equals(columnValue))
		{
			columnValue = setCalendarTime(Calendar.getInstance(), ((String) columnValue));
		}
		else if (columnRef.getName().equalsIgnoreCase("Date+Time") 
				&& columnValue != null && !"".equals(columnValue))
		{
			cal.setTimeInMillis(((DateCell) cellContent).getDate().getTime());
			String[] dateParts = ((String) columnValue).split(" ");
			if (dateParts.length != 3)
				throw new AdempiereException("Error: It must be [yyyy-mm-dd mm:hh:ss AM/PM] for Date+Time format / [hh:mm:ss AM/PM] for Time only format");
			
			columnValue = setCalendarTime(cal, dateParts[1] + " " + dateParts[2]);
		}
		else if (columnRef.getName().equalsIgnoreCase("Date") 
				&& columnValue != null && !"".equals(columnValue)) 
		{
			Timestamp ts = new Timestamp(((DateCell) cellContent).getDate().getTime());
			columnValue = ts;
		}
		*/
		else if (columnRef.getName().equalsIgnoreCase("Integer")
				|| columnRef.getName().equalsIgnoreCase("ID")) {
			if (cellValue != null && !"-".equals(cellValue) && !cellContent.equals(CellType.EMPTY)){
				try {
					columnValue = (int) ((NumberCell)cellContent).getValue();
				}
				catch (Exception ex)
				{
					columnValue = Long.valueOf((String)columnValue);
				}
			}else if (columnValue != null && !"".equals((String)columnValue) && !"-".equals((String)columnValue))
				columnValue = Long.valueOf((String)columnValue);
			else
				columnValue = 0;
			//columnValue = Integer.valueOf((String) columnValue);
		}

		return columnValue;
	}
	
	/**
	 * Get New PO of table.
	 * @param table
	 * @return
	 */
	PO getPO(MUNSImportSimpleTable table, String valueToSearch)
	{
		PO po = null;
		int poID = getIDForColumnReferenceOf(table.getUNS_ImportSimpleColumn(), valueToSearch);
		if (poID > 0) 
		{
			String tableName = table.getAD_Table().getTableName();
			po = Query.get(getCtx(), 
						   table.getExtensionHandler(), 
						   tableName, 
						   getTableNameIDOf(tableName) + "=" + poID, m_trx4Target)
					.first();
			if (po != null)
				return po;
		}	
		
		/** UNS Modification on extension handling */
		List<IModelFactory> factoryList = null; 
		if ((table.getExtensionHandler() != null) && (!"".equals(table.getExtensionHandler())))
		{
			factoryList = Service.locator().list(IModelFactory.class, table.getExtensionHandler(), null).getServices();
			if (factoryList.isEmpty())
			{
				log.log(Level.SEVERE, "Undefined Extension Handler of : " + table.getExtensionHandler() 
						+ " for table:"+ table.getAD_Table().getTableName());
				return null;
			}
		}
		else 
		{
			factoryList = Service.locator().list(IModelFactory.class).getServices();
		}
		if (factoryList != null)
		{
			for(IModelFactory factory : factoryList)
			{
				po = factory.getPO(table.getAD_Table().getTableName(), 0, m_trx4Target);
				if (po != null)
				{
					break;
				}
			}
		}
		return po;
	}
	
	/**
	 * 
	 * @param tableName
	 * @param searchKey
	 * @param name
	 * @return
	 */
	int getIDByValueName(String tableName, String searchKey, String name, boolean isUseUpperCase)
	{
		if (searchKey == null && name == null)
			return -1;
		
		String sql = "SELECT " + tableName + "_ID FROM " + tableName + " WHERE ";
		if (searchKey != null) {
			sql += (isUseUpperCase)? "Upper(TRIM (Value))=Upper(TRIM('" + searchKey + "'))" : " TRIM(Value)=TRIM('" + searchKey +"')";
			if (name != null)
				sql += (isUseUpperCase)? "AND Upper(TRIM(Name))=Upper(TRIM('" + name +"'))" : "AND TRIM(Name)=TRIM('" + name +"')";
		}
		else if (name != null)
			sql += (isUseUpperCase)? "Upper(TRIM(Name))=Upper(TRIM('" + name +"'))" : "TRIM(Name)=TRIM('" + name +"')";
		
		return DB.getSQLValue(m_trx4Target, sql);
	}
	
	/**
	 * 
	 * @param cal
	 * @param timeStr
	 * @return
	 */
	Timestamp setCalendarTime (Calendar cal, String timeStr)
	{
		int AMPMIndex = timeStr.indexOf("AM");
		AMPMIndex = (AMPMIndex > -1)? AMPMIndex : timeStr.indexOf("PM");
		//if (AMPMIndex == -1)
		//	throw new AdempiereException("Error: It must be [yyyy-mm-dd mm:hh:ss AM/PM] for Date+Time format / [hh:mm:ss AM/PM] for Time only format");
		
		String[] timeSplit = timeStr.split(":");
		if (timeSplit.length < 3)
			throw new AdempiereException("Error: It must be [yyyy-mm-dd mm:hh:ss AM/PM] for Date+Time format / [hh:mm:ss AM/PM] for Time only format");
		
		int hh = Integer.valueOf(timeSplit[0]);
		int mm = Integer.valueOf(timeSplit[1]);
		int ss = Integer.valueOf(timeSplit[2].substring(0, 1));
		String AMPM = timeStr.substring(AMPMIndex, timeStr.length());
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, hh);
		cal.set(Calendar.MINUTE, mm);
		cal.set(Calendar.SECOND, ss);
		
		if (AMPMIndex > -1)
			cal.set(Calendar.AM_PM, AMPM.equals("AM")? Calendar.AM : Calendar.PM);
		
		return new Timestamp(cal.getTimeInMillis());
	}
	
	/**
	 * 
	 * @param importTable
	 * @param workSheet
	 * @param row
	 * @return
	 */
	PO getPO(Sheet workSheet, int row)
	{
		PO po = null;		
		String whereClause = m_ImportTable.getWhereClause();
		
		if (m_ImportTable.getUNS_ImportSimpleColumn_ID() > 0 && (whereClause == null || "".equals(whereClause)))
		{
			I_UNS_ImportSimpleColumn column = m_ImportTable.getUNS_ImportSimpleColumn();
			
			//Object columnValue = getValueOf(workSheet, column, row);
			int idValue = 0;
			if (column.isUseWhereClause() 
				&& column.getWhereClause() != null 
				&& !column.getWhereClause().isEmpty())
			{
				idValue = getIDForColumnReferenceOf(column, workSheet, row, 
						workSheet.getCell(column.getName()+(row)).getContents().trim());
			}
			else {
				idValue = getIDForColumnReferenceOf(column, 
						workSheet.getCell(column.getName()+(row)).getContents().trim());
			}
			
//			int idValue = getIDForColumnReferenceOf(column, 
//					workSheet.getCell(column.getName()+(row+1)).getContents().trim());
			if (idValue > 0) {
				String tableName = m_ImportTable.getAD_Table().getTableName();
				po = Query.get(getCtx(), 
							   m_ImportTable.getExtensionHandler(), 
							   tableName, 
							   "AD_Client_ID=" + getAD_Client_ID() + " AND "+ getTableNameIDOf(tableName) + "=" + idValue, 
							   m_trx4Target)
						.firstOnly();
				if (po != null)
					return po;
			}
		}
		if (whereClause != null && !"".equals(whereClause))
		{
			String replacedClause = parseWhereClause(workSheet, row, whereClause);
			replacedClause = "AD_Client_ID=" + getAD_Client_ID() + " AND "+ replacedClause;
			// We already get the full whereClause, now try to get the model class.
			po = Query.get(getCtx(), 
						   m_ImportTable.getExtensionHandler(), 
						   m_ImportTable.getAD_Table().getTableName(), 
						   replacedClause, m_trx4Target)
					.firstOnly();
		}
		if (po != null)
			return po;
		/** UNS Modification on extension handling */
		List<IModelFactory> factoryList = null; 
		if ((m_ImportTable.getExtensionHandler() != null) && (!"".equals(m_ImportTable.getExtensionHandler())))
		{
			factoryList = Service.locator().list(IModelFactory.class, m_ImportTable.getExtensionHandler(), null)
					.getServices();
			if (factoryList.isEmpty())
			{
				log.log(Level.SEVERE, "Undefined Extension Handler of : " + m_ImportTable.getExtensionHandler() 
						+ " for table:"+ m_ImportTable.getAD_Table().getTableName());
				return null;
			}
		}
		else 
		{
			factoryList = Service.locator().list(IModelFactory.class).getServices();
		}
		if (factoryList != null)
		{
			for(IModelFactory factory : factoryList)
			{
				po = factory.getPO(m_ImportTable.getAD_Table().getTableName(), 0, m_trx4Target);
				if (po != null)
				{
					break;
				}
			}
		}
		return po;
	}
	
	String parseWhereClause(Sheet workSheet, int row, String whereClause)
	{
		String[] clauseToken = whereClause.split("@");
		String replacedClause = clauseToken[0]; // the first token must be free char if for "@xxx@", 
											    // first index of clauseToken will be "".
		for (int i=1; i < clauseToken.length; i++)
		{
			I_UNS_ImportSimpleColumn column = m_ImportTable.getImportColumnOf(clauseToken[i]);
			if (column == null)
				throw new AdempiereException("Error in where clause: Cannot find the Import Simple Column for \\@" +
						clauseToken[i] + "\\@");
			Object columnClauseValue = getValueOf(workSheet, column, row);
			
			//if (columnClauseValue != null) {
			String colRef = column.getAD_Reference().getName();
			if (colRef.equals("String") || colRef.equals("Text") || colRef.equals("Printer Name")
					|| colRef.equals("Text Long") || colRef.equals("Memo"))
					columnClauseValue = ((String)columnClauseValue).replace("'", "''");
			
			if (colRef.equals("String") || colRef.equals("Text") || colRef.equals("Printer Name")
				|| colRef.equals("Text Long") || colRef.equals("Memo") 
				|| colRef.equals("Time") || colRef.equals("Date")
				|| colRef.equals("Date+Time") || colRef.equals("URL")
				|| colRef.equals("FileName") || colRef.equals("FilePath"))
				replacedClause += (columnClauseValue != null)? columnClauseValue : "";
			else
				replacedClause += (columnClauseValue != null)? columnClauseValue : "null";
			
			i++; // just jump to next token, as it should get un-replaced string.
			if (i < clauseToken.length)
				replacedClause += clauseToken[i];
		}
		return replacedClause;
	}

	/**
	 * This is to get an ID for the column using whereClause mechanism.
	 * 
	 * @param column
	 * @param workSheet
	 * @param row
	 * @param columnValue
	 * @return
	 */
	int getIDForColumnReferenceOf(I_UNS_ImportSimpleColumn column, Sheet workSheet, int row, String columnValue)
	{
		int referer_ID = -1;
		
		I_AD_Table tableRef = column.getAD_Table();
		String whereClause = column.getWhereClause();
		
		String validColVal = columnValue.replaceAll("'", "''");
		whereClause = whereClause.replaceAll("@" + column.getName() + "@", validColVal); 
		
		String sql = "SELECT " + tableRef.getTableName() + "_ID FROM " +tableRef.getTableName() + 
				" WHERE " + parseWhereClause(workSheet, row, whereClause);
				
		referer_ID = DB.getSQLValue(m_trx4Target, sql);
		
		return referer_ID;
	}
	
	/**
	 * 
	 * @param column
	 * @param columnValue
	 * @return
	 */
	int getIDForColumnReferenceOf(I_UNS_ImportSimpleColumn column, String columnValue)
	{
		columnValue = columnValue.replace("'", "''");
		I_AD_Reference columnRef = column.getAD_Reference();
		int referer_ID = -1;
		if (columnRef.getName().equalsIgnoreCase("Table Direct") 
			|| columnRef.getName().equalsIgnoreCase("Table")
			|| columnRef.getName().equalsIgnoreCase("Search"))
		{
			I_AD_Table tableRef = column.getAD_Table();
			if (column.getColumnReferer_ID() > 0)
			{
				String sql = "SELECT " + tableRef.getTableName() + "_ID FROM " +tableRef.getTableName() + 
						" WHERE " + getWhereClauseOfColumnCase(column, columnValue);
						
					referer_ID = DB.getSQLValue(m_trx4Target, sql);
					
					return referer_ID;
			}
			
			if (column.getValueIndex() == -1 && column.getNameIndex() == -1)
				throw new AdempiereUserError(
						"Error on Column Definition of " + 
						column.getUNS_ImportSimpleTable().getAD_Table().getTableName() + "." + 
						column.getAD_Column().getColumnName() + 
						".\nBoth index of Value and Name cannot be less then zero.");
				
			String[] valueSplit = 
					(column.getSeparatorChar() == null || "".equals(column.getSeparatorChar())) ?
					new String[]{(String) columnValue} : 
					((String) columnValue).split(column.getSeparatorChar());
			
			String searchKeyRef = null;
			String nameRef = null;
			
			if (column.getValueIndex() != -1) 
			{
				int valueIndex = !column.isReversal()? column.getValueIndex()  
						: valueSplit.length-column.getValueIndex()-1;
				searchKeyRef = valueSplit[valueIndex].trim();
			}
			if (column.getNameIndex() != -1) {
				int nameIndex = !column.isReversal()? column.getNameIndex()  
						: valueSplit.length-column.getNameIndex()-1;
				nameRef = valueSplit[nameIndex].trim();
			}
			referer_ID = getIDByValueName(
					column.getAD_Table().getTableName(), searchKeyRef, nameRef, column.isUppercaseCharacter());
		}
		else if (columnRef.getName().equalsIgnoreCase("Account")) 
		{
			String sql = "SELECT C_ValidCombination_ID FROM C_ValidCombination WHERE Combination=?";
			referer_ID = DB.getSQLValue(m_trx4Target, sql, columnValue);
		}
		else //if (columnRef.getName().equalsIgnoreCase("Account")) 
		{
			// It must and should only come from getPO.
			I_AD_Table table = column.getUNS_ImportSimpleTable().getAD_Table();
			String sql = "SELECT " + table.getTableName() + "_ID FROM " + table.getTableName() + 
					" WHERE " + getWhereClauseOfColumnCase(column, columnValue);
			referer_ID = DB.getSQLValue(m_trx4Target, sql);
		}
		return referer_ID;
	}

	/**
	 * 
	 * @param tableName
	 * @return
	 */
	String getTableNameIDOf (String tableName)
	{
		String tableNameID = tableName + "_ID";
		if ("M_Product_Category_Acct".equalsIgnoreCase(tableName)) {
			tableNameID = "M_Product_Category_ID";
		}
		else if ("M_Product_BOM".equalsIgnoreCase(tableName)) {
			tableNameID = "M_Product_ID";
		}
		else if ("UNS_Product_Division".equalsIgnoreCase(tableName)) {
			tableNameID = "M_Product_ID";
		}
		return tableNameID;
	}
	
	/**
	 * 
	 * @param column
	 * @param columnValue
	 * @return
	 */
	String getWhereClauseOfColumnCase(I_UNS_ImportSimpleColumn column, String columnValue)
	{
		String columnNameToSearch = column.getAD_Column().getColumnName();
		if (column.getColumnReferer_ID() > 0)
			columnNameToSearch = column.getColumnReferer().getColumnName();
		
		String whereClause = "";
		
		columnValue = column.isUppercaseCharacter()? columnValue.trim().toUpperCase() : columnValue;
		
		if (column.isUseLikeClause())
		{
			whereClause = column.isUppercaseCharacter()? 
				"Upper(TRIM(" + columnNameToSearch + ")) LIKE '%" + columnValue + "%'" 
				: "TRIM(" + columnNameToSearch + ") LIKE '%" + columnValue + "%'";
		}
		else {
			whereClause = column.isUppercaseCharacter()? 
					"Upper(TRIM(" + columnNameToSearch + ")) = '" + columnValue + "'" 
					: "TRIM(" + columnNameToSearch + ") = '" + columnValue + "'";
		}
		
		return whereClause;
	}
	
	/**
	 * 
	 * @param table
	 */
	void setTableColumnRef(I_UNS_ImportSimpleTable table)
	{
		m_tableColumnRef = table.getWhereClause();
		if (m_tableColumnRef == null || "".equals(m_tableColumnRef) || "-".equals(m_tableColumnRef))
			m_tableColumnRef = "@" + table.getUNS_ImportSimpleColumn().getName() + "@";
	}
	
	/**
	 * 
	 * @param column
	 * @return
	 */
	boolean isSetAsTableID(I_UNS_ImportSimpleColumn column)
	{
		return m_tableColumnRef.indexOf("@" + column.getName() + "@") != -1;
	}
	
	/**
	 * 
	 * @param rowSize
	 * @return
	 */
	protected int resetSavePointTrx(int rowSize)
	{
		if (m_savePointTrx == -1)
		{
			if (rowSize > 15000)
				m_savePointTrx = 500;
			else
				m_savePointTrx = 150;
		}
		
		return m_savePointTrx;
	}
	
	
	/**
	 * 
	 */
	private void commitTrx()
	{
		Trx trx = Trx.get(m_trx4Target, false);
		
		try {
			trx.commit(true);
			//trx.close();
			//DB.closeTarget();
//			trx.close();
//			m_trx4Target = Trx.createTrxName();
//			trx = Trx.get(m_trx4Target, true);
//			m_trx4Target = trx.getTrxName();
//			trx.start();
			//Trx.startTrxMonitor();
			//setTrxName(Trx.createTrxName());
		}
		catch (SQLException ex)
		{
			throw new AdempiereException(ex.getMessage(), ex);
		}
	}
}
/*
String cellName = column.getName() + (row+1);
Cell cellContent = sheet.getCell(cellName); // find by cell name.
if (cellContent == null)
	cellContent = sheet.getCell(column.getColumnNo(), row);

String cellValue = cellContent.getContents();

Object columnValue = cellContent.getContents();
if (cellValue == null || "".equals(cellValue))
	columnValue = column.getDefaultValue();

if (columnValue == null || cellValue.isEmpty())
	continue;

I_AD_Reference columnRef = column.getAD_Reference();

if (columnRef.getName().equalsIgnoreCase("Table Direct") 
	|| columnRef.getName().equalsIgnoreCase("Table")
	|| columnRef.getName().equalsIgnoreCase("Search")
	|| columnRef.getName().equalsIgnoreCase("Account"))
{
	int idReferer = getIDForColumnReferenceOf(column, ((String) columnValue).trim());
	if (idReferer <= 0)
		continue;
	columnValue = idReferer;
}
else if (columnRef.getName().equalsIgnoreCase("List") && !column.isReferToListValue()) 
{
	String sql = "SELECT value FROM AD_Ref_List WHERE AD_Reference_ID=? AND name=?";
	columnValue = DB.getSQLValueString(m_trxName, 
									   sql, 
									   ADColumn.getAD_Reference_Value_ID(), 
									   cellValue);
}
else if (columnRef.getName().equalsIgnoreCase("Yes-No")) 
{
	columnValue = cellValue.equalsIgnoreCase("Y") || cellValue.equalsIgnoreCase("Yes")?
			true : false;
}
else if (columnRef.getName().equalsIgnoreCase("Amount")
		|| columnRef.getName().equalsIgnoreCase("Number")
		|| columnRef.getName().equalsIgnoreCase("Quantity")
		|| columnRef.getName().equalsIgnoreCase("Costs+Prices")) 
{
	if (cellValue != null && !"".equals(cellValue) && !"-".equals(cellValue))
		columnValue = BigDecimal.valueOf(((NumberCell)cellContent).getValue());
	else
		columnValue = BigDecimal.ZERO;
}
else if (columnRef.getName().equalsIgnoreCase("Time")) 
{
	if (columnValue == null || "".equals(columnValue))
		continue;
	
	columnValue = setCalendarTime(Calendar.getInstance(), ((String) columnValue));
}
else if (columnRef.getName().equalsIgnoreCase("Date+Time")) 
{
	if (columnValue == null || "".equals(columnValue))
		continue;
	
	Calendar cal = Calendar.getInstance();
	cal.setTimeInMillis(((DateCell) cellContent).getDate().getTime());
	String[] dateParts = ((String) columnValue).split(" ");
	if (dateParts.length != 3)
		throw new AdempiereException("Error: It must be [yyyy-mm-dd mm:hh:ss AM/PM] for Date+Time format / [hh:mm:ss AM/PM] for Time only format");
	
	columnValue = setCalendarTime(cal, dateParts[1] + " " + dateParts[2]);
}
else if (columnRef.getName().equalsIgnoreCase("Date")) {
	Timestamp ts = new Timestamp(((DateCell) cellContent).getDate().getTime());
	columnValue = ts;
}
else if (columnRef.getName().equalsIgnoreCase("Integer")
		|| columnRef.getName().equalsIgnoreCase("ID")) {
	columnValue = Integer.valueOf(cellValue);
}
*/
