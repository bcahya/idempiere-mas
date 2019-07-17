package com.uns.form.logic;

import java.awt.Container;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JComponent;

import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.matica.form.listener.MAT_ButtonEvent;
import org.matica.form.listener.MAT_FieldChangeEvent;
import org.matica.form.listener.MAT_I_Listener;
import org.uns.matica.form.listener.UNS_I_TableLogic;

import com.uns.util.MessageBox;

import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSMCUInfo;
import com.uns.model.MUNSMedicalRecord;

public class MCUBusinessLogic extends UNS_I_TableLogic {

	private MUNSEmployee m_employee;
	
	private Properties m_ctx;
	private String m_trxName;
	
	private boolean m_isUpdateNecessary = false;
	private boolean m_isNeedCommitTrx = false;
	private boolean m_isRefreshing = false;
	private boolean m_isInitialized = false;

	private MUNSMCUInfo m_mcu;

	public MCUBusinessLogic() {
		//constructor 
	}
	
	@Override
	public void setListenerOwner(MAT_I_Listener lst)
	{
		super.setListenerOwner(lst);
		initMCUFormData();
	}

	private void initMCUFormData() 
	{
		if (m_isInitialized || Env.getAD_Client_ID(m_tblListener.getCtx())==0)
			return;
		
		m_ctx = m_tblListener.getCtx();
		m_trxName = m_tblListener.getTrxName();
		if (m_trxName == null)
			m_trxName = Trx.createTrxName();
		
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
		
		return retValues;
	}

	@Override
	public void tableChange(String tableName, int row, int column,
			String columnName) {
		
		return;
	}

	@Override
	public boolean needCommitTrx() {
		
		return m_isNeedCommitTrx;
	}

	@Override
	public int getTableIDOf(String fieldName) {
		
		return MUNSMedicalRecord.Table_ID;
	}

	@Override
	public void onWindowOpened() {
		if (m_isInitialized || Env.getAD_Client_ID(m_tblListener.getCtx())==0)
			return;
		
		refreshAllData();
	}

	private void refreshAllData() {
		// Set the Production panel data.
		m_isRefreshing = true;
		
		m_tblListener.setFieldValue(MUNSMCUInfo.COLUMNNAME_MCUDate, null);
		m_tblListener.setFieldValue(MUNSMCUInfo.COLUMNNAME_UNS_MCU_Info_ID, null);
				
		refreshEmployee();
				
		m_isRefreshing = false;
	}

	private void refreshEmployee() {
		m_isRefreshing = true;
		
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_Name, null);
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_Gender, null);
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_PlaceOfBirth, null);
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_DateOfBirth, null);
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_AD_Org_ID, Env.getAD_Org_ID(m_ctx));
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_RegNo, null);
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_EmploymentType, null);
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_HighestEdBackround, null);
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_Nationality, null);
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_Religion, null);
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_MaritalStatus, null);
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_FamilyName, null);
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_FatherName, null);
		m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_MotherName, null);
		
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
		if (m_isRefreshing || e.getNewValue() == null)
			return;
		
		Object newValue = e.getNewValue();
		
		if (e.getFieldName().equals(MUNSMCUInfo.COLUMNNAME_UNS_MCU_Info_ID)) {
			
			int UNS_MCU_Info_ID = (Integer) newValue;
			
			m_mcu = new MUNSMCUInfo(m_ctx, UNS_MCU_Info_ID, m_trxName);
			
			m_isRefreshing = true;
			
			m_employee = new MUNSEmployee(m_ctx, 0, m_trxName);
			
			m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_Name, m_mcu.getName());
			m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_Gender, m_mcu.getGender());
			m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_PlaceOfBirth, m_mcu.getPlaceOfBirth());
			m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_DateOfBirth, m_mcu.getDateOfBirth());
			m_tblListener.setFieldValue(MUNSEmployee.COLUMNNAME_AD_Org_ID, m_mcu.getAD_OrgTrx_ID());
			
			m_isRefreshing = false;
			m_isUpdateNecessary = true;
			m_employee.m_isFormModification = true;
		}
	}

	@Override
	public void onConfirmOK() {
		
		storeToEmplomentData();
		
		if (!saveAllData())
			return;
		
		Container theContainer = (Container) m_tblListener.getFormFrame();
		
		int retAnswer = MessageBox.showMsg(null, m_ctx,
				(JComponent) theContainer.getComponent(0),
				"Data Employee has created, Close the MCU form input?", 
				"Save completed",
				MessageBox.YESNO, MessageBox.ICONINFORMATION);
		
		if (retAnswer == MessageBox.RETURN_YES)
			m_tblListener.disposeFrame();
		
		refreshAllData();
	}

	private void storeToEmplomentData() {
		m_employee = new MUNSEmployee(m_ctx, 0, m_trxName);
		
		m_employee.setName((String) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_Name));
		m_employee.setGender((String) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_Gender));
		m_employee.setPlaceOfBirth((String) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_PlaceOfBirth));
		m_employee.setDateOfBirth((Timestamp) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_DateOfBirth));
		m_employee.setAD_Org_ID((Integer) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_AD_Org_ID));
		m_employee.setRegNo((String) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_RegNo));
		m_employee.setEmploymentType((String) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_EmploymentType));
		m_employee.setHighestEdBackround((String) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_HighestEdBackround));
		m_employee.setNationality((String) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_Nationality));
		m_employee.setReligion((String) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_Religion));
		m_employee.setMaritalStatus((String) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_MaritalStatus));
		m_employee.setFamilyName((String) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_FamilyName));
		m_employee.setFatherName((String) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_FatherName));
		m_employee.setMotherName((String) m_tblListener.getFieldValue(MUNSEmployee.COLUMNNAME_MotherName));
	}

	@Override
	public void onConfirmCancel() {
		if(!m_isUpdateNecessary)
		{
			m_tblListener.disposeFrame();
			return;
		}
		Container theContainer = (Container) m_tblListener.getFormFrame();
		int retAnswer = MessageBox.showMsg(null, m_ctx, 
										   (JComponent) theContainer.getComponent(0),
										   "You've made some changes, save it before close the window?", 
										   "Confirm on Close", 
										   MessageBox.YESNOCANCEL, 
										   MessageBox.ICONINFORMATION);
		
		if (retAnswer == MessageBox.RETURN_YES) {
			if (!saveAllData())
				return;
			m_tblListener.disposeFrame();
		}
		else if (retAnswer == MessageBox.RETURN_NO)
			m_tblListener.disposeFrame();
		
		// If canceled .. just keep the form opened.
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
		boolean saveCompleted = false;
		
		if (!m_employee.save())
			return saveCompleted;
		
		m_mcu.setUNS_Employee_ID(m_employee.get_ID());
		if (!m_mcu.save())
			return saveCompleted;
		
		m_isNeedCommitTrx  = true;
		return true;
	}

}
