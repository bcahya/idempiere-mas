/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.base.AbstractProductPricing;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trace;

import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.google.maps.model.DistanceMatrixRow;
import com.unicore.model.MUNSExpPriceAdd;
import com.unicore.model.MUNSExpPriceDetail;
import com.unicore.model.MUNSExpPriceDirection;
import com.unicore.util.GeocodingUtils;
import com.unicore.util.UNSLocation;
import com.unicore.util.UNSSimpleDirection;


/**
 *  Product Price Calculations
 *
 *  @author Jorg Janke
 *  @version $Id: MProductPricing.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MProductPricing extends AbstractProductPricing
{
	
	private String trxName=null;

	/**
	 * New constructor to be used with the ProductPriceFactories
	 */
	public MProductPricing() {}
	
	/**
	 * 	Old Constructor to keep backward
	 *  compatibility
	 * 	@param M_Product_ID product
	 * 	@param C_BPartner_ID partner
	 * 	@param Qty quantity
	 * 	@param isSOTrx SO or PO
	 *  @param trxName the transaction
	 */
	public MProductPricing (int M_Product_ID, int C_BPartner_ID, 
			BigDecimal Qty, boolean isSOTrx, String trxName)
	{
		setInitialValues(M_Product_ID, C_BPartner_ID, Qty, isSOTrx, trxName);
	}
    
    /**
	 * Product pricing for expedition
	 * @param origin_ID
	 * @param destination_ID
	 */
	public MProductPricing (MLocation origin, MLocation destination)
	{
		this (0, 0, Env.ZERO, true, 0);
		this.m_origin = origin;
		this.m_destination = destination;
		this.m_isExpeditionPrice = true;
	}


	/**
	 * 	Constructor
	 * 	@param M_Product_ID product
	 * 	@param C_BPartner_ID partner
	 * 	@param Qty quantity
	 * 	@param isSOTrx SO or PO
	 *  @deprecated Use constructor with explicit trxName parameter
	 */
	public MProductPricing (int M_Product_ID, int C_BPartner_ID, 
		BigDecimal Qty, boolean isSOTrx)
	{
		this(M_Product_ID,C_BPartner_ID,Qty,isSOTrx,null);
	}	//	MProductPricing
	
	@Override
	public void setInitialValues(int M_Product_ID, int C_BPartner_ID, BigDecimal qty, boolean isSOTrx, String trxName) {
		super.setInitialValues(M_Product_ID, C_BPartner_ID, qty, isSOTrx, trxName);
		checkVendorBreak();
	}
	
	private void checkVendorBreak() {
		int thereAreVendorBreakRecords = DB.getSQLValue(trxName, 
				"SELECT count(M_Product_ID) FROM M_ProductPriceVendorBreak WHERE M_Product_ID=? AND (C_BPartner_ID=? OR C_BPartner_ID is NULL)",
				m_M_Product_ID, m_C_BPartner_ID);
		m_useVendorBreak = thereAreVendorBreakRecords > 0;
	}
    
    /**
	 * 	Constructor
	 * 	@param M_Product_ID product
	 * 	@param C_BPartner_ID partner
	 * 	@param Qty quantity
	 * 	@param isSOTrx SO or PO
	 */
	public MProductPricing (int M_Product_ID, int C_BPartner_ID, 
		BigDecimal Qty, boolean isSOTrx, int C_BPartner_Location_ID)
	{
		if (!m_isExpeditionPrice)
		{
			m_M_Product_ID = M_Product_ID;
			m_C_BPartner_ID = C_BPartner_ID;
			m_C_BPartner_Location_ID = C_BPartner_Location_ID;
			if (Qty != null && Env.ZERO.compareTo(Qty) != 0)
				m_Qty = Qty;
			m_isSOTrx = isSOTrx;
			int thereAreVendorBreakRecords = DB.getSQLValue(null, 
					"SELECT count(M_Product_ID) FROM M_ProductPriceVendorBreak"
					+ " WHERE M_Product_ID=? AND C_BPartner_ID=?",
					m_M_Product_ID, m_C_BPartner_ID);
			m_useVendorBreak = thereAreVendorBreakRecords > 0;
		}
		
		MAcctSchema[] acctSchem = MAcctSchema.getClientAcctSchema(
				Env.getCtx(), Env.getAD_Client_ID(Env.getCtx()));
		m_isIgnoredUnprocessedPrice = acctSchem[0].isIgnoredUnprocessedPrice();
	}	//	MProductPricing
	
	public boolean isIgnoredUnprocessedPrice()
	{
		return this.m_isIgnoredUnprocessedPrice;
	}

	private boolean 	m_isIgnoredUnprocessedPrice = false;
	private int 		m_M_Product_ID = 0;
	private int 		m_C_BPartner_ID = 0;
	private BigDecimal 	m_Qty = Env.ONE;
	private boolean		m_isSOTrx = true;
	//
	private int			m_M_PriceList_ID = 0;

	private int 		m_M_PriceList_Version_ID = 0;
	private Timestamp 	m_PriceDate;	
	/** Precision -1 = no rounding		*/
	private int		 	m_precision = -1;
	
	
	private boolean 	m_calculated = false;
	private boolean 	m_vendorbreak = false;
	private Boolean		m_found = null;
	
	private BigDecimal 	m_PriceList = Env.ZERO;
	private BigDecimal 	m_PriceStd = Env.ZERO;
	private BigDecimal 	m_PriceLimit = Env.ZERO;
	private int 		m_C_Currency_ID = 0;
	private boolean		m_enforcePriceLimit = false;
	private int 		m_C_UOM_ID = 0;
	private boolean		m_discountSchema = false;
	private boolean		m_isTaxIncluded = false;

	private boolean 	m_useVendorBreak = false;
    private int 		m_M_Product_Category_ID = 0;
	
	//UniCore Customization
	private int			m_C_BPartner_Location_ID = 0;
	private MLocation	m_origin = null;
	private MLocation	m_destination = null;
	private boolean		m_isExpeditionPrice = false;
	private BigDecimal	m_distance = Env.ZERO;
	private BigDecimal	m_duration = Env.ZERO;
	private String		m_msg = null;
	private final BigDecimal		CIBU = new BigDecimal(1000);
	private final BigDecimal		NAMPULUH = new BigDecimal(60);
	
	/**	Logger			*/
	protected CLogger	log = CLogger.getCLogger(getClass());
	
	
	/**
	 * 	Calculate Price
	 * 	@return true if calculated
	 */
	public boolean calculatePrice ()
	{
		if (m_M_Product_ID == 0 && !m_isExpeditionPrice
			|| (m_found != null && !m_found.booleanValue()))	//	previously not found
			return false;
		
        if(m_M_PriceList_Version_ID > 0 && !isValidVersion())
		{
			m_M_PriceList_Version_ID = 0;
		}
		
		if (m_isExpeditionPrice)
		{
			m_calculated = calculateExpeditionPrice();
		}
		
		if (m_useVendorBreak) {
			//	Price List Version known - vendor break
			if (!m_calculated) {
				m_calculated = calculatePLV_VB ();
				if (m_calculated)
					m_vendorbreak = true;
			}
			//	Price List known - vendor break
			if (!m_calculated) {
				m_calculated = calculatePL_VB();
				if (m_calculated)
					m_vendorbreak = true;
			}
			//	Base Price List used - vendor break
			if (!m_calculated) {
				m_calculated = calculateBPL_VB();
				if (m_calculated)
					m_vendorbreak = true;
			}
		}
		
		//	Price List Version known
		if (!m_calculated)
			m_calculated = calculatePLV ();
		//	Price List known
		if (!m_calculated)
			m_calculated = calculatePL();
		//	Base Price List used
		if (!m_calculated)
			m_calculated = calculateBPL();
		//	Set UOM, Prod.Category
		if (!m_calculated)
			setBaseInfo();
		//	User based Discount
		if (m_calculated && !m_vendorbreak)
			calculateDiscount();
        //UniCore Customization
		if(m_calculated)
			initialAdditionalPrice();
		
		setPrecision();		//	from Price List
		//
		m_found = new Boolean (m_calculated);
		return m_calculated;
	}	//	calculatePrice

	/**
	 * 	Calculate Price based on Price List Version
	 * 	@return true if calculated
	 */
	private boolean calculatePLV()
	{
		if (m_M_Product_ID == 0 || m_M_PriceList_Version_ID == 0)
			return false;
		//
		String sql = "SELECT bomPriceStd(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceStd,"	//	1
			+ " bomPriceList(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceList,"		//	2
			+ " bomPriceLimit(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceLimit,"	//	3
			+ " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,"	//	4..7
			+ " pl.EnforcePriceLimit, pl.IsTaxIncluded "	// 8..9
			+ "FROM M_Product p"
			+ " INNER JOIN M_ProductPrice pp ON (p.M_Product_ID=pp.M_Product_ID)"
			+ " INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID)"
			+ " INNER JOIN M_Pricelist pl ON (pv.M_PriceList_ID=pl.M_PriceList_ID) "
			+ "WHERE pv.IsActive='Y'"
			+ " AND pp.IsActive='Y'"
			+ " AND p.M_Product_ID=?"				//	#1
			+ " AND pv.M_PriceList_Version_ID=?";	//	#2
		m_calculated = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, m_M_PriceList_Version_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				//	Prices
				m_PriceStd = rs.getBigDecimal(1);
				if (rs.wasNull())
					m_PriceStd = Env.ZERO;
				m_PriceList = rs.getBigDecimal(2);
				if (rs.wasNull())
					m_PriceList = Env.ZERO;
				m_PriceLimit = rs.getBigDecimal(3);
				if (rs.wasNull())
					m_PriceLimit = Env.ZERO;
				//
				m_C_UOM_ID = rs.getInt(4);
				m_C_Currency_ID = rs.getInt(6);
				m_M_Product_Category_ID = rs.getInt(7);
				m_enforcePriceLimit = "Y".equals(rs.getString(8));
				m_isTaxIncluded = "Y".equals(rs.getString(9));
				//
				if (log.isLoggable(Level.FINE)) log.fine("M_PriceList_Version_ID=" + m_M_PriceList_Version_ID + " - " + m_PriceStd);
				m_calculated = true;
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e); 
			m_calculated = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return m_calculated;
	}	//	calculatePLV

	/**
	 * 	Calculate Price based on Price List
	 * 	@return true if calculated
	 */
	private boolean calculatePL()
	{
		if (m_M_Product_ID == 0)
			return false;

		//	Get Price List
		/**
		if (m_M_PriceList_ID == 0)
		{
			String sql = "SELECT M_PriceList_ID, IsTaxIncluded "
				+ "FROM M_PriceList pl"
				+ " INNER JOIN M_Product p ON (pl.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE M_Product_ID=? "
				+ "ORDER BY IsDefault DESC";
			PreparedStatement pstmt = null;
			try
			{
				pstmt = DB.prepareStatement(sql);
				pstmt.setInt(1, m_M_Product_ID);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())
				{
					m_M_PriceList_ID = rs.getInt(1);
					m_isTaxIncluded = "Y".equals(rs.getString(2));
				}
				rs.close();
				pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "calculatePL (PL)", e);
			}
			finally
			{
				try
				{
					if (pstmt != null)
						pstmt.close ();
				}
				catch (Exception e)
				{}
				pstmt = null;
			}
		}
		/** **/
		if (m_M_PriceList_ID == 0)
		{
			log.log(Level.SEVERE, "No PriceList");
			Trace.printStack();
			return false;
		}

		//	Get Prices for Price List
		String sql = "SELECT bomPriceStd(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceStd,"	//	1
			+ " bomPriceList(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceList,"		//	2
			+ " bomPriceLimit(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceLimit,"	//	3
			+ " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,pl.EnforcePriceLimit "	// 4..8
			+ " FROM M_Product p"
			+ " INNER JOIN M_ProductPrice pp ON (p.M_Product_ID=pp.M_Product_ID)"
			+ " INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID ";
		if (m_isIgnoredUnprocessedPrice)
		{
			sql += " AND pv.IsApproved = 'Y' AND pv.DocStatus IN ('CO','CL'))";
		}
		else
		{
			sql += ")";
		}
		sql 
			+= " INNER JOIN M_Pricelist pl ON (pv.M_PriceList_ID=pl.M_PriceList_ID) "
			+ " WHERE pv.IsActive='Y'"
			+ " AND pp.IsActive='Y'"
			+ " AND p.M_Product_ID=?"				//	#1
			+ " AND pv.M_PriceList_ID=?"			//	#2
			+ " ORDER BY pv.ValidFrom DESC";
		m_calculated = false;
		if (m_PriceDate == null)
			m_PriceDate = new Timestamp (System.currentTimeMillis());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, m_M_PriceList_ID);
			rs = pstmt.executeQuery();
			while (!m_calculated && rs.next())
			{
				Timestamp plDate = rs.getTimestamp(5);
				//	we have the price list
				//	if order date is after or equal PriceList validFrom
				if (plDate == null || !m_PriceDate.before(plDate))
				{
					//	Prices
					m_PriceStd = rs.getBigDecimal (1);
					if (rs.wasNull ())
						m_PriceStd = Env.ZERO;
					m_PriceList = rs.getBigDecimal (2);
					if (rs.wasNull ())
						m_PriceList = Env.ZERO;
					m_PriceLimit = rs.getBigDecimal (3);
					if (rs.wasNull ())
						m_PriceLimit = Env.ZERO;
						//
					m_C_UOM_ID = rs.getInt (4);
					m_C_Currency_ID = rs.getInt (6);
					m_M_Product_Category_ID = rs.getInt(7);
					m_enforcePriceLimit = "Y".equals(rs.getString(8));
					//
					if (log.isLoggable(Level.FINE)) log.fine("M_PriceList_ID=" + m_M_PriceList_ID 
						+ "(" + plDate + ")" + " - " + m_PriceStd);
					m_calculated = true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			m_calculated = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		if (!m_calculated)
			log.finer("Not found (PL)");
		return m_calculated;
	}	//	calculatePL

	/**
	 * 	Calculate Price based on Base Price List
	 * 	@return true if calculated
	 */
	private boolean calculateBPL()
	{
		if (m_M_Product_ID == 0 || m_M_PriceList_ID == 0)
			return false;
		//
		String sql = "SELECT bomPriceStd(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceStd,"	//	1
			+ " bomPriceList(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceList,"		//	2
			+ " bomPriceLimit(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceLimit,"	//	3
			+ " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,"	//	4..7
			+ " pl.EnforcePriceLimit, pl.IsTaxIncluded "	// 8..9
			+ " FROM M_Product p"
			+ " INNER JOIN M_ProductPrice pp ON (p.M_Product_ID=pp.M_Product_ID)"
			+ " INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID ";
		if (m_isIgnoredUnprocessedPrice)
		{
			sql += " AND pv.IsApproved = 'Y' AND pv.DocStatus IN ('CO','CL'))";
		}
		else
		{
			sql += ")";
		}
		sql 
			+= " INNER JOIN M_Pricelist bpl ON (pv.M_PriceList_ID=bpl.M_PriceList_ID)"
			+ " INNER JOIN M_Pricelist pl ON (bpl.M_PriceList_ID=pl.BasePriceList_ID) "
			+ " WHERE pv.IsActive='Y'"
			+ " AND pp.IsActive='Y'"
			+ " AND p.M_Product_ID=?"				//	#1
			+ " AND pl.M_PriceList_ID=?"			//	#2
			+ " ORDER BY pv.ValidFrom DESC";
		m_calculated = false;
		if (m_PriceDate == null)
			m_PriceDate = new Timestamp (System.currentTimeMillis());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, m_M_PriceList_ID);
			rs = pstmt.executeQuery();
			while (!m_calculated && rs.next())
			{
				Timestamp plDate = rs.getTimestamp(5);
				//	we have the price list
				//	if order date is after or equal PriceList validFrom
				if (plDate == null || !m_PriceDate.before(plDate))
				{
					//	Prices
					m_PriceStd = rs.getBigDecimal (1);
					if (rs.wasNull ())
						m_PriceStd = Env.ZERO;
					m_PriceList = rs.getBigDecimal (2);
					if (rs.wasNull ())
						m_PriceList = Env.ZERO;
					m_PriceLimit = rs.getBigDecimal (3);
					if (rs.wasNull ())
						m_PriceLimit = Env.ZERO;
						//
					m_C_UOM_ID = rs.getInt (4);
					m_C_Currency_ID = rs.getInt (6);
					m_M_Product_Category_ID = rs.getInt(7);
					m_enforcePriceLimit = "Y".equals(rs.getString(8));
					m_isTaxIncluded = "Y".equals(rs.getString(9));
					//
					if (log.isLoggable(Level.FINE)) log.fine("M_PriceList_ID=" + m_M_PriceList_ID 
						+ "(" + plDate + ")" + " - " + m_PriceStd);
					m_calculated = true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			m_calculated = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		if (!m_calculated)
			log.finer("Not found (BPL)");
		return m_calculated;
	}	//	calculateBPL

	/**
	 * 	Calculate Price based on Price List Version and Vendor Break
	 * 	@return true if calculated
	 */
	private boolean calculatePLV_VB()
	{
		if (m_M_Product_ID == 0 || m_M_PriceList_Version_ID == 0)
			return false;
		//
		String sql = "SELECT pp.PriceStd,"	//	1
			+ " pp.PriceList,"		//	2
			+ " pp.PriceLimit,"	//	3
			+ " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,"	//	4..7
			+ " pl.EnforcePriceLimit, pl.IsTaxIncluded "	// 8..9
			+ "FROM M_Product p"
			+ " INNER JOIN M_ProductPriceVendorBreak pp ON (p.M_Product_ID=pp.M_Product_ID)"
			+ " INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID)"
			+ " INNER JOIN M_Pricelist pl ON (pv.M_PriceList_ID=pl.M_PriceList_ID) "
			+ "WHERE pv.IsActive='Y'"
			+ " AND pp.IsActive='Y'"
			+ " AND p.M_Product_ID=?"				//	#1
			+ " AND pv.M_PriceList_Version_ID=?"	//	#2
			+ " AND pp.C_BPartner_ID=?"				//	#3
			+ " AND ?>=pp.BreakValue"				//  #4
			+ " ORDER BY BreakValue DESC";
		m_calculated = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, m_M_PriceList_Version_ID);
			pstmt.setInt(3, m_C_BPartner_ID);
			pstmt.setBigDecimal(4, m_Qty);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				//	Prices
				m_PriceStd = rs.getBigDecimal(1);
				if (rs.wasNull())
					m_PriceStd = Env.ZERO;
				m_PriceList = rs.getBigDecimal(2);
				if (rs.wasNull())
					m_PriceList = Env.ZERO;
				m_PriceLimit = rs.getBigDecimal(3);
				if (rs.wasNull())
					m_PriceLimit = Env.ZERO;
				//
				m_C_UOM_ID = rs.getInt(4);
				m_C_Currency_ID = rs.getInt(6);
				m_M_Product_Category_ID = rs.getInt(7);
				m_enforcePriceLimit = "Y".equals(rs.getString(8));
				m_isTaxIncluded = "Y".equals(rs.getString(9));
				//
				if (log.isLoggable(Level.FINE)) log.fine("M_PriceList_Version_ID=" + m_M_PriceList_Version_ID + " - " + m_PriceStd);
				m_calculated = true;
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e); 
			m_calculated = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return m_calculated;
	}	//	calculatePLV_VB

	/**
	 * 	Calculate Price based on P rice List and Vendor break
	 * 	@return true if calculated
	 */
	private boolean calculatePL_VB()
	{
		if (m_M_Product_ID == 0)
			return false;

		//	Get Price List
		/**
		if (m_M_PriceList_ID == 0)
		{
			String sql = "SELECT M_PriceList_ID, IsTaxIncluded "
				+ "FROM M_PriceList pl"
				+ " INNER JOIN M_Product p ON (pl.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE M_Product_ID=? "
				+ "ORDER BY IsDefault DESC";
			PreparedStatement pstmt = null;
			try
			{
				pstmt = DB.prepareStatement(sql);
				pstmt.setInt(1, m_M_Product_ID);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next())
				{
					m_M_PriceList_ID = rs.getInt(1);
					m_isTaxIncluded = "Y".equals(rs.getString(2));
				}
				rs.close();
				pstmt.close();
				pstmt = null;
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "calculatePL (PL)", e);
			}
			finally
			{
				try
				{
					if (pstmt != null)
						pstmt.close ();
				}
				catch (Exception e)
				{}
				pstmt = null;
			}
		}
		/** **/
		if (m_M_PriceList_ID == 0)
		{
			log.log(Level.SEVERE, "No PriceList");
			Trace.printStack();
			return false;
		}

		//	Get Prices for Price List
		String sql = "SELECT pp.PriceStd,"	//	1
			+ " pp.PriceList,"		//	2
			+ " pp.PriceLimit,"	//	3
			+ " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,pl.EnforcePriceLimit "	// 4..8
			+ "FROM M_Product p"
			+ " INNER JOIN M_ProductPriceVendorBreak pp ON (p.M_Product_ID=pp.M_Product_ID)"
			+ " INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID ";
		if (m_isIgnoredUnprocessedPrice)
		{
			sql += " AND pv.IsApproved = 'Y' AND pv.DocStatus IN ('CO'))";
		}
		else
		{
			sql += ")";
		}
		sql 
			+= " INNER JOIN M_Pricelist pl ON (pv.M_PriceList_ID=pl.M_PriceList_ID) "
			+ "WHERE pv.IsActive='Y'"
			+ " AND pp.IsActive='Y'"
			+ " AND p.M_Product_ID=?"				//	#1
			+ " AND pv.M_PriceList_ID=?"			//	#2
			+ " AND pp.C_BPartner_ID=?"				//	#3
			+ " AND ?>=pp.BreakValue"				//  #4
			+ " ORDER BY pv.ValidFrom DESC, BreakValue DESC";
		m_calculated = false;
		if (m_PriceDate == null)
			m_PriceDate = new Timestamp (System.currentTimeMillis());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, m_M_PriceList_ID);
			pstmt.setInt(3, m_C_BPartner_ID);
			pstmt.setBigDecimal(4, m_Qty);
			rs = pstmt.executeQuery();
			while (!m_calculated && rs.next())
			{
				Timestamp plDate = rs.getTimestamp(5);
				//	we have the price list
				//	if order date is after or equal PriceList validFrom
				if (plDate == null || !m_PriceDate.before(plDate))
				{
					//	Prices
					m_PriceStd = rs.getBigDecimal (1);
					if (rs.wasNull ())
						m_PriceStd = Env.ZERO;
					m_PriceList = rs.getBigDecimal (2);
					if (rs.wasNull ())
						m_PriceList = Env.ZERO;
					m_PriceLimit = rs.getBigDecimal (3);
					if (rs.wasNull ())
						m_PriceLimit = Env.ZERO;
						//
					m_C_UOM_ID = rs.getInt (4);
					m_C_Currency_ID = rs.getInt (6);
					m_M_Product_Category_ID = rs.getInt(7);
					m_enforcePriceLimit = "Y".equals(rs.getString(8));
					//
					if (log.isLoggable(Level.FINE)) log.fine("M_PriceList_ID=" + m_M_PriceList_ID 
						+ "(" + plDate + ")" + " - " + m_PriceStd);
					m_calculated = true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			m_calculated = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		if (!m_calculated)
			log.finer("Not found (PL)");
		return m_calculated;
	}	//	calculatePL_VB

	/**
	 * 	Calculate Price based on Base Price List and Vendor Break
	 * 	@return true if calculated
	 */
	private boolean calculateBPL_VB()
	{
		if (m_M_Product_ID == 0 || m_M_PriceList_ID == 0)
			return false;
		//
		String sql = "SELECT pp.PriceStd,"	//	1
			+ " pp.PriceList,"		//	2
			+ " pp.PriceLimit,"	//	3
			+ " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,"	//	4..7
			+ " pl.EnforcePriceLimit, pl.IsTaxIncluded "	// 8..9
			+ "	FROM M_Product p"
			+ " INNER JOIN M_ProductPriceVendorBreak pp ON (p.M_Product_ID=pp.M_Product_ID)"
			+ " INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID ";
		if (m_isIgnoredUnprocessedPrice)
		{
			sql += " AND pv.IsApproved = 'Y' AND pv.DocStatus IN ('CO'))";
		}
		else
		{
			sql += ")";
		}
		sql 
			+= " INNER JOIN M_Pricelist bpl ON (pv.M_PriceList_ID=bpl.M_PriceList_ID)"
			+ " INNER JOIN M_Pricelist pl ON (bpl.M_PriceList_ID=pl.BasePriceList_ID) "
			+ "	WHERE pv.IsActive='Y'"
			+ " AND pp.IsActive='Y'"
			+ " AND p.M_Product_ID=?"				//	#1
			+ " AND pl.M_PriceList_ID=?"			//	#2
			+ " AND pp.C_BPartner_ID=?"				//	#3
			+ " AND ?>=pp.BreakValue"				//  #4
			+ " ORDER BY pv.ValidFrom DESC, BreakValue DESC";
		m_calculated = false;
		if (m_PriceDate == null)
			m_PriceDate = new Timestamp (System.currentTimeMillis());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, m_M_Product_ID);
			pstmt.setInt(2, m_M_PriceList_ID);
			pstmt.setInt(3, m_C_BPartner_ID);
			pstmt.setBigDecimal(4, m_Qty);
			rs = pstmt.executeQuery();
			while (!m_calculated && rs.next())
			{
				Timestamp plDate = rs.getTimestamp(5);
				//	we have the price list
				//	if order date is after or equal PriceList validFrom
				if (plDate == null || !m_PriceDate.before(plDate))
				{
					//	Prices
					m_PriceStd = rs.getBigDecimal (1);
					if (rs.wasNull ())
						m_PriceStd = Env.ZERO;
					m_PriceList = rs.getBigDecimal (2);
					if (rs.wasNull ())
						m_PriceList = Env.ZERO;
					m_PriceLimit = rs.getBigDecimal (3);
					if (rs.wasNull ())
						m_PriceLimit = Env.ZERO;
						//
					m_C_UOM_ID = rs.getInt (4);
					m_C_Currency_ID = rs.getInt (6);
					m_M_Product_Category_ID = rs.getInt(7);
					m_enforcePriceLimit = "Y".equals(rs.getString(8));
					m_isTaxIncluded = "Y".equals(rs.getString(9));
					//
					if (log.isLoggable(Level.FINE)) log.fine("M_PriceList_ID=" + m_M_PriceList_ID 
						+ "(" + plDate + ")" + " - " + m_PriceStd);
					m_calculated = true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			m_calculated = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		if (!m_calculated)
			log.finer("Not found (BPL)");
		return m_calculated;
	}	//	calculateBPL_VB

	/**
	 * 	Set Base Info (UOM)
	 */
	private void setBaseInfo()
	{
		if (m_M_Product_ID == 0)
			return;
		
		MProduct product = MProduct.get(Env.getCtx(), m_M_Product_ID);
		if (product != null) {
			 m_C_UOM_ID = product.getC_UOM_ID();
			 m_M_Product_Category_ID = product.getM_Product_Category_ID();
		}
		
	}	//	setBaseInfo

	/**
	 * 	Is Tax Included
	 *	@return tax included
	 */
	public boolean isTaxIncluded()
	{
		return m_isTaxIncluded;
	}	//	isTaxIncluded
	
	
	/**************************************************************************
	 * 	Calculate (Business Partner) Discount
	 */
	private void calculateDiscount()
	{
		m_discountSchema = false;
		if (m_C_BPartner_ID == 0 || m_M_Product_ID == 0)
			return;
		
		int M_DiscountSchema_ID = 0;
		BigDecimal FlatDiscount = null;
		String sql = "SELECT COALESCE(p.M_DiscountSchema_ID,g.M_DiscountSchema_ID),"
			+ " COALESCE(p.PO_DiscountSchema_ID,g.PO_DiscountSchema_ID), p.FlatDiscount "
			+ "FROM C_BPartner p"
			+ " INNER JOIN C_BP_Group g ON (p.C_BP_Group_ID=g.C_BP_Group_ID) "
			+ "WHERE p.C_BPartner_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, m_C_BPartner_ID);
			rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				M_DiscountSchema_ID = rs.getInt(m_isSOTrx ? 1 : 2);
				FlatDiscount = rs.getBigDecimal(3);
				if (FlatDiscount == null)
					FlatDiscount = Env.ZERO;
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		//	No Discount Schema
		if (M_DiscountSchema_ID == 0)
			return;
		
		MDiscountSchema sd = MDiscountSchema.get(Env.getCtx(), M_DiscountSchema_ID);	//	not correct
		if (sd.get_ID() == 0 || MDiscountSchema.CUMULATIVELEVEL_Line.equals(sd.getCumulativeLevel()))
			return;
		//
		m_discountSchema = true;		
		m_PriceStd = sd.calculatePrice(m_Qty, m_PriceStd, m_M_Product_ID, 
			m_M_Product_Category_ID, FlatDiscount);
		
	}	//	calculateDiscount

	
	/**************************************************************************
	 * 	Calculate Discount Percentage based on Standard/List Price
	 * 	@return Discount
	 */
	public BigDecimal getDiscount()
	{
		BigDecimal Discount = Env.ZERO;
		if (m_PriceList.intValue() != 0)
			Discount = BigDecimal.valueOf((m_PriceList.doubleValue() - m_PriceStd.doubleValue())
				/ m_PriceList.doubleValue() * 100.0);
		if (Discount.scale() > 2)
			Discount = Discount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return Discount;
	}	//	getDiscount


	/**************************************************************************
	 * 	Get Product ID
	 *	@return id
	 */
	public int getM_Product_ID()
	{
		return m_M_Product_ID;
	}
	
	/**
	 * 	Get PriceList ID
	 *	@return pl
	 */
	public int getM_PriceList_ID()
	{
		return m_M_PriceList_ID;
	}	//	getM_PriceList_ID
	
    
	/**
	 * 	Set PriceList
	 *	@param M_PriceList_ID pl
	 */
	public void setM_PriceList_ID( int M_PriceList_ID)
	{
		m_M_PriceList_ID = M_PriceList_ID;
		m_calculated = false;
	}	//	setM_PriceList_ID
	
	/**
	 * 	Get PriceList Version
	 *	@return plv
	 */
	public int getM_PriceList_Version_ID()
	{
		return m_M_PriceList_Version_ID;
	}	//	getM_PriceList_Version_ID
	
	/**
	 * 	Set PriceList Version
	 *	@param M_PriceList_Version_ID plv
	 */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
	{
		m_M_PriceList_Version_ID = M_PriceList_Version_ID;
		m_calculated = false;
	}	//	setM_PriceList_Version_ID
	
    /**
	 * 	Get Price Date
	 *	@return date
	 */
	public Timestamp getPriceDate()
	{
		return m_PriceDate;
	}	//	getPriceDate
	
	/**
	 * 	Set Price Date
	 *	@param priceDate date
	 */
	public void setPriceDate(Timestamp priceDate)
	{
		m_PriceDate = priceDate;
		m_calculated = false;
	}	//	setPriceDate
	
	/**
	 * 	Set Precision.
	 */
	private void setPrecision ()
	{
		if (m_M_PriceList_ID != 0)
			m_precision = MPriceList.getPricePrecision(Env.getCtx(), getM_PriceList_ID());
	}	//	setPrecision
	
	/**
	 * 	Get Precision
	 *	@return precision - -1 = no rounding
	 */
	public int getPrecision()
	{
		return m_precision;
	}	//	getPrecision
	
	/**
	 * 	Round
	 *	@param bd number
	 *	@return rounded number
	 */
	private BigDecimal round (BigDecimal bd)
	{
		if (m_precision >= 0	//	-1 = no rounding
			&& bd.scale() > m_precision)
			return bd.setScale(m_precision, BigDecimal.ROUND_HALF_UP);
		return bd;
	}	//	round
	
	/**************************************************************************
	 * 	Get C_UOM_ID
	 *	@return uom
	 */
	public int getC_UOM_ID()
	{
		if (!m_calculated)
			calculatePrice();
		return m_C_UOM_ID;
	}
	
	/**
	 * 	Get Price List
	 *	@return list
	 */
	public BigDecimal getPriceList()
	{
		if (!m_calculated)
			calculatePrice();
		return round(m_PriceList);
	}
	/**
	 * 	Get Price Std
	 *	@return std
	 */
	public BigDecimal getPriceStd()
	{
		if (!m_calculated)
			calculatePrice();
		return round(m_PriceStd);
	}
	/**
	 * 	Get Price Limit
	 *	@return limit
	 */
	public BigDecimal getPriceLimit()
	{
		if (!m_calculated)
			calculatePrice();
		return round(m_PriceLimit);
	}
	/**
	 * 	Get Price List Currency
	 *	@return currency
	 */
	public int getC_Currency_ID()
	{
		if (!m_calculated)
			calculatePrice();
		return m_C_Currency_ID;
	}
	/**
	 * 	Is Price List enforded?
	 *	@return enforce limit
	 */
	public boolean isEnforcePriceLimit()
	{
		if (!m_calculated)
			calculatePrice();
		return m_enforcePriceLimit;
	}	//	isEnforcePriceLimit

	/**
	 * 	Is a DiscountSchema active?
	 *	@return active Discount Schema
	 */
	public boolean isDiscountSchema()
	{
		return m_discountSchema || m_useVendorBreak;	
	}	//	isDiscountSchema
	
	/**
	 * 	Is the Price Calculated (i.e. found)?
	 *	@return calculated
	 */
	public boolean isCalculated()
	{
		return m_calculated;
	}	//	isCalculated
	
	@Override
	public void setOrderLine(I_C_OrderLine orderLine, String trxName) {
		super.setOrderLine(orderLine, trxName);
		checkVendorBreak();
	}
	
	@Override
	public void setInvoiceLine(I_C_InvoiceLine invoiceLine, String trxName) {
		super.setInvoiceLine(invoiceLine, trxName);
		checkVendorBreak();
	}
	
	@Override
	public void setProjectLine(I_C_ProjectLine projectLine, String trxName) {
		super.setProjectLine(projectLine, trxName);
		checkVendorBreak();
	}
	
	@Override
	public void setRequisitionLine(I_M_RequisitionLine reqLine, String trxName) {
		super.setRequisitionLine(reqLine, trxName);
		checkVendorBreak();
	}
	
	@Override
	public void setRMALine(I_M_RMALine rmaLine, String trxName) {
		super.setRMALine(rmaLine, trxName);
		checkVendorBreak();
	}
	
    /**
	 * UniCore Customization
	 * Initial additional price from Price List Schema
	 */
	public void initialAdditionalPrice()
	{
		StringBuilder sb = new StringBuilder("SELECT CONCAT(COALESCE(List_AddAmt, 0), ")
			.append("'@', COALESCE(Limit_AddAmt, 0), '@', COALESCE(Std_AddAmt, 0)) FROM ")
			.append(" M_DiscountSchemaLine WHERE M_DiscountSchema_ID = (SELECT M_DiscountSchema_ID ")
			.append(" FROM M_PriceList_Version WHERE M_PriceList_Version_ID = ?) ")
			.append(" AND UNS_Cluster_ID = (SELECT UNS_Cluster_ID FROM C_BPartner_Location ")
			.append(" WHERE C_BPartner_Location_ID = ?) AND UNS_Outlet_Grade_ID = (")
			.append(" SELECT UNS_Outlet_Grade_ID FROM C_BPartner WHERE C_BPartner_ID = ?)")
			.append(" AND UNS_Outlet_Type_ID = (SELECT UNS_Outlet_Type_ID FROM C_BPartner ")
			.append(" WHERE C_BPartner_ID = ?)" );
		
		String sql = sb.toString();
		
		String retVal = DB.getSQLValueString(
				null
				, sql
				, m_M_PriceList_Version_ID
				, m_C_BPartner_Location_ID
				, m_C_BPartner_ID
				, m_C_BPartner_ID);
		
		if(null != retVal)
		{
			String[] values = retVal.split("@");
			BigDecimal addPriceList = new BigDecimal(values[0]);
			BigDecimal addPriceLimit = new BigDecimal(values[1]);
			BigDecimal addPriceStd = new BigDecimal(values[2]);
			m_PriceLimit = m_PriceLimit.add(addPriceLimit);
			m_PriceList = m_PriceList.add(addPriceList);
			m_PriceStd = m_PriceStd.add(addPriceStd);
		}
	}
	
	private boolean isValidVersion()
	{
		if (!m_isIgnoredUnprocessedPrice)
			return true;
		if(m_M_PriceList_Version_ID == 0)
			return true;
		MPriceListVersion version =
				new MPriceListVersion(Env.getCtx(), 
						m_M_PriceList_Version_ID, null);
		return version.isApproved() && (version.getDocStatus().equals("CO"));
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean calculateExpeditionPrice ()
	{
		if (m_M_PriceList_ID == 0)
		{
			log.log(Level.SEVERE, "No PriceList");
			Trace.printStack();
			return false;
		}

		MPriceList priceList = new MPriceList(
				Env.getCtx(), m_M_PriceList_ID, null);
		return calculateExpeditionPrce(priceList);
	}
	
	private boolean calculateExpeditionPrce(MPriceList priceList)
	{
		String priceBased = priceList.getPriceBased();
		if (priceList.getBasePriceList_ID() > 0)
		{
			MPriceList subContractorPriceList = 
					(MPriceList) priceList.getBasePriceList();
			if (priceList.getBasicCalculation().equals(subContractorPriceList
					.getBasicCalculation()))
			{
				m_calculated = calculateExpeditionPrce(subContractorPriceList);
			}
		}
		
		if (priceBased.equals(MPriceList.PRICEBASED_CityToCity))
		{
			m_calculated = calculateCityToCity(priceList);
		}
		else if (priceBased.equals(MPriceList.PRICEBASED_DistanceOfCity))
		{
			m_calculated = calculateDistanceOnCity(priceList);
		}
		else if (priceBased.equals(MPriceList.PRICEBASED_GeneralDistance))
		{
			m_calculated = calculateGeneralDistance(priceList);
		}
		
		if (!m_calculated)
		{
			m_distance = Env.ZERO;
			m_duration = Env.ZERO;
			m_PriceLimit = Env.ZERO;
			m_PriceList = Env.ZERO;
			m_PriceStd = Env.ZERO;
		}
		
		return m_calculated;
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean calculateCityToCity (MPriceList priceList)
	{		
		m_enforcePriceLimit = priceList.isEnforcePriceLimit();
		MUNSExpPriceDirection priceDirection = priceList.getPriceDirection(
				m_PriceDate, m_origin.getC_City_ID(), 
				m_destination.getC_City_ID());
		
		if (null == priceDirection)
		{
			addMessage("Path is not on Priec List");
			log.log(Level.SEVERE, "Path is not on Priec List");
			Trace.printStack();
			return false;
		}
		
		MUNSExpPriceDetail[] pricings = priceDirection.getPriceDetails(false);
		if (pricings.length == 0)
		{
			addMessage("Price is not defined");
			log.log(Level.SEVERE, "Price is not defined");
			Trace.printStack();
			return false;
		}

		MUNSExpPriceAdd[] adds = priceDirection.getPriceAdds(false);
		boolean isAddPrice = adds.length > 0;
		
		if (isAddPrice)
		{
			UNSSimpleDirection direction = getDirection(
					m_origin.toString(), m_destination.toString());
			direction.init();
			List<UNSLocation> cities = direction.getCities();
			UNSLocation lastPath = null;
			for (int x=0; x<cities.size(); x++)
			{
				if (x==0)	{	lastPath = cities.get(x);}
				for (int i=0; i<adds.length; i++)
				{
					m_calculated = calculateAddPrice(
							adds[i], lastPath, cities.get(x));
					if (!m_calculated)
					{
						return m_calculated;
					}
				}
				
				if (x>0)	{	lastPath = cities.get(x);}
			}

			m_distance = direction.getDistance();
			m_duration = direction.getDuration();
			m_distance = m_distance.divide(CIBU, 5, RoundingMode.HALF_UP);
			m_duration = m_duration.divide(NAMPULUH, 5, RoundingMode.HALF_UP)
					.divide(NAMPULUH, 5, RoundingMode.HALF_UP);
		}
		else
		{
			DistanceMatrix disMatrix = new GeocodingUtils().getDistanceMatrix(
					m_origin.toString(), m_destination.toString());
			
			DistanceMatrixRow row = disMatrix.rows[0];
			DistanceMatrixElement element = row.elements[0];
			if (!element.status.toString().equals(DistanceMatrixElementStatus.OK.toString()))
			{
				addMessage("can't init distance from google");
				m_calculated = false;
				return m_calculated;
			}
			double distance = disMatrix.rows[0].elements[0].distance.inMeters;
			double duration = disMatrix.rows[0].elements[0].duration.inSeconds;
			m_distance = new BigDecimal(distance /1000);
			m_duration = new BigDecimal(duration / 60 /60);
		}
	
		for (int i=0; i<pricings.length; i++)
		{
			MUNSExpPriceDetail pricing = pricings[i];
			if (!pricing.isActive())
			{
				continue;
			}
			
			BigDecimal tmpStd = pricing.getPriceStd();
			BigDecimal tmpList = pricing.getPriceList();
			BigDecimal tmpLimit = pricing.getPriceLimit();
			if (pricing.isPricePerKM())
			{
				tmpStd = tmpStd.multiply(m_distance);
				tmpLimit = tmpLimit.multiply(m_distance);
				tmpList = tmpList.multiply(m_distance);
			}
			
			m_PriceStd = m_PriceStd.add(tmpStd);
			m_PriceLimit = m_PriceLimit.add(tmpLimit);
			m_PriceList = m_PriceList.add(tmpList);
		}
		
		m_calculated = true;
		return m_calculated;
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean calculateDistanceOnCity (MPriceList priceList)
	{
		m_enforcePriceLimit = priceList.isEnforcePriceLimit();		
		UNSSimpleDirection trackDirection = getDirection(
				m_origin.toString(), m_destination.toString());
		trackDirection.init();
		
		BigDecimal cibu = new BigDecimal(1000);
		BigDecimal nampul = new BigDecimal(60);
		m_distance = trackDirection.getDistance().divide(
				cibu, 5, RoundingMode.HALF_UP);
		m_duration = trackDirection.getDuration().divide(
				nampul, 5, RoundingMode.HALF_UP).divide(nampul, 5, 
						RoundingMode.HALF_UP);
		
		List<UNSLocation> cities = trackDirection.getCities();
		UNSLocation lastPath = null;
		for (int i=0; i<cities.size(); i++)
		{
			if (i==0)	{	lastPath = cities.get(i);}
			UNSLocation currentPath = cities.get(i);
			PO cityPO = currentPath.getPO();
			if (null == cityPO)
			{
				addMessage("Unknown city " + currentPath.getAlias());
				log.log(Level.SEVERE, "Unknown city " + currentPath.getAlias());
				Trace.printStack();
				return false;
			}
			
			MUNSExpPriceDirection priceDirection = priceList.getPriceDirection(
					m_PriceDate, m_origin.getC_City_ID(), m_destination.getC_City_ID());
			if (null == priceDirection)
			{
				addMessage("Path is not on Priec List" + cityPO.toString());
				log.log(Level.SEVERE, "Path is not on Priec List" 
							+ cityPO.toString());
				Trace.printStack();
				return false;
			}
			
			BigDecimal distance = currentPath.getDistanceAsBD();
			distance = distance.divide(cibu, 5, RoundingMode.HALF_UP);
			MUNSExpPriceDetail[] priceDetails = 
					priceDirection.getPriceDetails(false);
			
			for (int j=0; j<priceDetails.length; j++)
			{
				MUNSExpPriceDetail priceDetail = priceDetails[j];
				BigDecimal tmpDistance = priceDetail.getDistance();
				if (tmpDistance.compareTo(Env.ONE.negate()) == 0
						|| tmpDistance.compareTo(distance) >= 0)
				{
					tmpDistance = distance;
				}
				
				distance = distance.subtract(tmpDistance);
				if (j == (priceDetails.length-1) && distance.signum() != 0)
				{
					tmpDistance = tmpDistance.add(distance);
				}
				
				BigDecimal tmpStd = priceDetail.getPriceStd();
				BigDecimal tmpList = priceDetail.getPriceList();
				BigDecimal tmpLimit = priceDetail.getPriceLimit();
				if (priceDetail.isPricePerKM())
				{
					tmpStd = tmpStd.multiply(tmpDistance);
					tmpList = tmpList.multiply(tmpDistance);
					tmpLimit = tmpLimit.multiply(tmpDistance);
				}
				m_PriceStd = m_PriceStd.add(tmpStd);
				m_PriceList = m_PriceList.add(tmpList);
				m_PriceLimit = m_PriceLimit.add(tmpLimit);
				
				distance = distance.subtract(tmpDistance);
				if (distance.signum() == 0)	{	break;}
			}
			
			MUNSExpPriceAdd[] adds = priceDirection.getPriceAdds(false);
			
			for (int j=0; j<adds.length; j++)
			{
				m_calculated = calculateAddPrice(adds[j], lastPath, currentPath);
				if (!m_calculated)
				{
					return m_calculated;
				}
			}
			
			if (i>0)	{	lastPath = currentPath;}
		}
		
		m_calculated = true;
		
		return m_calculated;
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean calculateGeneralDistance (MPriceList priceList)
	{
		m_enforcePriceLimit = priceList.isEnforcePriceLimit();
		MPriceListVersion version = priceList.getPriceListVersion(m_PriceDate);
		if (null == version)
		{
			addMessage("Price List is not have version");
			log.log(Level.SEVERE, getMessage());
			Trace.printStack();
			return false;
		}
		
		MUNSExpPriceDetail[] priceDetails = version.getExpPriceDetail(false);
		MUNSExpPriceAdd[] adds = version.getExpPriceAdds(false);
		boolean isAddPrice = adds.length > 0;
		
		if (isAddPrice)
		{
			UNSSimpleDirection direction = getDirection(
					m_origin.toString(), m_destination.toString());
			direction.init();
			List<UNSLocation> cities = direction.getCities();
			UNSLocation lastPath = null;
			for (int x=0; x<cities.size(); x++)
			{
				if (x==0)	{	lastPath = cities.get(x);}
				for (int i=0; i<adds.length; i++)
				{
					m_calculated = calculateAddPrice(
							adds[i], lastPath, cities.get(x));
					if (!m_calculated)
					{
						return m_calculated;
					}
				}
				
				if (x>0)	{	lastPath = cities.get(x);}
			}

			m_distance = direction.getDistance();
			m_duration = direction.getDuration();
			BigDecimal cibu = new BigDecimal(1000);
			BigDecimal nampul = new BigDecimal(60);
			m_distance = m_distance.divide(cibu, 5, RoundingMode.HALF_UP);
			m_duration = m_duration.divide(nampul, 5, RoundingMode.HALF_UP)
					.divide(nampul, 5, RoundingMode.HALF_UP);
		}
		else
		{
			DistanceMatrix matrix = new GeocodingUtils().getDistanceMatrix(
					m_origin.toString(), m_destination.toString());
		
			double distance = matrix.rows[0].elements[0].distance.inMeters;
			double duration = matrix.rows[0].elements[0].duration.inSeconds;
			distance = distance / 1000;
			duration = duration / 60 / 60;
			m_distance = new BigDecimal(distance);
			m_duration = new BigDecimal(duration);
		}
		
		BigDecimal uncalculateDistance = m_distance;
		
		for (int j=0; j<priceDetails.length; j++)
		{
			MUNSExpPriceDetail priceDetail = priceDetails[j];
			BigDecimal tmpDistance = priceDetail.getDistance();
			if (tmpDistance.compareTo(Env.ONE.negate()) == 0
					|| tmpDistance.compareTo(uncalculateDistance) >= 0)
			{
				tmpDistance = uncalculateDistance;
			}
			
			uncalculateDistance = uncalculateDistance.subtract(tmpDistance);
			if (j == (priceDetails.length-1) && uncalculateDistance.signum() != 0)
			{
				tmpDistance = tmpDistance.add(uncalculateDistance);
			}
			
			BigDecimal tmpStd = priceDetail.getPriceStd();
			BigDecimal tmpList = priceDetail.getPriceList();
			BigDecimal tmpLimit = priceDetail.getPriceLimit();
			if (priceDetail.isPricePerKM())
			{
				tmpStd = tmpStd.multiply(tmpDistance);
				tmpList = tmpList.multiply(tmpDistance);
				tmpLimit = tmpLimit.multiply(tmpDistance);
			}			
			m_PriceStd = m_PriceStd.add(tmpStd);
			m_PriceList = m_PriceList.add(tmpList);
			m_PriceLimit = m_PriceLimit.add(tmpLimit);
			
			if (uncalculateDistance.signum() == 0)	{	break;}
		}
		
		m_calculated = true;
		return m_calculated;
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getDistance ()
	{
		if (!m_calculated)	{	calculatePrice();}
		return m_distance;
	}
	
	/**
	 * 
	 * @return
	 */
	public BigDecimal getDuration ()
	{
		if (!m_calculated)	{	calculatePrice();}
		return m_duration;
	}
	
	/**
	 * 
	 * @param add
	 * @param last
	 * @param current
	 * @return
	 */
	private boolean calculateAddPrice (MUNSExpPriceAdd add, UNSLocation last, 
			UNSLocation current)
	{
		BigDecimal distance = Env.ZERO;
		boolean ok = false;
		PO lastPO = last.getPO();
		if (null == lastPO)
		{
			addMessage("Can't initial location " + last.getAlias());
			log.log(Level.SEVERE, "Can't initial location " + last.getAlias());
			Trace.printStack();
			return false;
		}
		
		PO currentPO = current.getPO();
		if (null == currentPO)
		{
			addMessage("Can't initial location " + current.getAlias());
			log.log(Level.SEVERE, "Can't initial location " + current.getAlias());
			Trace.printStack();
			return false;
		}
		
		if (add.getAddPriceType().equals(MUNSExpPriceAdd.
				ADDPRICETYPE_PassedThroughCity))
		{
			ok = currentPO.get_ID() == add.getOrigin_ID();
			if (ok)
			{
				distance = current.getDistanceAsBD();
			}
		}
		else if (add.getAddPriceType().equals(MUNSExpPriceAdd.
				ADDPRICETYPE_PassedThroughTwoCities))
		{
			ok = lastPO.get_ID() == add.getOrigin_ID()
					&& currentPO.get_ID() == add.getDestination_ID();
			if (ok)
			{
				distance = current.getDistanceAsBD();
				if (currentPO.get_ID() != lastPO.get_ID())
				{
					distance = distance.add(last.getDistanceAsBD());
				}
			}
		}
		else if (add.getAddPriceType().equals(MUNSExpPriceAdd.
				ADDPRICETYPE_PassedThroughDistrict))
		{
			if (currentPO.get_TableName() == MCity.Table_Name)
			{
				List<UNSLocation> districts = current.getChilds();
				for (int i=0; i<districts.size(); i++)
				{
					ok = calculateAddPrice(add, districts.get(i), 
							districts.get(i));
					if (!ok)	{	break;}
				}
				return ok;
			}
			
			ok = currentPO.get_ID() != add.getUNS_District_ID();
			if (ok)
			{
				distance = current.getDistanceAsBD();
			}
		}
		
		if (ok)
		{
			distance = distance.divide(new BigDecimal(1000), 5, 
					RoundingMode.HALF_UP);
			
			BigDecimal tmpStd = add.getPriceStd();
			if (add.isPricePerKM())
			{
				tmpStd.multiply(distance);
			}
			
			m_PriceStd = m_PriceStd.add(tmpStd);
			m_PriceLimit = m_PriceLimit.add(tmpStd);
			m_PriceList = m_PriceList.add(tmpStd);
		}
		
		return true;
	}
	
	/**
	 * Add message
	 * @param msg
	 */
	private void addMessage (String msg)
	{
		if (null == m_msg)
		{	m_msg = "";
		}
		else 
		{	
			m_msg += "\n";
		}
		
		m_msg += msg;
	}
	
	public String getMessage ()
	{
		return m_msg;
	}
	
	private UNSSimpleDirection m_direction = null;
	private UNSSimpleDirection getDirection (String origin, String destination)
	{
		if (m_direction == null)
		{
			m_direction = new UNSSimpleDirection(origin, destination);
		}
		
		return m_direction;
	}
}	//	MProductPrice
