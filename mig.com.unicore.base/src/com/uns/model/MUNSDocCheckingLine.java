/**
 * 
 */
package com.uns.model;

import java.util.Properties;

/**
 * @author toriq
 *
 */
public class MUNSDocCheckingLine extends X_UNS_DocCheckingLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4625302192968277994L;

	/**
	 * 
	 */
	public MUNSDocCheckingLine(Properties ctx, int UNS_DocCheckingLine_ID, String trxname) {
		// TODO Auto-generated constructor stub
		super(ctx, UNS_DocCheckingLine_ID, trxname);
	}
	
	public MUNSDocCheckingLine (MUNSDocChecking DocChecking)
	{
		this (DocChecking.getCtx(), 0, DocChecking.get_TrxName());
		if (DocChecking.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setClientOrg(DocChecking.getAD_Client_ID(), DocChecking.getAD_Org_ID());
		setUNS_DocChecking_ID(DocChecking.get_ID());
	}
}