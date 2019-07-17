/**
 * 
 */
package com.uns.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * @author root
 *
 */
public class MUNSVoucherCode extends X_UNS_Voucher_Code {

	/**
	 * 
	 */
	private static final long serialVersionUID = 996223053291564839L;
	private MUNSVoucher m_parent = null;

	/**
	 * @param ctx
	 * @param UNS_Voucher_Code_ID
	 * @param trxName
	 */
	public MUNSVoucherCode(Properties ctx, int UNS_Voucher_Code_ID,
			String trxName) {
		super(ctx, UNS_Voucher_Code_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSVoucherCode(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}
	
	public MUNSVoucherCode(MUNSVoucher parent)
	{
		super(parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setUNS_Voucher_ID(parent.get_ID());
		m_parent = parent;
	}
	
	
	/**
	 * Get Parent Record
	 */
	public MUNSVoucher getParent()
	{
		if(m_parent != null)
			return m_parent;
		m_parent = (MUNSVoucher) getUNS_Voucher();
		return m_parent;
	}
	
	/**
	 * valdate voucher
	 */
	public void validateVoucher()
	{
		String previxConf = getParent().getPrefix() == null ? "" : getParent().getPrefix();
		String suffixConf = getParent().getSuffix() == null ? "" : getParent().getSuffix();
		
		int startConf = getParent().getStartNo();
		int endConf = getParent().getEndNo();
		int previxLength = previxConf.length();
		int suffixLength = suffixConf.length();
		
		if(previxLength + suffixLength + 1 > getName().length())
			throw new AdempiereUserError(Msg.getMsg("", "Invalid Voucher Code"));
		
		String thePrevix = getName().substring(0, previxLength);
		String theSuffix = getName().substring(getName().length()-suffixLength, getName().length());
		String code = getName().substring(previxLength, getName().length()-suffixLength);
		Integer codeInt = null;
		
		try
		{
			codeInt = new Integer(code);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getMessage());
		}
		
		if(!thePrevix.equals(previxConf))
			throw new AdempiereUserError(Msg.getMsg("", "Invalid Previx"));
		else if(!theSuffix.equals(suffixConf))
			throw new AdempiereUserError(Msg.getMsg("", "Invalid Sufix"));
		else if(codeInt == null || codeInt > endConf || codeInt < startConf)
			throw new AdempiereUserError(Msg.getMsg("", "Invalid Voucher Code"));
	}
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if(newRecord || is_ValueChanged(COLUMNNAME_Name))
			validateVoucher();
		
		return super.beforeSave(newRecord);
	}
	
	public static void main(String[] args)
	{
		String text = "Endang+Nurseha";
		System.out.println(text.substring(3,13));
	}
	
	public static MUNSVoucherCode get(String trxName, String code, int voucherbook_id, boolean onlyNotUse)
	{
		MUNSVoucherCode voucherCode = null;
		
		String sql = "SELECT * FROM ".concat(Table_Name).concat(" WHERE ")
				.concat(COLUMNNAME_Name).concat("=? AND ").concat(COLUMNNAME_UNS_Voucher_ID)
				.concat("= ?");
		
		if(onlyNotUse)
		{
			sql = sql.concat(" AND ").concat(COLUMNNAME_IsUsed).concat("='N'");
		}
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try
		{
			st = DB.prepareStatement(sql, trxName);
			st.setString(1, code);
			st.setInt(2, voucherbook_id);
			rs = st.executeQuery();
			if(rs.next())
			{
				voucherCode = new MUNSVoucherCode(Env.getCtx(), rs, trxName);
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
		
		return voucherCode;
	}
}
