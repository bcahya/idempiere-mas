package com.uns.importer;

import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Properties;

import jxl.Sheet;
import com.uns.importer.ImporterValidation;
import com.uns.model.MUNSContractRecommendation;
import com.uns.model.MUNSEmployee;
import com.uns.model.X_UNS_Contract_Recommendation;

import org.compiere.model.PO;
import org.compiere.util.DB;

public class Employee2ImporterValidation implements ImporterValidation {

	protected Properties 	m_ctx 				= null;
	protected String		m_trxName 			= null;
	protected Sheet			m_sheet				= null;
	protected Hashtable<String, PO> m_PORefMap	= new Hashtable<String, PO>();
	protected int m_recordCount = 0;

	
	public Employee2ImporterValidation() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
		
	}
	
	public Employee2ImporterValidation(Properties ctx, Sheet sheet, String trxName)
	{
		this.m_ctx = ctx;
		this.m_sheet = sheet;
		this.m_trxName = trxName;
	}
	
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) {

		
		
		return null;
	}

	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) {
		
		MUNSEmployee emp = (MUNSEmployee) po;
		String sql = 
				"SELECT uns_contract_recommendation_id FROM uns_contract_recommendation "
				+ "WHERE NewNik = ?";
	int cr = DB.getSQLValueEx(m_trxName, sql, emp.getValue().toString());
	if(cr <= -1 ){
		X_UNS_Contract_Recommendation conRegist = new X_UNS_Contract_Recommendation(m_ctx, 0, m_trxName);
		conRegist.setAD_Org_ID(emp.getAD_Org_ID());
		conRegist.setNewDept_ID(emp.getAD_Org_ID());
		conRegist.setNewNIK(emp.getValue());
		conRegist.setUNS_Employee_ID(emp.getUNS_Employee_ID());
		conRegist.setNewPayrollLevel(MUNSEmployee.PAYROLLLEVEL_Level1);
		conRegist.setNextPayrollTerm(MUNSEmployee.PAYROLLTERM_Monthly);
		conRegist.setEmploymentType(emp.getEmploymentType());
		conRegist.setNextContractType("CN1");
		conRegist.setNewSectionOfDept_ID(emp.getC_BPartner_ID());
		conRegist.setNewJob_ID(emp.getC_Job_ID());
		conRegist.setNewGender(emp.getGender());
		conRegist.setNewShift(emp.getShift());
		conRegist.setDateContractStart(new Timestamp(System.currentTimeMillis()));
		conRegist.setDateContractEnd(new Timestamp(System.currentTimeMillis()));
		conRegist.setProcessed(true);
		conRegist.setDocAction(MUNSContractRecommendation.DOCACTION_Close);
		conRegist.setDocStatus(MUNSContractRecommendation.DOCSTATUS_Completed);
		conRegist.saveEx();
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
		// TODO Auto-generated method stub
		return null;
	}

}
