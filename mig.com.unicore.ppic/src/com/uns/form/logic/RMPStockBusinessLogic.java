package com.uns.form.logic;

import java.awt.Container;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JComponent;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MMovement;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.matica.form.listener.MAT_ButtonEvent;
import org.matica.form.listener.MAT_FieldChangeEvent;
import org.matica.form.listener.MAT_I_Listener;
import org.uns.matica.form.listener.UNS_I_TableLogic;

import com.uns.util.MessageBox;

import com.uns.model.MUNSMP1Form;
import com.uns.model.MUNSMP1FormDetail;
import com.uns.model.MUNSResource;

public class RMPStockBusinessLogic extends UNS_I_TableLogic {

	private static final String TABLE_DETAIL = "UNS_RMPForm_Detail";			
	private Properties m_ctx;
	private String m_trxName;
	
	private boolean m_isUpdateNecessary = false;
	private boolean m_isNeedCommitTrx = false;
	private MUNSMP1Form m_Form;
	private MUNSMP1FormDetail[] m_Detail = null;
	private boolean m_isRefreshing = false;
	private boolean m_isInitialized = false;

	public RMPStockBusinessLogic() {
		
	}
	
	@Override
	public void setListenerOwner(MAT_I_Listener lst)
	{
		super.setListenerOwner(lst);
		initStokingInputFormData();
	}

	private void initStokingInputFormData() 
	{
		if (m_isInitialized || Env.getAD_Client_ID(m_tblListener.getCtx())==0)
			return;
		
//		if (Env.getAD_Org_ID(m_tblListener.getCtx()) != UNSApps.getRefAsInt(UNSApps.RMP_ORG))
//			return;
		
		m_ctx = m_tblListener.getCtx();
		m_trxName = m_tblListener.getTrxName();
		if (m_trxName == null)
			m_trxName = Trx.createTrxName();
		
		m_Form = new MUNSMP1Form(m_ctx, 0, m_trxName);
		m_Form.setDateDoc(new Timestamp(System.currentTimeMillis()));
		m_Form.setProductionDate(new Timestamp(System.currentTimeMillis()));
		m_Form.setAD_OrgTrx_ID(Env.getAD_Org_ID(m_ctx));
		
		m_isInitialized = true;
	}

	@Override
	public int getRowCount() {
		
		return 0;
	}

	@Override
	public int getColumnCount() {
		
		return 0;
	}

