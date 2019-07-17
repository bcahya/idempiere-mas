/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.MCurrency;
import org.compiere.model.MPriceList;
import org.compiere.model.PO;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.uns.base.model.Query;

import com.unicore.model.factory.UNSOrderModelFactory;

/**
 * @author ALBURHANY
 *
 */
public class MUNSExpeditionDetail extends X_UNS_ExpeditionDetail {

	private MUNSExpedition m_Exp;
	private MUNSExpeditionPath m_ExpPath;
	private MCurrency m_Currency = null;
	boolean isJobSpecOrder = false;
	private I_M_PriceList m_priceList = null;
	private String errMsg = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7505593547710495635L;

	/**
	 * @param ctx
	 * @param UNS_ExpeditionDetail_ID
	 * @param trxName
	 */
	public MUNSExpeditionDetail(Properties ctx, int UNS_ExpeditionDetail_ID,
			String trxName) {
		super(ctx, UNS_ExpeditionDetail_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSExpeditionDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		
		// TODO Auto-generated constructor stub
	}
	
	public boolean beforeSave (boolean newRecord)
	{			
		if(!isJobSpecOrder){
			checkField();
			if(getM_Product_ID() > 0)
			{
				if(!existsOnPriceList())
				{
					log.saveError("Error", "Product is not on price list");
					return false;
				}
			}
			BigDecimal ONETHOUSAND = new BigDecimal(1000);
			setTonase(getWeight().divide(ONETHOUSAND).multiply(getQty()));
			BigDecimal lineNetAmt = Env.ZERO;
			I_M_PriceList priceList = getM_PriceList();
			getPriceActual();
			getPriceLimit();
			getPriceList();
			if (getM_PriceList().isEnforcePriceLimit() 
					&& getPriceActual().compareTo(getPriceLimit()) == -1)
			{
				throw new AdempiereException("Price Actual below than Price Limit");
			}
			String ff = priceList.getBasicCalculation();
			if (null == ff)
			{
				throw new AdempiereUserError("Can't initialize Price List.");
			}
			
			else if (priceList.getBasicCalculation().equals(
					MPriceList.BASICCALCULATION_Wholesale))
			{
				checkMultipleLines ();
			}
			switch (getM_PriceList().getBasicCalculation())
			{
				case MPriceList.BASICCALCULATION_Volume :
					lineNetAmt = getPriceActual().multiply(getVolume()).
							multiply(getQty());
					break;
				case MPriceList.BASICCALCULATION_Weight :
					lineNetAmt = getPriceActual().multiply(getWeight()).
							multiply(getQty());
					break;
				case MPriceList.BASICCALCULATION_Wholesale :
					lineNetAmt = getPriceActual();
					break;
				default : break;
			}
			setLineNetAmt(lineNetAmt);
		}
		
		return true;
	}
	
	public boolean afterSave (boolean newRecord, boolean success)
	{
		if(!isJobSpecOrder)
		{
			if(newRecord || is_ValueChanged(COLUMNNAME_LineNetAmt))
				updateParentAmount();
			if(newRecord || is_ValueChanged(COLUMNNAME_Qty) || is_ValueChanged(COLUMNNAME_Volume)
					|| is_ValueChanged(COLUMNNAME_Weight))
				updateParentTonVol();
		}
		
		boolean needCreateItems = newRecord || is_ValueChanged(COLUMNNAME_M_Movement_ID)
									|| is_ValueChanged(COLUMNNAME_UNS_PackingList_ID)
										|| is_ValueChanged(COLUMNNAME_C_Order_ID)
											|| is_ValueChanged(COLUMNNAME_M_InOut_ID);
		
		if(needCreateItems && !detailItems())
		{
			log.saveError("Error", errMsg);
			return false;
		}
		
		return true;
	}
	
	private boolean detailItems()
	{
		if(isManual())
		{
			if(!getUNS_Expedition().isSOTrx())
			{
				String clear = "DELETE FROM UNS_ExpeditionItems WHERE UNS_ExpeditionSign_ID IN (SELECT UNS_ExpeditionSign_ID"
						+ " FROM UNS_ExpeditionSign WHERE UNS_ExpeditionDetailPO_ID=?)";
				DB.executeUpdate(clear, get_ID(), false, get_TrxName());
				
				clear = "DELETE FROM UNS_ExpeditionSign WHERE UNS_ExpeditionDetailPO_ID=?";
				DB.executeUpdate(clear, get_ID(), false, get_TrxName());
			}
			else
			{
				String clear = "DELETE FROM UNS_ExpeditionItems WHERE UNS_ExpeditionSign_ID IN (SELECT UNS_ExpeditionSign_ID"
						+ " FROM UNS_ExpeditionSign WHERE UNS_ExpeditionDetailSO_ID=?)";
				DB.executeUpdate(clear, get_ID(), false, get_TrxName());
				
				clear = "DELETE FROM UNS_ExpeditionSign WHERE UNS_ExpeditionDetailSO_ID=?";
				DB.executeUpdate(clear, get_ID(), false, get_TrxName());
			}
			
			if(!createItems())
				return false;
		}
		else if(!isManual())
		{
			String sql = "UPDATE UNS_ExpeditionSign SET UNS_ExpeditionDetailSO_ID=? WHERE UNS_ExpeditionDetailPO_ID"
						+ " IN (SELECT UNS_ExpeditionDetail_ID FROM UNS_ExpeditionDetail WHERE UNS_Expedition_ID=?)";
			int count = DB.executeUpdate(sql, new Object[]{get_ID(), getUNS_Expedition().getJobSO_ID()}, false, get_TrxName());
			if(count < 0)
				errMsg = "Error when trying copy detail items";
		}
		
		return true;
	}
	public boolean afterDelete(boolean success)
	{
		updateParentAmount();
		updateParentTonVol();
		return true;
	}
	
	public boolean updateParentAmount()
	{
		if(getUNS_ExpeditionPath_ID() > 0)
		{
			m_ExpPath = new MUNSExpeditionPath(getCtx(), getUNS_ExpeditionPath_ID(), get_TrxName());
			
			String up = "SELECT COALESCE(SUM(LineNetAmt), 0) FROM UNS_ExpeditionDetail"
							+ " WHERE UNS_ExpeditionPath_ID = ?";
			BigDecimal sumLineNetAmt = DB.getSQLValueBD(get_TrxName(), up, getUNS_ExpeditionPath_ID());
			
			m_ExpPath.setTotalAmt(sumLineNetAmt);
			if(!m_ExpPath.save())
				throw new AdempiereException("Cannot Update Parent");
		}
		
		if(getUNS_Expedition_ID() > 0)
		{
			m_Exp = new MUNSExpedition(getCtx(), getUNS_Expedition_ID(), get_TrxName());
			String up = "SELECT COALESCE(SUM(LineNetAmt), 0) FROM UNS_ExpeditionDetail"
					+ " WHERE UNS_Expedition_ID = ?";
			BigDecimal sumLineNetAmt = DB.getSQLValueBD(get_TrxName(), up, getUNS_Expedition_ID());
	
			m_Exp.setTotalAmt(sumLineNetAmt);
			if(!m_Exp.save())
				throw new AdempiereException("Cannot Update Header");
		}
		return true;
	}
	
	public boolean updateParentTonVol()
	{
		if(getUNS_ExpeditionPath_ID() > 0)
		{
			m_ExpPath = new MUNSExpeditionPath(getCtx(), getUNS_ExpeditionPath_ID(), get_TrxName());
			
			String upTon = "SELECT COALESCE(SUM(Tonase), 0) FROM UNS_ExpeditionDetail"
							+ " WHERE UNS_ExpeditionPath_ID = ?";
			BigDecimal sumTonase = DB.getSQLValueBD(get_TrxName(), upTon, getUNS_ExpeditionPath_ID());
			
			String upVol = "SELECT COALESCE(SUM(Volume), 0) FROM UNS_ExpeditionDetail"
					+ " WHERE UNS_ExpeditionPath_ID = ?";
			BigDecimal sumVolume = DB.getSQLValueBD(get_TrxName(), upVol, getUNS_ExpeditionPath_ID());
	
			m_ExpPath.setTonase(sumTonase);
			m_ExpPath.setVolume(sumVolume);
			if(!m_ExpPath.save())
				throw new AdempiereException("Cannot Update Parent");
		}
		
		if(getUNS_Expedition_ID() > 0)
		{
			m_Exp = new MUNSExpedition(getCtx(), getUNS_Expedition_ID(), get_TrxName());
			
			String upTon = "SELECT COALESCE(SUM(Tonase), 0) FROM UNS_ExpeditionDetail"
					+ " WHERE UNS_Expedition_ID = ?";
			BigDecimal sumTonase = DB.getSQLValueBD(get_TrxName(), upTon, getUNS_Expedition_ID());
			
			String upVol = "SELECT COALESCE(SUM(Volume), 0) FROM UNS_ExpeditionDetail"
					+ " WHERE UNS_Expedition_ID = ?";
			BigDecimal sumVolume = DB.getSQLValueBD(get_TrxName(), upVol, getUNS_Expedition_ID());
	
			m_Exp.setTonase(sumTonase);
			m_Exp.setVolume(sumVolume);
			if(!m_Exp.save())
				throw new AdempiereException("Cannot Update Header");
		}
		return true;
	}
	
	@Override
	public BigDecimal getPriceActual ()
	{
		BigDecimal priceActual = super.getPriceActual();
		if (priceActual.signum() > 0)
		{
			return priceActual;
		}
		else if(getUNS_Expedition_ID() > 0)
		{
			priceActual = getUNS_Expedition().getPriceActual();
		}
		
		else if(getUNS_ExpeditionPath_ID() > 0)
		{
			priceActual = getUNS_ExpeditionPath().getPriceActual();
		}	
		setPriceActual(priceActual);
		return priceActual;
	}
	
	@Override
	public BigDecimal getPriceList ()
	{
		BigDecimal priceList = super.getPriceList();
		if (priceList.signum() > 0)
		{
			return priceList;
		}
		else if(getUNS_Expedition_ID() > 0)
		{
			priceList = getUNS_Expedition().getPriceList();
		}
		
		else if(getUNS_ExpeditionPath_ID() > 0)
		{
			priceList = getUNS_ExpeditionPath().getPriceList();
		}	
		setPriceList(priceList);
		return priceList;
	}
	
	@Override
	public BigDecimal getPriceLimit ()
	{
		BigDecimal priceLimit = super.getPriceLimit();
		if (priceLimit.signum() > 0)
		{
			return priceLimit;
		}
		else if(getUNS_Expedition_ID() > 0)
		{
			priceLimit = getUNS_Expedition().getPriceLimit();
		}
		
		else if(getUNS_ExpeditionPath_ID() > 0)
		{
			priceLimit = getUNS_ExpeditionPath().getPriceLimit();
		}	
		setPriceLimit(priceLimit);
		return priceLimit;
	}
	
	public boolean checkField()
	{
		if(getQty().compareTo(Env.ZERO) <= 0 ||
				getWeight().compareTo(Env.ZERO) <= 0 ||
					getVolume().compareTo(Env.ZERO) <= 0)
			throw new AdempiereException("#Qty #Volume #Weight -- Cannot 0 or Negate");
			
		return true;
	}
	
	@Override
	public void setLineNetAmt(BigDecimal LineNetAmt)
	{
		int precision = getCurrency().getStdPrecision();
		BigDecimal roundValue = LineNetAmt.setScale(precision, RoundingMode.HALF_UP);
		
		super.setLineNetAmt(roundValue);
	}
	
	public MCurrency getCurrency()
	{
		if(m_Currency != null)
			return m_Currency;
		m_Exp = new MUNSExpedition(getCtx(), getUNS_Expedition_ID(), get_TrxName());
		m_Currency = (MCurrency) m_Exp.getCurrency();
		
		return m_Currency;
	}
	
	public I_M_PriceList getM_PriceList ()
	{
		m_priceList = getUNS_Expedition_ID() > 0 ?
				getUNS_Expedition().getM_PriceList() :
					getUNS_ExpeditionPath_ID() > 0 ? 
							getUNS_ExpeditionPath().getM_PriceList() :
								null;
		
		return m_priceList;
	}
	
	public void checkMultipleLines ()
	{
		MUNSExpeditionDetail[] details = null;
		if (isMultiplePath())
		{
			details = MUNSExpeditionDetail.gets((PO) getUNS_ExpeditionPath());
		}
		else
		{
			details = MUNSExpeditionDetail.gets((PO) getUNS_Expedition());
		}
		
		for (int i=0; i<details.length; i++)
		{
			if (details[i].get_ID() == get_ID())	{	continue;}
			throw new AdempiereException("The type of Price List you're choose "
					+ "is wholesale. You can't create multiple lines.");
		}
	}
	
	public static MUNSExpeditionDetail[] gets (PO po)
	{
		String wc = po.get_TableName() + "_ID = ?";
		List<MUNSExpeditionDetail> list = Query.get(
				po.getCtx(), UNSOrderModelFactory.EXTENSION_ID, Table_Name, 
				wc, po.get_TrxName()).setParameters(po.get_ID()).
				setOnlyActiveRecords(true).list();
		
		MUNSExpeditionDetail[] lines = new MUNSExpeditionDetail[list.size()];
		list.toArray(lines);
		
		return lines;
	}
	
	public boolean isMultiplePath ()
	{
		return getUNS_ExpeditionPath_ID() > 0;
	}
	public boolean isJobSpecOrder(boolean _isJobSpecOrder)
	{
		isJobSpecOrder = _isJobSpecOrder;
		
		return isJobSpecOrder;
	}
	
	public boolean createItems()
	{
		String tableName = getUNS_PackingList_ID() > 0 ? "UNS_PackingList"
								: getM_Movement_ID() > 0 ? "M_Movement"
										: getC_Order_ID() > 0 ? "C_Order"
												: getM_InOut_ID() > 0 ? "M_InOut"
														: null;
		if(tableName == null)
			return true;
		
		switch (tableName) {
		case "UNS_PackingList": return fromPackingList();
		case "M_Movement": return fromMovement();
		case "C_Order": return fromOrder();
		case "M_InOut": return fromInOut();
		default:
			break;
		}
		
		return true;
	}
	
	private boolean fromPackingList()
	{
		String sql = "SELECT bp.C_BPartner_ID, io.C_BPartner_Location_ID, iol.M_Product_ID, SUM(iol.MovementQty), iol.C_UOM_ID FROM UNS_PackingList pl"
				+ " INNER JOIN UNS_PackingList_Order plo ON plo.UNS_PackingList_ID = pl.UNS_PackingList_ID"
				+ " INNER JOIN UNS_PackingList_Line pll ON pll.UNS_PackingList_Order_ID = plo.UNS_PackingList_Order_ID"
				+ " INNER JOIN M_InOut io ON io.M_InOut_ID = plo.M_InOut_ID"
				+ " INNER JOIN M_InOutLine iol ON iol.M_InOutLine_ID = pll.M_InOutLine_ID AND iol.M_InOut_ID = io.M_InOut_ID"
				+ " INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = io.C_BPartner_ID"
				+ " WHERE pl.UNS_PackingList_ID=? GROUP BY bp.C_BPartner_ID, iol.M_Product_ID, iol.C_UOM_ID,"
				+ " io.C_BPartner_Location_ID ORDER BY bp.Name ASC";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Hashtable<Integer, MUNSExpeditionSign> mapSign = new Hashtable<>();
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, getUNS_PackingList_ID());
			rs = stmt.executeQuery();
			while (rs.next()) {
				MUNSExpeditionSign sign = mapSign.get(rs.getInt(1));
				
				if(null == sign)
				{
					sign = new MUNSExpeditionSign(getCtx(), 0, get_TrxName());
					sign.setUNS_ExpeditionDetailPO_ID(get_ID());
					sign.setAD_Org_ID(getAD_Org_ID());
					sign.setC_BPartner_ID(rs.getInt(1));
					sign.setC_BPartner_Location_ID(rs.getInt(2));
					sign.setIsManual(false);
					if(!sign.save())
					{
						errMsg = "Failed when create Sign";
						return false;
					}
					
					mapSign.put(sign.getC_BPartner_ID(), sign);
				}
				
				MUNSExpeditionItems items = new MUNSExpeditionItems(getCtx(), 0, get_TrxName());
				items.setUNS_ExpeditionSign_ID(sign.get_ID());
				items.setAD_Org_ID(sign.getAD_Org_ID());
				items.setM_Product_ID(rs.getInt(3));
				items.setQty(rs.getBigDecimal(4));
				items.setC_UOM_ID(rs.getInt(5));
				items.setIsManual(false);
				if(!items.save())
				{
					errMsg = "Failed when create items";
					return false;
				}
			}
		} catch (SQLException e) {
			errMsg = e.getMessage();
			return false;
		}
		return true;
	}
	
	private boolean fromMovement()
	{
		String sql = "SELECT (CASE WHEN dt.IsInTransit = 'Y' THEN orgwh.Name ELSE org.Name END), p.M_Product_ID, SUM(ml.MovementQty)"
				+ ", p.C_UOM_ID FROM M_Movement m"
				+ " INNER JOIN M_MovementLine ml ON ml.M_Movement_ID = m.M_Movement_ID"
				+ " INNER JOIN AD_Org org ON org.AD_Org_ID = m.AD_Org_ID"
				+ " INNER JOIN M_Product p ON p.M_Product_ID = ml.M_Product_ID"
				+ " LEFT JOIN M_Warehouse wh ON wh.M_Warehouse_ID = m.DestinationWarehouse_ID"
				+ " LEFT JOIN AD_Org orgwh ON orgwh.AD_Org_ID = wh.AD_Org_ID"
				+ " INNER JOIN C_DocType dt ON dt.C_DocType_ID = m.C_DocType_ID"
				+ " WHERE m.M_Movement_ID=? GROUP BY p.M_Product_ID, dt.C_DocType_ID, orgwh.AD_Org_ID, org.AD_Org_ID";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Hashtable<String, MUNSExpeditionSign> mapSign = new Hashtable<>();
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, getM_Movement_ID());
			rs = stmt.executeQuery();
			while (rs.next()) {
				MUNSExpeditionSign sign = mapSign.get(rs.getString(1));
				
				if(null == sign)
				{
					sign = new MUNSExpeditionSign(getCtx(), 0, get_TrxName());
					sign.setUNS_ExpeditionDetailPO_ID(get_ID());
					sign.setAD_Org_ID(getAD_Org_ID());
					sign.setBPartnerName(rs.getString(1));
					sign.setAddress("-");
					sign.setIsManual(false);
					if(!sign.save())
					{
						errMsg = "Failed when create Sign";
						return false;
					}
					
					mapSign.put(sign.getBPartnerName(), sign);
				}
				
				MUNSExpeditionItems items = new MUNSExpeditionItems(getCtx(), 0, get_TrxName());
				items.setUNS_ExpeditionSign_ID(sign.get_ID());
				items.setAD_Org_ID(sign.getAD_Org_ID());
				items.setM_Product_ID(rs.getInt(2));
				items.setQty(rs.getBigDecimal(3));
				items.setC_UOM_ID(rs.getInt(4));
				items.setIsManual(false);
				if(!items.save())
				{
					errMsg = "Failed when create items";
					return false;
				}
			}
		} catch (SQLException e) {
			errMsg = e.getMessage();
			return false;
		}
		
		return true;
	}
	
	private boolean fromOrder()
	{
		String sql = "SELECT o.C_BPartner_ID, o.C_BPartner_Location_ID, ol.M_Product_ID, SUM((ol.QtyOrdered-ol.QtyDelivered)),"
				+ " ol.C_UOM_ID FROM C_Order o"
				+ " INNER JOIN C_OrderLine ol ON ol.C_Order_ID = o.C_Order_ID"
				+ " WHERE ol.M_Product_ID IS NOT NULL AND o.C_Order_ID=? GROUP BY o.C_Order_ID, ol.M_Product_ID, ol.C_UOM_ID";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Hashtable<Integer, MUNSExpeditionSign> mapSign = new Hashtable<>();
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, getC_Order_ID());
			rs = stmt.executeQuery();
			while (rs.next()) {
				MUNSExpeditionSign sign = mapSign.get(rs.getInt(1));
				
				if(null == sign)
				{
					sign = new MUNSExpeditionSign(getCtx(), 0, get_TrxName());
					sign.setUNS_ExpeditionDetailPO_ID(get_ID());
					sign.setAD_Org_ID(getAD_Org_ID());
					sign.setC_BPartner_ID(rs.getInt(1));
					sign.setC_BPartner_Location_ID(rs.getInt(2));
					sign.setIsManual(false);
					if(!sign.save())
					{
						errMsg = "Failed when create Sign";
						return false;
					}
					
					mapSign.put(sign.getC_BPartner_ID(), sign);
				}
				
				if(rs.getBigDecimal(4).compareTo(Env.ZERO) != 0)
				{
					MUNSExpeditionItems items = new MUNSExpeditionItems(getCtx(), 0, get_TrxName());
					items.setUNS_ExpeditionSign_ID(sign.get_ID());
					items.setAD_Org_ID(sign.getAD_Org_ID());
					items.setM_Product_ID(rs.getInt(3));
					items.setQty(rs.getBigDecimal(4));
					items.setC_UOM_ID(rs.getInt(5));
					items.setIsManual(false);
					if(!items.save())
					{
						errMsg = "Failed when create items";
						return false;
					}
				}
			}
		} catch (SQLException e) {
			errMsg = e.getMessage();
			return false;
		}
		
		return true;
	}
	
	private boolean fromInOut()
	{
		String sql = "SELECT io.C_BPartner_ID, io.C_BPartner_Location_ID, iol.M_Product_ID, SUM((iol.MovementQty)),"
				+ " iol.C_UOM_ID FROM M_InOut io"
				+ " INNER JOIN M_InOutLine iol ON iol.M_InOut_ID = io.M_InOut_ID"
				+ " WHERE iol.M_Product_ID IS NOT NULL AND io.M_InOut_ID=? GROUP BY io.M_InOut_ID, iol.M_Product_ID, iol.C_UOM_ID";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Hashtable<Integer, MUNSExpeditionSign> mapSign = new Hashtable<>();
			stmt = DB.prepareStatement(sql, get_TrxName());
			stmt.setInt(1, getM_InOut_ID());
			rs = stmt.executeQuery();
			while (rs.next()) {
				MUNSExpeditionSign sign = mapSign.get(rs.getInt(1));
				
				if(null == sign)
				{
					sign = new MUNSExpeditionSign(getCtx(), 0, get_TrxName());
					sign.setUNS_ExpeditionDetailPO_ID(get_ID());
					sign.setAD_Org_ID(getAD_Org_ID());
					sign.setC_BPartner_ID(rs.getInt(1));
					sign.setC_BPartner_Location_ID(rs.getInt(2));
					sign.setIsManual(false);
					if(!sign.save())
					{
						errMsg = "Failed when create Sign";
						return false;
					}
					
					mapSign.put(sign.getC_BPartner_ID(), sign);
				}
				
				if(rs.getBigDecimal(4).compareTo(Env.ZERO) != 0)
				{
					MUNSExpeditionItems items = new MUNSExpeditionItems(getCtx(), 0, get_TrxName());
					items.setUNS_ExpeditionSign_ID(sign.get_ID());
					items.setAD_Org_ID(sign.getAD_Org_ID());
					items.setM_Product_ID(rs.getInt(3));
					items.setQty(rs.getBigDecimal(4));
					items.setC_UOM_ID(rs.getInt(5));
					items.setIsManual(false);
					if(!items.save())
					{
						errMsg = "Failed when create items";
						return false;
					}
				}
			}
		} catch (SQLException e) {
			errMsg = e.getMessage();
			return false;
		}
		
		return true;
	}
	
	private boolean existsOnPriceList()
	{
		String sql = "SELECT COUNT(pp.*) FROM M_ProductPrice pp"
				+ " INNER JOIN M_PriceList_Version mvp ON mvp.M_PriceList_Version_ID = pp.M_PriceList_Version_ID"
				+ " WHERE mvp.ValidFrom <= ? AND pp.IsActive = 'Y' AND pp.M_Product_ID = ?";
		int exists = DB.getSQLValue(get_TrxName(), sql, new Object[]{getUNS_Expedition().getDateDoc(), getM_Product_ID()});
		
		return exists > 0;
	}
}