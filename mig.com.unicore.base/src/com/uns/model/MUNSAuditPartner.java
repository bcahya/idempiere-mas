package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.DB;

import com.uns.base.model.Query;

/**
 * @author Burhani Adam
 *
 */

public class MUNSAuditPartner extends X_UNS_AuditPartner {
	
	private static final long serialVersionUID = -5548358215727078895L;

	public MUNSAuditPartner(Properties ctx, int UNS_AuditPartner_ID,
			String trxName) {
		super(ctx, UNS_AuditPartner_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MUNSAuditPartner(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public static MUNSAuditPartner getOfParentAndPartner(MUNSAudit audit, int BPartner_ID)
	{
		MUNSAuditPartner partner = null;
		partner = new Query(audit.getCtx(), Table_Name,
				COLUMNNAME_UNS_Audit_ID + "=? AND " + COLUMNNAME_C_BPartner_ID + "=?", audit.get_TrxName())
					.setParameters(audit.get_ID(), BPartner_ID).first();
		
		return partner;
	}
	
	@Override
	protected boolean beforeDelete()
	{
		String sql = "DELETE FROM UNS_AuditDocument WHERE UNS_AuditPartner_ID=?";
		DB.executeUpdate(sql, get_ID(), get_TrxName());
		
		return super.beforeDelete();
	}
}
