/**
 * 
 */
package com.uns.model.callout;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import org.adempiere.base.IColumnCallout;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MDocType;
import org.compiere.model.MPeriod;

import com.uns.model.I_UNS_Employee;
import com.uns.model.I_UNS_HalfPeriod_Presence;
import com.uns.model.MUNSHalfPeriodPresence;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSResourceWorkerLine;

/**
 * @author menjangan
 *
 */
public class CalloutHalfPeriodPresence implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutHalfPeriodPresence() {
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue = null;
		if (mField.getColumnName().equals(MUNSHalfPeriodPresence.COLUMNNAME_C_Period_ID))
			retValue = C_Period_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		else if (mField.getColumnName().equals(MUNSHalfPeriodPresence.COLUMNNAME_HalfPeriodNo))
			retValue = HalfPeriodNo(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSHalfPeriodPresence.COLUMNNAME_UNS_Employee_ID))
			retValue = UNS_Employee(ctx, WindowNo, mTab, mField, value, oldValue);
		return retValue;
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
	protected String C_Period_ID(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (null == value)
			return "";
		
		Integer C_Period_ID = (Integer) value;
		
		if (C_Period_ID <= 0)
			return "";
		
		String HalfPeriodNo = (String)mTab.getValue(MUNSHalfPeriodPresence.COLUMNNAME_HalfPeriodNo);
		
		if (null == HalfPeriodNo)
			return "";
		
		MPeriod period = MPeriod.get(ctx, C_Period_ID);
		
		Calendar calendarStartPeriod = Calendar.getInstance();
		Calendar calendarEndPeriod = Calendar.getInstance();
		calendarStartPeriod.setTimeInMillis(period.getStartDate().getTime());
		calendarEndPeriod.setTimeInMillis(period.getEndDate().getTime());
		
		if (HalfPeriodNo.equals(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod1))
		{
			calendarEndPeriod.set(Calendar.DATE, 15);
		}
		else if (HalfPeriodNo.equals(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod2))
		{
			calendarStartPeriod.set(Calendar.DATE, 16);
		}
		Timestamp newStartDateOfPeriod = new Timestamp(calendarStartPeriod.getTimeInMillis());
		Timestamp newEndDateOfPeriod = new Timestamp(calendarEndPeriod.getTimeInMillis());
		mTab.setValue(MUNSHalfPeriodPresence.COLUMNNAME_StartDate, newStartDateOfPeriod);
		mTab.setValue(MUNSHalfPeriodPresence.COLUMNNAME_EndDate, newEndDateOfPeriod);		
		
		return "";
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
	protected String HalfPeriodNo(
			Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (null == value)
			return "";
		
		String HalfPeriodNo = (String) value;
		Integer C_Period_ID = (Integer)mTab.getValue(MUNSHalfPeriodPresence.COLUMNNAME_C_Period_ID);
		
		if (C_Period_ID.intValue() <= 0)
			return "Please Input Period";
		
		MPeriod period = MPeriod.get(ctx, C_Period_ID);
		
		Calendar calendarStartPeriod = Calendar.getInstance();
		Calendar calendarEndPeriod = Calendar.getInstance();
		calendarStartPeriod.setTimeInMillis(period.getStartDate().getTime());
		calendarEndPeriod.setTimeInMillis(period.getEndDate().getTime());
		
		if (HalfPeriodNo.equals(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod1))
		{
			calendarEndPeriod.set(Calendar.DATE, 15);
		}
		else if (HalfPeriodNo.equals(MUNSHalfPeriodPresence.HALFPERIODNO_HalfPeriod2))
		{
			calendarStartPeriod.set(Calendar.DATE, 16);
		}
		Timestamp newStartDateOfPeriod = new Timestamp(calendarStartPeriod.getTimeInMillis());
		Timestamp newEndDateOfPeriod = new Timestamp(calendarEndPeriod.getTimeInMillis());
		mTab.setValue(MUNSHalfPeriodPresence.COLUMNNAME_StartDate, newStartDateOfPeriod);
		mTab.setValue(MUNSHalfPeriodPresence.COLUMNNAME_EndDate, newEndDateOfPeriod);		
		
		return "";
	}
	
	protected String UNS_Employee (Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue)
	{
		if (null == value)
			return null;
		I_UNS_HalfPeriod_Presence halfPresence = 
				GridTabWrapper.create(mTab, I_UNS_HalfPeriod_Presence.class);
		I_UNS_Employee employee = halfPresence.getUNS_Employee();

		halfPresence.setVendor_ID(employee.getVendor_ID());
		
		MDocType[] payrollDocType = 
				MDocType.getOfDocBaseType(ctx, MDocType.DOCBASETYPE_PayrollEmployee);
		if (payrollDocType == null || payrollDocType.length < 2)
			throw new AdempiereException("You have not defined document type for Payroll Employee " +
					"and or Payroll Worker.");
		if(employee.getVendor_ID()==0) {
			int payDT = payrollDocType[0].get_ID();
			if (!payrollDocType[0].getName().equalsIgnoreCase("Payroll Employee"))
				payDT = payrollDocType[1].get_ID();
			halfPresence.setC_DocType_ID(payDT); //C_DocType of Payroll Employee
		}
		else {
			int payDT = payrollDocType[1].get_ID();
			if (!payrollDocType[1].getName().equalsIgnoreCase("Payroll Worker"))
				payDT = payrollDocType[1].get_ID();
			halfPresence.setC_DocType_ID(payDT); //Payroll Worker
		}
		halfPresence.setC_BPartner_ID(employee.getC_BPartner_ID());
		/*
		MUNSResourceWorkerLine rscWorker = MUNSResourceWorkerLine.getWorkerOfOrg(
				ctx, 
				halfPresence.getAD_Org_ID(), 
				halfPresence.getUNS_Employee_ID(), 
				null);
		*/
		MUNSResourceWorkerLine rscWorker = 
				new MUNSResource(ctx, halfPresence.getUNS_Resource_ID(), null)
				.getWorker(halfPresence.getUNS_Employee_ID());
		if(null == rscWorker)
			return null;
		halfPresence.setUNS_Job_Role_ID(rscWorker.getUNS_Job_Role_ID());
		return null;
	}

}
