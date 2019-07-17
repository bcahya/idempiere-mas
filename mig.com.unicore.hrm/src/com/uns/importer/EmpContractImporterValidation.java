/**
 * 
 */
package com.uns.importer;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Properties;

import jxl.Sheet;
import com.uns.base.model.Query;
import com.uns.model.MUNSContractRecommendation;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSPayrollLevelConfig;
import com.uns.model.X_UNS_Contract_Recommendation;
import com.uns.model.factory.UNSHRMModelFactory;

import org.compiere.model.PO;

/**
 * @author Haryadi
 *
 */
public class EmpContractImporterValidation implements ImporterValidation {

	protected Properties 	m_ctx = null;
	protected String		m_trxName = null;
	protected Sheet			m_sheet	= null;
	protected Hashtable<String, PO> m_PORefMap = null;
	private	  String m_employmentType = MUNSContractRecommendation.EMPLOYMENTTYPE_Company;
	
	/**
	 * 
	 */
	public EmpContractImporterValidation(Properties ctx, Sheet sheet, String trxName) {
		m_ctx = ctx;
		m_trxName = trxName;
		m_sheet = sheet;
	}

	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
		
	}
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{
		MUNSContractRecommendation newCtr = (MUNSContractRecommendation)po;
		setEmploymentType(newCtr);
		
		MUNSPayrollConfiguration payConfig = MUNSPayrollConfiguration.getCurrentActive(m_ctx, m_trxName);
		MUNSPayrollLevelConfig payrollLevel = 
				payConfig.getPayrollLevel(newCtr.getNewPayrollLevel(), newCtr.getNextPayrollTerm());
		
		// Set all necessary values from the payroll configuration.
		newCtr.setEmploymentType(getEmploymentType());
		newCtr.setDocStatus(MUNSContractRecommendation.DOCSTATUS_Completed);
		newCtr.setDocAction(MUNSContractRecommendation.DOCACTION_Close);
		newCtr.setProcessed(true);
		
		String oriNotes = newCtr.getNotes();
		//newCtr.setNewNIK(oriNotes);
		newCtr.setNotes("***Imported on " + new java.util.Date() + "***");
		
		if (payrollLevel != null && payrollLevel.getUNS_PayrollLevel_Config_ID() > 1)
		{
			if(newCtr.getNew_G_Pokok().signum() <= 0)
				newCtr.setNew_G_Pokok(payrollLevel.getMin_G_Pokok());
			if(newCtr.getNew_T_Jabatan().signum() <= 0)
				newCtr.setNew_T_Jabatan(payrollLevel.getMin_G_T_Jabatan());
			if(newCtr.getNew_T_Kesejahteraan().signum() <= 0)
				newCtr.setNew_T_Kesejahteraan(payrollLevel.getMin_G_T_Kesejahteraan());
			if(newCtr.getNew_T_Lembur().signum() <= 0)
				newCtr.setNew_T_Lembur(payrollLevel.getMin_G_T_Lembur());
			
			if (newCtr.getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_SubContract)
				|| !newCtr.getNextPayrollTerm().equals(
						MUNSContractRecommendation.NEXTPAYROLLTERM_Monthly))
				newCtr.setNew_G_Pokok(payrollLevel.getUMK());
			
			newCtr.setNew_A_L1(payrollLevel.getMin_A_L1());
			newCtr.setNew_A_L2(payrollLevel.getMin_A_L2());
			newCtr.setNew_A_L3(payrollLevel.getMin_A_L3());
		}
		MUNSEmployee employee = null;
		
		if (newCtr.getUNS_Employee_ID() == 0)
		{
			String name = oriNotes.substring(0, oriNotes.indexOf("__"));
			String ttl = oriNotes.substring(oriNotes.indexOf("__") + 2);
			System.out.println("TTL : " + ttl);
			Date ttlDate = Date.valueOf(ttl);
			
			String whereClause = "Name=? AND DateOfBirth=?";
			employee = 
					Query.get(m_ctx, UNSHRMModelFactory.EXTENSION_ID, MUNSEmployee.Table_Name, whereClause, m_trxName)
					.setParameters(name, new Timestamp(ttlDate.getTime()))
					.first();
			
			if (employee == null)
			{
				employee = new MUNSEmployee(m_ctx, 0, m_trxName);
				employee.setAD_Org_ID(newCtr.getNewDept_ID());
				employee.setName(name);
				employee.setDateOfBirth(new Timestamp(ttlDate.getTime()));
				employee.setValue(newCtr.getNewNIK());
				employee.setEmploymentType(getEmploymentType());
				employee.setPayrollTerm(newCtr.getNextPayrollTerm());
				employee.setC_Job_ID(newCtr.getNewJob_ID());
				employee.setC_BPartner_ID(newCtr.getNewSectionOfDept_ID());
				employee.setPayrollLevel(newCtr.getNewPayrollLevel());
				employee.setGender(newCtr.getNewGender());
				employee.setFamilyName("-");
				employee.setFatherName("-");
				employee.setMotherName("-");
				employee.setNationality(MUNSEmployee.NATIONALITY_WargaNegaraIndonesia);
				employee.setPlaceOfBirth("-");
				//employee.setReligion("");
				if (employee.getGender().equals(MUNSEmployee.GENDER_Female)) {
					if (ttlDate.before(Date.valueOf("1988-01-01")))
						employee.setMaritalStatus(MUNSEmployee.MARITALSTATUS_Kawin0Tanggungan);
					else
						employee.setMaritalStatus(MUNSEmployee.MARITALSTATUS_TidakKawin0Tanggungan);
				}
				else {
					if (ttlDate.before(Date.valueOf("1985-01-01")))
						employee.setMaritalStatus(MUNSEmployee.MARITALSTATUS_Kawin0Tanggungan);
					else
						employee.setMaritalStatus(MUNSEmployee.MARITALSTATUS_TidakKawin0Tanggungan);
				}
				//employee.setHighestEdBackround("");
				employee.setIsBlacklist(false);
				employee.setRegNo(employee.getValue());
				
				
				if (getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_Company))
					//&& (newCtr.getNextEmploymentStatus())
				{
					;
				}
				else if (getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_SubContract))
				{
					employee.setVendor_ID(newCtr.getNewAgent_ID());
					employee.setContractNumber(newCtr.getNextContractNumber());
				}
				employee.saveEx();
			}
			newCtr.setUNS_Employee_ID(employee.get_ID());
			
			if (poRefMap == null)
				poRefMap = new Hashtable<String, PO>();
			m_PORefMap = poRefMap;
		}
		else {
			employee = new MUNSEmployee(m_ctx, newCtr.getUNS_Employee_ID(), m_trxName);
		}
		//employee.setValue(oriNotes);
		
		if (m_PORefMap == null)
			m_PORefMap = poRefMap;
		if (m_PORefMap.get(newCtr.getNewNIK()) == null)
			m_PORefMap.put(newCtr.getNewNIK(), employee);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO)
	 */
	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{
		MUNSEmployee employee = (MUNSEmployee)
				poRefMap.get((String) po.get_Value(MUNSContractRecommendation.COLUMNNAME_NewNIK));
		employee.setUNS_Contract_Recommendation_ID(po.get_ID());
		MUNSContractRecommendation theCtr = (MUNSContractRecommendation) po;
		
		employee.setAD_Org_ID(theCtr.getNewDept_ID());
		employee.setValue(theCtr.getNewNIK());
		employee.setPayrollLevel(theCtr.getNewPayrollLevel());
		employee.setEmploymentType(getEmploymentType());
		employee.setPayrollTerm(theCtr.getNextPayrollTerm());
		employee.setAD_Org_ID(theCtr.getNewDept_ID());
		employee.setC_BPartner_ID(theCtr.getNewSectionOfDept_ID());
		employee.setShift(theCtr.getNewShift());
		employee.setC_Job_ID(theCtr.getNewJob_ID());
		if (getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_SubContract)) {
			employee.setContractNumber(theCtr.getNextContractNumber());
			employee.setUNS_SlotType_ID(theCtr.getNewSlotType_ID());
			employee.setVendor_ID(theCtr.getNewAgent_ID());
		}
		employee.saveEx();
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveAllRow(java.util.Hashtable, org.compiere.model.PO[])
	 */
	@Override
	public String afterSaveAllRow(Hashtable<String, PO> poRefMap, PO[] pos) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#getPOReferenceMap()
	 */
	@Override
	public Hashtable<String, PO> getPOReferenceMap() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	protected String getEmploymentType()
	{
		return m_employmentType;
	}

	/**
	 * 
	 * @param ctr
	 */
	protected void setEmploymentType(X_UNS_Contract_Recommendation ctr)
	{
		String newNIK = ctr.getNewNIK();
		
		if (newNIK.charAt(0) == 'P')
			m_employmentType = MUNSContractRecommendation.EMPLOYMENTTYPE_SubContract;
		else
			m_employmentType = MUNSContractRecommendation.EMPLOYMENTTYPE_Company;
	}
}
