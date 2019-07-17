/**
 * 
 */
package com.uns.model.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MAcctSchemaDefault;
import org.compiere.model.MBPartner;
import org.compiere.model.MCost;
import org.compiere.model.MCostElement;
import org.compiere.model.MProduct;
import org.compiere.model.ProductCost;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.acct.Doc;
import com.uns.base.acct.DocLine;
import com.uns.base.model.MCostDetail;
import com.uns.model.MUNSEmployee;
import com.uns.model.MUNSMOutCostingConfig;
import com.uns.model.MUNSPayrollConfiguration;
import com.uns.model.MUNSProduction;
import com.uns.model.MUNSProductionDetail;
import com.uns.model.MUNSProductionWorker;
import com.uns.model.MUNSProductionWorkerResult;
import com.uns.model.factory.UNSPPICModelFactory;

/**
 * @author YAKA
 * 
 */
public class Doc_UNSProduction extends Doc {

	private DocLine[] m_WorkerLines = null;
	private MUNSProduction m_Production = null;
	private int m_Reversal_ID = 0;

	/**
	 * @param as
	 * @param clazz
	 * @param rs
	 * @param defaultDocumentType
	 * @param trxName
	 */
	public Doc_UNSProduction(MAcctSchema as, Class<?> clazz, ResultSet rs,
			String defaultDocumentType, String trxName) {
		super(as, clazz, rs, defaultDocumentType, trxName);
	}

	/**
	 * Constructor
	 * 
	 * @param as
	 *            accounting schema
	 * @param rs
	 *            record
	 * @param trxName
	 *            trx
	 */
	public Doc_UNSProduction(MAcctSchema as, ResultSet rs, String trxName) {
		super(as, MUNSProduction.class, rs, DOCTYPE_UNSProduction, trxName);
	} // Doc_Production

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.acct.Doc#loadDocumentDetails()
	 */
	@Override
	protected String loadDocumentDetails() {
		setC_Currency_ID(NO_CURRENCY);
		m_Production = (MUNSProduction) getPO();
		m_Reversal_ID = m_Production.getReversal_ID();
		setDateDoc(m_Production.getMovementDate());
		setDateAcct(m_Production.getMovementDate());
		// Contained Objects
		p_lines = loadLines(m_Production);
		if (MUNSProduction.OUTPUTTYPE_Multi.equals(m_Production.getOutputType()) ||
				MUNSProduction.OUTPUTTYPE_MultiOptional.equals(m_Production.getOutputType())) 
		{
			MUNSProductionDetail pdLine = null;
			boolean isAllMOutCostConfDefined = true;
			
			for (DocLine line : p_lines) 
			{
				if (!line.isProductionOutput())
					continue;
				
				pdLine = (MUNSProductionDetail) line.getPO();
				
				if (!pdLine.isPrimary()) {
					MUNSMOutCostingConfig conf = MUNSMOutCostingConfig.get(
							getCtx(), line.getM_Product_ID(), getTrxName());
					if (conf == null) {
						isAllMOutCostConfDefined = false;
						break;
					}
				}
			}
			if (!isAllMOutCostConfDefined)
				throw new AdempiereUserError(
						"Cannot find Multi Output Costing Configuration for non-primary output of " + pdLine.getM_Product(),
						".\nYou need to define all the secondary-output's costing method.");
		}
		loadLinesWorker();
		log.fine("Lines=" + p_lines.length);
		return null;
	}

	private DocLine[] loadLines(MUNSProduction prod) 
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		// Production
		// -- ProductionDetail - the real level
		String sqlPL = "SELECT *, '' AS ProductType, 0 AS QtyAvailable FROM UNS_Production_Detail pd "
				+ "WHERE pd.UNS_Production_ID=? ORDER BY pd.Line";

