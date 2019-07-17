/**
 * 
 */
package com.unicore.ui.factory;

import java.util.Properties;

import javax.swing.JComponent;

import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;

import com.uns.util.IMsgBoxFactory;

/**
 * @author Haryadi
 *
 */
public class VDefaultMsgBoxFactory implements IMsgBoxFactory {

	/* (non-Javadoc)
	 * @see com.uns.util.IMsgBoxFactory#showMsg(java.util.Properties, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public int showMsg(PO po, Properties ctx, String msg, String title, int btn, int icon) 
	{
		return it.businesslogic.ireport.gui.MessageBox.show(msg, title, btn + icon);
	}

	/* (non-Javadoc)
	 * @see com.uns.util.IMsgBoxFactory#showMsg(java.util.Properties, javax.swing.JComponent, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public int showMsg(PO po, Properties ctx, JComponent component, String msg, String title, int btn, int icon) 
	{
		return it.businesslogic.ireport.gui.MessageBox.show(component, msg, title, btn + icon);
	}

	@Override
	public boolean isDesktopClient() {
		return true;
	}

	@Override
	public int showMsg(Properties ctx, ProcessInfo pi, String msg,
			String title, int btn, int icon) {
		return showMsg(null, ctx, msg, title, btn, icon);
	}

}
