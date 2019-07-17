/**
 * 
 */
package com.uns.importer;

import java.util.Hashtable;
import java.util.Properties;

import jxl.Sheet;
import com.uns.model.process.SimpleImportXLS;

import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MLocation;
import org.compiere.model.PO;
import org.compiere.util.DB;

/**
 * @author AzHaidar
 *
 */
public class BPLocationImporterValidation implements ImporterValidation {

	protected Properties 	m_ctx = null;
	protected String		m_trxName = null;
	protected Sheet			m_sheet	= null;
	protected Hashtable<String, PO> m_PORefMap = null;
	
	public static final String ADDRESS_1 = "Address_1";
	//public static final String ADDRESS_2 = "E";
	public static final String COUNTRY = "Country";
	public static final String PROVINCE = "Province";
	public static final String CITY = "City";
	public static final String DISTRICT = "District";
	public static final String VILLAGE = "Village";
	public static final String PHONE_1 = "Phone_1";
	public static final String PHONE_2 = "Phone_2";
	public static final String FAX = "Fax";
	public static final String ZIP = "Zip";
	
	/**
	 * 
	 */
	public BPLocationImporterValidation(Properties ctx, Sheet sheet, String trxName) {
		m_ctx = ctx;
		m_trxName = trxName;
		m_sheet = sheet;
	}

