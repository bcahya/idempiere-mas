/**
 * 
 */
package com.uns.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.compiere.model.MProduct;
import org.compiere.model.MProductBOM;


/**
 * @author menjangan
 *
 */
public class MUNSForecastAllocation {

	private Hashtable<Integer, MUNSResource> m_ResourceChilds = new Hashtable<Integer, MUNSResource>();
	private Hashtable<Integer, MUNSForecastInput> m_AllocationInput = new Hashtable<Integer, MUNSForecastInput>();
	private Hashtable<Integer, MUNSProductAllocation> m_AllocationOutput = new Hashtable<Integer, MUNSProductAllocation>();
	private MUNSResource m_Resource = null;
	private MUNSForecastHeader m_ForecastHeader = null;

	public MUNSForecastAllocation(MUNSForecastHeader forecastHeader)
	{
		this.m_Resource = (MUNSResource) forecastHeader.getUNS_Resource();
		this.m_ForecastHeader = forecastHeader;
	}
	/**
	 * 
	 * @param resource
	 */
	public void setResource(MUNSResource resource)
	{
		m_Resource = resource;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSResource getResource()
	{
		return  m_Resource;
	}
	
	/**
	 * 
	 * @param forecastHeader
	 */
	public void setForecastHeader(MUNSForecastHeader forecastHeader)
	{
		m_ForecastHeader = forecastHeader;
	}
	
	/**
	 * 
	 * @return
	 */
	public MUNSForecastHeader getForecastHeader()
	{
		return m_ForecastHeader;
	}
	
	
	/**
	 * 
	 * @param resource
	 */
	public String initInOutAllocations()
	{	
		// First check if it was already initialized.
		MUNSForecastInput[] inputAllocations = m_ForecastHeader.getInputAllocations();
		if (inputAllocations != null && inputAllocations.length > 0)
			return "Nothing to do. It's been initialized.";
		
		initResourceChilds();
		initProductAllocations();
		initInput();
		//initQtyAllocation();
		saveForecastAllocation();
		
		return "Allocations initialization completed.";
	}
	
	
	private void saveForecastAllocation()
	{
		for (MUNSForecastInput input : m_AllocationInput.values())
		{
			try
			{
				input.save();
			}catch(Exception ex)
			{
				throw new IllegalArgumentException(ex.getMessage().toString());
			}
		}

		for (MUNSProductAllocation output : m_AllocationOutput.values())
		{
			try
			{
				output.save();
			}catch(Exception ex)
			{
				throw new IllegalArgumentException(ex.getMessage().toString());
			}
		}
	}
	
	
	/**
	 * 
	 */
	private void initProductAllocations()
	{
		for (MUNSResourceInOut output : m_Resource.getOutputLines())
		{
			MUNSProductAllocation productAllocation = m_AllocationOutput.get(output.getM_Product_ID());
			if (null == productAllocation)
			{
				productAllocation = new MUNSProductAllocation(
						m_ForecastHeader.getCtx(), 0, m_ForecastHeader.get_TrxName());
				productAllocation.setUNS_Forecast_Header_ID(m_ForecastHeader.getUNS_Forecast_Header_ID());
				productAllocation.setAD_Org_ID(m_ForecastHeader.getAD_Org_ID());
				productAllocation.setM_Product_ID(output.getM_Product_ID());
				productAllocation.setC_UOM_ID(output.getM_Product().getC_UOM_ID());
				m_AllocationOutput.put(output.getM_Product_ID(), productAllocation);
			}
			
		}
	}
	
	/**
	 * 
	 */
	private void initInput()
	{
		//TODO sebenarnya ada kemungkinan pada sebuah resource terdapat child yang bahan baku 'Main Part' nya 
		//dari luar/pembelian. Semestinya algoritma di bawah ini men-trace seluruh resource's childs dan jika 
		//input child tersebut bukan merupakan output dari transisi child sebelumnya, maka input tsb berarti
		//merupakan input yang diperhitungkan dalam forecast.
		for (MUNSResource startNode : getStartNodes())
		{
			initInput(startNode.getStartNodes());
		}
	}
	
	
	/**
	 * 
	 * @param resources
	 */
	private void initInput(MUNSResource[] resources)
	{
		for (MUNSResource resource : resources)
		{
			//String resourceType = resource.getResourceType();
			if (!resource.isWorkStation())
			{
				initInput(resource.getStartNodes());
				continue;
			}
			if (resource.isParentStartNode())
			{
				MUNSResourceInOut[] outputs = resource.getOutputLines();
				for (MUNSResourceInOut output : outputs)
				{
					if (output.getOutputType().equals(X_UNS_Resource_InOut.OUTPUTTYPE_Multi))
					{
						MProductBOM[] boms = MProductBOM.getBOMLines((MProduct)output.getM_Product());
						for (MProductBOM bom : boms)
						{
							if (!bom.isActive()) continue;
							// cek untuk jaga2 jika konfigurasinya input ini bisa jadi adalah input yang ada di transisi juga
							MUNSForecastInput forecastInput = m_AllocationInput.get(bom.getM_ProductBOM().getM_Product_ID());
							if (null == forecastInput 
									&& bom.getM_PartType().getName().equalsIgnoreCase("Main Part"))
							{
								forecastInput = new MUNSForecastInput(
										m_ForecastHeader.getCtx(), 0, m_ForecastHeader.get_TrxName());
								forecastInput.setAD_Org_ID(m_ForecastHeader.getAD_Org_ID());
								forecastInput.setUNS_Forecast_Header_ID(m_ForecastHeader.getUNS_Forecast_Header_ID());
								forecastInput.setM_Product_ID(bom.getM_ProductBOM_ID());
								forecastInput.setC_UOM_ID(bom.getM_ProductBOM().getC_UOM_ID());
								
								m_AllocationInput.put(forecastInput.getM_Product_ID(), forecastInput);
							}
						}
						break;
					}
					else {
						MProductBOM[] boms = MProductBOM.getBOMLines((MProduct)output.getM_Product());
						for (MProductBOM bom : boms)
						{
							// cek untuk jaga2 jika konfigurasinya input ini bisa jadi adalah input yang ada di transisi juga
							MUNSForecastInput forecastInput = m_AllocationInput.get(bom.getM_ProductBOM().getM_Product_ID());
							String materialPartType = bom.getM_PartType().getName();
							if (null == materialPartType)
								continue;
							
							if (null == forecastInput && materialPartType.equalsIgnoreCase("Main Part"))
							{
								forecastInput = new MUNSForecastInput( 
										m_ForecastHeader.getCtx(), 0, m_ForecastHeader.get_TrxName());
								forecastInput.setAD_Org_ID(m_ForecastHeader.getAD_Org_ID());
								forecastInput.setUNS_Forecast_Header_ID(m_ForecastHeader.getUNS_Forecast_Header_ID());
								forecastInput.setM_Product_ID(output.getM_Product_ID());
								forecastInput.setC_UOM_ID(output.getM_Product().getC_UOM_ID());
								forecastInput.save();
								
								m_AllocationInput.put(forecastInput.getM_Product_ID(), forecastInput);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private MUNSResource[] getStartNodes()
	{
		MUNSResource[] nodes = null;
		List<MUNSResource> listResource = new ArrayList<MUNSResource>();
		for (MUNSResource resource : m_ResourceChilds.values())
		{
			if (resource.isParentStartNode())
				listResource.add(resource);
		}
		nodes = new MUNSResource[listResource.size()];
		listResource.toArray(nodes);
		return nodes;
	}
	
	/**
	 * 
	 */
	private void initResourceChilds()
	{
		for (MUNSResource child : m_Resource.getLines())
		{
			m_ResourceChilds.put(child.getUNS_Resource_ID(), child);
		}
	}

}
