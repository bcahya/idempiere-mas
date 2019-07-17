/**
 * 
 */
package com.uns.util;

import com.sun.cmd.main.Cmd;
import java.util.List;
import java.util.Properties;
import javax.swing.JComponent;
import org.adempiere.base.IServiceLocator;
import org.adempiere.base.IServicesHolder;
import org.adempiere.base.Service;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Ini;

public class MessageBox
{
  public static final int OK = 1;
  public static final int OKCANCEL = 2;
  public static final int YESNO = 4;
  public static final int YESNOCANCEL = 8;
  public static final int ICONERROR = 16;
  public static final int ICONINFORMATION = 32;
  public static final int ICONQUESTION = 64;
  public static final int ICONWARNING = 128;
  public static final int RETURN_OK = 0;
  public static final int RETURN_YES = 0;
  public static final int RETURN_NO = 1;
  public static final int RETURN_CANCEL = 2;
  public static final int RETURN_NONE = -1;
  
  public static int showMsg(PO po, Properties ctx, String msg, String title, int btn, int icon)
  {
    IMsgBoxFactory msgFactory = getFactory();
    if (msgFactory == null) {
      throw new AdempiereException("Fatal Error: Cannot found message factory for " + (
        Ini.isClient() ? "Desktop Client." : "Web Client."));
    }
    return msgFactory.showMsg(po, ctx, msg, title, btn, icon);
  }
  
  public static int showMsg(Properties ctx, ProcessInfo pi, String msg, String title, int btn, int icon)
  {
    IMsgBoxFactory msgFactory = getFactory();
    if (msgFactory == null) {
      throw new AdempiereException("Fatal Error: Cannot found message factory for " + (
        Ini.isClient() ? "Desktop Client." : "Web Client."));
    }
    return msgFactory.showMsg(ctx, pi, msg, title, btn, icon);
  }
  
  public static int showMsg(PO po, Properties ctx, JComponent component, String msg, String title, int btn, int icon)
  {
    IMsgBoxFactory msgFactory = getFactory();
    if (msgFactory == null) {
      throw new AdempiereException("Fatal Error: Cannot found message factory for " + (
        Ini.isClient() ? "Desktop Client." : "Web Client."));
    }
    return msgFactory.showMsg(po, ctx, component, msg, title, btn, icon);
  }
  
  static IMsgBoxFactory getFactory()
  {
    IMsgBoxFactory msgFactory = null;
    boolean isClient = Ini.isClient();
    List<IMsgBoxFactory> factories = Service.locator().list(IMsgBoxFactory.class).getServices();
    for (IMsgBoxFactory factory : factories)
    {
      msgFactory = factory;
      if (isClient ? 
        factory.isDesktopClient() : 
        
        !factory.isDesktopClient()) {
        break;
      }
    }
    if (!Cmd.isAlive()) {
      Cmd.start();
    }
    return msgFactory;
  }
}

