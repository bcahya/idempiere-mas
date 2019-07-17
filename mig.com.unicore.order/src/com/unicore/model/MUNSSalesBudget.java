/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MTree_NodeBP;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author menjangan
 *
 */
public class MUNSSalesBudget extends X_UNS_SalesBudget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_SalesBudget_ID
	 * @param trxName
	 */
	public MUNSSalesBudget(Properties ctx, int UNS_SalesBudget_ID,
			String trxName) {
		super(ctx, UNS_SalesBudget_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSalesBudget(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean afterSave(boolean newRecord, boolean success)
	{
		if(!updateHeader())
			return false;
		
		return super.afterSave(newRecord, success);
	}
	
	private boolean updateHeader()
	{
		String tableName = "";
		int param = 0;
		
		if(getM_DiscountSchema_ID() > 0)
		{
			tableName = MDiscountSchema.Table_Name;
			param = getM_DiscountSchema_ID();
		}
		else if(getM_DiscountSchemaBreak_ID() > 0)
		{
			tableName = MDiscountSchemaBreak.Table_Name;
			param = getM_DiscountSchemaBreak_ID();
		}
		else if(getUNS_DSBreakLine_ID() > 0)
		{
			tableName = MUNSDSBreakLine.Table_Name;
			param = getUNS_DSBreakLine_ID();
		}
		else
		{
			throw new AdempiereException("Error when update header.");
		}
		
		StringBuilder prepareSQL = new StringBuilder("UPDATE ")
		.append(tableName).append(" SET ").append(MDiscountSchema.COLUMNNAME_QtyReserved)
		.append(" = (SELECT SUM(COALESCE(").append(COLUMNNAME_QtyReserved)
		.append(",0)) FROM ").append(Table_Name).append(" WHERE ").append(tableName)
		.append("_ID = ").append(param).append("), ").append(MDiscountSchema.COLUMNNAME_MovementQty)
		.append(" = (SELECT SUM(COALESCE(").append(COLUMNNAME_MovementQty).append(", 0)) FROM ")
		.append(Table_Name).append(" WHERE ").append(tableName).append("_ID = ").append(param).append("), ")
		.append(MDiscountSchema.COLUMNNAME_QtyInvoiced).append(" = (SELECT SUM(COALESCE(")
		.append(COLUMNNAME_QtyInvoiced).append(", 0)) FROM ").append(Table_Name).append(" WHERE ")
		.append(tableName).append("_ID = ").append(param).append(") ")
		.append(" WHERE ").append(tableName).append("_ID = ").append(param);
		
		int result = DB.executeUpdate(prepareSQL.toString(), get_TrxName());
		return result >= 0;
	}
	
	public boolean isParentOf(int C_BPartner_ID)
	{
		MTree_NodeBP treeNode = MTree_NodeBP.get(getCtx(), 0, getC_BPartner_ID(), get_TrxName());
		return treeNode.isParentOf(C_BPartner_ID);
	}

	public boolean updateReserved(BigDecimal value)
	{
		BigDecimal tempReservedQty = getQtyReserved();
		BigDecimal newReservedQty = tempReservedQty.add(value);
		setQtyReserved(newReservedQty);
		return save();
	}
	
	public BigDecimal getAllowedOrder()
	{
		BigDecimal allowed = getQtyAllocated().subtract(getQtyReserved());
		return allowed;
	}
	
	public BigDecimal getAllowedInvoiced()
	{
		return getQtyAllocated().subtract(getQtyInvoiced());
	}
	
	public BigDecimal getAllowedInOut()
	{
		return getQtyAllocated().subtract(getMovementQty());
	}
	
	
	public boolean isInMyOutletGrade(int C_BPartner_ID)
	{
		if(getUNS_Outlet_Grade_ID() <= 0)
			return false;
		else if(C_BPartner_ID <= 0)
			return false;
		
		MBPartner partner = MBPartner.get(getCtx(), C_BPartner_ID);
		return partner.getUNS_Outlet_Grade_ID() == getUNS_Outlet_Grade_ID();
	}
	/**
	 * 
	 * @param C_BPartner_ID
	 * @return
	 */
	public boolean isInMyBPGroup(int C_BPartner_ID)
	{
		if(C_BPartner_ID <= 0)
			return false;
		else if(getC_BP_Group_ID() <= 0)
			return false;
		
		MBPartner partner = MBPartner.get(getCtx(), C_BPartner_ID);
		return getC_BP_Group_ID() == partner.getC_BP_Group_ID();
	}
	
	/**
	 * 
	 * @param po
	 * @param QtyAllocated
	 */
	public MUNSSalesBudget(PO po, BigDecimal QtyAllocated)
	{
		super(po.getCtx(), 0, po.get_TrxName());
		set_Value(po.get_TableName().concat("_ID"), po.get_ID());
		setAD_Org_ID(po.getAD_Org_ID());
		setAD_Client_ID(po.getAD_Client_ID());
		setQtyAllocated(QtyAllocated);
		setQtyInvoiced(Env.ZERO);
		setQtyReserved(Env.ZERO);
		setMovementQty(Env.ZERO);
	}
}
