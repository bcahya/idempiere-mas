/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import com.uns.model.I_UNS_MedicalRecord;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSEmployeeAllowanceRecord;
import com.uns.model.MUNSLeavePermissionTrx;
import com.uns.model.MUNSMedicalRecord;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSPayrollLevelConfig;
import com.uns.model.MUNSReimbursement;

/**
 * @author Inohtaf
 *
 */
public class CalloutMedical implements IColumnCallout {

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) throws AdempiereUserError 
	{
		String tableName = mTab.getTableName();
		
		if (MUNSMedicalRecord.Table_Name.equals(tableName))
		{
			if (mField.getColumnName().equals(MUNSMedicalRecord.COLUMNNAME_UNS_Employee_ID)
					|| mField.getColumnName().equals(MUNSMedicalRecord.COLUMNNAME_who_is_sick))
			{	
				return setMedicalDependantData(ctx, WindowNo, mTab, mField, value, oldValue);
			}
			else if (mField.getColumnName().equals(MUNSMedicalRecord.COLUMNNAME_medical_date))
			{	
				return medicalDate(ctx, WindowNo, mTab, mField, value, oldValue);
			}
			else if (mField.getColumnName().equals(MUNSMedicalRecord.COLUMNNAME_leave_type_recommendation))
			{	
				return setMedicalDependantData(ctx, WindowNo, mTab, mField, value, oldValue);
			}
		}
		else if (MUNSReimbursement.Table_Name.equals(tableName)) 
		{
			if (mField.getColumnName().equals(MUNSReimbursement.COLUMNNAME_UNS_Employee_ID)
				|| mField.getColumnName().equals(MUNSReimbursement.COLUMNNAME_RequestDate))
				
				return setReimbursementAllowance(ctx, WindowNo, mTab, mField, value, oldValue);
		}

		return null;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String medicalDate(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (null == value)
			return null;
		
		I_UNS_MedicalRecord medicalRecord = GridTabWrapper.create(mTab, I_UNS_MedicalRecord.class);
		
		Integer UNS_Employee_ID = medicalRecord.getUNS_Employee_ID();
		
		Timestamp date = (Timestamp)value;
		
		MUNSPayrollConfiguration payConfig = MUNSPayrollConfiguration.get(ctx, date, null);
		BigDecimal medicalCosts = BigDecimal.ZERO;
		
		if (null != UNS_Employee_ID && UNS_Employee_ID > 0)
		{
			MUNSEmployee employee = (MUNSEmployee) medicalRecord.getUNS_Employee();
			
			if (employee.getUNS_Contract_Recommendation_ID() == 0
				|| date.after(employee.getUNS_Contract_Recommendation().getDateContractEnd()))
			{
				return "Employee is out of contract.";
			}
			else if (medicalRecord.getC_InvoiceLine_ID() == 0)
			{
				setAllowance(ctx, WindowNo, mTab, date);
				
				medicalCosts = payConfig.getMedicalKaryawan();
			}
		}
		else if (medicalRecord.getC_InvoiceLine_ID() == 0)
		{
			medicalCosts = payConfig.getMedicalNonKaryawan();
		}
		//medicalRecord.setPay(medicalPay);
		medicalRecord.setMedicalCosts(medicalCosts);
		
		return null;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String setMedicalDependantData(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
	
		if(null == value)
			return null;

		I_UNS_MedicalRecord medicalRecord = GridTabWrapper.create(mTab, I_UNS_MedicalRecord.class);
		/*
		Object tmpID = mTab.getValue(MUNSMedicalRecord.COLUMNNAME_UNS_Employee_ID);
		if (null == tmpID)
			return null;
		*/
		Integer UNS_Employee_ID = medicalRecord.getUNS_Employee_ID();
		
		if (UNS_Employee_ID == 0)
			return null;
		
		String dependent = (String) mTab.getValue("who_is_sick");
		
		String depValue = null;
		
		MUNSEmployee employee = new MUNSEmployee(ctx, UNS_Employee_ID, null);
		
		if (null == dependent || dependent.equals(MUNSMedicalRecord.WHO_IS_SICK_Employee))
			depValue = employee.getName();
		else if (dependent.equals(MUNSMedicalRecord.WHO_IS_SICK_EmployeeInsured1))
			depValue = employee.getEmployeeInsured1();
		else if (dependent.equals(MUNSMedicalRecord.WHO_IS_SICK_EmployeeInsured2))
			depValue = employee.getEmployeeInsured2();
		else if (dependent.equals(MUNSMedicalRecord.WHO_IS_SICK_EmployeeInsured3))
			depValue = employee.getEmployeeInsured3();
		else if (dependent.equals(MUNSMedicalRecord.WHO_IS_SICK_EmployeeInsured4))
			depValue = employee.getEmployeeInsured4();
		
		//if(depValue == null) {
			//mTab.setValue("who_is_sick", MUNSMedicalRecord.WHO_IS_SICK_Employee);
			//medicalRecord.setwho_is_sick(MUNSMedicalRecord.WHO_IS_SICK_Employee);
			//throw new AdempiereUserError("The employee do not have "+dependent);
			//return "The employee do not have " + dependent;
		//}
		//else
			medicalRecord.setinsured_name(depValue);
			//mTab.setValue("insured_name", depValue);
		
		if (medicalRecord.getC_InvoiceLine_ID() == 0) {
			Timestamp date = (Timestamp) mTab.getValue(MUNSMedicalRecord.COLUMNNAME_medical_date);
			setAllowance(ctx, WindowNo, mTab, date);
		}
		
		String leaveTypeRecommendation = medicalRecord.getleave_type_recommendation();
		
		if (leaveTypeRecommendation != null && !leaveTypeRecommendation.isEmpty())
		{
			int numberOfChild = employee.getNumberOfChild();
			
			String sql = "SELECT count(*) FROM UNS_LeavePermissionTrx WHERE UNS_Employee_ID=? AND LeaveType=? AND" +
					" DocStatus IN (?, ?)";
			int prevMaternity = 
					DB.getSQLValue(Trx.createTrxName(), sql, 
								   employee.get_ID(), 
								   MUNSLeavePermissionTrx.LEAVETYPE_MaternityHamilPlusMelahirkan,
								   MUNSLeavePermissionTrx.DOCSTATUS_Completed, 
								   MUNSLeavePermissionTrx.DOCSTATUS_Closed);
			if (prevMaternity < 0)
				prevMaternity = 0;
			mTab.setValue(MUNSMedicalRecord.COLUMNNAME_NumberOfChild, numberOfChild + prevMaternity + 1);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	private String setReimbursementAllowance(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
			return null;

		Object tmpID = mTab.getValue(MUNSMedicalRecord.COLUMNNAME_UNS_Employee_ID);
		if (null == tmpID)
			return null;

		Integer UNS_Employee_ID = (Integer) tmpID;
		
		if (UNS_Employee_ID == 0)
			return null;
		
		Timestamp requestDate = (Timestamp) mTab.getValue(MUNSReimbursement.COLUMNNAME_RequestDate);
		
		if (null == requestDate)
			return null;
		
		setAllowance(ctx, WindowNo, mTab, requestDate);
		
		return null;
	}

	/**
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param trxDate
	 * @return
	 */
	private String setAllowance (Properties ctx, int WindowNo, GridTab mTab, Timestamp trxDate)
	{
		/*
		if(null == value)
			return null;
		*/
		Object tmpID = mTab.getValue(MUNSMedicalRecord.COLUMNNAME_UNS_Employee_ID);
		if (null == tmpID)
			return null;
		
		if (null == trxDate)
			return null;
		
		String trxName = null; Trx.createTrxName();
		
		MUNSEmployee employee = new MUNSEmployee(ctx, (Integer) tmpID, trxName);

		Timestamp contractEnd = employee.getUNS_Contract_Recommendation().getDateContractEnd();
		
		if (contractEnd == null || trxDate.after(contractEnd))
			return null;
		
		BigDecimal employeeAllowance = MUNSPayrollLevelConfig.getMedicalAllowanceOf(ctx, employee, trxName);
		
		if (employeeAllowance == null || employeeAllowance.signum() < 1)
			return null;
		
		MUNSEmployeeAllowanceRecord employeeAllowanceRcd = MUNSEmployeeAllowanceRecord.getCreateMedical(
				ctx, employee, trxDate, trxName);
		
		if (employeeAllowanceRcd == null)
			return null;
		
		mTab.setValue(MUNSMedicalRecord.COLUMNNAME_MedicalAllowance, employeeAllowanceRcd.getMedicalAllowance());
		mTab.setValue(MUNSMedicalRecord.COLUMNNAME_RemainingAllowance, employeeAllowanceRcd.getRemainingAmt());
		
		return null;
	}
}