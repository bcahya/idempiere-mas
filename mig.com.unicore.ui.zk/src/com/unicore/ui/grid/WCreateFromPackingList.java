/**
 * 
 */
package com.unicore.ui.grid;

import java.util.Vector;
import java.util.logging.Level;

import static org.compiere.model.SystemIDs.COLUMN_UNS_BP_CANVASSER_M_LOCATOR_ID;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.apps.form.WCreateFromWindow;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.model.GridTab;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Vlayout;

/**
 * @author Burhani Adam
 *
 */
public class WCreateFromPackingList extends CreateFromPackingList 
		implements EventListener<Event>, ValueChangeListener
{
	private WCreateFromWindow window;
	/** Window No               */
	private int p_WindowNo;
	protected Label locatorLabel = new Label();
	protected WTableDirEditor locatorField;
	private boolean 	m_actionActive = false;
	protected Button button = new Button();

	/**
	 * @param gridTab
	 */
	public WCreateFromPackingList(GridTab gridTab) {
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
		window.tableChanged(null);
	}
	
	@Override
	public void onEvent(Event arg0) throws Exception {
		if (m_actionActive)
			return;
		m_actionActive = true;
		
		int M_Locator_ID = locatorField.getValue() == null ? 0 : (Integer) locatorField.getValue();
		if(M_Locator_ID > 0)
			loadLines(Env.getContextAsInt(Env.getCtx(), p_WindowNo, "UNS_PackingList_ID"), M_Locator_ID);
		
		m_actionActive = false;
	}
	
	protected void zkInit() throws Exception
	{		
        locatorLabel.setText("Locator");
        button.setTooltiptext("Refresh");
        button.setTooltip("Refresh");
        button.setName("Refresh");
        button.setLabel("Refresh");
        
		Vlayout vlayout = new Vlayout();
		vlayout.setVflex("1");
		vlayout.setWidth("100%");
    	Panel parameterPanel = window.getParameterPanel();
		parameterPanel.appendChild(vlayout);
		
		Grid parameterStdLayout = GridFactory.newGridLayout();
    	vlayout.appendChild(parameterStdLayout);
		
		Rows rows = (Rows) parameterStdLayout.newRows();
		Row row = rows.newRow();
		row.appendCellChild(locatorLabel);
		row.appendCellChild(locatorField.getComponent());
		row.appendCellChild(button);
		
        button.addActionListener((EventListener<?>) this);
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
		initLocator();
		
		return true;
	}   //  dynInit

	protected void loadLines (int UNS_PackingList_ID, int M_Locator_ID)
	{
		loadTableOIS(getLines(UNS_PackingList_ID, M_Locator_ID));
	}
	
	protected void initLocator() throws Exception
	{
		MLookup lookup = MLookupFactory.get(Env.getCtx(), p_WindowNo, 0, COLUMN_UNS_BP_CANVASSER_M_LOCATOR_ID,
				DisplayType.TableDir);
		locatorField = new WTableDirEditor("M_Locator_ID", true, false, true, lookup);
	}
}