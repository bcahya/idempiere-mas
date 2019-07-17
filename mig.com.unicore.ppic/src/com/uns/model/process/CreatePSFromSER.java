/**
 * 
 */
package com.uns.model.process;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.model.MStorageOnHand;
import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;

import com.uns.model.MUNSProductionSchedule;
import com.uns.model.MUNSSER;
import com.uns.model.MUNSSERLineProduct;

/**
 * @author YAKA
 *
 */
public class CreatePSFromSER extends SvrProcess implements ProcessCall {

	/**
	 * 
	 */
	int serProduct_ID = 0;
	int AD_Org_ID = 0;
	MUNSSERLineProduct m_serProduct = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if ("UNS_SERLineProduct_ID".equals(name))
				serProduct_ID = para[i].getParameterAsInt();
			else if ("AD_Org_ID".equals(name))
				AD_Org_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);		
		}
		
		m_serProduct = new MUNSSERLineProduct(getCtx(), serProduct_ID, get_TrxName());

	}	//prepare
	
	private BigDecimal getQtyOnHand()
	{
		BigDecimal qtyOnHand = BigDecimal.ZERO;
		MStorageOnHand[] storageOnHand = MStorageOnHand.getOfProduct(
				getCtx(), m_serProduct.getM_Product_ID(), get_TrxName());
		for (MStorageOnHand soh : storageOnHand)
		{
			qtyOnHand = qtyOnHand.add(soh.getQtyOnHand());
		}
		return qtyOnHand;
	}
	
	private BigDecimal getQtyMTOnHand(BigDecimal qtyOnHand)
	{
		MProduct product = MProduct.get(getCtx(), m_serProduct.getM_Product_ID());
		BigDecimal weight = product.getWeight();
		BigDecimal qtyMTOnHand = qtyOnHand.multiply(weight).multiply(new BigDecimal(0.001).
				multiply(getQtyOnHand()));
		return qtyMTOnHand;
	}
	

	@Override
	protected String doIt() throws Exception {

		if ( m_serProduct.get_ID() == 0 )
			throw new AdempiereUserError("Could not load SER Line Product");

		MUNSSER ser = m_serProduct.getParent().getParent();
		MUNSProductionSchedule new_PS = new MUNSProductionSchedule(getCtx(), 0, get_TrxName());
		new_PS.setAD_Org_ID(AD_Org_ID);
		new_PS.setQtyMT(m_serProduct.getWeight());
		new_PS.setQtyUom(m_serProduct.getQty());
		new_PS.setUNS_SERLineProduct_ID(serProduct_ID);
		new_PS.setDescription("Manufacturing Order " + m_serProduct.getAD_Org_ID() + " "+m_serProduct.getM_Product().getName());
		BigDecimal qtyOnHand = getQtyOnHand();
		new_PS.setQtyOnHand(qtyOnHand);
		new_PS.setQtyMTOnHand(getQtyMTOnHand(qtyOnHand));
		new_PS.setProductionRemarks("Production "+m_serProduct.getM_Product().getName() + " for " + ser.getC_BPartnerSR().getName());
		new_PS.setStickerRemarks(m_serProduct.getM_Product().getName());
		new_PS.setPSType(MUNSProductionSchedule.PSTYPE_MasterProductionSchedule);
		if (!new_PS.save())
			throw new AdempiereException("Can't create Manufactung Order");

		String retVal = "Manufacturing Order " +new_PS.getDocumentNo() + " created.";
		
		return retVal;

	}

}
