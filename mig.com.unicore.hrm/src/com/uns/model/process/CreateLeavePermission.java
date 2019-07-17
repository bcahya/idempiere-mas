/**
 * 
 */
package com.uns.model.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSDispensationConfig;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSLeavePermissionTrx;
import com.uns.model.MUNSMedicalRecord;
import com.uns.model.MUNSMonthlyPresenceSummary;

/**
 * @author Inohtaf, Modified by ITD-Andy 14-08-2013, 28-08-2013
 * 
 */
public class CreateLeavePermission extends SvrProcess 
{
	private MUNSLeavePermissionTrx leavePermission;
	private MUNSMedicalRecord medRecord;
	private int medRecordID;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		for (ProcessInfoParameter param : getParameter()) 
		{
			String paramName = param.getParameterName();
			if (paramName.equalsIgnoreCase("UNS_MedicalRecord_ID")) {
				medRecordID = param.getParameterAsInt();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		String msg = null;
		leavePermission = new MUNSLeavePermissionTrx(getCtx(), 0, get_TrxName());
		medRecord = new MUNSMedicalRecord(getCtx(), medRecordID, get_TrxName());
		
		leavePermission.setAD_Org_ID(medRecord.getEmployeeDepID());
		leavePermission.setUNS_Employee_ID(medRecord.getUNS_Employee_ID());
		leavePermission.setC_Period_ID(medRecord.getC_Period_ID());
		
		if (medRecord.getleave_type_recommendation() != null) 
		{
			if (medRecord.getleave_type_recommendation()
					.equals(MUNSMedicalRecord.LEAVE_TYPE_RECOMMENDATION_SuratKeteranganIstirahat)) {
				leavePermission.setLeaveType(MUNSLeavePermissionTrx.LEAVETYPE_SuratKeteranganIstirahat);
			} 
			else if (medRecord.getleave_type_recommendation()
					.equals(MUNSMedicalRecord.LEAVE_TYPE_RECOMMENDATION_SuratKeteranganIstirahatKecelakaanKerja)) {
				leavePermission.setLeaveType(MUNSLeavePermissionTrx.LEAVETYPE_SuratKeteranganIstirahatKecelakaanKerja);
			} 
			else if (medRecord.getleave_type_recommendation()
					.equals(MUNSMedicalRecord.LEAVE_TYPE_RECOMMENDATION_SuratKeteranganIstirahatKeguguran)) {
				leavePermission.setLeaveType(MUNSLeavePermissionTrx.LEAVETYPE_PermissionDispensationIzinDibayar);
				
				int dispensationID = MUNSDispensationConfig.getIDOf("KGR", get_TrxName());
				if (dispensationID <= 0)
					throw new AdempiereException("Dispensation Configuration of Keguguran has not beet defined yet.");
				leavePermission.setUNS_DispensationConfig_ID(dispensationID);
			}
		}
		
		leavePermission.setJobCareTaker_ID(0);
		leavePermission.setIsApproved(false);
		leavePermission.setLeaveDateStart(medRecord.getsl_recommend_startdate());
		leavePermission.setLeaveDateEnd(medRecord.getsl_recommend_enddate());
		
		MUNSMonthlyPresenceSummary monthly = MUNSMonthlyPresenceSummary.getCreate(
				getCtx(), (MUNSEmployee) medRecord.getUNS_Employee(), medRecord.getC_Period_ID(), null, null, get_TrxName());
		
		if (monthly == null) {
			msg = "Failed create leave permission, employee's monthly summary has not been created.";
		} 
		else {
			leavePermission.setUNS_YearlyPresenceSummary_ID(monthly.getUNS_YearlyPresenceSummary_ID());
			leavePermission.setC_Year_ID(monthly.getUNS_YearlyPresenceSummary().getC_Year_ID());
			leavePermission.setEmploymentType(medRecord.getUNS_Employee().getEmploymentType());
			leavePermission.saveEx();
			msg = "Leave Permission document is created with number " + leavePermission.getDocumentNo();
		}

		return msg;
	}
}
