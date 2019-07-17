/**
 * 
 */
package com.uns.util;

import com.mchange.v2.c3p0.C3P0ProxyConnection;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.DB;

public final class DBUtils
{
  public static final String COLUMN_ROW_INDEX = "Row_Index";
  public static final String COLUMN_ROW_ID = "Row_ID";
  
  public static Object[] getSingleRowValues(String trxName, String sql, Object... params)
  {
    PreparedStatement stmt = DB.prepareStatement(sql, trxName);
    Object[] retValues = new Object[0];
    try
    {
      if (params != null) {
        for (int i = 0; i < params.length; i++) {
          stmt.setObject(i + 1, params[i]);
        }
      }
      ResultSet rs = stmt.executeQuery();
      if (!rs.next()) {
        return retValues;
      }
      ResultSetMetaData md = rs.getMetaData();
      retValues = new Object[md.getColumnCount()];
      for (int i = 0; i < md.getColumnCount(); i++) {
        retValues[i] = rs.getObject(i + 1);
      }
    }
    catch (SQLException ex)
    {
      throw new AdempiereException(ex.getMessage());
    }
    return retValues;
  }
  
  public static Integer[] getIntValues(String trxName, String sql, Object... params)
  {
    PreparedStatement stmt = DB.prepareStatement(sql, trxName);
    List<Integer> intList = new ArrayList();
    try
    {
      if (params != null) {
        for (int i = 0; i < params.length; i++) {
          stmt.setObject(i + 1, params[i]);
        }
      }
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        intList.add(Integer.valueOf(rs.getInt(1)));
      }
    }
    catch (SQLException ex)
    {
      throw new AdempiereException(ex.getMessage());
    }
    Integer[] intVals = new Integer[intList.size()];
    intList.toArray(intVals);
    return intVals;
  }
  
  public static String getPOColumnsAsDBArray(PO po)
  {
    String poKeyColumn = po.get_TableName() + "_ID";
    
    StringBuffer retCols = new StringBuffer("'{\"" + poKeyColumn + "\"");
    int colCount = po.get_ColumnCount();
    for (int i = 0; i < colCount; i++) {
      if (!po.get_ColumnName(i).equalsIgnoreCase(poKeyColumn)) {
        retCols.append(", \"" + po.get_ColumnName(i) + "\"");
      }
    }
    retCols.append("}'");
    
    return retCols.toString();
  }
  
  public static String[] getPOColumnsAsArrayOfString(PO po)
  {
    String poKeyColumn = po.get_TableName() + "_ID";
    
    int colCount = po.get_ColumnCount();
    String[] retCols = new String[colCount];
    retCols[0] = poKeyColumn;
    
    int j = 1;
    for (int i = 0; i < colCount; i++) {
      if (!po.get_ColumnName(i).equalsIgnoreCase(poKeyColumn))
      {
        retCols[j] = po.get_ColumnName(i);
        j++;
      }
    }
    return retCols;
  }
  
  public static StringBuffer getPOValuesAsDBArray(PO po)
  {
    StringBuffer retVals = new StringBuffer("{\"" + po.get_ID() + "\"");
    
    String poKeyColumn = po.get_TableName() + "_ID";
    
    int colCount = po.get_ColumnCount();
    for (int i = 0; i < colCount; i++) {
      if (!po.get_ColumnName(i).equalsIgnoreCase(poKeyColumn))
      {
        Object colVal = po.get_Value(i);
        
        retVals.append(", \"" + colVal + "\"");
      }
    }
    retVals.append("}");
    
    return retVals;
  }
  
  public static String[] getPOValuesAsArrayOfString(PO po)
  {
    String poKeyColumn = po.get_TableName() + "_ID";
    
    int colCount = po.get_ColumnCount();
    
    String[] retVals = new String[colCount];
    
    retVals[0] = String.valueOf(po.get_ID());
    
    int j = 1;
    for (int i = 0; i < colCount; i++) {
      if (!po.get_ColumnName(i).equalsIgnoreCase(poKeyColumn))
      {
        Object colVal = po.get_Value(i);
        if (colVal == null)
        {
          retVals[j] = "NILL";
        }
        else if ((colVal instanceof Boolean))
        {
          boolean theBool = ((Boolean)colVal).booleanValue();
          retVals[j] = (theBool ? "Y" : "N");
        }
        else
        {
          retVals[j] = colVal.toString();
        }
        j++;
      }
    }
    return retVals;
  }
  
  public static String getPOTypesAsDBArray(PO po)
  {
    StringBuffer retVals = new StringBuffer("{\"1\"");
    
    String poKeyColumn = po.get_TableName() + "_ID";
    
    POInfo poInfo = po.getPOInfo();
    
    int colCount = poInfo.getColumnCount();
    for (int i = 0; i < colCount; i++) {
      if (!poInfo.getColumnName(i).equalsIgnoreCase(poKeyColumn))
      {
        Class<?> colClass = poInfo.getColumnClass(i);
        
        int colType = 2;
        if ((colClass == Integer.class) || (colClass == BigDecimal.class)) {
          colType = 1;
        }
        retVals.append(", \"" + colType + "\"");
      }
    }
    retVals.append("}");
    
    return retVals.toString();
  }
  
  public static String[] getPOTypesAsArrayOfString(PO po)
  {
    String poKeyColumn = po.get_TableName() + "_ID";
    
    POInfo poInfo = po.getPOInfo();
    
    int colCount = poInfo.getColumnCount();
    
    String[] retVals = new String[colCount];
    retVals[0] = "1";
    
    int j = 1;
    for (int i = 0; i < colCount; i++) {
      if (!poInfo.getColumnName(i).equalsIgnoreCase(poKeyColumn))
      {
        Class<?> colClass = poInfo.getColumnClass(i);
        
        String colType = "2";
        if ((colClass == Integer.class) || (colClass == BigDecimal.class)) {
          colType = "1";
        }
        retVals[j] = colType;
        j++;
      }
    }
    return retVals;
  }
  
  public static Hashtable<Object, Integer> executeBatchUpdateOfPO(Hashtable<?, ?> hashPO, String trxName)
  {
    String[] colsArray = null;
    String[] typesArray = null;
    String[][] valuesArray = null;
    String tableName = null;
    
    List<Object> keysTmp = new ArrayList();
    
    int i = 0;
    Iterator<?> keys = hashPO.keySet().iterator();
    while (keys.hasNext())
    {
      Object key = keys.next();
      
      keysTmp.add(key);
      
      PO thePO = (PO)hashPO.get(key);
      if ((colsArray == null) || (typesArray == null))
      {
        tableName = thePO.get_TableName();
        colsArray = getPOColumnsAsArrayOfString(thePO);
        typesArray = getPOTypesAsArrayOfString(thePO);
        valuesArray = new String[hashPO.size()][];
      }
      valuesArray[i] = getPOValuesAsArrayOfString(thePO);
      i++;
    }
    Hashtable<Object, Integer> idMapOfKeys = new Hashtable();
    
    String sql = "SELECT * FROM executeBatchUpdatePO(?, ?, ?, ?)";
    
    PreparedStatement stmt = DB.prepareStatement(sql, trxName);
    ResultSet rs = null;
    try
    {
      Connection conn = stmt.getConnection();
      Array pgColsArray = getSqlArray(colsArray, "varchar", conn);
      Array pgTypesArray = getSqlArray(typesArray, "varchar", conn);
      Array pgValuesArray = getSqlArray(valuesArray, "varchar", conn);
      stmt.setString(1, tableName);
      stmt.setArray(2, pgColsArray);
      stmt.setArray(3, pgTypesArray);
      stmt.setArray(4, pgValuesArray);
      
      rs = stmt.executeQuery();
      i = 0;
      while (rs.next())
      {
        int theRowIx = rs.getInt("Row_Index");
        int theNewId = rs.getInt("Row_ID");
        
        Object theKey = keysTmp.get(theRowIx);
        
        idMapOfKeys.put(theKey, Integer.valueOf(theNewId));
        i++;
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
      throw new AdempiereException(ex.getMessage());
    }
    return idMapOfKeys;
  }
  
  public static Hashtable<Integer, Integer> executeBatchUpdateOfPO(PO[] poList, String trxName)
  {
    Hashtable<Integer, Integer> idMapOfKeys = new Hashtable();
    if (poList == null) {
      return idMapOfKeys;
    }
    String[] colsArray = null;
    String[] typesArray = null;
    String[][] valuesArray = null;
    String tableName = null;
    
    PO[] arrayOfPO = poList;int j = poList.length;
    for (int i = 0; i < j; i++)
    {
      PO thePO = arrayOfPO[i];
      if ((colsArray == null) || (typesArray == null))
      {
        tableName = thePO.get_TableName();
        colsArray = getPOColumnsAsArrayOfString(thePO);
        typesArray = getPOTypesAsArrayOfString(thePO);
        valuesArray = new String[poList.length][];
      }
      valuesArray[i] = getPOValuesAsArrayOfString(thePO);
    }
    String sql = "SELECT * FROM executeBatchUpdatePO(?, ?, ?, ?)";
    
    Object stmt = DB.prepareStatement(sql, trxName);
    ResultSet rs = null;
    try
    {
      Connection conn = ((PreparedStatement)stmt).getConnection();
      Array pgColsArray = getSqlArray(colsArray, "varchar", conn);
      Array pgTypesArray = getSqlArray(typesArray, "varchar", conn);
      Array pgValuesArray = getSqlArray(valuesArray, "varchar", conn);
      ((PreparedStatement)stmt).setString(1, tableName);
      ((PreparedStatement)stmt).setArray(2, pgColsArray);
      ((PreparedStatement)stmt).setArray(3, pgTypesArray);
      ((PreparedStatement)stmt).setArray(4, pgValuesArray);
      
      rs = ((PreparedStatement)stmt).executeQuery();
      while (rs.next())
      {
        int theRowIx = rs.getInt("Row_Index");
        int theNewId = rs.getInt("Row_ID");
        
        idMapOfKeys.put(Integer.valueOf(theRowIx), Integer.valueOf(theNewId));
      }
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
      throw new AdempiereException(ex.getMessage());
    }
    return idMapOfKeys;
  }
  
  public static Array getSqlArray(Object[] data, String sqlTypeName, Connection conn)
    throws SQLException
  {
    Array array;
    if ((conn instanceof C3P0ProxyConnection))
    {
      C3P0ProxyConnection proxy = (C3P0ProxyConnection)conn;
      try
      {
        Method m = Connection.class.getMethod("createArrayOf", new Class[] { String.class, Object[].class });
        Object[] args = { sqlTypeName, data };
        array = (Array)proxy.rawConnectionOperation(m, C3P0ProxyConnection.RAW_CONNECTION, args);
      }
      catch (Exception e)
      {
    	throw new SQLException(e);
      }
    }
    else
    {
      array = conn.createArrayOf(sqlTypeName, data);
    }
    return array;
  }
}
