package com.uns.model.process;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttributeSetInstance;
//import org.compiere.model.MSysConfig;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.MUNSProduction;
import com.uns.model.MUNSProductionDetail;
import com.uns.model.MUNSProductionWorker;
import com.uns.model.factory.UNSPPICModelFactory;


/**
 * 
 * Process to create production lines based on the plans
 * defined for a particular production header
 * @author Paul Bowden
 *
 */
public class ProductionCreate extends SvrProcess {

	private int p_UNS_Production_ID=0;
	private MUNSProduction m_production = null;
	private boolean mustBeStocked = true;  //not used
	private String bomTypeToUsed = null;
	private boolean recreate = false;
	
	private Properties m_Ctx;
	private String m_TrxName;
	
	public ProductionCreate() {
		m_Ctx = getCtx();
		m_TrxName = get_TrxName();
	}

	@Override
	protected void prepare() 
	{	
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if ("Recreate".equals(name))
				recreate = para[i].getParameterAsBoolean();
			else if ("MustBeStocked".equals(name))
				mustBeStocked = para[i].getParameterAsBoolean();
			else if ("BOMType".equals(name)) 
			{
				Object bomType = para[i].getParameter();
				bomTypeToUsed = bomType != null? (String) bomType : null;
			}
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		
		p_UNS_Production_ID = getRecord_ID();
		m_production = new MUNSProduction(getCtx(), p_UNS_Production_ID, get_TrxName());

	}	//prepare

