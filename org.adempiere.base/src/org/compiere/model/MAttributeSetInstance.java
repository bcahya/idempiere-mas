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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;

/**
 *  Product Attribute Set Instance
 *
 *	@author Jorg Janke
 *	@version $Id: MAttributeSetInstance.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *			<li>BF [ 2675699 ] MAttributeSetInstance.create should create Lot/Serial/Guaran
 */
public class MAttributeSetInstance extends X_M_AttributeSetInstance
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7870720973216607658L;


	/**
	 * 	Get Attribute Set Instance from ID or Product
	 *	@param ctx context
	 * 	@param M_AttributeSetInstance_ID id or 0
	 * 	@param M_Product_ID required if id is 0
	 * 	@return Attribute Set Instance or null if error
	 */
	public static MAttributeSetInstance get (Properties ctx, 
		int M_AttributeSetInstance_ID, int M_Product_ID)
	{
		MAttributeSetInstance retValue = null;
		//	Load Instance if not 0
		if (M_AttributeSetInstance_ID != 0)
		{
			if (s_log.isLoggable(Level.FINE)) s_log.fine("From M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID);
			return new MAttributeSetInstance (ctx, M_AttributeSetInstance_ID, null);
		}
		//	Get new from Product
		if (s_log.isLoggable(Level.FINE)) s_log.fine("From M_Product_ID=" + M_Product_ID);
		if (M_Product_ID == 0)
			return null;
		String sql = "SELECT M_AttributeSet_ID, M_AttributeSetInstance_ID "
			+ "FROM M_Product "
			+ "WHERE M_Product_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_Product_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				int M_AttributeSet_ID = rs.getInt(1);
			//	M_AttributeSetInstance_ID = rs.getInt(2);	//	needed ?
				//
				retValue = new MAttributeSetInstance (ctx, 0, M_AttributeSet_ID, null);
			}
		}
		catch (SQLException ex)
		{
			s_log.log(Level.SEVERE, sql, ex);
			retValue = null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		return retValue;
	}	//	get
	
	//TAMBAHAN
	public static MAttributeSetInstance get (Properties ctx, String trxName,
			int M_AttributeSetInstance_ID, int M_Product_ID)
		{
			MAttributeSetInstance retValue = null;
			//	Load Instance if not 0
			if (M_AttributeSetInstance_ID != 0)
			{
				if (s_log.isLoggable(Level.FINE)) s_log.fine("From M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID);
				return new MAttributeSetInstance (ctx, M_AttributeSetInstance_ID, trxName);
			}
			//	Get new from Product
			if (s_log.isLoggable(Level.FINE)) s_log.fine("From M_Product_ID=" + M_Product_ID);
			if (M_Product_ID == 0)
				return null;
			String sql = "SELECT M_AttributeSet_ID, M_AttributeSetInstance_ID "
				+ "FROM M_Product "
				+ "WHERE M_Product_ID=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, trxName);
				pstmt.setInt(1, M_Product_ID);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					int M_AttributeSet_ID = rs.getInt(1);
				//	M_AttributeSetInstance_ID = rs.getInt(2);	//	needed ?
					//
					retValue = new MAttributeSetInstance (ctx, 0, M_AttributeSet_ID, trxName);
				}
			}
			catch (SQLException ex)
			{
				s_log.log(Level.SEVERE, sql, ex);
				retValue = null;
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
			//
			return retValue;
		}	//	get

	private static CLogger		s_log = CLogger.getCLogger (MAttributeSetInstance.class);

	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_AttributeSetInstance_ID id
	 *	@param trxName transaction
	 */
	public MAttributeSetInstance (Properties ctx, int M_AttributeSetInstance_ID, String trxName)
	{
		super (ctx, M_AttributeSetInstance_ID, trxName);
		if (M_AttributeSetInstance_ID == 0)
		{
		}
	}	//	MAttributeSetInstance

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAttributeSetInstance (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAttributeSetInstance

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_AttributeSetInstance_ID id
	 * 	@param M_AttributeSet_ID attribute set
	 *	@param trxName transaction
	 */
	public MAttributeSetInstance (Properties ctx, int M_AttributeSetInstance_ID, 
		int M_AttributeSet_ID, String trxName)
	{
		this (ctx, M_AttributeSetInstance_ID, trxName);
		setM_AttributeSet_ID(M_AttributeSet_ID);
	}	//	MAttributeSetInstance

	/**	Attribute Set				*/
	private MAttributeSet 		m_mas = null;
	/**	Date Format					*/
	private DateFormat			m_dateFormat = DisplayType.getDateFormat(DisplayType.Date);

	/**
	 * 	Set Attribute Set
	 * 	@param mas attribute set
	 */
	public void setMAttributeSet (MAttributeSet mas)
	{
		m_mas = mas;
		setM_AttributeSet_ID(mas.getM_AttributeSet_ID());
	}	//	setAttributeSet

	/**
	 * 	Get Attribute Set
	 *	@return Attrbute Set or null
	 */
	public MAttributeSet getMAttributeSet()
	{
		if (m_mas == null && getM_AttributeSet_ID() != 0)
			m_mas = new MAttributeSet (getCtx(), getM_AttributeSet_ID(), get_TrxName());
		return m_mas;
	}	//	getMAttributeSet

	/**
	 * 	Set Description.
	 * 	- Product Values
	 * 	- Instance Values
	 * 	- SerNo	= #123
	 *  - Lot 	= \u00ab123\u00bb
	 *  - GuaranteeDate	= 10/25/2003
	 */
	public void setDescription()
	{
		//	Make sure we have a Attribute Set
		getMAttributeSet();
		if (m_mas == null)
		{
			setDescription ("");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		
		//	Instance Attribute Values
		MAttribute[] attributes = m_mas.getMAttributes(true);
		for (int i = 0; i < attributes.length; i++)
		{
			MAttributeInstance mai = attributes[i].getMAttributeInstance(getM_AttributeSetInstance_ID());
			if (mai != null && mai.getValue() != null)
			{
				if (sb.length() > 0)
					sb.append("_");
				sb.append(mai.getValue());
			}
		}
		//	SerNo
		if (m_mas.isSerNo() && getSerNo() != null)
		{
			if (sb.length() > 0)
				sb.append("_");
			sb.append(m_mas.getSerNoCharStart()).append(getSerNo()).append(m_mas.getSerNoCharEnd());
		}
		//	Lot
		if (m_mas.isLot() && getLot() != null)
		{
			if (sb.length() > 0)
				sb.append("_");
			sb.append(m_mas.getLotCharStart()).append(getLot()).append(m_mas.getLotCharEnd());
		}
		//	GuaranteeDate
		if (m_mas.isGuaranteeDate() && getGuaranteeDate() != null)
		{
			if (sb.length() > 0)
				sb.append("_");
			sb.append (m_dateFormat.format(getGuaranteeDate()));
		}

		//	Product Attribute Values
		attributes = m_mas.getMAttributes(false);
		for (int i = 0; i < attributes.length; i++)
		{
			MAttributeInstance mai = attributes[i].getMAttributeInstance(getM_AttributeSetInstance_ID());
			if (mai != null)
			{
				if (sb.length() > 0)
					sb.append("_");
				if (mai.getValue() != null)
					sb.append(mai.getValue());
				else
					sb.append("");
			}
		}
		setDescription (sb.toString());
	}	//	setDescription


	/**
	 * 	Get Guarantee Date
	 * 	@param getNew if true calculates/sets guarantee date
	 *	@return guarantee date or null if days = 0
	 */
	public Timestamp getGuaranteeDate(boolean getNew)
	{
		if (getNew)
		{
			int days = getMAttributeSet().getGuaranteeDays();
			if (days > 0)
			{
				Timestamp ts = TimeUtil.addDays(new Timestamp(System.currentTimeMillis()), days);
				setGuaranteeDate(ts);
			}
		}
		return getGuaranteeDate();
	}	//	getGuaranteeDate

	/**
	 * 	Get Lot No
	 * 	@param getNew if true create/set new lot
	 * 	@param M_Product_ID product used if new
	 *	@return lot
	 */
	public String getLot (boolean getNew, int M_Product_ID)
	{
		if (getNew)
			createLot(M_Product_ID);
		return getLot();
	}	//	getLot

	/**
	 * 	Create Lot
	 * 	@param M_Product_ID product used if new
	 *	@return lot info
	 */
	public KeyNamePair createLot (int M_Product_ID)
	{
		KeyNamePair retValue = null;
		int M_LotCtl_ID = getMAttributeSet().getM_LotCtl_ID();
		if (M_LotCtl_ID != 0)
		{
			MLotCtl ctl = new MLotCtl (getCtx(), M_LotCtl_ID, null);
			MLot lot = ctl.createLot(M_Product_ID);
			setM_Lot_ID (lot.getM_Lot_ID());
			setLot (lot.getName());
			retValue = new KeyNamePair (lot.getM_Lot_ID(), lot.getName());	
		}
		return retValue;
	}	//	createLot
	
	/**
	 * 	To to find lot and set Lot/ID
	 *	@param Lot lot
	 *	@param M_Product_ID product
	 */
	public void setLot (String Lot, int M_Product_ID)
	{
		//	Try to find it
		MLot mLot = MLot.getProductLot(getCtx(), M_Product_ID, Lot, get_TrxName());
		if (mLot != null)
			setM_Lot_ID(mLot.getM_Lot_ID());
		setLot (Lot);
	}	//	setLot

	/**
	 * 	Exclude Lot creation
	 *	@param AD_Column_ID column
	 *	@param isSOTrx SO
	 *	@return true if excluded
	 */
	public boolean isExcludeLot (int AD_Column_ID, boolean isSOTrx)
	{
		getMAttributeSet();
		if (m_mas != null)
			return m_mas.isExcludeLot (AD_Column_ID, isSOTrx);
		return false;
	}	//	isExcludeLot

	/**
	 *	Get Serial No
	 * 	@param getNew if true create/set new Ser No
	 *	@return Serial Number
	 */
	public String getSerNo (boolean getNew)
	{
		if (getNew)
		{
			int M_SerNoCtl_ID = getMAttributeSet().getM_SerNoCtl_ID();
			if (M_SerNoCtl_ID != 0)
			{
				MSerNoCtl ctl = new MSerNoCtl (getCtx(), M_SerNoCtl_ID, get_TrxName());
				setSerNo(ctl.createSerNo());
			}
		}
		return getSerNo();
	}	//	getSerNo

	/**
	 *	Exclude SerNo creation
	 *	@param AD_Column_ID column
	 *	@param isSOTrx SO
	 *	@return true if excluded
	 */
	public boolean isExcludeSerNo (int AD_Column_ID, boolean isSOTrx)
	{
		getMAttributeSet();
		if (m_mas != null)
			return m_mas.isExcludeSerNo (AD_Column_ID, isSOTrx);
		return false;
	}	//	isExcludeSerNo

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) 
	{
		if (super.afterSave(newRecord, success)) 
		{
			if (newRecord && success)
			{
				//use id as description when description is empty
				String desc = this.getDescription();
				if (desc == null || desc.trim().length() == 0)
				{
					this.set_ValueNoCheck("Description", Integer.toString(getM_AttributeSetInstance_ID()));
					String sql = "UPDATE M_AttributeSetInstance SET Description = ? WHERE M_AttributeSetInstance_ID = ?";
					int no = DB.executeUpdateEx(sql, 
							new Object[]{Integer.toString(getM_AttributeSetInstance_ID()), getM_AttributeSetInstance_ID()}, 
							get_TrxName());
					if (no <= 0)
					{
						log.log(Level.SEVERE, "Failed to update description.");
						return false;
					}
				}
			}
			return true;
		}
		
		return false;
	}
	
	/**
	 * Create & save a new ASI for given product.
	 * Automatically creates Lot#, Serial# and Guarantee Date.
	 * @param ctx
	 * @param product
	 * @param trxName
	 * @return newly created ASI
	 */
	public static MAttributeSetInstance create(Properties ctx, MProduct product, String trxName)
	{
		MAttributeSetInstance asi = new MAttributeSetInstance(ctx, 0, trxName);
		asi.setClientOrg(product.getAD_Client_ID(), 0);
		asi.setM_AttributeSet_ID(product.getM_AttributeSet_ID());
		// Create new Lot, Serial# and Guarantee Date
		if (asi.getM_AttributeSet_ID() > 0)
		{
			asi.getLot(true, product.get_ID());
			asi.getSerNo(true);
			asi.getGuaranteeDate(true);
		}
		//
		asi.saveEx();
		return asi;
	}
	
	/**
	 * AutoGerate & save a new ASI for given product.
	 * Automatically creates Lot#
	 * @param ctx
	 * @param product
	 * @param trxName
	 * @return newly created ASI
	 */
	public static MAttributeSetInstance generateLot(Properties ctx, MProduct product, String trxName)
	{
		MAttributeSetInstance asi = new MAttributeSetInstance(ctx, 0, trxName);
		asi.setClientOrg(product.getAD_Client_ID(), 0);
		asi.setM_AttributeSet_ID(product.getM_AttributeSet_ID());
		// Create new Lot
		if (asi.getM_AttributeSet_ID() > 0)
		{
			asi.getLot(true, product.get_ID());
		}
		//
		asi.setDescription();
		asi.saveEx();
		return asi;
	}
	
	//TAMBAHAN
	/**
	 * 
	 * @param po
	 * @param columnName_Product_ID
	 * @param columnName_ASI_ID
	 * @param trxName
	 * @return
	 */
	public static MAttributeSetInstance initAttributeValuesFrom(
			PO po,String columnName_Product_ID,String columnName_ASI_ID, String trxName) 
	{
		MProduct product = MProduct.get(Env.getCtx(), po.get_ValueAsInt(columnName_Product_ID));
		return initAttributeValuesFrom(po, product, columnName_ASI_ID, trxName);
	}

	public static MAttributeSetInstance initAttributeValuesFrom(
			PO po,MProduct product,String columnName_ASI_ID, String trxName) 
	{
		MAttributeSetInstance asi = null;
		//MProduct product = MProduct.get(Env.getCtx(), po.get_ValueAsInt(columnName_Product_ID));
		if (product.getM_AttributeSet_ID() == 0)
		{
			throw new AdempiereUserError("Admin Error: Attribute Set of Product '" + product.getValue() + "' have not defined yet.", 
										 "Please let the Administrator define it in the product definition!");
		}
		int asi_ID = po.get_ValueAsInt(columnName_ASI_ID) ;
		if (asi_ID <= 0) 
		{
			asi = MAttributeSetInstance.create(Env.getCtx(), product, trxName);
			po.set_ValueNoCheck(columnName_ASI_ID,asi.getM_AttributeSetInstance_ID());
		}
		else
		{
			asi = MAttributeSetInstance.get(Env.getCtx(), trxName, asi_ID, product.getM_Product_ID());
		}
		asi.save();
		asi.resetAttributeValuesFrom(po, trxName);
		asi.setDescription();
		asi.save();
		return asi;
	}
	
	/**
	 * 
	 * @param po
	 * @param trxName
	 * @return
	 */
	public MAttributeSetInstance resetAttributeValuesFrom(PO po, String trxName) {
		// if (po.)
		MAttributeSet attrSet = this.getMAttributeSet();

		if (attrSet.isUPC()) {
			MAttributeInstance attrInstance = MAttributeInstance.getNoSave(getCtx(),
					this.getM_AttributeSetInstance_ID(), attrSet.getUPCAttribute_ID(), trxName);

			MAttribute attr = (MAttribute) attrSet.getUPCAttribute();

			if (attr.getAD_Table_ID() <= 0 && po != null) {
				String upcVal = po.get_ValueAsString("UPC");
				attrInstance.setValue(upcVal);
				attrInstance.saveEx();
			} else if (po != null) {
				attr.resetInstanceValue(attrInstance, po, trxName);
			}
		}

		MAttribute[] attributes = attrSet.getMAttributes(true);

		for (MAttribute attr : attributes) {
			if (attr.get_ID() == attrSet.getUPCAttribute_ID())
				continue;

			MAttributeInstance attrInstance = MAttributeInstance.getNoSave(getCtx(),
					this.getM_AttributeSetInstance_ID(), attr.getM_Attribute_ID(), trxName);

			if (null == attrInstance)
				continue;

			int tableToSearch = attr.getAD_Table_ID();

			if ((tableToSearch <= 0) || (po == null))
				continue;

			attr.resetInstanceValue(attrInstance, po, trxName);
		}
		return this;
	}
		
		/**
		 * Create new asi and copy asi values from this asi.
		 * @param product
		 * @return {@link MAttributeSetInstance}
		 */
		public MAttributeSetInstance createCopyAttributeValues(MProduct product)
		{
			MAttributeSetInstance asiTo = MAttributeSetInstance.create(
					getCtx(), product, get_TrxName());
			PO.copyValues(this, asiTo);
			asiTo.save();
			return asiTo.copyAttributeValuesFrom(this);		
		}
		
		
		/**
		 * Copy values from asi in parameter to this asi.
		 * @param asiFrom
		 * @return {@link MAttributeSetInstance}
		 */
		private MAttributeSetInstance copyAttributeValuesFrom(MAttributeSetInstance asiFrom)
		{
			MAttribute[] attributes = asiFrom.getMAttributeSet().getMAttributes(true);
			MAttribute[] attributesTo = this.getMAttributeSet().getMAttributes(true);
			
			for (MAttribute attr : attributes)
			{
				MAttributeInstance attrInstance = 
						MAttributeInstance.getNoSave(getCtx(), asiFrom.getM_AttributeSetInstance_ID(), 
								attr.getM_Attribute_ID(), get_TrxName());
				
				if (null == attrInstance)
					continue;
				
				for(MAttribute attrTo : attributesTo)
				{
					if(!attrTo.getName().equals(attr.getName()))
						continue;
					
					MAttributeInstance attrInstanceTo = MAttributeInstance.getNoSave(
							getCtx(), getM_AttributeSetInstance_ID(), attrTo.getM_Attribute_ID(), get_TrxName());
					if(null == attrInstanceTo)
					{
						attrInstanceTo = new MAttributeInstance(
								getCtx(), attrTo.get_ID(), getM_AttributeSetInstance_ID(), 
								attrInstance.getValue(), get_TrxName());
					}
					
					if((attrInstance.getValue() != null && attrInstanceTo.getValue() == null)
						|| 
						(attrInstance.getValue() != null && attrInstanceTo.getValue() != null 
							&& !attrInstance.getValue().equals(attrInstanceTo.getValue())))
					{
						attrInstanceTo.setValue(attrInstance.getValue());
					}
					
					attrInstanceTo.save();
				}
			}
			return this;
		}
		
		/**
		 * Create new attribute set instance.
		 * Return new MAttributeSetInstance if asi from is found or null if not.
		 * @param po
		 * @param columnNameAsi //Column name of Attribute Set Instance
		 * @param columnNameProduct //Column name of Product
		 * @param columnNameAsiTo //Column name of Attribute Set Instance To
		 * @param trxName // transaction name.
		 * @return {@link MAttributeSetInstance} or null
		 */
		public static MAttributeSetInstance createCopyOfASI(
				PO po,
				String columnNameAsi,
				String columnNameProduct,
				String columnNameAsiTo,
				String trxName)
		{
			int M_Product_ID = po.get_ValueAsInt(columnNameProduct);
			int M_AttributeSetInstance_ID = po.get_ValueAsInt(columnNameAsi);
			
			if(M_Product_ID == 0 || M_AttributeSetInstance_ID == 0)
				return null;
			
			MAttributeSetInstance asiFrom = MAttributeSetInstance.get(
					Env.getCtx(), trxName, M_AttributeSetInstance_ID, M_Product_ID);
			
			if(null == asiFrom)
				return null;
			
			MProduct product = MProduct.get(Env.getCtx(), M_Product_ID);
			MAttributeSetInstance asiTo = asiFrom.createCopyAttributeValues(product);
			po.set_Value(columnNameAsiTo, asiTo.getM_AttributeSetInstance_ID());
			
			return asiTo;
		}
		
		public boolean isAllowedOverwriteCreated()
		{
			String sql = "SELECT COALESCE(SUM(QtyOnHand), 0) FROM M_StorageOnHand WHERE M_AttributeSetInstance_ID = ?";
			BigDecimal qtyOnMyASI = DB.getSQLValueBD(get_TrxName(), sql, get_ID());		
			return qtyOnMyASI.signum() == 0;
		}
		
		/**
		 * 
		 * @param movementDate
		 */
		public void setCreated(Timestamp movementDate)
		{
			if(!isAllowedOverwriteCreated())
			{
				if (log.isLoggable(Level.WARNING)) log.log(Level.WARNING, "Can't overwrite Created of ASI " + get_ID());
				return;
			}
			String query = "UPDATE M_AttributeSetInstance SET Created =? WHERE M_AttributeSetInstance_ID =?";
			Object[] params = {movementDate,get_ID()};
			DB.executeUpdate(query, params, false, get_TrxName());		
		}
		
		public static void resetDescription(Properties ctx, int M_AttributeSetInstance_ID, String trxName)
		{
			MAttributeSetInstance asi = new MAttributeSetInstance(ctx, M_AttributeSetInstance_ID, trxName);
			asi.setDescription();
			asi.save();
		}
		
}	//	MAttributeSetInstance
