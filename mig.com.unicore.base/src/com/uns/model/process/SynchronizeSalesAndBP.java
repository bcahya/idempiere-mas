/**
 * 
 */
package com.uns.model.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.MBPartner;
import org.compiere.model.MUser;
//import org.compiere.model.PO;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

/**
 * @author root
 *
 */
public class SynchronizeSalesAndBP extends SvrProcess{

	/**
	 * 
	 */
	public SynchronizeSalesAndBP() {
	}

	@Override
	protected void prepare() {
		// nothing
	}

	@Override
	protected String doIt() throws Exception {
		List<MBPartner> listBPartner = initialBP();
		for(MBPartner bp : listBPartner)
		{
			MUser[] users = MUser.getOfBPartner(getCtx(), bp.get_ID(), get_TrxName());
			int appliedCount = 0;
			for(MUser user : users)
			{
				if(bp.getValue().equals(user.getName()))
				{
					appliedCount++;
					continue;
				}
				
				MBPartner bpartner = new MBPartner(getCtx());
//				PO.copyValues(bp, bpartner);
				bpartner.setAD_Org_ID(user.getAD_Org_ID());
				
				bpartner.setValue(user.getName());
				bpartner.setName(user.getRealName());
				bpartner.setName2(user.getRealName());
				bpartner.setIsEmployee(true);
				bpartner.setIsSalesRep(true);
				bpartner.setDescription("::Auto Create on Synchronize Sales Representative::");
				bpartner.setIsVendor(false);
				bpartner.setIsCustomer(false);
				bpartner.setIsManufacturer(false);
				bpartner.setIsDiscountPrinted(false);
				bpartner.saveEx(get_TrxName());
				user.setC_BPartner_ID(bpartner.get_ID());
				user.saveEx();
			}
			
			if(appliedCount == 0 && users.length > 0)
				bp.deleteEx(true);
		}
		return null;
	}
	
	private List<MBPartner> initialBP()
	{
		List<MBPartner> list = new ArrayList<MBPartner>();
		String sql = "SELECT * FROM C_BPartner WHERE IsEmployee = ? AND IsSalesRep = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = DB.prepareStatement(sql, get_TrxName());
			st.setString(1, "Y");
			st.setString(2, "Y");
			rs = st.executeQuery();
			while (rs.next()) {
				MBPartner bp = new MBPartner(getCtx(), rs, get_TrxName());
				list.add(bp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
