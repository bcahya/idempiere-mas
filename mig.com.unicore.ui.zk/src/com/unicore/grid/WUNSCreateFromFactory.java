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

import com.unicore.model.I_UNS_Cvs_RptCustomer;
import com.unicore.model.I_UNS_PackingList;
import com.unicore.model.I_UNS_PackingList_Order;
import com.uns.model.I_UNS_Audit;

import com.unicore.ui.grid.WCreateFromAudit;
import com.unicore.ui.grid.WCreateFromCanvas;
import com.unicore.ui.grid.WCreateFromInvoice;
import com.unicore.ui.grid.WCreateFromPackingList;
import com.unicore.ui.grid.WCreateFromPackingListOrder;

/**
 * 
 * @author Unta-Andy
 *
 */
public class WUNSCreateFromFactory implements ICreateFromFactory 
{

	@Override
	public ICreateFrom create(GridTab mTab) 
	{
		String tableName = mTab.getTableName();
		if (tableName.equals(I_C_Invoice.Table_Name))
			return new WCreateFromInvoice(mTab);
		else if (tableName.equals(I_UNS_PackingList_Order.Table_Name))
			return new WCreateFromPackingListOrder(mTab);
		else if (tableName.equals(I_UNS_Cvs_RptCustomer.Table_Name))
			return new WCreateFromCanvas(mTab);
		else if (tableName.equals(I_UNS_PackingList.Table_Name))
			return new WCreateFromPackingList(mTab);
		else if (tableName.equals(I_UNS_Audit.Table_Name))
			return new WCreateFromAudit(mTab);
		return null;
	}

}
