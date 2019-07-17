/**
 * 
 */
package com.uns.model.process;

import java.util.Hashtable;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MTransaction;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.util.UNSTimeUtil;

import com.uns.model.MUNSMPSHeader;
import com.uns.model.MUNSMPSProduct;
import com.uns.model.MUNSMPSProductWeekly;

/**
 * @author AzHaidar
 *
 */
public class MPSQtyUsedSyncProcessor extends SvrProcess 
{
	private MUNSMPSHeader m_MPSHeader = null;
	private boolean m_isSyncOnlySoldProduct = true;
	
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
			if (paramName.equals("UNS_MPSHeader_ID")) 
			{
				int mpsHeaderID = param[i].getParameterAsInt();
				m_MPSHeader = new MUNSMPSHeader(getCtx(), mpsHeaderID, get_TrxName());
			}
			else if (paramName.equals("OnlySold_Product"))
			{
				m_isSyncOnlySoldProduct = param[i].getParameterAsBoolean();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		String whereClause = "AD_Client_ID=? AND MovementType IN (?, ?, ?, ?, ?, ?) " +
				" AND MovementDate BETWEEN ? AND ? AND IsSyncWithMPS=?";
		if (m_isSyncOnlySoldProduct)
			whereClause += " AND M_Product_ID IN (SELECT mpsP.M_Product_ID FROM UNS_MPSProduct mpsP "
						+ " 	INNER JOIN M_Product p ON p.M_Product_ID=mpsP.M_Product_ID " +
						"		WHERE p.isSold='Y' AND mpsP.UNS_MPSHeader_ID=?)";
		else
			whereClause += " AND M_Product_ID IN (SELECT mpsP.M_Product_ID FROM UNS_MPSProduct mpsP "
						+ "		WHERE p.isSold='Y' AND mpsP.UNS_MPSHeader_ID=?)";
		
		List<MTransaction> transactionList = 
				new Query(getCtx(), MTransaction.Table_Name, whereClause, get_TrxName())
				.setParameters(getAD_Client_ID(), 
							   MTransaction.MOVEMENTTYPE_CustomerShipment,
							   MTransaction.MOVEMENTTYPE_Production_,
							   //TODO MTransaction service
							   //MTransaction.MOVEMENTTYPE_Service_,
							   MTransaction.MOVEMENTTYPE_WorkOrder_,
							   MTransaction.MOVEMENTTYPE_InventoryOut,
							   MTransaction.MOVEMENTTYPE_VendorReturns,
							   m_MPSHeader.getPeriodStart().getStartDate(),
							   m_MPSHeader.getPeriodEnd().getEndDate(),
							   'N',
							   m_MPSHeader.get_ID())
				.setOrderBy(MTransaction.COLUMNNAME_M_Product_ID + ", " + 
							MTransaction.COLUMNNAME_MovementDate + ", " + 
							MTransaction.COLUMNNAME_MovementType)
				.list();
		
		Hashtable<String, MUNSMPSProductWeekly> mpsPrdWeeklyMap = new Hashtable<String, MUNSMPSProductWeekly>();
		
		for (MTransaction trx : transactionList)
		{
			//TODO Sales Order MPS
			//if (trx.isSyncWithMPS())
			//	continue;
			
			int weekNo = UNSTimeUtil.getProductionWeekNo(trx.getMovementDate());
			String mapKey = trx.getM_Product_ID() + "-" + weekNo;
			
			MUNSMPSProductWeekly mpsPrdWeekly = mpsPrdWeeklyMap.get(mapKey);
			if (mpsPrdWeekly == null) 
			{
				mpsPrdWeekly = MUNSMPSProductWeekly.get(
						getCtx(), m_MPSHeader.get_ID(), trx.getM_Product_ID(), weekNo, get_TrxName());
				if (mpsPrdWeekly == null) // if still null then just continue.
					continue;
				//if (mpsPrdWeekly.get_ID() == 0)
				//	mpsPrdWeekly.initBasicNecessaryInfo(mpsProduct);
				mpsPrdWeeklyMap.put(mapKey, mpsPrdWeekly);
			}
			if (trx.getMovementType().equals(MTransaction.MOVEMENTTYPE_CustomerShipment))
				mpsPrdWeekly.setQtyDelivered(mpsPrdWeekly.getQtyDelivered().add(trx.getMovementQty().abs()));
			else 
				mpsPrdWeekly.setQtyMiscUsed(mpsPrdWeekly.getQtyMiscUsed().add(trx.getMovementQty().abs()));
			
			MUNSMPSProduct mpsPrd = mpsPrdWeekly.getParent();
			if (mpsPrd.getWeekToBeUpdated() <= weekNo && mpsPrd.getWeekToBeUpdated() > 0)
				continue;
			
			mpsPrd.setWeekToBeUpdated(weekNo);
			if (!mpsPrd.save())
				throw new AdempiereException("Failed when setting week to be updated in MPS Product.");
			
			if (m_MPSHeader.isSyncDatabase())
			{
				m_MPSHeader.setIsSyncDatabase(true);

				if (!m_MPSHeader.save())
					throw new AdempiereException("Failed when updating MPS Header.");
			}

		}
		//save all map values.
		for (MUNSMPSProductWeekly mpsPrdWeekly : mpsPrdWeeklyMap.values())
		{
			if (!mpsPrdWeekly.save())
				throw new AdempiereException("Failed when saving synchronized data in MPS Product Weekly.");
		}
		return null;
	}

}
