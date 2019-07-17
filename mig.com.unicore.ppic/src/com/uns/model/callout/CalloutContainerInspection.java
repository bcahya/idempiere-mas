/**
 * 
 */
package com.uns.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.uns.model.MUNSContainerArrival;
import com.uns.model.MUNSContainerDeparture;
import com.uns.model.X_UNS_ContainerArrival;
import com.uns.qad.model.X_UNS_QAStatusContainer;

/**
 * @author YAKA
 *
 */
public class CalloutContainerInspection extends CalloutEngine implements
		IColumnCallout {

	/**
	 * 
	 */
	public CalloutContainerInspection() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCallout#start(java.util.Properties, int, org.compiere.model.GridTab, org.compiere.model.GridField, java.lang.Object, java.lang.Object)
	 */
	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {
		String retValue = null;
		if (value==null){
			mTab.setValue(X_UNS_ContainerArrival.COLUMNNAME_UNS_ContainerManagement_ID, null);
		} else if (X_UNS_QAStatusContainer.COLUMNNAME_UNS_ContainerArrival_ID.equals(mField.getColumnName())){
			return calloutCA(ctx, WindowNo, mTab, mField, value);
		} else if (X_UNS_QAStatusContainer.COLUMNNAME_UNS_ContainerDeparture_ID.equals(mField.getColumnName())){
			return calloutCD(ctx, WindowNo, mTab, mField, value);
		} 
		
		return retValue;
	}

	private String calloutCD(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		Integer cd_id = (Integer) mTab.getValue(X_UNS_QAStatusContainer.COLUMNNAME_UNS_ContainerDeparture_ID);
		
		if (cd_id.intValue()<=0)
			return "";
		else {
			MUNSContainerDeparture cd = new MUNSContainerDeparture(ctx, cd_id.intValue(), null);
			mTab.setValue(X_UNS_QAStatusContainer.COLUMNNAME_UNS_ContainerManagement_ID, cd.getUNS_ContainerManagement_ID());
		}
		return null;
	}

	private String calloutCA(Properties ctx, int windowNo, GridTab mTab,
			GridField mField, Object value) {
		Integer ca_id = (Integer) mTab.getValue(X_UNS_QAStatusContainer.COLUMNNAME_UNS_ContainerArrival_ID);

		if (ca_id.intValue()<=0)
			return "";
		else {
			MUNSContainerArrival ca = new MUNSContainerArrival(ctx, ca_id.intValue(), null);
			mTab.setValue(X_UNS_ContainerArrival.COLUMNNAME_UNS_ContainerManagement_ID, ca.getUNS_ContainerManagement_ID());
		}
		return null;
	}

}
