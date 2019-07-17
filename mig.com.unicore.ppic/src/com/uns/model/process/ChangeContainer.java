package com.uns.model.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.model.MUNSContainerDeparture;
import com.uns.model.MUNSShipmentSchedule;
import com.uns.qad.model.MUNSQAContainerInspection;
import com.uns.qad.model.MUNSQAStatusContainer;

public class ChangeContainer extends SvrProcess {


	private int m_cd_ID;
	private int m_cm_ID;
	private MUNSContainerDeparture cd = null;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++){
			String name = para[i].getParameterName();
			log.fine("prepare - " + para[i]);
			if (MUNSContainerDeparture.COLUMNNAME_UNS_ContainerDeparture_ID.equals(name))
				m_cd_ID = para[i].getParameterAsInt();
			else if (MUNSContainerDeparture.COLUMNNAME_UNS_ContainerManagement_ID.equals(name))
				m_cm_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		
		cd = new MUNSContainerDeparture(getCtx(), m_cd_ID, get_TrxName()); 
	}

	@Override
	protected String doIt() throws Exception {
		MUNSShipmentSchedule ss = new MUNSShipmentSchedule(getCtx(), cd.getHeader().get_ID(), get_TrxName());
		if (ss.getUNS_QAContainerInspection_ID()>0){
			MUNSQAContainerInspection ci = new MUNSQAContainerInspection(getCtx(), ss.getUNS_QAContainerInspection_ID(), get_TrxName());
			for(MUNSQAStatusContainer sc : ci.getLines()){
				if (sc.getUNS_ContainerDeparture_ID()==m_cd_ID){
					if(sc.getUNS_ContainerManagement_ID()==m_cm_ID)
						continue;
					else{
						MUNSQAStatusContainer new_sc = new MUNSQAStatusContainer(getCtx(), 0, get_TrxName());
						new_sc.setAD_Org_ID(cd.getAD_Org_ID());
						new_sc.setInspectionTime(new Timestamp( System.currentTimeMillis() ));
						new_sc.setChkByLOG("Not checked");
						new_sc.setChkByQAD("Not checked");
						new_sc.setUNS_QAContainerInspection_ID(ci.get_ID());
						new_sc.setUNS_ContainerDeparture_ID(m_cd_ID);
						new_sc.setUNS_ContainerManagement_ID(m_cm_ID);
						new_sc.setCondition(true);
						if(!new_sc.save())
							return "Can't change container";
						
						cd.setUNS_ContainerManagement_ID(m_cm_ID);
						if(!cd.save())
							return "Can't change container departure";
					}
				}
			}
		}
		return null;
	}

}
