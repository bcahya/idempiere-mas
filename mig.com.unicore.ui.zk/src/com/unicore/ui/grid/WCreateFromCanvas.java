/**
 * 
 */
package com.unicore.ui.grid;

import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.apps.form.WCreateFromWindow;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Vlayout;

/**
 * @author Burhani Adam
 *
 */
public class WCreateFromCanvas extends CreateFromCanvas implements EventListener<Event>, ValueChangeListener {

	private WCreateFromWindow window;
	/** Window No               */
	private int p_WindowNo;
	protected Button button = new Button();
	private boolean 	m_actionActive = false;
	/**
	 * @param gridTab
	 */
	public WCreateFromCanvas(GridTab gridTab) {
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

	@Override
	public void valueChange(ValueChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(Event arg0) throws Exception
	{
		if (m_actionActive)
			return;
		m_actionActive = true;
		
		loadOnHand(Env.getContextAsInt(Env.getCtx(), p_WindowNo, "UNS_Cvs_Rpt_ID"));
		
		m_actionActive = false;
	}
	
	protected void loadOnHand (int UNS_Cvs_Rpt_ID)
	{
		loadTableOIS(getOnHandLocator(UNS_Cvs_Rpt_ID));
	}   //  LoadOrder
	
	protected void zkInit() throws Exception
	{
		int UNS_CvsCustomer_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "UNS_CvsCustomer_ID");
		String customer = DB.getSQLValueString(null,
				"SELECT UPPER(Name) FROM UNS_CvsCustomer WHERE UNS_CvsCustomer_ID=?",
				UNS_CvsCustomer_ID);
		
		button.setTooltiptext(customer);
        button.setTooltip(customer);
        button.setName(customer);
        button.setLabel(customer);
		Vlayout vlayout = new Vlayout();
		vlayout.setVflex("1");
		vlayout.setWidth("100%");
    	Panel parameterPanel = window.getParameterPanel();
		parameterPanel.appendChild(vlayout);
		
		Grid parameterStdLayout = GridFactory.newGridLayout();
    	vlayout.appendChild(parameterStdLayout);
		
		Rows rows = (Rows) parameterStdLayout.newRows();
		Row row = rows.newRow();
		
		row.appendChild(button);
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
		button.addActionListener(this);
		
		return true;
	}   //  dynInit
}
