/**
 * 
 */
package com.uns.base.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrg;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MUNSEmployeeLoan;
import com.uns.model.MUNSMedicalRecord;
import com.uns.model.factory.UNSHRMModelFactory;

/**
 * @author Haryadi
 *
 */
public class MInvoice extends org.compiere.model.MInvoice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 446549441894508710L;

	/**
	 * @param ctx
	 * @param C_Invoice_ID
	 * @param trxName
	 */
	public MInvoice(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	
	/**
	 * 
	 * @return
	 */
	public MUNSMedicalRecord[] getAllMedicalReferrals()
	{
		MUNSMedicalRecord[] retMedRefs = null;
		
		String whereClause = "C_InvoiceLine_ID IN (SELECT invLine.C_InvoiceLine_ID FROM C_InvoiceLine invLine " +
				" WHERE invLine.C_Invoice_ID=" + get_ID() + ")";
		
		List<MUNSMedicalRecord> medRecList = Query.get(
				getCtx(), UNSHRMModelFactory.EXTENSION_ID, MUNSMedicalRecord.Table_Name, whereClause, get_TrxName())
				.list();
		
		retMedRefs = new MUNSMedicalRecord[medRecList.size()];
		medRecList.toArray(retMedRefs);
		
		return retMedRefs;
	}
	
	@Override
	public String prepareIt()
	{
		String superStatus = super.prepareIt();
		if (DOCSTATUS_Drafted.equals(superStatus) || DOCSTATUS_Invalid.equals(superStatus))
			return superStatus;
		
		MInvoiceLine[] invLines = getLines();
		
		for (MInvoiceLine iLine : invLines)
		{
			String sql = "SELECT sum(MedicalCosts) FROM UNS_MedicalRecord WHERE C_InvoiceLine_ID=?";
			BigDecimal totalLineCosts = DB.getSQLValueBD(get_TrxName(), sql, iLine.get_ID());
			
			if (totalLineCosts == null || totalLineCosts.signum() <= 0)
				throw new AdempiereException("Cannot process null or zero cost of total employee costs.");
			
			if (totalLineCosts.compareTo(iLine.getLineTotalAmt()) != 0)
				throw new AdempiereException("Line amount (line no " + iLine.getLine() + 
						") and total employee costs not equal [" + iLine.getLineTotalAmt() + " & " + 
						totalLineCosts.setScale(2, RoundingMode.HALF_UP));
		}
		
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public String completeIt()
	{
		String superStatus = super.completeIt();
		if (!DOCSTATUS_Completed.equals(superStatus)) {
			return superStatus;
		}
		
		MUNSMedicalRecord[] allMedicals = getAllMedicalReferrals();
		MOrg psn = MOrg.get(getCtx(), "PSN", get_TrxName());
		int dtId = MDocType.getDocType(MDocType.DOCBASETYPE_LoanInstallment);
		
		for (MUNSMedicalRecord employeeRec : allMedicals)
		{
			if (employeeRec.getUNS_Employee_ID() == 0)
				continue;
			
			MUNSEmployeeLoan employeeLoan = new MUNSEmployeeLoan(getCtx(), 0, get_TrxName());
			
			employeeLoan.setAD_Org_ID(psn.get_ID());
			employeeLoan.setUNS_Employee_ID(employeeRec.getUNS_Employee_ID());
			employeeLoan.setC_DocType_ID(dtId);
			employeeLoan.setLoanType(MUNSEmployeeLoan.LOANTYPE_Medical);
			employeeLoan.setUNS_MedicalRecord_ID(employeeRec.get_ID());
			employeeLoan.setInstallment(employeeRec.getMedicalCosts());
			employeeLoan.setInstallmentPeriod(1);
			employeeLoan.setMedicalCosts(employeeRec.getMedicalCosts());
			employeeLoan.setLoanAmt(employeeRec.getMedicalCosts());
			employeeLoan.setLoanAmtLeft(employeeRec.getMedicalCosts());
			employeeLoan.setRequestDate(employeeRec.getmedical_date());
			employeeLoan.setTrxDate(getDateAcct());
			employeeLoan.setPaidPeriod(0);
			employeeLoan.setTotalPaid(Env.ZERO);
			employeeLoan.setReason("Hospital Referral invoiced to the employee for medical record no " 
								 + employeeRec.getDocumentNo());
			employeeLoan.saveEx();
			
			employeeRec.setDocStatus(DOCSTATUS_Completed);
			employeeRec.setProcessed(true);
			employeeRec.saveEx();
		}
		
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}
}
