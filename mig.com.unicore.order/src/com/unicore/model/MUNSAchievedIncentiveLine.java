/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author root
 *
 */
public class MUNSAchievedIncentiveLine extends X_UNS_AchievedIncentive_Line {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSAchievedIncentive m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_AchievedIncentive_Line_ID
	 * @param trxName
	 */
	public MUNSAchievedIncentiveLine(Properties ctx,
			int UNS_AchievedIncentive_Line_ID, String trxName) {
		super(ctx, UNS_AchievedIncentive_Line_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSAchievedIncentiveLine(Properties ctx, ResultSet rs,
			String trxName) {
		super(ctx, rs, trxName);
	}
	
	/**
	 * 
	 */
	public MUNSAchievedIncentive getParent()
	{
		if(null != m_parent)
			return m_parent;
		
		return (MUNSAchievedIncentive) getUNS_AchievedIncentive();
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		return super.beforeSave(newRecord);
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		updateHeaderPeriod();
		return super.afterSave(newRecord, success);
	}
	
	/**
	 * 
	 * @param parentPeriod
	 */
	public MUNSAchievedIncentiveLine(MUNSAcvIncentiveByPeriod parentPeriod)
	{
		super(parentPeriod.getCtx(), 0, parentPeriod.get_TrxName());
		setUNS_AcvIncentiveByPeriod_ID(parentPeriod.get_ID());
		setUNS_AchievedIncentive_ID(parentPeriod.getUNS_AchievedIncentive_ID());
		setAD_Org_ID(parentPeriod.getAD_Org_ID());
		m_acvIncentiveByPeriod = parentPeriod;
	}

	
	/**
	 * 
	 */
	public void updateHeaderPeriod()
	{
		MUNSAcvIncentiveByPeriod acvPeriod = getAcvIncentivePeriod();
		String sql = "SELECT COALESCE(SUM(Amount), 0) FROM UNS_AchievedIncentive_Line "
				+ " WHERE UNS_AcvIncentiveByPeriod_ID = ?";
		BigDecimal totalAmt = DB.getSQLValueBD(get_TrxName(), sql, acvPeriod.get_ID());
		acvPeriod.setAmount(totalAmt);
		acvPeriod.saveEx();
	}
	
	private MUNSAcvIncentiveByPeriod m_acvIncentiveByPeriod = null;
	
	/**
	 * 
	 * @return
	 */
	public MUNSAcvIncentiveByPeriod getAcvIncentivePeriod()
	{
		if(null != m_acvIncentiveByPeriod)
			return m_acvIncentiveByPeriod;
		
		m_acvIncentiveByPeriod = new MUNSAcvIncentiveByPeriod(
				getCtx(), getUNS_AcvIncentiveByPeriod_ID(), get_TrxName());
		
		return m_acvIncentiveByPeriod;
	}

	/**
	 * 
	 * @param parent
	 * @param reference
	 * @param trxName
	 * @return
	 */
	public static MUNSAchievedIncentiveLine get(PO parent, PO reference, String trxName)
	{
		if(null == parent || null == reference)
			throw new AdempiereException(
					"Error on get Achieved Incentive Line no parent and no reference parameter");
		
		StringBuilder sql = new StringBuilder("SELECT * FROM ").append(Table_Name)
				.append(" WHERE ").append(parent.get_TableName()).append("_ID = ?")
				.append(" AND ").append(reference.get_TableName()).append("_ID = ?");
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = DB.prepareStatement(sql.toString(), trxName);
			st.setInt(1, parent.get_ValueAsInt(parent.get_TableName().concat("_ID")));
			st.setInt(2, reference.get_ValueAsInt(reference.get_TableName().concat("_ID")));
			rs = st.executeQuery();
			if(rs.next())
			{
				return new MUNSAchievedIncentiveLine(Env.getCtx(), rs, trxName);
			}
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param reference
	 * @param trxName
	 * @return
	 */
	public static MUNSAchievedIncentiveLine getCreate(PO reference, int C_Period_ID, int BPartner_ID, String trxName)
	{
		if(null == reference)
			throw new AdempiereException(
					"Error on get Achieved Incentive Line no reference parameter");
		
		String sql = "SELECT COUNT(l.*) FROM UNS_AchievedIncentive_Line l"
				+ " INNER JOIN UNS_AcvIncentiveByPeriod p ON p.UNS_AcvIncentiveByPeriod_ID"
				+ " = l.UNS_AcvIncentiveByPeriod_ID"
				+ " INNER JOIN UNS_AchievedIncentive i ON i.UNS_AchievedIncentive_ID = l.UNS_AchievedIncentive_ID"
				+ " WHERE i.C_BPartner_ID = ? AND " + reference.get_TableName() + "_ID = ?";
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = DB.prepareStatement(sql.toString(), trxName);
			st.setInt(1, reference.get_ValueAsInt(reference.get_TableName().concat("_ID")));
			rs = st.executeQuery();
			if(rs.next())
			{
				return new MUNSAchievedIncentiveLine(Env.getCtx(), rs, trxName);
			}
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param C_InvoiceLine_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSAchievedIncentiveLine get(int C_InvoiceLine_ID, String trxName)
	{
		if(C_InvoiceLine_ID <= 0)
			throw new AdempiereException(
					"Error on get Achieved Incentive Line no reference parameter");
		
		StringBuilder sql = new StringBuilder("SELECT * FROM ").append(Table_Name)
				.append(" WHERE C_InvoiceLine_ID = ?");
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = DB.prepareStatement(sql.toString(), trxName);
			st.setInt(1, C_InvoiceLine_ID);
			rs = st.executeQuery();
			if(rs.next())
			{
				return new MUNSAchievedIncentiveLine(Env.getCtx(), rs, trxName);
			}
		} catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public boolean isSalesIncentive()
	{
		return getC_InvoiceLine_ID() > 0;
	}
	
	public boolean isBillingIncentive()
	{
		return getC_PaymentAllocate_ID() > 0;
	}
	
	/**
	 * @param ctx
	 * @param po
	 * @param schemeType
	 * @param date
	 * @param SalesRep_ID
	 * @param SubSalesRep_ID
	 * @param baseAmt
	 * @param amount
	 * @param type
	 * @param DocumentNo
	 * @param incentive
	 * @param C_BPartner_ID
	 * @param M_Product_ID
	 * @param trxName
	 * @return
	 */
	public static MUNSAchievedIncentiveLine getCreate(Properties ctx, PO po, 
			String schemeType, Timestamp date, int SalesRep_ID, int SubSalesRep_ID, 
			BigDecimal baseAmt, BigDecimal amount, String type, 
			String DocumentNo, MUNSIncentive incentive, int C_BPartner_ID, int M_Product_ID, String trxName)
	{
		MUNSAchievedIncentiveLine line = null;
		MUNSAcvIncentiveByPeriod parent = MUNSAcvIncentiveByPeriod.getCreate(ctx,
				SalesRep_ID, C_BPartner_ID, date, trxName);
		line = MUNSAchievedIncentiveLine.get(ctx, po, schemeType, 
				parent, type, incentive.get_ID(), C_BPartner_ID, M_Product_ID, trxName);
		if(line == null)
		{		
			String nextid = "SELECT getnextid('UNS_AchievedIncentive_Line')";
			int id = DB.getSQLValue(trxName, nextid);
			String create = "INSERT INTO UNS_AchievedIncentive_Line"
					+ " (UNS_AchievedIncentive_Line_ID, UNS_AchievedIncentive_Line_UU,"
					+ " AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy,"
					+ " IsActive, UNS_AcvIncentiveByPeriod_ID, UNS_AchievedIncentive_ID, "
					+ " SalesRep_ID, UNS_Incentive_ID, SourceType, DocumentNo, DateTrx,"
					+ " AmountBase, Amount, SchemaType, C_BPartner_ID, M_Product_ID";
			if(po == null)
				create += ")";
			else
				create += ", " + po.get_TableName() + "_ID)";
			
			create += " VALUES (?, generate_uuid(), 1000007, 0, ?, ?, ?, ?, 'Y', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
			
			if(po == null)
				create += ")";
			else
				create += ", ?)";
			if(po == null)
			{
				Object[] params = {id, new Timestamp(System.currentTimeMillis()), 
					Env.getAD_User_ID(ctx), new Timestamp(System.currentTimeMillis()), 
					Env.getAD_User_ID(ctx), parent.get_ID(), parent.getUNS_AchievedIncentive_ID(),
					SubSalesRep_ID, incentive.get_ID(), type, DocumentNo, date, baseAmt, amount, 
					schemeType, C_BPartner_ID > 0 ? C_BPartner_ID : null, M_Product_ID > 0 ? M_Product_ID : null};
				DB.executeUpdate(create, params, false, trxName);
			}
			else
			{
				Object[] params = {id, new Timestamp(System.currentTimeMillis()), 
					Env.getAD_User_ID(ctx), new Timestamp(System.currentTimeMillis()), 
					Env.getAD_User_ID(ctx), parent.get_ID(), parent.getUNS_AchievedIncentive_ID(),
					SubSalesRep_ID, incentive.get_ID(), type, DocumentNo, date, baseAmt, amount, 
					schemeType, C_BPartner_ID > 0 ? C_BPartner_ID : null, M_Product_ID > 0 ? M_Product_ID : null,
					po.get_ID()};
				DB.executeUpdate(create, params, false, trxName);
			}
			
			line = new MUNSAchievedIncentiveLine(ctx, id, trxName);
		}
		else
		{
			String update = "UPDATE UNS_AchievedIncentive_Line SET"
							+ " DocumentNo = ?,"
							+ " DateTrx = ?,"
							+ " AmountBase = ?,"
							+ " Amount = ?"
							+ " WHERE UNS_AchievedIncentive_Line_ID = ?";
			DB.executeUpdate(update, new Object[]{DocumentNo, date, baseAmt, 
					amount, line.get_ID()}, false, trxName);
		}
		line.updateHeaderPeriod();
		
		return line ;
	}
	
	public static MUNSAchievedIncentiveLine get(Properties ctx, PO po, String schemeType,
			MUNSAcvIncentiveByPeriod parent, String type, int UNS_Incentive_ID,
			int C_BPartner_ID, int M_Product_ID, String trxName)
	{
		MUNSAchievedIncentiveLine line = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SchemaType = ? AND UNS_AcvIncentiveByPeriod_ID = ?"
				+ " AND UNS_Incentive_ID = ? AND SourceType = ?");
		if(M_Product_ID > 0)
			sb.append(" AND M_Product_ID = ").append(M_Product_ID);
		if(C_BPartner_ID > 0)
			sb.append(" AND C_BPartner_ID = ").append(C_BPartner_ID);
		if(po != null)
			sb.append(" AND ").append(po.get_TableName()).append("_ID = ").append(po.get_ID());

		line = new Query(ctx, Table_Name, sb.toString(), trxName).setParameters(
				schemeType, parent.get_ID(), UNS_Incentive_ID, type).first();
		
		return line;
	}
}