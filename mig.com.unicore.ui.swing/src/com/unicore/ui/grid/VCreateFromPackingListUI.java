/**
 * 
 */
package com.unicore.ui.grid;

import static org.compiere.model.SystemIDs.COLUMN_UNS_BP_CANVASSER_M_LOCATOR_ID;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.compiere.apps.AEnv;
import org.compiere.grid.VCreateFromDialog;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import com.uns.util.MessageBox;

/**
 * @author Burhani Adam
 *
 */
public class VCreateFromPackingListUI extends CreateFromPackingList
		implements ActionListener, VetoableChangeListener, TableModelListener {

	private VCreateFromDialog dialog;
	private int p_WindowNo;
	private JLabel locatorLabel = new JLabel();
	private VLookup locatorField;
	
	/**
	 * @param gridTab
	 */
	public VCreateFromPackingListUI(GridTab gridTab) {
		super(gridTab);
		if (log.isLoggable(Level.INFO)) log.info(getGridTab().toString());
		
		dialog = new VCreateFromDialog(this, getGridTab().getWindowNo(), true);
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if(!dynInit())
				return;;
			jbInit();
			setInitOK(true);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
			setInitOK(false);
		}
		AEnv.positionCenterWindow(AEnv.getWindow(p_WindowNo), dialog);

	}

	@Override
	public void tableChanged(TableModelEvent e)
	{
		if(e.getColumn() == 7)
		{
			BigDecimal qtyLocRequest = (BigDecimal) dialog.getMiniTable().getValueAt(e.getFirstRow(), 6);
			BigDecimal qtyRequest = (BigDecimal) dialog.getMiniTable().getValueAt(e.getFirstRow(), 7);
			if(qtyRequest.compareTo(qtyLocRequest) == 1)
			{
				String msg = "Qty Request bigger than Qty Locator Request";
				String title = Msg.getMsg(Env.getCtx(), "Notice");
				MessageBox.showMsg(null,
						Env.getCtx()
						, msg
						, title
						, MessageBox.ICONINFORMATION
						, MessageBox.ICONQUESTION);
			}
		}
	}

	@Override
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException {
		if (log.isLoggable(Level.CONFIG)) log.config(evt.getPropertyName() + "=" + evt.getNewValue());
		dialog.tableChanged(null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		int M_Locator_ID = locatorField.getValue() == null ? 0 : (Integer) locatorField.getValue();
		int UNS_PackingList_ID = (Env.getContextAsInt(Env.getCtx(), p_WindowNo, "UNS_PackingList_ID"));
		if(M_Locator_ID <= 0 || UNS_PackingList_ID <= 0)
			return;
		
		loadLines(UNS_PackingList_ID, M_Locator_ID);
		dialog.getMiniTable().getModel().addTableModelListener(dialog);
		dialog.getMiniTable().getModel().addTableModelListener(this);
	}
	
	private void jbInit() throws Exception
    {   
		locatorLabel.setText("Locator");
		
		CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	CPanel parameterStdPanel = new CPanel(new GridBagLayout());
    	parameterPanel.add(parameterStdPanel, BorderLayout.CENTER);
    	
    	parameterStdPanel.add(locatorLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    	parameterStdPanel.add(locatorField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    }
	
	public boolean dynInit() throws Exception
	{
		log.config("");
		super.dynInit();
		dialog.setTitle(getTitle());
		initLocator();
		locatorField.addActionListener(this);
		
		return true;
	}   //  dynInit
	
	/**
	 *  Load Data - Invoices
	 */
	protected void loadLines (int UNS_PackingList_ID, int M_Locator_ID)
	{
		loadTableOIS(getLines(UNS_PackingList_ID, M_Locator_ID));
	}   //  LoadOrder
	
	/**
	 *  Load onHand into Table
	 *  @param data data
	 */
	protected void loadTableOIS (Vector<?> data)
	{
		//  Remove previous listeners
		dialog.getMiniTable().getModel().removeTableModelListener(dialog);
		DefaultTableModel model = new DefaultTableModel(data, getOISColumnNames());
		model.addTableModelListener(dialog);
		dialog.getMiniTable().setModel(model);
		// 
		
		configureMiniTable(dialog.getMiniTable());
	}   //  loadOrder
	
	@Override
	public Object getWindow() {
		return dialog;
	}
	
	public void showWindow()
	{
		dialog.setVisible(true);
	}
	
	public void closeWindow()
	{
		dialog.dispose();
	}
	
	@Override
	protected void configureMiniTable(IMiniTable miniTable) {
		super.configureMiniTable(miniTable);
	}
	
	protected void initLocator()
	{
		int AD_Column_ID = COLUMN_UNS_BP_CANVASSER_M_LOCATOR_ID;      
		MLookup lookup = MLookupFactory.get(Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.TableDir);
		locatorField = new VLookup ("C_BPartner_ID", true, false, true, lookup);
	}
}