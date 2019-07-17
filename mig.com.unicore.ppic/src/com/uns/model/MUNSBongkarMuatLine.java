/**
 * 
 */
package com.uns.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.PO;
import org.compiere.util.DB;

/**
 * @author menjangan
 *
 */
public class MUNSBongkarMuatLine extends X_UNS_BongkarMuatLine {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MUNSBongkarMuat m_Parent =null;

	/**
	 * @param ctx
	 * @param UNS_BongkarMuatLine_ID
	 * @param trxName
	 */
	public MUNSBongkarMuatLine(Properties ctx, int UNS_BongkarMuatLine_ID,
			String trxName) {
		super(ctx, UNS_BongkarMuatLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSBongkarMuatLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @return
	 */
	public PO getHeader()
	{
		if (m_Parent == null)
			m_Parent = new MUNSBongkarMuat(
					getCtx(), getUNS_BongkarMuat_ID(), get_TrxName());
		
		return m_Parent;
	}
	
	/**
	 * 
	 */
	public boolean beforeSave (boolean newRecord)
	{
		if (getLine() == 0)
		{
			String sql = 
					"SELECT COALESCE(MAX(Line),0)+10 FROM " + Table_Name 
					+ " WHERE " + COLUMNNAME_UNS_BongkarMuat_ID + " =?";
			int ii = DB.getSQLValueEx (get_TrxName(), sql, getUNS_BongkarMuat_ID());
			setLine (ii);
		}
		
		if (!newRecord)
		{
			if (null == getTypeBongkar())
				throw new FillMandatoryException("TypeBongkar");
		}
		
//		setLineNetAmt(getPriceCost().multiply(getQty()));
		
		return true;
	}
	
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}
	
	public MUNSBongkarMuatLine(MUNSBongkarMuat bongkarMuat)
	{
		this(bongkarMuat.getCtx(),0,bongkarMuat.get_TrxName());
		setClientOrg(bongkarMuat);
		setUNS_BongkarMuat_ID(bongkarMuat.getUNS_BongkarMuat_ID());
		m_Parent = bongkarMuat;
	}
	
	/**
	 * 
	 */
	public boolean afterSave(boolean newRecord, boolean sucess)
	{
		if (!updateHeader())
			return false;
		
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	protected BigDecimal getTotalLineNet()
	{
		BigDecimal total = BigDecimal.ZERO;
		MUNSBongkarMuatLine[] lines = ((MUNSBongkarMuat)getHeader()).getLines();
		for (int i=0; i< lines.length; i++)
		{
			MUNSBongkarMuatLine line = lines[i];
			total = total.add(line.getLineNetAmt());
		}
		return total;
	}
	
	/**
	 * 
	 * @return
	 */
	protected boolean updateHeader()
	{
		MUNSBongkarMuat bm = 
				new MUNSBongkarMuat(getCtx(), getUNS_BongkarMuat_ID(), get_TrxName());
		bm.setTotalAmt(getTotalLineNet());
		if (!bm.save())
			return false;
		
		return true;
	}
}
