/**
 * 
 */
package com.unicore.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import com.uns.util.MessageBox;

/**
 * @author menjangan
 *
 */
public class MUNSSOConfirmationLine extends X_UNS_SOConfirmationLine {
	
	private MUNSSOConfirmation m_parent;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param ctx
	 * @param UNS_SOConfirmationLine_ID
	 * @param trxName
	 */
	public MUNSSOConfirmationLine(Properties ctx,
			int UNS_SOConfirmationLine_ID, String trxName) {
		super(ctx, UNS_SOConfirmationLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSSOConfirmationLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public MUNSSOConfirmationLine(MUNSSOConfirmation parent)
	{
		super(parent.getCtx(), 0, parent.get_TrxName());
		setAD_Org_ID(parent.getAD_Org_ID());
		setUNS_SOConfirmation_ID(parent.get_ID());		
	}
	
	public MUNSSOConfirmation getParent()
	{
		if(null != m_parent)
			return m_parent;
		
		m_parent = (MUNSSOConfirmation) getUNS_SOConfirmation();
		return m_parent;
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(getLine() <= 0)
		{
			int LineNo = DB.getSQLValue(get_TrxName(), "SELECT COALESCE(MAX(line), 0) + 10 FROM UNS_SOConfirmationLine WHERE UNS_SOConfirmation_ID =?", getUNS_SOConfirmation_ID());
			setLine(LineNo);
		}
		
		if(!is_new() && is_ValueChanged(COLUMNNAME_PriceActual))
		{
			String question = Msg.getMsg(getCtx(), "ChangeActualPriceMsg");
			String title = Msg.getMsg(getCtx(), "ChangeActualPriceTitle");
			int retVal = MessageBox.showMsg(this, getCtx(), question, title, MessageBox.OKCANCEL, MessageBox.ICONQUESTION);
			if(MessageBox.RETURN_OK == retVal)
			{
				deleteDiscountTrx();
			}
			else
			{
				question = Msg.getMsg(getCtx(), "ReturnActualPriceMsg");
				title = Msg.getMsg(getCtx(), "ReturnActualPriceTitle");
				int retval = MessageBox.showMsg(this, getCtx(), question, title , MessageBox.YESNO, MessageBox.ICONQUESTION);
				if(MessageBox.RETURN_YES == retval)
					setPriceActual((BigDecimal)get_ValueOld(COLUMNNAME_PriceActual));
				else
					throw new AdempiereException("Price Actual tidak dapat dirubah.");
			}
		}
		return super.beforeSave(newRecord);
	}
	
	private void deleteDiscountTrx()
	{
		String columnRef = getParent().getConfirmationType().equals(MUNSSOConfirmation.CONFIRMATIONTYPE_OrderConfirmation) 
				? COLUMNNAME_C_OrderLine_ID : COLUMNNAME_C_InvoiceLine_ID;
		int param = getParent().getConfirmationType().equals(MUNSSOConfirmation.CONFIRMATIONTYPE_OrderConfirmation) 
				? getC_OrderLine_ID() : getC_InvoiceLine_ID();
		StringBuilder sb = new StringBuilder("DELETE FROM UNS_DiscountTrx WHERE ")
			.append(columnRef).append(" = ").append(param);
		
		int retVal = DB.executeUpdate(sb.toString(), false, get_TrxName());
		if(retVal == -1)
			throw new AdempiereException("FAILED ON DELETE LINE DISCOUNT TRX");
	}
}
