/**
 * 
 */
package com.unicore.ui.grid;

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
import javax.swing.table.DefaultTableModel;
import org.compiere.apps.AEnv;
import org.compiere.grid.VCreateFromDialog;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.swing.CPanel;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * @author Burhani Adam
 *
 */
public class VCreateFromCanvasUI extends CreateFromCanvas implements ActionListener, VetoableChangeListener{

	private VCreateFromDialog dialog;
	/** Window No               */
	private int p_WindowNo;
	private boolean 	m_actionActive = false;
	
	private JButton Customer = new JButton();
	String customer = null;
	
	/**
	 * @param gridTab
	 */
	public VCreateFromCanvasUI(GridTab gridTab) {
		super(gridTab);
		
		if (log.isLoggable(Level.INFO)) log.info(getGridTab().toString());
		
		dialog = new VCreateFromDialog(this, getGridTab().getWindowNo(), true);
//		{		
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = -4368560348932051910L;
//			private MiniTable dataTable = new MiniTable();
//			@Override
//			public void actionPerformed(ActionEvent e)
//			{
//				if (e.getActionCommand().equals(SELECT_DESELECT_ALL))
//				{
//					TableModel model = dataTable.getModel();
//					model.removeTableModelListener(this);
//					
//					Boolean selectAll = selectDeselectAllAction.isPressed() ? Boolean.FALSE : Boolean.TRUE;
//					if(selectAll)
//					{
//						loadOnHand(1);
//					}
//					
//					model.addTableModelListener(this);
//					
//					info();
//				}
//			}
//		};
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit())
				return;
			jbInit();

			setInitOK(true);
			loadOnHand(Env.getContextAsInt(Env.getCtx(), p_WindowNo, "UNS_Cvs_Rpt_ID"));
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
		
		loadOnHand(Env.getContextAsInt(Env.getCtx(), p_WindowNo, "UNS_Cvs_Rpt_ID"));
		
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
    	Customer.setText("                                                                              "
    			+ "                                     "
    			+ customer
    			+ "                                     "
    			+ "                                                                              ");
    	Customer.setToolTipText(Msg.getMsg(Env.getCtx(), "Refresh", false));
    	
    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	CPanel parameterStdPanel = new CPanel(new GridBagLayout());
    	parameterPanel.add(parameterStdPanel, BorderLayout.CENTER);
    	
    	parameterStdPanel.add(Customer, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
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
		Customer.addActionListener(this);
		
		int UNS_CvsCustomer_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "UNS_CvsCustomer_ID");
		customer = DB.getSQLValueString(null,
				"SELECT UPPER(Name) FROM UNS_CvsCustomer WHERE UNS_CvsCustomer_ID=?",
				UNS_CvsCustomer_ID);
		
		return true;
	}   //  dynInit

	/**
	 *  Load Data - Order
	 *  @param C_Order_ID Order
	 *  @param forInvoice true if for invoice vs. delivery qty
	 *  @param M_Locator_ID
	 */
	protected void loadOnHand (int UNS_Cvs_Rpt_ID)
	{
		loadTableOIS(getOnHandLocator(UNS_Cvs_Rpt_ID));
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
}
