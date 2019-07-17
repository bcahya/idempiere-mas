package com.uns.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.Env;

import com.uns.model.MUNSPSRealization;
import com.uns.model.MUNSProduction;
import com.uns.model.MUNSProductionDetail;
import com.uns.model.MUNSProductionSchedule;
import com.uns.model.MUNSProductionWorker;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSSrcProductDetail;


/**
 * 
 * Process to create production lines based on the plans
 * defined for a particular production header
 * @author Paul Bowden
 *
 */
public class ProductionProcess extends SvrProcess {

	private int p_M_Production_ID=0;
	private Timestamp p_MovementDate = null;
	private MUNSProduction m_production = null;
	private boolean mustBeStocked = false;  //not used
	private MUNSProductionSchedule m_ps;
	
	
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
		//	log.fine("prepare - " + para[i]);
			if (para[i].getParameter() == null)
				;
			else if (name.equals("MovementDate"))
				p_MovementDate = (Timestamp)para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		
		p_M_Production_ID = getRecord_ID();
		m_production = new MUNSProduction(getCtx(), p_M_Production_ID, get_TrxName());
		if (m_production.getUNS_ProductionSchedule_ID() != 0)
			m_ps = new MUNSProductionSchedule(getCtx(), m_production.getUNS_ProductionSchedule_ID(), get_TrxName());
		else
			m_ps = null;

	}	//prepare

	@Override
	protected String doIt() throws Exception {
		
		if ( m_production.get_ID() == 0 )
			throw new AdempiereUserError("Could not load production header");
		
		if ( m_production.getIsCreated().equals("N") )
			return "Not created";
		
		if ( m_production.isProcessed() )
			return "Already processed";
		
		return processLines();
			
	}

	protected String processLines() throws Exception {
		
		int processed = 0;
		BigDecimal sumOutput = Env.ZERO;
		BigDecimal sumQtyUsed = Env.ZERO; 
		m_production.setMovementDate(p_MovementDate);
		MUNSProductionDetail[] lines = m_production.getLines();
		
		//check quantity production detail
		for (MUNSProductionDetail line : lines)
		{
			if (line.isEndProduct())
			{
				sumOutput = sumOutput.add(line.getMovementQty());
				
				if(null != m_ps && m_ps.getSERLineProduct().getM_Product_ID()==m_production.getM_Product_ID())
					MUNSPSRealization.createRealization(m_ps, line, getCtx(), get_TrxName());
		
				MUNSPSRealization psr = MUNSPSRealization.get(getCtx(), get_TrxName(), line.getUNS_Production_Detail_ID());
				
				if(null != psr)
					psr.updateMPS();
				else
					log.log(Level.INFO, line.getUNS_Production_Detail_ID() + " not update MPS");
			} else
				sumQtyUsed = sumQtyUsed.add(line.getQtyUsed());
			
		}
		if (sumOutput.compareTo(Env.ZERO)<0 && sumQtyUsed.compareTo(Env.ZERO)< 0)
			throw new AdempiereSystemError("Please update production detail before complete.");
		
		StringBuilder errors = new StringBuilder();
		for ( int i = 0; i<lines.length; i++) {
			MUNSSrcProductDetail[] sourceList = null;
			if (!m_production.getPSType().equalsIgnoreCase(MUNSProduction.PSTYPE_MasterProductionSchedule) 
				&& !m_production.getPSType().equalsIgnoreCase(MUNSProduction.PSTYPE_Reprocess)
				&& !lines[i].isEndProduct())
				sourceList = m_ps.getLinesSrcProductDetail(false);
			
			errors.append( lines[i].createTransactions(m_production.getMovementDate(), mustBeStocked, sourceList) );
			
			lines[i].setProcessed( true );
			lines[i].saveEx(get_TrxName());
			if (m_production.getUNS_ProductionSchedule_ID()==0 && lines[i].isEndProduct())
				if(!MUNSProductionSchedule.createManufacturingOrder(getCtx(), get_TrxName(), lines[i]))
					throw new AdempiereSystemError("Error when create Manufacturing Order");;
			processed++;
		}
		
		if(m_production.isWorkerBase() 
				&& null != m_production.getWorkerResultType()) {
		//	throw new AdempiereUserError("Undefined Payment Method ");
			if (m_production.getWorkerResultType().equals(MUNSResource.PAYMENTMETHOD_Daily))
				errors.append(MUNSProductionWorker.createDailyReceivables(getCtx(), m_production, get_TrxName()));
			else if (m_production.getWorkerResultType().equals(MUNSResource.PAYMENTMETHOD_GroupResult))
				errors.append(MUNSProductionWorker.createPersonalResultReceivable(getCtx(), m_production, get_TrxName()));
			else if (m_production.getWorkerResultType().equals(MUNSResource.PAYMENTMETHOD_PersonalResult))
				errors.append(MUNSProductionWorker.createGroupResultReceivable(getCtx(), m_production, get_TrxName()));
			
			if(!m_production.createWorkerAbsence())
				errors.append("Failed to create absence of worker");
		}		
		
		//TODO QA STATUS
		/**
		 * @author YAKA
		 * create Product Release Certificate
		 */
//		if (MUNSProduction.getQAMonitoring(getCtx(), m_production)){
//			MUNSQAStatusPRC prc = new MUNSQAStatusPRC(getCtx(), 0, get_TrxName());
//			errors.append(prc.createQAMonitoring(m_production));
//		} else {
//			for (MUNSProductionDetail pd : m_production.getOutputLines()){
//				MStorageOnHand.setReleaseQty(pd.getM_Product_ID(), 
//											 pd.getM_Locator_ID(), 
//											 pd.getM_AttributeSetInstance_ID(), 
//											 pd.getMovementQty(), pd.get_TrxName());
//			}
//		}
	
		if ( errors.toString().compareTo("") != 0 ) {
			log.log(Level.SEVERE, errors.toString() );
			throw new AdempiereSystemError(errors.toString());
		}
		
		m_production.setIsComplete("Y");
		m_production.setProcessed(true);
		
		m_production.saveEx(get_TrxName());
		StringBuilder msgreturn = new StringBuilder().append(processed).append(" production lines were processed");
		addLog(msgreturn.toString());
		return msgreturn.toString();
	}

}