	@Override
	public Vector<Vector<Object>> loadValuesOf(String jTableName) {

		Vector<Vector<Object>> retValues = new Vector<Vector<Object>>();
		
		if (Env.getAD_Client_ID(m_tblListener.getCtx())==0)
			return retValues;
		
		if (jTableName == null)
			return retValues;
		
		if (jTableName.equalsIgnoreCase(TABLE_DETAIL))
		{
			if (null == m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID))
				;
			else 
			{
				reloadTableDetail((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID));
				for (MUNSMP1FormDetail detail : m_Detail)
				{
					Vector<Object> row = new Vector<Object>();
					row.add(detail.getNomer());
					row.add(detail.getM_Locator_ID());
					row.add(detail.getLastStock());
					row.add(detail.getFillIn());
					row.add(detail.getStartingStock());
					row.add(detail.getAdd1());
					row.add(detail.getAdd2());
					row.add(detail.getAdd3());
					row.add(detail.getAdd4());
					row.add(detail.getAdd5());
					row.add(detail.getAdditionalQty());
					
					retValues.add(row);
				}
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
		if (m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID)==null)
			return;
		
		Object newValue = m_tblListener.getValueOf(tableName, row, column);
			
		if (tableName.equals(TABLE_DETAIL) && 
				(column == 2 || column == 3 
				|| column == 5 || column == 6 || column == 7 || column == 8 || column == 9))
		{
			if (newValue == null)
				return;
			
			BigDecimal BDNew = (BigDecimal) newValue;
			
			BigDecimal StartQ = BigDecimal.ZERO;
			BigDecimal AddQ = BigDecimal.ZERO;
			
			if (column==2 && BDNew.compareTo(m_Detail[row].getLastStock())!=0)
			{
				StartQ = BDNew.add(m_Detail[row].getFillIn());
				m_Detail[row].setLastStock(BDNew);
			} 
			else if (column==3 && BDNew.compareTo(m_Detail[row].getFillIn())!=0)
			{
				StartQ = BDNew.add(m_Detail[row].getLastStock());
				m_Detail[row].setFillIn(BDNew);
			} 

			if (column==5 && BDNew.compareTo(m_Detail[row].getAdd1())!=0)
			{
				AddQ = BDNew.add(m_Detail[row].getAdd2())
						.add(m_Detail[row].getAdd3())
						.add(m_Detail[row].getAdd4())
						.add(m_Detail[row].getAdd5());
				m_Detail[row].setAdd1(BDNew);
			} 
			else if (column==6 && BDNew.compareTo(m_Detail[row].getAdd2())!=0)
			{
				AddQ = BDNew.add(m_Detail[row].getAdd1())
						.add(m_Detail[row].getAdd3())
						.add(m_Detail[row].getAdd4())
						.add(m_Detail[row].getAdd5());
				m_Detail[row].setAdd2(BDNew);
			} 
			else if (column==7 && BDNew.compareTo(m_Detail[row].getAdd3())!=0)
			{
				AddQ = BDNew.add(m_Detail[row].getAdd1())
						.add(m_Detail[row].getAdd2())
						.add(m_Detail[row].getAdd4())
						.add(m_Detail[row].getAdd5());
				m_Detail[row].setAdd3(BDNew);
			} 
			else if (column==8 && BDNew.compareTo(m_Detail[row].getAdd4())!=0)
			{
				AddQ = BDNew.add(m_Detail[row].getAdd1())
						.add(m_Detail[row].getAdd2())
						.add(m_Detail[row].getAdd3())
						.add(m_Detail[row].getAdd5());
				m_Detail[row].setAdd4(BDNew);
			} 
			else if (column==9 && BDNew.compareTo(m_Detail[row].getAdd5())!=0)
			{
				AddQ = BDNew.add(m_Detail[row].getAdd1())
						.add(m_Detail[row].getAdd2())
						.add(m_Detail[row].getAdd3())
						.add(m_Detail[row].getAdd4());
				m_Detail[row].setAdd5(BDNew);
			}

			m_isRefreshing = true;
			m_tblListener.setValueOf(TABLE_DETAIL, BDNew, row, column);
			
			m_tblListener.setValueOf(TABLE_DETAIL, StartQ, row, 4);
			m_Detail[row].setStartingStock(StartQ);
			
			m_tblListener.setValueOf(TABLE_DETAIL, AddQ, row, 10);
			m_Detail[row].setAdditionalQty(AddQ);
			m_isRefreshing = false;
		}
		
		m_Detail[row].m_isFormModification = true;
		m_isUpdateNecessary = true;
	}

	@Override
	public boolean needCommitTrx() {
		
		return m_isNeedCommitTrx;
	}

	@Override
	public int getTableIDOf(String fieldName) {
		
		return MUNSMP1Form.Table_ID;
	}

	@Override
	public void onWindowOpened() {
		if (Env.getAD_Client_ID(m_tblListener.getCtx())==0)
			return;
		
		//refreshAllData();
	}

