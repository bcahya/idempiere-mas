/**
 * 
 */
package com.unicore.ui.grid;

import static org.compiere.model.SystemIDs.COLUMN_C_INVOICE_SALESREP_ID;
import static org.compiere.model.SystemIDs.COLUMN_C_BPartner_BP_Group_ID;
import static org.compiere.model.SystemIDs.COLUMN_C_INVOICE_C_BPARTNER_ID;
import static org.compiere.model.SystemIDs.COLUMN_C_BPARTNER_LOCATION_UNS_RAYON_ID;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.compiere.apps.AEnv;
import org.compiere.grid.VCreateFromDialog;
import org.compiere.grid.ed.VDate;
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
public class VCreateFromAuditUI extends CreateFromAudit implements ActionListener, VetoableChangeListener{

	private VCreateFromDialog dialog;
	/** Window No               */
	private int p_WindowNo;
	private boolean m_actionActive = false;
	
	private JLabel bpGroupLabel = new JLabel();
	private VLookup bpGroupField;
	private JLabel bPartnerLabel = new JLabel();
	private VLookup bPartnerField;
	private JLabel salesLabel = new JLabel();
	private VLookup salesField;
	private JLabel rayonLabel = new JLabel();
	private VLookup rayonField;
	private JCheckBox isReturn = new JCheckBox();
	private JCheckBox isCantSelected = new JCheckBox();
	private JButton Refresh = new JButton();
	private JLabel fromLabel = new JLabel();
	private VDate fromField = new VDate();
	private JLabel toLabel = new JLabel();
	private VDate toField = new VDate();
	private JLabel docNoLabel = new JLabel();
	private JTextField docNoField = new JTextField();
	private JLabel docPackingListNoLabel= new JLabel();
	private JTextField docPackingListNoField = new JTextField();
	private JCheckBox isResultAudit = new JCheckBox();
	
	/**
	 * @param gridTab
	 */
	public VCreateFromAuditUI(GridTab gridTab) {
		super(gridTab);
		
		if (log.isLoggable(Level.INFO)) log.info(getGridTab().toString());
		
		dialog = new VCreateFromDialog(this, getGridTab().getWindowNo(), true);
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit())
				return;
			jbInit();

