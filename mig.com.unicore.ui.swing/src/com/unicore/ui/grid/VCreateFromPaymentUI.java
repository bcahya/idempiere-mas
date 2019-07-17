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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.compiere.apps.AEnv;
import org.compiere.grid.VCreateFromDialog;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

/**
 * @author Burhani Adam
 *
 */
public class VCreateFromPaymentUI extends CreateFromPayment
	implements ActionListener, VetoableChangeListener, TableModelListener {
	
	private VCreateFromDialog dialog;
	private int p_WindowNo;
	public DecimalFormat format = DisplayType.getNumberFormat(DisplayType.Amount);
	private JLabel paidAmtLabel = new JLabel();
	private JLabel writeOffAmtLabel = new JLabel();
	private JLabel discAmtLabel = new JLabel();
	private JLabel overUnderAmtLabel = new JLabel();
	private JTextField paidAmtField = new JTextField();
	private JTextField writeOffAmtField = new JTextField();
	private JTextField discAmtField = new JTextField();
	private JTextField overUnderAmtField = new JTextField();
	
	/**
	 * @param gridTab
	 */
	public VCreateFromPaymentUI(GridTab gridTab) {
		super(gridTab);
		
		
		if (log.isLoggable(Level.INFO)) log.info(getGridTab().toString());
		
		dialog = new VCreateFromDialog(this, getGridTab().getWindowNo(), true);
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			dynInit();
			jbInit();
			loadInvoices(Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_Payment_ID"));
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
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException {
		if (log.isLoggable(Level.CONFIG)) log.config(evt.getPropertyName() + "=" + evt.getNewValue());
		dialog.tableChanged(null);
	}
	
	private boolean justUpdate = false;
	@Override
	public void tableChanged(TableModelEvent e)
	{
		//ini buat handle ga keupdate field diatasnya, kalau pake ini ke update. karna sempet ada perubahan label text :D
		if(!(paidAmtLabel.getText().equals(" '-_-' ")))
			paidAmtLabel.setText("^_^");
		
		if(justUpdate || e.getColumn() == 0)
		{
			updateText(dialog.getMiniTable());
			try {
				jbInit();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			justUpdate = false;
			return;
		}
	
		BigDecimal paidAmt = (BigDecimal) dialog.getMiniTable().getValueAt(e.getFirstRow(), 1);
		BigDecimal writeOffAmt = (BigDecimal) dialog.getMiniTable().getValueAt(e.getFirstRow(), 2);
		BigDecimal discAmt = (BigDecimal) dialog.getMiniTable().getValueAt(e.getFirstRow(), 3);
		BigDecimal overUnderAmt = (BigDecimal) dialog.getMiniTable().getValueAt(e.getFirstRow(), 4);
		BigDecimal openAmt = (BigDecimal) dialog.getMiniTable().getValueAt(e.getFirstRow(), 6);
		
		if(e.getColumn() == 1)
		{
			justUpdate = true;
			BigDecimal totalPaidAmt = paidAmt.add(writeOffAmt).add(discAmt);
			overUnderAmt = openAmt.subtract(totalPaidAmt);
			dialog.getMiniTable().setValueAt(overUnderAmt, e.getFirstRow(), 4);
			return;
		}
		
		if(e.getColumn() == 2 || e.getColumn() == 3)
		{
			justUpdate = true;
			BigDecimal totalPaidAmt = paidAmt.add(writeOffAmt).add(discAmt);
			overUnderAmt = openAmt.subtract(totalPaidAmt);
			if(overUnderAmt.signum() >= 0)
				dialog.getMiniTable().setValueAt(overUnderAmt, e.getFirstRow(), 4);
			else
			{
				overUnderAmt = (BigDecimal) dialog.getMiniTable().getValueAt(e.getFirstRow(), 4);
				paidAmt = openAmt.subtract((overUnderAmt.add(discAmt).add(writeOffAmt)));
				dialog.getMiniTable().setValueAt(paidAmt, e.getFirstRow(), 1);
			}
			return;
		}
		
		if(e.getColumn() == 4)
		{
			justUpdate = true;
			BigDecimal totalUnPaidAmt = overUnderAmt.add(writeOffAmt).add(discAmt);
			paidAmt = openAmt.subtract(totalUnPaidAmt);
			dialog.getMiniTable().setValueAt(paidAmt, e.getFirstRow(), 1);
			return;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
	
	public boolean dynInit() throws Exception
	{
		super.dynInit();
		dialog.setTitle(getTitle());
		dialog.getMiniTable().getModel().removeTableModelListener(dialog);
		dialog.getMiniTable().getModel().removeTableModelListener(this);
		DefaultTableModel model = new DefaultTableModel(getInvoiceData(
				Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_Payment_ID")), getOISColumnNames());
		model.addTableModelListener(dialog);
		model.addTableModelListener(this);
		dialog.getMiniTable().setModel(model);
		dialog.getMiniTable().setRowSelectionAllowed(true);
		configureMiniTable(dialog.getMiniTable());
		updateText(dialog.getMiniTable());
		
		return true;
	}
	
	private void jbInit() throws Exception
    {   
		paidAmtLabel.setText("Paid Amount  ");
		writeOffAmtLabel.setText("Write Off Amount  ");
		discAmtLabel.setText("Discount Amount  ");
		overUnderAmtLabel.setText("Over Under Amount ");
		
		paidAmtField.setColumns(10);
		writeOffAmtField.setColumns(10);
		discAmtField.setColumns(10);
		overUnderAmtField.setColumns(10);
		
		paidAmtField.setText(paidAmtString);
		writeOffAmtField.setText(writeOffAmtString);
		discAmtField.setText(discAmtString);
		overUnderAmtField.setText(overUnderAmtString);
		
		paidAmtField.setEditable(false);
		writeOffAmtField.setEditable(false);
		discAmtField.setEditable(false);
		overUnderAmtField.setEditable(false);
		
		CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	CPanel parameterStdPanel = new CPanel(new GridBagLayout());
    	parameterPanel.add(parameterStdPanel, BorderLayout.CENTER);
    	
    	parameterStdPanel.add(paidAmtLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    	parameterStdPanel.add(paidAmtField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    	
    	parameterStdPanel.add(writeOffAmtLabel, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    	parameterStdPanel.add(writeOffAmtField, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    	
    	parameterStdPanel.add(discAmtLabel, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    	parameterStdPanel.add(discAmtField, new GridBagConstraints(7, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    	
    	parameterStdPanel.add(overUnderAmtLabel, new GridBagConstraints(9, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    	parameterStdPanel.add(overUnderAmtField, new GridBagConstraints(10, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    }
	
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
	
	/**
	 *  Load Data - Invoices
	 */
	protected void loadInvoices (int C_Payment_ID)
	{
		loadTableOIS(getInvoiceData(C_Payment_ID));
	}  
	
	/**
	 *  @param data data
	 */
	protected void loadTableOIS (Vector<?> data)
	{		
		//  Remove previous listeners
		dialog.getMiniTable().getModel().removeTableModelListener(dialog);
		dialog.getMiniTable().getModel().removeTableModelListener(this);
		DefaultTableModel model = new DefaultTableModel(data, getOISColumnNames());
		model.addTableModelListener(dialog);
		model.addTableModelListener(this);
		dialog.getMiniTable().setModel(model);
		dialog.getMiniTable().setRowSelectionAllowed(true);
		configureMiniTable(dialog.getMiniTable());
	}   
	
	private String paidAmtString = null;
	private String writeOffAmtString = null;
	private String discAmtString = null;
	private String overUnderAmtString = null;

	private boolean updateText(IMiniTable miniTable)
	{
		BigDecimal paidAmtBD = Env.ZERO;
		BigDecimal writeOffAmtBD = Env.ZERO;
		BigDecimal discAmtBD = Env.ZERO;
		BigDecimal overUnderAmtBD = Env.ZERO;
		BigDecimal amt = Env.ZERO;
		BigDecimal writeOff = Env.ZERO;
		BigDecimal discAmt = Env.ZERO;
		BigDecimal overUnderAmt = Env.ZERO;
		
		for (int i = 0; i < miniTable.getRowCount(); i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
			{
				amt = (BigDecimal) miniTable.getValueAt(i, 1);
				writeOff = (BigDecimal) miniTable.getValueAt(i, 2);
				discAmt = (BigDecimal) miniTable.getValueAt(i, 3);
				overUnderAmt = (BigDecimal) miniTable.getValueAt(i, 4);
				
				paidAmtBD = paidAmtBD.add(amt);
				writeOffAmtBD = writeOffAmtBD.add(writeOff);
				discAmtBD = discAmtBD.add(discAmt);
				overUnderAmtBD = overUnderAmtBD.add(overUnderAmt);
			}
		}
		
		paidAmtString = format.format(paidAmtBD);
		writeOffAmtString = format.format(writeOffAmtBD);
		discAmtString = format.format(discAmtBD);
		overUnderAmtString = format.format(overUnderAmtBD);
		
		return true;
	}
}