	private void refreshAllData() {
		if (m_Form.get_ID() == 0)
			throw new AdempiereException("System can't refresh Unsaved Data," +
					"please press OK Botton before refresh.");
		
		Object Resource_ID = null;
		if (m_Form.get_ID() == 0)
		{
			Resource_ID = m_tblListener
					.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID);
		}
		else
		{
			Resource_ID = m_Form.getUNS_Resource_ID();
			m_isRefreshing = true;
			
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_MP1Form_ID, m_Form.get_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTrx_ID, m_Form.getAD_OrgTrx_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_DateDoc, m_Form.getDateDoc());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_ProductionDate, m_Form.getProductionDate());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTo_ID, m_Form.getAD_OrgTo_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_M_Locator_ID, m_Form.getM_Locator_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID, m_Form.getUNS_Resource_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID, m_Form.getUNS_SlotType_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_M_Product_ID, m_Form.getM_Product_ID());
			
			m_isRefreshing = false;
		}
		
		//reload table detail
		if (Resource_ID!=null)
		{
			Integer WC_ID = (Integer) Resource_ID;
			reloadTableDetail(WC_ID);
		}
	}

	private void reloadTableDetail(int WorkCenter_ID) {
		boolean isInit = false;
		if (WorkCenter_ID>0)
		{
			if (m_Detail !=null && m_Detail.length>0)
			{
				m_isRefreshing = true;
				for (int i=0; i < m_Detail.length; i++)
				{
 					m_tblListener.removeRow(TABLE_DETAIL, 0);
				}
				m_isRefreshing = false;
			}
			
			m_Detail = m_Form.getLines(true);
			
			if (m_Detail.length==0)
			{
				m_Detail = MUNSMP1Form.initLines(m_ctx, m_Form, m_trxName);
				m_Form.m_isFormModification=true;
				isInit = true;
			}
		}
		
		m_isRefreshing = true;
		
		for (int i=0; i < m_Detail.length; i++)
		{				
			if (isInit || m_tblListener.getRowCountOf(TABLE_DETAIL)<m_Detail.length)
			{
				Vector<Object> nullVector = new Vector<Object>();
				m_tblListener.addRow(TABLE_DETAIL, nullVector);
			}

			//Get last stock from Storage 
			//FIXME for FORM
//			m_Detail[i].setLastStock(MStorageOnHand.getOnHandOfProductLocator(
//						m_Detail[i].getM_Locator_ID(), 
//						UNSApps.getRefAsInt(UNSApps.PRD_KBA), 
//						m_trxName)
//					);
			
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getNomer(), i, 0);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getM_Locator_ID(), i, 1);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getLastStock(), i, 2);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getFillIn(), i, 3);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getStartingStock(), i, 4);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getAdd1(), i, 5);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getAdd2(), i, 6);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getAdd3(), i, 7);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getAdd4(), i, 8);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getAdd5(), i, 9);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getAdditionalQty(), i, 10);
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
	public void onFieldChange(MAT_FieldChangeEvent e) 
	{
		if (m_isRefreshing || e.getNewValue()==null)
			return;
		
//		if (Env.getAD_Org_ID(m_tblListener.getCtx()) != UNSApps.getRefAsInt(UNSApps.RMP_ORG))
//			return;
		
		Object newValue = e.getNewValue();
		
		if (e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID)) {
			int intValue = (Integer) newValue;
			Timestamp dateValue = (Timestamp) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_ProductionDate);
			Integer OrgValue = (Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTo_ID);
			
			MUNSMP1Form form = MUNSMP1Form.checkAvaliable(m_ctx, intValue, dateValue, OrgValue, m_trxName);
			
			if (form!=null){
				Container theContainer = (Container) m_tblListener.getFormFrame();
				String msg = Msg.getMsg(m_ctx, "DuplicateDataMsg");
				String title = Msg.getMsg(m_ctx, "DuplicateDataTitle");
				int retAnswer = MessageBox.showMsg(null, m_ctx, (JComponent) theContainer.getComponent(0),
												   msg, 
												   title, 
												   MessageBox.YESNO, MessageBox.ICONINFORMATION);
				
				if (retAnswer == MessageBox.RETURN_YES) 
				{
					m_Form = form;
					refreshAllData();
				}
				else if (retAnswer == MessageBox.RETURN_NO)
				{
					m_isRefreshing = true;
					m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID, null);
					m_isRefreshing = false;
					return;
				}
				
			}
			
			if (m_Form.getUNS_Resource_ID()!=0 && m_Form.getUNS_Resource_ID()==intValue){
				if (m_Form.getM_Movement_ID()>0)
				{
					MMovement move = new MMovement(m_ctx, m_Form.getM_Movement_ID(), m_trxName);
					if (move.getDocStatus() != MMovement.STATUS_Drafted) {
						m_Form.setIsCreated(true);
						m_Form.saveEx();

						m_isRefreshing = true;
						m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_IsCreated, true);
						m_isRefreshing = false;
					}
				}
				
				return;
			}
			
			MUNSResource resource = new MUNSResource(m_ctx, intValue, m_trxName);
