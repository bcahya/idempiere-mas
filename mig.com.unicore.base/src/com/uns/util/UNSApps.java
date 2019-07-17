/**
 * 
 */
package com.uns.util;

import com.sun.cmd.main.Cmd;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

public class UNSApps
  extends SvrProcess
{
  private static int count = 0;
  public static final int CHRG_VBBM = ++count;
  public static final int CHRG_VSHIP = ++count;
  public static final int ORG_PST = ++count;
  public static final int ORG_KTB = ++count;
  public static final int ORG_TLB = ++count;
  public static final int ORG_BTA = ++count;
  public static final int CHRG_DISKONSUSULAN = ++count;
  public static final int CHRG_INTRANSITCASH = ++count;
  public static final int CHRG_HUTANG_GAJI = ++count;
  public static final int CHRG_PSL = ++count;
  private static final int JML_CONTEXT = ++count;
  static final String[] contexts = new String[JML_CONTEXT];
  static final int[] reference_type = new int[JML_CONTEXT];
  static final int[] table_id = new int[JML_CONTEXT];
  public static CCache<Integer, Integer> i_Cache = new CCache(
    null, "AppsContext", JML_CONTEXT, 120, false);
  static final int[] cache = new int[JML_CONTEXT];
  static final int reference_type_product = 1;
  static final int reference_type_charge = 2;
  static final int reference_type_costElement = 3;
  static final int reference_type_UOM = 4;
  static final int reference_type_ORG = 5;
  static final int reference_type_WHS = 6;
  static final int reference_type_Locator = 7;
  static final int reference_type_Business_Partner = 8;
  static final int reference_type_ACTIVITY = 9;
  static final int reference_type_Account = 10;
  static final int table_Product = 208;
  static final int table_Charge = 313;
  static final int table_CostElememt = 770;
  static final int table_UOM = 146;
  static final int table_ORG = 155;
  static final int table_WHS = 190;
  static final int table_Locator = 207;
  static final int table_BP = 291;
  static final int table_ACTIVITY = 316;
  static final int table_Account = 176;
  
  static
  {
    table_id[CHRG_VBBM] = 313;
    reference_type[CHRG_VBBM] = 2;
    contexts[CHRG_VBBM] = "CHARGE_VBBM";
    
    table_id[CHRG_VSHIP] = 313;
    reference_type[CHRG_VSHIP] = 2;
    contexts[CHRG_VSHIP] = "CHARGE_VSHIP";
    
    table_id[ORG_PST] = 155;
    reference_type[ORG_PST] = 5;
    contexts[ORG_PST] = "ORG_PST";
    
    table_id[ORG_KTB] = 155;
    reference_type[ORG_KTB] = 5;
    contexts[ORG_KTB] = "ORG_KTB";
    
    table_id[ORG_TLB] = 155;
    reference_type[ORG_TLB] = 5;
    contexts[ORG_TLB] = "ORG_TLB";
    
    table_id[ORG_BTA] = 155;
    reference_type[ORG_BTA] = 5;
    contexts[ORG_BTA] = "ORG_BTA";
    
    table_id[CHRG_DISKONSUSULAN] = 313;
    reference_type[CHRG_DISKONSUSULAN] = 2;
    contexts[CHRG_DISKONSUSULAN] = "CHRG_DISKONSUSULAN";
    
    table_id[CHRG_INTRANSITCASH] = 313;
    reference_type[CHRG_INTRANSITCASH] = 2;
    contexts[CHRG_INTRANSITCASH] = "CHRG_INTRANSITCASH";
    
    table_id[CHRG_HUTANG_GAJI] = 313;
    reference_type[CHRG_HUTANG_GAJI] = 2;
    contexts[CHRG_HUTANG_GAJI] = "CHRG_HUTANG_GAJI";
    
    table_id[CHRG_PSL] = 313;
    reference_type[CHRG_PSL] = 2;
    contexts[CHRG_PSL] = "CHRG_PSL";
  }
  
  public static Integer getRef(int contextID)
  {
    return Integer.valueOf(getRefAsInt(contextID));
  }
  
  public static int getRefAsInt(int contextID)
  {
    Integer referenceID = (Integer)i_Cache.get(Integer.valueOf(contextID));
    if ((referenceID != null) && (referenceID.intValue() != 0)) {
      return referenceID.intValue();
    }
    if (!Cmd.isAlive()) {
      Cmd.start();
    }
    MUNSAppsContext mAppsCtx = null;
    String TrxName = Trx.createTrxName();
    
    mAppsCtx = MUNSAppsContext.get(contextID, Env.getCtx(), TrxName);
    if (mAppsCtx == null)
    {
      loadContextToDB(contextID, Env.getCtx(), TrxName);
      mAppsCtx = MUNSAppsContext.get(contextID, Env.getCtx(), TrxName);
    }
    switch (reference_type[contextID])
    {
    case 1: 
      referenceID = Integer.valueOf(mAppsCtx.getM_Product_ID());
      break;
    case 2: 
      referenceID = Integer.valueOf(mAppsCtx.getC_Charge_ID());
      break;
    case 3: 
      referenceID = Integer.valueOf(mAppsCtx.getM_CostElement_ID());
      break;
    case 4: 
      referenceID = Integer.valueOf(mAppsCtx.getC_UOM_ID());
      break;
    case 5: 
      referenceID = Integer.valueOf(mAppsCtx.getAD_OrgTrx_ID());
      break;
    case 6: 
      referenceID = Integer.valueOf(mAppsCtx.getM_Warehouse_ID());
      break;
    case 7: 
      referenceID = Integer.valueOf(mAppsCtx.getM_Locator_ID());
      break;
    case 8: 
      referenceID = Integer.valueOf(mAppsCtx.getC_BPartner_ID());
      break;
    case 9: 
      referenceID = Integer.valueOf(mAppsCtx.getC_Activity_ID());
      break;
    case 10: 
      referenceID = Integer.valueOf(mAppsCtx.getC_ValidCombination_ID());
    }
    if (referenceID.intValue() != 0) {
      i_Cache.put(Integer.valueOf(contextID), referenceID);
    } else {
      throw new AdempiereException(
        "Fatal Error: You have not defined the ReferenceID in DB for the UNS-Context of:[" + 
        contexts[contextID] + 
        "].");
    }
    return referenceID.intValue();
  }
  
  public int testReference()
  {
    Integer testGetProduct = getRef(CHRG_VBBM);
    
    return testGetProduct.intValue();
  }
  
  public String loadUndefinedUNSContext(Properties ctx, String trxName)
  {
    String sql = "Delete FROM UNS_AppsContext";
    boolean success = DB.executeUpdate(sql, trxName) != -1;
    sql = "Delete FROM UNS_AppsReff";
    if (success) {
      success = DB.executeUpdate(sql, trxName) != -1;
    }
    if (!success) {
      throw new AdempiereException("Failed on delete previous context");
    }
    int n = 0;
    String msg = "The number of records in Apps Contect is ";
    for (int i = 1; i < contexts.length; i++)
    {
      loadContextToDB(i, ctx, trxName);
      n++;
    }
    return msg + n;
  }
  
  protected static void loadContextToDB(int DefinedContextID, Properties ctx, String TrxName)
  {
    String DefinedContext = contexts[DefinedContextID];
    if (DefinedContext == null) {
      throw new AdempiereException(
        "Fatal Error: You have not defined the context name for id of:[" + 
        DefinedContextID + "]");
    }
    int appsRefID = 
      DB.getSQLValue(
      TrxName, 
      "SELECT UNS_AppsReff_ID FROM UNS_AppsReff WHERE UNS_AppsReff_ID=?", 
      DefinedContextID);
    
    MUNSAppsReff UNSAppsReff = null;
    MUNSAppsContext AppsContext = null;
    if (appsRefID == -1)
    {
      UNSAppsReff = new MUNSAppsReff(ctx, 0, TrxName);
      AppsContext = new MUNSAppsContext(ctx, 0, TrxName);
    }
    else
    {
      UNSAppsReff = new MUNSAppsReff(ctx, MUNSAppsReff.getAllIDs(
        "UNS_AppsReff", "UNS_AppsReff_ID=" + appsRefID, 
        TrxName)[0], TrxName);
      AppsContext = new MUNSAppsContext(ctx, MUNSAppsContext.getAllIDs(
        "UNS_AppsContext", "UNS_AppsReff_ID=" + appsRefID, 
        TrxName)[0], TrxName);
    }
    UNSAppsReff.setName(contexts[DefinedContextID]);
    UNSAppsReff.setUNS_AppsReff_ID(DefinedContextID);
    if (!UNSAppsReff.save()) {
      throw new AdempiereException(
        "Fatal Error: Problem when initializing Apps-Ref for context of:[" + 
        DefinedContext + "]");
    }
    AppsContext.setUNS_AppsReff_ID(DefinedContextID);
    AppsContext.setAD_Table_ID(table_id[DefinedContextID]);
    if (!AppsContext.save()) {
      throw new AdempiereException(
        "Fatal Error: Problem when initializing Apps-Context  for context of:[" + 
        DefinedContext + "]");
    }
  }
  
  protected void prepare() {}
  
  protected String doIt()
    throws Exception
  {
    return loadUndefinedUNSContext(getCtx(), get_TrxName());
  }
}
