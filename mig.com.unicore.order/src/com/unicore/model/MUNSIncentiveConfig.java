/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartnerProduct;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPayment;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.model.MProduct;

import com.unicore.base.model.MInvoice;
import com.unicore.base.model.MOrderLine;

/**
 * @author Burhani Adam
 *
 */
public class MUNSIncentiveConfig extends X_UNS_IncentiveConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3676329812508201872L;

	/**
	 * @param ctx
	 * @param UNS_IncentiveConfig_ID
	 * @param trxName
	 */
	public MUNSIncentiveConfig(Properties ctx, int UNS_IncentiveConfig_ID,
			String trxName) {
		super(ctx, UNS_IncentiveConfig_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSIncentiveConfig(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static ArrayList<MUNSIncentiveConfig> getSupervisor(Properties ctx, PO po, int SalesRep_ID,
			String IncentiveType, String trxName)
	{
		ArrayList<MUNSIncentiveConfig> configs = new ArrayList<>();
		
		String sql = "SELECT DISTINCT(c.UNS_IncentiveConfig_ID) FROM UNS_IncentiveConfig c"
				+ " INNER JOIN UNS_IncentiveConfigLine l ON l.UNS_IncentiveConfig_ID"
				+ " = c.UNS_IncentiveConfig_ID AND l.SalesRep_ID = ?"
				+ " AND (c.IncentiveType IS NULL OR c.IncentiveType = ?)";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = DB.prepareStatement(sql, trxName);
			stmt.setInt(1, SalesRep_ID);
			stmt.setString(2, IncentiveType);
			rs = stmt.executeQuery();
			while (rs.next()) {
				configs.add(new MUNSIncentiveConfig(ctx, rs.getInt(1), trxName));
			}
		} catch (SQLException e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return configs;
	}
	
	public static ArrayList<MUNSIncentiveConfigRequire> getRequire(Properties ctx,
			MUNSIncentiveConfig parent, String trxName)
	{
		ArrayList<MUNSIncentiveConfigRequire> requires = new ArrayList<>();
		String sql = "SELECT r.* FROM UNS_IncentiveConfigRequire r"
				+ " WHERE r.UNS_IncentiveConfig_ID = ?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = DB.prepareStatement(sql, trxName);
			stmt.setInt(1, parent.get_ID());
			rs = stmt.executeQuery();
			while (rs.next()) {
				requires.add(new MUNSIncentiveConfigRequire(ctx, rs, trxName));
			}
		} catch (SQLException e) {
			throw new AdempiereException(e.getMessage());
		}
		
		return requires;
	}
	
	public String runSupervisorTree(PO po, String IncentiveType, boolean isContinuous,
			int SalesRep_ID, BigDecimal sourceAmt, BigDecimal subAmt, 
			int Reference_ID, MUNSIncentive incentive)
	{
		boolean isMatch = false;
		BigDecimal baseAmt = isUseBaseAmt() ? sourceAmt : subAmt;
		if(baseAmt.compareTo(Env.ZERO) == 0)
			return "";
		
		if(po != null)
		{
			if(po instanceof MInvoiceLine)
				isMatch = checkSupOnInvoiceLine((MInvoiceLine) po);
			else if(po instanceof MOrderLine)
				isMatch = checkSupOnOrderLine((MOrderLine) po);
			else if(po instanceof MInvoice)
				isMatch = checkSupOnInvoice((MInvoice) po);
			else if(po instanceof MPayment)
				isMatch = checkSupOnPayment((MPayment) po);
		}
		
		if(isMatch)
		{
			MUNSAchievedIncentiveLine ref = new MUNSAchievedIncentiveLine(getCtx(), Reference_ID, get_TrxName());
			BigDecimal amt = Env.ZERO;
			if(isIncentiveAmount())
				amt = getIncentiveAmt();
			else
				amt = (getIncentiveAmt().multiply(baseAmt)).divide(Env.ONEHUNDRED, RoundingMode.HALF_DOWN);
					
			MUNSAchievedIncentiveLine.getCreate(
					getCtx(), po, ref.getSchemaType(), ref.getDateTrx(), getSalesRep_ID(), SalesRep_ID, baseAmt, amt, 
					ref.getSourceType(), ref.getDocumentNo(), incentive, 0, 0, get_TrxName());
			
			if(isContinuous)
			{
				for(int i = 0; i <= 100; i++)
				{
					ArrayList<MUNSIncentiveConfig> configs = 
							MUNSIncentiveConfig.getSupervisor(getCtx(), po, getSalesRep_ID(), 
									ref.getUNS_Incentive().getIncentiveType(), get_TrxName());
					
					if(configs.size() == 0)
						break;
					for(MUNSIncentiveConfig config : configs)
					{
						config.runSupervisorTree(null, ref.getUNS_Incentive().getIncentiveType(),
								false, getSalesRep_ID(), sourceAmt, amt, Reference_ID, incentive);
					}
				}
			}
		}
		
		return "";
	}
	
	private boolean checkSupOnInvoiceLine(MInvoiceLine line)
	{
		boolean match = false;
		MProduct product = MProduct.get(getCtx(), line.getM_Product_ID());
		ArrayList<MUNSIncentiveConfigRequire> requires = 
				MUNSIncentiveConfig.getRequire(getCtx(), this, get_TrxName());
		if(requires.size() == 0)
			return true;
		for(MUNSIncentiveConfigRequire require : requires)
		{
			if(require.getM_Product_ID() > 0 && require.getM_Product_ID() == product.get_ID())
				match = true;
			
			if(require.getM_Product_Category_ID() > 0 && 
					require.getM_Product_Category_ID() == product.getM_Product_Category_ID())
				match = true;
			
			if(require.getVendor_ID() > 0)
			{
				MBPartnerProduct[] pp = MBPartnerProduct.getOfProduct(getCtx(), product.get_ID(), get_TrxName());
				for(int i = 0; i < pp.length; i++)
				{
					if(pp[i].getC_BPartner_ID() ==  require.getVendor_ID())
					{
						match = true;
						break;
					}
				}
			}
			
			if(match)
				break;
		}
		
		return match;
	}
	
	private boolean checkSupOnOrderLine(MOrderLine line)
	{
		boolean match = false;
		MProduct product = MProduct.get(getCtx(), line.getM_Product_ID());
		for(MUNSIncentiveConfigRequire require :
			MUNSIncentiveConfig.getRequire(getCtx(), this, get_TrxName()))
		{
			if(require.getM_Product_ID() > 0 && require.getM_Product_ID() == product.get_ID())
				match = true;
			
			if(require.getM_Product_Category_ID() > 0 && 
					require.getM_Product_Category_ID() == product.getM_Product_Category_ID())
				match = true;
			
			if(require.getVendor_ID() > 0)
			{
				MBPartnerProduct[] pp = MBPartnerProduct.getOfProduct(getCtx(), product.get_ID(), get_TrxName());
				for(int i = 0; i < pp.length; i++)
				{
					if(pp[i].getC_BPartner_ID() ==  require.getVendor_ID())
					{
						match = true;
						break;
					}
				}
			}
			
			if(match)
				break;
		}
		
		return match;
	}
	
	private boolean checkSupOnInvoice(MInvoice inv)
	{
		//TODO
		//kedepannya akan diterapkan juga kondisi supervisor dengan tipe invoice
		return true;
	}
	
	private boolean checkSupOnPayment(MPayment pay)
	{
		//TODO
		//kedepannya akan diterapkan juga kondisi supervisor dengan tipe payment
		return true;
	}
}