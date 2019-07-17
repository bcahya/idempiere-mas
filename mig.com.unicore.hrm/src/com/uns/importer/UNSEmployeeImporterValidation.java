/**
 * 
 */
package com.uns.importer;

import java.util.Hashtable;
import java.util.Properties;

import jxl.Sheet;
import com.uns.model.MUNSEmployee;
import com.uns.model.process.SimpleImportXLS;

import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Util;

/**
 * @author ALBURHANY
 *
 */
public class UNSEmployeeImporterValidation implements ImporterValidation {

	protected Properties 	m_ctx = null;
	protected String		m_trxName = null;
	protected Sheet			m_sheet	= null;
	protected Hashtable<String, PO> m_PORefMap = null;
	
	static final String COL_Jon_Position = "JobPosition";
	static final String	COL_ForceUpdate	= "ForceUpdate";
	
	/**
	 * 
	 */
	public UNSEmployeeImporterValidation(Properties ctx, Sheet sheet, String trxName) {
		
		m_ctx = ctx;
		m_trxName = trxName;
		m_sheet = sheet;
	}

	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow)
	{		
		boolean isForceUpdate = (Boolean) freeColVals.get(COL_ForceUpdate);
		MUNSEmployee emp = (MUNSEmployee) po;
		
		if (emp.get_ID() > 0 && !isForceUpdate)
			return SimpleImportXLS.DONT_SAVE;
		
		if (Util.isEmpty(emp.getNickName(), true)) 
		{
			String name = emp.getName();
			
			String[] nameParts = name.split(" ");	
			emp.setNickName(nameParts[0]);
		}
		
		return null;
	}

	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) 
	{
		MUNSEmployee emp = (MUNSEmployee)po;
		
		MUser user = new Query(m_ctx, MUser.Table_Name, "UNS_Employee_ID=? OR UPPER(Value)=? OR UPPER(Name)=?", m_trxName)
			.setParameters(emp.get_ID(), emp.getValue(), emp.getValue()).first();
		
		String position = emp.getC_Job().getName();
		
		if (user != null) {
			user.set_ValueNoCheck(MUser.COLUMNNAME_UNS_Employee_ID, emp.get_ID());
			user.saveEx();
		}
		else  
		{
			user = new MUser(m_ctx, 0, m_trxName);
			user.setName(emp.getValue());
			user.setValue(emp.getValue());
			user.setUNS_Employee_ID(emp.get_ID());
			user.setRealName(emp.getName());
			user.setPassword(emp.getValue());
			
			String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE AD_Org_ID=? AND UPPER(Name)=?";
			int bp_ID = DB.getSQLValueEx(m_trxName, sql, emp.getAD_Org_ID(), position.toUpperCase());
			
			user.setC_BPartner_ID(bp_ID);
			if (position.equalsIgnoreCase("Sales") || position.equalsIgnoreCase("Kolektor"))
				user.setIsUserLogin(true);
			
			user.saveEx();
		}
		
		return null;
	}

	@Override
	public String afterSaveAllRow(Hashtable<String, PO> poRefMap, PO[] pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hashtable<String, PO> getPOReferenceMap() {
		return m_PORefMap;
	}

	@Override
	public void setTrxName(String trxName) {
		// TODO Auto-generated method stub
		
	}

}
