package com.uns.form.logic;

import java.awt.Container;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JComponent;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.matica.form.listener.MAT_ButtonEvent;
import org.matica.form.listener.MAT_FieldChangeEvent;
import org.matica.form.listener.MAT_I_Listener;
import org.uns.matica.form.listener.UNS_I_TableLogic;

import com.uns.util.MessageBox;

import com.uns.base.model.Query;
import com.uns.model.MUNSProduction;
import com.uns.model.MUNSProductionDetail;
import com.uns.model.MUNSProductionOutPlan;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author ITD-ANDY
 *
 */
public class ProductionBusinessLogic extends UNS_I_TableLogic {
	
	private static final String TABLE_DETAIL = MUNSProductionDetail.Table_Name;
	private static final String TABLE_OUTPLAN = MUNSProductionOutPlan.Table_Name;
	private Properties m_ctx;
	private String m_trxName;
	
	
	private boolean m_isUpdateNecessary = false;
	private boolean m_isNeedCommitTrx = false;
	private MUNSProduction m_Production;
	private MUNSProductionOutPlan[] m_ProductionOutPlans = null;
	private MUNSProductionDetail[] m_ProductionDetails = null;
	private boolean m_isRefreshing = false;
	private boolean m_isInitialized = false;

	public ProductionBusinessLogic() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setListenerOwner(MAT_I_Listener lst)
	{
		super.setListenerOwner(lst);
		initProductionInputFormData();
	}

