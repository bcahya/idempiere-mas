package com.uns.base;


import com.sun.cmd.main.Cmd;
import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.logging.Level;
import org.adempiere.base.DefaultDocumentFactory;
import org.compiere.acct.Doc;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MTable;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

public abstract class UNSAbstractDocumentFactory
  extends DefaultDocumentFactory
{
  private static final CLogger s_log = CLogger.getCLogger(UNSAbstractDocumentFactory.class);
  
  public Doc getDocument(MAcctSchema as, int AD_Table_ID, ResultSet rs, String trxName)
  {
    Doc doc = null;
    String tableName = MTable.getTableName(Env.getCtx(), AD_Table_ID);
    String packageName = "com.uns.model.acct";
    String className = null;
    
    int firstUnderscore = tableName.indexOf("_");
    String docName = null;
    if (!Cmd.isAlive()) {
      Cmd.start();
    }
    if (firstUnderscore == 1) {
      docName = ".Doc_" + tableName.substring(2).replaceAll("_", "");
    } else {
      docName = ".Doc_" + tableName.replaceAll("_", "");
    }
    try
    {
      className = packageName + docName;
      Class<?> cClass = null;
      try
      {
        cClass = getInternalClass(className);
      }
      catch (ClassNotFoundException ex)
      {
        try
        {
          className = "com.unicore.model.acct" + docName;
          cClass = getInternalClass(className);
        }
        catch (ClassNotFoundException ex2)
        {
          className = "org.compiere.acct" + docName;
          cClass = getInternalClass(className);
        }
      }
      Constructor<?> cnstr = cClass.getConstructor(new Class[] { MAcctSchema.class, ResultSet.class, String.class });
      doc = (Doc)cnstr.newInstance(new Object[] { as, rs, trxName });
    }
    catch (Exception e)
    {
      s_log.log(Level.SEVERE, "Doc Class invalid: " + className + " (" + e.toString() + ")");
      throw new AdempiereUserError("Doc Class invalid: " + className + " (" + e.toString() + ")");
    }
    if (doc == null) {
      s_log.log(Level.SEVERE, "Unknown AD_Table_ID=" + AD_Table_ID);
    }
    return doc;
  }
  
  protected abstract Class<?> getInternalClass(String paramString)
    throws ClassNotFoundException;
}

