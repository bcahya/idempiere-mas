/******************************************************************************
 * Copyright (C) 2013 Elaine Tan                                              *
 * Copyright (C) 2013 Trek Global
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package com.unicore.grid;

import org.compiere.grid.ICreateFrom;
import org.compiere.grid.ICreateFromFactory;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_Movement;

import com.unicore.model.I_UNS_Cvs_RptCustomer;
import com.unicore.model.I_UNS_PackingList;
import com.unicore.model.I_UNS_PackingList_Order;
import com.uns.model.I_UNS_Audit;
import com.uns.model.I_UNS_VAT;

import com.unicore.ui.grid.VCreateFromAuditUI;
import com.unicore.ui.grid.VCreateFromCanvasUI;
import com.unicore.ui.grid.VCreateFromInvoiceUI;
import com.unicore.ui.grid.VCreateFromOrder;
import com.unicore.ui.grid.VCreateFromPackingListOrderUI;
import com.unicore.ui.grid.VCreateFromPackingListUI;
import com.unicore.ui.grid.VCreateFromPaymentUI;
import com.unicore.ui.grid.VCreateFromRequisition;
import com.unicore.ui.grid.VCreateFromVATUI;


/**
 * 
 * @author Unta-Andy
 *
 */
public class UNSCreateFromFactory implements ICreateFromFactory 
{

	@Override
	public ICreateFrom create(GridTab mTab) 
	{
		String tableName = mTab.getTableName();
		if (tableName.equals(I_C_Invoice.Table_Name))
			return new VCreateFromInvoiceUI(mTab);
		if (tableName.equals(I_UNS_PackingList_Order.Table_Name))
			return new VCreateFromPackingListOrderUI(mTab);
		if (tableName.equals(I_M_Movement.Table_Name))
			return new VCreateFromRequisition(mTab);
		if(tableName.equals(I_C_Order.Table_Name))
			return new VCreateFromOrder(mTab);
		if(tableName.equals(I_UNS_Cvs_RptCustomer.Table_Name))
			return new VCreateFromCanvasUI(mTab);
		if(tableName.equals(I_UNS_VAT.Table_Name))
			return new VCreateFromVATUI(mTab);
		if(tableName.equals(I_UNS_Audit.Table_Name))
			return new VCreateFromAuditUI(mTab);
		if(tableName.equals(I_C_Payment.Table_Name))
			return new VCreateFromPaymentUI(mTab);
		if(tableName.equals(I_UNS_PackingList.Table_Name))
			return new VCreateFromPackingListUI(mTab);

		return null;
	}

}