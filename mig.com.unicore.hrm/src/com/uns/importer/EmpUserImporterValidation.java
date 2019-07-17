package com.uns.importer;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Properties;

import jxl.Sheet;
import com.uns.model.MUNSEmployee;

import org.compiere.model.MUser;
import org.compiere.model.PO;

public class EmpUserImporterValidation implements ImporterValidation {

	protected Properties 	m_ctx 				= null;
	protected String		m_trxName 			= null;
	protected Sheet			m_sheet				= null;
	protected Hashtable<String, PO> m_PORefMap	= new Hashtable<String, PO>();
	protected final String	PSWD_DEFAULT = "Default";
	
	/**
	 * 
	 */
	public EmpUserImporterValidation(Properties ctx, Sheet sheet, String trxName) {
		m_ctx = ctx;
		m_trxName = trxName;
		m_sheet = sheet;
	}
	
	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
	}
	
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) {
		MUser user = (MUser)po;
		
		if (user==null)
			return null;
		
		MUNSEmployee employee = MUNSEmployee.get(m_ctx, user.getUNS_Employee_ID());
		
		if (employee==null || user.getUNS_Employee_ID()==0)
			return "Error not found Employee, NIK : "+user.getValue();
				
		user.setName(employee.getValue());
		user.setBirthday(employee.getDateOfBirth());
		user.setC_Job_ID(employee.getC_Job_ID());
		user.setC_BPartner_ID(employee.getC_BPartner_ID());
		user.setDescription(employee.getName());
		
		if (user.getPassword().equals(PSWD_DEFAULT)){
			String tl = new SimpleDateFormat("ddMMyyyy").format(employee.getDateOfBirth());
			user.setPassword(employee.getValue() + tl);
		} else {
			user.setPassword((String) user.get_ValueOld(MUser.COLUMNNAME_Password));
		}
		
		return null;
	}

	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) {
		return null;
	}

	@Override
	public String afterSaveAllRow(Hashtable<String, PO> poRefMap, PO[] pos) {

		return null;
	}

	@Override
	public Hashtable<String, PO> getPOReferenceMap() {
		
		return null;
	}

}
