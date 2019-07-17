/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.AdempiereUserError;

/**
 * @author eko
 *
 */
public class MUNSMessOccupants extends X_UNS_Mess_Occupants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_Mess_Occupants_ID
	 * @param trxName
	 */
	public MUNSMessOccupants(Properties ctx, int UNS_Mess_Occupants_ID,
			String trxName) {
		super(ctx, UNS_Mess_Occupants_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSMessOccupants(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public int[] getOccupantsEmployee()
	{
		int[] employees = {getUNS_Employee_ID(), getFamEmployee1_ID(), getFamEmployee2_ID()
				, getFamEmployee3_ID(), getFamEmployee4_ID(), getFamEmployee4_ID()
				, getFamEmployee5_ID(), getFamEmployee6_ID()};
		return employees;
	}
	
	public String[] getFamsNonEmployee()
	{
		String[] fams = {getFamily1(),getFamily2(),getFamily3(),getFamily4()
				,getFamily5(),getFamily6(),getNonEmpName()};
		return fams;
	}
	
	private Integer getTotalPenghuni()
	{
		Integer totalPenghuni = 0;
		for(MUNSMessOccupants occupant : getParent().getLines(true))
		{
			for(int employeeOccupant : occupant.getOccupantsEmployee())
			{
				if(employeeOccupant > 0)
					totalPenghuni++;
			}
			for(String OccupantsNonEmployee : occupant.getFamsNonEmployee())
			{
				if(null != OccupantsNonEmployee &&
						OccupantsNonEmployee.trim().length()>0)
					totalPenghuni++;
			}
				
		}
		return totalPenghuni;
	}
	
	private boolean validateFarmEmployee(int FEno) {
		if (!get_Value("FamEmployee"+FEno+"_ID").equals(get_Value(COLUMNNAME_UNS_Employee_ID))){
			for (int i = 1;i<7;i++){
				if (i==FEno || get_Value("FamEmployee"+i+"_ID")==null)
					continue;
				
				if(get_Value("FamEmployee"+FEno+"_ID").equals(get_Value("FamEmployee"+i+"_ID")))
					return false;
			}
		} else
			return false;
	
		return true;
	}

	public boolean updateHeader()
	{
		MUNSMessPartition parent = getParent();
		parent.setOccupiedByOccupants(getTotalPenghuni());
		parent.setAvailableToOccupy(parent.getMaximumToOccupy()-parent.getOccupiedByOccupants());
		if(parent.getAvailableToOccupy()<0)
			throw new AdempiereUserError("User input, Number of residents is too much.");
		if(!parent.save())
			return false;
		return true;
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{		
		if(!isFam1Employee())
		{
			if(get_ValueOld(COLUMNNAME_FamEmployee1_ID) != null)
			{
				X_UNS_Employee employee = new X_UNS_Employee(
						getCtx(), new Integer(get_ValueOld(COLUMNNAME_FamEmployee1_ID).toString())
						, get_TrxName());
				employee.setUNS_Mess_Partition_ID(0);
				employee.save();
			}
			setFamEmployee1_ID(0);
		}
		else 
		{
			setFamily1(null);
			if(getFamEmployee1_ID()!= 0 && !validateFarmEmployee(1))
				throw new AdempiereUserError("User input, Duplicate employee.");
		}
		if(!isFam2Employee())
		{
			if(get_ValueOld(COLUMNNAME_FamEmployee2_ID) != null &&
					validateFarmEmployee(2))
			{
				X_UNS_Employee employee = new X_UNS_Employee(
						getCtx(), new Integer(get_ValueOld(COLUMNNAME_FamEmployee2_ID).toString())
						, get_TrxName());
				employee.setUNS_Mess_Partition_ID(0);
				employee.save();
			}
			setFamEmployee2_ID(0);
		}
		else
		{
			setFamily2(null);
			if(getFamEmployee1_ID()!= 0 && !validateFarmEmployee(1))
				throw new AdempiereUserError("User error, Duplicate employee.");
		}
		if(!isFam3Employee())
		{
			if(get_ValueOld(COLUMNNAME_FamEmployee3_ID) != null &&
					validateFarmEmployee(3))
			{
				X_UNS_Employee employee = new X_UNS_Employee(
						getCtx(), new Integer(get_ValueOld(COLUMNNAME_FamEmployee3_ID).toString())
						, get_TrxName());
				employee.setUNS_Mess_Partition_ID(0);
				employee.save();
			}
			setFamEmployee3_ID(0);
		}
		else
		{
			setFamily3(null);
			if(getFamEmployee1_ID()!= 0 && !validateFarmEmployee(1))
				throw new AdempiereUserError("User error, Duplicate employee.");
		}
		if(!isFam4Employee())
		{
			if(get_ValueOld(COLUMNNAME_FamEmployee4_ID) != null &&
					validateFarmEmployee(4))
			{
				X_UNS_Employee employee = new X_UNS_Employee(
						getCtx(), new Integer(get_ValueOld(COLUMNNAME_FamEmployee4_ID).toString())
						, get_TrxName());
				employee.setUNS_Mess_Partition_ID(0);
				employee.save();
			}
			setFamEmployee4_ID(0);
		}
		else
		{
			setFamily4(null);
			if(getFamEmployee1_ID()!= 0 && !validateFarmEmployee(1))
				throw new AdempiereUserError("User error, Duplicate employee.");
		}
		if(!isFam5Employee())
		{
			if(get_ValueOld(COLUMNNAME_FamEmployee5_ID) != null &&
					validateFarmEmployee(5))
			{
				X_UNS_Employee employee = new X_UNS_Employee(
						getCtx(), new Integer(get_ValueOld(COLUMNNAME_FamEmployee5_ID).toString())
						, get_TrxName());
				employee.setUNS_Mess_Partition_ID(0);
				employee.save();
			}
			setFamEmployee5_ID(0);
		}
		else
		{
			setFamily5(null);
			if(getFamEmployee1_ID()!= 0 && !validateFarmEmployee(1))
				throw new AdempiereUserError("User error, Duplicate employee.");
		}
		if(!isFam6Employee())
		{
			if(get_ValueOld(COLUMNNAME_FamEmployee6_ID) != null &&
					validateFarmEmployee(6))
			{
				X_UNS_Employee employee = new X_UNS_Employee(
						getCtx(), new Integer(get_ValueOld(COLUMNNAME_FamEmployee6_ID).toString())
						, get_TrxName());
				employee.setUNS_Mess_Partition_ID(0);
				employee.save();
			}
			setFamEmployee6_ID(0);
		}
		else
		{
			setFamily6(null);
			if(getFamEmployee1_ID()!= 0 && !validateFarmEmployee(1))
				throw new AdempiereUserError("User error, Duplicate employee.");
		}
		
		return super.beforeSave(newRecord);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean sucess) {
		
		int[] employees = getOccupantsEmployee();
		
		for (int employee_ID : employees)
		{
			if (employee_ID <= 0)
				continue;
			
			X_UNS_Employee employee = new X_UNS_Employee(getCtx(), employee_ID, get_TrxName());
			employee.setUNS_Mess_Partition_ID(getUNS_Mess_Partition_ID());
			employee.save();
			
		}
		if(!updateHeader())
			return false;
		
		return true;
	}

	@Override
	protected boolean beforeDelete() {
		for(int employeeID : getOccupantsEmployee()){
			if (employeeID==0) 
				continue;
			MUNSEmployee employee = MUNSEmployee.get(getCtx(), employeeID);
			employee.setUNS_Mess_Partition_ID(0);
			employee.save();
		}
		return super.beforeDelete();
	}

	@Override
	protected boolean afterDelete(boolean success) {
		if(!updateHeader())
			throw new AdempiereException("Failed To Update Header");
		
		return super.afterDelete(success);
	}
	

	public MUNSMessPartition getParent()
	{
		return (MUNSMessPartition)getUNS_Mess_Partition();
	}

}
