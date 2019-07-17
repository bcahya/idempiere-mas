package com.uns.model.process;
/**
 * 
 *
package com.uns.model.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartnerProduct;
import org.compiere.model.MProduct;
import org.compiere.model.MProductBOM;
import org.compiere.model.MStorageOnHand;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import com.uns.model.MUNSMPSHeader;
import com.uns.model.MUNSMPSProduct;
import com.uns.model.MUNSMPSProductWeekly;
import com.uns.model.MUNSMRP;
import com.uns.model.MUNSMRPWeekly;
import com.uns.model.MUNSOtherProductConf;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSResourceInOut;
import com.uns.model.X_UNS_Resource_InOut;

/**
 * @author menjangan
 * @Untasoft 
 *
public class MRPGenerator_OLD extends SvrProcess {
	
	private Properties m_ctx = null;
	private String m_trxName = null;
	private Hashtable<String, MUNSMRPWeekly> m_MRPWeeklySumary = new Hashtable<String, MUNSMRPWeekly>();
	private Hashtable<String, MUNSMRPWeekly> m_MRPWeekList = new Hashtable<String, MUNSMRPWeekly>();
	private Hashtable<String, MUNSMRP> m_mrpListDetail = new Hashtable<String, MUNSMRP>();
	private Hashtable<Integer, MUNSMRP> m_mrpListSummary = new Hashtable<Integer, MUNSMRP>();
	private Hashtable<Integer, MProduct> m_ProductListIsAllocated = new Hashtable<Integer, MProduct>();
	private Hashtable<Integer, MProduct> m_materialOfOutputResourceTmp = new Hashtable<Integer, MProduct>();
	private Hashtable<Integer, MProduct> m_listMulti = new Hashtable<Integer, MProduct>();
	private Hashtable<Integer, BigDecimal> m_OnHandOfProduct = null;
	private MUNSMPSHeader m_prevMPS = null;
	private String m_processMsg;
	private MUNSMPSHeader m_MPSHeader = null;
	int m_MaxWeekOfPeriods = 0;
	private int m_WeekStart = 0;
	
	/**
	 * 
	 *
	public MRPGenerator_OLD() {
		
	}
	
	public MRPGenerator_OLD(MUNSMPSHeader mpsHeader)
	{
		m_ctx = mpsHeader.getCtx();
		m_trxName = mpsHeader.get_TrxName();
		m_MPSHeader = mpsHeader;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 *
	@Override
	protected void prepare() {
		
		m_ctx = getCtx();
		m_trxName = get_TrxName();
		m_MPSHeader= new MUNSMPSHeader(m_ctx, getRecord_ID(), m_trxName);
		if(m_MPSHeader.getPrevMPS_ID() > 0)
			m_prevMPS = (MUNSMPSHeader)m_MPSHeader.getPrevMPS();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 *
	@Override
	protected String doIt() throws Exception {
		String msg = null;
		MUNSMRP[] listMRPToUpdate = m_MPSHeader.getListMRP(false);
		Hashtable<Integer, BigDecimal> onHands = getAllStorageOnHandOfProduct(listMRPToUpdate);
		MUNSResource resource = m_MPSHeader.getResource();
		getMaterialFrom(resource);
		deleteMRPToUpdate(listMRPToUpdate);
		m_MaxWeekOfPeriods =  m_MPSHeader.getWeekEnd();
		m_WeekStart = m_MPSHeader.getWeekStart();
		msg = generateMRPOf(onHands);
		if(null == msg)
		{
			m_MPSHeader.setGenerate_MRP("Y");
			m_MPSHeader.save();
		}
		return msg;
	}
	*/
	/**
	 * 
	 * @param listMrpToUpdate
	 * @return
	 *
	private boolean deleteMRPToUpdate(MUNSMRP[] listMrpToUpdate)
	{
		for (MUNSMRP mrpToUpdate : listMrpToUpdate)
		{
			if(mrpToUpdate.getDocStatus().equals(MUNSMRP.DOCSTATUS_Completed))
			{
				m_materialOfOutputResourceTmp.remove(mrpToUpdate.getM_Product_ID());
				continue;
			}
			
			if (!mrpToUpdate.delete(true))
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param listMRPToUpdate
	 * @return
	 *
	private Hashtable<Integer, BigDecimal> getAllStorageOnHandOfProduct(MUNSMRP[] listMRPToUpdate)
	{
		if (null != m_OnHandOfProduct)
		{
			return m_OnHandOfProduct;
		}
		//Expected storage On Hand of products to be update 
		m_OnHandOfProduct = new Hashtable<Integer, BigDecimal>();
		for (MUNSMRP mrpToUpdate : listMRPToUpdate)
		{
			BigDecimal qtyOnHand = mrpToUpdate.getExpectedStorageOnHand();
			m_OnHandOfProduct.put(mrpToUpdate.getM_Product_ID(), qtyOnHand);
		}
		if (listMRPToUpdate.length <= 0 && null != m_prevMPS)
		{
			for (MUNSMRP prevMRP : m_prevMPS.getListMRP(false))
			{
				BigDecimal lastOnHand = prevMRP.getLastPrevOnHand( m_prevMPS.getWeekEnd());
				m_OnHandOfProduct.put(prevMRP.getM_Product_ID(), lastOnHand);
			}
		}
		
		return m_OnHandOfProduct;
	}
	*/
	/**
	 * 
	 * @param mps
	 * @return
	 *
	private String generateMRPOf(Hashtable<Integer, BigDecimal> onHands)
	{
		MUNSMPSProduct[] mpsProducts = m_MPSHeader.getLines();
		for (MUNSMPSProduct mpsProduct : mpsProducts){
			MProductBOM[] boms = MProductBOM.getBOMLines(
					m_ctx, mpsProduct.getM_Product_ID(), m_trxName);
			generateMRP(mpsProduct,boms, onHands);
		}
		saveMRP();
		return m_processMsg;
	}
	
	
	/**
	 * 
	 * @param m_Product_ID
	 * @return
	 *
	private boolean isHasAllocated(int m_Product_ID)
	{
		MProduct getProduct = m_ProductListIsAllocated.get(m_Product_ID);
		if (null == getProduct)
			return false;
		
		return true;
	}
	
	/**
	 * 
	 * @param mpsProduct
	 * @param boms
	 * @param onHands
	 *
	private void generateMRP(
			MUNSMPSProduct mpsProduct, MProductBOM[] boms, Hashtable<Integer, BigDecimal> onHands)
	{
		for (MProductBOM bom : boms)
		{
			boolean hasAllocation = false;
			boolean isMulti = false;
			MProduct listOfMaterial = 
					m_materialOfOutputResourceTmp.get(bom.getM_ProductBOM().getM_Product_ID());
			
			if (null == listOfMaterial)
				continue;
		
			MUNSMRP mrp = new MUNSMRP(m_ctx, 0, m_trxName);
			mrp.generateListProduct(mpsProduct,listOfMaterial, onHands);
			String keyMRPListDetail = "";
			
			isMulti = isMulti(mpsProduct.getM_Product_ID());
			keyMRPListDetail = ""+mpsProduct.getUNS_MPSProduct_ID()+"_"+listOfMaterial.getM_Product_ID();
			m_mrpListDetail.put(keyMRPListDetail, mrp);
			hasAllocation = isHasAllocated(listOfMaterial.getM_Product_ID());
			
			if (!isHasAllocated(listOfMaterial.getM_Product_ID()))
				m_ProductListIsAllocated.put(listOfMaterial.getM_Product_ID(), listOfMaterial);
				
				//jika product output resource tidak multi atau multi tetapi belum dialokasikan 
				// maka buat mrp summarynya
			if (!isMulti ||(isMulti && !hasAllocation))
			{
				MUNSMRP mrpSummary = m_mrpListSummary.get(listOfMaterial.getM_Product_ID());
				if (null == mrpSummary){
					mrpSummary = new MUNSMRP(m_ctx, 0, m_trxName);
					mrpSummary.copyFrom(mrp);
					mrpSummary.setUNS_MPSHeader_ID(m_MPSHeader.getUNS_MPSHeader_ID());
					m_mrpListSummary.put(mrpSummary.getM_Product_ID(), mrpSummary);
				}
				else{
					calculate(mrpSummary, mrp);
				}
			}
			
			generateMRPWeekly(mpsProduct, listOfMaterial, onHands, isMulti, hasAllocation);
			
			MProductBOM[] bomsOfBOM = MProductBOM.getBOMLines(listOfMaterial);
			if (null != bomsOfBOM)
			{
				MUNSMPSProduct mpsProductTmp = createMPSProductTmp(mrp);
				generateMRP(mpsProductTmp, bomsOfBOM, onHands);
			}
		}
	}
	
	/**
	 * 
	 * @param mrp
	 * @return
	 *
	private MUNSMPSProduct createMPSProductTmp(MUNSMRP mrp)
	{
		MUNSMPSProduct mpsProduct = new MUNSMPSProduct(m_ctx, 0, m_trxName);
		mpsProduct.setAD_Org_ID(mrp.getAD_Org_ID());
		mpsProduct.setM_Product_ID(mrp.getM_Product_ID());
		mpsProduct.setC_UOM_ID(mrp.getC_UOM_ID());
		mpsProduct.setUNS_MPSProduct_ID(mrp.getUNS_MPSProduct_ID());
		return mpsProduct;
	}
	
	/**
	 * 
	 * @param MPSP
	 * @param BOM
	 * @return
	 *
	public void generateMRPWeekly(
			MUNSMPSProduct mpsProduct, MProduct material, Hashtable<Integer, BigDecimal> onHands,
					boolean isMulti, boolean isHasAllocation)
	{
		BigDecimal onHand = BigDecimal.ZERO;
		BigDecimal QtyBOM = BigDecimal.ZERO;
		QtyBOM = MUNSResource.getBOMQty(
		mpsProduct.getM_Product_ID(), material.getM_Product_ID(), m_trxName);
	
		int M_Product_ID = material.getM_Product_ID();
		MStorageOnHand[] StorageOnHand = MStorageOnHand.getOfProduct(
				m_ctx, M_Product_ID , m_trxName);
		MUNSOtherProductConf OPC = MUNSOtherProductConf.get(m_ctx, M_Product_ID, m_trxName);
		if (null == OPC)
		{
			throw new AdempiereException("NO PRODUCT CONFIGURATION  "+ M_Product_ID);
		}
		BigDecimal safetyStock = OPC.getSafetyStock();
		BigDecimal MOQ = OPC.getMOQ();
		
		String sql = "SELECT MAX(" + MBPartnerProduct.COLUMNNAME_LeadTime + ") FROM "
				+ MBPartnerProduct.Table_Name + " WHERE "
				+ MBPartnerProduct.COLUMNNAME_M_Product_ID + "=?";
		int leadTime = DB.getSQLValue(m_trxName, sql, material.getM_Product_ID());
		if (leadTime <=0){
			throw new IllegalArgumentException(
					"Please Defined Lead Time For Product "+ material.getValue()+"_"+material.getName());
		}
		
		BigDecimal qtyOnHand = onHands.get(material.getM_Product_ID());
		if (null == qtyOnHand)
		{
			for (MStorageOnHand SOH : StorageOnHand)
			{
				onHand = onHand.add(SOH.getQtyOnHand());
			}
		}
		else
		{
			onHand = qtyOnHand;
		}
		if(null == onHand)
			onHand = BigDecimal.ZERO;
		generateMRPWeeklyDetail(mpsProduct, QtyBOM, material, isMulti, isHasAllocation);
		if(!isMulti ||  (isMulti && !isHasAllocation))
			generateMRPWeeklySummary(
							leadTime, 
								QtyBOM, 
									safetyStock, 
										MOQ,
											onHand, 
												material, 
													isMulti, 
														isHasAllocation);
	}
	
	/**
	 * 
	 * @param mpsProduct
	 * @param QtyBOM
	 * @param material
	 * @param isMulti
	 * @param isHasAllocation
	 *
	private void generateMRPWeeklyDetail(
			MUNSMPSProduct mpsProduct, BigDecimal QtyBOM, MProduct material,
			boolean isMulti, boolean isHasAllocation)
	{
		MUNSMPSProductWeekly[] MPSPWeekly = mpsProduct.getLinesWeekly(); //list MPS Weekly
		BigDecimal GrosRequirement = BigDecimal.ZERO;
		for (MUNSMPSProductWeekly MPSPW : MPSPWeekly)
		{
			BigDecimal QtyMaterial = BigDecimal.ZERO;
			if(MPSPW.getActualToOrderUOM().compareTo(BigDecimal.ZERO) > 0)
			{
				QtyMaterial = MPSPW.getActualToOrderUOM();
			}
			else
			{
				QtyMaterial = MPSPW.getActualToStockUOM();
			}
			QtyMaterial = QtyBOM.multiply(QtyMaterial);
			GrosRequirement = QtyMaterial;
			MUNSMRPWeekly MRPW = new MUNSMRPWeekly(m_ctx, 0, m_trxName); // create new MRP weekly
			
			MRPW.setAD_Org_ID(MPSPW.getAD_Org_ID());
			MRPW.setEndDate(MPSPW.getEndDate());
			MRPW.setStartDate(MPSPW.getStartDate());
			MRPW.setWeekNo(MPSPW.getWeekNo());
			MRPW.setgrosrequirement(GrosRequirement);
			MRPW.setGRManufacturing(GrosRequirement);
			MRPW.setUsagePlant(BigDecimal.ZERO);
			
			String key = ""+MRPW.getWeekNo() + "_" +mpsProduct.getUNS_MPSProduct_ID() 
					+ "_" + material.getM_Product_ID();
			
			m_MRPWeekList.put(key, MRPW);
			
			if (!isMulti || (isMulti && !isHasAllocation))
			{				
				String keySummary = "" + MRPW.getWeekNo() + "_" + material.getM_Product_ID();
				MUNSMRPWeekly MRPWeeklySumary = m_MRPWeeklySumary.get(keySummary);
				
				if (null == MRPWeeklySumary)
				{
					MRPWeeklySumary = new MUNSMRPWeekly(m_ctx, 0, m_trxName);
					MRPWeeklySumary.copyFrom(MRPW);
					m_MRPWeeklySumary.put(keySummary, MRPWeeklySumary);
				}
				else
				{
					calculate(MRPWeeklySumary, MRPW);
				}	
			}
		}
	}
	
	/**
	 * Get list of MRP Weekly and sort bay date start of week
	 * @return list Of MRP Weekly
	 *
	private List<MUNSMRPWeekly> getListOfMRPWeekly(int material_ID)
	{
		List<MUNSMRPWeekly> list = new ArrayList<MUNSMRPWeekly>();
		
		for(String keySet : m_MRPWeeklySumary.keySet())
		{
			int matarialID = getMaterialID(keySet);
			if(material_ID != matarialID)
				continue;
			
			MUNSMRPWeekly mw = m_MRPWeeklySumary.get(keySet);
			list.add(mw);
		}
		Collections.sort(list);
		return list;
	}
	
	private int getMaterialID(String keySet)
	{
		String materialID = "";
		int index = keySet.length();
		String parsMaterialID = "";
		while (!("_" + parsMaterialID).equals(keySet.substring(index)))
		{
			parsMaterialID = keySet.substring(index);
			materialID = parsMaterialID;
			index--;
		}		
		return new Integer(materialID);
	}
	
	
	/**
	 * 
	 * @param mpsProduct
	 * @param leadTime
	 * @param QtyBOM
	 * @param safetyStock
	 * @param MOQ
	 * @param onHand
	 * @param material
	 * @param isMulti
	 * @param isHasAllocation
	 *
	private void generateMRPWeeklySummary(int leadTime, BigDecimal QtyBOM,
			BigDecimal safetyStock, BigDecimal MOQ, BigDecimal onHand, MProduct material,
			boolean isMulti, boolean isHasAllocation)
	{
		MUNSMRPWeekly weeklyPORESummary = null;
		BigDecimal prevProjectedOnHand = BigDecimal.ZERO;
		BigDecimal GrosRequirement = BigDecimal.ZERO;
		BigDecimal NetRequirement = BigDecimal.ZERO;
		m_WeekStart = m_MPSHeader.getWeekStart();
		List<MUNSMRPWeekly> list = getListOfMRPWeekly(material.get_ID());
		for(MUNSMRPWeekly mrpWeek : list)
		{
			GrosRequirement = mrpWeek.getgrosrequirement();
			if (mrpWeek.getWeekNo() == m_WeekStart)
			{
				prevProjectedOnHand = onHand;
			}
			mrpWeek.setPOIssuePlanDate(null);
			mrpWeek.setUsagePlant(BigDecimal.ZERO);
			
			int week = mrpWeek.getWeekNo() - leadTime;
			BigDecimal PORE = BigDecimal.ZERO;
			if (week >= m_WeekStart)
			{
				String keySummaryPORE = "" + week 
						+  "_" + material.getM_Product_ID();
				
				weeklyPORESummary = m_MRPWeeklySumary.get(keySummaryPORE);
				PORE = weeklyPORESummary.getPORE();
				BigDecimal currOnHand = prevProjectedOnHand;
				BigDecimal GR = mrpWeek.getgrosrequirement();
				BigDecimal kekurangan = currOnHand.subtract(GR);
				
				/**
				 *  jika projected onHand masih kurang dari safety stock maka akan looping terus
				 *  sampai projected onHand lebih dari safety stock
				 *
				while (kekurangan.compareTo(safetyStock) < 0)
				{
					PORE = PORE.add(MOQ);
					currOnHand = currOnHand.add(MOQ);
					kekurangan = currOnHand.subtract(GR);
				}
				
				weeklyPORESummary.setPORE(PORE);
				weeklyPORESummary.setAORE(PORE);
			}
			
			mrpWeek.setPOR(PORE);
			mrpWeek.setScheduleReceipt(PORE);
			mrpWeek.setProjectedAvailable(prevProjectedOnHand.add(PORE));
			mrpWeek.setProjectedOnHand(
					mrpWeek.getProjectedAvailable().subtract(mrpWeek.getgrosrequirement()));
			mrpWeek.setAROH(mrpWeek.getProjectedOnHand());
			mrpWeek.setAATP(mrpWeek.getProjectedAvailable());
			NetRequirement = GrosRequirement.add(
					safetyStock).subtract(mrpWeek.getScheduleReceipt().add(prevProjectedOnHand));
			
			mrpWeek.setNetRequirement(NetRequirement);
			prevProjectedOnHand = mrpWeek.getProjectedOnHand();
		}
	}
	
	
	/**
	 * 
	 * @param productOutput
	 *
	private void initMaterialOfProduct(MProduct productOutput)
	{
		MProductBOM[] boms = MProductBOM.getBOMLines(productOutput);
		for (MProductBOM bom : boms){
			MProduct product = MProduct.get(m_ctx, bom.getM_ProductBOM().getM_Product_ID());
			if (product.isPurchased())
			{
				MProduct materialOfProductTmp = m_materialOfOutputResourceTmp.get(
						bom.getM_ProductBOM().getM_Product_ID());
				if (null == materialOfProductTmp){
					materialOfProductTmp = new MProduct(m_ctx, bom.getM_ProductBOM().getM_Product_ID(), m_trxName);
					m_materialOfOutputResourceTmp.put(materialOfProductTmp.getM_Product_ID(), materialOfProductTmp);
				}
			}
		}
	}

	/**
	 * 
	 * @param resource
	 *
	private void getOutputOf(MUNSResource resource)
	{
		MUNSResourceInOut[] outputOfResources = resource.getOutputLines();
		for (MUNSResourceInOut outputOfResource : outputOfResources){
			MProduct productOutput = new MProduct(m_ctx, outputOfResource.getM_Product_ID(), m_trxName);
			if (outputOfResource.getOutputType().equals(X_UNS_Resource_InOut.OUTPUTTYPE_Multi))
				m_listMulti.put(outputOfResource.getM_Product_ID(), productOutput);
			initMaterialOfProduct(productOutput);
		}
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 *
	private boolean isMulti(int M_Product_ID)
	{
		MProduct productMulti = m_listMulti.get(M_Product_ID);
		if (null == productMulti)
			return false;
		return true;
	}
	
	/**
	 * ini
	 * @param resource
	 * @throws Exception
	 *
	private void getMaterialFrom(MUNSResource resource)
	{
		for (MUNSResource childs : resource.getLines()){
			getOutputOf(childs);
			if (!childs.isWorkStation()){
				getMaterialFrom(childs);
			}
		}
	}
	
	/**
	 * 
	 * @param current
	 * @param next
	 *
	private void calculate(MUNSMRP current, MUNSMRP next)
	{
		current.setGrandTotalMt(current.getGrandTotalMt().add(next.getGrandTotalMt()));
		current.setGrandTotalUOM(current.getGrandTotalUOM().add(next.getGrandTotalUOM()));
		current.setTotalGrossManufacturMT(
				current.getTotalGrossManufacturMT().add(next.getTotalGrossManufacturMT()));
		current.setTotalGrossManufacturUOM(
				current.getTotalGrossManufacturUOM().add(next.getTotalGrossManufacturUOM()));
	}
	
	/**
	 * 
	 * @param current
	 * @param newWeeklyData
	 *
	private void calculate(MUNSMRPWeekly current, MUNSMRPWeekly newWeeklyData)
	{
		current.setgrosrequirement(current.getgrosrequirement().add(newWeeklyData.getgrosrequirement()));
		current.setGRManufacturing(current.getgrosrequirement());
	}
	
	
	/**
	 * 
	 * @throws AdempiereException
	 *
	private boolean saveMRP() throws AdempiereException
	{
		Iterator<MUNSMRP> MRPSumaryIterator = m_mrpListSummary.values().iterator();
		while (MRPSumaryIterator.hasNext())
		{
			MUNSMRP MRPSumary = MRPSumaryIterator.next();
			MRPSumary.setScaleAllQty();
			if (!MRPSumary.save())
				return false;
			
			Iterator<MUNSMRP> MRPListIterator = m_mrpListDetail.values().iterator();
			while (MRPListIterator.hasNext())
			{
				MUNSMRP MRPList = MRPListIterator.next();
				if (MRPList.getM_Product_ID() == MRPSumary.getM_Product_ID())
				{
					MRPList.setMRPParent_ID(MRPSumary.getUNS_MRP_ID());
					MRPList.setScaleAllQty();
					if (!MRPList.save())
						return false;
					
					for (int i=m_WeekStart; i<=m_MaxWeekOfPeriods; i++)
					{
						String key = ""+ i + "_" +MRPList.getUNS_MPSProduct_ID() 
								+ "_" + MRPList.getM_Product_ID();
						MUNSMRPWeekly MRPWeeklyList = m_MRPWeekList.get(key);
						if (null != MRPWeeklyList)
						{
							MRPWeeklyList.setUNS_MRP_ID(MRPList.getUNS_MRP_ID());
							MRPWeeklyList.setScaleAllQty();
							if (!MRPWeeklyList.save())
								return false;
						}
					}
				}
				
			}
			for (int i=1; i<=m_MaxWeekOfPeriods; i++)
			{
				String keyMRPSumary = "" + i + "_" + MRPSumary.getM_Product_ID();
				MUNSMRPWeekly MRPWeeklySumary = m_MRPWeeklySumary.get(keyMRPSumary);
				if (null != MRPWeeklySumary)
				{
					MRPWeeklySumary.setUNS_MRP_ID(MRPSumary.getUNS_MRP_ID());
					MRPWeeklySumary.setScaleAllQty();
					if (!MRPWeeklySumary.save())
						return false;
				}
			}
		}
		return true;
	}
}
*/