/**
 * 
 */
package com.uns.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MLocator;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author UNTA_Andy
 * 
 */
public class MUNSArmada extends X_UNS_Armada
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4402746809324980719L;
	public static final String DEFAULT_ARMADA = "Standart";

	/**
	 * @param ctx
	 * @param UNS_Armada_ID
	 * @param trxName
	 */
	public MUNSArmada(Properties ctx, int UNS_Armada_ID, String trxName)
	{
		super(ctx, UNS_Armada_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSArmada(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(newRecord)
			initArmadaLocator();
		
		return super.beforeSave(newRecord);
	}
	
	private void initArmadaLocator()
	{
		MWarehouse[] whs = MWarehouse.getForOrg(getCtx(), getAD_Org_ID());
		if(whs == null || whs.length == 0)
			throw new AdempiereException("NO Warehouse!");
		
		MWarehouse wh = whs[0];
		MLocator locator = new MLocator(wh, getValue());
		locator.setX(wh.getValue());
		locator.setY(wh.getName());
		locator.setZ(getValue());
		locator.saveEx();
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		return super.afterSave(newRecord, success);
	}
	
	/**
	 * 
	 * @param trxName
	 * @return
	 */
	public static MUNSArmada getDefault(String trxName)
	{
		MUNSArmada armada = null;
		String sql = "SELECT * FROM UNS_Armada WHERE Name = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		try
		{
			st = DB.prepareStatement(sql, trxName);
			st.setString(1, DEFAULT_ARMADA);
			rs = st.executeQuery();
			if(rs.next())
				armada = new MUNSArmada(Env.getCtx(), rs, trxName);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, st);
		}
		
		if(null == armada)
			throw new AdempiereException("No default Armada. Please create new armada with name Standart.");
		
		return armada;
	}
}
