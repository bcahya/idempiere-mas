/**
 * 
 */
package org.uns.matica.form.listener;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;

/**
 * @author Haryadi
 *
 */
public interface UNS_I_TableListener extends UNS_I_Listener 
{

	/**
	 * One of table's cell has been changed.
	 * 
	 * @param e
	 */
	public void tableChanged(TableModelEvent e);
	
	/**
	 * 
	 * @param tableName
	 * @param table
	 */
	public void addTable(String tableName, JTable table);
	
	/**
	 * 
	 * @param tableName
	 * @param row
	 * @param column
	 * @return
	 */
	public Object getValueOf(String tableName, int row, int column);
	
	/**
	 * Get value of a table for a row with column name.
	 * 
	 * @param tableName
	 * @param row
	 * @param columnName columnName here usually is field's name not the field's column name.
	 * @return
	 */
	public Object getValueOf(String tableName, int row, String columnName);
	
	/**
	 * 
	 * @param tableName
	 * @return
	 */
	public Vector<Vector<Object>> getAllValueOf (String tableName);

	/**
	 * 
	 * @param tableName
	 * @param newValue
	 * @param row
	 * @param col
	 */
	public void setValueOf(String tableName, Object newValue, int row, int col);

	/**
	 * 
	 * @param tableName
	 * @param newValue
	 * @param row
	 * @param columnName
	 */
	public void setValueOf(String tableName, Object newValue, int row, String columnName);
	
	/**
	 * 
	 * @param tableName
	 * @return
	 */
	public JTable getTableOf(String tableName);
	
	/**
	 * Get the number of column of a table.
	 * @param tableName
	 * @return -1 if the tableName does not exist.
	 */
	public int getColumnCountOf(String tableName);
	
	/**
	 * Get the number of row of a table.
	 * @param tableName
	 * @return -1 if the tableName does not exist.
	 */
	public int getRowCountOf(String tableName);
	
	/**
	 * Add a new row to the tableName.
	 * @param tableName
	 * @param rowData
	 */
	public void addRow(String tableName, Vector<Object> rowData);

	/**
	 * Add a new row to the tableName.
	 * @param tableName
	 * @param rowData
	 */
	public void addRow(String tableName, Object[] rowData);

	/**
	 * Remove a row at the given index.
	 * @param tableName
	 * @param row
	 */
	public void removeRow(String tableName, int row);

	/**
	 * Replace the table data with the given new rows of vector data.
	 * 
	 * @param tableName
	 * @param dataVector
	 * @param columnIdentifiers the name of columns.
	 */
	public void replaceData(String tableName, Vector<Vector<Object>> dataVector, Vector<String> columnIdentifiers);
	
	/**
	 * Replace the table data with the given new rows of array of data (object).
	 * 
	 * @param tableName
	 * @param dataArray
	 * @param columnIdentifiers the name of columns.
	 */
	public void replaceData(String tableName, Object[][] dataArray, Object[] columnIdentifiers);
	
	/**
	 * The table is selected by user.
	 * 
	 * @param tableName
	 */
	public void selectionChanged(ListSelectionEvent e);
}
