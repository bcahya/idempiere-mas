/**
 * 
 */
package com.uns.base.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import com.uns.util.ErrorMsg;

import com.uns.model.MUNSPSSOAllocation;
import com.uns.model.MUNSSER;



/**
 * @author menjangan
 *
 */
public class MOrderLine extends org.compiere.model.MOrderLine {
	
	private MOrder m_parent = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param ctx
	 * @param C_OrderLine_ID
	 * @param trxName
	 */
	public MOrderLine(Properties ctx, int C_OrderLine_ID, String trxName) {
		super(ctx, C_OrderLine_ID, trxName);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MOrderLine(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
	
	@Override
	public MOrder getParent()
	{
		if (null == m_parent)
			m_parent = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		
		return m_parent;
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		String msg =
				MUNSSER.cekProductAvalability(this);
		if (msg != null)
		{
			ErrorMsg.setErrorMsg(getCtx(), "SaveError", msg);
			return false;
		}
		
		//FIXME SET Estimation at 20FT
		//set Estimation(20FT) container
//		BigDecimal EstimasiContainer = BigDecimal.ZERO;
//		EstimasiContainer = getQtyEntered().divide(getUOMInCtn());
//		setEstimationCtn(EstimasiContainer);
		
		//MAttributeSetInstance.initAttributeValuesFrom(
		//		this, COLUMNNAME_M_Product_ID, COLUMNNAME_M_AttributeSetInstance_ID, get_TrxName());
		
		return super.beforeSave(newRecord);
	}
	
	/**
	 * 
	 */
	public MUNSPSSOAllocation[] getAllocation() {
		ArrayList<MUNSPSSOAllocation> list = new ArrayList<MUNSPSSOAllocation>();

		String sql = "SELECT UNS_PSSOAllocation_ID FROM UNS_PSSOAllocation "
				+ "WHERE C_OrderLine_ID = "+getC_OrderLine_ID();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MUNSPSSOAllocation(getCtx(), rs.getInt(1),
						get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (SQLException ex) {
			throw new AdempiereException("Unable to load SO Allocation.",
					ex);
		} finally {
			DB.close(rs, pstmt);
		}

		MUNSPSSOAllocation[] retValue = new MUNSPSSOAllocation[list
				.size()];
		list.toArray(retValue);
		return retValue;
	}

}
