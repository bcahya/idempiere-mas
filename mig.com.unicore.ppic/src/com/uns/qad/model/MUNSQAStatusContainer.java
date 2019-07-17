/**
 * 
 */
package com.uns.qad.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;

import com.uns.model.MUNSContainerArrival;
import com.uns.model.MUNSContainerDeparture;
import com.uns.model.MUNSContainerManagement;
import com.uns.model.X_UNS_ContainerArrival;
import com.uns.model.X_UNS_ContainerDeparture;

/**
 * @author YAKA
 *
 */
public class MUNSQAStatusContainer extends X_UNS_QAStatusContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4677830204711251236L;
	private MUNSQAStatusNC[] m_lines;

	/**
	 * @param ctx
	 * @param UNS_QAStatusContainer_ID
	 * @param trxName
	 */
	public MUNSQAStatusContainer(Properties ctx, int UNS_QAStatusContainer_ID,
			String trxName) {
		super(ctx, UNS_QAStatusContainer_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSQAStatusContainer(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		setIsNC(false);
		
		if (getExteriorCondition().equals(EXTERIORCONDITION_Poor))
			setIsNC(true);
		if (getPestActivity().equals(PESTACTIVITY_Yes))
			setIsNC(true);
		if (getOdourSmell().equals(ODOURSMELL_Abnormal))
			setIsNC(true);
		if (getFloor().equals(FLOOR_Poor))
			setIsNC(true);
		if (getWall().equals(WALL_Poor))
			setIsNC(true);
		if (getRoof().equals(ROOF_Poor))
			setIsNC(true);
		if (getCleanliness().equals(CLEANLINESS_Not_OK))
			setIsNC(true);
		if (getDoor().equals(DOOR_Poor))
			setIsNC(true);
		if (getHinges().equals(HINGES_Poor))
			setIsNC(true);
		if (getSeal().equals(SEAL_Poor))
			setIsNC(true);
		
		if(isNC())
			setQAStatus(QASTATUS_OnHold);
		else
			setQAStatus(QASTATUS_Release);
		
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean beforeDelete() {
		if(!getParent().isProcessed())
			return true;
		return false;
	}

	@Override
	protected boolean afterDelete(boolean success) {
		if (getParent().getInspectionType().equals(X_UNS_QAContainerInspection.INSPECTIONTYPE_DepartureLoading)){
			MUNSContainerDeparture cd = new MUNSContainerDeparture(getCtx(), getUNS_ContainerDeparture_ID(), get_TrxName());
			cd.setQAStatus(X_UNS_ContainerDeparture.QASTATUS_PendingInspectionLabTest);
			if (!cd.save(get_TrxName()))
				throw new AdempiereException("Can't update container departure");
		} else {
			MUNSContainerArrival ca = new MUNSContainerArrival(getCtx(), getUNS_ContainerArrival_ID(), get_TrxName());
			ca.setQAStatus(X_UNS_ContainerArrival.QASTATUS_PendingInspectionLabTest);
			if (!ca.save(get_TrxName()))
				throw new AdempiereException("Can't update container arrival");
		}
		return super.afterDelete(success);
	}

	public MUNSQAContainerInspection getParent(){
		return new MUNSQAContainerInspection(getCtx(), getUNS_QAContainerInspection_ID(), get_TrxName());
	}
	
	/**
	 * 
	 * @param requery
	 * @return MUNSQAStatusNC[]
	 */
	protected MUNSQAStatusNC[] getLines(boolean requery){
		if (m_lines != null	&& !requery){
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		
		List<MUNSQAStatusNC> mList =
				new Query(getCtx(), MUNSQAStatusNC.Table_Name
						, MUNSQAStatusNC.COLUMNNAME_UNS_QAStatusContainer_ID + " =?"
						, get_TrxName())
		.setParameters(getUNS_QAStatusContainer_ID())
		.setOrderBy(MUNSQAStatusNC.COLUMNNAME_UNS_QAStatusContainer_ID).list();
		
		m_lines = new MUNSQAStatusNC[mList.size()];
		mList.toArray(m_lines);
		return m_lines;
	}
	
	/**
	 * 
	 * @return MUNSQAStatusNC[]
	 */
	public MUNSQAStatusNC[] getLines()
	{
		return getLines(false);
	}
	
	public MUNSQAStatusNC getLastNC(){
		String sql="SELECT UNS_QAStatus_NC_ID FROM UNS_QAStatus_NC " +
				"WHERE UNS_QAStatusContainer_ID = ? ORDER BY UNS_QAStatus_NC_ID DESC";
		int retVal = DB.getSQLValue(get_TrxName(), sql, getUNS_QAStatusContainer_ID());
		if(retVal == -1)
			return null;
		return new MUNSQAStatusNC(getCtx(), retVal, get_TrxName());
	}

	public void setCondition(boolean good) {
		if(good){
			setExteriorCondition(EXTERIORCONDITION_Good);
			setPestActivity(PESTACTIVITY_No);
			setOdourSmell(ODOURSMELL_Normal);
			setFloor(FLOOR_Good);
			setWall(WALL_Good);
			setRoof(ROOF_Good);
			setCleanliness(CLEANLINESS_OK);
			setDoor(DOOR_Good);
			setHinges(HINGES_Good);
			setSeal(SEAL_Good);			
		} else {
			setExteriorCondition(EXTERIORCONDITION_Poor);
			setPestActivity(PESTACTIVITY_Yes);
			setOdourSmell(ODOURSMELL_Abnormal);
			setFloor(FLOOR_Poor);
			setWall(WALL_Poor);
			setRoof(ROOF_Poor);
			setCleanliness(CLEANLINESS_Not_OK);
			setDoor(DOOR_Poor);
			setHinges(HINGES_Poor);
			setSeal(SEAL_Poor);
		}
	}

	@Override
	public void setProcessed(boolean Processed) {
		super.setProcessed(Processed);
		MUNSContainerManagement cm = new MUNSContainerManagement(
				getCtx(), getUNS_ContainerManagement_ID(), get_TrxName());
		cm.setLastQAStatus(getQAStatus());
		cm.setLastQADate(getParent().getInspectionDate());
		if(!cm.save()){
			throw new AdempiereException("error when update Container Management.");
		}
	}

	public void updateContainerStatus(){
		if (getParent().getInspectionType().equals(X_UNS_QAContainerInspection.INSPECTIONTYPE_DepartureLoading)){
			MUNSContainerDeparture cd = new MUNSContainerDeparture(getCtx(), getUNS_ContainerDeparture_ID(), get_TrxName());
			if(getQAStatus().equals(QASTATUS_Release))
				cd.setQAStatus(QASTATUS_Release);
			else if (getQAStatus().equals(QASTATUS_Reject))
				cd.setQAStatus(QASTATUS_Reject);
			else cd.setQAStatus(QASTATUS_OnHold);
			if (!cd.save(get_TrxName()))
				throw new AdempiereException("Can't update container departure");
		} else {
			MUNSContainerArrival ca = new MUNSContainerArrival(getCtx(), getUNS_ContainerArrival_ID(), get_TrxName());
			if(getQAStatus().equals(QASTATUS_Release))
				ca.setQAStatus(QASTATUS_Release);
			else if (getQAStatus().equals(QASTATUS_Reject))
				ca.setQAStatus(QASTATUS_Reject);
			else ca.setQAStatus(QASTATUS_OnHold);
			if (!ca.save(get_TrxName()))
				throw new AdempiereException("Can't update container arrival");
		}
	}
	
	public void updateContainerManagement(){
		MUNSContainerManagement cm = new MUNSContainerManagement(getCtx(), getUNS_ContainerManagement_ID(), get_TrxName());
		cm.setLastQADate(getParent().getInspectionDate());
		cm.setLastQAStatus(getQAStatus());
		if (!cm.save(get_TrxName()))
			throw new AdempiereException("Can't update container management");
	}

	public void createContainerStatus(MUNSQAContainerInspection header, PO po, boolean b){
		setAD_Org_ID(header.getAD_Org_ID());
		setInspectionTime(new Timestamp( System.currentTimeMillis() ));
		setChkByLOG("Not checked");
		setChkByQAD("Not checked");
		setUNS_QAContainerInspection_ID(header.get_ID());
		setQAStatus(MUNSQAStatusContainer.QASTATUS_OnHold);
		setCondition(true);
		if(po.get_TableName().equals(MUNSContainerDeparture.Table_Name)){
			MUNSContainerDeparture cd = (MUNSContainerDeparture) po;
			setUNS_ContainerDeparture_ID(cd.get_ID());
			setUNS_ContainerManagement_ID(cd.getUNS_ContainerManagement_ID());
		} else {
			MUNSContainerArrival ca = (MUNSContainerArrival) po;
			setUNS_ContainerArrival_ID(ca.get_ID());
			setUNS_ContainerManagement_ID(ca.getUNS_ContainerManagement_ID());
		}
	}
}
