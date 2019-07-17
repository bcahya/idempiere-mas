/**
 * 
 */
package com.unicore.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.compiere.util.DB;

/**
 * @author ALBURHANY
 *
 */
public class MUNSVATDocNo extends X_UNS_VATDocNo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6366055269893767305L;

	/**
	 * @param ctx
	 * @param UNS_VATDocNo_ID
	 * @param trxName
	 */
	public MUNSVATDocNo(Properties ctx, int UNS_VATDocNo_ID, String trxName) {
		super(ctx, UNS_VATDocNo_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSVATDocNo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if(!newRecord && getUsageStatus().equals(USAGESTATUS_Used))
		{
			String sql = "SELECT COUNT(*) FROM UNS_VATDocNo WHERE UNS_VATRegisteredSequences_ID = ?"
					+ " AND UsageStatus <> 'U'";
			if(DB.getSQLValue(get_TrxName(), sql, getUNS_VATRegisteredSequences_ID()) == 0)
			{
				MUNSVATRegisteredSequences rs = new MUNSVATRegisteredSequences(getCtx(), getUNS_VATRegisteredSequences_ID(), get_TrxName());
				if(!rs.getDocStatus().equals("CL") && !rs.processIt(DocAction.ACTION_Close) || !rs.save())
				{
					log.saveError("Error", rs.getProcessMsg());
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @param ctx
	 * @param Division_ID
	 * @param trxName
	 * @return {@link MUNSVATDocNo}
	 */
	public static MUNSVATDocNo getUnUseByDiv(Properties ctx, int Division_ID, String trxName)
	{
		String sql = "SELECT vat.UNS_VATRegisteredSequences_ID FROM UNS_VATRegisteredSequences vat WHERE"
				+ " vat.AD_Org_ID = ? AND vat.DocStatus IN ('CO', 'CL') AND EXISTS (SELECT 1 FROM"
				+ " UNS_VATDocNo vdn WHERE vdn.UNS_VATRegisteredSequences_ID = vat.UNS_VATRegisteredSequences_ID"
				+ " AND vdn.UsageStatus = 'NU') ORDER BY DateReceived ASC";
		int squence = DB.getSQLValue(trxName, sql, Division_ID);
		
		if(squence > 0)
		{
			MUNSVATDocNo dn = null;
			dn = new Query(ctx, Table_Name, COLUMNNAME_UNS_VATRegisteredSequences_ID + "=? AND "
					+ COLUMNNAME_UsageStatus + "=?", trxName)
						.setParameters(squence, USAGESTATUS_NotUsed).setOrderBy(COLUMNNAME_SequenceUsedNo).first();
			return dn;
		}
		
		return null;
	}
	
	public static MUNSVATDocNo getByVATLine(Properties ctx, int UNS_VATLine_ID, String trxName)
	{
		MUNSVATDocNo dn = null;
		dn = new Query(ctx, Table_Name, COLUMNNAME_UNS_VATLine_ID + "=?", trxName).setParameters(UNS_VATLine_ID).first();
		return dn;
	}
	
	public static MUNSVATDocNo getByTaxNo(Properties ctx, int Org_ID, String taxNo, String trxName)
	{
		MUNSVATDocNo dn = null;
		dn = new Query(ctx, Table_Name, COLUMNNAME_AD_Org_ID + "=? AND " + COLUMNNAME_TaxInvoiceNo + "=?", trxName)
					.setParameters(Org_ID, taxNo).first();
		return dn;
	}
}