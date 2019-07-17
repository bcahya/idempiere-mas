/**
 * 
 */
package com.unicore.model.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MStorageOnHand;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.unicore.model.MUNSBPCanvasser;
import com.unicore.model.X_UNS_CvsCustomer_Location;

import com.unicore.model.I_UNS_Cvs_Rpt;
import com.unicore.model.I_UNS_Cvs_RptGroup;
import com.unicore.model.I_UNS_Cvs_RptProduct;
import com.unicore.model.MUNSCvsRpt;
import com.unicore.model.MUNSCvsRptGroup;
import com.unicore.model.SimplePrice;

/**
 * @author root
 *
 */
public class CalloutCanvas implements IColumnCallout {

	/**
	 * 
	 */
	public CalloutCanvas() {
		super();
	}
	
	private String C_BPartner_ID(
			Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value
			, Object oldValue)
	{
		if(null == value)
		{
			mTab.setValue("C_BPartner_Location_ID", null);
			mTab.setValue("SalesRep_ID", null);
			return null;
		}
		
		int C_BPartner_Location_ID = DB.getSQLValue(
				null, "SELECT C_BPartner_Location_ID FROM C_BPartner_Location WHERE C_BPartner_ID = ?"
				, (Integer) value);
		int SalesRep_ID = DB.getSQLValue(
				null, "SELECT AD_User_ID FROM AD_User WHERE C_BPartner_ID = ?", (Integer) value);
		
		mTab.setValue("C_BPartner_Location_ID", C_BPartner_Location_ID);
		mTab.setValue("SalesRep_ID", SalesRep_ID);
		
		if(mTab.getTableName().equals("UNS_Cvs_Physical"))
		{
			setM_Warehouse_ID((Integer) value, mTab);
		}
		return null;
	}
	
	private String M_Product_ID(
			Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value
			, Object oldValue)
	{
		I_UNS_Cvs_RptProduct model = GridTabWrapper.create(mTab, I_UNS_Cvs_RptProduct.class);
		if(null == value)
		{
			model.setC_UOM_ID(-1);
			model.setPriceActual(Env.ZERO);
			model.setPriceLimit(Env.ZERO);
			model.setPriceList(Env.ZERO);
			return null;
		}
		
		GridTab parent = mTab.getParentTab();
		
		int C_UOM_ID = DB.getSQLValue(
				null, "SELECT C_UOM_ID FROM M_Product WHERE M_Product_ID = ?", (Integer) value);
		
		int M_Locator_ID = DB.getSQLValue(
				null, "SELECT M_Locator_ID FROM UNS_BP_Canvasser WHERE C_BPartner_ID = ?"
				, (Integer) parent.getParentTab().getValue("C_BPartner_ID"));
		
		SimplePrice price = new SimplePrice(
				(Integer) parent.getParentTab().getValue("C_BPartner_ID"), (Integer) value, null);
		
		BigDecimal onHand = MStorageOnHand.getQtyOnHandForLocator(
				(Integer) value, M_Locator_ID, model.getM_AttributeSetInstance_ID(), null);
		
		model.setC_UOM_ID(C_UOM_ID);
		mTab.setValue("QtyAvailable", onHand);
		model.setM_Locator_ID(M_Locator_ID);
		model.setPriceActual(price.getPriceActual());
		model.setPriceLimit(price.getPriceLimit());
		model.setPriceList(price.getPriceList());
		
		return null;
	}
	
	private String QtySold(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) 
	{
		I_UNS_Cvs_RptProduct i = GridTabWrapper.create(mTab, I_UNS_Cvs_RptProduct.class);
		i.setLineNetAmt(i.getPriceActual().multiply(i.getQtySold()));
		return null;
	}
	
	private void setM_Warehouse_ID(int C_BPartner_ID, GridTab mTab)
	{
		MUNSBPCanvasser cvsInfo = MUNSBPCanvasser.getOf(C_BPartner_ID, null);
		if(null == cvsInfo)
			throw new AdempiereUserError("No set Canvasser Info");
		
		if(cvsInfo.getM_Locator_ID() <= 0)
			throw new AdempiereUserError("Not set locator of Canvasser");
		
		int M_Warehouse_ID = cvsInfo.getM_Locator().getM_Warehouse_ID();
		mTab.setValue("M_Warehouse_ID", M_Warehouse_ID);
	}
	
	private String CustomerLocation_ID(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) 
	{
		if(null == value || (Integer) value <= 0)
			return null;
		
		String address = DB.getSQLValueString(null, "SELECT GetSimpleAddress(?)", (Integer) value);
		if(!Util.isEmpty(address, true))
		{
			mTab.setValue(X_UNS_CvsCustomer_Location.COLUMNNAME_Name, address);
		}
		
		return null;
	}
	
	private String UNS_Cvs_RptGroup_ID(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) 
	{
		if(null == value || (Integer) value <= 0)
			return null;
		
		MUNSCvsRptGroup grp = new MUNSCvsRptGroup(ctx, (Integer) value, null);
		I_UNS_Cvs_Rpt rpt = GridTabWrapper.create(mTab, I_UNS_Cvs_Rpt.class);
		
		rpt.setC_BPartner_ID(grp.getC_BPartner_ID());
		rpt.setC_BPartner_Location_ID(grp.getC_BPartner_Location_ID());
		rpt.setSalesRep_ID(grp.getSalesRep_ID());
		
		return null;
	}

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		
		if(mField.getColumnName().equals("C_BPartner_ID"))
			return C_BPartner_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals("M_Product_ID"))
			return M_Product_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mField.getColumnName().equals("QtySold"))
			return QtySold(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mTab.getTableName().equals("UNS_CvsCustomer_Location")
				&& mField.getColumnName().equals("C_Location_ID"))
			return CustomerLocation_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		else if(mTab.getTableName().equals("UNS_Cvs_Rpt")
				&& mField.getColumnName().equals("UNS_Cvs_RptGroup_ID"))
			return UNS_Cvs_RptGroup_ID(ctx, WindowNo, mTab, mField, value, oldValue);
		
		return null;
	}
}
