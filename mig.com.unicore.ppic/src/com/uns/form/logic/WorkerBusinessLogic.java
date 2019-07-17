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
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSProduction;
import com.uns.model.MUNSProductionOutPlan;
import com.uns.model.MUNSProductionWorker;
import com.uns.model.MUNSProductionWorkerResult;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author ITD-ANDY
 *
 */
public class WorkerBusinessLogic extends UNS_I_TableLogic {
	
	private static final String TABLE_WORKERRESULT = MUNSProductionWorkerResult.Table_Name;
	private static final String TABLE_WORKER = MUNSProductionWorker.Table_Name;
	private Properties m_ctx;
	private String m_trxName;
	
	
	private boolean m_isUpdateNecessary = false;
	private boolean m_isNeedCommitTrx = false;
	private MUNSProduction m_Production;
	private MUNSProductionWorker[] m_ProductionWorkers = null;
	private MUNSProductionWorkerResult[] m_ProductionWRs = null;
	private boolean m_isRefreshing = false;
	private boolean m_isInitialized = false;
	private boolean m_itsNewRow = false;

	public WorkerBusinessLogic() {
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
			throw new AdempiereException("Cannot start RMP Input with null Process Info.");
		
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
		

		m_ProductionWorkers = m_Production.getWorkers();
		
		if (m_ProductionWorkers != null && m_ProductionWorkers.length > 0 
				&& m_ProductionWorkers[0].get_ID() != 0){
			if (!m_Production.getWorkerResultType().equals(MUNSProduction.WORKERRESULTTYPE_Daily)) {
				// try to get the RMP Line, and create it (not saved) if no line created yet.
				// m_ProductionDetails = m_Production.getLines();

				List<MUNSProductionWorkerResult> list = Query.get(m_tblListener.getCtx(),
								UNSPPICModelFactory.getExtensionID(),
								MUNSProductionWorkerResult.Table_Name,
								"UNS_Production_Worker_ID="	+ m_ProductionWorkers[0].get_ID(),
								m_tblListener.getTrxName())
						.setOrderBy(MUNSProductionWorkerResult.COLUMNNAME_UNS_Production_WorkerResult_ID)
						.list();
				MUNSProductionWorkerResult[] retValue = new MUNSProductionWorkerResult[list.size()];
				retValue = list.toArray(retValue);
				m_ProductionWRs = retValue;
			}
		}
		
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
		
		if (jTableName.equalsIgnoreCase(TABLE_WORKER))
		{
			for (MUNSProductionWorker worker : m_ProductionWorkers)
			{
				Vector<Object> row = new Vector<Object>();
				row.add(worker.isActive());
				row.add(worker.getLabor_ID());
				row.add(worker.getReplacementLabor_ID());
				row.add(worker.getUNS_Job_Role_ID());
				row.add(worker.getPayrollTerm());
				row.add(worker.getC_BPartner_ID());
				row.add(worker.getPresenceStatus());
				row.add(worker.getWorkHours());
				row.add(worker.getHoursOverTime());

				retValues.add(row);
			}
			
			retValues.add(createNullVector(9));
		}
		else if (jTableName.equalsIgnoreCase(TABLE_WORKERRESULT))
		{
			if (m_ProductionWRs == null && m_ProductionWorkers != null &&
					m_ProductionWorkers.length > 0 && m_ProductionWorkers[0].get_ID() != 0)
				m_ProductionWRs = m_ProductionWorkers[0].getResults();
			else
				return retValues;
			
			for (MUNSProductionWorkerResult result : m_ProductionWRs)
			{
				Vector<Object> row = new Vector<Object>();
				row.add(result.isActive());
				row.add(result.getUNS_Production_Worker_ID());
				row.add(result.getM_Product_ID());
				row.add(result.getProductionQty());
				row.add(result.getReceivableAmt());
				row.add(result.getDescription());

				retValues.add(row);
			}
		}
		return retValues;
	}
	
	private Vector<Object> createNullVector(int columnCount){
		Vector<Object> retval = new Vector<Object>();
		
		retval.add(true); // IsActive Column
			
		for (int i=1; i<columnCount; i++){
			retval.add(null);
		}
		
		return retval;
	}

