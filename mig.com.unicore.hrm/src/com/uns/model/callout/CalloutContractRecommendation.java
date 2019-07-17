/**
 * 
 */
package com.uns.model.callout;

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;

import com.uns.model.I_UNS_Contract_Recommendation;
import com.uns.model.I_UNS_Employee;
import com.uns.model.MUNSContractRecommendation;
import com.uns.model.MUNSPayrollTermConfig;

/**
 * @author menjangan
 *
 */
public class CalloutContractRecommendation implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutContractRecommendation() {
		
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retval = null;
		if (null == value)
			return retval;
		
		if (mField.getColumnName().equals(MUNSContractRecommendation.COLUMNNAME_UNS_Employee_ID))
			employee(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSContractRecommendation.COLUMNNAME_AD_Org_ID))
			AD_Org(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSContractRecommendation.COLUMNNAME_DateContractEnd))
			retval = checkDateEnd(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSContractRecommendation.COLUMNNAME_NewSectionOfDept_ID))
			retval = C_BPartner_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSContractRecommendation.COLUMNNAME_NextContractType))
			retval = ContractType(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MUNSContractRecommendation.COLUMNNAME_DateContractEnd))
			retval = dateContractEnd(ctx, WindowNo, mTab, mField, value, oldValue);
		
		return retval;
	}
	/** @author ITD-Andy */
	private String checkDateEnd(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retval = null;
		
		if(null == value || 
				mTab.getValue(MUNSContractRecommendation.COLUMNNAME_DateContractStart)==null)
			return null;
		
		Timestamp dateStart = (Timestamp) mTab.getValue(MUNSContractRecommendation.COLUMNNAME_DateContractStart);
		Timestamp dateEnd = (Timestamp) mTab.getValue(MUNSContractRecommendation.COLUMNNAME_DateContractStart);
		
		if(dateEnd.before(dateStart))
			retval = "Invalid date, DateEnd must be greater than DateStart!";
		
		return retval;
	}
	/** end */
	public String employee(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue)
	{
		if(null == value | value=="")
			return null;

		I_UNS_Contract_Recommendation recommendation = 
				GridTabWrapper.create(mTab, I_UNS_Contract_Recommendation.class);
		I_UNS_Employee employee = recommendation.getUNS_Employee();
		
		recommendation.setNewNIK(employee.getValue());
		recommendation.setNewSectionOfDept_ID(employee.getC_BPartner_ID());
		recommendation.setEmploymentType(employee.getEmploymentType());
		
		return null;
	}
	
	public String AD_Org(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue)
	{
		if(null == value)
			return null;
		Integer AD_Org_ID = (Integer)value;
		mTab.setValue(MUNSContractRecommendation.COLUMNNAME_NewDept_ID, AD_Org_ID);
		return null;
	}
	
	public String C_BPartner_ID(
			Properties ctx, int windowNo
			, GridTab mTab, GridField mField, Object value, Object oldVal)
	{
		if(null == value | value=="")
			return null;

		I_UNS_Contract_Recommendation recommendation = 
				GridTabWrapper.create(mTab, I_UNS_Contract_Recommendation.class);
		initialPayrollTerm(ctx, recommendation);
		
		return "";
	}
	
	public String ContractType(
			Properties ctx, int windowNo, GridTab mTab
			, GridField mField, Object value, Object oldValue)
	{
		if(null == value | value=="")
			return null;

		I_UNS_Contract_Recommendation recommendation = 
				GridTabWrapper.create(mTab, I_UNS_Contract_Recommendation.class);
		
		initialPayrollTerm(ctx, recommendation);
		return "";
	}
	
	
	/**
	 * 
	 * @param recommendation
	 * @return
	 */
	public String initialPayrollTerm(Properties ctx, I_UNS_Contract_Recommendation recommendation)
	{
		if(recommendation.getAD_Org_ID() <= 0)
			return "";
		if(recommendation.getNewSectionOfDept_ID() <= 0)
			return "";
		else if(recommendation.getNextContractType() == null)
			return "";
		else if(recommendation.getDateContractEnd() == null)
			return "";
		
		//setPayroll Term
		String payrollTerm = MUNSPayrollTermConfig.getPayrollTermOf(
				recommendation.getAD_Org_ID(),
				recommendation.getNewSectionOfDept_ID(), 
				recommendation.getNextContractType()
				,Env.getContextAsDate(ctx, "Date")
				, null);
		
		if(null == payrollTerm)
			return "";
		
		recommendation.setNextPayrollTerm(payrollTerm);
		
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
	public String dateContractEnd(
			Properties ctx, int WindowNo, GridTab mTab
			, GridField mField, Object value, Object oldValue)
	{
		if(null == value | value=="")
			return null;

		I_UNS_Contract_Recommendation recommendation = 
				GridTabWrapper.create(mTab, I_UNS_Contract_Recommendation.class);
		
		initialPayrollTerm(ctx, recommendation);
		return "";
	}
}
