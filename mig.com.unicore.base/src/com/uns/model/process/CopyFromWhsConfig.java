/**
 * 
 */
package com.uns.model.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSWarehouseConfig;
import com.uns.model.MUNSWarehouseConfigLine;

/**
 * @author Burhani Adam
 *
 */
public class CopyFromWhsConfig extends SvrProcess {

	/**
	 * 
	 */
	public CopyFromWhsConfig() {
		// TODO Auto-generated constructor stub
	}
	
	private int m_WhsConfig_ID = 0;
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter param : params)
		{	
			if (param.getParameterName().equals(
					"UNS_WarehouseConfig_ID")) {
				m_WhsConfig_ID = param.getParameterAsInt();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		MUNSWarehouseConfig from = new MUNSWarehouseConfig(getCtx(), m_WhsConfig_ID, get_TrxName());
		MUNSWarehouseConfig to = new MUNSWarehouseConfig(getCtx(), getRecord_ID(), get_TrxName());
		int count = 0;
		for(MUNSWarehouseConfigLine line : from.getLines())
		{
			if(MUNSWarehouseConfigLine.getByWhsDocType(getCtx(), to.getM_Warehouse_ID(),
					line.getC_DocType_ID(), get_TrxName()) != null)
			continue;
			
			MUNSWarehouseConfigLine newLine = new MUNSWarehouseConfigLine(getCtx(), 0, get_TrxName());
			newLine.setM_Warehouse_ID(to.getM_Warehouse_ID());
			newLine.setAD_Org_ID(to.getAD_Org_ID());
			newLine.setC_DocType_ID(line.getC_DocType_ID());
			newLine.setIsActive(line.isActive());
			newLine.setDescription(line.getDescription());
			newLine.saveEx();
			count = count + 1;
		}
		
		return "Success. #" + count + " created";
	}
}