	@Override
	public void tableChange(String tableName, int row, int column,
			String columnName) {
		// only allow change on Detail.
		if (m_isRefreshing)
			return;
		
		if (!m_isInitialized)
			return;
		
		if (m_itsNewRow)
			return;
		
		Object newValue = m_tblListener.getValueOf(tableName, row, column);
			
		if (tableName.equals(TABLE_WORKER) && column==1 && newValue!= null)
		{
			m_isRefreshing = true;
			if (duplicateWorker((Integer) newValue, row, column))
				return;
			
			m_itsNewRow = itsNewRow(row);
		} 
		else if (tableName.equals(TABLE_WORKER) && row==m_ProductionWorkers.length)
			return;
			
		//if new record will automatic add 1 row
		if (tableName.equals(TABLE_WORKER) && m_itsNewRow)
		{
			m_tblListener.addRow(TABLE_WORKER, createNullVector(9));
			
			m_itsNewRow = false;
		}
		
		//change Resource Worker / Replacement Labor / Job Role
		if (tableName.equals(TABLE_WORKER) && (column==1 || column==2 || column==3)){
			if (newValue == null)
				newValue = 0;
			int intNew = (Integer) newValue;
			
			if (column==1 && intNew != m_ProductionWorkers[row].getLabor_ID())
				changeResourceWorker(m_ProductionWorkers[row], intNew);
			else if (column==2 && intNew != m_ProductionWorkers[row].getReplacementLabor_ID())
				m_ProductionWorkers[row].setReplacementLabor_ID(intNew);
			else if (column==3 && intNew != m_ProductionWorkers[row].getUNS_Job_Role_ID())
				m_ProductionWorkers[row].setUNS_Job_Role_ID(intNew);
		}
		//change IsActive Column
		else if (column==0)
		{
			if (newValue == null)
				newValue = true;
			boolean booleanNew = (Boolean) newValue;
			
			if (tableName.equals(TABLE_WORKER))
				m_ProductionWorkers[row].setIsActive(booleanNew);
			else
				m_ProductionWRs[row].setIsActive(booleanNew);
		} 
		//change Employment Status / Presence Status / Description
		else if (tableName.equals(TABLE_WORKER) && (column==4 || column==6) || 
				tableName.equals(TABLE_WORKERRESULT) && column==5)
		{
			if (newValue == null)
				newValue = "";
			String stringNew = (String) newValue;
			
			if (column==4 && !stringNew.equals(m_ProductionWorkers[row].getPayrollTerm()))
				m_ProductionWorkers[row].setPayrollTerm(stringNew);
			else if (column==6 && !stringNew.equals(m_ProductionWorkers[row].getPresenceStatus()))
				m_ProductionWorkers[row].setPresenceStatus(stringNew);
			else if (column==5 && !stringNew.equals(m_ProductionWRs[row].getDescription()))
				m_ProductionWRs[row].setDescription(stringNew);
		} 
		//change Work Hours / Hours OverTime / Production Quantity
		else if (tableName.equals(TABLE_WORKER) && (column==7 || column==8) ||
				tableName.equals(TABLE_WORKERRESULT) && column==3)
		{
			if (newValue == null)
				newValue = BigDecimal.ZERO;
			BigDecimal bdNew = (BigDecimal) newValue;
			
			if (column==7 && bdNew.compareTo(m_ProductionWorkers[row].getWorkHours())!=0)
				m_ProductionWorkers[row].setWorkHours(bdNew);
			else if (column==6 && bdNew.compareTo(m_ProductionWorkers[row].getHoursOverTime())!=0)
				m_ProductionWorkers[row].setHoursOverTime(bdNew);
			else if (column==3 && bdNew.compareTo(m_ProductionWRs[row].getProductionQty())!=0)
				m_ProductionWRs[row].setProductionQty(bdNew);
		}
		
		//refresh table Worker
		if (tableName.equals(TABLE_WORKER))
		{
			m_ProductionWorkers[row].m_isFormModification = true;
			refreshTableWorker();
			if (m_ProductionWorkers[row].get_ID()!=0)
				refreshTableWorkerResult(m_ProductionWorkers[row].get_ID());
		} 
		//refresh table worker result
		else if (tableName.equals(TABLE_WORKERRESULT)) 
		{
			m_ProductionWRs[row].m_isFormModification = true;
			refreshTableWorkerResult(m_ProductionWRs[row].getUNS_Production_Worker_ID());
			m_ProductionWRs[row].saveEx();
		}
		
		m_isUpdateNecessary = true;
	}

