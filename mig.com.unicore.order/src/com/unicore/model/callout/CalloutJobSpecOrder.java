package com.unicore.model.callout;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.netbeans.api.visual.graph.layout.GridGraphLayout;

import com.unicore.model.MUNSExpPriceDetail;

import com.unicore.model.MUNSExpeditionDetail;
import com.unicore.model.X_UNS_Expedition;
import com.unicore.model.X_UNS_ExpeditionDetail;

public class CalloutJobSpecOrder implements IColumnCallout{

	public CalloutJobSpecOrder()
	{
		
	}

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) 
	{
		String retValue = null;
		if (mField.getColumnName().equals(X_UNS_Expedition.COLUMNNAME_C_DocType_ID))
		{
			retValue = this.calloutCheckJobSpecOrder(ctx, WindowNo, mTab, mField, retValue);
		}
		else if(mField.getColumnName().equals(X_UNS_ExpeditionDetail.COLUMNNAME_JobSODetail_ID))
			retValue = jobSODetail(ctx, WindowNo, mTab, mField, value);
		
		return retValue;
	}

	private String calloutCheckJobSpecOrder(Properties ctx, int windowNo, GridTab mTab, GridField mField,
			Object value)
	{
		int docTypeID = 0;
		if (mField.getColumnName().equals(X_UNS_Expedition.COLUMNNAME_C_DocType_ID))
		{
			docTypeID = (Integer) mTab.getValue("C_DocType_ID");
			String docBaseType = DB.getSQLValueString(null, "select docbasetype from c_doctype where c_doctype_id = "+docTypeID);
			if(docBaseType.equals("JSP"))
			{
				mTab.setValue(X_UNS_Expedition.COLUMNNAME_IsSOTrx, true);
			}
			else
			{
				mTab.setValue(X_UNS_Expedition.COLUMNNAME_IsSOTrx, false);
			}
		}
		return null;
	}
	
	private String jobSODetail(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value)
	{
		if(value == null || (Integer) value <= 0)
			return null;
		
		MUNSExpeditionDetail ed = new MUNSExpeditionDetail(ctx, (Integer) value, null);
		
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_UNS_PackingList_ID, ed.getUNS_PackingList_ID() <= 0 ? null : ed.getUNS_PackingList_ID());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_M_InOut_ID, ed.getM_InOut_ID() <= 0 ? null : ed.getM_InOut_ID());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_M_Movement_ID, ed.getM_Movement_ID() <= 0 ? null : ed.getM_Movement_ID());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_Description, ed.getDescription());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_ItemDescription, ed.getItemDescription());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_C_Charge_ID, ed.getC_Charge_ID());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_Weight, ed.getWeight());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_Qty, ed.getQty());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_Tonase, ed.getTonase());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_Volume, ed.getVolume());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_PriceActual, ed.getPriceActual());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_PriceLimit, ed.getPriceLimit());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_PriceList, ed.getPriceList());
		mTab.setValue(X_UNS_ExpeditionDetail.COLUMNNAME_LineNetAmt, ed.getLineNetAmt());
		
		return null;
	}	
}