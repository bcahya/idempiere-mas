package com.unicore.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MUser;
import org.compiere.util.DB;

import com.uns.model.MUNSChargeRS;

import com.unicore.model.MUNSChecker;
import com.unicore.model.X_UNS_Checker;
import com.unicore.model.X_UNS_CheckerConfirmation;
import com.unicore.model.X_UNS_Checker_Result;

public class CalloutChecker implements IColumnCallout{

	public CalloutChecker() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		String retValue = null;

		if (mField.getColumnName().equals(X_UNS_Checker_Result.COLUMNNAME_UNS_Checker_ID))
		{
			retValue = this.checkerInResult(ctx, WindowNo, mTab, mField, value);
		}
		if (mField.getColumnName().equals(X_UNS_CheckerConfirmation.COLUMNNAME_UNS_Checker_ID))
		{
			retValue = this.checkerInConfim(ctx, WindowNo, mTab, mField, value);
		}
		if (mField.getColumnName().equals(X_UNS_Checker.COLUMNNAME_UNS_Charge_RS_ID))
		{
			retValue = this.chargeRS(ctx, WindowNo, mTab, mField, value);
		}
		if (mField.getColumnName().equals(X_UNS_Checker.COLUMNNAME_C_BPartner_ID))
		{
			retValue = this.bpartnerInChecker(ctx, WindowNo, mTab, mField, value);
		}
		
		return retValue;
	}
	
	private String checkerInResult(Properties ctx, int windowNo, GridTab mTab, GridField mField,
			Object value)
	{
		if (null == value || (Integer) value <= 0)
		{
			mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_AD_Org_ID, null);
			mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_AD_OrgTo_ID, null);
			mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_UNS_BillingGroup_Result_ID, null);
			mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_CheckerType, null);
			mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_DateDoc, null);
			mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_Description, null);
			return "";
		}
		
		MUNSChecker c = new MUNSChecker(ctx, (Integer) value, null);
		
		if(c.get_ID() > 0)
		{
			mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_AD_Org_ID, c.getAD_Org_ID());
			mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_CheckerType, c.getCheckerType());
			mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_DateDoc, c.getDateDoc());
			mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_Description, c.getDescription());
			mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_SalesRep_ID, c.getSalesRep_ID());
			mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_isDefinedCost, c.isDefinedCost());
			
			if(c.getCheckerType().equals("BC"))
				mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_AD_OrgTo_ID, c.getAD_OrgTo_ID());
			else
			{
				String sql = "SELECT UNS_BillingGroup_Result_ID FROM UNS_BillingGroup_Result"
						+ " WHERE UNS_BillingGroup_ID = " + c.getUNS_BillingGroup_ID();
				mTab.setValue(X_UNS_Checker_Result.COLUMNNAME_UNS_BillingGroup_Result_ID, DB.getSQLValue(null, sql));
			}
		}
	return null;
	}			
	
	private String checkerInConfim(Properties ctx, int windowNo, GridTab mTab, GridField mField,
			Object value)
	{
		if (null == value || (Integer) value <= 0)
		{
			mTab.setValue(X_UNS_CheckerConfirmation.COLUMNNAME_AD_OrgFrom_ID, null);
			mTab.setValue(X_UNS_CheckerConfirmation.COLUMNNAME_SalesRep_ID, null);
			mTab.setValue(X_UNS_CheckerConfirmation.COLUMNNAME_Description, null);
			return "";
		}
		
		MUNSChecker c = new MUNSChecker(ctx, (Integer) value, null);
		if(c.get_ID() > 0)
		{
			mTab.setValue(X_UNS_CheckerConfirmation.COLUMNNAME_AD_OrgFrom_ID, c.getAD_Org_ID());
			mTab.setValue(X_UNS_CheckerConfirmation.COLUMNNAME_SalesRep_ID, c.getSalesRep_ID());
			mTab.setValue(X_UNS_CheckerConfirmation.COLUMNNAME_Description, c.getDescription());
		}
		
		return null;
	}
	
	private String chargeRS(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value)
	{
		if(null == value || (Integer) value <= 0)
		{
			mTab.setValue(X_UNS_CheckerConfirmation.COLUMNNAME_AD_Org_ID, null);
			mTab.setValue(X_UNS_CheckerConfirmation.COLUMNNAME_C_BPartner_ID, null);
			mTab.setValue(X_UNS_CheckerConfirmation.COLUMNNAME_SalesRep_ID, null);
			return "";
		}
		
		MUNSChargeRS rs = new MUNSChargeRS(ctx, (Integer) value, null);
		
		mTab.setValue(X_UNS_Checker.COLUMNNAME_AD_Org_ID, rs.getAD_Org_ID());
		mTab.setValue(X_UNS_Checker.COLUMNNAME_C_BPartner_ID, rs.getC_BPartner_ID());
		mTab.setValue(X_UNS_Checker.COLUMNNAME_SalesRep_ID,
				DB.getSQLValue(null, "SELECT AD_User_ID FROM AD_User WHERE C_BPartner_ID = " + rs.getC_BPartner_ID()));
		
		return null;
	}
	
	private String bpartnerInChecker(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value)
	{
		if(null == value || (Integer) value <= 0)
			return "";
		MUser[] user = MUser.getOfBPartner(ctx, (Integer) value, null);
		
		if(user.length> 0)
			mTab.setValue(X_UNS_Checker.COLUMNNAME_SalesRep_ID, user[0].get_ID());
		
		return null;
	}
}