	private boolean duplicateWorker(Integer newValue, int row, int col) 
	{
		int count = 0;
		for (MUNSProductionWorker worker : m_ProductionWorkers)
		{
			if (worker.getLabor_ID()==newValue)
				count++;
		}
		
		if (count == 0)
			return false;
		
		if (m_ProductionWorkers.length == row)
			m_tblListener.setValueOf(TABLE_WORKER, null, row, col);
		else
			m_tblListener.setValueOf(TABLE_WORKER, 
					m_ProductionWorkers[row].getLabor_ID(), row, col);
		
		m_isRefreshing = false;
		return true;
	}

	private boolean itsNewRow(int row) {
		// it's new Row at MUNSProductionWorker
		try 
		{
			m_ProductionWorkers[row].get_ID();
			
			return false;
		} 
		catch (ArrayIndexOutOfBoundsException newRecord) 
		{
			// store to temporary data
			MUNSProductionWorker[] temp = m_ProductionWorkers;

			// initialization new m_ProductionWorkers variable
			m_ProductionWorkers = new MUNSProductionWorker[row + 1];

			// store old date to new variable
			for (int i = 0; i < row; i++)
				m_ProductionWorkers[i] = temp[i];

			// initialization new row
			m_ProductionWorkers[row] = new MUNSProductionWorker(m_ctx, 0, m_trxName);
			m_ProductionWorkers[row].setAD_Org_ID(m_Production.getAD_Org_ID());
			m_ProductionWorkers[row].setIsActive(true);
			m_ProductionWorkers[row].setIsAdditional(true);
			m_ProductionWorkers[row].setPresenceStatus(MUNSProductionWorker.PRESENCESTATUS_FullDay);
			m_ProductionWorkers[row].setWorkHours(new BigDecimal(7));
			m_ProductionWorkers[row].setHoursOverTime(BigDecimal.ZERO);
			m_ProductionWorkers[row].setUNS_Production_ID(m_Production.get_ID());
			
			return m_ProductionWorkers[row].save();
		}
	}

