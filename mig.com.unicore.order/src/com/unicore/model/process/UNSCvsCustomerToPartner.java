package com.unicore.model.process;


import java.sql.Timestamp;
import java.util.Hashtable;
import org.compiere.model.MBPGroup;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import com.unicore.model.MUNSCvsCustomer;
import com.unicore.model.MUNSCvsCustomerLocation;

public class UNSCvsCustomerToPartner extends SvrProcess {
	
	/**
	 * 
	 */
	private MUNSCvsCustomer m_model = null;
	private Hashtable<String, Object> m_mapValues = null;
	
	public UNSCvsCustomerToPartner() 
	{
		super();
	}

	@Override
	protected void prepare() 
	{
		ProcessInfoParameter[] pis = getParameter();
		m_mapValues = new Hashtable<>();
		for (int i=0; i<pis.length; i++)
		{
			m_mapValues.put(pis[i].getParameterName(), pis[i].getParameter());
		}
		m_model = new MUNSCvsCustomer(getCtx(), getRecord_ID(), get_TrxName());
	}

	@Override
	protected String doIt() throws Exception 
	{
		if (m_model == null)
		{
			return "Can't Load Model";
		}
		else if (m_model.getCreateBusinessPartner().equals("Y"))
		{
			return "Business Partner has ben created";
		}
		
		MBPartner partner = new MBPartner(getCtx());
		partner.setValue((String) m_mapValues.get(
				MUNSCvsCustomer.COLUMNNAME_Value));
		partner.setName((String) m_mapValues.get(
				MUNSCvsCustomer.COLUMNNAME_Name));
		partner.setAD_Org_ID(new Integer(m_mapValues.get(
				MUNSCvsCustomer.COLUMNNAME_AD_Org_ID).toString()));
		partner.setIsCustomer(true);
		partner.setBirthday((Timestamp) m_mapValues.get(
				MUNSCvsCustomer.COLUMNNAME_Birthday));
		partner.setBPGroup(new MBPGroup(getCtx(), new Integer(m_mapValues.get(
				MUNSCvsCustomer.COLUMNNAME_C_BP_Group_ID).toString()), 
				get_TrxName()));
		partner.setDescription((String) m_mapValues.get(
				MUNSCvsCustomer.COLUMNNAME_Description));
		partner.setOwnerBirthday((Timestamp) m_mapValues.get(
				MUNSCvsCustomer.COLUMNNAME_OwnerBirthday));
		partner.setOwnerIDCardNo((String) m_mapValues.get(
				MUNSCvsCustomer.COLUMNNAME_OwnerIDCardNo));
		partner.setOwnerName((String) m_mapValues.get(
				MUNSCvsCustomer.COLUMNNAME_OwnerName));	
		partner.saveEx();
		
		m_model.setCreateBusinessPartner("Y");
		m_model.setC_BPartner_ID(partner.get_ID());
		m_model.saveEx();
		return createLocation(partner);
	}
	
	private String createLocation(MBPartner partner)
	{
		if (null == partner)
			return null;
		
		MUNSCvsCustomerLocation[] locations = m_model.getLines(true);
		for (int i=0; i<locations.length; i++)
		{
			MBPartnerLocation loc = new MBPartnerLocation(partner);
			loc.setIsBillTo(true);
			loc.setIsPayFrom(true);
			loc.setIsRemitTo(true);
			loc.setIsShipTo(true);
			loc.setBPartnerSearchKey(locations[i].getBPartnerSearchKey());
			loc.setValue(locations[i].getValue());
			loc.setC_Location_ID(locations[i].getC_Location_ID());
			loc.setC_SalesRegion_ID(locations[i].getC_SalesRegion_ID());
			loc.setUNS_Cluster_ID(locations[i].getUNS_Cluster_ID());
			loc.setUNS_Pasar_ID(locations[i].getUNS_Pasar_ID());
			loc.setUNS_Rayon_ID(locations[i].getUNS_Rayon_ID());
			loc.setLatitude(locations[i].getLatitude());
			loc.setLongitude(locations[i].getLongitude());
			loc.saveEx();
		}
		return null;
	}
}
