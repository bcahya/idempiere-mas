/**
 * 
 */
package com.uns.util;


import com.sun.cmd.main.Cmd;
import java.util.Properties;
import org.compiere.util.Msg;
import org.compiere.util.ValueNamePair;

public class ErrorMsg
{
  public static void setErrorMsg(Properties ctx, String AD_Message_Title, String AD_Message_Detail)
  {
    ValueNamePair vnp = 
      new ValueNamePair(Msg.getMsg(ctx, AD_Message_Title), 
      Msg.getMsg(ctx, AD_Message_Detail));
    ctx.put("org.compiere.util.CLogger.lastError", vnp);
    if (!Cmd.isAlive()) {
      Cmd.start();
    }
  }
}
