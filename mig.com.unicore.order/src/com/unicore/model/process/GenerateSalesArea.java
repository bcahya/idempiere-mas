/**
 * 
 */
package com.unicore.model.process;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.IProcessUI;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.unicore.model.MUNSCustomerSalesPricing;

/**
 * @author Toriq
 * @see www.untasoft.com
 */
public class GenerateSalesArea extends SvrProcess {
	
	private int m_AD_User_ID=0;
	private int m_M_PriceList_ID=0;
	private int m_UNS_Rayon_ID=0;
	private int m_AD_Org_ID=0;
	private IProcessUI m_ui = null;
	private String m_priceListName = null;
	private String m_salesName = null;
	private String message = null;
	
	protected void prepare(){
		ProcessInfoParameter[] para = getParameter();
		for (int i =0 ; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("AD_User_ID"))
				m_AD_User_ID = para[i].getParameterAsInt();
			else if (name.equals("M_PriceList_ID"))
				m_M_PriceList_ID = para[i].getParameterAsInt();
			else if (name.equals("UNS_Rayon_ID"))
				m_UNS_Rayon_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_Org_ID"))
				m_AD_Org_ID = para[i].getParameterAsInt();
			else 
				log.log(Level.SEVERE, "Unknown Parameter : "+ name);
		}
		
		m_priceListName = DB.getSQLValueString(get_TrxName(), "SELECT Name FROM M_PriceList WHERE M_PriceList_ID = ?", m_M_PriceList_ID);
		m_salesName = DB.getSQLValueString(get_TrxName(), "SELECT Name FROM AD_User WHERE AD_User_ID = ?", m_AD_User_ID);
	}
	
	protected String doIt() throws Exception {

		m_ui = Env.getProcessUI(getCtx());
		String sql ="SELECT cp.C_Bpartner_ID,cl.C_Bpartner_Location_ID, cp.name FROM C_Bpartner cp "
				+ "INNER JOIN C_Bpartner_Location cl on cp.C_Bpartner_ID=cl.C_Bpartner_ID "
				+ "WHERE cl.UNS_Rayon_ID=? and cp.IsCustomer='Y' AND cp.C_Bpartner_ID not in(SELECT sp.C_Bpartner_ID "
				+ "FROM UNS_Customer_SalesPricing sp WHERE sp.AD_User_ID=? AND M_PriceList_ID = ?)";
		ResultSet rs=null;
		PreparedStatement pstmt= null;
		int count = 0;
		
		try {
			pstmt = DB.prepareStatement(sql,get_TrxName());
			pstmt.setInt(1, m_UNS_Rayon_ID);
			pstmt.setInt(2, m_AD_User_ID);
			pstmt.setInt(3, m_M_PriceList_ID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MUNSCustomerSalesPricing sp=new MUNSCustomerSalesPricing(getCtx(), 0, get_TrxName());
				sp.setAD_Org_ID(m_AD_Org_ID);
				sp.setAD_User_ID(m_AD_User_ID);
				sp.setC_BPartner_ID(rs.getInt(1));
				sp.setC_BPartner_Location_ID(rs.getInt(2));
				sp.setUNS_Rayon_ID(m_UNS_Rayon_ID);
				sp.setM_PriceList_ID(m_M_PriceList_ID);
				sp.saveEx();
				message = "Created record [ " + m_salesName + " | " + m_priceListName + " | " + rs.getString(3);
				m_ui.statusUpdate(message);
				count ++;
			}
		} 
		catch (SQLException e)
		{
			throw new AdempiereException(e.getMessage());
		} catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		finally {
			DB.close(rs, pstmt);
		}
		message = count + " Record Created.";
		return message;
	}

		/**
	 * 
	 */
	public GenerateSalesArea() {
		super();
	}

}
