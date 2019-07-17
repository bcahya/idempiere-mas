/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
//import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
//import org.compiere.model.MInvoiceTax;
import org.compiere.util.DB;

//import com.uns.base.model.Query;

/**
 * @author ALBURHANY
 *
 */
public class MUNSPSProduct extends X_UNS_PS_Product {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7639404670102322140L;

	/**
	 * @param ctx
	 * @param UNS_PS_Product_ID
	 * @param trxName
	 */
	public MUNSPSProduct(Properties ctx, int UNS_PS_Product_ID, String trxName) {
		super(ctx, UNS_PS_Product_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSPSProduct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public boolean beforeSave (boolean newRecord)
	{	
		StringBuffer sql = new StringBuffer
				("SELECT UNS_PS_Product_ID FROM UNS_PS_Product"
						+ " WHERE (M_Product_ID = " + getM_Product_ID()
						+ " OR M_Product_Category_ID = " + getM_Product_Category_ID() + ")"
						+ " AND UNS_PointSchema_ID = " + getUNS_PointSchema_ID());
		int idPSProduct = DB.getSQLValue(get_TrxName(), sql.toString());
		
		if (idPSProduct >= 1)
			throw new AdempiereException("Duplicate Product In One Point Schema..!!");
		
		return true;
	}
}
