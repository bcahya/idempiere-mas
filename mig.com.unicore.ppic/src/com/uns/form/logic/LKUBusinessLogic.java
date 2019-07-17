package com.uns.form.logic;

import java.awt.Container;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JComponent;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.matica.form.listener.MAT_ButtonEvent;
import org.matica.form.listener.MAT_FieldChangeEvent;
import org.matica.form.listener.MAT_I_Listener;
import org.uns.matica.form.listener.UNS_I_TableLogic;

import com.uns.util.MessageBox;

import com.uns.base.model.Query;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSMP1Form;
import com.uns.model.MUNSMP1FormDetail;
import com.uns.model.MUNSResource;
import com.uns.model.factory.UNSPPICModelFactory;

public class LKUBusinessLogic extends UNS_I_TableLogic {

	private static final String TABLE_DETAIL = "UNS_LKUForm_Detail";
	private Properties m_ctx;
	private String m_trxName;
	
	private boolean m_isUpdateNecessary = false;
	private boolean m_isNeedCommitTrx = false;
	private MUNSMP1Form m_Form;
	private MUNSMP1FormDetail[] m_Detail = null;
	private boolean m_isRefreshing = false;
	private boolean m_isInitialized = false;

	public LKUBusinessLogic() {
		
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
		
//		if (Env.getAD_Org_ID(m_tblListener.getCtx()) != UNSApps.getRefAsInt(UNSApps.LKU_ORG))
//			return;
		
		m_ctx = m_tblListener.getCtx();
		m_trxName = m_tblListener.getTrxName();
		if (m_trxName == null)
			m_trxName = Trx.createTrxName();
		
		//if (m_tblListener.getProcessInfo() == null)
		//	throw new AdempiereException("Cannot start Production Input with null Process Info.");
		
		m_Form = new MUNSMP1Form(m_ctx, 0, m_trxName);
		m_Form.setAD_Org_ID(0);
		m_Form.setAD_OrgTo_ID(Env.getAD_Org_ID(m_ctx));
		m_Form.setDateDoc(new Timestamp(System.currentTimeMillis()));
		m_Form.setProductionDate(new Timestamp(System.currentTimeMillis()));
//		m_Form.setM_Product_ID(UNSApps.getRefAsInt(UNSApps.PRD_KBB));
				
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
					row.add(detail.getUNS_Sheller_ID());
					row.add(detail.getStartingStock());
					row.add(detail.getAdditionalQty());
					row.add(detail.getEndingStock());
					row.add(detail.getProductionQty());
					row.add(detail.getKC());
					row.add(detail.getCoconutShell());
					
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
			
		//change Worker
		if (tableName.equals(TABLE_DETAIL) && column == 1)
		{
			if (newValue == null)
				return;
			
			int intNew = (Integer) newValue;
			
			m_isRefreshing = true;
			
			if (checkDuplicateWorker(intNew))
			{
				m_tblListener.setValueOf(TABLE_DETAIL, 0, row, column);
				return;
			} 
			
			if (column==1 && intNew!=m_Detail[row].getUNS_Sheller_ID())
			{
				m_Detail[row].setUNS_Sheller_ID(intNew);
				m_Detail[row].setEmploymentStatus(
						MUNSEmployee.get(m_ctx, intNew).getPayrollTerm());
			} 
			
			
			m_tblListener.setValueOf(TABLE_DETAIL, intNew, row, column);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[row].getEmploymentStatus(), row, 2);
			m_isRefreshing = false;
			
		}
		else if (tableName.equals(TABLE_DETAIL) 
				&& (column == 5 || column == 7 || column == 8 || column == 9 || column == 10))
		{
			if (newValue == null)
				return;
			
			BigDecimal BDNew = (BigDecimal) newValue;
			
			m_isRefreshing = true;
			if (BDNew.compareTo(BigDecimal.ZERO)<0)
				throw new AdempiereUserError("Illegal value, can't negatif Quantity.");
			
			else if (column==5 && BDNew.compareTo(m_Detail[row].getEndingStock())!=0)
			{
				m_Detail[row].setProductionQty(calculateQty(row, BDNew));
				m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[row].getProductionQty(), row, 6);
			} 
			else if (column==7 && BDNew.compareTo(m_Detail[row].getKC())!=0)
			{
				m_Detail[row].setWhiteMeatBW(BDNew);
			} 
			else if (column==8 && BDNew.compareTo(m_Detail[row].getCoconutShell())!=0)
			{
				m_Detail[row].setCoconutShell(BDNew);
			} 
			else if (column==9 && BDNew.compareTo(m_Detail[row].getKCExtra())!=0)
			{
				m_Detail[row].setKCExtra(BDNew);
			} 
			else if (column==10 && BDNew.compareTo(m_Detail[row].getCCSExtra())!=0)
			{
				m_Detail[row].setCCSExtra(BDNew);
			} 
			
