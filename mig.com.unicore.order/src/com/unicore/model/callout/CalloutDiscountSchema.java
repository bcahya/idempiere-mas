/**
 * 
 */
package com.unicore.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;

import com.unicore.model.MDiscountSchema;
import com.unicore.model.MDiscountSchemaBreak;
import com.unicore.model.MUNSBonusClaim;

/**
 * @author menjangan
 *
 */
public class CalloutDiscountSchema implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutDiscountSchema() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retVal = null;
		
		//this for Discount bonus claim
		if(mTab.getTableName().equals(MUNSBonusClaim.Table_Name) && mField.getColumnName().equals("C_BPartner_ID"))
			retVal = C_BPartner_Bonus(ctx, WindowNo, mTab, mField, value, oldValue);
		//Discount schema
		else if(mField.getColumnName().equalsIgnoreCase(MDiscountSchema.COLUMNNAME_C_BPartner_ID))
			retVal = C_BPartner_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equalsIgnoreCase(MDiscountSchema.COLUMNNAME_C_BP_Group_ID))
			retVal = C_BPGroup_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MDiscountSchemaBreak.COLUMNNAME_IsVendorCashback))
			retVal = IsVendorCashBack(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals(MDiscountSchema.COLUMNNAME_IsBirthdayDiscount))
			retVal = isBirthdayDiscount(ctx, WindowNo, mTab, mField, value, oldValue);
		
		return retVal;
	}
	
	private String C_BPGroup_ID(Properties ctx, int WindowNo, GridTab mTab
			, GridField mField, Object value, Object oldValue)
	{
		mTab.setValue(MDiscountSchema.COLUMNNAME_C_BPartner_ID, -1);
		return null;
	}
	
	private String C_BPartner_ID(Properties ctx, int WindowNo, GridTab mTab
			, GridField mField, Object value, Object oldValue)
	{
		mTab.setValue(MDiscountSchema.COLUMNNAME_C_BP_Group_ID, -1);
		return null;
	}
	
	private String IsVendorCashBack(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		mTab.setValue(MDiscountSchema.COLUMNNAME_RequirementType, MDiscountSchema.REQUIREMENTTYPE_MustSameWithThisSchema);
		return null;
	}
	
	private String isBirthdayDiscount(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		mTab.setValue(MDiscountSchema.COLUMNNAME_RequirementType, MDiscountSchema.REQUIREMENTTYPE_MustSameWithThisSchema);
		return null;
	}
	
	/** callout for UNS Discount bonus claim */
	private String C_BPartner_Bonus(Properties ctx, int WindowNo, GridTab mtab, GridField mField, Object value, Object oldValue)
	{
		if(null == value)
		{
			mtab.setValue("C_BPartner_Location_ID", value);
			return "";
		}
		
		int location_ID = DB.getSQLValue(null, "SELECT C_BPartner_Location_ID FROM C_BPartner_Location WHERE C_BPartner_ID =?", value);
		mtab.setValue("C_BPartner_Location_ID", location_ID);
		return null;
	}
}
