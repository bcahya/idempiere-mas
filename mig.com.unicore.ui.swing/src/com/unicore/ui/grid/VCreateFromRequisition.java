/**
 * 
 */
package com.unicore.ui.grid;

import static org.compiere.model.SystemIDs.COLUMN_M_MOVEMENT_DESTINATIONWWAREHOUSE_ID;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.compiere.apps.AEnv;
import org.compiere.grid.VCreateFromDialog;
import org.compiere.grid.ed.VLocator;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MLocator;
import org.compiere.model.MLocatorLookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MMovement;
import org.compiere.model.Query;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

/**
 * @author ALBURHANY
 *
 */
public class VCreateFromRequisition extends CreateFromRequisition implements ActionListener, VetoableChangeListener {

	private VCreateFromDialog dialog;
	/**
	 * 
	 */
	public VCreateFromRequisition(GridTab mTab) {
		super(mTab);
		if (log.isLoggable(Level.INFO)) log.info(getGridTab().toString());
		
		dialog = new VCreateFromDialog(this, getGridTab().getWindowNo(), true);
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit())
				return;
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

	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
	//
	private JLabel warehouseLabel = new JLabel();
	private VLookup warehouseField;
	
	private JLabel requisitionLabel = new JLabel();
	private JComboBox<KeyNamePair> requisitionField = new JComboBox<KeyNamePair>();
		
	private JCheckBox isSeeStock = new JCheckBox();
	private JLabel locatorLabel = new JLabel();
	private VLocator locatorField = new VLocator();
	
	/**
	 *  Dynamic Init
	 *  @throws Exception if Lookups cannot be initialized
	 *  @return true if initialized
	 */
	public boolean dynInit() throws Exception
	{
		log.config("");
		super.dynInit();
		dialog.setTitle(getTitle());
		isSeeStock.setSelected(true);
		isSeeStock.addActionListener(this);
		
		MLocatorLookup locator = new MLocatorLookup(Env.getCtx(), p_WindowNo, -1);
		locatorField = new VLocator ("M_Locator_ID", true, false, true,	locator, p_WindowNo);

		initBPartner(false);	
		
		return true;
	}   //  dynInit

