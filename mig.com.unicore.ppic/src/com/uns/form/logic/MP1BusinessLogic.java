package com.uns.form.logic;

import java.awt.Container;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JComponent;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProductBOM;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.matica.form.listener.MAT_ButtonEvent;
import org.matica.form.listener.MAT_FieldChangeEvent;
import org.matica.form.listener.MAT_I_Listener;
import org.uns.matica.form.listener.UNS_I_TableLogic;

import com.uns.util.MessageBox;

import com.uns.base.model.Query;
import com.uns.model.MUNSMP1Form;
import com.uns.model.MUNSMP1FormDetail;
import com.uns.model.MUNSResource;
import com.uns.model.factory.UNSPPICModelFactory;

public class MP1BusinessLogic extends UNS_I_TableLogic {

	private static final String TABLE_DETAIL = MUNSMP1FormDetail.Table_Name;
	private Properties m_ctx;
	private String m_trxName;
	
	
	private boolean m_isUpdateNecessary = false;
	private boolean m_isNeedCommitTrx = false;
	private MUNSMP1Form m_Form;
	private MUNSMP1FormDetail[] m_Detail = null;
	private boolean m_isRefreshing = false;
	private boolean m_isInitialized = false;
	private boolean m_isSwitchingRecord = false;
	
	private String m_sdate = "";
	private Integer m_SlotType_ID = 0;
	private Integer m_AD_OrgTo_ID = 0; 
	private Integer m_ppConfig_ID = 0; 
	private BigDecimal m_PreReject = BigDecimal.ZERO;
	private BigDecimal m_totalQty = BigDecimal.ZERO;

	public MP1BusinessLogic() {
		
	}
	
	@Override
	public void setListenerOwner(MAT_I_Listener lst)
	{
		super.setListenerOwner(lst);
		initProductionInputFormData();
	}

