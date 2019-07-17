package com.uns.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

public class MUNSMP1FormDetail extends X_UNS_MP1Form_Detail	 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3855582211326988622L;
	public boolean m_isFormModification = false;
	private MUNSResource m_resource;
	private MUNSMP1Form m_form;

	public static final String SHELLER = "Sheller";
	public static final String PARRER = "Parrer";
	
	public MUNSMP1FormDetail(Properties ctx, int UNS_MP1Form_Detail_ID,
			String trxName) {
		super(ctx, UNS_MP1Form_Detail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSMP1FormDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static MUNSMP1FormDetail init(MUNSMP1Form header, MUNSResource workStation) 
	{
		MUNSMP1FormDetail detail = new MUNSMP1FormDetail(header.getCtx(), 0, 
				header.get_TrxName());
		
		detail.setClientOrg(header);
		detail.setUNS_MP1Form_ID(header.get_ID());
		detail.setUNS_Resource_ID(workStation.get_ID());
		
		List<MUNSResourceWorkerLine> lines = workStation.getWorkerListOf(workStation);
		for (MUNSResourceWorkerLine woLine : lines)
		{
			MUNSJobRole jobRole = new MUNSJobRole(header.getCtx(), woLine.getUNS_Job_Role_ID(), 
				header.get_TrxName());
			if (!woLine.isActive())
			{
				continue;
			} 
			else if (jobRole.getName().equals(SHELLER) && detail.getUNS_Sheller_ID()==0)
			{
				detail.setUNS_Sheller_ID(woLine.getLabor_ID());
			}
			else if (jobRole.getName().equals(PARRER) && detail.getUNS_Parrer1_ID()==0)
			{
				detail.setUNS_Parrer1_ID(woLine.getLabor_ID());	
			}
			else if (jobRole.getName().equals(PARRER) && detail.getUNS_Parrer2_ID()==0)
			{
				detail.setUNS_Parrer2_ID(woLine.getLabor_ID());
			}
			else if (jobRole.getName().equals(PARRER) && detail.getUNS_Parrer3_ID()==0)
			{
				detail.setUNS_Parrer3_ID(woLine.getLabor_ID());
			}
			else if (jobRole.getName().equals(SHELLER) && detail.getUNS_Sheller_ID()!=0
					&& !woLine.isAdditional())
			{
				throw new AdempiereException("Too many sheller at WS-"+workStation.getName());
			}
			else if (jobRole.getName().equals(PARRER) && detail.getUNS_Parrer1_ID()!=0 
					&& detail.getUNS_Parrer2_ID()!=0 && detail.getUNS_Parrer3_ID()!=0
					&& !woLine.isAdditional())
			{
				throw new AdempiereException("Too many parrer at WS-"+workStation.getName());
			}
//			else if (detail.getUNS_Sheller_ID()==0 || detail.getUNS_Parrer1_ID()==0)
//			{
//				throw new AdempiereException("Few worker at WS-"+workStation.getName());
//			}
//			else
//			{
//				throw new AdempiereException("Error at Worker Configuration WS-"+workStation.getName());
//			}
		}
		
		for(MUNSResourceLocator rLoc : workStation.getLocatorLines()){
			if (rLoc.getM_Product_ID()==header.getM_Product_ID()){
				detail.setM_Locator_ID(rLoc.getM_Locator_ID());
				break;
			}
		}
		
		//Login as MPD or LKU , get starting stock from Last Stock
//		String sDate = new SimpleDateFormat("yyyy-MM-dd").format(header.getProductionDate());
//		if (Env.getAD_Org_ID(header.getCtx())==UNSApps.getRefAsInt(UNSApps.MPD_ORG)
//				|| Env.getAD_Org_ID(header.getCtx())==UNSApps.getRefAsInt(UNSApps.LKU_ORG)) 
//		{
//			String sql = "SELECT SUM(MovementQTY) FROM M_Transaction WHERE MovementDate < " +
//					"'" + sDate + "' AND M_Product_ID = " + header.getM_Product_ID() + 
//					" AND M_Locator_ID = "+ detail.getM_Locator_ID();
//			
//			BigDecimal dbValue = DB.getSQLValueBD(header.get_TrxName(), sql);
//			
//			if (dbValue==null)
//				detail.setStartingStock(Env.ZERO);
//			else
//				detail.setStartingStock(dbValue);
//		}
		
		
		return detail;
	}

	public MUNSResource getResource() {
		if (m_resource == null)
			m_resource = new MUNSResource(getCtx(), getUNS_Resource_ID(), get_TrxName());
		
		return m_resource;
	}

	public MUNSMP1Form getMP1Form() {
		if (m_form == null)
			m_form = (MUNSMP1Form) getUNS_MP1Form();
		
		return m_form;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		setProductionQty(getStartingStock().add(getAdditionalQty()).subtract(getEndingStock()));
		
		return super.beforeSave(newRecord);
	}
	
	
}