	private void initProductionInputFormData() 
	{
		if (m_isInitialized || Env.getAD_Client_ID(m_tblListener.getCtx())==0)
			return;
		
		m_ctx = m_tblListener.getCtx();
		m_trxName = m_tblListener.getTrxName();
		if (m_trxName == null)
			m_trxName = Trx.createTrxName();
		
		if (m_tblListener.getProcessInfo() == null)
			throw new AdempiereException("Cannot start Production Input with null Process Info.");
		
		if (m_tblListener.getProcessInfo().getRecord_ID() > 0) {
			m_Production = new MUNSProduction(m_ctx, m_tblListener.getProcessInfo().getRecord_ID(), m_trxName);
		}
		else {
			ProcessInfoParameter[] paramList = m_tblListener.getProcessInfo().getParameter();
			for (ProcessInfoParameter param : paramList)
			{
				String name = param.getParameterName();
				if (name.equals(MUNSProduction.Table_Name)) {
					int UNS_Production_ID = param.getParameterAsInt();
					m_Production = new MUNSProduction(m_ctx, UNS_Production_ID, m_trxName);
				}
			}
		}
		
		if (m_Production == null )
			throw new AdempiereException("Cannot start Production Input Form " +
					"without an initialized Production Document.");
		
		m_ProductionOutPlans = m_Production.getOutputs();
		
		// try to get the RMP Line, and create it (not saved) if no line created yet.
		// m_ProductionDetails = m_Production.getLines();
		List<MUNSProductionDetail> list = null;
		list = Query.get(m_tblListener.getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSProductionDetail.Table_Name, 
						 "UNS_Production_ID=" + m_Production.get_ID(), m_tblListener.getTrxName())
						 .setOrderBy(MUNSProductionDetail.COLUMNNAME_Line)
					.list();
		MUNSProductionDetail[] retValue = new MUNSProductionDetail[list.size()];
		retValue = list.toArray(retValue);
		m_ProductionDetails = retValue;
		
		if (m_ProductionDetails.length==0)
			throw new AdempiereException("Cannot start Production Input Form " +
						"without an initialized detail Document.");
		
		m_isInitialized = true;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector<Vector<Object>> loadValuesOf(String jTableName) {

		Vector<Vector<Object>> retValues = new Vector<Vector<Object>>();
		
		if (Env.getAD_Client_ID(m_tblListener.getCtx())==0)
			return retValues;
		
		if (jTableName == null)
			return retValues;
		
		if (jTableName.equalsIgnoreCase(TABLE_OUTPLAN))
		{
			for (MUNSProductionOutPlan outPlan : m_ProductionOutPlans)
			{
				Vector<Object> row = new Vector<Object>();
				row.add(outPlan.getM_Product_ID());
				row.add(outPlan.isActive());
				row.add(outPlan.isPrimary());
				row.add(outPlan.getQtyPlan());
				row.add(outPlan.getC_UOM_ID());
				row.add(outPlan.getM_Product_ID());
				retValues.add(row);
			}
		}
		else if (jTableName.equalsIgnoreCase(TABLE_DETAIL))
		{
			for (MUNSProductionDetail detail : m_ProductionDetails)
			{
				Vector<Object> row = new Vector<Object>();
				row.add(detail.getLine());
				row.add(detail.getM_Product_ID());
				row.add(detail.isActive());
				row.add(detail.isEndProduct());
				row.add(detail.getEndingStock());
				row.add(detail.getMovementQty());
				row.add(detail.getM_Product().getC_UOM_ID());
				row.add(detail.getM_Locator_ID());
				row.add(detail.getQtyAvailable());
				retValues.add(row);
			}
		}
		return retValues;
	}

	@Override
	public void tableChange(String tableName, int row, int column,
			String columnName) {
		// only allow change on Detail.
		if (m_isRefreshing)
			return;
		if (tableName.equals(TABLE_DETAIL) == false)
			return;
		if (!m_isInitialized)
			return;
				
		Object newValue = m_tblListener.getValueOf(tableName, row, column);
			
		//change Active
		if (tableName.equals(TABLE_DETAIL) && column == 2) {
			if (newValue == null)
				newValue = true;
			boolean booleanNew = (Boolean) newValue;
			if (booleanNew != m_ProductionDetails[row].isActive())
				m_ProductionDetails[row].setIsActive(booleanNew);

		}
		
		//change Locator
		if (tableName.equals(TABLE_DETAIL) && column==7){
			if (newValue == null)
				newValue = 0;
			int intNew = (Integer) newValue;
			if (intNew != m_ProductionDetails[row].getM_Locator_ID())
				changeLocator(m_ProductionDetails[row], intNew);
			
		}
		//change Ending Stock / Ending Stock Base (Material Only) 
		else if (tableName.equals(TABLE_DETAIL) && (column==4)){
			if (newValue == null)
				newValue = Env.ZERO;
			BigDecimal bdNew = (BigDecimal) newValue;
			
			if (bdNew.compareTo(m_ProductionDetails[row].getEndingStock()) != 0)
				changeEndingStock(m_ProductionDetails[row], bdNew);
			
		}
		//Change Movement Quantity
		else if (tableName.equals(TABLE_DETAIL) && (column==5)){
			if (newValue == null)
				newValue = Env.ZERO;
			BigDecimal bdNew = (BigDecimal) newValue;
			
			if (bdNew.compareTo(m_ProductionDetails[row].getMovementQty()) != 0)
				changeQuantity(m_ProductionDetails[row], bdNew);
			
		}
		
		if ((column==2 || column == 4 || column == 5 || column == 7)){
			m_ProductionDetails[row].m_isFormModification = true;
			refreshAllData();
			m_isUpdateNecessary = true;
		}
	}

	private void changeLocator(MUNSProductionDetail detail, int intNew) {
		detail.setM_Locator_ID(intNew);
	}
	
	private void changeEndingStock(MUNSProductionDetail detail, BigDecimal bdNew) {
		if (bdNew.compareTo(detail.getQtyAvailable())>0)
			return;
		
		if (!m_Production.isEndingStockBase() || detail.isEndProduct()){
			return;
		}
		
		detail.setEndingStock(bdNew);
		detail.setQtyUsed(detail.getQtyAvailable().subtract(detail.getEndingStock()));
		detail.setMovementQty(detail.getQtyUsed().negate());
	}

	private void changeQuantity(MUNSProductionDetail detail, BigDecimal bdNew) {
		
		if (m_Production.isEndingStockBase() && !detail.isEndProduct()){
			return;
		} 		
		
		if(detail.isEndProduct()){
			detail.setPlannedQty(bdNew);
			detail.setMovementQty(bdNew);
		} else {
			detail.setQtyUsed(bdNew);
			detail.setMovementQty(bdNew.negate());
		}
	}


	@Override
	public boolean needCommitTrx() {
		// TODO Auto-generated method stub
		return m_isNeedCommitTrx;
	}

	@Override
	public int getTableIDOf(String fieldName) {
		// TODO Auto-generated method stub
		return MUNSProduction.Table_ID;
	}

	@Override
	public void onWindowOpened() {
		if (Env.getAD_Client_ID(m_tblListener.getCtx())==0)
			return;
		
		refreshAllData();
	}

	private void refreshAllData() {
		// Set the Production panel data.
		m_isRefreshing = true;
		
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_DocumentNo, m_Production.getDocumentNo());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_C_DocType_ID, m_Production.getC_DocType_ID());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_PSType, m_Production.getPSType());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_Name, m_Production.getName());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_UNS_ProductionSchedule_ID, m_Production.getUNS_ProductionSchedule_ID());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_ProductionDate, m_Production.getProductionDate());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_MovementDate, m_Production.getMovementDate());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_UNS_Resource_ID, m_Production.getUNS_Resource_ID());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_OutputType, m_Production.getOutputType());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_IsWorkerBase, m_Production.isWorkerBase());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_IsPersonalResult, m_Production.isWorkerBase());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_WorkerResultType, m_Production.getWorkerResultType());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_UNS_ProductionPayConfig_ID, m_Production.getUNS_ProductionPayConfig_ID());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_IsOverTime, m_Production.isOverTime());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_HoursOverTime, m_Production.getHoursOverTime());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_M_Product_ID, m_Production.getM_Product_ID());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_M_Locator_ID, m_Production.getM_Locator_ID());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_ProductionQty, m_Production.getProductionQty());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_IsEndingStockBase, m_Production.isEndingStockBase());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_HasStickerInfo, m_Production.isHasStickerInfo());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_ProductSticker_ID, m_Production.getProductSticker_ID());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_StickerInfo, m_Production.getStickerInfo());
			
		if (m_Production.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Multi))
		{
			m_tblListener.setPanelVisible(TABLE_OUTPLAN, true);
			refreshTableOutPlan();
			//overwrite output
			MUNSProductionOutPlan primary = m_Production.getPrimaryOutput();
			m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_M_Product_ID, primary.getM_Product_ID());
			m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_M_Locator_ID, primary.getM_Locator_ID());
			m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_ProductionQty, primary.getQtyPlan());
		}
		else
			m_tblListener.setPanelVisible(TABLE_OUTPLAN, false);
				
		refreshTableProduction();
				
		m_isRefreshing = false;
	}

	private void refreshTableProduction() {
		m_isRefreshing = true;
		
		for (int i=0; i < m_ProductionDetails.length; i++)
		{
			m_tblListener.setValueOf(TABLE_DETAIL, m_ProductionDetails[i].getLine(), i, 0); //Line Number
			m_tblListener.setValueOf(TABLE_DETAIL, m_ProductionDetails[i].getM_Product_ID(), i, 1); //Product 
			m_tblListener.setValueOf(TABLE_DETAIL, m_ProductionDetails[i].isActive(), i, 2); //Active
			m_tblListener.setValueOf(TABLE_DETAIL, m_ProductionDetails[i].isEndProduct(), i, 3); //End Product
			m_tblListener.setValueOf(TABLE_DETAIL, m_ProductionDetails[i].getEndingStock(), i, 4); //End Quantity
			m_tblListener.setValueOf(TABLE_DETAIL, m_ProductionDetails[i].getMovementQty(), i, 5); //Move Quantity
			m_tblListener.setValueOf(TABLE_DETAIL, m_ProductionDetails[i].getM_Product().getC_UOM_ID(), i, 6); //UOM
			m_tblListener.setValueOf(TABLE_DETAIL, m_ProductionDetails[i].getM_Locator_ID(), i, 7); //Locator
			m_tblListener.setValueOf(TABLE_DETAIL, m_ProductionDetails[i].getQtyAvailable(), i, 8); //Locator
		}
		m_isRefreshing = false;
		
	}

	private void refreshTableOutPlan() {
		m_isRefreshing = true;
		
		m_ProductionOutPlans = m_Production.getOutputs();
		
		for (int i=0; i < m_ProductionOutPlans.length; i++)
		{
			m_tblListener.setValueOf(TABLE_OUTPLAN, m_ProductionOutPlans[i].getM_Product_ID(), i, 0); //Product 
			m_tblListener.setValueOf(TABLE_OUTPLAN, m_ProductionOutPlans[i].isActive(), i, 1); //Active
			m_tblListener.setValueOf(TABLE_OUTPLAN, m_ProductionOutPlans[i].isPrimary(), i, 2); //Primary
			m_tblListener.setValueOf(TABLE_OUTPLAN, m_ProductionOutPlans[i].getQtyPlan(), i, 3); //Quantity
			m_tblListener.setValueOf(TABLE_OUTPLAN, m_ProductionOutPlans[i].getC_UOM_ID(), i, 4); //UOM
			m_tblListener.setValueOf(TABLE_OUTPLAN, m_ProductionOutPlans[i].getM_Locator_ID(), i, 5); //Locator
		}
		m_isRefreshing = false;
	}

	@Override
	public void onWindowClosed() {
	}

	@Override
	public void onButton(MAT_ButtonEvent e) {
	}

	@Override
	public void onFieldChange(MAT_FieldChangeEvent e) {
		if (m_isRefreshing)
			return;
		
		//Object newValue = e.getNewValue();
		
		if (e.getFieldName().equals("")) {
			
			m_isUpdateNecessary = true;
		}
	}

	@Override
	public void onConfirmOK() {
		if (!saveAllData())
			return;

		Container theContainer = (Container) m_tblListener.getFormFrame();
		String msg = Msg.getMsg(m_ctx, "CloseProductionFormMsg");
		String title = Msg.getMsg(m_ctx, "CloseProductionFormTitle");
		int retAnswer = MessageBox.showMsg(null, m_ctx,
				(JComponent) theContainer.getComponent(0),
				msg, title,
				MessageBox.YESNO, MessageBox.ICONINFORMATION);
		
		if (retAnswer == MessageBox.RETURN_YES)
			m_tblListener.disposeFrame();
		
		m_isInitialized = false;
		initProductionInputFormData();
		refreshAllData();
	}

	@Override
	public void onConfirmCancel() {
		if(!m_isUpdateNecessary)
		{
			m_tblListener.disposeFrame();
			return;
		}
		Container theContainer = (Container) m_tblListener.getFormFrame();
		String msg= Msg.getMsg(m_ctx, "SaveChangesMsg");
		String title = Msg.getMsg(m_ctx, "SaveChangesTitle");
		int retAnswer = MessageBox.showMsg(null, m_ctx, 
										   (JComponent) theContainer.getComponent(0),
										   msg, title, 
										   MessageBox.YESNOCANCEL, 
										   MessageBox.ICONINFORMATION);
		
		if (retAnswer == MessageBox.RETURN_YES) {
			if (!saveAllData())
				return;
			m_tblListener.disposeFrame();
		}
		else if (retAnswer == MessageBox.RETURN_NO)
			m_tblListener.disposeFrame();
		
		m_isInitialized = false;
		initProductionInputFormData();
		refreshAllData();
		// If canceled .. just keep the form opened.
	}

	/* (non-Javadoc)
	 * @see org.matica.form.listener.MAT_I_BusinessLogic#onConfirmRefresh()
	 */
	@Override
	public void onConfirmRefresh() 
	{
		;
	}
	
	@Override
	public void onRowSelection(String tableName, int row, int[] rows) {
		// TODO Auto-generated method stub
	}

	private boolean saveAllData()
	{
		boolean saveCompleted = false;
		
		m_Production.m_isFormModification = true;
		if (!m_Production.save())
			return saveCompleted;
		
		for (int i=0; i < m_ProductionDetails.length; i++) {
			MUNSProductionDetail detail = m_ProductionDetails[i];
			
			if (detail.m_isFormModification){
				detail.saveEx();
			}
		}
		
		m_isNeedCommitTrx  = true;
		return true;
	}
}
