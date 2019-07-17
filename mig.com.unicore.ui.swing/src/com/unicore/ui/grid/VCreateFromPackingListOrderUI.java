/**
 * UntaCore Customization.
 * @UntaSoft www.untasoft.com
 */
package com.unicore.ui.grid;

import static org.compiere.model.SystemIDs.COLUMN_C_INVOICE_C_BPARTNER_ID;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

import com.unicore.model.MUNSPackingListOrder;

public class VCreateFromPackingListOrderUI extends CreateFromPackingListOrder implements ActionListener, VetoableChangeListener
{

	private VCreateFromDialog dialog;

	public VCreateFromPackingListOrderUI(GridTab mTab)
	{
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
	}   //  VCreateFrom
	
	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
	//
	private JLabel bPartnerLabel = new JLabel();
	private VLookup bPartnerField;
	
	private JLabel orderLabel = new JLabel();
	private JComboBox<KeyNamePair> orderField = new JComboBox<KeyNamePair>();
	
	private JLabel invoiceLabel = new JLabel();
	private JComboBox<KeyNamePair> invoiceField = new JComboBox<KeyNamePair>();
	
	private JLabel upcLabel = new JLabel();
	private JTextField upcField = new JTextField();
	
	private JCheckBox sameWarehouseCb = new JCheckBox();
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
		sameWarehouseCb.setSelected(true);
		sameWarehouseCb.addActionListener(this);
		
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
    	bPartnerLabel.setText(Msg.getElement(Env.getCtx(), "C_BPartner_ID"));
    	orderLabel.setText(Msg.getElement(Env.getCtx(), "C_Order_ID", false));
    	locatorLabel.setText(Msg.translate(Env.getCtx(), "M_Locator_ID"));
    	invoiceLabel.setText(Msg.getElement(Env.getCtx(), "C_Invoice_ID", false));
    	sameWarehouseCb.setText(Msg.getMsg(Env.getCtx(), "FromSameWarehouseOnly", true));
    	sameWarehouseCb.setToolTipText(Msg.getMsg(Env.getCtx(), "FromSameWarehouseOnly", false));
        upcLabel.setText(Msg.getElement(Env.getCtx(), "UPC", false));

    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	CPanel parameterStdPanel = new CPanel(new GridBagLayout());
    	parameterPanel.add(parameterStdPanel, BorderLayout.CENTER);

