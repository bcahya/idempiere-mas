/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;

import org.compiere.model.I_M_Product;
import org.compiere.model.MDocType;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import com.uns.util.UNSTimeUtil;

import com.uns.base.model.Query;
import com.uns.model.MUNSMRP;
import com.uns.model.MUNSMRPWeekly;
import com.uns.model.factory.UNSPPICModelFactory;


/**
 * @author AzHaidar
 *
 */
public class MRPCreateRequisition extends SvrProcess {

	//private int p_Record_ID;
	private Timestamp m_DateFrom;
	private Timestamp m_DateTo;
	private int m_M_PriceList_ID;
	private int m_M_Warehouse_ID;
	private String m_PriorityRule;
	//private int m_M_Product_ID;
	private boolean m_ConsolidateDocument;
	private int m_weekFrom;
	private int m_weekTo;

	/**
	 * 
	 */
	public MRPCreateRequisition() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if ("DateFrom".equals(name))
				m_DateFrom = (Timestamp) para[i].getParameter();
			else if ("DateTo".equals(name))
				m_DateTo = (Timestamp) para[i].getParameter();
			//else if ("M_Product_ID".equals(name))
			//	m_M_Product_ID = (Integer) para[i].getParameterAsInt();
			else if ("M_PriceList_ID".equals(name))
				m_M_PriceList_ID = (Integer) para[i].getParameterAsInt();
			else if ("M_Warehouse_ID".equals(name))
				m_M_Warehouse_ID = (Integer) para[i].getParameterAsInt();
			else if ("PriorityRule".equals(name))
				m_PriorityRule = (String) para[i].getParameter();
			else if ("ConsolidateDocument".equals(name))
				m_ConsolidateDocument = para[i].getParameterAsBoolean();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		
		//p_Record_ID = getRecord_ID(); 
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		m_weekFrom = UNSTimeUtil.getProductionWeekNo(m_DateFrom);
		
		m_weekTo = UNSTimeUtil.getProductionWeekNo(m_DateTo);
		
		String orderBy = MUNSMRPWeekly.COLUMNNAME_M_Product_ID + ", " + MUNSMRPWeekly.COLUMNNAME_WeekNo + " ASC ";
		
		MUNSMRPWeekly[] mrpList = 
				MUNSMRPWeekly.get(getCtx(), m_weekFrom, m_weekTo, true, true, orderBy, get_TrxName());
		
		if (null == mrpList || mrpList.length == 0)
			return "No (0) MRP Weekly Line is generated to new Requisition.";

		Hashtable<Integer, Hashtable<Integer, MUNSMRPWeekly>> mapOfRequirement = analyzeRequirement(mrpList);
		
