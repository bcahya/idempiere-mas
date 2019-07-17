/**
 * 
 */
package com.unicore.model;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;

import com.uns.base.model.Query;

/**
 * @author user
 * 
 */
public class MUNSDiscountBonus extends X_UNS_DiscountBonus
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1194026458056091371L;

	/**
	 * @param ctx
	 * @param UNS_DiscountBonus_ID
	 * @param trxName
	 */
	public MUNSDiscountBonus(Properties ctx, int UNS_DiscountBonus_ID, String trxName)
	{
		super(ctx, UNS_DiscountBonus_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSDiscountBonus(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	public static MUNSDiscountBonus[] getBonus(PO po)
	{
		StringBuilder whereClauseFinal = new StringBuilder(" ");

		if (po.get_Table_ID() == MDiscountSchema.Table_ID)
		{
			whereClauseFinal =
					whereClauseFinal.append(MDiscountSchema.COLUMNNAME_M_DiscountSchema_ID).append("=")
							.append(po.get_ID());
		}
		else if (po.get_Table_ID() == MDiscountSchemaBreak.Table_ID)
		{
			whereClauseFinal =
					whereClauseFinal.append(MDiscountSchemaBreak.COLUMNNAME_M_DiscountSchemaBreak_ID)
							.append("=").append(po.get_ID());
		}
		else if (po.get_Table_ID() == MUNSDSBreakLine.Table_ID)
		{
			whereClauseFinal =
					whereClauseFinal.append(MUNSDSBreakLine.COLUMNNAME_UNS_DSBreakLine_ID).append("=")
							.append(po.get_ID());
		}

		List<MUNSDiscountBonus> discountBonuses =
				new Query(po.getCtx(), MUNSDiscountBonus.Table_Name, whereClauseFinal.toString(),
						po.get_TrxName()).setOrderBy(MUNSDiscountBonus.COLUMNNAME_SeqNo).list();

		return discountBonuses.toArray(new MUNSDiscountBonus[discountBonuses.size()]);
	}
	
	public MUNSDiscountBonus(PO po)
	{
		super(po.getCtx(), 0, po.get_TrxName());
		setAD_Org_ID(po.getAD_Org_ID());
		set_Value(po.get_TableName() + "_ID", po.get_ID());
	}
	
	public void copyValuesOf(MUNSDiscountBonus prev)
	{
		setAD_Org_ID(prev.getAD_Org_ID());
		setBreakDiscount(prev.getBreakDiscount());
		setM_Product_ID(prev.getM_Product_ID());
	}
	
	public void checkMaxDiscount(MUNSDiscountTrx discountTrx)
	{
		StringBuilder errorMsg = new StringBuilder();
		if(discountTrx.getQtyBonuses().compareTo(getBreakDiscount()) > 0)
		{
			errorMsg.append("Discount bonus Qty [").append(discountTrx.getFirstDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("]")
			.append("can't bigger than Qty of schema [").append(getBreakDiscount()
					.setScale(2, RoundingMode.HALF_DOWN)).append("] ");
		}
		
		String msg = errorMsg.toString();
		
		if(null != msg && !msg.isEmpty())
		{
			throw new AdempiereException(msg);
		}
	}
}
