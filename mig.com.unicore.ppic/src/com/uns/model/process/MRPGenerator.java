/**
 * 
 */
package com.uns.model.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProductBOM;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import com.uns.model.MUNSMPSHeader;
import com.uns.model.MUNSResource;

/**
 * @author AzHaidar
 * @Untasoft 
 */
public class MRPGenerator extends SvrProcess {
	
	private Properties m_ctx = null;
	private String m_trxName = null;
	private MUNSMPSHeader m_MPSHeader = null;
	int m_MaxWeekOfPeriods = 0;
	
	/**
	 * 
	 */
	public MRPGenerator() {
		
	}
	
	public MRPGenerator(MUNSMPSHeader mpsHeader)
	{
		m_ctx = mpsHeader.getCtx();
		m_trxName = mpsHeader.get_TrxName();
		m_MPSHeader = mpsHeader;
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#prepare()
	 */
	@Override
	protected void prepare() 
	{	
		m_ctx = getCtx();
		m_trxName = get_TrxName();
		m_MPSHeader= new MUNSMPSHeader(m_ctx, getRecord_ID(), m_trxName);
		//if(m_MPSHeader.getPrevMPS_ID() > 0)
		//	m_prevMPS = (MUNSMPSHeader)m_MPSHeader.getPrevMPS();
	}

	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	@Override
	protected String doIt() throws Exception 
	{
		String msg = null;
		// Delete previous generated MRP data.
		String sql = "DELETE FROM UNS_MRPWeekly WHERE UNS_MRP_ID IN " +
				" (SELECT mrp.UNS_MRP_ID FROM UNS_MRP mrp WHERE mrp.UNS_MPSHeader_ID=" + m_MPSHeader.get_ID() + ")";
		DB.executeUpdateEx(sql, m_trxName);
		
		sql = "DELETE FROM UNS_MRP WHERE UNS_MPSHeader_ID=" + m_MPSHeader.get_ID();
		DB.executeUpdateEx(sql, m_trxName);
		
		MUNSResource resource = m_MPSHeader.getResource();
		
		sql = "SELECT * FROM GenerateInitialMRPData (?, ?, ?, ?, ?)";
		msg = DB.getSQLValueString(get_TrxName(), sql, 
								   getAD_Client_ID(), 
								   m_MPSHeader.getAD_Org_ID(), 
								   getAD_User_ID(), 
								   m_MPSHeader.get_ID(), 
								   resource.get_ID());

		if(null != msg && msg.equals("Completed."))
		{
			m_MPSHeader.setGenerate_MRP("Y");
			m_MPSHeader.save();
		}
		return msg;
	}
	
	/* (non-Javadoc)
	 * @see org.compiere.process.SvrProcess#doIt()
	 */
	protected String doIt_OLD() throws Exception 
	{
		String msg = null;
		// Delete previous generated MRP data.
		String sql = "DELETE FROM UNS_MRPWeekly WHERE UNS_MRP_ID IN " +
				" (SELECT mrp.UNS_MRP_ID FROM UNS_MRP mrp WHERE mrp.UNS_MPSHeader_ID=" + m_MPSHeader.get_ID() + ")";
		DB.executeUpdateEx(sql, m_trxName);
		
		sql = "DELETE FROM UNS_MRP WHERE UNS_MPSHeader_ID=" + m_MPSHeader.get_ID();
		DB.executeUpdateEx(sql, m_trxName);
		
		sql = "SELECT DISTINCT bom.M_Product_ID, bom.M_ProductBOM_ID, bom.BOMQty " +
				"FROM M_Product_BOM bom, UNS_Resource_InOut rio, M_Product p " +
				"WHERE bom.M_Product_ID=rio.M_Product_ID AND bom.M_ProductBOM_ID=p.M_Product_ID " +
				"  AND bom.BOMType=? AND p.IsPurchased='Y' ";

		MUNSResource rsc = m_MPSHeader.getResource();
		if (rsc.getResourceType().equals(MUNSResource.RESOURCETYPE_4WorkStation))
			sql += " AND rio.UNS_Resource_ID = ?";
		else if (rsc.getResourceType().equals(MUNSResource.RESOURCETYPE_3WorkCenter))
			sql += " AND rio.UNS_Resource_ID IN (SELECT ws.UNS_Resource_ID FROM UNS_Resource ws " +
					"	WHERE ws.ResourceParent_ID IN (SELECT wc.UNS_Resource_ID FROM UNS_Resource wc " +
					"		WHERE wc.UNS_Resource_ID=?))";
		else if (rsc.getResourceType().equals(MUNSResource.RESOURCETYPE_2ProductionLine))
			sql += " AND rio.UNS_Resource_ID IN (SELECT ws.UNS_Resource_ID FROM UNS_Resource ws " +
					"	WHERE ws.ResourceParent_ID IN (SELECT wc.UNS_Resource_ID FROM UNS_Resource wc " +
					"		WHERE wc.ResourceParent_ID IN (SELECT pl.UNS_Resource_ID FROM UNS_Resource pl " +
					"			WHERE pl.UNS_Resource_ID=?)))";
		else if (rsc.getResourceType().equals(MUNSResource.RESOURCETYPE_1Plant))
			sql += " AND rio.UNS_Resource_ID IN (SELECT ws.UNS_Resource_ID FROM UNS_Resource ws " +
					"	WHERE ws.ResourceParent_ID IN (SELECT wc.UNS_Resource_ID FROM UNS_Resource wc " +
					"		WHERE wc.ResourceParent_ID IN (SELECT pl.UNS_Resource_ID FROM UNS_Resource pl " +
					"			WHERE pl.ResourceParent_ID=?)))";
		
		PreparedStatement stmt = DB.prepareStatement(sql, m_trxName);
		ResultSet rs = null;
		try {
			stmt.setString(1, MProductBOM.BOMTYPE_StandardPart);
			stmt.setInt(2, rsc.get_ID());
			rs = stmt.executeQuery();
			while (rs.next())
			{
				
			}
		}
		catch (SQLException ex) {
			throw new AdempiereException (ex.getMessage());
		}
		
		if(null == msg)
		{
			m_MPSHeader.setGenerate_MRP("Y");
			m_MPSHeader.save();
		}
		return msg;
	}	
}