	@Override
	protected String doIt() throws Exception 
	{
		if ( m_production.get_ID() == 0 )
			throw new AdempiereUserError("Could not load production header");

		if ( m_production.isProcessed() )
			return "Already processed";

		return createLines();

	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 * @throws AdempiereUserError
	 *
	private boolean costsOK(int M_Product_ID) throws AdempiereUserError {
		// Warning will not work if non-standard costing is used
		String sql = "SELECT ABS(((cc.currentcostprice-(SELECT SUM(c.currentcostprice*bom.bomqty)"
            + " FROM m_cost c"
            + " INNER JOIN m_product_bom bom ON (c.m_product_id=bom.m_productbom_id)"
	            + " INNER JOIN m_costelement ce ON (c.m_costelement_id = ce.m_costelement_id AND ce.costingmethod = 'S')"
            + " WHERE bom.m_product_id = pp.m_product_id)"
            + " )/cc.currentcostprice))"
            + " FROM m_product pp"
            + " INNER JOIN m_cost cc on (cc.m_product_id=pp.m_product_id)"
            + " INNER JOIN m_costelement ce ON (cc.m_costelement_id=ce.m_costelement_id)"
            + " WHERE cc.currentcostprice > 0 AND pp.M_Product_ID = ?"
            + " AND ce.costingmethod='S'";
		
		BigDecimal costPercentageDiff = DB.getSQLValueBD(get_TrxName(), sql, M_Product_ID);
		
		if (costPercentageDiff == null)
		{
			costPercentageDiff = Env.ZERO;
			String msg = "Could not retrieve costs";
			if (MSysConfig.getBooleanValue("MFG_ValidateCostsOnCreate", false, getAD_Client_ID())) {
				throw new AdempiereUserError(msg);
			} else {
				log.warning(msg);
			}
		}
		
		if ( (costPercentageDiff.compareTo(new BigDecimal("0.005")))< 0 )
			return true;
		
		return false;
	}
	*/

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	protected String createLines() throws Exception {
		
		int created = 0;
//		int product_id = 0;
		BigDecimal qtyPlan;
		
		if("MLT".equals(m_production.getOutputType())){
//			product_id = isBom(m_production.getPrimaryOutput().getM_Product_ID());
			qtyPlan = m_production.getPrimaryOutput().getQtyPlan();
		} else{
//			product_id = isBom(m_production.getM_Product_ID());
			qtyPlan = m_production.getProductionQty();
		}
		/**
		 * not uset again, used in Idempiere		 * 
		 * 
		if (!costsOK(product_id)) {
			String msg = "Excessive difference in standard costs";
			if (MSysConfig.getBooleanValue("MFG_ValidateCostsDifferenceOnCreate", false, getAD_Client_ID())) {
			throw new AdempiereUserError("Excessive difference in standard costs");
			} else {
				log.warning(msg);
			}
		}
		*/
		
		if (!recreate && "Y".equalsIgnoreCase(m_production.getIsCreated()))
			throw new AdempiereUserError("Production details already created. " +
					"Retry to generate it by selecting \"Recreate\" checkbox!");
		else if (recreate && "Y".equalsIgnoreCase(m_production.getIsCreated()))
		{
			m_production.deleteLines(get_TrxName());
			m_production.setIsCreated("N");
			m_production.saveEx();
		}
		
		if (null == qtyPlan	|| qtyPlan.compareTo(BigDecimal.ZERO)<0){
			throw new AdempiereUserError("Please set quantity to the primary output product");
		}
		
		Hashtable<Integer, MAttributeSetInstance> FGsAsi = null;
		
		if (m_production.getIsCreated().equalsIgnoreCase(MUNSProduction.ISCREATED_Yes))
			FGsAsi = MUNSProduction.getFGsAsi(getCtx(), get_TrxName(), m_production);

		created = m_production.createLines(mustBeStocked, bomTypeToUsed, FGsAsi);
		if ( created == 0 ) 
			return "Failed when creating production details.\n";
		
		m_production.setIsCreated("Y");
		m_production.save(get_TrxName());
		
		//regenerate worker and result
		if (m_production.isWorkerBase() && m_production.isPersonalResult())
		{
			m_production.deleteWorkers(get_TrxName());
			if (!MUNSProductionWorker.generateWorker(getCtx(), get_TrxName(), m_production))
				throw new AdempiereException("Resource has no workers defined yet.");
			for(MUNSProductionDetail pd : m_production.getLines())
			{
				pd.setPlannedQty(Env.ZERO);
				if (!pd.isEndProduct())
					pd.setQtyUsed(Env.ZERO);
				pd.setMovementQty(Env.ZERO);
				pd.save();
			}
		} 
		else if (m_production.isWorkerBase() && (m_production.isGroupResult() || m_production.isDailyResult()))
		{
			m_production.deleteWorkers(get_TrxName());
			if (!MUNSProductionWorker.generateWorker(getCtx(), get_TrxName(), m_production))
				throw new AdempiereException("Resource has no workers defined yet.");
		} 
		else if (m_production.isEndingStockBase())
		{	
			for(MUNSProductionDetail pd : m_production.getLines())
			{
				if (pd.isEndProduct())
					pd.setMovementQty(Env.ZERO);
				pd.save();
			}
		}
		
		StringBuilder msgreturn = new StringBuilder().append(created).append(" production details were created");
		if(m_production.isPersonalResult())
			msgreturn.append(", Please update production detail and worker result.");
		return msgreturn.toString();
	}
	
	/**
	 * 
	 * @param M_Product_ID
	 * @return
	 * @throws Exception
	 */
	protected int isBom(int M_Product_ID) throws Exception
	{
		String bom = DB.getSQLValueString(get_TrxName(), "SELECT isbom FROM M_Product WHERE M_Product_ID = ?", M_Product_ID);
		if ("N".compareTo(bom) == 0)
		{
			throw new AdempiereUserError ("Attempt to create product line for Non Bill Of Materials");
		}
		int materials = DB.getSQLValue(get_TrxName(), "SELECT count(M_Product_BOM_ID) FROM M_Product_BOM WHERE M_Product_ID = ?", M_Product_ID);
		if (materials == 0)
		{
			throw new AdempiereUserError ("Attempt to create product line for Bill Of Materials with no BOM Products");
		}
		return M_Product_ID;
	}

	public static MUNSProductionDetail[] createDetail(MUNSProduction production) {
		ProductionCreate pc = new ProductionCreate();
		
		//initial variable
		pc.setM_Ctx(production.getCtx());
		pc.setM_TrxName(production.get_TrxName());
		pc.p_UNS_Production_ID = production.get_ID();
		pc.m_production = production;
		pc.mustBeStocked = false;
		pc.bomTypeToUsed = null;
		pc.recreate = false;
		
		try {
			pc.doIt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new AdempiereException("Error when Create Production.");
		}
		
		List<MUNSProductionDetail> list = null;
		list = Query.get(pc.getCtx(), UNSPPICModelFactory.getExtensionID(), MUNSProductionDetail.Table_Name, 
						 "UNS_Production_ID=" + pc.p_UNS_Production_ID, pc.get_TrxName())
						 .setOrderBy(MUNSProductionDetail.COLUMNNAME_Line)
					.list();
		MUNSProductionDetail[] retValue = new MUNSProductionDetail[list.size()];
		retValue = list.toArray(retValue);
		
		return retValue;
	}
		

	public Properties getM_Ctx() {
		return m_Ctx;
	}

	public void setM_Ctx(Properties m_Ctx) {
		this.m_Ctx = m_Ctx;
	}

	public String getM_TrxName() {
		return m_TrxName;
	}

	public void setM_TrxName(String m_TrxName) {
		this.m_TrxName = m_TrxName;
	}

	@Override
	public Properties getCtx() {
		if (getM_Ctx() ==null)
			return super.getCtx();
		
		return getM_Ctx();
	}

	@Override
	public String get_TrxName() {
		if (getM_TrxName() ==null)
			return super.get_TrxName();
		
		return getM_TrxName();
	}
	
}
