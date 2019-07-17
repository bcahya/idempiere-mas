/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * @author Burhani Adam
 *
 */
public class MUNSIncentiveComponent extends X_UNS_IncentiveComponent  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9114916549535213861L;

	/**
	 * @param ctx
	 * @param UNS_IncentiveComponent_ID
	 * @param trxName
	 */
	public MUNSIncentiveComponent(Properties ctx,
			int UNS_IncentiveComponent_ID, String trxName) {
		super(ctx, UNS_IncentiveComponent_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSIncentiveComponent(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public boolean beforeSave(boolean newRecord)
	{
		setAD_Org_ID(getUNS_Incentive().getAD_Org_ID());
		if(getAD_OrgTrx_ID() > 0)
			setComponentType(COMPONENTTYPE_Organization);
		else if(getC_BPartner_ID() > 0)
			setComponentType(COMPONENTTYPE_Customer);
		else if(getM_Product_ID() > 0)
			setComponentType(COMPONENTTYPE_Product);
		else if(getM_Product_Category_ID() > 0)
			setComponentType(COMPONENTTYPE_ProductCategory);
		else if(getC_BP_Group_ID() > 0)
			setComponentType(COMPONENTTYPE_CustomerGroup);
		else if(getUNS_Rayon_ID() > 0)
			setComponentType(COMPONENTTYPE_Rayon);
		else if(getUNS_Outlet_Grade_ID() > 0)
			setComponentType(COMPONENTTYPE_GradeCustomer);
		else if(getUNS_Outlet_Type_ID() > 0)
			setComponentType(COMPONENTTYPE_CustomerType);
		else if(getAD_Ref_List_ID() > 0)
		{
			setAD_Ref_List_ID(getAD_Ref_List_ID());
			setTenderType(getAD_Ref_List().getValue());
			setComponentType(COMPONENTTYPE_TenderType);
		}
		
		return true;
	}
	
	public static MUNSIncentiveComponent[] getByType(Properties ctx, 
			int UNS_Incentive_ID, String type, String trxName)
	{
		List<MUNSIncentiveComponent> cmps = new Query(ctx, Table_Name, 
				COLUMNNAME_ComponentType + "=? AND " + COLUMNNAME_UNS_Incentive_ID + "=?",
				trxName).setParameters(type, UNS_Incentive_ID).list();
		
		return cmps.toArray(new MUNSIncentiveComponent[cmps.size()]);
	}
}