			m_tblListener.setValueOf(TABLE_DETAIL, BDNew, row, column);
			m_isRefreshing = false;
		}
		
		m_Detail[row].m_isFormModification = true;
		m_isUpdateNecessary = true;
	}

	private BigDecimal calculateQty(int row, BigDecimal EndStok) {
		BigDecimal retval = BigDecimal.ZERO;
		
		//calculation Quantity
		retval = m_Detail[row].getStartingStock().add(m_Detail[row].getAdditionalQty());
		retval = retval.subtract(EndStok);
		
		if (retval.compareTo(BigDecimal.ZERO)<0)
			throw new AdempiereUserError("Calculation Error","Illegal value, calculation result is negatif.");
		
		return retval;
	}

	private boolean checkDuplicateWorker(int EmployeeID) {
		boolean found = false;
		
		//check at form
		if (!found)
		{
			for (MUNSMP1FormDetail detail : m_Detail)
			{
				if (EmployeeID == detail.getUNS_Sheller_ID())
					found = true;
			}
			
			if (found)
				return found;
		}
		
		//check at Database
		Timestamp date = (Timestamp) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_ProductionDate);
		String sDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		int SlotTypeID =  (Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID);
		
		String WhereClause = "ProductionDate = '" + sDate + "' AND UNS_SlotType_ID=" + SlotTypeID;
		
		List<MUNSMP1FormDetail> list = new ArrayList<MUNSMP1FormDetail>();
		list = Query.get(m_ctx, UNSPPICModelFactory.getExtensionID(), 
				MUNSMP1Form.Table_Name, WhereClause, m_trxName)
				.list();
		
		for (MUNSMP1FormDetail detail : list)
		{
			if (EmployeeID == detail.getUNS_Sheller_ID())
				found = true;
		}
		
		return found;
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
		
	}

	private void refreshAllData() {
		if (m_Form.get_ID() == 0)
			throw new AdempiereException("System can't refresh Unsaved Data," +
					"please press OK Botton before refresh.");
		
		int resource_ID = 0;
		if (m_Form.get_ID() == 0)
		{
			resource_ID = (Integer) m_tblListener
					.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID);
		}
		else
		{
			resource_ID = m_Form.getUNS_Resource_ID();
			m_isRefreshing = true;

			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_MP1Form_ID, m_Form.get_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTo_ID, m_Form.getAD_OrgTo_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_DocumentNo, m_Form.getDocumentNo());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_DateDoc, m_Form.getDateDoc());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_ProductionDate, m_Form.getProductionDate());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID, m_Form.getUNS_Resource_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID, m_Form.getUNS_SlotType_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID, 
							m_Form.getUNS_ProductionPayConfig_ID());
			
			m_isRefreshing = false;
		}
		
		//reload table detail
		if (resource_ID!=0)
		{
			reloadTableDetail(resource_ID);
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
			
			if (m_Detail[i].getEmploymentStatus()==null && m_Detail[i].getUNS_Sheller_ID()!=0)
				m_Detail[i].setEmploymentStatus(
						MUNSEmployee.get(m_ctx, m_Detail[i].getUNS_Sheller_ID()).getPayrollTerm());
						
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getNomer(), i, 0);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getUNS_Sheller_ID(), i, 1);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getEmploymentStatus(), i, 2);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getStartingStock(), i, 3);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getAdditionalQty(), i, 4);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getEndingStock(), i, 5);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getProductionQty(), i, 6);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getWhiteMeatBW(), i, 7);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getCoconutShell(), i, 8);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getKCExtra(), i, 9);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getCCSExtra(), i, 10);
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
		
