/**
 * 
 */
package com.uns.base.model;

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;


/**
 * @author YAKA
 *
 */
public class X_C_DocType extends org.compiere.model.X_C_DocType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1552749690961298861L;

	/** Billing Document */
	public static final String DOCBASETYPE_Billing = "BIL";
	
	public X_C_DocType(Properties ctx, int C_DocType_ID, String trxName) {
		super(ctx, C_DocType_ID, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static int getID(String C_DocBaseType, String trxName){
		String sql = "SELECT C_DocType_ID FROM C_DocType "
				+ "WHERE DocBaseType = ? ";
		
		int i = DB.getSQLValue(trxName, sql, C_DocBaseType);
		
		if (i<=0){
			throw new AdempiereException("Document Type not set.");
		}
		return i;
	}
	


}
