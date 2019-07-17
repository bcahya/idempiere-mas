/**
 * 
 */
package com.uns.model;

import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;

/**
 * @author menjangan
 *
 */
public class NIKGenerator {

	public static final String TYPE_PERMANEN ="N";
	public static final String TYPE_PEMBORONG = "P";
	public static final String TYPE_HARIAN = "H";
	private String m_NewNIK = null;

	/**
	 * 
	 */
	public NIKGenerator(String type, String trxName) {
		// TODO Auto-generated constructor stub
		if (type.equals(TYPE_PERMANEN))
			generateNIKPermanen(trxName);
		else if (type.equals(TYPE_HARIAN))
			generateNIKHarianBualan(trxName);
		else if (type.equals(TYPE_PEMBORONG))
			generateNIKPemborong(trxName);
		else
			throw new AdempiereUserError("UNKNOW TYPE");
	}
	
	/**
	 * Generate NIK For Employee With Contract Permanen
	 * @param ctx
	 * @param trxName
	 */
	private void generateNIKPermanen(String trxName)
	{
		String sql = "SELECT MAX(" 
				+ MUNSEmployee.COLUMNNAME_Value + ") FROM " 
				+ MUNSEmployee.Table_Name + " WHERE SUBSTRING(" 
				+ MUNSEmployee.COLUMNNAME_Value + " FROM 1 FOR 1) <> '" 
				+ TYPE_PEMBORONG + "' AND " + "SUBSTRING(" 
				+ MUNSEmployee.COLUMNNAME_Value + " FROM 1 FOR 1) <> '" + TYPE_HARIAN + "'";
		
		String nik = DB.getSQLValueString(trxName, sql);
		if (null == nik)
			m_NewNIK = "00000";
		else
		{
			Integer nikAsInt = new Integer(nik);
			nikAsInt += 1;
			String numberAsString = nikAsInt.toString();
			for(int i=0; i<5; i++)
			{
				if (numberAsString.length() < 6)
					numberAsString = "0" + numberAsString;
			}
			m_NewNIK = numberAsString;
		}
	}
	
	/**
	 * 
	 * @param trxName
	 */
	private void generateNIKHarianBualan(String trxName)
	{
		String sql =  "SELECT MAX(" 
				+ MUNSEmployee.COLUMNNAME_Value + ") FROM " 
				+ MUNSEmployee.Table_Name + " WHERE SUBSTRING(" 
				+ MUNSEmployee.COLUMNNAME_Value + " FROM 1 FOR 1) = '" 
				+ TYPE_HARIAN + "'";
		
		String nik = DB.getSQLValueString(trxName, sql);
		if (null == nik)
			m_NewNIK = "H000001";
		else
		{
			nik = nik.substring(1, 6);
			Integer nikAsInt = new Integer(nik);
			nikAsInt += 1;
			String numberAsString = nikAsInt.toString();
			for(int i=0; i<5; i++)
			{
				if (numberAsString.length() < 6)
					numberAsString = "0" + numberAsString;
			}
			m_NewNIK = TYPE_HARIAN + numberAsString;
		}
	}
	
	/**
	 * 
	 * @param trxName
	 */
	private void generateNIKPemborong(String trxName)
	{
		String sql =  "SELECT MAX(" 
				+ MUNSEmployee.COLUMNNAME_Value + ") FROM " 
				+ MUNSEmployee.Table_Name + " WHERE SUBSTRING(" 
				+ MUNSEmployee.COLUMNNAME_Value + " FROM 1 FOR 1) = '" 
				+ TYPE_PEMBORONG + "'";
		
		String nik = DB.getSQLValueString(trxName, sql);
		if (null == nik)
			m_NewNIK = "P000001";
		else
		{
			nik = nik.substring(1, 6);
			Integer nikAsInt = new Integer(nik);
			nikAsInt += 1;
			String numberAsString = nikAsInt.toString();
			for(int i=0; i<5; i++)
			{
				if (numberAsString.length() < 6)
					numberAsString = "0" + numberAsString;
			}
			m_NewNIK = TYPE_PEMBORONG + numberAsString;
		}
	}
	
	/**
	 * 
	 * @return String Of NIK
	 */
	public String getNewNIK()
	{
		return m_NewNIK;
	}
}
