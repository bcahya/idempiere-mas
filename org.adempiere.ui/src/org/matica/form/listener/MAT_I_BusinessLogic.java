/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          * 
 * http://www.adempiere.org                                           * 
 *                                                                    * 
 * Copyright (C) Matteo Carminati	                                    * 
 * Copyright (C) Contributors                                         * 
 *                                                                    * 
 * This program is free software; you can redistribute it and/or      * 
 * modify it under the terms of the GNU General Public License        * 
 * as published by the Free Software Foundation; either version 2     * 
 * of the License, or (at your option) any later version.             * 
 *                                                                    * 
 * This program is distributed in the hope that it will be useful,    * 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     * 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       * 
 * GNU General Public License for more details.                       * 
 *                                                                    * 
 * You should have received a copy of the GNU General Public License  * 
 * along with this program; if not, write to the Free Software        * 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         * 
 * MA 02110-1301, USA.                                                * 
 *                                                                    * 
 * Contributors:                                                      * 
 *  - Matteo Carminati (mcarminati@ma-tica.it)                        *
 *                                                                    *
 * Sponsors:                                                          *
 *  - MA-TICA (http://www.ma-tica.it/)                                *
 **********************************************************************/

package org.matica.form.listener;


public abstract class MAT_I_BusinessLogic 
{
	
	
	protected MAT_I_Listener m_lstowner = null;
	
	public abstract void onWindowOpened();

	public abstract void onWindowClosed();

	public abstract void onButton(MAT_ButtonEvent e);

	public abstract void onFieldChange(MAT_FieldChangeEvent e);	
	
	public abstract void onConfirmOK();

	public abstract void onConfirmCancel();

	public abstract void onConfirmRefresh();
	
	public void setListenerOwner(MAT_I_Listener lst)
	{
		this.m_lstowner = lst;
	}
}
