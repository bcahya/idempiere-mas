/**
 * 
 */
package com.uns.model.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.uns.model.I_UNS_Contract_Evaluation;
import com.uns.model.I_UNS_Contract_Recommendation;
import com.uns.model.MUNSContractEvaluation;
import com.uns.model.MUNSEmployee;
import com.uns.model.X_UNS_Contract_Evaluation;

/**
 * @author eko
 *
 */
public class CalloutContractEvaluation implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutContractEvaluation() {

	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(
	 * java.util.Properties, int, org.compiere.model.GridTab
	 * , org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
	
		if(null == value)
			return null;
		
		I_UNS_Contract_Evaluation contractEvaluation = 
				GridTabWrapper.create(mTab, I_UNS_Contract_Evaluation.class);
		MUNSEmployee m_employee = null;
		
		
		if (contractEvaluation.getUNS_Employee_ID() > 0)
			m_employee = MUNSEmployee.get(ctx, contractEvaluation.getUNS_Employee_ID());
		
		if(mField.getColumnName().equals(I_UNS_Contract_Evaluation.COLUMNNAME_TotalGrade)
				&& m_employee == null)
			return null;
		
		if(mField.getColumnName().equals(I_UNS_Contract_Evaluation.COLUMNNAME_TotalGrade)
			&& m_employee.getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_SubContract))
		{
			if(contractEvaluation.getTotalGrade().compareTo(new BigDecimal(60))<0)
				contractEvaluation.setGrade(X_UNS_Contract_Evaluation.GRADE_D);
			else if (contractEvaluation.getTotalGrade().compareTo(new BigDecimal(65))<=0)
				contractEvaluation.setGrade(X_UNS_Contract_Evaluation.GRADE_C);
			else if (contractEvaluation.getTotalGrade().compareTo(new BigDecimal(75))<=0)
				contractEvaluation.setGrade(X_UNS_Contract_Evaluation.GRADE_B);
			else if(contractEvaluation.getTotalGrade().compareTo(new BigDecimal(76))>=0)
				contractEvaluation.setGrade(X_UNS_Contract_Evaluation.GRADE_A);
		}
		else if(mField.getColumnName().equals(I_UNS_Contract_Evaluation.COLUMNNAME_TotalGrade)
				&& m_employee.getEmploymentType().equals(MUNSEmployee.EMPLOYMENTTYPE_Company))
		{
			if (contractEvaluation.getTotalGrade().compareTo(new BigDecimal(1.8)) < 0)
				contractEvaluation.setGrade(X_UNS_Contract_Evaluation.GRADE_E);
			else if (contractEvaluation.getTotalGrade().compareTo(new BigDecimal(2.6)) < 0)
				contractEvaluation.setGrade(X_UNS_Contract_Evaluation.GRADE_D);
			else if (contractEvaluation.getTotalGrade().compareTo(new BigDecimal(3.4)) < 0)
				contractEvaluation.setGrade(X_UNS_Contract_Evaluation.GRADE_C);
			else if (contractEvaluation.getTotalGrade().compareTo(new BigDecimal(4.2)) < 0)
				contractEvaluation.setGrade(X_UNS_Contract_Evaluation.GRADE_B);
			else if (contractEvaluation.getTotalGrade().compareTo(new BigDecimal(4.2)) >= 0)
				contractEvaluation.setGrade(X_UNS_Contract_Evaluation.GRADE_A);
		}
		else if(mField.getColumnName().equals(
				MUNSContractEvaluation.COLUMNNAME_UNS_Contract_Recommendation_ID))
		{
			I_UNS_Contract_Recommendation cr = contractEvaluation.getUNS_Contract_Recommendation();
			contractEvaluation.setUNS_Employee_ID(cr.getUNS_Employee_ID());
			contractEvaluation.setLastContractDate(cr.getDateContractStart());
			contractEvaluation.setLastEndContractDate(cr.getDateContractEnd());
		}

		return null;
	}

}
