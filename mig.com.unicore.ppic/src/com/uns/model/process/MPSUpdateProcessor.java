/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.MUNSMPSHeader;
import com.uns.model.MUNSMPSProduct;
import com.uns.model.MUNSMPSProductWeekly;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author AzHaidar
 *
 */
public class MPSUpdateProcessor extends SvrProcess {

	int m_UNSMPSHeaderID = 0;
	boolean m_updateAllNextMPS = false;
	
	/**
	 * 
	 */
	public MPSUpdateProcessor() {
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
			if (paramName.equals("MPSHeader_ID")) {
				m_UNSMPSHeaderID = param[i].getParameterAsInt();
			}
			else if (paramName.equals("UpdateNext")) {
				m_updateAllNextMPS = param[i].getParameterAsBoolean();
			}
		}
		
		if (m_UNSMPSHeaderID == 0)
			m_UNSMPSHeaderID = getRecord_ID();
		if (m_UNSMPSHeaderID == 0)
			throw new AdempiereUserError("You have to select an MPS to be updated.");
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		return updateMPS(new MUNSMPSHeader(getCtx(), m_UNSMPSHeaderID, get_TrxName()));
	}
	
	/**
	 * 
	 * @param mps
	 * @return
	 */
	String updateMPS(MUNSMPSHeader mps)
	{
		String whereClause = "UNS_MPSHeader_ID=? AND (WeekToBeUpdated IS NOT NULL AND WeekToBeUpdated<>0)";
		
		List<MUNSMPSProduct> mpsProductList = 
				Query.get(getCtx(), 
						  UNSPPICModelFactory.getExtensionID(), 
						  MUNSMPSProduct.Table_Name, 
						  whereClause, 
						  get_TrxName())
					.setParameters(mps.get_ID())
					.list();

		for (MUNSMPSProduct mpsProduct : mpsProductList)
		{
			updateMPSProduct(mpsProduct);
		}
		if (!m_updateAllNextMPS)
			return "Update to MPS succeed.";
		
		MUNSMPSHeader nextMPS = mps.getNextMPS();
		if (nextMPS != null)
			return updateMPS(mps.getNextMPS());
		
		return "Update to MPS and next's MPS succeed.";
	}
	
	/**
	 * 
	 * @param mpsProduct
	 * @return
	 */
	String updateMPSProduct(MUNSMPSProduct mpsProduct)
	{
		MProduct product = (MProduct) mpsProduct.getM_Product();
		String msg = "Update to MPS Product of " + product.getValue();
		
		MUNSMPSHeader mps = (MUNSMPSHeader) mpsProduct.getUNS_MPSHeader();
		MUNSMPSHeader prevMPS = (MUNSMPSHeader) mps.getPrevMPS();
		
		MUNSMPSProductWeekly[] mpsProdWeeklyList = mpsProduct.getLinesWeekly();
					//mpsProduct.getLinesFromWeek(mpsProduct.getWeekToBeUpdated());
		
		int weekToUpdateIx = 0;
		for (weekToUpdateIx=0; weekToUpdateIx < mpsProdWeeklyList.length; weekToUpdateIx++)
			if (mpsProdWeeklyList[weekToUpdateIx].getWeekNo() == mpsProduct.getWeekToBeUpdated())
				break;

		MUNSMPSProductWeekly prevWeek = null;
		if (weekToUpdateIx == 0 && prevMPS != null)
			prevWeek = prevMPS.getLatestWeeklyProductOf(mpsProduct.getM_Product_ID());
		
		//Hashtable<Integer, MUNSMPSProductWeekly> mpsProdWeeklyMap = convertIntoWeeklyMap(mpsProdWeeklyList);
		
		BigDecimal prevAAB = Env.ZERO;
		BigDecimal prevSAB = Env.ZERO;
		BigDecimal prevSOH = Env.ZERO;
		BigDecimal prevOH = Env.ZERO;
		BigDecimal prevSRSFS = Env.ZERO;
		BigDecimal prevRSFS = Env.ZERO;
		if (null != prevWeek) {
			prevSAB = prevWeek.getScheduledAvaialbleBalances();
			prevAAB = prevWeek.getActualAvailableBalances();
			prevSOH = prevWeek.getSOH();
			prevOH = prevWeek.getQtyOnHand();
			prevSRSFS = prevWeek.getSRSFS();
			prevRSFS = prevWeek.getRSFS();
		}
		else { 
			prevAAB = mpsProduct.getQtyOnHand();
			prevSAB = prevAAB;
			prevSOH = prevAAB;
			prevOH = prevAAB;
		}
		
		for (int i=weekToUpdateIx; i < mpsProdWeeklyList.length; i++)
		{
			MUNSMPSProductWeekly currWeekMPS = mpsProdWeeklyList[i];
			//MUNSMPSProductWeekly nextWeekMPS = (i+1 == mpsProdWeeklyList.length)? null : mpsProdWeeklyList[i+1];
			//BigDecimal nextAO = (nextWeekMPS == null)? Env.ZERO : nextWeekMPS.getActualToOrderUOM();
			MUNSMPSProductWeekly latestReleasedProductWeekly = 
					currWeekMPS.getPreviousReleasedMPSProductWeekly(
							prevMPS, mpsProdWeeklyList, i, mpsProduct.getM_Product_ID());
			
			BigDecimal currSAB = prevSAB.add(currWeekMPS.getActualScheduledUOM())
					.subtract(currWeekMPS.getActualToOrderUOM());
			
			BigDecimal latestSASFS = (latestReleasedProductWeekly == null)? 
					Env.ZERO : latestReleasedProductWeekly.getActualScheduledUOM();
			
			BigDecimal currSRSFS = latestSASFS.add(prevSRSFS).subtract(currWeekMPS.getActualToOrderUOM());
			
			BigDecimal currSOH = 
					prevSOH.add(currWeekMPS.getActualScheduledUOM()).subtract(currWeekMPS.getActualToOrderUOM());
			
			BigDecimal currAAB = 
					prevAAB.add(currWeekMPS.getActualProduced()).subtract(currWeekMPS.getQtyDelivered());
			
			BigDecimal latestASFS = (latestReleasedProductWeekly == null)? 
					Env.ZERO : latestReleasedProductWeekly.getActualProduced();
			
			BigDecimal qtyMiscUsed = (latestReleasedProductWeekly == null)? 
					Env.ZERO : latestReleasedProductWeekly.getQtyMiscUsed();
			
			BigDecimal currRSFS = 
					latestASFS.add(prevRSFS).subtract(currWeekMPS.getQtyDelivered()).subtract(qtyMiscUsed);
			
			BigDecimal currOH = 
					prevOH.add(currWeekMPS.getActualProduced())
					.subtract(currWeekMPS.getQtyDelivered())
					.subtract(qtyMiscUsed);
			
			currWeekMPS.setScheduledAvaialbleBalances(currSAB);
			currWeekMPS.setSRSFS(currSRSFS);
			currWeekMPS.setSOH(currSOH);
			
			currWeekMPS.setActualAvailableBalances(currAAB);
			currWeekMPS.setSRSFS(currRSFS);
			currWeekMPS.setQtyOnHand(currOH);
			
			prevWeek = currWeekMPS;
			if (!currWeekMPS.save())
				throw new AdempiereException ("Failed when updating MPS Product for weekno:" + currWeekMPS.getWeekNo());

			prevSAB = currSAB;
			prevAAB = currAAB;
			prevSOH = currSOH;
			prevOH = currOH;
			prevSRSFS = currSRSFS;
			prevRSFS = currRSFS;
		}
		
		MUNSMPSProduct nextMPSProduct = 
				MUNSMPSProduct.getNoCreate(getCtx(), mps.getNextMPS().get_ID(), product.get_ID(), get_TrxName());
		if (nextMPSProduct != null)
		{
			nextMPSProduct.setQtyOnHand(prevOH);
			nextMPSProduct.setSOH(prevSOH);
			
			int weekToBeUpdated = (prevWeek.getWeekNo() == 53)? 1 : prevWeek.getWeekNo() + 1;
			nextMPSProduct.setWeekToBeUpdated(weekToBeUpdated);
			
			if (!nextMPSProduct.save())
				throw new AdempiereException("Failed when updating the Qty on hand of next MPS Product.");
		}
		return msg + " succeed.";
	}
	
	/**
	 * 
	 * @param mpsWeeklyArray
	 * @return
	 */
	Hashtable<Integer, MUNSMPSProductWeekly> convertIntoWeeklyMap (MUNSMPSProductWeekly[] mpsWeeklyArray)
	{
		Hashtable<Integer, MUNSMPSProductWeekly> retMap = new Hashtable<Integer, MUNSMPSProductWeekly>();
		
		for (MUNSMPSProductWeekly mpsWeek : mpsWeeklyArray)
		{
			retMap.put(mpsWeek.getWeekNo(), mpsWeek);
		}
		
		return retMap;
	}

}
