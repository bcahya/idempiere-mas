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

package org.matica.form;

import org.matica.form.listener.MAT_ButtonEvent;
import org.matica.form.listener.MAT_FieldChangeEvent;
import org.matica.form.listener.MAT_I_BusinessLogic;

public class MAT3TestBusinessLogic extends MAT_I_BusinessLogic {


	@Override
	public void onWindowOpened() {
		System.out.println(MAT3TestBusinessLogic.class.getName() + "-> Window Opened");
 		
	}

	@Override
	public void onWindowClosed() {
		System.out.println(MAT3TestBusinessLogic.class.getName() + "-> Window Closed");
	}


	@Override
	public void onButton(MAT_ButtonEvent e) {
		System.out.println(MAT3TestBusinessLogic.class.getName() + "-> onButton: " + e.getButtonName() + " " + e );
	}

	@Override
	public void onFieldChange(MAT_FieldChangeEvent e) {
		System.out.println(MAT3TestBusinessLogic.class.getName() + "-> onFieldChange " + e.getFieldName() + " " + e);
	}

	@Override
	public void onConfirmOK() {
		System.out.println(MAT3TestBusinessLogic.class.getName() + "-> onConfirmOk");
	}

	@Override
	public void onConfirmCancel() {
		System.out.println(MAT3TestBusinessLogic.class.getName() + "-> onConfirmCancel");
		
	}

	@Override
	public void onConfirmRefresh() {
		// TODO Auto-generated method stub
		
	}

}
