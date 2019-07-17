/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author root
 *
 */
public class SimplePrice {

	private BigDecimal m_priceList		= Env.ZERO;
	private BigDecimal m_priceLimit		= Env.ZERO;
	private BigDecimal m_priceActual	= Env.ZERO;
	private int m_M_Product_ID			= 0;
	private int m_C_BPartner_ID			= 0;
	private String m_TrxName			= null;
	private boolean m_calculated		= false;
//	private int m_precission			= 0;
	
	public SimplePrice(int C_BPartner_ID, int M_Product_ID, String trxName)
	{
		this.m_C_BPartner_ID	= C_BPartner_ID;
		this.m_M_Product_ID		= M_Product_ID;
		this.m_TrxName			= trxName;
	}
	
//	public void setPrecission(int precission)
//	{
//		this.m_precission = precission;
//	}
	
	public void set_TrxName(String trxName)
	{
		this.m_TrxName = trxName;
	}
	
	public void setC_BPartner_ID(int C_BPartner_ID)
	{
		this.m_C_BPartner_ID = C_BPartner_ID;
	}
	
	public SimplePrice()
	{
		super();
	}
	
	public BigDecimal getPriceList()
	{
		if(!m_calculated)
			calculatePrice();
		return m_priceList;
	}
	
	public BigDecimal getPriceLimit()
	{
		if(!m_calculated)
			calculatePrice();
		
		return m_priceLimit;
	}
	
	public BigDecimal getPriceActual()
	{
		if(!m_calculated)
			calculatePrice();
		
		return m_priceActual;
	}
	
	private void calculatePrice()
	{
		StringBuilder sb = new StringBuilder("SELECT COALESCE(PriceList, 0), ")
			.append("COALESCE(PriceLimit, 0), COALESCE(PriceActual, 0) ")
			.append("FROM C_OrderLine WHERE M_Product_ID =? AND ")
			.append(" C_Order_ID IN (SELECT C_Order_ID FROM C_Order WHERE C_BPartner_ID = ? ")
			.append(" AND IsActive = 'Y' AND (DocStatus = 'CO' OR DocStatus = 'CL') ")
			.append("AND C_DocType_ID = (Select C_DocType_ID FROM C_DocType WHERE ")
			.append("DocSubTypeSO = 'CO')) ORDER BY DateOrdered DESC");
		
		String sql = sb.toString();
//		BigDecimal divider = Env.ONE;
		
		PreparedStatement st	= null;
		ResultSet rs			= null;
		
		try
		{
			st = DB.prepareStatement(sql, m_TrxName);
			st.setInt(1, m_M_Product_ID);
			st.setInt(2, m_C_BPartner_ID);
			rs = st.executeQuery();
			if(rs.next())
			{
				if(m_priceList.compareTo(Env.ZERO) == 0
						&& m_priceLimit.compareTo(Env.ZERO) == 0
						&& m_priceActual.compareTo(Env.ZERO) == 0)
				{
					m_priceList		= rs.getBigDecimal(1);
					m_priceLimit	= rs.getBigDecimal(2);
					m_priceActual	= rs.getBigDecimal(3);
//					divider			= rs.getBigDecimal(4);
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, st);
			m_calculated = true;
		}
		
//		if(divider.intValue() <= 1)
//			return;
		
//		m_priceList		= m_priceList.divide(divider, m_precission, RoundingMode.HALF_DOWN);
//		m_priceLimit	= m_priceLimit.divide(divider, m_precission, RoundingMode.HALF_DOWN);
//		m_priceActual	= m_priceActual.divide(divider, m_precission, RoundingMode.HALF_DOWN);
	}

}
