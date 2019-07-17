/**
 * 
 */
package com.uns.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Burhani Adam
 *
 */
public class MUNSAuditDocument extends X_UNS_AuditDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4727921196100677072L;

	/**
	 * @param ctx
	 * @param UNS_AuditDocument_ID
	 * @param trxName
	 */
	public MUNSAuditDocument(Properties ctx, int UNS_AuditDocument_ID,
			String trxName) {
		super(ctx, UNS_AuditDocument_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MUNSAuditDocument(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}
	
	public void setInvoice(org.compiere.model.MInvoice inv)
	{
		setAD_Org_ID(inv.getAD_Org_ID());
		setDocumentNo(inv.getDocumentNo());
		setC_Invoice_ID(inv.get_ID());
		setC_DocType_ID(inv.getC_DocType_ID());
		setDocStatus(inv.getDocStatus());
		setDescription(inv.getDescription());
		setGrandTotal(inv.getGrandTotal());
	}
}
