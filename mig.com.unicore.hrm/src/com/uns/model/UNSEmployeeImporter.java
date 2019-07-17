/**
 * 
 */
package com.uns.model;

import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import com.uns.importer.ImporterValidation;

import org.compiere.model.MBPartner;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Org;
import org.compiere.model.X_C_Job;
import org.compiere.model.X_C_JobCategory;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

/**
 * @author menjangan
 *
 */
public class UNSEmployeeImporter implements ImporterValidation {
	
	protected Properties 	m_ctx 				= null;
	protected String		m_trxName 			= null;
	protected Sheet			m_sheet				= null;
	protected Hashtable<String, PO> m_PORefMap	= new Hashtable<String, PO>();
	protected int m_recordCount = 0;

	/**
	 * 
	 */
	public UNSEmployeeImporter() {
		// TODO Auto-generated constructor stub
	}

	public UNSEmployeeImporter(Properties ctx, Sheet sheet, String trxName)
	{
		this.m_ctx = ctx;
		this.m_sheet = sheet;
		this.m_trxName = trxName;
	}

	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
	}
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) {
		
		MUNSEmployee emp = (MUNSEmployee) po;
		if(null == emp.getName())
			throw new AdempiereUserError("Field name is mandatory!!!");
		if(emp.getAD_Org_ID() <= 0)
			throw new AdempiereUserError("Organization is Mandatory!!!");
		if(emp.getEmploymentType() == null)
			emp.setEmploymentType("COM");
		if(emp.getPayrollLevel() == null)
			emp.setPayrollLevel("COM".equals(emp.getEmploymentType()) ? "4" : "6");
		if(emp.getRegNo() == null)
			emp.setRegNo("-");
		if(emp.getNickName() == null)
			emp.setNickName(emp.getName().split(" ")[0]);
		if(emp.getPlaceOfBirth()== null)
			emp.setPlaceOfBirth("Lampung");
		if(emp.getMaritalStatus() == null)
			emp.setMaritalStatus(MUNSEmployee.MARITALSTATUS_TidakKawin0Tanggungan);
		if(emp.getDateOfBirth() == null)
			emp.setDateOfBirth(Timestamp.valueOf("1988-10-10 00:00:01"));
		if(emp.getNationality() == null)
			emp.setNationality(MUNSEmployee.NATIONALITY_WargaNegaraIndonesia);
		if(emp.getFamilyName() == null)
			emp.setFamilyName("-");
		if(emp.getFatherName() == null)
			emp.setFatherName("-");
		if(emp.getMotherName() == null)
			emp.setMotherName("-");
		if(emp.getEntryDate() == null)
			emp.setEntryDate(new Timestamp(System.currentTimeMillis()));
		if(emp.getEmploymentType() == null)
			emp.setEmploymentType("COM");
		if(emp.getC_Job_ID() == 0)
		{
			Cell cellContent = m_sheet.getCell("J"+ (m_recordCount +2));
			
			String cellVal = cellContent.getContents();
			
			String jobName = cellVal;
			if(jobName == null)
				jobName = "Default";
			
			boolean isEmployee = "COM".equals(emp.getEmploymentType());
			emp.setC_Job_ID(getC_Job_ID(emp.getAD_Org_ID(),jobName, isEmployee));
		}
		if(emp.getC_BPartner_ID() <= 0)
		{
			Cell cellContent = m_sheet.getCell("G" + (m_recordCount + 2));
			String cellVal = cellContent.getContents();
			if(cellVal == null)
				cellVal = "Undefined";
			boolean isEmployee = "COM".equals(emp.getEmploymentType());
			emp.setC_BPartner_ID(getSectionOfDept(emp.getAD_Org_ID(), cellVal, isEmployee));
		}
		if(emp.getPayrollTerm() == null)
		{
			String payrollTerm = emp.getEmploymentType().equals("COM") 
					? MUNSEmployee.PAYROLLTERM_Monthly : MUNSEmployee.PAYROLLTERM_HarianBulanan;
			emp.setPayrollTerm(payrollTerm);
		}		
		if(emp.getGender() == null)
			emp.setGender("M");
		
		m_recordCount++;
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
	 */
	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po,
			Hashtable<String, Object> freeColVals, int currentRow) 
	{
		
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveAllRow(java.util.Hashtable, org.compiere.model.PO[])
	 */
	@Override
	public String afterSaveAllRow(Hashtable<String, PO> poRefMap, PO[] pos) {
		// TODO Auto-generated method stub
//		String previxNik = "AG";
//		int nikCount = 1000000;
		for(PO po : pos)
		{
			MUNSEmployee emp = (MUNSEmployee) po;
			if(emp.getUNS_Contract_Recommendation_ID() > 0)
				continue;
//			emp.setValue(previxNik + nikCount++);
			emp.createContract();
			emp.save();
		}
		
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
	
	private int getC_Job_ID(int AD_Org_ID, String jobName, boolean isEmployee)
	{
		int c_job_id = 0;
		String sql = "SELECT C_Job_ID FROM C_Job WHERE C_Job.Name like '" + jobName + "'" +
				" AND IsEmployee ='Y' AND AD_Org_ID =" + AD_Org_ID;
		
		c_job_id = DB.getSQLValue(m_trxName, sql);
		if(c_job_id <= 0)
			c_job_id = createJob(AD_Org_ID, jobName, isEmployee);
		
		return c_job_id;
	}
	
	private int createJob(int AD_Org_ID, String jobName, boolean isEmployee)
	{
		X_C_Job job = new X_C_Job(m_ctx, 0, m_trxName);
		job.setName(jobName);
		job.setAD_Org_ID(AD_Org_ID);
		job.setC_JobCategory_ID(getDefaultJobCategory_ID(AD_Org_ID));
		job.setDescription("Auto Generate From Import Employee");
		job.setIsEmployee(isEmployee);
		job.save();
		
		return job.get_ID();
	}
	
	private int getDefaultJobCategory_ID(int AD_Org_ID)
	{
		int C_JobCategory_ID = 0;
		String sql = "SELECT C_JobCategory_ID FROM C_JobCategory " +
				"WHERE C_JobCategory.AD_Org_ID = " + AD_Org_ID;
		C_JobCategory_ID = DB.getSQLValue(m_trxName, sql);
		if(C_JobCategory_ID <= 0)
			C_JobCategory_ID = createNewJobCategory(AD_Org_ID);
		
		return C_JobCategory_ID;
	}
	
	private int createNewJobCategory(int AD_Org_ID)
	{
		X_C_JobCategory jobCat = new X_C_JobCategory(m_ctx, 0, m_trxName);
		jobCat.setName("Default Job Category");
		jobCat.setAD_Org_ID(AD_Org_ID);
		jobCat.setDescription("Auto Generate From Import Employee");
		jobCat.set_ValueNoCheck("LevelNo", "4"); //set to levl 4; //FIXME
		jobCat.save();
		return jobCat.get_ID();
	}
	
	/**
	 * 
	 * @param AD_Org_ID
	 * @param name
	 * @param isEmployee
	 * @return
	 */
	private int getSectionOfDept(int AD_Org_ID, String name, boolean isEmployee)
	{
		int sectionOfDept_ID = 0;
		String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE AD_Org_ID = ? AND Name LIKE ? AND IsEmployee = ?";
		sectionOfDept_ID = DB.getSQLValue(m_trxName, sql, AD_Org_ID, name, isEmployee? "Y": "N");
		if(sectionOfDept_ID <= 0)
			sectionOfDept_ID = createNewSectionOfDept(AD_Org_ID, name);
		
		return sectionOfDept_ID;
	}
	
	/**
	 * 
	 * @param AD_Org_ID
	 * @param name
	 * @return
	 */
	private int createNewSectionOfDept(int AD_Org_ID, String name)
	{
		X_AD_Org org = new X_AD_Org(m_ctx, AD_Org_ID, m_trxName);
		MBPartner bpartner = new MBPartner(m_ctx);
		bpartner.setAD_Org_ID(AD_Org_ID);
		String val = new StringBuilder(org.getValue()).append("-").append(name).toString();
		bpartner.setValue(val);
		bpartner.setName(name);
		bpartner.setName2(name);
		bpartner.setIsEmployee(true);
		bpartner.setDescription("::AUTO GENERATE ON IMPORT EMPLOYEE/WORKER::");
		bpartner.setIsVendor(false);
		bpartner.setIsCustomer(false);
		bpartner.setIsManufacturer(false);
		bpartner.setIsDiscountPrinted(false);
		bpartner.setIsSalesRep(false);
		bpartner.saveEx(m_trxName);
		return bpartner.get_ID();
	}

}
