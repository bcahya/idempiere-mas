/**
 * 
 */
package com.uns.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAccount;
import org.compiere.model.MPeriod;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSHRMModelFactory;



/**
 * @author eko
 *
 */
public class MUNSPayrollConfiguration extends X_UNS_PayrollConfiguration implements DocAction, DocOptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_processMsg = null;
	private MUNSPayrollConfiguration[] m_lines = null;
	private boolean m_justPrepared = false;

	/**
	 * @param ctx
	 * @param UNS_PayrollConfiguration_ID
	 * @param trxName
	 */
	public MUNSPayrollConfiguration(Properties ctx,
			int UNS_PayrollConfiguration_ID, String trxName) {
		super(ctx, UNS_PayrollConfiguration_ID, trxName);
		// 
	}
	
	
	/**
	 * 
	 * @param ctx
	 * @param period
	 * @param AD_Org_ID
	 * @param trxName
	 * @return MUNSPayrollConfiguration
	 */
	public static MUNSPayrollConfiguration get(Properties ctx, MPeriod period,String trxName)
	{
		Timestamp startDate = period.getStartDate();
		Timestamp endDate = period.getEndDate();
		MUNSPayrollConfiguration payConfig = null;
		final String whereClause = "'" + startDate + "' BETWEEN " + COLUMNNAME_ValidFrom 
				+ " AND " + COLUMNNAME_ValidTo 
				+ " AND '" + endDate + "' BETWEEN " + COLUMNNAME_ValidFrom + " AND " 
				+ COLUMNNAME_ValidTo + " AND "
				+ COLUMNNAME_IsActive + " = 'Y'";
		payConfig = Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, whereClause, trxName).firstOnly();
		return payConfig;
	}	
	
	/**
	 * 
	 * @param ctx
	 * @param startDate
	 * @param trxName
	 * @return
	 */
	public static MUNSPayrollConfiguration get(Properties ctx, Timestamp startDate, String trxName)
	{
		final String whereClause = "'" + startDate + "' BETWEEN " + COLUMNNAME_ValidFrom 
				+ " AND " + COLUMNNAME_ValidTo 
				+ " AND " + COLUMNNAME_IsActive + " = 'Y'";
		return Query.get(
				ctx, UNSHRMModelFactory.EXTENSION_ID, Table_Name, whereClause, trxName).firstOnly();
		
		/*
		String query = "SELECT UNS_PayrollConfiguration_id FROM UNS_PayrollConfiguration"
			       + " where validfrom <= '"+startDate+"' and validto > '" +startDate+"'";
	
		PreparedStatement pst = null;
		int payrollConfigID = 0;
		
		try {
		    pst = DB.prepareStatement(query, trxName);
		    ResultSet rs = pst.executeQuery();
		    if(rs == null)
		    	throw new AdempiereUserError("Error Input Period");
		    if(rs.next())
		        payrollConfigID = rs.getInt(1);
		    pst.close();
		    rs.close();
		    pst = null;
		}catch(SQLException ex) {
			// do nothing
		}	
	
		MUNSPayrollConfiguration pc = new MUNSPayrollConfiguration(ctx, payrollConfigID, trxName);
		
		return pc;
		*/
	}
	
	
	@Override
	protected boolean beforeSave(boolean newRecord){
	
		if (newRecord)
			if (!isValidDate())
				throw new AdempiereUserError("Range of Valid date already exist");
		
		return super.beforeSave(newRecord);
	}
	
	private boolean isValidDate()
	{
		String sql = "SELECT * FROM UNS_PayRollConfiguration prc WHERE '" + getValidFrom() + 
				"' BETWEEN prc.ValidFrom  AND prc.ValidTo OR '" + getValidTo() + 
				"'  BETWEEN  prc.ValidFrom AND prc.ValidTo AND UNS_PayrollConfiguration_ID <> "
				+ getUNS_PayrollConfiguration_ID();
		
		int count = DB.getSQLValue(get_TrxName(), sql);
		if (count == -1)
			return true;
		
		return false;
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPayrollConfiguration(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// 
	}
	
	public MUNSPayrollConfiguration[] getLines(String whereClause){
		String whereClauseFinal = "";
		if (whereClause != null)
			whereClauseFinal += whereClause;
		
		List<MUNSPayrollConfiguration> list = Query.get(getCtx(), UNSHRMModelFactory.getExtensionID(), 
											MUNSPayrollConfiguration.Table_Name, whereClauseFinal, get_TrxName())
											.setOrderBy(MUNSPayrollConfiguration.COLUMNNAME_UNS_PayrollConfiguration_ID)
											.list();
		return list.toArray(new MUNSPayrollConfiguration[list.size()]);
	}
	
	public MUNSPayrollConfiguration[] getLines(){
		
		if(m_lines == null)
			m_lines = getLines(null);
		
		return m_lines;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocOptions#customizeValidActions
	 * (java.lang.String, java.lang.Object, java.lang.String, java.lang.String
	 * , int, java.lang.String[], java.lang.String[], int)
	 */
	@Override
	public int customizeValidActions(String docStatus, Object processing,
			String orderType, String isSOTrx, int AD_Table_ID,
			String[] docAction, String[] options, int index) {
		// 
		if (docStatus.equals(DocumentEngine.STATUS_Drafted)
    			|| docStatus.equals(DocumentEngine.STATUS_Invalid)) {
    		options[index++] = DocumentEngine.ACTION_Prepare;
    	}
    	
    	// If status = Completed, add "Reactivte" in the list
    	if (docStatus.equals(DocumentEngine.STATUS_Completed)) {
    		options[index++] = DocumentEngine.ACTION_Reverse_Correct;
    		options[index++] = DocumentEngine.ACTION_Void;
    	}   	
    		
    	return index;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#processIt(java.lang.String)
	 */
	@Override
	public boolean processIt(String action) throws Exception {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (action, getDocAction());
		// 
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#unlockIt()
	 */
	@Override
	public boolean unlockIt() {
		log.info(toString());
		setProcessed(false);
		// 
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#invalidateIt()
	 */
	@Override
	public boolean invalidateIt() {
		log.info(toString());
		setDocAction(DOCACTION_Prepare);
		// 
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#prepareIt()
	 */
	@Override
	public String prepareIt() {
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
			
				m_justPrepared = true;
				
			if (!DOCACTION_Complete.equals(getDocAction()))
				setDocAction(DOCACTION_Complete);
			setProcessed(true);
			return DocAction.STATUS_InProgress;
		}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#approveIt()
	 */
	@Override
	public boolean approveIt() {
		log.info(toString());
		setIsApproved(true);
		// 
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#rejectIt()
	 */
	@Override
	public boolean rejectIt() {
		log.info(toString());
		setIsApproved(false);
		// 
		return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#completeIt()
	 */
	@Override
	public String completeIt() {
		// 
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		log.info(toString());
		
//		Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		setProcessed(true);	
		//m_processMsg = info.toString();
		//
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#voidIt()
	 */
	@Override
	public boolean voidIt() {
		// 
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#closeIt()
	 */
	@Override
	public boolean closeIt() {// 
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseCorrectIt()
	 */
	@Override
	public boolean reverseCorrectIt() {
		// 
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reverseAccrualIt()
	 */
	@Override
	public boolean reverseAccrualIt() {
		// 
		return false;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#reActivateIt()
	 */
	@Override
	public boolean reActivateIt() {
		// 
				log.info(toString());
				// Before reActivate
				m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
				if (m_processMsg != null)
					return false;
				
				m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
				if (m_processMsg != null)
					return false;
				
				setDocAction(DOCACTION_Complete);
				setProcessed(false);
				return true;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getSummary()
	 */
	@Override
	public String getSummary() {
		// 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentNo()
	 */
	@Override
	public String getDocumentNo() {
		// 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDocumentInfo()
	 */
	@Override
	public String getDocumentInfo() {
		// 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#createPDF()
	 */
	@Override
	public File createPDF() {
		// 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getProcessMsg()
	 */
	@Override
	public String getProcessMsg() {
		// 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getDoc_User_ID()
	 */
	@Override
	public int getDoc_User_ID() {
		// 
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getC_Currency_ID()
	 */
	@Override
	public int getC_Currency_ID() {
		// 
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.DocAction#getApprovalAmt()
	 */
	@Override
	public BigDecimal getApprovalAmt() {
		// 
		return null;
	}
	
	@Override
	public void setDocStatus(String newStatus) {
		// 
		
	}


	@Override
	public String getDocStatus() {
		// 
		return null;
	}
	

	@Override
	public String getDocAction() {
		// 
		return null;
	}
	
	public BigDecimal getPPH21Worker(MUNSHalfPeriodPresence halfPresence)
	{
		BigDecimal pph1TH = BigDecimal.ZERO;
		BigDecimal ptkp = BigDecimal.ZERO;
		double pphLevel1 = getPajakLevel1().doubleValue()/100;
		double pphLevel2 = getPajakLevel2().doubleValue()/100;
		double pphLevel3 = getPajakLevel3().doubleValue()/100;
		double pphLevel4 = getPajakLevel4().doubleValue()/100;
		double pphLevel5 = getPajakLevel5().doubleValue()/100;
		double pajakNonNPWP = getPajakNonNPWP().doubleValue();
		
		MUNSEmployee employee = (MUNSEmployee)halfPresence.getUNS_Employee();
		if (null == employee.getMaritalStatus())
			throw new AdempiereUserError("Please Define marital status of employee ");
		if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_Kawin0Tanggungan))
			ptkp = getK0();
		else if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_Kawin1Tanggungan))
			ptkp = getK1();
		else if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_Kawin2Tanggungan))
			ptkp = getK2();
		else if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_Kawin3Tanggungan))
			ptkp = getK3();
		else if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_TidakKawin0Tanggungan))
			ptkp = getTK0();
		else if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_TidakKawin1Tanggungan))
			ptkp = getTK1();
		else if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_TidakKawin2Tanggungan))
			ptkp = getTK2();
		
		double gajiBruto = halfPresence.getGajiBruto();
		double biayaJabatanPercent = getBiayaJabatan().doubleValue();
		double biayaJabatan = gajiBruto * biayaJabatanPercent / 100;
		biayaJabatan *= 12;
		if(biayaJabatan > getMaxBiayaJabatan().doubleValue())
			biayaJabatan = getMaxBiayaJabatan().doubleValue();
		double gajiNet = gajiBruto - (biayaJabatan + halfPresence.getPotongan());
		double gajiNet1Th = gajiNet * 12;
		
		if (gajiNet1Th <= ptkp.doubleValue())
			return pph1TH;
		
		double pkp = gajiNet1Th - ptkp.doubleValue();
		double pphTerutang = 0.0;
		
		if (pkp <= getLevel1().doubleValue())
		{
			pphTerutang = pkp * pphLevel1;
		}
		if (pkp > getLevel1().doubleValue() 
				&& pkp <= getLevel2().doubleValue())
		{
			double pph1 = getLevel1().doubleValue() * pphLevel1;
			double pph2 = pkp - getLevel1().doubleValue();
			pph2 *= pphLevel2;
			pphTerutang = pph1 + pph2;
		}
		if (pkp > getLevel2().doubleValue() 
				&& pkp <= getLevel3().doubleValue())
		{
			double pph1 = getLevel1().doubleValue() * pphLevel1;
			double pph2 = pkp - getLevel1().doubleValue();
			double pph3 = pph2 - getLevel3().doubleValue();
			pph2 *= pphLevel2;
			pph3 *= pphLevel3;
			pphTerutang = pph1 + pph2 + pph3;
		}
		else if (pkp > getLevel3().doubleValue() && pkp <= getLevel4().doubleValue())
		{
			double pph1 = getLevel1().doubleValue() * pphLevel1;
			double pph2 = pkp - getLevel1().doubleValue();
			double pph3 = pph2 - getLevel3().doubleValue();
			double pph4 = pph3 - getLevel4().doubleValue();
			pph2 *= pphLevel2;
			pph3 *= pphLevel3;
			pph4 *= pphLevel4;
			pphTerutang = pph1 + pph2 + pph3 + pph4;
		}
		else if (pkp >= getLevel5().doubleValue())
		{
			double pph1 = getLevel1().doubleValue() * pphLevel1;
			double pph2 = pkp - getLevel1().doubleValue();
			double pph3 = pph2 - getLevel3().doubleValue();
			double pph4 = pph3 - getLevel4().doubleValue();
			double pph5 = pph4 - getLevel5().doubleValue();
			pph2 *= pphLevel2;
			pph3 *= pphLevel3;
			pph4 *= pphLevel4;
			pph5 *= pphLevel5;
			pphTerutang = pph1 + pph2 + pph3 + pph4 + pph5;
		}
		
		double pph21 = pphTerutang / 12;
		if(null == employee.getNPWP() || "".equals(employee.getNPWP()))
			pph21 = pph21 * (pajakNonNPWP/100);
		
		return new BigDecimal(pph21);
	}
	
	/**
	 * Get PPH For Employee
	 * @param payrollEmployee
	 * @return BigDecimal PPH
	 */
	public BigDecimal getPPH(MUNSPayrollEmployee payrollEmployee)
	{
		BigDecimal pph1TH = BigDecimal.ZERO;
		BigDecimal ptkp = BigDecimal.ZERO;
		double pphLevel1 = getPajakLevel1().doubleValue()/100;
		double pphLevel2 = getPajakLevel2().doubleValue()/100;
		double pphLevel3 = getPajakLevel3().doubleValue()/100;
		double pphLevel4 = getPajakLevel4().doubleValue()/100;
		double pphLevel5 = getPajakLevel5().doubleValue()/100;
		double pajakNonNPWP = getPajakNonNPWP().doubleValue();
		
		MUNSEmployee employee = (MUNSEmployee)payrollEmployee.getUNS_Employee();
		if (null == employee.getMaritalStatus())
			throw new AdempiereUserError("Please Define marital status of employee ");
		if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_Kawin0Tanggungan))
			ptkp = getK0();
		else if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_Kawin1Tanggungan))
			ptkp = getK1();
		else if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_Kawin2Tanggungan))
			ptkp = getK2();
		else if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_Kawin3Tanggungan))
			ptkp = getK3();
		else if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_TidakKawin0Tanggungan))
			ptkp = getTK0();
		else if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_TidakKawin1Tanggungan))
			ptkp = getTK1();
		else if (employee.getMaritalStatus().equals(MUNSEmployee.MARITALSTATUS_TidakKawin2Tanggungan))
			ptkp = getTK2();
		
		double gajiBruto = payrollEmployee.getPPHBrutoAmt().doubleValue();
		double biayaJabatanPercent = getBiayaJabatan().doubleValue();
		double biayaJabatan = gajiBruto * biayaJabatanPercent / 100;
