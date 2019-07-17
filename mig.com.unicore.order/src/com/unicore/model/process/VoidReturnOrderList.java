/**
 * 
 */
package com.unicore.model.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MInOutLineMA;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTransaction;
import org.compiere.model.X_M_Transaction;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.unicore.base.model.MInOut;
import com.unicore.base.model.MInvoice;
import com.unicore.model.MUNSPLReturnOrder;

/**
 * @author Burhani Adam
 *
 */
public class VoidReturnOrderList extends SvrProcess {
	
	private int m_PL_ReturnOrder_ID = 0;
	private String m_Reason = null;
	private String m_Description = null;

	/**
	 * 
	 */
	public VoidReturnOrderList() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("UNS_PL_ReturnOrder_ID"))
				m_PL_ReturnOrder_ID = para[i].getParameterAsInt();
			else if (name.equals("Reason"))
				m_Reason = para[i].getParameterAsString();
			else if (name.equals("Description"))
				m_Description = para[i].getParameterAsString();
			else 
				log.log(Level.SEVERE, "Unknown Parameter :" + name);
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{	
		MUNSPLReturnOrder ro = new MUNSPLReturnOrder(getCtx(), m_PL_ReturnOrder_ID, get_TrxName());
		ro.setIsCancelled(true);
		ro.setReason(m_Reason);
		ro.setDescription(m_Description);
		ro.saveEx();
		
		MInOut io = new MInOut(getCtx(), ro.getUNS_PackingList_Order().getM_InOut_ID(), get_TrxName());
		MInvoice iv = new MInvoice(getCtx(), ro.getUNS_PackingList_Order().getC_Invoice_ID(), get_TrxName());
		
		iv.setDescription(iv.getDescription() + " :: " + m_Reason + "-" + m_Description);
		if(!iv.processIt(DocAction.STATUS_Voided) || !iv.save())
			throw new AdempiereException(iv.getProcessMsg());
		
		if(!io.processIt(DocAction.STATUS_Voided) || !io.save())
			throw new AdempiereException(io.getProcessMsg());
		
		org.compiere.model.MInOutLine[] iol = io.getLines();
		MTransaction mtrx = null;
		
		for(int i = 0; i < iol.length; i++)
		{
			String OriginLoc = "SELECT M_Locator_ID FROM UNS_PackingList_Line WHERE M_InOutLine_ID=?";
			int loc_id = DB.getSQLValue(get_TrxName(), OriginLoc, iol[i].get_ID());
			String getTrx = "SELECT UNS_PL_ConfirmProduct_ID FROM UNS_PL_ConfirmLine WHERE UNS_PackingList_Line_ID ="
					+ " (SELECT UNS_PackingList_Line_ID FROM UNS_PackingList_Line WHERE M_InOutLine_ID=?)";
			int trx_id = DB.getSQLValue(get_TrxName(), getTrx, iol[i].get_ID());
			
			if (iol[i].getM_AttributeSetInstance_ID() == 0)
			{
				MInOutLineMA mas[] = MInOutLineMA.get(getCtx(),
					iol[i].getM_InOutLine_ID(), get_TrxName());
				for (int j = 0; j < mas.length; j++)
				{
					MInOutLineMA ma = mas[j];
					BigDecimal QtyMA = ma.getMovementQty();

					//Intransit Customer
					if (!MStorageOnHand.add(getCtx(), io.getM_Warehouse_ID(),
						iol[i].getM_Locator_ID(),
						iol[i].getM_Product_ID(),
						ma.getM_AttributeSetInstance_ID(),
						QtyMA.negate(),ma.getDateMaterialPolicy(),
						get_TrxName()))
					{
						throw new AdempiereException("Cannot correct Inventory OnHand (MA) [" + iol[i].getM_Product().getValue() + "] - ");
					}
					
					//Origin Locator
					if (!MStorageOnHand.add(getCtx(), io.getM_Warehouse_ID(),
							loc_id,
							iol[i].getM_Product_ID(),
							ma.getM_AttributeSetInstance_ID(),
							QtyMA,ma.getDateMaterialPolicy(),
							get_TrxName()))
					{
						throw new AdempiereException("Cannot correct Inventory OnHand (MA) [" + iol[i].getM_Product().getValue() + "] - ");
					}
					
					//	Create Transaction intransit loc
					mtrx = new MTransaction (getCtx(), iol[i].getAD_Org_ID(),
						X_M_Transaction.MOVEMENTTYPE_PackedIn, iol[i].getM_Locator_ID(),
						iol[i].getM_Product_ID(), ma.getM_AttributeSetInstance_ID(),
						QtyMA.negate(), io.getMovementDate(), get_TrxName());
					mtrx.setUNS_PL_ConfirmProduct_ID(trx_id);
					if (!mtrx.save())
					{
						throw new AdempiereException("Could not create Material Transaction (MA) [" + iol[i].getM_Product().getValue() + "]");
					}
					
					//	Create Transaction origin loc
					mtrx = new MTransaction (getCtx(), iol[i].getAD_Org_ID(),
						X_M_Transaction.MOVEMENTTYPE_PackedOut, loc_id,
						iol[i].getM_Product_ID(), ma.getM_AttributeSetInstance_ID(),
						QtyMA, io.getMovementDate(), get_TrxName());
					mtrx.setUNS_PL_ConfirmProduct_ID(trx_id);
					if (!mtrx.save())
					{
						throw new AdempiereException("Could not create Material Transaction (MA) [" + iol[i].getM_Product().getValue() + "]");
					}
				}
			}
			//	sLine.getM_AttributeSetInstance_ID() != 0
			if (mtrx == null)
			{
				Timestamp dateMPolicy = io.getMovementDate();
				if(iol[i].getM_AttributeSetInstance_ID()>0)
				{
					I_M_AttributeSetInstance asi = iol[i].getM_AttributeSetInstance();
					if(dateMPolicy != asi.getCreated())
					{
						MAttributeSetInstance asiModel = (MAttributeSetInstance) asi;
						asiModel.setCreated(dateMPolicy);
					}
				}
				
				//	Fallback: Update Storage - see also VMatch.createMatchRecord
				if (!MStorageOnHand.add(getCtx(), io.getM_Warehouse_ID(),
					iol[i].getM_Locator_ID(),
					iol[i].getM_Product_ID(),
					iol[i].getM_AttributeSetInstance_ID(),
					iol[i].getMovementQty().negate(),dateMPolicy,get_TrxName()))
				{
					throw new AdempiereException("Cannot correct Inventory OnHand [" + iol[i].getM_Product().getValue() + "] - ");
				}
				
				//	FallBack: Create Transaction
				mtrx = new MTransaction (getCtx(), iol[i].getAD_Org_ID(),
					X_M_Transaction.MOVEMENTTYPE_PackedIn, iol[i].getM_Locator_ID(),
					iol[i].getM_Product_ID(), iol[i].getM_AttributeSetInstance_ID(),
					iol[i].getMovementQty().negate(), io.getMovementDate(), get_TrxName());
				mtrx.setUNS_PL_ConfirmProduct_ID(trx_id);
				if (!mtrx.save())
				{
					throw new AdempiereException("Could not create Material Transaction [" + iol[i].getM_Product().getValue() + "]");
				}
				
				if (!MStorageOnHand.add(getCtx(), io.getM_Warehouse_ID(),
						loc_id,
						iol[i].getM_Product_ID(),
						iol[i].getM_AttributeSetInstance_ID(),
						iol[i].getMovementQty(),dateMPolicy,get_TrxName()))
				{
					throw new AdempiereException("Cannot correct Inventory OnHand [" + iol[i].getM_Product().getValue() + "] - ");
				}
					
				//	FallBack: Create Transaction
				mtrx = new MTransaction (getCtx(), iol[i].getAD_Org_ID(),
					X_M_Transaction.MOVEMENTTYPE_PackedOut, iol[i].getM_Locator_ID(),
					iol[i].getM_Product_ID(), iol[i].getM_AttributeSetInstance_ID(),
					iol[i].getMovementQty(), io.getMovementDate(), get_TrxName());
				mtrx.setUNS_PL_ConfirmProduct_ID(trx_id);
				if (!mtrx.save())
				{
					throw new AdempiereException("Could not create Material Transaction [" + iol[i].getM_Product().getValue() + "]");
				}
			}
		}	//	stock movement
		
		return null;
	}
}