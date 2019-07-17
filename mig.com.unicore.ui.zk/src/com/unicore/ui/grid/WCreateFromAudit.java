/**
 * 
 */
package com.unicore.ui.grid;

import static org.compiere.model.SystemIDs.COLUMN_C_INVOICE_SALESREP_ID;
import static org.compiere.model.SystemIDs.COLUMN_C_INVOICE_C_BPARTNER_ID;
import static org.compiere.model.SystemIDs.COLUMN_C_BPARTNER_LOCATION_UNS_RAYON_ID;
import static org.compiere.model.SystemIDs.COLUMN_C_BPartner_BP_Group_ID;

import java.sql.Timestamp;
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
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.compiere.model.GridTab;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Vlayout;
import org.adempiere.webui.component.*;


/**
 * @author Burhani Adam
 *
 */
public class WCreateFromAudit extends CreateFromAudit implements EventListener<Event>, ValueChangeListener {

	private WCreateFromWindow window;
	/** Window No               */
	private int p_WindowNo;
	private boolean m_actionActive = false;
	private Label bpGroupLabel = new Label();
	private WSearchEditor bpGroupField;
	private Label bPartnerLabel = new Label();
	private WSearchEditor bPartnerField;
	private Label salesLabel = new Label();
	private WSearchEditor salesField;
	private Label rayonLabel = new Label();
	private WSearchEditor rayonField;
	private Checkbox isReturn = new Checkbox();
	private Checkbox isCantSelected = new Checkbox();
	private Button Refresh = new Button();
	private Label fromLabel = new Label();
	private WDateEditor fromField = new WDateEditor();
	private Label toLabel = new Label();
	private WDateEditor toField = new WDateEditor();
	private Label docNoLabel = new Label();
	private Textbox docNoField = new Textbox();
	private Label docPackingListLabel = new Label();
	private Textbox docPackingListField = new Textbox();
	private Checkbox isResultAudit = new Checkbox();
	
	/**
	 * 
	 */
	public WCreateFromAudit(GridTab gridTab) {
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
		
		int UNS_Audit_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "UNS_Audit_ID");
		int C_BPartner_ID = bPartnerField.getValue() == null ? 0 : (Integer) bPartnerField.getValue();
		int C_BPartner_Group_ID = bpGroupField.getValue() == null ? 
				0 : (Integer) bpGroupField.getValue();
		int SalesRep_ID = salesField.getValue() == null ? 0 : (Integer) salesField.getValue();
		int UNS_Rayon_ID = rayonField.getValue() == null ?
				0 : (Integer) rayonField.getValue();
		Timestamp dateFrom = (Timestamp) fromField.getValue();
		Timestamp dateTo = (Timestamp) toField.getValue();
		String DocumentNo = (String) docNoField.getValue();
		String PackingListNo = (String) docPackingListField.getValue();
		
		loadInvoice(UNS_Audit_ID, C_BPartner_ID, C_BPartner_Group_ID, SalesRep_ID, 
				UNS_Rayon_ID, isReturn.isSelected(), isCantSelected.isSelected(), dateFrom, dateTo, DocumentNo, PackingListNo, 
				isResultAudit.isSelected());
		
