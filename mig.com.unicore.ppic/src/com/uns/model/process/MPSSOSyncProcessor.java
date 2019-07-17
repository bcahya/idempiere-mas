/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.compiere.model.MOrderLine;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;

import com.uns.util.UNSTimeUtil;

import com.uns.model.MUNSMPSHeader;
import com.uns.model.MUNSMPSProduct;
import com.uns.model.MUNSMPSProductWeekly;


/**
 * @author AzHaidar
 *
 */
public class MPSSOSyncProcessor extends SvrProcess {

	static final String SOMPSSyncMethod_Sync_only_SO_Lines_exists_in_MPS = "Method1";
	static final String SOMPSSyncMethod_Sync_only_selected_SO_Lines_not_in_MPS = "Method2";
	static final String SOMPSSyncMethod_Sync_all_SO_Lines_not_in_MPS = "Method3";
	static final String SOMPSSyncMethod_Sync_all_SO_Lines = "Method4";
	
	String m_SOMPSSyncMethod = null;
	int m_UNSMPSHeaderID = 0;
	int m_COrderID = 0;
	int m_COrderLineID = 0;
	MUNSMPSHeader m_MPSHeader = null;
	
	/**
	 * 
	 */
	public MPSSOSyncProcessor() {
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] param = getParameter();
		for (int i=0; i<param.length; i++)
		{
			String paramName = param[i].getParameterName();
			if (paramName.equals("SOMPSSyncMethod")) {
				m_SOMPSSyncMethod = (String) param[i].getParameter();
			}
			else if (paramName.equals("UNS_MPSHeader_ID")){
				m_UNSMPSHeaderID = param[i].getParameterAsInt();
			}
			else if (paramName.equals("C_Order_ID")){
				m_COrderID = param[i].getParameterAsInt();
			}
			else if (paramName.equals("C_OrderLine_ID")){
				m_COrderLineID = param[i].getParameterAsInt();
			}
		}
		if (m_SOMPSSyncMethod == null || m_UNSMPSHeaderID == 0 || m_COrderID == 0)
			throw new AdempiereUserError("Error: you have not select either Synchronize method, MPS, or SO");
		if (SOMPSSyncMethod_Sync_only_selected_SO_Lines_not_in_MPS.equals(m_SOMPSSyncMethod) 
			&& m_COrderLineID == 0)
			throw new AdempiereUserError("Error: you have to select SO Line.");
		m_MPSHeader = new MUNSMPSHeader(getCtx(), m_UNSMPSHeaderID, get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		String msg = null;
		
		if (SOMPSSyncMethod_Sync_only_selected_SO_Lines_not_in_MPS.equals(m_SOMPSSyncMethod))
		{
			msg = syncSelectedSOLine(m_COrderLineID);
		}
		
		String whereClause = "";
		if (m_COrderID == 0)
			whereClause = " DatePromised BETWEEN '" + m_MPSHeader.getPeriodStart().getStartDate() + 
			"' AND '" + m_MPSHeader.getPeriodEnd().getEndDate() + "'";
		else 
			whereClause = " C_Order_ID=" + m_COrderID + " ";
		
		if (SOMPSSyncMethod_Sync_all_SO_Lines_not_in_MPS.equals(m_SOMPSSyncMethod))
		{
			whereClause += " AND IsSyncWithMPS='N' AND M_Product_ID NOT IN " +
					" (SELECT mpsProd.M_Product_ID FROM UNS_MPSProduct mpsProd " +
					"  WHERE mpsProd.UNS_MPSHeader_ID=" + m_UNSMPSHeaderID + ")";
			int[] toSyncLines = PO.getAllIDs(MOrderLine.Table_Name, whereClause, get_TrxName());
			for (int soLine : toSyncLines)
			{
				msg = syncSelectedSOLine(soLine);
			}
		}
		else if (SOMPSSyncMethod_Sync_only_SO_Lines_exists_in_MPS.equals(m_SOMPSSyncMethod))
		{
			whereClause += " AND IsSyncWithMPS='N' AND M_Product_ID IN " +
					" (SELECT mpsProd.M_Product_ID FROM UNS_MPSProduct mpsProd " +
					"  WHERE mpsProd.UNS_MPSHeader_ID=" + m_UNSMPSHeaderID + ")";
			int[] toSyncLines = PO.getAllIDs(MOrderLine.Table_Name, whereClause, get_TrxName());
			for (int soLine : toSyncLines)
			{
				msg = syncSelectedSOLine(soLine);
			}
		}
		else if (SOMPSSyncMethod_Sync_all_SO_Lines.equals(m_SOMPSSyncMethod))
		{
			whereClause += " AND IsSyncWithMPS='N'";
			int[] toSyncLines = PO.getAllIDs(MOrderLine.Table_Name, whereClause, get_TrxName());
			for (int soLine : toSyncLines)
			{
				msg = syncSelectedSOLine(soLine);
			}
		}
		
		
		return msg;
	}
	
