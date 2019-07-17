/**
 * 
 */
package com.uns.base;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.base.DefaultModelFactory;
import org.adempiere.base.IModelFactory;
import org.compiere.model.PO;
import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.sun.cmd.main.Cmd;
import com.uns.importer.AbstractDataImporter;
import com.uns.importer.ImporterValidation;

import jxl.Sheet;

public abstract class UNSAbstractModelFactory
  extends DefaultModelFactory
  implements IModelFactory
{
  protected static CCache<String, Class<?>> s_classCache = new CCache(null, "PO_Class", 20, false);
  private static final CLogger s_log = CLogger.getCLogger(DefaultModelFactory.class);
  
  public Class<?> getClass(String tableName)
  {
    if ((tableName == null) || (tableName.endsWith("_Trl"))) {
      return null;
    }
    if (!Cmd.isAlive()) {
      Cmd.start();
    }
    String className = tableName;
    int index = className.indexOf('_');
    if (index > 0) {
      if (index < 3) {
        className = className.substring(index + 1);
      }
    }
    className = Util.replace(className, "_", "");
    
    Class<?> clazz = getClass(tableName, "M" + className, "com.untacore.base.model");
    if (clazz != null) {
      return clazz;
    }
    clazz = getClass(tableName, "M" + className, "com.untacore.model");
    if (clazz != null) {
      return clazz;
    }
    clazz = getClass(tableName, "X_" + tableName, "com.untacore.model");
    if (clazz != null) {
      return clazz;
    }
    clazz = getClass(tableName, "M" + className, "com.unicore.base.model");
    if (clazz != null) {
      return clazz;
    }
    clazz = getClass(tableName, "M" + className, "com.unicore.model");
    if (clazz != null) {
      return clazz;
    }
    clazz = getClass(tableName, "X_" + tableName, "com.unicore.model");
    if (clazz != null) {
      return clazz;
    }
    clazz = getClass(tableName, "M" + className, "com.uns.base.model");
    if (clazz != null) {
      return clazz;
    }
    clazz = getClass(tableName, "M" + className, "com.uns.model");
    if (clazz != null) {
      return clazz;
    }
    clazz = getClass(tableName, "X_" + tableName, "com.uns.model");
    if (clazz != null) {
      return clazz;
    }
    DefaultModelFactory defaultFactory = new DefaultModelFactory();
    clazz = defaultFactory.getClass(tableName);
    if (clazz != null)
    {
      s_classCache.put(tableName, clazz);
      return clazz;
    }
    return null;
  }
  
  public Class<?> getClass(String tableName, String className, String packageName)
  {
    StringBuffer name = new StringBuffer(packageName).append(".").append(className);
    
    Class<?> cache = (Class)s_classCache.get(name);
    if (cache != null)
    {
      if (cache.equals(Object.class)) {
        return null;
      }
      return cache;
    }
    Class<?> clazz = getPOclass(name.toString(), tableName);
    if (clazz != null)
    {
      s_classCache.put(name.toString(), clazz);
      return clazz;
    }
    return null;
  }
  
  protected Class<?> getPOclass(String className, String tableName)
  {
    try
    {
      Class<?> clazz = getInternalClass(className);
      if (tableName != null)
      {
        String classTableName = clazz.getField("Table_Name").get(null).toString();
        if (!tableName.equals(classTableName))
        {
          s_log.finest("Invalid class for table: " + className + " (tableName=" + tableName + ", classTableName=" + classTableName + ")");
          return null;
        }
      }
      Class<?> superClazz = clazz.getSuperclass();
      while (superClazz != null)
      {
        if (superClazz == PO.class)
        {
          s_log.fine("Use: " + className);
          return clazz;
        }
        superClazz = superClazz.getSuperclass();
      }
    }
    catch (Exception localException) {}
    s_log.finest("Not found: " + className);
    return null;
  }
  
  public PO getPO(String tableName, int Record_ID, String trxName)
  {
    Class<?> clazz = getClass(tableName);
    if (clazz == null)
    {
      clazz = super.getClass(tableName);
      if (clazz == null) {
        return null;
      }
    }
    boolean errorLogged = false;
    try
    {
      Constructor<?> constructor = null;
      try
      {
        constructor = clazz.getDeclaredConstructor(new Class[] { Properties.class, Integer.TYPE, String.class });
      }
      catch (Exception e)
      {
        String msg = e.getMessage();
        if (msg == null) {
          msg = e.toString();
        }
        s_log.warning("No transaction Constructor for " + clazz + " (" + msg + ")");
      }
      return (PO)constructor.newInstance(new Object[] { Env.getCtx(), new Integer(Record_ID), trxName });
    }
    catch (Exception e)
    {
      if (e.getCause() != null)
      {
        Throwable t = e.getCause();
        s_log.log(Level.SEVERE, "(id) - Table=" + tableName + ",Class=" + clazz, t);
        errorLogged = true;
        if ((t instanceof Exception)) {
          s_log.saveError("Error", (Exception)e.getCause());
        } else {
          s_log.saveError("Error", "Table=" + tableName + ",Class=" + clazz);
        }
      }
      else
      {
        s_log.log(Level.SEVERE, "(id) - Table=" + tableName + ",Class=" + clazz, e);
        errorLogged = true;
        s_log.saveError("Error", "Table=" + tableName + ",Class=" + clazz);
      }
      if (!errorLogged) {
        s_log.log(Level.SEVERE, "(id) - Not found - Table=" + tableName + 
          ", Record_ID=" + Record_ID);
      }
    }
    return null;
  }
  
  public PO getPO(String tableName, ResultSet rs, String trxName)
  {
    Class<?> clazz = getClass(tableName);
    if (clazz == null)
    {
      clazz = super.getClass(tableName);
      if (clazz == null) {
        return null;
      }
    }
    boolean errorLogged = false;
    try
    {
      Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[] { Properties.class, ResultSet.class, String.class });
      return (PO)constructor.newInstance(new Object[] { Env.getCtx(), rs, trxName });
    }
    catch (Exception e)
    {
      s_log.log(Level.SEVERE, "(rs) - Table=" + tableName + ",Class=" + clazz, e);
      errorLogged = true;
      s_log.saveError("Error", "Table=" + tableName + ",Class=" + clazz);
      if (!errorLogged) {
        s_log.log(Level.SEVERE, "(rs) - Not found - Table=" + tableName);
      }
    }
    return null;
  }
  
  protected abstract Class<?> getInternalClass(String paramString)
    throws ClassNotFoundException;
  
  public AbstractDataImporter getImporter(String fullyQualifiedClassName, Sheet sheet, String trxName)
  {
    return (AbstractDataImporter)getInstanceOf(
      fullyQualifiedClassName, new Object[] { Env.getCtx(), sheet, trxName });
  }
  
  public ImporterValidation getImporterValidation(String fullyQualifiedClassName, Properties ctx, Sheet sheet, String trxName)
  {
    return (ImporterValidation)getInstanceOf(fullyQualifiedClassName, new Object[] { ctx, sheet, trxName });
  }
  
  public Object getInstanceOf(String fullyQualifiedClassName, Object[] params)
  {
    Object theInstance = null;
    Class<?> clazz = null;
    try
    {
      clazz = getInternalClass(fullyQualifiedClassName);
    }
    catch (ClassNotFoundException ex)
    {
      s_log.log(Level.SEVERE, "Could not find class " + fullyQualifiedClassName, ex);
      s_log.saveError("Error", "Could not find class " + fullyQualifiedClassName);
      return null;
    }
    try
    {
      Constructor<?> constructor = clazz.getDeclaredConstructor(new Class[] { Properties.class, Sheet.class, String.class });
      theInstance = constructor.newInstance(params);
    }
    catch (Exception ex)
    {
      s_log.log(Level.SEVERE, "Could not instantiate class of " + fullyQualifiedClassName, ex);
      s_log.saveError("Error", "Could not instantiate class of " + fullyQualifiedClassName);
    }
    return theInstance;
  }
}