	@Override
	public void setTrxName(String trxName) {
		m_trxName = trxName;
		
	}
	
	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#beforeSave(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
	 */
	@Override
	public String beforeSave(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{	
		MBPartnerLocation BPLoc = (MBPartnerLocation) po;
		if (po.get_ID() == 0)
		{
			Object oNPWP = freeColVals.get("E");
			
			String sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE Value='" + BPLoc.getBPartnerSearchKey() + "'";
			
			if (oNPWP != null) 
			{
				Object oTaxSerialNo = freeColVals.get("F");
				
				if (!((String)oNPWP).trim().isEmpty())
				{
					sql = "SELECT C_BPartner_ID FROM C_BPartner WHERE ";

					sql += "TRIM(TaxID)='" + ((String)oNPWP).trim() + "'";
				
					if (oTaxSerialNo != null && !((String)oTaxSerialNo).trim().isEmpty())
						sql += " OR Trim(TaxSerialNo)='" + ((String) freeColVals.get("F")).trim() + "'";
				}
				else if (oTaxSerialNo != null && !((String)oTaxSerialNo).trim().isEmpty())
					sql += "SELECT C_BPartner_ID FROM C_BPartner WHERE Trim(TaxSerialNo)='" 
						+ ((String) freeColVals.get("F")).trim() + "'";
			}
			
			int BP_ID = DB.getSQLValueEx(m_trxName, sql);
			
			if (BP_ID <= 0)
				return "Cannot found Business Partner with value=" + BPLoc.getBPartnerSearchKey() + " / NPWP=" + oNPWP;
			
			BPLoc.setC_BPartner_ID(BP_ID);
		}
		
		Object oAddress1 = freeColVals.get(ADDRESS_1);
		if (oAddress1 == null || ((String) oAddress1).trim().isEmpty())
			return SimpleImportXLS.DONT_SAVE;
		
		MLocation loc = new MLocation(m_ctx, BPLoc.getC_Location_ID(), m_trxName);
		
		//if (loc == null)
		//	loc = new MLocation(m_ctx, 0, m_trxName);
		
		loc.setAddress1((String) oAddress1);
		
		//loc.set_ValueOfColumn(MLocation.COLUMNNAME_AD_Org_ID, po.getAD_Org_ID());
		
		//if (freeColVals.get(ADDRESS_2) != null)
		//	loc.set_ValueOfColumn(MLocation.COLUMNNAME_Address2, freeColVals.get(ADDRESS_2));
		
		Object oCity = freeColVals.get(CITY);
		if (oCity != null)
		{
			if (!((String) oCity).trim().isEmpty()) {
				String sql = "SELECT C_City_ID FROM C_City Where UPPER(Name) LIKE '%" + oCity + "'";
				int city_ID = DB.getSQLValue(m_trxName, sql);
				
				if (city_ID <= 0) {
					city_ID = 0;
					loc.set_ValueOfColumn(MLocation.COLUMNNAME_City, freeColVals.get(CITY));
				}
				
				loc.setC_City_ID(city_ID);
			}
		}
		
		int country_ID = 0;
		
		Object oRegion = freeColVals.get(PROVINCE);
		if (oRegion != null)
		{
			if (!((String) oRegion).isEmpty()) {
				String sql = "SELECT C_Region_ID FROM C_Region Where UPPER(Name) LIKE '%" + oRegion + "'";
				int region_ID = DB.getSQLValue(m_trxName, sql);
				
				if (region_ID <= 0) {
					region_ID = 0;
					loc.set_ValueOfColumn(MLocation.COLUMNNAME_RegionName, freeColVals.get(PROVINCE));
				}
				
				loc.setC_Region_ID(region_ID);
			}
		}
		
		Object oCountry = freeColVals.get(COUNTRY);
		if (oCountry == null)
		{
			if (loc.getC_Region_ID() > 0)
				country_ID = loc.getC_Region().getC_Country_ID();
		}
		else {
			loc.setC_Country_ID((Integer) oCountry);
		}
		
		if (country_ID > 0)
			loc.setC_Country_ID(country_ID);
		
		Object oDistrict = freeColVals.get(DISTRICT);
		if (oDistrict != null)
		{
			if (!((String) oDistrict).trim().isEmpty()) 
			{
				
				String sql = "SELECT UNS_District_ID FROM UNS_District Where UPPER(Name) LIKE '%" + oDistrict + "' ";
				
				if (loc.getC_City_ID() > 0)
					sql += " AND C_City_ID=" + loc.getC_City_ID();
				else  if (loc.getC_Region_ID() > 0)
					sql += " AND C_Region_ID=" + loc.getC_Region_ID();
				
				int District_ID = DB.getSQLValue(m_trxName, sql);
				
				if (District_ID > 0) {
					loc.setUNS_District_ID(District_ID);
				}
			}
		}
		
		Object oVillage = freeColVals.get(VILLAGE);
		if (oVillage != null)
		{
			if (!((String) oVillage).trim().isEmpty()) 
			{
				
				String sql = "SELECT UNS_KelurahanDesa_ID FROM KelurahanDesa Where UPPER(Name) LIKE '%" + oVillage + "' ";
				
				if (loc.getUNS_District_ID() > 0)
					sql += " AND UNS_District_ID=" + loc.getUNS_District_ID();
				else if (loc.getC_City_ID() > 0)
					sql += " AND C_City_ID=" + loc.getC_City_ID();
				else if (loc.getC_Region_ID() > 0)
					sql += " AND C_Region_ID=" + loc.getC_Region_ID();
				
				int District_ID = DB.getSQLValue(m_trxName, sql);
				
				if (District_ID > 0) {
					loc.setUNS_KelurahanDesa_ID(District_ID);
				}
			}
		}
		
		if (freeColVals.get(ZIP) != null && !((String)freeColVals.get(ZIP)).trim().isEmpty())
			loc.setPostal((String) freeColVals.get(ZIP));
		
		loc.saveEx();
		
		if (loc.get_ID() == 0)
			return SimpleImportXLS.DONT_SAVE;
		
		BPLoc.setC_Location_ID(loc.get_ID());
		//BPLoc.set_ValueNoCheck(MBPartnerLocation.COLUMNNAME_Phone, freeColVals.get(PHONE_1));
		//BPLoc.set_ValueNoCheck(MBPartnerLocation.COLUMNNAME_Phone2, freeColVals.get(PHONE_2));
		//BPLoc.set_ValueNoCheck(MBPartnerLocation.COLUMNNAME_Fax, freeColVals.get(FAX));
		
		//BPLoc.saveEx();
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveRow(java.util.Hashtable, org.compiere.model.PO, java.util.Hashtable)
	 */
	@Override
	public String afterSaveRow(Hashtable<String, PO> poRefMap, PO po, Hashtable<String, Object> freeColVals, int currentRow) 
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#afterSaveAllRow(java.util.Hashtable, org.compiere.model.PO[])
	 */
	@Override
	public String afterSaveAllRow(Hashtable<String, PO> poRefMap, PO[] pos) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uns.importer.ImporterValidation#getPOReferenceMap()
	 */
	@Override
	public Hashtable<String, PO> getPOReferenceMap() {
		return null;
	}

}
