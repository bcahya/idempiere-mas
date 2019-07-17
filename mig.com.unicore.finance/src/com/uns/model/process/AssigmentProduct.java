/**
 * 
 */
package com.uns.model.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

//import com.uns.model.MUNSBCProductCategory;

/**
 * @author YAKA
 *
 */
public class AssigmentProduct extends SvrProcess implements ProcessCall {


	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
//	private MUNSBCProductCategory m_Record;
	private MProduct m_Product = null;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (MProduct.COLUMNNAME_M_Product_ID.equalsIgnoreCase(name))
				m_Product = new MProduct(getCtx(), para[i].getParameterAsInt(), get_TrxName());			
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
//		m_Record = new MUNSBCProductCategory(getCtx(), getRecord_ID(), get_TrxName());
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception {
		if (null == m_Product)
			throw new AdempiereException("Product not found");
		//TODO BC product Catrgory
		//m_Product.setUNS_BCProductCategory_ID(m_Record.get_ID());
		if (!m_Product.save())
			throw new AdempiereException("Can't save product");
			
		return null;
	}

}
