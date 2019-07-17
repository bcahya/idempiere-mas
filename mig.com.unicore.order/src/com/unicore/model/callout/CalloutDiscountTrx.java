package com.unicore.model.callout;

import java.util.Properties;
import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import com.unicore.model.MUNSDiscountTrx;

/**
 * 
 * @author Menjangan
 * @see http://www.untasoft.com
 *
 */
public class CalloutDiscountTrx implements IColumnCallout {

	public CalloutDiscountTrx () {
		super ();
	}
	
	private final String[] m_columns = 
	{
		MUNSDiscountTrx.COLUMNNAME_FirstDiscount,
		MUNSDiscountTrx.COLUMNNAME_SecondDiscount,
		MUNSDiscountTrx.COLUMNNAME_ThirdDiscount,
		MUNSDiscountTrx.COLUMNNAME_FourthDiscount,
		MUNSDiscountTrx.COLUMNNAME_FifthDiscount,
		MUNSDiscountTrx.COLUMNNAME_FlatValueDiscount,
		MUNSDiscountTrx.COLUMNNAME_QtyBonuses
	};

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) 
	{
		for (int i=0; i<m_columns.length; i++)
		{
			if (!m_columns[i].equals(mField.getColumnName()))
			{
				continue;
			}
			
			mTab.setValue("IsChangedByUser", true);
			mTab.setValue("IsNeedRecalculate", true);
		}
		return null;
	}
}
