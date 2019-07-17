/**
 * 
 */
package org.uns.matica.form.listener;

import java.util.Vector;

import org.matica.form.listener.MAT_I_Listener;

/**
 * @author Haryadi
 *
 */
public abstract class UNS_I_TableLogic extends UNS_I_BusinessLogic 
{
	/*
	 * Member variable to hold the row and column number for this Table Form.
	 */
	protected int m_rowCount = 0;
	protected int m_columnCount = 0;
	protected UNS_I_TableListener m_tblListener = null;
	
	public abstract int getRowCount();
	
	public abstract int getColumnCount();
	
	//protected 
	
	//public abstract Object getValueAt(int row, int column);
	
	/**
	 * Load and get all values of a jtable of jTableName (Using name of Grid Table type Container).
	 * @param jTableName
	 * @return
	 */
	public abstract Vector<Vector<Object>> loadValuesOf(String jTableName);
	
	/**
	 * The table of tableName has changed at row and column index.
	 * 
	 * @param tableName
	 * @param row
	 * @param column
	 * @param columnName
	 */
	public abstract void tableChange(String tableName, int row, int column, String columnName);
	
	/**
	 * 
	 * @param tableName
	 * @param row the index of row currently selected (leade selection index)
	 * @param rows the sorted (ascending) arrays of currently selected rows index
	 */
	public abstract void onRowSelection(String tableName, int row, int[] rows);

	@Override
	public void setListenerOwner(MAT_I_Listener lst)
	{
		this.m_lstowner = lst;
		this.m_tblListener = (UNS_I_TableListener) lst;
	}
}
