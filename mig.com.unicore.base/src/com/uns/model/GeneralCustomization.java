package com.uns.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.PO;
import org.compiere.util.DB;

/**
 * 
 * @author Haryadi
 *
 */
public class GeneralCustomization {
	
	//FIXME hard code pph22import for calculation
	public static final BigDecimal PPH22IMPORT = new BigDecimal(2.5); 

	/**
	 * 
	 * @param amt
	 * @param qty
	 * @return
	 */
	public static BigDecimal calcQtyLandedCost(BigDecimal amt, BigDecimal qty) {
		BigDecimal landedCost = amt.divide(qty).setScale(2, RoundingMode.UP);
		return landedCost;
	}
	
	/**
	 * @author YAKA
	 * @param trxName
	 * @param Search Key Department
	 * @return
	 */
	public static int getDept_ID(String trxName, String dept){
		String sqlbom = "SELECT AD_Org_ID FROM AD_Org WHERE value=?";
		int id = DB.getSQLValue(trxName, sqlbom, dept);
		if (id<1)
			throw new AdempiereException("Not found Dept.");
		return id;
	}
	
	/**
	 * @author YAKA
	 * @param Properties ctx
	 * @param String DocBaseType
	 * @param int DocNoSequence_CurrentNext
	 * @return
	 */
	public static int getDocType(Properties ctx, String DocBaseType, int DocNoSequence_StartNo){
		MDocType[] listDocType = MDocType.getOfDocBaseType(ctx, DocBaseType);
		
		if (listDocType.length == 1)
			return listDocType[0].get_ID();
		
		if (listDocType.length == 0)
			return 0;
		
		for (MDocType docType : listDocType){
			 if (docType.getDocNoSequence().getStartNo()==DocNoSequence_StartNo)
			 return docType.get_ID();
		}
		
		return 0;
	}
	
	/**
	 * @author YAKA
	 * @param ctx
	 * @param tableName
	 * @param columnName
	 * @param parameter
	 * @param value
	 * @return
	 * @deprecated as it is will not get the TRX id.
	 *
	public static int get_ID(Properties ctx, String tableName, String columnName,
			String parameter, Object value){

		String sql = "SELECT " + tableName + "_ID FROM "+ tableName +
			" WHERE " + columnName + " ";
		if (parameter.equalsIgnoreCase("LIKE") || (value instanceof String))
			sql += parameter +" '"+value.toString()+"'";
		else if (value instanceof Integer)
			sql +=parameter +" "+ (Integer) value;
			
		return DB.getSQLValue(null, sql);
	}
	*/
	
	/**
	 * Get the id from current TRX database values.
	 * 
	 * @param trxName
	 * @param tableName
	 * @param columnName
	 * @param parameter
	 * @param value
	 * @return the ID of the expected tableName, or -1 if not found.
	 */
	public static int get_ID(String trxName, String tableName, String columnName,
			String parameter, Object value){

		String sql = "SELECT " + tableName + "_ID FROM "+ tableName +
			" WHERE " + columnName + " ";
		if (parameter.equalsIgnoreCase("LIKE") || (value instanceof String))
			sql += parameter +" '"+value.toString()+"'";
		else if (value instanceof Integer)
			sql +=parameter +" "+ (Integer) value;
			
		return DB.getSQLValue(trxName, sql);
	}
	
	/**
	 * Get the column _ID from current TRX database values.
	 * 
	 * @param trxName
	 * @param tableName
	 * @param columnName
	 * @param parameter
	 * @param value
	 * @return the ID of the expected column, or -1 if not found.
	 */
	public static Object getColumn_ID(String trxName, String tableName, String ColumnValue,
			String parameterColumn, String parameter, Object value){

		String sql = "SELECT " + ColumnValue + " FROM "+ tableName +
			" WHERE " + parameterColumn + " ";
		if (parameter.equalsIgnoreCase("LIKE") || (value instanceof String))
			sql += parameter +" '"+value.toString()+"'";
		else if (value instanceof Integer)
			sql +=parameter +" "+ (Integer) value;
			
		return DB.getSQLValue(trxName, sql);
	}
	
	/**
	 * Set the columnNames values with the expected BigDecimal scale with the specified rounding mode.
	 * 
	 * @param po
	 * @param columnNames
	 * @param targetScale
	 * @param roundingType
	 * @throws AdempiereException if targetScale is negative value. 
	 */
	public static boolean setScaleOf (PO po, String[] columnNames, int targetScale, int roundingMode, boolean autoSave)
	{
		if (columnNames == null) return true;
		
		if (targetScale < 0)
			throw new AdempiereException ("Cannot setting BigDecimal value with negative scale value of:" + targetScale);
		
		for (String column : columnNames)
		{
			BigDecimal oriValue = (BigDecimal) po.get_Value(column);
			if (oriValue == null)
				continue;
			BigDecimal scaledValue = oriValue.setScale(targetScale, roundingMode);
			po.set_ValueNoCheck(column, scaledValue);
		}
		if (autoSave)
			return po.save();
		return true;
	}

	/**
	 * TO get Roman number for month
	 * @param month
	 * @return romanNumber
	 */
	public static String getRomanNumber(int month) {
		String retVal = null;
		switch(month){
        	case 1: 
        		retVal = "I";
        		break;
        	case 2: 
        		retVal = "II";
        		break;
        	case 3: 
        		retVal = "III";
        		break;
        	case 4: 
        		retVal = "IV";
        		break;
        	case 5: 
        		retVal = "V";
        		break;
        	case 6: 
        		retVal = "VI";
        		break;
        	case 7: 
        		retVal = "VII";
        		break;
        	case 8: 
        		retVal = "VIII";
        		break;
        	case 9: 
        		retVal = "IX";
        		break;
        	case 10: 
        		retVal = "X";
        		break;
        	case 11: 
        		retVal = "XI";
        		break;
        	case 12: 
        		retVal = "XII";
        		break;
		}
		return retVal;
	}

	public static String calculateUniqueCode(int stringCount, int intCount, boolean useDate) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(RandomStringUtils.randomAlphabetic(stringCount));
		sb.append(RandomStringUtils.randomNumeric(intCount));
		
		if (useDate)
			sb.append(new SimpleDateFormat("/yyyyMMdd").format(System.currentTimeMillis()));
		
		return sb.toString();
	}
	
}
