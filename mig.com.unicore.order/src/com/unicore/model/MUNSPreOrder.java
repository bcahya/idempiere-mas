/**
 * 
 */
package com.unicore.model;

import java.util.Properties;
import org.compiere.model.Query;

/**
 * @author Burhani Adam
 *
 */
public class MUNSPreOrder extends X_UNS_PreOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7856737762338903369L;

	public MUNSPreOrder(Properties ctx, int UNS_PreOrder_ID, String trxName) {
		super(ctx, UNS_PreOrder_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public java.util.List<MUNSPreOrderLine> getLines(int UNS_PreOrder_ID)
	{		
		java.util.List<MUNSPreOrderLine> lines = new Query(getCtx(), MUNSPreOrderLine.Table_Name,
				COLUMNNAME_UNS_PreOrder_ID + "=?", get_TrxName()).setParameters(UNS_PreOrder_ID).list();
		
		return lines;
	}
}
