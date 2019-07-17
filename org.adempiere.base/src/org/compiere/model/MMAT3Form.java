package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;



public class MMAT3Form extends X_MAT3_Form {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2022477070493486535L;

	
	public MMAT3Form(Properties ctx, int MAT3_Form_ID, String trxName) {
		super(ctx, MAT3_Form_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MMAT3Form(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Return an MAT3Form data model by the AD_Form_ID value
	 * @param AD_Form_ID
	 * @return
	 */
	public static MMAT3Form get(Properties ctx, String trxName, int AD_Form_ID)
		throws Exception
	{		
		String whereClause = X_MAT3_Form.COLUMNNAME_AD_Form_ID + " = ? ";
		Query qry = new Query(ctx, X_MAT3_Form.Table_Name, whereClause, trxName);
		qry.setParameters(AD_Form_ID);
		return (MMAT3Form)qry.firstOnly();
	}
	
}
