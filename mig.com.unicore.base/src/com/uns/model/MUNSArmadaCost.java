/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MInOutLine;
import org.compiere.model.MOrderLine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Burhani Adam
 *
 */
public class MUNSArmadaCost extends X_UNS_ArmadaCost {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2556670364202840385L;

	/**
	 * @param ctx
	 * @param UNS_ArmadaCost_ID
	 * @param trxName
	 */
	public MUNSArmadaCost(Properties ctx, int UNS_ArmadaCost_ID, String trxName) {
		super(ctx, UNS_ArmadaCost_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSArmadaCost(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	protected boolean beforeSave(boolean newRecord)
	{
		if(getC_OrderLine_ID() > 0)
		{
			MOrderLine line = new MOrderLine(getCtx(), getC_OrderLine_ID(), get_TrxName());
			BigDecimal amt = line.getPriceActual().multiply(getQty()); 
			setAmount(amt);
		}
		
		return true;
	}
	
	public boolean afterSave(boolean newRecord, boolean success)
	{
		if(!success)
			return false;
		
		if(getM_InOutLine_ID() > 0)
		{
			MInOutLine line = new MInOutLine(getCtx(), getM_InOutLine_ID(), get_TrxName());
			String sql = "SELECT SUM(Qty) FROM UNS_ArmadaCost WHERE M_InOutLine_ID=?";
			BigDecimal qty = DB.getSQLValueBD(get_TrxName(), sql, getM_InOutLine_ID());
			if(qty.compareTo(line.getMovementQty()) > 0)
			{
				log.saveError("Error", "Over Quantity");
				return false;
			}
		}
		
		MUNSArmada armada = new MUNSArmada(getCtx(), getUNS_Armada_ID(), get_TrxName());
		BigDecimal qtySubtract = newRecord ? Env.ZERO : (BigDecimal) (get_ValueOld(COLUMNNAME_Amount));
		armada.setAmount(armada.getAmount().add(getAmount()).subtract(qtySubtract));
		armada.saveEx();
		
		return true;
	}
	
	public boolean beforeDelete()
	{
		MUNSArmada armada = new MUNSArmada(getCtx(), getUNS_Armada_ID(), get_TrxName());
		armada.setAmount(armada.getAmount().subtract(getAmount()));
		armada.saveEx();
		
		return true;
	}
}
