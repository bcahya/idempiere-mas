/**
 * 
 */
package com.uns.model.validator;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutConfirm;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInOutLineConfirm;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPeriod;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import com.uns.base.model.Query;
import com.uns.model.GeneralCustomization;
import com.uns.model.MUNSBCNumberGeneratorLine;
import com.uns.model.MUNSPackingSlip;
import com.uns.model.MUNSPackingSlipLine;
import com.uns.model.MUNSPackingSlipSupplier;
import com.uns.model.MUNSProduction;
import com.uns.model.MUNSSOShipment;
import com.uns.model.factory.UNSPPICModelFactory;
import com.uns.qad.model.MUNSQAStatusInOut;
import com.uns.qad.model.MUNSQAStatusPRC;
import com.uns.qad.model.MUNSQAStatusPRCLine;

/**
 * @author YAKA
 *
 */
public class PPICValidator implements ModelValidator {

	/**
	 * 
	 */
	public PPICValidator() {
		super ();
	}
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(PPICValidator.class);
	
	/** Client			*/
	private int			m_AD_Client_ID = -1;

	/* (non-Javadoc)
	 * @see org.compiere.model.ModelValidator#initialize(org.compiere.model.ModelValidationEngine, org.compiere.model.MClient)
	 */
	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {

		if (client != null) {	
			m_AD_Client_ID = client.getAD_Client_ID();
			log.info(client.toString());
		}
		else  {
			log.info("Initializing global validator: "+this.toString());
		}

		// Tables to be monitored (n/a)
		//engine.addDocValidate(MUNSProduction.Table_Name, this);
		
		// Documents to be monitored
		engine.addDocValidate(MInOutConfirm.Table_Name, this);
		engine.addDocValidate(MInOut.Table_Name, this);
		engine.addDocValidate(MUNSProduction.Table_Name, this);
		engine.addDocValidate(MInvoice.Table_Name, this);
		engine.addDocValidate(MUNSPackingSlip.Table_Name, this);
		//engine.addDocValidate(MOrder.Table_Name, this);

	}	//	initialize