//			int AD_OrgTo_ID = (Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTo_ID);
			
			m_Form.setUNS_Resource_ID(intValue);
			m_Form.setUNS_SlotType_ID(resource.getUNS_SlotType_ID());
			m_Form.setProductionDate(dateValue);
			m_Form.setDocumentNo(m_Form.getDocNo(resource));
			
//			if (AD_OrgTo_ID==UNSApps.getRefAsInt(UNSApps.MPD_ORG))
//			{
//				m_Form.setM_Product_ID(UNSApps.getRefAsInt(UNSApps.PRD_KBA));
//				m_Form.setM_Locator_ID(UNSApps.getRefAsInt(UNSApps.LOC_PTA));
//			}
//			else if (AD_OrgTo_ID==UNSApps.getRefAsInt(UNSApps.LKU_ORG))
//			{
//				m_Form.setM_Product_ID(UNSApps.getRefAsInt(UNSApps.PRD_KBB));
//				m_Form.setM_Locator_ID(UNSApps.getRefAsInt(UNSApps.LOC_PTB));
//			}
			
			m_isRefreshing = true;
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID, resource.getUNS_SlotType_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_M_Product_ID, m_Form.getM_Product_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_M_Locator_ID, m_Form.getM_Locator_ID());
			m_isRefreshing = false;
			
			reloadTableDetail(intValue);
		}
		else if (e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_AD_OrgTo_ID)) {
//			int AD_OrgTo_ID = (Integer) newValue;
			
//			if (AD_OrgTo_ID==UNSApps.getRefAsInt(UNSApps.MPD_ORG))
//			{
//				m_Form.setM_Product_ID(UNSApps.getRefAsInt(UNSApps.PRD_KBA));
//				m_Form.setM_Locator_ID(UNSApps.getRefAsInt(UNSApps.LOC_PTA));
//			}
//			else if (AD_OrgTo_ID==UNSApps.getRefAsInt(UNSApps.LKU_ORG))
//			{
//				m_Form.setM_Product_ID(UNSApps.getRefAsInt(UNSApps.PRD_KBB));
//				m_Form.setM_Locator_ID(UNSApps.getRefAsInt(UNSApps.LOC_PTB));
//			}
//			else
//				return;
			
			m_isRefreshing = true;
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_M_Product_ID, m_Form.getM_Product_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_M_Locator_ID, m_Form.getM_Locator_ID());
			m_isRefreshing = false;
		}
		
		m_Form.m_isFormModification = true;
		m_isUpdateNecessary = true;
	}

	@Override
	public void onConfirmOK() {
//		if (Env.getAD_Org_ID(m_tblListener.getCtx()) != UNSApps.getRefAsInt(UNSApps.RMP_ORG)
//				|| (m_Form!=null && m_Form.isCreated())){
//			m_tblListener.disposeFrame();
//			return;
//		}
	
		Container theContainer = (Container) m_tblListener.getFormFrame();
		
		if (!saveAllData()){
			String m = Msg.getMsg(m_ctx, "DataChangedAnotherUserMsg");
			String t = Msg.getMsg(m_ctx, "DataChangedAnotherUserTitle");
			MessageBox.showMsg(null, m_ctx, (JComponent) theContainer.getComponent(0),
					   m, t,
					   MessageBox.OK, MessageBox.ICONINFORMATION);
			
			refreshAllData();
			return;
		}

		String msg = Msg.getMsg(m_ctx, "CreateMovementFormMsg");
		String title = Msg.getMsg(m_ctx, "CreateMovementFormTitle");
		int retAnswer = MessageBox.showMsg(null, m_ctx, (JComponent) theContainer.getComponent(0),
				msg, title,
				MessageBox.YESNO, MessageBox.ICONINFORMATION);
		
		if (retAnswer == MessageBox.RETURN_YES)
		{
			MUNSMP1Form.CreateUpdateMovement(m_Form);
//			
//			for(MUNSMP1FormDetail detail : m_Detail){
//				if (detail.getUNS_Production_ID()>0)
//					MUNSProduction.CreateUpdateProduction(m_Form);
//			}
			
			m_tblListener.disposeFrame();
		}
		else
			refreshAllData();
	}

	@Override
	public void onConfirmCancel() {
		if(!m_isUpdateNecessary || (m_Form!=null && m_Form.isCreated()))
		{
			m_tblListener.disposeFrame();
			return;
		}
		Container theContainer = (Container) m_tblListener.getFormFrame();
		String msg= Msg.getMsg(m_ctx, "SaveChangesMsg");
		String title = Msg.getMsg(m_ctx, "SaveChangesTitle");
		int retAnswer = MessageBox.showMsg(null, m_ctx, (JComponent) theContainer.getComponent(0),
										   msg, title, 
										   MessageBox.YESNOCANCEL, MessageBox.ICONINFORMATION);
		
		if (retAnswer == MessageBox.RETURN_YES) {
			if (!saveAllData()){

				String m = Msg.getMsg(m_ctx, "DataChangedAnotherUserMsg");
				String t = Msg.getMsg(m_ctx, "DataChangedAnotherUserTitle");
				MessageBox.showMsg(null, m_ctx, (JComponent) theContainer.getComponent(0),
						   m, t, MessageBox.OK, MessageBox.ICONINFORMATION);
				
				refreshAllData();
				return;
			}
			
			m_tblListener.disposeFrame();
		}
		else if (retAnswer == MessageBox.RETURN_NO)
			m_tblListener.disposeFrame();
		
		// If canceled .. just keep the form opened.
		else
			refreshAllData();
	}

	/* (non-Javadoc)
	 * @see org.matica.form.listener.MAT_I_BusinessLogic#onConfirmRefresh()
	 */
	@Override
	public void onConfirmRefresh() 
	{
		if (m_Form!=null && m_Form.isCreated())
			return;
		
		refreshAllData();
	}
	
	@Override
	public void onRowSelection(String tableName, int row, int[] rows) {
		
	}

	private boolean saveAllData()
	{
		m_Form.m_isFormModification = true;
		boolean saveCompleted = false;
		updateHeader();
		
		// Form must be refresh if another user has save same data.
		MUNSMP1Form form = MUNSMP1Form.checkAvaliable(m_ctx,
						m_Form.getUNS_Resource_ID(), 
						m_Form.getProductionDate(),
						m_Form.getAD_OrgTo_ID(),
						m_trxName);
				
		if (form != null && form.get_ID() != m_Form.get_ID()) {
			m_Form.setUNS_MP1Form_ID(form.get_ID());
			return saveCompleted;
		}
		
		if (!m_Form.save())
			return saveCompleted;

		for (int i=0; i < m_Detail.length; i++) {
			updateStockDetail(i);
			if (m_Detail[i].m_isFormModification){
				m_Detail[i].setUNS_MP1Form_ID(m_Form.get_ID());
				m_Detail[i].saveEx();
			}
		}
		
		saveCompleted = true;
		m_isNeedCommitTrx  = saveCompleted;
		return saveCompleted;
	}
	
	private void updateHeader() {
		m_Form.setAD_OrgTrx_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTrx_ID));
		m_Form.setDateDoc((Timestamp) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_DateDoc));
		m_Form.setProductionDate((Timestamp) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_ProductionDate));
		m_Form.setAD_OrgTo_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTo_ID));
		m_Form.setM_Locator_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_M_Locator_ID));
		m_Form.setUNS_Resource_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID));
		m_Form.setUNS_SlotType_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID));
	}

	private void updateStockDetail(int row) {
		m_Detail[row].setNomer((Integer) m_tblListener.getValueOf(TABLE_DETAIL, row, 0));
		m_Detail[row].setM_Locator_ID((Integer) m_tblListener.getValueOf(TABLE_DETAIL, row, 1));
		m_Detail[row].setLastStock((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 2));
		m_Detail[row].setFillIn((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 3));
		m_Detail[row].setStartingStock((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 4));
		m_Detail[row].setAdd1((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 5));
		m_Detail[row].setAdd2((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 6));
		m_Detail[row].setAdd3((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 7));
		m_Detail[row].setAdd4((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 8));
		m_Detail[row].setAdd5((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 9));
		m_Detail[row].setAdditionalQty((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 10));
		
	}
}