			setInitOK(true);
//			boolean retur = isReturn.isSelected();
//			boolean cantSelected = isCantSelected.isSelected();
//			loadInvoices(Env.getContextAsInt(Env.getCtx(), p_WindowNo, "UNS_Audit_ID"),
//					bPartnerField.getValue() == null ? 0 : (int) bPartnerField.getValue(),
//							bPartnerField.getValue() == null ? 0 : (int) bPartnerField.getValue(),
//									salesField.getValue() == null ? 0 : (int) salesField.getValue(),
//											rayonField.getValue() == null ? 0 : (int) rayonField.getValue(),
//											retur, cantSelected);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
			setInitOK(false);
		}
		AEnv.positionCenterWindow(AEnv.getWindow(p_WindowNo), dialog);
	}

	@Override
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException {
		if (log.isLoggable(Level.CONFIG)) log.config(evt.getPropertyName() + "=" + evt.getNewValue());
		dialog.tableChanged(null);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (m_actionActive)
			return;
		m_actionActive = true;
		if (log.isLoggable(Level.CONFIG)) log.config("Action=" + e.getActionCommand());
		
		boolean retur = isReturn.isSelected();
		boolean cantSelected = isCantSelected.isSelected();
		boolean resultAudit = isResultAudit.isSelected();
		Timestamp dateAcct = Env.getContextAsDate(Env.getCtx(), p_WindowNo, "DateAcct");
		if(toField.getTimestamp().after(dateAcct))
		{
			String msg = "You can not search invoices with past the account date (" + dateAcct.toString().substring(0, 10) + ")";
			String title = Msg.getMsg(Env.getCtx(), "Notice");
			MessageBox.showMsg(null,
					Env.getCtx()
					, msg
					, title
					, MessageBox.ICONINFORMATION
					, MessageBox.ICONQUESTION);
			m_actionActive = false;
			return;
		}
			
		loadInvoices(Env.getContextAsInt(Env.getCtx(), p_WindowNo, "UNS_Audit_ID"),
			bPartnerField.getValue() == null ? 0 : (int) bPartnerField.getValue(),
				bpGroupField.getValue() == null ? 0 : (int) bPartnerField.getValue(),
					salesField.getValue() == null ? 0 : (int) salesField.getValue(),
						rayonField.getValue() == null ? 0 : (int) rayonField.getValue(),
							retur, cantSelected, fromField.getTimestamp(), toField.getTimestamp(),
								docNoField.getText() == null ? "" : docNoField.getText(),
										docPackingListNoField.getText() == null ? "" : docPackingListNoField.getText(),
												resultAudit);
		m_actionActive = false;	
	}
	
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
    	fromLabel.setText("From");
    	toLabel.setText("To");
		salesLabel.setText("Sales");
		rayonLabel.setText("Rayon");
    	bPartnerLabel.setText("Business Partner");
		bpGroupLabel.setText("Partner Group");
		isReturn.setText("Credit Memo");
		isReturn.setToolTipText("If true then Credit Memo Invoices");
		Refresh.setText("Refresh");
		isCantSelected.setText("Can't Selected");
		docNoLabel.setText("Document No");
		docPackingListNoLabel.setText("PackingList No");
		isResultAudit.setText("Result Audit");
		isResultAudit.setToolTipText("If true then only showing record before audit");
		
    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	CPanel parameterStdPanel = new CPanel(new GridBagLayout());
    	parameterPanel.add(parameterStdPanel, BorderLayout.CENTER);
    
    	parameterStdPanel.add(fromLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(fromField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterStdPanel.add(toLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(toField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(salesLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(salesField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterStdPanel.add(rayonLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(rayonField, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(bpGroupLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(bpGroupField,  new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterStdPanel.add(bPartnerLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(bPartnerField, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(docNoLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(docNoField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterStdPanel.add(docPackingListNoLabel, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(docPackingListNoField, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(Refresh, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
    			,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(isReturn, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterStdPanel.add(isCantSelected, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterStdPanel.add(isResultAudit, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    }//  jbInit
    
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
		initBPartner(); initBPartnerGroup(); initSalesRep(); initRayon(); initDate();
//		bpGroupField.addActionListener(this);
//		bPartnerField.addActionListener(this);
//		accountField.addActionListener(this);
		isReturn.setSelected(false);
		isCantSelected.setSelected(false);
		Refresh.addActionListener(this);
		isResultAudit.setSelected(true);
		
		return true;
	}   //  dynInit

	/**
	 *  Load Data - Invoices
	 */
	protected void loadInvoices (int UNS_VAT_ID, int C_BPartner_ID, int C_BP_Group_ID, int SalesRep_ID,
			int UNS_Rayon_ID, boolean isReturn, boolean isCantSelected, Timestamp dateFrom, Timestamp dateTo,
			String DocumentNo, String PackingListNo, boolean isResultAudit)
	{
		loadTableOIS(getInvoiceData(UNS_VAT_ID, C_BPartner_ID, C_BP_Group_ID, SalesRep_ID, UNS_Rayon_ID,
				isReturn, isCantSelected, dateFrom, dateTo, DocumentNo, PackingListNo, isResultAudit));
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
		
		configureMiniTable(dialog.getMiniTable(), isCantSelected.isSelected());
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
	protected void configureMiniTable(IMiniTable miniTable, boolean isCantSelected) {
		super.configureMiniTable(miniTable, isCantSelected);
	}
	
	protected void initBPartner()
	{
		int AD_Column_ID = COLUMN_C_INVOICE_C_BPARTNER_ID;      
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		bPartnerField = new VLookup ("C_BPartner_ID", false, false, true, lookup);
	}
	
	protected void initBPartnerGroup()
	{
		int AD_Column_ID = COLUMN_C_BPartner_BP_Group_ID;       
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.TableDir);
		bpGroupField = new VLookup ("C_BP_Group_ID", false, false, true, lookup);
	}
	
	protected void initSalesRep()
	{
		int AD_Column_ID = COLUMN_C_INVOICE_SALESREP_ID;      
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.TableDir);
		salesField = new VLookup ("AD_User_ID", false, false, true, lookup);
	}
	
	protected void initRayon()
	{
		int AD_Column_ID = COLUMN_C_BPARTNER_LOCATION_UNS_RAYON_ID;      
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.TableDir);
		rayonField = new VLookup ("UNS_Rayon_ID", false, false, true, lookup);
	}
	
	protected void initDate()
	{
		Timestamp dateAcct = Env.getContextAsDate(Env.getCtx(), p_WindowNo, "DateAcct");
		fromField.setValue(dateAcct);
		toField.setValue(dateAcct);
		fromField.setMandatory(true);
		toField.setMandatory(true);
	}
}