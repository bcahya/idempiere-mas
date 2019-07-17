package com.uns.util;

import java.util.Properties;
import javax.swing.JComponent;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;

public abstract interface IMsgBoxFactory
{
  public abstract int showMsg(PO paramPO, Properties paramProperties, String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract int showMsg(Properties paramProperties, ProcessInfo paramProcessInfo, String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract int showMsg(PO paramPO, Properties paramProperties, JComponent paramJComponent, String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract boolean isDesktopClient();
}
