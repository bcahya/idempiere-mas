package com.uns.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.compiere.util.DB;

public class MUNSBiayaBongkarMuat extends X_UNS_Biaya_Bongkar_Muat{

	/**
	 * @author (Ahong)
	 */
	private static final long serialVersionUID = 1L;
	
	/** TypeBongkar AD_Reference_ID=1000025 */
	public static final int TYPEBONGKAR_AD_Reference_ID=1000025;
	/** Bongkar Tanpa Alat = BTA */
	public static final String TYPEBONGKAR_BongkarTanpaAlat = "BTA";
	/** Bongkar Dengan Forklift = BDF */
	public static final String TYPEBONGKAR_BongkarDenganForklift = "BDF";
	/** Bongkar Dengan Crane = BDC */
	public static final String TYPEBONGKAR_BongkarDenganCrane = "BDC";
	/** Disusun / Dibongkar = DSB */
	public static final String TYPEBONGKAR_DisusunDibongkar = "DSB";

	public MUNSBiayaBongkarMuat(Properties ctx, int UNS_Biaya_Bongkar_Muat_ID,
			String trxName) {
		super(ctx, UNS_Biaya_Bongkar_Muat_ID, trxName);
	}

	public MUNSBiayaBongkarMuat(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	
	/**
	 * 
	 * @param M_Product_ID
	 * @param C_UOM_ID
	 * @param AD_Org_ID
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getBiayaBongkarMuat (
			String handlingType, boolean isLoading, 
			int M_Product_ID, int C_UOM_ID, int AD_Org_ID, String trxName) 
	{	
		String columnToSelect = "";
		if (TYPEBONGKAR_BongkarDenganCrane.equals(handlingType))
			columnToSelect = isLoading? COLUMNNAME_LoadingWithCrane : COLUMNNAME_UnloadingWithCrane;
		else if (TYPEBONGKAR_BongkarDenganForklift.equals(handlingType))
			columnToSelect = isLoading? COLUMNNAME_LoadingWithForklift : COLUMNNAME_UnloadingWithForklift;
		else if (TYPEBONGKAR_BongkarTanpaAlat.equals(handlingType))
			columnToSelect = isLoading? COLUMNNAME_LoadingManual : COLUMNNAME_UnloadingManual;
		else 
			columnToSelect = isLoading? COLUMNNAME_LoadingManual : COLUMNNAME_UnloadingManual;

		BigDecimal cost = BigDecimal.ZERO;
		String sql = "SELECT " + columnToSelect + " FROM UNS_Biaya_Bongkar_Muat "
				+ "WHERE M_Product_ID =? AND C_UOM_ID =? AND AD_Org_ID = ?";
		cost = DB.getSQLValueBD(trxName, sql, M_Product_ID, C_UOM_ID, AD_Org_ID);
		
		if(null == cost || cost.signum()<= 0)
			cost = getBiayaBongkarMuat(columnToSelect, M_Product_ID, C_UOM_ID, trxName);
		return cost;
	}

	/**
	 * 
	 * @param m_product_id
	 * @param c_uom_id
	 * @return
	 */
	private static BigDecimal getBiayaBongkarMuat 
						(String columnToSelect, int productID, int uomID, String trxName){
		BigDecimal costBongkarMuat = BigDecimal.ZERO;
		try{
			String sql =
					" SELECT " + columnToSelect + " FROM " + Table_Name + " WHERE " ;
			String whereProduct = "";
			String whereUOM = "";
			if (productID > 0) {
				whereProduct = COLUMNNAME_M_Product_ID + "=" + productID;
			}
			if (uomID > 0) {
				whereUOM = COLUMNNAME_C_UOM_ID+"=" + uomID;
			}
			sql += whereProduct + (productID>0? " AND " : "") + whereUOM + (uomID>0? " AND " : "") +  
					" ValidFrom = (SELECT MAX(ValidFrom) FROM "+ Table_Name + " WHERE " +
					whereProduct + (productID>0? " AND " : "") + whereUOM + ")";
			
			PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				costBongkarMuat = rs.getBigDecimal(1);
			}
			DB.close(rs);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return costBongkarMuat;
	}
}
