/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package com.uns.base.model;

/** Interface Extension for compiere.model.I_C_DocType
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_C_DocType extends org.compiere.model.I_C_DocType
{
	public static final String DOCBASETYPE_Budget = "BUD";
	public static final String DOCBASETYPE_MaterialRequest = "MRQ";
	public static final String DOCBASETYPE_PaymentRequest = "PRQ";
	public static final String DOCBASETYPE_Cheque = "CEQ";
	public static final String DOCBASETYPE_Budget_Additional = "BAD";
	public static final String DOCBASETYPE_Billing = "BIL";

	/** Coconut  RMP = CCR **/
	public static final String DOCBASETYPE_RMPKBCampur = "KBC";
	public static final String DOCBASETYPE_RMPKBPilih = "KBP";
	public static final String DOCBASETYPE_RMPKelapaCungkil = "KCL";
	public static final String DOCBASETYPE_RMPKelapaKopra = "KKP";	
	
	/** BongkarMuat */
	public static final String DocBaseType_BongkarMuat = "BMT";
	
	/** Sales Enquiry */
	public static final String DocBaseType_SalesEnquiry = "SEN";
	
	/** Loading Cost */
	public static final String DocBaseType_LoadingCost = "LDC";
	
}