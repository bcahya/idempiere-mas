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
package com.uns.model;

/** Interface Extension for compiere.model.I_C_DocType
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_C_DocType extends org.compiere.model.I_C_DocType
{
	public static final String DOCBASETYPE_Budget = "BUD";
	public static final String DOCBASETYPE_MaterialRequest = "MRN";
	public static final String DOCBASETYPE_MaterialRequestBudgeted = "MRQ";
	public static final String DOCBASETYPE_PaymentRequest = "PRQ";
	public static final String DOCBASETYPE_Cheque = "CEQ";
	public static final String DOCBASETYPE_Budget_Additional = "BAD";
	public static final String DOCBASETYPE_Billing = "BIL";
	public static final String DOCBASETYPE_ShipmentSchedule = "MSS";
	public static final String DOCBASETYPE_ProductionSchedule = "MPS";
	public static final String DOCBASETYPE_Production = "PCO";
	public static final String DOCBASETYPE_WorkOrder = "WOD";
	public static final String DOCBASETYPE_WorkOrderNonTrx = "WON";
	public static final String DOCBASETYPE_Project = "PRJ";

	/** Coconut  RMP = CCR **/
	public static final String DOCBASETYPE_CoconutRMP = "RMP";
	public static final String DOCBASETYPE_RMP_KB_Campur = "KBC";
	public static final String DOCBASETYPE_RMP_KB_Pilih = "KBP";
	public static final String DOCBASETYPE_RMP_K_Cungkil = "KCL";
	public static final String DOCBASETYPE_RMP_K_Kopra = "KKP";

	/** Clinic Documents. */
	public static final String DOCBASETYPE_Clinic = "CLN";	
	
	/** QAD Document */
	public static final String DOCBASETYPE_QAD = "QAD";
	
	/** Costing Document */
	public static final String DOCBASETYPE_ActualCostReconciliation = "COS";

	/** loan installment */
	public static final String DOCBASETYPE_LoanInstallment = "LNI";
	
	/** Utilities Uses */
	public static final String DOCBASETYPE_UtilitiesUses = "UUS";
	
	
}