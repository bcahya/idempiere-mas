/**
 * 
 */
package com.uns.base.acct;

import org.compiere.acct.Doc;
import org.compiere.model.PO;


/**
 * @author YAKA
 *
 */
public class DocLine extends org.compiere.acct.DocLine {
	/** Production indicator	*/
	private boolean 			m_isProductionOutput = false;
	

	/**
	 *	Create Document Line
	 *	@param po line persistent object
	 *	@param doc header
	 */
	public DocLine (PO po, Doc doc)
	{
		super(po, doc);
	}
	
	public void setC_BPartner_ID(int C_BPartner_ID)
	{
		super.setC_BPartner_ID(C_BPartner_ID);
	}
	
	/**
	 *  Get Production Header
	 *  @return M_Production_ID
	 */
	public int getUNS_Production_ID()
	{
		int index = p_po.get_ColumnIndex("UNS_Production_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getUNS_Production_ID
	
	/**
	 *  Get Product Service (PO -> MUNSQALabTest)
	 *  @return ProductService_ID
	 */
	public int getUNS_QAMLab_Result_ID()
	{
		int index = p_po.get_ColumnIndex("UNS_QAMLab_Result_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getUNS_Production_ID
	
	/**
	 *  Get Material Service (PO -> MProductBOM)
	 *  @return M_Product_BOM_ID (ID)
	 */
	public int getM_Product_BOM_ID()
	{
		int index = p_po.get_ColumnIndex("M_Product_BOM_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	}   //  getUNS_Production_ID
	
	/**
	 *  Get UNS_QALabTest_ID
	 *  @return UNS_QALabTest_ID
	 */
	public int getUNS_QALabTest_ID()
	{
		int index = p_po.get_ColumnIndex("UNS_QALabTest_ID");
		if (index != -1)
		{
			Integer ii = (Integer)p_po.get_Value(index);
			if (ii != null)
				return ii.intValue();
		}
		return 0;
	} 
	
	public int getC_Charge_ID()
	{
		return super.getC_Charge_ID();
	}
	
	//TAMBAHAN
	/**
	 * 	Set Production BOM flag
	 *	@param isProductionOutput flag
	 */
	public void setIsProductionOutput(boolean isProductionOutput)
	{
		m_isProductionOutput = isProductionOutput;
	}	//	setProductionBOM
	
	/**
	 * 	Is this the production's output
	 *	@return true if it is an output, false if it is the production input.
	 */
	public boolean isProductionOutput()
	{
		return m_isProductionOutput;
	}	//	isProductionBOM
	
}