//		biayaJabatan *= 12;
		if(biayaJabatan > getMaxBiayaJabatan().doubleValue())
			biayaJabatan = getMaxBiayaJabatan().doubleValue();
		double gajiNet =  (payrollEmployee.getPPHBrutoAmt().subtract(payrollEmployee.getPPHDeducAmt())).doubleValue();//gajiBruto - (biayaJabatan + payrollEmployee.getPotongan().doubleValue());
		gajiNet = gajiNet - biayaJabatan;
		double gajiNet1Th = gajiNet * 12;
		
		if (gajiNet1Th <= ptkp.doubleValue())
			return pph1TH;
		
		double pkp = gajiNet1Th - ptkp.doubleValue();
		double pphTerutang = 0.0;
		int pkpBulet = (int) (pkp / 1000);
		pkpBulet = pkpBulet * 1000;
		pkp= pkpBulet;
		
		if (pkp <= getLevel1().doubleValue())
		{
			pphTerutang = pkp * pphLevel1;
		}
		if (pkp > getLevel1().doubleValue() 
				&& pkp <= getLevel2().doubleValue())
		{
			double pph1 = getLevel1().doubleValue() * pphLevel1;
			double pph2 = pkp - getLevel1().doubleValue();
			pph2 *= pphLevel2;
			pphTerutang = pph1 + pph2;
		}
		if (pkp > getLevel2().doubleValue() 
				&& pkp <= getLevel3().doubleValue())
		{
			double pph1 = getLevel1().doubleValue() * pphLevel1;
			double pph2 = getLevel2().doubleValue() - getLevel1().doubleValue();
			double pph3 = pkp - getLevel2().doubleValue();
			pph2 *= pphLevel2;
			pph3 *= pphLevel3;
			pphTerutang = pph1 + pph2 + pph3;
		}
		else if (pkp > getLevel3().doubleValue() && pkp <= getLevel4().doubleValue())
		{
			double pph1 = getLevel1().doubleValue() * pphLevel1;
			double pph2 = getLevel2().doubleValue() - getLevel1().doubleValue();
			double pph3 = getLevel3().doubleValue() - getLevel2().doubleValue();
			double pph4 = pkp - getLevel3().doubleValue();
			pph2 *= pphLevel2;
			pph3 *= pphLevel3;
			pph4 *= pphLevel4;
			pphTerutang = pph1 + pph2 + pph3 + pph4;
		}
		else if (pkp > getLevel5().doubleValue())
		{
			double pph1 = getLevel1().doubleValue() * pphLevel1;
			double pph2 = getLevel2().doubleValue() - getLevel1().doubleValue();
			double pph3 = getLevel3().doubleValue() - getLevel2().doubleValue();
			double pph4 = getLevel4().doubleValue() - getLevel3().doubleValue();
			double pph5 = pkp - getLevel5().doubleValue();
			pph2 *= pphLevel2;
			pph3 *= pphLevel3;
			pph4 *= pphLevel4;
			pph5 *= pphLevel5;
			pphTerutang = pph1 + pph2 + pph3 + pph4 + pph5;
		}
		
		double pph21 = pphTerutang / 12;
		if(null == employee.getNPWP() || "".equals(employee.getNPWP()))
			pph21 = pph21 * (pajakNonNPWP/100);
		
		return new BigDecimal(pph21);
	}
	

	/**
	 * 
	 * @param payrollLevel
	 * @param payrollType (Employment status list Reference)
	 * @return
	 */
	public MUNSPayrollLevelConfig getPayrollLevel(String payrollLevel, String payrollTerm)
	{
		return Query.get(
				getCtx(), UNSHRMModelFactory.getExtensionID(), 
				MUNSPayrollLevelConfig.Table_Name, 
				COLUMNNAME_UNS_PayrollConfiguration_ID 
				+ " = " + getUNS_PayrollConfiguration_ID() 
				+ " AND " + MUNSPayrollLevelConfig.COLUMNNAME_PayrollLevel + " = '" + payrollLevel + "'"
				+ " AND " + MUNSPayrollLevelConfig.COLUMNNAME_PayrollTerm + " = '" + payrollTerm + "'", 
				get_TrxName())
				.firstOnly();
	}
	
	/**
	 * Get C_ValidCombination for HutangUpahBuruhDirect.
	 * 
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public static MAccount getHutangUpahBuruhDirectAcct(Properties ctx, String trxName)
	{
		return (MAccount) getCurrentActive(ctx, trxName).getHutangUpahBuruhDirectAcct();
	}
	
	/**
	 * Get C_ValidCombination for HutangGajiBulanan.
	 * 
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public static MAccount getHutangGajiBulananAcct(Properties ctx, String trxName)
	{
		return (MAccount) getCurrentActive(ctx, trxName).getHutangGajiBulananAcct();
	}
	
	/**
	 * Get C_ValidCombination for HutangGajiHarian.
	 * 
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public static MAccount getHutangGajiHarianAcct(Properties ctx, String trxName)
	{
		return (MAccount) getCurrentActive(ctx, trxName).getHutangGajiHarianAcct();
	}
	
	/**
	 * Get currently (based on current Timestamp) active payroll configuration. 
	 * @param ctx
	 * @param trxName
	 * @return
	 */
	public static MUNSPayrollConfiguration getCurrentActive(Properties ctx, String trxName)
	{
		MUNSPayrollConfiguration payConf = 
				Query.get(ctx, UNSHRMModelFactory.getExtensionID(), Table_Name, 
						"Now() BETWEEN ValidFrom AND ValidTo AND IsActive='Y'", 
						trxName)
					.firstOnly();
		return payConf;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param columnNameAcct
	 * @param trxName
	 * @return
	 */
	public static MAccount getAccountOf (Properties ctx, String columnNameAcct, String trxName)
	{
		String sql = "SELECT " + columnNameAcct + " FROM UNS_PayrollConfiguration " +
				" WHERE now() BETWEEN ValidFrom AND ValidTo" ;
		
		int validCombinationID = DB.getSQLValue(trxName, sql);
		
		if (validCombinationID <= 0)
			throw new AdempiereException("Cannot find account default for " + columnNameAcct) ;
		
		return MAccount.get(ctx, validCombinationID);
	}
	
	private int m_Period_ID = 0;
	private Timestamp m_startDate = null;
	private Timestamp m_endDate = null;
	
	
	public void initPayrollPeriodOf (Timestamp date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		
		int start = getPayrollDateStart();
		int end = getPayrollDateEnd();
		int maxDay = calendar.getMaximum(Calendar.DAY_OF_MONTH);
		int median = maxDay / 2;
		int curDate = calendar.get(Calendar.DATE);
		int period = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.DATE, start);
		
		if (start > median)
		{
			if (curDate >= start)
			{
				m_startDate = new Timestamp(calendar.getTimeInMillis());
				calendar.add(Calendar.MONTH, 1);
				calendar.set(Calendar.DATE, end);
				m_endDate = new Timestamp(calendar.getTimeInMillis());
				period = calendar.get(Calendar.MONTH);
				year = calendar.get(Calendar.YEAR);
			}
			else
			{
				calendar.add(Calendar.MONTH, -1);
				m_startDate = new Timestamp(calendar.getTimeInMillis());
				calendar.add(Calendar.MONTH, 1);
				calendar.set(Calendar.DATE, end);
				m_endDate = new Timestamp(calendar.getTimeInMillis());
			}
		}
		else 
		{
			if (curDate < start)
			{
				calendar.add(Calendar.MONTH, -1);
				m_startDate = new Timestamp(calendar.getTimeInMillis());
				calendar.add(Calendar.MONTH, 1);
				calendar.set(Calendar.DATE, end);
				m_endDate = new Timestamp(calendar.getTimeInMillis());
				period = calendar.get(Calendar.MONTH);
				year = calendar.get(Calendar.YEAR);
			}
		}
		
		period++;
		String sql = "SELECT C_Year_ID FROM C_Year WHERE FiscalYear = ? ";
		int C_Year_ID = DB.getSQLValue(get_TrxName(), sql, "" + year);
		if (C_Year_ID <= 0)
		{
			throw new AdempiereUserError("Cant' find year " + year);
		}
		
		sql = "SELECT C_Period_ID FROM C_Period WHERE PeriodNo = ? "
				+ " AND C_Year_ID = ? ";
		
		m_Period_ID = DB.getSQLValue(
				get_TrxName(), sql, period, C_Year_ID);
	}
	
	public void initPayrollPeriodOf (int period_ID)
	{
		MPeriod period = MPeriod.get(getCtx(), period_ID);
		Calendar cal = TimeUtil.getCalendar(period.getStartDate());
		
		int startConfig = getPayrollDateStart();
		int endConfig = getPayrollDateEnd();
		
		cal.set(Calendar.DATE, startConfig);
		
		int daysOfMonth = cal.getActualMaximum(Calendar.DATE);
		int median = daysOfMonth / 2;
		
		if (startConfig > median)
		{
			cal.add(Calendar.MONTH, -1);
		}
		
		m_startDate = new Timestamp(cal.getTimeInMillis());
		
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, endConfig);
		
		m_endDate = new Timestamp(cal.getTimeInMillis());
		
	}
	
	public int getC_Period_ID ()
	{
		return this.m_Period_ID;
	}
	
	public Timestamp getStartDate ()
	{
		return this.m_startDate;
	}
	
	public Timestamp getEndDate ()
	{
		return this.m_endDate;
	}
}
