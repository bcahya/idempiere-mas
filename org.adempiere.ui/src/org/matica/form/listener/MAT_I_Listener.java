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

import java.util.Properties;

import org.compiere.model.GridField;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfo;


public interface MAT_I_Listener 
{
	
	public Object getFieldValue(String fieldName);
		
	public void setFieldValue(String fieldName, Object newValue);

	/**
	 * Set field value with the value from PO for the field of field name.
	 * Use this method to make sure a null value of a field to be set to the default value set in the form field
	 * definition. If the form field defined as mandatory, then throw FillMandatoryException if it tries 
	 * to force a null value.
	 * @param fieldName you must be sure the column name of the field is the same with the field name 
	 *        defined in the field form definition.
	 * @param po
	 */
	public void setFieldValue(String fieldName, PO po); 
	
	public GridField getField(String fieldName);
	
	public void disposeFrame();
	
	public void setFrameTitle(String title);
	
	public void setFrameWidth(int width);
	
	public void setFrameHeight(int height);
	
	public void setPanelVisible(String panelName, boolean visible);
	
	public ProcessInfo getProcessInfo();
	
	public String getTrxName();
	
	public Properties getCtx();
	
	public boolean isWebui();
}
