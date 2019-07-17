/**
 * 
 */
package com.untacore.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInventory;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Util;
import com.unicore.model.MUNSImportSimpleTable;
import com.unicore.model.MUNSImportSimpleXLS;
import com.uns.model.process.SimpleImportXLS;

/**
 * @author Burhani Adam
 *
 */
public class PhysicalImporter extends SvrProcess {

	private String whereClause = null;
	/**
	 * 
	 */
	public PhysicalImporter() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		MInventory it = new MInventory(getCtx(), getRecord_ID(), get_TrxName());
		if(it.isComplete())
			throw new AdempiereException("This document has processed, can't running this process");
		if(Util.isEmpty(it.getFile_Directory(), true))
		{
			throw new AdempiereException("Untuk melakukan proses ini, anda harus mengisi file dengan direktori/path yang benar");
		}
		
		String sql = "SELECT UNS_ImportSimpleXLS_ID FROM UNS_ImportSimpleXLS WHERE Name = 'PhysicalInventory'";
		int id = DB.getSQLValue(get_TrxName(), sql);
		if(id <= 0)
			throw new AdempiereException("Failed when trying Simple Import XLS with PhysicalInventory name");
		
		MUNSImportSimpleXLS simple = new MUNSImportSimpleXLS(getCtx(), id, get_TrxName());
		simple.setFile_Directory(it.getFile_Directory());
		simple.saveEx();
		
		MUNSImportSimpleTable tables[] = simple.getLines(true);
		
		if(tables.length > 1)
			throw new AdempiereException("more than one lines in Import Simple XLS " + simple.getName());
		
		MUNSImportSimpleTable table = tables[0];
		whereClause = "Line = @A@ AND M_Locator_ID = @B@ AND M_Product_ID = @C@";
		table.setWhereClause(whereClause + " AND M_Inventory_ID = " + getRecord_ID());
		table.saveEx();
		
		SimpleImportXLS si = new SimpleImportXLS(getCtx(), simple, table, false, 0, 0, 0, getAD_Client_ID(), get_TrxName());;
		String success = null;
		try{
			 success = si.doIt();
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		
		return success;
	}
}