	   /**
     *	Model Change of a monitored Table.
     *	Called after PO.beforeSave/PO.beforeDelete
     *	when you called addModelChange for the table
     *	@param po persistent object
     *	@param type TYPE_
     *	@return error message or null
     *	@exception Exception if the recipient wishes the change to be not accept.
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.get_TableName() + " Type: "+type);
		String msg = null;
		/*
		if (po.get_TableName().equals(MUNSProduction.Table_Name) && type == TYPE_AFTER_CHANGE) 
		{
			MUNSProduction p = (MUNSProduction) po;
			if(p.is_ValueChanged(MUNSProduction.COLUMNNAME_IsComplete)){
				msg = updateStorage(p);
				if (msg != null)
					return msg;
			}
		}
		*/
		return msg;
	}	//	modelChange

	/**
	 *	Validate Document.
	 *	Called as first step of DocAction.prepareIt 
     *	when you called addDocValidate for the table.
     *	Note that totals, etc. may not be correct.
	 *	@param po persistent object
	 *	@param timing see TIMING_ constants
     *	@return error message or null
	 */
	@SuppressWarnings("deprecation")
	public String docValidate (PO po, int timing)
	{
		log.info(po.get_TableName() + " Timing: "+timing);
		String msg;
		
		// After Complete the Shipment Confirm (MInOutConfirm) Document
		if (po.get_TableName().equals(MInOutConfirm.Table_Name) && timing == TIMING_AFTER_COMPLETE) 
		{
			MInOutConfirm ioConfirm = (MInOutConfirm) po;
			if (MInOutConfirm.CONFIRMTYPE_ShipReceiptConfirm.equals(ioConfirm.getConfirmType()))
			{
				msg = updateSOShipment((MInOutConfirm) po);
				if (msg != null)
					return msg;
			}
		}
		
		// After Complete the Material Receipt (MInOut) Document
		if (po.get_TableName().equals(MInOut.Table_Name) && timing == TIMING_AFTER_COMPLETE) 
		{
			MInOut io = (MInOut) po;
			if (io.getC_DocType().getDocBaseType().equals(MDocType.DOCBASETYPE_MaterialReceipt))
			{
				for (MInOutLine iol : io.getLines())
				{
					MUNSPackingSlipLine psl = 
							Query.get(io.getCtx(), UNSPPICModelFactory.getExtensionID(), 
									  MUNSPackingSlipLine.Table_Name, "M_InOutLine_ID=?", io.get_TrxName())
								.setParameters(iol.get_ID())
								.first();
					if (psl == null)
						continue;
					psl.setQtyReceived(iol.getQtyEntered());
					psl.saveEx();
				}
				msg = updateStorage((MInOut) po);
				if (msg != null)
					return msg;
			}
		}
		
		if (po.get_TableName().equals(MInOut.Table_Name) && timing == TIMING_AFTER_VOID) 
		{
			MInOut io = (MInOut) po;
			if (io.getC_DocType().getDocBaseType().equals(MDocType.DOCBASETYPE_MaterialReceipt))
			{
				for (MInOutLine iol : io.getLines())
				{
					MUNSPackingSlipLine psl = 
							Query.get(io.getCtx(), UNSPPICModelFactory.getExtensionID(), 
									  MUNSPackingSlipLine.Table_Name, "M_InOutLine_ID=?", io.get_TrxName())
								.setParameters(iol.get_ID())
								.first();
					if (psl == null)
						continue;
					psl.setQtyReceived(Env.ZERO);
					psl.setDescription("***The receipt is voided***");
					psl.saveEx();
				}
			}
		}
		
		// Moved from method modelChange as the MUNSProduction now implements DocAction.
		if (po.get_TableName().equals(MUNSProduction.Table_Name) && timing == TIMING_AFTER_COMPLETE) 
		{
			MUNSProduction p = (MUNSProduction) po;
			//if(p.is_ValueChanged(MUNSProduction.COLUMNNAME_IsComplete)){
			msg = updateStorage(p);
			if (msg != null)
				return msg;
			//}
		}
		
		// After Complete the Packing Slip Document, generate BC Code Number.
		if (po.get_TableName().equals(MUNSPackingSlip.Table_Name) && timing == TIMING_AFTER_COMPLETE) 
		{
			MUNSPackingSlip ps = (MUNSPackingSlip) po;
			for (MUNSPackingSlipSupplier pss : ps.getLines())
			{
				if (pss.getBCNo()!=null && !pss.getBCNo().trim().equals(""))
					continue;
				
				pss.setBCNo(MUNSBCNumberGeneratorLine.getBCCodeNumber(ps.getCtx(), ps.getUNS_BCCode_ID(),
						MPeriod.get(ps.getCtx(), ps.getBCDocDate(), ps.getAD_Org_ID()).getC_Year_ID(),
						ps.getBCDocDate(), false, ps.getAD_Org_ID(), po, ps.get_TrxName()));
				pss.saveEx();
			}
		}
		
		// Before Prepare the Invoice (Customer) Document, Must Input NTPN.
		if (po.get_TableName().equals(MInvoice.Table_Name) && timing == TIMING_BEFORE_PREPARE) 
		{
			MInvoice inv = (MInvoice) po;
			
			MInvoiceLine[] invLines = inv.getLines();
			BigDecimal PPNImport = Env.ZERO;
			BigDecimal PPh22Import = Env.ZERO;
			BigDecimal importDutyAmt = Env.ZERO;
			for (MInvoiceLine invLine : invLines)
			{
				PPNImport = PPNImport.add(invLine.getPPNImport());
				PPh22Import = PPh22Import.add(invLine.getPPh22Import());
				importDutyAmt = importDutyAmt.add(invLine.getImportDutyAmt());
			}
			
			//FIXME for SALES TRANSACTION
//			inv.setPPNImport(PPNImport);
//			inv.setPPh22Import(PPh22Import);
//			inv.setImportDutyAmt(importDutyAmt);
			inv.saveEx();
			/*
			if (inv.isSOTrx() && inv.getNTPN()==null){
				return "Please input NTPN before prepare.";
			}
			*/
		}
		
		// After Complete the Invoice (Customer) Document, generate BC Code Number.
		if (po.get_TableName().equals(MInvoice.Table_Name) && timing == TIMING_AFTER_COMPLETE) 
		{
//			MInvoice inv = (MInvoice) po;
//			if (inv.isSOTrx() && inv.getUNS_BCCode_ID() > 0)
//			{
//				inv.setBCDocNo(MUNSBCNumberGeneratorLine.getBCCodeNumber(inv.getCtx(), inv.getUNS_BCCode_ID(),
//					MPeriod.get(inv.getCtx(), inv.getBCDocDate(), inv.getAD_Org_ID()).getC_Year_ID(),
//					inv.getBCDocDate(), false, inv.getAD_Org_ID(), po, inv.get_TrxName()));
//				inv.saveEx();
//			}
		}
				
		return null;
	}	//	docValidate

	/**
	 *	User Login.
	 *	Called when preferences are set
	 *	@param AD_Org_ID org
	 *	@param AD_Role_ID role
	 *	@param AD_User_ID user
	 *	@return error message or null
	 */
	public String login (int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		log.info("AD_User_ID=" + AD_User_ID);
		return null;
	}	//	login

	
	/**
	 *	Get Client to be monitored
	 *	@return AD_Client_ID client
	 */
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}	//	getAD_Client_ID

	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("BillingValidator");
		return sb.toString ();
	}	//	toString
	
	private String updateSOShipment(MInOutConfirm po) 
	{
		String msg = null;
		MInOut io = new MInOut(po.getCtx(), po.getM_InOut_ID(), null);
		
		for(MInOutLineConfirm iolConfirm : po.getLines(false))
		{
			MUNSSOShipment sos = MUNSSOShipment.get(po.getCtx(), iolConfirm.getM_InOutLine_ID(), 
													MUNSSOShipment.COLUMNNAME_M_InOutLine_ID,
													po.get_TrxName());
			if (sos == null) continue;
			
			sos.setQtyUom(iolConfirm.getConfirmedQty());
			BigDecimal qtyMT = 
					sos.getM_Product().getWeight().multiply(iolConfirm.getConfirmedQty())
					.multiply(new BigDecimal(0.001));
			sos.setQtyMT(qtyMT.setScale(4, BigDecimal.ROUND_HALF_UP));
			if(!sos.save())
				return "Error when update SOShipment";
			
			msg = MUNSQAStatusInOut.createShipReceiptInspection(io, po, false);
		}
		
		if(MUNSQAStatusInOut.checkShipReceipt(io)){
			msg = MUNSQAStatusInOut.createShipReceiptInspection(io, po, true);
			if (msg!=null)
				throw new AdempiereException(msg);
		}
		
		return msg;
	}
	
	private String updateStorage(MInOut io) 
	{
		//TODO QA Status
//		if(MUNSQAStatusInOut.checkShipReceipt(io))
//		{
//			int sio_id = GeneralCustomization.get_ID(io.get_TrxName(), MUNSQAStatusInOut.Table_Name, 
//					MInOut.COLUMNNAME_M_InOut_ID, "=", io.get_ID());
//			MUNSQAStatusInOut sio = new MUNSQAStatusInOut(io.getCtx(), sio_id, io.get_TrxName());
//			for(MUNSQAStatusInOutLine line : sio.getLines())
//			{
//				MStorageOnHand soh = MStorageOnHand.get(io.getCtx(), line.getM_InOutLine().getM_Locator_ID(),
//						line.getM_Product_ID(), 
//						line.getM_InOutLine().getM_AttributeSetInstance_ID(), io.get_TrxName());
//				if (soh==null)
//					return "Error when update StorageOnHand (Validator)";
//				soh.setQtyQAStatus(line.getReleaseQty(), line.getOnHoldQty(), line.getNCQty());	
//			}
//		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private String updateStorage(MUNSProduction p) 
	{
		int prc_id = GeneralCustomization.get_ID(p.get_TrxName(), MUNSQAStatusPRC.Table_Name, 
												 MUNSProduction.COLUMNNAME_UNS_Production_ID, "=", p.get_ID());
		MUNSQAStatusPRC prc = new MUNSQAStatusPRC(p.getCtx(), prc_id, p.get_TrxName());

		for(MUNSQAStatusPRCLine line : prc.getLines())
		{
			MStorageOnHand soh = MStorageOnHand.get(p.getCtx(), line.getM_Locator_ID(),
													line.getM_Product_ID(), 
													line.getM_AttributeSetInstance_ID(), p.get_TrxName());
			if (soh==null)
				return "Error when update StorageOnHand (Validator)";			

//			if (MProduct.isStockedOnPallet(soh.getCtx(), line.getM_Product_ID()))
//			{
				/*
				String QAStatusToSet = MStorageOnHand.checkQAStatus(
						MUNSQAStatusPRCLine.QASTATUS_PendingInspection);
				*/
//				PalletHelper.resetAllPackagesOfQAStatus(
//						line.getM_Product_ID(), 
//						line.getM_Locator_ID(), 
//						line.getM_AttributeSetInstance_ID(), 
//						PalletHelper.QASTATUS_RELEASE,
//						line.getQAStatus(), 
//						soh.getCtx(), 
//						soh.get_TrxName());
				/*
				PalletHelper.moveAllPackagesFromToQAStatus(line.getM_Product_ID(), 
															line.getM_Locator_ID(), 
															line.getM_AttributeSetInstance_ID(), 
															PalletHelper.QASTATUS_RELEASE,
															QAStatusToSet, 
															soh.getCtx(), 
															soh.get_TrxName());
				*
				PalletHelper.moveOut(p.getCtx(), (
									 MProduct) line.getM_Product(), 
									 line.getM_Locator_ID(), 
									 line.getM_AttributeSetInstance_ID(),
									 line.getQty(), 
									 PalletHelper.QASTATUS_RELEASE, 
									 p.get_TrxName());
				*/
//			}
//			else {
//				soh.setQtyQAStatus(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
//			}
//			soh.setQAStatus(line.getQAStatus());
//			soh.save();
		}
		return null;
	}
	
}
