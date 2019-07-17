package com.uns.model.process;

import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MProduct;
import org.compiere.model.MProductBOM;
import org.compiere.model.PO;
//import org.compiere.model.MSysConfig;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;


/**
 * 
 * Process to create production lines based on the plans
 * defined for a particular production header
 * @author Paul Bowden
 *
 */
public class CopyProductBOM extends SvrProcess 
{
	private Properties 	m_Ctx;
	private String 		m_TrxName;
	
	private boolean 	m_isDeleteOld = false;
	private int			m_productIDToCopyFrom;
	private MProduct 	m_theProduct = null;
	private String 		m_BOMTypeToCopy = null;
	private String 		m_NewBOMType = null;
	
	public CopyProductBOM() {
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
			if ("DeleteOld".equals(name))
				m_isDeleteOld = para[i].getParameterAsBoolean();
			else if ("M_Product_ID".equals(name))
				m_productIDToCopyFrom = para[i].getParameterAsInt();
			else if ("BOMProductType".equals(name)) 
				m_BOMTypeToCopy = para[i].getParameterAsString();
			else if ("BOMType".equals(name)) 
				m_NewBOMType = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		
		m_Ctx = getCtx();
		m_TrxName = get_TrxName();
		
		m_theProduct = MProduct.get(getCtx(), getRecord_ID());
	}	//prepare

	@Override
	protected String doIt() throws Exception 
	{
		if (m_isDeleteOld)
		{
			StringBuffer sql = 
					new StringBuffer("DELETE FROM M_Product_BOM WHERE M_Product_ID=")
					.append(m_theProduct.get_ID());
			
			if (m_NewBOMType != null)
				sql.append(" AND BOMType=").append("'" + m_NewBOMType + "'");
			else if (m_BOMTypeToCopy != null)
				sql.append(" AND BOMType=").append("'" + m_BOMTypeToCopy + "'");
			
			DB.executeUpdateEx(sql.toString(), m_TrxName);
		}
		
		MProductBOM[] bomToCopies = MProductBOM.getBOMLines(m_Ctx, m_productIDToCopyFrom, m_BOMTypeToCopy, m_TrxName);
		
		int theProductID = m_theProduct.get_ID();
		
		int maxLineNo = 
				DB.getSQLValue(m_TrxName, "SELECT MAX(Line) FROM M_Product_BOM WHERE M_Product_ID=?", theProductID);
		
		for (MProductBOM bom : bomToCopies)
		{
			MProductBOM newBOM = new MProductBOM(m_Ctx, 0, m_TrxName);
			
			maxLineNo += 10;
			
			PO.copyValues(bom, newBOM);
			newBOM.setM_Product_ID(theProductID);
			newBOM.setLine(maxLineNo);
			newBOM.setBOMType(m_NewBOMType == null || m_NewBOMType.isEmpty()? bom.getBOMType() : m_NewBOMType);
			newBOM.setM_PartType_ID(bom.getM_PartType_ID());
			newBOM.setFormulaType(bom.getFormulaType());
			newBOM.setFormulaQty(bom.getFormulaQty());
			newBOM.setFormulaRounding(bom.getFormulaRounding());
			newBOM.setFormulaPrecision(bom.getFormulaPrecision());
			newBOM.setBOMQty(bom.getBOMQty());
			newBOM.saveEx();
		}
		
		return "Completed. Copied " + bomToCopies.length + " line(s) of BOM-Part.";
	}
	
} // CopyProductBOM
