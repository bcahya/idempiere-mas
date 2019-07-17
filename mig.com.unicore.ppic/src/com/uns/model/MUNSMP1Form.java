package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.util.AdempiereUserError;

import com.uns.base.model.Query;
import com.uns.model.factory.UNSPPICModelFactory;

public class MUNSMP1Form extends X_UNS_MP1Form {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8634678422660304761L;
	public boolean m_isFormModification = false;
	private MUNSMP1FormDetail[] m_lines;

	public MUNSMP1Form(Properties ctx, int UNS_MP1Form_ID, String trxName) {
		super(ctx, UNS_MP1Form_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSMP1Form(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public String getDocNo(MUNSResource wc){
		String retval = new SimpleDateFormat("MMdd/").format(getProductionDate()) + 
				wc.getValue().substring(3, 10) + "-" + 
				wc.getValue().charAt(wc.getValue().length()-1);
		
		return retval;
	}
	
	public static MUNSMP1Form checkAvaliable(Properties ctx, int WorkCenter_ID, Timestamp date, 
			int AD_Org_ID, String trxName) {
		//check at Database
		String sDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

		String WhereClause = "ProductionDate = '" + sDate + "' AND UNS_Resource_ID = " + WorkCenter_ID
				+ "  AND AD_OrgTo_ID = " + AD_Org_ID;

		MUNSMP1Form retval = Query.get(ctx, UNSPPICModelFactory.getExtensionID(),
				MUNSMP1Form.Table_Name, WhereClause, trxName).first();
		
		return retval;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param WorkCenter_ID
	 * @param date
	 * @param AD_Org_ID
	 * @param PPConfig_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSMP1Form checkAvaliable(Properties ctx, int WorkCenter_ID, Timestamp date, 
			int AD_Org_ID, int PPConfig_ID, String trxName) {
		//check at Database
		String sDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

		//if (PPConfig_ID==0)
		//	throw new FillMandatoryException(COLUMNNAME_UNS_ProductionPayConfig_ID);
		
		String WhereClause = "ProductionDate = '" + sDate + "' AND UNS_Resource_ID = " + WorkCenter_ID
				+ "  AND AD_OrgTo_ID = " + AD_Org_ID;// + " AND UNS_ProductionPayConfig_ID = " + PPConfig_ID;

		List<MUNSMP1Form> forms = Query.get(ctx, UNSPPICModelFactory.getExtensionID(),
				MUNSMP1Form.Table_Name, WhereClause, trxName).list();
		
		MUNSMP1Form retForm = null;
		boolean hasRMPInputBeenUsed = false;
		for (MUNSMP1Form form : forms)
		{
			if (form.getUNS_ProductionPayConfig_ID() > 0)
				hasRMPInputBeenUsed = true;
			
			retForm = form;
			if (form.getUNS_ProductionPayConfig_ID() == PPConfig_ID) {
				break;
			}
		}
		
		if (!hasRMPInputBeenUsed && retForm != null) {
			retForm.setUNS_ProductionPayConfig_ID(PPConfig_ID);
		}
		else if (retForm != null && retForm.getUNS_ProductionPayConfig_ID() != PPConfig_ID)
			retForm = null;
		
		return retForm;
	}
	
	public MUNSMP1FormDetail[] getLines(boolean requery){
		if (m_lines == null || requery)
			m_lines = getLines(null);

		return m_lines;
	}

	private MUNSMP1FormDetail[] getLines(String whereClause) {
		String whereClauseFinal = "UNS_MP1Form_ID="+get_ID();
		if (whereClause != null)
			whereClauseFinal = whereClauseFinal + " AND " + whereClause;
		
		List<MUNSMP1FormDetail> list = Query.get(getCtx(), 
				UNSPPICModelFactory.getExtensionID(), 
				MUNSMP1FormDetail.Table_Name, 
				whereClauseFinal, get_TrxName())
				.setOrderBy(MUNSMP1FormDetail.COLUMNNAME_Nomer)
				.list();

		m_lines = list.toArray(new MUNSMP1FormDetail[list.size()]);
		
		return m_lines;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord) {

//		setAD_OrgTrx_ID(UNSApps.getRefAsInt(UNSApps.RMP_ORG));
		
		return super.beforeSave(newRecord);
	}

	public static MUNSMP1FormDetail[] initLines(Properties ctx, MUNSMP1Form header,
			String trxName) {
		
		MUNSResource workCenter = new MUNSResource(ctx, header.getUNS_Resource_ID(),
				trxName);
		
		List<MUNSMP1FormDetail> detailForm = new ArrayList<MUNSMP1FormDetail>(); 
		int no = 0;
		for (MUNSResource workStation : workCenter.getLines()){
			MUNSMP1FormDetail detail = MUNSMP1FormDetail.init(header, workStation);
			detail.setNomer(++no);
			detail.m_isFormModification = true;
			detailForm.add(detail);
		}
		
		MUNSMP1FormDetail[] retval = new MUNSMP1FormDetail[detailForm.size()];
		detailForm.toArray(retval);
		return retval;
	}
	
	public static void CreateUpdateMovement(MUNSMP1Form form) {
		Properties ctx = form.getCtx();
		String trxName = form.get_TrxName();
		MMovement move = null;
		
		move = new MMovement(ctx, form.getM_Movement_ID(), trxName);
		
		move.setAD_Org_ID(form.getAD_OrgTrx_ID());
		setMovement(form, move);
		move.saveEx();
		form.setM_Movement_ID(move.get_ID());
		form.saveEx();
		
		for (MUNSMP1FormDetail detail : form.getLines(true)){
			if (detail.getFillIn().compareTo(BigDecimal.ZERO)==0
					& detail.getAdditionalQty().compareTo(BigDecimal.ZERO)==0){
				if (detail.getM_MovementLine_ID()>0){
					MMovementLine line = new MMovementLine(ctx, detail.getM_MovementLine_ID(), trxName);
					line.deleteEx(false);
					
					detail.setM_MovementLine_ID(0);
					detail.saveEx();
				}
				
				continue;
			}
			
			MMovementLine line = new MMovementLine(ctx, detail.getM_MovementLine_ID(), trxName);
			
			line.setM_Movement_ID(move.get_ID());
			setMovementLine(detail, line);
			line.saveEx();
			
			detail.setM_MovementLine_ID(line.get_ID());
			detail.saveEx();
		}
	}
	
	public static void setMovement(MUNSMP1Form form, MMovement move) {
		//TODO FORM PRODUKSI
//		move.setAD_Org_ID(form.getAD_OrgTrx_ID());
//		move.setC_DocType_ID(MDocType.getDocType(MMovement.DOCBASETYPE_MaterialMovement));
//		move.setMovementDate(form.getProductionDate());
//		move.setMovementType(MMovement.MOVEMENTTYPE_ProductionMMType);
//		move.setDocBaseType(MMovement.DOCBASETYPE_MaterialMovement);
//		
//		MUNSResource wc = MUNSResource.get(form.getCtx(), form.getUNS_Resource_ID());
//		move.setDescription("Move to " + wc.getName().substring(12, wc.getName().length()));
//		
//		//if Product is Not KBA will SEND to LKU
//		if(form.getM_Product_ID()==UNSApps.getRefAsInt(UNSApps.PRD_KBA))
//			move.setM_WarehouseTo_ID(UNSApps.getRefAsInt(UNSApps.MP1_WHS));
//		else
//			move.setM_WarehouseTo_ID(UNSApps.getRefAsInt(UNSApps.LKU_WHS));
	}

	public static void setMovementLine(MUNSMP1FormDetail detail, MMovementLine line) {
		MUNSResource rsc = new MUNSResource(detail.getCtx(), detail.getUNS_Resource_ID(), detail.get_TrxName());
		for(MUNSResourceLocator rscLoc : rsc.getLocatorLines())
		{
			if (rscLoc.getM_Product_ID()!=detail.getMP1Form().getM_Product_ID())
				continue;
			
			line.setDescription("Automatic generated from Form Pengisian Keranjang Kelapa.");
			line.setM_Product_ID(rscLoc.getM_Product_ID());
			line.setM_Locator_ID(detail.getMP1Form().getM_Locator_ID());
			line.setM_LocatorTo_ID(detail.getM_Locator_ID());
			
			line.setMovementQty(detail.getFillIn().add(detail.getAdditionalQty()));
		}
		
		if (line.getM_Product_ID()==0 || line.getM_Locator_ID()==0)
			throw new AdempiereUserError("Not found Locator at Manufacturing Resource Locator");
	}

	public static MUNSMP1Form getByMove(org.compiere.model.MMovement move) {
		MUNSMP1Form retval = Query.get(move.getCtx(), UNSPPICModelFactory.getExtensionID(), 
				MUNSMP1Form.Table_Name, MUNSMP1Form.COLUMNNAME_M_Movement_ID + "=" + move.get_ID(), 
				move.get_TrxName()).firstOnly();
		
		return retval;
	}
}
