/**
 * 
 */
package com.unicore.base.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoiceBatch;
import org.compiere.model.MInvoiceBatchLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocator;
import org.compiere.model.MMovementLineConfirm;
import org.compiere.model.MMovementLineMA;
import org.compiere.model.MOrder;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.MTransaction;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

import com.uns.base.model.Query;
import com.uns.model.MProduct;

import com.unicore.model.factory.UniCoreMaterialManagementModelFactory;

/**
 * @author menjangan
 *
 */
public class MInvoice extends org.compiere.model.MInvoice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param C_Invoice_ID
	 * @param trxName
	 */
	public MInvoice(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInvoice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param order
	 * @param C_DocTypeTarget_ID
	 * @param invoiceDate
	 */
	public MInvoice(MOrder order, int C_DocTypeTarget_ID, Timestamp invoiceDate) {
		super(order, C_DocTypeTarget_ID, invoiceDate);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ship
	 * @param invoiceDate
	 */
	public MInvoice(MInOut ship, Timestamp invoiceDate) {
		super(ship, invoiceDate);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param batch
	 * @param line
	 */
	public MInvoice(MInvoiceBatch batch, MInvoiceBatchLine line) {
		super(batch, line);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	/**
	 * 
	 * @param confirm
	 * @return
	 */
	public String createInventoryLostInvoice(MMovementConfirm confirm)
	{
		String retVal = "";
		MMovement move = new MMovement(getCtx(), confirm.getM_Movement_ID(), get_TrxName());
		this.setAD_Org_ID(confirm.getAD_Org_ID());
		this.setC_BPartner_ID(move.getC_BPartner_ID());
		int C_BP_Location = move.getC_BPartner_Location_ID();
		if(C_BP_Location <= 0)
		{
			MBPartnerLocation[] location = MBPartnerLocation.getForBPartner(getCtx(), move.getC_BPartner_ID(), get_TrxName());
			C_BP_Location = location[0].get_ID();
		}
		this.setC_BPartner_Location_ID(C_BP_Location);
		this.setAD_User_ID(move.getAD_User_ID());
		
		if(this.save())
		{
			confirm.setC_Invoice_ID(this.get_ID());
			confirm.save();
			retVal = createLines(confirm);
		}
		else
		{
			retVal = "[Process Failed]-Can't create Header Document of Inventory Lost Invoice.";
		}
		
		return retVal;
	}
	
	/**
	 * 
	 * @param confirm
	 * @return
	 */
	private String createLines(MMovementConfirm confirm)
	{
		for(MMovementLineConfirm lineConf : confirm.getLines(false))
		{
			MMovementLine lineMove = new MMovementLine(
					getCtx(), lineConf.getM_MovementLine_ID(), get_TrxName());
			MInvoiceLine iLine = new MInvoiceLine(this);
			iLine.setM_Product_ID(lineMove.getM_Product_ID());
			iLine.setM_AttributeSetInstance_ID(lineMove.getM_AttributeSetInstance_ID());
			iLine.setC_UOM_ID(iLine.getM_Product().getC_UOM_ID());
			iLine.setQty(lineConf.getDifferenceQty());
			iLine.setPrice();
			iLine.save();
			lineConf.setC_InvoiceLine_ID(iLine.get_ID());
			lineConf.save();
		}
		
		return "";
	}

	@Override
	public boolean beforeSave(boolean newRecord)
	{
		setDocType();
		return super.beforeSave(newRecord);
	}
	
	
	@Override
	public boolean beforeDelete()
	{
		int cInvPL = DB.getSQLValue(get_TrxName(), "SELECT uns_packinglist_order_id from uns_packinglist_order where m_invoice_id = "+get_ID());
		
		if(cInvPL>0)
		{
			int docPL = DB.getSQLValue(get_TrxName(), "SELECT documentno from uns_packinglist_order where uns_packinglist_order_id = "+cInvPL);
			throw new AdempiereException("Could not delete this record because reference PLOrder : "+docPL);
		}
		return true;
	}
	
	private void setDocType()
	{
		String wherClause = 
				MDocType.COLUMNNAME_DocBaseType + " = '"
		+ MDocType.DOCBASETYPE_ARInvoice + "' AND " 
						+ MDocType.COLUMNNAME_ExtensionHandler 
						+ " IS NOT NULL ";
		int C_DocType_ID = Query.get(
				getCtx(), 
				UniCoreMaterialManagementModelFactory.EXTENSION_ID, 
				MDocType.Table_Name, 
				wherClause, 
				get_TrxName()).firstIdOnly();
		if(C_DocType_ID <= 0)
			throw new AdempiereException("Not Defined Document Type");
		
		setC_DocTypeTarget_ID(C_DocType_ID);
		setC_DocType_ID(C_DocType_ID);
	}

	@Override
	public String completeIt()
	{
		MDocType docType = (MDocType)getC_DocType();
		if(MDocType.DOCBASETYPE_ARInvoice.equals(docType.getDocBaseType())
				&& docType.getExtensionHandler() != null)
		{
			MMovementConfirm confirm = MMovementConfirm.getOfInvoice(getC_Invoice_ID(), get_TrxName());
			if(null == confirm)
			{
				m_processMsg = "[Failed to complete document]-Movement confirm not found";
				return DocAction.STATUS_Invalid;
			}
			MMovementLineConfirm[] linesConf = confirm.getLines(false);
			for(int i=0; i<linesConf.length; i++)
			{
				MMovementLineConfirm lineConf = linesConf[i];
				MMovementLine lineMove = new MMovementLine(
						getCtx(), lineConf.getM_MovementLine_ID(), get_TrxName());
				
				MTransaction trxFrom = null;
				MLocator locator = lineMove.getIntransitLocator(false);
				MProduct product = new MProduct(getCtx(), lineMove.getM_Product_ID(), get_TrxName());
				
				if(null == locator)
				{
					m_processMsg = "[Failed to complete document]-Intransit locator not found";
					return DocAction.STATUS_Invalid;
				}
				
				int M_Asi_ID = lineMove.getM_AttributeSetInstanceTo_ID();
				if(M_Asi_ID <= 0)
				{
					M_Asi_ID = lineMove.getM_AttributeSetInstance_ID();
				}
				
				
				if(M_Asi_ID <= 0)
				{
					MMovementLineMA[] moveLineMA = MMovementLineMA.get(
							getCtx(), lineMove.get_ID(), get_TrxName());
					for(int j=0; j< moveLineMA.length; j++)
					{
						//Update Storage 
						if (!MStorageOnHand.add(getCtx(),locator.getM_Warehouse_ID(),
								locator.getM_Locator_ID(),
								lineMove.getM_Product_ID(), 
								moveLineMA[j].getM_AttributeSetInstance_ID(),
								lineConf.getDifferenceQty().negate(),
								moveLineMA[j].getDateMaterialPolicy(), 
								get_TrxName()))
						{
							String lastError = CLogger.retrieveErrorString("");
							m_processMsg = "Cannot correct Inventory OnHand (MA) - " + lastError;
							return DocAction.STATUS_Invalid;
						}
						
						trxFrom = new MTransaction (
								getCtx(), 
								locator.getAD_Org_ID(), 
								MTransaction.MOVEMENTTYPE_MovementFrom,
								locator.getM_Locator_ID(), 
								lineMove.getM_Product_ID(), 
								moveLineMA[i].getM_AttributeSetInstance_ID(),
								lineConf.getDifferenceQty(), 
								getDateAcct(), 
								get_TrxName());
						trxFrom.setM_MovementLine_ID(lineMove.getM_MovementLine_ID());
						if (!trxFrom.save())
						{
							m_processMsg = "Transaction From not inserted";
							return DocAction.STATUS_Invalid;
						}
					}
				}
				
				
				if(trxFrom == null)
				{
					MAttributeSetInstance asi = new MAttributeSetInstance(
							getCtx(), lineMove.getM_AttributeSetInstance_ID(), get_TrxName());

					Timestamp dateMPolicy = asi.getCreated();
					
					if(!MStorageOnHand.add(
							getCtx(), 
							locator.getM_Warehouse_ID(), 
							locator.get_ID(), 
							lineMove.getM_Product_ID(), 
							M_Asi_ID, 
							lineConf.getDifferenceQty().negate(), 
							dateMPolicy, 
							get_TrxName()))
					{
						String lastError = CLogger.retrieveErrorString("");
						m_processMsg = "Cannot correct Inventory " + (isSOTrx()? "Reserved" : "Ordered") + " (MA) - [" + product.getValue() + "] - " + lastError;
						return DocAction.STATUS_Invalid;
					}
					
					trxFrom = new MTransaction(
							getCtx(), 
							lineConf.getAD_Org_ID(), 
							MTransaction.MOVEMENTTYPE_CustomerShipment, 
							locator.getM_Locator_ID(), 
							product.get_ID(), 
							M_Asi_ID, 
							lineConf.getDifferenceQty(), 
							dateMPolicy, 
							get_TrxName());
					if(!trxFrom.save())
					{
						m_processMsg = "Could not create Material Transaction (MA) [" + product.getValue() + "]";
						return DocAction.STATUS_Invalid;
					}
				}
			}
		}
		return super.completeIt();
	}
}