	private void initProductionInputFormData() 
	{
		if (m_isInitialized || Env.getAD_Client_ID(m_tblListener.getCtx())==0 )
			return;
		
//		if (Env.getAD_Org_ID(m_tblListener.getCtx()) != UNSApps.getRefAsInt(UNSApps.MPD_ORG))
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
//		m_Form.setM_Product_ID(UNSApps.getRefAsInt(UNSApps.PRD_KBA));
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
				reloadTableDetail(
						(Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID));
				for (MUNSMP1FormDetail detail : m_Detail)
				{
					Vector<Object> row = new Vector<Object>();
					row.add(detail.getNomer());
					row.add(detail.getUNS_Sheller_ID());
					row.add(detail.getUNS_Parrer1_ID());
					row.add(detail.getUNS_Parrer2_ID());
					row.add(detail.getUNS_Parrer3_ID());
					row.add(detail.getStartingStock());
					row.add(detail.getAdditionalQty());
					row.add(detail.getEndingStock());
					row.add(detail.getProductionQty());
					row.add(detail.getKC());
					row.add(detail.getWhiteMeatBW());
					row.add(detail.getCoconutWaterBS());
					row.add(detail.getCoconutShell());
					row.add(detail.getSkinMeat());
					row.add(detail.getRawMeat());
					row.add(detail.getBadMeat());
					row.add(detail.getPreReject());
					
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
		if (tableName.equals(TABLE_DETAIL) 
				&& (column == 1 || column == 2 || column == 3 || column == 4))
		{
			if (newValue == null)
				return;
			
			int intNew = (Integer) newValue;
			
			m_isRefreshing = true;
			
			if (checkDuplicateWorker(intNew))
			{
				m_tblListener.setValueOf(TABLE_DETAIL, null, row, column);
			} 
			
			if (column==1 && intNew!=m_Detail[row].getUNS_Sheller_ID())
			{
				m_Detail[row].setUNS_Sheller_ID(intNew);
			} 
			else if (column==2 && intNew!=m_Detail[row].getUNS_Parrer1_ID())
			{
				m_Detail[row].setUNS_Parrer1_ID(intNew);
			} 
			else if (column==3 && intNew!=m_Detail[row].getUNS_Parrer2_ID())
			{
				m_Detail[row].setUNS_Parrer2_ID(intNew);
			} 
			else if (column==4 && intNew!=m_Detail[row].getUNS_Parrer3_ID())
			{
				m_Detail[row].setUNS_Parrer3_ID(intNew);
			}
			
			m_tblListener.setValueOf(TABLE_DETAIL, intNew, row, column);
			m_isRefreshing = false;
			
		}
		else if (tableName.equals(TABLE_DETAIL) 
				&& (column == 7 || column == 9 || column == 10 
					|| column == 11 || column == 13 || column == 15))
		{
			if (newValue == null)
				return;
			
			BigDecimal BDNew = (BigDecimal) newValue;
			
			m_isRefreshing = true;
			if (BDNew.compareTo(BigDecimal.ZERO)<0)
				throw new AdempiereUserError("Illegal value, can't negatif Quantity.");
			
			
			else if (column==7 && BDNew.compareTo(m_Detail[row].getEndingStock())!=0)
			{
				m_Detail[row].setProductionQty(calculateQty(row, BDNew));
				m_Detail[row].setEndingStock(BDNew);
				m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[row].getProductionQty(), row, 8);
				m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[row].getKC(), row, 9);
				m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[row].getCoconutShell(), row, 12);
				m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[row].getSkinMeat(), row, 13);
				m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[row].getRawMeat(), row, 14);
				m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[row].getBadMeat(), row, 15);
			}  
			else if (column==9 && BDNew.compareTo(m_Detail[row].getKC())!=0)
			{
				m_Detail[row].setKC(BDNew);
			} 
			else if (column==10 && BDNew.compareTo(m_Detail[row].getWhiteMeatBW())!=0)
			{
				m_Detail[row].setWhiteMeatBW(BDNew);
				m_tblListener.setValueOf(TABLE_DETAIL, 
						BDNew.add(m_Detail[row].getSkinMeat()).add(m_Detail[row].getBadMeat()),
						row, 9);
			} 
			else if (column==11 && BDNew.compareTo(m_Detail[row].getCoconutWaterBS())!=0)
			{
				m_Detail[row].setCoconutWaterBS(BDNew);
			} 
			else if (column==13 && BDNew.compareTo(m_Detail[row].getSkinMeat())!=0)
			{
				m_Detail[row].setSkinMeat(BDNew);
				m_tblListener.setValueOf(TABLE_DETAIL, 
						BDNew.add(m_Detail[row].getWhiteMeatBW()).add(m_Detail[row].getBadMeat()),
						row, 9);
			}  
			else if (column==15 && BDNew.compareTo(m_Detail[row].getBadMeat())!=0)
			{
				m_Detail[row].setBadMeat(BDNew);
				m_tblListener.setValueOf(TABLE_DETAIL, 
						BDNew.add(m_Detail[row].getWhiteMeatBW()).add(m_Detail[row].getSkinMeat()),
						row, 9);
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
		retval = retval.subtract(EndStok).setScale(0);
		
		if (retval.signum() < 0)
			throw new AdempiereUserError("Calculation Error","Illegal value, calculation result is negatif.");
				
		//calculation CoconutShell
		MProductBOM[] boms = MProductBOM.getBOMLines(m_ctx, 0//UNSApps.getRefAsInt(UNSApps.PRD_CCS)//, true
				, m_trxName);
		BigDecimal divisor = Env.ONE;
		for (MProductBOM bom : boms){
			if (bom.getM_ProductBOM_ID()==m_Form.getM_Product_ID())
				divisor = bom.getBOMQty();
		}
		BigDecimal ccs = retval.divide(divisor, 2, RoundingMode.HALF_UP);
		m_Detail[row].setCoconutShell(ccs);
		
		//BigDecimal tbkm = ((BigDecimal) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisBKM))
		//		.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
		BigDecimal tbkm = m_Form.getTeoritisBKM().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
		if (tbkm.signum()==0)
			return retval;
		/*
		BigDecimal tsm = ((BigDecimal) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisSM))
				.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
		BigDecimal trm = ((BigDecimal) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisRM))
				.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
		BigDecimal tbm = ((BigDecimal) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisBM))
				.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
		*/
		BigDecimal tsm = m_Form.getTeoritisSM().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
		BigDecimal trm = m_Form.getTeoritisRM().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
		BigDecimal tbm = m_Form.getTeoritisBM().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
		//calculation Skin Meat
		if (tsm.signum()>0)
		{
			BigDecimal skm = retval.multiply(tbkm).multiply(tsm).setScale(2, RoundingMode.HALF_UP);
			m_Detail[row].setSkinMeat(skm);
		}
		
		// calculation Raw Meat
		if (trm.signum()>0 && !m_Form.isKBPecahSegar())
		{
			BigDecimal rwm = retval.multiply(tbkm).multiply(trm).setScale(2, RoundingMode.HALF_UP);
			m_Detail[row].setRawMeat(rwm);
		}
		
		// calculation Bad Meat
		if (tbm.signum()>0 && !m_Form.isKBPecahSegar())
		{
			BigDecimal bdm = retval.multiply(tbkm).multiply(tbm).setScale(2, RoundingMode.HALF_UP);
			m_Detail[row].setBadMeat(bdm);
		}
		
		// calculation KC
		if (m_Form.isKBPecahSegar()) {
			BigDecimal kc = m_Detail[row].getBadMeat().add(m_Detail[row].getSkinMeat())
					.add(m_Detail[row].getWhiteMeatBW());
			m_Detail[row].setKC(kc);
		}

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
				
				if (EmployeeID == detail.getUNS_Parrer1_ID())
					found = true;
				
				if (EmployeeID == detail.getUNS_Parrer2_ID())
					found = true;

				if (EmployeeID == detail.getUNS_Parrer3_ID())
					found = true;
			}
			
			if (found)
				return found;
		}
		
		//check at Database
		Timestamp date = (Timestamp) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_ProductionDate);
		m_sdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		m_SlotType_ID =  (Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID);
		m_AD_OrgTo_ID = (Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTo_ID);
		
		String WhereClause = "ProductionDate = '" + m_sdate + "' AND UNS_SlotType_ID = " + 
				m_SlotType_ID + " AND AD_OrgTo_ID = " + m_AD_OrgTo_ID;
		
		List<MUNSMP1FormDetail> list = new ArrayList<MUNSMP1FormDetail>();
		list = Query.get(m_ctx, UNSPPICModelFactory.getExtensionID(), 
				MUNSMP1Form.Table_Name, WhereClause, m_trxName)
				.list();
		
		for (MUNSMP1FormDetail detail : list)
		{
			if (EmployeeID == detail.getUNS_Sheller_ID())
				found = true;
			
			if (EmployeeID == detail.getUNS_Parrer1_ID())
				found = true;
			
			if (EmployeeID == detail.getUNS_Parrer2_ID())
				found = true;

			if (EmployeeID == detail.getUNS_Parrer3_ID())
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
		
		//refreshAllData();
	}

	private void refreshAllData() {
		if (m_Form.get_ID() == 0)
			throw new AdempiereException("System can't refresh unsaved data," +
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
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_PreReject, m_Form.getPreReject());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_IsKBPecahSegar, m_Form.isKBPecahSegar());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisBKM, m_Form.getTeoritisBKM());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisSM, m_Form.getTeoritisSM());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisRM, m_Form.getTeoritisRM());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisBM, m_Form.getTeoritisBM());
			
			if (m_Form.getSupervisor_ID()!=0)
				m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor_ID, m_Form.getSupervisor_ID());
			
			if (m_Form.getSupervisor1_ID()!=0)
				m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor1_ID, m_Form.getSupervisor1_ID());
			
			if (m_Form.getSupervisor2a_ID()!=0)
				m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor2a_ID, m_Form.getSupervisor2a_ID());
			
			if (m_Form.getSupervisor2b_ID()!=0)
				m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor2b_ID, m_Form.getSupervisor2b_ID());
			
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
		if (WorkCenter_ID>0 && m_isSwitchingRecord)
		{
			if (m_Detail !=null && m_Detail.length > 0 && m_Form.get_ID() == 0)
			{
				m_isRefreshing = true;
				for (int i=0; i < m_Detail.length; i++)
				{
 					m_tblListener.removeRow(TABLE_DETAIL, 0);
				}
				m_isRefreshing = false;
			} 
			
			//Reload from DB 
			//if (m_Form.get_ID()==0 || m_Detail == null){
				m_Detail = m_Form.getLines(true);
			//}
			
			if (m_Detail.length==0)
			{
				m_Detail = MUNSMP1Form.initLines(m_ctx, m_Form, m_trxName);
				m_Form.m_isFormModification=true;
				isInit = true;
			}
		} else if (WorkCenter_ID == 0)
			return;
		
		//m_isRefreshing = true;
		
		for (int i=0; i < m_Detail.length; i++)
		{				
			if (isInit || m_tblListener.getRowCountOf(TABLE_DETAIL)<m_Detail.length)
			{
				Vector<Object> nullVector = new Vector<Object>();
				m_tblListener.addRow(TABLE_DETAIL, nullVector);
			}
						
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getNomer(), i, 0);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getUNS_Sheller_ID(), i, 1);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getUNS_Parrer1_ID(), i, 2);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getUNS_Parrer2_ID(), i, 3);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getUNS_Parrer3_ID(), i, 4);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getStartingStock(), i, 5);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getAdditionalQty(), i, 6);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getEndingStock(), i, 7);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getProductionQty(), i, 8);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getKC(), i, 9);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getWhiteMeatBW(), i, 10);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getCoconutWaterBS(), i, 11);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getCoconutShell(), i, 12);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getSkinMeat(), i, 13);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getRawMeat(), i, 14);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getBadMeat(), i, 15);
			m_tblListener.setValueOf(TABLE_DETAIL, m_Detail[i].getPreReject(), i, 16);
		}
		
		//m_isRefreshing = false;
		m_isSwitchingRecord = false;
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