//		if (Env.getAD_Org_ID(m_tblListener.getCtx()) != UNSApps.getRefAsInt(UNSApps.LKU_ORG))
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
				int retAnswer = MessageBox.showMsg(null, m_ctx, 
												   (JComponent) theContainer.getComponent(0),
												   msg, 
												   title, 
												   MessageBox.YESNO, 
												   MessageBox.ICONINFORMATION);
				
				if (retAnswer == MessageBox.RETURN_YES) 
				{
					m_Form = form;
					refreshAllData();
				}
				else if (retAnswer == MessageBox.RETURN_NO)
				{
					m_isRefreshing = true;
					m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID, 0);
					m_isRefreshing = false;
					return;
				}
				
			}
			
			//Document Number will change if new Record
			if (m_Form.getUNS_Resource_ID()!=0 && m_Form.getUNS_Resource_ID()==intValue)
				return;
			
			MUNSResource resource = new MUNSResource(m_ctx, intValue, m_trxName);
			
			m_Form.setProductionDate(dateValue);
			m_Form.setDocumentNo(m_Form.getDocNo(resource));
			m_Form.setUNS_Resource_ID(intValue);
			m_Form.setUNS_SlotType_ID(resource.getUNS_SlotType_ID());
			
			m_isRefreshing = true;
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_DocumentNo, m_Form.getDocumentNo());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID, resource.getUNS_SlotType_ID());
			m_isRefreshing = false;
			
			reloadTableDetail(intValue);
		}
		
		m_Form.m_isFormModification = true;
		m_isUpdateNecessary = true;
	}

	@Override
	public void onConfirmOK() {
//		if (Env.getAD_Org_ID(m_tblListener.getCtx()) != UNSApps.getRefAsInt(UNSApps.LKU_ORG))
//		{
//			m_tblListener.disposeFrame();
//			return;
//		}
		
		Container theContainer = (Container) m_tblListener.getFormFrame();
		int retAnswer = 0;
//		boolean create = true;
		
		if (!saveAllData()){

			String m = Msg.getMsg(m_ctx, "DataChangedAnotherUserMsg");
			String t = Msg.getMsg(m_ctx, "DataChangedAnotherUserTitle");
			MessageBox.showMsg(null, m_ctx, 
					   (JComponent) theContainer.getComponent(0),
					   m, t, 
					   MessageBox.OK, 
					   MessageBox.ICONINFORMATION);
			
			refreshAllData();
			return;
		}

		if (m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID)==null){

			String message = Msg.getMsg(m_ctx, "CloseWithoutCreateMsg");
			String ttl = Msg.getMsg(m_ctx, "CloseWithoutCreateTitle");
			retAnswer = MessageBox.showMsg(null, m_ctx,
					(JComponent) theContainer.getComponent(0),
					message, ttl, MessageBox.YESNO, MessageBox.ICONINFORMATION);
//			create = false;
		}
		else
		{
			String msg = Msg.getMsg(m_ctx, "WantToCloseFormMsg");
		    String title = Msg.getMsg(m_ctx, "WantToCloseFormTitle");
			retAnswer = MessageBox.showMsg(null, m_ctx,
					(JComponent) theContainer.getComponent(0),
					msg, title, MessageBox.YESNO, MessageBox.ICONINFORMATION);
		
		if (retAnswer == MessageBox.RETURN_YES)
		{
//			if (create)
//				MUNSProduction.CreateUpdateProduction(m_Form);
			
			m_tblListener.disposeFrame();
		}
		else
			refreshAllData();
	}
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
										   msg , 
										  title , 
										   MessageBox.YESNOCANCEL, 
										   MessageBox.ICONINFORMATION);
		
		if (retAnswer == MessageBox.RETURN_YES) {
			if (!saveAllData()){
				String m = Msg.getMsg(m_ctx, "DataChangedAnotherUserMsg");
				String t = Msg.getMsg(m_ctx, "DataChangedAnotherUserTitle");
				MessageBox.showMsg(null, m_ctx, 
						   (JComponent) theContainer.getComponent(0),
						   m,  t, 
						   MessageBox.OK, 
						   MessageBox.ICONINFORMATION);
				
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
		refreshAllData();
	}
	
	@Override
	public void onRowSelection(String tableName, int row, int[] rows) {
		
	}

	private boolean saveAllData()
	{
		m_Form.m_isFormModification = true;
		boolean saveCompleted = false;
		updateMP1Form();
		
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
			updateMP1FormDetail(i);
			
			if (m_Detail[i].m_isFormModification){
				m_Detail[i].setUNS_MP1Form_ID(m_Form.get_ID());
				m_Detail[i].saveEx();
			}
		}
		
		saveCompleted = true;
		m_isNeedCommitTrx  = saveCompleted;
		return saveCompleted;
	}

	private void updateMP1Form() {
		m_Form.setAD_OrgTo_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTo_ID));
		m_Form.setDocumentNo((String) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_DocumentNo));
		m_Form.setDateDoc((Timestamp) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_DateDoc));
		m_Form.setProductionDate((Timestamp) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_ProductionDate));
		m_Form.setUNS_Resource_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID));
		m_Form.setUNS_SlotType_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID));
		
		if (m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID)!=null)
			m_Form.setUNS_ProductionPayConfig_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID));
		}

	private void updateMP1FormDetail(int row) {
		m_Detail[row].setNomer((Integer) m_tblListener.getValueOf(TABLE_DETAIL, row, 0));
		m_Detail[row].setUNS_Sheller_ID((Integer) m_tblListener.getValueOf(TABLE_DETAIL, row, 1));
		m_Detail[row].setEmploymentStatus((String) m_tblListener.getValueOf(TABLE_DETAIL, row, 2));
		m_Detail[row].setEndingStock((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 5));
		m_Detail[row].setProductionQty(calculateQty(row, m_Detail[row].getEndingStock()));
		m_Detail[row].setKC((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 7));
		m_Detail[row].setCoconutWaterBS((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 8));
		m_Detail[row].setKCExtra((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 9));
		m_Detail[row].setCCSExtra((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 10));
	}

}
