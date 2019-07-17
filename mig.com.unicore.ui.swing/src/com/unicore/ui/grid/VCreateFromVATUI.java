/**
 * 
 */
package com.unicore.ui.grid;

import static org.compiere.model.SystemIDs.COLUMN_C_BANKSTATEMENT_C_BANKACCOUNT_ID;
import static org.compiere.model.SystemIDs.COLUMN_C_BPartner_BP_Group_ID;
import static org.compiere.model.SystemIDs.COLUMN_C_INVOICE_C_BPARTNER_ID;
import static org.compiere.model.SystemIDs.COLUMN_AD_ORG_C_SALESREGION_ID;
import static org.compiere.model.SystemIDs.COLUMN_C_INVOICELINE_M_PRODUCT_ID;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
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

/**
 * @author Burhani Adam
 *
 */
public class VCreateFromVATUI extends CreateFromVAT implements ActionListener, VetoableChangeListener{

	private VCreateFromDialog dialog;
	/** Window No               */
	private int p_WindowNo;
	private boolean m_actionActive = false;
	
	private JLabel bpGroupLabel = new JLabel();
	private VLookup bpGroupField;
	private JLabel bPartnerLabel = new JLabel();
	private VLookup bPartnerField;
	private JLabel accountLabel = new JLabel();
	private VLookup accountField;
	private JLabel regionLabel = new JLabel();
	private VLookup regionField;
	private JLabel productLabel = new JLabel();
	private VLookup productField;
	private JCheckBox isPKP = new JCheckBox();
	private JButton Refresh = new JButton();
	private JCheckBox isCreateReplacement = new JCheckBox();
	private boolean isReplacement = false;
	
	/**
	 * @param gridTab
	 */
	public VCreateFromVATUI(GridTab gridTab) {
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
			boolean pkp = isPKP.isSelected();
			isReplacement = isCreateReplacement.isSelected();
			loadInvoices(Env.getContextAsInt(Env.getCtx(), p_WindowNo, "UNS_VAT_ID"),
					bPartnerField.getValue() == null ? 0 : (int) bPartnerField.getValue(),
							bPartnerField.getValue() == null ? 0 : (int) bPartnerField.getValue(),
									accountField.getValue() == null ? 0 : (int) accountField.getValue(),
											pkp, isReplacement,
												regionField.getValue() == null ? 0 : (int) regionField.getValue(),
														productField.getValue() == null ? 0 : (int) productField.getValue());
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
		
		boolean pkp = isPKP.isSelected();
		isReplacement = isCreateReplacement.isSelected();
		loadInvoices(Env.getContextAsInt(Env.getCtx(), p_WindowNo, "UNS_VAT_ID"),
				bPartnerField.getValue() == null ? 0 : (int) bPartnerField.getValue(),
						bpGroupField.getValue() == null ? 0 : (int) bpGroupField.getValue(),
								accountField.getValue() == null ? 0 : (int) accountField.getValue(),
										pkp, isReplacement,
										regionField.getValue() == null ? 0 : (int) regionField.getValue(),
												productField.getValue() == null ? 0 : (int) productField.getValue());
		
		
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
    	bPartnerLabel.setText("Business Partner");
		bpGroupLabel.setText("Partner Group");
		accountLabel.setText("Account");
		regionLabel.setText("Region");
		productLabel.setText("Product");
		isPKP.setText("PKP");
		isPKP.setToolTipText("If true then Partner PKP only");
		isCreateReplacement.setText("Create Replacement");
		isCreateReplacement.setToolTipText("If true will create replacement");
		Refresh.setText("Refresh");
		
    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	CPanel parameterStdPanel = new CPanel(new GridBagLayout());
    	parameterPanel.add(parameterStdPanel, BorderLayout.CENTER);
    
    	parameterStdPanel.add(bpGroupLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(bpGroupField,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(bPartnerLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(bPartnerField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(accountLabel, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(accountField, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(regionLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(regionField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(Refresh, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(isPKP, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(isCreateReplacement, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(productLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(productField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
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
		initBPartner(); initBPartnerGroup(); initAccount(); initRegion(); initProduct();
		isPKP.setSelected(true);
		isCreateReplacement.setSelected(false);
		Refresh.addActionListener(this);
		
		return true;
	}   //  dynInit

	/**
	 *  Load Data - Order
	 *  @param C_Order_ID Order
	 *  @param forInvoice true if for invoice vs. delivery qty
	 *  @param M_Locator_ID
	 */
	protected void loadInvoices (int UNS_VAT_ID, int C_BPartner_ID, int C_BP_Group_ID,
			int C_BankAccount_ID, boolean isPKP, boolean isReplacement, int Region_ID, int M_Product_ID)
	{
		loadTableOIS(getInvoiceData(UNS_VAT_ID, C_BPartner_ID, C_BP_Group_ID, C_BankAccount_ID,
				isPKP, isReplacement, Region_ID, M_Product_ID));
	}   //  LoadOrder
	
	/**
	 *  Load onHand into Table
	 *  @param data data
	 */
	protected void loadTableOIS (Vector<?> data)
	{
		//  Remove previous listeners
		dialog.getMiniTable().getModel().removeTableModelListener(dialog);
		DefaultTableModel model = new DefaultTableModel(data, getOISColumnNames(isReplacement));
		model.addTableModelListener(dialog);
		dialog.getMiniTable().setModel(model);
		// 
		
		configureMiniTable(dialog.getMiniTable(), isReplacement);
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
	protected void configureMiniTable(IMiniTable miniTable, boolean isReplacement) {
		super.configureMiniTable(miniTable, isReplacement);
	}
	
	protected void initBPartner()
	{
		int AD_Column_ID = COLUMN_C_INVOICE_C_BPARTNER_ID;      
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		bPartnerField = new VLookup ("C_BPartner_ID", true, false, true, lookup);
	}
	
	protected void initBPartnerGroup()
	{
		int AD_Column_ID = COLUMN_C_BPartner_BP_Group_ID;       
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.TableDir);
		bpGroupField = new VLookup ("C_BP_Group_ID", false, false, true, lookup);
	}
	
	protected void initAccount()
	{
		int AD_Column_ID = COLUMN_C_BANKSTATEMENT_C_BANKACCOUNT_ID;      
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.TableDir);
		accountField = new VLookup ("C_BankAccount_ID", false, false, true, lookup);
	}
	
	protected void initRegion()
	{
		int AD_Column_ID = COLUMN_AD_ORG_C_SALESREGION_ID;
		MLookup lookup = MLookupFactory.get(Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.TableDir);
		regionField = new VLookup("AD_Region_ID", false, false, true, lookup);
	}
	
	protected void initProduct()
	{
		int AD_Column_ID = COLUMN_C_INVOICELINE_M_PRODUCT_ID;
		MLookup lookup = MLookupFactory.get(Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		productField = new VLookup("M_Product_ID", false, false, true, lookup);
	}
}