		try {
			PreparedStatement pstmtPL = DB
					.prepareStatement(sqlPL, getTrxName());
			pstmtPL.setInt(1, get_ID());
			ResultSet rsPL = pstmtPL.executeQuery();
			
			while (rsPL.next()) 
			{
				MUNSProductionDetail line = new MUNSProductionDetail(getCtx(), rsPL, getTrxName());
			
				if (line.getMovementQty().signum() == 0) 
				{
					log.info("LineQty=0 - " + line);
					continue;
				}
				DocLine docLine = new DocLine(line, this);
				docLine.setReversalLine_ID(line.getReversalLine_ID());
				docLine.setQty(line.getMovementQty(), false);
				docLine.setDateDoc(prod.getMovementDate());

				//
				docLine.setAmount(BigDecimal.ZERO); // belum disetting
				// Identify finished BOM Product
				docLine.setIsProductionOutput(line.isEndProduct());
				//
				log.fine(docLine.toString());
				list.add(docLine);
			}
			rsPL.close();
			pstmtPL.close();
		} catch (Exception ee) {
			log.log(Level.SEVERE, sqlPL, ee);
		}

		DocLine[] dl = new DocLine[list.size()];
		list.toArray(dl);
		return dl;
	}

	/**
	 * 
	 * @param production
	 */
	private void loadLinesWorker() 
	{
		Hashtable<String, DocLine> mapOfDocLine = new Hashtable<String, DocLine>();
		
		for (MUNSProductionWorker worker : m_Production.getWorkers()) 
		{
			MUNSEmployee employee = new MUNSEmployee(getCtx(), worker.getLabor_ID(), getTrxName());
			MBPartner sod = (MBPartner) employee.getC_BPartner();
			MBPartner vendor = (MBPartner) employee.getVendor();//worker.getC_BPartner();
			
			for (MUNSProductionWorkerResult wr : worker.getResults()) 
			{
				if (wr.getReceivableAmt().signum() == 0)
					continue;
				
				DocLine docLine = mapOfDocLine.get(generateKey(
						wr.getM_Product_ID(), sod.getC_BPartner_ID()));
				if (null == docLine) 
				{
					docLine = new DocLine(sod, this);
					
					if (worker.getReversalLine_ID() > 0)
						docLine.setReversalLine_ID(sod.get_ID());
					docLine.setM_Product_ID(wr.getM_Product_ID());
					docLine.setC_BPartner_ID(sod.getC_BPartner_ID());
					mapOfDocLine.put(
							generateKey(wr.getM_Product_ID(),
									sod.getC_BPartner_ID()), docLine);
					docLine.setQty(BigDecimal.ZERO, false);
					docLine.setAmount(BigDecimal.ZERO);
				}
				docLine.setQty(docLine.getQty().add(wr.getProductionQty()), false);
				docLine.setAmount(docLine.getAmtSource().add(worker.getReceivableAmt()));

				if (wr.getInsentifPemborong().signum() == 0)
					continue;
				
				DocLine docLinePemborong = mapOfDocLine.get(
						generateKey(wr.getM_Product_ID(), vendor.get_ID()));
				
				if (null == docLinePemborong) 
				{
					docLinePemborong = new DocLine(vendor, this);
					
					if (worker.getReversalLine_ID() > 0)
						docLinePemborong.setReversalLine_ID(vendor.get_ID());
					docLinePemborong.setM_Product_ID(wr.getM_Product_ID());
					docLinePemborong.setC_BPartner_ID(vendor.get_ID());
					mapOfDocLine.put(
							generateKey(wr.getM_Product_ID(), vendor.get_ID()), docLinePemborong);
					docLinePemborong.setAmount(BigDecimal.ZERO);
				}
				docLinePemborong.setAmount(
						docLinePemborong.getAmtSource().add(worker.getInsentifPemborong()));
				docLinePemborong.setQty(BigDecimal.ZERO, false);
			}
		}

		List<DocLine> docLines = new ArrayList<DocLine>();
		
		for (DocLine docLine : mapOfDocLine.values()) 
		{
			if (docLine.getAmtSource().compareTo(BigDecimal.ZERO) > 0) {
				docLines.add(docLine);
			}
		}
		m_WorkerLines = new DocLine[docLines.size()];
		docLines.toArray(m_WorkerLines);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.acct.Doc#getBalance()
	 */
	@Override
	public BigDecimal getBalance() {
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.acct.Doc#createFacts(org.compiere.model.MAcctSchema)
	 */
	@Override
	public ArrayList<Fact> createFacts(MAcctSchema as) 
	{
		// create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID(as.getC_Currency_ID());
		// Line pointer
		FactLine fl = null;
		
		for (int i = 0; i < p_lines.length; i++) 
		{
			DocLine line = p_lines[i];
			// Calculate Costs
			BigDecimal costs = null;
			BigDecimal[] retCost = new BigDecimal[2];
			MProduct product = line.getProduct();

			//int variedHeader = 0;
			BigDecimal variance = BigDecimal.ZERO;
			boolean zeroCostOK = false;
			
			int C_Activity_ID = ((MUNSProductionDetail) line.getPO()).getActivityOfThisDetail();
			
			if (!((MUNSProductionDetail) line.getPO()).isEndProduct())
				zeroCostOK = true;
			
			if (isReversal(line)) {
				costs = Env.ZERO;
			}
			else 
			{
				costs = line.getProductCosts(as, line.getAD_Org_ID(), zeroCostOK, "UNS_Production_Detail_ID=?");
			
				if ((costs == null || costs.signum() == 0) && line.isProductionOutput())// && line.getUNS_Production_ID() != variedHeader) 
				{
					String costingMethod = product.getCostingMethod(as);
					String outputType = m_Production.getOutputType();
					MUNSProductionDetail currOutputPD = (MUNSProductionDetail) line.getPO();
					
					if (MAcctSchema.COSTINGMETHOD_StandardCosting.equals(costingMethod)
						&& !"MLT".equals(outputType)
						&& (costs == null || costs.signum() == 0)) 
					{
						p_Error = "No Standard Costs for (Single) Output of " + line.getLine() + " - " + line;
						return null;
					}
					
					// Get BOM Cost - Sum of individual input lines
					BigDecimal bomCost = Env.ZERO;
					for (int ii = 0; ii < p_lines.length; ii++) 
					{
						DocLine line0 = p_lines[ii];
						if (line0.getUNS_Production_ID() != line.getUNS_Production_ID()
								|| line0.isProductionOutput())
							continue;
						
						MUNSProductionDetail currInputPD = (MUNSProductionDetail) line0.getPO();
						
						boolean isInputOfCurrOutput = false;
						if (currOutputPD.isPrimary()) {
							if (currInputPD.getAllPrimaryOutputMap().get(currOutputPD.get_ID()) != null)
								isInputOfCurrOutput = true;
						}else {
							if (currInputPD.getAllNonPrimaryOutputMap().get(currOutputPD.get_ID()) != null)
								isInputOfCurrOutput = true;
						}
						
						if (isInputOfCurrOutput) 
						{
							BigDecimal currOutputPortion = calculateFormulaPortion(line, line0);
							
							BigDecimal lineCost = 
									line0.getProductCosts(
											as, line.getAD_Org_ID(), false, "UNS_Production_Detail_ID=?")
									.setScale(2, BigDecimal.ROUND_HALF_UP);
							bomCost = 
									bomCost.add(lineCost.multiply(currOutputPortion));
						}
					}
					
					if (MUNSProduction.OUTPUTTYPE_Multi.equals(m_Production.getOutputType()) ||
							MUNSProduction.OUTPUTTYPE_MultiOptional.equals(m_Production.getOutputType())) 
					{
						MUNSMOutCostingConfig multiOut = MUNSMOutCostingConfig.get(
								getCtx(), line.getM_Product_ID(), getTrxName());
					
						if (multiOut != null) 
						{
							retCost = multiOut.getCosting(as, line.getAD_Org_ID(),
									false, line, bomCost.negate());
							bomCost = retCost[1];
							costs = retCost[0];
						} else {
							costs = MUNSMOutCostingConfig.getNotSetCosting(
									getCtx(), getTrxName(), as, line.getAD_Org_ID(), 
									m_Production.isMultiOptional(), false, line, p_lines, bomCost.negate());
						}
					} 
					else {
						if (MAcctSchema.COSTINGMETHOD_StandardCosting.equals(costingMethod))
							variance = (costs.setScale(2, BigDecimal.ROUND_HALF_UP))
									.subtract(bomCost.negate());
						else
							costs = (bomCost.signum() < 0) ? bomCost.negate() : bomCost;
					}
	
					// only post variance if it's not zero
					if (variance.compareTo(BigDecimal.ZERO) != 0) 
					{
						// get variance account
						int validCombination = MAcctSchemaDefault.get(getCtx(), as.get_ID()).getP_RateVariance_Acct();
						MAccount base = MAccount.get(getCtx(), validCombination);
						MAccount account = MAccount.get(getCtx(),
								as.getAD_Client_ID(), as.getAD_Org_ID(),
								as.get_ID(), base.getAccount_ID(), 0, 0, 0, 0, 0,
								0, 0, 0, 0, 0, 0, 0, 0, 0, getTrxName());
						// post variance
						fl = fact.createLine(line, account, as.getC_Currency_ID(), variance.negate());
						if (fl == null) {
							p_Error = "Couldn't post variance " + line.getLine()
									+ " - " + line;
							return null;
						}
						fl.setQty(Env.ZERO);
						fl.setC_Activity_ID(C_Activity_ID);
					}
					// costs = bomCost.negate();
				} 
				else if (costs == null || costs.signum() == 0) 
				{
					MUNSMOutCostingConfig multiOut = MUNSMOutCostingConfig.get(
							getCtx(), line.getM_Product_ID(), getTrxName());
					
					if (multiOut == null && !isZeroCostPossible(as, line)) 
					{
						// jika bukan merupakan hasil dari production sebelumnya maka return null.
						p_Error = "No Costs for Line " + line.getLine() + " - "
								+ line + " Product Name:"
								+ line.getProduct().getName();
						return null;
					}
					costs = BigDecimal.ZERO;
				}
			}
			
			// Inventory DR CR
			fl = fact.createLine(
					line, line.getAccount(ProductCost.ACCTTYPE_P_Asset, as), as.getC_Currency_ID(), costs);
			
			String description = line.getDescription();
			if (description == null)
				description = "";
			if (line.isProductionOutput())
				description += "(*)";
			
			if (fl == null) {
				p_Error = "No Costs for Line " + line.getLine() + " - " + line;
				return null;
			}
			else {
				fl.setM_Locator_ID(line.getM_Locator_ID());
				fl.setQty(line.getQty());
				fl.setC_Activity_ID(C_Activity_ID);
				
				if (isReversal(line))
				{
					if (!fl.updateReverseLine (
							MUNSProduction.Table_ID, m_Reversal_ID, line.getReversalLine_ID(), Env.ONE))
					{
						p_Error = "Original Production not posted yet";
						return null;
					}
					costs = fl.getAcctBalance();
				}
			}
			// Cost Detail
			if (!MCostDetail.createProduction(as, line.getAD_Org_ID(),
					UNSPPICModelFactory.getExtensionID(),
					line.getM_Product_ID(),
					line.getM_AttributeSetInstance_ID(), line.get_ID(), 0,
					costs, line.getQty(), description, getTrxName())) {
				p_Error = "Failed to create cost detail record";
				return null;
			}
		}

		for (DocLine docLine : m_WorkerLines) 
		{
			MUNSProductionDetail pd = m_Production.getProductionDetailOf(docLine.getM_Product_ID());
			
			if (null == pd)
				continue;

			int C_Activity_ID = pd.getActivityOfThisDetail();
			
			// fact debit
			FactLine factDr = null;
			FactLine factCr = null;
			BigDecimal costs = null;
			
			if (isReversal(docLine))
			{
				costs = Env.ZERO;
			}
			else {
				MCostDetail costDetail = MCostDetail.get(getCtx(),
						"C_BPartner_ID =?", UNSPPICModelFactory.getExtensionID(),
						docLine.getC_BPartner_ID(),
						pd.getM_AttributeSetInstance_ID(), as.get_ID(),
						getTrxName());
	
				if (null != costDetail)
					costs = costDetail.getAmt();
	
				if (null == costs || costs.signum() == 0)
					costs = docLine.getAmtSource();
			}
			/*
			 * MAccount assetCr = MAccount.get(getCtx(), getAD_Client_ID(),
			 * getAD_Org_ID() , as.getC_AcctSchema_ID() , 1000217, 0,
			 * docLine.getM_Product_ID() , docLine.getC_BPartner_ID(), 0, 0, 0,
			 * 0, 0, 0, 0, 0, 0, 0, 0);
			 */
			MAccount workerExpense = MUNSPayrollConfiguration
					.getHutangUpahBuruhDirectAcct(getCtx(), getTrxName());
			MAccount assetDr = docLine.getAccount(ProductCost.ACCTTYPE_P_Asset, as);

			int C_Currency_ID = as.getC_Currency_ID();

			factDr = fact.createLine(docLine, assetDr, C_Currency_ID, costs, null);
			//
			if (factDr == null) {
				p_Error = "DR not created: " + docLine;
				log.log(Level.WARNING, p_Error);
				return null;
			}
			factDr.setAD_Org_ID(m_Production.getAD_Org_ID());
			factDr.setC_BPartner_ID(docLine.getC_BPartner_ID());
			factDr.setC_Activity_ID(C_Activity_ID);
			
			if (isReversal(docLine))
			{
				if (factDr.updateReverseLine(
						MUNSProduction.Table_ID, m_Reversal_ID, docLine.getReversalLine_ID(), Env.ONE))
				{
					p_Error = "Original Production not posted yet";
					return null;
				}
			}

			factCr = fact.createLine(docLine, workerExpense, C_Currency_ID, null, costs);

			if (factCr == null) {
				p_Error = "CR not created: " + docLine;
				log.log(Level.WARNING, p_Error);
				return null;
			}
			factCr.setAD_Org_ID(m_Production.getAD_Org_ID());
			factCr.setC_BPartner_ID(docLine.getC_BPartner_ID());
			factCr.setC_Activity_ID(C_Activity_ID);

			if (isReversal(docLine))
			{
				if (factCr.updateReverseLine(
						MUNSProduction.Table_ID, m_Reversal_ID, docLine.getReversalLine_ID(), Env.ONE))
				{
					p_Error = "Original Production not posted yet";
					return null;
				}
				costs = factCr.getAcctBalance();
			}

			/*
			 * MCostElement[] costElements =
			 * MCostElement.getNonCostingMethods(m_production); MCostElement
			 * costElement = null; for (int i=0; i < costElements.length;) {
			 * costElement = costElements[i]; break; }
			 */
			if (!MCostDetail.createProductionWorker(as, docLine.getAD_Org_ID(),
					UNSPPICModelFactory.getExtensionID(),
					docLine.getM_Product_ID(),
					pd.getM_AttributeSetInstance_ID(),
					m_Production.get_ID(), docLine.getC_BPartner_ID(), 0, costs,
					docLine.getQty(), docLine.getDescription(), getTrxName())) {
				p_Error = "Failed to create cost detail record";
				return null;
			}
		}
		//
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	} // createFact
	
	
	private String generateKey(int key1, int key2) {
		return "" + key1 + key2;
	}

	/**
	 * To check if the product is possible to zero cost.
	 * 
	 * @param line
	 * @return
	 */
	private boolean isZeroCostPossible(MAcctSchema as, DocLine line) 
	{
		MProduct product = (MProduct) line.getProduct();
		String CostingLevel = product.getCostingLevel(as);
		// Org Element
		int Org_ID = getAD_Org_ID();
		int M_ASI_ID = line.getM_AttributeSetInstance_ID();

		if (MAcctSchema.COSTINGLEVEL_Client.equals(CostingLevel)) {
			Org_ID = 0;
			M_ASI_ID = 0;
		} else if (MAcctSchema.COSTINGLEVEL_Organization.equals(CostingLevel))
			M_ASI_ID = 0;
		else if (MAcctSchema.COSTINGLEVEL_BatchLot.equals(CostingLevel))
			Org_ID = 0;

		MCostElement ce = MCostElement.getMaterialCostElement(getCtx(),
				product.getCostingMethod(as));

		MCost mcost = MCost.get(getCtx(), product.getAD_Client_ID(), Org_ID,
				product.get_ID(), as.getM_CostType_ID(), as.get_ID(),
				ce.get_ID(), M_ASI_ID, getTrxName());
		if (mcost == null)
			return false;
		if (mcost.getCurrentCostPrice().compareTo(BigDecimal.ZERO) == 0
				&& mcost.getCurrentQty().compareTo(BigDecimal.ZERO) > 0)
			return true;

		return false;
	}
	
	private boolean isReversal(DocLine line) {
		return m_Reversal_ID !=0 && line.getReversalLine_ID() != 0;
	}
	
	private boolean hasMoreOnePrimaryOutput = false;
	private boolean checked = false;
	
	private BigDecimal calculateFormulaPortion(DocLine outputLine, DocLine inputLine)
	{
		BigDecimal thePortion = Env.ONE;
		
		MUNSProductionDetail inputDetail  = (MUNSProductionDetail) inputLine.getPO();
		
		if (inputDetail.getPrimaryOutput_ID() > 0)
			return Env.ONE;
		
		if (m_Production.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Multi)
			||m_Production.getOutputType().equals(MUNSProduction.OUTPUTTYPE_Single))
			return thePortion;
		
		if (!checked)
		{
			String sql = "SELECT COUNT(*) FROM UNS_ProductionDetail WHERE UNS_Production_ID=?";
			
			if (m_Production.getOutputType().equals(MUNSProduction.OUTPUTTYPE_MultiOptional))
				sql += " AND IsPrimary='Y'";
			
			int countOutput = DB.getSQLValueEx(getTrxName(), sql, m_Production.get_ID());
			
			hasMoreOnePrimaryOutput = countOutput > 1 ? true :false;
		}
		
		if (!hasMoreOnePrimaryOutput)
			return thePortion;
		
		MUNSProductionDetail outputDetail = (MUNSProductionDetail) outputLine.getPO();
		
		Hashtable<Integer, MUNSProductionDetail> outputsOfTheInput = inputDetail.getAllPrimaryOutputMap();
		
		if (outputsOfTheInput.size() == 0)
			throw new AdempiereException(
					"Failed while calculating input proportion. Product input of "
					+ inputDetail.getM_Product().getValue() 
					+ " is not bom related to any of this production's output.");

		BigDecimal totalOutputOfInput = Env.ZERO;
		
		if (m_Production.isOptional())
		{
			for(MUNSProductionDetail output : outputsOfTheInput.values()) {
				totalOutputOfInput = totalOutputOfInput.add(output.getMovementQty());
			}
			thePortion = outputDetail.getMovementQty()
					.divide(totalOutputOfInput, 10, BigDecimal.ROUND_UNNECESSARY);
		}
		else  if (m_Production.isMultiOptional()) 
		{
			if (!outputDetail.isPrimary()) {
				
			}
			
			//Hashtable<Integer, MUNSProductionDetail> allPrimaryOutputs = outputDetail.getAllPrimaryOutputMap();
			//Hashtable<Integer, MUNSProductionDetail> outputsOfTheInput = inputDetail.getAllPrimaryOutputMap();

			
		}
		//BigDecimal currentOutLineBOMQty = 
		//		MProductBOM.
		
		
		return thePortion;
	}
}