	/**
	 * 
	 * @param soLineID
	 * @return
	 */
	String syncSelectedSOLine(int soLineID)
	{
		MOrderLine soLine = new MOrderLine(getCtx(), soLineID, get_TrxName());
		
		I_M_Product product = soLine.getM_Product();
		String msg = "SO Line of product [" + product.getValue() + "] has been sycn to MPS.";
		
		BigDecimal orderedQtyUOM = soLine.getQtyEntered();
		BigDecimal orderedQtyMT = orderedQtyUOM.multiply(product.getWeight()).multiply(new BigDecimal(0.001));
		
		Timestamp datePromised = soLine.getC_Order().getDatePromised();
		if (null == datePromised)
			datePromised = soLine.getC_Order().getDatePromised();
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(datePromised.getTime());
		int month = cal.get(Calendar.MONTH);

		int weekNo = UNSTimeUtil.getProductionWeekNo(datePromised);
		
		// Create the MPS Product Tab for the SO Line.
		MUNSMPSProduct mpsProduct = MUNSMPSProduct.getCreate(getCtx(), 
													   m_UNSMPSHeaderID, 
													   soLine.getM_Product_ID(), 
													   get_TrxName());
		mpsProduct.setTotalActualToOrderUOM(mpsProduct.getTotalActualToOrderUOM().add(orderedQtyUOM));
		mpsProduct.setTotalActualToOrderMT(mpsProduct.getTotalActualToOrderMT().add(orderedQtyMT));
		
		mpsProduct.setActualToOrderUOM(month, mpsProduct.getActualToOrderUOM(month).add(orderedQtyUOM));
		mpsProduct.setActualToOrderMT(month, mpsProduct.getActualToOrderMT(month).add(orderedQtyMT));
		
		//mpsProduct.setTotalActualToStockUOM(mpsProduct.getTotalActualToStockUOM().add(orderedQtyUOM));
		//mpsProduct.setTotalActualToStockMT(mpsProduct.getTotalActualToStockMT().add(orderedQtyMT));
		
		//mpsProduct.setQtyUOM(month, mpsProduct.getQtyUOM(month).add(orderedQtyUOM));
		//mpsProduct.setQtyMT(month, mpsProduct.getQtyMT(month).add(orderedQtyMT));
		
		if (mpsProduct.getWeekToBeUpdated() == 0 || mpsProduct.getWeekToBeUpdated() > weekNo)
			mpsProduct.setWeekToBeUpdated(weekNo);
		
		if (!mpsProduct.save())
			throw new AdempiereException("Failed when create new MPS Product.");
		
		// Create the MPS Product Weekly Tab for the SO Line.
		MUNSMPSProductWeekly mpsProductWeekly =
				MUNSMPSProductWeekly.get(getCtx(), mpsProduct, weekNo, get_TrxName());
		mpsProductWeekly.setActualToOrderUOM(mpsProductWeekly.getActualToOrderUOM().add(orderedQtyUOM));
		mpsProductWeekly.setActualToOrderMT(mpsProductWeekly.getActualToOrderMT().add(orderedQtyMT));
		
		//mpsProductWeekly.setActualToStockUOM(mpsProductWeekly.getActualToStockUOM().add(orderedQtyUOM));
		//mpsProductWeekly.setActualToStockMT(mpsProductWeekly.getActualToStockMT().add(orderedQtyMT));

		if (!mpsProductWeekly.save())
			throw new AdempiereException("Failed when create new MPS Product Weekly.");
		
		MUNSMPSHeader mpsHeader = (MUNSMPSHeader) mpsProduct.getUNS_MPSHeader();

		if (!mpsHeader.isSyncDatabase())
		{
			mpsHeader.setIsSyncDatabase(true);

			if (!mpsHeader.save())
				throw new AdempiereException("Failed when updating MPS based on production scheduled.");
		}

		//TODO MPS SALES ORDER
		//soLine.setIsSyncWithMPS(true);
		if (!soLine.save())
			throw new AdempiereException ("Failed when updating SO Line-MPS sychronize status.");
		/*
		 * No need yet, sebab pada posisi ini blm dapat diketahui di resource department mana akan di perintah 
		 * oleh PPIC utk memproduksi ini, selain itu informasi actual to order pada tab MPSProduct Resource dan 
		 * MPS Product Resource Weekly tidak diperlukan.
		MUNSResource rsc = 
				MUNSResource.getChildOf(getCtx(), get_TrxName(), 
									   mo.getProductionDepartment_ID(), 
									   getM_Product_ID(), 
									   mo.getUNS_MPSHeader().getUNS_Forecast_Header().getUNS_Resource_ID());
		if (rsc == null)
			throw new AdempiereUserError ("Cannot Rebundle schedule for undefined Manufacturing Resource of the product.");
		
		MUNSMPSProductResource mpsProductRsc = 
				MUNSMPSProductResource.get(getCtx(), 
										   mpsProduct.getUNS_MPSProduct_ID(), 
										   rsc.getUNS_Resource_ID(), 
										   get_TrxName());
		if (mpsProductRsc.save())
			throw new AdempiereException("Failed when create new MPS Product Resource.");
			
		MUNSMPSProductRscWeekly mpsProductRscWeekly =  
				MUNSMPSProductRscWeekly.get(getCtx(), 
											mpsProductRsc.getUNS_MPSProduct_Resource_ID(), 
											weekNo, 
											get_TrxName());
		
		if (mpsProductRscWeekly.save())
			throw new AdempiereException("Failed when create new MPS Product Resource Weekly.");
		
		setUNS_MPSProductRsc_Weekly_ID(mpsProductRscWeekly.getUNS_MPSProductRsc_Weekly_ID());
		*/
		return msg;
	}

}