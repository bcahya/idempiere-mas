/**
 * 
 */
package com.unicore.ui.grid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.apps.form.WCreateFromWindow;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WLocatorEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.editor.WStringEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.model.GridTab;
import org.compiere.model.MColumn;
import org.compiere.model.MLocatorLookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MProduct;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Space;
import org.zkoss.zul.Vlayout;

import com.unicore.base.model.MInvoice;
import com.unicore.model.MUNSPackingListOrder;

/**
 * @author root
 *
 */
public class WCreateFromPackingListOrder extends CreateFromPackingListOrder implements EventListener<Event>, ValueChangeListener{

	private WCreateFromWindow window;
	/**
	 * @param gridTab
	 */
	public WCreateFromPackingListOrder(GridTab gridTab) {
		super(gridTab);
		log.info(getGridTab().toString());
		
		window = new WCreateFromWindow(this, getGridTab().getWindowNo());
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit())
				return;
			zkInit();
			setInitOK(true);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
			setInitOK(false);
			throw new AdempiereException(e.getMessage());
		}
		AEnv.showWindow(window);
	}

	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
		
	protected Label bPartnerLabel = new Label();
	protected WEditor bPartnerField;
	protected Button button = new Button();
	protected Label orderLabel = new Label();
	protected Listbox orderField = ListboxFactory.newDropdownListbox();
	
    protected Label invoiceLabel = new Label();
    protected Listbox invoiceField = ListboxFactory.newDropdownListbox();
	protected Checkbox sameWarehouseCb = new Checkbox();
	protected Label locatorLabel = new Label();
	protected WLocatorEditor locatorField = new WLocatorEditor();
	protected Label upcLabel = new Label();
	protected WStringEditor upcField = new WStringEditor();
	
	protected void zkInit() throws Exception
	{
    	bPartnerLabel.setText(Msg.getElement(Env.getCtx(), "C_BPartner_ID"));
		orderLabel.setText(Msg.getElement(Env.getCtx(), "C_Order_ID", false));
		invoiceLabel.setText(Msg.getElement(Env.getCtx(), "C_Invoice_ID", false));
		locatorLabel.setText(Msg.translate(Env.getCtx(), "M_Locator_ID"));
        sameWarehouseCb.setText(Msg.getMsg(Env.getCtx(), "FromSameWarehouseOnly", true));
        sameWarehouseCb.setTooltiptext(Msg.getMsg(Env.getCtx(), "FromSameWarehouseOnly", false));
        upcLabel.setText(Msg.getElement(Env.getCtx(), "UPC", false));
        button.setTooltiptext("Refresh");
        button.setTooltip("Refresh");
        button.setName("Refresh");
        button.setLabel("Refresh");
        button.addActionListener(new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				int C_BPartner_ID = bPartnerField.getValue() == null ? 0: (Integer) bPartnerField.getValue();
				initBPOrderDetails(C_BPartner_ID, false);			
			}
		});
		Vlayout vlayout = new Vlayout();
		vlayout.setVflex("1");
		vlayout.setWidth("100%");
    	Panel parameterPanel = window.getParameterPanel();
		parameterPanel.appendChild(vlayout);
		
		Grid parameterStdLayout = GridFactory.newGridLayout();
    	vlayout.appendChild(parameterStdLayout);
		
		Rows rows = (Rows) parameterStdLayout.newRows();
		Row row = rows.newRow();
		row.appendChild(bPartnerLabel.rightAlign());
		if (bPartnerField != null) {
			row.appendChild(bPartnerField.getComponent());
			bPartnerField.fillHorizontal();
		}
	
		row.appendChild(orderLabel.rightAlign());
		row.appendChild(orderField);
		orderField.setHflex("1");
		
		row = rows.newRow();
		row.appendChild(locatorLabel.rightAlign());
		row.appendChild(locatorField.getComponent());
		row.appendChild(invoiceLabel.rightAlign());
		row.appendChild(invoiceField);
		invoiceField.setHflex("1");  
		row = rows.newRow();
		row.appendChild(new Space());
		row.appendChild(sameWarehouseCb);
		
		row = rows.newRow();
		row.appendChild(upcLabel.rightAlign());
		row.appendChild(upcField.getComponent());
		
		row.appendCellChild(button);
	}

	private boolean 	m_actionActive = false;
	
	/**
	 *  Action Listener
	 *  @param e event
	 * @throws Exception 
	 */
	public void onEvent(Event e) throws Exception
	{
		if (m_actionActive)
			return;
		m_actionActive = true;
		
		//  Order
		if (e.getTarget().equals(orderField))
		{
			KeyNamePair pp = orderField.getSelectedItem().toKeyNamePair();
			if (pp == null || pp.getKey() == 0)
				;
			else
			{
				int C_Order_ID = pp.getKey();
				//  set Invoice and Shipment to Null
				invoiceField.setSelectedIndex(-1);
				loadOrder(C_Order_ID, false, locatorField.getValue()!=null?((Integer)locatorField.getValue()).intValue():0);
			}
		}
		//  Invoice
		else if (e.getTarget().equals(invoiceField))
		{
			KeyNamePair pp = invoiceField.getSelectedItem().toKeyNamePair();
			if (pp == null || pp.getKey() == 0)
				;
			else
			{
				int C_Invoice_ID = pp.getKey();
				//  set Order and Shipment to Null
				orderField.setSelectedIndex(-1);
				loadInvoice(C_Invoice_ID, locatorField.getValue()!=null?((Integer)locatorField.getValue()).intValue():0);
			}
		}
		//sameWarehouseCb
        else if (e.getTarget().equals(sameWarehouseCb))
        {
        	int C_BPartner_ID = bPartnerField.getValue() == null ? 0: (Integer) bPartnerField.getValue();
			initBPOrderDetails(C_BPartner_ID, false);			
        }	
		else if (e.getTarget().equals(upcField.getComponent()))
		{
			checkProductUsingUPC();
		}
		
		m_actionActive = false;
	}
	
	/**
	 * Checks the UPC value and checks if the UPC matches any of the products in the
	 * list.
	 */
	private void checkProductUsingUPC()
	{
		String upc = upcField.getDisplay();
		//DefaultTableModel model = (DefaultTableModel) dialog.getMiniTable().getModel();
		ListModelTable model = (ListModelTable) window.getWListbox().getModel();
		
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
				model.updateComponent(row, row);
			}
		}
		upcField.setValue("");
	}

	/**
	 * Finds the row where a given product is. If the product is not found
	 * in the table -1 is returned.
	 * @param M_Product_ID
	 * @return  Row of the product or -1 if non existing.
	 * 
	 */
	private int findProductRow(int M_Product_ID)
	{
		//DefaultTableModel model = (DefaultTableModel)dialog.getMiniTable().getModel();
		ListModelTable model = (ListModelTable) window.getWListbox().getModel();
		KeyNamePair kp;
		for (int i=0; i<model.getRowCount(); i++) {
			kp = (KeyNamePair)model.getValueAt(i, 4);
			if (kp.getKey()==M_Product_ID) {
				return(i);
			}
		}
		return(-1);
	}
		
	/**
	 *  Change Listener
	 *  @param e event
	 */
	public void valueChange (ValueChangeEvent e)
	{
		if (log.isLoggable(Level.CONFIG)) log.config(e.getPropertyName() + "=" + e.getNewValue());

		//  BPartner - load Order/Invoice/Shipment
		if (e.getPropertyName().equals("C_BPartner_ID"))
		{
			int C_BPartner_ID = 0; 
			if (e.getNewValue() != null){
				C_BPartner_ID = ((Integer)e.getNewValue()).intValue();
			}
			
			initBPOrderDetails (C_BPartner_ID, true);
		}
		window.tableChanged(null);
	}   //  vetoableChange
	
	/**************************************************************************
	 *  Load BPartner Field
	 *  @param forInvoice true if Invoices are to be created, false receipts
	 *  @throws Exception if Lookups cannot be initialized
	 */
	protected void initBPartner (boolean forInvoice) throws Exception
	{
		int Column_C_BPartner_ID = MColumn.getColumn_ID(MInvoice.Table_Name, MInvoice.COLUMNNAME_C_BPartner_ID);
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, Column_C_BPartner_ID, DisplayType.Search);
		bPartnerField = new WSearchEditor (MInvoice.COLUMNNAME_C_BPartner_ID, true, false, true, lookup);
		
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

	/**
	 * Init Details - load invoices not shipped
	 * @param C_BPartner_ID BPartner
	 */
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
		{
			invoiceField.addItem(knp);
		}
		
		invoiceField.addActionListener(this);
		upcField.addValueChangeListener(this);
	}
	
	/**
	 *  Load PBartner dependent Order/Invoice/Shipment Field.
	 *  @param C_BPartner_ID BPartner
	 *  @param forInvoice for invoice
	 */
	protected void initBPOrderDetails (int C_BPartner_ID, boolean forInvoice)
	{
		if (log.isLoggable(Level.CONFIG)) log.config("C_BPartner_ID=" + C_BPartner_ID);
		KeyNamePair pp = new KeyNamePair(0,"");
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
				orderField.setSelectedKeyNamePair(knp);
			}
		}
		
		if(C_Order_ID > 0)
		{
			int M_Locator_ID = locatorField.getValue()!=null?((Integer)locatorField.getValue()).intValue():0;
			loadTableOIS(getOrderData(C_Order_ID, forInvoice, M_Locator_ID));
		}
		
		orderField.addActionListener(this);
		initBPDetails(C_BPartner_ID);
	}   //  initBPartnerOIS
	
	public void initBPDetails(int C_BPartner_ID) 
	{
		initBPInvoiceDetails(C_BPartner_ID);
	}

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
	 *  Load Data - Invoice
	 *  @param C_Invoice_ID Invoice
	 *  @param M_Locator_ID
	 */
	protected void loadInvoice (int C_Invoice_ID, int M_Locator_ID)
	{
		loadTableOIS(getInvoiceData(C_Invoice_ID, M_Locator_ID));
	}
		
	/**
	 *  Load Order/Invoice/Shipment data into Table
	 *  @param data data
	 */
	protected void loadTableOIS (Vector<?> data)
	{
		window.getWListbox().clear();
		
		//  Remove previous listeners
		window.getWListbox().getModel().removeTableModelListener(window);
		//  Set Model
		ListModelTable model = new ListModelTable(data);
		model.addTableModelListener(window);
		window.getWListbox().setData(model, getOISColumnNames());
		//
		
		configureMiniTable(window.getWListbox());
	}   //  loadOrder
	
	public void showWindow()
	{
		window.setVisible(true);
	}
	
	public void closeWindow()
	{
		window.dispose();
	}

	@Override
	public Object getWindow() {
		return window;
	}

	public boolean dynInit() throws Exception
	{
		log.config("");
		super.dynInit();
		window.setTitle(getTitle());
		sameWarehouseCb.setSelected(true);
		sameWarehouseCb.addActionListener(this);

		MLocatorLookup locator = new MLocatorLookup(Env.getCtx(), p_WindowNo, -1);	
		locatorField = new WLocatorEditor ("M_Locator_ID", true, false, true, locator, p_WindowNo);
		
		initBPartner(false);

		upcField = new WStringEditor ("UPC", false, false, true, 10, 30, null, null);
		upcField.getComponent().addEventListener(Events.ON_CHANGE, this);

		return true;
	}   //  dynInit
}
