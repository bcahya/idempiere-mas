/**
 * 
 */
package com.unicore.ui.factory;

import java.util.Properties;

import javax.swing.JComponent;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Callback;
import org.adempiere.webui.component.Messagebox;
import org.compiere.model.GridTab;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

import com.unicore.ui.UNSMsgboxInfo;
import com.uns.util.IMsgBoxFactory;
import com.uns.util.MessageBox;

/**
 * @author Haryadi
 *
 */
public class WDefaultMsgBoxFactory implements IMsgBoxFactory, EventListener<Event> {

	/* (non-Javadoc)
	 * @see com.uns.util.IMsgBoxFactory#showMsg(java.util.Properties, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public int showMsg(PO po, Properties ctx, String msg, String title, int btn, int icon) 
	{
		int retVal = Messagebox.IGNORE;
		
		if (po == null) {
			retVal = Messagebox.showDialog(msg, title, getBrowserBtn(btn), getBrowserIcon(icon), null, true);
		}
		else {
			UNSMsgboxInfo msgboxInfoTmp = null; 
			
			if (po.getProcessInfo() != null)
				msgboxInfoTmp = po.getProcessInfo().getMsgboxInfo();
			else
				msgboxInfoTmp = analyzeMsgboxInfo(po.getGridTab());
			
			final UNSMsgboxInfo msgboxInfo = msgboxInfoTmp;
			
			if (msgboxInfo == null) {
				retVal = Messagebox.showDialog(msg, title, getBrowserBtn(btn), getBrowserIcon(icon), null, false);
			}
			else {
				synchronized (msgboxInfo) {
					try {
						msgboxInfo.setMsg(msg);
						msgboxInfo.setTitle(title);
						msgboxInfo.setButtons(getBrowserBtn(btn));
						msgboxInfo.setIcon(getBrowserIcon(icon));
						msgboxInfo.setModalMode(false);
						msgboxInfo.setNeedToShow(true);
						
						Thread.sleep(100);
						
						msgboxInfo.wait();
					}
					catch (InterruptedException ex) {
						ex.printStackTrace();
						throw new AdempiereException("Failed: cannot hold MessageboxInfo.");
					}
				}
				
				retVal = msgboxInfo.getResult();
			}
		}
		
		retVal = convertBrowserReturn(retVal);
		return retVal;
	}
	
	private UNSMsgboxInfo analyzeMsgboxInfo(GridTab gTab)
	{
		UNSMsgboxInfo msgboxInfo = null;
		
		if (gTab != null) 
		{
			msgboxInfo = gTab.getMsgboxInfo();
			
			if (msgboxInfo == null && gTab.getParentTab() != null)
				return analyzeMsgboxInfo(gTab.getParentTab());
		}
		
		return msgboxInfo;
	}

	/* (non-Javadoc)
	 * @see com.uns.util.IMsgBoxFactory#showMsg(java.util.Properties, javax.swing.JComponent, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public int showMsg(PO po, Properties ctx, JComponent component, String msg, String title, int btn, int icon) 
	{
		int retVal;
		retVal = Messagebox.showDialog(msg, title, getBrowserBtn(btn), getBrowserIcon(icon), null, false);
		retVal = convertBrowserReturn(retVal);
		return retVal;
	}

	@Override
	public int showMsg(Properties ctx, ProcessInfo pi, String msg, String title, int btn, int icon) 
	{
		int retVal = Messagebox.IGNORE;
		
		if (pi == null) {
			retVal = Messagebox.showDialog(msg, title, getBrowserBtn(btn), getBrowserIcon(icon), null, true);
		}
		else {
			UNSMsgboxInfo msgboxInfo = pi.getMsgboxInfo(); 
			
			if (msgboxInfo == null) {
				retVal = Messagebox.showDialog(msg, title, getBrowserBtn(btn), getBrowserIcon(icon), null, true);
			}
			else {
				synchronized (msgboxInfo) {
					try {
						msgboxInfo.setMsg(msg);
						msgboxInfo.setTitle(title);
						msgboxInfo.setButtons(getBrowserBtn(btn));
						msgboxInfo.setIcon(getBrowserIcon(icon));
						msgboxInfo.setModalMode(false);
						msgboxInfo.setNeedToShow(true);
						
						Thread.sleep(100);
						
						msgboxInfo.wait();
					}
					catch (InterruptedException ex) {
						ex.printStackTrace();
						throw new AdempiereException("Failed: cannot hold MessageboxInfo.");
					}
				}
				
				retVal = msgboxInfo.getResult();
			}
		}
		
		retVal = convertBrowserReturn(retVal);
		return retVal;
	}

	/**
	 * 
	 * @param btn
	 * @return
	 */
	protected static int getBrowserBtn(int btn) 
	{
		int btnVal = MessageBox.OK;
		
		switch (btn) {
			case MessageBox.OK : 
				btnVal = Messagebox.OK;
				break;
			case MessageBox.OKCANCEL : 
				btnVal = Messagebox.OK + Messagebox.CANCEL;
				break;
			case MessageBox.YESNO : btnVal = Messagebox.YES + Messagebox.NO;
				break;
			case MessageBox.YESNOCANCEL : btnVal = Messagebox.YES + Messagebox.NO + Messagebox.CANCEL;
				break;
		}
		
		return btnVal;
	}
	
	/**
	 * 
	 * @param icon
	 * @return
	 */
	protected static String getBrowserIcon(int icon)
	{
		String iconVal = Messagebox.NONE;
		
		switch (icon) {

		case MessageBox.ICONERROR :
			iconVal = Messagebox.ERROR;
			break;
		case MessageBox.ICONINFORMATION :
			iconVal = Messagebox.INFORMATION;
			break;
		case MessageBox.ICONQUESTION :
			iconVal = Messagebox.QUESTION;
			break;
		case MessageBox.ICONWARNING :
			iconVal = Messagebox.EXCLAMATION;
			break;
		}
		
		return iconVal;
	}
	
	/**
	 * 
	 * @param retVal
	 * @return
	 */
	protected static int convertBrowserReturn(int retVal) 
	{
		int properReturn = Messagebox.ABORT;
		
		switch (retVal) {
		
		case Messagebox.IGNORE :
			properReturn = MessageBox.RETURN_NONE;
			break;
		case Messagebox.OK :
			properReturn = MessageBox.RETURN_OK;
			break;
		case Messagebox.CANCEL :
			properReturn = MessageBox.RETURN_CANCEL;
			break;
		case Messagebox.YES :
			properReturn = MessageBox.RETURN_YES;
			break;
		case Messagebox.NO :
			properReturn = MessageBox.RETURN_NO;
			break;
		}
		
		return properReturn;
	}

	@Override
	public boolean isDesktopClient() {
		return false;
	}

	@Override
	public void onEvent(Event event) throws Exception {
	
		if (event.equals("showMsgbox"))
		{
			final UNSMsgboxInfo msgboxInfo = (UNSMsgboxInfo) event.getData();
			
			Messagebox.showDialog(
				msgboxInfo.getMsg(), msgboxInfo.getTitle(), msgboxInfo.getButtons(), msgboxInfo.getIcon(), 
				new Callback<Integer>() {
					@Override
					public void onCallback(Integer result) {
						synchronized (msgboxInfo) {
							msgboxInfo.setResult(result);
							msgboxInfo.setNeedToShow(false);
							msgboxInfo.notify();
						}
					}});

		}
	}

}