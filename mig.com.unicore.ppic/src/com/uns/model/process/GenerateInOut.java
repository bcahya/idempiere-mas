package com.uns.model.process;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.uns.base.model.Query;
import com.uns.model.MUNSResource;
import com.uns.model.MUNSResourceInOut;
import com.uns.model.MUNSResourceTransition;
import com.uns.model.factory.UNSPPICModelFactory;

public class GenerateInOut extends SvrProcess {

	/** The Record */
	private int m_Record_ID = 0;
	private static final String INITIAL_OUTPUT = "OUTPUT";
	private static final String INITIAL_ACTUAL_CAPS = "ACTUAL_CAPS";
	private String m_action = null;
	private boolean m_isResetNodeState = false;
	private boolean m_isSynchronizeOutput = false;
	
	/** Hacking code, ngakalin biar bisa mengetahui jumlah setelah diupdate. */
	private Hashtable<Integer, MUNSResourceInOut> m_updatedInOut = new Hashtable<Integer, MUNSResourceInOut>(); 
	
	/** Record MUNSResource */
	private MUNSResource m_resource = null;
	

	/**
	 * Get Record
	 * 
	 * @return Record
	 */
	protected MUNSResource getRecord() {
		return new MUNSResource(getCtx(), m_Record_ID, get_TrxName());
	} // getResourceRecord

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("ProcessType"))
				m_action = para[i].getParameter().toString();
			
			else if (name.equals("ResetNodeState"))
				m_isResetNodeState = para[i].getParameterAsBoolean();
			else if (name.equals("IsSynchronize"))
				m_isSynchronizeOutput = para[i].getParameterAsBoolean();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_Record_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {

		m_resource = getRecord();
		
		if (m_isResetNodeState)
			MUNSResource.resetNodeStateRecursively(m_resource, get_TrxName());
		
		String msg = "Completed.";
		try {
			if (m_action.equals(INITIAL_OUTPUT))
			{
				deleteResourceOutputs(m_resource);
				generateResourceOutput(m_resource);
				//generateResourceOutput(m_resource);
			}
			else if (m_action.equals(INITIAL_ACTUAL_CAPS))
			{
				//generateTransitionalCapacities(m_resource);
				//generateResourceActualMaxCaps(m_resource);
				generateActualMaxCaps(m_resource);
			}
		}
		catch (AdempiereException ex) {
			msg = ex.getMessage();
		}
		
		return msg;
	}
	
	/**
	 * @param resource
	 */
	private void deleteResourceOutputs(MUNSResource resource)
	{
		MUNSResource[] childs = resource.getLines();
		
		if (childs != null && (childs.length > 0))
		{
			for (MUNSResource child : childs)
			{
				deleteResourceOutputs(child);
			}
		}
		if (!resource.isWorker() && !resource.isMachine())
		{
			MUNSResourceInOut[] childOutputs = resource.getOutputLines();
			
			if (childOutputs != null)
			{
				resource.deleteOutputs();
			}
		}
	}
	
	/**
	 * Recursively generate resource's outputs.
	 * @param resource
	 */
	private void generateResourceOutput(MUNSResource resource)
	{
		MUNSResource[] childs = resource.getLines();
		
		Hashtable<String, ResourceOutputTmp> rscOutputs = new Hashtable<String, ResourceOutputTmp>();
		Hashtable<String, BigDecimal> hTotalAllDaysProductionHours = new Hashtable<String, BigDecimal>();
		boolean isSlicedOutput = false;
		
		for (MUNSResource child : childs)
		{
			if (child.isWorker() || child.isMachine())
			{
				// Reset the capacities first.
				
				MUNSResourceInOut rio = 
						Query.get(getCtx(), 
								  UNSPPICModelFactory.getExtensionID(), 
								  MUNSResourceInOut.Table_Name, 
								  "UNS_Resource_ID=? AND InOutType='O' AND (OutputType=? OR (IsPrimary='Y' AND OutputType=?))",
								  get_TrxName())
							.setParameters(child.get_ID(), MUNSResourceInOut.OUTPUTTYPE_Single, MUNSResourceInOut.OUTPUTTYPE_Multi)
							.firstOnly();
				if (m_isSynchronizeOutput)
				{
					if (rio != null) {
						rio.setProcessInfo(getProcessInfo());
						if (rio.getOutputType().equals(MUNSResourceInOut.OUTPUTTYPE_Multi))
							MUNSResourceInOut.synchronizeMultiMaxCaps(rio);
						else
							MUNSResourceInOut.validateSingleMaxCaps(rio);
					}
					else
						MUNSResourceInOut.synchronizeOptionalMaxCaps(child, null);
				}
				
				MUNSResourceInOut[] childOutputs = child.getOutputLines(true);
				for (MUNSResourceInOut childOutput : childOutputs)
				{
					String rscOutputTmpKey = 
							childOutput.getOutputLinkProductID() + "-" + childOutput.getM_Product_ID();
					
					ResourceOutputTmp outputTmp = rscOutputs.get(rscOutputTmpKey);
					
					if ( outputTmp == null)
					{
						MUNSResourceInOut output = new MUNSResourceInOut(getCtx(), 0, get_TrxName());
						PO.copyValues(childOutput, output);
						output.setM_Locator_ID(childOutput.getM_Locator_ID());
						output.setUNS_OutputLink_ID(childOutput.getUNS_OutputLink_ID());
						output.setIsPrimary(childOutput.isPrimary());
						output.setOptimumCaps(childOutput.getOptimumCaps());
						output.setOptimumCapsMT(childOutput.getOptimumCapsMT());
						output.setMaxCapsMT(childOutput.getMaxCapsMT());
						output.setMaxCaps(childOutput.getMaxCaps());
						output.setActualMaxCaps(childOutput.getMaxCaps());
						output.setActualMaxCapsMT(childOutput.getMaxCapsMT());
						outputTmp = new ResourceOutputTmp(output);
						rscOutputs.put(rscOutputTmpKey, outputTmp);
					}
					else 
					{						
						if (outputTmp.m_Output.getMaxCapsMT().compareTo(childOutput.getMaxCapsMT()) > 0)
						{ // kapasitas maks. di outputTmp > kaps. max mesin maka gunakan kaps.max mesin.
							outputTmp.m_Output.setMaxCapsMT(childOutput.getMaxCapsMT());
							//konfersi MT Ke UOM
							outputTmp.m_Output.setMaxCaps(childOutput.getMaxCaps());
							outputTmp.m_Output.setActualMaxCapsMT(childOutput.getMaxCapsMT());
							outputTmp.m_Output.setActualMaxCaps(childOutput.getMaxCaps());
						}
						if (outputTmp.m_Output.getOptimumCapsMT().compareTo(childOutput.getOptimumCapsMT()) < 0) {
							outputTmp.m_Output.setOptimumCaps(outputTmp.m_Output.getOptimumCaps());
							outputTmp.m_Output.setOptimumCapsMT(outputTmp.m_Output.getOptimumCapsMT());
						}
					}
					outputTmp.m_Output.setAllDaysTotalProductionHours();
				}
			}
			//else if (child.isParentEndNode())
			else
			{
				generateResourceOutput(child);
				
				MUNSResourceInOut[] childOutputs = child.getOutputLines(true);

				for (MUNSResourceInOut childOutput : childOutputs)
				{
					// If the child is for reworking and the childOutput is the child's previous output,
					// do not count it's capacities as parent's caps.
					if (child.isRework() && child.isPreviousResourceOutput(childOutput.getM_Product_ID()))
						continue;
					
					for (int i=1; i<=7; i++)
					{
						BigDecimal totalDayProductionHours = hTotalAllDaysProductionHours.get(
								generateKeyProductionHours(childOutput.getM_Product_ID(), i));
						if (null == totalDayProductionHours)
							totalDayProductionHours = BigDecimal.ZERO;
						
						BigDecimal dayProductionHours = (BigDecimal)childOutput
								.get_Value("Day"+i+"ProductionHours");
						if (null == dayProductionHours)
							dayProductionHours = BigDecimal.ZERO;
						totalDayProductionHours = dayProductionHours.add(totalDayProductionHours);
						
						hTotalAllDaysProductionHours.put(
								generateKeyProductionHours(
										childOutput.getM_Product_ID(), i), totalDayProductionHours);
					}
					
					//if (isOutputSetAsTransitionOutIn(child, childOutput.getM_Product_ID()))
					if (!child.isConsideredAsParentOutput(childOutput.getM_Product_ID()))
						continue; // Jika output tidak ada transisi-nya, itu artinya mrp output dari parent 
								  // meskipun child bukanlah parent's end-node.
					
					String rscOutputTmpKey = 
							childOutput.getOutputLinkProductID() + "-" + childOutput.getM_Product_ID();
					
					ResourceOutputTmp outputTmp = rscOutputs.get(rscOutputTmpKey);
					
					if ( outputTmp == null)
					{
						MUNSResourceInOut output = new MUNSResourceInOut(getCtx(), 0, get_TrxName());
						PO.copyValues(childOutput, output);
						output.setM_Locator_ID(childOutput.getM_Locator_ID());
						output.setUNS_OutputLink_ID(childOutput.getUNS_OutputLink_ID());
						output.setIsPrimary(childOutput.isPrimary());
						output.setOptimumCaps(childOutput.getOptimumCaps());
						output.setMaxCaps(childOutput.getMaxCaps());
						output.setMaxCapsMT(childOutput.getMaxCapsMT());
						//output.setActualMaxCaps(childOutput.getActualMaxCaps());
						//output.setActualMaxCapsMT(childOutput.getActualMaxCapsMT());
						outputTmp = new ResourceOutputTmp(output);
						rscOutputs.put(rscOutputTmpKey, outputTmp);
					}
					else 
					{
						outputTmp.m_Output.setMaxCapsMT(outputTmp.m_Output.getMaxCapsMT().add(childOutput.getMaxCapsMT()));
						outputTmp.m_Output.setMaxCaps(outputTmp.m_Output.getMaxCaps().add(childOutput.getMaxCaps()));
						outputTmp.m_Output.setOptimumCaps(outputTmp.m_Output.getOptimumCaps().add(childOutput.getOptimumCaps()));
						outputTmp.m_Output.setOptimumCapsMT(outputTmp.m_Output.getOptimumCapsMT().add(childOutput.getOptimumCapsMT()));
						outputTmp.m_Output.setActualMaxCaps(outputTmp.m_Output.getActualMaxCaps().add(childOutput.getActualMaxCaps()));
						outputTmp.m_Output.setActualMaxCapsMT(outputTmp.m_Output.getActualMaxCapsMT().add(childOutput.getActualMaxCapsMT()));
					}
				}
			}
			
		}
		if (resource.isWorkStation() && isSlicedOutput)
		{
			Iterator<ResourceOutputTmp> outputList = rscOutputs.values().iterator();
			while (outputList.hasNext())
			{
				ResourceOutputTmp outputTmp = outputList.next();
				if (outputTmp.m_numberOfOutput == 1)
				{
					rscOutputs.remove(outputTmp.m_Output.getM_Product_ID());
				}
			}
		}
		if (rscOutputs.size() > 0)
		{
			Hashtable<Integer, Integer> primaryProductInOutMap = new Hashtable<>();
			
			Iterator<ResourceOutputTmp> outputs = rscOutputs.values().iterator();
			while (outputs.hasNext())
			{
				ResourceOutputTmp outputTmp = outputs.next();
				outputTmp.m_Output.setIsNeedToSyncOutput(m_isSynchronizeOutput);
				outputTmp.m_Output.setAD_Org_ID(resource.getAD_Org_ID());
				outputTmp.m_Output.setUNS_Resource_ID(resource.getUNS_Resource_ID());
				if (!resource.isWorkStation())
				{
					for (int i=1; i<=7; i++)
					{
						BigDecimal totalDayProductionHours = hTotalAllDaysProductionHours.get(
								generateKeyProductionHours(outputTmp.m_Output.getM_Product_ID(), i));
						if (null != totalDayProductionHours)
							outputTmp.m_Output.set_ValueOfColumn(
									"Day"+i+"ProductionHours", totalDayProductionHours);
					}
				}
				// Save the primary output first, to make sure all non-primary output can be linked to it's primary.
				if (outputTmp.m_Output.isPrimary()) 
				{
					outputTmp.m_Output.saveEx();
					
					primaryProductInOutMap.put(
							outputTmp.m_Output.getM_Product_ID(), outputTmp.m_Output.get_ID());
				}
			}
			
			// Now save the non-primary output.
			Enumeration<String> outputTmpKeys = rscOutputs.keys();
			
			while(outputTmpKeys.hasMoreElements()) 
			{
				String key = outputTmpKeys.nextElement();
				
				if (key.charAt(0) == '-')
					continue;
				
				int primaryProductID = Integer.valueOf(key.substring(0, key.indexOf("-")));
				
				Integer outputLink_ID = primaryProductInOutMap.get(primaryProductID);
				if(null == outputLink_ID)
					continue;
				
				MUNSResourceInOut nonPrimaryRIO = rscOutputs.get(key).m_Output;
				nonPrimaryRIO.setUNS_OutputLink_ID(outputLink_ID);
				nonPrimaryRIO.saveEx();
			}
		}
	}
	
	/**
	 * Recursively generate resource's outputs.
	 * @param resource
	 */
	private void generateActualMaxCaps(MUNSResource resource)
	{
		MUNSResource[] childs = resource.getLines(false);
		
		for (MUNSResource child : childs)
		{
			if (child.isWorker() || child.isMachine())
			{
				// DO nothing.
			}
			else
			{
				generateActualMaxCaps(child);
			}
		}
		if (resource.isWorkStation())
			return;
		// Reset to zero value to all non-parent start node of child's output.
		for (MUNSResource child : resource.getLines(false))
		{
			if (child.isParentStartNode())
				continue;
			for (MUNSResourceInOut output : child.getOutputLines(false))
			{
				output.setActualMaxCaps(BigDecimal.ZERO);
				output.setActualMaxCapsMT(BigDecimal.ZERO);
				MUNSResourceInOut outputs = m_updatedInOut.get(output.getUNS_Resource_InOut_ID());
				if (null == outputs)
				{
					m_updatedInOut.put(output.getUNS_Resource_InOut_ID(), output);
				}
				//output.save();
			}
		}
		//Init all childs actual max caps.
		for (MUNSResource parentStartNode : resource.getStartNodes(false))
		{
			initActualMaxCaps(parentStartNode);
		}
		//Reset all this resource actual max caps to ZERO.
		for (MUNSResourceInOut output : resource.getOutputLines(false))
		{
			output.setActualMaxCaps(BigDecimal.ZERO);
			output.setActualMaxCapsMT(BigDecimal.ZERO);
			//output.save();
			MUNSResourceInOut outputs = m_updatedInOut.get(output.getUNS_Resource_InOut_ID());
			if (null == outputs)
			{
				m_updatedInOut.put(output.getUNS_Resource_InOut_ID(), output);
			}
		}
		//Trace through all of this resource's child. And then get all the child's output which are 
		//set as parent Output and then add all the same actual max caps to the parent output.
		for (MUNSResource child : resource.getLines(false))
		{
			for (MUNSResourceInOut childOutput : child.getOutputLines(false))
			{
				MUNSResourceInOut updatedChildOutput = 
						m_updatedInOut.get(childOutput.getUNS_Resource_InOut_ID());
				if (null == updatedChildOutput)
					updatedChildOutput = childOutput;
					//throw new AdempiereException ("It should not null <updatedChildOutput>.");
				if (child.isConsideredAsParentOutput(childOutput.getM_Product_ID()))
				{
					for (MUNSResourceInOut parentOutput : resource.getOutputLines(false))
					{	
						//MUNSResourceInOut inoutTmp = m_updatedInOut.get(parentOutput.getUNS_Resource_InOut_ID());
						if (parentOutput.getM_Product_ID() == childOutput.getM_Product_ID())
						{
							//MProduct productOutput = (MProduct)parentOutput.getM_Product();
							//BigDecimal childActualMaxCaps = childOutput.getActualMaxCaps();
							
							MUNSResourceInOut updatedParentOutput = m_updatedInOut.get(parentOutput.getUNS_Resource_InOut_ID());
							if (null == updatedParentOutput)
								throw new AdempiereException ("It should not null <updatedParentOutput>.");
							
							updatedParentOutput.setActualMaxCaps(updatedParentOutput.getActualMaxCaps().add(updatedChildOutput.getActualMaxCaps()));
							updatedParentOutput.setActualMaxCapsMT(updatedParentOutput.getActualMaxCapsMT().add(updatedChildOutput.getActualMaxCapsMT()));
							//parentOutput.save();
						}
					}
				}
			}
		}
		// Jika sudah berada pada resource m_resource, maka simpan seluruh m_updatedInOut.
		if (resource.getUNS_Resource_ID() == m_resource.getUNS_Resource_ID())
		{
			for (MUNSResourceInOut updatedOutput : m_updatedInOut.values()) {
				//MUNSResource rsc = (MUNSResource) updatedOutput.getUNS_Resource();
				//BigDecimal actualMaxCaps = updatedOutput.getActualMaxCaps();
				if (!updatedOutput.save())
					throw new AdempiereException ("Failed when updating and saving updated output.");
			}
		}
	}
		
	/**
	 * Recursively generate resource's outputs.
	 * @param resource
	 */
	void generateActualMaxCaps_Old(MUNSResource resource)
	{
		MUNSResource[] childs = resource.getLines(true);
		
		for (MUNSResource child : childs)
		{
			if (child.isWorker() || child.isMachine())
			{
				//Do nothing.
			}
			else
			{
				generateActualMaxCaps(child);
			}
		}
		if (resource.isWorkStation())
			return;
		for (MUNSResource child : resource.getLines(true))
		{
			if (child.isParentStartNode())
				continue;
			for (MUNSResourceInOut output : child.getOutputLines(true))
			{
				output.setActualMaxCaps(BigDecimal.ZERO);
				output.setActualMaxCapsMT(BigDecimal.ZERO);
				MUNSResourceInOut outputs = m_updatedInOut.get(output.getUNS_Resource_InOut_ID());
				if (null == outputs)
				{
					m_updatedInOut.put(output.getUNS_Resource_InOut_ID(), output);
				}
				output.save();
			}
		}
		//Init all childs actual max caps.
		for (MUNSResource parentStartNode : resource.getStartNodes(true))
		{
			initActualMaxCaps(parentStartNode);
		}
		//Reset all this resource actual max caps to ZERO.
		for (MUNSResourceInOut output : resource.getOutputLines(true))
		{
			output.setActualMaxCaps(BigDecimal.ZERO);
			output.setActualMaxCapsMT(BigDecimal.ZERO);
			output.save();
		}
		//Trace through all of this resource's child. And then get all the child's output which are set as
		//parent Output and then add all the same actual max caps to the parent output.
		for (MUNSResource child : resource.getLines(true))
		{
			for (MUNSResourceInOut childOutput : child.getOutputLines(true))
			{
				if (child.isConsideredAsParentOutput(childOutput.getM_Product_ID()))
				{
					for (MUNSResourceInOut parentOutput : resource.getOutputLines(true))
					{	
						//MUNSResourceInOut inoutTmp = m_updatedInOut.get(parentOutput.getUNS_Resource_InOut_ID());
						if (parentOutput.getM_Product_ID() == childOutput.getM_Product_ID())
						{
							//MProduct productOutput = (MProduct)parentOutput.getM_Product();
							//BigDecimal childActualMaxCaps = childOutput.getActualMaxCaps();
							parentOutput.setActualMaxCaps(parentOutput.getActualMaxCaps().add(childOutput.getActualMaxCaps()));
							parentOutput.setActualMaxCapsMT(parentOutput.getActualMaxCapsMT().add(childOutput.getActualMaxCapsMT()));
							parentOutput.save();
						}
					}
				}
			}
		}
	}
		
	/**
	 * 
	 * @param resource
	 */
	private void initActualMaxCaps(MUNSResource resource)
	{
		MUNSResourceTransition[] transitionList = resource.getTransitionLines(true);
		for (MUNSResourceTransition transition : transitionList)
		{
			MUNSResource nextRsc = (MUNSResource) transition.getNextResource();
			
			if (nextRsc.isRework()) {
				//initActualMaxCaps(nextRsc);
				continue;
			}
			MUNSResourceInOut[] transitionIOList = transition.getLines(true);
			for (MUNSResourceInOut transitionIO : transitionIOList)
			{
				MUNSResourceInOut[] outputNextRscList = (transition.isDirectBOM())?
						nextRsc.getOutputLinesForInputOf(transitionIO.getM_Product_ID()): 
						nextRsc.getOutputLines(false);
				for (MUNSResourceInOut outputNextRsc : outputNextRscList)
				{
					BigDecimal actualMaxCaps = BigDecimal.ZERO;
					BigDecimal actualMaxCapsMT = BigDecimal.ZERO;
			
					// Ambil OutIn yg merupakan Output parameter resource (previous resource) utk
					// mendapatkan jumlah Actual Output utk dijadikan input.
					MUNSResourceInOut prevOutputAsInput = resource.getResourceOutput(
							transitionIO.getM_Product_ID());
					MProduct prevProduct = (MProduct)transitionIO.getM_Product();
					MProduct nextProduct = (MProduct)outputNextRsc.getM_Product();
					BigDecimal bomQty = MUNSResourceInOut.getBOMQty(
							nextProduct.getM_Product_ID(), prevProduct.getM_Product_ID(), get_TrxName());
					if (bomQty.signum()<=0 || prevOutputAsInput == null)
						continue;
					BigDecimal prevActualMaxCaps = prevOutputAsInput.getActualMaxCaps();
					actualMaxCaps = prevActualMaxCaps.divide(bomQty, 4, BigDecimal.ROUND_HALF_UP);
						
					actualMaxCapsMT = actualMaxCaps.multiply(
							outputNextRsc.getM_Product().getWeight()
							.multiply(new BigDecimal(0.001))).setScale(3, BigDecimal.ROUND_HALF_UP);
					//BigDecimal nextOutputActualMaxCaps = outputNextRsc.getActualMaxCaps();
					MUNSResourceInOut updatedInOut = m_updatedInOut.get(outputNextRsc.getUNS_Resource_InOut_ID());
					if (updatedInOut == null)
						throw new AdempiereException("Seharusnya gak error.");
					BigDecimal nextOutputActualMaxCaps = updatedInOut.getActualMaxCaps();
						
					if (nextOutputActualMaxCaps.add(actualMaxCaps)
							.compareTo(outputNextRsc.getMaxCaps()) > 0)
					{
						//outputNextRsc.setActualMaxCapsMT(outputNextRsc.getMaxCapsMT());
						//outputNextRsc.setActualMaxCaps(outputNextRsc.getMaxCaps());
						updatedInOut.setActualMaxCapsMT(outputNextRsc.getMaxCapsMT());
						updatedInOut.setActualMaxCaps(outputNextRsc.getMaxCaps());
					}
					else
					{
						updatedInOut.setActualMaxCaps(updatedInOut.getActualMaxCaps().add(actualMaxCaps));
						updatedInOut.setActualMaxCapsMT(updatedInOut.getActualMaxCapsMT().add(actualMaxCapsMT));
					}
					//outputNextRsc.save();
				}
			}
			initActualMaxCaps((MUNSResource)transition.getNextResource());
		}
	}
	
	/**
	 * 
	 * @param wc
	 *
	private void generateTransitionalCapacities_Old(MUNSResource resource)
	{
		if (resource.isWorkStation())
			return;
		for (MUNSResource startNode : resource.getLines())
		{
			if (!startNode.isWorkCenter())
				generateTransitionalCapacities_Old(startNode);
			
			initActualMaxCaps(startNode);
		}
	}
*/

	/**
	 * 
	 * @param wc
	 *
	private void generateTransitionalCapacities(MUNSResource resource)
	{
		for (MUNSResource child : resource.getLines())
		{
			if (!child.isWorkStation())
				generateTransitionalCapacities(child);
			
			initActualMaxCaps(child);
		}
	}
*/

	/**
	 * Recursively generate resource's outputs.
	 * @param resource
	 *
	private void generateResourceActualMaxCaps(MUNSResource resource)
	{
		MUNSResource[] childs = resource.getLines();
		
		Hashtable<Integer, ResourceOutputTmp> rscOutputs = new Hashtable<Integer, ResourceOutputTmp>();
		
		for (MUNSResource child : childs)
		{
			if (!child.isWorker() && !child.isMachine())
			{
				generateResourceActualMaxCaps(child);
				
				MUNSResourceInOut[] childOutputs = child.getOutputLines();

				for (MUNSResourceInOut childOutput : childOutputs)
				{
					if (isOutputSetAsTransitionOutIn(child, childOutput.getM_Product_ID()))
						continue; // Jika output tidak ada transisi-nya, itu artinya mrp output dari parent 
								  // meskipun child bukanlah parent's end-node.
					
					ResourceOutputTmp outputTmp = rscOutputs.get(childOutput.getM_Product_ID());
					
					if ( outputTmp == null)
					{
						MUNSResourceInOut output = resource.getResourceOutput(childOutput.getM_Product_ID());
//						PO.copyValues(childOutput, output);
						output.setActualMaxCaps(childOutput.getActualMaxCaps());
						output.setActualMaxCapsMT(childOutput.getActualMaxCapsMT());
						outputTmp = new ResourceOutputTmp(output);
						rscOutputs.put(childOutput.getM_Product_ID(), outputTmp);
					}
					else 
					{
						outputTmp.m_Output.setActualMaxCapsMT(
								outputTmp.m_Output.getActualMaxCapsMT().add(childOutput.getActualMaxCapsMT()));
						outputTmp.m_Output.setActualMaxCaps(
								outputTmp.m_Output.getActualMaxCaps().add(childOutput.getActualMaxCaps()));
					}
				}
			}
			
		}
		if (rscOutputs.size() > 0)
		{
			Iterator<ResourceOutputTmp> outputs = rscOutputs.values().iterator();
			while (outputs.hasNext())
			{
				ResourceOutputTmp outputTmp = outputs.next();
				outputTmp.m_Output.save();
			}
		}
	}
	*/
	
	private String generateKeyProductionHours(int M_Product_ID, int day)
	{
		return ""+M_Product_ID+"_"+day;
	}
}

	
	
class ResourceOutputTmp 
{
	
	int m_numberOfOutput = 0;
	MUNSResourceInOut m_Output = null;
	
	public ResourceOutputTmp(MUNSResourceInOut output) 
	{
		m_numberOfOutput = 1;
		m_Output = output;
	}
	
	public String toString()
	{
		String productName = "";
		if (m_Output != null)
		{
			productName = m_Output.getM_Product().getName();
		}
		return productName;
	}
}