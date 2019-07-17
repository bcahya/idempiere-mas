/**
 * 
 */
package com.unicore.model.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.X_M_InOut;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Util;

import com.unicore.base.model.MInOut;
import com.unicore.base.model.MInvoice;
import com.unicore.model.MUNSPLConfirm;
import com.unicore.model.MUNSPLConfirmProduct;

/**
 * @author Burhani Adam
 *
 */
public class CreateInvoiceClaimPLReturn extends SvrProcess {

	/**
	 * 
	 */
	public CreateInvoiceClaimPLReturn() {
		// TODO Auto-generated constructor stub
	}

	private int m_PriceList_ID = 0;
	private Timestamp m_DateInvoiced = null;
	private String m_MsgErr = null;
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i =0 ; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("M_PriceList_ID"))
				m_PriceList_ID = para[i].getParameterAsInt();
			else if (name.equals("DateDoc"))
				m_DateInvoiced = para[i].getParameterAsTimestamp();
			else
				log.log(Level.SEVERE,"Data Not Found : "+ name);
		}
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception
	{
		MUNSPLConfirm plc = new MUNSPLConfirm(getCtx(), getRecord_ID(), get_TrxName());
		String whereClause = " AND DifferenceQty > 0";
		MUNSPLConfirmProduct[] plcpList = plc.getProducts(whereClause, null);
		if(plcpList.length <= 0)
			return "Not found difference qty in lines";
		
		
		//make faster get driver with SQL Query
		String sql = "SELECT C_BPartner_ID FROM UNS_Shipping WHERE DocStatus IN ('CO', 'CL') AND UNS_Shipping_ID ="
				+ " (SELECT UNS_Shipping_ID FROM UNS_ShippingFreight WHERE UNS_PackingList_ID=?)";
		int idDrv = DB.getSQLValue(get_TrxName(), sql, plc.getUNS_PL_Return().getUNS_PackingList_ID());
		MBPartnerLocation[] bpl = MBPartnerLocation.getForBPartner(getCtx(), idDrv, get_TrxName());
		if(bpl.length <=0)
			throw new AdempiereException("Business partner dont have a location");
		
		
		MInOut io = new MInOut(getCtx(), 0, get_TrxName());
		io.setAD_Org_ID(plc.getAD_Org_ID());
		io.setC_BPartner_ID(idDrv);
		io.setC_BPartner_Location_ID(bpl[0].get_ID());
		io.setSalesRep_ID(getAD_User_ID());
		io.setMovementDate(m_DateInvoiced);
		io.setM_Warehouse_ID(plcpList[0].getM_Warehouse_ID());
		io.setC_DocType_ID(MDocType.getDocType(MDocType.DOCBASETYPE_MaterialDelivery));
		io.setMovementType(X_M_InOut.MOVEMENTTYPE_CustomerShipment);
		io.setIsSOTrx(true);
		if(!io.save())
			m_MsgErr = io.getProcessMsg();
		
		MInvoice inv = new MInvoice(io, io.getMovementDate());
		
//		inv.setClientOrg(getAD_Client_ID(), plc.getAD_Org_ID());
//		inv.setDateInvoiced(new Timestamp(System.currentTimeMillis()));
//		inv.setDateAcct(inv.getDateInvoiced());
//		inv.setC_DocTypeTarget_ID(MDocType.getDocType(MDocType.DOCBASETYPE_ARInvoice));
		inv.setDescription("AUTO GENERATED FROM Packing List Return " + plc.getDocumentNo());
//		inv.setC_BPartner_ID(idDrv);
//		inv.setC_BPartner_Location_ID(bpl[0].get_ID());
		inv.setM_PriceList_ID(m_PriceList_ID);
//		inv.setSalesRep_ID(getAD_User_ID());
//		inv.setPaymentRule(X_C_Invoice.PAYMENTRULE_OnCredit);
//		inv.setDocStatus(DocAction.STATUS_Drafted);
//		inv.setDocAction(DocAction.ACTION_Complete);
		if(!inv.save())
			throw new AdempiereException(inv.getProcessMsg());
		//
		for(MUNSPLConfirmProduct plcp  : plcpList)
		{
			plcp.createShipInvLine(io, inv);
			
//			MInvoiceLine invLine = new MInvoiceLine(inv);
//			invLine.setM_Product_ID(plcp.getM_Product_ID());
//			invLine.setQty(plcp.getDifferenceQty());
//			invLine.setC_UOM_ID(plcp.getC_UOM_ID());
//			if(!invLine.save())
//				throw new AdempiereException("Error while trying invoice line");
		}
		
		plc.setC_Invoice_ID(inv.get_ID());
		plc.saveEx();
		
		if(Util.isEmpty(m_MsgErr, true))
			m_MsgErr = "Shipment no #" + io.getDocumentNo() + " Invoice no #" + inv.getDocumentNo(); 
		
		return m_MsgErr;
	}
}