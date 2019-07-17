
package com.unicore.model.process;

import java.util.logging.Level;

import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.unicore.model.MUNSExpedition;
import com.unicore.model.MUNSExpeditionDetail;


public class CreateJobSpecOrder extends SvrProcess 
{

	private int m_JobSO_ID = 0;

	@Override
	protected void prepare() 
	{
		
		ProcessInfoParameter[] params = getParameter();
		for (int i=0; i<params.length; i++)
		{
			ProcessInfoParameter param = params[i];
			String paramName = param.getParameterName();
			if(paramName.equals("JobSO_ID"))
				m_JobSO_ID = param.getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown parameter " + paramName);
		}
	}

	@Override
	protected String doIt() throws Exception 
	{	
//		if(m_JobSpecOrder <= 0)
//		{
//			throw new AdempiereException("Error Message : Parameter Cannot Fill");
//		}
//		
//		if(m_Exp.isMultiplePath())
//		{
//			isCreateJSO();
//			int cekPathRefer = DB.getSQLValue(get_TrxName(), "SELECT UNS_ExpeditionPath_ID from UNS_ExpeditionPath "
//					+ "where uns_expedition_id = "+m_Exp.get_ID()+" and jobsopath_id ="+m_JobSpecOrder);
//			if(cekPathRefer <0)
//			{
//				MUNSExpedition m_ExpRefer = new MUNSExpedition(getCtx(), m_JobSpecOrder, get_TrxName());
//				for(MUNSExpeditionPath pathRefer : m_ExpRefer.getPath(m_JobSpecOrder))
//				{
//					MUNSExpeditionPath createPath = new MUNSExpeditionPath(getCtx(), 0, get_TrxName());
//					createPath.setAD_Org_ID(pathRefer.getAD_Org_ID());
//					createPath.setOrigin_ID(pathRefer.getOrigin_ID());
//					createPath.setUNS_Expedition_ID(m_Exp.get_ID());
//					createPath.setM_PriceList_ID(pathRefer.getM_PriceList_ID());
//					createPath.setDestination_ID(pathRefer.getDestination_ID());
//					createPath.setDistance(pathRefer.getDistance());
//					createPath.setDescription(pathRefer.getDescription());
//					createPath.setPriceList(pathRefer.getPriceList());
//					createPath.setVolume(pathRefer.getVolume());
//					createPath.setTonase(pathRefer.getTonase());
//					createPath.setPriceActual(pathRefer.getPriceActual());
//					createPath.setPriceLimit(pathRefer.getPriceLimit());
//					createPath.setTotalAmt(pathRefer.getTotalAmt());
//					createPath.setM_PriceList_ID(pathRefer.getM_PriceList_ID());
//					createPath.setJobSOPath_ID(m_JobSpecOrder);
//					createPath.saveEx();
//				
//					for(MUNSExpeditionDetail detailRefer : pathRefer.getDetail(pathRefer.get_ID()))
//					{
//						MUNSExpeditionDetail createDetail = new MUNSExpeditionDetail(getCtx(), 0, get_TrxName());
//						createDetail.setAD_Org_ID(detailRefer.getAD_Org_ID());
////						createDetail.setUNS_Expedition_ID(m_Exp.get_ID());
//						createDetail.setUNS_ExpeditionPath_ID(createPath.getUNS_ExpeditionPath_ID());
//						createDetail.setC_Charge_ID(detailRefer.getC_Charge_ID());
//						createDetail.setWeight(detailRefer.getWeight());
//						createDetail.setQty(detailRefer.getQty());
//						createDetail.setTonase(detailRefer.getTonase());
//						createDetail.setVolume(detailRefer.getVolume());
//						createDetail.setItemDescription(detailRefer.getItemDescription());
//						createDetail.setDescription(detailRefer.getDescription());
//						createDetail.setPriceActual(detailRefer.getPriceActual());
//						createDetail.setPriceLimit(detailRefer.getPriceLimit());
//						createDetail.setPriceList(detailRefer.getPriceList());
//						createDetail.setLineNetAmt(detailRefer.getLineNetAmt());
//						createDetail.setJobSODetail_ID(m_JobSpecOrder);
//						createDetail.isJobSpecOrder(isCreateJSO);
//						createDetail.saveEx();
//					}
//							
//				}
//			}
//		}
//		else
//		{
//			MUNSExpedition m_ExpRefer = new MUNSExpedition(getCtx(), m_JobSpecOrder, get_TrxName());
//			int cekDetailRefer = DB.getSQLValue(get_TrxName(), "SELECT uns_expeditiondetail_id from UNS_ExpeditionDetail "
//					+ "where uns_expedition_id = "+m_Exp.get_ID());
//			if(cekDetailRefer<0)
//			{
//				for(MUNSExpeditionDetail detailRefer : m_ExpRefer.getDetail(m_JobSpecOrder))
//				{
//					MUNSExpeditionDetail createDetail = new MUNSExpeditionDetail(getCtx(), 0, get_TrxName());
//					createDetail.setAD_Org_ID(detailRefer.getAD_Org_ID());
//					createDetail.setUNS_Expedition_ID(m_Exp.get_ID());
//					createDetail.setC_Charge_ID(detailRefer.getC_Charge_ID());
//					createDetail.setWeight(detailRefer.getWeight());
//					createDetail.setQty(detailRefer.getQty());
//					createDetail.setTonase(detailRefer.getTonase());
//					createDetail.setVolume(detailRefer.getVolume());
//					createDetail.setItemDescription(detailRefer.getItemDescription());
//					createDetail.setDescription(detailRefer.getDescription());
//					createDetail.setPriceActual(detailRefer.getPriceActual());
//					createDetail.setPriceLimit(detailRefer.getPriceLimit());
//					createDetail.setPriceList(detailRefer.getPriceList());
//					createDetail.setLineNetAmt(detailRefer.getLineNetAmt());
//					createDetail.setJobSODetail_ID(m_JobSpecOrder);
//					createDetail.saveEx();
//				}
//			
//			}
//			else
//			{
//				/** Current Expedition */
//				MUNSExpeditionPath createPathExpCurr = new MUNSExpeditionPath(getCtx(), 0, get_TrxName());
//				createPathExpCurr.setAD_Org_ID(m_Exp.getAD_Org_ID());
//				createPathExpCurr.setUNS_Expedition_ID(m_Exp.getUNS_Expedition_ID());
//				createPathExpCurr.setOrigin_ID(m_Exp.getOrigin_ID());
//				createPathExpCurr.setDestination_ID(m_Exp.getDestination_ID());
//				createPathExpCurr.setDistance(m_Exp.getDistance());
//				createPathExpCurr.setM_PriceList_ID(m_Exp.getM_PriceList_ID());
//				createPathExpCurr.setVolume(m_Exp.getVolume());
//				createPathExpCurr.setTonase(m_Exp.getTonase());
//				createPathExpCurr.setPriceActual(m_Exp.getPriceActual());
//				createPathExpCurr.setPriceLimit(m_Exp.getPriceLimit());
//				createPathExpCurr.setPriceList(m_Exp.getPriceList());
//				createPathExpCurr.setTotalAmt(m_Exp.getTotalAmt());
//				createPathExpCurr.setJobSOPath_ID(m_Exp.get_ID());
//				createPathExpCurr.setUNS_Expedition_ID(m_Exp.get_ID());
//				createPathExpCurr.saveEx();
//				
////				int test= createPathExpCurr.getUNS_ExpeditionPath_ID();
//				for(MUNSExpeditionDetail detailCurr : m_Exp.getDetail(m_Exp.get_ID()))
//				{
//					detailCurr.setUNS_ExpeditionPath_ID(createPathExpCurr.getUNS_ExpeditionPath_ID());
//					detailCurr.saveEx();
//				}
//				
//				/** Next Expedition */
//				
//				MUNSExpedition expNext = new MUNSExpedition(getCtx(), m_JobSpecOrder, get_TrxName());
//				MUNSExpeditionPath createPathExpNext = new MUNSExpeditionPath(getCtx(), 0, get_TrxName());
//				createPathExpNext.setAD_Org_ID(expNext.getAD_Org_ID());
//				createPathExpNext.setUNS_Expedition_ID(m_Exp.getUNS_Expedition_ID());
//				createPathExpNext.setOrigin_ID(expNext.getOrigin_ID());
//				createPathExpNext.setDestination_ID(expNext.getDestination_ID());
//				createPathExpNext.setDistance(expNext.getDistance());
//				createPathExpNext.setM_PriceList_ID(expNext.getM_PriceList_ID());
//				createPathExpNext.setVolume(expNext.getVolume());
//				createPathExpNext.setTonase(expNext.getTonase());
//				createPathExpNext.setPriceActual(expNext.getPriceActual());
//				createPathExpNext.setPriceLimit(expNext.getPriceLimit());
//				createPathExpNext.setPriceList(expNext.getPriceList());
//				createPathExpNext.setTotalAmt(expNext.getTotalAmt());
//				createPathExpNext.setJobSOPath_ID(expNext.get_ID());
//				createPathExpNext.setUNS_Expedition_ID(m_Exp.get_ID());
//				createPathExpNext.saveEx();
//				for(MUNSExpeditionDetail detailRefer : expNext.getDetail(m_Exp.get_ID()))
//				{
//					MUNSExpeditionDetail createDetail = new MUNSExpeditionDetail(getCtx(), 0, get_TrxName());
//					createDetail.setAD_Org_ID(detailRefer.getAD_Org_ID());
////					createDetail.setUNS_Expedition_ID(m_Exp.get_ID());
//					createDetail.setUNS_ExpeditionPath_ID(createPathExpNext.getUNS_ExpeditionPath_ID());
//					createDetail.setC_Charge_ID(detailRefer.getC_Charge_ID());
//					createDetail.setWeight(detailRefer.getWeight());
//					createDetail.setQty(detailRefer.getQty());
//					createDetail.setTonase(detailRefer.getTonase());
//					createDetail.setVolume(detailRefer.getVolume());
//					createDetail.setItemDescription(detailRefer.getItemDescription());
//					createDetail.setDescription(detailRefer.getDescription());
//					createDetail.setPriceActual(detailRefer.getPriceActual());
//					createDetail.setPriceLimit(detailRefer.getPriceLimit());
//					createDetail.setPriceList(detailRefer.getPriceList());
//					createDetail.setLineNetAmt(detailRefer.getLineNetAmt());
//					createDetail.setJobSODetail_ID(m_JobSpecOrder);
//					createDetail.saveEx();
//					m_Exp.isJobSpecOrder(isCreateJSO());
//				}
//				
//				m_Exp.setIsMultiplePath(true);
//				m_Exp.saveEx();
//			}
//		}
//	
//		m_Exp.setJobSO_ID(m_JobSpecOrder);
//		m_Exp.saveEx();
		int count = 0;
		
		MUNSExpedition exp = new MUNSExpedition(getCtx(), m_JobSO_ID, get_TrxName());
		for(MUNSExpeditionDetail oldExpDetail : exp.getDetail(exp.get_ID()))
		{
			MUNSExpeditionDetail expDetail = new MUNSExpeditionDetail(getCtx(), 0, getName());
			PO.copyValues(oldExpDetail, expDetail);
			expDetail.setUNS_Expedition_ID(getRecord_ID());
			expDetail.setJobSODetail_ID(oldExpDetail.get_ID());
			expDetail.setIsManual(false);
			expDetail.saveEx(get_TrxName());
			count += 1;
		}
		
		return "#" + count + " Record has created";
	}
	
//	public boolean isCreateJSO()
//	{
//		isCreateJSO = true;
//		return isCreateJSO;
//	}
}