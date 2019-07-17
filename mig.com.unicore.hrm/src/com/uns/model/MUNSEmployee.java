/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MUser;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.uns.util.ErrorMsg;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;
import com.uns.model.process.LoadBasicPayroll;

/**
 * @author eko
 *
 */
public class MUNSEmployee extends X_UNS_Employee 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean m_isFormModification;

	/**
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @param trxName
	 */
	public MUNSEmployee(Properties ctx, int UNS_Employee_ID, String trxName) {
		super(ctx, UNS_Employee_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSEmployee(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean beforeSave (boolean newRecord){
		if(null==getValue() || "".equals(getValue()) ){
			setValue("0000000");
		}
		
		if (is_ValueChanged(COLUMNNAME_AttendanceName)) {
			Object oOld = get_ValueOld(COLUMNNAME_AttendanceName);
			String attOld = (String) oOld;
			updateCheckInOuts(false, attOld);
			updateCheckInOuts(true, getAttendanceName());
		}
		
		if(isBlacklist()|| isTerminate())
			setIsActive(false);
		if(!isBlacklist()&& !isTerminate())
			setIsActive(true);
		
		if (!Util.isEmpty(getAttendanceName(), true))
		{
			String sql = "SELECT COUNT(*) FROM UNS_Employee WHERE "
					+ " AttendanceName = ? AND UNS_Employee_ID <> ?";
			int record = DB.getSQLValue(get_TrxName(), sql, getAttendanceName(), 
					get_ID());
			if (record > 0)
			{
				ErrorMsg.setErrorMsg(getCtx(), "Duplicate Attendance Name", 
						"Duplicate AttendanceName");
				return false;
			}
		}
		
		return true;
	}
	
	public static List<MUNSEmployee> get(Properties ctx, int AD_Org_ID, String employeeType, String trxName)
	{
		return Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, MUNSEmployee.Table_Name
				, COLUMNNAME_AD_Org_ID + " = " + AD_Org_ID + 
				" AND " + COLUMNNAME_EmploymentType + " = " + employeeType, trxName)
				.list();
	}
	
	/**
	 * @author YAKA
	 * @param ctx
	 * @param UNS_Employee_ID
	 * @return
	 */
	public static MUNSEmployee get(Properties ctx, int UNS_Employee_ID){
		return new MUNSEmployee(ctx, UNS_Employee_ID, null);		
	}
	
	/**
	 * @author YAKA
	 * @param ctx
	 * @param NIK
	 * @return
	 */
	public static MUNSEmployee get(Properties ctx, String Nik, String trxName){
		MUNSEmployee retval= Query.get(ctx, UNSHRMModelFactory.getExtensionID(),
					MUNSEmployee.Table_Name, "value=?", trxName)
				.setParameters(Nik).setOnlyActiveRecords(true)
				.firstOnly();
		
		return retval;		
	}

	public BigDecimal getMedicalAllowanceLeft()
	{
		return (getMedicalAllowance().subtract(getMedicalAllowanceUsed()));
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	public boolean isHoliday(String day)
	{
		return day.equals(getNoWorkDay());
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	public boolean isHoliday(int day)
	{
		return String.valueOf(day).equals(getNoWorkDay());
	}	
	
	/**
	 * 
	 * @param sectionDept_ID
	 * @param contractType
	 * @param trxName
	 * @return
	 */
	public static List<MUNSEmployee> getOf(
			int sectionDept_ID, String contractType, String trxName)
	{
		List<MUNSEmployee> list = new ArrayList<MUNSEmployee>();
		
		String sql = "SELECT e.* FROM " + Table_Name
				+ " e INNER JOIN "
				+ MUNSContractRecommendation.Table_Name + " c ON "
				+ "c." + MUNSContractRecommendation
							.COLUMNNAME_UNS_Contract_Recommendation_ID
				+ " = e." + COLUMNNAME_UNS_Contract_Recommendation_ID
				+ " WHERE e." + COLUMNNAME_C_BPartner_ID + " =? AND "
				+ " c." + MUNSContractRecommendation.COLUMNNAME_NextContractType
				+ " =?";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, trxName);
			st.setInt(1, sectionDept_ID);
			st.setString(2, contractType);
			
			rs = st.executeQuery();
			while (rs.next())
			{
				MUNSEmployee employee = new MUNSEmployee(Env.getCtx(), rs, trxName);
				list.add(employee);
			}
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		} finally
		{
			DB.close(rs, st);
		}
		
		if(list.size() == 0)
			return null;
				
		return list;
	}
	
	/**
	 * create new Contract from import
	 */
	public void createContract()
	{
		MUNSContractRecommendation newContract = new MUNSContractRecommendation(this);
		//date COntract start and date contract end
		Timestamp dateContractStart = getEntryDate();
		Calendar calContract = Calendar.getInstance();
		calContract.setTimeInMillis(System.currentTimeMillis());
		calContract.add(Calendar.DAY_OF_YEAR, 365);
		if(null == dateContractStart)
			dateContractStart = new Timestamp(System.currentTimeMillis());
		Timestamp dateContractEnd = new Timestamp(calContract.getTimeInMillis());
		newContract.setDateContractStart(dateContractStart);
		newContract.setDateContractEnd(dateContractEnd);
		//manage contract type
		String cotractType = MUNSEmployee.EMPLOYMENTTYPE_Company.equals(getEmploymentType()) 
				? MUNSContractRecommendation.NEXTCONTRACTTYPE_Contract1 
						: MUNSContractRecommendation.NEXTCONTRACTTYPE_SquenceContract;
		String payrollLevel = getPayrollLevel();
		
		if(null == payrollLevel)
			payrollLevel = MUNSEmployee.EMPLOYMENTTYPE_Company.equals(getEmploymentType()) 
				? MUNSContractRecommendation.NEWPAYROLLLEVEL_Level4
						: MUNSContractRecommendation.NEWPAYROLLLEVEL_Level6;
		
		newContract.setNewPayrollLevel(payrollLevel);
		newContract.setNextContractType(cotractType);
		newContract.setNextPayrollTerm(getPayrollTerm());
		newContract.setNewDept_ID(getAD_Org_ID());
		newContract.setNewShift(getShift() == null ? SHIFT_Shift : getShift());
		newContract.setNewSectionOfDept_ID(getC_BPartner_ID());
		newContract.setNewGender(getGender());
		newContract.setNewJob_ID(getC_Job_ID());
		newContract.setEmploymentType(getEmploymentType());
		String nik = getValue();
		if(null == nik || nik.equals("0000000"))
		{
			NIKGenerator nikGen = new NIKGenerator(NIKGenerator.TYPE_HARIAN, get_TrxName());	
			nik = nikGen.getNewNIK();
		}
		newContract.setNewNIK(nik);
		newContract.saveEx();
		LoadBasicPayroll basePay = new LoadBasicPayroll(newContract);
		
		String success = basePay.load();
		if(success != null)
			throw new AdempiereException(success);
		
		try {
			newContract.processIt(MUNSContractRecommendation.DOCACTION_Complete);
			newContract.saveEx();
		} catch (Exception ex) {
			StringBuilder sb = new StringBuilder("Failed to complete contract document : ")
					.append(newContract.getDocumentNo())
					.append(". Caused by : ")
					.append(ex.getMessage());
			String errMsg = sb.toString();
			throw new AdempiereException(errMsg);
		}
	}
	public String toString(){
		
		return "NIP :" + "[" + getValue() + "]" + "-" + "NAME :" + "[" + getName() + "]";
	}
	
	public MUser initializeUser()
	{
		String sqlCheck = "SELECT AD_User_ID FROM AD_User WHERE UNS_Employee_ID = ?";
		int user_ID = DB.getSQLValue(get_TrxName(), sqlCheck, getUNS_Employee_ID());
		if(user_ID <= 0)
			return createUser();
		
		return new MUser(getCtx(), user_ID, get_TrxName());
	}
	
	public MUser createUser()
	{
		MUser user = new MUser(getCtx(), 0, get_TrxName());
		user.setUNS_Employee_ID(get_ID());
		user.setValue(getValue());
		user.setAD_Org_ID(getAD_Org_ID());
		user.setAD_OrgTrx_ID(getAD_Org_ID());
		user.setBirthday(getDateOfBirth());
		user.setC_Job_ID(getC_Job_ID());
		user.setRealName(getName());
		user.setPhone(getHomePhone());
		user.setPhone2(getMobilePhone());
		user.setPassword(getValue()+"123");
		user.setName(getName());
		user.setComments("::Auto generate on import::");
		user.saveEx();
		
		return user;
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	public boolean isWorkDay (String day)
	{
		return !day.equals(getNoWorkDay());
	}
	
	public static MUNSEmployee[] gets (
			Properties ctx, String whereClause, List<Object> params, 
			String orderBy, String trxName)
	{
		Query query = Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
				whereClause, trxName);
		if (params != null && params.size() > 0)
		{
			query.setParameters(params);
		}
		if (!Util.isEmpty(orderBy, true))
		{
			query.setOrderBy(orderBy);
		}
		
		List<MUNSEmployee> list = query.list();
		MUNSEmployee[] employes = new MUNSEmployee[list.size()];
		list.toArray(employes);
		
		return employes;
	}
	
	public static MUNSEmployee getByAttName (String trxName, String attName)
	{
		return Query.get(
				Env.getCtx(), UNSHRMModelFactory.EXTENSION_ID, Table_Name, 
				COLUMNNAME_AttendanceName + " = ? ", trxName).
				setParameters(attName).
				first();
	}
	
	public String updateEmployeeData(MUNSContractRecommendation contract)
	{
		if (contract.getDocAction().equals(DocAction.ACTION_Void))
		{
			String sql = " SELECT CONCAT(DateContractStart, '-', "
					+ " AD_Org_ID) FROM UNS_Contract_Recommendation "
					+ " WHERE IsMoveTo = 'Y' AND DateContractStart "
					+ " < ? AND UNS_Employee_ID = ? ORDER BY DateContractStart";
			String arrays = DB.getSQLValueString(get_TrxName(), sql, 
					contract.getDateContractStart(), getUNS_Employee_ID());
			if (null != arrays)
			{
				String[] vals = arrays.split("-");
				if (vals.length == 2)
				{
					Timestamp dateStart = Timestamp.valueOf(vals[0]);
					Integer orgFrom = new Integer(vals[1]);
					setLastTransferFrom(orgFrom);
					setIndicatorTransfer(dateStart);
				}
			}
			else
			{
				setLastTransferFrom(-1);
				setIndicatorTransfer(null);
			}
			
			setPayrollLevel(PAYROLLLEVEL_NotDefined);

			BigDecimal newMedicalAllowance = Env.ZERO;
			setMedicalAllowance(newMedicalAllowance);
			setMedicalAllowanceUsed(BigDecimal.ZERO);
			setGender(null);
			setC_BPartner_ID(-1);
			setC_Job_ID(-1);
			setUNS_Contract_Recommendation_ID(-1);
			setPayrollTerm(null);
			setIsUseGeneralPayroll(false);
			if (null != contract.getNewShift())
			{
				setShift(null);
			}
			if(getEmploymentType().equals(EMPLOYMENTTYPE_SubContract))
			{
				setContractNumber(0);
				setVendor_ID(-1);
			}
		}
		else
		{
			MUNSPayrollConfiguration payConfig = MUNSPayrollConfiguration.get(
					getCtx(), contract.getDateContractStart(), get_TrxName());
			if(null == payConfig)
				return "Could not find payroll configuration";
			MUNSPayrollLevelConfig payLevelConfig = payConfig.getPayrollLevel(
					contract.getNewPayrollLevel()
					, contract.getNextPayrollTerm());
			
			if (null == payLevelConfig)
				return "Not found Payroll Level Configuration with level " 
					+ contract.getNewPayrollLevel();

			setPayrollLevel(contract.getNewPayrollLevel());
			if(contract.isMoveTo())
			{
				Timestamp lastTransferDate = null;
				int lastTransferFrom = 0;
				
				lastTransferDate = contract.getDateContractStart();
				lastTransferFrom = getAD_Org_ID();
				
				setIndicatorTransfer(lastTransferDate);
				setLastTransferFrom(lastTransferFrom);
			}
			
			if(contract.getUNS_Contract_Evaluation_ID() > 0)
				setLast_Evaluation_ID(contract.getUNS_Contract_Evaluation_ID());

//			BigDecimal prevMedicalAllowence = 
//					employee.getMedicalAllowance().subtract(
//			employee.getMedicalAllowanceUsed());
			
			setIsUseGeneralPayroll(contract.isUseGeneralPayroll());
			BigDecimal newMedicalAllowance = payLevelConfig.getMedicalAllowance();
//			newMedicalAllowance = newMedicalAllowance.add(prevMedicalAllowence);
			setMedicalAllowance(newMedicalAllowance);
			setMedicalAllowanceUsed(BigDecimal.ZERO);
			setGender(contract.getNewGender());
			setC_BPartner_ID(contract.getNewSectionOfDept_ID());
			setAD_Org_ID(contract.getNewDept_ID());
			setC_Job_ID(contract.getNewJob_ID());
			setUNS_Contract_Recommendation_ID(contract.get_ID());
			setPayrollTerm(contract.getNextPayrollTerm());
			if (null != contract.getNewShift())
			{
				setShift(contract.getNewShift());
			}
			if(getEmploymentType().equals(EMPLOYMENTTYPE_SubContract))
			{
				setContractNumber(contract.getNextContractNumber());
				setVendor_ID(contract.getNewAgent_ID());
			}

		}
		
		return null;
	}
	
	/**
	 * @param ctx
	 * @param name
	 * @param birthDay
	 * @param trxName
	 * @return {@link MUNSEmployee} OR NULL
	 */
	public static MUNSEmployee getBaseNameBirthDay(Properties ctx, String name, Timestamp DateOfBirth, String trxName)
	{
		MUNSEmployee emp = null;
		String whereClause = " Name = ? AND DateOfBirth = ?";
		
		emp = new Query(ctx, Table_Name, whereClause, trxName).setParameters(name, DateOfBirth).first();
		
		return emp;
	}
	
	/**
	 * @param datePresence
	 * @return
	 */
	public Timestamp getStartTime(Timestamp datePresence)
	{
		Timestamp startTime = MUNSWorkHoursConfigLine.getStartTimeEmployee(
									getCtx(), get_ID(), datePresence, get_TrxName());
		
		if(startTime == null)
			startTime = getDefaultStartTime();
		
		return startTime;
	}
	
	/**
	 * @param datePresence
	 * @return
	 */
	public Timestamp getEndTime(Timestamp datePresence)
	{
		Timestamp endTime = MUNSWorkHoursConfigLine.getEndTimeEmployee(
									getCtx(), get_ID(), datePresence, get_TrxName());
		
		if(endTime == null)
			endTime = getDefaultStartTime();
		
		return endTime;
	}
	
	public Timestamp getDefaultStartTime()
	{
		String sql = "SELECT st.TimeSlotStart FROM UNS_SlotType st WHERE st.UNS_SlotType_ID ="
				+ " (SELECT rs.UNS_SlotType_ID FROM UNS_Resource rs WHERE rs.UNS_Resource_ID ="
				+ " (SELECT wl.UNS_Resource_ID FROM UNS_Resource_WorkerLine wl"
				+ " WHERE wl.Labor_ID = ?))";
		Timestamp startTime = DB.getSQLValueTS(get_TrxName(), sql, get_ID());
		
		return startTime;
	}
	
	public Timestamp getDefaultEndTime()
	{
		String sql = "SELECT st.TimeSlotEnd FROM UNS_SlotType st WHERE st.UNS_SlotType_ID ="
				+ " (SELECT rs.UNS_SlotType_ID FROM UNS_Resource rs WHERE rs.UNS_Resource_ID ="
				+ " (SELECT wl.UNS_Resource_ID FROM UNS_Resource_WorkerLine wl"
				+ " WHERE wl.Labor_ID = ?))";
		Timestamp endTime = DB.getSQLValueTS(get_TrxName(), sql, get_ID());
		
		return endTime;
	}
	
	private boolean updateCheckInOuts (boolean isLenkedToEmployee, String attName) {
		String sql = "UPDATE UNS_CheckInOut SET IsLinkedToEmployee = ? WHERE AttendanceName = ?";
		int ok = DB.executeUpdate(sql, new Object[]{isLenkedToEmployee ? "Y" : "N", attName}, false, get_TrxName());
		return ok != -1;
	}
}