	/**
	 *  Static Init.
	 *  <pre>
	 *  parameterPanel
	 *      parameterBankPanel
	 *      parameterStdPanel
	 *          bPartner/order/invoice/shopment/licator Label/Field
	 *  dataPane
	 *  southPanel
	 *      confirmPanel
	 *      statusBar
	 *  </pre>
	 *  @throws Exception
	 */
    private void jbInit() throws Exception
    {
    	warehouseLabel.setText(Msg.getElement(Env.getCtx(), "DestinationWarehouse_ID"));
    	requisitionLabel.setText("Requisition / Contract");
    	locatorLabel.setText(Msg.translate(Env.getCtx(), "M_Locator_ID"));
    	isSeeStock.setText(Msg.getMsg(Env.getCtx(), "AvaliableStockOnly", true));
    	isSeeStock.setToolTipText(Msg.getMsg(Env.getCtx(), "AvaliableStockOnly", false));

    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	CPanel parameterStdPanel = new CPanel(new GridBagLayout());
    	parameterPanel.add(parameterStdPanel, BorderLayout.CENTER);

    	parameterStdPanel.add(warehouseLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	if (warehouseField != null)
    	{	
    		parameterStdPanel.add(warehouseField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	}
    	parameterStdPanel.add(requisitionLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(requisitionField,  new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(locatorLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(locatorField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterStdPanel.add(isSeeStock, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));    	
    }   //  jbInit

	/*************************************************************************/

	private boolean 	m_actionActive = false;
	
	/**
	 *  Action Listener
	 *  @param e event
	 */
	public void actionPerformed(ActionEvent e)
	{
		boolean seestock = isSeeStock.isSelected();
		if (log.isLoggable(Level.CONFIG)) log.config("Action=" + e.getActionCommand());
		
		if (m_actionActive)
			return;
		m_actionActive = true;
		if (log.isLoggable(Level.CONFIG)) log.config("Action=" + e.getActionCommand());
		//  Order
		if (e.getSource().equals(requisitionField) || e.getSource().equals(isSeeStock))
		{
			KeyNamePair pp = (KeyNamePair)requisitionField.getSelectedItem();
			int C_Order_ID = 0;
			if (pp != null)
				C_Order_ID = pp.getKey();
			loadOrder(C_Order_ID, locatorField.getValue()!=null?((Integer)locatorField.getValue()).intValue():0,
					seestock, (Integer) warehouseField.getValue());
		}
		m_actionActive = false;
	}   //  actionPerformed

	/**
	 *  Change Listener
	 *  @param e event
	 */
	public void vetoableChange (PropertyChangeEvent e)
	{
		if (log.isLoggable(Level.CONFIG)) log.config(e.getPropertyName() + "=" + e.getNewValue());
		dialog.tableChanged(null);
	}   //  vetoableChange
	
	/**
	 *  Load Data - Order
	 *  @param C_Order_ID Order
	 *  @param forInvoice true if for invoice vs. delivery qty
	 *  @param M_Locator_ID
	 */
	protected void loadOrder (int M_Requisition_ID, int M_Locator_ID, boolean isSeeStock, int DestinationWarehouse_ID)
	{
		loadTableOIS(getRequisitionData(M_Requisition_ID, isSeeStock, M_Locator_ID, DestinationWarehouse_ID));
	}   //  LoadOrder
	
	/**
	 *  Load Order/Invoice/Shipment data into Table
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
		
		MiniTable swingTable = (MiniTable) miniTable;
		TableColumn col = swingTable.getColumn(3);
		col.setCellEditor(new InnerLocatorTableCellEditor());
	}

	/**
	 * Custom cell editor for setting locator from minitable.
	 *
	 * @author Daniel Tamm
	 *
	 */
	public class InnerLocatorTableCellEditor extends AbstractCellEditor implements TableCellEditor {

		/**
		 *
		 */
		private static final long serialVersionUID = -7143484413792778213L;
		KeyNamePair currentValue;
		JTextField 	editor;

		public Object getCellEditorValue() {
			String locatorValue = editor.getText();
			MLocator loc = null;
			try {
				// Lookup locator using value
				loc = new Query(Env.getCtx(), MLocator.Table_Name, "value=?", null)
									.setParameters(locatorValue)
									.setClient_ID()
									.first();
				// Set new keyNamePair for minitable
				currentValue = getLocatorKeyNamePair(loc.get_ID());

			} catch (Exception e) {
				String message = Msg.getMsg(Env.getCtx(), "Invalid") + " " + editor.getText();
				JOptionPane.showMessageDialog(null, message);
			}
			return(currentValue);

		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {

			currentValue = (KeyNamePair)value;
			editor = new JTextField();
			editor.setText(currentValue.getName());
			return(editor);

		}

	}

	@Override
	public Object getWindow() {
		return dialog;
	}
	
	protected void initBPartner (boolean forInvoice) throws Exception
	{
		//  load BPartner
		int AD_Column_ID = COLUMN_M_MOVEMENT_DESTINATIONWWAREHOUSE_ID;        //  C_Invoice.C_BPartner_ID
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		warehouseField = new VLookup ("M_Warehouse_ID", true, true, true, lookup);
		int DestinationWarehouse_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, MMovement.COLUMNNAME_DestinationWarehouse_ID);

		warehouseField.setValue(new Integer(DestinationWarehouse_ID));
		initBPOrderDetails(DestinationWarehouse_ID, forInvoice);
	}   //  initBPartner

	protected void initBPOrderDetails (int C_BPartner_ID, boolean forInvoice)
	{
		if (log.isLoggable(Level.CONFIG)) log.config("C_BPartner_ID=" + C_BPartner_ID);
		KeyNamePair pp = new KeyNamePair(0,"");
		//  load PO Orders - Closed, Completed
		requisitionField.removeActionListener(this);
		requisitionField.removeAllItems();
		requisitionField.addItem(pp);
		requisitionField.addActionListener(this);
		dialog.pack();

		initBPDetails(C_BPartner_ID);
	}   //  initBPartnerOIS
	
	public void initBPDetails(int C_BPartner_ID) 
	{
		initBPInvoiceDetails(C_BPartner_ID);
	}
	
	private void initBPInvoiceDetails(int C_BPartner_ID)
	{
		if (log.isLoggable(Level.CONFIG)) log.config("C_BPartner_ID" + C_BPartner_ID);
		
		ArrayList<KeyNamePair> list = loadRequisitionData(C_BPartner_ID);
		for(KeyNamePair knp : list)
			requisitionField.addItem(knp);
	}
	
	protected void loadInvoice (int C_Invoice_ID, int M_Locator_ID)
	{
		loadTableOIS(getRequisitionData(C_Invoice_ID, isSeeStock.isSelected(), M_Locator_ID, (Integer) warehouseField.getValue()));
	}
}