		m_actionActive = false;
	}
	
	protected void zkInit() throws Exception
	{
		bpGroupLabel = new Label(Msg.translate(Env.getCtx(), "C_BP_Group_ID"));
		bPartnerLabel = new Label(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		fromLabel = new Label(Msg.translate(Env.getCtx(), "DateFrom"));
		toLabel = new Label(Msg.translate(Env.getCtx(), "DateTo"));
		salesLabel = new Label(Msg.translate(Env.getCtx(), "SalesRep_ID"));
		rayonLabel = new Label(Msg.translate(Env.getCtx(), "UNS_Rayon_ID"));
		docNoLabel = new Label(Msg.translate(Env.getCtx(), "DocumentNo"));
		docPackingListLabel = new Label(Msg.translate(Env.getCtx(), "PackingListNo"));
		isResultAudit.setLabel("Result Audit");
		isResultAudit.setChecked(true);
		isReturn.setLabel("Credit Memo");
		isReturn.setChecked(false);
		isCantSelected.setLabel("Can't Selected");
		isCantSelected.setChecked(false);
		Refresh.setLabel("Refresh");
		
		Vlayout vlayout = new Vlayout();
		vlayout.setVflex("1");
		vlayout.setWidth("100%");
    	Panel parameterPanel = window.getParameterPanel();
		parameterPanel.appendChild(vlayout);
		
		Grid parameterStdLayout = GridFactory.newGridLayout();
    	vlayout.appendChild(parameterStdLayout);

		Rows rows = (Rows) parameterStdLayout.newRows();
		
		Row row = rows.newRow();
		rows.appendChild(row);
		row.appendCellChild(fromLabel.rightAlign());
		row.appendCellChild(fromField.getComponent());
		row.appendCellChild(toLabel.rightAlign());
		row.appendCellChild(toField.getComponent());
		
		row = rows.newRow();
		rows.appendChild(row);
		row.appendCellChild(salesLabel.rightAlign());
		row.appendCellChild(salesField.getComponent());
		row.appendCellChild(rayonLabel.rightAlign());
		row.appendCellChild(rayonField.getComponent());
		
		row = rows.newRow();
		rows.appendChild(row);
		row.appendCellChild(bpGroupLabel.rightAlign());
		row.appendCellChild(bpGroupField.getComponent());
		row.appendCellChild(bPartnerLabel.rightAlign());
		row.appendCellChild(bPartnerField.getComponent());
		
		row = rows.newRow();
		rows.appendChild(row);
		row.appendCellChild(docNoLabel.rightAlign());
		row.appendCellChild(docNoField);
		row.appendCellChild(docPackingListLabel.rightAlign());
		row.appendCellChild(docPackingListField);
		
		row.appendCellChild(Refresh);
		
		row = rows.newRow();
		rows.appendChild(row);
		row.appendCellChild(isReturn);
		row.appendCellChild(isCantSelected);
		row.appendCellChild(isResultAudit);
	}
	
	public boolean dynInit() throws Exception
	{
		log.config("");
		super.dynInit();
		window.setTitle(getTitle());
		initRayon();
		initBPGroup();
		initSales();
		initBPartner();
		Refresh.addActionListener(this);
		return true;
	}   //  dynInit
	
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
		
		configureMiniTable(window.getWListbox(), isCantSelected.isChecked());
	}   //  loadInvoice
	
	protected void loadInvoice (int UNS_Audit_ID, int C_BPartner_ID, int C_BPartner_Group_ID, 
			int SalesRep_ID, int UNS_Rayon_ID, boolean isReturn, boolean isCantSelected, 
			Timestamp dateFrom, Timestamp dateTo, String DocumentNo, String PackingListNo, boolean isResultAudit)
	{
		loadTableOIS(getInvoiceData(UNS_Audit_ID, C_BPartner_ID, C_BPartner_Group_ID, 
				SalesRep_ID, UNS_Rayon_ID, isReturn, isCantSelected, dateFrom, dateTo, DocumentNo,
				PackingListNo,  isResultAudit));
	}   //  LoadInvoice
	
	protected void initBPartner()
	{
		int AD_Column_ID = COLUMN_C_INVOICE_C_BPARTNER_ID;      
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		bPartnerField = new WSearchEditor("C_BPartner_ID", false, false, true, lookup);
	}
	
	protected void initSales()
	{
		int AD_Column_ID = COLUMN_C_INVOICE_SALESREP_ID;
		MLookup lookup = MLookupFactory.get(Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		salesField = new WSearchEditor("SalesRep_ID", false, false, true, lookup);
	}
	
	protected void initRayon()
	{
		int AD_Column_ID = COLUMN_C_BPARTNER_LOCATION_UNS_RAYON_ID;
		MLookup lookup = MLookupFactory.get(Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		rayonField = new WSearchEditor("UNS_Rayon_ID", false, false, true, lookup);
	}
	
	protected void initBPGroup()
	{
		int AD_Column_ID = COLUMN_C_BPartner_BP_Group_ID;
		MLookup lookup = MLookupFactory.get(Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		bpGroupField = new WSearchEditor("Group", false, false, true, lookup);
	}
}