		if (m_ConsolidateDocument)
			return consolidateRequestLine(mapOfRequirement, mrpList[0].getAD_Org_ID());
		else
			return separatedRequestLine(mapOfRequirement);		
	}
	
	/**
	 * 
	 * @param mrpList
	 * @return
	 */
	String separatedRequestLine(Hashtable<Integer, Hashtable<Integer, MUNSMRPWeekly>> mapOfRequirement)
	{
		Hashtable<Integer, MRequisition> tmpGroupingByWeekNo = new Hashtable<Integer, MRequisition>();
		for (Hashtable<Integer, MUNSMRPWeekly> mapOfMRPWeekly : mapOfRequirement.values())
		{
			I_M_Product material = null;
			for (MUNSMRPWeekly mrpLine : mapOfMRPWeekly.values())
			{
				if (mrpLine.getSOR().signum() <= 0)
					continue;
				MRequisition requisition = tmpGroupingByWeekNo.get(mrpLine.getWeekNo());
				if (requisition == null)
				{
					requisition = new MRequisition(getCtx(), 0, get_TrxName());
					requisition.setAD_Org_ID(mrpLine.getAD_Org_ID());
					requisition.setAD_User_ID(getAD_User_ID());
					requisition.setDateRequired(mrpLine.getStartDate());
					requisition.setDateDoc(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					requisition.setM_PriceList_ID(m_M_PriceList_ID);
					requisition.setM_Warehouse_ID(m_M_Warehouse_ID);
					requisition.setPriorityRule(m_PriorityRule);
					requisition.setDescription("::Auto generated from MRP::");
					requisition.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_PurchaseRequisition));
					requisition.save();
					
					tmpGroupingByWeekNo.put(mrpLine.getWeekNo(), requisition);
				}
				MRequisitionLine reqLine = new MRequisitionLine(requisition);

				if (null == material) // because the loop is based on same product.
					material = mrpLine.getM_Product();
				
				reqLine.setM_Product_ID(material.getM_Product_ID());
				reqLine.setC_UOM_ID(material.getC_UOM_ID());
				//reqLine.setC_BPartner_ID(line.getC_BPartner_ID());
				reqLine.setQty(mrpLine.getSOR());
				//reqLine.setM_AttributeSetInstance_ID(line.getM_AttributeSetInstance_ID());
				
				if (!reqLine.save())
					return "Error when saving requisition line for product of '" + material.getValue() + "'. " +
							"Probably you have not set default/current Supplier for the product.";
				/*
				 * mrpLine.setRequisitionRef_ID(reqLine.get_ID());
				 * Belum ada yang perlu di save utk MRPWeekly, karena akan di handle pada oleh trigger.
				if (!mrpLine.save())
					return "Error when saving requisition line for product of '" + material.getValue() + "'. " +
					"Cannot update MRPWeekly with the created Requisition Line ID.";
				*/
			}
		}
		return "Completed.";
	}
	
	/**
	 * 
	 * @param mrpList
	 * @return
	 */
	String consolidateRequestLine(Hashtable<Integer, Hashtable<Integer, MUNSMRPWeekly>> mapOfRequirement,
			int AD_Org_ID)
	{
		MRequisition requisition = new MRequisition(getCtx(), 0, get_TrxName());
		requisition.setAD_Org_ID(AD_Org_ID);
		requisition.setAD_User_ID(getAD_User_ID());
		requisition.setDateRequired(m_DateFrom);
		requisition.setDateDoc(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		requisition.setM_PriceList_ID(m_M_PriceList_ID);
		requisition.setM_Warehouse_ID(m_M_Warehouse_ID);
		requisition.setPriorityRule(m_PriorityRule);
		requisition.setDescription("::Auto generated from MRP::");
		requisition.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_PurchaseRequisition));
		requisition.save();
		for (Hashtable<Integer, MUNSMRPWeekly> mapOfMRPWeekly : mapOfRequirement.values())
		{
			I_M_Product material = null;
			BigDecimal qtyToRequest = Env.ZERO;
			BigDecimal moq = Env.ZERO;
			for (MUNSMRPWeekly mrpLine : mapOfMRPWeekly.values())
			{
				if (mrpLine.getSOR().signum() <= 0)
					continue;
				if (null == material) {
					material = mrpLine.getM_Product();
					moq = mrpLine.getUNS_MRP().getMOQ();
				}
				qtyToRequest = qtyToRequest.add(mrpLine.getSOR());				
			}
			if (qtyToRequest.compareTo(moq) < 0)
				qtyToRequest = moq;
			
			MRequisitionLine reqLine = new MRequisitionLine(requisition);

			reqLine.setM_Product_ID(material.getM_Product_ID());
			reqLine.setC_UOM_ID(material.getC_UOM_ID());
			reqLine.setQty(qtyToRequest);
			
			if (!reqLine.save())
				return "Error when saving requisition line for product of '" + material.getValue() + "'. " +
						"Probably you have not set default/current Supplier for the product.";
		}
		return "Completed.";
	}
	
	/**
	 * 
	 * @param mrpList
	 * @return
	 */
	Hashtable<Integer, Hashtable<Integer, MUNSMRPWeekly>> 
		analyzeRequirement(MUNSMRPWeekly[] mrpList)
	{
		Hashtable<Integer, Hashtable<Integer, MUNSMRPWeekly>> mapOfRequirement =
				new Hashtable<Integer, Hashtable<Integer,MUNSMRPWeekly>>();
		// Convert them into multi map first.
		for (MUNSMRPWeekly mrpWeekly : mrpList)
		{
			Hashtable<Integer, MUNSMRPWeekly> mapOfByProduct = mapOfRequirement.get(mrpWeekly.getM_Product_ID());
			if (null == mapOfByProduct)
			{
				mapOfByProduct = new Hashtable<Integer, MUNSMRPWeekly>();
				mapOfRequirement.put(mrpWeekly.getM_Product_ID(), mapOfByProduct);
			}
			mapOfByProduct.put(mrpWeekly.getWeekNo(), mrpWeekly);
		}
		
		int prevWeekNo = m_weekFrom - 1;
		if (prevWeekNo <= 0)
			prevWeekNo = 53;
		
		Enumeration<Integer> listOfMaterial = mapOfRequirement.keys();
		while (listOfMaterial.hasMoreElements())
		{
			int productID = listOfMaterial.nextElement();
			MUNSMRPWeekly prevMRP = Query
					.get(getCtx(), UNSPPICModelFactory.getExtensionID(), 
						 MUNSMRPWeekly.Table_Name, "M_Product_ID=? AND WeekNo=?", get_TrxName())
					.setParameters(productID, prevWeekNo)
					.first();
			BigDecimal prevSOH = Env.ZERO;
			if (prevMRP != null)
				prevSOH = prevMRP.getSOH();
			
			Hashtable<Integer, MUNSMRPWeekly> mapOfMRPWeekly = mapOfRequirement.get(productID);
			MUNSMRP theMRP = null;
			for (int weekNo=m_weekFrom; weekNo <= m_weekTo; weekNo++)
			{
				MUNSMRPWeekly mrpWeekly = mapOfMRPWeekly.get(weekNo);
				if (mrpWeekly == null)
					continue;
				if (theMRP == null)
					theMRP = (MUNSMRP) mrpWeekly.getUNS_MRP();
				BigDecimal requiredSOR = 
						mrpWeekly.getScheduledRequirement()
						.add(theMRP.getSafetyStock())
						.subtract(prevSOH);
				if (requiredSOR.signum() < 0)
					requiredSOR = Env.ZERO;
				else if (requiredSOR.compareTo(theMRP.getMOQ()) < 0)
					requiredSOR = theMRP.getMOQ();
				
				BigDecimal soh = prevSOH.add(requiredSOR).subtract(mrpWeekly.getScheduledRequirement());
				mrpWeekly.setSOR(requiredSOR);
				mrpWeekly.setSOH(soh);
				
				prevSOH = soh;
			}
		}
		
		return mapOfRequirement;
	}

}
