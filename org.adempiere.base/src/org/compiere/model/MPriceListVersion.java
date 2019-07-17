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

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import com.unicore.model.MUNSExpPriceAdd;
import com.unicore.model.MUNSExpPriceDetail;
import com.unicore.model.MUNSExpPriceDirection;
import com.uns.model.IUNSApprovalInfo;


/**
 *	Price List Version Model
 *	
 *  @author Jorg Janke, Menjangan
 *  @version $Id: MPriceListVersion.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MPriceListVersion extends X_M_PriceList_Version 
		implements DocAction, IUNSApprovalInfo
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3607494586575155059L;
	
	/** UntaCore Customization */
	private String m_processMsg = null;
	private boolean m_justPrepared = false;
	private MUNSExpPriceDirection[] m_directions = null;
	private MUNSExpPriceDetail[] m_priceDetails = null;
	private X_M_ProductPriceVendorBreak[] m_ppvb = null;
	private MUNSExpPriceAdd[] m_priceAdds = null;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_PriceList_Version_ID id
	 *	@param trxName transaction
	 */
	public MPriceListVersion(Properties ctx, int M_PriceList_Version_ID, 
			String trxName)
	{
		super(ctx, M_PriceList_Version_ID, trxName);
		if (M_PriceList_Version_ID == 0)
		{
		//	setName (null);	// @#Date@
		//	setM_PriceList_ID (0);
		//	setValidFrom (TimeUtil.getDay(null));	// @#Date@
		//	setM_DiscountSchema_ID (0);
		}
	}	//	MPriceListVersion

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MPriceListVersion(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MPriceListVersion

	/**
	 * 	Parent Constructor
	 *	@param pl parent
	 */
	public MPriceListVersion (MPriceList pl)
	{
		this (pl.getCtx(), 0, pl.get_TrxName());
		setClientOrg(pl);
		setM_PriceList_ID(pl.getM_PriceList_ID());
	}	//	MPriceListVersion
	
	/** Product Prices			*/
	private MProductPrice[] m_pp = null;
	/** Price List				*/
	private MPriceList		m_pl = null;

	/**
	 * 	Get Parent PriceList
	 *	@return price List
	 */
	public MPriceList getPriceList()
	{
		if (m_pl == null && getM_PriceList_ID() != 0)
			m_pl = MPriceList.get (getCtx(), getM_PriceList_ID(), null);
		return m_pl;
	}	//	PriceList
	
	
	/**
	 * 	Get Product Price
	 * 	@param refresh true if refresh
	 *	@return product price
	 */
	public MProductPrice[] getProductPrice (boolean refresh)
	{
		if (m_pp != null && !refresh)
			return m_pp;
		m_pp = getProductPrice(null);
		return m_pp;
	}	//	getProductPrice
	
	/**
	 * 	Get Product Price
	 * 	@param whereClause optional where clause
	 * 	@return product price
	 */
	public MProductPrice[] getProductPrice (String whereClause)
	{
		String localWhereClause = 
				I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID+"=? ";
		if (whereClause != null)
			localWhereClause += " " + whereClause;
		List<MProductPrice> list = new Query(
				getCtx(),I_M_ProductPrice.Table_Name,localWhereClause,
				get_TrxName())
			.setParameters(getM_PriceList_Version_ID())
			.list();
		MProductPrice[] pp = new MProductPrice[list.size()];
		list.toArray(pp);
		return pp;
	}	//	getProductPrice
	
	/**
	 * 	Set Name to Valid From Date.
	 * 	If valid from not set, use today
	 */
	public void setName()
	{
		if (getValidFrom() == null)
			setValidFrom (TimeUtil.getDay(null));
		if (getName() == null)
		{
			String name = DisplayType.getDateFormat(DisplayType.Date)
				.format(getValidFrom());
			setName(name);
		}
	}	//	setName
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		setName();
		if (is_ValueChanged(COLUMNNAME_Processed))
		{
			processLines(isProcessed());
		}
		return true;
	}	//	beforeSave

	@Override
	public boolean processIt(String action) throws Exception {
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(action, getDocAction());
	}

	@Override
	public boolean unlockIt() {
		return false;
	}

	@Override
	public boolean invalidateIt() {
		return false;
	}

	@Override
	public String prepareIt() 
	{
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		setProcessed(true);
		if (!DocAction.ACTION_Complete.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Complete);
		return DocAction.STATUS_InProgress;
	}

	@Override
	public boolean approveIt() {
		setIsApproved(true);
		return true;
	}

	@Override
	public boolean rejectIt() {
		setIsApproved(false);
		return true;
	}

	@Override
	public String completeIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		if (!m_justPrepared)
		{
			String msg = prepareIt();
			if (!msg.equals(DocAction.STATUS_InProgress))
			{
				return DocAction.STATUS_Invalid;
			}
		}
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		if(!isApproved())
			approveIt();
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		m_justPrepared = true;
		if (!DocAction.ACTION_Close.equals(getDocAction()))
			setDocAction(DocAction.ACTION_Close);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_BEFORE_VOID);
		if (null != m_processMsg)
			return false;
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this, ModelValidator.TIMING_AFTER_VOID);
		if (null != m_processMsg)
			return false;
		
		setDocAction(DocAction.ACTION_None);
		return true;
	}

	@Override
	public boolean closeIt() {
		if (log.isLoggable(Level.INFO)) log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DocAction.ACTION_None);

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(
				this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		return true;
	}

	@Override
	public boolean reverseCorrectIt() {
		m_processMsg = "Revers is not allowed.";
		return false;
	}

	@Override
	public boolean reverseAccrualIt() {
		m_processMsg = "Revers is not allowed.";
		return false;
	}

	@Override
	public boolean reActivateIt() {
		m_processMsg = "Reactivate is not allowed.";
		return false;
	}
	
	@Override
	public String getDocumentNo()
	{
		String docNo = super.getDocumentNo();
		if (null == docNo)
			docNo = "Undefined";
		
		return docNo;
	}

	@Override
	public String getSummary() {
		boolean isSoPrice = getM_PriceList().isSOPriceList();
		return (new StringBuilder(isSoPrice ? "Selling Price: " : 
			"Purchasing Price: ").append("Valid From ").append(getValidFrom())
			.append(":").append(getDescription()!= null ? getDescription() : ""))
				.toString();
	}

	@Override
	public String getDocumentInfo() {
		return getSummary();
	}

	@Override
	public File createPDF() {
		return null;
	}

	@Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get expedition price direction.
	 * @param requery
	 * @return
	 */
	public MUNSExpPriceDirection[] getExpPriceDirections (boolean requery)
	{
		if (null != m_directions && !requery)
		{
			set_TrxName(m_directions, get_TrxName());
			return m_directions;
		}
		
		List<MUNSExpPriceDirection> list = new Query(
				getCtx(), MUNSExpPriceDirection.Table_Name, 
				MUNSExpPriceDirection.COLUMNNAME_M_PriceList_Version_ID + " = ?", 
				get_TrxName()).setParameters(get_ID()).list();
		
		m_directions = new MUNSExpPriceDirection[list.size()];
		list.toArray(m_directions);
		
		return m_directions;
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSExpPriceDetail[] getExpPriceDetail (boolean requery)
	{
		if (null != m_priceDetails && !requery)
		{
			set_TrxName(m_priceDetails, get_TrxName());
			return m_priceDetails;
		}
		
		List<MUNSExpPriceDetail> list = new Query(
				getCtx(), MUNSExpPriceDetail.Table_Name, 
				MUNSExpPriceDetail.COLUMNNAME_M_PriceList_Version_ID + " = ?", 
				get_TrxName()).setParameters(get_ID()).list();
		
		m_priceDetails = new MUNSExpPriceDetail[list.size()];
		list.toArray(m_priceDetails);
		
		return m_priceDetails;
	}
	
	/**
	 * Get Product Price Vendor Break
	 * @param requery
	 * @return
	 */
	public X_M_ProductPriceVendorBreak[] getPPriceVBreak (boolean requery)
	{
		if (null != m_ppvb && !requery)
		{
			set_TrxName(m_ppvb, get_TrxName());
			return m_ppvb;
		}
		
		List<X_M_ProductPriceVendorBreak> list = new Query(
				getCtx(), X_M_ProductPriceVendorBreak.Table_Name, 
				X_M_ProductPriceVendorBreak.
				COLUMNNAME_M_PriceList_Version_ID + " = ?", get_TrxName()).
				setParameters(get_ID()).list();
		
		m_ppvb = new X_M_ProductPriceVendorBreak[list.size()];
		list.toArray(m_ppvb);
		
		return m_ppvb;
	}
	
	/**
	 * Set Processed
	 */
	private void processLines (boolean processed)
	{
		getProductPrice(false);
		getExpPriceDetail(false);
		getExpPriceDirections(false);
		getPPriceVBreak(false);
		for (int i=0; i<m_pp.length; i++)
		{
			m_pp[i].setProcessed(processed);
			m_pp[i].saveEx();
		}
		for (int i=0; i<m_directions.length; i++)
		{
			m_directions[i].setProcessed(processed);
			m_directions[i].saveEx();
		}
		for (int i=0; i<m_priceDetails.length; i++)
		{
			m_priceDetails[i].setProcessed(processed);
			m_priceDetails[i].saveEx();
		}
		for (int i=0; i<m_ppvb.length; i++)
		{
			m_ppvb[i].setProcessed(processed);
			m_ppvb[i].saveEx();
		}
	}
	
	/**
	 * 
	 * @param requery
	 * @return
	 */
	public MUNSExpPriceAdd[] getExpPriceAdds (boolean requery)
	{
		if (null != m_priceAdds && !requery)
		{
			set_TrxName(m_priceAdds, get_TrxName());
			return m_priceAdds;
		}
		
		List<MUNSExpPriceAdd> list = new Query(
				getCtx(), MUNSExpPriceAdd.Table_Name, 
				MUNSExpPriceAdd.COLUMNNAME_M_PriceList_Version_ID + " = ?", 
				get_TrxName()).setParameters(get_ID()).
				setOnlyActiveRecords(true).list();
		
		m_priceAdds = new MUNSExpPriceAdd[list.size()];
		list.toArray(m_priceAdds);
		
		return m_priceAdds;
	}

	@Override
	public List<Object[]> getApprovalInfoColumnClassAccessable() {
		List<Object[]> list = new ArrayList<>();
		list.add(new Object[]{String.class, true});
		list.add(new Object[]{String.class, true});
		list.add(new Object[]{BigDecimal.class, true});
		list.add(new Object[]{BigDecimal.class, true});
		list.add(new Object[]{BigDecimal.class, true});
		return list;
	}

	@Override
	public String[] getDetailTableHeader()
	{
		return new String[]{
				"Product",
				"Vendor",
				"Price List",
				"Price Std",
				"Price Limit",
		};
	}

	@Override
	public List<Object[]> getDetailTableContent()
	{
		List<Object[]> list = new ArrayList<>();
		String sql = "SELECT p.Name AS Product, '' AS Vendor, "
				+ " pp.PriceList AS PriceList, pp.PriceStd AS PriceStd, "
				+ " pp.PriceLimit AS PriceLimit FROM M_ProductPrice pp "
				+ " INNER JOIN M_Product p ON p.M_Product_ID = pp.M_Product_ID "
				+ " WHERE pp.M_PriceList_Version_ID = ? UNION "
				+ " SELECT p.Name AS Product, v.name AS Vendor, "
				+ " pp.PriceList AS PriceList, pp.PriceStd AS PriceStd, "
				+ " pp.PriceLimit AS PriceLimit FROM "
				+ " M_ProductPriceVendorBreak pp "
				+ " INNER JOIN M_Product p ON p.M_Product_ID = pp.M_Product_ID "
				+ " INNER JOIN C_BPartner v ON v.C_BPartner_ID = pp.C_BPartner_ID "
				+ " WHERE pp.M_PriceList_Version_ID = ?";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, get_TrxName());
			st.setInt(1, get_ID());
			st.setInt(2, get_ID());
			rs = st.executeQuery();
			while (rs.next())
			{
				Object rowData[] = new Object[5];
				rowData[0] = rs.getObject(1);
				rowData[1] = rs.getObject(2);
				rowData[2] = rs.getObject(3);
				rowData[3] = rs.getObject(4);
				rowData[4] = rs.getObject(5);
				list.add(rowData);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs, st);
		}
		
		return list;
	}

	@Override
	public boolean isShowAttachmentDetail() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTableIDDetail() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDocAction() {
		// TODO Auto-generated method stub
		return null;
	}
}	//	MPriceListVersion
