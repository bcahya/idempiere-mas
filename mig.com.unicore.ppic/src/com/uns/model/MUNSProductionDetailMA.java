
/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;




import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MUNSImportContent;

//import org.compiere.model.MCostDetail;


/**
 * @author YAKA
 *
 */
public class MUNSProductionDetailMA extends X_UNS_Production_DetailMA {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2808020068940739413L;

	/**
	 * @param ctx
	 * @param UNS_Production_DetailMA_ID
	 * @param trxName
	 */
	public MUNSProductionDetailMA(Properties ctx,
			int UNS_Production_DetailMA_ID, String trxName) {
		super(ctx, UNS_Production_DetailMA_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSProductionDetailMA(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * Parent constructor
	 * @param parent
	 * @param asi
	 * @param qty
	 * @param ctx
	 * @param trxName
	 */
	public MUNSProductionDetailMA( MUNSProductionDetail parent, int asi, BigDecimal qty)	{
		
		super(parent.getCtx(),0,parent.get_TrxName());
		setM_AttributeSetInstance_ID(asi);
		setUNS_Production_Detail_ID(parent.get_ID());
		setMovementQty(qty);
		setAD_Org_ID(parent.getAD_Org_ID());
		
	}
	
	public static MUNSProductionDetailMA get( MUNSProductionDetail parent, int asi, Timestamp dateMPolicy )  {
		
		String where = " UNS_Production_Detail_ID = ? AND M_AttributeSetInstance_ID = ? AND DateMaterialPolicy = ? ";
		
		MUNSProductionDetailMA lineMA = MTable.get(parent.getCtx(), MUNSProductionDetailMA.Table_Name).createQuery(where, parent.get_TrxName())
		.setParameters(parent.getUNS_Production_Detail_ID(), asi, dateMPolicy).firstOnly();
		
		if (lineMA != null)
			return lineMA;
		
		lineMA = new MUNSProductionDetailMA(parent, asi,	Env.ZERO);
		lineMA.setDateMaterialPolicy(dateMPolicy);
		return lineMA;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
//		if(success){
//			MUNSProductionDetail parent = getParent();
//			MUNSImportContent[] icList = MUNSProduction.getPrevImportList(getCtx(), get_TrxName(), parent.getM_AttributeSetInstance_ID(), parent.getM_Product_ID());
//			if ((icList == null || icList.length <=0) 
//				&& MAttributeSetInstance.isImportedProduct(getCtx(), get_TrxName(), getM_AttributeSetInstance_ID())){
//			//if (null != cekImport()){
//				//MUNSBCProductCategory bcpc = getBCProductCategory();
//				MUNSImportContent ic = MUNSImportContent.getImportContent(getCtx(), getM_AttributeSetInstance_ID(), getUNS_Production_Detail().getUNS_Production_ID(), get_TrxName());
//				MUNSProduction grandParent = parent.getParent();
//				if (ic.getUNS_ImportContent_ID() == 0){
//					ic.setUNS_Production_ID(grandParent.get_ID());
//					if(grandParent.getOutputType().equalsIgnoreCase(MUNSProduction.OUTPUTTYPE_Multi))
//						ic.setM_Product_ID(grandParent.getPrimaryOutput().getM_Product_ID());
//					else 
//						ic.setM_Product_ID(grandParent.getM_Product_ID());
//					ic.setMaterial_ID(parent.getM_Product_ID());
//					ic.setHSCode(getBCProductCategory().getHSCode());
//					ic.setM_AttributeSetInstance_ID(getM_AttributeSetInstance_ID());
//					ic.setQtyBOM(parent.getPlannedQty());
//					ic.setImportContent(getMovementQty().abs().divide(parent.getPlannedQty(), 4, RoundingMode.HALF_UP)
//							.multiply(Env.ONEHUNDRED).setScale(2));
//					ic.setQtyImport(getMovementQty().abs());
//					//MCostDetail costDetail = AttributeSetInstance.getCost(getCtx(), get_TrxName(), parent.getM_Product_ID(), getM_AttributeSetInstance_ID());
//					//ic.setCost(costDetail.getAmt().divide(costDetail.getQty(), 2, RoundingMode.HALF_UP));
//					//ic.setCost(MAttributeSetInstance.getPrice(getCtx(), get_TrxName(), parent.getM_Product_ID()));
//					loadPriceFromInvoice(ic);
//					if (!ic.save())
//						return false;
//				}
//			} else if ( icList!= null && icList.length > 0){
//				for (MUNSImportContent ic : icList){
//					MUNSImportContent new_ic = new MUNSImportContent(getCtx(), 0, get_TrxName());
//					MUNSImportContent.copyValues(ic, new_ic, ic.getAD_Client_ID(), ic.getAD_Org_ID());
//					int p_id = ic.getUNS_Production_ID();
//					MUNSProduction p = new MUNSProduction(getCtx(), p_id, get_TrxName());
//					BigDecimal comparison = parent.getQtyUsed().divide(p.getProductionQty(), 20, RoundingMode.HALF_DOWN);
//					BigDecimal new_qty = (ic.getQtyBOM().multiply(comparison)).setScale(4, RoundingMode.HALF_DOWN);
//					new_ic.setQtyBOM(new_qty);
//					new_ic.setQtyImport(new_qty);
//					new_ic.setUNS_Production_ID(parent.getParent().getUNS_Production_ID());
//					if (!new_ic.save())
//						return false;
//				}
//			}
//		}
		return super.afterSave(newRecord, success);
	}
	
	@Override
	protected boolean afterDelete(boolean success)
	{
		// delete UNS_Productio_WRAttributes berdasarkan
		
//		String sql = "DELETE FROM UNS_Production_WRAttributes WHERE M_AttributeSetInstance_ID =? ";
//		
//		DB.executeUpdateEx(
//				sql, new Object[]{getM_AttributeSetInstance_ID()}, get_TrxName());
		
		return true;
	}

	@Override
	public MUNSProductionDetail getParent() {
		return new MUNSProductionDetail(getCtx(), getUNS_Production_Detail_ID(), get_TrxName());
	}
	
//	private MUNSBCProductCategory getBCProductCategory(){
//		MProduct product = new MProduct(getCtx(), getParent().getM_Product_ID(), get_TrxName());
//		MUNSBCProductCategory bcpc = product.getBCProductCategory();
//		if (bcpc.get_ID() == 0)
//			return null;
//		return bcpc;
//	}
	
	/**
	 * Try to get price and currency used either from Invoice, or Material Receipt based on it's M_Product_ID,   
	 * and M_AttributeSetInstance_ID, and then set it to the given MUNSImportContent.
	 */
	protected void loadPriceFromInvoice(MUNSImportContent ic)
	{
		org.compiere.model.MProduct product = (org.compiere.model.MProduct) getParent().getM_Product();
		// Try to get it based on ASI in M_InOutLineMA.
		int receiptLine = DB.getSQLValue(get_TrxName(), 
				" SELECT iol.M_InOutLine_ID FROM M_InOutLine iol, M_InOut io, M_InOutLineMA iolMA " +
				" WHERE iol.M_InOut_ID=io.M_InOut_ID AND iol.M_InOutLine_ID=iolMA.M_InOutLine_ID " +
				"	AND	io.C_DocType_ID=(SELECT dt.C_DocType_ID FROM C_DocType dt WHERE dt.IsSOTrx='N' AND " +
				"						 dt.DocBaseType=? AND dt.AD_Client_ID=?) " +
				"	AND iol.M_Product_ID=? AND iolMA.M_AttributeSetInstance_ID=?", 
				MDocType.DOCBASETYPE_MaterialReceipt, getAD_Client_ID(),
				product.getM_Product_ID(), getM_AttributeSetInstance_ID());
		if (receiptLine <= 0) {
			// Nothing in the InOutLineMA, try to get it directly from the receipt line.
			receiptLine = DB.getSQLValue(get_TrxName(), 
					" SELECT iol.M_InOutLine_ID FROM M_InOutLine iol, M_InOut io " +
					" WHERE iol.M_InOut_ID=io.M_InOut_ID " +
					"	AND	io.C_DocType_ID=(SELECT dt.C_DocType_ID FROM C_DocType dt WHERE dt.IsSOTrx='N' AND " +
					"						 dt.DocBaseType=? AND dt.AD_Client_ID=?) " +
					"	AND iol.M_Product_ID=? AND iol.M_AttributeSetInstance_ID=?", 
					MDocType.DOCBASETYPE_MaterialReceipt, getAD_Client_ID(),
					product.getM_Product_ID(), getM_AttributeSetInstance_ID());
		}
		if (receiptLine <= 0)
			throw new AdempiereException("Get Import Content Price: Can not find proper receipt line for product of " + 
					product.getValue() + "_" + product.getName() + " with M_AttributeSetInstance_ID of " +
					getM_AttributeSetInstance().getDescription());
		int invLineID = DB.getSQLValue(get_TrxName(), 
					" SELECT il.C_InvoiceLine_ID FROM C_InvoiceLine il, C_Invoice i " +
					" WHERE il.C_Invoice_ID=i.C_Invoice_ID AND il.M_InOutLine_ID=? " +
					"	AND	i.C_DocType_ID=(SELECT dt.C_DocType_ID FROM C_DocType dt WHERE dt.IsSOTrx='N' AND " +
					"						 dt.DocBaseType=? AND dt.AD_Client_ID=?) ", 
					receiptLine, MDocType.DOCBASETYPE_APInvoice, getAD_Client_ID());
		
		if (invLineID <= 0)
			throw new AdempiereException("Get Import Content Price: Can not find proper Invoice Line for product of " + 
					product.getValue() + "_" + product.getName() + " with M_AttributeSetInstance_ID of " +
					getM_AttributeSetInstance().getDescription());
		
		MInvoiceLine invLine = new Query(getCtx(), MInvoiceLine.Table_Name, "C_InvoiceLine_ID=?", get_TrxName())
								.setParameters(invLineID)
								.first();
		if (invLine == null)
			throw new AdempiereException("Get Import Content Price: Failed when loading instance of InvoiceLine for Product of " + 
					product.getValue() + "_" + product.getName() + " with M_AttributeSetInstance_ID of " +
					getM_AttributeSetInstance().getDescription());
		
		ic.setCost(invLine.getLineNetAmt().divide(invLine.getQtyEntered(), 2, BigDecimal.ROUND_HALF_UP));
		ic.setC_Currency_ID(invLine.getC_Invoice().getC_Currency_ID());
	}

}