//		if (Env.getAD_Org_ID(m_tblListener.getCtx()) != UNSApps.getRefAsInt(UNSApps.MPD_ORG))
//			return;
		
		boolean needChangeDetail = false;
		Object newValue = e.getNewValue();
		
		if (e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_IsKBPecahSegar)) 
		{
			if ((Boolean) newValue){
				m_isRefreshing = true;
				
				m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisRM, BigDecimal.ZERO);
				m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisBM, BigDecimal.ZERO);
				
				m_Form.setTeoritisRM(BigDecimal.ZERO);
				m_Form.setTeoritisBM(BigDecimal.ZERO);
				
				m_isRefreshing = false;
				
			}
			
			m_isUpdateNecessary = true;
			needChangeDetail = true;
		} 
		else if (e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID)
				 || e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_ProductionDate)
				 || e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID)) 
		{
			Timestamp newDateValue = m_Form.getProductionDate();
			int newRscID = m_Form.getUNS_Resource_ID();
			Integer PPConfig = m_Form.getUNS_ProductionPayConfig_ID();
			
			if (e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID))
				newRscID = (Integer) newValue;
			else if (e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_ProductionDate))
				newDateValue = (Timestamp) newValue;
			else if (e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID))
				PPConfig = (Integer) newValue;
			Integer OrgValue = (Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTo_ID);
			MUNSMP1Form form = MUNSMP1Form.checkAvaliable(
					m_ctx, newRscID, newDateValue, OrgValue, PPConfig, m_trxName);
			
			if (form != null && form.get_ID() == m_Form.get_ID())
				return;
			
			if (m_isUpdateNecessary)
			{				
				Container theContainer = (Container) m_tblListener.getFormFrame();
				String msg = Msg.getMsg(m_ctx, "SaveBeforeOpenNewMsg");
				String title = Msg.getMsg(m_ctx, "SaveBeforeOpenNewTitle");
				int retAnswer = MessageBox.showMsg(null, m_ctx, 
						   (JComponent) theContainer.getComponent(0),
						   msg, title, 
						   MessageBox.YESNOCANCEL, 
						   MessageBox.ICONWARNING);
				if (retAnswer == MessageBox.RETURN_YES) {
					m_Form.m_isFormModification = true;
					saveAllData();

					String m = Msg.getMsg(m_ctx, "CreateProductionMsg");
					String t = Msg.getMsg(m_ctx, "CreateProductionTitle");
					retAnswer = MessageBox.showMsg(null, m_ctx,
							(JComponent) theContainer.getComponent(0),
							m,
							t, MessageBox.YESNO, MessageBox.ICONINFORMATION);
				
					if (retAnswer == MessageBox.RETURN_YES)
						;//MUNSProduction.CreateUpdateProduction(m_Form);
					else
						refreshAllData();

					Trx trx = Trx.get(m_trxName, false);
					try {
						trx.commit(true);
					}
					catch (SQLException ex)
					{
						throw new AdempiereException("Failed when committing saved data. ErrMsg: " + ex.getMessage());
					}
					
					m_isUpdateNecessary = false;
				}
				else if (retAnswer == MessageBox.RETURN_NO)
					m_isUpdateNecessary = false;
				else {
					m_isRefreshing = true;
					m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID, m_Form.getUNS_Resource_ID());
					m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_ProductionDate, m_Form.getProductionDate());
					m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID, 
												m_Form.getUNS_ProductionPayConfig_ID());
					m_isRefreshing = false;
					return;
				}
			}
			
			m_Form.set_ValueOfColumn(e.getFieldName(), newValue);
			
			//int intValue = (Integer) newValue;
			if (newRscID == 0 || newDateValue == null || PPConfig == 0)
				return;
			
			//Timestamp dateValue = (Timestamp) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_ProductionDate);
			m_isRefreshing = true;
			//if (form!=null){
			if (form == null)
			{
				m_Form = new MUNSMP1Form(m_ctx, 0, m_trxName);
				m_Form.setAD_Org_ID(0);
				m_Form.setAD_OrgTo_ID(Env.getAD_Org_ID(m_ctx));
				m_Form.setDateDoc(new Timestamp(System.currentTimeMillis()));
				m_Form.setProductionDate(newDateValue);
				m_Form.setUNS_Resource_ID(newRscID);
				m_Form.setUNS_ProductionPayConfig_ID(PPConfig);
//				m_Form.setM_Product_ID(UNSApps.getRefAsInt(UNSApps.PRD_KBA));
				/*
				if (m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID) != null)
					m_Form.setUNS_ProductionPayConfig_ID(
							(Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID));
				*/
				Object kbPSObjt = m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_IsKBPecahSegar);
				boolean isKBPPecahSegar =  kbPSObjt instanceof Boolean? (Boolean) kbPSObjt :
						((String) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_IsKBPecahSegar)).equals("Y")?
								true : false;
				m_Form.setIsKBPecahSegar(isKBPPecahSegar);

				MUNSResource resource = new MUNSResource(m_ctx, newRscID, m_trxName);
				m_Form.setUNS_SlotType_ID(resource.getUNS_SlotType_ID());
				
				m_Form.setSupervisor_ID(resource.getSupervisor_ID());
				m_Form.setSupervisor1_ID(resource.getSupervisor1_ID());
				m_Form.setSupervisor2a_ID(resource.getSupervisor2a_ID());
				m_Form.setSupervisor2b_ID(resource.getSupervisor2b_ID());
				

				/*// Previous code before adding code to ask to save before switching. 
				Container theContainer = (Container) m_tblListener.getFormFrame();
				int retAnswer = MessageBox.showMsg(m_ctx, 
												   (JComponent) theContainer.getComponent(0),
												   "You've same data, do you want to change?", 
												   "Confirm on Change", 
												   MessageBox.YESNO, 
												   MessageBox.ICONINFORMATION);
				
				if (retAnswer == MessageBox.RETURN_YES) 
				{
					m_Form = form;
					MUNSResource resource = new MUNSResource(m_ctx, intValue, m_trxName);
					
					m_Form.setProductionDate(dateValue);
					m_Form.setDocumentNo(m_Form.getDocNo(resource));
					setVariable();
					m_Detail = m_Form.getLines(true);
					refreshAllData();
				}
				else if (retAnswer == MessageBox.RETURN_NO)
				{
					m_isRefreshing = true;
					m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID, 0);
					m_isRefreshing = false;
					return;
				}
				*/
			}
			else {
				m_Form = form;
				m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_IsKBPecahSegar, m_Form.isKBPecahSegar());
				m_tblListener.setFieldValue(
						MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID, m_Form.getUNS_ProductionPayConfig_ID());
			}
			//Document Number will change if new Record
			//if (m_Form.getUNS_Resource_ID() != 0 && m_Form.getUNS_Resource_ID() == newRscID)
			//	return;
			
			/*
			m_Form.setProductionDate(newDateValue);
			m_Form.setDocumentNo(m_Form.getDocNo(resource));
			m_Form.setUNS_Resource_ID(newRscID);
			*/
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_DocumentNo, m_Form.getDocumentNo());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID, m_Form.getUNS_SlotType_ID());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_DateDoc, m_Form.getDateDoc());
			
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisBM, m_Form.getTeoritisBM());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisSM, m_Form.getTeoritisSM());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisRM, m_Form.getTeoritisRM());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisBKM, m_Form.getTeoritisBKM());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisBKM, m_Form.getTeoritisBKM());
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_PreReject, m_Form.getPreReject());
			//if (m_Form.getSupervisor_ID()!=0)
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor_ID, m_Form.getSupervisor_ID());
			
			//if (resource.getSupervisor1_ID()!=0)
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor1_ID, m_Form.getSupervisor1_ID());
			
			//if (resource.getSupervisor2a_ID()!=0)
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor2a_ID, m_Form.getSupervisor2a_ID());
			
			//if (resource.getSupervisor2b_ID()!=0)
			m_tblListener.setFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor2b_ID, m_Form.getSupervisor2b_ID());
			
			m_isSwitchingRecord = true;
			reloadTableDetail(newRscID);
			m_isRefreshing = false;
		} 
		else if (e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_PreReject)) 
		{
			BigDecimal bdValue = (BigDecimal) newValue;
			m_Form.setPreReject(bdValue);

			m_PreReject = bdValue;
			needChangeDetail = true;
			m_isUpdateNecessary = true;
		} 
		else if (e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_TeoritisBKM)) 
		{
			BigDecimal bdValue = (BigDecimal) newValue;
			m_Form.setTeoritisBKM(bdValue);
			
			needChangeDetail = true;
			m_isUpdateNecessary = true;
		} 
		else if (e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_TeoritisSM)) 
		{
			BigDecimal bdValue = (BigDecimal) newValue;
			m_Form.setTeoritisSM(bdValue);
			
			needChangeDetail = true;
			m_isUpdateNecessary = true;
		} 
		else if (e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_TeoritisRM)) 
		{
			BigDecimal bdValue = (BigDecimal) newValue;
			m_Form.setTeoritisRM(bdValue);
			
			needChangeDetail = true;
			m_isUpdateNecessary = true;
		} 
		else if (e.getFieldName().equals(MUNSMP1Form.COLUMNNAME_TeoritisBM)) 
		{
			BigDecimal bdValue = (BigDecimal) newValue;
			m_Form.setTeoritisBM(bdValue);
			needChangeDetail = true;
			m_isUpdateNecessary = true;
		}
		

		if (needChangeDetail){
			m_isRefreshing = true;
			calculateDetail();
			reloadTableDetail(m_Form.getUNS_Resource_ID());
			m_isRefreshing = false;
		}
		
		m_Form.m_isFormModification = true;
	}

	private void setVariable() 
	{
		setM_PreReject();
		setM_sdate();
		setM_AD_OrgTo_ID();
		setM_SlotType_ID();
		setM_PPConfig_ID();
		setTotalQty();
	}

	private void calculateDetail() 
	{
		if (m_Detail == null || m_Detail.length == 0)
			return;
		
		for (int row = 0; row<m_Detail.length; row++){
			if (m_totalQty.compareTo(BigDecimal.ZERO)>0)
				m_Detail[row].setPreReject(m_Form.getPreReject().multiply(m_Detail[row].getProductionQty()
						.divide(m_totalQty, 4, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP));
			
			if (m_Detail[row].getProductionQty().signum()==0)
				continue;
			
			calculateQty(row, m_Detail[row].getEndingStock());
		}
	}
	
	private BigDecimal setTotalQty()
	{
		String sql = "SELECT SUM(productionqty) FROM UNS_MP1Form_Detail WHERE UNS_MP1Form_ID IN " +
							"(SELECT UNS_MP1Form_ID FROM UNS_MP1Form WHERE ProductionDate = '"+ getM_sdate() + "' " +
								"AND UNS_SlotType_ID = " + getM_SlotType_ID() + " AND AD_OrgTo_ID = "+ getM_AD_OrgTo_ID()+ ")";
		
		m_totalQty = DB.getSQLValueBD(m_trxName, sql);
		
		return m_totalQty;
	}

	private void calculatePreReject() {
		try {
			updateAllPreReject(getM_PreReject(), getM_sdate(), getM_SlotType_ID(), getM_AD_OrgTo_ID(), getM_PPConfig_ID());
			
			updateAllPreRejectDetail(getM_PreReject(), getM_sdate(), getM_SlotType_ID(), getM_AD_OrgTo_ID(), getM_PPConfig_ID());
		} catch (Exception ex) {
			throw new AdempiereException("Error when update pre reject. "
					+ ex.toString());
		}
	}

	private void updateAllPreRejectDetail(BigDecimal bdValue, String sDate, 
			Integer slotType_ID, Integer org_ID, Integer ppc_ID) {
		
		String sql = "UPDATE UNS_MP1Form_Detail SET PreReject = ROUND(" + bdValue.setScale(2) +
//				"(SELECT AVG(prereject) FROM UNS_MP1Form WHERE ProductionDate = '"+ sDate + "' " +
//				"AND UNS_SlotType_ID = " + slotType_ID + " AND AD_OrgTo_ID = " + org_ID + " " +
//						"AND UNS_ProductionPayConfig_ID = " + ppc_ID + ") " + 
				"* ( CAST(ProductionQty AS numeric) / " +
						"(SELECT SUM(productionqty) FROM UNS_MP1Form_Detail WHERE UNS_MP1Form_ID IN " +
							"(SELECT UNS_MP1Form_ID FROM UNS_MP1Form WHERE ProductionDate = '" + sDate + "' " +
								"AND UNS_SlotType_ID = " + slotType_ID + " AND AD_OrgTo_ID = " + org_ID + " " +
										"AND UNS_ProductionPayConfig_ID = " + ppc_ID + ")" +
						") " +
					") , 2::numeric) " +
				"WHERE UNS_MP1Form_ID IN (SELECT UNS_MP1Form_ID FROM UNS_MP1Form " +
					"WHERE ProductionDate = '" + sDate + "' AND UNS_SlotType_ID = " + slotType_ID + " " +
						"AND AD_OrgTo_ID = " + org_ID + " AND UNS_ProductionPayConfig_ID = " + ppc_ID + ")";
		
		DB.executeUpdate(sql, m_trxName);
	}

	private void updateAllPreReject(BigDecimal bdValue, String sDate,
			Integer slotType_ID, Integer org_ID, Integer ppc_ID) {
		
		String sql = "Update UNS_MP1Form SET PreReject = " + bdValue.setScale(2) + " " +
				"WHERE ProductionDate = '" + sDate + "' " +
				"AND UNS_SlotType_ID = "+slotType_ID + " " +
				"AND AD_OrgTo_ID = "+ org_ID + " " +
				"AND UNS_ProductionPayConfig_ID = " + ppc_ID;
		
		DB.executeUpdate(sql, m_trxName);
	}

	@Override
	public void onConfirmOK() {
//		if (Env.getAD_Org_ID(m_tblListener.getCtx()) != UNSApps.getRefAsInt(UNSApps.MPD_ORG)
//				|| !m_isUpdateNecessary)
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
			String msg = Msg.getMsg(m_ctx, "CloseWithoutCreateMsg");
			String title = Msg.getMsg(m_ctx, "CloseWithoutCreateTitle");
			retAnswer = MessageBox.showMsg(null, m_ctx,
					(JComponent) theContainer.getComponent(0),
					msg, title, MessageBox.YESNO, MessageBox.ICONINFORMATION);
//			create = false;
		}
		else
		{
			String message = Msg.getMsg(m_ctx, "CloseWithoutCreateMsg");
		String ttl = Msg.getMsg(m_ctx, "CloseWithoutCreateTitle");
		retAnswer = MessageBox.showMsg(null, m_ctx,
					(JComponent) theContainer.getComponent(0),
					message, ttl, MessageBox.YESNO, MessageBox.ICONINFORMATION);
		
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
		String msg = Msg.getMsg(m_ctx, "SaveChangesMsg");
		String title = Msg.getMsg(m_ctx, "SaveChangesTitle");
		int retAnswer = MessageBox.showMsg(null, m_ctx, 
										   (JComponent) theContainer.getComponent(0),
										   msg, 
										   title, 
										   MessageBox.YESNOCANCEL, 
										   MessageBox.ICONINFORMATION);
		
		if (retAnswer == MessageBox.RETURN_YES) {
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
		//updateMP1Form();
		
		//Form must be refresh if another user has save same data.
		/*
		MUNSMP1Form form = MUNSMP1Form.checkAvaliable(m_ctx,
						m_Form.getUNS_Resource_ID(), 
						m_Form.getProductionDate(),
						m_Form.getAD_OrgTo_ID(),
						m_Form.getUNS_ProductionPayConfig_ID(),
						m_trxName);
		
		if (form!=null && form.get_ID()!=m_Form.get_ID())
		{
			m_Form.setUNS_MP1Form_ID(form.get_ID());
			return saveCompleted;
		}
		*/
		m_Form.set_TrxName(m_trxName);
		if (!m_Form.save())
			return saveCompleted;

		for (int i=0; i < m_Detail.length; i++) {
			//updateMP1FormDetail(i);
			m_Detail[i].set_TrxName(m_trxName);
			if (m_Detail[i].m_isFormModification){
				m_Detail[i].setUNS_MP1Form_ID(m_Form.get_ID());
				m_Detail[i].saveEx();
			}
		}
		
		setVariable();
		calculatePreReject();
		
		saveCompleted = true;
		m_isNeedCommitTrx  = saveCompleted;
		m_isUpdateNecessary = false;
		return saveCompleted;
	}
/*
	private void updateMP1Form() {
		m_Form.setAD_OrgTo_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTo_ID));
		m_Form.setDocumentNo((String) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_DocumentNo));
		m_Form.setDateDoc((Timestamp) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_DateDoc));
		m_Form.setProductionDate((Timestamp) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_ProductionDate));
		m_Form.setUNS_Resource_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_Resource_ID));
		m_Form.setUNS_SlotType_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID));
		m_Form.setTeoritisBKM((BigDecimal) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisBKM));
		m_Form.setIsKBPecahSegar((Boolean) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_IsKBPecahSegar));
		m_Form.setPreReject((BigDecimal) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_PreReject));
		m_Form.setTeoritisSM((BigDecimal) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisSM));
		m_Form.setTeoritisRM((BigDecimal) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisRM));
		m_Form.setTeoritisBM((BigDecimal) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_TeoritisBM));
		
		if (m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor_ID) != null)
			m_Form.setSupervisor_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor_ID));
		
		if (m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor1_ID) != null)
			m_Form.setSupervisor1_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor1_ID));
		
		if (m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor2a_ID) != null)
			m_Form.setSupervisor2a_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor2a_ID));
		
		if (m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor2b_ID) != null)
			m_Form.setSupervisor2b_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_Supervisor2b_ID));
		
		if (m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID)!=null)
			m_Form.setUNS_ProductionPayConfig_ID((Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID));
	}

	private void updateMP1FormDetail(int row) {
		m_Detail[row].setNomer((Integer) m_tblListener.getValueOf(TABLE_DETAIL, row, 0));
		
		m_Detail[row].setUNS_Sheller_ID((Integer) (
				(m_tblListener.getValueOf(TABLE_DETAIL, row, 1) == null) 
					? 0 : m_tblListener.getValueOf(TABLE_DETAIL, row, 1)));
		
		m_Detail[row].setUNS_Parrer1_ID((Integer) (
				(m_tblListener.getValueOf(TABLE_DETAIL, row, 2) == null) 
					? 0 : m_tblListener.getValueOf(TABLE_DETAIL, row, 2)));
		
		m_Detail[row].setUNS_Parrer2_ID((Integer) (
				(m_tblListener.getValueOf(TABLE_DETAIL, row, 3) == null) 
					? 0 : m_tblListener.getValueOf(TABLE_DETAIL, row, 3)));
		
		m_Detail[row].setUNS_Parrer3_ID((Integer) (
				(m_tblListener.getValueOf(TABLE_DETAIL, row, 4) == null) 
					? 0 : m_tblListener.getValueOf(TABLE_DETAIL, row, 4)));
		
		m_Detail[row].setEndingStock((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 7));
		m_Detail[row].setWhiteMeatBW((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 10));
		m_Detail[row].setCoconutWaterBS((BigDecimal) m_tblListener.getValueOf(TABLE_DETAIL, row, 11));
		
		m_Detail[row].setProductionQty(calculateQty(row, m_Detail[row].getEndingStock()));
	}
*/	
	public String getM_sdate() {
		return m_sdate;
	}

	public void setM_sdate() {
		String m_sdate = "";
		
		if(m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_ProductionDate) != null){
			Timestamp dateValue = (Timestamp) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_ProductionDate);
			m_sdate = new SimpleDateFormat("yyyy-MM-dd").format(dateValue);
		} else {
			Timestamp dateValue = m_Form.getProductionDate();
			m_sdate = new SimpleDateFormat("yyyy-MM-dd").format(dateValue);
		}
		
		this.m_sdate = m_sdate;
	}

	public Integer getM_SlotType_ID() {
		return m_SlotType_ID;
	}
	
	public void setM_SlotType_ID() {
		Integer m_slotType_ID = 0;
		
		if(m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID) != null){
			m_slotType_ID = (Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID);
		} else
			m_slotType_ID = m_Form.getUNS_SlotType_ID();
		
		this.m_SlotType_ID = m_slotType_ID;
	}

	public Integer getM_PPConfig_ID() {
		return m_ppConfig_ID;
	}
	
	public void setM_PPConfig_ID() {
		Integer m_ppConfig_ID = 0;
		
		if(m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_ProductionPayConfig_ID) != null){
			m_ppConfig_ID = (Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_UNS_SlotType_ID);
		} else
			m_ppConfig_ID = m_Form.getUNS_ProductionPayConfig_ID();
		
		this.m_ppConfig_ID = m_ppConfig_ID;
	}

	public Integer getM_AD_OrgTo_ID() {
		return m_AD_OrgTo_ID;
	}

	public void setM_AD_OrgTo_ID() {
		Integer m_AD_OrgTo_ID;
		
		if(m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTo_ID) != null){
			m_AD_OrgTo_ID = (Integer) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_AD_OrgTo_ID);
		} else
			m_AD_OrgTo_ID = Env.getAD_Org_ID(m_ctx);
		
		this.m_AD_OrgTo_ID = m_AD_OrgTo_ID;
	}
	
	public BigDecimal getM_PreReject() {
		return m_PreReject;
	}

	public void setM_PreReject() {
		BigDecimal preReject = BigDecimal.ZERO;
		
		if(m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_PreReject) != null){
			preReject = (BigDecimal) m_tblListener.getFieldValue(MUNSMP1Form.COLUMNNAME_PreReject);
		} else
			preReject = m_Form.getPreReject();
		
		this.m_PreReject = preReject;
	}

}