    	parameterStdPanel.add(bPartnerLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	if (bPartnerField != null)
    	{	
    		parameterStdPanel.add(bPartnerField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	}
    	parameterStdPanel.add(orderLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(orderField,  new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    	parameterStdPanel.add(invoiceLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(invoiceField,  new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(locatorLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(locatorField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterStdPanel.add(sameWarehouseCb, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
        parameterStdPanel.add(upcLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        parameterStdPanel.add(upcField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
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
		if (log.isLoggable(Level.CONFIG)) log.config("Action=" + e.getActionCommand());
		
		if (m_actionActive)
			return;
		m_actionActive = true;
		if (log.isLoggable(Level.CONFIG)) log.config("Action=" + e.getActionCommand());
		//  Order
		if (e.getSource().equals(orderField))
		{
			KeyNamePair pp = (KeyNamePair)orderField.getSelectedItem();
			int C_Order_ID = 0;
			if (pp != null)
				C_Order_ID = pp.getKey();
			invoiceField.setSelectedIndex(-1);
			loadOrder(C_Order_ID, false, locatorField.getValue()!=null?((Integer)locatorField.getValue()).intValue():0);
		}
		//  Invoice
		else if (e.getSource().equals(invoiceField))
		{
			KeyNamePair pp = (KeyNamePair)invoiceField.getSelectedItem();
			int C_Invoice_ID = 0;
			if (pp != null)
				C_Invoice_ID = pp.getKey();
			orderField.setSelectedIndex(-1);
			loadInvoice(C_Invoice_ID, locatorField.getValue()!=null?((Integer)locatorField.getValue()).intValue():0);
		}
		//sameWarehouseCb
		else if (e.getSource().equals(sameWarehouseCb))
		{
			int C_BPartner_ID = bPartnerField.getValue() == null ? 0: (Integer) bPartnerField.getValue();
			initBPOrderDetails(C_BPartner_ID, false);
		}
		else if (e.getSource().equals(upcField))
		{
			checkProductUsingUPC();
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
	protected void loadOrder (int C_Order_ID, boolean forInvoice, int M_Locator_ID)
	{
		loadTableOIS(getOrderData(C_Order_ID, forInvoice, M_Locator_ID));
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
		int AD_Column_ID = COLUMN_C_INVOICE_C_BPARTNER_ID;        //  C_Invoice.C_BPartner_ID
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		bPartnerField = new VLookup ("C_BPartner_ID", true, false, true, lookup);
		int C_BPartner_ID = -1;
		int C_Order_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, MUNSPackingListOrder.COLUMNNAME_C_Order_ID);
		int C_Invoice_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, MUNSPackingListOrder.COLUMNNAME_C_Invoice_ID);
		if(C_Order_ID > 0)
		{
			C_BPartner_ID = DB.getSQLValue(null, "SELECT C_BPartner_ID FROM C_Order WHERE C_Order_ID = ?", C_Order_ID);
		}
		else if(C_Invoice_ID > 0)
		{
			C_BPartner_ID = DB.getSQLValue(null, "SELECT C_BPartner_ID FROM C_Order WHERE C_Invoice_ID = ?", C_Invoice_ID);
		}

		bPartnerField.setValue(new Integer(C_BPartner_ID));
		initBPOrderDetails(C_BPartner_ID, forInvoice);
	}   //  initBPartner

	protected void initBPOrderDetails (int C_BPartner_ID, boolean forInvoice)
	{
		if (log.isLoggable(Level.CONFIG)) log.config("C_BPartner_ID=" + C_BPartner_ID);
		KeyNamePair pp = new KeyNamePair(0,"");
		//  load PO Orders - Closed, Completed
		orderField.removeActionListener(this);
		orderField.removeAllItems();
		orderField.addItem(pp);
		
		ArrayList<KeyNamePair> list = loadOrderData(C_BPartner_ID, forInvoice, sameWarehouseCb.isSelected());
		int C_Order_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_Order_ID");
		for(KeyNamePair knp : list)
		{
			orderField.addItem(knp);
			if(knp.getKey() == C_Order_ID)
			{
				orderField.setSelectedItem(knp);
			}
		}
		
		if(C_Order_ID > 0)
		{
			int M_Locator_ID = locatorField.getValue()!=null?((Integer)locatorField.getValue()).intValue():0;
			loadTableOIS(getOrderData(C_Order_ID, forInvoice, M_Locator_ID));
		}
		orderField.addActionListener(this);
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

		//  load Shipments (Receipts) - Completed, Closed
		invoiceField.removeActionListener(this);
		invoiceField.removeAllItems();
		//	None
		KeyNamePair pp = new KeyNamePair(0,"");
		invoiceField.addItem(pp);
		
		ArrayList<KeyNamePair> list = loadInvoiceData(C_BPartner_ID);
		for(KeyNamePair knp : list)
			invoiceField.addItem(knp);
		
		invoiceField.setSelectedIndex(0);
		invoiceField.addActionListener(this);
		upcField.addActionListener(this);
	}
	
	protected void loadInvoice (int C_Invoice_ID, int M_Locator_ID)
	{
		loadTableOIS(getInvoiceData(C_Invoice_ID, M_Locator_ID));
	}
	
	private void checkProductUsingUPC()
	{
		String upc = upcField.getText();
		DefaultTableModel model = (DefaultTableModel)dialog.getMiniTable().getModel();
		// Lookup UPC
		List<MProduct> products = MProduct.getByUPC(Env.getCtx(), upc, null);
		for (MProduct product : products)
		{
			int row = findProductRow(product.get_ID());
			if (row >= 0)
			{
				BigDecimal qty = (BigDecimal)model.getValueAt(row, 1);
				model.setValueAt(qty, row, 1);
				model.setValueAt(Boolean.TRUE, row, 0);
				model.fireTableRowsUpdated(row, row);
			}
		}
		upcField.setText("");
		upcField.requestFocusInWindow();
	}
	
	private int findProductRow(int M_Product_ID)
	{
		DefaultTableModel model = (DefaultTableModel)dialog.getMiniTable().getModel();
		KeyNamePair kp;
		for (int i=0; i<model.getRowCount(); i++) {
			kp = (KeyNamePair)model.getValueAt(i, 4);
			if (kp.getKey()==M_Product_ID) {
				return(i);
			}
		}
		return(-1);
	}
}
