/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.model.MOrderLine;

/**
 * @author UNTA-Andy
 * 
 */
public class MUNSDiscountTrx extends X_UNS_DiscountTrx
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8666800345230464715L;
	private static Logger s_log;
	public static final int ORDER_DISCOUNTTRX_REF = 1;
	public static final int ORDERLINE_DISCOUNTTRX_REF = -1;

	/**
	 * @param ctx
	 * @param UNS_DiscountTrx_ID
	 * @param trxName
	 */
	public MUNSDiscountTrx(Properties ctx, int UNS_DiscountTrx_ID, String trxName)
	{
		super(ctx, UNS_DiscountTrx_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSDiscountTrx(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public void setDiscountTrx(MUNSDSBreakLine breakLine, MDiscountSchemaBreak DSbreak,
			UNSDiscountBonus discountBonus)
	{
		String tableName = MTable.getTableName(getCtx(), discountBonus.getTable_ID());
		if(null == tableName || "".equals(tableName))
		{	
			throw new AdempiereException("Not found table with id : " 
						+ discountBonus.getTable_ID());
		}
		
		String coumnname = tableName + "_ID";
		set_Value(coumnname, discountBonus.getRecord_ID());
		if(DSbreak != null)
			setM_DiscountSchemaBreak_ID(DSbreak.get_ID());
		if(breakLine != null)
			setUNS_DSBreakLine_ID(breakLine.get_ID());

		setFirstDiscount(Env.ZERO);
		setSecondDiscount(Env.ZERO);
		setThirdDiscount(Env.ZERO);
		setFourthDiscount(Env.ZERO);
		setFifthDiscount(Env.ZERO);

	}
	
	/**
	 * 
	 * @param discountBonus
	 * @param po
	 * @return
	 */
	public static MUNSDiscountTrx get(UNSDiscountBonus discountBonus, PO po)
	{
		MUNSDiscountTrx discountTrx = null;
		
		StringBuilder preparedSQL = new StringBuilder("SELECT * FROM ")
		.append(Table_Name).append(" WHERE ")
		.append(MTable.getTableName(discountBonus.getCtx(), discountBonus.getTable_ID()))
		.append("_ID = ").append(discountBonus.getRecord_ID())
		.append(" AND ").append(COLUMNNAME_UNS_DiscountBonus_ID)
		.append(" IS NULL");
		
		if(po != null)
		{
			preparedSQL.append(" AND ").append(po.get_TableName())
			.append("_ID = ").append(po.get_ID());
		}
		
		String sql = preparedSQL.toString();
		PreparedStatement stm = null;
		ResultSet rs = null;
		
		try {
			stm = DB.prepareStatement(sql, discountBonus.getTrxName());
			rs = stm.executeQuery();
			if(rs.next())
			{
				discountTrx = new MUNSDiscountTrx(
						discountBonus.getCtx(), rs, discountBonus.getTrxName());
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new AdempiereException("Error query on get Discount trx");
		}
		
		return discountTrx;
	}

	@Deprecated
	public static MUNSDiscountTrx get(UNSDiscountBonus discountBonus)
	{
		String sql =
				"SELECT * FROM UNS_DiscountTrx WHERE "
						+ MTable.getTableName(discountBonus.getCtx(), discountBonus.getTable_ID())
						+ "_ID=? ORDER BY SeqNo";
		ArrayList<MUNSDiscountTrx> list = new ArrayList<MUNSDiscountTrx>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, discountBonus.getTrxName());
			pstmt.setInt(1, discountBonus.getRecord_ID());
			rs = pstmt.executeQuery();
			if (rs.next())
				list.add(new MUNSDiscountTrx(discountBonus.getCtx(), rs, discountBonus.getTrxName()));
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (list.size() == 0)
			return new MUNSDiscountTrx(discountBonus.getCtx(), 0, discountBonus.getTrxName());

		MUNSDiscountTrx[] trxs = new MUNSDiscountTrx[list.size()];
		list.toArray(trxs);

		return trxs[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.compiere.model.PO#beforeSave(boolean)
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getSeqNo() <= 0)
		{
			String SQL =
					"SELECT COALESCE(MAX(SeqNo),0)  FROM UNS_DiscountTrx WHERE C_OrderLine_ID=? OR C_InvoiceLine_ID=?";
			int line = DB.getSQLValue(get_TrxName(), SQL, getC_OrderLine_ID(), getC_InvoiceLine_ID());
			setSeqNo(line + 10);
		}
		
		checkDiscount();
		
		return super.beforeSave(newRecord);
	}

	public void setZeroValueDiscount()
	{
		setFlatPercentDiscount(Env.ZERO);
		setFlatValueDiscount(Env.ZERO);
		setFirstDiscount(Env.ZERO);
		setSecondDiscount(Env.ZERO);
		setThirdDiscount(Env.ZERO);
		setFourthDiscount(Env.ZERO);
		setFifthDiscount(Env.ZERO);
		setDiscountValue1st(Env.ZERO);
		setDiscountValue2nd(Env.ZERO);
		setDiscountValue3rd(Env.ZERO);
		setDiscountValue4th(Env.ZERO);
		setDiscountValue5th(Env.ZERO);
	}

	public BigDecimal getSUMDiscountValue()
	{
		BigDecimal sumValue = Env.ZERO;

		sumValue =
				getDiscountValue1st().add(getDiscountValue2nd()).add(getDiscountValue3rd())
						.add(getDiscountValue4th()).add(getDiscountValue5th());

		return sumValue;
	}
	
	/**
	 * Get discount trx of order or order line
	 * @param discountTrxRef 1= order | -1= order line
	 * @param ID =>id of order or order line
	 * @param trxName
	 * @return
	 */
	public static MUNSDiscountTrx[] getOf(String TableName, int ID, String trxName)
	{
		List<MUNSDiscountTrx> list = new ArrayList<MUNSDiscountTrx>();
		String whereClause = null;
		if(Util.isEmpty(TableName, true))
			throw new AdempiereException("null table name");
		else
			whereClause = TableName + "_ID = ?";
//		if(discountTrxRef == ORDER_DISCOUNTTRX_REF)
//		{
//			whereClause = MUNSDiscountTrx.COLUMNNAME_C_Order_ID + " =?";
//		}
//		else if(discountTrxRef == ORDERLINE_DISCOUNTTRX_REF)
//		{
//			whereClause = MUNSDiscountTrx.COLUMNNAME_C_OrderLine_ID + " =?";
//		}
//		else
//		{
//			throw new AdempiereException("null where clause!");
//		}
		
		StringBuilder sqlBuild = new StringBuilder("SELECT ");
		sqlBuild.append(" * FROM ").append(MUNSDiscountTrx.Table_Name)
				.append(" WHERE ").append(whereClause);
		
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = DB.prepareStatement(sqlBuild.toString(), trxName);
			stm.setInt(1, ID);
			rs = stm.executeQuery();
			while (rs.next()){
				MUNSDiscountTrx discountTrx = new MUNSDiscountTrx(Env.getCtx(), rs, trxName);
				list.add(discountTrx);
			}
		} catch (SQLException e) {
			throw new AdempiereException("Failed run query : " + e.getMessage());
		} finally {
			DB.close(rs, stm);
		}
		
		
		MUNSDiscountTrx[] discountsTrx = new MUNSDiscountTrx[list.size()];
		list.toArray(discountsTrx);
		
		return discountsTrx;
	}
	
	public MUNSDiscountTrx(UNSDiscountBonus discountBonus)
	{
		super(discountBonus.getCtx(), 0, discountBonus.getTrxName());
		setAD_Org_ID(discountBonus.getOrgTrx_ID());
	}
	
	public void updateReserveQty(boolean revert)
	{
		BigDecimal qtyVal = getQtyValDiscounted();
		
		if(revert)
		{
			qtyVal = qtyVal.negate();
		}
		
		int C_BPartner_ID = 0;
		int salesRep_ID = 0;
		int AD_Org_ID = 0;
		
		if(getC_Order_ID() > 0)
		{
			String sql = "SELECT CONCAT(COALESCE(o.C_BPartner_ID, 0), '@'" 
					+ ", COALESCE(o.SalesRep_ID, 0),'@', COALESCE (o.AD_ORg_ID))"
					+ " FROM C_Order o WHERE o.C_Order_ID = ?";
			String retVal = DB.getSQLValueString(get_TrxName(), sql, getC_Order_ID());
			String[] spilted = retVal.split("@");
			
			C_BPartner_ID = new Integer(spilted[0]);
			salesRep_ID = new Integer(spilted[1]);
			AD_Org_ID = new Integer(2);
		}
		else if(getC_OrderLine_ID() > 0)
		{
			String sql = "SELECT CONCAT(COALESCE(o.C_BPartner_ID, 0), '@'" 
					+ ", COALESCE((SELECT C_BPartner_ID FROM AD_User WHERE AD_User_ID = o.SalesRep_ID), 0),'@', COALESCE (o.AD_ORg_ID))"
					+ " FROM C_OrderLine ol INNER JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID "
					+ " WHERE ol.C_OrderLine_ID = ?";
			
			String retVal = DB.getSQLValueString(get_TrxName(), sql, getC_OrderLine_ID());
			String[] splited = retVal.split("@");
			
			C_BPartner_ID = new Integer(splited[0]);
			salesRep_ID = new Integer(splited[1]);
			AD_Org_ID = new Integer(splited[2]);
		}
		else if(getC_Invoice_ID() > 0)
		{
			String sql = "SELECT CONCAT(COALESCE(o.C_BPartner_ID, 0), '@'" 
					+ ", COALESCE((SELECT C_BPartner_ID FROM AD_User WHERE AD_User_ID = o.SalesRep_ID), 0),'@', COALESCE (o.AD_ORg_ID))"
					+ " FROM C_Invoice o WHERE o.C_Invoice_ID = ?";
			String retVal = DB.getSQLValueString(get_TrxName(), sql, getC_Invoice_ID());
			String[] spilted = retVal.split("@");
			
			C_BPartner_ID = new Integer(spilted[0]);
			salesRep_ID = new Integer(spilted[1]);
			AD_Org_ID = new Integer(spilted[2]);
		}
		else if(getC_InvoiceLine_ID() > 0)
		{
			String sql = "SELECT CONCAT(COALESCE(o.C_BPartner_ID, 0), '@'" 
					+ ", COALESCE((SELECT C_BPartner_ID FROM AD_User WHERE AD_User_ID = o.SalesRep_ID), 0),'@', "
					+ " COALESCE (o.AD_ORg_ID))"
					+ " FROM C_InvoiceLine ol INNER JOIN C_Invoice o ON o.C_Invoice_ID = ol.C_Order_ID "
					+ " INNER JOIN M_Product p ON p.M_Product_ID = ol.M_Product_ID "
					+ " WHERE ol.C_OrderLine_ID = ?";
			
			String retVal = DB.getSQLValueString(get_TrxName(), sql, getC_InvoiceLine_ID());
			String[] splited = retVal.split("@");
			
			C_BPartner_ID = new Integer(splited[0]);
			salesRep_ID = new Integer(splited[1]);
			AD_Org_ID = new Integer(splited[2]);
		}
		
		if(getM_DiscountSchema_ID() > 0)
		{
			MDiscountSchema schema = new MDiscountSchema(
					getCtx(), getM_DiscountSchema_ID(), get_TrxName());
			if(schema.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_CustomerBudget)
					||schema.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_CustomerGradeBudget)
					||schema.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_CustomerGroupBudget))
				schema.updateReserveQtyVal(qtyVal, C_BPartner_ID);	
			else if(schema.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget))
				schema.updateReserveQtyVal(qtyVal, salesRep_ID);
			else if (schema.getBudgetType().equals("OB"))
				schema.updateReserveQtyVal(qtyVal, AD_Org_ID);
			else
				schema.updateReserveQtyVal(qtyVal, 0);
		}
		else if(getM_DiscountSchemaBreak_ID() > 0)
		{
			MDiscountSchemaBreak dsBreak = new MDiscountSchemaBreak(
					getCtx(), getM_DiscountSchemaBreak_ID(), get_TrxName());
			if (dsBreak.getBudgetCalculation().equals(MDiscountSchemaBreak.BUDGETCALCULATION_Amount))
			{
				qtyVal = getDiscountedAmt();
				if (revert)
					qtyVal = qtyVal.negate();
			}
			
			if(dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerBudget)
					||dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGradeBudget)
					||dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGroupBudget))
				dsBreak.updateReserveQtyVal(qtyVal, C_BPartner_ID);	
			else if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget))
				dsBreak.updateReserveQtyVal(qtyVal, salesRep_ID);	
			else if (dsBreak.getBudgetType().equals("OB"))
				dsBreak.updateReserveQtyVal(qtyVal, AD_Org_ID);
			else
				dsBreak.updateReserveQtyVal(qtyVal, 0);
		}
		else if(getUNS_DSBreakLine_ID() > 0)
		{
			MUNSDSBreakLine dsBreakLine = new MUNSDSBreakLine(
					getCtx(), getUNS_DSBreakLine_ID(), get_TrxName());
			MDiscountSchemaBreak dsBreak = new MDiscountSchemaBreak(
					getCtx(), dsBreakLine.getM_DiscountSchemaBreak_ID(), get_TrxName());

			if (dsBreak.getBudgetCalculation().equals(MDiscountSchemaBreak.BUDGETCALCULATION_Amount))
			{
				qtyVal = getDiscountedAmt();
				if (revert)
					qtyVal = qtyVal.negate();
			}
			if(dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerBudget)
					||dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGradeBudget)
					||dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGroupBudget))
				dsBreakLine.updateReserveQtyVal(qtyVal, C_BPartner_ID);
			else if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget))
				dsBreakLine.updateReserveQtyVal(qtyVal, salesRep_ID);	
			else if (dsBreak.getBudgetType().equals("OB"))
				dsBreakLine.updateReserveQtyVal(qtyVal, AD_Org_ID);
			else
				dsBreakLine.updateReserveQtyVal(qtyVal, 0);
		}
	}
	
	public void updateShipReceiptQty(boolean isReserved)
	{
		BigDecimal qtyVal = getQtyValDiscounted();
		
		if(isReserved)
		{
			qtyVal = qtyVal.negate();
		}
		
		int C_BPartner_ID = 0;
		int salesRep_ID = 0;
		int orgID = 0;
		
		if(getC_Order_ID() > 0)
		{
			String sql = "SELECT CONCAT(COALESCE(o.C_BPartner_ID, 0), '@'" 
					+ ", COALESCE((SELECT C_BPartner_ID FROM AD_User WHERE AD_User_ID = o.SalesRep_ID), 0), '@', COALESCE (o.AD_Org_ID, 0))"
					+ " FROM C_Order o WHERE C_Order_ID = ?";
			String retVal = DB.getSQLValueString(get_TrxName(), sql, getC_Order_ID());
			String[] spilted = retVal.split("@");
			
			C_BPartner_ID = new Integer(spilted[0]);
			salesRep_ID = new Integer(spilted[1]);
			orgID = new Integer(spilted[2]);
		}
		else if(getC_OrderLine_ID() > 0)
		{
			String sql = "SELECT CONCAT(COALESCE(p.weight,0),'@', COALESCE(o.C_BPartner_ID, 0), '@'" 
					+ ", COALESCE((SELECT C_BPartner_ID FROM AD_User WHERE AD_User_ID = o.SalesRep_ID), 0), '@', COALESCE (o.AD_Org_ID, 0))"
					+ " FROM C_OrderLine ol INNER JOIN C_Order o ON o.C_Order_ID = ol.C_Order_ID "
					+ " INNER JOIN M_Product p ON p.M_Product_ID = ol.M_Product_ID "
					+ " WHERE ol.C_OrderLine_ID = ?";
			
			String retVal = DB.getSQLValueString(get_TrxName(), sql, getC_OrderLine_ID());
			String[] splited = retVal.split("@");
			
			C_BPartner_ID = new Integer(splited[1]);
			salesRep_ID = new Integer(splited[2]);
			orgID = new Integer(splited[3]);
		}
		else if(getC_Invoice_ID() > 0)
		{
			String sql = "SELECT CONCAT(COALESCE(o.C_BPartner_ID, 0), '@'" 
					+ ", COALESCE((SELECT C_BPartner_ID FROM AD_User WHERE AD_User_ID = o.SalesRep_ID), 0), '@', COALESCE (o.AD_Org_ID))"
					+ " FROM C_Invoice o WHERE o.C_Invoice_ID = ?";
			String retVal = DB.getSQLValueString(get_TrxName(), sql, getC_Invoice_ID());
			String[] spilted = retVal.split("@");
			
			C_BPartner_ID = new Integer(spilted[0]);
			salesRep_ID = new Integer(spilted[1]);
			orgID = new Integer(spilted[2]);
		}
		else if(getC_InvoiceLine_ID() > 0)
		{
			String sql = "SELECT CONCAT(COALESCE(p.weight,0),'@', COALESCE(o.C_BPartner_ID, 0), '@'" 
					+ ", COALESCE((SELECT C_BPartner_ID FROM AD_User WHERE AD_User_ID = o.SalesRep_ID), 0), '@', COALESCE (o.AD_Org_ID))"
					+ " FROM C_InvoiceLine ol INNER JOIN C_Invoice o ON o.C_Invoice_ID = ol.C_Invoice_ID "
					+ " INNER JOIN M_Product p ON p.M_Product_ID = ol.M_Product_ID "
					+ " WHERE ol.C_InvoiceLine_ID = ?";
			
			String retVal = DB.getSQLValueString(get_TrxName(), sql, getC_InvoiceLine_ID());
			String[] splited = retVal.split("@");
			
			C_BPartner_ID = new Integer(splited[1]);
			salesRep_ID = new Integer(splited[2]);
			orgID = new Integer(splited[3]);
		}
		
		if(getM_DiscountSchema_ID() > 0)
		{
			MDiscountSchema schema = new MDiscountSchema(
					getCtx(), getM_DiscountSchema_ID(), get_TrxName());
			
			if(schema.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerBudget)
					||schema.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGradeBudget)
					||schema.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGroupBudget))
				schema.updateShipReceiptQtyVal(qtyVal, C_BPartner_ID);	
			else if(schema.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget))
				schema.updateShipReceiptQtyVal(qtyVal, salesRep_ID);
			else if (schema.getBudgetType().equals("OB"))
				schema.updateShipReceiptQtyVal(qtyVal, orgID);
			else
				schema.updateShipReceiptQtyVal(qtyVal, 0);
		}
		else if(getM_DiscountSchemaBreak_ID() > 0)
		{
			MDiscountSchemaBreak dsBreak = new MDiscountSchemaBreak(
					getCtx(), getM_DiscountSchemaBreak_ID(), get_TrxName());

			if (dsBreak.getBudgetCalculation().equals(MDiscountSchemaBreak.BUDGETCALCULATION_Amount))
			{
				qtyVal = getDiscountedAmt();
				if (isReserved)
					qtyVal = qtyVal.negate();
			}
			if(dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerBudget)
					||dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGradeBudget)
					||dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGroupBudget))
				dsBreak.updateShipReceiptQtyVal(qtyVal, C_BPartner_ID);	
			else if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget))
				dsBreak.updateShipReceiptQtyVal(qtyVal, salesRep_ID);	
			else if (dsBreak.getBudgetType().equals("OB"))
				dsBreak.updateShipReceiptQtyVal(qtyVal, orgID);
			else
				dsBreak.updateShipReceiptQtyVal(qtyVal, 0);
		}
		else if(getUNS_DSBreakLine_ID() > 0)
		{
			MUNSDSBreakLine dsBreakLine = new MUNSDSBreakLine(
					getCtx(), getUNS_DSBreakLine_ID(), get_TrxName());
			MDiscountSchemaBreak dsBreak = new MDiscountSchemaBreak(
					getCtx(), dsBreakLine.getM_DiscountSchemaBreak_ID(), get_TrxName());
			if (dsBreak.getBudgetCalculation().equals(MDiscountSchemaBreak.BUDGETCALCULATION_Amount))
			{
				qtyVal = getDiscountedAmt();
				if (isReserved)
					qtyVal = qtyVal.negate();
			}
			if(dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerBudget)
					||dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGradeBudget)
					||dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGroupBudget))
				dsBreakLine.updateShipReceiptQtyVal(qtyVal, C_BPartner_ID);	
			else if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget))
				dsBreakLine.updateShipReceiptQtyVal(qtyVal, salesRep_ID);	
			else if (dsBreak.getBudgetType().equals("OB"))
				dsBreakLine.updateShipReceiptQtyVal(qtyVal, orgID);
			else
				dsBreakLine.updateShipReceiptQtyVal(qtyVal, 0);
		}
	}
	
	public void updateInvoicedQty(boolean revers)
	{
		BigDecimal qtyVal = getQtyValDiscounted();
		
		if(revers)
		{
			qtyVal = qtyVal.negate();
		}
		
		int C_BPartner_ID = 0;
		int salesRep_ID = 0;
		int orgID = 0;
		
		if(getC_Invoice_ID() > 0)
		{
			String sql = "SELECT CONCAT(COALESCE(i.C_BPartner_ID, 0), '@'" 
					+ ", COALESCE((SELECT C_BPartner_ID FROM AD_User WHERE AD_User_ID = i.SalesRep_ID), 0), '@', COALESCE (i.AD_Org_ID, 0))"
					+ " FROM C_Invoice i WHERE i.C_Invoice_ID = ?";
			String retVal = DB.getSQLValueString(get_TrxName(), sql, getC_Invoice_ID());
			String[] spilted = retVal.split("@");
			
			C_BPartner_ID = new Integer(spilted[0]);
			salesRep_ID = new Integer(spilted[1]);
			orgID = new Integer(spilted[2]);
		}
		else if(getC_InvoiceLine_ID() > 0)
		{
			String sql = "SELECT CONCAT(COALESCE(p.weight,0),'@', COALESCE(o.C_BPartner_ID, 0), '@'" 
					+ ", COALESCE((SELECT C_BPartner_ID FROM AD_User WHERE AD_User_ID = o.SalesRep_ID), 0), '@', COALESCE (o.AD_Org_ID, 0))"
					+ " FROM C_InvoiceLine ol INNER JOIN C_Invoice o ON o.C_Invoice_ID = ol.C_Invoice_ID "
					+ " INNER JOIN M_Product p ON p.M_Product_ID = ol.M_Product_ID "
					+ " WHERE ol.C_InvoiceLine_ID = ?";
			
			String retVal = DB.getSQLValueString(get_TrxName(), sql, getC_InvoiceLine_ID());
			String[] splited = retVal.split("@");
			
			C_BPartner_ID = new Integer(splited[1]);
			salesRep_ID = new Integer(splited[2]);
			orgID = new Integer(splited[3]);
		}
		
		if(getM_DiscountSchema_ID() > 0)
		{
			MDiscountSchema schema = new MDiscountSchema(
					getCtx(), getM_DiscountSchema_ID(), get_TrxName());
			
			if(schema.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerBudget)
					||schema.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGradeBudget)
					||schema.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGroupBudget))
				schema.updateInvoicedQtyVal(qtyVal, C_BPartner_ID);	
			else if(schema.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget))
				schema.updateInvoicedQtyVal(qtyVal, salesRep_ID);
			else if (schema.getBudgetType().equals("OB"))
				schema.updateInvoicedQtyVal(qtyVal, orgID);
			else
				schema.updateInvoicedQtyVal(qtyVal, 0);
		}
		else if(getM_DiscountSchemaBreak_ID() > 0)
		{
			MDiscountSchemaBreak dsBreak = new MDiscountSchemaBreak(
					getCtx(), getM_DiscountSchemaBreak_ID(), get_TrxName());

			if (dsBreak.getBudgetCalculation().equals(MDiscountSchemaBreak.BUDGETCALCULATION_Amount))
			{
				qtyVal = getDiscountedAmt();
				if (revers)
					qtyVal = qtyVal.negate();
			}
			if(dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerBudget)
					||dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGradeBudget)
					||dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGroupBudget))
				dsBreak.updateInvoicedQtyVal(qtyVal, C_BPartner_ID);	
			else if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget))
				dsBreak.updateInvoicedQtyVal(qtyVal, salesRep_ID);	
			else if (dsBreak.getBudgetType().equals("OB"))
				dsBreak.updateInvoicedQtyVal(qtyVal, orgID);
			else
				dsBreak.updateInvoicedQtyVal(qtyVal, 0);
		}
		else if(getUNS_DSBreakLine_ID() > 0)
		{
			MUNSDSBreakLine dsBreakLine = new MUNSDSBreakLine(
					getCtx(), getUNS_DSBreakLine_ID(), get_TrxName());
			MDiscountSchemaBreak dsBreak = new MDiscountSchemaBreak(
					getCtx(), dsBreakLine.getM_DiscountSchemaBreak_ID(), get_TrxName());
			if (dsBreak.getBudgetCalculation().equals(MDiscountSchemaBreak.BUDGETCALCULATION_Amount))
			{
				qtyVal = getDiscountedAmt();
				if (revers)
					qtyVal = qtyVal.negate();
			}
			if(dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerBudget)
					||dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGradeBudget)
					||dsBreak.getBudgetType().equals(MDiscountSchemaBreak.BUDGETTYPE_CustomerGroupBudget))
				dsBreakLine.updateInvoicedQtyVal(qtyVal, C_BPartner_ID);	
			else if(dsBreak.getBudgetType().equals(MDiscountSchema.BUDGETTYPE_SalesBudget))
				dsBreakLine.updateInvoicedQtyVal(qtyVal, salesRep_ID);	
			else if (dsBreak.getBudgetType().equals("OB")) 
				dsBreakLine.updateInvoicedQtyVal(qtyVal, orgID);
			else
				dsBreakLine.updateInvoicedQtyVal(qtyVal, 0);
		}
	}
	
	public void setDataValue(MUNSBonusClaimLine claimLine)
	{
		setUNS_BonusClaimLine_ID(claimLine.get_ID());
	}
	
	public static MUNSDiscountTrx getCreate(UNSDiscountBonus db, PO po1, PO po2)
	{
		MUNSDiscountTrx discountTrx = null;
		String requestBonusTableName = MTable.getTableName(db.getCtx(), db.getTable_ID());
		
		StringBuilder sql = new StringBuilder("SELECT * FROM ").append(Table_Name)
				.append(" WHERE ").append(po1.get_TableName()).append("_ID").append("=?")
				.append(" AND ").append(po2.get_TableName()).append("_ID ").append("=?")
				.append(" AND ").append(requestBonusTableName).append("_ID").append("=?");
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql.toString(), db.getTrxName());
			st.setInt(1, po1.get_ID());
			st.setInt(2, po2.get_ID());
			st.setInt(3, db.getRecord_ID());
			rs = st.executeQuery();
			if (rs.next())
			{
				discountTrx = new MUNSDiscountTrx(db.getCtx(), rs, db.getTrxName());
			}
		} catch (Exception ex)
		{
			throw new AdempiereException("could not get discount transaction." + ex.getMessage());
		} finally
		{
			DB.close(rs, st);
		}
		
		if(null == discountTrx)
		{
			discountTrx = new MUNSDiscountTrx(db);
			discountTrx.set_Value(po1.get_TableName()+"_ID", po1.get_ID());
			discountTrx.set_Value(po2.get_TableName() + "_ID", po2.get_ID());
			discountTrx.setDiscountTrx(null, null, db);
			discountTrx.setName(po1.get_Value("Name").toString());
			discountTrx.saveEx();
		}
		
		return discountTrx;
	}
	
	public void checkDiscount()
	{
		PO po = null;
		if(getM_DiscountSchema_ID() > 0)
		{
			po = new MDiscountSchema(getCtx(), getM_DiscountSchema_ID(), get_TrxName());
		}
		else if(getM_DiscountSchemaBreak_ID() > 0)
		{
			po = new MDiscountSchemaBreak(getCtx(), getM_DiscountSchemaBreak_ID(), get_TrxName());
		}
		else if(getUNS_DSBreakLine_ID() > 0)
		{
			po = new MUNSDSBreakLine(getCtx(), getUNS_DSBreakLine_ID(), get_TrxName());
		}
		else if(getUNS_DiscountBonus_ID() > 0)
		{
			po = new MUNSDiscountBonus(getCtx(), getUNS_DiscountBonus_ID(), get_TrxName());
		}
		
		if(null == po)
		{
			throw new AdempiereException("No discount reference");
		}
		
//		checkDiscount(po);
	}
	
	
	public boolean isZeroDiscount()
	{
		boolean isZero = true;
		if(getFlatValueDiscount().signum() > 0)
			isZero = false;
		if(isZero && getFirstDiscount().signum() > 0)
			isZero = false;
		if(isZero && getSecondDiscount().signum() > 0)
			isZero = false;
		if(isZero && getThirdDiscount().signum() > 0)
			isZero = false;
		if(isZero && getFourthDiscount().signum() > 0)
			isZero = false;
		if(isZero && getFifthDiscount().signum() > 0)
			isZero = false;
		if(isZero && getQtyBonuses().signum() > 0)
			isZero = false;
		
		return isZero;
	}
	
	public static MUNSDiscountTrx[] get(PO po)
	{
		List<MUNSDiscountTrx> list = new ArrayList<>();
		
		String SQL = "SELECT * FROM " + Table_Name + " WHERE " + Table_Name 
				+ "." + po.get_TableName() + "_ID = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = DB.prepareStatement(SQL, po.get_TrxName());
			st.setInt(1, po.get_ID());
			rs = st.executeQuery();
			while (rs.next()) {
				list.add(new MUNSDiscountTrx(po.getCtx(), rs, po.get_TrxName()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		MUNSDiscountTrx[] retVal = new MUNSDiscountTrx[list.size()];
		list.toArray(retVal);
		return retVal;
	}
	
	public boolean isOverBudget()
	{
		boolean result = false;
		UNSDiscountBonus discountBonus = null;
		if(getC_Order_ID() > 0)
		{
			MOrder order = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
			discountBonus = new UNSDiscountBonus(
					getCtx(), order.get_Table_ID(), order.get_ID(), get_TrxName());
			discountBonus.setDataValue(0, 0, Env.ZERO, Env.ZERO, order.getGrandTotal(), false);
		}
		else if(getC_OrderLine_ID() > 0)
		{
			MOrderLine orderLine = new MOrderLine(getCtx(), getC_OrderLine_ID(), get_TrxName());
			discountBonus = new UNSDiscountBonus(
					getCtx(), orderLine.get_Table_ID(), orderLine.get_ID(), get_TrxName());
			discountBonus.setDataValue(
					orderLine.getM_Product_ID(), orderLine.getC_UOM_ID(), orderLine.getQtyEntered()
					, orderLine.getPriceActual(), orderLine.getLineNetAmt(), false);
		}
		else if(getC_Invoice_ID() > 0)
		{
			MInvoice invoice = new MInvoice(getCtx(), getC_Invoice_ID(), get_TrxName());
			discountBonus = new UNSDiscountBonus(
					getCtx(), invoice.get_Table_ID(), invoice.get_ID(), get_TrxName());
			discountBonus.setDataValue(0, 0, Env.ZERO, Env.ZERO, invoice.getGrandTotal(), false);
		}
		else if(getC_InvoiceLine_ID() > 0)
		{
			MInvoiceLine invoiceLine = new MInvoiceLine(getCtx(), getC_InvoiceLine_ID(), get_TrxName());
			discountBonus = new UNSDiscountBonus(
					getCtx(), invoiceLine.get_Table_ID(), invoiceLine.get_ID(), get_TrxName());
			discountBonus.setDataValue(
					invoiceLine.getM_Product_ID(), invoiceLine.getC_UOM_ID(), invoiceLine.getQtyEntered()
					, invoiceLine.getPriceActual(), invoiceLine.getLineNetAmt(), false);
		}
		
		if(getM_DiscountSchema_ID() > 0)
		{
			MDiscountSchema schema = new MDiscountSchema(
					getCtx(), getM_DiscountSchema_ID(), get_TrxName());
			result = schema.isBudgetAvailable(discountBonus);
		}
		else if(getM_DiscountSchemaBreak_ID() > 0)
		{
			MDiscountSchemaBreak schemaBreak = new MDiscountSchemaBreak(
					getCtx(), getM_DiscountSchemaBreak_ID(), get_TrxName());
			result = schemaBreak.isBudgetAvailable(discountBonus);
		}
		else if(getUNS_DSBreakLine_ID() > 0)
		{
			MUNSDSBreakLine dsbreakLine = new MUNSDSBreakLine(getCtx(), getUNS_DSBreakLine_ID(), get_TrxName());
			result = dsbreakLine.isBudgetAvailable(discountBonus);
		}
	
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCalculationType()
	{
		if(getM_DiscountSchema_ID() > 0)
			return MDiscountSchemaBreak.CALCULATIONTYPE_Value;
		
		StringBuilder sb = new StringBuilder("SELECT ").append(MDiscountSchemaBreak.COLUMNNAME_CalculationType)
				.append(" FROM ").append(MDiscountSchemaBreak.Table_Name).append(" WHERE ");
		if(getM_DiscountSchemaBreak_ID() > 0)
		{
			sb.append(MDiscountSchemaBreak.COLUMNNAME_M_DiscountSchemaBreak_ID).append(" = ")
			.append(getM_DiscountSchemaBreak_ID());
		}
		else if(getUNS_DSBreakLine_ID() > 0)
		{
			sb.append(MDiscountSchemaBreak.COLUMNNAME_M_DiscountSchemaBreak_ID).append(" = ")
			.append("(SELECT ").append(MUNSDSBreakLine.COLUMNNAME_M_DiscountSchemaBreak_ID)
			.append(" FROM ").append(MUNSDSBreakLine.Table_Name).append(" WHERE ")
			.append(MUNSDSBreakLine.COLUMNNAME_UNS_DSBreakLine_ID).append(" =")
			.append(getUNS_DSBreakLine_ID());
		}
		else
		{
			sb.append(" 1=2 ");
		}
		
		String sql = sb.toString();
		String result = DB.getSQLValueString(get_TrxName(), sql);
		return result;
	}
	
	public boolean isMix()
	{
		if(getM_DiscountSchemaBreak_ID() == 0 && getUNS_DSBreakLine_ID() == 0)
			return false;
		
		boolean isMix = false;
		String sql = new String ("SELECT IsMix FROM M_DiscountSchemaBreak WHERE M_DiscountSchemaBreak_ID = ");
		if(getM_DiscountSchemaBreak_ID() > 0)
		{
			sql += "" + getM_DiscountSchemaBreak_ID();
		}
		else if(getUNS_DSBreakLine_ID() > 0)
		{
			sql += "(SELECT M_DiscountSchemaBreak_ID FROM UNS_DSBreakLine WHERE UNS_DSBreakLine_ID = "
					+ getUNS_DSBreakLine_ID() + ")";
		}
		
		isMix = DB.getSQLValueString(get_TrxName(), sql).equals("Y");
		return isMix;
	}
}