	private void changeResourceWorker(MUNSProductionWorker worker, int intNew) {
		worker.setLabor_ID(intNew);
		worker.setReplacementLabor_ID(intNew);
		
		MUNSEmployee employee = worker.getLabor();
		worker.setPayrollTerm(employee.getPayrollTerm());
		worker.setC_BPartner_ID(employee.getVendor_ID());
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
		
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_ProductionDate, m_Production.getProductionDate());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_MovementDate, m_Production.getMovementDate());
		m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_AD_Org_ID, m_Production.get_Value("AD_Org_ID"));
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
		
		if (m_Production.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Multi))
		{
			//overwrite output
			MUNSProductionOutPlan primary = m_Production.getPrimaryOutput();
			m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_M_Product_ID, primary.getM_Product_ID());
			m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_M_Locator_ID, primary.getM_Locator_ID());
			m_tblListener.setFieldValue(MUNSProduction.COLUMNNAME_ProductionQty, primary.getQtyPlan());
		}
		
		refreshTableWorker();
		
		if (!m_Production.getOutputType().equals(MUNSProduction.WORKERRESULTTYPE_Daily))
		{
			m_tblListener.setPanelVisible(TABLE_WORKERRESULT, true);
			if (m_ProductionWorkers != null && m_ProductionWorkers.length > 0
					&& m_ProductionWorkers[0].get_ID() != 0)
			refreshTableWorkerResult(m_ProductionWorkers[0].get_ID());
		}
		else
			m_tblListener.setPanelVisible(TABLE_WORKERRESULT, false);
		
		m_isRefreshing = false;
	}

	private void refreshTableWorkerResult(int UNS_Production_Worker_ID) {
		m_isRefreshing = true;
		
		for (int i=0; i <= m_tblListener.getRowCountOf(TABLE_WORKERRESULT); i++)
		{
			if (m_tblListener.getRowCountOf(TABLE_WORKERRESULT)==0)
				break;
			
			m_tblListener.removeRow(TABLE_WORKERRESULT, 0);
		}
		
		for (int i=0; i < m_ProductionWRs.length; i++)
		{
			if (i>=m_tblListener.getRowCountOf(TABLE_WORKERRESULT)){
				Vector<Object> nullVector = new Vector<Object>();
				m_tblListener.addRow(TABLE_WORKERRESULT, nullVector);
			}
			
			m_tblListener.setValueOf(TABLE_WORKERRESULT, m_ProductionWRs[i].isActive(), i, 0); //Active
			m_tblListener.setValueOf(TABLE_WORKERRESULT, m_ProductionWRs[i].getUNS_Production_Worker_ID(), i, 1); //worker header 
			m_tblListener.setValueOf(TABLE_WORKERRESULT, m_ProductionWRs[i].getM_Product_ID(), i, 2); //Product
			m_tblListener.setValueOf(TABLE_WORKERRESULT, m_ProductionWRs[i].getProductionQty(), i, 3); //Quantity
			m_tblListener.setValueOf(TABLE_WORKERRESULT, m_ProductionWRs[i].getReceivableAmt(), i, 4); //Receipt Amount
			m_tblListener.setValueOf(TABLE_WORKERRESULT, m_ProductionWRs[i].getDescription(), i, 5); //Description
		}
		m_isRefreshing = false;
		
	}

	private void refreshTableWorker() {
		m_isRefreshing = true;
		
		if (m_ProductionWorkers.length == 0){
			m_isRefreshing = false;
			return;
		}
		
		for (int i=0; i < m_ProductionWorkers.length; i++)
		{
			m_tblListener.setValueOf(TABLE_WORKER, m_ProductionWorkers[i].isActive(), i, 0); //active 
			m_tblListener.setValueOf(TABLE_WORKER, m_ProductionWorkers[i].getLabor_ID(), i, 1); //labor ID
			m_tblListener.setValueOf(TABLE_WORKER, m_ProductionWorkers[i].getReplacementLabor_ID(), i, 2); //Current Labor
			m_tblListener.setValueOf(TABLE_WORKER, m_ProductionWorkers[i].getUNS_Job_Role_ID(), i, 3); //Job Role
			m_tblListener.setValueOf(TABLE_WORKER, m_ProductionWorkers[i].getPayrollTerm(), i, 4); //Employment Status
			m_tblListener.setValueOf(TABLE_WORKER, m_ProductionWorkers[i].getC_BPartner_ID(), i, 5); //Contractor
			m_tblListener.setValueOf(TABLE_WORKER, m_ProductionWorkers[i].getPresenceStatus(), i, 6); //Present Status
			m_tblListener.setValueOf(TABLE_WORKER, m_ProductionWorkers[i].getWorkHours(), i, 7); //Normal work hour
			m_tblListener.setValueOf(TABLE_WORKER, m_ProductionWorkers[i].getHoursOverTime(), i, 8); //Hour Overtime
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
		
		refreshAllData();
		// If canceled .. just keep the form opened.
	}

	/* (non-Javadoc)
	 * @see org.matica.form.listener.MAT_I_BusinessLogic#onConfirmRefresh()
	 */
	@Override
	public void onConfirmRefresh() 
	{
		m_isInitialized = true;
		initProductionInputFormData();
		refreshAllData();
	}
	
	@Override
	public void onRowSelection(String tableName, int row, int[] rows) {
		if (TABLE_WORKER.equals(tableName) 
				&& !m_Production.getWorkerResultType()
					.equals(MUNSProduction.WORKERRESULTTYPE_Daily)
				&& row < m_ProductionWorkers.length)
		{
			int UNS_Production_Worker_ID = m_ProductionWorkers[row].get_ID();
			
			//refresh from DB
			List<MUNSProductionWorkerResult> list = null;
			list = Query.get(m_tblListener.getCtx(), UNSPPICModelFactory.getExtensionID(), 
								MUNSProductionWorkerResult.Table_Name, 
								"UNS_Production_Worker_ID=" + UNS_Production_Worker_ID, 
								m_tblListener.getTrxName())
							.setOrderBy("UNS_Production_WorkerResult_ID").list();
			MUNSProductionWorkerResult[] retValue = new MUNSProductionWorkerResult[list.size()];
			retValue = list.toArray(retValue);
			m_ProductionWRs = retValue;
			
			refreshTableWorkerResult(UNS_Production_Worker_ID);
		}
	}

	private boolean saveAllData()
	{
		boolean saveCompleted = false;
		
		m_Production.m_isFormModification = true;
		if (!m_Production.save())
			return saveCompleted;
		
		for (int i=0; i < m_ProductionWorkers.length; i++) {
			MUNSProductionWorker worker = m_ProductionWorkers[i];
			
			if (worker.m_isFormModification ){
				if (worker.getLabor_ID()!=0)
					worker.saveEx();
				else
					continue;
			}
			
			for (int j=0; j < m_ProductionWRs.length; j++) {
				MUNSProductionWorkerResult wResult = m_ProductionWRs[j];
				
				if (wResult.m_isFormModification){
					wResult.saveEx();
				}
			}
		}
		
		m_isNeedCommitTrx  = true;
		return true;
	}

}
